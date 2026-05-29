#!/bin/bash

# Capture current branch to return to it later
INITIAL_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Usage check
if [ "$#" -lt 1 ]; then
    echo "Usage: $0 <tag1> [tag2 ... tagN]"
    echo "Example: $0 v9.7 v9.8 v9.9 v9.10 v9.11"
    return 1 2>/dev/null || exit 1
fi

RELEASES=("$@")
TEST_DIR="perf_test"
ITERATIONS=10
OUTPUT_DIR="test_output_bench"

mkdir -p "$OUTPUT_DIR"

# Stash current changes to allow checkout, and track if we stashed anything
STASH_OUT=$(git stash push --quiet)
STASHED=0
if [[ "$STASH_OUT" != "No local changes to save" ]]; then
    STASHED=1
fi

echo "| Release | Optimization Flags | Total Time (10 runs of 10 files) | Avg Time per run |"
echo "| :--- | :--- | :--- | :--- |"

for rel in "${RELEASES[@]}"; do
    if ! git checkout "$rel" --quiet 2>/dev/null; then
        echo "Error: Could not checkout $rel" >&2
        continue
    fi

    if ! ./gradlew shadowJar --quiet > /dev/null 2>&1; then
        echo "Error: Build failed for $rel" >&2
        continue
    fi

    JAR_FILE=$(ls build/libs/*.jar | head -n 1)
    if [ ! -f "$JAR_FILE" ]; then
        echo "Error: JAR not found for $rel" >&2
        continue
    fi

    # Detect supported flags
    HELP_OUTPUT=$(java -jar "$JAR_FILE" -h 2>&1)
    SUPPORTED_FLAGS=""

    if echo "$HELP_OUTPUT" | grep -q -- "-p"; then
        SUPPORTED_FLAGS="$SUPPORTED_FLAGS -p"
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
if [ "$STASHED" -eq 1 ]; then
    git stash pop --quiet
fi
