# Chapter 3. Overview of PTOCA

This chapter:
*   Summarizes the concepts that form the basis of PTOCA
*   Summarizes the data structures in PTOCA

## General Concepts

The Presentation Text object is a description of text for a portion of a document that has been generated from one of many possible sources. Examples of possible sources are:
*   Formatting from revisable text
*   Transformation from other data streams
*   Editor or formatting process
*   Direct generation process

Once created, the object can be presented, revised, or used in a resource such as an overlay. It occupies a given amount of space, the object space, and can be located and oriented in a physical area, the object area. The environment that carries the object is the provider of all external relationships for the object, including the object area.

The object space consists of an array or matrix of addressable positions which identify potential locations at which to place the basic elements of the object, graphic characters. Graphic characters are placed at addressable positions called the presentation positions, rotated relative to a baseline, and have the baseline of a group of characters undergo orientation to various angular positions, such as vertical presentation. These positioning functions are specified by control sequences which are carried along with the graphic characters. The initial positions or beginning values for many of the control sequences are described in a descriptor.

### Data Stream Environment

The Presentation Text object is designed to be carried by and become part of a data stream, called the controlling environment. The data stream defines rules by which the object can be carried. Further information about data streams can be found in Appendix A, "MO:DCA Environment" and Appendix B, "IPDS Environment".

### Data Structures

The Presentation Text Data Descriptor carries the size, shape, and other special information about the object. The data stream is responsible for providing the proper information to the receiver, but PTOCA specifies a hierarchical method for determining the default values to be used by the receiver if the data stream does not supply the requisite information.

The Presentation Text data contains the code points that identify the graphic characters and the control sequences that specify where and how the graphic characters are to be positioned within the object space. The graphic character code points that represent text information can be specified in a Transparent Data (TRN), a Repeat String (RPS), or a Unicode Complex Text (UCT) control sequence, or they can be specified as free-standing code points that appear between control sequences. Graphic character code points can also be resolved to glyph IDs in a font. These glyph IDs are carried in Glyph Layout Control (GLC) chains for presentation.

<!-- Page 28 -->

Further information about PTOCA data structures is found under "Presentation Text Data" and "Presentation Text Data Descriptor".

### Subsets of Function

The control sequences represent the functional capabilities provided by the Presentation Text object. Since receivers of the object might not all have equivalent capabilities, it is convenient to create subsets, also called subset levels, of the total function that is available. The base is a set of functions required in any environment, including the ability to interpret and validate the control sequences and parameters, and to detect and report exception conditions that are within the PTOCA subsets.

*   **PT1**: Includes a set of relatively primitive control sequences that a receiver is expected to support.
*   **PT2**: Includes all of the PT1 subset plus new control sequences for underscore, overstrike, superscripts and subscripts.
*   **PT3**: Includes all of the PT2 subset plus a new control sequence to enable spot (highlight) colors and process colors for text and rules.
*   **PT4**: Includes new control sequences to support the rendering of complex text.

The intent of subsets is to reduce the number of combinations of supported controls so that interchange between host processors is manageable. For further information about subsets, see Chapter 6, "Compliance with PTOCA", Appendix A, "MO:DCA Environment", and Appendix B, "IPDS Environment".

## Spatial Concepts

### Object Space

The Presentation Text object space defines the presentation space into which the presented text characters will fit. The object space is the matrix of addressable positions which are available to the generating process that defined it. This space has no relationship to the physical medium or printed page until it is placed in an object area by a composition process as part of the creation of a page or overlay. The Presentation Text object has no concept of pages, although the composition process may create an entire page from one object.

Positioning of the object space within the object area is accomplished by a mapping within the controlling environment. The object area is the boundary for text presentation by a receiver, and the controlling environment specifies the error recovery action that must occur if any portion of a character or rule violates the object area boundary. The object space is the boundary for the text positioned for presentation.

### Coordinate Systems

The Presentation Text object uses two orthogonal coordinate systems:
1.  **Xp, Yp coordinate system**: Simulates the reader's view of the object space.
2.  **I, Bcoordinate system**: Indicates the direction of the addition of characters to form words and lines, and the direction of the addition of subsequent lines.

The Xp, Yp coordinate is an orthogonal coordinate system based on the fourth quadrant of a standard Cartesian coordinate system. Both the Xp axis and the Yp axis specify positive values, which is a difference from the Cartesian system where the Y axis in the fourth quadrant specifies negative values. The origin of the coordinate system is in the upper left corner; the positive Xp-axis is from left-to-right, and the positive Yp-axis is from top-to-bottom. The frame of reference for the Xp, Yp coordinate system is provided by the environment's coordinate system for the object area into which the object space is placed. The location of the Xp, Yp coordinate system origin is specified as an offset from the object area's coordinate system origin.

The Xp, Yp coordinate system describes the boundary of the object space, which is a rectangle with sides equal to the extent along each axis. That is, the Xp-extent is the length along the Xp-axis, and the Yp-extent is the length along the Yp-axis. Thus the object space is bounded by a rectangle described by the following four coordinate pairs:
*   Xp-origin, Yp-origin
*   Xp-extent, Yp-origin
*   Xp-extent, Yp-extent
*   Xp-origin, Yp-extent

Please see Figure 4.

<!-- Page 29 -->

The Xp, Yp coordinate system and the I, Bcoordinate system are closely related, as indicated in Figure 5. In fact, the Xp-extent is equal to one of the I, Bcoordinate extents, either the I-extent or the B-extent, and the Yp-extent is equal to the other. Therefore, the angle between the I-axis and B-axis will be identical to the angle between the Xp-axis and the Yp-axis. The Xp, Yp coordinate system describes the spatial viewport for the reader, while the I, Bcoordinate system describes the directions to be used for presentation and for interpretation by the reader of the graphic characters being presented.

**Figure 4. Presentation Space Definition**

<!-- Page 30 -->

The I, Bcoordinate system adds a concept of direction to the object space definition. The reader of text comprehends the text by assembling the characters into words or phrases. The direction in which the reader normally constructs the words or phrases is called the inline direction or I-direction. The inline direction for typical Latin-based text is left-to-right, but for languages such as Japanese, or tasks such as labeling graphs, the inline direction may be top-to-bottom or one of the other possible directions. Please see Figure 5.

**Figure 5. I, B Coordinate System Examples**

The inline direction is also the direction of increasing positive values of *i* along the I-axis, and prescribes the order in which succeeding characters are processed by a receiver. The maximum value of *i* is the I-extent.

All of the graphic characters placed in the inline direction for a given value of *b* constitute a line. The direction in which successive lines are placed for continued reading of the text is the baseline direction or B-direction. The baseline direction for typical Latin-based text is top-to-bottom, but for other languages, such as Japanese vertical writing, the baseline direction is from right-to-left. The baseline direction is also the direction of increasing positive values of *b* along the B-axis. The maximum value of *b* is the B-extent.

### Measurement

Although the controlling environment, as a carrier of the Presentation Text object, specifies the layout characteristics of the object presentation, the object, as a self-defining portion, provides the measurement units used by the generator in formatting the data. The Presentation Text object provides for both the English and metric systems of measurement. The measurement units for the object are specified in the Presentation Text Data Descriptor or determined by defaults. Measurement units can be specified so that the Xp-axis and the Yp-axis have different resolutions.

Measurement units are used throughout PTOCA to identify the units of measure to be used for such things as extents and offsets along the X and Y axes of a coordinate system.

<!-- Page 31 -->

Each individual measurement unit is specified as two separate values:
*   **Unit base**: This value represents the length of the measurement base. It is specified as a one-byte coded value:
    *   X'00': Ten inches
    *   X'01': Ten centimeters
*   **Units per unit base**: This value represents the number of units in the measurement base. It is specified as a two-byte numeric value between 1 and 32,767.

The term *units of measure* is defined as the measurement base value divided by the value of the units per unit base.

For example, if the measurement base is 10 inches and the units per unit base value is 5,000, the units of measure are 10 inches / 5000 or one five-hundredth of an inch. Here are some additional examples:

| Units/inch | Comments |
| :--- | :--- |
| 1,440 X 1,440 units/inch | 14,400 divisions in 10 inches on both axes |
| 80 X 77 units/centimeter | 800 divisions in 10 centimeters on Xp and 770 divisions in 10 centimeters on Yp |

The size of the object space is specified in measurement units. Each addressable position is one measurement unit away from another addressable position in any direction. That is, a specified measurement unit along the Xp-axis separates the addressable positions in the direction parallel to the Xp-axis, and another specified measurement unit along the Yp-axis separates the addressable positions in the direction parallel to the Yp-axis. This creates an array of addressable positions, each of which has the potential of being designated as the position of a graphic character.

The measurement units thus defined become the measurement units for all linear measurements within the object. The receivers must convert from these measurement units to measurement units for their environment as required, and keep track of rounding errors, making appropriate adjustments as needed to ensure presentation fidelity at a given level of capability.

The measurement units for angular dimensions are degrees.

## Font Concepts

When a PTOCA receiver detects a graphic character code point, the code point must be translated into a pattern of marks on some medium. A single-byte or multi-byte code point is used to identify the graphic character which is to be presented. Before presentation can take place, several attributes of the graphic character must be determined, such as the following:
*   What character is represented by the code point?
*   Is the character valid?
*   What is the shape of the character?

The assignment of code points to characters is done by means of a code page or similar encoding structure such as a character map. A code page or character map can be envisioned as a table which contains pairs of values, where the first element of each pair is the code point and the second element is the identifier of the graphic character. The code page also defines the number of bytes required to represent a character, that is, bytes per code point.

For some font technologies such as the FOCA font technology, the validity of a character may be verified by referring to a graphic character set. A graphic character set is a set of letters, digits, punctuation marks, arithmetic operators, chemical symbols, or other symbols. If the character represented by the code point is not

<!-- Page 32 -->

contained in the graphic character set, then that character is invalid, and another graphic character must be substituted for it. The active coded font designates what graphic character should be substituted in its place.

The shape or graphic pattern of the character is determined from the related font. A font consists of an algorithm for presenting all the graphic characters that have a given style, size, weight and certain other characteristics. Here are examples:
*   Style: Bodoni
*   Size: 10-point
*   Weight: bold
*   Other characteristics: italic

This algorithm could consist of a style manual, raster patterns, vector graphic command lists, stroke generation programs, engraved type, or other means of specifying the necessary attributes. The font also specifies the character increment or escapement, that is, the width of the character, and the character reference point or character origin, that is, the point within the graphic pattern which is to be aligned with the presentation position. Within a Presentation Text object, the desired characteristics are specified through a reference to a specific font. The coded font contains the encoding and the shape and metric information which are assigned to each graphic character. The presentation process applies the graphic character code points found within the Presentation Text object to the active coded font in order to determine the presentation characteristics of the characters. The font is managed as a font resource in the controlling environment. A Presentation Text object uses this resource by making reference to the coded font.

The structure and content of FOCA-based fonts is defined by the Font Object Content Architecture (FOCA), which is described in *Font Object Content Architecture Reference*, AFPC-0007.

The structure and content of TrueType and OpenType fonts are described in the following documents available from the Microsoft® and Apple® web sites:
*   OpenType Specification Version 1.4 (Microsoft Corporation: October 11, 2002), at http://www.microsoft.com/typography/otspec/default.htm
*   TrueType Reference Manual (Apple Computer, Inc.: December 18, 2002), at http://developer.apple.com/fonts/TrueType-Reference-Manual

## Graphic Character Placement Concepts

Graphic characters are the basic elements of the Presentation Text object. The control sequences defined by PTOCA deal with the presentation of these graphic characters regarding either their positioning within the object, or some attribute of their presentation, such as color.

<!-- Page 33 -->

PTOCA assumes that the graphic characters are identified by one-byte or multi-byte code points that are defined within the encoding structure for a font. Each graphic character thus identified has a defined character reference point or character origin, a character increment or character escapement, and a character baseline that allows them to be correctly positioned along the baseline in the I-direction of the Presentation Text object. Please see Figure 6.

**Figure 6. Horizontal Metrics: TrueType/OpenType Fonts and FOCA Fonts**

The presentation of a graphic character is accomplished by placing the character reference point or character origin of the graphic character at the presentation position. The presentation position is an I, Bcoordinate pair, that is, an addressable position in the object space. The *b* value is fixed for the current baseline, Bc. The current *i* value, the new presentation position, is calculated from the previous *i* value by adding the character increment or character escapement of the graphic character being presented to the previous value of *i*, that is, the previous presentation position.

The presentation position in PTOCA designates a between-the-pels position on a presentation surface, not a pel centerline intersection position. The concept of between-the-pels positioning is especially important for the presentation of rules. Please see "Graphic Character Processing" for more information.

Object generators will determine which characters are to be placed on each line of the object. This does not require that the font be known at generation time in all cases. For fixed pitch fonts where the character increment is a constant value and for fonts utilizing standard metrics, it is possible for any font with the same metrics to be specified without modification to the relative positioning of the graphic characters.

Spacing between the characters can be modified by an adjustment, which is either an increment or a decrement on the character increment values provided for the graphic characters. In addition, the character increment specified for the space code point may be changed to a different value at any time to provide variation in the spacing between words.

<!-- Page 34 -->

Lines of graphic characters are ended by moving the presentation position to the beginning of the next line. This may be done using the positioning control sequences or through the use of a control sequence that causes the baseline increment value and the inline margin to set the presentation position to the next line.

PTOCA is intended to be precise enough to permit multiple products to reproduce the Presentation Text object faithfully. Faithful reproduction includes such aspects as the size and relative positions of graphic characters and strings of graphic characters. The responsibility for faithful reproduction belongs to the process that presents the object. PTOCA is also designed to permit less than faithful reproduction. It is possible to specify exception conditions for which continuation of processing is acceptable. This permits a process that cannot faithfully reproduce the object to continue with its best approximation. If less than faithful reproduction is acceptable for an application, interchange among a larger set of receivers is possible.

## Chaining Concepts

The Presentation Text object uses a control sequence to indicate that a function is to be performed. The control sequence consists of the Control Sequence Introducer and a list of parameters.

A Control Sequence Introducer contains the following fields:
*   A one-byte prefix, X'2B'
*   A one-byte class, X'D3'
*   A one-byte length
*   A one-byte function type

Control sequences can be chained together using a chaining convention. Although the first control sequence in a chain has the prefix and class, the remaining chained control sequences do not. Chaining reduces the number of bytes to be handled and removes the need to determine whether the next character is a control sequence or not. Please see Table 4 for a list of PTOCA control sequences, showing both unchained and chained function types. Please see "Control Sequence Chaining" for more information about chaining.

## Character String Concepts

Graphic characters may be grouped together as character strings to eliminate the necessity of checking for the Control Sequence Prefix. This capability is useful for creating strings of repeated characters. An example is the leader dots in a table of contents. The leader dot graphic character occurs only once per line in the object although it is repeated many times at presentation.

In addition this capability, when used in conjunction with chaining, allows the object to be described in terms of two parsing modes: control sequences and graphic characters. These two basic modes can then be optimized separately in an implementation.

## Ruled Line Concepts

Simple line graphic functions have been incorporated to satisfy requirements for figure enclosures, tables, boxes, line drawings, and so on. The capability includes vertical and horizontal rules which may have both the length and the width of the rules specified.

## Suppression Concepts

An ability to restrict the presentation of the graphic characters in a controlled way is provided by the suppression function. Suppression is accomplished by marking the text data to be suppressed and specifying

<!-- Page 35 -->

an identifier to allow grouping of the marked text data. All data marked with an active suppression identifier is prevented from being presented when the object is processed. The controlling environment specifies which suppression identifiers are active for the object.

Suppression can be used to create form letters that have portions of the form left blank, or filled in differently, depending on the intended audience of each instance of the letter.

## Orientation and Rotation Concepts

There are times when it is desirable to place graphic characters in other than the customary upright reading position. For example, when labeling a graph, the graphic characters would be placed upright, but the line would be vertical; that is, the I-direction would be top-to-bottom. The I-direction and B-direction determine the orientation of the text, and an I-direction change is called a change of orientation. However, since the upright position is with respect to the I-axis, when reorientation occurs the characters appear to rotate at the same time. To create a vertical effect, such as in a graph, the graphic characters must also be rotated. Please see Figure 7. This figure illustrates changes in orientation with no change in character rotation.

**Figure 7. Orientation Examples**

Orientation is specified in degrees in a clockwise direction from the zero-degree starting point. The zero-degree starting point is the I-axis when the I-direction is left-to-right. A change in text orientation may also move the I, Borigin to a different corner of the text object space. Figure 7 shows the location of the I, Borigin for the 8 text orientations. The rotation of the characters is described in terms of angular movement of the character shape with respect to the character baseline, and is specified as part of the selection criteria for fonts.

## Extended Functions Concepts

Controls are provided in PTOCA to accomplish specialized functions. These functions include underscore, overstrike, superscript, and subscript.

This group of control sequences follows a modal concept in that, once started, the function does not terminate until stopped. Each control sequence marks the beginning or the ending of a text field for which the function is invoked. The same control sequence syntax with a non-zero parameter value begins the text field, and with a zero parameter value indicates the end of the field. All other control sequences are valid within these text fields without causing termination of the field.

<!-- Page 36 -->

Underscore is the capability of drawing a line under individual characters or groups of characters. Overstrike is the capability of filling a field with a specific character to provide a marked-out appearance.

The superscript and subscript functions require the ability to move temporarily from the designated baseline by small amounts. The superscript function requires movement in the negative B-direction, that is, above the baseline. The subscript function requires movement in the positive B-direction, that is, below the baseline. The amount of the incremental moves about the baseline is also variable. This allows a sophisticated implementation to provide a wide range of superscript and subscript capability, to be used, for example, when positioning the various parts of mathematical equations.

## Complex Text Processing Overview

The Unicode standard recommends that text for all languages be stored in the order that it would be read or spoken, without regard to presentation order. With few exceptions, Latin, Cyrillic, and Greek scripts present text in the same order that data processing systems store the text. These exceptions are ligatures which are combinations of characters and accented characters. Traditionally, computer applications encode these combined characters using one encoding point and one graphic character. As an example, most systems encode Latin small ligature ff as one character.

Complex text languages provide different layouts for the presentation of text and its storage. Bi-directional (BIDI) languages present text normally from right to left; however, some text such as numbers and embedded Latin, Cyrillic, and Greek scripts, are written from left to right. These languages include Arabic, Urdu, Farsi, and Hebrew.

The following example nests Western and bi-directional scripts. Lowercase characters represent Western Script and uppercase characters represent a bi-directional script:
*   Data Storage order: my address is SUITE B 100 YORK BLVD richmond hill
*   Presentation order: my address is DVLB KROY 100 B ETIUS richmond hill

Some languages require that characters be presented with different shapes or in a different order than their storage order. Arabic characters can be represented by one of four shapes depending on their position in a word. Arabic characters can also combine to form ligatures. In many south Asian languages, characters may need to be repositioned, reordered, or split, depending on adjacent characters.

Composition applications that need to present Complex Text will use layout engines such as International Components for Unicode (ICU), Windows® Uniscribe, Apple Advanced Typography, Pango, Scribe, or Graphite, to present text. Each engine has a different implementation. Outputs from the engines will differ somewhat. Some engines have better support for some language scripts than others.

PTOCA supports consistent text presentation through the Unicode Complex Text (UCT) control sequence and its complementary supporting glyph run control sequences. PTOCA presents text on a line-by-line basis. This means that applications must provide text boundary analysis. ICU provides iterators that support this type of analysis. These break iterators support determining character, word, line-break, and sentence boundaries. The Unicode Standard Annex #29 provides definitions for these boundaries. The ICU User Guide provides examples of boundary analysis. The Unicode BIDI algorithm works best on paragraphs, so the composition application should apply the algorithm before breaking the text into individual lines.

<!-- Page 37 -->

The ICU Layout Engine supports the presentation of complex text through a common client interface. This uniform base interface models a TrueType/OpenType font at a particular point size and device resolution. TrueType/OpenType fonts have the following characteristics:
*   A font is a collection of images called glyphs. Each glyph in the font has a unique 16-bit id.
*   There is a mapping from Unicode code points to glyph ids. Some glyphs may not have a mapping.
*   There is a method to get the width of each glyph.
*   There is a method to get the position of a control point for each glyph

Client applications perform boundary analysis and determine text direction runs as necessary. They then call the layout engine to produce an array of glyph indices in display order, an array of x, y position pairs for each glyph, and optionally an array that maps each glyph back to the input text array.

The MO:DCA Presentation Text Data Descriptor and Presentation Text Data structured fields carry Presentation Text Objects in MO:DCA documents. The Presentation Text object space provides the coordinate system that allows object generators to position graphic characters and glyphs without error. It is the responsibility of the generator to ensure that it positions the graphic characters and glyphs correctly so that they do not exceed the object space.

The general approach composition applications will take to present complex text data is:
*   If the data contains bi-directional scripts, use the Unicode BIDI algorithm to break the text into directional sequences. The Unicode BIDI algorithm works best with paragraphs, so it must be applied before text is broken into separate lines.
*   If multiple TrueType/OpenType fonts are used to present the text, composition applications must identify the individual font that will be used for each substring of text to which the layout engine is applied. This step should be performed prior to, or concurrent with, the script analysis that identifies the appropriate layout engine. The ICU Paragraph Layout API provides class interfaces to represent linked fonts with methods to request the individual font and the extent of the text string to be composed. If the entire Unicode text is not rendered with a single font, the subsequent steps must be repeated for each font and substring.
*   The application will need to determine where line breaks occur because PTOCA constrains text output sequences to individual lines. The appropriate position to break text can vary by language or script. The Unicode Standard Annex #29 provides definitions for character, word, line-break, and sentence boundaries. International Components for Unicode provides break iterators that support this type of analysis. The ICU User Guide provides examples of boundary analysis.
*   The application will use a layout engine to format text sequences. A text sequence is a run of text that use the same font (e.g. typeface with the same typographic attributes including weight, width, height, posture) where the text accumulates in the same direction. Layout engines normally return:
    *   Arrays of glyph indices
    *   Arrays of glyph positions
    These arrays provide the information required to present the glyphs.
*   The application will then normalize the positions with respect to the origin established for the current text object.
*   The application will obtain or calculate the Object Identifier (OID) value of the TrueType/OpenType font used to generate the glyph ID values. This value allows presentation devices to verify that they retrieve the glyphs from the exact same font that the application used. See the *Mixed Object Document Content Architecture Reference*, AFPC-0004 for the definition of the algorithm used to calculate the OID of a TrueType/OpenType font.
*   The application will provide the full font name (FFN) of the TrueType/OpenType font used to generate the glyph ID values. This name provides a human-readable identification of the font and is also used to select a specific font in a font collection when the font OID identifies a collection.

Successful completion of these tasks will result in the application having the presentation data normalized so that it can create the GIR/GAR[/GOR] sequences and the preceding GLC control (the notation "[/GOR]" will be

<!-- Page 38 -->

used to indicate that the GOR is optional). This set of controls is called a GLC chain. The generator creates the following control sequences:
*   **A Glyph Layout Control (GLC)** which identifies the start of the complex text position requirements sequence for this text run. The GLC specifies:
    1.  the advance along the current baseline caused by processing this GLC chain
    2.  the font OID to identify and validate the font used for the subsequent glyph run control sequences
    3.  the full font name of the font, which is used to select a specific font from a font collection when the font OID identifies a collection
*   **One or more groups of**:
    1.  a Glyph ID Run (GIR) which contains the glyph IDs for this text run
    2.  a Glyph Advance Run (GAR) which contains the advances in the inline direction for each glyph
    3.  an optional Glyph Offset Run (GOR) that provides the offsets of each glyph from the baseline. This control provides the ability to position glyphs such as diacritical marks and accents
*   **An optional Unicode Complex Text (UCT) control sequence** which contains the text encoded in UTF16-BE in data storage order. Use of the UCT is recommended as it provides applications the ability to interpret the sequence of glyphs rendered by the printer.

The maximum length of a PTOCA control sequence limits a single GIR to no more than 125 glyphs. This means that print applications must be prepared to generate multiple GIR/GAR[/GOR] groupings to support long Unicode encoded text strings.

If multiple fonts linked to the currently active font are used to render the text, a GLC chain must be generated for each substring that uses a different linked font. The presentation device will use the FONTOID parameter of the GLC to identify and validate the linked font used for the subsequent glyph run control sequences. If the FONTOID parameter identifies a font collection, the presentation device uses the FFONTNME parameter of the GLC to select the specific font from the collection.

## Encryption Concepts

In certain regulatory environments, such as the financial industry, there exists the need to protect customer information such as Personal Identification Numbers (PINs) and Transaction Authentication Numbers (TANs) until presentation time. Typically, this private information must be encrypted, meaning that the code points that make up the character string to be protected cannot appear in the data stream as directly readable code points. Using special algorithms, an encryption/decryption key can be used to turn the character code points into encrypted bytes.

At presentation time, these encrypted bytes can then be turned back into code points in a character string by applying the same encryption/decryption key to algorithmically convert them back into presentation text. To preserve the security of the data stream, the actual encryption/decryption key does not appear in the data stream; key information is passed instead. The decryption device has a lookup table to correlate the key information with the actual encryption/decryption key to be used for decryption.

PTOCA has the ability to identify encrypted bytes that represent this protected information. It also provides a means to set the key information for these encrypted bytes to facilitate decryption into code points in a character string at presentation time. If the decryption should fail, a mechanism is provided in PTOCA to substitute alternate text in the place where the decrypted code points were intended to go.

<!-- Page 39 -->

## Exception Handling Concepts

PTOCA defines exception condition codes that identify the various exception conditions that can arise during the processing of a Presentation Text object. These codes are provided for reference purposes only. PTOCA also specifies a standard action for each exception condition that indicates the recommended action a processor should take when it encounters the exception condition. The manner in which a PTOCA receiver processes exception conditions depends upon the controlling environment. For any PTOCA exception condition the controlling environment may provide its own identifier, which overrides the PTOCA exception condition code. The controlling environment also may provide its own action, which overrides the PTOCA standard action.

## Presentation Text Data

Presentation Text data contains the graphic characters and the control sequences necessary to position the characters within the object space. The data consists of:
*   graphic characters to be presented
*   control sequences that position them
*   modal control sequences that adjust the positions by small amounts
*   other functions which cause the text output to be presented with differences in appearance

The graphic characters are expected to conform to a font representation so that they can be translated from the code point in the object data to the character in the font. The units of measure for linear displacements are derived from the Presentation Text Data Descriptor or from the hierarchical defaults.

## Control Sequence Summary Descriptions

The following pages contain summary descriptions of the PTOCA control sequences. Summary tables are provided following the descriptions. Please see "Control Sequence Detailed Descriptions" for detailed descriptions of syntax, semantics, and pragmatics.

### Absolute Move Baseline (AMB)
Establishes the baseline and the current presentation position at a new B-axis coordinate, Bcnew, which is a specified number of measurement units from the I-axis. There is no change to the current I-axis coordinate, Ic.

### Absolute Move Inline (AMI)
Establishes the current presentation position on the baseline at a new I-axis coordinate, Icnew, which is a specified number of measurement units from the B-axis. There is no change to the current B-axis coordinate, Bc.

### Begin Line (BLN)
Establishes the current presentation position on the baseline with the new I-axis coordinate, Icnew, equal to the inline margin, and the new B-axis coordinate, Bcnew, increased by the amount of the baseline increment from Bc. The baseline increment is established by the Set Baseline Increment control sequence.

### Begin Suppression (BSU)
Marks the beginning of a field of presentation text, identified by a local identifier (LID), which is not to be presented when the LID is activated in the controlling environment. This control sequence does not alter the effects of other control sequences within it, except that graphic characters and rules are not presented.

<!-- Page 40 -->

Suppression of presentation text by more than one control sequence at a time is not allowed; that is, nesting of suppression control sequences is not allowed.

### Draw B-axis Rule (DBR)
Draws a line of specified length and specified width in the B-direction from the current presentation position. The location of the current presentation position is unchanged.

### Draw I-axis Rule (DIR)
Draws a line of specified length and specified width in the I-direction from the current presentation position. The location of the current presentation position is unchanged.

### Encrypted Data (ENC)
Specifies a sequence of encrypted bytes that must be decrypted into a corresponding character string before standard text processing can be performed.

### End Suppression (ESU)
Marks the end of a field of presentation text, identified by a LID, which is not to be presented when the LID is activated by the controlling environment.

### Glyph Advance Run (GAR)
Specifies the displacement of glyphs along the current baseline.

### Glyph ID Run (GIR)
Specifies the IDs of glyphs to be placed along the current baseline.

### Glyph Layout Control (GLC)
Specifies control information for the start of one or more glyph runs along the current baseline.

### Glyph Offset Run (GOR)
Specifies offsets of glyphs above or below the current baseline.

### No Operation (NOP)
Specifies a string of bytes that are to be ignored.

### Overstrike (OVS)
Specifies a text field that is to be overstruck with a specified graphic character. The overstrike function is initiated by an OVS control sequence with a non-zero bypass identifier, and is terminated by an OVS control sequence with a zero-value bypass identifier. The fields may not be nested or overlapped. The bypass identifier controls which portions of a line are to be overstruck; this provides for bypassing white space created by AMI, RMI, and space characters.

<!-- Page 41 -->

### Relative Move Baseline (RMB)
Establishes the presentation position on the baseline at a new B-axis coordinate, Bcnew, which is a specified number of measurement units from the current B-axis coordinate, Bc. There is no change to the current I-axis coordinate, Ic.

### Relative Move Inline (RMI)
Establishes the presentation position on the baseline at a new I-axis coordinate, Icnew, which is a specified number of measurement units from the current I-axis position, Ic. There is no change to the current B-axis coordinate, Bc.

### Repeat String (RPS)
Specifies a string of characters that are to be repeated until the number of bytes in the graphic characters presented is equal to a specified number of bytes. The string is not checked for control sequences. When the specified number of bytes is equal to the number of bytes in the characters in the data parameter, this control sequence is identical in function to the Transparent Data control sequence.

### Set Baseline Increment (SBI)
Specifies the value of the increment to be added to the B-axis coordinate of the current presentation position, Bc, when a Begin Line control sequence is processed.

### Set Coded Font Local (SCFL)
Specifies a LID to be used as an index into the font map of the controlling environment to determine which coded font, character rotation, and font modification parameters have been selected for use in the object.

### Set Encrypted Alternate (SEA)
Specifies the alternate text used (should the decryption fail) for all Encrypted Data (ENC) control sequences that follow.

### Set Extended Text Color (SEC)
Specifies a color value and defines the color space and encoding for that value. Supports spot (highlight) colors and process colors. The specified color value is applied to foreground areas of the text presentation space, that is, characters, rules, and underscores.

### Set Inline Margin (SIM)
Specifies the value to be used as the new I-axis coordinate, Icnew, of the new presentation position after a Begin Line control sequence is processed. The new presentation position is the addressable position nearest to the B-axis at which the character reference point of a graphic character may be placed.

### Set Intercharacter Adjustment (SIA)
Specifies the increment to be added to or subtracted from the I-axis coordinate of the current presentation position, Ic. The direction parameter indicates whether to add or subtract the increment. If the direction is positive, the increment is added; if negative, the increment is subtracted. This control sequence may be used to compress or expand words for emphasis, improved appearance, or justification.

<!-- Page 42 -->

### Set Key Information (SKI)
Specifies the encryption key information used to decrypt data for all Encrypted Data (ENC) control sequences that follow.

### Set Text Color (STC)
Specifies a named color value to be applied to foreground areas of the text presentation space, that is, characters, rules, and underscores. The values of the foreground color parameter serve as indexes into the color-value table found in Table 13.

### Set Text Orientation (STO)
Establishes the positive I-axis orientation as an angular displacement from the Xp-axis, determining the I-direction. This control sequence also establishes the positive B-axis orientation as an angular displacement from the Xp-axis, determining the B-direction.

The I-axis must be parallel to one of the Xp, Yp coordinate axes and the B-axis must be parallel to the other. The determination of the orientation and direction of the I-axis and B-axis places the origin of the I, Bcoordinate system at one of the corners of the rectangular object space.

### Set Variable Space Character Increment (SVI)
Specifies the increment to be used as the character increment for the character identified as the Variable Space Character by the font or by the controlling environment. This increment is added to the I-axis coordinate of the current presentation position, Ic, when the Variable Space Character code point is processed in order to establish the new presentation position. This has no effect on the B-axis coordinate value.

### Temporary Baseline Move (TBM)
Specifies a temporary movement of the current baseline away from the established baseline. The established baseline B-axis coordinate is maintained until a Temporary Baseline Move control sequence occurs. Temporary moves are made by the amount of the temporary baseline increment in one of three ways:
*   **Above**: Direction parameter = 3
*   **Below**: Direction parameter = 2
*   **Back to the established baseline**: Direction parameter = 1

The temporary baseline function is terminated by a TBM control sequence which returns the temporary baseline to the same B-axis coordinate as that of the established baseline.

### Transparent Data (TRN)
Specifies a string of characters that are to be presented, but not checked for control sequences.

### Underscore (USC)
Specifies a text field that is to be underscored. The underscore function is initiated by an Underscore control sequence with a non-zero bypass identifier, and is terminated by a USC control sequence with a bypass identifier of zero. The fields may not be nested or overlapped. The bypass identifier controls which portions of a line are to be underscored; this provides for bypassing white space created by AMI, RMI, and space characters.

<!-- Page 43 -->

### Unicode Complex Text (UCT)
Identifies a sequence of Unicode code points that can be processed as Unicode complex text. The sequence starts with the first byte following the end of the UCT control sequence and ends with the last byte identified by the complex text length parameter in the control sequence. Rendering complex text involves bidirectional (bidi) layout processing and glyph processing.

**Table 4. Summary of PTOCA Control Sequences**

| Control Sequence Name | Unchained Function Type | Chained Function Type |
| :--- | :---: | :---: |
| **Inline Controls** | | |
| "Set Inline Margin (SIM)" | X'C0' | X'C1' |
| "Set Intercharacter Adjustment (SIA)" | X'C2' | X'C3' |
| "Set Variable Space Character Increment (SVI)" | X'C4' | X'C5' |
| "Absolute Move Inline (AMI)" | X'C6' | X'C7' |
| "Relative Move Inline (RMI)" | X'C8' | X'C9' |
| **Baseline Controls** | | |
| "Set Baseline Increment (SBI)" | X'D0' | X'D1' |
| "Absolute Move Baseline (AMB)" | X'D2' | X'D3' |
| "Relative Move Baseline (RMB)" | X'D4' | X'D5' |
| "Begin Line (BLN)" | X'D8' | X'D9' |
| "Set Text Orientation (STO)" | X'F6' | X'F7' |
| **Controls for Data Strings** | | |
| "Unicode Complex Text (UCT)" | X'6A' | — |
| "Glyph Layout Control (GLC)" | X'6D' | — |
| "Glyph ID Run (GIR)" | X'8B' | — |
| "Glyph Advance Run (GAR)" | X'8C' | X'8D' |
| "Glyph Offset Run (GOR)" | X'8E' | X'8F' |
| "Encrypted Data (ENC)" | X'98' | X'99' |
| "Set Encrypted Alternate (SEA)" | X'9C' | X'9D' |
| "Transparent Data (TRN)" | X'DA' | X'DB' |
| "Repeat String (RPS)" | X'EE' | X'EF' |
| "No Operation (NOP)" | X'F8' | X'F9' |
| **Controls for Rules** | | |
| "Draw I-axis Rule (DIR)" | X'E4' | X'E5' |
| "Draw B-axis Rule (DBR)" | X'E6' | X'E7' |
| **Character Controls** | | |
| "Set Text Color (STC)" | X'74' | X'75' |
| "Set Extended Text Color (SEC)" | X'80' | X'81' |

<!-- Page 44 -->

**Table 4 Summary of PTOCA Control Sequences (cont'd.)**

| Control Sequence Name | Unchained Function Type | Chained Function Type |
| :--- | :---: | :---: |
| "Set Key Information (SKI)" | X'9A' | X'9B' |
| "Set Coded Font Local (SCFL)" | X'F0' | X'F1' |
| "Begin Suppression (BSU)" | X'F2' | X'F3' |
| "End Suppression (ESU)" | X'F4' | X'F5' |
| **Field Controls** | | |
| "Overstrike (OVS)" | X'72' | X'73' |
| "Underscore (USC)" | X'76' | X'77' |
| "Temporary Baseline Move (TBM)" | X'78' | X'79' |

<!-- Page 45 -->

**Table 5. Explanation of Symbols Used in Tables**

| Symbol | Meaning |
| :--- | :--- |
| Ic | Current inline addressable position |
| Bc | Current baseline addressable position |
| Icnew | New current inline addressable position |
| Bcnew | New current baseline addressable position |
| Io | Inline addressable position at origin |
| Bo | Baseline addressable position at origin |
| Ih | Initial I-axis coordinate established by data stream |
| Bh | Initial B-axis coordinate established by data stream |
| Imargin | I-axis coordinate at left margin |
| Best | Established B-axis coordinate |
| CI | Character increment specified by font resource |
| ADJSTMNT | Intercharacter adjustment (increment or decrement) |
| VSI | Variable Space Character increment |
| CHAR | Any character with CI > 0 (non-null character) |
| NULLCHAR | Any character with CI = 0 (null character) |

**Table 6. Summary of Directive Control Sequences**

| Control Sequence Name | Mnemonic | Parameter | Effect |
| :--- | :--- | :--- | :--- |
| Absolute Move Baseline | AMB | DSPLCMNT | Bcnew = Bo + DSPLCMNT |
| Absolute Move Inline | AMI | DSPLCMNT | Icnew = Io + DSPLCMNT |
| Begin Line | BLN | None | Icnew = Imargin, Bcnew = Bc + INCRMENT, Best = Best + INCRMENT |
| Begin Suppression | BSU | LID | Do not present text following this control through next ESU with same LID, if LID is active at controlling environment level. |
| Draw B-Axis Rule | DBR | RLENGTH, RWIDTH | Draw rule in B-direction from Bc to Bc + RLENGTH. Rule width = RWIDTH. Ic and Bc are unchanged. |
| Draw I-Axis Rule | DIR | RLENGTH, RWIDTH | Draw rule in I-direction from Ic to Ic + RLENGTH. Rule width = RWIDTH. Ic and Bc are unchanged. |
| Encrypted Data | ENC | ENCDATA | Encrypted bytes that must be decrypted into text characters for standard text processing. |
| End Suppression | ESU | LID | End suppression of characters if same LID as preceding BSU. |

<!-- Page 46 -->

**Table 6 Summary of Directive Control Sequences (cont'd.)**

| Control Sequence Name | Mnemonic | Parameter | Effect |
| :--- | :--- | :--- | :--- |
| Glyph Advance Run | GAR | ADVANCE | Specifies the displacement of glyphs along the current baseline. |
| Glyph ID Run | GIR | GLYPHID | Specifies the IDs of glyphs to be placed along the current baseline. |
| Glyph Layout Control | GLC | IADVNCE, OIDLGTH, FFNLGTH, FONTOID, FFONTNME | Specifies control information for the start of one or more glyph runs along the current baseline. |
| Glyph Offset Run | GOR | OFFSET | Specifies offsets of glyphs above or below the current baseline. |
| No Operation | NOP | IGNDATA | Ignore bytes IGNDATA. No change to Ic or Bc. |
| Relative Move Baseline | RMB | INCRMENT | Bcnew = Bc + INCRMENT |
| Relative Move Inline | RMI | INCRMENT | Icnew = Ic + INCRMENT |
| Repeat String | RPS | RLENGTH, RPTDATA | Repeat RPTDATA until RLENGTH bytes from RPTDATA have been presented. |
| Transparent Data | TRN | TRNDATA | Process all code points in TRNDATA as characters. |
| Unicode Complex Text | UCT | UCTVERS, CTLNGTH, CTFLGS, BIDICT, GLYPHCT, ALTIPOS | Process the next CTLNGTH Unicode code points as complex text. |

**Table 7. Summary of Modal Control Sequences**

| Control Sequence Name | Mnemonic | Parameter | Effect |
| :--- | :--- | :--- | :--- |
| Set Baseline Increment | SBI | INCRMENT | Upon execution of BLN, Bcnew = Bc + INCRMENT. |
| Set Coded Font Local | SCFL | LID | Establish active font, character rotation, and font modification parameters. |
| Set Encrypted Alternate | SEA | ALTTEXT | Sets the alternate text to be used if the decryption of encrypted bytes in the Encrypted Data (ENC) control sequences that follow should fail. |
| Set Extended Text Color | SEC | COLSPCE, COLSIZE1, COLSIZE2, COLSIZE3, COLSIZE4, COLVALUE | Set process color and highlight color for text, rules, and underscores. |

<!-- Page 47 -->

**Table 7 Summary of Modal Control Sequences (cont'd.)**

| Control Sequence Name | Mnemonic | Parameter | Effect |
| :--- | :--- | :--- | :--- |
| Set Intercharacter Adjustment | SIA | ADJSTMNT, DIRCTION | If current character follows another character, Icnew = Ic +/- ADJSTMNT. Present character: Icnew = Ic + character increment. DIRCTION = 0 means ADJSTMNT is positive, DIRCTION = 1 means ADJSTMNT is negative. |
| Set Inline Margin | SIM | DSPLCMNT | Upon execution of BLN, Icnew = Io + DSPLCMNT = Imargin. |
| Set Key Information | SKI | KEYINFO | Sets the encryption key information used to decrypt data for all Encrypted Data (ENC) control sequences that follow. |
| Set Text Color | STC | FRGCOLOR | Set named color for text, rules, and underscores. |
| Set Text Orientation | STO | IORNTION, BORNTION | Establish angle of I-axis and B-axis with respect to Xp-axis. |
| Set Variable Space Character Increment | SVI | INCRMENT | Establish character increment of Variable Space Character. |

**Table 8. Summary of Field Control Sequences**

| Control Sequence Name | Mnemonic | Parameter | Effect |
| :--- | :--- | :--- | :--- |
| Overstrike | OVS | BYPSIDEN, OVERCHAR | Overstrike following text with OVERCHAR. BYPSIDEN controls overstrike of white space. BYPSIDEN = 0 terminates. Baseline reference is Bc. |
| Underscore | USC | BYPSIDEN | Underscore following text. BYPSIDEN controls underscore of white space. BYPSIDEN = 0 terminates. Baseline reference is Best. |
| Temporary Baseline Move | TBM | DIRCTION, PRECSION, INCRMENT | Create temporary baseline at Bcnew = Bc + INCRMENT. Best is unchanged. |

## Presentation Text Data Descriptor

The Presentation Text Data Descriptor specifies the units of measure for the Presentation Text object space, the size of the Presentation Text object space, and the initial values for modal parameters, called initial text conditions. Initial values not provided are defaulted by the controlling environment or the receiving device. The Presentation Text Data Descriptor provides the following initial values:
*   Unit base
*   Xp-units per unit base
*   Yp-units per unit base
*   Xp-extent of the presentation space
*   Yp-extent of the presentation space
*   Initial text conditions

<!-- Page 48 -->

The initial text conditions are values provided by the Presentation Text Data Descriptor to initialize the modal parameters of the control sequences. Modal control sequences typically are characterized by the word *set* in the name of the control sequence. Modal parameters are identified as such in their semantic descriptions.

### Initial Text Condition Summary Descriptions

The initial text conditions include the following parameters:
*   Baseline increment
*   Extended text color
*   Coded font local ID
*   Initial baseline coordinate
*   Initial inline coordinate
*   Inline margin
*   Intercharacter adjustment
*   Text color
*   Text orientation

The following pages contain summary descriptions of the initial text conditions. Please refer to "Objects" for detailed descriptions of semantics and pragmatics. Also see the corresponding control sequence, if appropriate, for additional information.

#### Baseline Increment
Specifies the value to be used for the increment parameter of the Set Baseline Increment control sequence. This increment represents the number of measurement units to be added to the B-axis coordinate of the current presentation position, Bc, when a Begin Line control sequence is processed. The current I-axis coordinate, Ic, is unchanged. The default value is the Default Baseline Increment associated with the default coded font of the device.

#### Coded Font Local ID
Specifies the value to be used as the LID in the Set Coded Font Local control sequence. This LID represents an index into a font map of the controlling environment used in the determination of which font, character rotation, and font modification parameters have been selected for use in the object. The default value is the LID of the default font of the device.

#### Extended Text Color
Specifies a foreground spot (highlight) color or process color to be used to present graphic characters, rules, and underscores.

#### Initial Baseline Coordinate
Specifies the value of the current presentation position B-axis coordinate, Bc. This value represents the displacement in the B-direction from the I-axis for the initial position for presentation of graphic characters or processing of control sequences. The default value is device-dependent.

#### Initial Inline Coordinate
Specifies the value of the current presentation position I-axis coordinate, Ic. This value represents the displacement in the I-direction from the B-axis for the initial position for presentation of graphic characters or processing of control sequences. The default value is zero.

<!-- Page 49 -->

#### Inline Margin
Specifies the value to be used for the displacement parameter of the Set Inline Margin control sequence. This value represents the I-axis coordinate of the presentation position nearest to the B-axis after a Begin Line control sequence is processed. The default value is zero.

#### Intercharacter Adjustment
Specifies the value to be used for the adjustment parameter of the Set Intercharacter Adjustment control sequence. This value represents the number of measurement units by which the I-axis coordinate of the current presentation position is adjusted when the SIA control sequence is processed. The direction of the adjustment is determined by the direction parameter. If the direction is positive, the adjustment is added; if negative, the adjustment is subtracted. The default value is zero for both the adjustment parameter and the direction parameter.

#### Text Color
Specifies a foreground named color value to be used to present text, rules, and underscores. A foreground color parameter value represents an index into the color-value table in Table 13. The default value is X'FF07'.

#### Text Orientation
Specifies the angular displacement values to be used for the I-axis orientation and the B-axis orientation parameters of the Set Text Orientation control sequence. The I-axis value represents the positive I-axis orientation as an angular displacement from the Xp-axis, and the resultant I-direction. The B-axis value represents the positive B-axis orientation as an angular displacement from the Xp-axis, and the resultant B-direction. The default value for the I-axis is X'0000', that is, zero degrees. The default value for the B-axis is X'2D00', that is, 90 degrees.

<!-- Page 50, 51 -->
