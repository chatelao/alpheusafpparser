import os
import re

def split_iso_file(filepath, output_base_dir):
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

    CHAPTER_TITLES = [
        "Scope",
        "Normative references",
        "Terms and definitions",
        "Notation",
        "Version designations",
        "Conformance",
        "Syntax",
        "Graphics",
        "Text",
        "Rendering",
        "Transparency",
        "Interactive features",
        "Multimedia features",
        "Document interchange"
    ]

    next_expected_chapter = 1
    # Chapters usually start with "# N Title"
    chapter_regex = re.compile(r'^#\s+(\d+)\s+(.*)')
    # Annexes usually start with "Annex A"
    annex_regex = re.compile(r'^Annex[\s\u00a0]+([A-Q])[\s\u00a0\(]')
    next_expected_annex_idx = 0
    ANNEX_LETTERS = "ABCDEFGHIJKLMNOPQ"

    found_bib = False

    for i, line in enumerate(lines):
        stripped_line = line.strip()

        # Heuristic to avoid TOC and other noise
        is_toc = "...." in stripped_line or re.search(r"\.\.\s+\d+$", stripped_line)

        is_clause = False
        if not is_toc and next_expected_chapter <= len(CHAPTER_TITLES):
            match = chapter_regex.match(stripped_line)
            if match:
                num = int(match.group(1))
                title = match.group(2).strip()
                if num == next_expected_chapter and title == CHAPTER_TITLES[next_expected_chapter-1]:
                    is_clause = True
                    next_expected_chapter += 1

        is_annex = False
        if not is_toc and not is_clause and next_expected_annex_idx < len(ANNEX_LETTERS):
            match = annex_regex.match(stripped_line)
            if match and match.group(1) == ANNEX_LETTERS[next_expected_annex_idx]:
                is_annex = True
                next_expected_annex_idx += 1

        is_bib = False
        if not is_toc and not is_clause and not is_annex and not found_bib:
            if stripped_line == "Bibliography":
                is_bib = True
                found_bib = True

        if is_clause or is_annex or is_bib:
            # Save current section
            sections.append((current_section_name, current_section_content))

            # Start new section
            if is_clause:
                current_section_name = f"Chapter_{next_expected_chapter - 1}"
            elif is_annex:
                current_section_name = f"Appendix_{ANNEX_LETTERS[next_expected_annex_idx - 1]}"
            elif is_bib:
                current_section_name = "Bibliography"

            current_section_content = [line]
        else:
            current_section_content.append(line)

    # Append the last section
    sections.append((current_section_name, current_section_content))

    # Clean up old files in doc_dir
    for old_file in os.listdir(doc_dir):
        if old_file.endswith(".md"):
            os.remove(os.path.join(doc_dir, old_file))

    # Write sections to files
    for name, content in sections:
        if not content:
            continue

        output_filename = f"{name}.md"
        output_path = os.path.join(doc_dir, output_filename)

        # In case of any weirdness, don't overwrite if it already exists (though with strict order it shouldn't)
        counter = 1
        final_path = output_path
        while os.path.exists(final_path):
            final_path = os.path.join(doc_dir, f"{name}_{counter}.md")
            counter += 1

        with open(final_path, 'w', encoding='utf-8') as f:
            f.writelines(content)

    print(f"Split {filename} into {len(sections)} sections in {doc_dir}")

def main():
    # Make sure we have the source file
    filepath = "specifications/markdown/ISO_32000-2_sponsored-ec2.md"
    if not os.path.exists(filepath):
        # Regenerate if missing
        import subprocess
        subprocess.run(["python3", "tools/pdf_to_md.py", "ISO_32000-2_sponsored-ec2.pdf"])

    if os.path.exists(filepath):
        split_iso_file(filepath, "specifications/markdown")
    else:
        print(f"File not found: {filepath}")

if __name__ == "__main__":
    main()
