import os
import sys
from pypdf import PdfReader

def convert_pdf_to_md(pdf_path, md_path):
    print(f"Converting {pdf_path} to {md_path}...")
    reader = PdfReader(pdf_path)
    with open(md_path, "w", encoding="utf-8") as f:
        for i, page in enumerate(reader.pages):
            text = page.extract_text()
            f.write(f"## Page {i + 1}\n\n")
            f.write(text)
            f.write("\n\n")

def main():
    pdf_dir = "specifications"
    md_dir = os.path.join(pdf_dir, "markdown")

    if not os.path.exists(md_dir):
        os.makedirs(md_dir)

    if len(sys.argv) > 1:
        # Process specific file(s)
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
        # Original behavior: process all
        for filename in os.listdir(pdf_dir):
            if filename.endswith(".pdf"):
                pdf_path = os.path.join(pdf_dir, filename)
                md_filename = filename.replace(".pdf", ".md")
                md_path = os.path.join(md_dir, md_filename)
                convert_pdf_to_md(pdf_path, md_path)

if __name__ == "__main__":
    main()
