# PDF ISO 32000-2 Cleanup Roadmap

This document outlines the phased cleanup of the Markdown files in `/specifications/markdown/ISO_32000-2_sponsored-ec2` to ensure structural and visual fidelity with the source PDF `/specifications/ISO_32000-2_sponsored-ec2.pdf`.

## Objective
Restore the visual and semantic structure of the ISO 32000-2 specification in Markdown format, addressing conversion artifacts and ensuring consistency across all chapters and appendices.

## Cleanup Criteria
The following criteria must be verified for each file:

1.  **Tables**:
    - Merge fragmented tables that were split during conversion.
    - Ensure correct cell alignment and header rows.
    - Fix broken multi-line cell content.
2.  **Notes & Examples**:
    - Standardize formatting to `> **NOTE**` or `> **EXAMPLE**`.
    - Ensure notes are properly blocked and not merged with regular paragraph text.
3.  **Formulas**:
    - Repair fragmented mathematical expressions (e.g., text matrix calculations in Chapter 9).
    - Use LaTeX-style formatting where appropriate for complex expressions.
4.  **Lists**:
    - Correct indentation for nested lists.
    - Ensure bullet points and numbered lists match the PDF structure.
5.  **Headings**:
    - Verify heading levels (H1-H6) match the PDF Table of Contents.
    - Ensure consistent numbering and spacing.
6.  **Cross-References**:
    - Fix broken internal links to tables, figures, and subclauses.

## Cleanup Phases

### Phase 1: Front Matter & Intro 🚧
- `Front_Matter.md`
- ✅ `Chapter_1.md` (Scope)
- ✅ `Chapter_2.md` (Normative references)
- ✅ `Chapter_3.md` (Terms and definitions)
- ✅ `Chapter_4.md` (Notation)
- ✅ `Chapter_5.md` (Version designations)
- ✅ `Chapter_6.md` (Conformance)

### Phase 2: Syntax & Graphics ⏳
- ⏳ **2.1. Chapter 7**: Syntax (broken tables and figures).
- ⏳ **2.2. Chapter 8**: Graphics (coordinate systems and patterns).

### Phase 3: Text, Rendering & Transparency ⏳
- ⏳ **3.1. Chapter 9**: Text (fragmented formulas and notes).
- ⏳ **3.2. Chapter 10**: Rendering.
- ⏳ **3.3. Chapter 11**: Transparency.

### Phase 4: Interactive & Multimedia ⏳
- ⏳ **4.1. Chapter 12**: Interactive features.
- ⏳ **4.2. Chapter 13**: Multimedia features.

### Phase 5: Document Interchange & Parts ⏳
- ⏳ **5.1. Chapter 14**: Document interchange (large tables and examples).

### Phase 6: Appendices & Bibliography ⏳
- ⏳ **6.1. Appendix A to E**: Basic structural cleanup.
- ⏳ **6.2. Appendix F to J**: Complex tables and examples.
- ⏳ **6.3. Appendix K to O**: Technical relationships and fragments.
- ⏳ **6.4. Appendix P to Q & Bibliography**: Final mathematical algorithms.

## Status Summary

| File | Status | Phase | Notes |
| :--- | :--- | :--- | :--- |
| `Front_Matter.md` | ⏳ Pending | 1 | |
| `Chapter_1.md` | ✅ Completed | 1 | Formatted "does not specify" list. |
| `Chapter_2.md` | ✅ Completed | 1 | Merged fragmented references and fixed tables. |
| `Chapter_3.md` | ✅ Completed | 1 | Bolded terms and fixed line breaks. |
| `Chapter_4.md` | ✅ Completed | 1 | Fixed headings and merged paragraphs. |
| `Chapter_5.md` | ✅ Completed | 1 | Verified as clean. |
| `Chapter_6.md` | ✅ Completed | 1 | Fixed paragraph breakage in 6.3.2.1. |
| `Chapter_7.md` | ⏳ Pending | 2.1 | Verified: Broken tables and figures. |
| `Chapter_8.md` | ⏳ Pending | 2.2 | |
| `Chapter_9.md` | ⏳ Pending | 3.1 | Verified: Fragmented formulas and notes. |
| `Chapter_10.md` | ⏳ Pending | 3.2 | |
| `Chapter_11.md` | ⏳ Pending | 3.3 | |
| `Chapter_12.md` | ⏳ Pending | 4.1 | |
| `Chapter_13.md` | ⏳ Pending | 4.2 | |
| `Chapter_14.md` | ⏳ Pending | 5.1 | Verified: Large tables and examples. |
| `Appendix_A.md` | ⏳ Pending | 6.1 | |
| `Appendix_B.md` | ⏳ Pending | 6.1 | |
| `Appendix_C.md` | ⏳ Pending | 6.1 | |
| `Appendix_D.md` | ⏳ Pending | 6.1 | |
| `Appendix_E.md` | ⏳ Pending | 6.1 | |
| `Appendix_F.md` | ⏳ Pending | 6.2 | |
| `Appendix_G.md` | ⏳ Pending | 6.2 | |
| `Appendix_H.md` | ⏳ Pending | 6.2 | |
| `Appendix_I.md` | ⏳ Pending | 6.2 | |
| `Appendix_J.md` | ⏳ Pending | 6.2 | |
| `Appendix_K.md` | ⏳ Pending | 6.3 | |
| `Appendix_L.md` | ⏳ Pending | 6.3 | |
| `Appendix_M.md` | ⏳ Pending | 6.3 | |
| `Appendix_N.md` | ⏳ Pending | 6.3 | |
| `Appendix_O.md` | ⏳ Pending | 6.3 | |
| `Appendix_P.md` | ⏳ Pending | 6.4 | |
| `Appendix_Q.md` | ⏳ Pending | 6.4 | |
| `Bibliography.md` | ⏳ Pending | 6.4 | |

---
*Roadmap created June 2026.*
