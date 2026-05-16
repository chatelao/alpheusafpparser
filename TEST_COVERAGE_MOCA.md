# Granular Test Coverage - MOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| MOCA-1-001 | This chapter provides a brief overview of Presentation Architecture. | ✅ |
| MOCA-1-002 | Figure 1 shows today's presentation environment. | ✅ |
| MOCA-1-003 | Figure 1. Presentation Environment.** The environment is a coordinated set of services architected to meet the presentation needs of today's applications. | ✅ |
| MOCA-1-004 | Document Creation Services** | ✅ |
| MOCA-1-005 | - import/export | ✅ |
| MOCA-1-006 | - edit/revise | ✅ |
| MOCA-1-007 | - format | ✅ |
| MOCA-1-008 | - transform | ✅ |
| MOCA-1-009 | Document Archiving Services** | ✅ |
| MOCA-1-010 | - retrieve | ✅ |
| MOCA-1-011 | - search | ✅ |
| MOCA-1-012 | - extract | ✅ |
| MOCA-1-013 | Document Viewing Services** | ✅ |
| MOCA-1-014 | - browse | ✅ |
| MOCA-1-015 | - navigate | ✅ |
| MOCA-1-016 | - search | ✅ |
| MOCA-1-017 | - annotate | ✅ |
| MOCA-1-018 | Document Printing Services** | ✅ |
| MOCA-1-019 | - submit | ✅ |
| MOCA-1-020 | - distribute | ✅ |
| MOCA-1-021 | - manage | ✅ |
| MOCA-1-022 | - finish | ✅ |
| MOCA-1-023 | The ability to create, store, retrieve, view, and print data in presentation formats friendly to people is a key requirement in almost every application of computers and information processing. This requirement is becoming increasingly difficult to meet because of the number of applications, servers, and devices that must interoperate to satisfy today's presentation needs. | ✅ |
| MOCA-1-024 | The solution is a presentation architecture base that is both robust and open ended, and easily adapted to accommodate the growing needs of the open system environment. AFP architectures provide that base by defining interchange formats for data streams and objects that enable applications, services, and devices to communicate with one another to perform presentation functions. These presentation functions might be part of an integrated system solution or they might be totally separated from one another in time and space. AFP architectures provide structures that support object-oriented models and client/server environments. | ✅ |
| MOCA-1-025 | AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ✅ |
| MOCA-1-026 | AFP architectures provide the means for representing documents in a data format that is independent of the methods used to capture or create them. Documents can contain combinations of text, image, graphics, and bar code objects in presentation-system-independent and resolution-independent formats. Documents can contain fonts, overlays, and other resource objects required at presentation time to present the data properly. Finally, documents can contain resource objects, such as a document index and tagging elements supporting the search and navigation of document data, for a variety of application purposes. | ✅ |
| MOCA-1-027 | The presentation architecture components are divided into two major categories: data streams and objects. | ✅ |
| MOCA-1-028 | Mixed Object Document Content Architecture (MO:DCA)** | ✅ |
| MOCA-1-029 | Intelligent Printer Data Stream (IPDS) Architecture** | ✅ |
| MOCA-1-030 | The **MO:DCA architecture** defines the data stream used by applications to describe documents and object envelopes for interchange with other applications and application services. The MO:DCA format supports storing and retrieving documents in an archive, viewing, annotation, and printing of documents or parts of documents in local or distributed systems environments. Presentation fidelity is accommodated by including resource objects in the documents that reference them. | ✅ |
| MOCA-1-031 | The **IPDS architecture** defines the data stream used by print server programs and device drivers to manage all-points-addressable page printing on a full spectrum of devices from low-end workstation and local area network-attached (LAN-attached) printers to high-speed, high-volume page printers for production jobs, shared printing, and mailroom applications. The same object content architectures carried in a MO:DCA data stream can be carried in an IPDS data stream to be interpreted and presented by microcode executing in printer hardware. The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. The IPDS architecture also provides interfaces for document finishing operations provided by pre-processing and post-processing devices attached to IPDS printers. | ✅ |
| MOCA-1-032 | Figure 2. Presentation Model.** This diagram shows the major components in a presentation system and their use of data stream and object architectures. | ✅ |
| MOCA-1-033 | Application** | ✅ |
| MOCA-1-034 | Print Services** | ✅ |
| MOCA-1-035 | Viewing Services** | ✅ |
| MOCA-1-036 | Archive Services** | ✅ |
| MOCA-1-037 | Device** | ✅ |
| MOCA-1-038 | Library** | ✅ |
| MOCA-1-039 | Printer** | ✅ |
| MOCA-1-040 | Post Processor** | ✅ |
| MOCA-1-041 | Overlays | ✅ |
| MOCA-1-042 | Page Segments | ✅ |
| MOCA-1-043 | Form Definition | ✅ |
| MOCA-1-044 | Color Management Resources | ✅ |
| MOCA-1-045 | Color Table | ✅ |
| MOCA-1-046 | Document Index | ✅ |
| MOCA-1-047 | Metadata | ✅ |
| MOCA-1-048 | Graphics | ✅ |
| MOCA-1-049 | Bar Codes | ✅ |
| MOCA-1-050 | Object Containers | ✅ |
| MOCA-1-051 | Other Objects | ✅ |
| MOCA-1-052 | Documents can be made up of different kinds of data, such as text, graphics, image, and bar code. Object content architectures describe the structure and content of each type of data format that can exist in a document or appear in a data stream. Objects can be either data objects or resource objects. | ✅ |
| MOCA-1-053 | A **data object** contains a single type of presentation data, that is, presentation text, vector graphics, raster image, or bar codes, and all of the controls required to present the data. | ✅ |
| MOCA-1-054 | A **resource object** is a collection of presentation instructions and data. These objects are referenced by name in the presentation data stream and can be stored in system libraries so that multiple applications and the print server can use them. | ✅ |
| MOCA-1-055 | All object content architectures (OCAs) are totally self-describing and independently defined. When multiple objects are composed on a page, they exist as peer objects that can be individually positioned and manipulated to meet the needs of the presentation application. | ✅ |
| MOCA-1-056 | Presentation Text Object Content Architecture (PTOCA)**: A data architecture for describing text objects that have been formatted for all-points-addressable presentations. Specifications of fonts, text color, and other visual attributes are included in the architecture definition. | ✅ |
| MOCA-1-057 | Image Object Content Architecture (IOCA)**: A data architecture for describing resolution-independent image objects captured from a number of different sources. Specifications of recording formats, data compression, color, and grayscale encoding are included in the architecture definition. | ✅ |
| MOCA-1-058 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA)**: A version of GOCA that is used in Advanced Function Presentation (AFP) environments. GOCA is a data architecture for describing vector graphics picture objects and line art drawings for a variety of applications. Specification of drawing primitives, such as lines, arcs, areas, and their visual attributes, are included in the architecture definition. | ✅ |
| MOCA-1-059 | Bar Code Object Content Architecture (BCOCA)**: A data architecture for describing bar code objects, using a number of different symbologies. Specification of the data to be encoded and the symbology attributes to be used are included in the architecture definition. | ✅ |
| MOCA-1-060 | Font Object Content Architecture (FOCA)**: A resource architecture for describing the structure and content of fonts referenced by presentation data objects in the document. | ✅ |
| MOCA-1-061 | Color Management Object Content Architecture (CMOCA)**: A resource architecture used to carry the color management information required to render presentation data. | ✅ |
| MOCA-1-062 | Metadata Object Content Architecture (MOCA)**: A resource architecture used to carry metadata in an AFP environment. | ✅ |
| MOCA-1-063 | The MO:DCA and IPDS architectures also support data objects that are not defined by object content architectures. Examples of such objects are Tag Image File Format (TIFF), Encapsulated PostScript® (EPS), and Portable Document Format (PDF). Such objects can be carried in a MO:DCA envelope called an object container, or they can be referenced without being enveloped in MO:DCA structures. | ✅ |
| MOCA-1-064 | In addition to object content architectures, the MO:DCA architecture defines envelope architectures for objects of common value in the presentation environment. Examples of these are Form Definition resource objects for managing the production of pages on the physical media, overlay resource objects that accommodate electronic storage of forms data, and index resource objects that support indexing and tagging of pages in a document. | ✅ |
| MOCA-1-065 | Page** | ✅ |
| MOCA-1-066 | - Presentation Text Object(s) | ✅ |
| MOCA-1-067 | - Graphics Object | ✅ |
| MOCA-1-068 | - Image Object | ✅ |
| MOCA-1-069 | - Letterhead can be an overlay resource containing text, image, and graphics objects | ✅ |
| MOCA-1-070 | - Object areas can overlap | ✅ |
| MOCA-2-001 | The Metadata Object Content Architecture (MOCA) defines an AFP object that carries metadata in an AFP environment. This object is called a Metadata Object (MO). The long range objective of MOCA is to improve the utility, manageability, and archive integrity of AFP documents. | ✅ |
| MOCA-2-002 | The initial MOCA specification is defined to support the requirements of AFP Archive. This places limits on the number of MO types and the level of penetration into the MO:DCA component hierarchy where metadata might be associated. Only descriptive metadata, with no presentation or operational semantic, is defined. | ✅ |
| MOCA-2-003 | MOCA might be extended, in the future, to include additional MO types, associations, and methods, including the possibility of operational metadata that reaches the presentation device. | ✅ |
| MOCA-3-001 | Metadata is descriptive information that is associated with and augments other data. Some examples of metadata for a print file are the Date, Author, Description, or Copyright information. The primary purpose of the Metadata Object Content Architecture is to provide a framework for carrying and referencing metadata in MO:DCA environments. There are many predefined metadata specifications. MOCA will accommodate selected industry defined metadata formats while also providing a placeholder for AFP specific metadata, to be defined by the AFPC to address targeted needs should they arise in the future. | ✅ |
| MOCA-3-002 | In the MO:DCA environment, MOs will be carried within an object container (BOC/EOC). | ✅ |
| MOCA-3-003 | In the IPDS environment, MOs will be carried using the Metadata Command Set, which includes the Write Metadata Control (WMC) and Write Metadata (WM) commands. | ✅ |
| MOCA-3-004 | Metadata, in general, has no intended presentation semantics. | ✅ |
| MOCA-4-001 | An MO consists of a header followed by MO data. | ❓ |
| MOCA-4-002 | This is the syntax of an MO. | ❓ |
| MOCA-4-003 | Data contained in fixed-length fields that are encoded as UTF-16BE is left-aligned. If the number of bytes used by the characters in these fields is smaller than the field length, the remaining bytes will be padded with “@” (X'0040'). | ❓ |
| MOCA-4-004 | 4 | 0–3 | UBIN | MOLength | X'00000032' - X'FFFFFFFF' | MO length, including this MOLength field | M | ❓ |
| MOCA-4-005 | MO header starts here** | ❓ |
| MOCA-4-006 | 2 | 4–5 | UBIN | HeaderLength | X'002E' - end of header | MO header length, including this HeaderLength field | M | ❓ |
| MOCA-4-007 | 6 | 6–11 | UTF16 | MOType | DES (X'0044 0045 0053') | Descriptive | M | ❓ |
| MOCA-4-008 | 8 | 12–19 | UTF16 | MOFormat | AFPT (X'0041 0046 0050 0054') | AFP Tagging | M | ❓ |
| MOCA-4-009 | XMP (X'0058 004D 0050 0040') | Extensible Metadata Platform (XMP) | ❓ |
| MOCA-4-010 | 20 | 20–39 | UTF16 | MOCompression | NONE (X'004E 004F 004E 0045 0040 0040 0040 0040 0040 0040') | Uncompressed | M | ❓ |
| MOCA-4-011 | GZIP (X'0047 005A 0049 0050 0040 0040 0040 0040 0040 0040') | “Gzip” text compression | ❓ |
| MOCA-4-012 | EXI (X'0045 0058 0049 0040 0040 0040 0040 0040 0040 0040') | Efficient XML Interchange (EXI) compression | ❓ |
| MOCA-4-013 | 8 | 40–47 | | | X'0000000000000000' | Reserved - should be set to zero | M | ❓ |
| MOCA-4-014 | 2 | 48–49 | UBIN | MONameLength | X'0000' - X'00FA', even values only | Length, in bytes, of the MOName field that follows | M | ❓ |
| MOCA-4-015 | 0–250 | 50–end of name | UTF16 | MOName | Any valid UTF-16BE characters (thus an even number of bytes) | A human-readable MO name in UTF-16BE | O | ❓ |
| MOCA-4-016 | End of name–end of header | | UNDF | | | Reserved for future use; receivers should accept but ignore; generators should not specify | O | ❓ |
| MOCA-4-017 | MO header ends here** | ❓ |
| MOCA-4-018 | MOData | | Any MO Data | O | ❓ |
| MOCA-4-019 | M/O: Mandatory or Optional field* | ❓ |
| MOCA-4-020 | MOLength**: The length of the complete MO, including the MOLength parameter. MOLength, in bytes, may be 50 (X'00000032') to X'FFFFFFFF'. If an invalid value is found in this field, the optional exception is EC-0100. | ❓ |
| MOCA-4-021 | HeaderLength**: The length of the MO header, including the HeaderLength parameter. HeaderLength, in bytes, may be any value greater than or equal to 46 (X'002E'). If an invalid value is found in this field, the optional exception is EC-0200. | ❓ |
| MOCA-4-022 | MOType**: One MOType is defined in the Metadata Object Content Architecture. The defined MOType is DES. See “MOType”. If an invalid or unsupported value is found in this field, the optional exception is EC-0220. | ❓ |
| MOCA-4-023 | MOFormat**: MOFormat is defined in the Metadata Object Content Architecture to indicate the format of MO data. See “MOFormat”. If an invalid or unsupported value is found in this field, the optional exception is EC-0230. | ❓ |
| MOCA-4-024 | MOCompression**: MOCompression is defined in the Metadata Object Content Architecture to indicate the type of compression applied to MO data. See “MOCompression”. If an invalid or unsupported value is found in this field, the optional exception is EC-0240. | ❓ |
| MOCA-4-025 | MONameLength**: The length of the MOName field. MONameLength, in bytes, may be any even value from 0 (X'0000') to 250 (X'00FA'). If an invalid value is found in this field, the optional exception is EC-0250. | ❓ |
| MOCA-4-026 | MOName**: A user-defined string containing an optional human-readable MO name. This field can contain up to 250 bytes; therefore, if the UTF-16BE string contains no surrogates, the MO name can contain up to 125 characters. If the environment containing the MO has a method of referencing an MO by name, this field is to be used as the name. If an invalid value is found in this field, the optional exception is EC-0210. | ❓ |
| MOCA-4-027 | MOData**: MO data. The format of the metadata is determined by the value of the MOFormat parameter. If an invalid value is found in this field, the optional exception is EC-0300. | ❓ |
| MOCA-4-028 | Both recognition and reporting of exception conditions is optional. Exception conditions have a format of EC-xxxx. | ❓ |
| MOCA-4-029 | The exception conditions are as follows: | ❓ |
| MOCA-4-030 | EC-0100 Invalid Length Value**: The specified MOLength is invalid. | ❓ |
| MOCA-4-031 | EC-0200 Invalid Field Value**: The specified HeaderLength is invalid. | ❓ |
| MOCA-4-032 | EC-0210 Invalid Field Value**: The specified MOName is not valid UTF-16BE. | ❓ |
| MOCA-4-033 | EC-0220 Invalid or Unsupported Field Value**: The specified MOType is invalid or unsupported. | ❓ |
| MOCA-4-034 | EC-0230 Invalid or Unsupported Field Value**: The specified MOFormat is invalid or unsupported. | ❓ |
| MOCA-4-035 | EC-0240 Invalid or Unsupported Field Value**: The specified MOCompression is invalid or unsupported. | ❓ |
| MOCA-4-036 | EC-0250 Invalid Field Value**: The specified MONameLength is invalid. | ❓ |
| MOCA-4-037 | EC-0300 Invalid MOData**: The specified MOData does not meet the specification associated with the indicated MOFormat. | ❓ |
| MOCA-4-038 | In the IPDS environment, MOCA exception conditions are mapped to IPDS exceptions and reported. To map a MOCA exception condition to an IPDS exception, the rule is simply to add X'06' on the front of the four digits of the MOCA exception condition. For example, MOCA exception condition EC-0220 becomes IPDS exception X'0602..20'. | ❓ |
| MOCA-5-001 | Attribute fields for MO type, format, and compression are carried in the MO header. Each of these fields is described below. | ❓ |
| MOCA-5-002 | DES** | ❓ |
| MOCA-5-003 | Each value for MOType is described in more detail below. | ❓ |
| MOCA-5-004 | MOType = DES (X'004400450053')** | ❓ |
| MOCA-5-005 | MOType DES refers to descriptive metadata used to label, tag, or otherwise describe elements of a print file. Common descriptive metadata are attributes such as Title, Date, and Author. Descriptive metadata is distinct from the primary content of a document and does not affect the architected rendering of data. | ❓ |
| MOCA-5-006 | AFP Tagging** | ❓ |
| MOCA-5-007 | XMP** | ❓ |
| MOCA-5-008 | Each value for MOFormat is described in more detail below. | ❓ |
| MOCA-5-009 | MOFormat = AFPT (X'0041004600500054')** | ❓ |
| MOCA-5-010 | MOFormat AFPT refers to a metadata object using the AFP Tagging definition, which defines a schema in XML format for both identifying some semantics and tagging the data in the AFP that correspond to those semantics. For example, AFP Tagging metadata can be used to state that some set of bytes in the AFP correspond to a figure, or a paragraph, or a hyperlink; this type of information could be used in a screen reader, to enable universal accessibility. | ❓ |
| MOCA-5-011 | See the *Metadata Guide for AFP* for the definition of the syntax of AFP Tagging metadata. | ❓ |
| MOCA-5-012 | MOFormat = XMP (X'0058004D00500040')** | ❓ |
| MOCA-5-013 | MOFormat XMP refers to a metadata object using the Extensible Metadata Platform (XMP) data model, serialization, and core properties. | ❓ |
| MOCA-5-014 | See the *XMP Specification dated September 2005* for a complete specification of XMP . | ❓ |
| MOCA-5-015 | NONE** | ❓ |
| MOCA-5-016 | GZIP** | ❓ |
| MOCA-5-017 | EXI** | ❓ |
| MOCA-5-018 | Each value for MOCompression is described in more detail below. | ❓ |
| MOCA-5-019 | MOCompression = NONE (X'004E004F004E0045004000400040004000400040')** | ❓ |
| MOCA-5-020 | The MO data is uncompressed. | ❓ |
| MOCA-5-021 | MOCompression = GZIP (X'0047005A00490050004000400040004000400040')** | ❓ |
| MOCA-5-022 | The MO data is compressed as text using GZIP. | ❓ |
| MOCA-5-023 | See *RFC 1952 - GZIP file format specification version 4.3*. | ❓ |
| MOCA-5-024 | MOCompression = EXI (X'0045005800490040004000400040004000400040')** | ❓ |
| MOCA-5-025 | The MO data is compressed as XML using Efficient XML Interchange. | ❓ |
| MOCA-5-026 | See *Efficient XML Interchange (EXI) Format 1.0*. | ❓ |
| MOCA-6-001 | This chapter describes the MOCA subsets that are supported in the MOCA architecture. | ❓ |
| MOCA-6-002 | MOCA subsets are used to identify a specific level of MOCA functionality. Each new (higher level) subset must incorporate the complete functionality of the previous (lower level) subset. The naming of MOCA subsets is defined as follows. | ❓ |
| MOCA-6-003 | MS** = Metadata Subset | ❓ |
| MOCA-6-004 | x** = level, starting with 1 | ❓ |
| MOCA-6-005 | The MS1 Subset is the level of MOCA compliance required to support the functionality contained in the first edition of the MOCA Reference. | ❓ |
| MOCA-6-006 | EC-0100 | ❓ |
| MOCA-6-007 | EC-0200 | ❓ |
| MOCA-6-008 | EC-0210 | ❓ |
| MOCA-6-009 | EC-0220 | ❓ |
| MOCA-6-010 | EC-0230 | ❓ |
| MOCA-6-011 | EC-0240 | ❓ |
| MOCA-6-012 | EC-0250 | ❓ |
| MOCA-6-013 | EC-0300 | ❓ |
| MOCA-6-014 | The AFP Consortium or consortium member companies might have patents or pending patent applications covering subject matter described in this document. The furnishing of this document does not give you any license to these patents. | ❓ |
| MOCA-6-015 | The following statement does not apply to the United Kingdom or any other country where such provisions are inconsistent with local law: **AFP Consortium PROVIDES THIS PUBLICATION “AS IS” WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.** Some states do not allow disclaimer of express or implied warranties in certain transactions, therefore, this statement might not apply to you. | ❓ |
| MOCA-6-016 | This publication could include technical inaccuracies or typographical errors. Changes are periodically made to the information herein; these changes will be incorporated in new editions of the publication. The AFP Consortium might make improvements and/or changes in the architecture described in this publication at any time without notice. | ❓ |
| MOCA-6-017 | Any references in this publication to Web sites are provided for convenience only and do not in any manner serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for this architecture and use of those Web sites is at your own risk. | ❓ |
| MOCA-6-018 | The AFP Consortium may use or distribute any information you supply in any way it believes appropriate without incurring any obligation to you. | ❓ |
| MOCA-6-019 | This information contains examples of data and reports used in daily business operations. To illustrate them in a complete manner, some examples include the names of individuals, companies, brands, or products. These names are fictitious and any similarity to the names and addresses used by an actual business enterprise is entirely coincidental. | ❓ |
| MOCA-6-020 | PostScript and XMP are either registered trademarks or trademarks of Adobe Systems Incorporated in the United States and/or other countries. | ❓ |
| MOCA-6-021 | AFPC and AFP Consortium are trademarks of the AFP Consortium. | ❓ |
| MOCA-6-022 | IBM is a trademark of the International Business Machines Corporation in the United States, other countries, or both. | ❓ |
| MOCA-6-023 | Advanced Function Presentation | ❓ |
| MOCA-6-024 | AFP Color Consortium | ❓ |
| MOCA-6-025 | AFP Color Management Architecture | ❓ |
| MOCA-6-026 | Bar Code Object Content Architecture | ❓ |
| MOCA-6-027 | Color Management Object Content Architecture | ❓ |
| MOCA-6-028 | InfoPrint | ❓ |
| MOCA-6-029 | Intelligent Printer Data Stream | ❓ |
| MOCA-6-030 | Mixed Object Document Content Architecture | ❓ |
| MOCA-6-031 | MO:DCA | ❓ |
| MOCA-6-032 | Other company, product, or service names might be trademarks or service marks of others. | ❓ |
