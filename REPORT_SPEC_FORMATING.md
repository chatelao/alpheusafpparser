# Specification Formatting Quality Report

This report evaluates the quality of Markdown conversions for the AFP Architecture Specifications located in `specifications/markdown/`.

To know the quality, compare the result to the original PDF resports to find the original structures.

## Summary Table

| Specification | Quality | Key Strengths | Primary Issues |
| :--- | :--- | :--- | :--- |
| **MO:DCA** (modca-reference-10) | Good | Structured tables, clear hierarchy | Some "on page XXX" references remain in subset chapters. |
| **PTOCA** (ptoca-reference-04) | Excellent | Purged of OCR artifacts, LaTeX math, structured tables | Minor split-word remnants. |
| **CMOCA** (cmoca-reference-02) | Excellent | Clean syntax tables, minimal PDF artifacts | None significant. |
| **FOCA** (foca-reference-06) | Good | Proper table formatting, clear sections | Minor OCR text merging in dense areas. |
| **MOCA** (moca-reference-02) | Excellent | Cleanest conversion, no artifacts detected | None. |
| **AFP GOCA** (afp-goca-reference-03) | Fair | Consistent headers | Heavy "on page XXX" cross-references, some table fragmentation. |
| **BCOCA** (bcoca-reference-11) | Poor | Chapters are delimited | Explicit `## Page XX` markers remain, tables are poorly structured. |
| **IOCA** (ioca-reference-09) | Fair | Text is readable | Missing Markdown table markers for many structures. |
| **IPDS** (ipds-reference-12) | Poor | Comprehensive content | No Markdown tables (text dumps), heavy PDF artifacts, broken paragraphs. |
| **Line Data** (linedata-reference-05) | Fair | Basic structure present | Diagrams as text, mixed table quality. |

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
*   **MOCA Reference 02**: Extremely clean. Appears to have been manually verified or processed with high-fidelity tools.
*   **CMOCA Reference 02**: Standardized registry and syntax tables. Clean Chapter structure.

### Good Quality (Minor Cleanup Needed)
*   **MO:DCA Reference 10**: High quality for main chapters (1-6). Subset chapters (Chapter 7) and Appendices still contain "on page XXX" physical references that should be removed.
*   **FOCA Reference 06**: Generally good, though some dense technical sections have slightly merged paragraphs from the OCR process.

### Fair/Poor Quality (Substantial Cleanup Needed)
*   **IPDS Reference 12**: **Critical Need.** This is the largest spec and the least processed. Commands like `Load Code Page (LCP)` are text dumps rather than tables, making them difficult to parse. Physical page references are ubiquitous.
*   **BCOCA Reference 11**: Contains literal `## Page 26` headers throughout the text, which breaks the logical structure. Tables are not formatted as Markdown tables.
*   **IOCA Reference 09**: Chapter 1 and 2 are readable, but structural descriptors in later chapters lack table formatting.
*   **AFP GOCA Reference 03**: While it has tables, it is riddled with "See '...' on page XX" which makes navigation in Markdown confusing.

---

## Verification of TOC.md
The `specifications/TOC.md` file was audited. Several links were found to be broken because they pointed to non-existent files (e.g., `markdown/modca-reference-10.md` instead of `markdown/modca-reference-10/Front_Matter.md`).

**Status**: Fixed. All 10 specification links in `TOC.md` now point to valid `Front_Matter.md` or primary entry files.
