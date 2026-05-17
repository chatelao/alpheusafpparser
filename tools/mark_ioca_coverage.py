import sys
import re
import os

def mark_coverage(file_path, ranges):
    if not os.path.exists(file_path):
        print(f"Error: {file_path} not found.")
        return 0

    with open(file_path, 'r') as f:
        lines = f.readlines()

    updated_lines = []
    marked_count = 0
    for line in lines:
        match = re.match(r'\|\s*(IOCA-([1-9A-G]+)-(\d+))\s*\|', line)
        if match:
            req_id = match.group(1)
            chap_str = match.group(2)
            num_str = match.group(3)

            # Check if req_id is in any of the ranges
            should_mark = False
            for start, end in ranges:
                if is_in_range(req_id, chap_str, num_str, start, end):
                    should_mark = True
                    break

            if should_mark and '❓' in line:
                line = line.replace('❓', '✅')
                marked_count += 1

        updated_lines.append(line)

    with open(file_path, 'w') as f:
        f.writelines(updated_lines)
    return marked_count

def parse_chap(chap_str):
    if chap_str.isdigit():
        return int(chap_str)
    else:
        # Handle single letters A, B, C...
        if len(chap_str) == 1:
            return 100 + ord(chap_str) - ord('A') + 1
        return 999 # Should not happen based on current specs

def is_in_range(req_id, chap_str, num_str, start, end):
    # start/end like IOCA-7-001
    def parse_full_id(rid):
        parts = rid.split('-')
        return parse_chap(parts[1]), int(parts[2])

    curr_chap = parse_chap(chap_str)
    curr_num = int(num_str)

    start_chap, start_num = parse_full_id(start)
    end_chap, end_num = parse_full_id(end)

    if curr_chap < start_chap or curr_chap > end_chap:
        return False

    if curr_chap == start_chap and curr_num < start_num:
        return False

    if curr_chap == end_chap and curr_num > end_num:
        return False

    return True

if __name__ == "__main__":
    # Target Chapter 7: Function Sets [IOCA-7-001 to IOCA-7-1068]
    ranges = [
        ('IOCA-7-001', 'IOCA-7-1068'),
    ]
    count = mark_coverage('TEST_COVERAGE_IOCA.md', ranges)
    print(f"Marked {count} IOCA requirements as covered.")
