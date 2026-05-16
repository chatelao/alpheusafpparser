# Granular Test Coverage - PTOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| PTOCA-1-001 | This chapter provides a brief overview of Presentation Architecture. | ✅ |
| PTOCA-1-002 | Figure 1 shows today's presentation environment. | ✅ |
| PTOCA-1-003 | Figure 1. Presentation Environment.** The environment is a coordinated set of services architected to meet the presentation needs of today's applications. | ✅ |
| PTOCA-1-004 | Document Creation Services**: import/export, edit/revise, format, scan, transform | ✅ |
| PTOCA-1-005 | Document Archiving Services**: store, retrieve, index, search, extract | ✅ |
| PTOCA-1-006 | Document Viewing Services**: browse, navigate, search, clip, annotate, tag | ✅ |
| PTOCA-1-007 | Document Printing Services**: print, submit, distribute, manage, print, finish | ✅ |
| PTOCA-1-008 | The ability to create, store, retrieve, view, and print data in presentation formats friendly to people is a key requirement in almost every application of computers and information processing. This requirement is becoming increasingly difficult to meet because of the number of applications, servers, and devices that must interoperate to satisfy today's presentation needs. | ✅ |
| PTOCA-1-009 | The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP architectures provide structures that support object-oriented models and client/server environments. | ✅ |
| PTOCA-1-010 | AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ✅ |
| PTOCA-1-011 | AFP architectures provide the means for representing documents in a data format that is independent of the methods used to capture or create them. Documents can contain combinations of text, image, graphics, and bar code objects in presentation-system-independent and resolution-independent formats. Documents can contain fonts, overlays, and other resource objects required at presentation time to present the data properly. Finally, documents can contain resource objects, such as a document index and tagging elements supporting the search and navigation of document data, for a variety of application purposes. | ✅ |
| PTOCA-1-012 | The presentation architecture components are divided into two major categories: data streams and objects. | ✅ |
| PTOCA-1-013 | A data stream is a continuous ordered stream of data elements and objects conforming to a given format. Application programs can generate data streams destined for a presentation service, archive library, presentation device, or another application program. The strategic presentation data stream architectures are: | ✅ |
| PTOCA-1-014 | Mixed Object Document Content Architecture (MO:DCA) | ✅ |
| PTOCA-1-015 | Intelligent Printer Data Stream (IPDS) Architecture | ✅ |
| PTOCA-1-016 | The MO:DCA architecture defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. The MO:DCA format supports storing and retrieving documents in an archive, viewing, annotation, and printing of documents or parts of documents in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them. | ✅ |
| PTOCA-1-017 | The IPDS architecture defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers. | ✅ |
| PTOCA-1-018 | Figure 2. Presentation Model.** This diagram shows the major components in a presentation system and their use of data stream and object architectures. | ✅ |
| PTOCA-1-019 | MO:DCA** to presentation servers | ✅ |
| PTOCA-1-020 | IPDS** to printers and post processors | ✅ |
| PTOCA-1-021 | Resource Objects**: Fonts, Overlays, Page Segments, Form Definition, Color Management Resources, Color Table, Document Index, Metadata | ✅ |
| PTOCA-1-022 | Data Objects**: Text, Image, Graphics, Bar Codes, Object Containers, Other Objects | ✅ |
| PTOCA-1-023 | Documents can be made up of different kinds of data, such as text, graphics, image, and bar code. Object content architectures describe the structure and content of each type of data format that can exist in a document or appear in a data stream. Objects can be either data objects or resource objects. | ✅ |
| PTOCA-1-024 | A data object contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data. | ✅ |
| PTOCA-1-025 | A resource object is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them. | ✅ |
| PTOCA-1-026 | All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects that can be individually positioned and manipulated to meet the needs of the presentation application. | ✅ |
| PTOCA-1-027 | The AFPC-defined object content architectures are: | ✅ |
| PTOCA-1-028 | Presentation Text Object Content Architecture (PTOCA)**: A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition. | ✅ |
| PTOCA-1-029 | Image Object Content Architecture (IOCA)**: A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition. | ✅ |
| PTOCA-1-030 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA)**: A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition. | ✅ |
| PTOCA-1-031 | Bar Code Object Content Architecture (BCOCA)**: A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition. | ✅ |
| PTOCA-1-032 | Font Object Content Architecture (FOCA)**: A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document. | ✅ |
| PTOCA-1-033 | Color Management Object Content Architecture (CMOCA)**: A resource architecture used to carry the color management information required to render presentation data. | ✅ |
| PTOCA-1-034 | Metadata Object Content Architecture (MOCA)**: A resource architecture used to carry metadata in an AFP environment. | ✅ |
| PTOCA-1-035 | The MO:DCA and IPDS architectures also support data objects that are not defined by object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures. | ✅ |
| PTOCA-1-036 | In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document. | ✅ |
| PTOCA-1-037 | Figure 3. Presentation Page.** This is an example of a mixed-object page that can be composed in a presentation-system-independent MO:DCA format and printed on an IPDS printer. | ✅ |
| PTOCA-1-038 | Presentation Text Object(s) | ✅ |
| PTOCA-1-039 | Graphics Object | ✅ |
| PTOCA-1-040 | Image Object | ✅ |
| PTOCA-1-041 | Letterhead can be an overlay resource containing text, image, and graphics objects | ✅ |
| PTOCA-1-042 | Object areas can overlap | ✅ |
| PTOCA-2-001 | The Presentation Text object is the data object used in document processing environments for representing text which has been prepared for presentation. Text, as used here, means an ordered string of characters, such as graphic symbols, numbers, and letters, that are suitable for the specific purpose of representing coherent information. Text which has been prepared for presentation has been reduced to a primitive form through explicit specification of the characters and their placement in the presentation space. Control sequences which designate specific control functions may be embedded within the text. These functions extend the primitive form by applying specific characteristics to the text when it is presented. The collection of the graphic characters and control sequences is called Presentation Text, and the object that contains the Presentation Text is called the Presentation Text object. | ✅ |
| PTOCA-2-002 | Presentation Text is associated with the output of text information. A Presentation Text object is the description of Presentation text for a portion of a document, the intended connotation being finished product or formatted output. This output version of text contained within the object is in the form specified by Presentation Text Object Content Architecture (PTOCA) and has been designed for direct output on devices, such as displays or printers. | ✅ |
| PTOCA-2-003 | A Presentation Text object is a device-independent, self-defining representation of a two-dimensional presentation space, called the Presentation Text object space, or object space, which contains the Presentation Text data. The rules of PTOCA specify how the object space is constituted, what the boundaries are for text positioning, what the text content is, and how the text content is to be placed within the object space, using concepts such as sequential order, orientation, and position. | ✅ |
| PTOCA-2-004 | Architecture Note:** Note that when presentation text is processed in a MO:DCA environment where the Presentation Text Data Descriptor (PTD) is carried in the Active Environment Group (AEG) for the page, or when such text is processed in an IPDS environment, the Presentation Text object is bounded by the beginning of the page and the end of the page. This is sometimes called a text major environment. When the PTD is carried in the Object Environment Group (OEG) of a MO:DCA text object, the text object is bounded by the Begin Presentation Text (BPT) and End Presentation Text (EPT) structured fields. For such objects, the PTD in the AEG is ignored. | ✅ |
| PTOCA-2-005 | The Presentation Text object space is defined on the Xp, Yp coordinate system, which is an orthogonal coordinate system based on the fourth quadrant of a standard Cartesian coordinate system. The object space is positioned within the data stream's object area. Coincident with the Xp, Yp coordinate system is the I, Bcoordinate system, which is a translation of the Xp, Yp coordinate system. | ✅ |
| PTOCA-2-006 | The position of the elements in the object space is described in terms of the I, Bcoordinate system. The increasing I-axis is the inline direction, which is normally the reading direction of the text. The increasing B-axis is the baseline direction, which is normally the direction for adding lines of text. | ✅ |
| PTOCA-2-007 | The basic elements of the object are the graphic characters which are identified as code points of a code page. The identification of graphic characters, their relationship to each other, and the relationship of the code point to the graphic character are given by the coded font selected to present the text. | ✅ |
| PTOCA-2-008 | The relationship of the elements to the space they occupy is described in terms of their orientation, starting location, and units of measure. | ✅ |
| PTOCA-2-009 | The positioning of the graphic characters on a line is accomplished by moving the presentation position. Graphic characters may be placed adjacent to one another or positioned anywhere in the object space through the use of control sequences defined by PTOCA. Control sequences have been defined to move the presentation position to another position, to move to the beginning of another line, to adjust the distance between two adjacent characters, to draw lines such as rules, to adjust the distance between lines, to change the font, to specify the color of characters and rules, to overstrike a text field with a specified character, and to underscore a text field. | ✅ |
| PTOCA-2-010 | National Language Support (NLS) is handled in the level of formatting above the Presentation Text object. Font NLS support is provided in the font mapping function in the controlling environment. | ✅ |
| PTOCA-3-001 | Summarizes the concepts that form the basis of PTOCA | ✅ |
| PTOCA-3-002 | Summarizes the data structures in PTOCA | ✅ |
| PTOCA-3-003 | Formatting from revisable text | ✅ |
| PTOCA-3-004 | Transformation from other data streams | ✅ |
| PTOCA-3-005 | Editor or formatting process | ✅ |
| PTOCA-3-006 | Direct generation process | ✅ |
| PTOCA-3-007 | Once created, the object can be presented, revised, or used in a resource such as an overlay. It occupies a given amount of space, the object space, and can be located and oriented in a physical area, the object area. The environment that carries the object is the provider of all external relationships for the object, including the object area. | ✅ |
| PTOCA-3-008 | The object space consists of an array or matrix of addressable positions which identify potential locations at which to place the basic elements of the object, graphic characters. Graphic characters are placed at addressable positions called the presentation positions, rotated relative to a baseline, and have the baseline of a group of characters undergo orientation to various angular positions, such as vertical presentation. These positioning functions are specified by control sequences which are carried along with the graphic characters. The initial positions or beginning values for many of the control sequences are described in a descriptor. | ✅ |
| PTOCA-3-009 | The Presentation Text object is designed to be carried by and become part of a data stream, called the controlling environment. The data stream defines rules by which the object can be carried. Further information about data streams can be found in Appendix A, "MO:DCA Environment" and Appendix B, "IPDS Environment". | ✅ |
| PTOCA-3-010 | The Presentation Text Data Descriptor carries the size, shape, and other special information about the object. The data stream is responsible for providing the proper information to the receiver, but PTOCA specifies a hierarchical method for determining the default values to be used by the receiver if the data stream does not supply the requisite information. | ✅ |
| PTOCA-3-011 | The Presentation Text data contains the code points that identify the graphic characters and the control sequences that specify where and how the graphic characters are to be positioned within the object space. The graphic character code points that represent text information can be specified in a Transparent Data (TRN), a Repeat String (RPS), or a Unicode Complex Text (UCT) control sequence, or they can be specified as free-standing code points that appear between control sequences. Graphic character code points can also be resolved to glyph IDs in a font. These glyph IDs are carried in Glyph Layout Control (GLC) chains for presentation. | ✅ |
| PTOCA-3-012 | Further information about PTOCA data structures is found under "Presentation Text Data" and "Presentation Text Data Descriptor". | ✅ |
| PTOCA-3-013 | The control sequences represent the functional capabilities provided by the Presentation Text object. Since receivers of the object might not all have equivalent capabilities, it is convenient to create subsets, also called subset levels, of the total function that is available. The base is a set of functions required in any environment, including the ability to interpret and validate the control sequences and parameters, and to detect and report exception conditions that are within the PTOCA subsets. | ✅ |
| PTOCA-3-014 | PT1**: Includes a set of relatively primitive control sequences that a receiver is expected to support. | ✅ |
| PTOCA-3-015 | PT2**: Includes all of the PT1 subset plus new control sequences for underscore, overstrike, superscripts and subscripts. | ✅ |
| PTOCA-3-016 | PT3**: Includes all of the PT2 subset plus a new control sequence to enable spot (highlight) colors and process colors for text and rules. | ✅ |
| PTOCA-3-017 | PT4**: Includes new control sequences to support the rendering of complex text. | ✅ |
| PTOCA-3-018 | The intent of subsets is to reduce the number of combinations of supported controls so that interchange between host processors is manageable. For further information about subsets, see Chapter 6, "Compliance with PTOCA", Appendix A, "MO:DCA Environment", and Appendix B, "IPDS Environment". | ✅ |
| PTOCA-3-019 | The Presentation Text object space defines the presentation space into which the presented text characters will fit. The object space is the matrix of addressable positions which are available to the generating process that defined it. This space has no relationship to the physical medium or printed page until it is placed in an object area by a composition process as part of the creation of a page or overlay. The Presentation Text object has no concept of pages, although the composition process may create an entire page from one object. | ✅ |
| PTOCA-3-020 | Positioning of the object space within the object area is accomplished by a mapping within the controlling environment. The object area is the boundary for text presentation by a receiver, and the controlling environment specifies the error recovery action that must occur if any portion of a character or rule violates the object area boundary. The object space is the boundary for the text positioned for presentation. | ✅ |
| PTOCA-3-021 | 1.  **Xp, Yp coordinate system**: Simulates the reader's view of the object space. | ✅ |
| PTOCA-3-022 | 2.  **I, Bcoordinate system**: Indicates the direction of the addition of characters to form words and lines, and the direction of the addition of subsequent lines. | ✅ |
| PTOCA-3-023 | The Xp, Yp coordinate is an orthogonal coordinate system based on the fourth quadrant of a standard Cartesian coordinate system. Both the Xp axis and the Yp axis specify positive values, which is a difference from the Cartesian system where the Y axis in the fourth quadrant specifies negative values. The origin of the coordinate system is in the upper left corner; the positive Xp-axis is from left-to-right, and the positive Yp-axis is from top-to-bottom. The frame of reference for the Xp, Yp coordinate system is provided by the environment's coordinate system for the object area into which the object space is placed. The location of the Xp, Yp coordinate system origin is specified as an offset from the object area's coordinate system origin. | ✅ |
| PTOCA-3-024 | Xp-origin, Yp-origin | ✅ |
| PTOCA-3-025 | Xp-extent, Yp-origin | ✅ |
| PTOCA-3-026 | Xp-extent, Yp-extent | ✅ |
| PTOCA-3-027 | Xp-origin, Yp-extent | ✅ |
| PTOCA-3-028 | The Xp, Yp coordinate system and the I, Bcoordinate system are closely related, as indicated in Figure 5. In fact, the Xp-extent is equal to one of the I, Bcoordinate extents, either the I-extent or the B-extent, and the Yp-extent is equal to the other. Therefore, the angle between the I-axis and B-axis will be identical to the angle between the Xp-axis and the Yp-axis. The Xp, Yp coordinate system describes the spatial viewport for the reader, while the I, Bcoordinate system describes the directions to be used for presentation and for interpretation by the reader of the graphic characters being presented. | ✅ |
| PTOCA-3-029 | Figure 4. Presentation Space Definition** | ✅ |
| PTOCA-3-030 | The I, Bcoordinate system adds a concept of direction to the object space definition. The reader of text comprehends the text by assembling the characters into words or phrases. The direction in which the reader normally constructs the words or phrases is called the inline direction or I-direction. The inline direction for typical Latin-based text is left-to-right, but for languages such as Japanese, or tasks such as labeling graphs, the inline direction may be top-to-bottom or one of the other possible directions. Please see Figure 5. | ✅ |
| PTOCA-3-031 | Figure 5. I, B Coordinate System Examples** | ✅ |
| PTOCA-3-032 | The inline direction is also the direction of increasing positive values of *i* along the I-axis, and prescribes the order in which succeeding characters are processed by a receiver. The maximum value of *i* is the I-extent. | ✅ |
| PTOCA-3-033 | All of the graphic characters placed in the inline direction for a given value of *b* constitute a line. The direction in which successive lines are placed for continued reading of the text is the baseline direction or B-direction. The baseline direction for typical Latin-based text is top-to-bottom, but for other languages, such as Japanese vertical writing, the baseline direction is from right-to-left. The baseline direction is also the direction of increasing positive values of *b* along the B-axis. The maximum value of *b* is the B-extent. | ✅ |
| PTOCA-3-034 | Although the controlling environment, as a carrier of the Presentation Text object, specifies the layout characteristics of the object presentation, the object, as a self-defining portion, provides the measurement units used by the generator in formatting the data. The Presentation Text object provides for both the English and metric systems of measurement. The measurement units for the object are specified in the Presentation Text Data Descriptor or determined by defaults. Measurement units can be specified so that the Xp-axis and the Yp-axis have different resolutions. | ✅ |
| PTOCA-3-035 | Measurement units are used throughout PTOCA to identify the units of measure to be used for such things as extents and offsets along the X and Y axes of a coordinate system. | ✅ |
| PTOCA-3-036 | Unit base**: This value represents the length of the measurement base. It is specified as a one-byte coded value: | ✅ |
| PTOCA-3-037 | X'00': Ten inches | ✅ |
| PTOCA-3-038 | X'01': Ten centimeters | ✅ |
| PTOCA-3-039 | Units per unit base**: This value represents the number of units in the measurement base. It is specified as a two-byte numeric value between 1 and 32,767. | ✅ |
| PTOCA-3-040 | The term *units of measure* is defined as the measurement base value divided by the value of the units per unit base. | ✅ |
| PTOCA-3-041 | For example, if the measurement base is 10 inches and the units per unit base value is 5,000, the units of measure are 10 inches / 5000 or one five-hundredth of an inch. Here are some additional examples: | ✅ |
| PTOCA-3-042 | 1,440 X 1,440 units/inch | 14,400 divisions in 10 inches on both axes | ✅ |
| PTOCA-3-043 | 80 X 77 units/centimeter | 800 divisions in 10 centimeters on Xp and 770 divisions in 10 centimeters on Yp | ✅ |
| PTOCA-3-044 | The size of the object space is specified in measurement units. Each addressable position is one measurement unit away from another addressable position in any direction. That is, a specified measurement unit along the Xp-axis separates the addressable positions in the direction parallel to the Xp-axis, and another specified measurement unit along the Yp-axis separates the addressable positions in the direction parallel to the Yp-axis. This creates an array of addressable positions, each of which has the potential of being designated as the position of a graphic character. | ✅ |
| PTOCA-3-045 | The measurement units thus defined become the measurement units for all linear measurements within the object. The receivers must convert from these measurement units to measurement units for their environment as required, and keep track of rounding errors, making appropriate adjustments as needed to ensure presentation fidelity at a given level of capability. | ✅ |
| PTOCA-3-046 | The measurement units for angular dimensions are degrees. | ✅ |
| PTOCA-3-047 | What character is represented by the code point? | ✅ |
| PTOCA-3-048 | Is the character valid? | ✅ |
| PTOCA-3-049 | What is the shape of the character? | ✅ |
| PTOCA-3-050 | The assignment of code points to characters is done by means of a code page or similar encoding structure such as a character map. A code page or character map can be envisioned as a table which contains pairs of values, where the first element of each pair is the code point and the second element is the identifier of the graphic character. The code page also defines the number of bytes required to represent a character, that is, bytes per code point. | ✅ |
| PTOCA-3-051 | For some font technologies such as the FOCA font technology, the validity of a character may be verified by referring to a graphic character set. A graphic character set is a set of letters, digits, punctuation marks, arithmetic operators, chemical symbols, or other symbols. If the character represented by the code point is not | ✅ |
| PTOCA-3-052 | contained in the graphic character set, then that character is invalid, and another graphic character must be substituted for it. The active coded font designates what graphic character should be substituted in its place. | ✅ |
| PTOCA-3-053 | Style: Bodoni | ✅ |
| PTOCA-3-054 | Size: 10-point | ✅ |
| PTOCA-3-055 | Weight: bold | ✅ |
| PTOCA-3-056 | Other characteristics: italic | ✅ |
| PTOCA-3-057 | This algorithm could consist of a style manual, raster patterns, vector graphic command lists, stroke generation programs, engraved type, or other means of specifying the necessary attributes. The font also specifies the character increment or escapement, that is, the width of the character, and the character reference point or character origin, that is, the point within the graphic pattern which is to be aligned with the presentation position. Within a Presentation Text object, the desired characteristics are specified through a reference to a specific font. The coded font contains the encoding and the shape and metric information which are assigned to each graphic character. The presentation process applies the graphic character code points found within the Presentation Text object to the active coded font in order to determine the presentation characteristics of the characters. The font is managed as a font resource in the controlling environment. A Presentation Text object uses this resource by making reference to the coded font. | ✅ |
| PTOCA-3-058 | The structure and content of FOCA-based fonts is defined by the Font Object Content Architecture (FOCA), which is described in *Font Object Content Architecture Reference*, AFPC-0007. | ✅ |
| PTOCA-3-059 | OpenType Specification Version 1.4 (Microsoft Corporation: October 11, 2002), at http://www.microsoft.com/typography/otspec/default.htm | ✅ |
| PTOCA-3-060 | TrueType Reference Manual (Apple Computer, Inc.: December 18, 2002), at http://developer.apple.com/fonts/TrueType-Reference-Manual | ✅ |
| PTOCA-3-061 | Graphic characters are the basic elements of the Presentation Text object. The control sequences defined by PTOCA deal with the presentation of these graphic characters regarding either their positioning within the object, or some attribute of their presentation, such as color. | ✅ |
| PTOCA-3-062 | PTOCA assumes that the graphic characters are identified by one-byte or multi-byte code points that are defined within the encoding structure for a font. Each graphic character thus identified has a defined character reference point or character origin, a character increment or character escapement, and a character baseline that allows them to be correctly positioned along the baseline in the I-direction of the Presentation Text object. Please see Figure 6. | ✅ |
| PTOCA-3-063 | Figure 6. Horizontal Metrics: TrueType/OpenType Fonts and FOCA Fonts** | ✅ |
| PTOCA-3-064 | The presentation of a graphic character is accomplished by placing the character reference point or character origin of the graphic character at the presentation position. The presentation position is an I, Bcoordinate pair, that is, an addressable position in the object space. The *b* value is fixed for the current baseline, Bc. The current *i* value, the new presentation position, is calculated from the previous *i* value by adding the character increment or character escapement of the graphic character being presented to the previous value of *i*, that is, the previous presentation position. | ✅ |
| PTOCA-3-065 | The presentation position in PTOCA designates a between-the-pels position on a presentation surface, not a pel centerline intersection position. The concept of between-the-pels positioning is especially important for the presentation of rules. Please see "Graphic Character Processing" for more information. | ✅ |
| PTOCA-3-066 | Object generators will determine which characters are to be placed on each line of the object. This does not require that the font be known at generation time in all cases. For fixed pitch fonts where the character increment is a constant value and for fonts utilizing standard metrics, it is possible for any font with the same metrics to be specified without modification to the relative positioning of the graphic characters. | ✅ |
| PTOCA-3-067 | Spacing between the characters can be modified by an adjustment, which is either an increment or a decrement on the character increment values provided for the graphic characters. In addition, the character increment specified for the space code point may be changed to a different value at any time to provide variation in the spacing between words. | ✅ |
| PTOCA-3-068 | Lines of graphic characters are ended by moving the presentation position to the beginning of the next line. This may be done using the positioning control sequences or through the use of a control sequence that causes the baseline increment value and the inline margin to set the presentation position to the next line. | ✅ |
| PTOCA-3-069 | PTOCA is intended to be precise enough to permit multiple products to reproduce the Presentation Text object faithfully. Faithful reproduction includes such aspects as the size and relative positions of graphic characters and strings of graphic characters. The responsibility for faithful reproduction belongs to the process that presents the object. PTOCA is also designed to permit less than faithful reproduction. It is possible to specify exception conditions for which continuation of processing is acceptable. This permits a process that cannot faithfully reproduce the object to continue with its best approximation. If less than faithful reproduction is acceptable for an application, interchange among a larger set of receivers is possible. | ✅ |
| PTOCA-3-070 | The Presentation Text object uses a control sequence to indicate that a function is to be performed. The control sequence consists of the Control Sequence Introducer and a list of parameters. | ✅ |
| PTOCA-3-071 | A one-byte prefix, X'2B' | ✅ |
| PTOCA-3-072 | A one-byte class, X'D3' | ✅ |
| PTOCA-3-073 | A one-byte length | ✅ |
| PTOCA-3-074 | A one-byte function type | ✅ |
| PTOCA-3-075 | Control sequences can be chained together using a chaining convention. Although the first control sequence in a chain has the prefix and class, the remaining chained control sequences do not. Chaining reduces the number of bytes to be handled and removes the need to determine whether the next character is a control sequence or not. Please see Table 4 for a list of PTOCA control sequences, showing both unchained and chained function types. Please see "Control Sequence Chaining" for more information about chaining. | ✅ |
| PTOCA-3-076 | Graphic characters may be grouped together as character strings to eliminate the necessity of checking for the Control Sequence Prefix. This capability is useful for creating strings of repeated characters. An example is the leader dots in a table of contents. The leader dot graphic character occurs only once per line in the object although it is repeated many times at presentation. | ✅ |
| PTOCA-3-077 | In addition this capability, when used in conjunction with chaining, allows the object to be described in terms of two parsing modes: control sequences and graphic characters. These two basic modes can then be optimized separately in an implementation. | ✅ |
| PTOCA-3-078 | Simple line graphic functions have been incorporated to satisfy requirements for figure enclosures, tables, boxes, line drawings, and so on. The capability includes vertical and horizontal rules which may have both the length and the width of the rules specified. | ✅ |
| PTOCA-3-079 | An ability to restrict the presentation of the graphic characters in a controlled way is provided by the suppression function. Suppression is accomplished by marking the text data to be suppressed and specifying | ✅ |
| PTOCA-3-080 | an identifier to allow grouping of the marked text data. All data marked with an active suppression identifier is prevented from being presented when the object is processed. The controlling environment specifies which suppression identifiers are active for the object. | ✅ |
| PTOCA-3-081 | Suppression can be used to create form letters that have portions of the form left blank, or filled in differently, depending on the intended audience of each instance of the letter. | ✅ |
| PTOCA-3-082 | There are times when it is desirable to place graphic characters in other than the customary upright reading position. For example, when labeling a graph, the graphic characters would be placed upright, but the line would be vertical; that is, the I-direction would be top-to-bottom. The I-direction and B-direction determine the orientation of the text, and an I-direction change is called a change of orientation. However, since the upright position is with respect to the I-axis, when reorientation occurs the characters appear to rotate at the same time. To create a vertical effect, such as in a graph, the graphic characters must also be rotated. Please see Figure 7. This figure illustrates changes in orientation with no change in character rotation. | ✅ |
| PTOCA-3-083 | Figure 7. Orientation Examples** | ✅ |
| PTOCA-3-084 | Orientation is specified in degrees in a clockwise direction from the zero-degree starting point. The zero-degree starting point is the I-axis when the I-direction is left-to-right. A change in text orientation may also move the I, Borigin to a different corner of the text object space. Figure 7 shows the location of the I, Borigin for the 8 text orientations. The rotation of the characters is described in terms of angular movement of the character shape with respect to the character baseline, and is specified as part of the selection criteria for fonts. | ✅ |
| PTOCA-3-085 | Controls are provided in PTOCA to accomplish specialized functions. These functions include underscore, overstrike, superscript, and subscript. | ✅ |
| PTOCA-3-086 | This group of control sequences follows a modal concept in that, once started, the function does not terminate until stopped. Each control sequence marks the beginning or the ending of a text field for which the function is invoked. The same control sequence syntax with a non-zero parameter value begins the text field, and with a zero parameter value indicates the end of the field. All other control sequences are valid within these text fields without causing termination of the field. | ✅ |
| PTOCA-3-087 | Underscore is the capability of drawing a line under individual characters or groups of characters. Overstrike is the capability of filling a field with a specific character to provide a marked-out appearance. | ✅ |
| PTOCA-3-088 | The superscript and subscript functions require the ability to move temporarily from the designated baseline by small amounts. The superscript function requires movement in the negative B-direction, that is, above the baseline. The subscript function requires movement in the positive B-direction, that is, below the baseline. The amount of the incremental moves about the baseline is also variable. This allows a sophisticated implementation to provide a wide range of superscript and subscript capability, to be used, for example, when positioning the various parts of mathematical equations. | ✅ |
| PTOCA-3-089 | The Unicode standard recommends that text for all languages be stored in the order that it would be read or spoken, without regard to presentation order. With few exceptions, Latin, Cyrillic, and Greek scripts present text in the same order that data processing systems store the text. These exceptions are ligatures which are combinations of characters and accented characters. Traditionally, computer applications encode these combined characters using one encoding point and one graphic character. As an example, most systems encode Latin small ligature ff as one character. | ✅ |
| PTOCA-3-090 | Complex text languages provide different layouts for the presentation of text and its storage. Bi-directional (BIDI) languages present text normally from right to left; however, some text such as numbers and embedded Latin, Cyrillic, and Greek scripts, are written from left to right. These languages include Arabic, Urdu, Farsi, and Hebrew. | ✅ |
| PTOCA-3-091 | Data Storage order: my address is SUITE B 100 YORK BLVD richmond hill | ✅ |
| PTOCA-3-092 | Presentation order: my address is DVLB KROY 100 B ETIUS richmond hill | ✅ |
| PTOCA-3-093 | Some languages require that characters be presented with different shapes or in a different order than their storage order. Arabic characters can be represented by one of four shapes depending on their position in a word. Arabic characters can also combine to form ligatures. In many south Asian languages, characters may need to be repositioned, reordered, or split, depending on adjacent characters. | ✅ |
| PTOCA-3-094 | Composition applications that need to present Complex Text will use layout engines such as International Components for Unicode (ICU), Windows® Uniscribe, Apple Advanced Typography, Pango, Scribe, or Graphite, to present text. Each engine has a different implementation. Outputs from the engines will differ somewhat. Some engines have better support for some language scripts than others. | ✅ |
| PTOCA-3-095 | PTOCA supports consistent text presentation through the Unicode Complex Text (UCT) control sequence and its complementary supporting glyph run control sequences. PTOCA presents text on a line-by-line basis. This means that applications must provide text boundary analysis. ICU provides iterators that support this type of analysis. These break iterators support determining character, word, line-break, and sentence boundaries. The Unicode Standard Annex #29 provides definitions for these boundaries. The ICU User Guide provides examples of boundary analysis. The Unicode BIDI algorithm works best on paragraphs, so the composition application should apply the algorithm before breaking the text into individual lines. | ✅ |
| PTOCA-3-096 | A font is a collection of images called glyphs. Each glyph in the font has a unique 16-bit id. | ✅ |
| PTOCA-3-097 | There is a mapping from Unicode code points to glyph ids. Some glyphs may not have a mapping. | ✅ |
| PTOCA-3-098 | There is a method to get the width of each glyph. | ✅ |
| PTOCA-3-099 | There is a method to get the position of a control point for each glyph | ✅ |
| PTOCA-3-100 | Client applications perform boundary analysis and determine text direction runs as necessary. They then call the layout engine to produce an array of glyph indices in display order, an array of x, y position pairs for each glyph, and optionally an array that maps each glyph back to the input text array. | ✅ |
| PTOCA-3-101 | The MO:DCA Presentation Text Data Descriptor and Presentation Text Data structured fields carry Presentation Text Objects in MO:DCA documents. The Presentation Text object space provides the coordinate system that allows object generators to position graphic characters and glyphs without error. It is the responsibility of the generator to ensure that it positions the graphic characters and glyphs correctly so that they do not exceed the object space. | ✅ |
| PTOCA-3-102 | If the data contains bi-directional scripts, use the Unicode BIDI algorithm to break the text into directional sequences. The Unicode BIDI algorithm works best with paragraphs, so it must be applied before text is broken into separate lines. | ✅ |
| PTOCA-3-103 | If multiple TrueType/OpenType fonts are used to present the text, composition applications must identify the individual font that will be used for each substring of text to which the layout engine is applied. This step should be performed prior to, or concurrent with, the script analysis that identifies the appropriate layout engine. The ICU Paragraph Layout API provides class interfaces to represent linked fonts with methods to request the individual font and the extent of the text string to be composed. If the entire Unicode text is not rendered with a single font, the subsequent steps must be repeated for each font and substring. | ✅ |
| PTOCA-3-104 | The application will need to determine where line breaks occur because PTOCA constrains text output sequences to individual lines. The appropriate position to break text can vary by language or script. The Unicode Standard Annex #29 provides definitions for character, word, line-break, and sentence boundaries. International Components for Unicode provides break iterators that support this type of analysis. The ICU User Guide provides examples of boundary analysis. | ✅ |
| PTOCA-3-105 | The application will use a layout engine to format text sequences. A text sequence is a run of text that use the same font (e.g. typeface with the same typographic attributes including weight, width, height, posture) where the text accumulates in the same direction. Layout engines normally return: | ✅ |
| PTOCA-3-106 | Arrays of glyph indices | ✅ |
| PTOCA-3-107 | Arrays of glyph positions | ✅ |
| PTOCA-3-108 | The application will then normalize the positions with respect to the origin established for the current text object. | ✅ |
| PTOCA-3-109 | The application will obtain or calculate the Object Identifier (OID) value of the TrueType/OpenType font used to generate the glyph ID values. This value allows presentation devices to verify that they retrieve the glyphs from the exact same font that the application used. See the *Mixed Object Document Content Architecture Reference*, AFPC-0004 for the definition of the algorithm used to calculate the OID of a TrueType/OpenType font. | ✅ |
| PTOCA-3-110 | The application will provide the full font name (FFN) of the TrueType/OpenType font used to generate the glyph ID values. This name provides a human-readable identification of the font and is also used to select a specific font in a font collection when the font OID identifies a collection. | ✅ |
| PTOCA-3-111 | Successful completion of these tasks will result in the application having the presentation data normalized so that it can create the GIR/GAR[/GOR] sequences and the preceding GLC control (the notation "[/GOR]" will be | ✅ |
| PTOCA-3-112 | A Glyph Layout Control (GLC)** which identifies the start of the complex text position requirements sequence for this text run. The GLC specifies: | ✅ |
| PTOCA-3-113 | 1.  the advance along the current baseline caused by processing this GLC chain | ✅ |
| PTOCA-3-114 | 2.  the font OID to identify and validate the font used for the subsequent glyph run control sequences | ✅ |
| PTOCA-3-115 | 3.  the full font name of the font, which is used to select a specific font from a font collection when the font OID identifies a collection | ✅ |
| PTOCA-3-116 | One or more groups of**: | ✅ |
| PTOCA-3-117 | 1.  a Glyph ID Run (GIR) which contains the glyph IDs for this text run | ✅ |
| PTOCA-3-118 | 2.  a Glyph Advance Run (GAR) which contains the advances in the inline direction for each glyph | ✅ |
| PTOCA-3-119 | 3.  an optional Glyph Offset Run (GOR) that provides the offsets of each glyph from the baseline. This control provides the ability to position glyphs such as diacritical marks and accents | ✅ |
| PTOCA-3-120 | An optional Unicode Complex Text (UCT) control sequence** which contains the text encoded in UTF16-BE in data storage order. Use of the UCT is recommended as it provides applications the ability to interpret the sequence of glyphs rendered by the printer. | ✅ |
| PTOCA-3-121 | The maximum length of a PTOCA control sequence limits a single GIR to no more than 125 glyphs. This means that print applications must be prepared to generate multiple GIR/GAR[/GOR] groupings to support long Unicode encoded text strings. | ✅ |
| PTOCA-3-122 | If multiple fonts linked to the currently active font are used to render the text, a GLC chain must be generated for each substring that uses a different linked font. The presentation device will use the FONTOID parameter of the GLC to identify and validate the linked font used for the subsequent glyph run control sequences. If the FONTOID parameter identifies a font collection, the presentation device uses the FFONTNME parameter of the GLC to select the specific font from the collection. | ✅ |
| PTOCA-3-123 | In certain regulatory environments, such as the financial industry, there exists the need to protect customer information such as Personal Identification Numbers (PINs) and Transaction Authentication Numbers (TANs) until presentation time. Typically, this private information must be encrypted, meaning that the code points that make up the character string to be protected cannot appear in the data stream as directly readable code points. Using special algorithms, an encryption/decryption key can be used to turn the character code points into encrypted bytes. | ✅ |
| PTOCA-3-124 | At presentation time, these encrypted bytes can then be turned back into code points in a character string by applying the same encryption/decryption key to algorithmically convert them back into presentation text. To preserve the security of the data stream, the actual encryption/decryption key does not appear in the data stream; key information is passed instead. The decryption device has a lookup table to correlate the key information with the actual encryption/decryption key to be used for decryption. | ✅ |
| PTOCA-3-125 | PTOCA has the ability to identify encrypted bytes that represent this protected information. It also provides a means to set the key information for these encrypted bytes to facilitate decryption into code points in a character string at presentation time. If the decryption should fail, a mechanism is provided in PTOCA to substitute alternate text in the place where the decrypted code points were intended to go. | ✅ |
| PTOCA-3-126 | PTOCA defines exception condition codes that identify the various exception conditions that can arise during the processing of a Presentation Text object. These codes are provided for reference purposes only. PTOCA also specifies a standard action for each exception condition that indicates the recommended action a processor should take when it encounters the exception condition. The manner in which a PTOCA receiver processes exception conditions depends upon the controlling environment. For any PTOCA exception condition the controlling environment may provide its own identifier, which overrides the PTOCA exception condition code. The controlling environment also may provide its own action, which overrides the PTOCA standard action. | ✅ |
| PTOCA-3-127 | graphic characters to be presented | ✅ |
| PTOCA-3-128 | control sequences that position them | ✅ |
| PTOCA-3-129 | modal control sequences that adjust the positions by small amounts | ✅ |
| PTOCA-3-130 | other functions which cause the text output to be presented with differences in appearance | ✅ |
| PTOCA-3-131 | The graphic characters are expected to conform to a font representation so that they can be translated from the code point in the object data to the character in the font. The units of measure for linear displacements are derived from the Presentation Text Data Descriptor or from the hierarchical defaults. | ✅ |
| PTOCA-3-132 | The following pages contain summary descriptions of the PTOCA control sequences. Summary tables are provided following the descriptions. Please see "Control Sequence Detailed Descriptions" for detailed descriptions of syntax, semantics, and pragmatics. | ✅ |
| PTOCA-3-133 | Establishes the baseline and the current presentation position at a new B-axis coordinate, Bcnew, which is a specified number of measurement units from the I-axis. There is no change to the current I-axis coordinate, Ic. | ✅ |
| PTOCA-3-134 | Establishes the current presentation position on the baseline at a new I-axis coordinate, Icnew, which is a specified number of measurement units from the B-axis. There is no change to the current B-axis coordinate, Bc. | ✅ |
| PTOCA-3-135 | Establishes the current presentation position on the baseline with the new I-axis coordinate, Icnew, equal to the inline margin, and the new B-axis coordinate, Bcnew, increased by the amount of the baseline increment from Bc. The baseline increment is established by the Set Baseline Increment control sequence. | ✅ |
| PTOCA-3-136 | Marks the beginning of a field of presentation text, identified by a local identifier (LID), which is not to be presented when the LID is activated in the controlling environment. This control sequence does not alter the effects of other control sequences within it, except that graphic characters and rules are not presented. | ✅ |
| PTOCA-3-137 | Suppression of presentation text by more than one control sequence at a time is not allowed; that is, nesting of suppression control sequences is not allowed. | ✅ |
| PTOCA-3-138 | Draws a line of specified length and specified width in the B-direction from the current presentation position. The location of the current presentation position is unchanged. | ✅ |
| PTOCA-3-139 | Draws a line of specified length and specified width in the I-direction from the current presentation position. The location of the current presentation position is unchanged. | ✅ |
| PTOCA-3-140 | Specifies a sequence of encrypted bytes that must be decrypted into a corresponding character string before standard text processing can be performed. | ✅ |
| PTOCA-3-141 | Marks the end of a field of presentation text, identified by a LID, which is not to be presented when the LID is activated by the controlling environment. | ✅ |
| PTOCA-3-142 | Specifies the displacement of glyphs along the current baseline. | ✅ |
| PTOCA-3-143 | Specifies the IDs of glyphs to be placed along the current baseline. | ✅ |
| PTOCA-3-144 | Specifies control information for the start of one or more glyph runs along the current baseline. | ✅ |
| PTOCA-3-145 | Specifies offsets of glyphs above or below the current baseline. | ✅ |
| PTOCA-3-146 | Specifies a string of bytes that are to be ignored. | ✅ |
| PTOCA-3-147 | Specifies a text field that is to be overstruck with a specified graphic character. The overstrike function is initiated by an OVS control sequence with a non-zero bypass identifier, and is terminated by an OVS control sequence with a zero-value bypass identifier. The fields may not be nested or overlapped. The bypass identifier controls which portions of a line are to be overstruck; this provides for bypassing white space created by AMI, RMI, and space characters. | ✅ |
| PTOCA-3-148 | Establishes the presentation position on the baseline at a new B-axis coordinate, Bcnew, which is a specified number of measurement units from the current B-axis coordinate, Bc. There is no change to the current I-axis coordinate, Ic. | ✅ |
| PTOCA-3-149 | Establishes the presentation position on the baseline at a new I-axis coordinate, Icnew, which is a specified number of measurement units from the current I-axis position, Ic. There is no change to the current B-axis coordinate, Bc. | ✅ |
| PTOCA-3-150 | Specifies a string of characters that are to be repeated until the number of bytes in the graphic characters presented is equal to a specified number of bytes. The string is not checked for control sequences. When the specified number of bytes is equal to the number of bytes in the characters in the data parameter, this control sequence is identical in function to the Transparent Data control sequence. | ✅ |
| PTOCA-3-151 | Specifies the value of the increment to be added to the B-axis coordinate of the current presentation position, Bc, when a Begin Line control sequence is processed. | ✅ |
| PTOCA-3-152 | Specifies a LID to be used as an index into the font map of the controlling environment to determine which coded font, character rotation, and font modification parameters have been selected for use in the object. | ✅ |
| PTOCA-3-153 | Specifies the alternate text used (should the decryption fail) for all Encrypted Data (ENC) control sequences that follow. | ✅ |
| PTOCA-3-154 | Specifies a color value and defines the color space and encoding for that value. Supports spot (highlight) colors and process colors. The specified color value is applied to foreground areas of the text presentation space, that is, characters, rules, and underscores. | ✅ |
| PTOCA-3-155 | Specifies the value to be used as the new I-axis coordinate, Icnew, of the new presentation position after a Begin Line control sequence is processed. The new presentation position is the addressable position nearest to the B-axis at which the character reference point of a graphic character may be placed. | ✅ |
| PTOCA-3-156 | Specifies the increment to be added to or subtracted from the I-axis coordinate of the current presentation position, Ic. The direction parameter indicates whether to add or subtract the increment. If the direction is positive, the increment is added; if negative, the increment is subtracted. This control sequence may be used to compress or expand words for emphasis, improved appearance, or justification. | ✅ |
| PTOCA-3-157 | Specifies the encryption key information used to decrypt data for all Encrypted Data (ENC) control sequences that follow. | ✅ |
| PTOCA-3-158 | Specifies a named color value to be applied to foreground areas of the text presentation space, that is, characters, rules, and underscores. The values of the foreground color parameter serve as indexes into the color-value table found in Table 13. | ✅ |
| PTOCA-3-159 | Establishes the positive I-axis orientation as an angular displacement from the Xp-axis, determining the I-direction. This control sequence also establishes the positive B-axis orientation as an angular displacement from the Xp-axis, determining the B-direction. | ✅ |
| PTOCA-3-160 | The I-axis must be parallel to one of the Xp, Yp coordinate axes and the B-axis must be parallel to the other. The determination of the orientation and direction of the I-axis and B-axis places the origin of the I, Bcoordinate system at one of the corners of the rectangular object space. | ✅ |
| PTOCA-3-161 | Specifies the increment to be used as the character increment for the character identified as the Variable Space Character by the font or by the controlling environment. This increment is added to the I-axis coordinate of the current presentation position, Ic, when the Variable Space Character code point is processed in order to establish the new presentation position. This has no effect on the B-axis coordinate value. | ✅ |
| PTOCA-3-162 | Above**: Direction parameter = 3 | ✅ |
| PTOCA-3-163 | Below**: Direction parameter = 2 | ✅ |
| PTOCA-3-164 | Back to the established baseline**: Direction parameter = 1 | ✅ |
| PTOCA-3-165 | The temporary baseline function is terminated by a TBM control sequence which returns the temporary baseline to the same B-axis coordinate as that of the established baseline. | ✅ |
| PTOCA-3-166 | Specifies a string of characters that are to be presented, but not checked for control sequences. | ✅ |
| PTOCA-3-167 | Specifies a text field that is to be underscored. The underscore function is initiated by an Underscore control sequence with a non-zero bypass identifier, and is terminated by a USC control sequence with a bypass identifier of zero. The fields may not be nested or overlapped. The bypass identifier controls which portions of a line are to be underscored; this provides for bypassing white space created by AMI, RMI, and space characters. | ✅ |
| PTOCA-3-168 | Identifies a sequence of Unicode code points that can be processed as Unicode complex text. The sequence starts with the first byte following the end of the UCT control sequence and ends with the last byte identified by the complex text length parameter in the control sequence. Rendering complex text involves bidirectional (bidi) layout processing and glyph processing. | ✅ |
| PTOCA-3-169 | Table 4. Summary of PTOCA Control Sequences** | ✅ |
| PTOCA-3-170 | Inline Controls** | ✅ |
| PTOCA-3-171 | "Set Inline Margin (SIM)" | X'C0' | X'C1' | ✅ |
| PTOCA-3-172 | "Set Intercharacter Adjustment (SIA)" | X'C2' | X'C3' | ✅ |
| PTOCA-3-173 | "Set Variable Space Character Increment (SVI)" | X'C4' | X'C5' | ✅ |
| PTOCA-3-174 | "Absolute Move Inline (AMI)" | X'C6' | X'C7' | ✅ |
| PTOCA-3-175 | "Relative Move Inline (RMI)" | X'C8' | X'C9' | ✅ |
| PTOCA-3-176 | Baseline Controls** | ✅ |
| PTOCA-3-177 | "Set Baseline Increment (SBI)" | X'D0' | X'D1' | ✅ |
| PTOCA-3-178 | "Absolute Move Baseline (AMB)" | X'D2' | X'D3' | ✅ |
| PTOCA-3-179 | "Relative Move Baseline (RMB)" | X'D4' | X'D5' | ✅ |
| PTOCA-3-180 | "Begin Line (BLN)" | X'D8' | X'D9' | ✅ |
| PTOCA-3-181 | "Set Text Orientation (STO)" | X'F6' | X'F7' | ✅ |
| PTOCA-3-182 | Controls for Data Strings** | ✅ |
| PTOCA-3-183 | "Unicode Complex Text (UCT)" | X'6A' | — | ✅ |
| PTOCA-3-184 | "Glyph Layout Control (GLC)" | X'6D' | — | ✅ |
| PTOCA-3-185 | "Glyph ID Run (GIR)" | X'8B' | — | ✅ |
| PTOCA-3-186 | "Glyph Advance Run (GAR)" | X'8C' | X'8D' | ✅ |
| PTOCA-3-187 | "Glyph Offset Run (GOR)" | X'8E' | X'8F' | ✅ |
| PTOCA-3-188 | "Encrypted Data (ENC)" | X'98' | X'99' | ✅ |
| PTOCA-3-189 | "Set Encrypted Alternate (SEA)" | X'9C' | X'9D' | ✅ |
| PTOCA-3-190 | "Transparent Data (TRN)" | X'DA' | X'DB' | ✅ |
| PTOCA-3-191 | "Repeat String (RPS)" | X'EE' | X'EF' | ✅ |
| PTOCA-3-192 | "No Operation (NOP)" | X'F8' | X'F9' | ✅ |
| PTOCA-3-193 | Controls for Rules** | ✅ |
| PTOCA-3-194 | "Draw I-axis Rule (DIR)" | X'E4' | X'E5' | ✅ |
| PTOCA-3-195 | "Draw B-axis Rule (DBR)" | X'E6' | X'E7' | ✅ |
| PTOCA-3-196 | Character Controls** | ✅ |
| PTOCA-3-197 | "Set Text Color (STC)" | X'74' | X'75' | ✅ |
| PTOCA-3-198 | "Set Extended Text Color (SEC)" | X'80' | X'81' | ✅ |
| PTOCA-3-199 | Table 4 Summary of PTOCA Control Sequences (cont'd.)** | ✅ |
| PTOCA-3-200 | "Set Key Information (SKI)" | X'9A' | X'9B' | ✅ |
| PTOCA-3-201 | "Set Coded Font Local (SCFL)" | X'F0' | X'F1' | ✅ |
| PTOCA-3-202 | "Begin Suppression (BSU)" | X'F2' | X'F3' | ✅ |
| PTOCA-3-203 | "End Suppression (ESU)" | X'F4' | X'F5' | ✅ |
| PTOCA-3-204 | Field Controls** | ✅ |
| PTOCA-3-205 | "Overstrike (OVS)" | X'72' | X'73' | ✅ |
| PTOCA-3-206 | "Underscore (USC)" | X'76' | X'77' | ✅ |
| PTOCA-3-207 | "Temporary Baseline Move (TBM)" | X'78' | X'79' | ✅ |
| PTOCA-3-208 | Table 5. Explanation of Symbols Used in Tables** | ✅ |
| PTOCA-3-209 | Ic | Current inline addressable position | ✅ |
| PTOCA-3-210 | Bc | Current baseline addressable position | ✅ |
| PTOCA-3-211 | Icnew | New current inline addressable position | ✅ |
| PTOCA-3-212 | Bcnew | New current baseline addressable position | ✅ |
| PTOCA-3-213 | Io | Inline addressable position at origin | ✅ |
| PTOCA-3-214 | Bo | Baseline addressable position at origin | ✅ |
| PTOCA-3-215 | Ih | Initial I-axis coordinate established by data stream | ✅ |
| PTOCA-3-216 | Bh | Initial B-axis coordinate established by data stream | ✅ |
| PTOCA-3-217 | Imargin | I-axis coordinate at left margin | ✅ |
| PTOCA-3-218 | Best | Established B-axis coordinate | ✅ |
| PTOCA-3-219 | CI | Character increment specified by font resource | ✅ |
| PTOCA-3-220 | ADJSTMNT | Intercharacter adjustment (increment or decrement) | ✅ |
| PTOCA-3-221 | VSI | Variable Space Character increment | ✅ |
| PTOCA-3-222 | CHAR | Any character with CI > 0 (non-null character) | ✅ |
| PTOCA-3-223 | NULLCHAR | Any character with CI = 0 (null character) | ✅ |
| PTOCA-3-224 | Table 6. Summary of Directive Control Sequences** | ✅ |
| PTOCA-3-225 | Absolute Move Baseline | AMB | DSPLCMNT | Bcnew = Bo + DSPLCMNT | ✅ |
| PTOCA-3-226 | Absolute Move Inline | AMI | DSPLCMNT | Icnew = Io + DSPLCMNT | ✅ |
| PTOCA-3-227 | Begin Line | BLN | None | Icnew = Imargin, Bcnew = Bc + INCRMENT, Best = Best + INCRMENT | ✅ |
| PTOCA-3-228 | Begin Suppression | BSU | LID | Do not present text following this control through next ESU with same LID, if LID is active at controlling environment level. | ✅ |
| PTOCA-3-229 | Draw B-Axis Rule | DBR | RLENGTH, RWIDTH | Draw rule in B-direction from Bc to Bc + RLENGTH. Rule width = RWIDTH. Ic and Bc are unchanged. | ✅ |
| PTOCA-3-230 | Draw I-Axis Rule | DIR | RLENGTH, RWIDTH | Draw rule in I-direction from Ic to Ic + RLENGTH. Rule width = RWIDTH. Ic and Bc are unchanged. | ✅ |
| PTOCA-3-231 | Encrypted Data | ENC | ENCDATA | Encrypted bytes that must be decrypted into text characters for standard text processing. | ✅ |
| PTOCA-3-232 | End Suppression | ESU | LID | End suppression of characters if same LID as preceding BSU. | ✅ |
| PTOCA-3-233 | Table 6 Summary of Directive Control Sequences (cont'd.)** | ✅ |
| PTOCA-3-234 | Glyph Advance Run | GAR | ADVANCE | Specifies the displacement of glyphs along the current baseline. | ✅ |
| PTOCA-3-235 | Glyph ID Run | GIR | GLYPHID | Specifies the IDs of glyphs to be placed along the current baseline. | ✅ |
| PTOCA-3-236 | Glyph Layout Control | GLC | IADVNCE, OIDLGTH, FFNLGTH, FONTOID, FFONTNME | Specifies control information for the start of one or more glyph runs along the current baseline. | ✅ |
| PTOCA-3-237 | Glyph Offset Run | GOR | OFFSET | Specifies offsets of glyphs above or below the current baseline. | ✅ |
| PTOCA-3-238 | No Operation | NOP | IGNDATA | Ignore bytes IGNDATA. No change to Ic or Bc. | ✅ |
| PTOCA-3-239 | Relative Move Baseline | RMB | INCRMENT | Bcnew = Bc + INCRMENT | ✅ |
| PTOCA-3-240 | Relative Move Inline | RMI | INCRMENT | Icnew = Ic + INCRMENT | ✅ |
| PTOCA-3-241 | Repeat String | RPS | RLENGTH, RPTDATA | Repeat RPTDATA until RLENGTH bytes from RPTDATA have been presented. | ✅ |
| PTOCA-3-242 | Transparent Data | TRN | TRNDATA | Process all code points in TRNDATA as characters. | ✅ |
| PTOCA-3-243 | Unicode Complex Text | UCT | UCTVERS, CTLNGTH, CTFLGS, BIDICT, GLYPHCT, ALTIPOS | Process the next CTLNGTH Unicode code points as complex text. | ✅ |
| PTOCA-3-244 | Table 7. Summary of Modal Control Sequences** | ✅ |
| PTOCA-3-245 | Set Baseline Increment | SBI | INCRMENT | Upon execution of BLN, Bcnew = Bc + INCRMENT. | ✅ |
| PTOCA-3-246 | Set Coded Font Local | SCFL | LID | Establish active font, character rotation, and font modification parameters. | ✅ |
| PTOCA-3-247 | Set Encrypted Alternate | SEA | ALTTEXT | Sets the alternate text to be used if the decryption of encrypted bytes in the Encrypted Data (ENC) control sequences that follow should fail. | ✅ |
| PTOCA-3-248 | Set Extended Text Color | SEC | COLSPCE, COLSIZE1, COLSIZE2, COLSIZE3, COLSIZE4, COLVALUE | Set process color and highlight color for text, rules, and underscores. | ✅ |
| PTOCA-3-249 | Table 7 Summary of Modal Control Sequences (cont'd.)** | ✅ |
| PTOCA-3-250 | Set Intercharacter Adjustment | SIA | ADJSTMNT, DIRCTION | If current character follows another character, Icnew = Ic +/- ADJSTMNT. Present character: Icnew = Ic + character increment. DIRCTION = 0 means ADJSTMNT is positive, DIRCTION = 1 means ADJSTMNT is negative. | ✅ |
| PTOCA-3-251 | Set Inline Margin | SIM | DSPLCMNT | Upon execution of BLN, Icnew = Io + DSPLCMNT = Imargin. | ✅ |
| PTOCA-3-252 | Set Key Information | SKI | KEYINFO | Sets the encryption key information used to decrypt data for all Encrypted Data (ENC) control sequences that follow. | ✅ |
| PTOCA-3-253 | Set Text Color | STC | FRGCOLOR | Set named color for text, rules, and underscores. | ✅ |
| PTOCA-3-254 | Set Text Orientation | STO | IORNTION, BORNTION | Establish angle of I-axis and B-axis with respect to Xp-axis. | ✅ |
| PTOCA-3-255 | Set Variable Space Character Increment | SVI | INCRMENT | Establish character increment of Variable Space Character. | ✅ |
| PTOCA-3-256 | Table 8. Summary of Field Control Sequences** | ✅ |
| PTOCA-3-257 | Overstrike | OVS | BYPSIDEN, OVERCHAR | Overstrike following text with OVERCHAR. BYPSIDEN controls overstrike of white space. BYPSIDEN = 0 terminates. Baseline reference is Bc. | ✅ |
| PTOCA-3-258 | Underscore | USC | BYPSIDEN | Underscore following text. BYPSIDEN controls underscore of white space. BYPSIDEN = 0 terminates. Baseline reference is Best. | ✅ |
| PTOCA-3-259 | Temporary Baseline Move | TBM | DIRCTION, PRECSION, INCRMENT | Create temporary baseline at Bcnew = Bc + INCRMENT. Best is unchanged. | ✅ |
| PTOCA-3-260 | Unit base | ✅ |
| PTOCA-3-261 | Xp-units per unit base | ✅ |
| PTOCA-3-262 | Yp-units per unit base | ✅ |
| PTOCA-3-263 | Xp-extent of the presentation space | ✅ |
| PTOCA-3-264 | Yp-extent of the presentation space | ✅ |
| PTOCA-3-265 | Initial text conditions | ✅ |
| PTOCA-3-266 | The initial text conditions are values provided by the Presentation Text Data Descriptor to initialize the modal parameters of the control sequences. Modal control sequences typically are characterized by the word *set* in the name of the control sequence. Modal parameters are identified as such in their semantic descriptions. | ✅ |
| PTOCA-3-267 | Baseline increment | ✅ |
| PTOCA-3-268 | Extended text color | ✅ |
| PTOCA-3-269 | Coded font local ID | ✅ |
| PTOCA-3-270 | Initial baseline coordinate | ✅ |
| PTOCA-3-271 | Initial inline coordinate | ✅ |
| PTOCA-3-272 | Inline margin | ✅ |
| PTOCA-3-273 | Intercharacter adjustment | ✅ |
| PTOCA-3-274 | Text color | ✅ |
| PTOCA-3-275 | Text orientation | ✅ |
| PTOCA-3-276 | The following pages contain summary descriptions of the initial text conditions. Please refer to "Objects" for detailed descriptions of semantics and pragmatics. Also see the corresponding control sequence, if appropriate, for additional information. | ✅ |
| PTOCA-3-277 | Specifies the value to be used for the increment parameter of the Set Baseline Increment control sequence. This increment represents the number of measurement units to be added to the B-axis coordinate of the current presentation position, Bc, when a Begin Line control sequence is processed. The current I-axis coordinate, Ic, is unchanged. The default value is the Default Baseline Increment associated with the default coded font of the device. | ✅ |
| PTOCA-3-278 | Specifies the value to be used as the LID in the Set Coded Font Local control sequence. This LID represents an index into a font map of the controlling environment used in the determination of which font, character rotation, and font modification parameters have been selected for use in the object. The default value is the LID of the default font of the device. | ✅ |
| PTOCA-3-279 | Specifies a foreground spot (highlight) color or process color to be used to present graphic characters, rules, and underscores. | ✅ |
| PTOCA-3-280 | Specifies the value of the current presentation position B-axis coordinate, Bc. This value represents the displacement in the B-direction from the I-axis for the initial position for presentation of graphic characters or processing of control sequences. The default value is device-dependent. | ✅ |
| PTOCA-3-281 | Specifies the value of the current presentation position I-axis coordinate, Ic. This value represents the displacement in the I-direction from the B-axis for the initial position for presentation of graphic characters or processing of control sequences. The default value is zero. | ✅ |
| PTOCA-3-282 | Specifies the value to be used for the displacement parameter of the Set Inline Margin control sequence. This value represents the I-axis coordinate of the presentation position nearest to the B-axis after a Begin Line control sequence is processed. The default value is zero. | ✅ |
| PTOCA-3-283 | Specifies the value to be used for the adjustment parameter of the Set Intercharacter Adjustment control sequence. This value represents the number of measurement units by which the I-axis coordinate of the current presentation position is adjusted when the SIA control sequence is processed. The direction of the adjustment is determined by the direction parameter. If the direction is positive, the adjustment is added; if negative, the adjustment is subtracted. The default value is zero for both the adjustment parameter and the direction parameter. | ✅ |
| PTOCA-3-284 | Specifies a foreground named color value to be used to present text, rules, and underscores. A foreground color parameter value represents an index into the color-value table in Table 13. The default value is X'FF07'. | ✅ |
| PTOCA-3-285 | Specifies the angular displacement values to be used for the I-axis orientation and the B-axis orientation parameters of the Set Text Orientation control sequence. The I-axis value represents the positive I-axis orientation as an angular displacement from the Xp-axis, and the resultant I-direction. The B-axis value represents the positive B-axis orientation as an angular displacement from the Xp-axis, and the resultant B-direction. The default value for the I-axis is X'0000', that is, zero degrees. The default value for the B-axis is X'2D00', that is, 90 degrees. | ✅ |
| PTOCA-4-001 | Describes the role of parameters | ✅ |
| PTOCA-4-002 | Explains documentation conventions used in this chapter | ✅ |
| PTOCA-4-003 | Provides a detailed description of the control sequence | ✅ |
| PTOCA-4-004 | Explains how graphic characters are processed | ✅ |
| PTOCA-4-005 | Provides a detailed description of the Presentation Text data | ✅ |
| PTOCA-4-006 | Provides a detailed description of the Presentation Text Data Descriptor | ✅ |
| PTOCA-4-007 | current values each time presentation of a Presentation Text object begins. | ✅ |
| PTOCA-4-008 | . A receiver may determine the maximum | ✅ |
| PTOCA-4-009 | . Two bytes considered together are sixteen bits, called bits 0 - 15. Bit 0 is in the high-order | ✅ |
| PTOCA-4-010 | . The first byte received is the high-order byte, that is, bits 0 - 7. The | ✅ |
| PTOCA-4-011 | the same parameter in the current object according to the hierarchy. | ✅ |
| PTOCA-4-012 | Table 9. Parameter Specification Hierarchy** | ✅ |
| PTOCA-4-013 | Measurement Units | | X | Receiver dependent | ✅ |
| PTOCA-4-014 | Size | | X | Receiver dependent | ✅ |
| PTOCA-4-015 | Baseline Increment | X | X | Receiver dependent, should be based on the coded font | ✅ |
| PTOCA-4-016 | Suppression identifier | | | None | ✅ |
| PTOCA-4-017 | Coded Font Local ID | X | X | Receiver dependent | ✅ |
| PTOCA-4-018 | Intercharacter Adjustment | X | X | 0 | ✅ |
| PTOCA-4-019 | Intercharacter Direction | X | X | 0 | ✅ |
| PTOCA-4-020 | Inline Margin | X | X | 0 | ✅ |
| PTOCA-4-021 | Initial Baseline Coordinate | | X | Receiver dependent | ✅ |
| PTOCA-4-022 | Initial Inline Coordinate | | X | 0 | ✅ |
| PTOCA-4-023 | Foreground Color | X | X | X'FF07' | ✅ |
| PTOCA-4-024 | Text Orientation | X | X | 0,90 | ✅ |
| PTOCA-4-025 | Rule Length | X | | None | ✅ |
| PTOCA-4-026 | Rule Width | X | | Receiver dependent | ✅ |
| PTOCA-4-027 | Overstrike Bypass Identifiers | X | | X'01' | ✅ |
| PTOCA-4-028 | Overstrike Character | X | | Coded font dependent | ✅ |
| PTOCA-4-029 | Temporary Baseline Increment | X | | ½ the Baseline Increment | ✅ |
| PTOCA-4-030 | Temporary Baseline Direction | X | | 0 | ✅ |
| PTOCA-4-031 | Temporary Baseline Precision | X | | 0 | ✅ |
| PTOCA-4-032 | Underscore Bypass Identifiers | X | | X'01' | ✅ |
| PTOCA-4-033 | Variable Space Character Code | | | Coded font dependent | ✅ |
| PTOCA-4-034 | Variable Space Character Increment | X | | Coded font dependent | ✅ |
| PTOCA-4-035 | Alternate Text | X | | None | ✅ |
| PTOCA-4-036 | Key Information | X | | None | ✅ |
| PTOCA-4-037 | Key Information X None | ✅ |
| PTOCA-4-038 | object supports only one type of control sequence. | ✅ |
| PTOCA-4-039 | A one-byte prefix, X'2B' | ✅ |
| PTOCA-4-040 | A one-byte class, X'D3' | ✅ |
| PTOCA-4-041 | A one-byte length | ✅ |
| PTOCA-4-042 | A one-byte function type | ✅ |
| PTOCA-4-043 | A one-byte length | ✅ |
| PTOCA-4-044 | A one-byte function type | ✅ |
| PTOCA-4-045 | chained or unchained. | ✅ |
| PTOCA-4-046 | types, both unchained and chained. | ✅ |
| PTOCA-4-047 | A previous modal control sequence in the current Presentation Text object | ✅ |
| PTOCA-4-048 | Externally to the Presentation Text object | ✅ |
| PTOCA-4-049 | By default | ✅ |
| PTOCA-4-050 | of the text object space can be set to any value in the allowed range. | ✅ |
| PTOCA-4-051 | hierarchical default value for that parameter. The hierarchical default values are listed in Table 9. | ✅ |
| PTOCA-4-052 | Syntax:** A Control Sequence Introducer can appear only at the beginning of a control sequence. | ✅ |
| PTOCA-4-053 | An unchained Control Sequence Introducer has the following format: | ✅ |
| PTOCA-4-054 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-055 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-056 | 2 | UBIN | LENGTH | 2–255 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-057 | 3 | CODE | TYPE | X'00' – X'FE' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-058 | A chained Control Sequence Introducer has the following format: | ✅ |
| PTOCA-4-059 | 0 | UBIN | LENGTH | 2–255 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-060 | 1 | CODE | TYPE | X'00' – X'FE' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-061 | Pragmatics: A Control Sequence Introducer immediately precedes the data portion of a control sequence. | ✅ |
| PTOCA-4-062 | This mode of interpretation continues until the control sequence or control sequence chain is terminated. | ✅ |
| PTOCA-4-063 | ignore the control sequence. | ✅ |
| PTOCA-4-064 | If a mandatory parameter is missing from the control sequence, the standard action is to ignore the control | ✅ |
| PTOCA-4-065 | If the control sequence is longer than specified, the standard action is to ignore only the undefined | ✅ |
| PTOCA-4-066 | , is one measurement unit beyond the I-extent. | ✅ |
| PTOCA-4-067 | A font may contain smaller graphic characters which are designed to appear as superscripts or subscripts. | ✅ |
| PTOCA-4-068 | A font may consist entirely of graphic characters which are designed so that they will appear as superscripts | ✅ |
| PTOCA-4-069 | A font may be designed without smaller characters for use as superscripts and subscripts. With a font like | ✅ |
| PTOCA-4-070 | Nested superscripts and subscripts, that is, superscripts and subscripts of superscripts or subscripts | ✅ |
| PTOCA-4-071 | Mathematical symbols of different sizes, for example, integrals, sums, products, and exponents | ✅ |
| PTOCA-4-072 | Specially stylized superscripts or subscripts, such as italic characters and Greek letters | ✅ |
| PTOCA-4-073 | Table 10. Equations for Graphic Character Presentation** | ✅ |
| PTOCA-4-074 | Enter Object** | Use initial values from data stream | $I_c$ = $I_h$, $B_c$ = $B_h$ | ✅ |
| PTOCA-4-075 | Or use default initial values | $I_c$ = $I_o$ = 0, $B_c$ = $B_o$ = 0 | ✅ |
| PTOCA-4-076 | Present character, general case** | $B_c$ = $B_o$ = 0 | ✅ |
| PTOCA-4-077 | Present variable space character | $I_{cnew}$ = $I_c$ + VSI | ✅ |
| PTOCA-4-078 | Present graphic character | $I_{cnew}$ = $I_c$ + CI | ✅ |
| PTOCA-4-079 | Present character, specific cases** | **Following incrementing character:** | ✅ |
| PTOCA-4-080 | Present first character (incrementing) | $I_{cnew}$ = $I_c$ +/- ADJSTMNT | ✅ |
| PTOCA-4-081 | Followed by second character (incrementing) | Present first character (incrementing): $I_{cnew}$ = $I_c$ + CI | ✅ |
| PTOCA-4-082 | $I_{cnew}$ = $I_c$ +/- ADJSTMNT | ✅ |
| PTOCA-4-083 | Present second character (incrementing): $I_{cnew}$ = $I_c$ + CI | ✅ |
| PTOCA-4-084 | Following incrementing character:** | ✅ |
| PTOCA-4-085 | Present first character (non-incrementing) | $I_{cnew}$ = $I_c$ +/- ADJSTMNT | ✅ |
| PTOCA-4-086 | Present second character (incrementing) | Present first character (non-incrementing): $I_{cnew}$ = $I_c$ | ✅ |
| PTOCA-4-087 | Present second character (incrementing): $I_{cnew}$ = $I_c$ + CI | ✅ |
| PTOCA-4-088 | Following incrementing character:** | ✅ |
| PTOCA-4-089 | Present Variable Space Character | $I_{cnew}$ = $I_c$ + VSI | ✅ |
| PTOCA-4-090 | Followed by first character (incrementing) | Present first character (incrementing): $I_{cnew}$ = $I_c$ + CI | ✅ |
| PTOCA-4-091 | This table shows how the $I_{cnew}$ coordinate is modified during the presentation of characters. | ✅ |
| PTOCA-4-092 | Figure 9. Presentation Position with Intercharacter Adjustment | ✅ |
| PTOCA-4-093 | Figure 11. Between-the-Pels Illustrations for Baseline Rules | ✅ |
| PTOCA-4-094 | EC-1E01...A mandatory parameter is missing. | ✅ |
| PTOCA-4-095 | EC-1C01...The control sequence class is invalid. | ✅ |
| PTOCA-4-096 | EC-1E01...The control sequence length is not valid. | ✅ |
| PTOCA-4-097 | EC-1E01...An optional parameter is partially missing. | ✅ |
| PTOCA-4-098 | EC-0001...The control sequence function type is invalid. | ✅ |
| PTOCA-4-099 | EC-2100...The Presentation Text object contains a graphic character code point that is not defined in the | ✅ |
| PTOCA-4-100 | EC-0103...An attempt is made to present a character or a rule outside of the object space. | ✅ |
| PTOCA-4-101 | The V ariable Space Character, which is normally X'40' for EBCDIC single-byte fonts, X'20' for ASCII single- | ✅ |
| PTOCA-4-102 | The Control Sequence Prefix, which is X'2B'. | ✅ |
| PTOCA-4-103 | sequences be chained. | ✅ |
| PTOCA-4-104 | Offset refers to the position of a parameter. | ✅ |
| PTOCA-4-105 | Type denotes the syntax of the parameter by data type. Please see “Parameter Data Types” for | ✅ |
| PTOCA-4-106 | Name is a short field name suitable for coding. | ✅ |
| PTOCA-4-107 | Range denotes the smallest and largest valid values that may occur in this field. Negative numbers are | ✅ |
| PTOCA-4-108 | Meaning gives an explanatory or descriptive name for the parameter. | ✅ |
| PTOCA-4-109 | M/O refers to the parameter's appearance in the control sequence. O means that the parameter is optional. | ✅ |
| PTOCA-4-110 | Def refers to the existence of a PTOC A default value for the parameter which is to be used when no explicit | ✅ |
| PTOCA-4-111 | Ind specifies the validity of the default indicator. Y means that the default indicator is valid. N means that the | ✅ |
| PTOCA-4-112 | 1. Calculate the number of actual supported units per inch (X) as follows: | ✅ |
| PTOCA-4-113 | If the measurement base is ten inches, divide the number of supported units per ten inches by 10. | ✅ |
| PTOCA-4-114 | If the measurement base is ten centimeters, multiply the number of supported units per ten centimeters | ✅ |
| PTOCA-4-115 | 2. Calculate the ratio of actual supported units per inch (X) to the assumed 1,440 units per inch as follows: | ✅ |
| PTOCA-4-116 | Divide (X) by 1,440, yielding the ratio (Y). | ✅ |
| PTOCA-4-117 | 3. Calculate the new range value in the supported measurement units as follows: | ✅ |
| PTOCA-4-118 | Convert the old range value to base ten; then multiply it by the ratio (Y). | ✅ |
| PTOCA-4-119 | Round to the nearest integer. | ✅ |
| PTOCA-4-120 | 1. Supported units per inch = 2,400 ÷ 10 = 240 | ✅ |
| PTOCA-4-121 | 2. Ratio of supported units per inch to 1,440 units per inch = 240 ÷ 1,440 = 1/6 | ✅ |
| PTOCA-4-122 | 3. Range at 2,400 units per 10 inches: | ✅ |
| PTOCA-4-123 | The Absolute Move Baseline control sequence moves the baseline coordinate relative to the I-axis. | ✅ |
| PTOCA-4-124 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-125 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-126 | 2 | UBIN | LENGTH | 4 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-127 | 3 | CODE | TYPE | X'D2' – X'D3' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-128 | 4–5 | SBIN | DSPLCMNT | X'0000' – X'7FFF' | Displacement | M | N | N | ✅ |
| PTOCA-4-129 | stream documentation. | ✅ |
| PTOCA-4-130 | EC-1301...The value of DSPLCMNT is not supported or is not within the range specified by PTOCA. | ✅ |
| PTOCA-4-131 | EC-0103...The presentation position is outside the object space and presentation is attempted. | ✅ |
| PTOCA-4-132 | EC-1403...Negative DSPLCMNT is not valid. | ✅ |
| PTOCA-4-133 | The Absolute Move Inline control sequence moves the inline coordinate position relative to the B-axis. | ✅ |
| PTOCA-4-134 | 0 | CODE | PREFIX | X'2B' | Control sequence prefix | M | N | N | ✅ |
| PTOCA-4-135 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-136 | 2 | UBIN | LENGTH | 4 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-137 | 3 | CODE | TYPE | X'C6' – X'C7' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-138 | 4–5 | SBIN | DSPLCMNT | X'0000' – X'7FFF' | Displacement | M | N | N | ✅ |
| PTOCA-4-139 | stream documentation. | ✅ |
| PTOCA-4-140 | for the character being presented. Then presentation of characters may resume. | ✅ |
| PTOCA-4-141 | EC-1401...The value of DSPLCMNT is not supported or is not within the range specified by PTOCA. | ✅ |
| PTOCA-4-142 | EC-0103...The presentation position is outside the object space and presentation is attempted. | ✅ |
| PTOCA-4-143 | The Begin Line control sequence begins a new line. | ✅ |
| PTOCA-4-144 | 0 | CODE | PREFIX | X'2B' | Control sequence prefix | M | N | N | ✅ |
| PTOCA-4-145 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-146 | 2 | UBIN | LENGTH | 2 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-147 | 3 | CODE | TYPE | X'D8' – X'D9' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-148 | suppressed from the visible output. | ✅ |
| PTOCA-4-149 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-150 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-151 | 2 | UBIN | LENGTH | 3 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-152 | 3 | CODE | TYPE | X'F2' – X'F3' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-153 | 4 | CODE | LID | X'00' – X'FF' | Suppression identifier | M | N | N | ✅ |
| PTOCA-4-154 | 9801 exists. The standard action in this case is to ignore the control sequence. Please see the Pragmatics | ✅ |
| PTOCA-4-155 | corresponding End Suppression control sequence are processed as no-operations. | ✅ |
| PTOCA-4-156 | Nesting of Begin and End Suppression control sequences is not allowed. If a second Begin Suppression | ✅ |
| PTOCA-4-157 | If a Begin Suppression control sequence is followed by an End Suppression control sequence that has a | ✅ |
| PTOCA-4-158 | If an End Suppression control sequence occurs in a Presentation Text object without a previous valid Begin | ✅ |
| PTOCA-4-159 | If a Begin Suppression control sequence appears in a Presentation Text object with no corresponding End | ✅ |
| PTOCA-4-160 | object. That is, all of the data following the Begin Suppression control sequence is suppressed. | ✅ |
| PTOCA-4-161 | EC-9801...The value of the LID is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-162 | EC-0601...Nesting exists. | ✅ |
| PTOCA-4-163 | EC-0201...The values of the LIDs do not match within a BSU, ESU pair. | ✅ |
| PTOCA-4-164 | EC-0201...An ESU control sequence occurs without a preceding BSU control sequence. | ✅ |
| PTOCA-4-165 | EC-0401...A BSU control sequence occurs without a succeeding ESU control sequence. | ✅ |
| PTOCA-4-166 | The Draw B-axis Rule control sequence draws a rule in the B-direction. | ✅ |
| PTOCA-4-167 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-168 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-169 | 2 | UBIN | LENGTH | 4, 7 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-170 | 3 | CODE | TYPE | X'E6' – X'E7' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-171 | 4–5 | SBIN | RLENGTH | X'8000' – X'7FFF' | Length | M | N | N | ✅ |
| PTOCA-4-172 | 6–8 | SBIN | RWIDTH | See Semantics section | Width | O | Y | Y | ✅ |
| PTOCA-4-173 | conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-174 | A is a two-byte binary number from -32,768 through +32,767 | ✅ |
| PTOCA-4-175 | B is a one-byte binary fraction with bit 0 denoting 1/2 measurement unit, bit 1 denoting 1/4 measurement | ✅ |
| PTOCA-4-176 | For extensions in the B-direction, truncate the rule at the object space boundary. Receivers using character | ✅ |
| PTOCA-4-177 | For extensions in the I-direction, limit presentation to the portion of the rule that can be presented within the | ✅ |
| PTOCA-4-178 | For further information on rule positioning, please refer to Figure 11. | ✅ |
| PTOCA-4-179 | EC-0103...A parameter value will cause the rule to be outside the object space. | ✅ |
| PTOCA-4-180 | EC-1E01...RLENGTH is missing. | ✅ |
| PTOCA-4-181 | EC-8002...The value for RWIDTH is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-182 | EC-8202...The value for RLENGTH is not supported or is not in the range specified by PTOCA; or, a | ✅ |
| PTOCA-4-183 | The Draw I-axis Rule control sequence draws a rule in the I-direction. | ✅ |
| PTOCA-4-184 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-185 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-186 | 2 | UBIN | LENGTH | 4, 7 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-187 | 3 | CODE | TYPE | X'E4' – X'E5' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-188 | 4–5 | SBIN | RLENGTH | X'8000' – X'7FFF' | Length | M | N | N | ✅ |
| PTOCA-4-189 | 6–8 | SBIN | RWIDTH | See Semantics section | Width | O | Y | Y | ✅ |
| PTOCA-4-190 | conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-191 | A is a two-byte binary number from -32,768 through +32,767 | ✅ |
| PTOCA-4-192 | B is a one-byte binary fraction with bit 0 denoting 1/2 measurement unit, bit 1 denoting 1/4 measurement | ✅ |
| PTOCA-4-193 | stream documentation. | ✅ |
| PTOCA-4-194 | 0103 exists. The standard actions in this case are the following: | ✅ |
| PTOCA-4-195 | For extensions in the I-direction, truncate the rule at the object space boundary. Receivers using character | ✅ |
| PTOCA-4-196 | For extensions in the B-direction, presentation is limited to the portion of the rule that can be presented within | ✅ |
| PTOCA-4-197 | For further information on rule positioning, please refer to Figure 10. | ✅ |
| PTOCA-4-198 | EC-0103...A parameter value will cause the rule to be outside the object space. | ✅ |
| PTOCA-4-199 | EC-1E01...RLENGTH is missing. | ✅ |
| PTOCA-4-200 | EC-8002...The value for RWIDTH is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-201 | EC-8202...The value for RLENGTH is not supported or is not in the range specified by PTOCA; or, a | ✅ |
| PTOCA-4-202 | into text strings for standard text processing. This data is not scanned for embedded control sequences. | ✅ |
| PTOCA-4-203 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-204 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-205 | 2 | UBIN | LENGTH | 7–255 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-206 | 3 | CODE | TYPE | X'98' – X'99' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-207 | 4-7 | | | | Reserved; should be zero | M | N | N | ✅ |
| PTOCA-4-208 | 8-256 | UNDF | ENCDATA | Not applicable | Encrypted bytes to be decrypted | M | N | N | ✅ |
| PTOCA-4-209 | indicator, but X'F....F' is valid. | ✅ |
| PTOCA-4-210 | 1. The ENCDATA can be decrypted into printable code points | ✅ |
| PTOCA-4-211 | 2. The ENCDATA cannot be decrypted | ✅ |
| PTOCA-4-212 | 3. The ENCDATA cannot be decrypted and alternate text is used | ✅ |
| PTOCA-4-213 | remainder of the code points in the alternate text and continue processing. | ✅ |
| PTOCA-4-214 | EC-0103...The decrypted character string or alternate text will cause part of a character's character box to be | ✅ |
| PTOCA-4-215 | EC-1A01...The length of the decrypted character string or alternate text is an odd number, but a double-byte | ✅ |
| PTOCA-4-216 | EC-1A03...Invalid Unicode data in the decrypted character string or alternate text. This can be caused by | ✅ |
| PTOCA-4-217 | EC-1E01...LENGTH is not valid. | ✅ |
| PTOCA-4-218 | EC-9D01...Decryption is not available on this device. | ✅ |
| PTOCA-4-219 | EC-9D02...Decryption reported an error. | ✅ |
| PTOCA-4-220 | EC-9D03...No key information has been set for decryption. | ✅ |
| PTOCA-4-221 | 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N | ✅ |
| PTOCA-4-222 | 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N | ✅ |
| PTOCA-4-223 | 2 | UBIN | LENGTH | 3 | Control sequence length | M | N | N | ✅ |
| PTOCA-4-224 | 3 | CODE | TYPE | X'F4' – X'F5' | Control sequence function type | M | N | N | ✅ |
| PTOCA-4-225 | 4 | CODE | LID | X'00' – X'FF' | Suppression identifier | M | N | N | ✅ |
| PTOCA-4-226 | stream documentation. | ✅ |
| PTOCA-4-227 | must activate the LID. | ✅ |
| PTOCA-4-228 | EC-9801...The value of LID is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-229 | 0 UBIN LENGTH X'04' – X'FE'; | ✅ |
| PTOCA-4-230 | 1 CODE TYPE X'8C', X'8D' Control sequence | ✅ |
| PTOCA-4-231 | 2-3 X'0000' Reserved; should be zero M N N | ✅ |
| PTOCA-4-232 | conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-233 | terminates the GLC chain. | ✅ |
| PTOCA-4-234 | EC-9C03...Invalid sequence. The GAR is not preceded by a GIR, or, if it indicates chaining, it is not followed | ✅ |
| PTOCA-4-235 | EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was | ✅ |
| PTOCA-4-236 | EC-9C08...Glyph Advance count mismatch. The number of glyph advances specified is not the same as the | ✅ |
| PTOCA-4-237 | The Glyph ID Run control sequence specifies an array of glyph IDs from the current TrueType/OpenType font. | ✅ |
| PTOCA-4-238 | 0 UBIN LENGTH X'04' – X'FE'; | ✅ |
| PTOCA-4-239 | 1 CODE TYPE X'8B' Control sequence | ✅ |
| PTOCA-4-240 | 2-3 X'0000' Reserved; should be zero M N N | ✅ |
| PTOCA-4-241 | in this control sequence. | ✅ |
| PTOCA-4-242 | the chained GAR must contain n advances and the optional GOR must contain n offsets. | ✅ |
| PTOCA-4-243 | EC-9C02...Invalid glyph ID. The current font does not contain a specified glyph ID. | ✅ |
| PTOCA-4-244 | EC-9C03...Invalid sequence. The GIR is not preceded by either a GLC or a GAR or a GOR. | ✅ |
| PTOCA-4-245 | EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was | ✅ |
| PTOCA-4-246 | render text using arrays of glyph identifiers and positions. | ✅ |
| PTOCA-4-247 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-248 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-249 | 2 UBIN LENGTH 10 - (p - 1) Control sequence length M N N | ✅ |
| PTOCA-4-250 | 3 CODE TYPE X'6D' Control sequence function | ✅ |
| PTOCA-4-251 | 6 UBIN OIDLGTH 0, 13-129 Length of FONTOID | ✅ |
| PTOCA-4-252 | 7 UBIN FFNLGTH 0 - (255 - (10 + | ✅ |
| PTOCA-4-253 | 8-1 1 X'00...00' Reserved; should be zero M N N | ✅ |
| PTOCA-4-254 | Offset 12 – must be X'06' | ✅ |
| PTOCA-4-255 | Offset 13 – length of | ✅ |
| PTOCA-4-256 | Offset 14 to n – OID | ✅ |
| PTOCA-4-257 | condition EC-9C0B exists. | ✅ |
| PTOCA-4-258 | Example 1. GLC chain without optional controls. The GLC may reference the base font, or any font linked to | ✅ |
| PTOCA-4-259 | Example 2. GLC chain with optional controls. Since one or more glyphs must be positioned with an offset | ✅ |
| PTOCA-4-260 | Example 3. GLC chain with multiple GIR/GAR[/GOR] groupings. The text required more glyphs than a single | ✅ |
| PTOCA-4-261 | GLC GIR GAR GOR GIR GAR UCT <<chain ends>> | ✅ |
| PTOCA-4-262 | EC-9C00...Font Mismatch. The object OID specified in the GLC control sequence does not match the object | ✅ |
| PTOCA-4-263 | EC-9C01...Font format not valid for use with glyph layout control sequences. The current font is not a | ✅ |
| PTOCA-4-264 | EC-9C03...Unexpected control sequence. An unexpected control sequence was encountered between the | ✅ |
| PTOCA-4-265 | EC-9C09...Missing font OID. The GLC specified an OIDLGTH of zero, but no previous font OID was supplied | ✅ |
| PTOCA-4-266 | EC-9C0A...Count mismatch or invalid length. The byte count specified by the OIDLGTH and FFONTNME | ✅ |
| PTOCA-4-267 | EC-9C0B...Full Font Name specified without font OID. A font OID was not specified (OIDLGTH = 0), but a | ✅ |
| PTOCA-4-268 | the b-direction) to the glyph origin for each glyph ID in the preceding GIR. | ✅ |
| PTOCA-4-269 | 0 UBIN LENGTH X'04' – X'FE'; | ✅ |
| PTOCA-4-270 | 1 CODE TYPE X'8E', X'8F' Control sequence | ✅ |
| PTOCA-4-271 | 2-3 X'0000' Reserved; should be zero M N N | ✅ |
| PTOCA-4-272 | advances so that presentation devices can offset the correct glyph. | ✅ |
| PTOCA-4-273 | EC-9C03...Invalid sequence. The GOR is not preceded by a GAR, or, if it indicates chaining, is not followed | ✅ |
| PTOCA-4-274 | EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was | ✅ |
| PTOCA-4-275 | EC-9C08...Glyph offset count mismatch. The number of glyph offsets specified is not the same as the | ✅ |
| PTOCA-4-276 | The No Operation control sequence has no effect on presentation. | ✅ |
| PTOCA-4-277 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-278 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-279 | 2 UBIN LENGTH 2–255 Control sequence length M N N | ✅ |
| PTOCA-4-280 | 3 CODE TYPE X'F8' – X'F9' Control sequence | ✅ |
| PTOCA-4-281 | The Overstrike control sequence identifies text that is to be overstruck with a specified character. | ✅ |
| PTOCA-4-282 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-283 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-284 | 2 UBIN LENGTH 5 Control sequence length M N N | ✅ |
| PTOCA-4-285 | 3 CODE TYPE X'72' – X'73' Control sequence | ✅ |
| PTOCA-4-286 | 4 BITS BYPSIDEN See | ✅ |
| PTOCA-4-287 | accept the default indicator. | ✅ |
| PTOCA-4-288 | The overstrike character | ✅ |
| PTOCA-4-289 | How to place the overstrike characters in relation to the characters in the text field | ✅ |
| PTOCA-4-290 | Which controlled inline white space is to be overstruck | ✅ |
| PTOCA-4-291 | Absolute Move Inline control sequence | ✅ |
| PTOCA-4-292 | Relative Move Inline control sequence | ✅ |
| PTOCA-4-293 | Space character or variable space character | ✅ |
| PTOCA-4-294 | X'40' in EBCDIC single-byte code pages | ✅ |
| PTOCA-4-295 | X'20' in ASCII single-byte code pages | ✅ |
| PTOCA-4-296 | X'4040' in EBCDIC double-byte code pages | ✅ |
| PTOCA-4-297 | X'2020' in ASCII double-byte code pages | ✅ |
| PTOCA-4-298 | X'0020' | ✅ |
| PTOCA-4-299 | X'00A0' | ✅ |
| PTOCA-4-300 | 0-3 Reserved, that is, set to B'0' by generators and ignored by receivers | ✅ |
| PTOCA-4-301 | 4 Bypass Relative Move Inline | ✅ |
| PTOCA-4-302 | 5 Bypass Absolute Move Inline | ✅ |
| PTOCA-4-303 | 6 Bypass space characters and variable space characters | ✅ |
| PTOCA-4-304 | 7 No Bypass in Effect | ✅ |
| PTOCA-4-305 | A beginning OVS | ✅ |
| PTOCA-4-306 | Either end of bypassed controlled inline white space | ✅ |
| PTOCA-4-307 | Either end of a baseline move, which may be for the established baseline or for a temporary baseline | ✅ |
| PTOCA-4-308 | The beginning of negative changes in the presentation position caused by inline moves or negative | ✅ |
| PTOCA-4-309 | Boundaries where violation causes truncation | ✅ |
| PTOCA-4-310 | An ending OVS | ✅ |
| PTOCA-4-311 | The end of the Presentation Text object | ✅ |
| PTOCA-4-312 | condition exists. See the Pragmatics section for the exception condition code and the standard action. | ✅ |
| PTOCA-4-313 | 2100 exists. The standard action is to use a device default character as the overstrike character. If the graphic | ✅ |
| PTOCA-4-314 | At least one overstrike character must occur in an overstrike area. | ✅ |
| PTOCA-4-315 | The overstrike characters must be positioned relative to the delimiters of the overstrike area, rather than to | ✅ |
| PTOCA-4-316 | Overlap of any portion of the B-space of the overstrike character with the B-space of a character not within | ✅ |
| PTOCA-4-317 | Overlap of any portion of the B-space of the overstrike character with the B-space of another overstrike | ✅ |
| PTOCA-4-318 | Rounding of f the number of overstrike characters is permitted. The minimum required support is to round of f | ✅ |
| PTOCA-4-319 | Multiple passes over the same portions of the presentation space through the use of AMI and RMI control | ✅ |
| PTOCA-4-320 | An area of zero length is not considered to be a valid overstrike area. | ✅ |
| PTOCA-4-321 | EC-2100...The graphic character specified is not valid in the active font. | ✅ |
| PTOCA-4-322 | EC-3F02...The graphic character specified does not have a rotation available that is equivalent to the current | ✅ |
| PTOCA-4-323 | EC-9A01...The graphic character specified has an invalid character increment or is not a printable character. | ✅ |
| PTOCA-4-324 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-325 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-326 | 2 UBIN LENGTH 4 Control sequence length M N N | ✅ |
| PTOCA-4-327 | 3 CODE TYPE X'D4' – X'D5' Control sequence | ✅ |
| PTOCA-4-328 | measurement unit, please see the conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-329 | stream documentation. | ✅ |
| PTOCA-4-330 | EC-1601...The value of INCRMENT is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-331 | EC-0103..The presentation position is outside the object space and presentation is attempted. | ✅ |
| PTOCA-4-332 | EC-1403...Negative INCRMENT is not valid. | ✅ |
| PTOCA-4-333 | the current inline position. | ✅ |
| PTOCA-4-334 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-335 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-336 | 2 UBIN LENGTH 4 Control sequence length M N N | ✅ |
| PTOCA-4-337 | 3 CODE TYPE X'C8' – X'C9' Control sequence | ✅ |
| PTOCA-4-338 | measurement unit, please see the conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-339 | stream documentation. | ✅ |
| PTOCA-4-340 | character being presented. Then presentation of characters may resume. | ✅ |
| PTOCA-4-341 | EC-1501...The value of INCRMENT is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-342 | EC-0103..The presentation position is outside the object space and presentation is attempted. | ✅ |
| PTOCA-4-343 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-344 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-345 | 2 UBIN LENGTH 4–255 Control sequence length M N N | ✅ |
| PTOCA-4-346 | 3 CODE TYPE X'EE' – X'EF' Control sequence | ✅ |
| PTOCA-4-347 | RPTDATA do not accept the default indicator. | ✅ |
| PTOCA-4-348 | data-stream documentation. | ✅ |
| PTOCA-4-349 | RPS control sequence not be used in this manner, and that the TRN control sequence be used instead. | ✅ |
| PTOCA-4-350 | EC-1A01...A double-byte font is active and the length of RPTDATA is an odd number. | ✅ |
| PTOCA-4-351 | EC-1A03...Invalid Unicode data. This can be caused by one of the following: | ✅ |
| PTOCA-4-352 | EC-1B01...A double-byte font is active and RLENGTH is an odd number. | ✅ |
| PTOCA-4-353 | EC-1901...The value of RLENGTH is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-354 | EC-0103...A parameter value will cause part of a character's character box to be outside the object space, | ✅ |
| PTOCA-4-355 | EC-1F01...The control sequence length parameter is four and RLENGTH is not zero. | ✅ |
| PTOCA-4-356 | coordinate when a Begin Line control sequence is executed. This is a modal control sequence. | ✅ |
| PTOCA-4-357 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-358 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-359 | 2 UBIN LENGTH 4 Control sequence length M N N | ✅ |
| PTOCA-4-360 | 3 CODE TYPE X'D0' – X'D1' Control sequence | ✅ |
| PTOCA-4-361 | INCRMENT should be the Default Baseline Increment of the default coded font for the device. | ✅ |
| PTOCA-4-362 | 1 101 exists. The standard action is to ignore this control sequence and continue presentation with the value | ✅ |
| PTOCA-4-363 | Presentation Text Data Descriptor. | ✅ |
| PTOCA-4-364 | EC-1 101...The value of INCRMENT is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-365 | used. This is a modal control sequence. | ✅ |
| PTOCA-4-366 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-367 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-368 | 2 UBIN LENGTH 3 Control sequence length M N N | ✅ |
| PTOCA-4-369 | 3 CODE TYPE X'F0' – X'F1' Control sequence | ✅ |
| PTOCA-4-370 | 4 CODE LID X'00' – X'FE' Local identifier M Y Y | ✅ |
| PTOCA-4-371 | The PTOCA default value for the LID is X'00'. | ✅ |
| PTOCA-4-372 | stream documentation. | ✅ |
| PTOCA-4-373 | The receiver provides internal mapping, using device defaults. | ✅ |
| PTOCA-4-374 | The controlling environment provides the mapping to the receiver. | ✅ |
| PTOCA-4-375 | SCFL Control Sequence | ✅ |
| PTOCA-4-376 | not an exception condition in PTOCA. | ✅ |
| PTOCA-4-377 | EC-1E01...The LID is missing. | ✅ |
| PTOCA-4-378 | EC-0C01...The value of the LID is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-379 | EC-1802...A font mapping has not been provided. | ✅ |
| PTOCA-4-380 | EC-1802...The coded font specified by the mapping is not available to the receiver. | ✅ |
| PTOCA-4-381 | EC-3F02...The specified coded font is not compatible with the text orientation. | ✅ |
| PTOCA-4-382 | SCFL Control Sequence | ✅ |
| PTOCA-4-383 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-384 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-385 | 2 UBIN LENGTH 6, 7–255 Control sequence length M N N | ✅ |
| PTOCA-4-386 | 3 CODE TYPE X'9C' – X'9D' Control sequence | ✅ |
| PTOCA-4-387 | 4-7 Reserved; should be zero M N N | ✅ |
| PTOCA-4-388 | 8-256 CHAR ALTTEXT Not | ✅ |
| PTOCA-4-389 | default indicator, but X'F....F' is valid. | ✅ |
| PTOCA-4-390 | the ENC Semantics for a description of the details). | ✅ |
| PTOCA-4-391 | ignore the control sequence and continue processing. | ✅ |
| PTOCA-4-392 | EC-1E01...LENGTH is not valid. | ✅ |
| PTOCA-4-393 | The stroked and filled areas of solid text characters, including overstrike characters; with hollow characters, | ✅ |
| PTOCA-4-394 | The stroked area of a rule | ✅ |
| PTOCA-4-395 | The stroked area of an underscore | ✅ |
| PTOCA-4-396 | a definition of modal control sequences, see “Modal Control Sequences”. | ✅ |
| PTOCA-4-397 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-398 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-399 | 2 UBIN LENGTH 14–16 Control sequence length M N N | ✅ |
| PTOCA-4-400 | 3 CODE TYPE X'80' – X'81' Control sequence | ✅ |
| PTOCA-4-401 | 4 Reserved; should be zero M N N | ✅ |
| PTOCA-4-402 | 5 CODE COLSPCE Color space M N N | ✅ |
| PTOCA-4-403 | 10 UBIN COLSIZE1 X'01' – X'08', | ✅ |
| PTOCA-4-404 | 1 1 UBIN COLSIZE2 X'00' – X'08' Number of bits in | ✅ |
| PTOCA-4-405 | 12 UBIN COLSIZE3 X'00' – X'08' Number of bits in | ✅ |
| PTOCA-4-406 | 13 UBIN COLSIZE4 X'00' – X'08' Number of bits in | ✅ |
| PTOCA-4-407 | 14–(n-1) COL Value See | ✅ |
| PTOCA-4-408 | Color specifications M N N | ✅ |
| PTOCA-4-409 | - 1), where N=1,2,3. | ✅ |
| PTOCA-4-410 | 3. A value of X'00' indicates that component 3 is not specified in the color value, in which case | ✅ |
| PTOCA-4-411 | 1. The color that is rendered when a highlight color is specified is device dependent. For | ✅ |
| PTOCA-4-412 | 2. If the specified highlight color is 'presentation device default', devices whose default color | ✅ |
| PTOCA-4-413 | 3. On printing devices, the color of medium is normally white, in which case a coverage of n | ✅ |
| PTOCA-4-414 | 4. The highlight color space can also specify indexed colors when used in conjunction with a | ✅ |
| PTOCA-4-415 | - 1). The range for the a and b components is -127 to +127, which is mapped to the binary | ✅ |
| PTOCA-4-416 | - 1) to +(2 | ✅ |
| PTOCA-4-417 | 1. The presentation-process default specified by X'0000' and X'FF00' is resolved as follows: | ✅ |
| PTOCA-4-418 | For PTOCA text data, it is the presentation device default | ✅ |
| PTOCA-4-419 | 2. The color rendered on presentation devices that do not support white is device-dependent. For | ✅ |
| PTOCA-4-420 | 3. The presentation-process default specified by X'FF07' is resolved as the presentation device | ✅ |
| PTOCA-4-421 | 4. The value X'FFFF' is not defined in the Standard OCA Color Value Table but is used by some | ✅ |
| PTOCA-4-422 | For PTOCA text data, X'FFFF' may be specified in the Set Text Color (STC) control sequence to | ✅ |
| PTOCA-4-423 | 5. While the RGB values in the table can be used to render the OCA named colors, many | ✅ |
| PTOCA-4-424 | data-stream documentation. | ✅ |
| PTOCA-4-425 | action in this case is to use the presentation device default color. | ✅ |
| PTOCA-4-426 | EC-0E02...Invalid or unsupported color space. | ✅ |
| PTOCA-4-427 | EC-0E03...Invalid or unsupported color value. | ✅ |
| PTOCA-4-428 | EC-0E04...Invalid percent value. | ✅ |
| PTOCA-4-429 | EC-0E05...Invalid or unsupported number of bits in a color component. | ✅ |
| PTOCA-4-430 | graphic characters. This is a modal control sequence. | ✅ |
| PTOCA-4-431 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-432 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-433 | 2 UBIN LENGTH 4–5 Control sequence length M N N | ✅ |
| PTOCA-4-434 | 3 CODE TYPE X'C2' – X'C3' Control sequence | ✅ |
| PTOCA-4-435 | 6 CODE DIRECTION X'00' – X'01' Direction O Y Y | ✅ |
| PTOCA-4-436 | A space character or variable space character | ✅ |
| PTOCA-4-437 | A Begin Line control sequence | ✅ |
| PTOCA-4-438 | A Relative Move Inline control sequence | ✅ |
| PTOCA-4-439 | An Absolute Move Inline control sequence | ✅ |
| PTOCA-4-440 | The space character or variable space character | ✅ |
| PTOCA-4-441 | Begin Line control sequences | ✅ |
| PTOCA-4-442 | Relative Move Inline control sequences | ✅ |
| PTOCA-4-443 | Absolute Move Inline control sequences | ✅ |
| PTOCA-4-444 | X'40' in EBCDIC single-byte code pages | ✅ |
| PTOCA-4-445 | X'20' in ASCII single-byte code pages | ✅ |
| PTOCA-4-446 | X'4040' in EBCDIC double-byte code pages | ✅ |
| PTOCA-4-447 | X'2020' in ASCII double-byte code pages | ✅ |
| PTOCA-4-448 | X'0020' | ✅ |
| PTOCA-4-449 | X'00A0' | ✅ |
| PTOCA-4-450 | stream documentation. | ✅ |
| PTOCA-4-451 | The current presentation position | ✅ |
| PTOCA-4-452 | The current I-unit value | ✅ |
| PTOCA-4-453 | The current inline margin | ✅ |
| PTOCA-4-454 | The current intercharacter increment value | ✅ |
| PTOCA-4-455 | The current intercharacter decrement value | ✅ |
| PTOCA-4-456 | EC-1201...The value of ADJSTMNT or DIRECTION is not supported or is not in the range specified by | ✅ |
| PTOCA-4-457 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-458 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-459 | 2 UBIN LENGTH 4 Control sequence length M N N | ✅ |
| PTOCA-4-460 | 3 CODE TYPE X'C0' – X'C1' Control sequence | ✅ |
| PTOCA-4-461 | This control sequence does not change the current addressable position. | ✅ |
| PTOCA-4-462 | the value of DSPLCMNT is zero, the inline margin is at the B-axis. | ✅ |
| PTOCA-4-463 | EC-1E01...DSPLCMNT is missing. | ✅ |
| PTOCA-4-464 | EC-1001...The value of DSPLCMNT is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-465 | (ENC) controls. This is a modal control sequence. | ✅ |
| PTOCA-4-466 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-467 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-468 | 2 UBIN LENGTH 6, 7–255 Control sequence length M N N | ✅ |
| PTOCA-4-469 | 3 CODE TYPE X'9A' – X'9B' Control sequence | ✅ |
| PTOCA-4-470 | indicator, but X'F....F' is valid. | ✅ |
| PTOCA-4-471 | stream documentation. | ✅ |
| PTOCA-4-472 | 249 bytes. Consecutive, in this case, means the SKI controls have no intervening PTOCA controls between | ✅ |
| PTOCA-4-473 | effe ctively ignored, while consecutive SKIs after it, if any, begin a new KEYINFO definition. | ✅ |
| PTOCA-4-474 | EC-1E01...LENGTH is not valid. | ✅ |
| PTOCA-4-475 | EC-9D01...Decryption is not available on this device. | ✅ |
| PTOCA-4-476 | The stroked and filled areas of solid text characters, including overstrike characters; with hollow characters, | ✅ |
| PTOCA-4-477 | The stroked area of a rule | ✅ |
| PTOCA-4-478 | The stroked area of an underscore | ✅ |
| PTOCA-4-479 | a definition of modal control sequences, see “Modal Control Sequences”. | ✅ |
| PTOCA-4-480 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-481 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-482 | 2 UBIN LENGTH 4, 5 Control sequence length M N N | ✅ |
| PTOCA-4-483 | 3 CODE TYPE X'74' – X'75' Control sequence | ✅ |
| PTOCA-4-484 | 4–5 CODE FRGCOLOR See | ✅ |
| PTOCA-4-485 | 6 Retired parameter, see | ✅ |
| PTOCA-4-486 | 6. This parameter has been retired. It should not be generated by new applications, and should be | ✅ |
| PTOCA-4-487 | ignored by new printers. For a definition of this parameter, see “Retired Parameters”. | ✅ |
| PTOCA-4-488 | 1. The presentation-process default specified by X'0000' and X'FF00' is resolved as follows: | ✅ |
| PTOCA-4-489 | For PTOCA text data, it is the presentation device default | ✅ |
| PTOCA-4-490 | 2. The color rendered on presentation devices that do not support white is device-dependent. For example, some | ✅ |
| PTOCA-4-491 | 3. The presentation-process default specified by X'FF07' is resolved as the presentation device default. This color | ✅ |
| PTOCA-4-492 | 4. The value X'FFFF' is not defined in the Standard OCA Color Value Table but is used by some objects as a default | ✅ |
| PTOCA-4-493 | For PTOCA text data, X'FFFF' may be specified in the Set Text Color (STC) control sequence to indicate that the | ✅ |
| PTOCA-4-494 | 5. While the RGB values in the table can be used to render the OCA named colors, many implementations are and | ✅ |
| PTOCA-4-495 | stream documentation. | ✅ |
| PTOCA-4-496 | 1. Value set by Text Color initial text condition parameter in descriptor | ✅ |
| PTOCA-4-497 | 2. PTOCA default X'FF07' | ✅ |
| PTOCA-4-498 | EC-5803...The value of FRGCOLOR is invalid, or the specified color is not supported. | ✅ |
| PTOCA-4-499 | 1. The MO:DCA environment supports a Color Mapping Table (CMT) that may be used to map colors in a | ✅ |
| PTOCA-4-500 | 2. The IPDS environment allows a presentation device to implement limited simulated color support for | ✅ |
| PTOCA-4-501 | This is a modal control sequence. | ✅ |
| PTOCA-4-502 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-503 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-504 | 2 UBIN LENGTH 6 Control sequence length M N N | ✅ |
| PTOCA-4-505 | 3 CODE TYPE X'F6' – X'F7' Control sequence | ✅ |
| PTOCA-4-506 | 4–5 CODE IORNTION See | ✅ |
| PTOCA-4-507 | 6–7 CODE BORNTION See | ✅ |
| PTOCA-4-508 | The PTOCA default for IORNTION is zero. The PTOCA default for BORNTION is 90. | ✅ |
| PTOCA-4-509 | A is a nine-bit binary number (bits 0 - 8) which provides from 0 through 359 degrees. Values from 360 | ✅ |
| PTOCA-4-510 | B is a six-bit binary number (bits 9 - 14) which provides from 0 through 59 minutes. Values from 60 through | ✅ |
| PTOCA-4-511 | 63 are invalid. | ✅ |
| PTOCA-4-512 | C is a one-bit reserved field (bit 15) which must be 0. | ✅ |
| PTOCA-4-513 | 1. The following remain as previously specified: | ✅ |
| PTOCA-4-514 | The current presentation position, an $X_p$,$Y_p$ coordinate, | ✅ |
| PTOCA-4-515 | The current I-unit value, | ✅ |
| PTOCA-4-516 | The current inline margin, | ✅ |
| PTOCA-4-517 | The current intercharacter increment value, | ✅ |
| PTOCA-4-518 | The current intercharacter decrement value, | ✅ |
| PTOCA-4-519 | The current B-unit value, | ✅ |
| PTOCA-4-520 | The current baseline increment value, | ✅ |
| PTOCA-4-521 | The current coded font. | ✅ |
| PTOCA-4-522 | 2. The following will change: | ✅ |
| PTOCA-4-523 | The $X_p$ | ✅ |
| PTOCA-4-524 | Font character rotations appropriate to the new orientation are used. | ✅ |
| PTOCA-4-525 | Presentation position should be respecified if subsequent text is to be positioned elsewhere in the | ✅ |
| PTOCA-4-526 | Other modal parameter values should be respecified if they are more appropriate to the new orientation. | ✅ |
| PTOCA-4-527 | A new coded font should be specified: | ✅ |
| PTOCA-4-528 | 3. If the Presentation Text object measurement units specified for the $X_p$ | ✅ |
| PTOCA-4-529 | unexpected and use of a Set Text Orientation control sequence should be avoided. | ✅ |
| PTOCA-4-530 | EC-1E01...IORNTION or BORNTION is missing. | ✅ |
| PTOCA-4-531 | EC-0F01...IORNTION and BORNTION are identical. | ✅ |
| PTOCA-4-532 | EC-0F01...IORNTION or BORNTION not parallel to $X_p$ | ✅ |
| PTOCA-4-533 | EC-0F01...IORNTION and BORNTION not supported by receiver. | ✅ |
| PTOCA-4-534 | character. This is a modal control sequence. | ✅ |
| PTOCA-4-535 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-536 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-537 | 2 UBIN LENGTH 4 Control sequence length M N N | ✅ |
| PTOCA-4-538 | 3 CODE TYPE X'C4' – X'C5' Control sequence | ✅ |
| PTOCA-4-539 | conversion routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-540 | stream documentation. | ✅ |
| PTOCA-4-541 | 1. The current variable space character increment | ✅ |
| PTOCA-4-542 | 2. The default variable space character increment of the active coded font | ✅ |
| PTOCA-4-543 | 3. The character increment of the default variable space character code point | ✅ |
| PTOCA-4-544 | The current presentation position, an $X_p$,$Y_p$ coordinate | ✅ |
| PTOCA-4-545 | The current I-unit value | ✅ |
| PTOCA-4-546 | The current inline margin, | ✅ |
| PTOCA-4-547 | The current intercharacter increment value | ✅ |
| PTOCA-4-548 | The current intercharacter decrement value | ✅ |
| PTOCA-4-549 | The current B-unit value | ✅ |
| PTOCA-4-550 | The current baseline increment value | ✅ |
| PTOCA-4-551 | The current coded font | ✅ |
| PTOCA-4-552 | X'40' in EBCDIC single-byte code pages | ✅ |
| PTOCA-4-553 | X'20' in ASCII single-byte code pages | ✅ |
| PTOCA-4-554 | X'4040' in EBCDIC double-byte code pages | ✅ |
| PTOCA-4-555 | X'2020' in ASCII double-byte code pages | ✅ |
| PTOCA-4-556 | X'0020' | ✅ |
| PTOCA-4-557 | X'00A0' | ✅ |
| PTOCA-4-558 | increment of the active coded font is used. | ✅ |
| PTOCA-4-559 | EC-1E01...INCRMENT is missing | ✅ |
| PTOCA-4-560 | EC-1701...The value of INCRMENT is not supported or is not in the range specified by PTOCA.EC-1E01EC- | ✅ |
| PTOCA-4-561 | 1 10 PTOCA Reference | ✅ |
| PTOCA-4-562 | established baseline. | ✅ |
| PTOCA-4-563 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-564 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-565 | 2 UBIN LENGTH 3, 4, 6 Control sequence length M N N | ✅ |
| PTOCA-4-566 | 3 CODE TYPE X'78' – X'79' Control sequence | ✅ |
| PTOCA-4-567 | 4 CODE DIRECTION X'00' – X'03' Direction M Y Y | ✅ |
| PTOCA-4-568 | 5 BITS PRECISION X'00' – X'01' Precision O Y Y | ✅ |
| PTOCA-4-569 | 1/2 the baseline increment value. The PTOC A default value for DIRECTION and PRECISION is zero. | ✅ |
| PTOCA-4-570 | Change the current baseline coordinate by the amount specified by INCRMENT in the direction specified by | ✅ |
| PTOCA-4-571 | Return the baseline coordinate to the established baseline coordinate position | ✅ |
| PTOCA-4-572 | - INCRMENT | ✅ |
| PTOCA-4-573 | stream documentation. | ✅ |
| PTOCA-4-574 | How TBM operates: | ✅ |
| PTOCA-4-575 | 1 12 PTOCA Reference | ✅ |
| PTOCA-4-576 | How TBM affects other control sequences: | ✅ |
| PTOCA-4-577 | How TBM uses the PRECISION parameter: | ✅ |
| PTOCA-4-578 | 9803 exists. The standard action is to present the requested character at the established baseline | ✅ |
| PTOCA-4-579 | How TBM relates to underscore and overstrike: | ✅ |
| PTOCA-4-580 | Miscellaneous TBM exception conditions: | ✅ |
| PTOCA-4-581 | characters were being presented. | ✅ |
| PTOCA-4-582 | EC-9803...The value of INCRMENT, PRECISION, or DIRECTION is not supported or is not in the range | ✅ |
| PTOCA-4-583 | EC-9803...The PRECISION parameterspecifies the actual placement method but the receiver does not | ✅ |
| PTOCA-4-584 | EC-9803...A receiver using the substitution method cannot generate the required substitution character. | ✅ |
| PTOCA-4-585 | EC-9803...For a receiver that uses the actual placement method, the INCRMENT parameter exceeds the | ✅ |
| PTOCA-4-586 | EC-9803...A multi-offset TBM control sequence is received by a receiver that uses the substitution method. | ✅ |
| PTOCA-4-587 | 1 14 PTOCA Reference | ✅ |
| PTOCA-4-588 | EC-0103...The control sequence will cause part of a character's character box to be outside of the object | ✅ |
| PTOCA-4-589 | for embedded control sequences. | ✅ |
| PTOCA-4-590 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-591 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-592 | 2 UBIN LENGTH 2–255 Control sequence length M N N | ✅ |
| PTOCA-4-593 | 3 CODE TYPE X'DA' – X'DB' Control sequence | ✅ |
| PTOCA-4-594 | The contents of TRNDATA are unknown. TRNDATA does not accept the default indicator, but X'F....F' is valid. | ✅ |
| PTOCA-4-595 | 1 16 PTOCA Reference | ✅ |
| PTOCA-4-596 | EC-0103...The control sequence will cause part of a character's character box to be outside the object | ✅ |
| PTOCA-4-597 | EC-1A01...The control sequence length is an odd number, but a double-byte font is active. | ✅ |
| PTOCA-4-598 | EC-1A03...Invalid Unicode data. This can be caused by one of the following: | ✅ |
| PTOCA-4-599 | The Underscore control sequence identifies text fields that are to be underscored. | ✅ |
| PTOCA-4-600 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-601 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-602 | 2 UBIN LENGTH 3 Control sequence length M N N | ✅ |
| PTOCA-4-603 | 3 CODE TYPE X'76' – X'77' Control sequence | ✅ |
| PTOCA-4-604 | 4 BITS BYPSIDEN See | ✅ |
| PTOCA-4-605 | Absolute Move Inline control sequence | ✅ |
| PTOCA-4-606 | Relative Move Inline control sequence | ✅ |
| PTOCA-4-607 | Space character or variable space character | ✅ |
| PTOCA-4-608 | X'40' in EBCDIC single-byte code pages | ✅ |
| PTOCA-4-609 | X'20' in ASCII single-byte code pages | ✅ |
| PTOCA-4-610 | X'4040' in EBCDIC double-byte code pages | ✅ |
| PTOCA-4-611 | X'2020' in ASCII double-byte code pages | ✅ |
| PTOCA-4-612 | X'0020' | ✅ |
| PTOCA-4-613 | X'00A0' | ✅ |
| PTOCA-4-614 | 1 18 PTOCA Reference | ✅ |
| PTOCA-4-615 | 0-3 Reserved, that is, set to 0 by generators and ignored by receivers | ✅ |
| PTOCA-4-616 | 4 Bypass Relative Move Inline | ✅ |
| PTOCA-4-617 | 5 Bypass Absolute Move Inline | ✅ |
| PTOCA-4-618 | 6 Bypass space characters and variable space characters | ✅ |
| PTOCA-4-619 | 7 No Bypass in effect | ✅ |
| PTOCA-4-620 | A beginning USC | ✅ |
| PTOCA-4-621 | Either end of bypassed controlled inline white space | ✅ |
| PTOCA-4-622 | Either end of a baseline move, which may be for the established baseline or for a temporary baseline | ✅ |
| PTOCA-4-623 | The beginning of negative changes in the presentation position caused by inline moves or negative | ✅ |
| PTOCA-4-624 | Boundaries where violation causes truncation | ✅ |
| PTOCA-4-625 | An ending USC | ✅ |
| PTOCA-4-626 | The end of the Presentation Text object | ✅ |
| PTOCA-4-627 | the inline margin is underscored only if this area is entered by means of an inline move. | ✅ |
| PTOCA-4-628 | the active font is not a data-object font | ✅ |
| PTOCA-4-629 | the data is not encoded in a Unicode-based character encoding | ✅ |
| PTOCA-4-630 | the writing mode is vertical, as determined by a font character rotation of 90° or 270° | ✅ |
| PTOCA-4-631 | 1. When the UCT is specified within a GLC chain, that is when it is chained from a GAR or a GOR, it is used | ✅ |
| PTOCA-4-632 | 2. When the UCT is specified outside a GLC chain (a “stand-alone“ UCT), it is used to process and render the | ✅ |
| PTOCA-4-633 | bidirectional (bidi) layout processing. When the writing mode is horizontal, as determined by a character | ✅ |
| PTOCA-4-634 | glyph processing. Characters may require language-specific shaping such as arabic character shaping, | ✅ |
| PTOCA-4-635 | from the Unicode Consortium at http://unicode.org/standard/standard.html. | ✅ |
| PTOCA-4-636 | 0 CODE PREFIX X'2B' Control Sequence Prefix M N N | ✅ |
| PTOCA-4-637 | 1 CODE CLASS X'D3' Control sequence class M N N | ✅ |
| PTOCA-4-638 | 2 UBIN LENGTH X'10' Control sequence length M N N | ✅ |
| PTOCA-4-639 | 3 CODE TYPE X'6A' Control sequence function type M N N | ✅ |
| PTOCA-4-640 | 4 CODE UCTVERS X'01' UCT version level | ✅ |
| PTOCA-4-641 | 5 Reserved; should be zero M N N | ✅ |
| PTOCA-4-642 | 8 BITS CTFLGS See | ✅ |
| PTOCA-4-643 | 9 Reserved; should be zero M N N | ✅ |
| PTOCA-4-644 | 10 CODE BIDICT X'02', X'04', | ✅ |
| PTOCA-4-645 | 1 1 CODE GLYPHCT X'01', X'20' Glyph processing control: | ✅ |
| PTOCA-4-646 | routine described in “Interpreting Ranges”. | ✅ |
| PTOCA-4-647 | For graphic characters following each other: | ✅ |
| PTOCA-4-648 | For graphic characters following RMI, AMI, or BLN control sequences or following a space character or | ✅ |
| PTOCA-4-649 | For the variable space character: | ✅ |
| PTOCA-4-650 | For a non-incrementing character: | ✅ |
| PTOCA-4-651 | In all cases: | ✅ |
| PTOCA-4-652 | If $I_c$ | ✅ |
| PTOCA-4-653 | sum = summation over all the graphemes that were presented for the UCT | ✅ |
| PTOCA-4-654 | 0 Normalization | ✅ |
| PTOCA-4-655 | 1 Alternate inline position (I | ✅ |
| PTOCA-4-656 | 2 Alternate inline position (I | ✅ |
| PTOCA-4-657 | 3 Maintain current inline position ($I_c$ | ✅ |
| PTOCA-4-658 | 4 Reset paragraph direction | ✅ |
| PTOCA-4-659 | 5-7 Reserved, that is, set to B'0' by generators and ignored by receivers | ✅ |
| PTOCA-4-660 | . In this case the range of I | ✅ |
| PTOCA-4-661 | 1. The terms 'left', 'right', 'top', and 'bottom–as in 'L→R', 'R→L', 'T→B', and 'B→T'–only have meaning when | ✅ |
| PTOCA-4-662 | 2. The (+) i-direction is the direction of increasing i-values; the (-) i-direction is the direction of decreasing | ✅ |
| PTOCA-4-663 | either code points or the start of an unchained control sequence. | ✅ |
| PTOCA-4-664 | EC-1A01...The CTLNGTH parameter is an odd number, but the character encoding is double byte. | ✅ |
| PTOCA-4-665 | EC-9B01...The CTLNGTH, UCTVERS, BIDICT, or GLYPHCT parameter values are invalid. | ✅ |
| PTOCA-4-666 | EC-1A03...Invalid Unicode data. This can be caused by one of the following: | ✅ |
| PTOCA-4-667 | The value in the 1st byte of the UTF-8 byte sequence was not in the legal UTF-8 range (X'00' - X'7F' and | ✅ |
| PTOCA-4-668 | The value in the 2nd byte of the UTF-8 byte sequence was not in the legal UTF-8 range allowed by the value | ✅ |
| PTOCA-4-669 | The value in the 3rd or 4th byte of the UTF-8 byte sequence was not in the legal UTF-8 range for that byte | ✅ |
| PTOCA-4-670 | Text orientation. The orientation of the (i,b) coordinate system which specifies the baseline on which glyphs | ✅ |
| PTOCA-4-671 | Character rotation. Alignment of a character with respect to the baseline. Differentiates between horizontal | ✅ |
| PTOCA-4-672 | Writing mode. Can be horizontal (L→R or R→L) or vertical (top->bottom or bottom->top). Determines– | ✅ |
| PTOCA-4-673 | Bidirectional character property. A property value assigned by the Unicode standard to each character, | ✅ |
| PTOCA-4-674 | Text direction. Specifies the visual ordering of characters in a given directional run. The inherent directional | ✅ |
| PTOCA-4-675 | Paragraph direction. Specifies the dominant text direction for a UCT. Used as an input to the Unicode bidi | ✅ |
| PTOCA-4-676 | The text direction of the L | ✅ |
| PTOCA-4-677 | The text direction of the R | ✅ |
| PTOCA-4-678 | The text direction can also be set by the BIDICT parameter on the UCT. With BIDICT values X'22'and X'23', | ✅ |
| PTOCA-4-679 | If the paragraph direction is R→L, the complete string is rendered as: | ✅ |
| PTOCA-4-680 | If the paragraph direction is L→R, the complete string is rendered as: | ✅ |
| PTOCA-4-681 | Writing mode L→R, paragraph direction L→R: | ✅ |
| PTOCA-4-682 | Writing mode R→L, paragraph direction L→R: | ✅ |
| PTOCA-4-683 | Writing mode L→R, paragraph direction R→L: | ✅ |
| PTOCA-4-684 | Writing mode R→L, paragraph direction R→L: | ✅ |
| PTOCA-4-685 | Writing mode L→R, paragraph direction L→R: | ✅ |
| PTOCA-4-686 | Writing mode L→R, paragraph direction R→L: | ✅ |
| PTOCA-4-687 | Writing mode R→L, paragraph direction R→L: | ✅ |
| PTOCA-4-688 | Writing mode R→L, paragraph direction L→R: | ✅ |
| PTOCA-4-689 | Overstrike (OVS). Table 16 defines which Unicode space characters are treated as PTOCA | ✅ |
| PTOCA-4-690 | Set Intercharacter Adjustment (SIA). If glyph processing or bidi layout processing is enabled, intercharacter | ✅ |
| PTOCA-4-691 | Set Text Orientation (STO). If bidi layout processing is enabled and the writing mode is horizontal, characters | ✅ |
| PTOCA-4-692 | Set V ariable Space Character Increment (SVI). Table 16 defines which Unicode space | ✅ |
| PTOCA-4-693 | Temporary Baseline Move (TBM). If glyph processing or bidi layout processing is enabled, the precision | ✅ |
| PTOCA-4-694 | Underscore (USC). Table 16 defines which Unicode space characters are treated as PTOCA | ✅ |
| PTOCA-4-695 | 1. Measurement units parameters: | ✅ |
| PTOCA-4-696 | Unit base | ✅ |
| PTOCA-4-697 | 2. Size parameters | ✅ |
| PTOCA-4-698 | 3. Initial text condition parameters | ✅ |
| PTOCA-4-699 | 0 Ten inches | ✅ |
| PTOCA-4-700 | 1 Ten centimeters | ✅ |
| PTOCA-4-701 | 2-254 Reserved | ✅ |
| PTOCA-4-702 | 5000 or one five-hundredth of an inch. Here are further examples. | ✅ |
| PTOCA-4-703 | 800 X 800 units/in. Unit base = 0 | ✅ |
| PTOCA-4-704 | 80 X 77 units/cm. Unit base = 1 | ✅ |
| PTOCA-4-705 | 800 divisions in 10 cm. on $X_p$ | ✅ |
| PTOCA-4-706 | 770 divisions in 10 cm. on $Y_p$ | ✅ |
| PTOCA-4-707 | 203.3 X 195.5 | ✅ |
| PTOCA-4-708 | 240 x 240 units/in. Unit base = 0 | ✅ |
| PTOCA-4-709 | specified by PTOCA, exception condition EC-0705 exists. The standard action is to ignore the invalid | ✅ |
| PTOCA-4-710 | EC-0505...The value of the unit base parameter is not supported, or is not in the range specified by PTOC A. | ✅ |
| PTOCA-4-711 | EC-0605...The value of the $X_p$ | ✅ |
| PTOCA-4-712 | EC-0705...The value of the $X_p$ | ✅ |
| PTOCA-4-713 | EC-0103...The contents of the Presentation Text object will cause presentation outside of the object space. | ✅ |
| PTOCA-4-714 | Semantics: This parameter is reserved. Generators should set it to zero and receivers should ignore it. | ✅ |
| PTOCA-4-715 | Baseline increment | ✅ |
| PTOCA-4-716 | Coded font local ID | ✅ |
| PTOCA-4-717 | Extended text color | ✅ |
| PTOCA-4-718 | Initial baseline coordinate | ✅ |
| PTOCA-4-719 | Initial inline coordinate | ✅ |
| PTOCA-4-720 | Inline margin | ✅ |
| PTOCA-4-721 | Intercharacter adjustment | ✅ |
| PTOCA-4-722 | Text color | ✅ |
| PTOCA-4-723 | Text orientation | ✅ |
| PTOCA-4-724 | Increment control sequence. | ✅ |
| PTOCA-4-725 | EC-1 101...The value of the baseline increment parameter is not supported or is not in the range specified by | ✅ |
| PTOCA-4-726 | The default value is the LID of the default coded font of the device. | ✅ |
| PTOCA-4-727 | The Coded Font Local ID parameter is present in the Presentation Text Data Descriptor but a corresponding | ✅ |
| PTOCA-4-728 | An equate of the local identifier to a global identifier does not exist, or substitution parameters do not exist, in | ✅ |
| PTOCA-4-729 | The standard action is to substitute the receiver's default font for the requested font and continue processing. | ✅ |
| PTOCA-4-730 | EC-0C01...The value of the LID parameter is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-731 | EC-3F02...The font is not compatible with the text orientation. | ✅ |
| PTOCA-4-732 | EC-1802...The font requested cannot be provided. | ✅ |
| PTOCA-4-733 | The stroked and filled areas of solid text characters, including overstrike characters. With hollow characters, | ✅ |
| PTOCA-4-734 | The stroked area of a rule. | ✅ |
| PTOCA-4-735 | The stroked area of an underscore. | ✅ |
| PTOCA-4-736 | The standard action in this case is to use the presentation device default color. | ✅ |
| PTOCA-4-737 | EC-0E02...Invalid or unsupported color space. | ✅ |
| PTOCA-4-738 | EC-0E03...Invalid or unsupported color value. | ✅ |
| PTOCA-4-739 | EC-0E04...Invalid percent value. | ✅ |
| PTOCA-4-740 | EC-0E05...Invalid or unsupported number of bits in a color component. | ✅ |
| PTOCA-4-741 | at the I-axis. This does not affect the inline margin. | ✅ |
| PTOCA-4-742 | EC-6B02...The value of the B-displacement parameter is not supported or is not in the range specified by | ✅ |
| PTOCA-4-743 | the B-axis. This does not affect the inline margin. | ✅ |
| PTOCA-4-744 | EC-6A02...The value of the I-displacement parameter is not supported or is not in the range specified by | ✅ |
| PTOCA-4-745 | page 163 and Appendix B, “IPDS Environment”, for more information about valid ranges. | ✅ |
| PTOCA-4-746 | X'0000', the inline margin is at the B-axis. | ✅ |
| PTOCA-4-747 | EC-1001...The displacement parameter is not supported or is not in the range specified by PTOCA. | ✅ |
| PTOCA-4-748 | A space character or variable space character | ✅ |
| PTOCA-4-749 | A Begin Line control sequence | ✅ |
| PTOCA-4-750 | A Relative Move Inline control sequence | ✅ |
| PTOCA-4-751 | An Absolute Move Inline control sequence | ✅ |
| PTOCA-4-752 | decrement appears between graphic characters. | ✅ |
| PTOCA-4-753 | EC-1201...The value of the adjustment parameter or the direction parameter is not supported, or is not in the | ✅ |
| PTOCA-4-754 | range specified by PTOCA. | ✅ |
| PTOCA-4-755 | The stroked and filled areas of solid text characters, including overstrike characters. With hollow characters, | ✅ |
| PTOCA-4-756 | The stroked area of a rule. | ✅ |
| PTOCA-4-757 | The stroked area of an underscore. | ✅ |
| PTOCA-4-758 | 103 for the foreground color values and their associated colors. The default color attribute value is X'FF07'. | ✅ |
| PTOCA-4-759 | 1. Value set by Text Color initial text condition parameter in Descriptor | ✅ |
| PTOCA-4-760 | 2. PTOCA default – X'FF07'. | ✅ |
| PTOCA-4-761 | EC-5803...The foreground color parameter (FRGCOLOR) value is invalid, or the specified color is not | ✅ |
| PTOCA-4-762 | is a two-byte, three-part code of the form ABC. | ✅ |
| PTOCA-4-763 | A is a nine-bit binary number (bits 0 - 8) which provides from 0 through 359 degrees. Values from 360 | ✅ |
| PTOCA-4-764 | B is a six-bit binary number (bits 9 - 14) which provides from 0 through 59 minutes. Values from 60 through | ✅ |
| PTOCA-4-765 | 63 are invalid. | ✅ |
| PTOCA-4-766 | C is a one-bit reserved field (bit 15) which must be B'0'. | ✅ |
| PTOCA-4-767 | orientation values not within the range specified by PTOCA. | ✅ |
| PTOCA-4-768 | EC-6802...The I-axis is not parallel to the $X_p$ | ✅ |
| PTOCA-4-769 | EC-6902...The B-axis is not parallel to the $X_p$ | ✅ |
| PTOCA-5-001 | Describes exception condition detection | ✅ |
| PTOCA-5-002 | Describes exception responses and standard actions | ✅ |
| PTOCA-5-003 | Lists the PTOCA exception condition codes | ✅ |
| PTOCA-5-004 | Invalid or unsupported parameter value | ✅ |
| PTOCA-5-005 | Invalid or unsupported parameter | ✅ |
| PTOCA-5-006 | Invalid or unsupported control sequence | ✅ |
| PTOCA-5-007 | Syntactic | ✅ |
| PTOCA-5-008 | Semantic | ✅ |
| PTOCA-5-009 | Pragmatic | ✅ |
| PTOCA-5-010 | Invalid control sequence | ✅ |
| PTOCA-5-011 | Invalid parameter value | ✅ |
| PTOCA-5-012 | Control sequence appearing in invalid context | ✅ |
| PTOCA-5-013 | Selection of inconsistent or contradictory functions | ✅ |
| PTOCA-5-014 | Loss of presentation information | ✅ |
| PTOCA-5-015 | Mismatch of characteristics of Presentation Text object and presentation product | ✅ |
| PTOCA-5-016 | Unavailable resource, for example, coded font | ✅ |
| PTOCA-5-017 | Unavailable function, for example, overstrike | ✅ |
| PTOCA-5-018 | Unsupported control sequence | ✅ |
| PTOCA-5-019 | between an invalid parameter value and a parameter value out of the product's range. | ✅ |
| PTOCA-5-020 | exception conditions. If it does not, this detection must be performed by the receiver. | ✅ |
| PTOCA-5-021 | cannot process some of the Presentation Text object, the standard action could be to present it with | ✅ |
| PTOCA-5-022 | initiates the specified action, and is responsible for its satisfactory completion. | ✅ |
| PTOCA-5-023 | T erminate processing Presentation Text object | ✅ |
| PTOCA-5-024 | Ignore the control that caused the exception condition and continue processing the object | ✅ |
| PTOCA-5-025 | Partially process the control that caused the exception condition | ✅ |
| PTOCA-5-026 | Report exception condition back to generator or forward it to the presenter of the object | ✅ |
| PTOCA-5-027 | Cause an intervention-required condition to occur at the receiver | ✅ |
| PTOCA-5-028 | Mark the presentation information with diagnostic information | ✅ |
| PTOCA-5-029 | conditions received from the object processor. | ✅ |
| PTOCA-5-030 | Invalid or unsupported function type in control sequence. | ✅ |
| PTOCA-5-031 | Invalid control sequence or initial text condition parameter. | ✅ |
| PTOCA-5-032 | Invalid or unsupported initial text condition parameter identifier. | ✅ |
| PTOCA-5-033 | Control sequence or initial text condition parameter is not in the | ✅ |
| PTOCA-5-034 | A character has been positioned so that a portion of its character box | ✅ |
| PTOCA-5-035 | Caution - this exception condition is applicable only within a valid object | ✅ |
| PTOCA-5-036 | The active BSU LID is not the same as the LID specified in the ESU. | ✅ |
| PTOCA-5-037 | No active BSU LID when an ESU is processed. | ✅ |
| PTOCA-5-038 | BSU is encountered before the previous suppression has ended. | ✅ |
| PTOCA-5-039 | - or $Y_p$ | ✅ |
| PTOCA-5-040 | - or $Y_p$ | ✅ |
| PTOCA-5-041 | Baseline or inline orientation specified is not a valid or supported value. | ✅ |
| PTOCA-5-042 | The I and Borientations are identical. | ✅ |
| PTOCA-5-043 | Neither the I-direction nor the B-direction is parallel to the $X_p$ | ✅ |
| PTOCA-5-044 | The necessary mapping is not provided to support the specified coded | ✅ |
| PTOCA-5-045 | The specified coded font is not available to the receiver. | ✅ |
| PTOCA-5-046 | The target count parameter for RPS is invalid or unsupported. | ✅ |
| PTOCA-5-047 | The data string length for TRN or RPS is an odd number. It must be | ✅ |
| PTOCA-5-048 | The byte count specified for code points following UCT is an odd | ✅ |
| PTOCA-5-049 | number. It must be even for double–byte encoded data. | ✅ |
| PTOCA-5-050 | A high-order surrogate code value was not immediately followed by a | ✅ |
| PTOCA-5-051 | A low-order surrogate code value was not immediately preceded by a | ✅ |
| PTOCA-5-052 | An illegal UTF-8 byte sequence, as defined in the Unicode 3.2 | ✅ |
| PTOCA-5-053 | The RPS repeat length is an odd number when a double-byte font is | ✅ |
| PTOCA-5-054 | The class of a X'2B' control sequence is not X'D3'. | ✅ |
| PTOCA-5-055 | A required parameter has not been not specified. | ✅ |
| PTOCA-5-056 | Invalid control sequence or initial text condition parameter length. | ✅ |
| PTOCA-5-057 | Part of an optional parameter in a control sequence is missing. | ✅ |
| PTOCA-5-058 | A Coded Font LID has been omitted in a SCFL control sequence or in a | ✅ |
| PTOCA-5-059 | SVIcontrol sequence increment parameter is missing. | ✅ |
| PTOCA-5-060 | DBR or DIR length parameter is missing. | ✅ |
| PTOCA-5-061 | SIM displacement parameter is missing. | ✅ |
| PTOCA-5-062 | I-orientation parameter or B-orientation parameter is missing in an STO | ✅ |
| PTOCA-5-063 | The RPS control sequence length is four and the repeat length is not | ✅ |
| PTOCA-5-064 | Receiver is unable to support TBM by printing full size characters. | ✅ |
| PTOCA-5-065 | Receiver cannot support substitution character in the TBM field. | ✅ |
| PTOCA-5-066 | Temporary move size exceeds the device limitations. | ✅ |
| PTOCA-5-067 | Substitution method receiver cannot support multi-offset temporary | ✅ |
| PTOCA-5-068 | Character increment of overstrike character is less than or equal to | ✅ |
| PTOCA-5-069 | Character increment of overstrike character is less than the character- | ✅ |
| PTOCA-5-070 | Overstrike character is a non-printing character. | ✅ |
| PTOCA-5-071 | been set for decryption. | ✅ |
| PTOCA-6-001 | Describes the base level of PTOCA | ✅ |
| PTOCA-6-002 | Describes the PT1 subset of PTOCA | ✅ |
| PTOCA-6-003 | Describes the PT2 subset of PTOCA | ✅ |
| PTOCA-6-004 | Describes the PT3 subset of PTOCA | ✅ |
| PTOCA-6-005 | Describes the PT4 subset of PTOCA | ✅ |
| PTOCA-6-006 | States general requirements for compliance | ✅ |
| PTOCA-6-007 | Recognition of control sequences, chained or unchained | ✅ |
| PTOCA-6-008 | Interpretation and validation of the control sequence | ✅ |
| PTOCA-6-009 | Rejection of control sequences and parameters, including return of error data, that are not supported within | ✅ |
| PTOCA-6-010 | Reporting, on request from the controlling environment, the supported features | ✅ |
| PTOCA-6-011 | Reporting exception conditions to the controlling environment | ✅ |
| PTOCA-6-012 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-013 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-014 | 6-256 RPTDATA 8 | ✅ |
| PTOCA-6-015 | 6 DIRECTION X'00' 9 | ✅ |
| PTOCA-6-016 | 6 PRECISION X'00'-X'01' 3,10 | ✅ |
| PTOCA-6-017 | 6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3 | ✅ |
| PTOCA-6-018 | 1. This parameter is a signed binary number that may be positive or negative. Negative numbers are | ✅ |
| PTOCA-6-019 | 2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'. | ✅ |
| PTOCA-6-020 | 3. The default indicator is allowed, meaning obtain a value from the hierarchy. | ✅ |
| PTOCA-6-021 | 4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is | ✅ |
| PTOCA-6-022 | -units per unit base are assumed to be | ✅ |
| PTOCA-6-023 | 5. For the PTOCA range, see “Set Text Color (STC)”. The PT1 range is X'FF07'. | ✅ |
| PTOCA-6-024 | 6. The Begin Line (BLN) control sequence has no parameters. | ✅ |
| PTOCA-6-025 | 7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. The | ✅ |
| PTOCA-6-026 | 8. The Transparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does | ✅ |
| PTOCA-6-027 | 9. The default indicator is not allowed for this parameter in this subset. | ✅ |
| PTOCA-6-028 | 10. The STC PRECISION parameter has been retired; see “Retired Parameters”. New PTOCA | ✅ |
| PTOCA-6-029 | generators should not specify this parameter and new receivers should ignore it.. | ✅ |
| PTOCA-6-030 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-031 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-032 | 5-6 OVERCHAR X'0000'-X'FFFF' | ✅ |
| PTOCA-6-033 | 6-256 RPTDATA 8 | ✅ |
| PTOCA-6-034 | 6 DIRECTION X'00'-X'01' 3 | ✅ |
| PTOCA-6-035 | 6 PRECISION X'00'-X'01' 3,9 | ✅ |
| PTOCA-6-036 | 6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3 | ✅ |
| PTOCA-6-037 | 5 PRECISION X'00'-X'01' 3 | ✅ |
| PTOCA-6-038 | 6-7 INCRMENT X'0000'-X'7FFF' 3,4 | ✅ |
| PTOCA-6-039 | USC 4 BYPSIDEN X'00'-X'0E' 3 | ✅ |
| PTOCA-6-040 | 1. This parameter is a signed binary number that may be positive or negative. Negative numbers are | ✅ |
| PTOCA-6-041 | 2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'. | ✅ |
| PTOCA-6-042 | 3. The default indicator is allowed, meaning obtain a value from the hierarchy. | ✅ |
| PTOCA-6-043 | 4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is | ✅ |
| PTOCA-6-044 | 5. For the PTOCA range, see “Set Text Color (STC)”. The PT2 range is X'0000', X'FF00', | ✅ |
| PTOCA-6-045 | 6. The Begin Line (BLN) control sequence has no parameters. | ✅ |
| PTOCA-6-046 | 7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. | ✅ |
| PTOCA-6-047 | 8. The Transparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does | ✅ |
| PTOCA-6-048 | 9. The STC PRECISION parameter has been retired; see “Retired Parameters”. New PTOCA | ✅ |
| PTOCA-6-049 | generators should not specify this parameter and new receivers should ignore it. | ✅ |
| PTOCA-6-050 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-051 | 6-8 RWIDTH X'0000'-X'00C0' 1,2,3 | ✅ |
| PTOCA-6-052 | 5-6 OVERCHAR X'0000'-X'FFFF' | ✅ |
| PTOCA-6-053 | 6-256 RPTDATA 8 | ✅ |
| PTOCA-6-054 | 6 DIRECTION X'00'-X'01' 3 | ✅ |
| PTOCA-6-055 | 6 PRECISION X'00'-X'01' 3,9 | ✅ |
| PTOCA-6-056 | 6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3 | ✅ |
| PTOCA-6-057 | TBM 4 DIRECTION X'00'-X'03' 3 | ✅ |
| PTOCA-6-058 | 5 PRECISION X'00'-X'01' 3 | ✅ |
| PTOCA-6-059 | 6-7 INCRMENT X'0000'-X'7FFF' 3,4 | ✅ |
| PTOCA-6-060 | 1. This parameter is a signed binary number that may be positive or negative. Negative numbers are | ✅ |
| PTOCA-6-061 | 2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'. | ✅ |
| PTOCA-6-062 | 3. The default indicator is allowed, meaning obtain a value from the hierarchy. | ✅ |
| PTOCA-6-063 | 4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is | ✅ |
| PTOCA-6-064 | 5. For the PTOCA range, see “Set Text Color (STC)”. The PT3 range is X'0000', X'FF00', | ✅ |
| PTOCA-6-065 | 6. The Begin Line (BLN) control sequence has no parameters. | ✅ |
| PTOCA-6-066 | 7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. | ✅ |
| PTOCA-6-067 | 8. The Transparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does | ✅ |
| PTOCA-6-068 | 9. The STC PRECISION parameter has been retired; see “Retired Parameters”. New PTOCA | ✅ |
| PTOCA-6-069 | generators should not specify this parameter and new receivers should ignore it.. | ✅ |
| PTOCA-6-070 | 6 OIDLGTH 0, 13-129 | ✅ |
| PTOCA-6-071 | 7 FFNLGTH 0-(255-(10+OIDLGTH)) | ✅ |
| PTOCA-6-072 | 12 -n FONTOID (first byte) X'06' | ✅ |
| PTOCA-6-073 | 5-6 OVERCHAR X'0000'-X'FFFF' | ✅ |
| PTOCA-6-074 | 6-256 RPTDATA 7 | ✅ |
| PTOCA-6-075 | 6 DIRECTION X'00'-X'01' 2 | ✅ |
| PTOCA-6-076 | 6 PRECISION X'00'-X'01' 2,8 | ✅ |
| PTOCA-6-077 | 6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 2 | ✅ |
| PTOCA-6-078 | 5 PRECISION X'00'-X'01' 2 | ✅ |
| PTOCA-6-079 | 6-7 INCRMENT X'0000'-X'7FFF' 2,3 | ✅ |
| PTOCA-6-080 | 1. This parameter is a signed binary number that may be positive or negative. Negative numbers are | ✅ |
| PTOCA-6-081 | 2. The default indicator is allowed, meaning obtain a value from the hierarchy. | ✅ |
| PTOCA-6-082 | 3. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is | ✅ |
| PTOCA-6-083 | 4. For the PTOCA range, see “Set Text Color (STC)”. The PT4 range is the full Standard OCA | ✅ |
| PTOCA-6-084 | 5. The Begin Line (BLN) control sequence has no parameters. | ✅ |
| PTOCA-6-085 | 6. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. | ✅ |
| PTOCA-6-086 | 7. The Transparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does | ✅ |
| PTOCA-6-087 | 8. The STC PRECISION parameter has been retired; see “Retired Parameters”. New PTOCA | ✅ |
| PTOCA-6-088 | 9. The UCT must be chained to a GAR or GOR and is not rendered; all parameters are ignored. | ✅ |
| PTOCA-6-089 | General Requirements for Compliance | ✅ |
| PTOCA-A-001 | A set of rules that must be followed by all generators when constructing Presentation Text objects. | ✅ |
| PTOCA-A-002 | A set of Presentation Text processing capabilities that are guaranteed to be supported by all receivers. | ✅ |
| PTOCA-A-003 | characters it places in the object space are positioned so that they do not exceed the object space. | ✅ |
| PTOCA-A-004 | 164 PTOCA Reference | ✅ |
| PTOCA-A-005 | 0 CODE XPBASE X'00'–X'01' Unit base for X axis; must | ✅ |
| PTOCA-A-006 | 1 CODE YPBASE X'00' –X'01' Unit base for Y axis; must | ✅ |
| PTOCA-A-007 | 1. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is ten | ✅ |
| PTOCA-A-008 | 2. The default indicator is not allowed for this parameter in this subset. | ✅ |
| PTOCA-A-009 | 3. The TEXTFLAGS parameter is reserved. Generators should set this parameter to X'0000', and receivers | ✅ |
| PTOCA-A-010 | 4. See the description of the control sequence that specifies the initial text condition. | ✅ |
| PTOCA-A-011 | Presentation Text Structured Fields | ✅ |
| PTOCA-A-012 | Presentation Text Data Descriptor (PTD) | ✅ |
| PTOCA-A-013 | 166 PTOCA Reference | ✅ |
| PTOCA-A-014 | 0 CODE XPBASE X'00' Unit base for X axis; ten | ✅ |
| PTOCA-A-015 | 1 CODE YPBASE X'00' Unit base for Y axis; ten | ✅ |
| PTOCA-A-016 | Presentation Text Data (PTX) | ✅ |
| PTOCA-A-017 | Additional Related Structured Fields | ✅ |
| PTOCA-A-018 | 168 PTOCA Reference | ✅ |
| PTOCA-B-001 | The context of Presentation Text objects in the IPDS environment; as either text-major text or as independent | ✅ |
| PTOCA-B-002 | A comparison of PTOCA and IPDS exception conditions | ✅ |
| PTOCA-B-003 | IPDS commands specific to presentation text | ✅ |
| PTOCA-B-004 | Command structure and syntax | ✅ |
| PTOCA-B-005 | Initial conditions for the object | ✅ |
| PTOCA-B-006 | A means of reporting exception conditions | ✅ |
| PTOCA-B-007 | A means of resolving fonts and suppression identifiers | ✅ |
| PTOCA-B-008 | 1. All IPDS printers allow text to be placed directly within a logical page using the Write Text command. The | ✅ |
| PTOCA-B-009 | 2. Some IPDS printers support text objects (in addition to the text-major concept). In this case, the Write Text | ✅ |
| PTOCA-B-010 | specified in a text object. | ✅ |
| PTOCA-B-011 | 170 PTOCA Reference | ✅ |
| PTOCA-B-012 | 1. If a receiver cannot provide the color specified by FRGCOLOR, it may substitute values from Table 13 on | ✅ |
| PTOCA-B-013 | 2. The default indicator is allowed, meaning use the printer default. | ✅ |
| PTOCA-B-014 | 3. The TEXTFLAGS parameter is reserved. This parameter is not used in the IPDS environment. | ✅ |
| PTOCA-B-015 | 4. DIRCTION is always defaulted to X'00', that is, the positive direction, so this parameter is not carried in the | ✅ |
| PTOCA-B-016 | 5. The default indicator is not allowed for this parameter in this subset. | ✅ |
| PTOCA-B-017 | IPDS Text Command Set | ✅ |
| PTOCA-B-018 | 6 CODE Unit base X'00' | ✅ |
| PTOCA-B-019 | 7 X'00' Reserved X'00' | ✅ |
| PTOCA-B-020 | Presentation Exception Conditions | ✅ |
| PTOCA-B-021 | 172 PTOCA Reference | ✅ |
| PTOCA-B-022 | 0201..03 for text objects | ✅ |
| PTOCA-B-023 | Presentation Exception Conditions | ✅ |
| PTOCA-B-024 | Additional Related Commands | ✅ |
| PTOCA-B-025 | 174 PTOCA Reference | ✅ |
| PTOCA-B-026 | The external suppression value maps to the LID parameter of the BSU control sequence. For example, if the | ✅ |
| PTOCA-B-027 | The external suppression value maps to the LID through an equivalence table, using the current Load | ✅ |
| PTOCA-B-028 | Activate Resource (AR) | ✅ |
| PTOCA-B-029 | Deactivate Data-Object-Font Component (DDOFC) | ✅ |
| PTOCA-B-030 | Deactivate Font (DF) | ✅ |
| PTOCA-B-031 | Load Code Page (LCP) | ✅ |
| PTOCA-B-032 | Load Code Page Control (LCPC) | ✅ |
| PTOCA-B-033 | Write Object Container Control (WOCC) | ✅ |
| PTOCA-B-034 | Write Object Container (WOC) | ✅ |
| PTOCA-B-035 | XOH Erase Residual Font Data (ERFD) | ✅ |
| PTOCA-B-036 | XOH Erase Residual Print Data (ERPD) | ✅ |
| PTOCA-B-037 | Activate Resource (AR) | ✅ |
| PTOCA-B-038 | Deactivate Font (DF) | ✅ |
| PTOCA-B-039 | Load Code Page (LCP) | ✅ |
| PTOCA-B-040 | Load Code Page Control (LCPC) | ✅ |
| PTOCA-B-041 | Load Font (LF) | ✅ |
| PTOCA-B-042 | Load Font Character Set Control (LFCSC) | ✅ |
| PTOCA-B-043 | Load Font Equivalence (LFE) | ✅ |
| PTOCA-B-044 | XOH Erase Residual Font Data (ERFD) | ✅ |
| PTOCA-B-045 | Activate Resource (AR) | ✅ |
| PTOCA-B-046 | Deactivate Font (DF) | ✅ |
| PTOCA-B-047 | Load Font (LF) | ✅ |
| PTOCA-B-048 | Load Font Control (LFC) | ✅ |
| PTOCA-B-049 | Load Font Equivalence (LFE) | ✅ |
| PTOCA-B-050 | Load Font Index (LFI) | ✅ |
| PTOCA-B-051 | XOH Erase Residual Font Data (ERFD) | ✅ |
| PTOCA-B-052 | Activate Resource (AR) | ✅ |
| PTOCA-B-053 | Deactivate Font (DF) | ✅ |
| PTOCA-B-054 | Load Font Equivalence (LFE) | ✅ |
| PTOCA-B-055 | Load Symbol Set (LSS) | ✅ |
| PTOCA-B-056 | 176 PTOCA Reference | ✅ |
| PTOCA-C-001 | Describes retired functions that may occur in a PTOCA object | ✅ |
| PTOCA-C-002 | 5803 exists. The standard action in this case is to use X'FF07'. If PRECSION is X'01', and if the FRGCOLOR | ✅ |
| PTOCA-C-003 | 1. Value set by Text Color initial text condition parameter in Descriptor | ✅ |
| PTOCA-C-004 | 2. PTOCA default - X'00' | ✅ |
| PTOCA-C-005 | 1. Value previously set by Text Color initial text condition parameter in descriptor | ✅ |
| PTOCA-C-006 | 2. Data stream specified value | ✅ |
| PTOCA-C-007 | 3. Receiver's best possible value | ✅ |
| PTOCA-C-008 | 178 PTOCA Reference | ✅ |
| PTOCA-C-009 | 1. Receiver's best possible value | ✅ |
| PTOCA-C-010 | 2. Value previously set by Text Color initial text condition parameter in descriptor | ✅ |
| PTOCA-C-011 | 3. Data stream specified value | ✅ |
| PTOCA-C-012 | entirely coincidental. | ✅ |
| PTOCA-C-013 | 180 PTOCA Reference | ✅ |
| PTOCA-C-014 | Other company , product, or service names might be trademarks or service marks of others. | ✅ |
| PTOCA-C-015 | Mixed Object Document Content Architecture (MO:DCA); | ✅ |
| PTOCA-C-016 | Intelligent Printer Data Stream (IPDS) | ✅ |
| PTOCA-C-017 | AFP Line Data Architecture | ✅ |
| PTOCA-C-018 | Bar Code Object Content Architecture (BCOCA) | ✅ |
| PTOCA-C-019 | Color Management Object Content Architecture | ✅ |
| PTOCA-C-020 | Font Object Content Architecture (FOCA) | ✅ |
| PTOCA-C-021 | Graphics Object Content Architecture for AFP (AFP | ✅ |
| PTOCA-C-022 | Image Object Content Architecture (IOCA) | ✅ |
| PTOCA-C-023 | Metadata Object Content Architecture (MOCA) | ✅ |
| PTOCA-C-024 | Presentation Text Object Content Architecture (PTOCA) | ✅ |
| PTOCA-C-025 | 182 PTOCA Reference | ✅ |
| PTOCA-C-026 | AFP • application program | ✅ |
| PTOCA-C-027 | 184 PTOCA Reference | ✅ |
| PTOCA-C-028 | . See current baseline presentation coordinate. | ✅ |
| PTOCA-C-029 | . See current baseline print coordinate. | ✅ |
| PTOCA-C-030 | Bar Code command set • Bcoordinate | ✅ |
| PTOCA-C-031 | . See initial baseline print coordinate. | ✅ |
| PTOCA-C-032 | . For example, if P | ✅ |
| PTOCA-C-033 | . See baseline presentation origin. | ✅ |
| PTOCA-C-034 | B direction (B) • CGCSGID | ✅ |
| PTOCA-C-035 | 186 PTOCA Reference | ✅ |
| PTOCA-C-036 | CGPC • character reference point | ✅ |
| PTOCA-C-037 | character rotation • Code 39 | ✅ |
| PTOCA-C-038 | 188 PTOCA Reference | ✅ |
| PTOCA-C-039 | Code 128 • color management | ✅ |
| PTOCA-C-040 | Color Management Object Content Architecture (CMOCA) • control sequence | ✅ |
| PTOCA-C-041 | 190 PTOCA Reference | ✅ |
| PTOCA-C-042 | A TrueType/OpenType font, an optional code page, and | ✅ |
| PTOCA-C-043 | A TrueType/OpenType collection, either an index value | ✅ |
| PTOCA-C-044 | current drawing attributes • data object resource | ✅ |
| PTOCA-C-045 | 192 PTOCA Reference | ✅ |
| PTOCA-C-046 | Used to prepare for the presentation of a data object; | ✅ |
| PTOCA-C-047 | Included in a page or overlay via the Include Data Object | ✅ |
| PTOCA-C-048 | Invoked from within a data object; examples | ✅ |
| PTOCA-C-049 | data stream • device resolution | ✅ |
| PTOCA-C-050 | Print file (highest level) | ✅ |
| PTOCA-C-051 | Document | ✅ |
| PTOCA-C-052 | Page group | ✅ |
| PTOCA-C-053 | Data object (lowest level) | ✅ |
| PTOCA-C-054 | 300 dpi, there are 90,000 dots or bits of electronic data | ✅ |
| PTOCA-C-055 | device-version code page • dpi | ✅ |
| PTOCA-C-056 | 194 PTOCA Reference | ✅ |
| PTOCA-C-057 | error diffusion halftone • fixed medium information | ✅ |
| PTOCA-C-058 | 196 PTOCA Reference | ✅ |
| PTOCA-C-059 | fixed metrics • Font Typeface Global Identifier (FGID) | ✅ |
| PTOCA-C-060 | For fixed-pitch, uniform character increment fonts: the | ✅ |
| PTOCA-C-061 | For PSM fonts: the width of the space character | ✅ |
| PTOCA-C-062 | For typographic, proportionally spaced fonts: one-third of | ✅ |
| PTOCA-C-063 | Wherever the color attribute of P | ✅ |
| PTOCA-C-064 | . Likewise, | ✅ |
| PTOCA-C-065 | With other overlapping color values, the intersection | ✅ |
| PTOCA-C-066 | . In general, this mixing is a | ✅ |
| PTOCA-C-067 | . The intersection of the foregrounds | ✅ |
| PTOCA-C-068 | Wherever the color attribute of P | ✅ |
| PTOCA-C-069 | Wherever the color attribute of P | ✅ |
| PTOCA-C-070 | . In general, this mixing is a blending | ✅ |
| PTOCA-C-071 | font width (FW) • fully described font | ✅ |
| PTOCA-C-072 | 198 PTOCA Reference | ✅ |
| PTOCA-C-073 | Coded Character Set Identifier (CCSID) | ✅ |
| PTOCA-C-074 | Coded Graphic Character Set Global Identifier | ✅ |
| PTOCA-C-075 | Code Page Global ID (CPGID) | ✅ |
| PTOCA-C-076 | Font Typeface Global Identifier (FGID) | ✅ |
| PTOCA-C-077 | Global Resource Identifier (GRID) | ✅ |
| PTOCA-C-078 | Graphic Character Global Identifier (GCGID) | ✅ |
| PTOCA-C-079 | Graphic Character Set Global Identifier (GCSGID) | ✅ |
| PTOCA-C-080 | Graphic Character UCS Identifier (GCUID) | ✅ |
| PTOCA-C-081 | An identifier used by a data object to reference a | ✅ |
| PTOCA-C-082 | In MO:DCA, an encoded graphic character string that | ✅ |
| PTOCA-C-083 | Object identifier (OID) | ✅ |
| PTOCA-C-084 | A Uniform Resource Locator (URL), as defined in RFC | ✅ |
| PTOCA-C-085 | 1. GCSGID of a minimum set of graphic characters | ✅ |
| PTOCA-C-086 | 2. CPGID of the associated code page | ✅ |
| PTOCA-C-087 | 3. FGID of the associated font character set | ✅ |
| PTOCA-C-088 | 4. Font width in 1440ths of an inch. | ✅ |
| PTOCA-C-089 | 200 PTOCA Reference | ✅ |
| PTOCA-C-090 | For fixed-pitch, uniform character increment fonts: the | ✅ |
| PTOCA-C-091 | For PSM fonts: the width of the space character | ✅ |
| PTOCA-C-092 | For typographic fonts and proportionally spaced fonts: | ✅ |
| PTOCA-C-093 | guard bars • HSV color space | ✅ |
| PTOCA-C-094 | . See current inline presentation coordinate. | ✅ |
| PTOCA-C-095 | . See current inline print coordinate. | ✅ |
| PTOCA-C-096 | . See initial inline print coordinate. | ✅ |
| PTOCA-C-097 | human-readable interpretation (HRI) • image object | ✅ |
| PTOCA-C-098 | 202 PTOCA Reference | ✅ |
| PTOCA-C-099 | image object area • intercharacter gap | ✅ |
| PTOCA-C-100 | . See inline presentation origin. | ✅ |
| PTOCA-C-101 | intercharacter increment • JPEG File Interchange Format (JFIF) | ✅ |
| PTOCA-C-102 | 204 PTOCA Reference | ✅ |
| PTOCA-C-103 | Kanji • local area network (LAN) | ✅ |
| PTOCA-C-104 | 1 logical unit = 1/1440 inch | ✅ |
| PTOCA-C-105 | 1 logical unit = 1/240 inch | ✅ |
| PTOCA-C-106 | 1 L unit = 1/1440 inch | ✅ |
| PTOCA-C-107 | 1 L unit = 1/240 inch | ✅ |
| PTOCA-C-108 | Local Character Set Identifier (LCID) • maximum descender depth | ✅ |
| PTOCA-C-109 | 206 PTOCA Reference | ✅ |
| PTOCA-C-110 | meaning • MO:DCA IS/1 | ✅ |
| PTOCA-C-111 | . See also bilevel. | ✅ |
| PTOCA-C-112 | MO:DCA IS/2 • neutral white | ✅ |
| PTOCA-C-113 | 208 PTOCA Reference | ✅ |
| PTOCA-C-114 | non-presentation object • ordered page | ✅ |
| PTOCA-C-115 | orientation • page segment | ✅ |
| PTOCA-C-116 | 210 PTOCA Reference | ✅ |
| PTOCA-C-117 | . The proprietary P ANT ONE color matching | ✅ |
| PTOCA-C-118 | . A set of printer commands, developed by Hewlett- | ✅ |
| PTOCA-C-119 | Page-Segment command set • picket fence bar code | ✅ |
| PTOCA-C-120 | picture chain • presentation space orientation | ✅ |
| PTOCA-C-121 | 212 PTOCA Reference | ✅ |
| PTOCA-C-122 | presentation system • quantization | ✅ |
| PTOCA-C-123 | quiet zone • relative move | ✅ |
| PTOCA-C-124 | 214 PTOCA Reference | ✅ |
| PTOCA-C-125 | Fields or values that have been used by a product in a | ✅ |
| PTOCA-C-126 | Fields or values that have been removed from an | ✅ |
| PTOCA-C-127 | relative positioning • Royal Mail 4 State Customer Code (RM4SCC) | ✅ |
| PTOCA-C-128 | rule • sequential baseline | ✅ |
| PTOCA-C-129 | 216 PTOCA Reference | ✅ |
| PTOCA-C-130 | sequential baseline position • spot color | ✅ |
| PTOCA-C-131 | 218 PTOCA Reference | ✅ |
| PTOCA-C-132 | Document-processing applications can obtain resolution- | ✅ |
| PTOCA-C-133 | Device-service applications can obtain device-specific | ✅ |
| PTOCA-C-134 | symbol set • transform matrix | ✅ |
| PTOCA-C-135 | 1440 twips in one inch. | ✅ |
| PTOCA-C-136 | . This is also referred to as “transparent” or “leave | ✅ |
| PTOCA-C-137 | 220 PTOCA Reference | ✅ |
| PTOCA-C-138 | UTF-16 • variable space character | ✅ |
| PTOCA-C-139 | variable space character increment • X dimension | ✅ |
| PTOCA-C-140 | 222 PTOCA Reference | ✅ |
| PTOCA-C-141 | coordinate system • Yxy color space | ✅ |
| PTOCA-C-142 | 1 15, 1 17, 142 | ✅ |
| PTOCA-C-143 | EC-9601 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .55 | ✅ |
| PTOCA-C-144 | 224 PTOCA Reference | ✅ |
| PTOCA-C-145 | 1 10, 1 17, 141–143 | ✅ |
| PTOCA-C-146 | variable space character increment . . . . . . . . . . . . . . . . . . . . . . . . . 24, 108 | ✅ |
| PTOCA-C-147 | USC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 17 | ✅ |
| PTOCA-C-148 | ReferenceAFPC-0009-04 | ✅ |
