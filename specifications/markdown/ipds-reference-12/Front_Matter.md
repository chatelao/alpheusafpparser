## Page 1

Advanced Function Presentation Consortium
Data Stream and Object Architectures
Intelligent Printer Data Stream
Reference
AFPC-0001-12

## Page 2

Copyright © AFP Consortium 1987, 2023 ii
Note:
Before using this information, read the information in “Notices” on page 1023.
AFPC-0001-12
Thirteenth Edition (June 2023)
This edition applies to the Intelligent Printer Data Stream™ (IPDS™) architecture. It is the fifth edition produced by the AFP
Consortium™(AFPC™) and replaces and makes obsolete the previous edition (AFPC-0001-11). This edition remains
current until a new edition is published. This publication also applies to any subsequent releases of Advanced Function
Presentation™ (AFP™) products that use the IPDS architecture until otherwise indicated in a new edition.
T echnicalchanges are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no
technical significance are not noted. For a detailed list of changes, see “Changes in This Edition” on page xvii.
Internet
Visit our home page: www.afpcinc.org

## Page 3

Copyright © AFP Consortium 1987, 2023 iii
Preface
This book describes the functions and services associated with the Intelligent Printer Data Stream (IPDS)
architecture.
This book is a reference, not a tutorial. It complements individual product publications, but does not describe
product implementations of the architecture.
Who Should Read This Book
This book is for system programmers and other developers who need such information to develop or adapt a
product or program to interoperate with other presentation products in an Advanced Function Presentation
(AFP) environment.
The Intelligent Printer Data Stream Reference describes the function and composition of elements sent to
printers that support the Intelligent Printer Data Stream (IPDS) architecture. However, this book does not
describe any specific hardware or licensed programs that implement the IPDS architecture.
This book documents the architecture that encompasses IPDS products available at the time of publication.
Use this book in conjunction with your printer documentation to:
• Check for the correct input to an IPDS-supported printer
• Learn about the input to IPDS printers
• Develop print server programs to control IPDS printers
• Support the design of programs that provide input for the data stream; for example, a text formatting program
You should note that the IPDS architecture provides a host-to-printer interface intended to be used by
programs requiring direct control over printers.
AFP Consortium (AFPC)
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

## Page 4

iv IPDS Reference
member of the consortium was transferred to the InfoPrint ® Solutions Company, an IBM/Ricoh® joint venture;
currently Ricoh holds the founding member position . In February 2009, the consortium was incorporated under
a new set of bylaws with tiered membership and shared governance resulting in the creation of a formal open
standards body called the AFP Consortium (AFPC). Ownership of and responsibility for the AFP architectures
was transferred at that time to the AFP Consortium.
Publication History
The IPDS Reference was first published by IBM in 1987 and has had several enhancements and updates
since that time. The first eight editions were published by IBM Corporation and later editions were published by
the AFP Consortium.
First Edition published by IBM Corporation
S544-3417-00 dated August 1987
Second Edition published by IBM Corporation
S544-3417-01 dated March 1989
This edition provides enhanced detail and clarification, including:
• A more complete description of the X
m,Ym coordinate system and units of measure
• More detail on Acknowledge Replies
• More detail on exception handling and exception IDs
The following major new functions were added:
• Load Resource Equivalence command (later renamed to Activate Resource command)
• New action codes and exception IDs.
• Rules for copy group processing
• STM property pairs and OPC self-defining field data
• XOA Control Edge Marks command
• XOH Set Media Origin command
Third Edition published by IBM Corporation
S544-3417-02 dated February 1990
This edition provides an extensive restructuring to improve readability and ease of reference; the
following major new functions were added:
• The Define User Area command provides additional control over the area of a page that is
accessible to an application program. This command can be used by a print-driver program to print
non-subvertible security labels.
• The XOH Define Group Boundary command provides a means of grouping pages.
• The XOH Specify Group Operation command provides a means of specifying an operation to be
performed on a group of pages. The operation can be performed by a printer or by a pre-processing
or post-processing device attached to the printer.
This edition describes the relationship between the IPDS architecture and the new SAA/CCS
architectures that IBM announced in May 1989. Chapter One describes the function of IPDS
architecture within Systems Application Architecture
®. Chapter Fourteen provides additional detail
about IPDS functional divisions and supplies compliance and migration information.
Description of some of the data that IPDS carries has been removed from this edition and a much
more complete description of this data is provided in the following new books:
Graphics Object Content Architecture Reference, SC31-6804
Image Object Content Architecture Reference, SC31-6805
Presentation Text Object Content Architecture Reference, SC31-6803

## Page 5

IPDS Reference v
Fourth Edition published by IBM Corporation
S544-3417-03 dated August 1991
This edition was further restructured to improve readability and ease of reference; the following major
new functions were added:
• A data type for each field in the syntax tables
• Envelope media
• Magnetic Ink Character Recognition (MICR) printing
• Relative-metric fonts
• Several new exception IDs
• Two new image compression algorithms:
1. ABIC (Bilevel Q-Coder) compression algorithm
2. G3 Facsimile Two-Dimensional Coding 'Scheme (G3 MR) compression algorithm
Description of some of the bar code data that IPDS carries has been removed from this edition and a
much more complete description of this data is provided in the following new book:
Bar Code Object Content Architecture™ Reference, S544-3766
Fifth Edition published by IBM Corporation
S544-3417-04 dated August 1993
This edition provides enhanced detail and the following major new functions:
• Additional information added to all exception IDs
• An overview section describing IPDS resources and resource processing
• Enhanced Chapter 1 to describe how the IPDS Architecture fits into IBM's presentation
environments
• Enhanced description of IPDS presentation spaces and their coordinate systems
• More complete glossary
• Product identification in the XOH-OPC reply
• Several new exception IDs
• XOH Select Medium Modifications command
• XOH Stack Received Pages command
The following commands have been renamed:
• “Delete Font” has been renamed to “Deactivate Font”
• “Delete Overlay” has been renamed to “Deactivate Overlay”
• “Delete Page Segment” has been renamed to “Deactivate Page Segment”
• “Load Resource Equivalence” has been renamed to “Activate Resource”
Sixth Edition published by IBM Corporation
S544-3417-05 dated March 1996
This edition provides enhanced detail and the following major new functions:
• Activate Printer Alarm command
• General enhancements include:
– Additional information added to exception IDs
– Deactivate Font command moved to Device-Control command set
– Empty LFE commands now allowed
– Font resource clarifications
– New intervention required sense data
– Presentation space mixing clarifications
– Several new exception IDs
– The term “data block” renamed to “object area”

## Page 6

vi IPDS Reference
• Input media identification
• IPDS Dialog management
• Microfilm media
• N-up page placement and orientation
• Outline fonts including:
– Adobe® Type 1 font technology
– Query extensions
– Separate code page and font character set resources
• Replicate-and-trim mapping for IO Image
• Resource query enhancements
• Support for multiple media sources and multiple media destinations
Seventh Edition published by IBM Corporation
S544-3417-06 dated November 2002
This edition provides enhanced detail and the following major new functions:
• Activation failed NACK
• All architected units of measure
• Bilevel IO-Image color
• CID-keyed font technology
• Color fidelity
• Cut-sheet emulation
• Data object resources
• Data validation and ribbon fault errors
• Default character in outline code pages
• Double-byte outline fonts
• Double-byte raster to outline font migration support
• EPS and EPS with transparency
• Extended group ID format for OS/400
®
• Extended overlay and page segment support
• Extended page counters control
• External printer name
• Finishing (cutting, folding, inserting, punching, stapling, stitching)
• Finishing fidelity
• Font character set extensions
• Full-process text color (PTOCA PT3)
• G3 MH image compression algorithm
• GOCA box draw, image resolution, partial arcs, and process color
• IOCA Image resources and IOCA tile resources
• IOCA full-process color support (FS11, FS40, FS42, FS45)
• Logical page and object area coloring and shading
• Media identification by OID
• Multiple raster-font resolutions
• Named groups
• Object container architecture
• Object container OID for color mapping table
• Operator-directed recovery
• Page overlay rotation
• PDF pages, PDF pages with transparency, and PDF resources
• Physical media selection extensions
• Postal bar codes (Australia Post, Dutch KIX, Japan Postal, and Royal Mail)
• Presentation fidelity control

## Page 7

IPDS Reference vii
• Printer-detected forms mismatch
• Printer set-up verification
• Resident color profiles
• Resource version support
• Saved pages
• Scale-to-fill mapping option
• Set Media Size enhancements
• T oner saver
• Two-dimensional (2D) bar codes (Data Matrix, MaxiCode, and PDF417)
• UP 3ITM finishing
• XOH Separate Continuous Forms command
Eighth Edition published by IBM Corporation
S544-3417-07 dated May 2006
This edition provides enhanced detail and the following major new functions:
• Additional data objects: GIF , JFIF , JPEG2000, PCL®, and TIFF
• Additional group information
• Code 93 bar code
• Color management
• GCGID-to-Unicode enhancements
• Identification of TrueType and OpenType font errors
• Intelligent Mail
® Barcode
• JBIG2 image compression algorithm
• MICR enhancements
• PLANET bar code
• QR Code 2D bar code
• Rasterize Presentation Object command
• T ext fidelity control
• TrueType/OpenType font support (data-object fonts)
• UCC/EAN 128 bar code
• UP
3I Print Data
Ninth Edition published by AFP Consortium
AFPC-0001-01 dated June 2010
This edition provides enhanced detail and the following major new functions:
• Bind and trim finishing
• Clarifications based on multiple-company experience
• Cubic Bézier Curves GOCA drawing order
• Data Matrix encodation
• Desired bar code symbol width
• Exception ID additions and updates
• Exception IDs registered with action code X'1A'
• Extension of IDO color override for IOCA
• Image Resolution (X'9A') triplet
• IOCA Set Extended Bilevel Image Color exception ID
• Large copy set values
• Multi-image TIFF containers
• Multi-page PDF containers
• Number of pages in a group
• Partial arcs with clockwise drawing direction

## Page 8

viii IPDS Reference
• Partial support for HT and TTC CMRs
• Passthrough for audit color-conversion CMRs
• Rasterize Presentation Object extensions for color management
• Retired items identified
• Royal Mail RED T ag bar code
• Scale-to-Fill mapping option for graphics objects
• Set default support in GDD for normal line width
• Set default support in GDD for process color
• Set Line End GOCA drawing order
• Set Line Join GOCA drawing order
• Small bar code symbol support
• T ag Image File Format (TIFF) without transparency
• Unicode values in IPDS code pages
• UP 3I Enhancements
Tenth Edition published by AFP Consortium
AFPC-0001-09 dated August 2011
This edition provides enhanced detail and the following major new functions:
• AFPC TIFF Subset
• BCOCA™ subset BCD2
• Clarifications for:
– Color simulation when a page is saved
– Description of sense-byte format 0 byte 18
– Device resolution
– Duplicate code point exception in an LCP command
– EHC flags within an RPO command
– Exception ID descriptions
– Fidelity-control triplet descriptions and the Exception Handling Control flowchart
– FQN (X'02') triplet in the AR command triplets-use table
– IOCA self-defining fields table
– PCA for exception IDs X'0200..01', X'021C..01', and X'021E..01'
– Unsupported group ID formats in a Group Information (X'6E') triplet
• CMOCA error codes X'12' and X'13'
• GOCA subset GRS3
• ICC DeviceLink CMRs
• Intelligent Mail Container Barcode
• IPDS support for MO:DCA™ Interchange Set 3 (IS/3)
• Overview section to describe how color is specified within IPDS commands
• PTOCA subset PT4
• Retired Exception IDs more fully described
• Specific error condition identified in NACKs for multi-bulleted exception IDs
• T ext glyph runs in PTOCA data
– Allows better user control over text layout
– Supports Unicode complex text
• User-selected presentation-space size for PDF objects

## Page 9

IPDS Reference ix
Eleventh Edition published by AFP Consortium
AFPC-0001-10 dated June 2014
This edition provides enhanced detail and the following major new functions:
• Acknowledge Reply extended to allow additional debug information to be returned after an error
occurs
• Bar Code Type/Modifier Self-Defining Field for the OPC reply; also deprecates the Bar Code
Common Set
• Bearer Bars for Interleaved 2-of-5 bar codes
• Color for Bilevel and Grayscale Image (allows color to be specified for all bilevel image objects
supported within IPDS pages and overlays; also allows grayscale images to be colorized)
• Command and triplet descriptions updated to ensure mention of each appropriate exception ID in
context
• Deprecated the Royal Mail RED TAG bar code type
• Deprecated the USPS POSTNET and PLANET bar code types
• Finishing operations for folding (center-fold out, c-fold in, c-fold out, accordion-fold in, accordion-fold
out, single gate-fold in, single gate-fold out, double parallel-fold in, double parallel-fold out, double
gate-fold in, and double gate-fold out)
• Glossary definition for the term “deprecated”
• Glossary definitions for several terms (particularly color terms)
• GOCA Custom Line Types
• GOCA Marker Size support
• GOCA Nonzero Winding Mode
• GS1 DataBar bar codes
• Human-readable object names for captured objects
• Index entries provided for each exception ID definition indicating where the exception ID is defined
and used
• Internal rendering intent supported in presentation data objects; includes more information in the
CMRs-Used trace entry
• Keep Group T ogether as a Recovery Unit (new SGO Group Operation that allows repositioning and
error recovery on a group boundary)
• Media Feed Direction returned in the OPC reply
• Metadata Object Content Architecture (MOCA) added; metadata can be carried in MO:DCA print
files and documents, but is currently not supported in IPDS data streams
• New data stream structures:
– 2 new action codes (X'1B' and X'2B')
– 3 new commands (RRR, RRRL, and WTC)
– 18 new exception IDs (X'4040..00' (with action codes X'1B' and X'2B'), X'0408..05', X'0412..04',
X'0201..03', X'020A..06', X'020D..30', X'020D..31', X'020D..32', X'0238..04', X'0238..10',
X'0238..11', X'0255..0B', X'0256..24', X'0293..00', X'0293..03', X'0293..04', X'0140..00' (with action
codes X'1B' and X'2B'), X'0141..00')
– 1 new XOA order (XOA Obtain Additional Exception Information)
– 3 new XOH-OPC self-defining fields (X'000F', X'0024', and X'0027')
– 3 new object self-defining fields (TAP , TOC, and TDD)
– 15 new STM property pairs (X'1204', X'1205', X'1600', X'2001', X'4113', X'4114', X'4115', X'4132',
X'4403', X'500E', X'5801', X'80F9', X'F102', X'F211', and X'FF03')
• Object area orientation extended to any angle measured in degrees and minutes
• Object container version information can now be returned in the XOH-OPC reply
• Portable Network Graphics (PNG) objects (AFPC PNG Subset)
• Preprinted Form Overlay (PFO) – provides a more accurate simulation of preprinted forms
• Remove Resident Resource (RRR) – new command to allow deactivated data-object resources and
data-object-font components to be removed from the printer
• Request Resident Resource List (RRRL) – new command to allow a print server to obtain a list of all
printer-resident resources that includes information useful to a resource administrator

## Page 10

x IPDS Reference
• Royal Mail Mailmark TM bar codes
• T ext objects – allows text to be packaged in an object and positioned on a page or overlay like any
other presentation object; defines a new command called Write T ext Control (WTC)
• TIFF LZW with Differencing Predictor compression algorithm for IOCA images
• Updates to the description of unsupported IOCA function in an IPDS environment
• XOA Obtain Additional Exception Information – new XOA order to allow a printer server to obtain
additional debug information after a NACK has been reported
• Clarifications for:
– Color and color management
– Exception IDs
– Length field ranges
– Mixing rules
– Secondary resources
– T ext-major text
– Triplets
Twelfth Edition published by AFP Consortium
AFPC-0001-11 dated December 2016
This edition provides enhanced detail to support the IPDS products that were introduced in the years
2014 through 2016 and to support the work of the AFP Consortium. Specifically, the following new
function and clarification has been added:
• Clarifications for:
– Better identification of early IPDS products manufactured by IBM and Océ
– IOCA self-defining fields have been labeled to distinguish them from IPDS self-defining fields;
note that both IOCA and IPDS defines self-defining fields
– Retired items and retired exception IDs
– Sense data format used for various exception IDs
• Glossary definitions for several terms (particularly color, FOCA, GOCA, and metadata terms)
• Glossary updated to include the current definition for all AFP terms
• GOCA custom patterns supported
• GOCA linear and radial gradients supported
• Improvements to object-container-version reporting:
– Explicitly listed the object OIDs that constitute the variations when describing the object containers
that have variations defined for them
– TrueType/OpenType added to the list of object containers that have variations defined for them
• New appendix describing each numbered retired item and also items that have been unretired
• New data stream structures:
– 1 new text subset ID (LF4)
– 2 new object OIDs (AFPC SVG Subset object and non-OCA resource object)
– 3 new XOH-OPC self-defining fields (X'0025', X'0026', and X'0028')
– 25 new STM property pairs (X'1206', X'1207', X'1208', X'1209', X'120A', X'120B', X'1304', X'1305',
X'2002', X'4116', X'4117', X'4130', X'4131', X'6004', X'6005', X'6006', X'6007', X'6008', X'6009',
X'A0nn' (used in new places), X'F004', X'F005', X'F604', X'F605', and X'FC01')
– 45 new and updated exception IDs (X'0109..00', X'020D..0F', X'020D..13', X'020E..02',
X'020E..03', X'020E..04', X'020E..05', X'0256..B1', X'0256..B2', X'0256..B3', X'0256..B4',
X'0257..02', X'027C..0D', X'0300..08', X'035E..00', X'0368..06', X'03DC..00', X'03DC..01',
X'03DC..02', X'03DC..03', X'03DC..04', X'03DC..05', X'03DC..06', X'03DC..07', X'03DD..00',
X'03DD..01', X'03DD..02', X'03DD..03', X'03DD..04', X'03DD..05', X'03DD..06', X'03DD..07',
X'03DE..00', X'03DE..01', X'03DE..02', X'03DE..03', X'03DE..04', X'03DE..05', X'03DE..06',
X'03DE..07', X'03DF ..00', X'03DF ..01', X'03DF ..02', X'0500..04', and X'059B..10')
• New finishing support:

## Page 11

IPDS Reference xi
– New finishing operation for fold out
– New finishing options field defined with a new option to crease instead of fold for all defined fold
operations
– New XOH-OPC Finishing Options self-defining field so that a printer can report all supported
options
• New OPC Recognized Group ID Formats self-defining field so that a printer can list the Group ID
(X'00') triplet formats that are recognized; this self-defining field can help a host program to
determine what Group ID format to use
• New OPC Supported Device Resolutions self-defining field so that a printer can provide a list of the
currently supported resolutions; previously, the IM-Image and Coded-Font Resolution self-defining
field provided some of this information, but it was not always complete and it only covered the
resolution-dependent data types
• New subset ID LF4 added to the STM Loaded-Font command-set vector to identify support for
FOCA code pages when LF3 FOCA fonts are not also supported; code pages are used with
TrueType/OpenType font objects as well as with LF3 fonts
• Printer support for the MO:DCA GA (Graphic Arts) function set
• Seventeen new STM property pairs for functions that are optional, but previously not reported in
STM replies
• STM orientation-support property pair (X'A0nn') now used for all presentation objects
• Support for Non-OCA Resource objects (used with PDF and SVG objects)
• Support for Scalable Vector Graphics (SVG) objects
• The description of the X'059B..10' exception was updated to include an ordered list of possible
causes
• TrueType/OpenType fonts can be used with OCA presentation objects and with PDF and SVG
objects
• Two new STM property pairs to cover options and choices related to the GOCA architecture (font
positioning and cell positioning)

## Page 12

xii IPDS Reference
How to Use This Book
This book is divided into sixteen chapters and four appendixes:
• Chapter 1, “A Presentation Architecture Perspective”, on page 1 introduces the AFPC presentation
architectures and describes the role of data streams and data objects.
• Chapter 2, “Introduction to IPDS”, on page 7 introduces the Intelligent Printer Data Stream as a component
of printing subsystems. This chapter also describes IPDS functional divisions.
• Chapter 3, “IPDS Overview”, on page 17 describes the key concepts and terminology used by IPDS
Architecture.
• Chapter 4, “Device-Control Command Set”, on page 123 describes the commands used to set up a logical
page, communicate device controls, manage resources, and handle the acknowledgment protocol.
• Chapter 5, “T ext Command Set”, on page 457 describes the commands for presenting PTOCA text
information in a page, a page segment, or an overlay.
• Chapter 6, “IM-Image Command Set”, on page 479 describes the commands for presenting image raster
data in a page, a page segment, or an overlay.
• Chapter 7, “IO-Image Command Set”, on page 493 describes the commands for presenting IOCA image
data in a page, a page segment, or an overlay.
• Chapter 8, “Graphics Command Set”, on page 521 describes the commands for presenting GOCA graphics
data in a page, a page segment, or an overlay.
• Chapter 9, “Bar Code Command Set”, on page 547 describes the commands for presenting BCOCA data in
a page, a page segment, or an overlay.
• Chapter 10, “Object Container Command Set”, on page 563 describes the commands for downloading object
containers and presenting object container data in a page, a page segment, or an overlay.
• Chapter 11, “Metadata Command Set ”, on page 615 describes the commands for associating metadata with
any of many types of objects.
• Chapter 12, “Overlay Command Set”, on page 621 describes the commands for controlling frequently
accessed user data in a page, a page segment, or an overlay. An overlay contains its own environment and
appears the same on every page on which it is printed.
• Chapter 13, “Page-Segment Command Set”, on page 631 describes the commands for controlling frequently
accessed user data in a page. Unlike an overlay, a page segment is not independent of its page
environment.
• Chapter 14, “Loaded-Font Command Set”, on page 635 describes the commands for downloading coded-
font information to the printer.
• Chapter 15, “Triplets”, on page 703 describes substructures called triplets that are used within some IPDS
commands.
• Chapter 16, “Exception Reporting”, on page 789 provides additional information about the Acknowledge
Reply that is used by IPDS devices for exception reporting. A complete list of printer exception-reporting
codes is provided along with a description of page-counter and copy-counter adjustments.
• Chapter 17, “Compliance”, on page 977 provides a complete description of the IPDS functional divisions,
IPDS support requirements, and migration functions.
• Appendix A, “IPDS Commands Sorted by Command Code”, on page 989 provides a table listing the IPDS
command codes in numeric order, the meaning of the codes, and the section in this document where they
are described.
• Appendix B, “Examples of IPDS Command Sequences”, on page 993 provides examples of IPDS command
sequences that could be used to drive an IPDS printer.

## Page 13

IPDS Reference xiii
• Appendix C, “Image Compression and Recording Algorithms”, on page 1001 provides a brief description of
the algorithms used in the IO-Image command set for image compression and recording.
• Appendix D, “Retired Items”, on page 1007 lists each retired item that is mentioned within the body of this
book and also lists those items that have been unretired.
The “Glossary” on page 1027 defines terms used within the book.
How to Read the Syntax Diagrams
Throughout this book, syntax is described using the following format that shows the syntax of a command in a
horizontal representation followed by a table showing the data of the command. Refer to “Notation
Conventions” on page 67 and “The IPDS Command Format” on page 70 for a detailed description of the
command syntax. The syntax includes six basic data types:
CODE Architected constant
CHAR Character string
BITS Bit string
UBIN Unsigned binary
SBIN Signed binary
UNDF Undefined type
Command Syntax
Command
Length
Command ID Flags Correlation ID Data
Data Syntax
Offset Type Name Range Meaning Subset Range
The
field's
offset
The
field's
data
type
Name of field,
if applicable
Architecturally
valid range of
values
Meaning or purpose of the data element Range defined
by a subset of an
IPDS command
set
Note: The specific heading for the subset range column in a table will identify a specific subset, such as PS1
Range, or will indicate Required when the command does not belong to a specific subset.
The following is an example of IPDS syntax (for the Deactivate Page Segment command):
Length X'D66F' Flag CID Data
The data field is as follows:
Offset Type Name Range Meaning PS1 Range
0–1 CODE HAID X'0000'
X'0001' –
X'7EFF'
Deactivate All indicator
Page Segment Host-Assigned ID
X'0000'
X'0001' –
X'007F'

## Page 14

xiv IPDS Reference
Related Publications
Several other publications can help you understand the architecture concepts described in this book. AFP
Consortium publications and a few other AFP publications are available on the AFP Consortium website,
www.afpcinc.org.
Table 1. AFP Consortium Architecture References
AFP Architecture Publication Order Number
AFP Programming Guide and Line Data Reference AFPC-0010
Bar Code Object Content Architecture Reference AFPC-0005
Color Management Object Content Architecture Reference AFPC-0006
Font Object Content Architecture Reference AFPC-0007
Graphics Object Content Architecture for Advanced Function Presentation Reference AFPC-0008
Image Object Content Architecture Reference AFPC-0003
Intelligent Printer Data Stream Reference AFPC-0001
Metadata Object Content Architecture Reference AFPC-0013
Mixed Object Document Content Architecture™ (MO:DCA) Reference AFPC-0004
Presentation Text Object Content Architecture Reference AFPC-0009
Table 2. Additional AFP Consortium Documentation
AFPC Publication Order Number
AFP Color Management Architecture™ (ACMA™) G550-1046 (IBM)
AFPC Company Abbreviation Registry AFPC-0012
AFPC Font Typeface Registry AFPC-0016
BCOCA Frequently Asked Questions AFPC-0011
MO:DCA-L: The OS/2 PM Metafile (.met) Format AFPC-0014
Presentation Object Subsets for AFP AFPC-0002
Recommended IPDS Values for Object Container Versions AFPC-0017
Table 3. AFP Font-Related Documentation
Publication Order Number
Character Data Representation Architecture Reference and Registry;
please refer to the online version for the most current information
(https://www.ibm.com/software/globalization/cdra/index.html)
SC09-2190 (IBM)
Font Summary for AFP Font Collection S544-5633 (IBM)
Technical Reference for Code Pages S544-3802 (IBM)

## Page 15

IPDS Reference xv
Table 4. UP3I Architecture Documentation
UP3I Publication Order Number
Universal Printer Pre- and Post-Processing Interface (UP 3I) Specification Available at
www.afpcinc.org

## Page 16

xvi IPDS Reference

## Page 17

IPDS Reference xvii
Changes in This Edition
Changes between this edition and the previous edition are marked by a vertical bar “|” in the left margin.
This edition provides enhanced detail to support the IPDS products that were introduced in the years 2017
through 2023 and to support the work of the AFP Consortium. Specifically, the following new function and
clarification has been added:
• Clarifications for:
– The definition of foreground for GOCA graphics objects and BCOCA bar code objects
– The description of the scale-to-fit mapping option in the Image Resolution (X'9A') triplet
– The idea that the PDF presentation space size in the Object Container Presentation Space Size (X'9C')
triplet also specifies the PDF box that the PDF should be rendered to
– The OPC self-defining fields whose last encountered instance specifies the characteristic
– The orientation support required to satisfy MO:DCA interchange set IS/3
– The XOA Obtain Additional Exception Information (OAEI) order
• Glossary updated to include the current definition for all AFP terms
• Known colorant names defined for the CMOCA Colorant Identification List tag are also supported in the OPC
Colorant-Identification self-defining field
• Metadata now supported:
– “AFP T agging” metadata format supported
– New exception class: Metadata specification check
– New Metadata command set, including an MO1 command set subset and an MS1 data tower level
– New Metadata state, which can be entered from many other states
– New STM Metadata command-set vector
• MO:DCA GA (Graphic Arts) function set updated to remove “resident color profile” from the list of required
IPDS support
• New data stream structures:
– 1 new XOA Order:
◦ XOA Request Setup Name List (RSNL)
– 2 new triplets:
◦ Invoke T ertiary Resource (X'A2')
◦ Setup Name (X'9E')
– 2 new XOH-OPC self-defining fields (X'0029' and X'002A')
– 4 new Acknowledge Reply acknowledge types (X'07', X'08', X'47', and X'48')
– 5 new commands:
◦ Activate Setup Name (ASN), in the Device-Control command set
◦ Data Object Resource Equivalence 2 (DORE2), in the Object Container command set
◦ Delete Home-State Metadata (DHM), in the Metadata command set
◦ Write Metadata Control (WMC), in the Metadata command set
◦ Write Metadata (WM), in the Metadata command set

## Page 18

xviii IPDS Reference
– 14 new STM property pairs (X'1306', X'1307', X'120D', X'120E', X'4304', X'5501', X'5506', X'700A',
X'80FA', X'D001', X'F212', X'F403', X'FF14', and X'FF48')
– 75 new and updated exception IDs (X'010A..00', X'010D..00', X'0200..01', X'0202..02', X'0202..05',
X'0205..01', X'020B..05', X'020D..10', X'020D..11', X'020D..12', X'020D..19', X'021A..01', X'021A..03',
X'021C..01', X'021E..01', X'0256..C1', X'0256..C2', X'0256..C3', X'0256..C4', X'0256..C5', X'0256..C6',
X'027A..01', X'027B..01', X'027C..09', X'027C..0A', X'029D..01', X'029D..02', X'029D..03', X'02A1..00',
X'02A1..01', X'02A2..00', X'02D0..00', X'02D0..01', X'040F ..01', X'040F ..04', X'040F ..13', X'040F ..14',
X'040F ..15', X'040F ..16', X'040F ..17', X'040F ..18', X'040F ..19', X'040F ..1A', X'040F ..1B', X'040F ..1C',
X'040F ..1D', X'040F ..1E', X'040F ..20', X'040F ..30', X'040F ..31', X'040F ..32', X'040F ..33', X'040F ..34',
X'040F ..35', X'040F ..36', X'040F ..37', X'040F ..38', X'040F ..39', X'040F ..3A', X'040F ..3B', X'0412..05',
X'0500.03', X'059B..18', X'05B3..0F', X'05B3..10', X'05B3..11', X'0601..00', X'0602..00', X'0602..10',
X'0602..20', X'0602..30', X'0602..40', X'0602..50', X'0603..00', and X'50FA..00')
• New bar code support:
– Aztec Code
– Data Matrix Rectangular Extension: 24 new rectangular sizes for the Data Matrix bar code type
– Intelligent Mail Package Barcode
– QR Code with Image, which allows printing images in conjunction with QR code bar codes; such images
are secondary resources to a QR code bar code
◦ New Invoke T ertiary Resource (X'A2') triplet is used to invoke CMRs for such images
– “T oo much data” support for the QR Code and Data Matrix bar codes
• New Data Object Resource Equivalence 2 (DORE2) command fixes a bug that was introduced when use of
native object container TrueType/OpenType Fonts as secondary resources was added; DORE2 is very
similar to DORE, but adds the ability to specify the HAID pool that should be searched
• New IOCA support:
– Ability for a printer to report support of IOCA transparency masks, for function sets that do not include that
functionality
– Function sets FS14 and FS48
– nColor images
– nColor Names parameter
• New OPC Printer Speed self-defining field to report a printer’s speed
• New Parameter ID value for the OPC Product Identifier self-defining field to allow a printer to report
information, such as the EC level, about the subsystems on the printer
• New Trim finishing support
• “Object-container” state has been renamed to “object-container resource” state to better distinguish it from
“page object container” state and “overlay object container” state
• PTOCA encrypted text strings supported
• Setup Names, a new concept that allows association of a name with a set of printer configuration settings,
supported, including:
– New Activate Setup Name (ASN) command
– New OPC Active Setup Name self-defining field
– New Setup Name (X'9E') triplet
– New XOA Request Setup Name List (RSNL) order
– Two new acknowledge types for the replies to the ASN and XOA-RSNL
• STM property pair X'120B' retired, becoming retired item 149

## Page 19

Copyright © AFP Consortium 1987, 2023 xix
Contents
Preface . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
Who Should Read This Book .....................................................................................................................iii
AFP Consortium (AFPC) ...........................................................................................................................iii
Publication History.................................................................................................................................. iv
How to Use This Book .............................................................................................................................xii
How to Read the Syntax Diagrams........................................................................................................ xiii
Related Publications .............................................................................................................................. xiv
Changes in This Edition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xvii
Figures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . xxix
Tables . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .xxxiii
Chapter 1. A Presentation Architecture Perspective. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .1
The Presentation Environment ................................................................................................................... 1
Architecture Components.......................................................................................................................... 2
Data Streams ..................................................................................................................................... 2
Objects ............................................................................................................................................. 4
Chapter 2. Introduction to IPDS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .7
IPDS Architecture as a Component of Printing Subsystems .............................................................................. 8
The Spooled System Environment .......................................................................................................... 9
The Mainframe Interactive Environment ................................................................................................. 10
The Intelligent Workstation or Departmental System Environment ............................................................... 11
The Local Area Network Environment.................................................................................................... 12
Communication with an IPDS Device ......................................................................................................... 13
IPDS Functional Divisions ....................................................................................................................... 14
Chapter 3. IPDS Overview . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 17
The IPDS Presentation Environment ......................................................................................................... 17
Cut-Sheet Emulation Mode ................................................................................................................. 22
Overlays and Page Segments.............................................................................................................. 24
Using an Overlay as a Preprinted Form.................................................................................................. 26
IPDS Mixing Rules ............................................................................................................................ 27
Foreground and Background ........................................................................................................... 27
Merging Presentation Spaces.......................................................................................................... 28
General Mixing Rules .................................................................................................................... 30
Formblend Mixing Rule .................................................................................................................. 30
IPDS Default Mixing Rule ............................................................................................................... 31
Logical Page and Object Area Coloring.................................................................................................. 32
Specifying Color ............................................................................................................................... 33
Color Management ............................................................................................................................ 35
Basic Concepts ............................................................................................................................ 35
CMR-Usage Hierarchy ................................................................................................................... 35
Data-Object-Level CMRs ........................................................................................................... 37
Medium-Overlay-Level CMRs ..................................................................................................... 38
Page-Overlay-Level CMRs ......................................................................................................... 38
Page-Level CMRs .................................................................................................................... 38
Home-State-Level CMRs ........................................................................................................... 39
Default CMRs .......................................................................................................................... 39
CMR-Usage Hierarchy Processing ................................................................................................... 39
Selecting Color-Conversion CMRs ................................................................................................... 40
Pass-Through Audit Color-Conversion CMRs ..................................................................................... 41
CMR-Usage Hierarchy Summary ..................................................................................................... 42
Color Management Compliance ....................................................................................................... 43
Required Color-Management Function .......................................................................................... 43
Optional Color-Management Function ........................................................................................... 43
Color Resource Relationships ......................................................................................................... 44
Ordered Data ................................................................................................................................... 45

## Page 20

xx IPDS Reference
Fixed Medium Information................................................................................................................... 45
Fonts.............................................................................................................................................. 45
Coded-Font Components ............................................................................................................... 46
Data-Object-Font Components ........................................................................................................ 46
Expressing Linear Measurements ............................................................................................................. 47
Coordinate Systems............................................................................................................................... 48
X
m,Ym Coordinate System (Medium) ..................................................................................................... 48
Xp,Yp Coordinate System (Logical Page) ................................................................................................ 53
I,B Coordinate System (T ext) ............................................................................................................... 55
Character Development ................................................................................................................. 55
I,B Orientation .............................................................................................................................. 56
The Four Basic Object Orientations .................................................................................................. 57
Other Object Orientations ............................................................................................................... 59
Xt,Yt Coordinate System (T ext)............................................................................................................. 60
Xg,Yg Coordinate System (Graphics) ..................................................................................................... 60
Xio,Yio Coordinate System (IO Image) .................................................................................................... 60
Xbc,Ybc Coordinate System (Bar Code) .................................................................................................. 60
Xoc,Yoc Coordinate System (Object Container) ......................................................................................... 60
Xoa,Yoa Coordinate System (Object Area) ............................................................................................... 60
Coordinate System Relationships ......................................................................................................... 61
The Valid Printable Area ......................................................................................................................... 62
The User-Printable Area ......................................................................................................................... 63
Position Exceptions for Presentation Objects............................................................................................... 64
Logical Positioning and Physical Pels ........................................................................................................ 65
Processing IPDS Commands ................................................................................................................... 66
Notation Conventions ............................................................................................................................. 67
L-Unit Range Conversion Algorithm........................................................................................................... 68
The IPDS Command Format.................................................................................................................... 70
Host Acknowledgment Requests .............................................................................................................. 71
Printer Acknowledge Replies ................................................................................................................... 71
IPDS Operating States ........................................................................................................................... 72
Home State ..................................................................................................................................... 72
Presentation-
Object States ................................................................................................................. 73
Page State ...................................................................................................................................... 74
Overlay State ................................................................................................................................... 75
Page Segment State.......................................................................................................................... 76
Font State ....................................................................................................................................... 77
Code Page State............................................................................................................................... 78
IO-Image Resource State ................................................................................................................... 79
Object-Container Resource State ......................................................................................................... 80
Metadata State ................................................................................................................................. 81
Anystate.......................................................................................................................................... 82
Summary of the IPDS States and Commands ......................................................................................... 83
IPDS Resources ................................................................................................................................... 88
Resource IDs ................................................................................................................................... 89
HAID Pools.................................................................................................................................. 91
Resource Management ...................................................................................................................... 92
Physical Presence of Resources ...................................................................................................... 92
Availability of Resources ................................................................................................................ 94
Invocation of Resources ................................................................................................................. 97
Data Object Resources, Data-Object-Font Components, and Setup Files ................................................. 99
Multi-Page Resource Objects ........................................................................................................ 103
Pre-Rasterizing and Caching Presentation Objects ............................................................................ 104
Saving and Including Pages .......................................................................................................... 105
Relationship Between FOCA Character Metrics and TrueType Character Metrics ......................................... 107
Horizontal Metrics ....................................................................................................................... 107
Vertical Metrics........................................................................................................................... 108
Simulating Vertical Metrics ............................................................................................................ 109
Resource Management Summary........................................................................................................ 111
Metadata........................................................................................................................................112
Exception Handling...............................................................................................................................115
Pages Exactly-As-Requested .............................................................................................................115

## Page 21

IPDS Reference xxi
Best-Possible Output ........................................................................................................................115
Exception-Handling Combinations .......................................................................................................116
Exception-Handling Control ................................................................................................................117
Presentation Fidelity Control ...................................................................................................................118
Color Simulation Guidelines....................................................................................................................119
Simulating Out-of-Gamut Colors..........................................................................................................119
Simulating Colors on a Black-Only Printer without Grayscale Capability .......................................................119
Simulating Colors with Grayscale ........................................................................................................119
Default Handling ................................................................................................................................. 121
Chapter 4. Device-Control Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 123
Acknowledge Reply ............................................................................................................................. 124
Acknowledge Reply Data Format........................................................................................................ 127
Four-Byte Page and Copy Counter Format ....................................................................................... 127
Eighteen-Byte Page and Copy Counter Format ................................................................................. 127
General Rules for the Acknowledge Reply ............................................................................................ 132
Activate Resource ............................................................................................................................... 134
Activate Resource Triplet Considerations ............................................................................................. 154
Activate Setup Name
........................................................................................................................... 157
ASN Reply..................................................................................................................................... 158
Apply Finishing Operations .................................................................................................................... 160
Apply Finishing Operations Triplet Considerations.................................................................................. 161
Begin Page ........................................................................................................................................ 162
Deactivate Font................................................................................................................................... 163
Define User Area................................................................................................................................. 168
End .................................................................................................................................................. 170
End Page .......................................................................................................................................... 171
Include Saved Page............................................................................................................................. 172
Group ID (X'00') Triplet Considerations ................................................................................................ 173
Invoke CMR ....................................................................................................................................... 174
Load Copy Control............................................................................................................................... 176
Rules for Copy Subgroup Exception Processing .................................................................................... 192
When The Page Is T o Be Printed: ................................................................................................... 192
When The Page Is Not T o Be Printed: ............................................................................................. 193
Load Font Equivalence ......................................................................................................................... 196
Logical Page Descriptor ........................................................................................................................ 204
Area Coloring Triplet Considerations ................................................................................................... 209
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations ...................................................... 209
Logical Page Position ........................................................................................................................... 210
Manage IPDS Dialog............................................................................................................................ 216
No Operation...................................................................................................................................... 217
Presentation Fidelity Control .................................................................................................................. 218
Rasterize Presentation Object ................................................................................................................ 220
Processing Rules ............................................................................................................................ 222
Preprocessing Overlays ............................................................................................................... 222
Preprocessing Data Object Resources ............................................................................................ 222
Limitations................................................................................................................................. 223
Command Syntax ........................................................................................................................... 224
Color Specification (X'4E') Triplet Considerations............................................................................... 230
Object Offset (X'5A') Triplet Considerations ...................................................................................... 231
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations.................................................. 231
Image Resolution (X'9A') Triplet Considerations ................................................................................ 232
Object Container Presentation Space Size (X'9C') Triplet Considerations................................................ 232
Sense Type and Model ......................................................................................................................... 233
Acknowledge Reply for Sense Type and Model...................................................................................... 233
Device-Control Command-Set Vector.............................................................................................. 235
T ext Command-Set Vector ............................................................................................................ 244
IM-Image Command-Set Vector ..................................................................................................... 247
IO-Image Command-Set Vector ..................................................................................................... 249
Graphics Command-Set Vector...................................................................................................... 253
Bar Code Command-Set Vector ..................................................................................................... 256
Object Container Command-Set Vector ........................................................................................... 259
Metadata Command-Set Vector
..................................................................................................... 262

## Page 22

xxii IPDS Reference
Overlay Command-Set Vector ....................................................................................................... 262
Page Segment Command-Set Vector .............................................................................................. 263
Loaded-Font Command-Set Vector................................................................................................. 263
Set Home State .................................................................................................................................. 265
Set Presentation Environment ................................................................................................................ 266
Presentation Environment Triplet Considerations ................................................................................... 267
Execute Order Anystate ........................................................................................................................ 268
XOA Activate Printer Alarm ............................................................................................................... 270
XOA Alternate Offset Stacker ............................................................................................................ 271
XOA Control Edge Marks.................................................................................................................. 272
XOA Discard Buffered Data ............................................................................................................... 275
XOA Discard Unstacked Pages.......................................................................................................... 276
XOA Exception-Handling Control........................................................................................................ 277
XOA Mark Form.............................................................................................................................. 289
XOA Obtain Additional Exception Information........................................................................................ 290
OAEI Reply ............................................................................................................................... 291
XOA Print-Quality Control ................................................................................................................. 293
XOA Request Resource List .............................................................................................................. 294
Resource List Query.................................................................................................................... 295
Resource List Reply .................................................................................................................... 303
XOA Request Setup Name List
.......................................................................................................... 307
RSNL Reply............................................................................................................................... 309
Setup Name Information entry ....................................................................................................311
Execute Order Home State.................................................................................................................... 313
XOH Deactivate Saved Page Group.................................................................................................... 315
Group ID (X'00') Triplet Considerations ............................................................................................ 316
XOH Define Group Boundary............................................................................................................. 317
Coded Graphic Character Set Global Identifier (X'01') Triplet Considerations ........................................... 323
Finishing Operation (X'85') Triplet Considerations .............................................................................. 323
UP3I Finishing Operation (X'8E') Triplet Considerations ....................................................................... 324
XOH Eject to Front Facing................................................................................................................. 325
XOH Erase Residual Font Data .......................................................................................................... 326
XOH Erase Residual Print Data.......................................................................................................... 327
XOH Obtain Printer Characteristics ..................................................................................................... 328
OPC Command .......................................................................................................................... 328
OPC Reply ................................................................................................................................ 329
Printable-Area Self-Defining Field .............................................................................................. 331
Symbol-Set Support Self-Defining Field....................................................................................... 335
IM-Image and Coded-Font Resolution Self-Defining Field................................................................ 337
Storage Pools Self-Defining Field............................................................................................... 338
Retired Item 130 (Standard OCA Color Value Support Self-Defining Field) .......................................... 339
Installed Features Self-Defining Field.......................................................................................... 340
Available Features Self-Defining Field......................................................................................... 341
Resident Symbol-Set Support Self-Defining Field .......................................................................... 343
Print-Quality Support Self-Defining Field...................................................................................... 344
XOA-RRL RT & RIDF Support Self-Defining Field.......................................................................... 345
Activate Resource RT & RIDF Support Self-Defining Field............................................................... 346
Medium Modification IDs Supported Self-Defining Field .................................................................. 347
Deprecated (Common Bar Code Type/Modifier Self-Defining Field) ................................................... 348
Bar Code Type/Modifier Self-Defining Field .................................................................................. 350
Media-Destinations Self-Defining Field ........................................................................................ 355
Supported Group Operations Self-Defining Field ........................................................................... 356
Product Identifier Self-Defining Field ........................................................................................... 357
Object-Container Type Support Self-Defining Field ........................................................................ 361
DF Deactivation Types Supported Self-Defining Field ..................................................................... 365
PFC Triplets Supported Self-Defining Field .................................................................................. 366
Printer Setup Self-Defining Field ................................................................................................ 367
Finishing Operations Self-Defining Field ...................................................................................... 368
UP3I Tupel Self-Defining Field ................................................................................................... 369
UP3I Paper Input Media Self-Defining Field .................................................................................. 369
Colorant-Identification Self-Defining Field .................................................................................... 370
Device-Appearance Self-Defining Field ....................................................................................... 372

## Page 23

IPDS Reference xxiii
Keep-Group-T ogether-as-a-Recovery-Unit Self-Defining Field.......................................................... 373
Recognized Group ID Formats Self-Defining Field ......................................................................... 374
Supported Device Resolutions Self-Defining Field ......................................................................... 375
Object-Container Version Support Self-Defining Field ..................................................................... 376
Finishing Options Self-Defining Field .......................................................................................... 378
Printer Speed Self-Defining Field ............................................................................................... 379
Active Setup Name Self-Defining Field ........................................................................................ 380
XOH Page Counters Control.............................................................................................................. 381
XOH Print Buffered Data................................................................................................................... 382
XOH Remove Saved Page Group....................................................................................................... 383
Group ID (X'00') Triplet Considerations ............................................................................................ 383
XOH Select Input Media Source ......................................................................................................... 385
XOH Select Medium Modifications ...................................................................................................... 386
XOH Separate Continuous Forms....................................................................................................... 388
XOH Set Media Origin ...................................................................................................................... 389
XOH Set Media Size ........................................................................................................................ 397
XOH Specify Group Operation ........................................................................................................... 401
XOH Stack Received Pages .............................................................................................................. 408
XOH Trace .................................................................................................................................... 409
Acknowledge Reply for the XOH Trace Command ............................................................................. 414
Printer-Generated Trace Entries..................................................................................................... 414
Begin-Trace Trace Entry .......................................................................................................... 415
Begin-Page Trace Entry ........................................................................................................... 418
Begin-Overlay Trace Entry........................................................................................................ 423
Begin-Presentation-Object Trace Entry ....................................................................................... 425
CMRs-Used Trace Entry .......................................................................................................... 429
CMR-Activation Trace Entry...................................................................................................... 433
CMR-Invocation Trace Entry ..................................................................................................... 435
Media-Source-Selection Trace Entry........................................................................................... 437
Exception-ID Trace Entry ......................................................................................................... 438
Free-Form Trace Entry ............................................................................................................ 439
Include-Saved-Page Trace Entry ............................................................................................... 441
Include-Overlay Trace Entry...................................................................................................... 442
Include-Data-Object Trace Entry ................................................................................................ 444
Device-Appearance Trace Entry ................................................................................................ 446
Color-Fidelity Trace Entry ......................................................................................................... 447
CMR-T ag-Fidelity Trace Entry ................................................................................................... 448
Begin-Print-Unit Trace Entry ..................................................................................................... 449
Trace-Full Trace Entry ............................................................................................................. 451
End-Object Trace Entry............................................................................................................ 452
CMR-Deactivation Trace Entry .................................................................................................. 454
Chapter 5. Text Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 457
The T ext Presentation Space ................................................................................................................. 457
The T ext Object Area............................................................................................................................ 458
Mapping the T ext Presentation Space ...................................................................................................... 459
Interaction Between T ext Objects and T ext-Major T ext ................................................................................. 459
Load Equivalence................................................................................................................................ 460
Write T ext Control ................................................................................................................................ 462
T ext Area Position ........................................................................................................................... 463
T ext Output Control.......................................................................................................................... 466
Area Coloring Triplet Considerations ............................................................................................... 469
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations.................................................. 469
T ext Data Descriptor ........................................................................................................................ 470
Write T ext .......................................................................................................................................... 472
Spanning....................................................................................................................................... 472
Unicode Support ............................................................................................................................. 473
Unicode Complex T ext ................................................................................................................. 473
PTOCA Unicode Complex T ext (UCT) Control Sequence .................................................................... 473
PTOCA Glyph Layout Controls ...................................................................................................... 474
Control Sequence Summary.............................................................................................................. 476
Chapter 6. IM-Image Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 479

## Page 24

xxiv IPDS Reference
Write Image Control ............................................................................................................................. 481
Image Size .................................................................................................................................... 483
Input Image Data Format .................................................................................................................. 484
Image Magnification ........................................................................................................................ 485
Output Image Orientation.................................................................................................................. 486
Output Image Location ..................................................................................................................... 487
Image Color ................................................................................................................................... 489
Standard OCA Color-Value T able ................................................................................................... 489
Write Image ....................................................................................................................................... 491
Chapter 7. IO-Image Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 493
The IO-Image Presentation Space .......................................................................................................... 495
The IO-Image Object Area..................................................................................................................... 496
Mapping the IO-Image Presentation Space ............................................................................................... 496
Using IO Image as a Resource ............................................................................................................... 497
Write Image Control 2........................................................................................................................... 498
Image Area Position ........................................................................................................................ 500
Image Output Control....................................................................................................................... 503
Area Coloring Triplet Considerations ............................................................................................... 512
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations.................................................. 513
Image Data Descriptor ..................................................................................................................... 514
Write Image 2 ..................................................................................................................................... 517
Unsupported IOCA function in an IPDS Environment .............................................................................. 519
Chapter 8. Graphics Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 521
Drawing-Order Coordinate System.......................................................................................................... 522
Graphics Presentation Space Window ..................................................................................................... 523
Graphics Object Area ........................................................................................................................... 523
Positioning the Graphics Presentation Space Window ................................................................................. 524
Write Graphics Control ......................................................................................................................... 526
Graphics Area Position..................................................................................................................... 527
Graphics Output Control ................................................................................................................... 531
Area Coloring Triplet Considerations ............................................................................................... 534
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations.................................................. 534
Mapping Control Options .............................................................................................................. 535
Scale-to-Fit Mapping ............................................................................................................... 535
Scale-to-Fill Mapping............................................................................................................... 537
Center-and-Trim Mapping......................................................................................................... 538
Position-and-Trim Mapping ....................................................................................................... 539
Graphics Data Descriptor.................................................................................................................. 540
Write Graphics.................................................................................................................................... 543
Begin Segment Introducer................................................................................................................. 543
Drawing Orders .............................................................................................................................. 544
Chapter 9. Bar Code Command Set. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 547
Bar Code Presentation Space ................................................................................................................ 547
Bar Code Object Area .......................................................................................................................... 548
Positioning the Bar Code Presentation Space............................................................................................ 549
Write Bar Code Control......................................................................................................................... 550
Self-Defining Fields within the Write Bar Code Control ............................................................................ 550
Bar Code Area Position .................................................................................................................... 551
Bar Code Output Control .................................................................................................................. 555
Area Coloring Triplet Considerations ............................................................................................... 558
Invoke CMR (X'92') and Invoke T ertiary Resource X'A2' Triplet Considerations ........................................ 558
Bar Code Data Descriptor ................................................................................................................. 559
Color Specification (X'4E') Triplet Considerations............................................................................... 561
Write Bar Code ................................................................................................................................... 562
Chapter 10. Object Container Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 563
Object Container Presentation Space ...................................................................................................... 564
Object Container Object Area................................................................................................................. 564
Data Object Resource Equivalence Entries ............................................................................................... 565
Data Object Resource Equivalence ......................................................................................................... 569

## Page 25

IPDS Reference xxv
Data Object Resource Equivalence 2 ....................................................................................................... 571
Deactivate Data-Object-Font Component ................................................................................................. 573
Deactivate Data Object Resource ........................................................................................................... 574
Include Data Object ............................................................................................................................. 575
Data Object Area Position ................................................................................................................. 577
Data Object Output Control ............................................................................................................... 580
Override for Presentation Space Reset Mixing (X'70') triplet ................................................................. 584
Color Management Resources ...................................................................................................... 584
Data Object Data Descriptor .............................................................................................................. 585
Override for Color Specification (X'4E') Triplet ................................................................................... 586
Override for Object Offset (X'5A') Triplet .......................................................................................... 588
Override for Image Resolution (X'9A') Triplet..................................................................................... 588
Override for Object Container Presentation Space Size (X'9C') Triplet .................................................... 589
Remove Resident Resource .................................................................................................................. 590
Request Resident Resource List............................................................................................................. 592
Acknowledge Reply for Request Resident Resource List ......................................................................... 593
Write Object Container Control ............................................................................................................... 595
Object Container Area Position .......................................................................................................... 596
Object Container Output Control......................................................................................................... 599
Area Coloring Triplet Considerations ............................................................................................... 602
Invoke CMR (X'92') and Rendering Intent (X'95') Triplet Considerations.................................................. 602
Mapping Control Options .............................................................................................................. 603
Scale-to-Fit Mapping ............................................................................................................... 603
Center-and-Trim Mapping......................................................................................................... 604
Position-and-Trim Mapping ....................................................................................................... 605
Position Mapping.................................................................................................................... 606
Scale-to-Fill Mapping............................................................................................................... 606
UP
3I-Print-Data Mapping.......................................................................................................... 607
Object Container Data Descriptor ....................................................................................................... 609
Color Management Triplet Considerations........................................................................................ 610
Color Specification (X'4E') Triplet Considerations................................................................................611
Image Resolution (X'9A') Triplet Considerations .................................................................................611
Object Offset (X'5A') Triplet Considerations ...................................................................................... 612
Object Container Presentation Space Size (X'9C') Triplet Considerations................................................ 612
TrueType/OpenType Font Triplet Considerations ............................................................................... 612
Write Object Container ......................................................................................................................... 613
Chapter 11. Metadata Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 615
Delete Home-State Metadata ................................................................................................................. 616
Write Metadata Control ......................................................................................................................... 617
Metadata Data Descriptor ................................................................................................................. 618
Write Metadata ................................................................................................................................... 619
Chapter 12. Overlay Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 621
Overlay Command-Set Commands ......................................................................................................... 621
Begin Overlay..................................................................................................................................... 623
Deactivate Overlay .............................................................................................................................. 625
Include Overlay................................................................................................................................... 627
Chapter 13. Page-Segment Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 631
Page-Segment Command-Set Commands ............................................................................................... 631
Begin Page Segment ........................................................................................................................... 632
Deactivate Page Segment ..................................................................................................................... 633
Include Page Segment ......................................................................................................................... 634
Chapter 14. Loaded-Font Command Set . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 635
Graphic Character Placement Fundamentals ............................................................................................ 636
Font Inline Sequences...................................................................................................................... 637
Font Parameter Relationships............................................................................................................ 641
Characters Printed in the 0° Font Inline Sequence ............................................................................. 641
Characters Printed in the 90° Font Inline Sequence ............................................................................ 642
Characters Printed in the 180° Font Inline Sequence .......................................................................... 643
Characters Printed in the 270° Font Inline Sequence .......................................................................... 644

## Page 26

xxvi IPDS Reference
Printing a Kerned Character .............................................................................................................. 645
Printing an Underscore Character....................................................................................................... 645
Printing an Underscore with PTOCA PT2 ............................................................................................. 646
LF1-Type Coded-Font Command Summary .............................................................................................. 647
The Long Format LFI ....................................................................................................................... 648
The Short Format LFI ....................................................................................................................... 648
Parts of an LF1-Type Coded Font ....................................................................................................... 649
LF2-Type Coded-Font Command Summary .............................................................................................. 650
LF3-Type Coded-Font Command Summary .............................................................................................. 650
LF4-Type Code-Page Command Summary............................................................................................... 651
Invoking a Coded Font.......................................................................................................................... 651
Load Code Page ................................................................................................................................. 652
Load Code Page Control....................................................................................................................... 656
Load Font .......................................................................................................................................... 662
Load Font Character Set Control ............................................................................................................ 671
Load Font Control................................................................................................................................ 675
Load Font Index .................................................................................................................................. 687
Load Symbol Set ................................................................................................................................. 698
Chapter 15. Triplets . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 703
Group ID (X'00') Triplet ......................................................................................................................... 705
Coded Graphic Character Set Global Identifier (X'01') Triplet ........................................................................ 709
Fully Qualified Name (X'02') Triplet...........................................................................................................711
Color Specification (X'4E') Triplet ............................................................................................................ 713
Encoding Scheme ID (X'50') Triplet ......................................................................................................... 720
Object Offset (X'5A') Triplet.................................................................................................................... 722
Local Date and Time Stamp (X'62') Triplet................................................................................................. 724
Group Information (X'6E') Triplet ............................................................................................................. 727
Presentation Space Reset Mixing (X'70') Triplet ......................................................................................... 731
T oner Saver (X'74') Triplet ..................................................................................................................... 732
Color Fidelity (X'75') Triplet .................................................................................................................... 734
Metric Adjustment (X'79') Triplet ............................................................................................................. 737
Font Resolution and Metric T echnology (X'84') Triplet.................................................................................. 740
Finishing Operation (X'85') Triplet ........................................................................................................... 742
T ext Fidelity (X'86') Triplet ..................................................................................................................... 755
Finishing Fidelity (X'88') Triplet ............................................................................................................... 757
Data Object Font Descriptor (X'8B') Triplet ................................................................................................ 759
Linked Font (X'8D') Triplet ..................................................................................................................... 765
UP
3I Finishing Operation (X'8E') Triplet .................................................................................................... 767
Color Management Resource Descriptor (X'91') Triplet ................................................................................ 769
Invoke CMR (X'92') Triplet ..................................................................................................................... 772
Rendering Intent (X'95') Triplet ............................................................................................................... 774
CMR T ag Fidelity (X'96') Triplet............................................................................................................... 777
Device Appearance (X'97') Triplet ........................................................................................................... 779
Image Resolution (X'9A') Triplet.............................................................................................................. 781
Object Container Presentation Space Size (X'9C') Triplet ............................................................................. 783
Setup Name (X'9E') Triplet .................................................................................................................... 786
Invoke T ertiary Resource (X'A2') Triplet.................................................................................................... 787
Chapter 16. Exception Reporting . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 789
General Rules for Exceptions................................................................................................................. 789
Exception-Handling Control ................................................................................................................... 792
Exception Reporting ........................................................................................................................ 793
Alternate Exception Actions............................................................................................................... 793
Exception-Presentation Processing..................................................................................................... 794
Page Continuation ...................................................................................................................... 794
Exception Page Print ................................................................................................................... 794
Classes of Exceptions .......................................................................................................................... 795
Sense Byte Information ........................................................................................................................ 796
Formats for Sense Bytes 4-18 and 20-23.............................................................................................. 798
Action Codes (Sense Byte 2 for Printers That Return 24 Sense Bytes) ............................................................ 802
Exception Reporting Codes ................................................................................................................... 806
Exception Classes........................................................................................................................... 807

## Page 27

IPDS Reference xxvii
Printer Exception IDs ........................................................................................................................... 807
Command-Reject Exceptions ............................................................................................................ 808
Equipment Check with Intervention Required Exceptions......................................................................... 809
Intervention-Required Exceptions ........................................................................................................811
Equipment-Check Exceptions ............................................................................................................ 819
Data-Check Exceptions .................................................................................................................... 820
Specification Checks—Metadata Exceptions ......................................................................................... 824
Specification Checks—IO-Image Exceptions......................................................................................... 826
Specification Checks—Bar Code Exceptions ........................................................................................ 835
Specification Checks—Graphics Data Exceptions .................................................................................. 846
Specification Checks—General Exceptions .......................................................................................... 857
Conditions Requiring Host Notification ................................................................................................. 918
Data Object Error Codes ....................................................................................................................... 922
Error Codes for Anacomp and AnaStack Objects ................................................................................... 922
Error Codes for Color Mapping T able and Color Profile Objects ................................................................. 922
Error Codes for IO-Image Objects and IOCA Tile Resources..................................................................... 922
Error Codes for Other Data Objects..................................................................................................... 922
Page and Copy Counter Adjustments ...................................................................................................... 926
Page and Copy Counter Adjustments When a Data-Stream Exception Occurs ................................................. 929
Page Counter Scenarios ....................................................................................................................... 931
Introduction.................................................................................................................................... 931
Scenario 1 ..................................................................................................................................... 931
Scenario 2 ..................................................................................................................................... 932
Scenario 3 ..................................................................................................................................... 932
Scenario 4 ..................................................................................................................................... 933
Scenario 5 ..................................................................................................................................... 934
Scenario 6 ..................................................................................................................................... 935
Scenario 7 ..................................................................................................................................... 935
Scenario 8 ..................................................................................................................................... 936
Scenario 9 ..................................................................................................................................... 937
Scenario 10 ................................................................................................................................... 938
Scenario 11.................................................................................................................................... 939
Scenario 12 ................................................................................................................................... 940
Non-IPDS Sense Data.......................................................................................................................... 941
Non-IPDS Action Codes ................................................................................................................... 941
Command-Reject Exceptions ............................................................................................................ 944
Equipment Check with Intervention Required Exceptions......................................................................... 945
Intervention-Required Exceptions ....................................................................................................... 946
Reserved for Bus-Out Parity Check Exceptions ..................................................................................... 951
Equipment-Check Exceptions ............................................................................................................ 952
Data-Check Exceptions .................................................................................................................... 960
Specification Checks—IO-Image Exceptions......................................................................................... 965
Specification Checks—Bar Code Exceptions ........................................................................................ 966
Specification Checks—Graphics Data Exceptions .................................................................................. 967
Specification Check—General Exceptions ............................................................................................ 969
Conditions Requiring Host Notification ................................................................................................. 976
Chapter 17. Compliance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 977
IPDS Functional Divisions ..................................................................................................................... 977
IPDS Command Sets and Command-Set Subsets...................................................................................... 979
Data Divisions .................................................................................................................................... 981
Data T owers and Data-T ower Levels ................................................................................................... 981
IPDS Support Requirements .................................................................................................................. 983
Command-Set Support Requirements ................................................................................................. 983
Data T ower Support Requirements ..................................................................................................... 983
IPDS Support for MO:DCA Interchange Set IS/3 ........................................................................................ 984
Required IPDS Command Set support................................................................................................. 984
Additional Required Support .............................................................................................................. 985
Additional required support for the MO:DCA GA (Graphic Arts) Function Set .................................................... 986
Migration Functions ............................................................................................................................. 987
Appendix A. IPDS Commands Sorted by Command Code . . . . . . . . . . . . . . . . . . . . . . . . 989

## Page 28

xxviii IPDS Reference
Appendix B. Examples of IPDS Command Sequences . . . . . . . . . . . . . . . . . . . . . . . . . . . 993
A Printer-Initialization Sequence ............................................................................................................. 995
The Page-Segment Sequence ............................................................................................................... 996
The Overlay Sequence ......................................................................................................................... 997
The Page Sequence ............................................................................................................................ 999
Appendix C. Image Compression and Recording Algorithms . . . . . . . . . . . . . . . . . . . . . 1001
Modified ITU—TSS Modified READ Algorithm (IBM MMR) .......................................................................... 1001
Run-Length 4 Compression Algorithm (RL4) ............................................................................................ 1002
ABIC (Bilevel Q-Coder) Compression Algorithm (ABIC) .............................................................................. 1003
Concatenated ABIC Compression Algorithm ............................................................................................ 1003
ITU—TSS T .4 Facsimile Coding Scheme (G3 MH, One-Dimensional)............................................................ 1003
ITU—TSS T .4 Facsimile Coding Scheme (G3 MR, Two-Dimensional) ............................................................ 1003
ITU—TSS T .6 Facsimile Coding Scheme (G4 MMR) Compression Algorithm .................................................. 1003
ISO/ITU—TSS JPEG Compression Algorithms ......................................................................................... 1003
JBIG2 (Joint Bi-level Image Experts Group) Compression Algorithm.............................................................. 1004
Solid Fill Rectangle ............................................................................................................................. 1004
TIFF LZW Compression Algorithm ......................................................................................................... 1004
TIFF LZW with Differencing Predictor Compression Algorithm...................................................................... 1004
RIDIC Recording Algorithm .................................................................................................................. 1005
Unpadded RIDIC Recording Algorithm .................................................................................................... 1005
Appendix D. Retired Items . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1007
Notices . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1023
Trademarks....................................................................................................................................... 1024
Glossary . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1027
Index . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1069

## Page 29

Copyright © AFP Consortium 1987, 2023 xxix
Figures
1. Presentation Environment ........................................................................................................................ 1
2. Presentation Model ................................................................................................................................ 3
3. Presentation Page.................................................................................................................................. 5
4. IPDS Products in the Spooled System Environment....................................................................................... 9
5. IPDS Products in the Mainframe Interactive Environment.............................................................................. 10
6. IPDS Products in the Workstation or Departmental System Environment.......................................................... 11
7. IPDS Products in the Local Area Network (LAN) Environment........................................................................ 12
8. IPDS Functional Divisions ...................................................................................................................... 16
9. IPDS Presentation Spaces ..................................................................................................................... 19
10. Object Areas in a Logical Page .............................................................................................................. 20
11. Examples of Multiple Pages on a Medium Presentation Space ..................................................................... 21
12. Logical Division of Continuous Forms for Cut-Sheet Emulation ..................................................................... 22
13. A Sample Page with an Overlay and Page Segment .................................................................................. 25
14. Merging Presentation Spaces ............................................................................................................... 29
15. Examples of Shaded Areas .................................................................................................................. 32
16. CMR-Usage Hierarchy ........................................................................................................................ 36
17. Selecting Appropriate Color-Conversion CMRs ......................................................................................... 40
18. X m,Ym Coordinate System: Recommended Default Media Origins................................................................. 49
19. X m,Ym Coordinate System - Other Allowed Default Media Origins ................................................................. 50
20. X m,Ym Coordinate System for Envelopes ................................................................................................. 51
21. Default Media Origin for Computer Output on 105 mm Microfilm (Shown Cut into Microfiche).............................. 52
22. Default Media Origin for Computer Output on 16 mm Microfilm (CINE Representation) ...................................... 52
23. Default Media Origin for Computer Output on 16 mm Microfilm (COMIC Representation) ................................... 52
24. Locating Data by X m,Ym and Xp,Yp Coordinates ......................................................................................... 53
25. Object Area Rotation in X p,Yp Coordinate System ...................................................................................... 54
26. The I,B Coordinate System on the Logical Page ........................................................................................ 55
27. The Usable I,B T ext Orientations............................................................................................................ 56
28. Object Area Rotation in I,B Coordinate System, Part 1................................................................................ 57
29. Object Area Rotation in I,B Coordinate System, Part 2................................................................................ 58
30. 15 Degree Object Area Rotation in I,B Coordinate System........................................................................... 59
31. Calculating the Current T ext Position ...................................................................................................... 61
32. Example of the Valid Printable Area ........................................................................................................ 62
33. Example of the User’s Valid Printable Area............................................................................................... 64
34. Logical Position and Next Pel to Be Printed for Four I,B Orientations ............................................................. 65
35. An Example of IPDS Command Processing ............................................................................................. 66
36. Relationship between Presentation-Object States (other than IM-Image State) and Metadata State ..................... 73
37. Relationship between Home State, Page State, and the Presentation-Object States, and between Page State
and Metadata State ........................................................................................................................... 74
38. Relationship between Home State, Overlay State, and the Presentation-Object States, and between Overlay
State and Metadata State ................................................................................................................... 75
39. Relationship between Home State, Page Segment State, and the Presentation-Object States ............................ 76
40. Relationship between Home State and Font State ..................................................................................... 77
41. Relationship between Home State and Code Page State ............................................................................ 78
42. Relationship between Home State and IO-Image Resource State, and between IO-Image Resource State and
Metadata State ................................................................................................................................. 79
43. Relationship between Home State and Object-Container Resource State, and between Object-Container
Resource State and Metadata State ...................................................................................................... 80
44. Relationship between Other States and Metadata State .............................................................................. 81
45. The Complete IPDS State Diagram ........................................................................................................ 87
46. Examples of IPDS Commands Involved with Saving and Including Pages..................................................... 106
47. TrueType Font Horizontal Metrics......................................................................................................... 107
48. TrueType Font Vertical Metrics ............................................................................................................ 109
49. Some Exception-Handling Combinations ................................................................................................116
50. X m-Axis and Y m-Axis for Duplex Printing ................................................................................................ 183
51. N-up Partitions for Various Physical Media ............................................................................................. 184
52. N-up Partition Layouts with SMO = X'00'................................................................................................ 186
53. N-up Partition Layouts with SMO = X'01'................................................................................................ 186
54. N-up Partition Layouts with SMO = X'02'................................................................................................ 187
55. N-up Partition Layouts with SMO = X'03'................................................................................................ 187

## Page 30

xxx IPDS Reference
56. Using the LPP Command to Position the Logical Page When There Is One Page per Side ............................... 210
57. Page Positioning and Orientation Examples ............................................................................................211
58. Continuous-Forms, Duplex, 2-Up Example ............................................................................................ 212
59. Example Showing Three Edge Marks ................................................................................................... 272
60. Exception-Handling Control (spans four pages)....................................................................................... 285
61. Examples of Groups and Group Operations ........................................................................................... 319
62. Examples of Nested Finishing Operations.............................................................................................. 320
63. BCOCA Bar Code Subsets ................................................................................................................. 352
64. The XOH Set Media Origin Command (Cut-Sheet Media) ......................................................................... 390
65. The XOH Set Media Origin Command (Wide Continuous-Forms Media)....................................................... 391
66. The XOH Set Media Origin Command (Narrow Continuous-Forms Media) .................................................... 392
67. Examples of Commonly Used SMO/Duplex Combinations ........................................................................ 393
68. The XOH Set Media Origin Command (Front Side of an Envelope).............................................................. 394
69. The XOH Set Media Origin Command (Back Side of an X
m-Axis Duplex Envelope)......................................... 394
70. The XOH Set Media Origin Command (Back Side of a Y m-Axis Duplex Envelope) .......................................... 394
71. Examples Showing the Effect of SMS (Method 3) and SMO Command Combinations ..................................... 400
72. T ext Presentation Space .................................................................................................................... 457
73. Locating, Sizing, and Orienting the T ext Object Area ................................................................................ 458
74. Example of Position Mapping for T ext (Error Case) .................................................................................. 468
75. IM Image Where the Output Size Is Less Than the Input Size..................................................................... 484
76. IM Image Where the Output Size is Greater Than the Input Size ................................................................. 484
77. Example of IM-Image Magnification and Replication Where the Output Size Is Greater Than the Input
Size ............................................................................................................................................. 485
78. IO-Image Presentation Space ............................................................................................................. 495
79. Locating, Sizing, and Orienting the Image Object Area ............................................................................. 499
80. Example of Scale-to-Fit Mapping ......................................................................................................... 506
81. Example of Center-and-Trim Mapping................................................................................................... 507
82. Example of Position-and-Trim Mapping ................................................................................................. 508
83. Example of Replicate-and-Trim Mapping ............................................................................................... 510
84. Example of IO-Image Scale-to-Fill Mapping.............................................................................................511
85. Graphics Presentation Space.............................................................................................................. 522
86. Graphics Mapping ............................................................................................................................ 525
87. Locating, Sizing, and Orienting the Graphics Object Area.......................................................................... 527
88. An Example of Graphics Scale-to-Fit Mapping ........................................................................................ 536
89. Example of Graphics Scale-to-Fill Mapping ............................................................................................ 537
90. Example of Graphics Center-and-Trim Mapping ...................................................................................... 538
91. Example of Graphics Position-and-Trim Mapping .................................................................................... 539
92. Example of the Bar Code Presentation Space Mapped into the Bar Code Object Area..................................... 549
93. Locating the Bar Code Object Area ...................................................................................................... 551
94. Bar Code Symbols within the Bar Code Presentation Space ...................................................................... 559
95. Locating, Sizing, and Orienting the Object Container Object Area ............................................................... 565
96. An Example of Object Container Scale-to-Fit Mapping.............................................................................. 603
97. Example of Object Container Center-and-Trim Mapping............................................................................ 604
98. Example of Object Container Position-and-Trim Mapping .......................................................................... 605
99. Example of Object Container Scale-to-Fill Mapping.................................................................................. 606
100. Example of Object Container UP 3I-Print-Data Mapping ........................................................................... 607
101. Example of Various Font Inline Sequences Producing the Same Character Orientation.................................. 637
102. Rotation of Character Patterns........................................................................................................... 638
103. The 32 Ways to Print T ext ................................................................................................................. 639
104. Font Inline Sequence of 0° ................................................................................................................ 641
105. Font Inline Sequence of 90° .............................................................................................................. 642
106. Font Inline Sequence of 180°............................................................................................................. 643
107. Font Inline Sequence of 270°............................................................................................................. 644
108. Left-Kerned and Right-Kerned Character ............................................................................................. 645
109. Underscore Character ..................................................................................................................... 645
110. Examples of Underscores Created by the PTOCA Underscore Control Sequence......................................... 646
111. Overview of Sample Double-Byte Coded Font Section Records ................................................................ 649
112. Example of the V(y) and W(y) Values .................................................................................................. 670
113. Character Rotation with Respect to the Logical Page Coordinate System .................................................... 691
114. Examples of Finishing Operations (spans three pages) ........................................................................... 750
115. Reference Edges for Various Kinds of Media......................................................................................... 753
116. Character Placement Based on Character Rotation, Inline Direction, and Baseline Direction ........................... 762

## Page 31

IPDS Reference xxxi
117. Layout of a Negative Acknowledge Reply (NACK).................................................................................. 797
118. Logical Paper Path and Page Counters................................................................................................ 802
119. IPDS Functional Divisions ................................................................................................................. 978
120. Data T owers and Data-T ower Levels ................................................................................................... 982
121. RIDIC Raster Scan......................................................................................................................... 1005
122. Example of GOC Replicate-and-Trim Mapping (retired item 135).............................................................. 1020

## Page 32

xxxii IPDS Reference

## Page 33

Copyright © AFP Consortium 1987, 2023 xxxiii
Tables
1. AFP Consortium Architecture References ................................................................................................. xiv
2. Additional AFP Consortium Documentation ............................................................................................... xiv
3. AFP Font-Related Documentation ........................................................................................................... xiv
4. UP 3I Architecture Documentation .............................................................................................................xv
5. Foreground and Background .................................................................................................................. 27
6. Default Mixing Rules ............................................................................................................................. 31
7. Area Coloring ...................................................................................................................................... 33
8. Object Coloring.................................................................................................................................... 33
9. Position Exceptions for Presentation Objects ............................................................................................. 64
10. Field Ranges for Commonly-Supported Measurement Bases....................................................................... 69
11. IPDS Command Code Summary............................................................................................................ 83
12. Installation and Removal of Downloaded Resources .................................................................................. 93
13. Activation and Deactivation of Downloaded Resources ............................................................................... 94
14. Activation and Deactivation of Resident Resources, Coded Fonts, and Data-Object Fonts ................................. 95
15. Invocation of Resources....................................................................................................................... 98
16. IO Images used as Resources............................................................................................................... 99
17. Object Containers Used in the IPDS Environment...................................................................................... 99
18. Resource Management Summary ......................................................................................................... 111
19. Device Control Commands ................................................................................................................. 123
20. Acknowledge Protocol ....................................................................................................................... 123
21. Acknowledge Types .......................................................................................................................... 128
22. Valid RID Entry Lengths ..................................................................................................................... 138
23. RT/RIDF Triplet Combinations............................................................................................................. 155
24. Required and Optional Deactivation Types............................................................................................. 165
25. LCC Keywords................................................................................................................................. 178
26. Media Source Commands .................................................................................................................. 180
27. Exception Continuation Rules ............................................................................................................. 194
28. XOA Order Summary ........................................................................................................................ 268
29. Architecturally-Valid RT and RIDF Query Combinations ............................................................................ 298
30. XOH Order Summary ........................................................................................................................ 313
31. Group Operation Nesting ................................................................................................................... 318
32. Triplets Used With Each Group Operation.............................................................................................. 322
33. OPC Self-Defining Field Summary ....................................................................................................... 329
34. Common Values for Bar Code Types and Modifiers.................................................................................. 349
35. Relationship Between SDF X'000E' and X'000F'...................................................................................... 353
36. Printer-Generated Trace Entries .......................................................................................................... 414
37. T ext Commands ............................................................................................................................... 457
38. Summary of Control Sequences .......................................................................................................... 476
39. IM-Image Commands ........................................................................................................................ 479
40. IM-Image and IO-Image Comparison .................................................................................................... 479
41. Standard OCA Color-Value T able ......................................................................................................... 489
42. IO-Image Commands ........................................................................................................................ 493
43. IM-Image and IO-Image Comparison .................................................................................................... 493
44. IOCA Self-Defining Fields................................................................................................................... 517
45. Exception IDs for IOCA Function Sets ................................................................................................... 519
46. Graphics Commands ........................................................................................................................ 521
47. Summary of GOCA Drawing Orders ..................................................................................................... 544
48. Additional Drawing Orders Supported by Some Printers............................................................................ 546
49. Bar Code Commands ........................................................................................................................ 547
50. Object Container Commands .............................................................................................................. 563
51. Secondary Resource Usage ............................................................................................................... 566
52. Metadata Commands
........................................................................................................................ 615
53. Overlay Commands .......................................................................................................................... 621
54. Page Segment Commands................................................................................................................. 631
55. Loaded-Font Commands ................................................................................................................... 635
56. Identifying the Baseline Offset Value ..................................................................................................... 640
57. Triplets Summary ............................................................................................................................. 703
58. Standard OCA Color-Value T able ......................................................................................................... 717
59. Color Space Examples ...................................................................................................................... 718

## Page 34

xxxiv IPDS Reference
60. Examples of the Date Fields ............................................................................................................... 726
61. Sheet and Group Operations .............................................................................................................. 744
62. CMR Processing Modes .................................................................................................................... 769
63. Exception ID Specific Information......................................................................................................... 799
64. Action Codes ................................................................................................................................... 802
65. Error Codes for Data Objects .............................................................................................................. 922
66. Method of Adjusting the Counters ........................................................................................................ 926
67. Method of Adjusting the Counters When a Data-Stream Exception Occurs ................................................... 929
68. Retired Non-IPDS Action Codes .......................................................................................................... 941
69. Command-Set and Data-T ower Summary .............................................................................................. 981
70. IPDS Command Code Summary ......................................................................................................... 989
71. IPDS Acknowledge Reply................................................................................................................... 991
72. A Typical IPDS Command Sequence .................................................................................................... 993
73. Run-Length 4 Compression ............................................................................................................... 1002
74. Object Containers Used in the IPDS Environment (retired item 136) ........................................................... 1021
75. Object Containers Used in the IPDS Environment (retired item 137) ........................................................... 1021
76. Object Containers Used in the IPDS Environment (retired item 138) ........................................................... 1021

## Page 35

Copyright © AFP Consortium 1987, 2023 1
