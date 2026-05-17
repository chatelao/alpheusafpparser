# Granular Test Coverage - FOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| FOCA-1-001 | This chapter provides a brief overview of Presentation Architecture. | ✅ |
| FOCA-1-002 | Figure 1 shows today’s presentation environment. | ✅ |
| FOCA-1-003 | Figure 1. Presentation Environment.** The environment is a coordinated set of services architected to meet the presentation needs of today’s applications. | ✅ |
| FOCA-1-004 | Document Creation Services**: import/export, edit/revise, format, scan, transform | ✅ |
| FOCA-1-005 | Document Archiving Services**: store, retrieve, index, search, extract | ✅ |
| FOCA-1-006 | Document Viewing Services**: browse, navigate, search, clip, annotate, tag | ✅ |
| FOCA-1-007 | Document Printing Services**: print, submit, distribute, manage, print, finish | ✅ |
| FOCA-1-008 | The ability to create, store, retrieve, view, and print data in presentation formats friendly to people is a key requirement in almost every application of computers and information processing. This requirement is becoming increasingly difficult to meet because of the number of applications, servers, and devices that must interoperate to satisfy today’s presentation needs. | ✅ |
| FOCA-1-009 | The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP presentation architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP presentation architectures provide structures that support object-oriented models and client/server environments. | ✅ |
| FOCA-1-010 | AFP presentation architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP presentation architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ✅ |
| FOCA-1-011 | AFP presentation architectures provide the means for representing documents in a data format that is independent of the methods used to capture or create them. Documents can contain combinations of text, image, graphics, and bar code objects in device-independent and resolution-independent formats. Documents can contain fonts, overlays, and other resource objects required at presentation time to present the data properly. Finally, documents can contain resource objects, such as a document index and tagging elements supporting the search and navigation of document data, for a variety of application purposes. | ✅ |
| FOCA-1-012 | The presentation architecture components are divided into two major categories: data streams and objects. | ✅ |
| FOCA-1-013 | A data stream is a continuous ordered stream of data elements and objects conforming to a given format. Application programs can generate data streams destined for a presentation service, archive library, presentation device, or another application program. The strategic presentation data stream architectures are: | ✅ |
| FOCA-1-014 | Mixed Object Document Content Architecture (MO:DCA)** | ✅ |
| FOCA-1-015 | Intelligent Printer Data Stream (IPDS) Architecture** | ✅ |
| FOCA-1-016 | The MO:DCA architecture defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. Documents defined in the MO:DCA format can be archived in a database, then later retrieved, viewed, annotated, and printed in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them. | ✅ |
| FOCA-1-017 | The IPDS architecture defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers. | ✅ |
| FOCA-1-018 | Figure 2 shows a system model relating MO:DCA and IPDS data streams to the presentation environment previously described. Also shown in the model are the object content architectures that apply to all levels of presentation processing in a system. | ✅ |
| FOCA-1-019 | Figure 2. Presentation Model.** This diagram shows the major components in a presentation system and their use of data stream and object architectures. | ✅ |
| FOCA-1-020 | Data Objects**: Text, Image, Graphics, Bar Codes, Object Containers | ✅ |
| FOCA-1-021 | Resource Objects**: Fonts, Overlays, Page Segments, Form Definition, Color Management Resources, Color Table, Document Index, Metadata | ✅ |
| FOCA-1-022 | Documents can be made up of different kinds of data, such as text, graphics, image, and bar code. Object content architectures describe the structure and content of each type of data format that can exist in a document or appear in a data stream. Objects can be either data objects or resource objects. | ✅ |
| FOCA-1-023 | A **data object** contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data. | ✅ |
| FOCA-1-024 | A **resource object** is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them. | ✅ |
| FOCA-1-025 | All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects, that can be individually positioned and manipulated to meet the needs of the presentation application. | ✅ |
| FOCA-1-026 | The AFPC-defined object content architectures are: | ✅ |
| FOCA-1-027 | Presentation Text Object Content Architecture (PTOCA)**: A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition. | ✅ |
| FOCA-1-028 | Image Object Content Architecture (IOCA)**: A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition. | ✅ |
| FOCA-1-029 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA)**: A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition. | ✅ |
| FOCA-1-030 | Bar Code Object Content Architecture (BCOCA)**: A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition. | ✅ |
| FOCA-1-031 | Font Object Content Architecture (FOCA)**: A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document. | ✅ |
| FOCA-1-032 | Color Management Object Content Architecture (CMOCA)**: A resource architecture used to carry the color management information required to render presentation data. | ✅ |
| FOCA-1-033 | Metadata Object Content Architecture (MOCA)**: A resource architecture used to carry metadata in an AFP environment. | ✅ |
| FOCA-1-034 | The MO:DCA and IPDS architectures also support data objects that are not defined by AFPC object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures. | ✅ |
| FOCA-1-035 | In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document. | ✅ |
| FOCA-1-036 | Figure 3 shows an example of an all-points-addressable page composed of multiple presentation objects. | ✅ |
| FOCA-1-037 | Figure 3. Presentation Page.** This is an example of a mixed-object page that can be composed in a device-independent MO:DCA format and can be printed on an IPDS printer. | ✅ |
| FOCA-2-001 | This chapter presents introductory information about fonts, digitized font structures, and how digitized fonts are used in information processing. The information in this chapter will aid the reader in understanding the Font Object Content Architecture (FOCA), but is not a required component of the architecture. | ✅ |
| FOCA-2-002 | A font is a set of graphic characters with similar design characteristics; that is, a font is a designer's concept of how a set of graphic characters should appear. Graphic characters (*glyphs* is the term used by the ISO/IEC 9541 Font Information Interchange standard) are letters, numerals, punctuation marks, ideograms, or symbols that appear in text. | ✅ |
| FOCA-2-003 | Historically, a font was a collection of lead slugs containing the raised images of the characters. However, in electronic digital processing, the character images are digitized by transforming them into data or algorithms that are stored in a computer system. The character shape data is then used to display the images in the form of small dots on a presentation surface, for example, as picture elements (pels) on a display screen or as dots on a piece of paper. | ✅ |
| FOCA-2-004 | Figure 4 shows how the pels or dots form a pattern that can be interpreted as a graphic character; it shows the letter h from a typeface family with the design characteristic of serifs. Such a graphic character could be presented on a presentation surface through any of several available presentation technologies, for example, wire matrix impact, laser fusion, ink jet transfer, or display phosphor illumination, all of which are able to approximate the form, style, and appearance intended by the font designer. The data or algorithm used to represent the character shape in digitized form on a computer system can be quite different from the pels or dots that appear on the presentation surface. The character shapes can be digitized as a matrix of binary bits, as a series of vectors, or as a set of second or third order polynomial equations. | ✅ |
| FOCA-2-005 | Figure 4. Representation of a Graphic Character** | ✅ |
| FOCA-2-006 | A digitized font contains not only the character shape information, but all of the information needed by an information processing system to format a character string and render the character shapes with a given presentation technology. Digitized fonts contain descriptive information used to identify the specific font resource, metric information used to position the character shapes, and the shape information used to render the character shapes. | ✅ |
| FOCA-2-007 | In theory, a font could be defined to be the universal set of all the world's characters having the same design characteristics. In practice however, a digitized font resource contains a bounded set of characters having the same design characteristics. Further, multiple digitized font resources can exist having the same design characteristics, but each containing a different bounded set of characters. The set of characters contained in a font is determined by the font provider, depending on the language requirements for document processing. | ✅ |
| FOCA-2-008 | Each graphic character in a font has a unique name, which is called the graphic character identifier. Each graphic character in a document data stream has a unique code, which is called a code point. A code page is a table that associates graphic character identifiers with code points (see Figure 5). | ✅ |
| FOCA-2-009 | Figure 5. Relationship of Code Points to Graphic Characters** | ✅ |
| FOCA-2-010 | 1.  **Document Data Stream**: Code Page ID, Font Resource ID, Code Points | ✅ |
| FOCA-2-011 | 2.  **Code Page Resource**: Code Page ID, Mapping (Code Point to Character ID) | ✅ |
| FOCA-2-012 | 3.  **Font Resource**: Font Resource ID, Character ID, Metrics, Shape | ✅ |
| FOCA-2-013 | 4.  **Process**: Format Process, Print Process | ✅ |
| FOCA-2-014 | 5.  **Output Document** | ✅ |
| FOCA-2-015 | When documents are formatted and presented, the graphic characters in the document, which are usually encoded as code points of one or two bytes for each graphic character, are associated with the character information in a font through the code page. For an example, presentation of the word “the” requires the character shapes for the graphic characters t, h, and e. Each code point in the code page maps to a character identifier, which points to the font information, including metrics and shape. One code page is often sufficient to represent all the graphic characters in the document, although multiple code pages can be used. | ✅ |
| FOCA-2-016 | Fonts are either monospaced, in which the horizontal space occupied by different characters is the same, or typographic, that is, proportionally spaced fonts, in which the character widths vary. | ✅ |
| FOCA-2-017 | In typographic fonts, the font designer has adjusted the distance from one character to another to improve the visual flow of text by eliminating excess space. Adjusting space this way improves the readability and the appearance of a document. | ✅ |
| FOCA-2-018 | For comparison, the letter i and the letter m are printed below 15 times in both a monospaced font and a typographic font. Note that in the monospaced font, both strings of characters are the same length, but in the typographic font, the string of m's is longer than the string of i's. | ✅ |
| FOCA-2-019 | Monospaced**: | ✅ |
| FOCA-2-020 | Typographic**: | ✅ |
| FOCA-2-021 | The term **font resource** refers to the collection of code pages and other font information such as FOCA font parameters that are stored in the font library and used by data processing systems. The parameters define the attributes for a particular font. A parameter includes a name by which it can be referenced and a value that defines the attribute. See Chapter 5, “FOCA Parameters”, for information about FOCA parameters, including their names, acceptable values, and usages. | ✅ |
| FOCA-2-022 | Font resources normally reside in system storage; and the aggregate of stored font information is often called the font library. Font resources can also be resident in a presentation device or control unit. A font resource can be all or only a part of the information pertaining to a particular font. | ✅ |
| FOCA-2-023 | A **font reference** is information that identifies a particular font resource. A font reference is embedded either in a document data stream or in an application program, which processes a document data stream and requests the use of a font resource. For example, a font reference could be the name of a font file or a list of font parameters. When font references are carried in an architected data stream or a program, they use the format, that is, the syntax, required by the document content architecture. | ✅ |
| FOCA-2-024 | Note:** Font references should not be confused with data objects, which use their own syntax when carried in architected data streams and use the data stream only as an envelope. | ✅ |
| FOCA-2-025 | FOCA supports using font references by defining a precise set of parameters that specify a font resource or describe the font resource attributes. Each implementing product chooses the set of font resources it will support, and the set of characters contained in each of those font resources. For consistency when interchanging and presenting documents, all receiving sites, that is, processing applications and presentation devices, must have access to the same or equivalent font resources. | ✅ |
| FOCA-2-026 | An AFP font resource consists of font information for a specific font family in one or more styles and sizes originally developed by IBM. When FOCA font parameters are used, they support a broad range of IBM encoding schemes as well as the specific encoding supported by AFP products. For example, AFP products support a variety of encoding schemes for code pages and specific codes, which meet the processing requirements of specific geographic, language, or application environments. These encoding schemes identify the graphic characters of a document by their meaning, for instance, open parenthesis, or by their shape, for instance, left parenthesis. | ✅ |
| FOCA-2-027 | The graphic characters in a font resource can be defined to support multiple code page encoding schemes, or they can correspond precisely to the code points of a specific code page encoding scheme. AFP font resources contain a large amount of font information, which allow products to access and process the resources in a variety of application environments. | ✅ |
| FOCA-2-028 | The graphic characters in a font can be mapped to any of the various types of code page encoding schemes. However, the set of graphic characters defined within a font must be equal to or greater than the set of graphic characters defined in the code page. This ensures that all graphic characters in the encoding scheme are supported by the font and that font information is available at document formatting or document presentation time. | ✅ |
| FOCA-2-029 | The following are the two types of AFP font resources: | ✅ |
| FOCA-2-030 | A system-level font resource** is a font from which document processing applications can obtain formatting information that is resolution independent and from which device service applications can obtain device specific presentation information. A system-level font resource normally resides within a font library, which provides a common source of font information for all applications or devices. | ✅ |
| FOCA-2-031 | A device-level font resource** is a device-specific font from which the presentation device, or family of presentation devices, can obtain the font information required to present the character images. A device-level font resource normally resides within a ROM cartridge (a cartridge containing read-only memory), a loadable file on a disk or on a tape, or a loadable file from a host system. | ✅ |
| FOCA-2-032 | Figure 6 shows that FOCA supports all transactions as the user selects graphic characters and FOCA products draw on both levels of resources to produce the output. | ✅ |
| FOCA-2-033 | Figure 6. FOCA Information Processing Environment** | ✅ |
| FOCA-2-034 | Digitized fonts are grouped (structured) according to the different function of the information that they provide. The following list identifies the parameter groups, the type of information provided, the function of the information, and the number of times, as a general practice, the information would occur in a typical digitized font resource: | ✅ |
| FOCA-2-035 | Font-description parameters**: Provide font-descriptive information for referencing, that is, identifying and describing, fonts. Defined once per font resource. | ✅ |
| FOCA-2-036 | Font-metric parameters**: Provide font-metric information that states measurements for positioning the font and each character in the font. Repeated for each rotation supported. | ✅ |
| FOCA-2-037 | Character-shape parameters**: Provide character-shape information for forming images of the character shapes on a presentation surface. Repeated for each shape representation technology supported. | ✅ |
| FOCA-2-038 | Character-mapping parameters**: Provide character-mapping information for associating character identifiers with code points on code pages. Repeated for each code page supported. | ✅ |
| FOCA-2-039 | Figure 7 shows an example of the organization of parameters in the library. | ✅ |
| FOCA-2-040 | Figure 7. Font Information in a Library** | ✅ |
| FOCA-2-041 | Font-Descriptive Information**: Resource Name, Typeface, Weight, etc. (One per resource) | ✅ |
| FOCA-2-042 | Font-Metric Information**: Rotation 1, Rotation 2, ... Rotation n (One for each rotation) | ✅ |
| FOCA-2-043 | Max Ascender, A-Space, etc. | ✅ |
| FOCA-2-044 | Character-Shape Information**: Technology 1, Technology 2, ... Technology n (One for each technology) | ✅ |
| FOCA-2-045 | Shape Map, Shape Data, etc. | ✅ |
| FOCA-2-046 | Character-Mapping Information**: Code Page 1, Code Page 2, ... Code Page n (One for each code page) | ✅ |
| FOCA-2-047 | Page Name, Code Map, etc. | ✅ |
| FOCA-2-048 | Font-descriptive information identifies and describes a font. The information includes, for example, resource name, family name, typeface name, weight, and supported sizes. The identification of a font resource for use by a system processor requires some or all of this information in the library to ensure access to the correct font. Often, the originator of a document can identify a specific font resource by simply stating the name. But, in large systems that have numerous fonts and in distributed networks where available fonts are not known, listing each desired parameter and letting the system locate a resource that corresponds to the description might provide more accurate font selection. FOCA provides the common parameter definitions necessary to match descriptive information in a font reference to the definitions in a font resource. | ✅ |
| FOCA-2-049 | Font-descriptive information should be grouped to identify and describe the font resource. See “Font-Description Parameters” for a description of each FOCA parameter that carries font-descriptive information. | ✅ |
| FOCA-2-050 | Font-metric information contains metrics, which is measurement information that defines the height, width, and space for a font or for each character in the font. Font-metric information also includes character-metric information. For example, a font resource might contain the information for the averages or maximums for all of the graphic characters as well as the measurements for each character. This information determines where a character will appear in a presentation space. | ✅ |
| FOCA-2-051 | Furthermore, the presentation of graphic characters in a top-to-bottom writing mode requires different metric information than for left-to-right writing mode. Therefore, it is necessary to provide multiple groups of metric information for the various writing modes supported by the font resource. The font-metric information in FOCA is grouped by character rotation (see “Character Rotation” for the relationship between character rotation and non-Latin writing systems). The metrics can be expressed in the pel resolution of the presentation device that is used to present the font information or in a form that is resolution independent and applicable to all presentation devices. | ✅ |
| FOCA-2-052 | Font-metric information must be defined for each rotation supported by the font resource. For good performance, the metric information should be grouped within a font resource. See “Font-Metric Parameters” for a description of each FOCA parameter that carries font-metric information and “Character-Metric Parameters” for each FOCA parameter that carries character-metric information. | ✅ |
| FOCA-2-053 | Character-shape information enables the presentation device to create the image of the graphic character. The representation of character shapes in a font resource can be repeated for each supported shape representation technology (bitmaps, vectors, and conic sections). In theory, multiple device-specific representation technologies can be supported by a single font resource (for example, a font resource might include shape data for 240 pel, 300 pel and 600 pel bitmap representations), or a representation technology can be transformed from one to another (for example, vectors might be converted to 240-pel and 300-pel bitmap representations). In practice, most font resources contain the character shape information for a single representation technology. | ✅ |
| FOCA-2-054 | Character-shape information can be defined once for all rotations supported by the font-metric information, or could be repeated in different font resources for each rotation. For good performance, the shape information should be defined once in a single font resource for all supported rotations. See “Character-Shape Parameters” for a description of each FOCA parameter that carries character-shape information. | ✅ |
| FOCA-2-055 | Character-mapping information provides for the association of code points in a document to the appropriate graphic characters in a font. Character mapping requires knowledge about the techniques of document encoding, the identification of font characters, and the mapping of document character codes to font character identifiers. | ✅ |
| FOCA-2-056 | The characters in a font resource can be defined to support the character content of one or more code pages. Using code pages minimizes processor storage and maximizes the document processing efficiency. That is, when the characters in a font resource are defined independently of the code page intended for support, many different documents, encoded with different code pages, can be presented using a single font resource. | ✅ |
| FOCA-2-057 | Either a font resource contains the character identifiers and code points for mapping to each supported code page, or the code page definitions are stored separately from the font resource. If the first case is true and the font resource includes code page mappings, those mappings should be stored independently from the font-metric information and character-shape information (for each technology), except for character identifiers. The character-mapping information should be available for each supported code page. See “Character-Mapping Parameters” for a description of each FOCA parameter that carries character-mapping information. | ✅ |
| FOCA-2-058 | Digitized fonts are designed to be used by an application program that processes the font resource and creates output that the user can read when presented by a presentation device. The font resource must be produced and stored in the system before an application program can access and process it. The following sections describe the relationship of a font architecture to font production, font storage, and font processing. | ✅ |
| FOCA-2-059 | An organization that specializes in font development using sophisticated design tools creates a new font family. | ✅ |
| FOCA-2-060 | An organization creates a special font for a collection of symbols to distribute to its branch offices. | ✅ |
| FOCA-2-061 | An individual modifies a few graphic characters in an existing font to make a company slogan fit on one line of a wallet-sized card. | ✅ |
| FOCA-2-062 | A developer of computers or text systems adapts an existing font to meet a customer's requirement for better performance. | ✅ |
| FOCA-2-063 | A computer assembles some font components as they are needed to create a document that includes both text and graphics. | ✅ |
| FOCA-2-064 | Designs character shapes | ✅ |
| FOCA-2-065 | Converts the character shapes to a digital format and includes information for the presentation technology, such as bitmaps, vectors, or conic sections | ✅ |
| FOCA-2-066 | Defines the parameter values, such as height, width, and escapement point, for each character shape | ✅ |
| FOCA-2-067 | Assigns appropriate descriptive and identifying information, such as a graphic character identifier, to each character shape | ✅ |
| FOCA-2-068 | Creates any other information required by the application program | ✅ |
| FOCA-2-069 | FOCA supports font production by providing definitions of the font parameters and by describing how they are used. | ✅ |
| FOCA-2-070 | Data availability**: Is the descriptive and measurement information available both to the applications that generate and format documents as well as to the devices that present the output? | ✅ |
| FOCA-2-071 | Data ownership**: Is permission required to share or distribute this font information? | ✅ |
| FOCA-2-072 | To create font resources that are more accessible for application use, the data can be stored as logical units rather than as a single, comprehensive font file. Logical units permit each system component or application program to access only those font elements they require, and might have the additional benefit of minimizing costly license payments that apply to other logical elements that the application doesn't require. | ✅ |
| FOCA-2-073 | Network-distributed libraries that have database access communicate over the network. | ✅ |
| FOCA-2-074 | Host-system libraries that have shared data access or a resource-management program interface to any attached device or workstation application. | ✅ |
| FOCA-2-075 | Workstation-resident libraries or collections of files shared by users run in that or another workstation. | ✅ |
| FOCA-2-076 | Data compiled or linked to a certain editor has access only to the modules within that application program. | ✅ |
| FOCA-2-077 | Data resident within and available only to a specific presentation device is made available to the device by down-line loading or by exchangeable read-only storage. | ✅ |
| FOCA-2-078 | FOCA supports font storage by precisely defining the set of font parameters required in each AFP environment so applications can implement FOCA by using only those logical structures or elements they require. | ✅ |
| FOCA-2-079 | Editing**: Creating and then modifying a data stream called a revisable document. | ✅ |
| FOCA-2-080 | Formatting**: Using the revisable document to create a data stream, such as MO:DCA, called a presentation document. | ✅ |
| FOCA-2-081 | Presenting**: Using the presentation document to create an output document that humans can read, such as a printed page. | ✅ |
| FOCA-2-082 | Some systems require the user to request formatting as a separate step after editing, while other applications, such as a WYSIWYG editor, combine these tasks into what appears to be a single process. However, without a command from the user, WYSIWYG systems also perform each task individually before presenting the results. | ✅ |
| FOCA-2-083 | Personal computers with a character display and an all-points-addressable (APA), dot-matrix printer | ✅ |
| FOCA-2-084 | Office workstations with a WYSIWYG editor, display, and an APA laser printer | ✅ |
| FOCA-2-085 | Graphic workstations with a high-resolution color display and software applications for rotating and scaling text to annotate figures | ✅ |
| FOCA-2-086 | Publishing workstations with preview devices that permit low-quality, fast-output preview of output and with software applications for page layout and composition | ✅ |
| FOCA-2-087 | Workstations in a network with distributed storage and distributed print systems | ✅ |
| FOCA-2-088 | A processing system uses different types of font resource information when performing each of the three tasks. Figure 8 shows the tasks and indicates which type of information is used for each during font processing. | ✅ |
| FOCA-2-089 | Figure 8. Font Resource Requirements for Font Processing Tasks** | ✅ |
| FOCA-2-090 | 1.  **User Input** (User Reference Manual) | ✅ |
| FOCA-2-091 | 2.  **Editing** (Requires Font-Descriptive information) | ✅ |
| FOCA-2-092 | 3.  **Revisable Document** | ✅ |
| FOCA-2-093 | 4.  **Formatting** (Requires Font-Metrics and Character-Metrics information) | ✅ |
| FOCA-2-094 | 5.  **Presentation Document** | ✅ |
| FOCA-2-095 | 6.  **Presenting** (Requires Character-Shape information) | ✅ |
| FOCA-2-096 | 7.  **Output Document** | ✅ |
| FOCA-2-097 | Editing is the task of creating or modifying the data stream that is the source for producing a document. Editing is performed by an application program or editor. The editor processes the font references, which must be consistent with the syntax specified by the document content architecture. The references use the FOCA parameters that provide font-descriptive information, that is, the references identify or describe the font. The references are also used to perform character mapping. | ✅ |
| FOCA-2-098 | Formatting is the task of determining where information is to appear in a presentation space. The formatter uses the FOCA parameters that contain font-metric information and character-metric information and position the graphic characters on the presentation surface. | ✅ |
| FOCA-2-099 | The parameters include information about line and page breaks and how text flows around graphic and image objects in the document. Fonts provide only part of the information needed for character positioning. The formatter not only uses font parameters, but it also uses information contained in the document, such as paragraph breaks and other formatting instructions. | ✅ |
| FOCA-2-100 | FOCA defines metrics for all characters in a font. If a development team creates or acquires a font resource for a presentation product, all formatters supported for that product must have access to the metrics for each character in that font. | ✅ |
| FOCA-2-101 | Presenting is the task of transforming the formatted information to a visible form on a presentation surface. The FOCA parameters that support presenting the document use character-shape information that images the character at its correct position. | ✅ |
| FOCA-2-102 | The task of presenting creates the character shapes on a presentation surface at the position determined by the formatter. The presentation is performed by a hardware device, but it might be supported by software and hardware processes that provide the required position and shape information. The character-shape information is used only by the presentation device. Character-metric information used when presenting the font must correspond to the character-metric information used by the formatter. | ✅ |
| FOCA-2-103 | FOCA supports presenting character shapes by providing support for device-specific techniques of character representation. FOCA also permits font producers and product implementers to make use of more generic representation technologies. | ✅ |
| FOCA-2-104 | The editing, formatting, and presenting tasks all use the FOCA font-descriptive information. During editing, the editor identifies (references) the font resource; during formatting, the formatter uses that font resource to locate and position the graphic characters, and during presenting, the presentation device uses that font resource to create the character shape with the proper position and metric information. The references can specify a specific font resource name or provide descriptive information about the font. | ✅ |
| FOCA-2-105 | All three tasks are not necessarily performed on a single or integrated system. A workstation used for editing might be remote or detached from the system where formatting occurs. A document that is distributed for remote presentation might be formatted by the sender without knowing the devices or resources available for presentation. Many different document content architectures, document interchange data streams, and device data streams can be involved. In all cases, fonts must be referenced, graphic characters must be positioned, and character shapes must be presented. FOCA defines the necessary common and consistent font information that is needed for font processing. | ✅ |
| FOCA-3-001 | When you create a document, you specify the font or fonts to be used whenever the document is presented. The application programs that process the document use the font references in the document to locate the required font resources for document formatting and presentation. System and presentation device libraries provide font resources, which have assigned names that must be associated with the font references contained in a document. The association of font references to font resources is not necessarily a one-to-one association based only on the name. The principal method of referencing fonts is by listing the applicable font-description parameters. | ✅ |
| FOCA-3-002 | To enable text processing systems to locate and use the appropriate font resources, the font references must be made in a clear, consistent manner across all the systems, applications, and data streams that use the font resources. Then, if a document containing the font references is sent to other locations, the processors at those receiving locations can use the distinguishing characteristics or attributes to find the required font resource (perhaps filed under a different name), or to select an appropriate alternative. The following sections of this chapter describe: | ✅ |
| FOCA-3-003 | Understanding the font reference model | ✅ |
| FOCA-3-004 | Identifying fonts | ✅ |
| FOCA-3-005 | Selecting and substituting fonts | ✅ |
| FOCA-3-006 | Many components of a processing system interact to produce the final presentation of a document. The actual flow of the data stream through the system depends on both the configuration of the specific system and the intent or design of the user. Figure 9 illustrates the general flow of a document from creation to presentation. The figure assumes that the system, including the font resources, are in place when the user initiates a document. The sections following Figure 9 provide more detail for some of the separate transactions within the figure. | ✅ |
| FOCA-3-007 | Figure 9. Font Reference Model General Flow** | ✅ |
| FOCA-3-008 | User Input**: Create Font References through a User Interface | ✅ |
| FOCA-3-009 | Document Editing**: Produces Revisable Document | ✅ |
| FOCA-3-010 | Font Services**: Font Queries, System-Level Font Resources | ✅ |
| FOCA-3-011 | Document Formatting**: Produces Presentation Document | ✅ |
| FOCA-3-012 | Document Presentation Services**: Produces Device Data Stream | ✅ |
| FOCA-3-013 | Document Presenting**: Presentation Device or Device Controller, Device-level Font Resources | ✅ |
| FOCA-3-014 | Output Document** | ✅ |
| FOCA-3-015 | A user interacts with a text or graphics editor to produce a document that is in a revisable form and contains references to the desired fonts, as shown in Figure 10. | ✅ |
| FOCA-3-016 | Figure 10. Creation of a Document** | ✅ |
| FOCA-3-017 | The user enters the references through the editor's user interface, which might present a list of the names of the available fonts or a list of the characteristic font attributes that enable the processing system to select a font. The document data stream produced by the editor can specify fonts in one of the following ways: | ✅ |
| FOCA-3-018 | Specifies the requested font by name | ✅ |
| FOCA-3-019 | Maps the font attributes selected by the user to a specific font name | ✅ |
| FOCA-3-020 | Reproduces the font attributes of the requested font from a separately stored list of attributes specified by the user | ✅ |
| FOCA-3-021 | Reproduces the font attributes of the requested font from the font resource specified by the user | ✅ |
| FOCA-3-022 | Because editing the document creates the font references and does not include formatting the document, the user needs to record only the intended font as input (for example, 10 point IBM Sonoran Serif italic). | ✅ |
| FOCA-3-023 | The application program or editor might provide the user with a list of available fonts or locate an available font that corresponds to the font information provided. To do this, as shown in Figure 11, an editor might use an interface that permits inquiries to a font services facility concerning the font resources available to the processing system. Another method might be for the editor to directly access the font resource information in system storage. | ✅ |
| FOCA-3-024 | Figure 11. Editor Determination of Font Availability** | ✅ |
| FOCA-3-025 | The font resources available to a system need not be physically resident on the user's workstation (distributed data base) or physically available in the form required for presentation (logical fonts or transformable resources). The editor queries font services about the availability of font resources. | ✅ |
| FOCA-3-026 | The font services facility responds to editor queries and provides font information, as shown in Figure 12, by accessing system-level font resources available in the font library. The font references that the font services facility uses are different in form and content from those required by editing applications. | ✅ |
| FOCA-3-027 | Figure 12. Font Services Access to Font Information** | ✅ |
| FOCA-3-028 | During formatting, the processing system must interpret the font references contained in the revisable document, locate the required font-metric information, and produce a formatted, final-form document. To do so, as shown in Figure 13, the formatter might use an interface that permits inquiries to a font services facility concerning the font resources available to the processing system. The formatter must generate a query to the font services facility by providing an appropriate font reference (which might be different from that issued during editing), and it will receive a response to the query. | ✅ |
| FOCA-3-029 | Figure 13. Formatter Access to Measurement Information** | ✅ |
| FOCA-3-030 | The presentation document produced will contain references to the font resources used when formatting, and will often be more specific than those found in the revisable document. If the presentation document is sent to another location or system where the specified font resource is not available, it might be necessary to substitute another font for presentation of the document. Thus, font references contained in a presentation document should retain the user intent provided in the revisable document. | ✅ |
| FOCA-3-031 | The presentation services provide several font-related functions that might include: | ✅ |
| FOCA-3-032 | Accessing and transforming the font information required by a device | ✅ |
| FOCA-3-033 | Down loading font information to a presentation device | ✅ |
| FOCA-3-034 | Determining available resident font support in the device | ✅ |
| FOCA-3-035 | Substituting one font resource for another | ✅ |
| FOCA-3-036 | When processing presentation information, the processing system, as shown in Figure 14, must generate a query to the font services facility by providing an appropriate font reference. The font services facility responds by identifying the available font resources or the font and shape information that corresponds to the referenced font. | ✅ |
| FOCA-3-037 | Figure 14. Presentation Services Access to Font Information** | ✅ |
| FOCA-3-038 | When processing presentation information, the processing system might also perform a query to the attached presentation devices to determine which resident fonts are available on the device, and it will receive font references that identify those fonts. The data stream for the device produced by the presentation services must contain references to the device-specific font resources needed to present the document, and it might include the required font-metric and character-shape information. Because the data stream for the device must contain specific references to device-specific font resources, the content and format of the font references will often be more specific than those required in the revisable or presentation document references. | ✅ |
| FOCA-3-039 | Each of the previously-defined process steps involves the use of font references to font resources. Each reference might require different information in different formats, and the different font resources referenced might be identified by different name formats. To fully support font referencing, the following items must be defined: | ✅ |
| FOCA-3-040 | System-level identifying characteristics | ✅ |
| FOCA-3-041 | Device-level identifying characteristics | ✅ |
| FOCA-3-042 | User input font reference | ✅ |
| FOCA-3-043 | Revisable document font reference | ✅ |
| FOCA-3-044 | Presentation document font reference | ✅ |
| FOCA-3-045 | Device data stream font reference | ✅ |
| FOCA-3-046 | The general requirements for identifying font resources and referencing font resources are similar, although the specific requirements will vary by the type of installation, the type of implementation, and the point in the process. The specific content and format of a font resource name is defined by the implementing product specifications. When planning to identify font resources, the following factors must be considered: | ✅ |
| FOCA-3-047 | A font reference might be a list of font attributes, or it might be a system file name, a member name, or a resource-management name that is mapped to a system file name or member name. | ✅ |
| FOCA-3-048 | More than one font resource can correspond to a single font reference. | ✅ |
| FOCA-3-049 | More than one font reference can correspond to a single font resource. | ✅ |
| FOCA-3-050 | If a system uses raster font resources, different font resources might be necessary for each presentation device and each presentation size. | ✅ |
| FOCA-3-051 | If a system uses outline font resources, one font resource might provide the data for multiple font sizes, typeface variations, and presentation devices. | ✅ |
| FOCA-3-052 | The list of characters in a font resource might not match the list of characters in a code page. | ✅ |
| FOCA-3-053 | A user's intent should be distinguishable from system-default or architecture-default values. | ✅ |
| FOCA-3-054 | A user's font references should be as device-independent as possible to permit document distribution to a variety of sites and to permit document presentation on a variety of devices. | ✅ |
| FOCA-3-055 | Fonts can be identified in different ways. One example is by using the descriptive parameters used in the Map Coded Font structured field in MO:DCA. Another example is by using the Global Resource Identifier, GRID, as used in MO:DCA and IPDS. Refer to the *Mixed Object Document Content Architecture Reference* and the *Intelligent Printer Data Stream Reference*. | ✅ |
| FOCA-3-056 | The specific content and format of a font reference is defined by the appropriate document content, device service, or interface architecture. The following information is provided as a guideline for such use. | ✅ |
| FOCA-3-057 | A system-level font resource is generally designed to be shared across multiple applications and presentation devices in support of multiple document and presentation data streams. System-level font resources will contain the metric and shape information for a large number of graphic characters, identifying them by unique global identifiers, independent of any single document encoding technique. The set of attributes used to identify a system-level font resource is more extensive than those used to identify a device-level font resource because of the metric and shape information used by editor, formatter, and presentation processes. | ✅ |
| FOCA-3-058 | The following table identifies the AFP parameters and ISO properties (properties is the term used by the ISO/IEC 9541 Font Information Interchange standard) that should appear in a system-level font resource to support font referencing. Because locating a desired font resource requires matching the parameters of a font reference to corresponding parameters in the available font resources, this set of parameters represent the union of the parameters that should appear in any of the supported font references. | ✅ |
| FOCA-3-059 | resource name | fontname | ✅ |
| FOCA-3-060 | function set code | standardversion | ✅ |
| FOCA-3-061 | typeface name | typeface | ✅ |
| FOCA-3-062 | design source | dsnsource | ✅ |
| FOCA-3-063 | data source | datasource | ✅ |
| FOCA-3-064 | family name | fontfamily | ✅ |
| FOCA-3-065 | posture class | posture | ✅ |
| FOCA-3-066 | weight class | weight | ✅ |
| FOCA-3-067 | width class | propwidth | ✅ |
| FOCA-3-068 | primary GCSGID | incglyphcols | ✅ |
| FOCA-3-069 | nominal vertical font size | dsnsize | ✅ |
| FOCA-3-070 | minimum vertical font size | minsize | ✅ |
| FOCA-3-071 | maximum vertical font size | maxsize | ✅ |
| FOCA-3-072 | nominal horizontal font size | (not supported) | ✅ |
| FOCA-3-073 | minimum horizontal font size | minanascale | ✅ |
| FOCA-3-074 | maximum horizontal font size | maxanascale | ✅ |
| FOCA-3-075 | font classification | dsngroup | ✅ |
| FOCA-3-076 | structure class | structure | ✅ |
| FOCA-3-077 | font type flags | escclass | ✅ |
| FOCA-3-078 | character rotation | nomescdir | ✅ |
| FOCA-3-079 | average escapement | avgescx/y | ✅ |
| FOCA-3-080 | average lowercase escapement | avglcescx/y | ✅ |
| FOCA-3-081 | average capital escapement | avgcapescx/y | ✅ |
| FOCA-3-082 | figure space increment | tabescx/y | ✅ |
| FOCA-3-083 | maximum baseline extent | maxfontext | ✅ |
| FOCA-3-084 | The various methods of document encoding are addressed by a separate collection of code page (encoding vector) resources that map the supported code points to the font glyph identifiers. Differences in device resolution and imaging technology are addressed by the presentation process that transforms a system-level font resource and an appropriate code page resource into a device-level font resource. | ✅ |
| FOCA-3-085 | A device-level font resource is generally designed to be used by one device or family of related devices and a single document encoding technique. Device-level font resources should contain the metric and shape information for a single code page (encoding vector) and will most often identify the metric and shape information by the code point rather than by an independent glyph identifier (though it can use an attached mapping table to associate the code point to the glyph ID). The set of attributes used to identify a device-level font resource is less extensive than that used to identify a system-level font resource because the metric and shape information is used only by the presentation processes. | ✅ |
| FOCA-3-086 | The following table identifies the AFP parameters and ISO properties that should appear in a device-level font resource to support font referencing. | ✅ |
| FOCA-3-087 | resource name | fontname | ✅ |
| FOCA-3-088 | design source | dsnsource | ✅ |
| FOCA-3-089 | data source | datasource | ✅ |
| FOCA-3-090 | family name | fontfamily | ✅ |
| FOCA-3-091 | posture class | posture | ✅ |
| FOCA-3-092 | weight class | weight | ✅ |
| FOCA-3-093 | width class | propwidth | ✅ |
| FOCA-3-094 | primary GCSGID | incglyphcols | ✅ |
| FOCA-3-095 | primary CPGID | (not supported) | ✅ |
| FOCA-3-096 | nominal vertical font size | dsnsize | ✅ |
| FOCA-3-097 | minimum vertical font size | minsize | ✅ |
| FOCA-3-098 | maximum vertical font size | maxsize | ✅ |
| FOCA-3-099 | nominal horizontal font size | (not supported) | ✅ |
| FOCA-3-100 | minimum horizontal font size | minanascale | ✅ |
| FOCA-3-101 | maximum horizontal font size | maxanascale | ✅ |
| FOCA-3-102 | font classification | dsngroup | ✅ |
| FOCA-3-103 | structure class | structure | ✅ |
| FOCA-3-104 | font type flags | escclass | ✅ |
| FOCA-3-105 | character rotation | nomescdir | ✅ |
| FOCA-3-106 | average escapement | avgescx/y | ✅ |
| FOCA-3-107 | average lowercase escapement | avglcescx/y | ✅ |
| FOCA-3-108 | average capital escapement | avgcapescx/y | ✅ |
| FOCA-3-109 | figure space increment | tabescx/y | ✅ |
| FOCA-3-110 | maximum baseline extent | maxfontext | ✅ |
| FOCA-3-111 | pattern technology identifier | glyphshapetech | ✅ |
| FOCA-3-112 | A user-input font reference is generally designed to be used by people who might not be knowledgeable about font technology, but who wish to use different fonts in their document. Processing applications have a high degree of freedom in how they can present information to users and can hide most of the technical detail from the users, mapping the user's input to the specifics required by the data stream. The user-input font reference can be contained in a reference book containing examples and the corresponding font resource names that users type at their workstation. The font reference can be a workstation displayed list of available fonts from which users select the desired item, or it can be a series of pop-up pull-down menus from which users select various font characteristics (for example: bold, serif, 10 pt., italic) that are desired. | ✅ |
| FOCA-3-113 | Because the font resources available at any one workstation might not be available at all other workstations in a distributed environment, the user-input font reference should be independent of specific resource names and should focus instead on getting as much information as possible about the user's desired intent (for example, highlight this phrase with the bold version of the current font). Whatever technique is used for user input, the user's selection must be translatable or mappable into the revisable-document font reference. | ✅ |
| FOCA-3-114 | A revisable-document font reference is generally designed to focus on the user's intent for text appearance, with little or no emphasis on specific font resources or font metrics. The document can then be sent to any location, formatted using the best available font resource (that satisfies the user's intent), and printed or displayed for the recipient's use. | ✅ |
| FOCA-3-115 | Specification of the code page used for encoding the document is necessary at the revisable-document level, but the code page does not have to be linked to a font resource at this stage. The code page does not need to appear in the font reference (although it can if the document data stream architecture also uses the font reference to define the document encoding). | ✅ |
| FOCA-3-116 | The following table identifies the AFP parameters and ISO properties that should appear in a revisable document font reference. | ✅ |
| FOCA-3-117 | resource name | fontname | ✅ |
| FOCA-3-118 | design source | dsnsource | ✅ |
| FOCA-3-119 | family name | fontfamily | ✅ |
| FOCA-3-120 | posture class | posture | ✅ |
| FOCA-3-121 | weight class | weight | ✅ |
| FOCA-3-122 | width class | propwidth | ✅ |
| FOCA-3-123 | vertical font size | dsnsize | ✅ |
| FOCA-3-124 | font classification | dsngroup | ✅ |
| FOCA-3-125 | structure class | structure | ✅ |
| FOCA-3-126 | font type flags | escclass | ✅ |
| FOCA-3-127 | character rotation | nomescdir | ✅ |
| FOCA-3-128 | A presentation-document font reference is generally designed to focus on the formatter's intent for page layout, while recalling the user's intent for text appearance. The presentation-document font reference should contain all of the revisable-document font reference information along with enough additional information so as to identify the metrics used for page layout. The document can then be sent to any location, and the required font can be located, or an alternate font that closely approximates the metrics can be substituted (see “Font Selection and Substitution”). | ✅ |
| FOCA-3-129 | Specification of the glyph complement (graphic character set) that corresponds to the code page used for encoding the document is necessary at the presentation-document level, but the code page need not be linked to the font resource at this stage. | ✅ |
| FOCA-3-130 | resource name | fontname | ✅ |
| FOCA-3-131 | function set code | standardversion | ✅ |
| FOCA-3-132 | typeface name | typeface | ✅ |
| FOCA-3-133 | design source | dsnsource | ✅ |
| FOCA-3-134 | data source | datasource | ✅ |
| FOCA-3-135 | family name | fontfamily | ✅ |
| FOCA-3-136 | posture class | posture | ✅ |
| FOCA-3-137 | weight class | weight | ✅ |
| FOCA-3-138 | width class | propwidth | ✅ |
| FOCA-3-139 | primary GCSGID | incglyphcols | ✅ |
| FOCA-3-140 | vertical font size | dsnsize | ✅ |
| FOCA-3-141 | font classification | dsngroup | ✅ |
| FOCA-3-142 | structure class | structure | ✅ |
| FOCA-3-143 | font type flags | escclass | ✅ |
| FOCA-3-144 | character rotation | nomescdir | ✅ |
| FOCA-3-145 | average escapement | avgescx/y | ✅ |
| FOCA-3-146 | average lowercase escapement | avglcescx/y | ✅ |
| FOCA-3-147 | average capital escapement | avgcapescx/y | ✅ |
| FOCA-3-148 | figure space increment | tabescx/y | ✅ |
| FOCA-3-149 | maximum baseline extent | maxfontext | ✅ |
| FOCA-3-150 | A device data stream font reference is generally designed to focus on the presentation process's intent for glyph shape rendering, while providing enough reference information to identify the required font information under many different device-specific font resource names. | ✅ |
| FOCA-3-151 | Specification of the glyph complement (graphic character set) and the corresponding code page used for encoding the document is necessary at the device data stream level. | ✅ |
| FOCA-3-152 | resource name | fontname | ✅ |
| FOCA-3-153 | design source | dsnsource | ✅ |
| FOCA-3-154 | data source | datasource | ✅ |
| FOCA-3-155 | family name | fontfamily | ✅ |
| FOCA-3-156 | posture class | posture | ✅ |
| FOCA-3-157 | weight class | weight | ✅ |
| FOCA-3-158 | width class | propwidth | ✅ |
| FOCA-3-159 | primary GCSGID | incglyphcols | ✅ |
| FOCA-3-160 | primary CPGID | (not supported) | ✅ |
| FOCA-3-161 | vertical font size | dsnsize | ✅ |
| FOCA-3-162 | font classification | dsngroup | ✅ |
| FOCA-3-163 | structure class | structure | ✅ |
| FOCA-3-164 | font type flags | escclass | ✅ |
| FOCA-3-165 | character rotation | nomescdir | ✅ |
| FOCA-3-166 | average escapement | avgescx/y | ✅ |
| FOCA-3-167 | average lowercase escapement | avglcescx/y | ✅ |
| FOCA-3-168 | average capital escapement | avgcapescx/y | ✅ |
| FOCA-3-169 | figure space increment | tabescx/y | ✅ |
| FOCA-3-170 | maximum baseline extent | maxfontext | ✅ |
| FOCA-3-171 | pattern technology identifier | glyphshapetech | ✅ |
| FOCA-3-172 | When an application process encounters a font reference, it is necessary for the process to locate a font resource that corresponds to that reference. **Font selection** is the process of locating a font resource that corresponds to the font reference or that satisfies all the information contained in the font reference. **Font substitution** is the process of locating an alternate font resource that approximates the font resource, because no font resource could be found that met the selection criteria. | ✅ |
| FOCA-3-173 | Font selection is most simply performed by having an exact match of a font resource name to a font reference that contains the font resource name. But, in a distributed processing environment, the number and combination of font resource names is without limit. The chances of an exact match for a font resource name diminishes as the number of users in a distributed environment increases. | ✅ |
| FOCA-3-174 | Font substitution can be as simple as using a system or device default font (for example, substituting fixed pitch Courier whenever the requested font cannot be found), but that is often disappointing to the user. Font selection or substitution is most effective when all of the identifying characteristics contained in a font reference are intelligently compared to the identifying characteristics contained in all of the available font resources, until an exact or best match is located. | ✅ |
| FOCA-3-175 | The specific data formats and processing steps required for intelligent font substitution is outside the scope of this architecture. However, the following sections provide guidelines for various selection and substitution situations. | ✅ |
| FOCA-3-176 | The document data stream should contain information about a user's intent for presentation. The success of font substitution is described in terms of fidelity. There are five classes of fidelity, described in order of increasing fidelity: | ✅ |
| FOCA-3-177 | Content fidelity**: Permits a user to identify the characters used in the document, or to identify a character that cannot be presented. | ✅ |
| FOCA-3-178 | Format fidelity**: Preserves line and cell identity. A user must be able to identify the basic parts of the logical document structure. | ✅ |
| FOCA-3-179 | Layout fidelity**: Maintains the integrity of line endings, column endings, and page endings. Maintains the relationship between areas. | ✅ |
| FOCA-3-180 | Appearance fidelity**: Requires a document, or area, to appear as specified by the user. Substitution of visually equivalent fonts is permitted. | ✅ |
| FOCA-3-181 | Absolute fidelity**: Requires a document to appear precisely as specified by the user. No substitution of fonts is permitted. | ✅ |
| FOCA-3-182 | The document-editing process provides the interface between the user input and the revisable document data stream output. The preferred method is to take the information provided by the user input and select an available font resource that satisfies the user's intent, and then copy the required font parameters from the selected font resource into the corresponding parameters of the revisable-document font reference. | ✅ |
| FOCA-3-183 | For the document editing process, the following default order should be used for font selection: | ✅ |
| FOCA-3-184 | 1.  **Content-level fidelity**: The selected font resource must contain the glyphs needed to render the code points contained in the document data stream (GCSGID). | ✅ |
| FOCA-3-185 | 2.  **Format- and layout-level fidelity**: Takes precedence over appearance-level fidelity. | ✅ |
| FOCA-3-186 | Vertical font size | ✅ |
| FOCA-3-187 | Escapement direction | ✅ |
| FOCA-3-188 | Escapement class | ✅ |
| FOCA-3-189 | 3.  **Appearance-level fidelity**: | ✅ |
| FOCA-3-190 | Structure class | ✅ |
| FOCA-3-191 | Posture class | ✅ |
| FOCA-3-192 | Weight class | ✅ |
| FOCA-3-193 | Width class | ✅ |
| FOCA-3-194 | Font classification | ✅ |
| FOCA-3-195 | Family name | ✅ |
| FOCA-3-196 | Design source | ✅ |
| FOCA-3-197 | The document-formatting process provides the interface between the revisable-document data stream input and the presentation-document data stream output. | ✅ |
| FOCA-3-198 | For the document formatting process, the following default order should be used for font selection (same as for document editing): | ✅ |
| FOCA-3-199 | 1.  **Content-level fidelity**: Selected font resource must contain required glyphs (GCSGID). | ✅ |
| FOCA-3-200 | 2.  **Format- and layout-level fidelity**: Vertical font size, Escapement direction, Escapement class. | ✅ |
| FOCA-3-201 | 3.  **Appearance-level fidelity**: Structure, Posture, Weight, Width classes, Font classification, Family name, Design source. | ✅ |
| FOCA-3-202 | The document-presentation process provides the interface between the presentation-document data stream input and the device data stream output. | ✅ |
| FOCA-3-203 | For the document-presentation process, the following default order should be used for font selection: | ✅ |
| FOCA-3-204 | 1.  **Content-level fidelity**: Selected font resource must contain required glyphs (GCSGID). | ✅ |
| FOCA-3-205 | 2.  **Format- and layout-level fidelities**: Takes precedence over appearance-level fidelity after the document has been formatted. | ✅ |
| FOCA-3-206 | Vertical font size | ✅ |
| FOCA-3-207 | Escapement direction | ✅ |
| FOCA-3-208 | Escapement class | ✅ |
| FOCA-3-209 | Maximum extents | ✅ |
| FOCA-3-210 | Average escapement (General, Lowercase, Capital) | ✅ |
| FOCA-3-211 | Tabular escapement (figure space increment) | ✅ |
| FOCA-3-212 | 3.  **Appearance-level fidelity**: Structure, Posture, Weight, Width classes, Font classification, Family name, Design source. | ✅ |
| FOCA-4-001 | The Font Object Content Architecture (FOCA) supports presentation of character shapes by defining their characteristics, which include Font-Description information for identifying the characters, Font-Metric information for positioning the characters, and Character-Shape information for presenting the character images. Presenting a graphic character on a presentation surface requires that you communicate this information clearly to rotate and position characters correctly on the physical or logical page. For example, you can: | ✅ |
| FOCA-4-002 | Rotate a physical page within a print system | ✅ |
| FOCA-4-003 | Rotate and move a logical page to new locations on a physical page | ✅ |
| FOCA-4-004 | Rotate and position character shapes anywhere within a logical page | ✅ |
| FOCA-4-005 | Note:** FOCA does not address the orientation of pages on devices, logical pages on a physical page, or lines of text on a logical page. | ✅ |
| FOCA-4-006 | By defining FOCA parameters, you enable the system to separate font information from physical and logical page information as the system identifies, positions, and presents characters. This chapter gives an overview of FOCA by defining the terms you need when you use FOCA parameters, including terms for: | ✅ |
| FOCA-4-007 | The character coordinate system, including units of measure and direction within the coordinate system | ✅ |
| FOCA-4-008 | Graphic characters | ✅ |
| FOCA-4-009 | Character-set metrics | ✅ |
| FOCA-4-010 | Design metrics | ✅ |
| FOCA-4-011 | The chapter concludes with some general recommendations when using FOCA to design fonts. | ✅ |
| FOCA-4-012 | FOCA positions character shapes within an orthogonal character coordinate system, as shown in Figure 15. | ✅ |
| FOCA-4-013 | Figure 15. Character Coordinate System** | ✅ |
| FOCA-4-014 | Origin (Character Reference Point)**: The point where the axes intersect. | ✅ |
| FOCA-4-015 | X-Axis (Baseline)**: Positive X direction is the Escapement Direction. | ✅ |
| FOCA-4-016 | Y-Axis**: Positive Y direction is 90° counterclockwise or 270° clockwise from the positive X direction. | ✅ |
| FOCA-4-017 | FOCA locates font or character measurements in this system as distances between points. All the positioning and rotating measurements of a character are relative to the origin; as a character is rotated, the character coordinate system does not rotate. | ✅ |
| FOCA-4-018 | Note:** The default escapement direction is left-to-right along the x-axis of the character coordinate system. | ✅ |
| FOCA-4-019 | A font designer specifies the type of measurements used in defining a font as either **fixed units**, such as inches or centimeters, or **relative units**, which are dimensionless. | ✅ |
| FOCA-4-020 | Fixed units apply only to a single or limited number of devices, while relative units allow a font to be used by multiple devices. If the designated font was designed using relative units, the system scales or proportions the font measurements to accommodate the presentation device. | ✅ |
| FOCA-4-021 | FOCA expresses relative units as a decimal fraction of a Unit-Em. A **Unit-Em** is a unit of one that corresponds to the height of the design space, which is also the nominal vertical font size (specified in the Nominal Vertical Font Size parameter). | ✅ |
| FOCA-4-022 | Figure 16. Relative Unit as the Unit-Em** | ✅ |
| FOCA-4-023 | In order to find the fixed value of any of the relative metrics expressed as fractions of a Unit-Em, the fraction must be multiplied by the Unit-Em point value (customarily 1/72 inch). | ✅ |
| FOCA-4-024 | A Unit-Base specifies a code that represents the length of the measurement base. | ✅ |
| FOCA-4-025 | Table 10. Unit-Base Values** | ✅ |
| FOCA-4-026 | 0 | Ten inches | ✅ |
| FOCA-4-027 | 1 | Ten centimeters | ✅ |
| FOCA-4-028 | 2 | Relative units (measurement base = one Unit-Em) | ✅ |
| FOCA-4-029 | 3–255 | Reserved | ✅ |
| FOCA-4-030 | The Units per Unit-Base specifies the number of units in the measurement base. Allowable values are from 1 to 32,767. A value of 1000 is usually taken for the Units per Unit-Base multiplier for relative metrics. | ✅ |
| FOCA-4-031 | `Units of Measure = measurement base / Units per Unit-Base` | ✅ |
| FOCA-4-032 | Unit-Base = 0 (10 inches) | ✅ |
| FOCA-4-033 | Units per Unit-Base = 2400 | ✅ |
| FOCA-4-034 | `10 / 2400 = 1/240 inch` | ✅ |
| FOCA-4-035 | Unit-Base = 2 (1 Unit-Em) | ✅ |
| FOCA-4-036 | Units per Unit-Base = 20 | ✅ |
| FOCA-4-037 | `1 / 20 = 0.05 Unit-Em` | ✅ |
| FOCA-4-038 | In FOCA, units of direction are specified as two integer values: degrees (0 to +359) and minutes (0 to +59). Increasing values indicate increasing clockwise directions. | ✅ |
| FOCA-4-039 | Figure 17. Directions** | ✅ |
| FOCA-4-040 | This section defines the terms and concepts used to describe individual graphic characters in FOCA. | ✅ |
| FOCA-4-041 | FOCA uses a concept in which each character shape is defined to be within a **character box**, which is a conceptual rectangle. Boxes can be **bounded** (no extra space, just touching the shape) or **unbounded** (extra space). FOCA typically uses bounded boxes. | ✅ |
| FOCA-4-042 | Figure 18. Bounded Character Box for the Latin Letter ‘h’** | ✅ |
| FOCA-4-043 | All characters in a font are aligned on an imaginary line called the **character baseline**, which corresponds to the x-axis of the character coordinate system. | ✅ |
| FOCA-4-044 | Figure 19. Character Baseline** | ✅ |
| FOCA-4-045 | The **character reference point** corresponds to the origin (0,0) of the character coordinate system. It coincides with the presentation position. | ✅ |
| FOCA-4-046 | Figure 21. Character Reference Point** | ✅ |
| FOCA-4-047 | The **character escapement point** is the point where the next character reference point is usually positioned. | ✅ |
| FOCA-4-048 | Figure 22. Character Escapement Point** | ✅ |
| FOCA-4-049 | A-space** is the distance from the character reference point to the least positive character coordinate system x-axis value of the character shape. | ✅ |
| FOCA-4-050 | Positive**: Reference point is before the leftmost edge of the box. | ✅ |
| FOCA-4-051 | Zero**: Reference point coincides with the leftmost edge. | ✅ |
| FOCA-4-052 | Negative**: Implementation technique for kerning (left kerning). | ✅ |
| FOCA-4-053 | Figure 23. A-space** | ✅ |
| FOCA-4-054 | B-space** is the distance between the character coordinate system x-axis values of the two extremities of a character shape (the width of a bounded box). | ✅ |
| FOCA-4-055 | Figure 24. B-space** | ✅ |
| FOCA-4-056 | C-space** is the distance from the character’s most positive x-axis coordinate value to the character escapement point. | ✅ |
| FOCA-4-057 | Positive**: Escapement point is after the right-hand edge. | ✅ |
| FOCA-4-058 | Zero**: Escapement point coincides with the right-hand edge. | ✅ |
| FOCA-4-059 | Negative**: Implementation technique for kerning (right kerning). | ✅ |
| FOCA-4-060 | Figure 25. C-space** | ✅ |
| FOCA-4-061 | The **character increment** is the algebraic sum of A-space, B-space, and C-space. It is the distance from the character reference point to the character escapement point. | ✅ |
| FOCA-4-062 | Figure 26. Character Increment** | ✅ |
| FOCA-4-063 | Kerning** reduces the spacing between adjacent characters so they overlap. This is done by making A-space or C-space negative. | ✅ |
| FOCA-4-064 | Figure 27. An Example of Kerning** | ✅ |
| FOCA-4-065 | Pair Kerning** allows adjusting the increment between specific pairs of characters. | ✅ |
| FOCA-4-066 | Ascender height** is the distance from the character baseline to the top of the character box (most positive y-axis value). | ✅ |
| FOCA-4-067 | Figure 28. Ascender Height** | ✅ |
| FOCA-4-068 | Descender depth** is the distance from the character baseline to the bottom of a character box (most negative y-axis value). | ✅ |
| FOCA-4-069 | Figure 29. Descender Depth** | ✅ |
| FOCA-4-070 | Baseline extent** is the space parallel to the character baseline that can be used to place characters (the y-axis height of the box). | ✅ |
| FOCA-4-071 | Figure 32. Baseline Extent: Character on the Baseline** | ✅ |
| FOCA-4-072 | The **baseline offset** is the perpendicular distance from the character baseline to the top of a character box. | ✅ |
| FOCA-4-073 | The **slope** is the posture (incline) of the main strokes, measured clockwise from the vertical. Upright is usually 0°; italics is usually 17.5°. | ✅ |
| FOCA-4-074 | Figure 34. Slope 17.5°** | ✅ |
| FOCA-4-075 | This section defines terms that apply to the entire font. | ✅ |
| FOCA-4-076 | The **vertical size** (also known as body size, point size, em-height) represents the nominal baseline-to-baseline increment, including the designer's recommendation for internal leading. | ✅ |
| FOCA-4-077 | Figure 35. An Illustration of Vertical Size and Internal Leading** | ✅ |
| FOCA-4-078 | The size of a uniformly-spaced font, measured parallel to the baseline (also known as character width or character pitch). | ✅ |
| FOCA-4-079 | The average height of the uppercase characters, usually the height of the uppercase letter M. | ✅ |
| FOCA-4-080 | The nominal height above the baseline of lowercase characters (ignoring ascenders), usually the height of 'x'. | ✅ |
| FOCA-4-081 | `Internal Leading = Vertical Font Size - Baseline Extent (for text characters)` | ✅ |
| FOCA-4-082 | Additional white space that can be added to interline spacing without degrading appearance. | ✅ |
| FOCA-4-083 | Figure 36. An Illustration of External Leading** | ✅ |
| FOCA-4-084 | The maximum height attained by any graphic character in a font from the baseline to the top of its box. | ✅ |
| FOCA-4-085 | The maximum depth attained by any graphic character in a font from the baseline to the bottom of its box. | ✅ |
| FOCA-4-086 | The sum of the most positive baseline offset and the most positive descender depth of any character in the font. | ✅ |
| FOCA-4-087 | Designers may provide recommended size and position offsets for superscript and subscript characters. | ✅ |
| FOCA-4-088 | Overscore**: Bar over the character. | ✅ |
| FOCA-4-089 | Throughscore**: Bar through the character. | ✅ |
| FOCA-4-090 | Underscore**: Bar under the character. | ✅ |
| FOCA-4-091 | Offsets are specified from the character baseline to the top of the score stroke. | ✅ |
| FOCA-4-092 | Figure 37. Overscore, Throughscore, and Underscore** | ✅ |
| FOCA-4-093 | FOCA supports multidirectional text and multi-byte encoding. | ✅ |
| FOCA-4-094 | The principle distinctive characteristic is top-to-bottom or right-to-left direction. FOCA uses multiple character rotations (0°, 90°, 180°, 270°) in a single font resource. | ✅ |
| FOCA-4-095 | Horizontal writing**: 0° and 180° rotations. | ✅ |
| FOCA-4-096 | Vertical writing**: 90° and 270° rotations. | ✅ |
| FOCA-4-097 | Figures 38–41. 0°, 90°, 180°, 270° Rotations** | ✅ |
| FOCA-4-098 | For traditional Kanji, the baseline is rotated 90°, and characters are rotated 270°. | ✅ |
| FOCA-4-099 | Figure 42. Rotating Baseline and Characters** | ✅ |
| FOCA-4-100 | In systems like Japanese Kanji, characters can be horizontal (0°) or vertical (270°). | ✅ |
| FOCA-4-101 | Figure 45. 270° Character Rotation for Vertical Writing** | ✅ |
| FOCA-4-102 | Hebrew is written right-to-left. Hebrew code pages are usually bilingual Latin/Hebrew. FOCA specifies rules for consistent presentation of mixed Latin and Hebrew text. | ✅ |
| FOCA-4-103 | ISO describes metrics in terms of X,Y positions in a glyph coordinate system, while FOCA uses distances/offsets. ISO allows arbitrary positioning points; FOCA fixes them at the origin. They are equivalent but require transformation. | ✅ |
| FOCA-4-104 | Figure 48. FOCA and ISO Coordinate System Relationship** | ✅ |
| FOCA-4-105 | ISO uses a hierarchical naming tree (e.g., `ISO / ICD / IBM / IBM-CS / FONT / FGID / 12`). FOCA names can be mapped into this structure. | ✅ |
| FOCA-5-001 | Digital data processing requires that you create data structures using a defined set of parameters and parameter values organized in a specific format. FOCA provides a precise set of parameter definitions and architected values or ranges of values you can use to create font resources and font references. | ✅ |
| FOCA-5-002 | Font-description parameters | ✅ |
| FOCA-5-003 | Font-metric parameters | ✅ |
| FOCA-5-004 | Character-metric parameters | ✅ |
| FOCA-5-005 | Character-shape parameters | ✅ |
| FOCA-5-006 | Character-mapping parameters | ✅ |
| FOCA-5-007 | This section describes three parameter formats, the parameter types available for each format, and byte and bit numbering conventions. | ✅ |
| FOCA-5-008 | FOCA supports a variety of parameter formats, that is, types of syntax. The choice of format depends on the environment where you want to use the font resource. The following are three common formats supported by FOCA. See Chapter 6, “Font Interchange Formats”, for more detailed information about the formatting standards necessary to implement FOCA. | ✅ |
| FOCA-5-009 | Fixed-Format**: A fixed-format parameter is defined by its position and length within a string of fixed-format parameters. The variable name and its associated meaning is implied by the position of the parameter in the string. | ✅ |
| FOCA-5-010 | Self-Identifying**: A self-identifying parameter has a set of fields that identify the parameter, specify its length, and specify its values. | ✅ |
| FOCA-5-011 | Clear-Text**: A clear-text parameter has two fields, which are separated by a delimiter. The first field contains a character string that is the name of the parameter and the second field contains a character string that represents the value of the parameter. | ✅ |
| FOCA-5-012 | You define a FOCA parameter by identifying it and assigning it a value. The remaining sections of this chapter list the names of the parameters and show **Parameter type =** as requiring a value in the form of one of the following data types: | ✅ |
| FOCA-5-013 | Character string**: A character string parameter value is any user, system, or font-supplier defined name. It is composed of alphanumeric characters and can be any specified length. Unless otherwise specified, the default set is IBM Graphic Character Set 103. | ✅ |
| FOCA-5-014 | Flag**: A flag parameter value indicates a binary flag (1 or 0). | ✅ |
| FOCA-5-015 | Number**: A number parameter value uses real numbers or integer numbers to represent count or magnitude. | ✅ |
| FOCA-5-016 | Code**: A code parameter value is a collection of architected choices (integers, letters, or acronyms). | ✅ |
| FOCA-5-017 | Undefined**: An undefined parameter value is not defined by the architecture. | ✅ |
| FOCA-5-018 | Byte numbering**: During both transmission and storage, data is viewed as a long horizontal string. Each byte is identified by a positive integer (offset) starting with 0. | ✅ |
| FOCA-5-019 | Bit numbering**: Bytes are divided into eight bits, numbered left to right from 0 to 7 (big-endian). Bit 0 is the most significant bit. | ✅ |
| FOCA-5-020 | This section lists and describes the descriptive parameters required to identify a font, select the appropriate font for formatting, and locate the specified font for presentation. | ✅ |
| FOCA-5-021 | The Average Weighted Escapement parameter specifies the arithmetic average of the escapement of all, or some subset of, the characters in a font. The escapement value for each character is weighted by its anticipated frequency of use. | ✅ |
| FOCA-5-022 | Parameter type =** number | ✅ |
| FOCA-5-023 | Synonyms =** average character width | ✅ |
| FOCA-5-024 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-025 | Transformation to ISO/IEC 9541 font architecture:** No equivalent parameter exists in the ISO architecture. This parameter should be expressed as a non-iso-property in the Modal Properties property list. | ✅ |
| FOCA-5-026 | The Cap-M Height parameter specifies the height above the baseline for uppercase character shapes. Cap-M height is the nominal height of the uppercase characters and is usually equal to the height of the uppercase letter M. | ✅ |
| FOCA-5-027 | Parameter type =** number | ✅ |
| FOCA-5-028 | Transformation to Eastern Writing systems:** This parameter should be set to the character box height for Eastern writing systems. | ✅ |
| FOCA-5-029 | Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to `capheight` (Capitol Height). It is expressed as a relative rational number. | ✅ |
| FOCA-5-030 | The Character Rotation parameter specifies the rotation of the character box relative to the character baseline. Refer to “Units of Direction” for an explanation of character rotation. | ✅ |
| FOCA-5-031 | Parameter type =** number | ✅ |
| FOCA-5-032 | Synonyms =** font character rotation | ✅ |
| FOCA-5-033 | Transformation to Eastern Writing systems:** This parameter should be set to 0° or 270° for horizontal or vertical writing respectively. | ✅ |
| FOCA-5-034 | Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to `nomescdir` (nominal escapement direction), and to `nomwrmode` (Nominal Writing Mode). | ✅ |
| FOCA-5-035 | The Comment parameter allows the creator or the user of the font resource to make comments. The content of this parameter must be non-processable information and is ignored by any processing implementation. | ✅ |
| FOCA-5-036 | Parameter type =** character string | ✅ |
| FOCA-5-037 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-038 | Transformation to ISO/IEC 9541 font architecture:** This parameter can be expressed as a non-iso-property. | ✅ |
| FOCA-5-039 | The Design General Class parameter specifies the ISO (International Standards Organization) Font Standard General Classification of the font family design. | ✅ |
| FOCA-5-040 | Parameter type =** number | ✅ |
| FOCA-5-041 | Synonyms =** design class | ✅ |
| FOCA-5-042 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-043 | Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the class subfield of `dsngroup` (Design Group). The ISO property is a code in the range of 0 to 255. | ✅ |
| FOCA-5-044 | The Design Specific Group parameter specifies the ISO Font Standard Specific Group of the font family design. | ✅ |
| FOCA-5-045 | Parameter type =** number | ✅ |
| FOCA-5-046 | Synonyms =** design class | ✅ |
| FOCA-5-047 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-048 | Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the specific group subfield of `dsngroup` (Design Group). | ✅ |
| FOCA-5-049 | The Design Subclass parameter specifies the ISO Font Standard Subclass of the font family design. | ✅ |
| FOCA-5-050 | Parameter type =** number | ✅ |
| FOCA-5-051 | Synonyms =** design class | ✅ |
| FOCA-5-052 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-053 | Transformation to ISO/IEC 9541 font architecture:** This parameter corresponds to the subclass subfield of `dsngroup` (Design Group). | ✅ |
| FOCA-5-054 | The Em-Space Increment parameter specifies typographic space that corresponds to the space between sentences. Traditionally equals the vertical font size. | ✅ |
| FOCA-5-055 | Parameter type =** number | ✅ |
| FOCA-5-056 | Synonyms =** em increment | ✅ |
| FOCA-5-057 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern Writing systems. | ✅ |
| FOCA-5-058 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to the difference between the px,py and the ex,ey values for the Em-space glyph. | ✅ |
| FOCA-5-059 | The Extension Font parameter indicates that this font resource was designed to be an extension (contains user-defined characters) to another font. | ✅ |
| FOCA-5-060 | Parameter type =** flag | ✅ |
| FOCA-5-061 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-062 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list. | ✅ |
| FOCA-5-063 | The Family Name parameter specifies the common name for a font design. A font family includes all typeface variations of the font design. | ✅ |
| FOCA-5-064 | Parameter type =** character string | ✅ |
| FOCA-5-065 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-066 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `fontfamily`. | ✅ |
| FOCA-5-067 | The Font Local Identifier parameter specifies a numeric identifier assigned temporarily to a font resource within the context of another object. | ✅ |
| FOCA-5-068 | Parameter type =** number | ✅ |
| FOCA-5-069 | Synonyms =** font local ID, font LID | ✅ |
| FOCA-5-070 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-071 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list. | ✅ |
| FOCA-5-072 | The Font Typeface Global Identifier parameter (usually called Font Global Identifier, FGID) specifies the unique number assigned to the font typeface. | ✅ |
| FOCA-5-073 | Parameter type =** number | ✅ |
| FOCA-5-074 | Synonyms =** font global identifier, FGID, typeface identifier | ✅ |
| FOCA-5-075 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-076 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list. | ✅ |
| FOCA-5-077 | The Font Use Code parameter specifies the intended use of the graphic characters in a font. | ✅ |
| FOCA-5-078 | Parameter type =** code | ✅ |
| FOCA-5-079 | Valid choices:** | ✅ |
| FOCA-5-080 | 0: No font use intent | ✅ |
| FOCA-5-081 | 1: Image symbol set for text in a graphics object | ✅ |
| FOCA-5-082 | 3: Pattern symbol set in a graphics object | ✅ |
| FOCA-5-083 | 4: Marker symbol set in a graphics object | ✅ |
| FOCA-5-084 | 5: High resolution indicator in a graphics object | ✅ |
| FOCA-5-085 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-086 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property in the Font Description property list. | ✅ |
| FOCA-5-087 | The Graphic Character Set Global Identifier (GCSGID) parameter specifies the number assigned to a graphic character set. | ✅ |
| FOCA-5-088 | Parameter type =** number | ✅ |
| FOCA-5-089 | Synonyms =** graphic-character-set ID, character-set name | ✅ |
| FOCA-5-090 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-091 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `incglyphcols` subfield of `glyphcomp`. | ✅ |
| FOCA-5-092 | The Hollow Font parameter specifies that the graphic characters of the font appear with only the outer edges of the strokes. | ✅ |
| FOCA-5-093 | Parameter type =** flag | ✅ |
| FOCA-5-094 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-095 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to the Outline code (2) of the `structure` property. | ✅ |
| FOCA-5-096 | The Italics parameter specifies that the graphic characters are designed with a clockwise incline. | ✅ |
| FOCA-5-097 | Parameter type =** flag | ✅ |
| FOCA-5-098 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-099 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to the italic value (4) of the ISO `posture` property. | ✅ |
| FOCA-5-100 | The Kerning Pair Data parameter specifies that kerning pair data is available in the font resource. | ✅ |
| FOCA-5-101 | Parameter type =** flag | ✅ |
| FOCA-5-102 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-103 | Transformation to ISO/IEC 9541 font architecture:** Presence is indicated by the presence of the `peas` (Pairwise Escapement Adjusts) data itself. | ✅ |
| FOCA-5-104 | The Maximum Horizontal Font Size parameter specifies the maximum horizontal size for scaling, as specified by a font designer. | ✅ |
| FOCA-5-105 | Parameter type =** number | ✅ |
| FOCA-5-106 | Synonyms =** maximum character width, maximum horizontal point size | ✅ |
| FOCA-5-107 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-108 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `maxanascale` (Maximum Anamorphic Scale). | ✅ |
| FOCA-5-109 | The Maximum Vertical Font Size parameter specifies the maximum vertical size for scaling purposes as specified by a font designer. | ✅ |
| FOCA-5-110 | Parameter type =** number | ✅ |
| FOCA-5-111 | Synonyms =** maximum design size, maximum point size | ✅ |
| FOCA-5-112 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-113 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `maxsize`. | ✅ |
| FOCA-5-114 | The Measurement Units parameter specifies the unit base and units per unit base in both X and Y directions. | ✅ |
| FOCA-5-115 | Parameter type =** two codes and two numbers | ✅ |
| FOCA-5-116 | Valid unit-base code choices:** | ✅ |
| FOCA-5-117 | 0: Ten inches | ✅ |
| FOCA-5-118 | 1: Ten centimeters | ✅ |
| FOCA-5-119 | 2: Relative units | ✅ |
| FOCA-5-120 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-121 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `relunits`. | ✅ |
| FOCA-5-122 | The MICR Font parameter indicates that this font resource was designed for use in Magnetic Ink Character Recognition (MICR) applications. | ✅ |
| FOCA-5-123 | Parameter type =** flag | ✅ |
| FOCA-5-124 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-125 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-126 | The Minimum Horizontal Font Size parameter specifies the minimum horizontal size for scaling, as specified by a font designer. | ✅ |
| FOCA-5-127 | Parameter type =** number | ✅ |
| FOCA-5-128 | Synonyms =** minimum character width, minimum anamorphic scaling | ✅ |
| FOCA-5-129 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-130 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minanascale`. | ✅ |
| FOCA-5-131 | The Minimum Vertical Font Size parameter specifies the minimum vertical size for scaling purposes as specified by a font designer. | ✅ |
| FOCA-5-132 | Parameter type =** number | ✅ |
| FOCA-5-133 | Synonyms =** minimum point size, minimum size | ✅ |
| FOCA-5-134 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-135 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minsize`. | ✅ |
| FOCA-5-136 | The Nominal Character Slope parameter specifies the slope (stem incline) of the main strokes. Measured clockwise from vertical. | ✅ |
| FOCA-5-137 | Parameter type =** number | ✅ |
| FOCA-5-138 | Synonyms =** character slope | ✅ |
| FOCA-5-139 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-140 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `postureangle`. | ✅ |
| FOCA-5-141 | The Nominal Horizontal Font Size parameter specifies the nominal horizontal size for scaling. Corresponds to the escapement of the space character (SP010000). | ✅ |
| FOCA-5-142 | Parameter type =** number | ✅ |
| FOCA-5-143 | Synonyms =** horizontal font size, nominal horizontal point size | ✅ |
| FOCA-5-144 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-145 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-146 | The Nominal Vertical Font Size parameter specifies the vertical design size of the font. | ✅ |
| FOCA-5-147 | Parameter type =** number | ✅ |
| FOCA-5-148 | Synonyms =** nominal point size, design size | ✅ |
| FOCA-5-149 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-150 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `dsnsize`. | ✅ |
| FOCA-5-151 | The Overstruck Font parameter specifies that the graphic characters of the font appear as though over-struck by another graphic character (often a hyphen). | ✅ |
| FOCA-5-152 | Parameter type =** flag | ✅ |
| FOCA-5-153 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-154 | Transformation to ISO/IEC 9541 font architecture:** Represented by a unique font family name or non-iso-property. | ✅ |
| FOCA-5-155 | The Proportional Spaced parameter specifies that the character increments for each graphic character in the font resource might vary. | ✅ |
| FOCA-5-156 | Parameter type =** flag | ✅ |
| FOCA-5-157 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-158 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to the proportional value (2) of `escclass`. | ✅ |
| FOCA-5-159 | The Private Use parameter specifies that some or all of the data is privately owned or protected by a licensing agreement. | ✅ |
| FOCA-5-160 | Parameter type =** flag | ✅ |
| FOCA-5-161 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-162 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-163 | The Resource Name parameter identifies a resource object by a character string name. | ✅ |
| FOCA-5-164 | Parameter type =** character string | ✅ |
| FOCA-5-165 | Synonyms =** resource tag | ✅ |
| FOCA-5-166 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-167 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `fontname`. | ✅ |
| FOCA-5-168 | The Specified Horizontal Font Size parameter specifies the horizontal font size indicated by the document creator. | ✅ |
| FOCA-5-169 | Parameter type =** number | ✅ |
| FOCA-5-170 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-171 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-172 | The Specified Horizontal Scale Factor parameter specifies uniform or anamorphic scaling. Numerator in a ratio (Scale Factor / Vertical Size). | ✅ |
| FOCA-5-173 | Parameter type =** number | ✅ |
| FOCA-5-174 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-175 | Transformation to ISO/IEC 9541 font architecture:** No equivalent exists in resource architecture; appears only in data stream references. | ✅ |
| FOCA-5-176 | The Specified Vertical Font Size parameter specifies the vertical font size indicated by the document creator. | ✅ |
| FOCA-5-177 | Parameter type =** number | ✅ |
| FOCA-5-178 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-179 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `dsnsize`. | ✅ |
| FOCA-5-180 | The Transformable Font parameter specifies that the pattern data allows algorithmic transformation (e.g., scaling). | ✅ |
| FOCA-5-181 | Parameter type =** flag | ✅ |
| FOCA-5-182 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-183 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-184 | The Typeface Name parameter specifies the common name of the typeface (e.g., Sonoran Sans Serif Roman bold condensed). | ✅ |
| FOCA-5-185 | Parameter type =** character string | ✅ |
| FOCA-5-186 | Synonyms =** facename | ✅ |
| FOCA-5-187 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-188 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `typeface`. | ✅ |
| FOCA-5-189 | The Underscored Font parameter specifies that the graphic character pattern data contains underscores as part of the shape. | ✅ |
| FOCA-5-190 | Parameter type =** flag | ✅ |
| FOCA-5-191 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-192 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-193 | The Uniform Character Box Font parameter specifies that all raster patterns are of the same size. | ✅ |
| FOCA-5-194 | Parameter type =** flag | ✅ |
| FOCA-5-195 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-196 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-197 | The Weight Class parameter indicates the visual weight (degree or thickness of strokes). | ✅ |
| FOCA-5-198 | Parameter type =** code | ✅ |
| FOCA-5-199 | Valid choices:** | ✅ |
| FOCA-5-200 | 1: Ultralight | ✅ |
| FOCA-5-201 | 2: Extralight | ✅ |
| FOCA-5-202 | 3: Light | ✅ |
| FOCA-5-203 | 4: Semilight | ✅ |
| FOCA-5-204 | 5: Medium (normal) | ✅ |
| FOCA-5-205 | 6: Semibold | ✅ |
| FOCA-5-206 | 7: Bold | ✅ |
| FOCA-5-207 | 8: Extrabold | ✅ |
| FOCA-5-208 | 9: Ultrabold | ✅ |
| FOCA-5-209 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-210 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `weight`. | ✅ |
| FOCA-5-211 | The Width Class parameter indicates a relative change from the normal aspect ratio. | ✅ |
| FOCA-5-212 | Parameter type =** code | ✅ |
| FOCA-5-213 | Valid choices:** | ✅ |
| FOCA-5-214 | 1: Ultracondensed (50%) | ✅ |
| FOCA-5-215 | 2: Extracondensed (62.5%) | ✅ |
| FOCA-5-216 | 3: Condensed (75%) | ✅ |
| FOCA-5-217 | 4: Semicondensed (87.5%) | ✅ |
| FOCA-5-218 | 5: Normal (100%) | ✅ |
| FOCA-5-219 | 6: Semiexpanded (112.5%) | ✅ |
| FOCA-5-220 | 7: Expanded (125%) | ✅ |
| FOCA-5-221 | 8: Extraexpanded (150%) | ✅ |
| FOCA-5-222 | 9: Ultraexpanded (200%) | ✅ |
| FOCA-5-223 | Transformation to Eastern Writing systems:** This parameter is writing-system independent. | ✅ |
| FOCA-5-224 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `propwidth`. | ✅ |
| FOCA-5-225 | The X-Height parameter specifies the height of the body of lowercase graphic characters (usually height of 'x'). | ✅ |
| FOCA-5-226 | Parameter type =** number | ✅ |
| FOCA-5-227 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-228 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `lcheight`. | ✅ |
| FOCA-5-229 | These parameters apply to all of the font characters and are repeated for each supported character rotation. | ✅ |
| FOCA-5-230 | The Default Baseline Increment parameter specifies the nominal distance between character reference points in the vertical direction recommended by the designer. | ✅ |
| FOCA-5-231 | Parameter type =** number | ✅ |
| FOCA-5-232 | Transformation to Eastern Writing systems:** For vertical writing, distance between baselines of two columns. | ✅ |
| FOCA-5-233 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `minlinesp`. | ✅ |
| FOCA-5-234 | The External Leading parameter specifies recommended additional interline white space. | ✅ |
| FOCA-5-235 | Parameter type =** number | ✅ |
| FOCA-5-236 | Transformation to Eastern Writing systems:** Supplemental value for extending distance between baselines. | ✅ |
| FOCA-5-237 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-238 | The Figure Space Increment parameter specifies the character increment used for numerals. | ✅ |
| FOCA-5-239 | Parameter type =** number | ✅ |
| FOCA-5-240 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern Writing systems. | ✅ |
| FOCA-5-241 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `tabescx` and `tabescy`. | ✅ |
| FOCA-5-242 | The Internal Leading parameter specifies nominal white space above and below characters. | ✅ |
| FOCA-5-243 | Parameter type =** number | ✅ |
| FOCA-5-244 | Transformation to Eastern Writing systems:** Difference between Nominal Horizontal Size and Max Baseline Extent for 270° rotation. | ✅ |
| FOCA-5-245 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-246 | The Kerning parameter is a flag that indicates whether any of the metrics contain negative values allowing kerning. | ✅ |
| FOCA-5-247 | Parameter type =** flag | ✅ |
| FOCA-5-248 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-249 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-250 | The Kerning Pair Character 1 parameter specifies the first character in a kerning pair. | ✅ |
| FOCA-5-251 | Parameter type =** character string | ✅ |
| FOCA-5-252 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-253 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname`. | ✅ |
| FOCA-5-254 | The Kerning Pair Character 2 parameter specifies the second character in a kerning pair. | ✅ |
| FOCA-5-255 | Parameter type =** character string | ✅ |
| FOCA-5-256 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-257 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname` component of `peax` / `peay`. | ✅ |
| FOCA-5-258 | The Kerning Pair X-Adjust parameter specifies the escapement adjustment in the x direction for the specified pair. | ✅ |
| FOCA-5-259 | Parameter type =** number | ✅ |
| FOCA-5-260 | Synonyms =** pairwise escapement x-adjust | ✅ |
| FOCA-5-261 | Transformation to Eastern Writing systems:** This parameter does not apply to Eastern writing systems. | ✅ |
| FOCA-5-262 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to the adjustment component of `peax`. | ✅ |
| FOCA-5-263 | The Maximum Ascender Height parameter specifies the maximum height from the baseline to the top of any character box. | ✅ |
| FOCA-5-264 | Parameter type =** number | ✅ |
| FOCA-5-265 | Transformation to Eastern Writing systems:** Max distance from baseline to right-hand edge of character box for vertical writing. | ✅ |
| FOCA-5-266 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum x or y value of `maxfontext`. | ✅ |
| FOCA-5-267 | The Maximum Baseline Extent parameter specifies the space parallel to the baseline required to place characters. Sum of max baseline offset and max descender depth. | ✅ |
| FOCA-5-268 | Parameter type =** number | ✅ |
| FOCA-5-269 | Transformation to Eastern Writing systems:** Max Ascender Height plus Max Descender Depth. | ✅ |
| FOCA-5-270 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-271 | The Maximum Baseline Offset parameter specifies the maximum distance from the baseline to the upper edge of the character box. | ✅ |
| FOCA-5-272 | Parameter type =** number | ✅ |
| FOCA-5-273 | Transformation to Eastern Writing systems:** Equal to Maximum Ascender Height. | ✅ |
| FOCA-5-274 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-275 | The Maximum Character Box Height parameter specifies the height of uniform boxes or max height of variable boxes. | ✅ |
| FOCA-5-276 | Parameter type =** number | ✅ |
| FOCA-5-277 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-278 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-279 | The Maximum Character Box Width parameter specifies the width of uniform boxes or max width of variable boxes. | ✅ |
| FOCA-5-280 | Parameter type =** number | ✅ |
| FOCA-5-281 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-282 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-283 | The Maximum Character Increment parameter specifies the maximum increment for all characters in a font rotation. | ✅ |
| FOCA-5-284 | Parameter type =** number | ✅ |
| FOCA-5-285 | Transformation to Eastern Writing systems:** Max increment for 270° rotation metrics. | ✅ |
| FOCA-5-286 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-287 | The Maximum Descender Depth parameter specifies the maximum depth from the baseline to the bottom of any character box. | ✅ |
| FOCA-5-288 | Parameter type =** number | ✅ |
| FOCA-5-289 | Transformation to Eastern Writing systems:** Max distance from baseline to left-hand edge of box for vertical writing. | ✅ |
| FOCA-5-290 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to minimum x or y value of `maxfontext`. | ✅ |
| FOCA-5-291 | The Maximum Lowercase Ascender Height parameter specifies the max ascender height of lowercase (a–z) at 0° rotation. | ✅ |
| FOCA-5-292 | Parameter type =** number | ✅ |
| FOCA-5-293 | Transformation to Eastern Writing systems:** This parameter does not apply. | ✅ |
| FOCA-5-294 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-295 | The Maximum Lowercase Descender Depth parameter specifies the max descender depth of lowercase (a–z) at 0° rotation. | ✅ |
| FOCA-5-296 | Parameter type =** number | ✅ |
| FOCA-5-297 | Transformation to Eastern Writing systems:** This parameter does not apply. | ✅ |
| FOCA-5-298 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-299 | The Maximum V(y) parameter is the maximum of all Adobe ATM V(y) values (y coordinate of distance from origin to positioning point). | ✅ |
| FOCA-5-300 | Parameter type =** number | ✅ |
| FOCA-5-301 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-302 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum of all `P(y)` values. | ✅ |
| FOCA-5-303 | The Maximum W(y) parameter is the maximum of all Adobe ATM W(y) values (y coordinate of distance from positioning point to escapement point). | ✅ |
| FOCA-5-304 | Parameter type =** number | ✅ |
| FOCA-5-305 | Synonyms =** Vertical Character Increment | ✅ |
| FOCA-5-306 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-307 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to maximum absolute magnitude of all `(P(y)-E(y))` values. | ✅ |
| FOCA-5-308 | The Minimum A-space parameter specifies the smallest A-space (most negative or least positive) value. | ✅ |
| FOCA-5-309 | Parameter type =** number | ✅ |
| FOCA-5-310 | Transformation to Eastern Writing systems:** Least A-space for 270° rotation metrics. | ✅ |
| FOCA-5-311 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-312 | The Nominal Character Increment parameter specifies the most commonly repeated character increment. | ✅ |
| FOCA-5-313 | Parameter type =** number | ✅ |
| FOCA-5-314 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-315 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-316 | The Space Character Increment parameter specifies the default character increment for the space character. | ✅ |
| FOCA-5-317 | Parameter type =** number | ✅ |
| FOCA-5-318 | Transformation to Eastern Writing systems:** Space increment for 270° rotation. | ✅ |
| FOCA-5-319 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to difference between `px,py` and `ex,ey` for Normal Space. | ✅ |
| FOCA-5-320 | The Subscript Vertical Font Size parameter specifies designer's recommended size for subscript characters. | ✅ |
| FOCA-5-321 | Parameter type =** number | ✅ |
| FOCA-5-322 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-323 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsscaley`. | ✅ |
| FOCA-5-324 | The Subscript X-Axis Offset parameter specifies recommended vertical offset from baseline to subscript baseline. | ✅ |
| FOCA-5-325 | Parameter type =** number | ✅ |
| FOCA-5-326 | Transformation to Eastern Writing systems:** Does not apply. | ✅ |
| FOCA-5-327 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsoffsetx`. | ✅ |
| FOCA-5-328 | The Superscript Vertical Font Size parameter specifies designer's recommended size for superscript characters. | ✅ |
| FOCA-5-329 | Parameter type =** number | ✅ |
| FOCA-5-330 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-331 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsscaley`. | ✅ |
| FOCA-5-332 | The Superscript X-Axis Offset parameter specifies recommended vertical offset from baseline to superscript baseline. | ✅ |
| FOCA-5-333 | Parameter type =** number | ✅ |
| FOCA-5-334 | Transformation to Eastern Writing systems:** Does not apply. | ✅ |
| FOCA-5-335 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `vsoffsetx`. | ✅ |
| FOCA-5-336 | The Throughscore Position parameter specifies recommendation for drawing throughscores (perpendicular distance from baseline to top of stroke). | ✅ |
| FOCA-5-337 | Parameter type =** number | ✅ |
| FOCA-5-338 | Transformation to Eastern Writing systems:** Distance between center line and vertical score line. | ✅ |
| FOCA-5-339 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scoreoffsetx` / `scoreoffsety`. | ✅ |
| FOCA-5-340 | The Throughscore Width parameter specifies recommended width (thickness) of the throughscore. | ✅ |
| FOCA-5-341 | Parameter type =** number | ✅ |
| FOCA-5-342 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-343 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scorethick`. | ✅ |
| FOCA-5-344 | The Underscore Position parameter specifies recommendation for drawing underscores (distance from baseline to top of stroke). | ✅ |
| FOCA-5-345 | Parameter type =** number | ✅ |
| FOCA-5-346 | Transformation to Eastern Writing systems:** Distance between center line and vertical score line running to the left. | ✅ |
| FOCA-5-347 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scoreoffsetx` / `scoreoffsety` (`RIGHTSCORE`). | ✅ |
| FOCA-5-348 | The Underscore Width parameter specifies recommended width (thickness) of underscores. | ✅ |
| FOCA-5-349 | Parameter type =** number | ✅ |
| FOCA-5-350 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-351 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `scorethick`. | ✅ |
| FOCA-5-352 | The Uniform A-space parameter specifies that a uniform amount of A-space was removed from all patterns. | ✅ |
| FOCA-5-353 | Parameter type =** flag | ✅ |
| FOCA-5-354 | Transformation to Eastern Writing systems:** A-space for 270° rotation metrics. | ✅ |
| FOCA-5-355 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-356 | The Uniform Baseline Offset parameter specifies that all patterns have a common baseline offset. | ✅ |
| FOCA-5-357 | Parameter type =** flag | ✅ |
| FOCA-5-358 | Transformation to Eastern Writing systems:** Uniform Baseline Offset for 270° rotation metrics. | ✅ |
| FOCA-5-359 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-360 | The Uniform Character Increment parameter specifies that all increments are the same. | ✅ |
| FOCA-5-361 | Parameter type =** flag | ✅ |
| FOCA-5-362 | Transformation to Eastern Writing systems:** Uniform Character Increment for 270° rotation metrics. | ✅ |
| FOCA-5-363 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-364 | These parameters apply to individual characters and are repeated for each character in each rotation. | ✅ |
| FOCA-5-365 | Distance from character reference point to the least positive character coordinate system x-axis value. | ✅ |
| FOCA-5-366 | Parameter type =** number | ✅ |
| FOCA-5-367 | Transformation to Eastern Writing systems:** Distance between reference point and least positive x-axis value of 270° shape. | ✅ |
| FOCA-5-368 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to distance between `px,py` and closest `ext` property value. | ✅ |
| FOCA-5-369 | Height of the topmost mark of a graphic character relative to the baseline. | ✅ |
| FOCA-5-370 | Parameter type =** number | ✅ |
| FOCA-5-371 | Transformation to Eastern Writing systems:** Distance between reference point and most positive y-axis value of 270° shape. | ✅ |
| FOCA-5-372 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to max y (0° rotation) or max x (270° rotation). | ✅ |
| FOCA-5-373 | Distance from character baseline to the topmost edge of a character box. | ✅ |
| FOCA-5-374 | Parameter type =** number | ✅ |
| FOCA-5-375 | Transformation to Eastern Writing systems:** Equal to Ascender Height. | ✅ |
| FOCA-5-376 | Transformation to ISO/IEC 9541 font architecture:** Derived from `ext` (Extents) property. | ✅ |
| FOCA-5-377 | Width of the (bounded) character box. | ✅ |
| FOCA-5-378 | Parameter type =** number | ✅ |
| FOCA-5-379 | Transformation to Eastern Writing systems:** Distance between most/least positive x-axis value of 270° shape. | ✅ |
| FOCA-5-380 | Transformation to ISO/IEC 9541 font architecture:** Derived from `minx`/`maxx` or `miny`/`maxy`. | ✅ |
| FOCA-5-381 | Height of the character box for a graphic character. | ✅ |
| FOCA-5-382 | Parameter type =** number | ✅ |
| FOCA-5-383 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-384 | Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `miny` and `maxy`. | ✅ |
| FOCA-5-385 | Width of the character box for a character. | ✅ |
| FOCA-5-386 | Parameter type =** number | ✅ |
| FOCA-5-387 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-388 | Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `minx` and `maxx`. | ✅ |
| FOCA-5-389 | Algebraic sum of A-space, B-space, and C-space. | ✅ |
| FOCA-5-390 | Parameter type =** number | ✅ |
| FOCA-5-391 | Transformation to Eastern Writing systems:** Distance between reference point and escapement point for 270° shape. | ✅ |
| FOCA-5-392 | Transformation to ISO/IEC 9541 font architecture:** Derived from difference between `px,py` and `ex,ey`. | ✅ |
| FOCA-5-393 | Width of the space from the bounded character box to the escapement point. | ✅ |
| FOCA-5-394 | Parameter type =** number | ✅ |
| FOCA-5-395 | Transformation to Eastern Writing systems:** Distance between escapement point and least positive x-axis value of 270° shape. | ✅ |
| FOCA-5-396 | Transformation to ISO/IEC 9541 font architecture:** Derived from ISO glyph properties. | ✅ |
| FOCA-5-397 | Distance from baseline to bottom of character box. | ✅ |
| FOCA-5-398 | Parameter type =** number | ✅ |
| FOCA-5-399 | Transformation to Eastern Writing systems:** Distance between reference point and most negative y-axis value of 270° shape. | ✅ |
| FOCA-5-400 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `miny` (0° rotation) or `minx` (270° rotation). | ✅ |
| FOCA-5-401 | Registered identifier of a graphic character. Default encoding is EBCDIC, length is 8 bytes. | ✅ |
| FOCA-5-402 | Parameter type =** character string | ✅ |
| FOCA-5-403 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-404 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `gname`. | ✅ |
| FOCA-5-405 | Required for presentation of the character shapes. Repeated for each technology supported. | ✅ |
| FOCA-5-406 | Intended presentation resolution in the x direction. | ✅ |
| FOCA-5-407 | Parameter type =** number | ✅ |
| FOCA-5-408 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-409 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-410 | Intended presentation resolution in the y direction. | ✅ |
| FOCA-5-411 | Parameter type =** number | ✅ |
| FOCA-5-412 | Transformation to Eastern Writing systems:** Writing-system independent. | ✅ |
| FOCA-5-413 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-414 | Specifies whether character IDs in CMAP are linked to Name Map. | ✅ |
| FOCA-5-415 | Parameter type =** code | ✅ |
| FOCA-5-416 | Valid Choices:** | ✅ |
| FOCA-5-417 | 0: Linked | ✅ |
| FOCA-5-418 | 1: Unlinked | ✅ |
| FOCA-5-419 | Transformation to Eastern Writing systems:** Primarily used with eastern writing systems. | ✅ |
| FOCA-5-420 | Transformation to ISO/IEC 9541 font architecture:** No equivalent. | ✅ |
| FOCA-5-421 | Identifies various objects embedded in a font resource (e.g., CMAP, CID, PFB). | ✅ |
| FOCA-5-422 | Parameter type =** code | ✅ |
| FOCA-5-423 | Valid Choices:** | ✅ |
| FOCA-5-424 | 0: No information | ✅ |
| FOCA-5-425 | 1: CMAP file | ✅ |
| FOCA-5-426 | 5: CID file | ✅ |
| FOCA-5-427 | 6: PFB file | ✅ |
| FOCA-5-428 | 7: AFM file | ✅ |
| FOCA-5-429 | 8: File Name Map | ✅ |
| FOCA-5-430 | Transformation to ISO/IEC 9541 font architecture:** No equivalent. | ✅ |
| FOCA-5-431 | The actual pattern data for the shape representation technique. | ✅ |
| FOCA-5-432 | Parameter type =** undefined data | ✅ |
| FOCA-5-433 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-434 | Specifies alignment of beginning of each character's pattern data (exponent of base 2). | ✅ |
| FOCA-5-435 | Parameter type =** code | ✅ |
| FOCA-5-436 | Valid choices:** | ✅ |
| FOCA-5-437 | 0: One-byte Alignment | ✅ |
| FOCA-5-438 | 1: Two-byte Alignment | ✅ |
| FOCA-5-439 | 2: Four-byte Alignment | ✅ |
| FOCA-5-440 | 3: Eight-byte Alignment | ✅ |
| FOCA-5-441 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-442 | Byte alignment multiplier of the Pattern Data Offset. | ✅ |
| FOCA-5-443 | Parameter type =** number | ✅ |
| FOCA-5-444 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-445 | Total quantity of shape data in bytes. | ✅ |
| FOCA-5-446 | Parameter type =** number | ✅ |
| FOCA-5-447 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-448 | Used with Alignment Code to calculate actual byte offset. | ✅ |
| FOCA-5-449 | Parameter type =** number | ✅ |
| FOCA-5-450 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-451 | Specifies the technologies for font graphic patterns. | ✅ |
| FOCA-5-452 | Parameter type =** code | ✅ |
| FOCA-5-453 | Valid choices:** | ✅ |
| FOCA-5-454 | 5: Laser Matrix N-Bit Wide Horizontal Sections | ✅ |
| FOCA-5-455 | 30: CID Keyed Outline Font Technology | ✅ |
| FOCA-5-456 | 31: Type 1 PFB Outline Font Technology | ✅ |
| FOCA-5-457 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-458 | Specifies if an object is primary or auxiliary. | ✅ |
| FOCA-5-459 | Parameter type =** code | ✅ |
| FOCA-5-460 | Valid Choices:** | ✅ |
| FOCA-5-461 | 0: Primary | ✅ |
| FOCA-5-462 | 1: Auxiliary | ✅ |
| FOCA-5-463 | Transformation to ISO/IEC 9541 font architecture:** No equivalent. | ✅ |
| FOCA-5-464 | Index into Pattern Data Offset repeating group. | ✅ |
| FOCA-5-465 | Parameter type =** number | ✅ |
| FOCA-5-466 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-467 | Nominal direction in which characters are written or read. | ✅ |
| FOCA-5-468 | Parameter type =** code | ✅ |
| FOCA-5-469 | Valid Choices:** | ✅ |
| FOCA-5-470 | 0: No information | ✅ |
| FOCA-5-471 | 1: Horizontal | ✅ |
| FOCA-5-472 | 2: Vertical | ✅ |
| FOCA-5-473 | 3: Both | ✅ |
| FOCA-5-474 | Transformation to ISO/IEC 9541 font architecture:** Corresponds to `Writing Mode`. | ✅ |
| FOCA-5-475 | Used to map code points to graphic character identifiers. | ✅ |
| FOCA-5-476 | Descriptive title or short description of the code page. | ✅ |
| FOCA-5-477 | Parameter type =** character string | ✅ |
| FOCA-5-478 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-479 | Number assigned to a code page (CPGID). | ✅ |
| FOCA-5-480 | Parameter type =** number | ✅ |
| FOCA-5-481 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-482 | Value of the integer assigned to a graphic character in a list. | ✅ |
| FOCA-5-483 | Parameter type =** number | ✅ |
| FOCA-5-484 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-485 | Identifies the scheme used to code character data (e.g., EBCDIC, ASCII, UCS). | ✅ |
| FOCA-5-486 | Parameter type =** code | ✅ |
| FOCA-5-487 | Valid choices (Basic structure):** | ✅ |
| FOCA-5-488 | 0: No specified organization | ✅ |
| FOCA-5-489 | 2: IBM-PC Data | ✅ |
| FOCA-5-490 | 6: EBCDIC Presentation | ✅ |
| FOCA-5-491 | 8: UCS Presentation | ✅ |
| FOCA-5-492 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-493 | Identifies the naming source and method used to identify graphic characters. | ✅ |
| FOCA-5-494 | Parameter type =** code | ✅ |
| FOCA-5-495 | Valid choices:** | ✅ |
| FOCA-5-496 | 2: IBM Registered EBCDIC GCGID | ✅ |
| FOCA-5-497 | 3: Font Specific ASCII Character Name | ✅ |
| FOCA-5-498 | 5: CMap Binary Code Point | ✅ |
| FOCA-5-499 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-500 | Specifies the length of the graphic character identifier (default 8 bytes). | ✅ |
| FOCA-5-501 | Parameter type =** number | ✅ |
| FOCA-5-502 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-503 | Specifies that the character is not valid and should not be used. | ✅ |
| FOCA-5-504 | Parameter type =** flag | ✅ |
| FOCA-5-505 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-506 | Specifies that the character increment should be ignored. | ✅ |
| FOCA-5-507 | Parameter type =** flag | ✅ |
| FOCA-5-508 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-509 | Specifies that the character should not be presented. | ✅ |
| FOCA-5-510 | Parameter type =** flag | ✅ |
| FOCA-5-511 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-512 | Number of assigned code points in a code page. | ✅ |
| FOCA-5-513 | Parameter type =** number | ✅ |
| FOCA-5-514 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-515 | Specifies the section number of a multibyte code page (first byte of two-byte character). | ✅ |
| FOCA-5-516 | Parameter type =** number | ✅ |
| FOCA-5-517 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-518 | Code point assigned as the space character. | ✅ |
| FOCA-5-519 | Parameter type =** number | ✅ |
| FOCA-5-520 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-521 | Section number for the space character code point. | ✅ |
| FOCA-5-522 | Parameter type =** number | ✅ |
| FOCA-5-523 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-5-524 | Identifier used whenever a font doesn't contain info for a GCGID associated with a code point. | ✅ |
| FOCA-5-525 | Parameter type =** character string (named GCGID) | ✅ |
| FOCA-5-526 | Transformation to ISO/IEC 9541 font architecture:** Expressed as a non-iso-property. | ✅ |
| FOCA-6-001 | Font Information Interchange is defined to be the transfer of font information between or among processing systems, software processes, and hardware devices. You can transfer font information between any two systems, processes, or devices as long as both parties recognize the same format. The font information that you interchange can be a complete font resource, selected components of a font resource, or reference information about a font resource. | ✅ |
| FOCA-6-002 | This chapter specifies the public font information interchange formats supported by this architecture. | ✅ |
| FOCA-6-003 | The format for font resource data, to be loaded and managed by Advanced Function Presentation (AFP) software, is defined by the following syntax specification. The syntax specification is divided into three sections: objects, structured fields, and triplets. | ✅ |
| FOCA-6-004 | A coded font is an AFP font resource object that associates AFP font character set objects with AFP code page objects. | ✅ |
| FOCA-6-005 | 1.  **Begin Coded Font (BCF)** | ✅ |
| FOCA-6-006 | 2.  **Coded Font Control (CFC)** | ✅ |
| FOCA-6-007 | 3.  **Coded Font Index (CFI)** | ✅ |
| FOCA-6-008 | 4.  **End Coded Font (ECF)** | ✅ |
| FOCA-6-009 | 5.  **No Operation (NOP)**: (Optional) | ✅ |
| FOCA-6-010 | A code page is a font resource object that associates graphic character global IDs (GCGIDs) to code points. | ✅ |
| FOCA-6-011 | 1.  **Begin Code Page (BCP)** | ✅ |
| FOCA-6-012 | 2.  **Code Page Descriptor (CPD)** | ✅ |
| FOCA-6-013 | 3.  **Code Page Control (CPC)** | ✅ |
| FOCA-6-014 | 4.  **Code Page Index (CPI)** | ✅ |
| FOCA-6-015 | 5.  **End Code Page (ECP)** | ✅ |
| FOCA-6-016 | 6.  **No Operation (NOP)**: (Optional) | ✅ |
| FOCA-6-017 | A font character set is a font resource object that contains descriptive and metric information for the whole font, and metric and shape information for each character identifier. | ✅ |
| FOCA-6-018 | 1.  **Begin Font (BFN)** | ✅ |
| FOCA-6-019 | 2.  **Font Descriptor (FND)** | ✅ |
| FOCA-6-020 | 3.  **Font Control (FNC)** | ✅ |
| FOCA-6-021 | 4.  **Font Patterns Map (FNM)** | ✅ |
| FOCA-6-022 | 5.  **Font Orientation (FNO)** | ✅ |
| FOCA-6-023 | 6.  **Font Position (FNP)** | ✅ |
| FOCA-6-024 | 7.  **Font Index (FNI)** | ✅ |
| FOCA-6-025 | 8.  **Font Name Map (FNN)** | ✅ |
| FOCA-6-026 | 9.  **Font Patterns (FNG)** | ✅ |
| FOCA-6-027 | 10. **End Font (EFN)** | ✅ |
| FOCA-6-028 | 11. **No Operation (NOP)**: (Optional) | ✅ |
| FOCA-6-029 | A structured field introducer begins each structured field. | ✅ |
| FOCA-6-030 | Table 13. Structured Field Introducer** | ✅ |
| FOCA-6-031 | 0–1 | UBIN | Length | 8–32,767 | Length of Structured Field | M | ✅ |
| FOCA-6-032 | 2–4 | CODE | SFID | See Below | Structured Field Identifier | M | ✅ |
| FOCA-6-033 | 5 | BITS | SFFlags | See Below | Control Flags | M | ✅ |
| FOCA-6-034 | 6–7 | UBIN | Sequence Number | 1–32,767 | The number of the structured field in the object. | M | ✅ |
| FOCA-6-035 | 8 | UBIN | Extension Length | 1–255 | Length of extension data | O | ✅ |
| FOCA-6-036 | 9–n | UNDF | Extension Data | | Up to 254 bytes of data | O | ✅ |
| FOCA-6-037 | x–y | UNDF | Padding Data | | Up to 32,759 bytes of data | O | ✅ |
| FOCA-6-038 | Table 14. Structured-Field Identifiers** | ✅ |
| FOCA-6-039 | BCF** | X'D3A88A' | Begin Coded Font | ✅ |
| FOCA-6-040 | BCP** | X'D3A887' | Begin Code Page | ✅ |
| FOCA-6-041 | BFN** | X'D3A889' | Begin Font | ✅ |
| FOCA-6-042 | CFC** | X'D3A78A' | Coded Font Control | ✅ |
| FOCA-6-043 | CFI** | X'D38C8A' | Coded Font Index | ✅ |
| FOCA-6-044 | CPC** | X'D3A787' | Code Page Control | ✅ |
| FOCA-6-045 | CPD** | X'D3A687' | Code Page Descriptor | ✅ |
| FOCA-6-046 | CPI** | X'D38C87' | Code Page Index | ✅ |
| FOCA-6-047 | ECF** | X'D3A98A' | End Coded Font | ✅ |
| FOCA-6-048 | ECP** | X'D3A987' | End Code Page | ✅ |
| FOCA-6-049 | EFN** | X'D3A989' | End Font | ✅ |
| FOCA-6-050 | FNC** | X'D3A789' | Font Control | ✅ |
| FOCA-6-051 | FND** | X'D3A689' | Font Descriptor | ✅ |
| FOCA-6-052 | FNG** | X'D3EE89' | Font Patterns | ✅ |
| FOCA-6-053 | FNI** | X'D38C89' | Font Index | ✅ |
| FOCA-6-054 | FNM** | X'D3A289' | Font Patterns Map | ✅ |
| FOCA-6-055 | FNN** | X'D3AB89' | Font Names (Outline Fonts Only) | ✅ |
| FOCA-6-056 | FNO** | X'D3AE89' | Font Orientation | ✅ |
| FOCA-6-057 | FNP** | X'D3AC89' | Font Position | ✅ |
| FOCA-6-058 | NOP** | X'D3EEEE' | No Operation | ✅ |
| FOCA-6-059 | The Begin Coded Font (BCF) structured field begins a coded font object. | ✅ |
| FOCA-6-060 | 0–7 | CHAR | CFName | | Coded Font Name | O | ✅ |
| FOCA-6-061 | The Begin Code Page (BCP) structured field begins a code page object. | ✅ |
| FOCA-6-062 | 0–7 | CHAR | CPName | | Code Page Name | O | ✅ |
| FOCA-6-063 | 8–n | UNDF | Triplets | See Below | Self Defining Triplets | O | ✅ |
| FOCA-6-064 | The Begin Font (BFN) structured field begins the font character set object. | ✅ |
| FOCA-6-065 | 0–7 | CHAR | CSName | | Font Character Set Name | O | ✅ |
| FOCA-6-066 | 8–n | UNDF | Triplets | See Below | Self Defining Triplets | O | ✅ |
| FOCA-6-067 | The Coded Font Control (CFC) structured field specifies the length of the repeating group in the Coded Font Index (CFI) structured field. | ✅ |
| FOCA-6-068 | 0 | UBIN | CFIRGLen | X'19' | CFI Repeating Group Length | M | ✅ |
| FOCA-6-069 | 1 | UBIN | Retired | X'01' | Retired Parameter | M | ✅ |
| FOCA-6-070 | 2–end | Triplet | | X'79' | Metric Adjustment triplet | O | ✅ |
| FOCA-6-071 | The Coded Font Index (CFI) structured field names the font character sets and code pages for the font. | ✅ |
| FOCA-6-072 | Repeating Group Definition:** | ✅ |
| FOCA-6-073 | 0–7 | CHAR | FCSName | | Font Character Set Name | M | ✅ |
| FOCA-6-074 | 8–15 | CHAR | CPName | | Code Page Name | M | ✅ |
| FOCA-6-075 | 16–17 | UBIN | SVSize | 0–65,535 | Specified Vertical Font Size (in 1440ths) | M | ✅ |
| FOCA-6-076 | 18–19 | UBIN | SHScale | 0–65,535 | Specified Horizontal Scale Factor (in 1440ths) | M | ✅ |
| FOCA-6-077 | 20–23 | UNDF | | X'00000000' | Reserved | M | ✅ |
| FOCA-6-078 | 24 | UBIN | Section | | Section Number | M | ✅ |
| FOCA-6-079 | The Code Page Control (CPC) contains information about the code page. | ✅ |
| FOCA-6-080 | 0–7 | CHAR | DefCharID | | Default Graphic Character Global ID | M | ✅ |
| FOCA-6-081 | 8 | BITS | PrtFlags | See Below | Default Character Use Flags | M | ✅ |
| FOCA-6-082 | 9 | CODE | CPIRGLen | X'0A', X'0B', X'FE', X'FF' | CPI Repeating Group Length | M | ✅ |
| FOCA-6-083 | 10 | UBIN | VSCharSN | X'00'–X'FF' | Space Character Section Number | M | ✅ |
| FOCA-6-084 | 11 | UBIN | VSChar | X'00'–X'FF' | Space Character Code Point | M | ✅ |
| FOCA-6-085 | 12 | BITS | VSFlags | See Below | Code Page Use Flags | M | ✅ |
| FOCA-6-086 | +0–3 | UBIN | Unicode | | Optional Unicode value for default ID | O | ✅ |
| FOCA-6-087 | The Code Page Descriptor (CPD) structured field describes the code page. | ✅ |
| FOCA-6-088 | 0–31 | CHAR | CPDesc | | Code Page Description | M | ✅ |
| FOCA-6-089 | 32–33 | UBIN | GCGIDLen | 8 | Graphic Character GID Length | M | ✅ |
| FOCA-6-090 | 34–37 | UBIN | NumCdPts | 1–65,535 | Number of Assigned Code Points | M | ✅ |
| FOCA-6-091 | 38–39 | UBIN | GCSGID | | Graphic Character Set GID | M | ✅ |
| FOCA-6-092 | 40–41 | UBIN | CPGID | | Code Page Global Identifier | M | ✅ |
| FOCA-6-093 | 42–43 | UBIN | EncScheme | See Below | Encoding Scheme | O | ✅ |
| FOCA-6-094 | In a series of repeating groups, the Code Page Index (CPI) associates character IDs with code points. | ✅ |
| FOCA-6-095 | Repeating Group Definition:** | ✅ |
| FOCA-6-096 | 0–7 | CHAR | GCGID | | Graphic Character Global ID | M | ✅ |
| FOCA-6-097 | 8 | BITS | PrtFlags | See Below | Graphic Character Use Flags | M | ✅ |
| FOCA-6-098 | 9 or 9–10 | UBIN | CodePoint | 0–65,535 | Code Point | M | ✅ |
| FOCA-6-099 | +0 | UBIN | Count | X'00'–X'FF' | Number of Unicode scalar values | O | ✅ |
| FOCA-6-100 | ++0–3 | UBIN | Unicode | | Unicode scalar value | O | ✅ |
| FOCA-6-101 | The End Coded Font (ECF) structured field ends the coded font object. | ✅ |
| FOCA-6-102 | 0–7 | CHAR | CFName | | Coded Font Name | O | ✅ |
| FOCA-6-103 | The End Code Page (ECP) structured field ends the code page object. | ✅ |
| FOCA-6-104 | 0–7 | CHAR | CPName | | Code Page Name | O | ✅ |
| FOCA-6-105 | The End Font (EFN) structured field ends the font character set object. | ✅ |
| FOCA-6-106 | 0–7 | CHAR | CSName | | Font Character Set Name | O | ✅ |
| FOCA-6-107 | The Font Control (FNC) structured field provides defaults and information about the font character set. | ✅ |
| FOCA-6-108 | 0 | UBIN | Retired | X'01' | Retired Parameter | M | ✅ |
| FOCA-6-109 | 1 | CODE | PatTech | X'05', X'1E', X'1F' | Pattern Technology Identifier | M | ✅ |
| FOCA-6-110 | 2 | UNDF | Reserved | X'00' | Reserved | M | ✅ |
| FOCA-6-111 | 3 | BITS | FntFlags | See Below | Font Use Flags | M | ✅ |
| FOCA-6-112 | 4 | CODE | XUnitBase | X'00', X'02' | X Unit Base | M | ✅ |
| FOCA-6-113 | 5 | CODE | YUnitBase | X'00', X'02' | Y Unit Base | M | ✅ |
| FOCA-6-114 | 6–7 | UBIN | XftUnits | | X Units per Unit Base | M | ✅ |
| FOCA-6-115 | 8–9 | UBIN | YftUnits | | Y Units per Unit Base | M | ✅ |
| FOCA-6-116 | 10–11 | UBIN | MaxBoxWd | 0–32,767 | Max Character Box Width | M | ✅ |
| FOCA-6-117 | 12–13 | UBIN | MaxBoxHt | 0–32,767 | Max Character Box Height | M | ✅ |
| FOCA-6-118 | 14 | UBIN | FNORGLen | X'1A' | FNO Repeating Group Length | M | ✅ |
| FOCA-6-119 | 15 | UBIN | FNIRGLen | | FNI Repeating Group Length | M | ✅ |
| FOCA-6-120 | 16 | CODE | PatAlign | X'00', X'02', X'03' | Pattern Data Alignment Code | M | ✅ |
| FOCA-6-121 | 17–19 | UBIN | RPatDCnt | | Raster Pattern Data Count | M | ✅ |
| FOCA-6-122 | 20 | UBIN | FNPRGLen | X'16' | FNP Repeating Group Length | M | ✅ |
| FOCA-6-123 | 21 | UBIN | FNMRGLen | X'00', X'08' | FNM Repeating Group Length | M | ✅ |
| FOCA-6-124 | 22 | CODE | ResXUBase | X'00' | Shape resolution X Unit Base | O | ✅ |
| FOCA-6-125 | 23 | CODE | ResYUBase | X'00' | Shape resolution Y Unit Base | O | ✅ |
| FOCA-6-126 | 24–25 | UBIN | XfrUnits | | Shape resolution X units | O | ✅ |
| FOCA-6-127 | 26–27 | UBIN | YfrUnits | | Shape resolution Y units | O | ✅ |
| FOCA-6-128 | 28–31 | UBIN | OPatDCnt | | Outline Pattern Data Count | O | ✅ |
| FOCA-6-129 | 32–34 | UNDF | Reserved | X'000000' | Reserved | O | ✅ |
| FOCA-6-130 | 35 | UBIN | FNNRGLen | X'0C' | FNN Repeating Group Length | O | ✅ |
| FOCA-6-131 | 36–39 | UBIN | FNNDCnt | | FNN Data Count | O | ✅ |
| FOCA-6-132 | 40–41 | UBIN | FNNMapCnt | 0–65,535 | FNN Name Count | O | ✅ |
| FOCA-6-133 | 42–nn | Triplets | | | Self-Defining Triplets | O | ✅ |
| FOCA-6-134 | The Font Descriptor (FND) specifies the overall characteristics of a font character set. | ✅ |
| FOCA-6-135 | 0–31 | CHAR | TypeFcDesc | | Typeface Description | M | ✅ |
| FOCA-6-136 | 32 | CODE | FtWtClass | 1–9 | Weight Class | M | ✅ |
| FOCA-6-137 | 33 | CODE | FtWdClass | 1–9 | Width Class | M | ✅ |
| FOCA-6-138 | 34–35 | UBIN | MaxPtSize | | Max Vertical Size (10ths of Pt) | M | ✅ |
| FOCA-6-139 | 36–37 | UBIN | NomPtSize | | Nominal Vertical Size | M | ✅ |
| FOCA-6-140 | 38–39 | UBIN | MinPtSize | | Min Vertical Size | M | ✅ |
| FOCA-6-141 | 40–41 | UBIN | MaxHSize | 0–X'7FFE' | Max Horizontal Size (1440ths) | M | ✅ |
| FOCA-6-142 | 42–43 | UBIN | NomHSize | 0–X'7FFE' | Nominal Horizontal Size | M | ✅ |
| FOCA-6-143 | 44–45 | UBIN | MinHSize | 0–X'7FFE' | Minimum Horizontal Size | M | ✅ |
| FOCA-6-144 | 46 | CODE | DsnGenCls | 0–255 | Design General Class (ISO) | M | ✅ |
| FOCA-6-145 | 47 | CODE | DsnSubCls | 0–255 | Design Subclass (ISO) | M | ✅ |
| FOCA-6-146 | 48 | CODE | DsnSpcGrp | 0–255 | Design Specific Group (ISO) | M | ✅ |
| FOCA-6-147 | 49–63 | UBIN | Reserved | | Reserved | M | ✅ |
| FOCA-6-148 | 64–65 | BITS | FtDsFlags | See Below | Font Design Flags | M | ✅ |
| FOCA-6-149 | 66–75 | UBIN | Reserved | | Reserved | M | ✅ |
| FOCA-6-150 | 76–77 | UBIN | GCSGID | | Graphic Character Set GID | M | ✅ |
| FOCA-6-151 | 78–79 | UBIN | FGID | | Font Typeface GID (FGID) | M | ✅ |
| FOCA-6-152 | 80–nn | Triplets | | | Self Defining Triplets (X'02') | O | ✅ |
| FOCA-6-153 | The Font Patterns (FNG) structured field carries the character shape data (raster patterns or outline data). | ✅ |
| FOCA-6-154 | Outline Font Repeating Group:** | ✅ |
| FOCA-6-155 | 0–3 | UBIN | OFLLen | | Length of the outline font object | M | ✅ |
| FOCA-6-156 | 4–7 | UBIN | Checksum | | Checksum value | M | ✅ |
| FOCA-6-157 | 8–9 | UBIN | TIDLen | | Technology-specific ID length | M | ✅ |
| FOCA-6-158 | 10–n | UBIN | TIDName | | Technology-specific ID | O | ✅ |
| FOCA-6-159 | n+1..n+2 | UBIN | ODescLen | | Descriptor length | O | ✅ |
| FOCA-6-160 | n+3..m | UNDF | ObjDesc | | Descriptor data | O | ✅ |
| FOCA-6-161 | m+1..end | UNDF | ObjData | | Object data | O | ✅ |
| FOCA-6-162 | For each character in a raster font, the Font Index (FNI) describes specific characteristics and points to an entry in the Font Patterns Map (FNM). | ✅ |
| FOCA-6-163 | Repeating Group Definition:** | ✅ |
| FOCA-6-164 | 0–7 | CHAR | GCGID | | Graphic Character Global ID | M | ✅ |
| FOCA-6-165 | 8–9 | UBIN | CharInc | | Character Increment | M | ✅ |
| FOCA-6-166 | 10–11 | SBIN | AscendHt | | Ascender Height | O | ✅ |
| FOCA-6-167 | 12–13 | SBIN | DescendDp | | Descender Depth | O | ✅ |
| FOCA-6-168 | 14–15 | UBIN | Reserved | X'0000' | Reserved | O | ✅ |
| FOCA-6-169 | 16–17 | UBIN | FNMCnt | | FNM Index | O | ✅ |
| FOCA-6-170 | 18–19 | SBIN | ASpace | | A-Space | O | ✅ |
| FOCA-6-171 | 20–21 | UBIN | BSpace | | B-space | O | ✅ |
| FOCA-6-172 | 22–23 | SBIN | CSpace | | C-Space | O | ✅ |
| FOCA-6-173 | 24–25 | UBIN | Reserved | X'0000' | Reserved | O | ✅ |
| FOCA-6-174 | 26–27 | SBIN | BaseOset | | Baseline Offset | O | ✅ |
| FOCA-6-175 | The Font Patterns Map (FNM) structured field describes some characteristics of the font character patterns. | ✅ |
| FOCA-6-176 | Repeating Group Definition:** | ✅ |
| FOCA-6-177 | 0–1 | UBIN | CharBoxWd | | Character Box Width | M | ✅ |
| FOCA-6-178 | 2–3 | UBIN | CharBoxHt | | Character Box Height | M | ✅ |
| FOCA-6-179 | 4–7 | UBIN | PatDOset | | Pattern Data Offset | M | ✅ |
| FOCA-6-180 | The Font Name Map is used to map IBM character names to the character names in outline fonts. | ✅ |
| FOCA-6-181 | 0 | CODE | IBM format | X'02' | IBM character ID format | M | ✅ |
| FOCA-6-182 | 1 | CODE | Technology | X'03', X'05' | Tech-specific ID format | M | ✅ |
| FOCA-6-183 | Section 2 (Repeating Groups):** | ✅ |
| FOCA-6-184 | 0–7 | UNDF | GCGID | | Graphic Character Global ID | O | ✅ |
| FOCA-6-185 | 8–11 | UBIN | TSOffset | | Tech-specific identifier offset | O | ✅ |
| FOCA-6-186 | Section 3 (Variable Length):** | ✅ |
| FOCA-6-187 | 0 | UBIN | TSIDLen | 2–128 | Tech-specific ID length | O | ✅ |
| FOCA-6-188 | 1–n | UNDF | TSID | | Tech-specific identifier | O | ✅ |
| FOCA-6-189 | Offset | Meaning | Value | Comments | ✅ |
| FOCA-6-190 | 0 | GCGID Type | X'02' | Second section in EBCDIC | ✅ |
| FOCA-6-191 | 1 | GCGID Type | X'03' | Third section in Adobe ASCII | ✅ |
| FOCA-6-192 | 2–9 | GCGID | LA010000 | GCGID for lowercase 'a' | ✅ |
| FOCA-6-193 | 10–13 | Offset | X'0000005F' | 95 | ✅ |
| FOCA-6-194 | 14–21 | GCGID | LA020000 | GCGID for uppercase 'A' | ✅ |
| FOCA-6-195 | 22–25 | Offset | X'00000061' | 97 | ✅ |
| FOCA-6-196 | ... | ... | ... | ... | ✅ |
| FOCA-6-197 | 95 | TSID Length | 2 | ✅ |
| FOCA-6-198 | 96 | TSID | a | ✅ |
| FOCA-6-199 | 97 | TSID Length | 2 | ✅ |
| FOCA-6-200 | 98 | TSID | A | ✅ |
| FOCA-6-201 | Each repeating group in the Font Orientation (FNO) structured field applies to one character rotation. | ✅ |
| FOCA-6-202 | Repeating Group Definition:** | ✅ |
| FOCA-6-203 | 0–1 | UBIN | Reserved | X'0000' | Reserved | M | ✅ |
| FOCA-6-204 | 2–3 | UBIN | CharRot | | Character Rotation | M | ✅ |
| FOCA-6-205 | 4–5 | SBIN | MaxBOset | | Max Baseline Offset | M | ✅ |
| FOCA-6-206 | 6–7 | UBIN | MaxCharInc | | Max Character Increment | M | ✅ |
| FOCA-6-207 | 8–9 | UBIN | SpCharInc | | Space Character Increment | M | ✅ |
| FOCA-6-208 | 10–11 | UBIN | MaxBExt | | Max Baseline Extent | M | ✅ |
| FOCA-6-209 | 12 | BITS | OrntFlgs | | Orientation Control Flags | M | ✅ |
| FOCA-6-210 | 13 | UBIN | Reserved | X'00' | Reserved | M | ✅ |
| FOCA-6-211 | 14–15 | UBIN | EmSpInc | | Em-Space Increment | M | ✅ |
| FOCA-6-212 | 16–17 | UBIN | Reserved | X'0000' | Reserved | M | ✅ |
| FOCA-6-213 | 18–19 | UBIN | FigSpInc | | Figure Space Increment | M | ✅ |
| FOCA-6-214 | 20–21 | UBIN | NomCharInc | | Nominal Character Increment | M | ✅ |
| FOCA-6-215 | 22–23 | UBIN | DefBInc | 0–65,535 | Default Baseline Increment | M | ✅ |
| FOCA-6-216 | 24–25 | SBIN | MinASp | | Minimum A-Space | M | ✅ |
| FOCA-6-217 | The Font Position (FNP) structured field describes the common characteristics of all characters. | ✅ |
| FOCA-6-218 | Repeating Group Definition:** | ✅ |
| FOCA-6-219 | 0–1 | UBIN | Reserved | X'0000' | Reserved | M | ✅ |
| FOCA-6-220 | 2–3 | SBIN | LcHeight | | Lowercase Height | M | ✅ |
| FOCA-6-221 | 4–5 | SBIN | CapMHt | | Cap-M Height | M | ✅ |
| FOCA-6-222 | 6–7 | SBIN | MaxAscHt | | Max Ascender Height | M | ✅ |
| FOCA-6-223 | 8–9 | SBIN | MaxDesDp | | Max Descender Depth | M | ✅ |
| FOCA-6-224 | 10–14 | UBIN | Reserved | | Reserved | M | ✅ |
| FOCA-6-225 | 15 | UBIN | Retired | X'01' | Retired Parameter | M | ✅ |
| FOCA-6-226 | 16 | UBIN | Reserved | X'00' | Reserved | M | ✅ |
| FOCA-6-227 | 17–18 | UBIN | UscoreWd | | Underscore Width (units) | M | ✅ |
| FOCA-6-228 | 19 | UBIN | UscoreWdf | X'00' | Underscore Width (fraction) | M | ✅ |
| FOCA-6-229 | 20–21 | SBIN | UscorePos | | Underscore Position | M | ✅ |
| FOCA-6-230 | Can carry comments or unarchitected data. | ✅ |
| FOCA-6-231 | 0–end | UNDF | NopData | Any value | Comment data | O | ✅ |
| FOCA-6-232 | Enables identification using Global Identifiers. | ✅ |
| FOCA-6-233 | 0 | UBIN | TripLen | 5–254 | Triplet Length | M | ✅ |
| FOCA-6-234 | 1 | CODE | TripID | X'02' | Triplet Identifier | M | ✅ |
| FOCA-6-235 | 2 | CODE | FQNType | X'07', X'08' | GID usage type | M | ✅ |
| FOCA-6-236 | 3 | | | 0 | Reserved | M | ✅ |
| FOCA-6-237 | 4–253 | CHAR | FQName | | GID of the object | M | ✅ |
| FOCA-6-238 | Identifies object creation/revision date and time. | ✅ |
| FOCA-6-239 | 0 | UBIN | TripLen | 17 | Triplet Length | M | ✅ |
| FOCA-6-240 | 1 | CODE | TripID | X'62' | Triplet Identifier | M | ✅ |
| FOCA-6-241 | 2 | CODE | TSType | 0, 1, 3 | Time Stamp Type | M | ✅ |
| FOCA-6-242 | ... | ... | ... | ... | ... | ... | ✅ |
| FOCA-6-243 | Offset | Type | Name | Range | Meaning | M/O | ✅ |
| FOCA-6-244 | 0 | UBIN | TripLen | 6 | Triplet Length | M | ✅ |
| FOCA-6-245 | 1 | CODE | TripID | X'63' | Triplet Identifier | M | ✅ |
| FOCA-6-246 | 2 | CODE | FmtQual | X'01' | Format Qualifier | M | ✅ |
| FOCA-6-247 | 3–4 | UBIN | RMValue | | Retired RM value | M | ✅ |
| FOCA-6-248 | 5 | BITS | ResClassFlg | | Resource Class Flags | M | ✅ |
| FOCA-6-249 | Offset | Type | Name | Range | Meaning | M/O | ✅ |
| FOCA-6-250 | 0 | UBIN | TripLen | 61 | Triplet Length | M | ✅ |
| FOCA-6-251 | 1 | CODE | TripID | X'64' | Triplet Identifier | M | ✅ |
| FOCA-6-252 | 2 | CODE | FmtQual | | Format Qualifier | M | ✅ |
| FOCA-6-253 | 3–10 | CHAR | HostID | | Host/System Identifier | M | ✅ |
| FOCA-6-254 | 11–16 | CHAR | MediaID | | Media Identifier | M | ✅ |
| FOCA-6-255 | 17–60 | CHAR | DataSID | | Data Set Identifier | M | ✅ |
| FOCA-6-256 | Offset | Type | Name | Range | Meaning | M/O | ✅ |
| FOCA-6-257 | 0 | UBIN | TripLen | 4 | Triplet Length | M | ✅ |
| FOCA-6-258 | 1 | CODE | TripID | X'6D' | Triplet Identifier | M | ✅ |
| FOCA-6-259 | 2–3 | CODE | GCSGID | | Base font GCSGID | M | ✅ |
| FOCA-6-260 | Offset | Type | Name | Range | Meaning | M/O | ✅ |
| FOCA-6-261 | 0 | UBIN | Length | X'0F' | Triplet Length | M | ✅ |
| FOCA-6-262 | 1 | CODE | TID | X'79' | Triplet Identifier | M | ✅ |
| FOCA-6-263 | 2 | CODE | UnitBase | X'00' | Unit base | M | ✅ |
| FOCA-6-264 | 3–4 | UBIN | XUPUB | | Units per unit base X | M | ✅ |
| FOCA-6-265 | 5–6 | UBIN | YUPUB | | Units per unit base Y | M | ✅ |
| FOCA-6-266 | 7–8 | SBIN | H-Inc | | Horizontal increment | M | ✅ |
| FOCA-6-267 | 9–10 | SBIN | V-Inc | | Vertical increment | M | ✅ |
| FOCA-6-268 | 11–12 | SBIN | H-Base | | Horizontal baseline adjustment | M | ✅ |
| FOCA-6-269 | 13–14 | SBIN | V-Base | | Vertical baseline adjustment | M | ✅ |
| FOCA-6-270 | SAA CPI System Font Resource**: Query Font Metrics calls. | ✅ |
| FOCA-6-271 | IPDS Device Font Resource**: Loaded-Font Command Set. | ✅ |
| FOCA-6-272 | MO:DCA Presentation Document Font Reference**: Map Coded Font (MCF). | ✅ |
| FOCA-6-273 | SAA CPI System Font Reference**: Create Logical Font program call. | ✅ |
| FOCA-6-274 | IPDS Device Font Reference**: Load Font Equivalence (LFE) command. | ✅ |
| FOCA-7-001 | Compliance to the font architecture is defined in terms of semantic or syntactic support of a subset of a FOCA function set. FOCA function sets are defined as a list of font parameters. The font parameters making up a FOCA Function Set are divided into three subsets, representing those parameters required for font referencing (the minimal subset), character positioning (minimal subset plus font and character positioning parameters), and character presentation or font interchange (the full or complete function set). | ✅ |
| FOCA-7-002 | Semantic support** means that a product supports one of the three subsets of parameters defined for a function set, the parameter definitions, and the parameter range of values. The information may be retained in any product-defined syntax, arrangement, or packaging. In addition, a product may use the formal parameter name, the acronym, a product-defined synonym, or a local identifier. | ✅ |
| FOCA-7-003 | Syntactic support** means that the product supports the interchange format for one of the subsets of the function set, in addition to the semantic support. The number of parameters supported under semantic support is a subset of those supported under syntactic support because additional control parameters are required to define the syntactic relationship (maps, pointers, and indices) between parameters. | ✅ |
| FOCA-7-004 | Syntactic support includes the order of parameter occurrence (if order is prescribed), the encoding of parameter identities and values, and any additional parameters required for management of the structure. Syntactic support of an interchange function set of the font architecture requires support of the MO:DCA-defined resource-object wrappers (for example, BRG/ERG). | ✅ |
| FOCA-7-005 | It is the responsibility of each product which uses or provides font resource information to designate, in its product specification, the FOCA function set and subset supported. If the product is a system consisting of several products or modules, the functional specification should indicate the function set and subset supported by each of the applicable products or modules. | ✅ |
| FOCA-7-006 | The following items define the compliance requirements for each of four different classes of products and architectures: | ✅ |
| FOCA-7-007 | Document Editors and Revisable Document Data Streams** | ✅ |
| FOCA-7-008 | This class includes any program (or module of a larger program) that processes text input from a user and generates a revisable document data stream containing references to the user identified or described font resources. Editing products must designate the document data stream architectures that they support. The revisable document data stream architecture, if it supports the font architecture, must designate the function set supported and at least semantic support of the referencing subset of that function set. | ✅ |
| FOCA-7-009 | Font Services and Font Service Programming Interface** | ✅ |
| FOCA-7-010 | This class includes any font utility, resource management program or font service programming interface that manages or provides font resource information to another product, including any communication service program that accesses, stores, or transforms font resource information for interchange. It also includes any font generation program or utility that generates, transforms, or modifies font resource information. A product or interface specification in this class, which supports the font architecture, must designate the function set supported and provide syntactic support of the parameters in that function set. | ✅ |
| FOCA-7-011 | Note:** For any product that performs a transformation on font resources (to or from an internal format used by another product), one side of the transformation must be in the interchange format. | ✅ |
| FOCA-7-012 | Document Formatters and Final-Form Document Data Streams** | ✅ |
| FOCA-7-013 | This class includes any program (or module of a larger program) that uses character positioning information from a font resource to assist in the determination of the document format (for example, line breaks, column alignment, page breaks, and character string content), and the final-form document data stream used to represent that format. Any formatting product, which supports the font architecture, must designate the function set supported and at least semantic support of the font and character positioning subset. The final-form document data stream, if it supports the font architecture, must designate the function set supported and at least semantic support of the referencing subset. | ✅ |
| FOCA-7-014 | Note:** Formatting product support of the font architecture does not necessarily imply revision or final-form data stream support of the font architecture, although this might require complex data transformations and reference tables. | ✅ |
| FOCA-7-015 | Document Presentation and Presentation Service Data Streams** | ✅ |
| FOCA-7-016 | This class includes any program (or module of a larger program) or device that uses font resource information to assist in the generation of character images on the presentation surface, and it includes the presentation service or device service data streams required to control the presentation process. Any presentation product or presentation service data stream, which supports the font architecture, must designate the function set supported and at least the semantic support of the parameters for that function set. | ✅ |
| FOCA-7-017 | This font architecture fully supports all known IBM requirements for national language support, including multidirectional text and multibyte document encoding. It is, however, the responsibility of implementing products to provide the necessary collections of font information required to support the national language variations required by their product. That is, the font architecture provides for the definition of metric information for support of multiple character rotations, but the implementing product is responsible for providing the character positioning information for those rotations. | ✅ |
| FOCA-A-001 | The following table lists the FOCA structured fields defined for AFP System Font Resources (coded fonts, code pages, and font character sets). The table is sorted by the hexadecimal structured field identifier. | ✅ |
| FOCA-A-002 | X'D38C87' | Code Page Index (CPI) | [CPI](Chapter_6.md#cpi--d38c87--code-page-index) | ✅ |
| FOCA-A-003 | X'D38C89' | Font Index (FNI) | [FNI](Chapter_6.md#fni--d38c89--font-index) | ✅ |
| FOCA-A-004 | X'D38C8A' | Coded Font Index (CFI) | [CFI](Chapter_6.md#cfi--d38c8a--coded-font-index) | ✅ |
| FOCA-A-005 | X'D3A289' | Font Patterns Map (FNM) | [FNM](Chapter_6.md#fnm--d3a289--font-patterns-map) | ✅ |
| FOCA-A-006 | X'D3A687' | Code Page Descriptor (CPD) | [CPD](Chapter_6.md#cpd--d3a687--code-page-descriptor) | ✅ |
| FOCA-A-007 | X'D3A689' | Font Descriptor (FND) | [FND](Chapter_6.md#fnd--d3a689--font-descriptor) | ✅ |
| FOCA-A-008 | X'D3A787' | Code Page Control (CPC) | [CPC](Chapter_6.md#cpc--d3a787--code-page-control) | ✅ |
| FOCA-A-009 | X'D3A789' | Font Control (FNC) | [FNC](Chapter_6.md#fnc--d3a789--font-control) | ✅ |
| FOCA-A-010 | X'D3A78A' | Coded Font Control (CFC) | [CFC](Chapter_6.md#cfc--d3a78a--coded-font-control) | ✅ |
| FOCA-A-011 | X'D3A887' | Begin Code Page (BCP) | [BCP](Chapter_6.md#bcp--d3a887--begin-code-page) | ✅ |
| FOCA-A-012 | X'D3A889' | Begin Font (BFN) | [BFN](Chapter_6.md#bfn--d3a889--begin-font) | ✅ |
| FOCA-A-013 | X'D3A88A' | Begin Coded Font (BCF) | [BCF](Chapter_6.md#bcf--d3a88a--begin-coded-font) | ✅ |
| FOCA-A-014 | X'D3A987' | End Code Page (ECP) | [ECP](Chapter_6.md#ecp--d3a987--end-code-page) | ✅ |
| FOCA-A-015 | X'D3A989' | End Font (EFN) | [EFN](Chapter_6.md#efn--d3a989--end-font) | ✅ |
| FOCA-A-016 | X'D3A98A' | End Coded Font (ECF) | [ECF](Chapter_6.md#ecf--d3a98a--end-coded-font) | ✅ |
| FOCA-A-017 | X'D3AB89' | Font Name Map (FNN) | [FNN](Chapter_6.md#fnn--d3ab89--font-name-map) | ✅ |
| FOCA-A-018 | X'D3AC89' | Font Position (FNP) | [FNP](Chapter_6.md#fnp--d3ac89--font-position) | ✅ |
| FOCA-A-019 | X'D3AE89' | Font Orientation (FNO) | [FNO](Chapter_6.md#fno--d3ae89--font-orientation) | ✅ |
| FOCA-A-020 | X'D3EE89' | Font Patterns (FNG) | [FNG](Chapter_6.md#fng--d3ee89--font-patterns) | ✅ |
| FOCA-A-021 | X'D3EEEE' | No Operation (NOP) | [NOP](Chapter_6.md#nop--d3eeee--no-operation) | ✅ |
| FOCA-A-022 | The following table lists the FOCA triplets defined for AFP System Font Resources. The table is sorted by the hexadecimal triplet identifier. | ✅ |
| FOCA-A-023 | X'02' | Fully Qualified Name triplet | [X'02'](Chapter_6.md#x02--fully-qualified-name-triplet) | ✅ |
| FOCA-A-024 | X'62' | Local Date and Time Stamp Triplet | [X'62'](Chapter_6.md#x62--local-date-and-time-stamp-triplet) | ✅ |
| FOCA-A-025 | X'63' | CRC Resource Management triplet | [X'63'](Chapter_6.md#x63-type-1--crc-resource-management-triplet) | ✅ |
| FOCA-A-026 | X'63' | Font Resource Management triplet | [X'63'](Chapter_6.md#x63-type-2--font-resource-management-triplet) | ✅ |
| FOCA-A-027 | X'64' | Object Origin Identifier triplet | [X'64'](Chapter_6.md#x64--object-origin-identifier-triplet) | ✅ |
| FOCA-A-028 | X'65' | User Comment triplet | [X'65'](Chapter_6.md#x65--user-comment-triplet) | ✅ |
| FOCA-A-029 | X'6D' | Extension Font triplet | [X'6D'](Chapter_6.md#x6d--extension-font-triplet) | ✅ |
| FOCA-A-030 | X'79' | Metric Adjustment triplet | [X'79'](Chapter_6.md#x79--metric-adjustment-triplet) | ✅ |
| FOCA-B-001 | This appendix provides information to aid in understanding the relation between the parameters in this architecture and the parameters defined by the ISO/IEC 9541-1 Font Information Interchange standard. Detailed information about the transformation between ISO and FOCA parameters is defined with each FOCA parameter in the body of this document. | ✅ |
| FOCA-B-002 | FONTNAME** (Req.) | Resource Name | ✅ |
| FOCA-B-003 | DATAVERSION** (Opt.) | Function Set Code | ✅ |
| FOCA-B-004 | STANDARDVERSION** (Req.) | Function Set Code | ✅ |
| FOCA-B-005 | DATASOURCE** (Opt.) | Data Source | ✅ |
| FOCA-B-006 | DATACOPYRIGHT** (Opt.) | Intellectual Property Data | ✅ |
| FOCA-B-007 | DSNSOURCE** (Req.) | Design Source | ✅ |
| FOCA-B-008 | DSNCOPYRIGHT** (Opt.) | Intellectual Property Data | ✅ |
| FOCA-B-009 | RELUNITS** (Opt.) | Measurement Units | ✅ |
| FOCA-B-010 | TYPEFACE** (Opt.) | Typeface Name | ✅ |
| FOCA-B-011 | FONTFAMILY** (Req.) | Family Name | ✅ |
| FOCA-B-012 | POSTURE** (Req.) | Posture Class | ✅ |
| FOCA-B-013 | POSTUREANGLE** (Opt.) | Nominal Character Slope | ✅ |
| FOCA-B-014 | WEIGHT** (Req.) | Weight Class | ✅ |
| FOCA-B-015 | PROPWIDTH** (Req.) | Width Class | ✅ |
| FOCA-B-016 | STRUCTURE** (Req.) | Structure Class | ✅ |
| FOCA-B-017 | DSNGROUP** (Req.) | Design Group / Sub-Group / Specific Group / General Class / Sub-Class | ✅ |
| FOCA-B-018 | NUMGLYPHS** (Opt.) | Number of Characters | ✅ |
| FOCA-B-019 | INCGLYPHCOLS** (Req.) | Graphic Character Set Global ID | ✅ |
| FOCA-B-020 | INCGLYPHS** (Req.) | Graphic Character Global ID | ✅ |
| FOCA-B-021 | DSNSIZE** (Req.) | Nominal Vertical Font Size | ✅ |
| FOCA-B-022 | MINSIZE** (Req.) | Minimum Vertical Font Size | ✅ |
| FOCA-B-023 | MAXSIZE** (Req.) | Maximum Vertical Font Size | ✅ |
| FOCA-B-024 | CAPHEIGHT** (Opt.) | Cap-M Height | ✅ |
| FOCA-B-025 | LCHEIGHT** (Opt.) | X-Height | ✅ |
| FOCA-B-026 | WRMODENAME** (Opt.) | (derived from character rotation) | ✅ |
| FOCA-B-027 | NOMESCDIR** (Req.) | Character Rotation | ✅ |
| FOCA-B-028 | ESCCLASS** (Req.) | Proportional Spaced (flag) | ✅ |
| FOCA-B-029 | AVGESCX/Y** (Req.) | Average Escapement | ✅ |
| FOCA-B-030 | AVGLCESCX/Y** (Req.) | Average Lowercase Escapement | ✅ |
| FOCA-B-031 | AVGCAPESCX/Y** (Req.) | Average Capital Escapement | ✅ |
| FOCA-B-032 | TABESCX/Y** (Req.) | Figure Space Increment | ✅ |
| FOCA-B-033 | MAXFONTEXT** (Req.) | Maximum Character Box Height & Width | ✅ |
| FOCA-B-034 | SCOREOFFSETX/Y** (Opt.) | Underscore / Overscore / Throughscore Position | ✅ |
| FOCA-B-035 | SCORETHICK** (Opt.) | Underscore / Overscore / Throughscore Width | ✅ |
| FOCA-B-036 | VSOFFSETX/Y** (Opt.) | Superscript / Subscript X-Axis / Y-Axis Offset | ✅ |
| FOCA-B-037 | VSSCALEX/Y** (Opt.) | Superscript / Subscript Vertical / Horizontal Size | ✅ |
| FOCA-B-038 | MINLINESP** (Opt.) | Default Baseline Increment | ✅ |
| FOCA-B-039 | MINANASCALE** (Opt.) | Minimum Horizontal Font Size | ✅ |
| FOCA-B-040 | MAXANASCALE** (Opt.) | Maximum Horizontal Font Size | ✅ |
| FOCA-B-041 | COPYFITMEASURE** (Opt.) | Average Weighted Escapement | ✅ |
| FOCA-B-042 | GNAME** (Req.) | Graphic Character Global ID | ✅ |
| FOCA-B-043 | PX, PY** (Opt.) | (coordinate system origin) | ✅ |
| FOCA-B-044 | EX, EY** (Req.) | Character Increment | ✅ |
| FOCA-B-045 | EXT** (Req.) | A-Space, B-Space, C-Space, Ascender Height, Descender Depth | ✅ |
| FOCA-B-046 | PEAN** (Opt.) | (linkage by data structure, not name) | ✅ |
| FOCA-B-047 | PEAX** (Opt.) | Kerning Pair X Adjust | ✅ |
| FOCA-B-048 | Font Typeface Global ID | ✅ |
| FOCA-B-049 | Font Use Code | ✅ |
| FOCA-B-050 | Misc. Control Flags (Kerning Pair Data, MICR Font, etc.) | ✅ |
| FOCA-B-051 | Em-Space Increment | ✅ |
| FOCA-B-052 | Maximum/Minimum Character Slope | ✅ |
| FOCA-B-053 | Nominal Horizontal Font Size | ✅ |
| FOCA-B-054 | External/Internal Leading | ✅ |
| FOCA-B-055 | Maximum Ascender Height / Baseline Extent / Baseline Offset / Character Increment / Descender Depth | ✅ |
| FOCA-B-056 | Minimum A-Space | ✅ |
| FOCA-B-057 | Space Character Increment | ✅ |
| FOCA-B-058 | Character Box Height / Width | ✅ |
| FOCA-B-059 | Frequency of Use | ✅ |
| FOCA-C-001 | The following information defines the technologies which have been implemented for the definition of character shapes. Each technology is associated with a value of the Technology parameter in the Font Control Structured Field. This information is provided for information only and is not considered part of the font architecture, nor is it under control mode. | ✅ |
| FOCA-C-002 | The data format for this outline technology is documented in *Adobe Type 1 Font Format*, Adobe Systems Incorporated, 1990, and *Composite Font Extensions*, Adobe Systems Incorporated, 1989. | ✅ |
| FOCA-C-003 | The CID Keyed font files are totally contained within the FOCA Pattern Data parameter. | ✅ |
| FOCA-C-004 | All character string data within the CID Keyed font file uses the ASCII character encoding technique. | ✅ |
| FOCA-C-005 | The glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM Graphic Character Global Identifiers. Implementations must consider the possibility of different graphic character GID encodings and perform any necessary mapping. | ✅ |
| FOCA-C-006 | The data format for this outline technology is documented in *Adobe Type 1 Font Format*, Adobe Systems Incorporated, 1990. | ✅ |
| FOCA-C-007 | The Type 1 PFB file is totally contained within the FOCA Pattern Data parameter. | ✅ |
| FOCA-C-008 | All character string data within the Type 1 PFB file uses the ASCII character encoding technique. | ✅ |
| FOCA-C-009 | The PFB glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM Graphic Character Global Identifiers. Implementations must consider the possibility of different graphic character GID encodings and perform any necessary mapping. | ✅ |
| FOCA-C-010 | The data format for the TrueType outline technology is documented in the *TrueType Font Files Technical Specification* from Microsoft® Corporation and the *TrueType Reference Manual* from Apple Computer, Inc. | ✅ |
| FOCA-C-011 | The OpenType font format is an extension of the TrueType font format that allows better support for international character sets and broader multi-platform support. The OpenType font format, which was developed jointly by the Adobe and Microsoft Corporations, is further described in the OpenType Specification from Microsoft. | ✅ |
| FOCA-C-012 | Note:** These technologies are not supported within FOCA font objects, but are supported within AFP data streams as data object resources. | ✅ |
| FOCA-C-013 | An image is formatted as a single rectangle in the binary element sequence of a unidirectional raster scan with no interlaced fields and with parallel raster lines, from left to right (plus x-direction), from top to bottom (plus y-direction). Each binary element representing a pel, after decompression, without grayscale, is 0 for no dot, 1 for dot. More than one binary element can represent a pel, after decompression, corresponding to a grayscale algorithm. A pel is nominally centered about the point at which it appears. | ✅ |
| FOCA-C-014 | Scan lines may range from 1 bit wide to the device limit. | ✅ |
| FOCA-C-015 | The AFP Consortium or consortium member companies might have patents or pending patent applications covering subject matter described in this document. The furnishing of this document does not give you any license to these patents. | ✅ |
| FOCA-C-016 | The following statement does not apply to the United Kingdom or any other country where such provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICATION "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Some states do not allow disclaimer of express or implied warranties in certain transactions, therefore, this statement might not apply to you. | ✅ |
| FOCA-C-017 | This publication could include technical inaccuracies or typographical errors. Changes are periodically made to the information herein; these changes will be incorporated in new editions of the publication. The AFP Consortium might make improvements and/or changes in the architecture described in this publication at any time without notice. | ✅ |
| FOCA-C-018 | Any references in this publication to Web sites are provided for convenience only and do not in any manner serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for this architecture and use of those Web sites is at your own risk. | ✅ |
| FOCA-C-019 | The AFP Consortium may use or distribute any information you supply in any way it believes appropriate without incurring any obligation to you. | ✅ |
| FOCA-C-020 | These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries, or both: ACMA, Advanced Function Presentation, AFP, AFPCC, AFP Color Consortium, AFP Color Management Architecture, Bar Code Object Content Architecture, BCOCA, CMOCA, Color Management Object Content Architecture, InfoPrint®, Intelligent Printer Data Stream, IPDS, Mixed Object Document Content Architecture, MO:DCA, Ricoh®. | ✅ |
| FOCA-C-021 | Adobe®, the Adobe logo, PostScript®, and the PostScript logo are either registered trademarks or trademarks of Adobe Systems Incorporated in the United States and/or other countries. | ✅ |
| FOCA-C-022 | AFPC and AFP Consortium are trademarks of the AFP Consortium. | ✅ |
| FOCA-C-023 | IBM® is a registered trademark of the International Business Machines Corporation. MVS, PrintManager, Print Services Facility, SAA®, and Systems Application Architecture® are trademarks of IBM. | ✅ |
| FOCA-C-024 | ISO® is a registered trademark of the International Organization for Standardization. | ✅ |
| FOCA-C-025 | Microsoft® is a registered trademark of the Microsoft Corporation. | ✅ |
| FOCA-C-026 | UP3I is a trademark of UP3I Limited. | ✅ |
