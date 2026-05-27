import os
import sys
import re
from pypdf import PdfReader

def format_table(rows):
    if not rows:
        return ""
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

def is_likely_table_row(line, in_table):
    stripped = line.strip()
    if not stripped:
        return False

    parts = re.split(r'\s{3,}', stripped)

    if len(parts) < 2:
        if in_table and line.startswith(' ' * 8):
            return True
        return False

    first_part = parts[0].strip()
    # If it's a section header, it's not a table row
    # Adjust regex to catch "2   Normative references" with multiple spaces
    if re.match(r'^\d+(\.\d+)*\s{2,}[A-Z]', stripped):
        return False
    if re.match(r'^\d+(\.\d+)*\s+[A-Z]', stripped):
        return False

    # If it's just a number, but has other columns, it might be a table
    if re.match(r'^\d+(\.\d+)*$', first_part):
        if len(parts) >= 2: return True
        return False

    if re.match(r'^(EXAMPLE|NOTE|Table|Figure|Key|Sequence|FILTER name)\s*\d*', first_part, re.IGNORECASE):
        if "FILTER name" in first_part: return True
        if "Key" == first_part and "Type" in stripped: return True
        if "Dash Array" in first_part: return True
        return False

    if len(parts) >= 3:
        return True

    if len(parts) == 2:
        if len(stripped) > 35:
            return True
        if in_table:
            return True

    return False

def process_layout_page(text):
    lines = text.split('\n')
    cleaned_lines = []

    in_table = False
    table_rows = []

    watermark_p1 = re.compile(r"Sold by the PDF Association to Olivier Chatelain.*", re.IGNORECASE)
    watermark_p2 = re.compile(r"Single user only, copying and networking prohibited\.", re.IGNORECASE)
    header_footer_p1 = re.compile(r"ISO 32000-2:2020\(E\)")
    header_footer_p2 = re.compile(r"©\s*ISO\s*2020\s*[–-]\s*All rights reserved")
    page_num_p = re.compile(r"^\s*\d+\s*$")

    for line in lines:
        cleaned_line = watermark_p1.sub("", line)
        cleaned_line = watermark_p2.sub("", cleaned_line)
        cleaned_line = header_footer_p1.sub("", cleaned_line)
        cleaned_line = header_footer_p2.sub("", cleaned_line)

        if page_num_p.match(cleaned_line):
            cleaned_line = ""

        cleaned_line = re.sub(r"^\s*\d+\s{20,}", "", cleaned_line)
        cleaned_line = re.sub(r"\s{20,}\d+\s*$", "", cleaned_line)

        stripped = cleaned_line.strip()

        if is_likely_table_row(cleaned_line, in_table):
            if not in_table:
                in_table = True
                table_rows = []

            parts = re.split(r'\s{3,}', stripped)
            if len(parts) == 1 and in_table:
                if table_rows:
                    table_rows[-1][-1] = table_rows[-1][-1] + " " + stripped
            else:
                table_rows.append(parts)
            continue
        else:
            if in_table:
                table_md = format_table(table_rows)
                if table_md:
                    cleaned_lines.append(table_md)
                    cleaned_lines.append("")
                table_rows = []
                in_table = False

            if not stripped:
                if cleaned_lines and cleaned_lines[-1] != "":
                    cleaned_lines.append("")
                continue

            if cleaned_lines and cleaned_lines[-1] and cleaned_lines[-1].endswith('-'):
                if not re.match(r'^[0-9A-Z]', stripped):
                    cleaned_lines[-1] = cleaned_lines[-1][:-1] + stripped
                else:
                    cleaned_lines.append(stripped)
            else:
                # Catch headers and normalize them to single space for splitter
                header_match = re.match(r'^(\d+(\.\d+)*)\s{2,}([A-Z].*)', stripped)
                if header_match:
                    stripped = f"{header_match.group(1)} {header_match.group(3)}"

                if re.match(r'^\d+(\.\d+)*\s+[A-Z]', stripped):
                    if cleaned_lines and cleaned_lines[-1] != "":
                        cleaned_lines.append("")
                cleaned_lines.append(stripped)

    if in_table:
        table_md = format_table(table_rows)
        if table_md:
            cleaned_lines.append(table_md)

    return "\n".join(cleaned_lines)

def convert_pdf_to_md(pdf_path, md_path):
    print(f"Converting {pdf_path} to {md_path}...")
    reader = PdfReader(pdf_path)
    with open(md_path, "w", encoding="utf-8") as f:
        for i, page in enumerate(reader.pages):
            text = page.extract_text(extraction_mode="layout")
            f.write(f"## Page {i + 1}\n\n")
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
