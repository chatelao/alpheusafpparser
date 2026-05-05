Chapter 4. Mixed Documents: Adding MO:DCA Structured
Fields to Line Data
Chapter 3, “Using a Page Definition to Print Data” describes how Page Definitions can be used to format
traditional application line data without the need to make any application programming changes. Under certain
circumstances, however, functions are needed that can only be accomplished by changing the application.
These functions can be invoked by using one of a small set of MO:DCA structured fields, any of which can be
intermixed with line data to obtain specific results. A document of this type, in which structured fields are
intermixed with line data, is called a mixed document.
Note: MO:DCA structured fields cannot be combined with XML data.
MO:DCA structured fields cannot be interspersed with line data records indiscriminately. D
ata object structured
fields and resource structured fields can appear only within their respective objects and resources, and only in
the sequence allowed by the architecture
. For example, the Map Coded Font (MCF) structured field is part of
the Active Environment Group that in turn can appear in a presentation page, an overlay, or a Page Definition.
However, it is not permitted to include an MCF between line-mode data records in an output file or to bracket
line-mode records with Begin Page and End Page structured fields. Refer to “Page Definition Structure” on
page 16 for the structure of the Page Definition object and refer to the Mixed Object Document Content
Architecture (MO:DCA) Reference for the structure of other data and resource objects.
This chapter discusses how data and resource objects can be intermixed with line data and provides examples
of structured fields that can be included individually with line-data records. These structured fields are:
• Invoke Data Map
• Invoke Medium Map
• Include Page Segment
• Include Page Overlay
• Include Object
• Presentation T ext
Note: The No Operation (NOP) structured field may appear anywhere in a mixed document and thus is not
listed in the structured field groupings.
This chapter contains coding examples for some of these structured fields. Chapter 5, “Structured Fields in a
Page Definition and in Line Data” contains additional information on the format of these structured fields. See
the Mixed Object Document Content Architecture (MO:DCA) Reference for the formal definition of all MO:DCA
structured fields.
The presence of structured fields in line data does not change the fact that the Page Definition is the controlling
resource that
determines how the data appears on the page. Structured fields other than those that change
Data Maps or Medium Maps do not affect the placement of line-data records, nor do they affect the text
orientation or font selection used to print line-data records. These characteristics of line-data records are
defined in the Page Definition. Only when the application generates fully composed documents is a Page
Definition not used.

## Page 58

40 AFP Programming Guide and Line Data Reference
X'5A' Carriage Control Character
When printing in a z/OS system environment, if MO:DCA structured fields are used either in a fully composed
document or intermixed with line data, each MO:DCA structured field must be prefixed with a X'5A' character.
The X'5A' appears in the first byte position and provides a signal to a presentation services program that the
record is a structured field, not a data record.
The X'5A' character precedes the MO:DCA structured field and is not formally part of the structured field, so it
is not counted in the structured field length value that immediately follows it. The examples in this chapter all
contain a X'5A' character in the first byte position.
In a z/OS system environment, each MO:DCA structured field must occupy one record. The requirement to
prefix MO:DCA structured fields with X'5A' means that all other records in the data set must begin with a
carriage control character, even if it is only a “print-and-space-one-line” carriage control. Either ANSI or
machine code carriage control can be used for these records.
In an AIX environment, the carriage control character is optional. The New Line control, also called Linefeed
(X'25' in EBCDIC, X'0A' in ASCII),
is used to determine end-of-record in AIX. The use of the Linefeed carriage
control to determine end-of-record allows variable-length records to be easily created in AIX environments.
Print File Structure
An AFP print file consists of an optional inline resource group followed by one or more documents. Each
document may, in turn, be preceded by an optional document index. All resources in an inline resource group
must precede all other data in the print file. The group of resources is delimited by the Begin Resource Group
(BRG) and End Resource Group (ERG) structured fields. Each resource object in the group is delimited by the
Begin Resource (BRS
) and End Resource (ERS ) structured fields. If multiple fully composed documents are
present in the print file, they must be delimited by Begin Document (BDT) and End Document (EDT) structured
fields. Note that mixed line-page documents and composed documents can occur in any order following the
inline resource group. Figure 15 on page 41 shows the structure of an AFP print file.
Mixed Documents

## Page 59

AFP Programming Guide and Line Data Reference 41
Figure 15. Structure of a Print File
*  =  optional
s  =  can appear more than once
Note: can be any valid Resource Object, for example, an
Overlay, a Page Segment, a Form Definition.
These items can
be in any order.
EDT
IPG
* s
Presentation
Page
* s
* s
IMMInternal
Medium Map
* s
Resource
Environment
Group
* s
BDT
Repeated for each
Resource Object.
ERG
ERS
Resource
Object
(see note)
BRS
BRG
Mixed Line-Page
Documents
* s
*
Print File
BPF
*
EPF
*
Inline
Resource
Group
Document
* s*
Notes:
1. The BPF/EPF structured fields are optional as a pair; if one is specified, the other must be specified as
well.
2. The mixed line-page documents and composed documents can occur in any order following the inline
resource group.
3. Each AFP (MO:DCA ) document may optionally be preceded by a single document index that is implicitly
tied to the document and that indexes the document. For the formal definition of the MO:DCA document
index, see the Mixed Object Document Content Architecture (MO:DCA) Reference.
4. An AFP (MO:DCA ) document may contain Link Logical Element (LLE) structured fields following the BDT .
It may also group presentation pages into named page groups. MO:DCA page groups may in turn contain
T ag Logical Element (TLE) structured fields following the BNG. These structures do not affect the
presentation of the document. For the formal definition of these structures, see the Mixed Object Document
Content Architecture (MO:DCA) Reference.
5. If a Medium Map is included internal (inline) to the document, it is activated by immediately following it with
an IMM that explicitly invokes it, otherwise the internal Medium Map is ignored. An IMM that does not follow
an internal Medium Map may not invoke an internal Medium Map elsewhere in the document and is
assumed to reference a Medium Map in the current Form Definition.
Mixed Documents

## Page 60

42 AFP Programming Guide and Line Data Reference
The objects that comprise an AFP print file are as follows:
Inline Resource Group
Contains one or more resource objects to be associated with printing this file. See “Inline
Resource Group Structure” on page 43 for a detailed description of the structure of the
resource group and the objects it can contain.
Note: In the MO:DCA architecture, these resource groups are called external resource groups
because they occur outside a document.
The Inline Resource Group is an optional component of the Print File. If no Inline Resource
Group is defined, the resources stored in the AFP resource library of the system are used. (In
MVS/ESA™ with USERLIB support, resources might
be stored in private libraries that are
used at print time for individual data sets. Up to eight private libraries may be used with a
single data set. The libraries are named in the USERLIB parameter of the OUTPUT JCL
statement.)
The scope of an inline resource group is the print file. Once the last document in the print file
has been processed, the resources in the resource group are no longer available to the
presentation system for use with another print file.
Documents
The print file may contain one or more documents to be printed. These may be fully
composed-page documents, line-mode documents, or mixed-mode documents, in any order.
If multiple composed-page documents appear, each one must be delimited by a BDT and an
EDT structured field. For the complete definitions of document structure, see Appendix A,
“Document and Resource Object Diagrams”, on page 169.
Finishing Operations for a Print File
A Form Definition may be used to specify finishing operations to be applied to the documents in a print file. The
scope of the finishing operations as well as the type of operation is specified with a Medium Finishing Control
(MFC) structured field in the Document Environment Group (DEG) of the Form Definition. For a definition of the
finishing operations and parameters that may be specified, see the Mixed Object Document Content
Architecture (MO:DCA) Reference. The following rules specify how the scope of the finishing operations
applies to a print file when the file contains line-data and mixed-data documents, with or without BDT/EDT , as
well as composed documents.
• If the MFC specifies print-file level finishing, all media in the print file is collected for finishing in a print-file
level media collection and the finishing operations are applied to the complete collection; that is, the
complete print file.
• If the MFC specifies document-level finishing and selects all documents, the print file is processed as a set of
documents as follows:
– Any document bounded by BDT/EDT is processed as a single document regardless of whether the data
between BDT/EDT is line data, mixed data, or composed data.
– Line data and mixed data that is not bounded explicitly by BDT/EDT is processed as an implied document
with implied BDT/EDT . When such data follows the resource group or an EDT , a BDT is implied and the
implied document lasts until a BDT is encountered or until the end of the print file is reached. In either case,
the implied document is terminated with an implied EDT .
The media in each document, whether implied or explicit, is collected for finishing in a document-level media
collection and the finishing operations are applied to each collection, that is each document, individually.
Note that, in this case, the same finishing operations are applied to each document.
• If the MFC specifies document-level finishing and selects a single document, the print file is processed as a
set of documents in the same manner as when all documents are selected. The offset of the selected
document is calculated by counting all documents, whether implied or explicit, and the selected document
might
itself be an implied document. The media in the selected document are collected for finishing and the
Mixed Documents

## Page 61

AFP Programming Guide and Line Data Reference 43
finishing operations are applied to the single collection; that is, the single document. If the same document is
selected multiple times, finishing operations are applied in the order specified. Note that, using this type of
MFC, unique finishing operations may be specified for each document in the print file.
Inline Resource Group Structure
A resource group begins with the Begin Resource Group (BRG) structured field and ends with the End
Resource Group (ERG) structured field. Inline resources are included in the inline resource group and can be
referred to by name within the print file. They override objects of the same name stored in resource libraries
accessed by the print server. Each individual resource begins with the Begin Resource (BRS
) structured field
and ends with the End Resource (ERS ) structured field. When a resource object is stored in a library, the BRG,
BRS, ERG, and ERS structured fields are not present. When using AFP with a z/OS system, all structured
fields of resource objects included in inline resource groups must be preceded by the X'5A' character. Figure
16 shows the structure of an inline resource group.
Figure 16. Structure of an Inline Resource Group
Inline
Resource
Group
Resource
Object
BRG
BRS ERS
ERG
There might be more than one
resource in a resource group.
{
The structured fields and objects in an inline resource group are as follows. (Chapter 5, “Structured Fields in a
Page Definition and in Line Data” describes the structured fields.)
BRG (Begin Resource Group)
Begins an inline resource group in the Print File.
BRS
(Begin Resource)
Begins a resource object, specifies the resource type, and specifies the name used to select
the object for printing.
Resource Object
A resource object can be one of the following:
• A page segment
• An overlay
• A data object
• An object container
• A document
• A Form Definition
• A Page Definition
• A font object (a code page, a font character set, or a coded font)
Mixed Documents

## Page 62

44 AFP Programming Guide and Line Data Reference
See the description of the BRS structured field in the Mixed Object Document Content
Architecture (MO:DCA) Reference for the hexadecimal codes used to identify each type of
resource object.
ERS (End Resource)
Ends the resource object. Any name specified in the ERS must match the name specified in
the BRS.
ERG (End Resource Group)
Ends the inline resource group. Any name specified in the ERG must match the name
specified in the BRG.
Note: Not all presentation services programs support all resource objects in a Resource Group.
Programming Considerations for Inline Resources
Because most resource objects consist of variable-length records, any print file that includes these resources
inline must be in variable-length-record format and must use data records beginning with a carriage control
byte.
Invoke Data Map
The Invoke Data Map (IDM) structured field selects a new Data Map for printing line data and ends the current
line-format page.
Note: When using machine carriage control characters, care must be taken to prevent a blank page from being
printed at the start of a document. If the application inserts IDM structured fields following records that
have a “skip to channel nn immediate” carriage control (X'8B') without making an exception for the start
of the document, a blank page will be generated. When the first line data record contains a skip
immediate carriage control, a line-format page is started even though there is no data to be printed.
When the IDM follows the initial skip immediate carriage control at the start of the document to be
printed, the IDM ends the current page, causing a blank page to be printed. When the skip immediate
carriage control is used later in the document to end the page and it is followed by the IDM structured
field, a blank page does not occur since the skip immediate carriage control has already ended the
current line-format page.
• For traditional line data, processing begins with the first Line Descriptor (LND) structured field of the invoked
Data Map for the next line-format page.
• For record-format line data, processing begins with the first Record Descriptor (RCD) structured field that
matches the record ID of the first record processed following the IDM.
The IDM structured field can be used to change formatting based on some change in the application data,
such as the start of output for a different department or branch office.
The IDM structured field always contains sixteen bytes of information. The Data Map name in the data portion
of this structured field must be eight bytes long. If the name of the actual Data Map to be invoked is shorter
than eight bytes, trailing blanks must be added.
Mixed Documents

## Page 63

AFP Programming Guide and Line Data Reference 45
Sample IDM Structured Field
The Invoke Data Map structured field shown in Figure 17 causes a presentation services program to select
Data Map SUMMARY.
The IDM is 16 (X'10') bytes long and has the structured field identifier X'D3ABCA'. In the example, the flag byte
is set to X'00' and bytes 6 and 7 contain a sequence number of X'0000'. It is not necessary to number MO:DCA
structured fields sequentially or even to place a meaningful value in the sequence number field. However, for
some errors detected by presentation services programs , the sequence number of the structured field in error
is printed as part of the error information in the PIMSG data set. This field is reserved in MO:DCA data streams
and should be set to zero.
When a presentation services program
processes the IDM structured field, the current page is ended. The next
record read by the presentation services program begins on a new page and the information contained in Data
Map SUMMARY is used to format subsequent data lines. Use of this structured field assumes the currently
active Page Definition contains a Data Map with the name SUMMARY in its Begin Data Map structured field. If
no such Data Map exists, an error is generated.
Figure 17. Sample Invoke Data Map Structured Field
X'5A' X'0010' X'D3ABCA' X'00' X'0000' X'E2E4D4D4C1D9E840'
Invoke Medium Map
The Invoke Medium Map (IMM) structured field is similar to the IDM structured field except that it causes a
presentation services program to select a new Medium Map, or Copy Group, in the current Form Definition at
the point where the IMM structured field appears in the print file. The presentation services program ends
printing on the current sheet when an IMM is encountered. Note that if the Medium Map specifies the N-up
function, the IMM might cause the presentation services program to end printing on the current N-up partition
instead of on the current sheet.
The IMM structured field can appear in line-mode, mixed-mode, or fully composed documents. For line-mode
or mixed-mode data, processing resumes with the first Line Descriptor (LND) structured field in the Data Map
that is active for the next line-format page. When the Data Map contains RCDs, processing resumes with the
first RCD whose Record ID matches the current data record. The IMM structured field is sixteen bytes long and
must be coded as shown below.
The IMM structured field can be written by the application when some physical control of the output is required.
By using the IMM, the application can offset pages in the data from the medium origin, select paper from the
primary or alternate bin, or change between simplex and duplex printing, simply by selecting a Medium Map
that contains the desired function.
The functions provided by the IDM and IMM structured fields are the same as those provided by changing Data
Maps and Medium Maps with conditional processing in a Page Definition. It is possible to use conditional
processing to make the Data Map and Medium Map change without modifying the application to add the IDM
and IMM structured fields.
Note that at the beginning of a new composed document and at the beginning of a new set of line-data
records, control for presentation is returned to the first Medium Map in the Form Definition. This is shown in
Figure 18 on page 46.
Mixed Documents

## Page 64

46 AFP Programming Guide and Line Data Reference
Figure 18. Returning Control to First Medium Map in Form Definition
Form Definition
Medium Map M1
Medium Map M2
Line data records <presentation controlled by M1>
⋮
IMM, Medium Map M2
Line data records <presentation controlled by M2>
⋮
BDT <presentation control reverts to M1>
Composed Pages
⋮
IMM, Medium Map M2 <presentation controlled by M2>
⋮
Composed Pages
⋮
EDT
Line data records <presentation control reverts to M1>
⋮
Sample IMM Structured Field
The Invoke Medium Map structured field shown in Figure 19 causes the presentation services program to
select Medium Map BIN2. (Note that BIN2 contains four trailing blanks to fill out the eight-byte data field.)
When a presentation services program processes this structured field, the current page is ended. The next
record read by the presentation services program is placed on a new sheet and the information contained in
medium map BIN2 is used. Note that if the Medium Map specifies the N-up function, the next record may be
placed on a new partition of the same sheet. If the currently active Form Definition does not contain a Medium
Map with that name in the Begin Medium Map structured field, an error is generated.
Figure 19. Sample Invoke Medium Map Structured Field
X'5A' X'0010' X'D3ABCC' X'00' X'0000' X'C2C9D5F240404040'
Using Structured Fields to Skip to a New Page or Sheet
Chapter 3, “Using a Page Definition to Print Data” described the use of conditional processing in a Page
Definition to perform a skip-to-new-page or skip-to-new-sheet operation based on a change in the value of a
control field in an application data stream. The conditional processing function was added to the Page
Definition to provide another way of producing the same output as by e
mbedding IDM or IMM structured fields
in a line-data file to force a new page or sheet. When an IDM or IMM structured field appears in an application
data stream, the presentation services program ends the current page and resumes printing at the start of a
new page, using the first Line Descriptor in the current Data Map. When the Data Map contains RCDs, printing
resumes at the start of a new page using the first RCD whose Record ID matches the current data record.
The data stream shown in Figure 20 on page 47 provides the same result as the Page Definition shown in
Figure 13 on page 32 and the data stream shown in Figure 21 on page 47 provides the same result as the
Page Definition shown in Figure 14 on page 32.
Mixed Documents

## Page 65

AFP Programming Guide and Line Data Reference 47
Figure 20. Using an IDM Structured Field to Skip to a New Page
Line data records (with carriage control)
⋮
X'5A0010D3ABCA000001D5C5E6D7C7404040'
More line data records
⋮
Figure 21. Using an IMM Structured Field to Skip to a New Sheet
Line data records (with carriage control)
⋮
X'5A0010D3ABCC000001D5C5E6C6D4404040'
More line data records
⋮
The name of the Data Map invoked in Figure 20 is NEWPG. This is the name on the PAGEFORMAT statement in
the PPFA example in Figure 13 on page 32. Re-invoking the same Data Map causes a skip to a new page. It is
not necessary to have multiple Data Maps in the Page Definition to achieve this result. Consequently, standard
Page Definitions supplied with the print services software can be used with this method.
The same is true of skipping to a new physical sheet. Figure 21 invokes a Medium Map named NEWFM. Even if
NEWFM is the current and only Medium Map in the Form Definition, the presence of this structured field causes
a skip to a new sheet of paper, or, in the case of N-up presentation, possibly a skip to a new N-up partition.
IMM Structured Fields to Insert a Blank Sheet
Occasionally an application requires that a blank sheet appear between groups of output within a single data
set. This blank sheet might be selected from different-color paper loaded in the alternate bin, or it might just be
another sheet from the primary bin. The blank sheet is generated by using a Form Definition that specifies the
constant data function, which allows a sheet to be produced without any variable data on it. T o generate the
blank sheet, code two consecutive IMM structured fields, as shown in Figure 22.
Figure 22. Using Two IMM Structured Fields to Force a Blank Sheet
Line data records (with carriage control)
⋮
X'5A0010D3ABCC000000C1D3E3C2C9D54040'
X'5A0010D3ABCC000000D7D9C9C2C9D54040'
More line data records (with carriage control)
⋮
This example assumes a Form Definition with two Medium Maps, as could be built using the PPFA code
shown in Figure 23 on page 48. The first Medium Map coded in the example will be used for the initial pages.
They will contain user data (the CONSTANT parameter does not appear in this Medium Map) and are printed
on paper selected from the primary bin. When the point is reached where a blank sheet is to be inserted, the
application writes out an Invoke Medium Map that
selects the second Medium Map. This Medium Map selects
a sheet of paper from the alternate bin. No user data is placed on the pages coming from the alternate bin,
because CONSTANT FRONT and DUPLEX NO are coded. If the output were to be printed in duplex,
CONSTANT BOTH and DUPLEX YES can be coded instead.
Immediately following the IMM structured field to select the second Medium Map (ALTBIN) is a second IMM to
return to the original Medium Map (PRIBIN) for the next portion of the data. This set of two consecutive IMM
structured fields can be included in the output data stream as often as necessary.
Mixed Documents

## Page 66

48 AFP Programming Guide and Line Data Reference
Figure 23. Form Definition With Two IMMs to Force a Blank Sheet
FORMDEF BLANKT
OFFSET 0 0
REPLACE YES;
COPYGROUP PRIBIN
DUPLEX NO BIN 1;
COPYGROUP ALTBIN
CONSTANT FRONT
DUPLEX NO BIN 2;
Variable-Length and Fixed-Length Records
MO:DCA structured fields are variable in length so their lengths can differ. Line data records intermixed with
MO:DCA structured fields might also have different lengths. Fully composed MO:DCA documents may consist
of records up to 32K bytes long. However , variable-length data is not always desirable. Programming
requirements might make it preferable to use fixed-length records in some circumstances. A presentation
services program can process a mixed document of fixed-length records even though some of the records
contain structured fields with significant information that is much shorter than the data records to be printed. So
long as the information in the length portion of the structured field is correct and the structured field is padded
with blanks to the length of the other records in the data set, no errors are generated. The structured fields
shown in Figure 24 are all considered valid by the presentation services program
in a z/OS system
environment. The third form, however, might not be supported in a multi-system environment.
Figure 24. Three Versions of the Invoke Data Map Structured Field
X'0010' X'D3ABCA' X'00' X'0000' PFORMAT1 (Data Map [Page Format] name—8 bytes EBCDIC)
X'0050' X'D3ABCA'
(Identifier)
X'08'
(Flag
byte)
X'0000' PFORMAT1 (Data Map [Page
Format] name—8 bytes
EBCDIC)
63 bytes of X'00' followed by
one byte of X'40'
X'0010' X'D3ABCA'
(Identifier)
X'00'
(Flag
byte)
X'0000' PFORMAT1 (Data Map [Page
Format] name—8 bytes
EBCDIC)
64 bytes of any information to
fill the record out to 80 bytes
The first structured field at the top of Figure 24 is the most common form of Invoke Data Map. The IDM
structured field is 16 bytes (X'10') long, so the value X'10' appears in the length field of the introducer. Next is
the X'D3ABCA' identifier for Invoke Data Map. The flag byte is zero. The syntax rules for Invoke Data Map
indicate that the eight-byte name of the requested Data Map be coded as the data portion of the structured
field. This is the rightmost information in the figure.
The second structured field in Figure 24 is 80 bytes long, but here the formal MO:DCA conventions for using
padding bytes have been followed. In this example, the flag byte is coded as X'08', which signals that padding
bytes appear in the structured field. The padding bytes follow the variable data for the IDM structured field and
the final padding byte is coded as X'40' to signal that 64 padding bytes are present. The length field has been
changed from 16 (X'10') to 80 (X'50') to reflect the increased length of the structured field.
The third structured field in Figure 24 is identical to the first, except that the actual MO:DCA data appears as
the first 16 bytes in an 80-byte record. This format allows the IDM structured field to be included in a data set of
fixed-length 80-byte records and no errors would result in a z/OS system
environment.
Mixed Documents

## Page 67

AFP Programming Guide and Line Data Reference 49
Of course, fixed-length records that are longer than the number of bytes actually used to contain the MO:DCA
structured field information will result in a data set that is larger than one containing variable-length records,
each one no longer than necessary. This might be a consideration if the resulting data set is to be sent across
a network.
Position and Orientation of Objects
Two coordinate systems are used to position and rotate objects in line data: the page (X p,Yp) coordinate
system and the text (I,B) coordinate system. The page coordinate system is based on the fourth quadrant of a
standard Cartesian coordinate system with the origin in the top-left corner, the X axis increasing from left to
right, and the Y axis increasing from top to bottom. The text (I,B) coordinate system is defined, relative to the
page coordinate system, by the text orientation as follows:
Text Orientation (I,B) Coordinate System
0°,90° Origin at top-left corner, I increases left to right, B increases top to bottom.
90°,180° Origin at top-right corner, I increases top to bottom, B increases right to left.
180°,270° Origin at bottom-right corner, I increases right to left, B increases bottom to top.
270°,0° Origin at bottom-left corner, I increases bottom to top, B increases left to right.
The coordinate system used depends on the object and how it is included in line data. T able 10summarizes
how objects are positioned and rotated in line data. The table also summarizes how objects are positioned and
rotated in MO:DCA data that has been transformed from line data, using the Line Data Object Position
Migration (X'27') triplet to capture the text orientation that was active when the line data was presented with a
Page Definition. More details on how objects are positioned and rotated are
given in the sections that follow
the table.
Table 10. Position and Rotation of Objects in Line Data and MO:DCA Data
OBJECTS IN LINE DATA OBJECTS WITH X'27' TRIPLET IN MO:DCA DATA
TRANSFORMED FROM LINE DATA
Page Segment Object
Page Segment Origin
(XpsOset,YpsOset) in IPS specify an offset from the
current text coordinate system origin (I=0,B=0). The offset
is measured using the current text (I,B) coordinate system.
(XpsOset,YpsOset) in IPS specify an offset from the page
origin (Xp=0,Yp=0). The offset is measured using the page
(X
p,Yp) coordinate system. The offset was adjusted to
include the LND or RCD position.
IM—Image Object in Page Segment
IM—Image Object Origin
(XoaOset,YoaOset) in IOC specify an offset from the page
segment origin. The offset is measured using the current
text (I,B) coordinate system.
(XoaOset,YoaOset) in IOC specify an offset from the page
segment origin. The offset is measured using the
temporary (X,Y) coordinate system.
IM—Image Object Rotation
(XoaOrent,YoaOrent) in IOC specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
(XoaOrent,YoaOrent) in IOC specify a rotation that is
measured with respect to the page (X p,Yp) coordinate
system Xp-axis.
IM—Image Cell Origin
(XCOset,YCOset) in ICP specify an offset from the image
object origin. The offset is measured using the current text
(I,B) coordinate system.
(XCOset,YCOset) in ICP specify an offset from the image
object origin. The offset is measured using the temporary
(X,Y) coordinate system.
Mixed Documents

## Page 68

50 AFP Programming Guide and Line Data Reference
Table 10 Position and Rotation of Objects in Line Data and MO:DCA Data (cont'd.)
OBJECTS IN LINE DATA OBJECTS WITH X'27' TRIPLET IN MO:DCA DATA
TRANSFORMED FROM LINE DATA
OCA Object in Page Segment
OCA Object Origin—OBP Byte 23=X'00'
(XoaOset,YoaOset) in OBP specify an offset from the page
segment origin. The offset is measured using the current
text (I,B) coordinate system.
(XoaOset,YoaOset) in OBP specify an offset from the page
segment origin. The offset is measured using the
temporary (X,Y) coordinate system.
OCA Object Origin—OBP Byte 23=X'01'
(XoaOset,YoaOset) in OBP specify an offset from the page
origin (Xp=0,Yp=0). The offset is measured using the page
(X
p,Yp) coordinate system.
(XoaOset,YoaOset) in OBP specify an offset from the page
origin (Xp=0,Yp=0). The offset is measured using the page
(Xp,Yp) coordinate system.
OCA Object Rotation—OBP Byte 23=X'00'
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the current text (I,B) coordinate
system I-axis.
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the temporary (X,Y) coordinate
system X-axis.
OCA Object Rotation—OBP Byte 23=X'01'
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
Stand-alone IM—Image Object
IM—Image Object Origin
(XoaOset,YoaOset) in IOC specify an offset from the
current LND or RCD position. The offset is measured using
the current text (I,B) coordinate system.
(XoaOset,YoaOset) in IOC specify an offset from the
temporary coordinate system (X=0,Y=0) origin. The offset
is measured using the temporary (X,Y) coordinate system.
The offset was adjusted to include the LND or RCD
position.
IM—Image Object Rotation
(XoaOrent,YoaOrent) in IOC specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
(XoaOrent,YoaOrent) in IOC specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
IM—Image Cell Origin
(XCOset,YCOset) in ICP specify an offset from the image
object origin. The offset is measured using the current text
(I,B) coordinate system.
(XCOset,YCOset) in ICP specify an offset from the image
object origin. The offset is measured using the temporary
(X,Y) coordinate system.
Stand-alone OCA Object
OCA Object Origin—OBP Byte 23=X'00'
(XoaOset,YoaOset) in OBP specify an offset from current
LND or RCD position. The offset is measured using the
current text (I,B) coordinate system.
(XoaOset,YoaOset) in OBP specify an offset from the
temporary coordinate system (X=0,Y=0) origin. The offset
is measured using the temporary (X,Y) coordinate system.
The offset was adjusted to include the LND or RCD
position.
OCA Object Origin—OBP Byte 23=X'01'
(XoaOset,YoaOset) in OBP specify an offset from the page
origin (Xp=0,Yp=0). The offset is measured using the page
(Xp,Yp) coordinate system.
(XoaOset,YoaOset) in OBP specify an offset from the page
origin (Xp=0,Yp=0). The offset is measured using the page
(X
p,Yp) coordinate system.
Mixed Documents

## Page 69

AFP Programming Guide and Line Data Reference 51
Table 10 Position and Rotation of Objects in Line Data and MO:DCA Data (cont'd.)
OBJECTS IN LINE DATA OBJECTS WITH X'27' TRIPLET IN MO:DCA DATA
TRANSFORMED FROM LINE DATA
OCA Object Rotation—OBP Byte 23=X'00'
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the current text (I,B) coordinate
system I-axis.
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the temporary (X,Y) coordinate
system X-axis.
OCA Object Rotation—OBP Byte 23=X'01'
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
(XoaOrent,YoaOrent) in OBP specify a rotation that is
measured with respect to the page (X
p,Yp) coordinate
system Xp-axis.
Positioning With Respect to Current Descriptor
When objects are included in line data, they occur between line-data records and can be positioned with
respect to the inline/baseline position specified by the LNDs or RCDs used to process the records. More
precisely, an included object can be positioned with respect to the current LND, or current RCD. This is also
sometimes referred to as the current line position, which is defined as follows:
Current LND Position
If the line-data records use ANSI carriage controls, spacing or skipping is performed first and printing of the
record is performed last, therefore the current LND is the LND used to process the last record. If the line-data
records use machine carriage controls, printing of the record is performed first and spacing or skipping is
performed last. In this case, the current LND is the LND that is spaced to or skipped to, that is, it is the LND
that will be used to process the next record. Additionally, if the record is processed as a set of fields using a
reuse chain, the current LND is the base LND, that is, the LND that is at the head of the reuse chain. If the
current LND does not generate a position, the LND used is the last LND that did generate a position.
Current RCD Position
Because carriage controls are ignored in record-format line data, the current RCD is always the last record
RCD that was used to process a data record.
Include Page Segment
The Include Page Segment (IPS) structured field is used to place a page segment resource anywhere on the
page. It contains the full eight-character name of the page segment (with trailing blanks if necessary) and the
position of the page segment, often referred to as the page segment origin. The page segment might be
mapped in a Map Page Segment (MPS) structured field in the Active Environment Group (AEG) of the current
Data Map, in which case the page segment is downloaded to the printer and may be used multiple times. If it is
not mapped, the page segment data is loaded as part of the page.
Objects within the page segment might
be positioned with respect to the page segment origin. The page
segment inherits the Active Environment Group definition of the including page.
Mixed Documents

## Page 70

52 AFP Programming Guide and Line Data Reference
AFP print servers initialize the following PTOCA control sequences as shown prior to processing a text object
in an AFP page segment:
Control Sequence Value
Set Baseline Increment 6 lines per inch
Set Inline Margin 0
Set Intercharacter Adjustment 0
Set T ext Color X'FFFF' (printer default color)
Set T ext Orientation 0°,90°
The initial print position for text in the page segment is the reference point defined on the including page or
overlay coordinate system by the IPS, that is, the page segment origin.
Positioning of Page Segments
Special care must be taken when including page segments in line data to ensure that the objects in the page
segment are positioned and oriented properly.
Location of Page Segment Origin
The page segment origin is located on the page as follows:
• If one of the IPS offsets is specified as X'FFFFFF', the page segment origin along that axis is located at the
position specified in the current LND or RCD.
• If the IPS offset is not X'FFFFFF', the page segment origin is located at the IPS offset measured with respect
to the current text coordinate system origin (I=0,B=0), using the current text (I,B) coordinate system. For
example, if the text orientation is (90°,180°), the page segment offsets are measured from the top- right
corner of the page, with the I-axis running from top to bottom and the B-axis running from right to left.
• If the page segment is included with a Resource Object Include (X'6C') triplet on the LND or RCD, the page
segment origin is located at the specified offset measured with respect to the position specified in the current
LND or RCD, using the current text (I,B) coordinate system.
In summary, the origin of a page segment in line data is always positioned using the text (I,B) coordinate
system specified in the current LND or RCD.
Position and Orientation of IM Image Objects in a Page Segment
The image object area offset, as specified in the IOC structured field is measured with respect to the page
segment origin, using the text (I,B) coordinate system specified in the current LND or RCD. If the image is
celled, the Image Cell Position (ICP) structured field specifies an offset from the image object origin that is
measured using the current text (I,B) coordinate system.
The rotation of the IM image is specified in the IOC and is measured with respect to the page coordinate
system X
p-axis (origin is top- left corner of page).
Note: For page segments in MO:DCA data, if the IM image is complex (celled), it is recommended that the
rotation be set to (0°,90°). For page segments in mixed data, the rotation should be set to match the
current text orientation.
Mixed Documents

## Page 71

AFP Programming Guide and Line Data Reference 53
Position and Orientation of Image, Graphics, and Bar Code Objects in a Page
Segment
If the Object Area Position (OBP) structured field specifies byte 23 (RefCSys) = X'00' (current), the object area
offset is measured with respect to the page segment origin, using the text (I,B) coordinate system specified in
the current LND or RCD. The object area rotation is measured with respect to the I-axis of the current text (I,B)
coordinate system.
If OBP byte 23 = X'01' (page or overlay), the object area offset is measured with respect to the page origin (top-
left corner of page) using the page coordinate system. The object area rotation is measured with respect to the
page coordinate system X p-axis (origin is top-left corner of page).
Note: When line data that includes an IPS structured field is transformed into a MO:DCA document by an AFP
application program, the text orientation that was set when the page segment and its objects were
positioned must be captured and retained in order to properly position the page segment on the
MO:DCA page. This can be done using a Line Data Object Position Migration (X'27') triplet on the IPS
structured field in the MO:DCA document. For a description of this triplet, see the description of “Include
Page Segment (IPS)” on page 95.
Sample IPS Structured Field
Figure 25 contains a sample IPS structured field. This example places the segment SIGNAT at the current print
position. If the name of the segment were S1SIGNAT, then all eight characters would have to be coded in the
IPS structured field.
See the programming tip below for information on how the current print position is affected by the IPS.
Figure 25. Include Page Segment Structured Field
X'5A' X'0016' X'D3AF5F' X'00' X'0000' X'E2C9C7D5C1E34040' X'FFFFFF' X'FFFFFF'
Programming Tip
The current line position is unchanged after the page segment is printed. Additional logic might be needed in
the application to place subsequent print lines so that they do not overprint the page segment.
Mixed Documents

## Page 72

54 AFP Programming Guide and Line Data Reference
Include Page Overlay
The Include Page Overlay (IPO) structured field functions in a manner similar to Include Page Segment. The
IPO structured field specifies the full name of the overlay (any O1 prefix in the overlay name must be included)
and the position of the overlay origin. The IPO references an overlay resource that is to be positioned on the
page.
The overlay name must appear in the Map Page Overlay structured field of the Active Environment Group of
the Data Map currently in effect. The overlay contains its own Active Environment Group definition that
specifies the coordinate system for positioning and rotating objects, the size of the overlay, and the names of
any fonts used in it. Considerations for the current line position are the same as those discussed in the
previous programming tip
. The current line position is unchanged after the overlay has been placed.
Note: The IBM 3800 printer does not support the IPO function.
Positioning Overlays
Because overlays define their own coordinate system and environment, the rules for positioning an overlay
and its objects are somewhat different from those for positioning a page segment and its objects.
Location of Overlay Origin
The overlay origin is located as follows:
• If the IPO offset along either the page X p-axis or the page Y p-axis is specified as X'FFFFFF', the overlay
origin along that same axis is located by translating the current LND or RCD (I,B) position to an offset along
that Xp or Yp axis.
• If the IPO offset is not X'FFFFFF', the overlay origin is positioned at the specified (X p,Yp) offset measured
with respect to the page origin (top-left corner of page), using the page coordinate system.
• If the overlay is included with a Resource Object Include (X'6C') triplet on the LND or RCD, the overlay origin
is located at the specified offset measured with respect to the position specified in the current LND or RCD,
using the current text (I,B) coordinate system.
Orientation of Overlay
If the overlay is included either with an IPO or with a Resource Object Include (X'6C') triplet on the LND or
RCD, the overlay rotation may be specified as 0°, 90°, 180°, or 270°, and is measured with respect to the page
coordinate system Xp axis (origin is top-left corner of page). However, the 90°, 180°, and 270° rotations of a
page overlay are not supported in all AFP environments. Consult the product documentation to see which
rotations are supported. Note that the MO:DCA
IS/1 and IS/2 interchange sets only support 0° rotation of a
page overlay.
Position and Orientation of IM Image Object in an Overlay
The image object area offset, as specified in the IOC structured field, is measured using the overlay coordinate
system (origin is top-left corner of overlay).
The rotation of the IM image is specified in the IOC and is measured with respect to the overlay coordinate
system X-axis (origin is top-left corner of overlay).
Note: If the IM image is complex (celled), AFP print servers require the rotation set to 0°,90°.
Mixed Documents

## Page 73

AFP Programming Guide and Line Data Reference 55
Position and Orientation of IO Image, Graphics, and Bar Code Objects in an Overlay
If the Object Area Position (OBP) structured field specifies byte 23 (RefCSys) = X'00' (current) or X'01' (page or
overlay), the object area offset is measured with respect to the overlay origin (top-left corner of overlay) using
the overlay coordinate system.
The rotation of the OCA object is specified and measured using the overlay coordinate system X-axis (origin is
top-left corner of overlay).
Sample IPO Structured Field
A sample IPO structured field appears in Figure 26. It places overlay O1SIGNAT at the current print position on
the page.
Figure 26. Include Page Overlay Structured Field
X'5A' X'0016' X'D3AFD8' X'00' X'0000' X'D6F1E2C9C7D5C1E3' X'FFFFFF' X'FFFFFF'
Include Object
The Include Object (IOB) structured field references an object that is to be positioned on the page. In general,
the IOB may be used to include two classes of objects:
• OCA objects (BCOCA, GOCA, IOCA, PTOCA) that specify an Object Environment Group (OEG) or MO:DCA
page segments that contain such objects
• Non-OCA paginated presentation objects, such as TIFF images, that are supported by the presentation
system
The current AFP support for the IOB in line data is limited to the first class, OCA objects. When referencing an
OCA object, the IOB may be used to override position, size, orientation, mapping, and default color parameters
that are specified in the OEG. When referencing a non-OCA object, the IOB is used to specify the position,
size, orientation, and mapping parameters for the object.
The RefCSys parameter in the IOB is used to select the coordinate system for positioning and rotating the
object area into which the object is mapped:
Value Description
X'00' The object area offset in the IOB is measured with respect to the current LND or RCD position, using
the current text (I,B) coordinate system. The object area rotation in the IOB is measured with respect
to the current text (I,B) coordinate system I-axis.
X'01' The object area offset in the IOB is measured with respect to the page origin (X
p=0,Yp=0), using the
page (Xp,Yp) coordinate system. The object area rotation in the IOB is measured with respect to page
(Xp,Yp) coordinate system X p-axis.
Mixed Documents

## Page 74

56 AFP Programming Guide and Line Data Reference
Including Data Objects Directly in Line Data
Previously it was described how complete AFP resources can be included in the resource group of a print file
rather than having to be stored in an external resource library. This is one approach that can be used with
applications where many different resources must be included in the print stream and where it might
not be
feasible to store these resources externally to the application. However , another approach is possible for
applications that require large numbers of graphics, images, or bar codes.
One example of such an application is label printing, where many different labels are printed in multiple-up
format, each one requiring a unique bar code. Another example is a financial statement application that
includes a chart of specific investment performance for each customer. The programming logic required for
applications such as these is simpler if each bar code or page segment can be included in the output at the
same point as the other data for a given label or statement. Grouping all resources (which can number in the
thousands) in an external library or in a resource group at the beginning of the print file might
not be practical.
In addition, it might be preferable to keep the bar codes, images, or graphics as part of the actual line data for
archive purposes. Finally, including them directly in the line data can eliminate the problem of devising unique
names for thousands of objects that
change each time the program is run.
Graphics, images, and bar codes included with other print data in this manner are not true inline resources,
because they do not follow the rules for inline resources described previously. When structured fields that
make up graphics, images, or bar codes are included directly in the line data, they provide yet another example
of an AFP mixed-mode document.
Including Bar Code, Graphics, IO Image, and Presentation Text Objects
with OEG
Objects that include an Object Environment Group (BCOCA, GOCA, IOCA, and PTOCA objects) can be
included directly in a mixed-mode document intermixed with line data so long as the following rules are
observed:
• The reference coordinate system (byte 23 of the data field of the Object Area Position [OBP] structured field)
must be coded to provide the desired position and rotation of the object on the page:
– If OBP byte 23 (RefCSys) = X'00' (current), the object area offset is measured with respect to the position
specified in the current LND or RCD, using the current text (I,B) coordinate system. The object area
rotation is measured with respect to the I-axis of the current text (I,B) coordinate system.
– If OBP byte 23 (RefCSys) = X'01' (page or overlay), the object area offset is measured with respect to the
page origin, using the page coordinate system (origin is top-left corner of page). The object area rotation is
specified in the OBP and is measured with respect to the page coordinate system X
p-axis (origin is top- left
corner of page).
• If the image or graphic has been built as a page segment, delete the Begin Page Segment and End Page
Segment structured fields from the object. The remaining structured fields can be placed in the print stream
at the point where the image or graphic should appear.
Mixed Documents

## Page 75

AFP Programming Guide and Line Data Reference 57
Including IM Image Objects
Page segments containing IM image data do not have an Object Environment Group, so somewhat different
considerations apply to them. Between the BPS and EPS structured fields are the records that provide
positioning information for the bits that define the image and the actual bits themselves in uncompressed form.
Just as for BCOCA, GOCA, IOCA, and PTOCA
objects, the positioning information contained in the IOC
structured field should be coded to provide the desired placement of the image. Bytes 0 through 5 in the IOC
specify the image object area origin for IM images. The offset is measured with respect to the I,B position
specified in the current LND or RCD, using the current text (I,B) coordinate system. The image object area
offset should be coded as X'000000000000' to position the image at the current LND or RCD. If the image is
celled, the Image Cell Position (ICP) structured field specifies an offset from the image object origin that is
measured using the current text (I,B) coordinate system.
The rotation of the IM image is specified in the IOC and is measured with respect to the page coordinate
system X
p-axis (origin is top-left corner of page).
Note: For page segments in MO:DCA data, if the IM image is complex (celled), it is recommended that the
rotation be set to (0°,90°). For page segments in mixed data, the rotation should be set to match the
current text orientation.
The Begin Page Segment (X'D3A85F') and End Page Segment (X'D3A95F') structured fields should be
deleted. The remaining structured fields can then be placed in the print stream at the point where the image is
to appear.
Including Standalone Presentation Text (PTX) Structured Fields
The Presentation T ext (PTX) structured field is used to specify text data and the position, rotation, and fonts to
be used when presenting text data. The PTX structured field was previously known as Composed T ext (CTX),
but its identifier of X'D3EE9B' and all its components remain the same. PTX structured fields are made up of
control sequences and data. The PTX structured field is described in Presentation Text Object Content
Architecture Reference and provides different functions in the form of control sequences. PTX is probably the
most frequently used structured field in fully composed MO:DCA documents. PTX structured fields can be
intermixed with line data records so long as a few rules are followed:
• Each PTX structured field should be coded as a self-contained environment. While PTX control sequences
can be used to set the line spacing, page margin, data position, font, etc., these settings remain in effect only
for the current PTX structured field. Processing of follow-on line data records or structured fields might
change the settings. If a line data record follows a PTX, settings such as its placement and font is determined
by the information in the current LND or RCD of the active Page Definition. A PTX can affect the printing of
line-data records if it contains text control sequences that change inter-character and inter-word spacing,
because these characteristics are not controlled by a Page Definition. If another PTX structured field follows
the PTX, the text environment established by the last-used LND or RCD is re-issued before the new PTX is
processed. Some presentation systems that convert the mixed-mode data to MO:DCA might
also place
Begin Presentation T ext (BPT) and End Presentation T ext (EPT) structured fields around each embedded
PTX. Subsequent processing of the BPT will cause initial text conditions to be set prior to the processing of
the PTX. See Mixed Object Document Content Architecture (MO:DCA) Reference for more information on
initial text conditions set when the BPT is processed.
• Because the presentation
services software considers line-data files to be mapped totally with a Page
Definition, the presentation services software generates IPDS commands containing positioning and font
information for every record in the file. If a record turns out to be a PTX structured field, the information in the
PTX is used to create a subsequent IPDS Write T ext command. If a large number of PTX structured fields
are included in a line-mode data set, the additional IPDS commands generated by the presentation
services
software could add an unacceptable amount of processing overhead when the data set is printed.
• Page Definition information, PTX information, and any additional information contained in objects such as
bar code and image placed on the page interact, so the programmer must keep careful track of the page
Mixed Documents

## Page 76

58 AFP Programming Guide and Line Data Reference
position and fonts in effect as records are written. For example, if the text position, text orientation, or font is
not defined in a structured field or object, the values specified in the Page Definition for the current line-data
record will be used. Depending on the complexity of the application, it might be easier to write fully composed
output rather than using a Page Definition to set up the environment.
Figure 27. Presentation Text Structured Field
Length X'D3EE9B' Flag
byte
Sequence number Data
Record Format When Using PTX Structured Fields
When creating a mixed-mode data set that includes PTX structured fields, it is generally easier to use variable-
length records. The PTX structured field length ranges up to 32,759 bytes. Much spool space is wasted if every
record is padded out to this length, regardless of whether or not the entire 32K bytes contain valid data.
Using the PTX Structured Field
The PTX structured field contains PTOCA data, as defined in the Mixed Object Document Content Architecture
(MO:DCA) Reference. The general format of the PTX structured field is shown in Figure 27. Either of two types
of data can follow the PTX structured field introducer:
• The X'2BD3' escape sequence, followed by one or more text control sequences
• “Free-standing text”, which is a series of code points representing data to be printed
The first alternative is by far the most common use of PTX. A table of the control sequences that can be used
with the PTX structured field appears in T able 11 on page 60.
The PTOCA Architecture groups control sequences into function sets or subsets. PT1 is the base subset that
is supported by all AFP page printers. PT2 is a superset of PT1 that contains three additional control
sequences: Underscore (USC), Overstrike (OVS), and T emporary Baseline Move (TBM). PT3 is a superset of
PT2 that contains the Set Extended T ext Color (SEC) control sequence for supporting spot colors and process
colors in text. PT4 is a superset of PT3 that contains controls for glyph runs (used with complex Unicode text).
See Advanced Function Presentation Printer Information, G544-3290 for information on which PTOCA subsets
are supported by your printer.
In a PTX structured field, a control sequence immediately follows each X'2BD3' escape sequence. Each
control sequence can be coded as unchained (even-numbered functions) or chained (odd-numbered
functions). If unchained controls are used, each one must be preceded by the X'2BD3' escape sequence. In
the chained format, each control sequence immediately follows the previous one with no intervening X'2BD3'
escape sequence. The last control sequence in a chain must have the even-numbered (unchained) format to
signal the end of the chain.
Each text control sequence is a minimum of two bytes long, where the X'2BD3' escape sequence, if present, is
not counted as part of the length. The first byte indicates the length of the entire control sequence, including
the length byte itself, the function byte, and any parameter bytes. The second byte contains the odd or even
function code for the control sequence. A data field ranging from zero to 253 bytes follows.
One reason why free-standing text is seldom used is that one of the PTX control sequences available is
Transparent Data (TRN), which has a string of code points as its data field, and thereby provides the actual text
to be printed. Use of the TRN control sequence allows data whose encoding scheme uses the code points
X'2B' or X'D3' to be included in a PTX without having these code points interpreted as an escape sequence.
Mixed Documents

## Page 77

AFP Programming Guide and Line Data Reference 59
The usual sequences for placing text on a page are as follows:
• Specify the beginning print position using Absolute Move Inline (AMI) and Absolute Move Baseline (AMB)
control sequences
• Select the coded font to be used with the Set Coded Font Local (SCFL) control sequence
• Specify the code points of the text to be printed using a Transparent Data (TRN) control sequence
Here is an example:
X'5A001BD3EE9B0000002BD304D300F004C700B403F10106DAC4C1E3C1'
This example begins with a X'5A' carriage control character, as would be required in the z/OS system
environment. Following this byte in the example is a two-byte length field, which provides the length of the
entire record (X'001B' = 27). The X'5A' character is not included in this count. The next three bytes are the
Presentation T ext identifier (X'D3EE9B'). Following that is the X'00' flag byte and the two-byte sequence
number (X'0000'). The first two bytes of the data are the escape sequence (X'2BD3'), followed by a text control
sequence that indicates chaining.
The first control sequence is an Absolute Move Baseline that specifies a baseline offset of X'00F0' logical units
from the page origin. For a 240 units-per-inch coordinate system, this indicates an offset of one inch down the
page.
The second control sequence is an Absolute Move Inline that specifies an Inline offset of X'B4' or 180 units
from the left margin.
Following this is a Set Coded Font Local that selects the coded font that maps to font local ID 1 in the MCF
structured field in the Active Environment Group for the Data Map.
The last control sequence is a six-byte-long Transparent Data, which simply contains the word DATA and ends
the chaining sequence because it uses the X'DA' (even) function type.
Programming Tip
When deciding how to code Presentation T ext structured fields, keep in mind that it is good programming
practice to build as long a PTX structured field as possible, to reduce overhead in the print server associated
with reading and processing many short records written by the application. T ext control sequences should be
chained wherever possible. While a string of unchained control sequence pairs will work also, the presence of
the X'2BD3' escape sequences can use up many of the 32,759 bytes of the PTX structured field
unnecessarily.
Within a fully composed document, the last control sequence in any text object must always indicate end of
chaining. If PTX structured fields are intermixed with line data in a mixed-mode document, the last control
sequence in the PTX must also indicate end of chaining. This can be accomplished either by specifying an
even function type for the last control sequence or by ending every PTX with a No Operation control sequence
with an even function type (X'02F8').
Mixed Documents

## Page 78

60 AFP Programming Guide and Line Data Reference
Use of Fonts
Either fixed-pitch or proportionally spaced fonts can be used to present text with the PTX structured field.
Positioning of the first character in a string of data contained in the TRN control sequence can be
accomplished by preceding the TRN with one of the absolute or relative move text controls, as shown in the
example on page 59. If no move control sequences follow in the same PTX, data contained in any subsequent
TRN controls will be placed immediately following the text in the preceding TRN. Font information stored in the
printer is used to ensure that data does not overlap. As a result, it is possible to highlight one word in a string
simply by using a Set Coded Font text control. If the PTX record shown on page 59 is extended to print the
word DATA a second time in a different font, as in this example:
X'…2BD304D3010004C700B403F10106DBC4C1E3C103F10206DAC4C1E3C1'
then the resulting output will look like this:
DATADATA
Table 11. Control Sequences Used in PTX Structured Field
PTOCA Control Sequence Function Unchained
(Even Function)
Chained
(Odd Function)
PT1 Control Sequences
Absolute Move Baseline 04D2 04D3
Absolute Move Inline 04C6 04C7
Begin Line 02D8 02D9
Begin Suppression 03F2 03F3
Draw Baseline Rule 07E6 07E7
Draw Inline Rule 07E4 07E5
End Suppression 03F4 03F5
No Operation xxF8 xxF9
Relative Move Baseline 04D4 04D5
Relative Move Inline 04C8 04C9
Repeat String xxEE xxEF
Set Baseline Increment 04D0 04D1
Set Coded Font Local 03F0 03F1
Set Intercharacter Increment 04C2 04C3
Set Inline Margin 04C0 04C1
Set T ext Color 0574 0575
Set T ext Orientation 06F6 06F7
Set Variable Space Character Increment 04C4 04C5
Transparent Data xxDA xxDB
PT2 Control Sequences
Overstrike 0572 0573
T emporary Baseline Move xx78 xx79
Underscore 0376 0377
PT3 Control Sequences
Set Extended T ext Color xx80 xx81
Mixed Documents

## Page 79

AFP Programming Guide and Line Data Reference 61
Table 11 Control Sequences Used in PTX Structured Field (cont'd.)
PTOCA Control Sequence Function Unchained
(Even Function)
Chained
(Odd Function)
PT4 Control Sequences
Glyph Advance Run xx8C xx8D
Glyph ID Run xx8B
Glyph Layout Control xx6D
Glyph Offset Run xx8E xx8F
Unicode Complex T ext 106A
Right-justification and centering of text cannot be done simply by using PTX control sequences. Calculations
must be done in the program to place each character at the correct position on the page. This can become
fairly complex if proportional fonts are used.
Boxes and Rules
The Draw Baseline Rule and Draw Inline Rule control sequences may be used to draw rules and boxes on the
page to highlight information or to separate one area of the output from an adjacent area. The length and
thickness of the rule must be specified in the control sequence and these values are expressed in the units of
measure specified in the Presentation T ext Descriptor (PTD) structured field. If the rule is to be drawn in the
positive baseline or inline direction (that is, from top to bottom or from left to right), the positive number
expressing the length and thickness is used. If the rules are to be drawn in the direction opposite the baseline
direction (“up” relative to the data on the page) or the direction opposite the inline direction (“backward” relative
to the data on the page), the line length or thickness must be coded in two's complement form.
The two's complement of a two-byte hexadecimal number is obtained by inverting each bit of the number and
adding a one to the low-order bit position. For example, a one-inch rule is 240 logical units long, or X'F0' L-
units, when using 240 units per inch. This value can be placed directly in a Draw Inline Rule control sequence.
T o obtain the value to use when drawing this rule in the opposite direction, you calculate the two's complement
of X'F0' by inverting to get X'FF0F' and then adding X'0001'. The result is X'FF10'. The full, chained control
sequence that draws a 3-unit thick rule one inch long in the “backward” direction is X'07E5FF10000300'.
The third and fourth data bytes of the draw rule control sequence specify the thickness of the rule. T o
determine whether a positive number or the two's complement number is needed, you should decide in which
direction to add pels, starting from the initial print position. For inline rules, a positive thickness value adds pels
from top to bottom, while a two's complement value adds pels from bottom to top. For baseline rules, pels are
added to the right if the thickness value is positive and to the left if the thickness is expressed as a two's
complement (negative) number.
These details come into play when drawing boxes with mitered corners. T o make the box outline complete and
not have a gap between the end of a baseline rule and the start of an inline rule beneath it, you might
have to
change the origin point of the rule, the length of the rule, or the rule thickness from positive to negative. Gaps
between inline and baseline rules become increasingly visible as the thickness of the rules increase.
Figure 28 on page 62 illustrates a text-control sequence to draw a box one inch high by two inches wide. The
rules that generate the box are four pels thick, so the lengths of the rules in the Draw Rule control sequences
have been extended by 4 pels where necessary to make sure the corners are complete.
Mixed Documents

## Page 80

62 AFP Programming Guide and Line Data Reference
Figure 28. Text Controls to Draw a Box
… 04C7000F04D300F007E501E000040007E700F0000400…
< AMI >< AMB ><Inline Rule><Baseline Rule>
(bottom side)     (left side)
… 04C901E004D500F007E5FE20FFFC0007E6FF10FFFC00
< RMI >< RMB ><Inline Rule><Baseline Rule>
(top side)        (right side)
Mixed Documents

## Page 81

AFP Programming Guide and Line Data Reference 63
Composed Documents
The discussion up to this point has described how line-mode data can be printed in any desired format by
using an appropriate Page Definition. Information has also been provided on how formatting can be changed
at selected points in the output by using conditional processing or e
mbedded IDM or IMM structured fields to
select a new Data Map or Medium Map for use with subsequent line-data records. This technique of switching
among maps in the Page Definition or Form Definition requires advance knowledge of all the output formats
that will be used and the appropriate Data Maps and Medium Maps must be coded. Using this technique also
means that formatting can be changed only on a page basis. When a new Data Map or Medium Map is
selected, processing of the current page is ended and the next line-data record appears on the following page.
It is not possible to reach an intermediate point on a page and then select a new Data Map for use in
processing the remaining records on the page.
These and other limitations often make it impossible to use external formatting objects to produce the exact
kind of output desired for a particular application. If the positioning and formatting needed for each page of
your application output is not known in advance, then the application data probably does not lend itself to
outboard formatting using a Page Definition. In these cases, you should consider generating fully-composed
documents, rather than line-mode or mixed-mode data.
Some examples of applications that have been developed using fully-composed output are:
• Utility bills containing line-by-line details and graphical representations of energy use for each customer
compared to the average for all customers
• Insurance policies with clauses, supplements, and detailed client-specific information that vary from one
policy to the next
• Financial statements containing sections that
describe specific investments, payments, or accounts, each of
which vary considerably from one statement to the next. Boxes and shading might be used to highlight
certain items of information and the location of the boxes or shaded areas can be anywhere on the page.
In many cases the text in the output from these applications is also printed using proportional fonts, which can
be more difficult to place using a Page Definition than fixed-pitch fonts. Note that in composed documents, the
PTX structured fields must be bracketed by Begin Presentation T ext (BPT) and End Presentation T ext (EPT)
structured fields. These structured fields are allowed only in fully composed documents. They cannot be used
to bracket a set of PTX structured fields to be included in a line-data file.
For a definition of composed documents, see the Mixed Object Document Content Architecture (MO:DCA)
Reference.
Programming Options
Software packages are available that can be used to generate fully composed MO:DCA documents. Another
option is to develop a custom application that produces only the specific output desired.
The MO:DCA data stream generated by such an application will be transformed directly into IPDS by the print
services program. No optimizing is performed on MO:DCA data. As a result, the application developer should
be aware of throughput considerations associated with the MO:DCA structure. Such considerations are
highlighted in this chapter in boxes titled “Programming Tip”.
Mixed Documents

## Page 82

64 AFP Programming Guide and Line Data Reference
Overall Document Structure
A fully composed document will conform to the structure shown on the right side of Figure 30 on page 170.
Each document is composed of one or more pages that have the format shown in Figure 32 on page 171.
Each page must begin with an Active Environment Group, but the actual objects that appear on the page (text,
image, graphics, or bar code) follow the AEG and can appear in any order. The application programmer works
with these objects, so an understanding of their format, use, and placement on the page can be helpful when
developing an AFP program.
Document Indexing
Indexing and attribute tagging structured fields may be added to documents to permit the selective retrieval of
specific pages and page groups for later viewing or printing. The MO:DCA architecture defines six structured
fields specifically for this purpose:
• Begin Document Index
• Index Element
• T ag Logical Element
• End Document Index
• Begin Named Page Group
• End Named Page Group
An index is bracketed by Begin Document Index and End Document Index structured fields. It may contain
Index Element (IEL) structured fields used to locate objects in a document and T ag Logical Element structured
fields, used to tag pages and page groups with attribute names and their values. Pages in a document may be
grouped for indexing using the Begin Named Page Group (BNG) and End Named Page Group (ENG)
structured fields.
In AFP environments, the document index is located external to the document.
Programmers are free to include Begin Named Page Group (BNG), End Named Page Group (ENG), and T ag
Logical Element (TLE) structured fields in the body of fully composed documents. However, the BNG, ENG,
and TLE structured fields are not supported for indexing in a line-data or mixed-data environment.
Document Links
Fully composed MO:DCA documents may contain logical links between document components. An example is
a hypertext link from an area on page N that contains a technical term to an area on page M that contains the
term's definition. Such links are specified using Link Logical Element (LLE) structured fields. LLE structured
fields are not supported in line-data or mixed-data documents.
Mixed Documents

## Page 83

Copyright © AFP Consortium 1994, 2018 65
