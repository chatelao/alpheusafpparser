Chapter 6. CMR Processing
Overview of CMR Processing
CMRs are used to describe a process that:
• T akes presentation data specified in an input color space
• Converts it to the output color space of the presentation device
• Modifies the colors to create the desired output for a particular device
• Halftones the output data
The actual input and output device color spaces constrain which CMRs are applicable. There can be multiple
CMRs that are invoked, but only some of them are usable for given data.
Further, CMRs can be invoked at multiple levels of the AFP document hierarchy and it is possible to have more
than one CMR that is applicable for a given task at one time. For instance, there can be two audit color
conversions from RGB to CIELAB; one is SMPTE-C and the other is sRGB. One can be invoked at the object
level and the other at the page level. IPDS hierarchical rules and last-received-wins (when multiple conflicting
CMRs exist at the same level) are used to resolve conflicts.
Media matching also affects the hierarchical search. If the media attributes specified in the CMR header do not
match the media currently in use by the device, the hierarchical search may continue, looking for a CMR that
better matches the media. This is described in “Matching Media Type of CMR With Media Type of Device” on
page 112.
Note that there might be multiple CMRs of a given type invoked at one particular level. For instance, there
could be two audit Color Conversion CMRs attached to a GOCA object, one for CMYK input data and the other
for RGB data. Colors within the GOCA object might be specified using both color spaces and the appropriate
CMR would be used each time.
T aken as a whole, the CMR system can seem complex. But a typical situation will be simple. Some
complicated explanations will be included later to clearly define what must be done in complex situations, but
they will rarely be encountered.
When a CMR is needed, the device searches the hierarchy for an applicable CMR that applies to the current
color space. The AFP architecture hierarchy for CMRs is as follows:
1. CMR invoked with an object. Note that CMRs attached to an object received in home state are ignored and
that CMRs can be attached to an object when it is included using an IDO. (See “Color Conversion Profiles
Within TIFF , JFIF , and GIF” on page 113for a discussion of profiles embedded within the object.)
2. CMR invoked with a page or overlay
3. CMR invoked in home state
4. Device default CMR
If two applicable CMRs that both apply to the current color space are invoked at the same level, the last one
invoked is used. If no applicable CMR is explicitly invoked, a device default is used.
For color conversions, link CMRs are normally used to improve throughput. The following discussion assumes

## Page 114

98 CMOCA Reference
that there are no active link CMRs. “Link Color Conversion CMRs” on page 99, discusses Link Color
Conversion CMRs.
1. Presentation data specifies an input color space. Knowing that color space, a search is done of the invoked
audit T one Transfer Curve CMRs to find the first one that has the same number of components. If one is
not found, the identity tone transfer curve (that is, the printer default) is used.
2. Next, knowing the input color space, a search is done of the invoked audit Color Conversion CMRs to find
the first one that has that input color space. This is done by examining the Color Space Signature field
within the ICC profile header. In cases where the name of the input color space is unknown, the number of
components in the input data will be used to select a Color Conversion CMR.
3. A search of the invoked instruction Color Conversion CMRs is done to find the first one with the required
output color space. In most cases, this will be a device default CMR. Note that the audit and instruction
PCSs do not need to be the same. The device has the ability to convert between CIELAB and CIEXYZ, the
available PCSs.
4. A search is done of the invoked instruction T one Transfer Curve CMRs to find one with the correct number
of components.
5. A search is done of the invoked Halftone CMRs to find one with the right number of components.
6. The colored data is converted and halftoned using these CMRs. Note that it is possible to combine some of
the operations to improve performance.
“Applicable”, “Selected” CMRs
CMRs must be applicable in order to be used. If a CMR is not applicable, it may be ignored and no NACK is
issued. The following examples explain the meaning of applicable.
• An instruction T one Transfer Curve CMR with three components is not applicable if the device is
monochrome.
• An instruction Halftone CMR with three components is not applicable on a CMYK device.
• An audit Color Conversion CMR that converts from a three-component space is not applicable if the input
image has four components.
• An output Color Conversion CMR that converts into a four-component color space is not applicable if the
device is a CMYK printer that is running in a monochrome-appearance mode.
In order to select a CMR, the hierarchy is searched as discussed above, looking for an applicable CMR. The
first applicable CMR found in the hierarchy is selected and used.
CMR Processing

## Page 115

CMOCA Reference 99
Link Color Conversion CMRs
Link Color Conversion CMRs provide an efficient method for converting directly from the input color space to
the output color space. This is useful for optimizing performance. The next section, Link Color Conversion
CMRs Based on Audit/Instruction Color Conversion CMRs, discusses Link Color Conversion CMRs that link
audit and instruction CMRs. The section after that, “Link Color Conversions: ICC DeviceLink Subset” on page
100, shows how ICC DeviceLink CMRs are used in the hierarchy search.
Link Color Conversion CMRs Based on Audit/Instruction Color
Conversion CMRs
Link Color Conversion CMRs with subset types of X'01' (LinkColorConversion LUT) and X'02'
(LinkColorConversion Identity) are architected to require two special tags:
• Audit CMR identifier: A Link Audit CMR OID tag
• Instruction CMR Identifier: A Link Instruction CMR OID tag
When Color Conversion CMRs are sent to the device, an OID must be attached. The OID uniquely identifies
the CMR and is used to connect the audit and instruction Color Conversion CMRs with the matching link CMR.
The OID was computed from the CMR contents, using an architected algorithm that includes an MD-5
checksum. The OID format is described in the MO:DCA Registry Appendix of the Mixed Object Document
Content Architecture (MO:DCA) Reference.
The audit and instruction CMRs are identified as described above. Then a search is done of Link Color
Conversion CMRs to find a link CMR that combines the audit and instruction CMRs. This is done by comparing
the CMR OIDs with those specified in the link CMR. Note that any activated link CMR can be used. It does not
need to be invoked in order to be eligible for use.
Note, further, that an audit CMR must be identified to use a downloaded link CMR. If the audit-type information
from within an object (for example, a TIFF image) is used, there is no way to identify the link that can be
substituted. So if it is desirable to use a link CMR, an audit CMR must be attached to the TIFF object. The audit
CMR takes precedence over the ICC profile specified within the TIFF .
Link Color Conversion CMRs also require tags containing the CMR names of the audit and instruction CMRs.
These two tags are required but are for informational purposes only and are not used while selecting the link
CMR.
CMR Processing

## Page 116

100 CMOCA Reference
Link Color Conversions: ICC DeviceLink Subset
In some situations, it might be desirable to use a conversion that goes direct from input color space to output
color space without using an intermediate conversion into the PCS. For instance, a direct conversion can be
used to avoid losing information when conversions go in and out of the PCS. This might preserve information
about how much black to use, or information about a spot color. The ICC DeviceLink subset of the link CMR
can be used in this situation. Typically, the ICC DeviceLink Profile would be created in some special
customized or hand-crafted manner and be targeted at a particular device.
A link CMR with subset type of ICC DeviceLink must be invoked in order to be used. Note that other link CMRs
do not need to be invoked. The ICC DeviceLink subset can be easily identified by checking the CMRType field
in the CMR header: it will be “DL”. Since an ICC DeviceLink can be invoked at various levels of the hierarchy, a
hierarchical search must occur in order to select an ICC DeviceLink for use.
This special link CMR has precedence over audit/instruction color conversions. In the following, “Look for an
ICC DeviceLink” means that the input color space matches that of the data and the output space matches that
of the device. If multiple device links are invoked at a given level of the hierarchy, the last one invoked will be
selected.
The existence of an audit CC at some level has precedence over an ICC DeviceLink at a lower level. The
search algorithm is shown here and the figure below diagrammatically shows how to search.
• Search at the object level
– Look for an ICC DeviceLink. If found, stop and use it.
– Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the
selected pair.
• If an ICC Profile exists within the object (e.g., TIFF), use it and find an instruction CC CMR by searching all
levels.
• Else search at the page/overlay level
– Look for an ICC DeviceLink. If found, stop and use it.
– Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the
selected pair.
• Else search at higher levels
– Look for an ICC DeviceLink. If found, stop and use it.
– Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the
selected pair.
• Else use the default audit CC CMR
– Note that there is no default ICC DeviceLink CMR.
– Find an instruction CC CMR by searching all levels. Use the selected pair.
CMR Processing

## Page 117

CMOCA Reference 101
Figure 11. Selecting Appropriate Color-Conversion CMRs
Data-Object
Level ICC
DeviceLink
CMR
Inside Data
Object
Page/Overlay
Level
Home-State
Level
Default
Level
Note: ICC DeviceLink Profiles are not embedded within data objects.
Rendering intent is not used to select ICC DeviceLink CMRs.
CMR-Usage Hierarchy for color conversion CMRs
ICC
DeviceLink
CMR
ICC
DeviceLink
CMR
If ICC DeviceLink
is supported, this
path is followed.
If ICC DeviceLink
is not supported, this
path is followed.
Audit CC
CMR
Audit CC
CMR
Audit CC
CMR
ICC Profile
Audit CC
CMR
Note that the CMR media attributes of an ICC DeviceLink must match the media attributes of the device. This
means that the process defined in “Matching Media Type of CMR With Media Type of Device” on page 112
should be followed. If some of the attributes do not match and the hierarchy search continues, the hierarchy
search will continue normally as if the CMR that does not match were never found.
Rendering Intent is not used when selecting an ICC DeviceLink for use. An ICC DeviceLink represents exactly
one rendering intent that is specified in the header of the ICC Profile. If an ICC DeviceLink is selected for use
during a hierarchical search, it is used regardless of whether the currently active rendering intent matches the
rendering intent of the profile.
CMR Processing

## Page 118

102 CMOCA Reference
Audit/Instruction/Link Color Conversion CMRs
An audit Color Conversion CMR describes device-dependent colors or non-device colors (for example, sRGB)
in the presentation data. It provides a way of converting from the input color space to a profile connection
space (PCS).
An instruction Color Conversion CMR provides a way of converting from the PCS to the device output color
space. Note that only certain instruction CMRs are applicable for a given device. For instance:
• If the device is a CMYK printer, only tone transfer curves with 4 components are applicable.
• Some devices support only bilevel halftone screens, not multilevel screens.
• If the device is an RGB display, it requires color conversions that convert into the RGB of the display.
The device may ignore any instruction CMRs that are not applicable, thus making the search path shorter.
Similarly, an ICC DeviceLink CMR must have an output color space that matches the color space of the device.
If it does not match, the device may ignore it.
T able 49shows which ICC profiles may be used for audit CMRs and which may be used for instruction CMRs.
T able 12 on page 28 gives more information about the profiles.
Input type profiles describe colors coming from a scanner or digital camera and therefore they are not used as
instruction CMRs.
Table 49. Profile Subsets in Audit and Instruction Color Conversion CMRs
Type Audit Instruction
Monochrome input profile Yes No
Monochrome display profile Yes Yes
Monochrome output profile Yes Yes
Three-component matrix-based input profile Yes No
Three-component matrix-based display profile Yes Yes
N-component LUT-based input profile Yes No
N-component LUT-based display profile Yes Yes
N-component LUT-based output profiles Yes Yes
ColorSpace conversion profile Yes No
CMR Processing

## Page 119

CMOCA Reference 103
Creating Link Color Conversion CMRs – LinkColorConversion LUT
subset
A link CMR describes how to convert directly from an input color space to an output device color space. It links
an audit and an instruction CMR. It will define four color conversions, one for each of the possible rendering
intents. Both the audit and the instruction CMR have rendering intents specified in their ICC header. The
rendering intent in the ICC profile header of the instruction CMR becomes the Default Rendering Intent of the
link CMR.
The link creation process combines the audit and instruction CMRs and creates four LUT s. Each LUT of the
link CMR collapses all the steps of the two (audit and instruction) color conversions into a single
multidimensional lookup table. It is possible that some of the LUT s will be identical if not enough information
exists to create all versions separately. In this case, the offset for the LUT s could be the same.
T o create each link LUT , the appropriate color conversion based on rendering intent must be selected from the
ICC profiles for both the audit and the instruction Color Conversion CMRs. T o create each one of the four link
LUT s:
1. The rendering intent of the particular link LUT being created is identified.
2. The color conversion corresponding to that rendering intent is selected from the audit CMR.
3. The color conversion corresponding to that rendering intent is selected from the instruction CMR.
4. The selected audit and instruction color conversions are combined to produce the link LUT for that
rendering intent.
The selection is based on the rendering intent of the link LUT currently being created and the goal is to use the
color conversion from both the audit and the instruction CMR that is for that particular rendering intent.
When the color conversion rule for one of the rendering intents is not available, another color conversion must
be used. The substitution methods are discussed below.
The basic rules for selecting a color conversion that corresponds to a given rendering intent for a given ICC
profile are as follows:
1. If the appropriate AT oBxT ag (or BT oAxT ag) for that rendering intent exists, it is used. (AT oB0T ag is used for
perceptual, AT oB1T ag is used for media-relative colorimetric, AT oB2T ag is used for saturation.)
2. If that tag does not exist, the rendering intent in the header of this ICC profile is noted. The tag
corresponding to this rendering intent is used.
3. If that tag does not exist, the implementation is device-dependent.
For audit CMRs, only the AT oBxT ags are considered, not the BT oAxT ags. If no AT oBxT ags exist:
• For monochrome profiles, the grayTRCT ag is used for all rendering intents.
• For three-component matrix-based profiles, the matrix/TRC combinations are used for all rendering intents.
For instruction CMRs, only the BT oAxT ags are considered, not the AT oBxT ags. If no BT oAxT ags exist:
• For monochrome profiles, the inverse of the grayTRCT ag is used for all rendering intents.
• For three-component matrix-based profiles, the inverse of the matrix/TRC transformation, as described in the
ICC specification, is used for all rendering intents.
In order to create the link LUT for ICC-absolute colorimetric rendering intent, the following are combined:
• From the audit CMR, use the same tag(s) as were used when creating the link LUT for media-relative
colorimetric. Apply a white point conversion based on the audit CMR's ICC mediaWhitePointT ag.
CMR Processing

## Page 120

104 CMOCA Reference
• From the instruction CMR, use the same tag(s) as were used when creating the link LUT for media-relative
colorimetric. Apply a white point conversion based on the instruction CMR's ICC mediaWhitePointT ag.
CMR Processing

## Page 121

CMOCA Reference 105
Tone Transfer Curve and Printer Calibration
Processing of color information involves:
1. Converting color into the device color space (CMYK on normal printers)
2. Modifying the color for each plane
3. Halftoning each plane
The second step, modifying the color for each plane, is typically a one-dimensional conversion and is
represented as a 1-D array called a curve. The curve would actually be a set of curves, one for each of the
color planes in the color space. This document will use the term “curve” to represent the whole set of curves.
Actually, there can be multiple curves, each performing a different function. The curves are used sequentially
although, in practice, they might be concatenated to form one curve for improved performance. The effect of
each curve is a “delta” to the previous curve.
There are two basic uses of these modifying curves:
• There must be some way to put the device into a well-known state and maintain it in that state. This well-
known state should be the state it was in when the default instruction Color Conversion and Halftone CMRs
were created.
• The user might want to control the look-and-feel of the output.
Each device may handle these processes in different ways, but the following describes one way of dealing with
this complexity. Note that many devices will not have all these options available or will describe them with
different terms. There are four curves to be applied sequentially in this example:
Tone Transfer Curve (TTC) – This curve is contained in a CMR and can be specified by the user to modify the
behavior of a device so that a desired relationship between input and output is achieved. This could include ink
limiting, linearization, lightness, contrast, the relationship between the highlights, midtones and shadow
regions, or even reverse-video. Only one T one Transfer Curve is used during the processing of a color object.
The T one Transfer Curve that is used can vary with each color object. It is selected using the rules of the
CMOCA hierarchy. If no T one Transfer Curve is specified, the printer default (the identity) will be used.
Operator Requested Curve – This curve allows the user the same control of the look-and-feel of the output as
the T one Transfer Curve. However, this curve is controlled by input from the printer console and is not specified
in the data stream. The Operator Requested Curve will be constant for a complete print job or larger boundary.
Tone Reproduction Curve – This curve is used to put the printer into a known state. It compensates for dot
gain, printer characteristics, typical humidity, paper characteristics, ink/toner characteristics, speed, etc. There
might be different T one Reproduction Curves in one printer for printing on different sides, different engines, or
different media. The assumption is that, after applying the T one Reproduction Curve, the device is in an
optimal state.
Calibration Curve – This curve is used to modify the behavior of a device, returning it to a known state. The
assumption is that this known state is the optimal state. The Calibration Curve might be something controlled
by the operator or might be automatically controlled within the printer. Changes to the Calibration Curve might
need to occur relatively frequently due to changing ink/toner characteristics and changing humidity. The
Calibration Curve might be different for each T one Reproduction Curve in the printer.
The T one Transfer Curve (TTC) and the Operator Requested Curve perform essentially the same function, but
the first is transmitted in the data stream and the second is controlled via the printer's user interface. Normally,
the two curves would be applied sequentially. However, in some cases, the device might want the Operator
Requested Curve to override the TTC, effectively ignoring the TTC. If an applicable TTC is ignored, an error
condition exists that is governed by exception ID X'025E..05'. This exception signifies that an “invoked,
selected CMR was not used”. The effect of this exception is controlled by the Color Fidelity Triplet and by Error
Handling Control (EHC). The Color Fidelity Triplet or EHC can allow processing to continue by using a device
default (identity) TTC or can force processing to stop. Reporting of the NACK is also controlled by the Color
CMR Processing

## Page 122

106 CMOCA Reference
Fidelity Triplet or the EHC. Thus, by using the Color Fidelity Triplet or EHC, the user can control whether the
Operator Requested Curve is allowed to override the TTC.
Note that the above discussion assumes that the printer calibration is done digitally, in software, before the
color is halftoned. It is also possible to mechanically control the output after it is halftoned. For instance, it
might be possible to regulate the amount of ink emitted by an inkjet. This control could be used instead of the
Calibration Curve.
CMR Processing

## Page 123

CMOCA Reference 107
Use of Indexed CMRs
Indexed CMRs provide rules about how to render indexed colors. Indexed CMRs apply to indexed colors that
are specified using the Highlight color space. They do not apply to indexed colors found within PostScript or
other non-IPDS data objects.
For Indexed CMRs, both instruction and audit processing modes are valid. However, only Indexed CMRs with
a processing mode of instruction will be used. Indexed CMRs that have an audit processing mode specified
are ignored.
The tags in the Indexed CMR allow the CMR to use various color spaces in the descriptions. These color
spaces can be grayscale (monochrome), named colorants, RGB, CMYK, or CIELAB. A conversion from the
index into CIELAB must always be provided. If a conversion into another color space is provided, it is used
when applicable. For instance, if a conversion into CMYK is provided and the device is a CMYK printer, the
conversion is used. The CMYK is assumed to be the device's CMYK and no color conversion CMRs are used.
However, if a conversion into RGB is specified for that same CMYK printer, it is not applicable and the
conversion into CIELAB will be used instead. In this case, the instruction Color Conversion selected from the
hierarchy is used to convert the CIELAB into the output space of the device.
If the color palette is given in terms of named colorants, and some of the colorants required to produce a
particular index are not available in the device, then the CIELAB palette information will be used instead of the
named colorant information.
Indexed colors will ultimately be rendered in one of two color spaces:
1. The output color space of the device (typically CMYK for printers and RGB for displays)
2. A named colorant space, which could include
spot colors that are available in the printer and/or colorants
from the output color space of the device .
In the first case, the T one Transfer Curve CMR and the Halftone CMR selected from the hierarchy for the
output color space are used. In the second case, the number of component named-colorants is noted. The
hierarchy is searched for a T one Transfer Curve and a Halftone that have this same number of components
and the CMRs that are found are used.
The Indexed CMR to be used is selected using the normal hierarchical rules. Media-matching rules also apply.
When the Indexed CMR is selected, its palettes are searched for the index in question. If the index is not
found, IPDS exception processing is performed. No attempt is made to look for the index in any other Indexed
CMR.
If a highlight color index is specified in the data stream, but cannot be resolved by an Indexed CMR, IPDS
exception ID X'020E..03' is used. This can occur in two situations:
• No host-invoked Indexed CMR is found in the hierarchy.
• The required index is not found in the Indexed CMR that was selected.
CMR Processing

## Page 124

108 CMOCA Reference
Allowed Processing Modes
There are three possible processing modes: audit, instruction, and link. Only certain processing modes are
allowed with each specific type of CMR. An exception occurs if the processing mode is not valid for the CMR
type. The following table shows which processing modes are valid. In addition, the device should ignore
(without causing an exception) certain types of CMRs. That is also shown in the table.
A CMR is generic if the CMRVersion in the CMR Header is “generic”. Only T one Transfer Curve CMRs and
Halftone CMRs have registered generic versions, so if the type is anything else, an exception occurs.
Table 50. Allowed Processing Modes
CMR Type
Processing Mode
Non-Generic CMRs Generic CMRs
Audit Instruction Link Audit Instruction Link
Color Conversion valid valid invalid - error invalid - error
Tone Transfer
Curve
valid valid invalid - error valid - ignored 1 valid invalid - error
Halftone valid - ignored valid invalid - error valid - ignored 1 valid invalid - error
Link Color
Conversion
invalid - error invalid - error valid invalid - error
Indexed valid - ignored valid invalid - error invalid - error
Notes:
1. Generic T one Transfer Curves and generic Halftones are ignored because, in order to replace a generic CMR with a
specific CMR, you must know the targeted device. This is unknown for an audit CMR.
A CMR is passthrough if the CMRVersion in the CMR Header is “pasthru”. Only Color Conversions may be
passthrough so, if the CMR type is anything else, an exception occurs. The mode of a passthrough CC CMR
must be audit. The CMR is ignored if the mode is specified as instruction. If the mode is link, an exception is
generated since that is not valid for any Color Conversion CMRs.
CMR Processing

## Page 125

CMOCA Reference 109
Device Default CMRs
Every device must supply both audit and instruction default CMRs.
Default Instruction CMRs
The required default instruction CMRs are used to process colors in the color space of the device. If the device
has multiple appearances—for instance, a printer that can function as both a full-color printer and a
monochrome printer—then defaults for both identities must be available in the device.
For optimum quality, the device should have default instruction CMRs that are tuned for each type of media
that it supports. For instance, a printer that officially supports three different paper types would have three
different Color Conversion instruction CMRs available. It is possible that some media have different
characteristics on the two sides. In this case, the device would have a default Color Conversion CMR for each
side.
In some cases, a printer controller might be ripping pages that are sent to one of several print engines. It might
be doing load-balancing among the engines. In this case, the printer controller would have a default Color
Conversion CMR for each engine, and possibly for each media type on each engine.
The default Halftone and T one Transfer Curve CMRs might also differ depending on the media or engine.
The device is responsible for supplying the following device default instruction CMRs:
• An instruction Halftone CMR that takes the device color space as input.
• An instruction T one Transfer Curve CMR.
• An instruction Color Conversion CMR from an ICC Profile Connection Space (CIEXYZ or CIELAB) to the
device color space. The device must have the ability to convert between CIEXYZ and CIELAB or else it must
supply color conversions into the device output space from both. This CMR should have profiles for all
rendering intents.
• There are no default Indexed CMRs.
The default instruction Color Conversion CMRs can be used when constructing Link Color Conversion CMRs.
Therefore, the device manufacturer “publishes” these CMRs. OIDs for these CMRs are created by both the
device and the host system wishing to create link CMRs using them. The OIDs are the same in both situations.
Thus, the link CMR successfully matches the device's default CMR.
Note that printer default CMRs are used only when no other applicable CMR is invoked in the hierarchy. In
some cases it might be desirable to ensure that the device uses its defaults since it might have knowledge that
the application does not have. For instance, if the sides of the paper have different characteristics, then the
device might have different default Color Conversion CMRs for each side. The device will know which side it is
printing on and should select the appropriate color conversion.
T o force the printer to choose an applicable default instruction CMR, no applicable CMR of that type should be
invoked within the IPDS hierarchy. The printer may not use its default to override an applicable CMR that is
invoked at any level of the hierarchy.
CMR Processing

## Page 126

110 CMOCA Reference
Default Audit CMRs
Audit CMRs are not dependent on the output device or the media, so only one default in each category is
required.
The device is responsible for supplying the following device default audit CMRs:
• An audit Color Conversion CMR from input RGB to CIEXYZ or CIELAB.
– A display may assume that the RGB is its device RGB.
• An audit Color Conversion CMR from input CMYK to CIEXYZ or CIELAB.
– A full-color printer may assume that the CMYK is its device CMYK.
• An audit Color Conversion CMR from input grayscale to CIEXYZ or CIELAB.
• An audit Color Conversion CMR from YCrCb or YCbCr to CIEXYZ or CIELAB.
• Audit T one Transfer Curve CMRs that apply for varying number of components.
If any other color spaces such as Luv, HSV, HLS, Yxy, or CMY are used within the data stream, the application
is responsible for providing a color conversion CMR. The device may generate an exception if an applicable
Color Conversion CMR is not supplied.
The following audit CMR defaults have been architected:
• RGB:
– In an AFP printer, this is SMPTE-C RGB.
– In a display, this represents its device RGB.
• CMYK:
– In an AFP full-color printer, this represents the CMYK of the presentation device.
– In an AFP monochrome printer, this is SWOP CMYK.
– In an AFP full-color printer working in grayscale mode, the CMYK represents the CMYK of the device
when it is in full-color mode.
– In a display, this is SWOP CMYK.
• Grayscale:
– The Grayscale default applies when the color space has one component and more than 1 bit per pixel.
This includes color spaces that are specified as YCbCr where only the Y component is present.
– In an AFP full-color printer, C = M = Y = 0; K = 1 – gray_value, where K ranges from 0 (no black) to 1
(maximum black) and gray_value ranges from 0 (maximum black) to 1 (no black)
.
– In an AFP monochrome printer, the grayscale is the device's grayscale.
– In a display, R = G = B = gray_value, where gray_value ranges from 0 (maximum black) to 1 (no black) .
• YCbCr or YCrCb:
– The default YCbCr is based on CCIR Recommendation 601-1 but components are normalized so as to
occupy the full 256 levels of an 8-bit encoding. This version of YCbCr is used by TIFF and JFIF as their
default. The equations below are consistent with the TIFF 1 and JFIF2 specifications. YCbCr is first
converted to RGB using the procedure below and the resulting RGB is then converted to the profile
connection space. The audit Color Conversion CMR selected from the hierarchy is used to convert the
RGB into the profile connection space.
CMR Processing
1. TIFF 6.0 Specification Section 21: YCbCr Images
2. JPEG File Interchange Format V1.02 (Sept. 1992)

## Page 127

CMOCA Reference 111
– Y , Cb, and Cr are in the range 0 to 255 as they would be in a digital image. Cr and Cb are shifted from the
range 0 to 255 of a digital image into the range –128 to 127 in the equations below by subtracting 128.
– The procedure for converting a YCbCr value (256 levels) to a gamma-corrected RGB value is:
R = Y + 1.402 (Cr – 128)
G = Y - 0.34414 (Cb – 128) - 0.71414 (Cr – 128)
B = Y + 1.772 (Cb – 128)
R,G,B values should be clipped to the range [0,255] or [0.0,1.0] depending on the number system being
used.
• Note that the default white point for both CIELAB and CIEXYZ is D50.
• Halftone: No architected default.
• T one Transfer Curves: Identity.
• Indexed: No architected default.
Default CMRs to Replace Generic CMRs
Instruction Halftone CMRs and instruction T one Transfer Curve CMRs can be generic. Generic CMRs must be
replaced by device-specific CMRs. The device is required to have its own device-specific versions of all the
generic CMRs that are registered in the Color Management Object Content Architecture (see Appendix B,
“Generic CMR Name Registry”, on page 121). If the device does not recognize a generic CMR name, it NACKs
using exception ID X'025E..04'.
Passthrough Audit Color Conversion CMRs
An audit Color Conversion CMR can specify the version to be “pasthru”. Passthrough CMRs are defined only
for Color Conversion CMRs. Prop4, the color space for a CC CMR, should be specified. When this
passthrough CMR is invoked and Prop4 is the same as the color space of the device, then the color values will
be passed through without color conversion. If Prop4 is not the same as the device color space, or not
specified, then the passthrough CC CMR is ignored.
The CMRVersion in the CMR header indicates whether a CMR is passthrough. Prop4 in the header indicates
the color space. Other Prop fields are unspecified. A passthrough Color Conversion is valid only as an audit
CMR. It is ignored if its mode is instruction. An error is returned if the mode is link.
A passthrough CC CMR is treated like other audit CC CMRs in terms of selecting an audit CC CMR from the
hierarchy. A passthrough CC CMR has no data. There is no device-specific CMR that can be substituted for
the passthrough CC CMR. It merely instructs the device to avoid doing any color conversion.
CMR Processing

## Page 128

112 CMOCA Reference
Matching Media Type of CMR With Media Type of Device
In some cases, it is important to know if a CMR is media-specific. It is media-specific if the media is completely
specified. It is not media-specific in the following cases:
• If the target device is a display screen and the MediaBrightness is not specified or is not a valid entry. (Z is a
capitalized letter; x and y are digits.)
• If the target device is a printer and one or more of the four media fields in the header are not specified or are
invalid.
When an instruction CMR is needed, the hierarchy is searched to find an applicable CMR. There are two
possible outcomes:
• An applicable CMR is found in the hierarchy and is selected.
• No CMR is selected from the hierarchy and a default CMR must be used.
Note that the following discussion also applies to link CMRs with subset being ICC DeviceLink.
In the latter case, the device should attempt to select a default CMR whose media specification matches the
media being used. If there is no default CMR found that exactly matches the media being used, the device
selects the best existing default CMR.
In the former case, when an applicable CMR is selected from the hierarchy, then its media characteristics are
examined to determine whether it should be used.
1. Assume the selected CMR is media-specific.
• If all the media field values in the CMR match the media values current in the device, the CMR is used.
(For printers, all four media fields must match. For displays, only the MediaBrightness is considered.)
• If one or more of the media fields do not match the current media, the device searches the hierarchy for a
media-specific CMR that matches the current media. Multiple applicable CMRs might exist at each level
of the hierarchy and are included in the search, and each level of the hierarchy is searched in the normal
order, except for the device default level that is not part of the search. If no matching media-specific CMR
is found, then IPDS exception handling rules for CMR exceptions (ID X'025E..03') should be followed.
2. If all of the media fields in the CMR are unspecified, then the CMR is used regardless of the media in the
device. This provides the user with a means of ensuring the use of his chosen CMR.
3. If one or more of the media fields is invalid, then IPDS exception handling rules for CMR exceptions should
be followed.
4. Assume some, but not all, of the media fields are specified and the specified fields are valid.
• If all the media fields that are specified in the CMR match the media that is currently in the device, the
device searches the hierarchy for a CMR whose media attributes are a better match with the current
media. Multiple applicable CMRs might exist at each level of the hierarchy and are included in the
search, and each level of the hierarchy is searched in the normal order, except for the device default
level that is not part of the search. If a better matching CMR is not found, the original CMR is used.
• If any of the media fields that are specified in the CMR do NOT match the device, then the device
searches the hierarchy for a CMR whose media attributes do match the current media. Multiple
applicable CMRs might exist at each level of the hierarchy and are included in the search, and each level
of the hierarchy is searched in the normal order, except for the device default level that is not part of the
search. If no CMR is found whose attributes match the current media, then IPDS exception handling
rules for CMR exceptions (ID X'025E..03') should be followed.
CMR Processing

## Page 129

CMOCA Reference 113
Treatment of Named and Highlight Colors
The AFP architecture supports OCA named colors such as Blue, Red, and Brown that are inherently device-
dependent. As such, these named colors should not be used when an exact color is required. AFP architecture
has recommended RGB values for each OCA named color. It is recommended that these RGB values be
interpreted as SMPTE-C values and mapped to a device's output color space. Note that there is no architected
method for associating an audit color conversion CMR with the named OCA colors via the CMR hierarchy.
It is recommended that OCA Black (X'0008'), presented on a CMYK output device, be rendered as C=M=Y=
X'00' and K=X'FF'.
The AFP architecture also supports a Highlight color space. It can be used in two ways:
• The highlight color number specifies the spot color to use. The range is X'0000'–X'00FF'.
• The highlight color number is interpreted as an index into a palette. The range is X'0100'–X'FFFF'. The
Indexed CMR describes which colorants are used to render that color.
When the highlight color is interpreted as a spot color, no mechanism for converting this highlight color to the
device's output color space is provided by the Color Management Object Content Architecture. However, if an
application wants to assign a particular mix of colors to some highlight color, it can use a Color Mapping T able.
The section “Use of Indexed CMRs” on page 107 describes how other CMRs are used in conjunction with an
indexed color.
Color Conversion Profiles Within TIFF , JFIF , and GIF
Some presentation data objects contain internal color management information. ICC profiles can be embedded
within TIFF , JFIF , and GIF .
When presentation data objects contain internal color management information, the device will use internal
audit-like color management information, if any, when no applicable audit CMR is invoked with the object. The
object-level audit CMR has priority over internal color management information. However, the internal color
management information has priority over any audit CMRs at the page, home state, or device-default levels.
So the hierarchy is:
1. Object-level audit CMR
2. ICC profile within the object
3. Page-level audit CMR
4. Homestate-level audit CMR
5. Printer default audit CMR
The same rules hold for tone transfer curves that can be found within the object.
All internal instruction-like color management information is ignored.
An ICC DeviceLink profile cannot be specified within the object, according to the ICC color profile specification.
Link Color Conversion CMRs should be applied when processing TIFF , JFIF , and GIF if the device reports
support for Link CC CMRs (IPDS STM property pair X'E001').
In some cases, some color conversion controls can be specified within the TIFF , JFIF , GIF , EPS, or PDF . For
instance, a white point or a grayscale correction curve can be specified. Where these are part of the definition
of the input color space, they are used prior to applying any other color conversion, regardless of whether that
color conversion is specified within the object or within a CMR.
CMR Processing

## Page 130

114 CMOCA Reference
CMR Usage Within EPS, PDF
Audit Color Conversions
PostScript supports the following input color spaces:
• Device-Independent Color Spaces
– CIEBasedABC
– CIEBasedA
– CIEBasedDEF
– CIEBasedDEFG
• Device-Dependent Color Spaces
– DeviceGray
– DeviceRGB
– DeviceCMYK
• Special Color Spaces (They add features or properties to an underlying color space.)
– Pattern
– Indexed
– Separation
– DeviceN
Device-independent color spaces include a specification of how to convert from that input space to the CIEXYZ
connection space. An Audit Color Conversion invoked at the object level should override device-independent
color spaces that specify the same color space if a device reports that it can reliably apply CMRs to EPS/PDF
(IPDS STM property pair X'E100').
Architecture note: It might not be absolutely clear if the color space specified in the CMR is the same as that
being specified by the device-independent color since only the number of components is known about
the PostScript color space. Other information in the data stream might make it clear whether the color
spaces match.
Device-dependent color spaces are not clearly specified. There are default simple rules that can be used to
convert from one to another. They sometimes do not produce optimal output quality. These color space
definitions will be overridden by audit Color Conversion CMRs. However, PostScript LanguageLevel3 supports
the UseCIEColor parameter. This parameter allows selected device-dependent color spaces to be
systematically transformed into a device-independent CIE-based color space. If this parameter is used within
the EPS/PDF object to define a particular color space, the audit Color Conversion CMR will NOT override the
definition of that color space.
Special color spaces are not directly affected by audit Color Conversion CMRs. However, if a device-
dependent color space is modified by a CMR as in the preceding paragraph, and the special color space uses
that as the underlying color space, then the color of the special color space will be modified.
ICC profiles can be embedded in EPS/PDF . An audit Color Conversion CMR invoked at the object level should
override the ICC profiles that specify the same color space if a device reports that it can reliably apply CMRs to
EPS/PDF (IPDS STM property pair X'E100').
Instruction Color Conversions
The appropriate printer-default instruction Color Conversion CMR will be used for the default Color Rendering
Dictionary (CRD).
• If a CRD is specified within the PostScript data stream, it will be used instead of the default CRD.
CMR Processing

## Page 131

CMOCA Reference 115
• If an instruction Color Conversion CMR is associated directly with the EPS/PDF object, it will override both
the default CRD and any CRD specified within the EPS/PDF object.
Link Color Conversion CMRs
Link Color Conversion CMRs should be used when processing EPS/PDF if a device reports support for Link
CC CMRs (IPDS STM property pair X'E001') and it reports that it can reliably apply CMRs to EPS/PDF (IPDS
STM property pair X'E100').
Halftones
The appropriate printer-default instruction Halftone CMR will be used for the default EPS/PDF halftone.
• If a halftone is specified within the EPS/PDF object, it will be used instead of the default EPS/PDF halftone.
• If an instruction Halftone CMR is associated directly with the EPS/PDF object, it will override both the default
halftone and any halftone specified within the EPS/PDF object.
Note that these rules will be applied only if the PostScript RIP runs in bilevel mode. When the RIP runs in
multilevel mode, which means that the RIP output specifies 8-bit intensity values, any halftone operations in
the PostScript data stream will be ignored.
Audit Halftone CMRs have no effect on EPS/PDF processing.
Tone Transfer Curves
The printer default instruction T one Transfer Curve is the identity so it will have no effect on EPS/PDF .
• If a Transfer Function is specified within the EPS/PDF object, it will be used.
• If an instruction T one Transfer Curve CMR is associated directly with the EPS/PDF object, it will override any
Transfer Function specified within the EPS/PDF object.
Audit T one Transfer Curve CMRs have no effect on EPS/PDF processing.
CMR Processing

## Page 132

116 CMOCA Reference
Caveat
Some devices cannot completely, reliably ensure that a selected CMR is actually applied. This is because
some EPS/PDF objects can be created in such a way that it is not possible to override the parameters. Other
devices can reliably, predictably apply the CMRs during EPS/PDF processing.
Property pair X'E100' in the IPDS STM reply indicates that CMRs can be reliably applied to all EPS/PDF
objects. If a device cannot guarantee that CMRs will always be predictably applied, it does not report X'E100'
in the STM reply. When using such a device, it is recommended that an application that uses CMRs with EPS/
PDF objects should test the output to verify that the CMRs are applied as expected.
Implementation notes
The following are some suggestions of possible ways to apply CMRs to a EPS/PDF object. A PostScript
preamble could be used to set up parameters for the PS RIP and/or post-processing work could be done.
In order to specify CMR color conversions for CMYK and RGB, UseCIEColor can be used. This requires a PS
RIP that is level 3. Note that any UseCIEColor operations that come later in the PostScript data stream will
override the operations in the preamble. This is correct implementation.
The settransfer or setcolortransfer operators can be used to implement the T one Transfer Curve CMR.
PostScript Type 3 Halftone Dictionaries can be used to implement Halftone CMRs that are bilevel threshold
arrays. Halftone CMRs that are multilevel or error diffusion have no direct counterpart in standard PostScript
Language. If it is necessary to implement these types of halftones, the PostScript RIP could output 8-bit color
values and the product RIP could perform the screening as a post-processing activity. It might also be
possible to redefine some of the standard PostScript dictionary entries or operators to enable halftoning within
the PostScript RIP .
For both halftones and TTCs, the PostScript operator should be redefined at the end of the preamble so that
no halftones or transfer functions specified within the PostScript data stream will override those specified in
the preamble.
Different Encodings of CIELAB
CIELAB is defined as follows:
• L has a value between 0.0 and 100.0
• a has a value between –128.0 and 127.0
• b has a value between –128.0 and 127.0
The encoding for L consistently maps the range [0.0, 100.0] to [X'0000', X'FFFF'] or to [X'00', X'FF'] depending
on the number of bytes in the representation. The only exception to this is ICC profile legacy 16-bit encoding
that maps to [X'0000', X'FF00']. Consult the ICC profile specification for more information about this.
Different architectures convert the range for a and b into 1-byte or 2-byte values differently.
• CMRs encode a and b by mapping [–128.0, +127.0] into the range [X'00', X'FF'] or [X'0000', X'FFFF']. Thus,
–128.0 is represented by X'0000'. The hex values are treated as unsigned integers. These values are used
in the Indexed CMR and also when using a link CMR that converts from an input space of CIELAB.
• AFP treats the hex values as signed integers. Further, it specifies that the mapping depends on the number
of bits. If eight bits are used, the range –128 to +127 is mapped to the range X'80' to X'7F', i.e., –128 is
represented by X'80', and +127 is represented by X'7F'. If fewer than 8 bits are used, treatment of the most
negative binary endpoint for the a and b components is device-dependent. More than 8 bits are not allowed.
CMR Processing

## Page 133

CMOCA Reference 117
• The ICC profile specification specifies that [–128.0, +127.0] maps into the range [X'00', X'FF'] or [X'0000',
X'FFFF'], which is the same encoding used by CMRs. In addition, there is a special case with 16-bit ICC
encoding where “legacy” encoding is used. Consult the ICC profile specification for more information about
this.
• In TIFF , [–127.0, +127.0] maps into [X'80', X'7F']. Thus, TIFF is treating the hex values as signed integers,
but not exactly the same as IPDS does.
Care must be taken when using values from the different architectures to ensure that they are converted to a
common encoding.
Implications for Drivers
It is preferable for the host to suppress the downloading of CMRs that are not applicable to the device, but this
is not required. For instance, instruction Halftone CMRs for a three-component color space are not useful on a
CMYK printer.
Generic CMRs Versus Device-Specific CMRs
Certain instruction CMRs can be generic. Generic CMRs are defined for only two CMR types: Halftone CMRs
and T one Transfer Curve CMRs. The generic CMR must always be replaced by a device-specific CMR by
either the server or the device. Appendix B, “Generic CMR Name Registry”, on page 121 lists the registered
generic CMR names for the Halftone CMR type and the T one Transfer Curve CMR type, with brief descriptions
of the intended appearance of each.
An example of a generic CMR is a T one Transfer Curve CMR that specifies a “highlight-midtone” output
appearance. When the reference to the generic CMR is processed at print time, the generic CMR is mapped to
a specific highlight-midtone T one Transfer Curve CMR for the target device. The print server then downloads
and activates the device-specific CMR in place of the generic CMR.
In cases where the mapping from generic to device-specific was not done by the print server, the conversion
must be done in the presentation device. The device is required to have device-specific CMRs for each of the
registered generic CMRs.
The CMRVersion in the CMR header indicates whether a CMR is generic so that a device can recognize when
a mapping must be done. Appendix B, “Generic CMR Name Registry”, on page 121 indicates which fields of
the CMR header are used to represent the generic information needed to map from generic to specific. Other
fields are unspecified.
If a generic CMR is received and the device is unable to map it to a device-specific CMR, an error condition
exists (exception ID X'025E..04').
Partial Support of TTC and HT CMRs
In some cases, a device provides only partial support for certain types of CMRs. The device needs to declare
support in the IPDS STM reply to enable the host to transmit the CMR to the device. The device might receive
a CMR that is not supported and, if so, it should use the X'025E..05' exception (invoked, selected CMR was not
CMR Processing

## Page 134

118 CMOCA Reference
used). This exception is controlled by Error Handling Control and by the Color Fidelity triplet. Some situations
where a CMR is not used include:
1. An HT or TTC is supported at the job level but not supported at the page or object level. For instance, the
HT and/or TTC is applied in hardware or in software after a complete sheet is created.
2. A device does not support all subsets of halftones. For instance, a laser printer might be able to use a
threshold array but not an error diffusion halftone.
3. A limited number of HT s or TTCs are supported on one sheet. If a CMR is invoked after the maximum
number is reached, exception X'025E..05' should be used.
Note that exception handling should not be used with generic CMRs in cases where partial support affects the
selection of the device specific CMR that is used. The device is substituting as best it can and no exception
handling is required.
CMR Processing

## Page 135

Copyright © AFP Consortium 2006, 2025 119
