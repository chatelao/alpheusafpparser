Chapter 6. Compliance
This chapter describes the MOCA subsets that are supported in the MOCA architecture.
MOCA Subsets
MOCA subsets are used to identify a specific level of MOCA functionality. Each new (higher level) subset must
incorporate the complete functionality of the previous (lower level) subset. The naming of MOCA subsets is
defined as follows.
Each new MOCA subset will be identified by the term MSx, where:
• MS = Metadata Subset
• x = level, starting with 1
MS1 Subset
The MS1 Subset is the level of MOCA compliance required to support the functionality contained in the first
edition of the MOCA Reference.
The following value of MOType must be supported:
• DES
The following value of MOFormat must be supported:
• XMP
The following values of MOCompression must be supported:
• NONE
• GZIP
• EXI
In environments where exception conditions can be reported, the following exception conditions must be
supported:
• EC-0100
• EC-0200
• EC-0210
• EC-0220
• EC-0230
• EC-0240
• EC-0250
• EC-0300

## Page 32

18 MOCA Reference

## Page 33

Copyright © AFP Consortium 2014–2024 19
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

## Page 34

20 MOCA Reference
Trademarks
PostScript and XMP are either registered trademarks or trademarks of Adobe Systems Incorporated in the
United States and/or other countries.
AFPC and AFP Consortium are trademarks of the AFP Consortium.
IBM is a trademark of the International Business Machines Corporation in the United States, other countries, or
both.
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

## Page 35

Copyright © AFP Consortium 2014–2024 21
Glossary
This glossary contains terms that apply to MOCA and
also a few terms that apply to other related
presentation architectures.
If you do not find the term that you are looking for,
please refer to the IBM Dictionary of Computing
(document number ZC20-1699), the InfoPrint
Dictionary of Printing, or the glossary in the IPDS
Reference (document number AFPC-0001).
The following definitions are provided as supporting
information only, and are not intended to be used as
a substitute for the semantics described in the body
of this reference.
A
Advanced Function Presentation (AFP). An open
architecture for the management of presentable
information that is developed by the AFP Consortium
(AFPC). AFP comprises a number of data stream and data
object architectures:
• Mixed Object Document Content Architecture (MO:DCA);
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
AFP . See Advanced Function Presentation.
AFP Consortium (AFPC). A formal open standards body
that develops and maintains AFP architecture. Information
about the consortium can be found at
http://www.afpconsortium.org.
AFP data stream. A presentation data stream that is
processed in AFP environments. The MO:DCA
architecture defines the strategic AFP interchange data
stream. The IPDS architecture defines the strategic AFP
printer data stream.
AFPDS. A term formerly used to identify the composed-
page MO:DCA-based data stream interchanged in AFP
environments. See also MO:DCA and AFP data stream.
AFP environment. Wherever the AFP architecture is
used in any way; by an AFP vendor, an AFP customer, or
any combination thereof.
AFP Tagging. (1) Associating extra information,
contained in a metadata object, with a given piece of AFP
data. Among other uses, such information could enable
users with vision impairments or other restrictions to make
full use of the content provided by an AFP system. (2) In
MOCA, a known format of a metadata object.
application. (1) The use to which an information system
is put. (2) A collection of software components used to
perform specific types of work on a computer.
architected. Identifies data that is defined and controlled
by an architecture. Contrast with unarchitected.
B
bar code. An array of elements, such as bars, spaces,
and two-dimensional modules that together represent data
elements or characters in a particular symbology. The
elements are arranged in a predetermined pattern
following unambiguous rules defined by the symbology.
Bar Code Object Content Architecture (BCOCA). An
architected collection of constructs used to interchange
and present bar code data.
BCOCA. See Bar Code Object Content Architecture.
big endian. A format for storage or transmission of binary
data in which the most significant bit (or byte) is placed
first. Contrast with little endian.
C
CMOCA. See Color Management Object Content
Architecture.
Color Management Object Content Architecture
(CMOCA). An architected collection of constructs used for
the interchange and presentation of the color management
information required to render a print file, document, group
of pages or sheets, page, overlay, or data object with color
fidelity.
compression algorithm. An algorithm used to compress
data. Compression of data can decrease the volume of
data.
construct. An architected set of data such as a structured
field or a triplet.

## Page 36

22 MOCA Reference
controlling environment. The environment in which an
object is embedded, for example, the IPDS and MO:DCA
data streams.
D
data stream. A continuous stream of data that has a
defined format. An example of a defined format is a
structured field.
deprecated. An architected construct is marked as
“deprecated” to indicate that it should no longer be used
because it has been superseded by a newer construct.
Use or support of a deprecated construct is permitted but
no longer recommended. Constructs are deprecated rather
than immediately removed to provide backward
compatibility.
device dependent. Dependent upon one or more device
characteristics.
device independent. Not dependent upon device
characteristics.
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
document element. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. An application
or device does not have to understand control information
or data to parse a data stream when all the records in the
data stream are document elements. See also structured
field.
E
Efficient XML Interchange (EXI). A format that allows
XML documents to be encoded as binary data, rather than
as plain text.
exception. An invalid or unsupported data-stream
construct.
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
Extensible Markup Language (XML). A set of rules for
encoding documents in a format that is both human-
readable and machine-readable.
Extensible Metadata Platform (XMP). An ISO standard,
originally created by Adobe Systems Incorporated, for the
creation, processing, and interchange of standardized and
custom metadata for all kinds of resources.
F
FOCA. See Font Object Content Architecture.
Font Object Content Architecture (FOCA). An
architected collection of constructs used to describe fonts
and to interchange those font descriptions.
G
GOCA. See Graphics Object Content Architecture.
graphics data. Data containing lines, arcs, markers, and
other constructs that describe a picture.
Graphics Object Content Architecture (GOCA). An
architected collection of constructs used to interchange
and present graphics data.
GZIP . A widely-used,free software compression algorithm.
H
hexadecimal. A number system with a base of sixteen.
The decimal digits 0 through 9 and characters A through F
are used to represent hexadecimal digits. The hexadecimal
digits A through F correspond to the decimal numbers 10
through 15, respectively. An example of a hexadecimal
number is X'1B', that is equal to the decimal number 27.
host. In the IPDS architecture, a computer that drives a
printer.
I
image. An electronic representation of a picture produced
by means of sensing light, sound, electron radiation, or
other emanations coming from the picture or reflected by
controlling environment • image

## Page 37

MOCA Reference 23
the picture. An image can also be generated directly by
software without reference to an existing picture.
Image Object Content Architecture (IOCA). An
architected collection of constructs used to interchange
and present images.
Intelligent Printer Data Stream (IPDS). An architected
host-to-printer data stream that contains both data and
controls defining how the data is to be presented.
interchange. The predictable interpretation of shared
information in an environment where the characteristics of
each process need not be known to all other processes.
Contrast with exchange.
International Organization for Standardization
(ISO). An organization of national standards bodies from
various countries established to promote development of
standards to facilitate international exchange of goods and
services, and develop cooperation in intellectual, scientific,
technological, and economic activity.
interoperability. The capability to communicate, execute
programs, or transfer data among various functional units
in a way that requires the user to have little or no
knowledge of the unique characteristics of those units.
IOCA. See Image Object Content Architecture.
IPDS. See Intelligent Printer Data Stream.
ISO. See International Organization for Standardization.
L
little endian. A bit or byte ordering where the right-most
bits or bytes (those with a higher address) are most
significant. Contrast with big endian.
M
M/O. A table heading for architecture syntax. The entries
under this heading indicate whether the construct is
mandatory (M) or optional (O).
meaning. A table heading for architecture syntax. The
entries under this heading convey the meaning or purpose
of a construct. A meaning entry can be a long name, a
description, or a brief statement of function.
metadata. Descriptive information that is associated with
and augments other data.
Metadata command set. In the IPDS architecture, a
collection of commands used to associate MOCA data with
objects.
metadata object. In AFP, the resource object that carries
metadata.
Metadata Object Content Architecture (MOCA). A
resource object architecture to carry metadata that serves
to provide context or additional information about an AFP
object or other AFP data.
Mixed Object Document Content Architecture
(MO:DCA). An architected, presentation-system
-
independent data stream for interchanging documents.
MO:DCA. See Mixed Object Document Content
Architecture.
MOCA. See Metadata Object Content Architecture.
N
name. A table heading for architecture syntax. The
entries under this heading are short names that give a
general indication of the contents of the construct.
O
object. (1) A collection of structured fields. The first
structured field provides a begin-object function, and the
last structured field provides an end-object function. The
object can contain one or more other structured fields
whose content consists of one or more data elements of a
particular data type. An object can be assigned a name,
that can be used to reference the object. Examples of
objects are metadata, presentation text, font, graphics, and
image objects. (2) Something that a user works with to
perform a task.
offset. A table heading for architecture syntax. The
entries under this heading indicate the numeric
displacement into a construct. The offset is measured in
bytes and starts with byte zero. Individual bits can be
expressed as displacements within bytes.
P
parameter. (1) A variable that is given a constant value
for a specified application. (2) A variable used in
conjunction with a command to affect its result.
Presentation Text Object Content Architecture
(PTOCA). An architected collection of constructs used to
interchange and present presentation text data.
PTOCA. See Presentation Text Object Content
Architecture.
R
range. A table heading for architecture syntax. The
entries under this heading give numeric ranges applicable
to a construct. The ranges can be expressed in binary,
decimal, or hexadecimal. The range can consist of a single
value.
Image Object Content Architecture (IOCA) • range

## Page 38

24 MOCA Reference
reserved. Having no assigned meaning and put aside for
future use. The content of reserved fields is not used by
receivers, and should be set by generators to a specified
value, if given, or to binary zeros. A reserved field or value
can be assigned a meaning by an architecture at any time.
retired. Set aside for a particular purpose, and not
available for any other purpose. Retired fields and values
are specified for compatibility with existing products and
identify one of the following:
• Fields or values that have been used by a product in a
manner not compliant with the architected definition
• Fields or values that have been removed from an
architecture
S
semantics. The meaning of the parameters of a
construct. See also syntax.
structured field. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. See also
document element.
surrogate pair. A sequence of two Unicode code points
that allow for the encoding of as many as 1 million
additional characters without any use of escape codes.
surrogates. A way to refer to one or more surrogate
pairs.
syntax. The rules governing the structure of a construct.
See also semantics.
T
triplet. A three-part self-defining variable-length
parameter consisting of a length byte, an identifier byte,
and parameter-value bytes.
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
X
XML. See Extensible Markup Language.
XMP . See Extensible Metadata Platform.
reserved • XMP

## Page 39

Copyright © AFP Consortium 2014–2024 25
Index
A
AFP Archive . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 7
AFP Consortium .. . .. . . .. . .. . . .. . .. . . .. . . iii, v
AFP T agging. .. . . .. . .. . . .. . .. . . .. . .. . . . 12, 15
AFPC. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
See AFP Consortium
architectures
BCOCA.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
CMOCA . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . iii, 4
FOCA. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
GOCA . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
IOCA.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
IPDS .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 2
MO:DCA . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 2
MOCA . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
PTOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 4
C
changes to the architecture . . .. . .. . . .. . . .. vii
compliance . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . ..17
E
exception conditions
EC-0100. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0200. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0210. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0220. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0230. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0240. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0250. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
EC-0300. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 13–14
mapped to IPDS exceptions . .. . . .. . . ..14
EXI .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 12, 16
G
gzip .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 12, 16
I
IPDS environment . .. . . .. . .. . . .. . .. . . .. . . .. . 9
MOCA exceptions mapped to. . . . .. . . ..14
M
mapping MOCA exception
conditions to IPDS.. . . .. . .. . . .. . .. . . .. . . ..14
metadata. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 4, 9
presentation semantics .. . . .. . .. . . .. . . .. . 9
Metadata Guide for AFP . .. . . .. . .. . . .. . v, 15
Metadata Object .. . .. . . .. . .. . . .. . .. . . .. . 7, 11
compression . . .. . .. . . .. . .. . . .. . .. . . .. . . ..16
format. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . ..15
header . .. . . .. . . .. . .. . . .. . .. . . .. . . 11–13, 15
semantics . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . ..13
syntax . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . ..12
type . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . ..15
Metadata Object Content Architecture . . . 7
in IPDS environment . . .. . . .. . . .. . .. . .9, 14
in MO:DCA environment . .. . . .. . .. . . .. . . 9
purpose . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 9
subsets .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. .17
MO:DCA environment .. . .. . . .. . . .. . .. . . .. . . 9
MOCA
See Metadata Object Content
Architecture
MS1 subset . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. .17
S
subsets
MS1.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. .17
T
trademarks . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. .20
X
XMP . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. 12, 15

## Page 40

Advanced Function Presentation Consortium
Metadata Object Content Architecture
Reference
AFPC-0013-02
