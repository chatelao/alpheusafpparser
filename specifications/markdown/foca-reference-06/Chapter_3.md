# Chapter 3. Referencing Fonts

When you create a document, you specify the font or fonts to be used whenever the document is presented. The application programs that process the document use the font references in the document to locate the required font resources for document formatting and presentation. System and presentation device libraries provide font resources, which have assigned names that must be associated with the font references contained in a document. The association of font references to font resources is not necessarily a one-to-one association based only on the name. The principal method of referencing fonts is by listing the applicable font-description parameters.

To enable text processing systems to locate and use the appropriate font resources, the font references must be made in a clear, consistent manner across all the systems, applications, and data streams that use the font resources. Then, if a document containing the font references is sent to other locations, the processors at those receiving locations can use the distinguishing characteristics or attributes to find the required font resource (perhaps filed under a different name), or to select an appropriate alternative. The following sections of this chapter describe:

*   Understanding the font reference model
*   Identifying fonts
*   Selecting and substituting fonts

## Understanding the Font Reference Model

Many components of a processing system interact to produce the final presentation of a document. The actual flow of the data stream through the system depends on both the configuration of the specific system and the intent or design of the user. Figure 9 illustrates the general flow of a document from creation to presentation. The figure assumes that the system, including the font resources, are in place when the user initiates a document. The sections following Figure 9 provide more detail for some of the separate transactions within the figure.

**Figure 9. Font Reference Model General Flow**

*   **User Input**: Create Font References through a User Interface
*   **Document Editing**: Produces Revisable Document
*   **Font Services**: Font Queries, System-Level Font Resources
*   **Document Formatting**: Produces Presentation Document
*   **Document Presentation Services**: Produces Device Data Stream
*   **Document Presenting**: Presentation Device or Device Controller, Device-level Font Resources
*   **Output Document**

### User Input

A user interacts with a text or graphics editor to produce a document that is in a revisable form and contains references to the desired fonts, as shown in Figure 10.

**Figure 10. Creation of a Document**

The user enters the references through the editor's user interface, which might present a list of the names of the available fonts or a list of the characteristic font attributes that enable the processing system to select a font. The document data stream produced by the editor can specify fonts in one of the following ways:

*   Specifies the requested font by name
*   Maps the font attributes selected by the user to a specific font name
*   Reproduces the font attributes of the requested font from a separately stored list of attributes specified by the user
*   Reproduces the font attributes of the requested font from the font resource specified by the user

Because editing the document creates the font references and does not include formatting the document, the user needs to record only the intended font as input (for example, 10 point IBM Sonoran Serif italic).

### Editor Determination of Font Availability

The application program or editor might provide the user with a list of available fonts or locate an available font that corresponds to the font information provided. To do this, as shown in Figure 11, an editor might use an interface that permits inquiries to a font services facility concerning the font resources available to the processing system. Another method might be for the editor to directly access the font resource information in system storage.

**Figure 11. Editor Determination of Font Availability**

The font resources available to a system need not be physically resident on the user's workstation (distributed data base) or physically available in the form required for presentation (logical fonts or transformable resources). The editor queries font services about the availability of font resources.

### Font Services Access to Font Information

The font services facility responds to editor queries and provides font information, as shown in Figure 12, by accessing system-level font resources available in the font library. The font references that the font services facility uses are different in form and content from those required by editing applications.

**Figure 12. Font Services Access to Font Information**

### Formatter Access to Font Information

During formatting, the processing system must interpret the font references contained in the revisable document, locate the required font-metric information, and produce a formatted, final-form document. To do so, as shown in Figure 13, the formatter might use an interface that permits inquiries to a font services facility concerning the font resources available to the processing system. The formatter must generate a query to the font services facility by providing an appropriate font reference (which might be different from that issued during editing), and it will receive a response to the query.

**Figure 13. Formatter Access to Measurement Information**

The presentation document produced will contain references to the font resources used when formatting, and will often be more specific than those found in the revisable document. If the presentation document is sent to another location or system where the specified font resource is not available, it might be necessary to substitute another font for presentation of the document. Thus, font references contained in a presentation document should retain the user intent provided in the revisable document.

### Presentation Services Access to Font Information

The presentation services provide several font-related functions that might include:

*   Accessing and transforming the font information required by a device
*   Down loading font information to a presentation device
*   Determining available resident font support in the device
*   Substituting one font resource for another

When processing presentation information, the processing system, as shown in Figure 14, must generate a query to the font services facility by providing an appropriate font reference. The font services facility responds by identifying the available font resources or the font and shape information that corresponds to the referenced font.

**Figure 14. Presentation Services Access to Font Information**

When processing presentation information, the processing system might also perform a query to the attached presentation devices to determine which resident fonts are available on the device, and it will receive font references that identify those fonts. The data stream for the device produced by the presentation services must contain references to the device-specific font resources needed to present the document, and it might include the required font-metric and character-shape information. Because the data stream for the device must contain specific references to device-specific font resources, the content and format of the font references will often be more specific than those required in the revisable or presentation document references.

## Identifying Fonts

Each of the previously-defined process steps involves the use of font references to font resources. Each reference might require different information in different formats, and the different font resources referenced might be identified by different name formats. To fully support font referencing, the following items must be defined:

*   System-level identifying characteristics
*   Device-level identifying characteristics
*   User input font reference
*   Revisable document font reference
*   Presentation document font reference
*   Device data stream font reference

The general requirements for identifying font resources and referencing font resources are similar, although the specific requirements will vary by the type of installation, the type of implementation, and the point in the process. The specific content and format of a font resource name is defined by the implementing product specifications. When planning to identify font resources, the following factors must be considered:

*   A font reference might be a list of font attributes, or it might be a system file name, a member name, or a resource-management name that is mapped to a system file name or member name.
*   More than one font resource can correspond to a single font reference.
*   More than one font reference can correspond to a single font resource.
*   If a system uses raster font resources, different font resources might be necessary for each presentation device and each presentation size.
*   If a system uses outline font resources, one font resource might provide the data for multiple font sizes, typeface variations, and presentation devices.
*   The list of characters in a font resource might not match the list of characters in a code page.
*   A user's intent should be distinguishable from system-default or architecture-default values.
*   A user's font references should be as device-independent as possible to permit document distribution to a variety of sites and to permit document presentation on a variety of devices.

Fonts can be identified in different ways. One example is by using the descriptive parameters used in the Map Coded Font structured field in MO:DCA. Another example is by using the Global Resource Identifier, GRID, as used in MO:DCA and IPDS. Refer to the *Mixed Object Document Content Architecture Reference* and the *Intelligent Printer Data Stream Reference*.

The specific content and format of a font reference is defined by the appropriate document content, device service, or interface architecture. The following information is provided as a guideline for such use.

### System-Level Font Resource

A system-level font resource is generally designed to be shared across multiple applications and presentation devices in support of multiple document and presentation data streams. System-level font resources will contain the metric and shape information for a large number of graphic characters, identifying them by unique global identifiers, independent of any single document encoding technique. The set of attributes used to identify a system-level font resource is more extensive than those used to identify a device-level font resource because of the metric and shape information used by editor, formatter, and presentation processes.

The following table identifies the AFP parameters and ISO properties (properties is the term used by the ISO/IEC 9541 Font Information Interchange standard) that should appear in a system-level font resource to support font referencing. Because locating a desired font resource requires matching the parameters of a font reference to corresponding parameters in the available font resources, this set of parameters represent the union of the parameters that should appear in any of the supported font references.

#### Table 5. AFP Parameters Mapped to ISO Properties for System-Level Font Resources

| AFP Parameter | ISO Property |
| :--- | :--- |
| resource name | fontname |
| function set code | standardversion |
| typeface name | typeface |
| design source | dsnsource |
| data source | datasource |
| family name | fontfamily |
| posture class | posture |
| weight class | weight |
| width class | propwidth |
| primary GCSGID | incglyphcols |
| nominal vertical font size | dsnsize |
| minimum vertical font size | minsize |
| maximum vertical font size | maxsize |
| nominal horizontal font size | (not supported) |
| minimum horizontal font size | minanascale |
| maximum horizontal font size | maxanascale |
| font classification | dsngroup |
| structure class | structure |
| font type flags | escclass |
| character rotation | nomescdir |
| average escapement | avgescx/y |
| average lowercase escapement | avglcescx/y |
| average capital escapement | avgcapescx/y |
| figure space increment | tabescx/y |
| maximum baseline extent | maxfontext |

The various methods of document encoding are addressed by a separate collection of code page (encoding vector) resources that map the supported code points to the font glyph identifiers. Differences in device resolution and imaging technology are addressed by the presentation process that transforms a system-level font resource and an appropriate code page resource into a device-level font resource.

### Device-Level Font Resource

A device-level font resource is generally designed to be used by one device or family of related devices and a single document encoding technique. Device-level font resources should contain the metric and shape information for a single code page (encoding vector) and will most often identify the metric and shape information by the code point rather than by an independent glyph identifier (though it can use an attached mapping table to associate the code point to the glyph ID). The set of attributes used to identify a device-level font resource is less extensive than that used to identify a system-level font resource because the metric and shape information is used only by the presentation processes.

The following table identifies the AFP parameters and ISO properties that should appear in a device-level font resource to support font referencing.

#### Table 6. AFP Parameters Mapped to ISO Properties for Device-Level Font Resources

| AFP Parameter | ISO Property |
| :--- | :--- |
| resource name | fontname |
| design source | dsnsource |
| data source | datasource |
| family name | fontfamily |
| posture class | posture |
| weight class | weight |
| width class | propwidth |
| primary GCSGID | incglyphcols |
| primary CPGID | (not supported) |
| nominal vertical font size | dsnsize |
| minimum vertical font size | minsize |
| maximum vertical font size | maxsize |
| nominal horizontal font size | (not supported) |
| minimum horizontal font size | minanascale |
| maximum horizontal font size | maxanascale |
| font classification | dsngroup |
| structure class | structure |
| font type flags | escclass |
| character rotation | nomescdir |
| average escapement | avgescx/y |
| average lowercase escapement | avglcescx/y |
| average capital escapement | avgcapescx/y |
| figure space increment | tabescx/y |
| maximum baseline extent | maxfontext |
| pattern technology identifier | glyphshapetech |

### User-Input Font Reference

A user-input font reference is generally designed to be used by people who might not be knowledgeable about font technology, but who wish to use different fonts in their document. Processing applications have a high degree of freedom in how they can present information to users and can hide most of the technical detail from the users, mapping the user's input to the specifics required by the data stream. The user-input font reference can be contained in a reference book containing examples and the corresponding font resource names that users type at their workstation. The font reference can be a workstation displayed list of available fonts from which users select the desired item, or it can be a series of pop-up pull-down menus from which users select various font characteristics (for example: bold, serif, 10 pt., italic) that are desired.

Because the font resources available at any one workstation might not be available at all other workstations in a distributed environment, the user-input font reference should be independent of specific resource names and should focus instead on getting as much information as possible about the user's desired intent (for example, highlight this phrase with the bold version of the current font). Whatever technique is used for user input, the user's selection must be translatable or mappable into the revisable-document font reference.

### Revisable-Document Font Reference

A revisable-document font reference is generally designed to focus on the user's intent for text appearance, with little or no emphasis on specific font resources or font metrics. The document can then be sent to any location, formatted using the best available font resource (that satisfies the user's intent), and printed or displayed for the recipient's use.

Specification of the code page used for encoding the document is necessary at the revisable-document level, but the code page does not have to be linked to a font resource at this stage. The code page does not need to appear in the font reference (although it can if the document data stream architecture also uses the font reference to define the document encoding).

The following table identifies the AFP parameters and ISO properties that should appear in a revisable document font reference.

#### Table 7. AFP Parameters Mapped to ISO Properties for Revisable-Document Font References

| AFP Parameter | ISO Property |
| :--- | :--- |
| resource name | fontname |
| design source | dsnsource |
| family name | fontfamily |
| posture class | posture |
| weight class | weight |
| width class | propwidth |
| vertical font size | dsnsize |
| font classification | dsngroup |
| structure class | structure |
| font type flags | escclass |
| character rotation | nomescdir |

### Presentation-Document Font Reference

A presentation-document font reference is generally designed to focus on the formatter's intent for page layout, while recalling the user's intent for text appearance. The presentation-document font reference should contain all of the revisable-document font reference information along with enough additional information so as to identify the metrics used for page layout. The document can then be sent to any location, and the required font can be located, or an alternate font that closely approximates the metrics can be substituted (see “Font Selection and Substitution”).

Specification of the glyph complement (graphic character set) that corresponds to the code page used for encoding the document is necessary at the presentation-document level, but the code page need not be linked to the font resource at this stage.

#### Table 8. AFP Parameters Mapped to ISO Properties for Presentation-Document Font References

| AFP Parameter | ISO Property |
| :--- | :--- |
| resource name | fontname |
| function set code | standardversion |
| typeface name | typeface |
| design source | dsnsource |
| data source | datasource |
| family name | fontfamily |
| posture class | posture |
| weight class | weight |
| width class | propwidth |
| primary GCSGID | incglyphcols |
| vertical font size | dsnsize |
| font classification | dsngroup |
| structure class | structure |
| font type flags | escclass |
| character rotation | nomescdir |
| average escapement | avgescx/y |
| average lowercase escapement | avglcescx/y |
| average capital escapement | avgcapescx/y |
| figure space increment | tabescx/y |
| maximum baseline extent | maxfontext |

### Device Data Stream Font Reference

A device data stream font reference is generally designed to focus on the presentation process's intent for glyph shape rendering, while providing enough reference information to identify the required font information under many different device-specific font resource names.

Specification of the glyph complement (graphic character set) and the corresponding code page used for encoding the document is necessary at the device data stream level.

#### Table 9. AFP Parameters Mapped to ISO Properties for Device Data Stream Font References

| AFP Parameter | ISO Property |
| :--- | :--- |
| resource name | fontname |
| design source | dsnsource |
| data source | datasource |
| family name | fontfamily |
| posture class | posture |
| weight class | weight |
| width class | propwidth |
| primary GCSGID | incglyphcols |
| primary CPGID | (not supported) |
| vertical font size | dsnsize |
| font classification | dsngroup |
| structure class | structure |
| font type flags | escclass |
| character rotation | nomescdir |
| average escapement | avgescx/y |
| average lowercase escapement | avglcescx/y |
| average capital escapement | avgcapescx/y |
| figure space increment | tabescx/y |
| maximum baseline extent | maxfontext |
| pattern technology identifier | glyphshapetech |

## Font Selection and Substitution

When an application process encounters a font reference, it is necessary for the process to locate a font resource that corresponds to that reference. **Font selection** is the process of locating a font resource that corresponds to the font reference or that satisfies all the information contained in the font reference. **Font substitution** is the process of locating an alternate font resource that approximates the font resource, because no font resource could be found that met the selection criteria.

Font selection is most simply performed by having an exact match of a font resource name to a font reference that contains the font resource name. But, in a distributed processing environment, the number and combination of font resource names is without limit. The chances of an exact match for a font resource name diminishes as the number of users in a distributed environment increases.

Font substitution can be as simple as using a system or device default font (for example, substituting fixed pitch Courier whenever the requested font cannot be found), but that is often disappointing to the user. Font selection or substitution is most effective when all of the identifying characteristics contained in a font reference are intelligently compared to the identifying characteristics contained in all of the available font resources, until an exact or best match is located.

The specific data formats and processing steps required for intelligent font substitution is outside the scope of this architecture. However, the following sections provide guidelines for various selection and substitution situations.

### Identifying User Intent

The document data stream should contain information about a user's intent for presentation. The success of font substitution is described in terms of fidelity. There are five classes of fidelity, described in order of increasing fidelity:

*   **Content fidelity**: Permits a user to identify the characters used in the document, or to identify a character that cannot be presented.
*   **Format fidelity**: Preserves line and cell identity. A user must be able to identify the basic parts of the logical document structure.
*   **Layout fidelity**: Maintains the integrity of line endings, column endings, and page endings. Maintains the relationship between areas.
*   **Appearance fidelity**: Requires a document, or area, to appear as specified by the user. Substitution of visually equivalent fonts is permitted.
*   **Absolute fidelity**: Requires a document to appear precisely as specified by the user. No substitution of fonts is permitted.

### Document Editing

The document-editing process provides the interface between the user input and the revisable document data stream output. The preferred method is to take the information provided by the user input and select an available font resource that satisfies the user's intent, and then copy the required font parameters from the selected font resource into the corresponding parameters of the revisable-document font reference.

For the document editing process, the following default order should be used for font selection:

1.  **Content-level fidelity**: The selected font resource must contain the glyphs needed to render the code points contained in the document data stream (GCSGID).
2.  **Format- and layout-level fidelity**: Takes precedence over appearance-level fidelity.
    *   Vertical font size
    *   Escapement direction
    *   Escapement class
3.  **Appearance-level fidelity**:
    *   Structure class
    *   Posture class
    *   Weight class
    *   Width class
    *   Font classification
    *   Family name
    *   Design source

### Document Formatting

The document-formatting process provides the interface between the revisable-document data stream input and the presentation-document data stream output.

For the document formatting process, the following default order should be used for font selection (same as for document editing):

1.  **Content-level fidelity**: Selected font resource must contain required glyphs (GCSGID).
2.  **Format- and layout-level fidelity**: Vertical font size, Escapement direction, Escapement class.
3.  **Appearance-level fidelity**: Structure, Posture, Weight, Width classes, Font classification, Family name, Design source.

### Document Presentation

The document-presentation process provides the interface between the presentation-document data stream input and the device data stream output.

For the document-presentation process, the following default order should be used for font selection:

1.  **Content-level fidelity**: Selected font resource must contain required glyphs (GCSGID).
2.  **Format- and layout-level fidelities**: Takes precedence over appearance-level fidelity after the document has been formatted.
    *   Vertical font size
    *   Escapement direction
    *   Escapement class
    *   Maximum extents
    *   Average escapement (General, Lowercase, Capital)
    *   Tabular escapement (figure space increment)
3.  **Appearance-level fidelity**: Structure, Posture, Weight, Width classes, Font classification, Family name, Design source.
