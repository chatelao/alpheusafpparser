import sys
import collections
import re
import os
import argparse
from jfr_util import parse_jfr_samples

def main():
    parser = argparse.ArgumentParser(description="Aggregate JFR events across multiple files into collapsed stack format.")
    parser.add_argument("profile_dir", help="Directory containing JFR profiles.")
    parser.add_argument("version_pattern", help="Version pattern to match in filenames (e.g., 'v15.6').")
    parser.add_argument("event_type", nargs="?", default="jdk.ExecutionSample", help="JFR event type to extract (default: jdk.ExecutionSample).")
    parser.add_argument("--value-field", help="Field to sum instead of counting events (e.g., 'weight' for ObjectAllocationSample).")

    args = parser.parse_args()

    if not os.path.isdir(args.profile_dir):
        print(f"Error: {args.profile_dir} is not a directory", file=sys.stderr)
        sys.exit(1)

    total_stacks = collections.Counter()
    file_pattern = re.compile(rf"profile_{re.escape(args.version_pattern)}_run\d+\.jfr")

    files_processed = 0
    for filename in sorted(os.listdir(args.profile_dir)):
        if file_pattern.match(filename):
            filepath = os.path.join(args.profile_dir, filename)
            sys.stderr.write(f"Processing {filename}...\n")
            stacks = parse_jfr_samples(filepath, args.event_type, args.value_field)
            total_stacks.update(stacks)
            files_processed += 1

    if files_processed == 0:
        print(f"No files found for version {args.version_pattern} in {args.profile_dir}", file=sys.stderr)
        sys.exit(1)

    for stack, count in total_stacks.items():
        try:
            print(f"{stack} {count}")
        except BrokenPipeError:
            break

if __name__ == "__main__":
    main()
