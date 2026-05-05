Chapter 6. Font Interchange Formats
Font Information Interchange, for the purpose of this chapter, is defined to be the transfer of font information
between or among processing systems, software processes, and hardware devices. You can transfer font
information between any two systems, processes, or devices as long as both parties recognize the same
format. The font information that you interchange can
be a complete font resource, selected components of a
font resource, or reference information about a font resource. For example, a host-based application might
only require the metric information for formatting a document, while a printer might require both the metric and
shape information for printing that same document. The document itself might only contain a reference to the
font resource, since the resource might already exist on both the host and printer.
The font information might be interchanged on transportable media (for example, magnetic or optical disk,
diskette, or tape), or through a communications network. The formats permitted for FOCA information
interchange depend on the scope of the interchange. If the scope of the interchange is among a known set of
products, with all sending and receiving products recognizing a commonly defined font information format, then
those products might
use any agreed upon private font resource format. If the scope of the interchange is not
known and the capabilities of receiving products is not known, then those products must use a recognized
public font information format.
The parameter definitions in FOCA permit parameters to be expressed in a variety of formats. See “Parameter
Formats” on page 55 for a brief description of three common formats supported by FOCA. In addition, the
parameter definitions in FOCA include transformation information required for support of public interchange
using formats defined in the ISO/IEC 9541 Font Information Interchange standard.
This chapter specifies the public font information interchange formats supported by this architecture.
AFP System Font Resource
The format for font resource data, to be loaded and managed by Advanced Function Presentation (AFP)
software, is defined by the following syntax specification. The syntax specification is divided into three
sections, defining objects, structured fields, and triplets. The first section describes the three AFP font resource
objects: coded fonts, code pages, and font character sets. Each AFP font resource object is composed of self-
identifying structured fields, which are unique to that resource object, and self-identifying triplets that can
occur
in multiple structured fields and/or resource objects. Structured fields and triplets contain a set of parameters
(defined by the architecture) in an architected structure of fixed and/or variable length value fields. The objects,
structured fields, and triplets in each of the following three sections are arranged in alphabetic order to aid the
reader in locating the information. The order, location, and quantity of structured fields and triplets within an
object is defined by the architecture, and must occur as specified, except where variation is specifically
permitted by the architecture.
Objects
Coded Font
A coded font is an AFP font resource object that associates AFP font character set objects with AFP code page
objects. The metric and shape information for each of the characters defined by the font is contained in the font
character set, and the information for mapping the code points used in the document data stream to the
graphic character global identifiers used in the font character set is contained in the code page. A coded font
resource does not contain either the font character set object or the code page object, but only references
those objects by name. If the font character set referenced by the coded font contains scalable outline shape
data, the coded font object might
also include the desired vertical font size and horizontal scale factor.

## Page 134

112 FOCA Reference
The structured fields that compose the coded font must appear in the following sequence (Figure 51):
1. Begin Coded Font (BCF)
Identifies the object as a Coded Font, and identifies this particular object by name.
2. Coded Font Control (CFC)
Provides control information that is used to manage and interpret the data contained in the other structured
fields of the object.
3. Coded Font Index (CFI)
Identifies one or more Font Character Set and Code Page object pairs, and associated size information for
outline font scaling.
4. End Coded Font (ECF)
Identifies the end of the Coded Font object.
5. No Operation (NOP)
Provides a means to carry comment information. One or more NOP structured fields can appear between
any two structured fields within a coded font.
Figure 51. Structured Fields for Coded Fonts
Coded Font Resource
Begin Coded Font End Coded Font
Coded Font Control Coded Font Index
Coded Font Name Coded Font Name
CFI Repeating Group Length
Constant (X'01')
Metric Adjustment Triplet (X'79')
(one entry for each section, 1-190)
Font Character Set Name
Code Page Name
V ertical Font Size
Horizontal Scale Factor
Section Number
BCF CFC CFI ECF
A single-byte coded font has only one section and associates one font character set with one single-byte code
page. Code point values X'00' to X'FF' may be used to identify up to 256 characters. A single-byte coded font
section is always identified as X'00' in the Coded Font Index structured field.
A double-byte coded font may consist of multiple sections to permit identification of all the characters in a
writing system such as Kanji. Two bytes are needed to identify each character. The first byte identifies the
section, while the second byte identifies the code point (character) within that section. The number of
characters that can be identified with a double-byte coded font is equal to the number of code points allowed in
a section (256 maximum) multiplied by the number of sections allowed (256 maximum).
Double-byte Section Numbering
Raster Coded Fonts: Sections are numbered from X'41' to X'FE' and are identified in the Coded Font Index
(CFI) structured field. The maximum number of code points that can be defined in each section is 256 (code
points X'00' to X'FF').
Outline Coded Fonts: Up to 256 sections are permitted in a double-byte code page, depending on the
encoding system specified (190 maximum for EBCDIC encoding). When specifying a double-byte outline
coded font, the CFI section number field is set to X'00', indicating all sections of the associated double-byte
code page object.

## Page 135

FOCA Reference 113
Code Page
A code page is a font resource object that associates graphic character global IDs (GCGIDs) to code points. A
single-byte code page or a double-byte code page section can define up to 256 code points. A double-byte
code page can define up to 65,536 code points. Flags for each code point show if the character is valid,
printable, and incrementing.
If a code point is not specified in the code page, the default character from the Code Page Control (CPC)
structured field is printed when that code point is used, depending on code page flags and server-specified
data check values. Only one graphic character ID (GCGID) can be assigned per code point. However, the
same GCGID can be assigned to several code points.
The structured fields that compose the code page must appear in the following sequence (Figure 52 on page
114):
1. Begin Code Page (BCP)
Identifies the object as a Code Page, and identifies this particular object by name.
2. Code Page Descriptor (CPD)
The CPD provides the IBM registered global identifiers for the code page and its associated graphic
character set.
Application Note: The CPD was optional in the past (code page could be identified by its resource name),
but is now required. Existing applications should not be impacted by the presence of this structured
field (should ignore the field). AFP products which support MO:DCA MCF/2 GRID referencing of
coded fonts and/or which support the IPDS download of code page resources might require the data
contained in the CPD structured field. Some code pages might contain the structured field but not
the global identifiers needed by these products. In such cases, the product might not have sufficient
information to continue processing.
3. Code Page Control (CPC)
The CPC gives the default character ID information for the code page. The default character is used when
a code point is referenced in text and no character ID has been assigned to that code point in the Code
Page Index (CPI) structured field.
4. Code Page Index (CPI)
The CPI matches character IDs and code points. If a code point is not matched with a character ID, the
code point used will be the default character ID specified in the CPC.
5. End Code Page (ECP)
Identifies the end of the Code Page object.
6. No Operation (NOP)
Provides a means to carry comment information. One or more NOP structured fields can appear between
any two structured fields within a code page.

## Page 136

114 FOCA Reference
Figure 52. Structured Fields for Code Pages
Code Page Resource
Begin Code Page End Code Page
Code Page Descriptor Code Page Index
Code Page Control
Code Page Name
Triplets - 62,63,64
Code Page Name
Default GCGID
Default Char. Use Flags
Repeating Group Length
Space Character Code Point
Code Page Use Flags
Code Page Description
GCGID Length
Number of code points
GCSGID
CPGID
Encoding Scheme
(one entry for each character, 1-65536)
GCGID
Character Use Flags
Code Point
BCP CPD CPC CPI ECP
Note: Up to 256 repeating groups can be used in a single-byte code page or in a double-byte code page
section. Successive CPI structured fields are used to define each double-byte code page section. Each
repeating group and CPI is sorted according to the Sort Order flag in the CPC structured field.
Font Character Set
A font character set is a font resource object that contains descriptive and metric information for the whole font,
and metric and shape information for each character identifier in the font.
The structured fields that compose the font character set must appear in the following sequence (Figure 53 on
page 116):
1. Begin Font (BFN)
Identifies the object as a Font Character Set, and identifies this particular object by name.
2. Font Descriptor (FND)
The FND describes characteristics common to all characters, such as size and stroke thickness.
3. Font Control (FNC)
The FNC specifies defaults and other information about the font character set.
4. Font Patterns Map (FNM)
For each character, the FNM specifies the raster pattern box size and the address of the character raster
pattern in the Font Patterns (FNG) structured fields. This structured field is omitted for fonts that do not
contain shape data (raster or outline).
5. Font Orientation (FNO)
The FNO includes character rotation, maximum or uniform values, and other font data. Fonts have one,
two, three, or four FNOs (one per rotation).
6. Font Position (FNP)
The FNP specifies the common metrics. Fonts have one, two, three, or four FNPs (one per rotation).
7. Font Index (FNI)
For each character in a raster font, the FNI points to an entry in the FNM structured field and provides
character positioning information. For outline fonts, the FNI defines the character increment for each
character whose character increment is different from that specified in the FNO for that rotation. The FNI

## Page 137

FOCA Reference 115
for a given character rotation is specified in the appropriate repeating group of the FNO structured field.
Fonts have one, two, three, or four FNIs (one per rotation).
8. Font Name Map (FNN)
The FNN maps the AFP character names contained in the FNI structured field to the outline font
technology character names contained in the FNG structured field. This structured field is omitted from
fonts that do not have outline fonts included in their FNGs.
9. Font Patterns (FNG)
The FNG contains the character raster pattern or outline font data. This structured field is omitted for Metric
Only Fonts (MOFs) that do not contain shape information.
10. End Font (EFN)
Identifies the end of the Font Character Set object.
11. No Operation (NOP)
Provides a means to carry comment information. One or more NOP structured fields can appear between
any two structured fields within a font character set.

## Page 138

116 FOCA Reference
Figure 53. Structured Fields for Font Character Sets
Font Character Set Resource
Begin Font End Font
Font Descriptor Font Patterns
Font Name Map (outline only)
Font Index
Font Position
Font Control
Raster Font Pattern Map
Font Orientation
Font Char. Set Name
Triplets - 62,63,64
Font Char. Set Name
Typeface Description
Weight Class
Width Class
V ertical Sizes
Horizontal Sizes
ISO Design Info.
Font Design Flags
GCSGID
FGID
Triplets - 02
Raster:
Pattern Data
------- or -------
Outline:
(repeats for each object)
Object Length
Checksum
Object ID Length
Object ID
Object Descriptor Length
Object Descriptor
Object Data
IBM Character ID Format
Technology-Specific ID Format
(one entry for each IBM ID)
GCGID
Offset to Technology-Specific ID
(one entry for each Tech ID)
Technology-Specific ID Length
Technology-Specific ID
(one entry for each character)
GCGID
Character Increment
Ascender Height
Descender Depth
FNM Index
A-Space
B-Space
C-Space
Baseline Offset
(one entry for each character rotation)
Lowercase Height
Cap-M Height
Maximum Ascender Height
Maximum Descender Depth
Underscore Width
Underscore Position
Pattern Technology ID
Font Use Flags
Metrics Units of Measure
Max. Character Box
FNO Repeating Group Length
FNI Repeating Group Length
Pattern Data Alignment Code
Raster Pattern Data Count
FNP Repeating Group Length
FNM Repeating Group Length
Shape Units of Measure
Outline Pattern Data Count
FNN Repeating Group Length
FNN Data Count
FNN Name Count
Triplets - 6D
(one entry per character, 1-1000)
Character Box Width
Character Box Height
Pattern Data Offset
(one entry for each character rotation)
Character Rotation
Maximum Baseline Offset
Maximum Character Increment
Space Character Increment
Maximum Baseline Extent
Orientation Control Flags
Em-Space Increment
Figure-Space Increment
Nominal Character Increment
Default Baseline Increment
Minimum A-Space
BFN FND FNC FNM FNO FNP FNIs FNN FNGs EFN
(1-n)
(one per rotation)
(1-4)

## Page 139

FOCA Reference 117
Associating Character Identifiers
With Code Points
The Code Page Index (CPI) structured field in the code page has repeating groups of parameters. Each
repeating group pairs a character ID with a code point.
Figure 54. Associating Character Identifiers with Code Points
Code Page
CPI
cp/ID
cp
ID
Code Point
Character ID
cp/ID. . .
With Raster Pattern Addresses
A font can have one, two, three, or four Font Indexes (FNIs), one for each of the four possible character
rotations. Each FNI in the font character set has repeating groups of parameters. Each repeating group pairs a
character ID with a raster pattern address. The following list and diagram show the relationship of the character
IDs and the raster pattern addresses.
• The FNI parameter in the Font Orientation (FNO) structured field (for the current character rotation) specifies
the FNI to be used.
• Each repeating group in the FNI pairs a character ID and an index into the Font Patterns Map (FNM)
structured field.
• The FNM contains the addresses of the character raster patterns.
• The raster patterns are in the Font Patterns (FNG) structured fields of the font character set. The character
identifiers in the FNI are used to locate each character raster pattern.
Figure 55. Associating Character IDs with Raster Pattern Addresses
Bounded-Box Font Character Set
FNIs FNM FNGs
ID/index . . . addr raster
ID
addr
Character ID
Raster Pattern Address
ID/index addr raster. . . . . .
Single-Byte Raster Coded Font Summary
• Each character in the text string is represented by a one-byte code point (for example, X'C1' for the
uppercase character A).
• The character ID that is paired with the code point in the code page is matched with the same character ID
in the font character set.
• The character ID in the font character set is, in turn, paired with the character raster pattern for the
character.

## Page 140

118 FOCA Reference
Double-Byte Raster Coded Font Summary
• Each character in the text string is represented by a two-byte code (for example, X'41C1' for section 65
uppercase character A). The first byte shows the correct section (code page/font character set pair). The
second byte is the code point.
• The character ID that is paired with the code point in the code page is matched with the same character ID
in the font character set.
• The character ID in the font character set is, in turn, paired with the character raster pattern for the
character.
With Outline Fonts
When the FNC pattern technology field indicates an outline font is included in the FNG structured field, the
FNG is preceded by a Font Name Map (FNN) structured field which provides a map between the outline font
character names and the IBM graphic character identifiers (GCGID). The Font Patterns Map (FNM) is omitted
from fonts that have outline fonts included in the FNG.
The FNN is divided into three sections. The first section described the encoding scheme used in the second
and third sections. The second section contains IBM character names (GCGID) and the offset of the
corresponding outline font character name in the third section. The second section is sorted so the GCGIDs
are in ascending order. The third section has outline font character names. Each outline font character name in
third section is preceded by a length field giving the length of the following outline font character name.
Outline Single and Double-byte Coded Font Summary
• Mapping for single-byte and double-byte outline coded fonts are each treated the same. That is, it makes no
difference how many code points are present in the code page object. The FNN structured field provides the
necessary name mapping information.
• A character's outline representation name is paired with a character's IBM GCGID using the Font Name
Map.

## Page 141

FOCA Reference 119
Structured Fields
Resource objects contain structured fields. In some environments, each structured field is preceded by a X'5A'
carriage control character, which is not part of the structured field.
Each structured field must have an introducer and usually has parameter bytes that contain control information
or raster patterns. A structured field can also include padding bytes.
Structured Field Components
X’5A ’
Structured
Field
Introducer
Structured
Field
Parameters
Structured
Field
PaddingNumber of
bytes allowed 1 8 to 263 0 to 32,759 0 to 32,759
In this publication, the structured fields are presented alphabetically, not in required sequence.
For each structured field, the following information is included:
• Purpose
• Meaning and allowed values for the parameters
• Contents of constant parameters
The section heading for each structured field gives its abbreviation, the three-byte hexadecimal code identifier,
and the name of the structured field.
The contents of each structured field are presented in a table, and additional detailed descriptions follow the
table. Only the structured field parameters are described. The X'5A', the introducer, and the optional padding
values are not included in these descriptions.
For all structured fields, the following conventions apply:
• The byte offset of each structured field parameter is given.
• The first byte in the data field (after the structured field introducer) is numbered X'00' (byte 0).
• A number not preceded by X (hexadecimal) or B (binary) is a decimal number.
• Bit numbering follows the EBCDIC convention, with bit 0 being the most significant bit.
• A range of permitted values is provided for each parameter. The specified range is the architected range, not
that used by any one implementation. If the parameter is a constant or a reserved parameter, the single
value, not a range, is provided in the Range column of the tables. The purpose of the constant might
not be
given. A reserved parameter need not be checked; however, such parameters should be set to the specified
value (if one is given) or to 0 (zero).
Note: Fields marked “Reserved” are for future use. If bytes beyond the structured field bytes described here
are specified, results are unpredictable.
• Some structured fields contain data in repeating groups. For example, in a Code Page Index (CPI) structured
field, each repeating group specifies a character ID, a set of flags, and a code point. In this publication,
numbering of the repeating groups starts with zero (0).
• The structured field parameter positions are fixed; however, some structured fields have optional
parameters. T o maintain the fixed position requirements, all optional fields before the last one used must be
coded.
• Whenever a range is given, the “to” is inclusive. For example, the range “1 to 6” is the same as “1, 2, 3, 4, 5,
6”.
• A mandatory or optional indicator is provided with each parameter to indicate whether or not the parameter
field must be provided; not that the parameter value must be nonzero.

## Page 142

120 FOCA Reference
The structured field tables have a column called Type. The column contains one of the following notations,
notation meanings are shown in the Comments column.
Table 12. Notations in the Type Column in Structured Field Tables
Type Meaning
BITS Binary flags, where each binary bit is assigned a specific meaning.
CHAR Alphanumeric characters whose code points are defined in the EBCDIC code page 500. For
example, the characters ABC1 can be represented as X'C1C2C3F1'. The characters that can be
used are a restricted set in this code page. The characters that can be used are identified in the
registered IBM graphic character set for international data processing (GCSGID 103). Characters
that can be used within fields with data type CHAR are shown in Figure 56 on page 121.
CODE A numeric parameter in which each value is assigned a specific meaning.
SBIN A numeric parameter. A one-byte parameter can contain a value in the range of -128 to +127. For
example, a decimal +19 would be X'13' and a decimal -19 would be X'ED'.
Negative values are in twos complement form, for example:
• X'7FFF' = +32,767
• X'8000' = -32,768
UBIN A numeric parameter. A one-byte parameter can contain a value of decimal 0 to 255 (X'00' to X'FF').
UNDF An undefined value. The content of this parameter value is not defined by this architecture.
Note: Signed and unsigned binary fields are shown in hexadecimal.

## Page 143

FOCA Reference 121
Figure 56. EBCDIC Code Page 500 With Character Set 103
5- 6- 7- 8-4- 9- A- B- C- D- E- F-
LB010000 LB020000
LC010000 LC020000
LD010000 LD020000
LE010000 LE020000
LF010000 LF020000
LK010000 LK020000LS010000 LS020000
LL010000 LL020000LT010000 LT020000
LM010000 LM020000LU010000 LU020000
LN010000 LN020000LV010000 LV020000
LO010000 LO020000LW010000 LW020000 ND060000
ND050000
ND040000
ND030000
ND020000
ND010000
ND100000
SP120000 LA010000 LA020000LJ010000 LJ020000
SP010000
(SP)
SM030000
& _ { }
SP100000 SM110000 SM140000SM070000
SD190000
\
/ a Aj J
b B
c C
d D
Ee E
f F
k Ks S
l Lt T
m Mu U
Nn Nv V
o Ow W 6
5
4
3
2
1
0
SA010000 SP140000 SA050000
SP150000 SP040000
SA040000
? "
+ ; > =
Hex Digits
1st
2nd
-0
-1
-3
-2
-4
-5
-6
-E
-F
-7
-8
-A
-9
-B
-C
-D
SP110000 SC030000 SP080000
SP130000
SD130000 LI010000 LI020000LR010000 LR020000LZ010000 LZ020000
SM130000
ND090000
ND080000
ND070000LX010000
LH010000 LH020000LQ010000 LQ020000
SA030000 SM040000 SM020000
SP060000 SP070000 SP090000 SP050000
SM050000
SM010000
LG010000 LG020000LP010000 LP020000 LX020000
LY010000 LY020000
g
H
Gp
Q
P
Y
X
y
. $ , #
SM060000
[ :
`
h q
i Ir Rz Z
|
9
8
7x
( ) _ '
< * % @
˜
SM080000
]
SP020000
!
SD150000
^
GCSGID = 103, CPGID = 500

## Page 144

122 FOCA Reference
A structured field introducer begins each structured field.
Table 13. Structured Field Introducer
Offset Type Name Range Meaning M/O
0–1 UBIN Length 8–32,767 Length of Structured Field M
2–4 CODE SFID See Below Structured Field Identifier M
5 BITS SFFlags See Below Control Flags M
6–7 UBIN Sequence
Number
1–32,767 The number of the structured field in
the object.
M
8 UBIN Extension Length 1–255 Length of extension data O
9–n UNDF Extension Data Up to 254 bytes of data O
x–y UNDF Padding Data Up to 32,759 bytes of data O
Bytes Description
0–1 Length
A two-byte count field that specifies the length of the structured field. The length value can range from
8 to 32,767. The count includes the length itself, the structured field introducer parameters, and the
structured field data contents, including padding bytes when present. The count does not include the
X'5A' control character.
Note: Some platforms include structured fields in a larger platform-specific record by surrounding the
structured field with additional bytes (for example, the X'5A' prefix). This can result in a record
length greater than 32,767 if the structured field length is 32,767. Such a record length can be
misinterpreted as a negative number if the length is treated as SBIN. T o ensure portability of
print files, it is recommended that the maximum structured field length be limited to X'7FF0' =
32,752, which avoids such record length issues on the known platforms.
2–4 Identifier
A three-byte value that identifies the type of structured field; the identifiers (in hexadecimal) are as
follows:
Table 14. Structured-Field Identifiers
Field Name
Abbreviation
Assigned Number Full Field Name
BCF X'D3A88A' Begin Coded Font
BCP X'D3A887' Begin Code Page
BFN X'D3A889' Begin Font
CFC X'D3A78A' Coded Font Control
CFI X'D38C8A' Coded Font Index
CPC X'D3A787' Code Page Control
CPD X'D3A687' Code Page Descriptor
CPI X'D38C87' Code Page Index
ECF X'D3A98A' End Coded Font
ECP X'D3A987' End Code Page

## Page 145

FOCA Reference 123
Table 14 Structured-Field Identifiers (cont'd.)
Field Name
Abbreviation
Assigned Number Full Field Name
EFN X'D3A989' End Font
FNC X'D3A789' Font Control
FND X'D3A689' Font Descriptor
FNG X'D3EE89' Font Patterns
FNI X'D38C89' Font Index
FNM X'D3A289' Font Patterns Map
FNN X'D3AB89' Font Names (Outline Fonts Only)
FNO X'D3AE89' Font Orientation
FNP X'D3AC89' Font Position
NOP X'D3EEEE' No Operation
5 Flags
A one-byte field that specifies the value of optional structured-field indicators.
Bit 0 Extension Indicator. Shows whether a structured field introducer extension is present.
B'0' No extension is present.
B'1' An extension is present.
Bit 1 B'0'
Bit 2 Segmentation Indicator. Shows whether a structured field has been divided into multiple
segments; required when there is more data than will fit in a single structured field.
B'0' No segmentation is present.
B'1' Segmentation is present.
Bit 3 B'0'
Bit 4 Padding Indicator. Shows whether the structured field includes padding bytes (up to 32,759).
B'0' No padding is present.
B'1' Padding is present.
Bits
5–7
B'000'
6–7 Sequence Number
A two-byte correlation field that applications can use to label specific structured fields so the fields can
be located more easily in a data stream. Applications define the sequence-numbering conventions
used. Presentation services software does not validate sequence numbering; therefore, applications
can use any numbering convention.
Note: The MO:DCA architecture does not use the sequence number field and recommends that it be
set to zero for structured fields defined in the MO:DCA Reference.
8 Extension Length
This parameter is valid only if bit 0 of byte 5 is set to B'1'. The first byte of the extension field must
specify its length. The extension field length may be from 1 to 255, counting the length byte itself.
9–n Extension Data
If present, the extension data contains specific orders, parameters, and data appropriate for the given
structured field. The maximum length of the remainder of the structured field is 32,759 minus the
length in byte 8.

## Page 146

124 FOCA Reference
x–y Padding
If bit 4 (padding flag) is on in the flag byte, padding bytes have been added to the structured field.
Structured field padding must include the padding length value. There are two methods:
1. Specify the length in the last padding byte
2. Specify the length in the last three padding bytes; the last byte must be X'00' and the two
preceding bytes specify the padding length.
The method to use depends on the number of padding bytes:
Number of Padding Bytes Method Used
1 or 2 1
3 to 255 1 or 2
256 to 32,759 2

## Page 147

FOCA Reference 125
BCF – D3A88A – Begin Coded Font
The Begin Coded Font (BCF) structured field begins a coded font object.
Length
(2 bytes)
X'D3A88A'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0 or 8 bytes)
The data for the BCF structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CFName Coded Font Name O
Bytes Description
0–7 Coded Font Name
If used, it is suggested that the coded font name be the same name used to reference the object. Refer
to the Map Coded Font (MCF) in the Mixed Object Document Content Architecture Reference.
Character string names are encoded using CPGID 500, GCSGID 103 (see “Parameter Types” on
page 55). Other architectures or implementations may permit more or less characters than are
contained in GCSGID 103. For interchange purposes, CPGID=500, GCSGID=961 is recommended;
this code page contains the following characters:
• A–I (code points X'C1'–X'C9')
• J–R (code points X'D1'–X'D9')
• S–Z (code points X'E2'–X'E9')
• 0–9 (code points X'F0'–X'F9')
• $, #, and @ (code points X'5B', X'7B', and X'7C' respectively)
Note: If a Begin Coded Font (BCF) structured field has a name, the corresponding End Coded Font
(ECF) structured field name should match. However, in an ECF structured field, no name or a
null name (any name with X'FFFF' in the first two bytes) will match any name in the BCF
structured field.
Begin Coded Font (BCF)

## Page 148

126 FOCA Reference
BCP – D3A887 – Begin Code Page
The Begin Code Page (BCP) structured field begins a code page object.
Length
(2 bytes)
X'D3A887'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0, 8, or 10–32,759 bytes)
The data for the BCP structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CPName Code Page Name O
8–n UNDF Triplets See Below Self Defining Triplets O
Bytes Description
0–7 Code Page Name
If used, it is suggested that the code page name be the same name used to reference the object. See
bytes 8–15 of the Coded Font Index (CFI) repeating group in this document, or the Map Coded Font
(MCF) in the Mixed Object Document Content Architecture Reference.
Character string names are encoded using CPGID 500, GCSGID 103 (see “Parameter Types” on
page 55). Other architectures or implementations may permit more or less characters than are
contained in GCSGID 103. For interchange purposes, CPGID=500, GCSGID=961 is recommended;
this code page contains the following characters:
• A–I (code points X'C1'–X'C9')
• J–R (code points X'D1'–X'D9')
• S–Z (code points X'E2'–X'E9')
• 0–9 (code points X'F0'–X'F9')
• $, #, and @ (code points X'5B', X'7B', and X'7C' respectively)
Note: If a BCP structured field has a name, the corresponding End Code Page (ECP) structured field
name must match. In an ECP structured field, no name or a null name (any name with X'FFFF'
in the first two bytes) matches any name in the BCP structured field.
8–n Triplets
The following triplets may only occur once:
X'62', Type 0 “X'62' – Local Date and Time Stamp Triplet” on page 178 (not permitted when X'62'
Type 3 is present)
X'62', Type 1 Retired (Local RMARK Date and Time Stamp triplet)
X'62', Type 3 Local Revision Date and Time Stamp triplet (not permitted when X'62' Type 0 is
present)
X'63', Type 1 “X'63', Type 1 – CRC Resource Management Triplet” on page 180
X'63', Type 2 Retired (“X'63', Type 2 – Font Resource Management Triplet” on page 181)
X'64' “X'64' – Object Origin Identifier Triplet” on page 183
These triplets provide additional data used to manage this code page. Print-server software uses
this
information to determine whether or not to download a resource or to activate a resident resource.
Programs, such as IBM
Remote PrintManager™, or a printer with resident resources, use this
information for:
• Quick screening of resources
• Identification of identical objects from different network sources
• Notification when an object has been changed
Begin Code Page (BCP)

## Page 149

FOCA Reference 127
The following triplet can occur multiple times:
X'65' “X'65' – User Comment Triplet” on page 184
The triplets can occur in any order.
Begin Code Page (BCP)

## Page 150

128 FOCA Reference
BFN – D3A889 – Begin Font
The Begin Font (BFN) structured field begins the font character set object.
Length
(2 bytes)
X'D3A889'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0, 8, or 10–32,759 bytes)
The data for the BFN structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CSName Font Character Set Name O
8–n UNDF Triplets See Below Self Defining Triplets O
Bytes Description
0–7 Font Character Set Name
If used, it is suggested that the font character set name be the same name used to reference the
object. See bytes 0–7 of the Coded Font Index (CFI) repeating group in this document, or the Map
Coded Font (MCF) of the Mixed Object Document Content Architecture Reference.
Character string names are encoded using CPGID 500, GCSGID 103 (see “Parameter Types” on
page 55). Other architectures or implementations may permit more or less characters than are
contained in GCSGID 103. For interchange purposes, CPGID=500, GCSGID=961 is recommended;
this code page contains the following characters:
• A–I (code points X'C1'–X'C9')
• J–R (code points X'D1'–X'D9')
• S–Z (code points X'E2'–X'E9')
• 0–9 (code points X'F0'–X'F9')
• $, #, and @ (code points X'5B', X'7B', and X'7C' respectively)
Note: If a BFN structured field has a name, the corresponding EFN structured field name should
match. However, in an EFN structured field, no name or a null name (any name with X'FFFF' in
the first two bytes) matches any name in the BFN structured field.
8–n Triplets
The following triplets may only occur once:
X'62', Type 0 “X'62' – Local Date and Time Stamp Triplet” on page 178 (not permitted when X'62'
Type 3 is present)
X'62', Type 1 Retired (Local RMARK Date and Time Stamp triplet)
X'62', Type 3 Local Revision Date and Time Stamp triplet (not permitted when X'62' Type 0 is
present)
X'63', Type 1 “X'63', Type 1 – CRC Resource Management Triplet” on page 180
X'63', Type 2 Retired (“X'63', Type 2 – Font Resource Management Triplet” on page 181)
X'64' “X'64' – Object Origin Identifier Triplet” on page 183
These triplets provide additional data used to manage this font character set. Print-server software
uses
this information to determine whether or not to download a resource or to activate a resident
resource. Programs, such as IBM Remote PrintManager, or a printer with resident resources, use this
information for:
• Quick screening of resources
• Identification of identical objects from different network sources
• Notification when an object has been changed
Begin Font (BFN)

## Page 151

FOCA Reference 129
The following triplet can occur multiple times:
X'65' “X'65' – User Comment Triplet” on page 184
The triplets can occur in any order.
Begin Font (BFN)

## Page 152

130 FOCA Reference
CFC – D3A78A – Coded Font Control
The Coded Font Control (CFC) structured field specifies the length of the repeating group in the Coded Font
Index (CFI) structured field.
Length
(2 bytes)
X'D3A78A'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(2 or 17 bytes)
The data for the CFC structured field is as follows:
Offset Type Name Range Meaning M/O
0 UBIN CFIRGLen X'19' CFI Repeating Group Length M
1 UBIN Retired X'01' Retired Parameter M
2–end Triplet Metric Adjustment triplet (X'79') O
Bytes Description
0 CFI Repeating Group Length
This is a control parameter, used to manage the data structures. The value contained in this parameter
defines the length of the repeating group used in the Coded Font Index (CFI) structured field.
1 Constant
This is a retired parameter which must be set to a constant value of X'01'. No significance should be
attached to the value by any using application, but any font generator should set the value as
indicated.
2 to
end
Triplet
An optional Metric Adjustment triplet can be specified to provide adjustment values for certain font
metrics. Refer to “X'79' – Metric Adjustment Triplet” on page 186 for a description of these adjustment
values.
Coded Font Control (CFC)

## Page 153

FOCA Reference 131
CFI – D38C8A – Coded Font Index
The Coded Font Index (CFI) structured field names the font character sets and code pages for the font. The
structured field contains a set of parameters, defined as a repeating group. The length of the repeating group is
defined in the Coded Font Control structured field. The number of repeating groups in the structured field can
be determine by dividing the length of the CFI structured field (minus the length of the structured field
introducer) by the length of the CFI repeating group. The repeating groups are sorted in ascending order based
on the section identifier.
Single-Byte and Double-byte Outline
There is only one section for a single-byte font, or a double-byte outline font; therefore, there is only one
repeating group.
Double-Byte Raster Coded Fonts
There can be 190 sections (X'41' to X'FE'). Each section requires its own repeating group. The repeating
groups are sorted in ascending order based on the section identifier.
Length
(2 bytes)
X'D38C8A'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(25*[number of sections] bytes)
The data for the CFI structured field consists of a series of repeating groups. Each repeating group is defined
as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR FCSName Font Character Set Name M
8–15 CHAR CPName Code Page Name M
16–17 UBIN SVSize 0–65,535 Specified Vertical Font Size, in 20ths of
a point (1440ths of an inch)
M
18–19 UBIN SHScale 0–65,535 Specified Horizontal Scale Factor, in
20ths of a point (1440ths of an inch)
M
20–23 UNDF X'00000000' Reserved M
24 UBIN Section
X'00'
X'41'–X'FE'
X'FF'
Section Number
Single-byte
Double-byte Raster
reserved
M
Coded Font Index (CFI)

## Page 154

132 FOCA Reference
Bytes Description
0–7 Font Character Set Name
See “Resource Name” on page 69.
The member name of the font character set can be any value except null (any name that contains
X'FFFF' in the first two bytes) or eight blanks (X'4040 4040 4040 4040').
Character string names are encoded using CPGID 500, GCSGID 103 (see “Parameter Types” on
page 55). Other architectures or implementations may permit more or less characters than are
contained in GCSGID 103. For interchange purposes, CPGID=500, GCSGID=961 is recommended;
this code page contains the following characters:
• A–I (code points X'C1'–X'C9')
• J–R (code points X'D1'–X'D9')
• S–Z (code points X'E2'–X'E9')
• 0–9 (code points X'F0'–X'F9')
• $, #, and @ (code points X'5B', X'7B', and X'7C' respectively)
Raster Fonts
IBM's naming convention, which is not part of the FOCA architecture,
includes a two-character prefix
of C0 followed by one to six non-null characters. IBM presentation services software uses the entire
name to locate the font character set to be downloaded. Some presentation services software and
other Advanced Function Presentation (AFP) programs interpret font names using the two-character
prefix convention.
Outline Fonts
IBM's naming convention, which is not part of the FOCA architecture, includes a two-character prefix
of CZ followed by one to six non-null characters.
8–15 Code Page Name
See “Resource Name” on page 69.
The member name of the code page can be any value except null (any name that contains X'FFFF' in
the first two bytes) or eight blanks (X'4040 4040 4040 4040').
Character string names are encoded using CPGID 500, GCSGID 103 (see “Parameter Types” on
page 55). Other architectures or implementations may permit more or less characters than are
contained in GCSGID 103. For interchange purposes, CPGID=500, GCSGID=961 is recommended;
this code page contains the following characters:
• A–I (code points X'C1'–X'C9')
• J–R (code points X'D1'–X'D9')
• S–Z (code points X'E2'–X'E9')
• 0–9 (code points X'F0'–X'F9')
• $, #, and @ (code points X'5B', X'7B', and X'7C' respectively)
Coded Font Index (CFI)

## Page 155

FOCA Reference 133
16–17 Specified Vertical Font Size
See “Specified Vertical Font Size” on page 71.
This field is intended for outline font support, when no other size information is available in the font
reference (for example, MCF GRID or Font Descriptor). For raster fonts, this field is not used. A
nonzero value, when no other scaling information is available, indicates the size to which an outline
font shall be scaled, expressed in 20ths of a point (1440ths of an inch). For example, 10.8 points is
specified as 216. A nonzero value, when other scaling information is available, shall be ignored. A zero
value indicates that no size information has been specified.
Note: The IPDS and MO:DCA architectures limit the range of the vertical font size to 0–32,767. It is
recommended that this smaller range also be used in the CFI structured field.
18–19 Specified Horizontal Scale Factor
See “Specified Horizontal Scale Factor” on page 70.
This field is intended for outline font support, when no other size information is available in the font
reference (for example, MCF GRID or Font Descriptor). For raster fonts, this field is not used. A
nonzero value is only to be used in conjunction with the Vertical Font Size in bytes 16–17 of this same
structured field to determine the horizontal scaling ratio to be applied to the characters of the font. The
value is expressed in 20ths of a point (1440ths of an inch). A nonzero value, when any other size
information is available (for example, a vertical or horizontal size value expressed in the MCF GRID or
Font Descriptor) shall be ignored. A zero value indicates that no horizontal scaling information has
been specified.
Note: The IPDS and MO:DCA architectures limit the range of the horizontal scale factor to 0–32,767. It
is recommended that this smaller range also be used in the CFI structured field.
20–23 Reserved
24 Section Number
See “Section Number” on page 107.
Single-Byte
Must be X'00' to identify a single-byte section.
Double-Byte Raster Coded Fonts
Must be X'41' to X'FE' to identify a double-byte section.
This section number is the first byte of the two-byte code that identifies the character in a coded font
and in text.
Double-Byte Outline Coded Fonts
Must be X'00' to identify all double-byte sections.
Coded Font Index (CFI)

## Page 156

134 FOCA Reference
CPC – D3A787 – Code Page Control
The Code Page Control (CPC) contains information about the code page.
Length
(2 bytes)
X'D3A787'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(13 or 17 bytes)
The data for the CPC structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR DefCharID Default Graphic Character Global ID M
8 BITS PrtFlags See Below Default Character Use Flags M
9 CODE CPIRGLen
X'0A'
X'0B'
X'FE'
X'FF'
CPI Repeating Group Length:
Single-byte Code Page
Double-byte Code Page
Single-byte Code Page including
Unicode scalar values
Double-byte Code Page including
Unicode scalar values
M
10 UBIN VSCharSN X'00'–X'FF' Space Character Section Number M
11 UBIN VSChar X'00'–X'FF' Space Character Code Point M
12 BITS VSFlags See Below Code Page Use Flags M
Zero or one Unicode scalar value for the default character ID (only present when byte 9 contains either X'FE' or
X'FF') in the following format:
+ 0–3 UBIN Unicode
scalar value
X'00000000' –
X'FFFFFFFF'
(excluding
surrogates)
Unicode scalar value to be mapped to the default
character GCGID in bytes 0–7
O
Bytes Description
0–7 Default Graphic Character Global ID
See “Graphic Character Global Identifier” on page 95.
The character ID of the character used when no character is assigned to a code point. The default
character must be named in bytes 0–7 of a repeating group in the Font Index (FNI) structured field.
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
Note: The letters in a character ID are case-sensitive. Therefore LA020000, La020000, lA020000, and
la020000 denote four different characters. IBM font products use uppercase alphanumeric
character IDs based on IBM Corporate Standards. While other naming schemes can be used, it
is important to use a consistent naming scheme across all font objects in a system; therefore,
the GCGID scheme used by IBM font products is recommended.
Double-Byte Raster Coded Fonts
This parameter must be set the same for all sections of a double-byte raster coded font.
Note: In IPDS environments, the default Graphic Character Global ID is used with raster fonts for
unassigned code points (for which there is no CPI repeating group). IPDS raster fonts and
double-byte font sections must be fully populated; therefore, the default GCGID is used to fill in
the holes between defined characters.
Code Page Control (CPC)

## Page 157

FOCA Reference 135
In an IPDS outline font, the code page does not have to be fully populated, and the IPDS code
page does not always contain a default Graphic Character Global ID (older IPDS outline-font
printers do not support the default GCGID parameters). All code points that do not have a code-
point entry are assigned the default GCGID, if one is present in the IPDS command that begins
the code page. If a default GCGID is not provided in the IPDS command that begins the code
page, each code point without a code-point entry is assigned the undefined, non-printing,
incrementing processing flags, and if printing is continued as part of an error continuation action,
the space character code point is used instead of the default graphic character ID.
Therefore, to ensure consistent results on all IPDS printers, it is good practice to set the Default
Graphic Character Global ID to the same GCGID as that used by the space character so that
this code page can be used to print with both raster and outline font character sets. Also, when
you are using an older IPDS outline-font printer that does not support the default GCGID
parameter, if a default GCGID other than the space character is required for a code page to be
used with an outline font, that code page should be fully populated by using the desired default
GCGID to fill in the holes in the code page.
Refer to the Intelligent Printer Data Stream Reference for a description of the IPDS font objects.
8 Default Character Use Flags
Bit 0 Invalid Coded Character
When this flag bit is set to B'1' (on), the character is an invalid coded character (see “Invalid
Coded Character” on page 106). When this flag bit is set to B'0' (off), the character is a valid
coded character.
Bit 1 No Presentation
When this flag bit is set to B'1' (on), the character is
not to be printed (see “No Presentation”
on page 107). When this flag bit is set to B'0' (off), the character is to be printed.
Bit 2 No Increment
When this flag bit is set to B'1' (on), the print position is not to be incremented (see “No
Increment” on page 106). When this flag bit is set to B'0' (off), the print position is to be
incremented.
Bits
3–7
Reserved
Note: An attempt to print a character whose invalid flag bit is on (B'1') will cause a printer data check
unless that printer data check is suppressed by the print-server software.
9 CPI Repeating Group Length
The length of the CPI repeating group is dependent on the length of the code point as defined by the
Encoding Scheme parameter in the CPD Structured Field. A single-byte code point would result in a
CPI length of X'0A', while a double-byte code point would result in a length of X'0B'.
When GCGID-to-Unicode mappings are provided (values X'FE' or X'FF'), the length of each CPI
repeating group can vary depending on how many Unicode scalar values are associated with each
code point. Valid values for the CPI repeating group length include:
X'0A' Single-byte code point, repeating group length is X'0A' bytes
X'0B' Double-byte code point, repeating group length is X'0B' bytes
X'FE' Single-byte code point, repeating group length varies per code point depending on how many
Unicode scalar values are listed
X'FF' Double-byte code point, repeating group length varies per code point depending on how many
Unicode scalar values are listed
Note: Some FOCA products do not support the values X'FE' or X'FF'.
Code Page Control (CPC)

## Page 158

136 FOCA Reference
10–11 Space Character Section Number and Code Point
See “Space Character Section Number” on page 108, and “Space Character Code Point” on page
108.
If the code page represents a single-byte encoding scheme, the space character code point is
indicated in the first byte and is repeated in the second byte. If the code page represents a double-byte
encoding scheme, the space character section number is indicated in the first byte and the code point
in the second byte.
Double-Byte Raster Coded Fonts
This value must be the same for all sections.
Although the space character parameters can be assigned any value, the typically used values are
shown in the following table:
Basic Encoding Structure Single Byte Double Byte
IBM-PC Data X'2020' Not used
EBCDIC Presentation X'4040' X'4040'
UCS Presentation (latin) Not applicable X'0020'
UCS Presentation
(ideographic, full-width space)
Not applicable X'3000'
The character shape assigned to this code point is not printed. The increment for the space character
can be changed by a PTOCA Set Variable Space Character Increment (SVI) control sequence. Refer
to Presentation Text Object Content Architecture Reference for SVI information.
12 Code Page Use Flags
Bit 0 Sort Order
When this flag bit is set to B'1' (on), the CPI repeating groups are sorted in ascending code
point order. When this flag bit is set to B'0' (off), the CPI repeating groups are sorted in
ascending character ID order.
Bit 4 Variable Space Enable
This flag is used to enable variable spacing. When the flag is B'1', the PTOCA variable space
increment is used whenever the code point for the variable space character is encountered.
Some AFP products, such as DCF , use variable spacing for text justification and therefore
require this flag to be B'1'.
When the flag is B'0', all PTOCA SVI control sequences are ignored and the space character
is printed as defined in the accompanying font whenever the variable space code point is
encountered. If a code page is built that has this flag as B'0', it should not be used with AFP
products that require the variable space function.
Double-Byte Raster Coded Fonts
This flag bit must be set the same for all sections.
Optional Unicode mapping entry for the default character ID
T o allow code pages that contain user-defined characters (that is, those characters that have not been
registered with IBM and assigned a GCGID value) to be used with TrueType/OpenType fonts, the
default character ID can be mapped to a Unicode scalar value. This function is selected by specifying
either X'FE' (for a single-byte code page) or X'FF' (for a double-byte code page) in the CPI repeating
group length field (CPC byte 9).
Code Page Control (CPC)

## Page 159

FOCA Reference 137
Note: When CPC byte 9 is X'0A' or X'0B', the Unicode scalar value field is not present. When CPC
byte 9 is X'FE' or X'FF', the Unicode scalar value field can be present, but is not required.
+ 0–3 Unicode scalar value
A Unicode scalar value is any Unicode code point except high-surrogate and low-surrogate
code points. In other words, the ranges of values from X'00000000' to X'0000D7FF' and from
X'0000E000' to X'0010FFFF' inclusive. Any other value is an ill-formed Unicode value (as of
Unicode Standard 4.1); in the future, values above X'0010FFFF' might be added to the valid
Unicode range.
The Unicode scalar value and the GCGID should identify the same character and should be in
all TrueType/OpenType fonts used with this code page.
Code Page Control (CPC)

## Page 160

138 FOCA Reference
CPD – D3A687 – Code Page Descriptor
The Code Page Descriptor (CPD) structured field describes the code page.
Length
(2 bytes)
X'D3A687'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(42 or 44 bytes)
The data for the CPD structured field is as follows:
Offset Type Name Range Meaning M/O
0–31 CHAR CPDesc Code Page Description M
32–33 UBIN GCGIDLen 8 Graphic Character GID Length M
34–37 UBIN NumCdPts 1–65,535 Number of Coded Graphic Characters Assigned M
38–39 UBIN GCSGID
X'0000'
X'0001'–X'FFFE'
X'FFFF'
Graphic Character Set GID:
No GCSGID is assigned
GCSGID
No GCSGID is assigned
M
40–41 UBIN CPGID
X'0000'
X'0001'–X'FFFE'
X'FFFF'
Code Page Global Identifier
No CPGID is assigned
CPGID
No CPGID is assigned
M
42–43 UBIN EncScheme See Below Encoding Scheme O
Bytes Description
0–31 Code Page Description
See “Code Page Description” on page 103.
The character string assigned to this field is intended to aid the end user, who might need to edit the
code page, in identifying the set of characters represented by the code page. The name or title used
should correspond to the name assigned by the IBM registration authority when the code page was
registered. Though it may be any name or title that has meaning to the creator or editor of the code
page. Unless otherwise specified, character string data is encoded as CPGID 500, GCSGID 103 (see
“Parameter Types” on page 55).
32–33 Graphic Character GID Length
See “Graphic Character GID Length” on page 106.
This is the length of the IBM registered GCGID (AFP uses the eight-character identifier format), or a
user-assigned GCGID.
34–37 Number of Coded Graphic Characters Assigned
See “Number of Coded Graphic Characters Assigned” on page 107.
The number of assigned code points in the code page that equals the number of Code Page Index
(CPI) repeating groups.
38–39 Graphic Character Set Global Identifier
See “Graphic Character Set Global Identifier” on page 61.
This is the IBM registered GCSGID, or it may be a user defined GCSGID number from the reserved
number space X'FF00' to X'FFFE'.
Code Page Descriptor (CPD)

## Page 161

FOCA Reference 139
40–41 Code Page Global Identifier
See “Code Page Global Identifier” on page 103.
This is the IBM registered CPGID, or it may be a user defined CPGID number from the reserved
number space X'FF00' to X'FFFE'.
42–43 Encoding Scheme
See “Encoding Scheme” on page 104.
This parameter identifies the code page as either EBCDIC-Presentation encoded, IBM-PC-Data
(ASCII) encoded, or UCS-Presentation encoded. It also specifies the code points as either fixed
single-byte values or fixed double-byte values. The following values are used:
X'0000' No encoding scheme specified
X'0100' Single-byte, encoding not specified
X'0200' Double-byte, encoding not specified
X'2100' Single-byte IBM-PC Data
X'6100' Single-byte EBCDIC Presentation
X'6200' Double-byte EBCDIC Presentation
X'8200' Double-byte UCS Presentation
Note: Even though this field is optional, the encoding scheme should be specified to allow proper
interpretation of the code page. This field must be specified for a UCS Presentation code page.
Code Page Descriptor (CPD)

## Page 162

140 FOCA Reference
CPI – D38C87 – Code Page Index
In a series of repeating groups, the Code Page Index (CPI) associates character IDs with code points. Each
repeating group specifies a character ID, a set of flags, and a code point. The repeating groups may be sorted
in ascending order by character IDs or by code point, depending on the Sort Order flag in the Code Page
Control structured field. The default sort order is by ascending character ID order. One repeating group is
required, but as many as 65,536 repeating groups are allowed. The maximum number of repeating groups is
determined by the maximum number of code points in the code page: 256 for single-byte code pages, 256 for
double-byte code page sections, and 65,536 for double-byte code pages.
For processing efficiency, it is required that all code points in a single CPI correspond to the same double-byte
section. That is, each double-byte section shall begin a new CPI structured field; the segmentation flag is not
used
. For performance and storage efficiency, it is required that double-byte code pages be sorted in
ascending code point order. For migration consistency with the existing product base, it is required that single-
byte code pages be sorted in ascending character ID order (this includes those code pages which were
defined for use with double-byte raster fonts; each code page object represented one double-byte code page
section).
Note: The same GCGID could be assigned to multiple code points, therefore, “ascending order” includes
multiple GCGIDs of the same value but with different code point assignments. No specific ordering is
required within such a group of equal GCGIDs.
Each code point not included in this structured field is associated with the default character specified in the
Code Page Control (CPC) structured field.
Length
(2 bytes)
X'D38C87'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
The data for the CPI structured field consists of a series of repeating group entries. Each repeating group is
divided as follows, with the length of the group specified by the CPI Repeating Group Length parameter (byte
9) in the CPC structured field; the length is 10 bytes for a single-byte code page and 11 bytes for a double-byte
code page.
When a GCGID-to-Unicode mapping is provided in the CPI (that is, when CPC byte 9 contains either
X'FE' or
X'FF'), the code point field is followed by a Unicode mapping entry consisting of a count parameter and one or
more Unicode scalar values. T o allow for combining characters, each code point in the code page can be
mapped to a different number of Unicode scalar values.
Offset Type Name Range Meaning M/O
0–7 CHAR GCGID Graphic Character Global ID M
8 BITS PrtFlags See Below Graphic Character Use Flags M
9 or
9–10
UBIN CodePoint 0–65,535 Code Point M
Zero or one Unicode mapping-entry in the following format:
+ 0 UBIN Count X'00' –X'FF' Number of Unicode scalar values O
Zero or more Unicode scalar-value entries (depending on count value) in the following format:
++ 0–3 UBIN Unicode scalar
value
X'00000000' –
X'FFFFFFFF'
(excluding
surrogates)
Unicode scalar value to be mapped to the
GCGID value and code point value
O
Code Page Index (CPI)

## Page 163

FOCA Reference 141
Bytes Description
0–7 Graphic Character Global ID
See “Graphic Character Global Identifier” on page 95.
Identifies the character for the code point (byte 9) of this repeating group. The identifier must match
bytes 0–7 of a repeating group in the Font Index (FNI) structured field, or an error is reported at print
time.
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
Note: The letters in a character ID are case-sensitive. Therefore LA020000, La020000, lA020000, and
la020000 denote four different characters. IBM font products use uppercase alphanumeric
character IDs based on IBM Corporate Standards. While other naming schemes can be used, it
is important to use a consistent naming scheme across all font objects in a system; therefore,
the GCGID scheme used by IBM font products is recommended.
8 Graphic Character Use Flags
Bit 0 Invalid Coded Character
When this flag bit is set to B'1' (on), the character is an invalid coded character (see “Invalid
Coded Character” on page 106). When this flag bit is set to B'0' (off), the character is a valid
coded character.
Bit 1 No Presentation
When this flag bit is set to B'1' (on), the character not to be printed (see “No Presentation” on
page 107). When this flag bit is set to B'0' (off), the character is to be printed.
Bit 2 No Increment
When this flag bit is set to B'1' (on), the print position is not to be incremented (see “No
Increment” on page 106). When this flag bit is set to B'0' (off), the print position is to be
incremented.
Bits
3–7
Reserved for future use
Note: An attempt to print a character whose invalid flag bit in on (B'1') will cause a printer data check
unless that printer data check is suppressed by the print-server software.
.
9 or
9–10
Code Point
See “Code Point” on page 103.
The code point for the character. The number of bytes in the code point is determined by the Encoding
Scheme parameter in the CPD structured field (the default code point length is 1). An exception
condition exists if more than one repeating group contains the same code point, in which case,
processing should be terminated (unexpected print results would occur).
Optional Unicode mapping entry
T o allow code pages that contain user-defined characters (that is, those characters that have not been
registered with IBM and assigned a GCGID value) to be used with TrueType/OpenType fonts, each
code point can be mapped to one or more Unicode scalar values. This function is selected by
specifying either X'FE' (for a single-byte code page) or X'FF' (for a double-byte code page) in the CPI
repeating group length field (CPC byte 9). In this case, each repeating group entry must contain a
count value in byte +0 that specifies the number of Unicode scalar values (bytes ++0–3) to follow. T o
allow for combining characters, each code point in the code page can be mapped to a different number
of Unicode scalar values.
Code Page Index (CPI)

## Page 164

142 FOCA Reference
+ 0 Count
This parameter specifies the number of Unicode scalar values to follow. A value of
zero indicates that there are no Unicode scalar values mapped to this code point.
++ 0–3 Unicode scalar value
A Unicode scalar value is any Unicode code point except high-surrogate and low-
surrogate code points. In other words, the ranges of values from X'00000000' to
X'0000D7FF' and from X'0000E000' to X'0010FFFF' inclusive. Any other value is an
ill-formed Unicode value (as of Unicode Standard 4.1); in the future, values above
X'0010FFFF' might be added to the valid Unicode range.
The Unicode scalar value and the GCGID should identify the same character
and
should be in all TrueType/OpenType fonts used with this code page.
Note: When multiple Unicode scalar values are specified for a code point, some
presentation devices will present the multiple characters in a device-defined
manner. When the device supports the combining character capability of
Unicode, combining characters are presented correctly; but for simpler devices,
only the first Unicode scalar value might be presented or the combining
characters might be presented one after the other or all positioned at a single
place (but not necessarily aligned correctly).
For example, suppose code point X'DC' (an EBCDIC ü character) is mapped to
two Unicode scalar values: X'0075' (u) and X'0308' (combining diaeresis).
Some devices will correctly present these two Unicode scalar values as “ü”;
however, other devices present characters in a one-to-one fashion and might
present the code point as “u¨”. In some cases, this problem can be avoided by
mapping to a single, already-combined Unicode scalar value (if such a Unicode
value is registered), such as X'00FC' (ü).
Code Page Index (CPI)

## Page 165

FOCA Reference 143
ECF – D3A98A – End Coded Font
The End Coded Font (ECF) structured field ends the coded font object.
Length
(2 bytes)
X'D3A98A'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0 or 8 bytes)
The data for the ECF structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CFName Coded Font Name O
Bytes Description
0–7 Coded Font Name
The End Coded Font (ECF) structured field name, if supplied, must match the corresponding Begin
Coded Font (BCF) structured field name. In an ECF structured field, no name or a null name (any
name with X'FFFF' in the first two bytes) matches any name in the BCF structured field.
End Coded Font (ECF)

## Page 166

144 FOCA Reference
ECP – D3A987 – End Code Page
The End Code Page (ECP) structured field ends the code page object.
Length
(2 bytes)
X'D3A987'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0 or 8 bytes)
The data for the ECP structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CPName Code Page Name O
Bytes Description
0–7 Code Page Name
The ECP structured field name, if supplied, must match the corresponding BCP structured field name.
In an ECP structured field, no name or a null name (any name with X'FFFF' in the first two bytes)
matches any name in the BCP structured field.
End Code Page (ECP)

## Page 167

FOCA Reference 145
EFN – D3A989 – End Font
The End Font (EFN) structured field ends the font character set object.
Length
(2 bytes)
X'D3A989'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0 or 8 bytes)
The data for the EFN structured field is as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR CSName Font Character Set Name O
Bytes Description
0–7 T oken Name
The EFN structured field name, if supplied, must match the corresponding BFN structured field name.
In an EFN structured field, no name or a null name (any name with X'FFFF' in the first two bytes)
matches any name in the BFN structured field.
End Font (EFN)

## Page 168

146 FOCA Reference
FNC – D3A789 – Font Control
The Font Control (FNC) structured field provides defaults and information about the font character set.
Length
(2 bytes)
X'D3A789'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(22, 28, 32, 35, 42, or 46 bytes)
The data for the FNC structured field is as follows:
Offset Type Name Range Meaning M/O
0 UBIN Retired X'01' Retired Parameter M
1 CODE PatT ech
X'05'
X'1E'
X'1F'
Pattern T echnology Identifier:
Laser Matrix N-bit Wide
CID Keyed font (Type 0)
PFB (Type 1)
M
2 UNDF Reserved X'00' Reserved for future use M
3 BITS FntFlags See Below Font Use Flags M
4 CODE XUnitBase X'00'
X'02'
Fixed metrics, 10 inches
Relative metrics
M
5 CODE YUnitBase X'00'
X'02'
Fixed metrics, 10 inches
Relative metrics
M
6–7 UBIN XftUnits X'0960'
X'0BB8'
X'03E8'
240 pels per inch
300 pels per inch
1000 units per em
M
8–9 UBIN YftUnits X'0960'
X'0BB8'
X'03E8'
240 pels per inch
300 pels per inch
1000 units per em
M
10–11 UBIN MaxBoxWd 0–32,767 Maximum Character Box Width M
12–13 UBIN MaxBoxHt 0–32,767 Maximum Character Box Height M
14 UBIN FNORGLen X'1A' FNO Repeating Group Length M
15 UBIN FNIRGLen X'0A'
X'1C'
FNI Repeating Group Length M
16 CODE PatAlign
X'00'
X'02'
X'03'
Pattern Data Alignment Code:
1-Byte Alignment
4-Byte Alignment
8-Byte Alignment
M
17–19 UBIN RPatDCnt X'0' to X'7FFFFF' Raster Pattern Data Count M
20 UBIN FNPRGLen X'16' FNP Repeating Group Length M
21 UBIN FNMRGLen
X'00'
X'08'
FNM Repeating Group Length:
No Raster Data
Raster Data
M
22 CODE ResXUBase X'00' Shape resolution, 10 inches O
23 CODE ResYUBase X'00' Shape resolution, 10 inches O
24–25 UBIN XfrUnits X'0000'
X'0960'
X'0BB8'
No shape resolution provided
240 pels per inch
300 pels per inch
O
Font Control (FNC)

## Page 169

FOCA Reference 147
Offset Type Name Range Meaning M/O
26–27 UBIN YfrUnits X'0000'
X'0960'
X'0BB8'
No shape resolution provided
240 pels per inch
300 pels per inch
O
28–31 UBIN OPatDCnt X'0A' to
X'FFFFFFFF'
Outline Pattern Data Count O
32–34 UNDF Reserved X'000000' Reserved for future use O
35 UBIN FNNRGLen X'0C' FNN Repeating Group Length O
36–39 UBIN FNNDCnt X'02' to
X'FFFFFFFF'
FNN Data Count O
40–41 UBIN FNNMapCnt 0–65,535 FNN Name Count O
42–nn Triplets See Below Self-Defining Triplets O
Bytes Description
0 Retired; must be X'01'.
1 Pattern T echnology Identifier
See “Pattern T echnology Identifier” on page 100.
The valid values for AFP printing are:
X'05' Laser Matrix N-bit Wide
X'1E' Composite Adobe Type 0
X'1F' Type 1 Font Printer File Binary (PFB)
2 Reserved
3 Use Flags
Bit 0 Identifies whether normal or Magnetic Ink Character Recognition (MICR) printing is to be
done. See “MICR Font” on page 65.
B'0' Normal printing
B'1' MICR printing
Bit 1 Identifies whether this is a complete font resource or an extension to another font resource.
See “Extension Font” on page 60.
B'0' Complete Font
B'1' Extension Font
Bits
2–3
B'00', (reserved)
Bit 4 (Retired)
This bit was used by some implementations to determine whether the baseline offset should
be shifted by one pel or not. The original V1 unbounded box architecture counted pels instead
of the spaces between pels, thus no font could have a zero base- line offset. If this bit is 0
(default), and the baseline offset is negative, most implementations will shift the baseline
offset value up by one pel. If this bit is 1, some implementations will not alter the baseline
offset value. This processing does not apply to fonts using relative units of measure.
Bit 5 B'0', (reserved)
Font Control (FNC)

## Page 170

148 FOCA Reference
Bit 6 Uniform Character Box Font
Shows whether the raster pattern is variable or uniform for all the characters in the font
character set. See “Uniform Character Box Font” on page 72.
B'0' The raster pattern size varies. The individual character raster pattern box size is
specified in bytes 10–13 of the Font Index (FNI) structured field repeating groups. The
maximum width and height of the individual character raster pattern boxes is specified
in bytes 10–13 of this structured field.
B'1' The raster pattern size is uniform for all the characters. The uniform raster pattern box
size is specified in bytes 10–13 of this structured field.
Double-Byte Raster Coded Fonts
Sections X'41' to X'44' can have either uniform or variable size raster pattern boxes.
Sections X'45' to X'FE' must have uniform raster pattern boxes (bit must be B'1').
Bit 7 B'0', (reserved)
4–9 Font-Metrics Units of Measure
See “Units of Measure” on page 34.
4 X Unit Base
X'00' Base is fixed at 10 inches
X'02' Base is relative
5 Y Unit Base
X'00' Base is fixed at 10 inches
X'02' Base is relative
Note: Bytes 4 and 5 must have the same value.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the same coded font.
6–7 X Units per Unit Base
X'0960' (2400) 240-pel resolution, fixed base
X'0BB8' (3000) 300-pel resolution, fixed base
X'03E8' (1000) Relative base for Pattern T echnology Identifiers X'1E' and X'1F'
8–9 Y Units per Unit Base
X'0960' (2400) 240-pel resolution, fixed base
X'0BB8' (3000) 300-pel resolution, fixed base
X'03E8' (1000) Relative base for Pattern T echnology Identifiers X'1E' and X'1F'
Notes:
1. Bytes 6–7 and 8–9 must have the same value.
2. While 240-pel and 300-pel resolutions are the only fixed resolutions defined for AFP
system fonts, some AFP products support additional resolutions such as 480 pel and 600
pel. In particular, many IPDS printers will accept raster fonts at any pel resolution and
automatically convert them to the print-head resolution (support for “all resolutions in the
range X'0001'-X'7FFF' ” is indicated in the IPDS printer's OPC reply).
Therefore, it is possible to use raster fonts built at resolutions other than 240 pel and 300
pel, but this can cause problems in some circumstances. For example, some printers do
not support additional resolutions and some print-server software limits support to the two
FOCA-specified resolutions. Also, print quality can suffer since printers must convert the
Font Control (FNC)

## Page 171

FOCA Reference 149
font resolution into the print-head resolution. A few IPDS printers are optimized for 240-pel
and 300-pel resolutions, but not necessarily optimized for other values.
For example, a resolution of 240 pels-per-inch by 240 pels-per-inch is represented by 0,0,2400,2400
and a relative base is represented by 2,2,1000,1000.
If optional bytes 22–27 are not provided or contain binary zeroes, then for a fixed-metric font, bytes 4–
9 specify both the metric resolution and the shape resolution. For a relative-metric font, no shape
resolution is provided in this case. This could be a “metrics-only” font. See T able 15 on page 151 for
the relationship of these bytes to bytes 22–27.
Note: Use the following formula to convert between fixed-metric values (in pels)
and relative-metric
values:
relative value * point size * fixed resolution
fixed value = ----------------------------------------------
72 * relative units per unit base
10–13 Maximum Character Box Size
A 0° character rotation is assumed.
If bit 6 of byte 3 is set to B'0' (variable character box size), these values are the maximum width and
maximum height of all the raster pattern boxes in the font character set.
If bit 6 of byte 3 is set to B'1' (uniform character box size), these values are the uniform raster pattern
box size.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the high sections X'45' to X'FE' of the same coded font.
Outline fonts
An outline font will have an box width of zero and a box height of zero.
10–11 Maximum Character Box Width
See “Maximum Character Box Width” on page 80.
The raster pattern box width in pels, minus 1 (the first column of pels is column 0).
Note: Some server software
refers to this value as x-extent in some messages.
12–13 Maximum Character Box Height
See “Maximum Character Box Height” on page 79.
The raster pattern box height in pels, minus 1 (the first row is numbered as row 0).
Note: Some server software
refers to this value as y-extent in some messages.
Note: When the FNC pattern technology identifier contains X'1E' or X'1F' bytes 10–13 are set to
X'00000000'.
14 FNO Repeating Group Length
This is a control parameter, used to manage the data structures. The value contained in this parameter
defines the length of the repeating group used in the Font Orientation (FNO) structured field. The value
is a constant, set to X'1A'.
Font Control (FNC)

## Page 172

150 FOCA Reference
15 FNI Repeating Group Length
This is a control parameter, used to manage the data structures. The value contained in this parameter
defines the length of the repeating group used in the Font Index (FNI) structured field.
For raster technology fonts, this value must be X'1C'. For outline technology fonts, this value may be
X'0A' or X'1C'.
16 Raster Pattern Data Alignment
See “Pattern Data Alignment Code” on page 98.
The boundary alignments for the raster patterns. The codes X'00', X'02', and X'03' indicate one-byte,
four-byte, and eight-byte alignments, respectively. The choice is left to the font designer for a font
containing raster patterns. T o derive actual pattern data addresses, the pattern data address in bytes
4–7 in the Font Patterns Map (FNM) repeating groups must be multiplied by 1, 4, or 8, respectively.
Note: If the font contains no raster patterns, this byte must be set to X'00'.
17–19 Raster Pattern Data Count
See “Pattern Data Count” on page 99.
The total number of data bytes for all the Font Patterns (FNG) structured fields in this font character
set, when the pattern technology identifier is set to X'05'. Must be set to X'000000' if the font does not
contain raster patterns, or the pattern technology identifier is not X'05'.
20 FNP Repeating Group Length
This is a control parameter, used to manage the data structures. The value contained in this parameter
defines the length of the repeating group used in the Font Position (FNP) structured field. The value is
a constant, set to X'16'.
21 FNM Repeating Group Length This is a control parameter, used to manage the data structures. The
value contained in this parameter defines the length of the repeating group used in the Font Position
(FNP) structured field. The value is set to X'00' if the font contains outline font data in the FNGs.
Otherwise it contains X'08'.
22–27 Shape Resolution Units of Measure
See “Units of Measure” on page 34.
This parameter is optional for raster fonts, but is required if the FNC structured field includes outline
font descriptive information in bytes 28–41.
22 X Unit Base
X'00' Base is fixed at 10 inches
23 Y Unit Base
X'00' Base is fixed at 10 inches
24–25 X Units per Unit Base
X'0960' (2400) 240-pel resolution, fixed base
X'0BB8' (3000) 300-pel resolution, fixed base
X'0000' No value provided
26–27 Y Units per Unit Base
X'0960' (2400) 240-pel resolution, fixed base
X'0BB8' (3000) 300-pel resolution, fixed base
X'0000' No value provided
Note: Bytes 24–25 and 26–27 must have the same value.
For example, a resolution of 240 pels-per-inch by 240 pels-per-inch is represented by 0,0,2400,2400,
a 300 pels-per-inch by 300 pels-per-inch is represented by 0,0,3000,3000, and an outline font is
represented by 0,0,0000,0000.
Font Control (FNC)

## Page 173

FOCA Reference 151
28–31 Outline Pattern Data Count
See “Pattern Data Count” on page 99.
Length, in bytes, of the font pattern data when the pattern technology identifier is set to X'1E' or X'1F'.
This parameter is used only when the pattern technology identifier is not equal to X'05'.
32–34 Reserved for future use; must be set to a constant X'000000'.
35 FNN Repeating Group Length
This is a control parameter, used to manage the data structures. The value contained in this parameter
defines the length of the repeating group used in the Font Name Map (FNN) structured field. Used
when the pattern technology identifier is not equal to X'05'.
36–39 FNN Data Count
This is a control parameter used to manage the data structures and specify the number of data bytes
in the FNN structured fields. This field is included when the FNGs contain outline font data; when the
pattern technology identifier is equal to X'1E', or X'1F'.
40–41 FNN IBM Name Count
This is a control parameter used to manage the data structures and specify the number of IBM
character names (GCGIDs) mapped to outline font character names in the FNN structured fields. This
field is included when the FNGs contain outline font data; when the pattern technology identifier is
equal to X'1E', or X'1F'.
42–end Triplets
See “X'6D' – Extension Font Triplet” on page 185.
If this is an extension font character set (byte 3, bit 1 = B'1'), there must be one extension triplet. The
extension triplet contains the GCSGID for the base font. If the triplet is provided, all positional fields
must be included in the FNC structured field.
Table 15. Relationship Between FNC Unit of Measure and Font Resolution Fields
Bytes 4–5 Bytes 22–23 Bytes 24–25 Bytes 26–27 Bytes 6–9 Identify Bytes 24–27 Identify
X'0000' Not Present Not Present Not Present Fixed metric and fixed
raster shape base
Not Present
X'0000' X'0000' X'0000' X'0000' Fixed metric and fixed
raster shape base
Not Used
X'0000' X'0000' X'0960' or
X'0BB8'
X'0960' or
X'0BB8'
Fixed metric and fixed
raster shape base
Not Used
X'0202' Not Present Not Present Not Present Relative metric base No raster shape data for
this font
X'0202' X'0000' X'0000' X'0000' Relative metric base No raster shape data for
this font
X'0202' X'0000' X'0960' or
X'0BB8'
X'0960' or
X'0BB8'
Relative metric base Fixed raster shape base
Font Control (FNC)

## Page 174

152 FOCA Reference
FND – D3A689 – Font Descriptor
The Font Descriptor (FND) specifies the overall characteristics of a font character set.
Length
(2 bytes)
X'D3A689'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(80 or 85–588 bytes)
The data for the FND structured field is as follows:
Offset Type Name Range Meaning M/O
0–31 CHAR TypeFcDesc Typeface Description M
32 CODE FtWtClass
X'01'
X'02'
X'03'
X'04'
X'05'
X'06'
X'07'
X'08'
X'09'
Weight Class
Ultralight
Extralight
Light
Semilight
Medium (normal)
Semibold
Bold
Extrabold
Ultrabold
M
33 CODE FtWdClass
X'01'
X'02'
X'03'
X'04'
X'05'
X'06'
X'07'
X'08'
X'09'
Width Class
Ultracondensed
Extracondensed
Condensed
Semicondensed
Medium (normal)
Semiexpanded
Expanded
Extraexpanded
Ultraexpanded
M
34–35 UBIN MaxPtSize
0–720
0–32,767
Maximum Vertical Size, in 10ths of a Point:
Fixed
Relative
M
36–37 UBIN NomPtSize
0–720
0–32,767
Nominal Vertical Size, in 10ths of a Point:
Fixed
Relative
M
38–39 UBIN MinPtSize
0–720
0–32,767
Minimum Vertical Size, in 10ths of a Point:
Fixed
Relative
M
40–41 UBIN MaxHSize 0–X'7FFE' Maximum Horizontal Size, in 20ths of a point
(1440ths of an inch)
M
42–43 UBIN NomHSize 0–X'7FFE' Nominal Horizontal Size, in 20ths of a point
(1440ths of an inch)
M
44–45 UBIN MinHSize 0–X'7FFE' Minimum Horizontal Size, in 20ths of a point
(1440ths of an inch)
M
46 CODE DsnGenCls 0–255 Design General Class (ISO) M
47 CODE DsnSubCls 0–255 Design Subclass (ISO) M
48 CODE DsnSpcGrp 0–255 Design Specific Group (ISO) M
49–63 UBIN Reserved X'00...00' Reserved for future use M
64–65 BITS FtDsFlags See Below Font Design Flags M
Font Descriptor (FND)

## Page 175

FOCA Reference 153
Offset Type Name Range Meaning M/O
66–75 UBIN Reserved X'00...00' Reserved for future use M
76–77 UBIN GCSGID
X'0000'
X'0001'–X'FFFE'
X'FFFF'
Graphic Character Set GID:
No information is given
Registered GCSGID
No information is given
M
78–79 UBIN FGID
X'0000'
X'0001'–X'FFFE'
X'FFFF'
Font Typeface GID (FGID):
No information is given
Registered FGID
No information is given
M
80–nn Triplets See Below Self Defining Triplets O
Bytes Description
0–31 Typeface Description
This parameter (which was formerly called “Typeface Name”) contains descriptive information
including the Family Name and, when appropriate, the supported language complement. Examples
include “Helvetica Cyrillic Greek”, “Times New Roman”, and “Letter Gothic Latin1”.
See “Family Name” on page 60.
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
32 Weight Class
See “Weight Class” on page 72.
33 Width Class
See “Width Class” on page 73.
34–35 Maximum Vertical Font Size
See “Maximum Vertical Font Size” on page 64.
For outline fonts, this parameter contains the maximum recommended font size expressed in tenths of
a point. Otherwise it contains the actual raster font size, expressed in tenths of a point and is the same
value as contained in bytes 36–37. The range of values permitted in this field are: 0 to 720 for fixed
metrics and 0 to 32,767 for relative metrics. Originally, the range was 0 to 720 for relative metrics;
some products use this smaller range (support for values larger than 720 for relative metrics is
optional). A value of X'0000' indicates that no size is specified.
36–37 Nominal Vertical Font Size
See “Nominal Vertical Font Size” on page 68.
For outline fonts, this parameter contains the nominal presentation size, expressed in tenths of a point.
Otherwise it contains the actual raster font size, expressed in tenths of a point. The range of values
permitted in this field are 0 to 720 for fixed metrics and 0 to 32,767 for relative metrics. Originally, the
range was 0 to 720 for relative metrics; some products use this smaller range (support for values
larger than 720 for relative metrics is optional). A value of X'0000' indicates that no size is specified.
A point is a unit of measure. There are 12 points to a pica, and about 72 points to an inch.
38–39 Minimum Vertical Font Size
See “Minimum Vertical Font Size” on page 66.
For outline fonts, this parameter contains the minimum recommended font size, expressed in tenths of
a point. Otherwise it contains the actual raster font size, expressed in tenths of a point and is the same
value as contained in bytes 36–37. The range of values permitted in this field are: 0 to 720 for fixed
metrics and 0 to 32,767 for relative metrics. Originally, the range was 0 to 720 for relative metrics;
Font Descriptor (FND)

## Page 176

154 FOCA Reference
some products use this smaller range (support for values larger than 720 for relative metrics is
optional). A value of X'0000' indicates that no size is specified.
40–41 Maximum Horizontal Font Size
See “Maximum Horizontal Font Size” on page 63.
For outline fonts, this parameter contains the maximum recommended horizontal size, expressed in
20ths of a point (1440ths of an inch). Otherwise it contains the actual raster size, expressed in 20ths of
a point (1440ths of an inch) and is the same value as contained in bytes 42–43. The range of values
permitted in this field are: 0 to X'7FFE'.
42–43 Nominal Horizontal Font Size
See “Nominal Horizontal Font Size” on page 67.
For outline fonts, this parameter contains the nominal horizontal font size, expressed in 20ths of a
point (1440ths of an inch). Otherwise it contains the actual raster size, expressed in 20ths of a point
(1440ths of an inch).
For uniformly-spaced character sets, this is the value of the variable space increment converted to
20ths of a point (1440ths of an inch).
For mixed-pitch character sets, this is the value of the 12-pitch space converted to 20ths of a point
(1440ths of an inch).
For proportionally spaced character sets, this is one-third of the point size, converted to 20ths of a
point (1440ths of an inch).
The horizontal font size is included as the width portion of a GRID (global resource identifier).
The GRID is an eight-byte identifier in a MO:DCA Map Coded Font structured field used to identify a
font by global identifiers rather than external file names. The GRID is defined in Mixed Object
Document Content Architecture Reference. A grid contains the following two-byte fields in the order
shown:
1. GCSGID (graphic character set global identifier) from the font code page
2. CPGID (code page global identifier) from the font code page
3. FGID (font global identifier)
4. Font width in 1/1440-inch units
44–45 Minimum Horizontal Font Size
See “Minimum Horizontal Font Size” on page 65.
For outline fonts, this parameter contains the minimum recommended horizontal size, expressed in
20ths of a point (1440ths of an inch). Otherwise it contains the actual raster size, expressed in 20ths of
a point (1440ths of an inch) and is the same value as contained in bytes 42–43. The range of values
permitted in this field are: 0 to X'7FFE'.
46–48 ISO Font Typeface Design Classification
See “Design General Class (ISO)” on page 58, “Design Subclass (ISO)” on page 59, and “Design
Specific Group (ISO)” on page 59.
These three bytes represent the first, second, and third levels of a hierarchical typeface design
grouping system, defined by the International Organization for Standardization (ISO), see ISO/IEC
9541-1, Annex A, “Typeface Design Grouping”. Byte 46 is the least specific classification, while byte
48 is the most specific.
49–63 Reserved
Font Descriptor (FND)

## Page 177

FOCA Reference 155
64–65 Design Flags
Definitions of the terms in these flags are as follows:
Bit 0 B'0' Not italic
B'1' Italic
A type style with characters that slant. The slant is generally upward to the right. See
“Italics” on page 62.
Bit 1 B'0' Not underscored
B'1' Underscored
A font character set in which the raster pattern for each graphic character includes an
underscore as toned pels as well as the graphic character itself. See “Underscored
Font” on page 72.
Bit 2 B'0'
Bit 3 B'0' Solid
B'1' Hollow
A type style in which the interior of the character is not toned. See “Hollow Font” on
page 62.
Bit 4 B'0' Not overstruck
B'1' Overstruck
A type style in which one graphic character pattern (usually an overscore character) is
merged with each graphic character pattern. The overstrike raster pattern is included
as toned pels in the raster pattern for each graphic character. See “Overstruck Font”
on page 68.
Bits
5–15
B'00000000000'
66–75 Reserved
76–77 Graphic Character Set Global Identifier (GCSGID)
See “Graphic Character Set Global Identifier” on page 61.
This is the GCSGID of the font character set. It might
represent a set of characters that is either
smaller or larger than the set of characters contained in the code page associated with this font
character set in a coded font reference. When creating a coded font reference, the GCSGID that
represents the smaller of the two sets of characters (font character set or code page character set)
should be used.
78–79 Font Typeface Global Identifier (FGID)
See “Font Typeface Global Identifier” on page 61.
The FGID is included as part of the GRID.
80–end Triplets
“X'02' – Fully Qualified Name Triplet” on page 177.
There can be zero, one, or two Fully Qualified Name triplets specified in this field. Each Fully Qualified
Name triplet must be of a different type; the valid types include:
X'07' Family name
X'08' Typeface name
Font Descriptor (FND)

## Page 178

156 FOCA Reference
FNG – D3EE89 – Font Patterns
The Font Patterns (FNG) structured field carries the character shape data (raster patterns or outline data) for a
font character set. When there is more shape data than can fit in a single FNG structured field, a series of
consecutive FNG structured fields are used to carry the character shape data; the data can be split across
FNG structured fields at any byte boundary; the segmentation flag is not used.
The FNG structured field is omitted for fonts that do not contain shape information; these are called Metric Only
Fonts (MOFs).
Length
(2 bytes)
X'D3EE89'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(1–32,759 bytes)
The data for the FNG structured field depends on the font technology.
For raster fonts, the data for the FNG structured field is as follows:
Offset Type Name Range Meaning M/O
0–n UNDF PatData See Below Up to 32,759 bytes of pattern data M
Bytes Description
0–n Pattern Data
See “Pattern Data” on page 98.
Notes:
1. The first pel of each character raster pattern must start on a one-byte, four-byte, or eight-byte
boundary. See “Pattern Data Alignment Code” on page 98.
2. Multiple FNGs can be used to contain the shape data in a font. Each FNG can contain up to
32,759 bytes of data.
For outline fonts, the data for the FNG structured field is as follows:
Offset Type Name Range Meaning M/O
0–n UNDF PatData See Below Up to 32,759 bytes of pattern data M
Bytes Description
0–n Pattern Data
See “Pattern Data” on page 98.
Notes:
1. The outline font data is copied intact from the source outline font into the FNG.
2. Multiple FNGs are used to contain the shape data in a font. Each FNG can contain up to 32,759
bytes of data.
3. A raster pattern or outline font can cross into the next FNG.
Font Patterns (FNG)

## Page 179

FOCA Reference 157
When outline font data is included in the FNG the repeating group used in the FNG is as follows:
Offset Type Name Range Meaning M/O
0–3 UBIN OFLLen X'0A' to X'FFFFFFFF' Length of the outline font object M
4–7 UBIN Checksum See Below Checksum value for the technology-
specific object data
M
8–9 UBIN TIDLen X'0002'– X'FFFF' T echnology-specific object identifier
length
M
10–n UBIN TIDName See Below T echnology-specific object identifier O
n+1 to
n+2
UBIN ODescLen X'0002'– X'FFFF' T echnology-specific object descriptor
length
O
n+3 to m UNDF ObjDesc See Below T echnology-specific object descriptor O
m+1 to end ObjData See Below T echnology-specific object data O
Bytes Description
0–3 Length of the Outline Font Object
The length of this repeating group, which contains an identifiable outline font object, including this field.
4–7 Checksum
Checksum for the technology-specific object data included in this repeating group.
The checksum applies only to the object data portion (bytes m+1 to end) of the technology-specific
object. T o calculate the checksum, all of the bytes of the object data are considered as a continuous
sequence of bytes. The object data is then mapped to an array containing four unsigned bytes.
The first four bytes of object data are placed into the array as follows:
• The first byte of object data becomes byte 0 of the array (most significant byte).
• The second byte of object data becomes byte 1 of the array.
• The third byte of object data becomes byte 2 of the array.
• The fourth byte of object data becomes byte 3 of the array (least significant byte).
The remaining bytes of the object data are added on a byte by byte basis to the values contained in
the array, ignoring all carry bits. The mapping of the remaining object data is done such that the fifth
byte is added to the value in array position 0, the sixth byte to array position 1, the seventh byte to
array position 2, the eighth byte to array position 3, the ninth byte to array position 0, and so forth, until
all data has been processed. When all object data has been processed, the checksum is the unsigned,
32-bit integer created from the four-byte array.
Note: The following code fragment is shown to illustrate the algorithm:
uchar checksum_partial[4]={0,0,0,0};
short index=0;
ulong checksum;
uchar singlebyte;
while (1)
{
singlebyte=fgetc(pfb data file);
if (end of pfb data file) break;
checksum_partial[index] = checksum_partial[index] + singlebyte;
index = index + 1;
if (index = 4) index = 0;
}
checksum = *(ulong *)&checksum_partial[0];
Font Patterns (FNG)

## Page 180

158 FOCA Reference
8–9 T echnology-specific object identifier length
Length of the technology-specific object identifier plus the length of this field.
10–n T echnology-specific Object Identifier
Character string name of the object, as defined by the technology owner.
n+1 to
n+2
T echnology-specific Object Descriptor Length
Length of the T echnology-specific Object Descriptor plus the length of this field. The existence of this
field (and the associated descriptor) is dependent on the outline font Pattern T echnology Identifier of
the FNC Structured Field.
If the Pattern T echnology Identifier is X'1F' (outline font, Type 1 PFB), this field and the associated
descriptor data is omitted. If the Pattern T echnology Identifier is X'1E' (outline font, CID Indexed), this
field and the associated descriptor data is as described.
Note: If additional outline font technologies are supported in the future, it is expected that these
descriptor length and descriptor data fields will be used to describe the FNG data objects.
n+3 to
m
T echnology-specific Object Descriptor: Description of the T echnology-specific Object. The content of
this field is as described below:
Byte n+3: Object Type
0 = no information or N/A
1 = CMap File
2–4 = (reserved)
5 = CID file
6 = PFB file
7 = AFM file
8 = Filename Map File (eg. ATMDATA.DAT)
9–255 = (reserved)
See “Object Type” on page 97, and “Linkage Code” on page 97.
If the Object Type is 0, 2–4, or 9–255, the T echnology-specific Object Descriptor Length is determined
by the generator. Any data that occurs in the remainder of this field is not defined by this architecture
and should be ignored.
If the Object Type is 1 (CMAP), the T echnology-specific Object Descriptor Length is ten (two byte-
length, one object-type, and seven descriptor bytes) and shall be as described below:
Byte n+4 Precedence Code: 0 = Primary, 1 = Auxiliary
Note: Auxiliary objects are ignored, unless referenced within another technology-specific object.
Byte n+5 Linkage Code: 0 = Linked, 1 = Unlinked
Byte n+6: Writing Direction Code:
0 = no information or N/A
1 = horizontal
2 = vertical
3 = both vertical and horizontal
4–255 = (reserved)
Byte n+7 to n+8: IBM GCSGID of the CMAP
Byte n+9 to n+10: IBM CPGID of the CMAP
Font Patterns (FNG)

## Page 181

FOCA Reference 159
If the Object Type is 5 (CID), the T echnology-specific Object Descriptor Length is eight (two byte-
length, one object-type, and five descriptor bytes) and shall be as described below:
Byte n+4 Precedence Code: 0 = Primary, 1 = Auxiliary
Note: Auxiliary objects are ignored, unless referenced within another technology-specific object.
Byte n+5 to Byte n+6: Maximum V(y) value for all characters in the CID font
Byte n+7 to Byte n+8: Maximum W(y) value for all characters in the CID font
If the Object Type is 6 (PFB), 7 (AFM), or 8 (File Name Map), the T echnology-specific Object
Descriptor Length is three (two byte-length and one object-type bytes), and no other descriptor
information is provided.
m+1 to
end
T echnology-specific object data
The data associated with this object, in the format defined for the outline font technology vendor.
Font Patterns (FNG)

## Page 182

160 FOCA Reference
FNI – D38C89 – Font Index
The MO:DCA Structured Field Introducer (SFI) segmentation flag is used for DBCS outline font FNI structured
fields. This allows more than one FNI per rotation. DBCS outline fonts can have several thousand characters,
so the implementation of SFI segmentation is imperative for DBCS outline fonts.
For each character in a raster font, the Font Index (FNI) describes specific characteristics and points to an
entry in the Font Patterns Map (FNM) structured field.
For outline fonts, there is no Font Patterns Map (FNM) structured field, so the Font Index (FNI) does not
include the FNM Index. The FNM Index should be set to zero and ignored.
A font character set can have one, two, three, or four FNI structured fields, one for each rotation value. The FNI
to be used for a given rotation is specified in the appropriate repeating group of the character Font Orientation
(FNO) structured field.
For outline font technology X'1E' (CID Keyed), if the repeating group for a given Graphic Character GID is not
included in the FNI structured field, the “Nominal Character Increment” in the FNO structured field should be
used as the character increment.
Note: For storage and processing efficiency, if a large number of characters in the font character set share the
same character increment, the “Nominal Character Increment” in the FNO structured field may be set to
that nominal value and the repeating group for all characters having that same nominal value may be
omitted from the FNI structured field.
Length
(2 bytes)
X'D38C89'
(3 bytes)
X'00',
X'20'
X'0000'
(2 bytes)
Structured Field Data
([10 or 28]*[number of characters] bytes)
Note: There is one FNI repeating-group entry for each character in a font. When the font contains a larger
number of characters than can fit in a single 32K-byte long FNI structured field (for example, a DBCS
font), the FNI data can be carried in a series of consecutive, segmented FNI structured fields. In this
case, each FNI structured field except for the last FNI in the series has the segmentation-indicator flag
(bit 2) set to B'1'.
An FNI consists of one or more repeating groups, sorted into ascending order by the character IDs (bytes 0–7).
The length of each repeating group is specified in byte 15 of the FNC structured field. A length of 28 is used for
all raster fonts; outline fonts can use a length of 10 (when the optional parameters are not specified) or a length
of 28. Each repeating group is divided as follows:
Offset Type Name Range Meaning M/O
0–7 CHAR GCGID See Below Graphic Character Global Identifier M
8–9 UBIN CharInc
0–255
0–32,767
Character Increment
Fixed
Relative
M
10–11 SBIN AscendHt
± 256
± 32,767
Ascender Height
Fixed
Relative
O
12–13 SBIN DescendDp
± 256
± 32,767
Descender Depth
Fixed
Relative
O
14–15 UBIN Reserved X'0000' Reserved for future use O
16–17 UBIN FNMCnt See Below FNM Index O
Font Index (FNI)

## Page 183

FOCA Reference 161
Offset Type Name Range Meaning M/O
18–19 SBIN ASpace
± 256
± 32,767
A-Space
Fixed
Relative
O
20–21 UBIN BSpace
0–256
0–32,767
B-space
Fixed
Relative
O
22–23 SBIN CSpace
± 256
± 32,767
C-Space
Fixed
Relative
O
24–25 UBIN Reserved X'0000' Reserved for future use O
26–27 SBIN BaseOset
± 256
± 32,767
Baseline Offset
Fixed
Relative
O
Bytes Description
0–7 Graphic Character Global Identifier
See “Graphic Character Global Identifier” on page 95.
This identifier is used in bytes 0–7 of one or more repeating groups in the Code Page Index (CPI)
structured field to assign a code point to the character.
An error exists if the same character ID occurs in more than one repeating group in a Font Index (FNI).
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
Note: The letters in a character ID are case-sensitive. Therefore LA020000, La020000, lA020000, and
la020000 denote four different characters. IBM font products use uppercase alphanumeric
character IDs based on IBM Corporate Standards. While other naming schemes can be used, it
is important to use a consistent naming scheme across all font objects in a system; therefore,
the GCGID scheme used by IBM font products is recommended.
8–9 Character Increment
See “Character Increment” on page 94.
The number of pels or relative units by which the current print position changes in the inline (print)
direction when the character is printed. This value generally equals the sum of the A-space, B-space,
and C-space of the character. The value X'0000' indicates no change.
This value is used only if bit 7 of byte 12 of the Font Orientation (FNO) structured field is B'0'
(maximum increment) and the character is one of the following:
• The default character and bit 2 of byte 8 of the Code Page Control (CPC) structured field is set to
B'0' (the current print position is incremented by the default character increment).
• Any other character and bit 2 of byte 8 of the repeating group for the character in the Code Page
Index (CPI) structured field is set to B'0' (the current print position is incremented by this character
increment).
If bit 7 of byte 12 of the Font Orientation (FNO) structured field is B'1' (uniform increment), this
parameter should have the same value as bytes 6 and 7 of that structured field.
Note: This value is used in positioning the character. For outline fonts, the FNI character increment
need only be specified if the character increment is different from the “Nominal Character
Increment” in the FNO structured field.
Font Index (FNI)

## Page 184

162 FOCA Reference
10–11 Character Ascender
See “Ascender Height” on page 91.
The number of pels or relative units between the topmost toned pel and the character baseline. This
value is negative for characters having their topmost toned pel below the character baseline.
For positive values, the topmost pel is included in the count. For negative fixed-metric values, the
topmost pel is also included in the count; zero is not a valid value. For negative relative-metric values,
the topmost pel is not included in the count; zero means the top of the topmost pel is immediately
below the baseline.
Note: This measurement is not the height of an ascender in the typographic terms, that is, the portion
of certain lowercase characters that rises above the main body.
12–13 Character Descender
See “Descender Depth” on page 95.
The number of pels or relative units between the bottom of the bottommost toned pel and the character
baseline. This value is negative for characters having the bottom of their bottommost toned pel above
the character baseline.
For positive values, the bottommost pel is included in the count. For negative fixed-metric values, the
bottommost pel is also included in the count; zero is not a valid value. For negative relative-metric
values, the bottommost pel is not included in the count; zero means the bottom of the bottommost pel
is immediately above the baseline.
Note: This measurement is not the depth of a descender in typographic terms, that is, the portion of
certain characters that extends below the main body.
14–15 Reserved
A constant value of X'0000' that is r
eserved for future use.
16–17 FNM Index
This is a control parameter for managing the structured field content. The value represents the index
number of the repeating group in the Font Patterns Map (FNM) structured field that describes this
character. The index is based on 0 and is from 0 to the number of FNM structured field repeating
groups minus 1.
Note: This field does not have meaning when the FNGs have no pattern data (for example, Metrics
Only Fonts), or if the font contains outline font technology (X'1E' or X'1F') data.
18–19 A-Space
See “A-space” on page 91.
The number of pels or relative units measured in the inline (print) direction, from the current print
position to the near edge of the toned pel box (that is, up to but not including the first toned pel of the
character).
A negative A-space means that some part of the toned pel box extends before the current print
position when this character is printed (left kerning).
20–21 B-Space
See “B-space” on page 92.
The measurement of toned pels (inclusive) taken in the inline (print) direction of a given character (that
is, the width for 0 and 180 degrees rotation or the height for 90 and 270 degrees rotation in pels or
relative units of the toned pel box.
Font Index (FNI)

## Page 185

FOCA Reference 163
22–23 C-Space
See “C-space” on page 94.
The number of pels or relative units measured in the inline (print) direction from the far edge of the
toned pel box (that is, from but not including the toned pel furthest from the character reference point)
to the character escapement point.
A negative C-space value means that the next print position overlaps some part of the toned pel box
when the character is printed (right kerning).
24–25 Reserved
A constant value of X'0000' that is r
eserved for future use.
26–27 Baseline Offset
See “Baseline Offset” on page 92.
The number of pels or relative units between the top of the raster-pattern box and the character
baseline.
For positive values, the topmost pel or relative unit is included in the count. For negative fixed-metric
values, the topmost pel is also included in the count; zero means that the presentation services
software sets the character’s “not print” flag on. For negative relative metric values, the relative units
representing the topmost pel are not included in the count; zero means the relative unit representing
the top of the topmost pel is immediately below the baseline.
Notes:
1. This value is used in positioning the character.
2. The presentation services software
increments negative fixed metric values by +1 as it builds the
IPDS Load Font Index command.
Font Index (FNI)

## Page 186

164 FOCA Reference
FNM – D3A289 – Font Patterns Map
The Font Patterns Map (FNM) structured field describes some characteristics of the font character patterns.
Each pattern is described in a separate repeating group. This structured field is omitted if the font does not
contain raster patterns.
Length
(2 bytes)
X'D3A289'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(8*[number of patterns] bytes)
The repeating groups are sorted by the Pattern Data Address field and must be in the same order as the
characters in the Font Patterns (FNG) structured field. The FNM can contain up to 1000 repeating groups. The
length of each repeating group (8 bytes) is specified in byte 21 of the FNC structured field. Each repeating
group is divided as follows:
Offset Type Name Range Meaning M/O
0–1 UBIN CharBoxWd
0–255
0–32,767
Character Box Width
Fixed
Relative
M
2–3 UBIN CharBoxHt
0–255
0–32,767
Character Box Height
Fixed
Relative
M
4–7 UBIN PatDOset X'00' to X'FFFFFFFF' Pattern Data Offset M
Bytes Description
0–1 Character Box Width
See “Character Box Width” on page 93.
The width of the raster pattern box in pels, minus 1 (the first column is specified as 0; a 0° character
rotation is assumed.
Note: Some server software refers to this value as x-extent in some messages. The term x-extent has
a different meaning for characters than it does for other resources, such as page overlays.
2–3 Character Box Height
See “Character Box Height” on page 93.
The height of the raster pattern box in pels, minus 1 (the first row is specified as 0; a 0° character
rotation is assumed.
Note: Some server software
refers to this value as y-extent in some messages. The term y-extent has
a different meaning for characters than it does for other resources, such as page overlays. See
default baseline increment in the Font Orientation (FNO) structured field.
4–7 Pattern Data Offset
See “Pattern Data Offset” on page 100.
The start of the raster pattern for this character. This value multiplied by 1, 4, or 8 according to byte 16
(pattern data address alignment) in the Font Control (FNC) structured field is the offset, in bytes, to the
first pel of the character raster pattern contained in the collection of character patterns from all of the
Font Patterns (FNG) structured fields.
Note: In an AFP environment, the following rules apply:
• Pattern data offsets must be in ascending order.
• Each offset must be greater than the previous offset.
• Raster patterns must not overlap.
Font Patterns Map (FNM)

## Page 187

FOCA Reference 165
FNN – D3AB89 – Font Name Map
The Font Name Map is used to map IBM character names to the character names in outline fonts.
Length
(2 bytes)
X'D3AB89'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(2 or 16–32,759 bytes)
The data for the FNN structured field is divided into three sections. The first section identifies the encoding
scheme used in the the second and third sections FNN fields. Multiple physical records might be required to
contain all the data in a logical FNN.
Offset Type Name Range Meaning M/O
0 CODE IBM format
X'02'
IBM character ID format:
EBCDIC GCGID
M
1 CODE T echnology
format X'03'
X'05'
T echnology-specific character ID format:
Font-specific ASCII character name, used
with Type 1 PFB fonts
CMAP binary code point, used with
CID-keyed fonts
M
Bytes Description
0 IBM format
See “Graphic Character Identifier Type” on page 105.
This parameter defines the type of graphic character identifiers used in the first of the two repeating
groups of this structured field. Most often, these will be the IBM GCGIDs which occur in code page
objects.
1 T echnology-specific format
See “Graphic Character Identifier Type” on page 105.
This parameter defines the type of graphic character identifiers used in the second of the two
repeating groups of this structured field. Most often, these will be the character identifiers which occur
in the font character set.
The second section of the structured field is made of repeating groups containing IBM GCGIDs and indices to
the outline font technology character names. The third section of the structured field contains the outline font
technology character names and the length of each mapped name. The Font Control structured field gives the
number (bytes 40–41) of IBM GCGIDs included in this structured field. The IBM GCGIDs are sorted in
ascending order. Duplicate GCGIDs are not allowed. The Font Control structured field gives the repeating
group length (byte 35) for the FNN repeating group. The repeating group, containing zero or more entries, has
the following format:
Offset Type Name Range Meaning M/O
0–7 UNDF GCGID See Below Graphic Character Global ID O
8–11 UBIN TSOffset X'0' to X'FFFFFFFF' T echnology-specific identifier offset O
Bytes Description
0–7 Graphic Character Identifier
See “Graphic Character Global Identifier” on page 95.
This parameter defines the graphic character identifier; most often, this will be the IBM GCGID.
Font Name Map (FNN)

## Page 188

166 FOCA Reference
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
Note: The letters in a character ID are case-sensitive. Therefore LA020000, La020000, lA020000, and
la020000 denote four different characters. IBM font products use uppercase alphanumeric
character IDs based on IBM Corporate Standards. While other naming schemes can be used, it
is important to use a consistent naming scheme across all font objects in a system; therefore,
the GCGID scheme used by IBM font products is recommended.
8–11 T echnology-specific identifier offset
This is a control parameter that manages the structured field content. This parameter defines the offset
into the second of the two repeating groups of this structured field where the T echnology-specific
identifier will be found. It contains a count of the number of bytes from the first data byte of the first
FNN structured field to the technology-specific identifier which maps to the GCGID named in this
repeating group.
The third FNN section is variable length. The length of each member of the third section is defined in the first
byte of each member of the data area. The data area format for outline fonts with pattern technology identifier
X'1E' or X'1F' is:
Offset Type Name Range Meaning M/O
0 UBIN TSIDLen 2–128 T echnology-specific identifier length O
1–n UNDF TSID See Below T echnology-specific identifier O
Bytes Description
0 T echnology-specific identifier length
This is a control parameter that manages the structured field content. This parameter defines the
length of the character identifier, plus the length of this field; from 2 to 128 bytes.
1–n T echnology-specific identifier
This parameter defines the T echnology-specific identifier; the type of identifier is determined by byte 1
of the first section of this structured field.
FNN example
Table 16. FNN Example
Offset Meaning Value Comments
0 Graphic Character Identifier
Type
X'02' The second section will be represented in EBCDIC
1 Graphic Character Identifier
Type
X'03' The third section will be represented in Adobe
ASCII
End first section
Start second section
2–9 GCGID LA010000 GCGID for lowercase 'a'
10–13 Offset to technology-specific
identifier corresponding to
LA010000
X'0000005F' 95
14–21 GCGID LA020000 GCGID for uppercase 'A'
22–25 Offset to technology-specific
identifier corresponding to
LA020000
X'00000061' 97
Font Name Map (FNN)

## Page 189

FOCA Reference 167
Table 16 FNN Example (cont'd.)
Offset Meaning Value Comments
26–33 GCGID LB010000 GCGID for lowercase 'b'
34–37 Offset to technology-specific
identifier corresponding to
LB010000
X'0000005D' 93
38–45 GCGID LB020000 GCGID for uppercase 'B'
46–49 Offset to technology-specific
identifier corresponding to
LB020000
X'00000069' 105
50–57 GCGID SP010000 GCGID for space character
58–61 Offset to technology-specific
identifier corresponding to
SP010000
X'00000063' 99
62–69 GCGID ZX020000 GCGID for imaginary character
70–73 Offset to technology-specific
identifier corresponding to
ZX020000
X'0000004A' 74
End second section
Start third section
74 T echnology-specific identifier
length
X'13' (19) Section three items are not required to have a
specific order
75–92 T echnology-specific identifier Imaginary
Character
93 T echnology-specific identifier
length
2 Section three items are not required to have a
specific order
94 T echnology-specific identifier b
95 T echnology-specific identifier
length
2 Section three items are not required to have a
specific order
96 T echnology-specific identifier a
97 T echnology-specific identifier
length
2 Section three items are not required to have a
specific order
98 T echnology-specific identifier A
99 T echnology-specific identifier
length
6 Section three items are not required to have a
specific order
100–104 T echnology-specific identifier space
105 T echnology-specific identifier
length
2 Section three items are not required to have a
specific order
106 T echnology-specific identifier B
End third section
Font Name Map (FNN)

## Page 190

168 FOCA Reference
FNO – D3AE89 – Font Orientation
Each repeating group in the Font Orientation (FNO) structured field applies to one character rotation of a font
character set. There can be one, two, three, or four repeating groups.
Length
(2 bytes)
X'D3AE89'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(26*[number of repeating groups] bytes)
The length of each repeating group (26 bytes) is specified in byte 14 of the FNC structured field. Each
repeating group is divided as follows:
Offset Type Name Range Meaning M/O
0–1 UBIN Reserved X'0000' Reserved for future use M
2–3 UBIN CharRot
X'0000'
X'2D00'
X'5A00'
X'8700'
Character Rotation:
0 degrees
90 degrees
180 degrees
270 degrees
M
4–5 SBIN MaxBOset
± 256
± 32,767
Maximum Baseline Offset
Fixed
Relative
M
6–7 UBIN MaxCharInc
0–255
0–32,767
Maximum Character Increment
Fixed
Relative
M
8–9 UBIN SpCharInc
0–255
0–32,767
Space Character Increment
Fixed
Relative
M
10–11 UBIN MaxBExt
0–255
0–32,767
Maximum Baseline Extent
Fixed
Relative
M
12 BITS OrntFlgs See Below Orientation Control Flags M
13 UBIN Reserved X'00' Reserved for future use M
14–15 UBIN EmSpInc
0–255
0–32,767
Em-Space Increment
Fixed
Relative
M
16–17 UBIN Reserved X'0000' Reserved for future use M
18–19 UBIN FigSpInc
0–255
0–32,767
Figure Space Increment
Fixed
Relative
M
20–21 UBIN NomCharInc
0–32,767
Nominal Character Increment
Relative
M
22–23 UBIN DefBInc 0–65,535 Default Baseline Increment M
24–25 SBIN MinASp
± 255
± 32,767
Minimum A-Space
Fixed
Relative
M
Bytes Description
0–1 Reserved
Constant X'0000'; this field is reserved for future use.
Font Orientation (FNO)

## Page 191

FOCA Reference 169
2–3 Character Rotation
See “Character Rotation” on page 58.
Identifies the character rotation applicable to this repeating group of the FNO structured field.
4–5 Maximum Baseline Offset
See “Maximum Baseline Offset” on page 79.
If bit 6 of byte 12 (flags) is set to B'1' (uniform), this parameter is the uniform baseline offset in pels or
relative units for all the characters in this rotation. This means that all the characters have the same
baseline offset.
If bit 6 of byte 12 (flags) is set to B'0' (maximum), this parameter is the maximum baseline offset in pels
or relative units for all characters in this rotation. The baseline offset for each character is specified in
bytes 26 and 27 of the Font Index (FNI) structured field repeating group.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the high sections X'45' to X'FE' of the same coded font.
6–7 Maximum Character Increment
See “Maximum Character Increment” on page 80.
If bit 7 of byte 12 (flags) is set to B'1' (uniform), this parameter is the uniform character increment in
pels or relative units for all the characters in this rotation. This means that all the characters have the
same character increment.
If bit 7 of byte 12 (flags) is set to B'0' (maximum), this parameter is the maximum character increment
in pels or relative units for all characters in this rotation. The character increment for each character is
specified in bytes 8 and 9 of the Font Index (FNI) structured field repeating group.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the high sections X'45' to X'FE' of the same coded font.
8–9 Space Character Increment
See “Space Character Increment” on page 85.
The increment, in pels or relative units, to be used when a space character code point appears in the
text and no Set Variable Space Character Increment (SVI) has preceded the code point. The value is
usually equal to the width of the space character (character ID SP010000). The value is also called
word space.
10–11 Maximum Baseline Extent
See “Maximum Baseline Extent” on page 78.
This measurement, in pels or relative units, is equal to the maximum baseline offset for any character
in the font character set plus the distance from the baseline to the bottom edge of the character box for
any character in the font character set. The value must be equal to or greater than the maximum
baseline offset, that is, negative values are ignored.
Note: This value is used to determine if the characters are positioned off the page.
For uniform character boxes (FNC flag bit 6 on), the maximum baseline extent value is equivalent to
either the height or width of the character box (before being extended to meet byte boundary
requirements) depending on the character rotation.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the high sections X'45' to X'FE' of the same coded font.
Font Orientation (FNO)

## Page 192

170 FOCA Reference
12 Control Flags
Bits
0–2
Font Index Number
The value is used to select the Font Index (FNI) structured field for the repeating group for this
character rotation.
The value B'000' selects the first FNI, B'001' the second FNI, and so on.
Bit 3 B'0'; reserved
Bit 4 Kerning (see “Kerning Pair Data” on page 62)
B'0' No kerning data
B'1' Kerning data
Bit 5 Uniform A-space (see “Uniform A-space” on page 89)
Shows whether the value in bytes 24 and 25 is a minimum or uniform A-space for this
character rotation:
B'0' Minimum A-space
B'1' Uniform A-space
Double-Byte Raster Coded Fonts
Must be set to B'1' for sections X'45' to X'FE'.
Bit 6 Uniform Baseline Offset (see “Uniform Baseline Offset” on page 89)
Shows whether the value in bytes 4 and 5 is a maximum or uniform baseline offset for this
character rotation:
B'0' Maximum baseline offset
B'1' Uniform baseline offset
Double-Byte Raster Coded Fonts
Must be set to B'1' for sections X'45' to X'FE'.
Bit 7 Uniform Character Increment (see “Uniform Character Increment” on page 90)
Shows whether the value in bytes 6 and 7 is a maximum or uniform character increment for
this character rotation:
B'0' Maximum character increment
B'1' Uniform character increment
Double-Byte Raster Coded Fonts
Must be set to B'1' for sections X'45' to X'FE'.
13 Reserved
14–15 Em Space Increment
See “Em-Space Increment” on page 59.
The character increment, in pels or relative units, of an em space. An em is the square whose sides
are formed by the value of the point size; for example, 9 points at 240-pel by 240-pel resolution is 30
pels, a 9-point em at that resolution is 30 pels wide and 30 pels high.
16–17 Reserved
Font Orientation (FNO)

## Page 193

FOCA Reference 171
18–19 Figure Space
See “Figure Space Increment” on page 75.
The character increment, in pels or relative units, for numerals 0 through 9. A figure space is usually
equal to the width of the numeric space character (character ID SP310000).
20–21 Nominal Character Increment
See “Nominal Character Increment” on page 84 for a description of this parameter and how it can be
used efficiently.
This parameter is only applicable to outline font technology X'1E'. Its value should be ignored for all
other technologies. This value should be set to either the uniform character increment or the nominal
character increment to be used if no FNI character increment value is provided.
22–23 Default Baseline Increment
See “Default Baseline Increment” on page 74.
The default increment, in pels or relative units, between character baselines. A text formatter can use
this value when a line spacing value is not specified. For example, the following conversion from lines-
per-inch to pels can
be helpful:
Table 17. Sample Lines-Per-Inch to Pel Conversions
Lines-per-inch 240 Pels 300 Pels
12 20 25
10 24 30
8 30 37.5
6 40 50
24–25 Minimum A-space
See “Minimum A-space” on page 83.
If bit 5 of byte 12 (flags) is set to B'1' (uniform), this parameter is the uniform A-space in pels or relative
units to be used instead of the individual character A-space to position raster pattern boxes. This value
can be positive or zero, but not negative.
If bit 5 of byte 12 (flags) is set to B'0' (minimum), this value, in pels or relative units, is the largest left
kern for all the characters in this rotation.
Double-Byte Raster Coded Fonts
Must be the same for all font character sets in the high sections X'45' to X'FE' of the same coded font.
Font Orientation (FNO)

## Page 194

172 FOCA Reference
FNP – D3AC89 – Font Position
The Font Position (FNP) structured field describes the common characteristics of all the characters in a font
character set. Four repeating groups, one for each character rotation, are allowed. The order of these groups
must correspond to the order of the repeating groups in the Font Orientation (FNO) structured field, each of
which specifies a separate character rotation.
Length
(2 bytes)
X'D3AC89'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(22*[number of repeating groups] bytes)
The length of each repeating group (22 bytes) is specified in byte 20 of the FNC structured field. Each
repeating group is divided as follows:
Offset Type Name Range Meaning M/O
0–1 UBIN Reserved X'0000' Reserved for future use M
2–3 SBIN LcHeight
± 256
± 32,767
Lowercase Height
Fixed
Relative
M
4–5 SBIN CapMHt
± 256
± 32,767
Cap-M Height
Fixed
Relative
M
6–7 SBIN MaxAscHt
± 256
± 32,767
Maximum Ascender Height
Fixed
Relative
M
8–9 SBIN MaxDesDp
± 256
± 32,767
Maximum Descender Depth
Fixed
Relative
M
10–14 UBIN Reserved X'00...00' Reserved for future use M
15 UBIN Retired X'01' Retired Parameter M
16 UBIN Reserved X'00' Reserved for future use M
17–18 UBIN UscoreWd
0–255
0–32,767
Underscore Width (units)
Fixed
Relative
M
19 UBIN UscoreWdf X'00' Underscore Width (fraction) M
20–21 SBIN UscorePos
± 256
± 32,767
Underscore Position
Fixed
Relative
M
Font Position (FNP)

## Page 195

FOCA Reference 173
Bytes Description
0–1 X'0000'; Reserved for future use
2–3 Lowercase Height
See “X-Height” on page 74.
The Lowercase Height is the nominal height above the baseline (ignoring the ascender) of the
lowercase characters in a font and is usually the height of the lowercase letter x. The value of
lowercase height is specified by a font designer.
Notes:
1. This value is negative if all the characters are totally below the character baseline.
2. For most fonts the lowercase height value for the 0° rotation is used for all rotations.
3. The value X'0000' can mean that this parameter is unspecified.
4–5 Cap-M Height
See “Cap-M Height” on page 57.
The Cap-M Height is the nominal height above the baseline for uppercase character shapes and is
usually equal to the height of the uppercase letter M. The cap-M height value is specified by a font
designer.
Notes:
1. This value is negative if all the characters are totally below the character baseline.
2. For most fonts the cap-M height value for the 0° rotation is used for all rotations.
3. The value X'0000' can mean that this parameter is unspecified.
6–7 Maximum Ascender
See “Maximum Ascender Height” on page 78.
The maximum, in pels or relative units, of the character-ascender values for this character rotation.
The character-ascender values are found in the corresponding FNI.
Note: This value is zero or negative if all the characters are totally below the character baseline.
8–9 Maximum Descender
See “Maximum Descender Depth” on page 81.
The maximum, in pels or relative units, of the character-descender values for this character rotation.
The character-descender values are found in the corresponding FNI.
Note: This value is zero or negative if all the characters are totally above the character baseline.
10–14 X'0000000000'; Reserved for future use
15 X'01'; Constant
16 X'00'; Reserved for future use
Font Position (FNP)

## Page 196

174 FOCA Reference
17–19 Underscore Width
See “Underscore Width” on page 89.
Recommended thickness, in pels or relative units, of the stroke used by formatters to underscore
characters in text if FND byte 64 bit 1 is B'0' (characters are not underscored). Bytes 17 and 18
represent an integer; byte 19 is set to X'00'.
If the integer value is zero, no underscore stroke is intended or specified.
When a Draw Baseline Rule (DBR) text control is used to draw the underscore, this parameter
specifies the suggested width (thickness) of the underscore. If the underscore thickness is greater
than 32 pels, the processor (for example, DCF) draws multiple rules. Refer to Presentation Text Object
Content Architecture Reference for DBR information.
When a page or overlay is printed, the printing system will detect an error if the underscore thickness
would cause pels to be printed beyond the boundaries of the page or overlay. Refer to Mixed Object
Document Content Architecture Reference for Page Descriptor (PGD) information.
20–21 Underscore Position
See “Underscore Position” on page 88.
A value of zero has no meaning.
If the value of FND byte 64 bit 1 is B'0' (characters are not underscored), this value specifies the
recommended distance, in pels or relative units, from the baseline to the topmost pel of the underscore
stroke, excluding the topmost pel. A positive value indicates an underscore that begins below the
baseline. A negative value indicates an underscore that begins above the baseline.
When a page or overlay is printed, the printing system will detect an error if the underscore position
would cause pels to be printed beyond the boundaries of the page or overlay. Refer to Mixed Object
Document Content Architecture Reference for Page Descriptor (PGD) information.
Font Position (FNP)

## Page 197

FOCA Reference 175
NOP – D3EEEE – No Operation
The No Operation (NOP) structured field can be used to carry comments or other types of unarchitected data.
Length
(2 bytes)
X'D3EEEE'
(3 bytes)
Flags
(1 byte)
X'0000'
(2 bytes)
Structured Field Data
(0–32,759 bytes)
Offset Type Name Range Meaning M/O
0–end UNDF NopData Any value Up to 32,759 bytes of data with no
architectural definition
O
No Operation (NOP)

## Page 198

176 FOCA Reference
Triplets
Structured fields may include “triplets”, which are self-identifying extensions to the positional parameters. If a
triplet is included in a structured field, all preceding positional parameters become mandatory. A triplet contains
three components as shown below.
Table 18. Triplet Syntax
Offset Type Name Range Meaning M/O
0 UBIN TripLen 3–255 Triplet Length M
1 CODE TripID 1–255 Triplet Identifier M
2–254 TripVal Triplet Value M
Bytes Description
0 Triplet Length
Specifies the length of the triplet, including this byte.
1 Triplet Identifier
Identifies this triplet.
2–254 Triplet Value
Contains the data for this triplet.
Table 19. Summary of FOCA Triplets
Triplet ID Triplet Name Carrying Structured Fields
X'02' “X'02' – Fully Qualified Name Triplet” on page 177 FND
X'62' “X'62' – Local Date and Time Stamp Triplet” on page 178 BCP , BFN
X'63' “X'63', Type 1 – CRC Resource Management Triplet” on page 180 BCP , BFN
X'63' “X'63', Type 2 – Font Resource Management Triplet” on page 181 BCP , BFN
X'64' “X'64' – Object Origin Identifier Triplet” on page 183 BCP , BFN
X'65' “X'65' – User Comment Triplet” on page 184 BCP , BFN
X'6D' “X'6D' – Extension Font Triplet” on page 185 FNC
X'79' “X'79' – Metric Adjustment Triplet” on page 186 CFC

## Page 199

FOCA Reference 177
X'02' – Fully Qualified Name Triplet
This triplet enables the identification and referencing of objects using Global Identifiers. The semantics of this
triplet are defined in MO:DCA, but are repeated here for reader convenience.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 5–254 Triplet Length M
1 CODE TripID X'02' Triplet Identifier M
2 CODE FQNType
X'07'
X'08'
Specifies how the GID will be used:
Family Name
Typeface Name
M
3 Reserved, must be zero M
4–253 CHAR FQName GID of the object. Can be up to 250 bytes long. M
Bytes Description
0 Triplet Length
Specifies the length of the triplet, including this byte.
1 Triplet Identifier
A value of X'02' identifies this triplet as a Fully Qualified Name triplet.
2 Fully Qualified Name Type
X'07' Family name
The family name is a human-readable name for a group of fonts that are stylistic variants of a
single design. This identifier corresponds to the family name of the font design, for example
“Times New Roman” is the family name for the “Monotype Times New Roman Expanded” font
design. The family name is a character string that normally also appears as a substring in the
typeface name.
Note: Family names are not consistently identified in the industry, therefore it might
be
necessary for implementations to define a synonym table for mapping names. For
example, the name “TimesNewRoman” might need to be mapped to “Times New
Roman”.
X'08' Typeface name
The triplet contains the name of the font typeface; for example, “Times New Roman Bold
Italic”. This identifier corresponds to the full name of the typeface as specified by the font
supplier. This is the user interface name which, for example, might
be used for specification or
selection of the font design.
When the font is built from an Adobe Type 1 font, the typeface name is taken from the
FullName field in the original Adobe Type 1 font.
3 Reserved
4–253 Fully Qualified Name
This parameter is EBCDIC encoded using CPGID 500 and GCSGID 103.
Structured fields using triplet X'02':
• “FND – D3A689 – Font Descriptor” on page 152
Fully Qualified Name Triplet

## Page 200

178 FOCA Reference
X'62' – Local Date and Time Stamp Triplet
This triplet identifies the date and time that the object was created or revised.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 17 Triplet Length M
1 CODE TripID X'62' Identifies this triplet as a Local Date and Time
Stamp triplet
M
2 CODE TSType
X'00'
X'01'
X'03'
Time Stamp Type
Creation
(retired)
Revision
M
3 CODE Year
(part 1)
X'40'
X'F0'–X'F9'
Thousands and hundreds
position of the year:
19xx
20xx through 29xx
M
4–5 CODE Year
(part 2)
X'F0F0'–
X'F9F9'
T ens and units position of the year M
6–8 CODE Day X'F0F0F1'–
X'F3F6F6'
Day of the year M
9–10 CODE Hour X'F0F0'–
X'F2F3'
Hour of the day M
11–12 CODE Minute X'F0F0'–
X'F5F9'
Minute of the hour M
13–14 CODE Second X'F0F0'–
X'F5F9'
Second of the minute M
15–16 CODE DecSec X'F0F0'–
X'F9F9'
Hundredths of the second M
Bytes Description
0 Triplet Length
Length of the triplet, including this byte.
1 Triplet Identifier
Identifies this triplet as the Local Date and Time Stamp triplet.
2 Time Stamp Type
Identifies the type of time stamp.
X'00' Object Creation Date and Time
X'01' Retired for APSRMARK product's Date and Time
X'03' Object Revision Date and Time
3 Thousands and hundreds position of the year
This field identifies the first two digits of the year AD, using the Gregorian calendar. The 1900s are
encoded as X'40', the 2000s are encoded as X'F0', the 2100s as X'F1', the 2200s are encoded as
X'F2', and so on.
4–5 T ens and units position of the year
This field specifies the last two digits of the year AD, using the Gregorian calendar.
Local Date and Time Stamp Triplet

## Page 201

FOCA Reference 179
6–8 Day
This field specifies the day of the year, using the Gregorian calendar.
Table 20. Examples of the Date Fields in a Local Date and Time Stamp Triplet
Date Restructured as Encoded as
February 1, 1972 “ 72032” X'40F7F2F0F3F2'
December 31, 1999 “ 99365” X'40F9F9F3F6F5'
January 1, 2000 “000001” X'F0F0F0F0F0F1'
February 3, 2072 “072034” X'F0F7F2F0F3F4'
9–10 Hour of the Day, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9 (X'F9').
11–12 Minute of the Hour, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9
(X'F9').
13–14 Second of the Minute, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9
(X'F9').
15–16 Hundredth of the Second, as a two-character string using only the EBCDIC numbers 0 (X'F0') through
9 (X'F9').
Structured fields using triplet X'62':
• “BCP – D3A887 – Begin Code Page” on page 126
• “BFN – D3A889 – Begin Font” on page 128
Local Date and Time Stamp Triplet

## Page 202

180 FOCA Reference
X'63', Type 1 – CRC Resource Management Triplet
This triplet provides resource management information such as a Public/Private flag and a retired Cyclic
Redundancy Check (CRC) value, to be used in comparing two resource objects that should be identical.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 6 Triplet Length M
1 CODE TripID X'63' Resource Management triplet M
2 CODE FmtQual X'01' Format Qualifier M
3–4 UBIN RMValue Retired Resource Management value M
5 BITS ResClassFlg
Bit 0
Bits 1–7
B'0'
B'1'
B'0000000'
Public
Private
Reserved
M
Bytes Description
0 Triplet Length
Length of the triplet, including this byte.
1 Triplet Identifier
Identifies this triplet as a Font Resource Management triplet.
2 Format Qualifier
This value is a constant, identifying this as a type 1 resource management triplet.
3–4 Retired for Resource Management Value
This field is retired for IBM Print Services Facility™ (PSF) for Z/OS
, IBM Print Services Facility (PSF)
for VSE, and IBM Print Services Facility (PSF) for OS/400 private use only. A constant value of zero
(0) should be used in this field for all other applications.
The CRC is a numeric value calculated by the IBM
APSRMARK utility and used by the IBM Remote
PrintManager to map objects to locations in its resource library.
The CRC Resource Management format can exist at the same time in the same BCP structured field
with the Font Resource Management format.
5 Resource Class Flags
Bit 0 Private Use Flag
See “Private Use” on page 69.
Indicates whether or not this font resource contains data that is privately owned or protected
by a license agreement:
B'0' Contains no privately owned information
B'1' Contains privately owned information
Bits
1–7
Reserved
Structured fields using triplet X'63', Type 1:
• “BCP – D3A887 – Begin Code Page” on page 126
• “BFN – D3A889 – Begin Font” on page 128
CRC Resource Management Triplet

## Page 203

FOCA Reference 181
X'63', Type 2 – Font Resource Management Triplet
This triplet is retired for IBM Print Services Facility (PSF) for Z/OS , IBM Print Services Facility (PSF) for VSE ,
and IBM Print Services Facility (PSF) for OS/400 private use only.
This triplet, like the Type 1 triplet, provides additional information for comparing two resource objects that
should be identical.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 21 Triplet Length M
1 CODE TripID X'63' Resource Management triplet for fonts M
2 CODE FmtQual X'02' Resource Management triplet for fonts M
3–6 UBIN RMValue X'00' to
X'FFFFFFFF'
Engineering Change (EC) information M
7 CODE Year
(part 1) X'40'
X'F0'–X'F9'
Thousands and hundreds position of the year:
19xx
20xx through 29xx
M
8–9 CODE Year
(part 2)
X'F0F0' – X'F9F9' T ens and units position of the year M
10–12 CODE Day X'F0F0F1' –
X'F3F6F6'
Day of the year M
13–14 CODE Hour X'F0F0' – X'F2F3' Hour of the day M
15–16 CODE Minute X'F0F0' – X'F5F9' Minute of the hour M
17–18 CODE Second X'F0F0' – X'F5F9' Second of the minute M
19–20 CODE DecSec X'F0F0' – X'F9F9' Hundredths of the second M
Bytes Description
0 Triplet Length
Length of the triplet, including this byte.
1 Triplet Identifier
Identifies this triplet as a Font Resource Management triplet.
2 Format Qualifier
This value is a constant, identifying this as a Type 2 resource management triplet.
3–6 Resource Management Value
The Engineering Change (EC) information is provided by the IBM
APSRMARK utility and used by the
IBM Remote PrintManager to manage resident fonts.
The Font Resource Management format can exist at the same time in the same BCP structured field
with the CRC Resource Management format.
7 Thousands and hundreds position of the year
This field identifies the first two digits of the year AD, using the Gregorian calendar. The 1900s are
encoded as X'40', the 2000s are encoded as X'F0', the 2100s as X'F1', the 2200s are encoded as
X'F2', and so on.
8–9 T ens and units position of the year
This field specifies the last two digits of the year AD, using the Gregorian calendar.
Font Resource Management Triplet

## Page 204

182 FOCA Reference
10–12 Day
This field specifies the day of the year, using the Gregorian calendar.
Table 21. Examples of the Date Fields in a Font Resource Management Triplet
Date Restructured as Encoded as
February 1, 1972 “ 72032” X'40F7F2F0F3F2'
December 31, 1999 “ 99365” X'40F9F9F3F6F5'
January 1, 2000 “000001” X'F0F0F0F0F0F1'
February 3, 2072 “072034” X'F0F7F2F0F3F4'
13–14 Hour of the Day, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9 (X'F9').
15–16 Minute of the Hour, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9
(X'F9').
17–18 Second of the Minute, as a two-character string using only the EBCDIC numbers 0 (X'F0') through 9
(X'F9').
19–20 Hundredth of the Second, as a two-character string using only the EBCDIC numbers 0 (X'F0') through
9 (X'F9').
Structured fields using triplet X'63', Type 2:
• “BCP – D3A887 – Begin Code Page” on page 126
• “BFN – D3A889 – Begin Font” on page 128
Font Resource Management Triplet

## Page 205

FOCA Reference 183
X'64' – Object Origin Identifier Triplet
This triplet is used to identify the origin of the resource object.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 61 Triplet Length M
1 CODE TripID X'64' Object Origin Identifier triplet M
2 CODE FmtQual X'00'
X'01'
X'02'
X'03'
X'04'
X'05'–X'FF'
Reserved
MVS™
VM
PC
VSE
Reserved
M
3–10 CHAR HostID Host/System Identifier M
11–16 CHAR MediaID Media Identifier M
17–60 CHAR DataSID Data Set Identifier M
Bytes Description
0 Triplet Length
Length of the triplet, including this byte.
1 Triplet Identifier
Identifies this triplet as the Object Origin Identifier triplet.
2 Format Qualifier
This value is a constant, identifying this as a Type 1 resource management triplet.
3–10 Host/System Identifier
Identification of the specific system that owns the resource.
11–16 Media Identifier
Volume serial number of the data set.
17–60 Data Set Identifier
Identification of the resource file being processed.
Structured fields using triplet X'64':
• “BCP – D3A887 – Begin Code Page” on page 126
• “BFN – D3A889 – Begin Font” on page 128
Object Origin Identifier Triplet

## Page 206

184 FOCA Reference
X'65' – User Comment Triplet
This triplet contains user-defined data and has no effect on processing the object. Any number of comment
triplets are allowed within the limits of the maximum structured field length.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 2–255 Triplet Length M
1 CODE TripID X'65' Triplet Identifier M
2–254 CHAR Comment Optional user-defined information O
Bytes Description
0 Triplet Length
Specifies the length of the triplet, including this byte.
1 Triplet Identifier
A value of X'65' identifies this triplet as a User Comment triplet.
2–254 Triplet Value
See “Comment” on page 58.
Structured fields using triplet X'65':
• “BCP – D3A887 – Begin Code Page” on page 126
• “BFN – D3A889 – Begin Font” on page 128
User Comment Triplet

## Page 207

FOCA Reference 185
X'6D' – Extension Font Triplet
This triplet contains the GCSGID for the base font associated with an extension font. An extension font
contains user defined characters to be used with another font resource designated by the user.
Offset Type Name Range Meaning M/O
0 UBIN TripLen 4 Triplet Length M
1 CODE TripID X'6D' Triplet Identifier M
2–3 CODE GCSGID 0–65,536 Graphic Character Set GID M
Bytes Description
0 Triplet Length
Specifies the length of the triplet, including this byte.
1 Triplet Identifier
A value of X'6D' identifies this triplet as an Extension Font triplet
2–3 Triplet Value
See “Graphic Character Set Global Identifier” on page 61
This value identifies the graphic character set of the base font, to which the extension font is
appended.
Structured fields using triplet X'6D':
• “FNC – D3A789 – Font Control” on page 146
Extension Font Triplet

## Page 208

186 FOCA Reference
X'79' – Metric Adjustment Triplet
This triplet supplies metric values that can be used to adjust some of the metrics in an outline coded font. If
specified for a raster coded font, the triplet is ignored.
The Metric Adjustment triplet is only to be used with the specified vertical font size and specified horizontal
scale factor from the CFI structured field; if the specified vertical font size is X'0000', the triplet is ignored. If a
size from another source such as a MCF structured field is used, the Metric Adjustment triplet is ignored.
This triplet is defined as follows:
Offset Type Name Range Meaning M/O
0 UBIN Length X'0F' Length of the triplet, including this field M
1 CODE TID X'79' Identifies the Metric Adjustment triplet M
2 CODE UnitBase
X'00'
Metric technology unit base:
Fixed metrics, 10 inches
M
3–4 UBIN XUPUB X'0001'– X'7FFF' Units per unit base in the X direction M
5–6 UBIN YUPUB X'0001'– X'7FFF' Units per unit base in the Y direction M
7–8 SBIN H-uniform
increment
X'8000'– X'7FFF' Uniform character increment value for horizontal
writing
M
9–10 SBIN V-uniform
increment
X'8000'– X'7FFF' Uniform character increment value for vertical
writing
M
11–12 SBIN H-baseline
adjustment
X'8000'– X'7FFF' Baseline offset adjustment value for horizontal
writing
M
13–14 SBIN V-baseline
adjustment
X'8000'– X'7FFF' Baseline offset adjustment value for vertical
writing
M
Byte 0 Triplet length
This field contains the length of this triplet, including itself.
Byte 1 Triplet ID
A value of X'79' identifies this triplet as a Metric Adjustment triplet.
Byte 2 Metric technology unit base
Bytes 3–4 Units per unit base in the X direction
Bytes 5–6 Units per unit base in the Y direction
Bytes 3–4 and 5–6 must contain the same value.
Bytes 7–8 Uniform character increment value for horizontal writing
This value is used only with horizontal writing (character rotation = 0° or 180°), and is ignored
with vertical writing (character rotation = 90° or 270°).
This field specifies a uniform character increment value using the units of measure specified in
bytes 2–6.
If this value is not X'0000', the font will be treated as a uniform font and this value will be used
as the uniform character increment. For each character, the A-space and B-space is not
changed and the C-space is increased (or decreased) to achieve the specified character
increment.
If this value is X'0000', the character increment values from the font are used.
Metric Adjustment Triplet

## Page 209

FOCA Reference 187
Bytes 9–10 Uniform character increment value for vertical writing
This value is used only with vertical writing (character rotation = 90° or 270°), and is ignored
with horizontal writing (character rotation = 0° or 180°).
This field specifies a uniform character increment value using the units of measure specified in
bytes 2–6.
If this value is not X'0000', the font will be treated as a uniform font and this value will be used
as the uniform character increment. For each character, the A-space and B-space is not
changed and the C-space is increased (or decreased) to achieve the specified character
increment.
If this value is X'0000', the character increment values from the font are used.
Bytes 11–12 Baseline adjustment for horizontal writing
This value is used only with horizontal writing (character rotation = 0° or 180°), and is ignored
with vertical writing (character rotation = 90° or 270°).
This field specifies a baseline offset adjustment value using the units of measure specified in
bytes 2–6. For a character rotation of 0°, the value will be added to the baseline offset for each
character in the font. For a character rotation of 180°, the value will be subtracted from the
baseline offset for each character in the font.
Bytes 13–14 Baseline adjustment for vertical writing
This value is used only with vertical writing (character rotation = 90° or 270°), and is ignored
with horizontal writing (character rotation = 0° or 180°).
This field specifies a baseline offset adjustment value using the units of measure specified in
bytes 2–6. For a character rotation of 90°, the value will be added to the baseline offset for
each character in the font. For a character rotation of 270°, the value will be subtracted from
the baseline offset for each character in the font.
Structured fields using triplet X'79':
• “CFC – D3A78A – Coded Font Control” on page 130
Metric Adjustment Triplet

## Page 210

188 FOCA Reference
SAA CPI System Font Resource
The format for font resource data, to be passed to an application through the IBM Systems Application
Architecture® (SAA®) Common Programming Interface (CPI), is defined by the Query Font Metrics, Query Font
Width T able, and Query Kerning Pairs program calls. See Systems Application Architecture: Common
Programming Interface Presentation Reference, SC26-4359.
IPDS Device Font Resource
The format for font resource data, to be downloaded is defined in the Loaded-Font Command Set chapter of
the Intelligent Printer Data Stream (IPDS) architecture. See Intelligent Printer Data Stream Reference.
MO:DCA Presentation Document Font Reference
The format for font references, identifying the font resources used by any of the object content architectures
carried by the Mixed Object Document Content Architecture (MO:DCA), is defined by the MO:DCA Map Coded
Font (MCF) Structured Field. See Mixed Object Document Content Architecture Reference.
RFT-DCA Revisable Form Document Font Reference
The format for font references, identifying the font resources used by the Revisable Form T ext Document
Content Architecture (RFT-DCA), is defined by the Set CFID thru GFID (SFG) Structured Field. See Document
Content Architecture: Revisable-Form-Text Reference, SC23-0758.
SAA CPI System Font Reference
The format for font references, defined by the IBM Systems Application Architecture (SAA) Common
Programming Interface (CPI) is defined by the Create Logical Font program call. See Systems Application
Architecture: Common Programming Interface Presentation Reference, SC26-4359.
IPDS Device Font Reference
The format for font references in the Intelligent Printer Data Stream (IPDS) architecture is the Load Font
Equivalence (LFE) command. See Intelligent Printer Data Stream Reference.

## Page 211

Copyright © AFP Consortium 1998, 2015 189
