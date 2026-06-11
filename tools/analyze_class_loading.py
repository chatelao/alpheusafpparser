import sys
import argparse
import collections
from jfr_util import parse_jfr_class_loads

def main():
    parser = argparse.ArgumentParser(description="Analyze class-loading hotspots from JFR files.")
    parser.add_argument("jfr_files", nargs="+", help="JFR files to analyze.")
    parser.add_argument("--top", type=int, default=20, help="Number of top classes to show.")

    args = parser.parse_args()

    class_durations = collections.defaultdict(float)
    class_counts = collections.defaultdict(int)
    total_duration = 0.0
    total_count = 0

    for jfr_file in args.jfr_files:
        loads = parse_jfr_class_loads(jfr_file)
        for load in loads:
            cls = load["loadedClass"]
            duration = load.get("duration", 0.0)
            class_durations[cls] += duration
            class_counts[cls] += 1
            total_duration += duration
            total_count += 1

    if total_count == 0:
        print("No jdk.ClassLoad events found.")
        return

    print(f"Class-Loading Analysis (Total: {total_count} classes, {total_duration:.2f}ms)")
    print("-" * 70)
    print(f"{'Loaded Class':<50} | {'Count':<5} | {'Duration':<10}")
    print("-" * 70)

    # Sort by duration descending
    sorted_classes = sorted(class_durations.items(), key=lambda x: x[1], reverse=True)

    for cls, duration in sorted_classes[:args.top]:
        count = class_counts[cls]
        print(f"{cls[:50]:<50} | {count:<5} | {duration:>8.2f}ms")

if __name__ == "__main__":
    main()
