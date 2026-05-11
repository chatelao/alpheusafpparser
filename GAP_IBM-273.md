# Verification Report: IBM-273 German/Austrian Character Mapping Logic

This report documents the verification of the "external pointer system" for character mapping in the Alpheus AFP Parser, specifically regarding the resolution of byte `X'C0'` using the German/Austrian Code Page (T1V10273) versus International EBCDIC (T1V10500).

## 1. Architectural Analysis: The External Pointer System

The logic described involves a decoupled resolution chain:
1.  **MCF (X'D3AB8A')**: Declares a **Font Local Identifier (LID)** and binds it to a **Code Page** (e.g., T1V10273) via **Triplet X'85'**.
2.  **SCFL (X'F0')**: Activates the LID within the PTOCA data stream.
3.  **Mapping**: The engine uses the active LID's Code Page to map raw bytes to **GCGIDs**.

### Current Implementation Status

| Component | Status | Code Reference |
| :--- | :--- | :--- |
| **MCF Format 2** | ✅ Implemented | `MCF_MapCodedFont_Format2` |
| **Triplet X'02' Type X'85'** | ✅ Implemented | `Triplet.FullyQualifiedName` with `GlobalID_Use.CodePageNameReference` |
| **SCFL Control Sequence** | ✅ Implemented | `PTOCAControlSequence.SCFL_SetCodedFontLocal` |
| **Stateful Binding** | ❌ Missing | `AFPParserConfiguration` does not track LID-to-Charset mappings. |
| **Dynamic Resolution** | ❌ Missing | `PTX` and `TRN` decoding does not switch encodings based on `SCFL`. |

## 2. Syntax & Hex ID Discrepancies

### SCFL Hex ID
The provided logic references **SCFL (X'D2')**. However, according to the **PTOCA Reference (AFPC-0005-04)** and the current implementation, the correct Function Type for **Set Coded Font Local (SCFL)** is **`X'F0'`** (or `X'F1'` if chained).
- `X'D2'` is assigned to **Absolute Move Baseline (AMB)**.

### Triplet X'85' Syntax
The implementation correctly handles the **Fully Qualified Name (FQN)** triplet syntax for Code Page Name Reference (`X'85'`):
- **Length**: 1 byte
- **ID**: `X'02'` (Triplet ID)
- **Type**: `X'85'` (Code Page Name Reference)
- **Format**: `X'00'` (Character String)
- **Data**: 8-byte resource name (e.g., `T1V10273`)

## 3. Character Mapping Verification (Byte X'C0')

The following table demonstrates the mapping discrepancy for byte `X'C0'` depending on the active Code Page:

| Code Page | Resource Name | Byte | GCGID | Glyph |
| :--- | :--- | :--- | :--- | :--- |
| **IBM-273** | `T1V10273` | `X'C0'` | `LA040000` | `ä` |
| **IBM-500** | `T1V10500` | `X'C0'` | `SM110000` | `{` |

### Engine Behavior
Currently, the `getText()` methods in `TRN_TransparentData` and `GraphicCharacters` rely on:
1.  A hardcoded default (often `cp500`).
2.  The global `afpCharSet` configured in `AFPParserConfiguration`.

**The engine does not yet implement the "Blind Execution" logic** where it would dynamically switch the Charset based on the most recently encountered `SCFL` command and its associated `MCF` declaration.

## 4. Gap Summary
While the structural components (MCF, SCFL, FQN) are successfully parsed into an object model, the **semantic link** between them is not enforced during text extraction. To achieve the described logic, the `AFPParser` would need to maintain a stateful `FontManager` or similar context that updates the active `Charset` for the `PTX` parser upon encountering `SCFL`.
