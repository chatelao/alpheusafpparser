# ISO 32000-2

Second edition 2020-12

Document management — Portable document format — Part 2: PDF 2.0

Gestion de documents — Format de document portable — Partie 2: PDF 2.0

© ISO 2020

## COPYRIGHT PROTECTED DOCUMENT

© ISO 2020 All rights reserved. Unless otherwise specified, or required in the context of its implementation, no part of this publication may be reproduced or utilized otherwise in any form or by any means, electronic or mechanical, including photocopying, or posting on the internet or an intranet, without prior written permission. Permission can be requested from either ISO at the address below or ISO’s member body in the country of the requester.
ISO copyright office CP 401 • Ch. de Blandonnet 8 CH-1214 Vernier, Geneva Phone: +41 22 749 01 11 Email: copyright@iso.org Website: www.iso.org Published in Switzerland

## Contents

Foreword vii

Introduction viii

1 Scope 1

2 Normative references 2

3 Terms and definitions 7

4 Notation 15
4.1 General 15
4.2 Established notations 15

5 Version designations 17

6 Conformance 18
6.1 General 18
6.2 Conforming PDF documents 18
6.3 PDF processors 18

7 Syntax 20
7.1 General 20
7.2 Lexical conventions 21
7.3 Objects 24
7.4 Filters 34
7.5 File structure 53
7.6 Encryption 71
7.7 Document structure 96
7.8 Content streams and resources 110
7.9 Common data structures 114
7.10 Functions 123
7.11 File specifications 132
7.12 Extensions dictionary 141

8 Graphics 145
8.1 General 145
8.2 Graphics objects 145
8.3 Coordinate systems 149
8.4 Graphics state 156
8.5 Path construction and painting 169
8.6 Colour spaces 177
8.7 Patterns 219
8.8 External objects 253
8.9 Images 254
8.10 Form XObjects 270
8.11 Optional content 276

9 Text 293
9.1 General 293
9.2 Organisation and use of fonts 293
9.3 Text state parameters and operators 300
9.4 Text objects 306
9.5 Introduction to font data structures 311
9.6 Simple fonts 313
9.7 Composite fonts 327
9.8 Font descriptors 343
9.9 Embedded font programs 351
9.10 Extraction of text content 355

10 Rendering 360
10.1 General 360
10.2 Raster output device native colour 361
10.3 CIE-Based colour to device colour 361
10.4 Conversions among device colour spaces 361
10.5 Transfer functions 364
10.6 Halftones 366
10.7 Scan conversion details 382
10.8 Rendering for separations 385

11 Transparency 387
11.1 General 387
11.2 Overview of transparency 387
11.3 Basic compositing computations 389
11.4 Transparency groups 402
11.5 Soft masks 414
11.6 Specifying transparency in PDF 415
11.7 Colour space and rendering issues 425

12 Interactive features 437
12.1 General 437
12.2 Viewer preferences 437
12.3 Document-level navigation 441
12.4 Page-level navigation 458
12.5 Annotations 465
12.6 Actions 506
12.7 Forms 528
12.8 Digital signatures 567
12.9 Measurement properties 595
12.10 Geospatial features 601
12.11 Document requirements 606

13 Multimedia features 614
13.1 General 614
13.2 Multimedia 614
13.3 Sounds 637
13.4 Movies 638
13.5 Alternate presentations 640
13.6 3D Artwork 642
13.7 Rich media 700

14 Document interchange 713
14.1 General 713
14.2 Procedure sets 713
14.3 Metadata 714
14.4 File identifiers 719
14.5 Page-piece dictionaries 719
14.6 Marked content 720
14.7 Logical structure 722
14.8 Tagged PDF 745
14.9 Repurposing and accessibility support 796
14.10 Web capture 802
14.11 Prepress support 814
14.12 Document parts 834
14.13 Associated files 838

Annex A (informative) Operator Summary 844
Annex B (informative) Operators in Type 4 Functions 848
Annex C (informative) Advice on maximising portability 850
Annex D (normative) Character sets and encodings 853
Annex E (normative) Extending PDF 877
Annex F (normative) Linearized PDF 879
Annex G (informative) Linearized PDF access strategies 901
Annex H (informative) Example PDF files 905
Annex I (normative) PDF versions and compatibility 936
Annex J (informative) XObject comparison 938
Annex K (normative) XFA forms 944
Annex L (normative) Parent-child relationships between the standard structure elements in the standard structure namespace for PDF 2.0 947
Annex M (informative) Differences between the standard structure namespaces 973
Annex N (informative) Best practice for halftones 974
Annex O (normative) Fragment identifiers 977
Annex P (informative) An algorithm to determine the actual blending colour space of a transparency group 980
Annex Q (normative) Method for determining transparency on a page 982

Bibliography 984

Foreword

ISO (the International Organization for Standardization) is a worldwide federation of national standards bodies (ISO member bodies). The work of preparing International Standards is normally carried out through ISO technical committees. Each member body interested in a subject for which a technical committee has been established has the right to be represented on that committee.
International organizations, governmental and non-governmental, in liaison with ISO, also take part in the work. ISO collaborates closely with the International Electrotechnical Commission (IEC) on all matters of electrotechnical standardization.

The procedures used to develop this document and those intended for its further maintenance are described in the ISO/IEC Directives, Part 1. In particular the different approval criteria needed for the different types of ISO documents should be noted. This document was drafted in accordance with the editorial rules of the ISO/IEC Directives, Part 2 (see www.iso.org/directives).

Attention is drawn to the possibility that some of the elements of this document may be the subject of patent rights. ISO shall not be held responsible for identifying any or all such patent rights. Details of any patent rights identified during the development of the document will be in the Introduction and/or on the ISO list of patent declarations received (see www.iso.org/patents).

Any trade name used in this document is information given for the convenience of users and does not constitute an endorsement.

For an explanation on the voluntary nature of standards, the meaning of ISO specific terms and expressions related to conformity assessment, as well as information about ISO's adherence to the World Trade Organization (WTO) principles in the Technical Barriers to Trade (TBT) see the following URL: www.iso.org/iso/foreword.html.

This document was prepared by Technical Committee ISO/TC 171, Document management applications, Subcommittee SC 2, Application issues, in collaboration with Technical Committee ISO/TC 130, Graphic technology.

This second edition cancels and replaces the first edition (ISO 32000-2:2017), which has been technically revised.

A list of all the parts of ISO 32000 can be found on the ISO website. Changes from previous parts and editions are listed in the Introduction (clauses 0.3 and 0.4).

Any feedback or questions on this document should be directed to the user’s national standards body.
A complete listing of these bodies can be found at www.iso.org/members.html.

Introduction

## 0.1 PDF

PDF enables users to exchange and view electronic documents easily and reliably, independent of the environment in which they were created or the environment in which they are viewed or printed.

At the core of PDF is an advanced imaging model derived from the PostScript®1 page description language. This PDF Imaging Model enables the description of text and graphics in a device-independent and resolution-independent manner at a complete, precise and professional level. Unlike PostScript, which is a programming language, PDF is based on a structured binary file format that is optimised for high performance in interactive viewing.

PDF includes objects such as annotations and hypertext links that are not part of the page content itself but are useful for interactive viewing and document interchange. PDF also includes data structures such as tagged PDF, XMP and an associated files mechanism, that are useful for document management and content reuse.

PDF files can be created natively in PDF form, converted from other electronic formats. Since PDF supports a wide range of image and compression technologies, it is a suitable format for documents digitised from paper, microform, or other hard copy formats. Businesses, governments, libraries, archives and other institutions and individuals around the world use PDF to represent considerable bodies of important information. Since its introduction in 1993, aided by the explosive growth of the Internet, PDF has become widely used for the electronic exchange of documents.

There are several specific applications of PDF that have evolved in which limiting the use of some features of PDF while requiring the use of others, enhances the usefulness of PDF. The following

• PDF/X (ISO 15930) is the industry standard for the intermediate representation of printed material in electronic prepress systems for conventional printing applications.
• PDF/A (ISO 19005) is the industry standard for the archiving of digital documents.
• PDF/UA (ISO 14289) is the industry standard for accessible PDF documents and processors.
• PDF/E (ISO 24517) provides a mechanism for representing engineering documents and exchanging engineering data.
• PDF/VT (ISO 16612-2 and ISO 16612-3) is for high volume printing of personalised documents including variable data.
• ISO 19593 describes a method for storing data in a PDF file that correspond to the processing steps of printed products (such as cutting, folding, glueing, Braille, printed white, and printed varnish).
• ISO 21812 describes how document part metadata in a PDF file can be used to communicate the intended appearance of print products and their components.
As corporations, government agencies, and educational institutions streamline their operations by replacing paper-based workflows with electronic exchange of information, the impact and opportunity for the application of PDF will continue to grow at a rapid pace.

PDF, together with software for creating, viewing, printing and processing PDF files in a variety of ways, fulfils a set of requirements for electronic documents including:

• preservation of document fidelity independent of the device, platform, and software,
• merging of content from diverse sources — Web sites, word processing and spreadsheet programs, scanned documents, photos, and graphics — into one self-contained document while maintaining the integrity of all original source documents,
• an extensible metadata model at the document and object level,
• collaborative editing of documents from multiple locations or platforms,
• digital signatures to certify authenticity,
• security and permissions to allow the creator to retain control of the document and associated rights,
• accessibility of content to those with disabilities,
• extraction and reuse of content for use with other file formats and applications, and
• electronic forms to gather and/or represent data within business systems.

## 0.2 ISO 32000 and PDF

PDF was developed and specified by Adobe Systems Incorporated beginning in 1993 and continuing until 2007 when ISO 32000-1 was first prepared. The Adobe Systems version PDF 1.7 was the basis for ISO 32000-1. The ISO 32000 series has been created as a multi-part document, of which this is Part 2. This allows future parts to be created without rendering ISO 32000, or applications based on it, obsolete. See clause 5, "Version designations" for how the version numbers of PDF (1.0, 1.1, 1.2, […] 2.0) relate to one another.

The primary purpose of this document is to define well-formed PDF documents (conforming PDF files).

In carefully specifying what constitutes a well-defined PDF document, it is natural to describe why a particular feature is to be included in the PDF file and what effect it is designed to have on PDF processing software. So, although the primary objective of this document is to describe the content of conforming PDF files, it also serves secondary purposes of defining exactly how a PDF component is constructed, suggesting why a producer might choose to use the various PDF constructs, as well as what behaviour is elicited from software consuming that PDF file. The choice of which specific set of features a particular PDF processor supports is not specified.

PDF files represent electronic documents. Over time, it was natural to add features that take advantage of PDF’s nature, and the power of computer viewing devices. The size of the PDF documentation has more than quadrupled since its first introduction, and the number of features that a PDF processor is expected to support has grown to be large.

## 0.3 Changes introduced in ISO 32000-2:2017

Starting with ISO 32000-2:2017 (PDF 2.0) the term "conforming reader" is no longer used. The terms "interactive PDF processor", "PDF reader" and "PDF writer" are used instead, and have a conditional conformance definition. See 6, "Conformance" for further discussion of this change.

This document includes many changes from ISO 32000-1:2008, however only significant new features are marked as being new in PDF 2.0.

PDF 2.0 includes the following new features:

• 7.6.7, "Unencrypted wrapper document";
• 8.6.5.9, "Use of black point compensation";
• 12.5.6.24, "Projection annotations";
• 12.8.3.4, "CAdES signatures as used in PDF";
• 12.8.4, "Long term validation of signatures";
• 12.8.4.3, "Document Security Store (DSS)" and 12.8.5, "Document timestamp (DTS) dictionary";
• 12.10, "Geospatial features";
• 13.7, "Rich media" annotations;
• 14.7.4, "Namespaces" for tagged PDF;
• 14.9.6, "Pronunciation hints";
• 14.12, "Document parts";
• 14.13, "Associated files";
• Support for PRC (see 13.6, "3D Artwork");
• Support for UTF-8.

PDF 2.0 adds many new capabilities to existing features in PDF, including:

• Transparency and blend mode attributes for annotations;
• Stamp Annot intent;
• Polygon/Polyline real paths;
• 256-bit AES encryption;
• ECC-based certificates;
• Unicode-based passwords;
• Document requirement extensions;
• New value for tab order of fields and annotations;
• Page-level OutputIntents;
• Referenced (external) OutputIntents;
• Thumbnails for embedded files;
• Halftone Origin (HTO);
• Measurement & Point Data for image & form XObjects;
• L (length) key for inline image data;
• Viewer preferences enforcement (of print scaling);
• 3D measurements;
• GoToDp action;
• RichMediaExecute action;
• Extension to GoTo and GoToR to support linking to a specific structure element;
• Extension to Signature Field Locks and Signature Seed Values;
• Extensions to 3D viewing conditions, incl. transparency;
• Ref (reference) structure element property;
• PageNum and Bates artifact types;
• New list types for structured lists;
• “Short” (short name) attribute for table header cells;
• Extensions to OutputIntents (MixingHints and SpectralData).

The following clauses have been substantially rewritten for PDF 2.0:

• 7.4.7, "JBIG2Decode filter";
• 10.1 – 10.3, "Rendering";
• 11, "Transparency";
• 12.8, "Digital signatures";
• 14.3, "Metadata";
• 14.8, "Tagged PDF";
• 14.9, "Repurposing and accessibility support".

PDF 2.0 includes many important corrections, extensions and clarifications for existing features, including:

• Corrections for many typing errors including bad symbols and truncated formulae.
• Updates and changes in normative references and the bibliography.
• Improved cross referencing for clauses, tables and figures within this document.
• Clarification for processing dashed and degenerate lines, clarification for processing text objects and blending colour spaces within the transparency framework, clarifications and enhancements for annotation appearances, stamp annotations extension and polyline annotation enhancement.
• Strengthened encryption including introduction of elliptic curve cryptography, more control over forms tab ordering, enforced viewer preferences, rich text, improvements to digital signatures for long term signatures, 3D viewing improvements including 3D projections, revised blend formulae for ColorBurn and ColorDodge, additional structure tags to improve accessibility, requirement for metadata streams to be XMP and support for hyperlinks in rich text.
• Clarification for PDF version numbering, resource inheritance, required and optional signature dictionary SubFilter keys, artifacts, developer-defined extensions, word breaking and page sizes, which file to show when first opening a collection, scope of header attributes, precedence of CID font widths, when a CIDToGIDMap is used with Type 2 CID fonts, deprecate sound and movie actions and annotations in favour of newer methods, rendering intent and ImageMask, precedence of Type 1 encoding methods, the wording used to define delimiters with respect to << and >>, Identity CMaps and CIDFonts, a special case when closing and filling a path, that clipping follows filling rules and that operating on an undefined path generates an error.
• Clarification and terminology improvements among Type 1, TrueType, CFF and OpenType fonts; thumbnails for embedded files.
• Specification of XFA used for rich text in annotations.
• The rewrite of 14.8, "Tagged PDF" includes clarification of the parent/child relationships between tags, simplifies and extends the standard tag set, and adds the use of namespaces for custom tag sets (see also 14.7.4, "Namespaces" for new namespace functionality).

Some features present in earlier versions of PDF have been deprecated in PDF 2.0, including:

• XFA (incl. NeedsRendering);
• Movie, Sound and TrapNet annotations;
• Movie and Sound actions;
• Info dictionary;
• Assistive technology restrictions via DRM;
• ProcSet;
• OS-specific file specifications;
• OS-specific additions to Launch actions;
• Names for XObjects;
• Names for Fonts;
• Arrays of Blend Modes;
• Alternate Presentations;
• Open prepress interface (OPI);
• CharSet (For Type 1 fonts);
• CIDSet (for CID fonts);
• Prepress viewer preferences (ViewArea, ViewClip, etc.);
• NeedAppearances;
• adbe.pkcs7.sha1;
• adbe.x509.rsa_sha1;
• Encryption of FDF files;
• Suspects flag in MarkInfo dictionary;
• UR signatures;
• Transfer functions in the graphics state.

## 0.4 Changes introduced in ISO 32000-2:2020

ISO 32000-2:2020 includes additional corrections and clarifications as indicated in this clause. Precise locations of key changes are also indicated by the text string “(2020)”. In addition, F.3 "Linearized PDF document structure" and Annex L "Parent-child relationships between the standard structure elements in the standard structure namespace for PDF 2.0" have been significantly updated. PDF character collections were also updated as follows:

• Adobe-Japan1-6 becomes Adobe-Japan1-7;
• Adobe-CNS1-6 becomes Adobe-CNS1-7;
• Adobe-KR-9 is introduced;
• Adobe-Korea1-2 & Adobe-Japan2-0 were deprecated;
• Adobe-GB1-5 remains unchanged.

ISO 32000-2:2020 also makes several important Normative References changes due to various documents being withdrawn or obsoleted. Additionally some previous Normative References have been moved to the Bibliography:

• ISO 3166-1 is now an undated reference – see 7.9.2.2.2, "Text string language escape sequences";
• The ISO/IEC 14492 dated reference for JBIG2 was updated to the 2019 edition – see 7.4.7, "JBIG2Decode filter";
• The ISO/IEC 14496-22 dated reference for the Open Font format was updated to the 2019 edition – see 9.6.3, "TrueType fonts";
• ISO 15076-1:2010 dated reference for ICC.1 can be supplemented by the Errata list and approved revisions available from the ICC website (http://color.org/icc_specs2.xalter) – see 8.6.5.5, "ICCBased colour spaces";
• The ISO/IEC 15444-1 dated reference for JPEG 2000 was updated to the 2019 edition – see 7.4.9, "JPXDecode filter";
• The ISO/IEC 19444-1 dated reference for XFDF was updated to the 2019 edition;
• This document requires that RFC 3454 (“stringprep”) and RFC 4013 (“SASLprep”) continue to be used to maintain backward compatibility even though these RFCs are marked as obsolete by IETF;
• RFC 6234 (US Secure Hash Algorithms) is replaced by FIPS PUB 180-4; RFC 2083 (Portable Network Graphics) is replaced by ISO/IEC 15948:2004 for PNG predictors – see 7.4.4.4, "LZW and Flate predictor functions";
• ISO/DIS 21757-1 replaces several Adobe, ECMA and ISO publications related to ECMAScript in PDF 2.0 – see 12.6.4.17, "ECMAScript actions";
• This document makes explicit reference to ECMA-363 U3D 3rd edition and not the latest U3D 4th edition;
• PDF character collections are now all referenced to GitHub repositories;
• The TrueType Reference is now undated.

An attempt is being made to keep copies of all references without copyright restrictions available for free download on the following website: https://www.pdfa.org/iso-32000-normative-references/.

Document management — Portable document format — Part 2: PDF 2.0

> **IMPORTANT** — The electronic file of this document contains colours which are considered to be useful for the correct understanding of the document. Users who need a paper copy of this document will therefore benefit from using a colour printer.
