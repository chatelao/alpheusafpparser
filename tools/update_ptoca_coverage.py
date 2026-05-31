import re

def main():
    covered_ids = set()
    with open('TEST_COVERAGE_PTOCA.md', 'r', encoding='utf-8') as f:
        for line in f:
            match = re.search(r'\| (PTOCA-[A-Z0-9]+-[0-9]+) \| .* \| ✅ \|', line)
            if match:
                covered_ids.add(match.group(1))

    new_rows = []
    with open('ptoca_data.txt', 'r', encoding='utf-8') as f:
        for line in f:
            if '\t' in line:
                req_id, summary = line.strip().split('\t', 1)
                coverage = '✅' if req_id in covered_ids else '❓'
                new_rows.append(f"| {req_id} | {summary} | {coverage} |")

    header = "# Granular Test Coverage - PTOCA\n\n| Requirement ID | Summary | Coverage |\n| :--- | :--- | :---: |\n"

    with open('TEST_COVERAGE_PTOCA.md', 'w', encoding='utf-8') as f:
        f.write(header)
        f.write('\n'.join(new_rows))
        f.write('\n')

if __name__ == "__main__":
    main()
