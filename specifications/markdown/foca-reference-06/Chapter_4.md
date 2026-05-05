Chapter 4. FOCA Overview
The Font Object Content Architecture (FOCA) supports presentation of character shapes by defining their
characteristics, which include Font-Description information for identifying the characters, Font-Metric
information for positioning the characters, and Character-Shape information for presenting the character
images. Presenting a graphic character on a presentation surface requires that you communicate this
information clearly to rotate and position characters correctly on the physical or logical page. For example, you
can:
• Rotate a physical page within a print system
• Rotate and move a logical page to new locations on a physical page
• Rotate and position character shapes anywhere within a logical page
Note: FOCA does not address the orientation of pages on devices, logical pages on a physical page, or lines
of text on a logical page.
By defining FOCA parameters, you enable the system to separate font information from physical and logical
page information as the system identifies, positions, and presents characters. This chapter gives an overview
of FOCA by defining the terms you need when you use FOCA parameters, including terms for:
• The character coordinate system, including units of measure and direction within the coordinate system
• Graphic characters
• Character-set metrics
• Design metrics
The chapter concludes with some general recommendations when using FOCA to design fonts.
Character Coordinate System
FOCA positions character shapes within an orthogonal character coordinate system, as shown in Figure 15.
Figure 15. Character Coordinate System
Positive
Y Direction
Origin (Character Reference Point)
Escapement Direction
X-Axis (Baseline) Positive X Direction
Y -Axis
FOCA locates font or character measurements in this system as distances between points. The point where
the axes intersect is called the origin or character reference point (see “Character Reference Point” on page 37
for a specific example). All the positioning and rotating measurements of a character are relative to that point;
that is, as a character is rotated, the character coordinate system does not rotate.
The x-axis of the character coordinate system is coincident with the character baseline (see “Character
Baseline” on page 37). Positive x-axis direction is the escapement direction, as shown in Figure 15. Positive y-
axis direction is 90°; counterclockwise or 270° clockwise from the positive x-axis direction.
Note: The default escapement direction is left-to-right along the x-axis of the character coordinate system.

## Page 56

34 FOCA Reference
The following sections describe how you specify measurement and direction units within the character
coordinate system.
Units of Measure
A font designer specifies the type of measurements used in defining a font as either fixed units, such as inches
or centimeters, or relative units, which are dimensionless.
Fixed units apply only to a single or limited number of devices, while relative units allow a font to be used by
multiple devices. For example, a 240-pel per inch device requires 30 pels to represent 1/8 inch, but a 300-pel
per inch device cannot represent 1/8 inch with a whole number of pels (300/8 = 37.5). When using fixed units
for a presentation, the system evaluates the following:
• Can the specified length unit be accepted?
• Can the specified length unit be converted for use?
• Should some error-recovery action be taken?
If the fixed units for a designated font cannot be accepted or converted, the system might be unable to supply
an acceptable font for the selected presentation device, and it might take an error-recovery action. If the
designated font was designed using relative units, the system scales or proportions the font measurements to
accommodate the presentation device.
Unit-Em
FOCA expresses relative units as a decimal fraction of a Unit-Em. A Unit-Em is a unit of one that corresponds
to the height of the design space, which is also the nominal vertical font size. You specify this measurement in
the Nominal Vertical Font Size parameter. Figure 16 shows a relative unit expressed as the Unit-Em. See
“Nominal Vertical Font Size” on page 68 for information about the Nominal Vertical Font Size parameter.
Figure 16. Relative Unit as the Unit-Em
Grid Density = 20 x 20
Unit-Em
Height
(1 Unit)
0.6 Unit
For practical purposes, the Unit-Em must be expressed in some fixed unit of measure. In the printing industry,
the point, 1/72 inch, is customarily used. In order to find the fixed value of any of the relative metrics expressed
as fractions of a Unit-Em, the fraction must be multiplied by the Unit-Em point value.

## Page 57

FOCA Reference 35
Unit-Base
A Unit-Base specifies a one-byte code that represents the length of the measurement base. Allowable code
values for the Unit-Base are from 0 to 2 and represent the following:
Table 10. Unit-Base Values
Values Unit-Base
0
1
2
3-255
T en inches
T en centimeters
Relative units (measurement base = one Unit-Em)
Reserved
Units Per Unit-Base
The Units per Unit-Base specifies the number of units in the measurement base. Allowable values for the Units
per Unit-Base are from 1 to 32,767.
In order to avoid the use of decimal fractions, the Units per Unit-Base is customarily multiplied by the Unit-Em
value in points where the point value of the Unit-Em is the Unit-Base. The relative metrics must then also be
multiplied by the same factor. Furthermore, a value of 1000 is usually taken for the Units per Unit-Base
multiplier. For example, a Unit-Em value of 20 points becomes 20,000 units when multiplied by a Units per
Unit-Base value of 1000. A character height of 0.6 Unit-Em, as in Figure 16 on page 34, then becomes 12,000
units (0.6 * 20 * 1000).
Calculating the Units of Measure
Units of Measure is calculated by dividing the value of the measurement base (as specified by the Unit-Base
parameter) by the Units per Unit-Base value, according to the following formula:
Units of Measure = measurement base/Units per Unit-Base
Resolution is the reciprocal of Units of Measure. The following examples show how different resolutions can be
specified.
Example 1: An example of fixed-value measurements.
A resolution of 240 pels per inch is specified as:
Unit-Base = 0 (measurement base = 10 inches)
Units per Unit-Base = 2400
therefore:
Units of Measure = 10 inch/2400 = 1/240 inch
Resolution = 1/Unit of Measure = 240 units per inch
Example 2: An example of relative-value measurements.
A resolution of 20 divisions per Unit-Em is specified as:
Unit-Base = 2 (measurement base = 1 Unit-Em)
Units per Unit-Base = 20
therefore:
Units of Measure = measurement base/Units per Unit-Base
= 1(Unit-Em)/20
Resolution = 1/Unit of Measure = 20 Units per Unit-Em

## Page 58

36 FOCA Reference
With relative units of measure, font dimensions can be scaled to any desired size by multiplying the specified
dimension by the desired vertical font size. For example, if the height of a character image is expressed as 12
relative units when the Unit-Em value is 20 points, the character height as a relative value is 0.6 (12/20 = 0.6).
At any other Unit-Em value, such as 12 points, the character height is the relative value multiplied by the Unit-
Em value: 0.6 x 12 = 7.2 points.
Units of Direction
In FOCA, units of direction, as shown in Figure 17, are specified as two integer values, which represent
degrees and minutes:
Degrees Integers from 0 to +359.
Minutes Integers from 0 to +59.
Increasing values, as shown in Figure 17, indicate increasing clockwise directions.
Figure 17. Directions
Increasing
Character Concepts
This section defines the terms and concepts used to describe individual graphic characters in FOCA.
Character Boxes
FOCA uses a concept in which each character shape is defined to be within a character box, which is a
conceptual rectangle. When character shapes are presented, the character boxes are positioned and can be
rotated.
A character box is a conceptual rectangle having two sides parallel to the character baseline and two sides
perpendicular to the character baseline. A character box circumscribes a graphic character. We differentiate
between boxes having no extra space surrounding the character, and boxes having extra space, by the terms
bounded and unbounded, respectively. Figure 18 shows a bounded character box.
Figure 18. Bounded Character Box for the Latin Letter ‘h’
Character Box

## Page 59

FOCA Reference 37
In this architecture, unless noted otherwise, the character boxes are bounded-boxes. The four sides of the box
just touch the character shape.
Character boxes are positioned and rotated using the Font-Metric information for positioning provided in “Font-
Metric Parameters” on page 74.
Character Baseline
All characters in a font are aligned on an imaginary line called the character baseline. The character baseline
corresponds to the x-axis of the character coordinate system. The use of the character baseline ensures that
successive characters—even from different fonts—are properly aligned. Figure 19 shows the character
baseline.
Figure 19. Character Baseline
Character Baseline
If character shapes are presented using pel addressing, all character measurements represent distances
between the pels. No pels fall on the baseline. Figure 20 shows a figure presented in pels and its relationship
to the baseline.
Figure 20. A Character Shape Presented in Pels
Character Reference Point
The character reference point as shown in Figure 21, corresponds to the origin of the character coordinate
system. It coincides with the presentation position when the character is presented on the presentation
surface.
Figure 21. Character Reference Point
Reference
Point

## Page 60

38 FOCA Reference
Character Escapement Point
The character escapement point, as shown in Figure 22, is the point where the next character reference point
is usually positioned.
Figure 22. Character Escapement Point
Reference
Point
Character
Escapement
Point
A-space
A-space, as shown in Figure 23, is the distance from the character reference point to the least positive
character coordinate system x-axis value of the character shape.
Figure 23. A-space
Reference
Point
A
Space
Character
Escapement
Point
The baseline is coincident with the character coordinate system's x-axis. A-space can be positive, zero, or
negative:
• A positive value specifies that the character reference point lies before the leftmost edge of a character box.
• A zero value specifies that the reference point coincides with the leftmost edge of the character box.
• A negative value specifies that the character reference point lies after the leftmost edge of a character box.
Using negative A-space is a technique to implement kerning (kerning is described in “Kerning” on page 40).

## Page 61

FOCA Reference 39
B-space
B-space, as shown in Figure 24, is the distance between the character coordinate system x-axis values of the
two extremities of a character shape.
Figure 24. B-space
Reference
Point
Character
Escapement
Point
B
Space
For a bounded character box, the B-space is equal to the width of the character box. Negative or zero values of
B-space have no meaning.
C-space
C-space, as shown in Figure 25, is the distance, measured in the inline (positive x-axis) direction, from the
character’s most positive x-axis coordinate value in the character coordinate system, to the character
escapement point.
Figure 25. C-space
Reference
Point
Character
Escapement
Point
C
Space
C-space can be positive, zero, or negative:
• A positive value specifies that the escapement point lies after the right-hand edge of a character box.
• A zero value indicates that the escapement point coincides with the right-hand edge of a character box.
• A negative value specifies that the escapement point lies before the right-hand edge of a character box.
A technique to implement kerning is the use of negative C-space.

## Page 62

40 FOCA Reference
Character Increment
The character increment is the distance from the character reference point to the character escapement point.
Character increment, as shown in Figure 26, is the sum of the A-space, the B-space, and the C-space.
Figure 26. Character Increment
Reference
Point
Character
Escapement
Point
A
Space
B
Space
Character Increment
C
Space
Kerning
The spacing between adjacent characters can be reduced so that the characters overlap, which is called
kerning. Kerning is done by making the A-space or C-space negative. Figure 27 illustrates the kerning of the
characters a and f.
Figure 27. An Example of Kerning
Character
Baseline
B-Space
Pair Kerning
The kerning of two characters can be varied by adjusting the increment between them. Using this adjustment,
the two characters can be positioned closer together, further apart, or made to overlay one another.
Pair kerning can be implemented by specifying pairs of characters to be kerned and by specifying the
increment between them, depending on the tightness or looseness of the fit desired. See Chapter 5, “Kerning
Pair Character 1” on page 76, “Kerning Pair Character 2” on page 77, and “Kerning Pair X-Adjust” on page 77.

## Page 63

FOCA Reference 41
Ascender Height
Ascender height is the distance from the character baseline to the top of the character box. A negative
ascender height signifies that all of the graphic character is below the character baseline. Figure 28 shows the
ascender height.
Figure 28. Ascender Height
Ascender
Height
Character
Baseline
For a character rotation other than 0°, “ascender height” loses its meaning when the character is lying on its
side or is upside down with respect to normal viewing orientation. For the general case, Ascender Height is the
character's most positive y-axis value.
For bounded character boxes, for a given character having an ascender, ascender height and baseline offset
are equal.
Descender Depth
Descender depth is the distance from the character baseline to the bottom of a character box. A negative
descender depth signifies that all of the graphic character is above the character baseline. Figure 29 shows the
descender depth.
Figure 29. Descender Depth
Descender
Depth
Character
Baseline
Baseline Extent
Baseline extent is the space parallel to the character baseline that can be used to place characters. Figure 30
shows the baseline extent with a subscript character, Figure 31 on page 42 with a superscript character, and
Figure 32 on page 42 with a character on the baseline.
Note: A negative value for baseline extent is treated as zero.
Figure 30. Baseline Extent: Subscript Character
Baseline
Extent
Baseline

## Page 64

42 FOCA Reference
Figure 31. Baseline Extent: Superscript Character
Baseline
Extent
Baseline
Figure 32. Baseline Extent: Character on the Baseline
Baseline
Extent Baseline
Baseline Offset
The baseline offset is the perpendicular distance from the character baseline to the top of a character box. This
value is positive if any part of the character box is above the character baseline, and it is negative if all of the
character box is below the character baseline. If the baseline offset is zero, the top of the character box is at
the baseline.
Slope
The slope is the posture (or incline) of the main strokes in the graphic characters in a font and is measured
clockwise from the vertical. Slope is specified in degrees by a font designer. Figure 33 shows the letter f with 0°
slope.
Figure 33. Slope 0°
If the characters are upright or if they have a slight forward slant, the slope is small. If the characters have a
backward slant, the slope is large. Slope is usually 0° for upright fonts and 17.5° for italic fonts. Figure 34
shows the letter f with 17.5° slope.
Figure 34. Slope 17.5°


## Page 65

FOCA Reference 43
Font Concepts
This section defines the terms and concepts used to describe fonts as they apply to all the characters in an
entire font in FOCA.
Vertical Size
The size of a font can be characterized by the vertical size of its characters. Vertical size represents the
nominal baseline-to-baseline increment that includes the vertical size of the set of all characters and the
designer's recommendation for the internal leading (space above or below the characters). Vertical size is
illustrated in Figure 35.
Vertical size is also known as body size, point size, em-height.
Figure 35. An Illustration of Vertical Size and Internal Leading
Internal Leading
V ertical
Size
Baseline
To
Baseline
Increment
Horizontal Font Size
The size of a uniformly-spaced font can be characterized by the increment of its characters. Horizontal Font
size is measured parallel to the baseline when character rotation is 0°.
Horizontal font size is also known as character width and character pitch.
Cap-M Height
Cap-M height is the average height of the uppercase characters in a font. This value is specified by the
designer of a font and is usually the height of the uppercase M.
X-Height
X-height is the nominal height above the baseline (ignoring the ascender) of the lowercase characters in a font
and is usually the height of the lowercase letter x. The value of X-height is specified by a font designer.

## Page 66

44 FOCA Reference
Internal Leading
Internal leading is the total amount of space above and below the text character shapes, which provides an
aesthetically pleasing interline spacing. The value of this parameter usually equals the difference between the
vertical font size and the baseline extent for text characters in the font. Internal leading is illustrated in Figure
35 on page 43.
Note: Special characters that extend from baseline to baseline (the backslash, the forward slash, and
characters used for drawing boxes) are not included in this calculation.
Fonts are normally designed with some nominal amount of white space above or below the character images.
When placing more text on a page by using less space between lines, this value can be used to determine how
little space between lines can be used without overwriting lines of text.
External Leading
External leading is the amount of white space—in addition to the internal leading—that can be added to the
interline spacing without degrading the aesthetic appearance of a font. This value is usually specified by a font
designer. External leading is illustrated in Figure 36.
Figure 36. An Illustration of External Leading
External Leading
Internal Leading
V ertical Size
Baseline To
Baseline
Increment
text on a
printed
Maximum Ascender Height
The maximum ascender height is the maximum height attained by any graphic character in a font from the
character baseline to the top of the character box.
A value for the maximum ascender height is specified for each supported rotation of the character. This value
can be positive, zero, or negative.
• A positive value specifies that some of the graphic characters in a font extend above the character baseline.
• A value of zero specifies that the top of at least one character box is at the baseline, and no character box
extends above the baseline.
• A negative value specifies that all the graphic characters in a font lie completely below the character
baseline, as in the case where the characters are subscripts.

## Page 67

FOCA Reference 45
Maximum Descender Depth
The maximum descender depth is the maximum depth attained by any graphic character in a font from the
character baseline to the bottom of the character box.
The value for the maximum descender depth is specified for each supported rotation of the character. This
value can be positive, zero, or negative.
• A positive value specifies that some of the graphic characters in a font extend below the baseline.
• A value of zero specifies that the bottom of at least one character box is at the baseline, and no character
box extends below the baseline.
• A negative value specifies that all the graphic characters in a font lie completely above the character
baseline, as in the case where the graphic characters are superscripts.
Maximum Baseline Extent
Maximum baseline extent is the sum of the most positive baseline offset of any individual character in the font
and the most positive descender depth of any individual character in the font.
Note: A negative value for maximum baseline extent is treated as zero.
Superscripts and Subscripts
Font designers might recommend the size and position of the subscript and superscript characters in a font.
If a font does not include all of the required subscript and superscript characters for an application, an
application process can use the information to create the required characters in the size and position
recommended by the font designer. If the characters of the font have a slope (italic or oblique characters), the
reference point for superscript and subscript characters is normally adjusted to compensate for the angle of the
slope.

## Page 68

46 FOCA Reference
Overscores, Throughscores, and Underscores
When graphic characters are presented, a horizontal bar can also be presented over, through, or under the
graphic character. Bars over the character are called overscores, bars through the character are called
throughscores, bars under the character are called underscores. Figure 37 shows an overscore, a
throughscore, and an underscore.
Figure 37. Overscore, Throughscore, and Underscore
Overscore
Throughscore
Underscore
Recommendations for Overscores and Throughscores
Designer recommendations for overscores and throughscores in a font can be specified as follows:
• As a displacement of the top of the score stroke-width from the character baseline:
– A positive value specifies that the top of the overscore or throughscore stroke-width is positioned above
the character baseline.
– A value of zero specifies that the top of the overscore or throughscore stroke-width is coincident with the
character baseline.
– A negative value specifies that the top of the overscore or throughscore stroke-width is positioned below
the character baseline.
• As the width (thickness) of each overscore or throughscore stroke for implementation by the processing
system
Recommendations for Underscores
Designer recommendations for underscores in a font can be specified as follows:
• As a displacement of the top of the score stroke-width from the character baseline (see “Underscore
Position” on page 88):
– A positive value specifies that the top of the underscore stroke-width is positioned below the character
baseline.
– A value of zero specifies that the top of the stroke-width is coincident with the character baseline.
– A negative value specifies that the top of the underscore stroke-width is positioned above the character
baseline.
• As the width (thickness) of each underscore for implementation by the processing system

## Page 69

FOCA Reference 47
Non-Latin Language Support
This font architecture fully supports all requirements for national language support that were known while the
architecture was being developed , including multidirectional text and multi-byte document encoding. It is,
however, the responsibility of implementing products to provide the necessary collections of font information
required to support the national language variations required by their product. That is, the font architecture
provides for the definition of metric information for support of multiple character rotations, but the implementing
product is responsible for providing the character positioning information for those rotations.
The following sections describe how the concepts of the architecture, which are most often described in terms
of the left-to-right Latin writing system, are applied to non-Latin writing systems.
Character Rotation
The principle distinctive characteristic of non-Latin writing systems is presentation of text in a top-to-bottom
direction or in a right-to-left direction. FOCA addresses this characteristic by permitting the font designer to
provide metric information for multiple character rotations in a single font resource. The 0° and 180° character
rotations are normally used for horizontal writing and the 90° and 270° character rotations are normally used
for vertical writing.
Character rotation is specified in degrees and minutes. A font designer must specify at least one rotation for
the characters in a font and—depending on product and user requirements—more angles of rotation might be
necessary.
Positioning values might change for each rotation, which is shown in Figure 38 through Figure 41 on page 48.
The A-space, B-space, and C-space for a character vary for each rotation. These font metrics are specified by
the font designer.
Figure 38. 0° Rotation
A B C
Figure 39. 90° Rotation
A B C

## Page 70

48 FOCA Reference
Figure 40. 180° Rotation
A B C
Figure 41. 270° Rotation
A B C
Rotated Baseline and Character Boxes
If text is to be read top-to-bottom as in traditional Kanji, the baseline is rotated 90°, and the characters are
presented rotated 270°. The presentation of baseline rotated 90°, and characters boxes rotated 270° is
illustrated in Figure 42.
Note: Rotation of characters relative to the baseline is defined by FOCA. Rotation of the baseline is defined
outside of FOCA.
Figure 42. Rotating Baseline and Characters. (Baseline rotated 90° and Characters rotated 270°)
Character Rotation 0
Baseline Rotation 0
o
o
Character Rotation 270
Baseline Rotation 0
o
o
Character Rotation 270
Baseline Rotation 90
o
o

## Page 71

FOCA Reference 49
Eastern Writing Systems
In the Eastern writing systems, such as Japanese Kanji, graphic characters can be presented in either the left-
to-right direction (using font metric information for characters rotated 0°, see Figure 43) or the top-to-bottom
direction (using font metric information for characters rotated 270°, see Figure 43). Thus, font designers must
either specify metric information for two character rotations in one font resource or create two font resources,
one for each character rotation.
Figure 43. Two Rotations of a Kanji Character
Ascender
Descender
Character
Baseline
Character Rotation 0
o
Character Rotation 270
o
Ascender
Character
Baseline
A B C
A B C
The following diagrams illustrate in a general way how the character and font concepts of this chapter apply to
Japanese Kanji. Specific details are provided with each parameter definition of Chapter 5, “FOCA
Parameters”, on page 55.
Figure 44. 0° Character Rotation for Horizontal Writing
Baseline
Progression
A A
C
Internal Leading for 0 Rotation
o
V ertical
Font Size
Character
BaselineHorizontal
Font Size
B CB
External Leading for 0 Rotation
o

## Page 72

50 FOCA Reference
Figure 45. 270° Character Rotation for Vertical Writing
Baseline
Progression
A
A
B
C
External Leading for 270 Rotation
o
Internal Leading for 270 Rotation
o
Character
Baseline
C
B
V ertical
Font Size
Horizontal
Font Size
The terminology for overscore, underscore, and throughscore were defined with Latin text in mind, but apply to
Eastern text as shown in Figure 46.
Figure 46. Example of Score Positioning
Overscore
Underscore Underscore
Throughscore
Overscore
Character
Baseline
Character
Baseline
Baseline
Progression
Baseline
Progression
Character
Box Width
Reference
Point
Reference
Point
Ascender
Baseline OffsetDescender
Throughscore

## Page 73

FOCA Reference 51
Middle Eastern Writing Systems
The Hebrew alphabet consists of 27 characters. Five of these characters are final forms but in a font are
treated like any other character. Uppercase and lowercase letters do not exist in Hebrew.
Figure 47. Example of Hebrew Text
Hebrew sign
Hebrew is written from right-to-left but the design of each font is performed with the same rules as for Latin
fonts. Slanted fonts are permitted to both sides. Usually the oblique or italic fonts are slanted to the right in
order to avoid conflict with the italic Latin fonts. For text containing only Hebrew fonts the correct typographic
design should be an inclination to the left, according to the reading direction.
Kerning could be used in some cases but it is not necessary in order to obtain a correct font.
The Hebrew code pages are bilingual Latin/Hebrew code pages to permit mixed text in the same environment.
In a normal business environment the Hebrew text will generally be mixed with Latin text. The numbers and
punctuation marks are the same as in the Latin font.
For this reason the Hebrew fonts should match the following general rules in order to provide consistent page
presentation.
• Hebrew characters can be divided into two major categories, wide and thin characters. The wide Hebrew
characters (for example, alef and shin) should have similar width values as the wide Latin characters (for
example, H and W) contained in the same bilingual font.
• The height of the Hebrew characters should be approximately 1/3 lower than the difference between the
uppercase and lowercase for Latin. The result should be a font slightly lower than the uppercase Latin font.
• The space character used for Hebrew should be the same as for the matching Latin font.
• Hebrew characters with descenders will use all the permitted descender space for better readability.
• The typical vertical stem width of the Latin characters in the same font should equal the typical horizontal
stem of the Hebrew characters. Modern Hebrew sans-serif fonts can have the same width for the horizontal
and vertical parts of the characters.
• In case of a low resolution device, the Hebrew fonts are usually designed with the same height as the Latin
uppercase in order to improve readability.
Non-AFP Architecture Support
FOCA is only one of many font architectures that exist in the world of information processing. Many companies
have their own architecture for internal consumption, while others use industry, national, or international
standard architectures. The following sections, along with specific details provided in association with each of
the FOCA parameters in Chapter 5, “FOCA Parameters”, on page 55, provide the information necessary to
transform FOCA information between other widely accepted font architectures.
ISO 9541-1 Font Architecture
The ISO/IEC 9541-1 Information technology—Font information interchange: Part 1: Architecture document
provides the semantic definition of a comprehensive set of properties (parameters) for use in creating font
resources or font references for interchange at the global level. The specific syntactic representation of that

## Page 74

52 FOCA Reference
information is defined in ISO/IEC 9541-2 Information technology—Font information interchange: Part 2:
Interchange formats.
The following sections describe the general terminology and coordinate system differences between the ISO
and AFP font architectures, and the global naming concepts used in the ISO standard to assure globally
unique naming of information technology objects (for example, typeface names, family names, and resource
names).
Coordinate System
ISO/IEC 9541-1 describes font resource information in terms of x and y positions within a Cartesian glyph
coordinate system. This method of describing font metric information differs from the FOCA use of distances or
offsets in a similar Cartesian coordinate system. In addition, the ISO standard permits specification of the glyph
positioning point anywhere in the glyph coordinate system, while FOCA requires the positioning point to remain
fixed at the origin. This difference permits the ISO glyphs to always appear upright in the glyph coordinate for
different writing modes, while the AFP
method requires rotation of the character in the coordinate system to
achieve the same result. The two systems are equivalent, but require transformation of metric information
when going from one system to the other.
Figure 48 illustrates in a general way how the FOCA character coordinate system and the ISO glyph
coordinate system relate to each other, for left-to-right and top-to-bottom writing systems. Specific details for
transforming from one system to the other are provided with each applicable parameter definition of Chapter 5,
“FOCA Parameters”, on page 55.
Figure 48. FOCA and ISO Coordinate System Relationship
FOCA Character Coordinate System ISO Glyph Coordinate System
Reference
Point
Character
Escapement
Point
Character
Escapement
Point
Reference
Point
Escapement
Point
Position
Point
Escapement
Point
Position
Point
Ascender
Height
Decender
Depth
Ascender
Height
Decender
Depth
A B C
A B C
Min X
Min Y
Max X
Max Y
Min X
Min Y
Max X
Max Y
X
Y
X
Y
X
Y
X
Y

## Page 75

FOCA Reference 53
Global Naming
ISO uses a hierachically structured naming tree, rooted at the international level, for naming information
objects (see Figure 49). The organization of the tree and its sublevel branches permit organizations to graft in
existing internally defined names without serious incontinuity or redefinition.
Figure 49. ISO Hierarchical Naming Tree
ISO
Registration
Authority
Member
Body Standards ICD
United States IBM
Government Organization IBM-CS
ABC Inc.
FGID
12
ISO / ICD / IBM / IBM-CS / FONT / FGID / 12
FONT
The structure permits organizations to define their own local names for entities and to then use those names in
progressively broader environments by prepending owning branches of the naming tree. Thus, an AFP Font
Typeface Global ID might be identified by only the local identifier 12 if the context of its use is known, or it might
be identified by the name FONT/FGID/12 within an IBM operating environment if the context of its use is not
known, or it might be identified by the name ISO/ICD/IBM/IBM-CS/FONT
/FGID/12 within the global
environment if the context of its use is not known.
ISO/IEC 9541 structured names for the representation of font information is in two forms: ASN.1 numeric
identifiers and SGML clear text names. The IBM FONTS structured-name prefix using ASN.1 is 1-3-18-0-1.
Using SGML it is ICD0018/IBM/ /IBM-CS/FONTS.

## Page 76

54 FOCA Reference

## Page 77

Copyright © AFP Consortium 1998, 2015 55
