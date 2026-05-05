Chapter 4. Metadata Object (MO)
An MO consists of a header followed by MO data.

## Page 26

12 MOCA Reference
MO Syntax
This is the syntax of an MO.
Data contained in fixed-length fields that are encoded as UTF-16BE is left-aligned. If the number of bytes used
by the characters in these fields is smaller than the field length, the remaining bytes will be padded with “@”
(X'0040').
Table 4. MO Syntax
Length
in Bytes
Offset Type Name Range Meaning M/O
4 0-3 UBIN MOLength X'00000032' - X'FFFFFFFF' MO length, including this
MOLength field
M
MO header starts here
2 4-5 UBIN HeaderLength X'002E' - end of header MO header length,
including this
HeaderLength field
M
6 6-11 UTF16 MOType DES (X'0044 0045 0053') Descriptive M
8 12-19 UTF16 MOFormat AFPT (X'0041 0046 0050
0054')
AFP T agging M
XMP (X'0058 004D 0050
0040')
Extensible Metadata
Platform (XMP)
20 20-39 UTF16 MOCompression NONE (X'004E 004F 004E
0045 0040 0040 0040 0040
0040 0040')
Uncompressed M
GZIP (X'0047 005A 0049
0050 0040 0040 0040 0040
0040 0040')
“Gzip” text compression
EXI (X'0045 0058 0049
0040 0040 0040 0040 0040
0040 0040')
Efficient XML Interchange
(EXI) compression
8 40-47 X'0000000000000000' Reserved - should be set
to zero
M
2 48-49 UBIN MONameLength X'0000' - X'00FA', even
values only
Length, in bytes, of the
MOName field that follows
M
0-250 50-end of
name
UTF16 MOName Any valid UTF-16BE
characters (thus an even
number of bytes)
A human-readable MO
name in UTF-16BE
O
End of
name-end
of header
UNDF Reserved for future use;
receivers should accept
but ignore; generators
should not specify
O
MO header ends here
MOData Any MO Data O
M/O: Mandatory or Optional field
Metadata Object (MO)

## Page 27

MOCA Reference 13
MO Semantics
MOLength The length of the complete MO, including the MOLength parameter. MOLength, in
bytes, may be 50 (X'00000032') to X'FFFFFFFF'. If an invalid value is found in this
field, the optional exception is EC-0100.
HeaderLength The length of the MO header, including the HeaderLength parameter. HeaderLength,
in bytes, may be any value greater than or equal to 46 (X'002E'). If an invalid value is
found in this field, the optional exception is EC-0200.
MOType One MOType is defined in the Metadata Object Content Architecture. The defined
MOType is DES. See “MOType” on page 15. If an invalid or unsupported value is
found in this field, the optional exception is EC-0220.
MOFormat MOFormat is defined in the Metadata Object Content Architecture to indicate the
format of MO data.
See “MOFormat” on page 15. If an invalid or unsupported value is
found in this field, the optional exception is EC-0230.
MOCompression MOCompression is defined in the Metadata Object Content Architecture to indicate
the type of compression applied to MO data. See “MOCompression” on page 16. If an
invalid or unsupported value is found in this field, the optional exception is EC-0240.
MONameLength The length of the MOName field. MONameLength, in bytes, may be any even value
from 0 (X'0000') to 250 (X'00FA'). If an invalid value is found in this field, the optional
exception is EC-0250.
MOName A user-defined string containing an optional human-readable MO name. This field can
contain up to 250 bytes; therefore, if the UTF-16BE string contains no surrogates, the
MO name can contain up to 125 characters. If the environment containing the MO has
a method of referencing an MO by name, this field is to be used as the name. If an
invalid value is found in this field, the optional exception is EC-0210.
MOData MO data. The format of the metadata is determined by the value of the MOFormat
parameter. If an invalid value is found in this field, the optional exception is EC-0300.
Metadata Object (MO)

## Page 28

14 MOCA Reference
MO Exception Conditions
Both recognition and reporting of exception conditions is optional. Exception conditions have a format of
EC-xxxx.
The exception conditions are as follows:
EC-0100 Invalid Length Value
The specified MOLength is invalid.
EC-0200 Invalid Field Value
The specified HeaderLength is invalid.
EC-0210 Invalid Field Value
The specified MOName is not valid UTF-16BE.
EC-0220 Invalid or Unsupported Field Value
The specified MOType is invalid or unsupported.
EC-0230 Invalid or Unsupported Field Value
The specified MOFormat is invalid or unsupported.
EC-0240 Invalid or Unsupported Field Value
The specified MOCompression is invalid or unsupported.
EC-0250 Invalid Field Value
The specified MONameLength is invalid.
EC-0300 Invalid MOData
The specified MOData does not meet the specification associated with the indicated
MOFormat.
In the IPDS environment, MOCA exception conditions are mapped to IPDS exceptions and reported. T o map a
MOCA exception condition to an IPDS exception, the rule is simply to add X'06' on the front of the four digits of
the MOCA exception condition. For example, MOCA exception condition EC-0220 becomes IPDS exception
X'0602..20'.
Metadata Object (MO)

## Page 29

Copyright © AFP Consortium 2014–2024 15
