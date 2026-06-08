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

# 2. Hotspot Analysis for each version
echo "--- 2. Hotspot Analysis (CPU Samples) ---"

# Extract unique versions from filenames: profile_v15.6_run1.jfr -> v15.6
# Use sort -V for natural version sorting if available, otherwise fallback to sort -u
if sort -V </dev/null >/dev/null 2>&1; then
    VERSIONS=$(ls "$PROFILE_DIR"/profile_*.jfr 2>/dev/null | sed -n 's/.*profile_\(v[0-9.]*\)_run.*/\1/p' | sort -V | uniq)
else
    VERSIONS=$(ls "$PROFILE_DIR"/profile_*.jfr 2>/dev/null | sed -n 's/.*profile_\(v[0-9.]*\)_run.*/\1/p' | sort -u)
fi

if [ -z "$VERSIONS" ]; then
    echo "No JFR profiles found in $PROFILE_DIR"
else
    for v in $VERSIONS; do
        COLLAPSED="$PROFILE_DIR/collapsed_$v.txt"

        # Ensure collapsed stack file exists and is up to date
        python3 "$TOOLS_DIR/aggregate_collapsed_stacks.py" "$PROFILE_DIR" "$v" > "$COLLAPSED" 2>/dev/null

        if [ -s "$COLLAPSED" ]; then
            python3 "$TOOLS_DIR/analyze_hotspots.py" "$COLLAPSED"
        else
            echo "Analysis for $v:"
            echo "  No CPU samples captured (possibly execution time too short for 10ms sampling)."
        fi
        echo ""
    done
fi

# 3. Differential Analysis (Baseline v3.4 vs Latest)
echo "--- 3. Differential Hotspot Analysis (v3.4 vs Latest) ---"
LATEST=$(echo "$VERSIONS" | tail -n 1)
BASE="v3.4"

if [ "$LATEST" != "$BASE" ] && [ -n "$LATEST" ]; then
    COLLAPSED_BASE="$PROFILE_DIR/collapsed_$BASE.txt"
    COLLAPSED_LATEST="$PROFILE_DIR/collapsed_$LATEST.txt"
    DIFF_FILE="$PROFILE_DIR/diff_${BASE}_${LATEST}.txt"

    if [ -f "$COLLAPSED_BASE" ] && [ -f "$COLLAPSED_LATEST" ]; then
        python3 "$TOOLS_DIR/diff_collapsed_stacks.py" "$COLLAPSED_BASE" "$COLLAPSED_LATEST" > "$DIFF_FILE"
        python3 "$TOOLS_DIR/analyze_hotspots.py" "$DIFF_FILE"
    else
        echo "Differential analysis skipped: Missing collapsed stacks for $BASE or $LATEST"
    fi
else
    echo "Differential analysis skipped: Only one version or $BASE is the latest."
fi

echo "============================================================"
echo "      End of Audit Report"
echo "============================================================"
