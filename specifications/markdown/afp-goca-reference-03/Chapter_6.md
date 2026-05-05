Chapter 6. Environment Controls
This chapter describes:
• Control instructions
• Drawing processor facilities, including
– Current attributes
– Drawing process controls
Control Instructions
The controlling environment communicates with the graphics processor by means of control instructions and
drawing process controls. Control instructions are embedded in environment-dependent carriers in AFP GOCA
as follows:
• When the graphics object is carried in a MO:DCA data stream, the carrier is a Graphics Data Descriptor
(GDD) structured field; for more information, see Appendix A, “Mixed Object Document Content Architecture
(MO:DCA) Environment”, on page 179.
• When the graphics object is carried in an IPDS data stream, the carrier is a Graphics Data Descriptor self-
defining field in the Wr ite Graphics Control (WGC) command; for more information, see Appendix B,
“Intelligent Printer Data Stream (IPDS) Environment”, on page 189.
Both the GDD and WGC contain the Set Current Defaults control instruction, defined in “Set Current Defaults
(SCD) Instruction” on page 66.
Note: In the MO:DCA environment, if the drawing defaults contain any invalid bits, the processor optionally
raises exception condition EC-000A.

## Page 86

66 GOCA for AFP Reference
Set Current Defaults (SCD) Instruction
This control instruction sets the current default values of the selected attributes and drawing process controls.
When the graphics object is carried in a MO:DCA data stream, this control instruction is contained in the
Graphics Data Descriptor (GDD) structured field. When the graphics object is carried in an IPDS data stream,
this control instruction is contained in the Graphics Data Descriptor (GDD) self-defining field of the Write
Graphics Control (WGC) command.
Syntax
Offset T ype Name Range Meaning
0 CODE X'21' Set Current Defaults instruction
1 UBIN LENGTH 4–n Length of following data
2 SET X'00' Drawing attributes
X'01' Line attributes
X'02' Character attributes
X'03' Marker attributes
X'04' Pattern attributes
X'0B' Arc parameters
X'10' Process color attributes
X'1 1' Normal line width attribute
All others Reserved
3–4 CODE MASK X'0000'–X'FFFF' Set mask
5 BITS FLAGS
Bit 0 DEF AUL T B'0',B'1'
B'0' Set all indicated items to their standard
default values
B'1' Set the indicated items to the values
contained in the source data
Bits 1–3 RES1 B'000' Reserved; only valid value
Bits 4–7 RES2 B'1 1 1 1' Reserved; only valid value
6–n DA T A Default values; bytes 6 onward are not present if
DEF AUL T=B'0'
This instruction permits the setting of a variable number of values, under control of the MASK parameter in
bytes 3–4, into the attribute set selected by the value of the SET parameter in byte 2. When a MASK bit equals
0, the default does not change and data bytes are not present for that attribute. A B'1' in any bit of MASK
indicates that the corresponding item is to be set. If the DEF AUL T bit is B'0', these items are set to the standard
defaults; if it is B'1', these items are set to the values contained in the data in bytes 6–n of the instruction.
If the value of an attribute specifies the drawing default in an attribute setting order , for example the X'00' value
of the MODE parameter used in the Set Mix order , it causes the current default to be set to the standard default
value.
Bits 0–15 in MASK correspond to items within the selected attribute set, as shown in the following tables. The
number of bytes required is set into the item corresponding to each 1 bit in Mask, in ascending numerical order
of the MASK bit (0–15). Setting is terminated when all the items requested have been loaded.
Set Current Defaults Instruction

## Page 87

GOCA for AFP Reference 67
The default value of a given attribute should be specified only once. If specified more than once, the results are
implementation dependent; it is recommended that future implementations use the last-specified value.
Notes:
1. When the integral part of the line width attribute is set, the fractional part is reset to zero. See “Line
Attributes” on page 29 for a description of the Line Width attribute.
2. The format of the DA T A field is the same as the corresponding data in the attribute setting drawing orders.
Drawing Attributes (SET=X'00')
Mask bit Item name Length (bytes)
0 Color 2
1 Reserved; must be zero N/A
2 Foreground mix 1
3 Background mix 1
4–15 Reserved; must be zeros N/A
Note: Setting any of the above attributes to a value is a shorthand way of setting all color , or mix, attributes to
the same value.
Line Attributes (SET=X'01')
Mask bit Item name Length (bytes)
0 Line type 1
1 Line width 1
2 Line end 1
3 Line join 1
4–15 Reserved; must be zeros N/A
Note: The line type attribute cannot be set to a custom value by this instruction.
Character Attributes (SET=X'02')
Mask bit Item name Length (bytes)
0 Angle X,Y 4
1 Cell-size CW ,CH 4
2 Direction 1
3 Precision 1
4 Character Set 1
5 Shear , X,Y 4
6–15 Reserved; must be zeros N/A
Note: The character symbol default attribute is not settable by this instruction.
Set Current Defaults Instruction

## Page 88

68 GOCA for AFP Reference
Marker Attributes (SET=X'03')
Mask bit Item name Length (bytes)
0 Reserved; must be zero N/A
1 Marker cell-size width, height 4
2 Reserved; must be zero N/A
3 Marker precision (obsolete, see Appendix C, “AFP GOCA Migration Functions”,
on page 195)
1
4 Marker set 1
5–6 Reserved; must be zeros N/A
7 Marker symbol 1
8–15 Reserved; must be zeros N/A
Pattern Attributes (SET=X'04')
Mask bit Item name Length (bytes)
0–3 Reserved; must be zeros N/A
4 Pattern Set 1
5–6 Reserved; must be zeros N/A
7 Pattern Symbol 1
8–10 Reserved; must be zeros N/A
1 1 Pattern Reference Point 4
12–15 Reserved; must be zeros N/A
Arc Parameters (SET=X'0B')
Mask bit Item name Length (bytes)
0 P value 2
1 Q value 2
2 R value 2
3 S value 2
4–15 Reserved; must be zeros N/A
Set Current Defaults Instruction

## Page 89

GOCA for AFP Reference 69
Process Color Attributes (SET=X'10')
Mask bit Item name Length (bytes)
0 Foreground mix 1
1 Background mix 1
2 Process Color 12-14
3–15 Reserved; must be zeros N/A
Architecture Note: If the color is specified using Drawing Attributes - Set = X'00', and the process color is also
specified using Process Color - Set = X'10', the last-specified color is used.
Normal Line Width Attribute (SET=X'1 1')
Mask bit Item name Length (bytes)
0 Normal line width 2
1–15 Reserved; must be zeros N/A
Architecture Note: If the normal line width attribute is specified, it establishes the absolute value of the normal
line width in 1440ths of an inch. If the line attributes are also specified and define the line width as a
multiple of the normal line width, the multiple is calculated based on the absolute value of the normal line
width. Furthermore, all Set Line Width and Set Fractional Line Width orders in the object are also
calculated based on the absolute value of the normal line width.
Instruction Process Checks
A check condition is set under the following conditions:
IPC-0002 • If the SET parameter (byte 2) is invalid or unsupported
• If the FLAGS parameter (byte 5) bits 1–3 are not B'000', or bits 4–7 are not B'1 1 1 1'
• If an unallocated item is referenced in the MASK parameter (bytes 3–4)
IPC-0003 • If the FLAGS parameter (byte 5) bit 0 is B'0' and LENGTH is not X'04'
• If the FLAGS parameter (byte 5) bit 0 is B'1' and the length of the immediate data (byte 6
onward) does not exactly match the length implied by the MASK parameter
IPC-0021 • If any values in the data are invalid or unsupported
Set Current Defaults Instruction

## Page 90

70 GOCA for AFP Reference
Drawing Processor Facilities
The following facilities are available to the drawing processor while it is processing segments:
• Current attributes
• Drawing process controls
Current Attributes
As the orders in a segment are processed, the drawing processor maintains the current values of all primitive
attribute types in the current attributes. These values are used by the graphics processor to draw output
primitives in the GPS.
Figure 33 shows how the controlling environment uses pre-defined standard defaults and the Set Current
Defaults control instruction in the GDD and WGC to establish drawing defaults before the drawing processor is
invoked to process a segment. At the start of processing of each new segment, the drawing default values are
set into the current attributes.
Figure 33. Attributes and Drawing Process Control
CONTROLLING
ENVIRONMENT
GRAPHICS
PROCESSOR
STANDARD
DEFAULTS
DRAWING DEFAULTS
CURRENT DEFAULTS
CURRENT VALUES
(New values SET
by Instru
ctions)
Drawing
Processor
Invocatio
n
(New values SET
by Orders
)
SET
Instructions
and
Initialization
SET Orders
and
SEGMENT
Initiation
Drawing Process Controls
The following controls manage various aspects of the drawing process:
Parameter type The format of the parameters in the drawing orders. These controls are described in
“Parameter T ype” on page 71.
Arc parameters V alues used as parameters when drawing circles or ellipses. These controls are
described in “Set Arc Parameters (GSAP) Order” on page 130.
Drawing defaults exist for each drawing process control. The defaults are maintained by the processor , and
they are set to the standard defaults, or to the current defaults provided by the environment, whenever the
processor is invoked.
Drawing Processor Facilities

## Page 91

GOCA for AFP Reference 71
Parameter T ype
The parameter type specifies the format of the parameters within drawing orders, and has two parts:
• Coordinate type
• Geometric parameter format
The format of this control is:
Mnemonic Standard default Length (in bytes) Meaning
COORD X'00' 1 Coordinate type
GEOM X'00' 1 Geometric parameter format
The following value of the coordinate type parameter is specified by both the GRS3 and DR/2V0 subsets and
is supported in AFP GOCA environments:
V alue Meaning
X'00' 2-D coordinates
The following value of the geometric parameter format is specified by both the GRS3 and DR/2V0 subsets and
is supported in AFP GOCA environments:
V alue Meaning
X'00' 16-bit signed integer , “high byte first” format
Drawing Processor Facilities

## Page 92

72 GOCA for AFP Reference

## Page 93

Copyright © AFP Consortium 1997, 2017 73
