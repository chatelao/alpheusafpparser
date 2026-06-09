import sys
import collections
import re
import argparse

# Functional area definitions (ordered by priority)
AREAS = [
    ("Fast-Path Serialization", [
        r"com\.mgz\.xml\.AfpJacksonXmlWriter\.writeFieldDirectly",
        r"com\.mgz\.xml\.AfpJacksonXmlWriter\.writeControlSequence",
        r"com\.mgz\.xml\.AfpJacksonXmlWriter\.writeTripletDirectly"
    ]),
    ("Jackson Fallback", [
        r"com\.fasterxml\.jackson",
        r"com\.mgz\.xml\.AfpJacksonXmlWriter\.writeFieldViaJackson"
    ]),
    ("XML Library (Woodstox)", [
        r"com\.ctc\.wstx",
        r"org\.codehaus\.stax2"
    ]),
    ("Encoding & Sanitization", [
        r"com\.mgz\.xml\.EbcdicToUtf8",
        r"com\.mgz\.xml\.SanitizingXMLStreamWriter",
        r"com\.mgz\.xml\.AfpXmlStreamWriter\.writeEbcdic",
        r"com\.mgz\.util\.UtilCharacterEncoding"
    ]),
    ("I/O & Scanning", [
        r"com\.mgz\.afp\.parser\.AFPScanner",
        r"java\.nio\.MappedByteBuffer",
        r"java\.io\.File"
    ]),
    ("Field Parsing", [
        r"com\.mgz\.afp\.parser\.AFPParser",
        r"com\.mgz\.afp\.StructuredFieldFactory",
        r"decodeAFP",
        r"parseControlSequences"
    ]),
    ("Validation", [
        r"com\.mgz\.afp\..*\.validate",
        r"com\.mgz\.afp\.base\.StructuredField\.checkDataLength",
        r"com\.mgz\.afp\.bcoca\.BDD_BarCodeDataDescriptor\.decodeAFP"
    ]),
    ("Thread Orchestration", [
        r"com\.mgz\.afp\.parser\.Parallel",
        r"java\.util\.concurrent"
    ]),
    ("JVM & Infrastructure", [
        r"java\.lang\.ClassLoader",
        r"java\.lang\.invoke",
        r"jdk\.internal\.loader",
        r"java\.util\.zip\.ZipFile",
        r"java\.lang\.Thread\.run"
    ])
]

def categorize(stack):
    for area_name, patterns in AREAS:
        for pattern in patterns:
            if re.search(pattern, stack):
                return area_name
    return "Other"

def main():
    parser = argparse.ArgumentParser(description="Analyze hotspots and detect performance drift.")
    parser.add_argument("collapsed_file", help="Path to the collapsed stack file (standard or differential format).")
    parser.add_argument("--check-drift", action="store_true", help="Check for percentage share drift in functional areas.")
    parser.add_argument("--threshold", type=float, default=5.0, help="Drift threshold in percent (default: 5.0%%).")

    args = parser.parse_args()

    collapsed_file = args.collapsed_file
    stats1 = collections.defaultdict(int)
    stats2 = collections.defaultdict(int)
    total1 = 0
    total2 = 0
    is_diff = False

    try:
        with open(collapsed_file, 'r') as f:
            for line in f:
                line = line.strip()
                if not line:
                    continue
                parts = line.split(' ')
                # Check for differential format: stack count1 count2
                if len(parts) >= 3 and parts[-1].isdigit() and parts[-2].isdigit():
                    is_diff = True
                    count2 = int(parts[-1])
                    count1 = int(parts[-2])
                    stack = " ".join(parts[:-2])
                    area = categorize(stack)
                    stats1[area] += count1
                    stats2[area] += count2
                    total1 += count1
                    total2 += count2
                elif len(parts) >= 2 and parts[-1].isdigit():
                    count = int(parts[-1])
                    stack = " ".join(parts[:-1])
                    area = categorize(stack)
                    stats1[area] += count
                    total1 += count
    except FileNotFoundError:
        print(f"Error: File {collapsed_file} not found.", file=sys.stderr)
        sys.exit(1)
    except ValueError:
        print(f"Error: Invalid format in {collapsed_file}")
        sys.exit(1)

    if total1 == 0 and total2 == 0:
        print(f"No samples found in {collapsed_file}")
        return

    drifts = []

    if is_diff:
        print(f"Differential Analysis for {collapsed_file}")
        print(f"Baseline (V1) Total: {total1} samples")
        print(f"Target   (V2) Total: {total2} samples")

        if total1 == 0:
            print("\nWARNING: Baseline (V1) has 0 samples. Shift percentages are not meaningful.")
            print("-" * 60)
            print(f"{'Area':<30} | {'V2 Samples':<10} | {'V2 %':<10}")
            print("-" * 60)
            for area_name, _ in AREAS:
                c2 = stats2.get(area_name, 0)
                p2 = (c2 / total2 * 100) if total2 > 0 else 0
                print(f"{area_name:<30} | {c2:<10} | {p2:>9.2f}%")
            # Other
            c2 = stats2.get("Other", 0)
            p2 = (c2 / total2 * 100) if total2 > 0 else 0
            print(f"{'Other':<30} | {c2:<10} | {p2:>9.2f}%")
        else:
            print("-" * 85)
            print(f"{'Area':<30} | {'V1 %':<8} | {'V2 %':<8} | {'Shift %':<10} | {'Delta':<10}")
            print("-" * 85)

            for area_name, _ in AREAS + [("Other", [])]:
                c1 = stats1.get(area_name, 0)
                c2 = stats2.get(area_name, 0)
                p1 = (c1 / total1 * 100) if total1 > 0 else 0
                p2 = (c2 / total2 * 100) if total2 > 0 else 0
                shift = p2 - p1
                delta = c2 - c1
                print(f"{area_name:<30} | {p1:>6.2f}% | {p2:>6.2f}% | {shift:>+8.2f}% | {delta:>10}")

                # Drift detection: Only relevant for functional areas, excluding "JVM & Infrastructure" and "Other"
                # as they are often noise or outside direct control.
                # Actually, let's include all except "JVM & Infrastructure" and "Other" for strict guarding.
                if args.check_drift and area_name not in ["JVM & Infrastructure", "Other"]:
                    if shift > args.threshold:
                        drifts.append((area_name, shift))
    else:
        print(f"Analysis for {collapsed_file} (Total samples: {total1})")
        print("-" * 60)
        print(f"{'Area':<30} | {'Samples':<10} | {'Percentage':<10}")
        print("-" * 60)

        for area_name, _ in AREAS:
            count = stats1.get(area_name, 0)
            percentage = (count / total1 * 100) if total1 > 0 else 0
            print(f"{area_name:<30} | {count:<10} | {percentage:>9.2f}%")

        # Other
        other_count = stats1.get("Other", 0)
        other_percentage = (other_count / total1 * 100) if total1 > 0 else 0
        print(f"{'Other':<30} | {other_count:<10} | {other_percentage:>9.2f}%")

    if drifts:
        print(f"\nERROR: Area-Drift detected (Threshold: {args.threshold}%):")
        for area, shift in drifts:
            print(f"- {area}: {shift:>+6.2f}% shift")
        sys.exit(1)
    elif args.check_drift and is_diff:
        print("\nArea percentages are within stable limits.")

if __name__ == "__main__":
    main()
