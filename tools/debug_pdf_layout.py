from pypdf import PdfReader

reader = PdfReader("specifications/ISO_32000-2_sponsored-ec2.pdf")
page = reader.pages[16] # Page 17 is index 16
print(page.extract_text(extraction_mode="layout"))
