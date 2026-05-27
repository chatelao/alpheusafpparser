from pypdf import PdfReader
import re

reader = PdfReader("specifications/ISO_32000-2_sponsored-ec2.pdf")
# Page 36 of PDF is roughly where 7.2.1 is. Chapter 7 starts around page 34?
# Let's check page 34-40
for i in range(33, 45):
    print(f"--- Page {i+1} ---")
    text = reader.pages[i].extract_text(extraction_mode="layout")
    print(text)
