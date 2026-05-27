from pypdf import PdfReader

reader = PdfReader("specifications/ISO_32000-2_sponsored-ec2.pdf")
page = reader.pages[15] # Page 16 is likely where Chapter 1 starts (Page 17 in PDF numbering)
print(f"--- Page {16} ---")
print(reader.pages[15].extract_text(extraction_mode="layout"))
print(f"--- Page {17} ---")
print(reader.pages[16].extract_text(extraction_mode="layout"))
