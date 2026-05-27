
import xml.etree.ElementTree as ET

tree = ET.parse('build/reports/jacoco/test/jacocoTestReport.xml')
root = tree.getroot()

classes = []

for package in root.findall('package'):
    package_name = package.get('name').replace('/', '.')
    for cls in package.findall('class'):
        class_name = cls.get('name').replace('/', '.')
        instr = cls.find("counter[@type='INSTRUCTION']")
        if instr is not None:
            missed = int(instr.get('missed'))
            covered = int(instr.get('covered'))
            total = missed + covered
            classes.append({
                'name': class_name,
                'missed': missed,
                'covered': covered,
                'total': total,
                'percentage': (covered / total * 100) if total > 0 else 0
            })

# Sort by missed instructions descending
classes.sort(key=lambda x: x['missed'], reverse=True)

print(f"{'Class':<60} | {'Missed':<10} | {'Covered':<10} | {'Total':<10} | {'%':<10}")
print("-" * 105)
for cls in classes[:30]:
    print(f"{cls['name']:<60} | {cls['missed']:<10} | {cls['covered']:<10} | {cls['total']:<10} | {cls['percentage']:>6.2f}%")
