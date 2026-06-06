import subprocess
import sys
import collections
import re

def parse_jfr_samples(jfr_file, event_type="jdk.ExecutionSample"):
    cmd = ["jfr", "print", "--events", event_type, jfr_file]
    try:
        output = subprocess.check_output(cmd, text=True)
    except subprocess.CalledProcessError as e:
        print(f"Error running jfr: {e}", file=sys.stderr)
        return

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

            # Clean up frame description: "com.mgz.Parser.parse() line: 123" -> "com.mgz.Parser.parse"
            # Or just keep it as is but remove " line: ..." for cleaner graphs?
            # Standard collapsed often just uses class.method
            frame = line
            # Remove line number info
            frame = re.sub(r" line: \d+", "", frame)
            # Remove possible trailing comma (though JFR print doesn't seem to have them in this format)
            frame = frame.strip(",")
            current_stack.append(frame)

    for stack, count in stacks.items():
        print(f"{stack} {count}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python3 tools/jfr_to_collapsed.py <file.jfr> [event_type]")
        print("Default event_type: jdk.ExecutionSample")
        sys.exit(1)

    event = "jdk.ExecutionSample"
    if len(sys.argv) > 2:
        event = sys.argv[2]

    parse_jfr_samples(sys.argv[1], event)
