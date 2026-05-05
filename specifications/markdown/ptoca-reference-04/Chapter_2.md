Chapter 2. Introduction to PT OCA
The Presentation T ext object is the data object used in document processing environments for representing
text which has been prepared for presentation. T ext, as used here, means an ordered string of characters,
such as graphic symbols, numbers, and letters, that are suitable for the specific purpose of representing
coherent information. T ext which has been prepared for presentation has been reduced to a primitive form
through explicit specification of the characters and their placement in the presentation space. Control
sequences which designate specific control functions may be embedded within the text. These functions
extend the primitive form by applying specific characteristics to the text when it is presented. The collection of
the graphic characters and control sequences is called Presentation T ext, and the object that contains the
Presentation T ext is called the Presentation T ext object.
Presentation T ext is associated with the output of text information. A Presentation T ext object is the description
of Presentation text for a portion of a document, the intended connotation being finished product or formatted
output. This output version of text contained within the object is in the form specified by Presentation T ext
Object Content Architecture (PT OCA) and has been designed for direct output on devices, such as displays or
printers.
A Presentation T ext object is a device-independent, self-defining representation of a two-dimensional
presentation space, called the Presentation T ext object space, or object space, which contains the
Presentation T ext data. The rules of PTO CA specify how the object space is constituted, what the boundaries
are for text positioning, what the text content is, and how the text content is to be placed within the object
space, using concepts such as sequential order , orientation, and position.
Architecture Note: Note that when presentation text is processed in a MO:DCA environment where the
Presentation T ext Data Descriptor (PTD) is carried in the Active Environment Group (AEG) for the page,
or when such text is processed in an IPDS environment, the Presentation T ext object is bounded by the
beginning of the page and the end of the page. This is sometimes called a text major environment.
When the PTD is carried in the Object Environment Group (OEG) of a MO:DCA text object, the text
object is bounded by the Begin Presentation T ext (BPT) and End Presentation T ext (EPT) structured
fields. For such objects, the PTD in the AEG is ignored.
The Presentation T ext object space is defined on the X
p
,Y
p
coordinate system, which is an orthogonal
coordinate system based on the fourth quadrant of a standard Cartesian coordinate system. The object space
is positioned within the data stream's object area. Coincident with the X
p
,Y
p
coordinate system is the I,B
coordinate system, which is a translation of the X
p
,Y
p
coordinate system.
The position of the elements in the object space is described in terms of the I,B coordinate system. The
increasing I-axis is the inline direction, which is normally the reading direction of the text. The increasing B-axis
is the baseline direction, which is normally the direction for adding lines of text.
The basic elements of the object are the graphic characters which are identified as code points of a code page.
The identification of graphic characters, their relationship to each other , and the relationship of the code point
to the graphic character are given by the coded font selected to present the text.
The relationship of the elements to the space they occupy is described in terms of their orientation, starting
location, and units of measure.
The positioning of the graphic characters on a line is accomplished by moving the presentation position.
Graphic characters may be placed adjacent to one another or positioned anywhere in the object space through
the use of control sequences defined by PTOCA. Control sequences have been defined to move the
presentation position to another position, to move to the beginning of another line, to adjust the distance
between two adjacent characters, to draw lines such as rules, to adjust the distance between lines, to change
the font, to specify the color of characters and rules, to overstrike a text field with a specified character , and to
underscore a text field.

## Page 26

8 PT OCA Reference
National Language Support (NLS) is handled in the level of formatting above the Presentation T ext object. Font
NLS support is provided in the font mapping function in the controlling environment.
Introduction to PT OCA

## Page 27

Copyright © AFP Consortium 1997, 2025 9
