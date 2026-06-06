import subprocess
import sys
import collections
import re
import os

def parse_jfr_samples(jfr_file, event_type="jdk.ExecutionSample"):
    cmd = ["jfr", "print", "--events", event_type, jfr_file]
    try:
        output = subprocess.check_output(cmd, text=True)
    except subprocess.CalledProcessError as e:
        print(f"Error running jfr for {jfr_file}: {e}", file=sys.stderr)
        return collections.Counter()

    stacks = collections.Counter()
    current_stack = []
    in_stack_trace = False

    for line in output.splitlines():
        line = line.strip()
        if "stackTrace = [" in line:
            in_stack_trace = True
            current_stack = []
            continue

        if in_stack_trace:
            if line == "]":
                in_stack_trace = False
                if current_stack:
                    # JFR lists top to bottom. Collapsed wants bottom to top.
                    current_stack.reverse()
                    stacks[";".join(current_stack)] += 1
                continue

            frame = line
            # Remove line number info
            frame = re.sub(r" line: \d+", "", frame)
            # Remove possible trailing comma
            frame = frame.strip(",")
            current_stack.append(frame)

    return stacks

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 tools/aggregate_collapsed_stacks.py <profile_dir> <version_pattern> [event_type]")
        print("Example: python3 tools/aggregate_collapsed_stacks.py perf_test/profiles v15.6")
        sys.exit(1)

    profile_dir = sys.argv[1]
    version = sys.argv[2]
    event = "jdk.ExecutionSample"
    if len(sys.argv) > 3:
        event = sys.argv[3]

    if not os.path.isdir(profile_dir):
        print(f"Error: {profile_dir} is not a directory", file=sys.stderr)
        sys.exit(1)

    total_stacks = collections.Counter()
    file_pattern = re.compile(rf"profile_{re.escape(version)}_run\d+\.jfr")

    files_processed = 0
    for filename in sorted(os.listdir(profile_dir)):
        if file_pattern.match(filename):
            filepath = os.path.join(profile_dir, filename)
            print(f"Processing {filename}...", file=sys.stderr)
            stacks = parse_jfr_samples(filepath, event)
            total_stacks.update(stacks)
            files_processed += 1

    if files_processed == 0:
        print(f"No files found for version {version} in {profile_dir}", file=sys.stderr)
        sys.exit(1)

    for stack, count in total_stacks.items():
        print(f"{stack} {count}")

if __name__ == "__main__":
    main()
