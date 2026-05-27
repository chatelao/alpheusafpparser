import os
import sys
import re
from pypdf import PdfReader

def format_table(rows):
    if not rows:
        return ""
    # Filter out empty rows
    rows = [r for r in rows if any(cell.strip() for cell in r)]
    if not rows:
        return ""

    num_cols = max(len(r) for r in rows)
    if num_cols < 2:
        return "\n".join([" ".join(r) for r in rows])

    md_rows = []
    for i, row in enumerate(rows):
        row_cells = [cell.strip() for cell in row]
        row_cells += [""] * (num_cols - len(row_cells))
        md_rows.append("| " + " | ".join(row_cells) + " |")
        if i == 0 and len(rows) > 1:
            md_rows.append("| " + " | ".join(["---"] * num_cols) + " |")

    return "\n".join(md_rows)

def is_likely_table_row(line, in_table=False):
    stripped = line.strip()
    if not stripped:
        return False

    # Exclude bullets and list markers
    if re.match(r'^\s*[•\-\*]\s+', line):
        return False
    if re.match(r'^\s*[a-z]\)\s+', line):
        return False
    # Exclude TOC lines
    if re.search(r'\.{5,}', stripped):
        return False
    # Exclude section headers
    if re.match(r'^([A-Q\d]+(\.\d+)*)\s{2,}([A-Z].*)', stripped):
        return False

    # Exclude lines starting with NOTE, EXAMPLE, Figure, Table (unless it's a known header)
    if re.match(r'^(NOTE|EXAMPLE|Figure|Table|Clause|Annex)\b', stripped, re.IGNORECASE):
        return False

    parts = re.split(r'\s{3,}', stripped)
    if len(parts) >= 2:
        return True

    # Indented lines might be continuations of the last cell
    if in_table and line.startswith(' ' * 8) and len(stripped) > 0:
        return True

    return False

def process_layout_page(text):
    lines = text.split('\n')
    cleaned_lines = []

    table_buffer = []

    # Artifact patterns
    watermark_p1 = re.compile(r"Sold by the PDF Association to Olivier Chatelain.*", re.IGNORECASE)
    watermark_p2 = re.compile(r"Single user only, copying and networking prohibited\.", re.IGNORECASE)
    header_footer_p1 = re.compile(r"ISO 32000-2:2020\(E\)")
    header_footer_p2 = re.compile(r"©\s*ISO\s*2020\s*[–-]\s*All rights reserved")
    combined_footer = re.compile(r"^\s*\d+\s+©\s*ISO\s*2020.*", re.IGNORECASE)
    page_num_p = re.compile(r"^\s*\d+\s*$")
    standard_header = re.compile(r"^INTERNATIONAL STANDARD.*", re.IGNORECASE)

    def add_regular_line(stripped, lines_list):
        if not stripped:
            if lines_list and lines_list[-1] != "":
                lines_list.append("")
            return

        # Paragraph reconstruction
        if (lines_list and lines_list[-1] != "" and
            not lines_list[-1].startswith('#') and
            not lines_list[-1].startswith('|') and
            not re.match(r'^\s*[•\-\*a-z]\)\s+', lines_list[-1]) and
            not re.match(r'^\s*[•\-\*a-z]\)\s+', stripped)):

            if (re.match(r'^[a-z]', stripped) or
                not re.search(r'[.!?]$', lines_list[-1]) or
                re.search(r'[,;:]$', lines_list[-1])):

                if lines_list[-1].endswith('-'):
                    lines_list[-1] = lines_list[-1][:-1] + stripped
                else:
                    lines_list[-1] = (lines_list[-1] + " " + stripped).strip()
                return

        # Header detection
        is_toc = re.search(r'\.{3,}\s*\d+$', stripped)
        # Require 2+ spaces after the number/letter for headers
        # Use more inclusive regex for the first word in the title
        header_match = re.match(r'^([A-Q\d]+(\.\d+)*)\s{2,}([A-Z\(].*)', stripped)
        if header_match and not is_toc:
            # Collapse multiple spaces in title only
            num = header_match.group(1)
            title = re.sub(r'\s{2,}', ' ', header_match.group(3).strip())
            level = num.count('.') + 1
            if "Annex" in stripped: level = 1
            level = min(max(level, 1), 6)
            header_text = f"{'#' * level} {num} {title}"
            if lines_list and lines_list[-1] != "":
                lines_list.append("")
            lines_list.append(header_text)
            lines_list.append("")
            return

        # Collapse multiple spaces for all other regular text
        stripped = re.sub(r'\s{2,}', ' ', stripped)

        # NOTE/EXAMPLE blocks
        note_match = re.match(r'^((?:NOTE|EXAMPLE)\s*\d*)\s+(.*)', stripped, re.IGNORECASE)
        if note_match:
            prefix = note_match.group(1).upper().strip()
            rest = note_match.group(2).strip()
            note_text = f"> **{prefix}** {rest}"
            if lines_list and lines_list[-1] != "":
                lines_list.append("")
            lines_list.append(note_text)
            return

        lines_list.append(stripped)
        if stripped.startswith('#') or stripped.startswith('|') or stripped.startswith('>'):
            lines_list.append("")

    def flush_table(buffer, lines_list):
        if not buffer:
            return

        # Check if we have enough "real" table rows (non-empty, multiple parts)
        real_rows = [r for r in buffer if len(r) >= 2]

        if len(real_rows) >= 2:
            table_md = format_table(buffer)
            if table_md:
                lines_list.append(table_md)
                lines_list.append("")
        else:
            for row in buffer:
                add_regular_line(" ".join(row), lines_list)
        buffer.clear()

    for line in lines:
        cleaned_line = watermark_p1.sub("", line)
        cleaned_line = watermark_p2.sub("", cleaned_line)
        cleaned_line = header_footer_p1.sub("", cleaned_line)
        cleaned_line = header_footer_p2.sub("", cleaned_line)
        cleaned_line = combined_footer.sub("", cleaned_line)
        cleaned_line = standard_header.sub("", cleaned_line)

        if page_num_p.match(cleaned_line):
            cleaned_line = ""

        cleaned_line = re.sub(r"^\s*\d+\s{20,}", "", cleaned_line)
        cleaned_line = re.sub(r"\s{20,}\d+\s*$", "", cleaned_line)

        stripped = cleaned_line.strip()

        if is_likely_table_row(cleaned_line, in_table=bool(table_buffer)):
            row_parts = re.split(r'\s{3,}', stripped)
            if len(row_parts) == 1 and table_buffer:
                table_buffer[-1][-1] = table_buffer[-1][-1] + " " + stripped
            else:
                table_buffer.append(row_parts)
        elif not stripped and table_buffer:
            # Keep table open for one empty line
            if len(table_buffer) > 0 and table_buffer[-1] != [""]:
                table_buffer.append([""])
            else:
                flush_table(table_buffer, cleaned_lines)
                add_regular_line(stripped, cleaned_lines)
        else:
            flush_table(table_buffer, cleaned_lines)
            add_regular_line(stripped, cleaned_lines)

    flush_table(table_buffer, cleaned_lines)
    return "\n".join(cleaned_lines)

def convert_pdf_to_md(pdf_path, md_path):
    print(f"Converting {pdf_path} to {md_path}...")
    reader = PdfReader(pdf_path)
    with open(md_path, "w", encoding="utf-8") as f:
        for i, page in enumerate(reader.pages):
            text = page.extract_text(extraction_mode="layout")
            processed_text = process_layout_page(text)
            f.write(processed_text)
            f.write("\n\n")

def main():
    pdf_dir = "specifications"
    md_dir = os.path.join(pdf_dir, "markdown")

    if not os.path.exists(md_dir):
        os.makedirs(md_dir)

    if len(sys.argv) > 1:
        for arg in sys.argv[1:]:
            if os.path.isfile(arg):
                pdf_path = arg
                filename = os.path.basename(arg)
            else:
                pdf_path = os.path.join(pdf_dir, arg)
                filename = arg

            if not os.path.isfile(pdf_path):
                print(f"File not found: {pdf_path}")
                continue

            md_filename = filename.replace(".pdf", ".md")
            md_path = os.path.join(md_dir, md_filename)
            convert_pdf_to_md(pdf_path, md_path)
    else:
        for filename in os.listdir(pdf_dir):
            if filename.endswith(".pdf"):
                pdf_path = os.path.join(pdf_dir, filename)
                md_filename = filename.replace(".pdf", ".md")
                md_path = os.path.join(md_dir, md_filename)
                convert_pdf_to_md(pdf_path, md_path)

if __name__ == "__main__":
    main()
