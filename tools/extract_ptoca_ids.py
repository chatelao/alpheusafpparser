import os
import re
import glob

def extract_ids_from_file(filepath):
    results = []
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Find all [PTOCA-...] matches
    matches = re.finditer(r'\[(PTOCA-[A-Z0-9]+-[0-9]+)\]', content)

    last_end = 0
    for match in matches:
        req_id = match.group(1).strip()
        start = match.start()

        # Get text between last ID (or start of file) and this ID
        raw_text = content[last_end:start].strip()

        # Find the last paragraph.
        # Paragraphs are separated by double newlines or single newlines if they are list items.
        # Let's try to find the text after the last empty line.
        parts = re.split(r'\n\s*\n', raw_text)
        last_block = parts[-1].strip()

        # If it's a list item, we might only want that item.
        lines = last_block.split('\n')
        summary = " ".join(l.strip() for l in lines if l.strip())

        # Cleanup summary
        # Remove markdown headers
        summary = re.sub(r'^#+\s+', '', summary)
        # Remove list markers like '* ', '- ', '1. '
        summary = re.sub(r'^[\s\*\-\d\.]+\s+', '', summary)
        # Remove bold/italic markers
        summary = summary.replace('**', '').replace('__', '').replace('*', '').replace('_', '')
        # Remove multiple spaces
        summary = re.sub(r'\s+', ' ', summary)

        if not summary:
            summary = "No summary available"

        results.append((req_id, summary))
        last_end = match.end()

    return results

def main():
    base_path = 'specifications/markdown/ptoca-reference-04/'
    files = glob.glob(os.path.join(base_path, '*.md'))

    all_data = []
    for f in files:
        all_data.extend(extract_ids_from_file(f))

    # Sort data logically
    def sort_key(item):
        parts = item[0].split('-')
        # PTOCA-1-001 -> ['PTOCA', '1', '001']

        section = parts[1]
        try:
            section_num = int(section)
        except ValueError:
            mapping = {'FM': 0, 'A': 101, 'B': 102, 'C': 103, 'Glossary': 104}
            section_num = mapping.get(section, 999)

        try:
            id_num = int(parts[2])
        except ValueError:
            id_num = 0

        return (section_num, id_num)

    all_data.sort(key=sort_key)

    # Dedup by ID
    unique_data = {}
    for req_id, summary in all_data:
        if req_id not in unique_data:
            unique_data[req_id] = summary

    sorted_ids = sorted(unique_data.keys(), key=lambda x: sort_key((x, "")))

    with open('ptoca_data.txt', 'w', encoding='utf-8') as f:
        for req_id in sorted_ids:
            f.write(f"{req_id}\t{unique_data[req_id]}\n")

if __name__ == "__main__":
    main()
