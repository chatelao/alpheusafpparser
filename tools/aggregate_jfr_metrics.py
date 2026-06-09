import subprocess
import os
import re
import sys
import argparse
from collections import defaultdict

def extract_metrics(jfr_file):
    metrics = {
        'fw_bytes': 0,
        'fw_count': 0,
        'fw_duration': 0.0,
        'fr_bytes': 0,
        'fr_count': 0,
        'fr_duration': 0.0,
        'alloc_main': 0,
        'tlab_bytes': 0,
        'tlab_count': 0,
        'gc_pauses': 0,
        'gc_count': 0,
        'sp_count': 0,
        'class_load_count': 0
    }

    # FileWrite
    try:
        fw_output = subprocess.check_output(["jfr", "print", "--events", "jdk.FileWrite", jfr_file], text=True)
        metrics['fw_bytes'] = sum(int(m.group(1)) for m in re.finditer(r"bytesWritten = (\d+)", fw_output))
        metrics['fw_duration'] = sum(float(m.group(1)) for m in re.finditer(r"duration = ([\d\.]+) ms", fw_output))
        metrics['fw_count'] = len(re.findall(r"jdk.FileWrite", fw_output))
    except Exception:
        pass

    # FileRead
    try:
        fr_output = subprocess.check_output(["jfr", "print", "--events", "jdk.FileRead", jfr_file], text=True)
        metrics['fr_bytes'] = sum(int(m.group(1)) for m in re.finditer(r"bytesRead = (\d+)", fr_output))
        metrics['fr_duration'] = sum(float(m.group(1)) for m in re.finditer(r"duration = ([\d\.]+) ms", fr_output))
        metrics['fr_count'] = len(re.findall(r"jdk.FileRead", fr_output))
    except Exception:
        pass

    # Allocation
    try:
        alloc_output = subprocess.check_output(["jfr", "print", "--events", "jdk.ThreadAllocationStatistics,jdk.ObjectAllocationInNewTLAB", jfr_file], text=True)

        # Split by events
        events = re.split(r"jdk\.[a-zA-Z]+ \{", alloc_output)

        for event in events:
            if 'thread = "main"' in event and 'allocated =' in event:
                m = re.search(r"allocated = ([\d\.]+) (\w+)", event)
                if m:
                    val, unit = m.groups()
                    b = float(val)
                    if unit == 'MB': b *= 1024 * 1024
                    elif unit == 'KB': b *= 1024
                    elif unit == 'GB': b *= 1024 * 1024 * 1024
                    elif unit == 'B': b *= 1
                    if b > metrics['alloc_main']:
                        metrics['alloc_main'] = b

            if 'tlabSize =' in event:
                m = re.search(r"tlabSize = (\d+)", event)
                if m:
                    metrics['tlab_bytes'] += int(m.group(1))
                    metrics['tlab_count'] += 1
    except Exception:
        pass

    # GC
    try:
        gc_output = subprocess.check_output(["jfr", "print", "--events", "jdk.GarbageCollection", jfr_file], text=True)
        metrics['gc_pauses'] = sum(float(m.group(1)) for m in re.finditer(r"sumOfPauses = ([\d\.]+) ms", gc_output))
        metrics['gc_count'] = len(re.findall(r"jdk.GarbageCollection", gc_output))
    except Exception:
        pass

    # Safepoints
    try:
        sp_output = subprocess.check_output(["jfr", "print", "--events", "jdk.SafepointBegin", jfr_file], text=True)
        metrics['sp_count'] = len(re.findall(r"jdk.SafepointBegin", sp_output))
    except Exception:
        pass

    # Class Loading
    try:
        cl_output = subprocess.check_output(["jfr", "print", "--events", "jdk.ClassLoad", jfr_file], text=True)
        metrics['class_load_count'] = len(re.findall(r"jdk.ClassLoad", cl_output))
    except Exception:
        pass

    return metrics

def version_key(v):
    clean_v = v[len("large_"):] if v.startswith("large_") else v
    # Remove any suffix like _pdf
    base_v = clean_v.split('_')[0]
    return [int(x) for x in base_v[1:].split('.')]

def main():
    parser = argparse.ArgumentParser(description="Aggregate JFR metrics and optionally check for regressions.")
    parser.add_argument("--check", action="store_true", help="Check for performance regressions against baseline.")
    parser.add_argument("--baseline", default="v3.4", help="Baseline version for regression check (default: v3.4).")
    parser.add_argument("--threshold", type=float, default=10.0, help="Regression threshold in percent (default: 10.0%%).")
    parser.add_argument("--dir", default="perf_test/profiles", help="Directory containing JFR profiles.")

    args = parser.parse_args()

    profile_dir = args.dir
    if not os.path.exists(profile_dir):
        print(f"Directory {profile_dir} not found.")
        return

    version_data = defaultdict(list)

    files = [f for f in os.listdir(profile_dir) if f.endswith(".jfr")]
    if not files:
        print("No JFR data found.")
        return

    for filename in sorted(files):
        # Extract version and variant: profile_<version>[_<format>]_run<n>.jfr
        match = re.match(r"profile_((?:large_)?v[\d\.]+)(?:_(\w+))?_run\d+\.jfr", filename)
        if match:
            version = match.group(1)
            variant = match.group(2)
            full_version = version + (("_" + variant) if variant else "")
            filepath = os.path.join(profile_dir, filename)
            sys.stderr.write(f"Processing {filename}...\n")
            metrics = extract_metrics(filepath)
            version_data[full_version].append(metrics)

    if not version_data:
        print("No versioned JFR data found.")
        return

    sorted_versions = sorted(version_data.keys(), key=version_key)

    averages = {}

    print("# Performance Metrics Summary (Averages over runs)")
    print("\n| Version | FW (MB) | FW (ms) | FR (MB) | FR (ms) | Alloc (Main) | GC (ms) | Safepoints | Classes |")
    print("| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |")

    for version in sorted_versions:
        data = version_data[version]
        n = len(data)
        avg_fw_bytes = sum(d['fw_bytes'] for d in data) / n
        avg_fw_duration = sum(d['fw_duration'] for d in data) / n
        avg_fr_bytes = sum(d['fr_bytes'] for d in data) / n
        avg_fr_duration = sum(d['fr_duration'] for d in data) / n
        avg_alloc_main = sum(d['alloc_main'] for d in data) / n
        avg_gc_pauses = sum(d['gc_pauses'] for d in data) / n
        avg_sp_count = sum(d['sp_count'] for d in data) / n
        avg_cl_count = sum(d['class_load_count'] for d in data) / n

        averages[version] = {
            'alloc_main': avg_alloc_main,
            'gc_pauses': avg_gc_pauses
        }

        print(f"| {version} | {avg_fw_bytes/1024/1024:.2f} | {avg_fw_duration:.2f} | {avg_fr_bytes/1024/1024:.2f} | {avg_fr_duration:.2f} | {avg_alloc_main:.0f} | {avg_gc_pauses:.2f} | {avg_sp_count:.1f} | {avg_cl_count:.0f} |")

    if args.check:
        print("\n## Regression Check")
        baseline = args.baseline
        latest = sorted_versions[-1]

        if baseline not in averages:
            print(f"Error: Baseline version {baseline} not found in data.")
            return

        if latest == baseline:
            print("Latest version is the same as baseline. Skipping regression check.")
            return

        b_alloc = averages[baseline]['alloc_main']
        l_alloc = averages[latest]['alloc_main']

        b_gc = averages[baseline]['gc_pauses']
        l_gc = averages[latest]['gc_pauses']

        print(f"Comparing {latest} against {baseline} (Threshold: {args.threshold}%):")

        regressions = []

        # Allocation check
        if b_alloc > 0:
            diff_p = (l_alloc - b_alloc) / b_alloc * 100
            status = "OK" if diff_p <= args.threshold else "REGRESSION"
            print(f"- Thread Allocation (Main): {l_alloc:.0f} vs {b_alloc:.0f} ({diff_p:>+6.2f}%) -> {status}")
            if status == "REGRESSION":
                regressions.append("Thread Allocation")
        else:
            print(f"- Thread Allocation (Main): {l_alloc:.0f} vs 0 (N/A)")

        # GC Pause check
        if b_gc > 0:
            diff_p = (l_gc - b_gc) / b_gc * 100
            status = "OK" if diff_p <= args.threshold else "REGRESSION"
            print(f"- GC Pauses (ms): {l_gc:.2f} vs {b_gc:.2f} ({diff_p:>+6.2f}%) -> {status}")
            if status == "REGRESSION":
                regressions.append("GC Pauses")
        elif l_gc > 0:
             print(f"- GC Pauses (ms): {l_gc:.2f} vs 0.00 (INF%) -> REGRESSION")
             regressions.append("GC Pauses")
        else:
            print(f"- GC Pauses (ms): 0.00 vs 0.00 (0.00%) -> OK")

        if regressions:
            print(f"\nERROR: Performance regression(s) detected in: {', '.join(regressions)}")
            sys.exit(1)
        else:
            print("\nPerformance is within acceptable limits.")

if __name__ == "__main__":
    main()
