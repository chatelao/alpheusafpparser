# Specification Formatting Quality Report

This report evaluates the quality of Markdown conversions for the AFP Architecture Specifications located in `specifications/markdown/`.

To know the quality, compare the result to the original PDF resports to find the original structures.

## Summary Table

| Specification | Quality | Key Strengths | Primary Issues |
| :--- | :--- | :--- | :--- |
| **MO:DCA** (modca-reference-10) | Excellent | Structured tables, clear hierarchy, purged artifacts | Minor table fragmentation in Appendices. |
| **PTOCA** (ptoca-reference-04) | Excellent | Purged of OCR artifacts, LaTeX math, structured tables | Minor split-word remnants. |
| **CMOCA** (cmoca-reference-02) | Excellent | Clean syntax tables, minimal PDF artifacts | None significant. |
| **FOCA** (foca-reference-06) | Good | Proper table formatting, clear sections | Minor OCR text merging in dense areas. |
| **MOCA** (moca-reference-02) | Excellent | Cleanest conversion, no artifacts detected | None. |
| **AFP GOCA** (afp-goca-reference-03) | Fair | Consistent headers | Heavy "on page XXX" cross-references. |
| **BCOCA** (bcoca-reference-11) | Good | Purged of page markers, key syntax tables formatted | Some Chapter 4 tables and Appendices still need formatting. |
| **IOCA** (ioca-reference-09) | Fair | Text is readable | Missing Markdown table markers for many structures. |
| **IPDS** (ipds-reference-12) | Fair | Comprehensive content, key command tables formatted | Heavy "on page XXX" references and remaining text-dump tables. |
| **Line Data** (linedata-reference-05) | Poor | Basic structure present | Explicit `## Page XX` markers in Appendices, diagrams as text. |

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
*   **PTOCA Reference 04**: Follows the highest standard. Uses LaTeX for math ($X_p, Y_p$). Tables are fully converted. PDF artifacts are purged.
*   **MOCA Reference 02**: Extremely clean. Manual verification complete.
*   **CMOCA Reference 02**: Standardized registry and syntax tables. Clean Chapter structure.
*   **MO:DCA Reference 10**: High quality across all chapters and appendices. Physical page references have been removed.

### Good Quality (Minor Cleanup Needed)
*   **BCOCA Reference 11**: Significant progress. Page markers and PDF artifacts have been purged from all chapters and appendices. Key syntax tables (BSD, Types, Modifiers) are formatted. Remaining work involves formatting the rest of Chapter 4 tables.
*   **FOCA Reference 06**: Generally good, though some dense technical sections have slightly merged paragraphs from the OCR process.

### Fair/Poor Quality (Substantial Cleanup Needed)
*   **IPDS Reference 12**: **Improved.** Key Device-Control commands in Chapter 4 have been converted to tables. However, it remains the largest spec with many sections still in text-dump format. Physical page references are still ubiquitous.
*   **IOCA Reference 09**: Chapter 1 and 2 are readable, but structural descriptors in later chapters lack table formatting.
*   **AFP GOCA Reference 03**: Riddled with "See '...' on page XX" which makes navigation in Markdown confusing.
*   **Line Data Reference 05**: **Poor.** Contains literal `## Page XX` headers in Appendices and Chapter 3. Diagrams are represented as pre-formatted text.

---

## Verification of TOC.md
The `specifications/TOC.md` file was audited. All 10 specification links point to valid `Front_Matter.md` or primary entry files.

**Status**: Verified.
