Chapter 3. Color Management Resource (CMR)
A CMR consists of a header followed by CMR data.
General Color Conversion Concepts
T o start, for simplicity, assume that the source data is specified in a device-independent color space (that is, an
ICC Profile Connection Space (PCS) such as CIEXYZ or CIELAB). The procedure for producing output uses
the following sequence:
Figure 4. Procedure for Producing Halftone Output from PCS
Color Convert from
PCS to color space
of output device
Color
Calibration
Halftone
Data
When color data has been processed using the above sequence, the resulting object can be stored in a
database. It might be useful to keep an audit trail of the operations that were performed to create the object.
This audit information can be merely descriptive, or it can be used to undo some of the operations performed
on the object and thus restore it to the original form when it was expressed in the PCS color space. In this
case, the inverse of each function is applied in this sequence:
Figure 5. Procedure for Converting Input Data to Full Color in PCS
Undo Color Conversion
i.e. convert source color
space to PCS
Undo Color
Calibration
Undo
Halftoning
Here is a more mathematical way of expressing that sequence:
(halftone data)
-1 → (color calibration) -1 → (color conversion) -1
In actuality, halftoning is not typically undone. The halftoned version is typically not stored in the database.
Further, undoing the halftoning is very complex. Note also that objects prepared for a display are not halftoned.
Therefore, this document assumes that no attempt will be made to go from halftoned data back to the object
represented in the PCS color space.
Now, let's assume that the source data is specified in a device-dependent color space and it is desired to
render it on an output device. The source data must be converted to the color space of the device. This
involves the following combined sequence:
Figure 6. Procedure for Creating Halftone Data from Device-Specific Color Data
Convert from
source color
space to PCS
Undo
source
Color
Calibration
Color
Calibration
Convert from
PCS to color
space of
output device
Halftone
Data

## Page 26

10 CMOCA Reference
Information that relates to the creation of the source data is referred to as an audit CMR. It might describe how
the source data was created, as in an audit halftone. Or it might describe how to undo an operation that was
used to create the source data. For instance, an audit color conversion profile tells how to convert from the
color space of the source data to a PCS. Audit tone transfer curves have been architected to describe an
action that was done to create the source data. There is also an optional tag that describes how to undo the
tone transfer curve.
The above discussion assumes that the color was specified using a color space. It is also possible to specify a
color using an index and then define how to produce that color using a palette that tells which component inks/
toners are used, in which concentration. An Indexed CMR is used to define how to produce the indexed colors.
Understanding the Use of CMRs
There are seven basic uses of the CMR that must be considered:
1. Audit Halftone can be attached to an image that has been halftoned and indicates which halftone was
used.
2. Audit Tone Transfer Curve indicates a one-dimensional conversion that was applied to the input color
before halftoning. The inverse curve needs to be applied to get back to the input color space.
3. Audit Color Conversion is used to convert from the input color space to a profile connection space.
4. Instruction Color Conversion is used to convert from the profile connection space to the color space of
the output device.
5. Instruction Tone Transfer Curve is a one-dimensional conversion applied to the output color before
halftoning (or a set of 1-D conversions, one for each color component).
6. Instruction Halftone is used to halftone the output colored data.
7. Link Color Conversion provides an efficient means for converting directly from the input color space to
the output color space and can be substituted for the Audit Color Conversion/Instruction Color Conversion
pair. A special type of Link Color Conversion, ICC DeviceLink, converts directly from input to output space
without reference to an audit/instruction pair.
When a presentation device is processing data using the CMR system, these are the basic steps that are
followed. Note that device-default CMRs are used if an applicable CMR is not explicitly invoked. If a CMR is
ignored, the device must accept it but does not error check or process the contents.
1. The device ignores an Audit Halftone. It might be useful information to store with the image in a database,
but it is not currently used in the presentation device since undoing halftoning is not a simple process.
2. The device applies an applicable Audit Tone Transfer Curve. If an inverse tone transfer curve is
specified, it is used. Otherwise, the function specified by the tone transfer curve must be inverted before it
is applied. Note that the inverse tone transfer curve is not a well-defined function.
Note: It is strongly recommended that any audit T one Transfer Curve that is used should be invoked at the
object level. This helps avoid ambiguity that might occur since the selection of the TTC is based only
on the number of components in the color space.
3. If an applicable ICC DeviceLink exists, it is used. This link color conversion converts the input color space
Color Management Resource (CMR)

## Page 27

CMOCA Reference 11
to the output color space of the current device. If this CMR is selected for use, the next two steps (selection
of Audit Color Conversion and Instruction Color Conversion CMRs) are skipped.
4. If an applicable Audit Color Conversion exists, it is used. This audit Color Conversion converts the input
color space (RGB, CMYK, grayscale...) to a profile connection space.
5. An Instruction Color Conversion is used to convert from the profile connection space into the output
color space of the current device.
6. If an Instruction Tone Transfer Curve exists, the color is modified using it.
7. The colored data is halftoned using an Instruction Halftone.
In reality, the processing of sequential steps can be combined to improve performance. A Link Color
Conversion CMR can be used in place of a combined audit/instruction color conversion pair. Chapter 6
discusses how CMRs are used in more detail.
The Indexed CMR does not fit into the scheme discussed above. It is used to define how to produce indexed
color.
Color Management Resource (CMR)

## Page 28

12 CMOCA Reference
CMR Syntax
This is the syntax of a CMR. Bytes 0–163 represent the CMR Header. Each field in the CMR header has a
fixed length. The fields from byte 10–155 are character data encoded as UTF-16BE. Data in these fields are
left aligned. If the number of the characters in these fields is smaller than the field length, the remaining bytes
will be padded with @ (X'0040'). Any field from byte 44–139 is “unspecified” if it is filled with @ characters.
The CMR name is the concatenated fields in bytes 10–155, exactly in the order specified in the CMR header.
The CMR name is 73 characters (146 bytes) long. It should be unique, since it is the name that will be used in
the MO:DCA data stream to reference the resource. For example, if the CMR header fields are:
Alias=JohnMay4, CMRType=HT , CMRVersion=1.2, ManufacturerName=IBM, DeviceType=4100,
DeviceModel=PD1, MediaBrightness=94, MediaColor=wht, MediaFinish=gl, MediaWeight=90, number of
device levels=2, Halftone type=rnd, line screen frequency=141, resolution=600, and rotation=proc
then the CMR name is:
JohnMay4HT001.200IBM@@4100@@PD194@whtgl90@2@@@@rnd@@@141@600@proc@@@@@@@@
Table 4. CMR Object Syntax
Length
in
Bytes Offset Type Name Range Meaning M/O
4 0–3 4-byte
UBIN
Length X'000000A4' –
X'FFFFFFFF'
CMR length, including
length field
M
4 4–7 CODE CMRSig X'434D5239' Signature of this CMR M
2 8–9 X'0000' Reserved: should be set to
zero
M
CMR Name starts here. It is composed of bytes 10–155.
16 10–25 UTF16 CMRAlias No restriction Human-readable alias M
4 26–29 UTF16 CMRType CC (X'0043 0043') Color Conversion M
DL (X'0044 004C') ICC DeviceLink Color
Conversion
HT (X'0048 0054') Halftone
IX (X'0049 0058') Indexed
LK (X'004C 004B') Link Color Conversion
TC (X'0054 0043') T one Transfer Curve
14 30–43 UTF16 CMRVersion ddd.ddd (where “d” is a
decimal digit character)
CMRVersion number M
AFP .ddd (where “d” is a
decimal digit character)
Special AFP version
number
generic “generic”
pasthru “passthrough”
10 44–53 UTF16 Manufacturer
Name
See description for name Name of the manufacturer M
@@@@@
12 54–65 UTF16 DeviceType See description for type Type of the device M
@@@@@@
CMR Syntax

## Page 29

CMOCA Reference 13
Table 4 CMR Object Syntax (cont'd.)
Length
in
Bytes Offset Type Name Range Meaning M/O
6 66–71 UTF16 DeviceModel See description for model Model of the device M
@@@
6 72–77 UTF16 Media Brightness 0–100 for print media For print media, the
percentage of light reflected
from the media
M
Zxy for screens For screen, a CIE illuminant
@@@
6 78–83 UTF16 MediaColor No restriction on the range;
a sample of CMOCA-
recommended values is
given below
Color of the media: M
blu (X'0062 006C 0075') blue
buf (X'0062 0075 0066') buff
gdr (X'0067 0064 0072') goldenrod
grn (X'0067 0072 006E') green
gry (X'0067 0072 0079') gray
ivy (X'0069 0076 0079') ivory
noc (X'006E 006F 0063') no-color
org (X'006F 0072 0067') orange
pnk (X'0070 006E 006B') pink
red (X'0072 0065 0064') red
wht (X'0077 0068 0074') white
ylw (X'0079 006C 0077') yellow
A three-character value
consisting of only upper-
case characters in the
range [A,Z]
custom
@@@ (X'0040 0040 0040') not specified
CMR Syntax

## Page 30

14 CMOCA Reference
Table 4 CMR Object Syntax (cont'd.)
Length
in
Bytes Offset Type Name Range Meaning M/O
4 84–87 UTF16 MediaFinish No restriction on the range;
a sample of CMOCA-
recommended values is
given below
Surface characteristics of
the media:
M
cm (X'0063 006D') commodity
ct (X'0063 0074') coated
gl (X'0067 006C') glossy
hg (X'0068 0067') high-gloss
mt (X'006D 0074') matte
no (X'006E 006F') none
np (X'006E 0070') newsprint
sg (X'0073 0067') semi-gloss
st (X'0073 0074') satin
tr (X'0074 0072') treated
A two-character value
consisting of only upper-
case characters in the
range [A,Z]
custom
@@ (X'0040 0040') not specified
6 88–93 UTF16 MediaWeight 1–999 The basic weight of the
paper
M
@@@
10 94–103 UTF16 Prop1 See description
No restriction
CMRType property-specific
field 1
M
12 104–115 UTF16 Prop2 See description
No restriction
CMRType property-specific
field 2
M
8 116–123 UTF16 Prop3 See description
No restriction
CMRType property-specific
field 3
M
8 124–131 UTF16 Prop4 See description
No restriction
CMRType property-specific
field 4
M
8 132–139 UTF16 Prop5 See description
No restriction
CMRType property-specific
field 5
M
16 140–155 UTF16 @@@@@@@@ Reserved: should be set to
@@@@@@@@
M
CMR Name ends here. It is composed of bytes 10–155.
8 156–163 X'0000000000000000' Reserved: should be set to
zero
M
164–end CMRData Any Resource data O
Notes:
1. M/O denotes a mandatory or optional field
2. UTF16 denotes UTF-16BE
CMR Syntax

## Page 31

CMOCA Reference 15
CMR Header Semantics
Length The length of the complete CMR, including the Length parameter. Length may be 164
(X'000000A4') bytes up to X'FFFFFFFF'.
CMRSig The signature of the CMR that allows it to be easily recognized. It will be three ASCII
characters “CMR” followed by X'39', that is, X'434D5239'.
CMRAlias Eight-character user-defined string to enable an easy way of identifying the CMR.
CMRType Five CMRTypes are defined in this Color Management Object Content Architecture. They are:
Halftone, T one Transfer Curve, Color Conversion, Link Color Conversion, and Indexed. The
value of the CMRType must be specified in the header or an exception will be generated. Note
that a Link Color Conversion CMR has two possible identifiers in this field: LK for
LinkColorConversion LUT subset and DL for ICC DeviceLink subset.
CMRVersion The CMRVersion number consists of a major version number (an integer of 1–3 digits) and a
minor version number (an integer of 1–3 digits), separated by a decimal point (. =DECIMAL
POINT=X'002E'). If the number of digits is smaller than 3, zeroes will be padded to the left side
of the major number or to the right side of the minor number. For example, if the version
number is 1.20 then the value of the CMRVersion is 001.200.
A value of “AFP .ddd” is used to specify a minor version number along with “AFP” to indicate
that the CMR is a standard Color Conversion CMR that is supported by the AFP Consortium.
The supported standard color spaces will be spaces like SWOP , CMYK, and sRGB.
A value of “generic” (X'0067 0065 006E 0065 0072 0069 0063') in this field identifies a generic
CMR. Only Halftone and T one Transfer Curve CMRs may be identified as generic. CMR data
in generic CMRs is optional and is not used in AFP color management systems.
A value of “pasthru” (X'0070 0061 0073 0074 0068 0072 0075') identifies a color space that
should not be color-converted. Only Color Conversion CMRs may be identified as
passthrough. There is no data in a passthrough CMR.
The value of the CMRVersion must be specified in the header. The CMRVersion tracks
changes besides the changes in the device-specific fields, media-specific fields, and the
CMRType property-specific
fields. It reflects changes of algorithm, toner, and so on.
CMR Header Semantics

## Page 32

16 CMOCA Reference
Device-Specific Fields
For IPDS receivers, the ManufacturerName, DeviceType, and DeviceModel values must be provided in
accordance with the IPDS description of the Product Identifier self-defining field of the XOH Obtain Printer
Characteristics (OPC) reply. Refer to the Intelligent Printer Data Stream Reference. The field descriptions are
as follows:
ManufacturerName Name of the manufacturer.
DeviceType Device type of the printer that corresponds to the device type imprinted on the serial
number plate that is physically attached to the printer.
DeviceModel Model number of the printer that corresponds to the model number imprinted on the
serial number plate that is physically attached to the printer.
For the non-IPDS devices, a maximum of five characters are allowed for the ManufacturerName. The stock
symbol (maximum five characters), a unique name assigned by stock exchanges worldwide, is recommended
to be used for the ManufacturerName. The DeviceType and DeviceModel have to be unique and meaningful
for the devices. Alternatively, the ICC Manufacture ASCII Signature and Device ASCII Signature can be used
for the ManufacturerName and the DeviceModel.
Implementation notes:
1. If the DeviceType is unspecified (@@@@@@), then it automatically matches the DeviceType of the
target device. Similarly, if the DeviceModel is unspecified (@@@), then it automatically matches the
DeviceModel of the target device. The DeviceType and DeviceModel are sometimes used by print servers
to determine which CMRs to send to the presentation device. In particular, link CMRs are targeted for a
particular device based on the DeviceType and DeviceModel of the instruction Color Conversion CMR.
Multiple link CMRs can be associated with (or mapped to) an audit CC CMR in the CMR RAT . The link
CMRs that are sent down to the device are determined by finding matches with the DeviceType and
DeviceModel of the target device. Furthermore, Generic T one Transfer Curve and Halftone CMRs can
have mapped device-specific CMRs in the CMR RAT ; such mapped CMRs are sent to the device if the
DeviceType and DeviceModel in the mapped CMR match the DeviceType and DeviceModel of the target
device. In some situations, it is acceptable to let the CMR header values for DeviceType and DeviceModel
be unspecified (@@@@@@ or @@@). For example, CMRs that will be used only as audit CMRs can
have unspecified values for DeviceType and DeviceModel. If a link CMR or a device-specific HT or TTC
CMR is associated with another CMR in the CMR RAT and does not specify a DeviceType and/or
DeviceModel, the unspecified parameter(s) match the DeviceType and/or DeviceModel of any target
printer.
2. The device types and model numbers specified in the XOH-OPC reply and in the CMR header's
DeviceType might not use the same format. For instance, for the InfoPrint 4100, the XOH-OPC reply for
the device type would be “004100” encoded using EBCDIC. In the CMR header, the DeviceType is padded
with “@” on the right. Therefore, depending on the input provided to the Installer, the CMR DeviceType field
might be “004100” or “4100@@” encoded using UTF-16BE. T ools that compare the device type in the
XOH-OPC reply and in the CMR header must be prepared to indicate a match taking into account the
differences in padding practices.
CMR Header Semantics

## Page 33

CMOCA Reference 17
Media-Specific Fields
Media-specific fields describe the media and consist of four attributes: media brightness, media color, media
finish, and media weight. The values for the MediaColor and the MediaFinish are consistent with the values
defined by the Internet Printing Protocol (IPP) of the Printer Working Group (PWG). If the target device is a
display, only media brightness is specified.
T o use an instruction CMR, its media type must match the media currently being used by the device. Similarly,
in order to use an ICC DeviceLink CMR, its media attributes must match the device's media attributes. See
“Matching Media Type of CMR With Media Type of Device” on page 112 for a discussion of this requirement.
MediaBrightness For print media, indicates the percentage of light reflected from the media. The
brightness is measured with a brightmeter machine. The scale is based on the TAPPI
GE scale in the US and the ISO scale in the rest of the world. The ISO scale is usually
about two units higher than the GE value. For example, 100 ISO brightness is
equivalent to 98 brightness on the GE scale. In order to ensure that the CMR's media
type matches the media currently being used in the device, the scale that is used to
specify each value must be the same.
For screens, the brightness is defined as the CIE standard illuminant as Zxy, where Z
is a capitalized letter, and xy is a two-digit number (see ISO/CIE 10526:1999: CIE
standard illuminants for colorimetry). For example, D50, D65, etc.
MediaColor Indicates the color of the media being specified. CMOCA-recommended values exist
to encourage interoperability; a CMOCA-recommended value should be used if
appropriate for a CMR associated with a specific media. The value “noc” means
transparency. Custom values may be defined by the administrator.
There is no restriction on what value may be entered for this field as it is not checked
for validity.
MediaFinish Indicates the surface characteristics of the media. CMOCA-recommended values
exist to encourage interoperability; a CMOCA-recommended value should be used if
appropriate for a CMR associated with a specific media. The value “no” means no
coating. Custom values may be defined by the administrator.
There is no restriction on what value may be entered for this field as it is not checked
for validity.
MediaWeight Indicates the weight of the media rounded to the nearest whole number of grams per
square meter.
CMR Header Semantics

## Page 34

18 CMOCA Reference
CMRType Property-Specific Fields
CMRType=Halftone
Note: These fields are informational only. They are not checked for validity. Any value may be entered in the
Prop fields since no error checking is done.
Prop1 Number of Device Levels
Defines the number of device levels. The number should be greater than 1. For example, if the number
of device levels is equal to 3, then the device levels are 0, 1, and 2. If the number of device levels is
different for different components, this property represents the maximum value.
Prop2 Halftone Type
Defines the halftone type. Halftone types are divided into four major categories: clustered-dot,
stochastic, dispersed, and error diffusion. The dot shape is used to specify the type of the clustered-
dot, and the error diffusion filter name is used to specify the type of error diffusion halftone.
Table 5. Halftone Types
Value Meaning
rnd@@@ (X'0072 006E 0064 0040 0040 0040') Round dot for the clustered-dot halftone
sqr@@@ (X'0073 0071 0072 0040 0040 0040') Square dot for the clustered-dot halftone
dia@@@ (X'0064 0069 0061 0040 0040 0040') Diamond dot for the clustered-dot halftone
rhm@@@ (X'0072 0068 006D 0040 0040 0040') Rhombus dot for the clustered-dot halftone
elp@@@ (X'0065 006C 0070 0040 0040 0040') Elliptical dot for the clustered-dot halftone
eud@@@ (X'0065 0075 0064 0040 0040 0040') Euclidean dot for the clustered-dot halftone
lin@@@ (X'006C 0069 006E 0040 0040 0040') Line shape dot for the clustered-dot halftone
sto@@@ (X'0073 0074 006F 0040 0040 0040') Stochastic halftone
dsp@@@ (X'0064 0073 0070 0040 0040 0040') Dispersed halftone
erd@@@ (X'0065 0072 0064 0040 0040 0040') Unspecified error diffusion halftone
f-d@@@ (X'0066 002D 0064 0040 0040 0040') Floyd-Steinberg error diffusion halftone
jjn@@@ (X'006A 006A 006E 0040 0040 0040') Jarvis-Judice-Ninke error diffusion halftone
stu@@@ (X'0073 0074 0075 0040 0040 0040') Stucki error diffusion halftone
brk@@@ (X'0062 0072 006B 0040 0040 0040') Burkes error diffusion halftone
sra@@@ (X'0073 0072 0061 0040 0040 0040') Sierra error diffusion halftone
s-a@@@ (X'0073 002D 0061 0040 0040 0040') Stevenson Arce error diffusion halftone
Prop3 Line Screen Frequency
Defines the maximum line screen frequency of all the component screens. Line frequency is specified
in terms of the printer's resolution. A line screen frequency of zero should be used for stochastic,
dispersed, and error diffusion type halftones.
Prop4 Resolution
Defines the printer resolution in dots per inch in the array width (screen width) direction. Halftone
screens are normally designed for different printer resolutions. If the resolutions differ for different
components, this property represents the maximum value.
CMR Header Semantics

## Page 35

CMOCA Reference 19
Prop5 Rotation
Defines the orientation of the halftone. There are three possible values: orientation independent, along
the scan direction, and along the process direction.
Table 6. Halftone Rotations
Value Meaning
indp (X'0069 006E 0064 0070') Orientation independent
scan (X'0073 0063 0061 006E') Scan direction
proc (X'0070 0072 006F 0063') Process direction
CMRtype=Tone Transfer Curve
Note: These fields are informational only. They are not checked for validity. Any value may be entered in the
Prop fields since no error checking is done.
Prop1 Profile/Device Class Signature
The definition of the Device Class Signature is consistent with the definition in the ICC header. There
are four basic profile/device classes: Input, Display, Output, and ColorSpace Conversion.
Table 7. ICC Profile/Device Classes for Tone Transfer Curve CMRs
Value Meaning
scnr@ (X'0073 0063 006E 0072 0040') Input Device
mntr@ (X'006D 006E 0074 0072 0040') Display Device
prtr@ (X'0070 0072 0074 0072 0040') Output Device
spac@ (X'0073 0070 0061 0063 0040') ColorSpace Conversion
Prop2 Look-and-Feel
Look-and-Feel produced in the output when this T one Transfer Curve is applied. See Appendix B,
“Generic CMR Name Registry”, on page 121 for an explanation of what these values mean.
Table 8. Look-and-Feel Values
Value Meaning
hilmid (X'0068 0069 006C 006D 0069 0064') Highlight Midtone
standd (X'0073 0074 0061 006E 0064 0064') Standard
dark@@ (X'0064 0061 0072 006B 0040 0040') Dark
accutn (X'0061 0063 0063 0075 0074 006E') Accutone
Prop3 Halftone Characterization
This T one Transfer Curve was designed to work with a particular Halftone. This value is used to
identify that Halftone. For clustered-dot halftones, it is the line screen frequency (Prop3 of Halftone).
For other types of halftones, it is the halftone type (Halftone Prop2 but just the first four characters).
Prop4 Reserved for future use.
Prop5 Reserved for future use.
CMR Header Semantics

## Page 36

20 CMOCA Reference
CMRType=Color Conversion
Note: These fields are informational only. They are not checked for validity. Any value may be entered in the
Prop fields since no error checking is done.
Prop1 Profile/Device Class Signature
It is consistent with the definition of the Profile/Device Class Signature in the ICC header.
Table 9. ICC Profile/Device Classes for Color Conversion CMRs
Value Meaning
scnr@ (X'0073 0063 006E 0072 0040') Input Device profile
mntr@ (X'006D 006E 0074 0072 0040') Display Device profile
prtr@ (X'0070 0072 0074 0072 0040') Output Device profile
spac@ (X'0073 0070 0061 0063 0040') ColorSpace Conversion profile
Prop2 Reserved for future use.
Prop3 Reserved for future use.
Prop4 Color Space of Data
It is consistent with the definition of the Color Space of Data in the ICC header. T able 10shows the
possible values.
Table 10. The ICC Color Space of Data
Value Meaning
XYZ@ (X'0058 0059 005A 0040' ) XYZData
Lab@ (X'004C 0061 0062 0040') labData
Luv@ (X'004C 0075 0076 0040') luvData
YCbr (X'0059 0043 0062 0072') YCbCrData
Yxy@ (X'0059 0078 0079 0040') YxyData
RGB@ (X'0052 0047 0042 0040') rgbData
GRAY (X'0047 0052 0041 0059') grayData
HSV@ (X'0048 0053 0056 0040') hsvData
HLS@ (X'0048 004C 0053 0040') hlsData
CMYK (X'0043 004D 0059 004B') cmykData
CMY@ (X'0043 004D 0059 0040') cmyData
2CLR (X'0032 0043 004C 0052') 2colorData
3CLR (X'0033 0043 004C 0052') 3colorData (if not listed above)
4CLR (X'0034 0043 004C 0052') 4colorData (if not listed above)
5CLR (X'0035 0043 004C 0052') 5colorData
6CLR (X'0036 0043 004C 0052') 6colorData
7CLR (X'0037 0043 004C 0052') 7colorData
CMR Header Semantics

## Page 37

CMOCA Reference 21
Table 10 The ICC Color Space of Data (cont'd.)
Value Meaning
8CLR (X'0038 0043 004C 0052') 8colorData
9CLR (X'0039 0043 004C 0052') 9colorData
ACLR (X'0041 0043 004C 0052') 10colorData
BCLR (X'0042 0043 004C 0052') 11colorData
CCLR (X'0043 0043 004C 0052') 12colorData
DCLR (X'0044 0043 004C 0052') 13colorData
ECLR (X'0045 0043 004C 0052') 14colorData
FCLR (X'0046 0043 004C 0052') 15colorData
Prop5 PCS
The profile connection space specified as either CIEXYZ (XYZ) or CIELAB (Lab), encoded as for
Prop4.
CMRType=Link Color Conversion
Note: These fields are informational only. They are not checked for validity. Any value may be entered in the
Prop fields since no error checking is done.
Prop1 Input Device ManufacturerName
The ManufacturerName for the input device. Note that the ManufacturerName for the output device is
defined in the device-specific fields of this CMR name.
Prop2 Input DeviceType
The DeviceType for the input device. Note that the DeviceType for the output device is defined in the
device-specific fields of this CMR name.
Prop3 Input DeviceModel
The DeviceModel for the input device. Note that the DeviceModel for the output device is defined in the
device-specific fields of this CMR name.
Prop4 Input Color Space
The same as Color Space of Data defined in the ICC header, encoded as in T able 10 on page 20.
Prop5 Output Color Space
Device-specific color space, a subset of the Color Space of Data defined in the ICC profile header.
Possible values are shown in the following table.
Table 11. Output Color Spaces
Value Meaning
RGB@ (X'0052 0047 0042 0040') rgbData
GRAY (X'0047 0052 0041 0059') grayData
CMYK (X'0043 004D 0059 004B') cmykData
CMY@ (X'0043 004D 0059 0040') cmyData
CMR Header Semantics

## Page 38

22 CMOCA Reference
Table 11 Output Color Spaces (cont'd.)
Value Meaning
2CLR (X'0032 0043 004C 0052') 2colorData
3CLR (X'0033 0043 004C 0052') 3colorData (if not listed above)
4CLR (X'0034 0043 004C 0052') 4colorData (if not listed above)
5CLR (X'0035 0043 004C 0052') 5colorData
6CLR (X'0036 0043 004C 0052') 6colorData
7CLR (X'0037 0043 004C 0052') 7colorData
8CLR (X'0038 0043 004C 0052') 8colorData
9CLR (X'0039 0043 004C 0052') 9colorData
ACLR (X'0041 0043 004C 0052') 10colorData
BCLR (X'0042 0043 004C 0052') 11colorData
CCLR (X'0043 0043 004C 0052') 12colorData
DCLR (X'0044 0043 004C 0052') 13colorData
ECLR (X'0045 0043 004C 0052') 14colorData
FCLR (X'0046 0043 004C 0052') 15colorData
CMRType=Indexed
Note: These fields are informational only. They are not checked for validity. Any value may be entered in the
Prop fields since no error checking is done.
Prop1 Reserved for future use.
Prop2 Reserved for future use.
Prop3 Reserved for future use.
Prop4 Reserved for future use.
Prop5 Reserved for future use.
CMR Data
Data CMR data.
The content of the data is defined by the CMR type. The CMR data carries the color resource data.
The resource data is carried in a tagged format. The tags are loosely based on the TIFF tag syntax,
but with significant changes and additions. The tag syntax is defined in Chapter 5, “CMR Data
Architecture”, on page 37.
CMR data is optional for generic and passthrough CMRs. If CMR data is specified for a generic or
passthrough CMR, it is ignored.
CMR Header Semantics

## Page 39

CMOCA Reference 23
CMR Header Exception Conditions
On encountering an error, an exception is raised. Exception conditions have a format of EC-xxxxyy. xxxx
represents the tag value. For the purposes of error reporting, the fields in the CMR header are treated as
“implied tags”. The architecture defines the tags that describe data fields to have T agIDs of X'0000'–X'FFFF'.
However, IDs in the range X'EF00'–X'EFFF' have been reserved for error handling in the CMR Header.
Currently, IDs in the range X'EFF0'–X'EFF7' are used for CMR header error codes.
The error codes (yy in EC-xxxxyy) are used as follows:
X'03' Invalid length
X'10' Invalid or unsupported field value
The exception conditions are as follows:
EC-EFF003 Invalid Length Value
The specified Length is invalid.
EC-EFF110 Invalid Field Value
The specified value for CMRSig is not X'434D5239'.
EC-EFF210 Invalid Field Value
The specified CMRType is invalid.
EC-EFF310 Invalid Field Value
The specified CMRVersion is invalid.
EC-EFF410 Invalid Field Value
The specified MediaBrightness is invalid.
EC-EFF510 Retired item 1.
EC-EFF610 Retired item 2.
EC-EFF710 Invalid Field Value
The specified MediaWeight is invalid.
CMRData Exceptions in the data are defined by the actual tags.
Prop 1–5 are informational. The values are not checked.
CMR Header Exception Conditions

## Page 40

24 CMOCA Reference

## Page 41

Copyright © AFP Consortium 2006, 2025 25
