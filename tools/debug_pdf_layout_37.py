from pypdf import PdfReader

reader = PdfReader("specifications/ISO_32000-2_sponsored-ec2.pdf")
page = reader.pages[36] # Page 37 is index 36
print(page.extract_text(extraction_mode="layout"))
