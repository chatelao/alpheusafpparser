# Granular Test Coverage - CMOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| CMOCA-1-001 | This chapter provides a brief overview of Presentation Architecture. | ✅ |
| CMOCA-1-002 | Figure 1 shows today's presentation environment. | ✅ |
| CMOCA-1-003 | Figure 1. Presentation Environment.** The environment is a coordinated set of services architected to meet the presentation needs of today's applications. | ✅ |
| CMOCA-1-004 | Document Creation Services** | ✅ |
| CMOCA-1-005 | import/export | ✅ |
| CMOCA-1-006 | edit/revise | ✅ |
| CMOCA-1-007 | format | ✅ |
| CMOCA-1-008 | transform | ✅ |
| CMOCA-1-009 | Document Archiving Services** | ✅ |
| CMOCA-1-010 | retrieve | ✅ |
| CMOCA-1-011 | search | ✅ |
| CMOCA-1-012 | extract | ✅ |
| CMOCA-1-013 | Document Viewing Services** | ✅ |
| CMOCA-1-014 | browse | ✅ |
| CMOCA-1-015 | navigate | ✅ |
| CMOCA-1-016 | search | ✅ |
| CMOCA-1-017 | annotate | ✅ |
| CMOCA-1-018 | Document Printing Services** | ✅ |
| CMOCA-1-019 | submit | ✅ |
| CMOCA-1-020 | distribute | ✅ |
| CMOCA-1-021 | manage | ✅ |
| CMOCA-1-022 | finish | ✅ |
| CMOCA-1-023 | The ability to create, store, retrieve, view, and print data in presentation formats friendly to people is a key requirement in almost every application of computers and information processing. This requirement is becoming increasingly difficult to meet because of the number of applications, servers, and devices that must interoperate to satisfy today's presentation needs. | ✅ |
| CMOCA-1-024 | The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP architectures provide structures that support object-oriented models and client/server environments. | ✅ |
| CMOCA-1-025 | AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ✅ |
| CMOCA-1-026 | AFP architectures provide the means for representing documents in a data format that is independent of the methods used to capture or create them. Documents can contain combinations of text, image, graphics, and bar code objects in presentation-system-independent and resolution-independent formats. Documents can contain fonts, overlays, and other resource objects required at presentation time to present the data properly. Finally, documents can contain resource objects, such as a document index and tagging elements supporting the search and navigation of document data, for a variety of application purposes. | ✅ |
| CMOCA-1-027 | The presentation architecture components are divided into two major categories: data streams and objects. | ✅ |
| CMOCA-1-028 | A data stream is a continuous ordered stream of data elements and objects conforming to a given format. Application programs can generate data streams destined for a presentation service, archive library, presentation device, or another application program. The strategic presentation data stream architectures are: | ✅ |
| CMOCA-1-029 | Mixed Object Document Content Architecture (MO:DCA)** | ✅ |
| CMOCA-1-030 | Intelligent Printer Data Stream (IPDS) Architecture** | ✅ |
| CMOCA-1-031 | The MO:DCA architecture defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. The MO:DCA format supports storing and retrieving documents in an archive, viewing, annotation, and printing of documents or parts of documents in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them. | ✅ |
| CMOCA-1-032 | The IPDS architecture defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers. | ✅ |
| CMOCA-1-033 | Figure 2 shows a system model relating MO:DCA and IPDS data streams to the presentation environment previously described. Also shown in the model are the object content architectures that apply to all levels of presentation processing in a system. | ✅ |
| CMOCA-1-034 | Figure 2. Presentation Model.** This diagram shows the major components in a presentation system and their use of data stream and object architectures. | ✅ |
| CMOCA-1-035 | Data Objects** | ✅ |
| CMOCA-1-036 | Graphics | ✅ |
| CMOCA-1-037 | Bar Codes | ✅ |
| CMOCA-1-038 | Object Containers | ✅ |
| CMOCA-1-039 | Other Objects | ✅ |
| CMOCA-1-040 | Resource Objects** | ✅ |
| CMOCA-1-041 | Overlays | ✅ |
| CMOCA-1-042 | Page Segments | ✅ |
| CMOCA-1-043 | Form Definition | ✅ |
| CMOCA-1-044 | Color Management Resources | ✅ |
| CMOCA-1-045 | Color Table | ✅ |
| CMOCA-1-046 | Document Index | ✅ |
| CMOCA-1-047 | Metadata | ✅ |
| CMOCA-1-048 | Documents can be made up of different kinds of data, such as text, graphics, image, and bar code. Object content architectures describe the structure and content of each type of data format that can exist in a document or appear in a data stream. Objects can be either data objects or resource objects. | ✅ |
| CMOCA-1-049 | A data object contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data. | ✅ |
| CMOCA-1-050 | A resource object is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them. | ✅ |
| CMOCA-1-051 | All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects that can be individually positioned and manipulated to meet the needs of the presentation application. | ✅ |
| CMOCA-1-052 | The AFPC-defined object content architectures are: | ✅ |
| CMOCA-1-053 | Presentation Text Object Content Architecture (PTOCA):** A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition. | ✅ |
| CMOCA-1-054 | Image Object Content Architecture (IOCA):** A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition. | ✅ |
| CMOCA-1-055 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA):** A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition. | ✅ |
| CMOCA-1-056 | Bar Code Object Content Architecture (BCOCA):** A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition. | ✅ |
| CMOCA-1-057 | Font Object Content Architecture (FOCA):** A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document. | ✅ |
| CMOCA-1-058 | Color Management Object Content Architecture (CMOCA):** A resource architecture used to carry the color management information required to render presentation data. | ✅ |
| CMOCA-1-059 | Metadata Object Content Architecture (MOCA):** A resource architecture used to carry metadata in an AFP environment. | ✅ |
| CMOCA-1-060 | The MO:DCA and IPDS architectures also support data objects that are not defined by object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures. | ✅ |
| CMOCA-1-061 | In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document. | ✅ |
| CMOCA-1-062 | Figure 3 shows an example of an all-points-addressable page composed of multiple presentation objects. | ✅ |
| CMOCA-1-063 | Figure 3. Presentation Page.** This is an example of a mixed-object page that can be composed in a presentation-system-independent MO:DCA format and printed on an IPDS printer. | ✅ |
| CMOCA-2-001 | The Color Management Object Content Architecture (CMOCA) defines objects that provide color management in presentation environments. These objects are called Color Management Resources (CMRs). CMOCA has the following objectives: | ✅ |
| CMOCA-2-002 | Consistent output across different devices | ✅ |
| CMOCA-2-003 | Accurate output, to the best of the device capability, from a wide variety of inputs provided that the input color information is properly described | ✅ |
| CMOCA-2-004 | Consistent output across different data streams | ✅ |
| CMOCA-2-005 | Flexible controls that enable customers to obtain output to their exact specifications | ✅ |
| CMOCA-2-006 | The architecture described in this document is defined in the terms of the AFP architectures to support color management in AFP environments, but care has been taken to make the mechanisms applicable to other presentation environments as well. | ✅ |
| CMOCA-2-007 | The device that presents the data could be a printer, a display, or other system. This document frequently references printers but it should be understood that the architecture also applies to displays and other presentation devices. | ✅ |
| CMOCA-2-008 | A Color Management Resource (CMR) is an architected resource that is used to carry the color management information required to render a print file, document, page, or data object. Each CMR carries a single type of color management resource. There are five types of CMRs: | ✅ |
| CMOCA-2-009 | 1.  Halftone | ✅ |
| CMOCA-2-010 | 2.  Tone Transfer Curve | ✅ |
| CMOCA-2-011 | 3.  Color Conversion | ✅ |
| CMOCA-2-012 | 4.  Link Color Conversion | ✅ |
| CMOCA-2-013 | 5.  Indexed | ✅ |
| CMOCA-2-014 | Note:** Not all CMR types are applicable for a particular kind of presentation device; for instance, halftones are not applicable for a display. | ✅ |
| CMOCA-2-015 | A CMR can reflect processing that has been done on an object, in which case it is referred to as an **audit CMR**, or it can specify processing that is to be done to an object, in which case it is referred to as an **instruction CMR**. Finally, there is a special case of an audit and instruction color conversion pair that has been combined to produce a link color conversion. This combined color conversion is called a **link CMR**. | ✅ |
| CMOCA-2-016 | In AFP environments, CMRs are processed as AFP resources by print servers so they can be downloaded once, captured, and used repeatedly without additional downloads. CMRs are also applicable to non-AFP environments such as PostScript, PDF, and PCL®. | ✅ |
| CMOCA-2-017 | The primary purpose of the Color Management Object Content Architecture is to provide a standard definition for color management resources that are used for controlling presentation of color objects. “Color objects”, as used in the document, means full-color, grayscale, and monochrome objects. This standardization provides conventions and directions for current and future products to present objects in a consistent way. | ✅ |
| CMOCA-2-018 | Development of CMOCA has the following goals: | ✅ |
| CMOCA-2-019 | To allow a means to represent color management information in any environment | ✅ |
| CMOCA-2-020 | To use a format that is flexible enough to allow it to exist intact in interactive, presentation, and interchange environments that are defined in the following data stream architectures: | ✅ |
| CMOCA-2-021 | Intelligent Printer Data Stream (IPDS) and | ✅ |
| CMOCA-2-022 | Mixed Object Document Content Architecture (MO:DCA) | ✅ |
| CMOCA-2-023 | To describe the CMR in terms of architected tags | ✅ |
| CMOCA-2-024 | To use industry-standard constructs when architecting the CMRs | ✅ |
| CMOCA-2-025 | To allow the CMR to be fully described in device-independent and process-independent terms | ✅ |
| CMOCA-2-026 | To use a naming convention for the CMRs that allow device-specific color resources to be substituted for generic resources | ✅ |
| CMOCA-2-027 | To define CMRs so that multiple CMRs can be invoked at one time, and a hierarchy can be searched to determine the appropriate CMRs to use | ✅ |
| CMOCA-2-028 | In AFP environments, CMRs will be carried within an object container. CMRs can be associated with a document component at various levels: | ✅ |
| CMOCA-2-029 | 1.  Print file | ✅ |
| CMOCA-2-030 | 2.  Document | ✅ |
| CMOCA-2-031 | 3.  A group of pages or sheets | ✅ |
| CMOCA-2-032 | 4.  Page/Overlay | ✅ |
| CMOCA-2-033 | 5.  Data Object - for example, IOCA, EPS, TIFF | ✅ |
| CMOCA-2-034 | Within the IPDS data stream, CMRs are activated and deactivated like all other IPDS resources but the CMR is not used until it is explicitly invoked (except for certain Link Color Conversions CMRs, that need not be invoked). Within the device, IPDS hierarchical rules are used to determine which CMRs are actually applied. | ✅ |
| CMOCA-3-001 | A CMR consists of a header followed by CMR data. | ✅ |
| CMOCA-3-002 | To start, for simplicity, assume that the source data is specified in a device-independent color space (that is, an ICC Profile Connection Space (PCS) such as CIEXYZ or CIELAB). The procedure for producing output uses the following sequence: | ✅ |
| CMOCA-3-003 | Figure 4. Procedure for Producing Halftone Output from PCS** | ✅ |
| CMOCA-3-004 | 1.  Color Convert from PCS to color space of output device | ✅ |
| CMOCA-3-005 | 2.  Color Calibration | ✅ |
| CMOCA-3-006 | 3.  Halftone Data | ✅ |
| CMOCA-3-007 | When color data has been processed using the above sequence, the resulting object can be stored in a database. It might be useful to keep an audit trail of the operations that were performed to create the object. This audit information can be merely descriptive, or it can be used to undo some of the operations performed on the object and thus restore it to the original form when it was expressed in the PCS color space. In this case, the inverse of each function is applied in this sequence: | ✅ |
| CMOCA-3-008 | Figure 5. Procedure for Converting Input Data to Full Color in PCS** | ✅ |
| CMOCA-3-009 | 1.  Undo Halftoning (Note: typically not stored) | ✅ |
| CMOCA-3-010 | 2.  Undo Color Calibration | ✅ |
| CMOCA-3-011 | 3.  Undo Color Conversion (i.e. convert source color space to PCS) | ✅ |
| CMOCA-3-012 | (halftone data)⁻¹ → (color calibration)⁻¹ → (color conversion)⁻¹ | ✅ |
| CMOCA-3-013 | In actuality, halftoning is not typically undone. The halftoned version is typically not stored in the database. Further, undoing the halftoning is very complex. Note also that objects prepared for a display are not halftoned. Therefore, this document assumes that no attempt will be made to go from halftoned data back to the object represented in the PCS color space. | ✅ |
| CMOCA-3-014 | Now, let's assume that the source data is specified in a device-dependent color space and it is desired to render it on an output device. The source data must be converted to the color space of the device. This involves the following combined sequence: | ✅ |
| CMOCA-3-015 | Figure 6. Procedure for Creating Halftone Data from Device-Specific Color Data** | ✅ |
| CMOCA-3-016 | 1.  Convert from source color space to PCS | ✅ |
| CMOCA-3-017 | 2.  Undo source Color Calibration | ✅ |
| CMOCA-3-018 | 3.  Color Calibration (Target) | ✅ |
| CMOCA-3-019 | 4.  Convert from PCS to color space of output device | ✅ |
| CMOCA-3-020 | 5.  Halftone Data | ✅ |
| CMOCA-3-021 | Information that relates to the creation of the source data is referred to as an **audit CMR**. It might describe how the source data was created, as in an audit halftone. Or it might describe how to undo an operation that was used to create the source data. For instance, an audit color conversion profile tells how to convert from the color space of the source data to a PCS. Audit tone transfer curves have been architected to describe an action that was done to create the source data. There is also an optional tag that describes how to undo the tone transfer curve. | ✅ |
| CMOCA-3-022 | The above discussion assumes that the color was specified using a color space. It is also possible to specify a color using an index and then define how to produce that color using a palette that tells which component inks/toners are used, in which concentration. An Indexed CMR is used to define how to produce the indexed colors. | ✅ |
| CMOCA-3-023 | There are seven basic uses of the CMR that must be considered: | ✅ |
| CMOCA-3-024 | 1.  **Audit Halftone** can be attached to an image that has been halftoned and indicates which halftone was used. | ✅ |
| CMOCA-3-025 | 2.  **Audit Tone Transfer Curve** indicates a one-dimensional conversion that was applied to the input color before halftoning. The inverse curve needs to be applied to get back to the input color space. | ✅ |
| CMOCA-3-026 | 3.  **Audit Color Conversion** is used to convert from the input color space to a profile connection space. | ✅ |
| CMOCA-3-027 | 4.  **Instruction Color Conversion** is used to convert from the profile connection space to the color space of the output device. | ✅ |
| CMOCA-3-028 | 5.  **Instruction Tone Transfer Curve** is a one-dimensional conversion applied to the output color before halftoning (or a set of 1-D conversions, one for each color component). | ✅ |
| CMOCA-3-029 | 6.  **Instruction Halftone** is used to halftone the output colored data. | ✅ |
| CMOCA-3-030 | 7.  **Link Color Conversion** provides an efficient means for converting directly from the input color space to the output color space and can be substituted for the Audit Color Conversion/Instruction Color Conversion pair. A special type of Link Color Conversion, ICC DeviceLink, converts directly from input to output space without reference to an audit/instruction pair. | ✅ |
| CMOCA-3-031 | When a presentation device is processing data using the CMR system, these are the basic steps that are followed. Note that device-default CMRs are used if an applicable CMR is not explicitly invoked. If a CMR is ignored, the device must accept it but does not error check or process the contents. | ✅ |
| CMOCA-3-032 | 1.  The device ignores an Audit Halftone. It might be useful information to store with the image in a database, but it is not currently used in the presentation device since undoing halftoning is not a simple process. | ✅ |
| CMOCA-3-033 | 2.  The device applies an applicable Audit Tone Transfer Curve. If an inverse tone transfer curve is specified, it is used. Otherwise, the function specified by the tone transfer curve must be inverted before it is applied. Note that the inverse tone transfer curve is not a well-defined function. | ✅ |
| CMOCA-3-034 | 3.  If an applicable ICC DeviceLink exists, it is used. This link color conversion converts the input color space to the output color space of the current device. If this CMR is selected for use, the next two steps (selection of Audit Color Conversion and Instruction Color Conversion CMRs) are skipped. | ✅ |
| CMOCA-3-035 | 4.  If an applicable Audit Color Conversion exists, it is used. This audit Color Conversion converts the input color space (RGB, CMYK, grayscale...) to a profile connection space. | ✅ |
| CMOCA-3-036 | 5.  An Instruction Color Conversion is used to convert from the profile connection space into the output color space of the current device. | ✅ |
| CMOCA-3-037 | 6.  If an Instruction Tone Transfer Curve exists, the color is modified using it. | ✅ |
| CMOCA-3-038 | 7.  The colored data is halftoned using an Instruction Halftone. | ✅ |
| CMOCA-3-039 | In reality, the processing of sequential steps can be combined to improve performance. A Link Color Conversion CMR can be used in place of a combined audit/instruction color conversion pair. Chapter 6 discusses how CMRs are used in more detail. | ✅ |
| CMOCA-3-040 | The Indexed CMR does not fit into the scheme discussed above. It is used to define how to produce indexed color. | ✅ |
| CMOCA-3-041 | This is the syntax of a CMR. Bytes 0–163 represent the CMR Header. Each field in the CMR header has a fixed length. The fields from byte 10–155 are character data encoded as UTF-16BE. Data in these fields are left aligned. If the number of the characters in these fields is smaller than the field length, the remaining bytes will be padded with @ (X'0040'). Any field from byte 44–139 is “unspecified” if it is filled with @ characters. | ✅ |
| CMOCA-3-042 | The CMR name is the concatenated fields in bytes 10–155, exactly in the order specified in the CMR header. The CMR name is 73 characters (146 bytes) long. It should be unique, since it is the name that will be used in the MO:DCA data stream to reference the resource. For example, if the CMR header fields are: | ✅ |
| CMOCA-3-043 | Alias=JohnMay4, CMRType=HT, CMRVersion=1.2, ManufacturerName=IBM, DeviceType=4100, DeviceModel=PD1, MediaBrightness=94, MediaColor=wht, MediaFinish=gl, MediaWeight=90, number of device levels=2, Halftone type=rnd, line screen frequency=141, resolution=600, and rotation=proc | ✅ |
| CMOCA-3-044 | `JohnMay4HT001.200IBM@@4100@@PD194@whtgl90@2@@@@rnd@@@141@600@proc@@@@@@@@` | ✅ |
| CMOCA-3-045 | 4 | 0–3 | 4-byte UBIN | Length | X'000000A4' – X'FFFFFFFF' | CMR length, including length field | M | ✅ |
| CMOCA-3-046 | 4 | 4–7 | CODE | CMRSig | X'434D5239' | Signature of this CMR | M | ✅ |
| CMOCA-3-047 | 2 | 8–9 | | Reserved | X'0000' | Reserved: should be set to zero | M | ✅ |
| CMOCA-3-048 | CMR Name starts here. It is composed of bytes 10–155.** | ✅ |
| CMOCA-3-049 | 16 | 10–25 | UTF16 | CMRAlias | No restriction | Human-readable alias | M | ✅ |
| CMOCA-3-050 | 4 | 26–29 | UTF16 | CMRType | CC (X'0043 0043') | Color Conversion | M | ✅ |
| CMOCA-3-051 | DL (X'0044 004C') | ICC DeviceLink Color Conversion | ✅ |
| CMOCA-3-052 | HT (X'0048 0054') | Halftone | ✅ |
| CMOCA-3-053 | IX (X'0049 0058') | Indexed | ✅ |
| CMOCA-3-054 | LK (X'004C 004B') | Link Color Conversion | ✅ |
| CMOCA-3-055 | TC (X'0054 0043') | Tone Transfer Curve | ✅ |
| CMOCA-3-056 | 14 | 30–43 | UTF16 | CMRVersion | ddd.ddd | CMRVersion number | M | ✅ |
| CMOCA-3-057 | AFP .ddd | Special AFP version number | ✅ |
| CMOCA-3-058 | generic | “generic” | ✅ |
| CMOCA-3-059 | pasthru | “passthrough” | ✅ |
| CMOCA-3-060 | 10 | 44–53 | UTF16 | Manufacturer Name | See description | Name of the manufacturer | M | ✅ |
| CMOCA-3-061 | 12 | 54–65 | UTF16 | DeviceType | See description | Type of the device | M | ✅ |
| CMOCA-3-062 | 6 | 66–71 | UTF16 | DeviceModel | See description | Model of the device | M | ✅ |
| CMOCA-3-063 | 6 | 72–77 | UTF16 | Media Brightness | 0–100 | For print media, percentage of light reflected | M | ✅ |
| CMOCA-3-064 | Zxy | For screen, a CIE illuminant | ✅ |
| CMOCA-3-065 | 6 | 78–83 | UTF16 | MediaColor | | Color of the media: | M | ✅ |
| CMOCA-3-066 | blu | blue | ✅ |
| CMOCA-3-067 | buf | buff | ✅ |
| CMOCA-3-068 | gdr | goldenrod | ✅ |
| CMOCA-3-069 | grn | green | ✅ |
| CMOCA-3-070 | gry | gray | ✅ |
| CMOCA-3-071 | ivy | ivory | ✅ |
| CMOCA-3-072 | noc | no-color | ✅ |
| CMOCA-3-073 | org | orange | ✅ |
| CMOCA-3-074 | pnk | pink | ✅ |
| CMOCA-3-075 | red | red | ✅ |
| CMOCA-3-076 | wht | white | ✅ |
| CMOCA-3-077 | ylw | yellow | ✅ |
| CMOCA-3-078 | custom | three upper-case characters [A,Z] | ✅ |
| CMOCA-3-079 | @@@ | not specified | ✅ |
| CMOCA-3-080 | 4 | 84–87 | UTF16 | MediaFinish | | Surface characteristics: | M | ✅ |
| CMOCA-3-081 | cm | commodity | ✅ |
| CMOCA-3-082 | ct | coated | ✅ |
| CMOCA-3-083 | gl | glossy | ✅ |
| CMOCA-3-084 | hg | high-gloss | ✅ |
| CMOCA-3-085 | mt | matte | ✅ |
| CMOCA-3-086 | no | none | ✅ |
| CMOCA-3-087 | np | newsprint | ✅ |
| CMOCA-3-088 | sg | semi-gloss | ✅ |
| CMOCA-3-089 | st | satin | ✅ |
| CMOCA-3-090 | tr | treated | ✅ |
| CMOCA-3-091 | custom | two upper-case characters [A,Z] | ✅ |
| CMOCA-3-092 | @@ | not specified | ✅ |
| CMOCA-3-093 | 6 | 88–93 | UTF16 | MediaWeight | 1–999 | The basic weight of the paper | M | ✅ |
| CMOCA-3-094 | 10 | 94–103 | UTF16 | Prop1 | See description | CMRType property-specific field 1 | M | ✅ |
| CMOCA-3-095 | 12 | 104–115 | UTF16 | Prop2 | See description | CMRType property-specific field 2 | M | ✅ |
| CMOCA-3-096 | 8 | 116–123 | UTF16 | Prop3 | See description | CMRType property-specific field 3 | M | ✅ |
| CMOCA-3-097 | 8 | 124–131 | UTF16 | Prop4 | See description | CMRType property-specific field 4 | M | ✅ |
| CMOCA-3-098 | 8 | 132–139 | UTF16 | Prop5 | See description | CMRType property-specific field 5 | M | ✅ |
| CMOCA-3-099 | 16 | 140–155 | UTF16 | @@@@@@@@ | | Reserved | M | ✅ |
| CMOCA-3-100 | CMR Name ends here. It is composed of bytes 10–155.** | ✅ |
| CMOCA-3-101 | 8 | 156–163 | | X'00...00' | | Reserved: should be set to zero | M | ✅ |
| CMOCA-3-102 | 164–end | | CMRData | Any | | Resource data | O | ✅ |
| CMOCA-3-103 | 1. M/O denotes a mandatory or optional field | ✅ |
| CMOCA-3-104 | 2. UTF16 denotes UTF-16BE | ✅ |
| CMOCA-3-105 | The length of the complete CMR, including the Length parameter. Length may be 164 (X'000000A4') bytes up to X'FFFFFFFF'. | ✅ |
| CMOCA-3-106 | The signature of the CMR that allows it to be easily recognized. It will be three ASCII characters “CMR” followed by X'39', that is, X'434D5239'. | ✅ |
| CMOCA-3-107 | Eight-character user-defined string to enable an easy way of identifying the CMR. | ✅ |
| CMOCA-3-108 | Five CMRTypes are defined in this Color Management Object Content Architecture. They are: Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, and Indexed. The value of the CMRType must be specified in the header or an exception will be generated. Note that a Link Color Conversion CMR has two possible identifiers in this field: LK for LinkColorConversion LUT subset and DL for ICC DeviceLink subset. | ✅ |
| CMOCA-3-109 | The CMRVersion number consists of a major version number (an integer of 1–3 digits) and a minor version number (an integer of 1–3 digits), separated by a decimal point (. =DECIMAL POINT=X'002E'). If the number of digits is smaller than 3, zeroes will be padded to the left side of the major number or to the right side of the minor number. For example, if the version number is 1.20 then the value of the CMRVersion is 001.200. | ✅ |
| CMOCA-3-110 | A value of “AFP .ddd” is used to specify a minor version number along with “AFP” to indicate that the CMR is a standard Color Conversion CMR that is supported by the AFP Consortium. The supported standard color spaces will be spaces like SWOP, CMYK, and sRGB. | ✅ |
| CMOCA-3-111 | A value of “generic” (X'0067 0065 006E 0065 0072 0069 0063') in this field identifies a generic CMR. Only Halftone and Tone Transfer Curve CMRs may be identified as generic. CMR data in generic CMRs is optional and is not used in AFP color management systems. | ✅ |
| CMOCA-3-112 | A value of “pasthru” (X'0070 0061 0073 0074 0068 0072 0075') identifies a color space that should not be color-converted. Only Color Conversion CMRs may be identified as passthrough. There is no data in a passthrough CMR. | ✅ |
| CMOCA-3-113 | The value of the CMRVersion must be specified in the header. The CMRVersion tracks changes besides the changes in the device-specific fields, media-specific fields, and the CMRType property-specific fields. It reflects changes of algorithm, toner, and so on. | ✅ |
| CMOCA-3-114 | For IPDS receivers, the ManufacturerName, DeviceType, and DeviceModel values must be provided in accordance with the IPDS description of the Product Identifier self-defining field of the XOH Obtain Printer Characteristics (OPC) reply. Refer to the Intelligent Printer Data Stream Reference. The field descriptions are as follows: | ✅ |
| CMOCA-3-115 | ManufacturerName:** Name of the manufacturer. | ✅ |
| CMOCA-3-116 | DeviceType:** Device type of the printer that corresponds to the device type imprinted on the serial number plate that is physically attached to the printer. | ✅ |
| CMOCA-3-117 | DeviceModel:** Model number of the printer that corresponds to the model number imprinted on the serial number plate that is physically attached to the printer. | ✅ |
| CMOCA-3-118 | For the non-IPDS devices, a maximum of five characters are allowed for the ManufacturerName. The stock symbol (maximum five characters), a unique name assigned by stock exchanges worldwide, is recommended to be used for the ManufacturerName. The DeviceType and DeviceModel have to be unique and meaningful for the devices. Alternatively, the ICC Manufacture ASCII Signature and Device ASCII Signature can be used for the ManufacturerName and the DeviceModel. | ✅ |
| CMOCA-3-119 | Implementation notes:** | ✅ |
| CMOCA-3-120 | 1.  If the DeviceType is unspecified (@@@@@@), then it automatically matches the DeviceType of the target device. Similarly, if the DeviceModel is unspecified (@@@), then it automatically matches the DeviceModel of the target device. The DeviceType and DeviceModel are sometimes used by print servers to determine which CMRs to send to the presentation device. In particular, link CMRs are targeted for a particular device based on the DeviceType and DeviceModel of the instruction Color Conversion CMR. Multiple link CMRs can be associated with (or mapped to) an audit CC CMR in the CMR RAT. The link CMRs that are sent down to the device are determined by finding matches with the DeviceType and DeviceModel of the target device. Furthermore, Generic Tone Transfer Curve and Halftone CMRs can have mapped device-specific CMRs in the CMR RAT; such mapped CMRs are sent to the device if the DeviceType and DeviceModel in the mapped CMR match the DeviceType and DeviceModel of the target device. In some situations, it is acceptable to let the CMR header values for DeviceType and DeviceModel be unspecified (@@@@@@ or @@@). For example, CMRs that will be used only as audit CMRs can have unspecified values for DeviceType and DeviceModel. If a link CMR or a device-specific HT or TTC CMR is associated with another CMR in the CMR RAT and does not specify a DeviceType and/or DeviceModel, the unspecified parameter(s) match the DeviceType and/or DeviceModel of any target printer. | ✅ |
| CMOCA-3-121 | 2.  The device types and model numbers specified in the XOH-OPC reply and in the CMR header's DeviceType might not use the same format. For instance, for the InfoPrint 4100, the XOH-OPC reply for the device type would be “004100” encoded using EBCDIC. In the CMR header, the DeviceType is padded with “@” on the right. Therefore, depending on the input provided to the Installer, the CMR DeviceType field might be “004100” or “4100@@” encoded using UTF-16BE. Tools that compare the device type in the XOH-OPC reply and in the CMR header must be prepared to indicate a match taking into account the differences in padding practices. | ✅ |
| CMOCA-3-122 | Media-specific fields describe the media and consist of four attributes: media brightness, media color, media finish, and media weight. The values for the MediaColor and the MediaFinish are consistent with the values defined by the Internet Printing Protocol (IPP) of the Printer Working Group (PWG). If the target device is a display, only media brightness is specified. | ✅ |
| CMOCA-3-123 | To use an instruction CMR, its media type must match the media currently being used by the device. Similarly, in order to use an ICC DeviceLink CMR, its media attributes must match the device's media attributes. See “Matching Media Type of CMR With Media Type of Device” for a discussion of this requirement. | ✅ |
| CMOCA-3-124 | MediaBrightness:** For print media, indicates the percentage of light reflected from the media. The brightness is measured with a brightmeter machine. The scale is based on the TAPPI GE scale in the US and the ISO scale in the rest of the world. The ISO scale is usually about two units higher than the GE value. For example, 100 ISO brightness is equivalent to 98 brightness on the GE scale. In order to ensure that the CMR's media type matches the media currently being used in the device, the scale that is used to specify each value must be the same. For screens, the brightness is defined as the CIE standard illuminant as Zxy, where Z is a capitalized letter, and xy is a two-digit number (see ISO/CIE 10526:1999: CIE standard illuminants for colorimetry). For example, D50, D65, etc. | ✅ |
| CMOCA-3-125 | MediaColor:** Indicates the color of the media being specified. CMOCA-recommended values exist to encourage interoperability; a CMOCA-recommended value should be used if appropriate for a CMR associated with a specific media. The value “noc” means transparency. Custom values may be defined by the administrator. There is no restriction on what value may be entered for this field as it is not checked for validity. | ✅ |
| CMOCA-3-126 | MediaFinish:** Indicates the surface characteristics of the media. CMOCA-recommended values exist to encourage interoperability; a CMOCA-recommended value should be used if appropriate for a CMR associated with a specific media. The value “no” means no coating. Custom values may be defined by the administrator. There is no restriction on what value may be entered for this field as it is not checked for validity. | ✅ |
| CMOCA-3-127 | MediaWeight:** Indicates the weight of the media rounded to the nearest whole number of grams per square meter. | ✅ |
| CMOCA-3-128 | Note:** These fields are informational only. They are not checked for validity. Any value may be entered in the Prop fields since no error checking is done. | ✅ |
| CMOCA-3-129 | Prop1: Number of Device Levels** | ✅ |
| CMOCA-3-130 | Prop2: Halftone Type** | ✅ |
| CMOCA-3-131 | Defines the halftone type. Halftone types are divided into four major categories: clustered-dot, stochastic, dispersed, and error diffusion. The dot shape is used to specify the type of the clustered-dot, and the error diffusion filter name is used to specify the type of error diffusion halftone. | ✅ |
| CMOCA-3-132 | Table 5. Halftone Types** | ✅ |
| CMOCA-3-133 | rnd@@@** | Round dot for the clustered-dot halftone | ✅ |
| CMOCA-3-134 | sqr@@@** | Square dot for the clustered-dot halftone | ✅ |
| CMOCA-3-135 | dia@@@** | Diamond dot for the clustered-dot halftone | ✅ |
| CMOCA-3-136 | rhm@@@** | Rhombus dot for the clustered-dot halftone | ✅ |
| CMOCA-3-137 | elp@@@** | Elliptical dot for the clustered-dot halftone | ✅ |
| CMOCA-3-138 | eud@@@** | Euclidean dot for the clustered-dot halftone | ✅ |
| CMOCA-3-139 | lin@@@** | Line shape dot for the clustered-dot halftone | ✅ |
| CMOCA-3-140 | sto@@@** | Stochastic halftone | ✅ |
| CMOCA-3-141 | dsp@@@** | Dispersed halftone | ✅ |
| CMOCA-3-142 | erd@@@** | Unspecified error diffusion halftone | ✅ |
| CMOCA-3-143 | f-d@@@** | Floyd-Steinberg error diffusion halftone | ✅ |
| CMOCA-3-144 | jjn@@@** | Jarvis-Judice-Ninke error diffusion halftone | ✅ |
| CMOCA-3-145 | stu@@@** | Stucki error diffusion halftone | ✅ |
| CMOCA-3-146 | brk@@@** | Burkes error diffusion halftone | ✅ |
| CMOCA-3-147 | sra@@@** | Sierra error diffusion halftone | ✅ |
| CMOCA-3-148 | s-a@@@** | Stevenson Arce error diffusion halftone | ✅ |
| CMOCA-3-149 | Prop3: Line Screen Frequency** | ✅ |
| CMOCA-3-150 | Prop4: Resolution** | ✅ |
| CMOCA-3-151 | Prop5: Rotation** | ✅ |
| CMOCA-3-152 | Defines the orientation of the halftone. There are three possible values: orientation independent, along the scan direction, and along the process direction. | ✅ |
| CMOCA-3-153 | Table 6. Halftone Rotations** | ✅ |
| CMOCA-3-154 | indp** | Orientation independent | ✅ |
| CMOCA-3-155 | scan** | Scan direction | ✅ |
| CMOCA-3-156 | proc** | Process direction | ✅ |
| CMOCA-3-157 | Note:** These fields are informational only. They are not checked for validity. Any value may be entered in the Prop fields since no error checking is done. | ✅ |
| CMOCA-3-158 | Prop1: Profile/Device Class Signature** | ✅ |
| CMOCA-3-159 | The definition of the Device Class Signature is consistent with the definition in the ICC header. There are four basic profile/device classes: Input, Display, Output, and ColorSpace Conversion. | ✅ |
| CMOCA-3-160 | Table 7. ICC Profile/Device Classes for Tone Transfer Curve CMRs** | ✅ |
| CMOCA-3-161 | scnr@** | Input Device | ✅ |
| CMOCA-3-162 | mntr@** | Display Device | ✅ |
| CMOCA-3-163 | prtr@** | Output Device | ✅ |
| CMOCA-3-164 | spac@** | ColorSpace Conversion | ✅ |
| CMOCA-3-165 | Prop2: Look-and-Feel** | ✅ |
| CMOCA-3-166 | Look-and-Feel produced in the output when this Tone Transfer Curve is applied. See Appendix B, “Generic CMR Name Registry” for an explanation of what these values mean. | ✅ |
| CMOCA-3-167 | Table 8. Look-and-Feel Values** | ✅ |
| CMOCA-3-168 | hilmid** | Highlight Midtone | ✅ |
| CMOCA-3-169 | standd** | Standard | ✅ |
| CMOCA-3-170 | dark@@** | Dark | ✅ |
| CMOCA-3-171 | accutn** | Accutone | ✅ |
| CMOCA-3-172 | Prop3: Halftone Characterization** | ✅ |
| CMOCA-3-173 | Prop4: Reserved for future use.** | ✅ |
| CMOCA-3-174 | Prop5: Reserved for future use.** | ✅ |
| CMOCA-3-175 | Note:** These fields are informational only. They are not checked for validity. Any value may be entered in the Prop fields since no error checking is done. | ✅ |
| CMOCA-3-176 | Prop1: Profile/Device Class Signature** | ✅ |
| CMOCA-3-177 | It is consistent with the definition of the Profile/Device Class Signature in the ICC header. | ✅ |
| CMOCA-3-178 | Table 9. ICC Profile/Device Classes for Color Conversion CMRs** | ✅ |
| CMOCA-3-179 | scnr@** | Input Device profile | ✅ |
| CMOCA-3-180 | mntr@** | Display Device profile | ✅ |
| CMOCA-3-181 | prtr@** | Output Device profile | ✅ |
| CMOCA-3-182 | spac@** | ColorSpace Conversion profile | ✅ |
| CMOCA-3-183 | Prop2: Reserved for future use.** | ✅ |
| CMOCA-3-184 | Prop3: Reserved for future use.** | ✅ |
| CMOCA-3-185 | Prop4: Color Space of Data** | ✅ |
| CMOCA-3-186 | It is consistent with the definition of the Color Space of Data in the ICC header. Table 10 shows the possible values. | ✅ |
| CMOCA-3-187 | Table 10. The ICC Color Space of Data** | ✅ |
| CMOCA-3-188 | XYZ@** | XYZData | ✅ |
| CMOCA-3-189 | Lab@** | labData | ✅ |
| CMOCA-3-190 | Luv@** | luvData | ✅ |
| CMOCA-3-191 | YCbr** | YCbCrData | ✅ |
| CMOCA-3-192 | Yxy@** | YxyData | ✅ |
| CMOCA-3-193 | RGB@** | rgbData | ✅ |
| CMOCA-3-194 | GRAY** | grayData | ✅ |
| CMOCA-3-195 | HSV@** | hsvData | ✅ |
| CMOCA-3-196 | HLS@** | hlsData | ✅ |
| CMOCA-3-197 | CMYK** | cmykData | ✅ |
| CMOCA-3-198 | CMY@** | cmyData | ✅ |
| CMOCA-3-199 | 2CLR** | 2colorData | ✅ |
| CMOCA-3-200 | 3CLR** | 3colorData (if not listed above) | ✅ |
| CMOCA-3-201 | 4CLR** | 4colorData (if not listed above) | ✅ |
| CMOCA-3-202 | 5CLR** | 5colorData | ✅ |
| CMOCA-3-203 | 6CLR** | 6colorData | ✅ |
| CMOCA-3-204 | 7CLR** | 7colorData | ✅ |
| CMOCA-3-205 | 8CLR** | 8colorData | ✅ |
| CMOCA-3-206 | 9CLR** | 9colorData | ✅ |
| CMOCA-3-207 | ACLR** | 10colorData | ✅ |
| CMOCA-3-208 | BCLR** | 11colorData | ✅ |
| CMOCA-3-209 | CCLR** | 12colorData | ✅ |
| CMOCA-3-210 | DCLR** | 13colorData | ✅ |
| CMOCA-3-211 | ECLR** | 14colorData | ✅ |
| CMOCA-3-212 | FCLR** | 15colorData | ✅ |
| CMOCA-3-213 | Prop5: PCS** | ✅ |
| CMOCA-3-214 | The profile connection space specified as either CIEXYZ (XYZ) or CIELAB (Lab), encoded as for Prop4. | ✅ |
| CMOCA-3-215 | Note:** These fields are informational only. They are not checked for validity. Any value may be entered in the Prop fields since no error checking is done. | ✅ |
| CMOCA-3-216 | Prop1: Input Device ManufacturerName** | ✅ |
| CMOCA-3-217 | Prop2: Input DeviceType** | ✅ |
| CMOCA-3-218 | Prop3: Input DeviceModel** | ✅ |
| CMOCA-3-219 | Prop4: Input Color Space** | ✅ |
| CMOCA-3-220 | Prop5: Output Color Space** | ✅ |
| CMOCA-3-221 | Device-specific color space, a subset of the Color Space of Data defined in the ICC profile header. Possible values are shown in the following table. | ✅ |
| CMOCA-3-222 | Table 11. Output Color Spaces** | ✅ |
| CMOCA-3-223 | RGB@** | rgbData | ✅ |
| CMOCA-3-224 | GRAY** | grayData | ✅ |
| CMOCA-3-225 | CMYK** | cmykData | ✅ |
| CMOCA-3-226 | CMY@** | cmyData | ✅ |
| CMOCA-3-227 | 2CLR** | 2colorData | ✅ |
| CMOCA-3-228 | 3CLR** | 3colorData (if not listed above) | ✅ |
| CMOCA-3-229 | 4CLR** | 4colorData (if not listed above) | ✅ |
| CMOCA-3-230 | 5CLR** | 5colorData | ✅ |
| CMOCA-3-231 | 6CLR** | 6colorData | ✅ |
| CMOCA-3-232 | 7CLR** | 7colorData | ✅ |
| CMOCA-3-233 | 8CLR** | 8colorData | ✅ |
| CMOCA-3-234 | 9CLR** | 9colorData | ✅ |
| CMOCA-3-235 | ACLR** | 10colorData | ✅ |
| CMOCA-3-236 | BCLR** | 11colorData | ✅ |
| CMOCA-3-237 | CCLR** | 12colorData | ✅ |
| CMOCA-3-238 | DCLR** | 13colorData | ✅ |
| CMOCA-3-239 | ECLR** | 14colorData | ✅ |
| CMOCA-3-240 | FCLR** | 15colorData | ✅ |
| CMOCA-3-241 | Note:** These fields are informational only. They are not checked for validity. Any value may be entered in the Prop fields since no error checking is done. | ✅ |
| CMOCA-3-242 | Prop1: Reserved for future use.** | ✅ |
| CMOCA-3-243 | Prop2: Reserved for future use.** | ✅ |
| CMOCA-3-244 | Prop3: Reserved for future use.** | ✅ |
| CMOCA-3-245 | Prop4: Reserved for future use.** | ✅ |
| CMOCA-3-246 | Prop5: Reserved for future use.** | ✅ |
| CMOCA-3-247 | The content of the data is defined by the CMR type. The CMR data carries the color resource data. The resource data is carried in a tagged format. The tags are loosely based on the TIFF tag syntax, but with significant changes and additions. The tag syntax is defined in Chapter 5, “CMR Data Architecture”. | ✅ |
| CMOCA-3-248 | CMR data is optional for generic and passthrough CMRs. If CMR data is specified for a generic or passthrough CMR, it is ignored. | ✅ |
| CMOCA-3-249 | On encountering an error, an exception is raised. Exception conditions have a format of EC-xxxxyy. xxxx represents the tag value. For the purposes of error reporting, the fields in the CMR header are treated as “implied tags”. The architecture defines the tags that describe data fields to have TagIDs of X'0000'–X'FFFF'. However, IDs in the range X'EF00'–X'EFFF' have been reserved for error handling in the CMR Header. Currently, IDs in the range X'EFF0'–X'EFF7' are used for CMR header error codes. | ✅ |
| CMOCA-3-250 | X'03' Invalid length | ✅ |
| CMOCA-3-251 | X'10' Invalid or unsupported field value | ✅ |
| CMOCA-3-252 | The exception conditions are as follows: | ✅ |
| CMOCA-3-253 | EC-EFF003 Invalid Length Value:** The specified Length is invalid. | ✅ |
| CMOCA-3-254 | EC-EFF110 Invalid Field Value:** The specified value for CMRSig is not X'434D5239'. | ✅ |
| CMOCA-3-255 | EC-EFF210 Invalid Field Value:** The specified CMRType is invalid. | ✅ |
| CMOCA-3-256 | EC-EFF310 Invalid Field Value:** The specified CMRVersion is invalid. | ✅ |
| CMOCA-3-257 | EC-EFF410 Invalid Field Value:** The specified MediaBrightness is invalid. | ✅ |
| CMOCA-3-258 | EC-EFF510** Retired item 1. | ✅ |
| CMOCA-3-259 | EC-EFF610** Retired item 2. | ✅ |
| CMOCA-3-260 | EC-EFF710 Invalid Field Value:** The specified MediaWeight is invalid. | ✅ |
| CMOCA-3-261 | Prop 1–5 are informational. The values are not checked. | ✅ |
| CMOCA-4-001 | The following CMRTypes are defined: | ✅ |
| CMOCA-4-002 | Halftone | ✅ |
| CMOCA-4-003 | Tone Transfer Curve | ✅ |
| CMOCA-4-004 | Color Conversion | ✅ |
| CMOCA-4-005 | Link Color Conversion | ✅ |
| CMOCA-4-006 | Indexed | ✅ |
| CMOCA-4-007 | Each of the CMRTypes is described in more detail below. | ✅ |
| CMOCA-4-008 | In the following descriptions, the Optional Tags and Mandatory Tags are listed to show which tags are meaningful for each type and subset. Any other tags present are ignored by the receiver. The Comment tag is optional. The End Data tag is required in all CMR objects. | ✅ |
| CMOCA-4-009 | CMR Type HT (X'0048 0054')** | ✅ |
| CMOCA-4-010 | Halftone can be classified into two types: point-operation halftone and neighborhood-operation halftone. The output of the point-operation halftone depends only on the value of the current pixel. It can be used for numerous common halftone types including clustered dot, dispersed, and stochastic. Threshold arrays are commonly used for the bilevel point-operation halftones, and lookup tables are commonly used for the multilevel point-operation halftones. The error diffusion halftone is commonly used for the neighborhood-operation halftone. The most common error diffusion filters include Floyd-Steinberg, Jarvis-Judice-Ninke, Stucki, etc. The Halftone Subset tag indicates the halftone type, and thus determines required and optional tags for this Halftone CMR. | ✅ |
| CMOCA-4-011 | Mandatory Tags:** Halftone Subset, Array Width, Array Height, Bilevel Point-Operation Screen Data, End Data | ✅ |
| CMOCA-4-012 | Optional Tags:** Comment, Date and Time Stamp, Number of Components, Offset Tiling | ✅ |
| CMOCA-4-013 | Mandatory Tags:** Halftone Subset, Array Width, Array Height, Max Image Value, Number of Device Levels, Multilevel Point-Operation Screen Data, End Data | ✅ |
| CMOCA-4-014 | Optional Tags:** Comment, Date and Time Stamp, Number of Components, Offset Tiling | ✅ |
| CMOCA-4-015 | Mandatory Tags:** Halftone Subset, Array Width, Array Height, Error Diffusion Filter, Location of Current Pixel, Raster Direction, Boundary Condition, Threshold Value, End Data | ✅ |
| CMOCA-4-016 | Optional Tags:** Comment, Date and Time Stamp, Number of Components | ✅ |
| CMOCA-4-017 | Mandatory Tags:** Halftone Subset, Array Width, Array Height, Number of Device Levels, Error Diffusion Filter, Location of Current Pixel, Raster Direction, Boundary Condition, Quantization Boundary Table, End Data | ✅ |
| CMOCA-4-018 | Optional Tags:** Comment, Date and Time Stamp, Number of Components | ✅ |
| CMOCA-4-019 | CMR Type TC (X'0054 0043')** | ✅ |
| CMOCA-4-020 | Tone transfer curves are applied to data prior to halftoning or output. The inverse tone transfer curves are applied to restore data to the original state. The printer tone transfer curve produces a desired appearance by compensating for dot gain. The tone transfer curve for a display or other input device is used to correct non-linearity (gamma) of the device. Currently, there are two tone transfer curve subsets: ToneTransferCurve Array and ToneTransferCurve Identity. The Subset tag indicates the tone transfer curve type, and thus determines required and optional tags for this Tone Transfer Curve CMR. | ✅ |
| CMOCA-4-021 | Mandatory Tags:** Tone Transfer Curve Subset, Tone Transfer Curve Data, End Data | ✅ |
| CMOCA-4-022 | Optional Tags:** Comment, Date and Time Stamp, Number of Components, Tone Transfer Curve Length, Inverse Tone Transfer Curve Data | ✅ |
| CMOCA-4-023 | The tone transfer curve for each component is the identity. No data is sent with this subset, that is, no tone transfer curve is to be applied. This subset is implemented for performance reasons. | ✅ |
| CMOCA-4-024 | Mandatory Tags:** Tone Transfer Curve Subset, End Data | ✅ |
| CMOCA-4-025 | Optional Tags:** Comment, Date and Time Stamp | ✅ |
| CMOCA-4-026 | CMR Type CC (X'0043 0043')** | ✅ |
| CMOCA-4-027 | Each instance of this CMR type is a subset of the standard ICC profile. This allows the CMR to be used in any color management system. | ✅ |
| CMOCA-4-028 | The ICC Profile Data starts with a 128-byte header followed by the ICCTags. The ICCHeaderFields are contained in pre-defined byte positions as defined in Table 35. | ✅ |
| CMOCA-4-029 | Each subset of the ICC profile type, selected by the ICC Profile Subset tag, defines a subset of the ICC specification. For each subset, the Color Management Object Content Architecture defines the mandatory and optional ICCHeaderFields and ICCTags. Optional ICCTags and ICCHeaderFields will be processed as applicable if encountered. Any other ICCTags will be ignored. | ✅ |
| CMOCA-4-030 | Note:** The chromaticAdaptationTag is shown as optional for each subset. However, it is mandatory if the value in the mediaWhitePointTag is not D50. | ✅ |
| CMOCA-4-031 | Two ICCHeaderFields are mandatory for the Color Conversion CMR: Color Space of Data and Profile Connection Space. The descriptions are as follows: | ✅ |
| CMOCA-4-032 | Color Space of Data:** The ICC-supported color spaces and their signatures are listed in Table 10. | ✅ |
| CMOCA-4-033 | Profile Connection Space:** The ICC profile connection space is either CIELAB D50 or CIEXYZ D50 for all ICC profiles except the ICC DeviceLink profile. The CIELAB signature is “Lab” and the CIEXYZ signature is “XYZ”. | ✅ |
| CMOCA-4-034 | The currently allowed ICC profile subsets for Color Conversion CMRs include all the ICC profile types except for the DeviceLink and the Named Colour profiles. (For complete information, please refer to sections 8.6 and 8.9 in ICC 1:2004-10 Version 4.2.0.0.) | ✅ |
| CMOCA-4-035 | The ICC profile subsets for the Color Conversion CMR are listed in Table 12. | ✅ |
| CMOCA-4-036 | Monochrome input profile | Scanner, digital camera | Device → PCS | ✅ |
| CMOCA-4-037 | Monochrome display profile | Display | Device ← → PCS | ✅ |
| CMOCA-4-038 | Monochrome output profile | Printer | Device → PCS | ✅ |
| CMOCA-4-039 | Three-component matrix-based input profile | Scanner, digital camera | Device → PCS | ✅ |
| CMOCA-4-040 | Three-component matrix-based display profile | Display | Device ← → PCS | ✅ |
| CMOCA-4-041 | N-component LUT-based input profile | Scanner, digital camera | Device → PCS | ✅ |
| CMOCA-4-042 | N-component LUT-based display profile | Display | Device ← → PCS | ✅ |
| CMOCA-4-043 | N-component LUT-based output profiles | Printer, film recorder | PCS → Device | ✅ |
| CMOCA-4-044 | ColorSpace conversion profile | Non-device color space (e.g., sRGB, D65) | Non-device ← → PCS | ✅ |
| CMOCA-4-045 | The Basic Intents Column describes the most commonly used color conversion direction(s) for each ICC profile subset. | ✅ |
| CMOCA-4-046 | Mandatory Tags:** ICC Profile Subset, ICC Profile Data, End Data | ✅ |
| CMOCA-4-047 | Optional Tags:** Comment, Date and Time Stamp, ICC Profile Filename | ✅ |
| CMOCA-4-048 | Implementation note:** It is very important to include the ICC Profile Filename tag for debugging purposes regardless of the fact that it is optional. | ✅ |
| CMOCA-4-049 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-050 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-051 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-052 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-053 | Mandatory ICCTags:** profileDescriptionTag, grayTRCTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-054 | Optional ICCTags:** chromaticAdaptationTag, AT oB0Tag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag | ✅ |
| CMOCA-4-055 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-056 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-057 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-058 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-059 | Mandatory ICCTags:** profileDescriptionTag, grayTRCTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-060 | Optional ICCTags:** chromaticAdaptationTag, AT oB0Tag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag | ✅ |
| CMOCA-4-061 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-062 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-063 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-064 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-065 | Mandatory ICCTags:** profileDescriptionTag, grayTRCTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-066 | Optional ICCTags:** chromaticAdaptationTag, AT oB0Tag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag | ✅ |
| CMOCA-4-067 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-068 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-069 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-070 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-071 | Mandatory ICCTags:** profileDescriptionTag, redMatrixColumnTag, greenMatrixColumnTag, blueMatrixColumnTag, redTRCTag, greenTRCTag, blueTRCTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-072 | Optional ICCTags:** chromaticAdaptationTag, AT oB0Tag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag, gamutTag | ✅ |
| CMOCA-4-073 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-074 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-075 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-076 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-077 | Mandatory ICCTags:** profileDescriptionTag, redMatrixColumnTag, greenMatrixColumnTag, blueMatrixColumnTag, redTRCTag, greenTRCTag, blueTRCTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-078 | Optional ICCTags:** chromaticAdaptationTag, AT oB0Tag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag, gamutTag | ✅ |
| CMOCA-4-079 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-080 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-081 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-082 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-083 | Mandatory ICCTags:** profileDescriptionTag, AT oB0Tag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-084 | Optional ICCTags:** chromaticAdaptationTag, AT oB1Tag, AT oB2Tag, BT oA0Tag, BT oA1Tag, BT oA2Tag, gamutTag | ✅ |
| CMOCA-4-085 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-086 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-087 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-088 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-089 | Mandatory ICCTags:** profileDescriptionTag, AT oB0Tag, BT oA0Tag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-090 | Optional ICCTags:** chromaticAdaptationTag, AT oB1Tag, AT oB2Tag, BT oA1Tag, BT oA2Tag, gamutTag | ✅ |
| CMOCA-4-091 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-092 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-093 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-094 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-095 | Mandatory ICCTags:** profileDescriptionTag, AT oB0Tag, BT oA0Tag, AT oB1Tag, BT oA1Tag, AT oB2Tag, BT oA2Tag, gamutTag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-096 | Optional ICCTags:** chromaticAdaptationTag, colorantTableTag | ✅ |
| CMOCA-4-097 | This subset should be used as an audit color conversion. | ✅ |
| CMOCA-4-098 | Mandatory ICCHeaderFields** | ✅ |
| CMOCA-4-099 | 16–19 | Color Space of Data | ✅ |
| CMOCA-4-100 | 20–23 | Profile Connection Space | ✅ |
| CMOCA-4-101 | Optional ICCHeaderFields:** All other header fields | ✅ |
| CMOCA-4-102 | Mandatory ICCTags:** profileDescriptionTag, AT oB0Tag, BT oA0Tag, mediaWhitePointTag, copyrightTag | ✅ |
| CMOCA-4-103 | Optional ICCTags:** chromaticAdaptationTag, AT oB1Tag, AT oB2Tag, BT oA1Tag, BT oA2Tag, gamutTag | ✅ |
| CMOCA-4-104 | CMR Type LK (X'004C 004B') or DL (X'0044 004C')** | ✅ |
| CMOCA-4-105 | The purpose of the Link Color Conversion CMR is to convert directly from the input to the output color space. There are two major types of Link Color Conversion: | ✅ |
| CMOCA-4-106 | Link Color Conversion** with up to four Lookup Tables (LUTs) representing different rendering intents. It is selected for use based on the audit and instruction OIDs that are contained in its tags. | ✅ |
| CMOCA-4-107 | ICC DeviceLink** contains an ICC profile of type DeviceLink. It contains a complex color conversion description (with up to five processing elements) for exactly one rendering intent. It is selected for use when it is found during a search of the hierarchy of invoked link CMRs (only ICC DeviceLink CMRs are invoked, other link CMR subsets are not invoked). It is not based on an audit and instruction Color Conversion pair. | ✅ |
| CMOCA-4-108 | LinkColorConversion LUT subset:** Its purpose is to combine an audit Color Conversion CMR with an instruction Color Conversion CMR to improve performance. It allows up to four LUTs, each representing one of the four ICC rendering intents. It is permissible to reference the same LUT tag data for multiple rendering intents. The LUT is constructed by combining the processing required for the audit and the instruction color conversions. If one of the AT oB/BT oA tags in an audit or instruction CMR is missing when constructing the link LUT, the tag data for the rendering intent specified in that CMR's ICC profile header is used in place of the missing tag data. The default rendering intent profile header is used in place of the missing tag data. The default rendering intent for the Link Color Conversion CMR is the rendering intent specified in the ICCHeader Field of the instruction Color Conversion CMR. The processing detail is described in “Creating Link Color Conversion CMRs – LinkColorConversion LUT subset”. | ✅ |
| CMOCA-4-109 | ICC DeviceLink subset:** Its purpose is to provide a customized path to convert directly from input to output space with no dependency on an audit and instruction CMR. It allows only one single AT oB0Tag representing one rendering intent. The rendering intent is indicated in the header of the ICC profile. The AT oB0Tag contains up to five processing elements, possibly making the conversion more complex than if a single LUT were used. Whereas link CMRs in general are not invoked, CMRs with this subset ID must be invoked in order to be used. A hierarchical search is performed to determine if there is an applicable ICC DeviceLink that can be used. The device should search for an ICC DeviceLink before searching for an Audit/Instruction Color Conversion pair. The currently active Rendering Intent is ignored when an ICC DeviceLink is selected for use. | ✅ |
| CMOCA-4-110 | Currently, three Link Color Conversion subsets are defined. Table 22 lists the Link Color Conversion CMR subsets and their descriptions: | ✅ |
| CMOCA-4-111 | X'01' | LinkColorConversion LUT | Connection between two device color spaces, or the connection between a non-device color space and a device color space (e.g., scanner→printer or sRGB→printer). Selected based on OIDs of selected audit/instruction CC CMRs. | ✅ |
| CMOCA-4-112 | X'02' | LinkColorConversion Identity | No conversion needed. Selected based on OIDs of selected audit/instruction CC CMRs. | ✅ |
| CMOCA-4-113 | X'03' | ICC DeviceLink | Direct conversion between input and output space without reference to audit/instruction CC pair and without use of a PCS. | ✅ |
| CMOCA-4-114 | Mandatory Tags:** Link Color Conversion Subset, Link Audit CMR OID, Link Instruction CMR OID, Link Audit CMR Name, Link Instruction CMR Name, Default Rendering Intent, Link LUT Perceptual, End Data | ✅ |
| CMOCA-4-115 | Optional Tags:** Comment, Date and Time Stamp, Link LUT Media-Relative Colorimetric, Link LUT Saturation, Link LUT ICC-Absolute Colorimetric, Link CMRE Identifier | ✅ |
| CMOCA-4-116 | This subset is used when the input space is the same as the device's output space and no color conversion is to be done. There is no data with this subset. The OIDs for the audit and instruction Color Conversion CMR must be the same. | ✅ |
| CMOCA-4-117 | Mandatory Tags:** Link Color Conversion Subset, Link Audit CMR OID, Link Instruction CMR OID, Link Audit CMR Name, Link Instruction CMR Name, End Data | ✅ |
| CMOCA-4-118 | Optional Tags:** Comment, Date and Time Stamp, Link CMRE Identifier | ✅ |
| CMOCA-4-119 | Mandatory Tags:** Link Color Conversion Subset, ICC Profile Data, End Data | ✅ |
| CMOCA-4-120 | Optional Tags:** Comment, Date and Time Stamp, ICC Profile Filename | ✅ |
| CMOCA-4-121 | Mandatory ICCTags:** profileDescriptionTag, copyrightTag, profileSequenceDescTag, AtoB0Tag, colorantTableTag (required only if Data Colour Space is xCLR), colorantTableOutTag (required only if Profile Connection Space is xCLR) | ✅ |
| CMOCA-4-122 | Optional ICCTags:** None | ✅ |
| CMOCA-4-123 | CMR Type IX (X'0049 0058')** | ✅ |
| CMOCA-4-124 | An Indexed CMR contains one or more Color Palette tags that translate 2-byte indexed color values to the target color space. Five Color Palette tags are defined for the color spaces of gray, RGB, CMYK, CIELAB, and named colorants. The named colorants are defined through a set of colorant names that are specified in the Colorant Identification List tag. Currently, only one Indexed CMR subset is defined for the multi-output color spaces. It allows the mixture of different output color spaces in an Indexed CMR. When multiple Color Palette tags are present in a CMR, and the same indexed color value is specified in different Color Palette tags, the indexed color value in the Color Palette tag with the lower TagID number is used. If the color space of that Color Palette tag is not applicable for the output device, the CIELAB value specified for this indexed color value in the Color Palette tag is used for the substitution. | ✅ |
| CMOCA-4-125 | Color Palette Gray | Color Palette tag for monochrome output devices | ✅ |
| CMOCA-4-126 | Color Palette CMYK | Color Palette tag for CMYK output devices | ✅ |
| CMOCA-4-127 | Color Palette RGB | Color Palette tag for RGB output devices | ✅ |
| CMOCA-4-128 | Color Palette CIELAB | Color Palette tag for the D50 CIELAB color space | ✅ |
| CMOCA-4-129 | Color Palette Named Colorants | Color Palette tag for the named colorants color space | ✅ |
| CMOCA-4-130 | Mandatory Tags:** Indexed Subset, one of Color Palette tags, End Data | ✅ |
| CMOCA-4-131 | Optional Tags:** Comment, Date and Time Stamp, Color Palette tags | ✅ |
| CMOCA-4-132 | If no Color Palette tag is specified, exception condition EC-50400E exists. It is shown in “Color Palette Named Colorants”. | ✅ |
| CMOCA-4-133 | EC-50400E Missing Required Tag:** At least one Color Palette tag is required but none were specified. | ✅ |
| CMOCA-5-001 | The CMR Data field carries all the actual color resource data. The resource data is carried in a tagged format. CMR is big endian. The tags are loosely based on the TIFF tag syntax, but with significant changes and additions. The tags are carried first, optionally followed by the tag data. The last tag is always the End Data tag. | ✅ |
| CMOCA-5-002 | Each tag consists of 12 bytes in the following format: | ✅ |
| CMOCA-5-003 | 0–1 | CODE | TagID | X'0000'–X'FFFF' | Unique identifier for the tag | ✅ |
| CMOCA-5-004 | 2 | | Reserved | X'00' | Should be set to zero | ✅ |
| CMOCA-5-005 | 3 | CODE | Field Type | X'01' | 1-byte UBIN | ✅ |
| CMOCA-5-006 | X'02' | 2-byte UBIN | ✅ |
| CMOCA-5-007 | X'04' | 4-byte UBIN | ✅ |
| CMOCA-5-008 | X'05' | BYTE (8 bits) | ✅ |
| CMOCA-5-009 | X'06' | ASCII | ✅ |
| CMOCA-5-010 | X'07' | UTF16 (UTF-16BE) | ✅ |
| CMOCA-5-011 | X'08' | CODE (8 bit architected constant) | ✅ |
| CMOCA-5-012 | X'09' | BITS | ✅ |
| CMOCA-5-013 | 4–7 | UBIN | Count | X'00000000' – X'FFFFFFFF' | Number of values of the indicated Field Type (may be zero) | ✅ |
| CMOCA-5-014 | 8–11 | | ValueOffset | Any | Data, left-aligned, if it fits into 4 bytes. Otherwise, offset to data is an offset from byte 164 of the CMR (i.e., from the start of CMRData). | ✅ |
| CMOCA-5-015 | Field Type X'05' (BYTE) is used for the tags whose data has a defined structure, such as OID, Date and Time Stamp, ICC Profile Data, and Link LUT tags. Field Type X'06' (ASCII) is defined in the MO:DCA architecture with encoding scheme ID X'2100' – PC-Data, single byte. UBIN is defined as unsigned binary. | ✅ |
| CMOCA-5-016 | Tags X'F000'–X'FFFE' are private tags. Organizations may use a private tag in this range for their exclusive use without disclosing the tag contents. The architecture requires that such tag be non-essential, in the sense that any receivers not supporting the tag will not fail on parsing or using the resource. | ✅ |
| CMOCA-5-017 | X'EF00'–X'EFFF' are reserved for error handling for the CMR header. | ✅ |
| CMOCA-5-018 | The tags in a CMR must be specified in increasing order by their TagIDs. If they are out of order, the Exception EC-xxxx0F exists. Unless otherwise specified within the individual tag description, each TagID may appear at most once and Exception EC-xxxx0F exists if it is specified more than once. Multiple tags with the same ID may be accepted if the particular tag description explicitly states that it may repeat. The description in the tag must explain how the multiple tags are used and which one wins in cases of conflict. Tag values in the CMR tags are listed in the tag registry, that can be found in Appendix A, “Tag Registry”. Private tags are ignored. Any TagID not supported by the device causes the Exception EC-xxxx04. The last tag must be the End Data tag (TagID of X'FFFF'), or exception EC-FFFF0E exists. | ✅ |
| CMOCA-5-019 | There is no restriction on where the actual data fields are located, as long as they are within the CMRData field scope. Note that all the offsets are from the beginning of the CMRData field, so that the location of the CMR header can be changed without any need to update the ValueOffset values. The offsets do not have to be increasing as the TagIDs increase, nor do they have to follow any other rule. There is no requirement that all the data in the scope be used, that is, it is permissible to have data not referenced by any tag. | ✅ |
| CMOCA-5-020 | The number of bytes of data for a tag is the value of Count multiplied by the size of each data item as indicated by Field Type. For example, a Count of 1 indicates two bytes if Field Type is X'02' (2-byte UBIN) or X'07' (UTF16). | ✅ |
| CMOCA-5-021 | Each type of CMR has a list of Mandatory Tags and a list of optional tags. The receivers should ignore any unknown tags. If an optional tag is not present, the default value (if one exists) should be used. | ✅ |
| CMOCA-5-022 | On encountering an error, an exception is raised. Each exception is defined by a three byte value. The first two bytes are the relevant TagID value (X'0000'–X'FFFF'), while the third byte is the exception code. The exception codes are defined as follows: | ✅ |
| CMOCA-5-023 | X'04' | Unsupported TagID Value in a CMR tag | ✅ |
| CMOCA-5-024 | X'05' | Invalid Count Value | ✅ |
| CMOCA-5-025 | X'06' | Invalid Field Type | ✅ |
| CMOCA-5-026 | X'0E' | Missing Required Tag | ✅ |
| CMOCA-5-027 | X'0F' | Invalid Sequence | ✅ |
| CMOCA-5-028 | X'10' | Invalid or unsupported field value or an offset that causes the tag data to start or end after the end of the CMR (as defined by the CMR length) | ✅ |
| CMOCA-5-029 | X'11' | Inconsistent Tag Contents | ✅ |
| CMOCA-5-030 | X'12' | Incorrect order of repeating groups | ✅ |
| CMOCA-5-031 | X'13' | Duplicate value | ✅ |
| CMOCA-5-032 | The CMR coordinate system is a two dimensional Cartesian coordinate system. The horizontal axis is labeled x, and the vertical axis is labeled y. Positive x is to the right of the origin, and positive y is above the origin. The measurement unit is pixel. | ✅ |
| CMOCA-5-033 | Figure 7. Cartesian Coordinate System** | ✅ |
| CMOCA-5-034 | This section defines the CMR tags. | ✅ |
| CMOCA-5-035 | TagID:** X'0004' | ✅ |
| CMOCA-5-036 | Field Type:** X'06' (ASCII), X'07' (UTF16) | ✅ |
| CMOCA-5-037 | Count:** Number of characters | ✅ |
| CMOCA-5-038 | This tag defines arbitrary comment text, ignored by receivers. There is no default. | ✅ |
| CMOCA-5-039 | EC-000406 Invalid Field Type:** The specified Field Type is invalid for the tag. | ✅ |
| CMOCA-5-040 | EC-00040F Invalid Sequence:** The tag has been encountered out of sequence or more than once. | ✅ |
| CMOCA-5-041 | EC-000410 Invalid Value:** The offset caused some portion of the tag data to be outside of the CMRdata. | ✅ |
| CMOCA-5-042 | TagID:** X'0008' | ✅ |
| CMOCA-5-043 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-044 | Count:** 10 | ✅ |
| CMOCA-5-045 | This tag contains the date and time of the creation of the CMR. It is defined consistently with the MO:DCA definition of the Universal Date and Time Stamp Triplet X'72' that is specified in accordance with the format defined in ISO 8601:1988 (E). The tag is informational. The date and time values are not checked for validity. | ✅ |
| CMOCA-5-046 | 0–1 | 2-byte UBIN | YearAD | 0–65,535 | Year AD using Gregorian calendar | ✅ |
| CMOCA-5-047 | 2 | 1-byte UBIN | Month | 1–12 | Month of the year | ✅ |
| CMOCA-5-048 | 3 | 1-byte UBIN | Day | 1–31 | Day of the month | ✅ |
| CMOCA-5-049 | 4 | 1-byte UBIN | Hour | 0–23 | Hour of the day in 24-hour format | ✅ |
| CMOCA-5-050 | 5 | 1-byte UBIN | Minute | 0–59 | Minute of the hour | ✅ |
| CMOCA-5-051 | 6 | 1-byte UBIN | Second | 0–59 | Second of the minute | ✅ |
| CMOCA-5-052 | 7 | CODE | TimeZone | X'00', X'01', X'02' | Relationship of time to UTC (Coordinated, Ahead, Behind) | ✅ |
| CMOCA-5-053 | 8 | 1-byte UBIN | UTCDiffH | 0–23 | Hours ahead of or behind UTC | ✅ |
| CMOCA-5-054 | 9 | 1-byte UBIN | UTCDiffM | 0–59 | Minutes ahead of or behind UTC | ✅ |
| CMOCA-5-055 | YearAD:** Specifies the year AD using the Gregorian calendar. For example, the year 1999 is specified as X'07CF'. Represents the YYYY component. | ✅ |
| CMOCA-5-056 | Month:** Specifies the month of the year. January is X'01'. Represents the MM component. | ✅ |
| CMOCA-5-057 | Day:** Specifies the day of the month. The first day is X'01'. Represents the DD component. | ✅ |
| CMOCA-5-058 | Hour:** Specifies the hour (0-23). Represents the hh component. | ✅ |
| CMOCA-5-059 | Minute:** Specifies the minute (0-59). Represents the mm component. | ✅ |
| CMOCA-5-060 | Second:** Specifies the second (0-59). Represents the ss component. | ✅ |
| CMOCA-5-061 | TimeZone:** Defines the relation to UTC. | ✅ |
| CMOCA-5-062 | X'00':** Time is specified in UTC. UTCDiffH/M should be X'00'. (Suffix Z) | ✅ |
| CMOCA-5-063 | X'01':** Ahead of UTC. UTCDiffH/M specify the difference. (Suffix +hhmm) | ✅ |
| CMOCA-5-064 | X'02':** Behind UTC. UTCDiffH/M specify the difference. (Suffix -hhmm) | ✅ |
| CMOCA-5-065 | EC-000805 Invalid Count Value:** The specified Count field value is invalid for the tag. | ✅ |
| CMOCA-5-066 | EC-000806 Invalid Field Type:** The specified Field Type is invalid for the tag. | ✅ |
| CMOCA-5-067 | EC-00080F Invalid Sequence:** The tag has been encountered out of sequence or more than once. | ✅ |
| CMOCA-5-068 | EC-000810 Invalid Value:** The offset caused some portion of the tag data to be outside of the CMRdata. | ✅ |
| CMOCA-5-069 | TagID:** X'0011' | ✅ |
| CMOCA-5-070 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-071 | Count:** 1 | ✅ |
| CMOCA-5-072 | This tag defines the number of color components referenced by this resource. To comply with ICC, the number of components must be in the range of 1–15. | ✅ |
| CMOCA-5-073 | XYZ | X | Y | Z | ✅ |
| CMOCA-5-074 | Lab | L | a | b | ✅ |
| CMOCA-5-075 | Luv | L | u | v | ✅ |
| CMOCA-5-076 | Yxy | Y | X | y | ✅ |
| CMOCA-5-077 | YCbr | Y | Cb | Cr | ✅ |
| CMOCA-5-078 | RGB | R | G | B | ✅ |
| CMOCA-5-079 | GRAY | K | ✅ |
| CMOCA-5-080 | HSV | H | S | V | ✅ |
| CMOCA-5-081 | HLS | H | L | S | ✅ |
| CMOCA-5-082 | CMYK | C | M | Y | K | ✅ |
| CMOCA-5-083 | CMY | C | M | Y | ✅ |
| CMOCA-5-084 | 2CLR | Component 1 | Component 2 | ✅ |
| CMOCA-5-085 | 3CLR | Component 1 | Component 2 | Component 3 | ✅ |
| CMOCA-5-086 | 4CLR | Component 1 | Component 2 | Component 3 | Component 4 | ✅ |
| CMOCA-5-087 | The components are numbered according to the order in the ICC data tag. Additional color spaces can be added simply by defining the signature component assignments. Default is 1. | ✅ |
| CMOCA-5-088 | EC-001105 Invalid Count Value** | ✅ |
| CMOCA-5-089 | EC-001106 Invalid Field Type** | ✅ |
| CMOCA-5-090 | EC-00110F Invalid Sequence** | ✅ |
| CMOCA-5-091 | EC-001110 Invalid Value:** number of components is zero or greater than 15. | ✅ |
| CMOCA-5-092 | TagID:** X'1011' | ✅ |
| CMOCA-5-093 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-094 | Count:** 1 | ✅ |
| CMOCA-5-095 | This tag denotes a subset of the Halftone CMR type. | ✅ |
| CMOCA-5-096 | X'01' | Bilevel Point-Operation Halftone | ✅ |
| CMOCA-5-097 | X'02' | Multilevel Point-Operation Halftone | ✅ |
| CMOCA-5-098 | X'03' | Bilevel Error Diffusion Halftone | ✅ |
| CMOCA-5-099 | X'04' | Multilevel Error Diffusion Halftone | ✅ |
| CMOCA-5-100 | EC-101110 Invalid Value:** specified subset value is none of X'01', X'02', X'03', or X'04'. | ✅ |
| CMOCA-5-101 | TagID:** X'1021' | ✅ |
| CMOCA-5-102 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN) | ✅ |
| CMOCA-5-103 | Count:** Number of color components | ✅ |
| CMOCA-5-104 | This tag defines the width of the array along the x-direction in pixels for each color component. | ✅ |
| CMOCA-5-105 | TagID:** X'1025' | ✅ |
| CMOCA-5-106 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN) | ✅ |
| CMOCA-5-107 | Count:** Number of color components | ✅ |
| CMOCA-5-108 | This tag defines the height of the array along the y-direction in pixels for each color component. | ✅ |
| CMOCA-5-109 | TagID:** X'1030' | ✅ |
| CMOCA-5-110 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN) | ✅ |
| CMOCA-5-111 | Count:** Number of color components | ✅ |
| CMOCA-5-112 | This tag defines the maximum input image value per component. | ✅ |
| CMOCA-5-113 | TagID:** X'1035' | ✅ |
| CMOCA-5-114 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-115 | Count:** Number of color components | ✅ |
| CMOCA-5-116 | This tag defines the number of device levels per component for multilevel devices. Each specified Number of Device Levels must be greater than 2. | ✅ |
| CMOCA-5-117 | TagID:** X'1040' | ✅ |
| CMOCA-5-118 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN) | ✅ |
| CMOCA-5-119 | Count:** Number of color components | ✅ |
| CMOCA-5-120 | This tag defines the amount of shift in pixels between the halftone tiles in two adjacent rows for each component. | ✅ |
| CMOCA-5-121 | Figure 8. Illustration of Offset Tiling with Offset Tiling=2** | ✅ |
| CMOCA-5-122 | TagID:** X'1045' | ✅ |
| CMOCA-5-123 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN) | ✅ |
| CMOCA-5-124 | Count:** Sum of (Array Width × Array Height) over all color components | ✅ |
| CMOCA-5-125 | This tag specifies the threshold array values for each screen. Arranged in row-major format. Component-wise structured. | ✅ |
| CMOCA-5-126 | TagID:** X'1050' | ✅ |
| CMOCA-5-127 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-128 | Count:** Sum of (Array Width × Array Height × (Max Image Value + 1)) over all color components | ✅ |
| CMOCA-5-129 | This tag gives the device gray level for each pixel. Each screen is a 3-d table lookup. Dimensions are m × n × (Max Image Value + 1). | ✅ |
| CMOCA-5-130 | TagID:** X'1055' | ✅ |
| CMOCA-5-131 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-132 | Count:** Sum of (Array Width × Array Height) over all color components | ✅ |
| CMOCA-5-133 | This tag specifies a set of values in the error diffusion filter. Arranged in a 2-dimensional array for each color plane. | ✅ |
| CMOCA-5-134 | Figure 9. Illustration of Error Distribution with Floyd-Steinberg Filter** | ✅ |
| CMOCA-5-135 | TagID:** X'1060' | ✅ |
| CMOCA-5-136 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-137 | Count:** 2 × number of color components | ✅ |
| CMOCA-5-138 | Specifies a pair of values (row, column) describing the location of the current pixel in an error diffusion filter. | ✅ |
| CMOCA-5-139 | TagID:** X'1065' | ✅ |
| CMOCA-5-140 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-141 | Count:** Number of color components | ✅ |
| CMOCA-5-142 | X'01' | Normal raster | ✅ |
| CMOCA-5-143 | X'02' | Serpentine raster | ✅ |
| CMOCA-5-144 | Figure 10. Illustration of Normal Raster and Serpentine Raster** | ✅ |
| CMOCA-5-145 | TagID:** X'1070' | ✅ |
| CMOCA-5-146 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-147 | Count:** Number of color components | ✅ |
| CMOCA-5-148 | X'01' | None | ✅ |
| CMOCA-5-149 | X'02' | Zero boundary | ✅ |
| CMOCA-5-150 | X'03' | Reflect | ✅ |
| CMOCA-5-151 | X'04' | Periodic | ✅ |
| CMOCA-5-152 | TagID:** X'1075' | ✅ |
| CMOCA-5-153 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN) | ✅ |
| CMOCA-5-154 | Count:** Number of color components | ✅ |
| CMOCA-5-155 | Specifies a single threshold value for bilevel error diffusion halftones. | ✅ |
| CMOCA-5-156 | TagID:** X'1080' | ✅ |
| CMOCA-5-157 | Field Type:** X'01' (1-byte UBIN), X'02' (2-byte UBIN), X'04' (4-byte UBIN) | ✅ |
| CMOCA-5-158 | Count:** Sum of (Number of Device Levels – 1) over all color components | ✅ |
| CMOCA-5-159 | Specifies n one-dimensional arrays for multilevel error diffusion halftone. | ✅ |
| CMOCA-5-160 | 1 | 60 | ✅ |
| CMOCA-5-161 | 2 | 120 | ✅ |
| CMOCA-5-162 | 3 | 200 | ✅ |
| CMOCA-5-163 | Initial Value | Output Device Level | Corrected value | ✅ |
| CMOCA-5-164 | I ∈ [0, 60) | 0 | 0 | ✅ |
| CMOCA-5-165 | I ∈ [60, 120) | 1 | 85 | ✅ |
| CMOCA-5-166 | I ∈ [120, 200) | 2 | 170 | ✅ |
| CMOCA-5-167 | I ∈ [200, 255] | 3 | 255 | ✅ |
| CMOCA-5-168 | TagID:** X'2004' | ✅ |
| CMOCA-5-169 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-170 | Count:** 1 | ✅ |
| CMOCA-5-171 | X'01' | ToneTransferCurve Array | ✅ |
| CMOCA-5-172 | X'02' | ToneTransferCurve Identity | ✅ |
| CMOCA-5-173 | TagID:** X'2011' | ✅ |
| CMOCA-5-174 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-175 | Count:** Number of color components | ✅ |
| CMOCA-5-176 | X'01' | 256 1-byte entries | ✅ |
| CMOCA-5-177 | X'02' | 65,536 2-byte entries | ✅ |
| CMOCA-5-178 | TagID:** X'2015' | ✅ |
| CMOCA-5-179 | Field Type:** X'05' (byte) | ✅ |
| CMOCA-5-180 | Count:** Total length of the data | ✅ |
| CMOCA-5-181 | TagID:** X'2020' | ✅ |
| CMOCA-5-182 | Field Type:** X'05' (Byte) | ✅ |
| CMOCA-5-183 | Count:** Total length of the data | ✅ |
| CMOCA-5-184 | TagID:** X'3011' | ✅ |
| CMOCA-5-185 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-186 | Count:** 1 | ✅ |
| CMOCA-5-187 | X'01' | Monochrome input profile | ✅ |
| CMOCA-5-188 | X'02' | Monochrome display profile | ✅ |
| CMOCA-5-189 | X'03' | Monochrome output profile | ✅ |
| CMOCA-5-190 | X'04' | Three-component matrix-based input profile | ✅ |
| CMOCA-5-191 | X'05' | Three-component matrix-based display profile | ✅ |
| CMOCA-5-192 | X'06' | N-component LUT-based input profile | ✅ |
| CMOCA-5-193 | X'07' | N-component LUT-based display profile | ✅ |
| CMOCA-5-194 | X'08' | N-component LUT-based output profiles | ✅ |
| CMOCA-5-195 | X'09' | ColorSpace conversion profile | ✅ |
| CMOCA-5-196 | X'0A' | Retired item 3 (Abstract profile) | ✅ |
| CMOCA-5-197 | TagID:** X'3015' | ✅ |
| CMOCA-5-198 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-199 | Count:** The number of bytes in the profile | ✅ |
| CMOCA-5-200 | 0–3 | Profile size | ✅ |
| CMOCA-5-201 | 4–7 | CMM Type signature | ✅ |
| CMOCA-5-202 | 8–11 | Profile version number | ✅ |
| CMOCA-5-203 | 12–15 | Profile/Device Class signature | ✅ |
| CMOCA-5-204 | 16–19 | Color Space of Data | ✅ |
| CMOCA-5-205 | 20–23 | Profile Connection Space (PCS) | ✅ |
| CMOCA-5-206 | 24–35 | Date and time this profile was first created | ✅ |
| CMOCA-5-207 | 36–39 | acsp (61637370h) profile file signature | ✅ |
| CMOCA-5-208 | 40–43 | Primary Platform signature | ✅ |
| CMOCA-5-209 | 44–47 | Flags to indicate various options for the CMM | ✅ |
| CMOCA-5-210 | 48–51 | Device manufacturer | ✅ |
| CMOCA-5-211 | 52–55 | Device model | ✅ |
| CMOCA-5-212 | 56–63 | Device attributes (e.g., media type) | ✅ |
| CMOCA-5-213 | 64–67 | Rendering Intent | ✅ |
| CMOCA-5-214 | 68–79 | XYZ values of the illuminant (must be D50) | ✅ |
| CMOCA-5-215 | 80–83 | Profile Creator signature | ✅ |
| CMOCA-5-216 | 84–99 | Profile ID | ✅ |
| CMOCA-5-217 | 100–127 | 28 bytes reserved (set to zeros) | ✅ |
| CMOCA-5-218 | TagID:** X'3025' | ✅ |
| CMOCA-5-219 | Field Type:** X'06' (ASCII), X'07' (UTF16) | ✅ |
| CMOCA-5-220 | Count:** Number of characters | ✅ |
| CMOCA-5-221 | TagID:** X'4011' | ✅ |
| CMOCA-5-222 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-223 | Count:** 1 | ✅ |
| CMOCA-5-224 | X'01' | LinkColorConversion LUT | ✅ |
| CMOCA-5-225 | X'02' | LinkColorConversion Identity | ✅ |
| CMOCA-5-226 | X'03' | ICC DeviceLink | ✅ |
| CMOCA-5-227 | TagID:** X'4015' | ✅ |
| CMOCA-5-228 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-229 | Count:** Number of bytes in the OID | ✅ |
| CMOCA-5-230 | TagID:** X'4020' | ✅ |
| CMOCA-5-231 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-232 | Count:** Number of bytes in the OID | ✅ |
| CMOCA-5-233 | TagID:** X'4025' | ✅ |
| CMOCA-5-234 | Field Type:** X'07' (UTF16) | ✅ |
| CMOCA-5-235 | Count:** Number of characters | ✅ |
| CMOCA-5-236 | TagID:** X'4030' | ✅ |
| CMOCA-5-237 | Field Type:** X'07' (UTF16) | ✅ |
| CMOCA-5-238 | Count:** Number of characters | ✅ |
| CMOCA-5-239 | TagID:** X'4035' | ✅ |
| CMOCA-5-240 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-241 | Count:** 1 | ✅ |
| CMOCA-5-242 | Perceptual | X'00' | ✅ |
| CMOCA-5-243 | Media-Relative Colorimetric | X'01' | ✅ |
| CMOCA-5-244 | Saturation | X'02' | ✅ |
| CMOCA-5-245 | ICC-Absolute Colorimetric | X'03' | ✅ |
| CMOCA-5-246 | TagID:** X'4040' | ✅ |
| CMOCA-5-247 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-248 | Count:** The number of bytes in the LUT + 20 bytes of the header | ✅ |
| CMOCA-5-249 | 0 | 1 | 1-byte UBIN | 1–15 | Number of components of the input color space | ✅ |
| CMOCA-5-250 | 1 | 1 | 1-byte UBIN | 1–15 | Number of components of the output color space | ✅ |
| CMOCA-5-251 | 2–16 | 15 | 1-byte UBIN | 0–255 | Number of grid points in each component of input | ✅ |
| CMOCA-5-252 | 17 | 1 | 1-byte UBIN | 1, 2 | Precision: 1=1-byte UBIN, 2=2-byte UBIN | ✅ |
| CMOCA-5-253 | 18 | 1 | BITS | | Additional use flags: bit 0: Media-rel, bit 1: Saturation, bit 2: ICC-Abs | ✅ |
| CMOCA-5-254 | 19 | 1 | | 0 | Reserved | ✅ |
| CMOCA-5-255 | 20 to end | | | | LUT data | ✅ |
| CMOCA-5-256 | TagID:** X'4045' | ✅ |
| CMOCA-5-257 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-258 | TagID:** X'4050' | ✅ |
| CMOCA-5-259 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-260 | TagID:** X'4055' | ✅ |
| CMOCA-5-261 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-262 | TagID:** X'4090' | ✅ |
| CMOCA-5-263 | Field Type:** X'07' (UTF16) | ✅ |
| CMOCA-5-264 | Count:** Number of characters | ✅ |
| CMOCA-5-265 | TagID:** X'5011' | ✅ |
| CMOCA-5-266 | Field Type:** X'08' (CODE) | ✅ |
| CMOCA-5-267 | Count:** 1 | ✅ |
| CMOCA-5-268 | X'01' | Multi-output color spaces | ✅ |
| CMOCA-5-269 | TagID:** X'5015' | ✅ |
| CMOCA-5-270 | Field Type:** X'01' (1-byte UBIN) | ✅ |
| CMOCA-5-271 | Count:** 1 | ✅ |
| CMOCA-5-272 | TagID:** X'5020' | ✅ |
| CMOCA-5-273 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-274 | Count:** 9 × the number of color entries | ✅ |
| CMOCA-5-275 | 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value | ✅ |
| CMOCA-5-276 | 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components | ✅ |
| CMOCA-5-277 | 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Intensity of gray (X'00'=black, X'FF'=white) | ✅ |
| CMOCA-5-278 | TagID:** X'5025' | ✅ |
| CMOCA-5-279 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-280 | Count:** 12 × the number of color entries | ✅ |
| CMOCA-5-281 | 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value | ✅ |
| CMOCA-5-282 | 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components | ✅ |
| CMOCA-5-283 | 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Cyan | ✅ |
| CMOCA-5-284 | 9 | 1-byte UBIN | Component_2 | X'00'–X'FF' | Magenta | ✅ |
| CMOCA-5-285 | 10 | 1-byte UBIN | Component_3 | X'00'–X'FF' | Yellow | ✅ |
| CMOCA-5-286 | 11 | 1-byte UBIN | Component_4 | X'00'–X'FF' | Black | ✅ |
| CMOCA-5-287 | TagID:** X'5030' | ✅ |
| CMOCA-5-288 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-289 | Count:** 11 × the number of color entries | ✅ |
| CMOCA-5-290 | 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value | ✅ |
| CMOCA-5-291 | 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components | ✅ |
| CMOCA-5-292 | 8 | 1-byte UBIN | Component_1 | X'00'–X'FF' | Red | ✅ |
| CMOCA-5-293 | 9 | 1-byte UBIN | Component_2 | X'00'–X'FF' | Green | ✅ |
| CMOCA-5-294 | 10 | 1-byte UBIN | Component_3 | X'00'–X'FF' | Blue | ✅ |
| CMOCA-5-295 | TagID:** X'5035' | ✅ |
| CMOCA-5-296 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-297 | Count:** 8 × the number of color entries | ✅ |
| CMOCA-5-298 | 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value | ✅ |
| CMOCA-5-299 | 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components | ✅ |
| CMOCA-5-300 | TagID:** X'5040' | ✅ |
| CMOCA-5-301 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-302 | Count:** (Number of Named Colorants + 8) × the number of color entries | ✅ |
| CMOCA-5-303 | 0–1 | 2-byte UBIN | IndexedColorValue | X'0100' – X'FFFF' | 2-byte indexed color value | ✅ |
| CMOCA-5-304 | 2–7 | 2-byte UBIN | CIELABValue | X'0000' – X'FFFF' | L*, a*, b* components | ✅ |
| CMOCA-5-305 | 8 to 7+n | 1-byte UBIN | Component_i | X'00'–X'FF' | Intensity of i-th colorant | ✅ |
| CMOCA-5-306 | TagID:** X'5045' | ✅ |
| CMOCA-5-307 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-308 | Count:** Sum of the length over the Number of Named Colorants | ✅ |
| CMOCA-5-309 | 0 | 1-byte UBIN | Length | X'03'–X'FB' | Length of this repeating group | ✅ |
| CMOCA-5-310 | 1–end | UTF-16 | Colorant Name | | Colorant name in free format UTF-16BE | ✅ |
| CMOCA-5-311 | TagID:** X'FFFF' | ✅ |
| CMOCA-5-312 | Field Type:** X'05' (BYTE) | ✅ |
| CMOCA-5-313 | Count:** 0 | ✅ |
| CMOCA-5-314 | Signifies the end of the tag list. | ✅ |
| CMOCA-6-001 | Takes presentation data specified in an input color space | ✅ |
| CMOCA-6-002 | Converts it to the output color space of the presentation device | ✅ |
| CMOCA-6-003 | Modifies the colors to create the desired output for a particular device | ✅ |
| CMOCA-6-004 | Halftones the output data | ✅ |
| CMOCA-6-005 | The actual input and output device color spaces constrain which CMRs are applicable. There can be multiple CMRs that are invoked, but only some of them are usable for given data. | ✅ |
| CMOCA-6-006 | Further, CMRs can be invoked at multiple levels of the AFP document hierarchy and it is possible to have more than one CMR that is applicable for a given task at one time. For instance, there can be two audit color conversions from RGB to CIELAB; one is SMPTE-C and the other is sRGB. One can be invoked at the object level and the other at the page level. IPDS hierarchical rules and last-received-wins (when multiple conflicting CMRs exist at the same level) are used to resolve conflicts. | ✅ |
| CMOCA-6-007 | Media matching also affects the hierarchical search. If the media attributes specified in the CMR header do not match the media currently in use by the device, the hierarchical search may continue, looking for a CMR that better matches the media. This is described in “Matching Media Type of CMR With Media Type of Device”. | ✅ |
| CMOCA-6-008 | Note that there might be multiple CMRs of a given type invoked at one particular level. For instance, there could be two audit Color Conversion CMRs attached to a GOCA object, one for CMYK input data and the other for RGB data. Colors within the GOCA object might be specified using both color spaces and the appropriate CMR would be used each time. | ✅ |
| CMOCA-6-009 | Taken as a whole, the CMR system can seem complex. But a typical situation will be simple. Some complicated explanations will be included later to clearly define what must be done in complex situations, but they will rarely be encountered. | ✅ |
| CMOCA-6-010 | When a CMR is needed, the device searches the hierarchy for an applicable CMR that applies to the current color space. The AFP architecture hierarchy for CMRs is as follows: | ✅ |
| CMOCA-6-011 | 1.  **CMR invoked with an object.** Note that CMRs attached to an object received in home state are ignored and that CMRs can be attached to an object when it is included using an IDO. (See “Color Conversion Profiles Within TIFF, JFIF, and GIF” for a discussion of profiles embedded within the object.) | ✅ |
| CMOCA-6-012 | 2.  **CMR invoked with a page or overlay** | ✅ |
| CMOCA-6-013 | 3.  **CMR invoked in home state** | ✅ |
| CMOCA-6-014 | 4.  **Device default CMR** | ✅ |
| CMOCA-6-015 | If two applicable CMRs that both apply to the current color space are invoked at the same level, the last one invoked is used. If no applicable CMR is explicitly invoked, a device default is used. | ✅ |
| CMOCA-6-016 | For color conversions, link CMRs are normally used to improve throughput. The following discussion assumes that there are no active link CMRs. “Link Color Conversion CMRs”, discusses Link Color Conversion CMRs. | ✅ |
| CMOCA-6-017 | 1.  Presentation data specifies an input color space. Knowing that color space, a search is done of the invoked audit Tone Transfer Curve CMRs to find the first one that has the same number of components. If one is not found, the identity tone transfer curve (that is, the printer default) is used. | ✅ |
| CMOCA-6-018 | 2.  Next, knowing the input color space, a search is done of the invoked audit Color Conversion CMRs to find the first one that has that input color space. This is done by examining the Color Space Signature field within the ICC profile header. In cases where the name of the input color space is unknown, the number of components in the input data will be used to select a Color Conversion CMR. | ✅ |
| CMOCA-6-019 | 3.  A search of the invoked instruction Color Conversion CMRs is done to find the first one with the required output color space. In most cases, this will be a device default CMR. Note that the audit and instruction PCSs do not need to be the same. The device has the ability to convert between CIELAB and CIEXYZ, the available PCSs. | ✅ |
| CMOCA-6-020 | 4.  A search is done of the invoked instruction Tone Transfer Curve CMRs to find one with the correct number of components. | ✅ |
| CMOCA-6-021 | 5.  A search is done of the invoked Halftone CMRs to find one with the right number of components. | ✅ |
| CMOCA-6-022 | 6.  The colored data is converted and halftoned using these CMRs. Note that it is possible to combine some of the operations to improve performance. | ✅ |
| CMOCA-6-023 | CMRs must be applicable in order to be used. If a CMR is not applicable, it may be ignored and no NACK is issued. The following examples explain the meaning of applicable. | ✅ |
| CMOCA-6-024 | An instruction Tone Transfer Curve CMR with three components is not applicable if the device is monochrome. | ✅ |
| CMOCA-6-025 | An instruction Halftone CMR with three components is not applicable on a CMYK device. | ✅ |
| CMOCA-6-026 | An audit Color Conversion CMR that converts from a three-component space is not applicable if the input image has four components. | ✅ |
| CMOCA-6-027 | An output Color Conversion CMR that converts into a four-component color space is not applicable if the device is a CMYK printer that is running in a monochrome-appearance mode. | ✅ |
| CMOCA-6-028 | In order to select a CMR, the hierarchy is searched as discussed above, looking for an applicable CMR. The first applicable CMR found in the hierarchy is selected and used. | ✅ |
| CMOCA-6-029 | Link Color Conversion CMRs provide an efficient method for converting directly from the input color space to the output color space. This is useful for optimizing performance. | ✅ |
| CMOCA-6-030 | Audit CMR identifier: A Link Audit CMR OID tag | ✅ |
| CMOCA-6-031 | Instruction CMR Identifier: A Link Instruction CMR OID tag | ✅ |
| CMOCA-6-032 | When Color Conversion CMRs are sent to the device, an OID must be attached. The OID uniquely identifies the CMR and is used to connect the audit and instruction Color Conversion CMRs with the matching link CMR. The OID format is described in the MO:DCA Registry Appendix of the Mixed Object Document Content Architecture (MO:DCA) Reference. | ✅ |
| CMOCA-6-033 | The audit and instruction CMRs are identified as described above. Then a search is done of Link Color Conversion CMRs to find a link CMR that combines the audit and instruction CMRs. This is done by comparing the CMR OIDs with those specified in the link CMR. Note that any activated link CMR can be used. It does not need to be invoked in order to be eligible for use. | ✅ |
| CMOCA-6-034 | Note, further, that an audit CMR must be identified to use a downloaded link CMR. If the audit-type information from within an object (for example, a TIFF image) is used, there is no way to identify the link that can be substituted. So if it is desirable to use a link CMR, an audit CMR must be attached to the TIFF object. The audit CMR takes precedence over the ICC profile specified within the TIFF. | ✅ |
| CMOCA-6-035 | Link Color Conversion CMRs also require tags containing the CMR names of the audit and instruction CMRs. These two tags are required but are for informational purposes only and are not used while selecting the link CMR. | ✅ |
| CMOCA-6-036 | In some situations, it might be desirable to use a conversion that goes direct from input color space to output color space without using an intermediate conversion into the PCS. For instance, a direct conversion can be used to avoid losing information when conversions go in and out of the PCS. This might preserve information about how much black to use, or information about a spot color. The ICC DeviceLink subset of the link CMR can be used in this situation. Typically, the ICC DeviceLink Profile would be created in some special customized or hand-crafted manner and be targeted at a particular device. | ✅ |
| CMOCA-6-037 | A link CMR with subset type of ICC DeviceLink must be invoked in order to be used. Note that other link CMRs do not need to be invoked. The ICC DeviceLink subset can be easily identified by checking the CMRType field in the CMR header: it will be “DL”. Since an ICC DeviceLink can be invoked at various levels of the hierarchy, a hierarchical search must occur in order to select an ICC DeviceLink for use. | ✅ |
| CMOCA-6-038 | This special link CMR has precedence over audit/instruction color conversions. In the following, “Look for an ICC DeviceLink” means that the input color space matches that of the data and the output space matches that of the device. If multiple device links are invoked at a given level of the hierarchy, the last one invoked will be selected. | ✅ |
| CMOCA-6-039 | The existence of an audit CC at some level has precedence over an ICC DeviceLink at a lower level. The search algorithm is shown here and the figure below diagrammatically shows how to search. | ✅ |
| CMOCA-6-040 | Search at the object level** | ✅ |
| CMOCA-6-041 | Look for an ICC DeviceLink. If found, stop and use it. | ✅ |
| CMOCA-6-042 | Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the selected pair. | ✅ |
| CMOCA-6-043 | If an ICC Profile exists within the object (e.g., TIFF)**, use it and find an instruction CC CMR by searching all levels. | ✅ |
| CMOCA-6-044 | Else search at the page/overlay level** | ✅ |
| CMOCA-6-045 | Look for an ICC DeviceLink. If found, stop and use it. | ✅ |
| CMOCA-6-046 | Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the selected pair. | ✅ |
| CMOCA-6-047 | Else search at higher levels** | ✅ |
| CMOCA-6-048 | Look for an ICC DeviceLink. If found, stop and use it. | ✅ |
| CMOCA-6-049 | Else look for an audit CC CMR. If found, stop, find an instruction CC CMR by searching all levels. Use the selected pair. | ✅ |
| CMOCA-6-050 | Else use the default audit CC CMR** | ✅ |
| CMOCA-6-051 | Note that there is no default ICC DeviceLink CMR. | ✅ |
| CMOCA-6-052 | Find an instruction CC CMR by searching all levels. Use the selected pair. | ✅ |
| CMOCA-6-053 | Figure 11. Selecting Appropriate Color-Conversion CMRs** | ✅ |
| CMOCA-6-054 | An audit Color Conversion CMR describes device-dependent colors or non-device colors (for example, sRGB) in the presentation data. It provides a way of converting from the input color space to a profile connection space (PCS). | ✅ |
| CMOCA-6-055 | If the device is a CMYK printer, only tone transfer curves with 4 components are applicable. | ✅ |
| CMOCA-6-056 | Some devices support only bilevel halftone screens, not multilevel screens. | ✅ |
| CMOCA-6-057 | If the device is an RGB display, it requires color conversions that convert into the RGB of the display. | ✅ |
| CMOCA-6-058 | The device may ignore any instruction CMRs that are not applicable, thus making the search path shorter. Similarly, an ICC DeviceLink CMR must have an output color space that matches the color space of the device. If it does not match, the device may ignore it. | ✅ |
| CMOCA-6-059 | Table 49 shows which ICC profiles may be used for audit CMRs and which may be used for instruction CMRs. | ✅ |
| CMOCA-6-060 | Monochrome input profile | Yes | No | ✅ |
| CMOCA-6-061 | Monochrome display profile | Yes | Yes | ✅ |
| CMOCA-6-062 | Monochrome output profile | Yes | Yes | ✅ |
| CMOCA-6-063 | Three-component matrix-based input profile | Yes | No | ✅ |
| CMOCA-6-064 | Three-component matrix-based display profile | Yes | Yes | ✅ |
| CMOCA-6-065 | N-component LUT-based input profile | Yes | No | ✅ |
| CMOCA-6-066 | N-component LUT-based display profile | Yes | Yes | ✅ |
| CMOCA-6-067 | N-component LUT-based output profiles | Yes | Yes | ✅ |
| CMOCA-6-068 | ColorSpace conversion profile | Yes | No | ✅ |
| CMOCA-6-069 | Input type profiles describe colors coming from a scanner or digital camera and therefore they are not used as instruction CMRs. | ✅ |
| CMOCA-6-070 | A link CMR describes how to convert directly from an input color space to an output device color space. It links an audit and an instruction CMR. It will define four color conversions, one for each of the possible rendering intents. Both the audit and the instruction CMR have rendering intents specified in their ICC header. The rendering intent in the ICC profile header of the instruction CMR becomes the Default Rendering Intent of the link CMR. | ✅ |
| CMOCA-6-071 | The link creation process combines the audit and instruction CMRs and creates four LUTs. Each LUT of the link CMR collapses all the steps of the two (audit and instruction) color conversions into a single multidimensional lookup table. It is possible that some of the LUTs will be identical if not enough information exists to create all versions separately. In this case, the offset for the LUTs could be the same. | ✅ |
| CMOCA-6-072 | To create each link LUT, the appropriate color conversion based on rendering intent must be selected from the ICC profiles for both the audit and the instruction Color Conversion CMRs. To create each one of the four link LUTs: | ✅ |
| CMOCA-6-073 | 1.  The rendering intent of the particular link LUT being created is identified. | ✅ |
| CMOCA-6-074 | 2.  The color conversion corresponding to that rendering intent is selected from the audit CMR. | ✅ |
| CMOCA-6-075 | 3.  The color conversion corresponding to that rendering intent is selected from the instruction CMR. | ✅ |
| CMOCA-6-076 | 4.  The selected audit and instruction color conversions are combined to produce the link LUT for that rendering intent. | ✅ |
| CMOCA-6-077 | The selection is based on the rendering intent of the link LUT currently being created and the goal is to use the color conversion from both the audit and the instruction CMR that is for that particular rendering intent. | ✅ |
| CMOCA-6-078 | When the color conversion rule for one of the rendering intents is not available, another color conversion must be used. The substitution methods are discussed below. | ✅ |
| CMOCA-6-079 | The basic rules for selecting a color conversion that corresponds to a given rendering intent for a given ICC profile are as follows: | ✅ |
| CMOCA-6-080 | 1.  If the appropriate AT oBxTag (or BT oAxTag) for that rendering intent exists, it is used. (AT oB0Tag is used for perceptual, AT oB1Tag is used for media-relative colorimetric, AT oB2Tag is used for saturation.) | ✅ |
| CMOCA-6-081 | 2.  If that tag does not exist, the rendering intent in the header of this ICC profile is noted. The tag corresponding to this rendering intent is used. | ✅ |
| CMOCA-6-082 | 3.  If that tag does not exist, the implementation is device-dependent. | ✅ |
| CMOCA-6-083 | For monochrome profiles, the grayTRCTag is used for all rendering intents. | ✅ |
| CMOCA-6-084 | For three-component matrix-based profiles, the matrix/TRC combinations are used for all rendering intents. | ✅ |
| CMOCA-6-085 | For monochrome profiles, the inverse of the grayTRCTag is used for all rendering intents. | ✅ |
| CMOCA-6-086 | For three-component matrix-based profiles, the inverse of the matrix/TRC transformation, as described in the ICC specification, is used for all rendering intents. | ✅ |
| CMOCA-6-087 | From the audit CMR, use the same tag(s) as were used when creating the link LUT for media-relative colorimetric. Apply a white point conversion based on the audit CMR's ICC mediaWhitePointTag. | ✅ |
| CMOCA-6-088 | From the instruction CMR, use the same tag(s) as were used when creating the link LUT for media-relative colorimetric. Apply a white point conversion based on the instruction CMR's ICC mediaWhitePointTag. | ✅ |
| CMOCA-6-089 | 1.  Converting color into the device color space (CMYK on normal printers) | ✅ |
| CMOCA-6-090 | 2.  Modifying the color for each plane | ✅ |
| CMOCA-6-091 | 3.  Halftoning each plane | ✅ |
| CMOCA-6-092 | The second step, modifying the color for each plane, is typically a one-dimensional conversion and is represented as a 1-D array called a curve. The curve would actually be a set of curves, one for each of the color planes in the color space. This document will use the term “curve” to represent the whole set of curves. Actually, there can be multiple curves, each performing a different function. The curves are used sequentially although, in practice, they might be concatenated to form one curve for improved performance. The effect of each curve is a “delta” to the previous curve. | ✅ |
| CMOCA-6-093 | There must be some way to put the device into a well-known state and maintain it in that state. This well-known state should be the state it was in when the default instruction Color Conversion and Halftone CMRs were created. | ✅ |
| CMOCA-6-094 | The user might want to control the look-and-feel of the output. | ✅ |
| CMOCA-6-095 | Each device may handle these processes in different ways, but the following describes one way of dealing with this complexity. There are four curves to be applied sequentially in this example: | ✅ |
| CMOCA-6-096 | Tone Transfer Curve (TTC):** This curve is contained in a CMR and can be specified by the user to modify the behavior of a device so that a desired relationship between input and output is achieved. This could include ink limiting, linearization, lightness, contrast, the relationship between the highlights, midtones and shadow regions, or even reverse-video. Only one Tone Transfer Curve is used during the processing of a color object. It is selected using the rules of the CMOCA hierarchy. If no Tone Transfer Curve is specified, the printer default (the identity) will be used. | ✅ |
| CMOCA-6-097 | Operator Requested Curve:** This curve allows the user the same control of the look-and-feel of the output as the Tone Transfer Curve. However, this curve is controlled by input from the printer console and is not specified in the data stream. The Operator Requested Curve will be constant for a complete print job or larger boundary. | ✅ |
| CMOCA-6-098 | Tone Reproduction Curve:** This curve is used to put the printer into a known state. It compensates for dot gain, printer characteristics, typical humidity, paper characteristics, ink/toner characteristics, speed, etc. There might be different Tone Reproduction Curves in one printer for printing on different sides, different engines, or different media. The assumption is that, after applying the Tone Reproduction Curve, the device is in an optimal state. | ✅ |
| CMOCA-6-099 | Calibration Curve:** This curve is used to modify the behavior of a device, returning it to a known state. The assumption is that this known state is the optimal state. The Calibration Curve might be something controlled by the operator or might be automatically controlled within the printer. Changes to the Calibration Curve might need to occur relatively frequently due to changing ink/toner characteristics and changing humidity. The Calibration Curve might be different for each Tone Reproduction Curve in the printer. | ✅ |
| CMOCA-6-100 | The Tone Transfer Curve (TTC) and the Operator Requested Curve perform essentially the same function, but the first is transmitted in the data stream and the second is controlled via the printer's user interface. Normally, the two curves would be applied sequentially. However, in some cases, the device might want the Operator Requested Curve to override the TTC, effectively ignoring the TTC. If an applicable TTC is ignored, an error condition exists that is governed by exception ID X'025E..05'. This exception signifies that an “invoked, selected CMR was not used”. The effect of this exception is controlled by the Color Fidelity Triplet and by Error Handling Control (EHC). The Color Fidelity Triplet or EHC can allow processing to continue by using a device default (identity) TTC or can force processing to stop. Reporting of the NACK is also controlled by the Color Fidelity Triplet or the EHC. Thus, by using the Color Fidelity Triplet or EHC, the user can control whether the Operator Requested Curve is allowed to override the TTC. | ✅ |
| CMOCA-6-101 | Note that the above discussion assumes that the printer calibration is done digitally, in software, before the color is halftoned. It is also possible to mechanically control the output after it is halftoned. For instance, it might be possible to regulate the amount of ink emitted by an inkjet. This control could be used instead of the Calibration Curve. | ✅ |
| CMOCA-6-102 | Indexed CMRs provide rules about how to render indexed colors. Indexed CMRs apply to indexed colors that are specified using the Highlight color space. They do not apply to indexed colors found within PostScript or other non-IPDS data objects. | ✅ |
| CMOCA-6-103 | For Indexed CMRs, both instruction and audit processing modes are valid. However, only Indexed CMRs with a processing mode of instruction will be used. Indexed CMRs that have an audit processing mode specified are ignored. | ✅ |
| CMOCA-6-104 | The tags in the Indexed CMR allow the CMR to use various color spaces in the descriptions. These color spaces can be grayscale (monochrome), named colorants, RGB, CMYK, or CIELAB. A conversion from the index into CIELAB must always be provided. If a conversion into another color space is provided, it is used when applicable. For instance, if a conversion into CMYK is provided and the device is a CMYK printer, the conversion is used. The CMYK is assumed to be the device's CMYK and no color conversion CMRs are used. However, if a conversion into RGB is specified for that same CMYK printer, it is not applicable and the conversion into CIELAB will be used instead. In this case, the instruction Color Conversion selected from the hierarchy is used to convert the CIELAB into the output space of the device. | ✅ |
| CMOCA-6-105 | If the color palette is given in terms of named colorants, and some of the colorants required to produce a particular index are not available in the device, then the CIELAB palette information will be used instead of the named colorant information. | ✅ |
| CMOCA-6-106 | 1.  The output color space of the device (typically CMYK for printers and RGB for displays) | ✅ |
| CMOCA-6-107 | 2.  A named colorant space, which could include spot colors that are available in the printer and/or colorants from the output color space of the device. | ✅ |
| CMOCA-6-108 | In the first case, the Tone Transfer Curve CMR and the Halftone CMR selected from the hierarchy for the output color space are used. In the second case, the number of component named-colorants is noted. The hierarchy is searched for a Tone Transfer Curve and a Halftone that have this same number of components and the CMRs that are found are used. | ✅ |
| CMOCA-6-109 | The Indexed CMR to be used is selected using the normal hierarchical rules. Media-matching rules also apply. When the Indexed CMR is selected, its palettes are searched for the index in question. If the index is not found, IPDS exception processing is performed. No attempt is made to look for the index in any other Indexed CMR. | ✅ |
| CMOCA-6-110 | No host-invoked Indexed CMR is found in the hierarchy. | ✅ |
| CMOCA-6-111 | The required index is not found in the Indexed CMR that was selected. | ✅ |
| CMOCA-6-112 | There are three possible processing modes: audit, instruction, and link. Only certain processing modes are allowed with each specific type of CMR. An exception occurs if the processing mode is not valid for the CMR type. The following table shows which processing modes are valid. In addition, the device should ignore (without causing an exception) certain types of CMRs. That is also shown in the table. | ✅ |
| CMOCA-6-113 | A CMR is generic if the CMRVersion in the CMR Header is “generic”. Only Tone Transfer Curve CMRs and Halftone CMRs have registered generic versions, so if the type is anything else, an exception occurs. | ✅ |
| CMOCA-6-114 | Color Conversion | valid | valid | invalid - error | invalid - error | invalid - error | invalid - error | ✅ |
| CMOCA-6-115 | Tone Transfer Curve | valid | valid | invalid - error | valid - ignored¹ | valid | invalid - error | ✅ |
| CMOCA-6-116 | Halftone | valid - ignored | valid | invalid - error | valid - ignored¹ | valid | invalid - error | ✅ |
| CMOCA-6-117 | Link Color Conversion | invalid - error | invalid - error | valid | invalid - error | invalid - error | invalid - error | ✅ |
| CMOCA-6-118 | Indexed | valid - ignored | valid | invalid - error | invalid - error | invalid - error | invalid - error | ✅ |
| CMOCA-6-119 | 1. Generic Tone Transfer Curves and generic Halftones are ignored because, in order to replace a generic CMR with a specific CMR, you must know the targeted device. This is unknown for an audit CMR. | ✅ |
| CMOCA-6-120 | A CMR is passthrough if the CMRVersion in the CMR Header is “pasthru”. Only Color Conversions may be passthrough so, if the CMR type is anything else, an exception occurs. The mode of a passthrough CC CMR must be audit. The CMR is ignored if the mode is specified as instruction. If the mode is link, an exception is generated since that is not valid for any Color Conversion CMRs. | ✅ |
| CMOCA-6-121 | Every device must supply both audit and instruction default CMRs. | ✅ |
| CMOCA-6-122 | The required default instruction CMRs are used to process colors in the color space of the device. If the device has multiple appearances—for instance, a printer that can function as both a full-color printer and a monochrome printer—then defaults for both identities must be available in the device. | ✅ |
| CMOCA-6-123 | For optimum quality, the device should have default instruction CMRs that are tuned for each type of media that it supports. For instance, a printer that officially supports three different paper types would have three different Color Conversion instruction CMRs available. It is possible that some media have different characteristics on the two sides. In this case, the device would have a default Color Conversion CMR for each side. | ✅ |
| CMOCA-6-124 | In some cases, a printer controller might be ripping pages that are sent to one of several print engines. It might be doing load-balancing among the engines. In this case, the printer controller would have a default Color Conversion CMR for each engine, and possibly for each media type on each engine. | ✅ |
| CMOCA-6-125 | The default Halftone and Tone Transfer Curve CMRs might also differ depending on the media or engine. | ✅ |
| CMOCA-6-126 | An instruction Halftone CMR that takes the device color space as input. | ✅ |
| CMOCA-6-127 | An instruction Tone Transfer Curve CMR. | ✅ |
| CMOCA-6-128 | An instruction Color Conversion CMR from an ICC Profile Connection Space (CIEXYZ or CIELAB) to the device color space. The device must have the ability to convert between CIEXYZ and CIELAB or else it must supply color conversions into the device output space from both. This CMR should have profiles for all rendering intents. | ✅ |
| CMOCA-6-129 | There are no default Indexed CMRs. | ✅ |
| CMOCA-6-130 | The default instruction Color Conversion CMRs can be used when constructing Link Color Conversion CMRs. Therefore, the device manufacturer “publishes” these CMRs. OIDs for these CMRs are created by both the device and the host system wishing to create link CMRs using them. The OIDs are the same in both situations. Thus, the link CMR successfully matches the device's default CMR. | ✅ |
| CMOCA-6-131 | Note that printer default CMRs are used only when no other applicable CMR is invoked in the hierarchy. In some cases it might be desirable to ensure that the device uses its defaults since it might have knowledge that the application does not have. For instance, if the sides of the paper have different characteristics, then the device might have different default Color Conversion CMRs for each side. The device will know which side it is printing on and should select the appropriate color conversion. | ✅ |
| CMOCA-6-132 | To force the printer to choose an applicable default instruction CMR, no applicable CMR of that type should be invoked within the IPDS hierarchy. The printer may not use its default to override an applicable CMR that is invoked at any level of the hierarchy. | ✅ |
| CMOCA-6-133 | Audit CMRs are not dependent on the output device or the media, so only one default in each category is required. | ✅ |
| CMOCA-6-134 | An audit Color Conversion CMR from input RGB to CIEXYZ or CIELAB. (A display may assume that the RGB is its device RGB.) | ✅ |
| CMOCA-6-135 | An audit Color Conversion CMR from input CMYK to CIEXYZ or CIELAB. (A full-color printer may assume that the CMYK is its device CMYK.) | ✅ |
| CMOCA-6-136 | An audit Color Conversion CMR from input grayscale to CIEXYZ or CIELAB. | ✅ |
| CMOCA-6-137 | An audit Color Conversion CMR from YCrCb or YCbCr to CIEXYZ or CIELAB. | ✅ |
| CMOCA-6-138 | Audit Tone Transfer Curve CMRs that apply for varying number of components. | ✅ |
| CMOCA-6-139 | If any other color spaces such as Luv, HSV, HLS, Yxy, or CMY are used within the data stream, the application is responsible for providing a color conversion CMR. The device may generate an exception if an applicable Color Conversion CMR is not supplied. | ✅ |
| CMOCA-6-140 | The following audit CMR defaults have been architected: | ✅ |
| CMOCA-6-141 | RGB:** In an AFP printer, this is SMPTE-C RGB. In a display, this represents its device RGB. | ✅ |
| CMOCA-6-142 | CMYK:** In an AFP full-color printer, this represents the CMYK of the presentation device. In an AFP monochrome printer, this is SWOP CMYK. In an AFP full-color printer working in grayscale mode, the CMYK represents the CMYK of the device when it is in full-color mode. In a display, this is SWOP CMYK. | ✅ |
| CMOCA-6-143 | Grayscale:** The Grayscale default applies when the color space has one component and more than 1 bit per pixel. This includes color spaces that are specified as YCbCr where only the Y component is present. In an AFP full-color printer, C = M = Y = 0; K = 1 – gray_value. In an AFP monochrome printer, the grayscale is the device's grayscale. In a display, R = G = B = gray_value. | ✅ |
| CMOCA-6-144 | YCbCr or YCrCb:** The default YCbCr is based on CCIR Recommendation 601-1 but components are normalized so as to occupy the full 256 levels of an 8-bit encoding. This version of YCbCr is used by TIFF and JFIF as their default. | ✅ |
| CMOCA-6-145 | The default white point for both CIELAB and CIEXYZ is D50. | ✅ |
| CMOCA-6-146 | Halftone:** No architected default. | ✅ |
| CMOCA-6-147 | Tone Transfer Curves:** Identity. | ✅ |
| CMOCA-6-148 | Indexed:** No architected default. | ✅ |
| CMOCA-6-149 | Instruction Halftone CMRs and instruction Tone Transfer Curve CMRs can be generic. Generic CMRs must be replaced by device-specific CMRs. The device is required to have its own device-specific versions of all the generic CMRs that are registered in the Color Management Object Content Architecture (see Appendix B, “Generic CMR Name Registry”). If the device does not recognize a generic CMR name, it NACKs using exception ID X'025E..04'. | ✅ |
| CMOCA-6-150 | An audit Color Conversion CMR can specify the version to be “pasthru”. Passthrough CMRs are defined only for Color Conversion CMRs. Prop4, the color space for a CC CMR, should be specified. When this passthrough CMR is invoked and Prop4 is the same as the color space of the device, then the color values will be passed through without color conversion. If Prop4 is not the same as the device color space, or not specified, then the passthrough CC CMR is ignored. | ✅ |
| CMOCA-6-151 | The CMRVersion in the CMR header indicates whether a CMR is passthrough. Prop4 in the header indicates the color space. Other Prop fields are unspecified. A passthrough Color Conversion is valid only as an audit CMR. It is ignored if its mode is instruction. An error is returned if the mode is link. | ✅ |
| CMOCA-6-152 | A passthrough CC CMR is treated like other audit CC CMRs in terms of selecting an audit CC CMR from the hierarchy. A passthrough CC CMR has no data. There is no device-specific CMR that can be substituted for the passthrough CC CMR. It merely instructs the device to avoid doing any color conversion. | ✅ |
| CMOCA-6-153 | If the target device is a display screen and the MediaBrightness is not specified or is not a valid entry. | ✅ |
| CMOCA-6-154 | If the target device is a printer and one or more of the four media fields in the header are not specified or are invalid. | ✅ |
| CMOCA-6-155 | An applicable CMR is found in the hierarchy and is selected. | ✅ |
| CMOCA-6-156 | No CMR is selected from the hierarchy and a default CMR must be used. | ✅ |
| CMOCA-6-157 | (Refer to the specification for detailed rules on matching media characteristics.) | ✅ |
| CMOCA-6-158 | The AFP architecture supports OCA named colors such as Blue, Red, and Brown that are inherently device-dependent. As such, these named colors should not be used when an exact color is required. AFP architecture has recommended RGB values for each OCA named color. It is recommended that these RGB values be interpreted as SMPTE-C values and mapped to a device's output color space. | ✅ |
| CMOCA-6-159 | It is recommended that OCA Black (X'0008'), presented on a CMYK output device, be rendered as C=M=Y=X'00' and K=X'FF'. | ✅ |
| CMOCA-6-160 | The highlight color number specifies the spot color to use. The range is X'0000'–X'00FF'. | ✅ |
| CMOCA-6-161 | The highlight color number is interpreted as an index into a palette. The range is X'0100'–X'FFFF'. The Indexed CMR describes which colorants are used to render that color. | ✅ |
| CMOCA-6-162 | Some presentation data objects contain internal color management information. ICC profiles can be embedded within TIFF, JFIF, and GIF. | ✅ |
| CMOCA-6-163 | When presentation data objects contain internal color management information, the device will use internal audit-like color management information, if any, when no applicable audit CMR is invoked with the object. The object-level audit CMR has priority over internal color management information. However, the internal color management information has priority over any audit CMRs at the page, home state, or device-default levels. | ✅ |
| CMOCA-6-164 | 1.  Object-level audit CMR | ✅ |
| CMOCA-6-165 | 2.  ICC profile within the object | ✅ |
| CMOCA-6-166 | 3.  Page-level audit CMR | ✅ |
| CMOCA-6-167 | 4.  Homestate-level audit CMR | ✅ |
| CMOCA-6-168 | 5.  Printer default audit CMR | ✅ |
| CMOCA-6-169 | Device-Independent Color Spaces:** CIEBasedABC, CIEBasedA, CIEBasedDEF, CIEBasedDEFG | ✅ |
| CMOCA-6-170 | Device-Dependent Color Spaces:** DeviceGray, DeviceRGB, DeviceCMYK | ✅ |
| CMOCA-6-171 | Special Color Spaces:** Pattern, Indexed, Separation, DeviceN | ✅ |
| CMOCA-6-172 | If a CRD is specified within the PostScript data stream, it will be used instead of the default CRD. | ✅ |
| CMOCA-6-173 | If an instruction Color Conversion CMR is associated directly with the EPS/PDF object, it will override both the default CRD and any CRD specified within the EPS/PDF object. | ✅ |
| CMOCA-6-174 | Link Color Conversion CMRs should be used when processing EPS/PDF if a device reports support for Link CC CMRs and it reports that it can reliably apply CMRs to EPS/PDF. | ✅ |
| CMOCA-6-175 | If a halftone is specified within the EPS/PDF object, it will be used instead of the default EPS/PDF halftone. | ✅ |
| CMOCA-6-176 | If an instruction Halftone CMR is associated directly with the EPS/PDF object, it will override both the default halftone and any halftone specified within the EPS/PDF object. | ✅ |
| CMOCA-6-177 | If a Transfer Function is specified within the EPS/PDF object, it will be used. | ✅ |
| CMOCA-6-178 | If an instruction Tone Transfer Curve CMR is associated directly with the EPS/PDF object, it will override any Transfer Function specified within the EPS/PDF object. | ✅ |
| CMOCA-6-179 | Some devices cannot completely, reliably ensure that a selected CMR is actually applied. This is because some EPS/PDF objects can be created in such a way that it is not possible to override the parameters. | ✅ |
| CMOCA-6-180 | L** has a value between 0.0 and 100.0 | ✅ |
| CMOCA-6-181 | a** has a value between –128.0 and 127.0 | ✅ |
| CMOCA-6-182 | b** has a value between –128.0 and 127.0 | ✅ |
| CMOCA-6-183 | The encoding for L consistently maps the range [0.0, 100.0] to [X'0000', X'FFFF'] or to [X'00', X'FF'] depending on the number of bytes in the representation. | ✅ |
| CMOCA-6-184 | Different architectures convert the range for a and b into 1-byte or 2-byte values differently. Care must be taken when using values from the different architectures to ensure that they are converted to a common encoding. | ✅ |
| CMOCA-6-185 | It is preferable for the host to suppress the downloading of CMRs that are not applicable to the device, but this is not required. | ✅ |
| CMOCA-6-186 | Certain instruction CMRs can be generic. Generic CMRs are defined for only two CMR types: Halftone CMRs and Tone Transfer Curve CMRs. The generic CMR must always be replaced by a device-specific CMR by either the server or the device. | ✅ |
| CMOCA-6-187 | In some cases, a device provides only partial support for certain types of CMRs. The device needs to declare support in the IPDS STM reply to enable the host to transmit the CMR to the device. | ✅ |
| CMOCA-A-001 | This table defines the CMR tags in the base level of the Color Management Object Content Architecture. Support for some CMR types is optional, see Appendix C, “Compliance With Color Management Object Content Architecture”. When a presentation device supports a given CMR type, it must support the tags used by that CMR type, as defined in this table. | ✅ |
| CMOCA-A-002 | X'0004' | Comment | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed | ✅ |
| CMOCA-A-003 | X'0008' | Date and Time Stamp | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed | ✅ |
| CMOCA-A-004 | X'0011' | Number of Components | Halftone, Tone Transfer Curve | ✅ |
| CMOCA-A-005 | X'1011' | Halftone Subset | Halftone | ✅ |
| CMOCA-A-006 | X'1021' | Array Width | Halftone | ✅ |
| CMOCA-A-007 | X'1025' | Array Height | Halftone | ✅ |
| CMOCA-A-008 | X'1030' | Max Image Value | Halftone | ✅ |
| CMOCA-A-009 | X'1035' | Number of Device Levels | Halftone | ✅ |
| CMOCA-A-010 | X'1040' | Offset Tiling | Halftone | ✅ |
| CMOCA-A-011 | X'1045' | Bilevel Point-Operation Screen Data | Halftone | ✅ |
| CMOCA-A-012 | X'1050' | Multilevel Point-Operation Screen Data | Halftone | ✅ |
| CMOCA-A-013 | X'1055' | Error Diffusion Filter | Halftone | ✅ |
| CMOCA-A-014 | X'1060' | Location of Current Pixel | Halftone | ✅ |
| CMOCA-A-015 | X'1065' | Raster Direction | Halftone | ✅ |
| CMOCA-A-016 | X'1070' | Boundary Condition | Halftone | ✅ |
| CMOCA-A-017 | X'1075' | Threshold Value | Halftone | ✅ |
| CMOCA-A-018 | X'1080' | Quantization Boundary Table | Halftone | ✅ |
| CMOCA-A-019 | X'2004' | Tone Transfer Curve Subset | Tone Transfer Curve | ✅ |
| CMOCA-A-020 | X'2011' | Tone Transfer Curve Length | Tone Transfer Curve | ✅ |
| CMOCA-A-021 | X'2015' | Tone Transfer Curve Data | Tone Transfer Curve | ✅ |
| CMOCA-A-022 | X'2020' | Inverse Tone Transfer Curve Data | Tone Transfer Curve | ✅ |
| CMOCA-A-023 | X'3011' | ICC Profile Subset | Color Conversion | ✅ |
| CMOCA-A-024 | X'3015' | ICC Profile Data | Color Conversion | ✅ |
| CMOCA-A-025 | X'3025' | ICC Profile Filename | Color Conversion | ✅ |
| CMOCA-A-026 | X'4011' | Link Color Conversion Subset | Link Color Conversion | ✅ |
| CMOCA-A-027 | X'4015' | Link Audit CMR OID | Link Color Conversion | ✅ |
| CMOCA-A-028 | X'4020' | Link Instruction CMR OID | Link Color Conversion | ✅ |
| CMOCA-A-029 | X'4025' | Link Audit CMR Name | Link Color Conversion | ✅ |
| CMOCA-A-030 | X'4030' | Link Instruction CMR Name | Link Color Conversion | ✅ |
| CMOCA-A-031 | X'4035' | Default Rendering Intent | Link Color Conversion | ✅ |
| CMOCA-A-032 | X'4040' | Link LUT Perceptual | Link Color Conversion | ✅ |
| CMOCA-A-033 | X'4045' | Link LUT Media-Relative Colorimetric | Link Color Conversion | ✅ |
| CMOCA-A-034 | X'4050' | Link LUT Saturation | Link Color Conversion | ✅ |
| CMOCA-A-035 | X'4055' | Link LUT ICC-Absolute Colorimetric | Link Color Conversion | ✅ |
| CMOCA-A-036 | X'4090' | Link CMRE Identifier | Link Color Conversion | ✅ |
| CMOCA-A-037 | X'5011' | Indexed Subset | Indexed | ✅ |
| CMOCA-A-038 | X'5015' | Number of Named Colorants | Indexed | ✅ |
| CMOCA-A-039 | X'5020' | Color Palette Gray | Indexed | ✅ |
| CMOCA-A-040 | X'5025' | Color Palette CMYK | Indexed | ✅ |
| CMOCA-A-041 | X'5030' | Color Palette RGB | Indexed | ✅ |
| CMOCA-A-042 | X'5035' | Color Palette CIELAB | Indexed | ✅ |
| CMOCA-A-043 | X'5040' | Color Palette Named Colorants | Indexed | ✅ |
| CMOCA-A-044 | X'5045' | Colorant Identification List | Indexed | ✅ |
| CMOCA-A-045 | X'FFFF' | End Data | Halftone, Tone Transfer Curve, Color Conversion, Link Color Conversion, Indexed | ✅ |
| CMOCA-A-046 | 1. For an Indexed CMR, at least one of the Color Palette tags must be present. | ✅ |
| CMOCA-A-047 | 2. Tags X'F000'–X'FFFE' are private tags. | ✅ |
| CMOCA-B-001 | Generic CMRs are allowed for instruction Halftone CMRs and instruction Tone Transfer Curve CMRs. This appendix defines the currently registered generic CMR names. All presentation devices that support downloaded Halftone and Tone Transfer Curve CMRs must support all names defined in this registry. The device must substitute a device-specific CMR that fits as best it can. The device will not always have an accurate match but should not NACK if it recognizes the name. If the device does not recognize the generic CMR name, it uses exception ID X'025E..04' to indicate that this name is not supported. | ✅ |
| CMOCA-B-002 | No device or media information may be included in generic names. Only the fields describing the generic property are filled in. Other fields are not specified. Note that, for improved readability, spaces are left between fields of the CMR name in the examples below. | ✅ |
| CMOCA-B-003 | The CMR names specified in Figure 12 and Figure 13 are the only valid generic CMRs. | ✅ |
| CMOCA-B-004 | The registered generic Halftone CMRs are shown in Figure 12. Halftone Property 3 (Line Screen Frequency) or Property 2 (Halftone Type) are used to describe the generic quality of the halftone. | ✅ |
| CMOCA-B-005 | Figure 12. Generic Halftone CMRs** | ✅ |
| CMOCA-B-006 | The first eleven generic Halftone CMRs are used for clustered-dot halftones and indicate the line screen frequency of the halftone. The next three generic Halftone CMRs are used to specify a halftone of type stochastic, dispersed, or error diffusion. The last six are specific error diffusion halftones. | ✅ |
| CMOCA-B-007 | Accutone | ✅ |
| CMOCA-B-008 | Highlight Midtone | ✅ |
| CMOCA-B-009 | Standard | ✅ |
| CMOCA-B-010 | Figure 13. Generic Tone Transfer Curve CMRs** | ✅ |
| CMOCA-B-011 | A Tone Transfer Curve (color calibration) alters the darkness of image data, accounting for the dot gain characteristics of the printer. A TTC might be used to allow one device to emulate the output of another device. | ✅ |
| CMOCA-B-012 | The actual appearance depends on a combination of the printer model, the halftone screen, and the appearance selected (the TTC). | ✅ |
| CMOCA-B-013 | Standard:** Exactly undoing the dot gain of the printer. | ✅ |
| CMOCA-B-014 | Dark, Accutone, Highlight Midtone, Standard:** Have dot gains of 33, 22, 14, and 0 percent respectively, measured at the 50% dot. Accutone approximates linear L* tone characteristics. | ✅ |
| CMOCA-B-015 | The tone transfer curve (TTC) and halftone are interrelated. The TTC depends on the halftone that is selected. So, when mapping generic to device-specific, the halftone is mapped first and then the TTC mapping is done, taking into consideration the halftone that is being used. | ✅ |
| CMOCA-B-016 | A device-specific TTC would specify both the Look-and-Feel and the Halftone Characterization. | ✅ |
| CMOCA-B-017 | If the printer has device-specific halftones at 75, 104, and 141 lpi, it would need to have 12 device-specific TTCs available to cover all combinations of the 4 generic TTC appearances and 3 halftones. | ✅ |
| CMOCA-C-001 | In order to claim that the baseline Color Management Object Content Architecture is supported, a device is required to support certain CMRs as indicated in the following table. | ✅ |
| CMOCA-C-002 | Halftone | No | ✅ |
| CMOCA-C-003 | Tone Transfer Curve | No | ✅ |
| CMOCA-C-004 | Color Conversion | Yes | ✅ |
| CMOCA-C-005 | Link Color Conversion | No | ✅ |
| CMOCA-C-006 | Indexed | No | ✅ |
| CMOCA-C-007 | In order to claim that the baseline Color Management Object Content Architecture is supported, a device is required to support generic CMRs and device defaults as specified in the architecture reference. | ✅ |
| CMOCA-C-008 | The AFP Consortium or consortium member companies might have patents or pending patent applications covering subject matter described in this document. The furnishing of this document does not give you any license to these patents. | ✅ |
| CMOCA-C-009 | AFP Consortium PROVIDES THIS PUBLICATION “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.** | ✅ |
| CMOCA-C-010 | This publication could include technical inaccuracies or typographical errors. Changes are periodically made to the information herein; these changes will be incorporated in new editions of the publication. | ✅ |
| CMOCA-C-011 | These terms are trademarks or registered trademarks of Adobe Systems Incorporated: **Acrobat, Adobe, Photoshop, PostScript**. | ✅ |
| CMOCA-C-012 | These terms are trademarks of the AFP Consortium: **AFPC, AFP Consortium**. | ✅ |
| CMOCA-C-013 | IBM** and **InfoPrint** are trademarks of International Business Machines Corporation. **PANTONE** is a registered trademark of Pantone LLC. **Ricoh** is a registered trademark of Ricoh Co., Ltd. | ✅ |
| CMOCA-C-014 | <a name="glossary"></a> | ✅ |
| CMOCA-C-015 | audit CMR:** A color management resource that reflects processing that has been done on an object. | ✅ |
| CMOCA-C-016 | big endian:** A format for storage or transmission of binary data in which the most significant bit (or byte) is placed first. | ✅ |
| CMOCA-C-017 | CIELAB color space:** Internationally accepted color space model used as a standard to define color within the graphic arts industry. | ✅ |
| CMOCA-C-018 | color conversion:** The process of converting colors from one color space to another. | ✅ |
| CMOCA-C-019 | instruction CMR:** A color management resource that identifies processing that is to be done to an object. | ✅ |
| CMOCA-C-020 | PCS (Profile Connection Space):** The reference color space defined by ICC. | ✅ |
| CMOCA-C-021 | (Refer to the full PDF for the complete glossary of terms.)* | ✅ |
| CMOCA-C-022 | audit CMR:** 7, 99, 103, 113 | ✅ |
| CMOCA-C-023 | Color Conversion CMR:** 7, 10, 12, 28, 102, 108, 114 | ✅ |
| CMOCA-C-024 | Halftone CMR:** 7, 10, 12, 26, 98, 107 | ✅ |
| CMOCA-C-025 | Indexed CMR:** 7, 11, 12, 35, 107 | ✅ |
| CMOCA-C-026 | Link Color Conversion CMR:** 7, 10, 12, 33, 98 | ✅ |
| CMOCA-C-027 | Tone Transfer Curve CMR:** 7, 10, 12, 27, 107 | ✅ |
| CMOCA-C-028 | ICC profile:** 28, 68, 69, 71, 99 | ✅ |
| CMOCA-C-029 | (Refer to the full PDF for the complete index and page references.)* | ✅ |
