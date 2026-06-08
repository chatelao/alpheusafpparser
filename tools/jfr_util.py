import re
import collections
import subprocess
import sys

def parse_units(val_str):
    if not val_str:
        return 0
    m = re.match(r"([\d\.]+)\s*(\w+)?", val_str)
    if not m:
        return 0
    val, unit = m.groups()
    b = float(val)
    if unit == 'MB': b *= 1024 * 1024
    elif unit == 'kB': b *= 1024
    elif unit == 'GB': b *= 1024 * 1024 * 1024
    elif unit == 'B': b *= 1
    return int(b)

def parse_jfr_samples(jfr_file, event_type="jdk.ExecutionSample", value_field=None):
    cmd = ["jfr", "print", "--events", event_type, jfr_file]
    try:
        output = subprocess.check_output(cmd, text=True)
    except subprocess.CalledProcessError as e:
        print(f"Error running jfr for {jfr_file}: {e}", file=sys.stderr)
        return collections.Counter()

    stacks = collections.defaultdict(int)
    current_stack = []
    in_stack_trace = False
    current_value = 1 if value_field is None else 0

    for line in output.splitlines():
        line = line.strip()

        if value_field and f"{value_field} =" in line:
            m = re.search(rf"{re.escape(value_field)} = (.*)", line)
            if m:
                current_value = parse_units(m.group(1).strip())
            continue

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
                    stacks[";".join(current_stack)] += current_value
                # Reset for next event
                current_value = 1 if value_field is None else 0
                continue

            frame = line
            # Remove line number info
            frame = re.sub(r" line: \d+", "", frame)
            # Remove possible trailing comma
            frame = frame.strip(",")
            current_stack.append(frame)

    return stacks
