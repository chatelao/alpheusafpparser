import sys
import collections
import re

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
    ("Encoding & Sanitization", [
        r"com\.mgz\.xml\.EbcdicToUtf8",
        r"com\.mgz\.xml\.SanitizingXMLStreamWriter",
        r"com\.mgz\.xml\.AfpXmlStreamWriter\.writeEbcdic"
    ]),
    ("I/O & Scanning", [
        r"com\.mgz\.afp\.parser\.AFPScanner",
        r"java\.nio\.MappedByteBuffer",
        r"java\.io\.File"
    ]),
    ("Field Parsing", [
        r"com\.mgz\.afp\.parser\.AFPParser",
        r"com\.mgz\.afp\.StructuredFieldFactory"
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
    if len(sys.argv) < 2:
        print("Usage: python3 tools/analyze_hotspots.py <collapsed_file.txt>")
        sys.exit(1)

    collapsed_file = sys.argv[1]
    stats = collections.defaultdict(int)
    total_samples = 0

    try:
        with open(collapsed_file, 'r') as f:
            for line in f:
                line = line.strip()
                if not line:
                    continue
                parts = line.rsplit(' ', 1)
                if len(parts) == 2:
                    stack, count = parts
                    count = int(count)
                    area = categorize(stack)
                    stats[area] += count
                    total_samples += count
    except FileNotFoundError:
        print(f"Error: File {collapsed_file} not found.", file=sys.stderr)
        sys.exit(1)

    if total_samples == 0:
        print(f"No samples found in {collapsed_file}")
        return

    print(f"Analysis for {collapsed_file} (Total samples: {total_samples})")
    print("-" * 60)
    print(f"{'Area':<30} | {'Samples':<10} | {'Percentage':<10}")
    print("-" * 60)

    for area_name, _ in AREAS:
        count = stats.get(area_name, 0)
        percentage = (count / total_samples) * 100
        print(f"{area_name:<30} | {count:<10} | {percentage:>9.2f}%")

    # Other
    other_count = stats.get("Other", 0)
    other_percentage = (other_count / total_samples) * 100
    print(f"{'Other':<30} | {other_count:<10} | {other_percentage:>9.2f}%")

if __name__ == "__main__":
    main()
