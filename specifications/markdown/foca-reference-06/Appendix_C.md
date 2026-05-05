Appendix C. Pattern Technology Information
The following information defines the technologies which have been implemented for the definition of character
shapes. Each technology is associated with a value of the T echnology parameter in the Font Control
Structured Field. This information is provided for information only and is not considered part of the font
architecture, nor is it under control mode.
CID Keyed Outline Font Technology
The data format for this outline technology is documented in Adobe Type 1 Font Format, Adobe Systems
Incorporated, 1990, and Composite Font Extensions, Adobe Systems Incorporated, 1989.
The following notes apply to the FOCA implementation of the CID Keyed outline font technology:
• The CID Keyed font files are totally contained within the FOCA Pattern Data parameter.
• All character string data within the CID Keyed font file uses the ASCII character encoding technique.
• The glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM Graphic
Character Global Identifiers (or other names as defined by the Graphic Character GID Encoding parameter).
Implementations must consider the possibility of different graphic character GID encodings and perform any
necessary mapping.
Type 1 PFB Outline Font Technology
The data format for this outline technology is documented in Adobe Type 1 Font Format, Adobe Systems
Incorporated, 1990.
The following notes apply to the FOCA implementation of the Adobe Type 1 PFB outline font technology:
• The Type 1 PFB file is totally contained within the FOCA Pattern Data parameter.
• All character string data within the Type 1 PFB file uses the ASCII character encoding technique.
• The PFB glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM
Graphic Character Global Identifiers (or other names as defined by the Graphic Character GID Encoding
parameter). Implementations must consider the possibility of different graphic character GID encodings and
perform any necessary mapping.

## Page 224

202 FOCA Reference
TrueType/OpenType Outline Font Technology
The data format for the TrueType outline technology is documented in the TrueType Font Files Technical
Specification from Microsoft® Corporation and the TrueType Reference Manual from Apple Computer, Inc.
The OpenType font format is an extension of the TrueType font format that allows better support for
international character sets and broader multi-platform support. The OpenType font format, which was
developed jointly by the Adobe and Microsoft Corporations, is further described in the following document
available from the Microsoft web site:
OpenType Specification - Microsoft Corporation
Note: These technologies are not supported within FOCA font objects, but are supported within AFP data
streams as data object resources.
Laser Matrix N-Bit Wide Horizontal Sections
An image is formatted as a single rectangle in the binary element sequence of a unidirectional raster scan with
no interlaced fields and with parallel raster lines, from left to right (plus x-direction), from top to bottom (plus y-
direction). each binary element representing a pel, after decompression, without grayscale, is 0 for no dot, 1 for
dot. More than one binary element can represent a pel, after decompression, corresponding to a grayscale
algorithm. A pel is nominally centered about the point at which it appears.
Scan lines may range from 1 bit wide to the device limit.

## Page 225

Copyright © AFP Consortium 1998, 2015 203
Notices
The AFP Consortium or consortium member companies might have patents or pending patent applications
covering subject matter described in this document. The furnishing of this document does not give you any
license to these patents.
The following statement does not apply to the United Kingdom or any other country where such
provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICATION "AS IS"
WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT , MERCHANTABILITY OR FITNESS FOR A
PARTICULAR PURPOSE. Some states do not allow disclaimer of express or implied warranties in certain
transactions, therefore, this statement might not apply to you.
This publication could include technical inaccuracies or typographical errors. Changes are periodically made to
the information herein; these changes will be incorporated in new editions of the publication. The AFP
Consortium might make improvements and/or changes in the architecture described in this publication at any
time without notice.
Any references in this publication to Web sites are provided for convenience only and do not in any manner
serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for
this architecture and use of those Web sites is at your own risk.
The AFP Consortium may use or distribute any information you supply in any way it believes appropriate
without incurring any obligation to you.
This information contains examples of data and reports used in daily business operations. T o illustrate them in
a complete manner, some examples include the names of individuals, companies, brands, or products. These
names are fictitious and any similarity to the names and addresses used by an actual business enterprise is
entirely coincidental.

## Page 226

204 FOCA Reference
Trademarks
These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries,
or both:
ACMA
Advanced Function Presentation
AFP
AFPCC
AFP Color Consortium
AFP Color Management Architecture
Bar Code Object Content Architecture
BCOCA
CMOCA
Color Management Object Content Architecture
InfoPrint®
Intelligent Printer Data Stream
IPDS
Mixed Object Document Content Architecture
MO:DCA
Ricoh
®
Adobe®, the Adobe logo, PostScript ®, and the PostScript logo are either registered trademarks or trademarks
of Adobe Systems Incorporated in the United States and/or other countries.
AFPC and AFP Consortium are trademarks of the AFP Consortium.
IBM® is a registered trademark of the International Business Machines Corporation in the United States, other
countries, or both. MVS, PrintManager, Print Services Facility, SAA ®, and Systems Application Architecture ®
are trademarks or registered trademarks of the International Business Machines Corporation in the United
States, other countries, or both.
ISO® is a registered trademark of the International Organization for Standardization.
Microsoft® is a registered trademark of the Microsoft Corporation.
UP3I is a trademark of UP 3I Limited.
Other company, product, or service names might be trademarks or service marks of others.
Trademarks

## Page 227

Copyright © AFP Consortium 1998, 2015 205
Glossary
This glossary contains terms that apply to the FOCA
Architecture and also terms that apply to other
related presentation architectures.
If you do not find the term that you are looking for,
please refer to the IBM Dictionary of Computing,
document number ZC20-1699 or the InfoPrint
Dictionary of Printing.
The following definitions are provided as supporting
information only, and are not intended to be used as
a substitute for the semantics described in the body
of this reference.
A
absolute coordinate. One of the coordinates that identify
the location of an addressable point with respect to the
origin of a specified coordinate system. Contrast with
relative coordinate.
absolute move. A method used to designate a new
presentation position by specifying the distance from the
designated axes to the new presentation position. The
reference for locating the new presentation position is a
fixed position as opposed to the current presentation
position.
absolute positioning. The establishment of a position
within a coordinate system as an offset from the coordinate
system origin. Contrast with relative positioning.
Abstract Syntax Notation One (ASN.1). A notation for
defining data structures and data types. The notation is
defined in international standard ISO/IEC 8824(E). See
also object identifier.
ACK. See Positive Acknowledge Reply.
Acknowledge Reply. A printer-to-host reply that returns
printer information or reports exceptions. An Acknowledge
Reply can be positive or negative. See also Positive
Acknowledge Reply and Negative Acknowledge Reply.
Acknowledgment Request. A request from the host for
information from the printer. An example of an
Acknowledgment Request is the use of the
acknowledgment-required flag by a host system to request
an Acknowledge Reply from an attached printer.
acknowledgment-required flag (ARQ). A flag that
requests a printer to return an Acknowledge Reply. The
acknowledgment-required flag is bit zero of an IPDS
command's flag byte.
active coded font. The coded font that is currently being
used by a product to process text.
additive primary colors. Red, green, and blue light,
transmitted in video monitors and televisions. When used
in various degrees of intensity and variation, they create all
other colors of light; when superimposed equally, they
create white. Contrast with subtractive primary colors.
addressable position. A position in a presentation space
or on a physical medium that can be identified by a
coordinate from the coordinate system of the presentation
space or physical medium. See also picture element.
Synonymous with position.
Advanced Function Presentation (AFP). An open
architecture for the management of presentable
information that is developed by the AFP Consortium
(AFPC). AFP comprises a number of data stream and data
object architectures:
• Mixed Object Document Content (MO:DCA) Architecture;
formerly referred to as AFPDS
• Intelligent Printer Data Stream (IPDS) Architecture
• AFP Line Data Architecture
• Bar Code Object Content Architecture (BCOCA)
• Color Management Object Content Architecture
(CMOCA)
• Font Object Content Architecture (FOCA)
• Graphics Object Content Architecture for AFP (AFP
GOCA)
• Image Object Content Architecture (IOCA)
• Metadata Object Content Architecture (MOCA)
• Presentation T ext Object Content Architecture (PTOCA)
AEA. See alternate exception action.
AFM file. A file containing the metric information required
for positioning the characters of a font. The metric
information contained in this file was extracted from a PFB
file, in an ASCII file format defined by Adobe Systems Inc.,
and used for character positioning and page formatting.
AFP . See Advanced Function Presentation.
AFP Consortium (AFPC). A formal open standards body
that develops and maintains AFP architecture. Information
about the consortium can be found at www.afpcinc.org.
AFP data stream. A presentation data stream that is
processed in AFP environments. The MO:DCA
architecture defines the strategic AFP interchange data
stream. The IPDS architecture defines the strategic AFP
printer data stream.
AFPDS. A term formerly used to identify the composed-
page MO:DCA-based data stream interchanged in AFP
environments. See also MO:DCA and AFP data stream.
AIAG. See Automotive Industry Action Group.
AIM. See Automatic Identification Manufacturers, Inc.
all points addressable (APA). The capability to address,
reference, and position data elements at any addressable

## Page 228

206 FOCA Reference
position in a presentation space or on a physical medium.
Contrast with character cell addressable, in which the
presentation space is divided into a fixed number of
character-size rectangles in which characters can appear.
Only the cells are addressable. An example of all points
addressable is the positioning of text, graphics, and
images at any addressable point on the physical medium.
See also picture element.
alternate exception action (AEA). In the IPDS
architecture, a defined action that a printer can take when
a clearly defined, but unsupported, request is received.
Control over alternate exception actions is specified by an
Execute Order Anystate Exception-Handling Control
command.
American National Standards Institute (ANSI). An
organization consisting of producers, consumers, and
general interest groups. ANSI establishes the procedures
by which accredited organizations create and maintain
voluntary industry standards in the United States. It is the
United States constituent body of the International
Organization for Standardization (ISO).
anamorphic scaling. Scaling an object differently in the
vertical and horizontal directions. See also scaling,
horizontal font size, and vertical font size.
annotation. A comment or explanation associated with
the contents of a document component. An example of an
annotation is a string of text that represents a comment on
an image object on a page.
annotation link. In MO:DCA, a link type that specifies the
linkage from a source document component to a target
document component that contains an annotation.
annotation object. In MO:DCA, an object that contains
an annotation. Objects that are targets of annotation links
are annotation objects.
ANSI. See American National Standards Institute.
APA. See all points addressable.
append. In MO:DCA, an addition to or continuation of the
contents of a document component. An example of an
append is a string of text that is an addition to an existing
string of text on a page.
append link. In MO:DCA, a link type that specifies the
linkage from the end of a source document component to a
target document component that contains an append.
append object. In MO:DCA, an object that contains an
append. Objects that are targets of append links are
append objects.
application. (1) The use to which an information system
is put. (2) A collection of software components used to
perform specific types of work on a computer.
application program. A program written for or by a user
that applies to the user's work.
arc. A continuous portion of the curved line of a circle or
ellipse. See also full arc.
architected. Identifies data that is defined and controlled
by an architecture. Contrast with unarchitected.
arc parameters. Variables that specify the curvature of
an arc.
area. In GOCA, a set of closed figures that can be filled
with a pattern or a color.
area filling. A method used to fill an area with a pattern or
a color.
ARQ. See acknowledgment-required flag.
article. The physical item that a bar code identifies.
ascender. The parts of certain lowercase letters, such as
b, d, or f, that at zero-degree character rotation rise above
the top edge of other lowercase letters such as a, c, and e.
Contrast with descender.
ascender height. The character shape's most positive
character coordinate system Y-axis value.
ASN.1. See Abstract Syntax Notation One.
A-space. The distance from the character reference point
to the least positive character coordinate system X-axis
value of the character shape. A-space can be positive,
zero, or negative. See also B-space and C-space.
aspect ratio. (1) The ratio of the horizontal size of a
picture to the vertical size of the picture. (2) In a bar code
symbol, the ratio of bar height to symbol length.
asynchronous exception. Any exception other than
those used to report a synchronous data-stream defect
(action code X'01' or X'1F'), function no longer achievable
(action code X'06'), or synchronous resource-storage
problem (action code X'0C'). Asynchronous exceptions
occur after the received page station. An example of an
asynchronous exception is a paper jam. See also data-
stream exception. Contrast with synchronous exception.
attribute. A property or characteristic of one or more
constructs. See also character attribute, color attribute,
current drawing attributes, default drawing attributes, line
attributes, marker attributes, and pattern attributes.
Automatic Identification Manufacturers, Inc. (AIM). A
trade organization consisting of manufacturers, suppliers,
and users of bar codes.
Automotive Industry Action Group (AIAG). The
coalition of automobile manufacturers and suppliers
alternate exception action (AEA) • Automotive Industry Action Group (AIAG)

## Page 229

FOCA Reference 207
working to standardize electronic communications within
the auto industry.
B
bc. See current baseline print coordinate.
bi. See initial baseline print coordinate.
B. See baseline direction.
+B. Positive baseline direction.
Bc. See current baseline presentation coordinate.
Bo. See baseline presentation origin.
background. (1) The part of a presentation space that is
not occupied with object data. (2) In GOCA, that portion of
a graphics primitive that is mixed into the presentation
space under the control of the current values of the
background mix and background color attributes. Contrast
with foreground. (3) In GOCA, that portion of a character
cell that does not represent a character. (4) In bar codes,
the spaces, quiet zones, and area surrounding a printed
bar code symbol.
background color. The color of a background. Contrast
with foreground color.
background mix. (1) An attribute that determines how
the color of the background of a graphics primitive is
combined with the existing color of the graphics
presentation space. (2) An attribute that determines how
the points in overlapping presentation space backgrounds
are combined. Contrast with foreground mix.
band. An arbitrary layer of an image. An image can
consist of one or more bands of data.
bar. In bar codes, the darker element of a printed bar
code symbol. See also element. Contrast with space.
bar code. An array of elements, such as bars, spaces,
and two-dimensional modules
that together represent data
elements or characters in a particular symbology. The
elements are arranged in a predetermined pattern
following unambiguous rules defined by the symbology.
See also bar code symbol.
Bar Code command set. In the IPDS architecture, a
collection of commands used to present bar code symbols
in a page, page segment, or overlay.
bar code density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also character density, density, and
information density.
bar code object area. The rectangular area on a logical
page into which a bar code presentation space is mapped.
Bar Code Object Content Architecture (BCOCA). An
architected collection of constructs used to interchange
and present bar code data.
bar code presentation space. A two-dimensional
conceptual space in which bar code symbols are
generated.
bar code symbol. A combination of characters including
start and stop characters, quiet zones, data characters,
and check characters required by a particular symbology,
that form a complete, scannable entity. See also bar code.
bar code symbology. A bar code language. Bar code
symbologies are defined and controlled by various industry
groups and standards organizations. Bar code
symbologies are described in public domain bar code
specification documents. Synonymous with symbology.
See also Canadian Grocery Product Code (CGPC),
European Article Numbering (EAN), Japanese Article
Numbering (JAN), and Universal Product Code (UPC).
bar height. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar length and height.
bar length. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar height and height.
bar width. In bar codes, the thickness of a bar measured
from the edge closest to the symbol start character to the
trailing edge of the same bar.
bar width reduction. In bar codes, the reduction of the
nominal bar width dimension on film masters or printing
plates to compensate for systematic errors in some printing
processes.
base-and-towers concept. A conceptual illustration of an
architecture that shows the architecture as a base with
optional towers. The base and the towers represent
different degrees of function achieved by the architecture.
base support level. Within the base-and-towers concept,
the smallest portion of architected function that is allowed
to be implemented. This is represented by a base with no
towers. Synonymous with mandatory support level.
baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with printing baseline and
sequential baseline.
baseline coordinate. One of a pair of values that identify
the position of an addressable position with respect to the
origin of a specified I,B coordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,B coordinate system. Synonymous with B-
coordinate.
baseline direction (B). The direction in which successive
lines of text appear on a logical page. Synonymous with
baseline progression and B-direction.
bc • baseline direction (B)

## Page 230

208 FOCA Reference
baseline extent. A rectangular space oriented around the
character baseline and having one dimension parallel to
the character baseline. The space is measured along the Y
axis of the character coordinate system. For bounded
character boxes, the baseline extent at any rotation is its
character coordinate system Y-axis extent. Baseline extent
varies with character rotation. See also maximum baseline
extent.
baseline increment. The distance between successive
baselines.
baseline offset. The perpendicular distance from the
character baseline to the character box edge that is parallel
to the baseline and has the more positive character
coordinate system Y-axis value. For characters entirely
within the negative Y-axis region, the baseline offset can be
zero or negative. An example is a subscript character.
Baseline offset can vary with character rotation.
baseline presentation origin (B
o). The point on the B
axis where the value of the baseline coordinate is zero.
baseline progression (B). The direction in which
successive lines of text appear on a logical page.
Synonymous with baseline direction and B-direction.
B axis. The axis of the I,B coordinate system that extends
in the baseline or B-direction. The B axis does not have to
be parallel to the Y p axis of its bounding Xp,Yp coordinate
space.
BCOCA. See Bar Code Object Content Architecture.
B-coordinate. One of a pair of values that identify the
position of an addressable position with respect to the
origin of a specified I,B coordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,B coordinate system. Synonymous with
baseline coordinate.
B-direction (B). The direction in which successive lines
of text appear on a logical page. Synonymous with
baseline direction and baseline progression.
Bearer Bars. Bars that surround an Interleaved 2-of-5 bar
code to prevent misreads and short scans that might occur
when a skewed scanning beam enters or exits the bar
code symbol through its top or bottom edge. When plates
are used in the printing process, Bearer Bars help equalize
the pressure exerted by the printing plate over the entire
surface of the symbol to improve print quality. There are
two styles: 1) four bars that completely surround the
bar/space pattern and 2) two bars that are placed at the top
and the bottom of the bar/space pattern.
Begin Segment Introducer (BSI). An IPDS graphics
self-defining field that precedes all of the drawing orders in
a graphics segment.
between-the-pels. The concept of pel positioning that
establishes the location of a pel's reference point at the
edge of the pel nearest to the preceding pel rather than
through the center of the pel.
B-extent. The extent in the B-axis direction of an I,B
coordinate system. The B-extent must be parallel to one of
the axes of the coordinate system that contains the I,B
coordinate system. The B-extent is parallel to the Y
p-extent
when the B axis is parallel to the Y p axis or to the Xp-extent
when the B axis is parallel to the X p axis.
bilevel custom pattern. In GOCA, a custom pattern that
is defined as uncolored, then has a single color assigned to
it when it is used to fill an area. Contrast with full-color
custom pattern.
BITS. A data type for architecture syntax, indicating one
or more bytes to be interpreted as bit string information.
blend. A mixing rule in which the intersection of part of a
new presentation space Pnew with part of an existing
presentation space P existing changes to a new color attribute
that represents a color-mixing of the color attributes of P new
with the color attributes of P existing. For example, if P new has
foreground color-attribute blue and P existing has foreground
color-attribute yellow, the area where the two foregrounds
intersect changes to a color attribute of green. See also
mixing rule. Contrast with overpaint and underpaint.
body. (1) On a printed page, the area between the top
and bottom margins that can contain data. (2) In a book,
the portion between the front matter and the back matter.
boldface. A heavy-faced type. Printing in a heavy-faced
type.
boundary alignment. A method used to align image data
elements by adding padding bits to each image data
element.
bounded character box. A conceptual rectangular box,
with two sides parallel to the character baseline, that
circumscribes a character and is just large enough to
contain the character, that is, just touching the shape on all
four sides.
BSI. See Begin Segment Introducer.
B-space. The distance between the character coordinate
system X-axis values of the two extremities of a character
shape. See also A-space and C-space.
buffered pages. Pages and copies of pages that have
been received but not yet reflected in committed page
counters and copy counters.
C
Canadian Grocery Product Code (CGPC). The bar
code symbology used to code grocery items in Canada.
baseline extent • Canadian Grocery Product Code (CGPC)

## Page 231

FOCA Reference 209
cap-M height. The average height of the uppercase
characters in a font. This value is specified by the designer
of a font and is usually the height of the uppercase M.
CCSID. See Coded Character Set Identifier.
CGCSGID. See Coded Graphic Character Set Global
Identifier.
CGPC. See Canadian Grocery Product Code.
CHAR. A data type for architecture syntax, indicating one
or more bytes to be interpreted as character information.
character. (1) A member of a set of elements used for the
organization, control, or representation of data. A character
can be either a graphic character or a control character.
See also graphic character and control character. (2) In
bar codes, a single group of bar code elements that
represent an individual number, letter, punctuation mark, or
other symbol.
character angle. The angle that is between the baseline
of a character string and the horizontal axis of a
presentation space or physical medium.
character attribute. A characteristic that controls the
appearance of a character or character string.
character baseline. A conceptual reference line that is
coincident with the X axis of the character coordinate
system.
character box. A conceptual rectangular box with two
sides parallel to the character baseline. A character's
shape is formed within a character box by a presentation
process, and the character box is then positioned in a
presentation space or on a physical medium. The
character box can be rotated before it is positioned.
character-box reference edges. The four edges of a
character box.
character cell addressable. Allowing characters to be
addressed, referenced, and positioned only in a fixed
number of character-size rectangles into which a
presentation space is divided. Contrast with all points
addressable.
character cell size. The size of a rectangle in a drawing
space used to scale font symbols into the drawing space.
character code. An element of a code page or a cell in a
code table to which a character can be assigned. The
element is associated with a binary value. The assignment
of a character to an element of a code page determines the
binary value that will be used to represent each occurrence
of the character in a character string.
character coordinate system. An orthogonal coordinate
system that defines font and character measurement
distances. The origin is the character reference point. The
X axis coincides with the character baseline.
character density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also bar code density, density, and
information density.
character direction. In GOCA, an attribute controlling the
direction in which a character string grows relative to the
inline direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with direction.
character escapement point. The point where the next
character reference point is usually positioned. See also
character increment and presentation position.
character identifier. The unique name for a graphic
character.
character increment. The distance from a character
reference point to a character escapement point. For each
character, the increment is the sum of a character's A-
space, B-space, and C-space. A character's character
increment is the distance the inline coordinate is
incremented when that character is placed in a
presentation space or on a physical medium. Character
increment is a property of each graphic character in a font
and of the font's character rotation.
character increment adjustment. In a scaled font, an
adjustment to character increment values. The adjustment
value is derived from the kerning track values for the font
used to present the characters.
character metrics. Measurement information that
defines individual character values such as height, width,
and space. Character metrics can be expressed in specific
fixed units, such as pels, or in relative units that are
independent of both the resolution and the size of the font.
Often included as part of the more general term font
metrics. See also character set metrics and font metrics.
character pattern. The scan pattern for a graphic
character of a particular size, style, and weight.
character-pattern descriptor. Information that the printer
needs to separate font raster patterns. Each character
pattern descriptor is eight bytes long and specifies both the
character box size and an offset value; the offset value
permits the printer to find the beginning of the character
raster pattern within the character raster pattern data for
the complete coded font.
character positioning. A method used to determine
where a character is to appear in a presentation space or
on a physical medium.
character precision. The acceptable amount of variation
in the appearance of a character on a physical medium
from a specified ideal appearance, including no acceptable
variation. Examples of appearance characteristics that can
cap-M height • character precision

## Page 232

210 FOCA Reference
vary for a character are character shape and character
position.
character reference point. The origin of a character
coordinate system. The X axis is the character baseline.
character rotation. The alignment of a character with
respect to its character baseline, measured in degrees in a
clockwise direction. Examples are 0°, 90°, 180°, and 270°.
Zero-degree character rotation exists when a character is
in its customary alignment with the baseline. Character
rotation and font inline sequence are related in that
character rotation is a clockwise rotation; font inline
sequence is a counter-clockwise rotation. Contrast with
rotation.
character set. A finite set of different graphic characters
or control characters that is complete for a given purpose.
For example, the character set in ISO Standard 646, 7-Bit
Coded Character Set for Information Processing
Interchange.
character set attribute. An attribute used to specify a
coded font.
character set metrics. The measurements used in a
font. Examples are height, width, and character increment
for each character of the font. See also character metrics
and font metrics.
character shape. The visual representation of a graphic
character.
character shape presentation. A method used to form a
character shape on a physical medium at an addressable
position.
character shear. The angle of slant of a character cell
that is not perpendicular to a baseline. Synonymous with
shear.
character string. A sequence of characters.
check character. In bar codes, a character included
within a bar code message whose value is used to perform
a mathematical check to ensure the accuracy of that
message. Synonymous with check digit.
check digit. In bar codes, a character included within a
bar code message whose value is used to perform a
mathematical check to ensure the accuracy of that
message. Synonymous with check character.
CID file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for large character set fonts (for example, Japanese
ideographic fonts having several thousand characters).
CIE. See Commission Internationale d’Éclairage.
CIELAB color space. Internationally accepted color
space model used as a standard to define color within the
graphic arts industry, as well as other industries. L*, a*, and
b* are plotted at right angles to one another. Equal
distances in the space represent approximately equal color
difference.
CIEXYZ color space. The fundamental CIE-based color
space that allows colors to be expressed as a mixture of
the three tristimulus values X, Y , and Z.
CJK fonts. Fonts that contain a set of unified ideographic
characters used in the written Chinese, Japanese, and
Korean languages. The character encoding is the same for
each language, but there might be glyph variants between
languages.
clear area. A clear space that contains no machine-
readable marks preceding the start character of a bar code
symbol or following the stop character. Synonymous with
quiet zone. Contrast with intercharacter gap and space.
clipping. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window.
Synonymous with trimming.
CMAP file. A file containing the mapping of code points to
the character index values used in a CID file. The code
points conform to a particular character coding system
which is used to identify the characters in a document data
stream. The character index values are assigned in a CID
file for identification of the glyph procedure used to define
the character shape. The mapping information in this file is
in an ASCII file format defined by Adobe Systems Inc.
CMOCA. See Color Management Object Content
Architecture.
CMR. See Color management resource.
CMY . Cyan, magenta, and yellow, the subtractive primary
colors.
CMYK color space. The color model used in four-color
printing. Cyan, magenta, and yellow, the subtractive
primary colors, are used with black to effectively create a
multitude of other colors.
Codabar. A bar code symbology characterized by a
discrete, self-checking, numeric code with each character
represented by a standalone group of four bars and the
three spaces between them.
CODE. A data type for architecture syntax that indicates
an architected constant to be interpreted as defined by the
architecture.
Code 39. A bar code symbology characterized by a
variable-length, bidirectional, discrete, self-checking,
alphanumeric code. Three of the nine elements are wide
character reference point • Code 39

## Page 233

FOCA Reference 211
and six are narrow. It is the standard for LOGMARS (the
Department of Defense) and the AIAG.
Code 128. A bar code symbology characterized by a
variable-length, alphanumeric code with 128 characters.
Coded Character Set Identifier (CCSID). A 16-bit
number identifying a specific set consisting of an encoding
scheme identifier, character set identifiers, code page
identifiers, and other relevant information that uniquely
identifies the coded graphic character representation used.
coded font. (1) A resource containing elements of a code
page and a font character set, used for presenting text,
graphics character strings, and bar code HRI. See also
code page and font character set. (2) In FOCA, a resource
containing the resource names of a valid pair of font
character set and code page resources. The graphic
character set of the font character set must match the
graphic character set of the code page for the coded font
resource pair to be valid. (3) In the IPDS architecture, a
raster font resource containing code points that are directly
paired to font metrics and the raster representation of
character shapes, for a specific graphic character set. (4)
In the IPDS architecture, a font resource containing
descriptive information, a code page, font metrics, and a
digital-technology representation of character shapes for a
specific graphic character set.
coded font local identifier. A binary identifier that is
mapped by the controlling environment to a named
resource to identify a coded font. See also local identifier.
coded graphic character. A graphic character that has
been assigned one or more code points within a code
page.
coded graphic character set. A set of graphic
characters with their assigned code points.
Coded Graphic Character Set Global Identifier
(CGCSGID). A four-byte binary or a ten-digit decimal
identifier consisting of the concatenation of a GCSGID and
a CPGID. The CGCSGID identifies the code point
assignments in the code page for a specific graphic
character set, from among all the graphic characters that
are assigned in the code page.
code page. (1) A resource object containing descriptive
information, graphic character identifiers, and code points
corresponding to a coded graphic character set. Graphic
characters can be added over time; therefore, to
specifically identify a code page, both a GCSGID and a
CPGID should be used. See also coded graphic character
set. (2) A set of assignments, each of which assigns a
code point to a character. Each code page has a unique
name or identifier. Within a given code page, a code point
is assigned to one character. More than one character set
can be assigned code points from the same code page.
See also code point and section.
Code Page Global Identifier (CPGID). A unique code
page identifier that can be expressed as either a two-byte
binary or a five-digit decimal value.
code point. A unique bit pattern that can serve as an
element of a code page or a site in a code table, to which a
character can be assigned. The element is associated with
a binary value. The assignment of a character to an
element of a code page determines the binary value that
will be used to represent each occurrence of the character
in a character string. Code points are one or more bytes
long. See also code table and section.
code table. A table showing the character allocated to
each code point in a code. See also code page and code
point.
color. A visual attribute of things that results from the light
they emit, transmit, or reflect.
color attribute. An attribute that affects the color values
provided in a graphics primitive, a text control sequence, or
an IPDS command. Examples of color attributes are
foreground color and background color.
color component. A dimension of a color value
expressed as a numeric value. For example, a color value
might consist of one, two, three, four, or eight components,
also referred to as channels.
color conversion. The process of converting colors from
one color space to another.
color image. Images whose image data elements are
represented by multiple bits or whose image data element
values are mapped to color values. Constructs that map
image-data-element values to color values are look-up
tables and image-data-element structure parameters.
Examples of color values are screen color values for
displays and color toner values for printers.
color management. The technology to calibrate the color
of input devices (such as scanners or digital cameras),
display devices, and output devices (such as printers or
offset presses).
Color Management Object Content Architecture
(CMOCA). An architected collection of constructs used for
the interchange and presentation of the color management
information required to render a print file, document, group
of pages or sheets, page, overlay, or data object with color
fidelity.
color management resource. An object that provides
color management in presentation environments.
color management system. A set of software designed
to increase the accuracy and consistency of color between
color devices like a scanner, display, and printer.
color model. The method by which a color is specified.
For example, the RGB color space specifies color in terms
Code 128 • color model

## Page 234

212 FOCA Reference
of three intensities for red (R), green (G), and blue (B). Also
referred to as color space.
color of medium. The color of a presentation space
before any data is added to it. Synonymous with reset
color.
color space. The method by which a color is specified.
For example, the RGB color space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color model.
color table. A collection of color element sets. The table
can also specify the method used to combine the intensity
levels of each element in an element set to produce a
specific color. Examples of methods used to combine
intensity levels are the additive method and the subtractive
method. See also color model.
command. (1) In the IPDS architecture, a structured field
sent from a host to a printer. (2) In GOCA, a data-stream
construct used to communicate from the controlling
environment to the drawing process. The command
introducer is environment dependent. (3) A request for
system action.
command set. A collection of IPDS commands.
command-set vector. Information that identifies an IPDS
command set and data level supported by a printer.
Command-set vectors are returned with an Acknowledge
Reply to an IPDS Sense Type and Model command.
Commission Internationale d’Éclairage (CIE). An
association of international color scientists who produced
the standards that are used as the basis of the description
of color.
compression algorithm. An algorithm used to compress
image data. Compression of image data can decrease the
volume of data required to represent an image.
construct. An architected set of data such as a structured
field or a triplet.
continuous code. A bar code symbology characterized
by designating all spaces within the symbol as parts of
characters, for example, Interleaved 2 of 5. There is no
intercharacter gap in a continuous code. Contrast with
discrete code.
continuous-form media. Connected sheets. An example
of connected sheets is sheets of paper connected by a
perforated tear strip. Contrast with cut-sheet media.
control character. (1) A character that denotes the start,
modification, or end of a control function. A control
character can be recorded for use in a subsequent action,
and it can have a graphic representation. See also
character. (2) A control function the coded representation
of which consists of a single code point.
control instruction. A data construct transmitted from
the controlling environment and interpreted by the
environment interface to control the operation of the
graphics processor.
controlled white space. White space caused by
execution of a control sequence. See also white space.
controlling environment. The environment in which an
object is embedded, for example, the IPDS and MO:DCA
data streams.
control sequence. A sequence of bytes that specifies a
control function. A control sequence consists of a control
sequence introducer and zero or more parameters.
control sequence chaining. A method used to identify a
sequential string of control sequences so they can be
processed efficiently.
control sequence class. An assigned coded character
that identifies a control sequence's syntax and how that
syntax is to be interpreted. An example of a control
sequence class is X'D3', that identifies presentation text
object control sequences.
control sequence function type. The coded character
occupying the fourth byte of an unchained control
sequence introducer. This code defines the function whose
semantics can be prescribed by succeeding control
sequence parameters.
control sequence introducer. The information at the
beginning of a control sequence. An unchained control
sequence introducer consists of a control sequence prefix,
a class, a length, and a function type. A chained control
sequence introducer consists of a length and a function
type.
control sequence length. The number of bytes used to
encode a control sequence excluding the control sequence
prefix and class.
control sequence prefix. The escape character used to
identify a control sequence. The control sequence prefix is
the first byte of a control sequence. An example of a
control sequence prefix is X'2B'.
coordinate system. A Cartesian coordinate system. An
example is the image coordinate system that uses the
fourth quadrant with positive values for the Y axis. The
origin is the upper left-hand corner of the fourth quadrant.
A pair of (x,y) values corresponds to one image point. Each
image point is described by an image data element. See
also character coordinate system.
coordinates. A pair of values that specify a position in a
coordinate space. See also absolute coordinate and
relative coordinate.
color of medium • coordinates

## Page 235

FOCA Reference 213
copy control. A method used to specify the number of
copies for a presentation space and the modifications to be
made to each copy.
copy counter. Bytes in an Acknowledge Reply that
identify the number of copies of a page that have passed a
particular point in the logical paper path.
copy group. A set of copy subgroups that specify all
copies of a sheet. In the IPDS architecture, a copy group is
specified by a Load Copy Control command. In MO:DCA, a
copy group is specified within a Medium Map. See also
copy subgroup.
copy modification. The process of adding, deleting, or
replacing data on selected copies of a presentation space.
copy set. A collection of pages intended to be printed
multiple times. For example, when multiple copies of a
book or booklet is printed, each copy of the book or booklet
is a copy set. This term was originally used with copy
machines to identify collections of copies that are delivered
as sets or stapled as sets. The term was also used when
printing multiple copies of an MVS data set.
copy subgroup. A part of a copy group that specifies a
number of identical copies of a sheet and all modifications
to those copies. Modifications include the media source,
the media destination, medium overlays to be presented
on the sheet, text suppressions, the number of pages on
the sheet, and either simplex or duplex presentation. In the
IPDS architecture, copy subgroups are specified by Load
Copy Control command entries. In MO:DCA, copy
subgroups are specified by repeating groups in the
Medium Copy Count structured field in a Medium Map. See
also copy group.
correlation. A method used in the IPDS architecture to
match exceptions with commands.
correlation ID. A two-byte value that specifies an
identifier of an IPDS command. The correlation ID is
optional and is present only if bit one of the command's flag
byte is B'1'.
CPGID. See Code Page Global Identifier .
cpi. Characters per inch.
C-space. The distance from the most positive character
coordinate system X-axis value of a character shape to the
character escapement point. C-space can be positive,
zero, or negative. See also A-space and B-space.
current baseline coordinate. The baseline presentation
position at the present time. The baseline presentation
position is the summation of the increments of all baseline
controls since the baseline was established in the
presentation space. The baseline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Baseline control sequence. Synonymous
with current baseline presentation coordinate.
current baseline presentation coordinate (B
c). The
baseline presentation position at the present time. The
baseline presentation position is the summation of the
increments of all baseline controls since the baseline was
established in the presentation space. The baseline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Baseline control
sequence. Synonymous with current baseline coordinate.
current baseline print coordinate (b
c). In the IPDS
architecture, the baseline coordinate corresponding to the
current print position on a logical page. The current
baseline print coordinate is a coordinate in an I,B
coordinate system. See also I,B coordinate system.
current drawing attributes. The set of attributes used at
the present time to direct a drawing process. Contrast with
default drawing attributes.
current drawing controls. The set of drawing controls
used at the present time to direct a drawing process.
Contrast with default drawing controls.
current inline coordinate. The inline presentation
position at the present time. This inline presentation
position is the summation of the increments of all inline
controls since the inline coordinate was established in the
presentation space. An inline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Inline control sequence. Synonymous with
current inline presentation coordinate.
current inline presentation coordinate (I
c). The inline
presentation position at the present time. This inline
presentation position is the summation of the increments of
all inline controls since the inline coordinate was
established in the presentation space. An inline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Inline control sequence.
Synonymous with current inline coordinate.
current inline print coordinate (i c). In the IPDS
architecture, the inline coordinate corresponding to the
current print position on a logical page. The current inline
print coordinate is a coordinate in an I,B coordinate
system. See also I,B coordinate system.
current logical page. The logical page presentation
space that is currently being used to process the data
within a page object or an overlay object.
current position. The position identified by the current
presentation space coordinates. For example, the
coordinate position reached after the execution of a
drawing order. See also current baseline presentation
copy control • current position

## Page 236

214 FOCA Reference
coordinate and current inline presentation coordinate.
Contrast with given position.
custom line type value. A user-defined line type, defined
by a series of pairs of a dash/dot length followed by a move
length. Contrast with standard line type value.
custom pattern. In GOCA, a user-defined pattern,
defined by the picture drawn by a series of drawing orders
between a Begin Custom Pattern drawing order and an
End Custom Pattern drawing order. Custom patterns can
be either bilevel custom patterns or full-color custom
patterns. Contrast with patterns in the default pattern set.
custom pattern mode. In GOCA, a mode that is entered
when a Begin Custom Pattern drawing order is executed
and exited when an End Custom Pattern drawing order is
executed. While in this mode, drawing is done in a
separate, temporary graphics presentation space rather
than in the graphics presentation space of the current
GOCA object.
cut-sheet media. Unconnected sheets. Contrast with
continuous-form media.
D
data block. A deprecated term for object area.
data element. A unit of data that is considered indivisible.
data frame. A rectangular division of computer output on
microfilm.
data mask. A sequence of bits that can be used to
identify boundary alignment bits in image data.
data object. In the IPDS architecture, a presentation-form
object that is either specified within a page or overlay or is
activated as a resource and later included in a page or
overlay via the IDO command. Examples include: PDF
single-page objects, Encapsulated PostScript objects, and
IO Images. See also resource and data object resource.
data-object font. (1) In the IPDS architecture, a
complete-font resource that is a combination of font
components at a particular size, character rotation, and
encoding. A data-object font can be used in a manner
analogous to a coded font. The following useful
combinations can be activated into a data-object font:
• A TrueType/OpenType font, an optional code page, and
optional linked TrueType/OpenType objects; activated at
a particular size, character rotation, and encoding
• A TrueType/OpenType collection, either an index value
or a full font name to identify the desired font within the
collection, an optional code page, and optional linked
TrueType/OpenType objects; activated at a particular
size, character rotation, and encoding
See also data-object-font component. (2) In the MO:DCA
architecture, a complete non-FOCA font resource object
that is analogous to a coded font. Examples of data-object
fonts are TrueType fonts and OpenType fonts.
data-object-font component. In the IPDS architecture, a
font resource that is either printer resident or is
downloaded using object container commands. Data-
object-font components are used as components of a data-
object font. Examples of data-object-font components
include TrueType/OpenType fonts and TrueType/
OpenType collections. See also data-object font.
data object resource. In the IPDS architecture, an
object-container resource or IO-Image resource that is
either printer resident or downloaded. Data object
resources can be:
• Used to prepare for the presentation of a data object;
such as with a Color Management Resource or Resident
Color Profile Resource
• Included in a page or overlay via the Include Data Object
command; examples include: PDF single-page objects,
Encapsulated PostScript objects, and IO Images
• Invoked from within a data object; examples
include: PDF Resource objects
See also data object and resource.
data stream. A continuous stream of data that has a
defined format. An example of a defined format is a
structured field.
data-stream exception. In the IPDS architecture, a
condition that exists when the printer detects an invalid or
unsupported command, order, control, or parameter value
from the host. Data-stream exceptions are those whose
action code is X'01', X'19', or X'1F'. See also asynchronous
exception and synchronous exception.
DBCS. See double-byte character set.
decoder. In bar codes, the component of a bar code
reading system that receives the signals from the scanner,
performs the algorithm to interpret the signals into
meaningful data, and provides the interface to other
devices. See also reader and scanner.
default. A value, attribute, or option that is assumed when
none has been specified and one is needed to continue
processing. See also default drawing attributes and default
drawing controls.
default drawing attributes. The set of drawing attributes
adopted at the beginning of a drawing process and usually
at the beginning of each root segment that is processed.
See also root segment. Contrast with current drawing
attributes.
default drawing controls. The set of drawing controls
adopted at the start of a drawing process and usually at the
start of each root segment that is processed. See also root
segment. Contrast with current drawing controls.
custom line type value • default drawing controls

## Page 237

FOCA Reference 215
default indicator. A field whose bits are all B'1' indicating
that a hierarchical default value is to be used. The value
can be specified by an external parameter. See also
external parameter.
default pattern set. In GOCA, a set of predefined
patterns, like solid, dots, or horizontal lines. Contrast with
custom pattern.
density. The number of characters per inch (cpi) in a bar
code symbology. In most cases, the range is three to ten
cpi. See also bar code density, character density, and
information density.
deprecated. An architected construct is marked as
“deprecated” to indicate that it should no longer be used
because it has been superseded by a newer construct.
Use or support of a deprecated construct is permitted but
no longer recommended. Constructs are deprecated rather
than immediately removed to provide backward
compatibility.
descender. The part of the character that extends into the
character coordinate system negative Y-axis region.
Examples of letters with descenders at zero-degree
character rotation are g, j, p, q, y, and Q. Contrast with
ascender.
descender depth. The character shape's most negative
character coordinate system Y-axis value.
design metrics. A set of quantitative values,
recommended by a font designer, to describe the
characters in a font.
design size. The size of the unit Em for a font. All relative
font measurement values are expressed as a proportion of
the design size. For example, the width of the letter I can
be specified as one-fourth of the design size.
Device-Control command set. In the IPDS architecture,
a collection of commands used to set up a page,
communicate device controls, and manage printer
acknowledgment protocol.
device dependent. Dependent upon one or more device
characteristics. An example of device dependency is a font
whose characteristics are specified in terms of addressable
positions of specific devices. See also system-level font
resource.
device independent. Not dependent upon device
characteristics.
device-independent color space. A CIE-based color
space that allows color to be expressed in a device-
independent way. It ensures colors to be predictably and
accurately matched among various color devices.
device level font resource. A device-specific font object
from which a presentation device can obtain the font
information required to present character images.
device profile. A structure that provides a means of
defining the color characteristics of a given device in a
particular state.
device resolution. The number of pels that can be
printed in an inch, both horizontally and vertically. This is
the resolution that the printer uses when printing. Some
printers can be configured to print with a variety of
resolutions that can be selected by the operator. The
device resolution can be different in the two directions (for
example, a resolution of 360 by 720).
device-version code page. In the IPDS architecture, a
device version of a code page contains all of the
characters that were registered for the CPGID at the time
the printer was developed; since then, more characters
might have been added to the registry for that CPGID. A
device-version code page is identified by a CPGID. See
also code page.
digital half-toning. A method used to simulate gray
levels on a bi-level device.
digital image. An image whose image data was sampled
at regular intervals to produce a digital representation of
the image. The digital representation is usually restricted to
a specified set of values.
direction. In GOCA, an attribute that controls the
direction in which a character string grows relative to the
inline direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with character
direction.
discrete code. A bar code symbology characterized by
placing spaces that are not a part of the code between
characters, that is, intercharacter gaps.
DOCS. See drawing order coordinate space.
document. (1) A machine-readable collection of one or
more objects that represents a composition, a work, or a
collection of data. (2) A publication or other written
material.
document component. An architected part of a
document data stream. Examples of document
components are documents, pages, page groups, indexes,
resource groups, objects, and process elements.
document content architecture. A family of
architectures that define the syntax and semantics of the
document component. See also document component and
structured field.
document editing. A method used to create or modify a
document.
document element. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. An application
or device does not have to understand control information
default indicator • document element

## Page 238

216 FOCA Reference
or data to parse a data stream when all the records in the
data stream are document elements. See also structured
field.
document fidelity. The degree to which a document
presentation preserves the creator's intent.
document formatting. A method used to determine
where information is positioned in presentation spaces or
on physical media.
document presentation. A method used to produce a
visible copy of formatted information on physical media.
double-byte character set (DBCS). A character set that
can contain up to 65536 characters.
double-byte coded font. A coded font in which the code
points are two bytes long.
downloaded resource. In the IPDS architecture, a
resource in a printer that is installed and removed under
control of a host presentation services program. A
downloaded resource is referenced by a host-assigned
name that is valid for the duration of the session between
the presentation services program and the printer. Contrast
with resident resource.
drag. T o use a pointing device to move an object. For
example, clicking on a window border, and dragging it to
make the window larger.
draw functions. Functions that can be done during the
drawing of a picture. Examples of draw functions are
displaying a picture, boundary computation, and erasing a
graphics presentation space.
draw rule. A method used to construct a line, called a
rule, between two specified presentation positions. The line
that is constructed is either parallel to the inline I axis or
baseline B axis.
drawing control. A control that determines how a picture
is drawn. Examples of drawing controls are arc
parameters, transforms, and the viewing window.
drawing order. In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify, for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Drawing orders
can be included in a structured field. See also order.
drawing order coordinate space (DOCS). A two-
dimensional conceptual space in which graphics primitives
are drawn, using drawing orders, to create pictures.
drawing processor. A graphics processor component
that executes segments to draw a picture in a presentation
space. See also segment, graphics presentation space,
and image presentation space.
drawing units. Units of measurement used within a
graphics presentation space to specify absolute and
relative positions.
duplex. A method used to print data on both sides of a
sheet. Normal-duplex printing occurs when the sheet is
turned over the Y
m axis. Tumble-duplex printing occurs
when the sheet is turned over the Xm axis.
duplex printing. A method used to print data on both
sides of a sheet. Contrast with simplex printing.
dynamic segment. A segment whose graphics primitives
can be redrawn in different positions by dragging them
from one position to the next across a picture without
destroying the traversed parts of the picture.
E
EAN. See European Article Numbering.
EBCDIC. See Extended Binary-Coded Decimal
Interchange Code.
Efficient XML Interchange (EXI). A format that allows
XML documents to be encoded as binary data, rather than
as plain text.
element. (1) A bar or space in a bar code character or a
bar code symbol. (2) A structured field in a document
content architecture data stream. (3) In GOCA, a portion of
a segment consisting of either a single order or a group of
orders enclosed in an element bracket, in other words,
between a begin element and an end element. (4) A basic
member of a mathematical or logical class or set.
Em. In printing, a unit of linear measure referring to the
baseline-to-baseline distance of a font, in the absence of
any external leading.
Em square. A square layout space used for designing
each of the characters of a font.
encoding scheme. A set of specific definitions that
describe the philosophy used to represent character data.
The number of bits, the number of bytes, the allowable
ranges of bytes, the maximum number of characters, and
the meanings assigned to some generic and specific bit
patterns, are some examples of specifications to be found
in such a definition.
Encoding Scheme Identifier (ESID). A 16-bit number
assigned to uniquely identify a particular encoding scheme
specification. See also encoding scheme.
environment interface. The part of the graphics
processor that interprets commands and instructions from
the controlling environment.
document fidelity • environment interface

## Page 239

FOCA Reference 217
escape sequence. (1) In the IPDS architecture, the first
two bytes of a control sequence. An example of an escape
sequence is X'2BD3'. (2) A string of bit combinations that
is used for control in code extension procedures. The first
of these bit combinations represents the control function
Escape.
escapement direction. In FOCA, the direction from a
character reference point to the character escapement
point, that is, the font designer's intended direction for
successive character shapes. See also character direction
and inline direction.
ESID. See Encoding Scheme Identifier.
established baseline coordinate. The current baseline
presentation coordinate when no temporary baseline exists
or the last current baseline presentation coordinate that
existed before the first active temporary baseline was
created. If temporary baselines are created, the current
baseline presentation coordinate coincides with the
presentation coordinate of the most recently created
temporary baseline.
European Article Numbering (EAN). The bar code
symbology used to code grocery items in Europe.
exception. (1) An invalid or unsupported data-stream
construct. (2) In the IPDS architecture, a condition
requiring host notification. (3) In the IPDS architecture, a
condition that requires the host to resend data. See also
data-stream exception, asynchronous exception, and
synchronous exception.
exception action. Action taken when an exception is
detected.
exception condition. The condition that exists when a
product finds an invalid or unsupported construct.
exchange. The predictable interpretation of shared
information by a family of system processes in an
environment where the characteristics of each process
must be known to all other processes. Contrast with
interchange.
EXI. See Efficient XML Interchange.
expanded. A type width that widens all characters of a
typeface.
Extended Binary-Coded Decimal Interchange Code
(EBCDIC). A coded character set that consists of eight-bit
coded characters.
Extensible Markup Language (XML). A set of rules for
encoding documents in a format that is both human-
readable and machine-readable.
Extensible Metadata Platform (XMP). An ISO standard,
originally created by Adobe Systems Incorporated, for the
creation, processing, and interchange of standardized and
custom metadata for all kinds of resources.
external leading. The amount of white space, in addition
to the internal leading, that can be added to interline
spacing without degrading the aesthetic appearance of a
font. This value is usually specified by a font designer.
Contrast with internal leading.
external parameter. A parameter for which the current
value can be provided by the controlling environment, for
example, the data stream, or by the application itself.
Contrast with internal parameter.
F
factoring. The movement of a parameter value from one
state to a higher-level state. This permits the parameter
value to apply to all of the lower-level states unless
specifically overridden at the lower level.
FGID. See Font Typeface Global Identifier.
Filename Map File. A file containing the mapping of
object names to file names for use in establishing a font file
system. Object names and file names do not conform to
the same naming requirements, so it is necessary to
provide a mapping between them. The mapping
information in this file is in an ASCII file format defined by
Adobe Systems Inc.
fillet. A curved line drawn tangential to a specified set of
straight lines. An example of a fillet is the concave junction
formed where two lines meet.
final form data. Data that has been formatted for
presentation.
first read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with read rate.
fixed medium information. Information that can be
applied to a sheet by a printer or printer-attached device
that is independent of data provided through the data
stream. Fixed medium information does not mix with the
data provided by the data stream and is presented on a
sheet either before or after the text, image, graphics, or bar
code data provided within the data stream. Fixed medium
information can be used to create preprinted forms, or
other types of printing, such as colored logos or
letterheads, that cannot be created conveniently within the
data stream.
fixed metrics. Graphic character measurements in
physical units such as pels, inches, or centimeters.
FNN Linked. In FOCA, the FNN (Font Name map)
structured field permits the mapping of a set of IBM
GCGIDs to the character index values which occur in either
escape sequence • FNN Linked

## Page 240

218 FOCA Reference
a CMAP file or a Rearranged file. Because the set of
GCGIDs and the set of character index values must
correspond to the same set of characters, it is necessary to
identify which CMAP or Rearranged file (among the many
that could be located in a font file system) is associated
(linked) with the FNN structured field. Note that the Font
Name Map is known as the Character ID Map in IPDS.
FOCA. See Font Object Content Architecture.
font. A set of graphic characters that have a characteristic
design, or a font designer's concept of how the graphic
characters should appear. The characteristic design
specifies the characteristics of its graphic characters.
Examples of characteristics are character shape, graphic
pattern, style, size, weight class, and increment. Examples
of fonts are fully described fonts, symbol sets, and their
internal printer representations. See also coded font and
symbol set.
font baseline extent. In the IPDS architecture, the sum
of the uniform or maximum baseline offset and the
maximum baseline descender of all characters in the font.
font character set. A FOCA resource containing
descriptive information, font metrics, and the digital
representation of character shapes for a specified graphic
character set.
font control record. The record sent in an IPDS Load
Font Control command to specify a font ID and other font
parameters that apply to the complete font.
font height (FH). (1) A characteristic value, perpendicular
to the character baseline, that represents the size of all
graphic characters in a font. Synonymous with vertical font
size. (2) In a font character set, nominal font height is a
font-designer defined value corresponding to the nominal
distance between adjacent baselines when character
rotation is zero degrees and no external leading is used.
This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified font height is
the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
font index. (1) The mapping of a descriptive font name to
a font member name in a font library. An example of a font
member in a font library is a font resource object.
Examples of attributes used to form a descriptive font
name are typeface, family name, point size, style, weight
class, and width class. (2) In the IPDS architecture, an
LF1-type raster-font resource containing character metrics
for each code point of a raster font or raster-font section for
a particular font inline sequence. There can be a font index
for 0 degree, 90 degree, 180 degree, and 270 degree font
inline sequences. A font index can be downloaded to a
printer using the Load Font Index command. An LF1-type
coded font or coded-font section is the combination of one
fully described font and one font index. See also fully
described font.
font inline sequence. The clockwise rotation of the inline
direction relative to a character pattern. Character rotation
and font inline sequence are related in that character
rotation is a clockwise rotation; font inline sequence is a
counter-clockwise rotation.
font local identifier. A binary identifier that is mapped by
the controlling environment to a named resource to identify
a font. See also local identifier.
font metrics. Measurement information that defines
individual character values such as height, width, and
space, as well as overall font values such as averages and
maximums. Font metrics can be expressed in specific fixed
units, such as pels, or in relative units that are independent
of both the resolution and the size of the font. See also
character metrics and character set metrics.
font modification parameters. Parameters that alter the
appearance of a typeface.
font object. A resource object that contains some or all of
the description of a font.
Font Object Content Architecture (FOCA). An
architected collection of constructs used to describe fonts
and to interchange those font descriptions.
font production. A method used to create a font. This
method includes designing each character image,
converting the character images to a digital-technology
format, defining parameter values for each character,
assigning appropriate descriptive and identifying
information, and creating a font resource that contains the
required information in a format that can be used by a text
processing system. Digital-technology formats include bit
image, vector drawing orders, and outline algorithms.
Parameter values include such attributes as height, width,
and escapement.
font referencing. A method used to identify or
characterize a font. Examples of processes that use font
referencing are document editing, document formatting,
and document presentation.
Font Typeface Global Identifier (FGID). A unique font
identifier that can be expressed as either a two-byte binary
or a five-digit decimal value. The FGID is used to identify a
type style and the following characteristics: posture,
weight class, and width class.
font width (FW). (1) A characteristic value, parallel to the
character baseline, that represents the size of all graphic
characters in a font. Synonymous with horizontal font
size. (2) In a font character set, nominal font width is a
font-designer defined value corresponding to the nominal
FOCA • font width (FW)

## Page 241

FOCA Reference 219
character increment for a font character set. The value is
generally the width of the space character and is defined
differently for fonts with different spacing characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic, proportionally spaced fonts: one-third of
the vertical font size, that is also the default size of the
space character.
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified font width is
the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
foreground. (1) The part of a presentation space that is
occupied by object data. (2) In GOCA, the portion of a
graphics primitive that is mixed into the presentation space
under the control of the current value of the mix and color
attributes. See also pel. Contrast with background.
foreground color. A color attribute used to specify the
color of the foreground of a primitive. Contrast with
background color.
foreground mix. An attribute used to determine how the
foreground color of data is combined with the existing color
of a graphics presentation space. An example of data is a
graphics primitive. Contrast with background mix.
form. A division of the physical medium; multiple forms
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper, each representing a form. Envelopes are an
example of a physical medium that comprises only one
form. The IPDS architecture defines four types of
forms: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
form has a top edge. A form has two sides, a front side and
a back side. Synonymous with sheet.
format. The arrangement or layout of data on a physical
medium or in a presentation space.
formatter. A process used to prepare a document for
presentation.
formblend. This mixing rule is only used when a
preprinted form overlay (PFO) is merged as presentation
space P
PFO with other presentation data (presentation
space Pdata). The intersection of P PFO and Pdata is assigned
the following color attribute:
• Wherever the color attribute of P PFO is either color of
medium, or “white” (CMYK = X'00000000' for a printer,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P data. Likewise,
wherever the color attribute of P data is either color of
medium, or “white” (CMYK = X'00000000' for a printer,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P PFO.
• With other overlapping color values, the intersection
assumes a new color attribute that is generated in a
device-specific manner to simulate how the P
data color
attribute would mix onto a preprinted form that has the
color attribute of P
PFO. In general, this mixing is a
blending of the color attributes of P data and PPFO that is
determined by the two color attributes and by the print
media and the print technology.
See also mixing rule.
Formdef. See Form Definition.
Form Definition (Formdef). A print control object that
contains an environment definition and one or more
Medium Maps. Synonymous with Form map.
Form Map. A print control object that contains an
environment definition and one or more Medium Maps.
Synonymous with Form Definition. See also Medium Map.
full arc. A complete circle or ellipse. See also arc.
full-color custom pattern. In GOCA, a custom pattern
that has its colors completely assigned during its definition,
and can therefore contain any number of colors. Contrast
with bilevel custom pattern.
fully described font. In the IPDS architecture, an LF1-
type raster-font resource containing font metrics,
descriptive information, and the raster representation of
character shapes, for a specific graphic character set. A
fully described font can be downloaded to a printer using
the Load Font Control and Load Font commands. An LF1-
type coded font or coded-font section is the combination of
one fully described font and one font index. See also font
index.
function set. A collection of architecture constructs and
associated values. Function sets can be defined across or
within subsets.
FW. See font width.
G
GCGID. See Graphic Character Global Identifier.
GCSGID. See Graphic Character Set Global Identifier .
GCUID. See Graphic Character UCS Identifier.
GID. See global identifier.
foreground • GID

## Page 242

220 FOCA Reference
given position. The coordinate position at which drawing
is to begin. A given position is specified in a drawing order.
Contrast with current position.
Global Identifier (GID). Any of the following:
• Code Page Global ID (CPGID)
• Graphic Character Global Identifier (GCGID)
• Graphic Character UCS Identifier (GCUID)
• Font Typeface Global Identifier (FGID)
• Graphic Character Set Global Identifier (GCSGID)
• Coded Graphic Character Set Global Identifier
(CGCSGID)
• In MO:DCA, an encoded graphic character string that
provides a reference name for a document element.
• Global Resource Identifier (GRID)
• Object identifier (OID)
• Coded Character Set Identifier (CCSID).
global resource identifier (GRID). An eight-byte
identifier that identifies a coded font resource. A GRID
contains the following fields in the order shown:
1. GCSGID of a minimum set of graphic characters
required for presentation. It can be a character set that
is associated with the code page, or with the font
character set, or with both.
2. CPGID of the associated code page
3. FGID of the associated font character set
4. Font width in 1440ths of an inch.
glyph. A member of a set of symbols that represent data.
Glyphs can be letters, digits, punctuation marks, or other
symbols. Synonymous with graphic character. See also
character.
GOCA. See Graphics Object Content Architecture.
grapheme. (1) A minimally distinctive unit of writing in the
context of a particular writing system. For example, å (“a +
Combining Ring Above” or “Latin Small Letter A with Ring
Above”) is a grapheme in the Danish writing system.
(2) What an end-user thinks of as a character.
graphic character. A member of a set of symbols that
represent data. Graphic characters can be letters, digits,
punctuation marks, or other symbols. Synonymous with
glyph. See also character.
Graphic Character Global Identifier (GCGID). An
alphanumeric character string used to identify a specific
graphic character. A GCGID can be from four-bytes to
eight-bytes long.
graphic character identifier. The unique name for a
graphic character in a font or in a graphic character set.
See also character identifier.
Graphic Character Set Global Identifier (GCSGID). A
unique graphic character set identifier that can be
expressed as either a two-byte binary or a five-digit
decimal value.
Graphic Character UCS Identifier (GCUID). An
alphanumeric character string used to identify a specific
graphic character. The GCUID naming scheme is used for
additional characters and sets of characters that exist in
UNICODE; each GCUID begins with the letter U and ends
with a UNICODE code point. The Unicode Standard is fully
compatible with the earlier Universal Character Set (UCS)
Standard.
Graphics command set. In the IPDS architecture, a
collection of commands used to present GOCA data in a
page, page segment, or overlay.
graphics data. Data containing lines, arcs, markers, and
other constructs that describe a picture.
graphics model space. A two-dimensional conceptual
space in which a picture is constructed. All model
transforms are completed before a picture is constructed in
a graphics model space. Contrast with graphics
presentation space. Synonymous with model space.
graphics object. An object that contains graphics data.
See also object.
graphics object area. A rectangular area on a logical
page into which a graphics presentation space window is
mapped.
Graphics Object Content Architecture (GOCA). An
architected collection of constructs used to interchange
and present graphics data.
graphics presentation space. A two-dimensional
conceptual space in which a picture is constructed. In this
space graphics drawing orders are defined. The picture
can then be mapped onto an output medium. All viewing
transforms are completed before the picture is generated
for presentation on an output medium. An example of a
graphics presentation space is the abstract space
containing graphics pictures defined in an IPDS Write
Graphics Control command. Contrast with graphics model
space.
graphics presentation space window. The portion of a
graphics presentation space that can be mapped to a
graphics object area on a logical page.
graphics primitive. A basic construct used by an output
device to draw a picture. Examples of graphics primitives
are arc, line, fillet, character string, and marker.
graphics processor. The processing capability required
to interpret a GOCA object, that is, to present the picture
represented by the object. It includes the environment
interface, that interprets commands and instructions, and
the drawing processor, that interprets the drawing orders.
given position • graphics processor

## Page 243

FOCA Reference 221
graphics segment. A set of graphics drawing orders
contained within a Begin Segment command. See also
segment.
grayscale image. Images whose image data elements
are represented by multiple bits and whose image data
element values are mapped to more than one level of
brightness through an image data element structure
parameter or a look-up table.
GRID. See global resource identifier.
guard bars. The bars at both ends and the center of an
EAN, JAN, or UPC symbol, that provide reference points
for scanning.
GZIP . A widely-used, free software compression
algorithm.
H
HAID. See Host-Assigned ID.
height. In bar codes, the bar dimension perpendicular to
the bar width. Synonymous with bar height and bar length.
hexadecimal. A number system with a base of sixteen.
The decimal digits 0 through 9 and characters A through F
are used to represent hexadecimal digits. The hexadecimal
digits A through F correspond to the decimal numbers 10
through 15, respectively. An example of a hexadecimal
number is X'1B', that is equal to the decimal number 27.
highlight color. A spot color that is used to accentuate or
contrast monochromatic areas. See also spot color.
highlighting. The emphasis of displayed or printed
information. Examples are increased intensity of selected
characters on a display screen and exception highlighting
on an IPDS printer.
hollow font. A font design in which the graphic character
shapes include only the outer edges of the strokes.
home state. An initial IPDS operating state. A printer
returns to home state at the end of each page, and after
downloading a font, overlay, or page segment.
horizontal bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the Xbc
axis of the bar code presentation space. Synonymous with
picket fence bar code.
horizontal font size. (1) A characteristic value, parallel to
the character baseline, that represents the size of all
graphic characters in a font. Synonymous with font
width. (2) In a font character set, nominal horizontal font
size is a font-designer defined value corresponding to the
nominal character increment for a font character set. The
value is generally the width of the space character and is
defined differently for fonts with different spacing
characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic fonts and proportionally spaced
fonts: one-third of the vertical font size, that is also the
default size of the space character.
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified horizontal font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
horizontal scale factor. (1) In outline-font referencing,
the specified horizontal adjustment of the Em square. The
horizontal scale factor is specified in 1440ths of an inch.
When the horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also vertical scale
factor. (2) In FOCA, the numerator of a scaling ratio,
determined by dividing the horizontal scale factor by the
vertical font size. If the value specified is greater or less
than the specified vertical font size, the graphic characters
and their corresponding metric values are stretched or
compressed in the horizontal direction relative to the
vertical direction by the scaling ratio indicated.
host. (1) In the IPDS architecture, a computer that drives
a printer. (2) In IOCA, the host is the controlling
environment.
Host-Assigned ID (HAID). A two-byte ID in the range
X'0001'–X'7EFF' that is assigned to an IPDS resource by a
presentation-services program in the host. This ID uniquely
identifies a resource until that resource is deactivated, in
which case the HAID can be reused. HAIDs are used in
IPDS resource management commands.
Host-Assigned Resource ID. The combination of a
Host-Assigned ID with a section identifier, or a font inline
sequence, or both. The section identifier and font inline
sequence values are ignored for both page segments and
overlays. See also section identifier and font inline
sequence.
HRI. See human-readable interpretation.
human-readable interpretation (HRI). The printed
translation of bar code characters into equivalent Latin
alphabetic characters, Arabic numeral decimal digits, and
common special characters normally used for printed
human communication.
hypermedia. Interlinked pieces of information consisting
of a variety of data types such as text, graphics data,
image, audio, and video.
graphics segment • hypermedia

## Page 244

222 FOCA Reference
hypertext. Interlinked pieces of information consisting
primarily of text.
I
ic. See current inline print coordinate.
ii. See initial inline print coordinate.
I. See inline direction.
+I. Positive inline direction.
Ic. See current inline presentation coordinate.
Io. See inline presentation origin.
I axis. The axis of an I,B coordinate system that extends
in the inline direction. The I axis does not have to be
parallel to the X p axis of its bounding Xp,Yp coordinate
space.
I,B coordinate system. The coordinate system used to
present graphic characters. This coordinate system is used
to establish the inline direction and baseline direction for
the placement of successive graphic characters within a
presentation space. See also Xp,Yp coordinate system.
ICC. See International Color Consortium.
ICC profile. A file in the International Color Consortium
profile format, containing information about the color
reproduction capabilities of a device such as a scanner, a
digital camera, a monitor, or a printer. An ICC profile
includes three elements: 128-byte file header, tag table,
and tagged element data. The intent of this format is to
provide a cross-platform device profile format. Such device
profiles can be used to translate color data created on one
device into another device’s native color space.
ID. Identifier. See also Host-Assigned ID (HAID),
correlation ID, font control record, and overlay ID.
IDE. See image data element.
I-direction. (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with inline direction.
IDP . See image data parameter.
IEEE. Institute of Electrical and Electronics Engineers.
I-extent. The Xp-extent when the I axis is parallel to the X p
axis or the Yp-extent when the I axis is parallel to the Y p
axis. The definition of the I-extent depends on the X p- or
Yp-extent because the I,B coordinate system is contained
within an Xp,Yp coordinate system.
image. An electronic representation of a picture produced
by means of sensing light, sound, electron radiation, or
other emanations coming from the picture or reflected by
the picture. An image can also be generated directly by
software without reference to an existing picture.
image content. Image data and its associated image
data parameters.
image coordinate system. An X,Y Cartesian coordinate
system using only the fourth quadrant with positive values
for the Y axis. The origin of an image coordinate system is
its upper left hand corner. An X,Y coordinate specifies a
presentation position that corresponds to one and only one
image data element in the image content.
image data. Rectangular arrays of raster information that
define an image.
image data element (IDE). A basic unit of image
information. An image data element expresses the
intensity of a signal at a corresponding image point. An
image data element can use a look-up table to introduce a
level of indirection into the expression of grayscale image
or color image.
image data parameter (IDP). A parameter that describes
characteristics of image data.
image distortion. Deformation of an image such that the
original proportions of the image are changed and the
original balance and symmetry of the image are lost.
image object. An object that contains image data. See
also object.
image object area. A rectangular area on a logical page
into which an image presentation space is mapped.
Image Object Content Architecture (IOCA). An
architected collection of constructs used to interchange
and present images.
image point. A discrete X,Y coordinate in the image
presentation space. See also addressable position.
image presentation space (IPS). A two-dimensional
conceptual space in which an image is generated.
image segment. Image content bracketed by Begin
Segment and End Segment self-defining fields. See also
segment.
IM Image. A migration image object that is resolution
dependent, bi-level, and cannot be compressed or scaled.
Contrast with IO Image.
IM-Image command set. In the IPDS architecture, a
collection of commands used to present IM-Image data in a
page, page segment, or overlay.
hypertext • IM-Image command set

## Page 245

FOCA Reference 223
immediate mode. The mode in which segments are
executed as they are received and then discarded.
Contrast with store mode.
indexed object. An object in a MO:DCA document that is
referenced by an Index Element structured field in a
MO:DCA index. Examples of indexed objects are pages
and page groups.
information density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also bar code density, character
density, and density.
initial addressable position. The values assigned to I c
and Bc by the data stream at the start of object state. The
standard action values are I o and Bo.
initial baseline print coordinate (b i). The baseline
coordinate of the first print position on a logical page. See
also initial inline print coordinate.
initial inline print coordinate (i i). The inline coordinate
of the first print position on a logical page. See also initial
baseline print coordinate.
inline-baseline coordinate system. See I,B coordinate
system.
inline coordinate. The first of a pair of values that
identifies the position of an addressable position with
respect to the origin of a specified I,B coordinate system.
This value is specified as a distance in addressable
positions from the B axis of an I,B coordinate system.
inline direction (I). (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with I-direction.
inline margin. The inline coordinate that identifies the
initial addressable position for a line of text.
inline presentation origin (I o). The point on the I axis
where the value of the inline coordinate is zero.
input profile. An ICC profile that is associated with the
image and describes the characteristics of the device on
which the image was created.
Intelligent Printer Data Stream (IPDS). An architected
host-to-printer data stream that contains both data and
controls defining how the data is to be presented.
interchange. The predictable interpretation of shared
information in an environment where the characteristics of
each process need not be known to all other processes.
Contrast with exchange.
intercharacter adjustment. Additional distance applied
to a character increment that increases or decreases the
distance between presentation positions, effectively
modifying the amount of white space between graphic
characters. The amount of white space between graphic
characters is changed to spread the characters of a word
for emphasis, distribute excess white space on a line
among the words of that line to achieve right justification, or
move the characters on the line closer together as in
kerning. Examples of intercharacter adjustment are
intercharacter increment and intercharacter decrement.
intercharacter decrement. Intercharacter adjustment
applied in the negative I-direction from the current
presentation position. See also intercharacter adjustment.
intercharacter gap. In bar codes, the space between two
adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter space. Contrast with clear
area, element, and space.
intercharacter increment. Intercharacter adjustment
applied in the positive I-direction from the current
presentation position. See also intercharacter adjustment.
intercharacter space. In bar codes, the space between
two adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter gap. Contrast with
element and space.
interleaved bar code. A bar code symbology in which
characters are paired, using bars to represent the first
character and spaces to represent the second. An example
is Interleaved 2 of 5.
intermediate device. In the IPDS architecture, a device
that operates on the data stream and is situated between a
printer and a presentation services program in the host.
Examples include devices that capture and cache
resources and devices that spool the data stream.
internal leading. A font design parameter referring to the
space provided between lines of type to keep ascenders
separated from descenders and to provide an aesthetically
pleasing interline spacing. The value of this parameter
usually equals the difference between the vertical font size
and the font baseline extent. Contrast with external
leading.
internal parameter. In PTOCA, a parameter whose
current value is contained within the object. Contrast with
external parameter.
International Color Consortium (ICC). A group of
companies chartered to develop, use, and promote cross-
platform standards so that applications and devices can
exchange color data without ambiguity.
International Organization for Standardization
(ISO). An organization of national standards bodies from
various countries established to promote development of
standards to facilitate international exchange of goods and
immediate mode • International Organization for Standardization (ISO)

## Page 246

224 FOCA Reference
services, and develop cooperation in intellectual, scientific,
technological, and economic activity.
interoperability. The capability to communicate, execute
programs, or transfer data among various functional units
in a way that requires the user to have little or no
knowledge of the unique characteristics of those units.
introducer. In GOCA, that part of the data stream passed
from a controlling environment to a communication
processor that indicates whether entities are to be
processed in immediate mode or store mode. See also
immediate mode and store mode.
IOCA. See Image Object Content Architecture.
IO Image. An image object containing IOCA constructs.
Contrast with IM Image.
IO-Image command set. In the IPDS architecture, a
collection of commands used to present IOCA data in a
page, page segment, or overlay.
IPDS. See Intelligent Printer Data Stream.
IPDS dialog. A series of IPDS commands and IPDS
acknowledge replies. An IPDS dialog begins with the first
IPDS command that an IPDS device receives and ends
either when an IPDS command explicitly ends the dialog or
when the carrying-protocol session ends. There can be
multiple independent sessions each with an IPDS dialog.
See also session.
IPS. See image presentation space.
ISO. See International Organization for Standardization.
italics. A typeface with characters that slant upward to
the right. In FOCA, italics is the common name for the
defined inclined typeface posture attribute or parameter.
J
JAN. See Japanese Article Numbering.
Japanese Article Numbering (JAN). The bar code
symbology used to code grocery items in Japan.
jog. T o cause printedsheets to be stacked in an output
stacker offset from previously stacked sheets. Jogging is
requested by using an IPDS Execute Order Anystate
Alternate Offset Stacker command.
K
Kanji. A graphic character set for symbols used in
Japanese ideographic alphabets.
kerning. The design of graphic characters so that their
character boxes overlap, resulting in the reduction of space
between characters. This allows characters to be designed
for cursive languages, ligatures, and proportionally spaced
fonts. An example of kerning is the printing of adjacent
graphic characters so they overlap on the left or right side.
kerning track. A straight-line graph that associates
vertical font size with white space adjustment. The result of
this association is used to scale fonts.
kerning track intercept. The X-intercept of a kerning
track for a given vertical font size or white space
adjustment value.
kerning track slope. The slope of a kerning track.
keyword. A two-part self-defining parameter consisting of
a one-byte identifier and a one-byte value.
L
ladder bar code. A bar code pattern presenting the axis
of the symbol in its length dimension parallel to the Ybc axis
of the bar code presentation space. Synonymous with
vertical bar code.
LAN. See local area network.
landscape. A presentation orientation in which the Xm
axis is parallel to the long sides of a rectangular physical
medium. Contrast with portrait.
language. A set of symbols, conventions, and rules that
is used for conveying information. See also pragmatics,
semantics, and syntax.
LCID. See Local Character Set Identifier .
leading. A printer's term for the amount of space between
lines of a printed page. Leading refers to the lead slug
placed between lines of type in traditional typesetting. See
also internal leading and external leading.
leading edge. (1) The edge of a character box that in the
inline direction precedes the graphic character. (2) The
front edge of a sheet as it moves through a printer.
legibility. Characteristics of presented characters that
affect how rapidly, easily, and accurately one character can
be distinguished from another. The greater the speed,
ease, and accuracy of perception, the more legible the
presented characters. Examples of characteristics that
affect legibility are character shape, spacing, and
composition.
LID. See local identifier.
ligature. A single glyph representing two or more
characters. Examples of characters that can be presented
as ligatures are ff and ffi.
interoperability • ligature

## Page 247

FOCA Reference 225
line attributes. Those attributes that pertain to straight
and curved lines. Examples of line attributes are line type
and line width.
line type. A line attribute that controls the appearance of
a line. The line type can either be a standard line type
value or a custom line type value. Contrast with line width.
line width. A line attribute that controls the appearance of
a line. Examples of line width are normal and thick.
Contrast with line type.
link. A logical connection from a source document
component to a target document component.
Loaded-Font command set. In the IPDS architecture, a
collection of commands used to load font information into a
printer and to deactivate font resources.
local area network (LAN). A data network located on a
user's premises in which serial transmission is used for
direct data communication among data stations.
Local Character Set Identifier (LCID). A local identifier
used as a character, marker, or pattern set attribute.
local identifier (LID). An identifier that is mapped by the
controlling environment to a named resource.
location. A site within a data stream. A location is
specified in terms of an offset in the number of structured
fields from the beginning of a data stream, or in the number
of bytes from another location within the data stream.
logical page. A presentation space. One or more object
areas can be mapped to a logical page. A logical page has
specifiable characteristics, such as size, shape,
orientation, and offset. The shape of a logical page is the
shape of a rectangle. Orientation and offset are specified
relative to a medium coordinate system.
logical unit. A unit of linear measurement expressed with
a unit base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following logical units
are used:
• 1 logical unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 logical unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with L-unit.
look-up table (LUT). A logical list of colors or intensities.
The list has a name and can be referenced to select a color
or intensity. See also color table.
lossless. A form of image transformation in which all of
the data is retained. Contrast with lossy.
lossy. A form of image transformation in which some of
the data is lost. Contrast with lossless.
lowercase. Pertaining to small letters as distinguished
from capital letters. Examples of small letters are a, b, and
g. Contrast with uppercase.
L-unit. A unit of linear measurement expressed with a
unit base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following L-units are
used:
• 1 L-unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 L-unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with logical unit.
LUT . See look-up table.
M
M/O. A table heading for architecture syntax. The entries
under this heading indicate whether the construct is
mandatory (M) or optional (O).
magnetic ink character recognition
(MICR). Recognition of characters printed with ink that
contains particles of a magnetic material.
mainframe interactive (MFI). Pertaining to systems in
which nonprogrammable terminals are connected to a
mainframe.
mandatory support level. Within the base-and-towers
concept, the smallest portion of architected function that is
allowed to be implemented. This is represented by a base
with no towers. Synonymous with base support level.
marker. A symbol with a recognizable appearance that is
used to identify a particular location. An example of a
marker is a symbol that is positioned by the center point of
its cell.
marker attributes. The characteristics that control the
appearance of a marker. Examples of marker attributes are
size and color.
marker cell. A conceptual rectangular box that can
include a marker symbol and the space surrounding that
symbol.
marker precision. A method used to specify the degree
of influence that marker attributes have on the appearance
of a marker.
marker set. In GOCA, an attribute used to access a
coded font.
line attributes • marker set

## Page 248

226 FOCA Reference
marker symbol. A symbol that is used for a marker.
maximum ascender height. The maximum of the
individual character ascender heights. A value for
maximum ascender height is specified for each supported
character rotation. Contrast with maximum descender
depth.
maximum baseline extent. In FOCA, the sum of the
maximum of the individual character baseline offsets and
the maximum of the individual character descender depths,
for a given font.
maximum descender depth. The maximum of the
individual character descender depths. A value for
maximum descender depth is specified for each supported
character rotation. Contrast with maximum ascender
height.
meaning. A table heading for architecture syntax. The
entries under this heading convey the meaning or purpose
of a construct. A meaning entry can be a long name, a
description, or a brief statement of function.
measurement base. A base unit of measure from which
other units of measure are derived.
media. Plural of medium. See also medium.
media destination. The destination to which sheets are
sent as the last step in the print process. Some printers
support several media destinations to allow options such
as print job distribution to one or more specific
destinations, collated copies without having to resend the
document to the printer multiple times, and routing output
to a specific destination for security reasons. Contrast with
media source.
media source. The source from which sheets are
obtained for printing. Some printers support several media
sources so that media with different characteristics (such
as size, color, and type) can be selected when desired.
Contrast with media destination.
medium. A two-dimensional conceptual space with a
base coordinate system from which all other coordinate
systems are either directly or indirectly derived. A medium
is mapped onto a physical medium in a device-dependent
manner. Synonymous with medium presentation space.
See also logical page, physical medium, and presentation
space.
Medium Map. A print control object in a Form Map that
defines resource mappings and controls modifications to a
form, page placement on a form, and form copy
generation. See also Form Map.
medium presentation space. A two-dimensional
conceptual space with a base coordinate system from
which all other coordinate systems are either directly or
indirectly derived. A medium presentation space is mapped
onto a physical medium in a device-dependent manner.
Synonymous with medium. See also logical page, physical
medium, and presentation space.
metadata. Descriptive information that is associated with
and augments other data.
metadata object. In AFP, the resource object that carries
metadata.
Metadata Object Content Architecture (MOCA). A
resource object architecture to carry metadata that serves
to provide context or additional information about an AFP
object or other AFP data.
MFI. See mainframe interactive.
MICR. See magnetic ink character recognition.
Microfilm frame. A rectangular area on the microfilm
bounded by imaginary, intersecting grid lines within which a
data frame may be recorded. The grid lines are part of
gauges used for checking microfilm, but they do not
actually appear on the microfilm.
mil. 1/1000 inch.
mix. A method used to determine how the color of a
graphics primitive is combined with the existing color of a
graphics presentation space. See also foreground mix and
background mix.
Mixed Object Document Content Architecture
(MO:DCA). An architected, device-independent data
stream for interchanging documents.
mixing. (1) Combining foreground and background of one
presentation space with foreground and background of
another presentation space in areas where the
presentation spaces intersect. (2) Combining foreground
and background of multiple intersecting object data
elements in the object presentation space.
mixing rule. A method for specifying the color attributes
of the resulting foreground and background in areas where
two presentation spaces intersect.
MO:DCA. See Mixed Object Document Content
Architecture.
MO:DCA IS/1. MO:DCA Interchange Set 1. A subset of
MO:DCA that defines an interchange format for
presentation documents.
MO:DCA IS/2. MO:DCA Interchange Set 2. A retired
subset of MO:DCA that defines an interchange format for
presentation documents.
MO:DCA IS/3. MO:DCA Interchange Set 3. A subset of
MO:DCA that defines an interchange format for print files
that supersedes MO:DCA IS/1.
marker symbol • MO:DCA IS/3

## Page 249

FOCA Reference 227
MO:DCA-L. A MO:DCA subset that defines the OS/2
Presentation Manager (PM) metafile. This format is also
known as a .met file. The definition of this MO:DCA subset
is stabilized and is no longer being developed as part of the
MO:DCA architecture. It is defined in the document
MO:DCA-L: The OS/2 Presentation Manager Metafile
(.met) Format, available at www.afpcinc.org.
MO:DCA-P . A subset of the MO:DCA architecture that
defines presentation documents. This term is now
synonymous with the term MO:DCA.
MOCA. See Metadata Object Content Architecture.
model space. A two-dimensional conceptual space in
which a picture is constructed. All model transforms are
completed before a picture is constructed in a graphics
model space. Contrast with graphics presentation space.
Synonymous with graphics model space.
model transform. A transform that is applied to drawing-
order coordinates. Contrast with viewing transform.
module. In a bar code symbology, the nominal width of
the smallest element of a bar or space. Actual bar code
symbology bars and spaces can be a single module wide
or some multiple of the module width. The multiple need
not be an integer.
modulo-N check. A check in which an operand is divided
by a modulus to generate a remainder that is retained and
later used for checking. An example of an operand is the
sum of a set of digits. See also modulus.
modulus. In a modulo check, the number by which an
operand is divided. An example of an operand is the sum
of a set of digits. See also modulo-N check.
monospaced font. A font with graphic characters having
a uniform character increment. The distance between
reference points of adjacent graphic characters is constant
in the escapement direction. The blank space between the
graphic characters can vary. Synonymous with uniformly
spaced font. Contrast with proportionally spaced font and
typographic font.
move order. A drawing order that specifies or implies
movement from the current position to a given position.
See also current position and given position.
N
NACK. See Negative Acknowledge Reply.
name. A table heading for architecture syntax. The
entries under this heading are short names that give a
general indication of the contents of the construct.
named color. A color that is specified with a descriptive
name. An example of a named color is “green”.
navigation. The traversing of a document based on links
between contextually related document components.
navigation link. A link type that specifies the linkage from
a source document component to a contextually related
target document component. Navigation links can be used
to support applications such as hypertext and hypermedia.
Negative Acknowledge Reply (NACK). In the IPDS
architecture, a reply from a printer to a host, indicating that
an exception has occurred. Contrast with Positive
Acknowledge Reply.
nested resource. A resource that is invoked within
another resource using either an Include command or a
local ID. See also nesting resource.
nesting coordinate space. A coordinate space that
contains another coordinate space. Examples of
coordinate spaces are medium, overlay, page, and object
area.
nesting resource. A resource that invokes nested
resources. See also nested resource.
neutral white. A color attribute that gives a device-
dependent default color, typically white on a screen and
black on a printer. Note that neutral white and color of
medium are two different colors.
nonprocess runout (NPRO). An operation that moves
sheets of physical media through the printer without
printing on them. This operation is used to stack the last
printed sheet.
no operation (NOP). A construct whose execution
causes a product to proceed to the next instruction to be
processed without taking any other action.
NOP . See no operation.
normal-duplex printing. Duplex printing that simulates
the effect of physically turning the sheet around the Y
m
axis.
NPRO. See nonprocess runout.
N-up. The partitioning of a side of a sheet into a fixed
number of equal size partitions. For example, 4-up divides
each side of a sheet into four equal partitions.
O
object. (1) A collection of structured fields. The first
structured field provides a begin-object function, and the
last structured field provides an end-object function. The
object can contain one or more other structured fields
whose content consists of one or more data elements of a
particular data type. An object can be assigned a name,
that can be used to reference the object. Examples of
objects are presentation text, font, graphics, and image
MO:DCA-L • object

## Page 250

228 FOCA Reference
objects. (2) Something that a user works with to perform a
task.
object area. A rectangular area in a presentation space
into which a data object is mapped. The presentation
space can be for a page or an overlay. Examples are a
graphics object area, an image object area, and a bar code
object area.
object data. A collection of related data elements
bundled together. Examples of object data include graphic
characters, image data elements, and drawing orders.
object identifier (OID). (1) A notation that assigns a
globally unambiguous name to an object or a document
component. The notation is defined in international
standard ISO/IEC 8824(E). (2) A variable length (2-bytes
long to 129-bytes long) binary ID that uniquely identifies an
object. OIDs use the ASN.1 definite-short-form object
identifier format defined in the ISO/IEC 8824:1990(E)
international standard and described in the MO:DCA
Registry Appendix of the Mixed Object Document Content
Architecture Reference. An OID consists of a one-byte
identifier (X'06'), followed by a one-byte length (between
X'00' and X'7F'), followed by 0–127 content bytes.
OCR-A. See Optical Character Recognition-A.
OCR-B. See Optical Character Recognition-B.
offline. A device state in which the device is not under the
direct control of a host. Contrast with online.
offset. A table heading for architecture syntax. The
entries under this heading indicate the numeric
displacement into a construct. The offset is measured in
bytes and starts with byte zero. Individual bits can be
expressed as displacements within bytes.
OID. See object identifier.
online. A device state in which the device is under the
direct control of a host. Contrast with offline.
opacity. In bar codes, the optical property of a substrate
material that minimizes showing through from the back
side or the next sheet.
Optical Character Recognition-A (OCR-A). A font
containing the character set in ANSI standard X3.17-1981,
that contains characters that are both human-readable and
machine-readable.
Optical Character Recognition-B (OCR-B). A font
containing the character set in ANSI standard X3.49-1975,
that contains characters that are both human-readable and
machine-readable.
order. (1) In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify, for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Orders can be
included in a structured field. Synonymous with drawing
order. (2) In the IPDS architecture, a construct within an
execute-order command. (3) In IOCA, a functional
operation that is performed on the image content.
ordered page. In the IPDS architecture, a logical page
that does not contain any page segments or overlays, and
in which all text data and all image, graphics, and bar code
objects are ordered. The order of the data objects is such
that physical pel locations on the physical medium are
accessed by the printer in a sequential left-to-right and top-
to-bottom manner, where these directions are relative to
the top edge of the physical medium. Once a physical pel
location has been accessed by the printer, the page data
does not require the printer to access that same physical
pel location again.
orientation. The angular distance a presentation space
or object area is rotated in a specified coordinate system,
expressed in degrees and minutes. For example, the
orientation of printing on a physical medium, relative to the
X
m axis of the Xm,Ym coordinate system. See also
presentation space orientation and text orientation.
origin. The point in a coordinate system where the axes
intersect. Examples of origins are the addressable position
in an Xm,Ym coordinate system where both coordinate
values are zero and the character reference point in a
character coordinate system.
orthogonal. Intersecting at right angles. An example of
orthogonal is the positional relationship between the axes
of a Cartesian coordinate system.
outline font. A shape technology in which the graphic
character shapes are represented in digital form by a
series of mathematical expressions that define the outer
edges of the strokes. The resultant graphic character
shapes can be either solid or hollow.
output profile. An ICC profile that describes the
characteristics of the output device for which the image is
destined. The profile is used to color match the image to
the device's gamut.
overhead. In a bar code symbology, the fixed number of
characters required for starting, stopping, and checking a
bar code symbol.
overlay. (1) A resource object that contains presentation
data such as, text, image, graphics, and bar code data.
Overlays define their own environment and are often used
as pre-defined pages or electronic forms. Overlays are
classified according to how they are presented with other
presentation data: a medium overlay is positioned at the
origin of the medium presentation space before any pages
are presented, and a page overlay is positioned at a
specified point in a page's logical page. A Page
Modification Control (PMC) overlay is a special type of
object area • overlay

## Page 251

FOCA Reference 229
page overlay used in MO:DCA environments. (2) The final
representation of such an object on a physical medium.
Contrast with page segment.
Overlay command set. In the IPDS architecture, a
collection of commands used to load, deactivate, and
include overlays.
overlay ID. A one-byte ID assigned by a host to an
overlay. Overlay IDs are used in IPDS Begin Overlay,
Deactivate Overlay, Include Overlay, and Load Copy
Control commands.
overlay state. An operating state that allows overlay data
to be downloaded to a product. For example, a printer
enters overlay state from home state when the printer
receives an IPDS Begin Overlay command.
overpaint. A mixing rule in which the intersection of part
of a new presentation space Pnew with an existing
presentation space P existing keeps the color attribute of Pnew.
This is also referred to as “opaque” mixing. See also mixing
rule. Contrast with blend and underpaint.
overscore. A line parallel to the baseline and placed
above the character.
overstrike. In PTOCA, the presentation of a designated
character as a string of characters in a specified text field.
The intended effect is to make the resulting presentation
appear as though the text field, whether filled with
characters or blanks, has been marked out with the
overstriking character.
overstriking. The method used to merge two or more
graphic characters at the same addressable position in a
presentation space or on a physical medium.
P
page. (1) A data stream object delimited by a Begin Page
structured field and an End Page structured field. A page
can contain presentation data such as text, image,
graphics, and bar code data. (2) The final representation
of a page object on a physical medium.
page counter. Bytes in an IPDS Acknowledge Reply that
specify the number of pages that have passed a particular
point in a logical paper path.
page group. A named group of sequential pages. A page
group is delimited by a Begin Named Page Group
structured field and an End Named Page Group structured
field. A page group can contain nested page groups. All
pages in the page group inherit the attributes and
processing characteristics that are assigned to the page
group.
page segment. (1) In the IPDS architecture, a resource
object that can contain text, image, graphics, and bar code
data. Page segments do not define their own environment,
but are processed in the existing environment. (2) In
MO:DCA, a resource object that can contain any mixture of
bar code objects, graphics objects, and IOCA image
objects. A page segment does not contain an active
environment group. The environment for a page segment
is defined by the active environment group of the including
page or overlay. (3) The final representation of such an
object on a physical medium. Contrast with overlay.
Page-Segment command set. In the IPDS architecture,
a collection of commands used to load, deactivate, and
include page segments.
page-segment state. An operating state that makes
page-segment data available to a product. For example, a
printer enters page-segment state from home state when it
receives an IPDS Begin Page Segment command.
page state. In the IPDS architecture, an operating state
that makes page data available to a product. For example,
a printer enters page state from home state when it
receives an IPDS Begin Page command.
paginated object. A data object that can be rendered on
a single page or overlay. An example of a paginated object
is a single image in a multi-image TIFF file.
parameter. (1) A variable that is given a constant value
for a specified application. (2) A variable used in
conjunction with a command to affect its result.
partition. Dividing the medium presentation space into a
specified number of equal-sized areas in a manner
determined by the current physical media.
partitioning. A method used to place parts of a control
into two or more segments or structured fields. Partitioning
can cause difficulties for a receiver if one of the segments
or structured fields is not received or is received out of
order.
pattern. An array of symbols used to fill an area.
pattern attributes. The characteristics that specify the
appearance of a pattern.
pattern reference point. In GOCA, a position in the
graphics presentation space to be used as the origin of a
custom pattern; the pattern is tiled in all directions from this
position.
pattern set. An attribute in GOCA used to access a
symbol set or coded font.
pattern symbol. The geometric construct that is used
repetitively to generate a pattern. Examples of pattern
symbols are dots, squares, and triangles.
PCS. (1) See Print Contrast Signal. (2) See Profile
Connection Space.
Overlay command set • PCS

## Page 252

230 FOCA Reference
pel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity. Pels per inch is often used as
a measurement of presentation granularity. Synonymous
with picture element and pixel.
PFB file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for small character set fonts having one to two hundred
characters (for example, English, Greek, and Cyrillic).
PFO. See preprinted form overlay.
physical medium. A physical entity on which information
is presented. Examples of a physical medium are a sheet
of paper, a roll of paper, an envelope, and a display screen.
See also medium presentation space and sheet.
physical printable area. A bounded area defined on a
side of a sheet within which printing can take place. The
physical printable area is an attribute of sheet size and
printer capabilities, and cannot be altered by the host. The
physical printable area is mapped to the medium
presentation space, and is used in user printable area and
valid printable area calculations. Contrast with user
printable area and valid printable area.
picket fence bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the X
bc
axis of the bar code presentation space. Synonymous with
horizontal bar code.
picture chain. A string of segments that defines a picture.
Synonymous with segment chain.
picture element. The smallest printable or displayable
unit on a physical medium. In computer graphics, the
smallest element of a physical medium that can be
independently assigned color and intensity. Picture
elements per inch is often used as a measurement of
presentation granularity. Synonymous with pel and pixel.
pixel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity. Picture elements per inch is
often used as a measurement of presentation granularity.
Synonymous with pel and picture element.
point. (1) A unit of measure used mainly for measuring
typographical material. There are seventy-two points to an
inch. (2) In GOCA, a parameter that specifies the position
within the drawing order coordinate space. See also
drawing order coordinate space.
polyline. A sequence of connected lines.
portrait. A presentation orientation in which the X
m axis is
parallel to the short sides of a rectangular physical
medium. Contrast with landscape.
position. A position in a presentation space or on a
physical medium that can be identified by a coordinate
from the coordinate system of the presentation space or
physical medium. See also picture element. Synonymous
with addressable position.
Positive Acknowledge Reply (ACK). In the IPDS
architecture, a reply to an IPDS command that has its
acknowledgment-required flag on and in which no
exception is reported. Contrast with Negative Acknowledge
Reply.
posture. Inclination of a letter with respect to a vertical
axis. Examples of inclination are upright and inclined. An
example of upright is Roman. An example of inclined is
italics.
pragmatics. Information related to the usage of a
construct. See also semantics and syntax.
preprinted form. A form or sheet that is not blank when it
is selected as input media for presentation.
preprinted form overlay (PFO). An overlay and
associated processing designed to simulate a preprinted
form.
presentation device. A device that produces character
shapes, graphics pictures, images, or bar code symbols on
a physical medium. Examples of a physical medium are a
display screen and a sheet of paper.
presentation position. An addressable position that is
coincident with a character reference point. See also
addressable position and character reference point.
presentation services. In printing, a software component
that communicates with a printer using a printer data
stream, such as the IPDS data stream, to print pages,
download and manage print resources, and handle
exceptions.
presentation space. A conceptual address space with a
specified coordinate system and a set of addressable
positions. The coordinate system and addressable
positions can coincide with those of a physical medium.
Examples of presentation spaces are medium, logical
page, and object area. See also graphics presentation
space, image presentation space, logical page, medium
presentation space, and text presentation space.
presentation space orientation. The number of degrees
and minutes a presentation space is rotated in a specified
coordinate system. For example, the orientation of printing
on a physical medium, relative to the X m axis of the Xm,Ym
coordinate system. See also orientation and text
orientation.
pel • presentation space orientation

## Page 253

FOCA Reference 231
presentation text object. An object that contains
presentation text data. See also object.
Presentation Text Object Content Architecture
(PTOCA). An architected collection of constructs used to
interchange and present presentation text data.
print contrast. A measurement of the ratio of the
reflectivities between the bars and spaces of a bar code
symbol, commonly expressed as a percent. Synonymous
with Print Contrast Signal.
Print Contrast Signal (PCS). A measurement of the ratio
of the reflectivities between the bars and spaces of a bar
code symbol, commonly expressed as a percent.
Synonymous with print contrast.
print control object. A resource object that contains
layout, finishing, and resource mapping information used to
present a document on physical media. Examples of print
control objects are Form Maps and Medium Maps.
print direction. In FOCA, the direction in which
successive characters appear in a line of text.
printing baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with baseline and sequential
baseline.
print quality. In bar codes, the measure of compliance of
a bar code symbol to the requirements of dimensional
tolerance, edge roughness, spots, voids, reflectance, PCS,
and quiet zones defined within a bar code symbology.
print unit. In the IPDS architecture, a group of pages
bounded by XOH-DGB commands and subject to the
group operation keep group together as a print unit. A print
unit is commonly referred to as a print job.
process color. A color that is specified as a combination
of the components, or primaries, of a color space. A
process color is rendered by mixing the specified amounts
of the primaries. An example of a process color is C=0.1,
M=0.8, Y=0.2, K=0.1 in the cyan/magenta/yellow/black
(CMYK) color space. Contrast with spot color.
process element. In MO:DCA, a document component
that is defined by a structured field and that facilitates a
form of document processing that does not affect the
presentation of the document. Examples of process
elements are T ag Logical Elements (TLEs) that specify
document attributes and Link Logical Elements (LLEs) that
specify linkages between document components.
Profile Connection Space (PCS). The reference color
space defined by ICC, in which colors are encoded in order
to provide an interface for connecting source and
destination transforms. The PCS is based on the CIE 1931
standard colorimetric observer.
prolog. The first portion of a segment's data. Prologs are
optional. They contain attribute settings and drawing
controls. Synonymous with segment prolog.
propagation. A method used to retain a segment's
properties through other segments that it calls.
proper subset. A set whose members are also members
of a larger set.
proportion. Relationship of the width of a letter to its
height.
proportional spacing. The spacing of characters in a
printed line so that each character is allotted a space
based on the character's width.
Proportional Spacing Machine font (PSM font). A font
originating with the electric typewriter and having character
increment values that are integer multiples of the narrowest
character width.
proportionally spaced font. A font with graphic
characters that have varying character increments.
Proportional spacing can be used to provide the
appearance of even spacing between presented
characters and to eliminate excess blank space around
narrow characters. An example of a narrow character is the
letter i. Synonymous with typographic font. Contrast with
monospaced font and uniformly spaced font.
PSM font. See Proportional Spacing Machine font.
PTOCA. See Presentation Text Object Content
Architecture.
Q
quiet zone. A clear space that contains no machine-
readable marks preceding the start character of a bar code
symbol or following the stop character. Synonymous with
clear area. Contrast with intercharacter gap and space.
R
range. A table heading for architecture syntax. The
entries under this heading give numeric ranges applicable
to a construct. The ranges can be expressed in binary,
decimal, or hexadecimal. The range can consist of a single
value.
rasterize. T o convert presentation data into raster
(bitmap) form for display or printing.
raster pattern. A rectangular array of pels arranged in
rows called scan lines.
readability. The characteristics of visual material that
determine the degree of comfort with which it can be read
over a sustained period of time. Examples of
presentation text object • readability

## Page 254

232 FOCA Reference
characteristics that influence readability are type quality,
spacing, and composition.
reader. In bar code systems, the scanner or combination
of scanner and decoder. See also decoder and scanner.
read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with first read rate.
Rearranged File. A file containing the mapping of code
points to the character index values used in a CID file and
to the character names used in one or more PFB files. This
is a special case of the CMAP file which permits linking of
multiple font files and formats together. The code points
conform to a particular character coding system which is
used to identify the characters in a document data stream.
The mapping information in this file is in an ASCII file
format defined by Adobe Systems Inc.
recording algorithm. An algorithm that determines the
relationship between the physical location and logical
location of image points in image data.
recovery-unit group. In the IPDS architecture, a group of
pages identified by the XOH Define Group Boundary
command and controlled by the Keep-Group-T ogether-as-
a-Recovery-Unit group operation specified by the XOH
Specify Group Operation command. The recovery-unit
group also includes all copies specified by the Load Copy
Control command.
redaction. The process of applying an opaque mask over
a page so that a selected portion of the page is visible.
Since this function is typically used to prevent unauthorized
viewing of data, an associated security level is also
provided.
reflectance. In bar codes, the ratio of the amount of light
of a specified wavelength or series of wavelengths
reflected from a test surface to the amount of light reflected
from a barium oxide or magnesium oxide standard under
similar illumination conditions.
relative coordinate. One of the coordinates that identify
the location of an addressable point by means of a
displacement from some other addressable point. Contrast
with absolute coordinate.
relative line. A straight line developed from a specified
point by a given displacement.
relative metrics. Graphic character measurements
expressed as fractions of a square, called the Em square,
whose sides correspond to the vertical size of the font.
Because the measurements are relative to the size of the
Em square, the same metrics can be used for different
point sizes and different raster pattern resolutions. Relative
metrics require defining the unit of measure for the Em
square, the point size of the font, and, if applicable, the
resolution of the raster pattern.
relative move. A method used to establish a new current
position. Distance and direction from the current position
are used to establish the new current position. The
direction of displacement is inline along the I axis in the I-
direction, or baseline along the B axis in the B-direction, or
both.
relative positioning. The establishment of a position
within a coordinate system as an offset from the current
position. Contrast with absolute positioning.
repeat string. A method used to repeat the character
content of text data until a given number of characters has
been processed. Any control sequences in the text data
are ignored. This method provides the functional
equivalence of a Transparent Data control sequence when
the given number of repeated characters is equal to the
number of characters in the text data.
repeating group. A group of parameter specifications
that can be repeated.
reserved. Having no assigned meaning and put aside for
future use. The content of reserved fields is not used by
receivers, and should be set by generators to a specified
value, if given, or to binary zeros. A reserved field or value
can be assigned a meaning by an architecture at any time.
reset color. The color of a presentation space before any
data is added to it. Synonymous with color of medium.
resident resource. In the IPDS architecture, a resource
in a printer or in a resource-caching intermediate device. A
resident resource can be installed manually or can be
captured by the device if it is intended for public use. A
resident resource is referenced by a global ID that is valid
for the duration of the resource's presence in the device.
Contrast with downloaded resource.
resolution. (1) A measure of the sharpness of an input or
output device capability, as given by some measure
relative to the distance between two points or lines that can
just be distinguished. (2) The number of addressable pels
per unit of length.
resolution correction. A method used to present an
image on a printer without changing the physical size or
proportions of the image when the resolutions of the printer
and the image are different.
resolution-correction ratio. The ratio of a device
resolution to an image presentation space resolution.
resolution modification. A method used to write an
image on an image presentation space without changing
the physical size of the image when the resolutions of the
presentation space and the image are different.
resource. An object that is referenced by a data stream
or by another object to provide data or information.
Resource objects can be stored in libraries. In MO:DCA,
resource objects can be contained within a resource group.
reader • resource

## Page 255

FOCA Reference 233
Examples of resources are fonts, overlays, and page
segments. See also downloaded resource and resident
resource.
resource caching. In the IPDS architecture, a function in
a printer or intermediate device whereby downloaded
resources are captured and made resident in the printer or
intermediate device.
retired. Set aside for a particular purpose, and not
available for any other purpose. Retired fields and values
are specified for compatibility with existing products and
identify one of the following:
• Fields or values that have been used by a product in a
manner not compliant with the architected definition
• Fields or values that have been removed from an
architecture
RGB. Red, green and blue, the additive primary colors.
RGB color space. The basic additive color model used
for color video display, as on a computer monitor.
RM4SCC. See Royal Mail 4 State Customer Code.
Roman. Relating to a type style with upright letters.
root segment. A segment in the picture chain that is not
called by any other segment. If a single segment that is not
in a segment chain is drawn, it is treated as a root segment
for the duration of the drawing process.
rotating. In computer graphics, turning all or part of a
picture about an axis perpendicular to the presentation
space.
rotation. The orientation of a presentation space with
respect to the coordinate system of a containing
presentation space. Rotation is measured in degrees in a
clockwise direction. Zero-degree rotation exists when the
angle between a presentation space's positive X axis and
the containing presentation space's positive X axis is zero
degrees. Contrast with character rotation.
row. A subarray that consists of all elements that have an
identical position within the high dimension of a regular
two-dimensional array.
Royal Mail 4 State Customer Code (RM4SCC). A two-
dimensional bar code symbology developed by the United
Kingdom's Royal Mail postal service for use in automated
mail-sorting processes.
rule. A solid line of any line width.
S
sans serif. A type style characterized by strokes that end
with no flaring or crossing of lines at the stroke-ends.
Contrast with serif.
SBCS. See single-byte character set.
SBIN. A data type for architecture syntax, that indicates
that one or more bytes be interpreted as a signed binary
number, with the sign bit in the high-order position of the
leftmost byte. Positive numbers are represented in true
binary notation with the sign bit set to B'0'. Negative
numbers are represented in twos-complement binary
notation with a B'1' in the sign-bit position.
scaling. Making all or part of a picture smaller or larger by
multiplying the coordinate values of the picture by a
constant amount. If the same multiplier is applied along
both dimensions, the scaling is uniform, and the
proportions of the picture are unaffected. Otherwise, the
scaling is anamorphic, and the proportions of the picture
are changed. See also anamorphic scaling.
scaling ratio. (1) The ratio of an image-object-area size
to its image-presentation-space size. (2) In FOCA, the
ratio of horizontal to vertical scaling of the graphic
characters. See also horizontal scale factor.
scan line. A series of picture elements. Scan lines in
raster patterns form images. See also picture element and
raster pattern.
scanner. In bar codes, an electronic device that converts
optical information into electrical signals. See also reader.
scrolling. A method used to move a displayed image
vertically or horizontally so that new data appears at one
edge as old data disappears at the opposite edge. Data
disappears at the edge toward which an image is moved
and appears at the edge away from which the data is
moved.
SDA. See special data area.
section. A portion of a double-byte code page that
consists of 256 consecutive entries. The first byte of a two-
byte code point is the section identifier. A code-page
section is also called a code-page ward in some
environments. See also code page and code point.
section identifier. A value that identifies a section.
Synonymous with section number.
section number. A value that identifies a section.
Synonymous with section identifier.
secure overlay. An overlay that can be printed anywhere
within the physical printable area. A secure overlay is not
affected by an IPDS Define User Area command.
segment. (1) In GOCA, a set of graphics drawing orders
contained within a Begin Segment command. See also
graphics segment. (2) In IOCA, image content bracketed
by Begin Segment and End Segment self-defining fields.
See also image segment.
resource caching • segment

## Page 256

234 FOCA Reference
segment chain. A string of segments that defines a
picture. Synonymous with picture chain.
segment exception condition. An architecture-provided
classification of the errors that can occur in a segment.
Segment exception conditions are raised when a segment
error is detected. Examples of segment errors are segment
format, parameter content, and sequence errors.
segment offset. A position within a segment, measured
in bytes from the beginning of the segment. The beginning
of a segment is always at offset zero.
segment prolog. The first portion of a segment's data.
Prologs are optional. They contain attribute settings and
drawing controls. Synonymous with prolog.
segment properties. The segment characteristics used
by a drawing process. Examples of segment properties are
segment name, segment length, chained, dynamic,
highlighted, propagated, and visible.
segment transform. A model transform that is applied to
a whole segment.
self-checking. In bar codes, using a checking algorithm
that can be applied to each character independently to
guard against undetected errors.
semantics. The meaning of the parameters of a
construct. See also pragmatics and syntax.
sequential baseline. A conceptual line with respect to
which successive characters are aligned. See also
character baseline. Synonymous with baseline and printing
baseline.
sequential baseline position. The current addressable
position for a baseline in a presentation space or on a
physical medium. See also baseline coordinate and current
baseline presentation coordinate.
serif. A short line angling from or crossing the free end of
a stroke. Examples are horizontal lines at the tops and
bottoms of vertical strokes on capital letters, for example, I
and H, and the decorative strokes at the ends of the
horizontal members of a capital E. Contrast with sans serif.
session. In the IPDS architecture, the period of time
during which a presentation services program has a two-
way communication with an IPDS device. The session
consists of a physical attachment and a communications
protocol; the communications protocol carries an IPDS
dialog by transparently transmitting IPDS commands and
acknowledge replies. See also IPDS dialog.
setup file. In the IPDS architecture, an object container
that provides setup information for a printer. Setup files are
downloaded in home state and take effect immediately.
Setup files are not managed as resources.
shade. Variation of a color produced by mixing it with
black.
shape compression. A method used to compress
digitally encoded character shapes using a specified
algorithm.
shape technology. A method used to encode character
shapes digitally using a specified algorithm.
shear. The angle of slant of a character cell that is not
perpendicular to a baseline. Synonymous with character
shear.
shearline direction. In GOCA, the direction specified by
the character shear and character angle attributes.
sheet. A division of the physical medium; multiple sheets
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper, each representing a sheet. Envelopes are an
example of a physical medium that comprises only one
sheet. The IPDS architecture defines four types of
sheets: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
sheet has a top edge. A sheet has two sides, a front side
and a back side. Synonymous with form.
show-through. In bar codes, the generally undesirable
property of a substrate that permits underlying markings to
be seen.
side. A physical surface of a sheet. A sheet has a front
side and a back side. See also sheet.
simplex printing. A method used to print data on one
side of a sheet; the other side is left blank. Contrast with
duplex printing.
single-byte character set (SBCS). A character set that
can contain up to 256 characters.
single-byte coded font. A coded font in which the code
points are one byte long.
slope. The posture, or incline, of the main strokes in the
graphic characters of a font. Slope is specified in degrees
by a font designer.
space. In bar codes, the lighter element of a printed bar
code symbol, usually formed by the background between
bars. See also element. Contrast with bar, clear area,
intercharacter gap, and quiet zone.
space width. In bar codes, the thickness of a bar code
symbol space measured from the edge closest to the
symbol start character to the trailing edge of the same
space.
spanning. In the IPDS architecture, a method in which
one command is used to start a sequence of constructs.
segment chain • spanning

## Page 257

FOCA Reference 235
Subsequent commands continue and terminate that
sequence. See also control sequence chaining.
special data area (SDA). The data area in an IPDS
Acknowledge Reply that contains data requested by the
host or generated by a printer as a result of an exception.
Specifications for Web Offset Publications (SWOP). A
standard set of specifications for color separations, proofs,
and printing to ensure consistency of color printing.
spot. In bar codes, the undesirable presence of ink or dirt
in a bar code symbol space.
spot color. A color that is specified with a unique
identifier such as a number. A spot color is normally
rendered with a custom colorant instead of with a
combination of process color primaries. See also highlight
color. Contrast with process color.
stack. A list that is constructed and maintained so that the
next item to be retrieved and removed is the most recently
stored item still in the list. This is sometimes called last-in-
first-out (LIFO).
standard action. The architecture-defined action to be
taken on detecting an exception condition, when the
controlling environment specifies that processing should
continue.
standard line type value. A predefined line type, like
solid, invisible, or dash-dot. Contrast with custom line type
value.
start-stop character or pattern. In bar codes, a special
bar code character that provides the scanner with start and
stop reading instructions as well as a scanning direction
indicator. The start character is normally at the left end and
the stop character at the right end of a horizontally oriented
symbol.
store mode. A mode in which segments are stored for
later execution. Contrast with immediate mode.
stroke. A straight or curved line used to create the shape
of a letter.
structured field. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. See also
document element.
structured field introducer. In MO:DCA, the header
component of a structured field that provides information
that is common for all structured fields. Examples of
information that is common for all structured fields are
length, function type, and category type. Examples of
structured field function types are begin, end, data, and
descriptor. Examples of structured field category types are
presentation text, image, graphics, and page.
subset. Within the base-and-towers concept, a portion of
architecture represented by a particular level in a tower or
by a base. See also subsetting tower.
subsetting tower. Within the base-and-towers concept, a
tower representing an aspect of function achieved by an
architecture. A tower is independent of any other towers. A
tower can be subdivided into subsets. A subset contains all
the function of any subsets below it in the tower. See also
subset.
substrate. In bar codes, the surface on which a bar code
symbol is printed.
subtractive primary colors. Cyan, magenta, and yellow
colorants used to subtract a portion of the white light that is
illuminating an object. Subtractive colors are reflective on
paper and printed media. When used together with various
degrees of coverage and variation, they have the ability to
create billions of other colors. Contrast with additive
primary colors.
suppression. A method used to prevent presentation of
specified data. Examples of suppression are the
processing of text data without placing characters on a
physical medium and the electronic equivalent of the “spot
carbon,” that prevents selected data from being presented
on certain copies of a presentation space or a physical
medium.
surrogate pair. A sequence of two Unicode code points
that allow for the encoding of as many as 1 million
additional characters without any use of escape codes.
SWOP . See Specifications for Web Offset Publications.
symbol. (1) A visual representation of something by
reason of relationship, association, or convention. (2) In
GOCA, the subpicture referenced as a character definition
within a font character set and used as a character, marker,
or fill pattern. A bitmap can also be referenced as a symbol
for use as a fill pattern. See also bar code symbol.
symbol length. In bar codes, the distance between the
outside edges of the quiet zones of a bar code symbol.
symbol set. A coded font that is usually simpler in
structure than a fully described font. Symbol sets are used
where typographic quality is not required. Examples of
devices that might not provide typographic quality are dot-
matrix printers and displays. See also character set,
marker set, and pattern set.
symbology. A bar code language. Bar code symbologies
are defined and controlled by various industry groups and
standards organizations. Bar code symbologies are
described in public domain bar code specification
documents. Synonymous with bar code symbology. See
also Canadian Grocery Product Code (CGPC), European
Article Numbering (EAN), Japanese Article Numbering
(JAN), and Universal Product Code (UPC).
special data area (SDA) • symbology

## Page 258

236 FOCA Reference
synchronous exception. In the IPDS architecture, a
data-stream, function no longer achievable, or resource-
storage exception that must be reported to the host before
a printer can return a Positive Acknowledge Reply or can
increment the received-page counter for a page containing
the exception. Synchronous exceptions are those with
action code X'01', X'06', X'0C', or X'1F'. See also data-
stream exception. Contrast with asynchronous exception.
syntax. The rules governing the structure of a construct.
See also pragmatics and semantics.
system-level font resource. A common-source font from
which:
• Document-processing applications can obtain resolution-
independent formatting information.
• Device-service applications can obtain device-specific
presentation information.
T
temporary baseline. The shifted baseline used for
subscript and superscript.
temporary baseline coordinate. The B-value of the I,B
coordinate pair of an addressable position on the
temporary baseline.
temporary baseline increment. A positive or negative
value that is added to the current baseline presentation
coordinate to specify the position of a temporary baseline
in a presentation space or on a physical medium. Several
increments might have been used to place a temporary
baseline at the current baseline presentation coordinate.
text. A graphic representation of information. T ext can
consist of alphanumeric characters and symbols arranged
in paragraphs, tables, columns, and other shapes. An
example of text is the data sent in an IPDS Write T ext
command.
Text command set. In the IPDS architecture, a collection
of commands used to present PTOCA text data in a page,
page segment, or overlay.
text orientation. A description of the appearance of text
as a combination of inline direction and baseline direction.
See also baseline direction, inline direction, orientation,
and presentation space orientation.
text presentation. The transformation of document
graphic character content and its associated font
information into a visible form. An example of a visible form
of text is character shapes on a physical medium.
text presentation space. A two-dimensional conceptual
space in which text is generated for presentation on an
output medium.
throughscore. A line parallel to the baseline and placed
through the character.
tint. Variation of a color produced by mixing it with white.
toned. Containing marking agents such as toner or ink.
Contrast with untoned.
transform. A modification of one or more characteristics
of a picture. Examples of picture characteristics that can be
transformed are position, orientation, and size. See also
model transform, segment transform, and viewing
transform.
transform matrix. A matrix that is applied to a set of
coordinates to produce a transform.
translating. In computer graphics, moving all or part of a
picture in the presentation space from one location to
another without rotating.
transparent data. A method used to indicate that any
control sequences occurring in a specified portion of data
can be ignored.
trimming. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window.
Synonymous with clipping.
triplet. A three-part self-defining variable-length
parameter consisting of a length byte, an identifier byte,
and
parameter-value bytes.
triplet identifier. A one-byte type identifier for a triplet.
truncation. Planned or unplanned end of a presentation
space or data presentation. This can occur when the
presentation space extends beyond one or more
boundaries of its containing presentation space or when
there is more data than can be contained in the
presentation space.
tumble-duplex printing. A method used to simulate the
effect of physically turning a sheet around the Xm axis.
twip. A unit of measure equal to 1/20 of a point. There are
1440 twips in one inch.
type. A table heading for architecture syntax. The entries
under this heading indicate the types of data present in a
construct. Examples include: BITS, CHAR, CODE, SBIN,
UBIN, UNDF.
typeface. All characters of a single type family or style,
weight class, width class, and posture, regardless of size.
For example, Helvetica Bold Condensed Italics, in any
point size.
type family. All characters of a single design, regardless
of attributes such as width, weight, posture, and size.
Examples are Courier and Gothic.
synchronous exception • type family

## Page 259

FOCA Reference 237
type structure. Attributes of characters other than type
family or typeface. Examples are solid shape, hollow
shape, and overstruck.
type style. The form of characters within the same font,
for example, Courier or Gothic.
type weight. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its type weight. Examples are light, medium,
and bold. Synonymous with weight class.
type width. A parameter indicating a relative change from
the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with
width class.
typographic font. A font with graphic characters that
have varying character increments. Proportional spacing
can be used to provide the appearance of even spacing
between presented characters and to eliminate excess
blank space around narrow characters. An example of a
narrow character is the letter i. Synonymous with
proportionally spaced font. Contrast with monospaced font
and uniformly spaced font.
U
UBIN. A data type for architecture syntax, indicating one
or more bytes to be interpreted as an unsigned binary
number.
unarchitected. Identifies data that is neither defined nor
controlled by an architecture. Contrast with architected.
unbounded character box. A character box that can
have blank space on any sides of the character shape.
underpaint. A mixing rule in which the intersection of part
of a new presentation space P
new with part of an existing
presentation space P existing keeps the color attribute of
Pexisting. This is also referred to as “transparent” or “leave
alone” mixing. See also mixing rule. Contrast with blend
and overpaint.
underscore. A method used to create an underline
beneath the characters in a specified text field. An example
of underscore is the line presented under one or more
characters. Also a special graphic character used to
implement the underscoring function.
UNDF . A data type for architecture syntax, indicating one
or more bytes that are undefined by the architecture.
Unicode. A character encoding standard for information
processing that includes all major scripts of the world.
Unicode defines a consistent way of encoding multilingual
text. Unicode specifies a numeric value, a name, and other
attributes, such as directionality, for each of its characters;
for example, the name for $ is “dollar sign” and its numeric
value is X'0024'. This Unicode value is called a Unicode
code point and is represented as U+nnnn. Unicode
provides for three encoding forms (UTF-8, UTF-16, and
UTF-32), described as follows:
UTF-8 A byte-oriented form that is designed for
ease of use in traditional ASCII
environments. Each UTF-8 code point
contains from one to four bytes. All
Unicode code points can be encoded in
UTF-8 and all 7-bit ASCII characters can
be encoded in one byte.
UTF-16 The default Unicode encoding. A fixed,
two-byte Unicode encoding form that
can contain surrogates and identifies the
byte order of each UTF-16 code point via
a Byte Order Mark in the first 2 bytes of
the data. Surrogates are pairs of
Unicode code points that allow for the
encoding of as many as 1 million
additional characters without any use of
escape codes.
UTF-16BE UTF-16 that uses big endian byte order;
this is the byte order for all multi-byte
data within AFP data streams. The Byte
Order Mark is not necessary when the
data is externally identified as UTF-16BE
(or UTF-16LE).
UTF-16LE UTF-16 that uses little endian byte order.
UTF-32 A fixed, four-byte Unicode encoding form
in which each UTF-32 code point is
precisely identical to the Unicode code
point.
UTF-32BE UTF-32 serialized as bytes in most-
significant-byte-first order (big endian).
UTF-32BE is structurally the same as
UCS-4.
UTF-32LE UTF-32 serialized as bytes in least-
significant-byte-first order (little endian).
Uniform Symbol Specification (USS). A series of bar
code symbology specifications published by AIM; currently
included are USS-Interleaved 2 of 5, USS-39, USS-93,
USS-Codabar, and USS-128.
uniformly spaced font. A font with graphic characters
having a uniform character increment. The distance
between reference points of adjacent graphic characters is
constant in the escapement direction. The blank space
between the graphic characters can vary. Synonymous
with monospaced font. Contrast with proportionally spaced
font and typographic font.
unit base. A one-byte code that represents the length of
the measurement base. For example, X'00' might specify
that the measurement base is ten inches.
Universal Product Code (UPC). A standard bar code
symbology, commonly used to mark the price of items in
stores, that can be read and interpreted by a computer.
type structure • Universal Product Code (UPC)

## Page 260

238 FOCA Reference
untoned. Unmarked portion of a physical medium.
Contrast with toned.
UP³I. Universal Printer Pre- and Post-Processing
Interface; an industry standard interface designed for use
in complex printing systems. A specification for this
interface can be obtained at www.afpcinc.org.
UPA. See user printable area.
UPC. See Universal Product Code.
uppercase. Pertaining to capital letters. Examples of
capital letters are A, B, and C. Contrast with lowercase.
upstream data. IPDS commands that exist in a logical
path from a specific point in a printer back to, but not
including, host presentation services.
usable area. An area on a physical medium that can be
used to present data. See also viewport.
user printable area (UPA). The portion of the physical
printable area to which user-generated data is restricted.
See also logical page, physical printable area, and valid
printable area.
USS. See Uniform Symbol Specification.
V
valid printable area (VPA). The intersection of a logical
page with the area of the medium presentation space in
which printing is allowed. If the logical page is a secure
overlay, the area in which printing is allowed is the physical
printable area. If the logical page is not a secure overlay
and if a user printable area is defined, the area in which
printing is allowed is the intersection of the physical
printable area with the user printable area. If a user
printable area is not defined, the area in which printing is
allowed is the physical printable area. See also logical
page, physical printable area, secure overlay, and user
printable area.
variable space. A method used to assign a character
increment dimension of varying size to space characters.
The space characters are used to distribute white space
within a text line. The white space is distributed by
expanding or contracting the dimension of the variable
space character's increment dependent upon the amount
of white space to be distributed. See also variable space
character and variable space character increment.
variable space character. The code point assigned by
the data stream for which the character increment varies
according to the semantics and pragmatics of the variable
space function. This code point is not presented, but its
character increment parameter is used to provide spacing.
See also variable space character increment.
variable space character increment. The variable value
associated with a variable space character. The variable
space character increment is used to calculate the
dimension from the current presentation position to a new
presentation position when a variable space character is
found. See also variable space character.
verifier. In bar code systems, a device that measures the
bars, spaces, quiet zones, and optical characteristics of a
bar code symbol to determine if the symbol meets the
requirements of a bar code symbology, specification, or
standard.
vertical bar code. A bar code pattern that presents the
axis of the symbol in its length dimension parallel to the Y
bc
axis of the bar code presentation space. Synonymous with
ladder bar code.
vertical font size. (1) A characteristic value,
perpendicular to the character baseline, that represents the
size of all graphic characters in a font. Synonymous with
font height. (2) In a font character set, nominal vertical font
size is a font-designer defined value corresponding to the
nominal distance between adjacent baselines when
character rotation is zero degrees and no external leading
is used. This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified vertical font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
vertical scale factor. In outline-font referencing, the
specified vertical adjustment of the Em square. The vertical
scale factor is specified in 1440ths of an inch. When the
horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also horizontal scale
factor.
viewing transform. A transform that is applied to model-
space coordinates. Contrast with model transform.
viewing window. That part of a model space that is
transformed, clipped, and moved into a graphics
presentation space.
viewport. The portion of a usable area that is mapped to
the graphics presentation space window . See also
graphics model space and graphics presentation space.
visibility. The property of a segment that declares
whether the part of a picture defined by the segment is to
be displayed or not displayed during the drawing process.
void. In bar codes, the undesirable absence of ink in a
bar code symbol bar element.
untoned • void

## Page 261

FOCA Reference 239
VPA. See valid printable area.
W
ward. A deprecated term for section.
weight class. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its weight class. Examples are light, medium,
and bold. Synonymous with type weight.
white space. The portion of a line that is not occupied by
characters when the characters of all the words that can be
placed on a line and the spaces between those words are
assembled or formatted on a line. When a line is justified,
the white space is distributed among the words,
characters, or both on the line in some specified manner.
See also controlled white space.
width class. A parameter indicating a relative change
from the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with type
width.
window. A predefined part of a graphics presentation
space. See also graphics presentation space window .
writing mode. An identified mode for the setting of text in
a writing system, usually corresponding to a nominal
escapement direction of the graphic characters in that
mode; for example, left-to-right, right-to-left, top-to-bottom.
X
Xbc extent. The size of a bar code presentation space in
the Xbc dimension. See also bar code presentation space.
Xbc,Ybc coordinate system. The bar code presentation
space coordinate system.
X-dimension. In bar codes, the nominal dimension of the
narrow bars and spaces in a bar code symbol.
Xg,Yg coordinate system. In the IPDS architecture, the
graphics presentation space coordinate system.
X-height. The nominal height above the baseline,
ignoring the ascender, of the lowercase characters in a
font. X-height is usually the height of the lowercase letter x.
See also lowercase and ascender.
X
io,Yio coordinate system. The IO-Image presentation
space coordinate system.
XML. See Extensible Markup Language.
XMP . See Extensible Metadata Platform.
Xm,Ym coordinate system. (1) In the IPDS architecture,
the medium presentation space coordinate system. (2) In
MO:DCA, the medium coordinate system.
Xoa,Yoa coordinate system. The object area coordinate
system.
X
ol,Yol coordinate system. The overlay coordinate
system.
X
p extent. The size of a presentation space or logical
page in the X p dimension. See also presentation space and
logical page.
X
p,Yp coordinate system. The coordinate system of a
presentation space or a logical page. This coordinate
system describes the size, position, and orientation of a
presentation space or a logical page. Orientation of an X p,
Yp coordinate system is relative to an environment-
specified coordinate system. An example of an
environment-specified coordinate system is the Xm,Ym
coordinate system. The X p,Yp coordinate system origin is
specified by an IPDS Logical Page Position command. See
also logical page, medium presentation space, and
presentation space.
Xpg,Ypg coordinate system. The coordinate system of a
page presentation space. This coordinate system
describes the size, position, and orientation of a page
presentation space. Orientation of an X pg,Ypg coordinate
system is relative to an environment specified coordinate
system, for example, an Xm,Ym coordinate system.
Y
Ybc extent. The size of a bar code presentation space in
the Ybc dimension. See also bar code presentation space.
Yp extent. The size of a presentation space or logical
page in the Y p dimension. See also presentation space and
logical page.
VPA • Y p extent

## Page 262

240 FOCA Reference

## Page 263

Copyright © AFP Consortium 1998, 2015 241
Index
A
A-Space
Character Increment parameter .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .94
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .38
kerning . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 38, 40, 76
minimum . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .83
A-space parameter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .91
absolute fidelity .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .29
AFP font resource .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 9
AFP System Font Resource .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
AFP system font structured-field and triplet summary . . . .. . . .. . . 191
AFPC. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . iv, 4
appearance fidelity . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
architectures
BCOCA. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
CMOCA . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
FOCA. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
GOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
IOCA. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
IPDS . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 2
MO:DCA . .. . . .. . .. . . .. . .. . . .. . viii, 2, 125–126, 128, 154, 174, 188
MOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
PTOCA . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
Ascender Height parameter .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .91
ascenders
height . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 41, 78
lowercase height, maximum .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .81
maximum height.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .44
Asian text . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .49
Average Weighted Escapement parameter. .. . .. . . .. . .. . . .. . . .. . .. .57
B
B-Space
Character Increment parameter .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .94
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .39
B-space parameter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .92
baseline
character . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
increment .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .74
maximum extent.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 45, 78
rotated .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
baseline extent . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .41
baseline offset
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
maximum. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .79
Baseline Offset parameter . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .92
BCF (Begin Coded Font) structured field.. . . .. . .. . . .. . .. . . .. . . .. . . 125
BCP (Begin Code Page) structured field .. . . .. . .. . . .. . .. . . .. . . .. . . 126
Begin Code Page (BCP) structured field .. . . .. . .. . . .. . .. . . .. . . .. . . 126
Begin Coded Font (BCF) structured field.. . . .. . .. . . .. . .. . . .. . . .. . . 125
Begin Font (BFN) structured field . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 128
BFN (Begin Font) structured field . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 128
bit numbering. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .56
bit string, explanation. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 120
body size.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .43
box size flag, Font Control structured field . . .. . .. . . .. . .. . . .. . . .. . . 148
box, character . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .36
byte numbering .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .56
C
C-Space
Character Increment parameter . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .94
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .39
kerning . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..39–40, 76
C-space parameter . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .94
cap-M height . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
Cap-M Height parameter. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .57
carriage control character. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 119
CFC (Coded Font Control) structured field.. . . .. . . .. . .. . . .. . .. . . .. 130
CFI (Coded Font Index) structured field .. . .. . . .. . . .. . .. . . .. . .. . . .. 131
character baseline . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 33, 37, 74
character box
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .36
height, maximum . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .79
rotated . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .48
width, maximum . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .80
Character Box Height parameter. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .93
character box size flag . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 148
Character Box Width parameter. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .93
character collection name. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
character concepts . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .36
character coordinate system . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
character escapement point . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
character identifier, definition of . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
character increment .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .40
Character Increment parameter . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .94
character metrics .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .11
character pitch . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
character reference point . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 33, 37
character rotation .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
Character Rotation parameter.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .58
character rotations . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
character string . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 120
character string parameter value. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
character width . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
character-mapping information. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
character-mapping parameters . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..10, 103
character-metric parameters . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 10, 91
character-shape information. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
character-shape parameters . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 10, 96
clear-text parameters. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
code page
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
in AFP font resource . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 9
Code Page
Begin Code Page (BCP) structured field . . . .. . . .. . .. . . .. . 113, 126
Code Page Control (CPC) structured field . .. . . .. . .. . . .. . 113, 134
Code Page Descriptor (CPD) structured field . . .. . .. . . .. . 113, 138
Code Page Index (CPI) structured field. .. . . .. . . .. . .. . . .. . 113, 140
Code Page object .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
End Code Page (ECP) structured field . .. . . .. . . .. . .. . . .. . 113, 144
No Operation (NOP) structured field . .. . .. . . .. . . .. . .. . . .. . 113, 175
Code Page Control (CPC) structured field .. . . .. . . .. . .. . . .. . .. . . .. 134
Code Page Description parameter. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
Code Page Descriptor (CPD) structured field. .. . . .. . .. . . .. . .. . . .. 138
Code Page Global Identifier parameter .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
Code Page Index (CPI) structured field .. . .. . . .. . . .. . .. . . .. . .. . . .. 140
code parameter value . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
code point
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
variable-space character .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 108
Code Point parameter . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
code, explanation .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 120
Coded Font
Begin Coded Font (BCF) structured field . . . .. . . .. . .. . . .. . .111, 125
Coded Font Control (CFC) structured field . .. . . .. . .. . . .. . .111, 130
Coded Font Index (CFI) structured field .. . . .. . . .. . .. . . .. . .111, 131

## Page 264

242 FOCA Reference
Coded Font object . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
End Coded Font (ECF) structured field . . . .. . .. . . .. . .. . . .. .111, 143
No Operation (NOP) structured field . . .. . . .. . .. . . .. . .. . . .. .111, 175
Coded Font Control (CFC) structured field. . .. . .. . . .. . .. . . .. . . .. . . 130
Coded Font Index (CFI) structured field . .. . . .. . .. . . .. . .. . . .. . . .. . . 131
codes
encoding scheme . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 104
font use. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .61
weight class. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .72
width class . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .73
Comment parameter . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
concepts
characters.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .36
font. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .43
content fidelity . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
coordinate system .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .33
CPC (Code Page Control) structured field . . .. . .. . . .. . .. . . .. . . .. . . 134
CPD (Code Page Descriptor) structured field. . .. . . .. . .. . . .. . . .. . . 138
CPGID . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
CPI (Code Page Index) structured field . .. . . .. . .. . . .. . .. . . .. . . .. . . 140
CRC Resource Management triplet. . . .. . .. . . .. . .. . . .. . 126, 128, 180
D
data
availability .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
count. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .99
ownership .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
pattern .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 98–99
structure. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
type .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
Default Baseline Increment parameter . . .. . . .. . .. . . .. . .. . . .. . . .. . .. .74
default variable space increment. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .85
depth
descender.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 41, 95
lowercase descender, maximum . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .82
maximum descender . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .45
Descender Depth parameter . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
descenders
depth . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .41
lowercase depth, maximum . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .82
maximum depth .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 45, 81
Design Class parameters . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
General Class . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
Specific Group. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Subclass . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Design General Class parameter . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
Design Resolution X parameter . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96
Design Resolution Y parameter . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96
design size. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
Design Specific Group parameter. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Design Subclass parameter .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
designer, font . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
digital processing . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
digitized font resource .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .9, 13
document
fidelity. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
final-form . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 189
output. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .14
presentation . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 190
revisable . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 189
document editing
font selection .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .29
document formatting
font selection .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .30
font substitution. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .30
document presentation
font selection .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .31
font substitution. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .31
E
ECF (End Coded Font) structured field. .. . .. . . .. . . .. . .. . . .. . .. . . .. 143
ECP (End Code Page) structured field . .. . .. . . .. . . .. . .. . . .. . .. . . .. 144
editing . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 14–15
editor
determination of font availability . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .19
revisable document data stream . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 189
text processing system . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .14
WYSIWYG . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .14
EFN (End Font) structured field . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 145
em-height . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
Em-Space Increment parameter . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .59
Encoding Scheme parameter .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 104
End Code Page (ECP) structured field . .. . .. . . .. . . .. . .. . . .. . .. . . .. 144
End Coded Font (ECF) structured field. .. . .. . . .. . . .. . .. . . .. . .. . . .. 143
End Font (EFN) structured field . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 145
escapement
average weighted .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .57
direction . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
escapement point .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
Extension Font parameter . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Extension Font triplet . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 151, 185
external leading . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .44
External Leading parameter . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .75
F
Family Name parameter . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
fidelity
absolute . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .29
appearance .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
content . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
document . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
document editing . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .29
document formatting . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .30
document presentation . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .31
format.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
layout .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
Figure Space Increment parameter. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .75
final-form document .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 189
fixed measurement units . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .64
fixed-format parameters.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
fixed-metric to relative-metric conversion . .. . . .. . . .. . .. . . .. . .. . . .. 149
flag parameter types
Hollow Font .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .62
Invalid Coded Character. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 106
Italics .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .62
Kerning . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .76
Kerning Pair Data .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .62
No Increment . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 106
No Presentation . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 107
Overstruck Font . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .68
Underscored Font .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform A-space . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Baseline Offset . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Character Box Font . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform Character Increment . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .90
flag parameter value.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
FNC (Font Control) structured field . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 146
FND (Font Descriptor) structured field. . .. . .. . . .. . . .. . .. . . .. . .. . . .. 152
FNG (Font Patterns) structured field.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 156
FNI (Font Index) structured field . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 160
FNM (Font Patterns Map) structured field. .. . . .. . . .. . .. . . .. . .. . . .. 164
FNN (Font Name Map) structured field . .. . .. . . .. . . .. . .. . . .. . .. . . .. 165
FNO (Font Orientation) structured field. .. . .. . . .. . . .. . .. . . .. . .. . . .. 168
FNP (Font Position) structured field .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 172
FOCA
overview. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33

## Page 265

FOCA Reference 243
parameters. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
font
character-mapping information . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
character-shape information .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
concepts . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .43
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
designer . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
digital processing. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
digitized resource . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
Family Name parameter. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
font-descriptive information . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
font-metric information.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
horizontal size . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
identifying .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .22
information . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
library, definition of . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
model . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
monospaced . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
parameter groups . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .10
processing . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .14
processing summary. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .16
production.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
proportionally spaced . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
reference . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .9, 17
resource. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 8–9
scaling .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 63–64
selection. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
storage . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
substitution. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
typographic . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
vertical size . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
vertical subscript size . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .85
Font Character Set
Begin Font (BFN) structured field . . .. . .. . . .. . .. . . .. . .. . . .. 114, 128
End Font (EFN) structured field. .. . . .. . .. . . .. . .. . . .. . .. . . .. 114, 145
Font Character Set object .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
Font Control (FNC) structured field. .. . .. . . .. . .. . . .. . .. . . .. 114, 146
Font Descriptor (FND) structured field .. . . .. . .. . . .. . .. . . .. 114, 152
Font Index (FNI) structured field .. . . .. . .. . . .. . .. . . .. . .. . . .. 114, 160
Font Name Map (FNN) structured field . . . .. . .. . . .. . .. . . .. 114, 165
Font Orientation (FNO) structured field . . . .. . .. . . .. . .. . . .. 114, 168
Font Patterns (FNG) structured field . . .. . . .. . .. . . .. . .. . . .. 114, 156
Font Patterns Map (FNM) structured field .. . .. . . .. . .. . . .. 114, 164
Font Position (FNP) structured field .. . .. . . .. . .. . . .. . .. . . .. 114, 172
No Operation (NOP) structured field . . .. . . .. . .. . . .. . .. . . .. 114, 175
Font Control (FNC) structured field . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 146
Font Descriptor (FND) structured field.. . .. . . .. . .. . . .. . .. . . .. . . .. . . 152
font family name . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
Font Index (FNI) structured field . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 160
font library, definition of. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
Font Local Identifier parameter.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
font metrics . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
Font Name Map (FNN) structured field . . .. . . .. . .. . . .. . .. . . .. . . .. . . 165
Font Orientation (FNO) structured field. . .. . . .. . .. . . .. . .. . . .. . . .. . . 168
font parameter groups.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .10
Font Patterns (FNG) structured field. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 156
Font Patterns Map (FNM) structured field. . . .. . .. . . .. . .. . . .. . . .. . . 164
Font Position (FNP) structured field . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 172
font processing . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 14, 16
font production . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
font reference . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 9–10, 17
Font Reference Model.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
font resource . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .8, 10
record components .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
Font Resource Management triplet . . . .. . .. . . .. . .. . . .. . 126, 128, 181
font selection . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
font services .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .19, 189
font storage . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .13
font substitution .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
Font Typeface Global Identifier parameter . . .. . .. . . .. . .. . . .. . . .. . .. .61
Font Use Code parameter . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
font-description parameters . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 10, 56
font-descriptive information .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .11
font-metric Information . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .11
font-metric parameters . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 10, 74
format fidelity . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
format, parameters . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
formatter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..20, 189
formatting . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 14–15
Fully Qualified Name triplet .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 177
G
GCSGID . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
global identifier . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
Global Resource Identifier (GRID) . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
Graphic Character Global Identifier parameter . . . .. . .. . . .. . .. . . .. . .95
Graphic Character Identifier Length parameter. . . .. . .. . . .. . .. . . .. 106
Graphic Character Identifier Type parameter . .. . . .. . .. . . .. . .. . . .. 105
Graphic Character Set Global Identifier parameter . .. . . .. . .. . . .. . .61
graphic characters . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
GRID (Global Resource Identifier) . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
H
Hebrew text.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .51
height
ascender .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 78, 91
cap-M.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 43, 57
character box . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .93
lowercase ascender, maximum.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .81
maximum ascender . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .44
superscript . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .86
X . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
Hollow Font parameter . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .62
horizontal font size. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 43, 67
horizontal size scaling . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .65
I
identifier numbers for structured fields . .. . .. . . .. . . .. . .. . . .. . .. . . .. 122
identifiers
character .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
code page global . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
registered . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .95
set name .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
Unspecified Coded Character . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 109
identifying font resources . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
increment . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .40
maximum.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .80
nominal . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .84
space character . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .85
Intelligent Printer Data Stream (IPDS). . .. . .. . iii, viii, 2, 22, 135, 188
interchange .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 111
interchange formats .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 111
internal leading . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .44
Internal Leading parameter .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .76
Introducer, structured field
description . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 121
extension flag . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 123
padding flag.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 123
sequence number .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 123
Invalid Coded Character parameter .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 106
IPDS. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
IPDS (Intelligent Printer Data Stream). . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
IPDS Load Font Equivalence . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
IPDS Load Font Format .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188

## Page 266

244 FOCA Reference
ISO coordinate system . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .52
ISO Font Architecture .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .51
ISO naming . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .53
Italics parameter . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
K
Kanji text .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .49
kerning
A-space . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .38
C-space . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .39
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .40
overlap . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .40
pair. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .40
Kerning Pair Character 1 parameter . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
Kerning Pair Character 2 parameter . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .77
Kerning Pair Data parameter . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Kerning Pair X-Adjust parameter. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .77
Kerning parameter .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
L
layout fidelity . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
leading
external. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 44, 75
internal . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 44, 76
LID.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
Linkage Code . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
Local Date and Time Stamp triplet .. . . .. . .. . . .. . .. . . .. . 126, 128, 178
local ID . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
logical page. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .33
M
magnetic ink character recognition font bit . . .. . .. . . .. . .. . . .. . . .. . . 147
Map Coded Font . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .22
mapping
character . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
character-mapping parameters. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .10
code page.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .9–10
parameters. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
mapping of ISO parameters .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 193
maximum ascender height. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .44
Maximum Ascender Height parameter . . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
maximum baseline extent. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .45
Maximum Baseline Extent parameter .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
Maximum Baseline Offset parameter . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .79
Maximum Character Box Height parameter .. . .. . . .. . .. . . .. . . .. . .. .79
Maximum Character Box Width parameter . .. . .. . . .. . .. . . .. . . .. . .. .80
Maximum Character Increment parameter. . .. . .. . . .. . .. . . .. . . .. . .. .80
maximum descender depth . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .45
Maximum Descender Depth parameter . .. . . .. . .. . . .. . .. . . .. . . .. . .. .81
Maximum Horizontal Font Size parameter . . .. . .. . . .. . .. . . .. . . .. . .. .63
Maximum Lowercase Ascender Height parameter.. . .. . . .. . . .. . .. .81
Maximum Lowercase Descender Depth parameter. . .. . . .. . . .. . .. .82
Maximum V(y). . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .82
Maximum Vertical Font Size parameter . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
Maximum W(y) . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .83
MCF/2 Font Reference Format.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
measurement base . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
Measurement Units parameter .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
measurements . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .34
metadata.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
Metric Adjustment triplet .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 130, 186
metrics
character . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
font. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
MICR bit. . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 147
MICR Font parameter . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .65
Minimum A-space parameter. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
Minimum Horizontal Font Size parameter. .. . . .. . . .. . .. . . .. . .. . . .. . .65
Minimum Vertical Font Size parameter . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
MO:DCA, and Mixed Object Document Content
Architecture (MO:DCA) . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 14, 22, 188
MO:DCA .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
model, font reference
editor .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .19
font services . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .19
formatter .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .20
general flow .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
presentation services .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .21
user input . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
monospaced fonts . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
N
National Language Support . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..47, 190
No Increment parameter . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 106
No Operation (NOP) structured field.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 175
No Presentation parameter .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 107
Nominal Character Increment parameter . .. . . .. . . .. . .. . . .. . .. . . .. . .84
Nominal Character Slope parameter . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .67
Nominal Horizontal Font Size parameter. . .. . . .. . . .. . .. . . .. . .. . . .. . .67
Nominal Vertical Font Size parameters. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .68
NOP (No Operation) structured field.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 175
notation conventions, structured field descriptions. . .. . . .. . .. . . .. 119
notices . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 203
Number of Coded Graphic Characters Assigned parameter. . .. 107
number parameter value . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
numbering, byte and bit .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
O
Object Origin Identifier triplet . .. . . .. . .. . . .. . .. . . .. . . .. . . 126, 128, 183
Object Type.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
objects . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 111
offset .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 100
offset, baseline . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .42
operating system. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 111
origin .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
orthogonal coordinate system .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
output document . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .14
overscore
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
recommendations .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
Overstruck Font parameter .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .68
overview, FOCA. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
P
padding flag, structured field introducer .. . .. . . .. . . .. . .. . . .. . .. . . .. 123
padding, length values . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 124
pair kerning
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .40
parameters
A-space . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .91
Ascender Height . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .91
Average Weighted Escapement . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .57
B-space . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .92
Baseline Offset .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .92
byte and bit numbering . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
C-space . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .94
Cap-M Height . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .57

## Page 267

FOCA Reference 245
Character Box Height . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .93
Character Box Width. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .93
Character Increment parameter .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .94
Character Rotation . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
Code Page Description . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
Code Page Global Identifier. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
Code Point . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
Comment. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
Default Baseline Increment . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .74
Descender Depth . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
Design General Class .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .58
Design Resolution X . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96
Design Resolution Y . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96
Design Specific Group.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Design Subclass . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Em-Space Increment . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .59
Encoding Scheme. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 104
Extension Font . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
External Leading Increment. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .75
Family Name. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
Figure Space Increment . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .75
FOCA. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
Font Local Identifier .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .60
Font Typeface Global Identifier . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .61
Font Use Code . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .61
formats . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
Graphic Character GID Length . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 106
Graphic Character Global Identifier .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
Graphic Character Identifier Type . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
Graphic Character Set Global Identifier . . .. . .. . . .. . .. . . .. . . .. . .. .61
groups .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .10
Hollow Font . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Internal Leading .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
Invalid Coded Character. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 106
Italics . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Kerning . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
Kerning Pair Character 1 . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
Kerning Pair Character 2 . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .77
Kerning Pair Data . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Kerning Pair X-Adjust . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .77
Linkage Code .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
Maximum Ascender Height . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
Maximum Baseline Extent . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
Maximum Baseline Offset .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .79
Maximum Character Box Height.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .79
Maximum Character Box Width .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .80
Maximum Character Increment. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .80
Maximum Descender Depth .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .81
Maximum Horizontal Font Size . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
Maximum Lowercase Ascender Height. . . .. . .. . . .. . .. . . .. . . .. . .. .81
Maximum Lowercase Descender Depth. . .. . .. . . .. . .. . . .. . . .. . .. .82
Maximum V(y) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .82
Maximum Vertical Font Size .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
Maximum W(y) . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .83
Measurement Units .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
MICR Font . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .65
Minimum A-space . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .83
Minimum Horizontal Font Size . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .65
Minimum Vertical Font Size . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
No Increment .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 106
No Presentation .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 107
Nominal Character Increment . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .84
Nominal Character Slope .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
Nominal Horizontal Font Size . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
Nominal Vertical Font Size . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
Number of Coded Graphic Characters Assigned . . .. . . .. . . .. . . 107
Object Type . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
Overstruck Font .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
Pattern Data . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
Pattern Data Alignment Code. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
Pattern Data Alignment Value . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .99
Pattern Data Count . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .99
Pattern Data Offset . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 100
Pattern T echnology Identifier . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 100
Precedence Code .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 101
Private Use .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
Proportional Spaced . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
Resource Name . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
Section Number . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 107
self-identifying . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
Shape Pattern Index . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 101
Space Character Code Point . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 108
Space Character Increment.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .85
Space Character Section Number . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 108
Specified Horizontal Font Size. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
Specified Horizontal Scale Factor .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
Specified Vertical Font Size .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
Subscript Vertical Font Size .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .85
Subscript X-Axis Offset . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .86
Superscript Vertical Font Size . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .86
Superscript X-Axis Offset.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .87
Throughscore Position. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .87
Throughscore Width . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
Transformable Font . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
Typeface Name. . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
types . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
Underscore Position . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
Underscore Width .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Underscored Font .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform A-space . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Baseline Offset . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Character Box Font . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform Character Increment . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .90
Unspecified Coded Character Identifier .. . . .. . . .. . .. . . .. . .. . . .. 109
Weight Class. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Width Class .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
Writing Direction Code. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 102
X-Height . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .74
Pattern Data Alignment Code parameter. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
Pattern Data Alignment Value parameter . .. . . .. . . .. . .. . . .. . .. . . .. . .99
Pattern Data Count parameter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .99
Pattern Data Offset parameter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 100
Pattern Data parameter .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
pattern index . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 101
Pattern T echnology Identifier parameter . . .. . . .. . . .. . .. . . .. . .. . . .. 100
pattern technology information . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 201
pels . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .7, 37
physical page. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
point size. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .43
Precedence Code. . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 101
presentation document. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 190
presentation services. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..21, 190
presentation technologies . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
presenting . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 14, 16
Primary Graphic Character Set Global Identifier parameter . . .. . .61
Private Use parameter . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
production, font . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .13
programming interface . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 189
Proportional Spaced parameter . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
proportionally spaced fonts .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
R
recommendations
overscores . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
subscript .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
superscript . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
throughscores . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
underscores . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46

## Page 268

246 FOCA Reference
reference point . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
registered identifier . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
registry number .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .61
relative measurement units . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
relative-metric to fixed-metric conversion . . . .. . .. . . .. . .. . . .. . . .. . . 149
Remote PrintManager .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 126, 128
repeating group
description . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
representation technologies .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 10, 12
requirements
National Language Support . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .47, 190
Resource Name parameter . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
resource objects
code page.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 113
coded fonts . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
font character set. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
resources
digitized font . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .9, 13
font-descriptive information . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
font, device-level . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 9
font, system-level . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 9
revisable document. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 189
rotated baseline .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
rotated character boxes . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
rotation . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 10, 47, 58
S
SAA Font Data Access . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
SAA Font Reference Creation. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
scaling
horizontal font size . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .65
horizontal size . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
vertical font size .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
vertical size . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
scaling fonts .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 63–64
score position . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .87
Section Number parameter . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 107
self-identifying parameters. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
Set Variable Space Character Increment control sequence. .. . . 136
SFG Font Reference Format . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
Shape Pattern Index parameter . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 101
signed binary . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 120
slope
definition of. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
degrees . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
MICR font .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .65
nominal character . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
Space Character Code Point parameter .. . . .. . .. . . .. . .. . . .. . . .. . . 108
Space Character Increment parameter. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .85
Space Character Section Number parameter . .. . . .. . .. . . .. . . .. . . 108
Specified Horizontal Font Size parameter . . .. . .. . . .. . .. . . .. . . .. . .. .70
Specified Horizontal Scale Factor parameter . . .. . . .. . .. . . .. . . .. . .. .70
Specified Vertical Font Size parameter. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
standard font . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .51
stem incline . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
structured fields
Begin Code Page (BCP) . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 126
Begin Coded Font (BCF) . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 125
Begin Font (BFN). . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 128
Code Page Control (CPC). . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
Code Page Descriptor (CPD) . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 138
Code Page Index (CPI) . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 140
Coded Font Control (CFC) . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 130
Coded Font Index (CFI) . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 131
description of .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
End Code Page (ECP). . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 144
End Coded Font (ECF) . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 143
End Font (EFN). .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 145
Font Control (FNC). . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 146
Font Descriptor (FND) . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 152
Font Index (FNI) . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 160
Font Name Map (FNN) . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 165
Font Orientation (FNO) . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 168
Font Patterns (FNG) . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 156
Font Patterns Map (FNM) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 164
Font Position (FNP) . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 172
introducer . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 121
No Operation (NOP) . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 175
notation conventions. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 119
optional parameters, position of . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 119
repeating groups . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 119
sequence number .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 123
subscript . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
Subscript Vertical Font Size parameter. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .85
Subscript X-Axis Offset parameter. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .86
superscript . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
Superscript Vertical Font Size parameter . .. . . .. . . .. . .. . . .. . .. . . .. . .86
Superscript X-Axis Offset parameter . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .87
syntax . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
T
text processing system . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .14
throughscore
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
position . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .87
recommendations .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
width . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
Throughscore Position parameter . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .87
Throughscore Width parameter . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
trademarks .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . iv, 204
transform of character shapes.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .96
Transformable Font parameter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
transforms . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .51
triplets
CRC Resource Management (X'63'-type 1) .. . . .. . . 126, 128, 180
Extension Font (X'6D') . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 151, 185
Font Resource Management (X'63'-type 2) .. . . .. . . 126, 128, 181
Fully Qualified Name (X'02') . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 177
Local Date and Time Stamp (X'62') . . .. . .. . . .. . . .. . . 126, 128, 178
Metric Adjustment (X'79').. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 130, 186
Object Origin Identifier (X'64'). . .. . .. . . .. . .. . . .. . . .. . . 126, 128, 183
User Comment (X'65') . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 127, 129, 184
Triplets . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
Typeface Name parameter .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
typographic fonts. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
U
undefined parameter value .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
underscore
definition of. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
position . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
recommendations .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
width . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
underscore position. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
Underscore Position parameter . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
underscore width. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Underscore Width parameter. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Underscored Font parameter. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform A-space parameter . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Baseline Offset parameter . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Uniform Character Box Font parameter .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
Uniform Character Increment parameter. . .. . . .. . . .. . .. . . .. . .. . . .. . .90
Unit base. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .64
Unit-Base . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .35

## Page 269

FOCA Reference 247
Unit-Em . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 64
units . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
units of direction.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .36
units of measure . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 64
units per unit-base .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
unsigned binary .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 120
Unspecified Coded Character Identifier parameter . . .. . . .. . . .. . . 109
UP³I . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .ix, 238
User Comment triplet . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 127, 129, 184
user input . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
user intent . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
V
variable space increment . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 136, 154
variable-space character code point . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 108
vertical font size .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .68
vertical size . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .43
vertical size scaling . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
W
Weight Class parameter .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .72
Width Class parameter . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .73
writing direction .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 102
Writing Direction Code parameter .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 102
writing modes . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .47
WYSIWYG. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .14
X
X-height . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .43
X-Height parameter. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .74
X-resolution. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96
Y
Y-resolution . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .96

## Page 270

Advanced Function Presentation Consortium
Font Object Content Architecture
Reference
AFPC-0007-06
