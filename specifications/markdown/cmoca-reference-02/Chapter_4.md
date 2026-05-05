Chapter 4. CMR Types
The following CMRTypes are defined:
• Halftone
• T one Transfer Curve
• Color Conversion
• Link Color Conversion
• Indexed
Each of the CMRTypes is described in more detail below.
In the following descriptions, the Optional T ags and Mandatory T ags are listed to show which tags are
meaningful for each type and subset. Any other tags present are ignored by the receiver. The Comment tag is
optional. The End Data tag is required in all CMR objects.

## Page 42

26 CMOCA Reference
Halftone CMR
CMR Type HT (X'0048 0054')
Description
Halftone can be classified into two types: point-operation halftone and neighborhood-operation halftone. The
output of the point-operation halftone depends only on the value of the current pixel. It can be used for
numerous common halftone types including clustered dot, dispersed, and stochastic. Threshold arrays are
commonly used for the bilevel point-operation halftones, and lookup tables are commonly used for the
multilevel point-operation halftones. The error diffusion halftone is commonly used for the neighborhood-
operation halftone. The most common error diffusion filters include Floyd-Steinberg, Jarvis-Judice-Ninke,
Stucki, etc. The Halftone Subset tag indicates the halftone type, and thus determines required and optional
tags for this Halftone CMR.
Subset X'01': Bilevel Point-Operation Halftone
Mandatory Tags
Halftone Subset, Array Width, Array Height, Bilevel Point-Operation Screen Data, End Data
Optional Tags
Comment, Date and Time Stamp, Number of Components, Offset Tiling
Subset X'02': Multilevel Point-Operation Halftone
Mandatory Tags
Halftone Subset, Array Width, Array Height, Max Image Value, Number of Device Levels, Multilevel
Point-Operation Screen Data, End Data
Optional Tags
Comment, Date and Time Stamp, Number of Components, Offset Tiling
Subset X'03': Bilevel Error Diffusion Halftone
Mandatory Tags
Halftone Subset, Array Width, Array Height, Error Diffusion Filter, Location of Current Pixel, Raster
Direction, Boundary Condition, Threshold Value, End Data
Optional Tags
Comment, Date and Time Stamp, Number of Components
Subset X'04': Multilevel Error Diffusion Halftone
Mandatory Tags
Halftone Subset, Array Width, Array Height, Number of Device Levels, Error Diffusion Filter, Location of
Current Pixel, Raster Direction, Boundary Condition, Quantization Boundary T able, End Data
Optional Tags
Comment, Date and Time Stamp, Number of Components
Halftone CMR

## Page 43

CMOCA Reference 27
Tone Transfer Curve CMR
CMR Type TC (X'0054 0043')
Description
T one transfer curves are applied to data prior to halftoning or output. The inverse tone transfer curves are
applied to restore data to the original state. The printer tone transfer curve produces a desired appearance by
compensating for dot gain. The tone transfer curve for a display or other input device is used to correct non-
linearity (gamma) of the device. Currently, there are two tone transfer curve subsets: T oneTransferCurve Array
and T oneTransferCurve Identity. The Subset tag indicates the tone transfer curve type, and thus determines
required and optional tags for this T one Transfer Curve CMR.
Subset X'01': ToneTransferCurve Array
Mandatory Tags
T one Transfer Curve Subset, T one Transfer Curve Data, End Data
Optional Tags
Comment, Date and Time Stamp, Number of Components, T one Transfer Curve Length, Inverse T one
Transfer Curve Data
Subset X'02': ToneTransferCurve Identity
The tone transfer curve for each component is the identity. No data is sent with this subset, that is, no tone
transfer curve is to be applied. This subset is implemented for performance reasons.
Mandatory Tags
T one Transfer Curve Subset, End Data
Optional Tags
Comment, Date and Time Stamp
Tone Transfer Curve CMR

## Page 44

28 CMOCA Reference
Color Conversion CMR
CMR Type CC (X'0043 0043')
Description
Each instance of this CMR type is a subset of the standard ICC profile. This allows the CMR to be used in any
color management system.
The ICC Profile Data starts with a 128-byte header followed by the ICCT ags. The ICCHeaderFields are
contained in pre-defined byte positions as defined in T able 35 on page 69.
Each subset of the ICC profile type, selected by the ICC Profile Subset tag, defines a subset of the ICC
specification. For each subset, the Color Management Object Content Architecture defines the mandatory and
optional ICCHeaderFields and ICCT ags. Optional ICCT ags and ICCHeaderFields will be processed as
applicable if encountered. Any other ICCT ags will be ignored.
Note: The chromaticAdaptationT ag is shown as optional for each subset. However, it is mandatory if the value
in the mediaWhitePointT ag is not D50.
Two ICCHeaderFields are mandatory for the Color Conversion CMR: Color Space of Data and Profile
Connection Space. The descriptions are as follows:
Color Space of Data The ICC-supported color spaces and their signatures are listed in T able 10 on
page 20.
Profile Connection Space The ICC profile connection space is either CIELAB D50 or CIEXYZ D50 for all
ICC profiles except the ICC DeviceLink profile. The CIELAB signature is “Lab”
and the CIEXYZ signature is “XYZ”.
The currently allowed ICC profile subsets for Color Conversion CMRs include all the ICC profile types except
for the DeviceLink and the Named Colour profiles. (For complete information, please refer to sections 8.6 and
8.9 in ICC 1:2004-10 Version 4.2.0.0.)
The ICC profile subsets for the Color Conversion CMR are listed in T able 12.
Table 12. ICC Profile Subsets for the Color Conversion CMR
Subset Name Usage Basic Intents
Monochrome input profile Scanner, digital camera Device → PCS
Monochrome display profile Display Device ← → PCS
Monochrome output profile Printer Device → PCS
Three-component matrix-based input
profile
Scanner, digital camera Device → PCS
Three-component matrix-based
display profile
Display Device ← → PCS
N-component LUT-based input profile Scanner, digital camera Device → PCS
N-component LUT-based display
profile
Display Device ← → PCS
N-component LUT-based output
profiles
Printer, film recorder PCS → Device
ColorSpace conversion profile Non-device color space, for example,
sRGB, D65
Non-device ← → PCS
Color Conversion CMR

## Page 45

CMOCA Reference 29
The Basic Intents Column describes the most commonly used color conversion direction(s) for each ICC
profile subset.
Mandatory Tags
ICC Profile Subset, ICC Profile Data, End Data
Optional Tags
Comment, Date and Time Stamp, ICC Profile Filename
Implementation note: It is very important to include the ICC Profile Filename tag for debugging
purposes regardless of the fact that it is optional.
Subset X'01': Monochrome Input Profile
Mandatory ICCHeaderFields
Table 13. Mandatory ICCHeaderFields for Subset X'01': Monochrome Input Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, grayTRCT ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB0T ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag
Subset X'02': Monochrome Display Profile
Mandatory ICCHeaderFields
Table 14. Mandatory ICCHeaderFields for Subset X'02': Monochrome Display Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, grayTRCT ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB0T ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag
Color Conversion CMR

## Page 46

30 CMOCA Reference
Subset X'03': Monochrome Output Profile
Mandatory ICCHeaderFields
Table 15. Mandatory ICCHeaderFields for Subset X'03': Monochrome Output Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, grayTRCT ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB0T ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag
Subset X'04': Three-Component Matrix-Based Input Profile
Mandatory ICCHeaderFields
Table 16. Mandatory ICCHeaderFields for Subset X'04': Three-Component Matrix-Based Input Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, redMatrixColumnT ag, greenMatrixColumnT ag, blueMatrixColumnT ag,
redTRCT ag, greenTRCT ag, blueTRCT ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB0T ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag,
gamutT ag
Subset X'05': Three-Component Matrix-Based Display Profile
Mandatory ICCHeaderFields
Table 17. Mandatory ICCHeaderFields for Subset X'05': Three-Component Matrix-Based Display Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Color Conversion CMR

## Page 47

CMOCA Reference 31
Mandatory ICCTags
profileDescriptionT ag, redMatrixColumnT ag, greenMatrixColumnT ag, blueMatrixColumnT ag,
redTRCT ag, greenTRCT ag, blueTRCT ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB0T ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag,
gamutT ag
Subset X'06': N-Component LUT-Based Input Profile
Mandatory ICCHeaderFields
Table 18. Mandatory ICCHeaderFields for Subset X'06': N-Component LUT-Based Input Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, AT oB0T ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB1T ag, AT oB2T ag, BT oA0T ag, BT oA1T ag, BT oA2T ag, gamutT ag
Subset X'07': N-Component LUT-Based Display Profile
Mandatory ICCHeaderFields
Table 19. Mandatory ICCHeaderFields for Subset X'07': N-Component LUT-Based Display Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, AT oB0T ag, BT oA0T ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB1T ag, AT oB2T ag, BT oA1T ag, BT oA2T ag, gamutT ag
Color Conversion CMR

## Page 48

32 CMOCA Reference
Subset X'08': N-Component LUT-Based Output Profiles
Mandatory ICCHeaderFields
Table 20. Mandatory ICCHeaderFields for Subset X'08': N-Component LUT-Based Output Profiles
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, AT oB0T ag, BT oA0T ag, AT oB1T ag, BT oA1T ag, AT oB2T ag, BT oA2T ag, gamutT ag,
mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, colorantT ableT ag
Subset X'09': ColorSpace Conversion Profile
This subset should be used as an audit color conversion.
Mandatory ICCHeaderFields
Table 21. Mandatory ICCHeaderFields for Subset X'09': ColorSpace Conversion Profile
Byte Offset Content
16–19 Color Space of Data
20–23 Profile Connection Space
Optional ICCHeaderFields
All other header fields
Mandatory ICCTags
profileDescriptionT ag, AT oB0T ag, BT oA0T ag, mediaWhitePointT ag, copyrightT ag
Optional ICCTags
chromaticAdaptationT ag, AT oB1T ag, AT oB2T ag, BT oA1T ag, BT oA2T ag, gamutT ag
Subset X'0A': Retired Item 3 (Abstract Profile)
Color Conversion CMR

## Page 49

CMOCA Reference 33
Link Color Conversion CMR
CMR Type LK (X'004C 004B') or DL (X'0044 004C')
Description
The purpose of the Link Color Conversion CMR is to convert directly from the input to the output color space.
There are two major types of Link Color Conversion:
• Link Color Conversion with up to four Lookup T ables (LUT s) representing different rendering intents. It is
selected for use based on the audit and instruction OIDs that are contained in its tags.
• ICC DeviceLink contains an ICC profile of type DeviceLink. It contains a complex color conversion
description (with up to five processing elements) for exactly one rendering intent. It is selected for use when it
is found during a search of the hierarchy of invoked link CMRs (only ICC DeviceLink CMRs are invoked,
other link CMR subsets are not invoked). It is not based on an audit and instruction Color Conversion pair.
LinkColorConversion LUT subset: Its purpose is to combine an audit Color Conversion CMR with an
instruction Color Conversion CMR to improve performance. It allows up to four LUT s, each representing one of
the four ICC rendering intents. It is permissible to reference the same LUT tag data for multiple rendering
intents. The LUT is constructed by combining the processing required for the audit and the instruction color
conversions. If one of the AT oB/BT oA tags in an audit or instruction CMR is missing when constructing the link
LUT , the tag data for the rendering intent specified in that CMR's ICC profile header is used in place of the
missing tag data. The default rendering intent profile header is used in place of the missing tag data. The
default rendering intent for the Link Color Conversion CMR is the rendering intent specified in the ICCHeader
Field of the instruction Color Conversion CMR. The processing detail is described in “Creating Link Color
Conversion CMRs – LinkColorConversion LUT subset” on page 103.
ICC DeviceLink subset: Its purpose is to provide a customized path to convert directly from input to output
space with no dependency on an audit and instruction CMR. It allows only one single AT oB0T ag representing
one rendering intent. The rendering intent is indicated in the header of the ICC profile. The AT oB0T ag contains
up to five processing elements, possibly making the conversion more complex than if a single LUT were used.
Whereas link CMRs in general are not invoked, CMRs with this subset ID must be invoked in order to be used.
A hierarchical search is performed to determine if there is an applicable ICC DeviceLink that can be used. The
device should search for an ICC DeviceLink before searching for an Audit/Instruction Color Conversion pair.
The currently active Rendering Intent is ignored when an ICC DeviceLink is selected for use.
Currently, three Link Color Conversion subsets are defined. T able 22lists the Link Color Conversion CMR
subsets and their descriptions:
Table 22. Link Color Conversion Subsets
Subset ID Subset Name Usage
X'01' LinkColorConversion LUT Connection between two device color spaces, or the connection between
a non-device color space and a device color space, for example,
scanner→printer or sRGB→printer. This CMR is selected for use based
on the OIDs of the selected audit and instruction CC CMRs.
X'02' LinkColorConversion Identity No conversion needed. This CMR is selected for use based on the OIDs
of the selected audit and instruction CC CMRs.
X'03' ICC DeviceLink Direct conversion between an input color space and an output color
space without reference to an audit and instruction CC pair and without
use of a PCS.
Link Color Conversion CMR

## Page 50

34 CMOCA Reference
Subset X'01': LinkColorConversion LUT
Mandatory Tags
Link Color Conversion Subset, Link Audit CMR OID, Link Instruction CMR OID, Link Audit CMR Name,
Link Instruction CMR Name, Default Rendering Intent, Link LUT Perceptual, End Data
Note: LUT s for all four rendering intents must be provided, but duplicate LUT s may be identified by
setting the appropriate additional use flag in a specified Link LUT tag. For example, when all four
LUT s are identical, only the Link LUT Perceptual tag is specified. When an additional use flag for a
specific rendering intent is set to B'1', the Link-LUT tag for that rendering intent is omitted.
Optional Tags
Comment, Date and Time Stamp, Link LUT Media-Relative Colorimetric, Link LUT Saturation, Link LUT
ICC-Absolute Colorimetric, Link CMRE Identifier
Subset X'02': LinkColorConversion Identity
This subset is used when the input space is the same as the device's output space and no color conversion is
to be done. There is no data with this subset. The OIDs for the audit and instruction Color Conversion CMR
must be the same.
Mandatory Tags
Link Color Conversion Subset, Link Audit CMR OID, Link Instruction CMR OID, Link Audit CMR Name,
Link Instruction CMR Name, End Data
Optional Tags
Comment, Date and Time Stamp, Link CMRE Identifier
Subset X'03': ICC DeviceLink
Mandatory Tags
Link Color Conversion Subset, ICC Profile Data, End Data
Optional Tags
Comment, Date and Time Stamp, ICC Profile Filename
Mandatory ICCTags
profileDescriptionT ag, copyrightT ag, profileSequenceDescT ag, AtoB0T ag, colorantT ableT ag (required
only if the Data Colour Space Field is xCLR), colorantT ableOutT ag (required only if the Profile
Connection Space Field is xCLR)
Optional ICCTags
None
Link Color Conversion CMR

## Page 51

CMOCA Reference 35
Indexed CMR
CMR Type IX (X'0049 0058')
Description
An Indexed CMR contains one or more Color Palette tags that translate 2-byte indexed color values to the
target color space. Five Color Palette tags are defined for the color spaces of gray, RGB, CMYK, CIELAB, and
named colorants. The named colorants are defined through a set of colorant names that are specified in the
Colorant Identification List tag. Currently, only one Indexed CMR subset is defined for the multi-output color
spaces. It allows the mixture of different output color spaces in an Indexed CMR. When multiple Color Palette
tags are present in a CMR, and the same indexed color value is specified in different Color Palette tags, the
indexed color value in the Color Palette tag with the lower T agID number is used. If the color space of that
Color Palette tag is not applicable for the output device, the CIELAB value specified for this indexed color value
in the Color Palette tag is used for the substitution.
Table 23. List of Color Palette Tags
Name Meaning
Color Palette Gray Color Palette tag for monochrome output devices
Color Palette CMYK Color Palette tag for CMYK output devices
Color Palette RGB Color Palette tag for RGB output devices
Color Palette CIELAB Color Palette tag for the D50 CIELAB color space
Color Palette Named Colorants Color Palette tag for the named colorants color space
Subset X'01': Multi-Output Color Spaces
Mandatory Tags
Indexed Subset, one of Color Palette tags, End Data
Note: Number of Named Colorants tag and Colorant Identification List tag are mandatory if Color Palette
Named Colorants tag is specified.
Optional Tags
Comment, Date and Time Stamp, Color Palette tags
Exception Condition:
If no Color Palette tag is specified, exception condition EC-50400E exists. It is shown in “Color Palette Named
Colorants” on page 93.
EC-50400E Missing Required T ag
At least one Color Palette tag is required but none were specified.
Indexed CMR

## Page 52

36 CMOCA Reference

## Page 53

Copyright © AFP Consortium 2006, 2025 37
