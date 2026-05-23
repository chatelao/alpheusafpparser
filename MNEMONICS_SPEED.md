# Mnemonics Speed Profiling Results

This file contains the performance metrics for various AFP mnemonics (Structured Fields, Control Sequences, and Drawing Orders) gathered from running the project's test files.

**Profiling Environment:**
- **Date:** 2026-05-23
- **Writer:** JAXB Streaming Writer
- **Input:** 143 .afp files from the project's test resources

## Performance Summary per Mnemonic

| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
|----------|-------|------------|------------|------------|
| BRS      | 2     | 37         | 35         | 2          |
| BDT      | 134   | 16         | 3          | 13         |
| EDT      | 133   | 14         | 2          | 12         |
| PTX      | 1     | 9          | 9          | 0          |
| MDD      | 4     | 4          | 0          | 4          |
| TRN      | 93    | 1          | 1          | 0          |
| BDG      | 0     | 0          | 0          | 0          |
| BFM      | 0     | 0          | 0          | 0          |
| BMM      | 2     | 0          | 0          | 0          |
| BRG      | 2     | 0          | 0          | 0          |
| EDG      | 0     | 0          | 0          | 0          |
| MCC      | 2     | 0          | 0          | 0          |
| PGP      | 4     | 0          | 0          | 0          |

*Note: Mnemonics with 0ms total time may have taken less than 1ms.*
