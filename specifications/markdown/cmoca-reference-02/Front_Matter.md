## Page 1

Advanced Function Presentation Consortium
Data Stream and Object Architectures
Color Management
Object Content Architecture
Reference
AFPC-0006-02

## Page 2

Copyright © AFP Consortium 2006, 2025 ii
Note:
Before using this information, read the information in “Notices” on page 127.
AFPC-0006-02
Third Edition (May 2025)
This edition applies to the Color Management Object Content Architecture™ (CMOCA™). It is the second edition produced
by the AFP Consortium™ (AFPC™) and replaces and makes obsolete the previous edition (AFPC-0006-01 ). This edition
remains current until a new edition is published. This publication also applies to any subsequent releases of Advanced
Function Presentation™ (AFP™) products that use the CMOCA architecture until otherwise indicated in a new edition.
T echnical
changes are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no
technical significance are not noted. For a detailed list of changes, see “Changes in This Edition” on page vii.
Internet
Visit our home page: www.afpconsortium.org

## Page 3

Copyright © AFP Consortium 2006, 2025 iii
Preface
This book describes the functions and services associated with the Color Management Object Content
Architecture (CMOCA).
This book is a reference, not a tutorial. It complements individual product publications, but does not describe
product implementations of the architecture.
Who Should Read This Book
This book is for system programmers and other developers who need such information to develop or adapt a
product or program to interoperate with other presentation products in an Advanced Function Presentation
(AFP) environment.
AFP Consortium
The AFP Consortium is an international group bringing together voices from across the printing and
presentation industry to keep the AFP architecture up to date and continually improving. AFP Consortium
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
sessions to create the AFP Color Management Object Content Architecture (CMOCA). A major milestone was
reached by the AFP Color Consortium with the initial official release of the CMOCA specification in May 2006.
Since the cooperation between the members of the AFP Color Consortium turned out to be very effective and
valuable, it was decided to broaden the scope of the consortium efforts and IBM soon announced its plans to
open up the complete scope of the AFP architecture to the consortium. In June 2007, IBM's role as founding
member of the consortium was transferred to the InfoPrint
® Solutions Company, an IBM/Ricoh® joint venture;
currently Ricoh holds the founding member position . In February 2009, the consortium was incorporated under
a new set of bylaws with tiered membership and shared governance resulting in the creation of a formal open
standards body called the AFP Consortium (AFPC). Ownership of and responsibility for the AFP architectures
was transferred at that time to the AFP Consortium.

## Page 4

iv CMOCA Reference
Publication History
The CMOCA Reference was first published by IBM in 2006 and has had several enhancements and updates
since that time.
First Edition published by IBM Corporation
S550-0511-00 dated May 2006
Second Edition published by AFP Consortium
AFPC-0006-01 dated April 2012
This previous edition provided enhanced detail to support the CMOCA products that were
introduced in the years 2006 through 2011 and to support the work of the AFP Consortium.
Specifically, the following new function and clarification was provided:
• Expanded range of valid values for MediaFinish and MediaColor and provide AFPC
suggested values
• ICC DeviceLink CMR
• ICC Profile Filename tag
• Indexed CMR refinements including new exception codes X'12' and X'13'
• Media finish values for coated, commodity, newsprint, and treated media
• Partial Support of TTC & HT CMRs
• Passthrough CMR
• Registered AFP-Consortium-provided standard audit CMRs
• Removed Color Conversion CMR subset X'0A' (Abstract Profile)
• Clarifications for:
– Array Width and Array Height tag descriptions
– Custom values for media-finish and media-color fields in the CMR header
– Default audit CMRs color-space descriptions for grayscale and YCbCr/YCrCb
– Named and highlight colors plus a recommendation for OCA black
– Padding and use of device-specific values in the CMR header
– Removed EC-xxxx05 (Invalid Count Value) exception conditions that were covered by
EC-xxxx11 (Inconsistent T ag Contents)
How to Use This Book
This document is divided into six chapters and three appendixes:
• Chapter 1, “A Presentation Architecture Perspective”, on page 1 introduces the AFP presentation
architectures and describes the role of data streams and data objects.
• Chapter 2, “Introduction to CMOCA”, on page 7 introduces the goals and purposes of the Color Management
Object Content Architecture.
• Chapter 3, “Color Management Resource (CMR)”, on page 9 discusses the format of the Color Management
Resource (CMR) header and how CMRs are used to process data.
• Chapter 4, “CMR Types”, on page 25 defines each type of CMR.
• Chapter 5, “CMR Data Architecture”, on page 37 defines tag syntax and semantics.
• Chapter 6, “CMR Processing”, on page 97 discusses how search paths are used to determine which CMRs
to use, audit and instruction and link CMRs, generic vs. device-specific CMRs, and implications for drivers.
• Appendix A, “T ag Registry”, on page 119 lists the tags that devices receiving CMRs must support.
• Appendix B, “Generic CMR Name Registry”, on page 121 lists and explains all the registered generic CMR
names.
Publication History

## Page 5

CMOCA Reference v
• Appendix C, “Compliance With Color Management Object Content Architecture”, on page 125 explains what
is required to claim CMOCA support.
The “Glossary” on page 129 defines some of the terms used within this book.
Interpreting the Syntax
The basic data types used in the Color Management Object Content Architecture (CMOCA) are:
CODE Architected constant
BITS Bit string
UBIN Unsigned binary
BYTE 8 bits
ASCII ASCII-encoded characters
UTF16 UTF-16BE characters (2-bytes each)
UNDF Undefined data type
The following notation conventions apply to the CMR data structures.
• Each byte contains eight bits.
• Bytes of a CMR data structure are numbered from left to right beginning with byte 0. The left-most byte is the
most significant; this is called big endian. For example, a two-byte field followed by a one-byte field would be
numbered as follows:
Bytes 0–1 Field 1
Byte 2 Field 2
• Bit strings are numbered from left to right beginning with 0. For example, a one-byte bit string contains bit 0,
bit 1, …, bit 7.
• For numerical binary data, bit 0 is the most significant bit.
• Field values are expressed in hexadecimal or binary notation:
X'7FFF' = +32,767
B'0001' = 1
• Some bits or bytes are labeled reserved. The content of reserved fields is not checked by CMR receivers.
However, CMR generators should set reserved fields to the specified value, if one is given, or to zero.
• Some fields or values are labeled Retired item n, where n is an identifying number. These fields or values are
reserved for a particular purpose and must not be used for any other purpose.
Interpreting the Syntax

## Page 6

vi CMOCA Reference
Related Publications
Several other publications can help you understand the architecture concepts described in this book. The AFP
Consortium publications and other AFP publications below are available on the AFP Consortium website,
www.afpconsortium.org.
Table 1. AFP Consortium Architecture References
AFP Architecture Publication Book Identification
AFP Programming Guide and Line Data Reference AFPC-0010
Bar Code Object Content Architecture™ Reference AFPC-0005
Color Management Object Content Architecture Reference AFPC-0006
Font Object Content Architecture Reference AFPC-0007
Graphics Object Content Architecture for Advanced Function Presentation Reference AFPC-0008
Image Object Content Architecture Reference AFPC-0003
Intelligent Printer Data Stream™ Reference AFPC-0001
Metadata Object Content Architecture Reference AFPC-0013
Mixed Object Document Content Architecture™ (MO:DCA™ ) Reference AFPC-0004
Presentation Text Object Content Architecture Reference AFPC-0009
Table 2. Additional AFP Consortium Documentation
AFPC Publication Book Identification
AFP Color Management Architecture™ (ACMA™) G550-1046 (IBM)
AFPC Company Abbreviation Registry AFPC-0012
AFPC Font Typeface Registry AFPC-0016
BCOCA™ Frequently Asked Questions AFPC-0011
Metadata Guide for AFP AFPC-0018
MO:DCA-L: The OS/2 PM Metafile (.met) Format AFPC-0014
Presentation Object Subsets for AFP AFPC-0002
Recommended IPDS™ Values for Object Container Versions AFPC-0017
Table 3. AFP Font-Related Documentation
Publication Book Identification
Character Data Representation Architecture Reference and Registry SC09-2190 (IBM)
Font Summary for AFP Font Collection S544-5633 (IBM)
Technical Reference for Code Pages S544-3802 (IBM)
Related Publications

## Page 7

CMOCA Reference vii
Changes in This Edition
Changes between this edition and the previous edition are marked by a vertical bar “|” in the left margin.
This edition provides enhanced detail to support the CMOCA products that were introduced in the years 2012
through 2024 and to support the work of the AFP Consortium. Specifically, the following new function and
clarification has been provided:
• Known, device-independent, process colorant names in the Colorant Identification List tag
• Clarifications for:
– How color intensities vary as the intensity values move from 0 to the maximum value
– Color Palette Named Colorants tag description
Changes in This Edition

## Page 8

viii CMOCA Reference

## Page 9

Copyright © AFP Consortium 2006, 2025 ix
Contents
Preface . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
Who Should Read This Book .....................................................................................................................iii
AFP Consortium......................................................................................................................................iii
Publication History.................................................................................................................................. iv
How to Use This Book ............................................................................................................................. iv
Interpreting the Syntax ............................................................................................................................. v
Related Publications ............................................................................................................................... vi
Changes in This Edition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . vii
Figures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xiii
Tables . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xv
Chapter 1. A Presentation Architecture Perspective. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .1
The Presentation Environment ................................................................................................................... 1
Architecture Components.......................................................................................................................... 2
Data Streams ..................................................................................................................................... 2
Objects ............................................................................................................................................. 4
Chapter 2. Introduction to CMOCA . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .7
Chapter 3. Color Management Resource (CMR) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .9
General Color Conversion Concepts............................................................................................................ 9
Understanding the Use of CMRs............................................................................................................... 10
CMR Syntax......................................................................................................................................... 12
CMR Header Semantics ......................................................................................................................... 15
Device-Specific Fields ........................................................................................................................ 16
Media-Specific Fields ......................................................................................................................... 17
CMRType Property-Specific Fields........................................................................................................ 18
CMRType=Halftone....................................................................................................................... 18
CMRtype=T one Transfer Curve........................................................................................................ 19
CMRType=Color Conversion ........................................................................................................... 20
CMRType=Link Color Conversion..................................................................................................... 21
CMRType=Indexed ....................................................................................................................... 22
CMR Data............................................................................................................................................ 22
CMR Header Exception Conditions ........................................................................................................... 23
Chapter 4. CMR Types . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 25
Halftone CMR....................................................................................................................................... 26
Subset X'01': Bilevel Point-Operation Halftone......................................................................................... 26
Subset X'02': Multilevel Point-Operation Halftone ..................................................................................... 26
Subset X'03': Bilevel Error Diffusion Halftone .......................................................................................... 26
Subset X'04': Multilevel Error Diffusion Halftone ....................................................................................... 26
T one Transfer Curve CMR ....................................................................................................................... 27
Subset X'01': T oneTransferCurve Array.................................................................................................. 27
Subset X'02': T oneTransferCurve Identity ............................................................................................... 27
Color Conversion CMR ........................................................................................................................... 28
Subset X'01': Monochrome Input Profile ................................................................................................. 29
Subset X'02': Monochrome Display Profile.............................................................................................. 29
Subset X'03': Monochrome Output Profile............................................................................................... 30
Subset X'04': Three-Component Matrix-Based Input Profile........................................................................ 30
Subset X'05': Three-Component Matrix-Based Display Profile .................................................................... 30
Subset X'06': N-Component LUT-Based Input Profile ................................................................................ 31
Subset X'07': N-Component LUT-Based Display Profile............................................................................. 31
Subset X'08': N-Component LUT-Based Output Profiles ............................................................................ 32
Subset X'09': ColorSpace Conversion Profile .......................................................................................... 32
Subset X'0A': Retired Item 3 (Abstract Profile) ......................................................................................... 32
Link Color Conversion CMR..................................................................................................................... 33

## Page 10

x CMOCA Reference
Subset X'01': LinkColorConversion LUT ................................................................................................. 34
Subset X'02': LinkColorConversion Identity ............................................................................................. 34
Subset X'03': ICC DeviceLink .............................................................................................................. 34
Indexed CMR ....................................................................................................................................... 35
Subset X'01': Multi-Output Color Spaces ................................................................................................ 35
Chapter 5. CMR Data Architecture . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 37
T ag Syntax........................................................................................................................................... 37
Exception Syntax .................................................................................................................................. 38
Coordinate System ................................................................................................................................ 39
T ag Semantics ...................................................................................................................................... 40
Comment ........................................................................................................................................ 40
Date and Time Stamp ........................................................................................................................ 41
Number of Components...................................................................................................................... 43
Halftone Subset ................................................................................................................................ 44
Array Width...................................................................................................................................... 45
Array Height..................................................................................................................................... 46
Max Image Value .............................................................................................................................. 47
Number of Device Levels .................................................................................................................... 48
Offset Tiling ..................................................................................................................................... 49
Bilevel Point-Operation Screen Data ..................................................................................................... 50
Multilevel Point-Operation Screen Data.................................................................................................. 51
Error Diffusion Filter........................................................................................................................... 52
Location of Current Pixel..................................................................................................................... 54
Raster Direction ................................................................................................................................ 55
Boundary Condition ........................................................................................................................... 57
Threshold Value................................................................................................................................ 59
Quantization Boundary T able ............................................................................................................... 60
T one Transfer Curve Subset ................................................................................................................ 62
T one Transfer Curve Length ................................................................................................................ 63
T one Transfer Curve Data ................................................................................................................... 64
Inverse T one Transfer Curve Data ........................................................................................................ 66
ICC Profile Subset............................................................................................................................. 68
ICC Profile Data................................................................................................................................ 69
ICC Profile Filename.......................................................................................................................... 71
Link Color Conversion Subset.............................................................................................................. 72
Link Audit CMR OID .......................................................................................................................... 73
Link Instruction CMR OID ................................................................................................................... 74
Link Audit CMR Name ........................................................................................................................ 75
Link Instruction CMR Name ................................................................................................................. 76
Default Rendering Intent ..................................................................................................................... 77
Link LUT Perceptual .......................................................................................................................... 78
Link LUT Media-Relative Colorimetric .................................................................................................... 80
Link LUT Saturation ........................................................................................................................... 82
Link LUT ICC-Absolute Colorimetric ...................................................................................................... 84
Link CMRE Identifier .......................................................................................................................... 86
Indexed Subset ................................................................................................................................ 87
Number of Named Colorants ............................................................................................................... 88
Color Palette Gray............................................................................................................................. 89
Color Palette CMYK........................................................................................................................... 90
Color Palette RGB............................................................................................................................. 91
Color Palette CIELAB......................................................................................................................... 92
Color Palette Named Colorants ............................................................................................................ 93
Colorant Identification List ................................................................................................................... 95
End Data......................................................................................................................................... 96
Chapter 6. CMR Processing . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 97
Overview of CMR Processing................................................................................................................... 97
“Applicable”, “Selected” CMRs ................................................................................................................. 98
Link Color Conversion CMRs ................................................................................................................... 99
Link Color Conversion CMRs Based on Audit/Instruction Color Conversion CMRs .......................................... 99
Link Color Conversions: ICC DeviceLink Subset .................................................................................... 100
Audit/Instruction/Link Color Conversion CMRs........................................................................................... 102

## Page 11

CMOCA Reference xi
Creating Link Color Conversion CMRs – LinkColorConversion LUT subset ...................................................... 103
T one Transfer Curve and Printer Calibration .............................................................................................. 105
Use of Indexed CMRs .......................................................................................................................... 107
Allowed Processing Modes.................................................................................................................... 108
Device Default CMRs ........................................................................................................................... 109
Default Instruction CMRs .................................................................................................................. 109
Default Audit CMRs ..........................................................................................................................110
Default CMRs to Replace Generic CMRs .............................................................................................. 111
Passthrough Audit Color Conversion CMRs ........................................................................................... 111
Matching Media Type of CMR With Media Type of Device .............................................................................112
Treatment of Named and Highlight Colors..................................................................................................113
Color Conversion Profiles Within TIFF , JFIF , and GIF ...................................................................................113
CMR Usage Within EPS, PDF .................................................................................................................114
Audit Color Conversions ....................................................................................................................114
Instruction Color Conversions .............................................................................................................114
Link Color Conversion CMRs..............................................................................................................115
Halftones........................................................................................................................................115
T one Transfer Curves........................................................................................................................115
Caveat...........................................................................................................................................116
Different Encodings of CIELAB................................................................................................................116
Implications for Drivers ..........................................................................................................................117
Generic CMRs Versus Device-Specific CMRs ............................................................................................117
Partial Support of TTC and HT CMRs .......................................................................................................117
Appendix A. Tag Registry . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 119
Appendix B. Generic CMR Name Registry . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 121
Registered Generic Halftone CMRs......................................................................................................... 121
Registered Generic T one Transfer Curve CMRs......................................................................................... 122
Generic T one Transfer Curve Appearance Definition............................................................................... 122
Using Generic Halftones and TTCs T ogether ............................................................................................. 124
Appendix C. Compliance With Color Management Object Content Architecture. . . . . . . . . 125
Notices . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 127
Trademarks........................................................................................................................................ 128
Glossary . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 129
Index . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 139

## Page 12

xii CMOCA Reference

## Page 13

Copyright © AFP Consortium 2006, 2025 xiii
Figures
1. Presentation Environment ........................................................................................................................ 1
2. Presentation Model ................................................................................................................................ 3
3. Presentation Page.................................................................................................................................. 5
4. Procedure for Producing Halftone Output from PCS....................................................................................... 9
5. Procedure for Converting Input Data to Full Color in PCS................................................................................ 9
6. Procedure for Creating Halftone Data from Device-Specific Color Data .............................................................. 9
7. Cartesian Coordinate System ................................................................................................................. 39
8. Illustration of Offset Tiling with Offset Tiling=2............................................................................................. 49
9. Illustration of Error Distribution with Floyd-Steinberg Filter............................................................................. 52
10. Illustration of Normal Raster and Serpentine Raster ................................................................................... 55
11. Selecting Appropriate Color-Conversion CMRs ....................................................................................... 101
12. Generic Halftone CMRs ..................................................................................................................... 121
13. Generic T one Transfer Curve CMRs ..................................................................................................... 122
14. Dot Gain as a Function of Percent Dot .................................................................................................. 123
15. Lightness as a Function of Percent Dot ................................................................................................. 123

## Page 14

xiv CMOCA Reference

## Page 15

Copyright © AFP Consortium 2006, 2025 xv
Tables
1. AFP Consortium Architecture References .................................................................................................. vi
2. Additional AFP Consortium Documentation ................................................................................................ vi
3. AFP Font-Related Documentation ............................................................................................................ vi
4. CMR Object Syntax .............................................................................................................................. 12
5. Halftone Types .................................................................................................................................... 18
6. Halftone Rotations................................................................................................................................ 19
7. ICC Profile/Device Classes for T one Transfer Curve CMRs ........................................................................... 19
8. Look-and-Feel Values ........................................................................................................................... 19
9. ICC Profile/Device Classes for Color Conversion CMRs ............................................................................... 20
10. The ICC Color Space of Data ................................................................................................................ 20
11. Output Color Spaces ........................................................................................................................... 21
12. ICC Profile Subsets for the Color Conversion CMR .................................................................................... 28
13. Mandatory ICCHeaderFields for Subset X'01': Monochrome Input Profile ....................................................... 29
14. Mandatory ICCHeaderFields for Subset X'02': Monochrome Display Profile .................................................... 29
15. Mandatory ICCHeaderFields for Subset X'03': Monochrome Output Profile ..................................................... 30
16. Mandatory ICCHeaderFields for Subset X'04': Three-Component Matrix-Based Input Profile .............................. 30
17. Mandatory ICCHeaderFields for Subset X'05': Three-Component Matrix-Based Display Profile ........................... 30
18. Mandatory ICCHeaderFields for Subset X'06': N-Component LUT-Based Input Profile ...................................... 31
19. Mandatory ICCHeaderFields for Subset X'07': N-Component LUT-Based Display Profile ................................... 31
20. Mandatory ICCHeaderFields for Subset X'08': N-Component LUT-Based Output Profiles................................... 32
21. Mandatory ICCHeaderFields for Subset X'09': ColorSpace Conversion Profile................................................. 32
22. Link Color Conversion Subsets.............................................................................................................. 33
23. List of Color Palette T ags...................................................................................................................... 35
24. CMR Data T ag Syntax ......................................................................................................................... 37
25. Date and Time Stamp T ag Syntax .......................................................................................................... 41
26. Ordering Sequence of Color Spaces....................................................................................................... 43
27. Halftone Subsets ................................................................................................................................ 44
28. Raster Direction Values ....................................................................................................................... 55
29. Boundary Condition Values................................................................................................................... 57
30. Illustration of Quantization Boundary T able............................................................................................... 60
31. Implementation of Quantization Boundary T able........................................................................................ 60
32. T one Transfer Curve Subsets ................................................................................................................ 62
33. T one Transfer Curve Length Values ........................................................................................................ 63
34. ICC Profile Subsets............................................................................................................................. 68
35. ICC Header Fields .............................................................................................................................. 69
36. Link Color Conversion Subsets.............................................................................................................. 72
37. ICC Rendering Intents ......................................................................................................................... 77
38. Link LUT Perceptual T ag Syntax ........................................................................................................... 78
39. Link LUT Media-Relative Colorimetric T ag Syntax ..................................................................................... 80
40. Link LUT Saturation T ag Syntax ............................................................................................................ 82
41. Link LUT ICC-Absolute Colorimetric T ag Syntax ....................................................................................... 84
42. Indexed CMR Subset .......................................................................................................................... 87
43. Color Palette Gray T ag Syntax............................................................................................................... 89
44. Color Palette CMYK T ag Syntax ............................................................................................................ 90
45. Color Palette RGB T ag Syntax............................................................................................................... 91
46. Color Palette CIELAB T ag Syntax .......................................................................................................... 92
47. Color Palette Named Colorants T ag Syntax.............................................................................................. 93
48. Colorant Identification List T ag Syntax..................................................................................................... 95
49. Profile Subsets in Audit and Instruction Color Conversion CMRs................................................................. 102
50. Allowed Processing Modes................................................................................................................. 108
51. T ag Registry .....................................................................................................................................119
52. CMR Architecture Compliance Requirements ......................................................................................... 125

## Page 16

xvi CMOCA Reference

## Page 17

Copyright © AFP Consortium 2006, 2025 1
