# Chapter 3. MO:DCA Overview
This chapter:
• Describes the general syntax and semantics for MO:DCA structured fields [MODCA-3-001]
• Describes state, as defined by the MO:DCA architecture [MODCA-3-002]
• Describes the types and categories of MO:DCA parameters [MODCA-3-003]
• Describes conventions used in the MO:DCA architecture for coordinate systems, measurement units, and [MODCA-3-004]
rotation units
• Describes MO:DCA mixing rules [MODCA-3-005]
• Describes MO:DCA color management [MODCA-3-006]
• Describes MO:DCA metadata objects [MODCA-3-007]
• Describes font technologies used in MO:DCA documents [MODCA-3-008]
• Describes MO:DCA document indexing [MODCA-3-009]
• Describes other aspects of MO:DCA document presentation [MODCA-3-010]
• Describes and defines the MO:DCA exception conditions [MODCA-3-011]
## MO:DCA Data Structures
Each component of a mixed object document is explicitly defined and delimited in the data stream that
transmits it. This is accomplished through the use of MO:DCA data structures, called structured fields, that
reside in the data stream. Structured fields are used to envelop document components and to provide
commands and information to applications using the data stream. Structured fields may contain one or more
parameters. Each parameter provides one value from a set of values defined by the architecture. [MODCA-3-012]
## Notation Conventions
In addition to the information provided in “How to Read the Syntax Diagrams” , the following notation
conventions apply throughout this document:
• Bytes are numbered from left to right beginning with byte zero, which is considered the high order (most [MODCA-3-013]
significant) byte position. This is referred to as big-endian byte order. For example, a three-byte field would
consist of byte zero, byte one, and byte two.
• Each byte is composed of eight bits. [MODCA-3-014]
• Bits in a single byte are numbered from left to right beginning with bit zero, the most significant bit, and [MODCA-3-015]
continuing through bit seven, the least significant bit. This is referred to as big-endian bit order.
• When bits from multiple consecutive bytes are considered together, the first byte always contains bits zero to [MODCA-3-016]
seven and the bits of the additional bytes are numbered eight to n, where n is equal to one less than the total
number of bytes multiplied by eight. For example, a two-byte field would consist of bits zero to fifteen and a
four-byte field would consist of bits zero to thirty-one.
• Negative numbers are expressed in two's-complement form. See “Number” for details. [MODCA-3-017]
• Field values are expressed in hexadecimal or binary notation: [MODCA-3-018]
B'01111110' = X'7E' = +126
X'7FFF' = +32,767
X'8000' = -32,768 (when signed binary is used)
X'8000' = +32,768 (when unsigned binary is used) [MODCA-3-019]

## MO:DCA Structured Field Syntax
MO:DCA structured fields consist of two parts: an introducer that identifies the length and type of the structured
field, and data that provides the structured field's effect. The data is contained in a set of parameters, which
can consist of other data structures and data elements. The maximum length of a structured field is 32,767
bytes. The general format for a structured field is as follows:
Structured Field Introducer
Length (2B) Identifier (3B) Flags (1B) Reserved;
X'0000'
Extension Data Padding
Structured Field Introducer
The MO:DCA Structured Field Introducer (SFI) introduces a structured field, and identifies its type and its
length. SFIs have the following format:
SFI Syntax
Table 6. Structured Field Introducer (SFI)
Offset Type Name Range Meaning M/O Exc
0–1 UBIN SFLength 8–32,767 T otal length of the structured field
including the length of the
introducer
M X'82'
2–4 CODE SFTypeID A three-byte code that uniquely
identifies the structured field. See
“SFI Semantics” for a
description.
M X'78'
5 BITS FlagByte Used to indicate whether an [MODCA-3-020]
extension, segmentation, or
padding is in use
M X'82'
Bit 0 ExtFlag B'0', B'1' B'0' No SFI extension exists
B'1' SFI extension is present
Bit 1 Reserved; should be zero
Bit 2 SegFlag B'0', B'1' B'0' Data is not segmented
B'1' Data is segmented
Bit 3 Reserved; should be zero
Bit 4 PadFlag B'0', B'1' B'0' No padding data exists
B'1' Padding data is present
Bits 5–7 Reserved; should be zero
6–7 Reserved; should be zero M X'82'
The following optional extension appears only if bit 0 of FlagByte is B'1':
Structured Field Syntax [MODCA-3-021]

Table 6 Structured Field Introducer (SFI) (cont'd.)
Offset Type Name Range Meaning M/O Exc
8 UBIN ExtLength 1–255 Length of the extension including [MODCA-3-022]
the length of ExtLength itself
O X'82'
9 ExtData Reserved O X'00' [MODCA-3-023]
SFI Semantics
SFLength Defines the length of the structured field, including itself.
Application Note: Some platforms include structured fields in a larger platform-specific
record by surrounding the structured field with additional bytes (such as the X'5A' prefix).
This can result in a record length greater than X'7FFF' if the structured field length is
X'7FFF'. Such a record length can be misinterpreted as a negative number if the length
is treated as SBIN. T o ensure portability of MO:DCA print files, it is strongly
recommended that the maximum structured field length be limited to X'7FF0' = 32,752,
which avoids such record length issues on the known platforms. Note that MO:DCA
interchange sets have traditionally limited the maximum structured field length.
MO:DCA IS/3 limits the length to X'7FF0' = 32,752, MO:DCA IS/1 and IS/2 limit the
length to X'2000' = 8,192.
SFTypeID A three-byte field that uniquely identifies the structured field. It has the form D3TTCC, where:
Code Description
D3 The structured field class code that has been assigned to the MO:DCA
architecture.
TT The structured field type code. The type code identifies the function of the
structured field, such as begin, end, descriptor, or data. See “Type Codes” on
page 22 for a description of type codes.
CC The structured field category code. It identifies the lowest level component
that can be constructed using the structured field, such as document, active
environment group, page, or object. The same category code point assigned
to a component's begin structured field also is assigned to that component's
end structured field. These code points identify and delimit an entire
component within a data stream or an encompassing component. See
“Category Codes” for a description of category codes.
FlagByte Specifies the value of the optional indicators. Indicator bits are defined as follows:
Bit Indicator name and meaning
0 ExtFlag is the SFI extension flag. See “Structured Field Introducer Extension” [MODCA-3-024]
for details.
B'0' No SFI extension exists.
B'1' This structured field has an SFI extension.
2 SegFlag is the segmentation flag. See “Structured Field Segmentation” on [MODCA-3-025]
page 24 for details.
B'0' No segmentation in effect.
B'1' The data for this structured field has been segmented.
4 PadFlag is the padding flag. See “Structured Field Padding” for [MODCA-3-026]
details.
B'0' No padding data appended.
B'1' Padding data has been appended to the end of this
structured field.
Structured Field Syntax [MODCA-3-027]

All others Reserved; should be binary zero
Bytes 6–7 Reserved; should be zero
Application Note: In AFP environments, some applications use bytes 6–7 of the Structured
Field Introducer to specify a sequence number for the structured field. This is an
unarchitected use of these bytes and should be avoided.
ExtLength Specifies the length of the SFI extension, including the length of ExtLength itself. For
ExtLength to be valid, bit 0 of FlagByte must be B'1'.
ExtData Contains up to 254 bytes of application-defined SFI extension data. For ExtData to be valid, bit
0 of FlagByte must be B'1'. [MODCA-3-028]
Type Codes
The following type codes have been defined. All other type codes are reserved.
Table 7. Type Codes
Type Code Function Description
X'A0' Attribute An attribute structured field defines an attribute with parameters
such as name and value.
X'A2' Copy Count A copy count structured field specifies groups of sheet copies,
called copy subgroups, that are to be generated, and identifies
modification control structured fields that specify modifications to be
applied to each group.
X'A6' Descriptor A descriptor structured field defines the initial characteristics and,
optionally, the formatting directives for all objects, object areas, and
pages. Depending on the specific descriptor structured field type, it
may contain some set of parameters that identify:
• The size of the page or object [MODCA-3-029]
• Measurement units [MODCA-3-030]
• Initial presentation conditions [MODCA-3-031]
X'A7' Control A control structured field specifies the type of modifications that are
to be applied to a group of sheet copies, or a copy subgroup.
X'A8' Begin A begin structured field introduces and identifies a document
component. In general, a begin structured field may contain a
parameter that identifies the name of the component.
X'A9' End An end structured field identifies the end of a document component.
In general, an end structured field may contain a parameter that
identifies the name of the component.
X'AB' Map A map structured field provides the following functions in the
MO:DCA architecture:
• All occurrences of a variable embedded in structured field [MODCA-3-032]
parameter data can be given a new value by changing only one
reference in the mapping, rather than having to physically change
each occurrence. Thus all references to font X may cause a
Times Roman font to be used in one instance and a Helvetica
font in another instance merely by specifying the proper map
coded font structured field.
• The presence of the map structured field in a MO:DCA [MODCA-3-033]
environment group indicates use of the named resource within
the scope of the environment group.
X'AC' Position A position structured field specifies the coordinate offset value and
orientation for presentation spaces.
Structured Field Syntax [MODCA-3-034]

Table 7 Type Codes (cont'd.)
Type Code Function Description
X'AD' Process A process structured field specifies processing to be performed on
an object.
X'AF' Include An include structured field selects a named resource which is to be
embedded in the including data stream as if it appeared inline.
External resource object names on the begin structured field may or
may not coincide with the library name of that object, as library
name resolution is outside the scope of the MO:DCA architecture.
X'B0' Reserved See MO:DCA-L: The OS/2 PM Metafile (.met) Format, available at:
www.afpcinc.org.
X'B1' Migration A migration structured field is used to distinguish the MO:DCA
structured field from a structured field with the same acronym from
an earlier data-stream architecture. The earlier version is called
Format 1. The MO:DCA version is called Format 2.
X'B2' Variable A variable structured field defines or contains variable information.
X'B4' Link A link structured field defines a logical connection, or linkage,
between two document components.
X'EE' Data A data structured field consists of data whose meaning and
interpretation is governed by the object architecture for the
particular data object type.
Category Codes
The following category codes have been defined. All other category codes are reserved.
Category Code Description
X'5F' Page Segment
X'6B' Object Area
X'77' Reserved. See MO:DCA-L: The OS/2 PM Metafile (.met) Format, available at:
www.afpcinc.org.
X'7B' IM Image
X'88' Medium
X'8A' Coded Font
X'90' Process Element
X'92' Object Container
X'9B' Presentation T ext
X'A5' Print File
X'A7' Index
X'A8' Document
X'AD' Page Group
X'AF' Page
X'BB' Graphics
X'C3' Data Resource
X'C4' Document Environment Group (DEG)
X'C6' Resource Group
X'C7' Object Environment Group (OEG)
X'C9' Active Environment Group (AEG)
X'CC' Medium Map
X'CD' Form Map
X'CE' Name Resource
Structured Field Syntax [MODCA-3-035]

X'D8' Page Overlay
X'D9' Resource Environment Group (REG)
X'DC' Preprinted Form Overlay
X'DF' Overlay
X'EA' Data Suppression
X'EB' Bar Code
X'EE' No Operation
X'FB' Image
Structured Field Data
The structured field's data is contained in a parameter set that immediately follows the structured field's
introducer. The syntax and semantics for each MO:DCA structured field parameter set is given in Chapter 5,
“MO:DCA Structured Fields”,. Depending on the structured field and its purpose, the parameter
set may contain zero or more parameters. If parameters are present, they contain specific information
appropriate for the structured field. The data occupies as many bytes as needed, up to a maximum of 32,759
bytes.
Structured Field Introducer Extension
A structured field introducer may be extended by up to 255 bytes. The presence of an SFI extension is
indicated by a value of B'1' in bit 0 of the SFI flag byte. If an extension is present, the introducer is at least 8
bytes, but not more than 263 bytes, in length. The first byte of the extension specifies its length. If an extension
to the structured field introducer is present, the structured field's data can occupy up to 32,759 bytes, less the
length of the extension.
Structured Field Segmentation
When the total length of the introducer and the data portion of a structured field exceeds 32,767 bytes, the data
must be split into two or more fragments and specified on multiple consecutive structured fields. This is known
as segmenting a structured field. Segmenting normally only occurs for those structured fields that contain OCA
data.
When a structured field is segmented, the OCA may require that the data be split on specific data element
boundaries. The MO:DCA architecture permits other structured fields to be interspersed between the
segmented structured fields. However, for those cases where it is undesirable to split the data at a specific
boundary or to permit other structured fields to appear between the segmented structured fields, the MO:DCA
architecture provides a segmentation flag. This flag indicates that the segmented structured fields are all part
of a single, uninterrupted parameter string. When bit 2 of the SFI flag byte is set to B'1', the parameter data
may be split at any byte boundary and no other structured fields are permitted to appear between the
segmented structured fields. The segmentation flag value for the last structured field in a sequence of
structured fields containing a segmented parameter string must be B'0'.
Structured Field Padding
Padding bytes may be used by an application to extend the physical length of a structured field beyond what is
required by its introducer and parameter set. This could be done, for example, to make all structured fields the
same length or to make each structured field's length a multiple of some number. The use of padding is
indicated by a value of B'1' in bit 4 of the SFI flag byte.
If padding is indicated, the length of the padding is specified in the following manner:
• For 1 or 2 bytes of padding, the length is specified in the last padding byte. [MODCA-3-036]
• For 256 to 32,759 bytes of padding, the length is specified in the last three bytes of the padding data. The [MODCA-3-037]
last byte must be X'00' and the two preceding bytes specify the padding length.
Structured Field Syntax [MODCA-3-038]

• For 3 to 255 bytes of padding, the length can be specified by either method. [MODCA-3-039]
When padding is indicated:
• The structured field length value specifies the total length of the structured field, including the padding data. [MODCA-3-040]
• The padding length value specifies the total length of the padding data, including the padding length byte(s). [MODCA-3-041]
Structured Field Formats
The MO:DCA architecture has evolved from several previous IBM data streams, namely the Composed Page
Data Stream (CPDS), the Composite Document Presentation Data Stream (CDPDS), and the Advanced
Function Print Data Stream (AFPDS). Because of this, the MO:DCA architecture uses many of the same
structured fields originally defined for these architectures. However, in some cases new structured fields have
been defined that have the same name, acronym, and usage as these older structured fields. This has only
been done for those cases where it became necessary to expand the function of the structured field, but the
definition of the original structured field did not lend itself to expansion.
These new structured fields are always assigned a structured field identifier closely resembling the old one.
Although the structured field identifiers clearly differentiate between the two versions of the same structured
field, when referring to them by name or by acronym, the older version is known as Format 1 and the newer
MO:DCA version is known as Format 2. Two such structured fields are the Map Coded Font structured field
and the Presentation T ext Data Descriptor structured field.
Data Stream Format
The MO:DCA architecture does not dictate the physical format of the data stream or how it is transported. The
data stream may be carried within a communication protocol or it may be stored on a tape or disk. It may be
one continuous string of bytes or it may be broken up into multiple records. When broken into multiple records,
the records may be fixed length or variable length. Each record may contain an individual structured field, a
portion of a structured field, or any number of contiguous structured fields. The receiver must be capable of
receiving the data stream and parsing or processing it sequentially from start to finish. While receivers may
impose reasonable limits on blocking factors for buffer management purposes, they should not be designed to
process only one type of data stream format.
MO:DCA Data Stream States
The MO:DCA architecture defines a state to be a domain within the data stream, bounded by a begin-end
structured field pair, within which certain structured fields are permitted. This is also called a MO:DCA Object
(see Chapter 4, “MO:DCA Objects”,, for more information) . The processor of a MO:DCA data
stream is required to check the validity of the structured field sequence received. A valid structured field
sequence is one in which each structured field that is processed belongs to the set of permissible structured
fields for the begin-end envelope in which it is found. If a structured field other than one belonging to the set of
permissible structured fields is detected, a violation of the state has occurred, and the processor is required to
raise an exception condition.
See “MO:DCA Interchange Set 1” and “MO:DCA Interchange Set 3 (IS/3)” for details
of the structured fields that may be encountered in each state in MO:DCA, MO:DCA IS/1, and MO:DCA IS/3
data streams respectively.
Environment Hierarchies
The Active Environment Group and Object Environment Group are also hierarchically related. Parameters
specified in the OEG override like parameters specified in the AEG, while like parameters specified within the
same environment—whenever this is allowed—replace the previous specification. T o illustrate this point,
Data Stream States

consider the following example. Note that the same LID mapping rules apply when a resource object is
mapped with a Map Data Resource (MDR) structured field.
• A page contains an AEG with the following two Map Coded Font structured fields: [MODCA-3-042]
– An MCF that maps LID 1 to font A and LID 2 to font B
– An MCF that maps LID 3 to font D
• A graphics data object on that same page contains an OEG with the following two Map Coded Font [MODCA-3-043]
structured fields:
– An MCF that maps LID 3 to font E and LID 4 to font F
– An MCF that maps LID 5 to font H
For objects on that page that do not specify their own MCFs within their own OEGs, the LIDs and their
associated fonts would be:
• LID 1 = font A, from AEG MCF #1 [MODCA-3-044]
• LID 2 = font B, from AEG MCF #2 [MODCA-3-045]
• LID 3 = font D, from AEG MCF #2 [MODCA-3-046]
The LIDs and their associated fonts available within the graphics object would be:
• LID 1 = font A, from AEG MCF #1 [MODCA-3-047]
• LID 2 = font B, from AEG MCF #2 [MODCA-3-048]
• LID 3 = font E, from OEG MCF #1 [MODCA-3-049]
• LID 4 = font F , from OEG MCF #1 [MODCA-3-050]
• LID 5 = font H, from OEG MCF #2 [MODCA-3-051]
In this case, fonts A and B were made available from the MCFs contained in the AEG which was higher in the
environment hierarchy. However, font D was overridden when the first MCF in the OEG mapped LID 3 to font
E.
Similarly, if a Presentation Space Reset Mixing triplet were specified on both the Page Descriptor structured
field and one or more Object Area Descriptor structured fields within a particular overlay within a resource
group, the PGD would control the presentation space mixing for the entire overlay presentation space and the
OBDs would control the presentation space mixing for their individual object area presentation spaces.
Resource Environment Groups (REGs) are optional and do not affect AEGs and OEGs. Identifying a resource
in a REG does not remove the need to map that resource in the environment groups of the pages and objects
that use the resource.
Processing Order
Unless otherwise specified in a structured field's definition, all structured fields are processed in the order in
which they appear in the data stream. For example, if a presentation data stream contains a page with a text
object, an Include Page Overlay, a graphic object, a second Include Page Overlay, and an image object, in that
order, the objects are presented (imaged) on the page in that same order. That is, the text object is presented
first, the first overlay is presented second, the graphic object is presented third, the second overlay is
presented fourth, and the image object is presented last.
Likewise, unless otherwise specified in the structured field or triplet definition, structured field and triplet
parameters are also processed in the order in which they appear in the structured field or triplet.
Resource Search Order
Resources used by a MO:DCA document may be located in resource groups that are internal to the document
(such resource groups are only supported in the retired MO:DCA IS/2 interchange set, see “Retired
Interchange Set”), in resource groups that are external to the document (print file level resource
groups), or in resource libraries.
Data Stream States

The general search order for MO:DCA resources is as follows:
1. Internal (page level) resource groups (such resource groups are only supported in the retired MO:DCA IS/2 [MODCA-3-052]
interchange set, see “Retired Interchange Set”)
2. External (print file level) resource groups [MODCA-3-053]
3. External resource libraries [MODCA-3-054]
For the formal definition of resource groups in MO:DCA data streams, see “Resource Groups”.
Data Stream States

Structured Field Parameters
A structured field is composed of a set of parameters that provides data and control information to processors
of the data stream. The MO:DCA architecture has established a length, a set of permissible values and a
functional definition for each structured field parameter.
Mandatory and Optional Parameters
A parameter can be mandatory or optional. Chapter 5 provides a description of each structured field's
parameters. The description indicates whether each parameter is mandatory or optional.
Mandatory Parameters
A mandatory parameter appears in a structured field because the function of the parameter is required and a
value is essential for proper interpretation of the data stream. A value must be specified for a mandatory
parameter. The value specified either must be within the range of permissible parameter values, or it must
designate that an existing default value is to be used. A mandatory parameter requires that a suitable value for
the parameter must appear somewhere in the hierarchy of structured fields in the data stream.
Optional Parameters
An optional parameter can be omitted from a structured field if the function of that parameter is not required, or
if, although the function is required, a default value is acceptable. An optional parameter cannot be omitted if
the function is required and the default value is not acceptable.
Parameter Categories
A parameter's category refers to its structure. A parameter can consist of a single data element or it can be a
data structure composed of several data elements. Parameters that are data structures can have either a fixed
length or a variable length. In the MO:DCA architecture two types of parameters are used: fixed and self-
identifying.
Fixed Parameters
A parameter consisting of a single data element is called a fixed parameter. A fixed parameter has a constant
size in terms of bits and bytes and it always appears at the same location within its structured field. Fixed
parameters also are called positional parameters.
Self-identifying Parameters
Self-identifying parameters are data structures that consist of three or more data elements, one of which is
used to identify the purpose of the parameter. The self-identifying parameter in the MO:DCA architecture is
known as a triplet.
A triplet can have a variable length of up to 254 bytes. A triplet must consist of at least three data elements: a
length data element, an identifier data element, and one or more data elements for its contents. It can occupy
any location after any fixed parameters that occur in the structured field.
Repeating Groups
The MO:DCA architecture also supports another category of parameters known as a repeating group. A
repeating group consists of specific fixed or self-identifying parameters that have been combined into a defined
group. This group then becomes a data structure that may be specified multiple times.
Structured Field Parameters [MODCA-3-055]

When the repeating group contains self-identifying parameters, the first parameter in the repeating group is a
length parameter that indicates how many bytes comprise that repeating group. This length parameter is called
the RGLength parameter and the value specified always includes the length of the RGLength parameter itself,
which is usually two bytes.
When the repeating group contains only fixed parameters, the MO:DCA architecture may or may not specify
that the repeating group contains a RGLength parameter. When it does, the value specified for the RGLength
parameter always includes the length of the RGLength parameter itself.
Note: Frequently, a structured field may contain both positional and self-identifying parameters. When this
occurs, the positional parameters always occur before any self-identifying parameters. At times, some
or all of the positional parameters may be defined as optional. Optional parameters may only occur at
the end, after all mandatory parameters. When optional self-identifying parameters such as triplets are
added to a structured field that has optional positional parameters defined, all of the positional
parameters are considered mandatory and must appear before the first self-identifying parameter. See
“Include Page Overlay (IPO)” for an example of this type of structured field.
Parameter Values
A parameter's value can be specified directly, or it can be obtained indirectly through the use of defaults.
Specified Values
The values to be given to a parameter must be consistent with its length and data type. Additional constraints
on values may eliminate one or more values that otherwise could be assigned to a parameter.
Default Values
The use of defaults enables the sender of data-stream documents to omit the values for defaulted parameters,
permitting the receiving application to use predetermined values. A default value can be given to a parameter
by omitting any value for it, or by entering a value, defined by the architecture, requesting use of the default.
The source of the default value used for a parameter may be an environment group higher in the document
component hierarchy, or it may be an architected default established by the MO:DCA architecture.
Hierarchical Defaults
Parameter values established by an environment group at a higher level in the document component hierarchy
will be the default for a subordinate level unless a value is specified at the subordinate level. The scope of a
parameter is the same as the scope of the structured field that contains it. Thus the parameters established in
an active environment group for the current page will be in effect for the duration of the page, and will be the
default parameters for all objects contained in the page. If an object in the page has an associated object
environment group that specifies new values, the new parameter values will be in effect for the duration of the
object. If the parameters for a subsequent object in the page are unspecified, or if they specify that the default
value is to be used, the values from the current page's active environment group will be used. The placement
of parameter values at a higher level in the document hierarchy, for the purpose of enabling lower levels to
inherit these values as defaults, is known as factoring.
Architected Defaults
Certain parameters may be given default values by the MO:DCA architecture. Parameters that have been
given defaults are identified in the structured field descriptions in Chapter 5, “MO:DCA Structured Fields”, on
page 119. If a default is not listed for a parameter, no architected default exists.
Structured Field Parameters [MODCA-3-056]

Default Indicator
One of the values that usually can be given to a parameter is the default indicator. Use of the default indicator
for a parameter's value specifies that the current default value for the parameter is to be used. In the MO:DCA
architecture the default indicator has the value X'F…F'. The default indicator specifies that either a hierarchical
default value or an architected default value is to be used for the parameter. A default indicator is implied when
a fixed parameter has been omitted at the end of a structured field. A fixed parameter cannot be omitted if any
subsequent, optional, positional parameter is present, or if any triplet is present.
Any parameter for which the default indicator is valid must have a default value assigned. This value, which
must be valid for the parameter, is used when the default indicator is specified or implied. A structured field
whose parameter values are all default indicators has no effect and can be omitted from the data stream.
Parameter Occurrence
Parameters may be single-occurrence or multiple-occurrence. The syntax tables in Chapter 5, “MO:DCA
Structured Fields”, identify which parameters are single-occurrence and which are multiple-
occurrence.
Single-Occurrence Parameters
Single-occurrence parameters can occur only once in a structured field. Single-occurrence parameters can be
fixed parameters or triplets. If a value is specified for a single-occurrence parameter, it will be in effect for the
scope of its structured field. If the value of a single-occurrence parameter is omitted or if the default indicator is
specified, then normal default value inheritance will apply.
Multiple-Occurrence Parameters
Multiple-occurrence parameters are parameters that can appear more than once in a structured field. Multiple-
occurrence parameters can be triplets or repeating groups. A repeating group may consist of fixed parameters,
triplets, or a combination of fixed parameters and triplets. The following rules apply to multiple-occurrence
parameters:
• Triplets will not inherit values from higher levels of the document component hierarchy. [MODCA-3-057]
– If some triplets are omitted from a structured field at a lower level, default values will not be used. The
result will be that no values will exist for the omitted parameters for the scope of the structured field.
– If all triplets are omitted from a structured field, architected default values will be used for those
parameters that have them. The result will be that only those parameters having architected defaults will
have effect for the scope of the structured field.
• Fixed parameters will inherit values from higher levels of the document component hierarchy. If repeating [MODCA-3-058]
groups of fixed parameters are specified at more than one level within the document component hierarchy
and semantic conflicts occur, then the conflicts are resolved in favor of the lowest level for the scope of the
structured field.
Parameter Types
The term parameter type refers to a parameter's function rather than to the data type of the parameter's data.
For a listing of the six basic data types used by the MO:DCA architecture, see “How to Read the Syntax
Diagrams”. A parameter's function may be closely related to a data type, for example, in the case of
a bit string parameter and the BITS data type. A MO:DCA parameter may be a bit string, character string,
code, global identifier, local identifier, name, number, or an undefined type.
One of the most important functions for certain types of parameters is their use in referencing other document
components. A reference is the use of an identifier to refer to a component, structured field, or repeating
parameter group. References are usually found in structured fields that map component identifiers to local
Structured Field Parameters [MODCA-3-059]

identifiers, and that invoke or include components at specific data-stream locations. The effect is the same as if
the component appeared at the location in the data stream that contains the structured field that invokes or
includes it. Components that are referenced by include structured fields provide resource definitions or object
definitions. Components that are referenced by invoke structured fields provide format information, such as
that contained in environment groups.
Bit String
A bit string is a string of binary elements and corresponds to the BITS data type. Each bit of a bit string has a
value of either B'1' or B'0', which represents on or off respectively. Each bit usually is independent of the
others. Some combinations of bits may be invalid depending on what has been defined for the data element by
the MO:DCA architecture. The convention used for addressing bits within a bit string is that the leftmost bit is
bit 0.
Character String
A character string corresponds closely to the CHAR data type. It is used for identifiers composed of a string of
one or more graphic characters. Character strings are compared on the basis of the identifiers of the graphic
characters that are presented for the corresponding code points. In the MO:DCA data stream, this is governed
by the Coded Graphic Character Set Global Identifier (CGCSGID).
Code
A code is a value assigned by the MO:DCA architecture that relates to a particular meaning. The code
parameter type relates to the CODE data type. In general, parameters having a code type are given
hexadecimal values or value ranges to distinguish them from parameters with a number type.
Global Identifier
A global identifier (GID) is a string of bytes that is from 1 to 250 bytes in length. It is usually a coded graphic
character string with a data type of CHAR, but it can also be a number or a code. A global identifier has either
an alphanumeric character value that is a global name, such as the name of a document, or a numeric value
that is unique in the interchange environment. If an identifier is to be used where uniqueness is required, for
example to reference a component by name, the same name or value cannot be used more than once within
the scope of its reference. For example, the same name must not be given to two different resource definitions
of the same type in the same resource group.
Local Identifier
A local identifier (LID) is used within the data stream to reference a resource, such as a font, from within a
structured field or an OCA. The application creating the data stream is responsible for establishing the cross
references or mapping between the resources and their LIDs. The use of LIDs and mapping enables an
application to make one change in the mapping to effect multiple changes for the scope of an LID, rather than
having to make a change at each location where the LID appears.
Once established, an LID has meaning only within the context of the data stream that contains it. An LID has a
data type of CODE and its meaning is independent of where the data stream is created, filed, transmitted, or
presented.
Whenever a local identifier parameter type is used to relate structured fields present in the data stream, the
scope of reference for the LID is the begin-end pair enveloping the referenced resource. Thus both the
referenced resource and the referencing structured field must reside in the same begin-end envelope.
Structured fields, known as map structured fields, that specify a global to local mapping follow the normal
MO:DCA environment hierarchy rules.
Structured Field Parameters [MODCA-3-060]

Name
A name is an identifier composed of alphanumeric characters, and is closely related to the CHAR data type. A
name parameter type can relate either to a global or a local identifier. Names are compared on the basis of the
identifiers of the graphic characters that are presented for the corresponding code points. When comparing
names of unequal length, the shorter name is padded with space characters until it is the same length as the
longer name.
Generally, names of begin structured fields within a MO:DCA data stream are required to be unique only if their
names will be referenced and they reside in the same containing envelope with another begin structured field
of the same type.
Name parameters for end structured fields, if used, must match the name parameter for corresponding begin
structured fields. However if the first two bytes of the name parameter for an end structured field have the
value X'FFFF', it will, by default, match any name on the corresponding begin structured field.
Architecture Note: The semantic that stated “A value of X'0…0' for any positional parameter having a name
type indicates that a Fully Qualified Name (FQN) triplet exists in the structured field. The Fully Qualified
Name triplet contains a name that is used to replace the positional name parameter value” is outdated
and has been removed from the architecture. Each structured field that specifies a positional name
parameter (a “token name”) and that supports an override of this parameter using an FQN triplet,
already clearly states that such an FQN triplet (normally FQN type X'01') overrides and replaces the
positional name parameter. In fact, the FQN type X'01' triplet is defined as “Replace First GID name”,
and, by definition, replaces the token name regardless of whether the token name specifies a value of
X'0…0' or not.
The scope of any name reference is limited to the scope of the document component where the name is
specified. Thus a name appearing in an Active Environment Group has a scope that is limited to the page or
page overlay containing the Active Environment Group, and a name appearing in an Object Environment
Group has a scope that is limited to the object containing the Object Environment Group.
Number
A number or arithmetic value implies count or magnitude. All numbers used within the MO:DCA architecture
are either signed or unsigned integers as indicated in the syntax tables by the SBIN and UBIN data types
respectively.
In an unsigned number, all bits are used to express the absolute value of the number. For signed numbers, the
leftmost, or high order bit represents the sign, which is followed by the integer field.
Positive numbers are represented in true binary notation with the sign bit set to zero. Negative numbers are
represented in two's-complement binary notation with the sign bit set to one. Specifically, a negative number is
represented by the two's complement of the positive number. The two's-complement of a number is obtained
by inverting each bit of the number and adding a one to the low-order bit position.
Since the MO:DCA architecture defines X'F…F' as a default indicator, the arithmetic value -1 generally is not
permitted. However, in the case where a parameter cannot be defaulted, the value which normally is the
default indicator is interpreted as -1. Chapter 5, “MO:DCA Structured Fields”, and Chapter 6, “MO:
DCA Triplets”, identify parameters that cannot be defaulted. The maximum absolute values for
numbers that can be assigned to data elements that also can be assigned the default indicator are listed in
T able 8.
Structured Field Parameters [MODCA-3-061]

Table 8. Maximum Absolute Values of Numbers in the MO:DCA Architecture
Number of Bytes Data Type Absolute Values
Hexadecimal Decimal
1 SBIN X'7F' 127 [MODCA-3-062]
1 UBIN X'FE' 254 [MODCA-3-063]
2 SBIN X'7FFF' 32,767 [MODCA-3-064]
2 UBIN X'FFFE' 65,534 [MODCA-3-065]
3 SBIN X'7FFFFF' 8,388,607 [MODCA-3-066]
3 UBIN X'FFFFFE' 16,777,214 [MODCA-3-067]
4 SBIN X'7FFFFFFF' 2,147,483,647 [MODCA-3-068]
4 UBIN X'FFFFFFFE' 4,294,967,294 [MODCA-3-069]
Unique syntax is used for the expression of values that pertain to units of measurement and to rotation. See
“Measurement Units” and “Rotation Units” for details of this syntax.
Structured Field Parameters [MODCA-3-070]

## Coordinate Systems
The MO:DCA architecture defines a multi-level coordinate system hierarchy that allows a large degree of
flexibility in presenting data on a physical medium. A MO:DCA coordinate system is an orthogonal coordinate
system based on the fourth quadrant of a standard Cartesian coordinate system. Both the X axis and the Y
axis specify positive values, which is a difference from the Cartesian system where the Y axis in the fourth
quadrant specifies negative values.
Wherever negative offsets are supported, such as in the positioning of a page presentation space on the
medium presentation space, the negative X axis is generated by extending the X axis left of the origin, and the
negative Y axis is generated by extending the Y axis above the origin. Negative numbers are expressed in
two's complement notation.
Each individual coordinate system is associated with a specific presentation space. The MO:DCA architecture
defines the following presentation spaces:
Medium Presentation Space
The presentation space for the physical medium. This is the base presentation space onto
which all other presentation spaces are merged.
Page Presentation Space
The presentation space for the page, also called a logical page.
Overlay Presentation Space
The presentation space for an overlay.
Object Area Presentation Space
The presentation space for an object area.
Data Object Presentation Space
The presentation space for a data object. This presentation space is defined by the
corresponding data object architecture. For details on data object presentation spaces, refer
to the reference manual for each specific data object architecture.
The coordinate systems that correspond to the MO:DCA presentation spaces are listed in T able 9.
Each coordinate system defines its own coordinate axes, measurement units, and extents.
Table 9. MO:DCA Coordinate Systems
Coordinate System Notation for Axes
x direction y direction
Medium Xm Ym
Page Xpg Ypg
Overlay Xol Yol
Object Area Xoa Yoa
The origin of all MO:DCA coordinate systems is the point (0,0) where X equals zero and Y equals zero. The X
and Y axes form the top and left edges, respectively, of the presentation space, as shown in Figure 5.
The presentation space associated with the MO:DCA page can be specified to exist on either side of a sheet,
and multiple page presentation spaces can exist on the same side of a sheet. [MODCA-3-071]
## Coordinate Systems

Figure 5. A MO:DCA Presentation Space Coordinate System [MODCA-3-072]
## Measurement and Rotation
Measurement and rotation conventions are essential to the specification and interpretation of layout
information for data-stream documents. MO:DCA's conventions for measurement include data element
formats and definitions for units, extent, and position. Its conventions for rotation include data element formats
and definitions for units.
Measurement
The distance of a point from an origin is known as its absolute position. The distance of a point from another
point is known as its relative position. Distances are measured in addressable positions, and can mean X
m,Ym
units, Xpg,Ypg units, Xol,Yol units, or Xoa,Yoa units, depending on the extent or offset being measured.
Measurement Units
Measurement units are used throughout the MO:DCA architecture to identify the units of measure to be used
for such things as extents and offsets along the X and Y axes of a coordinate system.
Each individual measurement unit is specified as two separate values:
Unit base
This value represents the length of the measurement base. It is specified as a one-byte coded
value. The valid codes and their associated meanings are as follows:
X'00' T en inches
X'01' T en centimeters
Units per unit base
This value represents the number of units in the measurement base. It is specified as a two-
byte numeric value between 1 and 32,767.
The term units of measure is defined as the measurement base value divided by the units per unit base value. [MODCA-3-073]
## Measurement and Rotation

For example, if the measurement base is 10 inches and the units per unit base is 5000, then the units of
measure is 10 inches / 5000 or one five-hundredth of an inch.
The base measurement units for each axis is specified as part of the definition of a presentation space. Each
MO:DCA coordinate system may specify base measurement units independent from other coordinate systems
appearing on the same medium. Although the overall architecture design permits each axis to have a different
unit base, current implementations require that both unit bases be identical.
Measurement Unit Formats
The format used to resolve addressable positions into a unit of measure is a set of four parameters that specify
the X and Y units of length used for measurements in the X and Y direction, respectively.
Parameter Description
X unit base A one-byte code
Y unit base A one-byte code
X units per unit base A two-byte binary number from 1 through 32,767 in units of the X unit base
Y units per unit base A two-byte binary number from 1 through 32,767 in units of the Y unit base.
Since presentation devices can be built to support different units of measure along different axes, the units of
measure to which the presentation spaces have been designed can be specified in the data stream. The target
presentation device may determine if it can accept the specified length unit, if it can convert from the specified
addressable positions to one of its own, or if it recognizes a problem and possibly rejects that portion of the
data stream. The origins of coordinate systems can be established at any addressable position that exists
within a presentation space.
Extent
Each presentation space has two extents: the X extent, which parallels the X axis as it currently is oriented,
and the Y extent, which parallels the Y axis as it currently is oriented. Extents start at the origin of a
presentation space and end at a point determined by summing the extent value and the origin value. Negative
extent values are not permitted since the area enclosed by a MO:DCA coordinate system always starts at the
origin and proceeds in positive X and Y directions within its current orientation. In Figure 6 the X
extent of the presentation area is represented by line segment 0R and the Y extent by line segment 0D. [MODCA-3-074]
## Measurement and Rotation

Figure 6. Presentation Space Extents
The bottom edge of a presentation space is a line parallel to the X axis of the presentation space that
intercepts the Y axis at the end point of the Y extent. The right edge of a presentation space is a line parallel to
the Y axis of the presentation space that intercepts the X axis at the end point of the X extent.
The two extents specify the size of the presentation space. Using the example of a measurement base of 10
inches and a units per unit base of 5000, if the X extent were specified as 4250 and the Y extent as 5500, the
presentation space size would be 8.5 by 11 inches.
Offset
The origin of any MO:DCA coordinate system is expressed as an offset from the origin of another coordinate
system. The offset values for the X and Y axes can be positive or negative. Negative offset values are
expressed in two's complement notation. Any MO:DCA coordinate system that is offset from a reference
coordinate system need not be contained within that reference coordinate's extents.
The medium coordinate system is the base coordinate system from which all the other coordinate systems are
directly or indirectly offset. A coordinate system for a document component that is placed within a superior
document component references the coordinate system of the superior document component. For example,
the coordinate system of an object or a page overlay that is placed on a page references the page's coordinate
system. Since each MO:DCA coordinate system can be expressed in different base measurement units, the
offset of the origin of a subordinate coordinate system, relative to the origin of the reference coordinate system,
is always measured in the reference system's base measurement units. This permits the reference system to
influence the placement of the contained system.
The offset coordinate system inherits the orientation of the reference coordinate system. In Figure 7the origin for coordinate system B is offset ten X units and ten Y units from the reference coordinate system
A. Coordinate system B's origin is specified as the intersection of the lines drawn perpendicular to the X and Y
axes at the specified X and Y offset values from coordinate system A. [MODCA-3-075]
## Measurement and Rotation

Figure 7. Offset of a Coordinate System
Any portion of a coordinate system may be overlapped by one or more peer coordinate systems. For example,
two different object areas could be defined with the same origin so that one completely overlapped the other, or
their origins could be specified such that only a portion of the object areas overlapped.
Rotation
Rotation is used to change the presentation orientation of a document component with respect to that of the
superior document component that contains it.
Orientation refers to the rotation of a document component and its coordinate system with respect to the
coordinate system that contains it. After a MO:DCA coordinate system's origin and X and Y extents have been
established, the orientation value of the coordinate definition may cause the defined space to rotate in a
clockwise direction around its origin. Orientation is expressed in degrees and minutes, with the Y axis
orientation value being 90 degrees greater than the X axis orientation value.
Figure 8 shows the effect of rotating one coordinate system, shown as a series of rectangles,
within a containing coordinate system. Note how the X and Y extents, and thus the rectangle formed by these
extents, rotate around the contained coordinate system's origin point of 3 and 4 units from the origin of the
containing coordinate system. [MODCA-3-076]
## Measurement and Rotation

Figure 8. Examples of Coordinate System Orientation
Figure 9. Inheritance of Coordinate System Orientation
The orientation characteristics possessed by a MO:DCA coordinate system do not have to be the same as
those of its reference coordinate system. Any MO:DCA coordinate system may possess orientation
characteristics that are the same as, or different from, their reference coordinate system or any other MO:DCA
coordinate system. Figure 9 shows the effect of offsetting a page from a medium, then rotating it 90
degrees and then offsetting an object area from the page and rotating it 90 degrees. The object area inherited
the 90 degree page rotation which, when added to its 90 degrees rotation, produced a cumulative orientation
value of 180 degrees. [MODCA-3-077]
## Measurement and Rotation

Rotation Units
The rotation of the X and Y axes of an object area are specified in terms of rotation units. Rotation unit values
are expressed in degrees and minutes using two-byte, three-part binary numbers as shown in T able 10 on
page 40.
Table 10. Format for Numbers Expressed in Rotation Units
Bit Position Name Meaning
Bit 0–Bit 8 Degrees Used to represent 0 through 359 degrees. Values from 360
through 511 are invalid.
Bit 9–Bit 14 Minutes Used to represent 0 through 59 minutes. Values from 60
through 63 are invalid.
Bit 15 Reserved Value must be zero.
A rotation value of zero specifies no rotation with respect to the X axis of the presentation space in which the
origin of the page, page overlay, object area, or object is located. Increasing values indicate increasing
clockwise rotation. The four major orientations, plus-X, plus-Y , minus-X, and minus-Y , have values of 0
degrees, 90 degrees, 180 degrees, 270 degrees respectively. They are encoded as X'0000', X'2D00', X'5A00',
and X'8700'. Most structured fields limit rotation to one of these four orientations. See Figure 10.
Figure 10. Rotation of the X and Y Axes
In addition, the data object area is subject to the full range of rotation. T o obtain the rotation values one must
take into careful consideration the multi-part bit-expanded derivation of the 2–byte CODE. For example, 123
degrees, 30 minutes rotation is represented as degrees (B'001111011') and minutes (B'011110') with the last bit
(B'0') reserved. See Figure 11 . [MODCA-3-078]
## Measurement and Rotation

Figure 11. Rotation Units for the Data Object Area — Arbitrary Orientation
Overlays for a page are always positioned relative to the current orientation of the page coordinate system.
However, their X and Y extent values remain constant regardless of the orientation. Figure 12
shows this graphically.
Shape
The X and Y axes are perpendicular to each other, and the rotation of the Y axis is exactly 90 degrees more
than the rotation specified for the X axis. All MO:DCA presentation spaces must be rectangles. The shape of
the data object is not defined by the MO:DCA architecture and can take on any visual appearance.
Figure 12. A Page Overlay Applied to a Page in Two Different Orientations
Page Orientation 0º
Overlay Orientation 0º
Xpo
Ypo
Overlay Orientation 0º
Xpo
Ypo
Page Orientation 90º
## Presentation Space Mixing
Foreground and Background
MO:DCA presentation spaces such as the medium, page, overlay, and data object presentation spaces consist
of two parts: foreground and background. Foreground is the part of the presentation space that is occupied
with object data. This data can be pure object data such as text, or mixed object data such as image overlaying
text. Background is the part of the presentation space that is not occupied with object data. For data object
presentation spaces, the data object defines foreground and background, and may specify a color attribute for
Mixing

both. For each data object type, foreground, background, and color attributes are defined by the architecture
that defines the object content. For example, in a text presentation space, characters and rules are foreground,
everything else is background. Foreground is assigned a color attribute using the “Set Extended T ext Color”
control sequence. Background cannot be assigned a color and is therefore implicitly assigned the color of the
medium. When no color is specified for the background of a presentation space, the background is implicitly
assigned the color of the medium. The medium, page, and overlay presentation spaces are initially empty.
Empty MO:DCA presentation spaces contain only background, which is assigned the color of the medium.
T able 11summarizes the definition of foreground and background in AFP OCA-based object
presentation spaces:
Table 11. Foreground/Background in Data Object Presentation Spaces
Data Type Foreground Background
PTOCA T ext • Stroked and filled portion of text characters
• Stroked area of text rules [MODCA-3-079]
• Stroked area of underscores [MODCA-3-080]
Everything else
IM image B'1' image points B'0' image points
IOCA bilevel image
IOCA bilevel tiled
image
Significant image points, except image points
for which a transparency mask specifies B'0'
• Insignificant image points [MODCA-3-081]
• Image points for which a transparency mask [MODCA-3-082]
specifies B'0'
• All portions of the presentation space not [MODCA-3-083]
covered by image or tiles
IOCA grayscale or
color image
Entire image, except image points for which a
transparency mask specifies B'0'
• Image points for which a transparency mask [MODCA-3-084]
specifies B'0'
• All portions of the presentation space not [MODCA-3-085]
covered by image points
IOCA grayscale or
color tiled image
Entire tile, except image points for which a
transparency mask specifies B'0'
• Image points for which a transparency mask [MODCA-3-086]
specifies B'0'
• All portions of the presentation space not [MODCA-3-087]
covered by tiles
GOCA Graphics • Stroked area of lines (including arcs)
• Stroked and filled portion of pattern symbols [MODCA-3-088]
• Stroked and filled portion of marker symbols [MODCA-3-089]
• Stroked and filled portion of graphic [MODCA-3-090]
characters
• B'1' image points [MODCA-3-091]
• Entire area with solid fill [MODCA-3-092]
Everything else
BCOCA Bar Code • Bars and 2D modules
• Stroked and filled portions of HRI characters [MODCA-3-093]
• Stroked and filled portion of all other toned [MODCA-3-094]
constructs in the symbol (for example,
Bearer Bars)
Everything else
Colored object area,
page, or overlay
presentation space
Complete presentation space None
Empty object area,
page, or overlay
presentation space
None Complete presentation space
Non-OCA
Presentation Objects
See “Object Type Identifiers” See “Object Type Identifiers”
Mixing

Merging Presentation Spaces
Presentation spaces in a MO:DCA document are merged in the order in which the document components that
define these presentation spaces appear in the data stream, as follows:
• Medium presentation space. This is the base MO:DCA presentation space upon which all other [MODCA-3-095]
presentation spaces are merged.
– Medium overlay presentation space. Merged on the medium presentation space with a keyword on the
Medium Modification Control (MMC) structured field in a Medium Map. Medium overlays are merged on
the medium presentation space before any pages are merged. Multiple medium overlay presentation
spaces are merged in the order in which their keywords appear on the MMC structured field.
– Page presentation space. Merged on the medium presentation space in the order in which the
corresponding page appears in the document, in accordance with the specifications in the active Medium
Map.
◦ Object area presentation space. Merged on the page presentation space in the order in which the
corresponding data object is included on the page.
• Data object presentation space. Merged on the corresponding object area presentation space. [MODCA-3-096]
◦ Page overlay presentation space. If the page overlay is included via an IPO, it is merged on the page
presentation space in the order in which the overlay is included on the page. If the page overlay is
included via a PMC in a Medium Map, it is merged on the page presentation space before any data
objects or overlays included via an IPO are merged.
• Object area presentation space. Merged on the overlay presentation space in the order in which the [MODCA-3-097]
corresponding data object is included on the overlay.
• Data object presentation space. Merged on the corresponding object area presentation space. [MODCA-3-098]
The MO:DCA presentation space merge-order is shown in Figure 13.
Mixing

Figure 13. Merging Presentation Spaces
Figure Notes (numbers shown circled in the Figure):
1. Merged first on the medium presentation space as specified in a Medium Map print control object. [MODCA-3-099]
Multiple medium overlays are merged in the order in which they occur. If the overlay is a Medium
Preprinted Form Overlay (M-PFO), one such overlay may be specified and is merged last onto the
medium presentation space, after all other data for that medium presentation space has been
merged.
2. Merged first on the page presentation space as specified in a Medium Map print control object. [MODCA-3-100]
Multiple overlays are merged in the order in which they occur in the data stream. If the overlay is a
PMC Preprinted Form Overlay (PMC-PFO), one such overlay may be specified and is merged last
onto the page presentation space, after all other data for that page presentation space has been
merged.
3. May occur multiple times and is merged in the order in which it occurs in the data stream. [MODCA-3-101]
Mixing Rules
When multiple MO:DCA presentation spaces are merged, the background and foreground of the presentation
spaces mix. The resultant foreground is the union of all presentation space foregrounds, that is, once an area
is defined to be foreground, it remains foreground even if its color attribute is changed due to an “underpaint”
Mixing

mixing rule. The resultant background is everything else. The color of the resultant foreground and background
is determined by the mixing rules specified in the MO:DCA architecture.
When a new presentation space P n is merged onto an existing presentation space P e, four types of mixing
must be considered. Let F e and Be denote the P e foreground and background, respectively, and let F n and Bn
denote the P n foreground and background, respectively, then the mixing types can be characterized as follows:
Mixing Type Description
Bn on Be Background on background
Bn on Fe Background on foreground
Fn on Be Foreground on background
Fn on Fe Foreground on foreground
For each type of mixing, the resultant color is determined by the mixing rule that is specified. The following
mixing rules are defined for presentation space mixing:
Mixing Rule Definition
Overpaint When part of P n overpaints part of P e, the intersection is assigned the color attribute of P n.
This is also referred to as opaque or knock-out mixing.
Underpaint When part of P n underpaints part of P e, the intersection keeps the color attribute of P e. This is
also referred to as transparent mixing or leave alone mixing.
Blend When part of P n blends with part of P e, the intersection assumes a new color attribute which
represents a color-mixing of the color attribute of P n with the color attribute of P e. For example,
if Pn has foreground color attribute blue and P e has foreground color attribute yellow, the area
where the two foregrounds intersect would assume a color attribute of green.
Default Mixing Rule
When no presentation space mixing rule is specified, the following default MO:DCA mixing rule applies:
When a new presentation space P n is merged onto an existing presentation space P e, the background of P n
underpaints the background and foreground of P e, and the foreground of P n overpaints the background and
foreground of P e.
This default mixing rule can be summarized as follows:
Table 12. Default Color Mixing Rules
Mixing Type Default Mixing Rule
Bn on Be Underpaint
Bn on Fe Underpaint
Fn on Be Overpaint
Fn on Fe Overpaint
Preprinted Form Overlay (PFO) Mixing
Preprinted Form Overlays (PFOs) are designed to enable proper simulation of preprinted forms, particularly
their appearance when color data is presented on the form. This requires the definition of a special mixing rule,
called Formblend, which is the mixing rule for foreground on foreground mixing when page data and other
overlay data is merged with the PFO data. The Formblend mixing rule makes use of the fact that the PFO,
whether it is an M-PFO or an PMC-PFO, is always merged last on the presentation space with which it is
associated (the medium presentation space for an M-PFO, the page presentation space for a PMC-PFO). The
Formblend mixing rule is defined as follows:
Mixing

Formblend This mixing rule is only used when a simulated preprinted form, which is simulated as either a
Medium Preprinted Form overlay (M-PFO) or a PMC Preprinted Form overlay (PMC-PFO), is
merged as a new presentation space P n, onto an existing presentation space P e. The
intersection of Pn and Pe is assigned the following color attribute:
• Wherever the color attribute of P e is either the color of medium, or the color white (CMYK = [MODCA-3-102]
X'00000000' or RGB = X'FFFFFF'), the intersection is assigned the color attribute of P n.
• Wherever the color attribute of P e is not the color of medium and not the color white, the [MODCA-3-103]
intersection assumes a new color attribute that is generated in a device-specific manner to
simulate how the P e color attribute would mix onto a preprinted form that has the color
attribute of P n. In general, this mixing is a blending of the color attributes of P n and Pe that is
determined by the two color attributes and by the print media and the print technology.
Implementation Note: Since the result of merging one color with an existing color on a sheet
is, in general, a darker color, it is recommended that the mixing rule used to implement
Formblend simulates this behavior. A mixing rule with this property is defined as
“Multiply” in Document management — Portable document format — Part 1: PDF 1.7,
available at www.adobe.com. Use of this rule is recommended, particularly for non-
printing devices such as viewers.
The complete mixing rules for PFOs are defined as follows. Since, by definition, a PFO presentation space
(which is the overlay presentation space) is always merged last on its corresponding presentation space (the
medium presentation space for M-PFOs and the page presentation space for PMC-PFOs), P
n corresponds to
the PFO presentation space in this table:
Table 13. Color Mixing Rules for PFOs
Mixing Type Mixing Rule
Bn (PFO) on B e Underpaint
Bn (PFO) on Fe Underpaint
Fn (PFO) on B e Overpaint
Fn (PFO) on Fe Formblend
UP3i Print Data Mixing
Special mixing rules are defined for mixing the UP3i Print Data object type with other data on a page or overlay.
In that case, since the print data is presented by a UP3i device after (or possibly before) the complete page or
overlay is rendered by the printer, the presentation container cannot mix with the remainder of the page data
according to the default MO:DCA mixing rules. It would be difficult to merge this object type in the order in
which it is specified on a page since the UP3i Print Data object is normally rendered last (or first) due to the
physical configuration of the system. A new type of mixing is therefore architected for UP3i Print Data that is
defined as follows:
• The object area of the presentation container mixes in accordance with the default MO:DCA mixing rules. An [MODCA-3-104]
empty object area is transparent. If a Presentation Space Reset (X'70') Mixing triplet is specified on the OBD,
it can reset the space under the object area to color of medium. If a Color Specification (X'4E') triplet is
specified on the OBD, it can color the object area. Any object on the page that is specified after the Print
Data object can overpaint the object area with other data.
• The UP3i Print Data object is processed in its own presentation space by the UP3i device in accordance with [MODCA-3-105]
the Print Data format, as identified with the Print Data Format ID in the first 4 bytes of the object. It mixes with
the remainder of the page data in a manner that is defined by the Print Data format. For example, Print Data
format 'x' might define the mixing such that a bar code is printed with invisible ink that underpaints all
underlying data (i.e. the Print Data is transparent). Print Data format 'y' might define the mixing such that a
MICR ink is used to stroke the characters and overpaints all underlying data (i.e. the Print Data is opaque).
Mixing

## Color Management
The AFP Color Management Architecture (ACMA) is based on the concept of a color management resource
(CMR). A CMR is an architected resource that is used to carry all of the color management information
required to render a print file, document, group of pages or sheets, page, or data object with color fidelity.
CMRs are defined in an Advanced Function Presentation (AFP) architecture: the Color Management Object
Content Architecture (CMOCA). This architecture is defined in the Color Management Object Content
Architecture Reference.
In AFP environments, CMRs can be associated with document components and are processed as AFP
resources by print servers and printers so that they can be downloaded once, captured, and used repeatedly
without requiring additional downloads. CMRs are also applicable to non-AFP environments such as
PostScript, PDF , and PCL
®.
CMR names
A CMR is identified with a fixed-length name that is specified in the CMR header and that is generated based
on an architected naming scheme to ensure uniqueness. This naming scheme includes fields such as CMR
type, manufacturer, device type and device model number, and properties specific to the CMR type.
CMR types
Each CMR carries a single type of color management resource. The type of CMR resource is specified by the
CMR type parameter in the CMR header. The following CMR types are defined:
Color conversions
(CCs)
CMRs that carry International Color Consortium (ICC) profiles which tie a device-
specific color to or from the profile connection space (PCS).
The accuracy of color rendering is heavily dependent on the accuracy of the
description of the input colors using color conversion CMRs. Therefore, AFP
applications, document generators, and resource generators are strongly encouraged
to focus on defining the input colors as accurately as possible.
Halftones (HTs) CMRs that are applied to multi-bit data.
Indexed (IX) CMRs CMRs that map indexed colors in the data to output device colors or colorant
combinations.
Indexed (IX) CMRs are used to map a two-byte indexed color value, specified in the
data stream using the highlight color space, to device colors on a highlight color,
process color, or monochrome device. The device colors can be one of the following:
• A fractional mixture of one or more specific device colorants. [MODCA-3-106]
• A presentation-system-dependent process color (CMYK for printers, RGB for [MODCA-3-107]
displays).
• A gray value. [MODCA-3-108]
• A CIELAB value. This value is always specified, even in the above cases, to provide [MODCA-3-109]
a substitute color value if the device cannot generate the requested device color.
Link color
conversions
CMRS that provide look-up tables (LUT s) that convert directly from an input color
space in the presentation data to the output color space of the presentation device.
There are two subtypes of Link color conversion CMRs - Link LK CMRs and Link DL
CMRs.
Link LK CMRs are resources that are generated and processed internally in AFP
systems; they are not exposed to the AFP application or the job submitter, and they
cannot be referenced in the data stream. A Link LK CMR is created by combining the [MODCA-3-110]
## Color Management

CC CMR that defines an input color space with the CC CMR that defines the output
color space. Link LK CMRs can be important for presentation device performance;
therefore a goal of the AFP color management system is to provide Link LK CMRs for
the presentation device whenever it needs to convert from an input color space in the
presentation data to its own output color space.
Link DL CMRs carry ICC DeviceLink Profiles. They are similar to Link LK CMRs in that
they provide a direct conversion from an input color space to the output color space of
the presentation device. However Link DL CMRs are exposed to the AFP application
and the job submitter and are referenced in the data stream. While Link DL CMRs
apply to all supported color spaces, they are particularly useful in CMYK to CMYK
conversions to minimize changes in the K component during the conversion.
Tone transfer curves
(TTCs)
CMRs that are used to modify the values of a particular color component.
For more information on ICC profiles, see ISO 15076-1:2010 “Image technology colour management –
Architecture, profile format and data structure – Part 1: Based on ICC.1:2010”.
Processing modes
The attributes that dictate how the CMR is processed by an AFP system are referred to as processing modes
for CMRs. The following processing modes are defined:
Audit Reflects processing that has been done on a document component.
The accuracy of color rendering is heavily dependent on the accuracy of the
description of the input colors using audit color conversion CMRs.
Instruction Specifies processing that is to be done to a document component.
Link Links an input color space in the presentation data to the output color space of the
presentation device. Only Link color conversion CMRs (Link LK CMRs and Link DL
CMRs) can be processed as link CMRs.
Because some CMR types, such as a color conversion CMR, can be used in an audit mode or in an instruction
mode, the processing mode is not specified in the CMR itself. Instead, it is specified in the context within which
the CMR is associated with a document component.
IX CMRs should always be referenced as instruction CMRs. If they are referenced as audit CMRs, the output
device ignores them. Because IX CMRs specify a direct mapping from the indexed color value in the data
stream to an output color, audit CC CMRs and link CMRs are not used when an IX CMR is processed.
Instruction CC CMRs are used with IX CMRs only if the Lab value from the IX CMR is used. In that case, the
active CC CMR provides the conversion from the Lab value to the output device color value (CMYK, RGB, or
gray). Note that, as with all other CMR types, the output device uses the CMR hierarchy to select a single IX
CMR to be used with the data. If an indexed color value is not found in that IX CMR, no attempt is made to look
for that indexed color value in another IX CMR.
Halftone CMRs and tone transfer curve CMRs can be specified in a generic sense and referenced as
instruction CMRs to request an intended output appearance. Such CMRs are called generic CMRs. They are
identified with a fixed character pattern in the version field of the CMR name and with the absence of device-
specific fields in the name. The CMR Architecture registers all valid generic CMR names for HT and TTC
CMRs. Generic CMRs are never used directly by an output device; they are always replaced by device-specific
CMRs that provide the intended appearance. This replacement is done either by the print server based on
processing inline CMRs or processing the CMR RAT , or by the output device. The output device ignores
generic audit HT and TTC CMRs.
Color Conversion CMRs can be generated to force a passthrough of the colors in a presentation device without
being subject to color management. This is done by specifying the character string “pasthru” in the version field
of the CMR name. CMRs identified in this manner must be CC CMRs and must be referenced as audit CMRs. [MODCA-3-111]
## Color Management

The Prop4 property in the CMR name should be specified and indicates the color space is to be “passed
through” to the presentation device. A passthrough CC CMR contains no data. When such a CC CMR is
referenced as an audit CMR and is used for rendering data, if the color space specified matches the color
space of the presentation device the color values in the data will be rendered without going through a color
conversion. If the color space in the passthrough CMR is not the same as the device color space or if it is not
specified, or if the CMR is an instruction CC CMR, it is ignored and not used for any color conversions. A
passthrough CC CMR is treated like other audit CC CMRs in terms of selecting an audit CC CMR from the
hierarchy. There is no device-specific CMR which can be substituted for the passthrough CC CMR; it merely
instructs the device to not do a color conversion on the data.
T able 14 shows what processing modes are valid for each CMR type and whether the CMR type
can be specified as a generic CMR.
Table 14. CMR Type: Processing Mode and Generic Capability
CMR type
Non-generic CMR Generic CMR
Processing modes Processing modes
Audit Instruction Link Audit Instruction Link
Color
conversion
(CC)
Valid Valid Invalid: error Invalid: error Invalid: error
Tone transfer
curve (TTC)
Valid Valid Valid: ignored Valid
Halftone (HT) Valid: ignored Valid Valid: ignored Valid
Indexed (IX) Valid: ignored Valid Invalid: error
Link (LK and
DL)
Invalid: error Valid Invalid: error
Note: A CC CMR that is referenced as an audit CMR may be defined as a passthrough audit CC CMR by
specifying the character string “pasthru” in the version field of the CMR name. If such a CC CMR is
referenced as an instruction CC CMR, it is ignored. If the CC CMR is referenced as a link CMR, or if any
other CMR type is designated as a passthrough CMR, an error is generated.
Server Considerations:
1. Servers should download all valid combinations of CMR type and processing mode, even if the device [MODCA-3-112]
ignores them. This allows the architecture to define possible future use of such combinations without
causing errors on existing devices.
2. Servers should not download invalid combinations of CMR type and processing mode. Instead, they [MODCA-3-113]
should generate an error.
CMR Installation
CMRs in resource libraries are accessed using a CMR Resource Access T able (RAT). When CMRs are
installed in a resource library, the install program must build the CMR RAT entry that maps the CMR name to a
file name, to an object OID, and optionally to additional CMRs such as Link LK CMRs. When a color
conversion CMR is installed, a flag bit in the CMR RAT entry specifies whether this CMR would normally be
used to define input colors in the print file, that is, as an audit CMR. This flag bit is used to trigger the
generation of Link LK CMRs that convert from the input color space defined by that CMR to the output color
spaces, defined by other CMRs, of all target presentation devices that are configured to the install program and
that are to be used on the target print servers. These Link LK CMRs are then mapped to the color conversion
CMR in the CMR RAT . For generic CMRs, the install program automatically builds a CMR RAT entry for each [MODCA-3-114]
## Color Management

architected generic CMR name that points to a dummy generic CMR object and to an object OID for the
dummy generic CMR object. This entry allows users to map device-specific CMRs to the generic CMR in the
RAT .
CMRs and presentation devices
When a print server accesses the CMR RAT with a reference to an audit CMR in the data stream, it may
encounter Link LK CMRs that are mapped to the referenced audit CMR. If the target device supports
downloaded Link LK CMRs, the server uses the current target device type and model to select appropriate Link
LK CMRs for converting the input color space defined by the audit CMR to the output color space of the target
presentation device. Such Link LK CMRs are downloaded to the target device, if necessary.
Similarly, when a print server accesses the CMR RAT with a reference to a generic CMR in the data stream, it
may encounter device-specific CMRs of the same type that are mapped to the referenced CMR. If the device
supports downloaded CMRs of that type, the server uses the current device type and model to select
appropriate device-specific CMRs that are to be sent to the device in place of the generic CMR.
Device support for downloaded CC CMRs and generic HT and TTC CMRs is mandatory. Device support for
downloaded device-specific HT and TTC CMRs, for Link LK CMRs, and for IX CMRs is optional. If print file
refers to an optional CMR that is not supported by the output device, the print server recognizes an exception
condition. User-specified fidelity controls determine whether this exception condition is reported and whether
print file processing continues.
Associating CMRs with document components
An audit or instruction CMR or a link CMR (subtype DL) can be associated with a MO:DCA document
component and becomes a part of the CMR hierarchy that the presentation device uses to apply color
management to presentation data. At any given level of the document hierarchy, a Link DL CMR has higher
priority, in case of conflict, than an audit CC CMR. A Link LK CMR is not tied into the CMR hierarchy used by
the presentation device. Instead, if supported by the presentation device, it is sent to the device by the server
and is always used if a color conversion is needed to render presentation data and that conversion is defined
precisely by that Link LK CMR.
CMRs are associated with MO:DCA document components in the following manner:
Print file A CMR can be associated with the print file by referencing it as a resource in the
Document Environment Group (DEG) of the form definition that is invoked for the print
file by the job submitter.
Document A CMR can be associated with a specific document in the print file by using a CMR
that is referenced for the print file and targeting this CMR at the specific document.
Group of pages or
sheets
A CMR can be associated with a group of pages by referencing it as a resource in the
medium map that is invoked to process those pages.
Page or overlay A CMR can be associated with a page or overlay by referencing it as a resource in the
Active Environment Group (AEG) for the page or overlay. This reference is identified
with scope page or overlay to differentiate it from similar object level references that
can be factored up from the Object Environment Group (OEG) of a data object or from
an Include Object (IOB) structured field.
Data object A CMR can be associated with a data object such as IOCA, EPS, PDF , TIFF , JFIF ,
GIF in multiple ways:
• The data object can be installed with an install program that generates a data object [MODCA-3-115]
Resource Access T able (RAT). When this program builds the RAT entry for the data
object, it can also specify one or more CMRs that are to be associated with the
object. Each CMR reference indicates the processing mode of the CMR (audit or
instruction).
## Color Management

• If the data object is included on a page/overlay with an IOB, or if it is in a page [MODCA-3-116]
segment that is included on a page/overlay with an IOB, a CMR can be associated
with this object by specifying the name of the CMR on the IOB as an external
resource reference and then referencing the CMR with a Map Data Resource
(MDR) in the Active Environment Group (AEG) of the page. This method is similar to
how a resident SWOP or Euroscale color profile is associated with an EPS or PDF
object, and how a PDF resource is associated with a PDF object.
• If the data object is specified directly on the page/overlay, it can reference the CMR [MODCA-3-117]
in its OEG with an
MDR that references the CMR. Note that, for resource
management, any CMR reference in the OEG must be factored up to the AEG of
the including page or overlay.
• If the data object is an image object to be presented in conjunction with a QR Code [MODCA-3-118]
with Image bar code, and the bar code is included on a page/overlay with an IOB, a
tertiary CMR can be associated with the image object by specifying, on the IOB, the
name of the CMR as an external resource reference, paired with the internal
resource name used within the bar code object to reference the image object. In
addition, the CMR must also be referenced with an MDR in the AEG of the page or
overlay.
• If the data object is an image object to be presented in conjunction with a QR Code [MODCA-3-119]
with Image bar code, and the bar code is specified directly on the page/overlay, a
tertiary CMR can be associated with the image object by specifying, in an MDR in
the OEG of the bar code object, the name of the CMR as an external resource
reference, paired with the internal resource name used within the bar code object to
reference the image object. Note that, for resource management, any CMR
reference in the OEG must be factored up to the AEG of the including page or
overlay.
• In either of the two previous cases, where the data object is an image object to be [MODCA-3-120]
presented in conjunction with a QR Code with Image bar code, a CMR can also be
associated with the image object by being associated with the bar code object—that
is, for such image objects, an object-level CMR provided for the including QR Code
with Image bar code object is considered to be also associated with the image
object. Such image objects, then, can either have an object-level CMR associated
directly to them, through the previous two cases, or can have an object-level CMR
associated indirectly to them, by associating the object-level CMR with the bar code
object that includes them. The direct association takes precedence.
• The data object can contain embedded CMR-like information. An example is the [MODCA-3-121]
inclusion of an audit-like ICC profile in a TIFF object. Such information is used by
the presentation device when an object level CMR is not provided. If the data object
is installed using an install program, an embedded audit-like ICC profile can be
copied and converted into an audit CC CMR that is then associated with the data
object in the data object RAT . Optionally, the embedded profile can also be
extracted from the object to reduce the object size; this version of the object is
referred to as the compacted object. The copy and extract functions are allowed
only if the embedded ICC profile can be used independently of the data object, as
specified with a flag in the ICC header.
Note that if a data object is to be preprocessed with the Preprocess Presentation
Object (PPO) structured field, the same CMRs that are to be associated with the
object when rendered need to be associated with the object on the PPO. This is done
by specifying the CMRs on the PPO as external resource references and by mapping
the CMRs with a MDR in the Resource Environment Group (REG) that contains the
PPO.
## Color Management

Rendering intent
The proper use of CC CMRs and LK CMRs in a presentation device involves the concept of rendering intent.
Rendering intent is used to modify the appearance of color data. Rendering intents supported in AFP color
management are based on the rendering intents defined by the ICC, which are also used in other presentation
environments such as PostScript and PDF . The ICC defines four rendering intents:
• Perceptual [MODCA-3-122]
• Saturation [MODCA-3-123]
• Media-relative colorimetric [MODCA-3-124]
• ICC-absolute colorimetric [MODCA-3-125]
For more information on rendering intents, see ISO 15076-1:2010 “Image technology colour management –
Architecture, profile format and data structure – Part 1: Based on ICC.1:2010”.
Rendering intent is specified with the Rendering Intent (X'95') triplet on the Presentation Environment Control
(PEC) structured field. For document hierarchy levels other than the object level, rendering intents can be
specified independently for each major AFP color object type category, as follows:
• IOCA objects [MODCA-3-126]
• Object containers (EPS, PDF , TIFF , etc.) [MODCA-3-127]
• PTOCA text [MODCA-3-128]
• GOCA graphics objects [MODCA-3-129]
This allows one object type, such as text, to be rendered with a different rendering intent than another object
type, such as continuous tone IOCA image, with a single specification of the Rendering Intent triplet.
The rendering intent specified with the Rendering Intent (X'95') triplet, or with the Rendering Intent table vector
in the data object RAT (DO RAT), or that is embedded in a data object, is not used when a Link DL CMR is
used for a color conversion. Such CMRs specify the rendering intent internally and override any rendering
intent specified elsewhere.
Process colors can also be specified for a Bar Code Object Content Architecture (BCOCA) object with the
Color Specification (X'4E') triplet on the Bar Code Data Descriptor (BDD) structured field. However, the
rendering intent for BCOCA objects is fixed as media-relative colorimetric.
Rendering intents may be associated with a MO:DCA document component at the same levels of the
document hierarchy as CMRs, as follows:
• Print file [MODCA-3-130]
• Document [MODCA-3-131]
• Group of pages or sheets [MODCA-3-132]
• Page or overlay [MODCA-3-133]
• Data object; the rendering intent may be associated with a data object in a number of ways: [MODCA-3-134]
– By specifying a PEC with RI triplet in the OEG for the data object
– By specifying the RI triplet on the IOB that includes the data object
– By specifying the RI triplet on the PPO that is used to preprocess the data object
– By specifying the rendering intent in the data object RAT entry for a data object
– By the data object containing embedded rendering intent information; such information is used by the
presentation device when a rendering intent is not specified at the data object level using an RI triplet or a
data object RAT table vector. [MODCA-3-135]
## Color Management

Normal MO:DCA hierarchy rules apply for processing rendering intents. That is, a rendering intent specified for
a document component at a lower level in the hierarchy applies only to that document component and
overrides any other rendering intent specified at a higher level in the hierarchy.
CMRs and print media
Color rendering may also be significantly affected by the characteristics of the print media. CMRs may
therefore be tuned to specific media; this is indicated by specifying one of the following four media attributes in
an instruction CMR:
• Media brightness [MODCA-3-136]
• Media color [MODCA-3-137]
• Media finish [MODCA-3-138]
• Media weight [MODCA-3-139]
Each attribute has a valid range of values that is defined in the Color Management Object Content Architecture
Reference. An instruction CMR may specify none, some, or all of these attributes. The output device uses
these CMR media attributes and the media attributes of the current media to select an optimum CMR using the
following algorithm:
• If none of the media attributes are specified in an instruction CMR, the printer uses it [MODCA-3-140]
• If one or more of the media attributes in an instruction CMR are invalid, exception processing mode is [MODCA-3-141]
entered
• If all of the media attributes are specified in an instruction CMR and are valid, the CMR is processed as [MODCA-3-142]
follows:
– If all attributes match the current media, the CMR is used.
– If one or more attributes do not match the current media, the printer searches the hierarchy for a media-
specific CMR that matches the current media. Multiple applicable CMRs may exist at each level of the
hierarchy and are included in the search, and each level of the hierarchy is searched in the normal order,
except for the printer default level, which is not part of the search. If no matching media-specific CMR is
found, exception processing mode is entered.
• If some, but not all, of the media attributes are specified in an instruction CMR and are valid, the CMR is [MODCA-3-143]
processed as follows:
– If all the specified attributes match the current media, the printer searches the hierarchy for a CMR whose
media attributes are a better match with the current media. Multiple applicable CMRs may exist at each
level of the hierarchy and are included in the search, and each level of the hierarchy is searched in the
normal order, except for the printer default level, which is not part of the search. If a better matching CMR is
not found, the original CMR is used.
– If one or more of the specified attributes do not match the current media, the printer searches the hierarchy
for a CMR whose media attributes do match the current media. Multiple applicable CMRs may exist at
each level of the hierarchy and are included in the search, and each level of the hierarchy is searched in
the normal order, except for the printer default level, which is not part of the search. If no CMR is found
whose attributes match the current media, exception processing mode is entered.
CMR Processing
CMR association and scope
CMRs are associated with a document component implicitly. That is, that document component does not call
out the associated CMRs directly. [MODCA-3-144]
## Color Management

• At the print file level, a CMR is associated by referencing the CMR in a MDR in the DEG for the form [MODCA-3-145]
definition. The CMR applies to all documents in the print file.
• At the document level, the CMR is associated by referencing the CMR in a MDR in the DEG for the form [MODCA-3-146]
definition, and by pointing to the specific document in the print file. The CMR then applies only to that
document.
• At the group of pages or sheets level, the CMR is associated by referencing the CMR with a MDR in the [MODCA-3-147]
invoked medium map. The CMR applies to all pages or sheets processed with that medium map.
• At the page or overlay level, the CMR is associated by referencing the CMR in a MDR in the AEG for that [MODCA-3-148]
page or overlay. The CMR applies only to that page or overlay.
• At the data object level, the CMR is associated with a data object in any of the following ways: [MODCA-3-149]
– By referencing the CMR in the RAT entry for the object in a data object RAT
– By referencing the CMR on the IOB that is used to include the data object
– By referencing the CMR on the PPO that is used to preprocess the data object
– By referencing the CMR with a MDR in the Object Environment Group (OEG) of the data object
In general, when a CMR is associated implicitly with a document component, the scope of the CMR is the
complete document component, unless noted otherwise.
Resident SWOP or Euroscale color profiles are examples of color management resources that are associated
implicitly with an EPS or PDF object. They are not called out directly within the object. Their scope is the
complete EPS or PDF object with which they are associated.
CMR processing mode
The processing mode determines how a CMR is used in the presentation system. The audit processing mode
indicates that the CMR defines an operation that has been done on a document component. For example, an
audit CC CMR defines the device color that was used to generate the presentation data. It does that by
defining the relationship between the input device color space (often called the input color space) and PCS. An
audit HT CMR defines the halftone that was used to create the data. An audit TTC CMR defines a tone
adjustment that was applied to a color component before the halftone was applied to that component.
The instruction processing mode indicates, in a similar manner, that the CMR defines an operation that is to be
done on a document component. For example, an instruction CC CMR defines the relationship between PCS
and the output device color space (often called the output color space). An instruction TTC CMR defines a tone
adjustment that is to be applied to a color component before it is halftoned. An instruction HT CMR defines the
halftone that is to be applied to the color component. An instruction IX CMR defines the mapping of indexed
colors in a document component to output device colors.
The link processing mode is valid only with Link LK CMRs and Link DL CMRs and defines a direct conversion
from input color space to device output color space. Link DL CMRs can be associated directly with a document
component, but Link LK CMRs cannot. Instead, Link LK CMRs are associated with, or mapped to, CC CMRs
either in the CMR RAT entry, or, for CC CMRs in print file level resource groups, on the Begin Resource (BRS)
structured field that wraps the container of the CMR.
Audit, instruction, and link (for Link DL CMRs) processing modes are specified when a CMR is associated with
a document component. For print files, documents, page or sheet groups, pages, and overlays, the processing
mode is specified with the CMR Descriptor triplet on the MDR. For data objects, the processing mode can be
specified in multiple ways:
• With a CMR Descriptor triplet on the MDR in the OEG for the object [MODCA-3-150]
• With a CMR Descriptor triplet on the IOB that includes the object [MODCA-3-151]
• With a CMR Descriptor triplet on the PPO that is used to preprocess the object [MODCA-3-152]
## Color Management

• With a CMR Descriptor table vector (TV) in the data object RAT entry for the object [MODCA-3-153]
IX CMRs should be processed as instruction CMRs. IX CMRs that are to be processed as audit CMRs are
ignored by the output device.
The link processing mode is valid only with LK CMRs. Such CMRs are not associated directly with a document
component. Instead, link CMRs are associated with, or mapped to, CC CMRs either in the CMR RAT entry, or,
for CC CMRs in print file level resource groups, on the Begin Resource (BRS) structured field that wraps the
container of the CMR.
CMR hierarchy rules
The interaction of CMRs at different levels of the document hierarchy follows MO:DCA hierarchy and state
rules. When a CMR is associated with a document component at a given level, it replaces (for that level or
state only) any conflicting CMR that is associated with a document component at a higher level. For example, if
audit color conversion CMR (x) is associated with the print file, and audit color conversion CMR (y) is
associated with a data object on a page in a document in that print file, audit color conversion CMR (y) is used
as the active audit color conversion CMR for the duration of the data object processing, or the duration of the
object state. When the object state is terminated, audit color conversion CMR (x) again becomes the active
audit color conversion CMR.
Note that this CMR replacement rule applies only to conflicts. In the above example, if CMR (x) converts
device RGB to PCS and CMR (y) converts device CMYK to PCS, the CMRs do not conflict. Both can be used
to process RGB and CMYK colors in the data object. If two CMRS that conflict are specified at the same level
of the document hierarchy, the last-specified CMR is used.
An audit CC CMR that is designated as a passthrough CC CMR is treated like any other audit CC CMR with
respect to CMR hierarchy rules. That is, in the above example, the stated rules apply whether CMR (x) and/or
CMR (y) is a passthrough CC CMR or a normal CC CMR.
In addition, at any given document level, a Link DL CMR that is referenced at that level takes precedence, in
case of a conflict, over the audit CC CMR at that level. For example, if Link DL CMR (l) and audit CC CMR (a)
are both specified at the page level and both convert CMYK, Link DL CMR (l) is used to convert CMYK colors
on that page. Furthermore, Link DL CMR (l) would be the CMR that is inherited at the object level (i.e. the next
lower document level) for CMYK conversions, unless a CC CMR or Link DL CMR is specified at the object
level, in which case these CMRs would override any inherited CMR.
If two CMRs of the same type conflict and are specified at the same document level, the last-specified CMR is
used. For example, if two audit CC CMRs that convert RGB to PCS are specified at the page level, the CMR
that is specified last takes precedence.
Generic CMR processing
Halftone CMRs and tone transfer curve CMRs can be specified in a generic sense to request an intended
output appearance. Such CMRs are called generic CMRs. They are identified with a fixed character pattern of
generic (encoded in UTF-16BE) in the version field of the CMR name. Generic HT and TTC CMRs should be
referenced as instruction CMRs. Generic HT and TTC audit CMRs are ignored by the output device. Generic
CMRs are processed as follows:
• A server processes a reference to a generic instruction CMR in the same manner that it processes a [MODCA-3-154]
reference to a device-specific CMR, with one exception. Because the CMR is generic, the server checks
whether device-specific CMRs that match the device type and model of the target printer have been mapped
to the generic CMR in the CMR RAT . If yes, the device-specific CMRs are used instead. Note that this
mapping could occur inline as well by placing the generic CMR in an inline resource group and referencing
device-specific CMR replacements that match the device type and model of the target printer on the BRS of
the container. If no matching device-specific CMR is mapped to the generic CMR either inline or in the CMR
RAT , the server downloads (if necessary), activates, and invokes the generic CMR. [MODCA-3-155]
## Color Management

• The printer processes the CMR hierarchy in the normal manner, with one exception. If the active instruction [MODCA-3-156]
halftone CMR or TTC CMR is a generic CMR, the printer substitutes an appropriate version of a device-
specific default CMR.
Default CMRs
When the presentation device requires color management information to render presentation data but no
CMRs have been associated with the data in the document hierarchy, default CMRs are used. For converting
to output color spaces, these default CMRs are presentation device default instruction CMRs. For converting
from input color spaces, these default CMRs are architected default audit CMRs. For a definition of these
defaults, see the Color Management Object Content Architecture Reference. Note that there are no
architected default Link DL CMRs.
CMR exception processing
A CMR exception is detected when a CMR that has been referenced in the data stream (which includes
FormDefs and Medium Maps) or a data object RAT cannot be processed as specified. For example, a FormDef
may reference a device-specific instruction TTC CMR, but the output device does not support downloaded
TTC CMRs. The processing of such exceptions is controlled by the Color Fidelity (X'75') triplet.
The above does not apply to CMRs that are mapped to referenced CMRs but that are themselves not directly
referenced in the data stream or a data object RAT . This includes:
• Link LK CMRs that are mapped to color conversion CMRs in a CMR RAT or on the BRS of an inline CMR [MODCA-3-157]
• Device-specific halftone and tone transfer curve CMRs that are mapped to generic CMRs in a CMR RAT or [MODCA-3-158]
on the BRS of an inline CMR
The processing of such mapped CMRs is not governed by the Color Fidelity triplet. If a device does not support
the download of such a mapped CMR, it does not cause a CMR exception and the mapped CMR is ignored.
A CMR tag exception is detected when an unsupported CMR tag is encountered in a CMR. The processing of
such exceptions is controlled by the CMR T ag Fidelity (X'76') triplet.
CMRs in Print file level Resource Groups
CMRs may also be carried in the resource group for a print file, in which case they are called inline CMRs. The
CMR is first wrapped in a BOC/EOC object container, which in turn is wrapped in a BRS/ERS resource
envelope. The BRS specifies the CMR name, and may also specify the names of CMRs that are mapped to
the inline CMR. When resolving a CMR reference in the data stream, the print server must always search the
print file resource group—if one exists—first. The CMRname is matched against the CMRname that is
specified on the BRS structured field of the resource container. For a definition of the algorithm used by a print
server to process inline CMRs, see “Using the MDR to Map a Color Management Resource (CMR)”. [MODCA-3-159]
## Metadata Objects in AFP
A Metadata Object (MO) is an architected object used to carry descriptive metadata of predefined type and
format. Metadata can be associated with a MO:DCA print file. MOs are defined in the Metadata Object Content
Architecture (MOCA). This architecture is defined in the Metadata Object Content Architecture Reference.
In AFP environments, the MOs have no presentation semantic and may be ignored by print servers or printers.
Metadata

Associating MOs with an AFP print file
MO association and scope
Metadata is optional. When metadata is present, one or more contiguous MOs may be associated with a
MO:DCA print file. MOs are “inline”, are carried in a print file level resource group, must appear first within that
resource group, and apply to the entire print file. Within the resource group, each MO is first wrapped in a
BOC/EOC object container, which is wrapped in a BRS/ERS resource envelope.
MO Hierarchy Rules
When including multiple MOs the series of object containers must be contiguous and must appear first within
that resource group, and, as a whole, constitutes the metadata for the print file. The MO:DCA architecture
places no restriction on or significance to the sequence or order of included metadata; therefore, there are no
rules that specify the interaction of MOs such as hierarchy or inheritance.
Default MOs
The concept of a default metadata object has no meaning; therefore, default MOs are not defined.
Font Technologies
The MO:DCA architecture supports references to various font technologies for rendering character data.
These font technologies can be separated into two classes:
FOCA fonts
Non-FOCA fonts, also called data-object fonts
FOCA fonts have a structure that is defined by the Font Object Content Architecture (FOCA). They are
referenced in a MO:DCA data stream using a Map Coded Font (MCF) structured field. For a description of
FOCA fonts, see the Font Object Content Architecture Reference. Non-FOCA fonts are fonts whose structure
is not defined by the FOCA architecture. The structure of such fonts is not modified when they are used in
MO:DCA data streams and in AFP environments. However, such fonts may be carried in MO:DCA object
containers, if, for example, they are to be placed in an AFP resource group. Non-FOCA fonts are referenced in
a MO:DCA data stream using a Map Data Resource (MDR) structured field. Examples of non-FOCA fonts that
are supported in MO:DCA data streams are TrueType fonts (TTFs) and OpenType fonts (OTFs).
Relationship Between FOCA Character Metrics and TrueType Character
Metrics: Implementation Issues
It is important to have consistent presentation results regardless of the font technology used. The FOCA
Architecture defines the basic concepts and provides a rich set of font and character metrics; these FOCA
concepts lay out the presentation goals. The PTOCA architecture provides the capability to present strings of
text at various orientations as shown in Figure 78. The following describes the relationship
between various TrueType metrics and the corresponding FOCA-defined metrics and provides
recommendations for simulating metrics that are needed for presentation but are not directly provided in some
TrueType fonts.
Horizontal Metrics
When a TrueType rasterizer RIPs the outline descriptions into character bitmaps, TrueType metrics are
provided for positioning the bitmaps horizontally within a line of text. These metric values provide enough
information to calculate the metrics defined by FOCA for the 0 degree character rotation. This information
Fonts

includes the width and depth of the bitmap, the distance from the character origin to a corner of the bitmap, and
the distance to the origin of the next character.
Figure 14 compares the parameters commonly used with TrueType fonts to the horizontal (0
degree) metrics provided by a FOCA font. In practice, many TrueType fonts are built so that there is no top
indent or left indent; in this case, the bitmap is a tight box around the character and the indent values are zero.
Figure 14. Horizontal Metrics: TrueType/OpenType Fonts and FOCA Fonts
Based on this illustration, the key FOCA horizontal metrics can be calculated as follows:
Character Increment (HCI) = Escapement
A-space (HAS) = Left Indent - X Origin
B-space (HBS) = Black Width
C-space (HCS) = Escapement - A-space - B-space
Baseline Extent (HBE) = Black Depth
Baseline Offset (HBO) = Y Origin - Top Indent
Character Descender (HCD) = Top Indent + Black Depth - Y Origin
The FOCA metrics for 180- degree rotation (upside-down) have a simple relationship to those for 0-degree
rotation. The A-space and the C-space metrics are reversed, as are the baseline offset and character
descender metrics. The character increment, B-space, and baseline extent metrics are identical.
Note that, in practice, font rasterizers don't provide all of the parameters shown in the picture, but do provide
other parameters. For example, the font rasterizer can return the offset (xorigin, yorigin) from the character
origin of the top-left corner of the bitmap. This information can be related to the metrics formulas; for example:
A-space (HAS) = Left Indent - X Origin = Left Indent + xorigin
Baseline Offset (HBO) = Y Origin - Top Indent = yorigin - Top Indent
Fonts

Vertical Metrics
Character rotations of 90 and 270 degrees are used to support vertical forms of writing. In addition to the
metrics mentioned earlier, vertical positioning and character increment metrics are needed to place characters
in these rotations. Some TrueType fonts provide metrics for vertical writing in a structure called a “vtmx table”,
but others don't provide these metrics. The TrueType advance height corresponds to the FOCA vertical
character increment (VCI) and the TrueType top sidebearing corresponds to the FOCA vertical A-space (VAS),
but there is no TrueType metric that corresponds to the FOCA baseline offset.
When the vtmx metrics are available they can be used to calculate the equivalent FOCA vertical metrics. But,
when the font designer omitted them or when they can't be obtained from the TrueType rasterizer, a method is
needed to estimate appropriate FOCA equivalent values.
Simulating Vertical Metrics
Figure 15 shows again the TrueType horizontal metrics and some additional TrueType metrics that
can be obtained to describe the em-square. The figure also shows the target FOCA vertical metrics and a
method for simulating 270 degree FOCA vertical metrics from TrueType horizontal metrics.
Fonts

Figure 15. Vertical Metrics: TrueType/OpenType Fonts and FOCA Fonts
Any approach taken to approximate these metrics is well served to consider the scripts in which vertical writing
is most popular: East Asian scripts which use ideographic characters. These full width characters have
properties that can be utilized to make these estimations. First, they typically have an equal, or fixed,
Fonts

increment. Second, they are designed on a square grid, so their width and height are equal. Third, they are
usually the largest characters in the font.
For these reasons, using a fixed vertical character increment (VCI) equal to the largest horizontal increment
will be quite satisfactory for vertical writing. Generally, the maximum values for many basic metrics, such as
character increment, descender, and baseline offset can be obtained from the font file. Alternatively, the
properties listed previously make it reasonable to set VCI to the Em-Space Increment. The Em-space is
defined such that one em equals the height of the design space. Scalable font metrics are expressed as
fractions of this unit-Em.
These alternatives can be summarized mathematically as:
Character Increment (VCIestimated) = max(Escapement)
— or —
Character Increment (VCIestimated) = 1 em
T echniques to estimate appropriate values for VAS must keep two goals in mind. First, it should result in the
bitmaps of ideographic characters being placed within the vertical increment. Second, the vertical position of
the bitmap should reflect the relative horizontal baseline offset of the character. For example, the bitmap widths
for the BLACK LENTICULAR BRACKETS, U+3010 and U+3011, are small compared to their increment and
are designed to be positioned close to the character they enclose. This property must be preserved for vertical
writing.
T o accomplish these goals, first compute a constant value (Vy) to place the horizontal character origin relative
to the vertical character positioning point, using the TrueType em-square metrics and the following equation
(note that max(HBE) = urY + llY and max(HBO) = urY):
Vy(est) = int((em - max(HBE))/2) + max(HBO)
The first component of this equation, int((em - max(HBE))/2), is designed to position all of the character
bitmaps of the font within the vertical increment. The second component, max(HBO), calibrates the V Origin
metric to the highest character(s) within the font. With this reference, then calculate VAS for individual
characters with the equation:
VASestimated = Vy(est) - Y Origin
and achieve the design goals.
For fonts that are not based on ideographic characters, a different method of constructing a vertical character
increment and A-space could be used. For example, a fixed percentage (20%) of extra space, based on the
desired pointsize, could be added to the black depth to yield the VCIestimated. The extra space could be
divided evenly between the vertical A-space and vertical C-space. For characters without any black depth
(space characters), the pointsize could be used as VCIestimated.
The last task to address is estimating the horizontal position of the character bitmap. For vertical rotations, this
is reflected in the baseline offset (VBO) and character descender (VCD) metrics. Similar to the goal for vertical
positions, this metric should reflect the character's horizontal position within its horizontal increment.
Therefore, the metric calculations should essentially center the character's horizontal increment on the
baseline and preserve its horizontal position with respect to the increment. This is achieved with the equations:
Baseline Offset (VBO) = Left Indent - X Origin + Black Width - int(Escapement/2)
Character Descender (VCD) = X Origin - Left Indent + int(Escapement/2)
The remaining metrics for 270-degree character rotation can be calculated from the horizontal bitmap metrics
and those derived previously:
Baseline Extent (VBE) = Black Width
B-space (VBS) = Black Depth
C-space (VCS) = VCI - VAS - Black Depth
The vertical metrics for 90-degree character rotation can be directly deduced from the 270-degree metrics, in
the same manner used to convert 0-degree metrics to 180-degree metrics.
Fonts

## Document Indexing
The document index defined by the MO:DCA architecture provides functions for indexing the document based
on document structure and on application-defined document tags. The index is delimited by a Begin Document
Index structured field and an End Document Index structured field and may be located within the document or
external to the document. MO:DCA elements that may be indexed are pages and page groups. When
referenced by an index, they are called indexed objects. The MO:DCA elements within a document index that
reference indexed objects are Index Element (IEL) structured fields. The MO:DCA elements within a document
index that support content-based tagging are T ag Logical Element (TLE) structured fields.
A MO:DCA document index consists of the following structured fields. These structured fields are described in
detail in Chapter 5, “MO:DCA Structured Fields”,. Note that the IEL and TLE structured fields may
occur multiple times.
Begin Document Index (BDI)
Index Element (IEL)
Link Logical Element (LLE)
T ag Logical Element (TLE)
End Document Index (EDI)
When the document index is external to the document, the BDI structured field references the document using
a Fully Qualified Name type X'83' triplet. The document name specified in this triplet is inherited by all IEL and
TLE structured fields in the index.
Index Elements
The Index Element (IEL) structured field supports indexing of pages and page groups. When an IEL references
an indexed object, the type of indexed object (page or page group) is indicated by the name reference to the
indexed object. The name of the IEL structured field is specified by a Fully Qualified Name type X'CA' triplet,
and the name of the indexed object is specified by either a Fully Qualified Name (FQN) type X'87' triplet for a
page or by a FQN type X'0D' triplet for a page group. An IEL that references a page is called a page level IEL.
An IEL that references a page group is called a page group level IEL. A MO:DCA index may contain page level
IELs, page group-level IELs, or both. The order in which page level IELs and page group level IELs appear in
the index must be the same as the order in which the indexed Begin Page and Begin Page Group structured
fields appear in the document.
The IEL structured field provides the following information for the indexed object:
• Direct byte offset of the Begin indexed object structured field from the start of the Begin Document structured [MODCA-3-160]
field.
• Byte extent of the indexed object, from the first byte in the Begin structured field to the last byte in the End [MODCA-3-161]
structured field.
• Structured field offset of the Begin indexed object structured field, where the Begin Document structured field [MODCA-3-162]
has offset 0, and all following structured fields increment the offset by 1.
• Structured field extent of the indexed object, which is a count of the number of structured fields in the [MODCA-3-163]
indexed object, starting with the Begin indexed object structured field and ending with the End indexed object
structured field.
• Object offset of the Begin indexed object structured field, using a specified object type. For example, this [MODCA-3-164]
parameter may specify the number of pages that precede an indexed page group in the document.
• Object extent of the indexed object, using a specified subordinate object type. For example, if the [MODCA-3-165]
subordinate object is a page, this parameter may specify the number of pages in an indexed page group.
• If the indexed object is a page: [MODCA-3-166]
## Document Indexing

– The name of the medium map object that is active for formatting the indexed page on a physical medium
– The number of the indexed page in the set of sequential pages controlled by the active medium map,
where the first page in the set is number 1
– The PGP repeating group used to process the page
• If the indexed object is a page group: [MODCA-3-167]
– The number of pages that precede the page group in the document
– The number of pages contained in the page group
– The name of the medium map object that is active for formatting the first page in the indexed page group
on a physical medium
– The number of the first page-group page in the set of sequential pages controlled by the active medium
map, where the first page in the set is number 1, and where “active medium map” refers to the medium
map that is active at the beginning of the page-group
– The PGP repeating group used to process the first page-group page
An example of a page level IEL that specifies page offset and page extent is shown in Figure 16.
Figure 16. Page level IEL: Offset and Extent
An example of a page group level IEL that specifies page group offset and page group extent is shown in
Figure 17.
Figure 17. Page group level IEL: Offset and Extent [MODCA-3-168]
## Document Indexing

Figure 18 shows how the Medium Map information in a page level IEL is used to determine page
placement on a side of a sheet.
Tag Logical Elements
The T ag Logical Element (TLE) structured field supports the tagging of pages and page groups with an
attribute that may be used as an index key. The attribute is specified using attribute name and attribute value
triplets on the TLE structured field. When the TLE is specified in a document index, the element to be tagged
may be identified using a Fully Qualified Name triplet on the TLE structured field:
• FQN type X'87' triplet for a page [MODCA-3-169]
• FQN type X'0D' triplet for a page group [MODCA-3-170]
If a TLE in a document index does not contain an explicit page or page group reference, it inherits such a
reference from the last preceding IEL in the index. A TLE that explicitly references a page, or that inherits a
page reference from the last preceding IEL, is called a page level TLE. A TLE that explicitly references a page
group, or that inherits a page group reference from the last preceding IEL, is called a page group level TLE.
Figure 18. Page level IEL: Use of Medium Map Information
The TLE structured field tags the referenced element with the following information:
• Name of the attribute [MODCA-3-171]
## Document Indexing

• Value of the attribute [MODCA-3-172]
• Sequence number of the attribute, used to distinguish otherwise identical attributes [MODCA-3-173]
• Level number of the attribute, used to logically position the attribute in an application-defined hierarchy [MODCA-3-174]
Figure 19 shows how logical tags are applied to pages in a document using TLEs in an external
document index.
Figure 19. A Document with Logical Tags [MODCA-3-175]
## Document Links
Online, interactive forms of document processing require that linkages be established among components
within the document and from components within the document to components external to the document. One
example of such processing is the use of hypertext links, which are logical connections from one string of text
in a document to another string of text that is contextually related to the first. A viewing application can highlight
the source text, such as a technical term, and using hypertext links can provide the user with the option of
jumping to the linked text that is the glossary definition of the technical term. Another example is the
processing of annotations. A reviewer of a document may add comments to a string of text in a source
document, and require a link to connect these comments as annotations to the appropriate area in the source
document. A third example is the processing of appends. A document may be composed of pages
summarizing monthly phone calls. If a particular phone call is recorded late, it may need to be appended to an
existing page in the document, which requires a link from the existing page to the document component that
contains the late phone bill.
Links

Document links in the MO:DCA architecture are supported with Link Logical Element (LLE) structured fields.
Link Logical Elements
Link Logical Elements (LLE) structured fields are process elements that provide a general and extendable
linking capability between document components such as documents, page groups, pages, overlays, data
objects, and logical tags. The LLE structured field identifies a source and a target and specifies the purpose of
the link from source to target. The LLE optionally can specify a name that may be used to reference the LLE
and parameter data to be associated with the link.
LLEs may be embedded directly in the document that contains the source for the link. In that case, the source
link specified in the LLE inherits the document name and the names of all objects that are higher in the
document hierarchy. For example, if the LLE is in a page that is part of a page group, and if the source link
specifies an area on the page, then the source link inherits the names of the document, page group, and page.
LLEs may be embedded directly in the document that contains the target for the link. In that case, the target
link specified in the LLE inherits the document name and the names of all objects that are higher in the
document hierarchy. For example, if the LLE is in a page that is part of a page group, and if the target link
specifies an area on the page, then the target link inherits the names of the document, page group, and page.
LLEs may also be embedded in the index for the document that contains the source for the link, the target for
the link, or both. In that case, the source or target link in the LLE can inherit the document name from the index
if the document name is not explicitly specified in the respective repeating group. The source or target link may
also inherit the page or page group name specified by a preceding Index Element (IEL) structured field if such
names are not specified by the corresponding repeating group in the LLE and if the repeating group specifies
an object that is lower in the document hierarchy than the object defined by the IEL.
Document links defined by LLEs do not provide a presentation specification. It is left up to the application using
the LLEs to determine how to present the relationship between document components that are linked with an
LLE. For example, if an LLE is used to link a source document page to an object containing an annotation, a
viewing program may choose to highlight the annotated area on the source page and to display the annotation
in a separate window next to the source page. On the other hand, a print subsystem may choose to simply
gather all annotations and print them at the end of the source document with appropriate pointers to the source
pages.
An example showing how an LLE can be embedded in a document index to link an area on a page in the
source document to a text annotation is shown in Figure 20.
Links

Figure 20. Document Annotation using the LLE
Annotations and Appends
An annotation is a comment or explanation that is associated with the contents of a source document.
Annotations are normally generated based on a review of the final-form document using an interactive
presentation device such as a document viewer. Annotation data can be generated with a variety of data types
such as text and image, and can be carried within a number of document components including object
containers, overlays, pages, page groups, resource groups, and documents. Annotations are linked to the
source document component to which they apply using a Link Logical Element structured field.
An append is an addition to a source document component or a continuation of a source document
component. Appends can be generated with any MO:DCA document component. The simplest form of an
append is one document appended to another document. Appends are linked to the source document
component to which they apply using a Link Logical Element structured field.
The location of document components that carry annotations and appends follows the normal MO:DCA object
structure rules. For example, if an annotation is built using a page or a page group, it must be carried in a
document. If it is built using a data object, resource object, or object container, it can be carried in a resource
group.
## N-up Presentation
N-up is a presentation format where multiple pages are presented on a single physical medium. This format
provides the user with a high degree of flexibility for composing page objects onto sheets. When used on a
continuous-forms printer with a wide carriage, it can result in significant paper savings and improvements in
print reliability. In N-up presentation, each side of the physical medium is divided into a number of equal-size [MODCA-3-176]
## N-up Presentation

partitions, where the number of partitions is indicated by the number “N” in “N-up”. If duplex is specified, the
same N-up partitioning is applied to the back side as is applied to the front side. With simplex N-up
presentation, N pages are placed on the physical medium, and with duplex N-up presentation, 2N pages are
placed on the physical medium. Pages are placed into partitions using either a default N-up page placement or
an explicit N-up page placement, as specified in the Page Position (PGP) structured field. In the default N-up
page placement, consecutive pages in the data stream are placed into consecutively-numbered partitions. In
explicit N-up page placement, consecutive pages in the data stream are placed into explicitly-specified
partitions. For more information placement, see “Page Position (PGP) Format 2”. Pages
may be rotated within their partitions, and Page Modification Control (PMC) overlays may be applied to pages
before they are placed in their partition. Figure 21 shows the partitioning for wide continuous-forms
media, narrow continuous-forms media, and cut-sheet media; partitioning is not used with envelope media.
Partition numbering for various media is shown in Figure 61 to Figure 72.
Figure 21. N-up Partitions for Various Physical Media
1 Up
2 Up
3 Up
4 Up
Physical Media Width Physical Media Width Physical Media Width [MODCA-3-177]
## Cut-sheet Emulation (CSE) Print Mode
Some IPDS printers provide a cut-sheet emulation mode that can be used to print on continuous-forms media
that, once slit and collated, emulates two sheets of cut-sheet output. In this mode, the printer logically divides
the continuous-forms media in half parallel to the carrier strips and controls the placement of pages on either
the left side or the right side of the physical media as defined by a printer configuration option. The two portions
of the physical media are called sheetlets and are treated as if they were two separate pieces of cut-sheet
media. This logical division of the continuous-forms media is shown in Figure 22. When a MO:DCA
document is sent to a print server for printing in CSE mode, MO:DCA sheets and their content are mapped to
cut-sheet CSE sheetlets at the printer. Note that the top of each sheetlet is a narrow edge, and the default
sheetlet origin is the top-left corner of the sheetlet.
CSE Print Mode

Figure 22. Logical Division of Continuous Forms for Cut-sheet Emulation
The printer is configured for cut-sheet emulation mode by the printer operator while the printer is disconnected
from the print server. Cut-sheet emulation mode is activated by the print server after the printer has indicated
support for the mode. Note that cut-sheet emulation mode is not supported in viewing environments. Note also
that cut-sheet emulation mode is not supported with N-up presentation. When N-up is specified in the active
Medium Map, CSE mode is deactivated for the duration of that Medium Map.
When finishing operations are specified for a printer operating in CSE mode, the operations are specified for
and applied to each CSE sheetlet. That is, for finishing operations in CSE mode, the media is the sheetlet. This
is true whether the finishing operation is specified with a Finishing Operation (X'85') triplet or a UP3i Finishing
Operation (X'8E') triplet. [MODCA-3-178]
## Simulation of Preprinted Forms
Preprinted forms are often simulated with overlays. T o ensure that this simulation works correctly with any type
and color of page data, a new type of overlay, called a preprinted form overlay (PFO) is defined. In particular,
there are two types of preprinted form overlays:
• Medium Preprinted Form Overlay (M-PFO), which is used to simulate a preprinted form on a sheet-side. This [MODCA-3-179]
PFO is invoked using a keyword on the MMC structured field and causes the M-PFO to be applied to each
sheet-side in a copy subgroup.
• PMC Preprinted Form Overlay (PMC-PFO), which is used to simulate a preprinted form on a page on a [MODCA-3-180]
sheet-side. This PFO is invoked using the PMC structured field and causes the PFO to be applied to the
page processed by a PGP repeating group.
Only one type of PFO is allowed per sheet-side, and an M-PFO always overrides PMC-PFOs. If a M-PFO is
specified to simulate a preprinted form for a sheet-side, it is applied last, after all other page data and overlay
data has been applied to the sheet-side. If PMC-PFOs are also specified for pages on that sheet-side, they are
ignored, as are any additional M-PFOs. If a PMC-PFO is specified to simulate a preprinted form for a page on
a sheet-side, it is applied last, after all data for that page has been applied. Any additional PMC-PFOs for that
page are ignored.
Note that with N-up presentation, multiple pages can be presented on a sheet-side. Therefore it is possible to
have multiple PMC-PFOs on a sheet-side, each tied to a different page. A PMC-PFO is associated with a
single page, and should not overlap data from another page, because such overlap cannot occur with real
preprinted forms. If a PMC-PFO specified for one page overlaps data from another page, whether this data is
actual page data or PMC-PFO data for the other page, the appearance of the overlap area is presentation-
system dependent.
## Simulation of Preprinted Forms

PFOs are presented using a special mixing rule, called Formblend, that is designed to address the following
inherent characteristics of preprinted forms:
1. The color of preprinted forms cannot be knocked out. That is, the color of a preprinted form is its “color of [MODCA-3-181]
medium”. Unfortunately, a simulation of that color using AFP default mixing rules will allow that color to be
knocked out by overpainting it with either color of medium or with “white” (CMYK = X'00000000', RGB =
X'FFFFFF'). For example, if a yellow preprinted form is used and either color of medium or white is applied,
the color of the form remains yellow. When that form is simulated with the color yellow, for example on an
overlay, if either the color of medium or white is applied, the color of the form is the base color (usually
white), not yellow.
2. When a non-white color is applied to a colored preprinted form, some “blending” of the form color and the [MODCA-3-182]
new color occurs. The amount of blending depends on the two colors, the print technology (e.g. ink-jet or
EP), and the halftoning technology. However, a simulation of that color using AFP default mixing rules will
cause the new color to knock out the preprinted form color with no blending.
T o properly simulate the behavior of true preprinted forms, the Formblend mixing rule is defined as the mixing
rule for PFOs as follows. When PFO data is merged onto existing data:
• Wherever the color of the underlying data is either the color of medium or white (CMYK = X'00000000' or [MODCA-3-183]
RGB = X'FFFFFF'), the resultant color is the PFO color
• Wherever the color of the underlying data is not color of medium or white, the resultant color is a device- [MODCA-3-184]
specific blending of the underlying color with the PFO color that simulates how that device would blend the
underlying data onto a real preprinted form that has the PFO color. [MODCA-3-185]
## Document Finishing
Finishing operations, such as stapling and folding, for a print file may be specified using structures in the form
definition invoked for the print file. Such finishing operations may be applied at different levels of the print file,
and at each level the finishing operations have a defined scope:
• Print file level finishing: the scope is the complete print file. [MODCA-3-186]
• Document level finishing, all documents: the scope is each individual document in the print file. [MODCA-3-187]
• Document level finishing, selected document: the scope is a single document in the print file. [MODCA-3-188]
• Medium map level finishing, group of sheets: the scope is a collection of sheets. [MODCA-3-189]
• Medium map level finishing, each sheet: the scope is a single sheet. [MODCA-3-190]
Finishing operations for all levels are specified with a Medium Finishing Control (MFC) structured field. For
print file level and document level finishing, the MFC is specified in the document environment group (DEG) of
the form definition. For medium map level finishing, the MFC is specified in a medium map.
The actual finishing operation and its parameters are specified on the MFC with finishing triplets. Two triplets
are supported:
• Finishing Operation (X'85') triplet [MODCA-3-191]
• UP3i Finishing Operation (X'8E') triplet [MODCA-3-192]
These two triplets may be specified in any combination at any level, however the finishing operations must be
compatible.
When more than one finishing operation that involves a collection of media is specified for some portion of the
print file, a nesting of the operations is defined first by the scope of the operation (print file, document, medium
collection), and second by the order of the operation in the data stream. Finishing operations with an inherently
broader scope, for example, operations at the print file level, are nested outside of finishing operations with an
inherently narrower scope, for example, operations at the medium map level. If more than one operation is
specified with the same scope, the order of the finishing operation triplets defines the order of the nesting. The [MODCA-3-193]
## Document Finishing

first finishing operation specified defines the outermost nesting, and the last finishing operation specified
defines the innermost nesting. When a finishing operation is applied, all finishing operations nested inside this
operation are also applied. Finishing operations that are nested outside this operation are not affected. For a
complete definition of the finishing operation nesting rules, see “Finishing Operation Nesting Rules”. [MODCA-3-194]
## Exception Conditions
The application creating the data stream is responsible for producing a valid MO:DCA data stream, and the
application using the MO:DCA data stream is responsible for preserving a valid format. Nonetheless, exception
conditions may arise. A valid MO:DCA data stream is one that does not violate the architecture. A MO:DCA
data stream is in violation of the architecture when its structure or contents do not conform to the requirements
of the architecture.
An error is a product failure that produces or results in a data stream that violates the architecture. Since the
cause of an architecture violation cannot be determined when an application interprets a data stream, all
architecture violations are handled as exception conditions.
If absolute fidelity of a presentation document is not required, MO:DCA documents can be interchanged
among a larger set of products. It is possible for the processor of a MO:DCA data stream to continue
processing when it encounters exception conditions. This permits a process that cannot faithfully present a
document to continue with its best approximation.
Classifications
Exception conditions can be classified as:
• Syntactic [MODCA-3-195]
• Semantic [MODCA-3-196]
Syntactic exception conditions defined for this architecture include:
• Invalid or unknown structured field introducer (SFI); see “MO:DCA Structured Field Syntax” for [MODCA-3-197]
further discussion
• Invalid or unknown parameter within a recognized structured field [MODCA-3-198]
• Invalid parameter value within a recognized structured field [MODCA-3-199]
• Component appears in an invalid location or is missing [MODCA-3-200]
• Structured field appears in an invalid location or is missing [MODCA-3-201]
• Parameter is missing within a recognized structured field [MODCA-3-202]
Semantic exception conditions defined for this architecture include:
• Inconsistent or contradictory specifications [MODCA-3-203]
• Invalid relationships among the data-stream structured fields [MODCA-3-204]
## Exception Conditions

Detection
A MO:DCA-compliant product must detect the exception conditions defined by the architecture that apply to
the interchange set supported, within the scope of the supported OCAs. Exception conditions detected in the
structured fields and parameters that it interprets as it processes the data stream should be identified to an
exception handler within the receiver. The MO:DCA architecture defines eight categories of exception
conditions that can occur in an interchange data stream. The eight categories and their descriptions are as
follows:
Category Description
Invalid structured field
identifier
The structured field identifier contains invalid parameter values. Examples are
structured field identifiers with length values less than eight or invalid flag
settings. Not included in this category are invalid class codes, type codes, or
category codes.
Unrecognized identifier code This exception condition is caused by an unrecognized structured field
identifier code. It includes class codes or type codes that are not valid in this
architecture, or that are valid in this architecture, but are not acceptable in the
particular interchange set being used. It does not include invalid category
codes.
Data stream state violation A valid structured field appears in an invalid context in the data stream. This
exception includes:
• Repetition of a structured field within a state where repetition is not [MODCA-3-205]
permitted. An example is the appearance of two Page Descriptor structured
fields in a MO:DCA Active Environment Group.
• Appearance of a structured field within a state where it is not permitted. An [MODCA-3-206]
example is a Page Descriptor structured field appearing in a MO:DCA
Object Environment Group.
• Appearance of a structured field outside the specified structured field order [MODCA-3-207]
for that particular state. An example is a Begin Presentation T ext Object
structured field appearing in a MO:DCA Page before the Active
Environment Group.
Note: Not included in this category is the omission of a required structured
field.
Unrecognized structured field
or triplet
This exception includes:
• An SFI containing a category code: [MODCA-3-208]
– That is not valid in this architecture, or
– That is valid in this architecture, but is not acceptable in the particular
interchange set being used
• A triplet containing an identifier: [MODCA-3-209]
– That is not valid in this architecture, or
– That is valid in this architecture, but is not valid in the particular
interchange set being used
Required structured field
missing
A structured field, required to begin a containing component or to satisfy an
explicit invocation, is missing from the correct location in the data stream. An
example is a Begin Active Environment Group structured field missing from
the beginning of a page overlay.
Required parameter missing A parameter or parameter group, required in a specific structured field or in a
set of structured fields, is missing from the document component where it is
required. An example is a Begin Document structured field missing a Coded
Graphic Character Set Global Identifier triplet. [MODCA-3-210]
## Exception Conditions

Unacceptable parameter value A parameter contains a value that is not valid in this architecture, or it
contains a value that is valid in this architecture, but that is not acceptable in
the particular interchange set being used. An example is a value of 254 for
the X page units-base parameter in a Page Descriptor structured field. See
“PGD (X'D3A6AF') Syntax”.
Inconsistent parameter values A parameter contains a value that is inconsistent with the value of another
parameter in the structured field, or a parameter in another structured field.
An example is a name in an end structured field that does not match the
name in the corresponding begin structured field.
MO:DCA syntax tables identify the categories of exception conditions that can occur for each data element
through the use of a code listed in the Exc column. Each of the exception conditions is related to a bit position,
as shown in T able 15. The value assigned to Exc is based on the positions of the bits that
represent the exception condition categories that can apply to the data element. If no exception condition is
possible, the Exc column will contain X'00'.
For example, if it is possible for the data element to contain a value outside of the prescribed range, or if it is
possible for its value to conflict with that of another parameter, then both the unacceptable parameter value
and the inconsistent parameter value exception conditions can apply. The unacceptable parameter value is
represented by bit position six or B'00000010', and the inconsistent parameter value is represented by bit
position seven or B'00000001'. The code that is entered into the Exc column is formed by ORing the bit
representations of the exception condition categories that are possible, in this example resulting in
B'00000011' or X'03'.
Table 15. Bit Representation of MO:DCA Exception Condition Categories
Bit Position Exception Condition Category
Code
Binary Hexadecimal
Bit 0 Invalid structured field identifier B'10000000' X'80'
Bit 1 Unrecognized identifier code B'01000000' X'40'
Bit 2 Data stream state violation B'00100000' X'20'
Bit 3 Unrecognized structured field or triplet B'00010000' X'10'
Bit 4 Required structured field missing B'00001000' X'08'
Bit 5 Required parameter missing B'00000100' X'04'
Bit 6 Unacceptable parameter value B'00000010' X'02'
Bit 7 Inconsistent parameter values B'00000001' X'01'
None None B'00000000' X'00'
Exception Action
The action to be performed by a product that detects an exception condition is presentation-system dependent. [MODCA-3-211]
## Exception Conditions

