#!/bin/bash

# Capture current branch to return to it later
INITIAL_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Selected releases to investigate further, including first (v0.1) and last (v12.0)
RELEASES=("v0.1" "v1.0" "v3.0" "v3.4" "v3.5" "v4.0" "v4.1" "v4.2" "v4.3" "v5.0" "v5.1" "v5.2" "v5.3" "v7.0" "v9.11" "v12.0")
TEST_DIR="perf_test"
ITERATIONS=10
OUTPUT_DIR="test_output_bench_10x10"

# Temporary directory to hold test files across checkouts
TEMP_TEST_DIR=$(mktemp -d)
cp "$TEST_DIR"/*.afp "$TEMP_TEST_DIR"

mkdir -p "$OUTPUT_DIR"

echo "| Release | Optimization Flags | Total Time (10 runs of 10 files) | Avg Time per run |"
echo "| :--- | :--- | :--- | :--- |"

# Stash current changes to allow checkout
git stash push --quiet

for rel in "${RELEASES[@]}"; do
    git checkout "$rel" --quiet
    ./gradlew shadowJar --quiet > /dev/null 2>&1
    JAR_FILE=$(ls build/libs/*.jar | head -n 1)

    if [ -z "$JAR_FILE" ]; then
        echo "| **$rel** | Build Failed | N/A | N/A |"
        continue
    fi

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
        # Check if -d (directory mode) is supported
        if echo "$HELP_OUTPUT" | grep -q -- "-d"; then
             java -jar "$JAR_FILE" $SUPPORTED_FLAGS -d "$TEMP_TEST_DIR" "$OUTPUT_DIR" > /dev/null 2>&1
        else
            # Manual loop for old versions that don't support -d
            for f in "$TEMP_TEST_DIR"/*.afp; do
                fname=$(basename "$f")
                java -jar "$JAR_FILE" "$f" "$OUTPUT_DIR/$fname.xml" > /dev/null 2>&1
            done
        fi
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

rm -rf "$TEMP_TEST_DIR"
git checkout "$INITIAL_BRANCH" --quiet
git stash pop --quiet
