## Page 1

Advanced Function Presentation Consortium
Data Stream and Object Architectures
Font Object Content Architecture
Reference
AFPC-0007-06

## Page 2

Copyright © AFP Consortium 1998, 2015 ii
Note:
Before using this information, read the information in “Notices” on page 203.
AFPC-0007-06
Seventh Edition (October 2015)
This edition applies to Font Object Content Architecture (FOCA). It is the first edition produced by the AFP Consortium™
(AFPC™) and replaces and makes obsolete the previous edition (S544-3285-05) published by IBM ®. This edition remains
current until a new edition is published. This publication also applies to any subsequent releases of Advanced Function
Presentation™ (AFP™) products that use the FOCA architecture until otherwise indicated in a new edition.
Changes are indicated by a vertical bar to the left of the change. For a detailed list of changes, refer to “Changes in This
Edition” on page xi.
Internet
Visit our home page: http://www.afpcinc.org

## Page 3

Copyright © AFP Consortium 1998, 2015 iii
Preface
This book describes the functions and services associated with Font Object Content Architecture (FOCA). The
FOCA architecture describes semantics and terminology for font objects used in a variety of environments.
Syntax is provided for AFP system fonts used in the MO:DCA™ environment; refer to “AFP System Font
Resource” on page 111. The syntax for printer fonts used in the IPDS™ environment is fully described in the
Intelligent Printer Data Stream™ Reference.
Note: The FOCA architecture has been stabilized such that it can be fully used within AFP products and
environments, but will not be extended. Many AFP products use either FOCA fonts,
TrueType/OpenType fonts, or both.
Who Should Read This Book
This book describes the functions and services associated with the Font Object Content Architecture (FOCA).
It is for systems programmers and other developers who need such information to develop or adapt a product
or program to interoperate with other presentation products in an AFP
environment.
This book is a reference, not a tutorial. It complements individual product publications, but does not describe
product implementations of the architecture.
AFP data streams (MO:DCA and IPDS) provide the ability to manage and use a wide variety of font resources.
These font resources can be classified into two major categories: coded fonts and data-object fonts. Coded
fonts and their component parts are defined within the Font Object Content Architecture (FOCA) and are
described in this book; data-object-fonts are defined elsewhere. A good description of data-object fonts and
their component parts can be found in How To Use TrueType and OpenType Fonts in an AFP System.

## Page 4

iv FOCA Reference
AFP Consortium (AFPC)
The Advanced Function Presentation (AFP) architectures began as the strategic, general purpose document
and information presentation architecture for the IBM Corporation. The first specifications and products go
back to 1984. Although all of the components of the architecture have grown over the years, the major
concepts of object-driven structures, print integrity, resource management, and support for high print speeds
were built in from the start.
In the early twenty-first century, IBM saw the need to enable applications to create color output that is
independent from the device used for printing and to preserve color consistency, quality, and fidelity of the
printed material. This need resulted in the formation, in October 2004, of the AFP Color Consortium™
(AFPCC™). The goal was to extend the object architectures with support for full-color devices including
support for comprehensive color management. The idea of doing this via a consortium consisting of the
primary AFP architecture users was to build synergism with partners from across the relevant industries, such
as hardware manufacturers that produce printers as well as software vendors of composition, work flow,
viewer, and transform tools. Quickly more than 30 members came together in regular meetings and work group
sessions to create the AFP Color Management Object Content Architecture™ (CMOCA™). A major milestone
was reached by the AFP Color Consortium with the initial official release of the CMOCA specification in May
2006.
Since the cooperation between the members of the AFP Color Consortium turned out to be very effective and
valuable, it was decided to broaden the scope of the consortium efforts and IBM soon announced its plans to
open up the complete scope of the AFP architecture to the consortium. In June 2007, IBM's role as founding
member of the consortium was transferred to the InfoPrint
® Solutions Company, an IBM/Ricoh® joint venture.
In February 2009, the consortium was incorporated under a new set of bylaws with tiered membership and
shared governance resulting in the creation of a formal open standards body called the AFP Consortium
(AFPC). Ownership of and responsibility for the AFP architectures was transferred at that time to the AFP
Consortium.
AFP Consortium

## Page 5

FOCA Reference v
Publication History
The FOCA Reference was first published by IBM in 1988 and has had several enhancements and updates
since that time. The first six editions were published by IBM Corporation and the current edition was published
by the AFP Consortium.
Note that the FOCA architecture has been stabilized such that it can be fully used within AFP products and
environments, but will not be extended. Many AFP products use either FOCA fonts, TrueType/OpenType fonts,
or both.
First Edition published by IBM Corporation
S544-3285-00 dated December 1988
Second Edition published by IBM Corporation
S544-3285-01 dated February 1990
Third Edition published by IBM Corporation
S544-3285-02 dated June 1993
This edition provides enhanced detail and clarifications:
• Information has been added to show the relationship between FOCA and the ISO
® Font
Information Interchange Standard (ISO/IEC 9541-1:1991). The concepts and parameters of
FOCA can be mapped to, or transformed to, corresponding properties in the ISO font
standard. Likewise, many of the concepts and properties defined by the ISO font standard
can be mapped to, or transformed to, parameters of FOCA.
• Chapter 1 has been rewritten to introduce the family of IBM Presentation Architectures.
• Chapters 2–4 have been reorganized and edited to improve readability, arranging the
information to allow the reader to move more freely from general introductory concepts to
detailed architecture specifications.
• Chapter 5 has been expanded to include new FOCA parameters and to clarify how those
parameters apply to different writing systems (e.g., top to bottom).
• Chapter 6 has been added to show the relationship between the font semantics defined in
this document and the font syntax defined and interchanged by other architectures and
products.
• Appendix A has been replaced, adding a reference summary of the relationship between
IBM Font Architecture parameters and ISO/IEC 9541 Font Information Interchange standard
properties.
• The Glossary has been extensively revised to include all terms applicable to the family of
IBM Presentation Architectures.
Fourth Edition published by IBM Corporation
S544-3285-03 dated April 1996
This edition provides enhanced detail and the following major new additions:
• Syntax for AFP Host Fonts, previously contained in the AFP Host Font Data Stream
Reference ( IBM S544-3289-01), has been merged into chapter six.
• Outline Font support and associated syntax
• Miscellaneous technical clarifications and corrections (as marked)
• An appendix, which describes the pattern technologies supported by the FOCA architecture
Publication History

## Page 6

vi FOCA Reference
Fifth Edition published by IBM Corporation
S544-3285-04 dated June 2000
This edition provides clarifications and additional detail to support the FOCA products that IBM
has provided since 1996. Changes include:
• GCUID and UCS Presentation encoding scheme (basic Unicode support)
• 300 pel fixed metrics
• Metric Adjustment triplet for migrating from double-byte raster to outline fonts
• Fully Qualified Name (FQN) triplet for identifying and referencing fonts objects
• NOP structured field
• FND “Typeface Name” field changed to “Typeface Description”
• Typeface Name now allowed in FQN triplet on FND
• Default character support for outline fonts
• Triplet clarifications
• Space character clarifications
• Editorial clarifications
Sixth Edition published by IBM Corporation
S544-3285-05 dated June 2005
This edition provides clarifications and additional detailed description of the FOCA
architecture. Changes include:
• Extended point-size range for relative metrics
• Variable-space-enable flag in the Code Page Control
• Pointsize terminology clarifications
• Relationship of the FOCA architecture to TrueType and OpenType fonts
• Unicode scalar values in code pages
• Improved syntax tables
• Editorial clarifications
Publication History

## Page 7

FOCA Reference vii
How to Use This Book
This book is divided into seven chapters, with appendixes. Those readers who have little or no knowledge of
fonts or font architecture concepts should read the introductory chapters first. Those readers who are
experienced in using fonts in AFP implementations may wish to begin with “AFP System Font Resource” on
page 111, and then use Chapter 5, “FOCA Parameters”, on page 55 as a reference for parameter semantics.
Those readers who are experienced in using fonts in other AFP
product implementations may wish to begin
with their product publications, and then use Chapter 5, “FOCA Parameters”, on page 55 as a reference for
parameter semantics.
• Chapter 1, “A Presentation Architecture Perspective”, on page 1 introduces the Presentation Architecture
framework that is covered in this book.
• Chapter 2, “Introduction to Fonts”, on page 7 describes digitized fonts, text processing, font storage and
accessing, font referencing, and FOCA font concepts.
• Chapter 3, “Referencing Fonts”, on page 17 explains the relationship of fonts to the various processes in
document production. Included are topics of font selection and substitution, font identification, and document
fidelity.
• Chapter 4, “FOCA Overview”, on page 33 explains, in more detail, FOCA font architecture concepts and
character shape information.
• Chapter 5, “FOCA Parameters”, on page 55 provides semantic descriptions of the parameters used in font
resources, references, and queries.
• Chapter 6, “Font Interchange Formats”, on page 111 provides information about the formats required for the
interchange of font information.
• Chapter 7, “Compliance Requirements”, on page 189 defines the requirements for compliance to the
architecture.
• Appendix A, “AFP System Font Structured-Field and Triplet Summary”, on page 191 provides tables of AFP
system font data structures sorted by hexadecimal ID, with a page number reference to the full description of
each data structure.
• Appendix B, “Mapping of ISO Parameters”, on page 193 provides a summary cross-reference of all FOCA
and ISO 9541 parameters.
• Appendix C, “Pattern T echnology Information”, on page 201 provides information about the various shape
representation formats supported by FOCA.
The “Glossary” on page 205 defines those font terms used in this book, which might also be required by other
presentation architectures.
How to Use This Book

## Page 8

viii FOCA Reference
Related Publications
Several other publications can help you understand the architecture concepts described in this book. AFP
Consortium publications and a few other AFP publications are available on the AFP Consortium web site,
www.afpcinc.org.
Table 1. AFP Consortium Architecture References
AFP Architecture Publication Book Identification
AFP Programming Guide and Line Data Reference S544-3884 (IBM)
Bar Code Object Content Architecture™ Reference AFPC-0005
Color Management Object Content Architecture Reference AFPC-0006
Font Object Content Architecture Reference AFPC-0007
Graphics Object Content Architecture for Advanced Function Presentation Reference AFPC-0008
Image Object Content Architecture Reference AFPC-0003
Intelligent Printer Data Stream Reference AFPC-0001
Metadata Object Content Architecture Reference AFPC-0013
Mixed Object Document Content Architecture™ (MO:DCA) Reference AFPC-0004
Presentation Text Object Content Architecture Reference SC31-6803 (IBM)
Table 2. Additional AFP Consortium Documentation
AFPC Publication Book Identification
AFP Color Management Architecture™ (ACMA™) AFPC-0015
AFPC Company Abbreviation Registry AFPC-0012
AFPC Font Typeface Registry AFPC-0016
BCOCA™ Frequently Asked Questions AFPC-0011
MO:DCA-L:The OS/2 PM Metafile (.met) Format AFPC-0014
Presentation Object Subsets for AFP AFPC-0002
Recommended IPDS Values for Object Container Versions AFPC-0017
Table 3. AFP Font-Related Documentation
Publication Book Identification
Character Data Representation Architecture Reference and Registry;
please refer to the online version for the most current information:
http://www-306.ibm.com/software/globalization/cdra/index.jsp
SC09-2190 (IBM)
Font Summary for AFP Font Collection S544-5633 (IBM)
How To Use TrueType and OpenType Fonts in an AFP System G544-5876 (IBM)
Technical Reference for Code Pages S544-3802 (IBM)
Table 4. UP 3I™ Architecture Documentation
Related Publications

## Page 9

FOCA Reference ix
Table 4 UP 3I™ Architecture Documentation (cont'd.)
UP3I Publication Book Identification
Universal Printer Pre- and Post-Processing Interface (UP 3I) Specification Available at
www.afpcinc.org
Related Publications

## Page 10

x FOCA Reference

## Page 11

FOCA Reference xi
Changes in This Edition
Changes between this edition and the previous edition are marked in green by a vertical bar (|) in the left
margin.
This edition provides clarifications and additional detailed description of the FOCA architecture. Changes
include:
• Editorial clarifications
• IBM product-specific information has been removed and the term AFP or FOCA used in place of IBM where
appropriate
• Information about the AFP Consortium
• Style changes to make this book more consistent with the other AFPC publications
Note: The FOCA architecture has been stabilized such that it can be fully used within AFP products and
environments, but will not be extended. Many AFP products use either FOCA fonts,
TrueType/OpenType fonts, or both.
Changes in This Edition

## Page 12

xii FOCA Reference

## Page 13

Copyright © AFP Consortium 1998, 2015 xiii
Contents
Preface . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
Who Should Read This Book .....................................................................................................................iii
AFP Consortium (AFPC) .......................................................................................................................... iv
Publication History ................................................................................................................................... v
How to Use This Book .............................................................................................................................vii
Related Publications .............................................................................................................................. viii
Changes in This Edition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xi
Figures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xix
Tables . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xxi
Chapter 1. A Presentation Architecture Perspective. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .1
The Presentation Environment ................................................................................................................... 1
Architecture Components.......................................................................................................................... 2
Data Streams ..................................................................................................................................... 2
Objects ............................................................................................................................................. 4
Chapter 2. Introduction to Fonts . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .7
Fonts .................................................................................................................................................... 7
Font Resources .................................................................................................................................. 8
Font References ................................................................................................................................. 9
AFP
Font Resources............................................................................................................................ 9
Digitized Font Structures ......................................................................................................................... 10
Font-Descriptive Information................................................................................................................ 11
Font-Metric Information ...................................................................................................................... 11
Character-Shape Information............................................................................................................... 12
Character-Mapping Information............................................................................................................ 12
Using Digitized Fonts ............................................................................................................................. 13
Font Production ................................................................................................................................ 13
Font Storage .................................................................................................................................... 13
Font Processing................................................................................................................................ 14
Editing........................................................................................................................................ 15
Formatting................................................................................................................................... 15
Presenting................................................................................................................................... 16
Font Processing Summary.............................................................................................................. 16
Chapter 3. Referencing Fonts . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 17
Understanding the Font Reference Model ................................................................................................... 18
User Input ....................................................................................................................................... 18
Editor Determination of Font Availability ................................................................................................. 19
Font Services Access to Font Information............................................................................................... 19
Formatter Access to Font Information .................................................................................................... 20
Presentation Services Access to Font Information .................................................................................... 21
Identifying Fonts.................................................................................................................................... 22
System-Level Font Resource............................................................................................................... 23
Device-Level Font Resource................................................................................................................ 24
User-Input Font Reference .................................................................................................................. 25
Revisable-Document Font Reference .................................................................................................... 25
Presentation-Document Font Reference ................................................................................................ 26
Device Data Stream Font Reference ..................................................................................................... 27
Font Selection and Substitution ................................................................................................................ 28
Identifying User Intent ........................................................................................................................ 28
Document Editing.............................................................................................................................. 29
Document Formatting ........................................................................................................................ 30
Document Presentation ...................................................................................................................... 31
Chapter 4. FOCA Overview . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 33
Character Coordinate System .................................................................................................................. 33
Table of Contents

## Page 14

xiv FOCA Reference
Units of Measure............................................................................................................................... 34
Unit-Em ...................................................................................................................................... 34
Unit-Base.................................................................................................................................... 35
Units Per Unit-Base....................................................................................................................... 35
Calculating the Units of Measure ...................................................................................................... 35
Units of Direction............................................................................................................................... 36
Character Concepts ............................................................................................................................... 36
Character Boxes ............................................................................................................................... 36
Character Baseline ............................................................................................................................ 37
Character Reference Point .................................................................................................................. 37
Character Escapement Point ............................................................................................................... 38
A-space .......................................................................................................................................... 38
B-space .......................................................................................................................................... 39
C-space .......................................................................................................................................... 39
Character Increment .......................................................................................................................... 40
Kerning ........................................................................................................................................... 40
Pair Kerning................................................................................................................................. 40
Ascender Height ............................................................................................................................... 41
Descender Depth .............................................................................................................................. 41
Baseline Extent ................................................................................................................................ 41
Baseline Offset ................................................................................................................................. 42
Slope.............................................................................................................................................. 42
Font Concepts ...................................................................................................................................... 43
Vertical Size..................................................................................................................................... 43
Horizontal Font Size .......................................................................................................................... 43
Cap-M Height ................................................................................................................................... 43
X-Height.......................................................................................................................................... 43
Internal Leading ................................................................................................................................ 44
External Leading............................................................................................................................... 44
Maximum Ascender Height ................................................................................................................. 44
Maximum Descender Depth ................................................................................................................ 45
Maximum Baseline Extent................................................................................................................... 45
Superscripts and Subscripts ................................................................................................................ 45
Overscores, Throughscores, and Underscores ........................................................................................ 46
Recommendations for Overscores and Throughscores ......................................................................... 46
Recommendations for Underscores .................................................................................................. 46
Non-Latin Language Support ................................................................................................................... 47
Character Rotation ............................................................................................................................ 47
Rotated Baseline and Character Boxes.................................................................................................. 48
Eastern Writing Systems..................................................................................................................... 49
Middle Eastern Writing Systems ........................................................................................................... 51
Non-AFP
Architecture Support ................................................................................................................. 51
ISO 9541-1 Font Architecture .............................................................................................................. 51
Coordinate System........................................................................................................................ 52
Global Naming ............................................................................................................................. 53
Chapter 5. FOCA Parameters . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 55
Defining FOCA Parameters ..................................................................................................................... 55
Parameter Formats ........................................................................................................................... 55
Parameter Types .............................................................................................................................. 55
Byte and Bit Numbering...................................................................................................................... 56
Font-Description Parameters ................................................................................................................... 56
Average Weighted Escapement ........................................................................................................... 57
Cap-M Height ................................................................................................................................... 57
Character Rotation ............................................................................................................................ 58
Comment ........................................................................................................................................ 58
Design General Class (ISO) ................................................................................................................ 58
Design Specific Group (ISO)................................................................................................................ 59
Design Subclass (ISO) ....................................................................................................................... 59
Em-Space Increment ......................................................................................................................... 59
Extension Font ................................................................................................................................. 60
Family Name.................................................................................................................................... 60
Font Local Identifier ........................................................................................................................... 60
Table of Contents

## Page 15

FOCA Reference xv
Font Typeface Global Identifier............................................................................................................. 61
Font Use Code ................................................................................................................................. 61
Graphic Character Set Global Identifier .................................................................................................. 61
Hollow Font ..................................................................................................................................... 62
Italics.............................................................................................................................................. 62
Kerning Pair Data.............................................................................................................................. 62
Maximum Horizontal Font Size............................................................................................................. 63
Maximum Vertical Font Size ................................................................................................................ 64
Measurement Units ........................................................................................................................... 64
MICR Font ....................................................................................................................................... 65
Minimum Horizontal Font Size ............................................................................................................. 65
Minimum Vertical Font Size ................................................................................................................. 66
Nominal Character Slope .................................................................................................................... 67
Nominal Horizontal Font Size............................................................................................................... 67
Nominal Vertical Font Size .................................................................................................................. 68
Overstruck Font ................................................................................................................................ 68
Proportional Spaced .......................................................................................................................... 69
Private Use...................................................................................................................................... 69
Resource Name................................................................................................................................ 69
Specified Horizontal Font Size ............................................................................................................. 70
Specified Horizontal Scale Factor ......................................................................................................... 70
Specified Vertical Font Size ................................................................................................................. 71
Transformable Font ........................................................................................................................... 71
Typeface Name ................................................................................................................................ 71
Underscored Font ............................................................................................................................. 72
Uniform Character Box Font ................................................................................................................ 72
Weight Class.................................................................................................................................... 72
Width Class ..................................................................................................................................... 73
X-Height.......................................................................................................................................... 74
Font-Metric Parameters .......................................................................................................................... 74
Default Baseline Increment ................................................................................................................. 74
External Leading............................................................................................................................... 75
Figure Space Increment ..................................................................................................................... 75
Internal Leading ................................................................................................................................ 76
Kerning ........................................................................................................................................... 76
Kerning Pair Character 1 .................................................................................................................... 76
Kerning Pair Character 2 .................................................................................................................... 77
Kerning Pair X-Adjust......................................................................................................................... 77
Maximum Ascender Height ................................................................................................................. 78
Maximum Baseline Extent................................................................................................................... 78
Maximum Baseline Offset ................................................................................................................... 79
Maximum Character Box Height ........................................................................................................... 79
Maximum Character Box Width ............................................................................................................ 80
Maximum Character Increment ............................................................................................................ 80
Maximum Descender Depth ................................................................................................................ 81
Maximum Lowercase Ascender Height .................................................................................................. 81
Maximum Lowercase Descender Depth ................................................................................................. 82
Maximum V(y) .................................................................................................................................. 82
Maximum W(y) ................................................................................................................................. 83
Minimum A-space ............................................................................................................................. 83
Nominal Character Increment .............................................................................................................. 84
Space Character Increment................................................................................................................. 85
Subscript Vertical Font Size ................................................................................................................. 85
Subscript X-Axis Offset....................................................................................................................... 86
Superscript Vertical Font Size .............................................................................................................. 86
Superscript X-Axis Offset .................................................................................................................... 87
Throughscore Position ....................................................................................................................... 87
Throughscore Width .......................................................................................................................... 88
Underscore Position .......................................................................................................................... 88
Underscore Width ............................................................................................................................. 89
Uniform A-space ............................................................................................................................... 89
Uniform Baseline Offset...................................................................................................................... 89
Uniform Character Increment............................................................................................................... 90
Table of Contents

## Page 16

xvi FOCA Reference
Character-Metric Parameters ................................................................................................................... 91
A-space .......................................................................................................................................... 91
Ascender Height ............................................................................................................................... 91
Baseline Offset ................................................................................................................................. 92
B-space .......................................................................................................................................... 92
Character Box Height......................................................................................................................... 93
Character Box Width.......................................................................................................................... 93
Character Increment .......................................................................................................................... 94
C-space .......................................................................................................................................... 94
Descender Depth .............................................................................................................................. 95
Graphic Character Global Identifier ....................................................................................................... 95
Character-Shape Parameters .................................................................................................................. 96
Design Resolution X .......................................................................................................................... 96
Design Resolution Y .......................................................................................................................... 96
Linkage Code................................................................................................................................... 97
Object Type ..................................................................................................................................... 97
Pattern Data .................................................................................................................................... 98
Pattern Data Alignment Code .............................................................................................................. 98
Pattern Data Alignment Value .............................................................................................................. 99
Pattern Data Count............................................................................................................................ 99
Pattern Data Offset.......................................................................................................................... 100
Pattern T echnology Identifier ............................................................................................................. 100
Precedence Code ........................................................................................................................... 101
Shape Pattern Index ........................................................................................................................ 101
Writing Direction Code ..................................................................................................................... 102
Character-Mapping Parameters.............................................................................................................. 103
Code Page Description..................................................................................................................... 103
Code Page Global Identifier............................................................................................................... 103
Code Point .................................................................................................................................... 103
Encoding Scheme ........................................................................................................................... 104
Graphic Character Identifier Type ....................................................................................................... 105
Graphic Character GID Length........................................................................................................... 106
Invalid Coded Character ................................................................................................................... 106
No Increment ................................................................................................................................. 106
No Presentation .............................................................................................................................. 107
Number of Coded Graphic Characters Assigned .................................................................................... 107
Section Number .............................................................................................................................. 107
Space Character Code Point ............................................................................................................. 108
Space Character Section Number....................................................................................................... 108
Unspecified Coded Character Identifier................................................................................................ 109
Chapter 6. Font Interchange Formats . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 111
AFP System Font Resource.................................................................................................................... 111
Objects .......................................................................................................................................... 111
Coded Font ................................................................................................................................ 111
Code Page .................................................................................................................................113
Font Character Set.......................................................................................................................114
Associating Character Identifiers.................................................................................................117
Structured Fields..............................................................................................................................119
BCF – D3A88A – Begin Coded Font ............................................................................................... 125
BCP – D3A887 – Begin Code Page ................................................................................................ 126
BFN – D3A889 – Begin Font ......................................................................................................... 128
CFC – D3A78A – Coded Font Control ............................................................................................. 130
CFI – D38C8A – Coded Font Index................................................................................................. 131
CPC – D3A787 – Code Page Control .............................................................................................. 134
CPD – D3A687 – Code Page Descriptor .......................................................................................... 138
CPI – D38C87 – Code Page Index.................................................................................................. 140
ECF – D3A98A – End Coded Font.................................................................................................. 143
ECP – D3A987 – End Code Page................................................................................................... 144
EFN – D3A989 – End Font............................................................................................................ 145
FNC – D3A789 – Font Control ....................................................................................................... 146
FND – D3A689 – Font Descriptor ................................................................................................... 152
FNG – D3EE89 – Font Patterns ..................................................................................................... 156
Table of Contents

## Page 17

FOCA Reference xvii
FNI – D38C89 – Font Index........................................................................................................... 160
FNM – D3A289 – Font Patterns Map............................................................................................... 164
FNN – D3AB89 – Font Name Map .................................................................................................. 165
FNN example ........................................................................................................................ 166
FNO – D3AE89 – Font Orientation.................................................................................................. 168
FNP – D3AC89 – Font Position ...................................................................................................... 172
NOP – D3EEEE – No Operation..................................................................................................... 175
Triplets ......................................................................................................................................... 176
X'02' – Fully Qualified Name Triplet................................................................................................. 177
X'62' – Local Date and Time Stamp Triplet........................................................................................ 178
X'63', Type 1 – CRC Resource Management Triplet ........................................................................... 180
X'63', Type 2 – Font Resource Management Triplet ............................................................................ 181
X'64' – Object Origin Identifier Triplet............................................................................................... 183
X'65' – User Comment Triplet ........................................................................................................ 184
X'6D' – Extension Font Triplet ........................................................................................................ 185
X'79' – Metric Adjustment Triplet .................................................................................................... 186
SAA CPI System Font Resource............................................................................................................. 188
IPDS Device Font Resource .................................................................................................................. 188
MO:DCA Presentation Document Font Reference ...................................................................................... 188
RFT-DCA Revisable Form Document Font Reference ................................................................................. 188
SAA CPI System Font Reference............................................................................................................ 188
IPDS Device Font Reference ................................................................................................................. 188
Chapter 7. Compliance Requirements . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 189
Using National Language Support........................................................................................................... 190
Appendix A. AFP System Font Structured-Field and Triplet Summary . . . . . . . . . . . . . . . . 191
Appendix B. Mapping of ISO Parameters. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 193
Appendix C. Pattern Technology Information . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 201
CID Keyed Outline Font T echnology ........................................................................................................ 201
Type 1 PFB Outline Font T echnology ....................................................................................................... 201
TrueType/OpenType Outline Font T echnology ........................................................................................... 202
Laser Matrix N-Bit Wide Horizontal Sections.............................................................................................. 202
Notices . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 203
Trademarks........................................................................................................................................ 204
Glossary . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 205
Index . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 241
Table of Contents

## Page 18

xviii FOCA Reference

## Page 19

Copyright © AFP Consortium 1998, 2015 xix
Figures
1. Presentation Environment ........................................................................................................................ 1
2. Presentation Model ................................................................................................................................ 3
3. Presentation Page.................................................................................................................................. 5
4. Representation of a Graphic Character ....................................................................................................... 7
5. Relationship of Code Points to Graphic Characters........................................................................................ 8
6. FOCA Information Processing Environment ............................................................................................... 10
7. Font Information in a Library ................................................................................................................... 11
8. Font Resource Requirements for Font Processing T asks .............................................................................. 15
9. Font Reference Model General Flow ........................................................................................................ 18
10. Creation of a Document ....................................................................................................................... 18
11. Editor Determination of Font Availability................................................................................................... 19
12. Font Services Access to Font Information ................................................................................................ 19
13. Formatter Access to Measurement Information ......................................................................................... 20
14. Presentation Services Access to Font Information ..................................................................................... 21
15. Character Coordinate System ............................................................................................................... 33
16. Relative Unit as the Unit-Em ................................................................................................................. 34
17. Directions ......................................................................................................................................... 36
18. Bounded Character Box for the Latin Letter ‘h’ .......................................................................................... 36
19. Character Baseline ............................................................................................................................. 37
20. A Character Shape Presented in Pels ..................................................................................................... 37
21. Character Reference Point ................................................................................................................... 37
22. Character Escapement Point ................................................................................................................ 38
23. A-space............................................................................................................................................ 38
24. B-space............................................................................................................................................ 39
25. C-space ........................................................................................................................................... 39
26. Character Increment ........................................................................................................................... 40
27. An Example of Kerning ........................................................................................................................ 40
28. Ascender Height ................................................................................................................................ 41
29. Descender Depth ............................................................................................................................... 41
30. Baseline Extent: Subscript Character ...................................................................................................... 41
31. Baseline Extent: Superscript Character ................................................................................................... 42
32. Baseline Extent: Character on the Baseline .............................................................................................. 42
33. Slope 0° ........................................................................................................................................... 42
34. Slope 17.5° ....................................................................................................................................... 42
35. An Illustration of Vertical Size and Internal Leading .................................................................................... 43
36. An Illustration of External Leading .......................................................................................................... 44
37. Overscore, Throughscore, and Underscore .............................................................................................. 46
38. 0° Rotation ........................................................................................................................................ 47
39. 90° Rotation ...................................................................................................................................... 47
40. 180° Rotation .................................................................................................................................... 48
41. 270° Rotation .................................................................................................................................... 48
42. Rotating Baseline and Characters .......................................................................................................... 48
43. Two Rotations of a Kanji Character......................................................................................................... 49
44. 0° Character Rotation for Horizontal Writing ............................................................................................. 49
45. 270° Character Rotation for Vertical Writing.............................................................................................. 50
46. Example of Score Positioning................................................................................................................ 50
47. Example of Hebrew T ext ...................................................................................................................... 51
48. FOCA and ISO Coordinate System Relationship ....................................................................................... 52
49. ISO Hierarchical Naming Tree ............................................................................................................... 53
50. Example of V(y) and W(y) Parameters .................................................................................................... 82
51. Structured Fields for Coded Fonts .........................................................................................................112
52. Structured Fields for Code Pages..........................................................................................................114
53. Structured Fields for Font Character Sets ...............................................................................................116
54. Associating Character Identifiers with Code Points ...................................................................................117
55. Associating Character IDs with Raster Pattern Addresses ..........................................................................117
56. EBCDIC Code Page 500 With Character Set 103 .................................................................................... 121
Figure List

## Page 20

xx FOCA Reference

## Page 21

Copyright © AFP Consortium 1998, 2015 xxi
Tables
1. AFP Consortium Architecture References ................................................................................................. viii
2. Additional AFP Consortium Documentation ............................................................................................... viii
3. AFP Font-Related Documentation ........................................................................................................... viii
4. UP
3I™ Architecture Documentation......................................................................................................... viii
5. AFP Parameters Mapped to ISO Properties for System-Level Font Resources .................................................. 23
6. AFP Parameters Mapped to ISO Properties for Device-Level Font Resources ................................................... 24
7. AFP Parameters Mapped to ISO Properties for Revisable-Document Font References ....................................... 25
8. AFP Parameters Mapped to ISO Properties for Presentation-Document Font References.................................... 26
9. AFP Parameters Mapped to ISO Properties for Device Data Stream Font References ........................................ 27
10. Unit-Base Values................................................................................................................................ 35
11. Space Character Code Point Used in FOCA Fonts................................................................................... 108
12. Notations in the Type Column in Structured Field T ables ........................................................................... 120
13. Structured Field Introducer ................................................................................................................. 122
14. Structured-Field Identifiers ................................................................................................................. 122
15. Relationship Between FNC Unit of Measure and Font Resolution Fields ....................................................... 151
16. FNN Example .................................................................................................................................. 166
17. Sample Lines-Per-Inch to Pel Conversions ............................................................................................ 171
18. Triplet Syntax .................................................................................................................................. 176
19. Summary of FOCA Triplets ................................................................................................................. 176
20. Examples of the Date Fields in a Local Date and Time Stamp Triplet ........................................................... 179
21. Examples of the Date Fields in a Font Resource Management Triplet .......................................................... 182
22. AFP System Font Structured-Field Summary.......................................................................................... 191
23. AFP System Font Triplet Summary....................................................................................................... 191
24. Mapping of ISO Font Parameters ......................................................................................................... 193
Table List

## Page 22

xxii FOCA Reference

## Page 23

Copyright © AFP Consortium 1998, 2015 1
