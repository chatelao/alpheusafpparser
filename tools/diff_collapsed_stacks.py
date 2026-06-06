import sys
import collections

def read_collapsed(filepath):
    """
    Reads a collapsed stack file (format: 'stack count').
    Returns a dictionary mapping stack strings to integer counts.
    """
    stacks = collections.defaultdict(int)
    try:
        with open(filepath, 'r') as f:
            for line in f:
                line = line.strip()
                if not line:
                    continue
                parts = line.rsplit(' ', 1)
                if len(parts) == 2:
                    stack, count = parts
                    try:
                        stacks[stack] += int(count)
                    except ValueError:
                        continue
    except FileNotFoundError:
        print(f"Warning: File {filepath} not found.", file=sys.stderr)
    return stacks

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 tools/diff_collapsed_stacks.py <file1.txt> <file2.txt>")
        print("Outputs: stack count1 count2")
        sys.exit(1)

    stacks1 = read_collapsed(sys.argv[1])
    stacks2 = read_collapsed(sys.argv[2])

    all_stacks = set(stacks1.keys()) | set(stacks2.keys())

    for stack in sorted(all_stacks):
        count1 = stacks1.get(stack, 0)
        count2 = stacks2.get(stack, 0)
        # Output format compatible with differential flame graph scripts
        print(f"{stack} {count1} {count2}")

if __name__ == "__main__":
    main()
