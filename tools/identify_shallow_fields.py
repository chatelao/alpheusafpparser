import os
import re

def identify_shallow_fields(root_dir):
    shallow_fields = {}

    # regex to find class definition and base classes
    class_regex = re.compile(r'public class (\w+) extends (\w+)')
    # regex to find decodeAFP method
    decode_regex = re.compile(r'public void decodeAFP\s*\(')

    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.java'):
                filepath = os.path.join(root, file)
                with open(filepath, 'r') as f:
                    content = f.read()

                    # Skip non-StructuredField classes
                    if 'extends StructuredField' not in content:
                        continue

                    match = class_regex.search(content)
                    if match:
                        classname = match.group(1)
                        # Check if it overrides decodeAFP
                        if not decode_regex.search(content):
                            package = os.path.relpath(root, root_dir).replace(os.sep, '.')
                            if package not in shallow_fields:
                                shallow_fields[package] = []
                            shallow_fields[package].append(classname)
                        else:
                            # Also check for minimal/empty implementations (e.g., GAD_GraphicsData)
                            # This is a bit more complex, but we can look for specific patterns
                            # like empty bodies or just calling super.
                            decode_body_match = re.search(r'public void decodeAFP\s*\([^)]*\)\s*throws\s*AFPParserException\s*\{([\s\S]*?)\}', content)
                            if decode_body_match:
                                body = decode_body_match.group(1).strip()
                                if not body or body == 'super.decodeAFP(sfData, offset, length, config);' or body.startswith('//'):
                                     package = os.path.relpath(root, root_dir).replace(os.sep, '.')
                                     if package not in shallow_fields:
                                         shallow_fields[package] = []
                                     shallow_fields[package].append(classname + " (Minimal/Empty override)")

    return shallow_fields

def generate_report(shallow_fields, output_file):
    with open(output_file, 'w') as f:
        f.write("# Shallow Structured Field Implementations Report\n\n")
        f.write("This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).\n\n")

        total_count = sum(len(fields) for fields in shallow_fields.values())
        f.write(f"**Total Shallow Fields Identified: {total_count}**\n\n")

        for package in sorted(shallow_fields.keys()):
            f.write(f"## Package: {package}\n")
            for field in sorted(shallow_fields[package]):
                f.write(f"- {field}\n")
            f.write("\n")

if __name__ == "__main__":
    src_root = "src/main/java/com/mgz/afp"
    report_file = "SHALLOW_FIELDS_REPORT.md"
    fields = identify_shallow_fields(src_root)
    generate_report(fields, report_file)
    print(f"Report generated: {report_file}")
