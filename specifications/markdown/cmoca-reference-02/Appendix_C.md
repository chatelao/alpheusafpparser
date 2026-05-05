Appendix C. Compliance With Color Management Object
Content Architecture
In order to claim that the baseline Color Management Object Content Architecture is supported, a device is
required to support certain CMRs as indicated in the following table.
Table 52. CMR Architecture Compliance Requirements
CMR Type Required for Baseline Support?
Halftone No
T one Transfer Curve No
Color Conversion Yes
Link Color Conversion No
Indexed No
In order to claim that the baseline Color Management Object Content Architecture is supported, a device is
required to support generic CMRs as follows, regardless of whether it reports support for these CMR types in
the IPDS STM reply:
• It must support all of the Registered Generic Halftone CMRs shown in Figure 12 on page 121.
• It must support all of the Registered Generic T one Transfer Curve CMRs shown in Figure 13 on page 122.
In order to claim that the baseline Color Management Object Content Architecture is supported, a device is
required to supply device defaults as follows, regardless of what downloaded CMRs it supports:
• The device must supply the following default instruction CMRs:
– An instruction Halftone CMR that takes the device color space as input.
– An instruction T one Transfer Curve CMR.
– An instruction Color Conversion CMR from an ICC Profile Connection Space (CIEXYZ or CIELAB) to the
device color space. The device must have the ability to convert between CIEXYZ and CIELAB or else it
must supply color conversions into the device output space from both.
– There is no default Indexed CMR.
• The device must supply the following default audit CMRs:
– An audit Color Conversion CMR from input RGB to CIEXYZ or CIELAB.
◦ A display may assume that the RGB is its device RGB.
– An audit Color Conversion CMR from input CMYK to CIEXYZ or CIELAB.
◦ A full-color printer may assume that the CMYK is its device CMYK.
– An audit Color Conversion CMR from input grayscale to CIEXYZ or CIELAB.
◦ A monochrome printer may assume that the grayscale is its device grayscale.
– An audit Color Conversion CMR from YCrCb or YCbCr to CIEXYZ or CIELAB.
– Audit T one Transfer Curve CMRs for all possible number of components.
◦ Since the default audit T one Transfer Curve is the identity, it may be implicitly implemented by doing
nothing.

## Page 142

126 CMOCA Reference
– No default audit Halftone CMR is required since audit Halftone CMRs are ignored.
– No default audit Indexed CMR is required since audit Indexed CMRs are ignored.
• A device must be able to handle an input color space that is specified as CIELAB or CIEXYZ (i.e., a profile
connection space). No audit CMR is required for CIELAB or CIEXYZ since an instruction CMR can be used
directly.
Compliance

## Page 143

Copyright © AFP Consortium 2006, 2025 127
Notices
The AFP Consortium or consortium member companies might have patents or pending patent applications
covering subject matter described in this document. The furnishing of this document does not give you any
license to these patents.
The following statement does not apply to the United Kingdom or any other country where such
provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICATION “AS IS”
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

## Page 144

128 CMOCA Reference
Trademarks
These terms are trademarks or registered trademarks of Adobe Systems Incorporated in the United States,
other countries, or both:
Acrobat
Adobe
Photoshop
Postscript
These terms are trademarks of the AFP Consortium:
AFPC
AFP Consortium
These terms are trademarks or registered trademarks of Hewlett-Packard Company in the United States, other
countries, or both:
Hewlett-Packard
PCL
These terms are registered trademarks of the International Business Machines Corporation in the United
States, other countries, or both:
IBM
PANTONE is a registered trademark of Pantone LLC.
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
InfoPrint
Intelligent Printer Data Stream
IPDS
Mixed Object Document Content Architecture
MO:DCA
Ricoh
Other company, product, or service names might be trademarks or service marks of others.

## Page 145

Copyright © AFP Consortium 2006, 2025 129
Glossary
This glossary contains terms that apply to the
CMOCA architecture and also terms that apply to
other related presentation architectures.
Note: Only changes having to do with newly-added
CMOCA functionality or clarifications in this
edition are marked in color with a colored
revision bar to the left. All other changes—
terms or definitions that have been added,
deleted, or reworded—are not marked.
If you do not find the term that you are looking for,
please refer to the IBM Dictionary of Computing,
document number ZC20-1699 or the InfoPrint
Dictionary of Printing.
The following definitions are provided as supporting
information only, and are not intended to be used as
a substitute for the semantics described in the body
of this reference.
A
abstract profile. An ICC profile that represents abstract
transforms and does not represent any device model.
Color transformations using abstract profiles are performed
from PCS to PCS. Abstract profiles cannot be embedded in
images.
additive primary colors. Red, green, and blue light,
transmitted in video monitors and televisions. When used
in various degrees of intensity and variation, they create all
other colors of light; when superimposed equally, they
create white. Contrast with subtractive primary colors.
addressable position. A position in a presentation space
or on a physical medium that can be identified by a
coordinate from the coordinate system of the presentation
space or physical medium. See also pixel.
Advanced Function Presentation (AFP). An open
architecture for the management of presentable
information that is developed by the AFP Consortium
(AFPC). AFP comprises a number of data stream and data
object architectures:
• Mixed Object Document Content Architecture (MO:DCA);
formerly referred to as AFPDS
• Intelligent Printer Data Stream (IPDS)
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
AFP . See Advanced Function Presentation.
AFP Consortium (AFPC). A formal open standards body
that develops and maintains AFP architecture. Information
about the consortium can be found at
www.afpconsortium.org.
AFP GOCA. A subset of the GOCA architecture, originally
defined by IBM, specifically designed for AFP
environments. See Graphics Object Content Architecture
(GOCA).
application. (1) The use to which an information system
is put. (2) A collection of software components used to
perform specific types of work on a computer.
architected. Identifies data that is defined and controlled
by an architecture. Contrast with unarchitected.
array. A structure that contains an ordered group of data
elements. All elements in an array have the same data
type.
ASCII. Acronym for American Standard Code for
Information Interchange. A standard code used for
information exchange among data processing systems,
data communication systems, and associated equipment.
ASCII uses a coded character set consisting of 7-bit coded
characters.
attribute. A property or characteristic of one or more
constructs.
audit CMR. A color management resource that reflects
processing that has been done on an object.
B
base support level. Within the base-and-towers concept,
the smallest portion of architected function that is allowed
to be implemented. This is represented by a base with no
towers.
big endian. A format for storage or transmission of binary
data in which the most significant bit (or byte) is placed
first. Contrast with little endian.
bilevel. Having two levels; for example, every point in a
bilevel image has the value 1 or 0, representing a colored
image point or empty space. Contrast with multilevel.

## Page 146

130 CMOCA Reference
bilevel device. A device that is used in a manner that
permits it to process two-level color data. Contrast with
multilevel device.
BITS. A data type for architecture syntax, indicating one
or more bytes to be interpreted as bit string information.
brightness. Attribute of a visual sensation according to
which an area appears to exhibit more or less light.
BYTE. A data type for architecture syntax consisting of 8
bits and indicating that each byte has no predefined
interpretation. Therefore, in CMOCA, each byte is
interpreted as defined in the tag explanation.
C
calibration. T o adjust the correct value of a reading by
comparison to a standard.
Cartesian coordinate system. In a plane, an image
coordinate system that has positive values for the X and Y
axis in the top-right quadrant. The origin is the upper left-
hand corner of the bottom-right quadrant. A pair of (x,y)
values corresponds to one image point. Each image point
is described by an image data element.
CHAR. A data type for architecture syntax, indicating one
or more bytes to be interpreted as character information.
character. A member of a set of elements used for the
organization, control, or representation of data. A character
can be either a graphic character or a control character.
CIE. See Commission Internationale d'Éclairage.
CIELAB color space. Internationally accepted color
space model used as a standard to define color within the
graphic arts industry, as well as other industries. L*, a*, and
b* are plotted at right angles to one another. Equal
distances in the space represent approximately equal color
difference.
CIEXYZ color space. The fundamental CIE-based color
space that allows colors to be expressed as a mixture of
the three tristimulus values X, Y , and Z.
cluster-dot screening. A halftone method that uses
multiple pixels that vary from small to large dots as the
color gets darker. It is characterized by a polka-dot look.
CMOCA. See Color Management Object Content
Architecture.
CMR. See color management resource.
CMY . Cyan, magenta, and yellow, the subtractive primary
colors.
CMYK color space. The color model used in four-color
printing. Cyan, magenta, and yellow, the subtractive
primary colors, are used with black to effectively create a
multitude of other colors.
CODE. A data type for architecture syntax that indicates
an architected constant to be interpreted as defined by the
architecture.
color. A visual attribute of things that results from the light
they emit, transmit, or reflect.
colorants. Colors (pigments, dyes, inks) used by a
device, primarily a printer, to reproduce colors.
color calibration. The process of altering the behavior of
an input or output device to make it conform to an
established state, specified by a manufacturer, user,
industry specification, or standard.
color component. A dimension of a color value
expressed as a numeric value. For example, a color value
might consist of one, two, three, four, or eight components,
also referred to as channels.
color conversion. The process of converting colors from
one color space to another.
colorimetric intent. A gamut mapping method that is
intended to preserve the relationships between in-gamut
colors at the expense of out-of-gamut colors.
colorimetry. The science of measuring color and color
appearance. Classical colorimetry deals primarily with
color matches rather than with color appearance as such.
The main focus of colorimetry has been the development
of methods for predicting perceptual matches on the basis
of physical measurements.
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
of three intensities for red (R), green (G), and blue (B). Also
referred to as color space.
bilevel device • color model

## Page 147

CMOCA Reference 131
color palette. A system of designated colors that are
used in conjunction with each other to achieve visual
consistency.
Color Rendering Dictionary. A PostScript language
construct for converting colors from the CIEXYZ color
space to the device color space. It is analogous to the
“from PCS” part of an ICC printer profile with one rendering
intent; that is, the part used when the profile is a
destination profile.
color space. The method by which a color is specified.
For example, the RGB color space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color model.
ColorSpace conversion profile. An ICC profile that
provides the relevant information to perform a color space
transformation between the non-device color spaces and
the Profile Connection Space. It does not represent any
device model. ColorSpace conversion profiles can be
embedded in images.
column. A subarray consisting of all elements that have
an identical position within the low dimension of a regular
two-dimensional array.
Commission Internationale d'Éclairage (CIE). An
association of international color scientists who produced
the standards that are used as the basis of the description
of color.
construct. An architected set of data such as a structured
field or a triplet.
coordinate system. A Cartesian coordinate system. An
example is the image coordinate system that uses the
fourth quadrant with positive values for the Y axis. The
origin is the upper left-hand corner of the fourth quadrant.
A pair of (x,y) values corresponds to one image point. Each
image point is described by an image data element.
D
data object. An object that conveys information, such as
text, graphics, audio, or video.
data stream. A continuous stream of data that has a
defined format. An example of a defined format is a
structured field.
default. A value, attribute, or option that is assumed when
none has been specified and one is needed to continue
processing.
device attribute. A property or characteristic of a device.
device dependent. Dependent upon one or more device
characteristics.
device independent. Not dependent upon device
characteristics.
device-independent color space. A CIE-based color
space that allows color to be expressed in a device-
independent way. It ensures colors to be predictably and
accurately matched among various color devices.
device profile. A structure that provides a means of
defining the color characteristics of a given device in a
particular state.
dimension. The attribute of size given to arrays and
tables.
direction. In GOCA, an attribute that controls the
direction in which a character string grows relative to the
inline direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top.
dispersed-dot halftone. Any halftone algorithm that
turns on binary pixels individually without grouping them
into clusters. The “smallest available” dots are scattered in
a pseudorandom manner to print varying densities.
Commonly contrasted with cluster-dot screening.
dither. An intentional form of noise added to an image to
randomize quantization error. Dithering an image can
prevent unwanted patterns from appearing within the
image.
document. (1) A machine-readable collection of one or
more objects that represents a composition, a work, or a
collection of data. (2) A publication or other written
material.
document component. An architected part of a
document data stream. Examples of document
components are documents, pages, page groups, indexes,
resource groups, objects, and process elements.
dot gain. The phenomenon that occurs when ink is
transferred from the plate to the blanket of the press and
finally to the paper on which it is being printed. A dot for a
halftone or a screen gets larger because of the mechanical
process of transferring ink.
dots per inch. (1) The number of dots that will fit in an
inch. (2) A unit of measure for output resolution. (3) Dots
per inch (dpi) is also used to measure the quality of input
when using a scanner. In this case, dpi becomes a square
function measuring the dots both vertically as well as
horizontally. Consequently, when an image is scanned in at
300 dpi, there are 90,000 dots or bits of electronic data
(300 x 300) in every square inch.
color palette • dots per inch

## Page 148

132 CMOCA Reference
dpi. See dots per inch.
E
embedded ICC profile. ICC profiles that are embedded
within graphic documents and images. An embedded ICC
profile allows users to transparently move color data
between different computers, networks and even operating
systems without having to worry if the necessary profiles
are present on the destination systems.
EPS. Acronym for Encapsulated PostScript. A standard
file format for importing and exporting PostScript language
files among applications in a variety of heterogeneous
environments.
error diffusion halftone. A specific halftone method in
which quantization errors are diffused spatially in a quasi-
random manner.
exception. An invalid or unsupported data-stream
construct.
exception condition. The condition that exists when a
product finds an invalid or unsupported construct.
exchange. The predictable interpretation of shared
information by a family of system processes in an
environment where the characteristics of each process
must be known to all other processes. Contrast with
interchange.
F
format. The arrangement or layout of data on a physical
medium or in a presentation space.
G
gamma. A measure of contrast in photographic images.
More precisely, a parameter that describes the shape of
the transfer function for one or more stages in an imaging
pipeline. The transfer function is given by the expression
output = input gamma where both input and output are scaled
to the range 0 to 1.
gamut. In color reproduction, the subset of colors that can
be accurately represented in a given circumstance, such
as within a given color space or by a certain output device.
generic. Relating to, or characteristic of, a whole group or
class.
GIF . See Graphic Interchange Format.
GOCA. See Graphics Object Content Architecture.
Graphic Interchange Format (GIF). An image format
type generated specifically for computer use. Its resolution
is usually very low (72 dpi, or that of your computer
screen), making it undesirable for printing purposes.
Graphics Object Content Architecture (GOCA). An
architected collection of constructs used to interchange
and present graphics data. GOCA was originally defined by
IBM; this architecture is no longer used in AFP. Instead, a
subset of GOCA was defined for use in AFP environments,
called AFP GOCA. Usually when the term “GOCA” is used
in AFP documentation, it means AFP GOCA.
grayscale. A means of specifying color using only one
color component in shades of gray ranging from black to
white
.
H
halftone. A method of generating, on a press or laser
printer, an image that requires varying densities or shades
to accurately render the image. This is achieved by
representing the image as a pattern of dots of varying size.
Larger dots represent darker areas, and smaller dots
represent lighter areas of an image.
hexadecimal. A number system with a base of sixteen.
The decimal digits 0 through 9 and characters A through F
are used to represent hexadecimal digits. The hexadecimal
digits A through F correspond to the decimal numbers 10
through 15, respectively. An example of a hexadecimal
number is X'1B', that is equal to the decimal number 27.
hierarchy. A series of elements that have been graded or
ranked in some useful manner.
highlight color. A spot color that is used to accentuate or
contrast monochromatic areas. See also spot color.
home state. An initial IPDS operating state. A printer
returns to home state at the end of each page, and after
downloading a font, overlay, or page segment.
host. (1) In the IPDS architecture, a computer that drives
a printer. (2) In IOCA, the host is the controlling
environment.
HSV color space. (1) A transformation of the RGB color
space that allow colors to be described in terms more
natural to an artist. The name HSV stands for hue,
saturation, and value. (2) Abbreviation for hue, saturation,
and value (a color model used in some graphics
programs). HSV must be translated to another model for
color printing or for forming screen colors.
I
ICC. See International Color Consortium.
ICC-absolute colorimetric. A rendering intent in which
the chromatically adapted tristimulus values of the in-
gamut colors are unchanged. It is useful for spot colors and
dpi • ICC-absolute colorimetric

## Page 149

CMOCA Reference 133
when simulating one medium on another (proofing). Note
that this definition of ICC-absolute colorimetry is actually
called “relative colorimetry” in CIE terminology, since the
data has been normalized relative to the perfect diffuser
viewed under the same illumination source as the sample.
ICC DeviceLink profile. An ICC profile that provides a
mechanism in which to save and store a series of device
profiles and non-device profiles in a concatenated format
as long as the series begins and ends with a device profile.
This is useful for workflows where a combination of device
profiles and non-device profiles are used repeatedly.
ICC profile. A file in the International Color Consortium
profile format, containing information about the color
reproduction capabilities of a device such as a scanner, a
digital camera, a monitor, or a printer. An ICC profile
includes three elements: 128-byte file header, tag table,
and tagged element data. The intent of this format is to
provide a cross-platform device profile format. Such device
profiles can be used to translate color data created on one
device into another device's native color space.
illuminant. Something that can serve as a source of light.
image. An electronic representation of a picture produced
by means of sensing light, sound, electron radiation, or
other emanations coming from the picture or reflected by
the picture. An image can also be generated directly by
software without reference to an existing picture.
Image Object Content Architecture (IOCA). An
architected collection of constructs used to interchange
and present images.
indexed color. A color image format that contains a
palette of colors to define the image. Indexed color can
reduce file size while maintaining visual quality.
input profile. An ICC profile that is associated with the
image and describes the characteristics of the device on
which the image was created.
instruction CMR. A color management resource that
identifies processing that is to be done to an object.
Intelligent Printer Data Stream (IPDS). An architected
host-to-printer data stream that contains both data and
controls defining how the data is to be presented.
intensity. The extreme strength, degree, or amount of
ink.
interchange. The predictable interpretation of shared
information in an environment where the characteristics of
each process need not be known to all other processes.
Contrast with exchange.
International Color Consortium (ICC). A group of
companies chartered to develop, use, and promote cross-
platform standards so that applications and devices can
exchange color data without ambiguity.
International Organization for Standardization
(ISO). An organization of national standards bodies from
various countries established to promote development of
standards to facilitate international exchange of goods and
services, and develop cooperation in intellectual, scientific,
technological, and economic activity.
IOCA. See Image Object Content Architecture.
IPDS. See Intelligent Printer Data Stream.
ISO. See International Organization for Standardization.
J
JFIF . See JPEG File Interchange Format.
Joint Photographic Experts Group (JPEG). The Joint
Photographic Experts Group (JPEG) is a standards
committee that designed an image compression format.
The compression format they designed is lossy, in that it
deletes information from an image that it considers
unnecessary. JPEG files can range from small amounts of
lossless compression to large amounts of lossy
compression.
JPEG. An image compression standard. See Joint
Photographic Experts Group.
JPEG File Interchange Format (JFIF). (1) JPEG File
Interchange Format (JFIF) is the most common file format
for JPEG images. (TIFF is another file format that can be
used to store JPEG images, and JNG is a third.) JFIF is not
a formal standard; it was designed by a group of
companies (though it is most often associated with C-Cube
Microsystems, one of whose employees published it) and
became a de facto industry standard. (2) Three-
component JPEG images. RGB data is assumed without
gamma correction and the APP0 marker is used to specify
the resolution and optionally the thumbnail.
L
LID. See local identifier.
line art. An image that contains only black and white with
no shades of gray.
line screen frequency. The measure of distance
between the rows of dots that make up a halftone screen.
Lower line screens are used on rougher, low quality
printing substrates (such as newsprint), while higher line
screens are used for high quality print jobs on smooth art
papers.
lines per inch (lpi). (1) The number of lines per inch on a
halftone screen. (2) Units used when measuring line
screen frequency.
ICC DeviceLink profile • lines per inch (lpi)

## Page 150

134 CMOCA Reference
little endian. A bit or byte ordering where the right-most
bits or bytes (those with a higher address) are most
significant. Contrast with big endian.
local identifier (LID). An identifier that is mapped by the
controlling environment to a named resource.
look-up table (LUT). (1) A table used to map one or more
input values to one or more output values. (2) A logical list
of colors or intensities. The list has a name and can be
referenced to select a color or intensity.
lpi. See lines per inch.
LUT . See look-up table.
Luv color space. The CIE color space in which L*, u* and
v* are plotted at right angles to one another. Equal
distances in the space represent approximately equal color
difference.
M
media. Plural of medium. See also medium.
media-relative colorimetric. This rendering intent
rescales the in-gamut, chromatically-adapted tristimulus
values such that the white point of the actual medium is
mapped to the PCS white point (for either input or output).
It is useful for colors that have already been mapped to a
medium with a smaller gamut than the reference medium
(and therefore need no further compression).
medium. A two-dimensional conceptual space with a
base coordinate system from which all other coordinate
systems are either directly or indirectly derived. A medium
is mapped onto a physical medium in a presentation-
system-dependent manner. Synonymous with medium
presentation space. See also presentation space.
Mixed Object Document Content Architecture
(MO:DCA). An architected, presentation-system-
independent data stream for interchanging documents.
MO:DCA. See Mixed Object Document Content
Architecture.
monochrome. A single color. Monochrome usually refers
to a black-and-white image. Also referred to as line art or
bitmap mode in Adobe
® Photoshop®. See also bilevel.
multilevel. Having multiple levels; for example, every
point in a multilevel image can have values from 0 to n,
where n is greater than 1. Contrast with bilevel.
multilevel device. A device that is used in a manner that
permits it to process color data of more than two levels.
Contrast with bilevel device.
N
NACK. See Negative Acknowledge Reply.
name. A table heading for architecture syntax. The
entries under this heading are short names that give a
general indication of the contents of the construct.
named color. A color that is specified with a descriptive
name. An example of a named color is “green”.
nColor color space. The color model used in IOCA
images that contain color components that typically do not
match any of the standard AFP color spaces, such as RGB
and CMYK. Often such images contain more than four
color components, although the number of color
components can be anywhere from two to fifteen, inclusive.
Negative Acknowledge Reply (NACK). In the IPDS
architecture, a reply from a printer to a host, indicating that
an exception has occurred. Contrast with Positive
Acknowledge Reply.
neighborhood-operation halftone. Halftone algorithm
that transfers the quantization error due to thresholding to
the unhalftoned neighbors of the current pixel. Error
diffusion is a neighborhood operation since it operates not
only on the input pixel, but also its neighbors. Contrast with
point-operation halftone.
O
object. (1) A collection of structured fields. The first
structured field provides a begin-object function, and the
last structured field provides an end-object function. The
object can contain one or more other structured fields
whose content consists of one or more data elements of a
particular data type. An object can be assigned a name,
that can be used to reference the object. Examples of
objects are presentation text, font, graphics, and image
objects. (2) Something that a user works with to perform a
task.
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
little endian • object identifier (OID)

## Page 151

CMOCA Reference 135
offset. A table heading for architecture syntax. The
entries under this heading indicate the numeric
displacement into a construct. The offset is measured in
bytes and starts with byte zero. Individual bits can be
expressed as displacements within bytes.
OID. See object identifier.
orientation. The angular distance a presentation space
or object area is rotated in a specified coordinate system,
expressed in degrees and minutes. For example, the
orientation of printing on a physical medium, relative to the
Xm axis of the X m,Ym coordinate system. See also
presentation space orientation and text orientation.
origin. The point in a coordinate system where the axes
intersect. Examples of origins are the addressable position
in an X m,Ym coordinate system where both coordinate
values are zero and the character reference point in a
character coordinate system.
output profile. An ICC profile that describes the
characteristics of the output device for which the image is
destined. The profile is used to color match the image to
the device's gamut.
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
page overlay used in MO:DCA environments. (2) The final
representation of such an object on a physical medium.
P
page. (1) A data stream object delimited by a Begin Page
structured field and an End Page structured field. A page
can contain presentation data such as text, image,
graphics, and bar code data. (2) The final representation
of a page object on a physical medium.
palette. The collection of colors or shades available to a
graphics system or program.
PANTONE. The proprietary PANTONE color matching
system is the most popular method of specifying extra
colors—not out of the CMYK four color process—for print.
PANTONE colors are numbered and mixed from a base set
of colors. By specifying a specific PANTONE color, a
designer knows that there is little chance of color variance
on the presses.
parameter. (1) A variable that is given a constant value
for a specified application. (2) A variable used in
conjunction with a command to affect its result.
PCL. A set of printer commands, developed by Hewlett-
Packard
®, that provide access to printer features.
PCS. See Profile Connection Space.
PDF . An acronym for Acrobat ® Portable Document
Format. PDF files are cross platform and contain all of the
image and font data. Design attributes are retained in a
compressed single package.
perceptual rendering intent. The exact gamut mapping
of the perceptual rendering intent is vendor specific and
involves compromises such as trading off preservation of
contrast in order to preserve detail throughout the tonal
range. It is useful for general reproduction of images,
particularly pictorial or photographic-type images.
pixel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity. Picture elements per inch is
often used as a measurement of presentation granularity.
Synonymous with pel and picture element.
PNG. See Portable Network Graphics.
point-operation halftone. Any halftone algorithm that
produces output for a given location based only on the
single input pixel at that location, independent of its
neighbors. Thus, it is accomplished by a simple point-wise
comparison of the input image against a predetermined
threshold array or mask. Contrast with neighborhood-
operation halftone.
Portable Network Graphics (PNG). A lossless image
format.
Positive Acknowledge Reply (ACK). In the IPDS
architecture, a reply to an IPDS command that has its
acknowledgment-required flag on and in which no
exception is reported. Contrast with Negative Acknowledge
Reply.
PostScript. A page description programming language
created by Adobe Systems Inc. that is a presentation-
system-independent industry standard for outputting
documents and graphics. It describes pages to any output
device with a PostScript interpreter.
presentation data stream. A presentation data stream
that is processed in AFP environments. The MO:DCA
architecture describes the AFP interchange data stream.
The IPDS architecture describes the AFP printer data
stream.
presentation device. A device that produces character
shapes, graphics pictures, images, or bar code symbols on
a physical medium. Examples of a physical medium are a
display screen and a sheet of paper.
presentation space. A conceptual address space with a
specified coordinate system and a set of addressable
offset • presentation space

## Page 152

136 CMOCA Reference
positions. The coordinate system and addressable
positions can coincide with those of a physical medium.
Examples of presentation spaces are medium, logical
page, and object area.
presentation space orientation. The number of degrees
and minutes a presentation space is rotated in a specified
coordinate system. For example, the orientation of printing
on a physical medium, relative to the X m axis of the X m,Ym
coordinate system. See also orientation and text
orientation.
print file. A file that is created for the purpose of printing
data. The print file is the highest level of the MO:DCA data-
stream document-component hierarchy.
process color. A color that is specified as a combination
of the components, or primaries, of a color space. A
process color is rendered by mixing the specified amounts
of the primaries. An example of a process color is C=0.1,
M=0.8, Y=0.2, K=0.1 in the cyan/magenta/yellow/black
(CMYK) color space. Contrast with spot color.
profile. In CMOCA, refers to an ICC profile.
Profile Connection Space (PCS). The reference color
space defined by ICC, in which colors are encoded in order
to provide an interface for connecting source and
destination transforms. The PCS is based on the CIE 1931
standard colorimetric observer.
Q
quantization. The process of reducing an image with
many colors to one with fewer colors, usually in preparation
for its conversion to a palette-based image. As a result,
most parts of the image (that is, most of its pixels) are
given slightly different colors that amounts to a certain level
of error at each location. Since photographic images
usually have extended regions of similar colors that get
converted to the same quantized color, a quantized image
tends to have a flat or banded (contoured) appearance
unless it is also dithered.
R
raster. (1) The area of the video display that is covered by
sweeping the electron beam of the display horizontally and
vertically. Normally the electronics of the display sweep
each line horizontally from top to bottom and return to the
top during the vertical retrace interval. (2) In computer
graphics, a predetermined pattern of lines that provides
uniform coverage of a display space. (3) In nonimpact
printers, an on-or-off pattern of electrostatic images
produced by the laser print head under control of the
character generator.
raster direction. An attribute that controls the direction in
which a character string grows relative to the inline
direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top.
rendering intent. A particular gamut-mapping style or
method of converting colors in one gamut to colors in
another gamut. ICC profiles support four different
rendering intents: perceptual, media-relative colorimetric,
saturation, and ICC-absolute colorimetric.
repeating group. A group of parameter specifications
that can be repeated.
reserved. Having no assigned meaning and put aside for
future use. The content of reserved fields is not used by
receivers, and should be set by generators to a specified
value, if given, or to binary zeros. A reserved field or value
can be assigned a meaning by an architecture at any time.
resolution. (1) A measure of the sharpness of an input or
output device capability, as given by some measure
relative to the distance between two points or lines that can
just be distinguished. (2) The number of addressable
pixels per unit of length.
resource. An object that is referenced by a data stream
or by another object to provide data or information.
Resource objects can be stored in libraries. In MO:DCA,
resource objects can be contained within a resource group.
Examples of resources are fonts, overlays, and page
segments.
retired. Set aside for a particular purpose, and not
available for any other purpose. Retired fields and values
are specified for compatibility with existing products and
identify one of the following:
1. Fields or values that have been used by a product in a
manner not compliant with the architected definition.
2. Fields or values that have been removed from
architecture.
RGB. Red, green and blue, the additive primary colors.
RGB color space. The basic additive color model used
for color video display, as on a computer monitor.
RIP . A raster image processor (RIP) is a hardware or
software tool that processes a presentation data stream
and converts it—rasterizes it—to a printable format.
rotation. The orientation of a presentation space with
respect to the coordinate system of a containing
presentation space. Rotation is measured in degrees in a
clockwise direction. Zero-degree rotation exists when the
angle between a presentation space's positive X axis and
the containing presentation space's positive X axis is zero
degrees.
row. A subarray that consists of all elements that have an
presentation space orientation • row

## Page 153

CMOCA Reference 137
identical position within the high dimension of a regular
two-dimensional array.
S
saturation rendering intent. The exact gamut mapping
of the saturation rendering intent is vendor specific and
involves compromises such as trading off preservation of
hue in order to preserve the vividness of pure colors. It is
useful for images that contain objects such as charts or
diagrams.
SBIN. A data type for architecture syntax, that indicates
that one or more bytes be interpreted as a signed binary
number, with the sign bit in the high-order position of the
leftmost byte. Positive numbers are represented in true
binary notation with the sign bit set to B'0'. Negative
numbers are represented in twos-complement binary
notation with a B'1' in the sign-bit position.
screen. (1) A halftone-threshold array. (2) The display
surface of a display device such as a computer monitor.
semantics. The meaning of the parameters of a
construct. See also syntax.
server. In a network, hardware or software that provides
facilities to other stations. Examples include: a file server,
a printer server, and a mail server.
sheet. A division of the physical medium; multiple sheets
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper, each representing a sheet. Envelopes are an
example of a physical medium that comprises only one
sheet. The IPDS architecture defines four types of
sheets: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
sheet has a top edge. A sheet has two sides, a front side
and a back side.
signed integers. The positive natural numbers (1, 2, 3,
...), their negatives (-1, -2, -3, ...) and the number zero. The
set of all integers is usually denoted in mathematics by Z,
which stands for Zahlen (German for “numbers”).
Specifications for Web Offset Publications (SWOP). A
standard set of specifications for color separations, proofs,
and printing to ensure consistency of color printing.
spot color. A color that is specified with a unique
identifier such as a number. A spot color is normally
rendered with a custom colorant instead of with a
combination of process color primaries. See also highlight
color. Contrast with process color.
sRGB. One of the standard RGB color spaces, a means
of specifying precisely how any given RGB value should
appear on a display or printed paper or any other output
device. sRGB was promoted by the ICC and submitted for
standardization by the International Electrotechnical
Commission (IEC).
stochastic. A method that uses a pseudo-random dot
size and/or frequency to create halftone images, but
without the visible regularity in the dot patterns found in
traditional screening.
structured field. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both.
subtractive primary colors. Cyan, magenta, and yellow
colorants used to subtract a portion of the white light that is
illuminating an object. Subtractive colors are reflective on
paper and printed media. When used together with various
degrees of coverage and variation, they have the ability to
create billions of other colors. Contrast with additive
primary colors.
SWOP . See Specifications for Web Offset Publications.
syntax. The rules governing the structure of a construct.
See also semantics.
T
tag. A data structure that is used within the data portion of
a color management resource (CMR). A CMR tag consists
of T agID, FieldType, Count, and ValueOffset.
Tagged Image File Format (TIFF). A rich and flexible
graphics image format.
text. A graphic representation of information. T ext can
consist of alphanumeric characters and symbols arranged
in paragraphs, tables, columns, and other shapes. An
example of text is the data sent in an IPDS Write T ext
command.
text orientation. A description of the appearance of text
as a combination of inline direction and baseline direction.
See also orientation and presentation space orientation.
TIFF . See T agged Image File Format.
tone transfer curve. A mathematical representation of
the relationship between the input and output of a system,
subsystem, or equipment. The function is normally one
dimensional consisting of a single channel of input
corresponding to a single channel of output. In imaging
systems, it is mainly used for contrast adjustments. In
printing, the tone transfer curve is also used to modify
images to compensate for dot gain.
triplet. A three-part self-defining variable-length
parameter consisting of a length byte, an identifier byte,
and parameter-value bytes.
tristimulus values. Three values that together are used
to describe a specific color. These values are the amounts
saturation rendering intent • tristimulus values

## Page 154

138 CMOCA Reference
of three reference colors (such as red, green, and blue)
that can be mixed to give the same visual sensation as the
specific color.
type. A table heading for architecture syntax. The entries
under this heading indicate the types of data present in a
construct. Examples include: BITS, CHAR, CODE, SBIN,
UBIN, UNDF.
U
UBIN. A data type for architecture syntax, indicating one
or more bytes to be interpreted as an unsigned binary
number.
unarchitected. Identifies data that is neither defined nor
controlled by an architecture. Contrast with architected.
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
UTC. Coordinated Universal Time, the standard time
reference for Earth and the human race. Knowing the UTC
time and one's timezone offset from it, makes it possible to
calculate the local time; for example, 1:00 PM UTC
corresponds to 5:00 AM Pacific Standard Time (on the
same day). UTC is almost the same thing as Greenwich
Mean Time (GMT), that was originally used as the standard
time reference.
V
vector graphics. A vector has a defined starting point, a
designated direction, and a specified distance. Vector
graphics is line-based graphics data, where vectors
determine how straight and curved lines are shaped
between specific points. A picture consists of lines and
colors to fill the areas enclosed by the lines.
W
white point. One of a number of reference illuminants
used in colorimetry that serve to define the color “white”.
Depending on the application, different definitions of white
are needed to give acceptable results. For example,
photographs taken indoors might be lit by incandescent
lights, that are relatively orange compared to daylight.
Defining “white” as daylight will give unacceptable results
when attempting to color correct a photograph taken with
incandescent lighting.
Y
YCbCr. A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order.
YCrCb. A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order.
Yxy color space. A color space belonging to the XYZ
base family that expresses the XYZ values in terms of x
and y chromaticity coordinates, somewhat analogous to
the hue and saturation coordinates of the HSV color space.
type • Yxy color space

## Page 155

Copyright © AFP Consortium 2006, 2025 139
Index
1-byte UBIN .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
2-byte UBIN .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
4-byte UBIN .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
A
additive. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .64
AFP architecture hierarchy . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
AFP environment . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
AFP resource. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
AFPC. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . .iii
alias . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
applicable CMR .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
architected tags .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
architectures
BCOCA. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
CMOCA . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
FOCA. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
GOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
IOCA. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
MOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
PTOCA . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
Array Height .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 46
Array Width . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 45
ASCII . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
AT oB0T ag . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
AT oB1T ag . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
AT oB2T ag . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
audit CMR . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 7, 99, 103, 113
audit Color Conversion CMR . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .98, 102
B
big endian. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
bilevel .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .26
bilevel point-operation halftone.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .26
Bilevel Point-Operation Screen Data . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 50
BITS . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
blueMatrixColumnT ag .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
blueTRCT ag .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
Boundary Condition . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 57
BT oA0T ag. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
BT oA1T ag. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
BT oA2T ag. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
BYTE . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
C
calibration. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 105, 122
Calibration Curve . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
Cartesian coordinate system . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .39
CCIR . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
chromaticAdaptationT ag .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
CIELAB . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 9, 21, 28
encodings .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
ranges .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .89
CIELAB color space . . .. . .. . . .. . .. . . .. . . 35, 92, 97–98, 107, 109–111
CIELAB D50 color space. . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .87
CIELABValue. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .89
CIEXYZ . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 9, 21, 28, 98, 114
CIEXYZ color space . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .109–111
clustered-dot . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
CMM .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .69
CMOCA .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
CMR. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 7, 9
applicable . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
audit . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .7, 10, 99, 103, 113
default .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 110
audit Color Conversion . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..98, 102
Color Conversion. .. . . .. . . .. . .. . . .. . .. . .7, 10, 12, 28, 102, 108, 114
default .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 109–110
default . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. 109, 111–112
device-specific. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 117
generic . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. 108, 111, 117
Halftone . .. . .. . . .. . .. . . .. 7, 10, 12, 26, 98, 107–108, 111, 115–117
default .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 109
generic.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 121
header . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
semantics . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .15
ICC DeviceLink .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
ignored . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .10
Indexed. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . 7, 11–12, 35, 107–108, 111
instruction . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .7, 10, 99, 103, 112
default .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 109
instruction Color Conversion . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
instruction T one Transfer Curve . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
Link Color Conversion . . . .. . .. 7, 10, 12, 33, 98–99, 108, 113, 115
creating . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
media-specific . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 112
name .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
passthrough . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 111
selected . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
syntax . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
T one Transfer Curve . .. . . .. 7, 10, 12, 27, 107–108, 111, 115, 117
default .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 109–110
generic, and supported appearances .. . . .. . . .. . .. . . .. . .. . . .. 122
usage hierarchy . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
use .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .10
CMR data . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 22, 38
CMR Engine . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .86
CMR header
semantics . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .15
CMR name .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
CMR processing . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
CMRAlias . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 12, 15
CMRSig .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .15
CMRType . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 12, 15
CMRVersion . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 12, 15, 108
CMY color space. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 110
CMYK . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 105, 116
CMYK color space . . .. . . .. . . .. . .. . . .. . .. . . .. . . 35, 50, 87, 90, 107, 110
CMYK data .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
CMYK printer . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 102
CODE . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 12, 37
Color Conversion .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
Color Conversion CMR. .. . . .. . .. . . .. . .. . . .. . . 7, 10, 28, 102, 108, 114
default . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 109–110
Color Fidelity Triplet .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 105
color management . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
Color Management Object Content Architecture . .. . .. . . .. . .. . . .. . .. 7
Color Management Resource .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
See CMR
Color Mapping T able . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
color palette . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..35, 107
Color Palette CIELAB . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .92
Color Palette CMYK .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .90
Color Palette Gray . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Color Palette Named Colorants . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .93

## Page 156

140 CMOCA Reference
Color Palette RGB .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .91
Color Palette tags. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
Color Rendering Dictionary . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
color space . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 109
CIELAB. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 92, 97–98, 109–110
CIELAB D50 . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .87
CIEXYZ.. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 98, 109–110
CIEXYZ, and CIELAB .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
CMYK .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 50, 87, 90, 110
device-dependent . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 9, 114
device-independent .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
gray .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .87
gray, RGB, CMYK, CIELAB, and named colorants .. . . .. . . .. . .. .35
grayscale. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
grayscale, named colorants, RGB, CMYK, and CIELAB . .. . . 107
highlight . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 107, 113
input . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 21, 79, 97–98, 103, 113–114
Luv, HSV, HLS, Yxy, and CMY. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
monochrome. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .89
multi-output . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
named colorants . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 87–88, 93, 107
output. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 21, 79, 97, 103, 107, 113
RGB . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. 50, 87, 91, 97, 110
special .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
Color Space of Data . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 20, 28–32, 69
Color Space Signature . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
Colorant Identification List . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
Colorant Identification List tag . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
colorant names. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
colorantT ableT ag. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .32
ColorSpace conversion profile .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 20, 28
Comment . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..26–27, 29, 34–35, 40
coordinate system
Cartesian. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .39
Coordinated Universal Time .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .41
copyrightT ag.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
CRD
See Color Rendering Dictionary
curve . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
Calibration . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
Operator Requested . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
T one Reproduction . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
tone transfer . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
D
D50 . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
data
CMYK .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
RGB . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
Data Object . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
Date and Time Stamp .. . .. . . .. . .. . . .. . . .. . .. . . ..26–27, 29, 34–35, 41
default CMR .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . . 109, 111–112
audit . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
instruction .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 109
Default Rendering Intent .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 77
device
input . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .21
multilevel . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
device attribute . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
device manufacturer. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
device model . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
device-dependent color space .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 9, 114
device-independent color space . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
device-specific CMR. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 117
device-specific fields . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .16
DeviceModel . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 13, 16
DeviceType . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 12, 16
dispersed . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
display device profile . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .20
Document. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
document component
Data Object .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
Document . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
Group of pages or sheets . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
Overlay . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
Page . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
Print file. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
dot gain .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..27, 122
driver .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 117
E
EHC
See Error Handling Control
embedded ICC profile . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .113–114
End Data. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . .26–27, 29, 34–35, 96
environment
AFP . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
interchange .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
non-AFP. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
EPS . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .113–115
error . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
error diffusion. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
Error Diffusion Filter .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 52
error diffusion halftone . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
Error Handling Control . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 105
exception . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .23
exception code . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
exception syntax . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
G
gamma . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .27
gamutT ag . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–32
generic . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
generic CMR . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. 108, 111, 117
GIF . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
GOCA object . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
gray color space . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 35, 87
grayscale . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 7, 113
grayscale color space . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 107, 110
grayTRCT ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–30
greenMatrixColumnT ag .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
greenTRCT ag .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
grid points. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .79
H
halftone .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
bilevel point-operation . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
error diffusion . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
multilevel point-operation.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
neighborhood-operation . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
point-operation .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
Halftone .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 12, 115, 124
Halftone CMR .. . . .. . .. . . .. . . .. 7, 10, 26, 98, 107–108, 111, 115–117
default . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 109
generic . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 121
Halftone Dictionaries . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 116
Halftone Subset . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 44
halftone type. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
clustered-dot. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
dispersed.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
error diffusion . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
stochastic . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18

## Page 157

CMOCA Reference 141
header
CMR . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
hex values . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
hierarchical search . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
highlight color . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 113
highlight color space. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 107, 113
HLS color space . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
host. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 117
HSV color space . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110
I
ICC DeviceLink .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 12, 28, 33, 113
ICC profile . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 28, 68–69, 71, 99, 103, 113
embedded. . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .113–114
ICC Profile Data.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 29, 34, 69
ICC Profile Filename . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 29, 34, 71
ICC profile header .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
ICC profile specification . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 117
ICC Profile Subset .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29, 68
ICC-absolute colorimetric rendering intent . . .. . .. . . .. . .. . . .. . . .. . . 104
ICCHeaderFields . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
ICCT ags. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .28
Indexed . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
Indexed CMR. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 7, 11, 35, 107–108, 111
indexed color . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 107
indexed color values. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
Indexed Subset .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 35, 87
IndexedColorValue . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .89
input color space . . .. . . .. . .. . . .. . .. . . .. . . 21, 79, 97–98, 103, 113–114
input device. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .21
Input Device profile . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .20
instruction CMR .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 7, 99, 103, 112
instruction Color Conversion CMR.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
instruction T one Transfer Curve CMR .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .98
Intelligent Printer Data Stream .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
intensity . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .89
Intensity . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .90–91, 93
interchange environment. . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
Inverse T one Transfer Curve Data .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
IPDS. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 7, 107, 109, 112, 117
J
JFIF . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . 111, 113
K
known colorant names . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .95
L
Length.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 12, 15
Line Screen Frequency . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
Link Audit CMR Name.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 75
Link Audit CMR OID . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 73
Link CMRE Identifier . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 86
Link Color Conversion CMR .. . 7, 10, 12, 33, 98–99, 103, 108, 113,
115
creating. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
Link Color Conversion Subset . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 72
Link Instruction CMR Name .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 76
Link Instruction CMR OID . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 74
Link LUT ICC-Absolute Colorimetric . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 84
Link LUT Media-Relative Colorimetric .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 80
Link LUT Perceptual .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 78
Link LUT Saturation .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 82
LinkColorConversion Identity . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
LinkColorConversion LUT . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
load-balancing . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 109
Location of Current Pixel . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 54
Look-and-Feel.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .19
lookup table . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .78
LUT . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 103
Luv color space . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 110
M
management
color . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
ManufacturerName . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 12, 16
mark color . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .95
Max Image Value .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 47
media-specific CMR .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 112
media-specific fields .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .17
MediaBrightness . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 13, 17, 112
MediaColor .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 13, 17
MediaFinish . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 14, 17
MediaWeight . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 14, 17
mediaWhitePointT ag . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
Metadata Guide for AFP . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . ..vi
Mixed Object Document Content Architecture.. . . .. . .. . . .. . .. . . .. . .. 7
MO:DCA . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
monochrome color space . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
monochrome display profile . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
monochrome input profile . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
monochrome object .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
monochrome output profile .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
multi-output color spaces . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .35
multilevel. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
multilevel device . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .48
multilevel point-operation halftone . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
Multilevel Point-Operation Screen Data .. . .. . . .. . . .. . .. . . .. . .. . 26, 51
N
n-component LUT-based display profile.. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
n-component LUT-based input profile . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
N-component LUT-based output profiles. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
named color . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
named colorants color space . .. . . .. . .. . . .. . .. . . .. 35, 87–88, 93, 107
Named Colour profile. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .28
neighborhood-operation halftone .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .26
non-AFP environment . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
notices . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 127
Number of Components.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..26–27, 43
Number of Device Levels . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 18, 26, 48
Number of Named Colorants . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .88
O
object
GOCA . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
monochrome. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
Object Identifier . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 73–74
OCA . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 113
Offset Tiling .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 49
OID . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 73–74
Operator Requested Curve .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 105
output color space . . .. . . .. . . .. . .. . . .. . .. . . .. 21, 79, 97, 103, 107, 113
output device profile .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .20

## Page 158

142 CMOCA Reference
Overlay. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
P
Page. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
palette .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 107, 113
Pantone . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 93, 95
passthrough .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
passthrough CMR .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 111
PCL. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
PCS . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 9, 21, 69, 98, 102
See Profile Connection Space
PDF . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . .7, 113–115
perceptual . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
platform signature. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
point-operation halftone . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .26
PostScript. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . .7, 114–115
PostScript preamble . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
PostScript Type 3 Halftone Dictionaries . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
Presentation Architectures. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 1
presentation data . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
presentation device. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
presentation environment . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 1
Print file . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
private tag . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .37
processing . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .11
CMR . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
processing hierarchy . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 113
processing mode. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 108
See CMR, audit
See instruction CMR
See Link Color Conversion CMR
profile
ColorSpace Conversion . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .20
display device . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .20
input device . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .20
output device .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .20
Profile Connection Space . . . .. . .. . . .. . . .. . .. . . .. . .. . 9, 28–32, 69, 109
Profile Creator signature .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
profile ID .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
profile size . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
profile version number.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .69
Profile/Device Class Signature .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 19–20
profileDescriptionT ag . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
property-specific fields . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
Q
Quantization Boundary T able. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 60
R
Raster Direction .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 55
redMatrixColumnT ag . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
redTRCT ag . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
rendering intent .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 69, 77–78, 103, 109
ICC-absolute colorimetric .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 104
perceptual, media-relative colorimetric, and saturation . . . .. . . 103
Resolution . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
resource
AFP .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 7
retired .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . v
RGB . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
RGB color space . . .. . . .. . .. . . .. . .. . . .. . . ..35, 50, 87, 91, 97, 107, 110
RGB data . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .97
RGB display .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 102
Rotation . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .19
S
selected CMR .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .98
signed integers . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 116
SMPTE-C . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .97
special color space . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 114
spot color
See highlight color
sRGB.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 33, 97, 102
STM . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 116
stochastic . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
subtractive . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .64
syntax
CMR . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .12
exception.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .38
tag .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .37
T
tag
private . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .37
syntax . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .37
tags
architected . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
Array Height . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 46
Array Width .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 45
AT oB0T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
AT oB1T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
AT oB2T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
Bilevel Point-Operation Screen Data. .. . .. . . .. . . .. . .. . . .. . .. . 26, 50
blueMatrixColumnT ag . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
blueTRCT ag . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
Boundary Condition . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 57
BT oA0T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
BT oA1T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
BT oA2T ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
chromaticAdaptationT ag. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
Color Palette . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .35
Color Palette CIELAB.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .92
Color Palette CMYK . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .90
Color Palette Gray . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .89
Color Palette Named Colorants . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .93
Color Palette RGB . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .91
Colorant Identification List. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 35, 95
colorantT ableT ag . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .32
Comment.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . .26–27, 29, 34–35, 40
copyrightT ag . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–32
Date and Time Stamp. . . . .. . .. . . .. . .. . . .. . .. . .26–27, 29, 34–35, 41
Default Rendering Intent .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 77
End Data .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . .26–27, 29, 34–35, 96
Error Diffusion Filter. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 52
gamutT ag . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–32
grayTRCT ag . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29–30
greenMatrixColumnT ag. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
greenTRCT ag. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 30–31
Halftone Subset . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 26, 44
ICC Profile Data . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 29, 34, 69
ICC Profile Filename. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 29, 34, 71
ICC Profile Subset . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 29, 68
Indexed Subset.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 35, 87
Inverse T one Transfer Curve Data . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
Link Audit CMR Name . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 75
Link Audit CMR OID . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 73
Link CMRE Identifier . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 86
Link Color Conversion Subset . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 72
Link Instruction CMR Name .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 76
Link Instruction CMR OID . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 34, 74
Link LUT ICC-Absolute Colorimetric . .. . .. . . .. . . .. . .. . . .. . .. . 34, 84
Link LUT Media-Relative Colorimetric . . .. . . .. . . .. . .. . . .. . .. . 34, 80

## Page 159

CMOCA Reference 143
Link LUT Perceptual . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 78
Link LUT Saturation.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 34, 82
Location of Current Pixel . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 54
Max Image Value. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 47
mediaWhitePointT ag. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
Multilevel Point-Operation Screen Data . . .. . .. . . .. . .. . . .. . . . 26, 51
Number of Components . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .26–27, 43
Number of Device Levels. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 48
Number of Named Colorants . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .88
Offset Tiling . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 49
profileDescriptionT ag . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 29–32
Quantization Boundary T able . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 60
Raster Direction .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 55
redMatrixColumnT ag. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
redTRCT ag . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 30–31
Threshold Value .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 59
T one Transfer Curve Data.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 27, 64
T one Transfer Curve Length .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
T one Transfer Curve Subset .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 27, 62
target color space . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
three-component matrix-based display profile. .. . . .. . .. . . .. . . .. . .. .28
three-component matrix-based input profile .. . .. . . .. . .. . . .. . . .. . .. .28
Threshold Value.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 26, 59
TIFF . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 37, 99, 111, 113, 117
TimeZone . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
T one Reproduction Curve. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 105
tone transfer curve.. . . .. . .. . . .. . .. . . .. . . .. . 12, 98, 105, 115, 122, 124
processing hierarchy. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 113
T one Transfer Curve CMR . . .. . .. .7, 10, 27, 107–108, 111, 115, 117
default .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 109–110
generic, and supported appearances
Dark, Accutone, Highlight Midtone, and Standard . . .. . . .. . . 122
T one Transfer Curve Data . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 27, 64
T one Transfer Curve Length .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
T one Transfer Curve Subset .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 27, 62
T oneTransferCurve Array . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .27
T oneTransferCurve Identity . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .27
trademarks . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 128
TTC
See tone transfer curve
U
UseCIEColor . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 116
UTC . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
See Coordinated Universal Time
UTCDiffH . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
UTCDiffM . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
UTF16.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 12, 37
W
white point . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 113
Y
YCbCr .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .110–111
YCrCb .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .110–111
Yxy color space .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 110

## Page 160

Advanced Function Presentation Consortium
Color Management
Object Content Architecture
Reference
AFPC-0006-02
