Chapter 5. CMR Data Architecture
The CMR Data field carries all the actual color resource data. The resource data is carried in a tagged format.
CMR is big endian. The tags are loosely based on the TIFF tag syntax, but with significant changes and
additions. The tags are carried first, optionally followed by the tag data. The last tag is always the End Data
tag.
Tag Syntax
Each tag consists of 12 bytes in the following format:
Table 24. CMR Data Tag Syntax
Offset Type Name Range Meaning
0–1 CODE T agID X'0000'–X'FFFF' Unique identifier for the tag
2 Reserved X'00' Should be set to zero
3 CODE Field Type X'01' 1-byte UBIN
X'02' 2-byte UBIN
X'04' 4-byte UBIN
X'05' BYTE (8 bits)
X'06' ASCII
X'07' UTF16 (UTF-16BE)
X'08' CODE (8 bit architected constant)
X'09' BITS
4–7 UBIN Count X'00000000' –
X'FFFFFFFF'
Number of values of the indicated Field Type (may be
zero)
8–11 ValueOffset Any Data, left-aligned, if it fits into 4 bytes. Otherwise, offset
to data is an offset from byte 164 of the CMR (i.e., from
the start of CMRData).
Field Type X'05' (BYTE) is used for the tags whose data has a defined structure, such as OID, Date and Time
Stamp, ICC Profile Data, and Link LUT tags. Field Type X'06' (ASCII) is defined in the MO:DCA architecture
with encoding scheme ID X'2100' – PC-Data, single byte. UBIN is defined as unsigned binary.
T ags X'F000'–X'FFFE' are private tags. Organizations may use a private tag in this range for their exclusive
use without disclosing the tag contents. The architecture requires that such tag be non-essential, in the sense
that any receivers not supporting the tag will not fail on parsing or using the resource.
X'EF00'–X'EFFF' are reserved for error handling for the CMR header.
The tags in a CMR must be specified in increasing order by their T agIDs. If they are out of order, the Exception
EC-xxxx0F exists. Unless otherwise specified within the individual tag description, each T agID may appear at
most once and Exception EC-xxxx0F exists if it is specified more than once. Multiple tags with the same ID
may be accepted if the particular tag description explicitly states that it may repeat. The description in the tag
must explain how the multiple tags are used and which one wins in cases of conflict. T ag values in the CMR
tags are listed in the tag registry, that can be found in Appendix A, “T ag Registry”, on page 119. Private tags

## Page 54

38 CMOCA Reference
are ignored. Any T agID not supported by the device causes the Exception EC-xxxx04. The last tag must be the
End Data tag (T agID of X'FFFF'), or exception EC-FFFF0E exists.
There is no restriction on where the actual data fields are located, as long as they are within the CMRData field
scope. Note that all the offsets are from the beginning of the CMRData field, so that the location of the CMR
header can be changed without any need to update the ValueOffset values. The offsets do not have to be
increasing as the T agIDs increase, nor do they have to follow any other rule. There is no requirement that all
the data in the scope be used, that is, it is permissible to have data not referenced by any tag.
The number of bytes of data for a tag is the value of Count multiplied by the size of each data item as indicated
by Field Type. For example, a Count of 1 indicates two bytes if Field Type is X'02' (2-byte UBIN) or X'07'
(UTF16).
Each type of CMR has a list of Mandatory T ags and a list of optional tags. The receivers should ignore any
unknown tags. If an optional tag is not present, the default value (if one exists) should be used.
Exception Syntax
On encountering an error, an exception is raised. Each exception is defined by a three byte value. The first two
bytes are the relevant T agID value (X'0000'–X'FFFF'), while the third byte is the exception code. The exception
codes are defined as follows:
X'04' Unsupported T agID Value in a CMR tag
X'05' Invalid Count Value
X'06' Invalid Field Type
X'0E' Missing Required T ag
X'0F' Invalid Sequence
X'10' Invalid or unsupported field value or an offset that causes the tag data to start or end after the end
of the CMR (as defined by the CMR length)
X'11' Inconsistent T ag Contents
X'12' Incorrect order of repeating groups
X'13' Duplicate value
Exception Syntax

## Page 55

CMOCA Reference 39
Coordinate System
The CMR coordinate system is a two dimensional Cartesian coordinate system. The horizontal axis is labeled
x, and the vertical axis is labeled y. Positive x is to the right of the origin, and positive y is above the origin. The
measurement unit is pixel.
Figure 7. Cartesian Coordinate System
5 10-5-10
5
10
-5
-10
YY AXIS
X AXIS
X
P(5,2)
(0,0)
ORIGIN
2-Dimensional Cartesian Coordinate System
III
III IV
Coordinate System

## Page 56

40 CMOCA Reference
Tag Semantics
This section defines the CMR tags.
Comment
TagID X'0004'
Field Type X'06' (ASCII), X'07' (UTF16)
Count Number of characters
This tag defines arbitrary comment text, ignored by receivers.
There is no default.
Exception Conditions:
EC-000406 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-00040F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-000410 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Comment tag

## Page 57

CMOCA Reference 41
Date and Time Stamp
TagID X'0008'
Field Type X'05' (BYTE)
Count 10
This tag contains the date and time of the creation of the CMR. It is defined consistently with the MO:DCA
definition of the Universal Date and Time Stamp Triplet X'72' that is specified in accordance with the format
defined in ISO 8601:1988 (E). The tag is informational. The date and time values are not checked for validity.
Table 25. Date and Time Stamp Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN YearAD 0–65,535 Year AD using Gregorian calendar
2 1-byte UBIN Month 1–12 Month of the year
3 1-byte UBIN Day 1–31 Day of the month
4 1-byte UBIN Hour 0–23 Hour of the day in 24-hour format
5 1-byte UBIN Minute 0–59 Minute of the hour
6 1-byte UBIN Second 0–59 Second of the minute
7 CODE TimeZone
X'00'
X'01'
X'02'
Relationship of time to UTC:
Coordinated Universal Time
Ahead of UTC
Behind UTC
8 1-byte UBIN UTCDiffH 0–23 Hours ahead of or behind UTC
9 1-byte UBIN UTCDiffM 0–59 Minutes ahead of or behind UTC
YearAD Specifies the year AD using the Gregorian calendar. For example, the year 1999 is specified
as X'07CF', the year 2000 as X'07D0', and the year 2001 as X'07D1'. Represents the YYYY
component of a date in the format YYYYMMDD.
Month Specifies the month of the year. January is specified as X'01', and subsequent months are
numbered in ascending order. Represents the MM component of a date in the format
YYYYMMDD.
Day Specifies the day of the month. The first day of any month is specified as X'01', and
subsequent days are numbered in ascending order. Represents the DD component of a date
in the format YYYYMMDD. For example, the date December 31, 1999 is specified as
X'07CF0C1F', and January 1, 2000 is specified as X'07D00101'.
Hour Specifies the hour of the day in 24 hour format. Represents the hh component of a time in the
format hhmmss.
Minute Specifies the minute of the hour. Represents the mm component of a time in the format
hhmmss.
Second Specifies the second of the minute. Represents the ss component of a time in the format
hhmmss. For example, the time 4:35:21 PM is specified as X'102315'.
Date and Time Stamp tag

## Page 58

42 CMOCA Reference
TimeZone Defines the relation of the specified time with respect to Coordinated Universal Time (UTC).
This parameter, along with the UTCDiffH and UTCDiffM parameters, is used to accommodate
differences between a specified local time and UTC because of time zones and daylight
savings programs. For example, Mountain Time in the US is seven hours behind UTC when
daylight savings is inactive, and six hours behind UTC when daylight savings is active.
Value Description
X'00' Time is specified in Coordinated Universal Time (UTC). With this value, the
UTCDiffH and UTCDiffM parameters should be set to X'00'. When this time is
displayed or printed, the equivalence with UTC time is normally indicated with
a Z suffix, that is, hhmmssZ.
X'01' Specified time is ahead of UTC. The number of hours ahead of UTC is
specified by the UTCDiffH parameter; and the number of minutes ahead of
UTC is specified by the UTCDiffM parameter. When this time is displayed or
printed, the relationship with UTC time is normally indicated with a +
character, followed by the actual time difference in hours and minutes, that is
hhmmss+hhmm.
X'02' Specified time is behind UTC. The number of hours behind UTC is specified
by the UTCDiffH parameter; and the number of minutes behind UTC is
specified by the UTCDiffM parameter. When this time is displayed or printed,
the relationship with UTC time is normally indicated with a – character,
followed by the actual time difference in hours and minutes, that is hhmmss–
hhmm.
All others Reserved
There is no default.
Exception Conditions:
EC-000805 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-000806 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-00080F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-000810 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Date and Time Stamp tag

## Page 59

CMOCA Reference 43
Number of Components
TagID X'0011'
Field Type X'01' (1-byte UBIN)
Count 1
This tag defines the number of color components referenced by this resource. T o comply with ICC, the number
of components must be in the range of 1–15. The ordering sequences of different color spaces are listed in
T able 26.
Table 26. Ordering Sequence of Color Spaces
Color Space Component 1 Component 2 Component 3 Component 4
XYZ X Y Z
Lab L a b
Luv L u v
Yxy Y X y
YCbr Y Cb Cr
RGB R G B
GRAY K
HSV H S V
HLS H L S
CMYK C M Y K
CMY C M Y
2CLR Component 1 Component 2
3CLR Component 1 Component 2 Component 3
4CLR Component 1 Component 2 Component 3 Component 4
The components are numbered according to the order in the ICC data tag. Additional color spaces can be
added simply by defining the signature component assignments.
The default is 1.
Exception Conditions:
EC-001105 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-001106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-00110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-001110 Invalid Value
The specified number of components is zero or greater than 15.
Number of Components tag

## Page 60

44 CMOCA Reference
Halftone Subset
TagID X'1011'
Field Type X'08' (CODE)
Count 1
This tag denotes a subset of the Halftone CMR type. Currently, point-operation halftones and error diffusion
halftones are defined in the Halftone Subset. The point-operation halftone operates only on the input pixel and
not its neighbors. It includes clustered-dot, dispersed-dot, and stochastic halftones. The error diffusion halftone
requires neighborhood operations and thresholding. Each subset has a list of required and optional CMR tags.
The subsets are defined in T able 27.
Table 27. Halftone Subsets
Subset ID Name
X'01' Bilevel Point-Operation Halftone
X'02' Multilevel Point-Operation Halftone
X'03' Bilevel Error Diffusion Halftone
X'04' Multilevel Error Diffusion Halftone
There is no default.
Exception Conditions:
EC-101105 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-101106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10110E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-101110 Invalid Value
The specified subset value is none of X'01', X'02', X'03', or X'04'.
Halftone Subset tag

## Page 61

CMOCA Reference 45
Array Width
TagID X'1021'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN)
Count Number of color components
This tag defines the width of the array along the x-direction in pixels for each color component. It represents
the screen width for the point-operation halftones, and the error diffusion filter width for the error diffusion
halftones. The count must be equal to the number of color components referenced by this resource, and must
match the value specified by the Number of Components tag or its default. When multiple components are
specified, the order of the components is specified by the Number of Components tag. Each specified width
must be greater than zero.
Note: For processing efficiency, the values of Array Width for Error Diffusion Filter should be small, preferably
less than 255.
There is no default.
Exception Conditions:
EC-102106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10210E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10210F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-102110 Invalid Value
One or more width values are zero or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-102111 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Array Width tag

## Page 62

46 CMOCA Reference
Array Height
TagID X'1025'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN)
Count Number of color components
This tag defines the height of the array along the y-direction in pixels for each color component. It represents
the screen height for the point operation halftones, and the error diffusion filter height for the error diffusion
halftones. The count must be equal to the number of color components referenced by this resource, and must
match the value specified by the Number of Components tag or its default. When multiple components are
specified, the order of the components is specified by the Number of Components tag. Each specified height
must be greater than zero.
Note: For processing efficiency, the values of Array Height for Error Diffusion Filter should be small, preferably
less than 255.
There is no default.
Exception Conditions:
EC-102506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10250E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10250F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-102510 Invalid Value
One or more height values are zero or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-102511 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Array Height tag

## Page 63

CMOCA Reference 47
Max Image Value
TagID X'1030'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
Count Number of color components
This tag defines the maximum input image value per component. For instance, the maximum Max Image Value
of an 8-bit contone image is 255, but the Max Image Value could be 255, 252, 200, etc. The count must be
equal to the number of color components referenced by this resource, and must match the value specified by
the Number of Components tag or its default. When multiple components are specified, the order of the
components is specified by the Number of Components tag. Each specified Max Image Value must be greater
than 0.
There is no default.
Exception Conditions:
EC-103006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10300E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10300F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-103010 Invalid Value
One or more Max Image Values are zero or the offset caused some portion of the tag data to
be outside of the CMRdata.
EC-103011 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Max Image Value tag

## Page 64

48 CMOCA Reference
Number of Device Levels
TagID X'1035'
Field Type X'01' (1-byte UBIN)
Count Number of color components
This tag defines the number of device levels per component for multilevel devices. The device level starts with
0 and ends with the number of device levels minus 1. For example, if the number of device levels is equal to 3,
then the device levels are 0, 1, and 2. Each specified Number of Device Levels must be greater than 2. The
count must be equal to the number of color components referenced by this resource, and must match the
value specified by the Number of Components tag or its default. When multiple components are specified, the
order of the components is specified by the Number of Components tag.
There is no default.
Exception Conditions:
EC-103506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10350E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10350F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-103510 Invalid Value
One or more Number of Device Levels are smaller than 3 or the offset caused some portion of
the tag data to be outside of the CMRdata.
EC-103511 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Number of Device Levels tag

## Page 65

CMOCA Reference 49
Offset Tiling
TagID X'1040'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN)
Count Number of color components
This tag defines the amount of shift in pixels between the halftone tiles in two adjacent rows for each
component. The first row of tiles is arranged across device space pixels with the upper left pixel of the
threshold array coincident with the upper left pixel of the image. The offset specifies the shift to the right of
each subsequent row of tiles below the top row of tiles. The offset is specified with respect to the left side of a
complete tile to the left side of the first tile in the row of tiles underneath. Each row of tiles is right shifted the
same amount relative to the row above it. An example of Offset Tiling for an offset of two is illustrated in Figure
8. Partial tiles tile across the remaining device pixels. The count must be equal to the number of color
components referenced by this resource, and must match the value specified by the Number of Components
tag or its default. When multiple components are specified, the order of the components is specified by the
Number of Components tag.
Figure 8. Illustration of Offset Tiling with Offset Tiling=2
offset
offset
offset
width height
The default is zero.
Exception Conditions:
EC-104006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10400F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-104010 Invalid Value
One or more Offset Tiling values are greater than the Array Width or the offset caused some
portion of the tag data to be outside of the CMRdata.
EC-104011 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Offset Tiling tag

## Page 66

50 CMOCA Reference
Bilevel Point-Operation Screen Data
TagID X'1045'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
Count Sum of (Array Width × Array Height) over all color components
This tag specifies the threshold array values for each screen. Each screen has Array Width times Array Height
entries of the specified size, arranged in row-major format. The data is component-wise structured: that is, it
starts with the first component's threshold array followed by the second, and so on. The order of the
components is specified by the Number of Components tag. If the input pixel level is less than the pixel value in
the threshold array, the output pixel value should be B'0'; otherwise it should be B'1'.
Each halftone screen is developed for a particular output device. The inputs to the threshold array will be
values expressed in the color space of the device (CMYK or RGB) and the meaning of the values in the
threshold array depend on the device's color space. For CMYK devices, 255 or (2
16 – 1) or (2 32 – 1) represents
black. For RGB devices, those values represent white. Similarly, the meaning of the output value depends on
the device's color space. For CMYK devices, B'0' represents white. For RGB devices, B'0' represents black.
There is no default.
Exception Conditions:
EC-104506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10450E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10450F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-104510 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
EC-104511 Inconsistent T ag Contents
The count is inconsistent with Array Width × Array Height, or the number of color components.
Bilevel Point-Operation Screen Data tag

## Page 67

CMOCA Reference 51
Multilevel Point-Operation Screen Data
TagID X'1050'
Field Type X'01' (1-byte UBIN)
Count Sum of (Array Width × Array Height × (Max Image Value + 1)) over all color components
This tag gives the device gray level for each pixel. Each screen is a 3-d table lookup, defined as:
• o(x, y) = f (x', y', g)
• x' = x mod m
• y' = y mod n
Where:
• x, y = position of the pixel
• g = the input gray (or color) of the pixel at position (x, y), the maximum g is Max Image Value.
• o = the output multilevel at position (x, y)
• x' and y' are the reduced coordinate
• m and n are the Array Width and Array Height respectively
The data is structured component-wise, that is, it starts with the first component's screen followed by the
second, and so on. The output data must be no greater than the Number of Device Levels – 1. The dimensions
of the 3-d array are m × n × (Max Image Value + 1). Offset Tiling applies if the tables are tiled with a shift.
There is no default.
Exception Conditions:
EC-105006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10500E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10500F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-105010 Invalid Value
One or more data values are greater than (Number of Device Levels – 1) or the offset caused
some portion of the tag data to be outside of the CMRdata.
EC-105011 Inconsistent T ag Contents
The count is inconsistent with Array Width × Array Height × (Max Image Value + 1), or the
number of color components.
Multilevel Point-Operation Screen Data tag

## Page 68

52 CMOCA Reference
Error Diffusion Filter
TagID X'1055'
Field Type X'01' (1-byte UBIN)
Count Sum of (Array Width × Array Height) over all color components
This tag specifies a set of values in the error diffusion filter. The values are arranged in a 2-dimensional array
for each color plane. Each filter has Array Width times Array Height entries of the specified size, arranged in
row-major format. The data is component-wise structured: that is, it starts with the first component's error
diffusion filter array values followed by the second, and so on. The order of the components is specified by the
Number of Components tag.
The value in the error diffusion filter is called weight representing the proportion of the error distributed to the
pixel in that position. B'0's are assigned to the pixel that is currently processed, the pixels that are processed
before the current pixel, or the pixels that no error is distributed to. The error distributed to a pixel is the weight
of that pixel divided by the total weight, that is, the sum of all values in the filter.
Error diffusion propagates the error between the initial value and the corrected value of each pixel to the
neighboring pixels that are still to be processed. The error is defined as:
error = initial value – corrected value
where the initial value is the sum of the original image value plus the errors if errors have been propagated to
this pixel from the previously processed neighboring pixels. The corrected value is this value after threshold.
For example, assume that for a binary device, the threshold value is 128, and the current pixel value is 140.
Then:
• initial value = 140
• corrected value = 255 (since 140 > 128)
• error = 140 – 255 = –115
This error is then propagated to its surrounding future pixels. Below is the example of Floyd-Steinberg filter:
The filter is defined as:
0 0 7
3 5 1
The errors are distributed as illustrated in Figure 9.
Figure 9. Illustration of Error Distribution with Floyd-Steinberg Filter
7/16
1/16 5/163/16
error
There is no default.
Error Diffusion Filter tag

## Page 69

CMOCA Reference 53
Exception Conditions:
EC-105506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10550E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10550F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-105510 Invalid Value
All data values are equal to 0 or the offset caused some portion of the tag data to be outside of
the CMRdata.
EC-105511 Inconsistent T ag Contents
The count is inconsistent with Array Width × Array Height, or the number of color components.
Error Diffusion Filter tag

## Page 70

54 CMOCA Reference
Location of Current Pixel
TagID X'1060'
Field Type X'01' (1-byte UBIN)
Count 2 × number of color components
This tag specifies a pair of values describing the location of the pixel that is currently being processed in an
error diffusion filter. The first value indicates the number of the row, and the second value indicates the number
of the column, where the current processed pixel is located. The rows and columns are numbered starting with
1. The data is component-wise structured: that is, it starts with the first component's location indices followed
by the second, and so on. The count must be equal to two times the number of color components referenced
by this resource. When multiple components are specified, the order of the components is specified by the
Number of Components tag.
There is no default.
Exception Conditions:
EC-106005 Invalid Count Value
The specified Count field value is invalid for the tag. The count is not a multiple of two.
EC-106006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10600E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10600F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-106010 Invalid Value
The first index value is either zero or greater than the Array Height, the second index value is
either zero or greater than the Array Width, or the offset caused some portion of the tag data
to be outside of the CMRdata.
EC-106011 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Location of Current Pixel tag

## Page 71

CMOCA Reference 55
Raster Direction
TagID X'1065'
Field Type X'08' (CODE)
Count Number of color components
This tag denotes the raster direction for the error diffusion filter processing. Currently, two directions are
defined: the normal raster direction and the serpentine raster direction. The normal raster direction alternates
left-to-right and top-to-bottom. The serpentine raster direction alternates left-to-right, then right-to-left. The
raster direction is always left to right when the first row of the input image is being processed. The error
diffusion filter needs to be flipped 180 degrees when it processes right-to-left direction. For example, the error
diffusion filter of Floyd-Steinberg is:
0 x 7
3 5 1
where x is the current pixel. The filter becomes
7 x 0
1 5 3
when the raster direction is right-to-left.
Table 28. Raster Direction Values
Value Meaning
X'01' Normal raster
X'02' Serpentine raster
Figure 10. Illustration of Normal Raster and Serpentine Raster
(a) Normal Raster (b) Serpentine
There is no default.
Exception Conditions:
EC-106506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10650E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10650F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-106510 Invalid Value
The specified subset value is neither X'01' nor X'02' or the offset caused some portion of the
tag data to be outside of the CMRdata.
Raster Direction tag

## Page 72

56 CMOCA Reference
EC-106511 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Raster Direction tag

## Page 73

CMOCA Reference 57
Boundary Condition
TagID X'1070'
Field Type X'08' (CODE)
Count Number of color components
This tag denotes the boundary conditions for the error diffusion halftones. It defines the assumed values for the
image when the error diffusion filter crosses the boundary of the image. The boundary conditions apply to the
top, the left, and the right boundary of the image. The count must be equal to the number of color components
referenced by this resource, and must match the value specified by the Number of Components tag or its
default. When multiple components are specified, the order of the components is specified by the Number of
Components tag.
Four boundary conditions are defined: none, zero boundary, reflect, and periodic.
• None: no boundary condition is applied.
• Zero boundary: zeros are assigned for the image values outside the boundary.
• Reflect: image values that reflect at the boundary are assigned for the image values outside the boundary.
For example, if the image values in one scan line are 1 2 3...6 7 8, then the image values outside the right
boundary are 8 7 6 ...
• Periodic: image values that wrap around the axis in the same scan line are assigned for the image values
outside the boundary. For example, if the image values in one scan line are 1 2 3...5 6 7 8, then the image
values outside the right boundary are 1 2 3 ...
The boundary conditions are defined in T able 29.
Table 29. Boundary Condition Values
Value Meaning
X'01' None
X'02' Zero boundary
X'03' Reflect
X'04' Periodic
There is no default.
Exception Conditions:
EC-107006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10700E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10700F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-107010 Invalid Value
The specified subset value is none of the subsets X'01', X'02', X'03', or X'04', or the offset
caused some portion of the tag data to be outside of the CMRdata.
Boundary Condition tag

## Page 74

58 CMOCA Reference
EC-107011 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Boundary Condition tag

## Page 75

CMOCA Reference 59
Threshold Value
TagID X'1075'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
Count Number of color components
This tag specifies a single threshold value for the bilevel error diffusion halftones. If the initial pixel value is less
than the threshold value, the output pixel value should be B'0'; otherwise it should be B'1'. The count must be
equal to the number of color components referenced by this resource, and must match the value specified by
the Number of Components tag or its default. When multiple components are specified, the order of the
components is specified by the Number of Components tag.
Each halftone screen is developed for a particular output device. The values being compared to the threshold
will be values expressed in the color space of the device (CMYK or RGB) and the meaning of the values in the
threshold depends on the device's color space. For CMYK devices, 255 or (2
16 – 1) or (2 32 – 1) represents
black. For RGB devices, those values represent white. Similarly, the meaning of the output value depends on
the device's color space. For CMYK devices, B'0' represents white. For RGB devices, B'0' represents black.
There is no default.
Exception Conditions:
EC-107506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10750E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10750F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-107510 Invalid Value
One or more values are equal to zero or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-107511 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Threshold Value tag

## Page 76

60 CMOCA Reference
Quantization Boundary Table
TagID X'1080'
Field Type X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN)
Count Sum of (Number of Device Levels – 1) over all color components
This tag specifies n one-dimensional arrays for the multilevel error diffusion halftone, where n is the number of
color components. The array entries are threshold values for input pixels. The length of the array for each
component is equal to the Number of Device Levels minus 1 for that component. The indices (i) of the array
are in the range [1, Number of Device Levels – 1]. For a threshold value T
i in the array, where 1 ≤ i ≤ Number of
Device Levels – 1, Ti is the threshold value defined between device levels i–1 and i. That is, the first value in
the array is the threshold value defined between device levels 0 and 1, the second value in the array is the
threshold value defined between device levels 1 and 2, and so on. The values in the entries thus are
monotonically increasing. The digital value corresponding to a device level i is defined as (2
n – 1) × i / (Number
of Device Levels – 1) rounded to the nearest integer, where n is the number of bits in Field Type.
For a pixel value I:
• If I < T1, the corrected value after threshold is equal to the digital value corresponding to device level zero.
• If Ti ≤ I < Ti+1, the corrected value after threshold is equal to the digital value corresponding to device level i.
• If I ≥ Tj, where j is equal to the Number of Device Levels minus 1, the corrected value after threshold is equal
to the digital value corresponding to the maximum device level.
When multiple components are specified, the order of the components is specified by the Number of
Components tag. The data is structured component-wise, that is, it starts with the first component array
followed by the second, and so on.
T able 30is an example of a Quantization Boundary T able. The Number of Device Levels = 4, and the Field
Type is X'01' (1-byte UBIN). The array entries are: 60, 120, and 200, where 60 is the threshold value defined
between device levels 0 and 1, 120 is the threshold value defined between device levels 1 and 2, and 200 is
the threshold value defined between device levels 2 and 3.
Table 30. Illustration of Quantization Boundary Table
Index (i) Array Entry (Threshold Value)
1 60
2 120
3 200
Table 31. Implementation of Quantization Boundary Table
Initial Value Output Device Level Corrected value
I∈ [0, 60) 0 0
I∈ [60, 120) 1 85
I∈ [120, 200) 2 170
I∈ [200, 255] 3 255
There is no default.
Quantization Boundary Table tag

## Page 77

CMOCA Reference 61
Exception Conditions:
EC-108006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-10800E Missing Required T ag
The tag is required for the resource, but is missing.
EC-10800F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-108010 Invalid Value
One or more values are equal to zero, the values in the table are not monotonically increasing,
or the offset caused some portion of the tag data to be outside of the CMRdata.
EC-108011 Inconsistent T ag Contents
The count is inconsistent with the Number of Device Levels – 1 or the number of color
components.
Quantization Boundary Table tag

## Page 78

62 CMOCA Reference
Tone Transfer Curve Subset
TagID X'2004'
Field Type X'08' (CODE)
Count 1
This tag denotes a subset of the T one Transfer Curve CMR type. There are two tone transfer curve subsets
defined: T oneTransferCurve Array and T oneTransferCurve Identity. The T oneTransferCurve Array defines
transfer curves that map from the input space to a modified space. If the T one Transfer Curve Subset is the
identity, then no tone transfer curve is to be applied, that is, no data is sent with the T oneTransferCurve Identity
subset.
Table 32. Tone Transfer Curve Subsets
Subset ID Name
X'01' T oneTransferCurve Array
X'02' T oneTransferCurve Identity
There is no default.
Exception Conditions:
EC-200405 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-200406 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-20040E Missing Required T ag
The tag is required for the resource, but is missing.
EC-20040F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-200410 Invalid Value
The specified subset value is neither X'01' nor X'02'.
Tone Transfer Curve Subset tag

## Page 79

CMOCA Reference 63
Tone Transfer Curve Length
TagID X'2011'
Field Type X'08' (CODE)
Count Number of color components
This tag gives the number of entries in the tone transfer curve for each component. The count must be equal to
the number of color components referenced by this resource, and must match the value specified by the
Number of Components tag or its default. When multiple components are specified, the order of the
components is specified by the Number of Components tag. The only values allowed for length are encoded as
shown in T able 33.
Table 33. Tone Transfer Curve Length Values
Value Meaning
X'01' 256 1-byte entries in tone transfer curve
X'02' 65,536 2-byte entries in tone transfer curve
The default is X'01' (256 entries) for each component.
Architecture note: It is possible that the desired tone transfer curve has a number of entries that are less than
256 or 65,536, say 250. If this were allowed and input image data having a value of 253 were received,
special handling would be required. T o avoid this, the options for length are limited to 256 and 65,536.
The application or color management software can adjust the desired tone transfer curve to meet this
requirement. For instance, entries can be added at the end. These entries would all have the same
value as the last value in the original tone transfer curve. Another approach would be to scale the
original tone transfer curve so that it has 256 entries.
Exception Conditions:
EC-201106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-20110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-201110 Invalid Value
The specified value is not X'01' or X'02', or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-201111 Inconsistent T ag Contents
The count is inconsistent with the number of color components.
Tone Transfer Curve Length tag

## Page 80

64 CMOCA Reference
Tone Transfer Curve Data
TagID X'2015'
Field Type X'05' (byte)
Count T otal length of the data (see below)
This tag gives the data for all tone transfer curves. The T one Transfer Curve Data has n one-dimensional LUT s,
where n is the number of color components. When multiple components are specified, the order of the
components is specified by the Number of Components tag. The data is structured component-wise, that is, it
starts with the first component tone transfer curve data followed by the second, and so on. The length of each
curve is given by the corresponding T one Transfer Curve Length tag (256 or 65,536). The index for the tone
transfer curve starts with 0 and ends with the value specified by the T one Transfer Curve Length tag minus 1.
The Count is defined to be the total length of the data. This total length is the sum of the lengths of each tone
transfer curve over the number of color components. For each color component:
• If the T one Transfer Curve Length tag specifies X'01' (256 1-byte entries) for that component, then the length
of the tone transfer curve is 256 bytes.
• If the T one Transfer Curve Length tag specifies X'02' (65,536 2-byte entries) for that component, then the
length of the tone transfer curve is 131,072 bytes.
This tag always contains an offset to the data.
There is no default.
Tone Transfer Curve Usage
T one transfer curves are used as lookup tables to correct or calibrate the pixel values prior to output or
halftoning. For each pixel component value, the device will choose the corresponding curve and use the value
as an index. The value found at that index will be used instead of the original value.
The input and output of the tone transfer curve are interpreted to be in the color space of the device. For CMYK
devices, 255 or 65,535 represent black. For RGB devices, they represent white.
Architecture note: This is different from PostScript, where the transfer function is always interpreted as if the
component were additive and where subtractive input and output must be converted.
Mismatches between the data type of the T one Transfer Curve and the type of the input image data will be
handled as follows. For each color component:
• T one Transfer Curve Length is X'01' (data size is one byte) and image data is 2-byte values: Each image
value should be truncated to be the upper 8 bits of the value, that is, the image value is divided by 256.
• T one Transfer Curve Length is X'02' (data size is two bytes) and image data is 1-byte values: Each image
value should be converted to a 2-byte value by adding 8 bits of zero on the right side (by multiplying by 256).
Alternatively and equivalently, the tone transfer curve can be collapsed to include only entries from the
original array whose indices were multiples of 256.
• A similar process would be followed for image data having 4-byte values.
Exception Conditions:
EC-201506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-20150E Missing Required T ag
The tag is required for the resource, but is missing.
Tone Transfer Curve Data tag

## Page 81

CMOCA Reference 65
EC-20150F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-201510 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
EC-201511 Inconsistent T ag Contents
The count is inconsistent with the T one Transfer Curve Length, or the number of color
components.
Tone Transfer Curve Data tag

## Page 82

66 CMOCA Reference
Inverse Tone Transfer Curve Data
TagID X'2020'
Field Type X'05' (Byte)
Count T otal length of the data (see below)
This tag represents the inverse of the T one Transfer Curve Data, that is another part of the same T one Transfer
Curve CMR. It is an optional tag. If it is used, its data must be created by inverting the tone transfer curve
lookup table. Because the inverse is not a well-defined function, this tag allows the application or color
management system to clearly define the inverse. The Inverse T one Transfer Curve Data has n one-
dimensional LUT s, wheren is the number of color components. When multiple components are specified, the
order of the components is specified by the Number of Components tag. The data is structured component-
wise, that is, it starts with the first component tone transfer curve data followed by the second, and so on. The
length of each curve is given by the corresponding T one Transfer Curve Length tag (256 or 65,536).
The Count is defined to be the total length of the data. This total length is the sum of the lengths of each tone
transfer curve over the number of color components. For each color component:
• If the T one Transfer Curve Length tag specifies X'01' (256 1-byte entries) for that component, then the length
of the tone transfer curve is 256 bytes.
• If the T one Transfer Curve Length tag specifies X'02' (65,536 2-byte entries) for that component, then the
length of the tone transfer curve is 131,072 bytes.
This tag always contains an offset to the data.
There is no default.
Inverse Tone Transfer Curve Usage
The inverse color calibration data is used when the T one Transfer Curve CMR is being used as an audit CMR
and it is desired to undo the tone transfer curve that was applied to the image data. The inverse tone transfer
curves are used as lookup tables to convert or calibrate the input pixel values back to the original data. For
each pixel component value, the device chooses the corresponding curve and uses the value as an index. The
value found at that index is used instead of the original value. Exact details for processing are the same as
those for the T one Transfer Curve Data.
The input and output of the inverse tone transfer curve are interpreted to be in the color space of the input. For
CMYK devices, 255 or 65,535 represent black. For RGB devices, they represent white.
Architecture note: This is different from Post Script, where the transfer function is always interpreted as if the
component were additive and where subtractive input and output must be converted.
Exception Conditions:
EC-202006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-20200F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-202010 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Inverse Tone Transfer Curve Data tag

## Page 83

CMOCA Reference 67
EC-202011 Inconsistent T ag Contents
The count is inconsistent with the T one Transfer Curve Length, or the number of color
components.
Inverse Tone Transfer Curve Data tag

## Page 84

68 CMOCA Reference
ICC Profile Subset
TagID X'3011'
Field Type X'08' (CODE)
Count 1
This tag denotes a subset of the ICC profile. Each subset has a list of mandatory and optional
ICCHeaderFields and ICCtags. The receiver will ignore any other tags. The subsets are defined in T able 34.
Table 34. ICC Profile Subsets
Subset ID Name
X'01' Monochrome input profile
X'02' Monochrome display profile
X'03' Monochrome output profile
X'04' Three-component matrix-based input profile
X'05' Three-component matrix-based display profile
X'06' N-component LUT-based input profile
X'07' N-component LUT-based display profile
X'08' N-component LUT-based output profiles
X'09' ColorSpace conversion profile
X'0A' Retired item 3 (Abstract profile)
There is no default.
Exception Conditions:
EC-301105 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-301106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-30110E Missing Required T ag
The tag is required for the resource, but is missing.
EC-30110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-301110 Invalid Value
The specified subset value is invalid.
ICC Profile Subset tag

## Page 85

CMOCA Reference 69
ICC Profile Data
TagID X'3015'
Field Type X'05' (BYTE)
Count The number of bytes in the profile
This tag contains a complete encapsulated ICC profile. The ICC profile is interpreted based on the ICC Profile
Subset tag. Each subset denotes a subset of the ICC specification, listing required and optional tags and
ICCHeaderFields. Any other ICC profile tags are ignored.
Table 35. ICC Header Fields
Byte Offset Content
0–3 Profile size
4–7 CMM Type signature
8–11 Profile version number
12–15 Profile/Device Class signature
16–19 Color Space of Data (possibly a derived space) [“the canonical input space”]
20–23 Profile Connection Space (PCS) [“the canonical output space”]
24–35 Date and time this profile was first created
36–39 acsp (61637370h) profile file signature
40–43 Primary Platform signature
44–47 Flags to indicate various options for the CMM such as distributed processing and caching options
48–51 Device manufacturer of the device for which this profile is created
52–55 Device model of the device for which this profile is created
56–63 Device attributes unique to the particular device setup such as media type
64–67 Rendering Intent
68–79 The XYZ values of the illuminant of the Profile Connection Space. These values must correspond
to D50.
80–83 Profile Creator signature
84–99 Profile ID
100–127 28 bytes reserved for future expansion. These bytes must be set to zeros. There is no default.
Exception Conditions:
EC-301505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-301506 Invalid Field Type
The specified Field Type is invalid for the tag or the header content.
EC-30150E Missing Required T ag
The tag or the header content is required for the resource, but is missing.
ICC Profile Data tag

## Page 86

70 CMOCA Reference
EC-30150F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-301510 Invalid Value
An ICC tag or an ICCHeaderField content required for this subset of the ICC profile is missing
from the encapsulated ICC profile or the offset caused some portion of the tag data to be
outside of the CMRdata.
ICC Profile Data tag

## Page 87

CMOCA Reference 71
ICC Profile Filename
TagID X'3025'
Field Type X'06' (ASCII), X'07' (UTF16)
Count Number of characters
This tag holds the filename of the ICC Profile that was used to create this CMR. The ICC Profile that was in
that file is copied into the ICC Profile Data tag.
Exception Conditions:
EC-302506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-30250F Invalid sequence
The tag has been encountered out of sequence or more than once.
EC-302510 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
ICC Profile Filename tag

## Page 88

72 CMOCA Reference
Link Color Conversion Subset
TagID X'4011'
Field Type X'08' (CODE)
Count 1
This tag denotes a subset of the Link Color Conversion CMR type. The LinkColorConversion LUT subset
combines an audit Color Conversion CMR with an instruction Color Conversion CMR. The
LinkColorConversion Identity subset is used when the input space of the data is the same as the device‘s
output space and no color conversion is to be done on the data. The ICC DeviceLink subset provides a direct
conversion from input to output space with no involvement of audit and instruction Color Conversion CMRs.
Table 36. Link Color Conversion Subsets
Subset ID Name
X'01' LinkColorConversion LUT
X'02' LinkColorConversion Identity
X'03' ICC DeviceLink
There is no default.
Exception Conditions:
EC-401105 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-401106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40110E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-401110 Invalid or Unsupported Value
The specified value is neither X'01', X'02', nor X'03'. This error is also reported if the CMRType
in the CMR Header does not match the subset value. It is an error if:
• CMRType = LK and Subset ID is not X'01' or X'02'
• CMRType = DL and Subset ID is not X'03'
Note: It is possible that some implementations were complete before subset X'03' was added
to the architecture and they might NACK if it is specified. Server software should verify
that the STM Device-Control command-set vector shows support for the ICC
DeviceLink subset (X'E006') before sending down a CMR with the CMRType field of the
CMR header showing “DL” (ICC DeviceLink).
Link Color Conversion Subset tag

## Page 89

CMOCA Reference 73
Link Audit CMR OID
TagID X'4015'
Field Type X'05' (BYTE)
Count Number of bytes in the OID
This tag defines the OID (Object Identifier) of the audit Color Conversion CMR used in the Link Color
Conversion CMR. The OID is used to provide a universally unique identifier for the audit CMR.
There is no default.
Exception Conditions:
EC-401506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40150E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40150F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-401510 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Link Audit CMR OID tag

## Page 90

74 CMOCA Reference
Link Instruction CMR OID
TagID X'4020'
Field Type X'05' (BYTE)
Count Number of bytes in the OID
This tag defines the OID (Object Identifier) of the instruction Color Conversion CMR used in the Link Color
Conversion CMR. The OID is used to provide a universally unique identifier for the instruction CMR.
There is no default.
Exception Conditions:
EC-402006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40200E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40200F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-402010 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
EC-402011 Inconsistent T ag Contents
OID tags are different in the LinkColorConversion Identity.
Link Instruction CMR OID tag

## Page 91

CMOCA Reference 75
Link Audit CMR Name
TagID X'4025'
Field Type X'07' (UTF16)
Count Number of characters
This tag specifies the name of the audit Color Conversion CMR used in the Link Color Conversion CMR. The
tag is informational and is not checked for validity.
There is no default.
Exception Conditions:
EC-402506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40250E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40250F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-402510 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Link Audit CMR Name tag

## Page 92

76 CMOCA Reference
Link Instruction CMR Name
TagID X'4030'
Field Type X'07' (UTF16)
Count Number of characters
This tag specifies the name of the instruction Color Conversion CMR used in the Link Color Conversion CMR.
The name is informational and is not checked for validity.
There is no default.
Exception Conditions:
EC-403006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40300E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40300F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-403010 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Link Instruction CMR Name tag

## Page 93

CMOCA Reference 77
Default Rendering Intent
TagID X'4035'
Field Type X'08' (CODE)
Count 1
This tag defines the rendering intent that was found in the ICCHeaderFields in the instruction Color Conversion
CMR. The defined values are consistent with the ICC rendering intents.
Table 37. ICC Rendering Intents
Rendering Intent Value
Perceptual X'00'
Media-Relative Colorimetric X'01'
Saturation X'02'
ICC-Absolute Colorimetric X'03'
There is no default.
Exception Conditions:
EC-403505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-403506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40350E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40350F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-403510 Invalid Value
The specified value is invalid.
Default Rendering Intent tag

## Page 94

78 CMOCA Reference
Link LUT Perceptual
TagID X'4040'
Field Type X'05' (BYTE)
Count The number of bytes in the LUT + 20 bytes of the header
This tag defines a lookup table that converts the input color space of an audit Color Conversion CMR into the
output color space of an instruction Color Conversion CMR. The rendering intent of the LUT is perceptual.
However, flags can be set to indicate that the LUT is also used for other rendering intents (media-relative
colorimetric, saturation, or ICC-absolute colorimetric). The lookup table (LUT) has an input table and an output
table. The dimension of the input table is n (rows) by m (columns), where n is the number of data points, that is,
the product of the numbers of the grid points over the number of components of the input color space, and m is
the number of components of the input color space in the audit Color Conversion CMR. The dimension
corresponding to the first input component varies least rapidly and the dimension corresponding to the last
input component varies most rapidly. The grid point values in each color component of input color space are
obtained by using the following equation:
E
i = M × i / (q – 1)
where i is the i th grid point of that color component (i starts from 0 and ends with q – 1) and q is the number of
the grid points for that color component. M is 255 for 1-byte UBIN and 65,535 for 2-byte UBIN.
The output table is in the form of an n (rows) by p (columns) array, where p is the number of components of the
output color space in the instruction Color Conversion CMR. Each entry in the output table is a function value
of the corresponding entry in the input table. Only the output table is shown in the LUT . The table values are
arrays of 8-bit or 16-bit values.
The size of the LUT = n × p × (precision of data elements in bytes).
Table 38. Link LUT Perceptual Tag Syntax
Bytes Length Type Range Meaning
0 1 1-byte UBIN 1–15 Number of components of the input color space
1 1 1-byte UBIN 1–15 Number of components of the output color space
2–16 15 For each entry:
1-byte UBIN
For each entry:
0–255
Number of grid points in each component of the
input color space. There are 15 entries, each
encoded as one byte. Only the first m entries are
used, where m is the number of components of the
input color space. Unused entries should be set to
zeros.
17 1 1-byte UBIN
1
2
Precision of data elements:
1-byte UBIN
2-byte UBIN
18 1 BITS Additional use flags:
set to B'0' if false, set to B'1' if true
bit 0 B'0', B'1' Media-relative colorimetric
bit 1 B'0', B'1' Saturation
bit 2 B'0', B'1' ICC-absolute colorimetric
bit 3–7 B'00000' Reserved
Link LUT Perceptual tag

## Page 95

CMOCA Reference 79
Table 38 Link LUT Perceptual Tag Syntax (cont'd.)
Bytes Length Type Range Meaning
19 1 0 Reserved
20 to
end
For each data
point:
1-byte UBIN
2-byte UBIN
For each data
point:
0–255
0–65,535
LUT data
Number of components of the input color space
The number of components of the input color space in an audit Color Conversion CMR.
Number of components of the output color space
The number of components of the output color space in an instruction Color Conversion CMR.
Number of grid points
Number of grid points in each component of the input color space. The number of grid points in each
dimension is not necessarily the same. The decision on these numbers is implementation dependent. It
could be different from the number of grid points in each dimension of the input color space in the audit
color conversion. ICC allows a maximum of 15 color components in a color space. 15 bytes are allocated
for this header field.
Precision of data elements
The entry values can be either 1 byte or 2 bytes. The decision is implementation dependent.
Additional use flags
Each flag indicates that the LUT is also used for another rendering intent (media-relative colorimetric,
saturation, or ICC-absolute colorimetric).
LUT
data
The data in the LUT .
There is no default.
Exception Conditions:
EC-404005 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-404006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40400E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40400F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-404010 Invalid Value
The specified value is invalid or the offset caused some portion of the tag data to be outside of
the CMRdata.
Link LUT Perceptual tag

## Page 96

80 CMOCA Reference
Link LUT Media-Relative Colorimetric
TagID X'4045'
Field Type X'05' (BYTE)
Count The number of bytes in the LUT + 20 bytes of the header
This tag defines a lookup table that converts the input color space of an audit Color Conversion CMR into the
output color space of an instruction Color Conversion CMR. The rendering intent of the LUT is media-relative
colorimetric. However, flags can be set to indicate that the LUT is also used for other rendering intents
(saturation or ICC-absolute colorimetric). The lookup table (LUT) has an input table and an output table. The
dimension of the input table is n (rows) by m (columns), where n is the number of data points, that is, the
product of the numbers of the grid points over the number of components of the input color space, and m is the
number of components of the input color space in the audit Color Conversion CMR. The dimension
corresponding to the first input component varies least rapidly and the dimension corresponding to the last
input component varies most rapidly. The grid point values in each color component of input color space are
obtained by using the following equation:
E
i = M × i / (q – 1)
where i is the i th grid point of that color component (i starts from 0 and ends with q – 1) and q is the number of
the grid points for that color component. M is 255 for 1-byte UBIN and 65,535 for 2-byte UBIN.
The output table is in the form of an n (rows) by p (columns) array, where p is the number of components of the
output color space in the instruction Color Conversion CMR. Each entry in the output table is a function value
of the corresponding entry in the input table. Only the output table is shown in the LUT . The table values are
arrays of 8-bit or 16-bit values.
The size of the LUT = n × p × (precision of data elements in bytes).
Table 39. Link LUT Media-Relative Colorimetric Tag Syntax
Bytes Length Type Range Meaning
0 1 1-byte UBIN 1–15 Number of components of the input color space
1 1 1-byte UBIN 1–15 Number of components of the output color space
2–16 15 For each entry:
1-byte UBIN
For each entry:
0–255
Number of grid points in each component of the
input color space. There are 15 entries, each
encoded as one byte. Only the first m entries are
used, where m is the number of components of the
input color space. Unused entries should be set to
zeros.
17 1 1-byte UBIN
1
2
Precision of data elements:
1-byte UBIN
2-byte UBIN
18 1 BITS Additional use flags:
set to 0 if false, set to 1 if true
bit 0 B'0' Reserved
bit 1 B'0', B'1' Saturation
bit 2 B'0', B'1' ICC-absolute colorimetric
bit 3–7 B'00000' Reserved
Link LUT Media-Relative Colorimetric tag

## Page 97

CMOCA Reference 81
Table 39 Link LUT Media-Relative Colorimetric Tag Syntax (cont'd.)
Bytes Length Type Range Meaning
19 1 0 Reserved
20 to
end
For each data
point:
1-byte UBIN
2-byte UBIN
For each data
point:
0–255
0–65,535
LUT data
Number of components of the input color space
The number of components of the input color space in an audit Color Conversion CMR.
Number of components of the output color space
The number of components of the output color space in an instruction Color Conversion CMR.
Number of grid points
Number of grid points in each component of the input color space. The number of grid points in each
dimension is not necessarily the same. The decision on these numbers is implementation dependent. It
could be different from the number of grid points in each dimension of the input color space in the audit
color conversion. ICC allows a maximum of 15 color components in a color space. 15 bytes are allocated
for this header field.
Precision of data elements
The entry values can be either 1 byte or 2 bytes. The decision is implementation dependent.
Additional use flags
Each flag indicates that the LUT is also used for another rendering intent (saturation or ICC-absolute
colorimetric).
LUT
data
The data in the LUT .
There is no default.
Exception Conditions:
EC-404505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-404506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40450E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40450F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-404510 Invalid Value
The specified value is invalid or the offset caused some portion of the tag data to be outside of
the CMRdata.
EC-404511 Inconsistent T ag Contents
The LUT was provided in a previous tag.
• A Link LUT tag is provided and the flag for the rendering intent of this LUT was set in a
previous Link LUT tag.
• A flag for a rendering intent is set in multiple Link LUT tags.
Link LUT Media-Relative Colorimetric tag

## Page 98

82 CMOCA Reference
Link LUT Saturation
TagID X'4050'
Field Type X'05' (BYTE)
Count The number of bytes in the LUT + 20 bytes of the header
This tag defines a lookup table that converts the input color space of an audit Color Conversion CMR into the
output color space of an instruction Color Conversion CMR. The rendering intent of the LUT is saturation.
However, a flag can be set to indicate that the LUT is also used for ICC-absolute colorimetric. The lookup table
(LUT) has an input table and an output table. The dimension of the input table is n (rows) by m (columns),
where n is the number of data points, that is, the product of the numbers of the grid points over the number of
components of the input color space, and m is the number of components of the input color space in the audit
Color Conversion CMR. The dimension corresponding to the first input component varies least rapidly and the
dimension corresponding to the last input component varies most rapidly. The grid point values in each color
component of input color space are obtained by using the following equation:
E
i = M × i / (q – 1)
where i is the i th grid point of that color component (i starts from 0 and ends with q – 1) and q is the number of
the grid points for that color component. M is 255 for 1-byte UBIN and 65,535 for 2-byte UBIN.
The output table is in the form of an n (rows) by p (columns) array, where p is the number of components of the
output color space in the instruction Color Conversion CMR. Each entry in the output table is a function value
of the corresponding entry in the input table. Only the output table is shown in the LUT . The table values are
arrays of 8-bit or 16-bit values.
The size of the LUT = n × p × (precision of data elements in bytes).
Table 40. Link LUT Saturation Tag Syntax
Bytes Length Type Range Meaning
0 1 1-byte UBIN 1–15 Number of components of the input color space
1 1 1-byte UBIN 1–15 Number of components of the output color space
2–16 15 For each entry:
1-byte UBIN
For each entry:
0–255
Number of grid points in each component of the
input color space. There are 15 entries, each
encoded as one byte. Only the first m entries are
used, where m is the number of components of the
input color space. Unused entries should be set to
zeros.
17 1 1-byte UBIN
1
2
Precision of data elements:
1-byte UBIN
2-byte UBIN
18 1 BITS Additional use flags:
set to 0 if false, set to 1 if true
bits 0–1 B'00' Reserved
bit 2 B'0', B'1' ICC-absolute colorimetric
bits 3–7 B'00000' Reserved
19 1 0 Reserved
20 to
end
For each data
point:
1-byte UBIN
2-byte UBIN
For each data
point:
0–255
0–65,535
LUT data
Link LUT Saturation tag

## Page 99

CMOCA Reference 83
Number of components of the input color space
The number of components of the input color space in an audit Color Conversion CMR.
Number of components of the output color space
The number of components of the output color space in an instruction Color Conversion CMR.
Number of grid points
Number of grid points in each component of the input color space. The number of grid points in each
dimension is not necessarily the same. The decision on these numbers is implementation dependent. It
could be different from the number of grid points in each dimension of the input color space in the audit
color conversion. ICC allows a maximum of 15 color components in a color space. 15 bytes are allocated
for this header field.
Precision of data elements
The entry values can be either 1 byte or 2 bytes. The decision is implementation dependent.
Additional use flags
The flag indicates that the LUT is also used for ICC-absolute colorimetric.
LUT
data
The data in the LUT .
There is no default.
Exception Conditions:
EC-405005 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-405006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40500E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40500F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-405010 Invalid Value
The specified value is invalid or the offset caused some portion of the tag data to be outside of
the CMRdata.
EC-405011 Inconsistent T ag Contents
The LUT was provided in a previous tag.
• A Link LUT tag is provided and the flag for the rendering intent of this LUT was set in a
previous Link LUT tag.
• A flag for a rendering intent is set in multiple Link LUT tags.
Link LUT Saturation tag

## Page 100

84 CMOCA Reference
Link LUT ICC-Absolute Colorimetric
TagID X'4055'
Field Type X'05' (BYTE)
Count The number of bytes in the LUT + 20 bytes of the header
This tag defines a lookup table that converts the input color space of an audit Color Conversion CMR into the
output color space of an instruction Color Conversion CMR. The rendering intent of the LUT is ICC-Absolute
colorimetric. The lookup table (LUT) has an input table and an output table. The dimension of the input table is
n (rows) by m(columns), where n is the number of data points, that is, the product of the numbers of the grid
points over the number of components of the input color space, and m is the number of components of the
input color space in the audit Color Conversion CMR. The dimension corresponding to the first input
component varies least rapidly and the dimension corresponding to the last input component varies most
rapidly. The grid point values in each color component of input color space are obtained by using the following
equation:
E
i = M × i / (q – 1)
where i is the i th grid point of that color component (i starts from 0 and ends with q – 1) and q is the number of
the grid points for that color component. M is 255 for 1-byte UBIN and 65,535 for 2-byte UBIN.
The output table is in the form of an n (rows) by p (columns) array, where p is the number of components of the
output color space in the instruction Color Conversion CMR. Each entry in the output table is a function value
of the corresponding entry in the input table. Only the output table is shown in the LUT . The table values are
arrays of 8-bit or 16-bit values.
The size of the LUT = n × p × (precision of data elements in bytes).
Table 41. Link LUT ICC-Absolute Colorimetric Tag Syntax
Bytes Length Type Range Meaning
0 1 1-byte UBIN 1–15 Number of components of the input color space
1 1 1-byte UBIN 1–15 Number of components of the output color space
2–16 15 For each entry:
1-byte UBIN
For each entry:
0–255
Number of grid points in each component of the
input color space. There are 15 entries, each
encoded as one byte. Only the first m entries are
used, where m is the number of components of the
input color space. Unused entries should be set to
zeros.
17 1 1-byte UBIN
1
2
Precision of data elements:
1-byte UBIN
2-byte UBIN
18–19 2 0 Reserved
20 to
end
For each data
point:
1-byte UBIN
2-byte UBIN
For each data
point:
0–255
0–65,535
LUT data
Number of components of the input color space
The number of components of the input color space in an audit Color Conversion CMR.
Number of components of the output color space
The number of components of the output color space in an instruction Color Conversion CMR.
Link LUT ICC-Absolute Colorimetric tag

## Page 101

CMOCA Reference 85
Number of grid points
Number of grid points in each component of the input color space. The number of grid points in each
dimension is not necessarily the same. The decision on these numbers is implementation dependent. It
could be different from the number of grid points in each dimension of the input color space in the audit
color conversion. ICC allows a maximum of 15 color components in a color space. 15 bytes are allocated
for this header field.
Precision of data elements
The entry values can be either 1 byte or 2 bytes. The decision is implementation dependent.
LUT
data
The data in the LUT .
There is no default.
Exception Conditions:
EC-405505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-405506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40550E Missing Required T ag
The tag is required for the resource, but is missing.
EC-40550F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-405510 Invalid Value
The specified value is invalid or the offset caused some portion of the tag data to be outside of
the CMRdata.
EC-405511 Inconsistent T ag Contents
The LUT was provided in a previous tag.
• A Link LUT tag is provided and the flag for the rendering intent of this LUT was set in a
previous Link LUT tag.
• A flag for a rendering intent is set in multiple Link LUT tags.
Link LUT ICC-Absolute Colorimetric tag

## Page 102

86 CMOCA Reference
Link CMRE Identifier
TagID X'4090'
Field Type X'07' (UTF16)
Count Number of characters
This tag specifies the name and version of the CMR Engine used to generate the Link Color Conversion CMR.
There is no default.
Exception Conditions:
EC-409006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-40900F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-409010 Invalid Value
The offset caused some portion of the tag data to be outside of the CMRdata.
Link CMRE Identifier tag

## Page 103

CMOCA Reference 87
Indexed Subset
TagID X'5011'
Field Type X'08' (CODE)
Count 1
This tag denotes a subset of the Indexed CMR type. Currently, only one Indexed CMR subset is defined for the
multi-output color spaces. It allows a mixture of different output color spaces for an Indexed CMR. The color
spaces include gray, CMYK, RGB, CIELAB D50, and named colorants.
Table 42. Indexed CMR Subset
Subset ID Name
X'01' Multi-output color spaces
There is no default.
Exception Conditions:
EC-501105 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-501106 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50110E Missing Required T ag
The tag is required for the resource, but is missing.
EC-50110F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-501110 Invalid Value
The specified subset value is not X'01'.
Indexed Subset tag

## Page 104

88 CMOCA Reference
Number of Named Colorants
TagID X'5015'
Field Type X'01' (1-byte UBIN)
Count 1
This tag defines the number of named colorants referenced by this resource. This tag determines the number
of repeating groups in the Colorant Identification List tag and the length of each repeating group in the Color
Palette Named Colorants tag. The number of named colorants must be in the range of 1 to 15.
There is no default.
Exception Conditions:
EC-501505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-501506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50150E Missing Required T ag
The tag is required when the Color Palette Named Colorants tag and/or the Colorant
Identification List tag is specified, but is missing.
EC-50150F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-501510 Invalid Value
The specified number of named colorants is zero or greater than 15.
Number of Named Colorants tag

## Page 105

CMOCA Reference 89
Color Palette Gray
TagID X'5020'
Field Type X'05' (BYTE)
Count 9 × the number of color entries
This tag translates 2-byte indexed color values to the monochrome color space. It consists of 9-byte repeating
groups in the following format. Each repeating group maps an indexed color value to a gray value. Repeating
groups must be sorted in ascending order of indexed color value.
Table 43. Color Palette Gray Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN IndexedColorValue X'0100' – X'FFFF' 2-byte indexed color value specified in data stream
2–7 2-byte UBIN CIELABValue X'0000' – X'FFFF' L* component
a* component
b* component
8 1-byte UBIN Component_1 X'00'–X'FF' Intensity of gray , where X'00' is black (maximum
gray) and X'FF' is white (no gray)
All values must be specified. The CIELABValue is defined as a 2-byte CIELAB value with the D50 illuminant.
The CIELAB value from byte 2–7 is used for substitution if gray is not the output space of the device.
Note: The actual CIELAB ranges are: L* component: 0.0 to 100.0, a* and b* components: -128.0 to +127.0. All
these ranges need to be mapped to X'0000'–X'FFFF'.
There is no default.
Exception Conditions:
EC-502005 Invalid Count Value
The specified Count field value is invalid for the tag. It is less than 9 or it is not a multiple of 9.
EC-502006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50200F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-502010 Invalid Value
The IndexedColorValue is not valid or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-502012 Incorrect order of repeating groups
Repeating groups are not sorted in ascending order of IndexedColorValue.
Color Palette Gray tag

## Page 106

90 CMOCA Reference
Color Palette CMYK
TagID X'5025'
Field Type X'05' (BYTE)
Count 12 × the number of color entries
This tag translates 2-byte indexed color values to the CMYK color space. It consists of 12-byte repeating
groups in the following format. Each repeating group maps an indexed color value to a CMYK value. Repeating
groups must be sorted in ascending order of indexed color value.
Table 44. Color Palette CMYK Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN IndexedColorValue X'0100' – X'FFFF' 2-byte indexed color value specified in data stream
2–7 2-byte UBIN CIELABValue X'0000' – X'FFFF' L* component
a* component
b* component
8 1-byte UBIN Component_1 X'00'–X'FF' Intensity of cyan, where X'00' is no cyan and X'FF'
is maximum cyan
9 1-byte UBIN Component_2 X'00'–X'FF' Intensity of magenta, where X'00' is no magenta
and X'FF' is maximum magenta
10 1-byte UBIN Component_3 X'00'–X'FF' Intensity of yellow , where X'00' is no yellow and
X'FF' is maximum yellow
11 1-byte UBIN Component_4 X'00'–X'FF' Intensity of black, where X'00' is no black and X'FF'
is maximum black
All values must be specified. The CIELABValue is defined as a 2-byte CIELAB value with the D50 illuminant.
The CIELAB value from byte 2–7 is used for substitution if CMYK is not the output space of the device.
Note: The actual CIELAB ranges are: L* component: 0.0 to 100.0, a* and b* components: -128.0 to +127.0. All
these ranges need to be mapped to X'0000'–X'FFFF'.
There is no default.
Exception Conditions:
EC-502505 Invalid Count Value
The specified Count field value is invalid for the tag. It is less than 12 or it is not a multiple of
12.
EC-502506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50250F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-502510 Invalid Value
The IndexedColorValue is not valid or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-502512 Incorrect order of repeating groups
Repeating groups are not sorted in ascending order of IndexedColorValue.
Color Palette CMYK tag

## Page 107

CMOCA Reference 91
Color Palette RGB
TagID X'5030'
Field Type X'05' (BYTE)
Count 11 × the number of color entries
This tag translates 2-byte indexed color values to the RGB color space. It consists of 11-byte repeating groups
in the following format. Each repeating group maps an indexed color value to a RGB value. Repeating groups
must be sorted in ascending order of indexed color value.
Table 45. Color Palette RGB Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN IndexedColorValue X'0100' – X'FFFF' 2-byte indexed color value specified in data stream
2–7 2-byte UBIN CIELABValue X'0000' – X'FFFF' L* component
a* component
b* component
8 1-byte UBIN Component_1 X'00'–X'FF' Intensity of red, where X'00' is no red and X'FF' is
maximum red
9 1-byte UBIN Component_2 X'00'–X'FF' Intensity of green, where X'00' is no green and
X'FF' is maximum green
10 1-byte UBIN Component_3 X'00'–X'FF' Intensity of blue, where X'00' is no blue and X'FF' is
maximum blue
All values must be specified. The CIELABValue is defined as a 2-byte CIELAB value with the D50 illuminant.
The CIELAB value from byte 2–7 is used for substitution if RGB is not the output space of the device.
Note: The actual CIELAB ranges are: L* component: 0.0 to 100.0, a* and b* components: -128.0 to +127.0. All
these ranges need to be mapped to X'0000'–X'FFFF'.
There is no default.
Exception Conditions:
EC-503005 Invalid Count Value
The specified Count field value is invalid for the tag. It is less than 11 or it is not a multiple of
11.
EC-503006 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50300F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-503010 Invalid Value
The IndexedColorValue is not valid or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-503012 Incorrect order of repeating groups
Repeating groups are not sorted in ascending order of IndexedColorValue.
Color Palette RGB tag

## Page 108

92 CMOCA Reference
Color Palette CIELAB
TagID X'5035'
Field Type X'05' (BYTE)
Count 8 × the number of color entries
This tag translates 2-byte indexed color values to D50 CIELAB color space. The precision of the CIELAB
values is 2-byte. This tag consists of 8-byte repeating groups in the following format. Each repeating group
maps an indexed color value to a CIELAB value. Repeating groups must be sorted in ascending order of
indexed color value.
Table 46. Color Palette CIELAB Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN IndexedColorValue X'0100' – X'FFFF' 2-byte indexed color value specified in data stream
2–7 2-byte UBIN CIELABValue X'0000' – X'FFFF' L* component
a* component
b* component
All values must be specified. The CIELABValue is defined as a 2-byte CIELAB value with the D50 illuminant.
Note: The actual CIELAB ranges are: L* component: 0.0 to 100.0, a* and b* components: -128.0 to +127.0. All
these ranges need to be mapped to X'0000'–X'FFFF'.
There is no default.
Exception Conditions:
EC-503505 Invalid Count Value
The specified Count field value is invalid for the tag. It is less than 8 or it is not a multiple of 8.
EC-503506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50350F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-503510 Invalid Value
The IndexedColorValue is not valid or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-503512 Incorrect order of repeating groups
Repeating groups are not sorted in ascending order of IndexedColorValue.
Color Palette CIELAB tag

## Page 109

CMOCA Reference 93
Color Palette Named Colorants
TagID X'5040'
Field Type X'05' (BYTE)
Count (Number of Named Colorants + 8) × the number of color entries
This tag translates 2-byte indexed color values to the named colorants color space. It consists of (n+8)-byte
repeating groups in the following format, where n is the number of named colorants specified in the Number of
Named Colorants tag. Each repeating group maps an indexed color value to a mixture of n named colorants.
Repeating groups must be sorted in ascending order of indexed color value. Each field from byte 8 to 7+n
corresponds directly and in the same order to a Colorant Name specified in the Colorant Identification List tag,
that is, byte 8 corresponds to the first Colorant Name, byte 9 corresponds to the second Colorant Name, and
so on.
Table 47. Color Palette Named Colorants Tag Syntax
Offset Type Name Range Meaning
0–1 2-byte UBIN IndexedColorValue X'0100' – X'FFFF' 2-byte indexed color value specified in data stream
2–7 2-byte UBIN CIELABValue X'0000' – X'FFFF' L* component
a* component
b* component
8 1-byte UBIN Component_1 X'00'–X'FF' Intensity of the first colorant, where X'00' is no
colorant and X'FF' is maximum colorant
9 1-byte UBIN Component_2 X'00'–X'FF' Intensity of the second colorant, where X'00' is no
colorant and X'FF' is maximum colorant
…
7+n 1-byte UBIN Component_n X'00'–X'FF' Intensity of the nth colorant, where X'00' is no
colorant and X'FF' is maximum colorant
All values must be specified. The CIELABValue is defined as a 2-byte CIELAB value with the D50 illuminant.
The CIELAB value from byte 2–7 is used for substitution if any colorant required for this index is not available
in the device. Note that, if the intensity of a certain component is X'00' for a particular IndexedColorValue, then
that colorant is not required for that index.
Note: The actual CIELAB ranges are: L* component: 0.0 to 100.0, a* and b* components: -128.0 to +127.0. All
these ranges need to be mapped to X'0000'–X'FFFF'.
There is no default.
Implementation Note: Some devices may use this palette to resolve named spot colors like those in the
Pantone
® color system to device colors. In this case, only one component in a repeating group for this
palette has a nonzero intensity value.
Exception Conditions:
EC-504005 Invalid Count Value
The specified Count field value is invalid for the tag. It is less than (Number of Named
Colorants + 8) or it is not a multiple of (Number of Named Colorants + 8).
EC-504006 Invalid Field Type
The specified Field Type is invalid for the tag.
Color Palette Named Colorants tag

## Page 110

94 CMOCA Reference
EC-50400E Missing Required T ag
At least one Color Palette tag is required but none were specified.
EC-50400F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-504010 Invalid Value
The IndexedColorValue is not valid or the offset caused some portion of the tag data to be
outside of the CMRdata.
EC-504012 Incorrect order of repeating groups
Repeating groups are not sorted in ascending order of IndexedColorValue.
Color Palette Named Colorants tag

## Page 111

CMOCA Reference 95
Colorant Identification List
TagID X'5045'
Field Type X'05' (BYTE)
Count Sum of the length over the Number of Named Colorants
This tag specifies colorant names in free format UTF-16BE. The colorant name is used to identify the required
colorant, for example: Pantone xyz. Each colorant name is from 1–125 characters (2–250 bytes) long. Names
starting with @SPECIAL-COLORANT@ are reserved for special use such as “mark color”. The tag consists of
n repeating groups for the named colorants in the following format, where n is the number of named colorants
specified in the Number of Named Colorants tag.
Table 48. Colorant Identification List Tag Syntax
Offset Type Name Range Meaning
0 1-byte UBIN Length X'03'–X'FB' Length of this repeating group, including length
field
1–end UTF-16 Colorant Name Colorant name in free format UTF-16BE
The following colorant names can be used to specify usage of colorants in the device color space:
AFPC_Device_C Device Cyan
AFPC_Device_M Device Magenta
AFPC_Device_Y Device Yellow
AFPC_Device_K Device Black
AFPC_Device_R Device Red
AFPC_Device_G Device Green
AFPC_Device_B Device Blue
AFPC_Device_Gray Retired item 4
There is no default.
Exception Conditions:
EC-504505 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-504506 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-50450E Missing Required T ag
The tag is required when the Color Palette Named Colorants tag is specified, but is missing.
EC-50450F Invalid Sequence
The tag has been encountered out of sequence or more than once.
EC-504510 Invalid Value
The Length in one of the repeating groups is not valid, the number of repeating groups
specified does not match the Number of Named Colorants tag value, or the offset caused
some portion of the tag data to be outside of the CMRdata.
EC-504513 Duplicate value
A Colorant Name appears in more than one repeating group.
Colorant Identification List tag

## Page 112

96 CMOCA Reference
End Data
TagID X'FFFF'
Field Type X'05' (BYTE)
Count 0
This tag signifies the end of the tag list. This tag must be present for every CMR type, or exception EC-FFFF0F
exists.
Exception Conditions:
EC-FFFF05 Invalid Count Value
The specified Count field value is invalid for the tag.
EC-FFFF06 Invalid Field Type
The specified Field Type is invalid for the tag.
EC-FFFF0E Missing Required T ag
The tag is required for the resource, but is missing.
End Data tag

## Page 113

Copyright © AFP Consortium 2006, 2025 97
