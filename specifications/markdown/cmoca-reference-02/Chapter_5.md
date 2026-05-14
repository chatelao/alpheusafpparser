# Chapter 5. CMR Data Architecture

The CMR Data field carries all the actual color resource data. The resource data is carried in a tagged format. CMR is big endian. The tags are loosely based on the TIFF tag syntax, but with significant changes and additions. The tags are carried first, optionally followed by the tag data. The last tag is always the End Data tag.

## Tag Syntax

Each tag consists of 12 bytes in the following format:

### Table 24. CMR Data Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | CODE | TagID | X'0000'–X'FFFF' | Unique identifier for the tag |
| 2 | | Reserved | X'00' | Should be set to zero |
| 3 | CODE | Field Type | X'01' | 1-byte UBIN |
| | | | X'02' | 2-byte UBIN |
| | | | X'04' | 4-byte UBIN |
| | | | X'05' | BYTE (8 bits) |
| | | | X'06' | ASCII |
| | | | X'07' | UTF16 (UTF-16BE) |
| | | | X'08' | CODE (8 bit architected constant) |
| | | | X'09' | BITS |
| 4–7 | UBIN | Count | X'00000000' – X'FFFFFFFF' | Number of values of the indicated Field Type (may be zero) |
| 8–11 | | ValueOffset | Any | Data, left-aligned, if it fits into 4 bytes. Otherwise, offset to data is an offset from byte 164 of the CMR (i.e., from the start of CMRData). |

Field Type X'05' (BYTE) is used for the tags whose data has a defined structure, such as OID, Date and Time Stamp, ICC Profile Data, and Link LUT tags. Field Type X'06' (ASCII) is defined in the MO:DCA architecture with encoding scheme ID X'2100' – PC-Data, single byte. UBIN is defined as unsigned binary.

Tags X'F000'–X'FFFE' are private tags. Organizations may use a private tag in this range for their exclusive use without disclosing the tag contents. The architecture requires that such tag be non-essential, in the sense that any receivers not supporting the tag will not fail on parsing or using the resource.

X'EF00'–X'EFFF' are reserved for error handling for the CMR header.

The tags in a CMR must be specified in increasing order by their TagIDs. If they are out of order, the Exception EC-xxxx0F exists. Unless otherwise specified within the individual tag description, each TagID may appear at most once and Exception EC-xxxx0F exists if it is specified more than once. Multiple tags with the same ID may be accepted if the particular tag description explicitly states that it may repeat. The description in the tag must explain how the multiple tags are used and which one wins in cases of conflict. Tag values in the CMR tags are listed in the tag registry, that can be found in Appendix A, “Tag Registry”. Private tags are ignored. Any TagID not supported by the device causes the Exception EC-xxxx04. The last tag must be the End Data tag (TagID of X'FFFF'), or exception EC-FFFF0E exists.

There is no restriction on where the actual data fields are located, as long as they are within the CMRData field scope. Note that all the offsets are from the beginning of the CMRData field, so that the location of the CMR header can be changed without any need to update the ValueOffset values. The offsets do not have to be increasing as the TagIDs increase, nor do they have to follow any other rule. There is no requirement that all the data in the scope be used, that is, it is permissible to have data not referenced by any tag.

The number of bytes of data for a tag is the value of Count multiplied by the size of each data item as indicated by Field Type. For example, a Count of 1 indicates two bytes if Field Type is X'02' (2-byte UBIN) or X'07' (UTF16).

Each type of CMR has a list of Mandatory Tags and a list of optional tags. The receivers should ignore any unknown tags. If an optional tag is not present, the default value (if one exists) should be used.

## Exception Syntax

On encountering an error, an exception is raised. Each exception is defined by a three byte value. The first two bytes are the relevant TagID value (X'0000'–X'FFFF'), while the third byte is the exception code. The exception codes are defined as follows:

| Code | Meaning |
| :--- | :--- |
| X'04' | Unsupported TagID Value in a CMR tag |
| X'05' | Invalid Count Value |
| X'06' | Invalid Field Type |
| X'0E' | Missing Required Tag |
| X'0F' | Invalid Sequence |
| X'10' | Invalid or unsupported field value or an offset that causes the tag data to start or end after the end of the CMR (as defined by the CMR length) |
| X'11' | Inconsistent Tag Contents |
| X'12' | Incorrect order of repeating groups |
| X'13' | Duplicate value |

## Coordinate System

The CMR coordinate system is a two dimensional Cartesian coordinate system. The horizontal axis is labeled x, and the vertical axis is labeled y. Positive x is to the right of the origin, and positive y is above the origin. The measurement unit is pixel.

**Figure 7. Cartesian Coordinate System**

## Tag Semantics

This section defines the CMR tags.

### Comment

*   **TagID:** X'0004'
*   **Field Type:** X'06' (ASCII), X'07' (UTF16)
*   **Count:** Number of characters

This tag defines arbitrary comment text, ignored by receivers. There is no default.

**Exception Conditions:**
*   **EC-000406 Invalid Field Type:** The specified Field Type is invalid for the tag.
*   **EC-00040F Invalid Sequence:** The tag has been encountered out of sequence or more than once.
*   **EC-000410 Invalid Value:** The offset caused some portion of the tag data to be outside of the CMRdata.

### Date and Time Stamp

*   **TagID:** X'0008'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 10

This tag contains the date and time of the creation of the CMR. It is defined consistently with the MO:DCA definition of the Universal Date and Time Stamp Triplet X'72' that is specified in accordance with the format defined in ISO 8601:1988 (E). The tag is informational. The date and time values are not checked for validity.

#### Table 25. Date and Time Stamp Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | YearAD | 0–65,535 | Year AD using Gregorian calendar |
| 2 | 1-byte UBIN | Month | 1–12 | Month of the year |
| 3 | 1-byte UBIN | Day | 1–31 | Day of the month |
| 4 | 1-byte UBIN | Hour | 0–23 | Hour of the day in 24-hour format |
| 5 | 1-byte UBIN | Minute | 0–59 | Minute of the hour |
| 6 | 1-byte UBIN | Second | 0–59 | Second of the minute |
| 7 | CODE | TimeZone | X'00', X'01', X'02' | Relationship of time to UTC (Coordinated, Ahead, Behind) |
| 8 | 1-byte UBIN | UTCDiffH | 0–23 | Hours ahead of or behind UTC |
| 9 | 1-byte UBIN | UTCDiffM | 0–59 | Minutes ahead of or behind UTC |

*   **YearAD:** Specifies the year AD using the Gregorian calendar. For example, the year 1999 is specified as X'07CF'. Represents the YYYY component.
*   **Month:** Specifies the month of the year. January is X'01'. Represents the MM component.
*   **Day:** Specifies the day of the month. The first day is X'01'. Represents the DD component.
*   **Hour:** Specifies the hour (0-23). Represents the hh component.
*   **Minute:** Specifies the minute (0-59). Represents the mm component.
*   **Second:** Specifies the second (0-59). Represents the ss component.
*   **TimeZone:** Defines the relation to UTC.
    *   **X'00':** Time is specified in UTC. UTCDiffH/M should be X'00'. (Suffix Z)
    *   **X'01':** Ahead of UTC. UTCDiffH/M specify the difference. (Suffix +hhmm)
    *   **X'02':** Behind UTC. UTCDiffH/M specify the difference. (Suffix -hhmm)

**Exception Conditions:**
*   **EC-000805 Invalid Count Value:** The specified Count field value is invalid for the tag.
*   **EC-000806 Invalid Field Type:** The specified Field Type is invalid for the tag.
*   **EC-00080F Invalid Sequence:** The tag has been encountered out of sequence or more than once.
*   **EC-000810 Invalid Value:** The offset caused some portion of the tag data to be outside of the CMRdata.

### Number of Components

*   **TagID:** X'0011'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** 1

This tag defines the number of color components referenced by this resource. To comply with ICC, the number of components must be in the range of 1–15.

#### Table 26. Ordering Sequence of Color Spaces

| Color Space | Component 1 | Component 2 | Component 3 | Component 4 |
| :--- | :--- | :--- | :--- | :--- |
| XYZ | X | Y | Z | |
| Lab | L | a | b | |
| Luv | L | u | v | |
| Yxy | Y | X | y | |
| YCbr | Y | Cb | Cr | |
| RGB | R | G | B | |
| GRAY | K | | | |
| HSV | H | S | V | |
| HLS | H | L | S | |
| CMYK | C | M | Y | K |
| CMY | C | M | Y | |
| 2CLR | Component 1 | Component 2 | | |
| 3CLR | Component 1 | Component 2 | Component 3 | |
| 4CLR | Component 1 | Component 2 | Component 3 | Component 4 |

The components are numbered according to the order in the ICC data tag. Additional color spaces can be added simply by defining the signature component assignments. Default is 1.

**Exception Conditions:**
*   **EC-001105 Invalid Count Value**
*   **EC-001106 Invalid Field Type**
*   **EC-00110F Invalid Sequence**
*   **EC-001110 Invalid Value:** number of components is zero or greater than 15.

### Halftone Subset

*   **TagID:** X'1011'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

This tag denotes a subset of the Halftone CMR type.

#### Table 27. Halftone Subsets

| Subset ID | Name |
| :--- | :--- |
| X'01' | Bilevel Point-Operation Halftone |
| X'02' | Multilevel Point-Operation Halftone |
| X'03' | Bilevel Error Diffusion Halftone |
| X'04' | Multilevel Error Diffusion Halftone |

**Exception Conditions:**
*   **EC-101110 Invalid Value:** specified subset value is none of X'01', X'02', X'03', or X'04'.

### Array Width

*   **TagID:** X'1021'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN)
*   **Count:** Number of color components

This tag defines the width of the array along the x-direction in pixels for each color component.

### Array Height

*   **TagID:** X'1025'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN)
*   **Count:** Number of color components

This tag defines the height of the array along the y-direction in pixels for each color component.

### Max Image Value

*   **TagID:** X'1030'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
*   **Count:** Number of color components

This tag defines the maximum input image value per component.

### Number of Device Levels

*   **TagID:** X'1035'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** Number of color components

This tag defines the number of device levels per component for multilevel devices. Each specified Number of Device Levels must be greater than 2.

### Offset Tiling

*   **TagID:** X'1040'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN)
*   **Count:** Number of color components

This tag defines the amount of shift in pixels between the halftone tiles in two adjacent rows for each component.

**Figure 8. Illustration of Offset Tiling with Offset Tiling=2**

### Bilevel Point-Operation Screen Data

*   **TagID:** X'1045'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
*   **Count:** Sum of (Array Width × Array Height) over all color components

This tag specifies the threshold array values for each screen. Arranged in row-major format. Component-wise structured.

### Multilevel Point-Operation Screen Data

*   **TagID:** X'1050'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** Sum of (Array Width × Array Height × (Max Image Value + 1)) over all color components

This tag gives the device gray level for each pixel. Each screen is a 3-d table lookup. Dimensions are m × n × (Max Image Value + 1).

### Error Diffusion Filter

*   **TagID:** X'1055'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** Sum of (Array Width × Array Height) over all color components

This tag specifies a set of values in the error diffusion filter. Arranged in a 2-dimensional array for each color plane.

**Figure 9. Illustration of Error Distribution with Floyd-Steinberg Filter**

### Location of Current Pixel

*   **TagID:** X'1060'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** 2 × number of color components

Specifies a pair of values (row, column) describing the location of the current pixel in an error diffusion filter.

### Raster Direction

*   **TagID:** X'1065'
*   **Field Type:** X'08' (CODE)
*   **Count:** Number of color components

#### Table 28. Raster Direction Values

| Value | Meaning |
| :--- | :--- |
| X'01' | Normal raster |
| X'02' | Serpentine raster |

**Figure 10. Illustration of Normal Raster and Serpentine Raster**

### Boundary Condition

*   **TagID:** X'1070'
*   **Field Type:** X'08' (CODE)
*   **Count:** Number of color components

#### Table 29. Boundary Condition Values

| Value | Meaning |
| :--- | :--- |
| X'01' | None |
| X'02' | Zero boundary |
| X'03' | Reflect |
| X'04' | Periodic |

### Threshold Value

*   **TagID:** X'1075'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
*   **Count:** Number of color components

Specifies a single threshold value for bilevel error diffusion halftones.

### Quantization Boundary Table

*   **TagID:** X'1080'
*   **Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
*   **Count:** Sum of (Number of Device Levels – 1) over all color components

Specifies n one-dimensional arrays for multilevel error diffusion halftone.

#### Table 30. Illustration of Quantization Boundary Table

| Index (i) | Array Entry (Threshold Value) |
| :--- | :--- |
| 1 | 60 |
| 2 | 120 |
| 3 | 200 |

#### Table 31. Implementation of Quantization Boundary Table

| Initial Value | Output Device Level | Corrected value |
| :--- | :--- | :--- |
| I ∈ [0, 60) | 0 | 0 |
| I ∈ [60, 120) | 1 | 85 |
| I ∈ [120, 200) | 2 | 170 |
| I ∈ [200, 255] | 3 | 255 |

### Tone Transfer Curve Subset

*   **TagID:** X'2004'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

#### Table 32. Tone Transfer Curve Subsets

| Subset ID | Name |
| :--- | :--- |
| X'01' | ToneTransferCurve Array |
| X'02' | ToneTransferCurve Identity |

### Tone Transfer Curve Length

*   **TagID:** X'2011'
*   **Field Type:** X'08' (CODE)
*   **Count:** Number of color components

#### Table 33. Tone Transfer Curve Length Values

| Value | Meaning |
| :--- | :--- |
| X'01' | 256 1-byte entries |
| X'02' | 65,536 2-byte entries |

### Tone Transfer Curve Data

*   **TagID:** X'2015'
*   **Field Type:** X'05' (byte)
*   **Count:** Total length of the data

### Inverse Tone Transfer Curve Data

*   **TagID:** X'2020'
*   **Field Type:** X'05' (Byte)
*   **Count:** Total length of the data

### ICC Profile Subset

*   **TagID:** X'3011'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

#### Table 34. ICC Profile Subsets

| Subset ID | Name |
| :--- | :--- |
| X'01' | Monochrome input profile |
| X'02' | Monochrome display profile |
| X'03' | Monochrome output profile |
| X'04' | Three-component matrix-based input profile |
| X'05' | Three-component matrix-based display profile |
| X'06' | N-component LUT-based input profile |
| X'07' | N-component LUT-based display profile |
| X'08' | N-component LUT-based output profiles |
| X'09' | ColorSpace conversion profile |
| X'0A' | Retired item 3 (Abstract profile) |

### ICC Profile Data

*   **TagID:** X'3015'
*   **Field Type:** X'05' (BYTE)
*   **Count:** The number of bytes in the profile

#### Table 35. ICC Header Fields

| Byte Offset | Content |
| :--- | :--- |
| 0–3 | Profile size |
| 4–7 | CMM Type signature |
| 8–11 | Profile version number |
| 12–15 | Profile/Device Class signature |
| 16–19 | Color Space of Data |
| 20–23 | Profile Connection Space (PCS) |
| 24–35 | Date and time this profile was first created |
| 36–39 | acsp (61637370h) profile file signature |
| 40–43 | Primary Platform signature |
| 44–47 | Flags to indicate various options for the CMM |
| 48–51 | Device manufacturer |
| 52–55 | Device model |
| 56–63 | Device attributes (e.g., media type) |
| 64–67 | Rendering Intent |
| 68–79 | XYZ values of the illuminant (must be D50) |
| 80–83 | Profile Creator signature |
| 84–99 | Profile ID |
| 100–127 | 28 bytes reserved (set to zeros) |

### ICC Profile Filename

*   **TagID:** X'3025'
*   **Field Type:** X'06' (ASCII), X'07' (UTF16)
*   **Count:** Number of characters

### Link Color Conversion Subset

*   **TagID:** X'4011'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

#### Table 36. Link Color Conversion Subsets

| Subset ID | Name |
| :--- | :--- |
| X'01' | LinkColorConversion LUT |
| X'02' | LinkColorConversion Identity |
| X'03' | ICC DeviceLink |

### Link Audit CMR OID

*   **TagID:** X'4015'
*   **Field Type:** X'05' (BYTE)
*   **Count:** Number of bytes in the OID

### Link Instruction CMR OID

*   **TagID:** X'4020'
*   **Field Type:** X'05' (BYTE)
*   **Count:** Number of bytes in the OID

### Link Audit CMR Name

*   **TagID:** X'4025'
*   **Field Type:** X'07' (UTF16)
*   **Count:** Number of characters

### Link Instruction CMR Name

*   **TagID:** X'4030'
*   **Field Type:** X'07' (UTF16)
*   **Count:** Number of characters

### Default Rendering Intent

*   **TagID:** X'4035'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

#### Table 37. ICC Rendering Intents

| Rendering Intent | Value |
| :--- | :--- |
| Perceptual | X'00' |
| Media-Relative Colorimetric | X'01' |
| Saturation | X'02' |
| ICC-Absolute Colorimetric | X'03' |

### Link LUT Perceptual

*   **TagID:** X'4040'
*   **Field Type:** X'05' (BYTE)
*   **Count:** The number of bytes in the LUT + 20 bytes of the header

#### Table 38. Link LUT Perceptual Tag Syntax

| Bytes | Length | Type | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0 | 1 | 1-byte UBIN | 1–15 | Number of components of the input color space |
| 1 | 1 | 1-byte UBIN | 1–15 | Number of components of the output color space |
| 2–16 | 15 | 1-byte UBIN | 0–255 | Number of grid points in each component of input |
| 17 | 1 | 1-byte UBIN | 1, 2 | Precision: 1=1-byte UBIN, 2=2-byte UBIN |
| 18 | 1 | BITS | | Additional use flags: bit 0: Media-rel, bit 1: Saturation, bit 2: ICC-Abs |
| 19 | 1 | | 0 | Reserved |
| 20 to end | | | | LUT data |

### Link LUT Media-Relative Colorimetric

*   **TagID:** X'4045'
*   **Field Type:** X'05' (BYTE)

### Link LUT Saturation

*   **TagID:** X'4050'
*   **Field Type:** X'05' (BYTE)

### Link LUT ICC-Absolute Colorimetric

*   **TagID:** X'4055'
*   **Field Type:** X'05' (BYTE)

### Link CMRE Identifier

*   **TagID:** X'4090'
*   **Field Type:** X'07' (UTF16)
*   **Count:** Number of characters

### Indexed Subset

*   **TagID:** X'5011'
*   **Field Type:** X'08' (CODE)
*   **Count:** 1

#### Table 42. Indexed CMR Subset

| Subset ID | Name |
| :--- | :--- |
| X'01' | Multi-output color spaces |

### Number of Named Colorants

*   **TagID:** X'5015'
*   **Field Type:** X'01' (1-byte UBIN)
*   **Count:** 1

### Color Palette Gray

*   **TagID:** X'5020'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 9 × the number of color entries

#### Table 43. Color Palette Gray Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value |
| 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components |
| 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Intensity of gray (X'00'=black, X'FF'=white) |

### Color Palette CMYK

*   **TagID:** X'5025'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 12 × the number of color entries

#### Table 44. Color Palette CMYK Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value |
| 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components |
| 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Cyan |
| 9 | 1-byte UBIN | Component_2 | X'00'–X'FF' | Magenta |
| 10 | 1-byte UBIN | Component_3 | X'00'–X'FF' | Yellow |
| 11 | 1-byte UBIN | Component_4 | X'00'–X'FF' | Black |

### Color Palette RGB

*   **TagID:** X'5030'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 11 × the number of color entries

#### Table 45. Color Palette RGB Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value |
| 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components |
| 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Red |
| 9 | 1-byte UBIN | Component_2 | X'00'–X'FF' | Green |
| 10 | 1-byte UBIN | Component_3 | X'00'–X'FF' | Blue |

### Color Palette CIELAB

*   **TagID:** X'5035'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 8 × the number of color entries

#### Table 46. Color Palette CIELAB Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value |
| 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components |

### Color Palette Named Colorants

*   **TagID:** X'5040'
*   **Field Type:** X'05' (BYTE)
*   **Count:** (Number of Named Colorants + 8) × the number of color entries

#### Table 47. Color Palette Named Colorants Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value |
| 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components |
| 8 to 7+n | 1-byte UBIN | Component_i | X'00'–X'FF' | Intensity of i-th colorant |

### Colorant Identification List

*   **TagID:** X'5045'
*   **Field Type:** X'05' (BYTE)
*   **Count:** Sum of the length over the Number of Named Colorants

#### Table 48. Colorant Identification List Tag Syntax

| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0 | 1-byte UBIN | Length | X'03'–X'FB' | Length of this repeating group |
| 1–end | UTF-16 | Colorant Name | | Colorant name in free format UTF-16BE |

### End Data

*   **TagID:** X'FFFF'
*   **Field Type:** X'05' (BYTE)
*   **Count:** 0

Signifies the end of the tag list.
