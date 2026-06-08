#!/bin/bash

# Cold-start performance benchmark for afp2xml CLI
# Measures execution time of fresh JVM instances [DECOUPLE-4.2.4]

ITERATIONS=5
TEST_FILE="${1:-src/test/resources/afp/large_ibm273.afp}"
JAR_FILE=$(ls build/libs/*.jar 2>/dev/null | head -n 1)

if [ ! -f "$JAR_FILE" ]; then
    echo "Error: shadowJar not found. Please run ./gradlew shadowJar first."
    exit 1
fi

if [ ! -f "$TEST_FILE" ]; then
    echo "Error: Test file $TEST_FILE not found."
    exit 1
fi

echo "Benchmarking cold-start for $JAR_FILE"
echo "Test file: $TEST_FILE ($(du -h "$TEST_FILE" | cut -f1))"
echo "Iterations: $ITERATIONS"
echo "--------------------------------------------------"

TOTAL_TIME=0
MIN_TIME=999999
MAX_TIME=0

for i in $(seq 1 $ITERATIONS); do
    START_TIME=$(date +%s%3N)
    java -jar "$JAR_FILE" "$TEST_FILE" /dev/null > /dev/null 2>&1
    END_TIME=$(date +%s%3N)

    DURATION=$((END_TIME - START_TIME))
    echo "Run $i: ${DURATION}ms"

    TOTAL_TIME=$((TOTAL_TIME + DURATION))

    if [ "$DURATION" -lt "$MIN_TIME" ]; then MIN_TIME=$DURATION; fi
    if [ "$DURATION" -gt "$MAX_TIME" ]; then MAX_TIME=$DURATION; fi
done

AVG_TIME=$((TOTAL_TIME / ITERATIONS))

echo "--------------------------------------------------"
echo "Average Cold-Start Time: ${AVG_TIME}ms"
echo "Min Time: ${MIN_TIME}ms"
echo "Max Time: ${MAX_TIME}ms"
