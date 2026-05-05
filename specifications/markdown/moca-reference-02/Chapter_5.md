Chapter 5. MO Header Attributes
Attribute fields for MO type, format, and compression are carried in the MO header. Each of these fields is
described below.
MOType
The following MO types are defined:
• DES
Each value for MOType is described in more detail below.
DES
MOType = DES (X'004400450053')
Description
MOType DES refers to descriptive metadata used to label, tag, or otherwise describe elements of a print file.
Common descriptive metadata are attributes such as Title, Date, and Author. Descriptive metadata is distinct
from the primary content of a document and does not affect the architected rendering of data.
MOFormat
The following MO formats are defined:
• AFP T agging
• XMP
Each value for MOFormat is described in more detail below.
AFP Tagging
MOFormat = AFPT (X'0041004600500054')
Description
MOFormat AFPT refers to a metadata object using the AFP T agging definition, which defines a schema in XML
format for both identifying some semantics and tagging the data in the AFP that correspond to those
semantics. For example, AFP T agging metadata can be used to state that some set of bytes in the AFP
correspond to a figure, or a paragraph, or a hyperlink; this type of information could be used in a screen reader,
to enable universal accessibility.
See the Metadata Guide for AFP for the definition of the syntax of AFP T agging metadata.
XMP
MOFormat = XMP (X'0058004D00500040')
Description

## Page 30

16 MOCA Reference
MOFormat XMP refers to a metadata object using the Extensible Metadata Platform (XMP) data model,
serialization, and core properties.
See the XMP Specification dated September 2005 for a complete specification of XMP .
MOCompression
The following MO compression formats are defined:
• NONE
• GZIP
• EXI
Each value for MOCompression is described in more detail below.
NONE
MOCompression = NONE (X'004E004F004E0045004000400040004000400040')
Description
The MO data is uncompressed.
GZIP
MOCompression = GZIP (X'0047005A00490050004000400040004000400040')
Description
The MO data is compressed as text using GZIP .
See RFC 1952 - GZIP file format specification version 4.3.
EXI
MOCompression = EXI (X'0045005800490040004000400040004000400040')
Description
The MO data is compressed as XML using Efficient XML Interchange.
See Efficient XML Interchange (EXI) Format 1.0.
MO Header Attributes

## Page 31

Copyright © AFP Consortium 2014–2024 17
