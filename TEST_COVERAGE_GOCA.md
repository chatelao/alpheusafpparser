# Granular Test Coverage - GOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| GOCA-1-001 | This chapter provides a brief overview of Presentation Architecture. | ✅ |
| GOCA-1-002 | Figure 1 shows today's presentation environment. | ✅ |
| GOCA-1-003 | The environment is a coordinated set of services architected to meet the presentation needs of today's applications. | ✅ |
| GOCA-1-004 | import/export | edit/revise | format | scan | ✅ |
| GOCA-1-005 | transform | store | retrieve | index | ✅ |
| GOCA-1-006 | search | extract | browse | navigate | ✅ |
| GOCA-1-007 | search | clip | annotate | tag | ✅ |
| GOCA-1-008 | print | submit | distribute | manage | ✅ |
| GOCA-1-009 | print | finish | ✅ |
| GOCA-1-010 | The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP presentation architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP presentation architectures provide structures that support object-oriented models and client/server environments. | ✅ |
| GOCA-1-011 | AFP presentation architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP presentation architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ✅ |
| GOCA-1-012 | The presentation architecture components are divided into two major categories: data streams and objects. | ✅ |
| GOCA-1-013 | A data stream is a continuous ordered stream of data elements and objects conforming to a given format. Application programs can generate data streams destined for a presentation service, archive library, presentation device, or another application program. The strategic presentation data stream architectures are: | ✅ |
| GOCA-1-014 | Mixed Object Document Content Architecture (MO:DCA)** | ✅ |
| GOCA-1-015 | Intelligent Printer Data Stream (IPDS) Architecture** | ✅ |
| GOCA-1-016 | The MO:DCA architecture defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. Documents defined in the MO:DCA format can be archived in a database, then later retrieved, viewed, annotated, and printed in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them. | ✅ |
| GOCA-1-017 | The IPDS architecture defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers. | ✅ |
| GOCA-1-018 | Figure 2 shows a system model relating MO:DCA and IPDS data streams to the presentation environment previously described. Also shown in the model are the object content architectures that apply to all levels of presentation processing in a system. | ✅ |
| GOCA-1-019 | This diagram shows the major components in a presentation system and their use of data stream and object architectures. | ✅ |
| GOCA-1-020 | Object Architectures** | ✅ |
| GOCA-1-021 | MO:DCA to presentation servers | ✅ |
| GOCA-1-022 | IPDS to printers and post processors | ✅ |
| GOCA-1-023 | Presentation Architecture Model** | ✅ |
| GOCA-1-024 | Intermediate Device | Application | ✅ |
| GOCA-1-025 | Post Processor | Display | ✅ |
| GOCA-1-026 | Printer | Library | ✅ |
| GOCA-1-027 | Print Services | Resource | ✅ |
| GOCA-1-028 | Viewing Services | Fonts | ✅ |
| GOCA-1-029 | Archive Services | Overlays | ✅ |
| GOCA-1-030 | Specifies open architectures and international standards that allow interoperability and portability of data, applications, and skills. | Page Segments | ✅ |
| GOCA-1-031 | Form Definition | ✅ |
| GOCA-1-032 | Color Management Resources | ✅ |
| GOCA-1-033 | Color Table | ✅ |
| GOCA-1-034 | Document Index | ✅ |
| GOCA-1-035 | Metadata | ✅ |
| GOCA-1-036 | Text | ✅ |
| GOCA-1-037 | Image | ✅ |
| GOCA-1-038 | Graphics | ✅ |
| GOCA-1-039 | Bar Codes | ✅ |
| GOCA-1-040 | Object Containers | ✅ |
| GOCA-1-041 | Other Objects | ✅ |
| GOCA-1-042 | A data object contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data. | ✅ |
| GOCA-1-043 | A resource object is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them. | ✅ |
| GOCA-1-044 | All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects that can be individually positioned and manipulated to meet the needs of the presentation application. | ✅ |
| GOCA-1-045 | The AFPC-defined object content architectures are: | ✅ |
| GOCA-1-046 | Presentation Text Object Content Architecture (PTOCA):** A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition. | ✅ |
| GOCA-1-047 | Image Object Content Architecture (IOCA):** A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition. | ✅ |
| GOCA-1-048 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA):** A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition. | ✅ |
| GOCA-1-049 | Bar Code Object Content Architecture (BCOCA):** A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition. | ✅ |
| GOCA-1-050 | Font Object Content Architecture (FOCA):** A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document. | ✅ |
| GOCA-1-051 | Color Management Object Content Architecture (CMOCA):** A resource architecture used to carry the color management information required to render presentation data. | ✅ |
| GOCA-1-052 | Metadata Object Content Architecture (MOCA):** A resource architecture used to carry metadata in an AFP environment. | ✅ |
| GOCA-1-053 | The MO:DCA and IPDS architectures also support data objects that are not defined by object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures. | ✅ |
| GOCA-1-054 | In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document. | ✅ |
| GOCA-1-055 | Figure 3 shows an example of an all-points-addressable page composed of multiple presentation objects. | ✅ |
| GOCA-1-056 | This is an example of a mixed-object page that can be composed in a device-independent MO:DCA format and can be printed on an IPDS printer. | ✅ |
| GOCA-1-057 | 205 Main Street | ✅ |
| GOCA-1-058 | Sales have improved so dramatically since you have joined our team, I would like to know your techniques. | ✅ |
| GOCA-1-059 | Presentation Text Object(s) | ✅ |
| GOCA-1-060 | Graphics Object | ✅ |
| GOCA-1-061 | Image Object | ✅ |
| GOCA-1-062 | Letterhead can be an overlay resource containing text, image, and graphics objects | ✅ |
| GOCA-1-063 | Object areas can overlap | ✅ |
| GOCA-2-001 | Background of Computer Graphics | ✅ |
| GOCA-2-002 | Scope of GOCA | ✅ |
| GOCA-2-003 | Concepts of GOCA | ✅ |
| GOCA-2-004 | The generation of pictures by computer, called computer graphics, has been an application area for many years. However, computer graphics is no longer the specialized concern of large businesses using expensive hardware and consuming vast programming and computing resources. Applications using computer graphics are now readily available for small businesses and the home. | ✅ |
| GOCA-2-005 | In general, the term computer graphics refers to the definition and representation of graphics elements used to build pictures for presentation, either on hard-copy devices such as printers and plotters, or on soft-copy devices such as vector or raster displays. Interactive computer graphics refers to the creation and manipulation of these composed pictures using end-user input devices such as a tablet, joystick, or mouse. | ✅ |
| GOCA-2-006 | GOCA is an object architecture used to represent pictures generated by computer. This document defines the version of the GOCA architecture that is supported in Advanced Function Presentation (AFP) environments for printing and viewing. | ✅ |
| GOCA-2-007 | Lines or arcs | ✅ |
| GOCA-2-008 | Characters or symbols | ✅ |
| GOCA-2-009 | Shaded areas or point arrays | ✅ |
| GOCA-2-010 | Line width or line type | ✅ |
| GOCA-2-011 | Orientation or direction | ✅ |
| GOCA-2-012 | Shading pattern or resolution | ✅ |
| GOCA-2-013 | In addition, there is usually a set of controls, such as environment-defined defaults, that apply to all primitives. | ✅ |
| GOCA-2-014 | GOCA is concerned with the creation and manipulation of pictures built by direct invocation of the above primitives and attributes. Thus, GOCA is restricted to the creation and modification of what is generally termed vector, or line-drawn, graphics. However, additional architectures can be built on top of GOCA for creating and manipulating more complex constructs such as graphs, histograms, and pie charts. | ✅ |
| GOCA-2-015 | GOCA effectively defines a graphics subsystem that can exist in, or be invoked by, a number of environments. Each of these controlling environments can be specialized for a particular application area. AFP GOCA is the version of GOCA used to present and interchange graphics pictures in AFP environments. See Appendix A, “Mixed Object Document Content Architecture (MO:DCA) Environment”, and Appendix B, “Intelligent Printer Data Stream (IPDS) Environment” for details of these environments. | ✅ |
| GOCA-3-001 | The concept of the graphics processor | ✅ |
| GOCA-3-002 | The environment interface | ✅ |
| GOCA-3-003 | The drawing processor, including: | ✅ |
| GOCA-3-004 | Primitives | ✅ |
| GOCA-3-005 | Drawing orders | ✅ |
| GOCA-3-006 | Attributes | ✅ |
| GOCA-3-007 | Graphics coordinate spaces | ✅ |
| GOCA-3-008 | Segments | ✅ |
| GOCA-3-009 | Subsetting | ✅ |
| GOCA-3-010 | Exception Conditions | ✅ |
| GOCA-3-011 | Intelligent Printer Data Stream (IPDS) printers with graphics capability | ✅ |
| GOCA-3-012 | Mixed Object Document Content Architecture (MO:DCA) data streams for interchange | ✅ |
| GOCA-3-013 | AFP GOCA deals with GOCA objects that are created, interchanged, archived, and presented within these two controlling environments. | ✅ |
| GOCA-3-014 | Environment interface | ✅ |
| GOCA-3-015 | Drawing processor | ✅ |
| GOCA-3-016 | Figure 4 shows the components and connections of the graphics processor. | ✅ |
| GOCA-3-017 | To/from controlling environment** | ✅ |
| GOCA-3-018 | Environment Interface** | ✅ |
| GOCA-3-019 | The GRAPHICS PROCESSOR (GP)** | ✅ |
| GOCA-3-020 | GP Storage** | ✅ |
| GOCA-3-021 | Drawing Processor** | ✅ |
| GOCA-3-022 | Resources** | ✅ |
| GOCA-3-023 | Color Tables | ✅ |
| GOCA-3-024 | Coded Fonts | ✅ |
| GOCA-3-025 | Graphics Presentation Space (GPS)** | ✅ |
| GOCA-3-026 | The GPS (containing the graphics picture) is merged onto the presentation surface in a manner dependent on the controlling environment. | ✅ |
| GOCA-3-027 | The environment interface performs the functions required to interface the graphics processor with the controlling environment and is responsible for examining the data passed to it from the controlling environment. | ✅ |
| GOCA-3-028 | Commands:** The only command supported in AFP GOCA is the Begin Segment (X'70') command, which is used to define a segment. | ✅ |
| GOCA-3-029 | Control Instructions:** The only control instruction supported in AFP GOCA is the Set Current Defaults (X'21') instruction, which sets the current default values of selected attributes. | ✅ |
| GOCA-3-030 | Drawing Orders:** These orders comprise the segment data. They generate graphics primitives in the Graphics Presentation Space (GPS) and set their attributes. | ✅ |
| GOCA-3-031 | The graphics picture is drawn in the GPS by the drawing processor, which executes a sequence of drawing orders. The drawing processor is started by the controlling environment, which in AFP GOCA is the MO:DCA or IPDS environment. | ✅ |
| GOCA-3-032 | Drawing orders whose execution affects the GPS are called primitive drawing orders. These orders cause the designated primitive to be mixed into the GPS. Additional drawing orders set drawing attributes. All drawing orders are sometimes simply referred to as orders. | ✅ |
| GOCA-3-033 | Line primitives | ✅ |
| GOCA-3-034 | Area primitives | ✅ |
| GOCA-3-035 | Character string primitives | ✅ |
| GOCA-3-036 | Marker primitives | ✅ |
| GOCA-3-037 | Pattern primitives | ✅ |
| GOCA-3-038 | Image primitives | ✅ |
| GOCA-3-039 | The parameters of a primitive drawing order | ✅ |
| GOCA-3-040 | Modal parameters called attributes | ✅ |
| GOCA-3-041 | Control instructions that contain the Set Current Defaults instruction, such as the MO:DCA Graphics Data Descriptor (GDD) and the IPDS Write Graphics Control (WGC) | ✅ |
| GOCA-3-042 | Modal parameters have values initialized by the environment and can be altered by attribute-setting drawing orders or by control instructions. Modal parameter values persist until they are explicitly altered, or until the end of the graphics object is encountered. | ✅ |
| GOCA-3-043 | Drawing orders are defined for each of the following types of output primitive: | ✅ |
| GOCA-3-044 | Line primitives:** | ✅ |
| GOCA-3-045 | Line and Relative Line | One or more straight lines connected together. | ✅ |
| GOCA-3-046 | Full Arc | A full circle or ellipse. | ✅ |
| GOCA-3-047 | Partial Arc | A line from a point to the start of an arc, then a portion of a full arc. | ✅ |
| GOCA-3-048 | Fillet | A curved line drawn tangentially to a specified set of contiguous straight lines. | ✅ |
| GOCA-3-049 | Cubic Bezier Curve | A third-order curve defined by a group of four points. | ✅ |
| GOCA-3-050 | Box** | A rectangle drawn with square or round corners. | ✅ |
| GOCA-3-051 | Area** | One or more closed figures that can be filled. The closed figures can overlap. | ✅ |
| GOCA-3-052 | Character String** | A series of characters drawn along a baseline starting at a specified point. | ✅ |
| GOCA-3-053 | Marker** | A symbol positioned by its center, and drawn at one or more points. | ✅ |
| GOCA-3-054 | Pattern** | A symbol that is repeated to fill an area. | ✅ |
| GOCA-3-055 | Image** | A rectangular area containing a set of foreground and background points. | ✅ |
| GOCA-3-056 | A summary list of the Drawing Orders is given in “Summary List of Orders”. | ✅ |
| GOCA-3-057 | Drawing attributes | ✅ |
| GOCA-3-058 | Line attributes | ✅ |
| GOCA-3-059 | Character attributes | ✅ |
| GOCA-3-060 | Marker attributes | ✅ |
| GOCA-3-061 | Pattern attributes | ✅ |
| GOCA-3-062 | Color** | The color in which the foreground bits of the output primitive are to be drawn. | ✅ |
| GOCA-3-063 | Mix** | Affects how the foreground of the output primitive that is being drawn is to be merged with the color information already in the GPS. | ✅ |
| GOCA-3-064 | Background Mix** | Affects how the background of the output primitive that is being drawn is to be merged with the color information already in the GPS. | ✅ |
| GOCA-3-065 | Sets of mix and color attributes are provided for each type of primitive. | ✅ |
| GOCA-3-066 | Line Type** | The type of line to be drawn; for example, solid or dashed. | ✅ |
| GOCA-3-067 | Line Width** | The width of line to be drawn; for example, normal or wide. | ✅ |
| GOCA-3-068 | Line End** | The type of ending of stroked lines; for example, flat, square, or round. | ✅ |
| GOCA-3-069 | Line Join** | The type of joining of stroked lines; for example, round, bevel, or miter. | ✅ |
| GOCA-3-070 | Attribute | Meaning | ✅ |
| GOCA-3-071 | Character Precision** | The requested appearance fidelity of a character string. | ✅ |
| GOCA-3-072 | Character Shear** | The amount of slope of a character string. This attribute is not supported in AFP GOCA. | ✅ |
| GOCA-3-073 | Character Angle** | The angle between the character baseline and the GPS $X_g$ axis. Only values of 0°, 90°, 180°, and 270° are supported in AFP GOCA. | ✅ |
| GOCA-3-074 | Character Cell** | The size of the cell in which a character is drawn. | ✅ |
| GOCA-3-075 | Character Direction** | The direction in which characters are drawn. | ✅ |
| GOCA-3-076 | Character Set** | The set of symbols from which characters are obtained. | ✅ |
| GOCA-3-077 | Attribute | Meaning | ✅ |
| GOCA-3-078 | Marker Cell** | The size of the cell in which a marker is drawn. | ✅ |
| GOCA-3-079 | Marker Set** | The set of symbols from which the marker is obtained. | ✅ |
| GOCA-3-080 | Marker Symbol** | The particular symbol that is to be used to draw markers. | ✅ |
| GOCA-3-081 | Attribute | Meaning | ✅ |
| GOCA-3-082 | Pattern Set** | The set of symbols from which the area fill pattern is obtained. | ✅ |
| GOCA-3-083 | Pattern Symbol** | The particular symbol that is to be used as a fill pattern when filling an area. | ✅ |
| GOCA-3-084 | Pattern Reference Point** | The point that determines the positioning of a custom fill pattern. | ✅ |
| GOCA-3-085 | Note:** The Arc Parameters also specify characteristics of output primitives. In this, they act in a way very similar to attributes, but are conceptually distinct. | ✅ |
| GOCA-3-086 | Drawing Order Coordinate Space (DOCS) | ✅ |
| GOCA-3-087 | Graphics Presentation Space (GPS) | ✅ |
| GOCA-3-088 | The DOCS is the coordinate space in which the drawing orders specify graphics primitives. Points are described in the drawing orders by specifying the x and y coordinates in the DOCS. Extents and offsets are described in the drawing orders by specifying the x and y extents and offsets in the DOCS. The DOCS is a standard, 2-dimensional Cartesian coordinate system. Units of measure for the DOCS are specified in the Graphics Data Descriptor. In AFP GOCA, there is a one-to-one mapping between the DOCS coordinate system and its units of measure and the GPS coordinate system and its units of measure. Therefore in AFP GOCA, DOCS and GPS are equivalent coordinate systems. All references to coordinate systems in AFP GOCA will be made with respect to GPS. | ✅ |
| GOCA-3-089 | The GPS is the space in which the application user's view of the specified picture is generated. The GPS is a standard, 2-dimensional Cartesian coordinate system as shown in Figure 5. Coordinates in the GPS coordinate system are denoted by $(X_g, Y_g)$. Units of measure for the GPS are specified in the Graphics Data Descriptor. | ✅ |
| GOCA-3-090 | AFP GOCA uses 16-bit signed integers to specify GPS coordinates. A point outside GPS is characterized by a 2-byte arithmetic overflow. For a definition of the geometric parameter format used in AFP GOCA, see “Parameter Type” and “Drawing Order Subset”. | ✅ |
| GOCA-3-091 | The usable area is a presentation space and coordinate system defined by the controlling environment. It is the space in which the implementation presents the picture to the end user, and merges the GPS with other presentation spaces in the device. | ✅ |
| GOCA-3-092 | The controlling environment defines a GPS window on the GPS, and a graphics window mapping between the GPS window and the UA. In the AFP environment, the usable area is a MO:DCA or IPDS object area, which is merged with other object areas on a logical page presentation space. | ✅ |
| GOCA-3-093 | The color values specified using the Set Color and Set Extended Color drawing orders generate an index into the standard color table defined in Table 5. When a primitive is drawn, this color index is mixed with the color index of the GPS using the current mix and background mix attributes. The resulting color index of the GPS can be further modified by drawing another primitive at the same point in the GPS. When drawing is complete, the final color index is used to look up the current color value. The values in the color table control the physical process whereby colors are presented on the presentation surface. | ✅ |
| GOCA-3-094 | The standard color table is accessed by two-byte color index values. These values are the valid color index values that can be specified in the Set Extended Color order and the Set Color order. The value specified in the Set Color order is prefixed with X'FF' to generate a two-byte color index value. | ✅ |
| GOCA-3-095 | Table 5 shows the meanings of the two-byte values. The table also specifies the RGB values that can be used for each named color, assuming that each component is specified with 8 bits and that the component intensity range 0 to 1 is mapped to the binary value range 0 to 255. | ✅ |
| GOCA-3-096 | X'0000' or X'FF00' | Drawing default | - | - | - | ✅ |
| GOCA-3-097 | X'0001' or X'FF01' | Blue | 0 | 0 | 255 | ✅ |
| GOCA-3-098 | X'0002' or X'FF02' | Red | 255 | 0 | 0 | ✅ |
| GOCA-3-099 | X'0003' or X'FF03' | Pink/magenta | 255 | 0 | 255 | ✅ |
| GOCA-3-100 | X'0004' or X'FF04' | Green | 0 | 255 | 0 | ✅ |
| GOCA-3-101 | X'0005' or X'FF05' | Turquoise/cyan | 0 | 255 | 255 | ✅ |
| GOCA-3-102 | X'0006' or X'FF06' | Yellow | 255 | 255 | 0 | ✅ |
| GOCA-3-103 | X'0007' | White; see Note 1 | 255 | 255 | 255 | ✅ |
| GOCA-3-104 | X'0008' | Black | 0 | 0 | 0 | ✅ |
| GOCA-3-105 | X'0009' | Dark blue | 0 | 0 | 170 | ✅ |
| GOCA-3-106 | X'000A' | Orange | 255 | 128 | 0 | ✅ |
| GOCA-3-107 | X'000B' | Purple | 170 | 0 | 170 | ✅ |
| GOCA-3-108 | X'000C' | Dark green | 0 | 146 | 0 | ✅ |
| GOCA-3-109 | X'000D' | Dark turquoise | 0 | 146 | 170 | ✅ |
| GOCA-3-110 | X'000E' | Mustard | 196 | 160 | 32 | ✅ |
| GOCA-3-111 | X'000F' | Gray | 131 | 131 | 131 | ✅ |
| GOCA-3-112 | X'0010' | Brown | 144 | 48 | 0 | ✅ |
| GOCA-3-113 | X'FF07' | Presentation-process default; see Note 2 | - | - | - | ✅ |
| GOCA-3-114 | X'FF08' | Color of medium | - | - | - | ✅ |
| GOCA-3-115 | All others | Reserved | - | - | - | ✅ |
| GOCA-3-116 | 1. The color rendered on presentation devices that do not support white is device dependent. For example, some printers simulate with color of medium, which results in white if white media is used. | ✅ |
| GOCA-3-117 | 2. The presentation-process default specified by X'FF07' is resolved as the presentation device default. This color value is also known in GOCA as neutral white for compatibility with display devices. | ✅ |
| GOCA-3-118 | 3. While the RGB values in the table can be used to render the OCA named colors, many implementations are and have been device dependent. Nevertheless, it is recommended that OCA Black (X'0008') be rendered as C = M = Y = X'00', and K = X'FF'. | ✅ |
| GOCA-3-119 | The standard color table is equivalent to the Standard OCA Color Value Table defined in the MO:DCA controlling environment; see the Mixed Object Document Content Architecture (MO:DCA) Reference for the definition of this table. | ✅ |
| GOCA-3-120 | Process colors, using the RGB, CMYK, and CIELAB color spaces. | ✅ |
| GOCA-3-121 | Spot colors, using the highlight color space. | ✅ |
| GOCA-3-122 | Named colors, using the standard OCA color space. This is the color space that is supported by the Set Color and Set Extended Color drawing orders. For definitions of the color values used in this color space, see Table 5. | ✅ |
| GOCA-3-123 | Note:** When the standard OCA color space is selected with the Set Process Color drawing order, colors for foreground data are mixed into the GPS in the same manner as described for the Set Color and Set Extended Color orders. However, when any other color space is selected, colors for foreground data always overpaint the GPS. | ✅ |
| GOCA-3-124 | If two output primitives drawn into the GPS have a common point, they are mixed at that point to produce a result that is held at that point. The output primitives exist independently in segments, but do not exist independently in the GPS. There is no concept of the GPS having layers with the output primitives underlying and overlying one another at points of the space. | ✅ |
| GOCA-3-125 | AFP GOCA Graphics** | *   Stroked area of lines (including arcs)<br>*   Stroked and filled portion of pattern symbols<br>*   Stroked and filled portion of marker symbols<br>*   Stroked and filled portion of graphic characters<br>*   B'1' image points<br>*   Entire area with solid fill | Everything else | ✅ |
| GOCA-3-126 | Mixing applies only to those points of the GPS to which an output primitive is being drawn. The GPS always contains the result of the mixing of the output primitives currently drawn in the GPS. When a new output primitive is drawn into the GPS, each foreground or background point of the output primitive is combined with the corresponding point of the GPS to produce a new result in the GPS. Mixing is always an effect of a foreground or a background value of an output primitive on an existing GPS value. | ✅ |
| GOCA-3-127 | Table 6 summarizes the definition of foreground and background in the GPS. | ✅ |
| GOCA-3-128 | Note:** A custom pattern or gradient is mixed into the GPS in the same way as are patterns in the default pattern set: only stroked and filled portions of the custom pattern or gradient are foreground. For gradients, however, the entire gradient, as well as any outside areas for which the Outside value was not specified as “None”, is considered stroked and filled, even if the color white occurs. | ✅ |
| GOCA-3-129 | Implementation Note:** If a color fill of an area is simulated with a pattern fill, the complete fill is considered foreground, not just the stroked and filled portion of the pattern symbols. | ✅ |
| GOCA-3-130 | The attributes of mix and background mix specify the method by which the output-primitive color value is combined with the existing color value of each point of the GPS. These two mixing capabilities are not always the same mixing attribute value. For example, assume that the GPS contains a line on which the controlling environment wants to mix a character A, such that the background of that character does not interfere with the line. The application chooses Overpaint for the foreground mix attribute of the character and Leave Alone for the background mix attribute of the character. | ✅ |
| GOCA-3-131 | Every point of the GPS is background until points are drawn in GPS. The new color value of the current point of the GPS is obtained by applying the appropriate mix attribute to the existing value for that point with the appropriate, foreground or background, color value for the corresponding point of the output primitive being applied. | ✅ |
| GOCA-3-132 | The mix attributes are selected by use of the Set Mix or Set Background Mix orders. | ✅ |
| GOCA-3-133 | In the description that follows, the term **source** means the foreground, or background, of the primitive that is being drawn. The term **destination** means the area of the GPS on which the foreground or background of that primitive is being drawn. | ✅ |
| GOCA-3-134 | X'00' Drawing default:** This resets the mix attribute to its initial value. | ✅ |
| GOCA-3-135 | X'02' Overpaint:** The color value of the source replaces the color value of the destination. This is also sometimes referred to as opaque mixing. | ✅ |
| GOCA-3-136 | X'00' Drawing default:** This resets the mix attribute to its initial value. | ✅ |
| GOCA-3-137 | X'05' Leave Alone:** The color value of the destination is unchanged. This is also sometimes referred to as transparent mixing. | ✅ |
| GOCA-3-138 | Segments are self-contained collections of drawing orders and attributes. They are the basic units from which a picture is constructed. A segment can be given a name defined as a four-byte unsigned integer; however, this name is ignored in AFP GOCA. | ✅ |
| GOCA-3-139 | Facilities are provided to permit the chaining of segments during the process of describing a complete picture. Chaining is the unidirectional passing of control from one segment to another segment. | ✅ |
| GOCA-3-140 | Every segment is either chained or unchained. A collection of one or more chained segments defines the picture to be drawn. Unchained segments are ignored in AFP GOCA. Chaining provides a known and architected initial state for the chained segments. Therefore, chained segments are completely independent pieces of the picture. | ✅ |
| GOCA-3-141 | Drawing Level 2 Version 0 (DR/2V0).** The DR/2V0 subset is also referred to as “GRS2”. | ✅ |
| GOCA-3-142 | Graphics Subset Level 3 (GRS3).** The GRS3 subset includes additional functionality above DR/2V0. | ✅ |
| GOCA-3-143 | These subsets are supported in both the MO:DCA and IPDS environments. See Chapter 9, “Compliance” for details of these subsets. | ✅ |
| GOCA-3-144 | Exception conditions are defined by AFP GOCA for detectable errors in the syntax of GOCA constructs. They are reported to the controlling environment in an environment dependent manner. | ✅ |
| GOCA-3-145 | If the environment determines that processing can proceed, then for some of the exception conditions, AFP GOCA defines a standard action that is to be taken after the error is detected. For the other exception conditions, the environment must determine the continuation procedure. | ✅ |
| GOCA-4-001 | Output primitives in general | ✅ |
| GOCA-4-002 | Current position | ✅ |
| GOCA-4-003 | The symbols used to draw characters, markers, and shading patterns in areas | ✅ |
| GOCA-4-004 | The following output primitives and their associated attributes: | ✅ |
| GOCA-4-005 | Character strings | ✅ |
| GOCA-4-006 | Markers | ✅ |
| GOCA-4-007 | Images | ✅ |
| GOCA-4-008 | Output primitive overflow | ✅ |
| GOCA-4-009 | Output primitives are the basic element from which graphics pictures are built. They are drawn by one or more drawing orders containing the parameters that define the primitive. | ✅ |
| GOCA-4-010 | Primitives also use the modal parameters called attributes associated with them, as well as the general drawing process controls. | ✅ |
| GOCA-4-011 | The architecture defines exception conditions for invalid values of parameters within drawing orders and assigns exception condition codes, EC-xxxx, to these for reporting purposes. See “Drawing Order Exceptions” for details. | ✅ |
| GOCA-4-012 | Current position is a position in Graphics Presentation Space (GPS) remembered by the drawing processor. Current position is updated by the drawing processor as each output primitive value is executed. It is maintained as an $(X_g, Y_g)$ coordinate value in GPS. With the drawing orders that are described in Chapter 7, “Commands and Drawing Orders”, this updating of current position can, in general, be implemented by replacing the old value of current position by an $(X_g, Y_g)$ coordinate from the order being executed. | ✅ |
| GOCA-4-013 | With the first form, all coordinates required to draw the output primitive are contained in the order itself. | ✅ |
| GOCA-4-014 | With the second form, the current position is used as the first pair of coordinate values of the output primitive. | ✅ |
| GOCA-4-015 | The second form of drawing order is shorter than the first form. The second form is used when the initial coordinate of an order is the current position as established by the previous order, and effectively connects the primitives together. | ✅ |
| GOCA-4-016 | The drawing order Set Current Position is provided to manipulate current position. | ✅ |
| GOCA-4-017 | Current position is set to the origin of GPS—that is, $(X_g=0, Y_g=0)$—at the beginning of each new segment and at the beginning of each new custom pattern definition. | ✅ |
| GOCA-4-018 | Characters | ✅ |
| GOCA-4-019 | Markers | ✅ |
| GOCA-4-020 | Shading patterns in areas | ✅ |
| GOCA-4-021 | A particular symbol can be used as a character, as a marker, or as a pattern. | ✅ |
| GOCA-4-022 | For character symbols, the controlling environment provides access to sets of symbols by resolving the local identifier of the character set. | ✅ |
| GOCA-4-023 | The minimum degree of accuracy required for the appearance of the symbols is determined by the value of the character precision attribute. | ✅ |
| GOCA-4-024 | The method of defining a symbol does not limit the operations that can be applied to that symbol. Therefore, the method of symbol definition does not tie that symbol to a particular level of precision. An implementation can choose to support only certain precisions for particular types of symbol definition. Subsets may define what precision is required to be supported. | ✅ |
| GOCA-4-025 | Precision and the method by which symbols are defined are independent of each other. | ✅ |
| GOCA-4-026 | To draw a symbol, an $x,y$ position, a symbol set, and a code point are specified. | ✅ |
| GOCA-4-027 | If the requested symbol set does not exist, the appropriate exception condition is raised. The standard action for this exception is to use the appropriate standard default set. | ✅ |
| GOCA-4-028 | If the code point identifies a symbol that is not valid or not defined, the appropriate exception condition is raised. The standard action for this exception is to use the appropriate standard default symbol. | ✅ |
| GOCA-4-029 | Markers, patterns, and characters are all examples of symbols. Parts of the loading mechanism and handling facilities are common to all types of symbols. | ✅ |
| GOCA-4-030 | Table 7 summarizes how attributes are set when symbols are used for characters, markers, and patterns. | ✅ |
| GOCA-4-031 | Color** | Set Color, Set Extended Color, or Set Process Color orders | Set Color, Set Extended Color, or Set Process Color orders | Set Color, Set Extended Color, or Set Process Color orders | ✅ |
| GOCA-4-032 | (Foreground) Mix** | Set Mix order | Set Mix order | Set Mix order | ✅ |
| GOCA-4-033 | Background Mix** | Set Background Mix order | Set Background Mix order | Set Background Mix order | ✅ |
| GOCA-4-034 | Precision** | Set Character Precision order | Reserved | Reserved | ✅ |
| GOCA-4-035 | Shear** | Set Character Shear order | Reserved | Reserved | ✅ |
| GOCA-4-036 | Angle** | Set Character Angle order | Reserved | Reserved | ✅ |
| GOCA-4-037 | Cell Size** | Set Character Cell order | Set Marker Cell order | Reserved | ✅ |
| GOCA-4-038 | Direction** | Set Character Direction order | Not applicable | Not applicable | ✅ |
| GOCA-4-039 | Set** | Set Character Set order | Set Marker Set order | Set Pattern Set order | ✅ |
| GOCA-4-040 | Code Point** | Character String order | Set Marker Symbol order | Set Pattern Symbol order | ✅ |
| GOCA-4-041 | Reference Position** | Character String order | Marker order | Set Pattern Reference Point order for a custom pattern; not applicable for a gradient; otherwise, device default | ✅ |
| GOCA-4-042 | In raster symbol definitions and in fully described and outline fonts, the foreground color of the symbol is always the current character color attribute value. | ✅ |
| GOCA-4-043 | Straight Lines | ✅ |
| GOCA-4-044 | Curved Lines | ✅ |
| GOCA-4-045 | The **Line** order draws one or more contiguous straight lines by providing the endpoints of each line. | ✅ |
| GOCA-4-046 | The **Relative Line** order draws one or more contiguous straight lines by using offset values. | ✅ |
| GOCA-4-047 | Line at Given Position (GLINE) order | ✅ |
| GOCA-4-048 | Line at Current Position (GCLINE) order | ✅ |
| GOCA-4-049 | The current values of the line attributes are taken into account when the lines are drawn. Current position is set to the last point specified in the order. | ✅ |
| GOCA-4-050 | Relative Line at Given Position (GRLINE) order | ✅ |
| GOCA-4-051 | Relative Line at Current Position (GCRLINE) order | ✅ |
| GOCA-4-052 | The parameters of the order include an initial position, $(X_0, Y_0)$, and a set of offset values, $\{D_1, E_1\}, \dots, \{D_n, E_n\}$. The offsets are one-byte values that give the end point of a line relative to the start of that same line; that is, the differences in the $x,y$ coordinate values of the start and end points of the line. Negative values for these offsets are permitted. | ✅ |
| GOCA-4-053 | Straight lines are drawn between the points $(X_0, Y_0), (X_0 + D_1, Y_0 + E_1), (X_0 + D_1 + D_2, Y_0 + E_1 + E_2), \dots, (X_0 + D_1 + \dots + D_n, Y_0 + E_1 + \dots + E_n)$. | ✅ |
| GOCA-4-054 | The current values of the line attributes are taken into account when the relative lines are drawn. Current position is set to the last point calculated. | ✅ |
| GOCA-4-055 | Note:** The straight lines are drawn so that the line width is centered on the specified points. | ✅ |
| GOCA-4-056 | Full Arc | ✅ |
| GOCA-4-057 | Partial Arc | ✅ |
| GOCA-4-058 | Fillet | ✅ |
| GOCA-4-059 | Cubic Bezier Curve | ✅ |
| GOCA-4-060 | An ellipse with semi-axes $B = Multiplier \cdot b$, where $b$ is defined by Arc Parameters. | ✅ |
| GOCA-4-061 | Full Arc orders use the current value of the arc parameters to define the primitive. The arc parameters specify the required shape and size of an ellipse, which can be a circle. The arc parameters also specify the direction in which the ellipse is drawn: clockwise or counterclockwise. | ✅ |
| GOCA-4-062 | The Set Arc Parameters order sets the current value of the arc parameters. The arc parameters are shown in Figure 6. | ✅ |
| GOCA-4-063 | $Y' = S \cdot X + Q \cdot Y$ | ✅ |
| GOCA-4-064 | where $X$ and $Y$ are the coordinates of the points on the unit circle, and $X'$ and $Y'$ are the coordinates of the points on the defined ellipse. Note that the unit circle has a radius of 1 GPS unit. | ✅ |
| GOCA-4-065 | If $P \cdot R + S \cdot Q = 0$, the transform is termed **orthogonal** and the line from the origin (0,0) to the point $(P,S)$ is either a radius of the circle, or half the major/minor axis of the ellipse. The line from the origin to the point $(R,Q)$ is either the radius of the circle, or half the minor/major axis of the ellipse. | ✅ |
| GOCA-4-066 | If $P \cdot Q = R \cdot S$, the ellipse degenerates to a straight line or a point. If $P = Q = r$ and $R = S = 0$, the ellipse degenerates to a circle with radius $r$. | ✅ |
| GOCA-4-067 | If $P \cdot Q - R \cdot S > 0$, the direction is counterclockwise. | ✅ |
| GOCA-4-068 | If $P \cdot Q - R \cdot S < 0$, the direction is clockwise. | ✅ |
| GOCA-4-069 | Implementation Note:** For historical reasons, not all output devices have supported the directionality of arcs based on the determinant. However, all output devices that support the Cubic Bezier Curve (GCBEZ, GCCBEZ) drawing orders must support arc directionality based on the determinant. | ✅ |
| GOCA-4-070 | The Full Arc order draws one complete circle, or a complete ellipse. The parameters of this order are the center point, and a multiplier that specifies by how much the ellipse or circle defined by the Set Arc Parameters order is to be scaled, before being drawn. The Full Arc circle or ellipse is drawn either in a clockwise or counterclockwise direction, depending on the direction of the ellipse as defined by the arc parameters. Figure 7 shows the generation of an ellipse. The small ellipse at the origin is defined by the Set Arc Parameters order with minor axis $2b$. The Full Arc drawing order transforms this ellipse into an ellipse with center at current position or a specified point, and with a multiplier such that the new minor axis $2B = Multiplier \cdot 2b$. The major axis is scaled in the same manner. | ✅ |
| GOCA-4-071 | The current values of the line attributes are taken into account when each full arc is drawn. Current position is set to the center point of the arc. | ✅ |
| GOCA-4-072 | Note:** The parameter values are specified in GPS L-units. | ✅ |
| GOCA-4-073 | A line from current position to the start of an arc, followed by the partial arc defined by Start and Sweep angles. | ✅ |
| GOCA-4-074 | The Partial Arc primitive draws a line from a specified point or current position to the start of an arc, and then draws the arc. | ✅ |
| GOCA-4-075 | The arc is part of the full arc defined by the current arc parameters and the multiplier $M$. The center of the arc is at a point specified within the Partial Arc drawing order. The part of the arc that is drawn is defined by the start-angle and sweep-angle parameters. The direction of drawing is determined by the arc parameters. | ✅ |
| GOCA-4-076 | The start angle is the angle between the X axis of the unit circle space and the radius drawn from the center of the arc to the start point of the arc. The sweep angle is the angle subtended at the center of the arc by the two radii drawn from the center of the unit circle to the start and end points of the arc; see Figure 8. | ✅ |
| GOCA-4-077 | Both angles are specified in the unit-circle space, and hence are transformed by an amount defined by the current arc parameters in the same way that the unit circle is transformed. If the partial arc is part of a circle, the angles following the transform will be the same as the angles on the unit circle. If the partial arc is part of an ellipse, the angles following the transform will, in general, be different than the angles on the unit circle. | ✅ |
| GOCA-4-078 | The Partial Arc order can draw arcs with sweep angles greater than or equal to 0° and less than 360°; it cannot draw a complete 360° arc. The Full Arc drawing order can be used to draw a complete arc. | ✅ |
| GOCA-4-079 | The current values of the line attributes are taken into account when the partial arc is drawn. Current position is set to the endpoint of the arc. | ✅ |
| GOCA-4-080 | A curve tangential to straight lines $P_0-P_1, P_1-P_2, \dots$ | ✅ |
| GOCA-4-081 | This primitive is drawn using the Fillet order. The parameters of the order are the $(X_g, Y_g)$ coordinates for a set of points $P_0, P_1, \dots, P_n$. | ✅ |
| GOCA-4-082 | The points specified in the order are joined by conceptual straight lines, to which a curve is fitted. The curve is tangential to the first line at its start point, and to the last line at its end point. If there are more than two lines, the curve is tangential to the intermediate lines at their center points. If only two points are supplied, a straight line is drawn between the points. | ✅ |
| GOCA-4-083 | Architecture Note:** The Fillet drawing order does not support specification of a sharpness parameter. In Figure 9, this parameter would determine how close the drawn curve comes to the points $P_1, P_2,$ and $P_3$. If a quadrant of a circle or quadrant of an ellipse is used to fit the points, the sharpness parameter is not required since the circle or ellipse is completely defined by completing the parallelogram. If a quadrant arc is not used, a sharpness parameter can be used and is defined, in reference to Figure 9, as follows: | ✅ |
| GOCA-4-084 | 1.  Generate the virtual line $P_0M_1$ | ✅ |
| GOCA-4-085 | 2.  Find the midpoint of this line, $V_0$ | ✅ |
| GOCA-4-086 | 3.  Generate $V_0P_1$ | ✅ |
| GOCA-4-087 | 4.  Call the point where $V_0P_1$ intersects the arc $D_1$ | ✅ |
| GOCA-4-088 | 5.  The sharpness parameter is defined to be the ratio of $V_0D_1 \div D_1P_1$ | ✅ |
| GOCA-4-089 | The recommended value for the sharpness parameter, when used in AFP GOCA, is 0.7. | ✅ |
| GOCA-4-090 | The current values of the line attributes are taken into account when the fillet is drawn. Current position is set to the last point specified. | ✅ |
| GOCA-4-091 | The curve that is drawn is computed as follows (see Figure 9): | ✅ |
| GOCA-4-092 | 1.  Let the points specified in the order be known as $P_0, P_1, \dots, P_n$. | ✅ |
| GOCA-4-093 | 2.  Conceptual lines are drawn between the points $P_0$ to $P_1, $ $P_1$ to $P_2, $ $P_2$ to $P_3,$ and so on. | ✅ |
| GOCA-4-094 | 3.  The midpoints of the lines from $P_1$ to $P_2, $ $P_2$ to $P_3, \dots, P_{n-2}$ to $P_{n-1}$ are computed; call these $M_1, M_2, \dots, $ $M_{n-2}$. | ✅ |
| GOCA-4-095 | 4.  The points $P_0, P_1, M_1, P_2, M_2, P_3, \dots, M_{n-2}, P_{n-1}, P_n$ are then considered three at a time, starting with $P_0, P_1, M_1$. A quadrant of a circle is scaled, and can be distorted to become a part of an ellipse, in order that the curve be tangential to the line $P_0-P_1$ at the point $P_0$, and tangential to the line $P_1-M_1$ at the point $M_1$. The center point of the ellipse is the point obtained by completing the parallelogram defined by the sides $P_0-P_1$ and $P_1-M_1$. | ✅ |
| GOCA-4-096 | 5.  The next three points are considered, that is $M_1, P_2, M_2$ and a quadrant of a circle is transformed into part of an ellipse that is tangential to the line $M_1-P_2$ at $M_1$, and tangential to the line $P_2-M_2$ at $M_2$. | ✅ |
| GOCA-4-097 | 6.  This process continues, with part of an ellipse being fitted to three points in turn, until the last three points $M_{n-2}, P_{n-1}, P_n,$ have been incorporated; see Figure 9. | ✅ |
| GOCA-4-098 | Note:** If all the points $P_0$ through $P_n$ are within the GPS, the actual fillet does not go outside the GPS. | ✅ |
| GOCA-4-099 | A polycurve formed by overlapping sets of four points. | ✅ |
| GOCA-4-100 | A Cubic Bezier curve primitive is drawn by executing a Cubic Bezier Curve order. The parameters of the order are a set of points $P_0, P_1, \dots, P_n$. These points are considered in sets of four, the first being $P_0, P_1, P_2,$ and $P_3$. The second set is $P_3, P_4, P_5,$ and $P_6$; that is, the set overlaps at $P_3$ with the first set. This process continues, each set overlapping the previous set by one point, up to the final set, which is $P_{n-3}, P_{n-2}, P_{n-1},$ and $P_n$. | ✅ |
| GOCA-4-101 | An exception condition, EC-0003, occurs if the length of the order does not provide a number of points equal to $3m + 1$, where $m$ is the number of sets. The number of points provided includes the current position when the order is Cubic Bezier Curve at Current Position. | ✅ |
| GOCA-4-102 | A curve is drawn independently for each set of four points. It is computed as follows: | ✅ |
| GOCA-4-103 | 1.  Let the first set of points be labeled $P_0, P_1, P_2,$ and $P_3$. The curve is drawn from $P_0$ to $P_3$ and is tangential to $P_0P_1$ and $P_2P_3$; see Figure 10. | ✅ |
| GOCA-4-104 | 2.  The curve drawn is defined by the parametric equations: | ✅ |
| GOCA-4-105 | where $t$ takes values from 0 to 1. | ✅ |
| GOCA-4-106 | $R_x = 3 \cdot P_{1x} - 3 \cdot P_{0x}$ | ✅ |
| GOCA-4-107 | Note:** $P_{1x}$ and $P_{1y}$ are the $x$ and $y$ coordinates of point $P_1$, etc. | ✅ |
| GOCA-4-108 | 3.  $P_y, Q_y,$ and $R_y$ are given by the same formulas, but using the $y$ coordinates of the points $P_0, P_1, P_2,$ and $P_3$ instead of the $x$ coordinates. | ✅ |
| GOCA-4-109 | The choice of control points for the curves determine whether there is a smooth transition from one curve to the next. A smooth transition requires that two curves have the same slope at their connecting point. To ensure that the curves drawn have the same slope at the connecting point, the second control point of the previous curve and the first control point of the new curve must be collinear with the common point of the two curves. In Figure 10, for example, $P_2$ (second control point of the first curve), $P_3$ (common point), and $P_4$ (first control point of the new curve) are on the same line resulting in a smooth transition, whereas $P_5$ (second control point of the second curve), $P_6$ (common point), and $P_7$ (first control point of the third curve) are not collinear, resulting in the two curves joining at an angle. | ✅ |
| GOCA-4-110 | A rectangular box with optional rounded corners. | ✅ |
| GOCA-4-111 | Box at Given Position (GBOX) | ✅ |
| GOCA-4-112 | Box at Current Position (GCBOX) | ✅ |
| GOCA-4-113 | The Box order draws a rectangular box with corners that are either square, quadrants of a circle, or quadrants of an ellipse. | ✅ |
| GOCA-4-114 | The corner positions of a rectangle. There are two corners specified; in the case of the GCBOX order, one of the corners is the current position. | ✅ |
| GOCA-4-115 | The lengths of the horizontal and vertical axes of an ellipse. | ✅ |
| GOCA-4-116 | Both omitted, or either is specified as zero, square corners are drawn. | ✅ |
| GOCA-4-117 | Equal and nonzero, each corner is a quadrant of a circle. | ✅ |
| GOCA-4-118 | Not equal and nonzero, each corner is a quadrant of an ellipse. | ✅ |
| GOCA-4-119 | The current values of the line attributes, except for line join, are taken into account when the box is drawn. The line end attribute is used only for the internal ends of dotted or dashed lines. | ✅ |
| GOCA-4-120 | Current position is set to the first corner specified for the GBOX order, or is unchanged for the GCBOX order. | ✅ |
| GOCA-4-121 | Table 8 shows the attributes controlling the drawing of line primitives, that is, straight lines, curved lines, and boxes. | ✅ |
| GOCA-4-122 | LINE TYPE** | Solid (X'07') | 1 or 4n | Specification of type of line | ✅ |
| GOCA-4-123 | LINE WIDTH** | Normal (X'0100') | 2 | Specification of line width as fractional multiplier of normal line width | ✅ |
| GOCA-4-124 | NORMAL LINE WIDTH** | Device dependent | 2 | Specification of normal line width in 1440ths of an inch | ✅ |
| GOCA-4-125 | LINE END** | Round (X'03') | 1 | Specification of line end | ✅ |
| GOCA-4-126 | LINE JOIN** | Round (X'02') | 1 | Specification of line join | ✅ |
| GOCA-4-127 | COLOR** | Device dependent | 2 | Color value set into GPS for foreground | ✅ |
| GOCA-4-128 | PROCESS COLOR** | Device dependent | 12–14 | Process color value set into GPS for foreground | ✅ |
| GOCA-4-129 | MIX** | Overpaint (X'02') | 1 | Specification of Mix mode in GPS for foreground | ✅ |
| GOCA-4-130 | The dots and dashes are drawn. | ✅ |
| GOCA-4-131 | The spaces between the dots and dashes are not drawn and have no effect on the GPS. | ✅ |
| GOCA-4-132 | The sequence of line type dots and dashes is not reset, except by a Move Type order, which is an order that causes current position to be updated to a new value specified in the order before anything is drawn. Move Type orders are defined in Table 9. | ✅ |
| GOCA-4-133 | Any straight or curved line order that explicitly specifies the starting point of the line that is to be drawn | • Box at Given Position (GBOX)<br>• Cubic Bezier Curve at Given Position (GCBEZ)<br>• Fillet at Given Position (GFLT)<br>• Full Arc at Given Position (GFARC)<br>• Line at Given Position (GLINE)<br>• Partial Arc at Given Position (GPARC)<br>• Relative Line at Given Position (GRLINE) | ✅ |
| GOCA-4-134 | Orders that explicitly or implicitly set current position | • Set Current Position (GSCP) | ✅ |
| GOCA-4-135 | Other orders that specify an initial position | • Begin Image at Given Position (GBIMG)<br>• Character String at Given Position (GCHST)<br>• Marker at Given Position (GMRK) | ✅ |
| GOCA-4-136 | The standard line types are defined as follows: | ✅ |
| GOCA-4-137 | X'00' | Drawing Default | ✅ |
| GOCA-4-138 | X'01' | Dotted Line | ✅ |
| GOCA-4-139 | X'02' | Short-dashed Line | ✅ |
| GOCA-4-140 | X'03' | Dash-dot Line | ✅ |
| GOCA-4-141 | X'04' | Double-dotted Line | ✅ |
| GOCA-4-142 | X'05' | Long-dashed Line | ✅ |
| GOCA-4-143 | X'06' | Dash-double-dot Line | ✅ |
| GOCA-4-144 | X'07' | Solid Line | ✅ |
| GOCA-4-145 | X'08' | Invisible Line | ✅ |
| GOCA-4-146 | The guidelines for generating the standard line types are as follows: | ✅ |
| GOCA-4-147 | X'01' | 0, 2 | ✅ |
| GOCA-4-148 | X'02' | 3, 3 | ✅ |
| GOCA-4-149 | X'03' | 6, 4, 0, 4 | ✅ |
| GOCA-4-150 | X'04' | 0, 3, 0, 7 | ✅ |
| GOCA-4-151 | X'05' | 8, 3 | ✅ |
| GOCA-4-152 | X'06' | 6, 3, 0, 3, 0, 3 | ✅ |
| GOCA-4-153 | A Dash-dot Line with different line endings is illustrated by Figure 12. | ✅ |
| GOCA-4-154 | $W, 6 \cdot W, 4 \cdot W, 4 \cdot W, 0 \cdot W$ | ✅ |
| GOCA-4-155 | There is only one line type attribute. It has a current value that is either a standard value or a custom value, depending on which drawing order (Set Line Type or Set Custom Line Type) was specified last. | ✅ |
| GOCA-4-156 | Custom dashed line (6, 0.5) | ✅ |
| GOCA-4-157 | In any case, if the calculated line width exceeds the maximum supported by the device, that maximum is used. | ✅ |
| GOCA-4-158 | X'0000' | Drawing Default. The value of the attribute when the graphics processor was invoked. This value was set either by the Set Current Defaults instruction in the Graphics Data Descriptor or by the controlling environment. | ✅ |
| GOCA-4-159 | X'0100' | Normal line width (multiplier of 1). | ✅ |
| GOCA-4-160 | X'nnnn' | Multiplier. The high-order byte is an integral multiplier of the normal width, and the low-order byte is a fractional multiplier. | ✅ |
| GOCA-4-161 | Architecture Note:** The line width should be scaled when the controlling environment specifies a scaling mapping of the GPS window into the usable area (object area). | ✅ |
| GOCA-4-162 | The term $MH.MFR$ is a decimal number consisting of the integral line width multiplier followed by the fractional line width multiplier. For example, if $MH = 6$ and $MFR = 1/4, MH.MFR = 6.25$. | ✅ |
| GOCA-4-163 | At the intermediate points within output primitives that contain multiple lines. | ✅ |
| GOCA-4-164 | At the junction that occurs between the end point of a line primitive and the start point of a following line primitive specifying at current position, except when any of the following orders occur between the two primitives: | ✅ |
| GOCA-4-165 | A Move Type order. See Table 9 for the definition of a Move Type order. | ✅ |
| GOCA-4-166 | A Set of any Line attribute. | ✅ |
| GOCA-4-167 | A Begin Area or End Area order. | ✅ |
| GOCA-4-168 | At the junction between the start point of a closed figure within an area and the end point of the closed figure. | ✅ |
| GOCA-4-169 | The line end attribute is used to define the outline of all other line end points. | ✅ |
| GOCA-4-170 | 1. Except as detailed in 2 below, the line end attribute is not applicable to Full Arc or Box as they are closed figures. | ✅ |
| GOCA-4-171 | 2. The line end attribute applies only to the extreme ends of a set of contiguous lines, and if the line type is dotted or dashed, the internal ends of the dots and dashes. | ✅ |
| GOCA-4-172 | 3. Line ends are drawn only if the end point is visible, as indicated by the current line type attribute. | ✅ |
| GOCA-4-173 | 4. Line joins are drawn only if the junction point and the adjacent points of both lines are visible. | ✅ |
| GOCA-4-174 | 5. Line joins are not applied to the corners of the Box figure. | ✅ |
| GOCA-4-175 | The attribute can have the following values: | ✅ |
| GOCA-4-176 | X'00' | Drawing Default. The value of the attribute when the graphics processor was invoked. This value was set either by the Set Current Defaults instruction in the Graphics Data Descriptor or by the controlling environment. | ✅ |
| GOCA-4-177 | X'01' | **Flat.** The boundary of the end is formed by truncating a line at the end point, perpendicular to the line direction. | ✅ |
| GOCA-4-178 | X'02' | **Square.** The boundary of the end is formed by extending the line, in the line direction at the end point, by half the line width. This extension is then truncated with a line perpendicular to the line direction. **Note:** The effect is to place a rectangle on the end, even if the line is curved. | ✅ |
| GOCA-4-179 | X'03' | **Round.** The boundary of the end is formed by a semicircle, centered at the end point, with a radius of half the line width. | ✅ |
| GOCA-4-180 | Flat, square, and round line-end shapes are illustrated in Figure 14. | ✅ |
| GOCA-4-181 | The attribute can have the following values: | ✅ |
| GOCA-4-182 | X'00' | Drawing Default. The value of the attribute when the graphics processor was invoked. This value was set either by the Set Current Defaults instruction in the Graphics Data Descriptor or by the controlling environment. | ✅ |
| GOCA-4-183 | X'01' | **Bevel.** The outline of the join is formed by closing the notch between the lines with a straight line. The effect, if the lines are at an angle to each other, is of a beveled corner on the outside of the join. | ✅ |
| GOCA-4-184 | X'02' | **Round.** The outline of the join is formed by closing the notch between the lines with a circular arc of radius half the line width. The effect, if the lines are at an angle to each other, is of a rounded corner on the outside of the join. | ✅ |
| GOCA-4-185 | X'03' | **Miter.** The outline of the join is formed by projecting the inner and outer edges of the two lines until they meet at an angle. The effect of the mitered join is to close the notch on the outside of the join with the quadrilateral formed by the ends of the lines and the extended outer edges of the two lines. The mitered join has no effect on the inside of the join. At any given join, the miter length is the distance from the point at which the inner edges of the lines intersect to the point at which the outer edges of the lines intersect; that is, the diagonal of the miter. This distance increases as the angle between the lines decreases. | ✅ |
| GOCA-4-186 | When the joining lines connect at a sharp angle, a miter join results in a spike that extends well beyond the connection point. To avoid exceptionally long spikes when lines join at sharp angles, a bevel join is used instead of a miter. The angle at which conversion from a miter join to a bevel join takes place is the angle at which the ratio of the miter length to the line width exceeds the value 10; this is approximately 11 degrees. | ✅ |
| GOCA-4-187 | Bevel, round, and miter line-join shapes are illustrated in Figure 15. | ✅ |
| GOCA-4-188 | Note:** When non-solid lines are joined and flat line ends are used, the resulting corner can look strange. For example, with a dashed line that joins another dashed line at an angle, the corner will look different depending on how much of a dash bends around the corner; notches can appear when one of the line segments is shorter than its width, see Figure 16 (Dash-dot line, Flat endings). It is not necessary for an implementation to attempt to compensate for these inherent problems. | ✅ |
| GOCA-4-189 | The boundary of an area is defined as one or more closed figures that are either constructed or complete; see Figure 17. An example of a complete figure is one defined by the Full Arc order. Each constructed figure consists of a set of straight and curved lines connected together. These lines can be drawn if required. | ✅ |
| GOCA-4-190 | Open constructed, Closed constructed, Closed complete | ✅ |
| GOCA-4-191 | Each figure in an area must be properly closed, that is, its start and end points must be identical. If the points are not identical, the figure is closed arbitrarily with a straight line connecting the start and end points. The current position is set to the start point of the figure. | ✅ |
| GOCA-4-192 | Implementation Note:** If the Begin Area order specifies that the boundary is to be drawn, and if the area is not properly closed, the generated line to close the figure may or may not be drawn; this is presentation device dependent. The architecture recommendation is that this line not be drawn. If the Begin Area order specifies that the boundary is not to be drawn, the generated line to close the figure is not drawn. | ✅ |
| GOCA-4-193 | The figures formed in this way jointly define the area boundary. The interior of the area is shaded using the values of the pattern attributes that were current when the Begin Area order was executed. | ✅ |
| GOCA-4-194 | Alternate mode** | ✅ |
| GOCA-4-195 | Nonzero Winding mode** | ✅ |
| GOCA-4-196 | Nonzero Winding mode is similar to alternate mode, in that whether any point is within the area interior is determined by counting the number of times a conceptual line crosses the area boundary. With this mode, however, the direction of the boundary lines is taken into account. Using the same conceptual line, the number of crossings is counted, but boundary lines going in one direction—for example, right-to-left—count as +1, while lines going the other direction—left-to-right—count as -1. The original point is then considered to be inside the interior if the final count is nonzero. Thus a region with a nonzero number of line crossings from infinity is shaded, and a region with a zero number of line crossings from infinity is not shaded. | ✅ |
| GOCA-4-197 | Implementation Note:** It is strongly recommended that implementations that support Nonzero Winding mode also support directionality of full and partial arcs, and directionality of boxes. | ✅ |
| GOCA-4-198 | Alternate mode, Nonzero Winding mode | ✅ |
| GOCA-4-199 | The area is filled with the pattern specified by the pattern set and pattern symbol attributes that were current when the Begin Area order was executed. If no such set is available, exception condition EC-6803 is raised, the standard action for which is to use the standard default pattern set. If the code point is undefined in the specified pattern set, exception condition EC-6804 is raised, the standard action for which is to use the standard default pattern symbol. In AFP environments, this is X'10'—Solid fill. | ✅ |
| GOCA-4-200 | 1. When an End Area order is executed, the closed figures within the area are filled. The values of the pattern set, pattern symbol, pattern mix, and pattern background mix attributes that were current when the Begin Area order was executed are used in generating the fill pattern. When using the default pattern set or a bilevel custom pattern (but not when using a full-color custom pattern or gradient), the value of the pattern color attribute that was current when the Begin Area order was executed is also used in generating the fill pattern. When using a custom pattern, the value of the pattern reference point attribute that was current when the Begin Area order was executed is also used in generating the fill pattern. After the End Area order is executed, the current pattern color, pattern mix, and pattern background mix attributes are updated to reflect any change in the color, mix, and background mix attributes that may have been specified inside the area definition. | ✅ |
| GOCA-4-201 | 2. If indicated by the Begin Area order, the area boundary is drawn in the GPS in the sequence that the drawing orders that define the boundary are executed. The boundary lines are drawn using the values of the Line attributes that are current at the time the orders defining the boundary are executed. | ✅ |
| GOCA-4-202 | The value of the current position is not changed by the Begin Area order itself, but is changed by those orders that are used to define the area boundary. | ✅ |
| GOCA-4-203 | Comment | ✅ |
| GOCA-4-204 | Cubic Bezier Curve | ✅ |
| GOCA-4-205 | Fillet | ✅ |
| GOCA-4-206 | Full Arc | ✅ |
| GOCA-4-207 | No-Operation | ✅ |
| GOCA-4-208 | Partial Arc | ✅ |
| GOCA-4-209 | Relative Line | ✅ |
| GOCA-4-210 | Set Arc Parameters | ✅ |
| GOCA-4-211 | Set Background Mix | ✅ |
| GOCA-4-212 | Set Color | ✅ |
| GOCA-4-213 | Set Current Position | ✅ |
| GOCA-4-214 | Set Custom Line Type | ✅ |
| GOCA-4-215 | Set Extended Color | ✅ |
| GOCA-4-216 | Set Fractional Line Width | ✅ |
| GOCA-4-217 | Set Line End | ✅ |
| GOCA-4-218 | Set Line Join | ✅ |
| GOCA-4-219 | Set Line Type | ✅ |
| GOCA-4-220 | Set Line Width | ✅ |
| GOCA-4-221 | Set Mix | ✅ |
| GOCA-4-222 | Set Process Color | ✅ |
| GOCA-4-223 | Note:** The Marker and Character String orders are not permitted as part of an area definition. | ✅ |
| GOCA-4-224 | Patterns in the default pattern set | ✅ |
| GOCA-4-225 | Custom patterns | ✅ |
| GOCA-4-226 | Gradients | ✅ |
| GOCA-4-227 | Note that for all three types of patterns, a Pattern Symbol attribute value of X'00' selects the drawing default symbol. If no drawing default symbol is specified in the graphics data descriptor, the presentation default in AFP GOCA is solid fill, which is pattern symbol X'10' in the default pattern set. | ✅ |
| GOCA-4-228 | Note also that in the default pattern set, a pattern symbol attribute value of X'40' (blank) is treated the same as an attribute value of X'0F' (no fill). | ✅ |
| GOCA-4-229 | Implementation Note:** Implementation differences with respect to dot size, dot spacing, line thickness, and line spacing exist. In particular, some printers use the same line thickness and line spacing for pattern #10 as for pattern #9. However, all future presentation devices should try to replicate the published patterns as closely as possible. | ✅ |
| GOCA-4-230 | Architecture Note:** The precise appearance of the patterns in Figure 19 in this edition of this Reference do not exactly match earlier editions of this Reference, although differences are very slight. Any differences are unintentional and do not constitute a changed definition of the patterns. | ✅ |
| GOCA-4-231 | As with any pattern, a custom pattern can be set as the default pattern using the Set Current Defaults instruction. | ✅ |
| GOCA-4-232 | Similarly, when custom pattern mode is entered, the current attributes and current position are set by the drawing processor to the drawing defaults—this is the same processing as is done at the beginning of a new segment. In this way, moving a custom pattern definition from one place in a segment to another will not affect the custom pattern definition in any way, since the custom pattern inherits nothing from the surrounding segment. The initialization when entering custom pattern mode is not affected by the presence of a segment prolog in the segment containing the custom pattern definition. | ✅ |
| GOCA-4-233 | The type of a custom pattern is specified in the Begin Custom Pattern drawing order that starts the definition of the custom pattern. Every custom pattern, then, is either a bilevel custom pattern or a full-color custom pattern, but never both, and a given custom pattern cannot change its type. | ✅ |
| GOCA-4-234 | The Set Color, Set Extended Color, and Set Process Color drawing orders are allowed in bilevel custom pattern definitions, but are ignored. | ✅ |
| GOCA-4-235 | Linear gradients | ✅ |
| GOCA-4-236 | Radial gradients | ✅ |
| GOCA-4-237 | An example of a picture with both a linear gradient (from the top to the bottom in the background) and a radial gradient (on the circle) is shown in Figure 20. | ✅ |
| GOCA-4-238 | The two types of gradients are defined using the Linear Gradient and Radial Gradient drawing orders. | ✅ |
| GOCA-4-239 | Linear gradients go from a start color located at a start point to an end color located at an end point. The gradient, then, proceeds along a line from the start point to the end point; this line will be called the gradient line. The colors radiate out in a perpendicular fashion from the gradient line such that the gradient becomes 2-dimensional. The color gradually changes, starting at the line that is perpendicular to the gradient line and that goes through the start point, and ending at the line that is perpendicular to the gradient line and that goes through the end point. A linear gradient, then, continues out to the edge of the GPS. As an example, Figure 21 shows the entire GPS and what a gradient defined to go from blue at (0,0) to green at (20000, 10000) looks like. | ✅ |
| GOCA-4-240 | Start at (0,0), End at (20000,10000), Entire GPS range $(\pm 32767, \pm 32768)$. | ✅ |
| GOCA-4-241 | The quadrilateral on the lower left. This area is considered to be on the “start side” of the gradient since it borders the start of the gradient line. | ✅ |
| GOCA-4-242 | The triangle on the upper right. This area is considered to be on the “end side” of the gradient. | ✅ |
| GOCA-4-243 | Also included in the definition of a gradient are indications of how to fill these areas that are outside the gradient—these are the “Outside” values. There is one Outside value for the start side of the gradient and one Outside value for the end side, and the values do not have to be the same. The possible values: | ✅ |
| GOCA-4-244 | Pad** | Extend the color on the edge of the gradient as far as necessary to fill the outside area. For example, for a blue to green gradient, the outside area on the start side would be all blue and the outside area on the end side would be all green. | ✅ |
| GOCA-4-245 | Outside value | Meaning | ✅ |
| GOCA-4-246 | Repeat** | The gradient is repeated as many times as necessary to fill the outside areas, by repeating the gradient line in both directions. For example, for a blue to green gradient, the blue to green gradient would be repeated over and over right next to the previous gradient. | ✅ |
| GOCA-4-247 | Reflect** | Right next to the gradient, a mirror-image of the gradient is produced. If this does not fill the outside area, the gradient itself is then repeated, followed by another mirror image, followed by the same gradient, and so on. For example, for a blue to green gradient, a blue to green gradient followed immediately by a green to blue gradient would be drawn; continuing this, the end result would be a gradient going from blue to green to blue to green and so on to the edge of the GPS. | ✅ |
| GOCA-4-248 | None** | Fill the outside areas with no color. This is equivalent to treating these areas as if they had been filled with the X'0F' (no fill) pattern of the default pattern set. | ✅ |
| GOCA-4-249 | Figure 22 shows the effect of each Outside value on the example gradient from Figure 21; in the figure, both the start and end Outside values are set to the same value. | ✅ |
| GOCA-4-250 | Pad, None, Repeat, Reflect | ✅ |
| GOCA-4-251 | In addition to the gradient changing from the start color to the end color, additional colors can be specified to occur between the start and end. For example, rather than a gradient simply changing from blue to green, it can be defined that it also be yellow at some point in between. This point is called a color stop. A color stop is defined by the offset along the gradient line where it occurs, and the color to appear at that offset. Multiple color stops can be defined. As an example, Figure 23 shows the example gradient from Figure 21 but with the addition of a yellow color stop at an offset of 60% of the way along the gradient line. | ✅ |
| GOCA-4-252 | Two color stops at the same offset define a discontinuity of the gradient. For example, if two color stops are defined at halfway along the gradient line, the first yellow and the second purple, the gradient will smoothly change toward yellow in the first half of the gradient getting to yellow just before the halfway point, abruptly change to purple at the halfway point, then smoothly change away from purple in the second half of the gradient. | ✅ |
| GOCA-4-253 | If any part of the gradient is specified to transition from some color C to that same color C, that part of the gradient will be drawn as solid fill with color C. | ✅ |
| GOCA-4-254 | Radial gradients are conceptually very similar to linear gradients. They transition from a start color to an end color with any number of color stops in between. They have two Outside values to define how to fill areas outside the gradient. However, rather than the gradient changing color along a gradient line, radial gradients change color radiating from a start full arc to an end full arc. Conceptually, the color changes through an infinite number of intermediate full arcs that occur in the process of transitioning from the start full arc to the end full arc. For example, if the start full arc is smaller than the end full arc and its center is directly to the left of the end full arc’s center, the first intermediate full arc between the start and the end will be a tiny bit larger than, and a tiny bit to the right of, the start full arc. | ✅ |
| GOCA-4-255 | Figure 24 shows a radial gradient transitioning from yellow at the inside, start full arc to red at the outside, end full arc. Figure 25 shows a similar radial gradient, from yellow to red, but with overlapping start and end full arcs. Figure 26 shows the same radial gradient with full arcs that are completely outside each other. Note that, as can been seen in figures 25 and 26, conceptually the intermediate full arcs are drawn beginning at the start full arc and ending at the end full arc, which results in a picture where the end full arc looks closer to the viewer. | ✅ |
| GOCA-4-256 | Color stops in radial gradients work the same as those in linear gradients, but note that a color stop at an offset of, for example 60%, defines the color of the intermediate full arc that is 60% of the way between the start full arc and end full arc. | ✅ |
| GOCA-4-257 | There is an important difference between linear and radial gradients regarding the filling of areas outside the gradient. In cases where one of the full arcs is not contained inside the other full arc, as in Figure 25, a given intermediate full arc does not surround the previous intermediate full arc; instead it overlaps the previous intermediate full arc. The intermediate full arcs move in some specific direction rather than expanding in all directions; in Figure 25, the intermediate full arcs are always moving to the right and slightly upward. Thus it can be seen that if Outside=pad, the continuation of the gradient would only cause there to be more intermediate full arcs that would fill certain areas to the right and left, but would not fill the area above and below—this is shown in Figure 27. Thus, a radial gradient, even when taking into account the Outside values, does not necessarily completely fill the GPS; that is, in some cases, there are parts of the GPS that are outside the gradient but that cannot be filled using the Outside values. Note that when one full arc is completely inside the other, as was the case for Figure 24, the Outside values can fill the entire GPS; see Figure 28. | ✅ |
| GOCA-4-258 | As with any pattern, a gradient can be set as the default pattern using the Set Current Defaults instruction. | ✅ |
| GOCA-4-259 | Table 10 shows the attributes controlling the drawing of Area primitives. | ✅ |
| GOCA-4-260 | PATTERN SET** | Default pattern set (X'00') | 1 | Specification of pattern set identifier | ✅ |
| GOCA-4-261 | SYMBOL** | Solid fill (X'10') | 1 | Specification of pattern symbol code point | ✅ |
| GOCA-4-262 | COLOR** | Device dependent | 2 | Color value set into GPS for foreground | ✅ |
| GOCA-4-263 | PROCESS COLOR** | Device dependent | 12–14 | Process color value set into GPS for foreground | ✅ |
| GOCA-4-264 | MIX** | Overpaint (X'02') | 1 | Specification of mix mode in GPS for foreground | ✅ |
| GOCA-4-265 | BACKGROUND MIX** | Leave Alone (X'05') | 1 | Specification of mix mode in GPS for background | ✅ |
| GOCA-4-266 | REFERENCE POINT** | (0,0) | 4 | Specification of pattern reference point for custom patterns | ✅ |
| GOCA-4-267 | The Character String at Given Position order draws a character string starting at a given point $(X_0, Y_0)$, and sets the current position to $(X_0, Y_0)$. | ✅ |
| GOCA-4-268 | The Character String at Current Position order draws a character string starting at the current position, and does not change the current position. | ✅ |
| GOCA-4-269 | See “Character String (GCHST, GCCHST) Orders” for details. | ✅ |
| GOCA-4-270 | Code points in the order | ✅ |
| GOCA-4-271 | Character Attributes (see “Character Attributes”) | ✅ |
| GOCA-4-272 | Precision 1:** Device-specific (string) precision | ✅ |
| GOCA-4-273 | Precision 2:** Device-specific (character) precision | ✅ |
| GOCA-4-274 | Precision 3:** Stroke precision (not supported in AFP GOCA) | ✅ |
| GOCA-4-275 | Figure 29 shows two different methods used in AFP environments for positioning GOCA character strings. | ✅ |
| GOCA-4-276 | Horizontal Baseline, Vertical Baseline. | ✅ |
| GOCA-4-277 | Character precision has been implemented differently on different devices; it is not consistent among implementations. The intent of this precision is that characters are positioned and drawn as follows. Note that the character reference point is not always placed at the current position. Scale and rotation are not necessarily applied when drawing the symbol. | ✅ |
| GOCA-4-278 | 1.  The position of the first character is determined by the character direction attribute. Each device uses one of the two methods of locating the points R,E,T,B shown in Figure 29; refer to your device documentation for specific implementation information. | ✅ |
| GOCA-4-279 | When the character direction is left to right, point R for the first character is positioned at the current or given position from the Character String order. | ✅ |
| GOCA-4-280 | When the character direction is right to left, point E for the first character is positioned at the current or given position from the Character String order. | ✅ |
| GOCA-4-281 | When the character direction is top to bottom, point T for the first character is positioned at the current or given position from the Character String order. | ✅ |
| GOCA-4-282 | When the character direction is bottom to top, point B for the first character is positioned at the current or given position from the Character String order. | ✅ |
| GOCA-4-283 | 2.  The character is then drawn taking the following character attributes into account: | ✅ |
| GOCA-4-284 | For devices that scale GOCA characters, the symbol is scaled using the character cell-size attribute. This scaling is independent in the X-axis and Y-axis. | ✅ |
| GOCA-4-285 | The character cell is rotated by the angle given in the character angle attribute. | ✅ |
| GOCA-4-286 | For some devices, the character is rotated within the cell based on the selected font rotation. | ✅ |
| GOCA-4-287 | 3.  The next character in the string is positioned. | ✅ |
| GOCA-4-288 | When the character direction is left to right, a vector is generated from the left edge of the character box to the right edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-4-289 | When the character direction is top to bottom, a vector is generated from the top edge of the character box to the bottom edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-4-290 | When the character direction is right to left, a vector is generated from the right edge of the character box to the left edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-4-291 | When the character direction is bottom to top, a vector is generated from the bottom edge of the character box to the top edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-4-292 | 4.  Subsequent characters in the string are positioned and drawn in the same manner. Figure 30 shows the effect of the character direction and character angle attributes when the device uses the font positioning method. Figure 31 shows the effect of the character direction and character angle attributes when the device uses the cell positioning method. | ✅ |
| GOCA-4-293 | Character baseline, Current graphics position, Character reference point of first character. | ✅ |
| GOCA-4-294 | Character baseline, Current graphics position, Character reference point of first character. | ✅ |
| GOCA-4-295 | The character angle attribute can be ignored. | ✅ |
| GOCA-4-296 | The positioning of the first character can be approximate. | ✅ |
| GOCA-4-297 | Drawing of the symbol need take no account of scale or rotation. | ✅ |
| GOCA-4-298 | The positioning of further characters in the string need not be scaled according to the character cell-size attribute. | ✅ |
| GOCA-4-299 | Table 11 shows the attributes controlling the appearance of character strings. | ✅ |
| GOCA-4-300 | ANGLE** | X,Y No rotation | 4 | Character rotation $x$ and $y$ values | ✅ |
| GOCA-4-301 | CELLSIZE** | Device dependent | 4 | Specification of character cell width and height | ✅ |
| GOCA-4-302 | CELLSIZEF** | Device dependent | 4 | Specification of fractional extension of character cell width and height | ✅ |
| GOCA-4-303 | DIRECTION** | Left to right (X'01') | 1 | Specification of character direction | ✅ |
| GOCA-4-304 | PRECISION** | Character precision (X'02') | 1 | Specification of character precision | ✅ |
| GOCA-4-305 | CHARACTER SET** | Device dependent | 1 | Specification of character set local identifier | ✅ |
| GOCA-4-306 | SHEAR** | X,Y No shear | 4 | Shear $x$ and $y$ values; not supported in AFP GOCA | ✅ |
| GOCA-4-307 | SYMBOL** | Device dependent | 1 | Specification of default character code point | ✅ |
| GOCA-4-308 | COLOR** | Device dependent | 2 | Color value set into GPS for foreground | ✅ |
| GOCA-4-309 | PROCESS COLOR** | Device dependent | 12–14 | Process color value set into GPS for foreground | ✅ |
| GOCA-4-310 | MIX** | Overpaint (X'02') | 1 | Specification of mix mode in GPS for foreground | ✅ |
| GOCA-4-311 | BACKGROUND MIX** | Leave Alone (X'05') | 1 | Specification of mix mode in GPS for background | ✅ |
| GOCA-4-312 | The Marker at Given Position order draws a marker at one or more points. | ✅ |
| GOCA-4-313 | The Marker at Current Position order draws a marker at the current position and at one or more further points. | ✅ |
| GOCA-4-314 | The particular symbol that is drawn is the one identified by the current marker symbol from the current marker set. This symbol is drawn at all the positions specified in the one order. The only marker set supported in AFP GOCA is the default marker set, shown in Figure 32. | ✅ |
| GOCA-4-315 | X'00' Drawing default (blank), X'01' to X'0A', X'40' blank. | ✅ |
| GOCA-4-316 | Markers are scaled along with the rest of the GPS if scaling is necessary in the mapping from the GPS window into the usable area (object area). | ✅ |
| GOCA-4-317 | Draw markers taking into account all marker attributes. | ✅ |
| GOCA-4-318 | Center the marker at the specified position. | ✅ |
| GOCA-4-319 | Treat the marker precision attribute as obsolete. | ✅ |
| GOCA-4-320 | Follow the recommendation for the standard default value for marker cell-size specified in "Set Marker Cell (GSMC) Order". | ✅ |
| GOCA-4-321 | Table 12 shows the attributes controlling the appearance of markers. | ✅ |
| GOCA-4-322 | CELLSIZE** | Device dependent | 4 | Specification of marker cell width and height | ✅ |
| GOCA-4-323 | MARKER SET** | Device dependent | 1 | Specification of marker set local identifier | ✅ |
| GOCA-4-324 | SYMBOL** | Cross (X'01') | 1 | Specification of marker symbol code point | ✅ |
| GOCA-4-325 | COLOR** | Device dependent | 2 | Color value set into GPS for foreground | ✅ |
| GOCA-4-326 | PROCESS COLOR** | Device dependent | 12–14 | Process color value set into GPS for foreground | ✅ |
| GOCA-4-327 | MIX** | Overpaint (X'02') | 1 | Specification of mix mode in GPS for foreground | ✅ |
| GOCA-4-328 | BACKGROUND MIX** | Leave Alone (X'05') | 1 | Specification of mix mode in GPS for background | ✅ |
| GOCA-4-329 | Images are rectangular arrays of points that are included directly in the graphics picture definition. An image is represented by a sequence of orders. The first order is a Begin Image order, which is followed by one or more Image Data orders. The sequence must end with an End Image order. | ✅ |
| GOCA-4-330 | Note:** The only other orders permitted within the Begin Image/End Image order bracket are the No-Operation and Comment orders. | ✅ |
| GOCA-4-331 | Only one format of image data is defined: `FORMAT=X'00'`. With this format, each Image Data order contains the data for one row of the image. | ✅ |
| GOCA-4-332 | Image points are mapped to presentation-device pels. The size of the image is given by the WIDTH and HEIGHT parameters in the Begin Image order. There must be as many Image Data orders as the HEIGHT parameter, and each Image Data order must contain the number of bits specified by the WIDTH parameter, plus padding to a byte boundary. | ✅ |
| GOCA-4-333 | The position of the image in GPS is specified in GPS L-units. | ✅ |
| GOCA-4-334 | The current values of the image attributes are taken into account when drawing the image. An image must be completely defined in one segment. However, it may start in one segment and be completed in an appended segment. | ✅ |
| GOCA-4-335 | Table 13 shows the attributes controlling the appearance of an image. | ✅ |
| GOCA-4-336 | COLOR** | Device dependent | 2 | Color value set into GPS for foreground | ✅ |
| GOCA-4-337 | PROCESS COLOR** | Device dependent | 12–14 | Process color value set into GPS for foreground | ✅ |
| GOCA-4-338 | MIX** | Overpaint (X'02') | 1 | Specification of mix mode in GPS for foreground | ✅ |
| GOCA-4-339 | BACKGROUND MIX** | Leave Alone (X'05') | 1 | Specification of mix mode in GPS for background | ✅ |
| GOCA-4-340 | Because AFP GOCA uses 16-bit signed integers to specify GPS coordinates, a point outside the GPS is characterized by a 2-byte arithmetic overflow. Note that this does not mean that AFP GOCA processors are limited to 2-byte arithmetic. It simply means that they need to be able to detect 2-byte arithmetic overflows. For a definition of the geometric parameter format used in AFP GOCA, see “Parameter Type” and “Drawing Order Subset”. | ✅ |
| GOCA-5-001 | Segment types | ✅ |
| GOCA-5-002 | Segment processing sequence | ✅ |
| GOCA-5-003 | Segment properties | ✅ |
| GOCA-5-004 | Segment prolog | ✅ |
| GOCA-5-005 | Chained | ✅ |
| GOCA-5-006 | Unchained | ✅ |
| GOCA-5-007 | Segments are transmitted by the controlling environment to the drawing processor with Begin Segment Commands and the drawing orders that follow these commands. The Begin Segment (chained) command invokes the drawing processor to draw the segment. | ✅ |
| GOCA-5-008 | In the MO:DCA and IPDS environments, a graphics object can contain multiple chained segments. All chained segments within the object are processed independently in the sequence in which they arrive; together they generate the graphics picture. A segment cannot be split across multiple graphics objects. | ✅ |
| GOCA-5-009 | The Append option indicates that the segment is a continuation of the preceding segment. Unfinished drawing orders, areas, images, and prologs may be completed in appended segment data. See “Begin Segment Command” for further details of the functions of the Append option. | ✅ |
| GOCA-5-010 | Segment processing starts at the first segment in the segment chain. The processing of a segment always starts at its first order and proceeds in sequence, order by order, until the last order is processed, at which time the segment is terminated. | ✅ |
| GOCA-5-011 | When the invocation operates on a single segment, it is complete when the segment is terminated. | ✅ |
| GOCA-5-012 | When the invocation operates on a chain of segments, the graphics processor sequentially processes and terminates each segment in the chain. When the last specified chained segment is terminated, the invocation is complete. | ✅ |
| GOCA-5-013 | Associated with each segment is a set of properties. These properties are specified in the Begin Segment command; see “Begin Segment Command”. The function of these properties is to provide control information relevant to the processing of the segment. | ✅ |
| GOCA-5-014 | The properties and their functions are as follows: | ✅ |
| GOCA-5-015 | Length** | 2-byte length of segment data. | ✅ |
| GOCA-5-016 | Chain** | Indicates whether or not the segment is to be chained. | ✅ |
| GOCA-5-017 | Prolog** | Indicates that the segment has a prolog section at the beginning of the data. See **Segment Prolog** for details of prolog processing. | ✅ |
| GOCA-5-018 | New/Append** | Indicates whether this is a new segment or a segment to be appended to the previous segment. | ✅ |
| GOCA-5-019 | These properties are unique to the segment. They are not inherited between segments. They are defined when a segment is created. | ✅ |
| GOCA-5-020 | Segment prologs provide a defined position where initial attributes and drawing process controls are set. A prolog is optional; its presence is indicated by the PROLOG bit in the Begin Segment command. | ✅ |
| GOCA-5-021 | If present, the prolog is always at the beginning of the segment, and is ended by an End Prolog order within the same segment. Note that for a segment that is spread over multiple appended segments with multiple Begin Segment commands, the End Prolog drawing order may be specified in any of the appended segments. | ✅ |
| GOCA-5-022 | The end of a prolog in a segment must be indicated by an End Prolog order. Exception condition EC-000C is raised if the end of the segment is reached while still in the prolog. | ✅ |
| GOCA-5-023 | Note:** Exception condition EC-3E00 is raised if an End Prolog order is found when not in a prolog. | ✅ |
| GOCA-5-024 | Comment | ✅ |
| GOCA-5-025 | No-Operation | ✅ |
| GOCA-5-026 | Segment Characteristics | ✅ |
| GOCA-5-027 | Set Arc Parameters | ✅ |
| GOCA-5-028 | Set Background Mix | ✅ |
| GOCA-5-029 | Set Character Angle | ✅ |
| GOCA-5-030 | Set Character Cell | ✅ |
| GOCA-5-031 | Set Character Direction | ✅ |
| GOCA-5-032 | Set Character Precision | ✅ |
| GOCA-5-033 | Set Character Set | ✅ |
| GOCA-5-034 | Set Character Shear | ✅ |
| GOCA-5-035 | Set Color | ✅ |
| GOCA-5-036 | Set Current Position | ✅ |
| GOCA-5-037 | Set Custom Line Type | ✅ |
| GOCA-5-038 | Set Extended Color | ✅ |
| GOCA-5-039 | Set Fractional Line Width | ✅ |
| GOCA-5-040 | Set Line End | ✅ |
| GOCA-5-041 | Set Line Join | ✅ |
| GOCA-5-042 | Set Line Type | ✅ |
| GOCA-5-043 | Set Line Width | ✅ |
| GOCA-5-044 | Set Marker Cell | ✅ |
| GOCA-5-045 | Set Marker Precision (obsolete, see Appendix C, “AFP GOCA Migration Functions”) | ✅ |
| GOCA-5-046 | Set Marker Set | ✅ |
| GOCA-5-047 | Set Marker Symbol | ✅ |
| GOCA-5-048 | Set Mix | ✅ |
| GOCA-5-049 | Set Pattern Reference Point | ✅ |
| GOCA-5-050 | Set Pattern Set | ✅ |
| GOCA-5-051 | Set Pattern Symbol | ✅ |
| GOCA-5-052 | Set Process Color | ✅ |
| GOCA-5-053 | Implementation Note:** Some AFP printers also accept the Set Pick Identifier order in a prolog, and process this order as a No-Op. | ✅ |
| GOCA-5-054 | The other supported orders, when specified in the prolog, cause exception condition EC-000C to be raised. | ✅ |
| GOCA-5-055 | The semantics of the segment prolog for chained segments processed in immediate mode are as follows. | ✅ |
| GOCA-5-056 | The segment data is processed by the graphics processor following processing of a Begin Segment command. | ✅ |
| GOCA-5-057 | For an appended segment—that is, one specified with the APP flag equal to B'11'—the segment data that follows the Begin Segment command is part of the segment, not the whole segment. | ✅ |
| GOCA-5-058 | For an Immediate mode chained segment that is spread over multiple appended segments with multiple Begin Segment commands, only the PROLOG flag bit in the Begin Segment that is marked as New determines whether the segment has a prolog or not. The PROLOG bits in subsequent Begin Segment commands for appended segments are ignored. | ✅ |
| GOCA-5-059 | For a segment that is spread over multiple appended segments with multiple Begin Segment commands, it is necessary for the graphics processor to check the segment data in all the Begin Segment commands for the segment before it can determine whether the segment is valid or not, that is, whether or not the segment contains an End Prolog order to match the setting of the segment prolog property. | ✅ |
| GOCA-6-001 | Control instructions | ✅ |
| GOCA-6-002 | Drawing processor facilities, including: | ✅ |
| GOCA-6-003 | Current attributes | ✅ |
| GOCA-6-004 | Drawing process controls | ✅ |
| GOCA-6-005 | When the graphics object is carried in a MO:DCA data stream, the carrier is a Graphics Data Descriptor (GDD) structured field; for more information, see Appendix A, “Mixed Object Document Content Architecture (MO:DCA) Environment”. | ✅ |
| GOCA-6-006 | When the graphics object is carried in an IPDS data stream, the carrier is a Graphics Data Descriptor self-defining field in the Write Graphics Control (WGC) command; for more information, see Appendix B, “Intelligent Printer Data Stream (IPDS) Environment”. | ✅ |
| GOCA-6-007 | Both the GDD and WGC contain the Set Current Defaults control instruction, defined in “Set Current Defaults (SCD) Instruction”. | ✅ |
| GOCA-6-008 | Note:** In the MO:DCA environment, if the drawing defaults contain any invalid bits, the processor optionally raises exception condition EC-000A. | ✅ |
| GOCA-6-009 | When the graphics object is carried in a MO:DCA data stream, this control instruction is contained in the Graphics Data Descriptor (GDD) structured field. When the graphics object is carried in an IPDS data stream, this control instruction is contained in the Graphics Data Descriptor (GDD) self-defining field of the Write Graphics Control (WGC) command. | ✅ |
| GOCA-6-010 | 0 | CODE | X'21' | | Set Current Defaults instruction | ✅ |
| GOCA-6-011 | 1 | UBIN | LENGTH | 4–n | Length of following data | ✅ |
| GOCA-6-012 | 2 | SET | SET | | Drawing attributes:<br>X'00' Drawing attributes<br>X'01' Line attributes<br>X'02' Character attributes<br>X'03' Marker attributes<br>X'04' Pattern attributes<br>X'0B' Arc parameters<br>X'10' Process color attributes<br>X'11' Normal line width attribute<br>All others: Reserved | ✅ |
| GOCA-6-013 | 3–4 | CODE | MASK | X'0000'–X'FFFF' | Set mask | ✅ |
| GOCA-6-014 | 5 | BITS | FLAGS | | Bit 0: DEFAULT<br>B'0' Set all indicated items to their standard default values<br>B'1' Set the indicated items to the values contained in the source data<br>Bits 1–3: RES1 (Reserved; only valid value is B'000')<br>Bits 4–7: RES2 (Reserved; only valid value is B'1111') | ✅ |
| GOCA-6-015 | 6–n | DATA | | | Default values; bytes 6 onward are not present if DEFAULT = B'0' | ✅ |
| GOCA-6-016 | This instruction permits the setting of a variable number of values, under control of the MASK parameter in bytes 3–4, into the attribute set selected by the value of the SET parameter in byte 2. When a MASK bit equals 0, the default does not change and data bytes are not present for that attribute. A B'1' in any bit of MASK indicates that the corresponding item is to be set. If the DEFAULT bit is B'0', these items are set to the standard defaults; if it is B'1', these items are set to the values contained in the data in bytes 6–n of the instruction. | ✅ |
| GOCA-6-017 | If the value of an attribute specifies the drawing default in an attribute setting order, for example the X'00' value of the MODE parameter used in the Set Mix order, it causes the current default to be set to the standard default value. | ✅ |
| GOCA-6-018 | Bits 0–15 in MASK correspond to items within the selected attribute set, as shown in the following tables. The number of bytes required is set into the item corresponding to each 1 bit in Mask, in ascending numerical order of the MASK bit (0–15). Setting is terminated when all the items requested have been loaded. | ✅ |
| GOCA-6-019 | The default value of a given attribute should be specified only once. If specified more than once, the results are implementation dependent; it is recommended that future implementations use the last-specified value. | ✅ |
| GOCA-6-020 | 1. When the integral part of the line width attribute is set, the fractional part is reset to zero. See “Line Attributes” for a description of the Line Width attribute. | ✅ |
| GOCA-6-021 | 2. The format of the DATA field is the same as the corresponding data in the attribute setting drawing orders. | ✅ |
| GOCA-6-022 | 0 | Color | 2 | ✅ |
| GOCA-6-023 | 1 | Reserved; must be zero | N/A | ✅ |
| GOCA-6-024 | 2 | Foreground mix | 1 | ✅ |
| GOCA-6-025 | 3 | Background mix | 1 | ✅ |
| GOCA-6-026 | 4–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-027 | Note:** Setting any of the above attributes to a value is a shorthand way of setting all color, or mix, attributes to the same value. | ✅ |
| GOCA-6-028 | 0 | Line type | 1 | ✅ |
| GOCA-6-029 | 1 | Line width | 1 | ✅ |
| GOCA-6-030 | 2 | Line end | 1 | ✅ |
| GOCA-6-031 | 3 | Line join | 1 | ✅ |
| GOCA-6-032 | 4–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-033 | Note:** The line type attribute cannot be set to a custom value by this instruction. | ✅ |
| GOCA-6-034 | 0 | Angle X,Y | 4 | ✅ |
| GOCA-6-035 | 1 | Cell-size CW,CH | 4 | ✅ |
| GOCA-6-036 | 2 | Direction | 1 | ✅ |
| GOCA-6-037 | 3 | Precision | 1 | ✅ |
| GOCA-6-038 | 4 | Character Set | 1 | ✅ |
| GOCA-6-039 | 5 | Shear, X,Y | 4 | ✅ |
| GOCA-6-040 | 6–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-041 | Note:** The character symbol default attribute is not settable by this instruction. | ✅ |
| GOCA-6-042 | 0 | Reserved; must be zero | N/A | ✅ |
| GOCA-6-043 | 1 | Marker cell-size width, height | 4 | ✅ |
| GOCA-6-044 | 2 | Reserved; must be zero | N/A | ✅ |
| GOCA-6-045 | 3 | Marker precision (obsolete, see Appendix C, “AFP GOCA Migration Functions”) | 1 | ✅ |
| GOCA-6-046 | 4 | Marker set | 1 | ✅ |
| GOCA-6-047 | 5–6 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-048 | 7 | Marker symbol | 1 | ✅ |
| GOCA-6-049 | 8–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-050 | Mask bit | Item name | Length (bytes) | ✅ |
| GOCA-6-051 | 0–3 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-052 | 4 | Pattern Set | 1 | ✅ |
| GOCA-6-053 | 5–6 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-054 | 7 | Pattern Symbol | 1 | ✅ |
| GOCA-6-055 | 8–10 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-056 | 11 | Pattern Reference Point | 4 | ✅ |
| GOCA-6-057 | 12–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-058 | Mask bit | Item name | Length (bytes) | ✅ |
| GOCA-6-059 | 0 | P value | 2 | ✅ |
| GOCA-6-060 | 1 | Q value | 2 | ✅ |
| GOCA-6-061 | 2 | R value | 2 | ✅ |
| GOCA-6-062 | 3 | S value | 2 | ✅ |
| GOCA-6-063 | 4–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-064 | Mask bit | Item name | Length (bytes) | ✅ |
| GOCA-6-065 | 0 | Foreground mix | 1 | ✅ |
| GOCA-6-066 | 1 | Background mix | 1 | ✅ |
| GOCA-6-067 | 2 | Process Color | 12–14 | ✅ |
| GOCA-6-068 | 3–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-069 | Architecture Note:** If the color is specified using Drawing Attributes (SET = X'00'), and the process color is also specified using Process Color (SET = X'10'), the last-specified color is used. | ✅ |
| GOCA-6-070 | 0 | Normal line width | 2 | ✅ |
| GOCA-6-071 | 1–15 | Reserved; must be zeros | N/A | ✅ |
| GOCA-6-072 | Architecture Note:** If the normal line width attribute is specified, it establishes the absolute value of the normal line width in 1440ths of an inch. If the line attributes are also specified and define the line width as a multiple of the normal line width, the multiple is calculated based on the absolute value of the normal line width. Furthermore, all Set Line Width and Set Fractional Line Width orders in the object are also calculated based on the absolute value of the normal line width. | ✅ |
| GOCA-6-073 | IPC-0002** | ✅ |
| GOCA-6-074 | If the SET parameter (byte 2) is invalid or unsupported | ✅ |
| GOCA-6-075 | If the FLAGS parameter (byte 5) bits 1–3 are not B'000', or bits 4–7 are not B'1111' | ✅ |
| GOCA-6-076 | If an unallocated item is referenced in the MASK parameter (bytes 3–4) | ✅ |
| GOCA-6-077 | IPC-0003** | ✅ |
| GOCA-6-078 | If the FLAGS parameter (byte 5) bit 0 is B'0' and LENGTH is not X'04' | ✅ |
| GOCA-6-079 | If the FLAGS parameter (byte 5) bit 0 is B'1' and the length of the immediate data (byte 6 onward) does not exactly match the length implied by the MASK parameter | ✅ |
| GOCA-6-080 | IPC-0021** | ✅ |
| GOCA-6-081 | If any values in the data are invalid or unsupported | ✅ |
| GOCA-6-082 | Current attributes | ✅ |
| GOCA-6-083 | Drawing process controls | ✅ |
| GOCA-6-084 | As the orders in a segment are processed, the drawing processor maintains the current values of all primitive attribute types in the current attributes. These values are used by the graphics processor to draw output primitives in the GPS. | ✅ |
| GOCA-6-085 | Figure 33 shows how the controlling environment uses pre-defined standard defaults and the Set Current Defaults control instruction in the GDD and WGC to establish drawing defaults before the drawing processor is invoked to process a segment. At the start of processing of each new segment, the drawing default values are set into the current attributes. | ✅ |
| GOCA-6-086 | SET Instructions and Initialization -> SET Orders and SEGMENT Initiation | ✅ |
| GOCA-6-087 | Parameter type:** The format of the parameters in the drawing orders. These controls are described in “Parameter Type”. | ✅ |
| GOCA-6-088 | Arc parameters:** Values used as parameters when drawing circles or ellipses. These controls are described in “Set Arc Parameters (GSAP) Order”. | ✅ |
| GOCA-6-089 | Drawing defaults exist for each drawing process control. The defaults are maintained by the processor, and they are set to the standard defaults, or to the current defaults provided by the environment, whenever the processor is invoked. | ✅ |
| GOCA-6-090 | Coordinate type | ✅ |
| GOCA-6-091 | Geometric parameter format | ✅ |
| GOCA-6-092 | The format of this control is: | ✅ |
| GOCA-6-093 | COORD | X'00' | 1 | Coordinate type | ✅ |
| GOCA-6-094 | GEOM | X'00' | 1 | Geometric parameter format | ✅ |
| GOCA-6-095 | X'00'** 2-D coordinates | ✅ |
| GOCA-6-096 | X'00'** 16-bit signed integer, “high byte first” format | ✅ |
| GOCA-7-001 | The Begin Segment command | ✅ |
| GOCA-7-002 | Order formats | ✅ |
| GOCA-7-003 | Order alignment | ✅ |
| GOCA-7-004 | Current position in drawing orders | ✅ |
| GOCA-7-005 | Coordinate data | ✅ |
| GOCA-7-006 | Offset data | ✅ |
| GOCA-7-007 | Default values for drawing orders and attributes | ✅ |
| GOCA-7-008 | The following drawing orders: | ✅ |
| GOCA-7-009 | - Begin Area - Begin Custom Pattern - Begin Image at Given Position - Begin Image at Current Position - Box at Given Position - Box at Current Position - Character String at Given Position - Character String at Current Position - Comment - Cubic Bezier Curve at Given Position - Cubic Bezier Curve at Current Position - Delete Pattern - End Area - End Custom Pattern - End Image - End Prolog - Fillet at Given Position - Fillet at Current Position - Full Arc at Given Position - Full Arc at Current Position - Image Data - Line at Given Position - Line at Current Position - Linear Gradient - Marker at Given Position - Marker at Current Position - No-Operation - Partial Arc at Given Position - Partial Arc at Current Position - Radial Gradient - Relative Line at Given Position - Relative Line at Current Position - Segment Characteristics - Set Arc Parameters - Set Background Mix - Set Character Angle - Set Character Cell - Set Character Direction - Set Character Precision | ✅ |
| GOCA-7-010 | - Set Character Set - Set Character Shear - Set Color - Set Current Position - Set Custom Line Type - Set Extended Color - Set Fractional Line Width - Set Line End - Set Line Join - Set Line Type - Set Line Width - Set Marker Cell - Set Marker Set - Set Marker Symbol - Set Mix - Set Pattern Reference Point - Set Pattern Set - Set Pattern Symbol - Set Process Color | ✅ |
| GOCA-7-011 | 0 | CODE | X'70' | Begin | Segment command | ✅ |
| GOCA-7-012 | 1 | UBIN | LENGTH | X'0C' | Length of following parameters | ✅ |
| GOCA-7-013 | 2-5 | UBIN | NAME | Any | value Name of segment to be created; ignored | ✅ |
| GOCA-7-014 | 6 | BITS | FLAG1 | Any | value Ignored | ✅ |
| GOCA-7-015 | 7 | BITS | FLAG2 | Segment | Properties 2 | ✅ |
| GOCA-7-016 | Bit | 0 | CHAIN | | B'0',B'1' | ✅ |
| GOCA-7-017 | B'0' Chained | ✅ |
| GOCA-7-018 | B'1' Unchained | ✅ |
| GOCA-7-019 | Bits | 1-2 | CHPOS | Any | value Ignored | ✅ |
| GOCA-7-020 | Bit | 3 | PROLOG | | B'0',B'1' | ✅ |
| GOCA-7-021 | B'0' No Prolog | ✅ |
| GOCA-7-022 | B'1' Prolog | ✅ |
| GOCA-7-023 | Bit | 4 | Any | value | Ignored | ✅ |
| GOCA-7-024 | Bits | 5-6 | APP | B'00',B'01',B'10', | or B'1 1' | ✅ |
| GOCA-7-025 | B'00' New segment | ✅ |
| GOCA-7-026 | B'01' Reserved | ✅ |
| GOCA-7-027 | B'10' Reserved | ✅ |
| GOCA-7-028 | B'1 | 1' | Append | the | specified data to the end of the existing segment | ✅ |
| GOCA-7-029 | Bit | 7 | DATAFL | Any | value Ignored | ✅ |
| GOCA-7-030 | 8-9 | UBIN | SEGL | X'0000'-X'FFFF' | Segment data length | ✅ |
| GOCA-7-031 | 10-13 | CHAR | P/SNAME | Any | value Predecessor/successor name; ignored | ✅ |
| GOCA-7-032 | The | segment | data | in | the form of drawing orders follows immediately in the following format: | ✅ |
| GOCA-7-033 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-034 | 0-n | SEGDATA | Segment | data; | the number of bytes is equal to the number defined by SEGL | ✅ |
| GOCA-7-035 | Semantics | ✅ |
| GOCA-7-036 | This | command | defines | a | segment for immediate execution. It consists of a command code, a command length, and a parameters section. The command is followed by a data section that contains the drawing orders for the | ✅ |
| GOCA-7-037 | segment. | Bytes | 0-13 | of | this command are often referred to as the Begin Segment Introducer (BSI). | ✅ |
| GOCA-7-038 | Begin Segment Command | ✅ |
| GOCA-7-039 | Begin Segment Command | ✅ |
| GOCA-7-040 | Fixed 1-byte format is used for orders that have no operand data. | ✅ |
| GOCA-7-041 | Fixed 2-byte format is used for orders that have a single byte of operand data. | ✅ |
| GOCA-7-042 | Long format is used for orders with up to 255 bytes of operand data. | ✅ |
| GOCA-7-043 | Extended format is used for orders with up to 65,535 bytes of operand data. | ✅ |
| GOCA-7-044 | One fixed 1-byte order has an order code of X'00'. | ✅ |
| GOCA-7-045 | For fixed 2-byte orders, bit 0 is set to 0, and bit 4 is set to 1, that is, the first digit of the order code is less than 8, and the second digit is greater than, or equal to, 8. | ✅ |
| GOCA-7-046 | Orders that are not any of the other three formats are long format orders. | ✅ |
| GOCA-7-047 | Extended orders have an order code of X'FE', which introduces the extended format. | ✅ |
| GOCA-7-048 | . The sequence of parameters in coordinate data is (X g , Y g ); the format of the parameters is 16-bit twos-complement signed binary integers (SBIN). The drawing processor interprets coordinate data and raises an exception condition if the length of the data is not consistent with complete specification of points. | ✅ |
| GOCA-7-049 | All current attributes and drawing process controls are set to their default values when the environment containing the graphics processor is initialized. These default values are referred to as the standard defaults. | ✅ |
| GOCA-7-050 | - Environment-dependent values. | ✅ |
| GOCA-7-051 | - Architected values, that is, one of the values that can be selected with a nonzero attribute value has been architected as the default. | ✅ |
| GOCA-7-052 | The standard defaults are copied into another set of defaults, referred to as the current defaults, when the graphics processor is initialized. | ✅ |
| GOCA-7-053 | The current defaults can be changed by the Set Current Defaults control instruction. | ✅ |
| GOCA-7-054 | When a drawing process is initiated, the current defaults are copied into a set of defaults called the drawing defaults. These are the defaults that are assumed during the execution of the drawing process. | ✅ |
| GOCA-7-055 | The current values of the primitive attributes are either set or propagated at the start of a segment. “Current | ✅ |
| GOCA-7-056 | The current values of the drawing process controls are either set or propagated at the start of a segment. | ✅ |
| GOCA-7-057 | “Drawing Process Controls” describes how the initial values are determined. | ✅ |
| GOCA-7-058 | 1-byte | X'00' | GNOP1 | No-Operation | ✅ |
| GOCA-7-059 | Long | X'01' | GCOMT | Comment | ✅ |
| GOCA-7-060 | Long | X'04' | GSGCH | Segment Characteristics | ✅ |
| GOCA-7-061 | 2-byte | X'08' | GSPS | Set Pattern Set | ✅ |
| GOCA-7-062 | 2-byte | X'0A' | GSCOL | Set Color | ✅ |
| GOCA-7-063 | 2-byte | X'0C' | GSMX | Set Mix | ✅ |
| GOCA-7-064 | 2-byte | X'0D' | GSBMX | Set Background Mix | ✅ |
| GOCA-7-065 | Long | X'11' | GSFLW | Set Fractional Line Width | ✅ |
| GOCA-7-066 | 2-byte | X'18' | GSLT | Set Line Type | ✅ |
| GOCA-7-067 | 2-byte | X'19' | GSLW | Set Line Width | ✅ |
| GOCA-7-068 | 2-byte | X'1A' | GSLE | Set Line End | ✅ |
| GOCA-7-069 | 2-byte | X'1B' | GSLJ | Set Line Join | ✅ |
| GOCA-7-070 | Long | X'20' | GSCLT | Set Custom Line Type | ✅ |
| GOCA-7-071 | Long | X'21' | GSCP | Set Current Position | ✅ |
| GOCA-7-072 | Long | X'22' | GSAP | Set Arc Parameters | ✅ |
| GOCA-7-073 | Long | X'26' | GSECOL | Set Extended Color | ✅ |
| GOCA-7-074 | 2-byte | X'28' | GSPT | Set Pattern Symbol | ✅ |
| GOCA-7-075 | 2-byte | X'29' | GSMT | Set Marker Symbol | ✅ |
| GOCA-7-076 | Long | X'33' | GSCC | Set Character Cell | ✅ |
| GOCA-7-077 | Long | X'34' | GSCA | Set Character Angle | ✅ |
| GOCA-7-078 | Long | X'35' | GSCH | Set Character Shear | ✅ |
| GOCA-7-079 | Long | X'37' | GSMC | Set Marker Cell | ✅ |
| GOCA-7-080 | 2-byte | X'38' | GSCS | Set Character Set | ✅ |
| GOCA-7-081 | 2-byte | X'39' | GSCR | Set Character Precision | ✅ |
| GOCA-7-082 | 2-byte | X'3A' | GSCD | Set Character Direction | ✅ |
| GOCA-7-083 | 2-byte | X'3B' | GSMP | Set Marker Precision (obsolete) | ✅ |
| GOCA-7-084 | 2-byte | X'3C' | GSMS | Set Marker Set | ✅ |
| GOCA-7-085 | 2-byte | X'3E' | GEPROL | End Prolog | ✅ |
| GOCA-7-086 | 2-byte | X'5E' | GECP | End Custom Pattern | ✅ |
| GOCA-7-087 | Long | X'60' | GEAR | End Area | ✅ |
| GOCA-7-088 | 2-byte | X'68' | GBAR | Begin Area | ✅ |
| GOCA-7-089 | Long | X'80' | GCBOX | Box at Current Position | ✅ |
| GOCA-7-090 | Long | X'81' | GCLINE | Line at Current Position | ✅ |
| GOCA-7-091 | Long | X'82' | GCMRK | Marker at Current Position | ✅ |
| GOCA-7-092 | Long | X'83' | GCCHST | Character String at Current Position | ✅ |
| GOCA-7-093 | Long | X'85' | GCFLT | Fillet at Current Position | ✅ |
| GOCA-7-094 | Long | X'87' | GCFARC | Full Arc at Current Position | ✅ |
| GOCA-7-095 | Long | X'91' | GCBIMG | Begin Image at Current Position | ✅ |
| GOCA-7-096 | Long | X'92' | GIMD | Image Data | ✅ |
| GOCA-7-097 | Long | X'93' | GEIMG | End Image | ✅ |
| GOCA-7-098 | Long | X'A0' | GSPRP | Set Pattern Reference Point | ✅ |
| GOCA-7-099 | Long | X'A1' | GCRLINE | Relative Line at Current Position | ✅ |
| GOCA-7-100 | Long | X'A3' | GCPARC | Partial Arc at Current Position | ✅ |
| GOCA-7-101 | Long | X'A5' | GCCBEZ | Cubic Bezier Curve at Current Position | ✅ |
| GOCA-7-102 | Long | X'B2' | GSPCOL | Set Process Color | ✅ |
| GOCA-7-103 | Long | X'C0' | GBOX | Box at Given Position | ✅ |
| GOCA-7-104 | Long | X'C1' | GLINE | Line at Given Position | ✅ |
| GOCA-7-105 | Long | X'C2' | GMRK | Marker at Given Position | ✅ |
| GOCA-7-106 | Long | X'C3' | GCHST | Character String at Given Position | ✅ |
| GOCA-7-107 | Long | X'C5' | GFLT | Fillet at Given Position | ✅ |
| GOCA-7-108 | Long | X'C7' | GFARC | Full Arc at Given Position | ✅ |
| GOCA-7-109 | Long | X'D1' | GBIMG | Begin Image at Given Position | ✅ |
| GOCA-7-110 | Long | X'DE' | GBCP | Begin Custom Pattern | ✅ |
| GOCA-7-111 | Long | X'DF' | GDPT | Delete Pattern | ✅ |
| GOCA-7-112 | Long | X'E1' | GRLINE | Relative Line at Given Position | ✅ |
| GOCA-7-113 | Long | X'E3' | GPARC | Partial Arc at Given Position | ✅ |
| GOCA-7-114 | Long | X'E5' | GCBEZ | Cubic Bezier Curve at Given Position | ✅ |
| GOCA-7-115 | Extended | X'FEDC' | GLGD | Linear Gradient | ✅ |
| GOCA-7-116 | Extended | X'FEDD' | GRGD | Radial Gradient | ✅ |
| GOCA-7-117 | Set Pick Identifier (GSPIK, X'43'). This drawing order is in long format. | ✅ |
| GOCA-7-118 | End Segment drawing order (X'71'). This drawing order is in fixed 2-byte format, where the second byte is reserved and should be set to X'00'. | ✅ |
| GOCA-7-119 | This order indicates the start of a set of primitives that define an area boundary. | ✅ |
| GOCA-7-120 | 0 | CODE | X'68' | GBAR | Order code | ✅ |
| GOCA-7-121 | 1 | BITS | FLAGS | | Internal flags:<br>bit 0 RES1 (B'1'): Reserved for migration; only valid value<br>bit 1 BOUNDARY: Boundary-line draw indicator:<br> - B'0' Do not draw boundary lines<br> - B'1' Draw boundary lines<br>bit 2 INSIDE: Mode to determine inside:<br> - B'0' Alternate mode<br> - B'1' Nonzero Winding mode<br>bits 3–7 RES2 (B'00000'): Reserved; only valid value | ✅ |
| GOCA-7-122 | Semantics | ✅ |
| GOCA-7-123 | The | Begin | Area | order | starts the definition of a filled area. The area definition must be terminated by an End | ✅ |
| GOCA-7-124 | Area | order | . | See | “Areas” for details of the area definition. | ✅ |
| GOCA-7-125 | The | BOUNDARY | bit | determines | whether or not the boundary of the area is drawn. The INSIDE bit determines the method of filling the interior . | ✅ |
| GOCA-7-126 | The | pattern | set, | pattern | symbol, pattern mix, and pattern background mix attributes that are current when the | ✅ |
| GOCA-7-127 | Begin | Area | order | is | executed are used to fill the area. When using the default pattern set or a bilevel custom pattern (but not when using a full-color custom pattern or gradient ), the pattern color attribute that is current | ✅ |
| GOCA-7-128 | when | the | Begin | Area | order was executed is also used to fill the area. When using a custom pattern, the pattern reference point attribute that is current when the Begin Area order was executed is also used in generating the | ✅ |
| GOCA-7-129 | fill | pattern. | The | line | attributes that are current at the time the orders defining the boundary are executed are used to draw the boundary . | ✅ |
| GOCA-7-130 | The | value | of | the | current position is not changed by the Begin Area order itself, but is changed by those orders used to define the area boundary , including any implicit figure-closing orders that are required. | ✅ |
| GOCA-7-131 | Area | orders | must | not | be nested and an area must be defined completely within a single segment. | ✅ |
| GOCA-7-132 | For | an | area | within | an immediate-mode segment, temporary storage can be required. An exception condition, | ✅ |
| GOCA-7-133 | EC-6805, | is | set | if | this storage overflows. | ✅ |
| GOCA-7-134 | RES1 | is | set | to | B'1' for compatibility with old implementations. Generators must set this value, but receivers can ignore this bit. | ✅ |
| GOCA-7-135 | Implementation | Note: | The | Nonzero | Winding mode value of the INSIDE bit was added to the architecture later than the Alternate mode value. As such, not all GOCA receivers support Nonzero Winding mode, and | ✅ |
| GOCA-7-136 | some | receivers | will | ignore | the new value and use Alternate mode instead. Other receivers might report the retired exception condition EC-0002 if passed a Begin Area drawing order with INSIDE=B'1'. | ✅ |
| GOCA-7-137 | Begin Area | ✅ |
| GOCA-7-138 | This order indicates the start of a set of primitives that define a custom pattern. | ✅ |
| GOCA-7-139 | 0 | CODE | X'DE' | GBCP | Order code | ✅ |
| GOCA-7-140 | 1 | UBIN | LENGTH | 13 | Length of following data | ✅ |
| GOCA-7-141 | 2–3 | RES1 | X'0000' | Reserved; only valid value | ✅ |
| GOCA-7-142 | 4 | BITS | FLAGS | | Custom Pattern flags:<br>bit 0 FULLCOLOR:<br> - B'0' Bilevel custom pattern<br> - B'1' Full-color custom pattern<br>bits 1–7 RES2 (B'0000000'): Reserved; only valid value | ✅ |
| GOCA-7-143 | 5 | CODE | PATTSET | X'01'–X'FD' | Pattern set of the custom pattern | ✅ |
| GOCA-7-144 | 6 | CODE | PATTSYM | X'01'–X'FF' | Pattern symbol of the custom pattern | ✅ |
| GOCA-7-145 | 7–8 | SBIN | XLWIND | X'8000'–X'7FFF' | $X_{g}$ coordinate for left edge of the pattern window | ✅ |
| GOCA-7-146 | 9–10 | SBIN | XRWIND | X'8000'–X'7FFF' | $X_{g}$ coordinate for right edge of the pattern window | ✅ |
| GOCA-7-147 | 11–12 | SBIN | YBWIND | X'8000'–X'7FFF' | $Y_{g}$ coordinate for bottom edge of the pattern window | ✅ |
| GOCA-7-148 | 13–14 | SBIN | YTWIND | X'8000'–X'7FFF' | $Y_{g}$ coordinate for top edge of the pattern window | ✅ |
| GOCA-7-149 | Semantics | ✅ |
| GOCA-7-150 | The | Begin | Custom | Pattern | order starts the definition of a custom pattern. The custom pattern must be terminated by an End Custom Pattern order . | ✅ |
| GOCA-7-151 | The | FULLCOLOR | flag | specifies | whether the custom pattern being defined is a bilevel custom pattern or full- | ✅ |
| GOCA-7-152 | color | custom | pattern. | Both | types must be supported by drawing processors. A custom pattern cannot change from bilevel to full-color or vice versa. | ✅ |
| GOCA-7-153 | See | “Custom | Patterns | ” | for details of custom patterns. | ✅ |
| GOCA-7-154 | The | PATTSET | and | PATTSYM | values specify the pattern set and pattern symbol where this custom pattern will reside. When the current values of the pattern set and pattern symbol attributes specify these PATTSET and | ✅ |
| GOCA-7-155 | PATTSYM | values, | respectively | , | this custom pattern will be used to do area fill. | ✅ |
| GOCA-7-156 | Custom | patterns | (defined | with | this order), linear gradients (defined with the Linear Gradient order), and radial gradients (defined with the Radial Gradient order) share the pattern sets X'01'-X'FD'; the patterns using these | ✅ |
| GOCA-7-157 | pattern | sets | can | be | any combination of custom patterns and gradients without restriction. | ✅ |
| GOCA-7-158 | Definition | of | a | custom | pattern using this drawing order does not set the pattern set and pattern symbol attributes to use the custom pattern. T o use the custom pattern, the pattern set and pattern symbol attributes | ✅ |
| GOCA-7-159 | must | be | explicitly | | set. | ✅ |
| GOCA-7-160 | All | possible | valid | values | of the PATTSET and PATTSYM fields must be supported. There are 253 ∙ 255 = | ✅ |
| GOCA-7-161 | 64,515 | possible | combinations | of | PATTSET and PATTSYM. However , it is not required that implementations | ✅ |
| GOCA-7-162 | Begin Custom Pattern | ✅ |
| GOCA-7-163 | These orders identify the start of an image definition at a given position or at the current position. | ✅ |
| GOCA-7-164 | 0 | CODE | X'D1' | GBIMG | Order code | ✅ |
| GOCA-7-165 | 1 | UBIN | LENGTH | 10 | Length of following data | ✅ |
| GOCA-7-166 | 2–3 | SBIN | XPOS | X'8000'–X'7FFF' | $X_{g}$ coordinate of image origin (first image point of first image scan line) | ✅ |
| GOCA-7-167 | 4–5 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_{g}$ coordinate of image origin (first image point of first image scan line) | ✅ |
| GOCA-7-168 | 6 | CODE | FORMAT | X'00' | Format of the image data:<br> - X'00' Each image point is mapped to a presentation device pel | ✅ |
| GOCA-7-169 | 7 | RES | | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-170 | 8–9 | UBIN | WIDTH | X'0000'–X'FFFF' | Width of the image data, in image points | ✅ |
| GOCA-7-171 | 10–11 | UBIN | HEIGHT | X'0000'–X'FFFF' | Height of the image data, in rows, or scan lines | ✅ |
| GOCA-7-172 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-173 | 0 | CODE | X'91' | GCBIMG | Order code | ✅ |
| GOCA-7-174 | 1 | UBIN | LENGTH | 6 | Length of following data | ✅ |
| GOCA-7-175 | 2 | CODE | FORMAT | X'00' | Format of the image data:<br> - X'00' Each image point is mapped to a presentation device pel | ✅ |
| GOCA-7-176 | 3 | RES | | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-177 | 4–5 | UBIN | WIDTH | X'0000'–X'FFFF' | Width of the image data, in image points | ✅ |
| GOCA-7-178 | 6–7 | UBIN | HEIGHT | X'0000'–X'FFFF' | Height of the image data, in rows, or scan lines | ✅ |
| GOCA-7-179 | Semantics | ✅ |
| GOCA-7-180 | The | Begin | Image | at | Given Position (GBIMG) order defines an image at the specified position. The Begin | ✅ |
| GOCA-7-181 | Image | at | Current | Position | (GCBIMG) order defines an image at the current position. | ✅ |
| GOCA-7-182 | An | image | consists | of | a rectangular region of points and is defined by a sequence of orders, starting with a | ✅ |
| GOCA-7-183 | Begin | Image | order | and | ending with an End Image order . Between these two orders are one or more Image | ✅ |
| GOCA-7-184 | Data, | Comment, | or | No-Operation | orders, and these are the only orders permitted. | ✅ |
| GOCA-7-185 | The | XPOS | and | YPOS | parameters define the position of the image origin—that is, the first point of the first row—which is at the top-left corner of the image. This origin is defined in GPS units. | ✅ |
| GOCA-7-186 | Begin Image | ✅ |
| GOCA-7-187 | Printers that have a fixed resolution map point-to-pel at that resolution. | ✅ |
| GOCA-7-188 | Printers that have an acceptance mode for a fixed resolution map point-to-pel at the acceptance-mode resolution and then scale to the device resolution. | ✅ |
| GOCA-7-189 | Printers that have a fixed resolution but scale transparently to a diff erent device resolution map point- to-pel at the fixed resolution and then scale to the device resolution. | ✅ |
| GOCA-7-190 | Printers that support multiple raster source resolutions map point-to-pel to the single (maximum) device resolution reported in the IPDS XOH-OPC IM-Image and Coded-Font Resolution self-defining field. Such printers normally also provide acceptance modes at lower resolutions, so that if the GOCA image size is too small at the device resolution, the customer can switch to a lower-resolution acceptance mode. | ✅ |
| GOCA-7-191 | The image is not scaled when a scale-to-fit or scale-to-fill mapping to the object area is specified for the graphics object. | ✅ |
| GOCA-7-192 | 1. The practical limit for the WIDTH parameter range is 2040, which is the maximum number of bits in the | ✅ |
| GOCA-7-193 | 2. Some presentation devices support a smaller range than X'0000'-X'FFFF' for the HEIGHT parameter . | ✅ |
| GOCA-7-194 | These orders define a box with square or round corners, drawn with its first corner at a given position or at the current position. | ✅ |
| GOCA-7-195 | 0 | CODE | X'C0' | GBOX | Order code | ✅ |
| GOCA-7-196 | 1 | UBIN | LENGTH | 10, 12, 14 | Length of following data | ✅ |
| GOCA-7-197 | 2–3 | RES | | X'2000' | Reserved; only valid value | ✅ |
| GOCA-7-198 | 4–5 | SBIN | XPOS0 | X'8000'–X'7FFF' | $X_{g}$ coordinate of first corner | ✅ |
| GOCA-7-199 | 6–7 | SBIN | YPOS0 | X'8000'–X'7FFF' | $Y_{g}$ coordinate of first corner | ✅ |
| GOCA-7-200 | 8–9 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_{g}$ coordinate of diagonal corner | ✅ |
| GOCA-7-201 | 10–11 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_{g}$ coordinate of diagonal corner | ✅ |
| GOCA-7-202 | _Optional:_ | ✅ |
| GOCA-7-203 | 12–13 | UBIN | HAXIS | 0–32,767 | Full length of x-direction axis of ellipse for rounded corner | ✅ |
| GOCA-7-204 | 14–15 | UBIN | VAXIS | 0–32,767 | Full length of y-direction axis of ellipse for rounded corner | ✅ |
| GOCA-7-205 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-206 | 0 | CODE | X'80' | GCBOX | Order code | ✅ |
| GOCA-7-207 | 1 | UBIN | LENGTH | 6, 8, 10 | Length of following data | ✅ |
| GOCA-7-208 | 2–3 | RES | | X'2000' | Reserved; only valid value | ✅ |
| GOCA-7-209 | 4–5 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_{g}$ coordinate of diagonal corner | ✅ |
| GOCA-7-210 | 6–7 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_{g}$ coordinate of diagonal corner | ✅ |
| GOCA-7-211 | _Optional:_ | ✅ |
| GOCA-7-212 | 8–9 | UBIN | HAXIS | 0–32,767 | Full length of x-direction axis of ellipse for rounded corner | ✅ |
| GOCA-7-213 | 10–11 | UBIN | VAXIS | 0–32,767 | Full length of y-direction axis of ellipse for rounded corner | ✅ |
| GOCA-7-214 | Semantics | ✅ |
| GOCA-7-215 | The | Box | at | Given | Position (GBOX) order defines a rectangular box with square or rounded corners with its first corner specified by the first coordinate pair , and the diagonally-opposite corner specified by the second | ✅ |
| GOCA-7-216 | coordinate | pair | . | The | Box at Current Position (GCBOX) order defines a rectangular box with square or rounded | ✅ |
| GOCA-7-217 | Box | ✅ |
| GOCA-7-218 | These orders draw a character string at a given position or at the current position. | ✅ |
| GOCA-7-219 | 0 | CODE | X'C3' | GCHST | Order code | ✅ |
| GOCA-7-220 | 1 | UBIN | LENGTH | 4–255 | Length of following data | ✅ |
| GOCA-7-221 | 2–3 | SBIN | XPOS | X'8000'–X'7FFF' | $X_{g}$ coordinate of character string origin | ✅ |
| GOCA-7-222 | 4–5 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_{g}$ coordinate of character string origin | ✅ |
| GOCA-7-223 | 6–n | CHAR | CP | | Code points of each character in the string | ✅ |
| GOCA-7-224 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-225 | 0 | CODE | X'83' | GCCHST | Order code | ✅ |
| GOCA-7-226 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-227 | 2–n | CHAR | CP | | Code points of each character in the string | ✅ |
| GOCA-7-228 | Semantics | ✅ |
| GOCA-7-229 | The | Character | String | at | Given Position (GCHST) order draws a character string that starts at the specified position. The Character String at Current Position (GCCHST) order draws a character string that starts at the | ✅ |
| GOCA-7-230 | current position. | ✅ |
| GOCA-7-231 | Note: | The | current | position | is changed to (XPOS,YPOS) (GCHST), or is unchanged (GCCHST). | ✅ |
| GOCA-7-232 | The | font | from | which | the character definitions are to be obtained is given by the value of the current character set attribute. If the font identified by the value in the current character set attribute is not available, EC-C300 is | ✅ |
| GOCA-7-233 | raised. | The | standard | action | for EC-C300 is to use the standard default character set. | ✅ |
| GOCA-7-234 | The | particular | character | definitions | identified by the current character set are determined by the code points in the Character String order . The length of the code points is determined by the font. | ✅ |
| GOCA-7-235 | All | code | points | in | the Character String order must refer to valid graphic characters. If they do not, EC-C301 is raised. The standard action for EC-C301 is to use the standard default character symbol. | ✅ |
| GOCA-7-236 | The | color | of | the | foreground of all characters in the string is given by the current value of the character color attribute. | ✅ |
| GOCA-7-237 | The | way | in | which | characters in the string are merged with any output primitives that have already been drawn is controlled by the values of the character mix and background mix attributes. | ✅ |
| GOCA-7-238 | The | current | values | of | the line type, line width, pattern set, and pattern symbol attributes have no effect on the appearance of the characters in the string. | ✅ |
| GOCA-7-239 | Character String | ✅ |
| GOCA-7-240 | A high-order surrogate code value was not immediately followed by a low-order surrogate code value. The high-order surrogate range is U+D800 through U+DBFF . | ✅ |
| GOCA-7-241 | A low-order surrogate code value was not immediately preceded by a high-order surrogate code value. The low-order surrogate range is U+DC00 through U+DFFF . | ✅ |
| GOCA-7-242 | An illegal UTF-8 byte sequence, as defined in the Unicode Specification, was specified. | ✅ |
| GOCA-7-243 | The value in the 1st byte of the UTF-8 byte sequence was not in the legal UTF-8 range (X'00' - X'7F' and X'C2' - X'F4'). | ✅ |
| GOCA-7-244 | The value in the 2nd byte of the UTF-8 byte sequence was not in the legal UTF-8 range allowed by the value in the 1st byte. The valid ranges for the 2nd byte are shown in Table 14. | ✅ |
| GOCA-7-245 | The value in the 3rd or 4th byte of the UTF-8 byte sequence was not in the legal UTF- 8 range for that byte (X'80' - X'BF'). | ✅ |
| GOCA-7-246 | This order enables data to be stored within a segment. | ✅ |
| GOCA-7-247 | 0 | CODE | X'01' | GCOMT | Order code | ✅ |
| GOCA-7-248 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-249 | 2–n | UNDF | DATA | Any value | Comment data | ✅ |
| GOCA-7-250 | This order is treated as a No-Op. It has no effect on the GPS or on any current attribute or control. The data within the order can be any value and is ignored. The order can appear anywhere within a segment. | ✅ |
| GOCA-7-251 | This order does not raise any exception conditions. | ✅ |
| GOCA-7-252 | These orders generate a Cubic Bezier Curve that starts at a given position or at the current position. | ✅ |
| GOCA-7-253 | 0 | CODE | X'E5' | GCBEZ | Order code | ✅ |
| GOCA-7-254 | 1 | UBIN | LENGTH | 4–n | Length of following data; n must be less than 255 and be equal to 12m + 4, where m is the number of curves | ✅ |
| GOCA-7-255 | 2–3 | SBIN | XPOS0 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve start point | ✅ |
| GOCA-7-256 | 4–5 | SBIN | YPOS0 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve start point | ✅ |
| GOCA-7-257 | 6–7 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve, first control point | ✅ |
| GOCA-7-258 | 8–9 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve, first control point | ✅ |
| GOCA-7-259 | 10–11 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve, second control point | ✅ |
| GOCA-7-260 | 12–13 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve, second control point | ✅ |
| GOCA-7-261 | 14–15 | SBIN | XPOS3 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve endpoint, second curve start point | ✅ |
| GOCA-7-262 | 16–17 | SBIN | YPOS3 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve endpoint, second curve start point | ✅ |
| GOCA-7-263 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-264 | SBIN | XPOSF-2 | X'8000'–X'7FFF' | $X_g$ coordinate of final curve, first control point | ✅ |
| GOCA-7-265 | SBIN | YPOSF-2 | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve, first control point | ✅ |
| GOCA-7-266 | SBIN | XPOSF-1 | X'8000'–X'7FFF' | $X_g$ coordinate of final curve, second control point | ✅ |
| GOCA-7-267 | SBIN | YPOSF-1 | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve, second control point | ✅ |
| GOCA-7-268 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final curve endpoint | ✅ |
| GOCA-7-269 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve endpoint | ✅ |
| GOCA-7-270 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-271 | 0 | CODE | X'A5' | GCCBEZ | Order code | ✅ |
| GOCA-7-272 | 1 | UBIN | LENGTH | 0–n | Length of following data; n must be less than 255 and be equal to 12m, where m is the number of curves | ✅ |
| GOCA-7-273 | 2–3 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve, first control point | ✅ |
| GOCA-7-274 | 4–5 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve, first control point | ✅ |
| GOCA-7-275 | 6–7 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve, second control point | ✅ |
| GOCA-7-276 | 8–9 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve, second control point | ✅ |
| GOCA-7-277 | 10–11 | SBIN | XPOS3 | X'8000'–X'7FFF' | $X_g$ coordinate of first curve endpoint, second curve start point | ✅ |
| GOCA-7-278 | 12–13 | SBIN | YPOS3 | X'8000'–X'7FFF' | $Y_g$ coordinate of first curve endpoint, second curve start point | ✅ |
| GOCA-7-279 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-280 | SBIN | XPOSF-2 | X'8000'–X'7FFF' | $X_g$ coordinate of final curve, first control point | ✅ |
| GOCA-7-281 | SBIN | YPOSF-2 | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve, first control point | ✅ |
| GOCA-7-282 | SBIN | XPOSF-1 | X'8000'–X'7FFF' | $X_g$ coordinate of final curve, second control point | ✅ |
| GOCA-7-283 | SBIN | YPOSF-1 | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve, second control point | ✅ |
| GOCA-7-284 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final curve endpoint | ✅ |
| GOCA-7-285 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final curve endpoint | ✅ |
| GOCA-7-286 | The Cubic Bezier Curve at Given Position (GCBEZ) order generates a curve that starts at $P_0$ and uses points $P_1, P_2$, and $P_3$. The Cubic Bezier Curve at Current Position (GCCBEZ) order generates a curve that starts at the current position and uses points $P_1, P_2$, and $P_3$. | ✅ |
| GOCA-7-287 | Further points are used in groups of three to form a polycurve. Each group of points, together with the last point of the previous curve, generates a new curve, every curve being drawn independently for the set of four points. | ✅ |
| GOCA-7-288 | See “Cubic Bezier Curve” for details of curve drawing. | ✅ |
| GOCA-7-289 | The length of the order, LENGTH, must be consistent with the two-byte x-coordinates and two-byte y-coordinates and the requirement for sets of points, three at a time after the initial curve. | ✅ |
| GOCA-7-290 | The current values of the line attributes are taken into account when drawing the curve. | ✅ |
| GOCA-7-291 | A Cubic Bezier Curve at Given Position (GCBEZ) order with only one point is permitted. This serves only to move the current position, which is set to the specified point. A Cubic Bezier Curve at Current Position (GCCBEZ) order with only one point (the current position) is permitted and is treated as a No-Op. | ✅ |
| GOCA-7-292 | - **EC-0003**: The order has an incorrect length. The number of points, including the current position for the GCCBEZ drawing order, must equal $3m + 1$, where $m$ is the number of curves. Each point requires a length of 4 bytes. | ✅ |
| GOCA-7-293 | This order deletes a previously defined custom pattern or gradient, or deletes all previously defined custom patterns or gradients in a given pattern set. | ✅ |
| GOCA-7-294 | 0 | CODE | X'DF' | GDPT | Order code | ✅ |
| GOCA-7-295 | 1 | UBIN | LENGTH | 3, 4 | Length of following data | ✅ |
| GOCA-7-296 | 2–3 | RES | X'0000' | Reserved; only valid value | ✅ |
| GOCA-7-297 | 4 | CODE | PATTSET | X'01'–X'FD' | Pattern set of the pattern(s) to be deleted | ✅ |
| GOCA-7-298 | 5 | CODE | PATTSYM | X'01'–X'FF' | (Optional) Pattern symbol of the pattern to be deleted | ✅ |
| GOCA-7-299 | The Delete Pattern order, when it specifies a pattern symbol value, deletes one single custom pattern or gradient that was previously defined using the Begin Custom Pattern, Linear Gradient, or Radial Gradient orders. When the Delete Pattern order does not specify a pattern symbol value, it deletes all previously defined patterns in the specified pattern set. | ✅ |
| GOCA-7-300 | The PATTSET value specifies the pattern set of the pattern(s) to be deleted. The PATTSYM value, if included, specifies the pattern symbol of the pattern to be deleted. | ✅ |
| GOCA-7-301 | A request to delete all patterns in a given pattern set does not raise an exception condition if that pattern set has no patterns defined in it. However, a request to delete a specific pattern that has not been defined raises exception condition EC-DF00, for which the standard action is to ignore the Delete Pattern order. | ✅ |
| GOCA-7-302 | Patterns in the default pattern set cannot be deleted. An attempt to do so will raise exception condition EC-DF01, for which the standard action is to ignore the Delete Pattern order. | ✅ |
| GOCA-7-303 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-304 | - **EC-DF00**: The PATTSET and PATTSYM parameters are within the valid range, but no pattern exists with the pattern set and pattern symbol specified. | ✅ |
| GOCA-7-305 | - Standard action: Ignore the Delete Pattern order. | ✅ |
| GOCA-7-306 | - **EC-DF01**: The value specified for the PATTSET parameter is invalid. | ✅ |
| GOCA-7-307 | - Standard action: Ignore the Delete Pattern order. | ✅ |
| GOCA-7-308 | - **EC-DF02**: The value specified for the PATTSYM parameter is invalid; pattern symbol X'00' cannot be deleted. | ✅ |
| GOCA-7-309 | - Standard action: Ignore the Delete Pattern order. | ✅ |
| GOCA-7-310 | This order indicates the end of a set of primitives that define an area boundary. | ✅ |
| GOCA-7-311 | 0 | CODE | X'60' | GEAR | Order code | ✅ |
| GOCA-7-312 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-313 | 2–n | DATA | X'00'... | Reserved; only valid value | ✅ |
| GOCA-7-314 | The End Area order identifies the end of an area. The bytes of data on this order must all be X'00'. LENGTH is the number of bytes of zeros, and can be zero. | ✅ |
| GOCA-7-315 | - **EC-6000**: An End Area order has been executed without an unmatched Begin Area order having previously been executed. | ✅ |
| GOCA-7-316 | This order indicates the end of a set of primitives that define a custom pattern. | ✅ |
| GOCA-7-317 | 0 | CODE | X'5E' | GECP | Order code | ✅ |
| GOCA-7-318 | 1 | RES | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-319 | The End Custom Pattern order identifies the end of the definition of a custom pattern. | ✅ |
| GOCA-7-320 | - **EC-5E00**: An End Custom Pattern order has been executed without an unmatched Begin Custom Pattern order having previously been executed. | ✅ |
| GOCA-7-321 | - Standard action: Ignore the End Custom Pattern order. | ✅ |
| GOCA-7-322 | This order identifies the end of an image definition. | ✅ |
| GOCA-7-323 | 0 | CODE | X'93' | GEIMG | Order code | ✅ |
| GOCA-7-324 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-325 | 2–n | DATA | X'00'... | Reserved; only valid value | ✅ |
| GOCA-7-326 | The End Image order identifies the end of an image. The bytes of data on this order must all be X'00'. LENGTH is the number of bytes of zeros, and can be zero. | ✅ |
| GOCA-7-327 | - **EC-9300**: An End Image order is executed without an unmatched Begin Image order having been executed previously. | ✅ |
| GOCA-7-328 | - **EC-9301**: The number of Image Data orders between the Begin Image and End Image orders is not equal to the number of rows in the image, as given by the value of HEIGHT in the Begin Image order. | ✅ |
| GOCA-7-329 | This order indicates the end of the prolog of a segment. | ✅ |
| GOCA-7-330 | 0 | CODE | X'3E' | GEPROL | Order code | ✅ |
| GOCA-7-331 | 1 | RES | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-332 | The End Prolog order indicates the end of the prolog section of a segment. See “Segment Prolog” for details of the processing of segment prologs. | ✅ |
| GOCA-7-333 | - **EC-000C**: One of the following conditions has occurred within the prolog section of a segment: | ✅ |
| GOCA-7-334 | - A supported order that is not valid within a prolog is specified. | ✅ |
| GOCA-7-335 | - The end of the segment has been reached without an End Prolog order. | ✅ |
| GOCA-7-336 | - **EC-3E00**: An End Prolog order has occurred outside the prolog section of a segment. | ✅ |
| GOCA-7-337 | These orders draw a curved line tangential to a specified set of straight lines, at the given position or at the current position. | ✅ |
| GOCA-7-338 | 0 | CODE | X'C5' | GFLT | Order code | ✅ |
| GOCA-7-339 | 1 | UBIN | LENGTH | 4–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-340 | 2–3 | SBIN | XPOS0 | X'8000'–X'7FFF' | $X_g$ coordinate of first line start point | ✅ |
| GOCA-7-341 | 4–5 | SBIN | YPOS0 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line start point | ✅ |
| GOCA-7-342 | 6–7 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-343 | 8–9 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-344 | 10–11 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-345 | 12–13 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-346 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-347 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-348 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-349 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-350 | 0 | CODE | X'85' | GCFLT | Order code | ✅ |
| GOCA-7-351 | 1 | UBIN | LENGTH | 0–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-352 | 2–3 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-353 | 4–5 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-354 | 6–7 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-355 | 8–9 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-356 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-357 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-358 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-359 | The Fillet at Given Position (GFLT) order generates a single curve that starts at a specified position. The Fillet at Current Position (GCFLT) order generates a single curve that starts at the current position. Additional points can be added to form a polycurve. | ✅ |
| GOCA-7-360 | The points specified in the order are joined by imaginary straight lines and a curve is then fitted to the lines. The curve is tangential to the first line at its start point and to the last line at its end point. If there are intermediate lines, the curve is tangential to these lines at their center points. See “Fillet” for the definition of the curves drawn. | ✅ |
| GOCA-7-361 | A Fillet at Given Position (GFLT) order with only an initial position is permitted. This serves only to move the current position. A Fillet at Current Position (GCFLT) order with only an initial position (the current position) is permitted and is treated as a No-Op. | ✅ |
| GOCA-7-362 | When only two points are supplied, a straight line results. | ✅ |
| GOCA-7-363 | The current values of the line attributes are taken into account when drawing the fillet, and the current position is set to the last point specified. | ✅ |
| GOCA-7-364 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-365 | These orders construct a full circle or an ellipse with the center at a specified point or at the current position. | ✅ |
| GOCA-7-366 | 0 | CODE | X'C7' | GFARC | Order code | ✅ |
| GOCA-7-367 | 1 | UBIN | LENGTH | 6 | Length of following data | ✅ |
| GOCA-7-368 | 2–3 | SBIN | XPOS | X'8000'–X'7FFF' | $X_g$ coordinate of the center of the circle or ellipse | ✅ |
| GOCA-7-369 | 4–5 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_g$ coordinate of the center of the circle or ellipse | ✅ |
| GOCA-7-370 | 6 | UBIN | MH | X'00'–X'FF' | Integer portion of multiplier | ✅ |
| GOCA-7-371 | 7 | UBIN | MFR | X'00'–X'FF' | Fractional portion of multiplier | ✅ |
| GOCA-7-372 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-373 | 0 | CODE | X'87' | GCFARC | Order code | ✅ |
| GOCA-7-374 | 1 | UBIN | LENGTH | 2 | Length of following data | ✅ |
| GOCA-7-375 | 2 | UBIN | MH | X'00'–X'FF' | Integer portion of multiplier | ✅ |
| GOCA-7-376 | 3 | UBIN | MFR | X'00'–X'FF' | Fractional portion of multiplier | ✅ |
| GOCA-7-377 | The Full Arc at Given Position (GFARC) order constructs a circle or an ellipse with its center at the specified position. The Full Arc at Current Position (GCFARC) order constructs a circle or an ellipse with its center at the current position. A previous Set Arc Parameters drawing order determines the shape and orientation of the arc. | ✅ |
| GOCA-7-378 | If no Set Arc Parameters drawing order has been received, the presentation process draws an arc using the drawing default values of the arc parameters. The drawing direction is defined by the determinant of the transform, which is defined by the arc parameters. | ✅ |
| GOCA-7-379 | Note: The current position is set to (XPOS, YPOS) (GFARC), or is unchanged (GCFARC). | ✅ |
| GOCA-7-380 | The current values of the line attributes, except for line join, are taken into account when drawing the full arc. The line end attribute is used only for the internal ends of dotted or dashed lines. | ✅ |
| GOCA-7-381 | If this drawing order is in an area definition, the arc is treated as a closed figure. The BOUNDARY parameter in the Begin Area order determines whether the boundary of the arc is drawn. | ✅ |
| GOCA-7-382 | MH specifies the integer portion of a scale factor; MFR specifies the fractional portion of the scale factor. A combined value of X'0000' specifies a scale factor of 0. A decimal point is implied between MH and MFR. The fractional portion of the scale factor is calculated by dividing MFR by 256. For example, if MFR=X'40', its decimal value is 64, which, divided by 256 results in a fractional component for the scale factor of 1/4. | ✅ |
| GOCA-7-383 | For a circle, the radius is $(MH \cdot R + MFR \cdot R)$ where $R$ is the radius of the circle defined by the current arc parameters. For an ellipse, the major and minor axes are $(MH \cdot MAJ + MFR \cdot MAJ)$ and $(MH \cdot MIN + MFR \cdot MIN)$, where $MAJ$ and $MIN$ are the major and minor axes of the ellipse defined by the current arc parameters. | ✅ |
| GOCA-7-384 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-385 | - **EC-C601**: The drawing processor has detected an exceptional condition that can prevent the drawing of the arc within the normal limits of pel accuracy. | ✅ |
| GOCA-7-386 | - Standard action: The arc is drawn as accurately as the implementation can define. This action might produce straight lines. | ✅ |
| GOCA-7-387 | This order specifies the raster data for one scan line or row of an image. | ✅ |
| GOCA-7-388 | 0 | CODE | X'92' | GIMD | Order code | ✅ |
| GOCA-7-389 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-390 | 2–n | BITS | DATA | Any value | Image Data | ✅ |
| GOCA-7-391 | The Image Data order contains the data for one scan line or row of an image. Each Image Data order can contain any number of bytes of data, from zero up to a maximum of 255 bytes. | ✅ |
| GOCA-7-392 | The current position is not changed by the order. | ✅ |
| GOCA-7-393 | If the LENGTH parameter is not equal to the rounded-up quotient of image WIDTH divided by 8, there are too few or too many data bytes, and exception EC-9201 exists. | ✅ |
| GOCA-7-394 | See “Begin Image (GBIMG, GCBIMG) Orders” for details of the image construct. | ✅ |
| GOCA-7-395 | - **EC-9200**: A Begin Image order was not executed before the Image Data order in this segment. | ✅ |
| GOCA-7-396 | - **EC-9201**: There are insufficient, or too many, bytes of data in the Image Data order. | ✅ |
| GOCA-7-397 | - **EC-9301**: The number of Image Data orders between the Begin Image and End Image orders is not equal to the number of rows in the image, as specified by the HEIGHT parameter in the Begin Image order. | ✅ |
| GOCA-7-398 | These orders define one or more connected straight lines, drawn from the given position or from the current position. | ✅ |
| GOCA-7-399 | 0 | CODE | X'C1' | GLINE | Order code | ✅ |
| GOCA-7-400 | 1 | UBIN | LENGTH | 4–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-401 | 2–3 | SBIN | XPOS0 | X'8000'–X'7FFF' | $X_g$ coordinate of first line start point | ✅ |
| GOCA-7-402 | 4–5 | SBIN | YPOS0 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line start point | ✅ |
| GOCA-7-403 | 6–7 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-404 | 8–9 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-405 | 10–11 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-406 | 12–13 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-407 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-408 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-409 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-410 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-411 | 0 | CODE | X'81' | GCLINE | Order code | ✅ |
| GOCA-7-412 | 1 | UBIN | LENGTH | 0–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-413 | 2–3 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-414 | 4–5 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of first line endpoint | ✅ |
| GOCA-7-415 | 6–7 | SBIN | XPOS2 | X'8000'–X'7FFF' | $X_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-416 | 8–9 | SBIN | YPOS2 | X'8000'–X'7FFF' | $Y_g$ coordinate of second line endpoint | ✅ |
| GOCA-7-417 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-418 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-419 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final line endpoint | ✅ |
| GOCA-7-420 | The Line at Given Position (GLINE) order draws a line from the point specified by the first pair of coordinates to the point specified by the second pair of coordinates. If additional coordinate pairs are specified, the presentation process draws a line from the previous endpoint to the next coordinate pair. | ✅ |
| GOCA-7-421 | The Line at Current Position (GCLINE) order draws a line from the current position to the point specified by the first coordinate pair. If additional coordinate pairs are specified, the presentation process draws a line from the previous endpoint to the next coordinate pair. Consecutive points in the orders are joined by straight lines. | ✅ |
| GOCA-7-422 | The current values of the line attributes are taken into account when drawing the line. The current position is set to the last point specified. | ✅ |
| GOCA-7-423 | A Line at Given Position (GLINE) order with only an initial position is permitted. This form of GLINE moves the current position. A Line at Current Position (GCLINE) order with only an initial position (the current position) is permitted and is treated as a No-Op. | ✅ |
| GOCA-7-424 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-425 | This order defines a linear gradient to be used to fill an area. | ✅ |
| GOCA-7-426 | 0 | CODE | X'FE' | Extended | Format order code | ✅ |
| GOCA-7-427 | 1 | CODE | X'DC' | GLGD | Qualifier code | ✅ |
| GOCA-7-428 | 2–3 | UBIN | LENGTH | 29–65,535 | Length of following data | ✅ |
| GOCA-7-429 | 4–5 | RES | X'0000' | Reserved; only valid value | ✅ |
| GOCA-7-430 | 6 | CODE | PATTSET | X'01'–X'FD' | Pattern set of the gradient | ✅ |
| GOCA-7-431 | 7 | CODE | PATTSYM | X'01'–X'FF' | Pattern symbol of the gradient | ✅ |
| GOCA-7-432 | 8–9 | SBIN | $X_S$ | X'8000'–X'7FFF' | $X_g$ coordinate of the start of the gradient line | ✅ |
| GOCA-7-433 | 10–11 | SBIN | $Y_S$ | X'8000'–X'7FFF' | $Y_g$ coordinate of the start of the gradient line | ✅ |
| GOCA-7-434 | 12–13 | SBIN | $X_E$ | X'8000'–X'7FFF' | $X_g$ coordinate of the end of the gradient line | ✅ |
| GOCA-7-435 | 14–15 | SBIN | $Y_E$ | X'8000'–X'7FFF' | $Y_g$ coordinate of the end of the gradient line | ✅ |
| GOCA-7-436 | 16–n | COLSPEC_S | | See Semantics | Color specification of the start color (13–15 bytes) | ✅ |
| GOCA-7-437 | (n+1)–m | COLVALUE_E | | See Semantics | Color value of the end color (2–4 bytes) | ✅ |
| GOCA-7-438 | m+1 | CODE | OUTSIDE_S | X'00'–X'03' | Value for how to fill areas outside the start side of the gradient:<br> - X'00' None<br> - X'01' Pad<br> - X'02' Repeat<br> - X'03' Reflect<br> - All other values: Reserved | ✅ |
| GOCA-7-439 | m+2 | CODE | OUTSIDE_E | X'00'–X'03' | Value for how to fill areas outside the end side of the gradient:<br> - X'00' None<br> - X'01' Pad<br> - X'02' Repeat<br> - X'03' Reflect<br> - All other values: Reserved | ✅ |
| GOCA-7-440 | _Optional:_ | ✅ |
| GOCA-7-441 | m+3 | UBIN | OFFSET_1 | 0–10,000 | Offset along the gradient line of the first optional color stop (2 bytes) | ✅ |
| GOCA-7-442 | COLVALUE_1 | | See Semantics | Color value of the color of the first color stop (2–4 bytes) | ✅ |
| GOCA-7-443 | UBIN | OFFSET_2 | 0–10,000 | Offset along the gradient line of the second optional color stop (2 bytes) | ✅ |
| GOCA-7-444 | COLVALUE_2 | | See Semantics | Color value of the color of the second color stop (2–4 bytes) | ✅ |
| GOCA-7-445 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-446 | UBIN | OFFSET_F | 0–10,000 | Offset along the gradient line of the final optional color stop (2 bytes) | ✅ |
| GOCA-7-447 | COLVALUE_F | | See Semantics | Color value of the color of the final color stop (2–4 bytes) | ✅ |
| GOCA-7-448 | The Linear Gradient order defines a linear gradient to be used later to fill an area. See “Gradients” for details of gradients. | ✅ |
| GOCA-7-449 | The gradient goes from the start point ($X_S, Y_S$) to the end point ($X_E, Y_E$), with the color gradually changing from the start color (COLSPEC_S) to the end color (COLVALUE_E). Areas outside the gradient are filled based on the OUTSIDE_S and OUTSIDE_E parameters. Any number of color stops can be defined along the gradient line from the start point to the end point, which define offsets along the line where a specific color is to be found. | ✅ |
| GOCA-7-450 | If the start point and end point are the same point, usage of the gradient will result in no fill, no matter the value of the OUTSIDE_S and OUTSIDE_E parameters and no matter how many color stops have been specified. | ✅ |
| GOCA-7-451 | The offset fields in the color stops have values that can range from 0 to 10,000. This value is then divided by 10,000 to produce a number from 0.0 to 1.0, with 0.0 meaning the start point, 1.0 the end point, 0.5 the halfway point, and so on. | ✅ |
| GOCA-7-452 | The color stops must be in increasing order of offset; that is, each color stop offset value must be greater than or equal to the previous color stop offset value. If a color stop has an offset value that is smaller than the offset value of any previous color stop, or is otherwise invalid, exception condition EC-DC05 is raised, for which the standard action is to ignore the color stop. | ✅ |
| GOCA-7-453 | The color specification of the start color, COLSPEC_S, has the same format as bytes 1–end of the Set Process Color (GSPCOL) drawing order; see “Set Process Color (GSPCOL) Order” for information on how to process the color specification. Included in the color specification is a length field, a color space field, and four fields indicating how many bits are in each color component, as well as a color value field. The color value field specifies the start color and is interpreted using the other fields in the color specification. For all other colors in this order—that is, for the end color and for all color stop colors—only the color value field is specified. These color values are all the same length as the color value contained in COLSPEC_S, and are interpreted in the same way. As an example, if the start color is an RGB color encoded in three bytes, one for each component (R, G, and B), then all other colors in this order will also be three-byte values, one byte for each component. | ✅ |
| GOCA-7-454 | For problems with the colors specified in this order, exception conditions EC-0E02, EC-0E03, EC-0E04, and EC-0E05 are reported as described in the Set Process Color order. Note, however, that the standard action for the EC-0E02, EC-0E03, and EC-0E05 exceptions is different for this order than for the Set Process Color drawing order. For all three exception conditions, the standard action is to ignore this Linear Gradient order. | ✅ |
| GOCA-7-455 | If the length field in COLSPEC_S (the first byte) is invalid, exception condition EC-DC06 is raised, for which the standard action is to ignore the Linear Gradient order. | ✅ |
| GOCA-7-456 | - The Standard OCA color space (X'40') cannot be used. | ✅ |
| GOCA-7-457 | - If the Highlight color space (X'06') is used, all color values must resolve to Indexed CMR Color Palette tags. | ✅ |
| GOCA-7-458 | If the color specifications do not follow these rules, exception condition EC-DC07 is raised, for which the standard action is to ignore the Linear Gradient order. | ✅ |
| GOCA-7-459 | 1 14 GOCA for AFP Reference | ✅ |
| GOCA-7-460 | Linear interpolation is done. | ✅ |
| GOCA-7-461 | Interpolation is done in the specified color space. If the specified color space is the Highlight color space: | ✅ |
| GOCA-7-462 | - If all colors resolve to Color Palette tags of the same type, interpolation is done in the color space corresponding to that type; for example, if all colors resolve to Color Palette CMYK tags, interpolation is done in the CMYK color space. - A special case of the above is if all colors resolve to Color Palette Named Colorants tags. In this case, if all named colorants required for all the colors in the gradient are available in the device, interpolation is done in the intensity of the named colorants; otherwise, interpolation is done in the CIELAB color space, using the CIELABValue field of the Color Palette Named Colorants tags. - If all colors do not resolve to Color Palette tags of the same type, interpolation in done in the CIELAB color space, using the CIELABValue field of the Color Palette tags. | ✅ |
| GOCA-7-463 | 1 16 GOCA for AFP Reference | ✅ |
| GOCA-7-464 | These orders draw the current marker symbol at one or more positions starting from the given position or from the current position. | ✅ |
| GOCA-7-465 | 0 | CODE | X'C2' | GMRK | Order code | ✅ |
| GOCA-7-466 | 1 | UBIN | LENGTH | 4–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-467 | 2–3 | SBIN | XPOS0 | X'8000'–X'7FFF' | $X_g$ coordinate of first marker | ✅ |
| GOCA-7-468 | 4–5 | SBIN | YPOS0 | X'8000'–X'7FFF' | $Y_g$ coordinate of first marker | ✅ |
| GOCA-7-469 | 6–7 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of second marker | ✅ |
| GOCA-7-470 | 8–9 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of second marker | ✅ |
| GOCA-7-471 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-472 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final marker | ✅ |
| GOCA-7-473 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final marker | ✅ |
| GOCA-7-474 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-475 | 0 | CODE | X'82' | GCMRK | Order code | ✅ |
| GOCA-7-476 | 1 | UBIN | LENGTH | 0–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-477 | 2–3 | SBIN | XPOS1 | X'8000'–X'7FFF' | $X_g$ coordinate of second marker | ✅ |
| GOCA-7-478 | 4–5 | SBIN | YPOS1 | X'8000'–X'7FFF' | $Y_g$ coordinate of second marker | ✅ |
| GOCA-7-479 | ⋮ | ⋮ | ⋮ | ⋮ | ⋮ | ✅ |
| GOCA-7-480 | SBIN | XPOSF | X'8000'–X'7FFF' | $X_g$ coordinate of final marker | ✅ |
| GOCA-7-481 | SBIN | YPOSF | X'8000'–X'7FFF' | $Y_g$ coordinate of final marker | ✅ |
| GOCA-7-482 | The Marker at Given Position (GMRK) order draws an initial marker symbol at the point specified by the first coordinate pair, and draws additional marker symbols at all the points specified by the remaining coordinate pairs. The Marker at Current Position (GCMRK) order draws an initial marker symbol at the current position and draws additional marker symbols at all the points specified by the remaining coordinate pairs. | ✅ |
| GOCA-7-483 | Markers are positioned in GPS. The specified points define the position of the center of the marker. The current position is set to the last coordinate specified. If no coordinate has been specified, the current position remains unchanged. | ✅ |
| GOCA-7-484 | A Marker at Current Position (GCMRK) order with no coordinate values specified—that is, the value of LENGTH is zero—draws a marker at the current position. | ✅ |
| GOCA-7-485 | The markers are drawn at a size determined by the marker cell-size attribute. | ✅ |
| GOCA-7-486 | The marker set from which the marker symbol is obtained is given by the value of the marker set attribute. If this marker set is not available, EC-C200 is raised, the standard action for which is to use the standard default marker set. In AFP environments, this is the default marker set. | ✅ |
| GOCA-7-487 | The particular marker symbol that is drawn is given by the value of the current marker symbol attribute. If the code point is undefined in the marker set identified by the current marker set attribute, EC-C201 is raised, the standard action for which is to use the standard default marker symbol. In AFP environments, this is X'01'—Cross. | ✅ |
| GOCA-7-488 | The color of the markers is given by the value of the current marker color. The way in which the markers are merged with any output primitives that have already been drawn is controlled by the values of the marker mix and marker background mix attributes. | ✅ |
| GOCA-7-489 | Note: It is not an error if a marker symbol is placed inside the GPS such that part of the marker lies outside the GPS. However, the appearance of such a marker in the GPS is implementation defined. | ✅ |
| GOCA-7-490 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-491 | - **EC-C200**: The marker set identified by the value in the current marker set attribute is not available. | ✅ |
| GOCA-7-492 | - Standard action: The standard default marker set is used. In AFP environments, this is the default marker set. | ✅ |
| GOCA-7-493 | - **EC-C201**: The code point in the current marker symbol attribute is not defined in the current marker set. | ✅ |
| GOCA-7-494 | - Standard action: The standard default marker symbol is used. In AFP environments, this is X'01'—Cross. | ✅ |
| GOCA-7-495 | This order is a No-Operation. | ✅ |
| GOCA-7-496 | 0 | CODE | X'00' | GNOP1 | Order code | ✅ |
| GOCA-7-497 | The No-Operation order is a null operation. It has no effect on the GPS, or any current attribute or control. | ✅ |
| GOCA-7-498 | This order does not raise any exception conditions. | ✅ |
| GOCA-7-499 | These orders draw a line from the given position or the current position to the start of an arc, and then construct a partial arc. The start point of the arc is specified by the start angle, and the length of the arc is specified by the sweep angle. | ✅ |
| GOCA-7-500 | 0 | CODE | X'E3' | GPARC | Order code | ✅ |
| GOCA-7-501 | 1 | UBIN | LENGTH | 18 | Length of following data | ✅ |
| GOCA-7-502 | 2-3 | SBIN | XPOS | X'8000'-X'7FFF' | $X_g$ coordinate of line start point | ✅ |
| GOCA-7-503 | 4-5 | SBIN | YPOS | X'8000'-X'7FFF' | $Y_g$ coordinate of line start point | ✅ |
| GOCA-7-504 | 6-7 | SBIN | XCENT | X'8000'-X'7FFF' | $X_g$ coordinate of the center of the arc | ✅ |
| GOCA-7-505 | 8-9 | SBIN | YCENT | X'8000'-X'7FFF' | $Y_g$ coordinate of the center of the arc | ✅ |
| GOCA-7-506 | 10 | UBIN | MH | X'00'-X'FF' | Integer portion of multiplier | ✅ |
| GOCA-7-507 | 11 | UBIN | MFR | X'00'-X'FF' | Fractional portion of multiplier | ✅ |
| GOCA-7-508 | 12-15 | SBIN | START | X'00000000'-X'7FFFFFFF' | Start angle of arc, modulo 360 | ✅ |
| GOCA-7-509 | 16-19 | SBIN | SWEEP | X'00000000'-X'7FFFFFFF' | Sweep angle of arc, modulo 360 | ✅ |
| GOCA-7-510 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-511 | 0 | CODE | X'A3' | GCPARC | Order code | ✅ |
| GOCA-7-512 | 1 | UBIN | LENGTH | 14 | Length of following data | ✅ |
| GOCA-7-513 | 2-3 | SBIN | XCENT | X'8000'-X'7FFF' | $X_g$ coordinate of the center of the arc | ✅ |
| GOCA-7-514 | 4-5 | SBIN | YCENT | X'8000'-X'7FFF' | $Y_g$ coordinate of the center of the arc | ✅ |
| GOCA-7-515 | 6 | UBIN | MH | X'00'-X'FF' | Integer portion of multiplier | ✅ |
| GOCA-7-516 | 7 | UBIN | MFR | X'00'-X'FF' | Fractional portion of multiplier | ✅ |
| GOCA-7-517 | 8-11 | SBIN | START | X'00000000'-X'7FFFFFFF' | Start angle of arc, modulo 360 | ✅ |
| GOCA-7-518 | 12-15 | SBIN | SWEEP | X'00000000'-X'7FFFFFFF' | Sweep angle of arc, modulo 360 | ✅ |
| GOCA-7-519 | The Partial Arc at Given Position (GPARC) order draws a line from point (XPOS, YPOS) to the start of an arc, then draws the arc with its center at point (XCENT, YCENT). The Partial Arc at Current Position (GCPARC) order draws a line from the current position to the start of an arc, then draws the arc with its center at point (XCENT, YCENT). The arc is part of the full arc defined by the current arc parameters and the multiplier specified by MH and MFR. | ✅ |
| GOCA-7-520 | The part of the arc that is drawn is defined by the starting angle, START, and the sweep angle, SWEEP. Both angles are defined on the unit circle space and are transformed by an amount defined by the current arc parameters in the same way that the unit circle is transformed. See "Partial Arc".or details. | ✅ |
| GOCA-7-521 | A previous Set Arc Parameters drawing order determines the shape and orientation of the arc. If no Set Arc Parameters drawing order has been received, the presentation process draws an arc using the drawing default values of the arc parameters. | ✅ |
| GOCA-7-522 | The drawing direction is defined by the determinant of the transform, which is defined by the arc parameters. For details, see page 24. | ✅ |
| GOCA-7-523 | MH specifies the integer portion of a scale factor; MFR specifies the fractional portion of the scale factor. A decimal point is implied between MH and MFR. The fractional portion of the scale factor is calculated by dividing MFR by 256. For example, if MFR=X'40', its decimal value is 64, which, divided by 256 results in a fractional component for the scale factor of 1/4. | ✅ |
| GOCA-7-524 | For a circle, the radius is $(MH \cdot R + MFR \cdot R)$ where R is the radius of the circle defined by the current arc parameters. | ✅ |
| GOCA-7-525 | For an ellipse, the major and minor axes are $(MH \cdot MAJ + MFR \cdot MAJ)$ and $(MH \cdot MIN + MFR \cdot MIN)$, where MAJ and MIN are the major and minor axes of the ellipse defined by the current arc parameters. | ✅ |
| GOCA-7-526 | The START and SWEEP parameters are defined as signed 32-bit integers, whose range is restricted to positive values, that is, X'00000000' to X'7FFFFFFF'. The START and SWEEP angles are the numbers, in degrees, that result from dividing the integers by 65,536 ($2^{16}$) and interpreting the result as a modulo 360 number. The effective range of the angles is therefore greater than or equal to 0° and less than 360°. For example, if the sweep angle is specified to be X'00007FFF', its value is $32,767 \div 65,536 \pmod{360} = .5^\circ$. | ✅ |
| GOCA-7-527 | Note that since a sweep angle of any integer multiple of 360° results in a 0° arc, this drawing order cannot be used to draw a complete arc. The Full Arc drawing order can be used to draw a complete arc. | ✅ |
| GOCA-7-528 | The current values of the line attributes are taken into account when drawing the partial arc. | ✅ |
| GOCA-7-529 | The current position is moved to the endpoint of the arc. | ✅ |
| GOCA-7-530 | - **EC-0003**: The order has incorrect length. | ✅ |
| GOCA-7-531 | - **EC-E300**: The partial arc started inside GPS but then finished outside. Therefore, the calculated new current position is outside GPS. | ✅ |
| GOCA-7-532 | - **EC-E302**: A negative value is specified for the SWEEP angle. | ✅ |
| GOCA-7-533 | - **EC-E303**: A negative value is specified for the START angle. | ✅ |
| GOCA-7-534 | - **EC-000D**: The start and end points of a partial arc are inside GPS, but a portion of the arc is outside GPS. | ✅ |
| GOCA-7-535 | - Standard action: All drawing outside the GPS is suppressed. The portion of the arc that is inside the GPS is drawn. | ✅ |
| GOCA-7-536 | - **EC-C601**: The drawing processor has detected an exceptional condition that can prevent the drawing of the arc within the normal limits of pel accuracy. | ✅ |
| GOCA-7-537 | - Standard action: The arc is drawn as accurately as the implementation can define. This action might produce straight lines. | ✅ |
| GOCA-7-538 | This order defines a radial gradient to be used to fill an area. | ✅ |
| GOCA-7-539 | 0 | CODE | X'FE' | Extended | Format order code | ✅ |
| GOCA-7-540 | 1 | CODE | X'DD' | GRGD | Qualifier code | ✅ |
| GOCA-7-541 | 2-3 | UBIN | LENGTH | 33-65,535 | Length of following data | ✅ |
| GOCA-7-542 | 4-5 | RES | X'0000' | Reserved | Only valid value | ✅ |
| GOCA-7-543 | 6 | CODE | PATTSET | X'01'-X'FD' | Pattern set of the gradient | ✅ |
| GOCA-7-544 | 7 | CODE | PATTSYM | X'01'-X'FF' | Pattern symbol of the gradient | ✅ |
| GOCA-7-545 | 8-9 | SBIN | XPOS_S | X'8000'-X'7FFF' | $X_g$ coordinate of the center of the start full arc | ✅ |
| GOCA-7-546 | 10-11 | SBIN | YPOS_S | X'8000'-X'7FFF' | $Y_g$ coordinate of the center of the start full arc | ✅ |
| GOCA-7-547 | 12 | UBIN | MH_S | X'00'-X'FF' | Integer portion of the multiplier for the start full arc | ✅ |
| GOCA-7-548 | 13 | UBIN | MFR_S | X'00'-X'FF' | Fractional portion of the multiplier for the start full arc | ✅ |
| GOCA-7-549 | 14-15 | SBIN | XPOS_E | X'8000'-X'7FFF' | $X_g$ coordinate of the center of the end full arc | ✅ |
| GOCA-7-550 | 16-17 | SBIN | YPOS_E | X'8000'-X'7FFF' | $Y_g$ coordinate of the center of the end full arc | ✅ |
| GOCA-7-551 | 18 | UBIN | MH_E | X'00'-X'FF' | Integer portion of the multiplier for the end full arc | ✅ |
| GOCA-7-552 | 19 | UBIN | MFR_E | X'00'-X'FF' | Fractional portion of the multiplier for end full arc | ✅ |
| GOCA-7-553 | 20-n | COLSPEC_S | See Semantics | | Color specification of the start color (13-15 bytes) | ✅ |
| GOCA-7-554 | (n+1)-m | COLVALUE_E | See Semantics | | Color value of the end color (2-4 bytes) | ✅ |
| GOCA-7-555 | m+1 | CODE | OUTSIDE_S | X'00'-X'03' | Value for how to fill areas outside the start side of the gradient:<br>X'00' None<br>X'01' Pad<br>X'02' Repeat<br>X'03' Reflect<br>All other values Reserved | ✅ |
| GOCA-7-556 | m+2 | CODE | OUTSIDE_E | X'00'-X'03' | Value for how to fill areas outside the end side of the gradient:<br>X'00' None<br>X'01' Pad<br>X'02' Repeat<br>X'03' Reflect<br>All other values Reserved | ✅ |
| GOCA-7-557 | The following parameters are optional: | ✅ |
| GOCA-7-558 | m+3 | UBIN | OFFSET_1 | 0-10,000 | Offset of the intermediate full arc of the first optional color stop (2 bytes) | ✅ |
| GOCA-7-559 | COLVALUE_1 | See Semantics | | Color value of the color of the first color stop (2-4 bytes) | ✅ |
| GOCA-7-560 | UBIN | OFFSET_2 | 0-10,000 | Offset of the intermediate full arc of the second optional color stop (2 bytes) | ✅ |
| GOCA-7-561 | COLVALUE_2 | See Semantics | | Color value of the color of the second color stop (2-4 bytes) | ✅ |
| GOCA-7-562 | ⋮ | ⋮ | ⋮ | ⋮ | Further color stop information | ✅ |
| GOCA-7-563 | UBIN | OFFSET_F | 0-10,000 | Offset of the intermediate full arc of the final optional color stop (2 bytes) | ✅ |
| GOCA-7-564 | COLVALUE_F | See Semantics | | Color value of the color of the final color stop (2-4 bytes) | ✅ |
| GOCA-7-565 | The Radial Gradient order defines a radial gradient to be used later to fill an area. See "Gradients" for details of gradients. | ✅ |
| GOCA-7-566 | The gradient goes from the start full arc to the end full arc. The color changes gradually from the start color (COLSPEC_S) to the end color (COLVALUE_E). Areas outside the gradient are filled based on the OUTSIDE_S and OUTSIDE_E parameters. Any number of color stops can be defined along the gradient lines from the start full arc to the end full arc, which define intermediate full arcs between the start and end full arcs where a specific color is to be found. | ✅ |
| GOCA-7-567 | The start and end full arcs are defined in the same way as in the Full Arc at Given Position (GFARC) drawing order, using the appropriate XPOS, YPOS, MH, and MFR values in this drawing order. See "Full Arc (GFARC, GCFARC) Orders" for more information. Note that since both full arcs use the same arc parameters, the two will have the same shape (as will all intermediate full arcs along the gradient). | ✅ |
| GOCA-7-568 | Either multiplier value can be zero (that is, either MH_S=MFR_S=0 or MH_E=MFR_E=0), in which case the gradient starts or ends at a point instead of a full arc. If both multiplier values are zero (MH_S=MFR_S=MH_E=MFR_E=0), however, usage of the gradient will result in no fill, no matter the value of the OUTSIDE_S and OUTSIDE_E parameters and no matter how many color stops have been specified. In addition, if the start and end full arc have the same center and multiplier, usage of the gradient will result in no fill. | ✅ |
| GOCA-7-569 | If part or all of either full arc is outside the GPS, this is not an error. This functionality can be used to get radial gradients that completely fill the GPS. Implementations that can maintain a position outside the GPS should produce a gradient as expected—gradually changing from the start color at the start full arc toward the end color at the end full arc, even though some parts of the intermediate full arcs might be outside the GPS. For implementations that cannot maintain a position outside GPS, the results are implementation dependent. | ✅ |
| GOCA-7-570 | The offset fields in the color stops have values that can range from 0 to 10,000. This value is then divided by 10,000 to produce a number from 0.0 to 1.0, with 0.0 meaning the start full arc, 1.0 the end full arc, 0.5 the intermediate full arc halfway between the two, and so on. | ✅ |
| GOCA-7-571 | The color stops must be in increasing order of offset; that is, each color stop offset value must be greater than or equal to the previous color stop offset value. If a color stop has an offset value that is smaller than the offset value of any previous color stop, or is otherwise invalid, exception condition EC-DD05 is raised, for which the standard action is to ignore the color stop. | ✅ |
| GOCA-7-572 | The color specification of the start color, COLSPEC_S, has the same format as bytes 1–end of the Set Process Color (GSPCOL) drawing order; see "Set Process Color (GSPCOL) Order" for information on how to process the color specification. Included in the color specification is a length field, a color space field, and four fields indicating how many bits are in each color component, as well as a color value field. The color value field specifies the start color and is interpreted using the other fields in the color specification. For all other colors in this order—that is, for the end color and for all color stop colors—only the color value field is specified. | ✅ |
| GOCA-7-573 | These color values are all the same length as the color value contained in COLSPEC_S, and are interpreted in the same way. As an example, if the start color is an RGB color encoded in three bytes, one for each component (R, G, and B), then all other colors in this order will also be three-byte values, one byte for each component. | ✅ |
| GOCA-7-574 | For problems with the colors specified in this order, exception conditions EC-0E02, EC-0E03, EC-0E04, and EC-0E05 are reported as described in the Set Process Color order. Note, however, that the standard action for the EC-0E02, EC-0E03, and EC-0E05 exceptions is different for this order than for the Set Process Color drawing order. For all three exception conditions, the standard action is to ignore this Radial Gradient order. | ✅ |
| GOCA-7-575 | If the length field in COLSPEC_S (the first byte) is invalid, exception condition EC-DD06 is raised, for which the standard action is to ignore the Radial Gradient order. | ✅ |
| GOCA-7-576 | - The Standard OCA color space (X'40') cannot be used. | ✅ |
| GOCA-7-577 | - If the Highlight color space (X'06') is used, all color values must resolve to Indexed CMR Color Palette tags. | ✅ |
| GOCA-7-578 | If the color specifications do not follow these rules, exception condition EC-DD07 is raised, for which the standard action is to ignore the Radial Gradient order. | ✅ |
| GOCA-7-579 | - Linear interpolation is done. | ✅ |
| GOCA-7-580 | - Interpolation is done in the specified color space. If the specified color space is the Highlight color space: | ✅ |
| GOCA-7-581 | - If all colors resolve to Color Palette tags of the same type, interpolation is done in the color space corresponding to that type; for example, if all colors resolve to Color Palette CMYK tags, interpolation is done in the CMYK color space. | ✅ |
| GOCA-7-582 | - A special case of the above is if all colors resolve to Color Palette Named Colorants tags. In this case, if all named colorants required for all the colors in the gradient are available in the device, interpolation is done in the intensity of the named colorants; otherwise, interpolation is done in the CIELAB color space, using the CIELABValue field of the Color Palette Named Colorants tags. | ✅ |
| GOCA-7-583 | - If all colors do not resolve to Color Palette tags of the same type, interpolation in done in the CIELAB color space, using the CIELABValue field of the Color Palette tags. | ✅ |
| GOCA-7-584 | If the LENGTH field of this drawing order is not a valid length, given the expected color value sizes, exception EC-0003 is raised. The valid values for the LENGTH field, where $n$ is the number of color stops, are as follows: | ✅ |
| GOCA-7-585 | 12 | 33 + ($n \cdot 4$) | ✅ |
| GOCA-7-586 | 13 | 35 + ($n \cdot 5$) | ✅ |
| GOCA-7-587 | 14 | 37 + ($n \cdot 6$) | ✅ |
| GOCA-7-588 | The PATTSET and PATTSYM values specify the pattern set and pattern symbol where this gradient will reside. When the current values of the pattern set and pattern symbol attributes specify these PATTSET and PATTSYM values, respectively, this gradient will be used to do area fill. | ✅ |
| GOCA-7-589 | Linear gradients (defined with the Linear Gradient order), radial gradients (defined with this order), and custom patterns (defined with the Begin Custom Pattern order) share the pattern sets X'01'–X'FD'; the patterns using these pattern sets can be any combination of gradients and custom patterns without restriction. | ✅ |
| GOCA-7-590 | Definition of a gradient using this drawing order does not set the pattern set and pattern symbol attributes to use the gradient. To use the gradient, the pattern set and pattern symbol attributes must be explicitly set. | ✅ |
| GOCA-7-591 | All possible valid values of the PATTSET and PATTSYM fields must be supported. There are $253 \cdot 255 = 64,515$ possible combinations of PATTSET and PATTSYM. However, it is not required that implementations support the creation of 64,515 gradients. If insufficient storage is available to correctly handle a radial gradient definition, exception condition EC-DD03 is raised, for which the standard action is to ignore the Radial Gradient order. | ✅ |
| GOCA-7-592 | It is not possible to replace a gradient or custom pattern simply by sending a Radial Gradient order with the same PATTSET and PATTSYM parameters. If this is attempted, exception EC-DD02 is raised, for which the standard action is to ignore the Radial Gradient order. To replace a pattern at a given pattern set and pattern symbol, first delete the existing pattern using the Delete Pattern drawing order. | ✅ |
| GOCA-7-593 | The current position is not used nor changed by this drawing order. | ✅ |
| GOCA-7-594 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-595 | - **EC-0E02**: The color space specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-596 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-597 | - **EC-0E03**: A color value specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-598 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-599 | - **EC-0E04**: A highlight color percent value specified in the order is invalid. | ✅ |
| GOCA-7-600 | - Standard action: The maximum valid percent value is used. | ✅ |
| GOCA-7-601 | - **EC-0E05**: The number of bits for a color component specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-602 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-603 | - **EC-C601**: The drawing processor has detected an exceptional condition that can prevent the drawing of one of the full arcs within the normal limits of pel accuracy. | ✅ |
| GOCA-7-604 | - Standard action: The arc is drawn as accurately as the implementation can define. This action might produce straight lines. | ✅ |
| GOCA-7-605 | - **EC-DD00**: The value specified for the PATTSET parameter is invalid. | ✅ |
| GOCA-7-606 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-607 | - **EC-DD01**: The value specified for the PATTSYM parameter is invalid; a gradient cannot be assigned to pattern symbol X'00'. | ✅ |
| GOCA-7-608 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-609 | - **EC-DD02**: The PATTSET and PATTSYM values are within the valid range, but a pattern already resides at that location. | ✅ |
| GOCA-7-610 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-611 | - **EC-DD03**: Insufficient storage available to process and store a radial gradient. | ✅ |
| GOCA-7-612 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-613 | - **EC-DD04**: A value for one or both of the OUTSIDE_S or OUTSIDE_E parameters is invalid. | ✅ |
| GOCA-7-614 | - Standard action: Use the value X'00' - None. | ✅ |
| GOCA-7-615 | - **EC-DD05**: A color stop has an offset value that is smaller than the offset value of any previous color stop, or is otherwise invalid. | ✅ |
| GOCA-7-616 | - Standard action: Ignore the color stop. | ✅ |
| GOCA-7-617 | - **EC-DD06**: The length field in COLSPEC_S (the first byte) is invalid. | ✅ |
| GOCA-7-618 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-619 | - **EC-DD07**: Color specifications do not follow the specific rules for the Highlight color space or Standard OCA color space. | ✅ |
| GOCA-7-620 | - Standard action: Ignore the Radial Gradient order. | ✅ |
| GOCA-7-621 | These orders define one or more connected straight lines, at the given position or at the current position. For these drawing orders, the endpoint of each line is specified as an offset from the previous endpoint rather than as an absolute value. | ✅ |
| GOCA-7-622 | 0 | CODE | X'E1' | GRLINE | Order code | ✅ |
| GOCA-7-623 | 1 | UBIN | LENGTH | 4–n | Length of following data; $n$ must be less than 255 and a multiple of 2 | ✅ |
| GOCA-7-624 | 2-3 | SBIN | XPOS0 | X'8000'-X'7FFF' | $X_g$ coordinate of line start point | ✅ |
| GOCA-7-625 | 4-5 | SBIN | YPOS0 | X'8000'-X'7FFF' | $Y_g$ coordinate of line start point | ✅ |
| GOCA-7-626 | 6 | SBIN | XOFFS0 | X'80'-X'7F' | Offset in $X_g$ direction for first endpoint | ✅ |
| GOCA-7-627 | 7 | SBIN | YOFFS0 | X'80'-X'7F' | Offset in $Y_g$ direction for first endpoint | ✅ |
| GOCA-7-628 | 8 | SBIN | XOFFS1 | X'80'-X'7F' | Offset in $X_g$ direction for second endpoint | ✅ |
| GOCA-7-629 | 9 | SBIN | YOFFS1 | X'80'-X'7F' | Offset in $Y_g$ direction for second endpoint | ✅ |
| GOCA-7-630 | ⋮ | ⋮ | ⋮ | ⋮ | Offset data for further points | ✅ |
| GOCA-7-631 | 2n+4 | SBIN | XOFFSF | X'80'-X'7F' | Offset in $X_g$ direction for final endpoint | ✅ |
| GOCA-7-632 | 2n+5 | SBIN | YOFFSF | X'80'-X'7F' | Offset in $Y_g$ direction for final endpoint | ✅ |
| GOCA-7-633 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-7-634 | 0 | CODE | X'A1' | GCRLINE | Order code | ✅ |
| GOCA-7-635 | 1 | UBIN | LENGTH | 0–n | Length of following data; $n$ must be less than 255 and a multiple of 2 | ✅ |
| GOCA-7-636 | 2 | SBIN | XOFFS0 | X'80'-X'7F' | Offset in $X_g$ direction for first endpoint | ✅ |
| GOCA-7-637 | 3 | SBIN | YOFFS0 | X'80'-X'7F' | Offset in $Y_g$ direction for first endpoint | ✅ |
| GOCA-7-638 | 4 | SBIN | XOFFS1 | X'80'-X'7F' | Offset in $X_g$ direction for second endpoint | ✅ |
| GOCA-7-639 | 5 | SBIN | YOFFS1 | X'80'-X'7F' | Offset in $Y_g$ direction for second endpoint | ✅ |
| GOCA-7-640 | ⋮ | ⋮ | ⋮ | ⋮ | Offset data for further points | ✅ |
| GOCA-7-641 | 2n | SBIN | XOFFSF | X'80'-X'7F' | Offset in $X_g$ direction for final endpoint | ✅ |
| GOCA-7-642 | 2n+1 | SBIN | YOFFSF | X'80'-X'7F' | Offset in $Y_g$ direction for final endpoint | ✅ |
| GOCA-7-643 | The Relative Line at Given Position (GRLINE) order adds the offset of each line endpoint cumulatively to the line start point (specified by XPOS0, YPOS0) to generate a sequence of points $P_1, P_2, \dots, P_f$, where $P_f$ is the final endpoint specified. The Relative Line at Current Position (GCRLINE) order adds the offset of each line endpoint cumulatively to the current position to generate a sequence of points $P_1, P_2, \dots, P_f$, where $P_f$ is the final endpoint specified. | ✅ |
| GOCA-7-644 | Straight lines are drawn connecting the points in sequence, that is, from the starting point to $P_1$, from $P_1$ to $P_2, \dots$, and from $P_{f-1}$ to $P_f$. | ✅ |
| GOCA-7-645 | Any number of offsets can be included within the limits of the length specifications. | ✅ |
| GOCA-7-646 | A Relative Line at Given Position (GRLINE) order with only an initial position is permitted. This serves only to move the current position, which is set to the specified point. A Relative Line at Current Position (GCRLINE) order with only an initial position (the current position) is permitted and is treated as a No-Op. | ✅ |
| GOCA-7-647 | A relative line that starts inside GPS, but has values of offset specified that accumulate to be outside GPS, is an error. | ✅ |
| GOCA-7-648 | The current values of the line attributes are taken into account when drawing the relative lines. | ✅ |
| GOCA-7-649 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-650 | - **EC-E100**: A relative line starts inside GPS, but goes outside. | ✅ |
| GOCA-7-651 | This order is processed as a No-Op in AFP GOCA. It is valid only in the prolog of a segment. | ✅ |
| GOCA-7-652 | 0 | CODE | X'04' | GSGCH | Order code | ✅ |
| GOCA-7-653 | 1 | UBIN | LENGTH | 0–255 | Length of following data | ✅ |
| GOCA-7-654 | 2 | CODE | CHID | X'00' | Identification code for characteristics | ✅ |
| GOCA-7-655 | 3–n | UNDF | PARMS | | Parameters of characteristics | ✅ |
| GOCA-7-656 | Not applicable in AFP GOCA. | ✅ |
| GOCA-7-657 | - **EC-0400**: The Segment Characteristics order is detected outside the prolog section of a segment. | ✅ |
| GOCA-7-658 | This order sets the values of the current arc parameters. | ✅ |
| GOCA-7-659 | 0 | CODE | X'22' | GSAP | Order code | ✅ |
| GOCA-7-660 | 1 | UBIN | LENGTH | 8 | Length of following data | ✅ |
| GOCA-7-661 | 2-3 | SBIN | P | X'8000'-X'7FFF' | P parameter of arc transform | ✅ |
| GOCA-7-662 | 4-5 | SBIN | Q | X'8000'-X'7FFF' | Q parameter of arc transform | ✅ |
| GOCA-7-663 | 6-7 | SBIN | R | X'8000'-X'7FFF' | R parameter of arc transform | ✅ |
| GOCA-7-664 | 8-9 | SBIN | S | X'8000'-X'7FFF' | S parameter of arc transform | ✅ |
| GOCA-7-665 | The Set Arc Parameters order specifies the shape and orientation of a circle or an ellipse. Subsequent Full Arc orders specify the size and location of the circle or ellipse. Subsequent Partial Arc orders specify the size and location of the circle or ellipse that the partial arc is part of. For details, see "Full Arc" and "Partial Arc". | ✅ |
| GOCA-7-666 | where $X$ and $Y$ are the coordinates of points on the unit circle, and $X'$ and $Y'$ are the coordinates of points on the arc. The drawing direction is defined by the determinant of the transform, which is defined by the arc parameters. For details, see page 24. | ✅ |
| GOCA-7-667 | - $P = a \cdot \cos(A)$ | ✅ |
| GOCA-7-668 | - $Q = b \cdot \cos(A)$ | ✅ |
| GOCA-7-669 | - $R = -b \cdot \sin(A)$ | ✅ |
| GOCA-7-670 | - $S = a \cdot \sin(A)$ | ✅ |
| GOCA-7-671 | This drawing order does not change the current position. | ✅ |
| GOCA-7-672 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-673 | Character background mix | ✅ |
| GOCA-7-674 | Image background mix | ✅ |
| GOCA-7-675 | Marker background mix | ✅ |
| GOCA-7-676 | Pattern background mix | ✅ |
| GOCA-7-677 | 0 | CODE | X'0D' | GSBMX | Order code | ✅ |
| GOCA-7-678 | 1 | CODE | MODE | X'00'–X'05' | Mix-mode value:<br>X'00' Drawing default<br>X'01'–X'04' Not supported in AFP GOCA<br>X'05' Leave Alone<br>All other values are reserved | ✅ |
| GOCA-7-679 | The Set Background Mix order sets the current value of all four background mix attributes to the value specified in the order. The standard default in AFP environments is X'05'—Leave Alone. | ✅ |
| GOCA-7-680 | Background mix attributes control the way in which the color of the background of a primitive is combined with the color of the GPS. With MODE set to X'05', the background pels are transparent and do not affect the color of underlying pels in the GPS. Since this is the only background mix mode supported in AFP GOCA, selecting the drawing default (MODE X'00') will also default to MODE X'05'. | ✅ |
| GOCA-7-681 | For a description of the meaning of the various mix modes, see “Mix”. | ✅ |
| GOCA-7-682 | The following exception conditions cause a standard action to be taken: | ✅ |
| GOCA-7-683 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'05'—Leave Alone. | ✅ |
| GOCA-7-684 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'05'—Leave Alone. | ✅ |
| GOCA-7-685 | This order sets the value of the current character angle attribute. | ✅ |
| GOCA-7-686 | 0 | CODE | X'34' | GSCA | Order code | ✅ |
| GOCA-7-687 | 1 | UBIN | LENGTH | 4 | Length of following data | ✅ |
| GOCA-7-688 | 2–3 | SBIN | XPOS | X'8000'–X'7FFF' | $X_{g}$ coordinate of point | ✅ |
| GOCA-7-689 | 4–5 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_{g}$ coordinate of point | ✅ |
| GOCA-7-690 | The Set Character Angle order sets the value of the current character angle attribute to the value specified in the order. | ✅ |
| GOCA-7-691 | The character angle attribute controls the angle of the character baseline with respect to the GPS $X_{g}$ axis for subsequent character strings. This angle is specified using the values XPOS and YPOS, where the character baseline is a line parallel to the line that passes through the points ($X_{g}=0, Y_{g}=0$) and ($X_{g}=$ XPOS, $Y_{g}=$ YPOS). The angle of the baseline relative to the $X_{g}$ axis of GPS is then the angle whose tangent is YPOS/XPOS. The values of YPOS and XPOS are not required to be the sine and cosine of the angle. | ✅ |
| GOCA-7-692 | If YPOS is zero, and XPOS is positive, the character angle is 0°. | ✅ |
| GOCA-7-693 | If XPOS is zero, and YPOS is positive, the character angle is 90°. | ✅ |
| GOCA-7-694 | If YPOS is zero, and XPOS is negative, the character angle is 180°. | ✅ |
| GOCA-7-695 | If XPOS is zero, and YPOS is negative, the character angle is 270°. | ✅ |
| GOCA-7-696 | In AFP GOCA, the only supported values for character angle are 0°, 90°, 180°, and 270°. If XPOS is zero and YPOS is zero, the character angle is set to the drawing default. The standard default in AFP environments is 0°. | ✅ |
| GOCA-7-697 | The application of this attribute is dependent on the value of the character precision attribute; see “Character Strings” for details. This drawing order does not change the attributes of any other drawing order. | ✅ |
| GOCA-7-698 | The following exception condition raises a drawing process check: | ✅ |
| GOCA-7-699 | EC-0003** The order has an incorrect length. | ✅ |
| GOCA-7-700 | The following exception condition causes a standard action to be taken: | ✅ |
| GOCA-7-701 | Standard action: The closest character angle supported is used. | ✅ |
| GOCA-7-702 | This order sets the value of the current character cell-size attribute. | ✅ |
| GOCA-7-703 | 0 | CODE | X'33' | GSCC | Order code | ✅ |
| GOCA-7-704 | 1 | UBIN | LENGTH | 4, 8 | Length of following data | ✅ |
| GOCA-7-705 | 2–3 | SBIN | CELLWI | X'8000'–X'7FFF' | Width of character cell, integer part | ✅ |
| GOCA-7-706 | 4–5 | SBIN | CELLHI | X'8000'–X'7FFF' | Height of character cell, integer part | ✅ |
| GOCA-7-707 | 6–7 | UBIN | CELLWFR | X'0000'–X'FFFF' | Width of character cell, fractional part (optional) | ✅ |
| GOCA-7-708 | 8–9 | UBIN | CELLHFR | X'0000'–X'FFFF' | Height of character cell, fractional part (optional) | ✅ |
| GOCA-7-709 | The Set Character Cell order sets the value of the current character cell-size attribute to the value specified in the order. Depending on the device implementation of the specified precision, this attribute is used to scale characters specified in subsequent Character String drawing orders. Devices that use the font positioning method ignore the character cell. | ✅ |
| GOCA-7-710 | The application of this attribute is dependent on the value of the character precision attribute. See “Character Strings” for details. | ✅ |
| GOCA-7-711 | Four-byte format** | ✅ |
| GOCA-7-712 | - CELLWI specifies the width of the character cell in GPS units. | ✅ |
| GOCA-7-713 | - CELLHI specifies the height of the character cell in GPS units. | ✅ |
| GOCA-7-714 | Eight-byte format** | ✅ |
| GOCA-7-715 | - CELLWI specifies the width of the character cell in GPS units. | ✅ |
| GOCA-7-716 | - CELLWFR specifies the fractional part of the width of the character cell in GPS units. | ✅ |
| GOCA-7-717 | - CELLHI specifies the height of the character cell in GPS units. | ✅ |
| GOCA-7-718 | - CELLHFR specifies the fractional part of the height of the character cell in GPS units. | ✅ |
| GOCA-7-719 | The fractional parts do not exist in the drawing defaults and are set to zero when the drawing default is set into the current attribute. | ✅ |
| GOCA-7-720 | For device implementations that do not ignore the character cell, when the width or height in the current character cell-size attribute is 0, Character String drawing orders will draw nothing. | ✅ |
| GOCA-7-721 | This drawing order does not change the current position. Note that, for precisions 1 and 2 for some implementations, if the character cell size is specified as negative values, a mirror image of the character is generated. That is, if the cell width is negative, the character is mirrored about the $Y_{g}$ axis, and if the cell height is negative, the character is mirrored about the $X_{g}$ axis. Refer to “Character Strings” for a description of how the character cell is used on various AFP devices. | ✅ |
| GOCA-7-722 | The following exception condition raises a drawing process check: | ✅ |
| GOCA-7-723 | EC-0003** The order has an incorrect length. | ✅ |
| GOCA-7-724 | This order sets the value of the current character direction attribute. | ✅ |
| GOCA-7-725 | 0 | CODE | X'3A' | GSCD | Order code | ✅ |
| GOCA-7-726 | 1 | CODE | DIRECTION | X'00'–X'04' | Value for character direction:<br>X'00' Drawing default<br>X'01' Left to right<br>X'02' Top to bottom<br>X'03' Right to left<br>X'04' Bottom to top<br>All other values are reserved | ✅ |
| GOCA-7-727 | The Set Character Direction order sets the value of the current character direction attribute to the value specified in the order. The character direction attribute controls the placement of the first character in the string and each succeeding character relative to the previous character. | ✅ |
| GOCA-7-728 | Value Description** | ✅ |
| GOCA-7-729 | X'00' Drawing Default.** The standard default in AFP environments is left to right (X'01'). | ✅ |
| GOCA-7-730 | X'01' Left to right.** Characters are positioned so that, at a 0° character angle, the character reference point, which is point R in Figure 29, is coincident with the current position. A vector is then drawn from the left edge of the character box to the right edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-7-731 | X'02' Top to bottom.** Characters are positioned so that, at a 0° character angle, the character reference point, which is point T in Figure 29, is coincident with the current position. A vector is then drawn from the top edge of the character box to the bottom edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-7-732 | X'03' Right to left.** Characters are positioned so that, at a 0° character angle, the character reference point, which is point E in Figure 29, is coincident with the current position. A vector is then drawn from the right edge of the character box to the left edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-7-733 | X'04' Bottom to top.** Characters are positioned so that, at a 0° character angle, the character reference point, which is point B in Figure 29, is coincident with the current position. A vector is then drawn from the bottom edge of the character box to the top edge, and successive characters are placed in the direction of this vector. | ✅ |
| GOCA-7-734 | Architecture Note:** This graphics drawing order defines a function that is analogous to part of the text orientation function in presentation text, which defines an inline direction and the development of characters along this direction. | ✅ |
| GOCA-7-735 | The following exception conditions cause a standard action to be taken: | ✅ |
| GOCA-7-736 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'01'—Left to right. | ✅ |
| GOCA-7-737 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'01'—Left to right. | ✅ |
| GOCA-7-738 | This order sets the value of the current character precision attribute. | ✅ |
| GOCA-7-739 | 0 | CODE | X'39' | GSCR | Order code | ✅ |
| GOCA-7-740 | 1 | CODE | PREC | X'00'–X'03' | Value for character precision attribute:<br>X'00' Drawing default<br>X'01' String precision<br>X'02' Character precision<br>X'03' Stroke precision (not supported in AFP GOCA)<br>All other values are reserved | ✅ |
| GOCA-7-741 | The Set Character Precision order sets the value of the current character precision attribute to the value specified in the order. The character precision attribute controls the type of character that is to be used for drawing character strings. Refer to “Character Strings” for a description of how character precision is defined. | ✅ |
| GOCA-7-742 | Value Description** | ✅ |
| GOCA-7-743 | X'00' Drawing Default.** This value sets the current character precision attribute to the value of the drawing default. The standard default in AFP environments is precision X'02'. | ✅ |
| GOCA-7-744 | X'01' Precision 1—Device-Specific (String) Precision.** This precision has been implemented differently on different devices; it is not consistent among implementations. The characters are drawn using the quickest, simplest mode possible for the implementation. In this mode, the only attributes that must be considered when the characters are drawn are the character code point, character set, and character direction attributes. The character angle and character cell-size attributes are not guaranteed to have any effect on the appearance of characters in the string. | ✅ |
| GOCA-7-745 | X'02' Precision 2—Device-Specific (Character) Precision.** This precision has been implemented differently on different devices; it is not consistent among implementations. The character string is drawn taking into account all the attributes to determine the positioning of the characters. The character attributes are not guaranteed to affect the appearance of the characters in the string. | ✅ |
| GOCA-7-746 | X'03' Precision 3—Stroke Precision.** This value is not supported in AFP GOCA. If it is specified, exception EC-000E exists. | ✅ |
| GOCA-7-747 | The following exception conditions cause a standard action to be taken: | ✅ |
| GOCA-7-748 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'02'—Device-Specific (Character) Precision. | ✅ |
| GOCA-7-749 | Standard action: The standard default value of the attribute is used. In AFP environments, this is X'02'—Device-Specific (Character) Precision. | ✅ |
| GOCA-7-750 | This order sets the value of the current character set attribute. | ✅ |
| GOCA-7-751 | 0 | CODE | X'38' | GSCS | Order code | ✅ |
| GOCA-7-752 | 1 | CODE | LCID | X'00'–X'FF' | Local identifier (LCID) for the character set:<br>X'00' Drawing default<br>X'01'–X'FE' Local identifier for the character set<br>X'FF' Special character set | ✅ |
| GOCA-7-753 | The Set Character Set order sets the value of the current character set attribute to the value specified in the order. | ✅ |
| GOCA-7-754 | When the current character set attribute is X'00', it identifies the drawing default, that is, the default from the GDD, or if not specified, the standard default character set. In AFP environments, this is the presentation device default font. | ✅ |
| GOCA-7-755 | When the current character set attribute is X'01' to X'FE', it identifies the character set that is to be used to draw characters in subsequent Character String orders. The controlling environment maps the LCID to a specific font. | ✅ |
| GOCA-7-756 | When the current character set attribute is X'FF', it identifies the special character set, which is implementation-defined. | ✅ |
| GOCA-7-757 | Architecture Note:** In AFP environments, the special character set is the presentation device default font. | ✅ |
| GOCA-7-758 | Character definitions from the character set identified by the current character set attribute are modified under control of the current character precision attribute before being drawn. | ✅ |
| GOCA-7-759 | Architecture Note:** In MO:DCA and IPDS environments, the MO:DCA character rotation (IPDS font inline sequence) associated with the font is ignored when determining character direction and angle. However, when the font positioning method is used, the selected character direction should match the selected character rotation (font inline sequence) so that appropriate font metrics, such as character increment and A-space, are available. If a font with the required character rotation is not available to the presentation device, the spacing and positioning of characters is unpredictable. | ✅ |
| GOCA-7-760 | The following exception condition causes a standard action to be taken: | ✅ |
| GOCA-7-761 | Standard action: The standard default character set is used. In AFP environments, this is the presentation device default font. | ✅ |
| GOCA-7-762 | This order sets the value of the current character shear attribute. This order is processed as a No-Op in AFP GOCA. | ✅ |
| GOCA-7-763 | 0 | CODE | X'35' | GSCH | Order code | ✅ |
| GOCA-7-764 | 1 | UBIN | LENGTH | 4 | Length of following data | ✅ |
| GOCA-7-765 | 2–3 | SBIN | HX | X'8000'–X'7FFF' | Dividend of shear ratio | ✅ |
| GOCA-7-766 | 4–5 | SBIN | HY | X'8000'–X'7FFF' | Divisor of shear ratio | ✅ |
| GOCA-7-767 | Not applicable in AFP GOCA. | ✅ |
| GOCA-7-768 | The following exception condition raises a drawing process check: | ✅ |
| GOCA-7-769 | EC-0003** The order has an incorrect length. | ✅ |
| GOCA-7-770 | Character color | ✅ |
| GOCA-7-771 | Image color | ✅ |
| GOCA-7-772 | Line color | ✅ |
| GOCA-7-773 | Marker color | ✅ |
| GOCA-7-774 | Pattern color | ✅ |
| GOCA-7-775 | Architecture Note:** To fill an area with the color specified by this drawing order, select the drawing default with the Set Pattern Set order, and either the drawing default or solid fill with the Set Pattern Symbol order. | ✅ |
| GOCA-7-776 | 0 | CODE | X'0A' | GSCOL | Order code | ✅ |
| GOCA-7-777 | 1 | CODE | COL | X'00'–X'08' | Value for color attribute:<br>X'00' Drawing default<br>X'01' Blue<br>X'02' Red<br>X'03' Magenta (Pink)<br>X'04' Green<br>X'05' Cyan (Turquoise)<br>X'06' Yellow<br>X'07' Device default<br>X'08' Color of medium<br>All other values are reserved | ✅ |
| GOCA-7-778 | The Set Color order sets the current value of all five color attributes to the value specified in the order. Color attributes control the color of the foreground of the output primitives as they are drawn. | ✅ |
| GOCA-7-779 | The standard default in AFP environments is the presentation device default color. | ✅ |
| GOCA-7-780 | The color value specified by this order is prefixed with X'FF' to generate a two-byte color index value into the standard color table. See “Color”. | ✅ |
| GOCA-7-781 | The following exception conditions cause a standard action to be taken: | ✅ |
| GOCA-7-782 | Standard action: The action is implementation dependent. | ✅ |
| GOCA-7-783 | Standard action: The action is implementation dependent. | ✅ |
| GOCA-7-784 | Architecture Note:** If colors are simulated in AFP environments, color exceptions need not be generated. | ✅ |
| GOCA-7-785 | This order sets the value of the current position in the Graphics Presentation Space (GPS). | ✅ |
| GOCA-7-786 | 0 | CODE | X'21' | GSCP | Order code | ✅ |
| GOCA-7-787 | 1 | UBIN | LENGTH | 4 | Length of following data | ✅ |
| GOCA-7-788 | 2–3 | SBIN | XPOS | X'8000'–X'7FFF' | $X_{g}$ coordinate of point | ✅ |
| GOCA-7-789 | 4–5 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_{g}$ coordinate of point | ✅ |
| GOCA-7-790 | The Set Current Position order sets the value of current position to the value specified in the order. | ✅ |
| GOCA-7-791 | The following exception condition raises a drawing process check: | ✅ |
| GOCA-7-792 | EC-0003** The order has an incorrect length. | ✅ |
| GOCA-7-793 | This order sets the value of the current line type attribute to a custom value. | ✅ |
| GOCA-7-794 | 0 | CODE | X'20' | GSCLT | Order code | ✅ |
| GOCA-7-795 | 1 | UBIN | LENGTH | 0–n | Length of following data; n must be less than 255 and a multiple of 4 | ✅ |
| GOCA-7-796 | 2 | UBIN | DASH0H | X'00'–X'FF' | Integer portion of length of first dash/dot | ✅ |
| GOCA-7-797 | 3 | UBIN | DASH0FR | X'00'–X'FF' | Fractional portion of length of first dash/dot | ✅ |
| GOCA-7-798 | 4 | UBIN | MOVE0H | X'00'–X'FF' | Integer portion of length of first move | ✅ |
| GOCA-7-799 | 5 | UBIN | MOVE0FR | X'00'–X'FF' | Fractional portion of length of first move | ✅ |
| GOCA-7-800 | 6 | UBIN | DASH1H | X'00'–X'FF' | Integer portion of length of second dash/dot | ✅ |
| GOCA-7-801 | 7 | UBIN | DASH1FR | X'00'–X'FF' | Fractional portion of length of second dash/dot | ✅ |
| GOCA-7-802 | 8 | UBIN | MOVE1H | X'00'–X'FF' | Integer portion of length of second move | ✅ |
| GOCA-7-803 | 9 | UBIN | MOVE1FR | X'00'–X'FF' | Fractional portion of length of second move | ✅ |
| GOCA-7-804 | ... | ... | ... | ... | ... | ✅ |
| GOCA-7-805 | n–2 | UBIN | DASHFH | X'00'–X'FF' | Integer portion of length of final dash/dot | ✅ |
| GOCA-7-806 | n–1 | UBIN | DASHFFR | X'00'–X'FF' | Fractional portion of length of final dash/dot | ✅ |
| GOCA-7-807 | n | UBIN | MOVEFH | X'00'–X'FF' | Integer portion of length of final move | ✅ |
| GOCA-7-808 | n+1 | UBIN | MOVEFFR | X'00'–X'FF' | Fractional portion of length of final move | ✅ |
| GOCA-7-809 | The Set Custom Line Type order sets the value of the current line type attribute to the custom value specified in the order. The current line type attribute controls the type of line used to draw line primitives. | ✅ |
| GOCA-7-810 | When setting the line type attribute, this drawing order will set it to a custom value. The Set Line Type drawing order will set the attribute to a standard value. | ✅ |
| GOCA-7-811 | The first byte of the length of a dash/dot or move—this will be referred to as the H byte—specifies the integer portion of the length; the second byte—referred to as the FR byte—specifies the fractional portion. A combined value of X'0000' for a dash/dot length indicates a dash of length 0—that is, a dot. A decimal point is implied between H and FR. The fractional portion of the length is calculated by dividing FR by 256. For example, if FR=X'40', its decimal value is 64, which, divided by 256 results in a fractional component for the length of 1/4. | ✅ |
| GOCA-7-812 | All dash and move lengths are expressed in units of line width. | ✅ |
| GOCA-7-813 | If no dash and move lengths are provided (that is, if the LENGTH field is zero), or if all dash and move lengths are specified as zero, a solid line is drawn. | ✅ |
| GOCA-7-814 | If a dash length is not zero, but on a given device is less than one presentation device pel, the length of the dash is set to one pel. Similarly, if a move length is not zero, but is less than one presentation device pel, the move length is set to one pel. In other words, a nonzero length, no matter how small, must not become a zero length. | ✅ |
| GOCA-7-815 | The standard default for the line type attribute in AFP environments is the standard line type value X'07'—Solid line. | ✅ |
| GOCA-7-816 | See “Line Type” for more information on the line type attribute, including a discussion of how the dash/dot and move lengths are used to generate lines, and a discussion of standard and custom line type values. | ✅ |
| GOCA-7-817 | The following exception condition raises a drawing process check: | ✅ |
| GOCA-7-818 | EC-0003** The order has an incorrect length. | ✅ |
| GOCA-7-819 | Character color | ✅ |
| GOCA-7-820 | Image color | ✅ |
| GOCA-7-821 | Line color | ✅ |
| GOCA-7-822 | Marker color | ✅ |
| GOCA-7-823 | Pattern color | ✅ |
| GOCA-7-824 | Architecture Note: To fill an area with the color specified by this drawing order, select the drawing default with the Set Pattern Set order, and either the drawing default or solid fill with the Set Pattern Symbol order. | ✅ |
| GOCA-7-825 | 0 | CODE | X'26' | GSECOL | Order code | ✅ |
| GOCA-7-826 | 1 | UBIN | LENGTH | 2 | Length of following data | ✅ |
| GOCA-7-827 | 2–3 | CODE | COLOR | See Table 5 | Value for color attribute | ✅ |
| GOCA-7-828 | The Set Extended Color order sets the current value of all five color attributes to the value specified in the order. Color attributes control the color of the foreground bits of the output primitives as they are drawn. | ✅ |
| GOCA-7-829 | The color value specified by this order is used as a two-byte color index value into the standard color table; see "Color" for the meaning of the two-byte values. The color values supported by this order are the same as the values defined in the standard color table, and they are also the same as the values defined in the Standard OCA Color Value Table defined in the MO:DCA controlling environment; see the Mixed Object Document Content Architecture (MO:DCA) Reference. | ✅ |
| GOCA-7-830 | The standard default in AFP environments is the presentation device default color. | ✅ |
| GOCA-7-831 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-832 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-833 | - **Standard action**: The action is implementation dependent. | ✅ |
| GOCA-7-834 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-835 | - **Standard action**: The action is implementation dependent. | ✅ |
| GOCA-7-836 | Architecture Note: If colors are simulated in AFP environments, color exceptions need not be generated. | ✅ |
| GOCA-7-837 | This order sets the value of the current line width attribute. | ✅ |
| GOCA-7-838 | 0 | CODE | X'11' | GSFLW | Order code | ✅ |
| GOCA-7-839 | 1 | UBIN | LENGTH | 2 | Length of following data | ✅ |
| GOCA-7-840 | 2 | UBIN | MH | X'00'–X'FF' | Integral multiplier of normal line width | ✅ |
| GOCA-7-841 | 3 | UBIN | MFR | X'00'–X'FF' | Fractional multiplier of normal line width | ✅ |
| GOCA-7-842 | The Set Fractional Line Width order sets the value of the current line width attribute to the value specified in the order. The current line width attribute controls the width of line used to draw line primitives. | ✅ |
| GOCA-7-843 | MH specifies the integer portion of the normal line width multiplier; MFR specifies the fractional portion of the normal line width multiplier. A combined value of X'0000' specifies the drawing default. A combined value of X'0100' represents a unity multiplier, that is, normal line width. A decimal point is implied between MH and MFR. The fractional portion of the multiplier is calculated by dividing MFR by 256. For example, if MFR=X'40', its decimal value is 64, which, divided by 256 results in a fractional component for the multiplier of 1/4. | ✅ |
| GOCA-7-844 | See "Line Width" for more information on the line width attribute. | ✅ |
| GOCA-7-845 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-846 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-847 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is a multiplier of X'0100', that is, normal line width. | ✅ |
| GOCA-7-848 | This order sets the value of the current line end attribute. | ✅ |
| GOCA-7-849 | 0 | CODE | X'1A' | GSLE | Order code | ✅ |
| GOCA-7-850 | 1 | CODE | LINEEND | X'00'–X'03' | Value for line end attribute:<br>X'00' Drawing default<br>X'01' Flat<br>X'02' Square<br>X'03' Round<br>All other values: Reserved | ✅ |
| GOCA-7-851 | The Set Line End order sets the value of the current line end attribute to the value specified in the order. | ✅ |
| GOCA-7-852 | The current line end attribute applies to those output primitives that are drawn as straight or curved lines and have ends; that is, not complete figures, such as Box and Full Arc. It defines the shape of the start and end of groups of contiguous straight and curved lines. If the line type is not solid, the line end attribute also defines the shape of the internal ends of the dots and dashes, even for complete figures. | ✅ |
| GOCA-7-853 | The standard default in AFP environments is X'03'—Round. | ✅ |
| GOCA-7-854 | See "Line End and Line Join" for details of the line-end shapes and their application. | ✅ |
| GOCA-7-855 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-856 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'03'—Round. | ✅ |
| GOCA-7-857 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-858 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'03'—Round. | ✅ |
| GOCA-7-859 | This order sets the value of the current line join attribute. | ✅ |
| GOCA-7-860 | 0 | CODE | X'1B' | GSLJ | Order code | ✅ |
| GOCA-7-861 | 1 | CODE | LINEJOIN | X'00'–X'03' | Value for line join attribute:<br>X'00' Drawing default<br>X'01' Bevel<br>X'02' Round<br>X'03' Miter<br>All other values: Reserved | ✅ |
| GOCA-7-862 | The Set Line Join order sets the value of the current line join attribute to the value specified in the order. | ✅ |
| GOCA-7-863 | The current line join attribute applies to those output primitives that are drawn as straight or curved lines and have joins; that is, not complete figures, such as Box and Full Arc. The line join attribute defines the shape of the joins between contiguous straight and curved lines. | ✅ |
| GOCA-7-864 | The standard default in AFP environments is X'02'—Round. | ✅ |
| GOCA-7-865 | See "Line End and Line Join" for details of the line-join shapes and their application. | ✅ |
| GOCA-7-866 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-867 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'02'—Round. | ✅ |
| GOCA-7-868 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-869 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'02'—Round. | ✅ |
| GOCA-7-870 | This order sets the value of the current line type attribute to a standard value. | ✅ |
| GOCA-7-871 | 0 | CODE | X'18' | GSLT | Order code | ✅ |
| GOCA-7-872 | 1 | CODE | LINETYPE | X'00'–X'08' | Value for line type attribute:<br>X'00' Drawing default; solid if none specified<br>X'01' Dotted line<br>X'02' Short-dashed line<br>X'03' Dash-dot line<br>X'04' Double-dotted line<br>X'05' Long-dashed line<br>X'06' Dash-double-dot line<br>X'07' Solid line<br>X'08' Invisible line<br>All other values: Reserved | ✅ |
| GOCA-7-873 | The Set Line Type order sets the value of the current line type attribute to the standard value specified in the order. The current line type attribute controls the type of line used to draw line primitives. | ✅ |
| GOCA-7-874 | When setting the line type attribute, this drawing order will set it to a standard value. The Set Custom Line Type drawing order will set the attribute to a custom value. | ✅ |
| GOCA-7-875 | The standard default for the line type attribute in AFP environments is X'07'—Solid line. | ✅ |
| GOCA-7-876 | See "Line Type" for more information on the line type attribute, for guidelines on how the sequence of dashes, dots, and spaces should be generated, and for a discussion of standard and custom line type values. | ✅ |
| GOCA-7-877 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-878 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'07'—Solid line. | ✅ |
| GOCA-7-879 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-880 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'07'—Solid line. | ✅ |
| GOCA-7-881 | This order sets the value of the current line width attribute. | ✅ |
| GOCA-7-882 | 0 | CODE | X'19' | GSLW | Order code | ✅ |
| GOCA-7-883 | 1 | UBIN | MH | X'00'–X'FF' | Value for line width attribute:<br>X'00' Drawing default<br>X'01'–X'FF' Integral multiplier of normal line width | ✅ |
| GOCA-7-884 | The Set Line Width order sets the value of the current line width attribute to the value specified in the order. | ✅ |
| GOCA-7-885 | This order also resets the fractional part of the line width attribute to zero. The current line width attribute controls the width of line used to draw line primitives. | ✅ |
| GOCA-7-886 | MH specifies an integer multiplier of the normal line width. A value of X'01' represents a unity multiplier, that is, normal line width. | ✅ |
| GOCA-7-887 | The standard default in AFP environments is a multiplier of X'01'—normal line width. | ✅ |
| GOCA-7-888 | See "Line Width" for more information on the line width attribute. | ✅ |
| GOCA-7-889 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-890 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is a multiplier of X'01', that is, normal line width. | ✅ |
| GOCA-7-891 | This order sets the value of the current marker cell-size attribute. | ✅ |
| GOCA-7-892 | 0 | CODE | X'37' | GSMC | Order code | ✅ |
| GOCA-7-893 | 1 | UBIN | LENGTH | 4, 6 | Length of following data | ✅ |
| GOCA-7-894 | 2–3 | SBIN | CELLWI | X'8000'–X'7FFF' | Width of marker cell | ✅ |
| GOCA-7-895 | 4–5 | SBIN | CELLHI | X'8000'–X'7FFF' | Height of marker cell | ✅ |
| GOCA-7-896 | 6–7 | BITS | FLAGS | | Internal flags (optional):<br>Bit 0 NOTDEFLT: How to interpret a zero cell-size<br>B'0' Zero means drawing default<br>B'1' Zero means a size of zero<br>Bits 1–15: Reserved; only valid value is B'000...0' | ✅ |
| GOCA-7-897 | The Set Marker Cell order sets the value of the current marker cell-size attribute to the value specified in the order. | ✅ |
| GOCA-7-898 | Implementation Note: In earlier versions of AFP GOCA, the Set Marker Cell order was processed as a No-Op with a LENGTH field with value 4. Thus, some implementations will ignore this drawing order, and some will raise an exception if a LENGTH field with value 6 is encountered. | ✅ |
| GOCA-7-899 | The CELLWI and CELLHI values are in GPS units. | ✅ |
| GOCA-7-900 | If the value of CELLWI is a negative value, this indicates to present the marker as a mirror image in the $x$-direction—that is, about the $Y_g$-axis—of the normal marker symbol. Similarly, a negative CELLHI value indicates to mirror the marker about the $X_g$-axis. Note, however, that all symbols in the default marker set are symmetric in both the $x$ and $y$ directions, so mirror imaging will have no effect on them. | ✅ |
| GOCA-7-901 | - If NOTDEFLT = B'0' (or the FLAGS field is omitted), if either or both of CELLWI or CELLHI are X'0000', the marker cell-size is set to the drawing default value. | ✅ |
| GOCA-7-902 | - If NOTDEFLT = B'1', if either or both of CELLWI or CELLHI are X'0000', the marker cell-size is set to zero. | ✅ |
| GOCA-7-903 | While the marker cell-size attribute is set to zero, markers will be drawn with zero size: that is, the current position will be updated, but no actual markers will be drawn. | ✅ |
| GOCA-7-904 | The standard default marker cell-size in AFP environments is device dependent. However, it is recommended that the standard default marker cell-size be 7/120 of an inch for both width and height (although due to possible scaling, default-sized markers will not necessarily appear at 7/120 of an inch in the usable area). | ✅ |
| GOCA-7-905 | Markers are scaled along with the rest of the GPS if scaling is necessary in the mapping from the GPS window into the usable area (object area). | ✅ |
| GOCA-7-906 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-907 | This order sets the value of the current marker set attribute. | ✅ |
| GOCA-7-908 | 0 | CODE | X'3C' | GSMS | Order code | ✅ |
| GOCA-7-909 | 1 | CODE | LCID | X'00'–X'FF' | Local identifier (LCID) for the marker set:<br>X'00' Default marker set<br>X'01'–X'FE' Local identifier for marker set (not supported in AFP GOCA)<br>X'FF' Default marker set (not supported in AFP GOCA) | ✅ |
| GOCA-7-910 | The Set Marker Set order sets the value of the current marker set attribute to the value specified in the order. | ✅ |
| GOCA-7-911 | When the value of the marker set attribute is X'00', the marker is drawn from the default marker set. See "Markers" for diagrams of the marker symbols in the default marker set. | ✅ |
| GOCA-7-912 | Values X'01' to X'FF' are not supported in AFP GOCA. | ✅ |
| GOCA-7-913 | - **EC-C200**: The marker set identified by the value in the current marker set attribute is not available. | ✅ |
| GOCA-7-914 | - **Standard action**: The standard default marker set is used. In AFP environments, this is the default marker set. | ✅ |
| GOCA-7-915 | This order sets the value of the current marker symbol attribute. | ✅ |
| GOCA-7-916 | 0 | CODE | X'29' | GSMT | Order code | ✅ |
| GOCA-7-917 | 1 | CODE | MCPT | X'00'–X'0A', X'40' | Value of marker symbol code point:<br>X'00' Drawing default; cross if not specified<br>When Marker Set = X'00':<br>X'01' Cross<br>X'02' Plus<br>X'03' Diamond<br>X'04' Square<br>X'05' 6-point star<br>X'06' 8-point star<br>X'07' Filled diamond<br>X'08' Filled square<br>X'09' Dot<br>X'0A' Small circle<br>X'40' Blank<br>All other values: Reserved | ✅ |
| GOCA-7-918 | The Set Marker Symbol order sets the value of the current marker symbol attribute to the value in the order. | ✅ |
| GOCA-7-919 | See "Markers" for diagrams of the marker symbols corresponding to attribute values X'01'–X'0A' in the default marker set. | ✅ |
| GOCA-7-920 | The standard default in AFP environments is X'01'—Cross. | ✅ |
| GOCA-7-921 | - **EC-C201**: The code point identified by the value in the current marker symbol attribute is not defined in the current marker set. | ✅ |
| GOCA-7-922 | - **Standard action**: The standard default marker symbol is used. In AFP environments, this is X'01'—Cross. | ✅ |
| GOCA-7-923 | Character foreground mix | ✅ |
| GOCA-7-924 | Image foreground mix | ✅ |
| GOCA-7-925 | Line foreground mix | ✅ |
| GOCA-7-926 | Marker foreground mix | ✅ |
| GOCA-7-927 | Pattern foreground mix | ✅ |
| GOCA-7-928 | 0 | CODE | X'0C' | GSMX | Order code | ✅ |
| GOCA-7-929 | 1 | CODE | MODE | X'00'–X'05' | Mix-mode value:<br>X'00' Drawing default<br>X'01' Not supported in AFP GOCA<br>X'02' Overpaint<br>X'03'–X'05' Not supported in AFP GOCA<br>All other values: Reserved | ✅ |
| GOCA-7-930 | The Set Mix order sets the current value of all five mix attributes to the value specified in the order. Mix attributes control the way in which the color of the foreground of a primitive is combined with the color of the presentation space. | ✅ |
| GOCA-7-931 | With MODE set to X'02', the foreground pels are opaque and their color replaces the color of underlying pels in the GPS. Since this is the only foreground mix mode supported in AFP GOCA, selecting the drawing default (MODE X'00') will also default to MODE X'02'. | ✅ |
| GOCA-7-932 | For a description of the meaning of the various mix modes, see "Mix". | ✅ |
| GOCA-7-933 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-934 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'02'—Overpaint. | ✅ |
| GOCA-7-935 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-936 | - **Standard action**: The standard default value of the attribute is used. In AFP environments, this is X'02'—Overpaint. | ✅ |
| GOCA-7-937 | This order sets the value of the current pattern reference point attribute. | ✅ |
| GOCA-7-938 | 0 | CODE | X'A0' | GSPRP | Order code | ✅ |
| GOCA-7-939 | 1 | UBIN | LENGTH | 6 | Length of following data | ✅ |
| GOCA-7-940 | 2 | BITS | FLAGS | | Internal flags:<br>Bit 0 DEFAULT: How to interpret XPOS/YPOS<br>B'0' Set to the specified value<br>B'1' Set to the drawing default<br>Bits 1–7: Reserved; only valid value is B'000...0' | ✅ |
| GOCA-7-941 | 3 | RES | | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-942 | 4–5 | SBIN | XPOS | X'8000'–X'7FFF' | $X_g$ coordinate of the pattern reference point | ✅ |
| GOCA-7-943 | 6–7 | SBIN | YPOS | X'8000'–X'7FFF' | $Y_g$ coordinate of the pattern reference point | ✅ |
| GOCA-7-944 | The Set Pattern Reference Point order sets the value of the current pattern reference point attribute to the value specified in the order. | ✅ |
| GOCA-7-945 | The value of the pattern reference point attribute is used as the origin for the placement of custom patterns when filling an area. The pattern reference point is not used when filling an area either with patterns from the default pattern set or with gradients. | ✅ |
| GOCA-7-946 | Note that the pattern reference point does not have to be inside an area being filled. Conceptually, the custom pattern is tiled in all directions from the pattern reference point, all the way to the edges of the GPS. Therefore, the pattern reference point precisely determines the appearance of an area filled with a custom pattern, whether or not the pattern reference point is located on the inside of that area. | ✅ |
| GOCA-7-947 | If DEFAULT is B'1', the pattern reference point is set to the drawing default and the XPOS and YPOS parameters are ignored. | ✅ |
| GOCA-7-948 | The standard default pattern reference point in AFP GOCA is (0,0). | ✅ |
| GOCA-7-949 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-950 | This order sets the value of the current pattern set attribute. | ✅ |
| GOCA-7-951 | 0 | CODE | X'08' | GSPS | Order code | ✅ |
| GOCA-7-952 | 1 | CODE | LCID | X'00'–X'FF' | Local identifier (LCID) for the pattern set:<br>X'00' Default pattern set<br>X'01'–X'FD' Pattern set containing custom patterns and/or gradients<br>X'FE' Local identifier for the pattern set (not supported in AFP GOCA)<br>X'FF' Default pattern set (not supported in AFP GOCA) | ✅ |
| GOCA-7-953 | The Set Pattern Set order sets the value of the current pattern set attribute to the value specified in the order. | ✅ |
| GOCA-7-954 | When the value of the pattern set attribute is X'00', the pattern is drawn from the default pattern set. See "Patterns" for diagrams of the patterns in the default pattern set. | ✅ |
| GOCA-7-955 | When the value of the pattern set attribute is in the range X'01'–X'FD', the pattern is either a custom pattern that has been defined in the current segment using the Begin Custom Pattern drawing order or a gradient that has been defined in the current segment using the Linear Gradient or Radial Gradient drawing orders. | ✅ |
| GOCA-7-956 | The standard default in AFP environments is the default pattern set, X'00'. | ✅ |
| GOCA-7-957 | Values X'FE' and X'FF' are not supported in AFP GOCA. | ✅ |
| GOCA-7-958 | No exceptions are generated until the pattern set is used for area fill. See "Begin Area (GBAR) Order". | ✅ |
| GOCA-7-959 | This order sets the value of the current pattern symbol attribute. | ✅ |
| GOCA-7-960 | 0 | CODE | X'28' | GSPT | Order code | ✅ |
| GOCA-7-961 | 1 | CODE | PATT | | Value of pattern-symbol code point:<br>X'00' Drawing default<br>When Pattern Set = X'00':<br>X'01'–X'08' Dotted patterns of decreasing density<br>X'09' Vertical lines<br>X'0A' Horizontal lines<br>X'0B' Diagonal lines 1 (bottom-left to top-right)<br>X'0C' Diagonal lines 2 (bottom-left to top-right)<br>X'0D' Diagonal lines 1 (top-left to bottom-right)<br>X'0E' Diagonal lines 2 (top-left to bottom-right)<br>X'0F' No fill<br>X'10' Solid fill<br>X'40' Blank (processed the same as X'0F', No fill)<br>All other values: Reserved<br>When Pattern Set = X'01'–X'FD':<br>X'01'–X'FF' Pattern symbol value of a custom pattern or gradient | ✅ |
| GOCA-7-962 | The Set Pattern Symbol order sets the value of the current pattern symbol attribute to the value specified in the order. The value of the pattern symbol attribute determines which particular pattern from the current pattern set is used to fill the interior of subsequent areas. | ✅ |
| GOCA-7-963 | See "Patterns" for diagrams of the patterns corresponding to the attribute values X'01'–X'10' in the default pattern set. | ✅ |
| GOCA-7-964 | The standard default in AFP environments is X'10'. If the default pattern set is selected, this corresponds to the Solid-fill pattern. | ✅ |
| GOCA-7-965 | The pattern symbol value X'00' specifies to use the drawing default pattern symbol, no matter the value of the current pattern set attribute (as long as it is a supported value). For example, if the drawing default pattern, as set by the Set Current Defaults instruction, is pattern set X'03', pattern symbol X'14', that pattern will be used if the current pattern symbol attribute is set to X'00', whether the current pattern set attribute is X'03', X'00', X'11', or any other supported value. | ✅ |
| GOCA-7-966 | No exceptions are generated until the pattern symbol is used for area fill. See "Begin Area (GBAR) Order". | ✅ |
| GOCA-7-967 | Character color | ✅ |
| GOCA-7-968 | Image color | ✅ |
| GOCA-7-969 | Line color | ✅ |
| GOCA-7-970 | Marker color | ✅ |
| GOCA-7-971 | Pattern color | ✅ |
| GOCA-7-972 | Architecture Note: To fill an area with the color specified by this drawing order, select the drawing default with the Set Pattern Set order, and either the drawing default or solid fill with the Set Pattern Symbol order. | ✅ |
| GOCA-7-973 | 0 | CODE | X'B2' | GSPCOL | Order code | ✅ |
| GOCA-7-974 | 1 | UBIN | LENGTH | 12–14 | Length of following data | ✅ |
| GOCA-7-975 | 2 | RES | | X'00' | Reserved; only valid value | ✅ |
| GOCA-7-976 | 3 | CODE | COLSPCE | | Color space:<br>X'01' RGB<br>X'04' CMYK<br>X'06' Highlight color space<br>X'08' CIELAB<br>X'40' Standard OCA color space | ✅ |
| GOCA-7-977 | 4–7 | RES | | X'00000000' | Reserved; only valid value | ✅ |
| GOCA-7-978 | 8 | UBIN | COLSIZE1 | X'01'–X'08', X'10' | Number of bits in component 1; see color space definitions | ✅ |
| GOCA-7-979 | 9 | UBIN | COLSIZE2 | X'00'–X'08' | Number of bits in component 2; see color space definitions | ✅ |
| GOCA-7-980 | 10 | UBIN | COLSIZE3 | X'00'–X'08' | Number of bits in component 3; see color space definitions | ✅ |
| GOCA-7-981 | 11 | UBIN | COLSIZE4 | X'00'–X'08' | Number of bits in component 4; see color space definitions | ✅ |
| GOCA-7-982 | 12–n | COLVALUE | | See Semantics | Color specification | ✅ |
| GOCA-7-983 | COLSPCE is a code that defines the color space and the encoding for the color specification. If the color space is invalid, exception condition EC-0004 exists. The standard action is to use the device default color. If the color space is unsupported, exception condition EC-000E exists. The standard action is to use the device default color. A more specific and preferred exception for an invalid or unsupported color space is EC-0E02. The standard action is to use the device default color. | ✅ |
| GOCA-7-984 | Value Descriptions:** | ✅ |
| GOCA-7-985 | - **X'01' RGB color space**: The color value is specified with three components. Components 1, 2, and 3 are unsigned binary numbers that specify the red, green, and blue intensity values, in that order. COLSIZE1, COLSIZE2, and COLSIZE3 are nonzero and define the number of bits used to specify each component. COLSIZE4 is reserved and should be set to zero. The intensity range for the R, G, B components is 0 to 1, which is mapped to the binary value range 0 to $(2^{\text{COLSIZE}N} - 1)$, where $N=1,2,3$. | ✅ |
| GOCA-7-986 | - **Architecture Note**: The reference white point and the chromaticity coordinates for RGB are defined in SMPTE RP145-1987, entitled Color Monitor Colorimetry, and in RP37-1969, entitled Color Temperature for Color Television Studio Monitors, respectively. The reference white point is commonly known as Illuminant D6500 or simply D65. The R, G, B components are assumed to be gamma-corrected (nonlinear) with a gamma of 2.2. | ✅ |
| GOCA-7-987 | - **X'04' CMYK color space**: The color value is specified with four components. Components 1, 2, 3, and 4 are unsigned binary numbers that specify the cyan, magenta, yellow, and black intensity values, in that order. COLSIZE1, COLSIZE2, COLSIZE3, and COLSIZE4 are nonzero and define the number of bits used to specify each component. The intensity range for the C, M, Y, K components is 0 to 1, which is mapped to the binary value range 0 to $(2^{\text{COLSIZE}N} - 1)$, where $N=1,2,3,4$. This is a device-dependent color space. | ✅ |
| GOCA-7-988 | - **X'06' Highlight color space**: This color space defines a request for the presentation device to generate a highlight color. The color value is specified with one to three components. | ✅ |
| GOCA-7-989 | - **Component 1** is a two-byte unsigned binary number that specifies the highlight color number. The first highlight color is assigned X'0001', the second highlight color is assigned X'0002', and so on. The value X'0000' specifies the presentation device default color. COLSIZE1 = X'10' and defines the number of bits used to specify component 1. | ✅ |
| GOCA-7-990 | - **Component 2** is an optional one-byte unsigned binary number that specifies a percent coverage for the specified color. Percent coverage can be any value from 0% to 100% (X'00'–X'64'). The number of distinct values supported is presentation-device dependent. If the coverage is less than 100%, the remaining coverage is achieved with color of medium. COLSIZE2 = X'00' or X'08' and defines the number of bits used to specify component 2. A value of X'00' indicates that component 2 is not specified in the color value, in which case the architected default for percent coverage is 100%. A value of X'08' indicates that component 2 is specified in the color value. | ✅ |
| GOCA-7-991 | - **Component 3** is an optional one-byte unsigned binary number that specifies a percent shading, which is a percentage of black that is to be added to the specified color. Percent shading can be any value from 0% to 100% (X'00'–X'64'). The number of distinct values supported is presentation-device dependent. If percent coverage and percent shading are specified, the effective range for percent shading is 0% to (100-coverage)%. If the sum of percent coverage plus percent shading is less than 100%, the remaining coverage is achieved with color of medium. COLSIZE3 = X'00' or X'08' and defines the number of bits used to specify component 3. A value of X'00' indicates that component 3 is not specified in the color value, in which case the architected default for percent shading is 0%. A value of X'08' indicates that component 3 is specified in the color value. | ✅ |
| GOCA-7-992 | - **Implementation Note**: The percent shading parameter is currently not supported in AFP environments. If the percent value for component 2 or component 3 is invalid, exception condition EC-0E04 exists. The standard action is to use the maximum valid percent value. COLSIZE4 is reserved and should be set to zero. This is a device-dependent color space. | ✅ |
| GOCA-7-993 | - **Architecture Notes**: | ✅ |
| GOCA-7-994 | 1. The color that is rendered when a highlight color is specified is device dependent. For presentation devices that support colors other than black, highlight color values in the range X'0001' to X'FFFF' may be mapped to any color. For bilevel devices, the color may be simulated with a graphic pattern. | ✅ |
| GOCA-7-995 | 2. If the specified highlight color is "presentation device default", devices whose default color is black use the percent coverage parameter, which is specified in component 2, to render a percent shading. | ✅ |
| GOCA-7-996 | 3. On printing devices, the color of medium is normally white, in which case a coverage of $n$% results in adding $(100-n)$% white to the specified color, or tinting the color with $(100-n)$% white. Display devices may assume the color of medium to always be white and use this algorithm to render the specified coverage. | ✅ |
| GOCA-7-997 | 4. The highlight color space can also specify indexed colors when used in conjunction with a Color Mapping Table (CMT) or an Indexed (IX) Color Management Resource (CMR). In that case, component 1 specifies a two-byte value that is the index into the CMT or the IX CMR, and components 2 and 3 are ignored. Note that when both a CMT and Indexed CMRs are used, the CMT is always accessed first. To preserve compatibility with existing highlight color devices, indexed color values X'0000'–X'00FF' are reserved for existing highlight color applications and devices. That is, indexed color values in the range X'0000'–X'00FF', assuming they are not mapped to a different color space in a CMT, are mapped directly to highlight colors. Indexed color values in the range X'0100'–X'FFFF', assuming they are not mapped to a different color space in a CMT, are used to access Indexed CMRs. For a description of the Color Mapping Table in MO:DCA environments, see the Mixed Object Document Content Architecture (MO:DCA) Reference. | ✅ |
| GOCA-7-998 | - **X'08' CIELAB color space**: The color value is specified with three components. Components 1, 2, and 3 are binary numbers that specify the $L, a, b$ values, in that order, where $L$ is the luminance and $a$ and $b$ are the chrominance differences. Component 1 specifies the $L$ value as an unsigned binary number; components 2 and 3 specify the $a$ and $b$ values as signed binary numbers. COLSIZE1, COLSIZE2, and COLSIZE3 are nonzero and define the number of bits used to specify each component. COLSIZE4 is reserved and should be set to zero. The range for the $L$ component is 0 to 100, which is mapped to the binary value range 0 to $(2^{\text{COLSIZE}1} - 1)$. The range for the $a$ and $b$ components is -127 to +127, which is mapped to the binary range $-(2^{\text{COLSIZE}N - 1} - 1)$ to $+(2^{\text{COLSIZE}N - 1} - 1)$, where $N=2,3$. | ✅ |
| GOCA-7-999 | - For color fidelity, 8-bit encoding should be used for each component, that is, COLSIZE1, COLSIZE2, and COLSIZE3 are set to X'08'. When the recommended 8-bit encoding is used for the $a$ and $b$ components, the range is extended to include -128, which is mapped to the value X'80'. If the encoding is less than 8 bits, treatment of the most negative binary endpoint for the $a$ and $b$ components is device dependent, and tends to be insignificant because of the quantization error. | ✅ |
| GOCA-7-1000 | - **Architecture Note**: The reference white point for CIELAB is known as D50 and is defined in CIE publication 15-2 entitled Colorimetry. | ✅ |
| GOCA-7-1001 | - **X'40' Standard OCA color space**: The color value is specified with one component. Component 1 is an unsigned binary number that specifies a named color using a two-byte value from the Standard OCA Color Value Table. For a complete description of the Standard OCA Color Value Table, see the Mixed Object Document Content Architecture (MO:DCA) Reference. COLSIZE1 = X'10' and defines the number of bits used to specify component 1. COLSIZE2, COLSIZE3, and COLSIZE4 are reserved and should be set to zero. This is a device-dependent color space. See Table 5 for the meaning of the two-byte values. | ✅ |
| GOCA-7-1002 | - **All others**: Reserved | ✅ |
| GOCA-7-1003 | COLSIZE1 defines the number of bits used to specify the first color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. For example, if COLSIZE1 = X'06', the first color component has two padding bits. | ✅ |
| GOCA-7-1004 | COLSIZE2 defines the number of bits used to specify the second color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. | ✅ |
| GOCA-7-1005 | COLSIZE3 defines the number of bits used to specify the third color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. | ✅ |
| GOCA-7-1006 | COLSIZE4 defines the number of bits used to specify the fourth color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. | ✅ |
| GOCA-7-1007 | For COLSIZE1–COLSIZE4, if the specified value is invalid, exception condition EC-0004 exists. The standard action is to use the device default color. If the specified value is unsupported, exception condition EC-000E exists. The standard action is to use the device default color. A more specific and preferred exception for an invalid or unsupported number of bits in a color component is EC-0E05. The standard action is to use the device default color. | ✅ |
| GOCA-7-1008 | COLVALUE specifies the color value in the defined format and encoding. If the color value is invalid, exception condition EC-0004 exists. The standard action is to use the device default color. If the color value is unsupported, exception condition EC-000E exists. The standard action is to use the device default color. A more specific and preferred exception for an invalid or unsupported color value is EC-0E03. The standard action is to use the device default color. Note that the number of bytes specified for this parameter depends on the color space. For example, when there are 8 bits per component, an RGB color value is specified with 3 bytes, while a CMYK color value is specified with 4 bytes. If extra bytes are specified, they are ignored as long as the drawing order length is valid. | ✅ |
| GOCA-7-1009 | - **EC-0003**: The order has an incorrect length. | ✅ |
| GOCA-7-1010 | - **EC-0004**: The attribute value specified in the order is not valid. | ✅ |
| GOCA-7-1011 | - **Standard action**: The device default color is used. | ✅ |
| GOCA-7-1012 | - **EC-000E**: The attribute value specified in the order is not supported. | ✅ |
| GOCA-7-1013 | - **Standard action**: The device default color is used. | ✅ |
| GOCA-7-1014 | - **EC-0E02**: The color space specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-1015 | - **Standard action**: The device default color is used. | ✅ |
| GOCA-7-1016 | - **EC-0E03**: The color value specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-1017 | - **Standard action**: The device default color is used. | ✅ |
| GOCA-7-1018 | - **EC-0E04**: The highlight color percent value specified in the order is invalid. | ✅ |
| GOCA-7-1019 | - **Standard action**: The maximum valid percent value is used. | ✅ |
| GOCA-7-1020 | - **EC-0E05**: The number of bits for a color component specified in the order is invalid or unsupported. | ✅ |
| GOCA-7-1021 | - **Standard action**: The device default color is used. | ✅ |
| GOCA-7-1022 | 1. AFP printers should generate the specific and preferred exceptions defined for this drawing order. For example, if the color value is invalid or unsupported, AFP printers should generate EC-0E03. | ✅ |
| GOCA-7-1023 | 2. If colors are simulated in AFP environments, color exceptions need not be generated. | ✅ |
| GOCA-7-1024 | 3. When a color space other than the standard OCA color space is selected with this drawing order, the concept of mixing color index values in the GPS does not apply. The use of mixing rules other than "Overpaint" or "Leave Alone" is not possible. | ✅ |
| GOCA-7-1025 | 4. For a description of color spaces and their relationships, see R. Hunt, The Reproduction of Colour in Photography, Printing, and Television, Fifth Edition, Fountain Press, 1995. | ✅ |
| GOCA-8-001 | If the SET parameter (byte 2) is invalid or unsupported | ✅ |
| GOCA-8-002 | If the FLAGS parameter (byte 5) bits 1-3 are not B'000', or bits 4-7 are not B'1 1 1 1' | ✅ |
| GOCA-8-003 | If an unallocated item is referenced in the MASK parameter (bytes 3-4) | ✅ |
| GOCA-8-004 | If the FLAGS parameter (byte 5) bit 0 is B'0' and LENGTH is not X'04' | ✅ |
| GOCA-8-005 | If the FLAGS parameter (byte 5) bit 0 is B'1' and the length of the immediate data (byte 6 onward) does not exactly match the length implied by the MASK parameter | ✅ |
| GOCA-8-006 | CPC-70C5 Insufficien t data. The segment data is less than the length specified by SEGL parameter. | ✅ |
| GOCA-8-007 | For each exception condition, the AFP GOCA architecture defines the action that is to be taken when the condition arises. This action is one of the following: | ✅ |
| GOCA-8-008 | - Report a drawing process check (DPC). The identifier of the DPC is the same as that of the exception condition; that is, exception condition EC-xxxx raises DPC-xxxx. | ✅ |
| GOCA-8-009 | - Perform some architecture- or implementation-defined Standard action. For example, for EC-C301 on the | ✅ |
| GOCA-8-010 | The environment—for example, the IPDS environment—optionally can provide an exception handling control that causes the drawing processor to raise a drawing process check for each and every exception condition, rather than execute the standard action, if any , defined for the exception condition. This exception handling control, if provided, can specify what is to happen after the drawing process check has been raised; for example, terminate the draw function or skip to the next drawing order. | ✅ |
| GOCA-8-011 | Those for which no architected standard action is defined | ✅ |
| GOCA-8-012 | Those that have a standard action defined | ✅ |
| GOCA-8-013 | Drawing Order Exceptions | ✅ |
| GOCA-8-014 | The order is a fixed, two-byte format order, and the second byte is not in the segment. | ✅ |
| GOCA-8-015 | The order is a long format order, and the length byte is not in the segment. | ✅ |
| GOCA-8-016 | The order is a long or extended format order, and the number of bytes from the end of the length field to the end of the segment is less than the value of the length count. | ✅ |
| GOCA-8-017 | The order is an extended format order, and the qualifier byte is not in the segment. | ✅ |
| GOCA-8-018 | The order is an extended format order, and one or both of the length bytes are not in the segment. | ✅ |
| GOCA-8-019 | An order that is not valid in the prolog has been detected. | ✅ |
| GOCA-8-020 | The end of the segment has been reached without an End Prolog order. | ✅ |
| GOCA-8-021 | Drawing Order Exceptions | ✅ |
| GOCA-8-022 | Drawing Order Exceptions | ✅ |
| GOCA-8-023 | Drawing Order Exceptions | ✅ |
| GOCA-8-024 | Drawing Order Exceptions | ✅ |
| GOCA-8-025 | Drawing Order Exceptions | ✅ |
| GOCA-8-026 | Drawing Order Exceptions | ✅ |
| GOCA-9-001 | GRS = Graphics Subset | ✅ |
| GOCA-9-002 | x = level. GRS levels start with “2” = level 2. The next level can be called “3” = level 3. The GRS levels are not tied to the GOCA “DR” levels, although the starting level (“2”) is chosen to indicate a relationship to | ✅ |
| GOCA-9-003 | Recognition of commands and modes | ✅ |
| GOCA-9-004 | Interpretation and validation of the commands within the mode | ✅ |
| GOCA-9-005 | Rejection of those commands and modes that are not supported, and return of error data, within the supported subset levels | ✅ |
| GOCA-9-006 | Reporting, on request of the environment, the supported features of the drawing process | ✅ |
| GOCA-9-007 | Reporting error conditions to the environment | ✅ |
| GOCA-9-008 | Begin Segment (chained) in immediate mode | ✅ |
| GOCA-9-009 | Length | ✅ |
| GOCA-9-010 | Name (ignored in AFP GOCA) | ✅ |
| GOCA-9-011 | Prolog | ✅ |
| GOCA-9-012 | New/Append | ✅ |
| GOCA-9-013 | Begin Area (GBAR) order. The required support for INSIDE flag is Alternate Mode. | ✅ |
| GOCA-9-014 | Begin Image (GBIMG, GCBIMG) orders (format X'00' only) | ✅ |
| GOCA-9-015 | Character String (GCHST, GCCHST) orders | ✅ |
| GOCA-9-016 | Comment (GCOMT) order | ✅ |
| GOCA-9-017 | End Area (GEAR) order | ✅ |
| GOCA-9-018 | End Image (GEIMG) order | ✅ |
| GOCA-9-019 | End Prolog (GEPROL) order | ✅ |
| GOCA-9-020 | Fillet (GFLT , GCFLT) orders | ✅ |
| GOCA-9-021 | Full Arc (GFARC, GCFARC) orders | ✅ |
| GOCA-9-022 | Image Data (GIMD) order | ✅ |
| GOCA-9-023 | Line (GLINE, GCLINE) orders | ✅ |
| GOCA-9-024 | Marker (GMRK, GCMRK) orders | ✅ |
| GOCA-9-025 | No-Operation (GNOP1) order | ✅ |
| GOCA-9-026 | Relative Line (GRLINE, GCRLINE) orders | ✅ |
| GOCA-9-027 | Segment Characteristics (GSGCH) order. A check that this order is in the prolog state is optionally performed. | ✅ |
| GOCA-9-028 | Set Arc Parameters (GSAP) order | ✅ |
| GOCA-9-029 | Set Background Mix (GSBMX) order. The required support is X'00' and X'05'—Leave Alone. | ✅ |
| GOCA-9-030 | Set Character Angle (GSCA) order. The required support is 90-degree angles when applied to precision 2 symbols. | ✅ |
| GOCA-9-031 | Set Character Cell (GSCC) order | ✅ |
| GOCA-9-032 | Set Character Direction (GSCD) order | ✅ |
| GOCA-9-033 | Set Character Precision (GSCR) order. The required support is drawing default and precisions 1 and 2. | ✅ |
| GOCA-9-034 | Set Character Set (GSCS) order | ✅ |
| GOCA-9-035 | Set Character Shear (GSCH) order. The required support is drawing default and “no shear”. Other values can be treated as “no shear”, but generators should not produce these values. | ✅ |
| GOCA-9-036 | Set Color (GSCOL) order | ✅ |
| GOCA-9-037 | Set Current Position (GSCP) order | ✅ |
| GOCA-9-038 | Set Extended Color (GSECOL) order | ✅ |
| GOCA-9-039 | Set Line Type (GSLT) order | ✅ |
| GOCA-9-040 | Set Line Width (GSLW) order. The required support is normal line width, plus a further line width selectable by a multiplier of two. | ✅ |
| GOCA-9-041 | Set Marker Cell (GSMC) order. The required support is drawing default. | ✅ |
| GOCA-9-042 | Set Marker Precision (GSMP) order. The required support is drawing default and precisions 1 and 2. | ✅ |
| GOCA-9-043 | Set Marker Set (GSMS) order. The required support is drawing default (default marker set). | ✅ |
| GOCA-9-044 | Set Marker Symbol (GSMT) order | ✅ |
| GOCA-9-045 | Set Mix (GSMX) order. The required support is X'00' and X'02'—Overpaint. | ✅ |
| GOCA-9-046 | Set Pattern Set (GSPS) order. The required support is drawing default (default pattern set). | ✅ |
| GOCA-9-047 | Set Pattern Symbol (GSPT) order | ✅ |
| GOCA-9-048 | 1. Some AFP printers accept the Set Fractional Line Width (GSFLW) order. | ✅ |
| GOCA-9-049 | 2. Some AFP printers accept the following drawing orders and process them as No-Ops: | ✅ |
| GOCA-9-050 | Set Pick Identifier (GSPIK, X'43'). This drawing order is in long format. | ✅ |
| GOCA-9-051 | End Segment drawing order (X'71'). This drawing order is in fixed 2-byte format, where the second byte is reserved and should be set to X'00'. | ✅ |
| GOCA-9-052 | Set Fractional Line Width (GSFLW) order | ✅ |
| GOCA-9-053 | Set Process Color (GSPCOL) order | ✅ |
| GOCA-9-054 | Box (GBOX, GCBOX) orders; required support does not include the ability to draw boxes in a clockwise direction | ✅ |
| GOCA-9-055 | Partial Arc (GP ARC, GCP ARC) orders | ✅ |
| GOCA-9-056 | Image resolution information for GOCA image in MO:DCA and IPDS Graphics Data Descriptor (GDD) | ✅ |
| GOCA-9-057 | New exception EC-C303 for T rueType/OpenType font support | ✅ |
| GOCA-9-058 | Extensions to Set Current Defaults instruction: | ✅ |
| GOCA-9-059 | - Set Process Color (SET = X'10') - Set Normal Line Width (SET = X'11') | ✅ |
| GOCA-9-060 | Support for clockwise arcs in addition to the support for counterclockwise arcs (which was part of DR/2V0), as specified by the determinant of the arc parameters | ✅ |
| GOCA-9-061 | Support for the full range of possible line widths in the Set Line Width (GSLW) order | ✅ |
| GOCA-9-062 | Architecture note: As with DR/2V0, the Set Pick Identifier (X'43') and End Segment (X'71') drawing orders are tolerated, but not defined, in GRS3. As a recommendation, GRS3-compliant receivers should accept these two drawing orders and treat them as No-Ops, GRS3-compliant generators should not generate them, and GRS3 validators must allow them (but may generate a warning to discourage future use). | ✅ |
| GOCA-A-001 | A set of rules that must be followed by all generators when constructing graphics objects | ✅ |
| GOCA-A-002 | A set of graphics processing capabilities that are guaranteed to be supported by all receivers | ✅ |
| GOCA-A-003 | In order to conform to a particular MO:DCA Interchange Set, products that receive graphics objects and convert them using a processor for output to some device, are required to support all the graphics facilities defined in that interchange set. | ✅ |
| GOCA-A-004 | The drawing order subset that needs to be supported by the receiver for proper interpretation of the graphics data | ✅ |
| GOCA-A-005 | The GPS measurement units; note that these are also the DOCS measurement units | ✅ |
| GOCA-A-006 | The size and position of the GPS window that will be mapped to the MO:DCA object area | ✅ |
| GOCA-A-007 | The resolution of raster images in the object | ✅ |
| GOCA-A-008 | The graphics drawing defaults, specified by the Set Current Defaults instruction, that must be set up by the receiver | ✅ |
| GOCA-A-009 | Drawing Attributes | ✅ |
| GOCA-A-010 | Line Attributes | ✅ |
| GOCA-A-011 | Character Attributes | ✅ |
| GOCA-A-012 | Marker Attributes | ✅ |
| GOCA-A-013 | Pattern Attributes | ✅ |
| GOCA-A-014 | Arc Parameters | ✅ |
| GOCA-A-015 | Process Color Attributes | ✅ |
| GOCA-A-016 | Normal Line Width Attribute | ✅ |
| GOCA-A-017 | SF Length X'D3A6BB' Flags Reserved Self-Identifying Parameters | ✅ |
| GOCA-A-018 | Drawing Order Subset level 2, version 0 (DR/2V0). | ✅ |
| GOCA-A-019 | Window Specification (Mandatory) | ✅ |
| GOCA-A-020 | 0 | CODE | X'F6' | Window | Specification | ✅ |
| GOCA-A-021 | 1 | UBIN | LENGTH | 18 | Length of following data | ✅ |
| GOCA-A-022 | 2 BITS FLAGS | ✅ |
| GOCA-A-023 | Bit | 0 | PPS | B'0' | Picture Presentation Space: | ✅ |
| GOCA-A-024 | B'0' 2-D | ✅ |
| GOCA-A-025 | Bit | 1 | ABS | B'1' | Picture Dimensions: | ✅ |
| GOCA-A-026 | B'1' | Absolute; | picture | is | designed for presentation in L-units (see bytes 5-9) | ✅ |
| GOCA-A-027 | Bit | 2 | RES1 | B'0' | Reserved; only valid value | ✅ |
| GOCA-A-028 | Bit | 3 | IMGRES | B'0', | B'1' Image Resolution: | ✅ |
| GOCA-A-029 | B'0' | Resolution | not | defined | or non- | ✅ |
| GOCA-A-030 | symmetric image | ✅ |
| GOCA-A-031 | B'1' | X | and | Y | resolutions are equal and are defined by IMXYRES (see bytes 10- | ✅ |
| GOCA-A-032 | 1 1) | ✅ |
| GOCA-A-033 | Bit | 4 | IMGNS | B'0', | B'1' Non-symmetric image; ignored if bit 3 = B'1' | ✅ |
| GOCA-A-034 | B'0' | Resolution | not | defined | or symmetric image | ✅ |
| GOCA-A-035 | B'1' | Image | resolution | is | 120 × 144 points per inch | ✅ |
| GOCA-A-036 | Bits | 5-7 | RES2 | B'000' | Reserved; only valid value | ✅ |
| GOCA-A-037 | 3 | RES3 | X'00' | Reserved; | only valid value | ✅ |
| GOCA-A-038 | 4 | CODE | CFORMAT | X'00' | Geometric parameter format: | ✅ |
| GOCA-A-039 | X'00' | 16-bit | high-byte-first | signed | integer | ✅ |
| GOCA-A-040 | 5 | CODE | UBASE | X'00' | - X'01' Unit Base for GPS: | ✅ |
| GOCA-A-041 | X'00' | T | en | | inches | ✅ |
| GOCA-A-042 | X'01' | T | en | | centimeters | ✅ |
| GOCA-A-043 | 6-7 | UBIN | XRESOL | X'0001'-X'7FFF' | Number of X g | ✅ |
| GOCA-A-044 | units/UBASE; | must | be | the | same as YRESOL | ✅ |
| GOCA-A-045 | 8-9 | UBIN | YRESOL | X'0001'-X'7FFF' | Number of Y g | ✅ |
| GOCA-A-046 | units/UBASE; | must | be | the | same as XRESOL | ✅ |
| GOCA-A-047 | 10-1 | 1 | UBIN | IMXYRES | X'0000'-X'7FFF' | ✅ |
| GOCA-A-048 | X'0000' Not specified | ✅ |
| GOCA-A-049 | X'0001'-X'7FFF' | Number | of | image | points per | ✅ |
| GOCA-A-050 | UBASE | in | X | and | Y directions | ✅ |
| GOCA-A-051 | 12-13 | SBIN | XLWIND | X'8000'-X'7FFF', | see note | ✅ |
| GOCA-A-052 | X g | ✅ |
| GOCA-A-053 | coordinate | for | left | edge | of GPS window | ✅ |
| GOCA-A-054 | 14-15 | SBIN | XRWIND | X'8000'-X'7FFF', | see note | ✅ |
| GOCA-A-055 | X g | ✅ |
| GOCA-A-056 | coordinate | for | right | edge | of GPS window | ✅ |
| GOCA-A-057 | 16-17 | SBIN | YBWIND | X'8000'-X'7FFF', | see note | ✅ |
| GOCA-A-058 | Y g | ✅ |
| GOCA-A-059 | coordinate | for | bottom | edge | of GPS window | ✅ |
| GOCA-A-060 | 18-19 | SBIN | YTWIND | X'8000'-X'7FFF', | see note | ✅ |
| GOCA-A-061 | Y g | ✅ |
| GOCA-A-062 | coordinate | for | top | edge | of GPS window | ✅ |
| GOCA-A-063 | Note: | The | complete | range | is valid, and assumes a measurement unit of 1/1440 inch. That is, the measurement base is ten inches, and the X | ✅ |
| GOCA-A-065 | , Y g | ✅ |
| GOCA-A-066 | units | per | unit | base | are 14,400. | ✅ |
| GOCA-A-067 | 1. Calculate the number of actual supported units per inch X as follows: | ✅ |
| GOCA-A-068 | If the measurement base is ten inches, divide the number of supported units per ten inches by 10. | ✅ |
| GOCA-A-069 | If the measurement base is ten centimeters, multiply the number of supported units per ten centimeters by 0.254. | ✅ |
| GOCA-A-070 | 2. Calculate the ratio of actual supported units per inch X to the assumed 1440 units per inch. T o do this, divide X by 1440, yielding the ratio Y . | ✅ |
| GOCA-A-071 | 3. Calculate the new range value in the supported measurement units as follows: | ✅ |
| GOCA-A-072 | 1. Supported units per inch: | ✅ |
| GOCA-A-073 | 2400 ÷ 10 = 240 2. Ratio of supported units per inch to 1440 units per inch: 240 ÷ 1440 = 1/6 3. Range at 2400 units per 10 inches: | ✅ |
| GOCA-A-074 | 1. The obsolete IBM AFP Data Stream Reference, S544-3202, allowed 4 additional reserved bytes following the YTWIND parameter . These bytes are supported by AFP GOCA receivers for migration, but new AFP | ✅ |
| GOCA-A-075 | 2. The image resolution value specified by the IMGRES, IMGNS, and IMXYRES parameters allows a presentation device to maintain the size of GOCA images when scaling or resolution-correcting the GOCA object. In the absence of this information and any other externally-provided information on the resolution of a GOCA image, the image is mapped point-to-pel in the presentation device. In that case, the resulting image size varies with the resolution of the device. 3. The IPDS environment defines additional exceptions for invalid parameters in the IPDS version of the | ✅ |
| GOCA-A-076 | GDD. For example, an exception is defined for the case where the GPS Window coordinates are inconsistent. | ✅ |
| GOCA-A-077 | Set Current Defaults—Drawing Attributes | ✅ |
| GOCA-A-078 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-079 | 1 | UBIN | LENGTH | 4, | 8 Length of following data | ✅ |
| GOCA-A-080 | 2 | CODE | SET | X'00' | Drawing Attributes | ✅ |
| GOCA-A-081 | 3-4 | BITS | MASK | X'B000' | Set Mask | ✅ |
| GOCA-A-082 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-083 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-084 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-085 | 6-7 | CODE | COLOR | See | Table 5 Color | ✅ |
| GOCA-A-086 | 8 | CODE | FORMIX | X'00', | X'02' Foreground mix | ✅ |
| GOCA-A-087 | 9 | CODE | BACKMIX | X'00', | X'05' Background mix | ✅ |
| GOCA-A-088 | Set | Current | Defaults—Line | | Attributes | ✅ |
| GOCA-A-089 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-A-090 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-091 | 1 | UBIN | LENGTH | 4, | 8 Length of following data | ✅ |
| GOCA-A-092 | 2 | CODE | SET | X'01' | Line Attributes | ✅ |
| GOCA-A-093 | 3-4 | BITS | MASK | X'F000' | Set Mask | ✅ |
| GOCA-A-094 | 5 | CODE | FLAG | X'0F', | X'8F' X'0F' Use standard default | ✅ |
| GOCA-A-095 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-096 | 6 | CODE | LINETYPE | X'00'-X'08' | Line type | ✅ |
| GOCA-A-097 | 7 | UBIN | LINEWID | X'00'-X'FF' | Line width | ✅ |
| GOCA-A-098 | 8 | CODE | LINEEND | X'00'-X'03' | Line end | ✅ |
| GOCA-A-099 | 9 | CODE | LINEJOIN | X'00'-X'03' | Line join | ✅ |
| GOCA-A-100 | Set Current Defaults—Character Attributes | ✅ |
| GOCA-A-101 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-102 | 1 | UBIN | LENGTH | 4, | 19 Length of following data | ✅ |
| GOCA-A-103 | 2 | CODE | SET | X'02' | Character Attributes | ✅ |
| GOCA-A-104 | 3-4 | BITS | MASK | X'FC00' | Set Mask | ✅ |
| GOCA-A-105 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-106 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-107 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-108 | 6-9 | SBIN | ANGLE | X'8000'-X'7FFF' | Character Angle X,Y | ✅ |
| GOCA-A-109 | 10-13 | SBIN | CELLSIZE | X'8000'-X'7FFF' | Character cell-size CW ,CH | ✅ |
| GOCA-A-110 | 14 | CODE | DIRN | X'00'-X'04' | Character direction | ✅ |
| GOCA-A-111 | 15 | CODE | PREC | X'00'-X'02' | Character precision | ✅ |
| GOCA-A-112 | 16 | CODE | SET | X'00'-X'FF' | Character set | ✅ |
| GOCA-A-113 | 17-20 | SBIN | SHEAR | X'8000'-X'7FFF' | Character shear X,Y | ✅ |
| GOCA-A-114 | Set | Current | Defaults—Marker | | Attributes | ✅ |
| GOCA-A-115 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-A-116 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-117 | 1 | UBIN | LENGTH | 4, | 1 1 Length of following data | ✅ |
| GOCA-A-118 | 2 | CODE | SET | X'03' | Marker Attributes | ✅ |
| GOCA-A-119 | 3-4 | BITS | MASK | X'5900' | Set Mask | ✅ |
| GOCA-A-120 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-121 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-122 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-123 | 6-9 | SBIN | CELLSIZE | X'8000'-X'7FFF' | Marker cell-size width, height | ✅ |
| GOCA-A-124 | 10 | CODE | PREC | X'00'-X'02' | Marker precision (obsolete, see Appendix | ✅ |
| GOCA-A-125 | C, | “AFP | GOCA | Migration | Functions” | ✅ |
| GOCA-A-126 | 1 | 1 | CODE | SET | X'00' Marker set | ✅ |
| GOCA-A-127 | 12 | CODE | SYMBOL | X'00'-X'0A', | X'40' Marker symbol | ✅ |
| GOCA-A-128 | Set Current Defaults—Pattern Attributes | ✅ |
| GOCA-A-129 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-130 | 1 | UBIN | LENGTH | 4, | 10 Length of following data | ✅ |
| GOCA-A-131 | 2 | CODE | SET | X'04' | Pattern Attributes | ✅ |
| GOCA-A-132 | 3-4 | BITS | MASK | X'0910' | Set Mask | ✅ |
| GOCA-A-133 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-134 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-135 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-136 | 6 | CODE | SET | X'00' | -X'FD' Pattern set | ✅ |
| GOCA-A-137 | 7 | CODE | SYMBOL | X'00'- | X'FF' Pattern symbol | ✅ |
| GOCA-A-138 | 8-9 | SBIN | XPOS | X'8000'-X'7FFF' | X g | ✅ |
| GOCA-A-139 | coordinate | of | the | pattern | reference point | ✅ |
| GOCA-A-140 | 10-1 | 1 | SBIN | YPOS | X'8000'-X'7FFF' Y g | ✅ |
| GOCA-A-141 | coordinate | of | the | pattern | reference point | ✅ |
| GOCA-A-142 | Set | Current | Defaults—Arc | | Parameters | ✅ |
| GOCA-A-143 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-A-144 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-145 | 1 | UBIN | LENGTH | 4, | 12 Length of following data | ✅ |
| GOCA-A-146 | 2 | CODE | SET | X'0B' | Arc Parameters | ✅ |
| GOCA-A-147 | 3-4 | BITS | MASK | X'F000' | Set Mask | ✅ |
| GOCA-A-148 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-149 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-150 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-151 | 6-7 | SBIN | P | X'8000'-X'7FFF' | P parameter of arc transform | ✅ |
| GOCA-A-152 | 8-9 | SBIN | Q | X'8000'-X'7FFF' | Q parameter of arc transform | ✅ |
| GOCA-A-153 | 10-1 | 1 | SBIN | R | X'8000'-X'7FFF' R parameter of arc transform | ✅ |
| GOCA-A-154 | 12-13 | SBIN | S | X'8000'-X'7FFF' | S parameter of arc transform | ✅ |
| GOCA-A-155 | Set Current Defaults—Process Color Attributes | ✅ |
| GOCA-A-156 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-157 | 1 | UBIN | LENGTH | 4, | 18-20 Length of following data | ✅ |
| GOCA-A-158 | 2 | CODE | SET | X'10' | Process Color Attributes | ✅ |
| GOCA-A-159 | 3-4 | BITS | MASK | X'E000' | Set Mask | ✅ |
| GOCA-A-160 | 5 | CODE | FLAG | X'0F', | X'8F' | ✅ |
| GOCA-A-161 | X'0F' | Use | standard | | default | ✅ |
| GOCA-A-162 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-163 | 6 | CODE | FORMIX | X'00', | X'02' Foreground mix | ✅ |
| GOCA-A-164 | 7 | CODE | BACKMIX | X'00', | X'05' Background mix | ✅ |
| GOCA-A-165 | 8-19, 8-20, | ✅ |
| GOCA-A-166 | 8-21 | ✅ |
| GOCA-A-167 | CODE | PROCOLOR | Process | Color | value; syntax defined by Set | ✅ |
| GOCA-A-168 | Process | Color | drawing | order | starting with byte 2 (reserved) | ✅ |
| GOCA-A-169 | Set | Current | Defaults—Normal | Line | Width Attribute | ✅ |
| GOCA-A-170 | Offset | Type | Name | Range | Meaning | ✅ |
| GOCA-A-171 | 0 | CODE | X'21' | Set | Current Defaults instruction | ✅ |
| GOCA-A-172 | 1 | UBIN | LENGTH | 4, | 6 Length of following data | ✅ |
| GOCA-A-173 | 2 | CODE | SET | X'1 | 1' Normal Line Width Attribute | ✅ |
| GOCA-A-174 | 3-4 | BITS | MASK | X'8000' | Set Mask | ✅ |
| GOCA-A-175 | 5 | CODE | FLAG | X'0F', | X'8F' X'0F' Use standard default | ✅ |
| GOCA-A-176 | X'8F' | Use | values | in | bytes 6-n | ✅ |
| GOCA-A-177 | 6-7 | UBIN | NORML | W | X'0000'-X'FFFF' Normal line width in 1440ths of an inch | ✅ |
| GOCA-B-001 | Write Graphics Control (X'D684') | ✅ |
| GOCA-B-002 | Write Graphics (X'D685') | ✅ |
| GOCA-B-003 | Page state | ✅ |
| GOCA-B-004 | Overlay state | ✅ |
| GOCA-B-005 | Page Segment state | ✅ |
| GOCA-B-006 | Positive acknowledgement of graphics commands in Overlay state or Page Segment state means that general syntax and validity checks have been made and that the command, or command sequence, has been accepted for processing. Additional exceptions that are detected when the data is included on the page are reported at that time, assuming that exception reporting is enabled. | ✅ |
| GOCA-B-007 | Graphics Area Position (GAP) | ✅ |
| GOCA-B-008 | Graphics Output Control (GOC) | ✅ |
| GOCA-B-009 | Graphics Data Descriptor (GDD) | ✅ |
| GOCA-B-010 | It is an exception if there is an attempt to present data outside the boundary of the logical page. | ✅ |
| GOCA-B-011 | Chained | ✅ |
| GOCA-B-012 | Unchained | ✅ |
| GOCA-B-013 | This command is sent to the printer to map Local Identifiers referenced in graphics to a specified font in the printer . | ✅ |
| GOCA-B-014 | Activate Resource | ✅ |
| GOCA-B-015 | Deactivate Font | ✅ |
| GOCA-B-016 | Load Code Page | ✅ |
| GOCA-B-017 | Load Code Page Control | ✅ |
| GOCA-B-018 | Load Font | ✅ |
| GOCA-B-019 | Load Font Character Set Control | ✅ |
| GOCA-B-020 | Load Font Control | ✅ |
| GOCA-B-021 | Load Font Index | ✅ |
| GOCA-B-022 | Load Symbol Set | ✅ |
| GOCA-B-023 | IPDS environment; instead, the IPDS Alternate Exception Action or Page Continuation Action is performed, when appropriate. | ✅ |
| GOCA-C-001 | Describes migration functions that may occur in an AFP GOCA object. | ✅ |
| GOCA-C-002 | 1. T o allow existing applications to run unchanged. | ✅ |
| GOCA-C-003 | 2. T o provide a clear growth direction for future applications. | ✅ |
| GOCA-C-004 | 1. Obsolete functions. These are attributes, drawing orders, and parameters that will be accepted but ignored. New products must not generate these functions. | ✅ |
| GOCA-C-005 | 2. Obsolete exception conditions. These are exception conditions that will be accepted but ignored. New products must not generate these functions. | ✅ |
| GOCA-C-006 | 3. Retired functions. These are drawing orders and parameters whose use has been retired except for specific products. These specific products may use these functions. All other products should not use these functions; that is, generators should not generate these functions and receivers may ignore them. 4. Retired exception conditions. These are exception conditions whose use has been retired except for specific products. These specific products may report these exception conditions. All other products should not report these exception conditions. | ✅ |
| GOCA-C-007 | Precision 2 Character Precision. In AFP GOCA, this is the same as Precision 1—String Precision. | ✅ |
| GOCA-C-008 | 0 | CODE | X'3B' | GSMP | order code | ✅ |
| GOCA-C-009 | 1 | CODE | PREC | X'00'-X'03' | Value for marker precision attribute: | ✅ |
| GOCA-C-010 | X'00' Drawing default | ✅ |
| GOCA-C-011 | X'01' String precision | ✅ |
| GOCA-C-012 | X'02' Character precision | ✅ |
| GOCA-C-013 | X'03' | Stroke | precision | (not | supported in AFP GOCA) | ✅ |
| GOCA-C-014 | All | other | values | | Reserved | ✅ |
| GOCA-C-015 | Semantics | ✅ |
| GOCA-C-016 | The | Set | Marker | Precision | order sets the value of the current marker precision attribute to the value specified in the order . | ✅ |
| GOCA-C-017 | Value Description | ✅ |
| GOCA-C-018 | X'00' | Drawing | Default. | This | value sets the current marker precision attribute to the value of the drawing default. | ✅ |
| GOCA-C-019 | The | standard | default | in | AFP environments is X'02'—Character precision. | ✅ |
| GOCA-C-020 | X'01' | Precision | 1—String | Precision. | The markers are drawn using the quickest, simplest mode possible in the device. In this mode, the only attributes that must, as a minimum, be | ✅ |
| GOCA-C-021 | considered | when | the | markers | are drawn are the marker symbol and the marker set. The positioning of the marker can be approximate. | ✅ |
| GOCA-C-022 | X'02' | Precision | 2—Character | Precision. | In AFP GOCA, this is treated the same as precision 1. | ✅ |
| GOCA-C-023 | X'03' | Precision | 3—Stroke | Precision. | This value is not supported in AFP GOCA. | ✅ |
| GOCA-C-024 | The | following | exception | conditions | cause a standard action to be taken: | ✅ |
| GOCA-C-025 | EC-0004 | The | attribute | value | specified in the order is not valid. | ✅ |
| GOCA-C-026 | Standard | action: | The | standard | default value of the attribute is used. In AFP environments, this is X'02'—Character precision. | ✅ |
| GOCA-C-027 | EC-000E | The | attribute | value | specified in the order is not supported. | ✅ |
| GOCA-C-028 | Standard | action: | The | standard | default value of the attribute is used. In AFP environments, this is X'02'—Character precision. | ✅ |
| GOCA-C-029 | AFP | GOCA | Migration | | Functions | ✅ |
| GOCA-C-030 | The use of this parameter in the GDD is restricted to pre-2012 applications that generate or receive DR/2V0 (GRS2) AFP GOCA objects. | ✅ |
| GOCA-C-031 | 0 | CODE | X'F7' | Drawing | Order Subset | ✅ |
| GOCA-C-032 | 1 | UBIN | LENGTH | 7 | Length of following data | ✅ |
| GOCA-C-033 | 2 | CODE | X'B0' | Drawing | order subset | ✅ |
| GOCA-C-034 | 3-4 | RES | X'0000' | Reserved; | only valid value | ✅ |
| GOCA-C-035 | 5 | CODE | SUBLEV | X'02' | Drawing order subset level 2.0 | ✅ |
| GOCA-C-036 | 6 | CODE | VERSION | X'00' | V ersion 0 | ✅ |
| GOCA-C-037 | 7 | UBIN | LENGTH | X'01' | Length of following field | ✅ |
| GOCA-C-038 | 8 | CODE | GEOM | X'00' | Coordinate formats in data: | ✅ |
| GOCA-C-039 | X'00' | 16-bit | high-byte-first | signed | integer | ✅ |
| GOCA-C-040 | If | invalid | bits | are | specified in this self-identifying parameter , EC-000A may optionally be detected. | ✅ |
| GOCA-C-041 | Retired Exception Conditions | ✅ |
| GOCA-C-042 | EC-0002 | A | reserved | byte | or bit in the order is not set to zero. This is an optional exception. | ✅ |
| GOCA-C-043 | The | use | of | this | exception condition is restricted to pre-May-2012 AFP GOCA receivers. New | ✅ |
| GOCA-C-044 | AFP | GOCA | receivers | should | not report this exception condition. | ✅ |
| GOCA-C-045 | Note: | Some | pre-May-2012 | receivers | might report this exception condition when new functions are encountered that use previously reserved bits. | ✅ |
| GOCA-C-046 | AFP | GOCA | Migration | | Functions | ✅ |
| GOCA-D-001 | AFP GOCA commands sorted by identifier | ✅ |
| GOCA-D-002 | AFP GOCA commands sorted by acronym | ✅ |
| GOCA-D-003 | AFP GOCA control instructions sorted by identifier | ✅ |
| GOCA-D-004 | AFP GOCA control instructions sorted by acronym | ✅ |
| GOCA-D-005 | AFP GOCA drawing orders sorted by identifier | ✅ |
| GOCA-D-006 | AFP GOCA drawing orders sorted by acronym | ✅ |
| GOCA-D-007 | SCD X'21' Set Current Defaults 66 | ✅ |
| GOCA-D-008 | X'80' Box at Current Position GCBOX90 | ✅ |
| GOCA-D-009 | 1. The Set Pick Identifier (X'43') long-format drawing order is not formally part of AFP GOCA, but is accepted by some | ✅ |
| GOCA-D-010 | 2. The End Segment (X'71') fixed two-byte drawing order is not formally part of AFP GOCA, but is accepted by some | ✅ |
| GOCA-D-011 | AFP printers and treated as a No-Op. | ✅ |
| GOCA-D-012 | GSBMX X'0D' Set Background Mix 132 | ✅ |
| GOCA-D-013 | 1. The Set Pick Identifier (X'43') long-format drawing order is not formally part of AFP GOCA, but is accepted by some | ✅ |
| GOCA-D-014 | 2. The End Segment (X'71') fixed two-byte drawing order is not formally part of AFP GOCA, but is accepted by some | ✅ |
| GOCA-D-015 | AFP printers and treated as a No-Op. | ✅ |
| GOCA-D-016 | This information contains examples of data and reports used in daily business operations. T o illustrate them in a complete manner , some examples include the names of individuals, companies, brands, or products. These names are fictitious and any similarity to the names and addresses used by an actual business enterprise is entirely coincidental. | ✅ |
| GOCA-D-017 | Other company , product, or service names may be trademarks or service marks of others. | ✅ |
| GOCA-D-018 | Mixed Object Document Content Architecture (MO:DCA); | ✅ |
| GOCA-D-019 | Intelligent Printer Data Stream (IPDS) | ✅ |
| GOCA-D-020 | AFP Line Data Architecture | ✅ |
| GOCA-D-021 | Bar Code Object Content Architecture (BCOCA) | ✅ |
| GOCA-D-022 | Color Management Object Content Architecture (CMOCA) | ✅ |
| GOCA-D-023 | Font Object Content Architecture (FOCA) | ✅ |
| GOCA-D-024 | Graphics Object Content Architecture for AFP (AFP | ✅ |
| GOCA-D-025 | Image Object Content Architecture (IOCA) | ✅ |
| GOCA-D-026 | Metadata Object Content Architecture (MOCA) | ✅ |
| GOCA-D-027 | Presentation T ext Object Content Architecture (PTOCA) | ✅ |
| GOCA-D-028 | American National Standards Institute (ANSI). An organization consisting of producers, consumers, and general interest groups. ANSI establishes the procedures by which accredited organizations create and maintain | ✅ |
| GOCA-D-029 | anamorphic scaling • character | ✅ |
| GOCA-D-030 | CIELAB color space. Internationally accepted color model used as a standard to define color within the graphic arts industry , as well as other industries. L*, a*, and b* are plotted at right angles to one another . Equal distances in the space represent approximately equal color difference. clipping. Eliminating those parts of a picture that are outside of a clipping boundary such as a viewing window or presentation space. Synonymous with trimming. character angle • clipping | ✅ |
| GOCA-D-031 | CMOCA • color of medium | ✅ |
| GOCA-D-032 | default. A value, attribute, or option that is assumed when none has been specified and one is needed to continue processing. See also default drawing attributes and default drawing controls. default drawing attributes. Synonymous with drawing defaults. default drawing controls. The set of drawing controls adopted at the start of a drawing process and usually at the start of each root segment that is processed. Contrast with current drawing controls. default indicator . A field whose bits are all B'1' indicating that a hierarchical default value is to be used. The value can be specified by an external parameter . See also external parameter . default pattern set. A set of predefined patterns, like solid, dots, or horizontal lines. Contrast with custom pattern. color space • default pattern set | ✅ |
| GOCA-D-033 | The number of bits, the number of bytes, the allowable ranges of bytes, the maximum number of characters, and the meanings assigned to some generic and specific bit patterns, are some examples of specifications to be found in such a definition. deprecated • encoding scheme | ✅ |
| GOCA-D-034 | Encoding Scheme Identifier (ESID) • font width (FW) | ✅ |
| GOCA-D-035 | For fixed-pitch, uniform character increment fonts: the fixed character increment, which is also the space character increment | ✅ |
| GOCA-D-036 | For PSM fonts: the width of the space character | ✅ |
| GOCA-D-037 | For typographic, proportionally-spaced fonts: one-third of the vertical font size, which is also the default size of the space character . | ✅ |
| GOCA-D-038 | Coded Character Set Identifier (CCSID) | ✅ |
| GOCA-D-039 | Coded Graphic Character Set Global Identifier (CGCSGID) | ✅ |
| GOCA-D-040 | Code Page Global ID (CPGID) | ✅ |
| GOCA-D-041 | Font Typeface Global Identifier (FGID) | ✅ |
| GOCA-D-042 | Global Resource Identifier (GRID) | ✅ |
| GOCA-D-043 | Graphic Character Global Identifier (GCGID) | ✅ |
| GOCA-D-044 | Graphic Character Set Global Identifier (GCSGID) | ✅ |
| GOCA-D-045 | Graphic Character UCS Identifier (GCUID) | ✅ |
| GOCA-D-046 | An identifier used by a data object to reference a resource | ✅ |
| GOCA-D-047 | In the MO:DCA environment, an encoded graphic character string that provides a reference name for a document element. | ✅ |
| GOCA-D-048 | GPS • horizontal font size | ✅ |
| GOCA-D-049 | For fixed-pitch, uniform character increment fonts: the fixed character increment, which is also the space character increment | ✅ |
| GOCA-D-050 | For PSM fonts: the width of the space character | ✅ |
| GOCA-D-051 | For typographic fonts and proportionally-spaced fonts: | ✅ |
| GOCA-D-052 | kerning. The design of graphic characters so that their character boxes overlap, resulting in the reduction of space horizontal scale factor • kerning | ✅ |
| GOCA-D-053 | 1 logical unit = 1/1440 inch (unit base = 10 inches, units per unit base = 14,400) | ✅ |
| GOCA-D-054 | 1 logical unit = 1/240 inch (unit base = 10 inches, units per unit base = 2400) | ✅ |
| GOCA-D-055 | M mandatory support level. Within the base-and-towers concept, the smallest portion of architected function that is allowed to be implemented. This is represented by a base with no towers. Synonymous with base support level. marker . A symbol with a recognizable appearance that is used to identify a particular location. An example of a marker is a symbol that is positioned by the center point of its cell. marker attributes. The characteristics that control the appearance of a marker . Examples of marker attributes are cell-size and color . marker cell. A conceptual rectangular box that can include a marker symbol and the space surrounding that symbol. marker precision. A method used to specify the degree of influence that marker attributes have on the appearance of a marker; this method has been made obsolete . marker set. In GOCA, a set of graphic symbols used to indicate a position. marker symbol. A symbol that is used for a marker . keyword • marker symbol | ✅ |
| GOCA-D-056 | O object. (1) A collection of structured fields. The first structured field provides a begin-object function, and the last structured field provides an end-object function. The object can contain one or more other structured fields whose content consists of one or more data elements of a particular data type. An object can be assigned a name that can be used to reference the object. Examples of objects are presentation text, font, graphics, and image objects. (2) Something that a user works with to perform a task. object area. A rectangular area in a presentation space into which a data object is mapped. The presentation space can be for a page or an overlay . Examples are a graphics object area, an image object area, and a bar code object area. object data. A collection of related data elements bundled together . Examples of object data include graphic characters, image data elements, and drawing orders. obsolete. Removed from the architecture, and thus ignored by receivers. offline. A device state in which the device is not under the direct control of a host. Contrast with online. offset. A table heading for architecture syntax. The entries under this heading indicate the numeric displacement into a construct. The offset is measured in meaning • offset | ✅ |
| GOCA-D-057 | physical printable area. A bounded area defined on a side of a sheet within which printing can take place. The physical printable area is an attribute of sheet size and printer capabilities, and cannot be altered by the host. The physical printable area is mapped to the medium presentation space, and is used in user printable area and valid printable area calculations. Contrast with user printable area and valid printable area. picture element. Synonymous with pel. pixel. Synonymous with pel. point. (1) A unit of measure used mainly for measuring typographical material. There are seventy-two points to an inch. (2) In GOCA, a parameter that specifies the position online • point | ✅ |
| GOCA-D-058 | Em square, the same metrics can be used for different point sizes and different raster pattern resolutions. Relative metrics require defining the unit of measure for the Em square, the point size of the font, and, if applicable, the resolution of the raster pattern. relative positioning. The establishment of a position within a coordinate system as an offset from the current position. Contrast with absolute positioning. repeating group. A group of parameter specifications that can be repeated. reserved. Having no assigned meaning and put aside for future use. The content of reserved fields is not used by receivers, and should be set by generators to a specified polyline • reserved | ✅ |
| GOCA-D-059 | Fields or values that have been used by a product in a manner not compliant with the architected definition | ✅ |
| GOCA-D-060 | Fields or values that have been removed from an architecture | ✅ |
| GOCA-D-061 | segment properties. The segment characteristics used by a drawing process. Examples of segment properties are segment name and segment length. reset color • segment properties | ✅ |
| GOCA-D-062 | trimming. Eliminating those parts of a picture that are outside of a clipping boundary such as a viewing window or presentation space. Synonymous with clipping. triplet. A three-part self-defining variable-length parameter consisting of a length byte, an identifier byte, and parameter-value bytes. semantics • triplet | ✅ |
| GOCA-D-063 | UTF-32LE UTF-32 serialized as bytes in least- significant-byte-first order (little endian). uniformly spaced font. A font with graphic characters having a uniform character increment. The distance between reference points of adjacent graphic characters is constant in the escapement direction. The blank space between the graphic characters can vary . Synonymous with monospaced font. Contrast with proportionally spaced font and typographic font. triplet identifier • uniformly spaced font | ✅ |
| GOCA-D-064 | X pg ,Y pg coordinate system. The coordinate system of a page presentation space. This coordinate system describes the size, position, and orientation of a page presentation space. Orientation of an X pg ,Y pg coordinate system is relative to an environment-specified coordinate system, for example, an X m ,Y m coordinate system. unit base • X pg ,Y pg coordinate system | ✅ |
| GOCA-D-065 | Set Process Color (GSPCOL) order . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 161 drawing process check . . . . . . . . . . . . . . . . . . 168 drawing process controls . . . . . . . . . . . . . . . . .70 current position . . . . . . . . . . . . . . . . . . . . . . . . . .19 drawing processing environment . . . . . . . . .61 drawing processor . . . . . . . . . . . . . . . . . . . . . . . . . 1 1 drawing processor facilities drawing process controls . . . . . . . . . . . . . . .70 | ✅ |
| GOCA-D-066 | GPS window . . . . . . . . . . . . . . . . . . . . . . . . . . . 190 graphics object areas . . . . . . . . . . . . . . . . . 190 mapping control options . . . . . . . . . . . . . . 190 mapping defaults . . . . . . . . . . . . . . . . . . . . . . 190 | ✅ |
| GOCA-D-067 | Write Graphics Control . . . . . . . . . . . . . . . . . . 189 | ✅ |
| GOCA-D-068 | ReferenceAFPC-0008-03 | ✅ |
