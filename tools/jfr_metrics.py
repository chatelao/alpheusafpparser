import subprocess
import sys
import re

def get_jfr_metrics(jfr_file):
    print(f"Analyzing {jfr_file}...")

    # Extract FileWrite metrics
    try:
        fw_output = subprocess.check_output(["jfr", "print", "--events", "jdk.FileWrite", jfr_file], text=True)
        fw_bytes = sum(int(m.group(1)) for m in re.finditer(r"bytesWritten = (\d+)", fw_output))
        fw_duration = sum(float(m.group(1)) for m in re.finditer(r"duration = ([\d\.]+) ms", fw_output))
        fw_count = len(re.findall(r"jdk.FileWrite", fw_output))
        print(f"  jdk.FileWrite: {fw_count} events, {fw_bytes} bytes total, {fw_duration:.2f} ms total duration")
    except Exception as e:
        print(f"  Error extracting jdk.FileWrite: {e}")

    # Extract FileRead metrics
    try:
        fr_output = subprocess.check_output(["jfr", "print", "--events", "jdk.FileRead", jfr_file], text=True)
        fr_bytes = sum(int(m.group(1)) for m in re.finditer(r"bytesRead = (\d+)", fr_output))
        fr_duration = sum(float(m.group(1)) for m in re.finditer(r"duration = ([\d\.]+) ms", fr_output))
        fr_count = len(re.findall(r"jdk.FileRead", fr_output))
        print(f"  jdk.FileRead: {fr_count} events, {fr_bytes} bytes total, {fr_duration:.2f} ms total duration")
    except Exception as e:
        print(f"  Error extracting jdk.FileRead: {e}")

    # Extract Allocation metrics
    try:
        alloc_output = subprocess.check_output(["jfr", "print", "--events", "jdk.ThreadAllocationStatistics,jdk.ObjectAllocationInNewTLAB", jfr_file], text=True)

        # Split by events
        events = re.split(r"jdk\.[a-zA-Z]+ \{", alloc_output)

        max_main_bytes = 0
        tlab_bytes = 0
        tlab_count = 0

        for event in events:
            if 'thread = "main"' in event and 'allocated =' in event:
                m = re.search(r"allocated = ([\d\.]+) (\w+)", event)
                if m:
                    val, unit = m.groups()
                    b = float(val)
                    if unit == 'MB': b *= 1024 * 1024
                    elif unit == 'KB': b *= 1024
                    elif unit == 'GB': b *= 1024 * 1024 * 1024
                    if b > max_main_bytes: max_main_bytes = b

            if 'tlabSize =' in event:
                m = re.search(r"tlabSize = (\d+)", event)
                if m:
                    tlab_bytes += int(m.group(1))
                    tlab_count += 1

        print(f"  jdk.ThreadAllocationStatistics (main): {int(max_main_bytes)} bytes approx")
        if tlab_count > 0:
            print(f"  jdk.ObjectAllocationInNewTLAB: {tlab_count} events, {tlab_bytes} bytes total")

    except Exception as e:
        print(f"  Error extracting Allocation metrics: {e}")

    # Extract GC metrics
    try:
        gc_output = subprocess.check_output(["jfr", "print", "--events", "jdk.GarbageCollection", jfr_file], text=True)
        gc_pauses = sum(float(m.group(1)) for m in re.finditer(r"sumOfPauses = ([\d\.]+) ms", gc_output))
        gc_count = len(re.findall(r"jdk.GarbageCollection", gc_output))
        print(f"  jdk.GarbageCollection: {gc_count} events, {gc_pauses:.2f} ms total pauses")
    except Exception as e:
        print(f"  Error extracting GC metrics: {e}")

    # Extract Safepoint metrics
    try:
        sp_output = subprocess.check_output(["jfr", "print", "--events", "jdk.SafepointBegin", jfr_file], text=True)
        sp_count = len(re.findall(r"jdk.SafepointBegin", sp_output))
        print(f"  jdk.SafepointBegin: {sp_count} events")
    except Exception as e:
        print(f"  Error extracting Safepoint metrics: {e}")

    # Extract Class Loading metrics
    try:
        cl_output = subprocess.check_output(["jfr", "print", "--events", "jdk.ClassLoad", jfr_file], text=True)
        cl_count = len(re.findall(r"jdk.ClassLoad", cl_output))
        print(f"  jdk.ClassLoad: {cl_count} events")
    except Exception as e:
        print(f"  Error extracting jdk.ClassLoad: {e}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python3 tools/jfr_metrics.py <file.jfr>")
        sys.exit(1)
    get_jfr_metrics(sys.argv[1])
