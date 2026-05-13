# Chapter 3. AFP GOCA Overview

This chapter gives an overview of AFP GOCA, and describes:
*   The concept of the graphics processor
*   The environment interface
*   The drawing processor, including:
    *   Primitives
    *   Drawing orders
    *   Attributes
*   Graphics coordinate spaces
*   Color
*   Mix
*   Segments
*   Subsetting
*   Exception Conditions

## The Graphics Processor Model

GOCA is based on the concept of a graphics processor (GP). This processor is embedded into different controlling environments. Some typical controlling environments are:
*   Intelligent Printer Data Stream (IPDS) printers with graphics capability
*   Mixed Object Document Content Architecture (MO:DCA) data streams for interchange

AFP GOCA deals with GOCA objects that are created, interchanged, archived, and presented within these two controlling environments.

The graphics processor contains the following processing components:
*   Environment interface
*   Drawing processor

---

Figure 4 shows the components and connections of the graphics processor.

### Figure 4. The Graphics Processor (GP) Within the Controlling Environment

*   **To/from controlling environment**
*   **Environment Interface**
*   **The GRAPHICS PROCESSOR (GP)**
    *   **GP Storage**
    *   **Drawing Processor**
    *   **Resources**
        *   Color Tables
        *   Coded Fonts
    *   **Graphics Presentation Space (GPS)**
*   The GPS (containing the graphics picture) is merged onto the presentation surface in a manner dependent on the controlling environment.

---

## The Environment Interface

The environment interface performs the functions required to interface the graphics processor with the controlling environment and is responsible for examining the data passed to it from the controlling environment.

Three types of control structures are passed from the controlling environment to the graphics processor via the environment interface:
*   **Commands:** The only command supported in AFP GOCA is the Begin Segment (X'70') command, which is used to define a segment.
*   **Control Instructions:** The only control instruction supported in AFP GOCA is the Set Current Defaults (X'21') instruction, which sets the current default values of selected attributes.
*   **Drawing Orders:** These orders comprise the segment data. They generate graphics primitives in the Graphics Presentation Space (GPS) and set their attributes.

## Drawing Processor

The graphics picture is drawn in the GPS by the drawing processor, which executes a sequence of drawing orders. The drawing processor is started by the controlling environment, which in AFP GOCA is the MO:DCA or IPDS environment.

Drawing orders whose execution affects the GPS are called primitive drawing orders. These orders cause the designated primitive to be mixed into the GPS. Additional drawing orders set drawing attributes. All drawing orders are sometimes simply referred to as orders.

### Primitives

A primitive is the smallest portion of a picture that can be drawn. There are six types of primitive with their associated set of color and mix attributes:
*   Line primitives
*   Area primitives
*   Character string primitives
*   Marker primitives
*   Pattern primitives
*   Image primitives

A primitive is defined by:
*   The parameters of a primitive drawing order
*   Modal parameters called attributes
*   Control instructions that contain the Set Current Defaults instruction, such as the MO:DCA Graphics Data Descriptor (GDD) and the IPDS Write Graphics Control (WGC)

Modal parameters have values initialized by the environment and can be altered by attribute-setting drawing orders or by control instructions. Modal parameter values persist until they are explicitly altered, or until the end of the graphics object is encountered.

---

### Drawing Orders

Drawing orders are defined for each of the following types of output primitive:

| Primitive | Description |
| :--- | :--- |
| **Line primitives:** | |
| Line and Relative Line | One or more straight lines connected together. |
| Full Arc | A full circle or ellipse. |
| Partial Arc | A line from a point to the start of an arc, then a portion of a full arc. |
| Fillet | A curved line drawn tangentially to a specified set of contiguous straight lines. |
| Cubic Bezier Curve | A third-order curve defined by a group of four points. |
| **Box** | A rectangle drawn with square or round corners. |
| **Area** | One or more closed figures that can be filled. The closed figures can overlap. |
| **Character String** | A series of characters drawn along a baseline starting at a specified point. |
| **Marker** | A symbol positioned by its center, and drawn at one or more points. |
| **Pattern** | A symbol that is repeated to fill an area. |
| **Image** | A rectangular area containing a set of foreground and background points. |

A summary list of the Drawing Orders is given in “Summary List of Orders”.

### Attributes

Primitive attributes specify the characteristics of the output primitives that define the picture to be drawn. The following types of attribute are defined:
*   Drawing attributes
*   Line attributes
*   Character attributes
*   Marker attributes
*   Pattern attributes

#### Drawing Attributes

| Attribute | Meaning |
| :--- | :--- |
| **Color** | The color in which the foreground bits of the output primitive are to be drawn. |
| **Mix** | Affects how the foreground of the output primitive that is being drawn is to be merged with the color information already in the GPS. |
| **Background Mix** | Affects how the background of the output primitive that is being drawn is to be merged with the color information already in the GPS. |

Sets of mix and color attributes are provided for each type of primitive.

#### Line Attributes

| Attribute | Meaning |
| :--- | :--- |
| **Line Type** | The type of line to be drawn; for example, solid or dashed. |
| **Line Width** | The width of line to be drawn; for example, normal or wide. |
| **Line End** | The type of ending of stroked lines; for example, flat, square, or round. |
| **Line Join** | The type of joining of stroked lines; for example, round, bevel, or miter. |

---

#### Character Attributes

| Attribute | Meaning |
| :--- | :--- |
| **Character Precision** | The requested appearance fidelity of a character string. |
| **Character Shear** | The amount of slope of a character string. This attribute is not supported in AFP GOCA. |
| **Character Angle** | The angle between the character baseline and the GPS $X_g$ axis. Only values of 0°, 90°, 180°, and 270° are supported in AFP GOCA. |
| **Character Cell** | The size of the cell in which a character is drawn. |
| **Character Direction** | The direction in which characters are drawn. |
| **Character Set** | The set of symbols from which characters are obtained. |

#### Marker Attributes

| Attribute | Meaning |
| :--- | :--- |
| **Marker Cell** | The size of the cell in which a marker is drawn. |
| **Marker Set** | The set of symbols from which the marker is obtained. |
| **Marker Symbol** | The particular symbol that is to be used to draw markers. |

#### Pattern Attributes

| Attribute | Meaning |
| :--- | :--- |
| **Pattern Set** | The set of symbols from which the area fill pattern is obtained. |
| **Pattern Symbol** | The particular symbol that is to be used as a fill pattern when filling an area. |
| **Pattern Reference Point** | The point that determines the positioning of a custom fill pattern. |

**Note:** The Arc Parameters also specify characteristics of output primitives. In this, they act in a way very similar to attributes, but are conceptually distinct.

## Graphics Coordinate Spaces

Two coordinate spaces or presentation spaces are used in AFP GOCA:
*   Drawing Order Coordinate Space (DOCS)
*   Graphics Presentation Space (GPS)

### Drawing Order Coordinate Space (DOCS)

The DOCS is the coordinate space in which the drawing orders specify graphics primitives. Points are described in the drawing orders by specifying the x and y coordinates in the DOCS. Extents and offsets are described in the drawing orders by specifying the x and y extents and offsets in the DOCS. The DOCS is a standard, 2-dimensional Cartesian coordinate system. Units of measure for the DOCS are specified in the Graphics Data Descriptor. In AFP GOCA, there is a one-to-one mapping between the DOCS coordinate system and its units of measure and the GPS coordinate system and its units of measure. Therefore in AFP GOCA, DOCS and GPS are equivalent coordinate systems. All references to coordinate systems in AFP GOCA will be made with respect to GPS.

### Graphics Presentation Space (GPS)

The GPS is the space in which the application user's view of the specified picture is generated. The GPS is a standard, 2-dimensional Cartesian coordinate system as shown in Figure 5. Coordinates in the GPS coordinate system are denoted by $(X_g, Y_g)$. Units of measure for the GPS are specified in the Graphics Data Descriptor.

### Figure 5. Coordinate System Used for the GPS
```
      +Yg
       ^
       |
-Xg <--+--> +Xg
       |
       v
      -Yg
```

AFP GOCA uses 16-bit signed integers to specify GPS coordinates. A point outside GPS is characterized by a 2-byte arithmetic overflow. For a definition of the geometric parameter format used in AFP GOCA, see “Parameter Type” and “Drawing Order Subset”.

### Usable Area (UA)

The usable area is a presentation space and coordinate system defined by the controlling environment. It is the space in which the implementation presents the picture to the end user, and merges the GPS with other presentation spaces in the device.

The controlling environment defines a GPS window on the GPS, and a graphics window mapping between the GPS window and the UA. In the AFP environment, the usable area is a MO:DCA or IPDS object area, which is merged with other object areas on a logical page presentation space.

## Color

The color values specified using the Set Color and Set Extended Color drawing orders generate an index into the standard color table defined in Table 5. When a primitive is drawn, this color index is mixed with the color index of the GPS using the current mix and background mix attributes. The resulting color index of the GPS can be further modified by drawing another primitive at the same point in the GPS. When drawing is complete, the final color index is used to look up the current color value. The values in the color table control the physical process whereby colors are presented on the presentation surface.

The standard color table is accessed by two-byte color index values. These values are the valid color index values that can be specified in the Set Extended Color order and the Set Color order. The value specified in the Set Color order is prefixed with X'FF' to generate a two-byte color index value.

### Table 5. Standard Color Table

Table 5 shows the meanings of the two-byte values. The table also specifies the RGB values that can be used for each named color, assuming that each component is specified with 8 bits and that the component intensity range 0 to 1 is mapped to the binary value range 0 to 255.

| Value | Color | Red (R) | Green (G) | Blue (B) |
| :--- | :--- | :--- | :--- | :--- |
| X'0000' or X'FF00' | Drawing default | - | - | - |
| X'0001' or X'FF01' | Blue | 0 | 0 | 255 |
| X'0002' or X'FF02' | Red | 255 | 0 | 0 |
| X'0003' or X'FF03' | Pink/magenta | 255 | 0 | 255 |
| X'0004' or X'FF04' | Green | 0 | 255 | 0 |
| X'0005' or X'FF05' | Turquoise/cyan | 0 | 255 | 255 |
| X'0006' or X'FF06' | Yellow | 255 | 255 | 0 |
| X'0007' | White; see Note 1 | 255 | 255 | 255 |
| X'0008' | Black | 0 | 0 | 0 |
| X'0009' | Dark blue | 0 | 0 | 170 |
| X'000A' | Orange | 255 | 128 | 0 |
| X'000B' | Purple | 170 | 0 | 170 |
| X'000C' | Dark green | 0 | 146 | 0 |
| X'000D' | Dark turquoise | 0 | 146 | 170 |
| X'000E' | Mustard | 196 | 160 | 32 |
| X'000F' | Gray | 131 | 131 | 131 |
| X'0010' | Brown | 144 | 48 | 0 |
| X'FF07' | Presentation-process default; see Note 2 | - | - | - |
| X'FF08' | Color of medium | - | - | - |
| All others | Reserved | - | - | - |

**Notes:**
1. The color rendered on presentation devices that do not support white is device dependent. For example, some printers simulate with color of medium, which results in white if white media is used.
2. The presentation-process default specified by X'FF07' is resolved as the presentation device default. This color value is also known in GOCA as neutral white for compatibility with display devices.
3. While the RGB values in the table can be used to render the OCA named colors, many implementations are and have been device dependent. Nevertheless, it is recommended that OCA Black (X'0008') be rendered as C = M = Y = X'00', and K = X'FF'.

The standard color table is equivalent to the Standard OCA Color Value Table defined in the MO:DCA controlling environment; see the Mixed Object Document Content Architecture (MO:DCA) Reference for the definition of this table.

Colors may also be specified using the Set Process Color drawing order. This order supports the specification of:
*   Process colors, using the RGB, CMYK, and CIELAB color spaces.
*   Spot colors, using the highlight color space.
*   Named colors, using the standard OCA color space. This is the color space that is supported by the Set Color and Set Extended Color drawing orders. For definitions of the color values used in this color space, see Table 5.

**Note:** When the standard OCA color space is selected with the Set Process Color drawing order, colors for foreground data are mixed into the GPS in the same manner as described for the Set Color and Set Extended Color orders. However, when any other color space is selected, colors for foreground data always overpaint the GPS.

## Mix

If two output primitives drawn into the GPS have a common point, they are mixed at that point to produce a result that is held at that point. The output primitives exist independently in segments, but do not exist independently in the GPS. There is no concept of the GPS having layers with the output primitives underlying and overlying one another at points of the space.

### Table 6. Foreground/Background in Graphics Presentation Space

| Data Type | Foreground | Background |
| :--- | :--- | :--- |
| **AFP GOCA Graphics** | *   Stroked area of lines (including arcs)<br>*   Stroked and filled portion of pattern symbols<br>*   Stroked and filled portion of marker symbols<br>*   Stroked and filled portion of graphic characters<br>*   B'1' image points<br>*   Entire area with solid fill | Everything else |

Mixing applies only to those points of the GPS to which an output primitive is being drawn. The GPS always contains the result of the mixing of the output primitives currently drawn in the GPS. When a new output primitive is drawn into the GPS, each foreground or background point of the output primitive is combined with the corresponding point of the GPS to produce a new result in the GPS. Mixing is always an effect of a foreground or a background value of an output primitive on an existing GPS value.

Table 6 summarizes the definition of foreground and background in the GPS.

**Note:** A custom pattern or gradient is mixed into the GPS in the same way as are patterns in the default pattern set: only stroked and filled portions of the custom pattern or gradient are foreground. For gradients, however, the entire gradient, as well as any outside areas for which the Outside value was not specified as “None”, is considered stroked and filled, even if the color white occurs.

**Implementation Note:** If a color fill of an area is simulated with a pattern fill, the complete fill is considered foreground, not just the stroked and filled portion of the pattern symbols.

The attributes of mix and background mix specify the method by which the output-primitive color value is combined with the existing color value of each point of the GPS. These two mixing capabilities are not always the same mixing attribute value. For example, assume that the GPS contains a line on which the controlling environment wants to mix a character A, such that the background of that character does not interfere with the line. The application chooses Overpaint for the foreground mix attribute of the character and Leave Alone for the background mix attribute of the character.

Every point of the GPS is background until points are drawn in GPS. The new color value of the current point of the GPS is obtained by applying the appropriate mix attribute to the existing value for that point with the appropriate, foreground or background, color value for the corresponding point of the output primitive being applied.

The mix attributes are selected by use of the Set Mix or Set Background Mix orders.

In the description that follows, the term **source** means the foreground, or background, of the primitive that is being drawn. The term **destination** means the area of the GPS on which the foreground or background of that primitive is being drawn.

The supported values of the **foreground mix** attributes are:
*   **X'00' Drawing default:** This resets the mix attribute to its initial value.
*   **X'02' Overpaint:** The color value of the source replaces the color value of the destination. This is also sometimes referred to as opaque mixing.

The supported values of the **background mix** attributes are:
*   **X'00' Drawing default:** This resets the mix attribute to its initial value.
*   **X'05' Leave Alone:** The color value of the destination is unchanged. This is also sometimes referred to as transparent mixing.

## Segments

Segments are self-contained collections of drawing orders and attributes. They are the basic units from which a picture is constructed. A segment can be given a name defined as a four-byte unsigned integer; however, this name is ignored in AFP GOCA.

Facilities are provided to permit the chaining of segments during the process of describing a complete picture. Chaining is the unidirectional passing of control from one segment to another segment.

Every segment is either chained or unchained. A collection of one or more chained segments defines the picture to be drawn. Unchained segments are ignored in AFP GOCA. Chaining provides a known and architected initial state for the chained segments. Therefore, chained segments are completely independent pieces of the picture.

## Subsetting

GOCA supports the functional requirements of a wide spectrum of graphics devices in a number of different environments. To efficiently support this range of capabilities, GOCA defines subsets of functionality. The subsets described in this manual for AFP GOCA are labeled as follows:
*   **Drawing Level 2 Version 0 (DR/2V0).** The DR/2V0 subset is also referred to as “GRS2”.
*   **Graphics Subset Level 3 (GRS3).** The GRS3 subset includes additional functionality above DR/2V0.

These subsets are supported in both the MO:DCA and IPDS environments. See Chapter 9, “Compliance” for details of these subsets.

## Exception Conditions

Exception conditions are defined by AFP GOCA for detectable errors in the syntax of GOCA constructs. They are reported to the controlling environment in an environment dependent manner.

If the environment determines that processing can proceed, then for some of the exception conditions, AFP GOCA defines a standard action that is to be taken after the error is detected. For the other exception conditions, the environment must determine the continuation procedure.

---

Copyright © AFP Consortium 1997, 2017 19
