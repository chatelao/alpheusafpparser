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
| **AFP GOCA** (afp-goca-reference-03) | Good | Consistent headers, drawing orders converted | Minor Chapter 7 drawing order remnants pending table conversion. |
| **BCOCA** (bcoca-reference-11) | Excellent | Purged of page markers, key syntax tables formatted | None significant. |
| **IOCA** (ioca-reference-09) | Excellent | Text is readable, structural descriptors converted | None significant. |
| **IPDS** (ipds-reference-12) | Good | Comprehensive content, key command tables formatted | Artifact purging and split-word fixes pending for Chapters 12-13, 17 and Appendices. |
| **Line Data** (linedata-reference-05) | Good | Basic structure present, Chapter 5 SFs converted | Remaining Appendix A diagrams (Figures 35-42) still require conversion. |

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

### Good Quality (Minor Cleanup Needed)
*   **IPDS Reference 12**: Significant progress. All command and syntax tables are converted. Major progress on artifact and page reference purging.
*   **AFP GOCA Reference 03**: Purged of page references. Drawing orders and attribute tables mostly converted to Markdown.
*   **Line Data Reference 05**: Significant improvement. Artifacts and page references purged. All Chapter 5 structured fields and early Appendix A diagrams converted.

---

## Verification of TOC.md
The `specifications/TOC.md` file was audited. All 10 specification links point to valid `Front_Matter.md` or primary entry files.

**Status**: Verified.
