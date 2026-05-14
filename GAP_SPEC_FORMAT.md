# Specification Formatting Quality Report

This report evaluates the quality of Markdown conversions for the AFP Architecture Specifications located in `specifications/markdown/`.

To know the quality, compare the result to the original PDF resports to find the original structures.

## Summary Table

| Specification | Quality | Key Strengths | Primary Issues |
| :--- | :--- | :--- | :--- |
| **MO:DCA** (modca-reference-10) | Excellent | Structured tables, clear hierarchy, purged artifacts | None significant. |
| **PTOCA** (ptoca-reference-04) | Excellent | Purged of OCR artifacts, LaTeX math, structured tables | None significant. |
| **CMOCA** (cmoca-reference-02) | Excellent | Clean syntax tables, minimal PDF artifacts | None significant. |
| **FOCA** (foca-reference-06) | Excellent | Proper table formatting, clear sections | None significant. |
| **MOCA** (moca-reference-02) | Excellent | Cleanest conversion, no artifacts detected | None. |
| **Db2 12 for z/OS: Internationalization Guide** (db2z_12_charbook) | Excellent | Purged of artifacts, OCR errors fixed, tables converted | None significant. |
| **AFP GOCA** (afp-goca-reference-03) | Excellent | Consistent headers, drawing orders converted to tables | None significant. |
| **BCOCA** (bcoca-reference-11) | Excellent | Purged of page markers, key syntax tables formatted | None significant. |
| **IOCA** (ioca-reference-09) | Excellent | Text is readable, structural descriptors converted | None significant. |
| **IPDS** (ipds-reference-12) | Excellent | Purged of artifacts, all command and syntax tables formatted | None significant. |
| **Line Data** (linedata-reference-05) | Excellent | Artifacts purged, Chapter 5 SFs and Appendix A diagrams converted | None. |

---

## Evaluation Criteria

1.  **Heading Hierarchy**: Use of `#`, `##`, `###` to reflect document structure.
2.  **Artifacts**: Presence of PDF page numbers, headers/footers, and OCR-induced split words (e.g., `af fect`).
3.  **Table Formatting**: Use of GFM tables (`| --- |`) for syntax and registry data vs. plain text dumps.
4.  **Mathematical Notation**: Use of LaTeX-style notation for coordinate systems and subscripts.
5.  **Cross-References**: Removal of physical page references (e.g., "on page 45") in favor of relative links or names.

---

## Detailed Findings

### Excellent Quality (Standardized)
*   **PTOCA Reference 04**: Purged of OCR artifacts and page references. Tables fully converted and LaTeX math implemented.
*   **MOCA Reference 02**: Extremely clean. Manual verification complete.
*   **CMOCA Reference 02**: Standardized registry and syntax tables. Clean Chapter structure.
*   **MO:DCA Reference 10**: Fully cleaned; all artifacts and page references purged.
*   **BCOCA Reference 11**: Purged of artifacts and page references. All Chapter 4 and Appendix tables are formatted.
*   **IOCA Reference 09**: Artifacts and page references purged. All structural descriptors and Function Sets converted to tables.
*   **FOCA Reference 06**: Fully cleaned; OCR issues fixed and page references removed.
*   **Db2 12 Internationalization Guide**: Purged of artifacts and OCR errors. All chapters and appendices have tables converted.
*   **IPDS Reference 12**: Fully cleaned; all command and syntax tables are converted. All artifacts and page references purged.
*   **AFP GOCA Reference 03**: Purged of page references. All drawing orders and attribute tables in Chapter 7 converted to Markdown tables.
*   **Line Data Reference 05**: Fully cleaned; artifacts and page references purged. All Chapter 5 structured fields and Appendix A diagrams converted.

---

## Verification of TOC.md
The `specifications/TOC.md` file was audited. All 10 specification links point to valid `Front_Matter.md` or primary entry files.

**Status**: Verified.
