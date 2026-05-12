# Front Matter



**Advanced Function Presentation Consortium**
**Data Stream and Object Architectures**

**Presentation Text Object Content Architecture Reference**

**AFPC-0009-04**



 ii

**Note:** Before using this information, read the information in "Notices".

AFPC-0009-04
Fifth Edition (February 2025)

This edition applies to the Presentation Text Object Content Architecture (PTOCA). It is the second edition produced by the AFP Consortium™ (AFPC™) and replaces and makes obsolete the previous edition, AFPC-0009-03. This edition remains current until a new edition is published.

Technical changes are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no technical significance are not noted. For a detailed list of changes, see "Summary of Changes"  vii.

**Internet**
Visit our home page: [www.afpconsortium.org](http://www.afpconsortium.org)



## Preface

This book describes the functions and services associated with the Presentation Text Object Content Architecture (PTOCA) architecture.

It is a reference, not a tutorial. It complements individual product publications, but does not describe product implementations of the architecture.

### Who Should Read This Book

This book is for systems programmers and other developers who develop or adapt a product or program to interoperate with other presentation products in an Advanced Function Presentation™ environment.

### AFP Consortium (AFPC)

The AFP Consortium is an international group bringing together voices from across the printing and presentation industry to keep the AFP™ architecture up to date and continually improving. AFP Consortium members, often market competitors, work together to ensure this stable, efficient, flexible architecture continues to thrive, even as the world of printing and presentation changes.

The Advanced Function Presentation (AFP) architectures began as the strategic, general purpose document and information presentation architecture for the IBM® Corporation. The first specifications and products go back to 1984. Although all of the components of the architecture have grown over the years, the major concepts of object-driven structures, print integrity, resource management, and support for high print speeds were built in from the start.

In the early twenty-first century, IBM saw the need to enable applications to create color output that is independent from the device used for printing and to preserve color consistency, quality, and fidelity of the printed material. This need resulted in the formation, in October 2004, of the AFP Color Consortium™ (AFPCC™). The goal was to extend the object architectures with support for full-color devices including support for comprehensive color management. The idea of doing this via a consortium consisting of the primary AFP architecture users was to build synergism with partners from across the relevant industries, such as hardware manufacturers that produce printers as well as software vendors of composition, workflow, viewer, and transform tools. Quickly more than 30 members came together in regular meetings and work group sessions to create the AFP Color Management Object Content Architecture™ (CMOCA™). A major milestone was reached by the AFP Color Consortium with the initial official release of the CMOCA specification in May 2006.

Since the cooperation between the members of the AFP Color Consortium turned out to be very effective and valuable, it was decided to broaden the scope of the consortium efforts and IBM soon announced its plans to open up the complete scope of the AFP architecture to the consortium. In June 2007, IBM's role as founding member of the consortium was transferred to the InfoPrint® Solutions Company, an IBM/Ricoh® joint venture; currently Ricoh holds the founding member position. In February 2009, the consortium was incorporated under a new set of bylaws with tiered membership and shared governance resulting in the creation of a formal open standards body called the AFP Consortium (AFPC). Ownership of and responsibility for the AFP architectures was transferred at that time to the AFP Consortium.

### How to Use This Book

This book is divided into six chapters, three appendixes, and a glossary.



*   **Chapter 1, "A Presentation Architecture Perspective"** introduces the AFP presentation architectures and positions Presentation Text Object Content Architecture as a strategic object content architecture.
*   **Chapter 2, "Introduction to PTOCA"** briefly states the purpose and function of PTOCA.
*   **Chapter 3, "Overview of PTOCA"** introduces the concepts that form the basis of PTOCA and provides a brief description of the data structures.
*   **Chapter 4, "Data Structures in PTOCA"** provides the detailed syntax, semantics, and pragmatics of the data structures found in PTOCA.
*   **Chapter 5, "Exception Handling in PTOCA"** describes how exceptions are handled in PTOCA and lists the exception codes.
*   **Chapter 6, "Compliance with PTOCA"** describes how products may be valid generators or receivers of PTOCA.
*   **Appendix A, "MO:DCA Environment"** describes the Presentation Text object in the context of a MO:DCA™ data stream.
*   **Appendix B, "IPDS Environment"** describes the Presentation Text object in the context of an IPDS™ data stream.
*   **Appendix C, "PTOCA Retired Functions"** describes the retired PTOCA functions.
*   **The "Glossary"**  defines some of the terms used within this book.

### How to Read the Syntax Diagrams

Throughout this book, syntax is described using the structure defined below. Six basic data types are used in the syntax descriptions:

*   **CODE**: Architected constant
*   **CHAR**: Character string, which may consist of any code points
*   **BITS**: Bit string
*   **UBIN**: Unsigned binary
*   **SBIN**: Signed binary
*   **UNDF**: Undefined type

Syntax for PTOCA is shown in tables like the following:

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| The field's offset, data type, or both | Name of field if applicable | Range of valid values if applicable | Meaning or purpose of the data element | | | | |

*   **M/O**: **M** means mandatory. **O** means optional.
*   **Def**: **Y** means that a default value is defined for the field. **N** means that there is no default value defined for the field.
*   **Ind**: **Y** means that the field defaults to a hierarchical default value when the default indicator (X'F..F') is present. **N** means that the default indicator semantic is not valid for the field.

The following is an example of PTOCA syntax for the Begin Line (BLN) control sequence as it appears in this book:



| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 2 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'D8' – X'D9' | Control sequence function type | M | N | N |

Please refer to "Control Sequence Detailed Descriptions"  for a more detailed description of PTOCA syntax.



### Related Publications

Several other publications can help you understand the architecture concepts described in this book. The AFP Consortium publications and other AFP publications below are available on the AFP Consortium web site, [www.afpconsortium.org](http://www.afpconsortium.org).

**Table 1. AFP Consortium Architecture References**

| AFP Architecture Publication | Book Identification |
| :--- | :--- |
| AFP Programming Guide and Line Data Reference | AFPC-0010 |
| Bar Code Object Content Architecture™ Reference | AFPC-0005 |
| Color Management Object Content Architecture Reference | AFPC-0006 |
| Font Object Content Architecture Reference | AFPC-0007 |
| Graphics Object Content Architecture for Advanced Function Presentation Reference | AFPC-0008 |
| Image Object Content Architecture Reference | AFPC-0003 |
| Intelligent Printer Data Stream™ (IPDS) Reference | AFPC-0001 |
| Metadata Object Content Architecture Reference | AFPC-0013 |
| Mixed Object Document Content Architecture™ (MO:DCA) Reference | AFPC-0004 |
| Presentation Text Object Content Architecture Reference | AFPC-0009 |

**Table 2. Additional AFP Consortium Documentation**

| AFPC Publication | Book Identification |
| :--- | :--- |
| AFP Color Management Architecture™ (ACMA™) | G550–1046 (IBM) |
| AFPC Company Abbreviation Registry | AFPC-0012 |
| AFPC Font Typeface Registry | AFPC-0016 |
| BCOCA™ Frequently Asked Questions | AFPC-0011 |
| Metadata Guide for AFP | AFPC-0018 |
| MO:DCA-L:The OS/2 PM Metafile (.met) Format | AFPC-0014 |
| Presentation Object Subsets for AFP | AFPC-0002 |
| Recommended IPDS Values for Object Container Versions | AFPC-0017 |

**Table 3. AFP Font-Related Documentation**

| Publication | Book Identification |
| :--- | :--- |
| Character Data Representation Architecture Reference and Registry | SC09-2190 (IBM) |
| Font Summary for AFP Font Collection | S544-5633 (IBM) |
| Using OpenType Fonts in an AFP System | G544-5876 (IBM) |
| Technical Reference for Code Pages | S544-3802 (IBM) |



## Summary of Changes

This fifth edition of the PTOCA Reference contains the following changes:

*   Support for encrypted text data for secure printing and presentation
*   Glossary entries were updated to the latest common level
*   Small updates were made to correct errors and increase consistency and readability

As stated in the edition notice, the additions are marked in this publication in green, with green revision bars located on the left-hand side of a page.

