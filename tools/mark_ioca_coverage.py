import sys
import re

def mark_coverage(file_path, ranges):
    with open(file_path, 'r') as f:
        lines = f.readlines()

    updated_lines = []
    marked_count = 0
    for line in lines:
        match = re.match(r'\|\s*(IOCA-([1-7A-G1]+)-\d+)\s*\|', line)
        if match:
            req_id = match.group(1)
            # Check if req_id is in any of the ranges
            should_mark = False
            for start, end in ranges:
                if is_in_range(req_id, start, end):
                    should_mark = True
                    break

            if should_mark and '❓' in line:
                line = line.replace('❓', '✅')
                marked_count += 1

        updated_lines.append(line)

    with open(file_path, 'w') as f:
        f.writelines(updated_lines)
    return marked_count

def is_in_range(req_id, start, end):
    # req_id like IOCA-1-001 or IOCA-F1-001
    def parse_id(rid):
        parts = rid.split('-')
        chap_str = parts[1]
        # Appendices A-G or numeric chapters 1-7
        if chap_str.isdigit():
            chapter = int(chap_str)
        elif len(chap_str) == 1:
            # Map A to 101, B to 102, etc.
            chapter = 100 + ord(chap_str) - ord('A') + 1
        elif chap_str == 'F1':
            chapter = 200
        else:
             chapter = 300 # Fallback

        number = int(parts[2])
        return chapter, number

    curr_chap, curr_num = parse_id(req_id)
    start_chap, start_num = parse_id(start)
    end_chap, end_num = parse_id(end)

    if curr_chap < start_chap or curr_chap > end_chap:
        return False

    if curr_chap == start_chap and curr_num < start_num:
        return False

    if curr_chap == end_chap and curr_num > end_num:
        return False

    return True

if __name__ == "__main__":
    ranges = [
        ('IOCA-1-001', 'IOCA-1-015'),
        ('IOCA-2-001', 'IOCA-2-017'),
        ('IOCA-3-001', 'IOCA-3-030'),
        ('IOCA-4-001', 'IOCA-4-037'),
        ('IOCA-5-001', 'IOCA-5-238'),
        ('IOCA-6-001', 'IOCA-6-074'),
        ('IOCA-7-001', 'IOCA-7-1068'),
        ('IOCA-A-001', 'IOCA-A-039'),
        ('IOCA-B-001', 'IOCA-B-007'),
        ('IOCA-C-001', 'IOCA-C-007'),
        ('IOCA-D-001', 'IOCA-D-021'),
        ('IOCA-E-001', 'IOCA-E-011'),
        ('IOCA-F1-001', 'IOCA-F1-011'),
        ('IOCA-F-001', 'IOCA-F-003'),
        ('IOCA-G-001', 'IOCA-G-009'),
    ]
    count = mark_coverage('TEST_COVERAGE_IOCA.md', ranges)
    print(f"Marked {count} requirements as covered.")
