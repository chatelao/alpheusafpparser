Chapter 6. Exception Conditions and Actions
This chapter outlines the exception conditions and exception actions for IOCA, and summarizes:
• Exception conditions causing the common standard action
• Exception conditions causing unique standard actions
An exception condition is either mandatory or optional. If a function is supported and a mandatory exception
condition for the function is detected, the process must notify the controlling environment. If an optional
exception condition for the function is detected, the process might or might not need to notify the controlling
environment.
The table in “Mandatory or Optional Exception Conditions” on page 83 summarizes for each IOCA exception
condition whether it is mandatory or optional. Also shown in the table is whether the two primary IOCA
controlling environments–MO:DCA and IPDS–would consider the exception condition to be mandatory or
optional.
Exception Conditions
Exception conditions include violations of the following:
• Syntax
• Parameter value
• Self-defining field sequence
Exceptions are represented in the following format:
eee-xxxx
where:
eee identifies the exception group. The exception group can be one of the following:
EC Image Segment exception
xxxx is the exception code (two-byte hexadecimal value).
There are two types of exception conditions:
• Those that cause the common standard action
• Those that cause unique standard actions
The exception conditions are summarized in the following sections. Unique exception conditions and actions
that are related to a specific element are described in the corresponding section for the element.
Image Segment Exception Conditions
This exception occurs when a violation of format, parameter, or sequence of execution to this architecture is
detected in the Image Segment.
The exception is represented in the following format:
EC-xxxx
where:
EC identifies an Image Segment exception condition.
xxxx is the Image Segment exception condition code (two-byte hexadecimal value).
The following Image Segment exception conditions are defined:
• Exception conditions that cause the common standard action

## Page 96

78 IOCA Reference
• Exception conditions that cause unique standard actions
The IOCA Process Model recovers the exception condition according to the severity of the exception. The
severity of an exception depends on the Image Data parameter or the Image Data.
If an exception action is defined in the corresponding section, the action is taken first, and the exception
condition is kept until it is reported to the controlling environment.
If the action is not defined in the corresponding section, the rest of the Image Segment is not processed and
the exception condition is reported immediately to the controlling environment.
Exception Actions
The IOCA Process Model responds with an exception action when it detects an exception condition.
An exception condition prompts either of the following kinds of actions:
• An exception action defined by IOCA
• An alternative action that is defined outside the IOCA Process Model
The controlling environment governs which way the IOCA Process Model responds to the exception
conditions. For example, the IPDS architecture has a command to specify whether the IPDS-defined page
continuation action or the IOCA-defined exception action is to be taken.
IOCA Process Model Actions
The IOCA Process Model recovers the exception condition according to the severity of the exception. The
severity depends on the condition where the exception is detected.
The exception conditions are reset after the controlling environment has been notified.
Unique Standard Actions
If a unique exception action is defined for the exception condition (such as for EC-9401 and EC-9511), the
IOCA Process Model:
• Notifies the controlling environment of the condition
• Performs the defined unique action
Common Standard Action
If no unique exception action is defined, the IOCA Process Model:
• Notifies the controlling environment of the condition
• Does not process the rest of the Image Segment
The exception conditions are reset after the controlling environment has been notified.
Exception Conditions

## Page 97

IOCA Reference 79
Exception Conditions Causing the Common Standard Action
EC-0001 Invalid or unsupported code within an Image Segment
Condition: An invalid or unsupported self-defining field is detected within the Image Segment.
EC-0002 Retired
Condition: Retired. See Appendix G, “Retired Architecture”, on page 177 for information about this retired exception
condition.
EC-0003 The LENGTH value is not in the valid range
Condition: The LENGTH value is not in the valid range.
EC-0004 Invalid parameter value
Condition: A parameter value is not in the valid range.
Note: In cases where this exception is being generated for self-defining fields for which an EC-xx10 exception is available,
it is recommended that IOCA receivers generate the EC-xx10 exception instead of exception EC-0004. For example,
for the IDE Size parameter, EC-9610 would be generated and not EC-0004.
EC-0005 Invalid length
Condition: The LENGTH value is not in the valid, function-set specified range. EC-0005 is optional—IOCA receivers can
generate EC-0003 instead of EC-0005.
EC-xx0F Invalid sequence
Condition: The sequence of self-defining fields is not correct within an Image Segment. This exception condition is also
raised when a mandatory or necessary self-defining field is missing, or when a self-defining field other than Image Data, or
Band Image Data, appears more than once in an Image Segment. Value xx in the exception code depends on the
parameter in which the exception occurred. The parameter code is placed in this xx position.
For example:
• EC-700F for Begin Segment
• EC-710F for End Segment (see Note below)
• EC-8C0F for Begin Tile
• EC-8D0F for End Tile
• EC-8E0F for Begin Transparency Mask
• EC-8F0F for End Transparency Mask
• EC-910F for Begin Image Content
• EC-920F for Image Data (see Note below)
• EC-930F for End Image Content
• EC-940F for Image Size
• EC-950F for Image Encoding
• EC-960F for IDE Size
• EC-970F (Retired)
• EC-980F for Band Image
• EC-9B0F for IDE Structure
• EC-9C0F for Band Image Data (see Note below)
• EC-9F0F for External Algorithm Specification
• EC-B30F for nColor Names
• EC-B50F for Tile Position
• EC-B60F for Tile Size
• EC-B70F for Tile Set Color
• EC-B80F for Include Tile
• EC-BB0F for Tile TOC
• EC-CE0F for Image Subsampling
Note: This exception condition is not raised when the indicated self-defining field appears more than once.
Exceptions

## Page 98

80 IOCA Reference
EC-xx10 Invalid or unsupported Image Data parameter value
Condition: The specified value for a parameter is not valid in the architecture or function sets, or is not supported by the
implementation. Value xx in the exception code depends on the parameter in which the exception occurred. The parameter
code is placed in this xx position.
For example:
• EC-9410 for Image Size
• EC-9510 for Image Encoding
• EC-9610 for IDE Size
• EC-9710 (Retired)
• EC-9810 for Band Image
• EC-9B10 for IDE Structure
• EC-9F10 for External Algorithm Specification
• EC-B310 for nColor Names
• EC-B510 for Tile Position
• EC-B610 for Tile Size
• EC-B710 for Tile Set Color
• EC-BB10 for Tile TOC
• EC-CE10 for Image Subsampling
Note: Some controlling environments, such as IPDS, define an exception for an invalid value in the Set Extended Bilevel
Image Color self-defining field. Therefore, exception condition EC-F410 is reserved in IOCA for this use.
EC-xx11 Inconsistent Image Data parameters, or inconsistent Image Data parameter and Image Data
Condition: The specified value for a parameter is not consistent with a value specified in another parameter or with the
image data following it. Value xx in the exception code depends on the parameter in which the exception occurred. The
parameter code is placed in this xx position.
For example:
• EC-9411 for Image Size
• EC-9611 for IDE Size
• EC-9F11 for External Algorithm Specification
• EC-B311 for nColor Names
• EC-B511 for Tile Position
• EC-B611 for Tile Size
• EC-B711 for Tile Set Color
• EC-B811 for Include Tile
• EC-BB11 for Tile TOC
EC-9201 Invalid existence of Image Data
Condition: Image Data should not be present, as in the case when the image data is in bands.
EC-9801 Invalid Band Image parameter and Image Subsampling parameter coexistence
Condition: In some function sets, the Band Image parameter and the Image Subsampling parameter cannot coexist in the
same Image Content.
EC-9814 Invalid number of bands and bit counts
Condition: The number of BITCNT parameters is not equal to the BCOUNT in the Band Image parameter.
EC-9815 Invalid IDE size
Condition: The IDE size, determined by the Band Image parameter, does not match the IDE Size parameter.
Exceptions

## Page 99

IOCA Reference 81
EC-9B18 Invalid IDE Structure parameter
Condition: One of the following conditions occurs in the IDE Structure parameter:
• The sum of the SIZE values does not match the IDE size specified by the IDE Size parameter.
• The color model is CMYK and SIZE4 is missing.
• SIZE4 is present and the color model is not CMYK or nColor.
• More than four SIZE parameters are present and the color model is not nColor.
• The color model is nColor and the number of SIZE parameters is not equal to the second half of the FORMAT byte.
EC-9C01 Invalid existence of Band Image Data
Condition: Band Image Data should not be present, as in the case when the image data is not in bands.
EC-9C17 Invalid number/sequence of Band Image Data
Condition: The band numbers in a Band Image Data do not appear for the number of bands defined in the Band Image
parameter, or do not appear in succeeding order.
EC-9F01 Missing External Algorithm Specification parameter or Image Encoding parameter
Condition: An External Algorithm Specification parameter exists without a corresponding Image Encoding parameter, or
an Image Encoding parameter exists that requires an External Algorithm Specification parameter that cannot be found.
EC-CE01 Invalid Band Image parameter and Image Subsampling parameter coexistence
Condition: In some function sets, the Band Image parameter and the Image Subsampling parameter cannot coexist in the
same Image Content.
Exceptions

## Page 100

82 IOCA Reference
Exception Conditions Causing Unique Standard Actions
EC-9401 Inconsistent Image Size parameter value and Image Data
Condition: The size detected in the image data is different from the HSIZE or VSIZE value of the Image Size parameter.
System action: The size detected from the image data is used.
EC-9511 Inconsistent Image Data parameters, or inconsistent Image Data parameter and Image Data
Condition: The decoder encountered one of the following conditions when decompressing the image data:
• The image data is not encoded according to the compression or recording algorithm specified in the Image Encoding
parameter.
• The image data cannot be decoded successfully using the size values specified in the Image Size parameter. This
condition applies to compression or recording algorithms that do not permit the image size to be encoded in the image
data.
• The image data is not in complete accordance with the compression algorithm specified in the Image Encoding
parameter.
• The image data is encoded using the algorithm specified in the Image Encoding parameter, but uses a function of the
algorithm that is unsupported by the receiver.
System action: Receivers should attempt to present or make use of all successfully decompressed image data. Note that
the resulting partial image might differ from the original image.
EC-A902 Write outside of the Image Presentation Space
Condition: A portion of the extracted image is written outside the Image Presentation Space.
System action: The portion inside the Image Presentation Space is presented, and the rest is discarded.
Exceptions

## Page 101

IOCA Reference 83
Mandatory or Optional Exception Conditions
Exception IOCA MO:DCA IPDS
EC-0001 Mandatory Same as IOCA Same as IOCA
EC-0003 Mandatory Same as IOCA Same as IOCA
EC-0004 Mandatory Same as IOCA Same as IOCA
EC-0005 Optional* Same as IOCA Same as IOCA
EC-xx0F Mandatory Same as IOCA Same as IOCA
EC-xx10 Mandatory Same as IOCA Same as IOCA
EC-xx11 Mandatory Same as IOCA Same as IOCA
EC-9201 Mandatory Same as IOCA Same as IOCA
EC-9401 Mandatory Same as IOCA Same as IOCA
EC-9801 Mandatory Same as IOCA Same as IOCA
EC-9814 Mandatory Same as IOCA Same as IOCA
EC-9815 Mandatory Same as IOCA Same as IOCA
EC-9B18 Mandatory Same as IOCA Same as IOCA
EC-9C01 Mandatory Same as IOCA Same as IOCA
EC-9C17 Mandatory Same as IOCA Same as IOCA
EC-9F01 Mandatory Same as IOCA Same as IOCA
EC-A902 Mandatory Same as IOCA Same as IOCA
EC-CE01 Mandatory Same as IOCA Same as IOCA
*Receiver can generate EC-0003 instead of EC-0005.
Mandatory or Optional Exception Conditions

## Page 102

84 IOCA Reference

## Page 103

Copyright © AFP Consortium 2010, 2024 85
