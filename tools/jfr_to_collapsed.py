import sys
import argparse
from jfr_util import parse_jfr_samples

def main():
    parser = argparse.ArgumentParser(description="Convert JFR events to collapsed stack format.")
    parser.add_argument("jfr_file", help="Path to the JFR file.")
    parser.add_argument("event_type", nargs="?", default="jdk.ExecutionSample", help="JFR event type to extract (default: jdk.ExecutionSample).")
    parser.add_argument("--value-field", help="Field to sum instead of counting events (e.g., 'weight' for ObjectAllocationSample).")

    args = parser.parse_args()

    stacks = parse_jfr_samples(args.jfr_file, args.event_type, args.value_field)

    for stack, count in stacks.items():
        try:
            print(f"{stack} {count}")
        except BrokenPipeError:
            break

if __name__ == "__main__":
    main()
