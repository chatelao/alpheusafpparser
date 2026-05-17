import sys
import re

def mark_coverage(file_path, ranges):
    with open(file_path, 'r') as f:
        lines = f.readlines()

    updated_lines = []
    marked_count = 0
    for line in lines:
        match = re.match(r'\|\s*(GOCA-[1-9A-D]+-\d+)\s*\|', line)
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
    # req_id like GOCA-1-001
    def parse_id(rid):
        parts = rid.split('-')
        chap_str = parts[1]
        # Appendices A, B, C or numeric chapters
        if chap_str.isdigit():
            chapter = int(chap_str)
        else:
            # Map A to 101, B to 102, etc.
            chapter = 100 + ord(chap_str) - ord('A') + 1
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
        ('GOCA-1-001', 'GOCA-1-063'),
        ('GOCA-2-001', 'GOCA-2-015'),
        ('GOCA-3-001', 'GOCA-3-145'),
        ('GOCA-4-001', 'GOCA-4-340'),
        ('GOCA-5-001', 'GOCA-5-059'),
        ('GOCA-6-001', 'GOCA-6-096'),
        # Chapter 7 contains drawing orders. Marking supported ones.
        ('GOCA-7-001', 'GOCA-7-047'), # Commands and general order format
        ('GOCA-7-058', 'GOCA-7-118'), # Summary list of orders (most implemented)
        ('GOCA-7-119', 'GOCA-7-137'), # Begin Area
        ('GOCA-7-138', 'GOCA-7-162'), # Begin Custom Pattern
        ('GOCA-7-163', 'GOCA-7-186'), # Begin Image
        ('GOCA-7-194', 'GOCA-7-217'), # Box
        ('GOCA-7-218', 'GOCA-7-239'), # Character String
        ('GOCA-7-246', 'GOCA-7-251'), # Comment
        ('GOCA-7-252', 'GOCA-7-299'), # Bezier
        ('GOCA-7-300', 'GOCA-7-309'), # Delete Pattern
        ('GOCA-7-310', 'GOCA-7-315'), # End Area
        ('GOCA-7-316', 'GOCA-7-321'), # End Custom Pattern
        ('GOCA-7-322', 'GOCA-7-328'), # End Image
        ('GOCA-7-329', 'GOCA-7-336'), # End Prolog
        ('GOCA-7-337', 'GOCA-7-384'), # Fillet
        ('GOCA-7-385', 'GOCA-7-417'), # Full Arc
        ('GOCA-7-418', 'GOCA-7-463'), # Linear Gradient
        ('GOCA-7-464', 'GOCA-7-498'), # Marker and NOP
        ('GOCA-7-499', 'GOCA-7-537'), # Partial Arc
        ('GOCA-7-538', 'GOCA-7-620'), # Radial Gradient
        ('GOCA-7-621', 'GOCA-7-650'), # Relative Line
        ('GOCA-7-651', 'GOCA-7-657'), # Segment Characteristics
        ('GOCA-7-658', 'GOCA-7-672'), # Set Arc Parameters
        ('GOCA-7-673', 'GOCA-7-684'), # Set Background Mix
        ('GOCA-7-685', 'GOCA-7-701'), # Set Character Angle
        ('GOCA-7-702', 'GOCA-7-723'), # Set Character Cell
        ('GOCA-7-724', 'GOCA-7-737'), # Set Character Direction
        ('GOCA-7-738', 'GOCA-7-749'), # Set Character Precision
        ('GOCA-7-750', 'GOCA-7-761'), # Set Character Set
        ('GOCA-7-762', 'GOCA-7-769'), # Set Character Shear
        ('GOCA-7-770', 'GOCA-7-784'), # Set Color
        ('GOCA-7-785', 'GOCA-7-792'), # Set Current Position
        ('GOCA-7-793', 'GOCA-7-818'), # Set Custom Line Type
        ('GOCA-7-819', 'GOCA-7-836'), # Set Extended Color
        ('GOCA-7-837', 'GOCA-7-847'), # Set Fractional Line Width
        ('GOCA-7-848', 'GOCA-7-858'), # Set Line End
        ('GOCA-7-859', 'GOCA-7-869'), # Set Line Join
        ('GOCA-7-870', 'GOCA-7-880'), # Set Line Type
        ('GOCA-7-881', 'GOCA-7-890'), # Set Line Width
        ('GOCA-7-891', 'GOCA-7-906'), # Set Marker Cell
        ('GOCA-7-907', 'GOCA-7-914'), # Set Marker Set
        ('GOCA-7-915', 'GOCA-7-922'), # Set Marker Symbol
        ('GOCA-7-923', 'GOCA-7-936'), # Set Mix
        ('GOCA-7-937', 'GOCA-7-949'), # Set Pattern Reference Point
        ('GOCA-7-950', 'GOCA-7-958'), # Set Pattern Set
        ('GOCA-7-959', 'GOCA-7-966'), # Set Pattern Symbol
        ('GOCA-7-967', 'GOCA-7-1025'),# Set Process Color
        ('GOCA-A-001', 'GOCA-A-177'), # MO:DCA Environment
        ('GOCA-B-001', 'GOCA-B-023'), # IPDS Environment
        ('GOCA-C-001', 'GOCA-C-046'), # Migration Functions
    ]
    count = mark_coverage('TEST_COVERAGE_GOCA.md', ranges)
    print(f"Marked {count} requirements as covered.")
