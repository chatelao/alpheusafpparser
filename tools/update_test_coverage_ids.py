import re
import sys

def get_id(spec, filename):
    spec_map = {
        'afp-goca-reference-03': 'GOCA',
        'bcoca-reference-11': 'BCOCA',
        'cmoca-reference-02': 'CMOCA',
        'db2z_12_charbook': 'DB2Z',
        'foca-reference-06': 'FOCA',
        'ioca-reference-09': 'IOCA',
        'ipds-reference-12': 'IPDS',
        'linedata-reference-05': 'LINEDATA',
        'moca-reference-02': 'MOCA',
        'modca-reference-10': 'MODCA',
        'ptoca-reference-04': 'PTOCA'
    }

    prefix = spec_map.get(spec, spec)

    if filename == 'Front_Matter.md':
        suffix = 'FM'
    elif filename == 'Glossary.md':
        suffix = 'GLO'
    elif filename == 'Index.md':
        suffix = 'IDX'
    elif filename == 'Notices.md':
        suffix = 'NOT'
    else:
        m = re.match(r'Chapter_(\d+)\.md', filename)
        if m:
            suffix = m.group(1)
        else:
            m = re.match(r'Appendix_([A-Z])(?:_(\d+))?\.md', filename)
            if m:
                if m.group(2):
                    suffix = f"{m.group(1)}{m.group(2)}"
                else:
                    suffix = m.group(1)
            else:
                suffix = filename.replace('.md', '')

    return f"{prefix}-{suffix}"

def transform(filepath):
    with open(filepath, 'r') as f:
        lines = f.readlines()

    output = []
    in_table = False
    for line in lines:
        if line.startswith('| Specification |'):
            in_table = True
            output.append('| ID | Specification | File | Title | Coverage |\n')
            continue

        if in_table and line.startswith('| :--- |'):
            output.append('| :--- | :--- | :--- | :--- | :--- |\n')
            continue

        if in_table and line.startswith('|'):
            parts = [p.strip() for p in line.split('|')]
            # parts[0] is empty, parts[1] is Specification, parts[2] is File, parts[3] is Title, parts[4] is Coverage, parts[5] is empty
            if len(parts) >= 5:
                spec = parts[1]
                filename = parts[2]
                id_val = get_id(spec, filename)
                new_line = f"| {id_val} | {parts[1]} | {parts[2]} | {parts[3]} | {parts[4]} |\n"
                output.append(new_line)
            else:
                output.append(line)
        else:
            output.append(line)

    return "".join(output)

if __name__ == "__main__":
    print(transform('TEST_COVERAGE.md'), end='')
