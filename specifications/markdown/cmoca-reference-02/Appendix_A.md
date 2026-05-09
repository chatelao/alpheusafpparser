# Appendix A. Tag Registry

This table defines the CMR tags in the base level of the Color Management Object Content Architecture. Support for some CMR types is optional, see Appendix C, “Compliance With Color Management Object Content Architecture”, on page 125. When a presentation device supports a given CMR type, it must support the tags used by that CMR type, as defined in this table.

### Table 51. Tag Registry

| TagID | Tag Name | CMR Type |
| :--- | :--- | :--- |
| X'0004' | Comment | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed |
| X'0008' | Date and Time Stamp | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed |
| X'0011' | Number of Components | Halftone, Tone Transfer Curve |
| X'1011' | Halftone Subset | Halftone |
| X'1021' | Array Width | Halftone |
| X'1025' | Array Height | Halftone |
| X'1030' | Max Image Value | Halftone |
| X'1035' | Number of Device Levels | Halftone |
| X'1040' | Offset Tiling | Halftone |
| X'1045' | Bilevel Point-Operation Screen Data | Halftone |
| X'1050' | Multilevel Point-Operation Screen Data | Halftone |
| X'1055' | Error Diffusion Filter | Halftone |
| X'1060' | Location of Current Pixel | Halftone |
| X'1065' | Raster Direction | Halftone |
| X'1070' | Boundary Condition | Halftone |
| X'1075' | Threshold Value | Halftone |
| X'1080' | Quantization Boundary Table | Halftone |
| X'2004' | Tone Transfer Curve Subset | Tone Transfer Curve |
| X'2011' | Tone Transfer Curve Length | Tone Transfer Curve |
| X'2015' | Tone Transfer Curve Data | Tone Transfer Curve |
| X'2020' | Inverse Tone Transfer Curve Data | Tone Transfer Curve |
| X'3011' | ICC Profile Subset | Color Conversion |
| X'3015' | ICC Profile Data | Color Conversion |
| X'3025' | ICC Profile Filename | Color Conversion |
| X'4011' | Link Color Conversion Subset | Link Color Conversion |
| X'4015' | Link Audit CMR OID | Link Color Conversion |
| X'4020' | Link Instruction CMR OID | Link Color Conversion |
| X'4025' | Link Audit CMR Name | Link Color Conversion |
| X'4030' | Link Instruction CMR Name | Link Color Conversion |
| X'4035' | Default Rendering Intent | Link Color Conversion |
| X'4040' | Link LUT Perceptual | Link Color Conversion |
| X'4045' | Link LUT Media-Relative Colorimetric | Link Color Conversion |
| X'4050' | Link LUT Saturation | Link Color Conversion |
| X'4055' | Link LUT ICC-Absolute Colorimetric | Link Color Conversion |
| X'4090' | Link CMRE Identifier | Link Color Conversion |
| X'5011' | Indexed Subset | Indexed |
| X'5015' | Number of Named Colorants | Indexed |
| X'5020' | Color Palette Gray | Indexed |
| X'5025' | Color Palette CMYK | Indexed |
| X'5030' | Color Palette RGB | Indexed |
| X'5035' | Color Palette CIELAB | Indexed |
| X'5040' | Color Palette Named Colorants | Indexed |
| X'5045' | Colorant Identification List | Indexed |
| X'FFFF' | End Data | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed |

**Notes:**
1. For an Indexed CMR, at least one of the Color Palette tags must be present.
2. Tags X'F000'–X'FFFE' are private tags.
