# Chapter 2. Introduction to Fonts

This chapter presents introductory information about fonts, digitized font structures, and how digitized fonts are used in information processing. The information in this chapter will aid the reader in understanding the Font Object Content Architecture (FOCA), but is not a required component of the architecture.

## Fonts

A font is a set of graphic characters with similar design characteristics; that is, a font is a designer's concept of how a set of graphic characters should appear. Graphic characters (*glyphs* is the term used by the ISO/IEC 9541 Font Information Interchange standard) are letters, numerals, punctuation marks, ideograms, or symbols that appear in text.

Historically, a font was a collection of lead slugs containing the raised images of the characters. However, in electronic digital processing, the character images are digitized by transforming them into data or algorithms that are stored in a computer system. The character shape data is then used to display the images in the form of small dots on a presentation surface, for example, as picture elements (pels) on a display screen or as dots on a piece of paper.

Figure 4 shows how the pels or dots form a pattern that can be interpreted as a graphic character; it shows the letter h from a typeface family with the design characteristic of serifs. Such a graphic character could be presented on a presentation surface through any of several available presentation technologies, for example, wire matrix impact, laser fusion, ink jet transfer, or display phosphor illumination, all of which are able to approximate the form, style, and appearance intended by the font designer. The data or algorithm used to represent the character shape in digitized form on a computer system can be quite different from the pels or dots that appear on the presentation surface. The character shapes can be digitized as a matrix of binary bits, as a series of vectors, or as a set of second or third order polynomial equations.

**Figure 4. Representation of a Graphic Character**

A digitized font contains not only the character shape information, but all of the information needed by an information processing system to format a character string and render the character shapes with a given presentation technology. Digitized fonts contain descriptive information used to identify the specific font resource, metric information used to position the character shapes, and the shape information used to render the character shapes.

In theory, a font could be defined to be the universal set of all the world's characters having the same design characteristics. In practice however, a digitized font resource contains a bounded set of characters having the same design characteristics. Further, multiple digitized font resources can exist having the same design characteristics, but each containing a different bounded set of characters. The set of characters contained in a font is determined by the font provider, depending on the language requirements for document processing.

Each graphic character in a font has a unique name, which is called the graphic character identifier. Each graphic character in a document data stream has a unique code, which is called a code point. A code page is a table that associates graphic character identifiers with code points (see Figure 5).

**Figure 5. Relationship of Code Points to Graphic Characters**

1.  **Document Data Stream**: Code Page ID, Font Resource ID, Code Points
2.  **Code Page Resource**: Code Page ID, Mapping (Code Point to Character ID)
3.  **Font Resource**: Font Resource ID, Character ID, Metrics, Shape
4.  **Process**: Format Process, Print Process
5.  **Output Document**

When documents are formatted and presented, the graphic characters in the document, which are usually encoded as code points of one or two bytes for each graphic character, are associated with the character information in a font through the code page. For an example, presentation of the word “the” requires the character shapes for the graphic characters t, h, and e. Each code point in the code page maps to a character identifier, which points to the font information, including metrics and shape. One code page is often sufficient to represent all the graphic characters in the document, although multiple code pages can be used.

Fonts are either monospaced, in which the horizontal space occupied by different characters is the same, or typographic, that is, proportionally spaced fonts, in which the character widths vary.

In typographic fonts, the font designer has adjusted the distance from one character to another to improve the visual flow of text by eliminating excess space. Adjusting space this way improves the readability and the appearance of a document.

For comparison, the letter i and the letter m are printed below 15 times in both a monospaced font and a typographic font. Note that in the monospaced font, both strings of characters are the same length, but in the typographic font, the string of m's is longer than the string of i's.

*   **Monospaced**:
    iiiiiiiiiiiiiii
    mmmmmmmmmmmmmmm
*   **Typographic**:
    iiiiiiiiiiiiiii
    mmmmmmmmmmmmmmm

### Font Resources

The term **font resource** refers to the collection of code pages and other font information such as FOCA font parameters that are stored in the font library and used by data processing systems. The parameters define the attributes for a particular font. A parameter includes a name by which it can be referenced and a value that defines the attribute. See Chapter 5, “FOCA Parameters”, for information about FOCA parameters, including their names, acceptable values, and usages.

Font resources normally reside in system storage; and the aggregate of stored font information is often called the font library. Font resources can also be resident in a presentation device or control unit. A font resource can be all or only a part of the information pertaining to a particular font.

### Font References

A **font reference** is information that identifies a particular font resource. A font reference is embedded either in a document data stream or in an application program, which processes a document data stream and requests the use of a font resource. For example, a font reference could be the name of a font file or a list of font parameters. When font references are carried in an architected data stream or a program, they use the format, that is, the syntax, required by the document content architecture.

**Note:** Font references should not be confused with data objects, which use their own syntax when carried in architected data streams and use the data stream only as an envelope.

FOCA supports using font references by defining a precise set of parameters that specify a font resource or describe the font resource attributes. Each implementing product chooses the set of font resources it will support, and the set of characters contained in each of those font resources. For consistency when interchanging and presenting documents, all receiving sites, that is, processing applications and presentation devices, must have access to the same or equivalent font resources.

### AFP Font Resources

An AFP font resource consists of font information for a specific font family in one or more styles and sizes originally developed by IBM. When FOCA font parameters are used, they support a broad range of IBM encoding schemes as well as the specific encoding supported by AFP products. For example, AFP products support a variety of encoding schemes for code pages and specific codes, which meet the processing requirements of specific geographic, language, or application environments. These encoding schemes identify the graphic characters of a document by their meaning, for instance, open parenthesis, or by their shape, for instance, left parenthesis.

The graphic characters in a font resource can be defined to support multiple code page encoding schemes, or they can correspond precisely to the code points of a specific code page encoding scheme. AFP font resources contain a large amount of font information, which allow products to access and process the resources in a variety of application environments.

The graphic characters in a font can be mapped to any of the various types of code page encoding schemes. However, the set of graphic characters defined within a font must be equal to or greater than the set of graphic characters defined in the code page. This ensures that all graphic characters in the encoding scheme are supported by the font and that font information is available at document formatting or document presentation time.

The following are the two types of AFP font resources:

*   **A system-level font resource** is a font from which document processing applications can obtain formatting information that is resolution independent and from which device service applications can obtain device specific presentation information. A system-level font resource normally resides within a font library, which provides a common source of font information for all applications or devices.
*   **A device-level font resource** is a device-specific font from which the presentation device, or family of presentation devices, can obtain the font information required to present the character images. A device-level font resource normally resides within a ROM cartridge (a cartridge containing read-only memory), a loadable file on a disk or on a tape, or a loadable file from a host system.

Figure 6 shows that FOCA supports all transactions as the user selects graphic characters and FOCA products draw on both levels of resources to produce the output.

**Figure 6. FOCA Information Processing Environment**

## Digitized Font Structures

Digitized fonts are grouped (structured) according to the different function of the information that they provide. The following list identifies the parameter groups, the type of information provided, the function of the information, and the number of times, as a general practice, the information would occur in a typical digitized font resource:

*   **Font-description parameters**: Provide font-descriptive information for referencing, that is, identifying and describing, fonts. Defined once per font resource.
*   **Font-metric parameters**: Provide font-metric information that states measurements for positioning the font and each character in the font. Repeated for each rotation supported.
*   **Character-shape parameters**: Provide character-shape information for forming images of the character shapes on a presentation surface. Repeated for each shape representation technology supported.
*   **Character-mapping parameters**: Provide character-mapping information for associating character identifiers with code points on code pages. Repeated for each code page supported.

Figure 7 shows an example of the organization of parameters in the library.

**Figure 7. Font Information in a Library**

*   **Font-Descriptive Information**: Resource Name, Typeface, Weight, etc. (One per resource)
*   **Font-Metric Information**: Rotation 1, Rotation 2, ... Rotation n (One for each rotation)
    *   Max Ascender, A-Space, etc.
*   **Character-Shape Information**: Technology 1, Technology 2, ... Technology n (One for each technology)
    *   Shape Map, Shape Data, etc.
*   **Character-Mapping Information**: Code Page 1, Code Page 2, ... Code Page n (One for each code page)
    *   Page Name, Code Map, etc.

### Font-Descriptive Information

Font-descriptive information identifies and describes a font. The information includes, for example, resource name, family name, typeface name, weight, and supported sizes. The identification of a font resource for use by a system processor requires some or all of this information in the library to ensure access to the correct font. Often, the originator of a document can identify a specific font resource by simply stating the name. But, in large systems that have numerous fonts and in distributed networks where available fonts are not known, listing each desired parameter and letting the system locate a resource that corresponds to the description might provide more accurate font selection. FOCA provides the common parameter definitions necessary to match descriptive information in a font reference to the definitions in a font resource.

Font-descriptive information should be grouped to identify and describe the font resource. See “Font-Description Parameters” for a description of each FOCA parameter that carries font-descriptive information.

### Font-Metric Information

Font-metric information contains metrics, which is measurement information that defines the height, width, and space for a font or for each character in the font. Font-metric information also includes character-metric information. For example, a font resource might contain the information for the averages or maximums for all of the graphic characters as well as the measurements for each character. This information determines where a character will appear in a presentation space.

Furthermore, the presentation of graphic characters in a top-to-bottom writing mode requires different metric information than for left-to-right writing mode. Therefore, it is necessary to provide multiple groups of metric information for the various writing modes supported by the font resource. The font-metric information in FOCA is grouped by character rotation (see “Character Rotation” for the relationship between character rotation and non-Latin writing systems). The metrics can be expressed in the pel resolution of the presentation device that is used to present the font information or in a form that is resolution independent and applicable to all presentation devices.

Font-metric information must be defined for each rotation supported by the font resource. For good performance, the metric information should be grouped within a font resource. See “Font-Metric Parameters” for a description of each FOCA parameter that carries font-metric information and “Character-Metric Parameters” for each FOCA parameter that carries character-metric information.

### Character-Shape Information

Character-shape information enables the presentation device to create the image of the graphic character. The representation of character shapes in a font resource can be repeated for each supported shape representation technology (bitmaps, vectors, and conic sections). In theory, multiple device-specific representation technologies can be supported by a single font resource (for example, a font resource might include shape data for 240 pel, 300 pel and 600 pel bitmap representations), or a representation technology can be transformed from one to another (for example, vectors might be converted to 240-pel and 300-pel bitmap representations). In practice, most font resources contain the character shape information for a single representation technology.

Character-shape information can be defined once for all rotations supported by the font-metric information, or could be repeated in different font resources for each rotation. For good performance, the shape information should be defined once in a single font resource for all supported rotations. See “Character-Shape Parameters” for a description of each FOCA parameter that carries character-shape information.

### Character-Mapping Information

Character-mapping information provides for the association of code points in a document to the appropriate graphic characters in a font. Character mapping requires knowledge about the techniques of document encoding, the identification of font characters, and the mapping of document character codes to font character identifiers.

The characters in a font resource can be defined to support the character content of one or more code pages. Using code pages minimizes processor storage and maximizes the document processing efficiency. That is, when the characters in a font resource are defined independently of the code page intended for support, many different documents, encoded with different code pages, can be presented using a single font resource.

Either a font resource contains the character identifiers and code points for mapping to each supported code page, or the code page definitions are stored separately from the font resource. If the first case is true and the font resource includes code page mappings, those mappings should be stored independently from the font-metric information and character-shape information (for each technology), except for character identifiers. The character-mapping information should be available for each supported code page. See “Character-Mapping Parameters” for a description of each FOCA parameter that carries character-mapping information.

## Using Digitized Fonts

Digitized fonts are designed to be used by an application program that processes the font resource and creates output that the user can read when presented by a presentation device. The font resource must be produced and stored in the system before an application program can access and process it. The following sections describe the relationship of a font architecture to font production, font storage, and font processing.

### Font Production

Font production occurs under a variety of circumstances, for example:
*   An organization that specializes in font development using sophisticated design tools creates a new font family.
*   An organization creates a special font for a collection of symbols to distribute to its branch offices.
*   An individual modifies a few graphic characters in an existing font to make a company slogan fit on one line of a wallet-sized card.
*   A developer of computers or text systems adapts an existing font to meet a customer's requirement for better performance.
*   A computer assembles some font components as they are needed to create a document that includes both text and graphics.

Regardless of who or what initiates the font resource or its intended purpose, the font designer:
*   Designs character shapes
*   Converts the character shapes to a digital format and includes information for the presentation technology, such as bitmaps, vectors, or conic sections
*   Defines the parameter values, such as height, width, and escapement point, for each character shape
*   Assigns appropriate descriptive and identifying information, such as a graphic character identifier, to each character shape
*   Creates any other information required by the application program

FOCA supports font production by providing definitions of the font parameters and by describing how they are used.

### Font Storage

Font storage requires a method of addressing two factors:
*   **Data availability**: Is the descriptive and measurement information available both to the applications that generate and format documents as well as to the devices that present the output?
*   **Data ownership**: Is permission required to share or distribute this font information?

To create font resources that are more accessible for application use, the data can be stored as logical units rather than as a single, comprehensive font file. Logical units permit each system component or application program to access only those font elements they require, and might have the additional benefit of minimizing costly license payments that apply to other logical elements that the application doesn't require.

For example, a formatting application needs the font metric information that is available free to any using application, but does not require the licensed shape information that is required by a presentation device. Other applications may require font-metric information in addition. The following are some uses of font information as logical blocks:
*   Network-distributed libraries that have database access communicate over the network.
*   Host-system libraries that have shared data access or a resource-management program interface to any attached device or workstation application.
*   Workstation-resident libraries or collections of files shared by users run in that or another workstation.
*   Data compiled or linked to a certain editor has access only to the modules within that application program.
*   Data resident within and available only to a specific presentation device is made available to the device by down-line loading or by exchangeable read-only storage.

FOCA supports font storage by precisely defining the set of font parameters required in each AFP environment so applications can implement FOCA by using only those logical structures or elements they require.

### Font Processing

Font processing is the set of tasks performed when an application program accesses and uses a font resource. It includes three primary tasks:
*   **Editing**: Creating and then modifying a data stream called a revisable document.
*   **Formatting**: Using the revisable document to create a data stream, such as MO:DCA, called a presentation document.
*   **Presenting**: Using the presentation document to create an output document that humans can read, such as a printed page.

Some systems require the user to request formatting as a separate step after editing, while other applications, such as a WYSIWYG editor, combine these tasks into what appears to be a single process. However, without a command from the user, WYSIWYG systems also perform each task individually before presenting the results.

In the context of FOCA, font processing is accomplished by a text processing system, which is a type of editor or processing system that is the collection of hardware devices and software or firmware programs required to generate, modify, display, and print text on a presentation surface. The following are examples of text processing systems and components:
*   Personal computers with a character display and an all-points-addressable (APA), dot-matrix printer
*   Office workstations with a WYSIWYG editor, display, and an APA laser printer
*   Graphic workstations with a high-resolution color display and software applications for rotating and scaling text to annotate figures
*   Publishing workstations with preview devices that permit low-quality, fast-output preview of output and with software applications for page layout and composition
*   Workstations in a network with distributed storage and distributed print systems

A processing system uses different types of font resource information when performing each of the three tasks. Figure 8 shows the tasks and indicates which type of information is used for each during font processing.

**Figure 8. Font Resource Requirements for Font Processing Tasks**

1.  **User Input** (User Reference Manual)
2.  **Editing** (Requires Font-Descriptive information)
3.  **Revisable Document**
4.  **Formatting** (Requires Font-Metrics and Character-Metrics information)
5.  **Presentation Document**
6.  **Presenting** (Requires Character-Shape information)
7.  **Output Document**

#### Editing

Editing is the task of creating or modifying the data stream that is the source for producing a document. Editing is performed by an application program or editor. The editor processes the font references, which must be consistent with the syntax specified by the document content architecture. The references use the FOCA parameters that provide font-descriptive information, that is, the references identify or describe the font. The references are also used to perform character mapping.

#### Formatting

Formatting is the task of determining where information is to appear in a presentation space. The formatter uses the FOCA parameters that contain font-metric information and character-metric information and position the graphic characters on the presentation surface.

The parameters include information about line and page breaks and how text flows around graphic and image objects in the document. Fonts provide only part of the information needed for character positioning. The formatter not only uses font parameters, but it also uses information contained in the document, such as paragraph breaks and other formatting instructions.

FOCA defines metrics for all characters in a font. If a development team creates or acquires a font resource for a presentation product, all formatters supported for that product must have access to the metrics for each character in that font.

#### Presenting

Presenting is the task of transforming the formatted information to a visible form on a presentation surface. The FOCA parameters that support presenting the document use character-shape information that images the character at its correct position.

The task of presenting creates the character shapes on a presentation surface at the position determined by the formatter. The presentation is performed by a hardware device, but it might be supported by software and hardware processes that provide the required position and shape information. The character-shape information is used only by the presentation device. Character-metric information used when presenting the font must correspond to the character-metric information used by the formatter.

FOCA supports presenting character shapes by providing support for device-specific techniques of character representation. FOCA also permits font producers and product implementers to make use of more generic representation technologies.

### Font Processing Summary

The editing, formatting, and presenting tasks all use the FOCA font-descriptive information. During editing, the editor identifies (references) the font resource; during formatting, the formatter uses that font resource to locate and position the graphic characters, and during presenting, the presentation device uses that font resource to create the character shape with the proper position and metric information. The references can specify a specific font resource name or provide descriptive information about the font.

All three tasks are not necessarily performed on a single or integrated system. A workstation used for editing might be remote or detached from the system where formatting occurs. A document that is distributed for remote presentation might be formatted by the sender without knowing the devices or resources available for presentation. Many different document content architectures, document interchange data streams, and device data streams can be involved. In all cases, fonts must be referenced, graphic characters must be positioned, and character shapes must be presented. FOCA defines the necessary common and consistent font information that is needed for font processing.
