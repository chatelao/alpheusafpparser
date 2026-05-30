#!/bin/bash

# Capture current branch to return to it later
INITIAL_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Selected releases to investigate further
RELEASES=("v3.4" "v3.5" "v4.0" "v4.1" "v4.2" "v4.3" "v5.0" "v5.1" "v5.2" "v5.3")
TEST_DIR="perf_test"
ITERATIONS=10
OUTPUT_DIR="test_output_bench"

mkdir -p "$OUTPUT_DIR"

echo "### Performance Investigation: v3.4 to v5.3"
echo ""
echo "| Release | Optimization Flags | Total Time (10 runs of 10 files) | Avg Time per run |"
echo "| :--- | :--- | :--- | :--- |"

# Stash current changes (like 10x10.md) to allow checkout
git stash push --quiet

for rel in "${RELEASES[@]}"; do
    git checkout "$rel" --quiet
    ./gradlew shadowJar --quiet > /dev/null 2>&1
    JAR_FILE=$(ls build/libs/*.jar | head -n 1)

    # Detect supported flags
    HELP_OUTPUT=$(java -jar "$JAR_FILE" -h 2>&1)
    SUPPORTED_FLAGS=""

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

    # Start timing
    START_TIME=$(date +%s%3N)
    for i in $(seq 1 $ITERATIONS); do
        java -jar "$JAR_FILE" $SUPPORTED_FLAGS -d "$TEST_DIR" "$OUTPUT_DIR" > /dev/null 2>&1
    done
    END_TIME=$(date +%s%3N)

    TOTAL_TIME=$((END_TIME - START_TIME))

    # Manual division since bc might be missing
    AVG_TIME_INT=$((TOTAL_TIME / ITERATIONS))
    AVG_TIME_DEC=$(( (TOTAL_TIME % ITERATIONS) ))
    AVG_TIME="${AVG_TIME_INT}.${AVG_TIME_DEC}"

    # Trim leading space from flags
    FLAGS_DISPLAY=$(echo "$SUPPORTED_FLAGS" | xargs)
    if [ -z "$FLAGS_DISPLAY" ]; then FLAGS_DISPLAY="(None)"; fi

    echo "| **$rel** | \`$FLAGS_DISPLAY\` | $TOTAL_TIME ms | $AVG_TIME ms |"
done

git checkout "$INITIAL_BRANCH" --quiet
git stash pop --quiet
