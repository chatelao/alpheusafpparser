# PTOCA Visibility Gap Analysis

This document identifies where missing visible texts in the final XML could be hidden and not yet extracted from PTOCA (Presentation Text Object Content Architecture) structured fields.

## 1. Free-standing Graphic Characters (Major Gap)

**Rule:** PTOCA Reference 04, Chapter 4, "Graphic Character Processing".
Graphic character code points can appear directly in the `PTX` (Presentation Text Data) structured field, interleaved with control sequences.

**Status:** Not Implemented.
**Findings:** The `PTOCAControlSequenceParser.java` currently assumes the entire `PTX` payload consists of control sequences starting with a Control Sequence Introducer (CSI), typically identified by the `X'2B'` prefix. If a `PTX` starts with or contains free-standing graphic characters (e.g., EBCDIC text), the parser will either:
- Attempt to parse the text as a CSI, leading to `ArrayIndexOutOfBoundsException` or `AFPParserException` (failed to instantiate class).
- Skip the data if it doesn't match the expected `X'2BD3'` pattern.

**Recommendation:** Update `PTOCAControlSequenceParser` to detect non-`X'2B'` bytes and treat them as a "GraphicCharacter" run until the next `X'2B'` or the end of the field.

## 2. Complex Text Glyph Runs (PT4 Gap)

**Rule:** PTOCA Reference 04, Chapter 4, "Glyph Layout Control (GLC)", "Glyph ID Run (GIR)", etc.
PT4 introduced glyph runs where text is rendered via Glyph IDs rather than code points.

**Status:** Not Implemented.
**Findings:**
- `GLC` (X'6D'), `GIR` (X'8B'), `GAR` (X'8C/8D'), `GOR` (X'8E/8F') are not implemented.
- These sequences carry glyph IDs and positions. While glyph IDs themselves aren't "text" in a traditional sense, a `GLC` chain often ends with an optional `UCT` (Unicode Complex Text) sequence which *does* contain the original Unicode text for accessibility and searching.

**Recommendation:** Implement the PT4 control sequence classes. Ensure `UCT` within a `GLC` chain is extracted as text.

## 3. Control Sequences with "Hidden" Text

Several control sequences contain text or text-like data that is partially or not extracted.

### SEA (Set Encrypted Alternate)
- **Rule:** Contains `ALTTEXT` used if decryption fails.
- **Status:** Partially Implemented. `SEA_SetEncryptedAlternate.getText()` exists but assumes CP500.
- **Gap:** `SEA` controls can be concatenated. The current implementation only extracts text from individual `SEA` blocks, not the merged payload.

### NOP (No Operation)
- **Rule:** Contains `IGNDATA` which can be any bytes, often used for comments or "hidden" human-readable text.
- **Status:** Partially Implemented. `NOP_NoOperation.getText()` exists but assumes CP500.

### OVS (Overstrike)
- **Rule:** Contains `OVERCHAR` (the character used for overstriking).
- **Status:** Partially Implemented. The code point is stored, but not exposed as a `text` element in XML.

### ENC (Encrypted Data)
- **Rule:** Contains `ENCDATA`.
- **Status:** Data stored as raw bytes.
- **Gap:** Decryption is not possible without keys (SKI), but the architecture allows for identifying these blocks. Once decrypted, they follow standard text rules.

## 4. Encoding and Code Page Issues

**Rule:** PTOCA text interpretation depends on the "active coded font".
**Findings:**
- Most `getText()` implementations (e.g., in `TRN`, `RPS`, `BSU`, `ESU`) use a hardcoded heuristic or `Constants.cpIBM500`.
- The parser has access to `AFPParserConfiguration.getAfpCharSet()`, but many `PTOCAControlSequence` subclasses do not use it or use it inconsistently.
- `UCT` (Unicode Complex Text) correctly uses UTF-16BE.

## Summary Table: PTOCA Reference 04 End-to-End Verification

| Control Sequence | Mnemonic | Status | Visibility in XML |
| :--- | :--- | :--- | :--- |
| Absolute Move Baseline | AMB | OK | Metadata only |
| Absolute Move Inline | AMI | OK | Metadata only |
| Begin Line | BLN | OK | Structural |
| Begin Suppression | BSU | OK | Text extracted (CP500) |
| Draw B-Axis Rule | DBR | OK | Graphic |
| Draw I-Axis Rule | DIR | OK | Graphic |
| Encrypted Data | ENC | OK | Raw bytes only |
| End Suppression | ESU | OK | Text extracted (CP500) |
| Glyph Advance Run | GAR | **MISSING** | No |
| Glyph ID Run | GIR | **MISSING** | No |
| Glyph Layout Control | GLC | **MISSING** | No (FFONTNME is hidden) |
| Glyph Offset Run | GOR | **MISSING** | No |
| No Operation | NOP | OK | Text extracted (CP500) |
| Relative Move Baseline | RMB | OK | Metadata only |
| Relative Move Inline | RMI | OK | Metadata only |
| Repeat String | RPS | OK | Text extracted (CP500) |
| Transparent Data | TRN | OK | Text extracted (Config charset) |
| Unicode Complex Text | UCT | OK | Text extracted (UTF-16BE) |
| Set Baseline Increment | SBI | OK | Metadata |
| Set Coded Font Local | SCFL | OK | Metadata |
| Set Encrypted Alternate | SEA | OK | Text extracted (CP500) |
| Set Extended Text Color | SEC | OK | Metadata |
| Set Intercharacter Adj | SIA | OK | Metadata |
| Set Inline Margin | SIM | OK | Metadata |
| Set Key Information | SKI | OK | Metadata |
| Set Text Color | STC | OK | Metadata |
| Set Text Orientation | STO | OK | Metadata |
| Set Var Space Inc | SVI | OK | Metadata |
| Temporary Baseline Move | TBM | OK | Metadata |
| Underscore | USC | OK | Metadata |
| Overstrike | OVS | OK | Metadata (Code point hidden) |
| Graphic Characters | - | **MISSING** | No (Parsing failure) |

## Conclusion

Visible text can be lost in the following areas:
1. **Free-standing text** in `PTX` fields is completely ignored or causes errors.
2. **GLC Full Font Names** and **UCT metadata** within GLC chains.
3. **Concatenated SEA/ENC** payloads (only first block might be seen).
4. **Incorrect encoding** application for non-CP500 text.
