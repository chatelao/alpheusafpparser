## Page 1

Advanced Function Presentation Consortium
Data Stream and Object Architectures
Image Object Content Architecture
Reference
AFPC-0003-09

## Page 2

Copyright © AFP Consortium 2010, 2024 ii
Note
Before using this information, read the information in “Notices” on page 183.
AFPC-0003-09
Ninth Edition (July 2024)
This edition applies to the Image Object Content Architecture. It is the third edition produced by the AFP Consortium™
(AFPC™) and replaces and makes obsolete the previous edition (AFPC-0003-08 ). This edition remains current until a new
edition is published.
T echnical changes are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no
technical significance are not noted. For a detailed list of changes, see “Changes in This Edition” on page ix.
Internet
Visit our home page, where this publication and others are available:
www.afpconsortium.org

## Page 3

Copyright © AFP Consortium 2010, 2024 iii
Preface
This book describes the functions and services associated with Image Object Content Architecture (IOCA). It is
a reference, not a tutorial. It complements individual product publications, but does not describe product
implementations of the architecture.
Who Should Read This Book
This book is for systems programmers and other developers who develop or adapt a product or program to
interoperate with other presentation products in an Advanced Function Presentation™ environment.
AFP Consortium (AFPC)
The AFP Consortium is an international group bringing together voices from across the printing and
presentation industry to keep the AFP™ architecture up to date and continually improving. AFP Consortium
members, often market competitors, work together to ensure this stable, efficient, flexible architecture
continues to thrive, even as the world of printing and presentation changes.
The Advanced Function Presentation (AFP) architectures began as the strategic, general purpose document
and information presentation architecture for the IBM
® Corporation. The first specifications and products go
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
® Solutions Company, an IBM/Ricoh® joint venture;
currently Ricoh holds the founding member position. In February 2009, the consortium was incorporated under
a new set of bylaws with tiered membership and shared governance resulting in the creation of a formal open
standards body called the AFP Consortium (AFPC). Ownership of and responsibility for the AFP architectures
was transferred at that time to the AFP Consortium.

## Page 4

iv IOCA Reference
How to Use This Book
This book contains the following sections:
• Chapter 1, “A Presentation Architecture Perspective”, provides a brief overview of Presentation Architecture.
• Chapter 2, “Introduction to IOCA”, discusses the background of image processing and introduces IOCA.
• Chapter 3, “IOCA Overview”, discusses concepts involved in image processing.
• Chapter 4, “Formats and Codes”, shows formats used by IOCA, and code points assigned to and reserved
for IOCA.
• Chapter 5, “IOCA Image Segment”, describes the components of the IOCA entity.
• Chapter 6, “Exception Conditions and Actions”, lists exceptions to the IOCA definitions, and standard actions
to take when exceptions occur.
• Chapter 7, “Compliance”, describes the function sets that IOCA defines.
• Appendix A, “Compression and Recording Algorithms”, discusses compression and recording algorithms
that IOCA supports.
• Appendix B, “Bilevel, Grayscale, and Color Images”, summarizes how to specify these different types of
images.
• Appendix C, “IOCA Tile Resource”, describes the structure and use of tile resources.
• Appendix D, “MO:DCA Environment”, describes how the IOCA Image Segments are carried in the
MO:DCA™ data stream controlling environment.
• Appendix E, “IPDS Environment”, describes how the IOCA Image Segments are carried in the IPDS™
architecture controlling environment.
• Appendix F , “Notes for IOCA Generators”, discusses issues that should be considered when generating
efficient IOCA for high speed printing.
• Appendix G, “Retired Architecture”, describes parts of IOCA that have been retired.
• Glossary defines terms used in this book.
How to Use This Book

## Page 5

IOCA Reference v
How to Read the Syntax Diagrams
Throughout this book, syntax for IOCA is shown in tables, laid out as follows:
Offset Type Name Range Meaning M/O
Byte
offset
Data
type
Name, if
applicable
Range of valid
values, if
applicable
Meaning or purpose of the parameter M or
O
Bit offset
The “M/O” column indicates whether the parameter is mandatory or optional.
The syntax includes the following basic data types:
BITS Bit string
CHAR Character string
CODE Architected constant
UBIN Unsigned binary
The following is an example of IOCA syntax.
Offset Type Name Range Meaning M/O
0 CODE ID X'9B' IDE Structure parameter M
1 UBIN LENGTH X'06' – X'14' Length of the parameters to follow M
2 BITS FLAGS M
Bit 0 ASFLAG B'0' – B'1' Additive or Subtractive:
B'0' Additive
B'1' Subtractive
Bit 1 GRAYCODE B'0' – B'1' Gray coding:
B'0' Off
B'1' On
Bits 2–7 B'000000' Reserved; should be zero
3 CODE FORMAT X'01', X'02',
X'04', X'12',
X'8n'
Color model:
X'01' RGB
X'02' YCrCb
X'04' CMYK
X'12' YCbCr
X'8n' nColor (X'2' ≤ n ≤ X'F')
All other values are reserved.
M
4–6 X'000000' Reserved; should be zero M
7 UBIN SIZE1 X'00' – X'FF' Number of bits/IDE for component 1 M
8 UBIN SIZE2 X'00' – X'FF' Number of bits/IDE for component 2 O
9 UBIN SIZE3 X'00' – X'FF' Number of bits/IDE for component 3 O
10 UBIN SIZE4 X'00' – X'FF' Number of bits/IDE for component 4 O
11 UBIN SIZE5 X'00' – X'FF' Number of bits/IDE for component 5 O
...
21 UBIN SIZE15 X'00' – X'FF' Number of bits/IDE for component 15 O
How to Read the Syntax Diagrams

## Page 6

vi IOCA Reference
Notation Conventions
Throughout this document, the following notation conventions apply:
• Bytes are numbered from left to right beginning with byte 0, which is considered the high order byte position.
For example, a three-byte field consists of byte 0, byte 1, and byte 2.
• Each byte is composed of eight bits.
• Bits in a single byte are numbered from left to right beginning with bit 0, the most significant bit, and
continuing through bit 7, the least significant bit.
• When bits from multiple consecutive bytes are considered together, the first byte, byte 0, contains bits 0 to 7,
and byte n contains bits n×8 to n×8+7.
• A negative number is expressed by the two's-complement form of its positive number. The two's complement
of a number is obtained by first inverting every bit of the number and then adding one to the inverted number.
In the syntax summary diagrams, the conventions in the parameter groupings are:
• The identifier is shown for all the parameters. If the identifier is missing, the item is not a parameter, but a
grouping of parameters, for example, a tile.
• The following symbols have special meanings:
[ ] Brackets indicate optional parameters. When a parameter is shown without brackets, it must appear
if the corresponding grouping is present. For example, if a tile is being specified, Tile Position must
appear.
+ Plus signs indicate that a group of successive parameters may appear in any order relative to each
other.
(S) The enclosed (S) indicates that the parameter may be repeated. When it is present on a required
parameter, at least one instance of the parameter is required, but multiple instances of it may occur.
Notation Conventions

## Page 7

IOCA Reference vii
Related Publications
Several other publications can help you understand the architecture concepts described in this book. AFP
Consortium publications and a few other AFP publications are available on the AFP Consortium website,
http://www.afpconsortium.org.
Table 1. AFP Consortium Architecture References
AFP Architecture Publication Order Number
AFP Programming Guide and Line Data Reference AFPC-0010
Bar Code Object Content Architecture™ Reference AFPC-0005
Color Management Object Content Architecture Reference AFPC-0006
Font Object Content Architecture Reference AFPC-0007
Graphics Object Content Architecture for Advanced Function Presentation Reference AFPC-0008
Image Object Content Architecture Reference AFPC-0003
Intelligent Printer Data Stream™ Reference AFPC-0001
Metadata Object Content Architecture Reference AFPC-0013
Mixed Object Document Content Architecture™ (MO:DCA) Reference AFPC-0004
Presentation Text Object Content Architecture Reference AFPC-0009
Table 2. Additional AFP Consortium Documentation
AFPC Publication Order Number
AFP Color Management Architecture™ (ACMA™) G550-1046 (IBM)
AFPC Company Abbreviation Registry AFPC-0012
AFPC Font Typeface Registry AFPC-0016
BCOCA™ Frequently Asked Questions AFPC-0011
Metadata Guide for AFP AFPC-0018
MO:DCA-L: The OS/2 PM Metafile (.met) Format AFPC-0014
Presentation Object Subsets for AFP AFPC-0002
Recommended IPDS Values for Object Container Versions AFPC-0017
Further Reading
The following publications describe image compression algorithms:
• Abramson, Norman. Information Theory and Coding. New York: McGraw-Hill, 1963.
• Arps, R., T . Truong, D. Lu, R. Pasco, and T . Friedman, “A multipurpose VLSI chip for adaptive data
compression of bilevel images”. IBM Journal of Research and Development, Volume 32, No. 6 (November
1988).
• “Binary-image-manipulation Algorithms in Image View Facility”. IBM Journal of Research and Development,
vol. 31, no. 1 (January 1987).
Related Publications

## Page 8

viii IOCA Reference
• International Organization for Standardization and International Electrotechnical Commission. ISO/IEC
International Standard 10918-1. 1994.
• Composed Page Data Stream Architecture IS & TG Architecture Memorandum. AR-7262-03-POK.
Poughkeepsie, NY: IBM.
• International T elecommunications Union-T elecommunication Standardization Sector.Facsimile Coding
Schemes and Coding Control Functions for Group 4 Facsimile Apparatus. T erminal Equipment and
Protocols for T elematic Services Recommendations of the T Series, Recommendation T .6. ITU-TSS Volume
VII, Fascicle VII.3.
• ________. Standardization of Group 3 Facsimile Apparatus for Document Transmission. T erminal
Equipment and Protocols for T elematic Services Recommendations of the T Series, Recommendation T .4.
ITU-TSS Volume VII, Fascicle VII.3.
• ________. T erminal Equipment and Protocols for T elematic Services Recommendations of the T Series,
Recommendation T .81. 1993.
• Pennebaker, William B., and Joan L. Mitchell. JPEG: Still Image Data Compression Standard. New York: Van
Nostrand Reinhold, 1992. ISBN 0-442-01272-01.
• ________. “Standardization of Color Image Data Compression”. Part I. “Sequential Coding”. Proceedings
Electronic Imaging '89 East (October 2–5, 1989): 109–112.
• TIFF. Revision 6.0, Final. Aldus Corp.: June 3, 1992.
• Welch, T erry A. “A T echnique for High Performance Data Compression”. IEEE Computer, vol. 17, no. 6 (June
1984).
The following publications describe color and grayscale images:
• Commission Internationale de l'Eclairage. Colorimetry. CIE Publication no. 15-2.
• Hunt, R. The Representation of Colour in Photography, Printing and Television 5th ed. Foundation Press,
1995.
• Lucky, R. W., J. Salz, and E. J. Weldon Jr.. Principles of Data Communication (New York: McGraw-Hill,
1968).
Related Publications

## Page 9

IOCA Reference ix
Changes in This Edition
The following is a summary of the changes that have been made in this edition:
• The nColor Names parameter was added
– Syntax diagrams were updated to show the new parameter’s place
– The description of FS48 was updated to say that implementations of FS48 are very strongly
recommended to support the nColor Names parameter
– Various locations that stated that a CMR would be required to process an nColor image were updated,
since if an nColor Names parameter is included, some IOCA receivers will not need to do color
management if the colors in the nColor Names parameter match the implementation’s available colorants
– Exception conditions EC-B30F , EC-B310, and EC-B311 were added
• The glossary was updated to the latest common level
• A number of small updates were made to correct errors and increase consistency and readability
T echnical changes between this edition and the previous edition are shown in green, with a green “|” revision
bar in the left margin, as this text is.
Changes in This Edition

## Page 10

x IOCA Reference

## Page 11

Copyright © AFP Consortium 2010, 2024 xi
Contents
Preface . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
Who Should Read This Book .....................................................................................................................iii
AFP Consortium (AFPC) ...........................................................................................................................iii
How to Use This Book ............................................................................................................................. iv
How to Read the Syntax Diagrams.......................................................................................................... v
Notation Conventions.......................................................................................................................... vi
Related Publications ...............................................................................................................................vii
Further Reading.................................................................................................................................vii
Changes in This Edition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . ix
Figures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xv
Tables . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xvii
Chapter 1. A Presentation Architecture Perspective. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .1
The Presentation Environment ................................................................................................................... 1
Architecture Components.......................................................................................................................... 2
Data Streams ..................................................................................................................................... 2
Objects ............................................................................................................................................. 4
Chapter 2. Introduction to IOCA . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .7
Background ........................................................................................................................................... 7
What is IOCA? ........................................................................................................................................ 8
IOCA in Image Processing ........................................................................................................................ 9
The IOCA Process Model........................................................................................................................ 10
Chapter 3. IOCA Overview . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 11
IOCA Representation of Images ............................................................................................................... 11
Image Points ........................................................................................................................................ 13
Size and Resolution ............................................................................................................................... 14
Compression ........................................................................................................................................ 15
Image Coordinate System ....................................................................................................................... 16
Image Presentation Space ...................................................................................................................... 17
Image Tiling ......................................................................................................................................... 18
Function Sets ....................................................................................................................................... 19
Chapter 4. Formats and Codes . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 21
Formats .............................................................................................................................................. 21
Long Format .................................................................................................................................... 21
Extended Format .............................................................................................................................. 21
Code Points ......................................................................................................................................... 22
Chapter 5. IOCA Image Segment. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 23
Image Segment .................................................................................................................................... 24
Begin Segment ................................................................................................................................. 25
End Segment ................................................................................................................................... 26
Image Content ...................................................................................................................................... 27
Begin Image Content ......................................................................................................................... 28
End Image Content............................................................................................................................ 30
Image Data Parameters .......................................................................................................................... 31
Image Size ...................................................................................................................................... 32
Image Encoding................................................................................................................................ 34
IDE Size.......................................................................................................................................... 37
Band Image ..................................................................................................................................... 38
IDE Structure ................................................................................................................................... 41
nColor Names .................................................................................................................................. 45
External Algorithm Specification ........................................................................................................... 47
Image Subsampling........................................................................................................................... 51
Tiles ................................................................................................................................................... 53

## Page 12

xii IOCA Reference
Begin Tile ........................................................................................................................................ 55
End Tile .......................................................................................................................................... 56
Tile Position ..................................................................................................................................... 57
Tile Size .......................................................................................................................................... 58
Tile Set Color ................................................................................................................................... 61
Include Tile ...................................................................................................................................... 65
Tile TOC ......................................................................................................................................... 66
Transparency Masks .............................................................................................................................. 68
Begin Transparency Mask................................................................................................................... 70
End Transparency Mask ..................................................................................................................... 71
Image Data Elements ............................................................................................................................. 72
Image Data...................................................................................................................................... 73
Band Image Data .............................................................................................................................. 74
Chapter 6. Exception Conditions and Actions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 77
Exception Conditions ............................................................................................................................. 77
Image Segment Exception Conditions ................................................................................................... 77
Exception Actions.................................................................................................................................. 78
IOCA Process Model Actions ............................................................................................................... 78
Exception Conditions Causing the Common Standard Action.......................................................................... 79
Exception Conditions Causing Unique Standard Actions ................................................................................ 82
Mandatory or Optional Exception Conditions ............................................................................................... 83
Chapter 7. Compliance. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 85
Function Sets ....................................................................................................................................... 85
IOCA Function Set 10 (IOCA FS10)........................................................................................................... 87
IOCA Function Set 11 (IOCA FS11) ........................................................................................................... 89
IOCA Function Set 14 (IOCA FS14)........................................................................................................... 95
IOCA Function Set 40 (IOCA FS40)......................................................................................................... 103
IOCA Function Set 42 (IOCA FS42)......................................................................................................... 107
IOCA Function Set 45 (IOCA FS45)..........................................................................................................114
IOCA Function Set 48 (IOCA FS48)......................................................................................................... 125
Appendix A. Compression and Recording Algorithms . . . . . . . . . . . . . . . . . . . . . . . . . . . 139
Compression Algorithms ....................................................................................................................... 139
Modified ITU-TSS Modified READ Algorithm (IBM MMR-Modified Modified Read)......................................... 140
No Compression ............................................................................................................................. 140
Run Length 4 (RL4) Compression Algorithm ......................................................................................... 141
ABIC (Bilevel Q-Coder) Compression Algorithm..................................................................................... 141
TIFF algorithm 2 Compression Algorithm.............................................................................................. 142
Concatenated ABIC Compression Algorithm ......................................................................................... 142
OS/2 Image Support Compression Algorithm ........................................................................................ 142
TIFF PackBits Compression Algorithm................................................................................................. 143
TIFF LZW Compression Algorithm ...................................................................................................... 143
TIFF LZW with Differencing Predictor Compression Algorithm .................................................................. 143
Solid Fill Rectangle Compression Algorithm .......................................................................................... 144
ITU-TSS T .4 Group 3 Coding Standard (G3 MH-Modified Huffman) for Facsimile .......................................... 144
ITU-TSS T .4 Group 3 Coding Option (G3 MH-Modified READ) for Facsimile ................................................ 144
ITU-TSS T .6 Group 4 Coding Standard (G4 MMR-Modified Modified READ) for Facsimile .............................. 144
JPEG Compression Algorithms .......................................................................................................... 144
JBIG2 (Joint Bi-level Image Experts Group) Compression Algorithm .......................................................... 145
User-defined Compression Algorithm .................................................................................................. 145
Compression Algorithms and Explicit Image Dimensions ......................................................................... 146
Compression Algorithms for Different Image Types ................................................................................ 147
Recording Algorithms ........................................................................................................................... 148
RIDIC Recording Algorithm ............................................................................................................... 148
Bottom-to-T op Recording Algorithm..................................................................................................... 149
Unpadded RIDIC Recording Algorithm................................................................................................. 149
Appendix B. Bilevel, Grayscale, and Color Images . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 151
Related Image Data Parameters............................................................................................................. 151
Bilevel Images .................................................................................................................................... 151
Grayscale Images ............................................................................................................................... 152

## Page 13

IOCA Reference xiii
Color Images...................................................................................................................................... 152
Color Management .............................................................................................................................. 152
Appendix C. IOCA Tile Resource. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 153
Appendix D. MO:DCA Environment. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 155
IOCA Image Object in MO:DCA Data Stream ............................................................................................ 155
Compliance with MO:DCA Interchange Sets ............................................................................................. 155
Image Structured Fields ........................................................................................................................ 156
Image Data Descriptor (IDD) ............................................................................................................. 156
Image Picture Data (IPD) .................................................................................................................. 165
Appendix E. IPDS Environment . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 167
IOCA Image Objects in an IPDS Architecture ............................................................................................ 167
IPDS IO-Image Command Set ............................................................................................................... 168
Write Image Control 2 ...................................................................................................................... 168
Write Image 2................................................................................................................................. 169
Exception Handling ......................................................................................................................... 169
Unsupported IOCA Function in an IPDS Environment ............................................................................. 169
Additional Related Commands ............................................................................................................... 170
Special Notes ..................................................................................................................................... 170
Image Segment in IO-Image Command Set .......................................................................................... 170
Interpretation of IDE Value ................................................................................................................ 170
Image Presentation Space Mapping.................................................................................................... 171
Appendix F . Notes for IOCA Generators . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 173
General Considerations ........................................................................................................................ 173
Function Set 42 Considerations .............................................................................................................. 174
Function Set 45 and 48 Considerations .................................................................................................... 175
Appendix G. Retired Architecture . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
Image LUT-ID ..................................................................................................................................... 177
Syntax .......................................................................................................................................... 177
Exception Conditions ....................................................................................................................... 177
Image Structured Fields in MO:DCA-L Data Stream .................................................................................... 178
IDD in MO:DCA-L Data Stream .......................................................................................................... 178
IPD in MO:DCA-L Data Stream .......................................................................................................... 178
IOCA Function Set 20 (IOCA FS20)......................................................................................................... 179
Exception Condition EC-0002 ................................................................................................................ 181
Notices . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 183
Trademarks........................................................................................................................................ 184
Glossary . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 185
Index . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 227

## Page 14

xiv IOCA Reference

## Page 15

Copyright © AFP Consortium 2010, 2024 xv
Figures
1. Presentation Environment ........................................................................................................................ 1
2. Presentation Model ................................................................................................................................ 3
3. Presentation Page.................................................................................................................................. 5
4. Images and IOCA................................................................................................................................... 8
5. Steps in Image Processing ....................................................................................................................... 9
6. IOCA Process Model and the Controlling Environments................................................................................ 10
7. Image Concept and IOCA Representation ................................................................................................. 12
8. Image Point and IDE ............................................................................................................................. 13
9 Image Resolution .................................................................................................................................. 14
10. Image Compression ............................................................................................................................ 15
11 Image Coordinate System ..................................................................................................................... 16
12. Image Presentation Space ................................................................................................................... 17
13. Tiles of an Image ................................................................................................................................ 18
14. T op Three Lines of a Bilevel Image ......................................................................................................... 35
15. Example of a Four-Bit Single-Band Image with No Padding Bit ..................................................................... 38
16. Example of a Four-Bit Four-Band Image with No Padding Bit ....................................................................... 39
17. IDE Progression ................................................................................................................................. 48
18. Scan Line Format ............................................................................................................................. 141
19. RIDIC Recording Algorithm................................................................................................................. 148
20. Bottom-to-T op Recording Algorithm ...................................................................................................... 149

## Page 16

xvi IOCA Reference

## Page 17

Copyright © AFP Consortium 2010, 2024 xvii
Tables
1. AFP Consortium Architecture References ..................................................................................................vii
2. Additional AFP Consortium Documentation ................................................................................................vii
3. IOCA Code Points ................................................................................................................................ 22
4. Gray Code Values (Decimal)................................................................................................................... 42
5. Transparency Mask Structure ................................................................................................................. 69
6. Function Set Identification ...................................................................................................................... 85
7. Function Set 10 Structure ...................................................................................................................... 87
8. Function Set 11 Structure....................................................................................................................... 89
9. Function Set 14 Structure ...................................................................................................................... 95
10. Transparency Mask Structure ............................................................................................................... 95
11. Function Set 40 Structure ................................................................................................................... 103
12. Tile Structure ................................................................................................................................... 103
13. Function Set 42 Structure ................................................................................................................... 107
14. Tile Structure ................................................................................................................................... 107
15. Function Set 45 Structure ....................................................................................................................114
16. Image Content Structure .....................................................................................................................114
17. Data Tile Structure .............................................................................................................................114
18. Referencing Tile Structure ...................................................................................................................114
19. IOCA Tile Resource Structure ..............................................................................................................115
20. Transparency Mask Structure ..............................................................................................................115
21. Function Set 48 Structure ................................................................................................................... 125
22. Image Content Structure .................................................................................................................... 125
23. Data Tile Structure ............................................................................................................................ 125
24. Referencing Tile Structure .................................................................................................................. 125
25. IOCA Tile Resource Structure ............................................................................................................. 126
26. Transparency Mask Structure ............................................................................................................. 126
27. RL4 Code Words.............................................................................................................................. 141
28. Image Compression Algorithms Supported by IOCA ................................................................................ 146
29. Commonly-used Compression Algorithms for Each Data Type ................................................................... 147
30. IOCA Tile Resource Structure ............................................................................................................. 153
31. Function Set 20 Structure ................................................................................................................... 179

## Page 18

xviii IOCA Reference

## Page 19

Copyright © AFP Consortium 2010, 2024 1
