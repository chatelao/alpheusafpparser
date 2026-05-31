import re
import os
import subprocess

def get_covered_ids():
    covered_ids = set()
    # Search in src and test directories
    try:
        output = subprocess.check_output(['grep', '-rEho', r'\[PTOCA-[A-Z0-9]+-[0-9]+\]', 'src/', 'test/'], stderr=subprocess.STDOUT).decode('utf-8')
        for line in output.split('\n'):
            if line:
                covered_ids.add(line.strip('[]'))
    except subprocess.CalledProcessError:
        pass # No matches found
    return covered_ids

def main():
    covered_ids = get_covered_ids()
    print(f"Found {len(covered_ids)} unique covered requirement IDs in the codebase.")

    new_rows = []
    if not os.path.exists('ptoca_data.txt'):
        print("Error: ptoca_data.txt not found. Run extract_ptoca_ids.py first.")
        return

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

    print("TEST_COVERAGE_PTOCA.md updated.")

if __name__ == "__main__":
    main()
