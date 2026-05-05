Appendix A. Mixed Object Document Content Architecture (MO:
DCA) Environment
This appendix describes how graphics objects may be included within a Mixed Object Document Content
Architecture (MO:DCA) document for the purpose of interchanging the graphics objects between a generating
node and one or more receiving nodes. See the Mixed Object Document Content Architecture (MO:DCA)
Reference for a full description of the MO:DCA architecture. The Graphics Data Descriptor and Graphics Data
structured fields used to carry graphics objects in MO:DCA documents are defined in the following sections.
T o guarantee interchange, a MO:DCA document carrying a graphics object must include all information related
to the object. The MO:DCA document must therefore contain not only the definition of the graphics object, but
it must also provide linkage to the resources that the object references.
The discussion of MO:DCA structured fields is included in this appendix solely for setting the context of their
use by graphics.
Compliance with MO:DCA Interchange Sets
When graphics objects are interchanged with the purpose of outputting the objects on a display , printer , or
other output device, it is very important that visual fidelity be maintained as far as is possible. In an attempt to
satisfy this objective, the GOCA architecture defines the following for the MO:DCA environments:
• A set of rules that must be followed by all generators when constructing graphics objects
• A set of graphics processing capabilities that are guaranteed to be supported by all receivers
In order to comply with a particular MO:DCA Interchange Set, products that generate graphics objects must
only generate objects that contain graphics items and values defined in that interchange set. Including items or
values not in the interchange set can result in processing exceptions being raised by the receiving processor ,
and exception actions being taken. However , a generator must not rely on a receiver taking these actions.
In order to conform to a particular MO:DCA Interchange Set, products that receive graphics objects and
convert them using a processor for output to some device, are required to support all the graphics facilities
defined in that interchange set.

## Page 200

180 GOCA for AFP Reference
Graphics Structured Fields in the MO:DCA Environment
This section describes the syntax of the Graphics Data Descriptor (GDD) and Graphics Data (GAD) structured
fields in a MO:DCA document.
Graphics Data Descriptor (GDD) in the MO:DCA Environment
The GDD is a mandatory structured field in the Object Environment Group of a MO:DCA graphics object. The
GDD contains GOCA control instructions that define the following:
• The drawing order subset that needs to be supported by the receiver for proper interpretation of the graphics
data
• The GPS measurement units; note that these are also the DOCS measurement units
• The size and position of the GPS window that will be mapped to the MO:DCA object area
• The resolution of raster images in the object
• The graphics drawing defaults, specified by the Set Current Defaults instruction, that must be set up by the
receiver
In this environment, only the following attributes can have their default values set using the Set Current
Defaults instruction:
• Drawing Attributes
• Line Attributes
• Character Attributes
• Marker Attributes
• Pattern Attributes
• Arc Parameters
• Process Color Attributes
• Normal Line Width Attribute
Note: This is the same set of defaults as are supported by the Intelligent Printer Data Stream (IPDS)
architecture.
Structured Field Introducer
SF Length X'D3A6BB' Flags Reserved Self-Identifying Parameters
MO:DCA Environment

## Page 201

GOCA for AFP Reference 181
GDD Self-Identifying Parameters
Drawing Order Subset
This parameter has been retired for the DR/2V0 (GRS2) subset. New GOCA GRS2 generators should not
specify this parameter and new receivers should ignore it. GOCA generators that generate functions that are
only in a higher-level subset, such as GRS3, must not generate this parameter since there is no method to
specify a subset other than DR/2V0 (GRS2). If this parameter is not specified, the functional level of the GOCA
object is DR/2V0 (GRS2) or higher . If invalid bits are specified in this self-identifying parameter , EC-000A may
optionally be detected.
See Appendix C, “AFP GOCA Migration Functions”, on page 195 for information about this retired parameter .
Architecture Note: The obsolete IBM AFP Data Stream Reference, S544-3202, allowed the Drawing Order
Subset Parameter to be optional. If this parameter was not provided, the default was defined to be
Drawing Order Subset level 2, version 0 (DR/2V0).
MO:DCA Environment

## Page 202

182 GOCA for AFP Reference
Window Specification (Mandatory)
Offset T ype Name Range Meaning
0 CODE X'F6' Window Specification
1 UBIN LENGTH 18 Length of following data
2 BITS FLAGS
Bit 0 PPS B'0' Picture Presentation Space:
B'0' 2-D
Bit 1 ABS B'1' Picture Dimensions:
B'1' Absolute; picture is designed for
presentation in L-units (see bytes 5–9)
Bit 2 RES1 B'0' Reserved; only valid value
Bit 3 IMGRES B'0', B'1' Image Resolution:
B'0' Resolution not defined or non-
symmetric image
B'1' X and Y resolutions are equal and are
defined by IMXYRES (see bytes 10–
1 1)
Bit 4 IMGNS B'0', B'1' Non-symmetric image; ignored if bit 3 = B'1'
B'0' Resolution not defined or symmetric
image
B'1' Image resolution is 120 × 144 points
per inch
Bits 5–7 RES2 B'000' Reserved; only valid value
3 RES3 X'00' Reserved; only valid value
4 CODE CFORMA T X'00' Geometric parameter format:
X'00' 16-bit high-byte-first signed integer
5 CODE UBASE X'00' - X'01' Unit Base for GPS:
X'00' T en inches
X'01' T en centimeters
6–7 UBIN XRESOL X'0001'–X'7FFF' Number of X
g
units/UBASE; must be the same
as YRESOL
8–9 UBIN YRESOL X'0001'–X'7FFF' Number of Y
g
units/UBASE; must be the same
as XRESOL
10–1 1 UBIN IMXYRES X'0000'–X'7FFF'
X'0000' Not specified
X'0001'–X'7FFF' Number of image points per
UBASE in X and Y directions
12–13 SBIN XL WIND X'8000'–X'7FFF',
see note
X
g
coordinate for left edge of GPS window
14–15 SBIN XRWIN D X'8000'–X'7FFF',
see note
X
g
coordinate for right edge of GPS window
16–17 SBIN YBWIND X'8000'–X'7FFF',
see note
Y
g
coordinate for bottom edge of GPS window
18–19 SBIN YTWIND X'8000'–X'7FFF',
see note
Y
g
coordinate for top edge of GPS window
Note: The complete range is valid, and assumes a measurement unit of 1/1440 inch. That is, the measurement base is
ten inches, and the X
g
, Y
g
units per unit base are 14,400.
MO:DCA Environment

## Page 203

GOCA for AFP Reference 183
If invalid bits are specified in this self-identifying parameter , EC-000A may optionally be detected.
If a measurement unit other than 1/1440 inch is used, then the range values for XL WIND, XR WIND, YBWIND,
and YTWIND can be determined by using the following steps.
1. Calculate the number of actual supported units per inch X as follows:
• If the measurement base is ten inches, divide the number of supported units per ten inches by 10.
• If the measurement base is ten centimeters, multiply the number of supported units per ten centimeters
by 0.254.
2. Calculate the ratio of actual supported units per inch X to the assumed 1440 units per inch. T o do this,
divide X by 1440, yielding the ratio Y .
3. Calculate the new range value in the supported measurement units as follows:
a. Convert the old range value to base ten, then multiply it by the ratio Y .
b. Round to the nearest integer .
For example, suppose that the specified range is X'8000'–X'7FFF' when using 14,400 units per 10 inches. The
equivalent range at a unit of measure of 1/240 of an inch is calculated as follows:
1. Supported units per inch:
2400 ÷ 10 = 240
2. Ratio of supported units per inch to 1440 units per inch:
240 ÷ 1440 = 1/6
3. Range at 2400 units per 10 inches:
X'8000' = -32,768 (converted to base 10)
-32,768 ⋅ 1/6 = -5461.3333
X'7FFF' = 32,767 (converted to base 10)
32,767 ⋅ 1/6 = 5461.1667
Therefore, the equivalent range at 2400 units per 10 inches is -5461 to 5461, which in hexadecimal is X'EAAB'
to X'1555'.
Architecture Notes:
1. The obsolete IBM AFP Data Stream Reference, S544-3202, allowed 4 additional reserved bytes following
the YTWIND parameter . These bytes are supported by AFP GOCA receivers for migration, but new AFP
GOCA generators should not generate these bytes.
2. The image resolution value specified by the IMGRES, IMGNS, and IMXYRES parameters allows a
presentation device to maintain the size of GOCA images when scaling or resolution-correcting the GOCA
object. In the absence of this information and any other externally-provided information on the resolution of
a GOCA image, the image is mapped point-to-pel in the presentation device. In that case, the resulting
image size varies with the resolution of the device.
3. The IPDS environment defines additional exceptions for invalid parameters in the IPDS version of the
GDD. For example, an exception is defined for the case where the GPS Window coordinates are
inconsistent.
MO:DCA Environment

## Page 204

184 GOCA for AFP Reference
Set Current Defaults (Optional)
Defaults can be set by the appropriate Set Current Defaults instructions. For a complete description of this
instruction, see “Set Current Defaults (SCD) Instruction” on page 66. Each occurrence of the Set Current
Defaults instruction specifies a particular attribute set. The following tables show the maximum set of attributes
allowed. Subsets of these attribute sets are also allowed, using the MASK bits as selectors for attributes in the
particular attribute set. The format of the attribute sets is described in “Set Current Defaults (SCD) Instruction”
on page 66.
In the tables below , two possibilities exist. If the FLAG byte equals X'8F', the LENGTH byte would be specified
as the second value shown, and the values shown in bytes 6-n would be specified as shown. If the FLAG byte
instead equals X'0F', the LENGTH byte would be specified as the first value shown (that is, 4), and bytes 6-n
would not be specified.
Set Current Defaults—Drawing Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 8 Length of following data
2 CODE SET X'00' Drawing Attributes
3–4 BITS MASK X'B000' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6–7 CODE COLOR See T able 5 on page 15 Color
8 CODE FORMIX X'00', X'02' Foreground mix
9 CODE BACKMIX X'00', X'05' Background mix
Set Current Defaults—Line Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 8 Length of following data
2 CODE SET X'01' Line Attributes
3–4 BITS MASK X'F000' Set Mask
5 CODE FLAG X'0F', X'8F' X'0F' Use standard default
X'8F' Use values in bytes 6–n
6 CODE LINETYPE X'00'–X'08' Line type
7 UBIN LINEWID X'00'–X'FF' Line width
8 CODE LINEEND X'00'–X'03' Line end
9 CODE LINEJOIN X'00'–X'03' Line join
MO:DCA Environment

## Page 205

GOCA for AFP Reference 185
Set Current Defaults—Character Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 19 Length of following data
2 CODE SET X'02' Character Attributes
3–4 BITS MASK X'FC00' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6–9 SBIN ANGLE X'8000'–X'7FFF' Character Angle X,Y
10–13 SBIN CELLSIZE X'8000'–X'7FFF' Character cell-size CW ,CH
14 CODE DIRN X'00'–X'04' Character direction
15 CODE PREC X'00'–X'02' Character precision
16 CODE SET X'00'–X'FF' Character set
17–20 SBIN SHEAR X'8000'–X'7FFF' Character shear X,Y
Set Current Defaults—Marker Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 1 1 Length of following data
2 CODE SET X'03' Marker Attributes
3–4 BITS MASK X'5900' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6–9 SBIN CELLSIZE X'8000'–X'7FFF' Marker cell-size width, height
10 CODE PREC X'00'–X'02' Marker precision (obsolete, see Appendix
C, “AFP GOCA Migration Functions”, on
page 195)
1 1 CODE SET X'00' Marker set
12 CODE SYMBOL X'00'–X'0A', X'40' Marker symbol
MO:DCA Environment

## Page 206

186 GOCA for AFP Reference
Set Current Defaults—Pattern Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 10 Length of following data
2 CODE SET X'04' Pattern Attributes
3–4 BITS MASK X'0910' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6 CODE SET X'00' –X'FD' Pattern set
7 CODE SYMBOL X'00'– X'FF' Pattern symbol
8–9 SBIN XPOS X'8000'–X'7FFF' X
g
coordinate of the pattern reference point
10–1 1 SBIN YPOS X'8000'–X'7FFF' Y
g
coordinate of the pattern reference point
Set Current Defaults—Arc Parameters
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 12 Length of following data
2 CODE SET X'0B' Arc Parameters
3–4 BITS MASK X'F000' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6–7 SBIN P X'8000'–X'7FFF' P parameter of arc transform
8–9 SBIN Q X'8000'–X'7FFF' Q parameter of arc transform
10–1 1 SBIN R X'8000'–X'7FFF' R parameter of arc transform
12–13 SBIN S X'8000'–X'7FFF' S parameter of arc transform
MO:DCA Environment

## Page 207

GOCA for AFP Reference 187
Set Current Defaults—Process Color Attributes
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 18-20 Length of following data
2 CODE SET X'10' Process Color Attributes
3–4 BITS MASK X'E000' Set Mask
5 CODE FLAG X'0F', X'8F'
X'0F' Use standard default
X'8F' Use values in bytes 6–n
6 CODE FORMIX X'00', X'02' Foreground mix
7 CODE BACKMIX X'00', X'05' Background mix
8-19, 8-20,
8-21
CODE PROCOLOR Process Color value; syntax defined by Set
Process Color drawing order starting with
byte 2 (reserved)
Set Current Defaults—Normal Line Width Attribute
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4, 6 Length of following data
2 CODE SET X'1 1' Normal Line Width Attribute
3–4 BITS MASK X'8000' Set Mask
5 CODE FLAG X'0F', X'8F' X'0F' Use standard default
X'8F' Use values in bytes 6–n
6-7 UBIN NORML W X'0000'–X'FFFF' Normal line width in 1440ths of an inch
MO:DCA Environment

## Page 208

188 GOCA for AFP Reference
Graphics Data (GAD) in the MO:DCA Environment
The graphics segments for a graphics object are contained within one or more GAD structured fields. Receipt
of the first segment starts the drawing process. No restrictions exist on how much or how little graphics data is
specified in a single GAD, except for the length limit of the structured field. A GAD, for example, can carry
partial segments, full segments, multiple segments, or any combination of these. The only requirement is that
the data itself is ordered in the sequence that is expected for immediate processing and that the last GAD
completes the last segment.
Because this environment does not support the calling of segments, all segments should be chained
segments. Any unchained segments in the data are ignored.
The GAD structured field is optional in a MO:DCA graphics object and may be repeated multiple times.
Structured Field Introducer
SF Length X'D3EEBB' Flags Reserved Begin Segment commands followed by
segment data in the form of drawing
orders
Syntax and semantics for the Begin Segment command are described in “Begin Segment Command” on page
75.
GOCA Subsets within the MO:DCA Environment
GOCA objects in MO:DCA documents must comply with the architecture described in this Reference. MO:DCA
interchange sets may restrict the GOCA content in GOCA objects.
In the MO:DCA architecture, for any of the color attribute-setting orders, there are no color values that are
required to be supported. These color orders can specify any of the values allowed by the architecture. If a
receiver does not support the requested value, an exception condition is optionally raised and the standard
action is performed; that is, a device-dependent color is used.
All receiving products that claim to support the MO:DCA environment must support at least all the orders in
GOCA subset DR/2V0.
MO:DCA Environment

## Page 209

Copyright © AFP Consortium 1997, 2017 189
