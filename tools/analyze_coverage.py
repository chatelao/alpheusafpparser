
import xml.etree.ElementTree as ET

tree = ET.parse('build/reports/jacoco/test/jacocoTestReport.xml')
root = tree.getroot()

print(f"{'Package':<40} | {'Instruction Coverage':<20}")
print("-" * 63)

for package in root.findall('package'):
    name = package.get('name').replace('/', '.')
    instr = package.find("counter[@type='INSTRUCTION']")
    if instr is not None:
        missed = int(instr.get('missed'))
        covered = int(instr.get('covered'))
        total = missed + covered
        percentage = (covered / total * 100) if total > 0 else 0
        print(f"{name:<40} | {percentage:>6.2f}% ({covered}/{total})")

summary_instr = root.find("counter[@type='INSTRUCTION']")
missed = int(summary_instr.get('missed'))
covered = int(summary_instr.get('covered'))
total = missed + covered
percentage = (covered / total * 100)
print("-" * 63)
print(f"{'TOTAL':<40} | {percentage:>6.2f}% ({covered}/{total})")
