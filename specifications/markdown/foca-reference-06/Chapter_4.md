# Chapter 4. FOCA Overview

The Font Object Content Architecture (FOCA) supports presentation of character shapes by defining their characteristics, which include Font-Description information for identifying the characters, Font-Metric information for positioning the characters, and Character-Shape information for presenting the character images. Presenting a graphic character on a presentation surface requires that you communicate this information clearly to rotate and position characters correctly on the physical or logical page. For example, you can:

*   Rotate a physical page within a print system
*   Rotate and move a logical page to new locations on a physical page
*   Rotate and position character shapes anywhere within a logical page

**Note:** FOCA does not address the orientation of pages on devices, logical pages on a physical page, or lines of text on a logical page.

By defining FOCA parameters, you enable the system to separate font information from physical and logical page information as the system identifies, positions, and presents characters. This chapter gives an overview of FOCA by defining the terms you need when you use FOCA parameters, including terms for:

*   The character coordinate system, including units of measure and direction within the coordinate system
*   Graphic characters
*   Character-set metrics
*   Design metrics

The chapter concludes with some general recommendations when using FOCA to design fonts.

## Character Coordinate System

FOCA positions character shapes within an orthogonal character coordinate system, as shown in Figure 15.

**Figure 15. Character Coordinate System**

*   **Origin (Character Reference Point)**: The point where the axes intersect.
*   **X-Axis (Baseline)**: Positive X direction is the Escapement Direction.
*   **Y-Axis**: Positive Y direction is 90° counterclockwise or 270° clockwise from the positive X direction.

FOCA locates font or character measurements in this system as distances between points. All the positioning and rotating measurements of a character are relative to the origin; as a character is rotated, the character coordinate system does not rotate.

**Note:** The default escapement direction is left-to-right along the x-axis of the character coordinate system.

### Units of Measure

A font designer specifies the type of measurements used in defining a font as either **fixed units**, such as inches or centimeters, or **relative units**, which are dimensionless.

Fixed units apply only to a single or limited number of devices, while relative units allow a font to be used by multiple devices. If the designated font was designed using relative units, the system scales or proportions the font measurements to accommodate the presentation device.

#### Unit-Em

FOCA expresses relative units as a decimal fraction of a Unit-Em. A **Unit-Em** is a unit of one that corresponds to the height of the design space, which is also the nominal vertical font size (specified in the Nominal Vertical Font Size parameter).

**Figure 16. Relative Unit as the Unit-Em**

In order to find the fixed value of any of the relative metrics expressed as fractions of a Unit-Em, the fraction must be multiplied by the Unit-Em point value (customarily 1/72 inch).

#### Unit-Base

A Unit-Base specifies a code that represents the length of the measurement base.

**Table 10. Unit-Base Values**

| Value | Unit-Base |
| :--- | :--- |
| 0 | Ten inches |
| 1 | Ten centimeters |
| 2 | Relative units (measurement base = one Unit-Em) |
| 3–255 | Reserved |

#### Units Per Unit-Base

The Units per Unit-Base specifies the number of units in the measurement base. Allowable values are from 1 to 32,767. A value of 1000 is usually taken for the Units per Unit-Base multiplier for relative metrics.

#### Calculating the Units of Measure

Units of Measure is calculated using the following formula:
`Units of Measure = measurement base / Units per Unit-Base`

**Example 1 (Fixed)**: 240 pels per inch.
*   Unit-Base = 0 (10 inches)
*   Units per Unit-Base = 2400
*   `10 / 2400 = 1/240 inch`

**Example 2 (Relative)**: 20 divisions per Unit-Em.
*   Unit-Base = 2 (1 Unit-Em)
*   Units per Unit-Base = 20
*   `1 / 20 = 0.05 Unit-Em`

### Units of Direction

In FOCA, units of direction are specified as two integer values: degrees (0 to +359) and minutes (0 to +59). Increasing values indicate increasing clockwise directions.

**Figure 17. Directions**

## Character Concepts

This section defines the terms and concepts used to describe individual graphic characters in FOCA.

### Character Boxes

FOCA uses a concept in which each character shape is defined to be within a **character box**, which is a conceptual rectangle. Boxes can be **bounded** (no extra space, just touching the shape) or **unbounded** (extra space). FOCA typically uses bounded boxes.

**Figure 18. Bounded Character Box for the Latin Letter ‘h’**

### Character Baseline

All characters in a font are aligned on an imaginary line called the **character baseline**, which corresponds to the x-axis of the character coordinate system.

**Figure 19. Character Baseline**

### Character Reference Point

The **character reference point** corresponds to the origin (0,0) of the character coordinate system. It coincides with the presentation position.

**Figure 21. Character Reference Point**

### Character Escapement Point

The **character escapement point** is the point where the next character reference point is usually positioned.

**Figure 22. Character Escapement Point**

### A-space

**A-space** is the distance from the character reference point to the least positive character coordinate system x-axis value of the character shape.

*   **Positive**: Reference point is before the leftmost edge of the box.
*   **Zero**: Reference point coincides with the leftmost edge.
*   **Negative**: Implementation technique for kerning (left kerning).

**Figure 23. A-space**

### B-space

**B-space** is the distance between the character coordinate system x-axis values of the two extremities of a character shape (the width of a bounded box).

**Figure 24. B-space**

### C-space

**C-space** is the distance from the character’s most positive x-axis coordinate value to the character escapement point.

*   **Positive**: Escapement point is after the right-hand edge.
*   **Zero**: Escapement point coincides with the right-hand edge.
*   **Negative**: Implementation technique for kerning (right kerning).

**Figure 25. C-space**

### Character Increment

The **character increment** is the algebraic sum of A-space, B-space, and C-space. It is the distance from the character reference point to the character escapement point.

**Figure 26. Character Increment**

### Kerning

**Kerning** reduces the spacing between adjacent characters so they overlap. This is done by making A-space or C-space negative.

**Figure 27. An Example of Kerning**

**Pair Kerning** allows adjusting the increment between specific pairs of characters.

### Ascender Height

**Ascender height** is the distance from the character baseline to the top of the character box (most positive y-axis value).

**Figure 28. Ascender Height**

### Descender Depth

**Descender depth** is the distance from the character baseline to the bottom of a character box (most negative y-axis value).

**Figure 29. Descender Depth**

### Baseline Extent

**Baseline extent** is the space parallel to the character baseline that can be used to place characters (the y-axis height of the box).

**Figure 30. Baseline Extent: Subscript Character**
**Figure 31. Baseline Extent: Superscript Character**
**Figure 32. Baseline Extent: Character on the Baseline**

### Baseline Offset

The **baseline offset** is the perpendicular distance from the character baseline to the top of a character box.

### Slope

The **slope** is the posture (incline) of the main strokes, measured clockwise from the vertical. Upright is usually 0°; italics is usually 17.5°.

**Figure 33. Slope 0°**
**Figure 34. Slope 17.5°**

## Font Concepts

This section defines terms that apply to the entire font.

### Vertical Size

The **vertical size** (also known as body size, point size, em-height) represents the nominal baseline-to-baseline increment, including the designer's recommendation for internal leading.

**Figure 35. An Illustration of Vertical Size and Internal Leading**

### Horizontal Font Size

The size of a uniformly-spaced font, measured parallel to the baseline (also known as character width or character pitch).

### Cap-M Height

The average height of the uppercase characters, usually the height of the uppercase letter M.

### X-Height

The nominal height above the baseline of lowercase characters (ignoring ascenders), usually the height of 'x'.

### Internal Leading

The total amount of space above and below text character shapes providing aesthetically pleasing interline spacing.
`Internal Leading = Vertical Font Size - Baseline Extent (for text characters)`

### External Leading

Additional white space that can be added to interline spacing without degrading appearance.

**Figure 36. An Illustration of External Leading**

### Maximum Ascender Height

The maximum height attained by any graphic character in a font from the baseline to the top of its box.

### Maximum Descender Depth

The maximum depth attained by any graphic character in a font from the baseline to the bottom of its box.

### Maximum Baseline Extent

The sum of the most positive baseline offset and the most positive descender depth of any character in the font.

### Superscripts and Subscripts

Designers may provide recommended size and position offsets for superscript and subscript characters.

### Overscores, Throughscores, and Underscores

*   **Overscore**: Bar over the character.
*   **Throughscore**: Bar through the character.
*   **Underscore**: Bar under the character.

Offsets are specified from the character baseline to the top of the score stroke.

**Figure 37. Overscore, Throughscore, and Underscore**

## Non-Latin Language Support

FOCA supports multidirectional text and multi-byte encoding.

### Character Rotation

The principle distinctive characteristic is top-to-bottom or right-to-left direction. FOCA uses multiple character rotations (0°, 90°, 180°, 270°) in a single font resource.

*   **Horizontal writing**: 0° and 180° rotations.
*   **Vertical writing**: 90° and 270° rotations.

**Figures 38–41. 0°, 90°, 180°, 270° Rotations**

### Rotated Baseline and Character Boxes

For traditional Kanji, the baseline is rotated 90°, and characters are rotated 270°.

**Figure 42. Rotating Baseline and Characters**

### Eastern Writing Systems

In systems like Japanese Kanji, characters can be horizontal (0°) or vertical (270°).

**Figure 43. Two Rotations of a Kanji Character**
**Figure 44. 0° Character Rotation for Horizontal Writing**
**Figure 45. 270° Character Rotation for Vertical Writing**

### Middle Eastern Writing Systems

Hebrew is written right-to-left. Hebrew code pages are usually bilingual Latin/Hebrew. FOCA specifies rules for consistent presentation of mixed Latin and Hebrew text.

## Non-AFP Architecture Support

### ISO 9541-1 Font Architecture

ISO describes metrics in terms of X,Y positions in a glyph coordinate system, while FOCA uses distances/offsets. ISO allows arbitrary positioning points; FOCA fixes them at the origin. They are equivalent but require transformation.

**Figure 48. FOCA and ISO Coordinate System Relationship**

#### Global Naming

ISO uses a hierarchical naming tree (e.g., `ISO / ICD / IBM / IBM-CS / FONT / FGID / 12`). FOCA names can be mapped into this structure.
