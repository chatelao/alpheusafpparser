#!/bin/bash

# Alpheus Performance Audit Script
# Automates metric extraction and hotspot analysis from JFR profiles.

PROFILE_DIR="perf_test/profiles"
TOOLS_DIR="tools"

if [ ! -d "$PROFILE_DIR" ]; then
    echo "Error: Profile directory $PROFILE_DIR not found."
    exit 1
fi

echo "============================================================"
echo "      Alpheus Performance Audit Report"
echo "      Generated on: $(date)"
echo "============================================================"
echo ""

# 1. Metrics Summary & Regression Check
echo "--- 1. Aggregated Metrics & Regression Check ---"
# Check against v3.4 baseline with a 10% threshold
python3 "$TOOLS_DIR/aggregate_jfr_metrics.py" --check --baseline v3.4 --threshold 10.0
echo ""

# 1b. Cold-Start Performance
if [ -f "$TOOLS_DIR/benchmark_cold_start.sh" ]; then
    echo "--- 1b. Cold-Start Performance Benchmark ---"
    CURRENT_COLD=$(bash "$TOOLS_DIR/benchmark_cold_start.sh" --value-only)
    echo "Current Cold-Start: ${CURRENT_COLD}ms"

    # Optional: Hardcoded baseline for cold-start (v3.4 approx 1400ms)
    BASELINE_COLD=1450
    THRESHOLD_COLD=15
    if [ -n "$CURRENT_COLD" ]; then
        DIFF_COLD=$((CURRENT_COLD - BASELINE_COLD))
        PERC_COLD=$((DIFF_COLD * 100 / BASELINE_COLD))
        if [ "$PERC_COLD" -gt "$THRESHOLD_COLD" ]; then
            echo "WARNING: Cold-start regression detected! (${PERC_COLD}% over baseline ${BASELINE_COLD}ms)"
        else
            echo "Cold-start performance is stable (${PERC_COLD}% vs baseline ${BASELINE_COLD}ms)."
        fi
    fi
    echo ""
fi

# Function to perform analysis for a set of versions
perform_hotspot_analysis() {
    local VERSIONS=$1
    local TYPE_LABEL=$2
    local EVENT=$3
    local VALUE_FIELD=$4
    local SUFFIX=$5

    echo "--- $TYPE_LABEL Hotspot Analysis ($EVENT) ---"

    if [ -z "$VERSIONS" ]; then
        echo "No JFR profiles found for $TYPE_LABEL."
    else
        for v in $VERSIONS; do
            local COLLAPSED="$PROFILE_DIR/collapsed_${v}${SUFFIX}.txt"
            local EXTRA_ARGS=""
            if [ -n "$VALUE_FIELD" ]; then
                EXTRA_ARGS="--value-field $VALUE_FIELD"
            fi

            # Ensure collapsed stack file exists and is up to date
            python3 "$TOOLS_DIR/aggregate_collapsed_stacks.py" "$PROFILE_DIR" "$v" "$EVENT" $EXTRA_ARGS > "$COLLAPSED" 2>/dev/null

            if [ -s "$COLLAPSED" ]; then
                python3 "$TOOLS_DIR/analyze_hotspots.py" "$COLLAPSED"
            else
                echo "Analysis for $v ($TYPE_LABEL):"
                echo "  No samples captured."
            fi
            echo ""
        done
    fi
}

# Extract unique versions (including large_)
if sort -V </dev/null >/dev/null 2>&1; then
    ALL_VERSIONS=$(ls "$PROFILE_DIR"/profile_*.jfr 2>/dev/null | sed -n 's/.*profile_\(\(large_\)\?v[0-9.]*\(_[a-z]*\)\?\)_run.*/\1/p' | sort -V | uniq)
else
    ALL_VERSIONS=$(ls "$PROFILE_DIR"/profile_*.jfr 2>/dev/null | sed -n 's/.*profile_\(\(large_\)\?v[0-9.]*\(_[a-z]*\)\?\)_run.*/\1/p' | sort -u)
fi

STD_VERSIONS=$(echo "$ALL_VERSIONS" | grep -v "large_")
LARGE_VERSIONS=$(echo "$ALL_VERSIONS" | grep "large_")

# 2. CPU Hotspot Analysis
perform_hotspot_analysis "$STD_VERSIONS" "Standard CPU" "jdk.ExecutionSample" "" ""
perform_hotspot_analysis "$LARGE_VERSIONS" "Large File CPU" "jdk.ExecutionSample" "" ""

# 3. Allocation Hotspot Analysis (Deep Dive)
perform_hotspot_analysis "$LARGE_VERSIONS" "Large File Allocation" "jdk.ObjectAllocationSample" "weight" "_alloc"

# 3b. Class-Loading Analysis
echo "--- 3b. Class-Loading Hotspot Analysis ---"
# Look for a JFR that likely has class loading data (e.g. from cold-start or a fresh run)
# Fallback to test_classload.jfr if it exists from a recent manual run
LATEST_JFR=$(ls -t "$PROFILE_DIR"/profile_*_run1.jfr test_classload.jfr 2>/dev/null | head -n 1)
if [ -n "$LATEST_JFR" ] && [ -f "$TOOLS_DIR/analyze_class_loading.py" ]; then
    echo "Analyzing $LATEST_JFR"
    python3 "$TOOLS_DIR/analyze_class_loading.py" "$LATEST_JFR" --top 10
else
    echo "No JFR profiles found for class-loading analysis."
fi
echo ""

# Function to find the first version with samples
find_baseline_with_samples() {
    local VERSIONS=$1
    local SUFFIX=$2
    for v in $VERSIONS; do
        local COLLAPSED="$PROFILE_DIR/collapsed_${v}${SUFFIX}.txt"
        if [ -s "$COLLAPSED" ]; then
            echo "$v"
            return
        fi
    done
}

# 4. Differential Analysis (Baseline vs Latest)
echo "--- 4. Differential Hotspot Analysis (Standard) ---"
LATEST_STD=$(echo "$STD_VERSIONS" | tail -n 1)
BASE_STD=$(find_baseline_with_samples "$STD_VERSIONS" "")

if [ -n "$LATEST_STD" ] && [ -n "$BASE_STD" ] && [ "$LATEST_STD" != "$BASE_STD" ]; then
    COLLAPSED_BASE="$PROFILE_DIR/collapsed_$BASE_STD.txt"
    COLLAPSED_LATEST="$PROFILE_DIR/collapsed_$LATEST_STD.txt"
    DIFF_FILE="$PROFILE_DIR/diff_${BASE_STD}_${LATEST_STD}.txt"

    echo "Comparing $LATEST_STD against baseline $BASE_STD"
    if [ -f "$COLLAPSED_BASE" ] && [ -f "$COLLAPSED_LATEST" ]; then
        python3 "$TOOLS_DIR/diff_collapsed_stacks.py" "$COLLAPSED_BASE" "$COLLAPSED_LATEST" > "$DIFF_FILE"
        python3 "$TOOLS_DIR/analyze_hotspots.py" "$DIFF_FILE" --check-drift --threshold 5.0
    else
        echo "Differential analysis skipped: Missing collapsed stacks."
    fi
else
    echo "Differential analysis skipped: Insufficient data or same version."
fi
echo ""

# 4b. Differential Analysis (Large Files)
echo "--- 4b. Differential Hotspot Analysis (Large Files) ---"
LATEST_LARGE_CPU=$(echo "$LARGE_VERSIONS" | tail -n 1)
BASE_LARGE_CPU=$(find_baseline_with_samples "$LARGE_VERSIONS" "")

if [ -n "$LATEST_LARGE_CPU" ] && [ -n "$BASE_LARGE_CPU" ] && [ "$LATEST_LARGE_CPU" != "$BASE_LARGE_CPU" ]; then
    COLLAPSED_BASE="$PROFILE_DIR/collapsed_$BASE_LARGE_CPU.txt"
    COLLAPSED_LATEST="$PROFILE_DIR/collapsed_$LATEST_LARGE_CPU.txt"
    DIFF_FILE="$PROFILE_DIR/diff_${BASE_LARGE_CPU}_${LATEST_LARGE_CPU}.txt"

    echo "Comparing $LATEST_LARGE_CPU against baseline $BASE_LARGE_CPU"
    if [ -f "$COLLAPSED_BASE" ] && [ -f "$COLLAPSED_LATEST" ]; then
        python3 "$TOOLS_DIR/diff_collapsed_stacks.py" "$COLLAPSED_BASE" "$COLLAPSED_LATEST" > "$DIFF_FILE"
        python3 "$TOOLS_DIR/analyze_hotspots.py" "$DIFF_FILE" --check-drift --threshold 5.0
    else
        echo "Differential analysis skipped: Missing collapsed stacks."
    fi
else
    echo "Differential analysis skipped: Insufficient data or same version."
fi
echo ""

# 5. Differential Allocation Analysis (Large Files)
BASE_LARGE=$(echo "$LARGE_VERSIONS" | head -n 1)
LATEST_LARGE=$(echo "$LARGE_VERSIONS" | tail -n 1)

echo "--- 5. Differential Allocation Analysis ($BASE_LARGE vs $LATEST_LARGE) ---"

if [ "$BASE_LARGE" != "$LATEST_LARGE" ] && [ -n "$BASE_LARGE" ] && [ -n "$LATEST_LARGE" ]; then
    COLLAPSED_ALLOC_BASE="$PROFILE_DIR/collapsed_${BASE_LARGE}_alloc.txt"
    COLLAPSED_ALLOC_LATEST="$PROFILE_DIR/collapsed_${LATEST_LARGE}_alloc.txt"
    DIFF_ALLOC_FILE="$PROFILE_DIR/diff_alloc_${BASE_LARGE}_${LATEST_LARGE}.txt"

    if [ -f "$COLLAPSED_ALLOC_BASE" ] && [ -f "$COLLAPSED_ALLOC_LATEST" ]; then
        python3 "$TOOLS_DIR/diff_collapsed_stacks.py" "$COLLAPSED_ALLOC_BASE" "$COLLAPSED_ALLOC_LATEST" > "$DIFF_ALLOC_FILE"
        python3 "$TOOLS_DIR/analyze_hotspots.py" "$DIFF_ALLOC_FILE" --check-drift --threshold 5.0
    else
        echo "Differential allocation analysis skipped: Missing profiles for $BASE_LARGE or $LATEST_LARGE"
    fi
else
    echo "Differential allocation analysis skipped: Need at least two large versions."
fi

echo "============================================================"
echo "      End of Audit Report"
echo "============================================================"
