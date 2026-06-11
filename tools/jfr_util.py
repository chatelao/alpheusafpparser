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

def parse_duration_ms(duration_str):
    if not duration_str:
        return 0.0
    m = re.match(r"([\d\.]+)\s*(\w+)?", duration_str)
    if not m:
        return 0.0
    val, unit = m.groups()
    v = float(val)
    if unit == 's': v *= 1000.0
    elif unit == 'ms': v *= 1.0
    elif unit == 'us' or unit == 'µs': v /= 1000.0
    elif unit == 'ns': v /= 1000000.0
    return v

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

def parse_jfr_class_loads(jfr_file):
    cmd = ["jfr", "print", "--events", "jdk.ClassLoad", jfr_file]
    try:
        output = subprocess.check_output(cmd, text=True)
    except subprocess.CalledProcessError as e:
        print(f"Error running jfr for {jfr_file}: {e}", file=sys.stderr)
        return []

    class_loads = []
    current_event = {}

    for line in output.splitlines():
        line = line.strip()

        if line.startswith("jdk.ClassLoad {"):
            current_event = {}
            continue

        if line == "}":
            if "loadedClass" in current_event:
                class_loads.append(current_event)
            continue

        if " = " in line:
            parts = line.split(" = ", 1)
            key = parts[0].strip()
            val = parts[1].strip().rstrip(",")
            if key == "loadedClass":
                # Handle cases like "loadedClass = java.lang.Object (classLoader = bootstrap)"
                m = re.match(r"([^\s\(]+)", val)
                if m:
                    current_event["loadedClass"] = m.group(1)
            elif key == "duration":
                current_event["duration"] = parse_duration_ms(val)

    return class_loads
