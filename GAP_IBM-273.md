# Alpheus AFP Parser Encoding and Character Set Gap Analysis

This report evaluates the current state of encoding and character set support in the Alpheus AFP Parser, identifying architectural gaps and verifying specific character mapping logic.

---

## 1. General Encoding and Character Set Gaps

A critical testing gap exists in the verification of text extraction when using non-standard or state-switched character sets. Currently, many human-readable text sources in the codebase hardcode EBCDIC International (IBM-500) or rely on a global configuration that does not respect the stateful LID-to-Charset mapping defined by the AFP architecture.

### 1.1. Hardcoded IBM-500 Defaults (Resolved)
The hardcoded `Constants.cpIBM500` defaults in `getText()` methods have been resolved. The following classes now use the stateful `config.getAfpCharSet()`:
*   **GOCA**: `GCOMT_Comment`, `GCCHST_CharacterStringAtCurrentPosition`, `GCHST_CharacterStringAtGivenPosition`, `GEAR_EndArea`.
*   **BCOCA**: `BDA_BarCodeData` (human-readable portion).
*   **IOCA**: `UnknownSegmentLong`, `UnknownSegmentExtended`.
*   **FOCA**: `CPD_CodePageDescriptor`, `FND_FontDescriptor` (descriptors/descriptions).
*   **PTOCA**: `Undefined` control sequence, `NOP_NoOperation`.

### 1.2. Global vs. Stateful Charset Gaps (Mostly Resolved)
The parser now supports **"Blind Execution" logic** to dynamically switch the active Charset based on encountering dynamic font activation commands.
*   **PTOCA Stateful Switching**: `TRN_TransparentData` and `GraphicCharacters` now correctly switch encodings based on `SCFL` (Set Coded Font Local) commands. Verified by `StatefulEncodingTest.java`.
*   **GOCA Stateful Switching**: `GSCS_SetCharacterSet` drawing orders now dynamically update the parser's active charset using the LID-to-Charset mapping.
*   **MO:DCA Triplets**: `FullyQualifiedName`, `AttributeValue`, `Comment`, and `ObjectClassification` use the global configuration, which now tracks state during PTOCA/GOCA parsing.
*   **Line Data**: `FDX_FixedDataText` and `CCP_RepeatingGroup` (comparison strings) rely on the configuration.
*   **Base Classes**: `StructuredFieldBaseName` (for all Named SFs like BPG, BNG, etc.) uses the stateful configuration.

### 1.3. Multi-Byte and UTF-16BE Verification (Resolved)
While `UCT_UnicodeComplexText` and `CMR_ColorManagementResource` use `UTF-16BE`, coverage for these has been improved.
*   **Verification**: `CrossEncodingTest.java` verifies that mixed-encoding streams (e.g., EBCDIC PTX followed by UTF-16BE UCT) are extracted correctly into XML.

### 1.4. Text Extraction Heuristics (Resolved)
The library uses `MDRPTXXMLTest.java` to verify that `MDR` structured fields influence text extraction, and `StatefulEncodingTest.java` to verify that `SCFL` (Set Coded Font Local) commands within the `PTX` stream dynamically switch encodings.

### 1.5. Recommendations for Encoding Testing (Resolved)
1.  **Cross-Encoding Test Suite (Resolved)**: Implemented in `CrossEncodingTest.java`. Processes logical text ("München") encoded in multiple Code Pages (IBM-500, IBM-273, IBM-1141) and verifies the XML output is correctly decoded.
2.  **LID-to-Charset Round-Trip (Resolved)**: Implemented in `StatefulEncodingTest.java`.
3.  **Heuristic Validation (Resolved)**: Implemented in `HeuristicValidationTest.java`. Verifies that `UtilCharacterEncoding.isHumanReadable` correctly accounts for EBCDIC control characters like Next Line (NEL).

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
| **Stateful Binding** | ✅ Implemented | `AFPParserConfiguration` now tracks LID-to-Charset mappings. |
| **Dynamic Resolution** | ✅ Implemented | `PTX` and `TRN` decoding switches encodings based on `SCFL`. |

### 2.2. Verification & Testing

The stateful encoding logic has been verified by the following tests:
*   `StatefulEncodingTest.java`: Validates that `SCFL` correctly switches between IBM-273 and IBM-500 charsets within a `PTX` stream, ensuring byte `X'C0'` resolves to `ä` and `{` respectively.
*   `MDRPTXXMLTest.java`: Verifies that `MDR` structured fields correctly influence the charset used for subsequent `PTX` text extraction.

### 2.3. Syntax & Hex ID Discrepancies

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

This case study is now **Resolved**. `TRN_TransparentData` and `GraphicCharacters` now participate in the stateful "Blind Execution" logic.

#### Engine Behavior
The `getText()` methods in `TRN_TransparentData` and `GraphicCharacters` now correctly utilize:
1.  The stateful `afpCharSet` configured in `AFPParserConfiguration`.
2.  The "Blind Execution" logic where the engine dynamically switches the Charset based on the most recently encountered `SCFL` (PTOCA) or `GSCS` (GOCA) command and its associated `MCF` or `MDR` declaration.

### 2.4. Gap Summary (Resolved)
The **semantic link** between MCF/MDR declarations and subsequent data stream commands (SCFL, GSCS) is now enforced during text extraction. `AFPParserConfiguration` maintains a `codedFontLocalIdToCharsetMap` that is utilized by both the PTOCA and GOCA parsers to ensure correct localized text resolution.
