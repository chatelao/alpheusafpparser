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

# 1. Metrics Summary (Table)
echo "--- 1. Aggregated Metrics Summary ---"
python3 "$TOOLS_DIR/aggregate_jfr_metrics.py"
echo ""

# 2. Hotspot Analysis for each version
echo "--- 2. Hotspot Analysis (CPU Samples) ---"

# Extract unique versions from filenames: profile_v15.6_run1.jfr -> v15.6
VERSIONS=$(ls "$PROFILE_DIR"/profile_*.jfr 2>/dev/null | sed -n 's/.*profile_\(v[0-9.]*\)_run.*/\1/p' | sort -u)

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

echo "============================================================"
echo "      End of Audit Report"
echo "============================================================"
