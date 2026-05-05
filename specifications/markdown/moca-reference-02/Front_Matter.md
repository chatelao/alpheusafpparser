## Page 1

Advanced Function Presentation Consortium
Data Stream and Object Architectures
Metadata Object Content Architecture
Reference
AFPC-0013-02

## Page 2

Copyright © AFP Consortium 2014–2024 ii
Note
Before using this information, read the information in “Notices” on page 19.
AFPC-0013-02
Second Edition (December 2024)
This edition applies to the Metadata Object Content Architecture. It is the second edition produced by the AFP
Consortium™ (AFPC™) and replaces and makes obsolete the previous edition (AFPC-0013-01) . This edition remains
current until a new edition is published.
T echnical changes are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no
technical significance are not noted. For a detailed list of changes, see “Changes in This Edition ” on page vii.
Internet
Visit our home page, where this publication and others are available:
www.afpconsortium.org

## Page 3

Copyright © AFP Consortium 2014–2024 iii
Preface
This book describes the functions and services associated with the Metadata Object Content Architecture
(MOCA).
This book is a reference, not a tutorial. It complements individual product publications, but does not describe
product implementations of the architecture.
Who Should Read This Book
This book is for system programmers and other developers who need such information to develop or adapt a
product or program to interoperate with other presentation products in an Advanced Function Presentation™
(AFP™) environment.
AFP Consortium (AFPC)
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
sessions to create the AFP Color Management Object Content Architecture™ (CMOCA™). A major milestone
was reached by the AFP Color Consortium with the initial official release of the CMOCA specification in May
2006.
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

iv MOCA Reference
How to Use This Book
This document is divided into five chapters:
• Chapter 1, “A Presentation Architecture Perspective”, on page 1 introduces the AFPC presentation
architectures and describes the role of data streams and data objects.
• Chapter 2, “Introduction to MOCA”, on page 7 introduces the goals and scope of the Metadata Object
Content Architecture.
• Chapter 3, “Metadata Concepts”, on page 9 discusses metadata concepts as they relate to AFP .
• Chapter 4, “Metadata Object (MO)”, on page 11 specifies the MO syntax and semantics.
• Chapter 5, “MO Header Attributes”, on page 15 registers and describes the supported MO types, formats,
and compression.
• Chapter 6, “Compliance ”, on page 17 describes the subsets that MOCA defines.
The “Glossary” on page 21 defines some of the terms used in this book.
How to Read the Syntax Diagrams
The basic data types used in the Metadata Object Content Architecture (MOCA) are:
UBIN Unsigned binary
UTF16 UTF-16BE characters
UNDF Undefined type
The following notation conventions apply to the MO data structures.
• Each byte contains eight bits.
• Bytes of an MO data structure are numbered beginning with byte 0. For example, a two-byte field followed by
a one-byte field would be numbered as follows:
Bytes 0–1 Field 1
Byte 2 Field 2
• Field values are expressed in hexadecimal or binary notation:
X'7FFF' = +32,767
B'0001' = 1
• Some bits or bytes are labeled reserved. The content of reserved fields is not checked by MO receivers.
However, MO generators should set reserved fields to the specified value.
How to Use This Book

## Page 5

MOCA Reference v
Related Publications
Several other publications can help you understand the architecture concepts described in this book. AFP
Consortium publications and a few other AFP publications are available on the AFP Consortium website,
http://www.afpconsortium.org
.
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
Mixed Object Document Content Architecture™ (MO:DCA™) Reference AFPC-0004
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
Recommended IPDS™ Values for Object Container Versions AFPC-0017
Table 3. Other Documentation
Publication
XMP™ (Extensible Metadata Platform) Specification, September 2005
Efficient XML Interchange (EXI) Format 1.0
RFC 1952 - GZIP file format specification version 4.3
Related Publications

## Page 6

vi MOCA Reference

## Page 7

MOCA Reference vii
Changes in This Edition
The following is a summary of the changes that have been made in this edition:
• AFP T agging metadata format added
• MS1 subset defined; the subset includes all functionality found in the first edition of the MOCA Reference
– “Compliance” chapter added to introduce the concept of subsets and to contain the MS1 subset definition
• Discussion of mapping MOCA exception conditions to IPDS exceptions added
• Discussion of how Metadata Objects are carried in IPDS added
T echnical changes between this edition and the previous edition are shown in green, with a green “|” revision
bar in the left margin, as this text is.
Changes in This Edition

## Page 8

viii MOCA Reference

## Page 9

Copyright © AFP Consortium 2014–2024 ix
Contents
Preface . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
Who Should Read This Book .....................................................................................................................iii
AFP Consortium (AFPC) ...........................................................................................................................iii
How to Use This Book ............................................................................................................................. iv
How to Read the Syntax Diagrams......................................................................................................... iv
Related Publications ................................................................................................................................ v
Changes in This Edition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . vii
Figures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xi
Tables . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xiii
Chapter 1. A Presentation Architecture Perspective. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .1
The Presentation Environment ................................................................................................................... 1
Architecture Components.......................................................................................................................... 2
Data Streams ..................................................................................................................................... 2
Objects ............................................................................................................................................. 4
Chapter 2. Introduction to MOCA . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .7
Chapter 3. Metadata Concepts . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .9
Chapter 4. Metadata Object (MO) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 11
MO Syntax........................................................................................................................................... 12
MO Semantics ...................................................................................................................................... 13
MO Exception Conditions ........................................................................................................................ 14
Chapter 5. MO Header Attributes . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 15
MOType .............................................................................................................................................. 15
DES ............................................................................................................................................... 15
MOFormat ........................................................................................................................................... 15
AFP T agging.................................................................................................................................... 15
XMP............................................................................................................................................... 15
MOCompression ................................................................................................................................... 16
NONE............................................................................................................................................. 16
GZIP .............................................................................................................................................. 16
EXI ................................................................................................................................................ 16
Chapter 6. Compliance. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 17
MOCA Subsets ..................................................................................................................................... 17
MS1 Subset ......................................................................................................................................... 17
Notices . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 19
Trademarks.......................................................................................................................................... 20
Glossary . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 21
Index . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 25

## Page 10

x MOCA Reference

## Page 11

Copyright © AFP Consortium 2014–2024 xi
Figures
1. Presentation Environment ........................................................................................................................ 1
2. Presentation Model ................................................................................................................................ 3
3. Presentation Page.................................................................................................................................. 5

## Page 12

xii MOCA Reference

## Page 13

Copyright © AFP Consortium 2014–2024 xiii
Tables
1. AFP Consortium Architecture References ................................................................................................... v
2. Additional AFP Consortium Documentation ................................................................................................. v
3. Other Documentation .............................................................................................................................. v
4. MO Syntax ......................................................................................................................................... 12

## Page 14

xiv MOCA Reference

## Page 15

Copyright © AFP Consortium 2014–2024 1
