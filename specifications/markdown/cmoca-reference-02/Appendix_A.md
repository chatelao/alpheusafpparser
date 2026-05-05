Appendix A. Tag Registry
This table defines the CMR tags in the base level of the Color Management Object Content Architecture.
Support for some CMR types is optional, see Appendix C, “Compliance With Color Management Object
Content Architecture”, on page 125. When a presentation device supports a given CMR type, it must support
the tags used by that CMR type, as defined in this table.
Table 51. Tag Registry
TagID Tag Name CMR Type
X'0004' “Comment” on page 40 Halftone
T one Transfer Curve
Color Conversion
Link Color Conversion
Indexed
X'0008' “Date and Time Stamp” on page 41 Halftone
T one Transfer Curve
Color Conversion
Link Color Conversion
Indexed
X'0011' “Number of Components” on page 43 Halftone
T one Transfer Curve
X'1011' “Halftone Subset” on page 44 Halftone
X'1021' “Array Width” on page 45 Halftone
X'1025' “Array Height” on page 46 Halftone
X'1030' “Max Image Value” on page 47 Halftone
X'1035' “Number of Device Levels” on page 48 Halftone
X'1040' “Offset Tiling” on page 49 Halftone
X'1045' “Bilevel Point-Operation Screen Data” on page 50 Halftone
X'1050' “Multilevel Point-Operation Screen Data” on page 51 Halftone
X'1055' “Error Diffusion Filter” on page 52 Halftone
X'1060' “Location of Current Pixel” on page 54 Halftone
X'1065' “Raster Direction” on page 55 Halftone
X'1070' “Boundary Condition” on page 57 Halftone
X'1075' “Threshold Value” on page 59 Halftone
X'1080' “Quantization Boundary T able” on page 60 Halftone
X'2004' “T one Transfer Curve Subset” on page 62 T one Transfer Curve
X'2011' “T one Transfer Curve Length” on page 63 T one Transfer Curve
X'2015' “T one Transfer Curve Data” on page 64 T one Transfer Curve
X'2020' “Inverse T one Transfer Curve Data” on page 66 T one Transfer Curve
X'3011' “ICC Profile Subset” on page 68 Color Conversion
X'3015' “ICC Profile Data” on page 69 Color Conversion

## Page 136

120 CMOCA Reference
Table 51 Tag Registry (cont'd.)
TagID Tag Name CMR Type
X'3025' “ICC Profile Filename” on page 71 Color Conversion
X'4011' “Link Color Conversion Subset” on page 72 Link Color Conversion
X'4015' “Link Audit CMR OID” on page 73 Link Color Conversion
X'4020' “Link Instruction CMR OID” on page 74 Link Color Conversion
X'4025' “Link Audit CMR Name” on page 75 Link Color Conversion
X'4030' “Link Instruction CMR Name” on page 76 Link Color Conversion
X'4035' “Default Rendering Intent” on page 77 Link Color Conversion
X'4040' “Link LUT Perceptual” on page 78 Link Color Conversion
X'4045' “Link LUT Media-Relative Colorimetric” on page 80 Link Color Conversion
X'4050' “Link LUT Saturation” on page 82 Link Color Conversion
X'4055' “Link LUT ICC-Absolute Colorimetric” on page 84 Link Color Conversion
X'4090' “Link CMRE Identifier” on page 86 Link Color Conversion
X'5011' “Indexed Subset” on page 87 Indexed
X'5015' “Number of Named Colorants” on page 88 Indexed
X'5020' “Color Palette Gray” on page 89 Indexed
X'5025' “Color Palette CMYK” on page 90 Indexed
X'5030' “Color Palette RGB” on page 91 Indexed
X'5035' “Color Palette CIELAB” on page 92 Indexed
X'5040' “Color Palette Named Colorants” on page 93 Indexed
X'5045' “Colorant Identification List” on page 95 Indexed
X'FFFF' “End Data” on page 96 Halftone
T one Transfer Curve
Color Conversion
Link Color Conversion
Indexed
Notes:
1. For an Indexed CMR, at least one of the Color Palette tags must be present.
2. T ags X'F000'–X'FFFE' are private tags.
Tag Registry

## Page 137

Copyright © AFP Consortium 2006, 2025 121
