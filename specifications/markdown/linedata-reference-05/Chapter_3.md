# Chapter 3. Using a Page Definition to Print Data
One of the major enhancements provided by AFP to existing line-data applications is the ability to print
application-generated output in different formats without making any application changes. This function is
called outboard formatting, and is provided by the Page Definition resource object.
Page Definitions are supported by presentation services programs for z/OS, z/VM, z/VSE, AIX, Linux,
Windows, and iSeries.
A presentation services program uses Page Definitions to map the line data produced
by application programs. Page Definitions are not used when printing fully composed MO:DCA documents, as
formatting information is already included internally in these documents.
Page Definition names are provided in job control statements. Any print file can be associated with a Page
Definition by using the appropriate parameters in the job control statements for the applicable operating
system. See the reference publications for your print server and operating system for complete information.
## Common Examples of Page Definition Use
Many users want to take advantage of AFP capabilities that provide multiple-up printing or rotated printing
without any need to change the application that generates the output. Line data can be printed in any desired
format by creating a Page Definition that describes that format. Presentation services program
software
includes many Page Definitions that address common user needs, such as printing two-up output on 8.5 by 11
inch paper.
One example of multiple-up printing is provided by IBM-supplied Page Definition W12883. This Page Definition
prints two pages of 64 lines each, side by side in landscape mode on letter-sized paper. The data is printed at
8.2 lines per inch, so a 15-pitch or smaller font must be used to prevent the lines from overlapping.
Another example applies to users of continuous-forms impact printers who install a cut-sheet laser printer and
begin to use letter-size sheets of paper, rather than the larger forms found on impact printers. The impact
printers always printed in the ACROSS direction on paper whose width exceeded its length. But ACROSS
printing on cut-sheet paper is generally considered to mean printing parallel to the narrow edge, not the wide
edge. A Page Definition that prints in the DOWN direction, or in the landscape orientation, can be used to get
the same result with letter-size paper on a cut-sheet printer as with larger forms on an impact printer.
## Using More than One Page Definition
When a line-data file (such as a SYSOUT file produced by a System/370 application program) is printed, only
one Page Definition can be used to map the output format of that file. However, multiple copies of the file can
be printed, each one using a different Page Definition, if the appropriate job control statements are used. The
actual syntax varies depending on the operating system. An example for z/OS
is shown in Figure 5. By using a job stream similar to the one shown in the figure, multiple copies of a line-mode data set can be
generated, each one in a different format.
This example produces three different collated copies of the entire output file, each one formatted using a
different Page Definition. The same approach can be used with Form Definitions. Each OUTPUT statement
includes a different Form Definition name to invoke various options such as overlays, paper source, simplex, or
duplex.
Although only one Page Definition and Form Definition can be used when printing a single file, the internal
structure of Page Definitions and Form Definitions includes multiple sets of formatting rules. These sets of
rules are called Data Maps (also called Page Formats) in the Page Definition and Medium Maps (also called
copy groups) in the Form Definition. The Invoke Data Map and Invoke Medium Map structured fields can be


written in the output by an application program and used to switch between maps as printing proceeds. This
makes it possible for subsets of the file to be presented in different formats. Examples will be provided later in
this chapter and in Chapter 4, “Mixed Documents: Adding MO:DCA Structured Fields to Line Data”.
When a Page Definition containing more than one Data Map, or a Form Definition containing more than one
Medium Map, is used, the one that appears physically first in the resource object is selected as the default.
Figure 5. Printing a Data Set in z/OS Multiple Times with Different Page Definitions
//PRINTJOB JOB ...//STEP1 EXEC PGM=IEBGENER
//OUT1 OUTPUT PAGEDEF=PD1
//OUT2 OUTPUT PAGEDEF=PD2
//OUT3 OUTPUT PAGEDEF=PD3
⋮
//SYSUT2 DD SYSOUT=A,OUTPUT=(*.OUT1,*.OUT2,*.OUT3)
## Page Definition Structure
A Page Definition is required to compose line data into pages for printing on page printers. A Page Definition
consists of one or more Data Maps that define the page environment and provide instructions for mapping
each line of data to the page.
A Page Definition object can be referenced from a library defined to a presentation services program or can be
included inline at the beginning of a print file in some system environments. The structured fields in the Page
Definition conform to the MO:DCA architecture rules for structured fields. These rules are summarized in
# Chapter 5, “Structured Fields in a Page Definition and in Line Data” of this publication and are
formally defined in the Mixed Object Document Content Architecture (MO:DCA) Reference.
A Page Definition optionally can contain one or more Conditional Processing Control (CCP) structured fields.
Conditional processing permits the application programmer to define tests on selected data fields in the input
line records and to specify actions to take when the conditions of the test are satisfied. Figure 6 shows the
structure of a Page Definition.
Figure 6. Page Definition Structure
*  =  optional
s  =  can appear more than once
EPM
Data Map
s
IOBCCPResource
Environment
Group
*BPM
Page Definition
** s s


The structured fields and objects that compose a Page Definition are as follows. (Chapter 5, “Structured Fields
in a Page Definition and in Line Data” describes the structured fields.)
BPM (Begin Page Map)
Begins a Page Definition resource object. An optional token name may be specified to identify
the object.
## Resource Environment Group
The Resource Environment Group (REG) identifies complex resources that need to be loaded
in the presentation device before the pages that follow are processed.
CCP (Conditional Processing Control)
The CCP structured field is optional but can occur multiple times in the Page Definition. This
structured field appears at the beginning of the Page Definition, outside any of the Data Map
definitions, since it can be used by any Data Map to control switching among Data Maps in the
Page Definition and Medium Maps in the Form Definition. The CCP defines the condition to be
tested, the data value to be used to compare against the application data, the action to be
taken based on the result of the test, and when the action is to be taken.
A single CCP can make multiple tests, and Page Definitions can contain multiple conditions to
form complex testing sequences. These multiple conditions are reflected in multiple CCPs.
Each CCP in a Page Definition object has a unique identifier. The LND structured fields of a
Data Map use this identifier to invoke conditional processing. Each LND using conditional
processing specifies the length and position of the field in the application data record to be
tested. Different LNDs can invoke the same CCP multiple times in the same Data Map
definition.
See “Conditional Processing Control (CCP)” for details about the CCP structured
field.
IOB (Include Object)
The IOB structured field is optional but can occur multiple times in the Page Definition. The
IOB appears at the beginning of the Page Definition, following the CCP structured fields. The
IOB is processed when it is referenced by an LND or RCD. The reference consists of an ID
that is specified on the LND or RCD and that must match the ID on an IOB.
Data Map Object
Provides specific line definitions and mapping instructions for composing line data into a
presentation page format. A single Page Definition object may specify multiple Data Maps.
Different Data Maps in the Page Definition can
be selected by using the Invoke Data Map
structured field or by using conditional processing.
EPM (End Page Map)
Ends a Page Definition resource object. Any name specified in the EPM must match the name
specified in the BPM.


## Resource Environment Group
Figure 7 shows the structure of a Resource Environment Group (REG) in the Page Definition.
Figure 7. Resource Environment Group Structure for a Page Definition
*  =  optional
s  =  can appear more than once
Resource
Environment
Group
ESG
PPO
* s
MPO
* s
MDR
* s
BSG
A Resource Environment Group (REG), when specified in a Page Definition, is associated with a print file. The
REG is used to identify complex resources, such as high-resolution color images, that need to be downloaded
to the presentation device before the pages that follow are processed. The scope of a REG in the Page
Definition is the line-format data in the print file. When a print file contains multiple line-format data and mixed
data documents, the REG applies only to the line-format data documents in the print file. For a definition of
line-format data, see Figure 33. Line-format data may be bounded by explicit BDT/EDT pairs or
by implicit BDT/EDT pairs.
Architecture Note: To get the optimum performance benefit from the REG in the Page Definition, the print file
should contain only line-format data, and only large, complex objects should be mapped in the REG.
This will allow the line-format data to be treated as a single document, and the REG will cause all
mapped objects to be preloaded to the printer at the start of that document.
The REG in the Page Definition is not applied to MO:DCA
documents in the print file. The mapping of
resources in a REG is optional. Resources mapped in a REG must still be mapped in the AEG for the Data
Map that uses the resources. The structured fields that compose a Resource Environment Group are as
follows.
BSG (Begin Resource Environment Group)
Begins a Resource Environment Group. A token name may be specified to identify the REG.
MDR (Map Data Resource)
The MDR structured field is optional but can occur multiple times in a REG. The MDR specifies a resource that
is required for presentation. The resource is identified with a file name, the identifier of a begin structured field
for the resource, or any other identifier associated with the resource. The MDR may additionally specify a local
or internal identifier for the resource object. Such a local identifier may be embedded one or more times within
an object's data.


MPO (Map Page Overlay)
The MPO specifies overlay resources required for presentation. It is optional and can occur multiple times in a
REG.
PPO (Preprocess Presentation Object)
The PPO structured field is optional but can occur multiple times in a REG. The PPO specifies presentation
parameters for a data object that has been mapped as a resource. These parameters allow the presentation
device to preprocess and cache the object so that it is in presentation-ready format when it is included with a
subsequent include structured field in the document. Such preprocessing may involve a rasterization or RIP of
the object, but is not limited to that. The resource is identified with a file name, the identifier of a begin
structured field for the resource, or any other identifier associated with the resource. The referenced resource
and all required secondary resources must previously have been mapped with an MDR or an MPO in this
environment group.
Note: Preprocessing is not supported for objects that are included with structures that are outside the
document. Examples of such objects are medium overlays and PMC overlays, both of which are
included with structures in the Form Definition.
ESG (End Resource Environment Group)
Ends a Resource Environment Group.
## Data Map Structure
Figure 8 shows the structure of a Data Map, also called a Page Format.
Figure 8. Data Map Structure for a Page Definition
Data Map
Active
Environment
Group
Data Map
Transmission
Subcase
BDM EDM
Each Page Definition must include at least one Data Map. Structured fields in the Data Map accomplish the
page layout functions similar to those provided by FCBs used with non-AFP printers, but many additional
functions are available.
Each Data Map provides instructions for mapping line data to a page. The number of Data Maps that can be
included in a Page Definition is limited only by practical considerations such as whether the total size of the
Page Definition will be so large that it might not fit in a presentation services program
’s program storage. Each
Data Map in the Page Definition can contain entirely different information about how a page should appear, so
different Data Maps can be used from one page to the next with output produced by a line-data application.
The Data Maps in a Page Definition can select two types of line data processing:
• Traditional line data containing optional CCs and TRCs are processed using LNDs in the Data Map
Transmission Subcase.


• Record-format line data containing record IDs and optional CCs are processed using RCDs in the Data Map
Transmission Subcase.
All Data Maps in the Page Definition must specify the same line data processing.
The application can select which Data Map to use by writing an Invoke Data Map structured field in the output
file or by using conditional processing in the Page Definition to select a Data Map based on the value of a field
in the application data stream. Examples of using an IDM can be found in Chapter 4, “Mixed Documents:
Adding MO:DCA Structured Fields to Line Data”. Examples of conditional processing appear at
the end of this chapter.
The Data Map consists of two parts: the Active Environment Group and the Data Map Transmission Subcase.
Bracketing them are the Begin Data Map and End Data Map structured fields. The format of these structured
fields is as follows:
BDM (Begin Data Map)
Begins a Data Map. A one-to-eight character token name is required to identify the Data Map.
A one-byte code indicates whether the Data Map contains LNDs or RCDs. For the latter, the
BDM may contain a triplet that specifies the page margins,
a triplet that specifies page count
controls, and a triplet that specifies an encoding scheme .
EDM (End Data Map)
Ends a Data Map. Any name specified in the EDM must match the name specified in the BDM.
The Active Environment Group establishes the page environment, including page size, and can contain the
names of resources, such as fonts and page segments, that are to be mapped. The Data Map Transmission
Subcase specifies the position, orientation, color, and font selection for text, the identification of data fields to
be suppressed, any “fixed text” for the page, and any conditional processing tests and actions. It may also
specify objects to be included on the page.
## Active Environment Group Structure
Figure 9 shows the structure of an Active Environment Group (AEG) in the Data Map.
Figure 9. Data Map Active Environment Group Structure for a Page Definition
*  =  optional
s  =  can appear more than once
† = required for every IPO specified in a page
EAGBAG
PTD
*
OBP
(text)
*
OBD
(text)
*
PGDMPS
* s
MPO
* s†
MDR
s*
MCF
* s
Active
Environment
Group
PEC
*


The Active Environment Group contains structured fields that describe the features and characteristics of the
entire page. Page size, names of fonts, page segments, page overlays, and identifiers of objects to be used
are all part of the AEG. Because a page might consist entirely of text or entirely of image (as in a page
segment), most of these fields are optional. The only required structured field in the AEG is the Page
Descriptor, which contains the size of the page. If an application attempts to place data outside the page
boundaries, a positioning data-check error will be generated by the printer and reported by the presentation
services program
.
Font Lists
The Map Coded Font (MCF) and Map Data Resource (MDR) structured fields may be used in the AEG to list
fonts to be used on the page. If either is
used, each font is assigned a local identifier that is used in the body of
the page to select the font for a given line or field.
• Record-format processing
When the Page Definition specifies record-format processing, font specifications external to the Page
Definition are ignored.
Each font that is used must be mapped with an MCF or MDR in the AEG, and the MCF or MDR should
specify the encoding scheme with an Encoding Scheme ID (X'50') triplet. The values supported in the
ESidCP field of the Encoding Scheme ID triplet when printing page numbers with record-format processing
are:
– X'6100'—EBCDIC Presentation SBCS
– X'6200'—EBCDIC Presentation DBCS
– X'2100'—PC Data SBCS (ASCII)
– X'8200'—Unicode Presentation
The values supported in the ESidUD field of the Encoding Scheme ID triplet when printing page numbers
with record-format processing are:
– X'7200'—UTF-16, including surrogates; byte order is big-endian (UTF-16BE)
– X'7807'—UTF-8
The code points used for printing page numbers are:
– X'F0'–X'F9' for EBCDIC Presentation SBCS
– X'42F0'–X'42F9' for EBCDIC Presentation DBCS
– X'30'–X'39' for PC Data SBCS (ASCII) or UTF-8
– X'0030'–X'0039' for Unicode Presentation or UTF-16
• XML processing
When the Page Definition specifies XML Data processing, font specifications external to the PageDef are
ignored.
Each font that is used must be mapped with an MCF or MDR in the AEG, and the MCF or MDR must specify
the Encoding Scheme ID (X'50') triplet. The values supported in the ESidCP field of the Encoding Scheme ID
triplet when formatting XML data with a Page Definition are:
– X'6100'—EBCDIC Presentation SBCS
– X'2100'—PC Data SBCS (ASCII)
– X'8200'—EBCDIC Presentation
The values supported in the ESidUD field of the Encoding Scheme ID triplet when formatting XML data with
a Page Definition are:
– X'7200'—UTF-16, including surrogates; byte order is big-endian (UTF-16BE)
– X'7807'—UTF-8
The code points used for printing page numbers are:
– X'F0'–X'F9' for EBCDIC Presentation SBCS
– X'30'–X'39' for PC Data SBCS (ASCII) or UTF-8
– X'0030'–X'0039' for Unicode Presentation or UTF-16


Table Reference Characters
If the data to be printed contains 3800-style table reference characters, font information is required to map
each table reference character to the name of the font to be used when the data is printed. This information
can be provided either by font character-set names in job control statements accompanying the data to be
printed or by the fonts mapped in the AEG in the Page Definition. When no fonts are mapped in the AEG but
font character-set names are specified in the job control, the first character set specified corresponds to TRC 0,
the second to TRC 1, and so forth.
In z/OS, z/VM, and z/VSE,
the maximum number of characters allowed in the character-set name (CHARS)
parameter was four. This presented no problem when 3800 compatibility-mode character sets were used, as
none of them had names of more than four characters. But the typographic fonts available for page-mode
printing have eight-character names (including a two-character font prefix), and as a result cannot be coded in
the CHARS parameter. To associate a table reference character with an eight-character font name, a Page
Definition must be built that explicitly makes that mapping. A Page Definition is also required if five or more
fonts are to be used. Page Printer Formatting Aid (PPFA) is a software product available from IBM and Ricoh.
Figure 10 provides an example of PPFA source code that could be used to build a Page Definition that
addresses both requirements. Here, six table reference characters are defined and each one is associated
with a different font of the Sonoran Sans Serif family.
Figure 10. PPFA Code for Page Definition with Six TRCs to Select Typographic Fonts
SETUNITS LINESP 6 LPI;
PAGEDEF TRCXMP
WIDTH 8.2 IN HEIGHT 10.8 IN
REPLACE YES;
FONT ZERO A0758C; /* EIGHT-POINT SANS SERIF BOLD */
FONT ONE A0759C; /* NINE-POINT SANS SERIF BOLD */
FONT TWO A0750C; /* TEN-POINT SANS SERIF BOLD */
FONT THREE A075BC; /* 12-POINT SANS SERIF BOLD */
FONT FOUR A0559C; /* NINE-POINT SANS SERIF ROMAN */
FONT FIVE A0550C; /* TEN-POINT SANS SERIF ROMAN */
PAGEFORMAT JSTRC;
TRCREF 0 FONT ZERO;
TRCREF 1 FONT ONE;
TRCREF 2 FONT TWO;
TRCREF 3 FONT THREE;
TRCREF 4 FONT FOUR;
TRCREF 5 FONT FIVE;
PRINTLINE CHANNEL 1
POSITION .1 IN .2 IN REPEAT 20;
ENDSUBPAGE;
The rules for coding Table Reference Characters are different for page mode printers and for the 3800 running
in compatibility mode. Table 8 summarizes the differences.


Table 8. Use of TRCs in Page Mode and 3800 Compatibility Mode
Function Compatibility Mode Page Mode
Number of table reference characters
supported for a single output file
4 127
Valid hexadecimal values for table
reference characters
X'F0'–X'F3' X'F0'–X'F3' or X'00'–X'7E' for 4 or
fewer TRCs; X'00'–X'7E' for more
than 4 TRCs
How table reference characters are
associated with fonts
With character set names in job
control language
Same as compatibility mode for 4 or
fewer TRCs; with font names in the
AEG of the Data Map for more than 4
TRCs
For compatibility with 3800-1 applications, AFP print servers accept TRC values of X'F0' through X'F3' when
four or fewer TRCs are used. TRC values of X'00' through X'7E' are valid regardless of how many fonts are
used.
The Line Descriptor structured fields in the Data Map contain a bit that indicates which type of TRC to use.
PPFA and PMF set this bit to B'1' to indicate compatibility TRCs when four or fewer TRCs are described in the
Page Definition. These software programs set the bit to B'0' when more than four TRCs are used.
Note: Regardless of whether font character set names are specified in the job control or not, if fonts are
mapped in the AEG:
• Table reference character 0 (X'00' or X'F0') selects the first font mapped in the Active Environment
Group (AEG) of the Data Map; table reference character 1 (X'01' or X'F1') selects the second font
mapped in the AEG; and so on. This historically positional selection of fonts mapped in the AEG
precludes the use of a mixture of fonts mapped with MCFs and fonts mapped with MDRs. TRCs may
be used when all fonts in the AEG are mapped using MCFs only or MDRs only.
• A table reference character higher than 127 selects the first font mapped in the AEG of the Data Map.
• A table reference character higher than the number of fonts mapped defaults to the first font mapped
in the AEG of the Data Map.
Page Overlays
If the application uses the Include Page Overlay structured field to place overlays dynamically at any point on
the page, a Map Page Overlay structured field must be included in the Active Environment Group containing
the name of each overlay to be used.
Page Segments
A Map Page Segment structured field is not required in the Active Environment Group for each page segment
to be used by the application, but printer throughput improves if MPS structured fields are included. Mapped
page segments are loaded to the printer the first time they are included and are not reloaded on subsequent
invocations. These are called hard page segments. When a page segment is not mapped in the Active
Environment Group of the currently active Data Map, the page segment data is loaded to the printer every time
the segment is included by an Include Page Segment structured field. Such segments are called soft page
segments.
Data Objects
Data objects that are included with an IOB structured field, such as EPS objects and IOCA objects, can be
mapped using the MDR structured field. An MDR for such objects is not required in the AEG of the Data Map,


but might improve printer throughput if used. Mapped data objects are loaded to the printer the first time they
are included and are not reloaded on subsequent includes.
Color Management
A Color Management Resource (CMR) can be associated with a page, a data object included on the page with
an Include Object (IOB) structured field, or a GOCA or BCOCA object generated by the page definition.
Associating a CMR is accomplished by using the MDR structured field to reference the CMR as a resource in
the AEG for the Data Map. The CMR reference will be identified as targeted to the page or data object. If a
data object is included on a page with an Include Object (IOB) structured field or generated by the page
definition, a CMR can be associated with this object by specifying the name of the CMR on the IOB, LND,
RCD, or XMD as an external resource reference and then referencing the CMR with a MDR in the AEG of the
Data Map. See the Mixed Object Document Content Architecture (MO:DCA) Reference and Color
Management Object Content Architecture Reference for more information on Color Management in an AFP
environment.
Structured Fields
The structured fields that comprise the Active Environment Group in a Data Map are as follows: (See the
Mixed Object Document Content Architecture (MO:DCA) Reference for a complete description of these
structured fields.)
BAG (Begin Active Environment Group)
Begins an Active Environment Group. A token name may be specified to identify the AEG.
PEC (Presentation Environment Control)
The Presentation Environment Control structured field specifies parameters that affect the
rendering of presentation data and the appearance that is to be assumed by the presentation
device. The scope of the Presentation Environment Control structured field is the page
generated using the Data Map that contains this structured field.
Note: The PEC structured field in the AEG for the Data Map is only used to specify the
rendering intent for the page using the Rendering Intent (X'95')
triplet; all other PEC
triplets are ignored.
MCF (Map Coded Font)
Identifies each font resource object used in the page and assigns each a 1-byte local identifier.
The strategic format of the MCF structured field is called the MCF-2; the coexistence format is
called the MCF-1. For any reference to MCF throughout this book, refer to the Mixed Object
Document Content Architecture (MO:DCA) Reference for further description.
MDR (Map Data Resource)
Identifies data object resources that are to be downloaded to the printer for subsequent use in
the page.
MPO (Map Page Overlay)
Identifies overlay object resources used in the page. Each overlay referenced by an Include
Page Overlay structured field in the page must be mapped in an MPO structured field.
MPS (Map Page Segment)
Identifies page segments used on the page that are to be downloaded to the printer.
PGD (Page Descriptor)
Specifies the units of measure for the page presentation space and the size of the page. This
parameter is required.
OBD (Object Area Descriptor)
Specifies the units of measure for the text output area and the size of the area. The OBD is
optional. If specified, the units of measure must be the same as those specified for the page in
the PGD, and the output area size must be the same size as the page.


OBP (Object Area Position)
Specifies the origin and orientation of the text output area on the page, as well as the origin
and orientation of the text presentation space on the output area. The OBP is optional. If
specified, the origin of the output area and the origin of the text presentation space must be
the same as the origin of the page and the orientation of the output area and of the text
presentation space must be 0°.
PTD (Presentation Text Descriptor)
Specifies the units of measure for the text presentation space and the size of the space. For
composed page text objects enveloped with BPT and EPT structured fields, the PTD may also
specify initial text conditions for the text object. The PTD is required in the AEG if the page
contains presentation text objects. If the PTD is specified, the text presentation space units of
measure and size must match the page presentation space units and size specified in the
PGD. This descriptor has two formats:
• PTD-1, formerly called CTD, specifies only the text presentation space units of measure and
size.
• PTD-2 specifies the text presentation space units of measure, expands the fields containing
the presentation space extents from two bytes to three bytes, and allows initial text
conditions to be specified for composed page text objects enveloped with BPT and EPT .
These initial text conditions are set whenever a BPT structured field starts a new text object
and may be specified on the PTD-2 using the PTOCA control sequences shown in Table 9.
Note that whenever a BPT is encountered in the data stream, AFP servers
set the following
default page-level initial text conditions before the PTD initial conditions are set:
Parameter Value
Initial (I,B) Presentation Position (0,0)
Text Orientation 0°,90°
Coded Font Local ID X'FF' (default font)
Baseline Increment 6 lpi
Inline Margin 0
Intercharacter Adjustment 0
Text Color X'FFFF' (default color)
EAG (End Active Environment Group)
Ends the AEG. Any name specified in the EAG must match the name specified in the BAG.
Table 9. Initial Text Conditions in PTD-2
Initial Text Condition Parameter Control Sequence
Baseline Increment Set Baseline Increment (SBI)
Coded Font Local ID Set Coded Font Local (SCFL)
Initial Baseline Coordinate Absolute Move Baseline (AMB)
Initial Inline Coordinate Absolute Move Inline (AMI)
Inline Margin Set Inline Margin (SIM)
Intercharacter Adjustment Set Intercharacter Adjustment (SIA)
Extended Text Color Set Extended Text Color (SEC)
Text Color Set Text Color (STC)
Text Orientation Set Text Orientation (STO)


Data Map Transmission Subcase
A Data Map Transmission Subcase can contain LNDs, RCDs, or XMDs, but not a mixture.
Data Map Transmission Subcase with LNDs
Figure 11 shows the structure of a Data Map Transmission Subcase with LNDs.
Figure 11. Data Map Transmission Subcase with LNDs
LND
Data Map
Transmission
Subcase
*  =  optional
s  =  can appear more than once
BDX
DXD FDSLNC FDX
EDX
* * *s s
The principal function of the Data Map Transmission Subcase with LNDs is to map the lines of data to the
page. Each line on a page is represented by a Line Descriptor structured field, which contains the horizontal
and vertical position on the page where the line is to appear. Rotation and font information is also contained in
the Line Descriptors, as is the association with any conditional processing controls used to test data on the
current line. The Line Descriptor structured fields are generally used to map lines of text, but they can also be
used to position page segments or page overlays or to present line data code points as a bar code. An Include
Page Segment or Include Page Overlay structured field that
contains a value of X'FFFFFF' in the X-axis
positioning parameter, the Y-axis positioning parameter, or both indicates that the page segment or overlay is
to be placed at the X-axis or Y-axis position specified by the current LND.
Functions that originated with older printers are also implemented in Line Descriptors. If carriage control skipto-channel codes are used with the data, each channel code must be defined in at least one Line Descriptor
(LND) in the Page Definition; this LND defines the page position associated with that channel code number.
Carriage control characters that call for double spacing, triple spacing, or space suppression cause a
presentation services program
to skip over Line Descriptors or reuse the same Line Descriptor in the Data
Map, in a manner analogous to FCBs used with impact printers. Either ANSI or machine code carriage controls
can be used, but whichever type is selected must be used for the entire print file. Part of the data cannot
contain ANSI carriage controls and another part contain machine code carriage controls. In addition, if carriage
control characters are used, every record in the print file must begin with a carriage-control byte, even if it is
only “print with single spacing”. The type of carriage control being used must be identified in the job control
associated with the print file, just as in a non-AFP environment.


The following new functions are provided in Page Definitions that are not available in FCBs. These functions
are triggered by information in the Line Descriptor structured field.
• Field formatting, which is the ability to separate specific fields in a line-data record and place them anywhere
on a page. Field size, orientation, placement, color, and font to be used are specified in the Line Descriptor.
Fixed text may be specified in the Line Descriptor and added to data from application programs.
• Multiple-up printing, which is the ability to divide the page into sections, each with the appearance of a
smaller page. This can be accomplished by defining subpages, which are subsets of the page, using Line
Descriptors.
• Conditional processing, which is the ability to define tests on certain characters of the line data and perform
actions based on the results of the tests.
• Resource object include, which is the ability to include an overlay or page segment and position it relative to
the current line.
• Bar code generation, which is the ability to select a field in a record and present it as a bar code.
• Specification of spot (highlight) colors or process colors for a record or field.
• Object include, which is the ability to include a data object relative to the current line and change its position,
size, and orientation.
Each Line Descriptor formats only one line-data record. The same record may be formatted multiple times on a
page using the “reuse record” function in the Line Descriptor. Since Line Descriptors are contained in a Data
Map Transmission Subcase whose scope is a page, they cannot be used to place a single record on more than
one page.
The text suppression function in AFP is an implementation of the 3800-1 COPYMOD function. A combination
of information in the Line Descriptor structured field in the Page Definition and the Medium Modification Control
structured field in the Form Definition, it provides the same function as “spot carbons” with impact printers,
where multi-part forms with carbon paper were often used. Some of these applications required that selected
fields not be printed on certain copies of the output (for example, internal prices should not appear on customer
copies). The same effect can be obtained with AFP printers by defining fields as suppressible in the Page
Definition and then suppressing these fields on certain copies in the Form Definition.
Data Map Transmission Subcase with RCDs
A Data Map Transmission Subcase with RCDs has the same structure as one with LNDs except that the LNDs
are replaced with RCDs. A Data Map Transmission Subcase with RCDs is used to process record-format line
data instead of traditional line data.
Each record in the data contains a 1 to 250-byte Record ID and is processed by the RCD in the Data Map
Transmission Subcase that contains a matching Record ID. If a CC byte is specified in the record, it must
precede the Record ID and is not part of the compare. In addition to providing LND-like functions such as data
position, orientation, font selection, coloring, bar code generation, and object includes, RCDs support
additional functions like headers, trailers, page numbering, and graphics generation.
Data Map Transmission Subcase with XMDs
A Data Map Transmission Subcase with XMDs has the same structure as one with LNDs except that the LNDs
are replaced with XMDs. A Data Map Transmission Subcase with XMDs is used to process XML data instead
of traditional line data.
To process XML data, the processor must build a Qualified Tag by concatenating XML start tags. These
Qualified Tags are then compared to Qualified Tags in the Data Map. The Qualified Tags in the Data Map are
built by specifying a separate XML Name (X'8A')
triplet on each XML Descriptor (XMD) for each XML Start tag
that has to be traversed in order to process the content of an XML element. If an XMD with a matching
Qualified Tag is found, the content of the XML element is formatted with that XMD. If an XMD with a matching


Qualified Tag is not found, processing resumes with the next start tag. Note that as the processor parses the
XML, it must buffer the XML start tags traversed in order to have a current Qualified Tag. Each time an end tag
is found, the last matching start tag is removed. For example, in the following XML hierarchy:
<person>
<name>
<first>John</first>
<last>Doe</last>
</name>
</person>
The Qualified Tag for the element <first> is {person name first}. The Qualified Tag for the element <last> is
{person name last}. Notice that the tag for element <first> has been removed since its end was received prior
to the start tag for element <last>. To process this “current” Qualified Tag, an XMD in the Data Map would also
have a Qualified Tag made up from separate XML Name (X'8A')
triplets for each XML start tag. This Qualified
Tag for this XMD would match the current Qualified Tag and the XMD would be used to present the XML
element content “John” on the page.
In addition to providing LND-like functions such as data position, orientation, font selection, coloring, bar code
generation, and object includes, XMDs support additional functions like headers, trailers, page numbering, and
graphics generation.
Data Map Transmission Subcase Structure
The structured fields that compose the Data Map Transmission Subcase are as follows. (See Chapter 5,
“Structured Fields in a Page Definition and in Line Data” for a formal description of these
structured fields.)
BDX (Begin Data Map Transmission Subcase)
Begins the Data Map Transmission Subcase.
DXD (Data Map Transmission Subcase Descriptor)
Contains constant data.
LNC (Line Descriptor Count)
Specifies the number of Line Descriptor (LND) or Record Descriptor (RCD) structured fields in
the Data Map Transmission Subcase.
LND (Line Descriptor)
Specifies how the current line data should be processed. The Data Map Transmission
Subcase can contain more than one LND and each LND points to the next LND used.
When the print file does not use carriage control characters, processing begins with the first
LND structured field. When the data record contains a carriage control character that specifies
a channel code, the first LND containing that channel code is selected to control processing. If
there is no LND in the Data Map containing a channel code matching the channel code
specified in the data record, an error is generated.
If an LND specifies that a conditional processing test should be performed on the current
record, the LND specifies the field to be tested and the ID of the Conditional Processing
Control (CCP) structured field that contains the test criteria and actions. Such LNDs do not
place data on the page.
When the LND specifies that fixed text data should be printed, the data is located in the Fixed
Data Text (FDX) structured field.
RCD (Record Descriptor)
Specifies how the record with matching record ID should be processed. The Data Map
Transmission Subcase can contain more than one RCD.


With RCD processing, carriage controls in the data record are ignored. Processing begins with
the first RCD that matches the Record ID of the first record. If a matching RCD is not found, an
error is generated.
If conditional processing is to be performed on the current record, the RCD specifies the field
to be tested and the ID of the CCP that contains the test criteria and actions. Such RCDs are
called conditional processing RCDs and do not place data on the page.
When the RCD specifies that fixed text data should be printed, the data is located in the Fixed
Data Text (FDX) structured field.
XMD (XML Descriptor)
Specifies how the data with matching start tags should be processed. The Data Map
Transmission Subcase can contain more than one XMD.
With XMD processing, carriage controls and table reference characters in the data record are
not allowed. Processing begins with the first XMD that matches the start tag. If a matching
XMD is not found, the data is ignored and processing resumes with the next start tag.
If conditional processing is to be performed on the current element, the XMD specifies the field
to be tested and the ID of the CCP that contains the test criteria and actions. Such XMDs are
called conditional processing XMDs and do not place data on the page.
When the XMD specifies that fixed text data should be printed, the data is located in the Fixed
Data Text (FDX) structured field.
FDS (Fixed Data Size)
If constant text is to be included in line format data, this structured field is required. The FDS
specifies the number of bytes of the text that will be found in the following Fixed Data Text
(FDX) structured fields. One FDS structured field is used for all FDX structured fields.
FDX (Fixed Data Text)
Must follow an FDS structured field and contains data that can be added to or used instead of
line data. More than one FDX structured field is allowed.
EDX (End Data Map Transmission Subcase)
Ends the Data Map Transmission Subcase. Any name specified in the EDX must match the
name specified in the BDX.
## Field Formatting—LND Processing
A Page Definition may be used to break line-data records into fields that are formatted individually. This is done
by building a chain of LND structured fields called a reuse chain.
The first LND used to process an input record is called the base LND. If this LND specifies flag byte bit 6=B'1'
(reuse record), it is also the head of a reuse chain and points to the next LND in the chain with bytes 16–17.
This next LND is used to select and process a field in the same record. If additional field processing is required,
the next LND also specifies flag byte bit 6=B'1' and points to another LND to select and process another field in
the record, and so on. All LNDs in a reuse chain are called reuse LNDs. The last LND in a reuse chain
specifies flag byte bit 6=B'0' and bytes 16–17=X'0000'. This LND terminates the reuse chain.
## Field Formatting—RCD Processing
Field formatting is also supported when RCDs are used to process record-format line data. The first RCD used
to process an input record is called a record RCD. It is identified by RCDFlgs bit 6=B'0' and RCDFlgs bit 11=
B'0'. If the FLDrcd parameter in a record RCD is non-zero, it specifies the RCD number of a field RCD that is to
be used to process a field in this record. A field RCD is identified by RCDFlgs bit 6=B'1' and RCDFlgs bit 11=


B'0'. Multiple field RCDs can be chained to a record RCD in this manner. The last field RCD in this chain must
specify FLDrcd= X'0000'.
## Field Formatting—XMD Processing
Field formatting is also supported when XMDs are used to process XML data. The first XMD used to process a
start tag is called an element XMD. It is identified by XMDFlgs bit 6=B'0', XMDFlgs bit 10=B'0', and XMDFlgs
bit 11=B'0'. If the FLDxmd parameter in an element XMD is non-zero, it specifies the XMD number of a field
XMD that is to be used to process a field in this element data. A field XMD is identified by XMDFlgs bit 6=B'1'
and XMDFlgs bit 11=B'0'. Multiple field XMDs can be chained to an element XMD in this manner. The last field
XMD in this chain must specify FLDxmd=X'0000'.
## Using Conditional Processing in a Page Definition
The conditional processing function allows a different Data Map in the current Page Definition, a different
Medium Map in the current Form Definition, or both to be selected for use with the next page based on
characteristics of the application data stream. This provides a way to change Data Maps or Medium Maps as
necessary without having to make application programming changes. The new format can take effect either
before or after a specified line or a specified subpage. With LND-based Data Maps, a subpage is a subset of
the lines presented on a page. Subpages are defined in the Data Map by the user when coding a Page
Definition and are often used to create multiple-up Page Definitions. Subpages are ignored with RCD-based
and XMD-based Data Maps, that is, each page is a single subpage.
Conditional processing is implemented by a combination of structured fields in the Page Definition. CCP
structured fields specify the test to be performed and action to be taken, while LND, RCD, and XMD structured
fields include the location and length of data fields to be tested and a pointer to the CCP . When the conditions
of a test are satisfied, the actions that can be taken are switching to a new Data Map, switching to a new
Medium Map, or both, either before or after the current line or subpage is printed. When the action takes effect,
printing of the current page ends. If a new Data Map is selected, printing resumes on a new side of a sheet of
paper. If a new Medium Map is selected, printing resumes on a new physical sheet. As a result, it is not
possible to format part of a page with one Data Map and format another part of the same page with a different
Data Map. Note that the Medium Map might
specify the N-up function, which places multiple pages into
partitions on a sheet side. When N-up is specified, switching to a new Data Map or a new Medium Map might
cause printing to resume in a new partition instead of on a new sheet-side or a new sheet. For more
information on N-up printing, see the Mixed Object Document Content Architecture (MO:DCA) Reference.
Conditional processing can be used to format subsets of a single output file differently or it can be used to force
an eject to a new page or sheet based on some condition. Examples of these uses of conditional processing
are shown below.
Using Different Formats for Different Subsets of Output
A common example of this is an application program requirement to print detail pages of a report in a different
format from summary pages. Assuming that a known field in the application data stream can be tested to
identify the detail records and the summary records, a Page Definition with two Data Maps can be constructed
to provide the different formats without changes to the application program. Figure 12 assumes a
file where each record has identifying information in bytes 2 through 5. Records with the characters DETL in
these positions are to use Data Map PF1 and Medium Map CG1. Records with the characters SUMM in these
positions are to use Data Map PF2 and Medium Map CG2. Page Printer Formatting Aid (PPFA) is a software
product available from IBM and Ricoh.
Figure 12 shows the PPFA code that generates a Page
Definition to test these positions and to print the detail pages in the ACROSS direction and the summary pages
in the DOWN direction.


Figure 12. PPFA Code for Page Definition with Conditional Processing
SETUNITS 1 IN 1 IN ;
LINESP 6 LPI ;
PAGEDEF CPSAM1 REPLACE YES ;
PAGEFORMAT PF1
WIDTH 8.2 HEIGHT 10.0
DIRECTION ACROSS ;
PRINTLINE REPEAT 1
CHANNEL 1 ;
CONDITION TEST1 START 2 LENGTH 4
WHEN EQ 'SUMM'
BEFORE SUBPAGE
COPYGROUP CG2
PAGEFORMAT PF2 ;
PRINTLINE REPEAT 59 ;
ENDSUBPAGE ;
PAGEFORMAT PF2
WIDTH 10.0 HEIGHT 8.2
DIRECTION DOWN;
PRINTLINE REPEAT 1
CHANNEL 1 ;
CONDITION TEST2 START 2 LENGTH 4
WHEN EQ 'DETL'
BEFORE SUBPAGE
COPYGROUP CG1
PAGEFORMAT PF1 ;
PRINTLINE REPEAT 34 ;
ENDSUBPAGE ;
Conditionally Skipping to a New Page or a New Sheet
Another common use of conditional processing is to skip to a new page or a new sheet when a control break in
the output data occurs. This control break might be the start of a new customer number, a new department, or
some other change in the output that requires starting on a new page, or on a new sheet of paper.
Such cases include applications that print using multiple-up format, where having data for one department
appear on the left-hand half of the page while having data for a different department appear on the right-hand
half of the page is not desirable. This possibility can be avoided by having the application force a completely
new page when the department number changes. In PPFA, this condition is coded with the NEWSIDE
parameter.
Applications that print duplex output (using both front and back of the form) probably must force a new physical
sheet at a control break in the data, to avoid having output for two different user destinations on the front and
back of the same sheet. In PPFA, this condition is coded with the NEWFORM parameter. For output printed
multiple-up on both sides of the sheet, the NEWFORM parameter forces a new page and a new sheet. Coding
both is not necessary.
A new page or sheet can be forced when using a Page Definition with only one Data Map or a Form Definition
with only one Medium Map. Conditional processing can be used to re-invoke the currently active Data Map or
Medium Map when the condition is satisfied. This is what happens when NEWSIDE or NEWFORM is coded in
PPFA. More than one Data Map or Medium Map is required only if subsets of the output are to be formatted or
handled differently based on the defined condition. Note that if the Medium Map specifies the N-up function,
the new “sheet” might
actually be a new N-up partition on the sheet.


The example in Figure 13 shows PPFA source code to accomplish a skip to a new page when the department
number in character positions 1 through 3 changes.
Figure 13. PPFA Code for Page Definition to Skip to New Page
SETUNITS 1 IN 1 IN ;
LINESP 8 LPI ;
PAGEDEF NEWPG REPLACE YES
WIDTH 10.5 HEIGHT 8.1
DIRECTION DOWN ;
PAGEFORMAT NEWPG ;
PRINTLINE REPEAT 40
CHANNEL 1
POSITION .5 TOP ;
CONDITION TEST1 START 1 LENGTH 3
WHEN CHANGE
BEFORE SUBPAGE
NEWSIDE ;
ENDSUBPAGE ;
The example in Figure 14 is similar; but in this case the skip is to a new sheet, or form, where printing of the
output is resumed.
Figure 14. PPFA Code for Page Definition to Skip to New Sheet
SETUNITS 1 IN 1 IN ;
LINESP 8 LPI ;
PAGEDEF NEWFM REPLACE YES
WIDTH 10.5 HEIGHT 8.1
DIRECTION DOWN ;
PAGEFORMAT NEWFM ;
PRINTLINE REPEAT 40
CHANNEL 1
POSITION .5 TOP ;
CONDITION TEST1 START 1 LENGTH 3
WHEN CHANGE
BEFORE SUBPAGE
NEWFORM ;
ENDSUBPAGE ;
Processing Line Data with Shift-Out/Shift-In (SOSI) Controls
Shift-out (SO-X'0E') and shift-in (SI-X'0F') controls are used to signal the beginning and end, respectively, of a
string of double-byte code points that are to be rendered using characters from a double-byte font or rendered
as a QR Code bar code symbol. SOSI processing is specified in the print request and applies to both fixed text
fields and the input line data.
SOSI processing for text output is supported by two modes of font selection in the PageDef. Both modes may
be intermixed in the same Page Map.
• Record-based or field-based font selection. In this mode, the font to be used following an SO can be uniquely
selected for each record or field by specifying a non-zero shift-out font local ID in byte 26 of the LND or byte
34 of the RCD that is used to process the line data. The font used following an explicit SI is then always the
primary font specified in byte 10 of the LND or byte 23 of the RCD and use of this font must be enabled with
flag bit 4 = B'1'. An error condition exists if flag bit 4 = B'0'. Note that an implicit SI is assumed at the start of


every record. This selects the primary font specified in byte 10 of the LND or byte 23 of the RCD, if it is
enabled with flag bit 4 = B'1'.
• Page-based font selection. In this mode, the font to be used following an SO is the same for all records and
fields on the page. LND byte 26 or RCD byte 34 is set to X'00' and the font used following an SO is the font
mapped to local ID X'02' in the AEG for the Data Map. The font used following an explicit SI is the font
mapped to local ID X'01' in the AEG. Note that the font used following the implicit SI at the start of every
record is still the primary font specified in byte 10 of the LND or byte 23 of the RCD, as long as it is enabled
with flag bit 4 = B'1'. Both an SO font and an SI font must be mapped in the AEG with the proper local IDs.
The presence of only one mapped font is an error condition. If no fonts are mapped in the AEG, a
presentation system default may be used.
The SO and SI controls used to delimit the strings of double-byte code points are not valid printable characters
nor are they valid QR Code bar code data characters. The line data processor must either remove or convert
the SO and SI characters to blanks according to the selection of SOSI processing mode in the print request.
For text output, the SOSI processing modes are described as follows:
SOSI mode Action
SOSI1 Specifies that each shift-out, shift-in control is to be converted to a blank and a Set Coded
Font Local text control.
• Each SO (X'0E') is replaced with a blank (X'40'), followed by a PTOCA structure that
contains a Set Coded Font Local text control for the font mapped to local ID X'02'.
• Each SI (X'0F') is replaced with a PTOCA structure that contains a Set Coded Font Local
text control for the font mapped to local ID X'01', followed by a blank (X'40').
SOSI2 Specifies that each shift-out, shift-in control is to be converted to a Set Coded Font Local text
control.
• Each SO (X'0E') is replaced with a PTOCA structure that contains a Set Coded Font Local
text control for the font mapped to local ID X'02'.
• Each SI (X'0F') is replaced with a PTOCA structure that contains a Set Coded Font Local
text control for the font mapped to local ID X'01'.
SOSI3 Specifies that the shift-in control is to be converted to a Set Coded Font Local text control and
two blanks. A shift-out control is to be converted to a Set Coded Font Local text control.
• Each SO (X'0E') is replaced with a PTOCA structure that contains a Set Coded Font Local
text control for the font mapped to local ID X'02'.
• Each SI (X'0F') is replaced with a PTOCA structure that contains a Set Coded Font Local
text control for the font mapped to local ID X'01', followed by two blanks (X'4040').
SOSI4 Specifies that each shift-out, shift-in control is to be skipped and not counted when calculating
offsets for the print data. The conversion of the shift-out and shift-in controls for SOSI4 is the
same as for SOSI2.
Note: SOSI4 is used when double-byte character set (DBCS) text is converted from ASCII to
EBCDIC. When SOSI4 is specified, the page definition offsets are correct after
conversion; therefore, the user does not need to account for SOSI characters when
computing offsets to various fields within the data.
For QR Code bar code output, the data is converted to Shift/JIS ASCII data. The SO and SI control characters
are removed and are not converted to blanks and Set Coded Font Local text controls as they are for text
output. The converted data is then used as the QR Code bar code data. This processing is the same for all
SOSI processing modes. For SOSI4, each shift-out and shift-in control is not counted when computing offsets
to various fields within the data.
When processing data with SOSI controls, the processor assumes that each line or record starts with singlebyte code points. This means that the data is scanned for SOSI controls one byte at a time. After processing a
shift-out control, the data is scanned two bytes at a time. The first byte of each pair is checked to see if it is a


shift-in control. If a line is to start with double-byte data, the first byte in the line must be a shift-out control. This
is due to the fact that single-byte code points are assumed at the start of each line. The processor also
assumes that each field (including fixed text fields) starts with a single-byte code point when processing in
SOSI1, SOSI2, and SOSI3 modes. The processing of fields in SOSI4 mode is different than the other SOSI
modes. SOSI4 processing requires that SO and SI controls not be counted as part of the field positioning.
Therefore, the record is scanned to keep track of the last SO or SI prior to the field start position. The SO or SI
control found prior to the field is used to determine if the field starts with a single-byte or double-byte code
point.
Notes:
1. Since table reference characters (TRCs) also might
use the fonts mapped to local IDs X'01' and X'02' in the
AEG of the Data Map, it is recommended that the mixing of SOSI controls and TRCs be avoided when
using page-based font selection.
2. Shift-out/Shift-in controls are not used in Unicode data to signify a shift into and out of DBCS processing.
Therefore, it is not possible to switch processing between Unicode encoding and single-byte (SBCS)
encoding within a line data field or record. That is, when a line data field is processed with a Page
Definition, either the whole field is treated as Unicode-encoded or none of it is treated as Unicodeencoded.
3. When building bar codes from line data, SOSI input data is not appropriate for bar code symbologies other
than QR Code. Refer to the Bar Code Object Content Architecture Reference for information about the
valid encoding for each bar code.
4. When using Shift-out/Shift-in controls with Record Format data using delimited fields, if the field is to print
using double-byte code points, the SO control must follow the delimiter for the field.
5. Shift-out/Shift-in controls are not supported when processing XML data.
Printing Bar Codes with a Page Definition
A Page Definition can be used to print a bar code symbol using data from one of the following places:
• Line data record
• XML element
• Field in the line data record
• Field in the XML element
This is done by specifying a Bar Code Symbol Descriptor (X'69') triplet on the LND, RCD, or XMD. The
presence of this triplet indicates to the presentation services program that the selected field is to be presented
as a bar code symbol. The position specified by the LND, RCD, or XMD indicates the position of the symbol
origin and the text orientation specified by the LND, RCD, or XMD indicates the rotation of the symbol with
respect to the page X p-axis. The data used for the bar code can be obtained from multiple fields using the
Concatenate Bar Code Data (X'93') triplet. This triplet contains a start-new-symbol flag to indicate the
beginning of a series of concatenated fields; therefore, multiple bar code symbols can be created on a page by
reusing RCD or XMD structured fields with this flag set.
Note that the text suppression function is not supported when the field is presented as a bar code. This
function is supported only for text and is ignored for other data. Note also that the bar code function is not
supported on LNDs, RCDs, or XMDs that specify conditional processing (flag bit 11 = B'1'); if the Bar Code
Symbol Descriptor triplet is specified it is ignored.
For improved printer throughput, all bar code symbols on a page that use the same descriptor and that specify
the same rotation are grouped into a single bar code object by the presentation services program before the
page is presented. To align the object presentation space X
bc-axis with the X-axis of the bar code symbol, the
origin of the object presentation space is selected as one of the four corners of the page based on the LND,
RCD, or XMD text orientation. The bar code presentation space origin is therefore made coincident with the
current text coordinate system (I,B) origin. For example, if an LND specifies a (90°,180°) text orientation, the


symbol rotation is 90° and the origin of the bar code object presentation space is the top-right corner of the
page. The extents of the bar code object presentation space are determined by the extents of the page
presentation space. For example, if the origin of the object presentation space is the top-right corner of the
page, the X-extent of the object presentation space is the Y p-extent of the page and the Y-extent of the object
presentation space is the X p-extent of the page. The symbol origin offset from the object presentation space
origin and from the current text (I,B) coordinate system origin is specified by the IPos and BPos parameters of
the LND, RCD, or XMD. The units of measure for the bar code object presentation space, used for determining
symbol origin offsets, are the same as those defined on the page (X p,Yp) presentation space in the PGD
structured field of the Active Environment Group (AEG) for the Data Map.
The presentation services program also defines an object area presentation space for the object that is
identical in size, position, and units of measure to the bar code presentation space. The rotation of the object
area presentation space about the page X p-axis is the same as the rotation of the bar code symbol about this
axis, which is the same as the text orientation specified in the LND, RCD, or XMD.
Printing Graphics with a Page Definition
A Page Definition can be used to generate simple graphics primitives such as lines, boxes, circles, and ellipses
when record-format line data is processed with RCDs or XML data is processed with XMDs. This is done by
specifying a Graphics Descriptor (X'7E') triplet on the RCD or XMD. This triplet might
specify a complete
graphics primitive, as is always the case with a full arc, or it might specify the beginning or end of the primitive.
For improved printer throughput, all graphics primitives on a page with the same descriptor and the same
orientation are grouped into a single graphics object by the presentation services program before the page is
presented. The origin for the graphics object area is one of the four corners of the page as determined by the
text orientation specified in the TxtOrent parameter of the RCD or XMD and is therefore coincident with the
current (I,B) origin. The rotation of the graphics object area about the page X
p-axis matches the rotation of the
current text (I,B) coordinate system. For example, with a (90°,180°) text orientation, the object area rotation is
90°. The extents of the object area match the extents specified in the Margin Definition (X'7F') triplet on the
BDM. The position of the graphics primitive in the (I,B) coordinate system therefore maps to the same position
in the object area (X
oa,Yoa) coordinate system. This in turn is mapped to a graphics window whose upper left
corner is at the graphics presentation space (GPS) origin and whose extents match those of the object area.
The upper left corner of the graphics presentation space window is therefore also coincident with the current
(I,B) origin. The mapping between graphics window and object area is position and trim.
For example, if the RCD specifies a (90°,180°) text orientation, the upper left corner of the graphics window is
at the top-right corner of the page and graphics primitives in this object are rotated 90° with respect to the page
X
p axis. The X-extent of the graphics window is the Y-extent of the graphics object area and the Y-extent of the
graphics window is the X-extent of the graphics object area. The units of measure for the graphics presentation
space and for the graphics object area are the same as those defined on the page (X
p,Yp) presentation space
in the PGD structured field of the Active Environment Group (AEG) for the Data Map.
Relative Baseline Positioning—LND Processing
Records, fields, and objects are positioned using the print position specified in bytes 2–5 of the LND structured
field. These bytes normally specify an absolute offset from the origin of the current text (I,B) coordinate system.
With relative baseline positioning, LND bytes 4–5 may be used to specify a baseline position relative to an
established baseline position. This allows records, fields, and objects to be positioned (in the baseline
direction) relative to a previous record, field,
or object.
Relative baseline positioning is used when LND flag byte bit 13 is set to B'1'. The relative offset may be positive
or negative and is measured using the current I,B coordinate system. Note that the origin of the current I,B
coordinate system depends on the current text orientation. The baseline position used as a reference for the
relative offset depends on whether the LND that specifies relative positioning is a base LND and on whether a


page or subpage boundary was crossed since the last LND was used to print. The baseline position used as a
reference for the relative offset is determined as follows:
• For base LNDs, offsets are defined relative to the last base LND processed, either by printing or by spacing.
However, if a page or subpage boundary was crossed after the last base LND was processed, offsets are
defined relative to the first LND for the page or subpage.
• For reuse LNDs other than base LNDs, the offset is defined relative to the last LND used to print.
• If the first LND of a Data Map specifies relative positioning, the offset is defined relative to the current text
coordinate system origin (I=0,B=0), using the current text (I,B) coordinate system.
• If the first LND of a subpage specifies relative positioning, the offset is defined relative to the last print
position, using the current text (I,B) coordinate system. Note that when skipping into a subpage, if the
skipped-to LND specifies relative positioning, the relative offset is measured with respect to the first LND of
the subpage, which may specify a relative position as well. This function allows a subpage to “float” relative
to the last print position.
The following restriction applies to relative baseline positioning:
• The text orientation of an LND that specifies relative baseline positioning must be the same as the text
orientation of the LND that defines the baseline position from which the relative offset is measured.
Note that if the line data processed with relative baseline positioning LNDs contains carriage controls that
specify double or triple spacing, the presentation system must accumulate the relative offsets of the skipped
LNDs in order to achieve the proper line spacing. If an LND that specifies absolute positioning is skipped, the
position is reset to the absolute position and the relative offsets of any additional skipped LNDs are
accumulated with respect to the absolute position. When a page boundary is crossed, printing resumes at the
first LND.
Application Note: When relative baseline positioning is used, the PageDef generator cannot check for offpage errors, since the data normally determines, with skip-to-channel carriage controls, when the
relative baseline LNDs are invoked. AFP print servers will generate a page break if the active Data Map
is about to position data past the page's y-extent. This will not cause the generation of an error message.
Note that the page's y-extent is specified in the PGD of the Data Map.
Skip-to-Channel Processing for Relative Baseline Positioning
When a skip-to-channel carriage control is received, the remainder of the LNDs are searched sequentially for a
matching channel code until the end of the subpage is reached. If no matching channel code is found, and if
the skip is not to channel 1, a search is made for a relative LND with matching channel code starting at the top
of that subpage; if found, processing continues with this LND. If no relative LND with matching channel code is
found in the subpage or if the skip is to channel 1, the search for any LND with matching channel code
continues in the next sequential subpage. If the end of the Data Map is reached, a new page is started, and the
Data Map is searched, starting at the beginning, for any LND with matching channel code.
Relative Baseline Positioning—RCD Processing
Relative baseline positioning can also be used when record-format line data is processed with RCDs.
Relative baseline positioning is used when flag byte bit 13 is set to B'1'. The baseline position used as a
reference for the relative offset depends on whether the RCD that specifies relative positioning is a record RCD
and is determined as follows:
• For record RCDs, offsets are defined relative to the last record RCD processed. However, if a page boundary
was crossed after the last record RCD was processed, offsets are defined relative to the top margin.
• For field RCDs, the offset is defined relative to the last RCD used to print.


• If the first RCD of a Data Map specifies relative positioning, the offset is defined relative to the top margin.
The following restriction applies to relative baseline positioning:
• The text orientation of an RCD that specifies relative baseline positioning must be the same as the text
orientation of the RCD that defines the baseline position from which the relative offset is measured.
Relative Baseline Positioning—XMD Processing
Relative baseline positioning can also be used when XML data is processed with XMDs.
Relative baseline positioning is used when flag byte bit 13 is set to B'1'. The baseline position used as a
reference for the relative offset depends on whether the XMD that specifies relative positioning is an element
XMD and is determined as follows:
• For element XMDs, offsets are defined relative to the last element XMD processed. However, if a page
boundary was crossed after the last element XMD was processed, offsets are defined relative to the top
margin.
• For field XMDs, the offset is defined relative to the last XMD used to print.
• If the first XMD of a Data Map specifies relative positioning, the offset is defined relative to the top margin.
The following restriction applies to relative baseline positioning:
• The text orientation of an XMD that specifies relative baseline positioning must be the same as the text
orientation of the XMD that defines the baseline position from which the relative offset is measured.
Relative Inline Positioning—XMD Processing
Data and objects are positioned using the print position specified in IPos and BPos parameters of the XMD
structured field. The IPos normally specifies an absolute offset from the origin of the current text (I,B)
coordinate system. With relative inline positioning, the IPos parameter may be used to specify an inline
position relative to an established inline position. This allows data and objects to be positioned (in the inline
direction) relative to data placed previously on the page. If no data were placed on the page prior to the current
data, the relative inline position is relative to the left margin. Note that the actual location of the left margin on a
page is affected by the text orientation; see “Margin Definition (X'7F') Triplet”.
Relative inline positioning is used when XMD flag byte bit 12 is set to B'1'. The relative offset may be positive or
negative and is measured using the current I,B coordinate system. Note that the origin of the current I,B
coordinate system depends on the current text orientation.
The following restriction applies to relative inline positioning:
• The text orientation of an XMD that specifies relative inline positioning must be the same as the text
orientation of the XMD that defines the inline position from which the relative offset is measured.
Note: Data must not exceed the boundaries of the page, which are defined in the Page Descriptor (PGD)
structured field. If the new print position is outside these boundaries, printing of the page stops.


## The Function of the Form Definition
A Form Definition is a MO:DCA print control object that is used to place pages on sheets and is always
required for printing with presentation services programs . Form Definitions contain information about the
physical environment in which the output is to be printed, such as the paper drawer to be used and whether
printing is to be done in simplex or duplex mode. The Form Definition might also specify overlays to be used
with the data. Two types of overlays may be specified in a Form Definition: medium overlays and PMC
overlays. Medium overlays are positioned at the medium origin, while PMC overlays are positioned with
respect to the page origin. Contrast these with overlays that are mapped in a Page Definition, which are
invoked for a page using an Include Page Overlay (IPO) structured field and are called page overlays. The
overlays themselves must be generated with a separate program designed to build overlay objects. The format
for medium overlays and for PMC overlays (invoked in Form Definitions) is the same as the format for page
overlays (invoked with an IPO structured field and mapped in Page Definitions).
Form Definitions are like Page Definitions in that only one Form Definition can be associated with a given print
file and also in that each Form Definition includes one or more components. While the components of a Page
Definition are called Data Maps or Page Formats, the components of a Form Definition are called Medium
Maps or Copy Groups. An application program can switch between Medium Maps by using conditional
processing, as in the example in Figure 12. Control for presentation starts with the first Medium
Map in the Form Definition. Control for presentation can
be changed to a different Medium Map by using an
Invoke Medium Map (IMM) structured field. If the Form Definition is used to present multiple documents in a
print file, control for presentation is returned to the first Medium Map whenever a new document is
encountered.
A file can be printed multiple times, each with a different Form Definition. The example in Figure 5
can be modified to add Form Definition names in addition to Page Definition names.
Details on Form Definitions and overlay objects can be found in the Mixed Object Document Content
Architecture (MO:DCA) Reference. A set of Form Definitions that
address standard requirements is provided
with presentation services program software, but users can also create customized Form Definitions.


