import subprocess
import os
import re
import sys
from collections import defaultdict

def extract_metrics(jfr_file):
    metrics = {
        'fw_bytes': 0,
        'fw_count': 0,
        'alloc_main': 0,
        'tlab_bytes': 0,
        'tlab_count': 0,
        'gc_pauses': 0,
        'gc_count': 0,
        'sp_count': 0
    }

    # FileWrite
    try:
        fw_output = subprocess.check_output(["jfr", "print", "--events", "jdk.FileWrite", jfr_file], text=True)
        metrics['fw_bytes'] = sum(int(m.group(1)) for m in re.finditer(r"bytesWritten = (\d+)", fw_output))
        metrics['fw_count'] = len(re.findall(r"jdk.FileWrite", fw_output))
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

    return metrics

def main():
    profile_dir = "perf_test/profiles"
    if not os.path.exists(profile_dir):
        print(f"Directory {profile_dir} not found.")
        return

    version_data = defaultdict(list)

    files = [f for f in os.listdir(profile_dir) if f.endswith(".jfr")]
    if not files:
        print("No JFR data found.")
        return

    for filename in sorted(files):
        # Extract version: profile_<version> run<n>.jfr
        match = re.match(r"profile_(v[\d\.]+)_run\d+\.jfr", filename)
        if match:
            version = match.group(1)
            filepath = os.path.join(profile_dir, filename)
            sys.stderr.write(f"Processing {filename}...\n")
            metrics = extract_metrics(filepath)
            version_data[version].append(metrics)

    if not version_data:
        print("No versioned JFR data found.")
        return

    print("# Performance Metrics Summary (Averages over runs)")
    print("\n| Version | FileWrite (Events) | FileWrite (Bytes) | Thread Allocation (Main) | TLAB Allocation (Events) | GC Pauses (ms) | GC Count | Safepoints |")
    print("| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |")

    def version_key(v):
        return [int(x) for x in v[1:].split('.')]

    for version in sorted(version_data.keys(), key=version_key):
        data = version_data[version]
        n = len(data)
        avg_fw_count = sum(d['fw_count'] for d in data) / n
        avg_fw_bytes = sum(d['fw_bytes'] for d in data) / n
        avg_alloc_main = sum(d['alloc_main'] for d in data) / n
        avg_tlab_count = sum(d['tlab_count'] for d in data) / n
        avg_gc_pauses = sum(d['gc_pauses'] for d in data) / n
        avg_gc_count = sum(d['gc_count'] for d in data) / n
        avg_sp_count = sum(d['sp_count'] for d in data) / n

        print(f"| {version} | {avg_fw_count:.1f} | {avg_fw_bytes:.0f} | {avg_alloc_main:.0f} | {avg_tlab_count:.1f} | {avg_gc_pauses:.2f} | {avg_gc_count:.1f} | {avg_sp_count:.1f} |")

if __name__ == "__main__":
    main()
