import sys

def fix_file(path):
    with open(path, "r") as f:
        lines = f.readlines()

    new_lines = []
    for line in lines:
        # Wrap long lines in PTXAttributesTest.java
        if "PTXAttributesTest.java" in path:
            if len(line) > 100 and "assertTrue" in line:
                parts = line.split('", "', 1)
                if len(parts) == 2:
                    line = parts[0] + '",\n                "' + parts[1]
            elif len(line) > 100:
                # Try to break at a logical place
                idx = line.rfind(" = ", 0, 100)
                if idx != -1:
                    line = line[:idx+3] + "\n" + " " * 16 + line[idx+3:]

        # In AfpJacksonXmlWriter, fix specific lines if they are too long
        if "AfpJacksonXmlWriter.java" in path:
             if len(line) > 100:
                  if "private void" in line:
                       line = line.replace(", ", ",\n      ")
                  elif "writeNameAndTripletsDirectly" in line or "writeTripletsAndTextDirectly" in line or "writeNameDirectly" in line:
                       line = line.replace(", ", ",\n          ")

        new_lines.append(line)

    with open(path, "w") as f:
        f.writelines(new_lines)

fix_file("src/main/java/com/mgz/xml/AfpJacksonXmlWriter.java")
fix_file("src/test/java/com/mgz/xml/PTXAttributesTest.java")
