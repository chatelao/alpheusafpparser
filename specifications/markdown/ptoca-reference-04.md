# Front Matter

<!-- Page 1 -->

**Advanced Function Presentation Consortium**
**Data Stream and Object Architectures**

**Presentation Text Object Content Architecture Reference**

**AFPC-0009-04**

<!-- Page 2 -->

Copyright © AFP Consortium 1997, 2025 ii

**Note:** Before using this information, read the information in "Notices" on page 179.

AFPC-0009-04
Fifth Edition (February 2025)

This edition applies to the Presentation Text Object Content Architecture (PTOCA). It is the second edition produced by the AFP Consortium™ (AFPC™) and replaces and makes obsolete the previous edition, AFPC-0009-03. This edition remains current until a new edition is published.

Technical changes are indicated in green, with a green vertical bar to the left of the change. Editorial changes that have no technical significance are not noted. For a detailed list of changes, see "Summary of Changes" on page vii.

**Internet**
Visit our home page: [www.afpconsortium.org](http://www.afpconsortium.org)

<!-- Page 3 -->

## Preface

This book describes the functions and services associated with the Presentation Text Object Content Architecture (PTOCA) architecture.

It is a reference, not a tutorial. It complements individual product publications, but does not describe product implementations of the architecture.

### Who Should Read This Book

This book is for systems programmers and other developers who develop or adapt a product or program to interoperate with other presentation products in an Advanced Function Presentation™ environment.

### AFP Consortium (AFPC)

The AFP Consortium is an international group bringing together voices from across the printing and presentation industry to keep the AFP™ architecture up to date and continually improving. AFP Consortium members, often market competitors, work together to ensure this stable, efficient, flexible architecture continues to thrive, even as the world of printing and presentation changes.

The Advanced Function Presentation (AFP) architectures began as the strategic, general purpose document and information presentation architecture for the IBM® Corporation. The first specifications and products go back to 1984. Although all of the components of the architecture have grown over the years, the major concepts of object-driven structures, print integrity, resource management, and support for high print speeds were built in from the start.

In the early twenty-first century, IBM saw the need to enable applications to create color output that is independent from the device used for printing and to preserve color consistency, quality, and fidelity of the printed material. This need resulted in the formation, in October 2004, of the AFP Color Consortium™ (AFPCC™). The goal was to extend the object architectures with support for full-color devices including support for comprehensive color management. The idea of doing this via a consortium consisting of the primary AFP architecture users was to build synergism with partners from across the relevant industries, such as hardware manufacturers that produce printers as well as software vendors of composition, workflow, viewer, and transform tools. Quickly more than 30 members came together in regular meetings and work group sessions to create the AFP Color Management Object Content Architecture™ (CMOCA™). A major milestone was reached by the AFP Color Consortium with the initial official release of the CMOCA specification in May 2006.

Since the cooperation between the members of the AFP Color Consortium turned out to be very effective and valuable, it was decided to broaden the scope of the consortium efforts and IBM soon announced its plans to open up the complete scope of the AFP architecture to the consortium. In June 2007, IBM's role as founding member of the consortium was transferred to the InfoPrint® Solutions Company, an IBM/Ricoh® joint venture; currently Ricoh holds the founding member position. In February 2009, the consortium was incorporated under a new set of bylaws with tiered membership and shared governance resulting in the creation of a formal open standards body called the AFP Consortium (AFPC). Ownership of and responsibility for the AFP architectures was transferred at that time to the AFP Consortium.

### How to Use This Book

This book is divided into six chapters, three appendixes, and a glossary.

<!-- Page 4 -->

*   **Chapter 1, "A Presentation Architecture Perspective"** introduces the AFP presentation architectures and positions Presentation Text Object Content Architecture as a strategic object content architecture.
*   **Chapter 2, "Introduction to PTOCA"** briefly states the purpose and function of PTOCA.
*   **Chapter 3, "Overview of PTOCA"** introduces the concepts that form the basis of PTOCA and provides a brief description of the data structures.
*   **Chapter 4, "Data Structures in PTOCA"** provides the detailed syntax, semantics, and pragmatics of the data structures found in PTOCA.
*   **Chapter 5, "Exception Handling in PTOCA"** describes how exceptions are handled in PTOCA and lists the exception codes.
*   **Chapter 6, "Compliance with PTOCA"** describes how products may be valid generators or receivers of PTOCA.
*   **Appendix A, "MO:DCA Environment"** describes the Presentation Text object in the context of a MO:DCA™ data stream.
*   **Appendix B, "IPDS Environment"** describes the Presentation Text object in the context of an IPDS™ data stream.
*   **Appendix C, "PTOCA Retired Functions"** describes the retired PTOCA functions.
*   **The "Glossary"** on page 181 defines some of the terms used within this book.

### How to Read the Syntax Diagrams

Throughout this book, syntax is described using the structure defined below. Six basic data types are used in the syntax descriptions:

*   **CODE**: Architected constant
*   **CHAR**: Character string, which may consist of any code points
*   **BITS**: Bit string
*   **UBIN**: Unsigned binary
*   **SBIN**: Signed binary
*   **UNDF**: Undefined type

Syntax for PTOCA is shown in tables like the following:

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| The field's offset, data type, or both | Name of field if applicable | Range of valid values if applicable | Meaning or purpose of the data element | | | | |

*   **M/O**: **M** means mandatory. **O** means optional.
*   **Def**: **Y** means that a default value is defined for the field. **N** means that there is no default value defined for the field.
*   **Ind**: **Y** means that the field defaults to a hierarchical default value when the default indicator (X'F..F') is present. **N** means that the default indicator semantic is not valid for the field.

The following is an example of PTOCA syntax for the Begin Line (BLN) control sequence as it appears in this book:

<!-- Page 5 -->

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 2 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'D8' – X'D9' | Control sequence function type | M | N | N |

Please refer to "Control Sequence Detailed Descriptions" on page 47 for a more detailed description of PTOCA syntax.

<!-- Page 6 -->

### Related Publications

Several other publications can help you understand the architecture concepts described in this book. The AFP Consortium publications and other AFP publications below are available on the AFP Consortium web site, [www.afpconsortium.org](http://www.afpconsortium.org).

**Table 1. AFP Consortium Architecture References**

| AFP Architecture Publication | Book Identification |
| :--- | :--- |
| AFP Programming Guide and Line Data Reference | AFPC-0010 |
| Bar Code Object Content Architecture™ Reference | AFPC-0005 |
| Color Management Object Content Architecture Reference | AFPC-0006 |
| Font Object Content Architecture Reference | AFPC-0007 |
| Graphics Object Content Architecture for Advanced Function Presentation Reference | AFPC-0008 |
| Image Object Content Architecture Reference | AFPC-0003 |
| Intelligent Printer Data Stream™ (IPDS) Reference | AFPC-0001 |
| Metadata Object Content Architecture Reference | AFPC-0013 |
| Mixed Object Document Content Architecture™ (MO:DCA) Reference | AFPC-0004 |
| Presentation Text Object Content Architecture Reference | AFPC-0009 |

**Table 2. Additional AFP Consortium Documentation**

| AFPC Publication | Book Identification |
| :--- | :--- |
| AFP Color Management Architecture™ (ACMA™) | G550–1046 (IBM) |
| AFPC Company Abbreviation Registry | AFPC-0012 |
| AFPC Font Typeface Registry | AFPC-0016 |
| BCOCA™ Frequently Asked Questions | AFPC-0011 |
| Metadata Guide for AFP | AFPC-0018 |
| MO:DCA-L:The OS/2 PM Metafile (.met) Format | AFPC-0014 |
| Presentation Object Subsets for AFP | AFPC-0002 |
| Recommended IPDS Values for Object Container Versions | AFPC-0017 |

**Table 3. AFP Font-Related Documentation**

| Publication | Book Identification |
| :--- | :--- |
| Character Data Representation Architecture Reference and Registry | SC09-2190 (IBM) |
| Font Summary for AFP Font Collection | S544-5633 (IBM) |
| Using OpenType Fonts in an AFP System | G544-5876 (IBM) |
| Technical Reference for Code Pages | S544-3802 (IBM) |

<!-- Page 7 -->

## Summary of Changes

This fifth edition of the PTOCA Reference contains the following changes:

*   Support for encrypted text data for secure printing and presentation
*   Glossary entries were updated to the latest common level
*   Small updates were made to correct errors and increase consistency and readability

As stated in the edition notice, the additions are marked in this publication in green, with green revision bars located on the left-hand side of a page.

<!-- Page 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 omitted as they were TOC or blank -->
# Chapter 1. A Presentation Architecture Perspective

This chapter provides a brief overview of Presentation Architecture.

## The Presentation Environment

Figure 1 shows today's presentation environment.

**Figure 1. Presentation Environment.** The environment is a coordinated set of services architected to meet the presentation needs of today's applications.

*   **Document Creation Services**: import/export, edit/revise, format, scan, transform
*   **Document Archiving Services**: store, retrieve, index, search, extract
*   **Document Viewing Services**: browse, navigate, search, clip, annotate, tag
*   **Document Printing Services**: print, submit, distribute, manage, print, finish

The ability to create, store, retrieve, view, and print data in presentation formats friendly to people is a key requirement in almost every application of computers and information processing. This requirement is becoming increasingly difficult to meet because of the number of applications, servers, and devices that must interoperate to satisfy today's presentation needs.

The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP architectures provide structures that support object-oriented models and client/server environments.

AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data.

<!-- Page 20 -->

## Architecture Components

AFP architectures provide the means for representing documents in a data format that is independent of the methods used to capture or create them. Documents can contain combinations of text, image, graphics, and bar code objects in presentation-system-independent and resolution-independent formats. Documents can contain fonts, overlays, and other resource objects required at presentation time to present the data properly. Finally, documents can contain resource objects, such as a document index and tagging elements supporting the search and navigation of document data, for a variety of application purposes.

The presentation architecture components are divided into two major categories: data streams and objects.

### Data Streams

A data stream is a continuous ordered stream of data elements and objects conforming to a given format. Application programs can generate data streams destined for a presentation service, archive library, presentation device, or another application program. The strategic presentation data stream architectures are:

*   Mixed Object Document Content Architecture (MO:DCA)
*   Intelligent Printer Data Stream (IPDS) Architecture

The MO:DCA architecture defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. The MO:DCA format supports storing and retrieving documents in an archive, viewing, annotation, and printing of documents or parts of documents in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them.

The IPDS architecture defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers.

<!-- Page 21 -->

**Figure 2. Presentation Model.** This diagram shows the major components in a presentation system and their use of data stream and object architectures.

*   **MO:DCA** to presentation servers
*   **IPDS** to printers and post processors
*   **Resource Objects**: Fonts, Overlays, Page Segments, Form Definition, Color Management Resources, Color Table, Document Index, Metadata
*   **Data Objects**: Text, Image, Graphics, Bar Codes, Object Containers, Other Objects

### Objects

Documents can be made up of different kinds of data, such as text, graphics, image, and bar code. Object content architectures describe the structure and content of each type of data format that can exist in a document or appear in a data stream. Objects can be either data objects or resource objects.

A data object contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data.

A resource object is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them.

All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects that can be individually positioned and manipulated to meet the needs of the presentation application.

<!-- Page 22 -->

The AFPC-defined object content architectures are:

*   **Presentation Text Object Content Architecture (PTOCA)**: A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition.
*   **Image Object Content Architecture (IOCA)**: A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition.
*   **Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA)**: A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition.
*   **Bar Code Object Content Architecture (BCOCA)**: A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition.
*   **Font Object Content Architecture (FOCA)**: A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document.
*   **Color Management Object Content Architecture (CMOCA)**: A resource architecture used to carry the color management information required to render presentation data.
*   **Metadata Object Content Architecture (MOCA)**: A resource architecture used to carry metadata in an AFP environment.

The MO:DCA and IPDS architectures also support data objects that are not defined by object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures.

In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document.

<!-- Page 23 -->

**Figure 3. Presentation Page.** This is an example of a mixed-object page that can be composed in a presentation-system-independent MO:DCA format and printed on an IPDS printer.

*   Page
*   Presentation Text Object(s)
*   Graphics Object
*   Image Object
*   Letterhead can be an overlay resource containing text, image, and graphics objects
*   Object areas can overlap

<!-- Page 24, 25 -->
# Chapter 2. Introduction to PTOCA

The Presentation Text object is the data object used in document processing environments for representing text which has been prepared for presentation. Text, as used here, means an ordered string of characters, such as graphic symbols, numbers, and letters, that are suitable for the specific purpose of representing coherent information. Text which has been prepared for presentation has been reduced to a primitive form through explicit specification of the characters and their placement in the presentation space. Control sequences which designate specific control functions may be embedded within the text. These functions extend the primitive form by applying specific characteristics to the text when it is presented. The collection of the graphic characters and control sequences is called Presentation Text, and the object that contains the Presentation Text is called the Presentation Text object.

Presentation Text is associated with the output of text information. A Presentation Text object is the description of Presentation text for a portion of a document, the intended connotation being finished product or formatted output. This output version of text contained within the object is in the form specified by Presentation Text Object Content Architecture (PTOCA) and has been designed for direct output on devices, such as displays or printers.

A Presentation Text object is a device-independent, self-defining representation of a two-dimensional presentation space, called the Presentation Text object space, or object space, which contains the Presentation Text data. The rules of PTOCA specify how the object space is constituted, what the boundaries are for text positioning, what the text content is, and how the text content is to be placed within the object space, using concepts such as sequential order, orientation, and position.

**Architecture Note:** Note that when presentation text is processed in a MO:DCA environment where the Presentation Text Data Descriptor (PTD) is carried in the Active Environment Group (AEG) for the page, or when such text is processed in an IPDS environment, the Presentation Text object is bounded by the beginning of the page and the end of the page. This is sometimes called a text major environment. When the PTD is carried in the Object Environment Group (OEG) of a MO:DCA text object, the text object is bounded by the Begin Presentation Text (BPT) and End Presentation Text (EPT) structured fields. For such objects, the PTD in the AEG is ignored.

The Presentation Text object space is defined on the Xp, Yp coordinate system, which is an orthogonal coordinate system based on the fourth quadrant of a standard Cartesian coordinate system. The object space is positioned within the data stream's object area. Coincident with the Xp, Yp coordinate system is the I, Bcoordinate system, which is a translation of the Xp, Yp coordinate system.

The position of the elements in the object space is described in terms of the I, Bcoordinate system. The increasing I-axis is the inline direction, which is normally the reading direction of the text. The increasing B-axis is the baseline direction, which is normally the direction for adding lines of text.

The basic elements of the object are the graphic characters which are identified as code points of a code page. The identification of graphic characters, their relationship to each other, and the relationship of the code point to the graphic character are given by the coded font selected to present the text.

The relationship of the elements to the space they occupy is described in terms of their orientation, starting location, and units of measure.

The positioning of the graphic characters on a line is accomplished by moving the presentation position. Graphic characters may be placed adjacent to one another or positioned anywhere in the object space through the use of control sequences defined by PTOCA. Control sequences have been defined to move the presentation position to another position, to move to the beginning of another line, to adjust the distance between two adjacent characters, to draw lines such as rules, to adjust the distance between lines, to change the font, to specify the color of characters and rules, to overstrike a text field with a specified character, and to underscore a text field.

<!-- Page 26 -->

National Language Support (NLS) is handled in the level of formatting above the Presentation Text object. Font NLS support is provided in the font mapping function in the controlling environment.

<!-- Page 27 -->
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

The Presentation Text object is designed to be carried by and become part of a data stream, called the controlling environment. The data stream defines rules by which the object can be carried. Further information about data streams can be found in Appendix A, "MO:DCA Environment", on page 163 and Appendix B, "IPDS Environment", on page 169.

### Data Structures

The Presentation Text Data Descriptor carries the size, shape, and other special information about the object. The data stream is responsible for providing the proper information to the receiver, but PTOCA specifies a hierarchical method for determining the default values to be used by the receiver if the data stream does not supply the requisite information.

The Presentation Text data contains the code points that identify the graphic characters and the control sequences that specify where and how the graphic characters are to be positioned within the object space. The graphic character code points that represent text information can be specified in a Transparent Data (TRN), a Repeat String (RPS), or a Unicode Complex Text (UCT) control sequence, or they can be specified as free-standing code points that appear between control sequences. Graphic character code points can also be resolved to glyph IDs in a font. These glyph IDs are carried in Glyph Layout Control (GLC) chains for presentation.

<!-- Page 28 -->

Further information about PTOCA data structures is found under "Presentation Text Data" on page 21 and "Presentation Text Data Descriptor" on page 29.

### Subsets of Function

The control sequences represent the functional capabilities provided by the Presentation Text object. Since receivers of the object might not all have equivalent capabilities, it is convenient to create subsets, also called subset levels, of the total function that is available. The base is a set of functions required in any environment, including the ability to interpret and validate the control sequences and parameters, and to detect and report exception conditions that are within the PTOCA subsets.

*   **PT1**: Includes a set of relatively primitive control sequences that a receiver is expected to support.
*   **PT2**: Includes all of the PT1 subset plus new control sequences for underscore, overstrike, superscripts and subscripts.
*   **PT3**: Includes all of the PT2 subset plus a new control sequence to enable spot (highlight) colors and process colors for text and rules.
*   **PT4**: Includes new control sequences to support the rendering of complex text.

The intent of subsets is to reduce the number of combinations of supported controls so that interchange between host processors is manageable. For further information about subsets, see Chapter 6, "Compliance with PTOCA", on page 153, Appendix A, "MO:DCA Environment", on page 163, and Appendix B, "IPDS Environment", on page 169.

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

The Xp, Yp coordinate system and the I, Bcoordinate system are closely related, as indicated in Figure 5 on page 12. In fact, the Xp-extent is equal to one of the I, Bcoordinate extents, either the I-extent or the B-extent, and the Yp-extent is equal to the other. Therefore, the angle between the I-axis and B-axis will be identical to the angle between the Xp-axis and the Yp-axis. The Xp, Yp coordinate system describes the spatial viewport for the reader, while the I, Bcoordinate system describes the directions to be used for presentation and for interpretation by the reader of the graphic characters being presented.

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

The presentation position in PTOCA designates a between-the-pels position on a presentation surface, not a pel centerline intersection position. The concept of between-the-pels positioning is especially important for the presentation of rules. Please see "Graphic Character Processing" on page 40 for more information.

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

Control sequences can be chained together using a chaining convention. Although the first control sequence in a chain has the prefix and class, the remaining chained control sequences do not. Chaining reduces the number of bytes to be handled and removes the need to determine whether the next character is a control sequence or not. Please see Table 4 on page 25 for a list of PTOCA control sequences, showing both unchained and chained function types. Please see "Control Sequence Chaining" on page 36 for more information about chaining.

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

The following pages contain summary descriptions of the PTOCA control sequences. Summary tables are provided following the descriptions. Please see "Control Sequence Detailed Descriptions" on page 47 for detailed descriptions of syntax, semantics, and pragmatics.

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
Specifies a named color value to be applied to foreground areas of the text presentation space, that is, characters, rules, and underscores. The values of the foreground color parameter serve as indexes into the color-value table found in Table 13 on page 103.

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

The following pages contain summary descriptions of the initial text conditions. Please refer to "Objects" on page 3 for detailed descriptions of semantics and pragmatics. Also see the corresponding control sequence, if appropriate, for additional information.

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
Specifies a foreground named color value to be used to present text, rules, and underscores. A foreground color parameter value represents an index into the color-value table in Table 13 on page 103. The default value is X'FF07'.

#### Text Orientation
Specifies the angular displacement values to be used for the I-axis orientation and the B-axis orientation parameters of the Set Text Orientation control sequence. The I-axis value represents the positive I-axis orientation as an angular displacement from the Xp-axis, and the resultant I-direction. The B-axis value represents the positive B-axis orientation as an angular displacement from the Xp-axis, and the resultant B-direction. The default value for the I-axis is X'0000', that is, zero degrees. The default value for the B-axis is X'2D00', that is, 90 degrees.

<!-- Page 50, 51 -->
Chapter 4. Data Structures in PTOCA
This chapter:
• Describes the role of parameters
• Explains documentation conventions used in this chapter
• Provides a detailed description of the control sequence
• Explains how graphic characters are processed
• Provides a detailed description of the Presentation Text data
• Provides a detailed description of the Presentation Text Data Descriptor
Parameters and Parameter V alues
Kinds of Parameters: The control sequences used within the Presentation Text object may contain parameters
that describe and control the way the control sequence affe cts the graphic characters to be presented. A
parameter is a variable to which a value is assigned. A parameter has an architected length, a set of values,
and a functional definition. These parameter values may be numeric, such as those that tell where text is to be
presented, or logical, such as those that tell whether data should be suppressed. A parameter value can be the
default indicator , specified by the value X'F ...F'. The default indicator means that the effective hierarchical value
is to be used instead of a value explicitly specified at this point. A parameter can be a reserved field. A
reserved field has a prescribed value, but no functional definition. Reserved fields should be set to zero by
PTOCA generators and should be ignored by receivers.
A mandatory parameter appears in a control sequence because the function of that parameter is required and
an actual value is necessary for proper performance. A mandatory parameter value may be the default
indicator , provided that the parameter has an actual value somewhere in the hierarchy . Mandatory parameters
must be supported by both PTOCA generators and receivers. In a sense, all parameters are required, since
the value of each parameter must be known if it is to have an effe ct on presentation. However , some
parameters are required only for specific types of presentation, and an actual value is not always necessary for
some parameters. For example, a default value may be acceptable.
An optional parameter need not appear in a control sequence. The function of that parameter may not be
required, or if the function is required, a default value may be acceptable instead. An optional parameter may
appear if the default value is not acceptable, if it is desirable to make a value explicit for documentation, or to
avoid the effect of values specified externally to the Presentation Text object. Optional parameters must be
supported by all PTOCA receivers.
Hierarchy: Certain parameters, called external parameters, use the following hierarchical techniques in
specification. If the parameter is not specified by individual control sequences in the Presentation Text object,
that is, the parameter is omitted or the parameter is the default indicator , the parameter may be specified in the
Presentation Text Data Descriptor . If it is not, the PTOCA default value is used. Not all parameters need to be
set at all levels of the specification hierarchy . Table 9 on page 35 identifies the valid hierarchical specification
levels for the parameters, indicated by X in the associated column. Note that the hierarchy consists of the
control sequence first, then the descriptor , and finally the PTOCA default value. Thus from a receiver's point of
view , the primary source for a parameter value is a control sequence. If it is possible for a control sequence to
provide the value, there will be an X in the control sequence column. If there is no control sequence to provide
the needed value, or if the appropriate control sequence is present but specifies the default indicator , the
descriptor becomes the source of the value. If it is possible for the descriptor to provide the value, there will be
an X in the descriptor column. However , if the descriptor cannot provide the value, or if the descriptor specifies
the default indicator , the PTOCA default value is used.
Default values and Presentation Text Data Descriptor values are termed external parameter specifications,
because these parameters need not be explicitly defined in the Presentation Text object. These values become
current values each time presentation of a Presentation Text object begins.

<!-- Page 52 -->

34 PTOCA Reference
Ranges of V alues: Every value must fit within the field defined to contain it. Additional constraints on values are
defined by the PTOCA subsets. See Chapter 6, “Compliance with PTOCA”, on page 153 and the appendixes
for further information about ranges.
Negative numbers are expressed in twos-complement form. If a parameter is less than the minimum specified
or more than the maximum specified, an exception condition exists. See the semantics of the aff ected control
for the appropriate exception condition code.
The maximum absolute value of a one-byte arithmetic value is 254 when the default indicator is valid. When
the default indicator is specifically excepted, the one-byte maximum arithmetic value is 255. One-byte values
are always unsigned. The maximum absolute value of a signed two-byte arithmetic value is 32,768. The
maximum absolute value of an unsigned two-byte arithmetic value is 65,534 when the default indicator is valid,
and 65,535 when the default indicator is not valid.
The minimum requirements of PTOCA for receivers regarding range is to provide calculation capacity equal in
size to the number of bits in the respective parameters. This is limited by the subset. Processing of the
Presentation Text object necessitates tracking the current positions within the object space. In addition,
PTOCA requires that receivers be capable of tracking the current positions outside of the object space as long
as presentation is not attempted.
The following example illustrates the intent of this concept for I
c
. A receiver may determine the maximum
number of bits for I
c
based on the physical size and resolution of the mechanism. For example, the receiver
may base it on a carriage width of three inches and a resolution of 1/1440 inch. For this receiver , positioning
outside the object space could be handled in the cases where the object space is smaller than the carriage
width. But when the object space is equal to or greater than the carriage width, the receiver's calculation
capacity may not be large enough to contain the calculations outside the object space, and the results may be
unpredictable.
Such overflow is not considered to be an exception condition by PTOCA. However , the architectural
recommendation to generators is to avoid addressable positions that are outside of the object space.
Parameter Data T ypes: Every parameter value is one of the following data types:
A bit string (BITS) is a string of bits one or more bytes long. Each bit has the value B'1' or B'0'.
A code (CODE) is a constant for which PTOCA defines a particular meaning.
A number is an unsigned (UBIN) or signed (SBIN) arithmetic value that implies count or magnitude.
A character string (CHAR) is one or more bytes of character information.
An undefined field (UNDF) is a field that is not defined by PTOCA.
In all cases bytes are composed of eight bits, called bits 0 - 7. Bit 0 is in the high-order position; that is, bit 0 =
2
7
and bit 7 = 2
0
. T wo bytes considered together are sixteen bits, called bits 0 - 15. Bit 0 is in the high-order
position; that is, bit 0 = 2
15.
and bit 15 = 2
0
. The first byte received is the high-order byte, that is, bits 0 - 7. The
second byte is the low-order byte, that is, bits 8 - 15. This is called big-endian bit order and big-endian byte
order .
Differen t syntax is used for the expression of values that pertain to rotation.
The Default Indicator: For certain parameters, the value X'F ...F' represents the default indicator . For these
parameters, the arithmetic value -1 is not available. The default indicator does not specify a value for a
parameter and, therefore, maintains a default value for that parameter . This indicator specifies that the default
value be obtained from the hierarchy , not including previous instances of the same parameter in the current
object. The syntax of each control sequence specifies whether the default indicator is valid for its parameters.
In general, the default value for an omitted optional trailing parameter is obtained from previous instances of
the same parameter in the current object according to the hierarchy .
Parameters and Parameter V alues

<!-- Page 53 -->

PTOCA Reference 35
**Table 9. Parameter Specification Hierarchy**

| Parameter | Set by Control Sequence (highest priority) | Set by Descriptor | PTOCA Default Value (lowest priority) |
| :--- | :---: | :---: | :--- |
| Measurement Units | | X | Receiver dependent |
| Size | | X | Receiver dependent |
| Baseline Increment | X | X | Receiver dependent, should be based on the coded font |
| Suppression identifier | | | None |
| Coded Font Local ID | X | X | Receiver dependent |
| Intercharacter Adjustment | X | X | 0 |
| Intercharacter Direction | X | X | 0 |
| Inline Margin | X | X | 0 |
| Initial Baseline Coordinate | | X | Receiver dependent |
| Initial Inline Coordinate | | X | 0 |
| Foreground Color | X | X | X'FF07' |
| Text Orientation | X | X | 0,90 |
| Rule Length | X | | None |
| Rule Width | X | | Receiver dependent |
| Overstrike Bypass Identifiers | X | | X'01' |
| Overstrike Character | X | | Coded font dependent |
| Temporary Baseline Increment | X | | ½ the Baseline Increment |
| Temporary Baseline Direction | X | | 0 |
| Temporary Baseline Precision | X | | 0 |
| Underscore Bypass Identifiers | X | | X'01' |
| Variable Space Character Code | | | Coded font dependent |
| Variable Space Character Increment | X | | Coded font dependent |
| Alternate Text | X | | None |
| Key Information | X | | None |
Key Information X None
Parameters and Parameter V alues

<!-- Page 54 -->

36 PTOCA Reference
Control Sequence
The Presentation Text object can specify that text functions are to be performed, such as underlining, margin
setting, or justification. These functions are invoked by sequences that must begin with identifiers that
distinguish them from code points. A character that delimits a string that must be processed in a diff erent
manner may be thought of as an escape character . In the Presentation Text object, the equivalent of an escape
character is the Control Sequence Prefix. The string it delimits is a control sequence. The Presentation Text
object supports only one type of control sequence.
Control Sequence Format
A control sequence contains a Control Sequence Introducer followed by parameters. The parameter portion of
the control sequence may be from 0 to 253 bytes in length.
Introducer Parm
1
Parm
2
Parm
3
...
Parm
n
An unchained Control Sequence Introducer contains the following fields:
• A one-byte prefix, X'2B'
• A one-byte class, X'D3'
• A one-byte length
• A one-byte function type
Prefix Class Length Function T ype
X'2B' X'D3'
The length field delimits the control sequence by indicating the last byte in the control sequence. The length
field specifies the count of bytes in the control sequence, starting with itself. If the value of the length field is 2,
there are no parameters in the control sequence. If the value is 3 or greater , there are parameters.
The function type uniquely identifies a control sequence, defines its syntax, and determines its semantics. The
number of parameters and the number of bytes in each parameter are implicit in the function type definition.
A chained Control Sequence Introducer contains the following fields:
• A one-byte length
• A one-byte function type
Length Function T ype
Thus a Control Sequence Introducer is either two bytes or four bytes in length, depending on whether it is
chained or unchained.
Control Sequence Chaining
Control sequences may be chained, that is, concatenated. Chaining provides a look-ahead function that
permits a processor to avoid changes from processing control sequences to processing graphic characters
while scanning or executing Presentation Text Data. When control sequences are chained, the prefix and class
bytes are only required in the first control sequence in the chain.
Chaining is signaled by the presence of an odd function type. That is, the low-order bit is B'1'. If a control
sequence has a function type with the low-order bit equal to B'1', the string that follows the control sequence is
a chained control sequence. A chained control sequence begins with the length field, whereas an unchained
control sequence begins with the Control Sequence Prefix. The chain is terminated by a control sequence with
an even numbered function type (low-order bit is B'0'), or by the end of the current Presentation Text object. If a
Control Sequence

<!-- Page 55 -->

PTOCA Reference 37
control sequence has a function type with the low-order bit equal to B'0', the string which follows the control
sequence may be code points or an unchained control sequence.
Note: In some environments, terminating a chain by ending the Presentation Text object might cause problems,
therefore it is recommended that generators always terminate chains with a control sequence whose
low-order bit is B'0'.
Chains of control sequences may be as long as desired. Control sequences in a chain are interpreted in the
order in which they are encountered.
Table 4 on page 25 lists the control sequences that can appear within Presentation Text data and their function
types, both unchained and chained.
Modal Control Sequences
Certain control sequences are modal. That is, they establish a parameter value that persists after the control
sequence has been processed. An example is Set Inline Margin, which sets the size of the inline margin. When
such a control sequence is processed, its parameter value replaces the existing parameter value. The existing
parameter value may have been set in one of the following ways:
• A previous modal control sequence in the current Presentation Text object
• Externally to the Presentation Text object
• By default
The new parameter value remains in effect until a subsequent control sequence for that function is
encountered or until the end of the current Presentation Text object.
Architecture Note: Note that when presentation text is processed in a MO:DCA environment where the
Presentation Text Data Descriptor (PTD) is carried in the Active Environment Group (AEG) for the page
or overlay , or when such text is processed in an IPDS environment, the Presentation Text object is
bounded by the beginning of the page and the end of the page. This is sometimes called a text major
environment. When the PTD is carried in the Object Environment Group (OEG) of a MO:DCA text object,
the text object is bounded by the Begin Presentation Text (BPT) and End Presentation Text (EPT)
structured fields. For such objects, the PTD in the AEG is ignored.
Control Sequence

<!-- Page 56 -->

38 PTOCA Reference
Application Note: When the Begin Presentation Text (BPT) structured field is encountered in a MO:DCA data
stream, all initial text conditions specified in the Presentation Text Data Descriptor (PTD) structured field
are set prior to processing the text object. In addition, in AFP environments, whenever a BPT is
encountered, AFP presentation servers set the following default page-level initial text conditions before
the PTD initial conditions are set:
Parameter V alue
Initial (I,B) Presentation Position (0,0)
T ext Orientation 0°,90°
Coded Font Local ID X'FF' (default font)
Baseline Increment 6 lpi
Inline Margin 0
Intercharacter Adjustment 0
T ext Color X'FFFF' (default color)
Application Note: The PTD also specifies the size of the text object space. When the PTD is specified in the
AEG of a MO:DCA page in a text major environment, the extents of the text object space are set equal
to the extents of the page. When the PTD is specified in the OEG of a MO:DCA text object, the extents
of the text object space can be set to any value in the allowed range.
Control Sequence Default Indicator
The default indicator (X'F ..F') in a parameter of a control sequence causes the parameter to use the
hierarchical default value for that parameter . The hierarchical default values are listed in Table 9 on page 35.
Control Sequence Introducer
The Control Sequence Introducer is a two-byte or four-byte field, depending on whether the control sequence
is unchained or chained.
Syntax: A Control Sequence Introducer can appear only at the beginning of a control sequence.
**Syntax:** A Control Sequence Introducer can appear only at the beginning of a control sequence.

An unchained Control Sequence Introducer has the following format:

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 2–255 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'00' – X'FE' | Control sequence function type | M | N | N |

A chained Control Sequence Introducer has the following format:

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | LENGTH | 2–255 | Control sequence length | M | N | N |
| 1 | CODE | TYPE | X'00' – X'FE' | Control sequence function type | M | N | N |

<!-- Page 57 -->

PTOCA Reference 39
Semantics: A Control Sequence Introducer begins, specifies the length of, and identifies the type of a control
sequence. It suspends the processing of text and begins the processing of control sequences.
Pragmatics: A Control Sequence Introducer immediately precedes the data portion of a control sequence.
Control Sequence Prefix
The Control Sequence Prefix marks the beginning of an unchained control sequence. This parameter causes a
change in the mode of interpretation of a presentation text stream. When a Control Sequence Prefix is
encountered, the bytes immediately following are interpreted as a control sequence rather than as code points.
This mode of interpretation continues until the control sequence or control sequence chain is terminated.
Control Sequence Class
The control sequence class characterizes the syntax of the Control Sequence Introducer . This parameter
specifies how the introducer of the current control sequence should be interpreted. X'D3' has been assigned
for PTOCA. If any other class is encountered, exception condition EC-1C01 exists. The standard action is to
ignore the control sequence.
Control Sequence Length
The control sequence length specifies the length of the control sequence beginning with and including itself.
The prefix and class are not included in the length. If the length parameter , as specified for individual control
sequences, is invalid, exception condition EC-1E01 exists:
• If a mandatory parameter is missing from the control sequence, the standard action is to ignore the control
sequence.
• If the control sequence is longer than specified, the standard action is to ignore only the undefined
parameters.
If part of an optional parameter field is missing from the control sequence, exception condition EC-1E01 exists.
The standard action is to ignore the partially specified optional parameter field.
Control Sequence Function T ype
The control sequence function type characterizes the eff ect and syntax of a control sequence.
This parameter specifies how parameters in the control sequence are to be interpreted. The function type
identifies the operation of the control sequence; for example, to set a value or to draw a rule.
Please refer to Table 4 on page 25 for a listing of PTOCA function types. These are the only valid function
types. If any other function type is encountered, exception condition EC-0001 exists. The standard action is to
ignore the control sequence and continue presenting.
Control Sequence

<!-- Page 58 -->

40 PTOCA Reference
Graphic Character Processing
Format: The format of the graphic characters is specified by the active coded font. The coded font is specified
by an external reference to a coded font resource by specification of a coded font local identifier . The coded
font determines the length of the code point used to specify each graphic character , and the assignment of a
code point to each graphic character . If a character is contained in the Presentation Text object that is not
defined in the active coded font, exception condition EC-2100 exists. The standard action is to present the
default character defined by the active coded font. Please see “Font Concepts” on page 13 for more
information on fonts.
Presentation: Graphic character processing uses the I,Bcoordinate system. I
c
and B
c
represent the I and B
coordinates of the current presentation position. I
o
and B
o
represent the origin of the I, Bcoordinate system.
Please refer to Table 10 on page 42, Figure 8 on page 43, and Figure 9 on page 43. Upon entry into the
Presentation Text object, I
c
and B
c
are initialized to the values specified at the data stream level. If values are
not specified by the controlling environment, these coordinates are set to their standard action values; that is, I
c
= I
o
= 0 and B
c
= B
o
= 0. See “Enter Object” in Table 10 on page 42. After the object has been entered, the
values of I
c
and B
c
may be changed before the first character is presented, either by parameters in the
Presentation Text Data Descriptor or by control sequences in the Presentation Text data.
Each graphic character in a string of Presentation Text data normally causes a character shape, as defined in
the active font resource, to be placed on the presentation surface. The character's reference point coincides
with the presentation position, I
c
,B
c
, and the character baseline is made parallel to the baseline by rotation.
Simultaneously , the I
c
coordinate of the current presentation position is increased by the character increment
specified for that character in the active coded font. See “Present character , general case” in Table 10 on page
42.
If a character is to be placed immediately following another character on the presentation surface, the I
c
coordinate is first increased or decreased by the intercharacter adjustment. See “Present character , specific
cases” in Table 10 on page 42. Then the character shape is placed with the character reference point
coincident with this revised presentation position. Last, the current presentation position is incremented by the
addition of the character increment value. As each graphic character is placed, the current presentation
position is moved in the positive inline direction.
The concept of between-the-pels positioning on a presentation surface is important for the presentation of
rules. For inline rules, please see Figure 10 on page 44. Use one of the four diagrams, depending upon the
rule length and rule width values. When presentation is to begin for an inline rule, the physical pels are located
as shown. I and B are not directly represented in the diagrams, since the diagrams are intended to provide the
approximate location of the pels to be presented for inline rules. The I and B refer to direction only . For
example, if the I-axis is vertical and the I-direction is down as a result of an orientation change, an inline rule of
positive length and positive width would still be positioned as in diagram (b).
For baseline rules, please see Figure 1 1 on page 44. Use one of the four diagrams, depending upon the rule
length and rule width values. When presentation is to begin for a baseline rule, the physical pels are located as
shown. I and B are not directly represented in the diagrams, since the diagrams are intended to provide the
approximate location of the pels to be presented for baseline rules. The I and B refer to direction only . For
example, if the B-axis is horizontal and the B-direction is to the left as a result of an orientation change, a
baseline rule of positive length and positive width would still be positioned as in diagram (b).
Overrun: If the intercharacter adjustment is zero, n characters, each having a fixed character increment of CI,
may be printed in a presentation space whose I-extent is n times CI. For example, if n is 100 characters and CI
is 0.1 inch, that is, 10-pitch, all 100 characters will fit on a logical page with an I-extent of 10 inches. In this
case, the 100th character just fits within the presentation space. Following placement of the 100th character ,
the current inline coordinate position, I
c
, is one measurement unit beyond the I-extent.
Graphic Character Processing

<!-- Page 59 -->

PTOCA Reference 41
If an attempt is made to present any portion of a character's character box or any portion of a rule beyond the I-
extent, exception condition EC-0103 exists. If the presented character is defined in terms of A-space, B-space,
and C-space, only the B-space is considered part of the character box. The standard action for this exception
condition is to refrain from presenting the character whose character box is outside the object space boundary ,
and to continue processing without presenting characters. Processing continues until the presentation position
is moved to an addressable point that is valid. This exception condition exists regardless of the graphic
character causing the condition. For example, if the graphic character is a blank or a variable space character ,
the exception condition exists even though no marks are made on the presentation surface.
This exception condition ceases to exist when an intercharacter adjustment causes the presented character to
move into the object space. The exception condition exists if an intercharacter adjustment causes any part of
the character box of the presented character to move out of the object space. Throughout this process the
current baseline coordinate, B
c
, remains unchanged.
The minimum calculation capacity for overrun handling is equal to the number of bytes that constitutes the
parameter . Thus a two-byte parameter requires a minimum of two bytes of storage.
Control sequences are processed according to their semantics without regard to the presence of an overrun.
Consider the following example. Assume that the presentation position is I
o
, B
o
, that is, the upper left corner of
the presentation space, and that the character reference point of the character to be presented is the lower left
corner of its character box. In this case, an exception condition exists even though the presentation position is
within the object space, because some of the character shape extends outside of the object space. Invoking
the standard action causes additional character increments to be applied repeatedly , and each character is
outside of the object space. In effect, each character received is rejected until a control sequence is received
that moves the baseline away from the I-axis, such as Begin Line or Absolute Move Baseline.
Superscripting and Subscripting: Superscripts and subscripts are graphic characters that appear above or
below adjacent graphic characters on the same line, and may be smaller than adjacent graphic characters on
the same line. They have special purposes, such as representing exponents or footnote numbers.
Superscripts and subscripts may be implemented in diff erent ways.
• A font may contain smaller graphic characters which are designed to appear as superscripts or subscripts.
These characters may be designed with their character base above, on, or below the nominal baseline. With
a font like this, superscripting or subscripting is implicit in presenting one of these graphic characters.
• A font may consist entirely of graphic characters which are designed so that they will appear as superscripts
or subscripts when used with other fonts. With a font like this, superscripting or subscripting is implicit in
invoking the font.
• A font may be designed without smaller characters for use as superscripts and subscripts. With a font like
this, other means must be used to create superscripts and subscripts.
– Superscripting or subscripting can be accomplished by temporarily lowering or raising the B-axis
coordinate of the presentation position. With this technique, the size of the superscript or subscript graphic
characters is the same as the immediately preceding graphic characters. This technique is useful for
typescript, and the B-axis coordinate is usually lowered or raised ½ line.
– Superscripting or subscripting can be accomplished by temporarily lowering or raising the B-axis
coordinate of the presentation position and invoking a different font, whose graphic characters are smaller
than the immediately preceding graphic characters. Such smaller graphic characters are usually in a
diffe rent style specifically designed for superscripting or subscripting. This technique is useful in formal
letters and compositions. The distance that the B-axis coordinate is lowered or raised depends on the
purpose of the superscript or subscript. For example, a limit to an integral is displaced further than an
exponent.
This last technique is the most general, since it can apply to a variety of requirements, including the following:
• Nested superscripts and subscripts, that is, superscripts and subscripts of superscripts or subscripts
• Mathematical symbols of different sizes, for example, integrals, sums, products, and exponents
Graphic Character Processing

<!-- Page 60 -->

42 PTOCA Reference
• Specially stylized superscripts or subscripts, such as italic characters and Greek letters
In the context of superscripting and subscripting, the established baseline is the baseline on which a string of
graphic characters appears to rest, the temporary baseline is the baseline on which a superscript or subscript
appears to rest, and the current baseline is the baseline on which the next graphic character will appear to rest.
The current baseline may be the established baseline or the temporary baseline.
In PTOCA superscripting and subscripting, including the establishment of a temporary baseline, is specified by
the T emporary Baseline Move control sequence.
**Table 10. Equations for Graphic Character Presentation**

| WHEN | WHAT | EQUATIONS |
| :--- | :--- | :--- |
| **Enter Object** | Use initial values from data stream | Ic = Ih, Bc = Bh |
| | Or use default initial values | Ic = Io = 0, Bc = Bo = 0 |
| **Present character, general case** | Bc = Bo = 0 | |
| | Present variable space character | Icnew = Ic + VSI |
| | Present graphic character | Icnew = Ic + CI |
| **Present character, specific cases** | **Following incrementing character:** | |
| | Present first character (incrementing) | Icnew = Ic +/- ADJSTMNT |
| | Followed by second character (incrementing) | Present first character (incrementing): Icnew = Ic + CI |
| | | Icnew = Ic +/- ADJSTMNT |
| | | Present second character (incrementing): Icnew = Ic + CI |
| | **Following incrementing character:** | |
| | Present first character (non-incrementing) | Icnew = Ic +/- ADJSTMNT |
| | Present second character (incrementing) | Present first character (non-incrementing): Icnew = Ic |
| | | Present second character (incrementing): Icnew = Ic + CI |
| | **Following incrementing character:** | |
| | Present Variable Space Character | Icnew = Ic + VSI |
| | Followed by first character (incrementing) | Present first character (incrementing): Icnew = Ic + CI |

This table shows how the Icnew coordinate is modified during the presentation of characters.
cnew
coordinate is modified during the presentation of characters.
Graphic Character Processing

<!-- Page 61 -->

PTOCA Reference 43
Figure 8. Presentation Position without Intercharacter Adjustment
Figure 9. Presentation Position with Intercharacter Adjustment
Graphic Character Processing

<!-- Page 62 -->

44 PTOCA Reference
Figure 10. Between-the-Pels Illustrations for Inline Rules
Figure 1 1. Between-the-Pels Illustrations for Baseline Rules
Graphic Character Processing

<!-- Page 63 -->

PTOCA Reference 45
Exception Conditions: Control sequence processing and graphic character processing can cause the
following exception conditions:
• EC-1E01...A mandatory parameter is missing.
• EC-1C01...The control sequence class is invalid.
• EC-1E01...The control sequence length is not valid.
• EC-1E01...An optional parameter is partially missing.
• EC-0001...The control sequence function type is invalid.
• EC-2100...The Presentation Text object contains a graphic character code point that is not defined in the
active coded font.
• EC-0103...An attempt is made to present a character or a rule outside of the object space.
Graphic Character Processing

<!-- Page 64 -->

46 PTOCA Reference
Presentation Text Data
Presentation Text data consists of character code points and embedded control sequences, which together are
called presentation text. The architected content of the presentation text depends on the subset selected by
the generator of the object.
Syntax: Please see the appendixes for environmental information about the syntax of Presentation Text data.
Semantics: Presentation Text data inherits any modal parameter values, such as inline margin and coded font,
that were specified externally to the Presentation Text object. It also inherits the current presentation position.
These values may be specified by the controlling environment.
The content of Presentation Text data is graphic character code points and control sequences. For the syntax,
semantics, and pragmatics of the control sequences, see “Control Sequence Detailed Descriptions” on page
47.
Pragmatics: Presentation text can consist of almost any string of eight-bit bytes. In the single-byte mode, these
bytes may be seven-bit code points, with the leading bit zero, or eight-bit code points. In the double-byte mode,
they may be sixteen-bit code points. The coded character representation is determined through reference to a
coded font in the controlling environment. All code points in the presentation text are translated for
presentation by reference to the active coded font, with the following exceptions:
• The V ariable Space Character , which is normally X'40' for EBCDIC single-byte fonts, X'20' for ASCII single-
byte fonts, X'4040' for EBCDIC double-byte fonts, X'2020' for ASCII double-byte fonts, and X'0020' or
X'00A0' for Unicode fonts;
• The Control Sequence Prefix, which is X'2B'.
If it is necessary to present the Control Sequence Prefix code point, use the T ransparent Data control
sequence.
All control sequence displacements are expressed in terms of the I,Bcoordinate system. The directions of the
I,Bcoordinates are specified initially in the text orientation initial conditions in the Presentation Text Data
Descriptor . They can be respecified within a Presentation Text object with a Set T ext Orientation control
sequence.
When processing begins for the first Presentation Text Data in a given Presentation Text object, the current
presentation position, I
c
and B
c
, is set using values from the Presentation Text Data Descriptor . The initial inline
coordinate value and initial baseline coordinate value are used. When processing begins for subsequent
Presentation Text data within the same Presentation Text object, the current presentation position is set to the
last presentation position from the previous Presentation Text data.
Each graphic character code point in a Presentation Text object causes the character reference point of the
character shape to be placed at I
c
, B
c
with the character baseline parallel to the baseline. I
c
is increased by the
character increment and, for a character immediately followed by another character , is adjusted by the
intercharacter adjustment.
In addition to graphic character code points, Presentation Text data can contain embedded control sequences.
These are strings of two or more bytes which signal an alternate mode of processing for the content of the
current Presentation Text data. Although PTOCA allows the definition of various types of control sequences,
only one type of control sequence is permitted in the Presentation Text data. The escape character to be used
in the Control Sequence Introducer is X'2B'.
As previously stated, control sequences can be chained. However , there is no requirement that control
sequences be chained.
Presentation Text Data

<!-- Page 65 -->

PTOCA Reference 47
Control Sequence Detailed Descriptions
The following pages contain detailed descriptions of the PTOCA control sequences. The descriptions show the
syntax, semantics, and pragmatics of the control sequences.
Documentation Conventions: Each PTOCA control sequence is described syntactically in this reference by a
table. Following each table is semantic information related to each component of the control sequence.
Syntactically descriptive material following the table may indicate that additional restrictions apply to the control
sequence defined by the table. Each syntax table has the following columns:
• Offset refers to the position of a parameter .
• T ype denotes the syntax of the parameter by data type. Please see “Parameter Data T ypes” on page 34 for
more information.
• Name is a short field name suitable for coding.
• Range denotes the smallest and largest valid values that may occur in this field. Negative numbers are
expressed in twos-complement form.
• Meaning gives an explanatory or descriptive name for the parameter .
• M/O refers to the parameter's appearance in the control sequence. O means that the parameter is optional.
That is, the generator of the Presentation Text object does not have to include this parameter . However , the
receiver must support this parameter if it supports the control sequence that contains the parameter . M
means that the parameter's appearance is mandatory . If a particular control sequence occurs in the object,
all mandatory parameters in that control sequence must be present.
If a mandatory parameter is missing, exception condition EC-1E01 exists. The standard action is to ignore
the control sequence to which the missing parameter belongs.
• Def refers to the existence of a PTOC A default value for the parameter which is to be used when no explicit
value is provided and the standard action is to continue. Y means that there is such a value. N means that
there is no such value. This value, also called the standard action value, is defined in the description which
follows each syntax table.
• Ind specifies the validity of the default indicator . Y means that the default indicator is valid. N means that the
default indicator is not valid. The default indicator is represented by X'F ..F' and is described in “Control
Sequence Default Indicator” on page 38.
Reserved Fields Certain fields may be denoted as reserved. A reserved field is a parameter that has no
functional definition at the current time, but may have at some time in the future. All bytes comprising a field
defined to be reserved should be given a value of zero by generating applications. Receiving applications
should ignore any value in a reserved field.
Interpreting Ranges The range values shown in the syntax tables assume a measurement unit of 1/1440 inch.
That is, they assume that the measurement base is ten inches, and that the X
p
-units per unit base and Y
p
-units
per unit base are 14,400. If this assumption is not correct, and a different measurement unit is supported, the
correct range values can be determined by using the following steps:
1. Calculate the number of actual supported units per inch (X) as follows:
• If the measurement base is ten inches, divide the number of supported units per ten inches by 10.
• If the measurement base is ten centimeters, multiply the number of supported units per ten centimeters
by 0.254.
2. Calculate the ratio of actual supported units per inch (X) to the assumed 1,440 units per inch as follows:
• Divide (X) by 1,440, yielding the ratio (Y).
3. Calculate the new range value in the supported measurement units as follows:
• Convert the old range value to base ten; then multiply it by the ratio (Y).
• Round to the nearest integer .
Control Sequence Detailed Descriptions

<!-- Page 66 -->

48 PTOCA Reference
For example, suppose that the specified range is X'8000'–X'7FFF' when using 14,400 units per 10 inches. The
equivalent range at a unit of measure of 1/240 of an inch is calculated as follows:
1. Supported units per inch = 2,400 ÷ 10 = 240
2. Ratio of supported units per inch to 1,440 units per inch = 240 ÷ 1,440 = 1/6
3. Range at 2,400 units per 10 inches:
a. X'8000' = -32,768 (converted to base 10)
-32,768 × 1/6 = -5,461.3333
b. X'7FFF' = 32,767(converted to base 10)
32,767 × 1/6 = 5,461.1667
Therefore, the equivalent range at 2,400 units per 10 inches is -5,461 to 5,461 which in hexadecimal is
X'EAAB' to X'1555'.
Control Sequence Detailed Descriptions

<!-- Page 67 -->

PTOCA Reference 49
Absolute Move Baseline (AMB)
The Absolute Move Baseline control sequence moves the baseline coordinate relative to the I-axis.
Syntax
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 4 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'D2' – X'D3' | Control sequence function type | M | N | N |
| 4–5 | SBIN | DSPLCMNT | X'0000' – X'7FFF' | Displacement | M | N | N |
DSPLCMNT is a signed binary number expressed in measurement units. It does not accept the default
indicator . The range for this parameter assumes a measurement unit of 1/1440 inch. If it is necessary to
convert to a different measurement unit, please see the conversion routine described in “Interpreting Ranges”
on page 47.
Semantics
This control sequence specifies a displacement, DSPLCMNT , in the B-direction from the I-axis of the object
space to a new baseline coordinate position. After execution of this control sequence, presentation is resumed
at the new baseline coordinate position. This control sequence does not modify the current inline coordinate
position.
I
cnew
= I
c
B
cnew
= B
o
+ DSPLCMNT
If the value of DSPLCMNT is not supported or is not within the range specified by PTOC A, exception condition
EC-1301 exists. The standard action in this case is to continue presentation according to the description given
in the Pragmatics section.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If DSPLCMNT is zero, the addressable position is the I-axis, and any intercharacter adjustment is not applied.
If a move is to a presentation position for which the character box will exceed the object space and an attempt
is made to present there, exception condition EC-0103 exists. The standard action in this case is to refrain
from presenting the character that exceeds the object space, and to continue processing without presenting
characters until the presentation position occupies a valid addressable position for the character being
presented. Then presentation of characters may resume. PTOCA does not constrain the advancement of the
baseline coordinate position toward the I-axis. However , a constraint of this type may be imposed by the
subset level or by the receiver . If this constraint is applied and DSPLCMNT is negative, exception condition
EC-1403 exists. The standard action in this case is to ignore the control sequence.
AMB Control Sequence

<!-- Page 68 -->

50 PTOCA Reference
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1301...The value of DSPLCMNT is not supported or is not within the range specified by PTOCA.
• EC-0103...The presentation position is outside the object space and presentation is attempted.
• EC-1403...Negative DSPLCMNT is not valid.
AMB Control Sequence

<!-- Page 69 -->

PTOCA Reference 51
Absolute Move Inline (AMI)
The Absolute Move Inline control sequence moves the inline coordinate position relative to the B-axis.
Syntax
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control sequence prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 4 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'C6' – X'C7' | Control sequence function type | M | N | N |
| 4–5 | SBIN | DSPLCMNT | X'0000' – X'7FFF' | Displacement | M | N | N |
DSPLCMNT is a signed binary number expressed in measurement units. It does not accept the default
indicator . The range for this parameter assumes a measurement unit of 1/1440 inch. If it is necessary to
convert to a different measurement unit, please see the conversion routine described in “Interpreting Ranges”
on page 47.
Semantics
This control sequence specifies a displacement, DSPLCMNT , in the I-direction from the B-axis of the object
space to a new inline coordinate position, and resumes presentation at the new inline coordinate position. It
does not modify the current baseline coordinate position.
I
cnew
= I
o
+ DSPLCMNT
B
cnew
= B
c
If the value of DSPLCMNT is not supported or is not within the range specified by PTOC A, exception condition
EC-1401 exists. The standard action in this case is to continue presentation according to the description given
in the Pragmatics section.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If the value of DSPLCMNT is zero, the addressable position is at the B-axis, and any intercharacter adjustment
is not applied. If a move is to a presentation position for which the character's character box will exceed the
object space and an attempt is made to present there, exception condition EC-0103 exists. The standard
action in this case is to refrain from presenting the character that exceeds the object space, and to continue
processing without presenting characters until the presentation position occupies a valid addressable position
for the character being presented. Then presentation of characters may resume.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1401...The value of DSPLCMNT is not supported or is not within the range specified by PTOCA.
AMI Control Sequence

<!-- Page 70 -->

52 PTOCA Reference
• EC-0103...The presentation position is outside the object space and presentation is attempted.
AMI Control Sequence

<!-- Page 71 -->

PTOCA Reference 53
Begin Line (BLN)
The Begin Line control sequence begins a new line.
Syntax
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control sequence prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 2 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'D8' – X'D9' | Control sequence function type | M | N | N |
Semantics
This control sequence marks the beginning of a new line. It increments the current baseline coordinate position
by the amount of the baseline increment, INCRMENT . It sets the current inline coordinate to the inline margin.
Presentation is resumed at the new baseline coordinate position at the inline margin.
I
cnew
= I
margin
B
cnew
= B
c
+ INCRMENT
Exception Condition
This control sequence can cause the following exception condition:
• None
BLN Control Sequence

<!-- Page 72 -->

54 PTOCA Reference
Begin Suppression (BSU)
The Begin Suppression control sequence marks the beginning of a string of presentation text that may be
suppressed from the visible output.
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 3 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'F2' – X'F3' | Control sequence function type | M | N | N |
| 4 | CODE | LID | X'00' – X'FF' | Suppression identifier | M | N | N |
This control sequence marks the beginning of a string of presentation text that may be suppressed from the
visible output. It is activated by a local identifier , LID. This control sequence works in conjunction with the End
Suppression control sequence, which also contains a LID. Please see “End Suppression (ESU)” on page 63. If
the LID in this control sequence has been activated for the current Presentation Text object in the data stream
hierarchy , the string of presentation text between this control sequence and the next End Suppression control
sequence with the same LID does not appear in the visible output. Even though the text does not appear , all
control sequences within the suppressed field are executed, and the I-coordinate and B-coordinate are
updated as if the text had appeared. Only the actual presentation of the graphic characters and rules is
suppressed. Please see Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS
Environment”, on page 169 for further information about suppression.
If the value of the LID is not supported or is not within the range specified by PTOCA, exception condition EC-
9801 exists. The standard action in this case is to ignore the control sequence. Please see the Pragmatics
section for additional exception conditions.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
If the LID in this control sequence is not activated in the data stream hierarchy , this control sequence and the
corresponding End Suppression control sequence are processed as no-operations.
Pragmatics
Several kinds of exception conditions can exist with the Begin and End Suppression control sequences. These
exception conditions can exist whether or not the LID has been activated in the data-stream hierarchy .
• Nesting of Begin and End Suppression control sequences is not allowed. If a second Begin Suppression
control sequence follows a Begin Suppression control sequence without an intervening and corresponding
End Suppression control sequence, exception condition EC-0601 exists. The standard action in this case is
to ignore the second Begin Suppression control sequence.
BSU Control Sequence

<!-- Page 73 -->

PTOCA Reference 55
• If a Begin Suppression control sequence is followed by an End Suppression control sequence that has a
diffe rent value for the LID, exception condition EC-0201 exists. The standard action in this case is to ignore
the End Suppression control sequence.
• If an End Suppression control sequence occurs in a Presentation Text object without a previous valid Begin
Suppression control sequence, exception condition EC-0201 exists. The standard action is to ignore the End
Suppression control sequence.
• If a Begin Suppression control sequence appears in a Presentation Text object with no corresponding End
Suppression control sequence, exception condition EC-0401 exists. The standard action in this case is to
process the object as if the corresponding End Suppression control sequence were present at the end of the
object. That is, all of the data following the Begin Suppression control sequence is suppressed.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9801...The value of the LID is not supported or is not in the range specified by PTOCA.
• EC-0601...Nesting exists.
• EC-0201...The values of the LIDs do not match within a BSU, ESU pair .
• EC-0201...An ESU control sequence occurs without a preceding BSU control sequence.
• EC-0401...A BSU control sequence occurs without a succeeding ESU control sequence.
BSU Control Sequence

<!-- Page 74 -->

56 PTOCA Reference
Draw B-axis Rule (DBR)
The Draw B-axis Rule control sequence draws a rule in the B-direction.
Syntax
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 4, 7 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'E6' – X'E7' | Control sequence function type | M | N | N |
| 4–5 | SBIN | RLENGTH | X'8000' – X'7FFF' | Length | M | N | N |
| 6–8 | SBIN | RWIDTH | See Semantics section | Width | O | Y | Y |
a measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the
conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence specifies the dimensions of a rule that extends from the current presentation position in
both the B-direction and the I-direction. The current I-axis and B-axis coordinates are not changed by this
control sequence.
The length parameter , RLENGTH, is the length of the rule in the B-direction. If RLENGTH is omitted, exception
condition EC-1E01 exists. The standard action is to treat this control sequence as a no-operation. If the value
of RLENGTH is not supported or is not within the range specified by PTOCA, exception condition EC-8202
exists. The standard action is to ignore the control sequence and continue presentation with the value
determined according to the description given in the Pragmatics section.
The width parameter , RWIDTH, is the width of rule in the I-direction. RWIDTH consists of a three-byte two-part
binary number of the form AB. Both A and B are in measurement units.
• A is a two-byte binary number from -32,768 through +32,767
• B is a one-byte binary fraction with bit 0 denoting 1/2 measurement unit, bit 1 denoting 1/4 measurement
unit, and bit N denoting 1/(2
(N+1)
) measurement unit.
If RWI DTH is the default indicator , a value is obtained from the hierarchy . Please see the Pragmatics section
for further information. If the value of RWIDTH is not supported or is not within the range specified by PTOCA,
exception condition EC-8002 exists. The standard action is to ignore the control sequence and continue
presentation with the value determined according to the description given in the Pragmatics section.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163 , and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
DBR Control Sequence

<!-- Page 75 -->

PTOCA Reference 57
Pragmatics
If a width or length is specified that, when converted to pels, requires finer resolution than a device supports,
the device uses the next smaller width or length that it does support. If a specified width or length becomes
less than the finest device resolution, the device uses its finest resolution. If the value of RLENGTH or
RWIDTH is zero, no marks appear . Such resolution correction does not constitute an exception condition. If a
parameter value will cause any part of the rule to extend outside of the object space, exception condition code
EC-0103 exists. The standard actions in this case are the following:
• For extensions in the B-direction, truncate the rule at the object space boundary . Receivers using character
underscore to create rules must position, begin, or end characters in such a way as to prevent extension
beyond the limits of the object space.
• For extensions in the I-direction, limit presentation to the portion of the rule that can be presented within the
object space. If the receiver's minimum resolution will cause the object space to be exceeded, do not present
the rule.
A negative value for RLENGTH or RWIDTH reverses the direction of the corresponding extent, allowing
definition of a rule in four directions from a given presentation position. The support of negative values in the I-
direction and the B-direction is optional.
A rule of length +1 must have a diffe rent starting point from a rule of -1. The dif ference, within the tolerances of
the receiver , is equal to the receiver's finest resolution. If a parameter value is received by a receiver that does
not support it, exception condition EC-8202 exists. The standard action in this case is to ignore the control
sequence. The recommended default value for RWIDTH is receiver-dependent. For example, it may be the
width of the vertical bar character in the active font. A receiver incapable of drawing the rule may substitute a
graphic sequence that represents it.
For further information on rule positioning, please refer to Figure 1 1 on page 44.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-0103...A parameter value will cause the rule to be outside the object space.
• EC-1E01...RLENGTH is missing.
• EC-8002...The value for RWIDTH is not supported or is not in the range specified by PTOCA.
• EC-8202...The value for RLENGTH is not supported or is not in the range specified by PTOCA; or , a
parameter value is negative and the receiver cannot process it.
DBR Control Sequence

<!-- Page 76 -->

58 PTOCA Reference
Draw I-axis Rule (DIR)
The Draw I-axis Rule control sequence draws a rule in the I-direction.
Syntax
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 4, 7 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'E4' – X'E5' | Control sequence function type | M | N | N |
| 4–5 | SBIN | RLENGTH | X'8000' – X'7FFF' | Length | M | N | N |
| 6–8 | SBIN | RWIDTH | See Semantics section | Width | O | Y | Y |
a measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the
conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence specifies dimensions of a rule that extends from the current presentation position in both
the I-direction and the B-direction. The current I-axis and B-axis coordinates are not changed by this control
sequence.
The length parameter , RLENGTH, is the length of the rule in the I-direction. If RLENGTH is omitted, exception
condition EC-1E01 exists. The standard action is to treat this control sequence as a no-operation. If the value
of RLENGTH is not supported or is not within the range specified by PTOCA, exception condition EC-8202
exists. The standard action is to continue presentation with the value determined according to the description
given in the Pragmatics section.
The width parameter , RWIDTH, is the width of the rule in the B-direction.
RWIDTH consists of a three-byte two-part binary number of the form AB. Both A and B are in measurement
units.
• A is a two-byte binary number from -32,768 through +32,767
• B is a one-byte binary fraction with bit 0 denoting 1/2 measurement unit, bit 1 denoting 1/4 measurement
unit, and bit N denoting 1/(2
(N+1)
) measurement unit.
If RWI DTH is the default indicator , a value is obtained from the hierarchy . See the Pragmatics section for
further information. If the value of RWIDTH is not supported or is not within the range specified by PTOCA,
exception condition EC-8002 exists. The standard action is to continue presentation with the value determined
according to the description given in the Pragmatics section.
DIR Control Sequence

<!-- Page 77 -->

PTOCA Reference 59
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If a width or length is specified that, when converted to pels, requires finer resolution than a device supports,
the device uses the next smaller width or length that it does support. If a specified width or length becomes
less than the finest device resolution, the device uses its finest resolution. If the value of RLENGTH or
RWIDTH is zero, no marks appear . Such resolution correction does not constitute an exception condition. If a
parameter value will cause any part of the rule to extend outside of the object space, exception condition EC-
0103 exists. The standard actions in this case are the following:
• For extensions in the I-direction, truncate the rule at the object space boundary . Receivers using character
underscore to create rules must position, begin, or end characters in such a way as to prevent extension
beyond the limits of the object space.
• For extensions in the B-direction, presentation is limited to the portion of the rule that can be presented within
the object space. If the receiver's minimum resolution will cause the object space to be exceeded, do not
present the rule.
A negative value of RLENGTH or RWID TH reverses the direction of the corresponding extent, allowing
definition of a rule in four directions from a given presentation position. The support of negative values in the I-
direction and B-direction is optional.
A rule of length +1 must have a diffe rent starting point from a rule of -1. The dif ference, within the tolerances of
the receiver , is equal to the receiver's finest resolution. If a parameter value is received by a receiver that does
not support it, exception condition EC-8202 exists. The standard action in this case is to ignore the control
sequence. The recommended default value for RWIDTH is receiver-dependent. For example, it may be the
width of the underscore character in the active font. A receiver incapable of drawing the rule may substitute a
graphic sequence that represents it.
For further information on rule positioning, please refer to Figure 10 on page 44.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-0103...A parameter value will cause the rule to be outside the object space.
• EC-1E01...RLENGTH is missing.
• EC-8002...The value for RWIDTH is not supported or is not in the range specified by PTOCA.
• EC-8202...The value for RLENGTH is not supported or is not in the range specified by PTOCA; or , a
parameter value is negative and the receiver cannot process it.
DIR Control Sequence

<!-- Page 78 -->

60 PTOCA Reference
Encrypted Data (ENC)
The Encrypted Data control sequence contains a sequence of bytes that are encrypted and must be decrypted
into text strings for standard text processing. This data is not scanned for embedded control sequences.
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 7–255 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'98' – X'99' | Control sequence function type | M | N | N |
| 4-7 | | | | Reserved; should be zero | M | N | N |
| 8-256 | UNDF | ENCDATA | Not applicable | Encrypted bytes to be decrypted | M | N | N |
The data type of the contents of ENCDA T A is UNDF (undefined). ENCDA T A does not accept the default
indicator , but X'F ....F' is valid.
Semantics
This control sequence specifies a sequence of undefined encrypted bytes that must be decrypted. Once these
bytes have been decrypted using the decryption mechanism, the result is a character string whose encoding
scheme is set by the currently active font. Once decrypted, the standard rules for processing character strings
apply . If the ENCDA T A cannot be decrypted, then the alternate text is used from the Set Encrypted Alternate
(SEA) control and the standard rules for processing character strings also apply . No code point within the data
field is recognized as a Control Sequence Prefix. The current inline position is incremented for each graphic
character in the string.
For graphic characters following each other:
I
cnew
= I
c
+ ADJSTMNT + CI
Intervening non-incrementing characters are ignored.
For graphic characters following RMI, AMI, BLN control sequences or following a space character or variable
space character:
I
cnew
= I
c
+ CI
Intervening non-incrementing characters are ignored.
For the variable space character:
I
cnew
= I
c
+ VSI
For a non-incrementing character:
I
cnew
= I
c
In all cases:
B
cnew
= B
c
ENC Control Sequence

<!-- Page 79 -->

PTOCA Reference 61
Pragmatics
If the value of the LENGTH field is less than 7, exception condition EC-1E01 exists. The standard action is to
ignore the control sequence and continue processing.
When considering the exception conditions that can occur with an ENC, it is important to note that there are
three cases:
1. The ENCDA T A can be decrypted into printable code points
2. The ENCDA T A cannot be decrypted
3. The ENCDA T A cannot be decrypted and alternate text is used
What follows are the considerations for each case.
The ENCDA T A can be decrypted:
Once ENCDA T A has been decrypted, no absolute move, relative move, baseline positioning, or other
immediate or modal function is available. If code points representing control sequences are processed within
ENCDA T A, they are presented as graphic characters, and the active coded font determines whether any
character shapes appear on the presentation surface. If an Encrypted Data control sequence causes any
part of the character box of the character to exceed the object space, exception condition EC-0103 exists.
The standard action is not to present the character that exceeds the object space, and continue processing
without presenting characters until the presentation position is returned to an addressable position within the
object space that is a valid presentation position for the character being presented. Then presentation of
characters may resume. Note that when decrypted data causes EC-0103, alternate text will not be used.
Given that the length of the encrypted bytes to be decrypted can exceed the space available within a single
ENC control, a method to specify more than 249 bytes for encrypted bytes to be decrypted is provided. ENC
controls that are consecutive and part of the same control sequence chain have their ENCDA T A fields
concatenated together to form encrypted bytes that can be much longer than 249 bytes. Consecutive, in this
case, means the ENC controls have no intervening PTOCA controls between them.
The data length after decryption must be an even number for double-byte fonts. If the length of the decrypted
character string is an odd number when a double-byte font is active, exception condition EC-1A01 exists.
The standard action is to process the decrypted character string up to the last byte, skip the odd byte, and
continue processing. If alternate text has been set using the SEA control, it will not be used.
If the character encoding is Unicode but the code points in the decrypted character string are not valid
Unicode data, exception condition EC-1A03 exists. The standard action is to skip the remainder of the code
points in the ENC and continue processing. If alternate text has been set using the SEA control, it will not be
used.
The ENCDA T A cannot be decrypted:
If decryption is not available, exception condition EC-9D01 exists. If alternate text has been set using the
SEA control, then the standard action is to substitute the alternate text for the text string originally intended to
be printed. If no alternate text has been set using the SEA control, then the standard action is to ignore the
control sequence and continue processing.
Architecture Note: Some IPDS printers, under control of an environment-specific text fidelity control, will
allow reporting and continuation to be controlled when an ENC is encountered by a printer that cannot
decrypt text strings.
If decryption fails on the receiver , exception condition EC-9D02 exists. If alternate text has been set using the
SEA control, then the standard action is to substitute the alternate text for the text string originally intended to
be printed. If no alternate text has been set using the SEA control, then the standard action is to ignore the
control sequence and continue processing.
If no key information has been set for decryption using the SKIcontrol, exception condition EC-9D03 exists.
If alternate text has been set using the SEA control, then the standard action is to substitute the alternate text
ENC Control Sequence

<!-- Page 80 -->

62 PTOCA Reference
for the text string originally intended to be printed. If no alternate text has been set using the SEA control,
then the standard action is to ignore the control sequence and continue processing.
Application Note: It is recommended that a positioning control sequence (such as an AMI) follow each ENC
(or group of ENCs in the case where the ENCDA T A is concatenated together) to make sure that any
text that follows the ENCDA T A is positioned correctly (should the byte data fail to be decrypted).
The ENCDA T A cannot be decrypted and alternate text is used:
When alternate text is used, no absolute move, relative move, baseline positioning, or other immediate or
modal function is available. If code points representing control sequences are processed within AL TTEXT ,
they are presented as graphic characters, and the active coded font determines whether any character
shapes appear on the presentation surface. If the alternate text causes any part of the character box of the
character to exceed the object space, exception condition EC-0103 exists. The standard action is not to
present the character that exceeds the object space, and continue processing without presenting characters
until the presentation position is returned to an addressable position within the object space that is a valid
presentation position for the character being presented. Then presentation of characters may resume.
The data length of the alternate text must be an even number for double-byte fonts. If the length of the
alternate text is an odd number when a double-byte font is active, exception condition EC-1A01 exists. The
standard action is to process the alternate text up to the last byte, skip the odd byte, and continue
processing.
If the character encoding in the alternate text is Unicode but the code points in the alternate text character
string are not valid Unicode data, exception condition EC-1A03 exists. The standard action is to skip the
remainder of the code points in the alternate text and continue processing.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-0103...The decrypted character string or alternate text will cause part of a character's character box to be
outside the object space.
• EC-1A01...The length of the decrypted character string or alternate text is an odd number , but a double-byte
font is active.
• EC-1A03...Invalid Unicode data in the decrypted character string or alternate text. This can be caused by
one of the following:
– A high-order surrogate code value was not immediately followed by a low-order surrogate code value. The
high-order surrogate range is U+D800 through U+DBFF .
– A low-order surrogate code value was not immediately preceded by a high-order surrogate code value.
The low-order surrogate range is U+DC00 through U+DFFF .
– An illegal UTF-8 byte sequence, as defined in the Unicode 3.2 Specification, was specified. For more
information on illegal UTF-8 byte sequences, see T able 15 on page 129.
• EC-1E01...LENGTH is not valid.
• EC-9D01...Decryption is not available on this device.
• EC-9D02...Decryption reported an error .
• EC-9D03...No key information has been set for decryption.
ENC Control Sequence

<!-- Page 81 -->

PTOCA Reference 63
End Suppression (ESU)
The End Suppression control sequence marks the end of a string of presentation text suppressed from the
visible output.
#### Syntax

| Offset | Type | Name | Range | Meaning | M/O | Def | Ind |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix | M | N | N |
| 1 | CODE | CLASS | X'D3' | Control sequence class | M | N | N |
| 2 | UBIN | LENGTH | 3 | Control sequence length | M | N | N |
| 3 | CODE | TYPE | X'F4' – X'F5' | Control sequence function type | M | N | N |
| 4 | CODE | LID | X'00' – X'FF' | Suppression identifier | M | N | N |
This control sequence marks the end of a string of presentation text that has been suppressed. It works in
conjunction with the Begin Suppression control sequence. Please see “Begin Suppression (BSU)” on page 54
for information on the Begin Suppression control sequence. If the value of the LID is not supported or is not
within the range specified by PTOCA, exception condition EC-9801 exists. The standard action in this case is
to ignore this control sequence and continue presentation with the value determined according to the data-
stream hierarchy .
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
This control sequence does not change the current addressable position. In order to suppress a text string
from the visible output, the string should be bounded by a Begin Suppression control sequence and an End
Suppression control sequence that have the same values for the LID. In addition, the controlling environment
must activate the LID.
Exception Conditions
This control sequence can cause the following exception condition:
• EC-9801...The value of LID is not supported or is not in the range specified by PTOCA.
ESU Control Sequence

<!-- Page 82 -->

64 PTOCA Reference
Glyph Advance Run (GAR)
The Glyph Advance Run control sequence specifies the relative displacement along the current baseline (in
the i-direction) to the glyph origin for each glyph ID in the preceding GIR from the text position at the start of the
GLC.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 UBIN LENGTH X'04' – X'FE';
even values
only
Control sequence length.
The length is 4 + n∙(size
of ADV ANCE)
M N N
1 CODE TYPE X'8C', X'8D' Control sequence
function type
M N N
2-3 X'0000' Reserved; should be zero M N N
Zero or more advances along the baseline in the following format:
+0-1 UBIN ADV ANCE X'0000' –
X'FFFF'
Glyph advance along the
baseline
O N N
ADV ANCE is a signed binary number in measurement units. The range for ADV ANCE assumes a
measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the
conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence carries a sequence of glyph advances that correspond to the glyph IDs in the preceding
GIR. Each advance is a 2-byte unsigned displacement along the current baseline that is measured from the
text position that was defined at the start of the GLC chain to the glyph origin for each glyph ID. The
displacement is in the i-axis direction. The first advance corresponds to the first glyph ID, the second advance
corresponds to the second glyph ID, and so forth. Advances are specified using the current measurement
units.
Each GAR control sequence must be chained to a preceding GIR control sequence. It can be followed by a
chained GOR, GIR, or UCT control sequence. If it is followed by a diffe rent control sequence, the GAR
terminates the GLC chain.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9C03...Invalid sequence. The GAR is not preceded by a GIR, or , if it indicates chaining, it is not followed
by a GOR, GIR, or a UCT .
• EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was
found that is not part of a GLC chain.
• EC-9C08...Glyph Advance count mismatch. The number of glyph advances specified is not the same as the
number of glyph IDs specified in the preceding GIR.
GAR Control Sequence

<!-- Page 83 -->

PTOCA Reference 65
Glyph ID Run (GIR)
The Glyph ID Run control sequence specifies an array of glyph IDs from the current T rueT ype/OpenT ype font.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 UBIN LENGTH X'04' – X'FE';
even values
only
Control sequence length.
The length is 4 + n∙(size
of GL YPHID)
M N N
1 CODE TYPE X'8B' Control sequence
function type
M N N
2-3 X'0000' Reserved; should be zero M N N
Zero or more glyph IDs in the following format:
+0-1 UBIN GL YPHID X'0000' –
X'FFFF'
Glyph ID O N N
Semantics
This control sequence carries a sequence of glyph ids in the current font or in a font linked to the current font.
This font is identified by the font OID in the GLC. If the font is in a font collection, the OID in the GLC
corresponds to the font collection and the font name in the GLC is used to identify the font.
A GIR control sequence must be chained to a GLC, if in the first grouping, or to a preceding GAR or GOR, if in
subsequent groupings. It must be followed by a chained GAR that contains a glyph advance for each glyph ID
in this control sequence.
Pragmatics
It is possible that a text run may require more glyphs than the GIR can contain. Composition applications can
handle this by specifying multiple GIR/GAR[/GOR] groupings. If there are n glyph IDs contained in a GIR, then
the chained GAR must contain n advances and the optional GOR must contain n offsets.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9C02...Invalid glyph ID. The current font does not contain a specified glyph ID.
• EC-9C03...Invalid sequence. The GIR is not preceded by either a GLC or a GAR or a GOR.
• EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was
found that is not part of a GLC chain.
GIR Control Sequence

<!-- Page 84 -->

66 PTOCA Reference
Glyph Layout Control (GLC)
The Glyph Layout Control control sequence marks the start of one or more glyph run groupings that together
render text using arrays of glyph identifiers and positions.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 10 - (p - 1) Control sequence length M N N
3 CODE TYPE X'6D' Control sequence function
type
M N N
4–5 SBIN IADVNCE X'8000' –
X'7FFF'
The advance along the
current baseline after
processing this GLC chain.
A value of X'0000'
indicates that the current
text position is not changed
after processing the GLC
chain.
M N N
6 UBIN OIDLGTH 0, 13-129 Length of FONTOID
parameter
M N N
7 UBIN FFNLGTH 0 - (255 - (10 +
OIDLGTH)),
even values
only
Length of FFONTNME
parameter
M N N
8-1 1 X'00...00' Reserved; should be zero M N N
12-n UBIN FONTOID Object Identifier for the font
that contains the glyphs to
be rendered.
• Offset 12 – must be X'06'
• Offset 13 – length of
content bytes
• Offset 14 to n – OID
content
O N N
(n + 1) - p CHAR FFONTNME Full font name of the font
identified by the FONTOID
parameter , unless the font
is in a collection, in which
case the OID corresponds
to the collection. The name
is encoded in UTF-16BE.
O N N
IADVNCE is a signed binary number in measurement units. The range for IADVNCE assumes a measurement
unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the conversion
routine described in “Interpreting Ranges” on page 47.
IADVNCE specifies the advance along the current baseline (in the inline direction) that is caused by
processing this GLC chain. When this advance is added to the text position at the start of the GLC, it defines
GLC Control Sequence

<!-- Page 85 -->

PTOCA Reference 67
the current text position after the GLC chain has been processed. A value of X'0000' indicates that the current
text position is not changed after processing the GLC chain.
OIDLGTH specifies the length in bytes of the FONT OID parameter . A value of X'00' indicates that the
FONT OID parameter is not specified and the presentation device is to use the FONTOID specified in the
previous GLC in the text object that specified this parameter . Note that when a font matching the FONT OID is
found, it is always processed with the presentation parameters determined by the font mapping for the LID in
the last SCFL control sequence.
Application Note: When OIDLGTH = 0, the presentation device searches backwards through the PTOCA
control sequences until a GLC with an OID is found or the beginning of the text object is reached. In
MO:DCA environments, the beginning of the text object is indicated by the Begin Presentation Text
(BPT) structured field, which causes AFP presentation servers to reset the currently active font to the
presentation device default font by issuing a SCFL with LID = X'FF'. The AFP generator must therefore
always define an active font at the start of the text object by generating a SCFL control sequence that
specifies the font LID and a GLC that specifies the font OID.
FFNLGTH specifies the length in bytes of the FFONTNME parameter . A value of X'00' indicates that the
FFONTNME parameter is not specified. This parameter must be set to X'00' if the OIDLGTH parameter is set
to X'00', or exception condition EC-9C0B exists.
FONT OID specifies the OID of the font used to present the glyph runs in the GLC chain. If the font is in a font
collection, the OID is for the collection. Presentation devices must validate the OID specified in the GLC
against the current font or font collection. If the current font is linked to additional fonts, the FONT OID may refer
to the current font (the base font) or to any font linked to that base font. Only the font identified by FONT OID
will be used with the GLC chain, the other linked fonts are ignored when placing glyphs. See the Mixed Object
Document Content Architecture Reference, AFPC-0004 for the definition of the algorithm used to calculate the
OID of a T rueT ype/OpenT ype font.
FFONTNME specifies the Full Font Name (FFN) of the font identified by the FONT OID parameter , unless the
FONT OID corresponds to a font collection. If FONT OID corresponds to a font collection, the FFONTNME
parameter specifies the Full Font Name of the font in the collection. This parameter is used to search for a
specific font in a font collection if the FONT OID parameter identifies a collection. The name is encoded in UTF-
16BE. This parameter must not be specified if the FONTOID parameter is not specified; if it is, exception
condition EC-9C0B exists.
Semantics
This control sequence marks the start of a run of glyph IDs and positions contained in subsequent glyph run
control sequence groupings that make up the GLC chain. The glyph IDs in the T rueT ype/OpenT ype font
specified by the FONTOID are carried in GIR control sequences. The advances along the current baseline (in
the i-axis direction) are carried in GAR control sequences. The optional offsets from the current baseline (in the
b-axis direction) are carried in GOR control sequences. These subsequent controls must be chained to the
GLC using the PTOCA chaining rules.
The GLC control sequence must be followed by one or more GIR/GAR[/GOR] groupings. (The notation
“[/GOR]” will be used to indicate that the GOR is optional.) No other control sequences can be interspersed
within the GIR/GAR[/GOR] groupings or between them. Each GIR/GAR[/GOR] grouping causes a set of
glyphs to be rendered.
An optional UCT containing the Unicode encoded text may be chained to the last GAR[/GOR] and terminates
the chain. This UCT is not rendered. If the UCT is not specified, the last GAR[/GOR] terminates the chain.
After the GLC chain has been processed:
I
cnew
= I
c
+ IADVNCE
B
cnew
= B
c
GLC Control Sequence

<!-- Page 86 -->

68 PTOCA Reference
Pragmatics
The GIR/GAR[/GOR] groupings accumulate glyphs from the current font along a single baseline. While there is
no restriction on the order of the glyphs, it is recommended that the composition application list them
sequentially in the inline direction.
The UCT is optional and carries the Unicode text that the GIR/GAR[/GOR] groupings will present. Its use is
recommended as it provides applications the ability to interpret the sequence of glyphs rendered by the
presentation device.
The application must specify the glyph IDs, glyph advances, and glyph offsets in the same order . If there are n
glyph IDs present in a GIR, then the subsequent GAR must contain n advances, and the optional GOR must
contain n offsets.
If a text run requires more glyphs than a GIR can contain, the applications can provide multiple GIR/GAR
[/GOR] groupings chained to a single GLC.
In a GIR/GAR[/GOR] grouping, if all glyph offsets are 0, the application can choose not to specify the GOR(s).
If one glyph offset is not zero, the application must specify the GOR entries in the same order as the GIR and
GAR entries.
The introduction of support for glyph runs affects the operation of some current PTOCA control sequences.
The effects are described in the following table:
T able 1 1. Interaction of GLC chain with other control sequences
Control Sequence
Effect
Modal control sequences
Set Baseline Increment (SBI) Has no effect on GLC presentation
Set Coded Font Local (SCFL) Establishes the current font for use by GLC. When linked
fonts are used, the font specified by the SCFL is always
the base font.
Set Extended T ext Color (SEC) Specifies the foreground color used to present the glyphs
in the subsequent GLC chains
Set Inter-character adjustment (SIA) Has no effect on GLC presentation
Set Inline Margin (SIM) Has no effect on GLC presentation
Set T ext Color (STC) Specifies the foreground color used to present the glyphs
in the subsequent GLC chains
Set T ext Orientation (STO) Establishes the I-direction and B-direction used to present
the glyphs in the subsequent GLC chains
Set V ariable Space Character Increment (SVI) Has no effect on GLC presentation
Field control sequences
T emporary Baseline Move (TBM) T emporarily moves the baseline coordinate relative to the
established baseline coordinate position. As with
character-based text, glyph runs in the GLC chains are
positioned relative to this temporary baseline.
Overstrike (OVS), Underscore (USC) Have no effect on GLC presentation
Directive control sequences
Absolute Move Baseline (AMB) Moves the baseline coordinate position relative to the I-
axis.
GLC Control Sequence

<!-- Page 87 -->

PTOCA Reference 69
T able 1 1 Interaction of GLC chain with other control sequences (cont'd.)
Control Sequence
Effect
Absolute Move Inline (AMI) Moves the inline coordinate position relative to the B-axis
Begin Line (BLN) Begins a new line
Begin Suppression (BSU)/End Suppression (ESU) Causes presentation of the glyphs in all GLC chains that
are between the BSU and ESU to be suppressed if the
corresponding suppression ID is activated
Draw B-axis Rule (DBR) Has no effect on subsequent GLC presentation
Draw I-axis Rule (DIR) Has no effect on subsequent GLC presentation
No Operation (NOP) Has no effect on subsequent GLC presentation
Relative Move Baseline (RMB) Moves the baseline coordinate relative to the current
baseline coordinate position
Relative Move Inline (RMI) Moves the inline coordinate relative to the current inline
position
Repeat String (RPS) Has no effect on subsequent GLC presentation
T ransparent Data (TRN) Has no effect on subsequent GLC presentation
The following examples demonstrate various valid examples of glyph layout control chaining:
• Example 1. GLC chain without optional controls. The GLC may reference the base font, or any font linked to
the current font. Since all glyphs are positioned on the current effective baseline, the GOR control sequence
is omitted:
GLC GIR GAR <<chain ends>>
• Example 2. GLC chain with optional controls. Since one or more glyphs must be positioned with an offs et
from the baseline, the optional GOR control sequence is included in this chain. The PTOCA chain is
terminated by an optional UCT control which carries the Unicode encoded text presented by the glyph layout
control sequences:
GLC GIR GAR GOR UCT <<chain ends>>
• Example 3. GLC chain with multiple GIR/GAR[/GOR] groupings. The text required more glyphs than a single
GIR could list, so multiple GIR/GAR[/GOR] groupings are included. Since one or more glyphs in the first
grouping must be positioned with an offs et from the baseline, the optional GOR control sequence is included
in this group. The optional GOR is not needed in the second grouping, and is omitted. The PTOCA chain is
terminated by an optional UCT control which carries the Unicode encoded text presented by the glyph layout
control sequences:
GLC GIR GAR GOR GIR GAR UCT <<chain ends>>
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9C00...Font Mismatch. The object OID specified in the GLC control sequence does not match the object
OID of the current font or any font linked to the current font. If the OID matched the OID of a font collection,
the Full Font Name did not match any font in the collection.
• EC-9C01...Font format not valid for use with glyph layout control sequences. The current font is not a
T rueT ype/OpenT ype font.
• EC-9C03...Unexpected control sequence. An unexpected control sequence was encountered between the
GLC control sequence and the terminating control sequence.
GLC Control Sequence

<!-- Page 88 -->

70 PTOCA Reference
• EC-9C09...Missing font OID. The GLC specified an OIDLGTH of zero, but no previous font OID was supplied
within the text object.
• EC-9C0A...Count mismatch or invalid length. The byte count specified by the OIDLGTH and FFONTNME
parameters is not consistent with the overall control sequence length, or the OIDLGTH or FFNLGTH
parameters are outside their valid range.
• EC-9C0B...Full Font Name specified without font OID. A font OID was not specified (OIDLGTH = 0), but a
Full Font Name was specified (FFNLGTH ≠ 0).
GLC Control Sequence

<!-- Page 89 -->

PTOCA Reference 71
Glyph Offset Run (GOR)
The Glyph Of fset Run control sequence specifies an offset (relative displacement) from the current baseline (in
the b-direction) to the glyph origin for each glyph ID in the preceding GIR.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 UBIN LENGTH X'04' – X'FE';
even values
only
Control sequence length.
The length is 4 + n∙(size
of OFFSET)
M N N
1 CODE TYPE X'8E', X'8F' Control sequence
function type
M N N
2-3 X'0000' Reserved; should be zero M N N
Zero or more offsets from the current baseline in the following format:
+0-1 SBIN OFFSET X'8000' –
X'7FFF'
Glyph offset from the
current baseline
O N N
Semantics
This control sequence carries a sequence of glyph offsets that correspond to the glyph IDs in the preceding
GIR. Each offset is a 2-byte signed displacement from the current baseline in the b-axis direction to the glyph
origin for each glyph ID. Application of the offset does not change the position of the current baseline. That is,
the offset for each glyph ID is applied against the baseline that was defined at the start of the GLC chain. The
first offs et corresponds to the first glyph ID and the first glyph advance, the second offset corresponds to the
second glyph ID and the second glyph advance, and so forth. Of fsets are specified using the current
measurement units. A positive offset is measured towards the i-axis; a negative offset is measured away from
the i-axis.
Architecture Note: The direction in which GOR offsets are measured, as indicated by the sign of the offset, is
opposite to the direction in which increments in a RMB are measured, as indicated by the sign of the
increment.
The GOR control sequence is optional, but if present, must be chained to a GAR. It can be followed by a
chained GIR or UCT . Composition applications must specify the same number of glyph offsets as glyph
advances so that presentation devices can offs et the correct glyph.
Pragmatics
If a composition application needs to offset one glyph, it must offset all the glyphs identified in the preceding
GIR.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9C03...Invalid sequence. The GOR is not preceded by a GAR, or , if it indicates chaining, is not followed
by a GIR or a UCT .
• EC-9C06...GIR, GAR, or GOR control sequence found outside of a GLC chain. A GIR, GAR, or GOR was
found that is not part of a GLC chain.
• EC-9C08...Glyph Of fset count mismatch. The number of glyph offsets specified is not the same as the
number of glyph IDs specified in the preceding GIR.
GOR Control Sequence

<!-- Page 90 -->

72 PTOCA Reference
GOR Control Sequence

<!-- Page 91 -->

PTOCA Reference 73
No Operation (NOP)
The No Operation control sequence has no effe ct on presentation.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 2–255 Control sequence length M N N
3 CODE TYPE X'F8' – X'F9' Control sequence
function type
M N N
4–256 UNDF IGNDA T A Not
applicable
Ignored data O N N
Semantics
This control sequence specifies a string of bytes that are to be ignored.
Exception Condition
This control sequence can cause the following exception condition:
• None
NOP Control Sequence

<!-- Page 92 -->

74 PTOCA Reference
Overstrike (OVS)
The Overstrike control sequence identifies text that is to be overstruck with a specified character .
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 5 Control sequence length M N N
3 CODE TYPE X'72' – X'73' Control sequence
function type
M N N
4 BITS BYPSIDEN See
Semantics
Bypass identifiers M Y Y
5–6 CODE OVERCHAR X'0000' –
X'FFFF'
Overstrike identifiers M N N
BYPSIDEN is a flag byte with no measurement units. The PTOCA default for BYPSIDEN is X'01'. OVERCHAR
is defined as a one-byte or two-byte code point that, when coupled with the active coded font, specifies the
character to be used for overstriking. Single-byte code points are loaded in byte 6. OVERCHAR does not
accept the default indicator .
Semantics
Overstrike is accomplished with a pair of Overstrike control sequences. A beginning OVS with a non-zero
value in BYPSIDEN bits 4-7 activates overstrike. An ending OVS with a zero value in BYPSIDEN bits 4-7
deactivates overstrike. The beginning OVS control sequence immediately precedes the field of text to be
overstruck. It specifies the following:
• The overstrike character
• How to place the overstrike characters in relation to the characters in the text field
• Which controlled inline white space is to be overstruck
The text field to be overstruck, called the overstrike field, is delimited by the beginning OVS and either an
ending OVS control sequence or the end of the Presentation Text object. The overstrike field is a sequential
string of presentation text.
BYPSIDEN specifies which controlled inline white space within the overstrike field is to be overstruck.
Controlled inline white space is that area of the presented line that contains no visible material due to
movement of the presentation position in the I-direction caused by the following:
• Absolute Move Inline control sequence
• Relative Move Inline control sequence
• Space character or variable space character
Application Note: The following code points are normally used for the variable space character:
• X'40' in EBCDIC single-byte code pages
• X'20' in ASCII single-byte code pages
• X'4040' in EBCDIC double-byte code pages
• X'2020' in ASCII double-byte code pages
OVS Control Sequence

<!-- Page 93 -->

PTOCA Reference 75
The following code points are used for the variable space character in T rueT ype/OpenT ype fonts that
use a Unicode (UTF-16) encoding:
• X'0020'
• X'00A0'
Movement of the current inline position in the I-direction to or through a presentation position that already
contains material to be overstruck creates controlled inline white space for the entire move in the I-direction.
Not all inline white space is controlled.
White space resulting from non-printing characters (other than space characters or variable space characters)
within the character set, from substitution of non-printing characters for unsupported characters, from
intercharacter adjustment, or from the inline margin is not considered controlled inline white space.
Inline moves that cause the current addressable position to move in a direction opposite to the I-direction, that
is, negative inline moves, do not cause overstrike.
BYPSIDEN is bit-encoded as follows.
BIT MEANING
0-3 Reserved, that is, set to B'0' by generators and ignored by receivers
4 Bypass Relative Move Inline
5 Bypass Absolute Move Inline
6 Bypass space characters and variable space characters
7 No Bypass in Effect
Bits 0-3, Reserved
Reserved bits are set to B'0' by generators and ignored by receivers.
Bit 4, Bypass Relative Move Inline
A value of B'0' in this bit indicates that the controlled white space generated as a result of a Relative Move
Inline control sequence is to be overstruck. A value of B'1' in this bit indicates that such controlled white space
is not to be overstruck. It should be bypassed.
Bit 5, Bypass Absolute Move Inline
A value of B'0' in this bit indicates that the controlled white space generated as a result of an Absolute Move
Inline control sequence is to be overstruck. A value of B'1' in this bit indicates that such controlled white space
is not to be overstruck. It should be bypassed.
Bit 6, Bypass space characters
A value of B'0' in this bit indicates that the controlled white space generated as a result of space characters or
variable space characters is to be overstruck. A value of B'1' in this bit indicates that such controlled white
space is not to be overstruck. It should be bypassed.
Bit 7, No Bypass in Effect
A value of B'0' in this bit activates the other bypass flags. A value of B'1' in this bit indicates that the other
bypass flags are overridden, and that all text and white space bounded by the OVS pair should be overstruck.
If the value of BYPSIDEN is the default indicator , a value is obtained from the hierarchy . Please see T able 9 on
page 35.
Implementation Note: Most IPDS printers have implemented X'FF' as the default indicator , which results in
BYPSIDEN = X'01' - no bypass in effe ct. However , it could be argued that the proper default indicator is
OVS Control Sequence

<!-- Page 94 -->

76 PTOCA Reference
X'0F', since BYPSIDEN bits 0-3 are reserved, should be set to zero by generators, and should be
ignored by receivers. T o avoid confusion, it is strongly recommended that a default indicator not be used
for this parameter , and that the value X'01' is specified directly if the default is desired.
An overstrike area is that portion of the overstrike field for which text is actually overstruck. An overstrike area
is delimited by the addressable position in the following cases.:
• A beginning OVS
• Either end of bypassed controlled inline white space
• Either end of a baseline move, which may be for the established baseline or for a temporary baseline
• The beginning of negative changes in the presentation position caused by inline moves or negative
intercharacter adjustments
• Boundaries where violation causes truncation
• An ending OVS
• The end of the Presentation Text object
Additionally , the dimension in the positive I-direction of the overstrike field is defined by the minimum and
maximum I-coordinates specified between the overstrike area delimiters. White space resulting from the
application of the inline margin is overstruck only if this area is entered by means of an inline move.
Overstrike characters are presented side by side without regard to the position of the characters being
overstruck. Intercharacter adjustments are not applied to the placement of overstrike characters. The number
of overstrike characters required for each overstrike area within an overstrike field is equal to the inline
dimension of the overstrike area divided by the character increment of the overstrike character . If the result of
this division is less than one, one overstrike character must be presented in the overstrike area. If the result of
this division is greater than one and is not an integer , the decision to place an overstrike character is based on
rounding to the nearest integer . Normally this rounding is down to the next lower integer , except as specified in
the Pragmatics section.
If the value of OVERCHAR is not supported or is not within the range specified by PTOCA, an exception
condition exists. See the Pragmatics section for the exception condition code and the standard action.
Pragmatics
The intent of the semantic is to distribute the overstrike characters evenly in the overstrike area without
violating the delimiters of the overstrike area, that is, the I-coordinates at the beginning and end of the
overstrike area.
Presentation of a portion of an overstrike character is acceptable to avoid overlapping characters.
Characters are overstruck using the current baseline. That is, the overstrike function follows the current
baseline even when the baseline is changed by a T emporary Baseline Move control sequence.
OVERCHAR is defined as a one-byte or two-byte code point that, when coupled with the active coded font,
specifies the character to be used for overstriking. Single-byte code points are located in byte 6.
The selected overstrike character must be a printable character and must specify a positive, non-zero
character increment. If these conditions are not met by the selected overstrike character , exception condition
EC-9A01 exists. T o avoid an overflow of the overstrike field by the last overstrike character , the character
increment of the overstrike character should also be equal to or greater than the character box size. If this is
not true, exception condition EC-9A01 may optionally be detected.
Multiple beginning and ending Overstrike pairs may not be nested. However , no exception condition exists if a
beginning OVS is processed when another OVS is already active. The subsequent beginning OVS terminates
the previous OVS sequence and starts another . If an ending OVS is encountered when there has been no
previous OVS in the Presentation Text object, no exception condition exists. Ignore the ending OVS. If a
OVS Control Sequence

<!-- Page 95 -->

PTOCA Reference 77
Presentation Text object contains a beginning OVS without a matching ending OVS, no exception condition
exists. T erminate the OVS at the end of the Presentation Text object.
There is no provision in this control sequence to specify a coded font, and Set Coded Font Local control
sequences that occur within the overstrike field do not affect the appearance of the overstrike character . If the
desired overstrike character is not defined in the active font which is controlling the text presentation, the
beginning Overstrike control sequence must be preceded by a Set Coded Font Local control sequence that
specifies a different coded font that contains the overstrike character . In this case, the coded font controlling
the text presentation must be re-established following the ending Overstrike control sequence. If the graphic
character specified in the OVS control sequence is not valid in the active coded font, exception condition EC-
2100 exists. The standard action is to use a device default character as the overstrike character . If the graphic
character does not have a rotation available that is equivalent to the current character rotation, exception
condition EC-3F02 exists. The standard action is to accomplish the overstrike function to the best of the
receiver's ability .
There are no syntactic restrictions on the occurrence of Begin Line, Absolute Move Baseline, Relative Move
Baseline, and T emporary Baseline Move control sequences within the overstrike field.
Color is not a parameter of this control sequence.
Utilization of algorithms to reposition the overstrike characters within the overstrike areas of an overstrike field
is restricted in several ways.
• At least one overstrike character must occur in an overstrike area.
• The overstrike characters must be positioned relative to the delimiters of the overstrike area, rather than to
the last overstrike character in any previous overstrike area.
• Overlap of any portion of the B-space of the overstrike character with the B-space of a character not within
the overstrike area is considered unacceptable, except in the case of a single character .
• Overlap of any portion of the B-space of the overstrike character with the B-space of another overstrike
character within the overstrike field should be avoided. If overlap occurs, it should not be allowed to exceed
1/4 the B-space of either of the characters.
• Rounding of f the number of overstrike characters is permitted. The minimum required support is to round of f
to the nearest integer number of overstrike characters in the overstrike area. This overlap may be allowed at
a change of baseline, except when the overlap causes a portion of the character box to extend beyond a
boundary of the object space.
• Multiple passes over the same portions of the presentation space through the use of AMI and RMIcontrol
sequences are not restricted.
• An area of zero length is not considered to be a valid overstrike area.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-2100...The graphic character specified is not valid in the active font.
• EC-3F02...The graphic character specified does not have a rotation available that is equivalent to the current
character rotation.
• EC-9A01...The graphic character specified has an invalid character increment or is not a printable character .
OVS Control Sequence

<!-- Page 96 -->

78 PTOCA Reference
Relative Move Baseline (RMB)
The Relative Move Baseline control sequence moves the baseline coordinate relative to the current baseline
coordinate position.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4 Control sequence length M N N
3 CODE TYPE X'D4' – X'D5' Control sequence
function type
M N N
4–5 SBIN INCRMENT X'8000' –
X'7FFF'
Increment M N N
INCRMENT is a signed binary number in measurement units. It does not accept the default indicator . The
range for this parameter assumes a measurement unit of 1/1440 inch. If it is necessary to convert to a diffe rent
measurement unit, please see the conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence specifies an increment, INCRMENT , in the B-direction from the current baseline
coordinate position to a new baseline coordinate position. After execution of this control sequence,
presentation is resumed at the new baseline coordinate position. A positive value causes movement in the B-
direction, while a negative value causes movement toward the I-axis. This control sequence does not modify
the current inline coordinate position.
I
cnew
= I
c
B
cnew
= B
c
+ INCRMENT where 0 <= B
cnew
<= B-extent
If the value of INCRMENT is not supported or is not within the range specified by PTOCA, exception condition
EC-1601 exists. The standard action is to continue presentation according to the description given in the
Pragmatics section.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If the value of INCRMENT is zero, the addressable position is not displaced, and any intercharacter increment
is not applied. If a move is to a presentation position for which the character's character box will exceed the
object space and an attempt is made to present there, exception condition EC-0103 exists. The standard
action is to refrain from presenting the character that exceeds the object space, and to continue processing
without presenting characters until the presentation position occupies a valid addressable position for the
character being presented. Then presentation of characters may resume. PTOCA does not constrain
advancement of the baseline coordinate in the negative B-direction, that is, toward the I-axis. However , a
constraint of this type may be imposed by the subset level or by the receiver . If this constraint is applied and
RMB Control Sequence

<!-- Page 97 -->

PTOCA Reference 79
INCRMENT is negative, exception condition EC-1403 exists. The standard action is to ignore the control
sequence.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1601...The value of INCRMENT is not supported or is not in the range specified by PTOCA.
• EC-0103..The presentation position is outside the object space and presentation is attempted.
• EC-1403...Negative INCRMENT is not valid.
RMB Control Sequence

<!-- Page 98 -->

80 PTOCA Reference
Relative Move Inline (RMI)
The Relative Move Inline control sequence moves the inline coordinate of the presentation position relative to
the current inline position.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4 Control sequence length M N N
3 CODE TYPE X'C8' – X'C9' Control sequence
function type
M N N
4–5 SBIN INCRMENT X'8000' –
X'7FFF'
Increment M N N
INCRMENT is a signed binary number in measurement units. It does not accept the default indicator . The
range for this parameter assumes a measurement unit of 1/1440 inch. If it is necessary to convert to a diffe rent
measurement unit, please see the conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence specifies an increment, INCRMENT , in the I-direction from the current inline coordinate
position to a new inline coordinate position. After execution of this control sequence, presentation is resumed
at the new inline coordinate position. A positive value is in the direction of line growth, while a negative value
logically backspaces. This control sequence does not modify the current baseline coordinate position.
I
cnew
= I
c
+ INCRMENT where 0 <= I
cnew
<= I-extent
B
cnew
= B
c
If the value of INCRMENT is not supported or is not within the range specified by PTOCA, exception condition
EC-1501 exists. The standard action is to continue presentation according to the description given in the
Pragmatics section.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If the value of INCRMENT is zero, the addressable position is not moved, and any intercharacter adjustment is
not applied. If a move is to a presentation position for which the character's character box will exceed the
object space and an attempt is made to present there, exception condition EC-0103 exists. The standard
action is to refrain from presenting the character that exceeds the object space, and continue processing
without presenting characters until the presentation position occupies a valid addressable position for the
character being presented. Then presentation of characters may resume.
Exception Conditions
This control sequence can cause the following exception conditions:
RMI Control Sequence

<!-- Page 99 -->

PTOCA Reference 81
• EC-1501...The value of INCRMENT is not supported or is not in the range specified by PTOCA.
• EC-0103..The presentation position is outside the object space and presentation is attempted.
RMI Control Sequence

<!-- Page 100 -->

82 PTOCA Reference
Repeat String (RPS)
Syntax
The Repeat String control sequence contains a string of graphic character code points that is repeated on the
current line.
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4–255 Control sequence length M N N
3 CODE TYPE X'EE' – X'EF' Control sequence
function type
M N N
4–5 UBIN RLENGTH 0–32,767 Repeat length M N N
6–256 CHAR RPTDA T A Not
applicable
Repeated data O N N
The contents of RPTDA T A are unknown. RLENGTH is a binary number expressing byte count. RLENGTH and
RPTDA T A do not accept the default indicator .
Semantics
This control sequence specifies a string of bytes that is to be processed entirely as graphic character code
points. No code point is recognized as a Control Sequence Prefix. Current inline position is incremented for
each graphic character specified by a code point in the string. The baseline position is not changed.
For graphic characters following each other:
I
cnew
= I
c
+ intercharacter adjustment + character increment
Intervening non-incrementing characters are ignored.
For graphic characters following RMI, AMI, or BLN control sequences or following a space character or
variable space character:
I
cnew
= I
c
+ character increment
Intervening non-incrementing characters are ignored.
For the variable space character:
I
cnew
= I
c
+ VSI
For non-incrementing characters:
I
cnew
= I
c
In all cases:
B
cnew
= B
c
RPTDA T A is repeated until it fills the number of bytes specified by RLENGTH. If the number of bytes specified
by RLENGTH is not an integral multiple of the length of the data, a portion of the data is truncated. If the
number of bytes specified by the value of RLENGTH is less than the length of the data, a portion of the data is
truncated. If the number of bytes specified by RLENGTH is equal to the data provided, the Repeat String
control sequence has the same function as the T ransparent Data control sequence.
When a double-byte font is active and the length of RPTDA T A is an odd number , exception condition EC-1A01
exists. The standard action is to ignore the Repeat String control sequence and continue processing. When a
double-byte font is active and RLENGTH is an odd number , exception condition EC-1B01 exists. The standard
RPS Control Sequence

<!-- Page 101 -->

PTOCA Reference 83
action is to ignore the Repeat String control sequence and continue processing. If the value of RLENGTH is
not supported or is not within the range specified by PTOCA, exception condition EC-1901 exists. The
standard action is to ignore the control sequence and continue presenting.
The subset may limit the range permitted in this control sequence. For detailed information about function
subsets, please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”,
on page 163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for
data-stream documentation.
Pragmatics
The standard action value for RLENGTH is the length of RPTDA T A. If any part of a character's character box
will exceed the object space as a result of the Repeat String control sequence and an attempt is made to
present the string of characters, exception condition EC-0103 exists. The standard action is not to present the
character that exceeds the object space and to continue processing without presenting graphic characters until
the presentation position is returned to an addressable position that is a valid presentation position for the
character being presented.
If the value of the control sequence length parameter is four , indicating a length of four bytes, and the value of
RLENGTH is zero, that is, there are no data bytes, this control sequence has the effect of a no-operation. If the
value of the control sequence length parameter is greater than four , that is, data bytes are provided, and if the
value of RLENGTH is zero, no exception condition exists and the data bytes are ignored. If, however , the value
of the control sequence length parameter is four and RLENGTH is not zero, exception condition EC-1F01
exists. The standard action is to ignore this control sequence and continue processing.
If the character encoding is Unicode but the code points in the RPS are not valid Unicode data, exception
condition EC-1A03 exists. The standard action is to skip the remainder of the code points in the RPS, repeat
only the valid code point sequence, and continue processing.
Architecture Note: If the length of RLENGTH is the same as the length of RPTDA T A, the RPS control
sequence has the same effect as the T ransparent Data control sequence. It is recommended that the
RPS control sequence not be used in this manner , and that the TRN control sequence be used instead.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1A01...A double-byte font is active and the length of RPTDA T A is an odd number .
• EC-1A03...Invalid Unicode data. This can be caused by one of the following:
– A high-order surrogate code value was not immediately followed by a low-order surrogate code value. The
high-order surrogate range is U+D800 through U+DBFF .
– A low-order surrogate code value was not immediately preceded by a high-order surrogate code value.
The low-order surrogate range is U+DC00 through U+DFFF .
– An illegal UTF-8 byte sequence, as defined in the Unicode 3.2 Specification, was specified. For more
information on illegal UTF-8 byte sequences, see T able 15 on page 129.
• EC-1B01...A double-byte font is active and RLENGTH is an odd number .
• EC-1901...The value of RLENGTH is not supported or is not in the range specified by PTOCA.
• EC-0103...A parameter value will cause part of a character's character box to be outside the object space,
and presentation is attempted.
• EC-1F01...The control sequence length parameter is four and RLENGTH is not zero.
RPS Control Sequence

<!-- Page 102 -->

84 PTOCA Reference
Set Baseline Increment (SBI)
The Set Baseline Increment control sequence specifies the increment to be added to the current baseline
coordinate when a Begin Line control sequence is executed. This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4 Control sequence length M N N
3 CODE TYPE X'D0' – X'D1' Control sequence
function type
M N N
4–5 SBIN INCRMENT X'0000' –
X'7FFF'
Increment M Y Y
INCRMENT is a positive binary number expressed in measurement units. The range for this parameter
assumes a measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit,
please see the conversion routine described in “Interpreting Ranges” on page 47. The PTOCA default value for
INCRMENT should be the Default Baseline Increment of the default coded font for the device.
Semantics
This control sequence specifies an increment, INCRMENT , in the positive B-direction from the current baseline
coordinate position to a new established baseline coordinate position for subsequent presentation text in the
current Presentation Text object. INCRMENT is applied when a Begin Line control sequence is executed. If the
value of INCRMENT is not supported or is not within the range specified by PTOCA, exception condition EC-
1 101 exists. The standard action is to ignore this control sequence and continue presentation with the value
determined according to the hierarchy . If the value of INCRMENT is the default indicator , a value is obtained
from the hierarchy . Please see T able 9 on page 35.
If INCRMENT is omitted, the standard action is to make no change to the existing Baseline Increment
parameter .
If this control sequence is omitted, the Baseline Increment initial text condition parameter in the Presentation
T ext Data Descriptor (PTD) is used. If this initial text condition is not specified, a receiver default based on the
receiver default font should be used.
Note: The baseline increment, whether specified by the SBIcontrol sequence, by the Baseline Increment initial
text condition, or by a receiver default, is not affe cted by the active font or by changes to the active font.
Implementation Note: Most IPDS printers use a default baseline increment of 240/1440 inch = 1/6 inch if this
parameter is not specified in an SBIcontrol sequence or as an initial text condition in the PTD.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
SBI Control Sequence

<!-- Page 103 -->

PTOCA Reference 85
Pragmatics
The baseline coordinate position is incremented by INCRMENT after each Begin Line control sequence is
processed. If the value of INCRMENT is zero, each line appears superimposed over the preceding line.
This control sequence overrides the Baseline Increment initial text condition parameter that may occur in the
Presentation Text Data Descriptor .
Exception Conditions
This control sequence can cause the following exception condition:
• EC-1 101...The value of INCRMENT is not supported or is not in the range specified by PTOCA.
SBI Control Sequence

<!-- Page 104 -->

86 PTOCA Reference
Set Coded Font Local (SCFL)
The Set Coded Font Local control sequence activates a coded font and specifies the character attributes to be
used. This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 3 Control sequence length M N N
3 CODE TYPE X'F0' – X'F1' Control sequence
function type
M N N
4 CODE LID X'00' – X'FE' Local identifier M Y Y
The PTOCA default value for the LID is X'00'.
Semantics
This control sequence specifies a local identifier , LID, which is used by the controlling environment to access a
coded font for presentation of subsequent text in the current Presentation Text object. The current presentation
position is not changed by this control sequence. If the value of the LID is the default indicator , a value is
obtained from the hierarchy . Please see T able 9 on page 35.
If the LID is omitted, exception condition EC-1E01 exists. The standard action in this case is to ignore the
control sequence and continue presentation with the active font determined according to the hierarchy . If the
value of the LID is not supported or is not within the range specified by PTOCA, exception condition EC-0C01
exists. The standard action is to ignore the control sequence and continue presentation with the active coded
font determined according to the hierarchy .
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
The LID is equated to a coded font, character rotation, and font modification parameters by a mapping function
in the controlling environment. Please see “Related Publications” on page vi and “Font Concepts” on page 13
for font documentation.
PTOCA expects the local identifier , LID, to be mapped to a global identifier . For example, this mapping could
be accomplished in the following ways:
• The receiver provides internal mapping, using device defaults.
• The controlling environment provides the mapping to the receiver .
If a mapping is not provided, exception condition EC-1802 exists. The standard action is to ignore the control
sequence, substitute a coded font determined according to the hierarchy , and continue processing. If the
coded font specified by the mapping is not available to the receiver , exception condition EC-1802 exists. The
standard action in this case is to substitute a coded font, determined according to the hierarchy , for the
SCFL Control Sequence

<!-- Page 105 -->

PTOCA Reference 87
specified coded font and continue processing. The PTOC A parameter specification hierarchy is defined in
T able 9 on page 35.
If the text orientation is changed and the specified coded font is not compatible with the new orientation, when
an attempt is made to present a character from the selected coded font, exception condition EC-3F02 exists.
The standard action is to skip the presentation at this presentation position and use the active coded font
determined according to the hierarchy . This results in the best possible presentation within the receiver's
capability . In this case, any intercharacter adjustment is not applied.
The measurement units used by the medium and those used by the coded font selected for presentation are
assumed to be compatible. If they are not compatible, the standard action, when not superseded by the
controlling environment, is to present a best fit of the character and continue processing. A best fit could result
in no mark or unintelligible marks on the presentation surface. However , incompatible measurement units are
not an exception condition in PTOCA.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1E01...The LID is missing.
• EC-0C01...The value of the LID is not supported or is not in the range specified by PTOCA.
• EC-1802...A font mapping has not been provided.
• EC-1802...The coded font specified by the mapping is not available to the receiver .
• EC-3F02...The specified coded font is not compatible with the text orientation.
SCFL Control Sequence

<!-- Page 106 -->

88 PTOCA Reference
Set Encrypted Alternate (SEA)
The Set Encrypted Alternate control sequence contains the alternate text as a series of code points to be used
if the decryption of the encrypted bytes in the ENC control fails. This data is not scanned for embedded control
sequences. Alternate text will be used when the encrypted bytes in ENCDA T A cannot be decrypted, either
because: decryption is not available on the target device, the decryption fails during processing, or the
KEYINFO key information required for decryption has not been defined using the Set Key Information (SKI)
control.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 6, 7–255 Control sequence length M N N
3 CODE TYPE X'9C' – X'9D' Control sequence
function type
M N N
4-7 Reserved; should be zero M N N
8-256 CHAR AL TTEXT Not
applicable
Alternate text to be used
if decryption fails
O N N
The contents of AL TTEXT (if specified) are a character string of code points. AL TTEXT does not accept the
default indicator , but X'F ....F' is valid.
Semantics
Alternate text is processed when an ENC decryption cannot be performed and AL TTEXT is substituted (see
the ENC Semantics for a description of the details).
Pragmatics
Given that the length of the alternative text can exceed the space available within a single SEA control, a
method to specify more than 249 bytes for alternate text is provided. SEA controls that are consecutive and
part of the same control sequence chain have their AL TTEXT fields concatenated together to form alternate
text that can be much longer than 249 bytes. Consecutive, in this case, means the SEA controls have no
intervening PTOCA controls between them.
Application Note: The Set Encrypted Alternate (SEA) control sequence is modal, but is not an initial text
condition. For text major text, the SEA is not changed by AFP presentation servers when a MO:DCA
BPT structured field is encountered, and therefore later text on the same page will inherit any SEA set
previously on the page.
AL TTEXT is initially set to no value, meaning that in the event of a failure to decrypt the encrypted data, no text
will be substituted. If AL TTEXT is defined using a SEA control, it can be reset back to no value by specifying an
SEA with the AL TTEXT field omitted (LENGTH = 6). An SEA with LENGTH=6 resets the AL TTEXT back to no
value even if the SEA has other consecutive SEAs either before or after it. Consecutive SEAs before it, if any ,
are eff ectively ignored, while consecutive SEAs after it, if any , begin a new AL TTEXT definition.
The alternate text specified by AL TTEXT will not be verified until it is used by the ENC control. Refer to the
description of the ENC control on page 62 for a description of exception conditions that can occur within
AL TTEXT data.
SEA Control Sequence

<!-- Page 107 -->

PTOCA Reference 89
If the value of the LENGTH field is less than 6, exception condition EC-1E01 exists. The standard action is to
ignore the control sequence and continue processing.
Exception Conditions
This control sequence can cause the following exception condition:
• EC-1E01...LENGTH is not valid.
SEA Control Sequence

<!-- Page 108 -->

90 PTOCA Reference
Set Extended T ext Color (SEC)
The Set Extended T ext Color control sequence specifies a color value and defines the color space and
encoding for that value. The specified color value is applied to foreground areas of the text presentation space.
Foreground areas consist of the following:
• The stroked and filled areas of solid text characters, including overstrike characters; with hollow characters,
only the stroked portion of the character is considered foreground.
• The stroked area of a rule
• The stroked area of an underscore
All other areas of the text presentation space are considered background.
This is a modal control sequence.
Note: Colors may be specified using the Set T ext Color (STC) or the Set Extended T ext Color (SEC) control
sequences. Both STC and SEC can coexist in the same text object. The last issued control sequence
determines the current text color in accordance with the rules defined for modal control sequences. For
a definition of modal control sequences, see “Modal Control Sequences” on page 37.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 14–16 Control sequence length M N N
3 CODE TYPE X'80' – X'81' Control sequence
function type
M N N
4 Reserved; should be zero M N N
5 CODE COLSPCE Color space M N N
X'01'
X'04'
X'06'
X'08'
X'40'
RGB
CMYK
Highlight color space
CIELAB
Standard OCA color
space
6–9 Reserved; should be zero M N N
10 UBIN COLSIZE1 X'01' – X'08',
X'10'
Number of bits in
component 1, see color
space definitions
M N N
1 1 UBIN COLSIZE2 X'00' – X'08' Number of bits in
component 2, see color
space definitions
M N N
12 UBIN COLSIZE3 X'00' – X'08' Number of bits in
component 3, see color
space definitions
M N N
SEC Control Sequence

<!-- Page 109 -->

PTOCA Reference 91
Offset T ype Name Range Meaning M/O Def Ind
13 UBIN COLSIZE4 X'00' – X'08' Number of bits in
component 4, see color
space definitions
M N N
14–(n-1) COL V ALUE See
Semantics
for details
Color specifications M N N
Semantics
COLSPCE is a code that defines the color space and the encoding for the color specification. If the color space
is invalid or unsupported, exception condition EC-0E02 exists. The standard action is to use the device default
color .
V alue Description
X'01' RGBcolor space. The color value is specified with three components. Components 1, 2, and 3
are unsigned binary numbers that specify the red, green, and blue intensity values, in that
order . COLSIZE1, COLSIZE2, and COLSIZE3 are non-zero and define the number of bits
used to specify each component. COLSIZE4 is reserved and should be set to zero. The
intensity range for the R, G, and Bcomponents is 0 to 1, which is mapped to the binary value
range 0 to (2
ColSizeN
- 1), where N=1,2,3.
Architecture Note: The reference white point and the chromaticity coordinates for RGB are
defined in SMPTE RP 145-1987 entitled Color Monitor Colorimetry and RP 37-1969
entitled Color T emperature for Color T elevision Studio Monitors, respectively . The
reference white point is commonly known as Illuminant D
6500
or simply D65. The R, G,
and Bcomponents are assumed to be gamma-corrected (nonlinear) with a gamma of
2.2.
X'04' CMYK color space. The color value is specified with four components. Components 1, 2, 3,
and 4 are unsigned binary numbers that specify the cyan, magenta, yellow , and black intensity
values, in that order . COLSIZE1, COLSIZE2, COLSIZE3, and COLSIZE4 are non-zero and
define the number of bits used to specify each component. The intensity range for the C, M, Y ,
and K components is 0 to 1, which is mapped to the binary value range 0 to (2
ColSizeN
- 1),
where N=1,2,3,4. This is a device-dependent color space.
X'06' Highlight color space. This color space defines a request for the presentation device to
generate a highlight color . The color value is specified with one to three components.
Component 1 is a two-byte unsigned binary number that specifies the highlight color number .
The first highlight color is assigned X'0001', the second highlight color is assigned X'0002',
and so on. The value X'0000' specifies the presentation device default color . COLSIZE1 =
X'10' and defines the number of bits used to specify component 1.
Component 2 is an optional one-byte unsigned binary number that specifies a percent
coverage for the specified color . Percent coverage can be any value from 0% to 100% (X'00'–
X'64'). The number of distinct values supported is device-dependent. If the coverage is less
than 100%, the remaining coverage is achieved with color of medium. COLSIZE2 = X'00' or
X'08' and defines the number of bits used to specify component 2. A value of X'00' indicates
that component 2 is not specified in the color value, in which case the architected default for
percent coverage is 100%. A value of X'08' indicates that component 2 is specified in the color
value.
Component 3 is an optional one-byte unsigned binary number that specifies a percent
shading, which is a percentage of black that is to be added to the specified color . Percent
shading can be any value from 0% to 100% (X'00'–X'64'). The number of distinct values
supported is device-dependent. If percent coverage and percent shading are specified, the
effe ctive range for percent shading is 0% to (100-coverage)%. If the sum of percent coverage
SEC Control Sequence

<!-- Page 110 -->

92 PTOCA Reference
plus percent shading is less than 100%, the remaining coverage is achieved with color of
medium. COLSIZE3 = X'00' or X'08' and defines the number of bits used to specify component
3. A value of X'00' indicates that component 3 is not specified in the color value, in which case
the architected default for percent shading is 0%. A value of X'08' indicates that component 3
is specified in the color value.
Implementation Note: The percent shading parameter is currently not supported in AFP
environments.
If the percent value for component 2 or component 3 is invalid, exception condition EC-0E04
exists. The standard action is to use the maximum valid percent value.
COLSIZE4 is reserved and should be set to zero. This is a device-dependent color space.
Architecture Notes:
1. The color that is rendered when a highlight color is specified is device dependent. For
presentation devices that support colors other than black, highlight color values in the
range X'0001' to X'FFFF' may be mapped to any color . For bi-level devices, the color may
be simulated with a graphic pattern. In addition, presentation devices may not support the
% coverage and % shading parameters for highlight colors in PTOCA text. In that case,
these parameters are simulated with 100% coverage and 0% shading, respectively .
2. If the specified highlight color is 'presentation device default', devices whose default color
is black use the percent coverage parameter , which is specified in component 2, to render
a percent shading.
3. On printing devices, the color of medium is normally white, in which case a coverage of n
% results in adding (100-n)% white to the specified color , or tinting the color with (100-n)%
white. Display devices may assume the color of medium to always be white and use this
algorithm to render the specified coverage.
4. The highlight color space can also specify indexed colors when used in conjunction with a
Color Mapping T able (CMT) or an Indexed (IX) Color Management Resource (CMR).
When used with an Indexed CMR, component 1 specifies a two-byte value that is the
index into the CMR, and components 2 and 3 are ignored . Note that when both a CMT
and Indexed CMRs are used, the CMT is always accessed first. T o preserve compatibility
with existing highlight color devices, indexed color values X'0000' – X'00FF' are reserved
for existing highlight color applications and devices. That is, indexed colors values in the
range X'0000' – X'00FF', assuming they are not mapped to a diff erent color space in a
CMT , are mapped directly to highlight colors. Indexed color values in the range X'0100' –
X'FFFF', assuming they are not mapped to a diff erent color space in a CMT , are used to
access Indexed CMRs. For a description of the Color Mapping T able and Indexed CMRs
in MO:DCA environments, see the Mixed Object Document Content Architecture
Reference, AFPC-0004.
X'08' CIELABcolor space. The color value is specified with three components. Components 1, 2,
and 3 are binary numbers that specify the L, a, b values, in that order , where L is the
luminance and a and b are the chrominance dif ferences. Component 1 specifies the L value
as an unsigned binary number; components 2 and 3 specify the a and b values as signed
binary numbers. COLSIZE1, COLSIZE2, and COLSIZE3 are non-zero and define the number
of bits used to specify each component. COLSIZE4 is reserved and should be set to zero. The
range for the L component is 0 to 100, which is mapped to the binary value range 0 to (2
ColSize1
- 1). The range for the a and b components is -127 to +127, which is mapped to the binary
range -(2
ColSizeN-1
- 1) to +(2
ColSizeN-1
- 1).
For color fidelity , 8-bit encoding should be used for each component, that is, ColSize1,
ColSize2, and ColSize3 are set to X'08'. When the recommended 8-bit encoding is used for
the a and b components, the range is extended to include -128, which is mapped to the value
X'80'. If the encoding is less than 8 bits, treatment of the most negative binary endpoint for the
a and b components is device-dependent, and tends to be insignificant due to the quantization
error .
SEC Control Sequence

<!-- Page 111 -->

PTOCA Reference 93
Architecture Note: The reference white point for CIELAB is known as D50 and is defined in
CIE publication 15-2 entitled Colorimetry .
X'40' Standard OCA color space. The color value is specified with one component. Component 1 is
an unsigned binary number that specifies a named color using a two-byte value from the
Standard OCA Color V alue table. For a complete description of the Standard OCA Color V alue
T able, see the Mixed Object Document Content Architecture Reference, AFPC-0004.
COLSIZE1 = X'10' and defines the number of bits used to specify component 1. COLSIZE2,
COLSIZE3, COLSIZE4 are reserved and should be set to zero. This is a device-dependent
color space. The following table defines the valid color values used to specify named colors.
The table also specifies the RGB values that can be used for each named color , assuming that
each component is specified with 8 bits and that the component intensity range 0 to 1 is
mapped to the binary value range 0 to 255.
T able 12. SEC Color V alues
V alue Color Red
(R)
Green
(G)
Blue
(B)
X'0000' or X'FF00' Presentation-process default;
see Note 1 on page 94
X'0001' or X'FF01' Blue 0 0 255
X'0002' or X'FF02' Red 255 0 0
X'0003' or X'FF03' Pink/Magenta 255 0 255
X'0004' or X'FF04' Green 0 255 0
X'0005' or X'FF05' T urquoise/cyan 0 255 255
X'0006' or X'FF06' Y ellow 255 255 0
X'0007' White; see Note 2 on page 94 255 255 255
X'0008' Black 0 0 0
X'0009' Dark blue 0 0 170
X'000A' Orange 255 128 0
X'000B' Purple 170 0 170
X'000C' Dark green 0 146 0
X'000D' Dark turquoise 0 146 170
X'000E' Mustard 196 160 32
X'000F' Gray 131 131 131
X'0010' Brown 144 48 0
X'FF07' Presentation-process default;
see Note 3 on page 94
— — —
SEC Control Sequence

<!-- Page 112 -->

94 PTOCA Reference
T able 12 SEC Color V alues (cont'd.)
V alue Color Red
(R)
Green
(G)
Blue
(B)
X'FF08' Color of medium
— — —
Notes:
1. The presentation-process default specified by X'0000' and X'FF00' is resolved as follows:
• For PTOCA text data, it is the presentation device default
2. The color rendered on presentation devices that do not support white is device-dependent. For
example, some printers simulate with color of medium, which results in white if white media is
used.
3. The presentation-process default specified by X'FF07' is resolved as the presentation device
default. This color value is also known in GOCA as neutral white for compatibility with display
devices.
4. The value X'FFFF' is not defined in the Standard OCA Color V alue T able but is used by some
objects as a default indicator as follows:
• For PTOCA text data, X'FFFF' may be specified in the Set T ext Color (STC) control sequence to
indicate that the PTOCA default hierarchy is used to generate the color value. Note that X'FFFF'
is not supported in the Set Extended T ext Color (SEC) control sequence.
5. While the RGB values in the table can be used to render the OCA named colors, many
implementations are and have been device-dependent. Nevertheless, it is recommended that
OCA Black (X'0008') be rendered as C = M = Y = X'00', and K = X'FF'.
All others Reserved
COLSIZE1 defines the number of bits used to specify the first color component. The color component is right-
aligned and padded with zeros on the left to the nearest byte boundary . For example, if COLSIZE1 = X'06', the
first color component has two padding bits. If the specified value is invalid or unsupported, exception condition
EC-0E05 exists. The standard action is to use the device default color .
COLSIZE2 defines the number of bits used to specify the second color component. The color component is
right-aligned and padded with zeros on the left to the nearest byte boundary . If the specified value is invalid or
unsupported, exception condition EC-0E05 exists. The standard action is to use the device default color .
COLSIZE3 defines the number of bits used to specify the third color component. The color component is right-
aligned and padded with zeros on the left to the nearest byte boundary . If the specified value is invalid or
unsupported, exception condition EC-0E05 exists. The standard action is to use the device default color .
COLSIZE4 defines the number of bits used to specify the fourth color component. The color component is
right-aligned and padded with zeros on the left to the nearest byte boundary . If the specified value is invalid or
unsupported, exception condition EC-0E05 exists. The standard action is to use the device default color .
COLOR V ALUE specifies the color value in the defined format and encoding. If the color value is invalid or
unsupported, exception condition EC-0E03 exists. The standard action is to use the device default color .
Note that the number of bytes specified for this parameter depends on the color space. For example, when
using 8 bits per component, an RGBcolor value is specified with 3 bytes, while a CMYK color value is
specified with 4 bytes. If extra bytes are specified, they are ignored as long as the control sequence length is
valid.
Architecture Note: For a description of color spaces and their relationships, see Hunt, R., The Reproduction
of Colour in Photography , Printing, and T elevision, Fifth Edition, Fountain Press, 1995.
SEC Control Sequence

<!-- Page 113 -->

PTOCA Reference 95
The subset may limit the parameter ranges permitted in this control sequence. For detailed information about
subsets, please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”,
on page 163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for
data-stream documentation.
Pragmatics
If the receiver does not support the specified color value exception condition EC-0E03 exists. The standard
action in this case is to use the presentation device default color .
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-0E02...Invalid or unsupported color space.
• EC-0E03...Invalid or unsupported color value.
• EC-0E04...Invalid percent value.
• EC-0E05...Invalid or unsupported number of bits in a color component.
SEC Control Sequence

<!-- Page 114 -->

96 PTOCA Reference
Set Intercharacter Adjustment (SIA)
The Set Intercharacter Adjustment control sequence specifies additional increment or decrement between
graphic characters. This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4–5 Control sequence length M N N
3 CODE TYPE X'C2' – X'C3' Control sequence
function type
M N N
4–5 SBIN ADJSTMNT X'0000' –
X'7FFF'
Adjustment M Y Y
6 CODE DIRCTION X'00' – X'01' Direction O Y Y
ADJSTMNT is a positive binary number expressed in measurement units. The range for this parameter
assumes a measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit,
please see the conversion routine described in “Interpreting Ranges” on page 47. The PTOCA default value for
ADJSTMNT is X'0000'. DIRCTION is a code with no measurement units. The PTOCA default value for
DIRCTION is X'00'.
Semantics
ADJSTMNT specifies the value of additional space between graphic characters. This space is in the I-direction
from the end of the current character increment to the presentation position of the following graphic character .
When this value is positive, the adjustment is called an increment. When the value is negative, the adjustment
is called a decrement. DIRCTION specifies the direction in which the intercharacter adjustment is to be
applied. Intercharacter increment, which occurs when DIRCTION is X'00', is applied in the positive I-direction.
Intercharacter decrement, which occurs when DIRCTION is X'01', is applied in the negative I-direction. This
control sequence does not change the current presentation position.
For graphic characters following each other:
I
cnew
= I
c
+ ADJSTMNT + CI
For a graphic character following a RMI, AMI, BLN control sequence or following a space character or
variable space character:
I
cnew
= I
c
+ CI
For a non-incrementing character:
I
cnew
= I
c
For the variable space character:
I
cnew
= I
c
+ VSI
In all cases:
B
cnew
= B
c
If intercharacter adjustment is valid for a given graphic character , it is applied before presenting the character .
If it is an incrementing character , the current inline coordinate is incremented first by the intercharacter
adjustment, and then by the character increment. The same is true for a non-incrementing character , but the
intercharacter adjustment is inhibited for the character that follows the non-incrementing character . The result
SIA Control Sequence

<!-- Page 115 -->

PTOCA Reference 97
is that the non-incrementing character is coupled with the following graphic character . This accomplishes an
overstrike function, and the intercharacter adjustment is applied to the coupled characters as a unit.
Intercharacter adjustment is not applied before or after the following:
• A space character or variable space character
• A Begin Line control sequence
• A Relative Move Inline control sequence
• An Absolute Move Inline control sequence
Non-presenting characters are graphic characters except when identified as the designated variable space
character .
Intercharacter adjustment is inserted between the characters that form a word, but not between words. That is,
intercharacter adjustment is applied only when in inword mode. Inword mode is entered after any incrementing
character has been processed. Inword mode is exited after any word delimiter has been processed. The
following are word delimiters:
• The space character or variable space character
• Begin Line control sequences
• Relative Move Inline control sequences
• Absolute Move Inline control sequences
Application Note: The following code points are normally used for the variable space character:
• X'40' in EBCDIC single-byte code pages
• X'20' in ASCII single-byte code pages
• X'4040' in EBCDIC double-byte code pages
• X'2020' in ASCII double-byte code pages
The following code points are used for the variable space character in T rueT ype/OpenT ype fonts that
use a Unicode (UTF-16) encoding:
• X'0020'
• X'00A0'
If the value of ADJSTMNT is the default indicator , a value is obtained from the hierarchy . If the value of
DIRCTION is the default indicator , a value is obtained from the hierarchy . Please see T able 9 on page 35.
If the value of ADJSTMNT or DIRCTION is not supported or is not within the range specified by PTOCA,
exception condition EC-1201 exists. The standard action is to ignore this control sequence and continue
presentation with the parameter values determined according to the hierarchy .
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If the value of ADJSTMNT is zero, no additional intercharacter increment or decrement appears between
graphic characters. In this case, DIRCTION is optional, and the SIA control sequence does not change the
following:
• The current presentation position
• The current I-unit value
• The current inline margin
• The current intercharacter increment value
• The current intercharacter decrement value
SIA Control Sequence

<!-- Page 116 -->

98 PTOCA Reference
Exception Conditions
This control sequence can cause the following exception condition:
• EC-1201...The value of ADJSTMNT or DIRCTION is not supported or is not in the range specified by
PTOCA.
SIA Control Sequence

<!-- Page 117 -->

PTOCA Reference 99
Set Inline Margin (SIM)
The Set Inline Margin control sequence specifies the position of an inline margin. This is a modal control
sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4 Control sequence length M N N
3 CODE TYPE X'C0' – X'C1' Control sequence
function type
M N N
4–5 SBIN DSPLCMNT X'0000' –
X'7FFF'
Displacement M Y Y
DSPLCMNT is a positive binary number expressed in measurement units. The range for this parameter
assumes a measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit,
please see the conversion routine described in “Interpreting Ranges” on page 47. The PTOCA default value for
DSPLCMNT is the B-axis, that is, I
c
is zero.
Semantics
This control sequence specifies a displacement, DSPLCMNT , from the B-axis in the I-direction that is to be
applied when a Begin Line control sequence is processed in the current Presentation Text object. If the value
of DSPLCMNT is the default indicator , a value is obtained from the hierarchy . Please see T able 9 on page 35.
If DSPLCMNT is omitted, exception condition EC-1E01 exists. The standard action is to ignore the control
sequence and continue presentation with the Inline Margin that was in effect prior to this control sequence. If
the value of DSPLCMNT is not supported or is not within the range specified by PTOCA, exception condition
EC-1001 exists. The standard action is to ignore this control sequence and continue presentation with the
value determined according to the hierarchy .
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
This control sequence does not change the current addressable position.
Pragmatics
The current addressable position is at the inline margin after each Begin Line control sequence is executed. If
the value of DSPLCMNT is zero, the inline margin is at the B-axis.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1E01...DSPLCMNT is missing.
• EC-1001...The value of DSPLCMNT is not supported or is not in the range specified by PTOCA.
SIM Control Sequence

<!-- Page 118 -->

100 PTOCA Reference
Set Key Information (SKI)
The Set Key Information control sequence provides encryption key information to be used with Encrypted Data
(ENC) controls. This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 6, 7–255 Control sequence length M N N
3 CODE TYPE X'9A' – X'9B' Control sequence
function type
M N N
4–7 Reserved; should be zero M N N
8–256 UNDF KEYINFO Any value Key information O N N
The data type of the contents of KEYINFO is UNDF (undefined). KEYINFO does not accept the default
indicator , but X'F ....F' is valid.
Semantics
KEYINFO is a collection of bytes that are used by the decryption method to perform decryption on encrypted
text specified in the ENC control. The contents and length of this field will vary based on the encryption
implementation, with diff erent bytes used for diffe rent things. For example, when a Hardware Security Module
(HSM) is used, the key information might consist of a slot ID, key name, key label, and so forth. Diffe rent
decryption implementations might use diffe rent information.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
If the value of the LENGTH field is less than 6, exception condition EC-1E01 exists. The standard action is to
ignore the control sequence and continue processing.
If the output device does not support decryption, exception condition EC-9D01 exists. The standard action is to
ignore the control sequence and continue processing.
Architecture Note: Some IPDS printers, under control of an environment-specific text fidelity control, will allow
reporting and continuation to be controlled when an ENC is encountered by a printer that cannot decrypt
text strings.
Application Note: The Set Key information (SKI) control sequence is modal, but is not an initial text condition.
For text major text, the SKI is not changed by AFP presentation servers when a MO:DCA BPT structured
field is encountered, and therefore later text on the same page will inherit any SKI set previously on the
page.
Given the large variety of diffe rent decryption devices, a method to specify more than 249 bytes for decryption
information (if required) is provided. SKIcontrols that are consecutive and part of the same control sequence
SKI Control Sequence

<!-- Page 119 -->

PTOCA Reference 101
chain have their KEYINFO fields concatenated together to form key information that can be much longer than
249 bytes. Consecutive, in this case, means the SKIcontrols have no intervening PTOCA controls between
them. Every single SKIcontrol on its own, or every set of consecutive SKIcontrols, is handled as a normal
modal control, and simply replaces the previous encryption key information, for use in subsequent ENC
controls.
Note: It is poor security practice for the data bytes in KEYINFO to contain the encryption key used to originally
encrypt the data, since including these bytes in the print stream defeats the purpose of encrypting it in
the first place.
KEYINFO is initially set to no value. If KEYINFO is defined, it can be reset back to no value by specifying a SKI
with the KEYINFO field omitted (LENGTH = 6). A SKI with LENGTH=6 resets the KEYINFO field back to no
value even if the SKIhas other consecutive SKIs either before or after it. Consecutive SKIs before it, if any , are
effe ctively ignored, while consecutive SKIs after it, if any , begin a new KEYINFO definition.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1E01...LENGTH is not valid.
• EC-9D01...Decryption is not available on this device.
SKI Control Sequence

<!-- Page 120 -->

102 PTOCA Reference
Set T ext Color (STC)
The Set T ext Color control sequence specifies a color attribute for the foreground areas of the text presentation
space. Foreground areas consist of the following:
• The stroked and filled areas of solid text characters, including overstrike characters; with hollow characters,
only the stroked portion of the character is considered foreground.
• The stroked area of a rule
• The stroked area of an underscore
All other areas of the text presentation space are considered background.
This is a modal control sequence.
Note: Colors may be specified using the Set T ext Color (STC) or the Set Extended T ext Color (SEC) control
sequences. Both STC and SEC can coexist in the same text object. The last issued control sequence
determines the current text color in accordance with the rules defined for modal control sequences. For
a definition of modal control sequences, see “Modal Control Sequences” on page 37.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4, 5 Control sequence length M N N
3 CODE TYPE X'74' – X'75' Control sequence
function type
M N N
4–5 CODE FRGCOLOR See
Semantics
section
Foreground color M Y Y
6 Retired parameter , see
Architecture Note, also
see “Retired Parameters”
on page 177.
O
The PTOCA default value for FRGCOLOR is X'FF07'. Please see the pragmatics section for further details.
Architecture Note: Pre-year 2000 applications and printers support an optional PRECSION parameter in byte
6. This parameter has been retired. It should not be generated by new applications, and should be
ignored by new printers. For a definition of this parameter , see “Retired Parameters” on page 177 .
Semantics
The FRGCOLOR parameter specifies a color value. Syntactically valid values for specifying colors are X'0000'
through X'0010' and X'FF00' through X'FF08', which is the range of values defined in the Standard OCA Color
V alue T able. An additional valid value is X'FFFF', which is the default indicator and specifies that the color
value is obtained from the hierarchy . Please see the Pragmatics section, as well as T able 9 on page 35. The
PTOCA default value for FRGCOLOR is X'FF07'. For a definition of the Standard OCA Color V alue T able, see
the Mixed Object Document Content Architecture Reference, AFPC-0004.
If the color is not supported, or if the FRGCOLOR value is not syntactically valid, exception condition EC-5803
exists. The standard action in this case is to use X'FF07'.
STC Control Sequence

<!-- Page 121 -->

PTOCA Reference 103
The following table defines the valid color values used to specify named colors. The table also specifies the
RGB values that can be used for each named color , assuming that each component is specified with 8 bits and
that the component intensity range 0 to 1 is mapped to the binary value range 0 to 255.
T able 13. STC Color V alues
V alue Color Red
(R)
Green
(G)
Blue
(B)
X'0000' or X'FF00' Presentation-process default; see Note
1 on page 103
X'0001' or X'FF01' Blue 0 0 255
X'0002' or X'FF02' Red 255 0 0
X'0003' or X'FF03' Pink/Magenta 255 0 255
X'0004' or X'FF04' Green 0 255 0
X'0005' or X'FF05' T urquoise/cyan 0 255 255
X'0006' or X'FF06' Y ellow 255 255 0
X'0007' White; see Note 2 on page 103 255 255 255
X'0008' Black 0 0 0
X'0009' Dark blue 0 0 170
X'000A' Orange 255 128 0
X'000B' Purple 170 0 170
X'000C' Dark green 0 146 0
X'000D' Dark turquoise 0 146 170
X'000E' Mustard 196 160 32
X'000F' Gray 131 131 131
X'0010' Brown 144 48 0
X'FF07' Presentation-process default; see Note
3 on page 103
— — —
X'FF08' Color of medium
— — —
X'FFFF' Default indicator
— — —
Notes:
1. The presentation-process default specified by X'0000' and X'FF00' is resolved as follows:
• For PTOCA text data, it is the presentation device default
2. The color rendered on presentation devices that do not support white is device-dependent. For example, some
printers simulate with color of medium, which results in white if white media is used.
3. The presentation-process default specified by X'FF07' is resolved as the presentation device default. This color
value is also known in GOCA as neutral white for compatibility with display devices.
4. The value X'FFFF' is not defined in the Standard OCA Color V alue T able but is used by some objects as a default
indicator as follows:
• For PTOCA text data, X'FFFF' may be specified in the Set T ext Color (STC) control sequence to indicate that the
PTOCA default hierarchy is used to generate the color value. Note that X'FFFF' is not supported in the Set
Extended T ext Color (SEC) control sequence.
5. While the RGB values in the table can be used to render the OCA named colors, many implementations are and
have been device-dependent. Nevertheless, it is recommended that OCA Black (X'0008') be rendered as C = M = Y
= X'00', and K = X'FF'.
STC Control Sequence

<!-- Page 122 -->

104 PTOCA Reference
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
The presentation process default color attribute value (FRGCOLOR = X'FFFF') is determined hierarchically .
The following order applies:
1. V alue set by T ext Color initial text condition parameter in descriptor
2. PTOCA default X'FF07'
The device default value is the receiver ’ s default. For example, characters, rules, and underscores will be
presented in black on a receiver which supports only black. The receiver ’ s best possible value means that if
the receiver has limited color capabilities, then it may substitute a color it supports for one it does not support.
For example, the receiver may substitute red for pink, blue for turquoise, and so forth.
The color attribute values X'FF00' to X'FF06' are translated to X'0000' to X'0006' and treated exactly like those
colors. The PTOCA default value of X'FF07', is the device default. For example, characters, rules, and
underscores will be presented in black on a device which supports only black. A color attribute value of X'FF08'
means that the receiver's default background color should be used for the foreground color . This provides an
erase function.
Exception Conditions
This control sequence can cause the following exception condition:
• EC-5803...The value of FRGCOLOR is invalid, or the specified color is not supported.
Architecture Notes:
1. The MO:DCA environment supports a Color Mapping T able (CMT) that may be used to map colors in a
PTOCA object to other colors. When a CMT is active, valid FRGCOLOR values are mapped to their target
values. The retired PRECSION parameter , if supported, is processed for the target values.
2. The IPDS environment allows a presentation device to implement limited simulated color support for
PTOCA text and rules. When the printer is working in a mode where color simulation is allowed, all valid
but unsupported color values are accepted and result in a device-dependent simulation of the specified
color without the generation of exception EC-5803.
STC Control Sequence

<!-- Page 123 -->

PTOCA Reference 105
Set T ext Orientation (ST O)
The Set T ext Orientation control sequence establishes the I-direction and B-direction for the subsequent text.
This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 6 Control sequence length M N N
3 CODE TYPE X'F6' – X'F7' Control sequence
function type
M N N
4–5 CODE IORNTION See
Semantics
section
I-axis orientation M Y Y
6–7 CODE BORNTION See
Semantics
section
B-axis orientation M Y Y
The PTOCA default for IORNTION is zero. The PTOCA default for BORNTION is 90.
Semantics
This control sequence specifies the I-axis and B-axis orientations with respect to the X
p
-axis for the current
Presentation Text object. The orientations are rotational values expressed in degrees and minutes. IORNTION
and BORNTION have the same format. Each is a two-byte, three-part binary code of the form ABC.
• A is a nine-bit binary number (bits 0 - 8) which provides from 0 through 359 degrees. V alues from 360
through 51 1 are invalid.
• B is a six-bit binary number (bits 9 - 14) which provides from 0 through 59 minutes. V alues from 60 through
63 are invalid.
• C is a one-bit reserved field (bit 15) which must be 0.
The maximum value for IORNTION and BORNTION is X'B3F6' or B'101 1001 1 1 1 1 101 10', which is 359 degrees
and 59 minutes. Increasing values indicate increasing clockwise rotation of the I,B axes with respect to the X
p
,
Y
p
axes. A value of 0 for IORNTION indicates that there is no rotation relative to the X
p
-axis. That is, positive I-
direction is parallel to the X
p
-axis.
The origin of the I,B axes is always one of the four corners of the object space. If the text orientation is
changed, this origin may also change. See Figure 12 on page 106 for the location of the I,Borigin for the eight
text orientations that are supported by the PT1, PT2, and PT3 subsets. For example, if IORNTION and
BORNTION are 0,90 or 90,0, the origin of the I,B axes is at the upper left corner , or origin, of the object space.
This is where X
p
= 0 and Y
p
= 0. If IORNTION and BORNTION are 180,90 or 90,180, the origin of the I,B axes
is at the upper right corner of the object space. This is where X
p
= X
p
-extent, and Y
p
= 0.
STO Control Sequence

<!-- Page 124 -->

106 PTOCA Reference
Figure 12. Location of I,B Origin
If the inline direction changes or the origin of the B-axis changes, the inline margin also changes. The new
inline margin is parallel to the new B-axis, and it is displaced in the new I-direction from the new B-axis
according to the value of the Inline Margin parameter . If the value of IORNTION or BORNTION is the default
indicator , a value is obtained from the hierarchy . Please see T able 9 on page 35.
If IORNTION or BORNTION is omitted, exception condition EC-1E01 exists. The standard action is to assume
an orientation with the I-axis parallel to the X
p
-axis, and the B-axis parallel to the Y
p
-axis. If IORNTION and
BORNTION are identical, exception condition EC-0F01 exists. The standard action is to assume an orientation
with the I-axis parallel to the X
p
-axis, and the B-axis parallel to the Y
p
-axis.
Pragmatics
When the text orientation is changed, the text appears to rotate about the current presentation position, which
is an X
p
,Y
p
coordinate. Please see Figure 13 on page 107 for examples of text orientation and character
rotation. When such a change occurs, the current presentation position is not changed, but the values of I
c
and
B
c
are adjusted to correspond to the current presentation position relative to the new orientation.
If neither the I-direction nor the B-direction is parallel to the X
p
-direction or the Y
p
-direction, exception condition
EC-0F01 exists. The standard action is to set the I-direction parallel to the X
p
-direction and the B-direction
parallel to the Y
p
-direction.
Orientations other than 0,90 are valid, but may be constrained by receiver limitations or by parameters in the
controlling environment. If the orientation is not supported by the receiver , exception condition EC-0F01 exists.
The standard action is to use 0,90 degrees for the orientation. This exception condition and standard action
also apply to values for IORNTION and BORNTION not within the range specified by PTOCA.
Architecture Notes:
1. The following remain as previously specified:
• The current presentation position, an Xp,Yp coordinate,
• The current I-unit value,
• The current inline margin,
• The current intercharacter increment value,
• The current intercharacter decrement value,
• The current B-unit value,
• The current baseline increment value,
• The current coded font.
STO Control Sequence

<!-- Page 125 -->

PTOCA Reference 107
2. The following will change:
• The X
p
- Y
p
-axis from which inline margin is measured (that is, the inline margin appears to rotate).
• Font character rotations appropriate to the new orientation are used.
• Presentation position should be respecified if subsequent text is to be positioned elsewhere in the
Presentation Text space.
• Other modal parameter values should be respecified if they are more appropriate to the new orientation.
• A new coded font should be specified:
– If the current coded font is not valid in the new text orientation,
– If it is desired that the graphic characters be rotated in proper orientation with respect to the new
baseline.
3. If the Presentation Text object measurement units specified for the X
p
-axis are different from the
measurement units specified for the Y
p
-axis, the result of a Set T ext Orientation control sequence may be
unexpected and use of a Set T ext Orientation control sequence should be avoided.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1E01...IORNTION or BORNTION is missing.
• EC-0F01...IORNTION and BORNTION are identical.
• EC-0F01...IORNTION or BORNTION not parallel to X
p
-axis or Y
p
-axis.
• EC-0F01...IORNTION and BORNTION not supported by receiver .
Figure 13. Examples of T ext Orientation and Character Rotation
STO Control Sequence

<!-- Page 126 -->

108 PTOCA Reference
Set V ariable Space Character Increment (SVI)
The Set V ariable Space Character Increment control sequence specifies the increment for a variable space
character . This is a modal control sequence.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 4 Control sequence length M N N
3 CODE TYPE X'C4' – X'C5' Control sequence
function type
M N N
4–5 SBIN INCRMENT X'0000' –
X'7FFF'
Increment M Y Y
INCRMENT is a positive number expressed in measurement units. The range for this parameter assumes a
measurement unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the
conversion routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence specifies an increment, INCRMENT , for the variable space character . The increment is
in the I-direction from the presentation position of the variable space character to the addressable position for
the next graphic character or control sequence for subsequent text in the current Presentation Text object. This
control sequence does not change the current presentation position.
T o return to the current coded font's default value for the variable space character increment, set INCRMENT
to the default indicator . If the current coded font does not have such a default value, INCRMENT is set to the
character increment for the default variable space character .
If INCRMENT is omitted, exception condition EC-1E01 exists. The standard action is to continue with the
presentation using the standard action value for the variable space character increment. If the value of
INCRMENT is not supported or is not within the range specified by PTOCA, exception condition EC-1701
exists. The standard action is to ignore this control sequence and continue presentation with the value
determined according to the hierarchy . Please refer to the Pragmatics section for details.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
The inline coordinate of the presentation position is incremented by INCRMENT after each variable space
character is processed. Each variable space character causes the presentation position to move in the I-
direction by the amount of the variable space character increment. If the value of INCRMENT is zero, no
variable space appears between words, and intercharacter adjustment is not applied even though the resulting
characters appear side-by-side. When a Set V ariable Space Increment control sequence is received, the new
value of INCRMENT is saved and is applied to any subsequent variable space character received.
SVI Control Sequence

<!-- Page 127 -->

PTOCA Reference 109
The code point used for the variable space character is specified, either implicitly or explicitly , by the active
font. In this case, the default value for INCRMENT is the value specified by the active font in its current
orientation. The value is obtained in this order:
1. The current variable space character increment
2. The default variable space character increment of the active coded font
3. The character increment of the default variable space character code point
When the value of INCRMENT changes because of changes in the font or the SVIcontrol sequences, the new
value is carried but is not used until the variable space character is enabled.
The variable space character increment is not effe ctive for other graphic characters that are not presented, nor
for graphic characters that make no marks.
Architecture Note: The following remain as previously specified:
• The current presentation position, an Xp,Yp coordinate
• The current I-unit value
• The current inline margin,
• The current intercharacter increment value
• The current intercharacter decrement value
• The current B-unit value
• The current baseline increment value
• The current coded font
Application Note: The following code points are normally used for the variable space character:
• X'40' in EBCDIC single-byte code pages
• X'20' in ASCII single-byte code pages
• X'4040' in EBCDIC double-byte code pages
• X'2020' in ASCII double-byte code pages
The following code points are used for the variable space character in T rueT ype/OpenT ype fonts that
use a Unicode (UTF-16) encoding:
• X'0020'
• X'00A0'
Application Note: The Set V ariable Space Character Increment (SVI) control sequence is modal, but is not an
initial text condition. For text major text, SVI is not reset by AFP presentation servers when a MO:DCA
BPT structured field is encountered, and therefore later text on the same page will inherit any SVI set
previously on the page. If no SVI is specified in a page, then the default variable space character
increment of the active coded font is used.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1E01...INCRMENT is missing
• EC-1701...The value of INCRMENT is not supported or is not in the range specified by PTOCA.EC-1E01EC-
1701
SVI Control Sequence

<!-- Page 128 -->

1 10 PTOCA Reference
T emporary Baseline Move (TBM)
The T emporary Baseline Move control sequence changes the position of the baseline without changing the
established baseline.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 3, 4, 6 Control sequence length M N N
3 CODE TYPE X'78' – X'79' Control sequence
function type
M N N
4 CODE DIRCTION X'00' – X'03' Direction M Y Y
5 BITS PRECSION X'00' – X'01' Precision O Y Y
6–7 SBIN INCRMENT X'0000' –
X'7FFF'
T emporary baseline
increment
O Y Y
INCRMENT is a positive number expressed in measurement units. The range for this parameter assumes a
measurement unit of 1/1440 inch. If it is necessary to convert to diff erent measurement unit, please see the
conversion routine described in “Interpreting Ranges” on page 47. The PTOCA default value for INCRMENT is
1/2 the baseline increment value. The PTOC A default value for DIRCTION and PRECSION is zero.
Semantics
This control sequence does one of the following:
• Change the current baseline coordinate by the amount specified by INCRMENT in the direction specified by
DIRCTION
• Return the baseline coordinate to the established baseline coordinate position
This control sequence does not change the position of the established baseline coordinate or the inline
presentation position. INCRMENT specifies the magnitude of the temporary baseline increment. PRECSION
specifies the method by which the receiver exhibits the change in the baseline coordinate. DIRCTION specifies
the direction of the change.
DIRCTION is a bit-encoded value that specifies the following:
V alue Meaning
X'00' Do not change the baseline.
X'01' Return to the established baseline. Delete the temporary baseline created by TBM control
sequences.
X'02' Move the temporary baseline away from the I-axis one value of INCRMENT , performing a
subscript function. The increment is applied to the current baseline coordinate, not to the
established baseline, and has no effe ct on the established baseline.
X'03' Move the temporary baseline toward the I-axis one value of INCRMENT , performing a
superscript function. The increment is applied to the current baseline coordinate, not to the
established baseline, and has no effe ct on the established baseline.
TBM Control Sequence

<!-- Page 129 -->

PTOCA Reference 1 1 1
The following equations apply to DIRCTION:
If DIRCTION = 0:
B
cnew
= B
c
If DIRCTION = 1:
B
cnew
= B
est
If DIRCTION = 2:
B
cnew
= B
c
+ INCRMENT
If DIRCTION = 3:
B
cnew
= B
c
- INCRMENT
In all cases:
I
cnew
= I
c
PRECSION is bit-encoded as follows.
Bits 0-6
Bits 0-6 are reserved. They must be set to B'0' by generators, and ignored by receivers.
Bit 7
If bit 7 is B'0', the receiver must accurately place and represent the character using the coded font that is active
when the control sequence is executed. In this case, the movement of the baseline is not simulated, and the
character presented on the shifted baseline is the same as the characters used in the surrounding text. That is,
it is not a character specially designed for subscripting or superscripting. However , this does not prohibit
changing the coded font. If this bit is B'0', the intent is to ensure that the receiver has the word processing
capability of producing formal documents.
If bit 7 is B'1', the movement of the baseline may be simulated using specially designed subscript or
superscript characters which appear smaller than the surrounding text.
If the value of INCRMENT , PRECSION, or DIRCTION is the default indicator , a value is obtained from the
hierarchy . Please see T able 9 on page 35.
If the value of INCRMENT , PRECSION, or DIRCTION is not supported or is not within the range specified by
PTOCA, exception condition EC-9803 exists. The standard action is to ignore this control sequence and
continue presentation with the value determined according to the hierarchy . Please see the Pragmatics section
for details.
The subset may limit the range permitted in this control sequence. For detailed information about subsets,
please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:DCA Environment”, on page
163, and Appendix B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-
stream documentation.
Pragmatics
• How TBM operates:
Changing the baseline coordinate with this control sequence does not modify the position of the
established baseline coordinate.
Once the baseline coordinate has been changed by a TBM control sequence, it will remain at the new
location until it is terminated by another TBM control sequence or the end of the Presentation Text object.
After processing a TBM control sequence, additional TBM control sequences are processed relative to the
temporary baseline coordinate position, not the established baseline coordinate position. That is, a second
TBM with magnitude and direction equal to the first TBM causes the temporary baseline coordinate to be
TBM Control Sequence

<!-- Page 130 -->

1 12 PTOCA Reference
moved farther from the established baseline coordinate. A second TBM with magnitude equal to the first
but opposite direction cancels the effect of the first TBM and terminates the temporary baseline function.
The T emporary Baseline Increment parameter is modal. That is, once it is set, it remains set until it is
changed by another TBM control sequence. It is not necessary to generate a TBM control sequence in
order to give this parameter a value. The parameter is required only if its value is to be changed. If a TBM
control sequence does not include INCRMENT or specifies the default indicator for INCRMENT , the default
value is used. The default is 1/2 the current baseline increment.
The temporary baseline may be canceled, that is, reset to the established baseline coordinate position, by
specifying the “Return to Established Baseline” function. This is specified by setting DIRCTION equal to
X'01'. If a TBM control sequence is processed with DIRCTION set to X'01' when a temporary baseline has
not been established, the result is a no-operation. If a TBM control sequence specifies the Return to
Established Baseline function but includes the T emporary Baseline Increment parameter INCRMENT , the
baseline coordinate is returned to the established baseline coordinate position, and INCRMENT is
changed to the new value specified. PRECSION has no effe ct on the Return to Established Baseline
function.
The temporary baseline function is terminated if the temporary baseline coordinate coincides with the
established baseline coordinate, that is, when the established baseline and the current baseline are equal
at the end of processing the TBM control sequence. Therefore, creating a temporary baseline on one side
of the established baseline followed by another temporary baseline on the other side of the established
baseline without terminating the temporary baseline field is possible by changing the value of INCRMENT .
• How TBM affects other control sequences:
The temporary baseline field is not canceled by any other control sequences. Here are some examples:
A Begin Line control sequence is processed relative to the established baseline coordinate position, not
the temporary baseline coordinate position. Following the processing of a Begin Line control sequence,
new baseline coordinate positions are determined. The Baseline Increment is added to the current
baseline coordinate position to provide a new current baseline position. The Baseline Increment is also
added to the established baseline coordinate position to provide a new established baseline position.
Following the processing of a Begin Line control sequence, the temporary baseline is continued until a
terminating TBM control sequence ends the temporary baseline function.
A Relative Move Baseline control sequence is processed relative to the established baseline coordinate
position, not the temporary baseline coordinate position. Following the processing of a Relative Move
Baseline control sequence, new baseline coordinate positions are determined. The relative baseline
increment is added to the current baseline coordinate position to provide a new current baseline position.
The relative baseline increment is also added to the established baseline coordinate position to provide a
new established baseline position. Following execution of a Relative Move Baseline control sequence, the
temporary baseline is continued until a terminating TBM control sequence ends the temporary baseline
function.
• How TBM uses the PRECSION parameter:
PRECSION specifies how the receiver should exhibit characters at the temporary baseline. There is an
actual placement method and a substitution method. If PRECSION is X'01', a receiver may simulate the
temporary baseline move by substituting subscript or superscript graphics for graphics actually positioned
below or above the established baseline. This is the substitution method. If PRECSION is X'00', the
receiver is required to support the physical shift in the baseline coordinate, and may not substitute special
graphics for those specified at the temporary baseline. This is the actual placement method. Receivers
must support one of these two methods of presentation.
Receivers that implement the substitution method should support the other aspects of the TBM control
sequence just as if an actual shift in the baseline coordinate had occurred. That is, the character increment
of the active font, the current baseline, the established baseline, and the baseline increment must all be
maintained, just as though the characters being used were from the active font and the shift of the current
baseline had actually occurred.
TBM Control Sequence

<!-- Page 131 -->

PTOCA Reference 1 13
If a receiver implements only the substitution method and PRECSION is set to X'00', exception condition
EC-9803 exists. The standard action is to perform the substitution method. Furthermore, if a receiver using
the substitution method cannot generate the necessary substitution character , exception condition EC-
9803 exists. The standard action is to present the requested character at the established baseline
coordinate. It is not an exception if a TBM control sequence with PRECSION set to X'01' is encountered by
a receiver which implements only the actual placement method. If a receiver supports the substitution
method in addition to the actual placement method, setting PRECSION to X'01' causes selection of the
substitution method.
PRECSION is not a modal parameter . However , if another TBM control sequence that does not terminate
the temporary baseline function is received, and PRECSION is not specified, then the current value of
PRECSION is assumed. If the value of PRECSION is changed from X'00' to X'01' or from X'01' to X'00', the
precision change must be honored according to the rules for PRECSION as indicated in the semantics.
• How TBM relates to underscore and overstrike:
For receivers that support the actual placement method, the baseline position of characters resulting from
the underscore function is the established baseline. The baseline position of characters resulting from the
overstrike function is the current baseline, that is, the overstriking characters follow the temporary baseline.
If an overstrike or underscore function continues after the temporary baseline function is terminated, no
exception condition exists. The corresponding function continues relative to the current baseline, which is
equal to the established baseline. For receivers that support only the substitution method, the overstrike
and underscore functions occur relative to the established baseline.
• Miscellaneous TBM exception conditions:
A receiver that supports the actual placement method for the TBM control sequence establishes the
maximum temporary baseline displacement that it can support. If the T emporary Baseline Increment
parameter exceeds the limits supported by the receiver , exception condition EC-9803 exists. The standard
action is to use the established maximum limit that was exceeded as the standard action value for this
parameter .
More than one TBM control sequence without an intervening termination, such as a TBM of equal
magnitude and opposite direction, or a TBM specifying Return to Established Baseline, is called multi-
offsetting. Multi-offsetting support is required by PTOCA for receivers that support the actual placement
method. It is not required to know the number of multi-offsets that have been accepted.
Since receivers that support the substitution method do not physically create the temporary baseline, they
cannot support multi-offset. If a multi-offset TBM is received by a substitution method receiver , exception
condition EC-9803 exists. The standard action is to present according to the substitution method.
If processing a TBM causes any portion of a character box on the current baseline to exceed the
boundaries of the object space and presentation is attempted, exception condition EC-0103 exists. The
standard action is to continue processing without presenting characters until the addressable point
specified is valid for use as a presentation position, and then start presenting again. While processing
without presenting characters, the current presentation position is maintained as though the affe cted
characters were being presented.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-9803...The value of INCRMENT , PRECSION, or DIRCTION is not supported or is not in the range
specified by PTOCA.
• EC-9803...The PRECSION parameter specifies the actual placement method but the receiver does not
support it.
• EC-9803...A receiver using the substitution method cannot generate the required substitution character .
• EC-9803...For a receiver that uses the actual placement method, the INCRMENT parameter exceeds the
physical limit.
• EC-9803...A multi-offset TBM control sequence is received by a receiver that uses the substitution method.
TBM Control Sequence

<!-- Page 132 -->

1 14 PTOCA Reference
• EC-0103...The control sequence will cause part of a character's character box to be outside of the object
space, and presentation is attempted.
TBM Control Sequence

<!-- Page 133 -->

PTOCA Reference 1 15
T ransparent Data (TRN)
The T ransparent Data control sequence contains a sequence of code points that are presented without a scan
for embedded control sequences.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 2–255 Control sequence length M N N
3 CODE TYPE X'DA' – X'DB' Control sequence
function type
M N N
4–256 CHAR TRNDA T A Not
applicable
T ransparent data O N N
The contents of TRNDA T A are unknown. TRNDA T A does not accept the default indicator , but X'F ....F' is valid.
Semantics
This control sequence specifies a string of code points, all of which are to be processed as graphic characters.
No code point within the data field is recognized as a Control Sequence Prefix. The current inline position is
incremented for each graphic character in the string.
For graphic characters following each other:
I
cnew
= I
c
+ ADJSTMNT + CI
Intervening non-incrementing characters are ignored.
For graphic characters following RMI, AMI, BLN control sequences or following a space character or variable
space character:
I
cnew
= I
c
+ CI
Intervening non-incrementing characters are ignored.
For the variable space character:
I
cnew
= I
c
+ VSI
For a non-incrementing character:
I
cnew
= I
c
In all cases:
B
cnew
= B
c
Pragmatics
No absolute move, relative move, baseline positioning, or other immediate or modal function is available within
TRNDA T A. If code points representing control sequences are processed within TRNDA T A, they are presented
as graphic characters, and the active coded font determines whether any character shapes appear on the
presentation surface. If a T ransparent Data control sequence causes any part of a character's character box to
exceed the object space, exception condition EC-0103 exists. The standard action is not to present the
character that exceeds the object space, and continue processing without presenting characters until the
presentation position is returned to an addressable position within the object space that is a valid presentation
position for the character being presented. Then presentation of characters may resume.
TRN Control Sequence

<!-- Page 134 -->

1 16 PTOCA Reference
The data length must be an even number for double-byte fonts. If the T ransparent Data control sequence
length is an odd number when a double-byte font is active, exception condition EC-1A01 exists. The standard
action is to process the T ransparent Data control sequence up to the last byte, skip the odd byte, and continue
processing.
If the character encoding is Unicode but the code points in the TRN are not valid Unicode data, exception
condition EC-1A03 exists. The standard action is to skip the remainder of the code points in the TRN and
continue processing.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-0103...The control sequence will cause part of a character's character box to be outside the object
space, and presentation is attempted.
• EC-1A01...The control sequence length is an odd number , but a double-byte font is active.
• EC-1A03...Invalid Unicode data. This can be caused by one of the following:
– A high-order surrogate code value was not immediately followed by a low-order surrogate code value. The
high-order surrogate range is U+D800 through U+DBFF .
– A low-order surrogate code value was not immediately preceded by a high-order surrogate code value.
The low-order surrogate range is U+DC00 through U+DFFF .
– An illegal UTF-8 byte sequence, as defined in the Unicode 3.2 Specification, was specified. For more
information on illegal UTF-8 byte sequences, see T able 15 on page 129.
TRN Control Sequence

<!-- Page 135 -->

PTOCA Reference 1 17
Underscore (USC)
The Underscore control sequence identifies text fields that are to be underscored.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH 3 Control sequence length M N N
3 CODE TYPE X'76' – X'77' Control sequence
function type
M N N
4 BITS BYPSIDEN See
Semantics
section
Bypass identifiers M Y Y
BYPSIDEN is a binary field with no measurement units. The PTOCA default is X'01', which means no bypass
is in effect.
Semantics
Underscore is accomplished with a pair of USC control sequences. Underscore is activated with a beginning
USC with a non-zero value for BYPSIDEN bits 4-7. Underscore is deactivated with an ending USC with a zero
value for BYPSIDEN bits 4-7. This control sequence immediately precedes the field of text to be underscored,
which is called the underscore field. The control sequence specifies that text bounded by the two USC control
sequences is to be underscored, and it specifies which controlled inline white space within that text is to be
underscored.
The underscore field is delimited by a beginning USC control sequence and either an ending USC control
sequence or the end of the Presentation Text object. The underscore field is a sequential string of text, that is,
graphic characters or control sequences.
BYPSIDEN specifies which controlled inline white space within the underscore field is to be underscored.
Controlled inline white space is that area of the presented line that contains no visible material due to
movement of the presentation position in the I-direction caused by the following:
• Absolute Move Inline control sequence
• Relative Move Inline control sequence
• Space character or variable space character
Application Note: The following code points are normally used for the variable space character:
• X'40' in EBCDIC single-byte code pages
• X'20' in ASCII single-byte code pages
• X'4040' in EBCDIC double-byte code pages
• X'2020' in ASCII double-byte code pages
The following code points are used for the variable space character in T rueT ype/OpenT ype fonts that
use a Unicode (UTF-16) encoding:
• X'0020'
• X'00A0'
USC Control Sequence

<!-- Page 136 -->

1 18 PTOCA Reference
Movement of the current inline position in the I-direction to or through a presentation position that already
contains material to be underscored creates controlled inline white space for the entire move in the I-direction.
Not all inline white space is controlled. White space resulting from non-printing characters (other than space
characters or variable space characters) within the character set, from substitution of non-printing characters
for unsupported characters, from intercharacter adjustment, or from the inline margin is not considered
controlled inline white space.
Inline moves that cause the current addressable position to move in a direction opposite to the inline direction,
that is, in the negative inline direction, do not cause underscore.
BYPSIDEN is bit encoded as follows.
BIT MEANING
0-3 Reserved, that is, set to 0 by generators and ignored by receivers
4 Bypass Relative Move Inline
5 Bypass Absolute Move Inline
6 Bypass space characters and variable space characters
7 No Bypass in effect
Bits 0-3, Reserved
Reserved bits are set to 0 by generators and ignored by receivers.
Bit 4, Bypass Relative Move Inline
A value of B'0' in this bit indicates that the controlled white space generated as a result of a Relative Move
Inline control sequence is to be underscored. A value of B'1' in this bit indicates that such controlled white
space is not to be underscored. It should be bypassed.
Bit 5, Bypass Absolute Move Inline
A value of B'0' in this bit indicates that the controlled white space generated as a result of an Absolute Move
Inline control sequence is to be underscored. A value of B'1' in this bit indicates that such controlled white
space is not to be underscored. It should be bypassed.
Bit 6, Bypass space characters
A value of B'0' in this bit indicates that the controlled white space generated as a result of space characters or
variable space characters is to be underscored. A value of B'1' in this bit indicates that such controlled white
space is not to be underscored. It should be bypassed.
Bit 7, No Bypass in Effect
A value of B'0' in this bit activates the other bypass flags. A value of B'1' in this bit indicates that the other
bypass flags are overridden, and that all text and white space bounded by the USC pair should be
underscored. If the value of BYPSIDEN is the default indicator , a value is obtained from the hierarchy . Please
see T able 9 on page 35.
Implementation Note: Most IPDS printers have implemented X'FF' as the default indicator , which results in
BYPSIDEN = X'01' - no bypass in effe ct. However , it could be argued that the proper default indicator is
X'0F', since BYPSIDEN bits 0-3 are reserved, should be set to zero by generators, and should be
ignored by receivers. T o avoid confusion, it is strongly recommended that a default indicator not be used
for this parameter , and that the value X'01' is specified directly if the default is desired.
An underscore area is that portion of the underscore field for which text is actually underscored. An underscore
area is delimited by the addressable position in the following cases.
• A beginning USC
USC Control Sequence

<!-- Page 137 -->

PTOCA Reference 1 19
• Either end of bypassed controlled inline white space
• Either end of a baseline move, which may be for the established baseline or for a temporary baseline
• The beginning of negative changes in the presentation position caused by inline moves or negative
intercharacter adjustments
• Boundaries where violation causes truncation
• An ending USC
• The end of the Presentation Text object
The dimension in the positive I-direction of the underscore field is defined by the minimum and maximum I-
coordinates specified between the underscore area delimiters. White space resulting from the application of
the inline margin is underscored only if this area is entered by means of an inline move.
Pragmatics
The underscore area must be underscored with a solid line. For each character to be underscored, the solid
line must extend for the entire character box and for any intercharacter adjustment. The solid line may not
extend past the character box for the final character in the underscore field. That is, the solid line may extend
up to but not include the current presentation position, I
c
.
Underscore does not occur in the negative I-direction. If there is negative intercharacter adjustment, the
underscore is applied from the beginning of the character reference point, after it is placed, to a point which is
one character increment from there in the positive I-direction. If the negative intercharacter adjustment is large
enough to move the presentation position of the next character to a point which precedes the previous
character's presentation position by more than the character increment of the next character , then an
underscore beginning at the reference point of the next character will cause a gap in the underscore, as shown
in Figure 14.
Figure 14. Example of Intercharacter Increment in Underscore
Multiple beginning and ending USC pairs may not be nested. However , no exception condition exists if a
beginning USC control sequence is processed when another USC control sequence is already active. The
subsequent beginning USC terminates the previous USC and starts another . If an ending USC is encountered
when there has been no previous USC, no exception condition exists. Ignore the ending USC. If a Presentation
T ext object contains a beginning USC without a matching ending USC, no exception condition exists.
T erminate the USC at the end of the Presentation Text object.
There is no provision in the USC control sequence to specify a coded font. It is assumed that the receiver can
underscore. Underscore positioning is determined by the active coded font. If the active coded font is changed
USC Control Sequence

<!-- Page 138 -->

120 PTOCA Reference
within the underscore field, discontinuity of the underscore, such as mismatched lines, different line weights, or
multiple lines, could result. However , this does not constitute a violation of the requirement for a solid line.
If an STO control sequence changes the text orientation within an underscore field, the position of the
underscore is still determined by the active coded font. Please see Figure 15.
Figure 15. Relationship of Underscore to Changes in Font, Orientation, and Rotation
T ypically an ST O control sequence is accompanied by an SCFL control sequence that specifies the
appropriate coded font to use in the new orientation. The coded font specified by the SCFL determines where
the underscore is positioned in the new orientation. The requirement for a solid line is not violated as long as
the underscore extends for the character increment and any intercharacter adjustment. For some
combinations of text orientation and character rotation, valid underscore might be discontinuous.
An underscore area is delimited by a beginning USC, bypassed controlled inline white space, and either an
ending USC or the end of the Presentation Text object. Additionally , the dimension of this area in the I-direction
is defined by the minimum and maximum I-coordinates specified between the underscore delimiters.
There are no syntactic restrictions on the occurrence of Begin Line, Absolute Move Baseline, Relative Move
Baseline, and T emporary Baseline Move control sequences within an underscore field.
USC Control Sequence

<!-- Page 139 -->

PTOCA Reference 121
Characters occurring at a temporary baseline coordinate are underscored at the established baseline
coordinate position.
Color is not a parameter of this control sequence.
Exception Condition
This control sequence can cause the following exception condition:
• None
USC Control Sequence

<!-- Page 140 -->

122 PTOCA Reference
Unicode Complex T ext (UCT)
Architecture Note: The recommended method for rendering Unicode complex text is to use GLC chains. With
that method, the UCT may optionally be specified within a GLC chain as a carrier for metadata that
specifies the original Unicode code points and control information describing how those code points may
be rendered. In that usage, the UCT does not cause any characters to be rendered. Applications can
use the UCT in GLC chains to “virtually“ render the code points in order to better correlate substrings of
glyph runs with their corresponding code points. The use of the UCT as a stand-alone control sequence
to render complex text has not been implemented widely and is not supported in IPDS environments.
The Unicode Complex T ext (UCT) control sequence marks the start of a sequence of Unicode code points.
This sequence starts with the first byte following the end of the UCT control sequence and ends with the last
byte identified by the complex text length parameter in the control sequence. All bytes in this sequence are
processed as code points without a scan for embedded control sequences. There is no odd function type
defined for the UCT , therefore the UCT can be part of a chain of control sequences but will always terminate
the chain.
If the active font is a data-object font and the data is encoded in a Unicode-based character encoding such as
UTF-8 or UTF-16, the code points in the text that follows UCT can be processed as Unicode complex text.
Under the following conditions the code points cannot be processed as Unicode complex text and instead are
processed by the presentation device as if they were within a TRN control sequence, in which case all UCT
control information is ignored:
• the active font is not a data-object font
• the data is not encoded in a Unicode-based character encoding
• the writing mode is vertical, as determined by a font character rotation of 90° or 270°
The UCT can be used in two ways.
1. When the UCT is specified within a GLC chain, that is when it is chained from a GAR or a GOR, it is used
to identify the code points rendered by the glyphs in the GLC chain. In that case, the code points following
the UCT are not rendered, since the corresponding glyph IDs are rendered by the GLC chain. How much of
the UCT controls are processed by the receiver to correlate code points to glyphs is device-dependent. If
bidi processing and glyph processing are enabled within the UCT , a receiver may choose to process all of
the UCT controls to actually lay out the code points in order to more closely associate code points with
rendered glyphs. Or a receiver may choose to ignore all of the UCT controls, in which case the association
of code points with glyph IDs can be more coarse. Processing of the UCT has no effect on the current
inline position, I
c
, or on any other PTOCA environment controls.
2. When the UCT is specified outside a GLC chain (a “stand-alone“ UCT), it is used to process and render the
code points that follow .
Architecture Note: The processing and rendering of a UCT outside a GLC chain is not part of the PTOC A
PT4 function set. Not all presentation devices support this function; consult the appropriate product
documentation. IPDS printers generate an exception when a UCT is specified outside a GLC chain.
Some IPDS printers, under control of an environment-specified text fidelity control, will render the
UCT code points in a one-to-one manner .
Rendering complex text involves two types of special processing:
• bidirectional (bidi) layout processing. When the writing mode is horizontal, as determined by a character
rotation in the active font of 0° or 180°, characters are presented on the current PTOCA baseline in a
direction determined by the Unicode bidi algorithm. For a description of the Unicode bidi algorithm, see
http://unicode.org/reports/tr9. The direction can be left-to-right (L→R), right-to-left (R→L), or a
combination. Characters are presented at the character rotation specified in the active font, and the
appropriate horizontal metrics are used to position the glyphs. See T able 14 on page 128 for a description of
UCT character positioning.
UCT Control Sequence

<!-- Page 141 -->

PTOCA Reference 123
Application Note: It is strongly recommended that the logical (storage) order of Unicode text strings be
preserved when such strings are placed into text objects. This will allow the text, when carried in an
interchange format such as a MO:DCA document, to be interchanged among applications that support
text processing functions such as searching, sorting, and indexing. If the logical order is modified,
which might be tempting as an aid to formatting, the capability to apply such text processing functions
is lost.
• glyph processing. Characters may require language-specific shaping such as arabic character shaping,
composition/decomposition, and position adjustments. The rendering is no longer one code point to one
character to one glyph, and requires the assistance of a Unicode layout engine.
Implementation Note: In AFP environments, the ICU ParagraphLayout API is used to apply Unicode bidi
layout processing and glyph processing to complex text. This API is developed by the International
Components for Unicode (ICU) project, which is jointly managed by a group of companies and
individuals throughout the world; see http://site.icu-project.org.
Complex text processing for UCT text is enabled or disabled by controls in the UCT . If both bidi layout
processing and glyph processing are disabled, the code points in the text that follows UCT are not processed
as complex text and instead are processed as if they were contained within a TRN control sequence.
If either bidi layout processing or glyph processing is to be applied, the sequence of Unicode code points must
first be normalized. This involves, for example, replacing composite character sequences with their equivalent
composed character . The Unicode normalization format used in PTOCA objects is Normalization Form C
(NFC). If the normalization step was not applied by the formatter that generated the complex text, it is applied
by the presentation device before bidi layout processing or glyph processing is applied.
Architecture Note: The Unicode character encoding is defined in the Unicode Standard, which is available
from the Unicode Consortium at http://unicode.org/standard/standard.html.
Syntax
Offset T ype Name Range Meaning M/O Def Ind
0 CODE PREFIX X'2B' Control Sequence Prefix M N N
1 CODE CLASS X'D3' Control sequence class M N N
2 UBIN LENGTH X'10' Control sequence length M N N
3 CODE TYPE X'6A' Control sequence function type M N N
4 CODE UCTVERS X'01' UCT version level
X'01' Base level
M N N
5 Reserved; should be zero M N N
6–7 UBIN CTLNGTH 0 – 32,767 Length of complex text data that follows
this control sequence
M N N
8 BITS CTFLGS See
Semantics
section
Complex text processing control flags M N N
9 Reserved; should be zero M N N
UCT Control Sequence

<!-- Page 142 -->

124 PTOCA Reference
10 CODE BIDICT X'02', X'04',
X'05', X'12',
X'20', X'22',
X'23'
Bidi layout processing control:
X'02' Enable; default p.d. is
L→R
X'04' Enable; set p.d. L→R
X'05' Enable; set p.d. R→L
X'12' Enable; p.d. set from
previous UCT ;
default L→R
X'20' Disable
X'22' Disable; t.d. L→R
X'23' Disable; t.d. R→L
M N N
1 1 CODE GL YPHCT X'01', X'20' Glyph processing control:
X'01' Enable
X'20' Disable
M N N
12 –
15
Reserved; should be zero M N N
16 –
17
SBIN AL TIPOS X'8000' –
X'7FFF'
Alternate current inline position M N N
T able Note: The terms 'p.d.' and 't.d.' stand for paragraph direction and text direction, respectively .
AL TIPOS is a signed binary number in measurement units.The range for AL TIPOS assumes a measurement
unit of 1/1440 inch. If it is necessary to convert to a different measurement unit, please see the conversion
routine described in “Interpreting Ranges” on page 47.
Semantics
This control sequence marks the start of a string of code points, all of which are to be processed as graphic
characters. No code point within the marked string of text is recognized as a Control Sequence Prefix. This
string of code points is also called “UCT text”.
If the code points in the text that follows the UCT control sequence cannot be processed as Unicode complex
text, or if glyph processing and bidi processing are disabled, the code points are processed as if they were
contained in a TRN control sequence. In that case, the current inline position is incremented for each graphic
character in the string as follows:
• For graphic characters following each other:
I
cnew
= I
c
+ ADJSTMNT + CI
and intervening non-incrementing characters are ignored.
• For graphic characters following RMI, AMI, or BLN control sequences or following a space character or
variable space character:
I
cnew
= I
c
+ CI
and intervening non-incrementing characters are ignored.
• For the variable space character:
I
cnew
= I
c
+ VSI
• For a non-incrementing character:
I
cnew
= I
c
• In all cases:
B
cnew
= B
c
UCT Control Sequence

<!-- Page 143 -->

PTOCA Reference 125
If the code points in the text that follows the UCT control sequence can be processed as Unicode complex text,
and if glyph processing or bidi processing is enabled, glyph positions and advances are determined with the
aid of a Unicode layout engine. In that case the advancement of the current text position is no longer a direct
function of the individual character increments. Additionally , the optional placement of text at the alternate
inline position may dictate the new text position.
If CTFLGS bit 3 = B'0' - advance I
c
, the current text position I
cnew
at the completion of a UCT is then generated
as follows:
• If I
c
was used to position the UCT text:
I
cnew
= I
c
+ sum{G
I
}
• If I
a
was used to position the UCT text:
I
cnew
= I
a
If CTFLGS bit 3 = B'1' - maintain I
c
, the current text position I
cnew
at the completion of a UCT is given by:
I
cnew
= I
c
Where:
• I
c
= the current text position at the start of UCT processing
• I
a
= the alternate text position at the start of UCT processing
• G
I
= the increment for a grapheme in the UCT
• sum = summation over all the graphemes that were presented for the UCT
UCTVERS specifies the functional level of the complex text support in the UCT .
V alue Definition
X'01' Base level of complex text support
All others Reserved
CTLNGTH specifies the total number of bytes in the sequence of code points that follows UCT ; that is, it
specifies the length of the text that follows UCT . The bytes identified by this parameter are processed as code
points and are presented without a scan for embedded control sequences.
CTFLGS is a bit-encoded parameter that specifies controls for processing the Unicode complex text code
points that follow , and is defined as follows:
BIT MEANING
0 Normalization
1 Alternate inline position (I
a
) valid
2 Alternate inline position (I
a
) coordinates
3 Maintain current inline position (I
c
)
4 Reset paragraph direction
5-7 Reserved, that is, set to B'0' by generators and ignored by receivers
Bit 0, Normalization
A value of B'0' in this bit indicates that the Unicode complex text code points that follow have not been
normalized. The presentation device will apply a normalization step as long as bidi layout processing or glyph
processing (or both) is to be applied. If neither bidi layout processing or glyph processing is to be applied, the
normalization step is not applied. The normalization format generated is Normalization Form C (NFC). A value
of B'1' in this bit indicates that the Unicode complex text code points that follow have been normalized by the
generator of the text object. No additional normalization is applied by the presentation device. The
normalization format assumed is Normalization Form C (NFC).
Reserved bits are set to B'0' by generators and ignored by receivers.
Architecture Note: The definition of Normalization Format C (NFC) is available at
http://unicode.org/reports/tr15.
UCT Control Sequence

<!-- Page 144 -->

126 PTOCA Reference
Bit 1, Alternate inline position (I
a
) valid
A value of B'0' in this bit indicates that the alternate inline position parameter AL TIPOS is not valid and cannot
be used to position UCT text. A value of B'1' in this bit indicates that the alternate inline position parameter
AL TIPOS is valid and can be used to position UCT text.
Bit 2, Alternate inline position (I
a
) coordinates
A value of B'0' in this bit indicates that the alternate inline position parameter I
a
is specified using absolute i-
coordinate values. In this case the range of I
a
is restricted to positive values. A value of B'1' in this indicates
that the alternate inline position parameter I
a
is specified using relative i-coordinate values that are measured
with respect to the current inline position I
c
. In this case the range of I
a
allows positive and negative values.
Bit 3, Maintain current inline position (I
c
)
A value of B'0' in this bit indicates that the current inline position I
c
is advanced in accordance with the
equations for I
cnew
at the completion of the UCT . A value of B'1' in this bit indicates that the current inline
position I
c
should not be advanced when the UCT is processed. In this case the value of I
c
at the completion of
UCT processing is equal to the value of I
c
at the start of UCT processing.
Bit 4, Reset paragraph direction
A value of B'0' in this bit has no effe ct on the paragraph direction and is treated as a No-op. A value of B'1' in
this bit coupled with CTLNGTH= X'0000' causes the paragraph direction to be reset to an undefined state. In
that case all other UCT controls are ignored. If this bit is set to B'1' and CTLNGTH≠X'0000', the bit is treated as
if it were set to B'0'.
Implementation Note: When AFP print servers process a Begin Presentation Text (BPT) structured field in a
MO:DCA data stream, they issue a set of PTOCA control sequences to establish default initial text
conditions for processing the text object. When the server supports the processing of stand-alone UCT s
(UCT s that are not part of a GLC chain) in attached presentation devices, this set must include a UCT
control sequence with CTLNGTH = X'0000' and CTFLGS bit 4 = B'1' to reset the paragraph direction to
an undefined state at the beginning of each text object. When the server only supports the processing of
UCT s within GLC chains in attached presentation devices, this additional control is not necessary .
Bits 5-7, Reserved
Reserved bits are set to B'0' by generators and ignored by receivers.
BIDICT is a code that specifies controls for processing the Unicode complex text code points that follow with
the Unicode bidi algorithm. In all cases, characters are presented at the character rotation specified in the
active font. If the active font is not a data-object font, or if it is a data-object font that does not specify a
Unicode-based character encoding, this parameter is ignored. For most BIDICT values, this parameter
establishes the Unicode paragraph direction, which is used as an input to the Unicode bidi algorithm. T able 14
on page 128 shows how the paragraph direction for Unicode complex text affects the positioning of the UCT
text. See “Bidi Layout Processing for UCT T ext” on page 129 for a description of bidi layout processing for UCT
text.
V alue Description
X'02' Enable Unicode bidi layout processing for the complex text code points that follow . The
paragraph direction is determined by the complex text string based on the first strong
directional character that is encountered. If no paragraph direction can be determined, use a
left-to-right default paragraph direction.
X'04' Enable Unicode bidi layout processing for the complex text code points that follow . The
paragraph direction is set to left-to-right.
UCT Control Sequence

<!-- Page 145 -->

PTOCA Reference 127
X'05' Enable Unicode bidi layout processing for the complex text code points that follow . The
paragraph direction is set to right-to-left.
X'12' Enable Unicode bidi layout processing for the complex text code points that follow . The
paragraph direction is determined by the previously processed complex text string in this text
object. If this is the first complex text string that is encountered in this text object, or if the
paragraph direction is undefined, the paragraph direction is based on the first strong
directional character that is encountered. If no paragraph direction can be determined, use a
left-to-right default paragraph direction.
X'20' Disable Unicode bidi layout processing for the complex text code points that follow . The
current PTOCA inline direction determines the text direction on the current PTOCA baseline.
The code points in the UCT are processed with respect to text direction as if they were within a
TRN.
X'22' Disable Unicode bidi layout processing for the complex text code points that follow . The UCT
contains a single directional run and is processed with a fixed text direction that is left-to-right.
There are no text direction changes for this UCT . The alternate inline position parameter I
a
, if
specified, is not used.
X'23' Disable Unicode bidi layout processing for the complex text code points that follow . The UCT
contains a single directional run and is processed with a fixed text direction that is right-to-left.
There are no text direction changes for this UCT . The alternate inline position parameter I
a
, if
specified, is not used.
All others Reserved
GL YPHCT is a code that specifies controls for applying glyph processing to the Unicode complex text code
points that follow . In all cases, characters are presented at the character rotation specified in the active font. If
the active font is not a data-object font, or if it is a data-object font that does not specify a Unicode-based
character encoding, this parameter is ignored.
V alue Description
X'01' Enable glyph processing for the complex text code points that follow . Language-specific
processing is applied to runs of characters and may result in shaping, composition/
decomposition, and positional adjustments.
X'20' Disable glyph processing for the complex text code points that follow . The code points in the
UCT are processed with respect to glyph processing as if they were within a TRN. Characters
are rendered in a one-to-one fashion using the selected glyph in the active font.
All others Reserved
If Unicode bidi layout processing is disabled (BIDICT=X'20') and if Unicode glyph processing is disabled
(GL YPHCT=X'20') the UCT code points are not treated as complex text and are processed as if they were
within a TRN control sequence.
AL TIPOS is a parameter that specifies an alternate inline position (I
a
) on the current baseline that may be used
in place of the current inline text position (I
c
) when processing Unicode complex text and the paragraph
direction is opposite the writing mode direction. This parameter is ignored if Unicode bidi layout processing is
disabled for the code points that follow . This parameter is also ignored if the active font is not a data-object
font, or if it is a data-object font that specifies a non-Unicode-based character encoding. The use of this
parameter depends on the current character rotation–as specified in the active font–and the paragraph
direction for the UCT . T able 14 on page 128 shows how the text in a UCT is positioned either with respect to
the current inline position I
c
, or with respect to the alternate inline position I
a
.
UCT Control Sequence

<!-- Page 146 -->

128 PTOCA Reference
T able 14. UCT T ext Positioning
PTOCA (i,b)
Orientation
Character Rotation UCT Para-
graph
Direction
UCT Character
Position w .r . to I
a
or I
c
UCT Character Position
w .r . to I
c
only
All 8 (i,b)
orientations
Character rotation 0°: Horizontal
L→R writing mode
L→R Start at I
c
; place UCT
text so it extends in (+) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
R→L Start at I
a
; place UCT
text so it extends in (-) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
All 8 (i,b)
orientations
Character rotation 180°:
Horizontal R→L writing mode
R→L Start at I
c
; place UCT
text so it extends in (+) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
L→R Start at I
a
; place UCT
text so it extends in (-) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
All 8 (i,b)
orientations
Character rotation 270°: V ertical
T→B writing mode
N/A Start at I
c
; place UCT
text so it extends in (+) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
All 8 (i,b)
orientations
Character rotation 90°: V ertical
B→T writing mode
N/A Start at I
c
; place UCT
text so it extends in (+) i-
direction
Start at I
c
; place UCT text
so it extends in (+) i-
direction
T able Notes:
1. The terms 'left', 'right', 'top', and 'bottom–as in 'L→R', 'R→L', 'T→B', and 'B→T'–only have meaning when
the media has been oriented so that the characters appear right-side-up.
2. The (+) i-direction is the direction of increasing i-values; the (-) i-direction is the direction of decreasing
i-values.
Pragmatics
The CTLNGTH parameter must specify an even number when the character encoding is double-byte, as in
UTF-16. If CTLNGTH is an odd number when the character encoding is double byte, exception condition
EC-1A01 exists. The standard action is to process the code point sequence up to the last byte, skip the odd
byte, exit UCT processing mode, and continue processing. If the CTLNGTH value is out of the architected
range, exception condition EC-9B01 exists. The standard action is to process the UCT code points up to the
maximum valid value of CTLNGTH, exit UCT processing mode, and continue processing.
If the UCTVERS, BIDICT , or GL YPHCT parameters specify an invalid value, exception condition EC-9B01
exists. The standard action is to process the UCT code points as if they were within a TRN.
The code points that follow the UCT may not specify valid Unicode data. In that case exception condition
EC-1A03 exists, and the standard action is to skip the remainder of the UCT code points, exit UCT processing
mode, and continue processing.
The UCT control sequence always terminates chaining. Therefore, in the exception cases noted, when the
presentation device exits UCT processing mode, it continues processing by interpreting the next bytes as
either code points or the start of an unchained control sequence.
Exception Conditions
This control sequence can cause the following exception conditions:
• EC-1A01...The CTLNGTH parameter is an odd number , but the character encoding is double byte.
UCT Control Sequence

<!-- Page 147 -->

PTOCA Reference 129
• EC-9B01...The CTLNGTH, UCTVERS, BIDICT , or GL YPHCT parameter values are invalid.
• EC-1A03...Invalid Unicode data. This can be caused by one of the following:
– A high-order surrogate code value was not immediately followed by a low-order surrogate code value. The
high-order surrogate range is U+D800 through U+DBFF .
– A low-order surrogate code value was not immediately preceded by a high-order surrogate code value.
The low-order surrogate range is U+DC00 through U+DFFF .
– An illegal UTF-8 byte sequence, as defined in the Unicode 3.2 Specification, was specified. For more
information on illegal UTF-8 byte sequences, see T able 15.
Application Note. For a formal definition of UTF-8, consult the Unicode 3.2 Specification. The illegal UTF-8
byte sequences can be summarized as follows:
• The value in the 1st byte of the UTF-8 byte sequence was not in the legal UTF-8 range (X'00' - X'7F' and
X'C2' - X'F4').
• The value in the 2nd byte of the UTF-8 byte sequence was not in the legal UTF-8 range allowed by the value
in the 1st byte. The valid ranges for the 2nd byte are shown in T able 15.
T able 15. V alid V alues for UTF-8 First and Second Bytes
First Byte Second Byte
X'C2'–X'DF' X'80''–X'BF'
X'E0' X'A0''–X'BF'
X'E1''–X'EC' X'80''–X'BF'
X'ED' X'80''–X'9F'
X'EE''–X'EF' X'80''–X'BF'
X'F0' X'90''–X'BF'
X'F1''–X'F3' X'80''–X'BF'
X'F4' X'80''–X'8F'
• The value in the 3rd or 4th byte of the UTF-8 byte sequence was not in the legal UTF-8 range for that byte
(X'80' - X'BF').
Bidi Layout Processing for UCT T ext
A number of parameters–both outside the UCT and inside the UCT–determine how Unicode bidi layout
processing is applied to text in a UCT :
• T ext orientation. The orientation of the (i,b) coordinate system which specifies the baseline on which glyphs
are positioned and the reference inline direction for progressing text directional runs.
• Character rotation. Alignment of a character with respect to the baseline. Differentiates between horizontal
and vertical writing modes.
• Writing mode. Can be horizontal (L→R or R→L) or vertical (top->bottom or bottom->top). Determines–
together with the paragraph direction–how UCT text is positioned with respect to the current inline position
(I
c
) or with respect to an alternate inline position (I
a
).
• Bidirectional character property . A property value assigned by the Unicode standard to each character ,
including unassigned characters. V alues include strong L→R, strong R→L, weak L→R, weak R→L, and
neutral characters.
• T ext direction. Specifies the visual ordering of characters in a given directional run. The inherent directional
properties of Unicode characters dictate the text direction assigned many characters, while the Unicode bidi
layout algorithm will assign a direction to characters such as punctuation marks which have a neutral
directional property .
UCT Control Sequence

<!-- Page 148 -->

130 PTOCA Reference
• Paragraph direction. Specifies the dominant text direction for a UCT . Used as an input to the Unicode bidi
layout algorithm where it influences the ordering of directional runs and of directionally-neutral characters in
a UCT .
The processing of the Unicode complex text in a UCT occurs within the context of the PTOCA and AFP
presentation models, which define 32 ways to print text based on the 8 (i,b) text orientations and 4 character
rotations. Figure 16 on page 131 shows the 32 ways to print text in AFP environments.
UCT Control Sequence

<!-- Page 149 -->

PTOCA Reference 131
Figure 16. 32 Wa ys to Print T ext in AFP Environments
A B□C
D□E□F
D
A
E□BF□C
F□E□DC□B
A
F□E□D
C□BA
C□F
B□E
A
D
C□F
B□E
A D
C□FB□EA
D
C□F
B□E
AD
F□E□D
C□B
A
F□E□D
C□BA
C□B
A
F□E□D
C□BA
F□E□D
F□C
E□B
D A
F□C
E□B
D
A
F□CE□B
D
A
F□C
E□B
DA
C□BA
F□E□D
C□B
A
F□E□D
D A
E□B
F□C
A D
B□E
C□F
A
D
B□E
C□F
A
D
B□E
C□F
AD
B□E
C□F
D
A
E□B
F□C
DA
E□B
F□C
D□E□F
A
B□C
D□E□F
AB□C
D□E□F
A B□C
D□E□FA
B□C
A
B□C
D□E□F
AB□C
D□E□F
A
B□C
D□E□F
I□=□0°,□□B□=□90°□□□□□□□□I□=□90°,□□B□=□180°
I□=□0°,□□B□=□90°□□□□□□□□I□=□90°,□□B□=□180°
I□=□0°,□□B□=□90°□□□□□□□□I□=□90°,□□B□=□180°
I□=□0°,□□B□=□90°□□□□□□□□I□=□90°,□□B□=□180°
I□=□90°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□90°
I□=□90°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□90°
I□=□90°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□90°
I□=□90°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□90°
I□=□270°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□270°
I□=□270°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□270°
I□=□270°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□270°
 I□=□270°,□□B□=□0°□□□□□□□□I□=□180°,□□B□=□270°
Character□Rotation□=□0°
Character□Rotation□=□270°
Character□Rotation□=□180°
Character□Rotation□=□90°
I□=□0°,□□B□=□270°□□□□□□□□I□=□270°,□□B□=□180°
I□=□0°,□□B□=□270°□□□□□□□□I□=□270°,□□B□=□180°
I□=□0°,□□B□=□270°□□□□□□□□I□=□270°,□□B□=□180°
 I□=□0°,□□B□=□270°□□□□□□□□I□=□270°,□□B□=□180°
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
I□□□□□□□□□□□□B
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B□□□□□□□□□□□□I
B
I
B
I
B
I
B
I
B
I
B
I
B
I
B
I
I
B
I
B
I
B
I
B
I
B
I
B
I
B
I
B
UCT Control Sequence

<!-- Page 150 -->

132 PTOCA Reference
UCT Bidi Processing Examples
The following examples illustrate how the writing mode, text direction, and paragraph direction influence the
rendering of UCT Unicode bidi text. For all the examples, assume a character string that is stored in logical
(storage) order as:
R0 R1 R2 L3 L4 L5 R6 R7 R8 N9
where the R
i
are strong R→L characters, such as Arabic and Hebrew characters, the L
i
are strong L→R
characters, such as English (Latin) characters, and the N
i
have a neutral directional property , such as a
punctuation mark.
The text direction affe cts character ordering when processing a given directional run from logical (storage)
order into visual (rendering) order .
• The text direction of the L
i
characters is L→R. In that case the characters are rendered as:
L3 L4 L5
• The text direction of the R
i
characters is R→L. In that case the characters are rendered as:
R8 R7 R6 and R2 R1 R0
• The text direction can also be set by the BIDICT parameter on the UCT . With BIDICT values X'22'and X'23',
the text direction is fixed as either L→R or R→L, respectively , regardless of the directional property of the
characters. Care must be taken when using these BIDICT values or undesirable results may occur , as for
example, in the following cases:
– If BIDICT=X'22' (set L→R text direction), the R
i
strings are rendered as:
R0 R1 R2 and R6 R7 R8
– Similarly , if BIDICT=X'23' (set R→L text direction), the L
i
string is rendered as:
L5 L4 L3
The paragraph direction affe cts the ordering of directional runs in a UCT and of directionally-neutral characters
in a UCT . Assume that the complete string in the previous example is included in a single UCT .
• If the paragraph direction is R→L, the complete string is rendered as:
N9 R8 R7 R6 L3 L4 L5 R2 R1 R0
• If the paragraph direction is L→R, the complete string is rendered as:
R2 R1 R0 L3 L4 L5 R8 R7 R6 N9
The writing mode determines how UCT text is positioned on the current baseline. Assume the current inline
position is I
c
.
• Writing mode L→R, paragraph direction L→R:
(I
c
) R2 R1 R0 L3 L4 L5 R8 R7 R6 N9
• Writing mode R→L, paragraph direction L→R:
R2 R1 R0 L3 L4 L5 R8 R7 R6 N9 (I
c
)
• Writing mode L→R, paragraph direction R→L:
(I
c
) N9 R8 R7 R6 L3 L4 L5 R2 R1 R0
• Writing mode R→L, paragraph direction R→L:
N9 R8 R7 R6 L3 L4 L5 R2 R1 R0 (I
c
)
The alternate inline position (I
a
), if specified, can aff ect how the UCT text is positioned on the baseline as
follows.
• Writing mode L→R, paragraph direction L→R:
(I
c
) R2 R1 R0 L3 L4 L5 R8 R7 R6 N9 (I
a
)
• Writing mode L→R, paragraph direction R→L:
(I
c
) N9 R8 R7 R6 L3 L4 L5 R2 R1 R0 (I
a
)
• Writing mode R→L, paragraph direction R→L:
N9 R8 R7 R6 L3 L4 L5 R2 R1 R0 (I
c
) (I
a
)
• Writing mode R→L, paragraph direction L→R:
(I
c
(I
a
) R2 R1 R0 L3 L4 L5 R8 R7 R6 N9
UCT Control Sequence

<!-- Page 151 -->

PTOCA Reference 133
Unicode bidi layout processing is not supported in vertical T→Bor B→T writing modes. When characters that
have a horizontal directional attribute–such as Latin characters–are encountered while in a vertical writing
mode, they are presented in the same direction and at the same character rotation as the characters with a
vertical directional attribute. There is also no additional Unicode bidi layout processing for directional changes
due to embedded L→R or R→L sequences.
Positioning Considerations for UCT T ext
The above examples showing the effect of paragraph direction assume that the complete text string fits on one
line, can be included in a single UCT , and can be treated as a single paragraph. This is important because
proper rendering of Unicode bidi text requires that the Unicode bidi layout algorithm be applied to a complete
paragraph. This is not always practical. It may be necessary to break up a paragraph into multiple UCT s to
insert formatting commands for functions such as line breaks, page breaks, font changes, and color changes.
In those cases the single paragraph is segmented into multiple UCT s so that the appropriate PTOC A control
sequences can be inserted between UCT s.
Unfortunately , when a paragraph is segmented into multiple UCT s, the scope of the original paragraph is lost.
Applying a paragraph direction to each segmented UCT will, in general, not result in the correct rendering of
the original paragraph. T o avoid this, the PTOCA generator needs to invoke the Unicode bidi layout algorithm
to break the paragraph into directional runs and then package these directional runs into individual UCT s that
specify the desired text direction (BIDICT values X'22'and X'23'). For example the complete string could be
broken into the following three UCT s when a R→L paragraph direction is used. These UCT s must appear in the
data stream in the order shown to preserve the logical order of the Unicode text:
UCT1{TD=R→L} R0 R1 R2 UCT2{TD=L→R} L3 L4 L5 UCT3{TD=R→L} R6 R7 R8 N9
Since the substrings now no longer belong to the same UCT , care must be taken by the PTOCA generator to
ensure that the UCT s containing the substrings are positioned properly . Setting the writing mode to match the
paragraph direction for the complete string will minimize the need for explicit UCT positioning. In our example,
if the writing mode is R→L, the three UCT s would be rendered correctly based on the default text position
advancements:
(I
c3
) N9 R8 R7 R6 (I
c2
) L3 L4 L5 (I
c1
) R2 R1 R0 (I
c
)
where I
c
is the text position before the first UCT (UCT1) is processed, and I
c1
is the text position after UCT1 is
processed. However if the writing mode is L→R, the three UCT s would not be rendered correctly using the
default text position advancements:
(I
c
) R2 R1 R0 (I
c1
) L3 L4 L5 (I
c2
) N9 R8 R7 R6 (I
c3
)
In many circumstances explicit positioning of segmented UCT s is unavoidable, even if the writing mode
matches the paragraph direction of the complete string. Consider the need to print the character L5 in a bold
font:
UCT1{TD=R→L} R0 R1 R2
UCT2a{TD=L→R} L3 L4
<< font change >>
UCT2b{TD=L→R} L5
<< font change >>
UCT3{TD=R→L} R6 R7 R8 N9
Even with a R→L writing mode, the default positioning would result in the following incorrect rendering:
(I
c3
) N9 R8 R7 R6 (I
c2b
) L5 (I
c2a
) L3 L4 (I
c1
) R2 R1 R0 (I
c
)
In this case, UCT2a, UCT2b, and UCT3 must be explicitly positioned to maintain the correct layout, as follows.
Assume that M
i
is the movement of the text position caused by processing UCT1; that is, it is the sum of the
grapheme increments for UCT1:
UCT1{TD=R→L} R0 R1 R2
<< Relative Move Inline in (+) i-direction by M2b >>
UCT2a{TD=L→R} L3 L4
<< Relative Move Inline in (-) i-direction by {M2a + M2b}>>
UCT Control Sequence

<!-- Page 152 -->

134 PTOCA Reference
<< font change >>
UCT2b{TD=L→R} L5
<< font change >>
<< Relative Move Inline in (+) i-direction by M2a >>
UCT3{TD=R→L} R6 R7 R8 N9
This results in the correct rendering of the string:
(I
c1
) R2 R1 R0 (I
c
)
(I
c2a
) L3 L4 (I
c1
) R2 R1 R0 (I
c
)
(I
c2a
) L3 L4 (I
c2b
) L5 (I
c1
) R2 R1 R0 (I
c
)
(I
c3
) N9 R8 R7 R6 (I
c2a
) L3 L4 (I
c2b
) L5 (I
c1
) R2 R1 R0 (I
c
)
Implementation Note: The use of the ICU ParagraphLayout API is recommended for PTOCA generators that
need to segment bidi paragraphs. This APIcan provide the information needed to specify a text direction and
position for each segmented UCT . See http://site.icu-project.or .
Effect of Other PTOCA Control Sequences on UCT T ext
The unique characteristics of complex text require that some PTOCA control sequences have a different effect
on UCT text processing than they do on non-UCT text processing. These differenc es are defined as follows:
• Overstrike (OVS). T able 16 on page 134 defines which Unicode space characters are treated as PTOCA
space characters or variable space characters when an overstrike is generated. This applies to both UCT
text and Unicode-encoded non-UCT text.
• Set Intercharacter Adjustment (SIA). If glyph processing or bidi layout processing is enabled, intercharacter
adjustment is not applied to UCT text, and the current inline position is incremented for each graphic
character as follows:
I
cnew
= I
c
+ CI
In all other cases the SIA control sequence is processed the same for UCT text as for non-UCT text.
• Set T ext Orientation (ST O). If bidi layout processing is enabled and the writing mode is horizontal, characters
in complex text may be progressed in the negative i-direction as defined in T able 14 on page 128. In all other
cases the STO control sequence is processed the same for UCT text as for non-UCT text.
• Set V ariable Space Character Increment (SVI). T able 16 on page 134 defines which Unicode space
characters are mapped to the PTOCA variable space character and are assigned the increment specified by
the SVIcontrol sequence. This applies to both UCT text and Unicode-encoded non-UCT text.
• T emporary Baseline Move (TBM). If glyph processing or bidi layout processing is enabled, the precision
parameter in this control sequence is ignored for complex text, and such text is processed as if the precision
parameter were set to B'0' - actual placement method. This means that the presentation device must actually
move the baseline and position the complex text characters on the temporary baseline. In all other cases the
TBM control sequence is processed the same for UCT text as for non-UCT text.
• Underscore (USC). T able 16 on page 134 defines which Unicode space characters are treated as PTOCA
space characters or variable space characters when an underscore is generated. This applies to both UCT
text and Unicode-encoded non-UCT text.
All other PTOCA control sequences are processed the same for UCT text as for non-UCT text.
T able 16. Unicode Space Characters Mapped to PTOCA Space and V ariable Space Characters
Unicode Space Character Name Unicode Code Point PTOCA Space
Character
PTOCA V ariable
Space Character
SP ACE U+0020
yes yes
NO-BREAK SP ACE U+00A0
yes yes
UCT Control Sequence

<!-- Page 153 -->

PTOCA Reference 135
UCT Control Sequence

<!-- Page 154 -->

136 PTOCA Reference
Presentation Text Data Descriptor
The Presentation Text Data Descriptor provides initial parameters for the Presentation Text Object.
Syntax: Please see the appendixes for information about the syntax of the Presentation Text Data Descriptor
and its parameters within the MO:DCA and IPDS environments.
Semantics: A Presentation Text Data Descriptor describes a Presentation Text object. It specifies the
measurement units for the object and the size of the object. The descriptor also may describe the object
through a list of initial text conditions, which contain initial values such as the size of the inline margin.
Pragmatics: The Presentation Text Data Descriptor is optional, because its parameters may be specified by
the controlling environment or by default.
The descriptor parameters fall into the following categories:
1. Measurement units parameters:
• Unit base
• X
p
-units per unit base
• Y
p
-units per unit base
2. Size parameters
• X
p
-extent of the presentation space
• Y
p
-extent of the presentation space
3. Initial text condition parameters
The following section describes these parameters.
Measurement Unit Parameters
Semantics: These parameters specify the measurement units for the Presentation Text object space::
Unit base A number from 0 through 1
X
p
-units per unit base A number from 1 through 32,767
Y
p
-units per unit base A number from 1 through 32,767
The unit base parameter specifies the measurement base. It has the following values:
V alues Description
0 T en inches
1 T en centimeters
2-254 Reserved
If the value of the unit base parameter is not supported or is not within the range specified by PTOCA,
exception condition EC-0505 exists. The standard action is to ignore this parameter and continue presentation
with the value determined according to the hierarchy . The subset may limit the range permitted. For detailed
information about subsets, please see Chapter 6, “Compliance with PTOCA”, on page 153, Appendix A, “MO:
DCA Environment”, on page 163, and Appendix B, “IPDS Environment”, on page 169. See “Related
Publications” on page vi for data stream documentation.
Units of measure are defined as the measurement base divided by the units per unit base. That is, if the
measurement base is 10 inches and the units per unit base are 5,000, the units of measure are 10 inches /
5000 or one five-hundredth of an inch. Here are further examples.
Presentation Text Data Descriptor

<!-- Page 155 -->

PTOCA Reference 137
T able 17. Examples of Measurement Units
Units/inch Parameter V alues Comments
800 X 800 units/in. Unit base = 0
X
p
-units per unit base = 8,000
Y
p
-units per unit base = 8,000
8,000 divisions in 10 in. on both axes
80 X 77 units/cm. Unit base = 1
X
p
-units per unit base = 800
Y
p
-units per unit base = 770
800 divisions in 10 cm. on X
p
770 divisions in 10 cm. on Y
p
203.3 X 195.5
units/in.
Unit base = 0
X
p
-units per unit base = 2,033
Y
p
-units per unit base = 1,955
2,033 divisions in 10 in. on X
p
1,955 divisions in 10 in. on Y
p
240 x 240 units/in. Unit base = 0
X
p
-units per unit base = 2,400
Y
p
-units per unit base = 2,400
2,400 divisions in 10 in. on both axes
1,440 x 1,440
units/in.
Unit base = 0
X
p
-units per unit base = 14,400
Y
p
-units per unit base = 14,400
14,400 divisions in 10 in. on both axes
In row 1 of the table, the measurement base is 10 inches. The units per unit base value specifies 8,000
divisions in 10 inches, which is 800 divisions per inch. In row 2 of the table, the measurement base is 10
centimeters. The units per unit base value for the X
p
-axis specifies 800 divisions in each 10 centimeters, which
is 80 divisions per centimeter . The units per unit base value for the Y
p
-axis specifies 770 divisions per
measurement base, which is 77 divisions per centimeter . In this case, the units per unit base values dif fer
along the X
p
-axis and Y
p
-axis.
If the value of the X
p
-units per unit base parameter or the Y
p
-units per unit base parameter is not supported or
is not within the range specified by PTOCA, exception condition EC-0605 exists. The standard action in this
case is to ignore the parameter and continue presentation with the value determined according to the
hierarchy . The subset may limit the range permitted.
Measurement units specified for the X
p
-axis may be diffe rent from the measurement units specified for the Y
p
-
axis.
Pragmatics: The measurement units for the I-axis are the same as the measurement units for the X
p
,Y
p
axis
that is parallel to it. The units of the B-axis are the same as those of the other X
p
,Y
p
axis. The origin and
orientation of the I-axis and B-axis can be specified by the T ext Orientation initial text conditions, and by the Set
T ext Orientation control sequence. The values of the presentation text measurement units cannot be changed
within a Presentation Text object, but the values of presentation text orientation can be changed.
Size Parameters
Semantics: Size consists of two parameters that specify the dimensions of a Presentation Text object, that is,
the length of the X
p
-axis and the Y
p
-axis.
These dimensions, or extents, are described as follows.
X
p
-extent A number specifying the size of the Presentation Text object along the X
p
-axis.
Y
p
-extent A number specifying the size of the Presentation Text object along the Y
p
-axis.
Each extent is measured in X
p
-units or Y
p
-units. For information about the syntax of the extent fields, please
see Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS Environment”, on page 169.
If the value of the X
p
-extent parameter or the Y
p
-extent parameter is not supported or is not within the range
specified by PTOCA, exception condition EC-0705 exists. The standard action is to ignore the invalid
Presentation Text Data Descriptor

<!-- Page 156 -->

138 PTOCA Reference
parameter and continue presentation with the parameter value determined according to the hierarchy . The
subset may limit the range permitted.
These extents are used in conjunction with the measurement units to specify the size of the Presentation Text
object and for positions and displacements within the object.
Architecture Note: Note that when presentation text is processed in a MO:DCA environment where the
Presentation Text Data Descriptor (PTD) is carried in the Active Environment Group (AEG) for the page,
or when such text is processed in an IPDS environment, the Presentation Text object is bounded by the
beginning of the page and the end of the page. This is sometimes called a text major environment.
When the PTD is carried in the Object Environment Group (OEG) of a MO:DCA text object, the text
object is bounded by the Begin Presentation Text (BPT) and End Presentation Text (EPT) structured
fields. For such objects, the PTD in the AEG is ignored.
The range of supported values is receiver dependent.
Pragmatics: If the object's origin, orientation, and size are such that the object projects beyond the associated
object area, the applicable data-stream standard action or default action must be invoked. This allows the
controlling environment to control clipping.
If a control sequence, parameter , or other data element within the object causes presentation outside of the
object space, exception condition EC-0103 exists when an attempt is made to present. The standard action is
to ignore the character or control sequence that is presenting outside of the object space.
If any part of the character box for a character exceeds the boundaries of the object space, an exception
condition exists even though the character's presentation position is within the object space. Please see
“Graphic Character Processing” on page 40.
Exception Conditions: The measurement units parameters and the size parameters can cause the following
exception conditions:
• EC-0505...The value of the unit base parameter is not supported, or is not in the range specified by PTOC A.
• EC-0605...The value of the X
p
-units per unit base parameter or the Y
p
-units per unit base parameter is not
supported, or is not in the range specified by PTOCA.
• EC-0705...The value of the X
p
-extent parameter or the Y
p
-extent parameter is not supported, or is not in the
range specified by PTOCA.
• EC-0103...The contents of the Presentation Text object will cause presentation outside of the object space.
Presentation Text Flags
Semantics: This parameter is reserved. Generators should set it to zero and receivers should ignore it.
Presentation Text Data Descriptor

<!-- Page 157 -->

PTOCA Reference 139
Initial T ext Condition Parameters
The Initial T ext Condition parameters specify initial values for the Presentation Text object. They include the
following parameters:
• Baseline increment
• Coded font local ID
• Extended text color
• Initial baseline coordinate
• Initial inline coordinate
• Inline margin
• Intercharacter adjustment
• T ext color
• T ext orientation
The following pages contain the semantics and the pragmatics of the PTOCA initial text condition parameters.
For the syntax, please see Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS
Environment”, on page 169.
Baseline Increment
Baseline Increment specifies the distance between successive baselines.
Semantics: This parameter specifies the initial value of the increment in the B-direction from the current
baseline position to a new baseline position. If the value of this parameter is not supported or is not within the
range specified, exception condition EC-1 101 exists. The standard action is to ignore this parameter and
continue presentation with the value determined according to the hierarchy . The subset level may limit the
range permitted. See Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS
Environment”, on page 169 for more information about valid ranges.
The default value is the Default Baseline Increment associated with the default coded font of the device.
Pragmatics: The baseline position is incremented by this value after each Begin Line control sequence is
encountered in a text stream. If the value of this parameter is zero, each line appears superimposed over the
preceding line. This value can be overridden for the duration of a Presentation Text object by a Set Baseline
Increment control sequence.
Exception Conditions
This parameter can cause the following exception condition:
• EC-1 101...The value of the baseline increment parameter is not supported or is not in the range specified by
PTOCA.
Coded Font Local ID
C oded Font Local ID specifies a font.
Semantics: This parameter specifies the initial value of the coded font local identifier (LID). The LID is used by
the controlling environment to access a coded font when presenting subsequent text in the current
Presentation Text object. If the value of the LID is not supported or is not within the range specified, exception
condition EC-0C01 exists. The standard action is to ignore this parameter and to use the active coded font and
character attributes determined according to the hierarchy . The subset level may also limit the range permitted.
See Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS Environment”, on page 169 for
more information about valid ranges.
The default value is the LID of the default coded font of the device.
Presentation Text Data Descriptor

<!-- Page 158 -->

140 PTOCA Reference
Pragmatics: The LID is equated to a coded font, character rotation, and font modification parameters by a
mapping function in the controlling environment. If the text orientation is changed and the coded font selected
by this parameter is not compatible with the new orientation, when an attempt is made to present a character
from the selected coded font, exception condition EC-3F02 exists. The standard action is to complete the
presentation at this presentation position using the receiver's default font. This results in the best possible
presentation within the limits of the receiver's capability . If there is an intercharacter adjustment value, it is not
applied. The measurement units used in the object and those used by the coded font selected for presentation
are assumed to be compatible. If the measurement units are not compatible, the standard action is to present a
best fit of the character and continue processing. A best fit could result in no mark or unintelligible marks on the
presentation surface. Incompatibility of measurement units is not an exception condition in PTOCA.
Exception condition EC-1802 occurs in the following cases:
• The Coded Font Local ID parameter is present in the Presentation Text Data Descriptor but a corresponding
mapping function does not exist in the controlling environment.
• An equate of the local identifier to a global identifier does not exist, or substitution parameters do not exist, in
the controlling environment. The coded font identified by the map is not available in the receiver .
The standard action is to substitute the receiver's default font for the requested font and continue processing.
Exception Conditions
This parameter can cause the following exception conditions:
• EC-0C01...The value of the LID parameter is not supported or is not in the range specified by PTOCA.
• EC-3F02...The font is not compatible with the text orientation.
• EC-1802...The font requested cannot be provided.
Extended T ext Color
The Extended T ext Color parameter specifies a process or highlight color value and defines the color space
and encoding for that value. The specified color value is applied to foreground areas of the text presentation
space. Foreground areas consist of the following:
• The stroked and filled areas of solid text characters, including overstrike characters. With hollow characters,
only the stroked portion of the character is considered foreground.
• The stroked area of a rule.
• The stroked area of an underscore.
All other areas of the text presentation space are considered background.
Semantics: Refer to “Set Extended T ext Color (SEC)” on page 90 for a description of the parameter
semantics. The controlling environment may limit the range permitted. See “Related Publications” on page vi
for the appropriate data-stream documentation. Note that the Extended T ext Color parameter is not supported
as an initial text condition in IPDS Environments, see “Presentation Text Data Descriptor for T ext-Major T ext”
on page 170.
Pragmatics: If the receiver does not support the specified color value exception condition EC-0E03 exists.
The standard action in this case is to use the presentation device default color .
Exception Conditions
This parameter can cause the following exception conditions:
• EC-0E02...Invalid or unsupported color space.
• EC-0E03...Invalid or unsupported color value.
• EC-0E04...Invalid percent value.
• EC-0E05...Invalid or unsupported number of bits in a color component.
Presentation Text Data Descriptor

<!-- Page 159 -->

PTOCA Reference 141
Initial Baseline Coordinate
The Initial Baseline Coordinate specifies the point on the B-axis within the object space to which the current
addressable position will be initialized. This value is called the B-displacement.
Semantics: This parameter specifies a displacement in the B-direction from the I-axis for the initial
addressable position. If the value of the B-displacement is not supported or is not within the range specified,
exception condition EC-6B02 exists. The standard action in this case is to ignore the invalid parameter and
continue presentation with the value determined according to the hierarchy . The subset level may also limit the
range permitted. See Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS
Environment”, on page 169 for more information about valid ranges.
The default value is device-dependent.
Pragmatics: If the value of the B-displacement is X'0000', the initial addressable position in the B-direction is
at the I-axis. This does not affect the inline margin.
Exception Conditions
This parameter can cause the following exception condition:
• EC-6B02...The value of the B-displacement parameter is not supported or is not in the range specified by
PTOCA.
Initial Inline Coordinate
The Initial Inline Coordinate specifies the point on the I-axis within the object space to which the current
addressable position will be initialized. This value is called the I-displacement.
Semantics: This parameter specifies a displacement in the I-direction from the B-axis for the initial
addressable position. If the value of the I-displacement is not supported or is not within the range specified,
exception condition EC-6A02 exists. The standard action in this case is to ignore the invalid parameter and
continue presentation with the value determined according to the hierarchy . The subset level may also limit the
range permitted. See Appendix A, “MO:DCA Environment”, on page 163 and Appendix B, “IPDS
Environment”, on page 169 for more information about valid ranges.
The default value is zero.
Pragmatics: If the value of the I-displacement is X'0000', the initial addressable position in the I-direction is at
the B-axis. This does not affect the inline margin.
Exception Conditions
This parameter can cause the following exception condition:
• EC-6A02...The value of the I-displacement parameter is not supported or is not in the range specified by
PTOCA.
Inline Margin
Inline Margin specifies the position of the inline margin.
Semantics: This parameter specifies the initial value for the location of the inline margin. This value is a
displacement in the I-direction from the B-axis to the inline margin in the current Presentation Text object. If this
parameter is not supported or is not within the range specified, the exception condition EC-1001 exists. The
standard action is to ignore this parameter and continue presentation with the value determined according to
the hierarchy . The subset level may limit the range permitted. See Appendix A, “MO:DCA Environment”, on
page 163 and Appendix B, “IPDS Environment”, on page 169 for more information about valid ranges.
Presentation Text Data Descriptor

<!-- Page 160 -->

142 PTOCA Reference
The default value is zero.
Pragmatics: The addressable position for the first line of presentation text is specified by the Initial Baseline
Coordinate and Initial Inline Coordinate initial text conditions. After each Begin Line control sequence is
processed, the addressable position is placed at the inline margin. If the value of the inline margin parameter is
X'0000', the inline margin is at the B-axis.
Exception Conditions
This parameter can cause the following exception condition:
• EC-1001...The displacement parameter is not supported or is not in the range specified by PTOCA.
Intercharacter Adjustment
Intercharacter Adjustment specifies additional increment or decrement between graphic characters.
Semantics: The adjustment parameter specifies the initial value of additional space between graphic
characters. This space is in the I-direction from the end of the current character increment to the presentation
position of the following graphic character . When this value is positive, the adjustment is called an increment.
When the value is negative, the adjustment is called a decrement. The direction parameter specifies the
direction in which the intercharacter adjustment is to be applied. Intercharacter increment, which occurs when
the direction parameter is X'00', is applied in the positive I-direction. Intercharacter decrement, which occurs
when the direction parameter is X'01', is applied in the negative I-direction. The default value for the direction
parameter is X'00'.
Intercharacter adjustment is not applied before or after the following:
• A space character or variable space character
• A Begin Line control sequence
• A Relative Move Inline control sequence
• An Absolute Move Inline control sequence
For non-incrementing characters, the adjustment is applied from the current addressable position to locate the
presentation position of the non-incrementing character , but the current addressable position is unchanged.
The effect is that the non-incrementing character may be coupled with a graphic character , resulting in an
overstrike function, and the adjustment is applied to the coupled characters.
If the value of the adjustment parameter or the direction parameter is not supported or is not within the range
specified, exception condition EC-1201 exists. The standard action in this case is to ignore the Intercharacter
Adjustment parameter and continue presentation with the parameter values determined according to the
hierarchy . The subset level may also limit the range permitted. See Appendix A, “MO:DCA Environment”, on
page 163 and Appendix B, “IPDS Environment”, on page 169 for more information about valid ranges.
The default value is zero for both the adjustment parameter and the direction parameter .
Pragmatics: If the value of the adjustment parameter is X'0000', no additional intercharacter increment or
decrement appears between graphic characters.
Exception Conditions
This parameter can cause the following exception conditions:
• EC-1201...The value of the adjustment parameter or the direction parameter is not supported, or is not in the
range specified by PTOCA.
Presentation Text Data Descriptor

<!-- Page 161 -->

PTOCA Reference 143
T ext Color
The T ext Color parameter specifies a named color that selects the foreground color of subsequent text
characters, rules, and underscores.
Architecture Note: Pre-year 2000 applications and printers support an optional PRECSION parameter for text
color . This parameter has been retired. It should not be generated by new applications, and should be
ignored by new printers. For a definition of this parameter , see “Retired Parameters” on page 177.
Semantics: The named color value is applied to foreground areas of the text presentation space. Foreground
areas consist of the following:
• The stroked and filled areas of solid text characters, including overstrike characters. With hollow characters,
only the stroked portion of the character is considered foreground.
• The stroked area of a rule.
• The stroked area of an underscore.
All other areas of the text presentation space are considered background. Please refer to T able 13 on page
103 for the foreground color values and their associated colors. The default color attribute value is X'FF07'.
If the value of the foreground color attribute is not supported or is not within the range specified by PTOCA,
exception condition EC-5803 exists. The standard action is to ignore these parameters and continue
presentation with the value determined according to the hierarchy . The controlling environment may limit the
range permitted. See “Related Publications” on page vi for the appropriate data-stream documentation.
Pragmatics:
The default color attribute value (FRGCOLOR = X'FFFF') is determined hierarchically . The following order
applies:
1. V alue set by T ext Color initial text condition parameter in Descriptor
2. PTOCA default – X'FF07'.
The device default value is the receiver ’ s default. For example, characters, rules, and underscores will be
presented in black on a receiver which supports only black. The receiver ’ s best possible value means that if
the receiver has limited color capabilities, then it may substitute a color it supports for one it does not support.
For example, the receiver may substitute red for pink, blue for turquoise, and so forth.
The color attribute values X'FF00' to X'FF06' are translated to X'0000' to X'0006' and treated exactly like those
colors. The PTOCA default value of X'FF07' is the device default. For example, characters, rules, and
underscores will be presented in black on a device which supports only black. A color attribute value of X'FF08'
means that the receiver's default background color should be used for the foreground color . This provides an
erase function.
Exception Conditions
This parameter can cause the following exception condition:
• EC-5803...The foreground color parameter (FRGCOLOR) value is invalid, or the specified color is not
supported.
T ext Orientation
T ext Orientation consists of two parameters that establish the I-direction and the B-direction for presentation
text.
Semantics: These parameters specify the initial I-axis and B-axis orientations with respect to the X
p
-axis.
These orientations are expressed in degrees and minutes. The same format is used for both orientations. Each
is a two-byte, three-part code of the form ABC.
Presentation Text Data Descriptor

<!-- Page 162 -->

144 PTOCA Reference
• A is a nine-bit binary number (bits 0 - 8) which provides from 0 through 359 degrees. V alues from 360
through 51 1 are invalid.
• B is a six-bit binary number (bits 9 - 14) which provides from 0 through 59 minutes. V alues from 60 through
63 are invalid.
• C is a one-bit reserved field (bit 15) which must be B'0'.
A value of X'0000' for the I-axis orientation parameter indicates that the positive direction is parallel to the X
p
-
axis. Increasing values for these parameters indicate increasing clockwise rotation.
The default value for the I-axis is X'0000', that is, zero degrees. The default value for the B-axis is X'2D00', that
is, 90 degrees. The maximum value for IORNTION and BORNTION is X'B3F6' or B'101 1001 1 1 1 1 101 10', which
is 359 degrees and 59 minutes.
Pragmatics: I-direction is the direction in which additional characters appear in a line of text. B-direction is the
direction in which additional lines of text appear . If the I-axis is not parallel to either the X
p
-axis or Y
p
-axis,
exception condition EC-6802 exists. If the B-axis is not parallel to either the X
p
-axis or Y
p
-axis, exception
condition EC-6902 exists. The standard action in this case is to set the I-axis equal to the X
p
-axis direction and
the B-axis equal to the Y
p
-axis direction. The origin of the I-axis and the B-axis is always one of the four corners
of the object space.
Orientations other than 0,90 are valid, but may be constrained by receiver limitations or parameters in
controlling environment. If the I-axis orientation is not supported by the receiver , exception condition EC-6802
exists. If the B-axis orientation is not supported by the receiver , exception condition EC-6902 exists. The
standard action is to use 0,90 degrees. These exception condition codes and standard actions also apply to
orientation values not within the range specified by PTOCA.
Exception Conditions
This parameter can cause the following exception conditions:
• EC-6802...The I-axis is not parallel to the X
p
-axis or the Y
p
-axis.
• EC-6902...The B-axis is not parallel to the X
p
-axis or the Y
p
-axis.
Presentation Text Data Descriptor

<!-- Page 163 -->

Copyright © AFP Consortium 1997, 2025 145
Chapter 5. Exception Handling in PTOCA
This chapter:
• Describes exception condition detection
• Describes exception responses and standard actions
• Lists the PTOCA exception condition codes
Faithful Reproduction
PTOCA is intended to be precise enough to permit multiple products to reproduce the Presentation Text object
faithfully . Faithful reproduction includes such aspects as the size and relative positions of graphic characters
and strings of graphic characters. Examples are the placement of columns in tables, mathematical constructs
such as subscripts or limits of integrals, and the appearance of graphic characters. PTOCA can only make
faithful reproduction possible. The responsibility for faithful reproduction belongs to the process that presents
the object.
PTOCA is also designed to permit less than faithful reproduction of the Presentation Text object. It is possible
to specify those exception conditions for which continuation of processing is acceptable. This permits a
process that cannot faithfully reproduce the object to continue with its best approximation. If less than faithful
reproduction is acceptable for an application, interchange among a larger set of receivers is possible.
If a requirement for faithful reproduction is specified, and if a process cannot present a faithful reproduction,
reproduction is not continued.
T o satisfy these objectives, PTOCA anticipates the existence of exception conditions, specifies how each is to
be handled so that results are predictable, and lets the controlling environment control exception condition
actions.
Exception Conditions
An exception condition in the object is the appearance of the following:
• Invalid or unsupported parameter value
• Invalid or unsupported parameter
• Invalid or unsupported control sequence
PTOCA specifies valid values for parameters, appropriate and inappropriate parameters, and valid and invalid
combinations of control sequences. In addition, an implementation may accept only a subset of valid values or
only a subset of appropriate parameters and control sequences. However , PTOC A specifies, by subsetting,
which of its controls and parameters are to be supported by the implementations of a subset.
Exception conditions can be classified as:
• Syntactic
• Semantic
• Pragmatic
A syntactic exception condition is a violation of a structural architectural specification.
Syntactic exception conditions defined for PTOCA include:
• Invalid control sequence
• Invalid parameter value
• Control sequence appearing in invalid context

<!-- Page 164 -->

146 PTOCA Reference
A semantic exception condition is a violation of a functional architectural specification, that is, what a
parameter , structured field, or control sequence does, independent of how it looks or how it is used.
Semantic exception conditions defined for PTOCA include:
• Selection of inconsistent or contradictory functions
• Loss of presentation information
A pragmatic exception condition is an incorrect usage of an architectural specification that is valid structurally
and semantically . It is normally caused by an incompatibility between a Presentation Text object and a product
that processes or presents it. Pragmatic exception conditions are not defined by PTOCA and cannot be
detected by inspection of a Presentation Text object.
Pragmatic exception conditions include:
• Mismatch of characteristics of Presentation Text object and presentation product
• Unavailable resource, for example, coded font
• Unavailable function, for example, overstrike
• Unsupported control sequence
A product may be unable to distinguish between a syntactic exception and a pragmatic exception, for example,
between an invalid parameter value and a parameter value out of the product's range.
Exception Condition Detection
A potential exception condition may exist, yet not be detected during processing of a Presentation Text object.
A receiver is not required to process a Presentation Text object beyond its need to perform a specified function.
Therefore, a process usually detects only those exception conditions that pertain to the function it is
performing. PTOCA defines specific exception conditions that occur within the object, and enables the
controlling environment to provide a continuation capability by specifying standard action values to use when
no other source is available for the parameter values. In addition, PTOCA does not require that an
implementation of the controlling environment do anything more than receive exception conditions and
terminate processing the Presentation Text object as a result of them. However , the controlling environment
may place more stringent requirements.
Syntactic exception conditions can be detected without regard to the value of any other parameter or
structured field. A syntactic or semantic exception condition can be detected by inspection of a Presentation
T ext object. A pragmatic exception condition cannot be detected by inspection of a Presentation Text object
alone, but requires knowledge of characteristics of the receiver . If a product that produces or processes a
Presentation Text object knows the characteristics of one or more receivers, it can avoid or detect pragmatic
exception conditions. If it does not, this detection must be performed by the receiver .
Exception Condition Handling
All processors of a Presentation Text object need not have the same capability for detecting, processing, or
reporting exception conditions. Processors of similar capabilities may handle the same exception condition in
diffe rent ways. A processor may provide alternative ways to handle an exception condition; however , the
processor's capabilities are limited by PTOCA.
The controlling environment of a Presentation Text object can specify the response a receiver should make
when exception conditions are encountered. This is specified in structures contained in the controlling
environment. These structures identify one or more exception conditions, and specify the exception response
or effe ct the exception condition will have on the document presentation process. For example, a document
may be terminated when an exception condition is encountered, or processing may continue using the
architected standard action for the exception condition.
A standard action is specified in PTOCA for many exception conditions. For example, if an implementation
cannot process some of the Presentation Text object, the standard action could be to present it with
Exception Conditions

<!-- Page 165 -->

PTOCA Reference 147
unrecognized control sequences omitted or with specified valid parameters substituted for invalid parameters.
The standard actions are defined independent of where the exception condition is detected. That is, the
receiver may be an application, program product, mechanical device, and so forth. The process always
initiates the specified action, and is responsible for its satisfactory completion.
Exception Responses
When an exception condition is detected by a receiver , an exception response is assumed. Exception
responses are not specified by PTOCA. The exception condition codes specified are for reference purposes. If
the controlling environment specifies a different exception condition code for the same exception condition, the
controlling environment's exception condition code overrides the code specified by PTOCA. For example, if a
DBR control sequence will cause a rule to extend outside the boundaries of the object space, PTOCA specifies
that exception condition code EC-0103 be recognized. However , in the IPDS environment, exception ID
08C1..00 would be recognized. Please see Appendix A, “MO:DCA Environment”, on page 163 and Appendix
B, “IPDS Environment”, on page 169. See “Related Publications” on page vi for data-stream documentation.
Some exception responses can be common to all exception conditions. Others are specific to particular
exception conditions.
Exception responses that can be common to all exception conditions include the following:
• T erminate processing Presentation Text object
• Ignore the control that caused the exception condition and continue processing the object
• Partially process the control that caused the exception condition
• Report exception condition back to generator or forward it to the presenter of the object
• Cause an intervention-required condition to occur at the receiver
• Mark the presentation information with diagnostic information
An example of an exception response that can vary depending on the exception condition is to present the
data that caused the exception condition. The data presented may be a receiver-dependent approximation if
faithful reproduction is not required. The data presented may be a special, recognizable marker instead of, or
in addition to, other data at the position of an exception condition. For example, a blotch may appear where a
rule is drawn beyond an object space extent. Another exception response is to present no data at the position
of an exception condition.
A response for each exception condition may be selected in a manner independent of any other exception
condition. Multiple responses may be selected for one exception condition. Certain exception condition actions
are mutually exclusive by their nature. PTOCA assumes that the controlling environment provides structures
external to the Presentation Text object for handling the responses to exception classes or specific exception
conditions received from the object processor .
Standard Actions
PTOCA specifies the standard actions that it assumes to be taken by the Presentation Text object processor
for specific exception conditions that can occur in the object. The receiver is expected to implement the
PTOCA standard action for those exception conditions it can recognize as part of the support of the subset
level. If the controlling environment specifies a different standard action for the same exception condition, the
action specified by the controlling environment overrides the action specified by PTOCA. For example, if an
invalid control sequence generates an exception condition, PTOCA may specify that the presentation process
ignore the invalid control sequence and continue presenting. However , for the same exception condition, IPDS
may require the presentation process to stop processing the Presentation Text object. Thus the PTOCA
processor has two options when it recognizes an exception condition: it can simply report the exception
condition to the controlling environment, and let the environment handle it; or it can apply the PTOCA standard
action.
Exception Conditions

<!-- Page 166 -->

148 PTOCA Reference
Exception Condition Codes
The following list contains the exception conditions that implementations of PTOCA are obligated to test for .
The codes are consistent with those of the data streams, and conform to the registry of exception condition
codes found in IPDS. Please see the appendixes for information about exception conditions in the controlling
environment.
T able 18. PTOCA Exception Conditions
PTOCA
Exception
Condition
Meaning Comments
EC-0001 Invalid text control.
• Invalid or unsupported function type in control sequence.
• Invalid control sequence or initial text condition parameter .
• Invalid or unsupported initial text condition parameter identifier .
• Control sequence or initial text condition parameter is not in the
supported subset.
EC-0103 Character box exceeds
object space.
• A character has been positioned so that a portion of its character box
will exceed the object space in the I-direction or the B-direction.
• Caution - this exception condition is applicable only within a valid object
area. If the boundary of the object space coincides with or extends
beyond the boundary of the object area, the appropriate data-stream
exception may take precedence over this exception condition.
EC-0201 Invalid LID in ESU.
• The active BSU LID is not the same as the LID specified in the ESU.
• No active BSU LID when an ESU is processed.
EC-0401 The end of the object is
encountered before a text
suppression has ended.
EC-0505 PTD unit base specified is
not a valid or supported
value.
EC-0601 Nested BSU.
• BSU is encountered before the previous suppression has ended.
EC-0605 PTD X
p
- or Y
p
–units per
unit base specified is not
a valid or supported value.
EC-0705 PTD X
p
- or Y
p
–extent
specified is not a valid or
supported value.
EC-0C01 Coded Font LID specified
is not a valid or supported
value.
EC-0E02 Invalid or unsupported
color space in SEC.
EC-0E03 Invalid or unsupported
color value in SEC.
EC-0E04 Invalid percent value in
SEC.
The percent coverage parameter or the percent shading parameter in the
SEC (Highlight color space) contains an invalid value.
EC-0E05 Invalid or unsupported
number of bits for a color
component in SEC.
Exception Conditions

<!-- Page 167 -->

PTOCA Reference 149
T able 18 PTOCA Exception Conditions (cont'd.)
PTOCA
Exception
Condition
Meaning Comments
EC-0F01 Invalid text orientation in
ST O.
• Baseline or inline orientation specified is not a valid or supported value.
• The I and Borientations are identical.
• Neither the I-direction nor the B-direction is parallel to the X
p
–direction.
EC-1001 SIM displacement
specified is not a valid or
supported value.
EC-1 101 SBI baseline increment
specified is not a valid or
supported value.
EC-1201 SIA adjustment or
direction specified is not a
valid or supported value.
EC-1301 AMB displacement
specified is not a valid or
supported value.
EC-1401 AMI displacement
specified is not a valid or
supported value.
EC-1403 Advancement of the
baseline coordinate
toward the I-axis is
specified but is not
supported.
EC-1501 RMI increment specified is
not a supported value.
EC-1601 RMB increment specified
is not a supported value.
EC-1701 SVI increment specified is
not a valid or supported
value.
EC-1802 Invalid Coded Font LID.
• The necessary mapping is not provided to support the specified coded
font.
• The specified coded font is not available to the receiver .
EC-1901 Invalid or unsupported
RPS target count.
• The target count parameter for RPS is invalid or unsupported.
EC-1A01 RPS or TRN source string
length error or UCT data
length error .
• The data string length for TRN or RPS is an odd number . It must be
even for double-byte fonts.
• The byte count specified for code points following UCT is an odd
number . It must be even for double–byte encoded data.
Exception Conditions

<!-- Page 168 -->

150 PTOCA Reference
T able 18 PTOCA Exception Conditions (cont'd.)
PTOCA
Exception
Condition
Meaning Comments
EC-1A03 Invalid Unicode data.
• A high-order surrogate code value was not immediately followed by a
low-order surrogate code value. The high-order surrogate range is
U+D800 through U+DBFF .
• A low-order surrogate code value was not immediately preceded by a
high-order surrogate code value. The low-order surrogate range is
U+DC00 through U+DFFF .
• An illegal UTF-8 byte sequence, as defined in the Unicode 3.2
Specification, was specified. For a discussion of illegal UTF-8 byte
sequences, see the Application Note on page 129.
EC-1B01 RPS target string length
error .
• The RPS repeat length is an odd number when a double-byte font is
active. It must be even for double-byte fonts.
EC-1C01 Invalid control sequence
class.
• The class of a X'2B' control sequence is not X'D3'.
EC-1E01 Invalid length for control
sequence or initial text
condition parameter .
• A required parameter has not been not specified.
• Invalid control sequence or initial text condition parameter length.
• Part of an optional parameter in a control sequence is missing.
• A Coded Font LID has been omitted in a SCFL control sequence or in a
Coded Font Local ID initial text condition parameter .
• SVIcontrol sequence increment parameter is missing.
• DBR or DIR length parameter is missing.
• SIM displacement parameter is missing.
• I-orientation parameter or B-orientation parameter is missing in an ST O
control sequence.
EC-1F01 RPS length error .
• The RPS control sequence length is four and the repeat length is not
zero.
EC-2100 Invalid character .
EC-3F02 T ext orientation is
incompatible with selected
font.
EC-5803 An STC color or color
attribute cannot be
supported as specified.
EC-6802 Initial I-orientation
specified is not a valid or
supported value.
EC-6902 Initial B-orientation
specified is not a valid or
supported value.
EC-6A02 Initial I-displacement
specified is not a valid or
supported value.
EC-6B02 Initial B-displacement
specified is not a valid or
supported value.
EC-8002 DBR or DIR rule width
specified is not a valid or
supported value.
Exception Conditions

<!-- Page 169 -->

PTOCA Reference 151
T able 18 PTOCA Exception Conditions (cont'd.)
PTOCA
Exception
Condition
Meaning Comments
EC-8202 DBR or DIR rule length
specified is not a valid or
supported value.
EC-9801 BSU or ESU LID specified
is not a valid or supported
value.
EC-9803 TBM error .
• Receiver is unable to support TBM by printing full size characters.
• Receiver cannot support substitution character in the TBM field.
• T emporary move size exceeds the device limitations.
• Substitution method receiver cannot support multi-offset temporary
baselines.
EC-9A01 OVS selected overstrike
character that has an
invalid character
increment or is a non-
printing character .
• Character increment of overstrike character is less than or equal to
zero.
• Character increment of overstrike character is less than the character-
box size.
• Overstrike character is a non-printing character .
EC-9B01 UCT parameter values for
CTLNGTH, UCTVERS,
BIDICT , or GL YPHCT are
invalid.
EC-9D01 Decryption is not available
on this device.
EC-9D02 Decryption reported an
error .
EC-9D03 No key information has
been set for decryption.
Exception Conditions

<!-- Page 170 -->

152 PTOCA Reference

<!-- Page 171 -->

Copyright © AFP Consortium 1997, 2025 153
Chapter 6. Compliance with PTOCA
This chapter:
• Describes the base level of PTOCA
• Describes the PT1 subset of PTOCA
• Describes the PT2 subset of PTOCA
• Describes the PT3 subset of PTOCA
• Describes the PT4 subset of PTOCA
• States general requirements for compliance
Base Level
The mandatory base level contains functions and facilities required by all implementations. By itself the
mandatory base level is not sufficient; it must always be supplemented by a higher subset such as PT1, PT2,
or PT3. The base level represents the base set of functions defined within PTOCA. It is the minimum set of
functions required to be supported in any environment, and consists of the following minimum general
communication capabilities:
• Recognition of control sequences, chained or unchained
• Interpretation and validation of the control sequence
• Rejection of control sequences and parameters, including return of error data, that are not supported within
the supported subset
• Reporting, on request from the controlling environment, the supported features
• Reporting exception conditions to the controlling environment

<!-- Page 172 -->

154 PTOCA Reference
PT1 Subset
The following table shows the control sequences that are valid for a PT1 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT1 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-X'7F'
NOP 4-256 IGNDA T A 7
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'7F' 3
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00' 9
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'FF07' 3,5
6 PRECSION X'00'-X'01' 3,10
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TRN 4-256 TRNDA T A 8
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT1 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
PT1 Subset

<!-- Page 173 -->

PTOCA Reference 155
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT1 range is X'FF07'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. The
data is ignored.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data will be presented as a character string.
9. The default indicator is not allowed for this parameter in this subset.
10. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it..
PT1 Subset

<!-- Page 174 -->

156 PTOCA Reference
PT2 Subset
The following table shows the control sequences that are valid for a PT2 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT2 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-'X'7F'
NOP 4-256 IGNDA T A 7
OVS 4 BYPSIDEN X'00'-X'0E' 3
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'FE' 3
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00'-X'01' 3
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'0000', X'FF00', X'FF07', X'FFFF' 3,5
6 PRECSION X'00'-X'01' 3,9
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TBM 4 DIRCTION X'00'-X'03' 3
5 PRECSION X'00'-X'01' 3
6-7 INCRMENT X'0000'-X'7FFF' 3,4
TRN 4-256 TRNDA T A 8
USC 4 BYPSIDEN X'00'-X'0E' 3
PT2 Subset

<!-- Page 175 -->

PTOCA Reference 157
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT2 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT2 range is X'0000', X'FF00',
X'FF07', and X'FFFF'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
9. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it.
PT2 Subset

<!-- Page 176 -->

158 PTOCA Reference
PT3 Subset
The following table shows the control sequences that are valid for a PT3 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT3 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-'X'7F'
NOP 4-256 IGNDA T A 7
OVS 4 BYPSIDEN X'00'-X'0E' 3
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'FE' 3
SEC 5
10
1 1
12
13
14-17
COLSPCE
COLSIZE1
COLSIZE2
COLSIZE3
COLSIZE4
COL V ALUE
X'01', X'04', X'06'
X'08', X'40'
X'01'-X'08', X'10'
X'00'-X'08'
X'00'-X'08'
X'00'-X'08'
Full range allowed by
the color space
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00'-X'01' 3
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'0000', X'FF00', X'FF07', X'FFFF' 3,5
6 PRECSION X'00'-X'01' 3,9
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TBM 4 DIRCTION X'00'-X'03' 3
PT3 Subset

<!-- Page 177 -->

PTOCA Reference 159
Control Sequence Offset (Unchained) Parameter PT3 Range Notes
5 PRECSION X'00'-X'01' 3
6-7 INCRMENT X'0000'-X'7FFF' 3,4
TRN 4-256 TRNDA T A 8
USC 4 BYPSIDEN X'00'-X'0E' 3
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT3 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT3 range is X'0000', X'FF00',
X'FF07', and X'FFFF'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
9. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it..
PT3 Subset

<!-- Page 178 -->

160 PTOCA Reference
PT4 Subset
The following table shows the control sequences that are valid for a PT4 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT4 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 3
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 3
BLN 5
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'8000'-X'7FFF' 1, 3
6-7
8
RWIDTH X'8000'-X'7FFF'
X'00'-X'FF'
1,2
DIR 4-5 RLENGTH X'8000'-X'7FFF' 1,3
6-7
8
RWIDTH X'8000'-X'7FFF'
X'00'-X'FF'
1,2
ESU 4 LID X'01'-'X'7F'
GAR 4–253 (chained) ADV ANCE X'0000'-'X'FFFF' 3
GIR 4–253 (chained) GL YPHID X'0000'-'X'FFFF'
GLC 4-5 IADVNCE X'8000'-X'7FFF' 1, 3
6 OIDLGTH 0, 13-129
7 FFNLGTH 0-(255-(10+OIDLGTH))
12 -n FONTOID (first byte) X'06'
(n+1)–p FFONTNME valid UTF-16BE code points
GOR 4–253 (chained) OFFSET X'8000'-X'7FFF' 1
NOP 4-256 IGNDA T A 6
OVS 4 BYPSIDEN X'00'-X'0E' 2
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,3
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,3
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 7
SBI 4-5 INCRMENT X'0000'-X'7FFF' 2,3
SCFL 4 LID X'00'-X'FE' 2
SEC 5
10
1 1
12
13
14-17
COLSPCE
COLSIZE1
COLSIZE2
COLSIZE3
COLSIZE4
COL V ALUE
X'01', X'04', X'06'
X'08', X'40'
X'01'-X'08', X'10'
X'00'-X'08'
X'00'-X'08'
X'00'-X'08'
Full range allowed by
the color space
PT4 Subset

<!-- Page 179 -->

PTOCA Reference 161
Control Sequence Offset (Unchained) Parameter PT4 Range Notes
SIA 4-5 ADJSTMNT X'0000'-X'7FFF' 2,3
6 DIRCTION X'00'-X'01' 2
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 2,3
STC 4-5 FRGCOLOR X'0000'-X'0010', X'FF00'-X'FF08',
X'FFFF'
2,4
6 PRECSION X'00'-X'01' 2,8
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 2
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 2
SVI 4-5 INCRMENT X'0000'-X'7FFF' 2,3
TBM 4 DIRCTION X'00'-X'03' 2
5 PRECSION X'00'-X'01' 2
6-7 INCRMENT X'0000'-X'7FFF' 2,3
TRN 4-256 TRNDA T A 7
UCT 9
USC 4 BYPSIDEN X'00'-X'0E' 2
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The default indicator is allowed, meaning obtain a value from the hierarchy .
3. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
4. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT4 range is the full Standard OCA
Color V alue T able found in the MO:DCA Reference.
5. The Begin Line (BLN) control sequence has no parameters.
6. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
7. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
8. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it.
9. The UCT must be chained to a GAR or GOR and is not rendered; all parameters are ignored.
PT4 Subset

<!-- Page 180 -->

162 PTOCA Reference
General Requirements for Compliance
In claiming support as a PTOCA receiver , a product is stating that it has identified the subsets sufficient for its
needs, and has implemented all mandatory and optional control sequences, parameters, and ranges within
those subsets, including support of the PTOCA default values specified for those control sequences and
parameters. A receiver may also support additional function beyond the PTOCA subset that it claims to
support.
In claiming support as a PTOCA generator , a product is stating that it has identified which subset is suffic ient
for its needs, and that the Presentation Text object content it produces is limited to the control sequences and
parameters defined in that subset. Additionally , it is stating that it has not violated the syntactic, semantic, and
pragmatic restrictions specified in PTOCA. There is no requirement that any control sequence or parameter
must appear in the object.
A receiver need not check the Presentation Text object for compliance to the PTOCA subset beyond what the
receiver requires for the processing that represents its function. A printer , for example, normally checks all the
control sequences, parameters, and ranges within the object it is presenting. But a transform product might
only need to check the structured field introducers. A receiver may optionally provide warnings if it detects non-
compliance.
General Requirements for Compliance

<!-- Page 181 -->

Copyright © AFP Consortium 1997, 2025 163
Appendix A. MO:DCA Environment
This appendix describes how Presentation Text objects may be included within a MO:DCA document for the
purpose of interchanging Presentation Text objects between a generating node and a receiving node. See
Mixed Object Document Content Architecture Reference, AFPC-0004, for a full description of the MO:DCA
architecture.
The Presentation Text Data Descriptor (PTD) and Presentation Text Data (PTX) structured fields, which carry
Presentation Text objects in MO:DCA architecture documents, are defined in the following pages.
T o guarantee interchange, a MO:DCA document carrying a Presentation Text object must include all
information related to the object. The MO:DCA document must therefore contain not only the definition of the
Presentation Text object, but it must also provide linkage to the resources the object references.
The discussion of MO:DCA structured fields is included in this appendix solely for setting the context of their
use by PTOCA.
Compliance with MO:DCA Interchange Sets
When Presentation Text objects are interchanged with the purpose of outputting the objects on a display ,
printer , or other output device, it is very important that visual fidelity be maintained as far as possible. In an
attempt to satisfy this objective, PTOC A defines the following for the MO:DCA environment:
• A set of rules that must be followed by all generators when constructing Presentation Text objects.
• A set of Presentation Text processing capabilities that are guaranteed to be supported by all receivers.
In order to comply with a particular MO:DCA Interchange Set, products that generate Presentation Text objects
must only generate objects that contain Presentation Text items and values defined in that interchange set.
Including items or values not in the interchange set can result in processing exceptions being raised by the
receiving processor , and exception actions being taken. However , a generator must not rely on a receiver
taking these actions.
In order to conform to a particular MO:DCA Interchange Set, products that receive Presentation Text objects
and convert them using a processor for output to some device are required to support all the Presentation Text
facilities defined in that interchange set.
It is optional whether the processor in the receiving node checks each Presentation Text object for compliance
with the relevant interchange set. A receiver can optionally raise warnings, in the form of errors, if a non-
compliant Presentation Text object is detected.
The Presentation Text object space is the presentation space within which the object generator may position
graphic characters without error , and it is the responsibility of the generator to ensure that the graphic
characters it places in the object space are positioned so that they do not exceed the object space.

<!-- Page 182 -->

164 PTOCA Reference
Presentation Text Structured Fields in the MO:DCA Architecture
Presentation Text Data Descriptor (PTD)
The PTOCA Presentation Text Data Descriptor is carried in the MO:DCA Presentation Text Data Descriptor
(PTD) structured field.
Structured Field Introducer
SF Length SF Identifier
X'D3B19B'
Flags Reserved
X'0000'
PTD Data
The following table describes the contents of the Presentation Text Data Descriptor (PTD) structured field in
the MO:DCA architecture, which has a structured field identifier of X'D3B19B'.
Offset T ype Name Range Meaning M/O Def Ind
0 CODE XPBASE X'00'–X'01' Unit base for X axis; must
be the same as the unit
base for Y axis:
X'00' T en inches
X'01' T en centimeters
M N N
1 CODE YPBASE X'00' –X'01' Unit base for Y axis; must
be the same as the unit
base for X axis:
X'00' T en inches
X'01' T en centimeters
M N N
2–3 UBIN XPUNITVL X'0001' –
X'7FFF'
X
p
-units per unit base M N N
4–5 UBIN YPUNITVL X'0001' –
X'7FFF'
Y
p
-units per unit base M N N
6–8 UBIN XPEXTENT X'000001' –
X'007FFF'
X
p
-extent. See notes 1
and 2.
M N N
9–1 1 UBIN YPEXTENT X'000001' –
X'007FFF'
Y
p
-extent. See notes 1
and 2.
M N N
12–13 TEXTFLAGS Reserved. See note 3. O Y N
14–end of PTD TXTCONDS Initial text conditions O Y See
note 4.
Notes:
1. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is ten
inches, and the X
p
- and Y
p
-units per unit base are 14,400. If it is necessary to convert to a diffe rent
measurement unit, please see the conversion routine described in “Interpreting Ranges” on page 47.
2. The default indicator is not allowed for this parameter in this subset.
3. The TEXTFLAGS parameter is reserved. Generators should set this parameter to X'0000', and receivers
should ignore it.
4. See the description of the control sequence that specifies the initial text condition.
Presentation Text Structured Fields

<!-- Page 183 -->

PTOCA Reference 165
When the Presentation Text Data Descriptor (PTD) is included in the Active Environment Group (AEG) for a
MO:DCA page, the PTOCA object space and object area coincide with the page presentation space, therefore
the size of the Presentation Text object space is set equal to the size of the page presentation space. When a
Presentation Text object is transformed into an IPDS data stream, the PTD parameters are mapped to IPDS
Logical Page Descriptor (LPD) command parameters. Optionally , some PTD parameters may be transformed
into control sequences as part of an IPDS Wr ite T ext command. When the PTD is specified in the OEG of a
MO:DCA text object, the extents of the text object space are defined by the XPEXTENT and YPEXTENT
parameters and can be set to any value in the allowed range.
Architecture Note: When the PTD is included in the AEG for a MO:DCA page, some AFP print servers
require that the measurement units in the PTD match the measurement units in the Page Descriptor
(PGD). It is therefore strongly recommended that whenever the PTD is included in the AEG, the same
measurement units are specified in both the PTD and PGD.
The coded font information from the MO:DCA Map Coded Font (MCF) and Map Data Resource (MDR)
structured fields is used to determine what the Load Font Equivalence command content should be in the IPDS
environment. See Appendix B, “IPDS Environment”, on page 169 for more information about the IPDS
environment.
Initial text conditions are specified by including the control sequence that contains the parameter to be
initialized. It is recommended that exactly one chain be used to specify initial text conditions. The first control
sequence that specifies an initial text condition parameter starts with the X'2BD3' prefix and class code and
indicates chaining with an odd function type (low-order bit is B'1') if other control sequences follow . All control
sequences that follow , except for the last, are chained control sequences that start with their length byte (not
with X'2BD3') and have an odd function type. The last control sequence in the chain starts with the length byte
but indicates termination of the chain with an even function type (low-order bit is B'0'). For a description of
chaining, see “Control Sequence Chaining” on page 36.
T able 19 on page 165 shows which control sequence may be used to specify a particular initial text condition
parameter . Control sequences are optional and may appear in any order . If a control sequence appears
multiple times, the last occurrence determines the setting of the initial text condition. Control sequences that
are not listed in this table are treated as NOPs.
T able 19. PTOCA Initial T ext Conditions in a MO:DCA Environment
Initial T ext Condition Parameter Control Sequence
Baseline Increment Set Baseline Increment (SBI)
Coded Font Local ID Set Coded Font Local (SCFL)
Extended T ext Color Set Extended T ext Color (SEC)
Initial Baseline Coordinate Absolute Move Baseline (AMB)
Initial Inline Coordinate Absolute Move Inline (AMI)
Inline Margin Set Inline Margin (SIM)
Intercharacter Adjustment Set Intercharacter Adjustment (SIA)
T ext Color Set T ext Color (STC)
T ext Orientation Set T ext Orientation (STO)
Architecture Note: The Extended T ext Color parameter is not supported as an initial text condition in IPDS
environments when text is specified as text-major , that is, when the PTD for the text is specified in the
AEG for the page. This parameter is supported in IPDS environments when the text is specified in a text
object with OEG and the PTD is specified in the OEG.
Presentation Text Data Descriptor (PTD)

<!-- Page 184 -->

166 PTOCA Reference
Architecture Note: The following format 1 version of the Presentation Text Data Descriptor is a coexistence
MO:DCA structured field and may only be used for migration purposes. The PTD format 1 structured
field is not allowed in the OEG of a MO:DCA presentation text object.
Structured Field Introducer
SF Length SF Identifier
X'D3A69B'
Flags Reserved
X'0000'
PTD Data
Offset T ype Name Range Meaning M/O Def Ind
0 CODE XPBASE X'00' Unit base for X axis; ten
inches
M N N
1 CODE YPBASE X'00' Unit base for Y axis; ten
inches
M N N
2–3 UBIN XPUNITVL X'0960' –
X'3840'
X
p
-units per unit base M N N
4–5 UBIN YPUNITVL X'0960' –
X'3840'
Y
p
-units per unit base M N N
6–7 UBIN XPEXTENT X'0001' –
X'7FFF'
X
p
-extent M N N
8–9 UBIN YPEXTENT X'0001' –
X'7FFF'
Y
p
-extent M N N
10–1 1 BITS TEXTFLAGS Reserved. Must be set to
zeros
O Y N
Presentation Text Data (PTX)
Structured Field Introducer
SF Length SF Identifier
X'D3EE9B'
Flags Reserved
X'0000'
PTD Data
The Presentation Text Data (PTX) structured field has a structured field identifier of X'D3EE9B'. It contains the
graphic characters and the control sequences that position the graphic characters.
The contents of the PTX structured field, that is, graphic characters and control sequences, may be included
directly into one or more IPDS Wr ite T ext (WT) commands by removing the MO:DCA structured field introducer
and replacing it with the IPDS WT command syntax. The length information from the PTX structured field must
be changed to reflect the correct length in the WT command.
Presentation text data can span multiple PTX structured fields and can be split on any byte boundary .
Therefore, a control sequence or chain of control sequences, or a sequence of single- or multi-byte code points
can start in one PTX and continue or terminate in a following PTX.
Presentation Text Data (PTX)

<!-- Page 185 -->

PTOCA Reference 167
Presentation Exception Conditions
An implementation of the MO:DCA architecture may detect and report PTOCA exception conditions as
required to perform or assure the function for which it was designed. The MO:DCA architecture expects a
PTOCA processor to handle exception conditions, either by using PTOCA standard actions or by recovering in
some other manner .
Presentation Suppression Handling
Suppression of graphic characters is activated by referencing the local ID from the Begin Suppression and End
Suppression control sequences with a keyword in the MO:DCA Medium Modification Control (MMC) structured
field. The local ID may also be mapped to a global name with a Map Suppression (MSU) structured field. For
further information, please see the Mixed Object Document Content Architecture Reference, AFPC-0004.
Additional Related Structured Fields
Map Coded Font (MCF): Font information for FOCA-based fonts is provided by the Map Coded Font (MCF)
structured field which maps the LID parameter of the Set Coded Font Local control sequence to a FOCA font.
Map Data Resource (MDR): Font information for T rueT ype/OpenT ype fonts is provided by the Map Data
Resource (MDR) structured field which maps the LID parameter of the Set Coded Font Local control sequence
to a T rueT ype/OpenT ype font.
For further information about these structured fields, please refer to Mixed Object Document Content
Architecture Reference, AFPC-0004.
Additional Related Structured Fields

<!-- Page 186 -->

168 PTOCA Reference

<!-- Page 187 -->

Copyright © AFP Consortium 1997, 2025 169
Appendix B. IPDS Environment
The Intelligent Printer Data Stream (IPDS) provides the printer subsystem environment for Presentation Text
objects. This appendix describes:
• The context of Presentation Text objects in the IPDS environment; as either text-major text or as independent
text objects
• A comparison of PTOCA and IPDS exception conditions
• IPDS commands specific to presentation text
For further information about the IPDS Architecture, refer to Intelligent Printer Data Stream Reference, AFPC-
0001.
IPDS Presentation Text
Presentation Text objects are transmitted to print devices acting as receivers as part of an IPDS data stream so
that a presentation on the print device may be made with visual fidelity . The IPDS Architecture is expressly
designed to support all points addressable (AP A) printing function that allows text, image, graphics, and bar
code to be positioned and presented at any point on the printed page.
T ext is described to the IPDS printers in terms of Presentation Text data within Write T ext commands. The
Presentation Text data is comprised of graphic characters and control sequences in the same format as carried
in MO:DCA data streams except that the containing IPDS structure has a diffe rent syntax. The printers that
receive the Presentation Text object are expected to process the object contents accurately according to the
semantic definitions for the supported PTOC A subset.
IPDS Architecture provides the following for the object:
• Command structure and syntax
• Initial conditions for the object
• A means of reporting exception conditions
• A means of resolving fonts and suppression identifiers
IPDS Architecture provides positioning information for the object. The object does not carry the size and shape
of the physical medium or information about object positioning. The object does not control what part of its
object space is to be presented or what part will fit into the logical page specified by the data stream. The
object expresses the exact boundaries that the graphic characters it positions in the object space will require if
they are to be presented without error . The logical page may also be used to provide that boundary .
T ext data can be positioned anywhere on the logical page in two diff erent ways:
1. All IPDS printers allow text to be placed directly within a logical page using the Write T ext command. The
logical page can also contain other presentation objects specified with other IPDS commands either before
or after a Write T ext command. With this method, called “text major”, there is no text object area, and text
may be printed anywhere within the valid printable area. For text-major text, the text presentation space is
the logical page. Furthermore, object areas for other objects may be positioned with respect to the text.
2. Some IPDS printers support text objects (in addition to the text-major concept). In this case, the Write T ext
Control command defines a presentation space, object area, and mapping option. The text data is carried
within one or more Wr ite T ext commands.
Architecture Note: The Extended T ext Color parameter is not supported as an initial text condition in IPDS
environments for text-major text. This parameter is supported in IPDS environments when the text is
specified in a text object.

<!-- Page 188 -->

170 PTOCA Reference
IPDS T ext Command Set
Presentation Text Data Descriptor for T ext-Major T ext
The following table describes the contents of the PTOCA Presentation Text Data Descriptor and the
corresponding parameter location within the IPDS Logical Page Descriptor (LPD) command.
The offset in the table indicates the beginning of the parameter data relative to the beginning of the data
portion of the LPD command. This table reflects the descriptor subset syntax for the Presentation Text Data
Descriptor .
PTOCA Parameter IPDS LPD Offset Notes
UNITBASE 0
XPUNITVL 2-3
YPUNITVL 4-5
XPEXTENT 7-9 5
YPEXTENT 1 1-13 5
TEXTFLAGS 3
Initial T ext Condition Parameters
T ext Orientation:
IORNTION
BORNTION
24-25
26-27
2
2
Initial Inline Coordinate 28-29 5
Initial Baseline Coordinate 30-31 5
Inline Margin 32-33 2
Intercharacter Adjustment:
ADJSTMNT
DIRCTION
34-35 2
4
Baseline Increment 38-39 2
Coded Font Local ID 40 2
T ext Color:
FRGCOLOR
41-42 1,2
Notes:
1. If a receiver cannot provide the color specified by FRGCOLOR, it may substitute values from T able 13 on
page 103 defined in the STC control sequence.
2. The default indicator is allowed, meaning use the printer default.
3. The TEXTFLAGS parameter is reserved. This parameter is not used in the IPDS environment.
4. DIRCTION is always defaulted to X'00', that is, the positive direction, so this parameter is not carried in the
Logical Page Descriptor (LPD) command.
5. The default indicator is not allowed for this parameter in this subset.
IPDS Presentation Text Data Descriptor for T ext Objects
When a text object is used within an IPDS data stream, the PTOCA Presentation Text Data Descriptor is
carried within the Wr ite T ext Control (WTC) command. This portion of the WTC command is called the T ext
IPDS T ext Command Set

<!-- Page 189 -->

PTOCA Reference 171
Data Descriptor (TDD) and specifies parameters that define the text presentation space size and initial text
default conditions. The format of the TDD is as follows:
Offset T ype Name Range Meaning Required
0–1 UBIN Length X'0014' – end
of TDD
Length of TDD, including this field X'0014' – end of
TDD
2–3 CODE SDF ID X'A69B' Self-defining-field ID X'A69B'
4–5 X'0000' Reserved X'0000'
6 CODE Unit base X'00'
X'01'
X'02'
T en inches
T en centimeters
Retired item 54
X'00'
7 X'00' Reserved X'00'
8–9 UBIN XUPUB X'0001' –
X'7FFF'
X
t
units per unit base X'3840'
10–1 1 UBIN YUPUB X'0001' –
X'7FFF'
Y
t
units per unit base X'3840'
12–14 UBIN X
t
extent X'000001' –
X'007FFF'
X
t
extent of the text presentation space in L-
units
X'000001' –
X'007FFF'
15–17 UBIN Y
t
extent X'000001' –
X'007FFF'
Y
t
extent of the text presentation space in L-
units
X'000001' –
X'007FFF'
18–19 BITS T ext flags B'00...00' Reserved for text flags B'00...00'
20 to
end of
TDD
Initial text
conditions
Initial text conditions, in the same syntax as
bytes 14 to end of the MO:DCA PTD found in
the table under “Presentation Text Data
Descriptor (PTD)” on page 164
Presentation Text Data
Presentation Text data, which contains the graphic characters and the control sequences that position the
graphic characters, is carried in the IPDS Write T ext (WT) command. The subsets of PTOC A that are used by
IPDS printers are the PT1, PT2, PT3, and PT4 subsets. Notice that each subset contains all of the lower
number subsets; for example, PT3 contains all of PT2 (and therefore all of PT1).
The contents of the PTX structured field in a MO:DCA architecture data stream, that is, graphic characters and
control sequences, may be included directly into one or more IPDS Wr ite T ext (WT) commands. Remove the
MO:DCA structured field introducer , replace it with the IPDS WT command syntax, and correct the length
information to reflect the length of the WT command.
Presentation Text data can span multiple WT commands. That is, a control sequence or chain of control
sequences can be started in the data sent by one WT command and can be completed in the data sent by the
WT commands that follow .
A WT command may end in the middle of an embedded control sequence or a double-byte code point. In this
event, an exception results if any commands other than Execute Order Anystate, No Operation, Set Home
State, or Sense T ype and Model are received before the next WT command.
Presentation Exception Conditions
The IPDS Architecture defines its own exception condition codes, called exception IDs, which consist of three
bytes. PTOCA exception condition codes are mapped to IPDS exception IDs by mapping the two-byte PTOCA
code to the last two bytes of the IPDS exception code. In most cases, this mapping is one-to-one. Where it is
Presentation Exception Conditions

<!-- Page 190 -->

172 PTOCA Reference
not, the IPDS exception ID overrides the PTOC A exception condition code. The IPDS Architecture also defines
its own exception responses. In some cases, this exception response is the same as the standard exception
action defined by PTOCA. Where it is not, the IPDS exception response overrides the PTOCA standard
exception action. T able 20 shows the mapping of PTOCA exception condition codes to IPDS exception IDs.
T able 20. PTOCA Exception Conditions in an IPDS Environment
PTOCA Exception Condition Code IPDS Exception ID
EC-0001 0200..01
EC-0103 08C1..00 for text major
0201..03 for text objects
EC-0201 0202..01
EC-0401 0204..01
EC-0505 0264..02
EC-0601 0206..01
EC-0605 0260..02
EC-0705 0261..02
EC-0C01 020C..01
EC-0E02 020E..02
EC-0E03 020E..03
EC-0E04 020E..04
EC-0E05 020E..05
EC-0F01 020F ..01
EC-1001 0210..01
EC-1 101 021 1..01
EC-1201 0212..01
EC-1301 0213..01
EC-1401 0214..01
EC-1403 0214..03
EC-1501 0215..01
EC-1601 0216..01
EC-1701 0217..01
EC-1802 0218..02
EC-1901 0219..01
EC-1A01 021A..01
EC-1A03 021A..03
EC-1B01 021B..01
EC-1C01 021C..01
EC-1E01 021E..01
EC-1F01 021F ..01
EC-2100 0821..00
EC-3F02 023F ..02
EC-5803 0258..03
Presentation Exception Conditions

<!-- Page 191 -->

PTOCA Reference 173
T able 20 PTOCA Exception Conditions in an IPDS Environment (cont'd.)
PTOCA Exception Condition Code IPDS Exception ID
EC-6802 0268..02
EC-6902 0269..02
EC-6A02 026A..02
EC-6B02 026B..02
EC-8002 0280..02
EC-8202 0282..02
EC-9801 0298..01
EC-9803 0298..03
EC-9A01 029A..01
EC-9B01 029B..01
EC-9D01 029D..01
EC-9D02 029D..02
EC-9D03 029D..03
Additional Related Commands
The following description is an overview . For further information about these commands, please refer to
Intelligent Printer Data Stream Reference, AFPC-0001.
Sense T ype and Model (STM): Requests information from the printer that identifies the type and model of the
device and the command sets supported. The information requested is returned in the Special Data Area of the
Acknowledge Reply to the STM command. The command sets and data levels supported, such as PT1, are
also returned as part of the acknowledgement data.
XOH Obtain Printer Characteristics (XOH OPC): Requests information from the printer that identifies
characteristics of the device. The characteristics include information about the printable area currently
available, coded font resolution, and color support.
XOA Request Resource List (XOA RRL): Requests the printer to return a specified list of available resources
(coded fonts, overlays, and page segments) in the Acknowledge Reply to this command. This information is
provided for use in resource management functions.
Load Font Equivalence (LFE): This command is sent to the printer to map the LID parameter of the Set
Coded Font Local control sequence to the coded font's Host Assigned ID to select the font and font attributes
to be used by the printer . The coded fonts do not have to exist in the printer when the printer receives this
command.
The mapping function provided by this command is independent of the specific font technology employed by
the printer . For example, the device may resolve the mapping to stored font patterns downloaded from the host
or to permanently resident font patterns.
The coded font resource used for Presentation Text objects may be used by other data objects, such as
graphics, as well.
Load Equivalence (LE) and Load Copy Control (LCC): The LE command permits physical values
embedded in printer data to be referenced externally using different values, and the LCC command specifies
external suppression values to be used in conjunction with the presentation text. These two functions are used
to control the suppression function in PTOCA.
Additional Related Commands

<!-- Page 192 -->

174 PTOCA Reference
Suppression is started by external suppression values specified in the current Load Copy Control (LCC)
Command. The external suppression value works with the LID parameter in two ways:
• The external suppression value maps to the LID parameter of the BSU control sequence. For example, if the
external value is X'0A', the presentation text identified with the LID X'0A' is suppressed.
• The external suppression value maps to the LID through an equivalence table, using the current Load
Equivalence (LE) command. For example, if the external value is X'0B', the LE table may define the following
equivalences: X'0B'=X'01', X'0B'=X'02', X'0B'=X'03'. Thus, the external value X'0B' identifies presentation
text marked with LID values X'01', X'02', and X'03' for suppression.
If no external suppression values are started by the LCC command, the BSU and ESU control sequences
are processed as No Operation control sequences. Exception conditions defined for BSU or ESU control
sequences are detected by the printer whether or not an external suppression value is specified by the LCC
command.
During suppression, the printer performs all composition actions, including field checking, and processes all
control sequences, such as SCFL, RMI, AMI, DIR, and so on.
The printer can use a single suppression LID for more than one Begin and End Suppression pair if the LIDs are
all mapped to the same external value. For example, if LID values of X'06', X'07', and X'09' from BSU, ESU
control sequence pairs are mapped to an external value of X'02' from a Load Copy Control (LCC) command by
an appropriate LE command received by the printer , the presentation text marked by the three LID values will
be suppressed by the single value of X'02'.
BSU, ESU pairs outside an overlay have no effe ct within the overlay , and BSU, ESU pairs within an overlay
have no effect outside the overlay .
Font Commands
The IPDS architecture provides the ability to manage and use a wide variety of font resources. These font
resources can be classified into two major categories: coded font components and data-object-font
components. Coded fonts and their component parts are defined within the Font Object Content Architecture
(FOCA); data-object-font components include the FOCA code page, T rueT ype/OpenT ype fonts, and T rueT ype/
OpenT ype collections. T rueT ype/OpenT ype fonts use an outline font technology as do FOCA outline fonts. The
FOCA and IPDS architectures also include support for raster fonts and a very simple type of font called a
symbol set.
The following IPDS commands are used with T rueT ype/OpenT ype fonts and collections:
• Activate Resource (AR)
• Deactivate Data-Object-Font Component (DDOFC)
• Deactivate Font (DF)
• Load Code Page (LCP)
• Load Code Page Control (LCPC)
• Write Object Container Control (WOCC)
• Write Object Container (WOC)
• XOH Erase Residual Font Data (ERFD)
• XOH Erase Residual Print Data (ERPD)
The following IPDS commands are used with FOCA outline fonts:
• Activate Resource (AR)
• Deactivate Font (DF)
• Load Code Page (LCP)
• Load Code Page Control (LCPC)
• Load Font (LF)
• Load Font Character Set Control (LFCSC)
Font Commands

<!-- Page 193 -->

PTOCA Reference 175
• Load Font Equivalence (LFE)
• XOH Erase Residual Font Data (ERFD)
The following IPDS commands are used with FOCA raster fonts:
• Activate Resource (AR)
• Deactivate Font (DF)
• Load Font (LF)
• Load Font Control (LFC)
• Load Font Equivalence (LFE)
• Load Font Index (LFI)
• XOH Erase Residual Font Data (ERFD)
The following IPDS commands are used with symbol set fonts:
• Activate Resource (AR)
• Deactivate Font (DF)
• Load Font Equivalence (LFE)
• Load Symbol Set (LSS)
Font Commands

<!-- Page 194 -->

176 PTOCA Reference

<!-- Page 195 -->

Copyright © AFP Consortium 1997, 2025 177
Appendix C. PTOCA Retired Functions
Introduction
This appendix:
• Describes retired functions that may occur in a PTOCA object
General
The objective in defining retired functions is twofold: (1) to allow existing applications to run unchanged, and (2)
to provide a clear growth direction for future applications.
Retired Functions
Retired functions are control sequences and parameters whose use has been retired except for specific
products. Only these specific products may use these functions. All other products should not use these
functions, that is, generators should not generate these functions and receivers may ignore them.
Retired Parameters
STC Precision Parameter (Byte 6, Name PRECSION)
The use of this parameter is restricted to pre-year 2000 AFP applications and printers.
The PRECSION parameter is optional on the STC (byte 6), and specifies how the receiver should process
colors that are syntactically valid but not supported by the receiver . If PRECSION is X'00', the receiver must
support the color selected by FRGCOLOR as specified. If the color is not supported, exception condition EC-
5803 exists. The standard action in this case is to use X'FF07'. If PRECSION is X'01', and if the FRGCOLOR
value is not supported by the receiver , a default action is allowed. The receiver may use a substitute color or
X'FF07'. If the FRGCOLOR value is not syntactically valid, exception condition EC-5803 exists, regardless of
the value for the PRECSION parameter . The PRECSION parameter is modal, and X'00' is the default. This
parameter supports the default indicator (X'FF'), which means its value is provided by the hierarchy , as follows:
1. V alue set by T ext Color initial text condition parameter in Descriptor
2. PTOCA default - X'00'
If the value of the PRECSION parameter is not valid, EC-5803 exists. The standard action is to ignore the
parameter and continue presentation with the value determined according to the hierarchy .
The PRECSION parameter also defines the hierarchy for determining the presentation process default color
attribute value (FRGCOLOR = X'FFFF'). If PRECSION is X'00', the following order applies:
1. V alue previously set by T ext Color initial text condition parameter in descriptor
2. Data stream specified value
3. Receiver's best possible value

<!-- Page 196 -->

178 PTOCA Reference
If PRECSION is X'01', the following order applies:
1. Receiver's best possible value
2. V alue previously set by T ext Color initial text condition parameter in descriptor
3. Data stream specified value
Note that when a Color Mapping T able (CMT) is specified in the MO:DCA environment, the PRECSION
parameter is processed for the target color values, not for the original (source) color values.
Retired Functions

<!-- Page 197 -->

Copyright © AFP Consortium 1997, 2025 179
Notices
The AFP Consortium or consortium member companies might have patents or pending patent applications
covering subject matter described in this document. The furnishing of this document does not give you any
license to these patents.
The following statement does not apply to the United Kingdom or any other country where such
provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICA TION “AS IS”
WITHOUT W ARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
TO , THE IMPLIED W ARRANTIES OF NON-INFRINGEMENT , MERCHANT ABILITY OR FITNESS FOR A
P ARTICULAR PURPOSE. Some states do not allow disclaimer of express or implied warranties in certain
transactions, therefore, this statement might not apply to you.
This publication could include technical inaccuracies or typographical errors. Changes are periodically made to
the information herein; these changes will be incorporated in new editions of the publication. The AFP
Consortium might make improvements and/or changes in the architecture described in this publication at any
time without notice.
Any references in this publication to We b sites are provided for convenience only and do not in any manner
serve as an endorsement of those We b sites. The materials at those Web sites are not part of the materials for
this architecture and use of those Web sites is at your own risk.
The AFP Consortium may use or distribute any information you supply in any way it believes appropriate
without incurring any obligation to you.
This information contains examples of data and reports used in daily business operations. T o illustrate them in
a complete manner , some examples include the names of individuals, companies, brands, or products. These
names are fictitious and any similarity to the names and addresses used by an actual business enterprise is
entirely coincidental.

<!-- Page 198 -->

180 PTOCA Reference
T rademarks
These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries,
or both:
ACMA
Advanced Function Presentation
AFP
AFPCC
AFP Color Consortium
AFP Color Management Architecture
Bar Code Object Content Architecture
BCOCA
CMOCA
Color Management Object Content Architecture
InfoPrint
Intelligent Printer Data Stream
IPDS
Mixed Object Document Content Architecture
MO:DCA
Ricoh
The following terms are trademarks of other companies:
Apple and the Apple logo are either registered trademarks or trademarks of Apple Incorporated in the United
States and/or other countries.
AFPC and AFP Consortium are trademarks of the AFP Consortium.
International Business Machines Corporation in the United States, other countries, or both: IBM
Microsoft, Windows, Windows NT , and the Windows logo are registered trademarks of Microsoft Corporation in
the United States, other countries, or both.
Other company , product, or service names might be trademarks or service marks of others.

<!-- Page 199 -->

Copyright © AFP Consortium 1997, 2025 181
Glossary
This glossary contains terms that apply to the
Advanced Function Presentation (AFP) Architecture
and also terms that apply to other related
presentation architectures.
Note: Only changes having to do with newly-added
PTOCA functionality in this edition are marked
in color with a colored revision bar to the left.
All other changes—terms or definitions that
have been added, deleted, or reworded—are
not marked.
If you do not find the term that you are looking for ,
please refer to the IBM Dictionary of Computing,
document number ZC20-1699 or the InfoPrint
Dictionary of Printing.
The following definitions are provided as supporting
information only , and are not intended to be used as
a substitute for the semantics described in the body
of this reference.
A
absolute coordinate. One of the coordinates that identify
the location of an addressable point with respect to the
origin of a specified coordinate system. Contrast with
relative coordinate.
absolute move. A method used to designate a new
presentation position by specifying the distance from the
designated axes to the new presentation position. The
reference for locating the new presentation position is a
fixed position as opposed to the current presentation
position.
absolute positioning. The establishment of a position
within a coordinate system as an offset from the coordinate
system origin. Contrast with relative positioning.
abstract profile. An ICC profile that represents abstract
transforms and does not represent any device model.
Color transformations using abstract profiles are performed
from PCS to PCS. Abstract profiles cannot be embedded in
images.
Abstract Syntax Notation One (ASN.1). A notation for
defining data structures and data types. The notation is
defined in international standard ISO/IEC 8824(E). See
also object identifier .
ACK. See Positive Acknowledge Reply .
Acknowledge Reply . A printer-to-host reply that returns
printer information or reports exceptions. An Acknowledge
Reply can be positive or negative. See also Positive
Acknowledge Reply and Negative Acknowledge Reply .
Acknowledgment Request. A request from the host for
information from the printer . An example of an
Acknowledgment Request is the use of the
acknowledgment-required flag by a host system to request
an Acknowledge Reply from an attached printer .
acknowledgment-required flag (ARQ). A flag that
requests a printer to return an Acknowledge Reply . The
acknowledgment-required flag is bit zero of an IPDS
command's flag byte.
active coded font. The coded font that is currently being
used by a product to process text.
additive primary colors. Red, green, and blue light,
transmitted in video monitors and televisions. When used
in various degrees of intensity and variation, they create all
other colors of light; when superimposed equally , they
create white. Contrast with subtractive primary colors.
addressable position. A position in a presentation space
or on a physical medium that can be identified by a
coordinate from the coordinate system of the presentation
space or physical medium. See also picture element.
Synonymous with position.
Advanced Function Presentation (AFP). An open
architecture for the management of presentable
information that is developed by the AFP Consortium
(AFPC). AFP comprises a number of data stream and data
object architectures:
• Mixed Object Document Content Architecture (MO:DCA);
formerly referred to as AFPDS
• Intelligent Printer Data Stream (IPDS)
• AFP Line Data Architecture
• Bar Code Object Content Architecture (BCOCA)
• Color Management Object Content Architecture
(CMOCA)
• Font Object Content Architecture (FOCA)
• Graphics Object Content Architecture for AFP (AFP
GOCA)
• Image Object Content Architecture (IOCA)
• Metadata Object Content Architecture (MOCA)
• Presentation Text Object Content Architecture (PTOCA)
AEA. See alternate exception action.
AFM file. A file containing the metric information required
for positioning the characters of a font. The metric
information contained in this file was extracted from a PFB
file, in an ASCII file format defined by Adobe
®
Systems
Inc., and used for character positioning and page
formatting.

<!-- Page 200 -->

182 PTOCA Reference
AFP . See Advanced Function Presentation.
AFP archive. See AFP/A.
AFP Consortium (AFPC). A formal open standards body
that develops and maintains AFP architecture. Information
about the consortium can be found at
www.afpconsortium.org.
AFP data stream. A presentation data stream that is
processed in AFP environments. The MO:DCA
architecture defines the strategic AFP interchange data
stream. The IPDS architecture defines the strategic AFP
printer data stream.
AFPDS. A term formerly used to identify the composed-
page MO:DCA-based data stream interchanged in AFP
environments. See also MO:DCA and AFP data stream.
AFP environment. Wherever the AFP architecture is
used in any way; by an AFP vendor , an AFP customer , or
any combination thereof.
AFP GOCA. A subset of the GOCA architecture, originally
defined by IBM, specifically designed for AFP
environments. See Graphics Object Content Architecture
(GOCA).
AFP Line Data Architecture. An AFP architecture that
controls formatting of line data using a Page Definition
(PageDef).
AFP T agging. (1) Associating extra information,
contained in a metadata object, with a given piece of AFP
data. Among other uses, such information could enable
users with vision impairments or other restrictions to make
full use of the content provided by an AFP system. (2) In
MOCA, a known format of a metadata object.
AFP/A. A constrained version of the general MO:DCA
architecture aimed at interoperability for AFP documents in
an archiving system. Refer to the ISO 18565:2015
“Document management – AFP/Archive” standard for a
complete definition of AFP/A.
AIAG. See Automotive Industry Action Group.
AIM. See Automatic Identification Manufacturers, Inc.
all points addressable (AP A). The capability to address,
reference, and position data elements at any addressable
position in a presentation space or on a physical medium.
Contrast with character cell addressable, in which the
presentation space is divided into a fixed number of
character-size rectangles in which characters can appear .
Only the cells are addressable. An example of all points
addressable is the positioning of text, graphics, and
images at any addressable point on the physical medium.
See also picture element.
alternate exception action (AEA). In the IPDS
architecture, a defined action that a printer can take when
a clearly defined, but unsupported, request is received.
Control over alternate exception actions is specified by an
Execute Order Anystate Exception-Handling Control
command.
American National Standards Institute (ANSI). An
organization consisting of producers, consumers, and
general interest groups. ANSI establishes the procedures
by which accredited organizations create and maintain
voluntary industry standards in the United States. It is the
United States constituent body of the International
Organization for Standardization (ISO).
anamorphic scaling. Scaling an object differently in the
vertical and horizontal directions. See also scaling,
horizontal font size, and vertical font size.
annotation. (1) A process by which additional data or
attributes, such as highlighting, are associated with a page
or a position on a page. Application of this data or
attributes to the page is typically under the control of the
user . Common functions such as applying adhesive
removable notes to paper documents or using a
transparent highlighter are emulated electronically by the
annotation process. (2) A comment or explanation
associated with the contents of a document component. An
example of an annotation is a string of text that represents
a comment on an image object on a page.
annotation link. In MO:DCA, a link type that specifies the
linkage from a source document component to a target
document component that contains an annotation.
annotation object. In MO:DCA, an object that contains
an annotation. Objects that are targets of annotation links
are annotation objects.
ANSI. See American National Standards Institute.
AP A. See all points addressable.
append. In MO:DCA, an addition to or continuation of the
contents of a document component. An example of an
append is a string of text that is an addition to an existing
string of text on a page.
append link. In MO:DCA, a link type that specifies the
linkage from the end of a source document component to a
target document component that contains an append.
append object. In MO:DCA, an object that contains an
append. Objects that are targets of append links are
append objects.
application. (1) The use to which an information system
is put. (2) A collection of software components used to
perform specific types of work on a computer .
application program. A program written for or by a user
that applies to the user's work.
AFP • application program

<!-- Page 201 -->

PTOCA Reference 183
arc. A continuous portion of the curved line of a circle or
ellipse. See also full arc.
architected. Identifies data that is defined and controlled
by an architecture. Contrast with unarchitected.
archive interchange set. A constrained version of the
general MO:DCA architecture aimed at interoperability for
AFP documents in an archiving system. For archive
systems, the key requirement is to make each page stand
alone by eliminating the use of resolution-dependent fonts
and images, device-default fonts, and external resources.
See AFP/A.
arc parameters. V ariables that specify the curvature of
an arc.
area. In GOCA, a set of closed figures that can be filled
with a pattern or a color .
area filling. A method used to fill an area with a pattern or
a color .
ARQ. See acknowledgment-required flag.
array . A structure that contains an ordered group of data
elements. All elements in an array have the same data
type.
article. The physical item that a bar code identifies.
ascender . The parts of certain lowercase letters, such as
b, d, or f, that at zero-degree character rotation rise above
the top edge of other lowercase letters such as a, c, and e.
Contrast with descender .
ascender height. The character shape's most positive
character coordinate system Y -axis value.
ASCII. Acronym for American Standard Code for
Information Interchange. A standard code used for
information exchange among data processing systems,
data communication systems, and associated equipment.
ASCII uses a coded character set consisting of 7-bit coded
characters.
ASN.1. See Abstract Syntax Notation One.
A space. The distance from the character reference point
to the least positive character coordinate system X-axis
value of the character shape. A-space can be positive,
zero, or negative. See also B space and C space.
aspect ratio. (1) The ratio of the horizontal size of a
picture to the vertical size of the picture. (2) In a bar code
symbol, the ratio of bar height to symbol length.
asynchronous exception. Any exception other than
those used to report a synchronous data-stream defect
(action code X'01' or X'1F'), function no longer achievable
(action code X'06'), or synchronous resource-storage
problem (action code X'0C'). Asynchronous exceptions
occur after the received page station. An example of an
asynchronous exception is a paper jam. See also data-
stream exception. Contrast with synchronous exception.
attribute. A property or characteristic of one or more
constructs. See also character attribute, color attribute,
current drawing attributes, default drawing attributes, line
attributes, marker attributes, and pattern attributes.
audit CMR. A color management resource that reflects
processing that has been done on an object.
Automatic Identification Manufacturers, Inc. (AIM). A
trade organization consisting of manufacturers, suppliers,
and users of bar codes.
Automotive Industry Action Group (AIAG). The
coalition of automobile manufacturers and suppliers
working to standardize electronic communications within
the auto industry .
B
+B. Positive baseline direction.
B. See baseline direction.
background. (1) The part of a presentation space that is
not occupied with object data. Contrast with foreground.
(2) In GOCA, that portion of a graphics primitive that is
mixed into the presentation space under the control of the
current values of the background mix and background
color attributes. (3) In GOCA, that portion of a character
cell that does not represent a character . (4) In bar codes,
the spaces, quiet zones, and area surrounding a printed
bar code symbol.
background color . The color of a background. Contrast
with foreground color .
background mix. (1) An attribute that determines how
the color of the background of a graphics primitive is
combined with the existing color of the graphics
presentation space. (2) An attribute that determines how
the points in overlapping presentation space backgrounds
are combined. Contrast with foreground mix.
band. An arbitrary layer of an image. An image can
consist of one or more bands of data.
bar . In bar codes, the darker element of a printed bar
code symbol. See also element. Contrast with space.
bar code. An array of elements, such as bars, spaces,
and two-dimensional modules that together represent data
elements or characters in a particular symbology . The
elements are arranged in a predetermined pattern
following unambiguous rules defined by the symbology .
See also bar code symbol.
arc • bar code

<!-- Page 202 -->

184 PTOCA Reference
Bar Code command set. In the IPDS architecture, a
collection of commands used to present bar code symbols
in a page, page segment, or overlay .
bar code density . The number of characters per inch
(cpi) in a bar code symbology . In most cases, the range is
three to ten cpi. See also character density , density , and
information density .
bar code object area. The rectangular area on a logical
page into which a bar code presentation space is mapped.
Bar Code Object Content Architecture (BCOCA). An
architected collection of constructs used to interchange
and present bar code data.
bar code presentation space. A two-dimensional
conceptual space in which bar code symbols are
generated.
bar code symbol. A combination of characters including
start and stop characters, quiet zones, data characters,
and check characters required by a particular symbology ,
that form a complete, scannable entity . See also bar code.
bar code symbology . A bar code language. Bar code
symbologies are defined and controlled by various industry
groups and standards organizations. Bar code
symbologies are described in public domain bar code
specification documents. Synonymous with symbology .
See also Canadian Grocery Product Code (CGPC),
European Article Numbering (EAN), Japanese Article
Numbering (JAN), and Universal Product Code (UPC).
bar height. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar length and height.
bar length. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar height and height.
bar width. In bar codes, the thickness of a bar measured
from the edge closest to the symbol start character to the
trailing edge of the same bar .
bar width reduction. In bar codes, the reduction of the
nominal bar width dimension on film masters or printing
plates to compensate for systematic errors in some printing
processes.
base-and-towers concept. A conceptual illustration of an
architecture that shows the architecture as a base with
optional towers. The base and the towers represent
different degrees of function achieved by the architecture.
baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with printing baseline and
sequential baseline.
baseline coordinate. One of a pair of values that identify
the position of an addressable position with respect to the
origin of a specified I,Bcoordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,Bcoordinate system. Synonymous with B
coordinate.
baseline direction (B). The direction in which successive
lines of text appear on a logical page. Synonymous with
baseline progression and B direction.
baseline extent. A rectangular space oriented around the
character baseline and having one dimension parallel to
the character baseline. The space is measured along the Y
axis of the character coordinate system. For bounded
character boxes, the baseline extent at any rotation is its
character coordinate system Y -axis extent. Baseline extent
varies with character rotation. See also maximum baseline
extent.
baseline increment. The distance between successive
baselines.
baseline offset. The perpendicular distance from the
character baseline to the character box edge that is parallel
to the baseline and has the more positive character
coordinate system Y -axis value. For characters entirely
within the negative Y -axis region, the baseline offset can be
zero or negative. An example is a subscript character .
Baseline offset can vary with character rotation.
baseline presentation origin (B
o
). The point on the B
axis where the value of the baseline coordinate is zero.
baseline progression (B). The direction in which
successive lines of text appear on a logical page.
Synonymous with baseline direction and B direction.
base LND. The first Line Descriptor (LND) used to
process an input line-data record. See also reuse LND.
base support level. Within the base-and-towers concept,
the smallest portion of architected function that is allowed
to be implemented. This is represented by a base with no
towers. Synonymous with mandatory support level.
B axis. The axis of the I,Bcoordinate system that extends
in the baseline or B direction. The B axis does not have to
be parallel to the Y
p
axis of its bounding X
p
,Y
p
coordinate
space.
B
c
. See current baseline presentation coordinate.
b
c
. See current baseline print coordinate.
BCOCA. See Bar Code Object Content Architecture.
Bcoordinate. One of a pair of values that identify the
position of an addressable position with respect to the
origin of a specified I,Bcoordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,Bcoordinate system. Synonymous with
baseline coordinate.
Bar Code command set • Bcoordinate

<!-- Page 203 -->

PTOCA Reference 185
B direction (B). The direction in which successive lines of
text appear on a logical page. Synonymous with baseline
direction and baseline progression.
Bearer Bars. Bars that surround an Interleaved 2-of-5 bar
code to prevent misreads and short scans that might occur
when a skewed scanning beam enters or exits the bar
code symbol through its top or bottom edge. When plates
are used in the printing process, Bearer Bars help equalize
the pressure exerted by the printing plate over the entire
surface of the symbol to improve print quality . There are
two styles: 1) four bars that completely surround the
bar/space pattern and 2) two bars that are placed at the top
and the bottom of the bar/space pattern.
Begin Segment Introducer (BSI). An IPDS graphics
self-defining field that precedes all of the drawing orders in
a graphics segment.
between-the-pels. The concept of pel positioning that
establishes the location of a pel's reference point at the
edge of the pel nearest to the preceding pel rather than
through the center of the pel.
B extent. The extent in the B-axis direction of an I,B
coordinate system. The B extent must be parallel to one of
the axes of the coordinate system that contains the I,B
coordinate system. The B extent is parallel to the Y
p
extent
when the B axis is parallel to the Y
p
axis or to the X
p
extent
when the B axis is parallel to the X
p
axis.
b
i
. See initial baseline print coordinate.
big endian. A format for storage or transmission of binary
data in which the most significant bit (or byte) is placed
first. Contrast with little endian.
bilevel. Having two levels; for example, every point in a
bilevel image has the value 1 or 0, representing a colored
image point or empty space. Contrast with multilevel.
bilevel custom pattern. In GOCA, a custom pattern that
is uncolored at definition time, then has a single color
assigned to it when it is used to fill an area. Contrast with
full-color custom pattern.
bilevel device. A device that is used in a manner that
permits it to process two-level color data. Contrast with
multilevel device.
BITS. A data type for architecture syntax, indicating one
or more bytes to be interpreted as bit string information.
blend. A mixing rule in which the intersection of part of a
new presentation space P
new
with part of an existing
presentation space P
existing
changes to a new color attribute
that represents a color-mixing of the color attributes of P
new
with the color attributes of P
existing
. For example, if P
new
has
foreground color-attribute blue and P
existing
has foreground
color-attribute yellow , the area where the two foregrounds
intersect changes to a color attribute of green. See also
mixing rule. Contrast with overpaint and underpaint.
B
o
. See baseline presentation origin.
body . (1) On a printed page, the area between the top
and bottom margins that can contain data. (2) In a book,
the portion between the front matter and the back matter .
boldface. A heavy-faced type weight. Printing in a heavy-
faced type weight.
boundary alignment. A method used to align image data
elements by adding padding bits to each image data
element.
bounded character box. A conceptual rectangular box,
with two sides parallel to the character baseline, that
circumscribes a character and is just large enough to
contain the character , that is, just touching the shape on all
four sides.
brightness. Attribute of a visual sensation according to
which an area appears to exhibit more or less light.
BSI. See Begin Segment Introducer .
B space. The distance between the character coordinate
system X-axis values of the two extremities of a character
shape. See also A space and C space.
buffered pages. Pages and copies of pages that have
been received but not yet reflected in committed page
counters and copy counters.
BYTE. A data type for architecture syntax consisting of 8
bits and indicating that each byte has no predefined
interpretation. Therefore, in CMOCA, each byte is
interpreted as defined in the tag explanation.
C
calibration. T o adjust the correct value of a reading by
comparison to a standard.
Canadian Grocery Product Code (CGPC). The bar
code symbology used to code grocery items in Canada.
cap-M height. The average height of the uppercase
characters in a font. This value is specified by the designer
of a font and is usually the height of the uppercase M.
Cartesian coordinate system. In a plane, an image
coordinate system that has positive values for the X and Y
axis in the top-right quadrant. The origin is the upper left-
hand corner of the bottom-right quadrant. A pair of (x,y)
values corresponds to one image point. Each image point
is described by an image data element.
CCSID. See Coded Character Set Identifier .
CGCSGID. See Coded Graphic Character Set Global
Identifier .
B direction (B) • CGCSGID

<!-- Page 204 -->

186 PTOCA Reference
CGPC. See Canadian Grocery Product Code.
CHAR. A data type for architecture syntax, indicating one
or more bytes to be interpreted as character information.
character . (1) A member of a set of elements used for the
organization, control, or representation of data. A character
can be either a graphic character or a control character .
See also graphic character and control character . (2) In
bar codes, a single group of bar code elements that
represent an individual number , letter , punctuation mark, or
other symbol.
character angle. The angle that is between the baseline
of a character string and the horizontal axis of a
presentation space or physical medium.
character attribute. A characteristic that controls the
appearance of a character or character string.
character baseline. A conceptual reference line that is
coincident with the X axis of the character coordinate
system.
character box. A conceptual rectangular box with two
sides parallel to the character baseline. A character's
shape is formed within a character box by a presentation
process, and the character box is then positioned in a
presentation space or on a physical medium. The
character box can be rotated before it is positioned.
character-box reference edges. The four edges of a
character box.
character cell addressable. Allowing characters to be
addressed, referenced, and positioned only in a fixed
number of character-size rectangles into which a
presentation space is divided. Contrast with all points
addressable.
character cell size. The size of a rectangle in a drawing
space used to scale font symbols into the drawing space.
character code. An element of a code page or a cell in a
code table to which a character can be assigned. The
element is associated with a binary value. The assignment
of a character to an element of a code page determines the
binary value that will be used to represent each occurrence
of the character in a character string.
character coordinate system. An orthogonal coordinate
system that defines font and character measurement
distances. The origin is the character reference point. The
X axis coincides with the character baseline.
character density . The number of characters per inch
(cpi) in a bar code symbology . In most cases, the range is
three to ten cpi. See also bar code density , density , and
information density .
character direction. In GOCA, an attribute controlling the
direction in which a character string grows relative to the
inline direction. V alues are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with direction.
character escapement point. The point where the next
character reference point is usually positioned. See also
character increment and presentation position.
character identifier . The unique name for a graphic
character .
character increment. The distance from a character
reference point to a character escapement point. For each
character , the increment is the sum of a character's A
space, B space, and C space. A character's character
increment is the distance the inline coordinate is
incremented when that character is placed in a
presentation space or on a physical medium. Character
increment is a property of each graphic character in a font
and of the font's character rotation.
character increment adjustment. In a scaled font, an
adjustment to character increment values. The adjustment
value is derived from the kerning track values for the font
used to present the characters.
character metrics. Measurement information that
defines individual character values such as height, width,
and space. Character metrics can be expressed in specific
fixed units, such as pels, or in relative units that are
independent of both the resolution and the size of the font.
Often included as part of the more general term font
metrics. See also character set metrics and font metrics.
character origin. The point within the graphic pattern of a
character that is to be aligned with the presentation
position. See also character reference point.
character pattern. The scan pattern for a graphic
character of a particular size, style, and weight.
character-pattern descriptor . Information that the printer
needs to separate font raster patterns. Each character
pattern descriptor is eight bytes long and specifies both the
character box size and an offset value; the offset value
permits the printer to find the beginning of the character
raster pattern within the character raster pattern data for
the complete coded font.
character positioning. A method used to determine
where a character is to appear in a presentation space or
on a physical medium.
character precision. The acceptable amount of variation
in the appearance of a character on a physical medium
from a specified ideal appearance, including no acceptable
variation. Examples of appearance characteristics that can
vary for a character are character shape and character
position.
character reference point. The origin of a character
coordinate system. The X axis is the character baseline.
See also character origin.
CGPC • character reference point

<!-- Page 205 -->

PTOCA Reference 187
character rotation. The alignment of a character with
respect to its character baseline, measured in degrees in a
clockwise direction. Examples are 0°, 90°, 180°, and 270°.
Zero-degree character rotation exists when a character is
in its customary alignment with the baseline. Character
rotation and font inline sequence are related in that
character rotation is a clockwise rotation; font inline
sequence is a counter-clockwise rotation. Contrast with
rotation.
character set. A finite set of different graphic characters
or control characters that is complete for a given purpose.
For example, the character set in ISO Standard 646, 7-Bit
Coded Character Set for Information Processing
Interchange.
character set attribute. An attribute used to specify a
coded font.
character set metrics. The measurements used in a
font. Examples are height, width, and character increment
for each character of the font. See also character metrics
and font metrics.
character shape. The visual representation of a graphic
character .
character shape presentation. A method used to form a
character shape on a physical medium at an addressable
position.
character shear . The angle of slant of a character cell
that is not perpendicular to a baseline. Synonymous with
shear .
character string. A sequence of characters.
check character . In bar codes, a character included
within a bar code message whose value is used to perform
a mathematical check to ensure the accuracy of that
message. Synonymous with check digit.
check digit. In bar codes, a character included within a
bar code message whose value is used to perform a
mathematical check to ensure the accuracy of that
message. Synonymous with check character .
CID file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for large character set fonts (for example, Japanese
ideographic fonts having several thousand characters).
CIE. See Commission Internationale d'Éclairage.
CIELABcolor space. Internationally accepted color
space model used as a standard to define color within the
graphic arts industry , as well as other industries. L*, a*, and
b* are plotted at right angles to one another . Equal
distances in the space represent approximately equal color
dif ference.
CIEXYZ color space. The fundamental CIE-based color
space that allows colors to be expressed as a mixture of
the three tristimulus values X, Y , and Z.
CJK fonts. Fonts that contain a set of unified ideographic
characters used in the written Chinese, Japanese, and
Korean languages. The character encoding is the same for
each language, but there might be glyph variants between
languages.
clear area. A clear space that contains no machine-
readable marks preceding the start character of a bar code
symbol or following the stop character . Synonymous with
quiet zone. Contrast with intercharacter gap and space.
clipping. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window .
Synonymous with trimming.
cluster-dot screening. A halftone method that uses
multiple pixels that vary from small to large dots as the
color gets darker . It is characterized by a polka-dot look.
CMAP file. A file containing the mapping of code points to
the character index values used in a CID file. The code
points conform to a particular character coding system that
is used to identify the characters in a document data
stream. The character index values are assigned in a CID
file for identification of the glyph procedure used to define
the character shape. The mapping information in this file is
in an ASCII file format defined by Adobe Systems Inc.
CMOCA. See Color Management Object Content
Architecture.
CMR. See color management resource.
CMY . Cyan, magenta, and yellow , the subtractive primary
colors.
CMYK color space. (1) The color model used in four-
color printing. Cyan, magenta, and yellow , the subtractive
primary colors, are used with black to effectively create a
multitude of other colors. (2) The primary colors used
together in printing to effectiv ely create a multitude of other
colors: cyan, magenta, yellow , and black. Based on the
subtractive color theory; the primary colors used in four-
color printing processes.
Codabar . A bar code symbology characterized by a
discrete, self-checking, numeric code with each character
represented by a standalone group of four bars and the
three spaces between them.
CODE. A data type for architecture syntax that indicates
an architected constant to be interpreted as defined by the
architecture.
Code 39. A bar code symbology characterized by a
variable-length, bidirectional, discrete, self-checking,
alphanumeric code. Three of the nine elements are wide
character rotation • Code 39

<!-- Page 206 -->

188 PTOCA Reference
and six are narrow . It is the standard for LOGMARS (the
Department of Defense) and the AIAG.
Code 128. A bar code symbology characterized by a
variable-length, alphanumeric code with 128 characters.
Coded Character Set Identifier (CCSID). A 16-bit
number identifying a specific set consisting of an encoding
scheme identifier , character set identifiers, code page
identifiers, and other relevant information that uniquely
identifies the coded graphic character representation used.
coded font. (1) A resource containing elements of a code
page and a font character set, used for presenting text,
graphics character strings, and bar code HRI. See also
code page and font character set. (2) In FOCA, a resource
containing the resource names of a valid pair of font
character set and code page resources. The graphic
character set of the font character set must match the
graphic character set of the code page for the coded font
resource pair to be valid. (3) In the IPDS architecture, a
raster font resource containing code points that are directly
paired to font metrics and the raster representation of
character shapes, for a specific graphic character set. (4)
In the IPDS architecture, a font resource containing
descriptive information, a code page, font metrics, and a
digital-technology representation of character shapes for a
specific graphic character set.
coded font local identifier . A binary identifier that is
mapped by the controlling environment to a named
resource to identify a coded font. See also local identifier .
coded graphic character . A graphic character that has
been assigned one or more code points within a code
page.
coded graphic character set. A set of graphic
characters with their assigned code points.
Coded Graphic Character Set Global Identifier
(CGCSGID). A four-byte binary or a ten-digit decimal
identifier consisting of the concatenation of a GCSGID and
a CPGID. The CGCSGID identifies the code point
assignments in the code page for a specific graphic
character set, from among all the graphic characters that
are assigned in the code page.
code page. (1) A resource object containing descriptive
information, graphic character identifiers, and code points
corresponding to a coded graphic character set. Graphic
characters can be added over time; therefore, to
specifically identify a code page, both a GCSGID and a
CPGID should be used. See also coded graphic character
set. (2) A set of assignments, each of which assigns a
code point to a character . Each code page has a unique
name or identifier . Within a given code page, a code point
is assigned to one character . More than one character set
can be assigned code points from the same code page.
See also code point and section.
Code Page Global Identifier (CPGID). A unique code
page identifier that can be expressed as either a two-byte
binary or a five-digit decimal value.
code point. A unique bit pattern that can serve as an
element of a code page or a site in a code table, to which a
character can be assigned. The element is associated with
a binary value. The assignment of a character to an
element of a code page determines the binary value that
will be used to represent each occurrence of the character
in a character string. Code points are one or more bytes
long. See also code table and section.
code table. A table showing the character allocated to
each code point in a code. See also code page and code
point.
color . A visual attribute of things that results from the light
they emit, transmit, or reflect.
colorants. Colors (pigments, dyes, inks) used by a
device, primarily a printer , to reproduce colors.
color attribute. An attribute that affects the color values
provided in a graphics primitive, a text control sequence, or
an IPDS command. Examples of color attributes are
foreground color and background color .
color calibration. The process of altering the behavior of
an input or output device to make it conform to an
established state, specified by a manufacturer , user ,
industry specification, or standard.
color component. A dimension of a color value
expressed as a numeric value. For example, a color value
might consist of one, two, three, four , or eight components,
also referred to as channels.
color conversion. The process of converting colors from
one color space to another .
color image. Images whose image data elements are
represented by multiple bits or whose image data element
values are mapped to color values. Constructs that map
image-data-element values to color values are look-up
tables and image-data-element structure parameters.
Examples of color values are screen color values for
displays and color toner values for printers.
colorimetric intent. A gamut mapping method that is
intended to preserve the relationships between in-gamut
colors at the expense of out-of-gamut colors.
colorimetry . The science of measuring color and color
appearance. Classical colorimetry deals primarily with
color matches rather than with color appearance as such.
The main focus of colorimetry has been the development
of methods for predicting perceptual matches on the basis
of physical measurements.
color management. The technology to calibrate the color
of input devices (such as scanners or digital cameras),
Code 128 • color management

<!-- Page 207 -->

PTOCA Reference 189
display devices, and output devices (such as printers or
offset presses).
Color Management Object Content Architecture
(CMOCA). An architected collection of constructs used for
the interchange and presentation of the color management
information required to render a print file, document, group
of pages or sheets, page, overlay , or data object with color
fidelity .
color management resource. An object that provides
color management in presentation environments.
color management system. A set of software designed
to increase the accuracy and consistency of color between
color devices like a scanner , display , and printer .
color model. The method by which a color is specified.
For example, the RGBcolor space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color space.
color of medium. The color of a presentation space
before any data is added to it. Synonymous with reset
color .
color palette. A system of designated colors that are
used in conjunction with each other to achieve visual
consistency .
Color Rendering Dictionary . A PostScript language
construct for converting colors from the CIEXYZ color
space to the device color space. It is analogous to the
“from PCS” part of an ICC printer profile with one rendering
intent; that is, the part used when the profile is a
destination profile.
color space. The method by which a color is specified.
For example, the RGBcolor space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color model.
ColorSpace conversion profile. An ICC profile that
provides the relevant information to perform a color space
transformation between the non-device color spaces and
the Profile Connection Space. It does not represent any
device model. ColorSpace conversion profiles can be
embedded in images.
color table. A collection of color element sets. The table
can also specify the method used to combine the intensity
levels of each element in an element set to produce a
specific color . Examples of methods used to combine
intensity levels are the additive method and the subtractive
method. See also color model.
column. A subarray consisting of all elements that have
an identical position within the low dimension of a regular
two-dimensional array .
command. (1) In the IPDS architecture, a structured field
sent from a host to a printer . (2) In GOCA, a data-stream
construct used to communicate from the controlling
environment to the drawing process. The command
introducer is environment dependent. (3) A request for
system action.
command set. A collection of IPDS commands.
command-set vector . Information that identifies an IPDS
command set and data level supported by a printer .
Command-set vectors are returned with an Acknowledge
Reply to an IPDS Sense T ype and Model command.
Commission Internationale d'Éclairage (CIE). An
association of international color scientists who produced
the standards that are used as the basis of the description
of color .
complex text layout. The typesetting of writing systems
that require complex transformations between text input
and text display for proper rendering on the screen or the
printed page.
compression algorithm. An algorithm used to compress
image data. Compression of image data can decrease the
volume of data required to represent an image.
construct. An architected set of data such as a structured
field or a triplet.
continuous code. A bar code symbology characterized
by designating all spaces within the symbol as parts of
characters, for example, Interleaved 2 of 5. There is no
intercharacter gap in a continuous code. Contrast with
discrete code.
continuous-form media. Connected sheets. An example
of connected sheets is sheets of paper connected by a
perforated tear strip. Contrast with cut-sheet media.
control character . (1) A character that denotes the start,
modification, or end of a control function. A control
character can be recorded for use in a subsequent action,
and it can have a graphic representation. See also
character . (2) A control function the coded representation
of which consists of a single code point.
control instruction. A data construct transmitted from
the controlling environment and interpreted by the
environment interface to control the operation of the
graphics processor .
controlled white space. White space caused by
execution of a control sequence. See also white space.
controlling environment. The environment in which an
object is embedded, for example, the IPDS and MO:DCA
data streams.
control sequence. A sequence of bytes that specifies a
control function. A control sequence consists of a control
sequence introducer and zero or more parameters.
Color Management Object Content Architecture (CMOCA) • control sequence

<!-- Page 208 -->

190 PTOCA Reference
control sequence chaining. A method used to identify a
sequential string of control sequences so they can be
processed efficiently .
control sequence class. An assigned coded character
that identifies a control sequence's syntax and how that
syntax is to be interpreted. An example of a control
sequence class is X'D3', that identifies presentation text
object control sequences.
control sequence function type. The coded character
occupying the fourth byte of an unchained control
sequence introducer . This code defines the function whose
semantics can be prescribed by succeeding control
sequence parameters.
control sequence introducer . The information at the
beginning of a control sequence. An unchained control
sequence introducer consists of a control sequence prefix,
a class, a length, and a function type. A chained control
sequence introducer consists of a length and a function
type.
control sequence length. The number of bytes used to
encode a control sequence excluding the control sequence
prefix and class.
control sequence prefix. The escape character used to
identify a control sequence. The control sequence prefix is
the first byte of a control sequence. An example of a
control sequence prefix is X'2B'.
coordinates. A pair of values that specify a position in a
coordinate space. See also absolute coordinate and
relative coordinate.
coordinate system. A Cartesian coordinate system. An
example is the image coordinate system that uses the
fourth quadrant with positive values for the Y axis. The
origin is the upper left-hand corner of the fourth quadrant.
A pair of (x,y) values corresponds to one image point. Each
image point is described by an image data element. See
also character coordinate system.
copy control. A method used to specify the number of
copies for a presentation space and the modifications to be
made to each copy .
copy counter . Bytes in an Acknowledge Reply that
identify the number of copies of a page that have passed a
particular point in the logical paper path.
copy group. A set of copy subgroups that specify all
copies of a sheet. In the IPDS architecture, a copy group is
specified by a Load Copy Control command. In MO:DCA, a
copy group is specified within a Medium Map. See also
copy subgroup.
copy modification. The process of adding, deleting, or
replacing data on selected copies of a presentation space.
copy set. A collection of pages intended to be printed
multiple times. For example, when multiple copies of a
book or booklet is printed, each copy of the book or booklet
is a copy set. This term was originally used with copy
machines to identify collections of copies that are delivered
as sets or stapled as sets. The term was also used when
printing multiple copies of an MVS™ data set.
copy subgroup. A part of a copy group that specifies a
number of identical copies of a sheet and all modifications
to those copies. Modifications include the media source,
the media destination, medium overlays to be presented
on the sheet, text suppressions, the number of pages on
the sheet, and either simplex or duplex presentation. In the
IPDS architecture, copy subgroups are specified by Load
Copy Control command entries. In MO:DCA, copy
subgroups are specified by repeating groups in the
Medium Copy Count structured field in a Medium Map. See
also copy group.
correlation. A method used in the IPDS architecture to
match exceptions with commands.
correlation ID. A two-byte value that specifies an
identifier of an IPDS command. The correlation ID is
optional and is present only if bit one of the command's flag
byte is B'1'.
CPGID. See Code Page Global Identifier .
cpi. Characters per inch.
C space. The distance from the most positive character
coordinate system X-axis value of a character shape to the
character escapement point. C-space can be positive,
zero, or negative. See also A space and B space.
current baseline coordinate. The baseline presentation
position at the present time. The baseline presentation
position is the summation of the increments of all baseline
controls since the baseline was established in the
presentation space. The baseline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Baseline control sequence. Synonymous
with current baseline presentation coordinate.
current baseline presentation coordinate (B
c
). The
baseline presentation position at the present time. The
baseline presentation position is the summation of the
increments of all baseline controls since the baseline was
established in the presentation space. The baseline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Baseline control
sequence. Synonymous with current baseline coordinate.
current baseline print coordinate (b
c
). In the IPDS
architecture, the baseline coordinate corresponding to the
current print position on a logical page. The current
baseline print coordinate is a coordinate in an I,B
coordinate system. See also I,Bcoordinate system.
control sequence chaining • current baseline print coordinate (b
c
)

<!-- Page 209 -->

PTOCA Reference 191
current drawing attributes. The set of attributes used at
the present time to direct a drawing process. Contrast with
default drawing attributes.
current drawing controls. The set of drawing controls
used at the present time to direct a drawing process.
Contrast with default drawing controls.
current inline coordinate. The inline presentation
position at the present time. This inline presentation
position is the summation of the increments of all inline
controls since the inline coordinate was established in the
presentation space. An inline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Inline control sequence. Synonymous with
current inline presentation coordinate.
current inline presentation coordinate (I
c
). The inline
presentation position at the present time. This inline
presentation position is the summation of the increments of
all inline controls since the inline coordinate was
established in the presentation space. An inline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Inline control sequence.
Synonymous with current inline coordinate.
current inline print coordinate (i
c
). In the IPDS
architecture, the inline coordinate corresponding to the
current print position on a logical page. The current inline
print coordinate is a coordinate in an I,Bcoordinate
system. See also I,Bcoordinate system.
current logical page. The logical page presentation
space that is currently being used to process the data
within a page object or an overlay object.
current position. The position identified by the current
presentation space coordinates. For example, the
coordinate position reached after the execution of a
drawing order . See also current baseline presentation
coordinate and current inline presentation coordinate.
Contrast with given position.
custom line type value. A user-defined line type, defined
by a series of pairs of a dash/dot length followed by a move
length. Contrast with standard line type value.
custom pattern. In GOCA, a user-defined pattern,
defined by the picture drawn by a series of drawing orders
between a Begin Custom Pattern drawing order and an
End Custom Pattern drawing order . Custom patterns can
be either bilevel custom patterns or full-color custom
patterns. Contrast with patterns in the default pattern set.
custom pattern mode. In GOCA, a mode that is entered
when a Begin Custom Pattern drawing order is executed
and exited when an End Custom Pattern drawing order is
executed. While in this mode, drawing is done in a
separate, temporary graphics presentation space rather
than in the graphics presentation space of the current
GOCA object.
cut-sheet media. Unconnected sheets. Contrast with
continuous-form media.
D
data block. A deprecated term for object area.
data element. A unit of data that is considered indivisible.
data frame. A rectangular division of computer output on
microfilm.
Data Map. A print control object in a Page Definition
(PageDef) that establishes the page environment and
specifies the mapping of line data to the page.
Synonymous with Page Format.
data mask. A sequence of bits that can be used to
identify boundary alignment bits in image data.
data object. In the IPDS architecture, a presentation-form
object that is either specified within a page or overlay or is
activated as a resource and later included in a page or
overlay via the IDO command. Examples include: PDF
single-page objects, Encapsulated PostScript objects, and
IO Images. See also resource and data object resource.
data-object font. (1) In the IPDS architecture, a
complete-font resource that is a combination of font
components at a particular size, character rotation, and
encoding. A data-object font can be used in a manner
analogous to a coded font. The following useful
combinations can be activated into a data-object font:
• A T rueT ype/OpenT ype font, an optional code page, and
optional linked T rueT ype/OpenT ype objects; activated at
a particular size, character rotation, and encoding
• A T rueT ype/OpenT ype collection, either an index value
or a full font name to identify the desired font within the
collection, an optional code page, and optional linked
T rueT ype/OpenT ype objects; activated at a particular
size, character rotation, and encoding
See also data-object-font component. (2) In the MO:DCA
architecture, a complete non-FOCA font resource object
that is analogous to a coded font. Examples of data-object
fonts are T rueT ype fonts and OpenT ype fonts.
data-object-font component. In the IPDS architecture, a
font resource that is either printer resident or is
downloaded using object container commands. Data-
object-font components are used as components of a data-
object font. Examples of data-object-font components
include T rueT ype/OpenT ype fonts and T rueT ype/
OpenT ype collections. See also data-object font.
data object resource. In the IPDS architecture, an
object-container resource or IO-Image resource that is
current drawing attributes • data object resource

<!-- Page 210 -->

192 PTOCA Reference
either printer resident or downloaded. Data object
resources can be:
• Used to prepare for the presentation of a data object;
such as with a color management resource (CMR) or
Resident Color Profile Resource
• Included in a page or overlay via the Include Data Object
command; examples include: PDF single-page objects,
Encapsulated PostScript objects, and IO Images
• Invoked from within a data object; examples
include: PDF Resource objects and Non-OCA Resource
objects
See also data object and resource.
data stream. A continuous stream of data that has a
defined format. An example of a defined format is a
structured field.
data-stream exception. In the IPDS architecture, a
condition that exists when the printer detects an invalid or
unsupported command, order , control, or parameter value
from the host. Data-stream exceptions are those whose
action code is X'01', X'19', or X'1F'. See also asynchronous
exception and synchronous exception.
DBCS. See double-byte character set.
decoder . In bar codes, the component of a bar code
reading system that receives the signals from the scanner ,
performs the algorithm to interpret the signals into
meaningful data, and provides the interface to other
devices. See also reader and scanner .
decryption. The process of taking encrypted data and
converting it back into data that a human or a computer
can read and understand. See also encryption.
default. A value, attribute, or option that is assumed when
none has been specified and one is needed to continue
processing. See also default drawing attributes and default
drawing controls.
default drawing attributes. The set of drawing attributes
adopted at the beginning of a drawing process and usually
at the beginning of each root segment that is processed.
See also root segment. Contrast with current drawing
attributes.
default drawing controls. The set of drawing controls
adopted at the start of a drawing process and usually at the
start of each root segment that is processed. See also root
segment. Contrast with current drawing controls.
default indicator . A field whose bits are all B'1' indicating
that a hierarchical default value is to be used. The value
can be specified by an external parameter . See also
external parameter .
default pattern set. In GOCA, a set of predefined
patterns, like solid, dots, or horizontal lines. Contrast with
custom pattern.
density . The number of characters per inch (cpi) in a bar
code symbology . In most cases, the range is three to ten
cpi. See also bar code density , character density , and
information density .
deprecated. An architected construct is marked as
“deprecated” to indicate that it should no longer be used
because it has been superseded by a newer construct.
Use or support of a deprecated construct is permitted but
no longer recommended. Constructs are deprecated rather
than immediately removed to provide backward
compatibility .
descender . The part of the character that extends into the
character coordinate system negative Y -axis region.
Examples of letters with descenders at zero-degree
character rotation are g, j, p, q, y , and Q. Contrast with
ascender .
descender depth. The character shape's most negative
character coordinate system Y -axis value.
design metrics. A set of quantitative values,
recommended by a font designer , to describe the
characters in a font.
design size. The size of the unit Em for a font. All relative
font measurement values are expressed as a proportion of
the design size. For example, the width of the letter Ican
be specified as one-fourth of the design size.
device attribute. A property or characteristic of a device.
Device-Control command set. In the IPDS architecture,
a collection of commands used to set up a page,
communicate device controls, and manage printer
acknowledgment protocol.
device dependent. Dependent upon one or more device
characteristics. An example of device dependency is a font
whose characteristics are specified in terms of addressable
positions of specific devices. See also system-level font
resource.
device independent. Not dependent upon device
characteristics.
device-independent color space. A CIE-based color
space that allows color to be expressed in a device-
independent way . It ensures colors to be predictably and
accurately matched among various color devices.
device level font resource. A device-specific font object
from which a presentation device can obtain the font
information required to present character images.
device profile. A structure that provides a means of
defining the color characteristics of a given device in a
particular state.
device resolution. The number of pels that can be
printed in an inch, both horizontally and vertically . This is
data stream • device resolution

<!-- Page 211 -->

PTOCA Reference 193
the resolution that the printer uses when printing. Some
printers can be configured to print with a variety of
resolutions that can be selected by the operator . The
device resolution can be different in the two directions (for
example, a resolution of 360 by 720).
device-version code page. In the IPDS architecture, a
device version of a code page contains all of the
characters that were registered for the CPGID at the time
the printer was developed; since then, more characters
might have been added to the registry for that CPGID. A
device-version code page is identified by a CPGID. See
also code page.
digital halftoning. A method used to simulate gray levels
on a bilevel device.
digital image. An image whose image data was sampled
at regular intervals to produce a digital representation of
the image. The digital representation is usually restricted to
a specified set of values.
dimension. The attribute of size given to arrays and
tables.
direction. In GOCA, an attribute that controls the
direction in which a character string grows relative to the
inline direction. V alues are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with character
direction.
discrete code. A bar code symbology characterized by
placing spaces that are not a part of the code between
characters, that is, intercharacter gaps.
dispersed-dot halftone. Any halftone algorithm that
turns on binary pixels individually without grouping them
into clusters. The “smallest available” dots are scattered in
a pseudorandom manner to print varying densities.
Commonly contrasted with cluster-dot screening.
dither . An intentional form of noise added to an image to
randomize quantization error . Dithering an image can
prevent unwanted patterns from appearing within the
image.
DOCS. See drawing order coordinate space.
document. (1) A machine-readable collection of one or
more objects that represents a composition, a work, or a
collection of data. (2) A publication or other written
material.
document component. An architected part of a
document data stream. Examples of document
components are documents, pages, page groups, indexes,
resource groups, objects, and process elements.
document-component hierarchy . In MO:DCA, an
ordering of the document in terms of its lower-level
components. The components are ordered by decreasing
level as follows:
• Print file (highest level)
• Document
• Page group
• Page
• Data object (lowest level)
document content architecture. A family of
architectures that define the syntax and semantics of the
document component. See also document component and
structured field.
document editing. A method used to create or modify a
document.
document element. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. An application
or device does not have to understand control information
or data to parse a data stream when all the records in the
data stream are document elements. See also structured
field.
document fidelity . The degree to which a document
presentation preserves the creator's intent.
document formatting. A method used to determine
where information is positioned in presentation spaces or
on physical media.
document presentation. A method used to produce a
visible copy of formatted information on physical media.
dot gain. The phenomenon that occurs when ink is
transferred from the plate to the blanket of the press and
finally to the paper on which it is being printed. A dot for a
halftone or a screen gets larger because of the mechanical
process of transferring ink.
dots per inch. (1) The number of dots that will fit in an
inch. (2) A unit of measure for output resolution. (3) Dots
per inch (dpi) is also used to measure the quality of input
when using a scanner . In this case, dpi becomes a square
function measuring the dots both vertically as well as
horizontally . Consequently , when an image is scanned in at
300 dpi, there are 90,000 dots or bits of electronic data
(300 x 300) in every square inch.
double-byte character set (DBCS). A character set that
can contain up to 65536 characters.
double-byte coded font. A coded font in which the code
points are two bytes long.
downloaded resource. In the IPDS architecture, a
resource in a printer that is installed and removed under
control of a host presentation services program. A
downloaded resource is referenced by a host-assigned
name that is valid for the duration of the session between
the presentation services program and the printer . Contrast
with resident resource.
dpi. See dots per inch.
device-version code page • dpi

<!-- Page 212 -->

194 PTOCA Reference
drag. T o use a pointing device to move an object. For
example, clicking on a window border , and dragging it to
make the window larger .
draw functions. Functions that can be done during the
drawing of a picture. Examples of draw functions are
displaying a picture, boundary computation, and erasing a
graphics presentation space.
drawing control. A control that determines how a picture
is drawn. Examples of drawing controls are arc
parameters, transforms, and the viewing window .
drawing defaults. In GOCA, the set of attributes adopted
at the start of each segment that is processed. These
attributes are set either from standard defaults defined by
the controlling environment or from the Set Current
Defaults instruction that is contained in the Graphics Data
Descriptor . Synonymous with default drawing attributes.
Contrast with current drawing attributes.
drawing order . In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify , for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Drawing orders
can be included in a structured field. See also order .
drawing order coordinate space (DOCS). A two-
dimensional conceptual space in which graphics primitives
are drawn, using drawing orders, to create pictures.
drawing process control. In GOCA, a control used by
the graphics processor that determines how a picture is
drawn. Examples of drawing process controls are arc
parameters.
drawing processor . A graphics processor component
that executes segments to draw a picture in a presentation
space. See also segment, graphics presentation space,
and image presentation space.
drawing units. Units of measurement used within a
graphics presentation space to specify absolute and
relative positions.
draw rule. A method used to construct a line, called a
rule, between two specified presentation positions. The line
that is constructed is either parallel to the inline I axis or
baseline B axis.
duplex. A method used to print data on both sides of a
sheet. Normal-duplex printing occurs when the sheet is
turned over the Y
m
axis. T umble-duplex printing occurs
when the sheet is turned over the X
m
axis.
duplex printing. A method used to print data on both
sides of a sheet. Contrast with simplex printing.
dynamic segment. A segment whose graphics primitives
can be redrawn in different positions by dragging them
from one position to the next across a picture without
destroying the traversed parts of the picture.
E
EAN. See European Article Numbering.
EBCDIC. See Extended Binary-Coded Decimal
Interchange Code.
Efficient XML Interchange (EXI). A format that allows
XML documents to be encoded as binary data, rather than
as plain text.
element. (1) A bar or space in a bar code character or a
bar code symbol. (2) A structured field in a document
content architecture data stream. (3) In GOCA, a portion of
a segment consisting of either a single order or a group of
orders enclosed in an element bracket, in other words,
between a begin element and an end element. (4) A basic
member of a mathematical or logical class or set.
Em. In printing, a unit of linear measure referring to the
baseline-to-baseline distance of a font, in the absence of
any external leading.
embedded ICC profile. ICC profiles that are embedded
within graphic documents and images. An embedded ICC
profile allows users to transparently move color data
between different computers, networks and even operating
systems without having to worry if the necessary profiles
are present on the destination systems.
Em square. A square layout space used for designing
each of the characters of a font.
encoding scheme. A set of specific definitions that
describe the philosophy used to represent character data.
The number of bits, the number of bytes, the allowable
ranges of bytes, the maximum number of characters, and
the meanings assigned to some generic and specific bit
patterns, are some examples of specifications to be found
in such a definition.
Encoding Scheme Identifier (ESID). A 16-bit number
assigned to uniquely identify a particular encoding scheme
specification. See also encoding scheme.
encryption. A process to manipulate data to achieve data
security . T o read an encrypted data string, access to key
information that enables decryption of the data is required.
See also decryption.
environment interface. The part of the graphics
processor that interprets commands and instructions from
the controlling environment.
EPS. Acronym for Encapsulated PostScript. A standard
file format for importing and exporting PostScript language
drag • EPS

<!-- Page 213 -->

PTOCA Reference 195
files among applications in a variety of heterogeneous
environments.
error diffusion halftone. A specific halftone method in
which quantization errors are dif fused spatially in a quasi-
random manner .
escapement direction. In FOCA, the direction from a
character reference point to the character escapement
point, that is, the font designer's intended direction for
successive character shapes. See also character direction
and inline direction.
escape sequence. (1) In the IPDS architecture, the first
two bytes of a control sequence. An example of an escape
sequence is X'2BD3'. (2) A string of bit combinations that
is used for control in code extension procedures. The first
of these bit combinations represents the control function
Escape.
ESID. See Encoding Scheme Identifier .
established baseline coordinate. The current baseline
presentation coordinate when no temporary baseline exists
or the last current baseline presentation coordinate that
existed before the first active temporary baseline was
created. If temporary baselines are created, the current
baseline presentation coordinate coincides with the
presentation coordinate of the most recently created
temporary baseline.
European Article Numbering (EAN). The bar code
symbology used to code grocery items in Europe.
exception. (1) An invalid or unsupported data-stream
construct. (2) In the IPDS architecture, a condition
requiring host notification. (3) In the IPDS architecture, a
condition that requires the host to resend data. See also
data-stream exception, asynchronous exception, and
synchronous exception.
exception action. Action taken when an exception is
detected.
exception condition. The condition that exists when a
product finds an invalid or unsupported construct.
exchange. The predictable interpretation of shared
information by a family of system processes in an
environment where the characteristics of each process
must be known to all other processes. Contrast with
interchange.
EXI. See Efficient XML Interchange.
expanded. A type width that widens all characters of a
typeface.
Extended Binary-Coded Decimal Interchange Code
(EBCDIC). A coded character set that consists of eight-bit
coded characters.
Extensible Markup Language (XML). A set of rules for
encoding documents in a format that is both human-
readable and machine-readable.
Extensible Metadata Platform (XMP). An ISO standard,
originally created by Adobe Systems Incorporated, for the
creation, processing, and interchange of standardized and
custom metadata for all kinds of resources.
external leading. The amount of white space, in addition
to the internal leading, that can be added to interline
spacing without degrading the aesthetic appearance of a
font. This value is usually specified by a font designer .
Contrast with internal leading.
external parameter . A parameter for which the current
value can be provided by the controlling environment, for
example, the data stream, or by the application itself.
Contrast with internal parameter .
F
factoring. The movement of a parameter value from one
state to a higher-level state. This permits the parameter
value to apply to all of the lower-level states unless
specifically overridden at the lower level.
FGID. See Font T ypeface Global Identifier .
filename map file. A file containing the mapping of object
names to file names for use in establishing a font file
system. Object names and file names do not conform to
the same naming requirements, so it is necessary to
provide a mapping between them. The mapping
information in this file is in an ASCII file format defined by
Adobe Systems Inc.
fillet. A curved line drawn tangential to a specified set of
straight lines. An example of a fillet is the concave junction
formed where two lines meet.
final form data. Data that has been formatted for
presentation.
first read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with read rate.
fixed medium information. Information that can be
applied to a sheet by a printer or printer-attached device
that is independent of data provided through the data
stream. Fixed medium information does not mix with the
data provided by the data stream and is presented on a
sheet either before or after the text, image, graphics, or bar
code data provided within the data stream. Fixed medium
information can be used to create preprinted forms, or
other types of printing, such as colored logos or
letterheads, that cannot be created conveniently within the
data stream.
error diffusion halftone • fixed medium information

<!-- Page 214 -->

196 PTOCA Reference
fixed metrics. Graphic character measurements in
physical units such as pels, inches, or centimeters.
FNN linked. In FOCA, the FNN (Font Name map)
structured field permits the mapping of a set of IBM
GCGIDs to the character index values that occur in either a
CMAP file or a rearranged file. Because the set of GCGIDs
and the set of character index values must correspond to
the same set of characters, it is necessary to identify which
CMAP or rearranged file (among the many that could be
located in a font file system) is associated (linked) with the
FNN structured field. Note that the Font Name Map is
known as the Character ID Map in IPDS.
FOCA. See Font Object Content Architecture.
font. A set of graphic characters that have a characteristic
design, or a font designer's concept of how the graphic
characters should appear . The characteristic design
specifies the characteristics of its graphic characters.
Examples of characteristics are character shape, graphic
pattern, style, size, weight class, and increment. Examples
of fonts are fully described fonts, symbol sets, and their
internal printer representations. See also coded font and
symbol set.
font baseline extent. In the IPDS architecture, the sum
of the uniform or maximum baseline offset and the
maximum baseline descender of all characters in the font.
font character set. A FOCA resource containing
descriptive information, font metrics, and the digital
representation of character shapes for a specified graphic
character set.
font control record. The record sent in an IPDS Load
Font Control command to specify a font ID and other font
parameters that apply to the complete font.
font height (FH). (1) A characteristic value, perpendicular
to the character baseline, that represents the size of all
graphic characters in a font. Synonymous with vertical font
size. (2) In a font character set, nominal font height is a
font-designer defined value corresponding to the nominal
distance between adjacent baselines when character
rotation is zero degrees and no external leading is used.
This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified font height is
the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
font index. (1) The mapping of a descriptive font name to
a font member name in a font library . An example of a font
member in a font library is a font resource object.
Examples of attributes used to form a descriptive font
name are typeface, family name, point size, style, weight
class, and width class. (2) In the IPDS architecture, an
LF1-type raster-font resource containing character metrics
for each code point of a raster font or raster-font section for
a particular font inline sequence. There can be a font index
for 0 degree, 90 degree, 180 degree, and 270 degree font
inline sequences. A font index can be downloaded to a
printer using the Load Font Index command. An LF1-type
coded font or coded-font section is the combination of one
fully described font and one font index. See also fully
described font.
font inline sequence. The clockwise rotation of the inline
direction relative to a character pattern. Character rotation
and font inline sequence are related in that character
rotation is a clockwise rotation; font inline sequence is a
counter-clockwise rotation.
font local identifier . A binary identifier that is mapped by
the controlling environment to a named resource to identify
a font. See also local identifier .
font metrics. Measurement information that defines
individual character values such as height, width, and
space, as well as overall font values such as averages and
maximums. Font metrics can be expressed in specific fixed
units, such as pels, or in relative units that are independent
of both the resolution and the size of the font. See also
character metrics and character set metrics.
font modification parameters. Parameters that alter the
appearance of a typeface.
font object. A resource object that contains some or all of
the description of a font.
Font Object Content Architecture (FOCA). An
architected collection of constructs used to describe fonts
and to interchange those font descriptions.
font production. A method used to create a font. This
method includes designing each character image,
converting the character images to a digital-technology
format, defining parameter values for each character ,
assigning appropriate descriptive and identifying
information, and creating a font resource that contains the
required information in a format that can be used by a text
processing system. Digital-technology formats include bit
image, vector drawing orders, and outline algorithms.
Parameter values include such attributes as height, width,
and escapement.
font referencing. A method used to identify or
characterize a font. Examples of processes that use font
referencing are document editing, document formatting,
and document presentation.
Font T ypeface Global Identifier (FGID). A unique font
identifier that can be expressed as either a two-byte binary
or a five-digit decimal value. The FGID is used to identify a
type style and the following characteristics: posture, weight
class, and width class.
fixed metrics • Font T ypeface Global Identifier (FGID)

<!-- Page 215 -->

PTOCA Reference 197
font width (FW). (1) A characteristic value, parallel to the
character baseline, that represents the size of all graphic
characters in a font. Synonymous with horizontal font
size. (2) In a font character set, nominal font width is a
font-designer defined value corresponding to the nominal
character increment for a font character set. The value is
generally the width of the space character and is defined
differently for fonts with different spacing characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic, proportionally spaced fonts: one-third of
the vertical font size, that is also the default size of the
space character .
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified font width is
the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
foreground. (1) The part of a presentation space that is
occupied by object data. (2) In GOCA, the portion of a
graphics primitive that is mixed into the presentation space
under the control of the current value of the mix and color
attributes. See also pel. Contrast with background.
foreground color . A color attribute used to specify the
color of the foreground of a primitive. Contrast with
background color .
foreground mix. An attribute used to determine how the
foreground color of data is combined with the existing color
of a graphics presentation space. An example of data is a
graphics primitive. Contrast with background mix.
form. A division of the physical medium; multiple forms
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper , each representing a form. Envelopes are an
example of a physical medium that comprises only one
form. The IPDS architecture defines four types of
forms: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
form has a top edge. A form has two sides, a front side and
a back side. Synonymous with sheet.
format. The arrangement or layout of data on a physical
medium or in a presentation space.
formatter . A process used to prepare a document for
presentation.
formblend. (1) In IPDS, this mixing rule is only used
when a preprinted form overlay (PFO) is merged as
presentation space P
PFO
with other presentation data
(presentation space P
data
). The intersection of P
PFO
and
P
data
is assigned the following color attribute:
• Wherever the color attribute of P
PFO
is either color of
medium, or “white” (CMYK = X'00000000' for a printer ,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P
data
. Likewise,
wherever the color attribute of P
data
is either color of
medium, or “white” (CMYK = X'00000000' for a printer ,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P
PFO
.
• With other overlapping color values, the intersection
assumes a new color attribute that is generated in a
device-specific manner to simulate how the P
data
color
attribute would mix onto a preprinted form that has the
color attribute of P
PFO
. In general, this mixing is a
blending of the color attributes of P
data
and P
PFO
that is
determined by the two color attributes and by the print
media and the print technology .
See also mixing rule. (2) In MO:DCA, this mixing rule is
only used when a simulated preprinted form, which is
simulated as either a Medium Preprinted Form overlay (M-
PFO) or a PMC Preprinted Form overlay (PMC-PFO), is
merged as a new presentation space P
n
, onto an existing
presentation space P
e
. The intersection of the foregrounds
of P
n
and P
e
is assigned the following color attribute:
• Wherever the color attribute of P
e
is either the color of
medium, or the color white (CMYK = X'00000000' or
RGB = X'FFFFFF'), the intersection is assigned the color
attribute of P
n
.
• Wherever the color attribute of P
e
is not the color of
medium and not the color white, the intersection
assumes a new color attribute that is generated in a
device-specific manner to simulate how the P
e
color
attribute would mix onto a preprinted form that has the
color attribute of P
n
. In general, this mixing is a blending
of the color attributes of P
n
and P
e
that is determined by
the two color attributes and by the print media and the
print technology .
Formdef. See Form Definition.
Form Definition (Formdef). A print control object that
contains an environment definition and one or more
Medium Maps. Synonymous with Form map.
Form Map. A print control object that contains an
environment definition and one or more Medium Maps.
Synonymous with Form Definition. See also Medium Map.
full arc. A complete circle or ellipse. See also arc.
full-color custom pattern. In GOCA, a custom pattern
that has its colors completely assigned during its definition,
and can therefore contain any number of colors. Contrast
with bilevel custom pattern.
fully described font. In the IPDS architecture, an LF1-
type raster-font resource containing font metrics,
descriptive information, and the raster representation of
character shapes, for a specific graphic character set. A
fully described font can be downloaded to a printer using
the Load Font Control and Load Font commands. An LF1-
type coded font or coded-font section is the combination of
font width (FW) • fully described font

<!-- Page 216 -->

198 PTOCA Reference
one fully described font and one font index. See also font
index.
function set. (1) A collection of architecture constructs
and associated values. Function sets can be defined
across or within subsets. (2) In the MO:DCA architecture,
a formal extension to a MO:DCA interchange set that
provides additional capabilities beyond those provided by
the interchange set.
FW . See font width.
G
gamma. A measure of contrast in photographic images.
More precisely , a parameter that describes the shape of
the transfer function for one or more stages in an imaging
pipeline. The transfer function is given by the expression
output = input
gamma
where both input and output are scaled
to the range 0 to 1.
gamut. In color reproduction, the subset of colors that can
be accurately represented in a given circumstance, such
as within a given color space or by a certain output device.
GCGID. See Graphic Character Global Identifier .
GCSGID. See Graphic Character Set Global Identifier .
GCUID. See Graphic Character UCS Identifier .
generic. Relating to, or characteristic of, a whole group or
class.
GID. See global identifier .
GIF . See Graphic Interchange Format.
given position. The coordinate position at which drawing
is to begin. A given position is specified in a drawing order .
Contrast with current position.
GLC chain. The set of glyph layout control sequences
used to present a set of glyphs. It consists of a GLC control
sequence followed by one or more GIR/GAR/GOR control
sequence groupings, wherein the GOR is always optional.
These control sequences must be chained together using
PTOCA chaining rules. No other control sequences can be
interspersed within the GIR/GAR/GOR groupings or
between the groupings. The GLC chain may be terminated
by an optional UCT control sequence that carries the code
points of the glyphs rendered by the GLC chain.
Global Identifier (GID). Any of the following:
• Coded Character Set Identifier (CCSID)
• Coded Graphic Character Set Global Identifier
(CGCSGID)
• Code Page Global ID (CPGID)
• Font T ypeface Global Identifier (FGID)
• Global Resource Identifier (GRID)
• Graphic Character Global Identifier (GCGID)
• Graphic Character Set Global Identifier (GCSGID)
• Graphic Character UCS Identifier (GCUID)
• An identifier used by a data object to reference a
resource
• In MO:DCA, an encoded graphic character string that
provides a reference name for a document element
• Object identifier (OID)
• A Uniform Resource Locator (URL), as defined in RFC
1738, Internet Engineering T ask Force (IETF),
December , 1994.
Global Resource Identifier (GRID). An eight-byte
identifier that identifies a coded font resource. A GRID
contains the following fields in the order shown:
1. GCSGID of a minimum set of graphic characters
required for presentation; it can be a character set that
is associated with the code page, or with the font
character set, or with both
2. CPGID of the associated code page
3. FGID of the associated font character set
4. Font width in 1440ths of an inch.
glyph. (1) A member of a set of symbols that represent
data. Glyphs can be letters, digits, punctuation marks, or
other symbols. Synonymous with graphic character . See
also character . (2) In typography , a glyph is a particular
graphical representation of a grapheme, or sometimes
several graphemes in combination (a composed glyph), or
only a part of a grapheme. In computing as well as
typography , the term character refers to a grapheme or
grapheme-like unit of text, as found in natural language
writing systems (scripts). A character or grapheme is a unit
of text, whereas a glyph is a graphical unit. T rueT ype/
OpenT ype fonts describe glyphs as a set of paths.
glyph advance. A glyph advance is the absolute
displacement of a glyph's origin on the baseline in the
inline direction from a specific point. In the context of
complex text rendering using GLC chains, the specific
point is the current text position at the beginning of the
GLC chain.
glyph ID. A glyph ID is an index to a table entry in a
T rueT ype/OpenT ype font that allows an application to
retrieve the glyph's shape data.
glyph offset. A glyph offset is the offset of the glyph's
origin from the current baseline in the baseline direction. In
the context of complex text rendering using GLC chains,
the current baseline is the baseline defined at the
beginning of the GLC chain.
GOCA. See Graphics Object Content Architecture.
GPS. See graphics presentation space.
function set • GPS

<!-- Page 217 -->

PTOCA Reference 199
gradient. In GOCA, an area fill where one color gradually
changes to another . A gradient is a type of pattern.
grapheme. (1) A minimally distinctive unit of writing in the
context of a particular writing system. For example, å (“a +
Combining Ring Above” or “Latin Small Letter A with Ring
Above”) is a grapheme in the Danish writing system.
(2) What an end-user thinks of as a character . (3) In
typography , a grapheme is the fundamental unit in written
language. Graphemes include alphabetic letters, Chinese
characters, numerals, punctuation marks, and all the
individual symbols of any of the world's writing systems. In
a typeface each character typically corresponds to a single
glyph, but there are exceptions, such as a font used for a
language with a large alphabet or complex writing system,
where one character may correspond to several glyphs, or
several characters to one glyph.
graphic arts. Image rich, customized content that is
typically used for brochures and marketing documents.
graphic character . A member of a set of symbols that
represent data. Graphic characters can be letters, digits,
punctuation marks, or other symbols. Synonymous with
glyph. See also character .
Graphic Character Global Identifier (GCGID). An
alphanumeric character string used to identify a specific
graphic character . A GCGID can be from four bytes to eight
bytes long.
graphic character identifier . The unique name for a
graphic character in a font or in a graphic character set.
See also character identifier .
Graphic Character Set Global Identifier (GCSGID). A
unique graphic character set identifier that can be
expressed as either a two-byte binary or a five-digit
decimal value.
Graphic Character UCS Identifier (GCUID). An
alphanumeric character string used to identify a specific
graphic character . The GCUID naming scheme is used for
additional characters and sets of characters that exist in
UNICODE; each GCUID begins with the letter U and ends
with a UNICODE code point. The Unicode Standard is fully
compatible with the earlier Universal Character Set (UCS)
Standard.
Graphic Interchange Format (GIF). An image format
type generated specifically for computer use. Its resolution
is usually very low (72 dpi, or that of your computer
screen), making it undesirable for printing purposes.
Graphics command set. In the IPDS architecture, a
collection of commands used to present GOCA data in a
page, page segment, or overlay .
graphics data. Data containing lines, arcs, markers, and
other constructs that describe a picture.
graphics model space. A two-dimensional conceptual
space in which a picture is constructed. All model
transforms are completed before a picture is constructed in
a graphics model space. Contrast with graphics
presentation space. Synonymous with model space.
graphics object. An object that contains graphics data.
See also object.
graphics object area. A rectangular area on a logical
page into which a graphics presentation space window is
mapped.
Graphics Object Content Architecture (GOCA). An
architected collection of constructs used to interchange
and present graphics data. GOCA was originally defined by
IBM; this architecture is no longer used in AFP . Instead, a
subset of GOCA was defined for use in AFP environments,
called AFP GOCA. Usually when the term “GOCA” is used
in AFP documentation, it means AFP GOCA.
graphics presentation space. A two-dimensional
conceptual space in which a picture is constructed. In this
space graphics drawing orders are defined. The picture
can then be mapped onto an output medium. All viewing
transforms are completed before the picture is generated
for presentation on an output medium. An example of a
graphics presentation space is the abstract space
containing graphics pictures defined in an IPDS Wri te
Graphics Control command. Contrast with graphics model
space.
graphics presentation space window . The portion of a
graphics presentation space that can be mapped to a
graphics object area on a logical page.
graphics primitive. A basic construct used by an output
device to draw a picture. Examples of graphics primitives
are arc, line, fillet, character string, and marker .
graphics processor . The processing capability required
to interpret a GOCA object, that is, to present the picture
represented by the object. It includes the environment
interface, that interprets commands and instructions, and
the drawing processor , that interprets the drawing orders.
graphics segment. A set of graphics drawing orders
contained within a Begin Segment command. See also
segment.
grayscale. A means of specifying color using only one
color component in shades of gray ranging from black to
white.
grayscale image. Images whose image data elements
are represented by multiple bits and whose image data
element values are mapped to more than one level of
brightness through an image data element structure
parameter or a look-up table.
GRID. See Global Resource Identifier .
gradient • GRID

<!-- Page 218 -->

200 PTOCA Reference
guard bars. The bars at both ends and the center of an
EAN, JAN, or UPC symbol, that provide reference points
for scanning.
gzip. A widely-used, free software compression
algorithm.
H
HAID. See Host-Assigned ID.
halftone. A method of generating, on a press or laser
printer , an image that requires varying densities or shades
to accurately render the image. This is achieved by
representing the image as a pattern of dots of varying size.
Larger dots represent darker areas, and smaller dots
represent lighter areas of an image.
hard object. An object that is mapped with a Map
structured field in the environment group of a Form Map,
page, or overlay , that causes the server to retrieve the
object and send it to the presentation device. The object is
then referenced for inclusion at a later time. Contrast with
soft object.
height. In bar codes, the bar dimension perpendicular to
the bar width. Synonymous with bar height and bar length.
hexadecimal. A number system with a base of sixteen.
The decimal digits 0 through 9 and characters A through F
are used to represent hexadecimal digits. The hexadecimal
digits A through F correspond to the decimal numbers 10
through 15, respectively . An example of a hexadecimal
number is X'1B', that is equal to the decimal number 27.
hierarchy . A series of elements that have been graded or
ranked in some useful manner .
highlight color . A spot color that is used to accentuate or
contrast monochromatic areas. See also spot color .
highlighting. The emphasis of displayed or printed
information. Examples are increased intensity of selected
characters on a display screen and exception highlighting
on an IPDS printer .
hollow font. A font design in which the graphic character
shapes include only the outer edges of the strokes.
home state. An initial IPDS operating state. A printer
returns to home state at the end of each page, and after
downloading a font, overlay , or page segment.
horizontal bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the X
bc
axis of the bar code presentation space. Synonymous with
picket fence bar code.
horizontal font size. (1) A characteristic value, parallel to
the character baseline, that represents the size of all
graphic characters in a font. Synonymous with font
width. (2) In a font character set, nominal horizontal font
size is a font-designer defined value corresponding to the
nominal character increment for a font character set. The
value is generally the width of the space character and is
defined differently for fonts with different spacing
characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic fonts and proportionally spaced fonts:
one-third of the vertical font size, that is also the default
size of the space character .
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified horizontal font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
horizontal scale factor . (1) In outline-font referencing,
the specified horizontal adjustment of the Em square. The
horizontal scale factor is specified in 1440ths of an inch.
When the horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also vertical scale
factor . (2) In FOCA, the numerator of a scaling ratio,
determined by dividing the horizontal scale factor by the
vertical font size. If the value specified is greater or less
than the specified vertical font size, the graphic characters
and their corresponding metric values are stretched or
compressed in the horizontal direction relative to the
vertical direction by the scaling ratio indicated.
host. (1) In the IPDS architecture, a computer that drives
a printer . (2) In IOCA, the host is the controlling
environment.
Host-Assigned ID (HAID). A two-byte ID in the range
X'0001'–X'7EFF' that is assigned to an IPDS resource by a
presentation-services program in the host. This ID uniquely
identifies a resource until that resource is deactivated, in
which case the HAID can be reused. HAIDs are used in
IPDS resource management commands.
Host-Assigned Resource ID. The combination of a
Host-Assigned ID with a section identifier , or a font inline
sequence, or both. The section identifier and font inline
sequence values are ignored for both page segments and
overlays. See also section identifier and font inline
sequence.
HRI. See human-readable interpretation.
HSV color space. (1) A transformation of the RGBcolor
space that allow colors to be described in terms more
natural to an artist. The name HSV stands for hue,
saturation, and value. (2) Abbreviation for hue, saturation,
and value (a color model used in some graphics
programs). HSV must be translated to another model for
color printing or for forming screen colors.
guard bars • HSV color space

<!-- Page 219 -->

PTOCA Reference 201
human-readable interpretation (HRI). The printed
translation of bar code characters into equivalent Latin
alphabetic characters, Arabic numeral decimal digits, and
common special characters normally used for printed
human communication.
hypermedia. Interlinked pieces of information consisting
of a variety of data types such as text, graphics data,
image, audio, and video.
hypertext. Interlinked pieces of information consisting
primarily of text.
I
+I. Positive inline direction.
I. See inline direction.
I axis. The axis of an I,Bcoordinate system that extends
in the inline direction. The I axis does not have to be
parallel to the X
p
axis of its bounding X
p
,Y
p
coordinate
space.
I,Bcoordinate system. The coordinate system used to
present graphic characters. This coordinate system is used
to establish the inline direction and baseline direction for
the placement of successive graphic characters within a
presentation space. See also X
p
,Y
p
coordinate system.
I
c
. See current inline presentation coordinate.
i
c
. See current inline print coordinate.
ICC. See International Color Consortium.
ICC-absolute colorimetric. A rendering intent in which
the chromatically adapted tristimulus values of the in-
gamut colors are unchanged. It is useful for spot colors and
when simulating one medium on another (proofing). Note
that this definition of ICC-absolute colorimetry is actually
called “relative colorimetry” in CIE terminology , since the
data has been normalized relative to the perfect dif fuser
viewed under the same illumination source as the sample.
ICC DeviceLink profile. An ICC profile that provides a
mechanism in which to save and store a series of device
profiles and non-device profiles in a concatenated format
as long as the series begins and ends with a device profile.
This is useful for workflows where a combination of device
profiles and non-device profiles are used repeatedly .
ICC profile. A file in the International Color Consortium
profile format, containing information about the color
reproduction capabilities of a device such as a scanner , a
digital camera, a monitor , or a printer . An ICC profile
includes three elements: 128-byte file header , tag table,
and tagged element data. The intent of this format is to
provide a cross-platform device profile format. Such device
profiles can be used to translate color data created on one
device into another device's native color space.
ID. Identifier . See also Host-Assigned ID (HAID),
correlation ID, font control record, and overlay ID.
IDE. See image data element.
I direction. (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with inline direction.
IDP . See image data parameter .
IEEE. Institute of Electrical and Electronics Engineers.
I extent. The X
p
extent when the I axis is parallel to the X
p
axis or the Y
p
extent when the I axis is parallel to the Y
p
axis. The definition of the I extent depends on the X
p
or Y
p
extent because the I,Bcoordinate system is contained
within an X
p
,Y
p
coordinate system.
i
i
. See initial inline print coordinate.
illuminant. Something that can serve as a source of light.
image. An electronic representation of a picture produced
by means of sensing light, sound, electron radiation, or
other emanations coming from the picture or reflected by
the picture. An image can also be generated directly by
software without reference to an existing picture.
image block. A deprecated term for image object area.
image content. Image data and its associated image
data parameters.
image coordinate system. An X,Y Cartesian coordinate
system using only the fourth quadrant with positive values
for the Y axis. The origin of an image coordinate system is
its upper left hand corner . An X,Y coordinate specifies a
presentation position that corresponds to one and only one
image data element in the image content.
image data. Rectangular arrays of raster information that
define an image.
image data element (IDE). A basic unit of image
information. An image data element expresses the
intensity of a signal at a corresponding image point. An
image data element can use a look-up table to introduce a
level of indirection into the expression of grayscale image
or color image.
image data parameter (IDP). A parameter that describes
characteristics of image data.
image distortion. Deformation of an image such that the
original proportions of the image are changed and the
original balance and symmetry of the image are lost.
image object. An object that contains image data. See
also object.
human-readable interpretation (HRI) • image object

<!-- Page 220 -->

202 PTOCA Reference
image object area. A rectangular area on a logical page
into which an image presentation space is mapped.
Image Object Content Architecture (IOCA). An
architected collection of constructs used to interchange
and present images.
image point. A discrete X,Y coordinate in the image
presentation space. See also addressable position.
image presentation space (IPS). A two-dimensional
conceptual space in which an image is generated.
image segment. Image content bracketed by Begin
Segment and End Segment self-defining fields. See also
segment.
IM Image. A migration image object that is resolution
dependent, bi level, and cannot be compressed or scaled.
Contrast with IO Image.
IM-Image command set. In the IPDS architecture, a
collection of commands used to present IM-Image data in a
page, page segment, or overlay .
immediate mode. The mode in which segments are
executed as they are received and then discarded.
Contrast with store mode.
indexed color . A color image format that contains a
palette of colors to define the image. Indexed color can
reduce file size while maintaining visual quality .
indexed object. An object in a MO:DCA document that is
referenced by an Index Element structured field in a
MO:DCA index. Examples of indexed objects are pages
and page groups.
information density . The number of characters per inch
(cpi) in a bar code symbology . In most cases, the range is
three to ten cpi. See also bar code density , character
density , and density .
initial addressable position. The values assigned to I
c
and B
c
by the data stream at the start of object state. The
standard action values are I
o
and B
o
.
initial baseline print coordinate (b
i
). The baseline
coordinate of the first print position on a logical page. See
also initial inline print coordinate.
initial inline print coordinate (i
i
). The inline coordinate
of the first print position on a logical page. See also initial
baseline print coordinate.
inline-baseline coordinate system. See I,Bcoordinate
system.
inline coordinate. The first of a pair of values that
identifies the position of an addressable position with
respect to the origin of a specified I,Bcoordinate system.
This value is specified as a distance in addressable
positions from the B axis of an I,Bcoordinate system.
inline direction (I). (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with I direction.
inline margin. The inline coordinate that identifies the
initial addressable position for a line of text.
inline presentation origin (I
o
). The point on the I axis
where the value of the inline coordinate is zero.
inline resource. A resource object carried in a resource
group that precedes all documents in an AFP print file.
input profile. An ICC profile that is associated with the
image and describes the characteristics of the device on
which the image was created.
instruction CMR. A color management resource that
identifies processing that is to be done to an object.
Intelligent Printer Data Stream (IPDS). An architected
host-to-printer data stream that contains both data and
controls defining how the data is to be presented.
intensity . The extreme strength, degree, or amount of
ink.
interchange. The predictable interpretation of shared
information in an environment where the characteristics of
each process need not be known to all other processes.
Contrast with exchange.
interchange set. A defined set of MO:DCA function that
describes a level of interchange.
intercharacter adjustment. Additional distance applied
to a character increment that increases or decreases the
distance between presentation positions, effectively
modifying the amount of white space between graphic
characters. The amount of white space between graphic
characters is changed to spread the characters of a word
for emphasis, distribute excess white space on a line
among the words of that line to achieve right justification, or
move the characters on the line closer together as in
kerning. Examples of intercharacter adjustment are
intercharacter increment and intercharacter decrement.
intercharacter decrement. Intercharacter adjustment
applied in the negative I direction from the current
presentation position. See also intercharacter adjustment.
intercharacter gap. In bar codes, the space between two
adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter space. Contrast with clear
area, element, and space.
image object area • intercharacter gap

<!-- Page 221 -->

PTOCA Reference 203
intercharacter increment. Intercharacter adjustment
applied in the positive I direction from the current
presentation position. See also intercharacter adjustment.
intercharacter space. In bar codes, the space between
two adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter gap. Contrast with
element and space.
interleaved bar code. A bar code symbology in which
characters are paired, using bars to represent the first
character and spaces to represent the second. An example
is Interleaved 2 of 5.
intermediate device. In the IPDS architecture, a device
that operates on the data stream and is situated between a
printer and a presentation services program in the host.
Examples include devices that capture and cache
resources and devices that spool the data stream.
internal leading. A font design parameter referring to the
space provided between lines of type to keep ascenders
separated from descenders and to provide an aesthetically
pleasing interline spacing. The value of this parameter
usually equals the dif ference between the vertical font size
and the font baseline extent. Contrast with external
leading.
internal parameter . In PTOCA, a parameter whose
current value is contained within the object. Contrast with
external parameter .
International Color Consortium (ICC). A group of
companies chartered to develop, use, and promote cross-
platform standards so that applications and devices can
exchange color data without ambiguity .
International Organization for Standardization
(ISO). An organization of national standards bodies from
various countries established to promote development of
standards to facilitate international exchange of goods and
services, and develop cooperation in intellectual, scientific,
technological, and economic activity .
interoperability . The capability to communicate, execute
programs, or transfer data among various functional units
in a way that requires the user to have little or no
knowledge of the unique characteristics of those units.
introducer . In GOCA, that part of the data stream passed
from a controlling environment to a communication
processor that indicates whether entities are to be
processed in immediate mode or store mode. See also
immediate mode and store mode.
I
o
. See inline presentation origin.
IOCA. See Image Object Content Architecture.
IO Image. An image object containing IOCA constructs.
Contrast with IM Image.
IO-Image command set. In the IPDS architecture, a
collection of commands used to present IOCA data in a
page, page segment, or overlay .
IPDS. See Intelligent Printer Data Stream.
IPDS dialog. A series of IPDS commands and IPDS
Acknowledge Replies. An IPDS dialog begins with the first
IPDS command that an IPDS device receives and ends
either when an IPDS command explicitly ends the dialog or
when the carrying-protocol session ends. There can be
multiple independent sessions each with an IPDS dialog.
See also session.
IPS. See image presentation space.
ISO. See International Organization for Standardization.
italics. A typeface with characters that slant upward to
the right. In FOCA, italics is the common name for the
defined inclined typeface posture attribute or parameter .
J
JAN. See Japanese Article Numbering.
Japanese Article Numbering (JAN). The bar code
symbology used to code grocery items in Japan.
JFIF . See JPEG File Interchange Format.
jog. T o cause printed sheets to be stacked in an output
stacker offset from previously stacked sheets. Jogging is
requested by using an IPDS Execute Order Anystate
Alternate Offset Stacker command.
Joint Photographic Experts Group (JPEG). The Joint
Photographic Experts Group (JPEG) is a standards
committee that designed an image compression format.
The compression format they designed is lossy , in that it
deletes information from an image that it considers
unnecessary . JPEG files can range from small amounts of
lossless compression to large amounts of lossy
compression.
JPEG. An image compression standard. See Joint
Photographic Experts Group.
JPEG File Interchange Format (JFIF). (1) JPEG File
Interchange Format (JFIF) is the most common file format
for JPEG images. (TIFF is another file format that can be
used to store JPEG images, and JNG is a third.) JFIF is not
a formal standard; it was designed by a group of
companies (though it is most often associated with C-Cube
Microsystems, one of whose employees published it) and
became a de facto industry standard. (2) Three-
component JPEG images. RGB data is assumed without
intercharacter increment • JPEG File Interchange Format (JFIF)

<!-- Page 222 -->

204 PTOCA Reference
gamma correction and the APP0 marker is used to specify
the resolution and optionally the thumbnail.
K
Kanji. A graphic character set for symbols used in
Japanese ideographic alphabets.
kerning. The design of graphic characters so that their
character boxes overlap, resulting in the reduction of space
between characters. This allows characters to be designed
for cursive languages, ligatures, and proportionally spaced
fonts. An example of kerning is the printing of adjacent
graphic characters so they overlap on the left or right side.
kerning track. A straight-line graph that associates
vertical font size with white space adjustment. The result of
this association is used to scale fonts.
kerning track intercept. The X-intercept of a kerning
track for a given vertical font size or white space
adjustment value.
kerning track slope. The slope of a kerning track.
key information. Bytes used by the decryption system to
decrypt data that has been encrypted.
keyword. A two-part self-defining parameter consisting of
a one-byte identifier and a one-byte value.
L
ladder bar code. A bar code pattern presenting the axis
of the symbol in its length dimension parallel to the Y
bc
axis
of the bar code presentation space. Synonymous with
vertical bar code.
LAN. See local area network.
landscape. A presentation orientation in which the X
m
axis is parallel to the long sides of a rectangular physical
medium. Contrast with portrait.
language. A set of symbols, conventions, and rules that
is used for conveying information. See also pragmatics,
semantics, and syntax.
LCID. See Local Character Set Identifier .
leading. A printer's term for the amount of space between
lines of a printed page. Leading refers to the lead slug
placed between lines of type in traditional typesetting. See
also internal leading and external leading.
leading edge. (1) The edge of a character box that in the
inline direction precedes the graphic character . (2) The
front edge of a sheet as it moves through a printer .
legibility . Characteristics of presented characters that
affect how rapidly , easily , and accurately one character can
be distinguished from another . The greater the speed,
ease, and accuracy of perception, the more legible the
presented characters. Examples of characteristics that
affect legibility are character shape, spacing, and
composition.
LID. See local identifier .
ligature. A single glyph representing two or more
characters. Examples of characters that can be presented
as ligatures are ff and ffi.
linear gradient. In GOCA, a gradient where the color
change takes place along a line. Contrast with radial
gradient.
line art. An image that contains only black and white with
no shades of gray .
line attributes. Those attributes that pertain to straight
and curved lines. Examples of line attributes are line type
and line width.
line data. Unformatted text data. Line data can be
formatted using a Page Definition (PageDef).
line screen frequency . The measure of distance
between the rows of dots that make up a halftone screen.
Lower line screens are used on rougher , low quality
printing substrates (such as newsprint), while higher line
screens are used for high quality print jobs on smooth art
papers.
lines per inch (lpi). (1) The number of lines per inch on a
halftone screen. (2) Units used when measuring line
screen frequency .
line type. A line attribute that controls the appearance of
a line. The line type can either be a standard line type
value or a custom line type value. Contrast with line width.
line width. A line attribute that controls the appearance of
a line. Examples of line width are normal and thick.
Contrast with line type.
link. A logical connection from a source document
component to a target document component.
little endian. A bit or byte ordering where the right-most
bits or bytes (those with a higher address) are most
significant. Contrast with big endian.
Loaded-Font command set. In the IPDS architecture, a
collection of commands used to load font information into a
printer and to deactivate font resources.
local area network (LAN). A data network located on a
user's premises in which serial transmission is used for
direct data communication among data stations.
Kanji • local area network (LAN)

<!-- Page 223 -->

PTOCA Reference 205
Local Character Set Identifier (LCID). A local identifier
used as a character , marker , or pattern set attribute.
local identifier (LID). An identifier that is mapped by the
controlling environment to a named resource.
location. A site within a data stream. A location is
specified in terms of an offset in the number of structured
fields from the beginning of a data stream, or in the number
of bytes from another location within the data stream.
logical page. A presentation space. One or more object
areas can be mapped to a logical page. A logical page has
specifiable characteristics, such as size, shape,
orientation, and offset. The shape of a logical page is the
shape of a rectangle. Orientation and offset are specified
relative to a medium coordinate system.
logical unit. A unit of linear measurement expressed with
a unit base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following logical units
are used:
• 1 logical unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 logical unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with L unit.
look-up table (LUT). (1) A table used to map one or more
input values to one or more output values. (2) A logical list
of colors or intensities. The list has a name and can be
referenced to select a color or intensity . See also color
table.
lossless. A form of image transformation in which all of
the data is retained. Contrast with lossy .
lossy . A form of image transformation in which some of
the data is lost. Contrast with lossless.
lowercase. Pertaining to small letters as distinguished
from capital letters. Examples of small letters are a, b, and
g. Contrast with uppercase.
lpi. See lines per inch.
L unit. A unit of linear measurement expressed with a unit
base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following L units are
used:
• 1 L unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 L unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with logical unit.
LUT . See look-up table.
Luv color space. The CIE color space in which L*, u* and
v* are plotted at right angles to one another . Equal
distances in the space represent approximately equal color
dif ference.
M
magnetic ink character recognition
(MICR). Recognition of characters printed with ink that
contains particles of a magnetic material.
mainframe interactive (MFI). Pertaining to systems in
which nonprogrammable terminals are connected to a
mainframe.
mandatory support level. Within the base-and-towers
concept, the smallest portion of architected function that is
allowed to be implemented. This is represented by a base
with no towers. Synonymous with base support level.
marker . A symbol with a recognizable appearance that is
used to identify a particular location. An example of a
marker is a symbol that is positioned by the center point of
its cell.
marker attributes. The characteristics that control the
appearance of a marker . Examples of marker attributes are
size and color .
marker cell. A conceptual rectangular box that can
include a marker symbol and the space surrounding that
symbol.
marker precision. A method used to specify the degree
of influence that marker attributes have on the appearance
of a marker; this method has been made obsolete.
marker set. In GOCA, an attribute used to access a
coded font.
marker symbol. A symbol that is used for a marker .
maximum ascender height. The maximum of the
individual character ascender heights. A value for
maximum ascender height is specified for each supported
character rotation. Contrast with maximum descender
depth.
maximum baseline extent. In FOCA, the sum of the
maximum of the individual character baseline offsets and
the maximum of the individual character descender depths,
for a given font.
maximum descender depth. The maximum of the
individual character descender depths. A value for
maximum descender depth is specified for each supported
character rotation. Contrast with maximum ascender
height.
Local Character Set Identifier (LCID) • maximum descender depth

<!-- Page 224 -->

206 PTOCA Reference
meaning. A table heading for architecture syntax. The
entries under this heading convey the meaning or purpose
of a construct. A meaning entry can be a long name, a
description, or a brief statement of function.
measurement base. A base unit of measure from which
other units of measure are derived.
media. Plural of medium. See also medium.
media destination. The destination to which sheets are
sent as the last step in the print process. Some printers
support several media destinations to allow options such
as print job distribution to one or more specific
destinations, collated copies without having to resend the
document to the printer multiple times, and routing output
to a specific destination for security reasons. Contrast with
media source.
media-relative colorimetric. This rendering intent
rescales the in-gamut, chromatically-adapted tristimulus
values such that the white point of the actual medium is
mapped to the PCS white point (for either input or output).
It is useful for colors that have already been mapped to a
medium with a smaller gamut than the reference medium
(and therefore need no further compression).
media source. The source from which sheets are
obtained for printing. Some printers support several media
sources so that media with different characteristics (such
as size, color , and type) can be selected when desired.
Contrast with media destination.
medium. A two-dimensional conceptual space with a
base coordinate system from which all other coordinate
systems are either directly or indirectly derived. A medium
is mapped onto a physical medium in a presentation-
system-dependent manner . Synonymous with medium
presentation space. See also logical page, physical
medium, and presentation space.
Medium Map. A print control object in a Form Map that
defines resource mappings and controls modifications to a
form, page placement on a form, and form copy
generation. See also Form Map.
medium preprinted form overlay (M-PFO). In MO:DCA,
a PFO that is designed to simulate a preprinted form for a
sheet-side. An M-PFO is invoked with the MMC structured
field and is applied last to the medium presentation space
after all other data for the sheet-side has been applied.
medium presentation space. A two-dimensional
conceptual space with a base coordinate system from
which all other coordinate systems are either directly or
indirectly derived. A medium presentation space is mapped
onto a physical medium in a presentation-system-
dependent manner . Synonymous with medium. See also
logical page, physical medium, and presentation space.
metadata. Descriptive information that is associated with
and augments other data.
Metadata command set. In the IPDS architecture, a
collection of commands used to associate MOCA data with
objects.
metadata object. In AFP , the resource object that carries
metadata.
Metadata Object Content Architecture (MOCA). A
resource object architecture to carry metadata that serves
to provide context or additional information about an AFP
object or other AFP data.
MFI. See mainframe interactive.
MICR. See magnetic ink character recognition.
Microfilm frame. A rectangular area on the microfilm
bounded by imaginary , intersecting grid lines within which a
data frame may be recorded. The grid lines are part of
gauges used for checking microfilm, but they do not
actually appear on the microfilm.
mil. 1/1000 inch.
mix. A method used to determine how the color of a
graphics primitive is combined with the existing color of a
graphics presentation space. See also foreground mix and
background mix.
Mixed Object Document Content Architecture
(MO:DCA). An architected, presentation-system-
independent data stream for interchanging documents.
mixing. (1) Combining foreground and background of one
presentation space with foreground and background of
another presentation space in areas where the
presentation spaces intersect. (2) Combining foreground
and background of multiple intersecting object data
elements in the object presentation space.
mixing rule. A method for specifying the color attributes
of the resulting foreground and background in areas where
two presentation spaces intersect.
M/O. A table heading for architecture syntax. The entries
under this heading indicate whether the construct is
mandatory (M) or optional (O).
MOCA. See Metadata Object Content Architecture.
MO:DCA. See Mixed Object Document Content
Architecture.
MO:DCA GA. A MO:DCA function set that supports levels
of PDF used in graphic arts printing.
MO:DCA IS/1. MO:DCA Interchange Set 1. A subset of
MO:DCA that defines an interchange format for
presentation documents.
meaning • MO:DCA IS/1

<!-- Page 225 -->

PTOCA Reference 207
MO:DCA IS/2. MO:DCA Interchange Set 2. A retired
subset of MO:DCA that defines an interchange format for
presentation documents.
MO:DCA IS/3. MO:DCA Interchange Set 3. A subset of
MO:DCA that defines an interchange format for print files
that supersedes MO:DCA IS/1.
MO:DCA-L. A MO:DCA subset that defines the OS/2
Presentation Manager (PM) metafile. This format is also
known as a .met file. The definition of this MO:DCA subset
is stabilized and is no longer being developed as part of the
MO:DCA architecture. It is defined in the document
MO:DCA-L: The OS/2 Presentation Manager Metafile
(.met) Format, available at www.afpconsortium.org.
MO:DCA-P . A subset of the MO:DCA architecture that
defines presentation documents. This term is now
synonymous with the term MO:DCA.
model space. A two-dimensional conceptual space in
which a picture is constructed. All model transforms are
completed before a picture is constructed in a graphics
model space. Contrast with graphics presentation space.
Synonymous with graphics model space.
model transform. A transform that is applied to drawing-
order coordinates. Contrast with viewing transform.
module. In a bar code symbology , the nominal width of
the smallest element of a bar or space. Actual bar code
symbology bars and spaces can be a single module wide
or some multiple of the module width. The multiple need
not be an integer .
modulo-N check. A check in which an operand is divided
by a modulus to generate a remainder that is retained and
later used for checking. An example of an operand is the
sum of a set of digits. See also modulus.
modulus. In a modulo check, the number by which an
operand is divided. An example of an operand is the sum
of a set of digits. See also modulo-N check.
monochrome. A single color . Monochrome usually refers
to a black-and-white image. Also referred to as line art or
bitmap mode in Adobe Photoshop
®
. See also bilevel.
monospaced font. A font with graphic characters having
a uniform character increment. The distance between
reference points of adjacent graphic characters is constant
in the escapement direction. The blank space between the
graphic characters can vary . Synonymous with uniformly
spaced font. Contrast with proportionally spaced font and
typographic font.
move order . A drawing order that specifies or implies
movement from the current position to a given position.
See also current position and given position.
M-PFO. See medium preprinted form overlay (M-PFO).
multilevel. Having multiple levels; for example, every
point in a multilevel image can have values from 0 to n,
where n is greater than 1. Contrast with bilevel.
multilevel device. A device that is used in a manner that
permits it to process color data of more than two levels.
Contrast with bilevel device.
N
NACK. See Negative Acknowledge Reply .
name. A table heading for architecture syntax. The
entries under this heading are short names that give a
general indication of the contents of the construct.
named color . A color that is specified with a descriptive
name. An example of a named color is “green”.
navigation. The traversing of a document based on links
between contextually related document components.
navigation link. A link type that specifies the linkage from
a source document component to a contextually related
target document component. Navigation links can be used
to support applications such as hypertext and hypermedia.
nColor color space. The color model used in IOCA
images that contain color components that typically do not
match any of the standard AFP color spaces, such as RGB
and CMYK. Often such images contain more than four
color components, although the number of color
components can be anywhere from two to fifteen, inclusive.
Negative Acknowledge Reply (NACK). In the IPDS
architecture, a reply from a printer to a host, indicating that
an exception has occurred. Contrast with Positive
Acknowledge Reply .
neighborhood-operation halftone. Halftone algorithm
that transfers the quantization error due to thresholding to
the unhalftoned neighbors of the current pixel. Error
dif fusion is a neighborhood operation since it operates not
only on the input pixel, but also its neighbors. Contrast with
point-operation halftone.
nested resource. A resource that is invoked within
another resource using either an Include command or a
local ID. See also nesting resource.
nesting coordinate space. A coordinate space that
contains another coordinate space. Examples of
coordinate spaces are medium, overlay , page, and object
area.
nesting resource. A resource that invokes nested
resources. See also nested resource.
neutral white. A color attribute that gives a presentation-
system-dependent default color , typically white on a screen
MO:DCA IS/2 • neutral white

<!-- Page 226 -->

208 PTOCA Reference
and black on a printer . Note that neutral white and color of
medium are two different colors.
non-presentation object. An object that is not a
presentation object.
nonprocess runout (NPRO). An operation that moves
sheets of physical media through the printer without
printing on them. This operation is used to stack the last
printed sheet.
no operation (NOP). A construct whose execution
causes a product to proceed to the next instruction to be
processed without taking any other action.
NOP . See no operation.
normal-duplex printing. Duplex printing that simulates
the effect of physically turning the sheet around the Y
m
axis.
NPRO. See nonprocess runout.
N-up. The partitioning of a side of a sheet into a fixed
number of equal size partitions. For example, 4-up divides
each side of a sheet into four equal partitions.
O
object. (1) A collection of structured fields. The first
structured field provides a begin-object function, and the
last structured field provides an end-object function. The
object can contain one or more other structured fields
whose content consists of one or more data elements of a
particular data type. An object can be assigned a name,
that can be used to reference the object. Examples of
objects are presentation text, font, graphics, and image
objects. (2) Something that a user works with to perform a
task.
object area. A rectangular area in a presentation space
into which a data object is mapped. The presentation
space can be for a page or an overlay . Examples are a
graphics object area, an image object area, and a bar code
object area.
object data. A collection of related data elements
bundled together . Examples of object data include graphic
characters, image data elements, and drawing orders.
object identifier (OID). (1) A notation that assigns a
globally unambiguous name to an object or a document
component. The notation is defined in international
standard ISO/IEC 8824(E). (2) A variable length (2-bytes
long to 129-bytes long) binary ID that uniquely identifies an
object. OIDs use the ASN.1 definite-short-form object
identifier format defined in the ISO/IEC 8824:1990(E)
international standard and described in the MO:DCA
Registry Appendix of the Mixed Object Document Content
Architecture Reference. An OID consists of a one-byte
identifier (X'06'), followed by a one-byte length (between
X'00' and X'7F'), followed by 0–127 content bytes.
obsolete. Removed from the architecture, and thus
ignored by receivers.
OCR A. See Optical Character Recognition A.
OCR B. See Optical Character Recognition B.
offline. A device state in which the device is not under the
direct control of a host. Contrast with online.
offset. A table heading for architecture syntax. The
entries under this heading indicate the numeric
displacement into a construct. The offset is measured in
bytes and starts with byte zero. Individual bits can be
expressed as displacements within bytes.
OID. See object identifier .
online. A device state in which the device is under the
direct control of a host. Contrast with offli ne.
opacity . In bar codes, the optical property of a substrate
material that minimizes showing through from the back
side or the next sheet.
Optical Character Recognition A (OCR A). A font
containing the character set in ANSI standard X3.17-1981,
that contains characters that are both human readable and
machine readable.
Optical Character Recognition B (OCR B). A font
containing the character set in ANSI standard X3.49-1975,
that contains characters that are both human readable and
machine readable.
order . (1) In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify , for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Orders can be
included in a structured field. Synonymous with drawing
order . (2) In the IPDS architecture, a construct within an
execute-order command. (3) In IOCA, a functional
operation that is performed on the image content.
ordered page. In the IPDS architecture, a logical page
that does not contain any page segments or overlays, and
in which all text data and all image, graphics, and bar code
objects are ordered. The order of the data objects is such
that physical pel locations on the physical medium are
accessed by the printer in a sequential left-to-right and top-
to-bottom manner , where these directions are relative to
the top edge of the physical medium. Once a physical pel
location has been accessed by the printer , the page data
does not require the printer to access that same physical
pel location again.
non-presentation object • ordered page

<!-- Page 227 -->

PTOCA Reference 209
orientation. The angular distance a presentation space
or object area is rotated in a specified coordinate system,
expressed in degrees and minutes. For example, the
orientation of printing on a physical medium, relative to the
X
m
axis of the X
m
,Y
m
coordinate system. See also
presentation space orientation and text orientation.
origin. The point in a coordinate system where the axes
intersect. Examples of origins are the addressable position
in an X
m
,Y
m
coordinate system where both coordinate
values are zero and the character reference point in a
character coordinate system.
orthogonal. Intersecting at right angles. An example of
orthogonal is the positional relationship between the axes
of a Cartesian coordinate system.
outline font. A shape technology in which the graphic
character shapes are represented in digital form by a
series of mathematical expressions that define the outer
edges of the strokes. The resultant graphic character
shapes can be either solid or hollow .
output profile. An ICC profile that describes the
characteristics of the output device for which the image is
destined. The profile is used to color match the image to
the device's gamut.
overhead. In a bar code symbology , the fixed number of
characters required for starting, stopping, and checking a
bar code symbol.
overlay . (1) A resource object that contains presentation
data such as, text, image, graphics, and bar code data.
Overlays define their own environment and are often used
as pre-defined pages or electronic forms. Overlays are
classified according to how they are presented with other
presentation data: a medium overlay is positioned at the
origin of the medium presentation space before any pages
are presented, and a page overlay is positioned at a
specified point in a page's logical page. A Page
Modification Control (PMC) overlay is a special type of
page overlay used in MO:DCA environments. (2) The final
representation of such an object on a physical medium.
Contrast with page segment.
Overlay command set. In the IPDS architecture, a
collection of commands used to load, deactivate, and
include overlays.
overlay ID. A one-byte ID assigned by a host to an
overlay . Overlay IDs are used in IPDS Begin Overlay ,
Deactivate Overlay , Include Overlay , and Load Copy
Control commands.
overlay state. An operating state that allows overlay data
to be downloaded to a product. For example, a printer
enters overlay state from home state when the printer
receives an IPDS Begin Overlay command.
overpaint. A mixing rule in which the intersection of part
of a new presentation space P
new
with an existing
presentation space P
existing
keeps the color attribute of P
new
.
This is also referred to as “opaque” mixing. See also mixing
rule. Contrast with blend and underpaint.
overscore. A line parallel to the baseline and placed
above the character .
overstrike. In PTOCA, the presentation of a designated
character as a string of characters in a specified text field.
The intended effect is to make the resulting presentation
appear as though the text field, whether filled with
characters or blanks, has been marked out with the
overstriking character .
overstriking. The method used to merge two or more
graphic characters at the same addressable position in a
presentation space or on a physical medium.
P
page. (1) A data stream object delimited by a Begin Page
structured field and an End Page structured field. A page
can contain presentation data such as text, image,
graphics, and bar code data. (2) The final representation
of a page object on a physical medium.
page counter . Bytes in an IPDS Acknowledge Reply that
specify the number of pages that have passed a particular
point in a logical paper path.
PageDef. See Page Definition.
Page Definition (PageDef). A print control object used to
format line data into page data. A Page Definition contains
one or more Data Maps and may optionally specify
conditional processing of the line data. Synonymous with
Page Map. See also Data Map.
Page Format. Synonymous with Data Map.
page group. A named group of sequential pages. A page
group is delimited by a Begin Named Page Group
structured field and an End Named Page Group structured
field. A page group can contain nested page groups. All
pages in the page group inherit the attributes and
processing characteristics that are assigned to the page
group.
Page Map. A print control object used to format line data
into page data. A Page Map contains one or more Data
Maps and may optionally specify conditional processing of
the line data. Synonymous with Page Definition. See also
Data Map.
page segment. (1) In the IPDS architecture, a resource
object that can contain text, image, graphics, and bar code
data. Page segments do not define their own environment,
but are processed in the existing environment. (2) In
MO:DCA, a resource object that can contain any mixture of
bar code objects, graphics objects, and IOCA image
objects. A page segment does not contain an active
orientation • page segment

<!-- Page 228 -->

210 PTOCA Reference
environment group. The environment for a page segment
is defined by the active environment group of the including
page or overlay . (3) The final representation of such an
object on a physical medium. Contrast with overlay .
Page-Segment command set. In the IPDS architecture,
a collection of commands used to load, deactivate, and
include page segments.
page-segment state. An operating state that makes
page-segment data available to a product. For example, a
printer enters page-segment state from home state when it
receives an IPDS Begin Page Segment command.
page state. In the IPDS architecture, an operating state
that makes page data available to a product. For example,
a printer enters page state from home state when it
receives an IPDS Begin Page command.
paginated object. A data object that can be rendered on
a single page or overlay . An example of a paginated object
is a single image in a multi-image TIFF file.
palette. The collection of colors or shades available to a
graphics system or program.
P ANT ONE
®
. The proprietary P ANT ONE color matching
system is the most popular method of specifying extra
colors—not out of the CMYK four color process—for print.
P ANTONE colors are numbered and mixed from a base set
of colors. By specifying a specific P ANTONE color , a
designer knows that there is little chance of color variance
on the presses.
parameter . (1) A variable that is given a constant value
for a specified application. (2) A variable used in
conjunction with a command to affect its result.
partition. Dividing the medium presentation space into a
specified number of equal-sized areas in a manner
determined by the current physical media.
partitioning. A method used to place parts of a control
into two or more segments or structured fields. Partitioning
can cause difficulties for a receiver if one of the segments
or structured fields is not received or is received out of
order .
pattern. An array of symbols used to fill an area.
pattern attributes. The characteristics that specify the
appearance of a pattern.
pattern reference point. In GOCA, a position in the
graphics presentation space to be used as the origin of a
custom pattern; the pattern is tiled in all directions from this
position.
pattern set. An attribute in GOCA used to access a
symbol set or coded font.
pattern symbol. The geometric construct that is used
repetitively to generate a pattern. Examples of pattern
symbols are dots, squares, and triangles.
PCL
®
. A set of printer commands, developed by Hewlett-
Packard
®
, that provide access to printer features.
PCS. (1) See Print Contrast Signal. (2) See Profile
Connection Space.
PDF . An acronym for Acrobat
®
Portable Document
Format. PDF files are cross platform and contain all of the
image and font data. Design attributes are retained in a
compressed single package.
pel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity . Pels per inch is often used as
a measurement of presentation granularity . Synonymous
with picture element and pixel.
perceptual rendering intent. The exact gamut mapping
of the perceptual rendering intent is vendor specific and
involves compromises such as trading off preservation of
contrast in order to preserve detail throughout the tonal
range. It is useful for general reproduction of images,
particularly pictorial or photographic-type images.
PFB file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for small character set fonts having one to two hundred
characters (for example, English, Greek, and Cyrillic).
PFO. See preprinted form overlay (PFO).
physical file. A single operating system file intended for
presentation. The format of the file, and its delineation, is
defined by the operating system.
physical medium. A physical entity on which information
is presented. Examples of a physical medium are a sheet
of paper , a roll of paper , an envelope, and a display screen.
See also medium presentation space and sheet.
physical printable area. A bounded area defined on a
side of a sheet within which printing can take place. The
physical printable area is an attribute of sheet size and
printer capabilities, and cannot be altered by the host. The
physical printable area is mapped to the medium
presentation space, and is used in user printable area and
valid printable area calculations. Contrast with user
printable area and valid printable area.
picket fence bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the X
bc
axis of the bar code presentation space. Synonymous with
horizontal bar code.
Page-Segment command set • picket fence bar code

<!-- Page 229 -->

PTOCA Reference 21 1
picture chain. A string of segments that defines a picture.
Synonymous with segment chain.
picture element. The smallest printable or displayable
unit on a physical medium. In computer graphics, the
smallest element of a physical medium that can be
independently assigned color and intensity . Picture
elements per inch is often used as a measurement of
presentation granularity . Synonymous with pel and pixel.
pixel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity . Picture elements per inch is
often used as a measurement of presentation granularity .
Synonymous with pel and picture element.
PMC-PFO. See PMC preprinted form overlay (PMC-
PFO).
PMC preprinted form overlay (PMC-PFO). In MO:DCA,
a PFO that is designed to simulate a preprinted form for a
page. A PMC-PFO is invoked with the PMC structured field
and is applied last to the page presentation space after all
other data for the page has been applied.
PNG. See Portable Network Graphics.
point. (1) A unit of measure used mainly for measuring
typographical material. There are seventy-two points to an
inch. (2) In GOCA, a parameter that specifies the position
within the drawing order coordinate space. See also
drawing order coordinate space.
point-operation halftone. Any halftone algorithm that
produces output for a given location based only on the
single input pixel at that location, independent of its
neighbors. Thus, it is accomplished by a simple point-wise
comparison of the input image against a predetermined
threshold array or mask. Contrast with neighborhood-
operation halftone.
polyline. A sequence of connected lines.
Portable Network Graphics (PNG). A lossless image
format.
portrait. A presentation orientation in which the X
m
axis is
parallel to the short sides of a rectangular physical
medium. Contrast with landscape.
position. A position in a presentation space or on a
physical medium that can be identified by a coordinate
from the coordinate system of the presentation space or
physical medium. See also picture element. Synonymous
with addressable position.
Positive Acknowledge Reply (ACK). In the IPDS
architecture, a reply to an IPDS command that has its
acknowledgment-required flag on and in which no
exception is reported. Contrast with Negative Acknowledge
Reply .
PostScript. A page description programming language
created by Adobe Systems Inc. that is a presentation-
system-independent industry standard for outputting
documents and graphics. It describes pages to any output
device with a PostScript interpreter .
posture. Inclination of a letter with respect to a vertical
axis. Examples of inclination are upright and inclined. An
example of upright is Roman. An example of inclined is
italics.
pragmatics. Information related to the usage of a
construct. See also semantics and syntax.
preprinted form. A form or sheet that is not blank when it
is selected as input media for presentation.
preprinted form overlay (PFO). An overlay and
associated processing designed to simulate a preprinted
form.
presentation data stream. A presentation data stream
that is processed in AFP environments. The MO:DCA
architecture describes the AFP interchange data stream.
The IPDS architecture describes the AFP printer data
stream.
presentation device. A device that produces character
shapes, graphics pictures, images, or bar code symbols on
a physical medium. Examples of a physical medium are a
display screen and a sheet of paper .
presentation object. An object that describes
presentation data such as text, image, and graphics, in a
paginated, final-form format suitable for presentation on a
page. Contrast with non-presentation object.
presentation position. An addressable position that is
coincident with a character reference point. See also
addressable position and character reference point.
presentation process. Synonymous with presentation
system.
presentation services. In printing, a software component
that communicates with a printer using a printer data
stream, such as the IPDS data stream, to print pages,
download and manage print resources, and handle
exceptions.
presentation space. A conceptual address space with a
specified coordinate system and a set of addressable
positions. The coordinate system and addressable
positions can coincide with those of a physical medium.
Examples of presentation spaces are medium, logical
page, and object area. See also graphics presentation
space, image presentation space, logical page, medium
presentation space, and text presentation space.
presentation space orientation. The number of degrees
and minutes a presentation space is rotated in a specified
coordinate system. For example, the orientation of printing
picture chain • presentation space orientation

<!-- Page 230 -->

212 PTOCA Reference
on a physical medium, relative to the X
m
axis of the X
m
,Y
m
coordinate system. See also orientation and text
orientation.
presentation system. A system for presenting data. In
AFP environments such a system normally contains at
least a formatting application, a print server , and a printer .
Synonymous with presentation process.
presentation text object. An object that contains
presentation text data. See also object.
Presentation Text Object Content Architecture
(PTOCA). An architected collection of constructs used to
interchange and present presentation text data.
print contrast. A measurement of the ratio of the
reflectivities between the bars and spaces of a bar code
symbol, commonly expressed as a percent. Synonymous
with Print Contrast Signal.
Print Contrast Signal (PCS). A measurement of the ratio
of the reflectivities between the bars and spaces of a bar
code symbol, commonly expressed as a percent.
Synonymous with print contrast.
print control object. A resource object that contains
layout, finishing, and resource mapping information used to
present a document on physical media. Examples of print
control objects are Form Maps and Medium Maps.
print direction. In FOCA, the direction in which
successive characters appear in a line of text.
print file. A file that is created for the purpose of printing
data. The print file is the highest level of the MO:DCA data-
stream document-component hierarchy .
printing baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with baseline and sequential
baseline.
print quality . In bar codes, the measure of compliance of
a bar code symbol to the requirements of dimensional
tolerance, edge roughness, spots, voids, reflectance, PCS,
and quiet zones defined within a bar code symbology .
print unit. In the IPDS architecture, a group of pages
bounded by XOH-DGBcommands and subject to the
group operation keep group together as a print unit. A print
unit is commonly referred to as a print job.
process color . A color that is specified as a combination
of the components, or primaries, of a color space. A
process color is rendered by mixing the specified amounts
of the primaries. An example of a process color is C=0.1,
M=0.8, Y=0.2, K=0.1 in the cyan/magenta/yellow/black
(CMYK) color space. Contrast with spot color .
process element. In MO:DCA, a document component
that is defined by a structured field and that facilitates a
form of document processing that does not affect the
presentation of the document. Examples of process
elements are T ag Logical Elements (TLEs) that specify
document attributes and Link Logical Elements (LLEs) that
specify linkages between document components.
Profile Connection Space (PCS). The reference color
space defined by ICC, in which colors are encoded in order
to provide an interface for connecting source and
destination transforms. The PCS is based on the CIE 1931
standard colorimetric observer .
prolog. The first portion of a segment's data. Prologs are
optional. They contain attribute settings and drawing
controls. Synonymous with segment prolog.
propagation. A method used to retain a segment's
properties through other segments that it calls.
proper subset. A set whose members are also members
of a larger set.
proportion. Relationship of the width of a letter to its
height.
proportionally spaced font. A font with graphic
characters that have varying character increments.
Proportional spacing can be used to provide the
appearance of even spacing between presented
characters and to eliminate excess blank space around
narrow characters. An example of a narrow character is the
letter i. Synonymous with typographic font. Contrast with
monospaced font and uniformly spaced font.
proportional spacing. The spacing of characters in a
printed line so that each character is allotted a space
based on the character's width.
Proportional Spacing Machine font (PSM font). A font
originating with the electric typewriter and having character
increment values that are integer multiples of the narrowest
character width.
PSM font. See Proportional Spacing Machine font.
PTOCA. See Presentation Text Object Content
Architecture.
Q
quantization. The process of reducing an image with
many colors to one with fewer colors, usually in preparation
for its conversion to a palette-based image. As a result,
most parts of the image (that is, most of its pixels) are
given slightly different colors that amounts to a certain level
of error at each location. Since photographic images
usually have extended regions of similar colors that get
converted to the same quantized color , a quantized image
tends to have a flat or banded (contoured) appearance
unless it is also dithered.
presentation system • quantization

<!-- Page 231 -->

PTOCA Reference 213
quiet zone. A clear space that contains no machine-
readable marks preceding the start character of a bar code
symbol or following the stop character . Synonymous with
clear area. Contrast with intercharacter gap and space.
R
radial gradient. In GOCA, a gradient where the color
change takes place between two full arcs. Contrast with
linear gradient.
range. A table heading for architecture syntax. The
entries under this heading give numeric ranges applicable
to a construct. The ranges can be expressed in binary ,
decimal, or hexadecimal. The range can consist of a single
value.
raster . (1) The area of the video display that is covered by
sweeping the electron beam of the display horizontally and
vertically . Normally the electronics of the display sweep
each line horizontally from top to bottom and return to the
top during the vertical retrace interval. (2) In computer
graphics, a predetermined pattern of lines that provides
uniform coverage of a display space. (3) In nonimpact
printers, an on-or-off pattern of electrostatic images
produced by the laser print head under control of the
character generator .
raster direction. An attribute that controls the direction in
which a character string grows relative to the inline
direction. V alues are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top.
rasterize. T o convert presentation data into raster
(bitmap) form for display or printing.
raster pattern. A rectangular array of pels arranged in
rows called scan lines.
readability . The characteristics of visual material that
determine the degree of comfort with which it can be read
over a sustained period of time. Examples of
characteristics that influence readability are type quality ,
spacing, and composition.
reader . In bar code systems, the scanner or combination
of scanner and decoder . See also decoder and scanner .
read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with first read rate.
rearranged file. A file containing the mapping of code
points to the character index values used in a CID file and
to the character names used in one or more PFB files. This
is a special case of the CMAP file that permits linking of
multiple font files and formats together . The code points
conform to a particular character coding system that is
used to identify the characters in a document data stream.
The mapping information in this file is in an ASCII file
format defined by Adobe Systems Inc.
record-format line data. A form of line data where each
record is preceded by a 10-byte identifier . The record is
presented by matching its ID to the ID specified on a
Record Descriptor in the Data Map of a Page Definition.
recording algorithm. An algorithm that determines the
relationship between the physical location and logical
location of image points in image data.
recovery-unit group. (1) In the IPDS architecture, a
group of pages identified by the XOH Define Group
Boundary command and controlled by the Keep-Group-
T ogether-as-a-Recovery-Unit group operation specified by
the XOH Specify Group Operation command. The
recovery-unit group also includes all copies specified by
the Load Copy Control command. (2) In the MO:DCA
architecture, a group of pages identified as a unit for error
recovery purposes, such as in cases of a printer recovery
from an error that occurs in the middle of the group. A
recovery-unit group is identified by a Begin Named Group
(BNG) and End Named Group (ENG) pair that contains a
Keep Group T ogether (X'9D') triplet.
redaction. The process of applying an opaque mask over
a page so that a selected portion of the page is visible.
Since this function is typically used to prevent unauthorized
viewing of data, an associated security level is also
provided.
reflectance. In bar codes, the ratio of the amount of light
of a specified wavelength or series of wavelengths
reflected from a test surface to the amount of light reflected
from a barium oxide or magnesium oxide standard under
similar illumination conditions.
relative coordinate. One of the coordinates that identify
the location of an addressable point by means of a
displacement from some other addressable point. Contrast
with absolute coordinate.
relative line. A straight line developed from a specified
point by a given displacement.
relative metrics. Graphic character measurements
expressed as fractions of a square, called the Em square,
whose sides correspond to the vertical size of the font.
Because the measurements are relative to the size of the
Em square, the same metrics can be used for different
point sizes and different raster pattern resolutions. Relative
metrics require defining the unit of measure for the Em
square, the point size of the font, and, if applicable, the
resolution of the raster pattern.
relative move. A method used to establish a new current
position. Distance and direction from the current position
are used to establish the new current position. The
direction of displacement is inline along the I axis in the I
direction, or baseline along the B axis in the B direction, or
both.
quiet zone • relative move

<!-- Page 232 -->

214 PTOCA Reference
relative positioning. The establishment of a position
within a coordinate system as an offset from the current
position. Contrast with absolute positioning.
rendering intent. A particular gamut-mapping style or
method of converting colors in one gamut to colors in
another gamut. ICC profiles support four different
rendering intents: perceptual, media-relative colorimetric,
saturation, and ICC-absolute colorimetric.
repeating group. A group of parameter specifications
that can be repeated.
repeat string. A method used to repeat the character
content of text data until a given number of characters has
been processed. Any control sequences in the text data
are ignored. This method provides the functional
equivalence of a T ransparent Data control sequence when
the given number of repeated characters is equal to the
number of characters in the text data.
reserved. Having no assigned meaning and put aside for
future use. The content of reserved fields is not used by
receivers, and should be set by generators to a specified
value, if given, or to binary zeros. A reserved field or value
can be assigned a meaning by an architecture at any time.
reset color . The color of a presentation space before any
data is added to it. Synonymous with color of medium.
resident resource. In the IPDS architecture, a resource
in a printer or in a resource-caching intermediate device. A
resident resource can be installed manually or can be
captured by the device if it is intended for public use. A
resident resource is referenced by a global ID that is valid
for the duration of the resource's presence in the device.
Contrast with downloaded resource.
resolution. (1) A measure of the sharpness of an input or
output device capability , as given by some measure
relative to the distance between two points or lines that can
just be distinguished. (2) The number of addressable pels
per unit of length.
resolution correction. A method used to present an
image on a printer without changing the physical size or
proportions of the image when the resolutions of the printer
and the image are different.
resolution-correction ratio. The ratio of a device
resolution to an image presentation space resolution.
resolution modification. A method used to write an
image on an image presentation space without changing
the physical size of the image when the resolutions of the
presentation space and the image are different.
resource. An object that is referenced by a data stream
or by another object to provide data or information.
Resource objects can be stored in libraries. In MO:DCA,
resource objects can be contained within a resource group.
Examples of resources are fonts, overlays, and page
segments. See also downloaded resource, resident
resource, and secondary resource.
resource caching. In the IPDS architecture, a function in
a printer or intermediate device whereby downloaded
resources are captured and made resident in the printer or
intermediate device.
retired. Set aside for a particular purpose, and not
available for any other purpose. Retired fields and values
are specified for compatibility with existing products and
identify one of the following:
• Fields or values that have been used by a product in a
manner not compliant with the architected definition
• Fields or values that have been removed from an
architecture
reuse LND. A Line Descriptor (LND) in a chain of LNDs,
also called a reuse chain, where all LNDs process fields in
the same line-data record. See also base LND.
RGB. Red, green and blue, the additive primary colors.
RGBcolor space. The basic additive color model used
for color video display , as on a computer monitor .
RIP . A raster image processor (RIP) is a hardware or
software tool that processes a presentation data stream
and converts it—rasterizes it—to a printable format.
RM4SCC. See Royal Mail 4 State Customer Code.
Roman. Relating to a type style with upright letters.
root segment. A segment in the picture chain that is not
called by any other segment. If a single segment that is not
in a segment chain is drawn, it is treated as a root segment
for the duration of the drawing process.
rotating. In computer graphics, turning all or part of a
picture about an axis perpendicular to the presentation
space.
rotation. The orientation of a presentation space with
respect to the coordinate system of a containing
presentation space. Rotation is measured in degrees in a
clockwise direction. Zero-degree rotation exists when the
angle between a presentation space's positive X axis and
the containing presentation space's positive X axis is zero
degrees. Contrast with character rotation.
row . A subarray that consists of all elements that have an
identical position within the high dimension of a regular
two-dimensional array .
Royal Mail 4 State Customer Code (RM4SCC). A two-
dimensional bar code symbology developed by the United
Kingdom's Royal Mail postal service for use in automated
mail-sorting processes.
relative positioning • Royal Mail 4 State Customer Code (RM4SCC)

<!-- Page 233 -->

PTOCA Reference 215
rule. A solid line of any line width.
S
sans serif. A type style characterized by strokes that end
with no flaring or crossing of lines at the stroke ends.
Contrast with serif.
saturation rendering intent. The exact gamut mapping
of the saturation rendering intent is vendor specific and
involves compromises such as trading off preservation of
hue in order to preserve the vividness of pure colors. It is
useful for images that contain objects such as charts or
diagrams.
SBCS. See single-byte character set.
SBIN. A data type for architecture syntax, that indicates
that one or more bytes be interpreted as a signed binary
number , with the sign bit in the high-order position of the
leftmost byte. Positive numbers are represented in true
binary notation with the sign bit set to B'0'. Negative
numbers are represented in twos-complement binary
notation with a B'1' in the sign-bit position.
Scalable V ector Graphics (SVG). An XML-based vector
image format.
scaling. Making all or part of a picture smaller or larger by
multiplying the coordinate values of the picture by a
constant amount. If the same multiplier is applied along
both dimensions, the scaling is uniform, and the
proportions of the picture are unaffected. Otherwise, the
scaling is anamorphic, and the proportions of the picture
are changed. See also anamorphic scaling.
scaling ratio. (1) The ratio of an image-object-area size
to its image-presentation-space size. (2) In FOCA, the
ratio of horizontal to vertical scaling of the graphic
characters. See also horizontal scale factor .
scan line. A series of picture elements. Scan lines in
raster patterns form images. See also picture element and
raster pattern.
scanner . In bar codes, an electronic device that converts
optical information into electrical signals. See also reader .
screen. (1) A halftone-threshold array . (2) The display
surface of a display device such as a computer monitor .
scrolling. A method used to move a displayed image
vertically or horizontally so that new data appears at one
edge as old data disappears at the opposite edge. Data
disappears at the edge toward which an image is moved
and appears at the edge away from which the data is
moved.
SDA. See special data area.
secondary resource. A resource for an object that is
itself a resource.
section. A portion of a double-byte code page that
consists of 256 consecutive entries. The first byte of a two-
byte code point is the section identifier . A code-page
section is also called a code-page ward in some
environments. See also code page and code point.
section identifier . A value that identifies a section.
Synonymous with section number .
section number . A value that identifies a section.
Synonymous with section identifier .
secure overlay . An overlay that can be printed anywhere
within the physical printable area. A secure overlay is not
affected by an IPDS Define User Area command.
segment. (1) In GOCA, a set of graphics drawing orders
contained within a Begin Segment command. See also
graphics segment. (2) In IOCA, image content bracketed
by Begin Segment and End Segment self-defining fields.
See also image segment.
segment chain. A string of segments that defines a
picture. Synonymous with picture chain.
segment exception condition. An architecture-provided
classification of the errors that can occur in a segment.
Segment exception conditions are raised when a segment
error is detected. Examples of segment errors are segment
format, parameter content, and sequence errors.
segment offset. A position within a segment, measured
in bytes from the beginning of the segment. The beginning
of a segment is always at offset zero.
segment prolog. The first portion of a segment's data.
Prologs are optional. They contain attribute settings and
drawing controls. Synonymous with prolog.
segment properties. The segment characteristics used
by a drawing process. Examples of segment properties are
segment name, segment length, chained, dynamic,
highlighted, propagated, and visible.
segment transform. A model transform that is applied to
a whole segment.
self checking. In bar codes, using a checking algorithm
that can be applied to each character independently to
guard against undetected errors.
semantics. The meaning of the parameters of a
construct. See also pragmatics and syntax.
sequential baseline. A conceptual line with respect to
which successive characters are aligned. See also
character baseline. Synonymous with baseline and printing
baseline.
rule • sequential baseline

<!-- Page 234 -->

216 PTOCA Reference
sequential baseline position. The current addressable
position for a baseline in a presentation space or on a
physical medium. See also baseline coordinate and current
baseline presentation coordinate.
serif. A short line angling from or crossing the free end of
a stroke. Examples are horizontal lines at the tops and
bottoms of vertical strokes on capital letters, for example, I
and H, and the decorative strokes at the ends of the
horizontal members of a capital E. Contrast with sans serif.
server . In a network, hardware or software that provides
facilities to other stations. Examples include: a file server ,
a printer server , and a mail server .
session. In the IPDS architecture, the period of time
during which a presentation services program has a two-
way communication with an IPDS device. The session
consists of a physical attachment and a communications
protocol; the communications protocol carries an IPDS
dialog by transparently transmitting IPDS commands and
Acknowledge Replies. See also IPDS dialog.
setup file. In the IPDS architecture, an object container
that provides setup information for a printer . Setup files are
downloaded in home state and take effect immediately .
Setup files are not managed as resources.
setup name. A user-created name for a set of specific
settings on a device. There is at most one setup name
active on a device at any time, and it is allowed for there to
be no active setup name on a device.
shade. V ariation of a color produced by mixing it with
black.
shape compression. A method used to compress
digitally encoded character shapes using a specified
algorithm.
shape technology . A method used to encode character
shapes digitally using a specified algorithm.
shear . The angle of slant of a character cell that is not
perpendicular to a baseline. Synonymous with character
shear .
shearline direction. In GOCA, the direction specified by
the character shear and character angle attributes.
sheet. A division of the physical medium; multiple sheets
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper , each representing a sheet. Envelopes are an
example of a physical medium that comprises only one
sheet. The IPDS architecture defines four types of
sheets: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
sheet has a top edge. A sheet has two sides, a front side
and a back side. Synonymous with form.
show through. In bar codes, the generally undesirable
property of a substrate that permits underlying markings to
be seen.
side. A physical surface of a sheet. A sheet has a front
side and a back side. See also sheet.
signed integers. The positive natural numbers (1, 2, 3,
...), their negatives (-1, -2, -3, ...) and the number zero. The
set of all integers is usually denoted in mathematics by Z,
which stands for Zahlen (German for “numbers”).
simplex printing. A method used to print data on one
side of a sheet; the other side is left blank. Contrast with
duplex printing.
single-byte character set (SBCS). A character set that
can contain up to 256 characters.
single-byte coded font. A coded font in which the code
points are one byte long.
slope. The posture, or incline, of the main strokes in the
graphic characters of a font. Slope is specified in degrees
by a font designer .
soft object. An object that is not mapped in an
environment group and is therefore not sent to the
presentation device until it is referenced within a page or
overlay . Contrast with hard object.
space. In bar codes, the lighter element of a printed bar
code symbol, usually formed by the background between
bars. See also element. Contrast with bar , clear area,
intercharacter gap, and quiet zone.
space width. In bar codes, the thickness of a bar code
symbol space measured from the edge closest to the
symbol start character to the trailing edge of the same
space.
spanning. In the IPDS architecture, a method in which
one command is used to start a sequence of constructs.
Subsequent commands continue and terminate that
sequence. See also control sequence chaining.
special data area (SDA). The data area in an IPDS
Acknowledge Reply that contains data requested by the
host or generated by a printer as a result of an exception.
Specifications for W eb Offset Publications (SWOP). A
standard set of specifications for color separations, proofs,
and printing to ensure consistency of color printing.
spot. In bar codes, the undesirable presence of ink or dirt
in a bar code symbol space.
spot color . A color that is specified with a unique
identifier such as a number . A spot color is normally
rendered with a custom colorant instead of with a
combination of process color primaries. See also highlight
color . Contrast with process color .
sequential baseline position • spot color

<!-- Page 235 -->

PTOCA Reference 217
sRGB. One of the standard RGBcolor spaces, a means
of specifying precisely how any given RGB value should
appear on a display or printed paper or any other output
device. sRGB was promoted by the ICC and submitted for
standardization by the International Electrotechnical
Commission (IEC).
stack. A list that is constructed and maintained so that the
next item to be retrieved and removed is the most recently
stored item still in the list. This is sometimes called last-in-
first-out (LIFO).
standard action. The architecture-defined action to be
taken on detecting an exception condition, when the
controlling environment specifies that processing should
continue.
standard line type value. A predefined line type, like
solid, invisible, or dash dot. Contrast with custom line type
value.
start-stop character or pattern. In bar codes, a special
bar code character that provides the scanner with start and
stop reading instructions as well as a scanning direction
indicator . The start character is normally at the left end and
the stop character at the right end of a horizontally oriented
symbol.
stochastic. A method that uses a pseudo-random dot
size and/or frequency to create halftone images, but
without the visible regularity in the dot patterns found in
traditional screening.
store mode. A mode in which segments are stored for
later execution. Contrast with immediate mode.
stroke. A straight or curved line used to create the shape
of a letter .
structured field. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. See also
document element.
structured field introducer . In MO:DCA, the header
component of a structured field that provides information
that is common for all structured fields. Examples of
information that is common for all structured fields are
length, function type, and category type. Examples of
structured field function types are begin, end, data, and
descriptor . Examples of structured field category types are
presentation text, image, graphics, and page.
subordinate object. An object that is lower in the
document-component hierarchy than a given object. For
example, a page is a subordinate object to a page group,
and a page group is a subordinate object to a document.
subpage. A part of a logical page on which line data may
be placed. A line data record is identified as belonging to a
particular subpage with the subpage identifier byte in the
Line Descriptor (LND) structured field. Conditional
processing can be used with a Page Definition to select a
new Data Map and/or Medium Map to take effect before or
after the current subpage is printed.
subset. Within the base-and-towers concept, a portion of
architecture represented by a particular level in a tower or
by a base. See also subsetting tower .
subsetting tower . Within the base-and-towers concept, a
tower representing an aspect of function achieved by an
architecture. A tower is independent of any other towers. A
tower can be subdivided into subsets. A subset contains all
the function of any subsets below it in the tower . See also
subset.
substrate. In bar codes, the surface on which a bar code
symbol is printed.
subtractive primary colors. Cyan, magenta, and yellow
colorants used to subtract a portion of the white light that is
illuminating an object. Subtractive colors are reflective on
paper and printed media. When used together with various
degrees of coverage and variation, they have the ability to
create billions of other colors. Contrast with additive
primary colors.
suppression. A method used to prevent presentation of
specified data. Examples of suppression are the
processing of text data without placing characters on a
physical medium and the electronic equivalent of the “spot
carbon,” that prevents selected data from being presented
on certain copies of a presentation space or a physical
medium.
surrogate pair . A sequence of two Unicode code points
that allow for the encoding of as many as 1 million
additional characters without any use of escape codes.
surrogates. A way to refer to one or more surrogate
pairs.
SVG. See Scalable V ector Graphics.
SWOP . See Specifications for Web Offset Publications.
symbol. (1) A visual representation of something by
reason of relationship, association, or convention. (2) In
GOCA, the subpicture referenced as a character definition
within a font character set and used as a character , marker ,
or fill pattern. A bitmap can also be referenced as a symbol
for use as a fill pattern. See also bar code symbol.
symbol length. In bar codes, the distance between the
outside edges of the quiet zones of a bar code symbol.
symbology . A bar code language. Bar code symbologies
are defined and controlled by various industry groups and
standards organizations. Bar code symbologies are
described in public domain bar code specification
documents. Synonymous with bar code symbology . See
also Canadian Grocery Product Code (CGPC), European
sRGB • symbology

<!-- Page 236 -->

218 PTOCA Reference
Article Numbering (EAN), Japanese Article Numbering
(JAN), and Universal Product Code (UPC).
symbol set. A coded font that is usually simpler in
structure than a fully described font. Symbol sets are used
where typographic quality is not required. Examples of
devices that might not provide typographic quality are dot-
matrix printers and displays. See also character set,
marker set, and pattern set.
synchronous exception. In the IPDS architecture, a
data-stream, function no longer achievable, or resource-
storage exception that must be reported to the host before
a printer can return a Positive Acknowledge Reply or can
increment the received-page counter for a page containing
the exception. Synchronous exceptions are those with
action code X'01', X'06', X'0C', or X'1F'. See also data-
stream exception. Contrast with asynchronous exception.
syntax. The rules governing the structure of a construct.
See also pragmatics and semantics.
system-level font resource. A common-source font from
which:
• Document-processing applications can obtain resolution-
independent formatting information.
• Device-service applications can obtain device-specific
presentation information.
T
tag. A data structure that is used within the data portion of
a color management resource (CMR). A CMR tag consists
of T agID, FieldT ype, Count, and V alueOffset.
T agged Image File Format (TIFF). A rich and flexible
graphics image format.
temporary baseline. The shifted baseline used for
subscript and superscript.
temporary baseline coordinate. The B value of the I,B
coordinate pair of an addressable position on the
temporary baseline.
temporary baseline increment. A positive or negative
value that is added to the current baseline presentation
coordinate to specify the position of a temporary baseline
in a presentation space or on a physical medium. Several
increments might have been used to place a temporary
baseline at the current baseline presentation coordinate.
tertiary resource. A resource for an object that is itself a
secondary resource to another resource.
text. A graphic representation of information. T ext can
consist of alphanumeric characters and symbols arranged
in paragraphs, tables, columns, and other shapes. An
example of text is the data sent in an IPDS Wri te T ext
command.
T ext command set. In the IPDS architecture, a collection
of commands used to present PTOCA text data in a page,
page segment, or overlay .
text major . A description for text where the Presentation
T ext Data Descriptor (PTD) is specified in page controls. In
MO:DCA, the PTD is in the Active Environment Group
(AEG) for the page; in IPDS, the PTD is specified as initial
text-major conditions in the Logical Page Descriptor
command.
text object. (1) An object that contains text data. (2) A
presentation-system-independent, self-defining
representation of a two-dimensional presentation space,
called the text object space, that contains presentation text
data.
text object space. Synonymous with text presentation
space.
text orientation. A description of the appearance of text
as a combination of inline direction and baseline direction.
See also baseline direction, inline direction, orientation,
and presentation space orientation.
text presentation. The transformation of document
graphic character content and its associated font
information into a visible form. An example of a visible form
of text is character shapes on a physical medium.
text presentation space. A two-dimensional conceptual
space in which text is generated for presentation on an
output medium.
throughscore. A line parallel to the baseline and placed
through the character .
TIFF . See T agged Image File Format.
tint. V ariation of a color produced by mixing it with white.
toned. Containing marking agents such as toner or ink.
Contrast with untoned.
tone transfer curve. A mathematical representation of
the relationship between the input and output of a system,
subsystem, or equipment. The function is normally one
dimensional consisting of a single channel of input
corresponding to a single channel of output. In imaging
systems, it is mainly used for contrast adjustments. In
printing, the tone transfer curve is also used to modify
images to compensate for dot gain.
transform. A modification of one or more characteristics
of a picture. Examples of picture characteristics that can be
transformed are position, orientation, and size. See also
model transform, segment transform, and viewing
transform.
transform matrix. A matrix that is applied to a set of
coordinates to produce a transform.
symbol set • transform matrix

<!-- Page 237 -->

PTOCA Reference 219
translating. In computer graphics, moving all or part of a
picture in the presentation space from one location to
another without rotating.
transparent data. A method used to indicate that any
control sequences occurring in a specified portion of data
can be ignored.
trimming. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window .
Synonymous with clipping.
triplet. A three-part self-defining variable-length
parameter consisting of a length byte, an identifier byte,
and parameter-value bytes.
triplet identifier . A one-byte type identifier for a triplet.
tristimulus values. Three values that together are used
to describe a specific color . These values are the amounts
of three reference colors (such as red, green, and blue)
that can be mixed to give the same visual sensation as the
specific color .
truncation. Planned or unplanned end of a presentation
space or data presentation. This can occur when the
presentation space extends beyond one or more
boundaries of its containing presentation space or when
there is more data than can be contained in the
presentation space.
tumble-duplex printing. A method used to simulate the
effect of physically turning a sheet around the X
m
axis.
twip. A unit of measure equal to 1/20 of a point. There are
1440 twips in one inch.
type. A table heading for architecture syntax. The entries
under this heading indicate the types of data present in a
construct. Examples include: BITS, CHAR, CODE, SBIN,
UBIN, UNDF .
typeface. All characters of a single type family or style,
weight class, width class, and posture, regardless of size.
For example, Helvetica Bold Condensed Italics, in any
point size.
type family . All characters of a single design, regardless
of attributes such as width, weight, posture, and size.
Examples are Courier and Gothic.
type structure. Attributes of characters other than type
family or typeface. Examples are solid shape, hollow
shape, and overstruck.
type style. The form of characters within the same font,
for example, Courier or Gothic.
type weight. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its type weight. Examples are light, medium,
and bold. Synonymous with weight class.
type width. A parameter indicating a relative change from
the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with
width class.
typographic font. A font with graphic characters that
have varying character increments. Proportional spacing
can be used to provide the appearance of even spacing
between presented characters and to eliminate excess
blank space around narrow characters. An example of a
narrow character is the letter i. Synonymous with
proportionally spaced font. Contrast with monospaced font
and uniformly spaced font.
U
UBIN. A data type for architecture syntax, indicating one
or more bytes to be interpreted as an unsigned binary
number .
unarchitected. Identifies data that is neither defined nor
controlled by an architecture. Contrast with architected.
unbounded character box. A character box that can
have blank space on any sides of the character shape.
underpaint. A mixing rule in which the intersection of part
of a new presentation space P
new
with part of an existing
presentation space P
existing
keeps the color attribute of
P
existing
. This is also referred to as “transparent” or “leave
alone” mixing. See also mixing rule. Contrast with blend
and overpaint.
underscore. A method used to create an underline
beneath the characters in a specified text field. An example
of underscore is the line presented under one or more
characters. Also a special graphic character used to
implement the underscoring function.
UNDF . A data type for architecture syntax, indicating one
or more bytes that are undefined by the architecture.
Unicode. A character encoding standard for information
processing that includes all major scripts of the world.
Unicode defines a consistent way of encoding multilingual
text. Unicode specifies a numeric value, a name, and other
attributes, such as directionality , for each of its characters;
for example, the name for $ is “dollar sign” and its numeric
value is X'0024'. This Unicode value is called a Unicode
code point and is represented as U+nnnn. Unicode
provides for three encoding forms (UTF-8, UTF-16, and
UTF-32), described as follows:
UTF-8 A byte-oriented form that is designed for
ease of use in traditional ASCII
environments. Each UTF-8 code point
contains from one to four bytes. All
Unicode code points can be encoded in
translating • UTF-8

<!-- Page 238 -->

220 PTOCA Reference
UTF-8 and all 7-bit ASCIIcharacters can
be encoded in one byte.
UTF-16 The default Unicode encoding. A fixed,
two-byte Unicode encoding form that
can contain surrogates and identifies the
byte order of each UTF-16 code point via
a Byte Order Mark in the first 2 bytes of
the data. Surrogates are pairs of
Unicode code points that allow for the
encoding of as many as 1 million
additional characters without any use of
escape codes.
UTF-16BE UTF-16 that uses big endian byte order;
this is the byte order for all multi-byte
data within AFP data streams. The Byte
Order Mark is not necessary when the
data is externally identified as UTF-16BE
(or UTF-16LE).
UTF-16LE UTF-16 that uses little endian byte order .
UTF-32 A fixed, four-byte Unicode encoding form
in which each UTF-32 code point is
precisely identical to the Unicode code
point.
UTF-32BE UTF-32 serialized as bytes in most-
significant-byte-first order (big endian).
UTF-32BE is structurally the same as
UCS-4.
UTF-32LE UTF-32 serialized as bytes in least-
significant-byte-first order (little endian).
uniformly spaced font. A font with graphic characters
having a uniform character increment. The distance
between reference points of adjacent graphic characters is
constant in the escapement direction. The blank space
between the graphic characters can vary . Synonymous
with monospaced font. Contrast with proportionally spaced
font and typographic font.
Uniform Symbol Specification (USS). A series of bar
code symbology specifications published by AIM; currently
included are USS-Interleaved 2 of 5, USS-39, USS-93,
USS-Codabar , and USS-128.
unit base. A one-byte code that represents the length of
the measurement base. For example, X'00' might specify
that the measurement base is ten inches.
Universal Product Code (UPC). A standard bar code
symbology , commonly used to mark the price of items in
stores, that can be read and interpreted by a computer .
untoned. Unmarked portion of a physical medium.
Contrast with toned.
UP³I. Universal Printer Pre- and Post-Processing
Interface; an industry standard interface designed for use
in complex printing systems. A specification for this
interface can be obtained at www.afpconsortium.org.
UP A. See user printable area.
UPC. See Universal Product Code.
uppercase. Pertaining to capital letters. Examples of
capital letters are A, B, and C. Contrast with lowercase.
upstream data. IPDS commands that exist in a logical
path from a specific point in a printer back to, but not
including, host presentation services.
usable area. An area on a physical medium that can be
used to present data. See also viewport.
user printable area (UP A). The portion of the physical
printable area to which user-generated data is restricted.
See also logical page, physical printable area, and valid
printable area.
USS. See Uniform Symbol Specification.
UTC. Coordinated Universal T ime, the standard time
reference for Earth and the human race. Knowing the UTC
time and one's time zone offset from it, makes it possible to
calculate the local time; for example, 1:00 PM UTC
corresponds to 5:00 AM Pacific Standard T ime (on the
same day). UTC is almost the same thing as Greenwich
Mean T ime (GMT), that was originally used as the standard
time reference.
V
valid printable area (VP A). The intersection of a logical
page with the area of the medium presentation space in
which printing is allowed. If the logical page is a secure
overlay , the area in which printing is allowed is the physical
printable area. If the logical page is not a secure overlay
and if a user printable area is defined, the area in which
printing is allowed is the intersection of the physical
printable area with the user printable area. If a user
printable area is not defined, the area in which printing is
allowed is the physical printable area. See also logical
page, physical printable area, secure overlay , and user
printable area.
variable space. A method used to assign a character
increment dimension of varying size to space characters.
The space characters are used to distribute white space
within a text line. The white space is distributed by
expanding or contracting the dimension of the variable
space character's increment dependent upon the amount
of white space to be distributed. See also variable space
character and variable space character increment.
variable space character . The code point assigned by
the data stream for which the character increment varies
according to the semantics and pragmatics of the variable
space function. This code point is not presented, but its
character increment parameter is used to provide spacing.
See also variable space character increment.
UTF-16 • variable space character

<!-- Page 239 -->

PTOCA Reference 221
variable space character increment. The variable value
associated with a variable space character . The variable
space character increment is used to calculate the
dimension from the current presentation position to a new
presentation position when a variable space character is
found. See also variable space character .
vector graphics. A vector has a defined starting point, a
designated direction, and a specified distance. V ector
graphics is line-based graphics data, where vectors
determine how straight and curved lines are shaped
between specific points. A picture consists of lines and
colors to fill the areas enclosed by the lines.
verifier . In bar code systems, a device that measures the
bars, spaces, quiet zones, and optical characteristics of a
bar code symbol to determine if the symbol meets the
requirements of a bar code symbology , specification, or
standard.
vertical bar code. A bar code pattern that presents the
axis of the symbol in its length dimension parallel to the Y
bc
axis of the bar code presentation space. Synonymous with
ladder bar code.
vertical font size. (1) A characteristic value,
perpendicular to the character baseline, that represents the
size of all graphic characters in a font. Synonymous with
font height. (2) In a font character set, nominal vertical font
size is a font-designer defined value corresponding to the
nominal distance between adjacent baselines when
character rotation is zero degrees and no external leading
is used. This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified vertical font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
vertical scale factor . In outline-font referencing, the
specified vertical adjustment of the Em square. The vertical
scale factor is specified in 1440ths of an inch. When the
horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also horizontal scale
factor .
viewing transform. A transform that is applied to model-
space coordinates. Contrast with model transform.
viewing window . That part of a model space that is
transformed, clipped, and moved into a graphics
presentation space.
viewport. The portion of a usable area that is mapped to
the graphics presentation space window . See also
graphics model space and graphics presentation space.
visibility . The property of a segment that declares
whether the part of a picture defined by the segment is to
be displayed or not displayed during the drawing process.
void. In bar codes, the undesirable absence of ink in a
bar code symbol bar element.
VP A. See valid printable area.
W
ward. A deprecated term for section.
weight class. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its weight class. Examples are light, medium,
and bold. Synonymous with type weight.
white point. One of a number of reference illuminants
used in colorimetry that serve to define the color “white”.
Depending on the application, different definitions of white
are needed to give acceptable results. For example,
photographs taken indoors might be lit by incandescent
lights, that are relatively orange compared to daylight.
Defining “white” as daylight will give unacceptable results
when attempting to color correct a photograph taken with
incandescent lighting.
white space. The portion of a line that is not occupied by
characters when the characters of all the words that can be
placed on a line and the spaces between those words are
assembled or formatted on a line. When a line is justified,
the white space is distributed among the words,
characters, or both on the line in some specified manner .
See also controlled white space.
width class. A parameter indicating a relative change
from the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with type
width.
window . A predefined part of a graphics presentation
space. See also graphics presentation space window .
writing mode. An identified mode for the setting of text in
a writing system, usually corresponding to a nominal
escapement direction of the graphic characters in that
mode; for example, left-to-right, right-to-left, top-to-bottom.
X
X
bc
extent. The size of a bar code presentation space in
the X
bc
dimension. See also bar code presentation space.
X
bc
,Y
bc
coordinate system. The bar code presentation
space coordinate system.
X dimension. In bar codes, the nominal dimension of the
narrow bars and spaces in a bar code symbol.
variable space character increment • X dimension

<!-- Page 240 -->

222 PTOCA Reference
X
g
,Y
g
coordinate system. In the IPDS architecture, the
graphics presentation space coordinate system.
X height. The nominal height above the baseline,
ignoring the ascender , of the lowercase characters in a
font. X height is usually the height of the lowercase letter x.
See also lowercase and ascender .
X
io
,Y
io
coordinate system. The IO-Image presentation
space coordinate system.
XML. See Extensible Markup Language.
XMP . See Extensible Metadata Platform.
X
m
,Y
m
coordinate system. (1) In the IPDS architecture,
the medium presentation space coordinate system. (2) In
MO:DCA, the medium coordinate system.
X
oa
,Y
oa
coordinate system. The object area coordinate
system.
X
ol
,Y
ol
coordinate system. The overlay coordinate
system.
X
p
extent. The size of a presentation space or logical
page in the X
p
dimension. See also presentation space and
logical page.
X
pg
,Y
pg
coordinate system. The coordinate system of a
page presentation space. This coordinate system
describes the size, position, and orientation of a page
presentation space. Orientation of an X
pg
,Y
pg
coordinate
system is relative to an environment specified coordinate
system, for example, an X
m
,Y
m
coordinate system.
X
p
,Y
p
coordinate system. The coordinate system of a
presentation space or a logical page. This coordinate
system describes the size, position, and orientation of a
presentation space or a logical page. Orientation of an X
p
,
Y
p
coordinate system is relative to an environment-
specified coordinate system. An example of an
environment-specified coordinate system is the X
m
,Y
m
coordinate system. The X
p
,Y
p
coordinate system origin is
specified by an IPDS Logical Page Position command. See
also logical page, medium presentation space, and
presentation space.
X
qr
,Y
qr
coordinate system. In the BCOCA architecture,
the coordinate system defined by the QR Code symbol
when producing a QR Code with Image bar code.
Y
Y
bc
extent. The size of a bar code presentation space in
the Y
bc
dimension. See also bar code presentation space.
YCbCr . A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order .
YCCK. CMYK data carried in the luminance-chrominance
form. YCC are computed from CMY , while K is the black
channel carried in the reverse-video form (K = 255 - K).
See Appendix B, “Adobe APP14 JPEG Marker” in
Presentation Object Subsets for AFP .
YCrCb. A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order .
Y
p
extent. The size of a presentation space or logical
page in the Y
p
dimension. See also presentation space and
logical page.
Yxy color space. A color space belonging to the XYZ
base family that expresses the XYZ values in terms of x
and y chromaticity coordinates, somewhat analogous to
the hue and saturation coordinates of the HSV color space.
X
g
,Y
g
coordinate system • Yxy color space

<!-- Page 241 -->

Copyright © AFP Consortium 1997, 2025 223
Index
A
Absolute Move Baseline (AMB) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .49
Absolute Move Inline (AMI) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .51
AFPC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii
AMB . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .49
AMI . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .51
B
Begin Line (BLN) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .53
Begin Suppression (BSU) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .54
BITS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
BLN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .53
BSU . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .54
C
chaining . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 16, 36, 46
CHAR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
character escapement . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .15
CODE . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
coded font . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .46
color . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 23, 30–31, 90, 102, 121, 140, 143
complex text . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 18–20
control sequence . . . . . . . . . . . . . . . . . . . . . . . . . .7, 9, 16, 25, 27–29, 36, 1 12
control sequence (specific)
Absolute Move Baseline, AMB . . . . . . . . . . . 21, 41, 49, 77, 1 12, 120
Absolute Move Inline, AMI . . . . 21–22, 24, 51, 60, 74–75, 82, 96–
97, 1 15, 1 17, 142
Begin Line, BLN . . . .21, 23, 30–31, 41, 53, 60, 77, 82, 84, 96–97,
99, 1 12, 1 15, 120, 139, 142, 155, 157, 159
Begin Suppression, BSU . . . . . . . . . . . . . . . . . . . . . . . . . . . . 21, 54, 63, 174
Draw B-axis Rule, DBR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 56
Draw I-axis Rule, DIR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 58
Encrypted Data, ENC . . . . . . . . . . . . . . . . . . . . . . . 22–24, 60–62, 88, 100
Encryption. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .20
End Suppression, ESU . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 54, 63, 174
Glyph Advance Run, GAR . . . . . . . . . . . . . . . . . . .19–20, 22, 64–65, 67
Glyph ID Run, GIR . . . . . . . . . . . . . . . . . . . . . . . . . . . .19–20, 22, 64–65, 67
Glyph Layout Control, GLC . . . . . . . . . . . . . . . . . . . . . . . . . . 19, 22, 64–66
Glyph Offset Run, GOR . . . . . . . . . . . . . . . . . . . . . .19–20, 22, 64–65, 67
No Operation, NOP . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 73, 155, 157, 159
Overstrike, OVS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 74
Relative Move Baseline, RMB . . . . . . . . . . . . . . . . 23, 77–78, 1 12, 120
Relative Move Inline, RMI . . . . . . 22–24, 60, 74–75, 80, 82, 96–97,
1 15, 1 17, 142
Repeat String, RPS . . . . . . . . . . . . . . . . . . . . . . 23, 46, 82, 155, 157, 159
Set Baseline Increment, SBI . . . . . . . . . . . . . . . . . . . . 21, 23, 30, 84, 139
Set Coded Font Local, SCFL . . . . . . . . . . . . . . . . . . . . . . . . 23, 30, 77, 86
Set Encrypted Alternate, SEA . . . . . . . . . . . . . . . . . . . . . . . . . . . . 23, 61, 88
Set Extended T ext Color , SEC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 23, 90
Set Inline Margin, SIM . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 23, 31, 37, 99
Set Intercharacter Adjustment, SIA . . . . . . . . . . . . . . . . . . . . . . 23, 31, 96
Set Key Information, SKI . . . . . . . . . . . . . . . . . . . . . . 24, 61, 88, 100–101
Set T ext Color , STC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 24, 102
Set T ext Orientation, STO . . . . . . . . . . . . . . . . . . . . . 24, 31, 46, 105, 137
Set V ariable Space Character Increment, SVI . . . . . . . . . . . . . 24, 108
T emporary Baseline Move, TBM . . . . . . . . . . . . . 24, 76–77, 1 10, 120
T ransparent Data, TRN . . . . . . . . 23–24, 46, 82, 1 15, 155, 157, 159
Underscore, USC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 24, 1 17
Unicode Complex T ext, UCT . . . . . . . . . . . . . . . 18, 20, 25, 64, 67, 122
conventions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .47
coordinate system . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 10, 24, 40, 46, 105
D
data stream . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 9, 143, 165, 169
data types . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .34
DBR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .56
default . . . . 33–34, 49, 51, 54, 56, 58, 60, 63, 74, 78, 80, 82, 84, 86,
88, 90, 96, 99, 102, 105, 108, 1 10, 1 15, 1 17, 139, 141–143
default hierarchy . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .35
default indicator . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .38
DIR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .58
Draw B-axis Rule (DBR) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .56
Draw I-axis Rule (DIR) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .58
E
ENC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .60
Encrypted Data (ENC) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .60
End Suppression (ESU) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .63
ESU . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .63
exception condition . . . . . . . . . . . 10, 16, 34, 41, 76, 145, 148, 167, 171
exception condition code (specific)
EC-0001 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 39, 45, 148, 172
EC-0103 . . . . . 41, 45, 49–52, 57, 59, 61–62, 78–81, 83, 1 14–1 16,
138, 148, 172
EC-0201 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 55, 148, 172
EC-0401 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 55, 148, 172
EC-0505 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 136, 138, 148, 172
EC-0601 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 54, 148, 172
EC-0605 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 137–138, 148, 172
EC-0705 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 137–138, 148, 172
EC-0C01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 86–87, 139–140, 148, 172
EC-0E02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 90, 95, 140, 148, 172
EC-0E03 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 90, 95, 140, 148, 172
EC-0E04 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 90, 95, 140, 148, 172
EC-0E05 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 90, 95, 140, 148, 172
EC-0F01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 106–107, 149, 172
EC-1001 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 99, 141–142, 149, 172
EC-1 101 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .84–85, 139, 149, 172
EC-1201 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .97–98, 142, 149, 172
EC-1301 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 50, 149, 172
EC-1401 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 49, 51, 149, 172
EC-1403 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 49–50, 79, 149, 172
EC-1501 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 80–81, 149, 172
EC-1601 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 78–79, 149, 172
EC-1701 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 108, 149, 172
EC-1802 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .86–87, 140, 149, 172
EC-1901 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 83, 149, 172
EC-1A01 . . . . . . . . . . . . . . . . . . . . . . . . 61–62, 82–83, 1 16, 128, 149, 172
EC-1A03 . . . . . . . . . . . . . . . . . . . . . . . 61–62, 83, 1 16, 128–129, 150, 172
EC-1B01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 82–83, 150, 172
EC-1C01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 39, 45, 150, 172
EC-1E01 . . . 39, 45, 47, 56–59, 61–62, 86–87, 89, 99–101, 106–
109, 150, 172
EC-1F01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 83, 150, 172
EC-2100 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 40, 45, 77, 150, 172
EC-3F02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 77, 87, 140, 150, 172
EC-5803 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 104, 143, 150, 172
EC-6802 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 144, 150, 173
EC-6803 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 144
EC-6902 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 144, 150, 173
EC-6A02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 141, 150, 173
EC-6B02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 141, 150, 173
EC-8002 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 56–59, 150, 173
EC-8202 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 56–59, 151, 173
EC-9601 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .55

<!-- Page 242 -->

224 PTOCA Reference
EC-9801 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 54–55, 63, 151, 173
EC-9803 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 1 1, 1 13, 151, 173
EC-9A01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 76–77, 151, 173
EC-9B01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 128–129, 151, 173
EC-9C00 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .69
EC-9C01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .69
EC-9C02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .65
EC-9C03 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 64–65, 69, 71
EC-9C06 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 64–65, 71
EC-9C08 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 64, 71
EC-9C09 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .70
EC-9C0A . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .70
EC-9C0B . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .70
EC-9D01 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 61–62, 100–101, 151, 173
EC-9D02 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 61–62, 151, 173
EC-9D03 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 61, 151, 173
F
font . . 17, 21, 23–24, 30, 40, 65, 67, 76, 82, 86, 108–109, 1 15, 1 19,
122, 139
G
GAR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 64, 71
GIR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 65, 67, 71
GLC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 66–67, 71
Glyph Advance Run (GAR) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .64
Glyph ID Run (GIR) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .65
Glyph Layout Control (GLC) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .66
GOR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .71
graphic character placement . . . . . . . . . . . . . 7, 9, 14–15, 40–42, 46, 96
H
hierarchy . . . . 9–10, 21, 33–34, 56, 58, 84, 86, 90, 96, 99, 102, 108,
1 10, 1 17, 141–143
I
initial measurement unit parameters . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 136
initial size parameters . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 137
initial text condition . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 29, 136
initial text condition (specific)
Baseline Increment . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 139, 165
Coded Font Local ID . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 139, 165
Extended T ext Color . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 140
Initial Baseline Coordinate . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 141, 165
Initial Inline Coordinate . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 141, 165
Inline Margin . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 141, 165
Intercharacter Adjustment . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 142, 165
T ext Color . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 143, 165
T ext Orientation. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 143, 165
initial text condition parameters . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 139
M
measurement . . . . 12–13, 21, 34, 47, 49, 51, 56, 58, 63–64, 66, 74,
78, 80, 84, 96, 99, 105, 108, 1 10, 1 17, 124, 154, 157, 159
metadata . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 4
migration functions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
modal control sequence . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .37
N
nesting . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 24, 54, 1 19
No Operation (NOP) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .73
NOP . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .73
O
obsolete functions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
orientation . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 9, 17, 46, 87, 105, 139, 143
overflow . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .34
overrun . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .40
Overstrike (OVS) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .74
OVS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .74
P
parameter . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 27–29, 33, 35, 136, 154
parameter (specific)
alternate text . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 62, 88
B-axis orientation . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 105, 137, 143, 165
baseline displacement . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .49
baseline increment . . . . . . . . . . 16, 21, 30, 53, 78, 84, 1 10, 139, 165
Bidi layout processing control . . . . . . . 28, 124, 126–129, 132–133,
151
bypass identifier . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 22, 24, 74, 1 17
encrypted data . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 60, 88
extent . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 29, 137
foreground color . . . . . . 23–24, 30–31, 90, 102, 140, 143, 165, 170
foreground color precision . . . . . . . . . . . . . . . . . . . . . . . 24, 102, 165, 170
glyph advance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 64, 160
glyph advance along current baseline . . . . . . . . . . . . . 28, 66–67, 160
glyph font name . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 20, 28, 66–67, 70, 160
glyph ID . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 28, 65, 160
glyph length of font name. . . . . . . . . . . . . . . . . . . . . . . 28, 66–67, 70, 160
glyph length of object identifier for font. . . . . . . . 28, 66–67, 70, 160
glyph object identifier for font . . . . . . . . . . . . . . . . . . 20, 28, 66–67, 160
glyph offset from current baseline . . . . . . . . . . . . . . . . . . . . . . 28, 71, 160
I-axis orientation . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 31, 105, 137, 143, 165
ignored data . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .73
initial baseline coordinate . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 141, 165
initial inline coordinate . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 30, 141, 165
inline displacement. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 51, 99
inline increment. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .80
inline margin . . . . . . . . . . . . . . . . . . . . . . . 16, 21, 31, 46, 53, 99, 141, 165
intercharacter adjustment . . . 15, 23, 31, 40, 46, 87, 96, 140, 142,
165
intercharacter adjustment direction . . . . 23, 31, 96, 142, 165, 170
key information . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 88, 100
local identifier . . . . . . . . . . . . . . . . 21, 23, 30, 54, 63, 86, 139, 165, 174
overstrike character . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .74
repeat length . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .82
repeated data . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .82
rule length . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 56, 58
rule width . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 56, 58, 154, 157, 159
suppression identifier . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 54, 63
temporary baseline direction . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 24, 1 10
temporary baseline increment . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 24, 1 10
temporary baseline precision . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 10
transparent data . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 15
Unicode alternate current inline position . . . . . . . . 28, 124, 126–127
Unicode control flags . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 28, 123, 125–126
Unicode data length . . . . . . . . . . . . . . . . . . . 28, 123, 125–126, 128, 151
Unicode glyph processing control . . . . . . . . . 28, 124, 127–129, 151
Unicode version level . . . . . . . . . . . . . . . . . 28, 123, 125, 128–129, 151
unit base . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 13, 29, 136
units per unit base . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 13, 29, 136
variable space character increment . . . . . . . . . . . . . . . . . . . . . . . . . 24, 108

<!-- Page 243 -->

PTOCA Reference 225
parameter default hierarchy . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .35
parameter specification hierarchy . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .35
precision parameter . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
Presentation Architectures . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1
presentation environment . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1
Presentation Text data . . . . . . . . . . . . . . . . . . . . . . . . 9, 21, 40, 46, 163, 166
Presentation Text Data Descriptor . . . . . . 7, 9, 12, 19, 21, 29–30, 33,
37–38, 40, 46, 84–85, 136, 140, 163–164, 170
Presentation Text object . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 9, 33, 37
Presentation Text object space . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 9–10, 24
PTOCA PT1
compliance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 154
PTOCA PT2
compliance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 156
PTOCA PT3
compliance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 158
PTOCA PT4
compliance . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 160
R
Relative Move Baseline (RMB) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .78
Relative Move Inline (RMI) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .80
Repeat String (RPS) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .82
reserved fields. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .47
retired functions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
retired parameters . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 177
RMB . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .78
RMI . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .80
rotation . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 9, 17, 77, 86, 105
RPS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .82
S
SBI. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .84
SBIN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
SCFL . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .86
scope of text object . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 37
SEA . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .88
SEC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .90
Set Baseline Increment (SBI) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .84
Set Coded Font Local (SCFL) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .86
Set Encrypted Alternate (SEA) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .88
Set Extended T ext Color (SEC) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .90
Set Inline Margin (SIM) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .99
Set Intercharacter Adjustment (SIA) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .96
Set Key Information (SKI) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 100
Set T ext Color (STC) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 102
Set T ext Orientation (STO) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 105
Set V ariable Space Character Increment (SVI) . . . . . . . . . . . . . . . . . . . 108
SIA. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .96
SIM . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .99
SKI. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 100
standard action value . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .47
standard actions . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 147
STC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 102
STO . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 105
subset . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 10, 153
suppression . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 16, 21, 54, 63, 167, 174
SVI. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 108
syntax diagrams
BITS . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
CHAR . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
CODE . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
how to read . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
SBIN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
UBIN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
UNDF . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
T
TBM . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 10
T emporary Baseline Move (TBM) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 10
text major . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 37, 109, 138, 165, 169
text object . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 7, 37, 138, 169–170
trademarks . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iii, 180
T ransparent Data (TRN) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 15
TRN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 15
U
UBIN . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
UCT . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 71, 122
Underscore (USC) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 17
UNDF . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . iv
Unicode Complex T ext (UCT) . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 122
UP³I . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 220
USC . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 1 17

<!-- Page 244 -->

Advanced Function Presentation Consortium
Presentation Text Object Content Architecture
ReferenceAFPC-0009-04
