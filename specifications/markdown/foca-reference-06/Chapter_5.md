Chapter 5. FOCA Parameters
Digital data processing requires that you create data structures using a defined set of parameters and
parameter values organized in a specific format. FOCA provides a precise set of parameter definitions and
architected values or ranges of values you can use to create font resources and font references.
This chapter describes the conventions for defining FOCA parameters. Then, it defines the available FOCA
parameters, dividing them into the following five categories:
• Font-description parameters
• Font-metric parameters
• Character-metric parameters
• Character-shape parameters
• Character-mapping parameters
Defining FOCA Parameters
This section describes three parameter formats, the parameter types available for each format, and byte and
bit numbering conventions.
Parameter Formats
FOCA supports a variety of parameter formats, that is, types of syntax. The choice of format depends on the
environment where you want to use the font resource. The following are three common formats supported by
FOCA. See Chapter 6, “Font Interchange Formats”, on page 111 for more detailed information about the
formatting standards necessary to implement FOCA.
Fixed-Format
A fixed-format parameter is defined by its position and length within a string of fixed-format
parameters. The variable name and its associated meaning is implied by the position of the
parameter in the string. The position and length assigned to each fixed-format parameter is
defined in supporting documentation or elsewhere in the resource object. FOCA support of
fixed-format parameters requires a precise definition of parameters and the type of values
supported.
Self-Identifying
A self-identifying parameter has a set of fields that identify the parameter, specify its length,
and specify its values. The definition of these fields is specified by the implementing product or
architecture documentation.
Clear-Text
A clear-text parameter has two fields, which are separated by a delimiter. The first field
contains a character string that is the name of the parameter and the second field contains a
character string that represents the value of the parameter. The delimiter character and the set
of characters permitted is defined in the implementing product or architecture documentation.
Parameter Types
You define a FOCA parameter by identifying it and assigning it a value. The remaining sections of this chapter
list the names of the parameters and show Parameter type = as requiring a value in the form of one of the
following data types:
Character
string
A character string parameter value is any user, system, or font-supplier defined name. It is
composed of alphanumeric characters and can be any specified length. An example of a
character string is the font family name Sonoran Serif.

## Page 78

56 FOCA Reference
Unless otherwise specified (see “Graphic Character Set Global Identifier” on page 61), the
default set of graphic characters for character string data is the set of graphic characters
specified by IBM Graphic Character Set 103. That set consists of 94 characters: uppercase
and lowercase A–Z, the numerals 0–9, and the 32 special characters + < = > $ ` ^ ~ # % & * @
[ \ ] { | } ! " ' ( ) , _ - . / : ; ?
Unless otherwise specified (see “Code Page Global Identifier” on page 103), the default
encoding of the graphic characters for character string data is the IBM Interchange Code Page
for the environment in which the resource is being used.
Because font objects can be interchanged among environments, all syntactic representations
of font resources and font references should explicitly specify the Graphic Character Set
Global ID and the Code Page Global ID used for character string encoding.
Flag A flag parameter value indicates a binary flag and refers to a field interpreted as a binary bit,
which has a binary condition value of on (1), or off (0). Each flag bit can occur in a string of
binary flag bits as an independent variable, the setting of which does not affect the setting of
another flag bit in the same binary bit string.
Number A number parameter value uses real numbers or integer numbers to represent count or
magnitude.
Some quantities such as counts must be positive, but others such as measurements can be
either positive or negative. Numbers are assumed positive unless otherwise stated.
Code A code parameter value is a collection of architected choices. In general, parameters having
the code parameter type use a code (integers, letters, or acronyms) to identify the architected
choices. Specific interchange formats might use different code assignments to identify the
choices in the list or set, but those assignments must be unambiguously mappable to the
choices defined in this architecture. For reference purposes, this architecture assigns integer
codes to each of the architected choices.
Undefined A undefined parameter value is not defined by the architecture.
Byte and Bit Numbering
The byte and bit numbering conventions used in this publication follow those used within IBM System 370
Principles of Operation.
Byte
numbering
During both transmission and storage, data is viewed as a long horizontal string. For most
operations, access to data is left to right. The string of data is divided into units of eight bits
called bytes, which are the basic units of all data.
Each byte is identified by a positive integer, which is the address (offset) of that byte within the
data. Adjacent byte locations are identified by consecutive integers starting with 0, which
represent consecutive addresses.
Bit numbering Bytes are divided into eight bits. The bits are numbered, left to right, from 0 to 7. The four bits
on the left are sometimes referred to as the high-order and the four bits on the right as the low-
order bits. The bit numbers are not storage addresses: only bytes can be addressed. T o
change the value of individual bits in a byte, it is necessary to perform operations on the entire
byte.
Font-Description Parameters
This section lists and describes the descriptive parameters required to identify a font, select the appropriate
font for formatting, and locate the specified font for presentation. In general, most of the parameters defined in
this section will be used in both font resources and font references (the process of locating a referenced font
resource requires matching the parameter values in the font reference to the parameter values in the available

## Page 79

FOCA Reference 57
font resources). Where there are exceptions, the parameter definition will distinguish between font resource
usage and font reference usage.
Average Weighted Escapement
The Average Weighted Escapement parameter specifies the arithmetic average of the escapement of all, or
some subset of, the characters in a font. The escapement value for each character is weighted by its
anticipated frequency of use.
The average weighted escapement is computed as follows: each character increment is multiplied by its
frequency of use, the products of this multiplication are totaled, and the total is divided by 1000 times the
number of characters for which this average is being computed. This parameter is a descriptive attribute of the
font that specifies character spacing that is used when comparing fonts for font selection or substitution. When
comparing fonts, units of measure, character content, and frequency-of-use values for each font must be
known.
When computing a weighted average, based on some subset of the font characters (such as the lowercase
Latin alphabet), the frequency of use value for the desired characters should be set as required and set to zero
for all other characters. The computation of weighted average is calculated by using 1000 times the number of
characters in the subset, not the number of characters in the font.
If an implementation compares fonts using this value, it must either strictly control the set of frequency values
applied to the font characters to attain this value, or use a single frequency table to compute the values for
each font before making the comparison. Fonts that are interchanged among different environments might
have different character content or frequency-of-use values.
T o aid in the comparison of Latin-based fonts, a default set of frequency-of-use values is provided in the
definition of the Frequency of Use parameter. This set of values can be used explicitly to compute the average
weighted escapement, or can be used implicitly by providing a nonzero value of average weighted escapement
(using the 27 default frequency-of-use values) without providing any frequency-of-use values.
Parameter type = number
Synonyms = average character width
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Cap-M Height
The Cap-M Height parameter specifies the height above the baseline for uppercase character shapes. Cap-M
height is the nominal height of the uppercase characters and is usually equal to the height of the uppercase
letter M. The cap-M height value is specified by a font designer.
Parameter type = number
Transformation to Eastern Writing systems
This parameter should be set to the character box height for Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to capheight (Capitol Height). It is expressed as a relative rational number.

## Page 80

58 FOCA Reference
Character Rotation
The Character Rotation parameter specifies the rotation of the character box relative to the character baseline.
Refer to “Units of Direction” on page 36 for an explanation of character rotation. A user selects various writing
modes by specifying the appropriate character rotation.
FOCA permits the definition of character shapes that can be used for all rotations. A given character shape can
normally be rotated or translated to any position in the presentation space by using a variety of techniques.
However, to maintain the character spacing specified by a font designer, the information for character
positioning relative to the baseline must be specified for each required rotation of the character image.
Parameter type = number
Synonyms = font character rotation
Transformation to Eastern Writing systems
This parameter should be set to 0° or 270° for horizontal or vertical writing respectively.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to nomescdir (nominal escapement direction), and to nomwrmode (Nominal
Writing Mode). The nomescdir is expressed as a rational angle, measured counterclockwise from the
positive x-axis. For both horizontal and vertical writing, the AFP
Character Rotation and ISO Escapement
Direction values are the same. The nomwrmode property is expressed as the global name of the
corresponding nominal escapement direction (for example, ISO/IEC 9541-1/ /left-to-right).
Comment
The Comment parameter allows the creator or the user of the font resource to make comments. The content of
this parameter must be non-processable information and is ignored by any processing implementation.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter can be expressed as a non-iso-property, wherever the non-iso-property is permitted.
Design General Class (ISO)
The Design General Class parameter specifies the ISO (International Standards Organization) Font Standard
General Classification of the font family design. This parameter is intended for use in selecting an alternate font
when the requested font is not available. The General Class parameter is the least specific, the Subclass
parameter more specific, and the Specific Group parameter the most specific of the Design Class parameters.
Parameter type = number
Synonyms = design class
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the class subfield of dsngroup (Design Group). The ISO property is a code
in the range of 0 to 255.

## Page 81

FOCA Reference 59
Design Specific Group (ISO)
The Design Specific Group parameter specifies the ISO (International Standards Organization) Font Standard
Specific Group of the font family design. This parameter is intended for use in selecting an alternate font when
the requested font is not available. The General Class parameter is the least specific, the Subclass parameter
more specific, and the Specific Group parameter the most specific of the Design Class parameters.
Parameter type = number
Synonyms = design class
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the specific group subfield of dsngroup (Design Group). The ISO property
is a code in the range of 0 to 255.
Design Subclass (ISO)
The Design Subclass parameter specifies the ISO (International Standards Organization) Font Standard
Subclass of the font family design. This parameter is intended for use in selecting an alternate font when the
requested font is not available. The General Class parameter is the least specific, the Subclass parameter
more specific, and the Specific Group parameter the most specific of the Design Class parameters.
Parameter type = number
Synonyms = design class
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the subclass subfield of dsngroup (Design Group). The ISO property is a
code in the range of 0 to 255.
Em-Space Increment
The Em-Space Increment parameter specifies typographic space that corresponds to the space between
sentences. An Em-Space Increment is a formatting dimension that traditionally equals the vertical font size.
This value normally has a relative value of one (equal to the Unit-Em; see “Units of Measure” on page 34).
Parameter type = number
Synonyms = em increment
Transformation to Eastern Writing systems
This parameter does not apply to Eastern Writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the difference between the px,py (Positioning Point X,Y) and the ex,ey
(Escapement Point X,Y) values for the Em-space glyph, if it occurs in the subject font resource. The ISO
values are expressed as relative rational numbers with an x component, a y component, or both.

## Page 82

60 FOCA Reference
Extension Font
The Extension Font parameter indicates that this font resource was designed to be an extension (contains
user-defined characters) to another font (a base font containing a set of general-use characters).
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Family Name
The Family Name parameter specifies the common name for a font design. A font family includes all typeface
variations of the font design. The font family name should correspond to the family designation as it appears in
the appropriate product documentation.
The font-family name is specified by a font designer, for example, Sonoran Serif.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to fontfamily (Font Family). It is expressed as a character string value.
Font Local Identifier
The Font Local Identifier parameter specifies a numeric identifier assigned temporarily to a font resource within
the context of another object. The scope of the identifier is limited in time and space to the data stream in which
the font resource is being carried or referenced for use.
This parameter provides a short, unique tag by which the font object can be locally identified for reference
between functional entities. It is used in font references or font maps contained within a document data stream.
Parameter type = number
Synonyms = font local ID, font LID
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter represents a temporary identifier in
time and space, and generally should not be required in any interchange environment. If used, it should be
expressed as a non-iso-property in the Font Description property list.

## Page 83

FOCA Reference 61
Font Typeface Global Identifier
The Font Typeface Global Identifier parameter (usually called Font Global Identifier, FGID) specifies the unique
number assigned to the font typeface. The Font Typeface Global ID numbers that are supported are specified
in product documentation. Font Typeface Global IDs 1 through 65,279 are reserved for assignment by AFP
.
Parameter type = number
Synonyms = font global identifier, FGID, registry identifier, font-standard identifier, typeface global
identifier, typeface identifier
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Font Use Code
The Font Use Code parameter specifies the intended use of the graphic characters in a font. This parameter
permits font designers to specify their intent for fonts.
Parameter type = code
Valid choices:
0 No font use intent
1 Image symbol set for text in a graphics object
3 Pattern symbol set in a graphics object
4 Marker symbol set in a graphics object
5 High resolution indicator in a graphics object
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Graphic Character Set Global Identifier
The Graphic Character Set Global Identifier (GCSGID) parameter specifies the number assigned to a graphic
character set, which identifies the set of graphic characters contained in the font resource. The Graphic
Character Set Global ID numbers that are supported, are specified in product documentation. Graphic
Character Set Global IDs 1 through 65,279 are reserved for assignment by IBM.
Parameter type = number
Synonyms =graphic-character-set ID, graphic-character-set name, character-set name, character
complement, character-collection name, collection name
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to a single incglyphcols (Included Glyph Collections) field of the glyphcomp
(Glyph Complement) property. The ISO property uses the full ISO structured name, and any transform to
the ISO format should prepend the appropriate name prefix (see “Global Naming” on page 53). The ISO

## Page 84

62 FOCA Reference
glyphcomp property permits specification of one or more included or excluded collections, and one or more
included or excluded glyphs.
Hollow Font
The Hollow Font parameter specifies that the graphic characters of the font appear with only the outer edges of
the strokes. If the Hollow font flag is off (0), the graphic characters do not have a hollow appearance. If this flag
is on (1), the graphic characters do have a hollow appearance.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the Outline code (2) of the structure (Structure Code) property. The ISO
property is a code, identifying two different structures (solid and outline).
Italics
The Italics parameter specifies that the graphic characters are designed with a clockwise incline. If this flag is
off (0), the graphic character shapes have no clockwise italic design. If this flag is on (1), the character shapes
have a clockwise italic design.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the italic value (4) of the ISO posture (Posture Code) property. The ISO
property is a code, identifying five different posture combinations (upright, forward italic, backward italic,
forward oblique, and backward oblique).
Kerning Pair Data
The Kerning Pair Data parameter specifies that kerning pair data is available in the font resource. If this flag is
off (0), no kerning pair data is available in the font resource. If this flag is on (1), kerning pair data is available
for one or more character rotations.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter indicates the presence or absence
of kerning pair data in the font resource. In ISO the presence or absence of kerning pair data is indicated
by the presence or absence of the peas (Pairwise Escapement Adjusts) data itself. This AFP
flag can be
represented as a non-iso-property in the Font Description property list.

## Page 85

FOCA Reference 63
Maximum Horizontal Font Size
The Maximum Horizontal Font Size parameter specifies the maximum horizontal size for scaling, as specified
by a font designer. This value generally corresponds to the maximum character escapement value of a space
character (SP010000) in a font of the Nominal Vertical Font Size. The value is used to calculate a maximum
scaling ratio, which can be applied to the width of all characters of the font.
This parameter only occurs in font resources and does not occur in font references. The parameter is used to
determine the permitted maximum for shape manipulation.
This parameter should be expressed in fixed measurement units, not relative measurement units, and it should
correspond to the physical size of the font when presented on an output medium that is to be viewed at a
normal reading distance of 14 inches.
This parameter is often used for graphic-display fonts or for Asian languages where scaling can be different in
the horizontal and vertical directions.
The maximum horizontal font size is specified as the maximum horizontal increment to which the space
character (as a representative character of all characters in the font) can be scaled, given the vertical size as
specified by the nominal vertical font size parameter. The following two examples show how the scaling ratio
can be derived and used.
Example 1:
Assume a fixed-pitch font in which all characters (including the space character) have a character increment
of 1/12th inch (6 points), a nominal vertical font size of 10 points, and the font designer specifies a maximum
horizontal font size of 12 points.
Nominal Vertical Font Size = 10 Points
Nominal Horizontal Font Size = 6 Points
Maximum Horizontal Font Size = 12 Points
The maximum scaling ratio for all characters in this case is 2/1 (12 points divided by 6 points). If the font is
vertically scaled to 22 points, the nominal horizontal font size scales to 13.2 points (22/10 * 6). The maximum
character increment for each of the font's characters is 26.4 points (2/1 * 13.2).
Example 2:
Assume a proportional font in which all character increments are different and in which the space character
has a character increment of 4 points at a nominal vertical font size of 12 points, the letter A has a character
increment of 8 points at a nominal vertical font size of 12 points, and a font designer specified maximum
horizontal font size of 8 points.
Nominal Vertical Font Size = 12 Points
Nominal Horizontal Font Size = 4 Points
Maximum Horizontal Font Size = 8 Points
The maximum scaling ratio for all characters in this case is 2/1 (8 points divided by 4 points). If the font is
vertically scaled to 36 points, the nominal horizontal font size scales to 12.0 points (36/12 * 4). The maximum
character increment for the letter A becomes 48 points (2/1 * 8 * 36/12).
Parameter type = number
Synonyms = maximum character width, maximum space-character width, maximum horizontal size,
maximum horizontal point size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the writing-mode-dependent maxanascale (Maximum Anamorphic Scale)
property. The ISO property is expressed as a rational number corresponding to the ratio of the maximum to

## Page 86

64 FOCA Reference
nominal (for example, 2/1) size in the escapement direction, while the AFP parameter is expressed as a
maximum horizontal size from which the ratio is computed.
Maximum Vertical Font Size
The Maximum Vertical Font Size parameter specifies the maximum vertical size for scaling purposes as
specified by a font designer.
This parameter only occurs in font resources and does not occur in font references. The parameter is used to
determine the permitted maximum for shape manipulation.
The Maximum Vertical Font Size parameter should be expressed in fixed measurement units not relative
measurement units, and it should correspond to the physical size of the font when presented and viewed at a
normal reading distance of 14 inches.
This parameter is an indicator of the maximum valid size for the character-metrics values in this font. It is not
necessarily the maximum size that character images can be scaled. For font metrics that cannot or should not
be scaled, this parameter should be the same as the value of the Nominal Vertical Font Size parameter.
Parameter type = number
Synonyms maximum font vertical point size, maximum design size, maximum point size, maximum
body size, maximum size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to maxsize (Maximum Design Size). The ISO property is expressed as a
rational number in millimeters.
Measurement Units
The Measurement Units parameter contains four values that specify the unit base in the X direction, the unit
base in the Y direction, the units per unit base in the X direction, and the units per unit base in the Y direction,
respectively. Refer to “Units of Measure” on page 34 for information about the units of measure. These values
specify how the sizes of the components of the font are expressed.
For font resources (minimum, nominal, and maximum vertical font size), values must always be expressed in
fixed units of measure, which permits correct interpretation of minimum and maximum scaling. Relative units
can be used for all other character measurements. For consistency, specify all relative measurement units in 1/
1000th of a Unit-Em. The default measurement unit, to be used for all rational numbers in the absence of this
parameter, is 1/1440th of an inch.
This parameter can occur in font references or font resources. If the value is different in the font reference from
that found in a font resource, an application must normalize any associated metric values before performing a
compare.
Parameter type = two codes and two numbers
Valid unit-base code choices:
0 T en inches
1 T en centimeters
2 Relative units

## Page 87

FOCA Reference 65
Synonyms = base units, relative units
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to relunits (Relative Units). This parameter could be expressed as a non-iso-
property in the Font Description property list.
MICR Font
The MICR Font parameter indicates that this font resource was designed for use in Magnetic Ink Character
Recognition (MICR) applications.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Minimum Horizontal Font Size
The Minimum Horizontal Font Size parameter specifies the minimum horizontal size for scaling, as specified by
a font designer. The value of the parameter corresponds to the minimum character escapement value of a
space character (SP010000) in a font of the Nominal Vertical Font Size. The value is used to calculate a
minimum scaling ratio, which can be applied to the width of all characters of the font. This parameter is often
used for fonts in graphic displays or in Asian languages where scaling is permitted in horizontal and vertical
directions.
This parameter only occurs in font resources and does not occur in font references. The parameter is used to
determine the permitted minimum for shape manipulation.
This parameter should be expressed in fixed measurement units, not relative measurement units. It should
correspond to the physical size of the font when presented to be viewed at a normal distance of 14 inches.
The minimum horizontal font size is specified as the minimum horizontal increment to which the space
character (as a representative character of all characters in the font) can be scaled, given the vertical size as
specified by the nominal vertical font size parameter. The following two examples show how the scaling ratio
can be derived and used.
Example 1:
Assume a fixed-pitch font in which all characters (including the space character) have a character increment
of 1/12th inch (6 points), a nominal vertical font size of 10 points, and the font designer specifies a minimum
horizontal font size of 3 points.
Nominal Vertical Font Size = 10 Points
Nominal Horizontal Font Size = 6 Points
Minimum Horizontal Font Size = 3 Points
The minimum scaling ratio for all characters in this case is 1/2 (3 points divided by 6 points). If the font is
vertically scaled to 22 points, the nominal horizontal font size scales to 13.2 points (22/10 * 6). The minimum
character increment for each of the font's characters is 6.6 points (1/2 * 13.2).

## Page 88

66 FOCA Reference
Example 2:
Assume a Proportional Font in which all character increments are different and in which the space character
has a character increment of 4 points at a nominal vertical font size of 12 points, the letter A has a character
increment of 8 points at a nominal vertical font size of 12 points; and a font designer specified minimum
horizontal font size of 2 points.
Nominal Vertical Font Size = 12 Points
Nominal Horizontal Font Size = 4 Points
Minimum Horizontal Font Size = 2 Points
The minimum scaling ratio for all characters in this case is 1/2 (2 points divided by 4 points). If the font is
vertically scaled to 36 points, the nominal horizontal font size scales to 12.0 points (36/12 * 4). The minimum
character increment for the letter A becomes 12 points (1/2 * 8 * 36/12).
Parameter type = number
Synonyms = minimum character width, minimum space character width, minimum horizontal size,
minimum horizontal point size, minimum anamorphic scaling
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the writing-mode-dependent minanascale (Minimum Anamorphic Scale)
property. The ISO property is expressed as a rational number corresponding to the ratio of the minimum to
nominal (for example, 1/2) size in the escapement direction, while the AFP
parameter is expressed as a
minimum horizontal size from which the ratio is computed.
Minimum Vertical Font Size
The Minimum Vertical Font Size parameter specifies the minimum vertical size for scaling purposes as
specified by a font designer.
This parameter only occurs in font resources and does not occur in font references. The parameter is used to
determine the permitted minimum for shape manipulation.
The parameter should be expressed in fixed measurement units not relative measurement units. It should
correspond to the physical size of the font when presented to be viewed at a normal distance of 14 inches.
This parameter is an indicator of the minimum valid size for the character-metrics values in this font. It is not
necessarily the minimum size that character images can be scaled. For font metrics that cannot or should not
be scaled, this parameter should be the same as the value of the Nominal Vertical Font Size parameter.
Parameter type = number
Synonyms = minimum font vertical point size, minimum design size, minimum point size, minimum
body size, minimum size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to minsize (Minimum Design Size). The ISO property is expressed as a
rational number in millimeters.

## Page 89

FOCA Reference 67
Nominal Character Slope
The Nominal Character Slope parameter specifies the slope (stem incline is the term used in the ISO/IEC 9541
Font Information Interchange standard) of the graphic characters of this font. A value of zero for this parameter
indicates a positive direction parallel to the vertical axis. Increasing values indicate increasing clockwise
directions.
An upright font normally has a character slope of 0°. An italic font normally has a character slope of 17.5°
(seventeen degrees and thirty minutes). If nominal appearance of the character has a back slant, the angle is
large.
This parameter can occur in font resources or font references. If it occurs in a font resource, it is used to
specify the base reference for shape manipulation. If it occurs in a font reference, it is used to specify the
desired character slope. A processing application might either select a font resource having the same Nominal
Character Slope or might perform shape manipulation (within the range of Minimum to Maximum Character
Slope) to obtain the desired result.
Parameter type = number
Synonyms = character slope, nominal font character slope
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to postureangle (Posture Angle). The ISO property is expressed as a rational
angle, measured counterclockwise from the positive x-axis, while the AFP
value is measured clockwise
from the positive y-axis.
Nominal Horizontal Font Size
The Nominal Horizontal Font Size parameter specifies the nominal horizontal size for scaling. This parameter
corresponds to the character escapement value of the space character (SP010000) expressed in fixed units of
measure.
This parameter can occur in font resources and font references. If it occurs in a font resource, it is used to
specify the base reference for shape manipulation. If it occurs in a font reference, it has the same semantic as
“Specified Horizontal Scale Factor” on page 70.
The Nominal Horizontal Font Size parameter should be expressed in fixed measurement units (not relative
measurement units) and should correspond to the physical size of the font when presented on an output
medium that is to be viewed at a normal reading distance of 14 inches.
This parameter specifies the primary font-size indicator for fixed-pitch typewriter fonts. Historically, fixed-pitch
fonts were measured in characters per inch and represented with a size value for character width. Typographic
font size can be represented using the character width if a standard representative character (the space
character) is chosen as the basis for comparison.
Parameter type = number
Synonyms = space character width, horizontal size, horizontal font size, horizontal point size, nominal
horizontal point size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.

## Page 90

68 FOCA Reference
Nominal Vertical Font Size
The Nominal Vertical Font Size parameter specifies the vertical size (design size is the term used in the ISO/
IEC 9541 Font Information Interchange standard) of the font as specified by a font designer. The Nominal
Vertical Font Size parameter specifies the nominal size for which the character-metric values of this font are
defined.
This parameter can occur in font resources and font references. If it occurs in a font resource, it is used to
specify the base reference for shape manipulation. If it occurs in a font reference, it has the semantic of
specifying the desired size of the font.
The Nominal Vertical Font Size parameter should be expressed in fixed measurement units (not relative
measurement units) and should correspond to the physical size of the font when presented on an output
medium that is to be viewed at a normal reading distance of 14 inches.
Parameter type = number
Synonyms = nominal point size, font vertical point size, design size, point size, body size, nominal size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to dsnsize (Design Size). The ISO property is expressed as a rational number
in millimeters.
Overstruck Font
The Overstruck Font parameter specifies that the graphic characters of the font appear as though over-struck
by another graphic character (often a hyphen graphic character). If this flag is off (0), the graphic characters in
the font are not overstruck. If this flag is on (1), they are overstruck.
Note: This is not the same as the Throughscore parameter (see “Throughscore Position” on page 87).
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This characteristic should be represented by a
unique font family name. This AFP
parameter could also be expressed as a non-iso-property in the Font
Description property list.

## Page 91

FOCA Reference 69
Proportional Spaced
The Proportional Spaced parameter specifies that the character increments for each graphic character in the
font resource might vary. If this flag is off (0), the font is monospaced (all characters of the font have the same
character increment). If this flag is on (1), the font is proportionally spaced (some characters of the font have
different character increments).
Parameter type = flag
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the proportional value (2) of the modal escclass (Escapement Class)
property. The ISO property is a code, identifying two different escapement classes (monospaced and
proportional).
Private Use
The Private Use parameter specifies that some or all of the data contained in this font resource is privately
owned or protected by a licensing agreement. If this flag is off (0), the font is considered to be in the public
domain and can be modified, interchanged, or captured for use by other users; if this flag is on (1), the font is
considered to be privately owned, or subject to a licensing agreement, and should not be modified,
interchanged, or captured for use by any other users.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Resource Name
The Resource Name parameter identifies a resource object by a character string name. This parameter should
be a short name by which the resource object can be identified uniquely from among any other resource object
in a system or distributed data environment. Assignment and management of resource name uniqueness
depends upon the system environment in which the resource objects are used.
Parameter type = character string
Synonyms = resource tag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to fontname (Font Resource Name). The ISO property uses the full ISO
structured name, and any transform to the ISO format should prepend the appropriate name prefix (see
“Global Naming” on page 53).

## Page 92

70 FOCA Reference
Specified Horizontal Font Size
The Specified Horizontal Font Size parameter specifies the horizontal font size indicated by the document
creator or originator. The Specified Horizontal Font Size is specified in 20ths of a point (1440ths of an inch).
This parameter only occurs in a font reference. A processing application might either select a font resource
having the same Nominal Horizontal Font Size or might scale an outline font resource (within the range of
Minimum to Maximum Horizontal Font Size) to obtain the desired result.
Parameter type = number (registered number)
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Specified Horizontal Scale Factor
The Specified Horizontal Scale Factor parameter specifies uniform or anamorphic scaling of the graphic
characters and their associated metric information (for example, Character Increment). The value corresponds
to the numerator in a ratio consisting of the Specified Horizontal Scale Factor divided by the Specified Vertical
Font Size. The Specified Horizontal Scale Factor is specified in 20ths of a point (1440ths of an inch), and must
be a positive integer, greater than or equal to one.
This parameter only occurs in a font reference. If the value specified is equal to the Specified Vertical Font
Size, uniform vertical and horizontal scaling of the graphic characters occurs. If the value specified is greater or
less than the Specified Vertical Font Size, the graphic characters and their corresponding metric values are
stretched or compressed in the horizontal direction relative to the vertical direction by the ratio indicated.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO font resource architecture. This parameter, which appears only
in data stream font references, is used to express the desired anamorphic scaling to be applied to the
graphic characters in a font resource. The AFP
Specified Horizontal Scaling Factor, if used for selection
and scaling of an ISO font resource, should define a ratio that is within the range of the ISO Minimum and
Maximum Anamorphic Scale Factor values.

## Page 93

FOCA Reference 71
Specified Vertical Font Size
The Specified Vertical Font Size parameter specifies the vertical font size indicated by the document creator or
originator. The Specified Vertical Font Size is specified in 20ths of a point (1440ths of an inch).
This parameter only occurs in a font reference. A processing application might either select a font resource
having the same Nominal Vertical Font Size or might scale an outline font resource (within the range of
Minimum to Maximum Vertical Font Size) to obtain the desired result.
Parameter type = number (registered number)
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to dsnsize (Design Size). The ISO property is expressed as a rational in units
of millimeters. This property, used as a specified font size, should only occur in a font reference.
Transformable Font
The Transformable Font parameter specifies that the pattern data of this font resource is expressed using
algorithmic techniques that permit transformation of the graphic character shapes (for example, scaled to
different sizes). If this flag is off (0), the font will not be transformed. If this flag is on (1), the font can be
transformed.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Typeface Name
The Typeface Name parameter specifies the common name of the typeface. The typeface name is a common
name that the typeface is usually known by. It is usually some combination of family, posture, width, and
weight. The typeface name is assigned by a font designer, and it should correspond to the common name of
the font as it appears in the product documentation. An example of a typeface name is Sonoran Sans Serif
Roman bold condensed.
Parameter type = character string
Synonyms = facename
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to typeface (Typeface Name). The ISO property is expressed as a character
string message (intended as an informational property, not for match string compares).

## Page 94

72 FOCA Reference
Underscored Font
The Underscored Font parameter specifies that the graphic character pattern data of this font resource contain
underscores as part of the character shape. If this flag is off (0), the graphic character patterns in the font are
not underscored. If this flag is on (1), they are underscored.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list. This font characteristic could be represented by a unique font
family name.
Uniform Character Box Font
The Uniform Character Box Font parameter specifies that the raster (bit-mapped) pattern data for all the
graphic characters of the font resource are of the same size. This parameter is only valid if the “Pattern
T echnology Identifier” on page 100 indicates that the pattern data is of a bit map technology. If this flag is off
(0), the graphic character pattern boxes vary in size. If this bit is on (1), all the character boxes in the font are of
uniform height and width, and the height and width for each box are taken from the Maximum Character Box
Height and Maximum Character Box Width parameters, respectively.
The Character Box Height and the Character Box Width parameters specify the size of nonuniform character
boxes.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Weight Class
The Weight Class parameter indicates the visual weight (degree or thickness of strokes) of the collection of
graphic characters in the font resource. These values are assigned by a font designer, and the visual effect is
not defined in FOCA.
Parameter type = code
Valid choices:
1 Ultralight
2 Extralight
3 Light
4 Semilight
5 Medium (normal)
6 Semibold
7 Bold
8 Extrabold
9 Ultrabold

## Page 95

FOCA Reference 73
Synonyms = weight, weight-class indicator, font-weight-class indicator
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to weight (Weight Code).
Width Class
The Width Class parameter indicates a relative change from the normal aspect ratio (width-to-height ratio) as
specified by a font designer for the character shapes in a font. Although every character in a font might have a
different numeric aspect ratio, each character in a font of normal width has a relative aspect ratio of one. When
a new type style is created with a different width class (either by a font designer or by some automated means)
the relative aspect ratio of the characters in the new font is some percentage greater or less than those same
characters in the normal font. It is this difference that this parameter specifies.
The font designer assigns a width class designation for each design variation of a particular typeface.
However, if a font design is to be varied by automated means, percentage changes are allowed from normal to
each of the width class values. For uniformity, when AFP
fonts are varied by automated means, a percentage
change from normal is assigned to each of the width class values defined above.
The font designer normally assigns the width class values, and the corresponding percentage difference from
normal might not apply. Comparing the designated percentage values with the aspect-ratio differences for
several designed fonts, the ratios varied by as much as 50 percent from the designated percentage.
Note: It is not accepted practice to vary the width class of a font by automated means. The appearance
characteristics of a font can be severely altered by changing stroke width and changing the aspect ratio.
Parameter type = code
Valid choices:
1 Ultracondensed, which is 50 percent of normal
2 Extracondensed, which is 62.5 percent of normal
3 Condensed, which is 75 percent of normal
4 Semicondensed, which is 87.5 percent of normal
5 Normal (medium), which is 100 percent of normal
6 Semiexpanded, which is 112.5 percent of normal
7 Expanded, which is 125 percent of normal
8 Extraexpanded, which is 150 percent of normal
9 Ultraexpanded, which is 200 percent of normal
Synonyms = width, width-class indicator, font-width-class indicator, aspect ratio, width-height ratio,
proportionate width, proportion
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to propwidth (Proportionate Width Code).

## Page 96

74 FOCA Reference
X-Height
The X-Height parameter specifies the height of the body (not including the ascender) of lowercase graphic
characters above the character baseline. This value is assigned by a font designer. This parameter divided by
the Cap-M height calculates the ratio of X height to Cap-M height, which provides a useful selection or
substitution characteristic of the font design.
Parameter type = number
Synonyms = x height
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to lcheight (Lower Case Height). The ISO property is expressed as a relative
rational number.
Font-Metric Parameters
This section lists and describes those parameters that apply to all of the font characters. The parameters
provide information about a font that can be used for document formatting and document presentation.
Generally, these parameters and the Character-Metric Parameters are repeated for each character rotation
supported by a font resource. Most of the parameters defined in this section will be used in font resources and
will not be used in font references (the metric information is primarily used by applications for formatting and
shape presentation). Where there are exceptions, the parameter definition will distinguish between font
resource and font reference usage.
Default Baseline Increment
The Default Baseline Increment parameter specifies the nominal distance between character reference points
in the vertical direction (90 degrees to the character baseline) recommended by a font designer. This
parameter represents the baseline increment to use for this font, and it is specified by a font designer. This
value is normally greater than or equal to the nominal vertical font size, and it is often equal to the sum of the
nominal vertical font size and the internal leading.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the baselines of two columns of
graphic characters.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to minlinesp (Minimum Line Spacing). The ISO property is expressed as a
relative rational number.

## Page 97

FOCA Reference 75
External Leading
The External Leading parameter specifies the amount of white space, in addition to the vertical font size
increment, that can be added to the interline spacing without degrading the aesthetic appearance of a font. The
value of this parameter is usually specified by a font designer; it cannot be derived from other parameters in a
font. If fonts are designed with a very minimal amount of additional space above and below the character
images, additional space should be added between presentation lines to provide pleasing text appearance.
The value of this parameter might represent a font designer's recommendation for this additional leading
(interline spacing).
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter corresponds to any supplemental value the font designer recommends
for extending the distance between the baselines of two columns of graphic characters.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Figure Space Increment
The Figure Space Increment parameter specifies the character increment used for numerals. A figure space is
a formatting measure that sometimes equals the character increment of the numeric characters graphics of a
font. The font designer specifies the figure space.
If the numerals all have equal character increments, the value for this parameter can be derived from an
analysis of the character-increment parameters for the numeric characters of the font. If the numerals do not
have equal character increments, and the designer of the current font has not declared a value, the value of
the figure space increment cannot be determined.
Parameter type = number
Transformation to Eastern Writing systems
This parameter does not apply to Eastern Writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the tabescx (T abular Escapement X) andtabescy (T abular Escapement Y)
properties. The ISO values are expressed as relative rational numbers with an x component, a y
component, or both.

## Page 98

76 FOCA Reference
Internal Leading
The Internal Leading parameter specifies the nominal amount of white space above and below the character
shapes of the font that provides a nominal interline spacing for text that is aesthetically pleasing. This value is
specified by a font designer. Fonts are usually designed with some nominal amount of white space above and
below the character shapes. T o compress more text onto a page, characters can be presented with less space
between lines. The value of this parameter can be used to calculate the reduction in line spacing that can be
made without overwriting lines of text.
The value of this parameter is usually the difference between the vertical font size and the maximum baseline
extent for text characters of the font, but special characters that might extend beyond the normal extent of the
text character shapes are not included.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, the value of this parameter corresponds to the difference between the Nominal Font
Horizontal Size and the Maximum Baseline Extent for the 270° Character Rotation.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Kerning
The Kerning parameter is a flag that indicates whether any of the character metric parameters contain negative
values that permit the character images to kern. If this flag is off (0), there are no negative A-space or C-space
values. If this flag is on (1), A-space values or C-space values can be negative.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Kerning Pair Character 1
The Kerning Pair Character 1 parameter specifies the first character in a pair of characters for kerning. The
space between the two characters can be adjusted by the amount specified in the Kerning Pair X-Adjust
parameter. The character identifiers to be used are specified in the appropriate product documentation.
This parameter, the Kerning Pair Character 2 parameter, and the Kerning Pair X-Adjust parameter permit
adjusting the space between two specified characters, which can be used for defining kerning, presenting
mathematical formula, making composite characters, or making any other space adjustment that can be
required.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.

## Page 99

FOCA Reference 77
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to gname (Glyph Name). In the ISO architecture, Kerning pair data is
associated with the individual glyph metrics, thus the first glyph of the kerning pair is the gname associated
with the glyphmetrics property list in which the associated kerning pair data is located.
Kerning Pair Character 2
The Kerning Pair Character 2 parameter specifies the second character in a pair of characters for kerning. The
space between the two characters can be adjusted by the amount specified in the Kerning Pair X-Adjust
parameter. The character identifiers to be used are specified in the implementing product documentation.
This parameter, the Kerning Pair Character 1 parameter, and the Kerning Pair X-Adjust parameter permit
adjusting the space between two specified characters, which can be used for defining kerning, presenting
mathematical formula, making composite characters, or making any other space adjustment that can be
required.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the global name component (gname) of the peax (Pairwise Escapement
Adjust X) and peay (Pairwise Escapement Adjust Y) property, which are contained within the pean
(Pairwise Escapement Adjust Name) property list. The ISO architecture permits multiple, named kerning
pair techniques to exist for any given glyph (for example, Loose Pair, T ouching Pair, Sectored Pair, and
Class Pair). The AFP
kerning pair data should be represented under the global name: ISO(1) ICD(3) IBM
(18) IBM-CS(0) FONTS(1) KPAIR(3) (encoded according to the appropriate ASN.1 or SGML interchange
syntax).
Kerning Pair X-Adjust
The Kerning Pair X-Adjust parameter specifies the required escapement adjustment in the x direction for the
character pair specified in the Kerning Pair Character 1 and Kerning Pair Character 2 parameters that are
associated with this parameter. Letter pair kerning specifies two characters and the space adjustment to be
made between them. This parameter specifies the adjustment in the x direction, which can be positive or
negative. By using this adjustment, the two characters can be positioned closer together, farther apart, or
overlaying one another.
Parameter type = number
Synonyms = pairwise escapement x-adjust, adjustment X
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the adjustment component of the peax (Pairwise Escapement Adjust X)
property. The value is expressed as a relative rational number.

## Page 100

78 FOCA Reference
Maximum Ascender Height
The Maximum Ascender Height parameter specifies the maximum ascender height of any graphic character in
a font for a specific character rotation. This value is for a specific rotation of the character and is repeated for
each rotation supported.
Maximum ascender height is the maximum height attained by any character shape from the character baseline
to the top of the character box.
A negative value for this parameter specifies that all graphic characters in the font are completely below the
character baseline, as with subscripts.
The value of maximum ascender height can be derived from an analysis of the graphic-character shape
information for a font.
Parameter type = number
Synonyms = maximum ascender
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the maximum distance attained by any character shape from
the character baseline to the right-hand edge of the character box.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to maximum x, or maximum y value of the maxfontext (Maximum Font
Extents) value-list. If the AFP
Character Rotation is 0°, the Maximum Ascender Height value should be
expressed as the maximum y ISO value. If the AFP Character Rotation is 270°, the Maximum Ascender
Height value should be expressed as the maximum x ISO value. The value is expressed as a relative
rational number.
Maximum Baseline Extent
The Maximum Baseline Extent parameter specifies the space parallel to the character baseline that can be
used to place characters. If the maximum baseline offset and the maximum descender depth are positive, their
sum is the maximum baseline extent.
If no character in the font extends below the character baseline, the baseline extent is equal to the maximum
ascender height.
If no character in the font extends above the character baseline, the maximum baseline extent equals the
maximum descender depth. Baseline extent is used to determine the space required by characters at a
boundary such as the edge of a presentation space.
Parameter type = number
Synonyms = maximum base-line extension
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the Maximum Ascender Height plus the Maximum
Descender Depth (assuming the vertical baseline runs through the character box).
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the maxfontext (Maximum Font Extents) property list on import. This parameter could be
expressed as a non-iso-property in the Modal Properties property list.

## Page 101

FOCA Reference 79
Maximum Baseline Offset
The Maximum Baseline Offset parameter specifies the maximum distance of any character in a font from the
character baseline to the upper edge of the character box after it has been rotated as specified in the
Character Rotation parameter. If any part of the character box is above the character baseline, the baseline
offset is the perpendicular distance from the character baseline to the edge of the character box that is above
and farthest from the character baseline. When the complete character box is below the character baseline,
the baseline offset is the perpendicular distance from the character baseline to the edge of the character box
that is nearest to the character baseline. The maximum baseline offset is the greatest of the absolute values of
the baseline offset values.
If the Uniform Baseline Offset bit parameter is on (1), this parameter specifies a uniform baseline offset for all
characters for a given character rotation.
The Maximum Baseline Offset parameter positions the character box vertically from the character baseline
after rotation. If each graphic character has its own baseline offset, this parameter contains the maximum
baseline offset for any character in a font, and the amount of the baseline offset for each character is specified
by the Baseline Offset parameter.
Parameter type = number
Synonyms = uniform baseline offset
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the Maximum Ascender Height parameter. In the above
semantic, “right of” should be substituted for above, and “left of” should be substituted for below.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the maxfontext (Maximum Font Extents) property list on import. This parameter could be
expressed as a non-iso-property in the Modal Properties property list.
Maximum Character Box Height
The Maximum Character Box Height parameter specifies the height of uniform character boxes or the
maximum height of variable character boxes, depending on the value of the Uniform Character Box parameter.
If the Uniform Character Box parameter is off (0), this parameter specifies the maximum height of any
character box in a font, and the Character Box Height parameter specifies the height of each character box in a
font. This parameter can be used to determine if the character, when positioned, fits in the presentation space.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the maxfontext (Maximum Font Extents) property list on import. This parameter could be
expressed as a non-iso-property in the Modal Properties property list.

## Page 102

80 FOCA Reference
Maximum Character Box Width
The Maximum Character Box Width parameter specifies the width of uniform character boxes or the maximum
width of variable character boxes, depending on the value of the Uniform Character Box parameter.
If the Uniform Character Box parameter is off (0), the Maximum Character Box Width value specifies the width
of the widest character box in the font, and the Character Box Width parameter specifies the width of each
individual character box. This parameter can be used to determine if the character, when positioned, fits in the
presentation space.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the maxfontext (Maximum Font Extents) property list on import. This parameter could be
expressed as a non-iso-property in the Modal Properties property list.
Maximum Character Increment
The Maximum Character Increment parameter specifies the maximum character increment for all characters of
a font. If the Uniform Character Increment bit parameter is on (1), the character increment for all characters, for
a given character rotation, in a font is specified by this parameter. Otherwise, the character increment is
specified for each character by the Character Increment parameter.
For uniform increment fonts, this parameter specifies the increment from one graphic character to the next. If
the graphic characters have proportional increments, this parameter is the maximum character increment for
any character in a font, and the character increment for each character is specified by the Character Increment
parameter.
Parameter type = number
Synonyms = uniform character increment
Transformation to Eastern Writing systems
For vertical writing, this parameter is the maximum character increment for the 270° character rotation
metrics.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the px,py, ex and ey properties of the glyphmetrics property list on import. This parameter
could be expressed as a non-iso-property in the Modal Properties property list.

## Page 103

FOCA Reference 81
Maximum Descender Depth
The Maximum Descender Depth parameter specifies the maximum descender depth of all graphic characters
in a font for a specific character rotation. This maximum descender value applies to a specific rotation of the
character and is repeated for each rotation supported.
Maximum descender depth is the maximum depth attained by any graphic character in a font, from character
baseline to bottom of the character box. A negative descender depth specifies that all graphic characters in a
font are completely above the character baseline, for example, as when all graphic characters are
superscripts.
Parameter type = number
Synonyms = maximum descender
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the maximum distance attained by any character shape from
the character baseline to the left-hand edge of the character box.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to minimum x, or the minimum y value of the maxfontext (Maximum Font
Extents) value-list. If the AFP
Character Rotation is 0°, the Maximum Descender Depth value should be
expressed as the minimum y ISO value. If the AFP Character Rotation is 270°, the Maximum Descender
Depth value should be expressed as the minimum x ISO value. The value is expressed as a relative
rational number.
Maximum Lowercase Ascender Height
The Maximum Lowercase Ascender Height parameter specifies the maximum ascender height of the
lowercase graphic characters (a–z) in a font for a character rotation of 0°. The value of this parameter is a
descriptive attribute of a font that characterizes the appearance of the typeface design. This value is used for
comparing different font typefaces for alternate font selection or font substitution. This parameter can be used
to determine where floating accent characters should be positioned.
The value of maximum lowercase ascender height can be derived from an analysis of the graphic character
shape information of a font.
Parameter type = number
Synonyms = lowercase ascent
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.

## Page 104

82 FOCA Reference
Maximum Lowercase Descender Depth
The Maximum Lowercase Descender Depth parameter specifies the maximum descender depth of the
lowercase graphic characters (a–z) in a font for a character rotation of 0°. The value of this parameter is a
descriptive attribute of a font, characterizing the appearance of the typeface design. The value is used for
comparing different font typefaces for alternative font selection or font substitution. This parameter can be used
to determine where floating accent characters should be positioned.
The value of Maximum Lowercase Descender Depth can be derived from an analysis of the graphic character
shape information for a font.
Parameter type = number
Synonyms = lowercase descent
Transformation to Eastern Writing systems
This parameter does not apply to Eastern writing systems.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Maximum V(y)
The Maximum V(y) parameter is the maximum of all the Adobe ® ATM V(y) values returned for the characters in
a given CID font character set. The v(y) value is the y coordinate of the distance from the character origin to the
character positioning point. For horizontal writing modes, the character origin and the character positioning
point are normally coincident.
Figure 50. Example of V(y) and W(y) Parameters. This character means “beauty”.
Wy
Character Increm
en
t
B-space
V y
V ertical
Character
Positioning
Point
V ertical
Character
Escapement
Point
A-space
Character
Origin
C-space
Parameter type = number
Synonyms = none
Transformation to Eastern Writing systems
The definition of this parameter is writing-system independent, though the specific values will be different
depending on the design origin of the character and the location of the positioning point relative to that
origin.

## Page 105

FOCA Reference 83
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the maximum of all P(y) values in the font character set. The ISO property
is expressed as a relative rational number.
Maximum W(y)
The Maximum W(y) parameter is the maximum of all the Adobe ATM W(y) values returned for the characters in
a given CID font character set. The W(y) value is the y coordinate of the distance from the character
positioning point to the character escapement point. For horizontal writing modes, the character positioning
point and the character escapement point are normally on the same horizontal line.
Parameter type = number
Synonyms = Vertical Character Increment
Transformation to Eastern Writing systems
The definition of this parameter is writing-system independent, though the specific values will be different
depending on the location of the character positioning point and the character escapement point.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the maximum, absolute magnitude of all (P(y)-E(y)) values in the font
character set. The ISO property is expressed as a relative rational number.
Minimum A-space
The Minimum A-space parameter specifies either the most negative or the least positive A-space value for this
font. If the Uniform A-space parameter is off (0), the value of the Minimum A-space parameter specifies the
minimum A-space for all characters in a font. Otherwise, the parameter specifies the uniform A-space for all
characters for a given character rotation.
The minimum A-space value can be used to determine if the character extends outside the margin at the
beginning of a line.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the least A-space value for the 270° character rotation
metrics.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the px, py, minex, maxey properties of the glyphmetrics property list on import. This
parameter could be expressed as a non-iso-property in the Modal Properties property list.

## Page 106

84 FOCA Reference
Nominal Character Increment
The Nominal Character Increment parameter specifies the most commonly repeated character increment for
all characters of a font. This value permits storage and performance improvements in processing by using the
nominal value as a default, rather than searching for and processing highly repetitive character increments
(especially useful for Asian fonts, which have a large number of characters with the same character increment,
while still having some characters that can
be half-width or proportionally spaced).
Application note: CID-Keyed outline fonts (type X'1E') can be used with either single-byte code pages or
double-byte code pages and can contain proportional, full-width, and half-width characters. When such
a font contains a mixture of character sizes the Uniform Character Increment flag should be set to B'0'
(the font is not uniform) and the Nominal Character Increment parameter should contain the most
commonly repeated character increment. A character increment can be specified in the FNI for each
character in the font, but to decrease the size of the font, all characters that use the Nominal Character
Increment can be omitted from the FNI. T o determine the character increment for a particular character
in a CID-Keyed outline font, an application program should attempt to find an FNI repeating group entry
for the character; if one is not found, the Nominal Character Increment should be used. However, this
can be inefficient when the font is used with some double-byte code pages (such as, Asian code pages
whose characters are all of the same width) because most characters in the font do not have an FNI
entry. All double-byte raster fonts sections X'45'–X'FE' have this characteristic. Therefore, for processing
efficiency, the following algorithm can be used:
• When a CID-Keyed outline font is used with a double-byte code page, and the code page uses the
Double-Byte EBCDIC Presentation encoding scheme, the Nominal Character Increment can be used
as the character increment for all characters in the range X'4500'–X'FEFF'. The character increment
should be obtained from the FNI for all other characters. Note that for IBM-supplied CID-Keyed fonts,
characters in the range X'4100'–X'44FF' also have a uniform character increment and therefore the
Nominal Character Increment can be used for these characters, but since the FOCA architecture
allows characters in this range to be proportional, fonts from other sources might not adhere to this
convention.
• When a CID-Keyed outline font is used with any other code page, the FNI should first be searched for
a Character Increment, and if not found the Nominal Character Increment should be used.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the px, py, ex and ey properties of the glyphmetrics property list on import. This parameter
could be expressed as a non-iso-property in the Modal Properties property list.

## Page 107

FOCA Reference 85
Space Character Increment
The Space Character Increment parameter specifies the default value of the character increment to be used
with the space character that is identified by the Space Character Code Point parameter. The value of this
parameter is the character increment for the code point that corresponds to the space character. For double-
byte fonts, no other specification of the space-character increment is available. For single-byte fonts, the space
character increment can be specified in the Space Character Increment parameter or in the font-character
metrics information for the space character identifier. The space character increment is the value normally
used for the space between words in a sentence.
Parameter type = number
Synonyms = default variable space increment
Transformation to Eastern Writing systems
For vertical writing, this value corresponds to the space character increment for the 270° character
rotation.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the difference between the px,py (Positioning Point X,Y) and the ex,ey
(Escapement Point X,Y) values for the Normal Space glyph, if it occurs in the subject font resource. The
ISO values are expressed as relative rational numbers with an x component, a y component, or both.
Subscript Vertical Font Size
The Subscript Vertical Font Size parameter specifies a font designer's recommended vertical font size for
subscript characters associated with this font. If a font does not include all of the required subscript characters
for an application, and the application can substitute characters by anamorphically scaling the characters in a
font or by substituting characters from another font, this parameter specifies the recommended vertical font
size for those subscript characters.
Parameter type = number
Synonyms = subscript y size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the vsscaley (Variant Script Scale Y) property of the vscript (Variant Script)
property list. The ISO property is expressed as a rational anamorphic scaling of the Design Size. The ISO
architecture permits multiple, named variant scripts (for example, Left-vscript, Right-vscript, Ruby-vscript).
The AFP
subscript data should use the global name ISO/IEC 9541-1/ /RIGHT-VSCRIPT , represented in
accordance with the ASN.1 or SGML interchange syntax. The ISO standard uses the concept: right of the
alignment line when facing in the escapement direction.

## Page 108

86 FOCA Reference
Subscript X-Axis Offset
The Subscript X-Axis Offset parameter specifies a font designer's recommended vertical offset from the
character baseline to the character baseline for subscript characters associated with this font. Values are
expressed as a positive offset below the character baseline.
If a font does not include all of the required subscript characters for an application, and the application can
substitute characters by anamorphically scaling the characters of a font or by substituting characters from
another font, this parameter specifies the recommended vertical distance below the character baseline for
those subscript characters.
Parameter type = number
Synonyms = subscript x offset
Transformation to Eastern Writing systems
For vertical writing, this parameter does not apply.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the vsoffsetx (Variant Script Offset X) property of the vscript (Variant
Scripts) property list (see “Subscript Vertical Font Size” on page 85 for variant script naming). The ISO
property is expressed as a relative rational number.
Superscript Vertical Font Size
The Superscript Vertical Font Size parameter specifies a font designer's recommended vertical font size for
superscript characters associated with this font. If a font does not include all of the required superscript
characters for an application, and the application can substitute characters by anamorphically scaling the
characters of a font or by substituting characters from another font, this parameter specifies the recommended
vertical size for those superscript characters.
Parameter type = number
Synonyms = superscript y size
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the vsscaley (Variant Script Scale Y) property of the vscript (Variant Script)
property list. The ISO property is expressed as a rational anamorphic scaling of the Design Size. The ISO
architecture permits multiple, named variant scripts (for example, Left-vscript, Right-vscript, Ruby-vscript).
The AFP
superscript data should use the global name ISO/IEC 9541-1/ /LEFT-VSCRIPT , represented in
accordance with the ASN.1 or SGML interchange syntax. The ISO standard uses the concept: left of the
alignment line when facing in the escapement direction.

## Page 109

FOCA Reference 87
Superscript X-Axis Offset
The Superscript X-Axis Offset parameter specifies a font designer's recommended vertical offset from the
character baseline to the superscript character baseline associated with this font. Values for this parameter are
expressed as a positive offset above the character baseline.
If a font does not include all of the required superscript characters for an application, and the application can
substitute characters by anamorphically scaling the characters of a font or by substituting characters from
another font, this parameter specifies the recommended vertical distance above the character baseline for
those superscript characters.
Parameter type = number
Synonyms = superscript x offset
Transformation to Eastern Writing systems
For vertical writing, this parameter does not apply.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the vsoffsetx (Variant Script Offset X) property of the vscript (Variant
Scripts) property list (see “Superscript Vertical Font Size” on page 86 for variant script naming). The ISO
property is expressed as a relative rational number.
Throughscore Position
The Throughscore Position parameter specifies the recommendation of a font designer for drawing
throughscores for a font. This parameter is specified as the perpendicular distance from the character baseline
to the top of the throughscore stroke width. The stroke is parallel to the baseline. A value of zero means that
the top of the throughscore stroke is coincident with the baseline. A negative position specifies that the
throughscore stroke is below the character baseline.
Absence of the Throughscore Position parameter indicates that no throughscore position recommendation is
given. An implementation should use zero as a data stream default.
Parameter type = number
Synonyms = strikeout position, score position
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the center line and the vertical score
line running through the graphic characters.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the scoreoffsetx (Score Offset X) and the scoreoffsety (Score Offset Y)
properties of the Scores property list, except that the ISO property is measured to the center of the score.
The ISO property is expressed as a relative rational number in the x or y direction. The ISO architecture
permits multiple, named scores (for example, Rightscore, Leftscore, and Throughscore). The AFP
throughscore data should use the global name ISO/IEC 9541-1/ /THROUGHSCORE, represented in
accordance with the ASN.1 or SGML interchange syntax.

## Page 110

88 FOCA Reference
Throughscore Width
The Throughscore Width parameter specifies the recommendation of a font designer for the width (thickness)
of the throughscore for a font. A value of zero for this parameter indicates no font-designer recommendation is
available.
Absence of the Throughscore Width parameter indicates that no throughscore width recommendation is given.
An implementation should use the height of the character box of the underscore character as a data stream
default.
Parameter type = number
Synonyms = strikeout size, score thickness
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the scorethick (Score Thickness) property of the Scores property list (see
“Throughscore Position” on page 87). The ISO property is expressed as a relative rational number.
Underscore Position
The Underscore Position parameter specifies the recommendation of a font designer for drawing underscores
for a font. This parameter is specified as the perpendicular distance from the character baseline to the top of
the underscore stroke width. The stroke is parallel to the baseline. A value of zero means that the top of the
underscore stroke is coincident with the baseline.
Absence of the Underscore Position parameter indicates that no underscore position recommendation is
given. An implementation should use 75 relative units (75/1000 of an Em) as a data stream default.
A negative position specifies that the underscore stroke is above the character baseline.
If the Underscore font parameter is off (0), the character shapes of the font do not contain underscore strokes.
Parameter type = number
Synonyms = font underscore position, score position
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the center line and the vertical score
line running to the left. An implementation should use 75 relative units (75/1000 of an Em) from the edge of
the Maximum Character Box (75 relative units plus 1/2 the Maximum Character Box Width in relative units)
as a data stream default.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the scoreoffsetx (Score Offset X) and the scoreoffsety (Score Offset Y)
properties of the Scores property list, except that the ISO property is measured to the center of the score.
The ISO property is expressed as a relative rational number. The ISO architecture permits multiple, named
scores (for example, Rightscore, Leftscore, and Throughscore). The AFP
underscore should use the
global Name ISO/IEC 9541-1/ /RIGHTSCORE, represented in accordance with the ASN.1 or SGML
interchange syntax. ISO uses the concept: right of the baseline when facing in the escapement direction.

## Page 111

FOCA Reference 89
Underscore Width
The Underscore Width parameter specifies the recommendation of a font designer for the width (thickness) of
underscores for a font. A value of zero for this parameter indicates no font-designer recommendation is
available.
Absence of the Underscore Width parameter indicates that no underscore width recommendation is given. An
implementation should use the height of the character box of the underscore character as a data stream
default. If the character box height of the underscore character is not specified for a font, or is not available to
the process, the data stream default value should be equal to 50 relative units (50/1000 of an Em).
If the Underscore font parameter is off (0), the character shapes of a font do not contain underscore strokes.
Parameter type = number
Synonyms = underscore size, font underscore width, score thickness
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the scorethick (Score Thickness) property of the Scores property list (see
“Underscore Position” on page 88). The ISO property is expressed as a relative rational number.
Uniform A-space
The Uniform A-space parameter specifies that a uniform amount of A-space has been removed from all raster
(bit mapped) patterns for the font. This parameter is valid only if the “Uniform Character Box Font” on page 72
indicates uniform character boxes. If this flag is off (0), the Minimum A-space parameter specifies the smallest
A-space for all characters in the font. If this flag is on (1), the Minimum A-space parameter specifies a uniform
A-space value for all characters in the font.
Parameter type = flag
Transformation to Eastern Writing systems
For vertical writing, this parameter represents the A-space for 270° character rotation metrics.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.
Uniform Baseline Offset
The Uniform Baseline Offset parameter specifies that all raster (bit mapped) patterns for the font resource have
a common offset from the baseline to the top of the pattern box. If this flag is off (0), the baseline offset might
differ for each character; if this flag is on (1), the Maximum Baseline Offset parameter specifies a uniform
baseline offset for all characters in the font.
Parameter type = flag
Transformation to Eastern Writing systems
For vertical writing, this parameter represents the Uniform Baseline Offset for 270° character rotation
metrics.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.

## Page 112

90 FOCA Reference
Uniform Character Increment
The Uniform Character Increment parameter specifies that all character increments for the font resource are
the same (see also “Proportional Spaced” on page 69). If this flag is off (0), the graphic characters increment is
proportional; if this flag is on (1), the Maximum Character Increment parameter specifies a uniform character
increment for all characters in the font.
Parameter type = flag
Transformation to Eastern Writing systems
For vertical writing, this parameter represents the Uniform Character Increment for 270° character rotation
metrics.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Modal Properties property list.

## Page 113

FOCA Reference 91
Character-Metric Parameters
This section lists and describes those metric parameters that apply to individual characters of a font. The
parameters provide specific information about each character for document formatting and document
presentation. These parameters are repeated for each character in a font resource and for each rotation of
those characters.
A-space
The A-space parameter specifies the distance from the character reference point to the least positive character
coordinate system x-axis value of the character shape. The value of this parameter can be positive, zero, or
negative.
• A positive value means that the A-space and the character reference point lie in the escapement direction
before the start of B-space.
• An A-space value of zero means that there is no space preceding the character shape.
• A negative A-space value means that the A-space and the character reference point lie after the beginning of
B-space. Negative A-space is used in kerning.
The value of this parameter can be used to compute a character increment.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the character reference point and the
least positive character coordinate system x-axis value of the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture, but this parameter corresponds to the distance
between the px,py (Position Point X and Y) and the closest ext (Extents) property value along the
escapement direction line. T o convert the AFP
A-space value to the corresponding ISO value, the ISO px
and py values should first be set to 0,0 (the AFP reference point is at the origin of the coordinate system). If
the AFP Character Rotation is 0°, the minx (Minimum X Extent) should be set equal to the A-space value
(with appropriate unit-of-measure conversions). If the AFP Character Rotation is 270°, the maxy
(Maximum Y Extent) should be set equal to the negative of the A-space value (with appropriate unit of
measure conversions). The ISO property is expressed as a relative rational number.
Ascender Height
The Ascender Height parameter specifies the height of the topmost mark of a graphic character. If the
ascender height is negative, the graphic character lies completely below the character baseline. For example,
subscripts have a negative ascender height.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the character reference point and the
most positive character coordinate system y-axis value of the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture, but this parameter corresponds to the distance
between the px,py (Position Point X and Y) and a projection of the left extent (when facing in the
escapement direction) on a line passing through the px,py point, oblique to the escapement direction line.
T o convert theAFP
Ascender Height value to the corresponding ISO value, the ISO px and py values
should first be set to 0,0 (the AFP reference point is at the origin of the coordinate system). If the AFP

## Page 114

92 FOCA Reference
Character Rotation is 0°, the maxy (Maximum Y Extent) should be set equal to the Ascender Height value
(with appropriate unit-of-measure conversions). If the AFP Character Rotation is 270°, the maxx
(Maximum X Extent) should be set equal to the Ascender Height value (with appropriate unit of measure
conversions). The ISO property is expressed as a relative rational number.
Baseline Offset
The Baseline Offset parameter specifies the distance from the character baseline to the topmost edge of a
character box. If the Uniform Baseline Offset parameter is on (1), the Maximum Baseline Offset parameter
specifies the uniform offset for all the font's graphic characters, and this parameter value can be omitted. If the
Uniform Baseline Offset parameter is off (0), the Maximum Baseline Offset parameter specifies the maximum
offset for all the font's graphic characters, and this parameter specifies the baseline offset for each of the font's
graphic characters.
The baseline offset value is positive when any part of the character box is positioned above the character
baseline, and it is negative when the complete character box is positioned below the character baseline. If any
part of the character box is above the character baseline, the baseline offset is the distance from the character
baseline to the edge of the character box that is parallel, above, and farthest from the character baseline. If the
complete character box is below the character baseline, the baseline offset is the distance from the character
baseline to the edge of the character box that is nearest and parallel to the character baseline.
For raster fonts, the top edge of the top of the character box usually corresponds to the origin of the character-
shape information, and the baseline offset is useful in positioning the character image.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the Ascender Height. In the above semantic, “right of” should
be substituted for above, and “left of” should be substituted for below.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the ext (Extents) property list on import. This parameter could be expressed as a non-iso-
property in the Glyph Properties property list.
B-space
The B-space parameter specifies the width of the (bounded) character box. The value of this parameter can be
used to compute a character increment.
Parameter type =number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the most positive and least positive
character coordinate system x-axis value of the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture, but this parameter corresponds to the distance
between the minx and maxx (Min and Max X Extent), or the miny and maxy (min and Max Y Extent)
property value, depending on the escapement direction. T o convert the AFP
B-space value to the
corresponding ISO value, the ISO minx and maxy values should first be computed (see “A-space” on page
91). If the AFP Character Rotation is 0°, the maxx (Maximum X Extent) should be set equal to the minx
value plus the AFP B-space value (with appropriate unit-of-measure conversions). If the AFP Character
Rotation is 270°, the miny (Minimum Y Extent) should be set equal to the maxy value plus the negation of
the B-space value (with appropriate unit-of-measure conversions). The ISO property is expressed as a
relative rational number.

## Page 115

FOCA Reference 93
Character Box Height
The Character Box Height parameter specifies the height of the character box for a graphic character. If the
Uniform Character Box parameter is on (1), the uniform character box height for all of the font's graphic
characters is specified by the Maximum Character Box Height parameter and this parameter value is ignored.
If the Uniform Character Box parameter is off (0), the Maximum Character Box Height parameter specifies the
maximum character box height for all the font's graphic characters, and this parameter specifies the character
box height for each of the font's graphic characters.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the difference between the miny and maxy (Minimum Y and Maximum Y Extents) of the
Glyph Properties property list on import. This parameter could be expressed as a non-iso-property in the
Glyph Properties property list.
Character Box Width
The Character Box Width parameter specifies the width of the character box for a character. If the Uniform
Character Box parameter is on (1), the uniform character box width for all of the font's graphic characters is
specified by the Maximum Character Box Width parameter and this parameter value is ignored. If the Uniform
Character Box parameter is off (0), the Maximum Character Box Width parameter specifies the maximum
character box width for all the font's graphic characters, and this parameter specifies the character box width
for each of the font's graphic characters.
The width of the character box normally corresponds to the value of the B-space parameter.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the difference between the minx and maxx (Minimum X and Maximum X Extents) of the
Glyph Properties property list on import. This parameter could be expressed as a non-iso-property in the
Glyph Properties property list.

## Page 116

94 FOCA Reference
Character Increment
The Character Increment parameter value is the algebraic sum of the A-space, the B-space and the C-space
values for a character shape. Using a different value can result in displeasing aesthetic effects.
If the Uniform Character Increment parameter is on (1), the uniform character increment for all of the font's
graphic characters is specified by the Maximum Character Increment parameter and this parameter is ignored.
If the Uniform Character Increment parameter is off (0), the Maximum Character Increment parameter
specifies the maximum character increment for all the font's graphic characters, and this parameter specifies
the character increment for each of the font's graphic characters.
Parameter type = number
Synonyms = character escapement, escapement
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the reference point and the
escapement point for the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter is not required for export and can
be derived from the difference between the px,py (Position Point X and Y) property value and the ex,ey
(Escapement Point X and Y) property value on import. This parameter could be expressed as a non-iso-
property in the Glyph Properties property list.
C-space
The C-space parameter specifies the width of the space from the (bounded) character box to the escapement
point. This parameter can be positive, zero, or negative.
• A positive value means that the C-space lies (in the escapement direction) after B-space.
• A C-space value of zero means that there is no space following the character shape.
• A negative value means that C-space lies (in the escapement direction) before the end of B-space.
The value of this parameter can be used to compute a character increment.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the character escapement point and
the least positive character coordinate system x-axis value of the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture. This parameter is not required for export, and can be
computed from ISO glyph properties on import. This parameter could be represented as a non-iso-property
in the Glyph Properties property list.

## Page 117

FOCA Reference 95
Descender Depth
The Descender Depth parameter specifies the descender depth of a graphic character. A negative descender
depth specifies that the graphic character lies completely above the character baseline; superscript graphic
characters are an example.
Parameter type = number
Transformation to Eastern Writing systems
For vertical writing, this parameter is equal to the distance between the character reference point and the
most negative character coordinate system y-axis value of the 270° rotated character shape.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture, but this parameter corresponds to the distance
between the px,py (Position Point X and Y) and a projection of the right extent (when facing in the
escapement direction) on a line passing through the px,py point, oblique to the escapement direction line.
T o convert theAFP
Descender Depth value to the corresponding ISO value, the ISO px and py values
should first be set to 0,0 (the AFP reference point is at the origin of the coordinate system). If the AFP
Character Rotation is 0°, the miny (Minimum Y Extent) should be set equal to the Descender Depth value
(with appropriate unit-of-measure conversions). If the AFP Character Rotation is 270°, the minx (Minimum
X Extent) should be set equal to the Descender Depth value (with appropriate unit of measure
conversions). The ISO property is expressed as a relative rational number.
Graphic Character Global Identifier
The Graphic Character Global Identifier parameter specifies the registered identifier of a graphic character.
Unless otherwise specified, the default encoding is EBCDIC and the default length is 8 bytes.
The IBM Graphic Character Global IDs that are supported are specified in product documentation.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to gname (Glyph Name). The ISO property uses the full ISO structured name,
and any transform to the ISO format should prepend the appropriate name prefix (see “Global Naming” on
page 53). AFP
graphic characters, used in font resources, should be represented under the global name
prefix ISO(1) ICD(3) IBM(18) IBM-CS(0) FONTS(1) GCGID(2), encoded according to the appropriate
ASN.1 or SGML interchange syntax.

## Page 118

96 FOCA Reference
Character-Shape Parameters
This section lists and describes those parameters required for presentation of the character shapes. The
information does not include those parameters required for positioning the characters in the presentation
space. These parameters are repeated for each technology supported by the font resource. Most of the
parameters defined in this section are used in font resources and are not used in font references (the character
shape information is primarily used to present the character shape on the presentation surface). Where there
are exceptions, the parameter definition will distinguish between font resource and font reference usage.
Design Resolution X
The Design Resolution X parameter specifies the intended presentation resolution in the x direction for this
character shape representation technology. Transformations of a character shape from one technology, or
device specific format, to another requires knowledge about the resolution of the target devices. For example,
the transformation of an image from a resolution of 240 by 240 pels per inch to 60 by 72 per inch requires a
different transformation from that to a resolution of 300 by 300 pels per inch. An outline-representation
technique might only need the output-device resolution, but a raster representation needs both the design
resolution and the output-device resolution.
Parameter type = number
Synonyms = x device resolution
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.
Design Resolution Y
The Design Resolution Y parameter specifies the intended presentation resolution in the y direction for this
character-shape-representation technology. Transformation of a character shape from one technology, or
device specific format, to another requires knowledge about the resolution of the target devices. For example,
transformation of a 240 by 240 resolution image to a 60 by 72 resolution image requires a different
transformation from that to a 300 by 300 resolution image. An outline representation technique might only
require knowledge of the output-device resolution, but a raster representation requires knowledge of both the
design resolution and the output-device resolution.
Parameter type = number
Synonyms = y device resolution
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.

## Page 119

FOCA Reference 97
Linkage Code
The Linkage Code parameter specifies whether or not the character IDs in the CMAP file are linked to the
character IDs in the Name Map file. CMAP files that are not linked to the Name Map file should only be used
with the code page identified by the CPGID parameter.
Parameter type = code
Valid Choices:
0 Linked
1 Unlinked
Synonyms = none
Transformation to Eastern Writing systems
This parameter is used most often with eastern writing systems, but is not restricted to any particular
writing system.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture.
Object Type
The Object Type parameter provides a method of identifying various objects that can be imbedded in a font
resource. The objects identified by this parameter are most often non-AFP data objects which are architected
by other companies or organizations. The format specification for those objects must be obtained from the
defining source.
Parameter type = code
Valid Choices:
0 No information
1 CMAP file
5 CID file
6 PFB file
7 AFM file
8 File Name Map
Synonyms = none
Transformation to Eastern Writing systems
This parameter is used most often with eastern writing systems, but is not restricted to any particular
writing system.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture.

## Page 120

98 FOCA Reference
Pattern Data
The Pattern Data parameter specifies the pattern data for this character-shape representation technique. The
data might represent any of the character representation techniques and is formatted according to the
definition of the pattern technology and compression algorithm.
Parameter type = undefined data
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.
Pattern Data Alignment Code
The Pattern Data Alignment Code parameter specifies the alignment of the beginning of each character's
pattern data (see also Pattern Data Alignment Value). The code values assigned to this parameter represent
the exponent of the base 2 corresponding to the byte alignment. For example, a code value of 2 means raise
the base, 2, to the second power to get the Pattern Data Alignment Value of 4 bytes, a full word.
Use of the Pattern Data Alignment parameter allows flexibility in aligning pattern data in storage to permit use
of different computing systems with differing abilities in the degree of fineness in addressing storage.
Parameter type = code
Valid choices:
0 One-byte Alignment
1 Two-byte Alignment
2 Four-byte Alignment
3 Eight-byte Alignment
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.

## Page 121

FOCA Reference 99
Pattern Data Alignment Value
The Pattern Data Alignment Value parameter specifies the byte alignment of the beginning of each character's
pattern data (see also Pattern Data Alignment Code). The value assigned to this parameter is used as a
multiplier of the Pattern Data Offset to determine the corresponding byte offset.
Use of the Pattern Data Alignment Value parameter allows flexibility in aligning pattern data in storage to
permit use of different computing systems with differing abilities in the degree of fineness in addressing
storage.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.
Pattern Data Count
The Pattern Data Count parameter specifies the total quantity of shape data, expressed in number of bytes.
The data count does not include any header or trailer information that can be used in an interchange format to
identify the shape data.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.

## Page 122

100 FOCA Reference
Pattern Data Offset
The Pattern Data Offset parameter is used in conjunction with the Pattern Data Alignment Value parameter or
the Pattern Data Alignment Code parameter to calculate the actual byte offset of a character's shape data from
the beginning of the pattern data (see “Pattern Data” on page 98). The byte offset of the pattern data is the
Pattern Data Offset number multiplied by the alignment value defined by the Pattern Data Alignment Value
parameter or the Pattern Data Alignment Code parameter.
For example, if the Pattern Data Alignment is four-byte alignment, the actual pattern data offset, in bytes, is the
Pattern Data Offset number multiplied by 4. If the Pattern Data Offset number is 97 and the Pattern Data
Alignment is four-byte alignment, the actual pattern data offset is 97 * 4 = 388 bytes.
The value of this parameter is the actual data offset divided by the alignment value defined by the Pattern Data
Alignment Value or the Pattern Data Alignment Code parameter. In the preceding example, the offset value of
97 is the actual offset (388) divided by the alignment value of 4: 388 / 4 = 97.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.
Pattern Technology Identifier
The Pattern T echnology Identifier parameter specifies the technologies for the font graphic patterns for this
font. Pattern T echnology is defined by implementing font products and is documented in the font product
documentation.
This parameter is used in both font resources and font references.
Parameter type = code
Valid choices:
5 Laser Matrix N-Bit Wide Horizontal Sections
30 CID Keyed Outline Font T echnology
31 Type 1 PFB Outline Font T echnology
Synonyms = shape technology, font-shape technology
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.

## Page 123

FOCA Reference 101
Precedence Code
The Precedence Code parameter specifies whether or not a particular object is the primary object of its type in
the resource, or if it is an auxiliary (alternate) object of its type in the resource.
Implementation Note: When the Object Type is 5 (CID Font, “Object Type” on page 97), and the Font
Character Set is a base font (“Extension Font” on page 60), the precedence code will be set to 0
(primary). The precedence code for CID fonts will be set to 1 (auxiliary) if this is an extension font. When
the Object Type is 1 (CMap File), the precedence code will be set to 0 (primary) if this CMap is the first
CMap to be used for the current CPGID and Writing Mode. The precedence code for CMaps will be set
to 1 (auxiliary) if this CMap is pointed to by another CMap.
Parameter type = code
Valid Choices:
0 Primary
1 Auxiliary
Synonyms = none
Transformation to Eastern Writing systems
This parameter is used most often with eastern writing systems, but is not restricted to any particular
writing system.
Transformation to ISO/IEC 9541 font architecture
No equivalent property exists in the ISO architecture.
Shape Pattern Index
The Shape Pattern Index parameter specifies an index into the repeating group in the Pattern Data Offset
parameter that corresponds to the graphic character associated with this parameter. The index values defined
by this parameter allow using the same pattern data information for multiple character rotations. Some pattern
data can be unique for different rotations, and other pattern data can be used for multiple rotations.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Glyph Shapes property list.

## Page 124

102 FOCA Reference
Writing Direction Code
The Writing Direction Code parameter specifies the nominal direction in which characters of the font are written
or read by the end user.
Implementation Note: This field is only used with Object Type 1 (CMap, “Object Type” on page 97). When the
value of this field is 1 or 2 (horizontal or vertical), the CMap is intended to be used only for the specified
writing direction, but when the value of this field is 3 (both), the CMap is writing direction independent
and may be used for either vertical or horizontal writing.
Parameter type = code
Valid Choices:
0 No information
1 Horizontal (left to right or right to left)
2 Vertical
3 Both Vertical and Horizontal
Synonyms = writing mode, character rotation
Transformation to Eastern Writing systems
The definition of this parameter is writing-system independent, though the specific values will be different
depending on the direction specified.
Transformation to ISO/IEC 9541 font architecture
This parameter corresponds to the Writing Mode property. The ISO property is a named value.

## Page 125

FOCA Reference 103
Character-Mapping Parameters
This section lists and describes the parameters required to associate (map) the code points to the graphic
character identifiers for a code page. Most of the parameters defined in this section are used in font resources
and are not used in font references. The character mapping information is primarily used by applications to
associate code points in a document to the graphic character information in a font resource. Where there are
exceptions, the parameter definition will distinguish between font resource and font reference usage.
Code Page Description
The Code Page Description parameter provides a descriptive title or short description of the code page. The
value assigned to this parameter should be the same as that assigned by the IBM Code Page registration
authority when registering the code page, though any descriptive character string is permitted.
Parameter type = character string
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Code Page Global Identifier
The Code Page Global Identifier (CPGID) parameter specifies the number assigned to a code page. The code
page numbers that are supported are specified in product documentation.
Code Page Global IDs 1 through 65,279 are reserved for assignment by IBM.
This parameter is used in both font resources and font references.
Parameter type = number
Synonyms = code page, code-page identifier, code-page-standard identifier
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Code Point
The Code Point parameter specifies the value of the integer sequence assigned to a graphic character in an
ordered list of control and graphic characters. The code point numbers assigned depend on the code-page
definition. The code page definition can be specified by a user, or as specified in the appropriate product
documentation.
The Code Point parameter consists of a one-byte binary number representing a graphic character in a list of
256 potential control and graphic characters. If the Number of Code Points Available parameter specifies that
the available code points exceed 256, it is necessary that this parameter is used with the Section Number
parameter.

## Page 126

104 FOCA Reference
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Encoding Scheme
The Encoding Scheme parameter is a three-part, two-byte, hexadecimal number defined in the Character Data
Representation Architecture (CDRA) Reference, which identifies the scheme used to code graphic character
data. The first part is a one-digit hexadecimal number (1/2 byte) corresponding to the Basic Encoding
Structure. The second part is a one-digit hexadecimal number (1/2 byte) corresponding to the Number of Bytes
per Code Point, and the third part is a two-digit hexadecimal number (1 byte) corresponding to the Code
Extension Method.
Font resources and font references do not require the full set of Encoding Scheme parameter values
supported by CDRA. The valid set of choices required for FOCA support are identified below.
ASCII is the American Standard Code for Information Interchange, and EBCDIC is the Extended Binary Coded
Decimal Interchange Code. ISO is the International Standards Organization, which uses both a seven-bit
binary code and an eight-bit binary code to represent characters. UCS is the ISO universal coded character set
intended to contain most of the graphic characters used in languages and scripts throughout the world; the
fixed two-byte version of UCS (UCS-2) is commonly known as Unicode. UCS Presentation is a subset of UCS
that contains only code points that can be directly mapped to a single glyph.
Parameter type = code
Valid choices:
Basic Encoding Structure:
0 No specified organization
2 IBM-PC Data
6 EBCDIC Presentation
8 UCS Presentation (an all glyph scheme based on UCS)
Number of Bytes per Code Point:
1 Fixed Single-byte
2 Fixed Double-byte
Code Extension Method:
00 No specified extension
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.

## Page 127

FOCA Reference 105
Graphic Character Identifier Type
The Graphic Character Identifier Type parameter identifies the naming source and the method used to identify
graphic characters.
Note: Values 0, 1, and 4 are not used in FOCA fonts.
Parameter type = code
Valid choices:
0 No character identification defined
1 ISO Registered EBCDIC Glyph ID
2 IBM Registered EBCDIC GCGID
3 Font Specific ASCII Character Name
4 Font Specific Binary Glyph Index
5 CMap Binary Code Point
Note:
• An ISO Registered EBCDIC Glyph ID is the leaf element of a hierarchical structured name of a
glyph registered by the Association for Font Information Interchange in accordance with the
registration procedures of ISO/IEC 10036. The leaf element is a character string, representing a
decimal number in the range of 1 to (2**32)-1, with no leading zeros. The ISO Registered Glyph
IDs identified by this Graphic Character Identifier Type are expressed in EBCDIC encoding, with a
length defined by the Graphic Character Identifier Length parameter. The full structured name
is: “ISO/IEC 10036/RA/ /GLYPH::nnnn
”, where nnnn is the leaf element. Contact the Association
for Font Information Interchange, ISO/IEC 10036 Glyph Registrar, P .O. Box 33683, Northglenn,
Colorado 80233-0683 USA, for their most recent listing of the world's glyphs (for example,
International Glyph Register, Volume 1: Alphabetic Scripts and Graphic Symbols).
• An IBM Registered EBCDIC GCGID is a character string, using the uppercase letters A–Z and the
numbers 0–9. The IBM GCGIDs identified by this Graphic Character Identifier Type are expressed
in EBCDIC encoding, with the length of the GCGID string being 4 bytes or 8 bytes, depending on
the value of the Graphic Character Identifier Length parameter. IBM GCGIDs are published in
implementing product documentation, such as the Technical Reference for IBM Expanded Core
Fonts, S544-5228.
• A Font Specific ASCII Character Name is a character string, using the lowercase letters A–Z and
the numbers 0–9. The graphic character names identified by this Graphic Character Identifier
Type are expressed in ASCII encoding, with a variable length (the lesser of 128 or the value of the
Graphic Character Identifier Length parameter). These graphic character names might
be
registered by a single font or system supplier, or might be a collection of graphic character names
from multiple font or system suppliers. They must be unique within a font resource, but not
necessarily unique across multiple font resources.
• A Font Specific Binary Glyph Index is a binary string, representing an integer in the range of 1 to
(2**16)-1. The glyph index values identified by this Graphic Character Identifier Type may have a
length of one or two bytes, depending on the length defined by the Graphic Character Identifier
Length parameter. The assigned index number is font dependent, assigned by the creator of the
font, and is published in the implementing font product documentation. These glyph index values
might
be defined by a font or system supplier, or might be dynamically created by a document
processing system. They must be unique within a font resource, but not necessarily unique across
multiple font resources.
• A CMap Binary Code Point is a binary string, representing a character code as defined by an
Adobe Type 0 CMap file. The values identified by this Graphic Character Identifier Type may have
a length of 1, 2, or 4 bytes, depending on the length defined by the Graphic Character Identifier
Length parameter. The assigned values are defined by the creator of the Adobe Type 0 CMap file,
but is normally based on a national or internationally approved character coding standard, or a

## Page 128

106 FOCA Reference
widely used industry coding standard. Use of this parameter must identify the Adobe Type 0 CMap
file to which this method of identifying Graphic Character Identifiers is applicable.
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Graphic Character GID Length
The Graphic Character Identifier Length parameter specifies the length of the graphic character identifier. The
length is specified as an even number of bytes. Unless otherwise specified, the default length is eight bytes.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Description property list.
Invalid Coded Character
The Invalid Coded Character parameter specifies that the associated coded graphic character is not valid and
should not be used for processing. If this flag is off (0), the coded graphic character is valid. If this flag is on (1),
the coded graphic character is not valid.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
No Increment
The No Increment parameter specifies that the character increment for the corresponding coded character
should be ignored. If this flag is off (0), the coded character is incrementing. If this flag is on (1), the coded
character is non-incrementing.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.

## Page 129

FOCA Reference 107
No Presentation
The No Presentation parameter specifies that the corresponding coded character should be ignored. If this flag
is off (0), the coded character is presenting. If this flag is on (1), the coded character is non-presenting.
Parameter type = flag
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Number of Coded Graphic Characters Assigned
The Number of Coded Graphic Characters Assigned parameter specifies the number of graphic characters
(code points in one or more sections) that have been assigned in a code page.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Section Number
The Section Number parameter specifies the section number of a multibyte code page. A code page section
number is the first byte of a two-byte coded character. The number of valid sections depends on the encoding
scheme being used. The encoding scheme is defined in “Encoding Scheme” on page 104. If the encoding
scheme is double-byte EBCDIC,
values 1–64 and 255 are not permitted. See the Character Data
Representation Architecture Reference and Registry for other encoding scheme restrictions.
This parameter is used in both font resources and font references.
Parameter type = number
Synonyms = ward, section, coded-font section
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.

## Page 130

108 FOCA Reference
Space Character Code Point
The Space Character Code Point parameter specifies the code point assigned as the space character; for a
double-byte code page, the space character is identified with the space character section parameter followed
by the space character code point parameter.
The space character section and space character code point parameters can be assigned any value, but the
values normally used in FOCA fonts are shown in the following table:
Table 11. Space Character Code Point Used in FOCA Fonts
Basic Encoding Structure Single Byte Double Byte
IBM-PC Data X'2020' Not used
EBCDIC Presentation X'4040' X'4040'
UCS Presentation (Latin) Not applicable X'0020'
UCS Presentation
(ideographic, full-width space)
Not applicable X'3000'
The following characteristics apply to space characters:
• The increment for this character can be changed.
• The No Presentation flag in the Graphic Character Use Flags parameter equals 1.
Parameter type = number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.
Space Character Section Number
The Space Character Section Number parameter specifies the section number for the code point of the space
character.
Parameter type = number
Synonyms = space character ward number
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.

## Page 131

FOCA Reference 109
Unspecified Coded Character Identifier
The Unspecified Coded Character Identifier parameter specifies the registered identifier for the graphic
character information that is used whenever a font object does not contain the graphic character information
for the graphic character identifier associated with a code point, or whenever a code point has no associated
graphic character identifier. The default value for this parameter is the space character as specified by either
the Space Character Increment parameter or by the character positioning information for that character in the
font, depending on the implementation technique selected.
A coded character is specified by assigning a graphic character to a code point in the code page. For any code
point in a code page that does not have an explicitly specified graphic character identifier, the unspecified
coded character is implicitly specified. An unspecified coded character is not of necessity an invalid coded
character.
Parameter type = character string (named graphic character global identifier)
Transformation to Eastern Writing systems
This parameter is writing-system independent.
Transformation to ISO/IEC 9541 font architecture
No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-
property in the Font Resource property list.

## Page 132

110 FOCA Reference

## Page 133

Copyright © AFP Consortium 1998, 2015 111
