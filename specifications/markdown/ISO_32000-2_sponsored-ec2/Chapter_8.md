8 Graphics

8.1 General

The graphics operators used in PDF content streams describe the appearance of pages that are to be
reproduced on an output device. The facilities described in this clause are intended for both printer
and display applications.

The graphics operators form six main groups:

| • | Graphics state operators manipulate the data structure called the graphics state, the global |

framework within which the other graphics operators execute. The graphics state includes the
current transformation matrix (CTM), which maps user space coordinates used within a PDF
content stream into output device coordinates. It also includes the current colour, the current
clipping path, and many other parameters that are implicit operands of the painting operators.
| • | Path construction operators specify paths, which define shapes, line trajectories, and regions of |

various sorts. They include operators for beginning a new path, adding line segments and curves
to it, and closing it.
| • | Path-painting operators fill a path with a colour, paint a stroke along it, or use it as a clipping |

boundary.
| • | Other painting operators paint certain self-describing graphics objects. These include sampled |

images, geometrically defined shadings, and entire content streams that in turn contain sequences
of graphics operators.
| • | Text operators select and show character glyphs from fonts (descriptions of typefaces for |

representing text characters). Because PDF treats glyphs as general graphical shapes, many of the
text operators could be grouped with the graphics state or painting operators. However, the data
structures and mechanisms for dealing with glyph and font descriptions are sufficiently
specialised that clause 9, "Text" focuses on them.
| • | Marked-content operators associate higher-level logical information with objects in the content |

stream. This information does not affect the rendered appearance of the content (although it may
determine if the content should be presented at all; see 8.11, "Optional content"); it is useful to
applications that use PDF for document interchange. Marked-content is described in 14.6,
"Marked content".
This clause presents general information about device-independent graphics in PDF: how a PDF
content stream describes the abstract appearance of a page. Rendering — the device-dependent part of
graphics — is covered in clause 10, "Rendering". The Bibliography lists a number of books that give
details of these computer graphics concepts and their implementation.

8.2 Graphics objects

As discussed in 7.8.2, "Content streams", the data in a content stream shall be interpreted as a
sequence of operators and their operands, expressed as basic data objects according to standard PDF
syntax. A content stream can describe the appearance of a page, or it can be treated as a graphical
element in certain other contexts.

The operands and operators shall be written sequentially using postfix notation. Although this notation
resembles the sequential execution model of the PostScript language, a PDF content stream is not a
program to be interpreted; rather, it is a static description of a sequence of graphics objects. There are


## Page 161

specific rules, described below, for writing the operands and operators that describe a graphics object.

PDF provides five types of graphics objects:

| • | A path object is an arbitrary shape made up of straight lines, rectangles, and cubic Bézier curves. A |

path may intersect itself and may have disconnected sections and holes. A path object ends with
one or more painting operators that specify whether the path shall be stroked, filled, used as a
clipping boundary, or some combination of these operations.
| • | A text object consists of one or more character strings that identify sequences of glyphs to be |

painted. Like a path, text can be stroked, filled, or used as a clipping boundary.
| • | An external object (XObject) is an object defined outside the content stream and referenced as a |

named resource (see 7.8.3, "Resource dictionaries"). The interpretation of an XObject depends on
its type. An image XObject defines a rectangular array of colour samples to be painted; a form
XObject is an entire content stream to be treated as a single graphics object. Specialised types of
form XObjects shall be used to import content from one PDF file into another (reference XObjects)
and to group graphical elements together as a unit for various purposes (group XObjects). In
particular, the latter are used to define transparency groups for use in the transparent imaging
model (transparency group XObjects, discussed in detail in clause 11, "Transparency").
| • | An inline image object uses a special syntax to express the data for a small image directly within |

the content stream.
| • | A shading object describes a geometric shape whose colour is an arbitrary function of position |

within the shape. (A shading can also be treated as a colour when painting other graphics objects;
it is not considered to be a separate graphics object in that case.)
PDF 1.3 and earlier versions use an opaque imaging model in which each graphics object is painted in
sequence, completely obscuring any previous marks it may overlay on the page. PDF 1.4 introduced a
transparent imaging model in which objects can be less than fully opaque, allowing previously painted
| marks to show through. Each object is painted on the page with a specified | opacity, which may be |

constant at every point within the object’s shape or may vary from point to point. The previously
existing contents of the page form a backdrop with which the new object is composited, producing
results that combine the colours of the object and backdrop according to their respective opacity
characteristics. The objects at any given point on the page form a transparency stack, where the
stacking order is defined to be the order in which the objects shall be specified, bottommost object
first. All objects in the stack can potentially contribute to the result, depending on their colours, shapes,
and opacities.

PDF’s graphics parameters are so arranged that objects shall be painted by default with full opacity,
reducing the behaviour of the transparent imaging model to that of the opaque model. Accordingly, the
material in this clause applies to both the opaque and transparent models except where explicitly
| stated otherwise; the transparent model is described in its full generality in clause | 11, "Transparency". |

Although the painting behaviour described above is often attributed to individual operators making up
an object, it is always the object as a whole that is painted. "Figure 9 — Graphics objects" shows the
ordering rules for the operations that define graphics objects. Only those operators that are listed in
"Figure 9 — Graphics objects" for each type of graphics object or in the intervals between graphics
objects (called the content stream level in the figure) shall be used in that context. Every content stream
begins at the content stream level, where changes may be made to the graphics state, such as colours
and text attributes, as discussed in the following subclauses.

In "Figure 9 — Graphics objects", arrows indicate the operators that mark the beginning or end of each


## Page 162

type of graphics object. Some operators are identified individually, others by general category. "Table
50 — Operator categories" summarises these categories for all PDF operators.

Table 50 — Operator categories

| Category | Operators | Location |

| General graphics state | w, J, j, M, d, ri, i, gs, q, Q | "Table 56 — Graphics state operators" |

| Special graphics state | cm | "Table 56 — Graphics state operators" |

| Path construction | m, l, c, v, y, h, re | "Table 58 — Path construction operators" |

| Path painting | S, s, f, F, f*, B, B*, b, b*, n | "Table 59 — Path-painting operators" |

| Clipping paths | W, W* | "Table 60 — Clipping path operators" |

| Text objects | BT, ET | "Table 105 — Text object operators" |

| Text state | Tc, Tw, Tz, TL, Tf, Tr, Ts | "Table 103 — Text state operators" |

| Text positioning | Td, TD, Tm, T* | "Table 106 — Text-positioning operators" |

| Text showing | Tj, TJ, ', " | "Table 107 — Text-showing operators" |

| Type 3 fonts | d0, d1 | "Table 111 — Type 3 font operators" |

| Colour | CS, cs, SC, SCN, sc, scn, G, g, | "Table 73 — Colour operators" RG, rg, K, k |

| Shading patterns | Sh | "Table 76 — Shading operator" |

| Inline images | BI, ID, EI | "Table 90 — Inline image operators" |

| XObjects | Do | "Table 86 — XObject operator" |

| Marked-content | MP, DP, BMC, BDC, EMC | "Table 351 — Entries in a data dictionary" |

| Compatibility | BX, EX | "Table 33 — Compatibility operators" |


## Page 163

Figure 9 — Graphics objects

EXAMPLE             The path construction operators m and re signal the beginning of a path object. Inside the path object,
additional path construction operators are permitted, as are the clipping path operators W and W*, but not
general graphics state operators such as w or J. A path-painting operator, such as S or f, ends the path object
and returns to the content stream level.

NOTE 1        "Table 50 — Operator categories" and "Figure 9 — Graphics objects" were updated in this
document (2020).
NOTE 2        A PDF reader can process a content stream whose operations violate these rules for describing
graphics objects and can produce unpredictable behaviour, even though it can display and print
the stream correctly. PDF processors that attempt to extract graphics objects for editing or other
purposes often depend on the objects being well formed. The rules for graphics objects are also
important for the proper interpretation of marked-content (see 14.6, "Marked content").
| A graphics object also implicitly includes all graphics state para | meters that affect its behaviour. For |

instance, a path object depends on the value of the current colour parameter at the moment the path


## Page 164

object is defined. The effect shall be as if this parameter were specified as part of the definition of the
path object. However, the operators that are invoked at the content stream level to set graphics state
parameters shall not be considered to belong to any particular graphics object. Graphics state
parameters should be specified only when they change. A graphics object can depend on parameters
that were defined much earlier.

Similarly, the individual character strings within a text object implicitly include the graphics state
parameters on which they depend. Most of these parameters may be set inside or outside the text
object. The effect is as if they were separately specified for each text string.

The important point is that there is no semantic significance to the exact arrangement of graphics state
operators. When processing a PDF content stream a PDF processor may change an arrangement of
graphics state operators to any other arrangement that achieves the same values of the relevant
graphics state parameters for each graphics object. PDF processors shall not infer any higher-level
logical semantics from the arrangement of tokens constituting a graphics object. A separate
mechanism, marked-content (see 14.6, "Marked content"), allows such higher-level information to be
explicitly associated with the graphics objects.

8.3 Coordinate systems

8.3.1 General

Coordinate systems define the canvas on which all painting occurs. They determine the position,
orientation, and size of the text, graphics, and images that appear on a page. This subclause describes
each of the coordinate systems used in PDF, how they are related, and how transformations among
them are specified.

NOTE         The coordinate systems discussed in this subclause apply to two-dimensional graphics. PDF 1.6
introduced the ability to display 3D artwork, in which objects are described in a threedimensional coordinate system, as described in 13.6.5, "Coordinate systems for 3D".

8.3.2 Coordinate spaces

8.3.2.1 General

Paths and positions shall be defined in terms of pairs of coordinates on the Cartesian plane. A
coordinate pair is a pair of real numbers x and y that locate a point horizontally and vertically within a
two-dimensional coordinate space. A coordinate space is determined by the following properties with
respect to the current page:

•    The location of the origin
| • | The orientation of the x and y axes |
| --- | --- |
| • | The lengths of the units along each axis |

PDF defines several coordinate spaces in which the coordinates specifying graphics objects shall be
interpreted. The following subclauses describe these spaces and the relationships among them.

Transformations among coordinate spaces shall be defined by transformation matrices, which can
specify any linear mapping of two-dimensional coordinates, including translation, scaling, rotation,
reflection, and skewing. Transformation matrices are discussed in 8.3.3, "Common transformations"


## Page 165

and 8.3.4, "Transformation matrices".

8.3.2.2 Device space

The contents of a page ultimately appear on a raster output device such as a display or a printer. Such
devices vary greatly in the built-in coordinate systems they use to address pixels within their
imageable areas. A particular device’s coordinate system is called its device space. The origin of the
device space on different devices can fall in different places on the output page; on displays, the origin
can vary depending on the window system. Because the paper or other output medium moves through
different printers and imagesetters in different directions, the axes of their device spaces may be
oriented differently. For instance, vertical (y) coordinates may increase from the top of the page to the
bottom on some devices and from bottom to top on others. Finally, different devices have different
resolutions; some even have resolutions that differ in the horizontal and vertical directions.

NOTE         If coordinates in a PDF file were specified in device space, the file would be device-dependent
and would appear differently on different devices.

EXAMPLE            Images specified in the typical device spaces of a 72-pixel-per-inch display and a 600-dot-per-inch printer
would differ in size by more than a factor of 8; an 8-inch line segment on the display would appear less than
1 inch long on the printer. "Figure 10 — Device space" shows how the same graphics object, specified in
device space, can appear drastically different when rendered on different output devices.

Figure 10 — Device space

8.3.2.3 User space

To avoid the device-dependent effects of specifying objects in device space, PDF defines a deviceindependent coordinate system that always bears the same relationship to the current page, regardless
of the output device on which printing or displaying occurs. This device-independent coordinate
system is called user space.

The user space coordinate system shall be initialised to a default state for each page of a document. The
CropBox entry in the page dictionary shall specify the rectangle of user space corresponding to the
visible area of the intended output medium (display window or printed page). The positive x axis
extends horizontally to the right and the positive y axis vertically upward, as in standard mathematical
practice (subject to alteration by the Rotate entry in the page dictionary). The length of a unit along
both the x and y axes is set by the UserUnit entry (PDF 1.6) in the page dictionary (see "Table 31 —
Entries in a page object"). If that entry is not present or supported, the default value of 1 ⁄ 72 inch is
used. This coordinate system is called default user space.


## Page 166

NOTE 1       In the PostScript language, the origin of default user space always corresponds to the lower-left
corner of the output medium. While this convention is common in PDF documents as well, it is
not required; the page dictionary’s CropBox entry can specify any rectangle of default user space
to be made visible on the medium.
NOTE 2       The default for the size of the unit in default user space (1 ⁄ 72 inch) is approximately the same
as a point, a unit widely used in the printing industry. It is not exactly the same, however; there is
no universal definition of a point.
Conceptually, user space is an infinite plane. Only a small portion of this plane corresponds to the
imageable area of the output device: a rectangular region defined by the CropBox entry in the page
dictionary. The region of default user space that is viewed or printed can be different for each page and
is described in 14.11.2, "Page boundaries".

Coordinates in user space (as in any other coordinate space) may be specified as either integers or real
numbers, and the unit size in default user space does not constrain positions to any arbitrary grid. The
resolution of coordinates in user space is not related to the resolution of pixels in device space.

The transformation from user space to device space is defined by the current transformation matrix
(CTM), an element of the PDF graphics state (see 8.4, "Graphics state"). A PDF reader may adjust the
CTM for the native resolution of a particular output device, maintaining the device-independence of the
PDF page description. "Figure 11 — User space" shows how this allows an object specified in user
space to appear the same regardless of the device on which it is rendered.

The default user space provides a consistent, dependable starting place for PDF page descriptions
| regardless of the output device used. If necessary, a PDF content stream | may modify user space to be |  |
| --- | --- | --- |
| more suitable to its needs by applying the | coordinate transformation operator | , cm (see 8.4.4, "Graphics |

state operators"). Thus, what might appear to be absolute coordinates in a content stream are not
absolute with respect to the current page because they are expressed in a coordinate system that can
slide around and shrink or expand. Coordinate system transformation not only enhances deviceindependence but is a useful tool in its own right.

EXAMPLE            A content stream originally composed to occupy an entire page can be incorporated without change as an
element of another page by shrinking the coordinate system in which it is drawn.

Figure 11 — User space


## Page 167

8.3.2.4 Other coordinate spaces

In addition to device space and user space, PDF uses a variety of other coordinate spaces for
specialised purposes:

| • | The coordinates of text shall be specified in text space. The transformation from text space to user |

space shall be defined by a text matrix in combination with several text-related parameters in the
graphics state (see 9.4.2, "Text-positioning operators").
| • | Character glyphs in a font shall be defined in glyph space ("see 9.2.4, "Glyph positioning and |

metrics"). The transformation from glyph space to text space shall be defined by the font matrix.
For most types of fonts, this matrix shall be predefined to map 1000 units of glyph space to 1 unit
of text space; for Type 3 fonts, the font matrix shall be given explicitly in the font dictionary ("see
9.6.4, "Type 3 fonts").
| • | All sampled images shall be defined in image space. The transformation from image space to user |

space shall be predefined and cannot be changed. All images shall be 1 unit wide by 1 unit high in
user space, regardless of the number of samples in the image. To be painted, an image shall be
mapped to a region of the page by temporarily altering the CTM.
| • | A form XObject (discussed in 8.10, "Form XObjects") is a self-contained content stream that can be |

treated as a graphical element within another content stream. The space in which it is defined is
called form space. The transformation from form space to user space shall be specified by a form
matrix contained in the form XObject.
| • | PDF 1.2 defined a type of colour known as a pattern, discussed in 8.7, "Patterns". A pattern shall |

be defined either by a content stream that shall be invoked repeatedly to tile an area or by a
shading whose colour is a function of position. The space in which a pattern is defined is called
pattern space. The transformation from pattern space to user space shall be specified by a pattern
matrix contained in the pattern.
| • | PDF 1.6 embedded 3D artwork, which is described in three-dimensional coordinates (see 13.6.5, |

"Coordinate systems for 3D") that are projected into an annotation’s target coordinate system
(see 13.6.2, "3D Annotations").

8.3.2.5 Relationships among coordinate spaces

"Figure 12 — Relationships among coordinate systems" shows the relationships among the coordinate
spaces described above. Each arrow in the figure represents a transformation from one coordinate
space to another. PDF allows modifications to many of these transformations.


## Page 168

Figure 12 — Relationships among coordinate systems
Because PDF coordinate spaces are defined relative to one another, changes made to one
transformation can affect the appearance of objects defined in several coordinate spaces.

EXAMPLE            A change in the CTM, which defines the transformation from user space to device space, affects forms, text,
images, and patterns, since they are all upstream from user space.

8.3.3 Common transformations

A transformation matrix specifies the relationship between two coordinate spaces. By modifying a
transformation matrix, objects can be scaled, rotated, translated, or transformed in other ways.

| A transformation matrix in PDF shall be specified by six numbers, usually in the for | m of an array |

containing six elements. In its most general form, this array is denoted [a b c d e f]; it can represent any
linear transformation from one coordinate system to another. This subclause lists the arrays that
specify the most common transformations; 8.3.4, "Transformation matrices", discusses more
mathematical details of transformations, including information on specifying transformations that are
combinations of those listed here:

| • | Translations shall be specified as [ 1 0 0 1 𝑡𝑥 𝑡𝑦], where tx and ty shall be the distances to translate |

the origin of the coordinate system in the horizontal and vertical dimensions, respectively.
| • | Scaling shall be obtained by [ 𝑠𝑥 0 0 𝑠𝑦 0 0]. This scales the coordinates so that 1 unit in the |
| --- | --- |
| horizontal and vertical dimensions of the new coordinate system is the same size as | sx and sy |

units, respectively, in the previous coordinate system.
| • | Rotations shall be produced by [rc rs -rs rc 0 0], where rc = cos(q) and rs = sin(q) which has the |

effect of rotating the coordinate system axes by an angle q counter clockwise.
| • | Skew shall be specified by [1 wx wy 1 0 0], where wx = tan(a) and wy = tan(b) which skews the x |

axis by an angle a and the y axis by an angle b.
"Figure 13 — Effects of coordinate transformations" shows examples of each transformation. The
directions of translation, rotation, and skew shown in the figure correspond to positive values of the
array elements.


## Page 169

Figure 13 — Effects of coordinate transformations
NOTE 1        If several transformations are combined, the order in which they are applied is significant. For
example, first scaling and then translating the x axis is not the same as first translating and then
scaling it. In general, to obtain the expected results, transformations need to be done in the
following order: Translate, Rotate, Scale or skew.
"Figure 14 — Effect of transformation order" shows the effect of the order in which transformations
are applied. The figure shows two sequences of transformations applied to a coordinate system. After
each successive transformation, an outline of the letter n is drawn.

Figure 14 — Effect of transformation order
NOTE 2        The following transformations are shown in the figure: a translation of 10 units in the x direction
and 20 units in the y direction; a rotation of 30 degrees; a scaling by a factor of 3 in the x
direction
In the figure, the axes are shown with a dash pattern having a 2-unit dash and a 2-unit gap. In
addition, the original (untransformed) axes are shown in a lighter colour for reference. Notice
that the scale-rotate-translate ordering results in a distortion of the coordinate system, leaving


## Page 170

the x and y axes no longer perpendicular; the recommended translate-rotate-scale ordering
results in no distortion.

8.3.4 Transformation matrices

This subclause discusses the mathematics of transformation matrices.

To understand the mathematics of coordinate transformations in PDF, it is vital to remember two
points:

| • | Transformations alter coordinate systems, not graphics objects. All objects painted before a |

transformation is applied shall be unaffected by the transformation. Objects painted after the
transformation is applied shall be interpreted in the transformed coordinate system.
| • | Transformation matrices specify the transformation from the new (transformed) coordinate system |

to the original (untransformed) coordinate system. All coordinates used after the transformation
shall be expressed in the transformed coordinate system. PDF applies the transformation matrix
to find the equivalent coordinates in the untransformed coordinate system.
NOTE 1       Many computer graphics textbooks consider transformations of graphics objects rather than of
coordinate systems. Although either approach is correct and self-consistent, some details of the
calculations differ depending on which point of view is taken.
PDF represents coordinates in a two-dimensional space. The point (x, y) in such a space can be
expressed in vector form as [x y 1]. The constant third element of this vector (1) is needed so that the
vector can be used with 3-by-3 matrices in the calculations described below.

The transformation between two coordinate systems can be represented by a 3-by-3 transformation
matrix written as follows:

| a | b | 0 |
| --- | --- | --- |
| [c | d | 0] |
| e | f | 1 |

Because a transformation matrix has only six elements that can be changed, in most cases in PDF it
shall be specified as the six-element array [a b c d e f].

Coordinate transformations shall be expressed as matrix multiplications:

| ′ | ′ | a | b | 0 |  |  |
| --- | --- | --- | --- | --- | --- | --- |
| [𝑥 | 𝑦 | 1] =  [𝑥 | 𝑦 | 1] ×  [c | d | 0] |
| e | f | 1 |  |  |  |  |

Because PDF transformation matrices specify the conversion from the transformed coordinate system
to the original (untransformed) coordinate system, x′ and y′ in this equation shall be the coordinates in
the untransformed coordinate system, and x and y shall be the coordinates in the transformed system.
The multiplication is carried out as follows:
| 𝑥′  =  a× | 𝑥 +  c× | 𝑦+ | e |
| --- | --- | --- | --- |
| 𝑦′  =  b× | 𝑥 +  d  × 𝑦+ | f |  |

If a series of transformations is carried out, the matrices representing each of the individual
transformations can be multiplied together to produce a single equivalent matrix representing the


## Page 171

composite transformation.

NOTE 2       Matrix multiplication is not commutative — the order in which matrices are multiplied is
significant. Consider a sequence of two transformations: a scaling transformation applied to the
user space coordinate system, followed by a conversion from the resulting scaled user space to
device space. Let MS be the matrix specifying the scaling and MC the current transformation
matrix, which transforms user space to device space. Recalling that coordinates are always
specified in the transformed space, the correct order of transformations first converts the scaled
coordinates to default user space and then converts the default user space coordinates to device
space. This can be expressed as:
| XD  =  XU  × MC | = (XS  ×  MS)× | MC | =  XS ×  (MS  × MC) |

where:
XD denotes the coordinates in device space
XU denotes the coordinates in default user space
XS denotes the coordinates in scaled user space
This shows that when a new transformation is concatenated with an existing one, the matrix
representing it shall be multiplied before (premultiplied with) the existing transformation matrix.

This result is true in general for PDF: when a sequence of transformations is carried out, the matrix
representing the combined transformation (M′) is calculated by premultiplying the matrix
representing the additional transformation (MT) with the one representing all previously existing
transformations (M):
| 𝑀′ | =  𝑀𝑇 | ×  𝑀 |

NOTE 3       When rendering graphics objects, it is sometimes necessary for a PDF reader to perform the
inverse of a transformation — that is, to find the user space coordinates that correspond to a
given pair of device space coordinates. Not all transformations are invertible, however. For
example, if a matrix contains a, b, c, and d elements that are all zero, all user coordinates map to
the same device coordinates and there is no unique inverse transformation. Such noninvertible
transformations are not very useful and generally arise from unintended operations, such as
scaling by 0. Use of a noninvertible matrix when painting graphics objects can result in
unpredictable behaviour.

8.4 Graphics state

8.4.1 General

A PDF processor shall maintain an internal data structure called the graphics state that holds current
graphics control parameters. These parameters define the global framework within which the graphics
operators execute.

EXAMPLE 1          The f (fill) operator implicitly uses the current colour parameter, and the    S (stroke) operator additionally
uses the current line width parameter from the graphics state.

A PDF processor shall initialise the graphics state at the beginning of each page with the values
| specified in "Table 51 — Device-independent graphics state p | arameters" and "Table 52 — Device- |

dependent graphics state parameters". "Table 51 — Device-independent graphics state parameters"
lists those graphics state parameters that are device-independent and are appropriate to specify in
page descriptions. The parameters listed in "Table 52 — Device-dependent graphics state parameters"
control details of the rendering (scan conversion) process and are device-dependent; a page


## Page 172

description that is intended to be device-independent should not be written to modify these
parameters.

Table 51 — Device-independent graphics state parameters

| Parameter | Type | Value |

| CTM | array | The current transformation matrix, which maps positions from user coordinates to device coordinates (see 8.3, "Coordinate systems"). This matrix is modified by each application of the coordinate transformation operator, cm. Initial value: a matrix that transforms default user coordinates to device coordinates. |

| clipping path | (internal) | The current clipping path, which defines the boundary against which all output shall be cropped (see 8.5.4, "Clipping path operators"). Initial value: the size of the MediaBox. |

| color space | name or | The current colour space in which colour values shall be interpreted (see 8.6, |
| --- | --- | --- |
| array | "Colour spaces"). There are two separate colour space parameters: one for stroking and one for all other painting operations. Initial value: DeviceGray. |  |

| color | (various) | The current colour that shall be used during painting operations (see 8.6, "Colour spaces"). The type and interpretation of this parameter depend on the current colour space; for most colour spaces, a colour value consists of one to four numbers. There are two separate colour parameters: one for stroking and one for all other painting operations. Initial value: black. |

| text state | (various) | A set of nine graphics state parameters that pertain only to the painting of text. These include parameters that select the font, scale the glyphs to an appropriate size, and accomplish other effects. The text state parameters are described in 9.3, "Text state parameters and operators". |

| line width | number | The thickness, in user space units, of paths to be stroked (see 8.4.3.2, "Line width"). Initial value: 1.0. |

| line cap | integer | A code specifying the shape of the start and endcaps for an open stroked path or the caps at both ends of dashes in a stroked path (see 8.4.3.3, "Line cap style"). Initial value: 0, for butt caps. |

| line join | integer | A code specifying the shape of joints between connected segments of a stroked path ("see 8.4.3.4, "Line join style"). Initial value: 0, for mitered joins. |

| miter limit | number | The miter limit imposes a maximum on the ratio of the miter length to the line width. When the limit is exceeded, the join is converted from a miter to a bevel (see 8.4.3.5, "Miter limit"). This parameter limits the length of "spikes" produced when line segments join at sharp angles. Initial value: 10.0, for a miter cutoff below approximately 11.5 degrees. |

| dash pattern | array and | A description of the dash pattern that shall be used when paths are stroked |
| --- | --- | --- |
| number | (see 8.4.3.6, "Line dash pattern"). Initial value: [] 0, a solid line. |  |

| rendering intent | name | The rendering intent that shall be used when converting CIE-based colours to device colours (see 8.6.5.8, "Rendering intents"). Initial value: RelativeColorimetric. |


## Page 173

| Parameter | Type | Value |

| stroke adjustment | boolean | (PDF 1.2) A flag specifying whether to compensate for possible rasterization effects when stroking a path with a line width that is small relative to the pixel resolution of the output device (see 10.7.5, "Automatic stroke adjustment"). |

NOTE      This is considered a device-independent parameter, even though the
details of its effects are device-dependent.
Initial value: false.

| blend mode | name or | (PDF 1.4, array is deprecated in PDF 2.0) The current blend mode that shall |
| --- | --- | --- |
| array | be used in the transparent imaging model (see 11.3.5, "Blend mode"). A PDF |  |
| (array is | reader shall implicitly reset this parameter to its initial value at the |  |
| deprecated | beginning of execution of a transparency group XObject (see 11.6.6, |  |
| in PDF 2.0) | "Transparency group XObjects"). The value shall be either a name object, designating one of the standard blend modes listed in "Table 134 — Standard separable blend modes" and "Table 135 — Standard non-separable blend modes" in 11.3.5, "Blend mode", or an array of such names. In the latter case, the PDF reader shall use the first blend mode in the array that it recognises (or Normal if it recognises none of them). Initial value: Normal. |  |

| soft mask | dictionary | (PDF 1.4) A soft-mask dictionary (see 11.6.5.1, "Soft-mask dictionaries") |
| --- | --- | --- |
| or name | specifying the mask shape or mask opacity values that shall be used in the transparent imaging model (see 11.3.7.2, "Source shape and opacity" and 11.6.4.3, "Mask shape and opacity"), or the name None if no such mask is specified. A PDF reader shall implicitly reset this parameter to its initial value at the beginning of execution of a transparency group XObject (see 11.6.6, "Transparency group XObjects"). Initial value: None. |  |

| alpha constant | number | (PDF 1.4) The constant shape or constant opacity value that shall be used in the transparent imaging model (see 11.3.7.2, "Source shape and opacity" and 11.6.4.4, "Constant shape and opacity"). There are two separate alpha constant parameters: one for stroking and one for all other painting operations. A PDF reader shall implicitly reset this parameter to its initial value at the beginning of execution of a transparency group XObject (see 11.6.6, "Transparency group XObjects"). Initial value: 1.0. |

| alpha source | boolean | (PDF 1.4) A flag specifying whether the current soft mask and alpha constant parameters shall be interpreted as shape values (true) or opacity values (false). This flag also governs the interpretation of the SMask entry, if any, in an image dictionary (see 8.9.5, "Image dictionaries"). Initial value: false. |

| black point | name | (PDF 2.0) The black point compensation algorithm that shall be used when |
| --- | --- | --- |
| compensation | converting CIE-based colours (see 8.6.5.9, "Use of black point compensation"). Initial value: Default. |  |


## Page 174

Table 52 — Device-dependent graphics state parameters

| Parameter | Type | Value |

| overprint | boolean | (PDF 1.2) A flag specifying (on output devices that support the overprint control feature) whether painting in one set of colourants should cause the corresponding areas of other colourants to be erased (false) or left unchanged (true); see 8.6.7, "Overprint control". PDF 1.3, introduced two separate overprint parameters: one for stroking and one for all other painting operations. Initial value: false. |

| overprint mode | number | (PDF 1.3) A code specifying whether a colour component value of 0 in a DeviceCMYK colour space should erase that component (0) or leave it unchanged (1) when overprinting (see 8.6.7, "Overprint control"). Initial value: 0. |

| black generation | function or | (PDF 1.2) A function that calculates the level of the black colour component to |
| --- | --- | --- |
| name | use when converting RGB colours to CMYK (see 10.4.2.4, "Conversion from DeviceRGB to DeviceCMYK"). Initial value: a PDF reader shall initialise this to a suitable device dependent value. |  |

| undercolor removal | function or | (PDF 1.2) A function that calculates the reduction in the levels of the cyan, |
| --- | --- | --- |
| name | magenta, and yellow colour components to compensate for the amount of black added by black generation (see 10.4.2.4, "Conversion from DeviceRGB to DeviceCMYK"). Initial value: a PDF reader shall initialise this to a suitable device dependent value. |  |

| transfer | function, | (PDF 1.2, deprecated in PDF 2.0) A function that adjusts device gray or colour |
| --- | --- | --- |
| name, or | component levels to compensate for nonlinear response in a particular |  |
| array | output device (see 10.5, "Transfer functions"). Initial value: a PDF reader shall initialise this to a suitable device dependent value. |  |

| halftone | dictionary, | (PDF 1.2) A halftone screen for gray and colour rendering, specified as a |
| --- | --- | --- |
| stream, or | halftone dictionary or stream (see 10.6, "Halftones"). Initial value: a PDF |  |
| name | reader shall initialise this to a suitable device dependent value. |  |

| flatness | number | The precision with which curves shall be rendered on the output device (see 10.7.2, "Flatness tolerance"). The value of this parameter (positive number) gives the maximum error tolerance, measured in output device pixels; smaller numbers give smoother curves at the expense of more computation and memory use. Initial value: 1.0. |

| smoothness | number | (PDF 1.3) The precision with which colour gradients are to be rendered on the output device (see 10.7.3, "Smoothness tolerance"). The value of this parameter (0 to 1.0) gives the maximum error tolerance, expressed as a fraction of the range of each colour component; smaller numbers give smoother colour transitions at the expense of more computation and memory use. Initial value: a PDF reader shall initialise this to a suitable device dependent value. |

NOTE 1        Some graphics state parameters are set with specific PDF operators, some are set by including a
particular entry in a graphics state parameter dictionary, and some can be specified either way.

EXAMPLE 2           The current line width can be set either with the w operator or (in PDF 1.3) with the LW entry in a graphics
state parameter dictionary, whereas the current colour is set only with specific operators, and the current


## Page 175

halftone is set only with a graphics state parameter dictionary.

In general, a PDF processor, when interpreting the operators that set graphics state parameters, shall
simply store them unchanged for later use when interpreting the painting operators. However, some
parameters have special properties or call for behaviour that a PDF processor shall handle:

| • | Most parameters shall be of the correct type or have values that fall within a certain range. |
| --- | --- |
| • | Parameters that are numeric values, such as the current colour, line width, and miter limit, shall |

be clipped into valid range, if necessary. However, they shall not be adjusted to reflect capabilities
of the raster output device, such as resolution or number of distinguishable colours. Painting
operators perform such adjustments, but the adjusted values shall not be stored back into the
graphics state.
| • | Paths shall be internal objects that shall not be directly represented in PDF. |

NOTE 2       As indicated in "Table 51 — Device-independent graphics state parameters" and "Table 52 —
| Device-dependent graphics state p | arameters", some of the parameters — colour space, colour, and overprint — have two values, one used for stroking (of paths and text objects) and one for all other painting operations. The two parameter values can be set independently, allowing for operations such as combined filling and stroking of the same path with different colours. Except where noted, a term such as current colour is to be interpreted to refer to whichever colour parameter applies to the operation being performed. When necessary, the individual colour parameters are distinguished explicitly as the stroking colour and the nonstroking colour. |

8.4.2 Graphics state stack

A PDF document typically contains many graphical elements that are independent of each other and
nested to multiple levels. The graphics state stack allows these elements to make local changes to the
graphics state without disturbing the graphics state of the surrounding environment. The stack is a
LIFO (last in, first out) data structure in which the contents of the graphics state may be saved and later
restored using the following operators:

| • | The q operator shall push a copy of the entire graphics state onto the stack. |
| --- | --- |
| • | The Q operator shall restore the entire graphics state to its former value by popping it from the |

stack.
NOTE         These operators can be used to encapsulate a graphical element so that it can modify parameters
of the graphics state and later restore them to their previous values.
Occurrences of the q and Q operators shall be balanced within a given content stream (or within the
sequence of streams specified in a page dictionary’s Contents array).

8.4.3 Details of graphics state parameters

8.4.3.1 General

This subclause gives details of several of the device-independent graphics state parameters listed in
"Table 51 — Device-independent graphics state parameters".

8.4.3.2 Line width

The line width parameter specifies the thickness of the line used to stroke a path. It shall be a nonnegative number expressed in user space units; stroking a path shall entail painting all points whose
perpendicular distance from the path in user space is less than or equal to half the line width. The


## Page 176

effect produced in device space depends on the current transformation matrix (CTM) in effect at the
time the path is stroked. If the CTM specifies scaling by different factors in the horizontal and vertical
dimensions, the thickness of stroked lines in device space shall vary according to their orientation. The
actual line width achieved can differ from the requested width by as much as 2 device pixels,
depending on the positions of lines with respect to the pixel grid. Automatic stroke adjustment may be
used to ensure uniform line width; see 10.7.5, "Automatic stroke adjustment".

A line width of 0 shall denote the thinnest line that can be rendered at device resolution: 1 device pixel
wide. However, some devices cannot reproduce 1-pixel lines, and on high-resolution devices, they are
nearly invisible. Since the results of rendering such zero-width lines are device-dependent, they should
not be used.

8.4.3.3 Line cap style

The line cap style shall specify the shape that shall be used at both ends of open subpaths (and dashes
8.4.3.6, "Line dash pattern") when they are stroked. "Table 53 — Line cap styles" shows the allowed
values.

Table 53 — Line cap styles

| Style | Appearance | Description |

Butt cap. The stroke shall be squared off at the endpoint of the path. There
shall be no projection beyond the end of the path.

Round cap. A semicircular arc with a diameter equal to the line width shall
be drawn around the endpoint and shall be filled in.

Projecting square cap. The stroke shall continue beyond the endpoint of
the path for a distance equal to half the line width and shall be squared off.

8.4.3.4 Line join style

The line join style shall specify the shape to be used at the corners of paths that are stroked. "Table 54
— Line join styles" shows the allowed values. Join styles shall be significant only at points where
consecutive segments of a path connect at an angle; segments that meet or intersect fortuitously shall
receive no special treatment.

Table 54 — Line join styles

| Style | Appearance | Description |

Miter join. The outer edges of the strokes for the two segments shall be
extended until they meet at an angle, as in a picture frame. If the segments
meet at too sharp an angle (as defined by the miter limit parameter — see
8.4.3.5, "Miter limit"), a bevel join shall be used instead.


## Page 177

| Style | Appearance | Description |

Round join. An arc of a circle with a diameter equal to the line width shall
be drawn around the point where the two segments meet, connecting the
outer edges of the strokes for the two segments. This pie-slice-shaped
figure shall be filled in, producing a rounded corner.

Bevel join. The two segments shall be finished with butt caps (see 8.4.3.3,
"Line cap style") and the resulting notch beyond the ends of the segments
shall be filled with a triangle.

A zero length dash occurring at a zero length subpath segment does not have a determinable direction
and thus, if the line caps are non-round is rendered in an implementation-dependent manner.

In a closed subpath that is dashed, if the first segment starts with an on-dash and the last segment ends
within an on-dash, then they shall be joined.

NOTE          The definition of round join was changed in PDF 1.5. In rare cases, the implementation of the
previous specification could produce unexpected results.

8.4.3.5 Miter limit

When two line segments meet at a sharp angle and mitered joins have been specified as the line join
style, it is possible for the miter to extend far beyond the thickness of the line stroking the path. The
miter limit shall impose a maximum on the ratio of the miter length to the line width (see "Figure 15 —
Miter length"). When the limit is exceeded, the join is converted from a miter to a bevel.

| The ratio of miter length to line width is directly related to the angle | j between the segments in user |

space by the following formula:

| 𝑚𝑖𝑡𝑒𝑟𝐿𝑒𝑛𝑔𝑡ℎ | = | 1 |
| --- | --- | --- |
| 𝑙𝑖𝑛𝑒𝑊𝑖𝑑𝑡ℎ | sin𝑗 |  |

When the line width is zero, the miter length is zero.

NOTE          Very large miter lengths are allowed.

EXAMPLE             A miter limit of 1.414 converts miters to bevels for j less than 90 degrees, a limit of 2.0 converts them for j


## Page 178

less than 60 degrees, and a limit of 10.0 converts them for j less than approximately 11.5 degrees.

Figure 15 — Miter length

8.4.3.6 Line dash pattern

The line dash pattern shall control the pattern of dashes and gaps used to stroke paths. It shall be
specified by a dash array and a dash phase. The dash array’s elements shall be numbers that specify the
lengths of alternating dashes and gaps; the numbers shall be nonnegative and not all zero. The dash
phase shall be a number that specifies the distance into the dash pattern at which to start the dash. If
the dash phase is negative, it shall be incremented by twice the sum of all lengths in the dash array
until it is positive. The elements of both the dash array and the dash phase shall be expressed in user
space units.

Before beginning to stroke a path, the dash array shall be cycled through, adding up the lengths of
dashes and gaps. When the accumulated length equals the value specified by the dash phase, stroking
of the path shall begin, and the dash array shall be used cyclically from that point onward. "Table 55 —
Examples of line dash patterns" shows examples of line dash patterns.                         If the dash array is empty, the
dash phase shall be zero and the path shall be stroked with a solid, unbroken line.

Table 55 — Examples of line dash patterns

| Dash Array | Appearance | Description and Phase |

| [] 0 | No dash; solid, unbroken lines |

| [3] 0 | 3 units on, 3 units off, … |

| [2] 1 | 1 on, 2 off, 2 on, 2 off, … |

| [2 1] 0 | 2 on, 1 off, 2 on, 1 off, … |

| [3 5] 6 | 2 off, 3 on, 5 off, 3 on, 5 off, … |

| [2 3] 11 | 1 on, 3 off, 2 on, 3 off, 2 on, … |

| [2 1 3] 0 | 2 on, 1 off, 3 on, 2 off, 1 on, 3 off, 2 on, … |

| [2 1 3] -2 | 2 off, 2 on, 1 off, 3 on, 2 off, 1 on, 3 off, … |


## Page 179

Dashed lines shall wrap around curves and corners just as solid stroked lines do. The ends of each dash
shall be treated with the current line cap style, and corners within dashes shall be treated with the
current line join style. The treatment of overlapping line caps shall follow the rules given in 11.6.2,
"Specifying source and backdrop colours". A stroking operation shall take no measures to coordinate
the dash pattern with features of the path; it simply shall dispense dashes and gaps along the path in
the pattern defined by the dash array. If the end of a dashed segment coincides exactly with a join
point, then the end cap is painted before the corner.

When a path consisting of several subpaths is stroked, each subpath shall be treated independently —
that is, the dash pattern shall be restarted and the dash phase shall be reapplied to it at the beginning
of each subpath.

NOTE          As noted in 8.5.3.2, "Stroking" and in "Table 58 — Path construction operators", closed paths
| have no end caps, but the individual dash segmen | ts of a path stroked using a non | -empty line dash pattern are individually open paths and therefore receive end cap processing as specified in the graphics state. If any dash segment includes a corner then that corner is painted using the current join style in the graphics state. If a corner is not contained within any dashed segment the corner is not painted. |

8.4.4 Graphics state operators

"Table 56 — Graphics state operators" shows the operators that set the values of parameters in the
graphics state. (See also the colour operators listed in "Table 73 — Colour operators" and the text state
operators in "Table 103 — Text state operators".)

Table 56 — Graphics state operators

| Operands | Operator | Description |

| — | q | Save the current graphics state on the graphics state stack (see 8.4.2, "Graphics state stack"). |

| — | Q | Restore the graphics state by removing the most recently saved state from the stack and making it the current state (see 8.4.2, "Graphics state stack"). |

| a b c d e f | cm | Modify the current transformation matrix (CTM) by concatenating the specified matrix (see 8.3.2, "Coordinate spaces"). Although the operands specify a matrix, they shall be written as six separate numbers, not as an array. |

| lineWidth | w | Set the line width in the graphics state (see 8.4.3.2, "Line width"). |

| lineCap | J | Set the line cap style in the graphics state (see 8.4.3.3, "Line cap style"). |

| lineJoin | j | Set the line join style in the graphics state (see 8.4.3.4, "Line join style"). |

| miterLimit | M | Set the miter limit in the graphics state (see 8.4.3.5, "Miter limit"). |

| dashArray | d | Set the line dash pattern in the graphics state (see 8.4.3.6, "Line dash pattern"). |

dashPhase

| intent | ri | (PDF 1.1) Set the colour rendering intent in the graphics state (see 8.6.5.8, "Rendering intents"). |


## Page 180

| Operands | Operator | Description |

| flatness | i | Set the flatness tolerance in the graphics state (see 10.7.2, "Flatness tolerance"). flatness is a number in the range 0 to 100; a value of 0 shall specify the output device’s default flatness tolerance. |

| dictName | gs | (PDF 1.2) Set the specified parameters in the graphics state. dictName shall be the name of a graphics state parameter dictionary in the ExtGState subdictionary of the current resource dictionary (see the next subclause). |

8.4.5 Graphics state parameter dictionaries

While some parameters in the graphics state may be set with individual operators, as shown in "Table
56 — Graphics state operators", others may not. The latter may only be set with the generic graphics
state operator gs (PDF 1.2). The operand supplied to this operator shall be the name of a graphics state
parameter dictionary whose contents specify the values of one or more graphics state parameters. This
name shall be looked up in the ExtGState subdictionary of the current resource dictionary.

The graphics state parameter dictionary is also used by Type 2 patterns, which do not have a content
stream in which the graphics state operators could be invoked (see 8.7.4, "Shading patterns").

Each entry in the parameter dictionary shall specify the value of an individual graphics state
parameter, as shown in "Table 57 — Entries in a graphics state parameter dictionary". All entries need
not be present for every invocation of the gs operator; the supplied parameter dictionary may include
any combination of parameter entries. The results of gs shall be cumulative; parameter values
established in previous invocations persist until explicitly overridden.

NOTE         Note that some parameters appear in both "Table 56 — Graphics state operators" and "Table 57
| — Entries in a graphics state parameter d | ictionary"; these parameters can be set eit | her with |
| --- | --- | --- |
| individual graphics state operators or with | gs. It is expected that any future extensions to the graphics state will be implemented by adding new entries to the graphics state parameter dictionary rather than by introducing new graphics state operators. |  |

Table 57 — Entries in a graphics state parameter dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; shall be ExtGState for a graphics state parameter dictionary. |

| LW | number | (Optional; PDF 1.3) The line width (see 8.4.3.2, "Line width"). |

| LC | integer | (Optional; PDF 1.3) The line cap style (see 8.4.3.3, "Line cap style"). |

| LJ | integer | (Optional; PDF 1.3) The line join style (see 8.4.3.4, "Line join style"). |

| ML | number | (Optional; PDF 1.3) The miter limit (see 8.4.3.5, "Miter limit"). |

| D | array | (Optional; PDF 1.3) The line dash pattern, expressed as an array of the form [dashArray dashPhase], where dashArray shall be itself an array and dashPhase shall be a number (see 8.4.3.6, "Line dash pattern"). |


## Page 181

| Key | Type | Value |

| RI | name | (Optional; PDF 1.3) The name of the rendering intent (see 8.6.5.8, "Rendering intents"). |

| OP | boolean | (Optional) A flag specifying whether to apply overprint (see 8.6.7, "Overprint control"). In PDF 1.2 and earlier, there is a single overprint parameter that applies to all painting operations. Beginning with PDF 1.3, two separate overprint parameters were defined: one for stroking and one for all other painting operations. Specifying an OP entry shall set both parameters unless there is also an op entry in the same graphics state parameter dictionary, in which case the OP entry shall set only the overprint parameter for stroking. |

| op | boolean | (Optional; PDF 1.3) A flag specifying whether to apply overprint (see 8.6.7, "Overprint control") for painting operations other than stroking. If this entry is absent, the OP entry, if any, shall also set this parameter. |

| OPM | integer | (Optional; PDF 1.3) The overprint mode (see 8.6.7, "Overprint control"). |

| Font | array | (Optional; PDF 1.3) An array of the form [font size], where font shall be an indirect reference to a font dictionary and size shall be a number expressed in text space units. These two objects correspond to the operands of the Tf operator (see 9.3, "Text state parameters and operators"); however, the first operand shall be an indirect object reference instead of a resource name. |

| BG | function | (Optional) The black-generation function, which maps the interval [0.0 1.0] to the interval [0.0 1.0] (see 10.4.2.4, "Conversion from DeviceRGB to DeviceCMYK"). |

| BG2 | function or | (Optional; PDF 1.3) Same as BG except that the value may also be the name |
| --- | --- | --- |
| name | Default, denoting the black-generation function that was in effect at the start of the page. If both BG and BG2 are present in the same graphics state parameter dictionary, BG2 shall take precedence. |  |

| UCR | function | (Optional) The undercolour-removal function, which maps the interval [0.0 1.0] to the interval [−1.0 1.0] (see 10.4.2.4, "Conversion from DeviceRGB to DeviceCMYK"). |

| UCR2 | function or | (Optional; PDF 1.3) Same as UCR except that the value may also be the name |
| --- | --- | --- |
| name | Default, denoting the undercolour-removal function that was in effect at the start of the page. If both UCR and UCR2 are present in the same graphics state parameter dictionary, UCR2 shall take precedence. |  |

| TR | function, | (Optional, deprecated in PDF 2.0) The transfer function, which maps the |
| --- | --- | --- |
| name, or | interval [0.0 1.0] to the interval [0.0 1.0] (see 10.5, "Transfer functions"). The |  |
| array | value shall be either a single function (which applies to all process colourants) or an array of four functions (which apply to the process colourants individually). The name Identity may be used to represent the Identity function. |  |

| TR2 | function, | (Optional; PDF 1.3, deprecated in PDF 2.0) Same as TR except that the value |
| --- | --- | --- |
| name, or | may also be the name Default, denoting the transfer function that was in |  |
| array | effect at the start of the page. If both TR and TR2 are present in the same graphics state parameter dictionary, TR2 shall take precedence. |  |

| HT | dictionary, | (Optional) The halftone dictionary or stream (see 10.6, "Halftones") or the |
| --- | --- | --- |
| stream, or | name Default, denoting the halftone that was in effect at the start of the page. name |  |


## Page 182

| Key | Type | Value |

| FL | number | (Optional; PDF 1.3) The flatness tolerance (see 10.7.2, "Flatness tolerance"). |

| SM | number | (Optional; PDF 1.3) The smoothness tolerance (see 10.7.3, "Smoothness tolerance"). |

| SA | boolean | (Optional) A flag specifying whether to apply automatic stroke adjustment (see 10.7.5, "Automatic stroke adjustment"). |

| BM | name or | (Optional; PDF 1.4; array is deprecated in PDF 2.0) The current blend mode |
| --- | --- | --- |
| array | that shall be used in the transparent imaging model (see 11.3.5, "Blend |  |
| (array is | mode"). deprecated in PDF 2.0) |  |

| SMask | dictionary | (Optional; PDF 1.4) The current soft mask, specifying the mask shape or mask |
| --- | --- | --- |
| or name | opacity values that shall be used in the transparent imaging model (see 11.3.7.2, "Source shape and opacity" and 11.6.4.3, "Mask shape and opacity"). Although the current soft mask is sometimes referred to as a "soft clip", altering it with the gs operator completely replaces the old value with the new one, rather than intersecting the two as is done with the current clipping path parameter (see 8.5.4, "Clipping path operators"). |  |

| CA | number | (Optional; PDF 1.4) The current stroking alpha constant, specifying the constant shape or constant opacity value that shall be used for stroking operations in the transparent imaging model (see 11.3.7.2, "Source shape and opacity" and 11.6.4.4, "Constant shape and opacity"). |

| ca | number | (Optional; PDF 1.4) Same as CA, but for nonstroking operations. |

| AIS | boolean | (Optional; PDF 1.4) The alpha source flag ("alpha is shape"), specifying whether the current soft mask and alpha constant shall be interpreted as shape values (true) or opacity values (false). This flag also governs the interpretation of the SMask entry, if any, in an image dictionary (see 8.9.5, "Image dictionaries"). |

| TK | boolean | (Optional; PDF 1.4) The text knockout flag, shall determine the behaviour of overlapping glyphs within a text object in the transparent imaging model (see 9.3.8, "Text knockout"). This flag controls the behavior of glyphs obtained from any font type, including Type 3. |

| UseBlackPtComp | name | (Optional; PDF 2.0) This graphics state parameter controls whether black point compensation is performed while doing CIE-based colour conversions. It shall be set to either OFF, ON or Default. The semantics of Default are up to the PDF processor. See 8.6.5.9, "Use of black point compensation". The default value is: Default. |


## Page 183

| Key | Type | Value |

| HTO | array | (Optional; PDF 2.0) Halftone origin, specified as an array of two numbers specifying the X and Y location of the halftone origin in the current coordinate system. Although the numbers are specified in the current coordinate system, changes to the current coordinate system (for example as a result of invocation of a form XObject) do not move the halftone origin relative to the underlying device coordinate system. |

NOTE:    The HTO key is very similar to the HTP key defined in PDF versions up to
PDF 1.3 (1st Edition), but differs in the coordinate system used.

EXAMPLE             The following shows two graphics state parameter dictionaries. In the first, automatic stroke adjustment is
turned on, and the dictionary includes a transfer function (deprecated in PDF 2.0) that inverts its value,
| 𝑓 (𝑥) = | 1 − | 𝑥. In the second, overprint is turned off, and the dictionary includes a parabolic transfer |
| --- | --- | --- |
| function (deprecated in PDF 2.0), 𝑓 (𝑥) | =  (2𝑥  − | 1)2, with a sample of 21 values. The domain of the transfer |
| function, [0.0 1.0], is mapped to [0 20], and the range of the sample values, | [0 255], is mapped to the range of the transfer function, [0.0 1.0]. |  |

| 10 0 obj | %Page object <</Type /Page /Parent 5 0 R /Resources 20 0 R /Contents 40 0 R >> endobj |

| 20 0 obj | %Resource dictionary for page <</Font <</F1 25 0 R>> /ExtGState <</GS1 30 0 R /GS2 35 0 R >> >> endobj |

| 30 0 obj | %First graphics state parameter dictionary <</Type /ExtGState /SA true /TR 31 0 R >> endobj |

| 31 0 obj | %First transfer function <</FunctionType 0 /Domain [0.0 1.0] /Range [0.0 1.0] /Size 2 /BitsPerSample 8 /Length 7 /Filter /ASCIIHexDecode >> stream 01 00> endstream endobj |

| 35 0 obj | %Second graphics state parameter dictionary <</Type /ExtGState /OP false /TR 36 0 R >> endobj |


## Page 184

| 36 0 obj | %Second transfer function <</FunctionType 0 /Domain [0.0 1.0] /Range [0.0 1.0] /Size 21 /BitsPerSample 8 /Length 63 /Filter /ASCIIHexDecode >> |

stream
FF CE A3 7C 5B 3F 28 16 0A 02 00 02 0A 16 28 3F 5B 7C A3 CE FF>
endstream
endobj

8.5 Path construction and painting

8.5.1 General

Paths define shapes, trajectories, and regions of all sorts. They shall be used to draw lines, define the
shapes of filled areas, and specify boundaries for clipping other graphics. The graphics state shall
include a current clipping path that shall define the clipping boundary for the current page. At the
beginning of each page, the clipping path shall be initialised to the size of the MediaBox.

A path may contain any combination of zero or more line segments, which may be straight or curved.
Paths may connect to one another or may be disconnected. A pair of segments shall be said to connect
only if they are defined consecutively, with the second segment starting where the first one ends. Thus,
the order in which the segments of a path are defined shall be significant. Nonconsecutive segments
that meet or intersect fortuitously shall not be considered to connect.

NOTE         A path is made up of one or more disconnected subpaths, each comprising a sequence of
connected segments. The topology of the path is unrestricted: it can be concave or convex, can
contain multiple subpaths representing disjoint areas, and can intersect itself in arbitrary ways.
The h operator explicitly shall connect the end of a subpath back to its starting point; such a subpath is
said to be closed. A subpath that has not been explicitly closed is said to be open.

As discussed in 8.2, "Graphics objects", a path object is defined by a sequence of operators to construct
the path, followed by one or more operators to paint the path or to use it as a clipping boundary. PDF
path operators fall into three categories:

| • | Path construction operators (8.5.2, "Path construction operators") define the geometry of a path. A |

path is constructed by sequentially applying one or more of these operators.
| • | Path-painting operators (8.5.3, "Path-painting operators") end a path object, usually causing the |

object to be painted on the current page in any of a variety of ways.
| • | Clipping path operators (8.5.4, "Clipping path operators"), invoked immediately before a path- |

painting operator, cause the path object also to be used for clipping of subsequent graphics
objects.


## Page 185

8.5.2 Path construction operators

8.5.2.1 General

A path description is built up through the invocation of one or more path construction operators that
add segments to it. The path construction operators may be invoked in any sequence, but the first one
invoked shall be m or re to begin a new subpath. The path definition may conclude with the
application of a path-painting operator such as S, f, or b (see 8.5.3, "Path-painting operators"); this
operator may optionally be preceded by one of the clipping path operators W or W* (8.5.4, "Clipping
path operators").

NOTE          Note that the path construction operators do not place any marks on the page; only the painting
| operators do that. A path definition is not complete until a path | -painting operator has been applied to it. |

The path currently under construction is called the current path. In PDF (unlike PostScript), the
current path is not part of the graphics state and is not saved and restored along with the other
| graphics state parameters. PDF paths sh | all be strictly internal objects with no explicit representation. |

After the current path has been painted, it shall become no longer defined; there is then no current
| path until a new one is begun with the | m or re operator. |

The trailing endpoint of the segment most recently added to the current path is referred to as the
current point. If the current path is empty, the current point shall be undefined. Most operators that
| add a segment to the current path start at the current point; if the current point is | undefined, an error |

shall be generated.

"Table 58 — Path construction operators" shows the path construction operators. All operands shall be
numbers denoting coordinates in user space.

Table 58 — Path construction operators

| Operands | Operator | Description |

| x y | m | Begin a new subpath by moving the current point to coordinates (x, y), omitting any connecting line segment. If the previous path construction operator in the current path was also m, the new m overrides it; no vestige of the previous m operation remains in the path. |

| x y | l (lowercase L) | Append a straight line segment from the current point to the point (x, y). The new current point shall be (x, y). |

| x1 y1 x2 y2 x3 y3  c | Append a cubic Bézier curve to the current path. The curve shall extend from the current point to the point (x3, y3), using (x1, y1 ) and (x2, y2 ) as the Bézier control points (see 8.5.2.2, "Cubic Bézier curves"). The new current point shall be (x3, y3 ). |

| x2 y2 x3 y3 | v | Append a cubic Bézier curve to the current path. The curve shall extend from the current point to the point (x3, y3 ), using the current point and (x2, y2 ) as the Bézier control points (see 8.5.2.2, "Cubic Bézier curves"). The new current point shall be (x3, y3 ). |


## Page 186

| Operands | Operator | Description |

| x1 y1 x3 y3 | y | Append a cubic Bézier curve to the current path. The curve shall extend from the current point to the point (x3, y3 ), using (x1, y1 ) and (x3, y3 ) as the Bézier control points (see 8.5.2.2, "Cubic Bézier curves"). The new current point shall be (x3, y3 ). |

| — | h | Close the current subpath by appending a straight line segment from the current point to the starting point of the subpath. If the current subpath is already closed, h shall do nothing. This operator terminates the current subpath. Appending another segment to the current path shall begin a new subpath, even if the new segment begins at the endpoint reached by the h operation. |

| x y width | re | Append a rectangle to the current path as a complete subpath, with |
| --- | --- | --- |
| height | lower-left corner (x, y) and dimensions width and height in user space. The operation: x y width height re |  |

is equivalent to:
𝑥 𝑦 m
( 𝑥 +  𝑤𝑖𝑑𝑡ℎ ) y 𝐥
( 𝑥 +  𝑤𝑖𝑑𝑡ℎ )( 𝑦   +  ℎ𝑒𝑖𝑔ℎ𝑡 ) 𝐥
𝑥 ( 𝑦+   ℎ𝑒𝑖𝑔ℎ𝑡 ) 𝐥
h

8.5.2.2 Cubic Bézier curves

Curved path segments shall be specified as cubic Bézier curves. Such curves shall be defined by four
points: the two endpoints (the current point P0 and the final point P3) and two control points P1 and
| P2.Given the coordinates of the four points, the curv | e shall be generated by varying the parameter | t |

from 0.0 to 1.0 in the following equation:
| 𝑅(𝑡)= | (1 − | 𝑡)3𝑃0  +  3𝑡(1 | −  𝑡)2𝑃1 | +  3𝑡2(1 | −  𝑡)𝑃2 | +  𝑡3𝑃3 |

When t = 0.0, the value of the function R(t) coincides with the current point P0 ; when t = 1.0, R(t)
coincides with the final point P3. Intermediate values of t generate intermediate points along the curve.
| The curve does not, in general, pass thro | ugh the two control points P1 and P2. |

NOTE 1        Cubic Bézier curves have two useful properties:
| o | The curve can be very quickly split into smaller pieces for rapid rendering. |
| --- | --- |
| o | The curve is contained within the convex hull of the four points defining the curve, most easily visualized as the polygon obtained by stretching a rubber band around the outside of the four points. This property allows rapid testing of whether the curve lies completely outside the visible region, and hence does not have to be rendered. |

NOTE 2        The Bibliography lists several books that describe cubic Bézier curves in more depth.
The most general PDF operator for constructing curved path segments is the c operator, which
specifies the coordinates of points P1, P2, and P3 explicitly, as shown in "Figure 16 — Cubic Bézier curve
generated by the c operator". (The starting point, P0, is defined implicitly by the current point.)


## Page 187

Figure 16 — Cubic Bézier curve generated by the c operator
Two more operators, v and y, each specify one of the two control points implicitly (see "Figure 17 —
Cubic Bézier curves generated by the v and y operators"). In both of these cases, one control point and
the final point of the curve shall be supplied as operands; the other control point shall be implied:

| • | For the v operator, the first control point shall coincide with initial point of the curve. |
| --- | --- |
| • | For the y operator, the second control point shall coincide with final point of the curve. |

Figure 17 — Cubic Bézier curves generated by the v and y operators

8.5.3 Path-painting operators

8.5.3.1 General

The path-painting operators end a path object, causing it to be painted on the current page in the
manner that the operator specifies. The principal path-painting operators shall be S (for stroking) and f
(for filling). Variants of these operators combine stroking and filling in a single operation or apply
different rules for determining the area to be filled. Attempting to execute a painting operator when
the current path is undefined (at the beginning of a new page or immediately after a painting operator
has been executed) shall generate an error. "Table 59 — Path-painting operators" lists all the path-


## Page 188

painting operators.

Table 59 — Path-painting operators

| Operands | Operator | Description |

| — | S | Stroke the path. |

| — | s | Close and stroke the path. This operator shall have the same effect as the sequence h S. |

| — | f | Fill the path, using the non-zero winding number rule to determine the region to fill (see 8.5.3.3.2, "Non-zero winding number rule"). Any subpaths that are open shall be implicitly closed before being filled. |

| — | F | Equivalent to f; deprecated in PDF 2.0 and included only for compatibility. Although PDF readers shall be able to accept this operator, PDF writers should use f instead. |

| — | f* | Fill the path, using the even-odd rule to determine the region to fill (see 8.5.3.3.3, "Even- odd rule"). |

| — | B | Fill and then stroke the path, using the non-zero winding number rule to determine the region to fill. This operator shall produce the same result as constructing two identical path objects, painting the first with f and the second with S. |

NOTE      The filling and stroking portions of the operation consult different values of several
graphics state parameters, such as the current colour. See also 11.7.4.4, "Special pathpainting considerations".

| — | B* | Fill and then stroke the path, using the even-odd rule to determine the region to fill. This operator shall produce the same result as B, except that the path is filled as if with f* instead of f. See also 11.7.4.4, "Special path-painting considerations". |

| — | b | Close, fill, and then stroke the path, using the non-zero winding number rule to determine the region to fill. This operator shall have the same effect as the sequence h B. See also 11.7.4.4, "Special path-painting considerations". |

| — | b* | Close, fill, and then stroke the path, using the even-odd rule to determine the region to fill. This operator shall have the same effect as the sequence h B*. See also 11.7.4.4, "Special path-painting considerations". |

| — | n | End the path object without filling or stroking it. This operator shall be a path-painting |
| --- | --- | --- |
| no-op, used primarily for the side effect of changing the current clipping path ( | see 8.5.4, "Clipping path operators"). |  |

8.5.3.2 Stroking

The S operator shall paint a line along the current path. The stroked line shall follow each straight or
curved segment in the path, centred on the segment with sides parallel to it. Each of the path’s
subpaths shall be treated separately.

The results of the S operator shall depend on the current settings of various parameters in the graphics
state (see 8.4, "Graphics state", for further information on these parameters):

| • | The width of the stroked line is defined by the current line width parameter (8.4.3.2, "Line |

width").


## Page 189

| • | The colour or pattern of the line is defined by the current colour and colour space for stroking |

operations.
| • | The line may be painted either solid or with a dash pattern, as specified by the current line dash |

pattern (8.4.3.6, "Line dash pattern").
| • | If a subpath is open, the unconnected ends shall be treated according to the current line cap style, |

which may be butt, rounded, or square (see 8.4.3.3, "Line cap style").
| • | Wherever two consecutive segments are connected, the joint between them shall be treated |

according to the current line join style, which may be mitered, rounded, or beveled (see 8.4.3.4,
"Line join style"). Mitered joins shall be subject to the current miter limit (see 8.4.3.5, "Miter
limit").
Points at which unconnected segments happen to meet or intersect receive no special treatment. In
particular, using an explicit l operator to give the appearance of closing a subpath, rather than using h,
may result in a messy corner, because line caps are applied instead of a line join.

| • | The stroke adjustment parameter (PDF 1.2) specifies that coordinates and line widths be adjusted |

automatically to produce strokes of uniform thickness despite rasterization effects (see 10.7.5,
"Automatic stroke adjustment").
| • | For transparency compositing purposes a path shall be treated as a single graphics object as |

described in 11.6.2, "Specifying source and backdrop colours".
If a subpath is degenerate (consists of a single-point closed path or of two or more points at the same
coordinates), the S operator shall paint it only if round line caps have been specified, producing a filled
circle centred at the single point. If butt or projecting square line caps have been specified, S shall
produce no output, because the orientation of the caps would be indeterminate. This rule shall apply
only to zero-length subpaths of the path being stroked, and not to zero-length dashes in a dash pattern
of a non-degenerate subpath. In the latter case, the line caps shall always be painted, since their
orientation is determined by the direction of the underlying path except in the case of a degenerate
subpath. A single-point open subpath (specified by a trailing m operator) shall produce no output.

8.5.3.3 Filling

8.5.3.3.1 General

The f operator shall use the current nonstroking colour to paint the entire region enclosed by the
current path. If the path consists of several disconnected subpaths, f shall paint the insides of all
subpaths, considered together.

Any subpaths that are open shall be implicitly closed before being filled, except that if the last subpath
in the path is a single-point open subpath (specified by a trailing m operator), it shall be disregarded
and not considered to be part of the path. If a subpath is degenerate (consists entirely of one or more
points at the same coordinates), the subpath shall be considered to enclose the single device pixel lying
under that point; the result is device-dependent and not generally useful.

For a simple path, it is intuitively clear what region lies inside. However, for a more complex path, it is
not always obvious which points lie inside the path. For more detailed information, see 10.7.4, "Scan
conversion rules".


## Page 190

EXAMPLE            A path that intersects itself or has one subpath that encloses another.

The path machinery shall use one of two rules for determining which points lie inside a path: the nonzero winding number rule and the even-odd rule, both discussed in detail below. The non-zero winding
number rule is more versatile than the even-odd rule and shall be the standard rule the f operator uses.
Similarly, the W operator shall use this rule to determine the inside of the current clipping path. The
even-odd rule is occasionally useful for special effects or for compatibility with other graphics systems;
the f* and W* operators invoke this rule.

8.5.3.3.2 Non-zero winding number rule

The non-zero winding number rule determines whether a given point is inside a path by conceptually
drawing a ray from that point to infinity in any direction and then examining the places where a
segment of the path crosses the ray. Starting with a count of 0, the rule adds 1 each time a path
segment crosses the ray from left to right and subtracts 1 each time a segment crosses from right to
left. After counting all the crossings, if the result is 0, the point is outside the path; otherwise, it is
inside.

The method just described does not specify what to do if a path segment coincides with or is tangent to
the chosen ray. Since the direction of the ray is arbitrary, the rule simply chooses a ray that does not
encounter such problem intersections.

For simple convex paths, the non-zero winding number rule defines the inside and outside as one
would intuitively expect. The more interesting cases are those involving complex or self-intersecting
paths like the ones shown in "Figure 18 — Non-zero winding number rule". For a path consisting of a
five-pointed star, drawn with five connected straight line segments intersecting each other, the rule
considers the inside to be the entire area enclosed by the star, including the pentagon in the centre. For
a path composed of two concentric circles, the areas enclosed by both circles are considered to be
inside, provided that both are drawn in the same direction. If the circles are drawn in opposite
directions, only the doughnut shape between them is inside, according to the rule; the doughnut hole is
outside.

Figure 18 — Non-zero winding number rule

8.5.3.3.3 Even-odd rule

An alternative to the non-zero winding number rule is the even-odd rule. This rule determines whether
a point is inside a path by drawing a ray from that point in any direction and simply counting the


## Page 191

number of path segments that cross the ray, regardless of direction. If this number is odd, the point is
inside; if even, the point is outside. This yields the same results as the non-zero winding number rule
for paths with simple shapes, but produces different results for more complex shapes.

"Figure 19 — Even-odd rule" shows the effects of applying the even-odd rule to complex paths. For the
five-pointed star, the rule considers the triangular points to be inside the path, but not the pentagon in
the centre. For the two concentric circles, only the doughnut shape between the two circles is
considered inside, regardless of the directions in which the circles are drawn.

Figure 19 — Even-odd rule

8.5.4 Clipping path operators

The graphics state shall contain a current clipping path that limits the regions of the page affected by
painting operators. The closed subpaths of this path shall define the area that can be painted. Marks
falling inside this area shall be applied to the page; those falling outside it shall not be. Subclause
8.5.3.3, "Filling" defines what is inside a path as well as stating rules for closing paths and for
| degenerate paths. For a given path definition, the same area that would be filled by the | f operator is the |

area that would be used for a clip.

In the context of the transparent imaging model (PDF 1.4), the current clipping path constrains an
object’s shape (see 11.2, "Overview of transparency"). The effective shape is the intersection of the
object’s intrinsic shape with the clipping path; the source shape value shall be 0.0 outside this
| intersection. Similarly, the shape of a | transparency group (defined as the union of the shapes of its |

constituent objects) shall be influenced both by the clipping path in effect when each of the objects is
| painted and by the one in effect at the time the group’s results are painted onto its bac | kdrop. |

| The initial clipping path shall include the entire page. A clipping path operator ( | W or W*, shown in |

"Table 60 — Clipping path operators") may appear after the last path construction operator and before
the path-painting operator that terminates a path object. Although the clipping path operator appears
before the painting operator, it shall not alter the clipping path at the point where it appears. Rather, it
shall modify the effect of the succeeding painting operator. After the path has been painted, the
clipping path in the graphics state shall be set to the intersection of the current clipping path and the
newly constructed path.


## Page 192

Table 60 — Clipping path operators

| Operands | Operator | Description |

| — | W | Modify the current clipping path by intersecting it with the current path, using the non-zero winding number rule to determine which regions lie inside the clipping path. |

| — | W* | Modify the current clipping path by intersecting it with the current path, using the even-odd rule to determine which regions lie inside the clipping path. |

NOTE 1       In addition to path objects, text objects can also be used for clipping; see 9.3.6, "Text rendering
mode".
The n operator (see "Table 59 — Path-painting operators") is a no-op path-painting operator; it shall
cause no marks to be placed on the page, but can be used with a clipping path operator to establish a
new clipping path. That is, after a path has been constructed, the sequence W n shall intersect that path
with the current clipping path and shall establish a new clipping path.

NOTE 2       There is no way to enlarge the current clipping path or to set a new clipping path without
reference to the current one. However, since the clipping path is part of the graphics state, its
effect can be localized to specific graphics objects by enclosing the modification of the clipping
path and the painting of those objects between a pair of q and Q operators (see 8.4.2, "Graphics
state stack"). Execution of the Q operator causes the clipping path to revert to the value that was
saved by the q operator before the clipping path was modified.

8.6 Colour spaces

8.6.1 General

PDF includes facilities for specifying the colours of graphics objects. The colour facilities are divided
into two parts:

| • | Colour specification. A PDF file may specify abstract colours in a device-independent way. Colours |

may be described in any of a variety of colour systems, or colour spaces. Some colour spaces are
related to device colour representation (grayscale, RGB, CMYK), others to human visual
perception (CIE-based). Certain special features are also modelled as colour spaces: patterns,
colour mapping, separations, and high-fidelity and multitone colour.
| • | Colour rendering. A PDF processor shall reproduce colours on the raster output device by a |

multiple-step process that includes some combination of colour conversion, gamma correction,
halftoning, and scan conversion. Some aspects of this process use information that is specified in
PDF. However, unlike the facilities for colour specification, the colour-rendering facilities are
device-dependent and should not be included in a page description.
When the device is a subtractive colour device, "rendering for separations" may be implemented (see
10.8.2, "Separations"). In addition, a PDF reader may optionally support "separation simulation" for
any device (see 10.8.3, "Separation simulation"). In both cases overprinting (see 8.6.7, "Overprint
control") may be enabled. "Figure 20 — Colour specification" and "Figure 21 — Colour rendering"
| illustrate the division between PDF’s (d | evice-independent) colour specification and (device | - |
| --- | --- | --- |
| dependent) colour-rendering facilities. This | subclause describes the colour specification features, |  |

covering everything that PDF documents need to specify colours. The facilities for controlling colour


## Page 193

rendering are described in clause 10, "Rendering"; a PDF processor should use these facilities only to
configure or calibrate an output device or to achieve special device-dependent effects.

8.6.2 Colour values

As described in 8.5.3, "Path-painting operators", marks placed on the page by operators such as f and S
shall have a colour that is determined by the current colour parameter of the graphics state. A colour
value consists of one or more colour components, which are usually numbers. A gray level shall be
specified by a single number ranging from 0.0 (black) to 1.0 (white). Full colour values may be
specified in any of several ways; a common method uses three numeric values to specify red, green,
and blue components.

Colour values shall be interpreted according to the current colour space, another parameter of the
graphics state. A PDF content stream first selects a colour space by invoking the CS operator (for the
stroking colour) or the cs operator (for the nonstroking colour). It then selects colour values within
that colour space with the SC operator (stroking) or the sc operator (nonstroking). There are also
convenience operators — G, g, RG, rg, K, and k — that select both a colour space and a colour value
within it in a single step. "Table 73 — Colour operators" lists all the colour-setting operators.

Sampled images (see 8.9, "Images") specify the colour values of individual samples with respect to a
colour space designated by the image object itself. While these values are independent of the current
colour space and colour parameters in the graphics state, all later stages of colour processing shall
treat them in exactly the same way as colour values specified with the SC or sc operator.

8.6.3 Colour space families

| Colour spaces are classified | into colour space families | . Spaces within a family share the same general |

characteristics; they shall be distinguished by parameter values supplied at the time the space is
specified. The families fall into three broad categories:

| • | Device colour spaces directly specify colours or shades of gray that the output device shall |
| --- | --- |
| produce. They provide a variety of colour specification methods, including grayscale, | RGB (red- |

green-blue), and CMYK (cyan-magenta-yellow-black), corresponding to the colour space families
DeviceGray, DeviceRGB, and DeviceCMYK. Since each of these families consists of just a single
colour space with no parameters, they may be referred to as the DeviceGray, DeviceRGB, and
DeviceCMYK colour spaces.
| • | CIE-based colour spaces shall be based on an international standard for colour specification |

created by the Commission Internationale de l’Éclairage (International Commission on
Illumination). These spaces specify colours in a way that is independent of the characteristics of
| any particular output device. Colour space families in this category include | CalGray, CalRGB, Lab, |

and ICCBased. Individual colour spaces within these families shall be specified by means of
dictionaries containing the parameter values needed to define the space.
| • | Special colour spaces add features or properties to an underlying colour space. They include |

facilities for patterns, colour mapping, separations, and high-fidelity and multitone colour. The
corresponding colour space families are Pattern, Indexed, Separation, and DeviceN. Individual
colour spaces within these families shall be specified by means of additional parameters.


## Page 194

Figure 20 — Colour specification


## Page 195

Figure 21 — Colour rendering


## Page 196

"Table 61 — Colour space families" summarises the colour space families in PDF.

Table 61 — Colour space families

| Device | CIE-based | Special |

| DeviceGray (PDF 1.1) | CalGray (PDF 1.1) | Indexed (PDF 1.1) |

| DeviceRGB (PDF 1.1) | CalRGB (PDF 1.1) | Pattern (PDF 1.2) |

| DeviceCMYK (PDF 1.1) | Lab (PDF 1.1) | Separation (PDF 1.2) |

| ICCBased (PDF 1.3) | DeviceN (PDF 1.3) |

A colour space shall be defined by an array object whose first element is a name object identifying the
colour space family. The remaining array elements, if any, are parameters that further characterise the
colour space; their number and types vary according to the particular family. For families that do not
require parameters, the colour space may be specified simply by the family name itself instead of an
array.

A colour space shall be specified in one of two ways:

| • | Within a content stream, the CS or cs operator establishes the current colour space parameter in |

the graphics state. The operand shall always be name object, which either identifies one of the
colour spaces that need no additional parameters (DeviceGray, DeviceRGB, DeviceCMYK, or
some cases of Pattern) or shall be used as a key in the ColorSpace subdictionary of the current
resource dictionary (see 7.8.3, "Resource dictionaries"). In the latter case, the value of the
dictionary entry in turn shall be a colour space array or name. A colour space array shall never be
inline within a content stream.
| • | Outside a content stream, certain objects, such as image XObjects, shall specify a colour space as |

an explicit parameter, often associated with the key ColorSpace. In this case, the colour space
array or name shall always be defined directly as a PDF object, not by an entry in the ColorSpace
resource subdictionary. This convention also applies when colour spaces are defined in terms of
other colour spaces.
The following operators shall set the current colour space and current colour parameters in the
graphics state:

| • | CS shall set the stroking colour space; cs shall set the nonstroking colour space. |
| --- | --- |
| • | SC and SCN shall set the stroking colour; sc and scn shall set the nonstroking colour. Depending |

on the colour space, these operators shall have one or more operands, each specifying one
component of the colour value.
| • | G, RG, and K shall set the stroking colour space implicitly and the stroking colour as specified by |

the operands; g, rg, and k do the same for the nonstroking colour space and colour.

8.6.4 Device colour spaces

8.6.4.1 General

The device colour spaces enable a page description to specify colour values that are directly related to
their representation on an output device. Colour values in these spaces map directly (or by simple
conversions) to the application of device colourants, such as quantities of ink or intensities of display


## Page 197

phosphors. This enables a PDF writer to control colours precisely for a particular device, but the
results might not be consistent from one device to another.

Output devices form colours either by adding light sources together or by subtracting light from an
illuminating source. Computer displays and film recorders typically add colours; printing inks typically
subtract them. These two ways of forming colours give rise to two complementary methods of colour
specification, called additive and subtractive colour (see "Figure 22 — Additive and subtractive
colour"). The most widely used forms of these two types of colour specification are known as RGB and
CMYK, respectively, for the names of the primary colours on which they are based. They correspond to
the following device colour spaces:

| • | DeviceGray controls the intensity of achromatic light, on a scale from black to white. |
| --- | --- |
| • | DeviceRGB controls the intensities of red, green, and blue light, the three additive primary |

colours used in displays.
| • | DeviceCMYK controls the concentrations of cyan, magenta, yellow, and black inks, the four |

subtractive process colours used in printing.
NOTE         Although the notion of explicit colour spaces is a PDF 1.1 feature, the operators for specifying
colours in the device colour spaces — G, g, RG, rg, K, and k — are available in all versions of PDF.
Beginning with PDF 1.2, colours specified in device colour spaces can optionally be remapped
systematically into other colour spaces; see 8.6.5.6, "Default colour spaces".

Figure 22 — Additive and subtractive colour
In the transparent imaging model (PDF 1.4), the use of device colour spaces is subject to special
treatment within a transparency group whose group colour space is CIE-based (see 11.4,
"Transparency groups" and 11.6.6, "Transparency group XObjects"). In particular, the device colour
space operators should be used only if device colour spaces have been remapped to CIE-based spaces
by means of the default colour space mechanism. Otherwise, the colour results are implementationdependent and unpredictable.

8.6.4.2 DeviceGray colour space

Black, white, and intermediate shades of gray are special cases of full colour. A grayscale value shall be
represented by a single number in the range 0.0 to 1.0, where 0.0 corresponds to black, 1.0 to white,
and intermediate values to different gray levels.

EXAMPLE            This example shows alternative ways to select the DeviceGray colour space and a specific gray level within
that space for stroking operations.

| /DeviceGray CS | %Set DeviceGray colour space |


## Page 198

| gray SC | %Set gray level |
| --- | --- |
| gray G | %Set both in one operation |

The CS and SC operators shall select the current stroking colour space and current stroking colour
separately; G shall set them in combination. (The cs, sc, and g operators shall perform the same
functions for nonstroking operations.) Setting either current colour space to DeviceGray shall
initialise the corresponding current colour to 0.0.

8.6.4.3 DeviceRGB colour space

Colours in the DeviceRGB colour space shall be specified according to the additive RGB (red-greenblue) colour model, in which colour values shall be defined by three components representing the
intensities of the additive primary colourants red, green, and blue. Each component shall be specified
by a number in the range 0.0 to 1.0, where 0.0 shall denote the complete absence of a primary
component and 1.0 shall denote maximum intensity.

EXAMPLE            This example shows alternative ways to select the DeviceRGB colour space and a specific colour within that
space for stroking operations.

| /DeviceRGB CS | %Set DeviceRGB colour space |
| --- | --- |
| red green blue SC | %Set colour |

| red green blue RG | %Set both in one operation |
| --- | --- |
| 1 0 0 RG | %Set a pure red colour for stroking operations |

The CS and SC operators shall select the current stroking colour space and current stroking colour
separately; RG shall set them in combination. The cs, sc, and rg operators shall perform the same
functions for nonstroking operations. Setting either current colour space to DeviceRGB shall initialise
the red, green, and blue components of the corresponding current colour to 0.0.

8.6.4.4 DeviceCMYK colour space

The DeviceCMYK colour space allows colours to be specified according to the subtractive CMYK (cyan-
| magenta-yellow-black) model typical of printers and other paper | -based output devices. The four |

components in a DeviceCMYK colour value shall represent the concentrations of these process
| colourants. Each component shall be a number in the range 0.0 to | 1.0, where 0.0 shall denote the |
| --- | --- |
| complete absence of a process | colourant and 1.0 shall denote maximum concentration (absorbs as |

much as possible of the additive primary).

NOTE         As much as the reflective colours (CMYK) decrease reflection with increased ink v                  alues and
radiant colours (RGB) increases the intensity of colours with increased values the values work in
an opposite manner.

EXAMPLE            The following shows alternative ways to select the    DeviceCMYK    colour space and a specific colour within
that space for stroking operations.

| /DeviceCMYK CS | %Set DeviceCMYK colour space |
| --- | --- |
| cyan magenta yellow black SC | %Set colour |
| cyan magenta yellow black K | %Set both in one operation |

The CS and SC operators shall select the current stroking colour space and current stroking colour
separately; K shall set them in combination. The cs, sc, and k operators shall perform the same
functions for nonstroking operations. Setting either current colour space to DeviceCMYK shall


## Page 199

initialise the cyan, magenta, and yellow components of the corresponding current colour to 0.0 and the
black component to 1.0.

8.6.5 CIE-Based colour spaces

8.6.5.1 General

Calibrated colour in PDF shall be defined in terms of an international standard used in the graphic arts,
television, and printing industries. CIE-based colour spaces enable a page description to specify colour
values in a way that is related to human visual perception. The goal is for the same colour specification
to produce consistent results on different output devices, within the limitations of each device; "Figure
23 — Uncalibrated colour" illustrates the kind of variation in colour reproduction that can result from
the use of uncalibrated colour on different devices. PDF 1.1 supports three CIE-based colour space
families, named CalGray, CalRGB, and Lab; PDF 1.3 added a fourth, named ICCBased.

Figure 23 — Uncalibrated colour
A PDF reader shall ignore CalCMYK colour space attributes and render colours specified in this family
as if they had been specified using DeviceCMYK.

NOTE 1       In PDF 1.1, a colour space family named CalCMYK was partially defined, with the expectation
that its definition would be completed in a future version. However, this feature has been
completely removed. PDF 1.3 and later versions support calibrated four-component colour
spaces by means of ICC profiles (see 8.6.5.5, "ICCBased colour spaces").
NOTE 2       The details of the CIE colorimetric system and the theory on which it is based are beyond the
scope of this specification; see the Bibliography for sources of further information. The
semantics of CIE-based colour spaces are defined in terms of the relationship between the


## Page 200

space’s components and the tristimulus values X, Y, and Z of the CIE 1931 XYZ space. The CalRGB
and Lab colour spaces (PDF 1.1) are special cases of three-component CIE-based colour spaces,
known as CIE-based ABC colour spaces. These spaces are defined in terms of a two-stage,
nonlinear transformation of the CIE 1931 XYZ space. The formulation of such colour spaces
models a simple zone theory of colour vision, consisting of a nonlinear trichromatic first stage
combined with a nonlinear opponent-colour second stage. This formulation allows colours to be
digitised with minimum loss of fidelity, an important consideration in sampled images.
Colour values in a CIE-based ABC colour space shall have three components, arbitrarily named A, B, and
C. The first stage shall transform these components by first forcing their values to a specified range,
then applying decoding functions, and then multiplying the results by a 3-by-3 matrix, producing three
intermediate components arbitrarily named L, M, and N. The second stage shall transform these
intermediate components in a similar fashion, producing the final X, Y, and Z components of the CIE

1931 XYZ space (see "Figure 24 — Component transformations in a CIE-based ABC colour space").

Figure 24 — Component transformations in a CIE-based ABC colour space
Colour spaces in the CIE-based families shall be defined by an array

[name dictionary]

where name is the name of the family and dictionary is a dictionary containing parameters that further
characterise the space. The entries in this dictionary have specific interpretations that depend on the
colour space; some entries are required and some are optional. See the subclauses on specific colour
space families for details.

Setting the current stroking or nonstroking colour space to any CIE-based colour space shall initialise
all components of the corresponding current colour to 0.0 (unless the range of valid values for a given
component does not include 0.0, in which case the nearest valid value shall be substituted.)

NOTE 3       The model and terminology used here — CIE-based ABC (above) and CIE-based A (below) — are
derived from the PostScript language, which supports these colour space families in their full
generality. PDF supports specific useful cases of CIE-based ABC and CIE-based A spaces; most
others can be represented as ICCBased spaces.

8.6.5.2 CalGray colour spaces

A CalGray colour space (PDF 1.1) is a special case of a single-component CIE-based colour space,
known as a CIE-based A colour space. This type of space is the one-dimensional (and usually
achromatic) analog of CIE-based ABC spaces. Colour values in a CIE-based A space shall have a single
component, arbitrarily named A. "Figure 25 — Component transformations in a CIE-based A colour
space" illustrates the transformations of the A component to X, Y, and Z components of the CIE 1931


## Page 201

XYZ space.

Figure 25 — Component transformations in a CIE-based A colour space
A CalGray colour space shall be a CIE-based A colour space with only one transformation stage instead
of two. In this type of space, A represents the gray component of a calibrated gray space. This
component shall be in the range 0.0 to 1.0; component values falling outside that range shall be
adjusted to the nearest valid value without error indication. The decoding function (denoted by
"Decode A" in "Figure 25 — Component transformations in a CIE-based A colour space") is a gamma
function whose coefficient shall be specified by the Gamma entry in the colour space dictionary (see
"Table 62 — Entries in a CalGray colour space dictionary"). The transformation matrix denoted by
"Matrix A" in the figure is derived from the dictionary’s WhitePoint entry, as described below. Since
there is no second transformation stage, "Decode LMN" and "Matrix LMN" shall be implicitly taken to
be identity transformations.

Table 62 — Entries in a CalGray colour space dictionary

| Key | Type | Value |

| WhitePoint | array | (Required) An array of three numbers [XW YW ZW] specifying the tristimulus value, in the CIE 1931 XYZ space, of the diffuse white point; see 8.6.5.3, "CalRGB colour spaces", for further discussion. The numbers XW and ZW shall be positive, and YW shall be equal to 1.0. |

| BlackPoint | array | (Optional) An array of three numbers [XB YB ZB] specifying the tristimulus value, in the CIE 1931 XYZ space, of the diffuse black point; see 8.6.5.3, "CalRGB colour spaces", for further discussion. All three of these numbers shall be non-negative. Default value: [0.0 0.0 0.0]. |

| Gamma | number | (Optional) A number G defining the gamma for the gray (A) component. G shall be positive and is generally greater than or equal to 1. Default value: 1. |

The transformation defined by the Gamma and WhitePoint entries is
𝑋  =  𝐿  =  𝑋𝑊   × 𝐴𝐺
𝑌  =  𝑀   =  𝑌𝑊  ×  𝐴𝐺
𝑍  =  𝑁  =  𝑍𝑊   ×  𝐴𝐺
In other words, the A component shall be first decoded by the gamma function, and the result shall be
multiplied by the components of the white point to obtain the L, M, and N components of the
intermediate representation. Since there is no second stage, the L, M, and N components shall also be


## Page 202

the X, Y, and Z components of the final representation.

EXAMPLE 1           The examples in this     subclause  illustrate interesting and useful special cases of      CalGray   spaces. This
example establishes a space consisting of the Y dimension of the CIE 1931 XYZ space with the CCIR XA/11–
recommended D65 white point.

[/CalGray
<</WhitePoint [0.9505 1.00 1.0890]>>
]

EXAMPLE 2           This example establishes a calibrated gray space with the CCIR XA/11–recommended D65 white point and
opto-electronic transfer function.

[/CalGray
<</WhitePoint [0.9505 1.00 1.0890]
/Gamma 2.222
>>
]

8.6.5.3 CalRGB colour spaces

A CalRGB colour space is a CIE-based ABC colour space with only one transformation stage instead of
two. In this type of space, A, B, and C represent calibrated red, green, and blue colour values. These
three colour components shall be in the range 0.0 to 1.0; component values falling outside that range
shall be adjusted to the nearest valid value without error indication. The decoding functions (denoted
by "Decode ABC" in "Figure 24 — Component transformations in a CIE-based ABC colour space") are
gamma functions whose coefficients shall be specified by the Gamma entry in the colour space
dictionary (see "Table 63 — Entries in a CalRGB colour space dictionary"). The transformation matrix
denoted by "Matrix ABC" in "Figure 24 — Component transformations in a CIE-based ABC colour
| space" shall be defined by the dictionary | ’s Matrix entry. Since there is no second transformation stage, |

"Decode LMN" and "Matrix LMN" shall be implicitly taken to be identity transformations.

Table 63 — Entries in a CalRGB colour space dictionary

| Key | Type | Value |

| WhitePoint | array | (Required) An array of three numbers [XW YW ZW] specifying the tristimulus value, in the CIE 1931 XYZ space, of the diffuse white point; see below for further discussion. The numbers XW and ZW shall be positive, and YW shall be equal to 1.0. |

| BlackPoint | array | (Optional) An array of three numbers [XK YK ZK] specifying the tristimulus value, in the CIE 1931 XYZ space, of the diffuse black point; see below for further discussion. All three of these numbers shall be non-negative. Default value: [0.0 0.0 0.0]. |

| Gamma | array | (Optional) An array of three numbers [GR GG GB] specifying the gamma for the red, green, and blue (A, B, and C) components of the colour space. Default value: [1.0 1.0 1.0]. |

| Matrix | array | (Optional) An array of nine numbers [XA YA ZA XB YB ZB XC YC ZC] specifying the linear interpretation of the decoded A, B, and C components of the colour space with respect to the final XYZ representation. Default value: the identity matrix [1 0 0 0 1 0 0 0 1]. |

The WhitePoint and BlackPoint entries in the colour space dictionary shall control the overall effect
of the CIE-based gamut mapping function described in subclause 10.3, "CIE-Based colour to device


## Page 203

colour". Typically, the colours specified by WhitePoint and BlackPoint shall be mapped to the nearly
lightest and nearly darkest achromatic colours that the output device is capable of rendering in a way
that preserves colour appearance and visual contrast.

WhitePoint represents the diffuse achromatic highlight, not a specular highlight. Specular highlights,
achromatic or otherwise, are often reproduced lighter than the diffuse highlight. BlackPoint
represents the diffuse achromatic shadow; its value is limited by the dynamic range of the input device.
In images produced by a photographic system, the values of WhitePoint and BlackPoint vary with
exposure, system response, and artistic intent; hence, their values are image-dependent.

The transformation defined by the Gamma and Matrix entries in the CalRGB colour space dictionary
shall be
𝑋  =  𝐿 =  𝑋𝐴  ×  𝐴𝐺𝑅  +  𝑋𝐵  ×  𝐵𝐺𝐺  +  𝑋𝐶  × 𝐶𝐺𝐵
| 𝑌  =  𝑀  =  𝑌𝐴 ×  𝐴𝐺𝑅  +  𝑌𝐵  × 𝐵𝐺𝐺 | +  𝑌𝐶 ×  𝐶𝐺𝐵 |

𝑍  =  𝑁  =  𝑍𝐴 ×  𝐴𝐺𝑅  +  𝑍𝐵  ×  𝐵𝐺𝐺  +  𝑍𝐶  ×  𝐶𝐺𝐵

The A, B, and C components shall first be decoded individually by the gamma functions. The results
shall be treated as a three-element vector and multiplied by Matrix (a 3-by-3 matrix) to obtain the L, M,
| and N components of the intermediate representation. Since there i | s no second stage, these shall also |

be the X, Y, and Z components of the final representation.

EXAMPLE           The following shows an example of a    CalRGB   colour space for the CCIR XA/11  –recommended D65 white
| point with 1.8 gammas and Sony Trinitron phosphor chroma | ticities. |

[/CalRGB
<</WhitePoint [0.9505 1.00 1.0890]
/Gamma [1.8000 1.8000 1.8000]
/Matrix [0.4497 0.2446 0.0252
0.3163 0.6720 0.1412
0.1845 0.0833 0.9227
]
>>
]
The parameters of a CalRGB colour space may be specified in terms of the CIE 1931 chromaticity
coordinates (xR, yR ), (xG, yG ), (xB, yB ) of the red, green, and blue phosphors, respectively, and the
chromaticity (xW, yW ) of the diffuse white point corresponding to a linear RGB value (R, G, B), where R,
G, and B should all equal 1.0. The standard CIE notation uses lowercase letters to specify chromaticity
| coordinates and uppercase letters to specify tristimulus values. Given this information, | Matrix and |

WhitePoint shall be calculated as follows:

| 𝑧 =  𝑦𝑊 | ×  ((𝑥𝐺  −  𝑥𝐵 )× | 𝑦𝑅  −  (𝑥𝑅 −  𝑥𝐵 )× | 𝑦𝐺 +  (𝑥𝑅  −  𝑥𝐺 )× | 𝑦𝐵 ) |

| 𝑌 | =  𝑦𝑅 ×  (𝑥𝐺  −  𝑥𝐵 )× | 𝑦𝑊 | − (𝑥𝑊 | −  𝑥𝐵 )× | 𝑦𝐺  +  (𝑥𝑊  −  𝑥𝐺 )× | 𝑦𝐵 |
| --- | --- | --- | --- | --- | --- | --- |
| 𝐴 | 𝑅 | 𝑧 |  |  |  |  |

| 𝑋 | =  𝑌  ×  𝑥R | Z | =  Y | × (1−𝑥R | − 1) |
| --- | --- | --- | --- | --- | --- |
| 𝐴 | 𝐴 | 𝑦R | A | A | 𝑦R |

| Y | =  𝑦G  × (𝑥R  −  𝑥B)× | 𝑦W  −  (𝑥W | − 𝑥B)× | 𝑦R  +  (𝑥W  −  𝑥R)× | 𝑦B |
| --- | --- | --- | --- | --- | --- |
| B | G | 𝑧 |  |  |  |


## Page 204

| X | =  Y | ×  𝑥G | Z | =  Y | ×  (1−𝑥G | −  1) |
| --- | --- | --- | --- | --- | --- | --- |
| B | B | 𝑦G | B | B | 𝑦G |  |

| Y | =  𝑦B  ×  (𝑥R  −  𝑥G)× | 𝑦W | −  (𝑥W | −  𝑥G)× | 𝑦R +  (𝑥W | −  𝑥R)× | 𝑦G |
| --- | --- | --- | --- | --- | --- | --- | --- |
| C | B | 𝑧 |  |  |  |  |  |

| X | =  Y  ×  𝑥B | Z | =  Y | ×  (1−𝑥B | − 1) |
| --- | --- | --- | --- | --- | --- |
| C | C | 𝑦B | C | C | 𝑦B |

| XW | = | XA  ×  R +  XB  ×  G+ | XC  ×  B |
| --- | --- | --- | --- |
| YW | =  YA  ×  R+ | YB  ×  G+ | YC  ×  B |
| ZW | =  ZA  ×  R+ | ZB  × G+ | ZC  ×  B |

8.6.5.4 Lab colour spaces

A Lab colour space is a CIE-based ABC colour space with two transformation stages (see "Figure 24 —
Component transformations in a CIE-based ABC colour space"). In this type of space, A, B, and C
represent the L*, a*, and b* components of a CIE 1976 L*a*b* space. The range of the first (L*)
component shall be 0 to 100; the ranges of the second and third (a* and b*) components shall be
defined by the Range entry in the colour space dictionary (see "Table 64 — Entries in a Lab colour
space dictionary"). Component values falling outside the specified range shall be adjusted to the
nearest valid value without error indication.

Table 64 — Entries in a Lab colour space dictionary

| Key | Type | Value |

| WhitePoint | array | (Required) An array of three numbers [XW YW ZW] that shall specify the tristimulus value, in the CIE 1931 XYZ space, of the diffuse white point; see 8.6.5.3, "CalRGB colour spaces" for further discussion. The numbers XW and ZW shall be positive, and YW shall be 1.0. |

| BlackPoint | array | (Optional) An array of three numbers [XB YB ZB] that shall specify the tristimulus value, in the CIE 1931 XYZ space, of the diffuse black point; see 8.6.5.3, "CalRGB colour spaces" for further discussion. All three of these numbers shall be non- negative. Default value: [0.0 0.0 0.0]. |

| Range | array | (Optional) An array of four numbers [amin amax bmin bmax] that shall specify the range of valid values for the a* and b* (B and C) components of the colour space — that is, |
| --- | --- | --- |
| 𝑎𝑚𝑖𝑛 | ≤  𝑎  ∗≤  𝑎𝑚𝑎𝑥 |  |

and
| 𝑏𝑚𝑖𝑛 | ≤  𝑏∗≤ | 𝑏𝑚𝑎𝑥 |

Component values falling outside the specified range shall be adjusted to the nearest
valid value without error indication.
Default value: [-100 100 -100 100]

"Figure 26 — Lab colour space" illustrates the coordinates of a typical Lab colour space; "Figure 27 —


## Page 205

Colour gamuts" compares the gamuts (ranges of representable colours) for L*a*b*, RGB, and CMYK
spaces.

Figure 26 — Lab colour space

Figure 27 — Colour gamuts
A Lab colour space shall not specify explicit decoding functions or matrix coefficients for either stage of
the transformation from L*a*b* space to XYZ space (denoted by "Decode ABC", "Matrix ABC", "Decode
| LMN", and "Matrix LMN" in "Figure 24 — Component transformations in a CIE | -based ABC colour |

space"). Instead, these parameters shall have constant implicit values. The first transformation stage
shall be defined by the equations
𝐿∗ +  16     𝑎∗
| 𝐿  = | 116 | +  500 𝐿∗ +  16 |
| --- | --- | --- |
| 𝑀 | = | 116 |
| 𝐿∗ +  16 | 𝑏∗ |  |
| 𝑁  = | 116 | −  200 |

The second transformation stage shall be

𝑋  =  𝑋𝑊   × 𝑔(𝐿)
𝑌  =  𝑌𝑊  ×  𝑔(𝑀)
𝑍  =  𝑍𝑊  ×  𝑔(𝑁)
where the function g(x) shall be defined as
| 𝑔(𝑥)= | 𝑥3 | if 𝑥≥ | 6 |


## Page 206

| 𝑔(𝑥)= | 108  ×  (𝑥  −  4 ) | otherwise |
| --- | --- | --- |
| 841 | 29 |  |

EXAMPLE             The following defines the CIE 1976 L*a*b* space with the CCIR XA/11–recommended D65 white point (see
ITU Recommendation BT.709). The a* and b* components, although theoretically unbounded, are defined
to lie in the useful range -128 to +127.

[/Lab
<</WhitePoint [0.9505 1.00 1.0890]
/Range [-128 127 -128 127]
>>
]

8.6.5.5 ICCBased colour spaces

ICCBased colour spaces (PDF 1.3) shall be based on a cross-platform colour profile as defined by the
International Color Consortium (ICC). Unlike the CalGray, CalRGB, and Lab colour spaces, which are
characterised by entries in the colour space dictionary, an ICCBased colour space shall be
characterised by a sequence of bytes in a standard format. Details of the profile format can be found in
the ICC specification.

An ICCBased colour space shall be an array: [/ICCBased stream]

| The stream shall contain the ICC profile. Besides the usual entries common to all streams (see | "Table 5 |

— Entries common to all stream dictionaries"), the profile stream shall have the additional entries
| listed in "Table 65 — Additional entries specific to an ICC profile stream d | ictionary". |

Table 65 — Additional entries specific to an ICC profile stream d                           ictionary

| Key | Type | Value |

| N | integer | (Required) The number of colour components in the colour space described by the ICC profile data. This number shall match the number of components actually in the ICC profile. Valid values for N: 1, 3, or 4. |  |
| --- | --- | --- | --- |
| Alternate | name or | (Optional) An alternate colour space that shall be used in case the one specified in the |  |
| array | stream data is not supported. PDF readers should not use this colour space. The alternate space may be any valid colour space (except a Pattern colour space) that has the number of components specified by N. If this entry is omitted and the PDF reader does not understand the ICC profile data, the colour space that shall be used is DeviceGray, DeviceRGB, or DeviceCMYK, depending on whether the value of N is 1, 3, or 4, respectively. There shall not be conversion of source colour values, such as a tint transformation, when using the alternate colour space. Colour values within the range of the ICCBased colour space might not be within the range of the alternate colour space. In this case and after constraining to the ICCBased range, the nearest values within the range of the alternate space shall be substituted without error indication. |  |  |
| Range | array | (Optional) An array of 2 × N numbers [𝑚𝑖𝑛0 𝑚𝑎𝑥0 𝑚𝑖𝑛1 𝑚𝑎𝑥1 | … ] that shall specify the minimum and maximum valid values of the corresponding colour components. These values shall match the information in the ICC profile. Default value: [0.0 1.0 0.0 1.0…]. |
| Metadata | stream | (Optional; PDF 1.4) A metadata stream that shall contain metadata for the colour space ("see 14.3.2, "Metadata streams"). |  |

"Table 66 — ICC Specification versions supported by ICC based colour spaces" shows the versions of
the ICC specification on which the ICCBased colour spaces that PDF versions 1.3 and later shall use.
(Earlier versions of the ICC specification shall also be supported.)


## Page 207

Table 66 — ICC Specification versions supported by ICC based colour spaces

| PDF Version | ICC Specification Version |

| 1.3 | 3.3 |

1.4 ICC.1:1998-09 and its addendum ICC.1A:1999-04

1.5 ICC.1:2001-12

1.6 ICC.1:2003-09

1.7 ICC.1:2010 (ISO 15076-1:2010)

2.0 ICC.1:2010 (ISO 15076-1:2010)

PDF processors shall follow these guidelines for writing and rendering ICC based color spaces:

| • | A PDF reader shall support ICC.1:2010 as required by PDF 2.0, which will enable it to properly |

render all embedded ICC profiles regardless of the PDF version.
| • | A PDF reader shall always process an embedded ICC profile according to the corresponding |

version of the PDF being processed as shown in "Table 66 — ICC Specification versions supported
by ICC based colour spaces" above; it shall not substitute the alternate colour space in these cases.
| • | A PDF writer should use ICC 1:2010 profiles. It may embed profiles conforming to an earlier or |

later ICC version.
| • | A PDF processor shall substitute the alternate colour space for embedded profiles conforming to |

later ICC versions, if the PDF processor is not capable of properly processing the embedded ICC
profile.
| • | PDF writers shall only use the profile types shown in "Table 67 — ICC profile types" for specifying |

calibrated colour spaces for colouring graphics objects. Each of the indicated fields shall have one
of the values listed for that field in the second column of the table. Profiles shall satisfy both the
criteria shown in the table. The terminology is taken from the ICC specifications.
| • | Profiles shall conform to the specification version indicated by the Profile version number in its |

header.
NOTE 1       XYZ and 16-bit L*a*b* profiles are not listed.

Table 67 — ICC profile types

| Header Field | Required Value |

| deviceClass | icSigInputClass ('scnr') icSigDisplayClass ('mntr') icSigOutputClass ('prtr') icSigColorSpaceClass ('spac') |

| colorSpace | icSigGrayData ('GRAY') icSigRgbData ('RGB ') icSigCmykData ('CMYK') icSigLabData ('Lab ') |


## Page 208

The terminology used in PDF colour spaces and ICC colour profiles is similar, but sometimes the same
terms are used with different meanings. The default value for each component in an ICCBased colour
space is 0. The range of each colour component is a function of the colour space specified by the profile
and is indicated in the ICC specification. The ranges for several ICC colour spaces are shown in "Table
68 — Ranges for typical ICC colour spaces".

Table 68 — Ranges for typical ICC colour spaces

| ICC Colour Space | Component Ranges |

| Gray | [0.0 1.0] |

| RGB | [0.0 1.0] |

| CMYK | [0.0 1.0] |

| L*a*b* | 𝐿∗: [0 100]; a∗ and 𝑏∗: [−128 127] |

Since the ICCBased colour space is being used as a source colour space, only the "to CIE" profile
information (AToB in ICC terminology) shall be used; the "from CIE" (BToA) information shall be
ignored when present. An ICC profile may also specify a rendering intent, but a PDF reader shall ignore
this information; the rendering intent shall be specified in PDF by a separate parameter (see 8.6.5.8,
"Rendering intents").

The requirements stated above apply to an ICCBased colour space that is used to specify the source
colours of graphics objects. When such a space is used as the blending colour space for a transparency
group in the transparent imaging model (see 11.3.4, "Blending colour space"; 11.4, "Transparency
groups"; and 11.6.6, "Transparency group XObjects"), it shall have both "to CIE" (AToB) and "from CIE"
(BToA) information. This is because the group colour space shall be used as both the destination for
objects being painted within the group and the source for the group’s results. ICC profiles shall also be
used in specifying output intents for matching the colour characteristics of a PDF document with those
of a target output device or production environment. When used in this context, they shall be subject to
still other constraints on the "to CIE" and "from CIE" information; 14.11.5, "Output intents", for details.

| The representations of ICCBased colour spaces are less compact than | CalGray, CalRGB, and Lab, but |

can represent a wider range of colour spaces.

NOTE 2       One particular colour space is the "standard RGB" or sRGB, defined in IEC 61966-2-1 ed1.0
(1999-10) Multimedia systems and equipment - Colour measurement and management - Part 2-
1: Colour management - Default RGB colour space - sRGB (with Amendment 1 IEC 61966-2-1am1 ed1.0 (2003-01)). In PDF, the sRGB colour space can only be expressed as an ICCBased
space, although it can be approximated by a CalRGB space.

EXAMPLE            The following shows an ICCBased colour space for a typical three-component RGB space. The profile’s data
has been encoded in hexadecimal representation for readability; in actual practice, a lossless decompression
filter such as FlateDecode can be used.

| 10 0 obj | %Colour space [/ICCBased 15 0 R] |

endobj

| 15 0 obj | %ICC profile stream |


## Page 209

<</N 3
/Alternate /DeviceRGB
/Length 1605
/Filter /ASCIIHexDecode
>>
stream
00 00 02 0C 61 70 70 6C 02 00 00 00 6D 6E 74 72
52 47 42 20 58 59 5A 20 07 CB 00 02 00 16 00 0E
00 22 00 2C 61 63 73 70 41 50 50 4C 00 00 00 00
61 70 70 6C 00 00 04 01 00 00 00 00 00 00 00 02
00 00 00 00 00 00 F6 D4 00 01 00 00 00 00 D3 2B
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 09 64 65 73 63 00 00 00 F0 00 00 00 71
72 58 59 5A 00 00 01 64 00 00 00 14 67 58 59 5A
00 00 01 78 00 00 00 14 62 58 59 5A 00 00 01 8C
00 00 00 14 72 54 52 43 00 00 01 A0 00 00 00 0E
67 54 52 43 00 00 01 B0 00 00 00 0E 62 54 52 43
00 00 01 C0 00 00 00 0E 77 74 70 74 00 00 01 D0
00 00 00 14 63 70 72 74 00 00 01 E4 00 00 00 27
64 65 73 63 00 00 00 00 00 00 00 17 41 70 70 6C
65 20 31 33 22 20 52 47 42 20 53 74 61 6E 64 61
72 64 00 00 00 00 00 00 00 00 00 00 00 17 41 70
70 6C 65 20 31 33 22 20 52 47 42 20 53 74 61 6E
64 61 72 64 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 58 59 5A 58 59 5A 20 00 00 00 00 00 00 63 0A
00 00 35 0F 00 00 03 30 58 59 5A 20 00 00 00 00
00 00 53 3D 00 00 AE 37 00 00 15 76 58 59 5A 20
00 00 00 00 00 00 40 89 00 00 1C AF 00 00 BA 82
63 75 72 76 00 00 00 00 00 00 00 01 01 CC 63 75
63 75 72 76 00 00 00 00 00 00 00 01 01 CC 63 75
63 75 72 76 00 00 00 00 00 00 00 01 01 CC 58 59
58 59 5A 20 00 00 00 00 00 00 F3 1B 00 01 00 00
00 01 67 E7 74 65 78 74 00 00 00 00 20 43 6F 70
79 72 69 67 68 74 20 41 70 70 6C 65 20 43 6F 6D
70 75 74 65 72 73 20 31 39 39 34 00>
endstream
endobj

8.6.5.6 Default colour spaces

Colours that are specified in a device colour space (DeviceGray, DeviceRGB, or DeviceCMYK) are
device-dependent. By setting default colour spaces (PDF 1.1), a PDF writer can request that such
colours shall be systematically transformed (remapped) into device-independent CIE-based colour
spaces. This capability can be useful in a variety of circumstances:

| • | A document originally intended for one output device is redirected to a different device. |
| --- | --- |
| • | A document is intended to be compatible with older PDF readers that do not support CIE-based |

colours.
| • | Colour corrections or rendering intents need to be applied to device colours (see 8.6.5.8, |

"Rendering intents").
A colour space is selected for painting each graphics object. This is either the current colour space
parameter in the graphics state or a colour space given as an entry in an image XObject, inline image, or
shading dictionary. Regardless of how the colour space is specified, it shall be subject to remapping as
described below.


## Page 210

When a device colour space is selected, the ColorSpace subdictionary of the current resource
dictionary (see 7.8.3, "Resource dictionaries") is checked for the presence of an entry designating a
corresponding default colour space (DefaultGray, DefaultRGB, or DefaultCMYK, corresponding to
DeviceGray, DeviceRGB, or DeviceCMYK, respectively). If such an entry is present, its value shall be
used as the colour space for the operation currently being performed.

NOTE         (2020) This remapping means that the current colour space is defined by the default colour
space rather than DeviceGray, DeviceRGB or DeviceCMYK. Provisions in this standard that
apply specifically to device colour spaces are then not applicable to graphic objects painted when
the default colour space is not one of DeviceGray, DeviceRGB or DeviceCMYK.
Colour values in the original device colour space shall be passed unchanged to the default colour space,
which shall have the same number of components as the original space. The default colour space
should be chosen to be compatible with the original, taking into account the components’ ranges and
whether the components are additive or subtractive. If a colour value lies outside the range of the
default colour space, it shall be adjusted to the nearest valid value.

Any colour space other than a Lab, Indexed, or Pattern colour space may be used as a default colour
space and it should be compatible with the original device colour space as described above.

If the selected space is a special colour space based on an underlying device colour space, the default
colour space shall be used in place of the underlying space. This shall apply to the following colour
spaces:

| • | The underlying colour space of a Pattern colour space |
| --- | --- |
| • | The base colour space of an Indexed colour space |
| • | The alternate colour space of a Separation or DeviceN colour space (but only if the alternate |

colour space is actually selected)
| • | See 8.6.6, "Special colour spaces", for details on these colour spaces. |

There is no conversion of colour values, such as a tint transformation, when using the default colour
space. Colour values that are within the range of the device colour space might not be within the range
of the default colour space (particularly if the default is an ICCBased colour space). In this case, the
nearest values within the range of the default space are used. For this reason, a Lab colour space shall
not be used as the DefaultRGB colour space.

8.6.5.7 Implicit conversion of CIE-Based colour spaces

In cases where a source colour space accurately represents the particular output device being used, a
PDF processor should avoid converting the component colour values but use the source values directly
as output values. This avoids any unwanted computational error and in the case of 4 component colour
spaces avoids the conversion from 4 components to 3 and back to 4, a process that loses critical colour
information.

NOTE 1       In workflows in which PDF documents are intended for rendering on a specific target output
device (such as a printing press with particular inks and media), it is often useful to specify the
source colours for some or all of a document’s objects in a CIE-based colour space that matches
the calibration of the intended device. The resulting document, although tailored to the specific
characteristics of the target device, remains device-independent and will produce reasonable
results if retargeted to a different output device. However, the expectation is that if the
document is printed on the intended target device, source colours that have been specified in a


## Page 211

colour space matching the calibration of the device will pass through unchanged, without
conversion to and from the intermediate CIE 1931 XYZ space as depicted in "Figure 24 —
| Component transformations in a CIE | -based ABC colour space". |

NOTE 2       In particular, when colours intend         ed for a CMYK output device are specified in an              ICCBased
colour space using a matching CMYK printing profile, converting such colours from four
components to three components and back is unnecessary, and results in a loss of fidelity in the
| black component. In such cases, a PDF processor | could provide the ability for the user to specify a particular calibration to use for printing, proofing, or previewing. This calibration is then considered to be that of the native colour space of the intended output device (typically |
| --- | --- |
| DeviceCMYK), and colours expressed in a CIE | -based source colour space matching it can be treated as if they were specified directly in the device’s native colour space. |

NOTE 3       The conditions under which such implicit conversion is done cannot be specified in PDF, since
nothing in PDF describes the calibration of the output device (although an output intent
| dictionary, if present, can suggest such a calibration; | "see 14.11.5, "Output intents"). The |
| --- | --- |
| conversion is completely hidden by the PDF processor and plays n | o part in the interpretation of PDF colour spaces. |

When this type of implicit conversion is done, all of the semantics of the device colour space shall also
apply, even though they do not apply to CIE-based spaces in general. In particular:

| • | The non-zero overprint mode (see 8.6.7, "Overprint control") shall determine the interpretation |

of colour component values in the space.
| • | If the space is used as the blending colour space for a transparency group in the transparent |

imaging model (see 11.3.4, "Blending colour space"; 11.4, "Transparency groups"; and 11.6.6,
"Transparency group XObjects"), components of the space, such as Cyan, may be selected in a
| Separation or DeviceN colour space used within | the group (see 8.6.6.4, "Separation colour |
| --- | --- |
| spaces" and 8.6.6.5, "DeviceN colour spaces | "). |
| • | Likewise, any uses of device colour spaces for objects within such a transparency group have |

well-defined conversions to the group colour space.
NOTE 4       A source colour space can be specified directly (for example, with an ICCBased colour space) or
indirectly using the default colour space mechanism (for example, DefaultCMYK; see 8.6.5.6,
"Default colour spaces"). The implicit conversion of a CIE-based colour space to a device space
need not depend on whether the CIE-based space is specified directly or indirectly.

8.6.5.8 Rendering intents

Although CIE-based colour specifications are theoretically device-independent, they are subject to
practical limitations in the colour reproduction capabilities of the output device. Such limitations may
sometimes require compromises to be made among various properties of a colour specification when
rendering colours for a given device. Specifying a rendering intent (PDF 1.1) allows a PDF writer to set
priorities regarding which of these properties to preserve and which to sacrifice.

EXAMPLE            The PDF writer might request that colours falling within the output device’s gamut (the range of colours it
can reproduce) be rendered exactly while sacrificing the accuracy of out-of-gamut colours, or that a scanned
image such as a photograph be rendered in a perceptually pleasing manner at the cost of strict colorimetric
accuracy.

Rendering intents shall be specified with the ri operator (see 8.4.4, "Graphics state operators"), the RI
entry in a graphics state parameter dictionary (see 8.4.5, "Graphics state parameter dictionaries"), or
with the Intent entry in image dictionaries (see 8.9.5, "Image dictionaries"). The value shall be a name
identifying the rendering intent. "Table 69 — Rendering intents" lists the standard rendering intents
that shall be recognised.


## Page 212

Table 69 — Rendering intents

| Name | Description |

| AbsoluteColorimetri | Colours shall be represented solely with respect to the light source; no correction shall |
| --- | --- |
| c | be made for the output medium’s white point (such as the colour of unprinted paper). Thus, for example, a monitor’s white point, which is bluish compared to that of a printer’s paper, would be reproduced with a blue cast. In-gamut colours shall be reproduced exactly; out-of-gamut colours shall be mapped to the nearest value within the reproducible gamut NOTE 1  This style of reproduction has the advantage of providing exact colour matches from one output medium to another. It has the disadvantage of causing colours with Y values between the medium’s white point and 1.0 to be out of gamut. Logos and solid colours are typical cases requiring exact reproduction across different media. |

| RelativeColorimetric | Colours shall be represented with respect to the combination of the light source and the output medium’s white point (such as the colour of unprinted paper). Thus, a monitor’s white point can be reproduced on a printer by simply leaving the paper unmarked, ignoring colour differences between the two media. In-gamut colours shall be reproduced exactly; out-of-gamut colours shall be mapped to the nearest value within the reproducible gamut. NOTE 2  This style of reproduction has the advantage of adapting for the varying white points of different output media. It has the disadvantage of not providing exact colour matches from one medium to another. Vector graphics are a typical use case. |

| Saturation | Colours shall be represented in a manner that preserves or emphasizes saturation. Reproduction of in-gamut colours may or may not be colorimetrically accurate. NOTE 3  Business graphics are a typical use case where saturation is the most important attribute of the colour. |

| Perceptual | Colours shall be represented in a manner that provides a pleasing perceptual appearance. To preserve colour relationships, both in-gamut and out-of-gamut colours shall be generally modified from their precise colorimetric values. NOTE 4  Scanned images are a typical use case. |

"Figure 28 — Rendering intents" illustrates the effects of the standard rendering intents. These intents
have been chosen to correspond to those defined by the International Color Consortium (ICC), an
industry organisation that has developed standards for device-independent colour. If a PDF processor
does not recognise the specified name, it shall use the RelativeColorimetric intent by default.

NOTE          The exact set of rendering intents supported can vary from one output device to another; a
particular device does not have to support all PDF rendering intents and can support additional
ones beyond those listed in the table above.


## Page 213

Figure 28 — Rendering intents
See 11.7.5, "Rendering parameters and transparency", and in particular 11.7.5.3, "Rendering intent,
black point compensation and colour conversions", for further discussion of the role of rendering
intents in the transparent imaging model.

8.6.5.9 Use of black point compensation

Black point compensation applies to CIE-based colour conversion and extends the concept of the use of
rendering intents for colour conversion based upon the ICC architecture.

The use of black point compensation can be controlled through the UseBlackPtComp entry in the
ExtGState dictionary. If the value for UseBlackPtComp is ON, colour conversion shall be carried out
| according to the provisions in | ISO 18619. If it is set to OFF no black point compensation shall be carried |

out. If the value is not given or set to Default, then the behaviour is left to the PDF processor to
determine. If the current render intent of an object is AbsColorimetric then the value of
UseBlackPtComp shall be treated as OFF.


## Page 214

8.6.6 Special colour spaces

8.6.6.1 General

Special colour spaces add features or properties to an underlying colour space. There are four special
colour space families: Pattern, Indexed, Separation, and DeviceN.

8.6.6.2 Pattern colour spaces

A Pattern colour space (PDF 1.2) specifies that an area is to be painted with a pattern rather than a
single colour. The pattern shall be either a tiling pattern (Type 1) or a shading pattern (Type 2). 8.7,
"Patterns", discusses patterns in detail.

8.6.6.3 Indexed colour spaces

An Indexed colour space specifies a colour map or colour table of arbitrary colours in some other
space. A PDF reader shall treat each sample value as an index into the colour table and shall use the
colour value it finds there. This technique can considerably reduce the amount of data required to
represent a sampled image.

An Indexed colour space shall be defined by a four-element array:

[/Indexed base hival lookup]

The first element shall be the colour space family name Indexed. The remaining elements shall be
parameters that an Indexed colour space requires; their meanings are discussed below. Setting the
current stroking or nonstroking colour space to an Indexed colour space shall initialise the
corresponding current colour to 0.

The base parameter shall be an array or name that identifies the base colour space in which the values
in the colour table are to be interpreted. It shall be any device or CIE-based colour space or (PDF 1.3) a
| Separation or DeviceN space, but shall not be a | Pattern space or another Indexed space. If the base |

colour space is DeviceRGB, the values in the colour table shall be interpreted as red, green, and blue
| components; if the base colour space is a CIE | -based ABC space such as a CalRGB or Lab space, the |

values shall be interpreted as A, B, and C components.

The hival parameter shall be an integer that specifies the maximum valid index value. The colour table
| shall be indexed by integers in the range 0 to | hival. hival shall be no greater than 255, which is the |

integer required to index a table with 8-bit index values.

The colour table shall be defined by the lookup parameter, which may be either a stream or (PDF 1.2) a
byte string. It shall provide the mapping between index values and the corresponding colours in the
base colour space.

| The colour table data shall be 𝑚 | × (ℎ𝑖𝑣𝑎𝑙 | + | 1) bytes long, where m is the number of colour |

components in the base colour space. Each byte shall be an unsigned integer in the range 0 to 255 that
shall be scaled to the range of the corresponding colour component in the base colour space; that is, 0
corresponds to the minimum value in the range for that component, and 255 corresponds to the
maximum.


## Page 215

The colour components for each entry in the table shall appear consecutively in the string or stream.

EXAMPLE 1          If the base colour space is DeviceRGB and the indexed colour space contains two colours, the order of bytes
| in the string or stream is R0 G0 | B0 R1 | G1 B1, where letters denote the colour component and numeric subscripts denote the table entry. |

EXAMPLE 2          The following illustrates the specification of an Indexed colour space that maps 8-bit index values to threecomponent colour values in the DeviceRGB colour space.

[/Indexed
/DeviceRGB

<000000 FF0000 00FF00 0000FF B57342 …>
]
The example shows only the first five colour values in the lookup string; in all, there will be 256 colour
values and the string will be 768 bytes long. Having established this colour space, the PDF file can now
specify colours as single-component values in the range 0 to 255. For example, a colour value of 4
selects an RGB colour whose components are coded as the hexadecimal integers B5, 73, and 42.
Dividing these by 255 and scaling the results to the range 0.0 to 1.0 yields a colour with red, green, and
blue components of 0.710, 0.451, and 0.259, respectively.

Although an Indexed colour space is useful mainly for images, index values can also be used with the
colour selection operators SC, SCN, sc, and scn.

EXAMPLE 3          The following selects the same colour as does an image sample value of 123.

123 sc

The index value should be an integer in the range 0 to hival. If the value is a real number, it shall be
rounded to the nearest integer (0.5 values shall be rounded up); if it is outside the range 0 to hival, it
shall be adjusted to the nearest value within that range.

8.6.6.4 Separation colour spaces

A Separation colour space (PDF 1.2) provides a means for specifying the use of additional colourants
or for isolating the control of individual colour components of a device colour space for a subtractive
device. When such a space is the current colour space, the current colour shall be a single-component
value, called a tint, that controls the application of the given colourant or colour components only.

NOTE 1        Colour output devices produce full colour by combining primary or process colourants in varying
amounts. On an additive colour device such as a display, the primary colourants consist of red,
green, and blue phosphors; on a subtractive device such as a printer, they typically consist of
cyan, magenta, yellow, and sometimes black inks. In addition, some devices can apply special
colourants, often called spot colourants, to produce effects that cannot be achieved with the
standard process colourants alone. Examples include metallic and fluorescent colours and
special textures.
NOTE 2        When printing a page, most devices produce a single composite page on which all process
colourants (and spot colourants, if any) are combined. However, some devices, such as
imagesetters, produce a separate, monochromatic rendition of the page, called a separation, for
each colourant. When the separations are later combined — on a printing press, for example —
and the proper inks or other colourants are applied to them, the result is a full-colour page.
NOTE 3        The term separation is often misused as a synonym for an individual device colourant. In the
context of this discussion, a printing system that produces separations generates a separate
piece of physical medium (generally film) for each colourant. It is these pieces of physical


## Page 216

medium that are correctly referred to as separations. A particular colourant properly constitutes
a separation only if the device is generating physical separations, one of which corresponds to
the given colourant. The Separation colour space is so named for historical reasons, but it has
evolved to the broader purpose of controlling the application of individual colourants in general,
regardless of whether they are actually realised as physical separations.
NOTE 4       The operation of a Separation colour space itself is independent of the characteristics of any
particular output device. Depending on the device, the colour space does not have to correspond
to a true, physical separation or to an actual colourant. For example, a Separation colour space
could be used to control the application of a single process colourant (such as cyan) on a
composite device that does not produce physical separations, or could represent a colour (such
as orange) for which no specific colourant exists on the device. A Separation colour space
provides consistent, predictable behaviour, even on devices that cannot directly generate the
requested colour.
A Separation colour space is defined as follows:

[/Separation name alternateSpace tintTransform]

It shall be a four-element array whose first element shall be the colour space family name Separation.
The remaining elements are parameters that a Separation colour space requires; their meanings are
discussed below.

A colour value in a Separation colour space shall consist of a single tint component in the range 0.0 to
1.0. The value 0.0 shall represent the minimum amount of colourant that can be applied; 1.0 shall
represent the maximum. Tints shall always be treated as subtractive colours, even if the device
produces output for the designated component by an additive method. Thus, a tint value of 0.0 denotes
the lightest colour that can be achieved with the given colourant, and 1.0 is the darkest. The initial
value for both the stroking and nonstroking colour in the graphics state shall be 1.0. The SCN and scn
operators respectively shall set the current stroking and nonstroking colour to a tint value. A sampled
image with single-component samples may also be used as a source of tint values.

NOTE 5       This convention is the same as for DeviceCMYK colour components but opposite to the one for
DeviceGray and DeviceRGB.
The name parameter is a name object that shall specify the name of the colourant that this Separation
colour space is intended to represent (or one of the special names All or None; see below). With the
exception of the names Cyan, Magenta, Yellow and Black which are reserved to name the process
colourants of a CMYK device, such colourant names are arbitrary, and there may be any number of
them, subject to implementation limits.

The special colourant name All shall refer collectively to all colourants available on an output device,
including those for the standard process colourants. When a Separation space with this colourant
name is the current colour space, painting operators shall apply tint values to all available colourants
at once. When outputting to an additive device, such as a computer monitor, the subtractive tint values
of the All colourant shall be complemented by subtracting from 1 before applying to all available
colourants.

NOTE 6       This is useful for purposes such as painting registration targets in th             e same place on every
separation. Such marks are typically painted as the last step in composing a page to ensure that
they are not overwritten by subsequent painting operations.
The special colourant name None shall not produce any visible output. Painting operations in a
Separation space with this colourant name shall have no effect on the current page.


## Page 217

A PDF processor shall support Separation colour spaces with the colourant names All and None on all
devices, even if the devices are not capable of supporting any others. When processing Separation
spaces with either of these colourant names PDF processors shall ignore the alternateSpace and
tintTransform parameters (discussed below), although valid values shall still be provided.

At the moment the colour space is set to a Separation space, the PDF reader shall determine whether
the device has an available colourant corresponding to the name of the requested space. If so, the PDF
processor shall ignore the alternateSpace and tintTransform parameters; subsequent painting
operations within the space shall apply the designated colourant directly, according to the tint values
supplied.

The preceding paragraph applies only to subtractive output devices such as printers and imagesetters.
For an additive device such as a computer display, a Separation colour space never applies a process
colourant directly; it always reverts to the alternate colour space as described below. This is because
the model of applying process colourants independently does not work as intended on an additive
device.

EXAMPLE 1          In an R, G, B colour space  , painting tints of the Red component on a white background (1,1,1) produces a
result  that  varies  from  white  to  cyan  (0,1,1) which  is  not  as  might  be  otherwise  expected  for  a  red
component.

| This exception applies only to colourants for additive devices, not to any specific names, e.g., | Red, |

Green, and Blue. In contrast, a printer might have a (subtractive) ink named Red, which should work
as a Separation colour space just the same as any other supported colourant.

If the colourant name associated with a Separation colour space does not correspond to a colourant
available on the device, the PDF processor shall arrange for subsequent painting operations to be
performed in an alternate colour space. The intended colours may be approximated by colours in a
device or CIE-based colour space, which shall then be rendered using the usual primary or process
colourants:

| • | The alternateSpace parameter shall be an array or name object that identifies the alternate colour |

space, which may be any device or CIE-based colour space but may not be another special colour
space (Pattern, Indexed, Separation, or DeviceN).
| • | The tintTransform parameter shall be a function (see 7.10, "Functions"). During subsequent |

painting operations, a PDF processor calls this function to transform a tint value into colour
component values in the alternate colour space. The function shall be called with the tint value
and shall return the corresponding colour component values. That is, the number of components
and the interpretation of their values shall depend on the alternate colour space.
NOTE 7       In some cases where colourants are unavailable on the output device, painting in the alternate
colour space can produce a good approximation of the intended colour when only opaque
objects are painted. However, it does not necessarily reflect the interactions between an object
and its backdrop when overprinting (see 8.6.7, "Overprint control") is enabled. Separation
simulation (see 10.8.3) can be used as an alternative method to yield better results when
overprinting is involved. When transparency is involved, the use of the alternate space can
produce incorrect output regardless of what method is used.

EXAMPLE 2          The following illustrates the specification of a Separation colour space (object 5) that is intended to produce
a colour named LogoGreen. If the output device has no colourant corresponding to this colour, DeviceCMYK
| is used as the alternate colour space, and the | tint transformation function (object 12) maps tint values |


## Page 218

linearly into shades of a CMYK colour value approximating the LogoGreen colour.

| 5 0 obj | %Colour space [/Separation /LogoGreen /DeviceCMYK 12 0 R ] |

endobj

| 12 0 obj | %Tint transformation function <</FunctionType 4 /Domain [0.0 1.0] /Range [0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0] /Length 62 >> |

stream
{dup 0.84 mul
exch 0.00 exch dup 0.44 mul exch 0.21 mul
}
endstream
endobj

See 11.7.3, "Spot colours and transparency", for further discussion of the role of Separation colour
spaces in the transparent imaging model.

8.6.6.5 DeviceN colour spaces

DeviceN colour spaces (PDF 1.3) may contain an arbitrary number of colour components.

NOTE 1       They provide greater flexibility than is available with standard device colour spaces such as
DeviceCMYK or with individual Separation colour spaces.

EXAMPLE 1          It is possible to create a DeviceN    colour space consisting of only the cyan, magenta, and yellow colour
components, with the black component excluded.

NOTE 2       DeviceN colour spaces are used in applications such as these:
High-fidelity colour is the use of more than the standard CMYK process colourants to produce an
extended gamut, or range of colours. A popular example is the PANTONE Hexachrome system,
which uses six colourants: the usual cyan, magenta, yellow, and black, plus orange and green.
Multitone colour systems use a single-component image to specify multiple colour components.
In a duotone, for example, a single-component image can be used to specify both the black
component and a spot colour component. The tone reproduction is generally different for the
different components. For example, the black component can be painted with the exact sample
data from the single-component image; the spot colour component can be generated as a
nonlinear function of the image data in a manner that emphasizes the shadows. "Figure 29 —
Duotone image" shows an example that uses black and magenta colour components. In "Figure
30 — Quadtone image" a single-component grayscale image is used to generate a quadtone
result that uses four colourants: black and three PANTONE spot colours. See Example 4 in this
subclause for the code used to generate this image.


## Page 219

Figure 29 — Duotone image

Figure 30 — Quadtone image
DeviceN shall be used to represent colour spaces containing multiple components that correspond to
colourants of some target device. As with Separation colour spaces, PDF processors shall be able to
approximate the colourants if they are not available on the current output device, such as a display. To
accomplish this, the colour space definition provides a tint transformation function that shall be used
to convert all the components to an alternate colour space.

PDF 1.6 extended the meaning of DeviceN to include colour spaces that are referred to as NChannel
colour spaces. Such colour spaces may contain an arbitrary number of spot and process components,
which may or may not correspond to specific device colourants (the process components shall be from
a single process colour space). They provide information about each component that allows PDF
processors more flexibility in converting colours. These colour spaces shall be identified by a value of
NChannel for the Subtype entry of the attributes dictionary (see "Table 70 — Entries in a DeviceN
colour space attributes dictionary"). A value of DeviceN for the Subtype entry, or no value, shall mean
that only the previous features shall be supported. PDF processors that do not support PDF 1.6 shall


## Page 220

treat these colour spaces as normal DeviceN colour spaces and shall use the tint transformation
function as appropriate. PDF writers using the NChannel features should follow certain guidelines, as
noted throughout this subclause, to achieve good backward compatibility.

NOTE 3       PDF processors can use their own blending algorithms for on-screen viewing and composite
printing, rather than being required to use a specified tint transformation function. See also
clause 10.8, "Rendering for separations".
DeviceN colour spaces shall be defined in a similar way to Separation colour spaces — in fact, a
Separation colour space can be defined as a DeviceN colour space with only one component. A
DeviceN colour space shall be specified as follows:

[/DeviceN names alternateSpace tintTransform]
or
[/DeviceN names alternateSpace tintTransform attributes]

It is a four- or five-element array whose first element shall be the colour space family name DeviceN.
The remaining elements shall be parameters that a DeviceN colour space requires.

The names parameter shall be an array of name objects specifying the individual colour components.
The maximum number of entries in the names array in the computer on which the PDF processor is
running may be subject to implementation limits; see Annex C, "Advice on maximising portability".

The component names shall all be different from one another, except for the name None, which may be
repeated as described later in this subclause. The special name All, used by Separation colour spaces,
shall not be used. The names Cyan, Magenta, Yellow and Black are reserved to name the subtractive
process colourants of a CMYK device.

Colour values shall be tint components in the range 0.0 to 1.0:

| • | For DeviceN colour spaces that do not have a subtype of NChannel, 0.0 shall represent the |

minimum amount of colourant; 1.0 shall represent the maximum. Tints shall always be treated as
subtractive colours, even if the device produces output for the designated component by an
additive method. Thus, a tint value of 0.0 shall denote the lightest colour that can be achieved with
the given colourant, and 1.0 the darkest.
NOTE 4       This convention is the same one as for DeviceCMYK colour components but opposite to the one
for DeviceGray and DeviceRGB.
| • | For NChannel colour spaces, values for additive process colours (such as RGB) shall be specified |

in their natural form, where 1.0 shall represent maximum intensity of colour.
When this space is set to the current colour space (using the CS or cs operators), each component shall
be given an initial value of 1.0. The SCN and scn operators respectively shall set the current stroking
and nonstroking colour. Operand values supplied to SCN or scn shall be interpreted as colour
component values in the order in which the colours are given in the names array, as are the values in a
sampled image that uses a DeviceN colour space.

The alternateSpace parameter shall be an array or name object that can be any device or CIE-based
colour space but shall not be another special colour space (Pattern, Indexed, Separation, or
DeviceN). When the colour space is set to a DeviceN space, if any of the component names in the
colour space do not correspond to a colourant available on the device, the PDF processor should
perform subsequent painting operations in the alternate colour space specified by this parameter.


## Page 221

NOTE 5       In some cases PDF processors have more information about colourants and their interaction
than is provided through the alternateSpace parameter, and are free to use such information
instead of the alternateSpace parameter. In addition, where a DeviceN space contains an
attributes dictionary, PDF processors are free to use the information provided in the attributes
dictionary instead of the alternateSpace parameter.
For NChannel colour spaces, the components shall be evaluated individually; that is, only the ones not
present on the output device shall use the alternate colour space of that component.

The tintTransform parameter shall specify a function (see 7.10, "Functions") that is used to transform
the tint values into the alternate colour space. It shall be called with n tint values and returns m colour
component values, where n is the number of components needed to specify a colour in the DeviceN
colour space and m is the number required by the alternate colour space.

NOTE 6       Painting in the alternate colour space can produce a good approximation of the intended colour
when only opaque objects are painted. However, it does not correctly represent the interactions
between an object and its backdrop when the object is painted with transparency or when
overprinting (see 8.6.7, "Overprint control") is enabled.
The colour component name None, which may be present only for DeviceN colour spaces that do not
have the NChannel subtype, indicates that the corresponding colour component shall never be painted
on the page, as in a Separation colour space for the None colourant. When a DeviceN colour space is
painting the named device colourants directly, colour components corresponding to None colourants
shall be discarded. However, when the DeviceN colour space reverts to its alternate colour space,
those components shall be passed to the tint transformation function, which may use them as desired.

A DeviceN colour space whose component colourant names are all None shall always discard its
output, just the same as a Separation colour space for None; it shall never revert to the alternate
colour space. Reversion shall occur only if at least one colour component (other than None) is specified
and is not available on the device.

The optional attributes parameter shall be a dictionary (see "Table 70 — Entries in a DeviceN colour
space attributes dictionary") containing additional information about the components of this colour
space that PDF processors may use. PDF processors need not use the alternateSpace and tintTransform
parameters, and may instead use custom blending algorithms, along with other information provided
in the attributes dictionary if present. (If the value of the Subtype entry in the attributes dictionary is
NChannel, such information shall be present.) However, alternateSpace and tintTransform shall always
be provided for PDF processors that want to use them or do not support PDF 1.6.

Table 70 — Entries in a DeviceN colour space attributes dictionary

| Key | Type | Value |

| Subtype | name | (Optional; PDF 1.6) A name specifying the preferred treatment for the colour space. Values shall be DeviceN or NChannel. Default value: DeviceN. |


## Page 222

| Key | Type | Value |

| Colorants | dictionary | (Required if Subtype is NChannel and the colour space includes spot colourants; otherwise optional; PDF 1.6) A dictionary describing the individual colourants used in the DeviceN colour space. For each entry in this dictionary, the key shall be a colourant name and the value shall be an array defining a Separation colour space for that colourant (see 8.6.6.4, "Separation colour spaces"). The key shall match the colourant name given in that colour space. This dictionary provides information about the individual colourants that may be useful to some PDF processors. In particular, the alternate colour space and tint transformation function of a Separation colour space describe the appearance of that colourant alone, whereas those of a DeviceN colour space describe only the appearance of its colourants in combination. |

| Process | dictionary | (Required if Subtype is NChannel and the colour space includes components of a process colour space, otherwise optional; PDF 1.6) A dictionary (see "Table 71 — Entries in a DeviceN process dictionary") that describes the process colour space whose components are included in this colour space. |

| MixingHints | dictionary | (Optional; PDF 1.6) A dictionary (see "Table 72 — Entries in a DeviceN mixing hints dictionary") that specifies optional attributes of the inks that shall be used in blending calculations when used as an alternative to the tint transformation function. |

This dictionary provides information about the individual colourants that may be useful to some PDF
processors. In particular, the alternate colour space and tint transformation function of a Separation
colour space describe the appearance of that colourant alone, whereas those of a DeviceN colour space
describe only the appearance of its colourants in combination.

If Subtype is NChannel, the Colorants dictionary shall have entries for all spot colourants in this
colour space. The Colorants dictionary may also include additional colourants not used by this colour
space.

A value of NChannel for the Subtype entry indicates that some of the other entries in the Colorants
dictionary are required rather than optional. The Colorants entry specifies a Colorants dictionary that
contains entries for all the spot colourants in the colour space; they shall be defined using individual
Separation colour spaces. The Process entry specifies a process dictionary (see "Table 71 — Entries
in a DeviceN process dictionary") that identifies the process colour space that is used by this colour
space and the names of its components. It shall be present if Subtype is NChannel and the colour
space has process colour components. An NChannel colour space shall contain components from at
most one process colour space.

For colour spaces that have a value of NChannel for the Subtype entry in the attributes dictionary the
following restrictions apply to process colours:

| • | There may be colour components from at most one process colour space, which may be any |

device or CIE-based colour space.
| • | For a non-CMYK colour space, the names of the process components shall appear sequentially in |

the names array, in the normal colour space order (for example, Red, Green, and Blue). However,
the names in the names array need not match the actual colour space names (for example, a Red
component need not be named Red). The mapping of names is specified in the process dictionary


## Page 223

(see "Table 71 — Entries in a DeviceN process dictionary" and discussion below), which shall be
present.
| • | Definitions for process colourants should not appear in the Colorants dictionary. Any such |

definition shall be ignored if the colourant is also present in the process dictionary. Any
component not specified in the process dictionary shall be considered to be a spot colourant.
| • | For a CMYK colour space, a subset of the components may be present, and they may appear in any |

order in the names array. The reserved names Cyan, Magenta, Yellow, and Black shall always be
| considered to be process colours, which do not necessarily correspond to the | colourants of a |

specific device; they need not have entries in the process dictionary.
| • | The values associated with the process components shall be stored in their natural form (that is, |

subtractive colour values for CMYK and additive colour values for RGB), since they shall be
| interpreted directly as process values by consumers making use of the process di | ctionary. (For |
| --- | --- |
| additive colour spaces, this is the reverse of how colour values are specified for | DeviceN, as |

described above in the discussion of the names parameter.)
The MixingHints entry in the attributes dictionary specifies a mixing hints dictionary (see "Table 72 —
| Entries in a DeviceN mixing hints d | ictionary") that provides information about the characteristics of |
| --- | --- |
| colourants that may be used in blending calculat | ions when the actual colourants are not available on |

the target device. PDF processors need not use this information.

Table 71 — Entries in a DeviceN process dictionary

| Key | Type | Value |

| ColorSpace | name or | (Required) A name or array identifying the process colour space, |
| --- | --- | --- |
| array | which may be any device or CIE-based colour space except Lab. If an ICCBased colour space is specified, it shall provide calibration information appropriate for the process colour components specified in the names array of the DeviceN colour space. |  |

| Components | array | (Required) An array of component names that correspond, in order, to the components of the process colour space specified in ColorSpace. For example, an RGB colour space shall have three names corresponding to red, green, and blue. The names may be arbitrary (that is, not the same as the standard names for the colour space components) and shall match those specified in the names array of the DeviceN colour space, even if all components are not present in the names array. |


## Page 224

Table 72 — Entries in a DeviceN mixing hints dictionary

| Key | Type | Value |

| Solidities | dictionary | (Optional) A dictionary specifying the solidity of inks that shall be used in blending calculations when used as an alternative to the tint transformation function. For each entry, the key shall be a colourant name, and the value shall be a number between 0.0 and 1.0. This dictionary need not contain entries for all colourants used in this colour space; it may also include additional colourants not used by this colour space. A value of 1.0 simulates an ink that completely covers the inks beneath; a value of 0.0 simulates a transparent ink that completely reveals the inks beneath. An entry with a key of Default specifies a value that shall be used by all components in the associated DeviceN colour space for which a solidity value is not explicitly provided. If Default is not present, the default value for unspecified colourants shall be 0.0; interactive PDF processors may choose to use other values. If this entry is present, PrintingOrder shall also be present. |

| PrintingOrder | array | (Required if Solidities is present) An array of colourant names, specifying the order in which inks shall be laid down. Each component in the names array of the DeviceN colour space shall appear in this array (although the order is unrelated to the order specified in the names array). This entry may also list colourants unused by this specific DeviceN instance. |

NOTE      (2020) PrintingOrder precisely matches the optional ICC profile
colorantOrderTag (ISO 15076-1, 9.2.17), which specifies physical colourant
laydown relative to the substrate. It does not define viewing direction.

| DotGain | dictionary | (Optional) A dictionary specifying the dot gain of inks that shall be used in blending calculations when used as an alternative to the tint transformation function. Dot gain (or loss) represents the amount by which a printer’s halftone dots change as the ink spreads and is absorbed by paper. For each entry, the key shall be a colourant name, and the value shall be a function that maps values in the range 0 to 1 to values in the range 0 to 1. The dictionary may list colourants unused by this specific DeviceN instance and need not list all colourants. An entry with a key of Default shall specify a function that shall be used by all colourants for which a dot gain function is not explicitly specified. PDF processors may ignore values in this dictionary when other sources of dot gain information are available, such as ICC profiles associated with the process colour space or tint transformation functions associated with individual colourants. |

Each entry in the mixing hints dictionary refers to colourant names, which include spot colourants
referenced by the Colorants dictionary. Under some circumstances, they may also refer to one or more
individual process components called Cyan, Magenta, Yellow, or Black when DeviceCMYK is
specified as the process colour space in the process dictionary. However, applications shall ignore
these process component entries if they can obtain the information from an ICC profile.

NOTE 7        The mixing hints subdictionaries (as well as the Colorants dictionary) can specify colourants
that are not used in any given instance of a DeviceN colour space. This allows them to be
referenced from multiple DeviceN colour spaces, which can produce smaller file sizes as well as
consistent colour definitions across instances.
For consistency of colour, the following guidelines apply:

| • | The PDF processor should apply either the specified tint transformation function or invoke the |

same alternative blending algorithm for all DeviceN instances in the document.


## Page 225

NOTE 8        When the tint transformation function is used, the burden is on the PDF writer to guarantee that
the individual function definitions chosen for all DeviceN instances produce similar colour
appearances throughout the document.
| • | Blending algorithms should produce a similar appearance for colours when they are used as |

separation colours or as a component of a DeviceN colour space.

EXAMPLE 2           This example shows a DeviceN colour space consisting of three colour components named Orange, Green,
| and  None.  In  this  example,  the  DeviceN | colour  space,  object  30,  has  an  attributes  dictionary  whose |  |  |
| --- | --- | --- | --- |
| Colorants | entry  is  an  indirect  reference  to  object  45  (which  might  also  be  referenced  by  attributes dictionaries of other DeviceN colour spaces). tintTransform1, whose definition is not shown, maps three |  |  |
| colour components (tints of the | colourants Orange, Green, and None) to four colour components in the |  |  |
| alternate colour space,  DeviceCMYK. tintTransform2 maps a single colour component (an orange tint) | to |  |  |
| four  components  in | DeviceCMYK.  Likewise, | tintTransform3  maps  a  green  tint  to | DeviceCMYK,  and tintTransform4 maps a tint of PANTONE 131 to DeviceCMYK. |

| 30 0 obj | %Colour space [/DeviceN [/Orange /Green /None] /DeviceCMYK tintTransform1 <</Colorants 45 0 R>> ] endobj |

| 45 0 obj | %Colorants dictionary <</Orange [/Separation /Orange /DeviceCMYK tintTransform2 ] /Green [/Separation /Green /DeviceCMYK tintTransform3 ] /PANTONE#20131 [/Separation /PANTONE#20131 /DeviceCMYK tintTransform4 ] >> endobj |

NOTE 9        Example 3, Example 4 and Example 5 show the use of NChannel colour spaces.

EXAMPLE 3           This example shows the use of calibrated CMYK process components.

| 10 0 obj | %Colour space [/DeviceN [/Magenta /Spot1 /Yellow /Spot2] alternateSpace tintTransform1 |
| --- | --- |
| << | %Attributes dictionary /Subtype /NChannel /Process <</ColorSpace [/ICCBased CMYK_ICC profile] /Components [/Cyan /Magenta /Yellow /Black] >> /Colorants <</Spot1 [/Separation /Spot1 alternateSpace tintTransform2] /Spot2 [/Separation /Spot2 alternateSpace tintTransform3] >> >> ] endobj |


## Page 226

EXAMPLE 4          This example shows the recommended convention for dealing with situations where a spot colourant and a
process colour component have the same name. Since the names array cannot have duplicate names, the
process colours will need to be given different names, which are mapped to process components in the
| Components entry of the process dictionary. In this case, | Red refers to a spot | colourant; ProcessRed, |
| --- | --- | --- |
| ProcessGreen, and ProcessBlue are mapped to the components of an | RGB colour space. |  |

| 10 0 obj | %Colour space [/DeviceN [/ProcessRed /ProcessGreen /ProcessBlue /Red] alternateSpace tintTransform1 |
| --- | --- |
| << | %Attributes dictionary /Subtype /NChannel /Process <</ColorSpace [/ICCBased RGB_ICC profile] /Components [/ProcessRed /ProcessGreen /ProcessBlue] >> /Colorants <</Red [/Separation /Red alternateSpace tintTransform2]>> >> ] endobj |

EXAMPLE 5          This example shows the use of a mixing hints dictionary.

| 10 0 obj | %Colour space [/DeviceN [/Magenta /Spot1 /Yellow /Spot2] alternateSpace tintTransform1 << /Subtype /NChannel /Process <</ColorSpace [/ICCBased CMYK_ICC profile] /Components [/Cyan /Magenta /Yellow /Black] >> /Colorants <</Spot1 [/Separation /Spot1 alternateSpace tintTransform2] /Spot2 [/Separation /Spot2 alternateSpace tintTransform2] >> /MixingHints << /Solidities <</Spot1 1.0 /Spot2 0.0 >> /DotGain <</Spot1 function1 /Spot2 function2 /Magenta function3 /Yellow function4 >> /PrintingOrder [/Magenta /Yellow /Spot1 /Spot2] >> >> ] endobj |

See 11.7.3, "Spot colours and transparency", for further discussion of the role of DeviceN colour spaces
in the transparent imaging model.


## Page 227

8.6.6.6 Multitone examples

NOTE 1        The following examples illustrate various interesting and useful special cases of the use of
Indexed and DeviceN colour spaces in combination to produce multitone colours.
NOTE 2        Example 1 and Example 2 in this subclause illustrate the use of DeviceN to create duotone
colour spaces.

EXAMPLE 1           In this example, an Indexed colour space maps index values in the range 0 to 255 to a duotone DeviceN
space in cyan and black. In effect, the index values are treated as if they were tints of the duotone space,
which are then mapped into tints of the two underlying colourants. Only the beginning of the lookup table
| string for the | Indexed colour space is shown; the full table would contain 256 two-byte entries, each specifying a tint value for cyan and black, for a total of 512 bytes. If the alternate colour space of the DeviceN space is selected, the tint transformation function (object 15 in the example) maps the two tint components for cyan and black to the four components for a DeviceCMYK colour space by supplying zero values for the other two components. |

| 10 0 ob | %Colour space [/Indexed [/DeviceN [/Cyan /Black] /DeviceCMYK 15 0 R ] |

<6605 6806 6907 6B09 6C0A …>
]
endobj

| 15 0 obj | %Tint transformation function <</FunctionType 4 |  |
| --- | --- | --- |
| /Domain [0.0 | 1.0 | 0.0 1.0] /Range [0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0] /Length 16 >> stream { 0 0 3 -1 roll } endstream endobj |

EXAMPLE 2           This  example  shows  the  definition  of  another  duotone  colour  space,  this  time  using  black  and  gold
colourants (where gold is a spot colourant) and using a CalRGB space as the alternate colour space. This
| could be defined in the same way as in the preceding example, | with a tint transformation function that converts from the two tint components to colours in the alternate CalRGB colour space. |

| 30 0 obj | %Colour space [/Indexed [/DeviceN [/Black /Gold] [/CalRGB <</WhitePoint [1.0 1.0 1.0] /Gamma [2.2 2.2 2.2] >> ] |
| --- | --- |
| 35 0 R | %Tint transformation function ] |

… Lookup table …
]
endobj
NOTE 3        Given a formula for converting any combination of black and gold tints to calibrated RGB, a 2-in,
3-out Type 4 (PostScript calculator) function could be used for the tint transformation.
Alternatively, a Type 0 (sampled) function could be used, but this would require a large number


## Page 228

of sample points to represent the function accurately; for example, sampling each input variable
for 256 tint values between 0.0 and 1.0 would require 2562 = 65,536 samples. But since the
DeviceN colour space is being used as the base of an Indexed colour space, there are actually
only 256 available combinations of black and gold tint values.

EXAMPLE 3          This example shows a more compact way to represent this information is to put the alternate colour values
directly into the lookup table alongside the DeviceN colour values.

| 10 0 obj | %Colour space [/Indexed [/DeviceN [/Black /Gold /None /None /None] |
| --- | --- |
| [ | /CalRGB <</WhitePoint [1.0 1.0 1.0] /Gamma [2.2 2.2 2.2] >> ] |
| 20 0 R | %Tint transformation function ] |

… Lookup table …
]
endobj
NOTE 4       In Example 3 in this subclause, each entry in the lookup table has five components: two for the
black and gold colourants and three more (specified as None) for the equivalent CalRGB colour
components. If the black and gold colourants are available on the output device, the None
components are ignored; if black and gold are not available, the tint transformation function is
used to convert a five-component colour into a three-component equivalent in the alternate
CalRGB colour space. But because, by construction, the third, fourth, and fifth components are
the CalRGB components, the tint transformation function can merely discard the first two
| components and return the last three. This can be readily expressed with a | Type 4 (PostScript calculator) function (see Example 4 in this subclause). |

EXAMPLE 4          This example shows a Type 4 (PostScript calculator) function.

| 20 0 obj | %Tint transformation function <</FunctionType 4 /Domain [0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0] /Range [0.0 1.0 0.0 1.0 0.0 1.0] /Length 27 >> stream { 5 3 roll pop pop } endstream endobj |

EXAMPLE 5          This  example  uses  an  extension  of  the  techniques  described  above  to  produce  the  quadtone         (fourcomponent) image shown in "Figure 30 — Quadtone image").

| 5 0 obj | %Image XObject <</Type /XObject /Subtype /Image /Width 288 /Height 288 /ColorSpace 10 0 R /BitsPerComponent 8 /Length 105278 /Filter /ASCII85Decode >> stream … Data for grayscale image … endstream endobj |


## Page 229

| 10 0 obj | %Indexed colour space for image [/Indexed |
| --- | --- |
| 15 0 R | %Base colour space |

%Table has 256 entries
| 30 0 R | %Lookup table ] |

endobj

| 15 0 obj | %Base colour space ( DeviceN ) for Indexed space [/DeviceN |
| --- | --- |
| [/Black | %Four colourants (black plus three spot colours) /PANTONE#20216#20CVC /PANTONE#20409#20CVC /PANTONE#202985#20CVC |
| /None | %Three components for alternate space /None /None ] |
| 16 0 R | %Alternate colour space |
| 20 0 R | %Tint transformation function ] |

endobj

| 16 0 obj | %Alternate colour space for DeviceN space [/CalRGB <</WhitePoint [1.0 1.0 1.0]>> ] |

endobj

| 20 0 obj | %Tint transformation function for DeviceN space <</FunctionType 4 /Domain [0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0] /Range [0.0 1.0 0.0 1.0 0.0 1.0] /Length 44 >> |

stream
| { 7 3 roll | %Just discard first four values pop pop pop pop } |

endstream
endobj

| 30 0 obj | %Lookup table for Indexed colour space <</Length 1975 /Filter [/ASCII85Decode /FlateDecode] >> |

stream
8;T1BB2"M7*!"psYBt1k\gY1T<D&tO]r*F7Hga*
… Additional data ( seven components for each table entry ) …
endstream
endobj
NOTE 5       As in the preceding examples, an Indexed colour space based on a DeviceN space is used to
paint the grayscale image shown on the left in the plate with four colourants: black and three
PANTONE spot colours. The alternate colour space is a simple calibrated RGB. Thus, the DeviceN
colour space has seven components: the four desired colourants plus the three components of
the alternate space. The example shows the image XObject (see 8.9.5, "Image dictionaries")
representing the quadtone image, followed by the colour space used to interpret the image data.

8.6.7 Overprint control

The graphics state contains an overprint parameter, controlled by the OP and op entries in a graphics
state parameter dictionary. Overprint control is useful mainly on devices that produce true physical


## Page 230

separations, but it is available on some composite devices as well. Although the operation of this
parameter is device-dependent, it is described here rather than in the subclause on colour rendering,
because it pertains to an aspect of painting in device colour spaces that is important to many
applications.

Any painting operation marks some specific set of device colourants, depending on the colour space in
which the painting takes place. In a Separation or DeviceN colour space, the colourants to be marked
shall be specified explicitly; in a device or CIE-based colour space, they shall be implied by the process
colour model of the output device (see clause 10, "Rendering"). The overprint parameter is a boolean
| flag that determines how painting operations affect | colourants other than those explicitly or implicitly |

specified by the current colour space.

| If the overprint parameter is | false (the default value), painting a colour in any colour space shall cause |

the corresponding areas of unspecified colourants to be erased (painted with a tint value of 0.0). The
| effect is that the colour at any position on t | he page is whatever was painted there last, which is |

consistent with the normal painting behaviour of the opaque imaging model.

| If the overprint parameter is | true and the output device supports overprinting, erasing actions shall |
| --- | --- |
| not be performed; anything | previously painted in other colourants is left undisturbed. Consequently, |

the colour at a given position on the page may be a combined result of several painting operations in
| different colourants. The effect produced by such overprinting is device | -dependent and is not defined |

here.

NOTE 1       Not all devices support overprinting. Furthermore, many PostScript                  language compatible
printers support it only when separations are being produced, and not for composite output.
| If overprinting is not supported, the val | ue of the overprint parameter shall be ignored. |

| An additional graphics state parameter, the | overprint mode | (PDF 1.3), shall affect the interpretation of a |
| --- | --- | --- |
| tint value of 0.0 for a colour component in a | DeviceCMYK colour space when overprinting is enabled. |  |
| This parameter is controlled by the | OPM entry in a graphics state parameter dictionary; it shall have |  |
| an effect only when the overprint parameter is | true, as described above. Determination of whether a |  |

tint value is zero or non-zero shall be made on the tint value defined within the PDF file, before
quantisation into a device tint value for the output device.

| When colours are specified in a | DeviceCMYK colour space and the native colour space of the output |
| --- | --- |
| device is also DeviceCMYK, each of the source colour | components controls the corresponding device |

colourant directly. Ordinarily, each source colour component value replaces the value previously
| painted for the corresponding device | colourant, no matter what the new value is; this is the default |

behaviour, specified by overprint mode 0.

| When the overprint mode is 1 (also called | non-zero overprint mode | ), a tint value of 0.0 for a source |

colour component shall leave the corresponding component of the previously painted colour
| unchanged. The effect is equivalent | to painting in a DeviceN colour space that includes only those |
| --- | --- |
| components whose values are non | -zero. |


## Page 231

EXAMPLE            If the overprint parameter is true and the overprint mode is 1, the operation

0.2 0.3 0.0 1.0 k
is equivalent to
0.2 0.3 1.0 scn
in the colour space shown in this example.

| 10 0 obj | %Colour space [/DeviceN [/Cyan /Magenta /Black] /DeviceCMYK 15 0 R ] endobj |

| 15 0 obj | %Tint transformation function <</FunctionType 4 |  |  |  |
| --- | --- | --- | --- | --- |
| /Domain [0.0 | 1.0 | 0.0 1.0 | 0.0 | 1.0] |
| /Range [0.0 1.0 | 0.0 1.0 | 0.0 1.0  0.0 | 1.0] /Length 13 >> stream |  |
| { 0 | exch } endstream endobj |  |  |  |

Non-zero overprint mode shall apply only to painting operations that use the current colour in the
graphics state when the current colour space is DeviceCMYK (or is implicitly converted to
DeviceCMYK; see (8.6.5.7, "Implicit conversion of CIE-Based colour spaces"). It shall not, however,
apply to the painting of images or shadings (8.7.4, "Shading patterns"). It also shall not apply if the
native colour space of the output device does not include CMYK device colourants; in that case, source
colours shall be converted to the device’s native colour space, and all components participate in the
conversion, whatever their values.

NOTE 2       This is shown explicitly in the alternate colour space and tint transformation function of the
DeviceN colour space (see Example 3 in 8.6.6.5, "DeviceN colour spaces").
See 11.7.4, "Overprinting and transparency", for further discussion of the role of overprinting in the
transparent imaging model.

8.6.8 Colour operators

"Table 73 — Colour operators" lists the PDF operators that control colour spaces and colour values.
Also colour-related is the graphics state operator ri, listed in "Table 56 — Graphics state operators"
and discussed under 8.6.5.8, "Rendering intents". Colour operators may appear at the page description
level or inside text objects (see "Figure 9 — Graphics objects").


## Page 232

Table 73 — Colour operators

| Operands | Operator | Description |

| name | CS | (PDF 1.1) Set the current colour space to use for stroking operations. The operand name shall be a name object. If the colour space is one that can be specified by a name and no additional parameters (DeviceGray, DeviceRGB, DeviceCMYK, and certain cases of Pattern), the name may be specified directly. Otherwise, it shall be a name defined in the ColorSpace subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"); the associated value shall be an array describing the colour space (see 8.6.3, "Colour space families"). The names DeviceGray, DeviceRGB, DeviceCMYK, and Pattern always identify the corresponding colour spaces directly; they never refer to resources in the ColorSpace subdictionary. The CS operator shall also set the current stroking colour to its initial value, which depends on the colour space: In a DeviceGray, DeviceRGB, CalGray, or CalRGB colour space, the initial colour shall have all components equal to 0.0. In a DeviceCMYK colour space, the initial colour shall be [0.0 0.0 0.0 1.0]. In a Lab or ICCBased colour space, the initial colour shall have all components equal to 0.0 unless that falls outside the intervals specified by the space’s Range entry, in which case the nearest valid value shall be substituted. In an Indexed colour space, the initial colour value shall be 0. In a Separation or DeviceN colour space, the initial tint value shall be 1.0 for all colourants. In a Pattern colour space, the initial colour shall be a pattern object that causes nothing to be painted. |

| name | cs | (PDF 1.1) Same as CS but used for nonstroking operations. |

| c1… cn | SC | (PDF 1.1) Set the colour to use for stroking operations in a device, CIE-based (other than ICCBased), or Indexed colour space. The number of operands required and their interpretation depends on the current stroking colour space: For DeviceGray, CalGray, and Indexed colour spaces, one operand shall be required (n = 1). For DeviceRGB, CalRGB, and Lab colour spaces, three operands shall be required (n = 3). For DeviceCMYK, four operands shall be required (n = 4). |

| c1… cn | SCN | (PDF 1.2) Same as SC but also supports Pattern, Separation, DeviceN and |
| --- | --- | --- |
| c1… cn name | SCN | ICCBased colour spaces. If the current stroking colour space is a Separation, DeviceN, or ICCBased colour space, the operands c1… cn shall be numbers. The number of operands and their interpretation depends on the colour space. If the current stroking colour space is a Pattern colour space, name shall be the name of an entry in the Pattern subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). For an uncoloured tiling pattern (PatternType = 1 and PaintType = 2), c1… cn shall be component values specifying a colour in the pattern’s underlying colour space. For other types of patterns, these operands shall not be specified. |

| c1… cn | sc | (PDF 1.1) Same as SC but used for nonstroking operations. |


## Page 233

| Operands | Operator | Description |

| c1… cn | scn | (PDF 1.2) Same as SCN but used for nonstroking operations. |
| --- | --- | --- |
| c1… cn name | scn |  |

| gray | G | Set the stroking colour space to DeviceGray (or the DefaultGray colour space; see 8.6.5.6, "Default colour spaces") and set the gray level to use for stroking operations. gray shall be a number between 0.0 (black) and 1.0 (white). |

| gray | g | Same as G but used for nonstroking operations. |

| r g b | RG | Set the stroking colour space to DeviceRGB (or the DefaultRGB colour space; see 8.6.5.6, "Default colour spaces") and set the colour to use for stroking operations. Each operand shall be a number between 0.0 (minimum intensity) and 1.0 (maximum intensity). |

| r g b | rg | Same as RG but used for nonstroking operations. |

| c m y k | K | Set the stroking colour space to DeviceCMYK (or the DefaultCMYK colour space; see 8.6.5.6, "Default colour spaces") and set the colour to use for stroking operations. Each operand shall be a number between 0.0 (zero concentration) and 1.0 (maximum concentration). The behaviour of this operator is affected by the overprint mode (see 8.6.7, "Overprint control"). |

| c m y k | k | Same as K but used for nonstroking operations. |

Invoking operators that specify colours or other colour-related parameters in the graphics state is
restricted in certain circumstances. This restriction occurs when defining graphical figures whose
colours shall be specified separately each time they are used. Specifically, the restriction applies in
these circumstances:

| • | In any glyph description that uses the d1 operator (see 9.6.4, "Type 3 fonts") and to all other |  |  |
| --- | --- | --- | --- |
| content streams invoked from within th | e same glyph description. |  |  |
| • | In the content stream of an uncoloured tiling pattern ( | see 8.7.3.3, "Uncoloured tiling patterns | ") |

and to all other content streams invoked from within the uncoloured tiling pattern stream.
In these circumstances:

| • | All of the following operators shall be ignored: |

| CS | scn | K |
| --- | --- | --- |
| cs | G | k |
| SC | g | ri |
| SCN | RG | sh |

sc                   rg
| • | All of the following entries, if present in the graphics state parameter dictionary of a | gs operator |

shall be ignored:


## Page 234

| TR | BG | UCR |

| TR2 | BG2 | UCR2 |

| HT | UseBlackPtComp |

| • | Unless painting an image mask, all image painting operators shall be ignored. |

NOTE 1       Painting an image mask (see 8.9.6.2, "Stencil masking") is permitted because it does not specify
colours; instead, it designates places where the current colour is painted.
NOTE 2       (2020) Prior PDF specifications at this location stated that these circumstances would cause an
error, but elsewhere also stated that these same circumstances would be ignored. In PDF 2.0, the
| requirement stated here has changed to ignore to be consistent throughout this document | . |

8.7 Patterns

8.7.1 General

Patterns come in two varieties:

| • | Tiling patterns consist of a small graphical figure (called a pattern cell) that is replicated at fixed |

horizontal and vertical intervals to fill the area to be painted. The graphics objects to use for tiling
shall be described by a content stream.
| • | Shading patterns define a gradient fill that produces a smooth transition bet | ween colours across |

the area.
The colour to use shall be specified as a function of position using any of a variety of methods.

NOTE 1       When operators such as S (stroke), f (fill), and Tj (show text) paint an area of the page with the
current colour, they ordinarily apply a single colour that covers the area uniformly. However,
"paint" can be applied that consists of a repeating graphical figure or a smoothly varying colour
gradient instead of a simple colour. Such a repeating figure or smooth gradient is called a
pattern. Patterns are quite general, and have many uses; for example, they can be used to create
various graphical textures, such as weaves, brick walls, sunbursts, and similar geometrical and
chromatic effects.
NOTE 2       Older techniques such as defining a pattern by using character glyphs in a special font and
painting them repeatedly with the Tj operator are not recommended. Another technique,
| defining patterns as halftone screens, | is also not recommended because the effects produced are device-dependent. |
| --- | --- |
| Patterns shall be specified in a special family of colour spaces named | Pattern. These spaces shall use |
| pattern objects as the equivalent of colour values instead of the numeric com | ponent values used with |

other spaces. A pattern object shall be a dictionary or a stream, depending on the type of pattern; the
| term pattern dictionary is used generically throughout this | subclause to refer to either a dictionary |
| --- | --- |
| object or the dictionary p | ortion of a stream object. (Those pattern objects that are streams are |

specifically identified as such in the descriptions of particular pattern types; unless otherwise stated,
| they are understood to be simple dictionaries instead.) This | subclause describes Pattern colour spaces |

and the specification of colour values within them.

NOTE 3       See 8.6, "Colour spaces", for information about colour spaces and colour values in general and
11.6.7, "Patterns and transparency", for further discussion of the treatment of patterns in the
transparent imaging model.


## Page 235

8.7.2 General properties of patterns

A pattern dictionary contains descriptive information defining the appearance and properties of a
pattern. All pattern dictionaries shall contain an entry named PatternType, whose value identifies the
kind of pattern the dictionary describes: Type 1 for a tiling pattern or Type 2 for a shading pattern. The
remaining contents of the dictionary depend on the pattern type and are detailed in the subclauses on
individual pattern types.

All patterns shall be treated as colours; a Pattern colour space shall be established with the CS or cs
operator just like other colour spaces, and a particular pattern shall be installed as the current colour
with the SCN or scn operator (see "Table 73 — Colour operators").

A pattern’s appearance is described with respect to its own internal coordinate system. Every pattern
has a pattern matrix, a transformation matrix that maps the pattern’s internal coordinate system to the
default coordinate system of the pattern’s parent content stream (the content stream in which the
pattern is defined as a resource). The concatenation of the pattern matrix with that of the parent
content stream establishes the pattern coordinate space, within which all graphics objects in the
pattern shall be interpreted.

If a pattern is used on a page, the pattern appears in the Pattern subdictionary of that page’s resource
dictionary, and the pattern matrix maps pattern space to the default (initial) coordinate space of the
page. Changes to the page’s transformation matrix that occur within the page’s content stream, such as
rotation and scaling, have no effect on the pattern; it maintains its original relationship to the page no
matter where on the page it is used. Similarly, if a pattern is used within a form XObject (see 8.10,
"Form XObjects"), the pattern matrix maps pattern space to the form’s default user space (that is, the
form coordinate space at the time the form is painted with the Do operator). A pattern can be used
within another pattern; the inner pattern’s matrix defines its relationship to the pattern space of the
outer pattern.

NOTE         The PostScript language allows a pattern to be defined in one context but used in another. For
example, a pattern can be defined on a page (that is, its pattern matrix maps the pattern
coordinate space to the user space of the page) but is used in a form on that page, so that its
| relationship to the page is independent of each individual placement of the form. PDF does no | t support this feature. |

8.7.3 Tiling patterns

8.7.3.1 General

A tiling pattern consists of a small graphical figure called a pattern cell. Painting with the pattern
replicates the cell at fixed horizontal and vertical intervals to fill an area. The effect is as if the figure
were painted on the surface of a clear glass tile, identical copies of which were then laid down in an
array covering the area and trimmed to its boundaries. This process is called tiling the area.

The pattern cell can include graphical elements such as filled areas, text, and sampled images. Its shape
need not be rectangular, and the spacing of tiles can differ from the dimensions of the cell itself. When
performing painting operations such as S (stroke) or f (fill), the PDF processor shall paint the cell on
the current page as many times as necessary to fill an area. The order in which individual tiles
(instances of the cell) are painted is unspecified and unpredictable; figures on adjacent tiles should not
overlap.


## Page 236

The appearance of the pattern cell shall be defined by a content stream containing the painting
operators needed to paint one instance of the cell. Besides the usual entries common to all streams (see
"Table 5 — Entries common to all stream dictionaries"), this stream’s dictionary may contain the
additional entries listed in "Table 74 — Additional entries specific to a Type 1 pattern dictionary".

Table 74 — Additional entries specific to a Type 1 pattern dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Pattern for a pattern dictionary. |

| PatternType | integer | (Required) A code identifying the type of pattern that this dictionary describes; shall be 1 for a tiling pattern. |

| PaintType | integer | (Required) A code that determines how the colour of the pattern cell shall be specified: |

1 Coloured tiling pattern. The pattern’s content stream shall specify the colours
used to paint the pattern cell. The current colours in use when the PDF
processor begins processing the content stream are the ones initially in effect in
the pattern’s parent content stream. This is similar to the definition of the
pattern matrix; see 8.7.2, "General properties of patterns".

2 Uncoloured tiling pattern. The pattern’s content stream shall not specify any
colour information. Instead, the entire pattern cell is painted with a separately
specified colour each time the pattern is used. Essentially, the content stream
describes a stencil through which the current nonstroking colour shall be
poured. The content stream shall not invoke operators that specify colours or
other colour-related parameters in the graphics state; otherwise, the operator is
ignored and processing of the stream continues without error (see 8.6.8,
"Colour operators"). The content stream may paint an image mask however,
since it does not specify any colour information (see 8.9.6.2, "Stencil masking").

| TilingType | integer | (Required) A code that controls adjustments to the spacing of tiles relative to the device pixel grid: |

1 Constant spacing. Pattern cells shall be spaced consistently — that is, by a
multiple of a device pixel. To achieve this, the PDF processor may need to
distort the pattern cell slightly by making small adjustments to XStep, YStep,
and the transformation matrix. The amount of distortion shall not exceed 1
device pixel.

2 No distortion. The pattern cell shall not be distorted, but the spacing between
pattern cells may vary by as much as 1 device pixel, both horizontally and
vertically, when the pattern is painted. This achieves the spacing requested by
XStep and YStep on average but not necessarily for each individual pattern cell.

3 Constant spacing and faster tiling. Pattern cells shall be spaced consistently as in
tiling Type 1 but with additional distortion permitted to enable a more efficient
implementation.

| BBox | rectangle | (Required) An array of four numbers in the pattern coordinate system giving the coordinates of the left, bottom, right, and top edges, respectively, of the pattern cell’s bounding box. These boundaries shall be used to clip the pattern cell. |

NOTE 1    A BBox of zero height or width will still paint one pixel (see 10.7.4, "Scan conversion
rules").

| XStep | number | (Required) The desired horizontal spacing be | tween pattern cells, measured in the pattern coordinate system. |


## Page 237

| Key | Type | Value |

| YStep | number | (Required) The desired vertical spacing between pattern cells, measured in the pattern coordinate system. |

NOTE 2    XStep and YStep can differ from the dimensions of the pattern cell implied by the
BBox entry. This allows tiling with irregularly shaped figures.
XStep and YStep may be either positive or negative but shall not be zero.

| Resources | dictionary | (Required) A resource dictionary that shall contain all of the named resources required by the pattern’s content stream (see 7.8.3, "Resource dictionaries"). |

| Matrix | array | (Optional) An array of six numbers specifying the pattern matrix (see 8.7.2, "General properties of patterns"). Default value: the identity matrix [1 0 0 1 0 0]. |

The pattern dictionary’s BBox, XStep, and YStep values shall be interpreted in the pattern coordinate
system, and the graphics objects in the pattern’s content stream shall be defined with respect to that
coordinate system. The placement of pattern cells in the tiling is based on the location of one key
pattern cell, which is then displaced by multiples of XStep and YStep to replicate the pattern. The
origin of the key pattern cell coincides with the origin of the pattern coordinate system. The phase of
the tiling can be controlled by the translation components of the Matrix entry in the pattern dictionary.

Prior to painting with a tiling pattern, the PDF writer shall establish the pattern as the current colour in
the graphics state. Subsequent painting operations tile the painted areas with the pattern cell
described by the pattern’s content stream. To obtain the pattern cell, the PDF processor shall perform
these steps:

| a) | Saves the current graphics state (as if by invoking the | q operator) |
| --- | --- | --- |
| b) | Installs the graphics state that was in effect at the beginning of the pattern’s parent content stream, with |  |
| the current transformation matrix altered by the pattern matrix as described in | 8.7.2, "General |  |
| properties of patterns | " |  |
| c) | Paints the graphics objects specified in the pattern’s content stream |  |
| d) | Restores the saved graphics state (as if by invoking the | Q operator) |
| The pattern’s content stream shall not set any of the device | -dependent parameters in the graphics |  |

state (see "Table 52 — Device-dependent graphics state parameters") because it can result in incorrect
output.

8.7.3.2 Coloured tiling patterns

A coloured tiling pattern is a pattern whose colour is self-contained. In the course of painting the
pattern cell, the pattern’s content stream explicitly sets the colour of each graphical element it paints. A
single pattern cell may contain elements that are painted different colours; it may also contain sampled
grayscale or colour images. This type of pattern is identified by a pattern type of 1 and a paint type of 1
in the pattern dictionary.

When the current colour space is a Pattern space, a coloured tiling pattern shall be selected as the
current colour by supplying its name as the single operand to the SCN or scn operator. This name shall
be the key of an entry in the Pattern subdictionary of the current resource dictionary (see 7.8.3,
"Resource dictionaries"), whose value shall be the stream object representing the pattern. Since the


## Page 238

pattern defines its own colour information, no additional operands representing colour components
shall be specified to SCN or scn.

EXAMPLE 1          If P1 is the name of a pattern resource in the current resource dictionary, the following code establishes it
as the current nonstroking colour:

/Pattern cs
/P1 scn
NOTE 1       Subsequent executions of nonstroking painting operators, such as f (fill), Tj (show text), or Do
(paint external object) with an image mask, use the designated pattern to tile the areas to be
painted.
NOTE 2       The following defines a page (object 5) that paints three circles and a triangle using a coloured
tiling pattern (object 15) over a yellow background. The pattern consists of the symbols for the
four suits of playing cards (spades, hearts, diamonds, and clubs), which are character glyphs
taken from the ZapfDingbats font (see D.6, "ZapfDingbats set and encoding"); the pattern’s
content stream specifies the colour of each glyph. "Figure 31 — Coloured tiling pattern" shows
| the pattern cell on the left side and the patterned shapes on the right side | . |

EXAMPLE 2

| 5 0 obj | %Page object <</Type /Page /Parent 2 0 R /Resources 10 0 R /Contents 30 0 R |
| --- | --- |
| /CropBox [0 0 | 225 225] >> |

endobj

| 10 0 obj | %Resource dictionary for page <</Pattern  <</P1 15 0 R>> >> |

endobj

| 15 0 obj | %Pattern definition <</Type /Pattern |
| --- | --- |
| /PatternType 1 | %Tiling pattern |
| /PaintType 1 | %Coloured /TilingType 2 |
| /BBox [0 0 | 100 100] /XStep 100 /YStep 100 /Resources 16 0 R /Matrix [0.4 0.0 0.0 0.4 0.0 0.0] /Length 183 >> |

stream
| BT | %Begin text object |
| --- | --- |
| /F1 1 Tf | %Set text font and size |
| 64 0 0 64 7.1771 2.4414 Tm | %Set text matrix |

0 Tc                                                      %Set character spacing

0 Tw                                                      %Set word spacing
| 1.0 0.0 0.0 rg | %Set nonstroking colour to red |
| --- | --- |
| (\001) Tj | %Show spade glyph |

| 0.7478 -0.007 TD | %Move text position |  |  |  |
| --- | --- | --- | --- | --- |
| 0.0 | 1.0 | 0.0 | rg | %Set nonstroking colour to green |
| (\002) Tj | %Show heart glyph |  |  |  |

| -0.7323 0.7813 TD | %Move text position |
| --- | --- |
| 0.0 0.0 1.0 rg | %Set nonstroking colour to blue |
| (\003) Tj | %Show diamond glyph |


## Page 239

| 0.6913 0.007 TD | %Move text position |  |
| --- | --- | --- |
| 0.0 | 0.0 0.0 rg | %Set nonstroking colour to black |
| (\004) Tj | %Show club glyph |  |

| ET | %End text object |

endstream
endobj

| 16 0 obj | %Resource dictionary for pattern <</Font <</F1 20 0 R>> >> |

endobj

| 20 0 obj | %Font for pattern <</Type /Font /Subtype /Type1 /Encoding 21 0 R /BaseFont /ZapfDingbats >> |

endobj

| 21 0 obj | %Font encoding <</Type /Encoding /Differences [1 /a109 /a110 /a111 /a112] >> |

endobj

| 30 0 obj | %Contents of page <</Length 1252>> |

stream

0.0 G                                                     %Set stroking colour to black
| 1.0 1.0 0.0 rg | %Set nonstroking colour to yellow |
| --- | --- |
| 25 175 175 -150 re | %Construct rectangular path |
| f | %Fill path |

| /Pattern cs | %Set pattern colour space |
| --- | --- |
| /P1 scn | %Set pattern as nonstroking colour |

| 99.92 49.92 m | %Start new path |
| --- | --- |
| 99.92 77.52 77.52 99.92 49.92 99.92 c | %Construct lower-left circle 22.32 99.92 -0.08 77.52 -0.08 49.92 c -0.08 22.32 22.32 -0.08 49.92 -0.08 c 77.52 -0.08 99.92 22.32 99.92 49.92 c |
| B | %Fill and stroke path |

| 224.96 49.92 m | %Start new path |
| --- | --- |
| 224.96 77.52 202.56 99.92 174.96 99.92 c | %Construct lower-right circle 147.36 99.92 124.96 77.52 124.96 49.92 c 124.96 22.32 147.36 -0.08 174.96 -0.08 c 202.56 -0.08 224.96 22.32 224.96 49.92 c |
| B | %Fill and stroke path |

| 87.56 201.70 m | %Start new path |
| --- | --- |
| 63.66 87.90 55.46 157.32 69.26 133.40 c | %Construct upper circle 83.06 109.50 113.66 101.30 137.56 115.10 c 161.46 128.90 169.66 159.50 155.86 183.40 c 142.06 207.30 111.46 215.50 87.56 201.70 c |
| B | %Fill and stroke path |

| 50 50 m | %Start new path |
| --- | --- |
| 175 50 l | %Construct triangular path 112.5 158.253 l |
| b | %Close, fill, and stroke path |

endstream
endobj
NOTE 3       Several features of Example 2 in this subclause are noteworthy:


## Page 240

The three circles and the triangle are painted with the same pattern. The pattern cells align, even
though the circles and triangle are not aligned with respect to the pattern cell. For example, the
position of the blue diamonds varies relative to the three circles.
The pattern cell does not completely cover the tile: it leaves the spaces between the glyphs
unpainted. When the tiling pattern is used as a colour, the existing background (the yellow
rectangle) shows through these unpainted areas.

Figure 31 — Coloured tiling pattern

8.7.3.3 Uncoloured tiling patterns

An uncoloured tiling pattern is a pattern that has no inherent colour: the colour shall be specified
separately whenever the pattern is used. It provides a way to tile different regions of the page with
pattern cells having the same shape but different colours. This type of pattern shall be identified by a
pattern type of 1 and a paint type of 2 in the pattern dictionary. The pattern’s content stream shall not
explicitly specify any colours (see 8.6.8, "Colour operators"); it may paint an image mask (see 8.9.6.2,
"Stencil masking") but no other kind of image.

A Pattern colour space representing an uncoloured tiling pattern shall have a parameter: an object
identifying the underlying colour space in which the actual colour of the pattern shall be specified. The
underlying colour space shall be given as the second element of the array that defines the Pattern
colour space.

EXAMPLE 1          The array

[/Pattern /DeviceRGB]

defines a Pattern colour space with DeviceRGB as its underlying colour space.

NOTE         The underlying colour space cannot be another Pattern colour space.
Operands supplied to the SCN or scn operator in such a colour space shall include a colour value in the
underlying colour space, specified by one or more numeric colour components, as well as the name of a
pattern object representing an uncoloured tiling pattern.


## Page 241

EXAMPLE 2           If  the  current  resource  dictionary   (see  7.8.3,  "Resource  dictionaries")    defines  Cs3  as  the  name  of  a
ColorSpace resource whose value is the Pattern colour space shown above and P2 as a Pattern resource
denoting an uncoloured tiling pattern, the code

/Cs3 cs
0.30 0.75 0.21 /P2 scn

establishes Cs3 as the current nonstroking colour space and P2 as the current nonstroking colour, to be
painted in the colour represented by the specified components in the DeviceRGB colour space. Subsequent
| executions of nonstroking painting operators, such as | f (fill), Tj (show text), and Do (paint external object) with an image mask, use the designated pattern and colour to tile the areas to be painted. The same pattern can be used repeatedly with a different colour each time. |

EXAMPLE 3           This example is similar to Example 2 in 8.7.3.2, "Coloured tiling patterns", except that it uses an uncoloured
| tiling pattern to | paint the three circles and the triangle, each in a different colour (see | "Figure  32  — Uncoloured tiling pattern". To do so, it supplies four operands each time it invokes the scn operator: three |
| --- | --- | --- |
| numbers denoting the colour components in the underlying | DeviceRGB colour space, along with the name of the pattern. |  |

| 5 0 obj | %Page object <</Type /Page /Parent 2 0 R /Resources 10 0 R /Contents 30 0 R /CropBox [0 0 225 225] >> endobj |

| 10 0 obj | %Resource dictionary for page <</ColorSpace <</Cs12 12 0 R>> /Pattern <</P1 15 0 R>> >> endobj |

| 12 0 obj | %Colour space [/Pattern /DeviceRGB] endobj |

| 15 0 obj | %Pattern definition <</Type /Pattern |
| --- | --- |
| /PatternType 1 | %Tiling pattern |
| /PaintType 2 | %Uncoloured /TilingType 2 /BBox [0 0 100 100] /XStep 100 /YStep 100 /Resources 16 0 R /Matrix [0.4 0.0 0.0 0.4 0.0 0.0] /Length 127 >> stream |
| BT | %Begin text object |
| /F1 1 Tf | %Set text font and size |
| 64 0 0 64 7.1771 2.4414 Tm | %Set text matrix |

0 Tc                                                           %Set character spacing

0 Tw                                                           %Set word spacing

| (001) Tj | %Show spade glyph |
| --- | --- |
| 0.7478 -0.007 TD | %Move text position |
| (\002) Tj | %Show heart glyph |

| -0.7323 0.7813 TD | %Move text position |
| --- | --- |
| (\003) Tj | %Show diamond glyph |

| 0.6913 0.007 TD | %Move text position |
| --- | --- |
| (\004) Tj | %Show club glyph |


## Page 242

| ET | %End text object |

endstream
endobj

| 16 0 obj | %Resource dictionary for pattern <</Font <</F1 20 0 R>> >> |

endobj

| 20 0 obj | %Font for pattern <</Type /Font /Subtype /Type1 /Encoding 21 0 R /BaseFont /ZapfDingbats >> |

endobj

| 21 0 obj | %Font encoding <</Type /Encoding /Differences [1 /a109 /a110 /a111 /a112] >> |

endobj

| 30 0 obj | %Contents of page <</Length 1316>> |

stream

0.0 G                                                       %Set stroking colour to black
| 1.0 1.0 0.0 rg | %Set nonstroking colour to yellow |
| --- | --- |
| 25 175 175 -150 re | %Construct rectangular path |
| f | %Fill path |

| /Cs12 cs | %Set pattern colour space |
| --- | --- |
| 0.77 0.20 0.00 /P1 scn | %Set nonstroking colour and pattern |
| 99.92 49.92 m | %Start new path |
| 99.92 77.52 77.52 99.92 49.92 99.92 c | %Construct lower-left circle 22.32 99.92 -0.08 77.52 -0.08 49.92 c -0.08 22.32 22.32 -0.08 49.92 -0.08 c 77.52 -0.08 99.92 22.32 99.92 49.92 c |

| B | %Fill and stroke path |
| --- | --- |
| 0.2 0.8 0.4 /P1 scn | %Change nonstroking colour |
| 224.96 49.92 m | %Start new path |
| 224.96 77.52 202.56 99.92 174.96 99.92 c | %Construct lower-right circle 147.36 99.92 124.96 77.52 124.96 49.92 c 124.96 22.32 147.36 -0.08 174.96 -0.08 c 202.56 -0.08 224.96 22.32 224.96 49.92 c |
| B | %Fill and stroke path |

| 0.3 0.7 1.0 /P1 scn | %Change nonstroking colour |
| --- | --- |
| 87.56 201.70 m | %Start new path |
| 63.66 187.90 55.46 157.30 69.26 133.40 c | %Construct upper circle 83.06 109.50 113.66 101.30 137.56 115.10 c 161.46 128.90 169.66 159.50 155.86 183.40 c 142.06 207.30 111.46 215.50 87.56 201.70 c |
| B | %Fill and stroke path |

| 0.5 0.2 1.0 /P1 scn | %Change nonstroking colour |
| --- | --- |
| 50 50 m | %Start new path |
| 175 50 l | %Construct triangular path 112.5 158.253 l |
| b | %Close, fill, and stroke path |

endstream
endobj


## Page 243

Figure 32 — Uncoloured tiling pattern

8.7.4 Shading patterns

8.7.4.1 General

Shading patterns (PDF 1.3) provide a smooth transition between colours across an area to be painted,
independent of the resolution of any particular output device and without specifying the number of
steps in the colour transition. Patterns of this type shall be described by pattern dictionaries with a
pattern type of 2. "Table 75 — Entries in a Type 2 pattern dictionary" shows the contents of this type of
dictionary.

Table 75 — Entries in a Type 2 pattern dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Pattern for a pattern dictionary. |

| PatternType | integer | (Required) A code identifying the type of pattern that this dictionary describes; shall be 2 for a shading pattern. |

| Shading | dictionary | (Required) A shading object (see below) defining the shading pattern’s |
| --- | --- | --- |
| or stream | gradient fill. The contents of the dictionary shall consist of the entries in "Table 77 — Entries common to all shading dictionaries" and those in one of Table 78 to Table 83. |  |

| Matrix | array | (Optional) An array of six numbers specifying the pattern matrix (see 8.7.2, "General properties of patterns"). Default value: the identity matrix [1 0 0 1 0 0]. |

| ExtGState | dictionary | (Optional) A graphics state parameter dictionary (see 8.4.5, "Graphics state parameter dictionaries") containing graphics state parameters to be put into effect temporarily while the shading pattern is painted. Any parameters that are not so specified shall be inherited from the graphics state that was in effect at the beginning of the pattern’s parent content stream, and as modified by clause 11.6.7, "Patterns and transparency". |


## Page 244

The most significant entry is Shading, whose value shall be a shading object defining the properties of
the shading pattern’s gradient fill. This is a complex "paint" that determines the type of colour
transition the shading pattern produces when painted across an area. A shading object shall be a
dictionary or a stream, depending on the type of shading; the term shading dictionary is used
generically throughout this subclause to refer to either a dictionary object or the dictionary portion of
a stream object. (Those shading objects that are streams are specifically identified as such in the
descriptions of particular shading types; unless otherwise stated, they are understood to be simple
dictionaries instead.)

By setting a shading pattern as the current colour in the graphics state, a PDF content stream may use
it with painting operators such as f (fill), S (stroke), Tj (show text), or Do (paint external object) with
an image mask to paint a path, character glyph, or mask with a smooth colour transition. When a
shading is used in this way, the geometry of the gradient fill is independent of that of the object being
painted.

8.7.4.2 Shading operator

When the area to be painted is a relatively simple shape whose geometry is the same as that of the
gradient fill itself, the sh operator may be used instead of the usual painting operators. sh accepts a
shading dictionary as an operand and applies the corresponding gradient fill directly to current user
space. This operator does not require the creation of a pattern dictionary or a path and works without
reference to the current colour in the graphics state. "Table 76 — Shading operator" describes the sh
operator.

NOTE          Patterns defined by Type 2 pattern dictionaries do not tile. To create a tiling pattern containing a
gradient fill, invoke the sh operator from within the content stream of a Type 1 (tiling) pattern.

Table 76 — Shading operator

| Operands | Operator | Description |

| name | sh | (PDF 1.3) Paint the shape and colour shading described by a shading dictionary, subject to the current clipping path. The current colour in the graphics state is neither used nor altered. The effect is different from that of painting a path using a shading pattern as the current colour. name is the name of a shading dictionary resource in the Shading subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). All coordinates in the shading dictionary are interpreted relative to the current user space. (By contrast, when a shading dictionary is used in a Type 2 pattern, the coordinates are expressed in pattern space.) All colours are interpreted in the colour space identified by the shading dictionary’s ColorSpace entry (see "Table 77 — Entries common to all shading dictionaries"). The Background entry, if present, is ignored. This operator should be applied only to bounded or geometrically defined shadings. If applied to an unbounded shading, it paints the shading’s gradient fill across the entire clipping region, which may be time- consuming. |


## Page 245

8.7.4.3 Shading dictionaries

A shading dictionary specifies details of a particular gradient fill, including the type of shading to be
used, the geometry of the area to be shaded, and the geometry of the gradient fill. Various shading
types are available, depending on the value of the dictionary’s ShadingType entry:

| • | Function-based shadings (Type 1) define the colour of every point in the domain using a |

mathematical function (not necessarily smooth or continuous).
| • | Axial shadings (Type 2) define a colour blend along a line between two points, optionally |

extended beyond the boundary points by continuing the boundary colours.
| • | Radial shadings (Type 3) define a blend between two circles, optionally extended beyond the |

boundary circles by continuing the boundary colours. This type of shading is commonly used to
represent three-dimensional spheres and cones.
| • | Free-form Gouraud-shaded triangle meshes (Type 4) define a common construct used by many |

three-dimensional applications to represent complex coloured and shaded shapes. Vertices are
specified in free-form geometry.
| • | Lattice-form Gouraud-shaded triangle meshes (Type 5) are based on the same geometrical |

construct as Type 4 but with vertices specified as a pseudorectangular lattice.
| • | Coons patch meshes (Type 6) construct a shading from one or more colour patches, each bounded |

by four cubic Bézier curves.
| • | Tensor-product patch meshes (Type 7) are similar to Type 6 but with additional control points in |

each patch, affording greater control over colour mapping.
NOTE 1       "Table 77 — Entries common to all shading dictionaries" shows the entries that all shading
dictionaries share in common; entries specific to particular shading types are described in the
relevant subclause.
NOTE 2       The term target coordinate space, used in many of the following descriptions, refers to the
coordinate space into which a shading is painted. For shadings used with a Type 2 pattern
dictionary, this is the pattern coordinate space, discussed in 8.7.2, "General properties of
| patterns". For shadings used directly with the sh operator, it is the current | user space. |

Table 77 — Entries common to all shading dictionaries

| Key | Type | Value |

| ShadingType | integer | (Required) The shading type: |

1 Function-based shading

2 Axial shading

3 Radial shading

4 Free-form Gouraud-shaded triangle mesh

5 Lattice-form Gouraud-shaded triangle mesh

6 Coons patch mesh

7 Tensor-product patch mesh

| ColorSpace | name or | (Required) The colour space in which colour values shall be expressed. This may |
| --- | --- | --- |
| array | be any device, CIE-based, or special colour space except a Pattern space. See 8.7.4.4, "Colour space: special considerations" for further information. |  |


## Page 246

| Key | Type | Value |

| Background | array | (Optional) An array of colour components appropriate to the colour space, specifying a single background colour value. If present, this colour shall be used, before any painting operation involving the shading, to fill those portions of the area to be painted that lie outside the bounds of the shading object. |

NOTE 1   In the opaque imaging model, the effect is as if the painting operation were
performed twice: first with the background colour and then with the shading.
The background colour shall be applied only when the shading is used as part of
a shading pattern, not when painted directly with the sh operator.

| BBox | rectangle | (Optional) An array of four numbers giving the left, bottom, right, and top coordinates, respectively, of the shading’s bounding box. The coordinates shall be interpreted in the shading’s target coordinate space. If present, this bounding box shall be applied as a temporary clipping boundary when the shading is painted, in addition to the current clipping path and any other clipping boundaries in effect at that time. |

NOTE 2   A BBox of zero height or width will still paint one pixel (see 10.7.4, "Scan
conversion rules").

| AntiAlias | boolean | (Optional) A flag indicating whether to filter the shading function to prevent aliasing artifacts. |

NOTE 3   The shading operators sample shading functions at a rate determined by the
resolution of the output device. Aliasing can occur if the function is not smooth
| — that is, if it has a high spatial frequency relative to the sampling rate. Anti | - aliasing can be computationally expensive and is usually unnecessary, since most shading functions are smooth enough or are sampled at a high enough frequency to avoid aliasing effects. Default value: false. |

| Shading types 4 to 7 shall be defined by a stream containing descriptive data | characterising the |

shading’s gradient fill. In these cases, the shading dictionary is also a stream dictionary and may
contain any of the standard entries common to all streams (see "Table 5 — Entries common to all
stream dictionaries"). In particular, the stream dictionary shall include a Length entry.

In addition, some shading dictionaries also include a Function entry whose value shall be a function
object (dictionary or stream) defining how colours vary across the area to be shaded. In such cases, the
| shading dictionary usually defines the geometry of | the shading, and the function defines the colour |

transitions across that geometry. The function is required for some types of shading and optional for
others. Functions are described in detail in 7.10, "Functions".

NOTE 3        Discontinuous colour transitions, or those with high spatial frequency,                     can exhibit aliasing
effects when painted at low effective resolutions.

8.7.4.4 Colour space: special considerations

Conceptually, a shading determines a colour value for each individual point within the area to be
painted. In practice, however, PDF processors may actually compute colour values only for some
| subset of the points in the target area, with the colours of the | intervening points determined by |

interpolation between the ones computed. PDF processors are free to use this strategy as long as the
interpolated colour values approximate those defined by the shading to within the smoothness
tolerance specified in the graphics state (see 10.7.3, "Smoothness tolerance"). The ColorSpace entry


## Page 247

common to all shading dictionaries not only defines the colour space in which the shading specifies its
colour values but also determines the colour space in which colour interpolation is performed.

NOTE 1       Some types of shading (4 to 7) perform interpolation on a parametric value supplied as input to
the shading’s colour function, as described in the relevant subclause. This form of interpolation
is conceptually distinct from the interpolation described here, which operates on the output
colour values produced by the colour function and takes place within the shading’s target colour
space.
Gradient fills between colours defined by most shadings may be implemented using a variety of
interpolation algorithms, and these algorithms may be sensitive to the characteristics of the colour
space.

NOTE 2       Linear interpolation, for example, can have observably different results when applied in a
DeviceCMYK colour space than in a Lab colour space, even if the starting and ending colours are
perceptually identical. The difference arises because the two colour spaces are not linear relative
to each other.
Shadings shall be rendered according to the following rules:

| • | If ColorSpace is a device colour space different from the native colour space of the output device, |

colour values in the shading shall be converted to the native colour space using the standard
conversion formulas described in 10.4, "Conversions among device colour spaces". To optimise
performance, these conversions may take place at any time (before or after any interpolation on
the colour values in the shading). Thus, shadings defined with device colour spaces may have
colour gradient fills that are less accurate and somewhat device-dependent.
NOTE 3:      This does not apply to shadings having a Function entry in their shading dictionary because
| those shading perform gradient fill calculations on a single variable and then | convert to parametric colours. |
| --- | --- |
| • | If ColorSpace is a CIE-based colour space, all gradient fill calculations shall be performed in that |

space. Conversion to device colours shall occur only after all interpolation calculations have been
performed. Thus, the colour gradients are device-independent for the colours generated at each
point.
| • | If ColorSpace is a Separation or DeviceN colour space, a colour conversion (to the alternate |

colour space) occurs only if one or more of the specified colourants is not supported by the
device. In that case, gradient fill calculations shall be performed in the designated Separation or
DeviceN colour space before conversion to the alternate space. Thus, nonlinear tint
transformation functions shall be accommodated for an optimal representation of the shading.
| • | If ColorSpace is an Indexed colour space, all colour values specified in the shading shall be |

immediately converted to the base colour space. Depending on whether the base colour space is a
device or CIE-based space, gradient fill calculations shall be performed as stated above.
Interpolation shall never occur in an Indexed colour space, which is quantised and therefore
inappropriate for calculations that assume a continuous range of colours. For similar reasons, an
Indexed colour space shall not be used in any shading whose colour values are generated by a
function; this rule applies to any shading dictionary that contains a Function entry.

8.7.4.5 Shading types

8.7.4.5.1 General

In addition to the entries listed in "Table 77 — Entries common to all shading dictionaries", all shading
dictionaries have entries specific to the type of shading they represent, as indicated by the value of


## Page 248

their ShadingType entry. The following subclauses describe the available shading types and the
dictionary entries specific to each.

8.7.4.5.2 Type 1 (function-based) shadings

In Type 1 (function-based) shadings, the colour at every point in the domain is defined by a specified
mathematical function. The function need not be smooth or continuous. This type is the most general
of the available shading types and is useful for shadings that cannot be adequately described with any
of the other types. "Table 78 — Additional entries specific to a Type 1 shading dictionary" shows the
shading dictionary entries specific to this type of shading, in addition to those common to all shading
| dictionaries (see "Table 77 — Entries common to all shading dictionaries | "). |

This type of shading shall not be used with an Indexed colour space.

Table 78 — Additional entries specific to a Type 1 shading dictionary

| Key | Type | Value |

| Domain | array | (Optional) An array of four numbers [xmin xmax ymin ymax] specifying the rectangular domain of coordinates over which the colour function(s) are defined. Default value: [0 1 0 1]. |

| Matrix | array | (Optional) An array of six numbers specifying a transformation matrix mapping the coordinate space specified by the Domain entry into the shading’s target coordinate space. |

NOTE     To map the domain rectangle [0 1 0 1] to a 1-inch square with lower-left corner at
coordinates (100, 100) in default user space, the Matrix value would be [72 0 0 72
100 100].
Default value: the identity matrix [1 0 0 1 0 0].

| Function | function | (Required) A 2-in, n-out function or an array of n 2-in, 1-out functions (where n is |
| --- | --- | --- |
| or array | the number of colour components in the shading dictionary’s colour space). Each function’s domain shall be a superset of that of the shading dictionary. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. |  |

The domain rectangle (Domain) establishes an internal coordinate space for the shading that is
independent of the target coordinate space in which it shall be painted. The colour function(s)
(Function) specify the colour of the shading at each point within this domain rectangle. The
transformation matrix (Matrix) then maps the domain rectangle into a corresponding rectangle or
parallelogram in the target coordinate space. Points within the shading’s bounding box (BBox) that fall
outside this transformed domain rectangle shall be painted with the shading’s background colour
(Background); if the shading dictionary has no Background entry, such points shall be left unpainted.
If the function is undefined at any point within the declared domain rectangle, an error may occur,
even if the corresponding transformed point falls outside the shading’s bounding box.

8.7.4.5.3 Type 2 (axial) shadings

Type 2 (axial) shadings define a colour blend that varies along a linear axis between two endpoints and
extends indefinitely perpendicular to that axis. The shading may optionally be extended beyond either


## Page 249

or both endpoints by continuing the boundary colours indefinitely. "Table 79 — Additional entries
specific to a Type 2 shading dictionary" shows the shading dictionary entries specific to this type of
shading, in addition to those common to all shading dictionaries (see "Table 77 — Entries common to
all shading dictionaries      ").

This type of shading shall not be used with an Indexed colour space.

Table 79 — Additional entries specific to a Type 2 shading dictionary

| Key | Type | Value |

| Coords | array | (Required) An array of four numbers [x0 y0 x1 y1] specifying the starting and ending coordinates of the axis, expressed in the shading’s target coordinate space. If the starting and ending coordinates are coincident (x0=x1 and y0=y1) nothing shall be painted. |

| Domain | array | (Optional) An array of two numbers [t0 t1] specifying the limiting values of a parametric variable t. The variable is considered to vary linearly between these two values as the colour gradient varies between the starting and ending points of the axis. The variable t becomes the input argument to the colour function(s). Default value: [0.0 1.0]. |

| Function | function | (Required) A 1-in, n-out function or an array of n 1-in, 1-out functions (where n is |
| --- | --- | --- |
| or array | the number of colour components in the shading dictionary’s colour space). The function(s) shall be called with values of the parametric variable t in the domain defined by the Domain entry. Each function’s domain shall be a superset of that of the shading dictionary. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. |  |

| Extend | array | (Optional) An array of two boolean values specifying whether to extend the shading beyond the starting and ending points of the axis, respectively. Default value: [false false]. |

The colour blend shall be accomplished by linearly mapping each point (x, y) along the axis between
the endpoints (x0, y0) and (x1, y1) to a corresponding point in the domain specified by the shading
dictionary’s Domain entry. The points (0, 0) and (1, 0) in the domain correspond respectively to (x0,
y0) and (x1, y1) on the axis. Since all points along a line in domain space perpendicular to the line from
(0, 0) to (1, 0) have the same colour, only the new value of x needs to be computed:

| ′ | (𝑥1  −  𝑥0)× | (𝑥  −  𝑥0)+ | (𝑦1  −  𝑦0)× | (𝑦  −  𝑦0) |  |
| --- | --- | --- | --- | --- | --- |
| 𝑥  = | (𝑥 | −  𝑥  )2 +  (𝑦 | −  𝑦 | )2 |  |
| 1 | 0 | 1 | 0 |  |  |
| For 0 | ≤  𝑥′ ≤ | 1,t = | t0 +  (t1 − | t0)× | 𝑥′. |

The value of the parametric variable t is then determined from x′ as follows:

| • | For 0 | ≤ | x′  ≤ | 1, 𝑡 = 𝑡0 | + | (𝑡1 − 𝑡0 ) × | x′. |
| --- | --- | --- | --- | --- | --- | --- | --- |
| • | For x′ < 0, if the first element of the Extend array is true, then t = t0 ; otherwise, t is undefined and |  |  |  |  |  |  |

the point shall be left unpainted.
| • | For x′> 1, if the second element of the Extend array is true, then t = t1 ; otherwise, t is undefined |

and the point shall be left unpainted.


## Page 250

The resulting value of t shall be passed as input to the function(s) defined by the shading dictionary’s
Function entry, yielding the component values of the colour with which to paint the point (x, y).

NOTE          "Figure 33 — Axial shading" shows three examples of the use of an axial shading to fill a
rectangle and display text. The area to be filled extends beyond the shading’s bounding box. The
| shading is the same in all three cases, except for the values of the | Background and Extend entries in the shading dictionary. In the first example, the shading is not extended at either end and no background colour is specified; therefore, the shading is clipped to its bounding box at |
| --- | --- |
| both ends. The second example still has no background colour specif | ied, but the shading is extended at both ends; the result is to fill the remaining portions of the filled area with the |
| colours defined at the ends of the shading. In the third example, the shading is | not extended at |
| both ends and a background colour is sp | ecified; therefore, the background colour is used for the portions of the filled area beyond the ends of the shading. |

Extend = [false false], Background not specified

Extend = [true true], Background not specified

Extend = [true true], Background specified

Figure 33 — Axial shading

8.7.4.5.4 Type 3 (radial) shadings

Type 3 (radial) shadings define a colour blend that varies between two circles. Shadings of this type are
commonly used to depict three-dimensional spheres and cones. Shading dictionaries for this type of
shading contain the entries shown in "Table 80 — Additional entries specific to a Type 3 shading
dictionary", as well as those common to all shading dictionaries (see "Table 77 — Entries common to
all shading dictionaries       ").

This type of shading shall not be used with an Indexed colour space.


## Page 251

Table 80 — Additional entries specific to a Type 3 shading dictionary

| Key | Type | Value |

| Coords | array | (Required) An array of six numbers [x0 y0 r0 x1 y1 r1] specifying the centres and radii of the starting and ending circles, expressed in the shading’s target coordinate space. The radii r0 and r1 shall both be greater than or equal to 0. If one radius is 0, the corresponding circle shall be treated as a point; if both are 0, nothing shall be painted. |

| Domain | array | (Optional) An array of two numbers [t0 t1] specifying the limiting values of a parametric variable t. The variable is considered to vary linearly between these two values as the colour gradient varies between the starting and ending circles. The variable t becomes the input argument to the colour function(s). Default value: [0 1]. |

| Function | function | (Required) A 1-in, n-out function or an array of n 1-in, 1-out functions (where n is the |
| --- | --- | --- |
| or array | number of colour components in the shading dictionary’s colour space). The function(s) shall be called with values of the parametric variable t in the domain defined by the shading dictionary’s Domain entry. Each function’s domain shall be a superset of that of the shading dictionary. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. |  |

| Extend | array | (Optional) An array of two boolean values specifying whether to extend the shading beyond the starting and ending circles, respectively. Default value: [false false]. |

The colour blend is based on a family of blend circles interpolated between the starting and ending
circles that shall be defined by the shading dictionary’s Coords entry. The blend circles shall be defined
in terms of a subsidiary parametric variable. The appearance of the shading shall be as if an infinite
number of such circles are painted in turn, each with an infinitely narrow stroke.

s =   t−  t0
t1 −  t0

which varies linearly between 0.0 and 1.0 as t varies across the domain from t0 to t1, as specified by the
dictionary’s Domain entry. The centre and radius of each blend circle shall be given by the following
parametric equations:

𝑥c(s)=    𝑥0  +  s ×  (𝑥1  −  𝑥0)
𝑦c(s)=    𝑦0  +  s ×  (𝑦1  −  𝑦0)
r(s)=    r0 +  s ×  (r1  −  r0)

Each value of s between 0.0 and 1.0 determines a corresponding value of t, which is passed as the input
argument to the function(s) defined by the shading dictionary’s Function entry. This yields the
component values of the colour with which to paint the corresponding blend circle. For values of s not
lying between 0.0 and 1.0, the boolean elements of the shading dictionary’s Extend array determine
whether and how the shading is extended. If the first of the two elements is true, the shading shall be
extended beyond the defined starting circle to values of s less than 0.0; if the second element is true,
the shading shall be extended beyond the defined ending circle to s values greater than 1.0 unless radii
r0 and r1 in the Coords array are both zero.


## Page 252

NOTE 1        Either of the starting and ending circles can be larger than the other. If the shading is extended at
the smaller end, the family of blend circles continues as far as that value of s for which the radius
of the blend circle r (s) = 0. If the shading is extended at the larger end, the blend circles continue
as far as that s value for which r (s) is large enough to encompass the shading’s entire bounding
box (BBox). Extending the shading can thus cause painting to extend beyond the areas defined
by the two circles themselves. The two examples in the rightmost column of "Figure 34 — Radial
shadings depicting a cone" depict the results of extending the shading at the smaller and larger
ends, respectively.

Figure 34 — Radial shadings depicting a cone
Conceptually, all of the blend circles shall be painted in order of increasing values of s, from smallest to
largest. Blend circles extending beyond the starting circle shall be painted in the same colour defined
by the shading dictionary’s Function entry for the starting circle (t = t0, s = 0.0). Blend circles extending
beyond the ending circle shall be painted in the colour defined for the ending circle (t = t1, s = 1.0). The
painting is opaque, with the colour of each circle completely overlaying those preceding it. Therefore, if
a point lies on more than one blend circle, its final colour shall be that of the last of the enclosing circles
to be painted, corresponding to the greatest value of s.

NOTE 2        If one of the starting and ending circles entirely contains the other, the shading depicts a sphere,
| as in "Figure 35 — Radial shadings depicting a sphere | " and "Figure 36 — Radial shadings with extension". In "Figure 35 — Radial shadings depicting a sphere", the inner circle has zero radius; it is the starting circle in the figure on the left and the ending circle in the figure on the right. Neither shading is extended at either the smaller or larger end. In "Figure 36 — Radial shadings with extension", the inner circle in both figures has a non-zero radius and the shading is extended at the larger end. In each plate, a background colour is specified for the figure on the right but not for the figure on the left. |


## Page 253

Figure 35 — Radial shadings depicting a sphere

Figure 36 — Radial shadings with extension
NOTE 3        If neither circle contains the other, the shading depicts a cone. If the starting circle is larger, the
cone appears to point out of the page. If the ending circle is larger, the cone appears to point into
the page (see "Figure 34 — Radial shadings depicting a cone").

EXAMPLE 1           This example    shows the shading used for the objects in the leaf      -covered branch     in "Figure 37 — Radial
shading effect" (8.7.4.5.4, "Type 3 (radial) shadings"). Each leaf is filled with the same radial shading (object
| number 5). The colour function (object 10) is a stitching function (described in | 7.10.4, "Type 3 (stitching) |
| --- | --- |
| functions") whose two subfunctions (objects 11 and 12) are both exponential interpolation functio | ns (see 7.10.3, "Type 2 (exponential interpolation) functions"). |

| 5 0 obj | %Shading dictionary <</ShadingType 3 /ColorSpace /DeviceCMYK |  |
| --- | --- | --- |
| /Coords [0.0 0.0 0.096 0.0 0.0 | 1.0] | %Concentric circles /Function 10 0 R /Extend [true true] >> endobj |

| 10 0 obj | %Colour function <</FunctionType 3 /Domain [0.0 1.0] /Functions [11 0 R 12 0 R] /Bounds [0.708] /Encode [1.0 0.0 0.0 1.0] >> endobj |

| 11 0 obj | %First subfunction <</FunctionType 2 /Domain [0.0 1.0] /C0 [0.929 0.357 1.0 0.298] /C1 [0.631 0.278 1.0 0.027] /N 1.048 >> endobj |

| 12 0 obj | %Second subfunction <</FunctionType 2 |


## Page 254

/Domain [0.0 1.0]
/C0 [0.929 0.357 1.0 0.298]
/C1 [0.941 0.400 1.0 0.102]
/N 1.374
>>
endobj

EXAMPLE 2          This example shows how each leaf shown in "Figure 37 — Radial shading effect" is drawn as a path and then
| filled with the shading (where the name Sh1 is associated wi | th object 5 by the Shading subdictionary of the current resource dictionary;  see 7.8.3, "Resource dictionaries"). |

| 316.789 140.311 m | %Move to start of leaf |
| --- | --- |
| 303.222 146.388 282.966 136.518 279.122 121.983 c | %Curved segment |
| 277.322 120.182 l | %Straight line |
| 285.125 122.688 291.441 121.716 298.156 119.386 c | %Curved segment |
| 336.448 119.386 l | %Straight line |
| 331.072 128.643 323.346 137.376 316.789 140.311 c | %Curved segment |
| W n | %Set clipping path |
| q | %Save graphics state |
| 27.7843 0.00 0.00 -27.7843 310.2461 121.1521 cm | %Set matrix |
| /Sh1 sh | %Paint shading |
| Q | %Restore graphics state |

Figure 37 — Radial shading effect

8.7.4.5.5 Type 4 (free-form Gouraud-shaded triangle mesh) shadings

Type 4 (free-form Gouraud-shaded triangle mesh) shadings are commonly used to represent complex
coloured and shaded three-dimensional shapes. The area to be shaded is defined by a path composed
entirely of triangles. The colour at each vertex of the triangles is specified, and a technique known as
Gouraud interpolation is used to colour the interiors. "Table 81 — Additional entries specific to a Type
4 shading dictionary" shows the entries specific to this type of shading dictionary, in addition to those
| common to all shading dictionaries (see "Table 77 — Entries common to all shading dictionaries | ") and |
| --- | --- |
| stream dictionaries (see "Table 5 — Entries common to all stream dictionaries | "). |

Table 81 — Additional entries specific to a Type 4 shading dictionary

| Key | Type | Value |

| BitsPerCoordinate | integer | (Required) The number of bits used to represent each vertex coordinate. The value shall be 1, 2, 4, 8, 12, 16, 24, or 32. |

| BitsPerComponent | integer | (Required) The number of bits used to represent each colour component. The value shall be 1, 2, 4, 8, 12, or 16. |


## Page 255

| Key | Type | Value |

| BitsPerFlag | integer | (Required) The number of bits used to represent the edge flag for each vertex (see below). The value shall be 2, 4, or 8, but only the least significant 2 bits in each flag value shall be used. The value for the edge flag shall be 0, 1, or 2. |

| Decode | array | (Required) An array of numbers specifying how to map vertex coordinates and colour components into the appropriate ranges of values. The decoding method is similar to that used in image dictionaries (see 8.9.5.2, "Decode arrays"). The ranges shall be specified as follows: [xmin xmax ymin ymax c1,min c1,max… cn,min cn,max] Only one pair of c values shall be specified if a Function entry is present. |

| Function | function | (Optional) A 1-in, n-out function or an array of n 1-in, 1-out functions |
| --- | --- | --- |
| or array | (where n is the number of colour components in the shading dictionary’s colour space). If this entry is present, the colour data for each vertex shall be specified by a single parametric variable rather than by n separate colour components. The designated function(s) shall be called with each interpolated value of the parametric variable to determine the actual colour at each point. Each input value shall be forced into the range interval specified for the corresponding colour component in the shading dictionary’s Decode array. Each function’s domain shall be a superset of that interval. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. This entry shall not be used with an Indexed colour space. |  |

Unlike shading types 1 to 3, types 4 to 7 shall be represented as streams. Each stream contains a
sequence of vertex coordinates and colour data that defines the triangle mesh. In a Type 4 shading,
each vertex is specified by the following values, in the order shown:

f x y c1… cn

where

f is the vertex’s edge flag (discussed below)

x and y are its horizontal and vertical coordinates

c1… cn are its colour components

All vertex coordinates shall be expressed in the shading’s target coordinate space. If the shading
dictionary includes a Function entry, only a single parametric value, t, shall be specified for each
vertex in place of the colour components c1… cn.

The edge flag associated with each vertex determines the way it connects to the other vertices of the
triangle mesh. A vertex va with an edge flag value fa = 0 begins a new triangle, unconnected to any other.
At least two more vertices (vb and vc) shall be provided, but their edge flags shall be ignored. These
| three vertices define a triangle (va, vb, vc), as shown in "Figure 38 — Starting a new triangle in a free | - |

form Gouraud-shaded triangle mesh".


## Page 256

Figure 38 — Starting a new triangle in a free-form Gouraud-shaded triangle mesh
Subsequent triangles shall be defined by a single new vertex combined with two vertices of the
preceding triangle. Given triangle (va, vb, vc), where vertex va precedes vertex vb in the data stream and vb
precedes vc, a new vertex vd can form a new triangle on side vbc or side vac, as shown in "Figure 39 —
| Connecting triangles in a free | -form Gouraud-shaded triangle mesh | ". (Side vab is assumed to be shared |

with a preceding triangle and therefore is not available for continuing the mesh.) If the edge flag is fd =
1 (side vbc), the next vertex forms the triangle (vb, vc, vd); if the edge flag is fd = 2 (side vac), the next vertex
forms the triangle (va, vc, vd). An edge flag of fd = 0 starts a new triangle, as described above.

Figure 39 — Connecting triangles in a free-form Gouraud-shaded triangle mesh
Complex shapes can be created by using the edge flags to control the edge on which subsequent
triangles are formed.

EXAMPLE            "Figure 40 — Varying the value of the edge flag to create different shapes" shows two simple examples.
Mesh1 begins with triangle 1 and uses the following edge flags to draw each succeeding triangle:

| 1 (𝑓𝑎 =  𝑓𝑏 =  𝑓𝑐 =  0) | 7 (𝑓𝑖 =  2) |
| --- | --- |
| 2 (𝑓𝑑 =  1) | 8 (𝑓𝑗 =  2) |
| 3 (𝑓𝑒 =  1) | 9  (𝑓𝑘 =  2) |
| 4 (𝑓𝑓 =  1) | 10 (𝑓𝑙 =  1) |

| 5 (𝑓𝑔 =  1) | 11 (𝑓𝑚  =  1) |


## Page 257

6 (𝑓ℎ  = 1)

Mesh 2 again begins with triangle 1 and uses the following edge flags:
| 1 (𝑓𝑎 =  𝑓𝑏 =  𝑓𝑐 = 0) | 4 (𝑓𝑓 =  2) |
| --- | --- |
| 2 (𝑓𝑑 =  1) | 5 (𝑓𝑔 =  2) |
| 3 (𝑓𝑒 =  2) | 6 (𝑓ℎ = 2) |

The stream shall provide vertex data for a whole number of triangles with appropriate edge flags;
otherwise, an error occurs.

Figure 40 — Varying the value of the edge flag to create different shapes
The data for each vertex consists of the following items, reading in sequence from higher-order to
lower-order bit positions:

| • | An edge flag, expressed in BitsPerFlag bits |
| --- | --- |
| • | A pair of horizontal and vertical coordinates, expressed in BitsPerCoordinate bits each |
| • | A set of n colour components (where n is the number of components in the shading’s colour |

space), expressed in BitsPerComponent bits each, in the order expected by the sc operator
Each set of vertex data shall occupy a whole number of bytes. If the total number of bits required is not
divisible by 8, the last data byte for each vertex is padded at the end with extra bits, which shall be
ignored. The coordinates and colour values shall be decoded according to the Decode array in the
same way as in an image dictionary (see 8.9.5.2, "Decode arrays").

If the shading dictionary contains a Function entry, the colour data for each vertex shall be specified
by a single parametric value t rather than by n separate colour components. All linear interpolation
within the triangle mesh shall be done using the t values. After interpolation, the results shall be passed
to the function(s) specified in the Function entry to determine the colour at each point.

8.7.4.5.6 Type 5 (lattice-form Gouraud-shaded triangle mesh) shadings

Type 5 (lattice-form Gouraud-shaded triangle mesh) shadings are similar to Type 4, but instead of
using free-form geometry, their vertices are arranged in a pseudorectangular lattice, which is
topologically equivalent to a rectangular grid. The vertices are organised into rows, which need not be


## Page 258

geometrically linear (see "Figure 41 — Lattice-form triangle meshes").

Figure 41 — Lattice-form triangle meshes
"Table 82 — Additional entries specific to a Type 5 shading dictionary" shows the shading dictionary
entries specific to this type of shading, in addition to those common to all shading dictionaries (see
"Table 77 — Entries common to all shading dictionaries") and stream dictionaries (see "Table 5 —
Entries common to all stream dictionaries").

The data stream for a Type 5 shading has the same format as for Type 4, except that Type 5 does not
use edge flags to define the geometry of the triangle mesh. The data for each vertex thus consists of the
following values, in the order shown:

x y c1… cn

where

x and y shall be the vertex’s horizontal and vertical coordinates

c1… cn shall be its colour components

Table 82 — Additional entries specific to a Type 5 shading dictionary

| Key | Type | Value |

| BitsPerCoordinate | integer | (Required) The number of bits used to represent each vertex coordinate. The value shall be 1, 2, 4, 8, 12, 16, 24, or 32. |

| BitsPerComponent | integer | (Required) The number of bits used to represent each colour component. The value shall be 1, 2, 4, 8, 12, or 16. |

| VerticesPerRow | integer | (Required) The number of vertices in each row of the lattice; the value shall be greater than or equal to 2. The number of rows need not be specified. |

| Decode | array | (Required) An array of numbers specifying how to map vertex coordinates and colour components into the appropriate ranges of values. The decoding method is similar to that used in image dictionaries (see 8.9.5.2, "Decode arrays"). The ranges shall be specified as follows: [xmin xmax ymin ymax c1,min c1,max… cn,min cn,max] Only one pair of c values shall be specified if a Function entry is present. |


## Page 259

| Key | Type | Value |

| Function | function | (Optional) A 1-in, n-out function or an array of n 1-in, 1-out functions |
| --- | --- | --- |
| or array | (where n is the number of colour components in the shading dictionary’s colour space). If this entry is present, the colour data for each vertex shall be specified by a single parametric variable rather than by n separate colour components. The designated function(s) shall be called with each interpolated value of the parametric variable to determine the actual colour at each point. Each input value shall be forced into the range interval specified for the corresponding colour component in the shading dictionary’s Decode array. Each function’s domain shall be a superset of that interval. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. This entry shall not be used with an Indexed colour space. |  |

All vertex coordinates shall be expressed in the shading’s target coordinate space. If the shading
dictionary includes a Function entry, only a single parametric value, t, shall be specified for each
vertex in place of the colour components c1… cn.

The VerticesPerRow entry in the shading dictionary gives the number of vertices in each row of the
lattice. All of the vertices in a row shall be specified sequentially, followed by those for the next row.
Given m rows of k vertices each, the triangles of the mesh shall be constructed using the following
triplets of vertices, as shown in "Figure 41 — Lattice-form triangle meshes":

| (𝑉𝑖,𝑗, 𝑉𝑖,𝑗+1,𝑉𝑖+1,𝑗) | for 0 ≤  𝑖 ≤  𝑚− | 2,0  ≤ 𝑗 ≤  𝑘 −  2 (𝑉𝑖,𝑗+1, 𝑉𝑖+1,𝑗,𝑉𝑖+1,𝑗+1) |

See 8.7.4.5.5, "Type 4 (free-form Gouraud-shaded triangle mesh) shadings" for further details on the
format of the vertex data.

8.7.4.5.7 Type 6 (Coons patch mesh) shadings

Type 6 (Coons patch mesh) shadings are constructed from one or more colour patches, each bounded
by four cubic Bézier curves. Degenerate Bézier curves are allowed and are useful for certain graphical
effects. At least one complete patch shall be specified.

A Coons patch generally has two independent aspects:

| • | Colours are specified for each corner of the unit square, and bilinear interpolation is used to fill in |

colours over the entire unit square (see the upper figure in "Figure 42 — Coons patch mesh").
| • | Coordinates are mapped from the unit square into a four-sided patch whose sides are not |

necessarily linear (see the lower figure in "Figure 42 — Coons patch mesh". The mapping is
continuous: the corners of the unit square map to corners of the patch and the sides of the unit
square map to sides of the patch, as shown in "Figure 43 — Coordinate mapping from a unit
square to a four-sided Coons Patch".


## Page 260

Figure 42 — Coons patch mesh
The sides of the patch are given by four cubic Bézier curves, C1, C2, D1, and D2, defined over a pair of
parametric variables, u and v, that vary horizontally and vertically across the unit square. The four
corners of the Coons patch satisfy the following equations:

C1(0)=      D1(0)
C1(1)=      D2(0)
C2(0)=      D1(1)
C2(1)=      D2(1)


## Page 261

Figure 43 — Coordinate mapping from a unit square to a four-sided Coons Patch
Two surfaces can be described that are linear interpolations between the boundary curves. Along the u
axis, the surface SC is defined by

| SC(u,v)= | (1 −  v)× | C1(u)+ | v × C2(u) |

Along the v axis, the surface SD is given by

| SD(u,  v)= | (1 −  u)× | D1(v)+ | u ×  D2(v) |

A third surface is the bilinear interpolation of the four corners:

| SB(u,  v)= | (1 −  v)× | [(1  −  u)× | C1(0)+ | u ×  C1(1)]+ | v  × [(1  −  u)× | C2(0)+ | u  × C2(1)] |

The coordinate mapping for the shading is given by the surface S, defined as

S  =  SC +  SD  −  SB

This defines the geometry of each patch. A patch mesh is constructed from a sequence of one or more
such coloured patches.

Patches can sometimes appear to fold over on themselves — for example, if a boundary curve
intersects itself. As the value of parameter u or v increases in parameter space, the location of the
corresponding pixels in device space may change direction so that new pixels are mapped onto
previous pixels already mapped. If more than one point (u, v) in parameter space is mapped to the
same point in device space, the point selected shall be the one with the largest value of v. If multiple
points have the same v, the one with the largest value of u shall be selected. If one patch overlaps
another, the patch that appears later in the data stream shall paint over the earlier one.

NOTE         The patch is a control surface rather than a painting geometry. The outline of a projected square
(that is, the painted area) need not be the same as the patch boundary if, for example, the patch
folds over on itself, as shown in "Figure 44 — Painted area and boundary of a Coons Patch".


## Page 262

Figure 44 — Painted area and boundary of a Coons Patch
"Table 83 — Additional entries specific to a Type 6 shading dictionary" shows the shading dictionary
entries specific to this type of shading, in addition to those common to all shading dictionaries (see
"Table 77 — Entries common to all shading dictionaries") and stream dictionaries (see "Table 5 —
Entries common to all stream dictionaries").

Table 83 — Additional entries specific to a Type 6 shading dictionary

| Key | Type | Value |

| BitsPerCoordinate | integer | (Required) The number of bits used to represent each geometric coordinate. The value shall be 1, 2, 4, 8, 12, 16, 24, or 32. |

| BitsPerComponent | integer | (Required) The number of bits used to represent each colour component. The value shall be 1, 2, 4, 8, 12, or 16. |

| BitsPerFlag | integer | (Required) The number of bits used to represent the edge flag for each patch (see below). The value shall be 2, 4, or 8, but only the least significant 2 bits in each flag value shall be used. Valid values for the edge flag shall be 0, 1, 2, and 3. |

| Decode | array | (Required) An array of numbers specifying how to map coordinates and colour components into the appropriate ranges of values. The decoding method is similar to that used in image dictionaries (see 8.9.5.2, "Decode arrays"). The ranges shall be specified as follows: [xmin xmax ymin ymax c1,min c1,max… cn,min cn,max] Only one pair of c values shall be specified if a Function entry is present. |


## Page 263

| Key | Type | Value |

| Function | function | (Optional) A 1-in, n-out function or an array of n 1-in, 1-out functions |
| --- | --- | --- |
| or array | (where n is the number of colour components in the shading dictionary’s colour space). If this entry is present, the colour data for the corner points of each patch shall be specified by a single parametric variable rather than by n separate colour components. The designated function(s) shall be called with each interpolated value of the parametric variable to determine the actual colour at each point. Each input value shall be forced into the range interval specified for the corresponding colour component in the shading dictionary’s Decode array. Each function’s domain shall be a superset of that interval. If the value returned by the function for a given colour component is out of range, it shall be adjusted to the nearest valid value. This entry shall not be used with an Indexed colour space. |  |

The data stream provides a sequence of Bézier control points and colour values that define the shape
and colours of each patch. All of a patch’s control points shall be given first, followed by the colour
values for its corners. This differs from a triangle mesh (shading types 4 and 5), in which the
coordinates and colour of each vertex are given together. All control point coordinates shall be
expressed in the shading’s target coordinate space. See 8.7.4.5.5, "Type 4 (free-form Gouraud-shaded
triangle mesh) shadings" for further details on the format of the data.

As in free-form triangle meshes (Type 4), each patch has an edge flag that indicates which edge, if any,
it shares with the previous patch. An edge flag of 0 begins a new patch, unconnected to any other. This
shall be followed by 12 pairs of coordinates, x1 y1 x2 y2…x12 y12, which specify the Bézier control points
that define the four boundary curves. "Figure 45 — Colour values and edge flags in Coons Patch
meshes" shows how these control points correspond to the cubic Bézier curves C1, C2, D1, and D2
| identified in "Figure 43 — Coordinate mapping from a uni | t square to a four-sided Coons Patch". Colour |

values shall be given for the four corners of the patch, in the same order as the control points
| corresponding to the corners. Thus, | c1 is the colour at coordinates ( | x1, y1), c2 at (x4, y4), c3 at (x7, y7), and |

c4 at (x10, y10), as shown in the figure.

Figure 45 — Colour values and edge flags in Coons Patch meshes


## Page 264

"Figure 45 — Colour values and edge flags in Coons Patch meshes" also shows how non-zero values of
the edge flag (f = 1, 2, or 3) connect a new patch to one of the edges of the previous patch. In this case,
some of the previous patch’s control points serve implicitly as control points for the new patch as well
(see "Figure 46 — Edge connections in a Coons Patch Mesh"), and therefore shall not be explicitly
repeated in the data stream. "Table 84 — Data Values in a Coons Patch Mesh" summarises the required
data values for various values of the edge flag.

Figure 46 — Edge connections in a Coons Patch Mesh
If the shading dictionary contains a Function entry, the colour data for each corner of a patch shall be
specified by a single parametric value t rather than by n separate colour components c1… cn. All linear
interpolation within the mesh shall be done using the t values. After interpolation, the results shall be
passed to the function(s) specified in the Function entry to determine the colour at each point.

Table 84 — Data Values in a Coons Patch Mesh

| Edge Flag | Next Set of Data Values |

| f = 0 | 𝑥1 𝑦1 𝑥2 𝑦2 𝑥3 𝑦3 𝑥4 𝑦4 𝑥5 𝑦5 𝑥6 𝑦6 𝑥7 𝑦7 𝑥8 𝑦8 𝑥9 𝑦9 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 |

c1 c2 c3 c4
New patch; no implicit values


## Page 265

| Edge Flag | Next Set of Data Values |

| f = 1 | 𝑥5 𝑦5 𝑥6 𝑦6 𝑥7 𝑦7 𝑥8 𝑦8 𝑥9 𝑦9 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 c3 c4 Implicit values: |

| (𝑥1, 𝑦1)= | (𝑥4, 𝑦4) previous | c1 = c2 previous |
| --- | --- | --- |
| (𝑥2, 𝑦2)= | (𝑥5, 𝑦5) previous | c2 = c3 previous |
| (𝑥3, 𝑦3)= | (𝑥6, 𝑦6) previous |  |
| (𝑥4, 𝑦4)= | (𝑥7, 𝑦7) previous |  |

| f = 2 | 𝑥5 𝑦5 𝑥6 𝑦6 𝑥7 𝑦7 𝑥8 𝑦8 𝑥9 𝑦9 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 c3 c4 Implicit values: |

| (𝑥1, 𝑦1)= | (𝑥7, 𝑦7) previous | c1 = c3 previous |
| --- | --- | --- |
| (𝑥2, 𝑦2)= | (𝑥8, 𝑦8) previous | c2 = c4 previous |
| (𝑥3, 𝑦3)= | (𝑥9, 𝑦9) previous |  |
| (𝑥4, 𝑦4)= | (𝑥10, 𝑦10) previous |  |

| f = 3 | 𝑥5 𝑦5 𝑥6 𝑦6 𝑥7 𝑦7 𝑥8 𝑦8 𝑥9 𝑦9 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 c3 c4 Implicit values: |  |
| --- | --- | --- |
| (𝑥1, 𝑦1)= | (𝑥10, 𝑦10) previous | c1 = c4 previous |
| (𝑥2, 𝑦2)= | (𝑥11, 𝑦11) previous | c2 = c1 previous |
| (𝑥3, 𝑦3)= | (𝑥12, 𝑦12) previous |  |
| (𝑥4, 𝑦4)= | (𝑥1, 𝑦1) previous |  |

8.7.4.5.8 Type 7 (tensor-product patch mesh) shadings

Type 7 (tensor-product patch mesh) shadings are identical to Type 6, except that they are based on a
bicubic tensor-product patch defined by 16 control points instead of the 12 control points that define a
Coons patch. The shading dictionaries representing the two patch types differ only in the value of the
ShadingType entry and in the number of control points specified for each patch in the data stream.

NOTE         Although the Coons patch is more concise and easier to use, the tensor-product patch affords
greater control over colour mapping.
Like the Coons patch mapping, the tensor-product patch mapping is controlled by the location and
shape of four cubic Bézier curves marking the boundaries of the patch. However, the tensor-product
patch has four additional, "internal" control points to adjust the mapping. The 16 control points can be
arranged in a 4-by-4 array indexed by row and column, as follows (see "Figure 47 — Control points in
a tensor-product patch"):


## Page 266

| p03 | p13 | p23 | p33 |

| p02 | p12 | p22 | p32 |
| --- | --- | --- | --- |
| p01 | p11 | p21 | p31 |
| p00 | p10 | p20 | p30 |

Figure 47 — Control points in a tensor-product patch
As in a Coons patch mesh, the geometry of the tensor-product patch is described by a surface defined
over a pair of parametric variables, u and v, which vary horizontally and vertically over the unit square.
The surface is defined by the equation

| 3 | 3 |  |  |  |
| --- | --- | --- | --- | --- |
| S(u,  v)= | ∑ | ∑ | pij ×  Bi(u)× | Bj(v) i=0 j=0 |

where pij is the control point in column i and row j of the tensor, and Bi and Bj are the Bernstein
polynomials
B0(t)=     (1 −  t)3
| B1(t)= | 3t× | (1 −  t)2 |
| --- | --- | --- |
| B2(t)= | 3t2  × (1  −  t) |  |
| B3(t)= | t3 |  |

Since each point pij is actually a pair of coordinates (xij, yij), the surface can also be expressed as

| 3 | 3 |  |  |
| --- | --- | --- | --- |
| 𝑥(u,v)= | ∑∑ | 𝑥ij ×  Bi(u)× | Bj(v) i=0 j=0 |
| 3 | 3 |  |  |
| 𝑦(u,  v)= | ∑∑ | 𝑦ij ×  Bi(u)× | Bj(v) i=0 j=0 |

The geometry of the tensor-product patch can be visualized in terms of a cubic Bézier curve moving
from the bottom boundary of the patch to the top. At the bottom and top, the control points of this
curve coincide with those of the patch’s bottom (p00…p30) and top (p03…p33) boundary curves,


## Page 267

respectively. As the curve moves from the bottom edge of the patch to the top, each of its four control
points follows a trajectory that is in turn a cubic Bézier curve defined by the four control points in the
corresponding column of the array. That is, the starting point of the moving curve follows the
trajectory defined by control points p00…p03, the trajectory of the ending point is defined by points
p30…p33, and those of the two intermediate control points by p10…p13 and p20…p23. Equivalently, the patch
can be considered to be traced by a cubic Bézier curve moving from the left edge to the right, with its
control points following the trajectories defined by the rows of the coordinate array instead of the
columns.

| The Coons patch (Type 6) is actually a special case of the tensor | -product patch (Type 7) in which the |
| --- | --- |
| four internal control points ( | p11, p12, p21, p22) are implicitly defined by the boundary curves. The values |

of the internal control points are given by these equations

| 𝑝 | =  1(−4 | ×  𝑝 | +  6 ×  (𝑝 | +  𝑝 | )−  2  × (𝑝 | + 𝑝 | )+  3  × (𝑝 | +  𝑝 | )− | 1 ×  𝑝 | ) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 11 | 9 | 00 | 01 | 10 | 03 | 30 | 31 | 13 | 33 |  |  |
| 𝑝 | =  1(−4 | ×  𝑝 | +  6 ×  (𝑝 | +  𝑝 | )−  2  × (𝑝 | + 𝑝 | )+  3  × (𝑝 | +  𝑝 | )− | 1 ×  𝑝 | ) |
| 12 | 9 | 03 | 02 | 13 | 00 | 33 | 32 | 10 | 30 |  |  |
| 𝑝 | =  1(−4 | ×  𝑝 | +  6 ×  (𝑝 | +  𝑝 | )− | 2 ×  (𝑝 | + 𝑝 | )+  3  × (𝑝 | + 𝑝 | )−  1  × 𝑝 | ) |
| 21 | 9 | 30 | 31 | 20 | 33 | 00 | 01 | 23 | 03 |  |  |
| 𝑝 | =  1(−4 | ×  𝑝 | +  6 ×  (𝑝 | +  𝑝 | )− | 2 ×  (𝑝 | + 𝑝 | )+  3  × (𝑝 | + 𝑝 | )−  1  × 𝑝 | ) |
| 22 | 9 | 33 | 32 | 23 | 30 | 03 | 02 | 20 | 00 |  |  |

In the more general tensor-product patch, the values of these four points are unrestricted.

The coordinates of the control points in a tensor-product patch shall be specified in the shading’s data
stream in the following order:

| 4 | 5 | 6 | 7 |

| 3 | 14 | 15 | 8 |
| --- | --- | --- | --- |
| 2 | 13 | 16 | 9 |
| 1 | 12 | 11 | 10 |

All control point coordinates shall be expressed in the shading’s target coordinate space. These shall be
followed by the colour values for the four corners of the patch, in the same order as the corners
themselves. If the patch’s edge flag f is 0, all 16 control points and four corner colours shall be explicitly
specified in the data stream. If f is 1, 2, or 3, the control points and colours for the patch’s shared edge
are implicitly understood to be the same as those along the specified edge of the previous patch and
shall not be repeated in the data stream. "Table 85 — Data values in a tensor-product patch mesh"
summarises the data values for various values of the edge flag f, expressed in terms of the row and
| column indices used in "Figure 47 — Control points in a tensor | -product patch". See 8.7.4.5.5, "Type 4 |

(free-form Gouraud-shaded triangle mesh) shadings" for further details on the format of the data.


## Page 268

Table 85 — Data values in a tensor-product patch mesh

| Edge Flag | Next Set of Data Values |

| 𝑓 =  0 | 𝑥00 𝑦00 𝑥01 𝑦01 𝑥02 𝑦02 𝑥03 𝑦03 𝑥13 𝑦13𝑥23𝑦23 𝑥33 𝑦33 𝑥32 𝑦32 𝑥31𝑦31 𝑥30𝑦30 𝑥20 𝑦20 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 𝑥22 𝑦22 𝑥21 𝑦21 c00 c03 c33 c30 |

New patch; no implicit values

| 𝑓 =  1 | 𝑥13 𝑦13 𝑥23 𝑦23 𝑥33 𝑦33 𝑥32 𝑦32 𝑥31 𝑦31 𝑥30 𝑦30 𝑥20 𝑦20 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 𝑥22 𝑦22 𝑥21 𝑦21 c33 c30 Implicit values: |  |
| --- | --- | --- |
| (𝑥00, 𝑦00)= | (𝑥03,𝑦03) previous | 𝑐00 =  𝑐03 previous |
| (𝑥01, 𝑦01)= | (𝑥13,𝑦13) previous | 𝑐03 =  𝑐33 previous |
| (𝑥02, 𝑦02)= | (𝑥23,𝑦23) previous |  |
| (𝑥03, 𝑦03)= | (𝑥33,𝑦33) previous |  |

| 𝑓 =  2 | 𝑥13 𝑦13 𝑥23 𝑦23 𝑥33 𝑦33 𝑥32 𝑦32 𝑥31 𝑦31 𝑥30 𝑦30 𝑥20 𝑦20 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 𝑥22 𝑦22 𝑥21 𝑦21 𝑐33 𝑐30 Implicit values: |  |
| --- | --- | --- |
| (𝑥00, 𝑦00)= | (𝑥33,𝑦33) previous | 𝑐00 =  𝑐33 previous |
| (𝑥01, 𝑦01)= | (𝑥32,𝑦32) previous | 𝑐03 =  𝑐30 previous |
| (𝑥02, 𝑦02)= | (𝑥31,𝑦31) previous |  |
| (𝑥03, 𝑦03)= | (𝑥30,𝑦30) previous |  |

| 𝑓 =  3 | 𝑥13 𝑦13 𝑥23 𝑦23 𝑥33 𝑦33 𝑥32 𝑦32 𝑥31 𝑦31 𝑥30 𝑦30 𝑥20 𝑦20 𝑥10 𝑦10 𝑥11 𝑦11 𝑥12 𝑦12 𝑥22 𝑦22 𝑥21 𝑦21 𝑐33 𝑐30 Implicit values: |  |
| --- | --- | --- |
| (𝑥00, 𝑦00)= | (𝑥30,𝑦30) previous | 𝑐00 =  𝑐30 previous |
| (𝑥01, 𝑦01)= | (𝑥20,𝑦20) previous | 𝑐03 =  𝑐00 previous |
| (𝑥02, 𝑦02)= | (𝑥10,𝑦10) previous |  |
| (𝑥03, 𝑦03)= | (𝑥00,𝑦00) previous |  |

8.8 External objects

8.8.1 General

An external object (commonly called an XObject) is a graphics object whose contents are defined by a
self-contained stream, separate from the content stream in which it is used. There are two types of
external objects:

| • | An image XObject (8.9.5, "Image dictionaries") represents a sampled visual image such as a |

photograph.
| • | A form XObject (8.10, "Form XObjects") is a self-contained description of an arbitrary sequence of |

graphics objects.
Two further categories of external objects, group XObjects and reference XObjects (both PDF 1.4), are


## Page 269

actually specialised types of form XObjects with additional properties. See 8.10.3, "Group XObjects" and
8.10.4, "Reference XObjects" for additional information.

Any XObject can be painted as part of another content stream by means of the Do operator (see "Table
86 — XObject operator"). This operator applies to any type of XObject — image or form. The syntax is
the same in all cases, although details of the operator’s behaviour differ depending on the type.

Table 86 — XObject operator

| Operands | Operator | Description |

| name | Do | Paint the specified XObject. The operand name shall appear as a key in the XObject subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). The associated value shall be a stream whose Type entry, if present, is XObject. The effect of Do depends on the value of the XObject’s Subtype entry, which may be Image (see 8.9.5, "Image dictionaries") or Form (see 8.10, "Form XObjects"). |

Annex J, "XObject comparison", contains one method by which XObjects can be compared for
equivalency.

8.9 Images

8.9.1 General

PDF’s painting operators include general facilities for dealing with sampled images. A sampled image
(or just image for short) is a rectangular array of sample values, each representing a colour. The image
may approximate the appearance of some natural scene obtained through an input scanner or a video
camera, or it may be generated synthetically. “Figure 48 — Typical sampled image” shows a typical
sampled image.

Figure 48 — Typical sampled image
An image is defined by a sequence of samples obtained by scanning the image array in row or column
order. Each sample in the array consists of as many colour components as are needed for the colour
space in which they are specified — for example, one component for DeviceGray, three for
DeviceRGB, four for DeviceCMYK, or whatever number is required by a particular DeviceN space.
Each component is a 1-, 2-, 4-, 8-, or (PDF 1.5) 16-bit integer, permitting the representation of 2, 4, 16,


## Page 270

256, or (PDF 1.5) 65536 distinct values for each component. Other component sizes can be
accommodated when a JPXDecode filter is used; see 7.4.9, "JPXDecode filter".

PDF provides two means for specifying images:

| • | An image XObject (described in 8.9.5, "Image dictionaries") is a stream object whose dictionary |

specifies attributes of the image and whose data contains the image samples. Like all external
objects, it is painted on the page by invoking the Do operator in a content stream (see 8.8,
| "External objects"). Image XObjects have other uses as well, such as for alternate images ( | see |
| --- | --- |
| 8.9.5.4, "Alternate images"), image masks (8.9.6, "Masked images"), and thumbnail images ( | 12.3.4, |

"Thumbnail images").
| • | An inline image is a small image that is completely defined — both attributes and data — directly |

inline within a content stream. The kinds of images that can be represented in this way are
limited; see 8.9.7, "Inline images" for details.

8.9.2 Image parameters

The properties of an image — resolution, orientation, scanning order, and so forth — are entirely
| independent of the characteristics of the raster output device o | n which the image is to be rendered. A |

PDF processor usually renders images by a sampling technique that attempts to approximate the
colour values of the source as accurately as possible. The actual accuracy achieved depends on the
resolution and other properties of the output device.

To paint an image, four interrelated items shall be specified:

| • | The format of the image: number of columns (width), number of rows (height), number of colour |

components per sample, and number of bits per colour component
| • | The sample data constituting the image’s visual content |
| --- | --- |
| • | The correspondence between coordinates in user space and those in the image’s own internal |

coordinate space, defining the region of user space that will receive the image
| • | The mapping from colour component values in the image data to component values in the image’s |

colour space
All of these items shall be specified explicitly or implicitly by an image XObject or an inline image.

NOTE         For convenience, the following subclauses refer consistently to the object defining an image as
an image dictionary. Although this term properly refers only to the dictionary portion of the
stream object representing an image XObject, it can be understood to apply equally to the
stream’s data portion or to the parameters and data of an inline image.

8.9.3 Sample representation

The source format for an image shall be described by four parameters:

| • | The width of the image in samples |
| --- | --- |
| • | The height of the image in samples |
| • | The number of colour components per sample |
| • | The number of bits per colour component |

The image dictionary shall specify the width, height, and number of bits per component explicitly. The
number of colour components shall be inferred from the colour space specified in the dictionary.


## Page 271

NOTE         For images using the JPXDecode filter (see 7.4.9, "JPXDecode filter"), the number of bits per
component is determined from the image data and is not specified in the image dictionary. The
colour space does not have to be specified in the image dictionary.
Sample data shall be represented as a stream of bytes, interpreted as 8-bit unsigned integers in the
range 0 to 255. The bytes constitute a continuous bit stream, with the high-order bit of each byte first.
This bit stream, in turn, is divided into units of n bits each, where n is the number of bits per
component. Each unit encodes a colour component value, given with high-order bit first; units of 16
bits shall be given with the most significant byte first. Byte boundaries shall be ignored, except that
each row of sample data shall begin on a byte boundary. If the number of data bits per row is not a
multiple of 8, the end of the row is padded with extra bits to fill out the last byte. A PDF processor shall
ignore these padding bits.

Each n-bit unit within the bit stream shall be interpreted as an unsigned integer in the range 0 to 2n- 1,
with the high-order bit first. The image dictionary’s Decode entry maps this integer to a colour
component value, equivalent to what could be used with colour operators such as sc or g. Colour
components shall be interleaved sample by sample; for example, in a three-component RGB image, the
red, green, and blue components for one sample are followed by the red, green, and blue components
for the next.

If the image dictionary's ImageMask entry is false or absent, the colour samples in an image shall be
interpreted according to the colour space specified in the image dictionary (see 8.6, "Colour spaces"),
without reference to the colour parameters in the graphics state. However, if the image dictionary’s
ImageMask entry is true, the sample data shall be interpreted as a stencil mask for applying the
graphics state’s nonstroking colour parameters (see 8.9.6.2, "Stencil masking").

8.9.4 Image coordinate system

Each image has its own internal coordinate system, or image space. The image occupies a rectangle in
image space w units wide and h units high, where w and h are the width and height of the image in
samples. Each sample occupies one square unit. The coordinate origin (0, 0) is at the upper-left corner
of the image, with coordinates ranging from 0 to w horizontally and 0 to h vertically.

The image’s sample data are ordered by row, with the horizontal coordinate varying most rapidly. This
is shown in "Figure 49 — Source image coordinate system", where the numbers inside the squares
indicate the order of the samples, counting from 0. The upper-left corner of the first sample is at
coordinates (0, 0), the second at (1, 0), and so on through the last sample of the first row, whose upperleft corner is at (w - 1, 0) and whose upper-right corner is at (w, 0). The next samples after that are at
coordinates (0, 1), (1, 1), and so on to the final sample of the image, whose upper-left corner is at (w - 1,
h - 1) and whose lower-right corner is at (w, h).

NOTE         The image coordinate system and scanning order imposed by PDF do not preclude using
different conventions in the actual image. Coordinate transformations can be used to map from
other conventions to the PDF convention.
The correspondence between image space and user space is constant: the unit square of user space,
bounded by user coordinates (0, 0) and (1, 1), corresponds to the boundary of the image in image
space (see "Figure 50 — Mapping the source image"). Following the normal convention for user space,
| the coordinate (0, 0) is at the lower | -left corner of this square, corresponding to coordinates (0, | h) in |
| --- | --- | --- |
| image space. The implicit transformation from image space to user space, if specified explicitly, wo | uld |  |


## Page 272

be described by the matrix [1⁄w 0 0 -1⁄h 0 1].

Figure 49 — Source image coordinate system

Figure 50 — Mapping the source image
An image can be placed on the output page in any position, orientation, and size by using the cm
operator to modify the current transformation matrix (CTM) so as to map the unit square of user space
to the rectangle or parallelogram in which the image shall be painted. Typically, this is done within a
pair of q and Q operators to isolate the effect of the transformation, which can include translation,
rotation, reflection, and skew (see 8.3, "Coordinate systems").

EXAMPLE             If the XObject subdictionary of the current resource dictionary defines the name Image1 to denote an image
XObject, the code shown in this example paints the image in a rectangle whose lower-left corner is at
coordinates (100, 200), that is rotated 45 degrees counter clockwise, and that is 150 units wide and 80 units
high.

| q | %Save graphics state |
| --- | --- |
| 1 0 0 1 100 200 cm | %Translate |
| 0. 7071 0.7071 -0.7071 0.7071 0 0 cm | %Rotate |
| 150 0 0 80 0 0 cm | %Scale |
| /Image1 Do | %Paint image |
| Q | %Restore graphics state |


## Page 273

As discussed in 8.3.4, "Transformation matrices", these three transformations could be combined into one.
Of course, if the aspect ratio (width to height) of the original image in this example is different from 150:80,
the result will be distorted.

8.9.5 Image dictionaries

8.9.5.1 General

An image dictionary — that is, the dictionary portion of a stream representing an image XObject —
may contain the entries listed in "Table 87 — Additional entries specific to an image dictionary" in
addition to the usual entries common to all streams (see "Table 5 — Entries common to all stream
dictionaries"). There are many relationships among these entries, and the current colour space may
limit the choices for some of them. Attempting to use an image dictionary whose entries are
inconsistent with each other or with the current colour space shall cause an error.

The entries described here are appropriate for a base image — one that is invoked directly with the Do
operator.

NOTE          Some of the entries are not defined for images used in other ways, such as for alternate images
(see 8.9.5.4, "Alternate images"), image masks (see 8.9.6, "Masked images"), or thumbnail images
(see 12.3.4, "Thumbnail images").

Table 87 — Additional entries specific to an image dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be XObject for an image XObject. |

| Subtype | name | (Optional when used only as a thumbnail image, required otherwise) The type of XObject that this dictionary describes; shall be Image for an image XObject. |

NOTE      The conditions for when the Subtype key is required were clarified in
this document (2020).

| Width | integer | (Required) The width of the image, in samples. |

| Height | integer | (Required) The height of the image, in samples. |

| ColorSpace | name or | (Required for images, except those that use the JPXDecode filter; not |
| --- | --- | --- |
| array | permitted for image masks) The colour space in which image samples shall be specified; it can be any type of colour space except Pattern. If the image uses the JPXDecode filter, this entry may be present: |  |
| • | If ColorSpace is present, any colour space specifications in the JPEG 2000 data shall be ignored. |  |
| • | If ColorSpace is absent, the colour space specifications in the JPEG | 2000 data shall be used. The Decode array shall also be ignored unless ImageMask is true. |


## Page 274

| Key | Type | Value |

| BitsPerComponent | integer | (Required except for image masks and images that use the JPXDecode filter) The number of bits used to represent each colour component. Only a single value shall be specified; the number of bits shall be the same for all colour components. The value shall be 1, 2, 4, 8, or (from PDF 1.5) 16. If ImageMask is true, this entry is optional, but if specified, its value shall be 1. If the image stream uses a filter, the value of BitsPerComponent shall be consistent with the size of the data samples that the filter delivers. In particular, a CCITTFaxDecode or JBIG2Decode filter shall always deliver 1-bit samples, a RunLengthDecode or DCTDecode filter shall always deliver 8-bit samples, and an LZWDecode or FlateDecode filter shall deliver samples of a specified size if a predictor function is used. If the image stream uses the JPXDecode filter, this entry is optional and shall be ignored if present. The bit depth is determined by the PDF processor in the process of decoding the JPEG 2000 image. |

| Intent | name | (Optional; PDF 1.1) The name of a colour rendering intent that shall be used in rendering any image that is not an image mask (see 8.6.5.8, "Rendering intents"). This value is ignored if ImageMask is true. Default value: the current rendering intent in the graphics state. |

| ImageMask | boolean | (Optional) A flag indicating whether the image shall be treated as an image mask (see 8.9.6, "Masked images"). If this flag is true, the value of BitsPerComponent, if present, shall be 1 and Mask and ColorSpace shall not be specified; unmasked areas shall be painted using the current nonstroking colour. Default value: false. |

| Mask | stream or | (Optional; shall not be present for image masks; PDF 1.3) An image XObject |
| --- | --- | --- |
| array | defining an image mask to be applied to this image (see 8.9.6.3, "Explicit masking"), or an array specifying a range of colours to be applied to it as a colour key mask (see 8.9.6.4, "Colour key masking"). If ImageMask is true, this entry shall not be present. |  |

| Decode | array | (Optional) An array of numbers describing how to map image samples into the range of values appropriate for the image’s colour space (see 8.9.5.2, "Decode arrays"). If ImageMask is true, the array shall be either [0 1] or [1 0]; otherwise, its length shall be twice the number of colour components required by ColorSpace. If the image uses the JPXDecode filter and if ColorSpace is absent, the Decode array shall be ignored unless ImageMask is true. Default value: see "Table 88 — Default decode arrays". |

| Interpolate | boolean | (Optional) A flag indicating whether image interpolation should be performed by a PDF processor (see 8.9.5.3, "Image interpolation"). Default value: false. |

| Alternates | array | (Optional; PDF 1.3) An array of alternate image dictionaries for this image (see 8.9.5.4, "Alternate images"). This entry shall not be present in an image XObject that is itself an alternate image. |


## Page 275

| Key | Type | Value |

| SMask | stream | (Optional; PDF 1.4) A subsidiary image XObject defining a soft-mask image (see 11.6.5.2, "Soft-mask images") that shall be used as a source of mask shape or mask opacity values in the transparent imaging model. The alpha source parameter in the graphics state determines whether the mask values shall be interpreted as shape or opacity. If present, this entry shall override the current soft mask in the graphics state, as well as the image’s Mask entry, if any. However, the other transparency-related graphics state parameters — blend mode and alpha constant — shall remain in effect. If SMask is absent and SMaskInData has value 0, the image shall have no associated soft mask (although the current soft mask in the graphics state may still apply). |

NOTE 1    Interactions between SMask, SMaskInData and the current soft mask in
| the graphics state are set out in clause | 11.6.4.3, "Mask shape and opacity". |

| SMaskInData | integer | (Optional for images that use the | JPXDecode filter, meaningless otherwise; PDF 1.5) A code specifying how soft-mask information (see 11.6.5.2, "Soft- mask images") encoded with image samples shall be used: |

0 If present, encoded soft-mask image information shall be ignored.

1 The image’s data stream includes encoded soft-mask values. A PDF
processor shall create a soft-mask image from the information to be
used as a source of mask shape or mask opacity in the transparency
imaging model.

2 The image’s data stream includes colour channels that have been
premultiplied with an opacity channel; the image data also includes
the opacity channel. A PDF processor shall create a soft-mask image
from the opacity channel information to be used as a source of mask
shape or mask opacity in the transparency model.
If this entry has a non-zero value, SMask shall not be specified. See also
7.4.9, "JPXDecode filter".
NOTE 2    Interactions between SMask, SMaskInData and the current soft mask in
| the graphics state are set out in clause | 11.6.4.3, "Mask shape and opacity". Default value: 0. |

| Name | name | (Required in PDF 1.0; optional otherwise; deprecated in PDF 2.0) The name by which this image XObject is referenced in the XObject subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). |

| StructParent | integer | (Required if the image is a structural content item; PDF 1.3) The integer key of the image’s entry in the structural parent tree (see 14.7.5.4, "Finding structure elements from content items"). |

| ID | byte string | (Optional; PDF 1.3; indirect reference preferred) The digital identifier of the image’s parent Web Capture content set (see 14.10.6, "Object attributes related to web capture"). |

| OPI | dictionary | (Optional; PDF 1.2; deprecated in PDF 2.0) An OPI version dictionary for the image; see 14.11.7, "Open prepress interface (OPI)". If ImageMask is true, this entry shall be ignored. |

| Metadata | stream | (Optional; PDF 1.4) A metadata stream containing metadata for the image (see 14.3.2, "Metadata streams"). |


## Page 276

| Key | Type | Value |

| OC | dictionary | (Optional; PDF 1.5) An optional content group or optional content membership dictionary (see 8.11, "Optional content"), specifying the optional content properties for this image XObject. Before the image is processed by a PDF processor, its visibility shall be determined based on this entry. If it is determined to be invisible, the entire image shall be skipped, as if there were no Do operator to invoke it. |

| AF | array of | (Optional; PDF 2.0) An array of one or more file specification dictionaries |
| --- | --- | --- |
| dictionaries | (7.11.3, "File specification dictionaries") which denote the associated files for this image XObject. See 14.13, "Associated files" and 14.13.7, "Associated files linked to XObjects" for more details. |  |

| Measure | dictionary | (Optional; PDF 2.0) A measure dictionary (see "Table 266 — Entries in a measure dictionary") that specifies the scale and units which shall apply to the image. |

| PtData | dictionary | (Optional; PDF 2.0) A point data dictionary (see "Table 272 — Entries in a point data dictionary") that specifies the extended geospatial data that shall apply to the image. |

EXAMPLE             This example defines an image 256 samples wide by 256 high, with 8 bits per sample in the DeviceGray
| colour space. It paints the image on a page with its lower | -left corner positioned at coordinates (45, 140) in current user space and scaled to a width and height of 132 user space units. |

| 20 0 obj | %Page object <</Type /Page /Parent 1 0 R /Resources 21 0 R /MediaBox [0 0 612 792] /Contents 23 0 R >> endobj |

| 21 0 obj | %Resource dictionary for page <</XObject <</Im1 22 0 R>> >> endobj |

| 22 0 obj | %Image XObject <</Type /XObject /Subtype /Image /Width 256 /Height 256 /ColorSpace /DeviceGray /BitsPerComponent 8 /Length 83183 /Filter /ASCII85Decode >> stream 9LhZI9h\GY9i+bb;,p:e;G9SP92/)X9MJ>^:f14d;,U(X8P;cO;G9e];c$=k9Mn\] … Image data representing 65,536 samples … 8P;cO;G9e];c$=k9Mn\]~> endstream endobj |

| 23 0 obj | %Contents of page <</Length 56>> stream |
| --- | --- |
| q | %Save graphics state |
| 132 0 0 132 45 140 cm | %Translate to (45,140) and scale by 132 |


## Page 277

| /Im1 Do | %Paint image |
| --- | --- |
| Q | %Restore graphics state |

endstream
endobj

8.9.5.2 Decode arrays

Each image's colour component data is initially decomposed into integers in the domain 0 to 2n-1,
where n is the bit depth of the colour component. This bit depth is specified as the value of the image
dictionary's BitsPerComponent entry or, when the image uses the JPXDecode filter, is defined in the
JPEG 2000 data and can have different values per colour component. An image’s Decode array
specifies a linear mapping of each integer component value to a number that would be appropriate as a
component value in the image’s colour space.

Each pair of numbers in a Decode array specifies the lower and upper values to which the domain of
sample values in the image is mapped. A Decode array shall contain one pair of numbers for each
component in the colour space specified by the image’s ColorSpace entry. The mapping for each colour
component, by a PDF processor shall be a linear transformation; that is, it shall use the following
formula for linear interpolation:

| 𝑦  =  Interpolate(𝑥, | 𝑥min,𝑥max, | 𝑦min, 𝑦max) |  |
| --- | --- | --- | --- |
| =  𝑦 | +  ((𝑥  −  𝑥 | )×  𝑦max | − 𝑦min) |
| min | min | 𝑥max | −  𝑥min |

This formula is used to convert a value x between xmin and xmax to a corresponding value y between ymin
and ymax, projecting along the line defined by the points (xmin, ymin ) and (xmax, ymax ).

NOTE 1       While this formula applies to values outside the domain xmin to xmax and does not require that xmin <
xmax, note that interpolation used for colour conversion, such as the Decode array, does require
that xmin < xmax and clips x values to this domain so that x = xmin for all x ≤ xmin, and x = xmax for all x ≥
xmax.
For a Decode array of the form [Dmin Dmax], this can be written as
| 𝑦  =  Interpolate(𝑥, | 0, 2𝑛  −  1, 𝐷min,  𝐷max) |  |
| --- | --- | --- |
| =  𝐷 | +  (𝑥  ×  𝐷max | − 𝐷min) |
| min | 2𝑛  −  1 |  |

where

n shall be the bit depth of the corresponding colour component
x shall be the input value, in the domain 0 to 2n - 1

Dmin and Dmax shall be the values specified in the Decode array

y is the output value, which shall be interpreted in the image’s colour space
Samples with a value of 0 shall be mapped to Dmin, those with a value of 2n - 1 shall be mapped to Dmax,
and those with intermediate values shall be mapped linearly between Dmin and Dmax. "Table 88 — Default
decode arrays" lists the default Decode arrays which shall be used with the various colour spaces by a
PDF processor.


## Page 278

NOTE 2       For most colour spaces, the Decode arrays listed in the table map into the full range of allowed
component values. For an Indexed colour space, the default Decode array ensures that
component values that index a colour table are passed through unchanged.

Table 88 — Default decode arrays

Colour Space         Decode Array

DeviceGray           [0.0 1.0]

| DeviceRGB | [0.0 1.0 0.0 1.0 0.0 1.0] |

| DeviceCMYK | [0.0 1.0 0.0 1.0 0.0 1.0 0.0 1.0] |

CalGray              [0.0 1.0]

| CalRGB | [0.0 1.0 0.0 1.0 0.0 1.0] |

| Lab | [0 100 amin amax bmin bmax] where amin, amax, bmin, and bmax correspond to the values in the Range array of the image’s colour space |

| ICCBased | Same as the value of Range in the ICC profile of the image’s colour space |

| Indexed | [0 N], where N = 2n – 1 |

| Pattern | (Not permitted with images) |

Separation           [0.0 1.0]

| DeviceN | [0.0 1.0 0.0 1.0…0.0 1.0] (one pair of elements for each colour component) |

NOTE 3       PDF supports mappings that invert sample colour intensities by specifying a Dmin value greater
than Dmax. For example, if the image’s colour space is DeviceGray and the Decode array is [1.0
0.0], an input value of 0 is mapped to 1.0 (white); an input value of 2n - 1 is mapped to 0.0
(black).
The Dmin and Dmax parameters for a colour component need not fall within the range of values allowed for
that component.

NOTE 4       For instance, if an application uses 6-bit numbers as its native image sample format, it can
represent those samples in PDF in 8-bit form, setting the two unused high-order bits of each
sample to 0. The image dictionary can then specify a Decode array of [0.00000 4.04762], which
maps input values from 0 to 63 into the range 0.0 to 1.0 (4.04762 being approximately equal to
255 ÷ 63).
If an output value is not permitted for a component, it shall be adjusted to the nearest allowed value.

8.9.5.3 Image interpolation

Image interpolation is an attempt to produce a smooth transition between adjacent sample values
when rendering an image whose resolution is significantly lower than that of the output device. Setting
the value of the Interpolate entry in an image dictionary to true, is a way for a PDF to declare to a PDF
processor that a specific image might render better if interpolation is used for this particular image.
However, this is only a hint, and a PDF processor may ignore it.


## Page 279

8.9.5.4 Alternate images

Alternate images (PDF 1.3) provide a straightforward and backward-compatible way to include
multiple versions of an image in a PDF file for different purposes. These variant representations of the
image may differ, for example, in resolution or in colour space. The primary goal is to reduce the need
to maintain separate versions of a PDF document for low-resolution on-screen viewing and highresolution printing.

A base image (that is, the image XObject referred to in a resource dictionary) may contain an
Alternates entry. The value of this entry shall be an array of alternate image dictionaries specifying
variant representations of the base image. Each alternate image dictionary shall contain an image
XObject for one variant and shall specify its properties. "Table 89 — Entries in an alternate image
dictionary" shows the contents of an alternate image dictionary.

Table 89 — Entries in an alternate image dictionary

| Key | Type | Value |

| Image | stream | (Required) The image XObject for the alternate image. |

| DefaultForPrinting | boolean | (Optional) A flag indicating whether this alternate image is the default version to be used for printing according to the algorithm described below. At most one alternate for a given base image shall be so designated. Default value: false. |

| OC | dictionary | (Optional; PDF 1.5) An optional content group (see 8.11.2, "Optional content groups") or optional content membership dictionary (see 8.11.2.2, "Optional content membership dictionaries") that facilitates the selection of which alternate image to use. |

In PDF 1.5, optional content (see 8.11, "Optional content") may be used to facilitate selection between
alternate images. The following algorithm shall be used to determine which image, if any, shall be
rendered:

NOTE          (2020) The following algorithm was changed in this document to reflect that OC processing has
precedence over DefaultForPrinting functionality, and situations where no image is to be
rendered.
| a) | If the base image contains an | OC key then | DefaultForPrinting | shall be  ignored on all | Alternates | entries. |
| --- | --- | --- | --- | --- | --- | --- |
| b) | If the base image contains an | OC entry that specifies that the base image is visible, then the base image |  |  |  |  |

shall be rendered.
| c) | If the base image contains an | OC entry that specifies that the base image is not visible, then t | he list of |  |
| --- | --- | --- | --- | --- |
| alternate | image dictionarie | s specified by the base image | Alternates | entry shall be examined in order, |
| and the first entry not containing an | OC key, or containing an | OC entry specifying that the alternate |  |  |
| image should be visible, shall be selecte | d. Further, if | this selected alternate image has an | OC entry, then |  |

that OC entry shall also be processed to determine if the alternate image shall be rendered or not. If none
| of the alternate | image dictionarie | s have an | OC key, or none of the | alternate imag | e dictionaries | with an |
| --- | --- | --- | --- | --- | --- | --- |
| OC entry specify that that alternate image is visible, then nothing shall be shown. | DefaultForPrinting |  |  |  |  |  |
| shall be ignored on all | Alternates | entries. |  |  |  |  |
| d) | If the base image does not contain an | OC key and the PDF is being printed then the fi | rst entry in the |  |  |  |
| Alternates | array of the base image | that has | DefaultForPrinting | set to true shall be selected. | Further, i  f |  |


## Page 280

| this selected alternate image has an OC entry, then th | at OC entry shall also be processed to determine if |  |  |  |
| --- | --- | --- | --- | --- |
| the alternate image shall b | e printed or not. If no | alternate image dictionary | in the Alternates | array has |
| DefaultForPrinting | set to true, then the base image shall be printed. |  |  |  |

NOTE         Alternate images cannot also have an           Alternates    key as described in     "Table 87 — Additional
entries specific to an image dictionary".

EXAMPLE            The following shows an image with a single alternate. The base image is a grayscale image, and the alternate
is a high-resolution RGB image stored on a Web server.

| 10 0 obj | %Image XObject <</Type /XObject /Subtype /Image /Width 100 /Height 200 /ColorSpace /DeviceGray /BitsPerComponent 8 /Alternates 15 0 R /Length 2167 /Filter /DCTDecode >> |

stream
… Image data …
endstream
endobj

| 15 0 obj | %Alternate images array [<</Image 16 0 R /DefaultForPrinting true >> ] |

endobj

| 16 0 obj | %Alternate image <</Type /XObject /Subtype /Image /Width 1000 /Height 2000 /ColorSpace /DeviceRGB /BitsPerComponent 8 |
| --- | --- |
| /Length 0 | %This is an external stream /F <</FS /URL /F (http://www.myserver.mycorp.com/images/exttest.jpg) >> /FFilter /DCTDecode >> |

stream
endstream
endobj

8.9.6 Masked images

8.9.6.1 General

Ordinarily, in the opaque imaging model, images mark all areas they occupy on the page as if with
opaque paint. All portions of the image, whether black, white, gray, or colour, completely obscure any
marks that may previously have existed in the same place on the page. In the graphic arts industry and
page layout applications, however, it is common to crop or mask out the background of an image and
then place the masked image on a different background so that the existing background shows through
the masked areas. A number of PDF features are available for achieving such masking effects:


## Page 281

| • | The ImageMask entry in the image dictionary, specifies that the image data shall be used as a |

stencil mask for painting in the current colour.
| • | The Mask entry in the image dictionary (PDF 1.3) specifies a separate image XObject which shall |

be used as an explicit mask specifying which areas of the image to paint and which to mask out.
| • | Alternatively, the Mask entry (PDF 1.3) specifies a range of colours which shall be masked out |

wherever they occur within the image. This technique is known as colour key masking.
NOTE         Earlier versions of PDF commonly simulated masking by defining a clipping path enclosing only
those of an image’s samples that are to be painted. However, if the clipping path is very complex
(or if there is more than one clipping path) not all interactive PDF processors will render the
results in the same way. An alternative way to achieve the effect of an explicit mask is to define
the image being clipped as a pattern, make it the current colour, and then paint the explicit mask
as an image whose ImageMask entry is true.
In the transparent imaging model, a fourth type of masking effect, soft masking, is available through
the SMask entry (PDF 1.4) or the SMaskInData entry (PDF 1.5) in the image dictionary; see 11.6.5,
"Specifying soft masks", for further discussion.

8.9.6.2 Stencil masking

An image mask (an image XObject whose ImageMask entry is true) is a monochrome image in which
each sample is specified by a single bit. However, instead of being painted in opaque black and white,
the image mask is treated as a stencil mask that is partly opaque and partly transparent. Sample values
in the image do not represent black and white pixels; rather, they designate places on the page that
should either be marked with the current colour or masked out (not marked at all). Areas that are
masked out retain their former contents. The effect is like applying paint in the current colour through
a cut-out stencil, which lets the paint reach the page in some places and masks it out in others.

An image mask differs from an ordinary image in the following significant ways:

| • | The image dictionary shall not contain a ColorSpace entry because sample values represent |

masking properties (1 bit per sample) rather than colours.
| • | The value of the BitsPerComponent entry shall be 1. |
| --- | --- |
| • | The Decode entry determines how the source samples shall be interpreted. If the Decode array is |

[0 1] (the default for an image mask), a sample value of 0 shall mark the page with the current
colour, and a 1 shall leave the previous contents unchanged. If the Decode array is [1 0], these
meanings shall be reversed.
NOTE         One of the most important uses of stencil masking is for painting character glyphs represented as
bitmaps. Using such a glyph as a stencil mask transfers only its "black" bits to the page, leaving
the "white" bits (which are really just background) unchanged. For reasons discussed in 9.6.5.3,
"Encodings for Type 3 fonts", an image mask, rather than an image, need almost always be used
to paint glyph bitmaps.
If image interpolation (see 8.9.5.3, "Image interpolation") is requested during stencil masking, the
effect shall be to smooth the edges of the mask, not to interpolate the painted colour values. This effect
can minimise the jaggy appearance of a low-resolution stencil mask.

8.9.6.3 Explicit masking

In PDF 1.3, the Mask entry in an image dictionary may be an image mask, as described in subclause
8.9.6.2, "Stencil masking", which serves as an explicit mask for the primary (base) image. The base


## Page 282

image and the image mask need not have the same resolution (Width and Height values), but since all
images shall be defined on the unit square in user space, their boundaries on the page will coincide;
that is, they will overlay each other. The image mask indicates which places on the page shall be
painted and which shall be masked out (left unchanged). Unmasked areas shall be painted with the
corresponding portions of the base image; masked areas shall not be.

8.9.6.4 Colour key masking

In PDF 1.3, the Mask entry in an image dictionary may be an array specifying a range of colours to be
masked out. Samples in the image that fall within this range shall not be painted, allowing the existing
background to show through.

NOTE 1       The effect is similar to that of the video technique known as chroma-key.
| For colour key masking, the value of the Mask entry shall be an array of 2 | × | 𝑛 integers, |
| --- | --- | --- |
| [min1max1 | …min𝑛max𝑛], where n is the number of colour components in the image’s colour space. |  |

Each integer shall be in the range 0 to 2BitsPerComponent - 1, representing colour values before decoding
with the Decode array. An image sample shall be masked (not painted) if all of its colour components
before decoding, c1… cn, fall within the specified ranges (that is, if mini ≤ ci ≤ maxi for all 1 ≤ i ≤ n).

When colour key masking is specified, the use of a DCTDecode or lossy JPXDecode filter for the
stream can produce unexpected results.

NOTE 2       DCTDecode is always a lossy filter although JPXDecode has a lossy filter option. The use of a
lossy filter means that the output is only an approximation of the original input data. Therefore,
the use of this filter can lead to slight changes in the colour values of image samples, possibly
causing samples that were intended to be masked to be unexpectedly painted instead, in colours
slightly different from the mask colour.

8.9.7 Inline images

As an alternative to the image XObjects described in 8.9.5, "Image dictionaries", a sampled image may
be specified in the form of an inline image. This type of image shall be defined directly within the
content stream in which it will be painted rather than as a separate object. Because the inline format
gives the PDF processor less flexibility in managing the image data, it should be used only for small
images (4096 bytes or less).

An inline image object shall be delimited in the content stream by the operators BI (begin image), ID
(image data), and EI (end image). These operators are summarised in "Table 90 — Inline image
operators". BI and ID shall bracket a series of key-value pairs specifying the characteristics of the
image, such as its dimensions and colour space; the image data shall follow between the ID and EI
operators. The format is thus analogous to that of a stream object such as an image XObject:

BI
… Key-value pairs …
ID
… Image data …
EI


## Page 283

Table 90 — Inline image operators

| Operands | Operator | Description |

| — | BI | Begin an inline image object. |

| — | ID | Begin the image data for an inline image object. |

| — | EI | End an inline image object. |

Inline image objects shall not be nested; that is, two BI operators shall not appear without an
intervening EI to close the first object. Similarly, an ID operator shall only appear between a BI and its
balancing EI. Unless the image uses ASCIIHexDecode or ASCII85Decode as one of its filters, the ID
operator shall be followed by a single white-space character, and the next character shall be
interpreted as the first byte of image data.

The key-value pairs appearing between the BI and ID operators (as listed in "Table 91 — Entries in an
inline image object") are analogous to their respective key-value pairs in an image XObject dictionary
(see "Table 87 — Additional entries specific to an image dictionary") or a stream dictionary (see "Table
5 — Entries common to all stream dictionaries"). For convenience, the abbreviations shown in "Table
91 — Entries in an inline image object" and "Table 92 — Additional abbreviations in an inline image
| object" may be used in place of the full | names. Entries other than those listed shall be ignored. |

The value of the Length (or L) key, which shall be present on all inline images, is the length of the data
between the ID and EI operators excluding the white-space delimiting those operators. The value of
the Length key should not exceed 4096 bytes.

NOTE 1       Because the Length (or L) key is new to PDF 2.0, PDF processors will not encounter this key in
older versions of PDF.
NOTE 2       The L key permits PDF processors to efficiently skip inline images if they do not need to display
them. To skip an image a processor can advance beyond the single white-space character
| following the ID operator, then if the final or only filter is | ASCIIHexDecode or ASCII85Decode |
| --- | --- |
| skip any further white-space. The number of characters expressed by the | L key is then skipped, |
| and the EI operator is expected following optional | white-space. |

"Table 92 — Additional abbreviations in an inline image object" shows additional abbreviations that
can be used for the names of colour spaces and filters.

These abbreviations are valid only in inline images; they shall not be used in image XObjects.
JBIG2Decode, Crypt and JPXDecode are not listed in "Table 92 — Additional abbreviations in an
inline image object", because those filters shall not be used with inline images.

Table 91 — Entries in an inline image object

| Full Name | Abbreviation |

| BitsPerComponent | BPC |

| ColorSpace | CS |

| Decode | D |


## Page 284

| Full Name | Abbreviation |

| DecodeParms | DP |

| Filter | F |

| Height | H |

| ImageMask | IM |

| Intent (PDF 1.1) | No abbreviation |

| Interpolate | I (uppercase I) |

| Length (PDF 2.0) | L |

| Width | W |

Table 92 — Additional abbreviations in an inline image object

| Full Name | Abbreviation |

| DeviceGray | G |

| DeviceRGB | RGB |

| DeviceCMYK | CMYK |

| Indexed | I (uppercase i) |

| ASCIIHexDecode | AHx |

| ASCII85Decode | A85 |

| LZWDecode | LZW |

| FlateDecode (PDF 1.2) | Fl (uppercase F, lowercase L) |

| RunLengthDecode | RL |

| CCITTFaxDecode | CCF |

| DCTDecode | DCT |

The colour space specified by the ColorSpace (or CS) entry shall be one of the standard device colour
spaces (DeviceGray, DeviceRGB, or DeviceCMYK) and shall be present unless ImageMask (IM) is
present and has the value of true. It shall not be a CIE-based colour space or a special colour space, with
the exception of a limited form of Indexed colour space whose base colour space is a device space and
whose colour table is specified by a byte string (see 8.6.6.3, "Indexed colour spaces"). Beginning with
PDF 1.2, the value of the ColorSpace entry may also be the name of a colour space in the ColorSpace
subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). In this case, the
name may designate any colour space that can be used with an image XObject.


## Page 285

NOTE 3       The names DeviceGray, DeviceRGB, and DeviceCMYK (as well as their abbreviations G, RGB,
and CMYK) always identify the corresponding colour spaces directly; they never refer to
resources in the ColorSpace subdictionary.
The image data in an inline image may be encoded by using any of the standard PDF filters except
JPXDecode, Crypt and JBIG2Decode. The bytes between the ID operator and a white-space token, but
before the EI operator shall be treated the same as a stream object’s data (see 7.3.8, "Stream objects"),
even though they do not follow the standard stream syntax.

NOTE 4       This is an exception to the usual rule that the data in a content stream is interpreted according to
the standard PDF syntax for objects. Accordingly, this does not permit comments (see 7.2.4,
"Comments") within the image data.

EXAMPLE            This  example  shows  an  inline  image  17  samples  wide  by  17  high  with  8  bits  per  component  in  the
| DeviceRGB | colour space. The image has been encoded using LZW and ASCII base-85 encoding. The cm operator is used to scale it to a width and height of 17 units in user space and position it at coordinates (298, 388). The q and Q operators encapsulate the cm operation to limit its effect to resizing the image. |

| q | %Save graphics state |
| --- | --- |
| 17 0 0 17 298 388 cm | %Scale and translate coordinate space |

| BI | %Begin inline image object |
| --- | --- |
| /W 17 | %Width in samples |
| /H 17 | %Height in samples |
| /CS /RGB | %Colour space |
| /BPC 8 | %Bits per component /L 763 |
| /F [/A85 /LZW] | %Filters |

| ID | %Begin image data J1/gKA>.]AN&J?]-<HW]aRVcg*bb.\eKAdVV%/PcZ … Image data representing 289 samples … R.s(4KE3&d&7hb*7[%Ct2HCqC~> |
| --- | --- |
| EI | %End inline image object |
| Q | %Restore graphics state |

8.10 Form XObjects

8.10.1 General

A form XObject is a PDF content stream that is a self-contained description of any sequence of graphics
objects (including path objects, text objects, and sampled images). A form XObject may be painted
multiple times — either on several pages or at several locations on the same page — and produces the
same results each time, subject only to the graphics state at the time it is invoked. Not only is this
shared definition economical to represent in the PDF file, but under suitable circumstances the PDF
processor can optimise execution by caching the results of rendering the form XObject for repeated
reuse.

NOTE 1       The term form also refers to a completely different kind of object, an interactive form (sometimes
called an AcroForm), discussed in 12.7, "Forms". Whereas the form XObjects described in this
subclause correspond to the notion of forms in the PostScript language, interactive forms are the
PDF equivalent of the familiar paper instrument. Any unqualified use of the word form is
understood to refer to an interactive form; the type of form described here is always referred to
explicitly as a form XObject.
Form XObjects have various uses:


## Page 286

| • | As its name suggests, a form XObject may serve as the template for an entire page. |

EXAMPLE             A program that prints filled-in tax forms can first paint the fixed template as a form XObject and then paint
the variable information on top of it.

| • | Any graphical element that is to be used repeatedly, such as a company logo or a standard |

component in the output from a computer-aided design system, may be defined as a form XObject.
| • | Certain document elements that are not part of a page’s contents, such as annotation appearances |

(see 12.5.5, "Appearance streams"), shall be represented as form XObjects.
| • | A specialised type of form XObject, called a group XObject (PDF 1.4), can be used to group |

graphical elements together as a unit for various purposes (see 8.10.3, "Group XObjects"). In
particular, group XObjects shall be used to define transparency groups and soft masks for use in
the transparent imaging model (see 11.6.5.1, "Soft-mask dictionaries" and 11.6.6, "Transparency
group XObjects").
| • | Another specialised type of form XObject, a reference XObject (PDF 1.4), may be used to import |

content from one PDF document into another (see 8.10.4, "Reference XObjects").
A PDF writer shall perform the following two specific operations in order to use a form XObject:

| a) | Define the appearance of the form XObject. | A form XObject is a PDF content stream. The dictionary |

portion of the stream (called the form dictionary) shall contain descriptive information about the form
| XObject; the body of the stream shall describ | e the graphics objects that produce its appearance. The |  |  |
| --- | --- | --- | --- |
| contents of the form dictionary are described in | 8.10.2  , "Form dictionaries | ". |  |
| b) | Paint the form XObject. | The Do operator (see 8.8, "External objects | ") shall paint a form XObject whose |
| name is supplied as an operand. The name shall be defined in the | XObject | subdictionary of the current |  |
| resource dictionary. Before invoking this operator, th | e content stream in which it appears should set |  |  |

appropriate parameters in the graphics state. In particular, it should alter the current transformation
matrix to control the position, size, and orientation of the form XObject in user space.
| Each form XObje | ct is defined in its own coordinate system, called form space. The | BBox entry in the |

form dictionary shall be expressed in form space, as shall be any coordinates used in the form XObject’s
| content stream, such as path coordinates. The | Matrix entry in the form dictionary shall specify the |
| --- | --- |
| mapping from form space to the current user space. Each time the form XObject is painted by the | Do |

operator, this matrix shall be concatenated with the current transformation matrix to define the
mapping from form space to device space.

NOTE 2        This differs from the Matrix entry in a pattern dictionary, which maps pattern space to the initial
user space of the content stream in which the pattern is used.
When the Do operator is applied to a form XObject, a PDF processor shall perform the following tasks:

| a) | Saves the current graphics state, as if by invoking the | q operator ( | see 8.4.4, "Graphics state operators | ") |
| --- | --- | --- | --- | --- |
| b) | Concatenates the matrix from the form dictionary’s | Matrix entry with the current transformation matrix |  |  |

(CTM)
| c) | Clips according to the form dictionary’s | BBox entry |  |  |
| --- | --- | --- | --- | --- |
| d) | Paints the graphics objects spe | cified in the form’s content stream |  |  |
| e) | Restores the saved graphics state, as if by invoking the | Q operator ( | see 8.4.4, "Graphics state operators | ") |
| Except as described above, the initial graphics state for the form shall | be inherited from the graphics |  |  |  |
| state that is in effect at the time | Do is invoked. |  |  |  |


## Page 287

8.10.2 Form dictionaries

Every form XObject shall have a form type, which determines the format and meaning of the entries in
its form dictionary. This specification only defines one form type, Type 1. Form XObject dictionaries
may contain the entries shown in "Table 93 — Additional entries specific to a Type 1 form dictionary",
| in addition to the usual entries common to all streams (see | "Table 5 — Entries common to all stream |

dictionaries").

Table 93 — Additional entries specific to a Type 1 form dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be XObject for a form XObject. |

| Subtype | name | (Required) The type of XObject that this dictionary describes; shall be Form for a form XObject. |

| FormType | integer | (Optional) A code identifying the type of form XObject that this dictionary describes. The only valid value is 1. Default value: 1. |

| BBox | rectangle | (Required) An array of four numbers in the form coordinate system (see above), giving the coordinates of the left, bottom, right, and top edges, respectively, of the form XObject’s bounding box. These boundaries shall be used to clip the form XObject and to determine its size for caching. |

| Matrix | array | (Optional) An array of six numbers specifying the form matrix, which maps form space into user space (see 8.3.4, "Transformation matrices"). Default value: the identity matrix [1 0 0 1 0 0]. |

| Resources | dictionary | (Optional but strongly recommended; PDF 1.2) A dictionary specifying any resources (such as fonts and images) required by the form XObject (see 7.8, "Content streams and resources"). In a PDF whose version is 1.1 and earlier, all named resources used in the form XObject shall be included in the resource dictionary of each page object on which the form XObject appears, regardless of whether they also appear in the resource dictionary of the form XObject. These resources should also be specified in the form XObject’s resource dictionary as well, to determine which resources are used inside the form XObject. If a resource is included in both dictionaries, it shall have the same name in both locations. In PDF 1.2 and later versions, form XObjects may be independent of the content streams in which they appear, and this is strongly recommended although not required. In an independent form XObject, the resource dictionary of the form XObject is required and shall contain all named resources used by the form XObject. These resources shall not be promoted to the outer content stream’s resource dictionary, although that stream’s resource dictionary refers to the form XObject. |


## Page 288

| Key | Type | Value |

| Group | dictionary | (Optional; PDF 1.4) A group attributes dictionary indicating that the contents of the form XObject shall be treated as a group and specifying the attributes of that group (see 8.10.3, "Group XObjects"). If a Ref entry (see below) is present, the group attributes shall also apply to the external page imported by that entry, which allows such an imported page to be treated as a group without further modification. |

| Ref | dictionary | (Optional; PDF 1.4) A reference dictionary identifying a page to be imported from another PDF file, and for which the form XObject serves as a proxy (see 8.10.4, "Reference XObjects"). |

| Metadata | stream | (Optional; PDF 1.4) A metadata stream containing metadata for the form XObject (see 14.3.2, "Metadata streams"). |

| PieceInfo | dictionary | (Optional; PDF 1.3) A page-piece dictionary associated with the form XObject (see 14.5, "Page-piece dictionaries"). |

| LastModified | date | (Required if PieceInfo is present; optional otherwise; PDF 1.3) The date and time (see 7.9.4, "Dates") when the form XObject’s contents were most recently modified. If a page-piece dictionary (PieceInfo) is present, the modification date shall be used to ascertain which of the application data dictionaries it contains correspond to the current content of the form (see 14.5, "Page-piece dictionaries"). |

| StructParent | integer | (Required if the form XObject is a structural content item; PDF 1.3) The integer key of the form XObject’s entry in the structural parent tree (see 14.7.5.4, "Finding structure elements from content items"). |

| StructParents | integer | (Required if the form XObject contains marked-content sequences that are structural content items; PDF 1.3) The integer key of the form XObject’s entry in the structural parent tree (see 14.7.5.4, "Finding structure elements from content items"). At most one of the entries StructParent or StructParents shall be present. A form XObject shall be either a content item in its entirety or a container for marked-content sequences that are content items, but not both. |

| OPI | dictionary | (Optional; PDF 1.2; deprecated in PDF 2.0) An OPI version dictionary for the form XObject (see 14.11.7, "Open prepress interface (OPI)"). |

| OC | dictionary | (Optional; PDF 1.5) An optional content group or optional content membership dictionary (see 8.11, "Optional content") specifying the optional content properties for the form XObject. Before the form is processed, its visibility shall be determined based on this entry. If it is determined to be invisible, the entire form shall be skipped, as if there were no Do operator to invoke it. |

| Name | name | (Required in PDF 1.0; optional otherwise; deprecated in PDF 2.0) The name by which this form XObject is referenced in the XObject subdictionary of the current resource dictionary (see 7.8.3, "Resource dictionaries"). |


## Page 289

| Key | Type | Value |

| AF | array of | (Optional; PDF 2.0) An array of one or more file specification |
| --- | --- | --- |
| dictionaries | dictionaries (7.11.3, "File specification dictionaries") which denote the associated files for this form XObject. See 14.13, "Associated files" and 14.13.7, "Associated files linked to XObjects" for more details. |  |

| Measure | dictionary | (Optional; PDF 2.0) A measure dictionary (see "Table 266 — Entries in a measure dictionary") that specifies the scale and units which shall apply to the form. |

| PtData | dictionary | (Optional; PDF 2.0) A point data dictionary (see "Table 272 — Entries in a point data dictionary") that specifies the extended geospatial data that shall apply to the form. |

EXAMPLE            The following shows a simple form XObject that paints a filled square 1000 units on each side.

| 6 0 obj | %Form XObject <</Type /XObject /Subtype /Form /FormType 1 /BBox [0 0 1000 1000] /Matrix [1 0 0 1 0 0] /Length 58 >> stream |
| --- | --- |
| 0 | 0 m |
| 0 | 1000 l 1000 1000 l 1000 0 l f endstream endobj |

8.10.3 Group XObjects

A group XObject (PDF 1.4) is a special type of form XObject that can be used to group graphical
elements together as a unit for various purposes. It shall be distinguished by the presence of the
optional Group entry in the form dictionary (see 8.10.2, "Form dictionaries"). The value of this entry
shall be a subsidiary group attributes dictionary describing the properties of the group.

As shown in "Table 94 — Entries common to all group attributes dictionaries", every group XObject
shall have a group subtype (specified by the S entry in the group attributes dictionary) that determines
the format and meaning of the dictionary’s remaining entries. This specification only defines one
subtype, a transparency group XObject (subtype Transparency) representing a transparency group for
use in the transparent imaging model (see 11.4, "Transparency groups"). The remaining contents of
this type of dictionary are described in 11.6.6, "Transparency group XObjects".

Table 94 — Entries common to all group attributes dictionaries

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Group for a group attributes dictionary. |


## Page 290

| Key | Type | Value |

| S | name | (Required) The group subtype, which identifies the type of group whose attributes this dictionary describes and determines the format and meaning of the dictionary’s remaining entries. The only group subtype defined is Transparency; see 11.6.6, "Transparency group XObjects", for the remaining contents of this type of dictionary. |

8.10.4 Reference XObjects

8.10.4.1 General

Reference XObjects (PDF 1.4) enable one PDF document to import content from another. The document
in which the reference occurs is called the containing document; the one whose content is being
imported is the target document. The target document may reside in a file external to the containing
document or may be included within it as an embedded file stream (see 7.11.4, "Embedded file
streams").

The reference XObject in the containing document shall be a form XObject containing the Ref entry in
its form dictionary, as described below. This form XObject shall serve as a proxy that should be
| processed by a PDF processor when the referenced content is not available | . |

NOTE         The proxy can consist of a low-resolution image of the imported content, a piece of descriptive
text referring to it, a gray box to be displayed in its place, or any other similar placeholder.
PDF processors that do not recognise the Ref entry shall simply display or print the proxy as an
ordinary form XObject. Those PDF processors that do implement reference XObjects shall use the
proxy in place of the imported content if the latter is unavailable. An interactive PDF processor may
also provide a user interface to allow editing and updating of imported content links.

The imported content shall consist of a single, complete PDF page in the target document. It shall be
designated by a reference dictionary, which in turn shall be the value of the Ref entry in the reference
XObject’s form dictionary (see 8.10.2, "Form dictionaries"). The presence of the Ref entry shall
distinguish reference XObjects from other types of form XObjects. "Table 95 — Entries in a reference
dictionary" shows the contents of the reference dictionary.

Table 95 — Entries in a reference dictionary

| Key | Type | Value |

| F | file | (Required) The PDF file containing the target document. specification |

| Page | integer or text | (Required) A page index or page label (see 12.4.2, "Page labels") |
| --- | --- | --- |
| string | identifying the page of the target document containing the content to be imported. This reference is a weak one and may be inadvertently invalidated if the referenced page is changed or replaced in the target document after the reference is created. |  |


## Page 291

| Key | Type | Value |

| ID | array | (Optional) An array of two byte strings constituting a PDF file identifier (14.4, "File identifiers") for the PDF file containing the target document. The use of this entry improves a PDF processor’s chances of finding the intended PDF file and allows it to warn the user if the PDF file has changed since the reference was created. |

When the imported content replaces the proxy, it shall be transformed according to the proxy object’s
transformation matrix and clipped to the boundaries of its bounding box, as specified by the Matrix
and BBox entries in the proxy’s form dictionary (see 8.10.2, "Form dictionaries"). The combination of
the proxy object’s matrix and bounding box thus implicitly defines the bounding box of the imported
page. This bounding box typically coincides with the imported page’s crop box or art box (see 14.11.2,
"Page boundaries"), but may not correspond to any of the defined page boundaries. If the proxy
object’s form dictionary contains a Group entry, the specified group attributes shall apply to the
imported page as well, which allows the imported page to be treated as a group without further
modification.

8.10.4.2 Printing reference XObjects

When printing a page containing reference XObjects, a PDF processor may emit any of the following
items, depending on its capabilities, the user’s preferences, and the nature of the print job:

| • | The imported content designated by the reference XObject |
| --- | --- |
| • | The reference XObject as a proxy for the imported content |

8.10.4.3 Special considerations

Certain special considerations arise when reference XObjects interact with other PDF features:

| • | When the page imported by a reference XObject contains annotations (see 12.5, "Annotations"), |

all annotations that contain a printable, unhidden, visible appearance stream (12.5.5,
"Appearance streams") shall be included in the rendering of the imported page. If the proxy is a
snapshot image of the imported page, it shall also include the annotation appearances. These
appearances shall therefore be converted into part of the proxy’s content stream, either as
subsidiary form XObjects or by flattening them directly into the content stream.
| • | Logical structure information associated with a page (see 14.7, "Logical structure") may be |

ignored when importing that page into another document with a reference XObject. In a target
document with multiple pages, structure elements occurring on the imported page are typically
part of a larger structure pertaining to the document as a whole; such elements cannot
meaningfully be incorporated into the structure of the containing document. In a one-page target
document or one made up of independent, structurally unrelated pages, the logical structure for
the imported page may be wholly self-contained; in this case, it may be possible to incorporate
this structure information into that of the containing document.

8.11 Optional content

8.11.1 General

Optional content (PDF 1.5) refers to sections of content in a PDF document that can be selectively
viewed or hidden by document authors or consumers. This capability is useful in items such as CAD


## Page 292

drawings, layered artwork, maps, and multi-language documents.

The following subclauses describe the PDF structures used to implement optional content:

| • | 8.11.2, "Optional content groups" describes the primary structures used to control the visibility of |

the document.
| • | 8.11.3, "Making graphical content optional", describes how individual pieces of content in a |

document can declare themselves as belonging to one or more optional content groups.
| • | 8.11.4, "Configuring optional content", describes how the states of optional content groups are set. |

8.11.2 Optional content groups

8.11.2.1 General

An optional content group is a dictionary representing a collection of graphics that can be made visible
or invisible dynamically by PDF processors. The graphics belonging to such a group may reside
anywhere in the document: they need not be consecutive in drawing order, nor even belong to the
same content stream. "Table 96 — Entries in an optional content group dictionary" shows the entries
in an optional content group dictionary.

Table 96 — Entries in an optional content group dictionary

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be OCG for an optional content group dictionary. |

| Name | text string | (Required) The name of the optional content group, suitable for presentation in an interactive PDF processor’s user interface. |

| Intent | name or array | (Optional) A single name or an array of names that represent the intended use of the graphics in the group. The values View and Design, or any second-class name may be used (see Annex E, "Extending PDF"). A PDF processor may choose to use only groups that have a specific intent and ignore others. Default value: View. See 8.11.2.3, "Intent" for more information. |

| Usage | dictionary | (Optional) A usage dictionary describing the nature of the content controlled by the group. It may be used by features that automatically control the state of the group based on outside factors. See 8.11.4.4, "Usage and usage application dictionaries" for more information. |

In its simplest form, each dictionary shall contain a Type entry and a Name for presentation in a user
interface. It may have an Intent entry that describes its intended use (see 8.11.2.3, "Intent"), and a
Usage entry that describes the nature of its content (see 8.11.4.4, "Usage and usage application
dictionaries").

Individual content elements in a document may specify the optional content group or groups that
affect their visibility (see 8.11.3, "Making graphical content optional"). Any content whose visibility is
affected by a given optional content group belongs to that group.


## Page 293

A group shall be assigned a state, which is either ON or OFF. States themselves are not part of the PDF
document but may be set programmatically or through the interactive PDF processor’s user interface
to change the visibility of content. When a document is first opened by a PDF processor, the groups’
states shall be initialised based on the document’s default configuration dictionary (see 8.11.4.3,
"Optional content configuration dictionaries").

| Content belonging to a single group shall be visible when the group is ON and invisible when it is | OFF. |

Content may, however, belong to multiple groups, when its group is nested inside of another (parent)
group. In such a case, the content shall only be visible if this group and all its parent groups indicate
visibility. In other words, if the visibility state of an outer level indicates that the content needs to be
hidden, all inner levels shall be hidden regardless of their individual visibility states.

8.11.2.2 Optional content membership dictionaries

To express more complex visibility policies, content shall not declare itself to belong directly to an
optional content group but rather to an optional content membership dictionary, whose entries are
| shown in "Table 97 — Entries in an optional content membership dictionary | ". |

NOTE 1        8.11.3, "Making graphical content optional           " describes how content declares           its membership in a
group or membership dictionary.

Table 97 — Entries in an optional content membership dictionary

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be OCMD for an optional content membership dictionary. |

| OCGs | dictionary | (Optional) A dictionary or array of dictionaries specifying the optional content |
| --- | --- | --- |
| or array | groups whose states shall determine the visibility of content controlled by this membership dictionary. Null values or references to deleted objects shall be ignored. If this entry is not present, is an empty array, or contains references only to null or deleted objects, the P entry shall have no effect on the visibility of any content. |  |

| P | name | (Optional) A name specifying the visibility policy for content belonging to this membership dictionary. Valid values shall be: |
| --- | --- | --- |
| AllOn | visible only if all of the entries in OCGs are ON |  |
| AnyOn | visible if any of the entries in OCGs are ON |  |
| AnyOff | visible if any of the entries in OCGs are OFF |  |
| AllOff | visible only if all of the entries in OCGs are OFF Default value: AnyOn |  |

| VE | array | (Optional; PDF 1.6) An array specifying a visibility expression, used to compute visibility of content based on a set of optional content groups; see discussion below. |

An optional content membership dictionary may express its visibility policy in two ways:


## Page 294

| • | The P entry may specify a simple boolean expression indicating how the optional content groups |

specified by the OCGs entry determine the visibility of content controlled by the membership
dictionary.
| • | PDF 1.6 introduced the VE entry, which is a visibility expres | sion that may be used to specify an |

arbitrary boolean expression for computing the visibility of content from the states of optional
content groups.
If the VE key is present it shall be used in preference to the OCGs and P keys. For compatibility
purposes PDF writers should provide OCGs and P entries where possible, and especially in cases
where the use of VE is necessary to express the intended behaviour.

A visibility expression is an array with the following characteristics:

| • | Its first element shall be a name representing a boolean operator (And, Or, or Not). |
| --- | --- |
| • | Subsequent elements shall be either optional content groups or other visibility expressions. |
| • | If the first element is Not, it shall have only one subsequent element. If the first element is And or |

Or, it shall have one or more subsequent elements.
| • | In evaluating a visibility expression, the ON state of an optional content group shall be equated to |

the boolean value true; OFF shall be equated to false.
Membership dictionaries are useful in cases such as these:

| • | Some content may choose to be invisible when a group is ON and visible when it is | OFF. In this |
| --- | --- | --- |
| case, the content would belong to a membership dictionary whose | OCGs entry consists of a single |  |
| optional content group and whose | P entry is AnyOff or AllOff. |  |

NOTE 2       It is valid to have an OCGs entry consisting of a single group and a P entry that is AnyOn or AllOn.
However, in this case it is preferable to use an optional content group directly because it uses
fewer objects.
| • | Some content may belong to more than one group and needs to specify its policy when the groups |

are in conflicting states. In this case, the content would belong to a membership dictionary whose
OCGs entry consists of an array of optional content groups and whose P entry specifies the
visibility policy, as illustrated in Example 1 in this subclause. Example 2 in this subclause shows
the equivalent policy using visibility expressions.

EXAMPLE 1          This example shows content belonging to a membership dictionary whose OCGs entry consists of an array
of optional content groups and whose P entry specifies the visibility policy.

| <</Type /OCMD | %Content belonging to this optional content %membership dictionary is controlled by the states |
| --- | --- |
| /OCGs [12 0 R 13 0 R 14 0 R] | %of three optional content groups. |
| /P /AllOn | %Content is visible only if the state of all three |
| >> | %groups is ON; otherwise it’s hidden. |

EXAMPLE 2          This example shows a visibility expression equivalent to Example 1 in this subclause

<</Type /OCMD
| /VE [/And 12 0 R 13 0 R 14 0 R] | %Visibility expression equivalent to Example 1. >> |

EXAMPLE 3          This  example   shows   a  more  complicated  visibility  expression   based   on  five  optional  content  groups,
represented by objects 1 through 5. It is equivalent to

"OCG 1" OR (NOT "OCG 2") OR ("OCG 3" AND "OCG 4" AND "OCG 5")

<</Type /OCMD


## Page 295

| /VE [/Or | %Visibility expression: OR |
| --- | --- |
| 1 0 R | %OCG 1 |
| [/Not 2 0 R] | %NOT OCG 2 |
| [/And 3 0 R 4 0 R 5 0 R] | %OCG 3 AND OCG 4 AND OCG 5 ] |

>>

8.11.2.3 Intent

PDF defines two specific intents: Design, which may be used to represent a document designer’s
structural organisation of artwork, and View, which may be used for interactive PDF processors.

NOTE         The Intent entry in "Table 96 — Entries in an optional content group dictionary" provides a way
to distinguish between different intended uses of optional content. For example, many document
design applications, such as CAD packages, offer layering features for collecting groups of
graphics together and selectively hiding or viewing them for the convenience of the author.
However, this layering can be different (at a finer granularity, for example) than would be useful
to consumers of the document. Therefore, a single document can specify different intents for
different optional content groups. A PDF processor can decide to use only groups that are of a
specific intent.
Configuration dictionaries (see 8.11.4.3, "Optional content configuration dictionaries") may also
contain an Intent entry. If one or more of a group’s intents is contained in the current configuration’s
set of intents, the group shall be used in determining visibility. If there is no match, the group shall
have no effect on visibility.

If the configuration’s Intent is an empty array, no groups shall be used in determining visibility;
therefore, all content shall be considered visible.

8.11.3 Making graphical content optional

8.11.3.1 General

Graphical content in a PDF file may be made optional by specifying membership in an optional content
group or optional content membership dictionary. Two primary mechanisms exist for defining
membership:

| • | Sections of content streams delimited by marked-content operators may be made optional, as |

described in 8.11.3.2, "Optional content in content streams".
| • | Form and image XObjects and annotations may be made optional in their entirety by means of a |

dictionary entry, as described in 8.11.3.3, "Optional content in XObjects and annotations".
When it is determined that a piece of optional content in a PDF file is to be hidden, the following shall
occur:

| • | The content shall not be drawn. |
| --- | --- |
| • | Graphics state operations, such as setting the colour, transformation matrix, and clipping, shall |

still be applied. In addition, graphics state side effects that arise from drawing operators shall be
applied; in particular, the current text position shall be updated even for text wrapped in optional
content. In other words, graphics state parameters that persist past the end of a marked-content
section shall be the same whether the optional content is visible or not.
NOTE 1       Hiding a section of optional content does not change the colour of objects that do not belong to
the same optional content group.


## Page 296

| • | This rule shall also apply to operators that set state that is not strictly graphics state; for example, |

BX and EX.
| • | Objects such as form XObjects and annotations that have been made optional may be skipped |

entirely, because their contents are encapsulated such that no changes to the graphics state (or
other state) persist beyond the processing of their content stream.
Other features in interactive PDF processors, such as searching and editing, may be affected by the
ability to selectively show or hide content. An interactive PDF processor may choose whether to use
the document’s current state of optional content groups (and, correspondingly, the document’s visible
graphics) or to supply their own states of optional content groups to control the graphics they process.

NOTE 2       Tools to select and move annotations need to honour the current on-screen visibility of
annotations when performing cursor tracking and mouse-click processing. A full text search
engine, however, is likely to need to process all content in a document, regardless of its current
visibility on-screen. Export filters could choose the current on-screen visibility, the full content,
or present the user with a selection of optional content groups to control visibility.
NOTE 3       A PDF processor that does not support optional content, such as one that only supports PDF 1.4
functionality, will draw and process all content in a document.

8.11.3.2 Optional content in content streams

Sections of content in a content stream (including a page's content stream, a form or pattern’s content
stream, glyph descriptions of a Type 3 font as specified by its CharProcs entry, or an annotation’s
appearance) may be made optional by enclosing them between the marked-content operators BDC and
EMC (see 14.6, "Marked content") with a marked-content tag of OC. In addition, a DP marked-content
operator may be placed in a page’s content stream to force a reference to an optional content group or
groups on the page, even when the page has no current content in that layer.

| The property list associated with the marked | -content shall specify either an optional content group or |

optional content membership dictionary to which the content belongs. Because a group shall be an
| indirect object and a membership dictionary contains references to indirect objects, the property lis | t |

shall be a named resource listed in the Properties subdictionary of the current resource dictionary
(see 14.6.2, "Property lists"), as shown in Example 1 and Example 2 in this subclause.

Although the marked-content tag shall be OC, other applications of marked-content are not precluded
| from using OC as a tag. The marked-content is optional content only if the tag is | OC and the dictionary |
| --- | --- |
| operand is a valid optional content group that is included in the | OCGs array of the optional content |
| properties dictionary (see | "Table 98 — Entries in the optional content properties dictionary"), or a |

valid optional content membership dictionary.

To avoid conflict with other features that used marked-content (such as logical structure; see 14.7,
"Logical structure"), the following strategy is recommended:

| • | Where content is to be tagged with optional content markers as well as other markers, the |

optional content markers should be nested inside the other marked-content.
| • | Where optional content and the other markers would overlap but there is not strict containment, |

the optional content should be broken up into two or more BDC/EMC sections, nesting the
optional content sections inside the others as necessary.
NOTE         Breaking up optional content spans does not damage the nature of the visibility of the content,
whereas the same guarantee cannot be made for all other uses of marked-content.


## Page 297

In the following example, the state of the Show Greeting optional content group directly controls the
visibility of the text string "Hello" on the page. When the group is ON, the text is visible; when the
group is OFF, the text is hidden.

EXAMPLE 1

%Within a content stream
…
| /OC /oc1 BDC | %Optional content follows BT /F1 1 Tf 12 0 0 12 100 600 Tm (Hello) Tj ET |
| --- | --- |
| EMC | %End of optional content |

…

| << | %In the resources dictionary |
| --- | --- |
| /Properties <</oc1 5 0 R>> | %This dictionary maps the name oc1 to an |
| … | %optional content group (object 5) |

>>

| 5 0 obj | %The OCG controlling the visibility |
| --- | --- |
| << | %of the text. /Type /OCG /Name (Show Greeting) |

>>
endobj

The example above shows one piece of content associated with one optional content group. There are
other possibilities:

| • | More than one section of content may refer to the same group or membership dictionary, in which |

case the visibility of both sections is always the same.
| • | Equivalently, although less space-efficient, different sections may have separate membership |

dictionaries with the same OCGs and P entries. The sections shall have identical visibility
behaviour.
| • | Two sections of content may belong to membership dictionaries that refer to the same group(s) |

but with different P settings. For example, if one section has no P entry, and the other has a P
entry of AllOff, the visibility of the two sections of content shall be opposite. That is, the first
section shall be visible when the second is hidden, and vice versa.
The following example demonstrates both the direct use of optional content groups and the indirect
use of groups through a membership dictionary. The content (a black rectangle frame) is drawn if
either of the images controlled by the groups named Image A or Image B is shown. If both groups are
hidden, the rectangle frame is hidden.

EXAMPLE 2

%Within a content stream
…
| /OC /OC2 BDC | %Draws a black rectangle frame 0 g 4 w 100 100 412 592 re s |

EMC
| /OC /OC3 BDC | %Draws an image XObject q 412 0 0 592 100 100 cm |


## Page 298

/Im3 Do
Q
EMC
| /OC /OC4 BDC | %Draws an image XObject q 412 0 0 592 100 100 cm /Im4 Do Q |

EMC
…
| << | %The resource dictionary /Properties <</OC2 20 0 R /OC3 30 0 R /OC4 40 0 R>> /XObject <</lm3 50 0 R /lm4 /60 0 R>> |

>>

20 0 obj
| << | %Optional content membership dictionary /Type /OCMD /OCGs [30 0 R 40 0 R] /P /AnyOn |

>>
endobj

| 30 0 obj | %Optional content group "Image A" << /Type /OCG /Name (Image A) |

>>
endobj

| 40 0 obj | %Optional content group "Image B" |

<<
/Type /OCG
/Name (Image B)
>>
endobj

8.11.3.3 Optional content in XObjects and annotations

In addition to marked-content within content streams, form XObjects and image XObjects (see 8.8,
"External objects") and annotations (see 12.5, "Annotations") may contain an OC entry, which shall be
an optional content group or an optional content membership dictionary.

A form XObject or image XObject's visibility shall be determined by the state of the group or those of
the groups referenced by the membership dictionary in conjunction with its P (or VE) entry, along with
the current visibility state in the context in which the XObject is invoked (that is, whether objects are
visible in the content stream at the place where the Do operation occurred).

Annotations have various flags controlling on-screen and print visibility (see 12.5.3, "Annotation
flags"). If an annotation contains an OC entry, it shall be visible for screen or print only if the flags have
the appropriate settings and the group or membership dictionary indicates it shall be visible.

8.11.4 Configuring optional content

8.11.4.1 General

A PDF document containing optional content may specify the default states for the optional content
groups in the document and indicate which external factors shall be used to alter the states. The
following subclauses describe the PDF structures that are used to specify this information.


## Page 299

8.11.4.2, "Optional content properties dictionary" describes the structure that lists all the optional
content groups in the document and their possible configurations.

8.11.4.3, "Optional content configuration dictionaries" describes the structures that specify initial state
settings and other information about the groups in the document.

8.11.4.4, "Usage and usage application dictionaries" and 8.11.4.5, "Determining the state of optional
content groups" describe how the states of groups can be affected based on external factors.

8.11.4.2 Optional content properties dictionary

The optional OCProperties entry in the document catalog dictionary (see 7.7.2, "Document catalog
dictionary") shall contain, when present, the optional content properties dictionary, which contains a
list of all the optional content groups in the document, as well as information about the default and
alternate configurations for optional content. This dictionary shall be present if the PDF file contains
any optional content; if it is missing, a PDF processor shall ignore any optional content structures in
the document. This dictionary contains the following entries:

Table 98 — Entries in the optional content properties dictionary

| Key | Type | Value |

| OCGs | array | (Required) An array of indirect references to all the optional content groups in the document (see 8.11.2, "Optional content groups"), in any order. Every optional content group shall be included in this array. |

| D | dictionary | (Required) The default viewing optional content configuration dictionary (see 8.11.4.3, "Optional content configuration dictionaries"). |

| Configs | array | (Optional) An array of alternate optional content configuration dictionaries (see 8.11.4.3, "Optional content configuration dictionaries"). |

8.11.4.3 Optional content configuration dictionaries

The D and Configs entries in "Table 98 — Entries in the optional content properties dictionary" are
configuration dictionaries, which represent different presentations of a document’s optional content
groups for use by PDF processors. The D configuration dictionary shall be used to specify the initial
state of the optional content groups when a document is opened. Configs lists other configurations that
may be used under particular circumstances. The entries in a configuration dictionary are shown in
"Table 99 — Entries in an optional content configuration dictionary".

Table 99 — Entries in an optional content configuration dictionary

| Key | Type | Value |

| Name | text | (Optional) A name for the configuration, suitable for presentation in a user interface. string |

| Creator | text | (Optional) Name of the application or feature that created this configuration dictionary. string |


## Page 300

| Key | Type | Value |

| BaseState | name | (Optional) Used to initialise the states of all the optional content groups in a document when this configuration is applied. The value of this entry shall be one of the following names: |
| --- | --- | --- |
| ON | The states of all groups shall be turned ON. |  |
| OFF | The states of all groups shall be turned OFF. |  |
| Unchanged | The states of all groups shall be left unchanged. After this initialization, the contents of the ON and OFF arrays shall be processed, overriding the state of the groups included in the arrays. Default value: ON. If BaseState is present in the document’s default configuration dictionary, its value shall be ON. |  |

| ON | array | (Optional) An array of optional content groups whose state shall be set to ON when this configuration is applied. If the BaseState entry is ON, this entry is redundant. |

| OFF | array | (Optional) An array of optional content groups whose state shall be set to OFF when this configuration is applied. Any OCG group included in the ON array shall not also be included in the OFF array. If the BaseState entry is OFF, this entry is redundant. |

| Intent | name or | (Optional) A single name or an array of names used by a PDF processor to determine |
| --- | --- | --- |
| array | which optional content groups’ states to consider and which to ignore in calculating the visibility of content (see 8.11.2.3, "Intent"). Since this value may contain any name that could appear as the value of the Intent key in an optional content group dictionary, a special name, All, is used to indicate the set of all intents. Default value: View. (If Intent is present in the document’s default configuration dictionary, its value shall be View.) |  |

| AS | array | (Optional) An array of usage application dictionaries (see "Table 101 — Entries in a usage application dictionary") specifying which usage dictionary categories (see "Table 100 — Entries in an optional content usage dictionary") shall be consulted by PDF processors, when automatically setting the states of optional content groups based on external factors, such as the current system language or viewing magnification, and when they shall be applied. |


## Page 301

| Key | Type | Value |

| Order | array | (Optional) An array specifying the order for presentation of optional content groups in an interactive PDF processor’s user interface. The array elements may include the following objects: |
| --- | --- | --- |
| • | Optional content group dictionaries, whose Name entry shall be displayed in the user interface by the interactive PDF processor. |  |
| • | Arrays of optional content groups which may be displayed by an interactive PDF processor in a tree or outline structure. Each nested array may optionally have as its first element a text string to be used as a non-selectable label in an interactive PDF processor’s user interface. Text labels in nested arrays shall be used to present collections of related optional content groups, and not to communicate actual nesting of content inside multiple layers of groups (see Example 1 in 8.11.4.3, "Optional content configuration dictionaries"). To reflect actual nesting of groups in the content, such as for layers with sublayers, nested arrays of groups without a text label shall be used (see Example 2 in 8.11.4.3, "Optional content configuration dictionaries"). An empty array [] explicitly specifies that no groups shall be presented. In the default configuration dictionary, the default value shall be an empty array; in other configuration dictionaries, the default shall be the Order value from the default configuration dictionary. Any groups not listed in this array shall not be presented in any user interface that uses the configuration. |  |

| ListMode | name | (Optional) A name specifying which optional content groups in the Order array shall be displayed to the user. Valid values shall be: |
| --- | --- | --- |
| AllPages | Display all groups in the Order array. |  |
| VisiblePages | Display only those groups in the Order array that are referenced by one or more visible pages. Default value: AllPages. |  |

| RBGroups | array | (Optional) An array consisting of one or more arrays, each of which represents a collection of optional content groups whose states shall be intended to follow a radio button paradigm. That is, the state of at most one optional content group in each array shall be ON at a time. If one group is turned ON, all others shall be turned OFF. However, turning a group from ON to OFF does not force any other group to be turned ON. An empty array [] explicitly indicates that no such collections exist. In the default configuration dictionary, the default value shall be an empty array; in other configuration dictionaries, the default is the RBGroups value from the default configuration dictionary. |

| Locked | array | (Optional; PDF 1.6) An array of optional content groups that shall be locked when this configuration is applied. The state of a locked group cannot be changed through the user interface of an interactive PDF processor. PDF writers can use this entry to prevent the visibility of content that depends on these groups from being changed by users. Default value: an empty array. An interactive PDF processor may allow the states of optional content groups to be changed by means other than the user interface, such as ECMAScript or items in the AS entry of a configuration dictionary. |


## Page 302

NOTE         Example 1 and Example 2 in this subclause illustrate the use of the Order entry to control the
display of groups in a user interface.

EXAMPLE 1          Given the following PDF objects:

| 1 0 obj <</Type /OCG /Name (Skin)>> endobj | %Optional content groups 2 0 obj <</Type /OCG /Name (Bones)>> endobj 3 0 obj <</Type /OCG /Name (Bark)>> endobj 4 0 obj <</Type /OCG /Name (Wood)>> endobj |
| --- | --- |
| 5 0 obj | %Configuration dictionary <</Order [[(Frog Anatomy) 1 0 R 2 0 R] [(Tree Anatomy) 3 0 R 4 0 R]]>> |

An interactive PDF processor needs to display the optional content groups as follows:

Frog Anatomy
Skin
Bones
Tree Anatomy
Bark
Wood

EXAMPLE 2          Given the following PDF objects:

%Page contents
| /OC /L1 BDC | %Layer 1 |
| --- | --- |
| /OC /L1a BDC | %Sublayer A of layer 1 0 0 100 100 re f EMC |
| /OC /L1b BDC | %Sublayer B of layer 1 0 100 100 100 re f EMC EMC … |
| <</L1 1 0 R | %Resource names /L1a 2 0 R /L1b 3 0 R >> |
| … | %Optional content groups 1 0 obj <</Type /OCG /Name (Layer 1)>> endobj 2 0 obj <</Type /OCG /Name (Sublayer A)>> endobj 3 0 obj <</Type /OCG /Name (Sublayer B)>> endobj … |
| 4 0 obj | %Configuration dictionary <</Order [1 0 R [2 0 R 3 0 R]]>> |

An interactive PDF processor needs to display the optional content groups as follows:

Layer 1
Sublayer A
Sublayer B

The AS entry is an auto state array consisting of one or more usage application dictionaries that specify
how interactive PDF processors shall, and non-interactive PDF processors should, automatically set the
state of optional content groups based on external factors, as discussed in 8.11.4.4, “Usage and usage
application dictionaries”.


## Page 303

8.11.4.4 Usage and usage application dictionaries

Optional content groups are typically constructed to control the visibility of graphics objects that are
related in some way. Objects can be related in several ways; for example, a group may contain content
in a particular language or content suitable for viewing at a particular magnification.

An optional content group’s usage dictionary (the value of the Usage entry in an optional content group
dictionary; see "Table 96 — Entries in an optional content group dictionary") shall contain information
describing the nature of the content controlled by the group. This dictionary can contain any
combination of the entries shown in "Table 100 — Entries in an optional content usage dictionary".

Table 100 — Entries in an optional content usage dictionary

| Key | Type | Value |

| CreatorInfo | dictionary | (Optional) A dictionary used by the creating application to store application- specific data associated with this optional content group. It shall contain two required entries: |
| --- | --- | --- |
| Creator | A text string specifying the application that created the group. |  |
| Subtype | A name defining the type of content controlled by the group. Suggested values include but shall not be limited to Artwork, for graphic-design or publishing applications, and Technical, for technical designs such as building plans or schematics. Additional entries may be included to present information relevant to the creating application or related applications. If an Optional Content Group Dictionary (see "Table 96 — Entries in an optional content group dictionary") Intent entry contains Design then a CreatorInfo entry should be included. |  |

| Language | dictionary | (Optional) A dictionary specifying the language of the content controlled by this optional content group. It shall contain the following entry: |
| --- | --- | --- |
| Lang | (required) A text string that specifies a language and possibly a locale (see 14.9.2, "Natural language specification"). For example, es-MX represents Mexican Spanish. Additionally, it may contain the following entry: |  |
| Preferred | (optional) A name whose values shall be either ON or OFF. Default value: OFF. It shall be used by PDF processors when there is a partial match but no exact match between the system language and the language strings in all usage dictionaries. See 8.11.4.4, "Usage and usage application dictionaries" for more information. |  |

| Export | dictionary | (Optional) A dictionary containing one entry, ExportState, a name whose value shall be either ON or OFF. This value indicates the recommended state for content in this group when the document (or part of it) is saved by a PDF processor to a format that does not support optional content (for example, a raster image format). |


## Page 304

| Key | Type | Value |

| Zoom | dictionary | (Optional) A dictionary specifying a range of magnifications at which the content in this optional content group is best viewed. It shall contain one or both of the following entries: |
| --- | --- | --- |
| min | A number representing the minimum recommended magnification factor at which the group shall be ON. Default value: 0. |  |
| max | A number representing the magnification factor below which the group shall be ON. Default value: infinity. |  |

| Print | dictionary | (Optional) A dictionary specifying that the content to be used when printing. It may contain the following optional entries: |
| --- | --- | --- |
| Subtype | A name object specifying the kind of content controlled by the group; for example, Trapping, PrintersMarks or Watermark. |  |
| PrintState | A name that shall be either ON or OFF, indicating that the group shall be set to that state when the document is printed. |  |

| View | dictionary | (Optional) A dictionary that shall have a single entry, ViewState, a name that shall have a value of either ON or OFF, indicating the state of the group when the document is first opened by a PDF processor. |

| User | dictionary | (Optional) A dictionary specifying one or more users for whom this optional content group is primarily intended. This dictionary shall have two required entries: |
| --- | --- | --- |
| Type | A name object that shall be either Ind (individual), Ttl (title or position), or Org (organisation). |  |
| Name | A text string or array of text strings representing the name(s) of the individual, position or organisation. |  |

| PageElement | dictionary | (Optional) A dictionary declaring that the group contains a pagination artifact. It shall contain one entry, Subtype, whose value shall be a name that is either HF (header/footer), FG (foreground image or graphics), BG (background image or graphics), or L (logo). |

While the data in the usage dictionary serves as information for a document user to examine, it may
also be used by PDF processors to automatically manipulate the state of optional content groups based
on external factors such as current system language settings or zoom level. Document authors may use
usage application dictionaries to specify which entries in the usage dictionary shall be consulted to
automatically set the state of optional content groups based on such factors. Usage application
dictionaries shall be listed in the AS entry in an optional content configuration dictionary (see "Table
99 — Entries in an optional content configuration dictionary"). If no AS entry is present, states shall
not be automatically adjusted based on usage information.

A usage application dictionary specifies the rules by which usage entries shall be used by interactive
PDF processors, and should be used by non-interactive PDF processors, to automatically manipulate
the state of optional content groups, which groups shall be affected, and under which circumstances.
"Table 101 — Entries in a usage application dictionary" shows the entries in a usage application
dictionary.


## Page 305

Table 101 — Entries in a usage application dictionary

| Key | Type | Value |

| Event | name | (Required) A name defining the situation in which this usage application dictionary should be used. Shall be one of View, Print, or Export. |

| OCGs | array | (Optional) An array listing the optional content groups that shall have their states automatically managed based on information in their usage dictionary (see 8.11.4.4, "Usage and usage application dictionaries"). Default value: an empty array, indicating that no groups shall be affected. |

| Category | array | (Required) An array of names, each of which corresponds to a usage dictionary entry (see "Table 100 — Entries in an optional content usage dictionary"). When managing the states of the optional content groups in the OCGs array, each of the corresponding categories in the group’s usage dictionary shall be considered. |

The Event entry specifies whether the usage settings shall be applied during viewing, printing, or
exporting the document. The OCGs entry specifies the set of optional content groups to which usage
settings shall be applied. For each of the groups in OCGs, the entries in its usage dictionary (see "Table
100 — Entries in an optional content usage dictionary") specified by Category shall be examined to
yield a recommended state for the group. If all the entries yield a recommended state of ON, the group’s
| state shall be set to | ON; otherwise, its state shall be set to | OFF. |

The entries in the usage dictionary shall be used as follows:

| • | View: The state shall be the value of the | ViewState entry. This entry allows a document to contain |
| --- | --- | --- |
| content that is relevant only when the document is viewed interactively, such as instructions fo | r |  |

how to interact with the document.
| • | Print: The state shall be the value of the PrintState entry. If PrintState is not present, the state of |

the optional content group shall be left unchanged.
| • | Export: The state shall be the value of the ExportState entry. |
| --- | --- |
| • | Zoom: If the current magnification level of the document is greater than or equal to min and less |

than max, the ON state shall be used; otherwise, OFF shall be used.
| • | User: The Name entry shall specify a name or names to match with the user’s identification. The |
| --- | --- |
| Type entry determines how the Name entry shall be interpreted (name, title, or organis | ation). If |
| there is an exact match, the | ON state shall be used; otherwise OFF shall be used. |
| • | Language: This category shall allow the selection of content based on the language and locale of |

the application. If an exact match to the language and locale is found among the Lang entries of
the optional content groups in the usage application dictionary’s OCGs list, all groups that have
exact matches shall receive an ON recommendation. If no exact match is found, but a partial
match is found (that is, the language matches but not the locale), all partially matching groups
that have Preferred entries with a value of ON shall receive an ON recommendation. All other
groups shall receive an OFF recommendation.
There shall be no restriction on multiple entries with the same value of Event, in order to allow
documents with incompatible usage application dictionaries to be combined into larger documents and
have their behaviour preserved. If a given optional content group appears in more than one OCGs
array, its state shall be ON only if all categories in all the usage application dictionaries it appears in
have a state of ON.


## Page 306

EXAMPLE            This example shows the use of an auto state array with usage application dictionaries. The AS entry in the
default configuration dictionary is an array of three usage application dictionaries, one for each of the Event
values View, Print, and Export.

| /OCProperties | %OCProperties dictionary in document catalog dictionary <</OCGs [1 0 R 2 0 R 3 0 R 4 0 R] |
| --- | --- |
| /D <</BaseState /OFF | %The default configuration /ON [1 0 R] |
| /AS [ | %Auto state array of usage application dictionaries <</Event /View /Category [/Zoom] /OCGs [1 0 R 2 0 R 3 0 R 4 0 R]>> <</Event /Print /Category [/Print] /OCGs [4 0 R]>> <</Event /Export /Category [/Export] /OCGs [3 0 R 4 0 R]>> ] >> >> |

…

1 0 obj
<</Type /OCG
/Name (20000 foot view)
/Usage  <</Zoom  <</max 1.0>>>>
<<
endobj

2 0 obj
<</Type /OCG
/Name (10000 foot view)
/Usage  <</Zoom <</min 1.0 /max 2.0>>>>
>>
endobj

3 0 obj
<</Type /OCG
/Name (1000 foot view)
/Usage <</Zoom  <</min 2.0 /max 20.0>>
/Export <</ExportState /OFF>>>>
<<
endobj

4 0 obj
<</Type /OCG
/Name (Copyright notice)
/Usage  <</Print <</PrintState /ON>>
/Export <</ExportState /ON>>>>
>>
endobj

In the example, the usage application dictionary with event type View specifies that all optional
content groups have their states managed based on zoom level when viewing. Three groups (objects 1,
2, and 3) contain Zoom usage information. Object 4 has none; therefore, it is not affected by zoom level
changes. Object 3 receives an OFF recommendation when exporting. When printing or exporting,
object 4 receives an ON recommendation.

8.11.4.5 Determining the state of optional content groups

This subclause summarises the rules by which PDF processors make use of the configuration and usage
application dictionaries to set the state of optional content groups. For purposes of this discussion, it is
useful to distinguish the following types of PDF processors:

| • | Viewer applications which allow users to interact with the document in various ways. |


## Page 307

| • | Design applications, which offer layering features for collecting groups of graphics together and |

selectively hiding or viewing them.
NOTE 1       The following rules are not meant to apply to design applications; they can manage their states
in an entirely different manner if they choose.
| • | Aggregating applications, which import PDF files as graphics. |
| --- | --- |
| • | Printing applications, which print PDF files. |

When a document is opened, its optional content groups shall be assigned a state based on the D
(default) configuration dictionary in the OCProperties dictionary:

| a) | The value of | BaseState | shall be applied to all the groups. |  |  |
| --- | --- | --- | --- | --- | --- |
| b) | The groups listed in either the | ON or OFF array (depending on which one is | opposite to | BaseState | ) shall |

have their states adjusted.
| This state shall be the | initial state used by all PDF processors. |

NOTE 2       Viewer applications can also provide users with an option to view documents in this state (that
is, to disable the automatic adjustments discussed below). This option permits an accurate
| preview of the content as it will appear | in an aggregating applicatio | n or a stand | -alone printing system. |
| --- | --- | --- | --- |
| The remaining discussion in this | subclause applies only to | interactive PDF processors | . Such |
| applications shall examine the | AS array for usage application dictionaries that have an | Event of type |  |
| View. For each one found, | the groups listed in its | OCGs array shall be adjusted as described in | 8.11.4.4, |

"Usage and usage application dictionaries".

Subsequently, the document is ready for interactive viewing by a user. Whenever there is a change to a
factor that the usage application dictionaries with event type View depend on (such as zoom level), the
corresponding dictionaries shall be reapplied.

The user may manipulate optional content group states manually or by triggering set-OCG-state
actions (see 12.6.4.13, "Set-OCG-state actions") by, for example, clicking links or document outline
items. Manual changes shall override the states that were set automatically. The states of these groups
remain overridden and shall not be readjusted based on usage application dictionaries with event type
View as long as the document is open (or until the user reverts the document to its original state).

When a document is printed by an interactive PDF processor, usage application dictionaries with an
event type Print shall be applied over the current states of optional content groups. These changes
shall persist only for the duration of the print operation; then all groups shall revert to their prior
states.

Similarly, when a document is exported to a format that does not support optional content, usage
application dictionaries with an event type Export shall be applied over the current states of optional
content groups. Changes shall persist only for the duration of the export operation; then all groups
shall revert to their prior states.

NOTE 3       Although the event types Print and Export have identically named counterparts that are usage
categories, the corresponding usage application dictionaries are permitted to specify that other
categories can be applied.


## Page 308

