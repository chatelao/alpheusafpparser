# Alpheus AFP Parser Encoding and Character Set Gap Analysis

This report evaluates the current state of encoding and character set support in the Alpheus AFP Parser, identifying architectural gaps and verifying specific character mapping logic.

---

## 1. General Encoding and Character Set Gaps

A critical testing gap exists in the verification of text extraction when using non-standard or state-switched character sets. Currently, many human-readable text sources in the codebase hardcode EBCDIC International (IBM-500) or rely on a global configuration that does not respect the stateful LID-to-Charset mapping defined by the AFP architecture.

### 1.1. Hardcoded IBM-500 Defaults
The following classes hardcode `Constants.cpIBM500` in their `getText()` methods, making them likely to fail when processing data in other encodings (e.g., German IBM-273):
*   **GOCA**: `GCOMT_Comment`, `GCCHST_CharacterStringAtCurrentPosition`, `GCHST_CharacterStringAtGivenPosition`.
*   **BCOCA**: `BDA_BarCodeData` (human-readable portion).
*   **IOCA**: `UnknownSegmentLong`, `UnknownSegmentExtended`.
*   **FOCA**: `CPD_CodePageDescriptor`, `FND_FontDescriptor` (descriptors/descriptions).
*   **PTOCA**: `Undefined` control sequence, `NOP_NoOperation`.

### 1.2. Global vs. Stateful Charset Gaps
Many classes use `config.getAfpCharSet()`, which defaults to IBM-500. While this is configurable, the parser lacks the **"Blind Execution" logic** required to dynamically switch the active Charset based on encountering dynamic font activation commands.
*   **PTOCA Stateful Gaps**: `TRN_TransparentData` and `GraphicCharacters` currently use the global config. They must be tested with dynamic switching via `SCFL` (Set Coded Font Local) commands that point to `MCF` (Map Coded Font) declarations.
*   **MO:DCA Triplets**: `FullyQualifiedName`, `AttributeValue`, `Comment`, and `ObjectClassification` all rely on the global configuration.
*   **Line Data**: `FDX_FixedDataText` and `CCP_RepeatingGroup` (comparison strings) rely on the global configuration.
*   **Base Classes**: `StructuredFieldBaseName` (for all Named SFs like BPG, BNG, etc.) uses the global configuration.

### 1.3. Multi-Byte and UTF-16BE Verification
While `UCT_UnicodeComplexText` and `CMR_ColorManagementResource` use `UTF-16BE`, coverage for these is minimal.
*   **Gap**: No tests verify that mixed-encoding streams (e.g., EBCDIC PTX followed by UTF-16BE UCT) are extracted correctly into XML.

### 1.4. Text Extraction Heuristics
The library recently added `MDRPTXXMLTest.java` to verify that `MDR` structured fields correctly influence the Charset used for subsequent `PTX` text extraction.
*   **Gap:** This stateful logic needs to be extended to support `SCFL` (Set Coded Font Local) commands within the `PTX` stream itself, which dynamically switches fonts/encodings.

### 1.5. Recommendations for Encoding Testing
1.  **Cross-Encoding Test Suite**: Create a test suite that processes the same logical text (e.g., "München") encoded in multiple Code Pages (IBM-500, IBM-273, IBM-1141) and verifies the XML `<text>` output is identical and correctly decoded.
2.  **LID-to-Charset Round-Trip**: Implement a test that uses `MCF` to map LID 1 to IBM-273 and LID 2 to IBM-500, then verifies that `SCFL` switches in `PTX` result in correct character resolution (e.g., byte `X'C0'` resolving to `ä` or `{` respectively).
3.  **Heuristic Validation**: Test `UtilCharacterEncoding.isHumanReadable` against various non-English EBCDIC strings to ensure it doesn't incorrectly flag them as binary.

---

## 2. Verification Case Study: IBM-273 German/Austrian Character Mapping

This section documents the verification of the "external pointer system" for character mapping in the Alpheus AFP Parser, specifically regarding the resolution of byte `X'C0'` using the German/Austrian Code Page (T1V10273) versus International EBCDIC (T1V10500).

### 2.1. Architectural Analysis: The External Pointer System

The logic described involves a decoupled resolution chain:
1.  **MCF (X'D3AB8A')**: Declares a **Font Local Identifier (LID)** and binds it to a **Code Page** (e.g., T1V10273) via **Triplet X'85'**.
2.  **SCFL (X'F0')**: Activates the LID within the PTOCA data stream.
3.  **Mapping**: The engine uses the active LID's Code Page to map raw bytes to **GCGIDs**.

#### Current Implementation Status

| Component | Status | Code Reference |
| :--- | :--- | :--- |
| **MCF Format 2** | ✅ Implemented | `MCF_MapCodedFont_Format2` |
| **Triplet X'02' Type X'85'** | ✅ Implemented | `Triplet.FullyQualifiedName` with `GlobalID_Use.CodePageNameReference` |
| **SCFL Control Sequence** | ✅ Implemented | `PTOCAControlSequence.SCFL_SetCodedFontLocal` |
| **Stateful Binding** | ❌ Missing | `AFPParserConfiguration` does not track LID-to-Charset mappings. |
| **Dynamic Resolution** | ❌ Missing | `PTX` and `TRN` decoding does not switch encodings based on `SCFL`. |

### 2.2. Syntax & Hex ID Discrepancies

#### SCFL Hex ID
The provided logic references **SCFL (X'D2')**. However, according to the **PTOCA Reference (AFPC-0005-04)** and the current implementation, the correct Function Type for **Set Coded Font Local (SCFL)** is **`X'F0'`** (or `X'F1'` if chained).
- `X'D2'` is assigned to **Absolute Move Baseline (AMB)**.

#### Triplet X'85' Syntax
The implementation correctly handles the **Fully Qualified Name (FQN)** triplet syntax for Code Page Name Reference (`X'85'`):
- **Length**: 1 byte
- **ID**: `X'02'` (Triplet ID)
- **Type**: `X'85'` (Code Page Name Reference)
- **Format**: `X'00'` (Character String)
- **Data**: 8-byte resource name (e.g., `T1V10273`)

### 2.3. Character Mapping Verification (Byte X'C0')

The following table demonstrates the mapping discrepancy for byte `X'C0'` depending on the active Code Page:

| Code Page | Resource Name | Byte | GCGID | Glyph |
| :--- | :--- | :--- | :--- | :--- |
| **IBM-273** | `T1V10273` | `X'C0'` | `LA040000` | `ä` |
| **IBM-500** | `T1V10500` | `X'C0'` | `SM110000` | `{` |

### 2.4. Real-world Case Study: TRN Transparent Data Mismatch

A reported issue confirmed that German text in a `TRN_TransparentData` control sequence was incorrectly decoded using IBM-500, resulting in character mismatches for common German umlauts.

**Data Example:** `F}r Sie pers¦nlich zust{ndig:` (as seen in XML)
**Raw Bytes (Hex):** `c6 d0 99 40 e2 89 85 40 97 85 99 a2 6a 95 93 89 83 88 40 a9 a4 a2 a3 c0 95 84 89 87 7a`

| Byte | IBM-500 (Current) | IBM-273 (Expected) |
| :--- | :--- | :--- |
| `X'D0'` | `}` | `ü` |
| `X'6A'` | `¦` | `ö` |
| `X'C0'` | `{` | `ä` |

**Resulting String (IBM-500):** `F}r Sie pers¦nlich zust{ndig:`
**Resulting String (IBM-273):** `Für Sie persönlich zuständig:`

This case confirms the gap identified in Section 1.2: `TRN_TransparentData` relies on the global configuration and does not yet participate in the stateful "Blind Execution" logic required for correct localized text extraction.

#### Engine Behavior
Currently, the `getText()` methods in `TRN_TransparentData` and `GraphicCharacters` rely on:
1.  A hardcoded default (often `cp500`).
2.  The global `afpCharSet` configured in `AFPParserConfiguration`.

**The engine does not yet implement the "Blind Execution" logic** where it would dynamically switch the Charset based on the most recently encountered `SCFL` command and its associated `MCF` declaration.

### 2.4. Gap Summary
While the structural components (MCF, SCFL, FQN) are successfully parsed into an object model, the **semantic link** between them is not enforced during text extraction. To achieve the described logic, the `AFPParser` would need to maintain a stateful `FontManager` or similar context that updates the active `Charset` for the `PTX` parser upon encountering `SCFL`.
