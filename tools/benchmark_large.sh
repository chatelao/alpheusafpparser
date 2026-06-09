#!/bin/bash

# Capture current branch to return to it later
INITIAL_BRANCH=$(git rev-parse --abbrev-ref HEAD)
if [ "$INITIAL_BRANCH" = "HEAD" ]; then
    INITIAL_BRANCH=$(git rev-parse HEAD)
fi

# Cleanup function to restore repository state
cleanup() {
    echo ""
    echo "Restoring repository state..."
    git checkout "$INITIAL_BRANCH" --quiet 2>/dev/null
    git stash pop --quiet 2>/dev/null
    if [ -d "$TEMP_TEST_DIR" ]; then
        rm -rf "$TEMP_TEST_DIR"
    fi
}

# Trap signals and script exit
trap cleanup EXIT

# Specialized releases for large file benchmark
if [ -n "$RELEASES_TO_BENCHMARK" ]; then
    IFS=' ' read -r -a RELEASES <<< "$RELEASES_TO_BENCHMARK"
else
    RELEASES=("v3.4" "v15.6")
fi

TEST_DIR="${TEST_DIR:-perf_test_large}"
ITERATIONS="${ITERATIONS:-5}"
WARMUP_ITERATIONS="${WARMUP_ITERATIONS:-1}"
OUTPUT_DIR="test_output_bench_large"
FORMAT="${FORMAT:-xml}"

# Count files in TEST_DIR
FILE_COUNT=$(ls "$TEST_DIR"/*.afp 2>/dev/null | wc -l)
if [ "$FILE_COUNT" -eq 0 ]; then
    echo "Error: No AFP files found in $TEST_DIR"
    exit 1
fi

# Temporary directory to hold test files across checkouts
TEMP_TEST_DIR=$(mktemp -d)
cp "$TEST_DIR"/*.afp "$TEMP_TEST_DIR"

mkdir -p "$OUTPUT_DIR"

echo "============================================================"
echo "      Large File Performance Benchmark (~100KB files)"
echo "      Files: $FILE_COUNT | Iterations: $ITERATIONS"
echo "============================================================"
echo ""
echo "| Release | Optimization Flags | Total Time ($ITERATIONS runs) | Avg Time per run |"
echo "| :--- | :--- | :--- | :--- |"

# Stash current changes to allow checkout
git stash push --quiet

for rel in "${RELEASES[@]}"; do
    echo "Processing $rel..." >&2
    # Handle 'current' as the branch we started on
    if [ "$rel" = "current" ]; then
        git checkout "$INITIAL_BRANCH" --quiet
    else
        git checkout "$rel" --quiet || { echo "Error: Could not checkout $rel" >&2; continue; }
    fi

    ./gradlew shadowJar --quiet > /dev/null 2>&1 || { echo "| **$rel** | Build Failed | N/A | N/A |"; continue; }
    JAR_FILE=$(ls build/libs/*.jar | head -n 1)

    if [ -z "$JAR_FILE" ]; then
        echo "| **$rel** | JAR not found | N/A | N/A |"
        continue
    fi

    # Detect supported flags
    HELP_OUTPUT=$(java -jar "$JAR_FILE" -h 2>&1)
    SUPPORTED_FLAGS=""

    if [ "$FORMAT" != "xml" ]; then
        if echo "$HELP_OUTPUT" | grep -q -- "-f"; then
            SUPPORTED_FLAGS="$SUPPORTED_FLAGS -f $FORMAT"
        fi
    fi

    # Priority flags: -p (parallel), -P (ptx-debug), -a (aggressive-io), -c (charset-opt)
    if echo "$HELP_OUTPUT" | grep -q -- "-p"; then
        SUPPORTED_FLAGS="$SUPPORTED_FLAGS -p"
    fi
    if echo "$HELP_OUTPUT" | grep -q -- "-P"; then
        SUPPORTED_FLAGS="$SUPPORTED_FLAGS -P"
    fi
    if echo "$HELP_OUTPUT" | grep -q -- "-a"; then
        SUPPORTED_FLAGS="$SUPPORTED_FLAGS -a"
    fi
    if echo "$HELP_OUTPUT" | grep -q -- "-c"; then
        SUPPORTED_FLAGS="$SUPPORTED_FLAGS -c"
    fi

    # JVM Arguments for instrumentation
    JVM_ARGS=""
    if [ -n "$AGENT_PATH" ]; then
        JVM_ARGS="$JVM_ARGS -agentpath:$AGENT_PATH"
    fi

    # Warmup runs
    if [ "$WARMUP_ITERATIONS" -gt 0 ]; then
        echo "  Warming up ($WARMUP_ITERATIONS iterations)..." >&2
        for i in $(seq 1 $WARMUP_ITERATIONS); do
             if echo "$HELP_OUTPUT" | grep -q -- "-d"; then
                 java $JVM_ARGS -jar "$JAR_FILE" $SUPPORTED_FLAGS -d "$TEMP_TEST_DIR" "$OUTPUT_DIR" > /dev/null 2>&1
            else
                for f in "$TEMP_TEST_DIR"/*.afp; do
                    fname=$(basename "$f")
                    java $JVM_ARGS -jar "$JAR_FILE" "$f" "$OUTPUT_DIR/$fname.xml" > /dev/null 2>&1
                done
            fi
        done
    fi

    # Start timing
    START_TIME=$(date +%s%3N)
    for i in $(seq 1 $ITERATIONS); do
        # Enable JFR if requested
        JFR_ARGS=""
        if [ "$ENABLE_JFR" = "true" ]; then
            mkdir -p perf_test/profiles/
            # Add format suffix if not xml
            FORMAT_SUFFIX=""
            if [ "$FORMAT" != "xml" ]; then FORMAT_SUFFIX="_${FORMAT}"; fi
            # Optimize for fast runs: Increase sampling frequency to 1ms
            JFR_ARGS="-XX:StartFlightRecording=settings=profile,jdk.ExecutionSample#period=1ms,filename=perf_test/profiles/profile_large_${rel}${FORMAT_SUFFIX}_run${i}.jfr"
        fi

        # Check if -d (directory mode) is supported
        if echo "$HELP_OUTPUT" | grep -q -- "-d"; then
             java $JVM_ARGS $JFR_ARGS -jar "$JAR_FILE" $SUPPORTED_FLAGS -d "$TEMP_TEST_DIR" "$OUTPUT_DIR" > /dev/null 2>&1
        else
            # Manual loop for old versions that don't support -d
            for f in "$TEMP_TEST_DIR"/*.afp; do
                fname=$(basename "$f")
                java $JVM_ARGS $JFR_ARGS -jar "$JAR_FILE" "$f" "$OUTPUT_DIR/$fname.xml" > /dev/null 2>&1
            done
        fi
    done
    END_TIME=$(date +%s%3N)

    TOTAL_TIME=$((END_TIME - START_TIME))

    # Accurate average calculation
    if command -v bc > /dev/null; then
        AVG_TIME=$(echo "scale=1; $TOTAL_TIME / $ITERATIONS" | bc)
    else
        AVG_TIME_INT=$((TOTAL_TIME / ITERATIONS))
        AVG_TIME_FRAC=$(( (TOTAL_TIME * 10 / ITERATIONS) % 10 ))
        AVG_TIME="${AVG_TIME_INT}.${AVG_TIME_FRAC}"
    fi

    # Trim leading space from flags
    FLAGS_DISPLAY=$(echo "$SUPPORTED_FLAGS" | xargs)
    if [ -z "$FLAGS_DISPLAY" ]; then FLAGS_DISPLAY="(None)"; fi

    echo "| **$rel** | \`$FLAGS_DISPLAY\` | $TOTAL_TIME ms | $AVG_TIME ms |"
done
