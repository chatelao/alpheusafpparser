import os
import re

def split_md_file(filepath, output_base_dir):
    filename = os.path.basename(filepath)
    doc_name = os.path.splitext(filename)[0]
    doc_dir = os.path.join(output_base_dir, doc_name)

    if not os.path.exists(doc_dir):
        os.makedirs(doc_dir)

    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    sections = []
    current_section_name = "Front_Matter"
    current_section_content = []

    # Regex to identify Chapter or Appendix headers
    # Avoid matching TOC entries which usually have many dots
    chapter_regex = re.compile(r'^(Chapter\s+\d+|Appendix\s+[A-Z])(\.|\s|$)')

    for line in lines:
        # Heuristic: TOC lines usually have many dots
        if chapter_regex.match(line) and " . . . " not in line:
            # Save current section
            sections.append((current_section_name, current_section_content))

            # Start new section
            # Extract a clean name for the file
            # e.g., "Chapter 1. A Presentation Architecture Perspective" -> "Chapter_1"
            match = chapter_regex.match(line)
            header_type_num = match.group(1).replace(' ', '_')
            current_section_name = header_type_num
            current_section_content = [line]
        else:
            current_section_content.append(line)

    # Append the last section
    sections.append((current_section_name, current_section_content))

    # Write sections to files
    for name, content in sections:
        if not content:
            continue
        # Check if we have multiple sections with same name (unlikely but to be safe)
        output_filename = f"{name}.md"
        output_path = os.path.join(doc_dir, output_filename)

        # If file already exists, it might be due to multiple "Front_Matter" or similar
        # (Though with my logic only the first is Front_Matter)
        # Actually if there are multiple Chapter 1s (unlikely), we'd overwrite.
        # Let's handle duplicates just in case.
        counter = 1
        while os.path.exists(output_path):
            output_path = os.path.join(doc_dir, f"{name}_{counter}.md")
            counter += 1

        with open(output_path, 'w', encoding='utf-8') as f:
            f.writelines(content)

    print(f"Split {filename} into {len(sections)} sections in {doc_dir}")

def main():
    md_dir = "specifications/markdown"

    for filename in os.listdir(md_dir):
        if filename.endswith(".md") and os.path.isfile(os.path.join(md_dir, filename)):
            filepath = os.path.join(md_dir, filename)
            split_md_file(filepath, md_dir)

if __name__ == "__main__":
    main()
