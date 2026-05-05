Appendix B. Resource Access Table (RAT)
Font Interchange Information
This appendix formerly contained information on acceptable values that may be used in the Map Coded Font
(MCF) structured field to identify a particular Font Object Content Architecture (FOCA) font. It is no longer
practical to maintain this material in an appendix. For detailed information on the FOCA fonts that may be
referenced with a MCF structured field in a MO:DCA data stream, please see the font publications listed in
“Related Publications” on page vii.
Note: The referenced documents use the term character set as a short form of the qualified term font character
set. The latter form is used throughout this book. In this context, the two forms are equivalent.
The Resource Access Table (RAT)
The Resource Access T able (RAT) is used to map a resource name specified in the MO:DCA data stream to
information used to find and process the resource on a given system. The following resources can be
processed via a RAT :
• TrueType fonts (TTFs) and OpenType fonts (OTFs); the resource name is a full font name
• Color Management Resources (CMRs); the resource name is a CMR name
• Data objects; the resource name is the object name
Resource Access Table in MO:DCA Environments
The Resource Access T able (RAT) is installed on a given system by an application program. It is updated
whenever new resources that need to be accessed through a RAT are installed on that system, or whenever
such resources are updated, such as when a new version of a resource replaces an existing version. The
installed RAT remains active until it is updated or replaced. If no RAT is active, resources which require a RAT
to be accessed cannot be processed.
The RAT resides in the directory that it represents. There can be multiple RAT s in a system, one for each
directory. The file names in the RAT do not contain path information.
Implementation Notes:
1. In AFP systems, the file name for the various RAT s is hard-coded, as follows:
• TrueType/OpenType Font RAT : IBM_DataObjectFont.rat
• Color Management Resource RAT : AFP_ColorManagementResource.rat
• Data Object RAT : AFP_DataObjectResource.rat
2. Data objects may be installed in AFP resource libraries with or without a Data Object RAT . Print servers
should maintain the functionality of legacy applications that reference data objects that were not installed
with a RAT . However, if a library does contain a Data Object RAT , the RAT should be searched first to
ensure that the RAT information is used for any object in the library that was installed with the RAT .
Resource Access Table in IPDS Environments
The Resource Access T able is not used at the IPDS level.

## Page 564

532 MO:DCA Reference
Resource Access Table Definition
The table definition consists of a table header followed by zero or more variable-length repeating groups. The
table header specifies information that applies to the whole table including an identifier for the table, the length
of the table, and a table creation/update time stamp. A repeating group consists of a header followed by zero
or more variable-length table vectors. Each repeating group specifies the information needed to access and
process a specific resource. The repeating group content is defined by the resource object type, which is
identified by the resource encoded object-type OID. Repeating groups for a specific resource object type, such
as repeating groups for TTFs or OTFs, have the same syntax. Only a single repeating group is allowed for a
specific resource object. That is, a single resource object may only be defined and indexed once in the RAT .
Repeating groups must be compact. This means that for a table vector that can be repeated, all occurrences of
the vector must specify valid content, that is, the vectors cannot be empty unless there is only one occurrence
of that vector.
Resource Access Table

## Page 565

MO:DCA Reference 533
Resource Access Table Syntax
Offset Type Name Range Meaning M/O
Resource Access Table Header
0–3 UBIN Tlength 18–4,294,967,295 T able length M
4–5 CODE TBLid 1–65,534 T able ID M
6 CODE TBLtpe X'02', X'03', X'04' T able type:
X'02' TTF/OTF Resource Access
T able
X'03' CMR Resource Access
T able
X'04' Data Object Resource
Access T able
M
7–16 CODE UTStmp Universal Date and Time Stamp M
17 CODE InstInf X'00', X'01' Installer information:
X'00' Installer information not
specified; this parameter
ends the table header
X'01' Installer information
specified in bytes 18–57
40 bytes of Installer information that are only specified if InstInf = X'01'; these bytes are optional as a unit.
18–49 CHAR InstNme Name of Installer application O
50 UBIN InstVrs Version number of Installer
application
O
51 UBIN InstRel Release level of Installer application O
52 UBIN InstMod Modification level of Installer
application
O
53 UBIN InstSrv Service level of Installer application O
54–57 Reserved; should be zero O
Zero or more variable-length repeating groups
Offset Type Name Range Meaning M/O
Repeating Group Structure
0–1 UBIN RGLngth 22–65,535 Repeating group length M
2 Reserved; should be zero M
3 CODE RGTpe X'10' Repeating group type:
X'10' Resource access table
repeating group
All
others
Reserved
M
4–5 BITS RGFlgs Repeating group flags; semantics
defined by resource object-type
M
6–21 CODE ObjTpe Encoded object-type OID for
resource being accessed
M
Zero or more variable-length table vectors in fixed order. The table vector semantics and their order in the
repeating group are defined by the resource object type
Resource Access Table

## Page 566

534 MO:DCA Reference
Offset Type Name Range Meaning M/O
Table Vector Structure
0 UBIN TVLngth 2–252 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid T able vector identifier M
2–251 TVData T able vector data O
Resource Access Table Semantics
TBLlngth Contains the length of the table, including this length field, in bytes.
TBLid Contains the identifier for the table.
TBLtpe Is a code that defines the type of table.
Value Description
X'02' TrueType/OpenType Font (TTF/OTF) Resource Access T able. The table
specifies information needed to access and process a TTF/OTF resource.
X'03' Color Management Resource (CMR) Resource Access T able. The table
specifies information needed to access and process a CMR.
X'04' Data Object (DO) Resource Access T able. The table specifies information
needed to access and process a data object that is referenced in the data
stream as a resource object.
UTStmp Contains the time stamp that specifies when the table was created or when it was last
updated. The time stamp is specified with 10 bytes using the syntax specified in bytes 3-12 of
the Universal Date and Time Stamp (X'72') triplet, see “Universal Date and Time Stamp Triplet
X'72'” on page 418.
InstInf Is a code that defines whether the table header contains information about the Installer
application that generated this RAT .
Value Description
X'00' No additional Installer information is specified. This parameter terminates the
table header. No additional RAT header bytes are allowed and will cause a
RAT processing error if specified.
X'01' 40 additional bytes of Installer information are specified in bytes 18 - 57 of the
RAT header.
InstNme Is a character string that identifies the Installer application, encoded in UTF-16BE. The name
is left-justified and padded with blanks (space character = X'0020').
Architecture Note: The InfoPrint Font Installer Application is identified as “IBM FI”. The
InfoPrint Resource Installer Application is identified as “IBM RI”.
InstVrs Version number of the Installer application. For example, version 1 is identified with InstVrs =
X'01'.
InstRel Release level of the Installer application. For example, release level 2 is identified with InstRel
= X'02'.
InstMod Modification level of the Installer application. For example, modification level 3 is identified
with InstMod = X'03'.
InstSrv Service level of the Installer application. For example, service level 4 is identified with InstSrv
= X'04'.
Resource Access Table

## Page 567

MO:DCA Reference 535
RGlngth Contains the length of the repeating group, including this length field, in bytes.
RGtpe Is a code that defines the type of repeating group.
Value Description
X'10' Resource Access T able repeating group. The repeating group specifies
information needed to access and process a resource.
RGFlgs Specifies processing flags for the resource. The flag semantics are defined by the resource
object type.
ObjTpe Specifies the encoded object-type OID for the resource that is accessed and processed with
this repeating group. The encoded object-type OID for resource objects supported in MO:DCA
environments is registered in “Object Type Identifiers” on page 609. The OID is left-justified
and padded with zeros. For example, the encoded object-type OID for TrueType font objects is
X'06072B120004010133'. This OID is specified in the ObjTpe parameter as
X'06072B12000401013300000000000000'. The encoded object-type OID for CMRs is
X'06072B120004010139'. This OID is specified in the ObjTpe parameter as
X'06072B12000401013900000000000000'. The encoded object-type OIDs for data objects
installed using the Data Object Resource Access T able are summarized in T able 44 on page
543.
Resource Access Table Exception Condition Summary
An exception condition exists when the following is detected:
• The RAT header does not specify a valid TBLtpe parameter value
• A RAT repeating group header does not specify RGTpe = X'10'
• The ObjTpe parameter does not specify a supported encoded object-type OID
• The table contains invalid data
Repeating Group Definition for TrueType and OpenType Font Resources
TrueType and OpenType font resources are identified by encoded object-type OID = X'06072B120004010133'.
They are referenced in the MO:DCA data stream using Map Data Resource (MDR) structured fields. They can
also be referenced from a Begin Resource (BRS) structured field. The reference specifies a full font name that
is also specified by the font manufacturer in the font naming table. The full font name in the font may be
specified in multiple languages; the supported encoding is UTF-16. The full font name from the font reference
is used to index the RAT repeating groups, which specify the full font name of a TrueType/OpenType font in all
supported languages using the UTF-16 encoding. Within a repeating group the full font names in all languages
must be sorted so that the UTF-16 code point sequences for the names are in ascending numerical order. The
repeating groups are then sorted so that the UTF-16 code point sequences for the first full font names in each
repeating group are in ascending order. Note that in MO:DCA environments, all UTF-16 data is considered to
be in big-endian format (UTF-16BE).
Resource Access Table

## Page 568

536 MO:DCA Reference
Repeating Group Flag Definitions for TrueType and OpenType Font
Resources
Following are the flag definitions for TrueType and OpenType font resources.
RGFlgs Provide additional information for accessing and processing the TrueType/OpenType font
resource. RGFlgs bits have the following descriptions:
Bit Description
0 TrueType Collection (TTC)
B'0' The font is not packaged in a TTC. If this bit is set to B'0', the TTF/TTC File
Name vector (TVid = X'04') references a TrueType/OpenType font (TTF/
OTF), and the TTF/TTC Object OID vector (TVid = X'08'), if not empty,
specifies an object OID for the font. The TTC Font Index vector (TVid = X'1A')
should be empty and is ignored.
B'1' The font is packaged in a TTC. If this bit is set to B'1', the TTF/TTC File Name
vector (TVid = X'04') references a TrueType Collection (TTC), and the TTF/
TTC Object OID vector (TVid = X'08'), if not empty, specifies an object OID for
the collection. The TTC Font Index vector (TVid = X'1A') must specify a valid
index, and the collection must contain and index a version of the referenced
font that is logically equivalent to the font.
1 Linked Fonts
B'0' The font does not have any linked fonts. If this bit is set to B'0', the Linked
TTF/OTF Full Font Name vector (TVid = X'24') should be empty and is
ignored.
B'1' The font has linked fonts. A linked font is a complete TTF/OTF that is
processed as a logical extension of the base font. If this bit is set to B'1', the
Linked TTF/OTF Full Font Name vector (TVid = X'24') and any additional
Linked TTF/OTF Full Font Name vectors must specify valid full font names for
TTFs/OTFs. Note that linked fonts can be packaged in a TTC. Note also that
only one level of linking is supported. That is, if a linked font specifies its own
linked fonts, these “secondary” linked fonts are not processed as linked fonts
for the original base font.
2 Private
B'0' The installer considers this font or the TTC that contains this font to be a
public resource. A public resource is a candidate for resource capture by a
printer. A public resource may also be resident in the printer, and this version
can be used if the object OID matches the object OID associated with the
resource reference.
B'1' The installer considers this font or the TTC that contains this font to be a
private resource. A private resource is not a candidate for resource capture
by printers. A private resource is always downloaded to the printer; if an
object OID has been generated for the resource, it is ignored.
3 Embed
B'0' The installer does not allow this font or the TTC that contains this font to be
embedded inline into a print file level resource group.
B'1' The installer allows this font or the TTC that contains this font to be
embedded inline into a print file level resource group.
4 Capture
B'0' The installer does not allow this font or the TTC that contains this font to be
captured.
B'1' The installer allows this font or the TTC that contains this font to be captured.
A number of requirements must be met before the presentation system will
actually let resource capture take place:
Resource Access Table

## Page 569

MO:DCA Reference 537
• The font or collection must be identified as “public” (RGFlgs bit 2 set to B'0')
by the installer
• The font or collection must have an object OID associated with it
• The font or collection must be in a location that the presentation system
considers secure
5–15 Reserved; all bits must be B'0'.
Architecture Note:
1. The setting of RGFlgs bits 2-4 reflect not only the intent of the person running the install process, but also
the processing of the font permission bits (fsType parameter in the OS/2 T able of the TTF file) by the install
program. For example, if RGFlgs bit 2 = B'0' (font is public), this means (i) the intent of the person running
the install process is to install the font as a public font, and (ii) the font permission bits allow the font to be
treated as a public font.
2. If the RAT repeating group maps a full font name to the file name of a collection, the installer needs to
ensure that RGFlgs bits 2-4 apply to all fonts in the collection. For example, if RGFlgs bit 4 = B'1' (capture
allowed), then this needs to reflect all fonts in the collection, since the complete collection may end up
being captured.
Table Vector Definitions for TrueType and OpenType Font Resources
Following are the table vectors defined for TrueType and OpenType font resources. The table vectors must
appear in the order shown. Unless indicated otherwise, each table vector must occur once, regardless of
whether its data parameter is specified or not. If a table vector contains no data, its length must be set to X'02'
to indicate that the table vector data is not specified. This is also referred to as an empty table vector. T able
vectors within a RAT repeating group must be compact. This means that for a table vector that can be
repeated, all occurrences of the vector must specify valid content, that is, the vectors cannot be empty unless
there is only one occurrence of that vector.
Offset Type Name Range Meaning M/O
TrueType/OpenType Font (TTF/OTF) Full Font Name; table vector may be repeated to specify the full font name
in all supported languages
0 UBIN TVLngth 4–252; even values
only
T able vector length M
1 CODE TVid X'01' T able vector identifier M
2–251 CHAR FFName Full font name of the base font. This
parameter must be specified.
M
TrueType/OpenType Font or TrueType/OpenType Collection (TTC) File Name; table vector must be specified only
once
0 UBIN TVLngth 4–252; even values
only
T able vector length M
1 CODE TVid X'04' T able vector identifier M
Resource Access Table

## Page 570

538 MO:DCA Reference
Offset Type Name Range Meaning M/O
2–251 CHAR FileNme File name with which the font or the
collection that contains the font has
been stored in the presentation
system's resource library. RGFlgs bit
0 = B'0' indicates that the file name
references a TrueType/OpenType
font (TTF/OTF). RGFlgs bit 0 = B'1'
indicates that the file name
references a TrueType Collection
(TTC). The file name does not
include path information. This
parameter must be specified.
M
TrueType/OpenType Font or TrueType/OpenType Collection Object OID; table vector must be specified only
once
0 UBIN TVLngth 2–131 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'08' T able vector identifier M
2–130 CODE ObjOID The object OID that is assigned to
the font or to the collection that
contains the font. RGFlgs bit 0 = B'0'
indicates that the object OID is
associated with a TrueType/
OpenType font (TTF/OTF). RGFlgs
bit 0 = B'1' indicates that the object
OID is associated with a TrueType
Collection (TTC). The length of this
parameter must reflect the length of
the actual OID; padding bytes are
not allowed. The object OID enables
the font or the collection to be
captured and made resident in the
printer.
O
TrueType/OpenType Collection Font Index; table vector must be specified only once
0 UBIN TVLngth 2, 4 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'1A' T able vector identifier M
2–3 UBIN FntIndx The index used to locate the TTF/
OTF in the TTC. This is an index into
the array of offsets that comprise the
4th parameter in the TTC Header.
Each offset points to the directory of
a specific TTF/OTF in the TTC. An
index value of X'0000' selects the
first offset, a value of X'0001' selects
the second offset, a value of (n-1)
selects the nth offset. This index
must be specified if RGFlgs bit 0 =
B'1'. This vector should be empty
and is ignored if RGFlgs bit 0 = B'0'.
O
Linked TrueType/OpenType Font Full Font Name; table vector must be specified at least once and may be
repeated to specify multiple linked fonts
Resource Access Table

## Page 571

MO:DCA Reference 539
Offset Type Name Range Meaning M/O
0 UBIN TVLngth 2–252; even values
only
T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'24' T able vector identifier M
2–251 CHAR LFFName Full font name of the linked font. This
parameter must be specified if
RGFlgs bit 1 = B'1'.
O
Language Code Information for Full Font Names; table vector is optional and may be specified once
0 UBIN TVLngth 2–252; even values
only
T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'30' T able vector identifier M
2–251 CODE LCIDs An ordered sequence of two-byte
Language Code IDs (LCIDs) that
correspond in one-to-one fashion to
the ordered sequence of full font
name table vectors (TV ID = X'01') in
this repeating group.
OTable Notes:
1. All character data in the table vectors is encoded in UTF-16BE. This encoding is characterized by the
following parameters:
Encoding scheme ID - as carried in the Encoding Scheme ID (X'50') triplet: X'7200'
CCSID - as carried in the Coded Graphic Character Set Global Identifier (X'01') triplet (CCSID form):
1200 (X'04B0').
Note that in MO:DCA environments, all UTF-16 data is considered to be in big-endian format (UTF-16BE).
2. If multiple TrueType/OpenType Font (TTF/OTF) Full Font Name table vectors are specified, each vector
must specify a valid full font name.
3. If multiple Linked TrueType/OpenType Font (TTF/OTF) Full Font Name table vectors are specified, each
vector must specify a valid full font name.
4. The order in which multiple Linked TrueType/OpenType Font (TTF/OTF) Full Font Name table vectors are
specified in the repeating group determines the order in which the linked fonts are processed by the
presentation system:
• The base font is processed first, followed by the first linked font in the repeating group, followed by the
next linked font in the repeating group, and so on; the last linked font in the repeating group is processed
last.
• If an external (print file level) resource group is specified for the print file, this resource group is searched
first for a specified linked font. If the specified linked font is not found in the resource group, the RAT is
accessed to locate the linked font in a library. Note that linked fonts can be packaged in a TTC.
• Only one level of linking is supported. That is, if a linked font specifies its own linked fonts, these
“secondary” linked fonts are not processed as linked fonts for the original base font.
5. A specific linked font should only be specified once in a given repeating group.
6. LCIDs specify language and locale information for a character string that specifies a full font name and are
defined in the TrueType Font Files T echnical Specification available on the Microsoft web site. Examples of
LCIDs are X'0409': Primary Language = English, Locale Name = American; X'0807': Primary Language =
German, Locale Name = Swiss. A given LCID applies to the full font name that is in the same ordered
Resource Access Table

## Page 572

540 MO:DCA Reference
position in the repeating group. The first LCID applies to the first name, the second LCID applies to the
second name, and so on. The total number of LCIDs should match the total number of full font names. For
example, if the RAT RG for a given font contains two full font names, the first in English-US and the second
in German-Switzerland, table vector X'30' could optionally be specified once with data = X'04090807'.
7. When TrueType/OpenType fonts are installed in a resource library, they must not be wrapped with a
MO:DCA object envelope such as BOC/EOC, that is, they must be installed in their raw source format.
8. The minimum length of a TTF/OTF font OID or of a TTF/OTF font collection OID, assuming that the MD5
checksum is a value less than X'7F' preceded by all zeros and can therefore be represented by 1 byte, has
been calculated to be 13 bytes. The maximum length is 129 bytes.
Repeating Group Definition for Color Management Resources (CMRs)
CMRs are identified by encoded object-type OID = X'06072B120004010139'. They are referenced in the
MO:DCA data stream using Map Data Resource (MDR), Include Object (IOB), and Preprocess Presentation
Object (PPO) structured fields. They can also be referenced from a Begin Resource (BRS) structured field,
and from a Data Object RAT . The reference specifies a CMR name that is also specified by the CMR generator
in the CMR header. The encoding of the CMR name in the CMR header and in the CMR RAT entry is UTF-
16BE. The CMR name from the CMR reference is used to index the RAT repeating groups, which specify CMR
names using the UTF-16BE encoding. Repeating groups are sorted so that the UTF-16BE code point
sequences for the CMR names are in ascending numerical order. Note that in MO:DCA environments, all UTF-
16 data is considered to be in big-endian format (UTF-16BE).
Repeating Group Flag Definitions for Color Management Resources
Following are the flag definitions for CMRs.
RGFlgs Provide additional information for accessing and processing the CMR. RGFlgs bits have the
following descriptions:
Bit Description
0 Reserved; must be B'0'.
1 Mapped CMRs.
B'0' There are no Link LK CMRs or device-specific CMRs in this repeating group
that are mapped to the referenced CMR. The Mapped Device CMR TV (TVid
= X'24') should be empty and is ignored.
B'1' The repeating group contains Link LK CMRs or device-specific CMRs that are
mapped to the referenced CMR. If this bit is set to B'1', the Mapped Device
CMR TV (TVid = X'24') and any additional Mapped Device CMR TVs must
specify valid CMR names.
2 Private
B'0' The installer considers this CMR to be a public resource. A public resource is
a candidate for resource capture by a printer. A public resource may also be
resident in the printer, and this version can be used if the object OID matches
the object OID associated with the resource reference.
B'1' The installer considers this CMR to be a private resource. A private resource
is not a candidate for resource capture by printers. A private resource is
always downloaded to the printer.
3 Embed
B'0' The installer does not allow this CMR to be embedded inline into a print file
level resource group.
B'1' The installer allows this CMR to be embedded inline into a print file level
resource group.
Resource Access Table

## Page 573

MO:DCA Reference 541
4 Capture
B'0' The installer does not allow this CMR to be captured.
B'1' The installer allows this CMR to be captured. A number of requirements must
be met before the presentation system will actually let resource capture take
place:
• The CMR must be identified as “public” (RGFlgs bit 2 set to B'0') by the
installer
• The CMR must have an object OID associated with it
• The CMR must be in a location that the presentation system considers
secure
5 Copied/extracted Profile
B'0' The referenced CMR is not a Color Conversion CMR that was generated from
an ICC profile that was copied or extracted from a data object.
B'1' The referenced CMR is a Color Conversion CMR that was generated from an
ICC profile that was copied or extracted from a data object.
6 Reserved; must be B'0'.
7 CMR normal use Indicator - Audit or Instruction
B'0' The referenced CMR is normally intended to be used as an instruction CMR.
If the CMR is a Color Conversion CMR, this setting allows a CMR Installer to
generate Link LK CMRs that link the referenced CMR to all Color Conversion
CMRs that are normally intended to be used as audit CMRs.
B'1' The referenced CMR is normally intended to be used as an audit CMR. If the
CMR is a Color Conversion CMR, this setting allows a CMR Installer to
generate Link LK CMRs that link the referenced CMR to all Color Conversion
CMRs that are normally intended to be used as instruction CMRs.
8 CMR normal use Indicator - Audit and Instruction
B'0' RGFlgs bit 7 is to used to determine how the referenced CMR is normally
intended to be used.
B'1' RGFlgs bit 7 is ignored. The referenced CMR is normally intended to be used
as both an audit CMR and an instruction CMR. If the CMR is a Color
Conversion (CC) CMR, this setting allows the installer to generate Link LK
CMRs between the referenced CMR and all CC CMRs that are normally
intended to be used as either audit, instruction, or both audit and instruction
CMRs. That is, an installer can generate the following Link LK CMRs:
• From the referenced CMR to each CC CMR that is intended to be used as
an instruction CMR and map these Link LK CMRs to the referenced CMR.
• From each CC CMR that is intended to be used as an audit CMR to the
referenced CMR and map each Link LK CMR to the audit CMR.
• From the referenced CMR to each CC CMR that is intended to be used as
both an audit and an instruction CMR and map these Link LK CMRs to the
referenced CMR.
• From each CC CMR that is intended to be used as an audit and an
instruction CMR to the referenced CMR and map each Link LK CMR to the
audit/instruction CMR.
9–15 Reserved; all bits must be B'0'.
Table Vector Definitions for Color Management Resources
Following are the table vectors defined for CMRs. The table vectors must appear in the order shown. Unless
indicated otherwise, each table vector must occur once, regardless of whether its data parameter is specified
or not. If a table vector contains no data, its length must be set to X'02' to indicate that the table vector data is
Resource Access Table

## Page 574

542 MO:DCA Reference
not specified. This is also referred to as an empty table vector. T able vectors within a RAT repeating group
must be compact. This means that for a table vector that can be repeated, all occurrences of the vector must
specify valid content, that is, the vectors cannot be empty unless there is only one occurrence of that vector.
Offset Type Name Range Meaning M/O
CMR Name; table vector must be specified only once
0 UBIN TVLngth 148 T able vector length M
1 CODE TVid X'01' T able vector identifier M
2–147 CHAR CMRName Name of the CMR. This parameter
must be specified.
M
CMR File Name; table vector must be specified only once
0 UBIN TVLngth 4–252; even values
only
T able vector length M
1 CODE TVid X'04' T able vector identifier M
2–251 CHAR FileNme File name with which the CMR has
been stored in the presentation
system's resource library. The file
name does not include path
information. This parameter must be
specified.
M
CMR Object OID; table vector must be specified only once
0 UBIN TVLngth 12–131 T able vector length M
1 CODE TVid X'08' T able vector identifier M
2–(n-1) CODE ObjOID The object OID that is assigned to
the CMR. The length of this
parameter must reflect the length of
the actual OID; padding bytes are
not allowed. The object OID enables
the CMR to be captured and made
resident in the printer. For CC
CMRs, the object OID also allows
the printer to search for Link LK
CMRs.
M
Mapped CMR Name; table vector must be specified at least once and may be repeated to specify multiple
mapped CMRs
0 UBIN TVLngth 2,
148
T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'24' T able vector identifier M
2–147 CHAR CMRName Name of the mapped device-specific
CMR. This parameter must be
specified if RGFlgs bit 1 = B'1'.
O
ICC Profile OID; table vector is optional and may be specified once for a CC CMR; ignored if specified for other
CMR types
0 UBIN TVLngth 2, 12–131 T able vector length; a length of 2
indicates that the table vector data is
not specified
M
Resource Access Table

## Page 575

MO:DCA Reference 543
Offset Type Name Range Meaning M/O
1 CODE TVid X'18' T able vector identifier M
2–(n-1) CODE ObjOID The object OID for the ICC profile
that is carried by this CC CMR. The
length of this parameter must reflect
the length of the actual OID; padding
bytes are not allowed. The object
OID enables the unique identification
of ICC profiles in CC CMRs.
O
Table Notes:
1. All character data in the table vectors is encoded in UTF-16BE. This encoding is characterized by the
following parameters:
Encoding scheme ID - as carried in the Encoding Scheme ID (X'50') triplet: X'7200'
CCSID - as carried in the Coded Graphic Character Set Global Identifier (X'01') triplet (CCSID form):
1200 (X'04B0')
Note that in MO:DCA environments, all UTF-16 data is considered to be in big-endian format (UTF-16BE).
2. The Mapped CMR TV must be specified at least once, and can occur multiple times. If there are no
mapped CMRs, this TV must be specified once as an empty TV (TVLngth = 2). The order in which multiple
Mapped Device CMRs are specified in the repeating group is not significant. This TV is used to:
• map a Link LK CMR to this Color Conversion CMR if it is normally referenced as an audit CMR
• map a device-specific Halftone or T one Transfer Curve CMR to this generic Halftone or T one Transfer
Curve CMR.
3. The minimum length of a CMR object OID, assuming that the MD5 checksum is a value less than X'7F'
preceded by all zeros and can therefore be represented by 1 byte, has been calculated to be 10 bytes. The
maximum length is 129 bytes.
4. See the Color Management Object Content Architecture Reference for a definition of the CMR header and
the CMR name syntax.
5. When CMRs are installed in a resource library, they must not be wrapped with a MO:DCA object envelope
such as BOC/EOC, that is, they must be installed in their raw source format.
6. Link LK CMRs and Link DL CMRs are distinguished in the CMR RAT by the “LK” and “DL” Link CMR sub-
type designations in the CMRType field of the CMR name; this drives the different processing that is
associated with each Link CMR sub-type.
Repeating Group Definition for Data Object Resources
The following data objects can be processed with this RAT repeating group type:
Table 44. Data Object Resources Processed with RAT RG
Component ID Object Type Encoded Object-type OID
05 IOCA FS10 X'06072B120004010105'
11 IOCA FS11 X'06072B12000401010B'
12 IOCA FS45 X'06072B12000401010C'
13 EPS X'06072B12000401010D'
14 TIFF X'06072B12000401010E'
Resource Access Table

## Page 576

544 MO:DCA Reference
Table 44 Data Object Resources Processed with RAT RG (cont'd.)
Component ID Object Type Encoded Object-type OID
22 GIF X'06072B120004010116'
23 AFPC JPEG
Note: This object type was formerly referred to
as JFIF (JPEG).
X'06072B120004010117'
25 PDF Single-page Object X'06072B120004010119'
34 PCL Page Object X'06072B120004010122'
45 IOCA FS42 X'06072B12000401012D'
48 EPS with Transparency X'06072B120004010130'
49 PDF with Transparency X'06072B120004010131'
55 IOCA FS40 X'06072B120004010137'
58 JPEG2000 (JP2) X'06072B12000401013A'
60 TIFF without Transparency X'06072B12000401013C'
61 TIFF Multiple Image File X'06072B12000401013D'
62 TIFF Multiple Image - without Transparency -
File
X'06072B12000401013E'
63 PDF Multiple Page File X'06072B12000401013F'
64 PDF Multiple Page - with Transparency - File X'06072B120004010140'
65 AFPC PNG Subset X'06072B120004010141'
66 AFPC TIFF Subset X'06072B120004010142'
68 AFPC SVG Subset X'06072B120004010144'
70 IOCA FS48 X'06072B120004010146'
71 IOCA FS14 X'06072B120004010147'
These data object resources are referenced in the MO:DCA data stream using Map Data Resource (MDR),
Include Object (IOB), and Preprocess Presentation Object (PPO) structured fields. The data object name from
the reference is used to index the RAT repeating groups, which specify data object names using the UTF-16BE
encoding. Repeating groups are sorted so that the UTF-16BE code point sequences for the data object names
are in ascending numerical order. Note that in MO:DCA environments, all UTF-16 data is considered to be in
big-endian format (UTF-16BE).
Repeating Group Flag Definitions for Data Object Resources
Following are the flag definitions for data object resources.
RGFlgs Provide additional information for accessing and processing the data object resource. RGFlgs
bits have the following descriptions:
Bit Description
0 Reserved; must be B'0'.
1 Color Management Resources (CMRs).
B'0' There are no CMRs that are to be associated with the referenced data object.
The CMR Name TV (TVid = X'24') and the CMR Descriptor TV (TVid = X'28')
should be empty and are ignored.
B'1' The repeating group specifies CMRs that are to be associated with the
referenced data object. If this bit is set to B'1', the TV pairs consisting of a
Resource Access Table

## Page 577

MO:DCA Reference 545
CMR Name TV (TVid = X'24') and a CMR Descriptor TV (TVid = X'28') must
specify a valid CMR name and a valid CMR processing mode.
2 Private
B'0' The installer considers this data object resource to be a public resource. A
public resource is a candidate for resource capture by a printer. A public
resource may also be resident in the printer, and this version can be used if
the object OID matches the object OID associated with the resource
reference.
B'1' The installer considers this data object resource to be a private resource. A
private resource is not a candidate for resource capture by printers. A private
resource is always downloaded to the printer.
3 Embed
B'0' The installer does not allow this data object resource to be embedded inline
into a print file level resource group.
B'1' The installer allows this data object resource to be embedded inline into a
print file level resource group.
4 Capture
B'0' The installer does not allow this data object resource to be captured.
B'1' The installer allows this data object resource to be captured. A number of
requirements must be met before the presentation system will actually let
resource capture take place:
• The data object resource must be identified as “public” (RGFlgs bit 2 set to
B'0') by the installer
• The data object resource must have an object OID associated with it
• The data object resource must be in a location that the presentation system
considers secure
5 Compacted Object
B'0' A compacted object has not been generated from the data object. If this bit is
set to B'0', the TV pair consisting of a Compacted Object File Name TV (TVid
= X'14') and a Compacted Object OID TV (TVid = X'18') should be empty and
are ignored.
B'1' A compacted object has been generated by extracting the embedded ICC
profile from the referenced data object. If this bit is set to B'1', the TV pair
consisting of a Compacted Object File Name TV (TVid = X'14') and a
Compacted Object OID TV (TVid = X'18') must not be empty and must specify
valid data.
Implementation Note: T o differentiate the file name of the compacted object
from the file name of the referenced object, it is recommended that the
file name of the compacted object, encoded in UTF-16BE, be formed
by prepending the file name of the referenced data object with the
character string “iccr_”. For example, if the file name of the referenced
object is “image.jpeg”, the file name of the compacted object would be
“iccr_image.jpeg”.
6–15 Reserved; all bits must be B'0'.
Table Vector Definitions for Data Object Resources
Following are the table vectors defined for data object resources. The table vectors must appear in the order
shown. Unless indicated otherwise, each table vector must occur once, regardless of whether its data
parameter is specified or not. If a table vector contains no data, its length must be set to X'02' to indicate that
the table vector data is not specified. This is also referred to as an empty table vector. T able vectors within a
RAT repeating group must be compact. This means that for a table vector that can be repeated, all
Resource Access Table

## Page 578

546 MO:DCA Reference
occurrences of the vector must specify valid content, that is, the vectors cannot be empty unless there is only
one occurrence of that vector.
Offset Type Name Range Meaning M/O
Data Object Resource Name; table vector must be specified only once
0 UBIN TVLngth 4–252; even values
only
T able vector length M
1 CODE TVid X'01' T able vector identifier M
2–251 CHAR DORName Name of the data object resource.
This parameter must be specified.
M
Data Object Resource File Name; table vector must be specified only once
0 UBIN TVLngth 4–252; even values
only
T able vector length M
1 CODE TVid X'04' T able vector identifier M
2–251 CHAR FileNme File name with which the data object
resource has been stored in the
presentation system's resource
library. The file name does not
include path information. This
parameter must be specified.
M
Data Object Resource Object OID; table vector must be specified only once
0 UBIN TVLngth 2–131 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'08' T able vector identifier M
2–(n-1) CODE ObjOID The object OID that is assigned to
the data object resource. The length
of this parameter must reflect the
length of the actual OID; padding
bytes are not allowed. The object
OID enables the data object
resource to be captured and made
resident in the printer.
O
Compacted Object File Name; table vector must be specified only once
0 UBIN TVLngth 2–252; even values
only;
T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'14' T able vector identifier M
Resource Access Table

## Page 579

MO:DCA Reference 547
Offset Type Name Range Meaning M/O
2–251 CHAR FileNme File name with which the compacted
object has been stored in the
presentation system's resource
library. The file name does not
include path information. This
parameter is optional and is ignored
if RGFlgs bit 5 = B'0'. This parameter
must be specified if RGFlgs bit 5 =
B'1'.
Implementation Note: It is
recommended that the file name
of the compacted object, encoded
in UTF-16BE, be formed by
prepending the file name of the
referenced data object with the
character string “iccr_”.
O
Compacted Object OID; table vector must be specified only once
0 UBIN TVLngth 2–131 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'18' T able vector identifier M
2–(n-1) CODE ObjOID The object OID that is assigned to
the compacted object. The length of
this parameter must reflect the
length of the actual OID; padding
bytes are not allowed. The object
OID enables the compacted object
to be captured and made resident in
the printer. This parameter is
optional and is ignored if RGFlgs bit
5 = B'0'.
O
Data Object Rendering Intent; table vector must be specified only once
0 UBIN TVLngth 2, 10 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'1C' T able vector identifier M
2–3 Reserved; should be zero O
4 CODE IOCARI X'00'-X'03', X'FF' Rendering intent for IOCA objects:
X'00' perceptual
X'01' media-relative colorimetric
X'02' saturation
X'03' ICC-absolute colorimetric
X'FF' not specified
O
5 CODE OCARI X'00'-X'03', X'FF' Rendering intent for container (non-
OCA) objects; code definitions same
as for IOCARI
O
6–7 Reserved; should be zero O
8–9 Reserved; should be zero O
CMR Name; table vector must be specified at least once and must be followed by a CMR Descriptor TV (TVid =
X'28'); the TV pair may be repeated to specify multiple {CMR name + CMR processing mode} combinations
Resource Access Table

## Page 580

548 MO:DCA Reference
Offset Type Name Range Meaning M/O
0 UBIN TVLngth 2,
148
T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'24' T able vector identifier M
2–147 CHAR CMRName Name of the CMR. This parameter
must be specified if RGFlgs bit 1 =
B'1'.
O
CMR Descriptor; table vector must be specified at least once and must follow the CMR Name TV (TVid = X'24');
the TV pair may be repeated to specify multiple {CMR name + CMR processing mode} combinations
0 UBIN TVLngth 2, 4 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'28' T able vector identifier M
2 CODE ProcMode X'01', X'02' CMR processing mode. This
parameter must be specified if
RGFlgs bit 1 = B'1'.
Value Meaning
X'01' process as audit CMR
X'02' process as instruction CMR
O
3 Reserved; should be zero. This
parameter must be specified if
RGFlgs bit 1 = B'1'.
O
Image Resolution; table vector is optional and may be specified once for non-IOCA raster image objects;
ignored if specified for other objects
0 UBIN TVLngth 2, 10 T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'30' T able vector identifier M
2–3 Reserved; should be zero O
4 CODE XBase X'00'-X'01' Unit base for image resolution in the
X direction:
X'00' 10 inches
X'01' 10 centimeters
O
5 CODE YBase X'00'-X'01' Unit base for image resolution in the
Y direction; must be the same as
XBase:
X'00' 10 inches
X'01' 10 centimeters
O
6-7 UBIN XResol 1-32,767 Number of image points in X
direction per X unit base
O
8-9 UBIN YResol 1-32,767 Number of image points in Y
direction per Y unit base
O
Object Container Presentation Space Size; table vector is optional and may be specified once for PDF and SVG
presentation container objects; ignored if specified for other object types; bytes 5–16 are optional as a unit;
either all are specified or none are specified
0 UBIN TVLngth 2, 5, 17 T able vector length; a length of 2
indicates the table vector data is not
specified
M
Resource Access Table

## Page 581

MO:DCA Reference 549
Offset Type Name Range Meaning M/O
1 CODE TVid X'32' T able vector identifier M
2–3 Reserved; should be zero O
4 CODE PDFSize X'01'-X'05' Parameter used to determine the
PDF presentation space size:
X'01' MediaBox
X'02' CropBox
X'03' BleedBox
X'04' TrimBox
X'05' ArtBox
O
5 CODE XocBase X'00'–X'01' Presentation space size unit base for
the width (along X axis):
X'00' 10 inches
X'01' 10 centimeters
O
6 CODE YocBase X'00'–X'01' Presentation space size unit base for
the height (along Y axis):
X'00' 10 inches
X'01' 10 centimeters
O
7–8 UBIN XocUnits 1–32,767 Presentation space size units per
unit base for the width (along X axis)
O
9–10 UBIN YocUnits 1–32,767 Presentation space size units per
unit base for the height (along Y
axis)
O
11–13 UBIN XpsSize 1–32,767 Presentation space width (extent
along X axis)
O
14–16 UBIN YpsSize 1–32,767 Presentation space height (extent
along Y axis)
O
Color Specification; table vector is optional and may be specified once for IOCA bilevel image or non-OCA
bilevel and grayscale image; ignored if specified for other object types
0 UBIN TVLngth 2, n T able vector length; a length of 2
indicates the table vector data is not
specified
M
1 CODE TVid X'34' T able vector identifier M
2 Reserved; should be zero O
3 CODE ColSpce X'01', X'04', X'06',
X'08', X'40'
Color space:
X'01' RGB
X'04' CMYK
X'06' Highlight color space
X'08' CIELAB
X'40' Standard OCA color space
O
4-7 Reserved; should be zero O
8 UBIN ColSize1 X'01'–X'08', X'10' Number of bits in component 1; see
color space definitions
O
9 UBIN ColSize2 X'00'–X'08' Number of bits in component 2; see
color space definitions
O
10 UBIN ColSize3 X'00'–X'08' Number of bits in component 3; see
color space definitions
O
Resource Access Table

## Page 582

550 MO:DCA Reference
Offset Type Name Range Meaning M/O
11 UBIN ColSize4 X'00'–X'08' Number of bits in component 4; see
color space definitions
O
12–n Color Color specification O
Table Notes:
1. All character data in the table vectors is encoded in UTF-16BE. This encoding is characterized by the
following parameters:
Encoding scheme ID - as carried in the Encoding Scheme ID (X'50') triplet: X'7200'
CCSID - as carried in the Coded Graphic Character Set Global Identifier (X'01') triplet (CCSID form):
1200 (X'04B0').
Note that in MO:DCA environments, all UTF-16 data is considered to be in big-endian format (UTF-16BE).
2. When non-OCA objects such as EPS, PDF , GIF , TIFF , JFIF are installed in a resource library, they must not
be wrapped with a MO:DCA object envelope such as BOC/EOC, that is, they must be installed in their raw
source format.
3. The data content (bytes 2 - 9) of the Data Object Rendering Intent TV (TVid = X'1C') is optional as a unit;
that is bytes 2 - 9 are either all specified or none are specified.
4. The rendering intent specified in the Data Object Rendering Intent TV overrides the rendering intent
specified in the OEG of the data object, and any rendering intent information embedded in the data object.
The rendering intent specified in this table vector is downloaded to the presentation device but may not be
used if a Link DL CMR is associated with the data object; in that case the rendering intent specified in the
Link DL CMR is used to render the object
5. CMRs that are mapped to a data object in the RAT become secondary resources of that data object and
override any conflicting CMRs specified in the OEG of the data object. In order for these secondary
resources to be processed, the data object must itself be mapped as a resource in the AEG of the page or
overlay that includes the data object. This allows the print server to process the data object RAT entry while
processing the AEG and thereby ensure that secondary resources, such as mapped CMRs, are
downloaded to the presentation device before the device enters the page-build state. Data objects that are
mapped as resources before being included on a page or overlay are sometimes called hard objects. Data
objects that are not mapped as resources before being included on a page or overlay are sometimes called
soft objects. Therefore, using that terminology, CMRs that are mapped to a data object in the RAT will only
be processed for hard objects.
6. The minimum length of a data object OID, assuming that the MD5 checksum is a value less than X'7F'
preceded by all zeros and can therefore be represented by 1 byte, has been calculated to be 10 bytes. The
maximum length is 129 bytes.
7. The resolution specified in the Image Resolution TV overrides any raster image resolution specified on the
CDD in the OEG of the image object or inside the image.
8. The size specified in the Object Container Presentation Space Size TV overrides any presentation space
size specified on the CDD in the OEG of the container object.
9. The Object Container Presentation Space Size TV may be specified for PDF object types and for the
AFPC SVG Subset object type. For PDF , if this TV is not specified and if the Object Container Presentation
Space Size (X'9C') triplet is not specified for the object, the architected default is X'01' - MediaBox, which is
a mandatory parameter in PDF . If the Object Container Presentation Space Size TV or the Object
Container Presentation Space Size (X'9C') triplet is specified, but the selected size parameter is not
specified in the PDF object, the PDF default mechanism is used to select the presentation space size. For
SVG, this TV specifies the size of the SVG presentation space. Bytes 5–16 are optional as a unit; either all
are specified or none are specified.
Resource Access Table

## Page 583

MO:DCA Reference 551
10. The definition of bytes 2-n in the Color Specification (ID X'34') table vector matches the definition for the
corresponding bytes in the Color Specification (X'4E') triplet. These bytes are optional as a unit, that is,
bytes 2-n are either all specified or none are specified.
11. The Color Specification (ID X'34') table vector specifies the color that is to be used as the default color, or
the initial color, for an image object. This vector only specifies the color for the object presentation space; it
does not affect colors assigned to the object's object area. This table vector may be specified for IOCA
image, in which case it only applies to bilevel image; it is ignored when the image is not bilevel. It may also
be specified for non-OCA image file formats, as defined in the MO:DCA Object Type Registry appendix, in
which case it only applies to bilevel or grayscale image; it is ignored when the object is not a bilevel or
grayscale image. Note that all 1-bit per pixel image objects are considered bilevel. When the image is
grayscale, this table vector specifies the color that is to be grayscaled. The color space selected in the
table vector must be supported in the object’s data descriptor structured field. For example, if the table
vector specifies a default color using ColSpce =X'08' - CIELAB, the object’s data descriptor must also
support the CIELAB color space. If ColSpce =X'06' - Highlight color space, the % coverage and % shading
parameters are ignored. If the above conditions are not met, the table vector is ignored.
Resource Access Table

## Page 584

552 MO:DCA Reference

## Page 585

Copyright © AFP Consortium 1990, 2023 553
