# Chapter 5. FOCA Parameters

Digital data processing requires that you create data structures using a defined set of parameters and parameter values organized in a specific format. FOCA provides a precise set of parameter definitions and architected values or ranges of values you can use to create font resources and font references.

This chapter describes the conventions for defining FOCA parameters. Then, it defines the available FOCA parameters, dividing them into the following five categories:
*   Font-description parameters
*   Font-metric parameters
*   Character-metric parameters
*   Character-shape parameters
*   Character-mapping parameters

## Defining FOCA Parameters

This section describes three parameter formats, the parameter types available for each format, and byte and bit numbering conventions.

### Parameter Formats

FOCA supports a variety of parameter formats, that is, types of syntax. The choice of format depends on the environment where you want to use the font resource. The following are three common formats supported by FOCA. See Chapter 6, “Font Interchange Formats”, for more detailed information about the formatting standards necessary to implement FOCA.

*   **Fixed-Format**: A fixed-format parameter is defined by its position and length within a string of fixed-format parameters. The variable name and its associated meaning is implied by the position of the parameter in the string.
*   **Self-Identifying**: A self-identifying parameter has a set of fields that identify the parameter, specify its length, and specify its values.
*   **Clear-Text**: A clear-text parameter has two fields, which are separated by a delimiter. The first field contains a character string that is the name of the parameter and the second field contains a character string that represents the value of the parameter.

### Parameter Types

You define a FOCA parameter by identifying it and assigning it a value. The remaining sections of this chapter list the names of the parameters and show **Parameter type =** as requiring a value in the form of one of the following data types:

*   **Character string**: A character string parameter value is any user, system, or font-supplier defined name. It is composed of alphanumeric characters and can be any specified length. Unless otherwise specified, the default set is IBM Graphic Character Set 103.
*   **Flag**: A flag parameter value indicates a binary flag (1 or 0).
*   **Number**: A number parameter value uses real numbers or integer numbers to represent count or magnitude.
*   **Code**: A code parameter value is a collection of architected choices (integers, letters, or acronyms).
*   **Undefined**: An undefined parameter value is not defined by the architecture.

### Byte and Bit Numbering

*   **Byte numbering**: During both transmission and storage, data is viewed as a long horizontal string. Each byte is identified by a positive integer (offset) starting with 0.
*   **Bit numbering**: Bytes are divided into eight bits, numbered left to right from 0 to 7 (big-endian). Bit 0 is the most significant bit.

---

## Font-Description Parameters

This section lists and describes the descriptive parameters required to identify a font, select the appropriate font for formatting, and locate the specified font for presentation.

### Average Weighted Escapement
The Average Weighted Escapement parameter specifies the arithmetic average of the escapement of all, or some subset of, the characters in a font. The escapement value for each character is weighted by its anticipated frequency of use.

*   **Parameter type =** number
*   **Synonyms =** average character width
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-property in the Modal Properties property list.

### Cap-M Height
The Cap-M Height parameter specifies the height above the baseline for uppercase character shapes. Cap-M height is the nominal height of the uppercase characters and is usually equal to the height of the uppercase letter M.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter should be set to the character box height for Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to `capheight` (Capitol Height). It is expressed as a relative rational number.

### Character Rotation
The Character Rotation parameter specifies the rotation of the character box relative to the character baseline. Refer to “Units of Direction” for an explanation of character rotation.

*   **Parameter type =** number
*   **Synonyms =** font character rotation
*   **Transformation to Eastern Writing systems:** This parameter should be set to 0° or 270° for horizontal or vertical writing respectively.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to `nomescdir` (nominal escapement direction), and to `nomwrmode` (Nominal Writing Mode).

### Comment
The Comment parameter allows the creator or the user of the font resource to make comments. The content of this parameter must be non-processable information and is ignored by any processing implementation.

*   **Parameter type =** character string
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter can be expressed as a non-iso-property.

### Design General Class (ISO)
The Design General Class parameter specifies the ISO (International Standards Organization) Font Standard General Classification of the font family design.

*   **Parameter type =** number
*   **Synonyms =** design class
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the class subfield of `dsngroup` (Design Group). The ISO property is a code in the range of 0 to 255.

### Design Specific Group (ISO)
The Design Specific Group parameter specifies the ISO Font Standard Specific Group of the font family design.

*   **Parameter type =** number
*   **Synonyms =** design class
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the specific group subfield of `dsngroup` (Design Group).

### Design Subclass (ISO)
The Design Subclass parameter specifies the ISO Font Standard Subclass of the font family design.

*   **Parameter type =** number
*   **Synonyms =** design class
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the subclass subfield of `dsngroup` (Design Group).

### Em-Space Increment
The Em-Space Increment parameter specifies typographic space that corresponds to the space between sentences. Traditionally equals the vertical font size.

*   **Parameter type =** number
*   **Synonyms =** em increment
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern Writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to the difference between the px,py and the ex,ey values for the Em-space glyph.

### Extension Font
The Extension Font parameter indicates that this font resource was designed to be an extension (contains user-defined characters) to another font.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list.

### Family Name
The Family Name parameter specifies the common name for a font design. A font family includes all typeface variations of the font design.

*   **Parameter type =** character string
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `fontfamily`.

### Font Local Identifier
The Font Local Identifier parameter specifies a numeric identifier assigned temporarily to a font resource within the context of another object.

*   **Parameter type =** number
*   **Synonyms =** font local ID, font LID
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list.

### Font Typeface Global Identifier
The Font Typeface Global Identifier parameter (usually called Font Global Identifier, FGID) specifies the unique number assigned to the font typeface.

*   **Parameter type =** number
*   **Synonyms =** font global identifier, FGID, typeface identifier
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list.

### Font Use Code
The Font Use Code parameter specifies the intended use of the graphic characters in a font.

*   **Parameter type =** code
*   **Valid choices:**
    *   0: No font use intent
    *   1: Image symbol set for text in a graphics object
    *   3: Pattern symbol set in a graphics object
    *   4: Marker symbol set in a graphics object
    *   5: High resolution indicator in a graphics object
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list.

### Graphic Character Set Global Identifier
The Graphic Character Set Global Identifier (GCSGID) parameter specifies the number assigned to a graphic character set.

*   **Parameter type =** number
*   **Synonyms =** graphic-character-set ID, character-set name
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `incglyphcols` subfield of `glyphcomp`.

### Hollow Font
The Hollow Font parameter specifies that the graphic characters of the font appear with only the outer edges of the strokes.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to the Outline code (2) of the `structure` property.

### Italics
The Italics parameter specifies that the graphic characters are designed with a clockwise incline.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to the italic value (4) of the ISO `posture` property.

### Kerning Pair Data
The Kerning Pair Data parameter specifies that kerning pair data is available in the font resource.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Presence is indicated by the presence of the `peas` (Pairwise Escapement Adjusts) data itself.

### Maximum Horizontal Font Size
The Maximum Horizontal Font Size parameter specifies the maximum horizontal size for scaling, as specified by a font designer.

*   **Parameter type =** number
*   **Synonyms =** maximum character width, maximum horizontal point size
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `maxanascale` (Maximum Anamorphic Scale).

### Maximum Vertical Font Size
The Maximum Vertical Font Size parameter specifies the maximum vertical size for scaling purposes as specified by a font designer.

*   **Parameter type =** number
*   **Synonyms =** maximum design size, maximum point size
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `maxsize`.

### Measurement Units
The Measurement Units parameter specifies the unit base and units per unit base in both X and Y directions.

*   **Parameter type =** two codes and two numbers
*   **Valid unit-base code choices:**
    *   0: Ten inches
    *   1: Ten centimeters
    *   2: Relative units
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `relunits`.

### MICR Font
The MICR Font parameter indicates that this font resource was designed for use in Magnetic Ink Character Recognition (MICR) applications.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Minimum Horizontal Font Size
The Minimum Horizontal Font Size parameter specifies the minimum horizontal size for scaling, as specified by a font designer.

*   **Parameter type =** number
*   **Synonyms =** minimum character width, minimum anamorphic scaling
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minanascale`.

### Minimum Vertical Font Size
The Minimum Vertical Font Size parameter specifies the minimum vertical size for scaling purposes as specified by a font designer.

*   **Parameter type =** number
*   **Synonyms =** minimum point size, minimum size
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minsize`.

### Nominal Character Slope
The Nominal Character Slope parameter specifies the slope (stem incline) of the main strokes. Measured clockwise from vertical.

*   **Parameter type =** number
*   **Synonyms =** character slope
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `postureangle`.

### Nominal Horizontal Font Size
The Nominal Horizontal Font Size parameter specifies the nominal horizontal size for scaling. Corresponds to the escapement of the space character (SP010000).

*   **Parameter type =** number
*   **Synonyms =** horizontal font size, nominal horizontal point size
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Nominal Vertical Font Size
The Nominal Vertical Font Size parameter specifies the vertical design size of the font.

*   **Parameter type =** number
*   **Synonyms =** nominal point size, design size
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `dsnsize`.

### Overstruck Font
The Overstruck Font parameter specifies that the graphic characters of the font appear as though over-struck by another graphic character (often a hyphen).

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Represented by a unique font family name or non-iso-property.

### Proportional Spaced
The Proportional Spaced parameter specifies that the character increments for each graphic character in the font resource might vary.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to the proportional value (2) of `escclass`.

### Private Use
The Private Use parameter specifies that some or all of the data is privately owned or protected by a licensing agreement.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Resource Name
The Resource Name parameter identifies a resource object by a character string name.

*   **Parameter type =** character string
*   **Synonyms =** resource tag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `fontname`.

### Specified Horizontal Font Size
The Specified Horizontal Font Size parameter specifies the horizontal font size indicated by the document creator.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Specified Horizontal Scale Factor
The Specified Horizontal Scale Factor parameter specifies uniform or anamorphic scaling. Numerator in a ratio (Scale Factor / Vertical Size).

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** No equivalent exists in resource architecture; appears only in data stream references.

### Specified Vertical Font Size
The Specified Vertical Font Size parameter specifies the vertical font size indicated by the document creator.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `dsnsize`.

### Transformable Font
The Transformable Font parameter specifies that the pattern data allows algorithmic transformation (e.g., scaling).

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Typeface Name
The Typeface Name parameter specifies the common name of the typeface (e.g., Sonoran Sans Serif Roman bold condensed).

*   **Parameter type =** character string
*   **Synonyms =** facename
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `typeface`.

### Underscored Font
The Underscored Font parameter specifies that the graphic character pattern data contains underscores as part of the shape.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Uniform Character Box Font
The Uniform Character Box Font parameter specifies that all raster patterns are of the same size.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Weight Class
The Weight Class parameter indicates the visual weight (degree or thickness of strokes).

*   **Parameter type =** code
*   **Valid choices:**
    *   1: Ultralight
    *   2: Extralight
    *   3: Light
    *   4: Semilight
    *   5: Medium (normal)
    *   6: Semibold
    *   7: Bold
    *   8: Extrabold
    *   9: Ultrabold
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `weight`.

### Width Class
The Width Class parameter indicates a relative change from the normal aspect ratio.

*   **Parameter type =** code
*   **Valid choices:**
    *   1: Ultracondensed (50%)
    *   2: Extracondensed (62.5%)
    *   3: Condensed (75%)
    *   4: Semicondensed (87.5%)
    *   5: Normal (100%)
    *   6: Semiexpanded (112.5%)
    *   7: Expanded (125%)
    *   8: Extraexpanded (150%)
    *   9: Ultraexpanded (200%)
*   **Transformation to Eastern Writing systems:** This parameter is writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `propwidth`.

### X-Height
The X-Height parameter specifies the height of the body of lowercase graphic characters (usually height of 'x').

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `lcheight`.

---

## Font-Metric Parameters

These parameters apply to all of the font characters and are repeated for each supported character rotation.

### Default Baseline Increment
The Default Baseline Increment parameter specifies the nominal distance between character reference points in the vertical direction recommended by the designer.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** For vertical writing, distance between baselines of two columns.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minlinesp`.

### External Leading
The External Leading parameter specifies recommended additional interline white space.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Supplemental value for extending distance between baselines.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Figure Space Increment
The Figure Space Increment parameter specifies the character increment used for numerals.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern Writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `tabescx` and `tabescy`.

### Internal Leading
The Internal Leading parameter specifies nominal white space above and below characters.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Difference between Nominal Horizontal Size and Max Baseline Extent for 270° rotation.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Kerning
The Kerning parameter is a flag that indicates whether any of the metrics contain negative values allowing kerning.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Kerning Pair Character 1
The Kerning Pair Character 1 parameter specifies the first character in a kerning pair.

*   **Parameter type =** character string
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname`.

### Kerning Pair Character 2
The Kerning Pair Character 2 parameter specifies the second character in a kerning pair.

*   **Parameter type =** character string
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname` component of `peax` / `peay`.

### Kerning Pair X-Adjust
The Kerning Pair X-Adjust parameter specifies the escapement adjustment in the x direction for the specified pair.

*   **Parameter type =** number
*   **Synonyms =** pairwise escapement x-adjust
*   **Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to the adjustment component of `peax`.

### Maximum Ascender Height
The Maximum Ascender Height parameter specifies the maximum height from the baseline to the top of any character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Max distance from baseline to right-hand edge of character box for vertical writing.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum x or y value of `maxfontext`.

### Maximum Baseline Extent
The Maximum Baseline Extent parameter specifies the space parallel to the baseline required to place characters. Sum of max baseline offset and max descender depth.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Max Ascender Height plus Max Descender Depth.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Baseline Offset
The Maximum Baseline Offset parameter specifies the maximum distance from the baseline to the upper edge of the character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Equal to Maximum Ascender Height.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Character Box Height
The Maximum Character Box Height parameter specifies the height of uniform boxes or max height of variable boxes.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Character Box Width
The Maximum Character Box Width parameter specifies the width of uniform boxes or max width of variable boxes.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Character Increment
The Maximum Character Increment parameter specifies the maximum increment for all characters in a font rotation.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Max increment for 270° rotation metrics.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Descender Depth
The Maximum Descender Depth parameter specifies the maximum depth from the baseline to the bottom of any character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Max distance from baseline to left-hand edge of box for vertical writing.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to minimum x or y value of `maxfontext`.

### Maximum Lowercase Ascender Height
The Maximum Lowercase Ascender Height parameter specifies the max ascender height of lowercase (a–z) at 0° rotation.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter does not apply.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum Lowercase Descender Depth
The Maximum Lowercase Descender Depth parameter specifies the max descender depth of lowercase (a–z) at 0° rotation.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** This parameter does not apply.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Maximum V(y)
The Maximum V(y) parameter is the maximum of all Adobe ATM V(y) values (y coordinate of distance from origin to positioning point).

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum of all `P(y)` values.

### Maximum W(y)
The Maximum W(y) parameter is the maximum of all Adobe ATM W(y) values (y coordinate of distance from positioning point to escapement point).

*   **Parameter type =** number
*   **Synonyms =** Vertical Character Increment
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum absolute magnitude of all `(P(y)-E(y))` values.

### Minimum A-space
The Minimum A-space parameter specifies the smallest A-space (most negative or least positive) value.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Least A-space for 270° rotation metrics.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Nominal Character Increment
The Nominal Character Increment parameter specifies the most commonly repeated character increment.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Space Character Increment
The Space Character Increment parameter specifies the default character increment for the space character.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Space increment for 270° rotation.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to difference between `px,py` and `ex,ey` for Normal Space.

### Subscript Vertical Font Size
The Subscript Vertical Font Size parameter specifies designer's recommended size for subscript characters.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsscaley`.

### Subscript X-Axis Offset
The Subscript X-Axis Offset parameter specifies recommended vertical offset from baseline to subscript baseline.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Does not apply.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsoffsetx`.

### Superscript Vertical Font Size
The Superscript Vertical Font Size parameter specifies designer's recommended size for superscript characters.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsscaley`.

### Superscript X-Axis Offset
The Superscript X-Axis Offset parameter specifies recommended vertical offset from baseline to superscript baseline.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Does not apply.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsoffsetx`.

### Throughscore Position
The Throughscore Position parameter specifies recommendation for drawing throughscores (perpendicular distance from baseline to top of stroke).

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between center line and vertical score line.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scoreoffsetx` / `scoreoffsety`.

### Throughscore Width
The Throughscore Width parameter specifies recommended width (thickness) of the throughscore.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scorethick`.

### Underscore Position
The Underscore Position parameter specifies recommendation for drawing underscores (distance from baseline to top of stroke).

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between center line and vertical score line running to the left.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scoreoffsetx` / `scoreoffsety` (`RIGHTSCORE`).

### Underscore Width
The Underscore Width parameter specifies recommended width (thickness) of underscores.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scorethick`.

### Uniform A-space
The Uniform A-space parameter specifies that a uniform amount of A-space was removed from all patterns.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** A-space for 270° rotation metrics.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Uniform Baseline Offset
The Uniform Baseline Offset parameter specifies that all patterns have a common baseline offset.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** Uniform Baseline Offset for 270° rotation metrics.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Uniform Character Increment
The Uniform Character Increment parameter specifies that all increments are the same.

*   **Parameter type =** flag
*   **Transformation to Eastern Writing systems:** Uniform Character Increment for 270° rotation metrics.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

---

## Character-Metric Parameters

These parameters apply to individual characters and are repeated for each character in each rotation.

### A-space
Distance from character reference point to the least positive character coordinate system x-axis value.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between reference point and least positive x-axis value of 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to distance between `px,py` and closest `ext` property value.

### Ascender Height
Height of the topmost mark of a graphic character relative to the baseline.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between reference point and most positive y-axis value of 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to max y (0° rotation) or max x (270° rotation).

### Baseline Offset
Distance from character baseline to the topmost edge of a character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Equal to Ascender Height.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from `ext` (Extents) property.

### B-space
Width of the (bounded) character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between most/least positive x-axis value of 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from `minx`/`maxx` or `miny`/`maxy`.

### Character Box Height
Height of the character box for a graphic character.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `miny` and `maxy`.

### Character Box Width
Width of the character box for a character.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `minx` and `maxx`.

### Character Increment
Algebraic sum of A-space, B-space, and C-space.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between reference point and escapement point for 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `px,py` and `ex,ey`.

### C-space
Width of the space from the bounded character box to the escapement point.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between escapement point and least positive x-axis value of 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Derived from ISO glyph properties.

### Descender Depth
Distance from baseline to bottom of character box.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Distance between reference point and most negative y-axis value of 270° shape.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `miny` (0° rotation) or `minx` (270° rotation).

### Graphic Character Global Identifier
Registered identifier of a graphic character. Default encoding is EBCDIC, length is 8 bytes.

*   **Parameter type =** character string
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname`.

---

## Character-Shape Parameters

Required for presentation of the character shapes. Repeated for each technology supported.

### Design Resolution X
Intended presentation resolution in the x direction.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Design Resolution Y
Intended presentation resolution in the y direction.

*   **Parameter type =** number
*   **Transformation to Eastern Writing systems:** Writing-system independent.
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Linkage Code
Specifies whether character IDs in CMAP are linked to Name Map.

*   **Parameter type =** code
*   **Valid Choices:**
    *   0: Linked
    *   1: Unlinked
*   **Transformation to Eastern Writing systems:** Primarily used with eastern writing systems.
*   **Transformation to ISO/IEC 9541 font architecture:** No equivalent.

### Object Type
Identifies various objects embedded in a font resource (e.g., CMAP, CID, PFB).

*   **Parameter type =** code
*   **Valid Choices:**
    *   0: No information
    *   1: CMAP file
    *   5: CID file
    *   6: PFB file
    *   7: AFM file
    *   8: File Name Map
*   **Transformation to ISO/IEC 9541 font architecture:** No equivalent.

### Pattern Data
The actual pattern data for the shape representation technique.

*   **Parameter type =** undefined data
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Pattern Data Alignment Code
Specifies alignment of beginning of each character's pattern data (exponent of base 2).

*   **Parameter type =** code
*   **Valid choices:**
    *   0: One-byte Alignment
    *   1: Two-byte Alignment
    *   2: Four-byte Alignment
    *   3: Eight-byte Alignment
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Pattern Data Alignment Value
Byte alignment multiplier of the Pattern Data Offset.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Pattern Data Count
Total quantity of shape data in bytes.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Pattern Data Offset
Used with Alignment Code to calculate actual byte offset.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Pattern Technology Identifier
Specifies the technologies for font graphic patterns.

*   **Parameter type =** code
*   **Valid choices:**
    *   5: Laser Matrix N-Bit Wide Horizontal Sections
    *   30: CID Keyed Outline Font Technology
    *   31: Type 1 PFB Outline Font Technology
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Precedence Code
Specifies if an object is primary or auxiliary.

*   **Parameter type =** code
*   **Valid Choices:**
    *   0: Primary
    *   1: Auxiliary
*   **Transformation to ISO/IEC 9541 font architecture:** No equivalent.

### Shape Pattern Index
Index into Pattern Data Offset repeating group.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Writing Direction Code
Nominal direction in which characters are written or read.

*   **Parameter type =** code
*   **Valid Choices:**
    *   0: No information
    *   1: Horizontal
    *   2: Vertical
    *   3: Both
*   **Transformation to ISO/IEC 9541 font architecture:** Corresponds to `Writing Mode`.

---

## Character-Mapping Parameters

Used to map code points to graphic character identifiers.

### Code Page Description
Descriptive title or short description of the code page.

*   **Parameter type =** character string
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Code Page Global Identifier
Number assigned to a code page (CPGID).

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Code Point
Value of the integer assigned to a graphic character in a list.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Encoding Scheme
Identifies the scheme used to code character data (e.g., EBCDIC, ASCII, UCS).

*   **Parameter type =** code
*   **Valid choices (Basic structure):**
    *   0: No specified organization
    *   2: IBM-PC Data
    *   6: EBCDIC Presentation
    *   8: UCS Presentation
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Graphic Character Identifier Type
Identifies the naming source and method used to identify graphic characters.

*   **Parameter type =** code
*   **Valid choices:**
    *   2: IBM Registered EBCDIC GCGID
    *   3: Font Specific ASCII Character Name
    *   5: CMap Binary Code Point
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Graphic Character GID Length
Specifies the length of the graphic character identifier (default 8 bytes).

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Invalid Coded Character
Specifies that the character is not valid and should not be used.

*   **Parameter type =** flag
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### No Increment
Specifies that the character increment should be ignored.

*   **Parameter type =** flag
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### No Presentation
Specifies that the character should not be presented.

*   **Parameter type =** flag
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Number of Coded Graphic Characters Assigned
Number of assigned code points in a code page.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Section Number
Specifies the section number of a multibyte code page (first byte of two-byte character).

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Space Character Code Point
Code point assigned as the space character.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Space Character Section Number
Section number for the space character code point.

*   **Parameter type =** number
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.

### Unspecified Coded Character Identifier
Identifier used whenever a font doesn't contain info for a GCGID associated with a code point.

*   **Parameter type =** character string (named GCGID)
*   **Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property.
