# 12 Interactive features

## 12.1 General

This clause describes the PDF features that allow a user to interact with a document on the screen (with the exception of multimedia features, which are described in 13, "Multimedia features"):

• Preference settings to control the way the document is presented on the screen (12.2, "Viewer preferences") •    Navigation facilities for moving through the document in a variety of ways (subclauses 12.3, "Document-level navigation" and 12.4, "Page-level navigation") •    Annotations for adding text notes, links, rich media and other ancillary information to the document (12.5, "Annotations") •    Actions that can be triggered by specified events (12.6, "Actions") •    Interactive forms for gathering information from the user (12.7, "Forms") •    Digital signatures that authenticate the identity of a user and the validity of the document’s contents (12.8, "Digital signatures") •    Measurement properties that enable the display of real-world units corresponding to objects on a page (12.9, "Measurement properties") •    A geospatial coordinate system is introduced in subclause 12.10, "Geospatial features" along with a number of PDF constructs to support geospatially registered content.
• Subclause 12.11, "Document requirements" describes how a document may specify requirements that an interactive PDF processor should satisfy in order for the document to function as intended by the author.

## 12.2 Viewer preferences

The ViewerPreferences entry in a document’s catalog dictionary (see 7.7.2, "Document catalog dictionary") designates a viewer preferences dictionary (PDF 1.2) controlling the way the document shall be presented on the screen or in print. If no such dictionary is specified, PDF processors should behave in accordance with their own current user preference settings. "Table 147 — Entries in a viewer preferences dictionary" shows the contents of the viewer preferences dictionary.

Table 147 — Entries in a viewer preferences dictionary

| Key | Type | Value |
| --- | --- | --- |
| HideToolbar | boolean | (Optional) A flag specifying whether to hide the interactive PDF processor’s tool bars when the document is active. Default value: false. |
| HideMenubar | boolean | (Optional) A flag specifying whether to hide the interactive PDF processor’s menu bar when the document is active. Default value: false. |


| Key | Type | Value |
| --- | --- | --- |
| HideWindowUI | boolean | (Optional) A flag specifying whether to hide user interface elements in the document’s window (such as scroll bars and navigation controls), leaving only the document’s contents displayed. D efault value: false. |
| FitWindow | boolean | (Optional) A flag specifying whether to resize the document’s window to fit the size of the first displayed page. Default value: false. |
| CenterWindow | boolean | (Optional) A flag specifying whether to position the document’s window in the centre of the screen. Default value: false. |
| DisplayDocTitle | boolean | (Optional; PDF 1.4) A flag specifying whether the window’s title bar should display the document title taken from the dc:title element of the XMP metadata stream (see 14.3.2, "Metadata streams"). If false, the title bar should instead display the name of the PDF file containing the document. Default value: false. |
| NonFullScreenPageMode | name | (Optional) The document’s page mode, specifying how to display the document on exiting full-screen mode: |
| UseNone | Neither document outline nor thumbnail images visible |  |
| UseOutlines | Document outline visible |  |
| UseThumbs | Thumbnail images visible |  |
| UseOC | Optional content group panel visible This entry is meaningful only if the value of the PageMode entry in the catalog dictionary (see 7.7.2, "Document catalog dictionary") is FullScreen; it shall be ignored otherwise. Default value: UseNone. |  |
| Direction | name | (Optional; PDF 1.3) The predominant logical content order for text: |
| L2R | Left to right |  |
| R2L | Right to left (including vertical writing systems, such as Chinese, Japanese, and Korean) This entry has no direct effect on the document’s contents or page numbering but may be used to determine the relative positioning of pages when displayed side by side or printed n-up. Default value: L2R. |  |
| ViewArea | name | (Optional; PDF 1.4; deprecated in PDF 2.0) The name of the page boundary representing the area of a page that shall be displayed when viewing the document on the screen. The value is the key designating the relevant page boundary in the page object (see 7.7.3, "Page tree" and 14.11.2, "Page boundaries"). If the specified page boundary is not defined in the page object, its default value shall be used, as specified in "Table 31 — Entries in a page object". Default value: CropBox. This entry is intended primarily for use by prepress applications that interpret or manipulate the page boundaries as described in 14.11.2, "Page boundaries". The presence of this value in a PDF may cause a PDF to display differently from how it will be printed. |


| Key | Type | Value |
| --- | --- | --- |
| ViewClip | name | (Optional; PDF 1.4; deprecated in PDF 2.0) The name of the page boundary to which the contents of a page shall be clipped when viewing the document on the screen. The value is the key designating the relevant page boundary in the page object (see 7.7.3, "Page tree" and 14.11.2, "Page boundaries"). If the specified page boundary is not defined in the page object, its default value shall be used, as specified in "Table 31 — Entries in a page object". Default value: CropBox. This entry is intended primarily for use by prepress applications that interpret or manipulate the page boundaries as described in 14.11.2, "Page boundaries". The presence of this value in a PDF may cause a PDF to display differently from how it will be printed. |
| PrintArea | name | (Optional; PDF 1.4; deprecated in PDF 2.0) The name of the page boundary representing the area of a page that shall be rendered when printing the document. The value is the key designating the relevant page boundary in the page object (see 7.7.3, "Page tree" and 14.11.2, "Page boundaries"). If the specified page boundary is not defined in the page object, its default value shall be used, as specified in "Table 31 — Entries in a page object". Default value: CropBox. This entry is intended primarily for use by prepress applications that interpret or manipulate the page boundaries as described in 14.11.2, "Page boundaries". The presence of this value in a PDF may cause a PDF to display differently from how it will be printed. |
| PrintClip | name | (Optional; PDF 1.4; deprecated in PDF 2.0) The name of the page boundary to which the contents of a page shall be clipped when printing the document. The value is the key designating the relevant page boundary in the page object (see 7.7.3, "Page tree" and 14.11.2, "Page boundaries"). If the specified page boundary is not defined in the page object, its default value shall be used, as specified in "Table 31 — Entries in a page object". Default value: CropBox. This entry is intended primarily for use by prepress applications that interpret or manipulate the page boundaries as described in 14.11.2, "Page boundaries". The presence of this value in a PDF may cause a PDF to display differently from how it will be printed. |
| PrintScaling | name | (Optional; PDF 1.6) The page scaling option that shall be selected when a print dialogue is displayed for this document. Valid values are None, which indicates no page scaling, and AppDefault, which indicates the interactive PDF processor’s default print scaling. If this entry has an unrecognised value, AppDefault shall be used. Default value: AppDefault. If the print dialogue is suppressed and its parameters are provided from some other source, this entry nevertheless shall be honoured. |


| Key | Type | Value |
| --- | --- | --- |
| Duplex | name | (Optional; PDF 1.7) The paper handling option that shall be used when printing the PDF file from the print dialogue. The following values are valid: |
| Simplex | Print single-sided |  |
| DuplexFlipShortEdge | Duplex and flip on the short edge of the sheet |  |
| DuplexFlipLongEdge | Duplex and flip on the long edge of the sheet Default value: implementation dependent |  |
| PickTrayByPDFSize | boolean | (Optional; PDF 1.7) A flag specifying whether the PDF page size shall be used to select the input paper tray. This setting influences only the preset values used to populate the print dialogue presented by an interactive PDF processor. If PickTrayByPDFSize is true, the check box in the print dialogue associated with input paper tray shall be checked. This setting has no effect on operating systems that do not provide the ability to pick the input tray by size. Default value: implementation dependent |
| PrintPageRange | array | (Optional; PDF 1.7) The page numbers used to initialise the print dialogue box when the PDF file is printed. The array shall contain an even number of integers to be interpreted in pairs, with each pair specifying the first and last pages in a sub-range of pages to be printed. The first page of the PDF file shall be denoted by 1. Default value: implementation dependent |

> **NOTE** Although PrintPageRange uses 1-based page numbering, other features of PDF use zero-based page numbering.

| NumCopies | integer | (Optional; PDF 1.7) The number of copies that shall be printed when the print dialog is opened for this PDF file. Default value: implementation dependent, but typically 1 |
| --- | --- | --- |
| Enforce | array | (Optional; PDF 2.0) An array of names of Viewer preference settings that shall be enforced by PDF processors and that shall not be overridden by subsequent selections in the application user interface. "Table 148 — Names defined for an Enforce array" specifies names that shall be valid to use in this array. |

The Enforce array shall only include names that occur in "Table 148 — Names defined for an Enforce array". Future additions to this table shall be limited to keys in the viewer preferences dictionary with the following qualities:

• can be assigned values (default or specified) that cannot be used in a denial-of-service attack, and •    have default values that cannot be overridden using the application user interface.


Table 148 — Names defined for an Enforce array

| Name | Description |
| --- | --- |
| PrintScaling | (Optional; PDF 2.0) This name may appear in the Enforce array only if the corresponding entry in the viewer preferences dictionary ("Table 147 — Entries in a viewer preferences dictionary") specifies a valid value other than AppDefault. |

## 12.3 Document-level navigation

### 12.3.1 General

The features described in this subclause allow an interactive PDF processor to present the user with an interactive, global overview of a document in either of two forms:

• As a hierarchical outline showing the document’s internal structure •    As a collection of thumbnail images representing the pages of the document in miniature form Each item in the outline or each thumbnail image may be associated with a corresponding destination in the document, so that the user can jump directly to the destination by clicking with the mouse.

### 12.3.2 Destinations

#### 12.3.2.1 General

A destination defines a particular view of a document, consisting of the following items:

• The page of the document that shall be displayed •    The location of the document window on that page •    The magnification (zoom) factor Destinations may be associated with outline items (see 12.3.3, "Document outline"), annotations (12.5.6.5, "Link annotations"), or actions (12.6.4.2, "Go-To actions" and 12.6.4.3, "Remote Go-To actions"). In each case, the destination specifies the view of the document that shall be presented when the outline item or annotation is opened or the action is performed. In addition, the optional OpenAction entry in a document’s catalog dictionary (7.7.2, "Document catalog dictionary") may specify a destination that shall be displayed when the document is opened. A destination may be specified either explicitly by an array of parameters defining its properties or indirectly by name.

#### 12.3.2.2 Explicit destinations

"Table 149 — Destination syntax" shows the allowed syntactic forms for specifying a destination explicitly in a PDF file. In each case, page is an indirect reference to a page object (except in a remote go-to action; see 12.6.4.3, "Remote Go-To actions", or an embedded go-to action; see 12.6.4.4, "Embedded Go-To actions"). All coordinate values (left, right, top, and bottom) shall be expressed in the default user space coordinate system. The page’s bounding box is the smallest rectangle enclosing all of its contents. (If any side of the bounding box lies outside the page’s crop box, the corresponding side of the crop box shall be used instead; see 14.11.2, "Page boundaries" for further discussion of the crop box.)


> **NOTE** The above paragraph was corrected to also include embedded go-to actions (2020).
No page object can be specified for a destination associated with a remote go-to action (see 12.6.4.3, "Remote Go-To actions") because the destination page is in a different PDF document. In this case, the page parameter specifies an integer page number within the remote document instead of a page object in the current document.

Table 149 — Destination syntax

| Syntax | Meaning |
| --- | --- |
| [page /XYZ left top zoom] | Display the page designated by page, with the coordinates (left, top) positioned at the upper-left corner of the window and the contents of the page magnified by the factor zoom. A null value for any of the parameters left, top, or zoom specifies that the current value of that parameter shall be retained unchanged. A zoom value of 0 has the same meaning as a null value. |
| [page /Fit] | Display the page designated by page, with its contents magnified just enough to fit the entire page within the window both horizontally and vertically. If the required horizontal and vertical magnification factors are different, use the smaller of the two, centring the page within the window in the other dimension. |
| [page /FitH top] | Display the page designated by page, with the vertical coordinate top positioned at the top edge of the window and the contents of the page magnified just enough to fit the entire width of the page within the window. A null value for top specifies that the current value of that parameter shall be retained unchanged. |
| [page /FitV left] | Display the page designated by page, with the horizontal coordinate left positioned at the left edge of the window and the contents of the page magnified just enough to fit the entire height of the page within the window. A null value for left specifies that the current value of that parameter shall be retained unchanged. |
| [page /FitR left bottom right top] | Display the page designated by page, with its contents magnified just enough to fit the rectangle specified by the coordinates left, bottom, right, and top entirely within the window both horizontally and vertically. If the required horizontal and vertical magnification factors are different, use the smaller of the two, centring the rectangle within the window in the other dimension. |
| [page /FitB] | (PDF 1.1) Display the page designated by page, with its contents magnified just enough to fit its bounding box entirely within the window both horizontally and vertically. If the required horizontal and vertical magnification factors are different, use the smaller of the two, centring the bounding box within the window in the other dimension. |
| [page /FitBH top] | (PDF 1.1) Display the page designated by page, with the vertical coordinate top positioned at the top edge of the window and the contents of the page magnified just enough to fit the entire width of its bounding box within the window. A null value for top specifies that the current value of that parameter shall be retained unchanged. |


| Syntax | Meaning |
| --- | --- |
| [page /FitBV left] | (PDF 1.1) Display the page designated by page, with the horizontal coordinate left positioned at the left edge of the window and the contents of the page magnified just enough to fit the entire height of its bounding box within the window. A null value for left specifies that the current value of that parameter shall be retained unchanged. |

#### 12.3.2.3 Structure destinations

A destination provides a view within a given document, however there is no direct connection between the location of the view on the page and the content displayed. Structure elements (see 14.7.2, "Structure hierarchy") allow specific sequences of content on a page to be identified. A structure destination provides the same view mechanism as a destination, but references a structure element instead of a page. A structure destination shall use the same syntax as a destination (see "Table 149 — Destination syntax"), except that the first entry in the array shall be an indirect reference to a structure element dictionary instead of to a page dictionary.

> **EXAMPLE** A regular destination of the following syntax [page /FitH 500] could be represented as a structure destination with the following syntax [elem /FitH 500] where elem represents an indirect reference to a structure element dictionary.

The structure element shall be used to identify the page to which the content belongs and, using that page, the structure destination shall behave identically to a destination. To identify the page to which a structure destination refers, the following algorithm shall be used. The kids of the structure element shall be processed in linear array order. If the first kid is a marked-content reference or an object reference (see 14.6, "Marked content"), then the page to which that reference belongs shall be used as the page. If the first kid is a structure element, then processing shall continue down to that element using the same algorithm recursively. If no content or object reference is found under the first entry, processing should proceed to next entry, repeating the process. This shall continue until all entries have been processed or until the first page is identified. In the case where no page content is identified, then the page reference shall be assumed to be the first page in the document.

No structure element dictionary can be specified for a structure destination associated with a remote go-to action (see 12.6.4.3, "Remote Go-To actions") or embedded go-to actions (see 12.6.4.4, "Embedded Go-To actions") because the destination structure element is in a different PDF document.
In this case, the indirect reference to the structure element dictionary shall be replaced by a byte string representing a structure element ID (see "Table 355 — Entries in a structure element dictionary").

> **NOTE 1** The above paragraph was corrected to also include embedded go-to actions (2020).

> **NOTE 2** There is no requirement that a given structure element will have an ID associated with it, nor that it will remain consistent across edits to a PDF document. For remote go-to actions which rely on a target PDF having an ID, it is important that such an ID exist and that it remain consistent across versions of the target document.

#### 12.3.2.4 Named destinations

Instead of being defined directly with the explicit syntax shown in "Table 149 — Destination syntax", a destination may be referred to indirectly by means of a name object (PDF 1.1) or a byte string (PDF 1.2). This capability is especially useful when the destination is located in another PDF document.


> **NOTE 1** A link to the beginning of Chapter 6 in another document can refer to the destination by a name, such as Chap6.begin, instead of by an explicit page number in the other document. Then, the location of the chapter in the other document could change without invalidating the link. If an annotation or outline item that refers to a named destination has an associated action, such as a remote go-to action (see 12.6.4.3, "Remote Go-To actions") or a thread action (12.6.4.7, "Thread actions"), the destination is in the PDF file specified by the action’s F entry, if any; if there is no F entry, the destination is in the current PDF file.
In PDF 1.1, the correspondence between name objects and destinations shall be defined by the Dests entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary"). The value of this entry shall be a dictionary in which each key is a destination name and the corresponding value is either an array defining the destination, using the syntax shown in "Table 149 — Destination syntax", or a dictionary with a D entry whose value is such an array and may optionally contain an SD entry as defined in "Table 201 — Action types".

> **NOTE 2** The latter form allows additional attributes to be associated with the destination, as well as enabling a go-to action (see 12.6.4.2, "Go-To actions") that can be used as the target of a named destination.
In PDF 1.2 and later, the correspondence between strings and destinations may alternatively be defined by the Dests entry in the document’s name dictionary (see 7.7.4, "Name dictionary"). The value of this entry shall be a name tree (7.9.6, "Name trees") mapping name strings to destinations. (The keys in the name tree may be treated as text strings for display purposes.) The destination value associated with a key in the name tree may be either an array or a dictionary, as described in the preceding paragraph.

When trying to locate a named destination in a names tree, either in the same or in remote PDF files, the algorithms specified in J.3.3, "String objects" or J.3.4, "Name objects" shall be used (depending on the type of object being compared).

> **NOTE 3** The above paragraph was added in this document (2020).

> **NOTE 4** The use of strings as destination names is a PDF 1.2 feature. If compatibility with versions of PDF prior to PDF 1.2 is required, only name objects can be used to refer to named destinations. A document that supports PDF 1.2 or later can contain both types. However, if backward compatibility to PDF 1.2 is not a consideration, it is recommended that applications use the string form of representation in the Dests name tree.

### 12.3.3 Document outline

A PDF document may contain a document outline that the interactive PDF processor may display on the screen, allowing the user to navigate interactively from one part of the document to another. The outline consists of a tree-structured hierarchy of outline items (sometimes called bookmarks), which serve as a visual table of contents to display the document’s structure to the user. The user may interactively open and close individual items by clicking them with the mouse. When an item is open, its immediate children in the hierarchy shall become visible on the screen; each child may in turn be open or closed, selectively revealing or hiding further parts of the hierarchy. When an item is closed, all of its descendants in the hierarchy shall be hidden. Clicking the text of any visible item activates the item, causing the interactive PDF processor to jump to a destination or trigger an action associated with the item.

The root of a document’s outline hierarchy is an outline dictionary specified by the Outlines entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary"). "Table 150 — Entries in


the outline dictionary" shows the contents of this dictionary. Each individual outline item within the hierarchy shall be defined by an outline item dictionary ("Table 151 — Entries in an outline item dictionary"). The items at each level of the hierarchy form a linked list, chained together through their Prev and Next entries and accessed through the First and Last entries in the parent item (or in the outline dictionary in the case of top-level items). When displayed on the screen, the items at a given level shall appear in the order in which they occur in the linked list.

Table 150 — Entries in the outline dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Outlines for an outline dictionary. |
| First | dictionary | (Required if there are any open or closed outline entries; shall be an indirect reference) An outline item dictionary representing the first top-level item in the outline. |
| Last | dictionary | (Required if there are any open or closed outline entries; shall be an indirect reference) An outline item dictionary representing the last top-level item in the outline. |
| Count | integer | (Required if the document has any open outline entries) Total number of visible outline items at all levels of the outline. The value cannot be negative. This entry shall be omitted if there are no open outline items. |

Table 151 — Entries in an outline item dictionary

| Key | Type | Value |
| --- | --- | --- |
| Title | text string | (Required) The text that shall be displayed on the screen for this item. |
| Parent | dictionary | (Required; shall be an indirect reference) The parent of this item in the outline hierarchy. The parent of a top-level item shall be the outline dictionary itself. |
| Prev | dictionary | (Required for all but the first item at each level; shall be an indirect reference) The previous item at this outline level. |
| Next | dictionary | (Required for all but the last item at each level; shall be an indirect reference) The next item at this outline level. |
| First | dictionary | (Required if the item has any descendants; shall be an indirect reference) The first of this item’s immediate children in the outline hierarchy. |
| Last | dictionary | (Required if the item has any descendants; shall be an indirect reference) The last of this item’s immediate children in the outline hierarchy. |


| Key | Type | Value |
| --- | --- | --- |
| Count | integer | (Required if the item has any descendants) If the outline item is open, Count is the sum of the number of visible descendent outline items at all levels. The number of visible descendent outline items shall be determined by the following recursive process: |
| Step 1. | Initialize Count to zero. |  |
| Step 2. | Add to Count the number of immediate children. During repetitions of this step, update only the Count of the original outline item. |  |
| Step 3. | For each of those immediate children whose Count is positive and non-zero, repeat steps 2 and 3. If the outline item is closed, Count is negative and its absolute value is the number of descendants that would be visible if the outline item were opened. |  |
| Dest | name, byte | (Optional; shall not be present if an A entry is present) The |
| string, or array | destination that shall be displayed when this item is activated (see 12.3.2, "Destinations"). |  |
| A | dictionary | (Optional; PDF 1.1; shall not be present if a Dest entry is present) The action that shall be performed when this item is activated (see 12.6, "Actions"). |
| SE | dictionary | (Optional; PDF 1.3; shall be an indirect reference) The structure element to which the item refers (see 14.7.2, "Structure hierarchy"). |

> **NOTE** This value is not intended for navigation. Structure Destinations (Dest entry) are the method to provide structure-based navigation.

| C | array | (Optional; PDF 1.4) An array of three numbers in the range 0.0 to 1.0, representing the components in the DeviceRGB colour space of the colour that shall be used for the outline entry’s text. Default value: [0.0 0.0 0.0]. |
| --- | --- | --- |
| F | integer | (Optional; PDF 1.4) A set of flags specifying style characteristics for displaying the outline item’s text (see "Table 152 — Outline item flags"). Default value: 0. |

The value of the outline item dictionary’s F entry (PDF 1.4) shall be an integer interpreted as one-bit flags specifying style characteristics for displaying the item. Bit positions within the flag word are numbered from low-order to high-order bits, with the lowest-order bit numbered 1. "Table 152 — Outline item flags" shows the meanings of the flags; all other bits of the integer shall be 0.

Table 152 — Outline item flags

| Bit position | Name | Meaning |
| --- | --- | --- |
| Italic | If set to 1, display the item in italic. |  |
| Bold | If set to 1, display the item in bold. |  |


> **EXAMPLE** The following example shows a typical outline dictionary and outline item dictionary. See H.6, "Outline hierarchy example" for an example of a complete outline hierarchy.

21 0 obj <</Count 6 /First 22 0 R /Last 29 0 R >> endobj

22 0 obj <</Title ( Chapter 1 ) /Parent 21 0 R /Next 26 0 R /First 23 0 R /Last 25 0 R /Count 3 /Dest [3 0 R /XYZ 0 792 0] >> endobj

### 12.3.4 Thumbnail images

A PDF document may contain thumbnail images representing the contents of its pages in miniature form. An interactive PDF processor may display these images on the screen, allowing the user to navigate to a page by clicking its thumbnail image:

> **NOTE** Thumbnail images are not required, and can be included for some pages and not for others.
The thumbnail image for a page shall be an image XObject specified by the Thumb entry in the page object (see 7.7.3, "Page tree"). It has the usual structure for an image dictionary (8.9.5, "Image dictionaries"), but only the Width, Height, ColorSpace, BitsPerComponent, and Decode entries are significant; all of the other entries listed in "Table 87 — Additional entries specific to an image
| dictionary" shall be ignored if present. (If a | Subtype entry is specified, its value shall be | Image.) The |
| --- | --- | --- |
| image’s colour space shall be either | DeviceGray or DeviceRGB, or an Indexed colour space based on |  |

one of these.

> **EXAMPLE** This example shows a typical thumbnail image definition.

12 0 obj <</Width 76 /Height 99 /ColorSpace /DeviceRGB /BitsPerComponent 8 /Length 13 0 R /Filter [/ASCII85Decode /DCTDecode] >> stream s4IA>!"M;*Ddm8XA,lT0!!3,S!/(=R!<E3%!<N<(!WrK*!WrN, … Omitted data … endstream endobj

13 0 obj %Length of stream … endobj


### 12.3.5 Collections

#### 12.3.5.1 General

Beginning with PDF 1.7, PDF documents may specify how an interactive PDF processor’s user interface presents collections of file attachments, where the attachments are related in structure or content. Such a presentation is called a portable collection.

> **NOTE 1** The intent of portable collections is to present, sort, and search collections of related documents embedded in the containing PDF document, such as email archives, photo collections, and engineering bid sets. There is no requirement that documents in a collection have an implicit relationship or even a similarity; however, showing differentiating characteristics of related documents can be helpful for document navigation.
A collection dictionary specifies the viewing and organisational characteristics of portable collections. If this dictionary is present in a PDF document, the interactive PDF processor shall present the document as a portable collection. The EmbeddedFiles name tree specifies file attachments (see "Table 32 — Entries in the name dictionary").

When an interactive PDF processor first opens a PDF document containing a collection, it shall display the contents according to the View key of the collection dictionary. The initial document may be the container PDF or one of the embedded documents as specified by the D key in the collection dictionary.

> **NOTE 2** The page content in the initial document needs to contain information that helps the user understand what is contained in the collection, such as a title and an introductory paragraph.
The file attachments comprising a collection shall be located in the EmbeddedFiles name tree. All attachments in that tree are in the collection; any attachments not in that tree are not. For a PDF document that is an unencrypted wrapper for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document"), the EmbeddedFiles name tree shall contain exactly one entry, for the encrypted payload document.

Beginning with PDF 2.0, a portable collection may include an interactive layout, or presentation, of the collection contents. The collection layout or presentation is called a navigator. For more information about navigators, see 12.3.6, "Navigators".

When a navigator is used the collection dictionary shall contain some entries that support navigators.
The Navigator entry shall be an indirect reference to a navigator dictionary that describes the interactive layout. The value of the Colors entry shall be a collection colors dictionary that specifies a suggested set of colours for use by a collection layout. The Folders entry shall be an indirect reference to the root folder of the collection’s folder structure.

“Table 153 — Entries in a collection dictionary” describes the entries in a collection dictionary.

Table 153 — Entries in a collection dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Collection for a collection dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| Schema | dictionary | (Optional) A collection schema dictionary (see "Table 154 — Entries in a collection schema dictionary"). If absent, the interactive PDF processor may choose useful defaults that are known to exist in a file specification dictionary, such as the file name, file size, and modified date. |
| D | byte string | (Optional) A string that identifies an entry in the EmbeddedFiles name tree, determining the document that shall be initially presented in the user interface. If the D entry is missing or is not a valid byte string, the initial document shall be the one that contains the collection dictionary. If the D entry is a valid byte string that does not match any file in the EmbeddedFiles name tree, the interactive PDF processor shall select the first item from the list of files to display in its user interface; if no files exist in the name tree, the interactive PDF processor shall display an empty preview window. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the D entry is required, and shall identify the encrypted payload entry in the EmbeddedFiles name tree. |
| View | name | (Optional) The initial view. The following values are valid: |

# D The collection view shall be presented in details mode, with all

information in the Schema dictionary presented in a multicolumn format. This mode provides the most information to the user.
T The collection view shall be presented in tile mode, with each file in the collection denoted by a small icon and a subset of information from the Schema dictionary. This mode provides top-level information about the file attachments to the user.

# H The collection view shall be initially hidden. The interactive PDF

processor shall provide means for the user to view the collection by some explicit action. The PDF processor should display the document specified by the D entry.

> **NOTE** How the PDF processor chooses to display the collection is implementation specific.
| C | (PDF 2.0, valid only when Navigator is present) The collection view shall be presented by the navigator specified by the Navigator entry. Default value: D (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the View entry is required, and shall have a value of H. |  |
| --- | --- | --- |
| Navigator | dictionary | (Required if the value of View is C; PDF 2.0) An indirect reference to the navigator dictionary that describes the navigator that provides the collection view. See "Table 160 — Entries in a navigator dictionary". |
| Colors | dictionary | (Optional; PDF 2.0) A collection colors dictionary specifying a suggested set of colours for use by a collection layout. See "Table 157 — Entries in a collection colors dictionary". |

> **NOTE** It is recommended that a layout use the colours provided.


| Key | Type | Value |
| --- | --- | --- |
| Sort | dictionary | (Optional) A collection sort dictionary, which specifies the order in which items in the collection shall be sorted in the user interface (see "Table 156 — Entries in a collection sort dictionary"). |
| Folders | dictionary | (Required if the collection has folders; PDF 2.0) An indirect reference to a folder dictionary that is the single common ancestor of all other folders in a portable collection. See "Table 159 — Entries in a folder dictionary". |
| Split | dictionary | (Optional; PDF 2.0) A collection split dictionary that specifies the orientation of the splitter bar in the user interface provided by the interactive PDF processor. See "Table 158 — Entries in a collection split dictionary". If Split is not present, the preferred orientation is determined by the value of the View key. A value of D (or no value) shall indicate a horizontal orientation, while a value of T shall indicate a vertical orientation. No splitter shall be used if the View key has a value of H or C. |

A collection schema dictionary consists of a variable number of individual collection field dictionaries.
Each collection field dictionary has a key chosen by the interactive PDF writer, which shall be used to associate a field with data in a file specification. "Table 154 — Entries in a collection schema dictionary" describes the entries in a collection schema dictionary.

Table 154 — Entries in a collection schema dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be CollectionSchema for a collection schema dictionary. |
| Other keys | dictionary | (Optional) A collection field dictionary. Each key name is chosen at the discretion of the PDF writer. The key name shall be used to identify a corresponding collection item dictionary referenced from the file specification dictionary's CI entry (see CI key in "Table 43 — Entries in a file specification dictionary"). |

A collection field dictionary describes the attributes of a particular field in a portable collection, including the type of data stored in the field and the lookup key used to locate the field data in the file specification dictionary. "Table 155 — Entries in a collection field dictionary" describes the entries in a collection field dictionary.


Table 155 — Entries in a collection field dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be CollectionField for a collection field dictionary. |
| Subtype | name | (Required) The subtype of collection field or file-related field that this dictionary describes. This entry identifies the type of data that shall be stored in the field. The following values identify the types of fields in the collection item or collection subitem dictionary: |
| S | A text field. The field data shall be stored as a PDF text string. |  |

# D A date field. The field data shall be stored as a PDF date string (see 7.9.4,

"Dates").

# N A number field. The field data shall be stored as a PDF number.

The following values identify the types of file-related fields: F                         The field data shall be the file name of the embedded file stream, as identified by the UF entry of the file specification, if present; otherwise by the F entry of the file specification (see "Table 43 — Entries in a file specification dictionary").
| Desc | The field data shall be the description of the embedded file stream, as identified by the Desc entry in the file specification dictionary (see "Table 43 — Entries in a file specification dictionary"). |  |
| --- | --- | --- |
| ModDate | The field data shall be the modification date of the embedded file stream, as identified by the ModDate entry in the embedded file parameter dictionary (see "Table 45 — Entries in an embedded file parameter dictionary"). |  |
| CreationDate | The field data shall be the creation date of the embedded file stream, as identified by the CreationDate entry in the embedded file parameter dictionary (see "Table 45 — Entries in an embedded file parameter dictionary"). |  |
| Size | The field data shall be the size of the embedded file, as identified by the Size entry in the embedded file parameter dictionary (see "Table 45 — Entries in an embedded file parameter dictionary"). |  |
| CompressedSize | (PDF 2.0) The field data shall be the length of the embedded file stream, as identified by the Length entry in the embedded file stream dictionary (see 7.11.4, "Embedded file streams"), and the two values shall be identical. |  |
| N | text string | (Required) The textual field name that shall be presented to the user by the interactive PDF processor. |
| O | integer | (Optional) The relative order of the field name in the user interface. Fields shall be sorted by the interactive PDF processor in ascending order. |
| V | boolean | (Optional) The initial visibility of the field in the user interface. Default value: true. |
| E | boolean | (Optional) A flag indicating whether the interactive PDF processor should provide support for editing the field value. Default value: false. |

A collection sort dictionary identifies the fields that shall be used to sort items in the collection. The type of sorting depends on the type of data:


• Text strings shall be ordered lexically from smaller to larger, if ascending order is specified.

> **NOTE 3** Lexical ordering is an implementation dependency for interactive PDF processors.
• Numbers shall be ordered numerically from smaller to larger, if ascending order is specified.
• Dates shall be ordered from oldest to newest, if ascending order is specified.
"Table 156 — Entries in a collection sort dictionary" describes the entries in a collection sort dictionary.

Table 156 — Entries in a collection sort dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be CollectionSort for a collection sort dictionary. |
| S | name or | (Required) The name or names of fields that the interactive PDF processor shall |
| array | use to sort the items in the collection. If the value is a name, it identifies a field described in the parent collection dictionary. If the value is an array, each element of the array shall be a name that identifies a field described in the parent collection dictionary. The array form shall be used to allow additional fields to contribute to the sort, where each additional field shall be used to break ties. More specifically, if multiple collection item dictionaries have the same value for the first field named in the array, the values for successive fields named in the array shall be used for sorting, until a unique order is determined or until the named fields are exhausted. |  |
| A | boolean or | (Optional) If the value is a boolean, it specifies whether the interactive PDF |
| array | processor shall sort the items in the collection in ascending order (true) or descending order (false). If the value is an array, each element of the array shall be a boolean value that specifies whether the entry at the same index in the S array shall be sorted in ascending or descending order. If the number of entries in the A array is larger than the number of entries in the S array the extra entries in the A array shall be ignored. If the number of entries in the A array is less than the number of entries in the S array the missing entries in the A array shall be assumed to be true. Default value: true. |  |

Table 157 — Entries in a collection colors dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional; PDF 2.0) The type of PDF object that this dictionary describes; if present, shall be CollectionColors for a collection colors dictionary. |
| Background | array | (Optional; PDF 2.0) An array of three numbers in the range 0.0 to 1.0, representing a DeviceRGB colour used for the background of the view. |
| CardBackground | array | (Optional; PDF 2.0) An array of three numbers in the range 0.0 to 1.0, representing a DeviceRGB colour used for the background of the card. |
| CardBorder | array | (Optional; PDF 2.0) An array of three numbers in the range 0.0 to 1.0, representing a DeviceRGB colour used for the border of the card. |


| Key | Type | Value |
| --- | --- | --- |
| PrimaryText | array | (Optional; PDF 2.0) An array of three numbers in the range 0.0 to 1.0, representing a DeviceRGB colour used for the primary text in a navigator. |
| SecondaryText | array | (Optional; PDF 2.0) An array of three numbers in the range 0.0 to 1.0, representing a DeviceRGB colour used for other text in a navigator. |

When displaying a collection, an interactive PDF processor presents an initial view in which the available display area may be divided by a splitter bar into two areas; one area containing a display of the navigation controls of the collection as defined by the View and related entries of the collection dictionary, and one area containing a preview of the initial or currently selected document of the collection. The visibility, orientation or position of the splitter bar may be interactively adjusted by user action subsequent to its initial view as defined by the collection split dictionary, if provided.

Table 158 — Entries in a collection split dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional; PDF 2.0) The type of PDF object that this dictionary describes; if present, shall be CollectionSplit for a collection split dictionary. |
| Direction | name | (Optional; PDF 2.0) The orientation of the splitter bar. The following values are valid: |
| H | indicates that the window is split horizontally |  |
| V | indicates that the window is split vertically. |  |
| N | indicates that the window is not split. The entire window region shall be dedicated to the file navigation view. |  |
| Position | number | (Optional; PDF 2.0) The initial position of the splitter bar, specified as a percentage of the available window area. Values shall range from 0 to 100. The entry shall be ignored if Direction is set to N. |

#### 12.3.5.2 Collection hierarchical folders

Beginning with PDF 2.0, a portable collection can contain a Folders object for the purpose of organising files into a hierarchical structure. The structure is represented by a tree with a single root folder acting as the common ancestor for all other folders and files in the collection. The single root folder is referenced in the Folders entry of "Table 153 — Entries in a collection dictionary".

Table 159 — Entries in a folder dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional; PDF 2.0) The type of PDF object that this dictionary describes; if present, shall be Folder for a folder dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| ID | integer | (Required; PDF 2.0) A non-negative integer value representing the unique folder identification number. Two folders, in the same PDF document, shall not share the same ID value. The folder ID value appears as part of the name tree key of any file associated with this folder. A detailed description of the association between folder and files can be found after this table. |
| Name | text string | (Required; PDF 2.0) A file name representing the name of the folder. Two sibling folders shall not share the same name following case normalization. |

> **NOTE** Descriptions of file name and case normalization follow this table.

| Parent | dictionary | (Required for child folders; PDF 2.0) An indirect reference to the parent folder of this folder. This entry shall be absent for a root folder. |
| --- | --- | --- |
| Child | dictionary | (Required if the folder has any descendents; PDF 2.0) An indirect reference to the first child folder of this folder. |
| Next | dictionary | (Required for all but the last item at each level; PDF 2.0) An indirect reference to the next sibling folder at this level. Siblings should be ordered according to the collection Sort key ("Table 153 — Entries in a collection dictionary") or by folder name if no collection Sort key is present. |
| CI | dictionary | (Optional; PDF 2.0) The collection item dictionary. Beginning with |
| (uppercase ci) | PDF 1.7, a collection item dictionary shall contain the data described by the collection schema dictionary for a particular file in a collection (see 12.3.5, "Collections"). "Table 46 — Entries in a collection item dictionary" describes the entries in a collection item dictionary. |  |
| Desc | text string | (Optional; PDF 2.0) A text description associated with this folder. |
| CreationDate | date | (Optional; PDF 2.0) The date the folder was first created. |
| ModDate | date | (Optional; PDF 2.0) The date of the most recent change to immediate child files or folders of this folder. |
| Thumb | stream | (Optional; PDF 2.0) A stream object defining the thumbnail image for the folder See 12.3.4, "Thumbnail images". |
| Free | array | (Optional; only used by root folder; PDF 2.0) An array containing ID values that are not currently in use by the folder structure. The array shall contains zero or more pairs of numbers, a low value followed by a high value. Each pair represents an endpoint- inclusive range of values that are available for use when a new folder is added. Each low value shall be less than or equal to its corresponding high value. |

New values for the ID key shall be obtained by an interactive PDF processor by accessing the Free entry in the root folder. If an ID value is used from the Free entry array, the array shall be updated.


As previously described, the Name entry is a file name for a folder. A folder, as well as its associated files, have naming restrictions. Strings that conform to these restrictions are known as file names. A valid file name conforms to the following requirements:

• The string shall be a PDF text string.
• The string shall not contain any embedded NULL (U+0000) characters.
• The number of characters in the string shall be between 1 and 255 inclusive.
• The string shall not contain any of the eight special characters: SOLIDUS (U+002F) (/), REVERSE SOLIDUS (U+005C) (\), COLON (U+003A) (:), ASTERISK (U+002A) (*), QUOTATION MARK (U+0022) ("), LESS-THAN SIGN (U+003C) (<), GREATER-THAN SIGN (U+003E) (>) and VERTICAL LINE (U+007C) (|).
• The last character shall not be a FULL STOP (U+002E) (.).
An interactive PDF processor may choose to support invalid names or not. If not, an appropriate error message shall be provided.

In addition to the restriction on naming folders, as just described, it is further required that two file names in the same folder do not map to the same string following case normalization. Two file names that differ only in case are disallowed within the same folder. See "Unicode Standard Annex #21, Case Mappings" for information on case normalization.

The CI entry, a collection item dictionary, allows user-defined metadata to be associated with a folder, just as it does for embedded files in a collection.

Folders are indirect objects, and relationships between folders in the tree are specified using Parent, Child, and Next keys.

When folders are used, all files in the EmbeddedFiles name tree (see "Table 32 — Entries in the name dictionary") shall be treated as members of the folder structure by an interactive PDF processor. The association between files and folders is accomplished using a special naming convention on the key strings of the name tree. See 7.9.6, "Name trees", for a discussion of the key strings. If no folder structure is specified, interactive PDF processors should show all files in the collection in a flat list.

As previously mentioned, files in the EmbeddedFiles name tree are associated with folders by a special naming convention applied to the name tree key strings. Strings that conform to the following rules serve to associate the corresponding file with a folder:

• The name tree keys are PDF text strings.
• The first character, excluding any byte order marker, is LESS-THAN SIGN (U+003C) (<).
• The following characters shall be one or more digits (0 to 9) followed by the closing GREATERTHAN SIGN (U+003E) (>) •    The remainder of the string is a file name.
The section of the string enclosed by LESS-THAN SIGN GREATER-THAN SIGN(<>) is interpreted as a numeric value that specifies the ID value of the folder with which the file is associated. The value shall correspond to a folder ID. The section of the string following the folder ID tag shall represent the file name of the embedded file.


Files in the EmbeddedFiles name tree that do not conform to these rules shall be treated as associated with the root folder.

> **EXAMPLE 1** This example shows a collection dictionary representing an email in-box, where each item in the collection is  an  email  message.  The  actual  email  messages  are  contained  in  file  specification  dictionaries.  The organisational data associated with each email is described in a collection schema dictionary. Most actual organisational data (from, to, date, and subject) is provided in a collection item dictionary, but the size data comes from the embedded file parameter dictionary.

/Collection << /Type /Collection /Schema << /Type /CollectionSchema /from <</Subtype /S /N (From) /O 1 /V true /E false>> /to <</Subtype /S /N (To) /O 2 /V true /E false>> /date <</Subtype /D /N (Date received) /O 3 /V true /E false>> /subject <</Subtype /S /N (Subject) /O 4 /V true /E false>> /size <</Subtype /Size /N (Size) /O 5 /V true /E false>> >> /D (Doc1) /View /D /Sort <</S /date /A false>> >>

> **EXAMPLE 2** This example shows a collection item dictionary and a collection subitem dictionary. These dictionaries contain entries that correspond to the schema entries specified in the Example in 12.4.2, "Page labels". 7.11.6, "Collection items" specifies the collection item and collection subitem dictionaries.
 /CI << /Type /CollectionItem /from (Tom Jones) /to (Marry Jones) /subject << /Type /CollectionSubitem /P (Re:) /D (Let's have lunch on Friday!) >> /date (D:20050621094703-07’00) >>

### 12.3.6 Navigators

A portable collection can include an interactive layout, or presentation, for the collection contents (PDF 2.0) called a navigator. When a navigator is specified, a PDF processor should display that interactive layout and allow it to drive the presentation of the collection.

Navigators are specified by identifying a named layout, used to identify a presentation for the content within a collection. These interactive layouts provide more choice in presenting the collection contents than the simple views specified by the View key in the collection dictionary (see "Table 153 — Entries in a collection dictionary"). When a navigator dictionary is present, a PDF processor should use the value of the Layout entry to present the collection to the user.

Named layouts provide a number of options for presenting the contents of the collection to a user.
These options are provided to allow the choice of a navigator that is best suited to presenting the collection’s contents, including the folder structures and file attachments. The Layout key may consist of a single name value, to specify the named layout, or an array of names. When an array of names is provided, a PDF processor should select the first named layout it recognises. This mechanism is inherently extensible and allows inclusion of custom named layouts, but at least one of the values of


the Layout entry shall be one of the values listed in the entry (see "Table 160 — Entries in a navigator dictionary").

Table 160 — Entries in a navigator dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional; PDF 2.0) The type of PDF object that this dictionary describes; if present, shall be Navigator for a navigator dictionary. |
| Layout | name or | (Required; PDF 2.0) One or more names specifying the named layout of the |
| array of | navigator that should be used. When multiple names are provided, an interactive |  |
| names | PDF processor should present the first one it is capable of displaying in the order present in the array. One of the following names shall always be present, either singly or as the final entry in the array. |  |

# D Corresponding to the value of D in the View key in "Table 153 — Entries in a

collection dictionary".
T Corresponding to the value of T in the View key in "Table 153 — Entries in a collection dictionary".

# H Corresponding to the value of H in the View key in "Table 153 — Entries in a

collection dictionary".
| FilmStrip | A layout which displays a strip of thumbnails, providing an index to the file attachments within the collection. The selected attachment should be previewed alongside the index. |
| --- | --- |
| FreeForm | A layout which places thumbnails of the file attachments within the collection randomly in the view. |
| Linear | A layout which provides a large size preview of one file attachment in the collection and displays alongside the preview the metadata for the file attachment, including the name, description and other collection schema entries. |
| Tree | A layout presenting the contents of the collection in a tree view, showing the folder structure and the files as leaf nodes of the tree, akin to a traditional file system folder view. |

The D, T and H values for the Layout entry match those present in the View entry of a collection dictionary (see "Table 153 — Entries in a collection dictionary"). An interactive PDF processor should present the same display mode when encountering these values as it would if processing the View entry.

The FilmStrip layout describes a presentation of the collection contents in the form of a single strip of thumbnails. These thumbnails provide an index into the files and folders present within the collection.
When a user selects a file from the index, an interactive PDF processor should either display that file or provide a preview of the file.

> **NOTE 1** A common implementation for this is to have the strip of thumbnails across the bottom of the view and a large preview of the selected file or folder shown above it.
The FreeForm layout provides a simple layout, in which thumbnails for each item in the collection contents are displayed at a random location on the view. When a thumbnail is selected, an interactive PDF processor should display the attachment.


The Linear layout provides a view of an attachment, which is usually larger than just a thumbnail, with the metadata for the file displayed alongside it. An interactive PDF should display the first page of the file and should use the file schema and file specification dictionary to provide information about the attachment.

The Tree layout is intended to provide a classic folder view of the contents of a collection, akin to that found on many operating systems. An interactive PDF should present the folder structure as the nodes of the tree, with the attachments being presented as the leaves of the tree.

## 12.4 Page-level navigation

### 12.4.1 General

This subclause describes PDF facilities that enable the user to navigate from page to page within a document:

• Page labels for numbering or otherwise identifying individual pages (see 12.4.2, "Page labels").
• Article threads, which chain together items of content within the document that are logically connected but not physically sequential ( see 12.4.3, "Articles").
• Presentations that display the document in the form of a slide show, advancing from one page to the next either automatically or under user control ( see 12.4.4, "Presentations").
For another important form of page-level navigation, see 12.5.6.5, "Link annotations".

### 12.4.2 Page labels

Each page in a PDF document shall be identified by an integer page index that expresses the page’s relative position within the document. In addition, a document may optionally define page labels (PDF 1.3) to identify each page visually on the screen or in print. Page labels and page indices need not coincide: the indices shall be fixed, running consecutively through the document starting from 0 for the first page, but the labels may be specified in any way that is appropriate for the particular document.

> **NOTE 1** If the document begins with 12 pages of front matter numbered in Roman numerals and the remainder of the document is numbered in Arabic numerals, the first page would have a page index of 0 and a page label of i, the twelfth page would have index 11 and label xii, and the thirteenth page would have index 12 and label 1.
For purposes of page labelling, a document shall be divided into labelling ranges, each of which is a series of consecutive pages using the same numbering system. Labelling ranges shall not overlap, so that each page shall have only one label. Pages within a range shall be numbered sequentially in ascending order. A page’s label consists of a numeric portion based on its position within its labelling range, optionally preceded by a label prefix denoting the range itself.

> **NOTE 2** The pages in an appendix can be labelled with decimal numeric portions prefixed with the string A-; the resulting page labels would be A-1, A-2, and so on.
A document’s labelling ranges shall be defined by the PageLabels entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary"). The value of this entry shall be a number tree (7.9.7, "Number trees"), each of whose keys is the page index of the first page in a labelling range. The corresponding value shall be a page label dictionary defining the labelling characteristics for the pages


in that range. The tree shall include a value for page index 0. "Table 161 — Entries in a page label dictionary" shows the contents of a page label dictionary.

Table 161 — Entries in a page label dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be PageLabel for a page label dictionary. |
| S | name | (Optional) The numbering style that shall be used for the numeric portion of each page label: |

# D Decimal Arabic numerals

| R | Uppercase Roman numerals |
| --- | --- |
| r | Lowercase Roman numerals |

# A Uppercase letters (A to Z for the first 26 pages, AA to ZZ for the next 26,

and so on) a Lowercase letters (a to z for the first 26 pages, aa to zz for the next 26, and so on) There is no default numbering style; if no S entry is present, page labels shall consist solely of a label prefix with no numeric portion.

> **NOTE** If the P entry (next) specifies the label prefix Contents, each page is simply labelled Contents with no page number. (If the P entry is also missing or empty, the page label is an empty string.)

| P | text string | (Optional) The label prefix for page labels in this range. |
| --- | --- | --- |
| St | integer | (Optional) The value of the numeric portion for the first page label in the range. Subsequent pages shall be numbered sequentially from this value, which shall be greater than or equal to 1. Default value: 1. |

> **EXAMPLE** The following example shows a document with pages labelled i, ii, iii, iv, 1, 2, 3, A-8, A-9, …

1 0 obj <</Type /Catalog
| /PageLabels <</Nums [ | 0 <</S /r>> | %A number tree containing |
| --- | --- | --- |
| 4 <</S /D>> | %three page label dictionaries 7 <</S /D /P ( A- ) /St 8 >> ] >> … >> endobj |  |

### 12.4.3 Articles

Some types of documents may contain sequences of content items that are logically connected but not physically sequential.

> **EXAMPLE 1** A news story may begin on the first page of a newsletter and run over onto one or more nonconsecutive


interior pages.

To represent such sequences of physically discontiguous but logically related items, a PDF document may define one or more articles (PDF 1.1). The sequential flow of an article shall be defined by an article thread; the individual content items that make up the article are called beads on the thread.
Interactive PDF processors may provide navigation facilities to allow the user to follow a thread from one bead to the next.

The optional Threads entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary") holds an array of thread dictionaries ("Table 162 — Entries in a thread dictionary") defining the document’s articles. Each individual bead within a thread shall be represented by a bead dictionary ("Table 163 — Entries in a bead dictionary"). The thread dictionary’s F entry shall refer to the first bead in the thread; the beads shall be chained together sequentially in a doubly linked list through their N (next) and V (previous) entries. In addition, for each page on which article beads appear, the page object (see 7.7.3, "Page tree") shall contain a B entry whose value is an array of indirect references to the beads on the page, in drawing order.

Table 162 — Entries in a thread dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Thread for a thread dictionary. |
| F | dictionary | (Required; shall be an indirect reference) The first bead in the thread. |
| I | dictionary | (Optional) A thread information dictionary containing information about the thread, such as its title, author, and creation date. The contents of this dictionary shall conform to the syntax for the document information dictionary (see 14.3.3, "Document information dictionary"). |
| Metadata | stream | (Optional; PDF 2.0; shall be an indirect reference) A metadata stream containing information about the thread, such as its title, author, and creation date (see 14.3.2, "Metadata streams"). |

Table 163 — Entries in a bead dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Bead for a bead dictionary. |
| T | dictionary | (Required for the first bead of a thread; optional for all others; shall be an indirect reference) The thread to which this bead belongs. (PDF 1.1) This entry shall be permitted only for the first bead of a thread. (PDF 1.2) It shall be permitted for any bead but required only for the first. |
| N | dictionary | (Required; shall be an indirect reference) The next bead in the thread. In the last bead, this entry shall refer to the first bead. |
| V | dictionary | (Required; shall be an indirect reference) The previous bead in the thread. In the first bead, this entry shall refer to the last bead. |


| Key | Type | Value |
| --- | --- | --- |
| P | dictionary | (Required; shall be an indirect reference) The page object representing the page on which this bead appears. |
| R | rectangle | (Required) A rectangle specifying the location of this bead on the page in default user space. |

> **EXAMPLE 2** The following example shows a thread with three beads.

22 0 obj <</F 23 0 R /I <</Title (Man Bites Dog)>> >> endobj

23 0 obj <</T 22 0 R /N 24 0 R /V 25 0 R /P 8 0 R /R [158 247 318 905] >> endobj

24 0 obj <</T 22 0 R /N 25 0 R /V 23 0 R /P 8 0 R /R [322 246 486 904] >> endobj

25 0 obj <</T 22 0 R /N 23 0 R /V 24 0 R /P 10 0 R /R [157 254 319 903] >> endobj

### 12.4.4 Presentations

#### 12.4.4.1 General

Some interactive PDF processors may allow a document to be displayed in the form of a presentation or slide show, advancing from one page to the next either automatically or under user control. In addition, PDF 1.5 introduces the ability to advance between different states of the same page (12.4.4.2, "Sub-page navigation").

A page object (see 7.7.3, "Page tree") may contain two optional entries, Dur and Trans (PDF 1.1), to specify how to display that page in presentation mode. The Trans entry shall contain a transition dictionary describing the style and duration of the visual transition to use when moving from another page to the given page during a presentation. "Table 164 — Entries in a transition dictionary" shows the contents of the transition dictionary. (Some of the entries shown are needed only for certain transition styles, as indicated in the table.)


The Dur entry in the page object specifies the page’s display duration (also called its advance timing): the maximum length of time, in seconds, that the page shall be displayed before the presentation automatically advances to the next page.

> **NOTE 1** The user can advance the page manually before the specified time has expired.
If no Dur entry is specified in the page object, the page shall not advance automatically.

Table 164 — Entries in a transition dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Trans for a transition dictionary. |
| S | name | (Optional) The transition style that shall be used when moving to this page from another during a presentation. Default value: R. |
| Split | Two lines sweep across the screen, revealing the new page. The lines may be either horizontal or vertical and may move inward from the edges of the page or outward from the centre, as specified by the Dm and M entries, respectively. |  |
| Blinds | Multiple lines, evenly spaced across the screen, synchronously sweep in the same direction to reveal the new page. The lines may be either horizontal or vertical, as specified by the Dm entry. Horizontal lines move downward; vertical lines move to the right. |  |
| Box | A rectangular box sweeps inward from the edges of the page or outward from the centre, as specified by the M entry, revealing the new page. |  |
| Wipe | A single line sweeps across the screen from one edge to the other in the direction specified by the Di entry, revealing the new page. |  |
| Dissolve | The old page dissolves gradually to reveal the new one. |  |
| Glitter | Similar to Dissolve, except that the effect sweeps across the page in a wide band moving from one side of the screen to the other in the direction specified by the Di entry. |  |
| R | The new page simply replaces the old one with no special transition effect; the D entry shall be ignored. |  |
| Fly | (PDF 1.5) Changes are flown out or in (as specified by M), in the direction specified by Di, to or from a location that is offscreen except when Di is None. |  |
| Push | (PDF 1.5) The old page slides off the screen while the new page slides in, pushing the old page out in the direction specified by Di. |  |
| Cover | (PDF 1.5) The new page slides on to the screen in the direction specified by Di, covering the old page. |  |
| Uncover | (PDF 1.5) The old page slides off the screen in the direction specified by Di, uncovering the new page in the direction specified by Di. |  |
| Fade | (PDF 1.5) The new page gradually becomes visible through the old one. |  |
| D | number | (Optional) The duration of the transition effect, in seconds. Default value: 1. |


| Key | Type | Value |
| --- | --- | --- |
| Dm | name | (Optional; Split and Blinds transition styles only) The dimension in which the specified transition effect shall occur: |

# H Horizontal

| V | Vertical Default value: H. |  |
| --- | --- | --- |
| M | name | (Optional; Split, Box and Fly transition styles only) The direction of motion for the specified transition effect: |

# I Inward from the edges of the page (upper case i)

# O Outward from the centre of the page (upper case o)

Default value: I.

| Di | number | (Optional; Wipe, Glitter, Fly, Cover, Uncover and Push transition styles only) The direction |
| --- | --- | --- |
| or | in which the specified transition effect shall moves, expressed in degrees |  |
| name | counterclockwise starting from a left-to-right direction. (This differs from the page object’s Rotate entry, which is measured clockwise from the top.) If the value is a number, it shall be one of: |  |

# 0 Left to right

# 90 Bottom to top (Wipe only)

# 180 Right to left (Wipe only)

# 270 Top to bottom

# 315 Top-left to bottom-right (Glitter only)

If the value is a name, it shall be None, which is relevant only for the Fly transition when the value of SS is not 1.0.
Default value: 0.

| SS | number | (Optional; PDF 1.5; Fly transition style only) The starting or ending scale at which the changes shall be drawn. If M specifies an inward transition, the scale of the changes drawn shall progress from SS to 1.0 over the course of the transition. If M specifies an outward transition, the scale of the changes drawn shall progress from 1.0 to SS over the course of the transition Default: 1.0. |
| --- | --- | --- |
| B | boolean | (Optional; PDF 1.5; Fly transition style only) If true, the area that shall be flown in is rectangular and opaque. Default: false. |

> **NOTE 2** "Figure 76 — Presentation timing" illustrates the relationship between transition duration (D in the transition dictionary) and display duration (Dur in the page object). Note that the transition duration specified for a page (page 2 in the figure) governs the transition to that page from another page; the transition from the page is governed by the next page’s transition duration.

Figure 76 — Presentation timing


> **EXAMPLE** The following example shows the presentation parameters for a page to be displayed for 5 seconds. Before the page is displayed, there is a 3.5-second transition in which two vertical lines sweep outward from the centre to the edges of the page.

10 0 obj <</Type /Page /Parent 4 0 R /Contents 16 0 R /Dur 5 /Trans << /Type /Trans /D 3.5 /S /Split /Dm /V /M /O >> >> endobj

#### 12.4.4.2 Sub-page navigation

Sub-page navigation (PDF 1.5) provides the ability to navigate not only between pages but also between different states of the same page.

> **NOTE 1** A single page in a PDF presentation could have a series of bullet points that could be individually turned on and off. In such an example, the bullets would be represented by optional content (see 8.11.2, "Optional content groups"), and each state of the page would be represented as a navigation node.

> **NOTE 2** Interactive PDF processors need to save the state of optional content groups when a user enters presentation mode and restore it when presentation mode ends. This ensures, for example, that transient changes to bullets do not affect the printing of the document.
A navigation node dictionary (see "Table 165 — Entries in a navigation node dictionary") specifies actions to execute when the user makes a navigation request.

> **EXAMPLE** Pressing an arrow key.

The navigation nodes on a page form a doubly linked list by means of their Next and Prev entries. The primary node on a page shall be determined by the optional PresSteps entry in a page dictionary (see "Table 31 — Entries in a page object").

> **NOTE 3** An interactive PDF processor needs to respect navigation nodes only when in presentation mode (see 12.4.4, "Presentations").

Table 165 — Entries in a navigation node dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; shall be NavNode for a navigation node dictionary. |
| NA | dictionary | (Optional) An action (which may be the first in a sequence of actions) that shall be executed when a user navigates forward. |
| PA | dictionary | (Optional) An action (which may be the first in a sequence of actions) that shall be executed when a user navigates backward. |
| Next | dictionary | (Optional) The next navigation node, if any. |


| Key | Type | Value |
| --- | --- | --- |
| Prev | dictionary | (Optional) The previous navigation node, if any. |
| Dur | number | (Optional) The maximum number of seconds before the interactive PDF processor shall automatically advance forward to the next navigation node. If this entry is not specified, no automatic advance shall occur. |

An interactive PDF processor shall maintain a current navigation node. When a user navigates to a page, if the page dictionary has a PresSteps entry, the node specified by that entry shall become the current node. (Otherwise, there is no current node.) If the user requests to navigate forward (such as an arrow key press) and there is a current navigation node, the following shall occur:

a) The sequence of actions specified by NA (if present) shall be executed.
If NA specifies an action that navigates to another page, the following actions for navigating to another page take place, and Next should not be present.
b) The node specified by Next (if present) shall become the new current navigation node.
Similarly, if the user requests to navigate backward and there is a current navigation node, the following shall occur:

c) The sequence of actions specified by PA (if present) shall be executed.
If PA specifies an action that navigates to another page, the following actions for navigating to another page take place, and Prev should not be present.
d) The node specified by Prev (if present) shall become the new current navigation node.
Transition effects, similar to the page transitions described earlier, may be specified as transition actions that are part of the NA or PA sequence; see 12.6.4.15, "Transition actions".

If the user requests to navigate to another page (regardless of whether there is a current node) and that page’s dictionary contains a PresSteps entry, the following shall occur:

a) The navigation node represented by PresSteps shall become the current node.
b) If the navigation request was forward, or if the navigation request was for random acc ess (such as by
clicking on a link), the actions specified by NA shall be executed and the node specified by Next shall become the new current node, as described previously.
| If the navigation request was backward, the actions specified by | PA shall be exec | uted and the node |
| --- | --- | --- |
| specified by | Prev shall become the new current node, as described previously. |  |

c) The interactive PDF processor shall make the new page the current page and shall display it. Any page
transitions specified by the Trans entry of the page dictionary shall be performed.

## 12.5 Annotations

### 12.5.1 General

An annotation associates an object such as a note, link or rich media with a location on a page of a PDF document, or provides a way to interact with the user by means of the mouse and keyboard. PDF includes a wide variety of standard annotation types, described in detail in 12.5.6, "Annotation types".


Many of the standard annotation types may be displayed in either the open or the closed state. When closed, they appear on the page in some distinctive form, such as an icon, a box, or a rubber stamp, depending on the specific annotation type. When the user activates the annotation by clicking it, it exhibits its associated object, such as by opening a popup window displaying a text note ("Figure 77 — Open annotation") or by playing a sound or a movie.

Figure 77 — Open annotation Interactive PDF processors may permit the user to navigate through the annotations on a page by using the keyboard (in particular, the tab key). Beginning with PDF 1.5, PDF producers may make the navigation order explicit with the optional Tabs entry in a page object (see "Table 31 — Entries in a page object"). This entry has the following values:

• R (row order): Annotations shall be visited in rows running horizontally across the page. The direction within a row is defined by the Direction entry in the viewer preferences dictionary (see 12.2, "Viewer preferences"). The first annotation that shall be visited is the first annotation in the topmost row. When the end of a row is encountered, the first annotation in the next row shall be visited.
• C (column order): Annotations shall be visited in columns running vertically up and down the page. Columns shall be ordered by the Direction entry in the viewer preferences dictionary (see 12.2, "Viewer preferences"). The first annotation that shall be visited is the one at the top of the first column. When the end of a column is encountered, the first annotation in the next column shall be visited.
• S (structure order): Annotations shall be visited in the order in which they appear in the structure tree (see 14.7, "Logical structure"). The order for annotations that are not included in the structure tree is determined in a manner of the interactive PDF processor's choosing.
• A (annotation array order): All annotations shall be visited in the order in which they appear in the page Annots array. New in PDF 2.0. (See "Table 31 — Entries in a page object".) •    W (widgets order): Widget annotations shall be visited in the order in which they appear in the page Annots array, followed by other annotation types in row order. (See "Table 31 — Entries in


a page object".) New in PDF 2.0. For information about row order, see the R (row order) description.
These descriptions assume the page is being viewed in the orientation specified by the Rotate entry.
Conceptually, the behaviour of each annotation type may be implemented by a software module called an annotation handler. A PDF processor shall provide annotation handlers for all of the conforming annotation types. The set of annotation types is extensible. An interactive PDF processor shall provide certain expected behaviour for all annotation types that it does not recognise, as documented in 12.5.2, "Annotation dictionaries".

### 12.5.2 Annotation dictionaries

The optional Annots entry in a page object (see 7.7.3, "Page tree") holds an array of annotation
| dictionaries, each representing an annotation associated with the given page. | "Table 166 — Entries |
| --- | --- |
| common to all annotation dictionaries | " shows the required and optional entries that are common to all |

annotation dictionaries. The dictionary may contain additional entries specific to a particular
| annotation type; see the descriptions of individual annotation types in | 12.5.6, "Annotation types" for |
| --- | --- |
| details. A given annotation dictionary shall be referenced from the | Annots array of only one page. This |

requirement applies only to the annotation dictionary itself, not to subsidiary objects, which may be shared among multiple annotations.

Table 166 — Entries common to all annotation dictionaries

| Key | Type | Value |  |
| --- | --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Annot for an annotation dictionary. |  |
| Subtype | name | (Required) The type of annotation that this dictionary describes; see | "Table 171 — Annotation types" for specific values. |
| Rect | rectangle | (Required) The annotation rectangle, defining the location of the annotation on the page in default user space units. |  |
| Contents | text string | (Optional) Text that shall be displayed for the annotation or, if this type of annotation does not display text, an alternative description of the annotation’s contents in human-readable form. In either case, this text is useful when extracting the document’s contents in support of accessibility to users with disabilities or for other purposes (see 14.9.3, "Alternate descriptions"). See 12.5.6, "Annotation types" for more details on the meaning of this entry for each annotation type. |  |
| P | dictionary | (Optional except as noted below; PDF 1.3; not used in FDF files) An indirect reference to the page object with which this annotation is associated. This entry shall be present in screen annotations associated with rendition actions (PDF 1.5; see 12.5.6.18, "Screen annotations" and 12.6.4.14, "Rendition actions"). |  |
| NM | text string | (Optional; PDF 1.4) The annotation name, a text string uniquely identifying it among all the annotations on its page. |  |
| M | date or text | (Optional; PDF 1.1) The date and time when the annotation was most recently |  |
| string | modified. The format should be a date string as described in | 7.9.4, "Dates" but interactive PDF processors shall accept and display a string in any format. |  |


| Key | Type | Value |
| --- | --- | --- |
| F | integer | (Optional; PDF 1.1) A set of flags specifying various characteristics of the annotation (see 12.5.3, "Annotation flags"). Default value: 0. |
| AP | dictionary | (Optional; PDF 1.2) An appearance dictionary specifying how the annotation shall be presented visually on the page (see 12.5.5, "Appearance streams"). A PDF writer shall include an appearance dictionary when writing or updating the PDF file except for the two cases listed below. Every annotation (including those whose Subtype value is Widget, as used for form fields), except for the two cases listed below, shall have at least one appearance dictionary. |

• Annotations where the value of the Rect key consists of an array where the value at index 1 is equal to the value at index 3 and the value at index 2 is equal to the value at index 4.

> **NOTE** (2020) The bullet point above was changed from “or” to “and” in this document to match requirements in other published ISO PDF standards (such as PDF/A) .
• Annotations whose Subtype value is Popup, Projection or Link.

| AS | name | (Required if the appearance dictionary AP contains one or more subdictionaries; PDF 1.2) The annotation’s appearance state, which selects the applicable appearance stream from an appearance subdictionary (see 12.5.5, "Appearance streams"). |
| --- | --- | --- |
| Border | array | (Optional) An array specifying the characteristics of the annotation’s border, which shall be drawn as a rounded rectangle. (PDF 1.0) The array consists of three numbers defining the horizontal corner radius, vertical corner radius, and border width, all in default user space units. If the corner radii are 0, the border has square (not rounded) corners; if the border width is 0, no border is drawn. (PDF 1.1) The array may have a fourth element, an optional dash array defining a pattern of dashes and gaps that shall be used in drawing the border. The dash array shall be specified in the same format as in the line dash pattern parameter of the graphics state (see 8.4.3.6, "Line dash pattern"). The dash phase shall not be specified and shall be assumed to be 0. |

> **EXAMPLE** A Border value of [0 0 1 [3 2]] specifies a border 1 unit wide, with square corners, drawn with 3 -unit dashes alternating with 2 - unit gaps.

> **NOTE** (PDF 1.2) The dictionaries for some annotation types (such as free text and polygon annotations) can i nclude the BS entry. That entry specifies a border style dictionary that has more settings than the array specified for the Border entry. If an annotation dictionary includes the BS entry, then the Border entry is ignored. Default value: [0 0 1].


| Key | Type | Value |
| --- | --- | --- |
| C | array | (Optional; PDF 1.1) An array of numbers in the range 0.0 to 1.0, representing a colour used for the following purposes: The background of the annotation’s icon when closed The title bar of the annotation’s popup window The border of a link annotation The number of array elements determines the colour space in which the colour shall be defined: |

# 0 No colour; transparent

# 1 DeviceGray

# 3 DeviceRGB

# 4 DeviceCMYK

| StructParent | integer | (Required if the annotation is a structural content item; PDF 1.3) The integer key of the annotation’s entry in the structural parent tree (see 14.7.5.4, "Finding structure elements from content items"). |
| --- | --- | --- |
| OC | dictionary | (Optional; PDF 1.5) An optional content group or optional content membership dictionary (see 8.11, "Optional content") specifying the optional content properties for the annotation. Before the annotation is drawn, its visibility shall be determined based on this entry as well as the annotation flags specified in the F entry (see 12.5.3, "Annotation flags"). If it is determined to be invisible, the annotation shall not be drawn. (See 8.11.3.3, "Optional content in XObjects and annotations".) |
| AF | array of | (Optional; PDF 2.0) An array of one or more file specification dictionaries (7.11.3, |
| dictionaries | "File specification dictionaries") which denote the associated files for this annotation). See 14.13, "Associated files" and 14.13.9, "Associated files linked to an annotation dictionary" for more details. |  |
| ca | number | (Optional; PDF 2.0) When regenerating the annotation's appearance stream, this is the opacity value (11.2, "Overview of transparency") that shall be used for all nonstroking operations on all visible elements of the annotation in its closed state (including its background and border) but not the popup window that appears when the annotation is opened. Default value: 1.0 The specified value shall not be used if the annotation has an appearance stream (see 12.5.5, "Appearance streams"); in that case, the appearance stream shall specify any transparency. If no explicit appearance stream is defined for the annotation, and the processor is not able to regenerate the appearance, the annotation may be painted by implementation-dependent means that do not necessarily conform to the PDF imaging model; in this case, the effect of this entry is implementation-dependent as well. |


| Key | Type | Value |
| --- | --- | --- |
| CA | number | (Optional; PDF 1.4, PDF 2.0 for non-markup annotations) When regenerating the annotation's appearance stream, this is the opacity value (11.2, "Overview of transparency") that shall be used for stroking all visible elements of the annotation in its closed state, including its background and border, but not the popup window that appears when the annotation is opened. If a ca entry is not present in this dictionary, then the value of this CA entry shall also be used for nonstroking operations as well. Default Value: 1.0 The specified value shall not be used if the annotation has an appearance stream (12.5.5, "Appearance streams"); in that case, the appearance stream shall specify any transparency. If no explicit appearance stream is defined for the annotation, and the processor is not able to regenerate the appearance, the annotation may be painted by implementation-dependent means that do not necessarily conform to the PDF imaging model; in this case, the effect of this entry is implementation-dependent as well. |
| BM | name | (Optional; PDF 2.0)  The blend mode that shall be used when painting the annotation onto the page (see 11.3.5, "Blend Mode" and 11.6.3, "Specifying Blending Colour Space and Blend Mode"). If this key is not present, blending shall take place using the Normal blend mode. The value shall be a name object, designating one of the standard blend modes listed in "Table 134 — Standard separable blend modes" and "Table 135 — Standard non-separable blend modes" in 11.3.5, "Blend mode". |
| Lang | text string | (Optional; PDF 2.0) A language identifier overriding the document’s language identifier to specify the natural language for all text in the annotation except where overridden by other explicit language specifications (see 14.9.2, "Natural language specification"). |

A PDF reader shall render the appearance dictionary without regard to any other keys and values in the annotation dictionary and shall ignore the values of the C, IC, Border, BS, BE, BM, CA, ca, H, DA, Q, DS, LE, LL, LLE, and Sy keys.

> **NOTE** Requiring an appearance dictionary for each annotation ensures the reliable rendering of the annotations.

### 12.5.3 Annotation flags

The value of the annotation dictionary’s F entry is an integer interpreted as one-bit flags specifying various characteristics of the annotation. Bit positions within the flag word shall be numbered from low-order to high-order, with the lowest-order bit numbered 1. "Table 167 — Annotation flags" shows the meanings of the flags; all other bits of the integer shall be set to 0.


Table 167 — Annotation flags

Bit position Name Meaning

# 1 Invisible Applies only to annotations which do not belong to one of the standard

annotation types and for which no annotation handler is available. If set, do not render the unknown annotation and do not print it even if the Print flag is set. If clear, render such an unknown annotation using an appearance stream specified by its appearance dictionary, if any (see 12.5.5, "Appearance streams").

# 2 Hidden (PDF 1.2) If set, do not render the annotation or allow it to interact with the

user, regardless of its annotation type or whether an annotation handler is available.

> **NOTE 1** In cases where screen space is limited, the ability to hide and show annotations selectively can be used in combination with appearance streams (see 12.5.5, "Appearance streams") to render auxiliary popup information similar in function to online help systems.

# 3 Print (PDF 1.2) If set, print the annotation when the page is printed unless the

Hidden flag is also set. If clear, never print the annotation, regardless of whether it is rendered on the screen. If the annotation does not contain any appearance streams this flag shall be ignored.

> **NOTE 2** This can be useful for annotations representing interactive push-buttons, which would serve no meaningful purpose on the printed page.

# 4 NoZoom (PDF 1.3) If set, do not scale the annotation’s appearance to match the

magnification of the page. The location of the annotation on the page (defined by the upper-left corner of its annotation rectangle) shall remain fixed, regardless of the page magnification. See further discussion following this table.

# 5 NoRotate (PDF 1.3) If set, do not rotate the annotation’s appearance to match the

rotation of the page. The upper-left corner of the annotation rectangle shall remain in a fixed location on the page, regardless of the page rotation. See further discussion following this table.

# 6 NoView (PDF 1.3) If set, do not render the annotation on the screen or allow it to

interact with the user. The annotation may be printed (depending on the setting of the Print flag) but should be considered hidden for purposes of onscreen display and user interaction.

# 7 ReadOnly (PDF 1.3) If set, do not allow the annotation to interact with the user. The

annotation may be rendered or printed (depending on the settings of the NoView and Print flags) but should not respond to mouse clicks or change its appearance in response to mouse motions.
This flag shall be ignored for widget annotations; its function is subsumed by the ReadOnly flag of the associated form field (see "Table 226 — Entries common to all field dictionaries").

# 8 Locked (PDF 1.4) If set, do not allow the annotation to be deleted or its properties

(including position and size) to be modified by the user. However, this flag does not restrict changes to the annotation’s contents, such as the value of a form field.


Bit position Name Meaning

# 9 ToggleNoView (PDF 1.5) If set, invert the interpretation of the NoView flag for annotation

selection and mouse hovering, causing the annotation to be visible when the mouse pointer hovers over the annotation or when the annotation is selected.

# 10 LockedContents (PDF 1.7) If set, do not allow the contents of the annotation to be modified by

the user. This flag does not restrict deletion of the annotation or changes to other annotation properties, such as position and size.

If the NoZoom flag is set, the annotation shall always maintain the same fixed size on the screen and shall be unaffected by the magnification level at which the page itself is displayed. Similarly, if the NoRotate flag is set, the annotation shall retain its original orientation on the screen when the page is rotated (by changing the Rotate entry in the page object; see 7.7.3, "Page tree").

In either case, the annotation’s position is defined by the coordinates of the upper-left corner of its annotation rectangle, as defined by the Rect entry in the annotation dictionary and interpreted in the default user space of the page. When the default user space is scaled or rotated, the positions of the other three corners of the annotation rectangle are different in the altered user space than they were in the original user space. The PDF processor shall perform this alteration automatically. However, it shall not actually change the annotation’s Rect entry, which continues to describe the annotation’s relationship with the unscaled, unrotated user space.

> **NOTE** "Figure 78 — Coordinate adjustment with the NoRotate flag " shows how an annotation whose NoRotate flag is set remains upright when the page it is on is rotated 90 degrees clockwise. The upper-left corner of the annotation remains at the same point in default user space; the annotation pivots around that point.

Figure 78 — Coordinate adjustment with the NoRotate flag 12.5.4              Border styles

An annotation may optionally be surrounded by a border when displayed or printed. If present, the border shall be drawn completely inside the annotation rectangle. In PDF 1.1, the characteristics of the border shall be specified by the Border entry in the annotation dictionary ("Table 166 — Entries


common to all annotation dictionaries"). Beginning with PDF 1.2, the border characteristics for some types of annotations may instead be specified in a border style dictionary designated by the annotation’s BS entry. Such dictionaries may also be used to specify the width and dash pattern for the lines drawn by line, square, circle, and ink annotations. "Table 168 — Entries in a border style dictionary" summarises the contents of the border style dictionary. If neither the Border nor the BS entry is present, the border shall be drawn as a solid line with a width of 1 point.

Table 168 — Entries in a border style dictionary

| Key | Type | Value |  |
| --- | --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if |  |
| present, shall be | Border for a border style dictionary. |  |  |
| W | number | (Optional) The border width in points. If this value is 0, no border shall | be drawn. Default value: 1. |
| S | name | (Optional) The border style: |  |
| S | (Solid) A solid rectangle surrounding the annotation. Default value. |  |  |
| D | (Dashed) A dashed rectangle surrounding the annotation. The dash pattern may be specified by the D entry. |  |  |
| B | (Beveled) A simulated embossed rectangle that appears to be raised above the surface of the page. |  |  |
| I | (Inset) A simulated engraved rectangle that appears to be recessed below the surface of the page. |  |  |
| U | (Underline) A single line along the bottom of the annotation rectangle. An interactive PDF processor shall tolerate other border styles that it does not recognise and shall use the default value (which is S). |  |  |
| D | array | (Optional) A dash array defining a pattern of dashes and gaps that shall be used in drawing a dashed border (border style D in the S entry). The dash array shall be specified in the same format as in the line dash pattern parameter of the graphics state (see 8.4.3.6, "Line dash pattern"). The dash phase shall not be specified and shall be assumed to be 0. |  |

> **EXAMPLE** A D entry of [3 2] specifies a border drawn with 3-point dashes alternating with 2-point gaps.

Default value: [3].

Beginning with PDF 1.5, some annotations (square, circle, and polygon) may have a BE entry, which is a border effect dictionary that specifies an effect that shall be applied to the border of the annotations.
Beginning with PDF 1.6, free text annotations may also have a BE entry "Table 169 — Entries in a border effect dictionary" that describes the entries in a border effect dictionary.


Table 169 — Entries in a border effect dictionary

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Optional) A name representing the border effect to apply. Values are: |

# C The border should appear "cloudy"; that is, the border should be

drawn as a series of convex curved line segments in a manner that simulates the appearance of a cloud. The width and dash array specified by BS shall be honoured. Default value: S.
| S | No effect: the border shall be as described by the annotation dictionary’s BS entry. |  |
| --- | --- | --- |
| I | number | (Optional; valid only if the value of S is C) A number describing the intensity of the effect, in the range 0 to 2. Default value: 0. |

### 12.5.5 Appearance streams

Beginning with PDF 1.2, an annotation may specify one or more appearance streams as an alternative to the simple border and colour characteristics available in earlier versions. Appearance streams enable the annotation to be presented visually in different ways to reflect its interactions with the user.
Each appearance stream is a form XObject (see 8.10, "Form XObjects"): a self-contained content stream that shall be rendered inside the annotation rectangle.

The algorithm outlined in this subclause shall be used to map from the coordinate system of the appearance XObject (as defined by its Matrix entry; see "Table 93 — Additional entries specific to a Type 1 form dictionary") to the annotation’s rectangle in default user space:

Algorithm: appearance streams

| 1. | The appearance’s bounding box (specified by its BBox entry) shall be transformed, using Matrix, to produce a quadrilateral with arbitrary orientation. The transformed appearance box is the smallest upright rectangle that encompasses this quadrilateral. |  |  |  |
| --- | --- | --- | --- | --- |
| 2. | A matrix A shall be computed that scales and translates the transformed appearance box to align with the edges of the annotation’s rectangle (specified by the Rect entry). A maps the lower-left corner (the corner with the smallest x and y coordinates) and the upper-right corner (the corner with the greatest x and y coordinates) of the transformed appearance box to the corresponding corners of the annotation’s rectangle. |  |  |  |
| 3. | Matrix shall be concatenated with A to form a matrix AA that maps from the appearance’s coordinate system to the annotation’s rectangle in default user space: |  |  |  |
| 𝐴𝐴 | = | 𝑀𝑎𝑡𝑟𝑖𝑥 | × | 𝐴 |

The annotation may be further scaled and rotated if either the NoZoom or NoRotate flag is set (see 12.5.3, "Annotation flags"). Any transformation applied to the annotation as a whole shall be applied to the appearance within it.

Starting with PDF 1.4, an annotation appearance may include transparency. If the appearance’s stream dictionary does not contain a Group entry, it shall be treated as a non-isolated, non-knockout


transparency group. Otherwise, the isolated and knockout values specified in the group dictionary (see 11.6.6, "Transparency group XObjects") shall be used.

The transparency group shall be composited with a backdrop consisting of the page content along with any previously painted annotations, using the values of the BM, ca and CA entries in the annotation dictionary (see "Table 166 — Entries common to all annotation dictionaries ") and a soft mask of None.

> **NOTE 1** If a transparent annotation appearance is painted over an annotation that is drawn without
| using an appearance stream, the effect is implementation | -dependent. This is because such |
| --- | --- |
| annotations are sometimes drawn by means | that do not conform to the PDF imaging model. Also, |
| the effect of highlighting a transparent annotation appearance is implementation | -dependent. |

An annotation may define as many as three separate appearances:

• The normal appearance shall be used when the annotation is not interacting with the user. This appearance is also used for printing the annotation.
• The rollover appearance shall be used when the user moves the cursor into the annotation’s active area without pressing the mouse button.
• The down appearance shall be used when the mouse button is pressed or held down within the annotation’s active area.

> **NOTE 2** As used here, the term mouse denotes a generic pointing device that controls the location of a cursor on the screen and has at least one button that can be pressed, held down, and released.
See 12.6.3, "Trigger events" for further discussion.
The normal, rollover, and down appearances shall be defined in an appearance dictionary, which in
| turn is the value of the AP entry in the annotation dictionary (see | "Table 166 — Entries common to all |
| --- | --- |
| annotation dictionaries"). "Table 170 — Entries in an appearance dictionary | " shows the contents of the |

appearance dictionary.

Table 170 — Entries in an appearance dictionary

| Key | Type | Value |
| --- | --- | --- |
| N | stream or dictionary | (Required) The annotation’s normal appearance. |
| R | stream or dictionary | (Optional) The annotation’s rollover appearance. Default value: the value of the N entry. |
| D | stream or dictionary | (Optional) The annotation’s down appearance. Default value: the value of the N entry. |

Each entry in the appearance dictionary may contain either a single appearance stream or an
| appearance subdictionary | . In the latter case, the subdictionary shall define multiple appearance |  |
| --- | --- | --- |
| streams corresponding to different | appearance states | of the annotation. |

> **EXAMPLE** An annotation representing an interactive check box may have two appearance states named On and Off. Its appearance dictionary may be defined as

/AP <</N <</On formXObject1 /Off formXObject2 >> /D <</On formXObject3 /Off formXObject4


>>

>>

where formXObject1 and formXObject2 define the check box’s normal appearance in its checked and unchecked states, and formXObject3 and formXObject4 provide visual feedback, such as emboldening its outline, when the user clicks it. (No R entry is defined because no special appearance is needed when the user moves the cursor over the check box without pressing the mouse button.) The choice between the checked and unchecked appearance states is determined by the AS entry in the annotation dictionary (see "Table 166 — Entries common to all annotation dictionaries").

If a PDF processor does not have native support for a particular annotation type, the PDF processor shall render the annotation with its normal (N) appearance. PDF processors shall also attempt to provide reasonable behaviour (such as displaying nothing) if an annotation’s AS entry designates an appearance state for which no appearance is defined in the appearance dictionary.

For convenience in managing appearance streams that are used repeatedly, the AP entry in a PDF document’s name dictionary (see 7.7.4, "Name dictionary") may contain a name tree mapping name strings to appearance streams. The name strings have no standard meanings; no PDF objects may refer to appearance streams by name.

### 12.5.6 Annotation types

#### 12.5.6.1 General

PDF supports the standard annotation types listed in "Table 171 — Annotation types". The following subclauses describe each of these types in detail.

The values in the first column of "Table 171 — Annotation types" represent the value of the annotation dictionary’s Subtype entry. The third column indicates whether the annotation is a markup annotation, as described in 12.5.6.2, "Markup annotations" The subclause also provides more information about the value of the Contents entry for different annotation types.

Table 171 — Annotation types

| Annotation type | Description | Markup | Discussed in subclause |
| --- | --- | --- | --- |
| Text | Text annotation | Yes | 12.5.6.4, "Text annotations" |
| Link | Link annotation | No | 12.5.6.5, "Link annotations" |
| FreeText | (PDF 1.3) Free text annotation | Yes | 12.5.6.6, "Free text annotations" |
| Line | (PDF 1.3) Line annotation | Yes | 12.5.6.7, "Line annotations" |
| Square | (PDF 1.3) Square annotation | Yes | 12.5.6.8, "Square and circle annotations" |
| Circle | (PDF 1.3) Circle annotation | Yes | 12.5.6.8, "Square and circle annotations" |
| Polygon | (PDF 1.5) Polygon annotation | Yes | 12.5.6.9, "Polygon and polyline annotations" |


| Annotation type | Description | Markup | Discussed in subclause |
| --- | --- | --- | --- |
| PolyLine | (PDF 1.5) Polyline annotation | Yes | 12.5.6.9, "Polygon and polyline annotations" |
| Highlight | (PDF 1.3) Highlight annotation | Yes | 12.5.6.10, "Text markup annotations" |
| Underline | (PDF 1.3) Underline annotation | Yes | 12.5.6.10, "Text markup annotations" |
| Squiggly | (PDF 1.4) Squiggly-underline | Yes | 12.5.6.10, "Text markup annotations" annotation |
| StrikeOut | (PDF 1.3) Strikeout annotation | Yes | 12.5.6.10, "Text markup annotations" |
| Caret | (PDF 1.5) Caret annotation | Yes | 12.5.6.11, "Caret annotations" |
| Stamp | (PDF 1.3) Rubber stamp | Yes | 12.5.6.12, "Rubber stamp annotations" annotation |
| Ink | (PDF 1.3) Ink annotation | Yes | 12.5.6.13, "Ink annotations" |
| Popup | (PDF 1.3) Popup annotation | No | 12.5.6.14, "Popup annotations" |
| FileAttachment | (PDF 1.3) File attachment | Yes | 12.5.6.15, "File attachment |
| annotation | annotations" |  |  |
| Sound | (PDF 1.2; deprecated in PDF 2.0) | Yes | 12.5.6.16, "Sound annotations" Sound annotation |
| Movie | (PDF 1.2; deprecated in PDF 2.0) | No | 12.5.6.17, "Movie annotations" Movie annotation |
| Screen | (PDF 1.5) Screen annotation | No | 12.5.6.18, "Screen annotations" |
| Widget | (PDF 1.2) Widget annotation | No | 12.5.6.19, "Widget annotations" |
| PrinterMark | (PDF 1.4) Printer’s mark | No | 12.5.6.20, "Printer’s mark annotations" annotation |
| TrapNet | (PDF 1.3; deprecated in PDF 2.0) | No | 12.5.6.21, "Trap network annotations" Trap network annotation |
| Watermark | (PDF 1.6) Watermark annotation | No | 12.5.6.22, "Watermark annotations" |
| 3D | (PDF 1.6) 3D annotation | No | 13.6.2, "3D Annotations" |
| Redact | (PDF 1.7) Redact annotation | Yes | 12.5.6.23, "Redaction annotations" |
| Projection | (PDF 2.0) Projection annotation | Yes | 12.5.6.24, "Projection annotations" |
| RichMedia | (PDF 2.0) RichMedia annotation | No | 13.7.2, "RichMedia annotations" |

#### 12.5.6.2 Markup annotations

As mentioned in 12.5.2, "Annotation dictionaries", the meaning of an annotation’s Contents entry


varies by annotation type. Typically, it is the text that shall be displayed for the annotation or, if the annotation does not display text, an alternative description of the annotation’s contents in humanreadable form. In either case, the Contents entry is useful when extracting the document’s contents in support of accessibility to users with disabilities or for other purposes (see 14.9.3, "Alternate descriptions").

Many annotation types are defined as markup annotations because they are used primarily to mark up PDF documents (see "Table 172 — Additional entries in an annotation dictionary specific to markup annotations"). These annotations have text that appears as part of the annotation and may be displayed in other ways by an interactive PDF processor, such as in a comments pane. Markup annotations may be divided into the following groups:

• Free text annotations display text directly on the page. The annotation’s Contents entry specifies the displayed text.
• Most other markup annotations have an associated popup window that may contain text. The annotation’s Contents entry specifies the text that shall be displayed when the popup window is opened. These include text, line, square, circle, polygon, polyline, highlight, underline, squigglyunderline, strikeout, rubber stamp, caret, ink, and file attachment annotations.
• Sound annotations do not have a popup window but may also have associated text specified by the Contents entry.
• Projection annotations valid within the context of an associated run-time environment, such as an activated 3D model (see 12.5.6.24, "Projection annotations").

> **NOTE 1** The RC entry performs a similar role to the Contents entry except that the content’s textual representation is formatted. When both Contents and RC entries are present, it is expected that the contents of both entries are textually equivalent.
When separating text into paragraphs, a CARRIAGE RETURN (0Dh) shall be used and not, for example, a LINE FEED character (0Ah).

> **NOTE 2** A subset of markup annotations is called text markup annotations (12.5.6.10, "Text markup annotations").
The remaining annotation types are not considered markup annotations:

• The popup annotation type shall not appear by itself; it shall be associated with a markup annotation that uses it to display text.

> **NOTE 3** The Contents entry for a popup annotation is relevant only if it has no parent; in that case, it represents the text of the annotation.
• For all other annotation types (Link, Movie, Widget, RichMedia, PrinterMark, and TrapNet), the Contents entry may provide an alternative representation of the annotation’s contents in human-readable form, which is useful when extracting the document’s contents in support of accessibility to users with disabilities or for other purposes (see 14.9.3, "Alternate descriptions").
"Table 172 — Additional entries in an annotation dictionary specific to markup annotations" lists annotation dictionary entries that apply to all markup annotations.


Table 172 — Additional entries in an annotation dictionary specific to markup annotations

| Key | Type | Value |
| --- | --- | --- |
| T | text string | (Optional; PDF 1.1) The text label that shall be displayed in the title bar of the annotation’s popup window when open and active. This entry shall identify the user who added the annotation. |
| Popup | dictionary | (Optional; PDF 1.3) An indirect reference to a popup annotation for entering or editing the text associated with this annotation. |
| RC | text string | (Optional; PDF 1.5) A rich text string (see Adobe XML Architecture, |
| or text | XML Forms Architecture (XFA) Specification, version 3.3) that shall be |  |
| stream | displayed in the popup window when the annotation is opened. |  |
| CreationDate | date | (Optional; PDF 1.5) The date and time (7.9.4, "Dates") when the annotation was created. |
| IRT | dictionary | (Required if an RT entry is present, otherwise optional; PDF 1.5) A reference to the annotation that this annotation is "in reply to." Both annotations shall be on the same page of the document. The relationship between the two annotations shall be specified by the RT entry. If this entry is present in an FDF file (see 12.7.8, "Forms data format"), its type shall not be a dictionary but a text string containing the contents of the NM entry of the annotation being replied to, to allow for a situation where the annotation being replied to is not in the same FDF file. |
| Subj | text string | (Optional; PDF 1.5) Text representing a short description of the subject being addressed by the annotation. |
| RT | name | (Optional; meaningful only if IRT is present; PDF 1.6) A name specifying the relationship (the "reply type") between this annotation and one specified by IRT. Valid values are: |
| R | The annotation is considered a reply to the annotation specified by IRT. Interactive PDF processors shall not display replies to an annotation individually but together in the form of threaded comments. |  |
| Group | The annotation shall be grouped with the annotation specified by IRT; see the discussion following this Table. Default value: R. |  |


| Key | Type | Value |
| --- | --- | --- |
| IT | name | (Optional; PDF 1.6) A name describing the intent of the markup annotation. Intents allow interactive PDF processors to distinguish between different uses and behaviours of a single markup annotation type. If this entry is not present or its value is the same as the annotation type, the annotation shall have no explicit intent and should behave in a generic manner in an interactive PDF processor. Free text annotations ("Table 177 — Additional entries specific to a free text annotation"), line annotations ("Table 178 — Additional entries specific to a line annotation"), polygon annotations ("Table 181 — Additional entries specific to a polygon or polyline annotation"), (PDF 1.7) polyline annotations ("Table 181 — Additional entries specific to a polygon or polyline annotation") and stamp annotations (“Table 184 — Additional entries specific to a rubber stamp annotation") have defined intents, whose values are enumerated in the corresponding tables. |

In PDF 1.6, a set of annotations may be grouped so that they function as a single unit when a user interacts with them. The group consists of a primary annotation, which shall not have an IRT entry, and one or more subordinate annotations, which shall have an IRT entry that refers to the primary
| annotation and an RT entry whose value is | Group. |
| --- | --- |
| Some entries in the primary annotation are treated as | "group attributes" that shall apply to the group |
| as a whole; the corresponding entries in the subordinate annotations shall be ignored. These ent | ries |

are Contents (or RC and DS), M, C, T, Popup, CreationDate, Subj, and Open. Operations that manipulate any annotation in a group, such as movement, cut, and copy, shall be treated by interactive PDF processors as acting on the entire group.

> **NOTE 4** A primary annotation can have replies that are not subordinate annotations; that is, that do not have an RT value of Group.
2D markup annotations may be applied to specific views of the 3D artwork, using the ExData entry to identify the 3D annotation and the 3D view in that annotation. "Table 173 — Additional entries in markup annotation dictionaries specific to external data" lists additional markup annotation dictionary entries that apply to external data.


Table 173 — Additional entries in markup annotation dictionaries specific to external data

| Key | Type | Value |
| --- | --- | --- |
| ExData | dictionary | (Optional; PDF 1.7) An external data dictionary specifying data that shall be associated with the annotation. This dictionary contains the following entries: |
| Type | (Required) shall be ExData. |  |

> **NOTE** (2020) This document clarified that the Type key is always required.
Subtype (Required) a name specifying the type of data that the markup annotation shall be associated with. Values are: -    Markup3D (PDF 1.7) for a 3D comment. Additional entries in this dictionary are listed in "Table 309 — Additional entries specific to a 3D annotation" -    3DM (PDF 2.0) for a 3D measurement. Additional entries in this dictionary are listed in "Table 331 — Additional entries in a 3D measurement/markup dictionary for a 3D comment note and "Table 332 — Entries in the external data dictionary of a projection annotation".
- MarkupGeo (PDF 2.0) for geospatial markup. This Subtype does not define any additional entries.

#### 12.5.6.3 Annotation states

Beginning with PDF 1.5, annotations may have an author-specific state associated with them. The state is not specified in the annotation itself but in a separate text annotation that refers to the original annotation by means of its IRT ("in reply to") entry (see "Table 176 — Additional entries specific to a link annotation"). States shall be grouped into a number of state models, as shown in "Table 174 — Annotation states".

Table 174 — Annotation states

| State model | State | Description |
| --- | --- | --- |
| Marked | Marked | The annotation has been marked by the user. |
| Unmarked | The annotation has not been marked by the user (the default). |  |
| Review | Accepted | The user agrees with the change. |
| Rejected | The user disagrees with the change. |  |
| Cancelled | The change has been cancelled. |  |
| Completed | The change has been completed. |  |
| None | The user has indicated nothing about the change (the default). |  |

State changes made by a user shall be indicated in a text annotation with the following entries:

• The T entry (see "Table 172 — Additional entries in an annotation dictionary specific to markup annotations") shall specify the user.


• The IRT entry (see "Table 176 — Additional entries specific to a link annotation") shall refer to the original annotation.
• State and StateModel (see "Table 175 — Additional entries specific to a text annotation") shall update the state of the original annotation for the specified user.
Additional state changes shall be made by adding text annotations in reply to the previous reply for a given user.

#### 12.5.6.4 Text annotations

A text annotation represents a "sticky note" attached to a point in the PDF document. When closed, the annotation shall appear as an icon; when open, it shall display a popup window containing the text of the note in a font and size chosen by the interactive PDF processor. Text annotations shall not scale and rotate with the page; they shall behave as if the NoZoom and NoRotate annotation flags (see "Table 167 — Annotation flags") were always set. "Table 175 — Additional entries specific to a text annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 175 — Additional entries specific to a text annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Text for a text annotation. |
| Open | boolean | (Optional) A flag specifying whether the annotation shall initially be displayed open. Default value: false (closed). |
| Name | name | (Optional) The name of an icon that shall be used in displaying the annotation. Interactive PDF processors shall provide predefined icon appearances for at least the following standard names: Comment, Key, Note, Help, NewParagraph, Paragraph, Insert Additional names may be supported as well. Default value: Note. |
| State | text string | (Optional; PDF 1.5) The state to which the original annotation shall be set; see 12.5.6.3, "Annotation states". Default: Unmarked if StateModel is Marked; None if StateModel is Review. |
| StateModel | text string | (Required if State is present, otherwise optional; PDF 1.5) The state model corresponding to State; see 12.5.6.3, "Annotation states" |

> **EXAMPLE** The following example shows the definition of a text annotation.

22 0 obj <</Type /Annot /Subtype /Text /Rect [266 116 430 204] /Contents (The quick brown fox ate the lazy mouse .) >> endobj

#### 12.5.6.5 Link annotations

A link annotation represents either a hypertext link to a destination elsewhere in the document (see 12.3.2, "Destinations") or an action to be performed (12.6, "Actions"). "Table 176 — Additional entries


specific to a link annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 176 — Additional entries specific to a link annotation

| Key | Type | Value |  |
| --- | --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Link for a link annotation. |  |
| A | dictionary | (Optional; PDF 1.1) An action that shall be performed when the link annotation is activated (see 12.6, "Actions"). |  |
| Dest | array, name | (Optional; not permitted if an A entry is present) A destination that shall be |  |
| or byte string | displayed when the annotation is activated (12.3.2, "Destinations"). |  |  |
| H | name | (Optional; PDF 1.2) The annotation’s highlighting mode, the visual effect that shall be used when the mouse button is pressed or held down inside its active area: |  |
| N | (None) No highlighting. |  |  |
| I | (Invert) Invert the contents of the annotation rectangle. |  |  |
| O | (Outline) Invert the annotation’s border. |  |  |
| P | (Push) Display the annotation as if it were being pushed below the surface of the page. Default value: I. |  |  |
| PA | dictionary | (Optional; PDF 1.3) A URI action (see 12.6.4.8, "URI actions") formerly associated with this annotation. When a PDF processor changes an annotation from a URI (12.6.4.8, "URI actions") to a go-to action (12.6.4.2, "Go-To actions"), it may use this entry to save the data from the original URI action so that it can be changed back in case the target page for the go-to action is subsequently deleted. |  |
| QuadPoints | array | (Optional; PDF 1.6) An array of 8 | ×  𝑛 numbers specifying the coordinates of n quadrilaterals in default user space that comprise the region in which the link should be activated. The coordinates for each quadrilateral are given in the order: 𝑥1 𝑦1 𝑥2 𝑦2 𝑥3 𝑦3 𝑥4 𝑦4 |
| specifying the four vertices of the quadrilateral in counterclockwise order. For orientation purposes, such as when applying an underline border style, the bottom of a quadrilateral is the line formed by (x1, y1) and (x2, y2). If this entry is not present, or the PDF processor does not recognise it, or if any coordinates in the QuadPoints array lie outside the region specified by Rect then the activation region for the link annotation shall be defined by its Rect entry. |  |  |  |

> **NOTE** The last paragraph above was clarified in this document (2020).

BS dictionary (Optional; PDF 1.6) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the line width and dash pattern that shall be used in drawing the annotation’s border.

> **EXAMPLE** The following example shows a link annotation that jumps to a destination elsewher e in the document.

93 0 obj


<</Type /Annot /Subtype /Link /Rect [71 717 190 734] /Border [16 16 1] /Dest [3 0 R /FitR –4 399 199 533] >> endobj

#### 12.5.6.6 Free text annotations

A free text annotation (PDF 1.3) displays text directly on the page. Unlike an ordinary text annotation (see 12.5.6.4, "Text annotations"), a free text annotation has no open or closed state; instead of being displayed in a popup window, the text shall be always visible. "Table 177 — Additional entries specific to a free text annotation" shows the annotation dictionary entries specific to this type of annotation.
Subclause 12.7.4.3, "Variable text", describes the process of using these entries to generate the appearance of the text in these annotations.

Table 177 — Additional entries specific to a free text annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be FreeText for a free text annotation. |
| DA | string | (Required) The default appearance string that shall be used in formatting the text (see 12.7.4.3, "Variable text"). The annotation dictionary’s AP entry, if present, shall take precedence over the DA entry (see “Table 170 — Entries in an appearance dictionary” and 12.5.5, “Appearance streams”). |
| Q | integer | (Optional; PDF 1.4) A code specifying the form of quadding (justification) that shall be used in displaying the annotation’s text: |

# 0 Left-justified

# 1 Centred

# 2 Right-justified

Default value: 0 (left-justified).

| RC | text string | (Optional; PDF 1.5) A rich text string (see Adobe XML Architecture, XML Forms |
| --- | --- | --- |
| or text | Architecture (XFA) Specification, version 3.3) that shall be used to generate the |  |
| stream | appearance of the annotation. |  |

> **NOTE** As freetext annotations do not have an open state this cannot apply to the popup window as described for the RC key in "Table 172 — Additional entries in an annotation dictionary specific to markup annotations".

| DS | text string | (Optional; PDF 1.5) A default style string, as described in Adobe XML Architecture, XML Forms Architecture (XFA) Specification, version 3.3. |
| --- | --- | --- |
| CL | array | (Optional; meaningful only if IT is FreeTextCallout; PDF 1.6) An array of four or six numbers specifying a callout line attached to the free text annotation. Six numbers [x1 y1 x2 y2 x3 y3] represent the starting, knee point, and ending coordinates of the line in default user space, as shown in "Figure 79 — Free text annotation with callout". Four numbers [x1 y1 x2 y2] represent the starting and ending coordinates of the line. |


| Key | Type | Value |
| --- | --- | --- |
| IT | name | (Optional; PDF 1.6) A name describing the intent of the free text annotation (see also the IT entry in "Table 172 — Additional entries in an annotation dictionary specific to markup annotations"). The following values shall be valid: |
| FreeText | The annotation is intended to function as a plain free-text annotation. A plain free-text annotation is also known as a text box comment. |  |
| FreeTextCallout | The annotation is intended to function as a callout. The callout is associated with an area on the page through the callout line specified in CL. |  |
| FreeTextTypeWriter | The annotation is intended to function as a click-to-type or typewriter object and no callout line is drawn. Default value: FreeText |  |
| BE | dictionary | (Optional; PDF 1.6) A border effect dictionary (see "Table 169 — Entries in a border effect dictionary") used in conjunction with the border style dictionary specified by the BS entry. |
| RD | rectangle | (Optional; PDF 1.6) A set of four numbers describing the numerical differences between two rectangles: the Rect entry of the annotation and a rectangle contained within that rectangle. The inner rectangle is where the annotation’s text should be displayed. Any border styles and/or border effects specified by BS and BE entries, respectively, shall be applied to the border of the inner rectangle. The four numbers correspond to the differences in default user space between the left, top, right, and bottom coordinates of Rect and those of the inner rectangle, respectively. Each value shall be greater than or equal to 0. The sum of the top and bottom differences shall be less than the height of Rect, and the sum of the left and right differences shall be less than the width of Rect. |
| BS | dictionary | (Optional; PDF 1.6) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the line width and dash pattern that shall be used in drawing the annotation’s border. |
| LE | name | (Optional; meaningful only if CL is present; PDF 1.6) A name specifying the line ending style that shall be used in drawing the callout line specified in CL. The name shall specify the line ending style for the endpoint defined by the pairs of coordinates (x1, y1). "Table 179 — Line ending styles" shows the possible line ending styles. Default value: None. |

Figure 79 — Free text annotation with callout


#### 12.5.6.7 Line annotations

The purpose of a line annotation (PDF 1.3) is to display a single straight line on the page. When opened, it shall display a popup window containing the text of the associated note. "Table 178 — Additional entries specific to a line annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 178 — Additional entries specific to a line annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Line for a line annotation. |
| L | array | (Required) An array of four numbers, [x1 y1 x2 y2], specifying the starting and ending coordinates of the line in default user space. If the LL entry is present, this value shall represent the endpoints of the leader lines rather than the endpoints of the line itself; see "Figure 80 — Leader lines". |
| BS | dictionary | (Optional) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the width and dash pattern that shall be used in drawing the line. |
| LE | array | (Optional; PDF 1.4) An array of two names specifying the line ending styles that shall be used in drawing the line. The first and second elements of the array shall specify the line ending styles for the endpoints defined, respectively, by the first and second pairs of coordinates, (x1, y1 ) and (x2, y2 ), in the L array. "Table 179 — Line ending styles" shows the permitted values. Default value: [ /None /None ]. |
| IC | array | (Optional; PDF 1.4) An array of numbers in the range 0.0 to 1.0 specifying the interior colour that shall be used to fill the annotation’s line endings (see "Table 179 — Line ending styles"). The number of array elements shall determine the colour space in which the colour is defined: |

# 0 No colour; transparent

# 1 DeviceGray

# 3 DeviceRGB

# 4 DeviceCMYK

| LL | number | (Required if LLE is present, otherwise optional; PDF 1.6) The length of leader lines in default user space that extend from each endpoint of the line perpendicular to the line itself, as shown in "Figure 80 — Leader lines". A positive value shall mean that the leader lines appear in the direction that is clockwise when traversing the line from its starting point to its ending point (as specified by L); a negative value shall indicate the opposite direction. Default value: 0 (no leader lines). |
| --- | --- | --- |
| LLE | number | (Optional; PDF 1.6) A non-negative number that shall represents the length of leader line extensions that extend from the line proper 180 degrees from the leader lines, as shown in "Figure 80 — Leader lines". Default value: 0 (no leader line extensions). |


| Key | Type | Value |
| --- | --- | --- |
| Cap | boolean | (Optional; PDF 1.6) If true, the text specified by the Contents or RC entries shall be replicated as a caption in the appearance of the line, as shown in "Figure 81 — Lines with captions appearing as part of the line" and "Figure 82 — Line with a caption appearing as part of the offset". The text shall be rendered in a manner appropriate to the content, taking into account factors such as writing direction. Default value: false. |
| IT | name | (Optional; PDF 1.6) A name describing the intent of the line annotation (see also "Table 172 — Additional entries in an annotation dictionary specific to markup annotations"). Valid values shall be LineArrow, which means that the annotation is intended to function as an arrow, and LineDimension, which means that the annotation is intended to function as a dimension line. |
| LLO | number | (Optional; PDF 1.7) A non-negative number that shall represent the length of the leader |
| (capital | line offset, which is the amount of empty space between the endpoints of the |  |
| letters | annotation and the beginning of the leader lines. |  |

LLO)

| CP | name | (Optional; meaningful only if Cap is true; PDF 1.7) A name describing the annotation’s caption positioning. Valid values are Inline, meaning the caption shall be centred inside the line, and Top, meaning the caption shall be on top of the line. Default value: Inline |
| --- | --- | --- |
| Measure | dictionary | (Optional; PDF 1.7) A measure dictionary (see "Table 266 — Entries in a measure dictionary") that shall specify the scale and units that apply to the line annotation. |
| CO | array | (Optional; meaningful only if Cap is true; PDF 1.7) An array of two numbers that shall |
| (capital | specify the offset of the caption text from its normal position. The first value shall be |  |
| letters | the horizontal offset along the annotation line from its midpoint, with a positive value |  |
| CO) | indicating offset to the right and a negative value indicating offset to the left. The second value shall be the vertical offset perpendicular to the annotation line, with a positive value indicating a shift up and a negative value indicating a shift down. Default value: [0, 0] (no offset from normal positioning) |  |


Figure 80 — Leader lines "Figure 81 — Lines with captions appearing as part of the line" illustrates the effect of including a caption to a line annotation, which is specified by setting Cap to true.

Figure 81 — Lines with captions appearing as part of the line "Figure 82 — Line with a caption appearing as part of the offset" illustrates the effect of applying a caption to a line annotation that has a leader offset.


Figure 82 — Line with a caption appearing as part of the offset

Table 179 — Line ending styles

| Name | Appearance | Description |
| --- | --- | --- |
| Square | A square filled with the annotation’s interior colour, if any |  |

Circle A circle filled with the annotation’s interior colour, if any

Diamond A diamond shape filled with the annotation’s interior colour, if any

OpenArrow Two short lines meeting in an acute angle to form an open

arrowhead ClosedArrow Two short lines meeting in an acute angle as in the OpenArrow style and connected by a third line to form a triangular closed arrowhead

filled with the annotation’s interior colour, if any

None No line ending

Butt (PDF 1.5) A short line at the endpoint perpendicular to the line itself

ROpenArrow (PDF 1.5) Two short lines in the reverse direction from OpenArrow

RClosedArrow (PDF 1.5) A triangular closed arrowhead in the reverse direction from ClosedArrow

Slash (PDF 1.6) A short line at the endpoint approximately 30 degrees

clockwise from perpendicular to the line itself

#### 12.5.6.8 Square and circle annotations

Square and circle annotations (PDF 1.3) shall display, respectively, a rectangle or an ellipse on the page.
When opened, they shall display a popup window containing the text of the associated note. The rectangle or ellipse shall be inscribed within the annotation rectangle defined by the annotation dictionary’s Rect entry (see "Table 170 — Entries in an appearance dictionary").

"Figure 83 — Square and circle annotations" shows two annotations, each with a border width of 18 points. Despite the names square and circle, the width and height of the annotation rectangle need not be equal. "Table 180 — Additional entries specific to a square or circle annotation" shows the annotation dictionary entries specific to these types of annotations.


Figure 83 — Square and circle annotations

Table 180 — Additional entries specific to a square or circle annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Square or Circle for a square or circle annotation, respectively. |
| BS | dictionary | (Optional) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the line width and dash pattern that shall be used in drawing the rectangle or ellipse. |
| IC | array | (Optional; PDF 1.4) An array of numbers that shall be in the range 0.0 to 1.0 and shall specify the interior colour with which to fill the annotation’s rectangle or ellipse. The number of array elements determines the colour space in which the colour shall be defined: |

# 0 No colour; transparent

# 1 DeviceGray

# 3 DeviceRGB

# 4 DeviceCMYK

| BE | dictionary | (Optional; PDF 1.5) A border effect dictionary describing an effect applied to the border described by the BS entry (see "Table 169 — Entries in a border effect dictionary"). |
| --- | --- | --- |
| RD | rectangle | (Optional; PDF 1.5) A set of four numbers that shall describe the numerical differences between two rectangles: the Rect entry of the annotation and the actual boundaries of the underlying square or circle. Such a difference may occur in situations where a border effect (described by BE) causes the size of the Rect to increase beyond that of the square or circle. The four numbers shall correspond to the differences in default user space between the left, top, right, and bottom coordinates of Rect and those of the square or circle, respectively. Each value shall be greater than or equal to 0. The sum of the top and bottom differences shall be less than the height of Rect, and the sum of the left and right differences shall be less than the width of Rect. |


#### 12.5.6.9 Polygon and polyline annotations

Polygon annotations (PDF 1.5) display closed polygons on the page. Such polygons may have many vertices connected by straight lines. Polyline annotations (PDF 1.5) are similar to polygons, except that the first and last vertex are not implicitly connected.

Table 181 — Additional entries specific to a polygon or polyline annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Polygon or PolyLine for a polygon or polyline annotation, respectively. |
| Vertices | array | (Required unless a Path key is present, in which case it shall be ignored) An array of numbers specifying the alternating horizontal and vertical coordinates, respectively, of each vertex, in default user space. |
| LE | array | (Optional; meaningful only for polyline annotations) An array of two names that shall specify the line ending styles. The first and second elements of the array shall specify the line ending styles for the endpoints defined, respectively, by the first and last pairs of coordinates in the Vertices array. "Table 179 — Line ending styles" shows the allowed values. Default value: [/None /None]. |
| BS | dictionary | (Optional) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the width and dash pattern that shall be used in drawing the line. |
| IC | array | (Optional) An array of numbers that shall be in the range 0.0 to 1.0 and shall specify the interior color with which to fill the annotation’s line endings (see "Table 179 — Line ending styles"). The number of array elements determines the colour space in which the colour shall be defined: |

# 0 No colour; transparent

# 1 DeviceGray

# 3 DeviceRGB

# 4 DeviceCMYK

For Polyline annotations, the value of the IC key is used to fill only the line ending. However, for Polygon annotations, the value of the IC key is used to fill the entire shape, much as the F operator would fill a shape in a content stream.

BE dictionary (Optional; meaningful only for polygon annotations) A border effect dictionary that shall describe an effect applied to the border described by the BS entry (see "Table 169 — Entries in a border effect dictionary").


| Key | Type | Value |
| --- | --- | --- |
| IT | name | (Optional; PDF 1.6) A name that shall describe the intent of the polygon or polyline annotation (see also "Table 172 — Additional entries in an annotation dictionary specific to markup annotations"). The following values shall be valid: |
| PolygonCloud | The annotation is intended to function as a cloud object. |  |
| PolyLineDimension | (PDF 1.7) The polyline annotation is intended to function as a dimension. |  |
| PolygonDimension | (PDF 1.7) The polygon annotation is intended to function as a dimension. |  |
| Measure | dictionary | (Optional; PDF 1.7) A measure dictionary (see "Table 266 — Entries in a measure dictionary") that shall specify the scale and units that apply to the annotation. |
| Path | array | (Optional; PDF 2.0) An array of n arrays, each supplying the operands for a path building operator (m, l or c). If this key is present the Vertices key shall not be present. Each of the n arrays shall contain pairs of values specifying the points (x and y values) for a path drawing operation. The first array shall be of length 2 and specifies the operand of a moveto operator which establishes a current point. Subsequent arrays of length 2 specify the operands of lineto operators. Arrays of length 6 specify the operands for curveto operators. Each array is processed in sequence to construct the path. The current graphics state shall control the path width, dash pattern, etc. |

#### 12.5.6.10 Text markup annotations

Text markup annotations shall appear as highlights, underlines, strikeouts (all PDF 1.3), or jagged ("squiggly") underlines (PDF 1.4) in the text of a document. When opened, they shall display a popup window containing the text of the associated note. "Table 182 — Additional entries specific to text markup annotations" shows the annotation dictionary entries specific to these types of annotations.

Table 182 — Additional entries specific to text markup annotations

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Highlight, Underline, Squiggly, or StrikeOut for a highlight, underline, squiggly-underline, or strikeout annotation, respectively. |


| Key | Type | Value |  |
| --- | --- | --- | --- |
| QuadPoints | array | (Required) An array of 8 | ×  𝑛 numbers specifying the coordinates of n quadrilaterals in default user space. Each quadrilateral shall encompasses a word or group of contiguous words in the text underlying the annotation. The coordinates for each quadrilateral shall be given in the order: 𝑥1 𝑦1 𝑥2 𝑦2 𝑥3 𝑦3 𝑥4 𝑦4 specifying the quadrilateral’s four vertices in counterclockwise order (see "Figure 84 — QuadPoints specification"). The text shall be oriented with respect to the edge connecting points (x1, y1) and (x2, y2). |

Figure 84 — QuadPoints specification 12.5.6.11           Caret annotations

A caret annotation (PDF 1.5) is a visual symbol that indicates the presence of text edits. "Table 183 — Additional entries specific to a caret annotation" lists the entries specific to caret annotations.

Table 183 — Additional entries specific to a caret annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Caret for a caret annotation. |
| RD | rectangle | (Optional; PDF 1.5) A set of four numbers that shall describe the numerical differences between two rectangles: the Rect entry of the annotation and the actual boundaries of the underlying caret. Such a difference can occur. When a paragraph symbol specified by Sy is displayed along with the caret. The four numbers shall correspond to the differences in default user space between the left, top, right, and bottom coordinates of Rect and those of the caret, respectively. Each value shall be greater than or equal to 0. The sum of the top and bottom differences shall be less than the height of Rect, and the sum of the left and right differences shall be less than the width of Rect. |


| Key | Type | Value |
| --- | --- | --- |
| Sy | name | (Optional) A name specifying a symbol that shall be associated with the caret: |

# P A new paragraph symbol (¶) shall be associated with the

caret.
None No symbol shall be associated with the caret. Default value: None.

#### 12.5.6.12 Rubber stamp annotations

A rubber stamp annotation (PDF 1.3) displays text or graphics intended to look as if they were stamped on the page with a rubber stamp. When opened, it shall display a popup window containing the text of the associated note. "Table 184 — Additional entries specific to a rubber stamp annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 184 — Additional entries specific to a rubber stamp annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Stamp for a rubber stamp annotation. |
| Name | name | (Optional) The name of an icon that shall be used in displaying the annotation. PDF writers should include this entry and PDF readers should provide predefined icon appearances for at least the following standard names: Approved, Experimental, NotApproved, AsIs, Expired, NotForPublicRelease, Confidential, Final, Sold, Departmental, ForComment, TopSecret, Draft, ForPublicRelease Additional names may be supported as well. Default value: Draft. If the IT key is present and its value is not Stamp, this Name key shall not be present. |
| IT | name | (Optional; PDF 2.0) A name that shall describe the intent of the stamp. The following values shall be valid: |
| StampSnapshot | The appearance of this annotation has been taken from preexisting PDF content. |  |
| StampImage | The appearance of this annotation is an Image. |  |
| Stamp | The appearance of this annotation is a rubber stamp. Default value: Stamp |  |

#### 12.5.6.13 Ink annotations

An ink annotation (PDF 1.3) represents a freehand "scribble" composed of one or more disjoint paths.
When opened, it shall display a popup window containing the text of the associated note. "Table 185 — Additional entries specific to an ink annotation" shows the annotation dictionary entries specific to this type of annotation.


Table 185 — Additional entries specific to an ink annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Ink for an ink annotation. |
| InkList | array | (Required) An array of n arrays, each representing a stroked path. Each array shall be a series of alternating horizontal and vertical coordinates in default user space, specifying points along the path. When drawn, the points shall be connected by straight lines or curves in an implementation-dependent way. |
| BS | dictionary | (Optional) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the line width and dash pattern that shall be used in drawing the paths. |
| Path | array | (Optional; PDF 2.0) An array of n arrays, each supplying the operands for a path building operator (m, l or c). Each of the n arrays shall contain pairs of values specifying the points (x and y values) for a path drawing operation. The first array shall be of length 2 and specifies the operand of a moveto operator which establishes a current point. Subsequent arrays of length 2 specify the operands of lineto operators. Arrays of length 6 specify the operands for curveto operators. Each array is processed in sequence to construct the path. The current graphics state shall control the path width, dash pattern, etc. |

#### 12.5.6.14 Popup annotations

A popup annotation (PDF 1.3) displays text in a popup window for entry and editing. It shall not appear alone but is associated with a markup annotation, its parent annotation, and shall be used for editing the parent’s text. It shall have no appearance stream or associated actions of its own and shall be identified by the Popup entry in the parent’s annotation dictionary (see "Table 172 — Additional entries in an annotation dictionary specific to markup annotations"). "Table 186 — Additional entries specific to a popup annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 186 — Additional entries specific to a popup annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Popup for a popup annotation. |
| Parent | dictionary | (Optional; shall be an indirect reference) The parent annotation with which this popup annotation shall be associated. If this entry is present, the parent annotation’s Contents, M, C, and T entries (see "Table 170 — Entries in an appearance dictionary") shall override those of the popup annotation itself. |

> **NOTE** See also the Popup entry in "Table 172 — Additional entries in an annotation dictionary specific to markup annotations".


| Key | Type | Value |
| --- | --- | --- |
| Open | boolean | (Optional) A flag specifying whether the popup annotation shall initially be displayed open. Default value: false (closed). |

#### 12.5.6.15 File attachment annotations

A file attachment annotation (PDF 1.3) contains a reference to a file, which typically shall be embedded in the PDF file (see 7.11.4, "Embedded file streams").

> **NOTE** A table of data can use a file attachment annotation to link to a spreadsheet file based on that data; activating the annotation extracts the embedded file and gives the user an opportunity to view it or store it in the file system. "Table 187 — Additional entries specific to a file attachment annotation" shows the annotation dictionary entries specific to this type of annotation.
The Contents entry of the annotation dictionary may specify descriptive text relating to the attached file. Interactive PDF processors shall use this entry rather than the optional Desc entry (PDF 1.6) in the file specification dictionary (see "Table 43 — Entries in a file specification dictionary") identified by the annotation’s FS entry.

Table 187 — Additional entries specific to a file attachment annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be FileAttachment for a file attachment annotation. |
| FS | file specification | (Required) The file associated with this annotation. |
| Name | name | (Optional) The name of an icon that shall be used in displaying the annotation. PDF writers should include this entry and PDF readers should provide predefined icon appearances for at least the following standard names: Graph, PushPin, Paperclip, Tag Additional names may be supported as well. Default value: PushPin. |

#### 12.5.6.16 Sound annotations

The features described in this subclause are deprecated in PDF 2.0. They are superseded by the general multimedia framework described in 13.2, "Multimedia".

A sound annotation (PDF 1.2) is analogous to a text annotation except that instead of a text note, it contains sound recorded from the computer’s microphone or imported from a file. When the
| annotation is activated, the sound shall be played. The annotation shall behave like a text anno | tation in |
| --- | --- |
| most ways, with a different icon (by default, a speaker) to indicate that it represents a sound. | "Table |

188 — Additional entries specific to a sound annotation" shows the annotation dictionary entries specific to this type of annotation. Sound objects are discussed in 13.3, "Sounds".


Table 188 — Additional entries specific to a sound annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Sound for a sound annotation. |
| Sound | stream | (Required) A sound object defining the sound that shall be played when the annotation is activated (see 13.3, "Sounds"). |
| Name | name | (Optional) The name of an icon that shall be used in displaying the annotation. PDF writers should include this entry and PDF readers should provide predefined icon appearances for at least the standard names Speaker and Mic. Additional names may be supported as well. Default value: Speaker. |

#### 12.5.6.17 Movie annotations

The features described in this subclause are deprecated in PDF 2.0. They are superseded by the general multimedia framework described in 13.2, "Multimedia".

A movie annotation (PDF 1.2) contains animated graphics and sound to be presented on the computer screen and through the speakers. When the annotation is activated, the movie shall be played. "Table 189 — Additional entries specific to a movie annotation" shows the annotation dictionary entries specific to this type of annotation. Movies are discussed in 13.4, "Movies".

Table 189 — Additional entries specific to a movie annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Movie for a movie annotation. |
| T | text string | (Optional) The title of the movie annotation. Movie actions (12.6.4.10, "Movie actions") may use this title to reference the movie annotation. |
| Movie | dictionary | (Required) A movie dictionary that shall describe the movie’s static characteristics (see 13.4, "Movies"). |
| A | boolean or | (Optional) A flag or dictionary specifying whether and how to play the |
| dictionary | movie when the annotation is activated. If this value is a dictionary, it shall be a movie activation dictionary (see 13.4, "Movies") specifying how to play the movie. If the value is the boolean true, the movie shall be played using default activation parameters. If the value is false, the movie shall not be played. Default value: true. |  |

#### 12.5.6.18 Screen annotations

A screen annotation (PDF 1.5) specifies a region of a page upon which media clips may be played. It also serves as an object from which actions can be triggered. 12.6.4.14, "Rendition actions" discusses the relationship between screen annotations and rendition actions. "Table 190 — Additional entries


specific to a screen annotation" shows the annotation dictionary entries specific to this type of annotation.

Table 190 — Additional entries specific to a screen annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Screen for a screen annotation. |
| T | text string | (Optional) The title of the screen annotation. |
| MK | dictionary | (Optional) An appearance characteristics dictionary (see "Table 192 — Entries in an appearance characteristics dictionary"). The I entry of this dictionary provides the icon used in generating the appearance referred to by the screen annotation’s AP entry. |
| A | dictionary | (Optional; PDF 1.1) An action that shall be performed when the annotation is activated (see 12.6, "Actions"). |
| AA | dictionary | (Optional; PDF 1.2) An additional-actions dictionary defining the screen annotation’s behaviour in response to various trigger events (see 12.6.3, "Trigger events"). |

In addition to the entries in "Table 190 — Additional entries specific to a screen annotation", screen annotations may use the common entries in the annotation dictionary (see "Table 166 — Entries common to all annotation dictionaries") in the following ways:

• The P entry shall be used for a screen annotation referenced by a rendition action. It shall reference a valid page object, and the annotation shall be present in the page’s Annots array for the action to be valid.
• The AP entry refers to an appearance dictionary (see "Table 170 — Entries in an appearance dictionary") whose normal appearance provides the visual appearance for a screen annotation that shall be used for printing and default display when a media clip is not being played. If AP is not present, the screen annotation shall not have a default visual appearance and shall not be printed.

#### 12.5.6.19 Widget annotations

Interactive forms (see 12.7, "Forms") use widget annotations (PDF 1.2) to represent the appearance of fields and to manage user interactions. As a convenience, when a field has only a single associated widget annotation, the contents of the field dictionary (12.7.4, "Field dictionaries") and the annotation dictionary may be merged into a single dictionary containing entries that pertain to both a field and an annotation.

> **NOTE** This presents no ambiguity, since the contents of the two kinds of dictionaries do not conflict.
"Table 191 — Additional entries specific to a widget annotation" shows the annotation dictionary entries specific to this type of annotation; interactive forms and fields are discussed at length in 12.7.4, "Field dictionaries".


Table 191 — Additional entries specific to a widget annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Widget for a widget annotation. |
| H | name | (Optional) The annotation’s highlighting mode, the visual effect that shall be used when the mouse button is pressed or held down inside its active area: |
| N | (None) No highlighting. |  |
| I | (Invert) Invert the colours used to display the contents of the annotation rectangle. |  |
| O | (Outline) Stroke the colours used to display the annotation border. That is, for each colour channel in the colour space used for display of the annotation value, colour values shall be transformed by the function 𝑓 (𝑥) = 1 – 𝑥 for display. |  |
| P | (Push) Display the annotation’s down appearance, if any ( | see 12.5.5, "Appearance streams"). If no down appearance is defined, the contents of the annotation rectangle shall be offset to appear as if it were being pushed below the surface of the page. |
| T | (Toggle) Same as P (which is preferred). A highlighting mode other than P shall override any down appearance defined for the annotation. Default value: I. |  |
| MK | dictionary | (Optional) An appearance characteristics dictionary (see "Table 192 — Entries in an appearance characteristics dictionary") that shall be used in constructing a dynamic appearance stream specifying the annotation’s visual presentation on the page. The name MK for this entry is of historical significance only and has no direct meaning. |
| A | dictionary | (Optional; PDF 1.1) An action that shall be performed when the annotation is activated (see 12.6, "Actions"). |
| AA | dictionary | (Optional; PDF 1.2) An additional-actions dictionary defining the annotation’s behaviour in response to various trigger events (see 12.6.3, "Trigger events"). |
| BS | dictionary | (Optional; PDF 1.2) A border style dictionary (see "Table 168 — Entries in a border style dictionary") specifying the width and dash pattern that shall be used in drawing the annotation’s border. |
| Parent | dictionary | (Required if this widget annotation is one of multiple children in a field; optional otherwise) An indirect reference to the widget annotation’s parent field. A widget annotation may have at most one parent; that is, it can be included in the Kids array of at most one field |

The MK entry may be used to provide an appearance characteristics dictionary containing additional information for constructing the annotation’s appearance stream. "Table 192 — Entries in an appearance characteristics dictionary" shows the contents of this dictionary.


Table 192 — Entries in an appearance characteristics dictionary

| Key | Type | Value |
| --- | --- | --- |
| R | integer | (Optional) The number of degrees by which the widget annotation shall be rotated counterclockwise relative to the page. The value shall be a multiple of 90. Default value: 0. |
| BC | array | (Optional) An array of numbers that shall be in the range 0.0 to 1.0 specifying the colour of the widget annotation’s border. The number of array elements determines the colour space in which the colour shall be defined: |

# 0 No colour; transparent

# 1 DeviceGray

# 3 DeviceRGB

# 4 DeviceCMYK

| BG | array | (Optional) An array of numbers that shall be in the range 0.0 to 1.0 specifying the colour of the widget annotation’s background. The number of array elements shall determine the colour space, as described for BC. |
| --- | --- | --- |
| CA | text string | (Optional; button fields only) The widget annotation’s normal caption, which shall be displayed when it is not interacting with the user. Unlike the remaining entries listed in this Table, which apply only to widget annotations associated with push-button fields (see 12.7.5.2.2, "Push-buttons"), the CA entry may be used with any type of button field, including check boxes (see 12.7.5.2.3, "Check boxes") and radio buttons (12.7.5.2.4, "Radio buttons"). |
| RC | text string | (Optional; push-button fields only) The widget annotation’s rollover caption, which shall be displayed when the user rolls the cursor into its active area without pressing the mouse button. |
| AC | text string | (Optional; push-button fields only) The widget annotation’s alternate (down) caption, which shall be displayed when the mouse button is pressed within its active area. |
| I | stream | (Optional; push-button fields only; shall be an indirect reference) A form XObject defining the widget annotation’s normal icon, which shall be displayed when it is not interacting with the user. |
| RI | stream | (Optional; push-button fields only; shall be an indirect reference) A form XObject defining the widget annotation’s rollover icon, which shall be displayed when the user rolls the cursor into its active area without pressing the mouse button. |
| IX | stream | (Optional; push-button fields only; shall be an indirect reference) A form XObject defining the widget annotation’s alternate (down) icon, which shall be displayed when the mouse button is pressed within its active area. |
| IF | dictionary | (Optional; push-button fields only) An icon fit dictionary (see "Table 250 — Entries in an icon fit dictionary") specifying how the widget annotation’s icon shall be displayed within its annotation rectangle. If present, the icon fit dictionary shall apply to all of the annotation’s icons (normal, rollover, and alternate). |


| Key | Type | Value |
| --- | --- | --- |
| TP | integer | (Optional; push-button fields only) A code indicating where to position the text of the widget annotation’s caption relative to its icon: |

# 0 No icon; caption only

# 1 No caption; icon only

# 2 Caption below the icon

# 3 Caption above the icon

# 4 Caption to the right of the icon

# 5 Caption to the left of the icon

# 6 Caption overlaid directly on the icon

Default value: 0.

#### 12.5.6.20 Printer’s mark annotations

A printer’s mark annotation (PDF 1.4) represents a graphic symbol, such as a registration target, colour bar, or cut mark, that may be added to a page to assist production personnel in identifying components of a multiple-plate job and maintaining consistent output during production. See 14.11.3, "Printer’s marks" for further discussion.

#### 12.5.6.21 Trap network annotations

The features described in this subclause are deprecated in PDF 2.0.

A trap network annotation (PDF 1.3) may be used to define the trapping characteristics for a page of a PDF document.

> **NOTE** Trapping is the process of adding marks to a page along colour boundaries to avoid unwanted visual artifacts resulting from misregistration of colourants when the page is printed.
A page shall have no more than one trap network annotation, whose Subtype entry has the value TrapNet and which shall always be the last element in the page object’s Annots array (see 7.7.3.3, "Page objects"). See 14.11.6, "Trapping support" for further discussion.

#### 12.5.6.22 Watermark annotations

A watermark annotation (PDF 1.6) with a Fixed Print dictionary shall be used to represent graphics that are to be printed at a fixed size relative to the target media, and fixed relative position on the target media, regardless of the dimensions of that media. The FixedPrint entry of a watermark annotation dictionary (see "Table 193 — Additional entries specific to a watermark annotation") shall be a dictionary that contains values for specifying the size and position of the annotation (see "Table 194 — Entries in a fixed print dictionary").

Watermark annotations shall have no popup window nor other interactive elements. When displaying a watermark annotation on-screen, interactive PDF processors shall use the dimensions of the media box (see "Table 29 — Entries in the catalog dictionary") as the media dimensions so that the scroll and zoom behaviour is the same as for other annotations.

> **NOTE 1** Since many printing devices have nonprintable margins, such margins need to be taken into consideration when positioning watermark annotations near the edge of a page.


Table 193 — Additional entries specific to a watermark annotation

| Key | Type | Value |
| --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be Watermark for a watermark annotation. |
| FixedPrint | dictionary | (Optional) A fixed print dictionary (see "Table 194 — Entries in a fixed print dictionary") that specifies how this annotation shall be drawn relative to the dimensions of the target media. If this entry is not present, the annotation shall be drawn without any special consideration for the dimensions of the target media. If the dimensions of the target media are not known at the time of drawing, drawing shall be done relative to the dimensions specified by the page’s MediaBox entry (see "Table 31 — Entries in a page object"). |

Table 194 — Entries in a fixed print dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Required) Shall be FixedPrint. |
| Matrix | array | (Optional) The matrix used to transform the annotation’s rectangle before rendering. Default value: the identity matrix [1 0 0 1 0 0]. When positioning content near the edge of the media, this entry should be used to provide a reasonable offset to allow for unprintable margins. |
| H | number | (Optional) The amount to translate the associated content horizontally, as a percentage of the width of the target media (or if unknown, the width of the page’s MediaBox). 1.0 represents 100% and 0.0 represents 0%. Negative values should not be used, since they may cause content to be drawn off the media. Default value: 0. |
| V | number | (Optional) The amount to translate the associated content vertically, as a percentage of the height of the target media (or if unknown, the height of the page’s MediaBox). 1.0 represents 100% and 0.0 represents 0%. Negative values should not be used, since they may cause content to be drawn off the media. Default value: 0. |

When rendering a watermark annotation with a FixedPrint entry, the following behaviour shall occur:

• The annotation’s rectangle (as specified by its Rect entry) shall be translated to the origin and transformed by the Matrix entry of its FixedPrint dictionary to produce a quadrilateral with arbitrary orientation.
• The transformed annotation rectangle shall be defined as the smallest upright rectangle that encompasses this quadrilateral; it shall be used in place of the annotation rectangle referred to in steps 2 and 3 of "Algorithm: appearance streams" (see 12.5.5, "Appearance streams").
In addition, given a matrix B that maps a scaled and rotated page into the default user space, a new matrix shall be computed that cancels out B and translates the origin of the media (e.g., printed page)


to the origin of the default user space. This transformation shall be applied to ensure the correct scaling and alignment.

> **EXAMPLE** The following example shows a watermark annotation that prints a text string one inch from the left and one inch from the top of the printed page.

8 0 obj %Watermark appearance << /Length … /Subtype /Form /Resources … /BBox … >> stream … BT /F1 1 Tf 36 0 0 36 0 -36 Tm (Do Not Build) Tx ET … endstream endobj

| 9 0 obj | %Watermark annotation << /Rect … /Type /Annot /Subtype /Watermark /FixedPrint 10 0 R /AP <</N 8 0 R>> >> %in the page dictionary /Annots [9 0 R] |
| --- | --- |
| 10 0 obj | %Fixed print dictionary << /Type /FixedPrint |
| /Matrix [1 0 0 1 72 -72] | %Translate one inch right and one inch down /H 0 |
| /V 1.0 | %Translate the full height of the page vertically >> |

endobj

In situations other than the usual case where the PDF page size equals the media size, watermark annotations with a FixedPrint entry shall be printed in the following manner:

• When page tiling is selected in a PDF processor (that is, a single PDF page is printed on multiple pages), watermark annotations shall be printed at the specified size and position on each page to ensure that the content of the watermark annotation is present and legible on each printed page.
• When n-up printing is selected (that is, multiple PDF pages are printed on a single page), the annotations shall be printed at the specified size and shall be positioned as if the dimensions of the printed page were limited to a single portion of the page. This ensures that any content of the watermark annotation does not overlap content from other pages, thus rendering it illegible.

> **NOTE 2** There is no guarantee that the location of a fixed print annotation on any given output page will cover content.


#### 12.5.6.23 Redaction annotations

A redaction annotation (PDF 1.7) identifies content that is intended to be removed from the document.
The intent of redaction annotations is to enable the following process:

a) Content identification . A user applies redact annotations that specify the pieces or regions of content that
should be removed. Up until the next step is performed, the user can see, move and redefine these annotations.
b) Content removal . The user instructs the viewer application to apply the redact annotations, after which
the content in the area specified by the redact annotations is remov ed. In the removed content’s place, some marking appears to indicate the area has been redacted. Also, the redact annotations are removed from the PDF document.
Redaction annotations provide a mechanism for the first step in the redaction process (content identification). This allows content to be marked for redaction in a non -destructive way, thus enabling a review process for evaluating potential redactions prior to removing the specified content.

| Redaction annotations shall provide enough information to | be used in the second phase of the |
| --- | --- |
| redaction process (content removal). This phase is application | -specific and requires the PDF processor |
| to remove all content identified by the redaction annotation, as well as the annotation itself. | Interactive |

PDF processors that support redaction annotations shall provide a mechanism for applying content removal, and they shall remove all traces of the specified content. If a portion of an image is contained in a redaction region, that portion of the image data shall be destroyed; clipping or image masks shall not be used to hide that data. Such interactive PDF processors shall also be diligent in their consideration of all content th at can exist in a PDF document. “Table 195 — Additional entries specific to a redaction annotation” shows the additional entries specific to redaction annotations.

Table 195 — Additional entries specific to a redaction annotation

| Key | Type | Value |  |
| --- | --- | --- | --- |
| Subtype | name | (Required) The type of annotation that this dictionary describes; shall be | Redact for a redaction annotation. |
| QuadPoints | array | (Optional) An array of 8 x n numbers specifying the coordinates of n |  |
| quadrilaterals in default user space, as described in | "Table 182 — Additional entries specific to text markup annotations" for text markup annotations. If present, these quadrilaterals denote the content region that is intended to be removed. If this entry is not present, the Rect entry denotes the content region that is intended to be removed. |  |  |
| IC | array | (Optional) An array of three numbers in the range 0.0 to 1.0 specifying the components, in the DeviceRGB colour space, of the interior colour with which to fill the redacted region after the affected content has been removed. If this entry is absent, the interior of the redaction region is left transparent. This entry is ignored if the RO entry is present. |  |


| Key | Type | Value |
| --- | --- | --- |
| RO | stream | (Optional) A form XObject specifying the overlay appearance for this redaction annotation. After this redaction is applied and the affected content has been removed, the overlay appearance should be drawn such that its origin lines up with the lower-left corner of the annotation rectangle. This form XObject is not necessarily related to other annotation appearances, and may or may not be present in the AP dictionary. This entry takes precedence over the IC, OverlayText, DA, and Q entries. |
| OverlayText | text | (Optional) A text string specifying the overlay text that should be drawn over |
| string | the redacted region after the affected content has been removed. This entry is ignored if the RO entry is present. |  |
| Repeat | boolean | (Optional) If true, then the text specified by OverlayText should be repeated to fill the redacted region after the affected content has been removed. This entry is ignored if the RO entry is present. Default value: false. |
| DA | byte | (Required if OverlayText is present, ignored otherwise) The appearance string |
| string | that shall be used in formatting the overlay text when it is drawn after the affected content has been removed (see 12.7.4.3, "Variable text"). This entry is ignored if the RO entry is present. |  |
| Q | integer | (Optional) A code specifying the form of quadding (justification) that shall be used in laying out the overlay text: |

# 0 Left-justified

# 1 Centred

# 2 Right-justified

This entry is ignored if the RO entry is present. Default value: 0 (left-justified).

#### 12.5.6.24 Projection annotations

A projection annotation (PDF 2.0) is a markup annotation subtype (see 12.5.6.2, "Markup annotations") that has much of the functionality of other markup annotations. However, a projection annotation is only valid within the context of an associated run-time environment, such as an activated 3D model.

A projection annotation shall have a Subtype of Projection. The entries of a annotation dictionary for a projection annotation are those listed in "Table 166 — Entries common to all annotation dictionaries" and "Table 172 — Additional entries in an annotation dictionary specific to markup annotations".

Projection annotations provide a way to save 3D and other specialised measurements and comments as markup annotations. These measurements and comments then persist in the document.

When a projection annotation is used in conjunction with a 3D measurement (13.6.7.4, "3D measurements and projection annotations"), it has an ExData dictionary with a Subtype of 3DM. (See "Table 332 — Entries in the external data dictionary of a projection annotation".) Otherwise, the ExData dictionary is optional.

A projection annotation with a Rect entry that has zero height or zero width shall not have an AP dictionary.


12.5.6.25 3D and RichMedia annotations

3D and RichMedia annotations are defined in 13.6.2, "3D annotations" and 13.7.2, "RichMedia annotations".

## 12.6 Actions

### 12.6.1 General

In addition to jumping to a destination in the document, an annotation or outline item may specify an action (PDF 1.1) to perform, such as launching an application, playing a sound, changing an annotation’s appearance state. The optional A entry in the outline item dictionary (see "Table 151 — Entries in an outline item dictionary") and the dictionaries of some annotation types (see "Table 176 — Additional entries specific to a link annotation", "Table 190 — Additional entries specific to a screen annotation", "Table 191 — Additional entries specific to a widget annotation" and "Table 249 — Entries in an FDF field dictionary") specifies an action performed when the annotation or outline item is activated; in PDF 1.2, a variety of other circumstances may trigger an action as well (see 12.6.3, "Trigger events"). In addition, the optional OpenAction entry in a document’s catalog dictionary (7.7.2, "Document catalog dictionary") may specify an action that shall be performed when the document is opened. PDF includes a wide variety of standard action types, described in detail in 12.6.4, "Action types".

### 12.6.2 Action dictionaries

An action dictionary defines the characteristics and behaviour of an action. "Table 196 — Entries common to all action dictionaries" shows the required and optional entries that are common to all action dictionaries. The dictionary may contain additional entries specific to a particular action type; see the descriptions of individual action types in 12.6.4, "Action types" for details.

Table 196 — Entries common to all action dictionaries

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be Action for an action dictionary. |
| S | name | (Required) The type of action that this dictionary describes; see "Table 201 — Action types" for specific values. |
| Next | dictionary or array | (Optional; PDF 1.2) The next action or sequence of actions that shall be performed after the action represented by this dictionary. The value is either a single action dictionary or an array of action dictionaries that shall be performed in order; see |

> **NOTE 1** for further discussion.

> **NOTE 1** The action dictionary’s Next entry (PDF 1.2) allows sequences of actions to be chained together.
For example, the effect of clicking a link annotation with the mouse can be to play a sound, jump to a new page, and start up a movie. Note that the Next entry is not restricted to a single action but can contain an array of actions, each of which in turn can have a Next entry of its own. The actions can thus form a tree instead of a simple linked list. Actions within each Next array are executed in order, each followed in turn by any actions specified in its Next entry, and so on recursively. It is recommended that interactive PDF processors attempt to provide reasonable


behaviour in anomalous situations. For example, self-referential actions ought not be executed more than once, and actions that close the document or otherwise render the next action impossible ought to terminate the execution sequence. Applications need also provide some mechanism for the user to interrupt and manually terminate a sequence of actions.
PDF 1.5 introduces transition actions, which allow the control of drawing during a sequence of actions; see 12.6.4.15, "Transition actions".

> **NOTE 2** It is recommended that no action modify its own action dictionary or any other in the action tree in which it resides. The effect of such modification on subsequent execution of actions in the tree is undefined.

### 12.6.3 Trigger events

Selected types of annotations, page objects, or (beginning with PDF 1.3) interactive form fields may include an entry named AA that specifies an additional-actions dictionary (PDF 1.2) that extends the set
| of events that can trigger the execution of an action. In PDF 1.4, the document catalog dictionary ( | see |
| --- | --- |
| 7.7.2, "Document catalog dictionary | ") may also contain an AA entry for trigger events affecting the |

document as a whole. "Table 197 — Entries in an annotation’s additional-actions dictionary", “Table 198 — Entries in a page object’s additional-actions dictionary”, “Table 199 — Entries in a form field’s additional-actions dictionary” and "Table 200 — Entries in the document catalog’s additional-actions dictionary" show the contents of this type of dictionary.

PDF 1.5 introduces four trigger events in annotation’s additional-actions dictionary to support multimedia presentations:

• The PO and PC entries have a similar function to the O and C entries in the page object’s additional-actions dictionary (see "Table 197 — Entries in an annotation’s additional-actions dictionary"). However, associating these triggers with annotations allows annotation objects to be self-contained.

> **EXAMPLE** Annotations containing such actions can be copied or moved between pages without requiring page open/close actions to be changed.

• The PV and PI entries allow a distinction between pages that are open and pages that are visible.
At any one time, while more than one page may be visible, depending on the page layout.

> **NOTE 1** For these trigger events, the values of the flags specified by the annotation’s F entry (see 12.5.3, "Annotation flags") have no bearing on whether a given trigger event occurs.
For purposes of the trigger events E (enter), X (exit), D (down), and U (up), the term mouse denotes a generic pointing device with the following characteristics:

• A selection button that can be pressed, held down, and released. If there is more than one mouse button, the selection button is typically the left button.
• A notion of location — that is, an indication of where on the screen the device is pointing. Location is typically denoted by a screen cursor.
• A notion of focus — that is, which element in the document is currently interacting with the with the user. In many systems, this element is denoted by a blinking caret, a focus rectangle, or a colour change.


Table 197 — Entries in an annotation’s additional-actions dictionary

| Key | Type | Value |
| --- | --- | --- |
| E | dictionary | (Optional; PDF 1.2) An action that shall be performed when the cursor enters the annotation’s active area. |
| X | dictionary | (Optional; PDF 1.2) An action that shall be performed when the cursor exits the annotation’s active area. |
| D | dictionary | (Optional; PDF 1.2) An action that shall be performed when the mouse button is pressed inside the annotation’s active area. |
| U | dictionary | (Optional; PDF 1.2) An action that shall be performed when the mouse button is released inside the annotation’s active area. For backward compatibility, the A entry in an annotation dictionary, if present, takes precedence over this entry (see "Table 170 — Entries in an appearance dictionary"). |
| Fo | dictionary | (Optional; PDF 1.2; widget annotations only) An action that shall be performed when the annotation receives the input focus.. |
| Bl | dictionary | (Optional; PDF 1.2; widget annotations only) (Uppercase B, lowercase L) An action that shall be performed when the annotation loses the input focus. |
| PO | dictionary | (Optional; PDF 1.5) An action that shall be performed when the page containing the annotation is opened. |

> **EXAMPLE 1** When the user navigates to it from the next or previous page or by means of a link annotation or outline item.

The action shall be executed after the O action in the page’s additionalactions dictionary (see "Table 198 — Entries in a page object’s additionalactions dictionary") and the OpenAction entry in the document Catalog (see "Table 29 — Entries in the catalog dictionary"), if such actions are present.

PC dictionary (Optional; PDF 1.5) An action that shall be performed when the page containing the annotation is closed.

> **EXAMPLE 2** When the user navigates to the next or previous page, or follows a link annotation or outline item.

The action shall be executed before the C action in the page’s additionalactions dictionary (see "Table 198 — Entries in a page object’s additionalactions dictionary"), if present.

| PV | dictionary | (Optional; PDF 1.5) An action that shall be performed when the page containing the annotation becomes visible. |
| --- | --- | --- |
| PI | dictionary | (Optional; PDF 1.5) An action that shall be performed when the page containing the annotation is no longer visible in the interactive PDF processor’s user interface. |


Table 198 — Entries in a page object’s additional-actions dictionary

| Key | Type | Value |
| --- | --- | --- |
| O | dictionary | (Optional; PDF 1.2) An action that shall be performed when the page is opened (for example, when the user navigates to it from the next or previous page or by means of a link annotation or outline item). This action is independent of any that may be defined by the OpenAction entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary") and shall be executed after such an action. |
| C | dictionary | (Optional; PDF 1.2) An action that shall be performed when the page is closed (for example, when the user navigates to the next or previous page or follows a link annotation or an outline item). This action applies to the page being closed and shall be executed before any other page is opened. |

Table 199 — Entries in a form field’s additional-actions dictionary

| Key | Type | Value |
| --- | --- | --- |
| K | dictionary | (Optional; PDF 1.3) An ECMAScript action that shall be performed when the user modifies a character in a text field or combo box or modifies the selection in a scrollable list box. This action may check the added text for validity and reject or modify it. |
| F | dictionary | (Optional; PDF 1.3) An ECMAScript action that shall be performed before the field is formatted to display its value. This action may modify the field’s value before formatting. |
| V | dictionary | (Optional; PDF 1.3) An ECMAScript action that shall be performed when the field’s value is changed. This action may check the new value for validity. (The name V stands for "validate.") |
| C | dictionary | (Optional; PDF 1.3) An ECMAScript action that shall be performed to recalculate the value of this field when that of another field changes. (The name C stands for "calculate.") The order in which the document’s fields are recalculated shall be defined by the CO entry in the interactive form dictionary (see 12.7.3, "Interactive form dictionary"). |

Table 200 — Entries in the document catalog’s additional-actions dictionary

| Key | Type | Value |
| --- | --- | --- |
| WC | dictionary | (Optional; PDF 1.4) An ECMAScript action that shall be performed before closing a document. (The name WC stands for "will close.") |
| WS | dictionary | (Optional; PDF 1.4) An ECMAScript action that shall be performed before saving a document. (The name WS stands for "will save.") |
| DS | dictionary | (Optional; PDF 1.4) An ECMAScript action that shall be performed after saving a document. (The name DS stands for "did save.") |
| WP | dictionary | (Optional; PDF 1.4) An ECMAScript action that shall be performed before printing a document. (The name WP stands for "will print.") |


| Key | Type | Value |
| --- | --- | --- |
| DP | dictionary | (Optional; PDF 1.4) An ECMAScript action that shall be performed after printing a document. (The name DP stands for "did print.") |

Interactive PDF processors shall ensure the presence of such a device, or equivalent controls for simulating one, for the corresponding actions to be executed correctly. Mouse-related trigger events are subject to the following constraints:

• An E (enter) event may occur only when the mouse button is up.
• An X (exit) event may not occur without a preceding E event.
• A U (up) event may not occur without preceding E and D events.
• In the case of overlapping or nested annotations, entering a second annotation’s active area causes an X event to occur for the first annotation.

> **NOTE 2** The field-related trigger events K (keystroke), F (format), V (validate), and C (calculate) are not defined for button fields (see 12.7.5.2, "Button fields"). The effects of an action triggered by one of these events are limited only by the action itself and can occur outside the described scope of the event. For example, even though the F event is used to trigger actions that format field values prior to display, actions triggered by this event can perform a calculation or make any other modification to the document.
These field-related trigger events can occur either through user interaction or programmatically, such as in response to the NeedAppearances entry (deprecated in PDF 2.0) in the interactive form dictionary (see 12.7.3, "Interactive form dictionary"), importation of FDF data (12.7.8, "Forms data format"), or ECMAScript actions (12.6.4.17, "ECMAScript actions"). For example, the user’s modifying a field value can trigger a cascade of calculations and further formatting and validation for other fields in the document.

### 12.6.4 Action types

#### 12.6.4.1 General

PDF supports the standard action types listed in "Table 201 — Action types". The following subclauses describe each of these types in detail.

Table 201 — Action types

| Action type | Description | Discussed in subclause |
| --- | --- | --- |
| GoTo | Go to a destination in the current document. | 12.6.4.2, "Go-To actions" |
| GoToR | ("Go-to remote") Go to a destination in another | 12.6.4.3, "Remote Go-To actions" document. |
| GoToE | ("Go-to embedded"; PDF 1.6) Go to a destination in | 12.6.4.4, "Embedded Go-To actions" an embedded file. |
| GoToDp | ("Go-to document part"; PDF 2.0) Go to a specified | 12.6.4.5, "GoToDp action" DPart in the current document. |


| Action type | Description | Discussed in subclause |
| --- | --- | --- |
| Launch | Launch an application, usually to open a file. | 12.6.4.6, "Launch actions" |
| Thread | Begin reading an article thread. | 12.6.4.7, "Thread actions" |
| URI | Resolve a uniform resource identifier. | 12.6.4.8, "URI actions" |
| Sound | (PDF 1.2; deprecated in PDF 2.0) Play a sound. | 12.6.4.9, "Sound actions" |
| Movie | (PDF 1.2; deprecated in PDF 2.0) Play a movie. | 12.6.4.10, "Movie actions" |
| Hide | (PDF 1.2) Set an annotation’s Hidden flag. | 12.6.4.11, "Hide actions" |
| Named | (PDF 1.2) Execute a predefined action. | 12.6.4.12, "Named actions" |
| SubmitForm | (PDF 1.2) Send data to a uniform resource locator. | 12.7.6.2, "Submit-form action" |
| ResetForm | (PDF 1.2) Set fields to their default values. | 12.7.6.3, "Reset-form action" |
| ImportData | (PDF 1.2) Import field values from a file. | 12.7.6.4, "Import-data action" |
| SetOCGState | (PDF 1.5) Set the states of optional content groups. | 12.6.4.13, "Set-OCG-state actions" |
| Rendition | (PDF 1.5) Controls the playing of multimedia | 12.6.4.14, "Rendition actions" content. |
| Trans | (PDF 1.5) Updates the display of a document, using a | 12.6.4.15, "Transition actions" transition dictionary. |
| GoTo3DView | (PDF 1.6) Set the current view of a 3D annotation | 12.6.4.16, "Go-To-3D-View actions" |
| JavaScript | (PDF 1.3) Execute an ECMAScript script. | 12.6.4.17, "ECMAScript actions" |
| RichMediaExecute | (PDF 2.0; RichMedia annotation only) Specifies a | 12.6.4.18, "Rich-Media-Execute |
| command to be sent to the annotation’s handler. | actions" |  |

#### 12.6.4.2 Go-To actions

A go-to action changes the view to a specified destination (page, location, and magnification factor).
"Table 202 — Additional entries specific to a go-to action" shows the action dictionary entries specific to this type of action.

Table 202 — Additional entries specific to a go-to action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be GoTo for a go-to action. |
| D | name, byte | (Required) The destination to jump to (see 12.3.2, "Destinations"). string, or array |
| SD | array | (Optional; PDF 2.0) The structure destination to jump to (see 12.3.2.3, "Structure destinations"). If present, the structure destination should take precedence over destination in the D entry. |


> **NOTE** Specifying a go-to action in the A entry of a link annotation or outline item (see "Table 176 — Additional entries specific to a link annotation" and "Table 151 — Entries in an outline item dictionary") has the same effect as specifying the destination directly with the Dest entry. For example, the link annotation shown in the Example in 12.5.6.5, "Link annotations" which uses a go-to action, has the same effect as the one in the following Example, which specifies the destination directly. However, the go-to action is less compact and is not compatible with PDF 1.0; therefore, using a direct destination is preferable.

EXAMPLE

93 0 obj <</Type /Annot /Subtype /Link /Rect [71 717 190 734] /Border [16 16 1] /A <</Type /Action /S /GoTo /D [3 0 R /FitR –4 399 199 533] >> >> endobj

#### 12.6.4.3 Remote Go-To actions

A remote go-to action is similar to an ordinary go-to action but jumps to a destination in another PDF file instead of the current file. "Table 203 — Additional entries specific to a remote go-to action" shows the action dictionary entries specific to this type of action.

> **NOTE** Remote go-to actions cannot be used with embedded files; see 12.6.4.4, "Embedded Go-To actions"

Table 203 — Additional entries specific to a remote go-to action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be GoToR for a remote go-to action. |
| F | file specification | (Required) The file in which the destination shall be located. |
| D | name, byte string, | (Required) The destination to jump to (see 12.3.2, "Destinations"). |
| or array | If the value is an array defining an explicit destination (as described under 12.3.2.2, "Explicit destinations"), its first element shall be a page number within the remote document rather than an indirect reference to a page object in the current document. The first page shall be numbered 0. |  |
| SD | Array | (Optional; PDF 2.0) The structure destination to jump to (see 12.3.2.3, "Structure destinations"). The first element in the array shall be a byte string representing a structure element ID in the remote document, instead of an indirect reference to a structure element dictionary. If present, the structure destination should take precedence over destination in the D entry. |


| Key | Type | Value |
| --- | --- | --- |
| NewWindow | boolean | (Optional; PDF 1.2) A flag specifying whether to open the destination document in a new window. If this flag is false, the destination document replaces the current document in the same window. If this entry is absent, the interactive PDF processor should behave in accordance with its preference. |

#### 12.6.4.4 Embedded Go-To actions

An embedded go-to action (PDF 1.6) is similar to a remote go-to action but allows jumping to or from a PDF file that is embedded in another PDF file (see 7.11.4, "Embedded file streams"). Embedded files may be associated with file attachment annotations (see 12.5.6.15, "File attachment annotations") or with entries in the EmbeddedFiles name tree (see 7.7.4, "Name dictionary"). Embedded files may in turn contain embedded files. "Table 204 — Additional entries specific to an embedded go-to action" shows the action dictionary entries specific to embedded go-to actions.

> **NOTE** Embedded go-to actions work only for files of Type PDF.
Embedded go-to actions provide a complete facility for linking between a file in a hierarchy of nested embedded files and another file in the same or different hierarchy. The following terminology shall be used:

• The source is the document containing the embedded go-to action.
• The target is the document in which the destination lives.
• The T entry in the action dictionary is a target dictionary that locates the target in relation to the source, in much the same way that a relative path describes the physical relationship between two files in a file system. Target dictionaries may be nested recursively to specify one or more intermediate targets before reaching the final one. As the hierarchy is navigated, each intermediate target shall be referred to as the current document. Initially, the source is the current document.

> **NOTE** It is an error for a target dictionary to have an infinite cycle (for example, one where a target dictionary refers to itself). Interactive PDF processors need to attempt to detect such cases and refuse to execute the action if one is found.
• A child document shall be one that is embedded within another PDF file.
• The document in which a file is embedded shall be its parent.
• A root document is one that is not embedded in another PDF file. The target and source may be contained in root documents or embedded documents.

Table 204 — Additional entries specific to an embedded go-to action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be GoToE for an embedded go-to action. |
| F | file | (Optional) The root document of the target relative to the root |
| specification | document of the source. If this entry is absent, the source and target share the same root document. |  |


| Key | Type | Value |
| --- | --- | --- |
| D | name, byte | (Required) The destination in the target to jump to (see 12.3.2, |
| string, or array | "Destinations"). |  |
| NewWindow | boolean | (Optional) If true, the destination document should be opened in a new window; if false, the destination document should replace the current document in the same window. If this entry is absent, the interactive PDF processor should act according to its preference. |
| T | dictionary | (Optional if F is present; otherwise required) A target dictionary (see "Table 205 — Entries specific to a target dictionary") specifying path information to the target document. Each target dictionary specifies one element in the full path to the target and may have nested target dictionaries specifying additional elements. |

Table 205 — Entries specific to a target dictionary

| Key | Type | Value |
| --- | --- | --- |
| R | name | (Required) Specifies the relationship between the current document and the target (which may be an intermediate target). Valid values are P (the target is the parent of the current document) and C (the target is a child of the current document). |
| N | byte string | (Required if the value of R is C and the target is located in the EmbeddedFiles name tree; otherwise, it shall be absent) The name of the file in the EmbeddedFiles name tree. |
| P | integer or byte | (Required if the value of R is C and the target is associated with a file |
| string | attachment annotation; otherwise, it shall be absent) If the value is an integer, it specifies the page number (zero-based) in the current document containing the file attachment annotation. If the value is a string, it specifies a named destination in the current document that provides the page number of the file attachment annotation. |  |
| A | integer or text | (Required if the value of R is C and the target is associated with a file |
| string | attachment annotation; otherwise, it shall be absent) If the value is an integer, it specifies the index (zero-based) of the annotation in the Annots array (see "Table 31 — Entries in a page object") of the page specified by P. If the value is a text string, it specifies the value of NM in the annotation dictionary (see "Table 166 — Entries common to all annotation dictionaries"). |  |
| T | dictionary | (Optional) A target dictionary specifying additional path information to the target document. If this entry is absent, the current document is the target file containing the destination. |

> **EXAMPLE** The following example illustrates several possible relationships between source and target. Each object shown is an action dictionary for an embedded go-to action.

1 0 ob %Link to a child <</Type /Action /S /GoToE /D (Chapter 1) /T <</R /C /N (Embedded document)>> >>


endobj

2 0 obj %Link to the parent <</Type /Action /S /GoToE /D (Chapter 1) /T <</R /P>> >> endobj

3 0 obj %Link to a sibling <</Type /Action /S /GoToE /D (Chapter 1) /T <</R /P /T <</R /C /N (Another embedded document)>> >> >> endobj

4 0 obj %Link to an embedded file in an external document <</Type /Action /S /GoToE /D (Chapter 1) /F (someFile.pdf) /T <</R /C /N (Embedded document)>> >> endobj

5 0 obj %Link from an embedded file to a normal file <</Type /Action /S /GoToE /D (Chapter 1) /F (someFile.pdf) >> endobj

6 0 obj %Link to a grandchild <</Type /Action /S /GoToE /D (Chapter 1) /T <</R /C /N (Embedded document) /T <</R /C /P (A destination name) /A (annotName) >>  >> >> endobj

7 0 obj %Link to a niece/nephew through the source’s parent <</Type /Action /S /GoToE /D (destination) /T <</R /P /T <</R /C /N (Embedded document) /T <</R /C /P 3 /A (annotName) >> >> >>


>>

endobj

#### 12.6.4.5 GoToDp action

A GoToDp action changes the view to the Start page of a specified DPart (see "Table 409 — Entries in a DPart dictionary"). "Table 206 — Entries in a GoToDp dictionary" shows the action dictionary entries specific to this type of action.

Table 206 — Entries in a GoToDp dictionary

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required; PDF 2.0) The type of action that this dictionary describes; shall be GoToDp for a go-to document part action. |
| Dp | dictionary | (Required; PDF 2.0) The indirect reference to a DPart dictionary to go to. |

> **EXAMPLE 1** Using a GoToDp action reference

92 0 obj << /Parent 6 0 R /Start 100 0 R /End 101 0 R /Properties <</PartType (Cover)>> >> endobj

93 0 obj << /Type /Annot /Subtype /Link /Rect [71 717 190 734] /Border [16 16 1] /A << /Type /Action /S /GoToDp /Dp 92 0 R >> >> endobj

#### 12.6.4.6 Launch actions

A launch action launches an application or opens or prints a document. "Table 207 — Additional entries specific to a launch action" shows the action dictionary entries specific to this type of action.

Previously, Win, Mac, and Unix entries allowed the action dictionary to include platform-specific parameters for launching the designated application, however, they are now deprecated with PDF 2.0.
The F entry determines the file specification platform to be launched.


Table 207 — Additional entries specific to a launch action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Launch for a launch action. |
| F | file specification | (Required if none of the entries Win, Mac, or Unix is present) The application that shall be launched or the document that shall be opened or printed. If this entry is absent and the interactive PDF processor does not understand any of the alternative entries, it shall do nothing. |
| Win | dictionary | (Optional; deprecated in PDF 2.0) A dictionary containing Microsoft WindowsTM specific launch parameters (see "Table 208 — Entries in a Microsoft WindowsTM launch parameter dictionary"). |
| Mac | (undefined) | (Optional; deprecated in PDF 2.0) Mac OS–specific launch parameters; not yet defined. |
| Unix | (undefined) | (Optional; deprecated in PDF 2.0) UNIX-specific launch parameters; not yet defined. |
| NewWindow | boolean | (Optional; PDF 1.2) A flag specifying whether to open the destination document in a new window. If this flag is false, the destination document replaces the current document in the same window. If this entry is absent, the interactive PDF processor should behave in accordance with its current preference. This entry shall be ignored if the file designated by the F entry is not a PDF document. |

Table 208 — Entries in a Microsoft WindowsTM launch parameter dictionary

| Key | Type | Value |
| --- | --- | --- |
| F | byte string | (Required) The file name of the application that shall be launched or the document that shall be opened or printed, in standard Microsoft WindowsTM specific pathname format. If the name string includes a backslash character (\), the backslash shall itself be preceded by a backslash. This value shall be a simple string; it is not a file specification. |
| D | byte string | (Optional) A bye string specifying the default directory in standard DOS syntax. |

# O ASCII string (Optional) An ASCII string specifying the operation to perform:

| open | Open a document. |  |
| --- | --- | --- |
| print | Print a document. If the F entry designates an application instead of a document, this entry shall be ignored and the application shall be launched. Default value: open. |  |
| P | byte string | (Optional) A parameter string that shall be passed to the application designated by the F entry. This entry shall be omitted if F designates a document. |


#### 12.6.4.7 Thread actions

A thread action jumps to a specified bead on an article thread (see 12.4.3, "Articles"), in either the current document or a different one. "Table 209 — Additional entries specific to a thread action" shows the action dictionary entries specific to this type of action.

Table 209 — Additional entries specific to a thread action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Thread for a thread action. |
| F | file | (Optional) The file containing the thread. If this entry is absent, the thread is in |
| specification | the current file. |  |
| D | dictionary, | (Required) The destination thread, specified in one of the following forms: |
| integer, or | An indirect reference to a thread dictionary (see 12.4.3, "Articles"). In this case, |  |
| text string | the thread shall be in the current file. The index of the thread within the Threads array of its document’s catalog dictionary (see 7.7.2, "Document catalog dictionary"). The first thread in the array has index 0. The title of the thread as specified in its thread information dictionary (see "Table 162 — Entries in a thread dictionary"). If two or more threads have the same title, the one appearing first in the document catalog’s Threads array shall be used. |  |
| B | dictionary or | (Optional) The bead in the destination thread, specified in one of the following |
| integer | forms: An indirect reference to a bead dictionary (see 12.4.3, "Articles"). In this case, the thread shall be in the current file. The index of the bead within its thread. The first bead in a thread has index 0. |  |

The title of the thread as specified in its thread information dictionary (see "Table 162 — Entries in a thread dictionary"). If two or more threads have the same title, the one appearing first in the document catalog’s Threads array shall be used.

#### 12.6.4.8 URI actions

A uniform resource identifier (URI) is a string that identifies (resolves to) a resource on the Internet — typically a file that is the destination of a hypertext link, although it may also resolve to a query or other entity. (URIs are described in Internet RFC 3986.)

A URI action causes a URI to be resolved. "Table 210 — Additional entries specific to a URI action" shows the action dictionary entries specific to this type of action.


Table 210 — Additional entries specific to a URI action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be URI for a URI action. |
| URI | ASCII | (Required) The uniform resource identifier to resolve, encoded in UTF-8. string |
| IsMap | boolean | (Optional) A flag specifying whether to track the mouse position when the URI is resolved (see the discussion following this Table). Default value: false. This entry applies only to actions triggered by the user’s clicking an annotation; it shall be ignored for actions associated with outline items or with a document’s OpenAction entry. |

If the IsMap flag is true and the user has triggered the URI action by clicking an annotation, the coordinates of the mouse position at the time the action has been triggered shall be transformed from device space to user space and then offset relative to the upper-left corner of the annotation rectangle (that is, the value of the Rect entry in the annotation with which the URI action is associated).

> **EXAMPLE 1** If the mouse coordinates in user space are (xm, ym ) and the annotation rectangle extends from ( llx, lly ) at the lower-left to (urx, ury ) at the upper-right, the final coordinates (xf, yf ) are as follows:

| (𝑥𝑓  =  𝑥𝑚 | −  𝑙𝑙𝑥) |
| --- | --- |
| 𝑦f =  ur𝑦 | −  𝑦m |

If the resulting coordinates (xf, yf ) are fractional, they shall be rounded to the nearest integer values.
They shall then be appended to the URI to be resolved, separated by COMMAS (2Ch) and preceded by a QUESTION MARK (3Fh), as shown in this example:

> **EXAMPLE 2** http://www.iso.org/intro?100,200

To support URI actions, a PDF document’s catalog dictionary (see 7.7.2, "Document catalog dictionary") may include a URI entry whose value is a URI dictionary. Only one entry shall be defined for such a dictionary (see "Table 211 — Entry in a URI dictionary").

Table 211 — Entry in a URI dictionary

| Key | Type | Value |
| --- | --- | --- |
| Base | ASCII | (Optional) The base URI that shall be used in resolving relative URI |
| string | references. URI actions within the document may specify URIs in partial |  |
| form, to be interpreted relative to this base address. If no base URI | is specified, such partial URIs shall be interpreted relative to the location of the document itself. The use of this entry is parallel to that of the body element <BASE>, as described in the HTML 4.01 Specification. |  |

> **NOTE** The Base entry allows the URI of the document to be recorded in situations in which the document is accessed out of context. For example, if a document has been moved to a new location but contains relative links to other documents that have not been moved, the Base entry could be used to refer such links to the true location of the other documents, rather than that of the moved document.


#### 12.6.4.9 Sound actions

The features described in this subclause are deprecated with PDF 2.0. They are superseded by the general multimedia framework described in 13.2, "Multimedia".

A sound action (PDF 1.2) plays a sound through the computer’s speakers. "Table 212 — Additional entries specific to a sound action" shows the action dictionary entries specific to this type of action.
Sounds are discussed in 13.3, "Sounds".

Table 212 — Additional entries specific to a sound action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Sound for a sound action. |
| Sound | stream | (Required) A sound object defining the sound that shall be played (see 13.3, "Sounds"). |
| Volume | number | (Optional) The volume at which to play the sound, in the range -1.0 to 1.0. Default value: 1.0. |
| Synchronous | boolean | (Optional) A flag specifying whether to play the sound synchronously or asynchronously. If this flag is true, the interactive PDF processor retains control, allowing no further user interaction other than cancelling the sound, until the sound has been completely played. Default value: false. |
| Repeat | boolean | (Optional) A flag specifying whether to repeat the sound indefinitely. If this entry is present, the Synchronous entry shall be ignored. Default value: false. |
| Mix | boolean | (Optional) A flag specifying whether to mix this sound with any other sound already playing. If this flag is false, any previously playing sound shall be stopped before starting this sound; this can be used to stop a repeating sound (see Repeat). Default value: false. |

#### 12.6.4.10 Movie actions

The features described in this subclause are deprecated with PDF 2.0. They are superseded by the general multimedia framework described in 13.2, "Multimedia".

A movie action (PDF 1.2) can be used to play a movie in a floating window or within the annotation rectangle of a movie annotation (see 12.5.6.17, "Movie annotations" and 13.4, "Movies"). The movie annotation shall be associated with the page that is the destination of the link annotation or outline item containing the movie action, or with the page object with which the action is associated.

> **NOTE** A movie action by itself does not guarantee that the page the movie is on will be displayed before attempting to play the movie; such page change actions are done explicitly.
The contents of a movie action dictionary are identical to those of a movie activation dictionary (see "Table 307 — Entries in a movie activation dictionary"), with the additional entries shown in "Table


213 — Additional entries specific to a movie action". The contents of the activation dictionary associated with the movie annotation provide the default values. Any information specified in the movie action dictionary overrides these values.

Table 213 — Additional entries specific to a movie action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Movie for a movie action. |
| Annotation | dictionary | (Optional) An indirect reference to a movie annotation identifying the movie that shall be played. The dictionary shall include either an Annotation or a T entry but not both. |
| T | text string | (Optional) The title of a movie annotation identifying the movie that shall be played. The dictionary shall include either an Annotation or a T entry but not both. |
| Operation | name | (Optional) The operation that shall be performed on the movie: |
| Play | Start playing the movie, using the play mode specified by the dictionary’s Mode entry (see "Table 307 — Entries in a movie activation dictionary"). If the movie is currently paused, it shall be repositioned to the beginning before playing (or to the starting point specified by the dictionary’s Start entry, if present). |  |
| Stop | Stop playing the movie. |  |
| Pause | Pause a playing movie. |  |
| Resume | Resume a paused movie. Default value: Play. |  |

#### 12.6.4.11 Hide actions

A hide action (PDF 1.2) hides or shows one or more annotations on the screen by setting or clearing their Hidden flags (see 12.5.3, "Annotation flags"). This type of action can be used in combination with appearance streams and trigger events (see 12.5.5, "Appearance streams" and 12.6.3, "Trigger events") to display popup help information on the screen.

> **NOTE** The E (enter) and X (exit) trigger events in an annotation’s additional-actions dictionary can be used to show and hide the annotation when the user rolls the cursor in and out of its active area on the page. This can be used to pop up a help label, or tool tip, describing the effect of clicking at that location on the page.
"Table 214 — Additional entries specific to a hide action" shows the action dictionary entries specific to this type of action.


Table 214 — Additional entries specific to a hide action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Hide for a hide action. |
| T | dictionary, text | (Required) The annotation or annotations to be hidden or shown, shall |
| string, or array | be specified in any of the following forms: |  |

• An indirect reference to an annotation dictionary •    A text string giving the fully qualified field name of an interactive form field whose associated widget annotation or annotations are to be affected (see 12.7.4.2, "Field names") •    An array of such dictionaries or text strings

H boolean (Optional) A flag indicating whether to hide the annotation (true) or show it (false). Default value: true.

#### 12.6.4.12 Named actions

"Table 215 — Named actions" lists several named actions (PDF 1.2) that interactive PDF processors shall support; further names may be added in the future.

Table 215 — Named actions

| Name | Action |
| --- | --- |
| NextPage | Go to the next page of the document. |
| PrevPage | Go to the previous page of the document. |
| FirstPage | Go to the first page of the document. |
| LastPage | Go to the last page of the document. |

> **NOTE** Interactive PDF processors can support additional, nonstandard named actions, but any document using them is not portable.
If the PDF processor encounters a named action that is inappropriate for a viewing platform, or if the viewer does not recognise the name, it shall take no action.

"Table 216 — Additional entries specific to named actions" shows the action dictionary entries specific to named actions.

Table 216 — Additional entries specific to named actions

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Named for a named action. |
| N | name | (Required) The name of the action that shall be performed (see "Table 215 — Named actions"). |


#### 12.6.4.13 Set-OCG-state actions

A set-OCG-state action (PDF 1.5) sets the state of one or more optional content groups (see 8.11, "Optional content"). "Table 217 — Additional entries specific to a set-OCG-state action" shows the action dictionary entries specific to this type of action.

Table 217 — Additional entries specific to a set-OCG-state action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be SetOCGState for a set-OCG-state action. |
| State | array | (Required) An array consisting of any number of sequences beginning with a name object (ON, OFF, or Toggle) followed by one or more optional content group dictionaries. The array elements shall be processed from left to right; each name shall be applied to the subsequent groups until the next name is encountered: |
| ON | sets the state of subsequent groups to ON. |  |
| OFF | sets the state of subsequent groups to OFF. |  |
| Toggle | reverses the state of subsequent groups. |  |
| PreserveRB | boolean | (Optional) If true, indicates that radio-button state relationships between optional content groups (as specified by the RBGroups entry in the current configuration dictionary; see "Table 99 — Entries in an optional content configuration dictionary") should be preserved when the states in the State array are applied. That is, if a group is set to ON (either by ON or Toggle) during processing of the State array, any other groups belonging to the same radio-button group shall be turned OFF. If a group is set to OFF, there is no effect on other groups. If PreserveRB is false, radio-button state relationships, if any, shall be ignored. Default value: true. |

When a set-OCG-state action is performed, the State array shall be processed from left to right. Each name shall be applied to subsequent groups in the array until the next name is encountered, as shown in the following example.

> **EXAMPLE** 1

<< /S /SetOCGState /State [/OFF 2 0 R 3 0 R /Toggle 16 0 R 19 0 R /ON 5 0 R] >>

A group may appear more than once in the State array; its state shall be set each time it is encountered, based on the most recent name. ON, OFF and Toggle sequences have no required order.
More than one sequence in the array may contain the same name.

> **EXAMPLE 2** If the array contained [/OFF 1 0 R /Toggle 1 0 R], the group’s state would be ON after the action was


performed.

> **NOTE** While the specification allows a group to appear more than once in the State array, this is not intended to implement animation or any other sequential drawing operations. PDF processors are free to accumulate all state changes and apply only the net changes simultaneously to all affected groups before redrawing.

#### 12.6.4.14 Rendition actions

A rendition action (PDF 1.5) controls the playing of multimedia content (see 13, "Multimedia features").
This action may be used in the following ways:

• To begin the playing of a rendition object (see 13.2.3, "Renditions"), associating it with a screen annotation (see 12.5.6.18, "Screen annotations"). The screen annotation specifies where the rendition shall be played unless otherwise specified.
• To stop, pause, or resume a playing rendition.
• To trigger the execution of an ECMAScript script that may perform custom operations.
"Table 218 — Additional entries specific to a rendition action" lists the entries in a rendition action dictionary.

Table 218 — Additional entries specific to a rendition action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Rendition for a rendition action. |
| R | dictionary | (Required when OP is present with a value of 0 or 4; otherwise optional) A rendition object (see 13.2.3, "Renditions"). |
| AN | dictionary | (Required if OP is present with a value of 0, 1, 2, 3 or 4; otherwise optional) An indirect reference to a screen annotation (see 12.5.6.18, "Screen annotations"). |
| OP | integer | (Required if JS is not present; otherwise optional) The operation to perform when the action is triggered. Valid values shall be: |

# 0 If no rendition is associated with the annotation specified by AN, play

the rendition specified by R, associating it with the annotation. If a rendition is already associated with the annotation, it shall be stopped, and the new rendition shall be associated with the annotation.

# 1 Stop any rendition being played in association with the annotation

specified by AN, and remove the association. If no rendition is being played, there is no effect.

# 2 Pause any rendition being played in association with the annotation

specified b y AN. If no rendition is being played, there is no effect.

# 3 Resume any rendition being played in association with the

annotation specified by AN. If no rendition is being played or the rendition is not paused, there is no effect.

# 4 Play the rendition specified by R, associating it with the annotation

specified by AN. If a rendition is already associated with the annotation, resume the rendition if it is paused; otherwise, do nothing.


| Key | Type | Value |
| --- | --- | --- |
| JS | text string | (Required if OP is not present; otherwise optional) A text string or stream |
| or stream | containing an ECMAScript script that shall be executed when the action is triggered. |  |

Either the JS entry or the OP entry shall be present. If both are present, OP is considered a fallback that shall be executed if the interactive PDF processor is unable to execute ECMAScripts. If OP has an unrecognised value and there is no JS entry, the action is invalid.

In some situations, a pause (OP value of 2) or resume (OP value of 3) operation may not make s ense or the player may not support it. In such cases, the user should be notified of the failure to perform the operation.

> **EXAMPLE** A JPEG image.

Before a rendition action is executed, the interactive PDF processor shall make sure that the P entry of the screen annotation dictionary references a valid page object and that the annotation is present in the page object’s Annots array (see "Table 31 — Entries in a page object ").

A rendition may play in the rectangle occupied by a screen annotation, even if the annotation itself is not visible; for example, if its Hidden or NoView flags (see "Table 167 — Annotation flags") are set. If a screen annotation is not visible because its location on the page is not being displayed by the viewer, the rendition is not visible. However, it may become visible if the view changes, such as by scrolling.

#### 12.6.4.15 Transition actions

A transition action (PDF 1.5) may be used to control drawing during a sequence of actions. As discussed in 12.6.2, "Action dictionaries" the Next entry in an action dictionary may specify a sequence of actions. interactive PDF processors shall normally suspend drawing when such a sequence begins and resume drawing when it ends. If a transition action is present during a sequence, the interactive PDF processor shall render the state of the page viewing area as it exists after completion of the previous action and display it using a transition specified in the action dictionary (see "Table 219 — Additional entries specific to a transition action"). Once this transition completes, drawing shall be suspended again.

Table 219 — Additional entries specific to a transition action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be Trans for a transition action. |
| Trans | dictionary | (Required) The transition to use for the update of the display (see "Table 164 — Entries in a transition dictionary"). |


#### 12.6.4.16 Go-To-3D-View actions

A go-to-3D-view action (PDF 1.6) identifies a 3D annotation and specifies a view for the annotation to use (see 13.6, "3D Artwork"). "Table 220 — Additional entries specific to a go-to-3D-view action" shows the entries in a go-to-3D-view action dictionary.

Table 220 — Additional entries specific to a go-to-3D-view action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be GoTo3DView for a transition action. |
| TA | dictionary | (Required) The target annotation for which to set the view. |
| V | (various) | (Required) The view to use. It may be one of the following types: |

• A 3D view dictionary (see 13.6.4, "3D views").
• An integer specifying an index into the VA array in the 3D stream (see "Table 311 — Entries in a 3D stream dictionary").
• A text string matching the IN entry in one of the views in the VA array (see "Table 315 — Entries in a 3D view dictionary").
• A name that indicates the first (F), last (L), next (N), previous (P), or default (D) entries in the VA array; see discussion following this Table.

The V entry selects the view to apply to the annotation specified by TA. This view may be one of the predefined views specified by the VA entry of the 3D stream (see "Table 311 — Entries in a 3D stream dictionary") or a unique view specified here.

If the predefined view is specified by the names N (next) or P (previous), it should be interpreted in the following way:

• When the last view applied was specified by means of the VA array, N and P indicate the next and previous entries, respectively, in the VA array (wrapping around if necessary).
• When the last view was not specified by means of VA, using N or P should result in reverting to the default view.

#### 12.6.4.17 ECMAScript actions

JavaScript is referred to as ECMAScript throughout this document and is defined by ISO/DIS 21757-1.
For backwards compatibility reasons the term JavaScript is retained in keywords.

Upon invocation of an ECMAScript action, a PDF processor shall execute a script that is written in the ECMAScript programming language. ECMAScript extensions described in ISO/DIS 21757-1 shall also be allowed. Depending on the nature of the script, various interactive form fields in the document may update their values or change their visual appearances. ISO/DIS 21757-1 defines the contents and effects of ECMAScript scripts. "Table 221 — Additional entries specific to an ECMAScript action" shows the action dictionary entries specific to this type of action.


Table 221 — Additional entries specific to an ECMAScript action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be JavaScript for an ECMAScript action. |
| JS | text string or text | (Required) A text string or text stream containing the ECMAScript |
| stream | script to be executed. |  |

To support the use of parameterized function calls in ECMAScript scripts, the JavaScript entry in a PDF document’s name dictionary (see 7.7.4, "Name dictionary") may contain a name tree that maps name strings to document-level ECMAScript actions. When the document is opened, all of the actions in this name tree shall be executed, defining ECMAScript functions for use by other scripts in the document.

> **NOTE** The name strings associated with individual ECMAScript actions in the name dictionary serve merely as a convenient means for organising and packaging scripts. The names are arbitrary and need not bear any relation to the ECMAScript name space.

#### 12.6.4.18 Rich-Media-Execute actions

A rich-media-execute action identifies a rich media annotation and specifies a command to be se nt to that annotation’s handler (see Clause 13.7, "

Rich media". "Table 222 — Additional entries specific to a rich-media-execute action" shows the entries in a rich-media-execute action dictionary.

Table 222 — Additional entries specific to a rich-media-execute action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required; PDF 2.0) The type of action that this dictionary describes; shall be RichMediaExecute for a rich-media-execute action. |
| TA | dictionary | (Required; PDF 2.0) An indirect object reference to an annotation dictionary (of Subtype RichMedia) upon which to execute the command. |
| TI | dictionary | (Optional; PDF 2.0) A dictionary that shall be an indirect object reference to a RichMediaInstance dictionary that is also present in the Instances array of the annotation. |
| CMD | dictionary | (Required; PDF 2.0) A RichMediaCommand dictionary containing the command name and arguments to be executed when the rich-media- execute action is invoked. See "Table 223 — Entries in a RichMediaCommand dictionary". |

The RichMediaCommand dictionary contains a command name and optional arguments to be passed to the annotation handler specific to the target instance specified by the TI key in the parent rich-mediaexecute action dictionary.


Table 223 — Entries in a RichMediaCommand dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional; PDF 2.0) The type of PDF object that this dictionary describes; shall be RichMediaCommand. |
| C | text string | (Required; PDF 2.0) A text string specifying the script command (a primitive ECMAScript function name). If the target instance is a 3D model, the call shall be made in the global context of the annotation’s instance of the 3D ECMAScript engine. |
| A | (various) | (Optional; PDF 2.0) An object that specifies the arguments to the command. The object may either be a single typed value or an array of typed values, each an argument. Valid arguments are objects of type text string, integer, number, or boolean. Default value: no arguments. |

## 12.7 Forms

### 12.7.1 General

A PDF document may contain both interactive and non-Interactive forms.

An interactive form (PDF 1.2) — sometimes referred to as an AcroForm — is a collection of fields for gathering information interactively from the user. A PDF document may contain any number of fields appearing on any combination of pages, all of which make up a single, global interactive form spanning the entire document. Arbitrary subsets of these fields can be imported or exported from the document.

> **NOTE** Interactive forms are not to be confused with form XObjects (see 8.10, "Form XObjects"). Despite the similarity of names, the two are different, unrelated types of objects.
A non-interactive form (PDF 1.7) is a static representation of form fields. Such forms may have originally contained interactive fields such as text fields and radio buttons but were converted into non-interactive PDF files, they may represent form fields and/or data converted from external sources, or they may have been designed to be printed out and filled in manually. See 12.7.9, "Non-interactive forms".

### 12.7.2 Interactive Forms

Each field in a document’s form shall be defined by a field dictionary (see 12.7.4, "Field dictionaries").
For purposes of definition and naming, the fields can be organised hierarchically and can inherit attributes from their ancestors in the field hierarchy. A field’s children in the hierarchy may also include widget annotations (see 12.5.6.19, "Widget annotations") that define its appearance on the page. A field that has children that are fields is called a non-terminal field. A field that does not have children that are fields is called a terminal field.

A terminal field in an interactive form may have children that are widget annotations (see 12.5.6.19, "Widget annotations") that define its appearance on the page. As a convenience, when a field has only a single associated widget annotation, the contents of the field dictionary and the annotation dictionary (12.5.2, "Annotation dictionaries") may be merged into a single dictionary containing entries that


pertain to both a field and an annotation. (This presents no ambiguity, since the contents of the two kinds of dictionaries do not conflict.) If such an object defines an appearance stream, the appearance shall be consistent with the object’s current value as a field.

> **NOTE** Fields containing text whose contents are not known in advance will need to construct their appearance streams dynamically instead of defining them statically in an appearance dictionary; see 12.7.4.3, "Variable text".

### 12.7.3 Interactive form dictionary

The contents and properties of a document’s interactive form shall be defined by an interactive form dictionary that shall be referenced from the AcroForm entry in the document catalog dictionary (see 7.7.2, "Document catalog dictionary"). "Table 224 — Entries in the interactive form dictionary" shows the contents of this dictionary.

Table 224 — Entries in the interactive form dictionary

| Key | Type | Value |
| --- | --- | --- |
| Fields | array | (Required) An array of references to the document’s root fields (those with no ancestors in the field hierarchy). |
| NeedAppearances | boolean | (Optional; deprecated in PDF 2.0) A flag specifying whether to construct appearance streams and appearance dictionaries for all widget annotations in the document (see 12.7.4.3, "Variable text"). Default value: false. A PDF writer shall include this key, with a value of true, if it has not provided appearance streams for all visible widget annotations present in the document. |

> **NOTE** Appearance streams are required in PDF 2.0 and later.

| SigFlags | integer | (Optional; PDF 1.3) A set of flags specifying various document-level characteristics related to signature fields (see "Table 225 — Signature flags", and 12.7.5.5, "Signature fields"). Default value: 0. |
| --- | --- | --- |
| CO | array | (Required if any fields in the document have additional- actions dictionaries containing a C entry; PDF 1.3) An array of indirect references to field dictionaries with calculation actions, defining the calculation order in which their values will be recalculated when the value of any field changes (see 12.6.3, "Trigger events"). |
| DR | dictionary | (Optional) A resource dictionary (see 7.8.3, "Resource dictionaries") containing default resources (such as fonts, patterns, or colour spaces) that shall be used by form field appearance streams. At a minimum, this dictionary shall contain a Font entry specifying the resource name and font dictionary of the default font for displaying text. |
| DA | string | (Optional) A document-wide default value for the DA attribute of variable text fields (see 12.7.4.3, "Variable text"). |
| Q | integer | (Optional) A document-wide default value for the Q attribute of variable text fields (see 12.7.4.3, "Variable text"). |


| Key | Type | Value |
| --- | --- | --- |
| XFA | stream or array | (Optional; deprecated in PDF 2.0) A stream or array containing an XFA resource, whose format shall conform to the Data Package (XDP) Specification. See Annex K, “XFA forms”. |

The value of the interactive form dictionary’s SigFlags entry is an unsigned 32-bit integer containing flags specifying various document-level characteristics related to signature fields (see 12.7.5.5, "Signature fields"). Bit positions within the flag word shall be numbered from 1 (low-order) to 32 (high-order). "Table 225 — Signature flags" shows the meanings of the flags; all undefined flag bits shall be reserved and shall be set to 0.

Table 225 — Signature flags

Bit position Name Meaning

# 1 SignaturesExist If set, the document contains at least one signature field. This

flag allows an interactive PDF processor to enable user interface items (such as menu items or push-buttons) related to signature processing without having to scan the entire document for the presence of signature fields.

# 2 AppendOnly If set, the document contains signatures that may be invalidated

if the PDF file is saved (written) in a way that alters its previous contents, as opposed to an incremental update. Merely updating the PDF file by appending new information to the end of the previous version is safe (see H.7, "Updating example").
Interactive PDF processors may use this flag to inform a user requesting a full save that signatures will be invalidated and require explicit confirmation before continuing with the operation.

### 12.7.4 Field dictionaries

#### 12.7.4.1 General

Each field in a document’s interactive form shall be defined by a field dictionary, which shall be an indirect object. The field dictionaries may be organised hierarchically into one or more tree structures.
Many field attributes are inheritable, meaning that if they are not explicitly specified for a given field, their values are taken from those of its parent in the field hierarchy. Such inheritable attributes shall be designated as such in the "Table 226 — Entries common to all field dictionaries" and "Table 227 — Field flags common to all field types". The designation (Required; inheritable) means that an attribute shall be defined for every field, whether explicitly in its own field dictionary or by inheritance from an ancestor in the hierarchy. The inheritable behavior of field dictionaries are similar to that of Pages (see 7.7.3.4, "Inheritance of page attributes"). An interactive PDF processor shall not limit the range of inheritance for field dictionaries. "Table 226 — Entries common to all field dictionaries" shows those entries that are common to all field dictionaries, regardless of type. Entries that pertain only to a


particular type of field are described in the relevant subclauses referred to in "Table 226 — Entries common to all field dictionaries".

Table 226 — Entries common to all field dictionaries

| Key | Type | Value |
| --- | --- | --- |
| FT | name | (Required for terminal fields; inheritable) The type of field that this dictionary describes: |
| Btn | Button (see 12.7.5.2, "Button fields") |  |
| Tx | Text (see 12.7.5.3, "Text fields") |  |
| Ch | Choice (see 12.7.5.4, "Choice fields") |  |
| Sig | (PDF 1.3) Signature (see 12.7.5.5, "Signature fields") This entry may be present in a non-terminal field (one whose descendants are fields) to provide an inheritable FT value. However, a non-terminal field does not logically have a type of its own; it is merely a container for inheritable attributes that are intended for descendant terminal fields of any type. |  |
| Parent | dictionary | (Required if this field is the child of another in the field hierarchy; absent otherwise) The field that is the immediate parent of this one (the field, if any, whose Kids array includes this field). A field can have at most one parent; that is, it can be included in the Kids array of at most one other field. |
| Kids | array | (Sometimes required, as described below) An array of indirect references to the immediate children of this field. In a non-terminal field, the Kids array shall refer to field dictionaries that are immediate descendants of this field. In a terminal field, the Kids array ordinarily shall refer to one or more separate widget annotations that are associated with this field. However, if there is only one associated widget annotation, and its contents have been merged into the field dictionary, Kids shall be omitted. |
| T | text string | (Required) The partial field name (see 12.7.4.2, "Field names"). |
| TU | text string | (Optional; PDF 1.3) An alternative field name that shall be used in place of the actual field name wherever the field shall be identified in the user interface (such as in error or status messages referring to the field). This text is also useful when extracting the document’s contents in support of accessibility to users with disabilities or for other purposes (see 14.9.3, "Alternate descriptions"). |
| TM | text string | (Optional; PDF 1.3) The mapping name that shall be used when exporting interactive form field data from the document. |
| Ff | integer | (Optional; inheritable) A set of flags specifying various characteristics of the field (see "Table 227 — Field flags common to all field types"). Default value: 0. |
| V | (various) | (Optional; inheritable) The field’s value, whose format varies depending on the field type. See the descriptions of individual field types for further information. |
| DV | (various) | (Optional; inheritable) The default value to which the field reverts when a reset-form action is executed (see 12.7.6.3, "Reset-form action"). The format of this value is the same as that of V. |
| AA | dictionary | (Optional; PDF 1.2) An additional-actions dictionary defining the field’s behaviour in response to various trigger events (see 12.6.3, "Trigger events"). This entry has exactly the same meaning as the AA entry in an annotation dictionary (see 12.5.2, "Annotation dictionaries"). |


The value of the field dictionary’s Ff entry is an unsigned 32-bit integer containing flags specifying various characteristics of the field. Bit positions within the flag word shall be numbered from 1 (loworder) to 32 (high-order). The flags shown in "Table 227 — Field flags common to all field types" are common to all types of fields. Flags that apply only to specific field types are discussed in the subclauses describing those types. All undefined flag bits shall be reserved and shall be set to 0.

Table 227 — Field flags common to all field types

Bit position Name Meaning

# 1 ReadOnly If set, an interactive PDF processor shall not allow a user to change the value of

the field. Additionally, any associated widget annotations should not interact with the user; that is, they should not respond to mouse clicks nor change their appearance in response to mouse motions.
NOTE: This flag is useful for fields whose values are computed or imported from a database.

# 2 Required If set, the field shall have a value at the time it is exported by a submit-form action

(see 12.7.6.2, "Submit-form action").

# 3 NoExport If set, the field shall not be exported by a submit-form action (see 12.7.6.2,

"Submit-form action").

#### 12.7.4.2 Field names

The T entry in the field dictionary (see "Table 226 — Entries common to all field dictionaries" holds a text string defining the field’s partial field name. The fully qualified field name is not explicitly defined but shall be constructed from the partial field names of the field and all of its ancestors. For a field with no parent, the partial and fully qualified names are the same. For a field that is the child of another field, the fully qualified name shall be formed by appending the child field’s partial name to the parent’s fully qualified name, separated by a PERIOD (2Eh) as shown:

parent’s_full_name.child’s_partial_name

> **EXAMPLE** If a field with the partial field name PersonalData has a child whose partial name is Address, which in turn has a child with the partial name ZipCode, the fully qualified name of this last field is

PersonalData.Address.ZipCode

Because the PERIOD is used as a separator for fully qualified names, a partial name shall not contain a PERIOD character. Thus, all fields descended from a common ancestor share the ancestor’s fully qualified field name as a common prefix in their own fully qualified names.

A field dictionary that does not have a partial field name (T entry) of its own shall not be considered a field but simply a Widget annotation. Such annotations are different representations of the same underlying field; they should differ only in properties that specify their visual appearance. In addition, actual field dictionaries with the same fully qualified field name shall have the same field type (FT), value (V), and default value (DV).


> **EXAMPLE** A field with the fully qualified field name of PersonalData.Address.ZipCode can have multiple unnamed children representing different Widget annotations in multiple locations. Such children, although visually different, would represent the same field value to users.

#### 12.7.4.3 Variable text

When the contents and properties of a field are known in advance, its visual appearance can be specified by an appearance stream defined in the PDF file (see 12.5.5, "Appearance streams" and 12.5.6.19, "Widget annotations"). In some cases, however, the field may contain text whose value is not known until viewing time.

> **NOTE** Examples include text fields to be filled in with text typed by the user from the keyboard, scrollable list boxes whose contents are determined interactively at the time the document is displayed and fields containing current dates or values calculated by an ECMAScript.
In such cases, the PDF document cannot provide a statically defined appearance stream for displaying the field. Instead, the PDF processor shall construct an appearance stream dynamically at rendering time. The dictionary entries shown in "Table 228 — Additional entries common to all fields containing variable text" provide general information about the field’s appearance that can be combined with the specific text it contains to construct an appearance stream.

Table 228 — Additional entries common to all fields containing variable text

| Key | Type | Value |
| --- | --- | --- |
| DA | string | (Required; inheritable) The default appearance string containing a sequence of valid page-content graphics or text state operators that define such properties as the field’s text size and colour. |
| Q | integer | (Optional; inheritable) A code specifying the form of quadding (justification) that shall be used in displaying the text: |

# 0 Left-justified

# 1 Centred

# 2 Right-justified

Default value: 0 (left-justified).

| DS | text string | (Optional; PDF 1.5) A default style string, as described in Adobe XML Architecture, XML Forms Architecture (XFA) Specification, version 3.3. |
| --- | --- | --- |
| RV | text string or | (Optional; PDF 1.5) A rich text string, as described in Adobe XML |
| text stream | Architecture, XML Forms Architecture (XFA) Specification, version 3.3. |  |

The new appearance stream becomes the normal appearance (N) in the appearance dictionary associated with the field’s widget annotation (see "Table 170 — Entries in an appearance dictionary").

In PDF 1.5, form fields that have the RichText flag set (see "Table 231 — Field flags specific to text fields") specify formatting information as described in Adobe XML Architecture, XML Forms Architecture (XFA) Specification, version 3.3. For these fields, the following conventions are not used, and the entire annotation appearance shall be regenerated each time the value is changed.

For non-rich text fields, the appearance stream — which, like all appearance streams, is a form XObject — has the contents of its form dictionary initialised as follows:


• The resource dictionary (Resources) shall be created using resources from the interactive form dictionary’s DR entry (see "Table 224 — Entries in the interactive form dictionary").
• The lower-left corner of the bounding box (BBox) is set to coordinates (0, 0) in the form coordinate system. The box’s top and right coordinates are taken from the dimensions of the annotation rectangle (the Rect entry in the widget annotation dictionary).
• All other entries in the appearance stream’s form dictionary are set to their default values (see 8.10, "Form XObjects").

> **EXAMPLE** The appearance stream includes the following section of marked-content, which represents the portion of the stream that draws the text:

| /Tx BMC | %Begin marked-content with tag Tx |
| --- | --- |
| q | %Save graphics state … Any required graphics state changes, such as clipping … |
| BT | %Begin text object … Default appearance string ( DA ) … … Text-positioning and text-showing operators to show the variable text … |
| ET | %End text object |
| Q | %Restore graphics state |
| EMC | %End marked-content |

The BMC (begin marked-content) and EMC (end marked-content) operators are discussed in 14.6, "Marked
| content", q (save graphics state) and | Q (restore graphics state) are discussed in | 8.4.4, "Graphics state |
| --- | --- | --- |
| operators", BT (begin text object) and ET (end text object) are discussed in | 9.4, "Text objects". See the |  |

> **EXAMPLE** in 12.7.5.3, "Text fields" for an example of their use.

The default appearance string (DA) contains any graphics state or text state operators needed to establish the graphics state parameters, such as text size and colour, for displaying the field’s variable
| text. Only operators that are allowed within text objects shall occur in this string (see | "Figure 9 — |
| --- | --- |
| Graphics objects"). At a minimum, the string shall include a | Tf (text font) operator along with its two |

operands, font and size. The specified font value shall match a resource name in the Font entry of the default resource dictionary (referenced from the DR entry of the interactive form dictionary; see
| "Table 224 — Entries in the interactive form dictionary | "). A zero value for size means that the font |
| --- | --- |
| shall be auto-sized: its size shall be computed as an implementation dependent f | unction. |
| The default appearance string shall contain at most one | Tm (text matrix) operator. If this operator is |

present, the interactive PDF processor shall replace the horizontal and vertical translation components with positioning values it determines to be appropriate, based on the field value, the quadding (Q) attribute, and any layout rules it employs. If the default appearance string contains no Tm operator, the viewer shall insert one in the appearance stream (with appropriate horizontal and vertical translation components) after the default appearance string and before the text -positioning and textshowing operators for the variable text.

To update an existing appearance stream to reflect a new field value, the interactive PDF processor shall first copy any needed resources from the document’s DR dictionary (see "Table 224 — Entries in the interactive form dictionary") into the stream’s Resources dictionary. (If the DR and Resources dictionaries contain resources with the same name, the one already in the Resources dictionary shall be left intact, not replaced with the corresponding value from the DR dictionary.) The interactive PDF processor shall then replace the existing contents of the appearance stream from /Tx BMC to the matching EMC with the corresponding new contents as shown in Example 1 in 12.7.5.2.3, "Check boxes", 12.7.5, "Field types" (If the existing appearance stream contains no marked-content with tag Tx, the new contents shall be appended to the end of the original stream.)


### 12.7.5 Field types

#### 12.7.5.1 General

Interactive forms support the following field types:

• Button fields represent interactive controls on the screen that the user can manipulate with the mouse. They include push-buttons, check boxes, and radio buttons.
• Text fields are boxes or spaces in which the user can enter text from the keyboard.
• Choice fields contain several text items, at most one of which may be selected as the field value.
They include scrollable list boxes and combo boxes.
• Signature fields represent digital signatures and optional data for authenticating the name of the signer and the document’s contents.
The following subclauses describe each of these field types in detail.

#### 12.7.5.2 Button fields

##### 12.7.5.2.1 General

A button field (field type Btn) represents an interactive control on the screen that the user can manipulate with the mouse. There are three types of button fields:

• A push-button is a purely interactive control that responds immediately to user input without retaining a permanent value (see 12.7.5.2.2, "Push-buttons").
• A check box toggles between two states, on and off (see 12.7.5.2.3, "Check boxes").
• Radio button fields contain a set of related buttons that can each be on or off. Typically, at most one radio button in a set may be on at any given time, and selecting any one of the buttons automatically deselects all the others. (There are exceptions to this rule, as noted in 12.7.5.2.4, "Radio buttons") For button fields, bits 15, 16, 17, and 26 shall indicate the intended behaviour of the button field. An interactive PDF processor shall follow the intended behaviour, as defined in "Table 229 — Field flags specific to button fields" and clauses 12.7.5.2.2, "Push-buttons", and 12.7.5.2.4, "Radio buttons".

Table 229 — Field flags specific to button fields

Bit position Name Meaning

# 15 NoToggleToOff (Radio buttons only) If set, exactly one radio button shall be

selected at all times; selecting the currently selected button has no effect. If clear, clicking the selected button deselects it, leaving no button selected.

# 16 Radio If set, the field is a set of radio buttons; if clear, the field is a

check box. This flag may be set only if the Pushbutton flag is clear.

# 17 Pushbutton If set, the field is a push-button that does not retain a

permanent value.


Bit position Name Meaning

# 26 RadiosInUnison (PDF 1.5) If set, a group of radio buttons within a radio button

field that use the same value for the on state will turn on and off in unison; that is if one is checked, they are all checked. If clear, the buttons are mutually exclusive (the same behaviour as HTML radio buttons).

##### 12.7.5.2.2 Push-buttons

A push-button field shall have a field type of Btn and the Pushbutton flag (see "Table 229 — Field flags specific to button fields") set to one. Because this type of retains no permanent value, it shall not use the V and DV entries in the field dictionary (see "Table 226 — Entries common to all field dictionaries").

##### 12.7.5.2.3 Check boxes

A check box field represents one or more check boxes that toggle between two states, on and off, when manipulated by the user with the mouse or keyboard. Its field type shall be Btn and its Pushbutton and Radio flags (see "Table 229 — Field flags specific to button fields") shall both be clear. Each state can have a separate appearance, which shall be defined by an appearance stream in the appearance dictionary of the field’s widget annotation (see 12.5.5, "Appearance streams"). The appearance for the off state is optional but, if present, shall be stored in the appearance dictionary under the name Off.

The V entry in the field dictionary (see "Table 226 — Entries common to all field dictionaries") holds a name object representing the check box’s appearance state, which shall be used to select the appropriate appearance from the appearance dictionary. The value of the V key shall also be the value of the AS key. If they are not equal, then the value of the AS key shall be used instead of the V key to determine which appearance to use.

> **EXAMPLE 1** This example shows a typical check box definition.

1 0 obj <</Type /Annot /Subtype /Widget /Rect [100 100 120 120] /FT /Btn /T (Urgent) /V /Yes /AS /Yes /AP <</N <</Yes 2 0 R /Off 3 0 R>> >> endobj

2 0 obj <</Type /XObject /Subtype /Form /BBox [0 0 20 20] /Resources 20 0 R /Length 104 >> stream q 0 0 1 rg BT /ZaDb 12 Tf


0 0 Td (4) Tj ET Q endstream endobj

3 0 obj <</Type /XObject /Subtype /Form /BBox [0 0 20 20] /Resources 20 0 R /Length 104 >> stream q 0 0 1 rg BT /ZaDb 12 Tf 0 0 Td (8) Tj ET Q endstream endobj

Beginning with PDF 1.4, the field dictionary for check boxes and radio buttons may contain an optional Opt entry (see "Table 230 — Additional entry specific to check box and radio button fields"). If present, the Opt entry shall be an array of text strings representing the export value of each annotation in the field. It may be used for the following purposes:

• To represent the export values of check box and radio button fields in non-Latin writing systems (because name objects in the appearance dictionary cannot represent non-Latin text).
• To allow radio buttons or check boxes to be checked independently, even if they have the same export value.

> **EXAMPLE 2** A group of check boxes may be duplicated on more than one page such that the desired behaviour is that when a user checks a box, the corresponding boxes on each of the other pages are also checked. In this case, each of the corresponding check boxes is a widget in the Kids array of a check box field.

For radio buttons, the same behaviour shall occur only if the RadiosInUnison flag is set. If it is not set, at most one radio button in a field shall be set at a time.

Table 230 — Additional entry specific to check box and radio button fields

| Key | Type | Value |
| --- | --- | --- |
| Opt | array of text | (Optional; inheritable; PDF 1.4) An array containing one entry for each |
| strings | widget annotation in the Kids array of the radio button or check box field. Each entry shall be a text string representing the on state of the corresponding widget annotation. When this entry is present, the names used to represent the on state in the AP dictionary of each annotation may use numerical position (starting with 0) of the annotation in the Kids array, encoded as a name object (for |  |

example: /0, /1). This allows distinguishing between the annotations even if two or more of them have the same value in the Opt array.


##### 12.7.5.2.4 Radio buttons

A radio button field is a set of related buttons. Like check boxes, individual radio buttons have two states, on and off. A single radio button may not be turned off directly but only as a result of another button being turned on. Typically, a set of radio buttons (annotations that are children of a single radio button field) have at most one button in the on state at any given time; selecting any of the buttons automatically deselects all the others.

> **NOTE** An exception occurs when multiple radio buttons in a field have the same on state and the RadiosInUnison flag is set. In that case, turning on one of the buttons turns on all of them.
The field type is Btn, the Pushbutton flag (see "Table 229 — Field flags specific to button fields") is clear, and the Radio flag is set. This type of button field has an additional flag, NoToggleToOff, which specifies, if set, that exactly one of the radio buttons shall be selected at all times. In this case, clicking the currently selected button has no effect; if the NoToggleToOff flag is clear, clicking the selected button deselects it, leaving no button selected.

The Kids entry in the radio button field’s field dictionary (see "Table 226 — Entries common to all field dictionaries") holds an array of widget annotations representing the individual buttons in the set. The parent field’s V entry holds a name object corresponding to the appearance state of whichever child field is currently in the on state; the default value for this entry is Off. The value of the V key shall also be the value of the AS key. If they are not equal, then the value of the AS key shall be used instead of the V key to determine which appearance to use.

> **EXAMPLE** This example shows the object definitions for a set of radio buttons.

| 10 0 obj | %Radio button field <</Type /Annot /Subtype /Widget /Rect [100 100 120 120] /FT /Btn |
| --- | --- |
| /Ff … | %…Radio flag = 16, Pushbutton = 17 … /T (Credit card) /V /cardbrand1 /Kids [11 0 R  12 0 R] >> endobj |
| 11 0 obj | %First radio button <</Parent 10 0 R /AS /cardbrand1 /AP <</N <</cardbrand1 8 0 R /Off 9 0 R >> >> >> endobj |
| 12 0 obj %Second radio button <</Type /Annot /Subtype /Widget /Rect [200 200 220 220] /Parent 10 0 R /AS /Off /AP <</N <</cardbrand2 8 0 R /Off 9 0 R >> >> >> |  |


endobj

| 8 0 obj | %Appearance stream for "on" state <</Type /XObject /Subtype /Form /BBox [0 0 20 20] /Resources 20 0 R /Length 104 >> stream q 0 0 1 rg BT /ZaDb 12 Tf 0 0 Td (8) Tj ET Q endstream endobj |
| --- | --- |
| 9 0 obj | %Appearance stream for "off" state <</Type /XObject /Subtype /Form /BBox [0 0 20 20] /Resources 20 0 R /Length 104 >> stream q 0 0 1 rg BT /ZaDb 12 Tf 0 0 Td (4) Tj ET Q endstream endobj |

Like a check box field, a radio button field may use the optional Opt entry in the field dictionary (PDF 1.4) to define export values for its constituent radio buttons, using Unicode encoding for non-Latin characters (see "Table 230 — Additional entry specific to check box and radio button fields").

#### 12.7.5.3 Text fields

A text field (field type Tx) is a box or space for text fill-in data typically entered from a keyboard. The text may be restricted to a single line or may be permitted to span multiple lines, depending on the setting of the Multiline flag in the field dictionary’s Ff entry. "Table 231 — Field flags specific to text fields" shows the flags pertaining to this type of field. A text field shall have a field type of Tx. A conforming PDF file, and an interactive PDF processor shall obey the usage guidelines in "Table 231 — Field flags specific to text fields".

Table 231 — Field flags specific to text fields

Bit position Name Meaning

# 13 Multiline If set, the field may contain multiple lines of text; if clear, the field’s text

shall be restricted to a single line.


Bit position Name Meaning

# 14 Password If set, the field is intended for entering a secure password that should

not be echoed visibly to the screen. Characters typed from the keyboard shall instead be echoed in some unreadable form, such as asterisks or bullet characters.

> **NOTE** To protect password confidentiality, it is imperative that PDF processors never store the value of the text field in the PDF file if this flag is set.

# 21 FileSelect (PDF 1.4) If set, the text entered in the field represents the pathname of

a file whose contents shall be submitted as the value of the field.

# 23 DoNotSpellCheck (PDF 1.4) If set, text entered in the field shall not be spell-checked.

# 24 DoNotScroll (PDF 1.4) If set, the field shall not scroll (horizontally for single-line

fields, vertically for multiple-line fields) to accommodate more text than fits within its annotation rectangle. Once the field is full, no further text shall be accepted for interactive form filling; for non-interactive form filling, the filler should take care not to add more character than will visibly fit in the defined area.

# 25 Comb (PDF 1.5) May be set only if the MaxLen entry is present in the text field

dictionary (see "Table 232 — Additional entry specific to a text field") and if the Multiline, Password, and FileSelect flags are clear. If set, the field shall be automatically divided into as many equally spaced positions, or combs, as the value of MaxLen, and the text is laid out into those combs.

# 26 RichText (PDF 1.5) If set, the value of this field shall be a rich text string (see

Adobe XML Architecture, XML Forms Architecture (XFA) Specification, version 3.3). If the field has a value, the RV entry of the field dictionary ("Table 228 — Additional entries common to all fields containing variable text") shall specify the rich text string.

The field’s text shall be held in a text string (or, beginning with PDF 1.5, a stream) in the V (value) entry of the field dictionary. The contents of this text string or stream shall be used to construct an appearance stream for displaying the field, as described under 12.7.4.3, "Variable text" The text shall be presented in a single style (font, size, colour, and so forth), as specified by the DA (default appearance) string.

If the FileSelect flag (PDF 1.4) is set, the field shall function as a file-select control. In this case, the field’s text represents the pathname of a file whose contents shall be submitted as the field’s value:

• For fields submitted in HTML Form format, the submission shall use the MIME content type multipart / form-data, as described in Internet RFC 2045.
• For Forms Data Format (FDF) submission, the value of the V entry in the FDF field dictionary (see 12.7.8.3.2, "FDF fields") shall be a file specification (7.11, "File specifications") identifying the selected file.
• XML is not supported for file-select controls; therefore, no value shall be submitted in this case.
Besides the usual entries common to all fields (see "Table 226 — Entries common to all field dictionaries") and to fields containing variable text (see "Table 228 — Additional entries common to all fields containing variable text"), the field dictionary for a text field may contain the


additional entry shown in "Table 232 — Additional entry specific to a text field".

Table 232 — Additional entry specific to a text field

| Key | Type | Value |
| --- | --- | --- |
| MaxLen | integer | (Optional; inheritable) The maximum length of the field’s text, in characters. |

> **EXAMPLE** The following example shows the object definitions for a typical text field.

6 0 obj <</Type /Annot /Subtype /Widget /Rect [100 100 400 220] /FT /Tx /Ff … %Set Multiline flag /T (Silly prose) /DA (0 0 1 rg /Ti 12 Tf) /V (The quick brown fox ate the lazy mouse) /AP <</N 5 0 R>> >> endobj

5 0 obj <</Type /XObject /Subtype /Form /BBox [0 0 300 120] /Resources 21 0 R /Length 172 >> stream /Tx BMC q BT 0 0 1 rg /Ti 12 Tf 1 0 0 1 100 100 Tm 0 0 Td (The quick brown fox) Tj 0 -13 Td (ate the lazy mouse.) Tj ET Q EMC endstream endobj

#### 12.7.5.4 Choice fields

A choice field shall have a field type of Ch that contains several text items, one or more of which shall be selected as the field value. The items may be presented to the user in one of the following two forms:

• A scrollable list box •    A combo box consisting of a drop-down list. The combo box may be accompanied by an editable text box in which the user can type a value other than the predefined choices, as directed by the value of the Edit bit in the Ff entry.
The various types of choice fields are distinguished by flags in the Ff entry, as shown in "Table 233 — Field flags specific to choice fields". "Table 234 — Additional entries specific to a choice field" shows the field dictionary entries specific to choice fields.


Table 233 — Field flags specific to choice fields

| Bit position | Name | Meaning |
| --- | --- | --- |
| Combo | If set, the field is a combo box; if clear, the field is a list box. |  |
| Edit | If set, the combo box shall include an editable text box as well as a drop-down list; if clear, it shall include only a drop- down list. This flag shall be used only if the Combo flag is set. |  |
| Sort | If set, the field’s option items shall be sorted alphabetically. This flag is intended for use by PDF writers, not by PDF readers. PDF readers shall display the options in the order in which they occur in the Opt array (see "Table 234 — Additional entries specific to a choice field"). |  |
| MultiSelect | (PDF 1.4) If set, more than one of the field’s option items may be selected simultaneously; if clear, at most one item shall be selected. |  |
| DoNotSpellCheck | (PDF 1.4) If set, text entered in the field shall not be spell- checked. This flag shall not be used unless the Combo and Edit flags are both set. |  |
| CommitOnSelChange | (PDF 1.5) If set, the new value shall be committed as soon as a selection is made (commonly with the pointing device). In this case, supplying a value for a field involves three actions: selecting the field for fill-in, selecting a choice for the fill-in value, and leaving that field, which finalizes or "commits" the data choice and triggers any actions associated with the entry or changing of this data. If this flag is on, then processing does not wait for leaving the field action to occur, but immediately proceeds to the third step. This option enables applications to perform an action once a selection is made, without requiring the user to exit the field. If clear, the new value is not committed until the user exits the field. |  |

Table 234 — Additional entries specific to a choice field

| Key | Type | Value |
| --- | --- | --- |
| Opt | array | (Optional) An array of options that shall be presented to the user. Each element of the array is either a text string representing one of the available options or an array consisting of two text strings: the option’s export value and the text that shall be displayed as the name of the option. If this entry is not present, no choices should be presented to the user. |
| TI | integer | (Optional) For scrollable list boxes, the top index (the index in the Opt array of the first option visible in the list). Default value: 0. |


| Key | Type | Value |
| --- | --- | --- |
| I | array | (Sometimes required, otherwise optional; PDF 1.4) For choice fields that allow multiple selection (MultiSelect flag set), an array of integers, sorted in ascending order, representing the zero-based indices in the Opt array of the currently selected option items. This entry shall be used when two or more elements in the Opt array have different names but the same export value or when the value of the choice field is an array. If the items identified by this entry differ from those in the V entry of the field dictionary (see discussion following this Table), the V entry shall be used. |

The Opt array specifies the list of options in the choice field, each of which shall be represented by a text string that shall be displayed on the screen. Each element of the Opt array contains either this text string by itself or a two-element array, whose second element is the text string and whose first element is a text string representing the export value that shall be used when exporting interactive form field data from the document.

The field dictionary’s V (value) entry (see "Table 226 — Entries common to all field dictionaries") identifies the item or items currently selected in the choice field. If the field does not allow multiple selection — that is, if the MultiSelect flag (PDF 1.4) is not set — or if multiple selection is supported but only one item is currently selected, V is a text string representing the selected item, as given in the field dictionary’s Opt array. If multiple items are selected, V is an array of such strings. (For items represented in the Opt array by a two-element array, the name string is the second of the two array elements.) The default value of V is null, indicating that no item is currently selected.

> **EXAMPLE** The following example shows a typical choice field definition.

<</FT /Ch /Ff … /T (Body Color) /V (Blue) /Opt [(Red) (My favourite color) (Blue) ] >>

#### 12.7.5.5 Signature fields

A signature field (PDF 1.3) is a form field that contains a digital signature (see 12.8, "Digital signatures"). The field dictionary representing a signature field may contain the additional entries listed in "Table 235 — Additional entries specific to a signature field", as well as the standard entries described in "Table 226 — Entries common to all field dictionaries". The field type (FT) shall be Sig, and the field value (V), if present, shall be a signature dictionary containing the signature and specifying various attributes of the signature field (see "Table 255 — Entries in a signature dictionary").

> **NOTE 1** This signature form field serves two primary purposes. The first is to define the form field that provides the visual signing properties for display, and it can also hold information needed later when the actual signing takes place, such as the signature technology to use. This carries information from the author of the document to the software that later does the signing.


> **NOTE 2** Filling in (signing) the signature field entails updating at least the V entry and usually also the AP entry of the associated widget annotation. Exporting a signature field typically exports the T, V, and AP entries.
Like any other field, a signature field may be described by a widget annotation dictionary containing entries pertaining to an annotation as well as a field (12.5.6.19, "Widget annotations"). The annotation rectangle (Rect) in such a dictionary shall give the position of the field on its page. Signature fields that are not intended to be visible shall have an annotation rectangle that has zero height and width. PDF processors shall treat such signatures as not visible. PDF processors shall also treat signatures as not visible if either the Hidden bit or the NoView bit of the F entry is true. The F entry is described in "Table 166 — Entries common to all annotation dictionaries", and annotation flags are described in "Table 167 — Annotation flags".

The location of a signature within a document can have a bearing on its legal meaning. For this reason, signature fields shall never refer to more than one annotation.

> **NOTE 3** If more than one location is associated with a signature, the meaning can become ambiguous.
For signature fields that are visible, the appearance dictionary (AP) for the widget annotation of these fields should be created at the time of signature creation. This dictionary defines the field’s visual appearance on the page (see 12.5.5, "Appearance streams"), but the information included in the appearance dictionary shall not be used by a signature verification handler at the time of signature verification. It is there strictly for the purpose of providing a way for a human verifier to perform their own verification of the visual representation. A PDF processor shall not incorporate the validation status of a signature (e.g. a checkmark for passed or an X for failed) into the appearance of the signature field.

Table 235 — Additional entries specific to a signature field

| Key | Type | Value |  |
| --- | --- | --- | --- |
| Lock | dictionary | (Optional; shall be an indirect reference; PDF 1.5) | A signature field lock dictionary that specifies a set of form fields that shall be locked when this signature field is signed. "Table 236 — Entries in a signature field lock dictionary" lists the entries in this dictionary. |
| SV | dictionary | (Optional; shall be an indirect reference; PDF 1.5) A seed value dictionary (see "Table 237 — Entries in a signature field seed value dictionary") containing information that constrains the properties of a signature that is applied to this field. |  |

The signature field lock dictionary (described in "Table 236 — Entries in a signature field lock dictionary") contains the names of form fields whose values shall no longer be changed after this signature has been signed.

Table 236 — Entries in a signature field lock dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be SigFieldLock for a signature field lock dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| Action | name | (Required) A name which, in conjunction with Fields, indicates the set of fields that should be locked. The value shall be one of the following: |
| All | All fields in the document |  |
| Include | All fields specified in Fields |  |
| Exclude | All fields except those specified in Fields |  |
| Fields | array | (Required if the value of Action is Include or Exclude) An array of text strings containing field names. |
| P | number | (Optional; PDF 2.0) The access permissions granted for this document. Valid values shall be: |

# 1 No changes to the document are permitted; any change to the

document shall invalidate the signature.

# 2 Permitted changes shall be filling in forms, instantiating page

templates, and signing; other changes shall invalidate the signature.

# 3 Permitted changes are the same as for 2, as well as annotation

creation, deletion, and modification; other changes shall invalidate the signature.
There is no default value; absence of this key shall result in no effect on signature validation rules.
If MDP permission is already in effect from an earlier incremental save section or the original part of the document, the number shall specify permissions less than or equal to the permissions already in effect based on signatures earlier in the document. That is, permissions can be denied but not added. If the number specifies greater permissions than an MDP value already in effect, the new number is ignored.
If the document does not have an author signature, the initial permissions in effect are those based on the number 3.
The new permission applies to any incremental changes to the document following the signature of which this key is part.

The value of the SV entry in the field dictionary is a seed value dictionary whose entries (see "Table 237 — Entries in a signature field seed value dictionary") provide constraining information that shall be used at the time the signature is applied. The Ff entry in this signature field seed value dictionary specifies whether the other entries in the dictionary shall be honoured or whether they are merely recommendations.

Table 237 — Entries in a signature field seed value dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be SV for a seed value dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| Ff | integer | (Optional) A set of bit flags specifying the interpretation of specific entries in this dictionary. A value of 1 for the flag indicates that the associated entry is a required constraint. A value of 0 indicates that the associated entry is an optional constraint. Bit positions are 1 (Filter); 2 (SubFilter); 3 (V); 4 (Reasons); 5 (LegalAttestation); 6 (AddRevInfo); and 7 (DigestMethod). For PDF 2.0 the following bit flags are added: 8 (Lockdocument); and 9 (AppearanceFilter). Default value: 0. |
| Filter | name | (Optional) The signature handler that shall be used to sign the signature field. Beginning with PDF 1.7, if Filter is specified and the Ff entry indicates this entry is a required constraint, then the signature handler specified by this entry shall be used when signing; otherwise, signing shall not take place. If Ff indicates that this is an optional constraint, this handler may be used if it is available. If it is not available, a different handler may be used instead. |
| SubFilter | array | (Optional) An array of names indicating encodings to use when signing. The first name in the array that matches an encoding supported by the signature handler shall be the encoding that is actually used for signing. If SubFilter is specified and the Ff entry indicates that this entry is a required constraint, then the first matching encodings shall be used when signing; otherwise, signing shall not take place. If Ff indicates that this is an optional constraint, then the first matching encoding shall be used if it is available. If none is available, a different encoding may be used. |
| DigestMethod | array | (Optional; PDF 1.7) An array of names indicating acceptable digest algorithms to use while signing. The value shall be one of SHA1 (deprecated with PDF 2.0), SHA256, SHA384, SHA512 and RIPEMD160. The default value is implementation-specific. This property is only applicable if the digital credential signing contains RSA public/private keys. If it contains DSA public/ private keys, the digest algorithm is always SHA-1 and this attribute shall be ignored. |
| V | integer | (Optional) The minimum required capability of the signature field seed value dictionary parser. A value of 1 specifies that the parser shall be able |
| to recognise all seed value dictionary entries in a PDF 1.5 file. A value of | 2 specifies that it shall be able to recognise all seed value dictionary entries specified. A value of 3 specifies that it shall be able to recognise all seed value dictionary entries specified in PDF 2.0 and earlier. The Ff entry indicates whether this shall be treated as a required constraint. |  |
| Cert | dictionary | (Optional) A certificate seed value dictionary (see "Table 238 — Entries in a certificate seed value dictionary") containing information about the characteristics of the certificate that shall be used when signing. |


| Key | Type | Value |
| --- | --- | --- |
| Reasons | array | (Optional) An array of text strings that specifying possible reasons for signing a document. If specified, the reasons supplied in this entry replace those used by interactive PDF processors. If the Reasons array is provided and the Ff entry indicates that Reasons is a required constraint, one of the reasons in the array shall be used for the signature dictionary; otherwise, signing shall not take place. If the Ff entry indicates Reasons is an optional constraint, one of the reasons in the array may be chosen or a custom reason can be provided. If the Reasons array is omitted or contains a single entry with the value PERIOD (2Eh) and the Ff entry indicates that Reasons is a required constraint, the Reason entry shall be omitted from the signature dictionary (see "Table 255 — Entries in a signature dictionary"). |
| MDP | dictionary | (Optional; PDF 1.6) A dictionary containing a single entry whose key is P and whose value is an integer between 0 and 3. A value of 0 defines the signature as an approval signature (see 12.8, "Digital signatures"). The values 1 through 3 shall be used for certification signatures and correspond to the value of P in a DocMDP transform parameters dictionary (see "Table 257 — Entries in the DocMDP transform parameters dictionary"). If this MDP key is not present or the MDP dictionary does not contain a P entry, no rules shall be defined regarding the type of signature or its permissions. |
| TimeStamp | dictionary | (Optional; PDF 1.6) A timestamp dictionary containing two entries: |
| URL | An ASCII string specifying the URL of a timestamping server, providing a timestamp that is compliant with Internet RFC 3161 as updated by Internet RFC 5816. |  |
| Ff | An integer whose value is 1 (the signature shall have a timestamp) or 0 (the signature need not have a timestamp). Default value: 0. |  |

> **NOTE 1** Please see 12.8.3.3, "CMS (PKCS #7) signatures" for more details about hashing.

LegalAttestation array (Optional; PDF 1.6) An array of text strings specifying possible legal attestations (see 12.8.7, "Legal content attestations"). The value of the corresponding flag in the Ff entry indicates whether this is a required constraint.


| Key | Type | Value |
| --- | --- | --- |
| AddRevInfo | boolean | (Optional; PDF 1.7) A flag indicating whether revocation checking shall be carried out. If AddRevInfo is true, the PDF processor shall perform the following additional tasks when signing the signature field: |

• Perform revocation checking of the certificate (and the corresponding issuing certificates) used to sign.
• Include the revocation information within the signature value.
Three SubFilter values have been defined. For those values the AddRevInfo value shall be true only if SubFilter is adbe.pkcs7.detached or adbe.pkcs7.sha1. If SubFilter is adbe.x509.rsa_sha1, this entry shall be omitted or set to false. Additional SubFilter values may be defined that also use AddRevInfo values.
If AddRevInfo is true and the Ff entry indicates this is a required constraint, then the preceding tasks shall be performed. If they cannot be performed, then signing shall fail.
Default value: false NOTE 2    Revocation information is carried in the signature data as specified by PKCS #7. See 12.8.3.3, "CMS (PKCS #7) signatures".

> **NOTE 3** adbe.pkcs7.detached and adbe.pkcs7.sha1 are deprecated in PDF 2.0 .

| LockDocument | name | (Optional; PDF 2.0) A name value supplying the author’s intent for whether the signing dialogue should allow the user to lock the document at the time of signing. The value shall be one of true, false, and auto, as follows: |
| --- | --- | --- |
| true | the document should be locked at the time of signing. If the Ff entry indicates that LockDocument is not a required constraint, the user may choose to override this at the time of signing; otherwise, the document is locked after signing. |  |
| false | the document should not be locked after signing. Again, the required flag, Ff, determines whether this is a required constraint. |  |
| auto | the consuming application decided whether to present the lock user interface for the document and whether to honour the required flag, Ff, based on the properties of the document. Default value: auto |  |
| AppearanceFilter | text string | (Optional; PDF 2.0) A text string naming the appearance that shall be used when signing the signature field. interactive PDF processors may choose to maintain a list of named signature appearances. This text string provides authors with a means of specifying which appearance should be used to sign the signature field. If the required bit AppearanceFilter in Ff is set, the appearance shall be available to sign the document and is used. |

A seed value dictionary may also include seed values for private entries belonging to multiple handlers.
A given handler shall use only those private entries that are pertinent to itself and ignore any other private entries.

For optional keys that are not present, no constraint shall be placed upon the signature handler for that property when signing.


Table 238 — Entries in a certificate seed value dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be SVCert for a certificate seed value dictionary. |
| Ff | integer | (Optional) A set of bit flags specifying the interpretation of specific entries in this dictionary. A value of 1 for the flag means that a signer shall be required to use only the specified values for the entry. A value of 0 means that other values are permissible. Bit positions are 1 (Subject); 2 (Issuer); 3 (OID); 4 (SubjectDN); 5 (Reserved); 6 (KeyUsage); 7 (URL). Default value: 0. |
| Subject | array | (Optional) An array of byte strings containing DER- encoded X.509v3 certificates that are acceptable for signing. X.509v3 certificates are described in Internet RFC 5280. The value of the corresponding flag in the Ff entry indicates whether this is a required constraint. |
| SignaturePolicyOID | ASCII string | (Optional: PDF 2.0) The string representation of the OID of the signature policy to use when signing. |
| SignaturePolicyHashValue | string | (Optional: PDF 2.0) The computed hash value of the signature policy, computed the same way as hashValue of sigPolicyHash in clause 5.2.9 of CAdES (ETSI EN 319 122- 1), according to the hash algorithm specified by SignaturePolicyHashAlgorithm. |
| SignaturePolicyHashAlgorithm | name | (Optional: PDF 2.0) The hash function used to compute the value of the SignaturePolicyHashValue entry. Entries shall be represented the same way as SubFilter values specified in "Table 260 — SubFilter value algorithm support". |
| SignaturePolicyCommitmentType | Array of | (Optional: PDF 2.0) If the SignaturePolicyOID is present, |
| ASCII | this array defines the commitment types that may be used |  |
| strings | within the signature policy. An empty string may be used to indicate that all commitments defined by the signature policy may be used. |  |


| Key | Type | Value |  |
| --- | --- | --- | --- |
| SubjectDN | array of | (Optional; PDF 1.7) An array of dictionaries, each |  |
| dictionaries | specifying a Subject Distinguished Name (DN) that shall be present within the certificate for it to be acceptable for signing. The certificate ultimately used for the digital signature shall contain all the attributes specified in each of the dictionaries in this array. (PDF keys and values are mapped to certificate attributes and values.) The certificate is not constrained to use only attribute entries from these dictionaries but may contain additional attributes. The Subject Distinguished Name is described in Internet RFC 5280. The key can be any valid attribute identifier (OID). Attribute names shall contain characters in the set a-z A-Z 0-9 and PERIOD. Certificate attribute names are used as key names in the dictionaries in this array. Values of the attributes are used as values of the keys. Values shall be text strings. The value of the corresponding flag in the Ff entry indicates whether this entry is a required constraint. |  |  |
| KeyUsage | array of | (Optional; PDF 1.7) An array of ASCII strings, where each |  |
| ASCII | string specifies an acceptable key-usage extension that |  |  |
| strings | shall be present in the signing certificate. Multiple strings specify a range of acceptable key-usage extensions. The key-usage extension is described in Internet RFC 5280. Each character in a string represents a key-usage type, where the order of the characters indicates the key-usage extension it represents. The first through ninth characters in the string, from left to right, represent the required value for the following key-usage extensions: |  |  |
| 1 | digitalSignature | 4 dataEncipherment | 7 cRLSign |
| 2 | non-Repudiation | 5 keyAgreement | 8 encipherOnly |
| 3 | keyEncipherment | 6 keyCertSign | 9 decipherOnly Any additional characters shall be ignored. Any missing characters or characters that are not one of the following values, shall be treated as ‘X’. The following character values shall be supported: |

# 0 Corresponding key-usage shall not be set.

# 1 Corresponding key-usage shall be set.

X State of the corresponding key-usage does not matter.

> **EXAMPLE 1** The string values ‘1’ and ‘1XXXXXXXX’ represent settings where the key-usage type digitalSignature is set and the state of all other key-usage types do not matter.

The value of the corresponding flag in the Ff entry indicates whether this is a required constraint.


| Key | Type | Value |
| --- | --- | --- |
| Issuer | array | (Optional) An array of byte strings containing DER- encoded X.509v3 certificates of acceptable issuers. If the signer’s certificate refers to any of the specified issuers (either directly or indirectly), the certificate shall be considered acceptable for signing. The value of the corresponding flag in the Ff entry indicates whether this is a required constraint. This array may contain self-signed certificates. |
| OID | array | (Optional) An array of byte strings that contain Object Identifiers (OIDs) of the certificate policies that shall be present in the signing certificate. |

> **EXAMPLE 2** An example of such a string is:

(2.16.840.1.113733.1.7.1.1) This field shall only be used if the value of Issuer is not empty. The certificate policies extension is described in Internet RFC 5280. The value of the corresponding flag in the Ff entry indicates whether this is a required constraint.

| URL | ASCII string | (Optional) A URL, the use for which shall be defined by the URLType entry. |
| --- | --- | --- |
| URLType | Name | (Optional; PDF 1.7) A name indicating the usage of the URL entry. There are standard uses and there can be implementation-specific uses for this URL. The following value specifies a valid standard usage: Browser – The URL references content that shall be displayed in a web browser to allow enrolling for a new credential if a matching credential is not found. The Ff attribute’s URL bit shall be ignored for this usage. Third parties may extend the use of this attribute with their own attribute values, which shall conform to the guidelines described in Annex E, "Extending PDF". The default value is Browser. |

If the SignaturePolicyOID is absent, the SignaturePolicyHashValue, SignaturePolicyHashAlgorithm and SignaturePolicyCommitmentType fields shall be ignored. If the SignaturePolicyOID is present but the SignaturePolicyCommitmentType is absent, all commitments defined by the signature policy may be used.

> **NOTE** The above entries allow the creation of a signature-policy-identifier as in CAdES (ETSI 319 1221). All rules defined in CAdES apply. In particular, CAdES allows the creation of a EPES signature when the signature policy hash is not available, therefore, the absence of the SignaturePolicyHashValue does not preclude the creation of a PAdES-EPES signature.


### 12.7.6 Form actions

#### 12.7.6.1 General

Interactive forms also support special types of actions in addition to those described in 12.6.4, "Action types":

• submit-form action •    reset-form action •    import-data action 12.7.6.2            Submit-form action

Upon invocation of a submit-form action, an interactive PDF processor shall transmit the names and values of selected interactive form fields to a specified uniform resource locator (URL).

> **NOTE** Presumably, the URL is the address of a Web server that will process them and send back a response.
The value of the action dictionary’s Flags entry shall be an non-negative integer containing flags specifying various characteristics of the action. Bit positions within the flag word shall be numbered starting with 1 (low-order). "Table 240 — Flags for submit-form actions" shows the meanings of the flags; all undefined flag bits shall be reserved and shall be set to 0.

Table 239 — Additional entries specific to a submit-form action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be SubmitForm for a submit-form action. |
| F | file | (Required) A URL file specification (see 7.11.5, "URL specifications") |
| specification | giving the uniform resource locator (URL) of the script at the Web server that will process the submission. |  |
| Fields | array | (Optional) An array identifying which fields to include in the submission or which to exclude, depending on the setting of the Include/Exclude flag in the Flags entry (see "Table 240 — Flags for submit-form actions"). Each element of the array shall be either an indirect reference to a field dictionary or (PDF 1.3) a text string representing the fully qualified name of a field. Elements of both kinds may be mixed in the same array. If this entry is omitted, the Include/Exclude flag shall be ignored, and all fields in the document’s interactive form shall be submitted except those whose NoExport flag (see "Table 227 — Field flags common to all field types") is set. Fields with no values may also excluded, as dictated by the value of the IncludeNoValueFields flag; see "Table 240 — Flags for submit-form actions". |
| Flags | integer | (Optional; inheritable) A set of flags specifying various characteristics of the action (see "Table 240 — Flags for submit-form actions"). Default value: 0. |
| CharSet | string | (Optional; inheritable; PDF 2.0) Supported values include: utf-8, utf-16, Shift-JIS, BigFive, GBK, or UHC. |


Table 240 — Flags for submit-form actions

| Bit position | Name | Meaning |
| --- | --- | --- |
| Include/Exclude | If clear, the Fields array (see "Table 239 — Additional entries specific to a submit-form action") specifies which fields to include in the submission. (All descendants of the specified fields in the field hierarchy shall be submitted as well.) If set, the Fields array tells which fields to exclude. All fields in the document’s interactive form shall be submitted except those listed in the Fields array and those whose NoExport flag (see "Table 227 — Field flags common to all field types") is set and fields with no values if the IncludeNoValueFields flag is clear. |  |
| IncludeNoValueFields | If set, all fields designated by the Fields array and the Include/Exclude flag shall be submitted, regardless of whether they have a value (V entry in the field dictionary). For fields without a value, only the field name shall be transmitted. If clear, fields without a value shall not be submitted. |  |
| ExportFormat | Meaningful only if the SubmitPDF and XFDF flags are clear. If set, field names and values shall be submitted in HTML Form format. If clear, they shall be submitted in Forms Data Format (FDF); see 12.7.8, "Forms data format". |  |
| GetMethod | If set, field names and values shall be submitted using an HTTP GET request. If clear, they shall be submitted using a POST request. This flag is meaningful only when the ExportFormat flag is set; if ExportFormat is clear, this flag shall also be clear. |  |
| SubmitCoordinates | If set, the coordinates of the mouse click that caused the submit-form action shall be transmitted as part of the form data. The coordinate values are relative to the upper-left corner of the field’s widget annotation rectangle. They shall be represented in the data in the format name . x = xval & name . y = yval where name is the field’s mapping name (TM in the field dictionary) if present; otherwise, name is the field name. If the value of the TM entry is a single ASCII SPACE (20h) character, both the name and the ASCII PERIOD (2Eh) following it shall be suppressed, resulting in the format x = xval & y = yval This flag shall be used only when the ExportFormat flag is set. If ExportFormat is clear, this flag shall also be clear. |  |
| XFDF | (PDF 1.4) shall be used only if the SubmitPDF flags are clear. If set, field names and values shall be submitted as XFDF. |  |
| IncludeAppendSaves | (PDF 1.4) shall be used only when the form is being submitted in Forms Data Format (that is, when both the XFDF and ExportFormat flags are clear). If set, the submitted FDF file shall include the contents of all incremental updates to the underlying PDF document, as contained in the Differences entry in the FDF dictionary (see "Table 246 — Entries in the FDF dictionary"). If clear, the incremental updates shall not be included. |  |


| Bit position | Name | Meaning |
| --- | --- | --- |
| IncludeAnnotations | (PDF 1.4) shall be used only when the form is being submitted in Forms Data Format (that is, when both the XFDF and ExportFormat flags are clear). If set, the submitted FDF file shall include includes all markup annotations in the underlying PDF document (see 12.5.6.2, "Markup annotations"). If clear, markup annotations shall not be included. |  |
| SubmitPDF | (PDF 1.4) If set, the document shall be submitted as PDF, using the MIME media type application/pdf as defined by Internet RFC 8118. If set, all other flags shall be ignored except GetMethod. |  |

# 10 CanonicalFormat (PDF 1.4) If set, any submitted field values representing dates

shall be converted to the standard format described in 7.9.4, "Dates".

> **NOTE 1** The interpretation of a form field as a date is not specified explicitly in the field itself but only in the ECMAScript code that processes it.

# 11 ExclNonUserAnnots (PDF 1.4) shall be used only when the form is being submitted

in Forms Data Format (that is, when both the XFDF and ExportFormat flags are clear) and the IncludeAnnotations flag is set. If set, it shall include only those markup annotations whose T entry (see "Table 172 — Additional entries in an annotation dictionary specific to markup annotations") matches the name of the current user, as determined by the remote server to which the form is being submitted.

> **NOTE 2** The T entry for markup annotations specifies the text label that is displayed in the title bar of the annotation’s popup window and is assumed to represent the name of the user authoring the annotation.

> **NOTE 3** This allows multiple users to collaborate in annotating a single remote PDF document without affecting one another’s annotations.

# 12 ExclFKey (PDF 1.4) shall be used only when the form is being submitted

in Forms Data Format (that is, when both the XFDF and ExportFormat flags are clear). If set, the submitted FDF shall exclude the F entry.

# 14 EmbedForm (PDF 1.5) shall be used only when the form is being submitted

in Forms Data Format (that is, when both the XFDF and ExportFormat flags are clear). If set, the F entry of the submitted FDF shall be a file specification containing an embedded file stream representing the PDF file from which the FDF is being submitted.

The set of fields whose names and values are to be submitted shall be defined by the Fields array in the action dictionary ("Table 239 — Additional entries specific to a submit-form action") together with the Include/Exclude and IncludeNoValueFields flags in the Flags entry ("Table 240 — Flags for submitform actions"). Each element of the Fields array shall identify an interactive form field, either by an indirect reference to its field dictionary or (PDF 1.3) by its fully qualified field name (see 12.7.4.2,


"Field names"). If the Include/Exclude flag is clear, the submission consists of all fields listed in the Fields array, along with any descendants of those fields in the field hierarchy. If the Include/Exclude flag is set, the submission shall consist of all fields in the document’s interactive form except those listed in the Fields array.

The NoExport flag in the field dictionary’s Ff entry (see "Table 226 — Entries common to all field dictionaries" and "Table 227 — Field flags common to all field types") takes precedence over the action’s Fields array and Include/ Exclude flag. Fields whose NoExport flag is set shall not be included in a submit-form action.

Field names and values may be submitted in any of the following formats, depending on the settings of the action’s ExportFormat, SubmitPDF, and XFDF flags:

• HTML Form format (described in the HTML 4.01 Specification).
• Forms Data Format (FDF), which is described in 12.7.8, "Forms data format".
• XFDF, a version of FDF based on XML as defined by ISO 19444-1.
• PDF (in this case, the entire document shall be submitted rather than individual fields and values).
The name submitted for each field shall be its fully qualified name (see 12.7.4.2, "Field names"), and the value shall be specified by the V entry in its field dictionary.

For push-button fields submitted in FDF, the value submitted shall be that of the AP entry in the field’s widget annotation dictionary. If the submit-form action dictionary contains no Fields entry, such pushbutton fields shall not be submitted.

Fields with no value (that is, whose field dictionary does not contain a V entry) are ordinarily not included in the submission. The submit-form action’s IncludeNoValueFields flag may override this behaviour. If this flag is set, such valueless fields shall be included in the submission by name only, with no associated value.

#### 12.7.6.3 Reset-form action

Upon invocation of a reset-form action, an interactive PDF processor shall reset selected interactive form fields to their default values; that is, it shall set the value of the V entry in the field dictionary to that of the DV entry (see "Table 226 — Entries common to all field dictionaries"). If no default value is defined for a field, its V entry shall be removed. For fields that can have no value (such as pushbuttons), the action has no effect. "Table 241 — Additional entries specific to a reset-form action" shows the action dictionary entries specific to this type of action.

The value of the action dictionary’s Flags entry is a non-negative containing flags specifying various characteristics of the action. Bit positions within the flag word shall be numbered starting from 1 (loworder). Only one flag is defined for this type of action. All undefined flag bits shall be reserved and shall be set to 0.


Table 241 — Additional entries specific to a reset-form action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be ResetForm for a reset-form action. |
| Fields | array | (Optional) An array identifying which fields to reset or which to exclude from resetting, depending on the setting of the Include/ Exclude flag in the Flags entry (see "Table 242 — Flag for reset-form actions"). Each element of the array shall be either an indirect reference to a field dictionary or (PDF 1.3) a text string representing the fully qualified name of a field. Elements of both kinds may be mixed in the same array. If this entry is omitted, the Include/Exclude flag shall be ignored; all fields in the document’s interactive form are reset. |
| Flags | integer | (Optional; inheritable) A set of flags specifying various characteristics of the action (see "Table 242 — Flag for reset-form actions"). Default value: 0. |

Table 242 — Flag for reset-form actions

Bit position Name Meaning

# 1 Include/Exclude If clear, the Fields array (see "Table 241 — Additional

entries specific to a reset-form action") specifies which fields to reset. (All descendants of the specified fields in the field hierarchy are reset as well.) If set, the Fields array indicates which fields to exclude from resetting; that is, all fields in the document’s interactive form shall be reset except those listed in the Fields array.

#### 12.7.6.4 Import-data action

Upon invocation of an import-data action, a PDF processor shall import data (see “Table 243 — Additional entries specific to an import-data action”) from Forms Data Format (FDF), XFDF (XMLbased Forms Data Format according to ISO 19444-1) or any other data format that it supports into the document’s interactive form from a specified file.

Table 243 — Additional entries specific to an import-data action

| Key | Type | Value |
| --- | --- | --- |
| S | name | (Required) The type of action that this dictionary describes; shall be ImportData for an import-data action. |
| F | file specification | (Required) The FDF, XFDF or any other data format file from which to import the data. |


### 12.7.7 Named pages

The optional Pages entry (PDF 1.3) in a document’s name dictionary (see 7.7.4, "Name dictionary") contains a name tree that maps name strings to individual pages within the document. Naming a page allows it to be referenced in two different ways:

• An import-data action can add the named page to the document into which FDF is being imported, either as a page or as a button appearance.
• A script executed by an ECMAScript action can add the named page to the current document as a regular page.
| A named page that is intended to be visible to a user shall be left in the page tree ( | see 7.7.3, "Page |
| --- | --- |
| tree"), and there shall be a reference to it in the appropriate leaf node of the name dictionary’s | Pages |

tree. If the page is not intended to be displayed by the PDF processor, it shall be referenced from the name dictionary’s Templates tree instead. Such invisible pages shall have an object type of Template rather than Page and shall have no Parent or B entry (see "Table 31 — Entries in a page object").

### 12.7.8 Forms data format

#### 12.7.8.1 General

This subclause describes Forms Data Format (FDF), the file format used for interactive form data (PDF 1.2). FDF can be used when submitting form data to a server, receiving the response, and incorporating it into the interactive form. It can also be used to export form data to stand-alone files that can be stored, transmitted electronically, and imported back into the corresponding PDF interactive form. In addition, beginning in PDF 1.3, FDF can be used to define a container for annotations that are separate from the PDF document to which they apply.

FDF is based on PDF; it uses the same syntax and has essentially the same file structure (7.5, "File structure"). However, it differs from PDF in the following ways:

• The cross-reference table (7.5.4, "Cross-reference table") is optional.
• FDF files shall not be updated (see 7.5.6, "Incremental updates"). Objects shall only be of generation 0, and no two objects within an FDF file shall have the same object number.
• The document structure is much simpler than PDF, since the body of an FDF document consists of only one required object.
• The length of a stream shall not be specified by an indirect object.
FDF shall use the MIME media type application/vnd.fdf. On the Microsoft WindowsTM and UNIX platforms, FDF files shall have the extension .fdf; on Mac OS, they shall have file type 'FDF'.

#### 12.7.8.2 FDF file structure

##### 12.7.8.2.1 General

An FDF file shall be structured in essentially the same way as a PDF file but contains only those elements required for the export and import of interactive form and annotation data. It consists of three required elements and one optional element (see "Figure 85 — FDF file structure"):


• A one-line header identifying the version number of the PDF specification to which the file conforms •    A body containing the objects that make up the content of the file •    An optional cross-reference table containing information about the indirect objects in the file •    An optional trailer giving the location of the cross-reference table and of certain special objects within the body of the file

Figure 85 — FDF file structure 12.7.8.2.2         FDF header

The first line of an FDF file shall be a header, which shall contain

%FDF-1.2

The version number is given by the Version entry in the FDF catalog dictionary (see 12.7.8.3, "FDF catalog").

##### 12.7.8.2.3 FDF body

The body of an FDF file shall consist of a sequence of indirect objects representing the file’s catalog dictionary (see 12.7.8.3, "FDF catalog") and any additional objects that the catalog dictionary references. The objects are of the same basic types described in 7.5, "File structure" (other than the %PDF–n.m and %%EOF comments described in 7.5, "File structure") have no semantics. They are not necessarily preserved by PDF processors. Just as in PDF, objects in FDF can be direct or indirect.

##### 12.7.8.2.4 FDF trailer

The trailer of an FDF file enables an interactive PDF processor to find significant objects quickly within the body of the file. The last line of the file contains only the end-of-file marker, %%EOF. This marker shall be preceded by the FDF trailer dictionary, consisting of the keyword trailer followed by a series


of one or more key-value pairs enclosed in double angle brackets (<<…>>) (using LESS-THAN SIGNs (3Ch) and GREATER-THAN SIGNs (3Eh)). The only required key is Root whose value shall be an indirect reference to the file’s catalog dictionary (see "Table 244 — Entry in the FDF trailer dictionary"). The trailer may optionally contain additional entries for objects that are referenced from within the catalog dictionary.

Table 244 — Entry in the FDF trailer dictionary

| Key | Type | Value |
| --- | --- | --- |
| Root | dictionary | (Required; shall be an indirect reference) The catalog dictionary object for this FDF file (see 12.7.8.3, "FDF catalog"). |

Thus, the trailer has the overall structure

trailer <</Root c 0 R key2 value2 … keyn valuen >> %%EOF

where c is the object number of the file’s catalog dictionary.

#### 12.7.8.3 FDF catalog

##### 12.7.8.3.1 General

The root node of an FDF file’s object hierarchy is the catalog dictionary, located by means of the Root entry in the file’s trailer dictionary (see 12.7.8.2.4, "FDF trailer"). As shown in "Table 245 — Entries in the FDF catalog dictionary", the only required entry in the catalog dictionary is FDF; its value shall be an FDF dictionary ("Table 246 — Entries in the FDF dictionary"), which in turn shall contain references to other objects describing the file’s contents. The catalog dictionary may also contain an optional Version entry identifying the version of the PDF specification to which this FDF file conforms. “Table 245 — Entries in the FDF catalog dictionary” describes the entries in the FDF catalog dictionary.

Table 245 — Entries in the FDF catalog dictionary

| Key | Type | Value |
| --- | --- | --- |
| Version | name | (Optional; PDF 1.4) The version of the FDF specification to which this FDF file conforms (for example, 1.4) if later than the version specified in the file’s header (see 12.7.8.2.2, "FDF header"). If the header specifies a later version, or if this entry is absent, the document conforms to the version specified in the header. The value of this entry is a name object, not a number, and therefore shall be preceded by a slash character (/) when written in the FDF file (for example, /1.4). |


| Key | Type | Value |
| --- | --- | --- |
| FDF | dictionary | (Required) The FDF dictionary for this file (see "Table 246 — Entries in the FDF dictionary"). |

Table 246 — Entries in the FDF dictionary

| Key | Type | Value |
| --- | --- | --- |
| F | file specification | (Optional) The source file or target file: the PDF document file that this FDF file was exported from or is intended to be imported into. |
| ID | array | (Optional) An array of two byte strings constituting a file identifier (see 14.4, "File identifiers") for the source or target file designated by F, taken from the ID entry in the file’s trailer dictionary (see 7.5.5, "File trailer"). |
| Fields | array | (Optional) An array of FDF field dictionaries (see 12.7.8.3.2, "FDF fields" in 12.7.8.3, "FDF catalog") describing the root fields (those with no ancestors in the field hierarchy) that shall be exported or imported. This entry and the Pages entry shall not both be present. |
| Status | PDFDocEncoded | (Optional) A status string that shall be displayed indicating the |
| string | result of an action, typically a submit-form action (see 12.7.6.2, "Submit-form action"). The string shall be encoded with PDFDocEncoding. This entry and the Pages entry shall not both be present. |  |
| Pages | array | (Optional; PDF 1.3) An array of FDF page dictionaries (see 12.7.8.3.3, "FDF pages") describing pages that shall be added to a PDF target document. The Fields and Status entries shall not be present together with this entry. |
| Encoding | name | (Optional; PDF 1.3) The encoding that shall be used for any FDF field value or option (V or Opt in the field dictionary; see "Table 249 — Entries in an FDF field dictionary") or field name that is a string and does not begin with the Unicode prefix ZERO WIDTH NO-BREAK SPACE (U+FEFF). Default value: PDFDocEncoding. Other allowed values include Shift_JIS, BigFive, GBK, UHC, utf_8, utf_16 |
| Annots | array | (Optional; PDF 1.3) An array of FDF annotation dictionaries (see 12.7.8.3.4, "FDF annotation dictionaries" in 12.7.8.3, "FDF catalog"). The array may include annotations of any of the standard types listed in "Table 171 — Annotation types" except Link, Movie, Widget, PrinterMark, Screen, and TrapNet. |


| Key | Type | Value |
| --- | --- | --- |
| Differences | stream | (Optional; PDF 1.4) A stream containing all the bytes in all incremental updates made to the underlying PDF document since it was opened (see 7.5.6, "Incremental updates"). If a submit-form action submitting the document to a remote server as FDF has its IncludeAppendSaves flag set (see 12.7.6.2, "Submit-form action"), the contents of this stream shall be included in the submission. This allows any digital signatures (see 12.8, "Digital signatures") to be transmitted to the server. An incremental update shall be automatically performed just before the submission takes place, in order to capture all changes made to the document. The submission shall include the full set of incremental updates back to the time the document was first opened, even if some of them may already have been included in intervening submissions. Although a Fields or Annots entry (or both) may be present along with Differences, there is no requirement that their contents will be consistent with each other. In particular, if Differences contains a digital signature, only the values of the form fields given in the Differences stream shall be considered trustworthy under that signature. |
| Target | string | (Optional; PDF 1.4) The name of a browser frame in which the underlying PDF document shall be opened. This mimics the behaviour of the target attribute in HTML <href> tags. |
| EmbeddedFDFs | array | (Optional; PDF 1.4) An array of file specifications (see 7.11, "File specifications") representing other FDF files embedded within this one (7.11.4, "Embedded file streams"). |
| JavaScript | dictionary | (Optional; PDF 1.4) An ECMAScript dictionary (see "Table 248 — Entries in the ECMAScript dictionary") defining document-level ECMAScript scripts. |

Although deprecated in PDF 2.0, embedded FDF files specified in the FDF dictionary’s EmbeddedFDFs entry may be encrypted. Besides the usual entries for an embedded file stream, the stream dictionary representing such an encrypted FDF file shall contain the additional entry shown in "Table 247 — Additional entry in an embedded file stream dictionary for an encrypted FDF file" to identify the revision number of the FDF encryption algorithm used to encrypt the file. Although the FDF encryption mechanism is separate from the one for PDF file encryption described in 7.6, "Encryption" revision 1 (the only one defined) uses a similar RC4 encryption algorithm based on a 40-bit encryption key. The key shall be computed by means of an MD5 hash, using a padded user-supplied password as input. The computation shall be identical to steps (a) and (b) of the "Algorithm 2: Computing a file encryption key in order to encrypt a document (revision 4 and earlier)" in 7.6.4.3, "File encryption key algorithm"; the first 5 bytes of the result shall be the file encryption key for the embedded FDF file.


Table 247 — Additional entry in an embedded file stream dictionary for an encrypted FDF file

| Key | Type | Value |
| --- | --- | --- |
| EncryptionRevision | integer | (Required if the FDF file is encrypted; deprecated in PDF 2.0) The revision number of the FDF encryption algorithm used to encrypt the file. This value shall be 1. |

The JavaScript entry in the FDF dictionary holds an ECMAScript dictionary containing ECMAScript scripts that shall be defined globally at the document level, rather than associated with individual fields. The dictionary may contain scripts defining ECMAScript functions for use by other scripts in the document, as well as scripts that shall be executed immediately before and after the FDF file is imported. "Table 248 — Entries in the ECMAScript dictionary" shows the contents of this dictionary.

Table 248 — Entries in the ECMAScript dictionary

| Key | Type | Value |
| --- | --- | --- |
| Before | text string or | (Optional) A text string or text stream containing an |
| text stream | ECMAScript script that shall be executed just before the FDF file is imported. |  |
| After | text string or | (Optional) A text string or text stream containing an |
| text stream | ECMAScript script that shall be executed just after the FDF file is imported. |  |
| AfterPermsReady | text string or | (Optional; PDF 1.6) A text string or text stream containing an |
| text stream | ECMAScript script that shall be executed after the FDF file is imported and the usage rights in the PDF document have been determined (see 12.8.2.3, "UR"). |  |
| Doc | Array | (Optional) An array defining additional ECMAScript scripts that shall be added to those defined in the JavaScript entry of the document’s name dictionary (see 7.7.4, "Name dictionary"). The array shall contain an even number of elements, organised in pairs. The first element of each pair shall be a name and the second shall be a text string or text stream defining the script corresponding to that name. Each of the defined scripts shall be added to those already defined in the name dictionary and shall then be executed before the script defined in the Before entry is executed. |

> **NOTE** As described in 12.6.4.17, "ECMAScript actions" these scripts are used to define ECMAScript functions for use by other scripts in the document.

##### 12.7.8.3.2 FDF fields

Each field in an FDF file shall be described by an FDF field dictionary. "Table 249 — Entries in an FDF field dictionary" shows the contents of this type of dictionary. Most of the entries have the same form and meaning as the corresponding entries in a field dictionary ("Table 226 — Entries common to all field dictionaries", "Table 228 — Additional entries common to all fields containing variable text", "Table 232 — Additional entry specific to a text field", and "Table 234 — Additional entries specific to a


choice field") or a widget annotation dictionary ("Table 170 — Entries in an appearance dictionary" and "Table 191 — Additional entries specific to a widget annotation"). Unless otherwise indicated in the table, importing a field causes the values of the entries in the FDF field dictionary to replace those of the corresponding entries in the field with the same fully qualified name in the target document.

Table 249 — Entries in an FDF field dictionary

| Key | Type | Value |
| --- | --- | --- |
| Kids | array | (Optional) An array containing the immediate children of this field. Unlike the children of fields in a PDF file, which shall be specified as indirect object references, those of an FDF field may be either direct or indirect objects. |
| T | text string | (Required) The partial field name (see 12.7.4.2, "Field names"). |
| V | (various) | (Optional) The field’s value, whose format varies depending on the field type; see the descriptions of individual field types in 12.7.5, "Field types" for further information. |
| Ff | integer | (Optional) A set of flags specifying various characteristics of the field (see "Table 227 — Field flags common to all field types", "Table 229 — Field flags specific to button fields", "Table 231 — Field flags specific to text fields", and "Table 233 — Field flags specific to choice fields"). When imported into an interactive form, the value of this entry shall replace that of the Ff entry in the form’s corresponding field dictionary. If this field is present, the SetFf and ClrFf entries, if any, shall be ignored. |
| SetFf | integer | (Optional) A set of flags to be set (turned on) in the Ff entry of the form’s corresponding field dictionary. Bits equal to 1 in SetFf shall cause the corresponding bits in Ff to be set to 1. This entry shall be ignored if an Ff entry is present in the FDF field dictionary. |
| ClrFf | integer | (Optional) A set of flags to be cleared (turned off) in the Ff entry of the form’s corresponding field dictionary. Bits equal to 1 in ClrFf shall cause the corresponding bits in Ff to be set to 0. If a SetFf entry is also present in the FDF field dictionary, it shall be applied before this entry. This entry shall be ignored if an Ff entry is present in the FDF field dictionary. |
| F | integer | (Optional) A set of flags specifying various characteristics of the field’s widget annotation (see 12.5.3, "Annotation flags"). When imported into an interactive form, the value of this entry shall replace that of the F entry in the form’s corresponding annotation dictionary. If this field is present, the SetF and ClrF entries, if any, shall be ignored. |
| SetF | integer | (Optional) A set of flags to be set (turned on) in the F entry of the form’s corresponding widget annotation dictionary. Bits equal to 1 in SetF shall cause the corresponding bits in F to be set to 1. This entry shall be ignored if an F entry is present in the FDF field dictionary. |
| ClrF | integer | (Optional) A set of flags to be cleared (turned off) in the F entry of the form’s corresponding widget annotation dictionary. Bits equal to 1 in ClrF shall cause the corresponding bits in F to be set to 0. If a SetF entry is also present in the FDF field dictionary, it shall be applied before this entry. This entry shall be ignored if an F entry is present in the FDF field dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| AP | dictionary | (Optional) An appearance dictionary specifying the appearance of a push- button field (see 12.7.5.2.2, "Push-buttons"). The appearance dictionary’s contents are as shown in "Table 170 — Entries in an appearance dictionary", except that the values of the N, R, and D entries shall all be streams. |
| APRef | dictionary | (Optional; PDF 1.3) A dictionary holding references to external PDF files containing the pages to use for the appearances of a push-button field. This dictionary is similar to an appearance dictionary (see "Table 170 — Entries in an appearance dictionary"), except that the values of the N, R, and D entries shall all be named page reference dictionaries ("Table 253 — Entries in an FDF named page reference dictionary"). This entry shall be ignored if an AP entry is present. |
| IF | dictionary | (Optional; PDF 1.3; button fields only) An icon fit dictionary (see "Table 250 — Entries in an icon fit dictionary") specifying how to display a button field’s icon within the annotation rectangle of its widget annotation. |
| Opt | array | (Required; choice fields only) An array of options that shall be presented to the user. Each element of the array shall take one of two forms: A text string representing one of the available options A two-element array consisting of a text string representing one of the available options and a default appearance string for constructing the item’s appearance dynamically at viewing time (12.7.4.3, "Variable text"). |
| A | dictionary | (Optional) An action that shall be performed when this field’s widget annotation is activated (see 12.6, "Actions"). |
| AA | dictionary | (Optional) An additional-actions dictionary defining the field’s behaviour |
| in response to various trigger events ( | see 12.6.3, "Trigger events"). |  |
| RV | text string or | (Optional; PDF 1.5) A rich text string, as in Adobe XML Architecture, XML |
| text stream | Forms Architecture (XFA) Specification, version 3.3. |  |

In an FDF field dictionary representing a button field, the optional IF entry holds an icon fit dictionary (PDF 1.3) specifying how to display the button’s icon within the annotation rectangle of its widget annotation. "Table 250 — Entries in an icon fit dictionary" shows the contents of this type of dictionary.

Table 250 — Entries in an icon fit dictionary

| Key | Type | Value |
| --- | --- | --- |
| SW | name | (Optional) The circumstances under which the icon shall be scaled inside the annotation rectangle: |

# A Always scale.

# B Scale only when the icon is bigger than the annotation rectangle.

S Scale only when the icon is smaller than the annotation rectangle.

# N Never scale.

Default value: A.


| Key | Type | Value |
| --- | --- | --- |
| S | name | (Optional) The type of scaling that shall be used: |

# A Anamorphic scaling: Scale the icon to fill the annotation rectangle

exactly, without regard to its original aspect ratio (ratio of width to height).

# P Proportional scaling: Scale the icon to fit the width or height of the

annotation rectangle while maintaining the icon’s original aspect ratio.
If the required horizontal and vertical scaling factors are different, use the smaller of the two, centring the icon within the annotation rectangle in the other dimension.
Default value: P.

| A | array | (Optional) An array of two numbers that shall be between 0.0 and 1.0 indicating the fraction of leftover space to allocate at the left and bottom of the icon. A value of [0.0 0.0] shall position the icon at the bottom-left corner of the annotation rectangle. A value of [0.5 0.5] shall centre it within the rectangle. This entry shall be used only if the icon is scaled proportionally. Default value: [0.5 0.5]. |
| --- | --- | --- |
| FB | boolean | (Optional; PDF 1.5) If true, indicates that the button appearance shall be scaled to fit fully within the bounds of the annotation without taking into consideration the line width of the border. Default value: false. |

##### 12.7.8.3.3 FDF pages

The optional Pages field in an FDF dictionary (see "Table 246 — Entries in the FDF dictionary") shall contain an array of FDF page dictionaries (PDF 1.3) describing new pages that shall be added to the target document. "Table 251 — Entries in an FDF page dictionary" shows the contents of this type of dictionary.

Table 251 — Entries in an FDF page dictionary

| Key | Type | Value |
| --- | --- | --- |
| Templates | array | (Required) An array of FDF template dictionaries (see "Table 252 — Entries in an FDF template dictionary") that shall describe the named pages that serve as templates on the page. |
| Info | dictionary | (Optional) An FDF page information dictionary that shall contain additional information about the page. |

An FDF template dictionary shall contain information describing a named page that serves as a template. "Table 252 — Entries in an FDF template dictionary" shows the contents of this type of dictionary.


Table 252 — Entries in an FDF template dictionary

| Key | Type | Value |
| --- | --- | --- |
| TRef | dictionary | (Required) A named page reference dictionary (see "Table 253 — Entries in an FDF named page reference dictionary") that shall specify the location of the template. |
| Fields | array | (Optional) An array of references to FDF field dictionaries (see "Table 249 — Entries in an FDF field dictionary") describing the root fields that shall be imported (those with no ancestors in the field hierarchy). |
| Rename | boolean | (Optional) A flag that shall specify whether fields imported from the template shall be renamed in the event of name conflicts with existing fields. If this flag is true, fields with such conflicting names shall be renamed to guarantee their uniqueness. If false, the fields shall not be renamed; this results in multiple fields with the same name in the target document. Each time the FDF file provides attributes for a given field name, all fields with that name shall be updated. See the |

> **NOTE** in this subclause for further discussion.
Default value: true.

> **NOTE** The names of fields imported from a template can sometimes conflict with those of existing fields in the target document. This can occur, for example, if the same template page is imported more than once or if two different templates have fields with the same names.
Although the Rename flag does not define a renaming algorithm, this might be implemented by a PDF processor renaming fields by prepending a page number, a template name, and an ordinal number to the field name.

The TRef entry in an FDF template dictionary shall hold a named page reference dictionary that shall describe the location of external templates or page elements. "Table 253 — Entries in an FDF named page reference dictionary" shows the contents of this type of dictionary.

Table 253 — Entries in an FDF named page reference dictionary

| Key | Type | Value |
| --- | --- | --- |
| Name | string | (Required) The name of the referenced page. |
| F | file specification | (Optional) The file containing the named page. If this entry is absent, it shall be assumed that the page resides in the associated PDF file. |

##### 12.7.8.3.4 FDF annotation dictionaries

Each annotation dictionary in an FDF file shall have a Page entry (see "Table 254 — Additional entry for annotation dictionaries in an FDF file") that shall indicate the page of the source document to which the annotation is attached.


Table 254 — Additional entry for annotation dictionaries in an FDF file

| Key | Type | Value |
| --- | --- | --- |
| Page | integer | (Required for annotations in FDF files) The ordinal page number on which this annotation shall appear, where page 0 is the first page. |

### 12.7.9 Non-interactive forms

Unlike interactive forms, non-interactive forms do not use widget annotations but are represented with page content. Non-interactive forms are defined by the PrintField attrib0ute (14.8.5.6, "PrintField attributes") for repurposing and accessibility purposes.

## 12.8 Digital signatures

### 12.8.1 General

A digital signature (PDF 1.3) may be used to verify the integrity of the document’s contents using verification information related to a signer. The signature may be purely mathematical, such as a public/private-key encrypted document digest, or it may be a biometric form of identification, such as a handwritten signature, fingerprint, or retinal scan. The specific form of authentication used shall be implemented by a special software module called a signature handler. Signature handlers shall be identified in accordance with the rules defined in Annex E, "Extending PDF".

Digital signatures in PDF support four activities:

• the addition of a digital signature to a document, •    the verification of the validity of a signature added to a document, •    the addition of DSS dictionaries and of validation related information (VRI) to allow for later verifications (see 12.8.4.4, "Validation-related information (VRI)"), and •    the addition of document timestamp dictionaries (DTS) to allow for later verifications (see 12.8.5, "Document timestamp (DTS) dictionary").
PDF 2.0 processors should support digital signatures based on the Cryptographic Message Syntax (CMS) and CAdES (ETSI EN 319 122).

Signature information shall be contained in a signature dictionary, whose entries are listed in "Table 255 — Entries in a signature dictionary". Signature handlers may use or omit those entries that are marked optional in the table but should use them in a standard way if they are used at all. In addition, signature handlers may add private entries of their own. To avoid name duplication, the keys for all such private entries shall be prefixed with the registered handler name followed by a PERIOD (2Eh).

Signatures shall be created using an appropriate signature handler. That signature handler shall match with the type of the signature that is intended to be created. It shall compute a digest using a cryptographic hash function over the data of the document, and store it in the document.

To verify the signature, an appropriate signature handler is required. That signature handler shall match with the type of the signature that has been created. The signer’s certificate shall be determined and verified by the signature handler to match with any of the validation parameters and other


conditions. If the verification fails, the signature shall be considered invalid. The digest shall be recomputed and compared with the one stored in the document. Differences between the two indicates that modifications have been made since the document was signed and thus the signature shall be considered invalid.

> **NOTE 1** If a signed document is modified and saved by incremental update (see 7.5.6, "Incremental updates"), the data corresponding to the byte range of the original signature is preserved.
Therefore, if the signature is valid, the state of the document can be recreated as it existed at the time of signing.
There are two defined techniques for computing a digital signature of the contents of a PDF file:

• A byte range digest shall be computed over a range of bytes in the PDF file, that shall be indicated
| by the ByteRange entry in the signature dictionary. This range should be the entire | PDF file, |
| --- | --- |
| including the signature dictionary but excluding the signature value itself (the | Contents entry). In |

case of multiple digital signatures this range shall be the sequence of bytes starting from the "%PDF-" comment at the beginning of the PDF document to the end of the "%%EOF" comment, possibly followed by an optional EOL marker, terminating the incremental update that adds the digital signature dictionary to the document.  When a byte range digest is present, all values in the signature dictionary shall be direct objects.
• Additionally, modification detection may be specified by a signature reference dictionary. The TransformMethod entry shall specify the general method for modification detection, and the TransformParams entry shall specify the variable portions of the method.
A PDF document may contain the following standard types of signatures: •    At most one usage rights signature (PDF 1.5, deprecated in PDF 2.0). It shall only be applied if no other type of signature is already present. Its signature dictionary shall be referenced from the UR3 (PDF 1.6) entry in the permissions dictionary, whose entries are listed in "Table 263 — Entries in a permissions dictionary", (not from a signature field). The signature dictionary shall contain a Reference entry whose value is a signature reference dictionary that has a UR transform method. See 12.8.2.3, "UR" for information on how these signatures shall be created and validated. When a usage rights signature is present, it is up to the PDF processor or to the signature handler to process it or not.
• At most one certification signature (also known as author signature) (PDF 1.5). The signature dictionary of a certification signature shall be the value of a signature field and shall contain a ByteRange entry. It may also be referenced from the DocMDP entry in the permissions dictionary (see Table 263 — Entries in a permissions dictionary). The signature dictionary shall contain a signature reference dictionary (see "Table 256 — Entries in a signature reference dictionary") that has a DocMDP transform method. See 12.8.2.2, "DocMDP" for information on how these signatures shall be created and validated.
• One or more approval signatures (also known as recipient signatures). These shall follow the certification signature if one is present. The signature dictionary of an approval signature shall be the value of a signature field and shall contain a ByteRange entry.

> **NOTE 2** A signature dictionary for a certification or approval signature can also have a signature reference dictionary with a FieldMDP transform method; see 12.8.2.4, "FieldMDP".
• Any number of document timestamp signatures, see 12.8.5, "Document timestamp (DTS) dictionary". The timestamp signature dictionary of a document timestamp signature shall be the value of a signature field and shall contain a ByteRange entry.
A signature dictionary is used by all of these types of signatures.


Table 255 — Entries in a signature dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional if Sig; Required if DocTimeStamp) The type of PDF object that this dictionary describes; if present, shall be Sig for a signature dictionary or DocTimeStamp for a timestamp signature dictionary. The default value is: Sig. |
| Filter | name | (Required; inheritable) The name of the preferred signature handler to use when validating this signature. If the Prop_Build entry is not present, it shall be also the name of the signature handler that was used to create the signature. If Prop_Build is present, it may be used to determine the name of the handler that created the signature (which is typically the same as Filter but is not needed to be). A PDF processor may substitute a different handler when verifying the signature, as long as it supports the specified SubFilter format. Example signature handlers are Adobe.PPKLite, Entrust.PPKEF, CICI.SignIt, and VeriSign.PPKVS. The name of the filter (i.e. signature handler) |
| shall be identified in accordance with the rules defined in | Annex E, "Extending PDF". |  |
| SubFilter | name | (Optional) A name that describes the encoding of the signature value and key information in the signature dictionary. A PDF processor may use any handler that supports this format to validate the signature. (PDF 1.6) The following values for public-key cryptographic signatures should be used: adbe.x509.rsa_sha1, adbe.pkcs7.detached, adbe.pkcs7.sha1, ETSI.CAdES.detached (PDF 2.0) and ETSI.RFC3161 (PDF 2.0). (PDF 2.0) When the Type of this dictionary is DocTimeStamp, the SubFilter value should be ETSI.RFC3161 and when the Type of this dictionary is Sig (possibly by default) any of the values may be used except ETSI.RFC3161 (see 12.8.3, "Signature interoperability"). Other values may be defined by developers, and when used, shall be prefixed with the registered developer identification. All prefix names shall be registered (see Annex E, "Extending PDF"). The prefix "adbe" and the prefix "ETSI" have been registered by Adobe Systems and ETSI, respectively, and the subfilter names listed above and defined in 12.8.3, "Signature interoperability" and 12.8.5, "Document timestamp (DTS) dictionary" may be used by any developer. The values adbe.x509.rsa_sha1 and adbe.pkcs7.sha1 have been deprecated with PDF 2.0. To support backward compatibility, PDF readers should process these values for this key, but PDF writers shall not use this value for this key. |
| Contents | byte | (Required) The signature value. When ByteRange is present, the value shall |
| string | be a hexadecimal string (see 7.3.4.3, "Hexadecimal strings") representing the value of the byte range digest. For public-key signatures, Contents should be either a DER-encoded PKCS #1 binary data object, a DER-encoded CMS binary data object or a DER-encoded CMS SignedData binary data object. For document timestamp signatures, Contents shall be the TimeStampToken as specified in Internet RFC 3161 as updated by Internet RFC 5816. The value of the messageImprint field within the TimeStampToken shall be a hash of the bytes of the document indicated by the ByteRange and the ByteRange shall specify the complete PDF file contents (excepting the Contents value). Space for the Contents value shall be allocated before the message digest is computed (see 7.3.4, "String objects"). |  |


| Key | Type | Value |
| --- | --- | --- |
| Cert | byte | (Required when SubFilter is adbe.x509.rsa_sha1) An array of byte strings that |
| string or | shall represent the X.509 certificate chain used when signing and verifying |  |
| array | signatures that use public-key cryptography, or a byte string if the chain has only one entry. The signing certificate shall appear first in the array; it shall be used to verify the signature value in Contents, and the other certificates shall be used to verify the authenticity of the signing certificate. If SubFilter is adbe.pkcs7.detached or adbe.pkcs7.sha1, this entry shall not be used, and the certificate chain shall be put in the CMS envelope in Contents. If SubFilter is ETSI.CAdES.detached or ETSI.RFC3161, this entry shall not be used, and the certificate chain shall be put in the DER-encoded CMS SignedData object in Contents. |  |
| ByteRange | array | (Required for all signatures that are part of a signature field and usage rights signatures referenced from the UR3 entry in the permissions dictionary; shall be direct objects) An array of pairs of integers (starting byte offset, length in bytes) that shall describe the exact byte range for the digest calculation. |
| Multiple discontiguous byte ranges shall be used to | describe a digest that does |  |
| not include the signature value (the | Contents entry) itself. If SubFilter is ETSI.CAdES.detached or ETSI.RFC3161, the ByteRange shall cover the entire PDF file, including the signature dictionary but excluding the Contents value. When a byte range digest is present, all values in the signature dictionary shall be direct objects. |  |
| Reference | array | (Optional; PDF 1.5) An array of signature reference dictionaries (see "Table 256 — Entries in a signature reference dictionary"). If SubFilter is ETSI.RFC3161, this entry shall not be used. |
| Changes | array | (Optional) An array of three integers that shall specify changes to the document that have been made between the previous signature and this signature: in this order, the number of pages altered, the number of fields altered, and the number of fields filled in. The ordering of signatures shall be determined by the value of ByteRange. Since each signature results in an incremental save, later signatures have a greater length value. If SubFilter is ETSI.RFC3161, this entry shall not be used. |
| Name | text string | (Optional) The name of the person or authority signing the document. This value should be used only when it is not possible to extract the name from the signature. |

> **EXAMPLE 1** From the certificate of the signer.

If SubFilter is ETSI.RFC3161, this entry should not be used and should be ignored by a PDF processor.


| Key | Type | Value |
| --- | --- | --- |
| M | date | (Optional) The time of signing. Depending on the signature handler, this may be a normal unverified computer time or a time generated in a verifiable way from a secure time server. This value should be used only when the time of signing is not available in the signature. If SubFilter is ETSI.RFC3161, this entry should not be used and should be ignored by a PDF processor. |

> **EXAMPLE 2** A timestamp can be embedded in a CMS binary data object (see 12.8.3.3, "CMS (PKCS #7) signatures").

| Location | text string | (Optional) The CPU host name or physical location of the signing. If SubFilter is ETSI.RFC3161, this entry should not be used and should be ignored by a PDF processor. |
| --- | --- | --- |
| Reason | text string | (Optional) The reason for the signing, such as ( I agree…). If SubFilter is ETSI.RFC3161, this entry should not be used and should be ignored by a PDF processor. |
| ContactInfo | text string | (Optional) Information provided by the signer to enable a recipient to contact the signer to verify the signature. If SubFilter is ETSI.RFC3161, this entry should not be used and should be ignored by a PDF processor. |

> **EXAMPLE 3** A phone number.

| R | integer | (Optional; deprecated in PDF 2.0) The version of the signature handler that was used to create the signature. (PDF 1.5) This entry shall not be used, and the information shall be stored in the Prop_Build dictionary. |
| --- | --- | --- |
| V | integer | (Optional; PDF 1.5) The version of the signature dictionary format. It corresponds to the usage of the signature dictionary in the context of the value of SubFilter. The value is 1 if the Reference dictionary shall be considered critical to the validation of the signature. If SubFilter is ETSI.RFC3161, this V value shall be 0 (possibly by default). Default value: 0. |
| Prop_Build | dictionary | (Optional; PDF 1.5) A dictionary that may be used by a signature handler to record information that captures the state of the computer environment used for signing, such as the name of the handler used to create the signature, software build date, version, and operating system. The use of this dictionary is defined by Adobe PDF Signature Build Dictionary Specification, which provides implementation guidelines. |
| Prop_AuthTime | integer | (Optional; PDF 1.5) The number of seconds since the signer was last authenticated, used in claims of signature repudiation. It should be omitted if the value is unknown. If SubFilter is ETSI.RFC3161, this entry shall not be used. |
| Prop_AuthType | name | (Optional; PDF 1.5) The method that shall be used to authenticate the signer, used in claims of signature repudiation. Valid values shall be PIN, Password, and Fingerprint. If SubFilter is ETSI.RFC3161, this entry shall not be used. |

> **NOTE 3** The entries in the signature dictionary can be conceptualized as being in different dictionaries; they are in one dictionary for historical and cryptographic reasons. The categories are signature properties (R, M, Name, Reason, Location, Prop_Build, Prop_AuthTime, and


Prop_AuthType); key information (Cert and portions of Contents when the signature value is a CMS object, CMS object, or a document timestamp token); reference (Reference and ByteRange); and signature value (Contents when the signature value is a PKCS #1 object).

Table 256 — Entries in a signature reference dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be SigRef for a signature reference dictionary. |
| TransformMethod | name | (Required) The name of the transform method (see 12.8.2, "Transform methods") that shall guide the modification analysis that takes place when the signature is validated. Valid values shall be: |
| DocMDP | Used to detect modifications to a document relative to a signature field that is signed by the originator of a document; see 12.8.2.2, "DocMDP" |  |
| UR | (Deprecated in PDF 2.0) Used to detect modifications to a document that would invalidate a signature in a rights-enabled document; see 12.8.2.3, "UR" |  |
| FieldMDP | Used to detect modifications to a list of form fields specified in TransformParams; see 12.8.2.4, "FieldMDP" |  |
| TransformParams | dictionary | (Optional) A dictionary specifying transform parameters (variable data) for the transform method specified by TransformMethod. Each method takes its own set of parameters. See each of the subclauses specified previously for details on the individual transform parameter dictionaries. |
| Data | (various) | (Required when TransformMethod is FieldMDP, shall be an indirect reference) An indirect reference to the object in the document upon which the object modification analysis should be performed. For transform methods other than FieldMDP, this object is implicitly defined. |
| DigestMethod | name | (Required) A name identifying the algorithm that shall be used when computing the digest if not specified in the certificate. Valid values are MD5, SHA1 SHA256, SHA384, SHA512 and RIPEMD160. |

> **NOTE** (2020) The use of MD5 and SHA1 are deprecated with PDF 2.0. The DigestMethod key was also corrected to be required as no default value is defined.
Default value for PDF 1.5-1.7: MD5.


### 12.8.2 Transform methods

#### 12.8.2.1 General

Transform methods, along with transform parameters, shall determine which objects are included and excluded in revision comparison. The following subclauses discuss the types of transform methods, their transform parameters, and when they shall be used.

#### 12.8.2.2 DocMDP

##### 12.8.2.2.1 General

The DocMDP transform method shall be used to detect modifications relative to a signature field that is signed by the author of a document (the person applying a certification signature). A document can contain only one signature field that contains a DocMDP transform method. It enables the author to specify what changes shall be permitted to be made to the document and what changes invalidate the author’s signature.

> **NOTE** As discussed earlier, "MDP" stands for Modification Detection and Prevention. Certification signatures that use the DocMDP transform method enable detection of disallowed changes specified by the author. In addition, disallowed changes can also be prevented when the signature dictionary is referred to by the DocMDP entry in the permissions dictionary (see "Table 263 — Entries in a permissions dictionary").
A certification signature should have a legal attestation dictionary (see 12.8.7, "Legal content attestations") that specifies all content that might result in unexpected rendering of the document contents, along with the author’s attestation to such content. This dictionary may be used to establish an author’s intent if the integrity of the document is questioned.

The P entry in the DocMDP transform parameters dictionary (see "Table 257 — Entries in the DocMDP transform parameters dictionary") shall indicate the author’s specification of which changes to the document will invalidate the signature. (These changes to the document shall also be prevented if the signature dictionary is referred from the DocMDP entry in the permissions dictionary.) A value of 1 for P indicates that the document shall be final; that is, any changes shall invalidate the signature with the exception of subsequent DSS (see 12.8.4.3, "Document Security Store (DSS)") and/or document timestamp (see 12.8.5, "Document timestamp (DTS) dictionary") incremental updates. The values 2 and 3 shall permit modifications that are appropriate for form field or comment workflows as well as subsequent DSS and/or document timestamp incremental updates.

##### 12.8.2.2.2 Validating signatures that use the DocMDP transform method

To validate a signature that uses the DocMDP transform method, a PDF processor first shall verify the byte range digest. Next, it shall verify that any modifications that have been made to the document are permitted by the transform parameters.

Once the byte range digest is validated, the portion of the document specified by the ByteRange entry in the signature dictionary (see "Table 255 — Entries in a signature dictionary") is known to correspond to the state of the document at the time of signing. Therefore, PDF processors may compare the signed and current versions of the document to see whether there have been modifications to any objects that are not permitted by the transform parameters.


Table 257 — Entries in the DocMDP transform parameters dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be TransformParams for a transform parameters dictionary. |
| P | number | (Optional) The access permissions granted for this document. Changes to a PDF that are incremental updates which include only the data necessary to add DSS’s 12.8.4.3, "Document Security Store (DSS)" and/or document timestamps 12.8.5, "Document timestamp (DTS) dictionary" to the document shall not be considered as changes to the document as defined in the choices below. Valid values shall be: |

# 1 No changes to the document shall be permitted; any change to the

document shall invalidate the signature.

# 2 Permitted changes shall be filling in forms, instantiating page

templates, and signing; other changes shall invalidate the signature.

# 3 Permitted changes shall be the same as for 2, as well as annotation

creation, deletion, and modification; other changes shall invalidate the signature.
Default value: 2.

V name (Optional) The DocMDP transform parameters dictionary version. The only valid value shall be 1.2.

> **NOTE** This value is a name object, not a number.
Default value: 1.2.

#### 12.8.2.3 UR

The features described in this subclause are deprecated with PDF 2.0.

The UR transform method (deprecated in PDF 2.0) shall be used to detect changes to a document that shall invalidate a usage rights signature, which is referred to from the UR3 entry in the permissions dictionary (see "Table 263 — Entries in a permissions dictionary"). The transform parameters dictionary (see "Table 258 — Entries in the UR transform parameters dictionary") specifies the additional rights that shall be enabled if the signature is valid. If the signature is invalid because the document has been modified in a way that is not permitted or the identity of the signer is not granted the extended permissions, additional rights shall not be granted.

A PDF processor that modifies a PDF, with a UR signature in excess of the rights that are granted by that signature, should remove that signature prior to writing the newly modified PDF.

UR3 (PDF 1.6, deprecated in PDF 2.0): The ByteRange entry in the signature dictionary (see "Table 255 — Entries in a signature dictionary") shall be present. First, a PDF processor shall verify the byte range digest to determine whether the portion of the document specified by ByteRange corresponds to the state of the document at the time of signing. Next, a PDF processor shall examine the current version of the document to see whether there have been modifications to any objects that are not permitted by the transform parameters.


Table 258 — Entries in the UR transform parameters dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be TransformParams for a transform parameters dictionary. |
| Document | array | (Optional) An array of names specifying additional document-wide usage rights for the document. The only defined value shall be FullSave, which permits a user to save the document along with modified form and/or annotation data. (PDF 1.5) Any usage right that permits the document to be modified implicitly shall enable the FullSave right. If the PDF document contains a UR3 dictionary, only rights specified by the Annots entry that permit the document to be modified shall implicitly enable the FullSave right. For all other rights, FullSave shall be explicitly enabled in order to save the document. (Signature rights shall permit saving as part of the signing process but not otherwise). |
| Msg | text | (Optional) A text string that may be used to specify any arbitrary |
| string | information, such as the reason for adding usage rights to the document. |  |
| V | name | (Optional) The UR transform parameters dictionary version. The value shall be 2.2. If an unknown version is present, no rights shall be enabled. |

> **NOTE** This value is a name object, not a number.
Default value: 2.2.

| Annots | array | (Optional) An array of names specifying additional annotation-related usage rights for the document. Valid names (PDF 1.5) are Create, Delete, Modify, Copy, Import, and Export, which shall permit the user to perform the named operation on annotations. The following names (PDF 1.6) are also permitted (see "Table 263 — Entries in a permissions dictionary"): |
| --- | --- | --- |
| Online | Permits online commenting; that is, the ability to upload or download markup annotations from a server. |  |
| SummaryView | Permits a user interface to be shown that summarises the comments (markup annotations) in a document. |  |


| Key | Type | Value |
| --- | --- | --- |
| Form | array | (Optional) An array of names specifying additional form-field-related usage rights for the document. Valid names (PDF 1.5) are: |
| Add | Permits the user to add form fields to the document. |  |
| Delete | Permits the user to delete form fields to the document. |  |
| FillIn | Permits the user to save a document on which form fill-in has been done. |  |
| Import | Permits the user to import form data files in FDF, XFDF and text (CSV/TSV) formats. |  |
| Export | Permits the user to export form data files as FDF or XFDF. |  |
| SubmitStandalone | Permits the user to submit form data when the document is not open in a Web browser. |  |
| SpawnTemplate | Permits new pages to be instantiated from named page templates. The following names (PDF 1.6) are also valid: |  |
| BarcodePlaintext | Permits text form field data to be encoded as a plaintext two-dimensional barcode. |  |
| Online | Permits the use of forms-specific online mechanisms such as SOAP or Active Data Object. |  |
| Signature | array | (Optional) An array of names specifying additional signature-related usage rights for the document. The only defined value shall be Modify, which permits a user to apply a digital signature to an existing signature form field or clear a signed signature form field. |
| EF | array | (Optional; PDF 1.6) An array of names specifying additional usage rights for named embedded files in the document. Valid names shall be Create, Delete, Modify, and Import, which shall permit the user to perform the named operation on named embedded files. |
| P | boolean | (Optional; PDF 1.6) If false, any possible restriction may be ignored. Default value: false. |

#### 12.8.2.4 FieldMDP

The FieldMDP transform method shall be used to detect changes to the values of a list of form fields.
The entries in its transform parameters dictionary are listed in "Table 259 — Entries in the FieldMDP transform parameters dictionary".

Table 259 — Entries in the FieldMDP transform parameters dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be TransformParams for a transform parameters dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| Action | name | (Required) A name that, along with the Fields array, describes which form fields do not permit changes after the signature is applied. Valid values shall be: |
| All | All form fields. |  |
| Include | Only those form fields specified in Fields. |  |
| Exclude | Only those form fields not specified in Fields. |  |
| Fields | array | (Required if Action is Include or Exclude) An array of text strings containing field names. |
| V | name | (Optional; required for PDF 1.5 and later) The transform parameters dictionary version. The value for PDF 1.5 and later shall be 1.2. |

> **NOTE** This value is a name object, not a number.
Default value: 1.2.

On behalf of a document author creating a document containing both form fields and signatures the following shall be supported by PDF writers:

• The author specifies that form fields shall be filled in without invalidating the approval or certification signature. The P entry of the DocMDP transform parameters dictionary shall be set to either 2 or 3 (see "Table 257 — Entries in the DocMDP transform parameters dictionary").
• The author can also specify that after a specific recipient has signed the document, any modifications to specific form fields shall invalidate that recipient’s signature. There shall be a separate signature field for each designated recipient, each having an associated signature field lock dictionary (see "Table 236 — Entries in a signature field lock dictionary") specifying the form fields that shall be locked for that user.
• When the recipient signs the field, the signature, signature reference, and transform parameters dictionaries shall be created. The Action and Fields entries in the transform parameters dictionary shall be copied from the corresponding fields in the signature field lock dictionary.

> **NOTE** This copying is done because all objects in a signature dictionary are direct objects if the dictionary contains a byte range signature. Therefore, the transform parameters dictionary cannot reference the signature field lock dictionary indirectly.
FieldMDP signatures shall be validated in a similar manner to DocMDP signatures. See 12.8.2.2.2, "Validating signatures that use the DocMDP transform method" for details.

### 12.8.3 Signature interoperability

#### 12.8.3.1 General

It is intended that PDF processors allow interoperability between signature handlers; that is, a PDF file signed with a handler from one vendor should be able to be validated with a handler from a different vendor when they use the same set of validation parameters. PDF 2.0 defines new signature formats for PAdES signatures (PDF Advanced Electronic Signatures) as defined by ETSI EN 319 142-1 and ETSI EN 319 142-2.

If present, the SubFilter entry in the signature dictionary shall specify the encoding of the signature


value and key information, while the Filter entry shall specify the preferred handler that should be used to validate the signature. When handlers are being registered according to Annex E, "Extending PDF" they shall specify the SubFilter encodings they support enabling handlers other than the preferred handler to validate the signatures that the preferred handler creates.

There are several defined values for the SubFilter entry, all based on public-key cryptographic standards published by RSA Security and also as part of the standards issued by the Internet Engineering Task Force (IETF) Public Key Infrastructure (PKIX) working group and the European Telecommunications Standards Institute (ETSI). PDF 2.0 introduced Elliptic Curve Digital Signature Algorithm (ECDSA) support as defined by Internet RFC 5480.

"Table 260 — SubFilter value algorithm support" shows an overview of the SubFilter values along with the algorithms that are supported for each SubFilter.

Table 260 — SubFilter value algorithm support

| SubFilter values | adbe.pkcs7.detached, | adbe.pkcs7.sha1c | adbe.x509.rsa_sha1a ETSI.CAdES.detached or ETSI.RFC3161 |
| --- | --- | --- | --- |
| Message Digest | SHA1 (PDF 1.3) d | SHA1 (PDF 1.3)b | SHA1 (PDF 1.3) |
| SHA256 (PDF 1.6) | SHA256 (PDF 1.6) |  |  |
| SHA384 (PDF 1.7) | SHA384 (PDF 1.7) |  |  |
| SHA512 (PDF 1.7) | SHA512 (PDF 1.7) |  |  |
| RIPEMD160 (PDF 1.7) | RIPEMD160 (PDF 1.7) |  |  |
| RSA Algorithm Support | Up to 1024-bit (PDF 1.3) | See adbe.pkcs7.detached | See adbe.pkcs7.detached Up to 2048-bit (PDF 1.5) Up to 4096-bit (PDF 1.5) |
| DSA Algorithm Support | Up to 4096-bits (PDF 1.6) | See adbe.pkcs7.detached | No |
| ECDSA Algorithm Support | ANSI X9.62, Elliptic Curve | No | No |
| (defined by Internet RFC | Digital Signature Algorithm |  |  |
| 5480) | (ECDSA) (PDF 2.0) |  |  |

a Despite the appearance of sha1 in the name of this SubFilter value, supported encodings shall not be limited to the SHA-1 algorithm. The PKCS #1 object contains an identifier that indicates which algorithm shall be used. b  Other digest algorithms may be used to digest the signed-data field; however, SHA-1 shall be used to digest the data that is being signed. c  The values adbe.x509.rsa_sha1 and adbe.pkcs7.sha1 have been deprecated with PDF 2.0.

d SHA1 has been deprecated with PDF 2.0.

#### 12.8.3.2 PKCS #1 signatures

The PKCS #1 standard supports several public-key cryptographic algorithms and digest methods, including RSA encryption, DSA signatures, and SHA-1 and MD5 digests. For signing PDF files using PKCS #1, the only value of SubFilter that should be used is adbe.x509.rsa_sha1, which uses the RSA encryption algorithm and SHA-1 digest method. The certificate chain of the signer shall be stored in the Cert entry. PKCS #1 signatures are deprecated with PDF 2.0.


#### 12.8.3.3 CMS (PKCS #7) signatures

##### 12.8.3.3.1 General

When CMS signatures are used, the value of Contents shall be a DER-encoded CMS binary data object containing the signature.

For byte range signatures, Contents shall be a hexadecimal string with "<" and ">" delimiters. It shall fit precisely in the space between the ranges specified by ByteRange. Since the length of CMS objects is not entirely predictable, the value of Contents shall be padded with zeros at the end of the string (before the ">" delimiter) before writing the CMS to the allocated space in the PDF file.

The CMS object shall conform to Internet RFC 5652. Different SubFilter values may be used and shall be registered in accordance with Annex E, "Extending PDF". SubFilter shall take one of the following values:

• adbe.pkcs7.detached: The original signed message digest over the document’s byte range shall be incorporated as the normal CMS SignedData field. No data shall be encapsulated in the CMS SignedData field.
• adbe.pkcs7.sha1: The SHA-1 digest of the document’s byte range shall be encapsulated in the CMS SignedData field with ContentInfo of type Data. The digest of that SignedData shall be incorporated as the normal CMS digest. The value adbe.pkcs7.sha1 for the SubFilter key has been deprecated with PDF 2.0. To support backward compatibility, PDF readers should process this value for this key, but PDF writers shall not use this value.
At minimum the CMS object shall include the signer’s X.509 signing certificate. This certificate shall be used to verify the signature value in Contents.

If the signature handler has on-line access, it may place into the CMS object time stamping information and/or revocation information.

> **NOTE** Since PDF 2.0 supports two additional dictionaries, i.e. the DSS and the DTS dictionaries, revocation information can be better placed in a DSS dictionary while time-stamping information can also be placed in a DTS dictionary, in addition to being placed in the CMS object.
In such a case, the CMS object should contain the following:

• Timestamp information as an unsigned attribute (PDF 1.6): The timestamp token shall conform to Internet RFC 3161 as updated by Internet RFC 5816, and shall be computed and embedded into the CMS object as described in Appendix A of Internet RFC 3161 as updated by Internet RFC 5816. The specific treatment of this timestamp tokens and its processing is left to the particular signature handlers to define.
• Revocation information as a signed attribute (PDF 1.6): This attribute may include all the revocation information that is necessary to carry out revocation checks for the signer's certificate and its issuer certificates. Since revocation information is a signed attribute, it shall be obtained before the computation of the digital signature. This means that the software used by the signer should be able to construct the certification path and the associated revocation information. If one of the elements cannot be obtained (e.g. no connection is possible), a signature with this attribute will not be possible.
• The signature handler should capture the chain of certificates, including the signer certificate, along with the other certificates in the certificate chain, before signing. The signature handler should validate the certificate's chain before signing. However, if this is not possible, the DSS (see


12.8.4.3, "Document Security Store (DSS)") may be used for adding the information as an incremental update. This differs from the treatment when using adbe.x509.rsa_sha1 when the certificates shall be placed in the Cert key of the signature dictionary as defined in "Table 260 — SubFilter value algorithm support".
• One or more Internet RFC 5755 attribute certificates associated with the signer certificate (PDF 1.7). The specific treatment of attribute certificates and their processing is left to the particular signature handlers to define.
The policy of how to establish trusted identity lists to validate embedded certificates is up to the validation signature handler.

##### 12.8.3.3.2 Revocation of CMS-based signatures

For signatures with a SubFilter value other than ETSI.CAdES.detached, the following rules apply.

The adbe Revocation Information attribute object identifier is specified as follows using ASN.1 notation:

adbe-revocationInfoArchival OBJECT IDENTIFIER::= {adbe(1.2.840.113583) acrobat(1) security(1) 8}

The value of the revocation information attribute may include any of the following data types:

• Certificate Revocation Lists (CRLs), described in Internet RFC 5280.

> **NOTE** CRLs can be large and therefore require more pre-allocated space in the value of the Contents key.
• Online Certificate Status Protocol (OCSP) Responses, described in Internet RFC 6960. These are generally small and should be the data type included in the CMS object.
• Custom revocation information: The format is not prescribed by this specification, other than that it be encoded as an OCTET STRING. The application should be able to determine the type of data contained within the OCTET STRING by looking at the associated OBJECT IDENTIFIER.
The ASN.1 type of adbe's Revocation Information attribute value is RevocationInfoArchival defined as follows using ASN.1 notation:

RevocationInfoArchival::= SEQUENCE {
| crl | [0] EXPLICIT SEQUENCE of CRLs, OPTIONAL |
| --- | --- |
| ocsp | [1] EXPLICIT SEQUENCE of OCSPResponse, OPTIONAL otherRevInfo [2] EXPLICIT SEQUENCE of OtherRevInfo, OPTIONAL } OtherRevInfo::= SEQUENCE {Type OBJECT IDENTIFIER Value OCTET STRING } |

#### 12.8.3.4 CAdES signatures as used in PDF

##### 12.8.3.4.1 General

The Cryptographic Message Syntax (CMS)(see Internet RFC 5652) is used by CAdES signatures. The PDF signatures using the SubFilter value ETSI.CAdES.detached are referred to as PAdES signatures and they follow one of two CMS profiles created to be compatible with the corresponding CAdES profiles defined in ETSI EN 319 122. Combined with 12.8.4, "Long term validation of signatures" and 12.8.5, "Document timestamp (DTS) dictionary" as described in ETSI EN 319 142-1 (PAdES), compatibility with additional CAdES profiles can be achieved.


##### 12.8.3.4.2 Signature dictionary for PAdES signatures

When the SubFilter value is ETSI.CAdES.detached, the value of Contents shall be a DER-encoded CMS SignedData binary data object containing the signature. The signature dictionary shall follow the specification given in 12.7.5.5, "Signature fields" with the following additional restrictions/constraints:

• The ByteRange shall cover the entire PDF file, including the signature dictionary but excluding the Contents entry.
• The signature dictionary shall not contain a Cert entry.
• Either the time of signing may be indicated by the value of the M entry in the signature dictionary or the signing-time attribute may be used, but not both.

##### 12.8.3.4.3 Attributes for PAdES signatures

The attributes in the SignedData object used as the value of the signature dictionary Contents key shall obey the following rules:

a) content -type: shall be present and shall always have the val ue "id-data".
b) Signature timestamp: A timestamp from a trusted timestamp serve r should be present as a unsigned
attribute. The timestamp should be applied on the digital signature immediately after the signature is
| created so the timestamp specifies a time | as close as possible to the time at which the document was |  |  |
| --- | --- | --- | --- |
| signed. The rules from clause | 5.3 in ETSI | EN 319 122 | -1 shall apply. |

c) content timestamp: may be present. If the content timestamp attribute is present, it shall be used in the
same way as defined in clause 5. 2.8 in ETSI EN 319 122 -1.
d) exactly one single SignerInfo attribute shall be present.
e) message-digest: shall be present and shall be used as defined in CMS ( Internet RFC 5652).
f) signing-certificate or signing -certificate -v2: shall be used as a signed attribute as described in the ESS
| signing-certificate attribute or as described in the ESS signing | -certificate -v2 attribute as defined in clause |  |  |  |
| --- | --- | --- | --- | --- |
| 5.2.2 in ETSI EN 319 122 | -1. The details of the signing certificate attribute | are defined | in Internet | RFC |

5035.
g) signing-time: may be present. If present, it contains a UTC time. If the signing -time attribute is present,
the time of signing shall not be indicated by the value of the M entry in the signature dictionary.
h) signer-location, as defined in clause 5. 2.5 in ETSI EN 319 122 -1, may be present. In such a case, the
Location entry in the signature dictionary shall not be present.
i) these attributes shall not be used: counter -signature, content -reference, content -identifier, and content -
hints.
j) signer-attributes-v2: may be used and shall follow the definition given in clause 5. 2.6.1 of ETSI EN 319
122-1.
k) Unsigned signature attributes not explicitly noted here may be ignored.

##### 12.8.3.4.4 Profiles of ETSI.CAdES.detached

| The signatures using the | SubFilter value ETSI.CAdES.detached | may follow one of two profiles denoted |  |  |
| --- | --- | --- | --- | --- |
| as PAdES-E-BES (Basic Electronic Signature) and PAdES | -E-EPES (Explicit Policy Electronic Signature). |  |  |  |
| These are defined to be compatible with the corresponding profiles defined in | ETSI EN 319 122-2 (i.e., |  |  |  |
| CAdES-E-BES and CAdES-E-EPES, respectively). | These two CMS profiles are also denoted as PAdES | -E- |  |  |
| BES (Basic Electronic Signature) and PAdES | -E-PES (Explicit Policy Electronic Signature) in | ETSI EN |  |  |
| 319 142-2. If discrepancies exist between this documen | t and those of | ETSI, this document | shall prevail | . |


The following attributes may be present within the CMS SignedData object depending on the profile employed:

For both the PAdES-E-BES and the PAdES-E-PES profiles:

• If a commitment-type-indication attribute is present, a Reason entry shall not be used •    If a commitment-type-indication attribute is not present, a Reason entry may be used NOTE 2       The commitment-type-indication attribute contains an OID, whereas the entry Reason of the signature dictionary contains a text string which is language dependent.
For the PAdES-E-EPES profile,

• a signature-policy-identifier shall be present as a signed attribute. The rules from clause 5.2.9 in CAdES (ETSI EN 319 122-1) shall apply. The parameters of the signature policy shall take precedence over the seed values defined in 12.7.5.5, "Signature fields". Conforming signature handlers should enforce seed value constraints at signing time, if not overridden, and should enforce signature policies constraints at signing time when possible. During validation conforming signature handlers should not enforce seed values constraints, if present, but shall enforce signature policy constraints.

> **NOTE 3** It is important not to confuse the EPES defined attribute parameters with the "seed values" defined in clause 12.7.5.5, "Signature fields". While both bear similarities, seed values are workflow constraints for a given document, whereas signature policies represent general endorsement rules agreed upon by the signer and the verifier.
Further CAdES-T compatible capabilities may be provided after the time of the signature using one of the following methods:

• with the addition, by the signer, of a timestamp attribute within its signature when sufficient space has been prepared for it (see ETSI EN 319 122).
• with the addition, by the verifier, of a timestamp token that applies to the PDF document as a whole (see 12.8.5, "Document timestamp (DTS) dictionary").
In both cases, this demonstrates that the signer’s signature was created before the UTC time contained in the timestamp token.

All signed attributes shall be checked for proper values by signature validation software.

##### 12.8.3.4.5 Requirements for validation of PAdES signatures

For all profiles of PAdES signatures covered in this document (i.e., those with a SubFilter value of ETSI.CAdES.detached), when the user opens a signed document or requests verification of these signatures present in the PDF, a PDF processor, as the verifier, shall perform the following steps to verify them:

a) A signature handler shall compare the hash value of the signer's certificate, with the hash value
given in the signing-certificate attribute or the signing-certificate-v2 attribute. If the hashes do not match, then the signature is considered invalid. The signature handler shall use the public key contained in the signer's certificate to verify that the document digest found in the signature is correctly signed. If this is not the case, then the signature is considered invalid.
b) The signature handler shall validate the path of certificates used to verify the bin ding between the
subject distinguished name and subject public key as specified in Internet RFC 5280 clause 6, using a set of validation parameters.  The signature may be verified against a time other than the current


time if all validation information (e.g. certificates and revocation information) is known to have existed at that time (e.g. using DSS (see 12.8.4.3, "Document Security Store (DSS)"); using document timestamps, (see 12.8.5, "Document timestamp (DTS) dictionary"); or a signature timestamp is present in the signature as an unsigned attribute (see 12.8.3.4.3, "Attributes for PAdES signatures")). Otherwise, the local current time converted into the UTC shall be used.

> **NOTE** The claimed signing time specified by the signature dictionary value with the key M is not a trusted indication of the signing time.
c) When the signer's signature is verified against a time indicated in a timestamp token present in a
Document timestamp dictionary, then the signature handler shall also validate the path of certificates used to verify the timestamp unit's certificate, as specified in Internet RFC 5280 clause 6, using a set of validation parameters which may be different from the previous one.
d) The revocation status of the certification path shall be checked as specified in 12.8.3.4.6, "Signature
revocation checking model for PAdES signatures".

##### 12.8.3.4.6 Signature revocation checking model for PAdES signatures

A signature handler shall use either, or both, of the following methods to check the revocation status of every certificate belonging to a certification path:

• Certificate Revocation Lists (CRLs) obtained from Certification Authorities (CAs) or CRL Issuers that are identified using a set of validation parameters. Using a CRL, the certificate in question shall be checked against a list of revoked certificates.

> **NOTE 1** In addition to the certificate issue date and the issuing entities, the CRL specifies revoked certificates and can also specify the reasons for revocation. Each CRL also contains a date at the latest for the next release.
• Online Certificate Status Protocol (OCSP) responses for obtaining the revocation status of a given certificate from trusted network servers that are identified using a set of validation parameters.
For the verification of a signer's signature at a time located before the expiry of the signer's certificate, two cases need to be considered:

| 1. | the current UTC time, or |
| --- | --- |
| 2. | a UTC time in the past, when a timestamp token is present as an unsigned attribute of this signature or of the signature which covers this signature or of the document timestamp which covers this signature. |

> **NOTE 2** For a verification of a signer's signature after the expiry of the signer's certificate, see 12.8.4, "Long term validation of signatures".

##### 12.8.3.4.7 Revocation checking PAdES signatures at the current time

When the signer's signature is verified at the current UTC time, such verification shall be done before the expiry of the signer's certificate. The revocation of each certificate from the certification path of the signer's certificate shall be checked.

When CRLs are being used, a CRL is appropriate only if the current UTC time is included within the time period delimited by the thisUpdate and the nextUpdate fields of the CRL being used.

When OCSP responses are being used, an OCSP response is appropriate only if it has been captured before the expiry of the signer's certificate.


> **NOTE** Such checking is adequate for a data origin authentication service but not for a non-repudiation service, since after a successful validation done at one time, another validation done at a later time will provide a validation failure if one of the certificates from the certification path happens to be subsequently revoked.

##### 12.8.3.4.8 Revocation checking PAdES signatures at a time in the past

When a timestamp token is already present in the CAdES signature as a signature timestamp attribute (it is an unsigned attribute), the signer's signature shall be verified at the UTC time in the past indicated in that token. The revocation status of each certificate from the certification path of the signer's certificate shall be checked at that UTC time, as well as the revocation status of each certificate from the certification path of the timestamping unit's certificate.

> **NOTE** When there is no valid timestamp token present in the CAdES signature, revocation checking at a time in the past is addressed in 12.8.5, "Document timestamp (DTS) dictionary".
When CRLs are being used, a CRL is appropriate only if the two following conditions apply:

• the CRL has been captured before the expiry of the signer's certificate, and •    the time indicated in the nextUpdate field from the CRL is located after the UTC time in the past.
When OCSP responses are being used, an OCSP response is appropriate only if the two following conditions apply:

• the OCSP response has been captured before the expiry of the signer's certificate, and •    the time indicated in the producedAt field from the OCSP response is located after the UTC time in the past.
The following explanations only apply when the certificate of the timestamping unit that has produced the timestamp token has not yet expired at the time of the verification. The scenario when it has expired is addressed in 12.8.5.3, "Subsequent document timestamp dictionaries".

The reason for the revocation of a timestamping certificate and of every CA certificate belonging to the certification path shall be indicated either in the CRLs or in the OCSP responses that are being used.

The revocation status of each certificate of the certification path of the timestamping unit's certificate shall be checked at the current UTC time (i.e. not at a time in the past):

• If no certificate has been revoked, then the UTC time included in the timestamp token can be considered as reliable.
• If a certificate has been revoked for a reason which is either "keyCompromise" or "cACompromise" or if the revocation reason is missing, then the UTC time included in the timestamp token cannot be considered as reliable and the time indicated in the timestamp token cannot be used. The benefits of the timestamp token are then lost and revocation checking should thus be done at the current time (see 12.8.3.4.6, "Signature revocation checking model for PAdES signatures").
• If a certificate has been revoked for any other reason than "keyCompromise" or "cACompromise", then the UTC time included in the timestamp token can be considered as reliable (and is thus usable) only if the time of the revocation of that timestamping unit's certificate occurred after the UTC time included in the timestamp token. Otherwise, revocation checking should be done at the current time (see 12.8.3.4.6, "Signature revocation checking model for PAdES signatures").


### 12.8.4 Long term validation of signatures

#### 12.8.4.1 General

Long term validation (LTV) of signatures is achieved by using two types of dictionaries:

• document security store (DSS) dictionaries, and •    document timestamp dictionaries (DTS) (see 12.8.5, "Document timestamp (DTS) dictionary").

#### 12.8.4.2 Introduction to the document security store (DSS)

A PDF signature may not be successfully verified unless its collateral validation components are preserved, e.g., certificates, CRLs, timestamp tokens, revocation lists, and OCSP responses. To facilitate long term signature validation, PDF supports the ability to collect validation information to verify a signature at a later time if it has been verified once as being valid. Some of this information, i.e. certificates, CRLs and OCSP responses, when not already present in the signature, shall be stored in a document security store (DSS), see 12.8.4.3, "Document Security Store (DSS)". This will provide the information needed to verify a signature as this was done when that signature was first verified.

Without an authoritative timestamp token, a signature handler is not able to verify a signature at a date in the past to get the same validation result.
To allow this verification, one of the following is required:

• a signature timestamp attribute (which contains a timestamp token), or •    a timestamp token that applies to the PDF document as a whole (see 12.8.5, "Document timestamp (DTS) dictionary").
When that timestamp token is already present in the CAdES signature (see 12.8.3.4.7, "Revocation checking PAdES signatures at the current time") and if that timestamp token is valid, then the UTC time included in that timestamp token shall be used as the time reference to check the revocation status of the signer's certificate and of all the intermediate CA certificates, up to a trusted root. Afterwards, the DSS dictionary shall be used to collect the certificates, CRLs and OCSP responses that are relevant to validate that signature.
When there is no valid timestamp token present in the CAdES signature, or when such a timestamp token is present but is considered as invalid, the DSS dictionary shall be used to collect the certificates, CRLs and OCSP responses that are relevant to validate that signature and afterwards a timestamp token that applies to the PDF document as a whole shall be used and placed in a Document timestamp dictionary (DTS) as noted in 12.8.5, "Document timestamp (DTS) dictionary").
A timestamp token is itself signed. The case where the certificate of the timestamping unit which has issued that timestamp token has expired is addressed in 12.8.5.3, "Subsequent document timestamp dictionaries".
A timestamp token is itself signed, and so it is possible for the timestamp token's own validation data also to be preserved. As with signatures from the signer(s) of the document, a DSS dictionary may also include signature validation data relating to the timestamp token contained in a signature-timestamp attribute or to document timestamps, see 12.8.5, "Document timestamp (DTS) dictionary").

The DSS supports the ability to add this critical information after the signed document has been created and before this information is no longer available. At the time of signing, all of the collateral


validation components may not be available for various reasons including the inability to connect to remote servers that provide them (e.g. the user is offline) or the inability of the signer to bear the financial or time costs associated with obtaining these components. Also, in many workflows it is the recipient of a signed PDF document who is interested in a long term validation of the signatures and not the signer of the document.

The relevant validation data for each signature may be identified from the security store using a VRI dictionary (see 12.8.4.4, "Validation-related information (VRI)") for optimisation or to remove ambiguity of the validation data used to validate a specific signature.

> **NOTE** A benefit of using a DSS dictionary is that those components which are common to several signatures (e.g. certificates and revocation lists) can be stored in this dictionary once and referenced where ever they are needed. This can greatly reduce the size of a PDF document that contains several signatures.

#### 12.8.4.3 Document Security Store (DSS)

The document security store (DSS), when present, shall be a dictionary that shall be the value of a DSS key in the document catalog dictionary (see 7.7.2, "Document catalog dictionary"). This dictionary may contain:

• an array of all certificates used for the signatures, including timestamp signatures, that occur in the document. It shall also hold all the auxiliary certificates required to validate the certificates participating in certificate chain validations.
• an array of all Certificate Revocation Lists (CRL) (see Internet RFC 5280) used for some of the signatures, and •    an array of all Certificate Status Protocol (OCSP) responses (see Internet RFC 6960) used for some of the signatures.
• a VRI key whose value shall be a dictionary containing one VRI dictionary (validation-related information) for each signature represented in CMS format.
Any VRI dictionaries, if present, shall be located in document incremental update sections. If the signature dictionary to which a VRI dictionary applies is itself in an incremental update section, the DSS/VRI update shall be done later than the signature update. The inclusion of VRI dictionary entries is optional. All validation material referenced in VRI entries is included in DSS entries too.

“Table 261 — Entries in the document security store (DSS) dictionary” shows the entries in the DSS dictionary.

Table 261 — Entries in the document security store (DSS) dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) If present, shall be DSS for a document security store dictionary. |
| VRI | dictionary | (Optional) This dictionary contains Signature VRI dictionaries (see 12.8.4.4, "Validation-related information (VRI)"). The key of each entry in this dictionary is the base-16-encoded (uppercase) SHA-1 digest of the signature to which it appliesa and the value is the Signature VRI dictionary which contains the validation-related information for that signature. |


| Key | Type | Value |
| --- | --- | --- |
| Certs | array | (Optional) An array of indirect reference to streams, each containing one DER-encoded X.509 certificate (see Internet RFC 5280). This array contains certificates that may be used in the validation of any signatures in the document. |
| OCSPs | array | (Optional) An array of indirect references to streams, each containing a DER-encoded Online Certificate Status Protocol (OCSP) response (see Internet RFC 6960). This array contains OCSPs that may be used in the validation of the signatures in the document. |
| CRLs | array | (Optional) An array of indirect references to streams, each containing a DER-encoded Certificate Revocation List (CRL) (see Internet RFC 5280). This array contains CRLs that may be used in the validation of the signatures in the document. |

a For a document signature or document timestamp signatures, the bytes that are hashed are those of the complete hexadecimal string, including zero padding, in the Contents entry of the associated signature dictionary, containing the signature's DER-encoded binary data object (e.g. CMS or CAdES objects).

For the signatures of CRLs and OCSP responses, the bytes that are hashed are the respective signature object represented as a BER-encoded OCTET STRING encoded with primitive encoding.

#### 12.8.4.4 Validation-related information (VRI)

A signature VRI dictionary shall contain validation-related information (VRI) for one signature in the document that a given signature handler or PDF processor has used to successfully validate the given signature. A signature VRI dictionary shall reference:

• a selection of certificates (Cert) from the certificates list (Certs) in the DSS dictionary applicable to this signature; •    a selection of CRLs from the CRL list in the DSS dictionary applicable to this signature, if any; •    a selection of OCSP responses from the OCSP response list in the DSS dictionary applicable to this signature.
If any of the Cert, CRL or OCSP arrays is empty that entry in the dictionary shall be omitted.

A VRI dictionary may also contain the time at which the data placed in the VRI dictionary was created or/and a timestamp token which contains the UTC time at which the VRI dictionary was created.

A signature VRI dictionary shall not be used to record the information used in an unsuccessful validation attempt. “Table 262 — Entries in the signature validation-related information (VRI) dictionary” shows the entries in the VRI dictionary.

Table 262 — Entries in the signature validation-related information (VRI) dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) If present, shall be VRI for a validation-related information dictionary. |


| Key | Type | Value |
| --- | --- | --- |
| Cert | array | (Optional) An array of (indirect references to) streams, each containing one DER-encoded X.509 certificate (see Internet RFC 5280). This array contains certificates that were used in the validation of this signature. |
| CRL | array | (Required, if a CRL is present) An array of indirect references to streams that are all CRLs used to determine the validity of the certificates in the chains related to this signature. Each stream shall reference a CRL that is an entry in the CRLs array in the DSS dictionary. |
| OCSP | array | (Required, if an OCSP is present) An array of indirect references to streams that are all OCSPs used to determine the validity of the certificates in the chains related to this signature. Each stream shall reference an OCSP that is an entry in the OCSPs array in the DSS dictionary. |
| TU | date | (Optional) The date/time at which this signature VRI dictionary was created. TU shall be a date string as defined in 7.9.4, "Dates". |
| TS | stream | (Optional) A stream containing the DER-encoded timestamp (see Internet RFC 3161 as updated by Internet RFC 5816) that contains the date/time at which this signature VRI dictionary was created. |

> **NOTE 1** The date/time contained in the timestamp token can be used for audit purposes.

> **NOTE 2** The hash value to be contained in the timestamp token is left undefined. For PKCS #7 signatures the datum that is hashed and included in the messageImprint field of the DER encoded timestamp stored in the TS entry (see Internet RFC 3161 as updated by Internet RFC 5816) is the encryptedDigest field in the signature's PKC S#7 object (as defined in Internet RFC 2315).

> **EXAMPLE 1** DSS dictionary (and associated objects)

100 0 obj << /Type /Catalog /DSS 101 0 R … other stuff here … >> endobj

101 0 obj << /VRI 102 0 R /OCSPs [103 0 R] /CRLs [104 0 R] /Certs [105 0 R 106 0 R] >> endobj

102 0 obj << /4B783B9A6D0D69E4E881BFDF080835E896735416  <</OCSP [103 0 R] /CRL [104 0 R]>> >> endobj

103 0 obj << /Length … %whatever the length of the stream is >> stream


… OCSP data goes here … endstream

104 0 obj << /Length … %whatever the length of the stream is >> stream … %CRL data goes here … endstream

105 0 obj << /Length … %whatever the length of the stream is >> stream … Certificate data goes here … endstream

106 0 obj << /Length … %whatever the length of the stream is >> stream … Certificate data goes here … endstream

> **EXAMPLE 2** DSS sample with Two Signatures

101 0 obj << /VRI 102 0 R /OCSPs [103 0 R 107 0 R] /CRLs [104 0 R] /Certs [105 0 R 106 0 R] >> endobj

102 0 obj <<
| /4B783B9A6D0D69E4E881BFDF080835E896735416 | <</OCSP [103 0 R] /CRL [104 0 R]>> |
| --- | --- |
| /123456789ABCDEF987654321FEDCBA1234567890 | <</OCSP [107 0 R]>> |
| >> |  |

107 0 obj << /Length … %whatever the length of the stream is >> stream … OCSP data goes here … endstream

#### 12.8.4.5 Usage of the DSS VRI

The validation data contained in the DSS dictionary and those embedded in the signature itself may be used by another party later relying on the signature. The applicability of the validation data in the signature VRI dictionary is subject to external conditions such as a set of validation parameters. In the presence of DSS in a PDF document the preferred order of the search for validation data should be as follows:


a) Validation d ata referenced in the VRI dictionary
b) Validation data in DSS
c) Validation data embedded in the signature.

### 12.8.5 Document timestamp (DTS) dictionary

#### 12.8.5.1 General

A document timestamp dictionary establishes the exact contents of the complete PDF file at the time indicated in the timestamp token.

#### 12.8.5.2 Initial document timestamp dictionary

The timestamp token shall be an Internet RFC 3161 TimeStampToken, as updated by Internet RFC 5816, obtained from a trusted timestamp authority. A Document Timestamp dictionary is a standard signature dictionary as described in "Table 255 — Entries in a signature dictionary".

The ByteRange key shall cover the entire PDF file, including the signature dictionary but excluding the Contents value. The hash value computed over that byte range shall be sent to a timestamping authority and the TimeStampToken which is received shall be placed in the Contents key.

The existence of one or more document timestamps shall be determined by examining signature fields (see 12.7.5.5, "Signature fields"). A document level timestamp is treated as a digital signature in most respects. It normally would not have any visual appearance within the document content.

> **NOTE** When a document level timestamp is validated, using the same procedures as for other signatures using its own set of validation parameters, one can determine that the contents of the document have not been changed since a given past date.

#### 12.8.5.3 Subsequent document timestamp dictionaries

A timestamp token present either in a signature timestamp attribute or in a document timestamp dictionary may expire due to the expiry of its certificate or the cryptographic strength of some of their algorithms may not be resistant any longer to some new cryptographic attack.

It is thus necessary to apply a new timestamp token before the expiry of the certificate and/or before a cryptographic attack may succeed but it is also necessary to be able to demonstrate that the certificates related to the certification path of the timestamp authority certificate were not revoked for a reason which is either "keyCompromise" or "cACompromise" at the time when the new timestamp token has been applied to the document.

The certificates, CRLs or OCSP responses used to demonstrate that the certificates related to the certification path of the previous timestamp token was not revoked for a reason which is either "keyCompromise" or "cACompromise" just before the time the new timestamp token has been requested shall be included into the DSS dictionary.

Then the new timestamp token shall be placed into a new document timestamp dictionary which will protect the whole structure.


The process may be repeated several times as long as there is a need to reverify the signatures included in the document. “Figure 86 — Illustration of a PDF document with repeated LTV” illustrates a PDF document with repeated LTV.

Figure 86 — Illustration of a PDF document with repeated LTV When evaluating the DocMDP restrictions (see 12.8.2.2, "DocMDP") the presence of a document timestamp and/or DSS information shall be ignored.

> **EXAMPLE** Document timestamp

1 0 obj << /Type /Catalog /Pages 2 0 R /AcroForm 5 0 R >> endobj

2 0 obj << /Kids [3 0 R] /Count 1 /Type /Pages >> endobj

3 0 obj << /Type /Page /Parent 2 0 R


/MediaBox [0 0 612 792] /Annots 4 0 R … other keys go here … >> endobj

4 0 obj << /Type /Annot /Subtype /Widget /Rect [0 0 0 0] /F 4 /P 3 0 R /FT /Sig /T (Sig) /V 6 0 R >> endobj

5 0 obj << /Fields [4 0 R] /SigFlags 3 >> endobj

6 0 obj << /Type /DocTimeStamp /Filter /Adobe.PPKLite /SubFilter /ETSI.RFC3161
| /Contents <00000> | %values go here inside of <> |
| --- | --- |
| /ByteRange [0 0 0 0] | %values go here inside of [] >> |

endobj

### 12.8.6 Permissions

The Perms entry in the document catalog dictionary (see "Table 29 — Entries in the catalog dictionary") shall specify a permissions dictionary (PDF 1.5). Each entry in this dictionary (see "Table 263 — Entries in a permissions dictionary" for the currently defined entries) shall specify the name of a permission handler that controls access permissions for the document. These permissions are similar to those defined by security handlers (see "Table 22 — Standard security handler user access permissions") but do not require that the document be encrypted. For a permission to be actually granted for a document, it shall be allowed by each permission handler that is present in the permissions dictionary as well as by the security handler.

> **NOTE** An example of a permission is the ability to fill in a form field.


Table 263 — Entries in a permissions dictionary

| Key | Type | Value |
| --- | --- | --- |
| DocMDP | dictionary | (Optional) An indirect reference to a signature dictionary (see "Table 255 — Entries in a signature dictionary"). This dictionary shall contain a Reference entry that shall be a signature reference dictionary (see "Table 255 — Entries in a signature dictionary") that has a DocMDP transform method (see 12.8.2.2, "DocMDP") and corresponding transform parameters. If this entry is present, PDF processors shall enforce the permissions specified by the P entry in the DocMDP transform parameters dictionary and shall also validate the corresponding signature based on whether any of these permissions have been violated. |
| UR3 | dictionary | (Optional; deprecated in PDF 2.0) A signature dictionary that may be used to specify and validate additional capabilities (usage rights) granted for this document; that is, the enabling of features of a PDF processor that are not available by default. The signature dictionary shall contain a Reference entry that shall be a signature reference dictionary that has a UR transform method (see 12.8.2.3, "UR"). The transform parameter dictionary for this method indicates which additional permissions shall be granted for the document. If the signature is valid and recognized by the PDF processor, then the PDF processor shall allow the specified permissions for the document, in addition to the default permissions. |

> **NOTE** For example, a PDF processor may not permit saving documents by default. The signature can be used to validate that the additional permissions placed within the PDF document have been granted by the authority or agent that did the signing.

### 12.8.7 Legal content attestations

The PDF language provides a number of capabilities that can make the rendered appearance of a PDF document vary. These capabilities could potentially be used to construct a document that misleads the recipient of a document, intentionally or unintentionally. These situations are relevant when considering the legal implications of a signed PDF document.

PDF provides a mechanism by which a document recipient can determine whether the document can
| be trusted. The primary method is to accept only documents that contain certification si | gnatures (one |
| --- | --- |
| that has a DocMDP signature that defines what shall be permitted to change in a document; | see |

12.8.2.2, "DocMDP").

When creating certification signatures, PDF writers should also create a legal attestation dictionary, whose entries are shown in "Table 264 — Entries in a legal attestation dictionary". This dictionary shall be the value of the Legal entry in the document catalog dictionary (see "Table 29 — Entries in the catalog dictionary"). Its entries shall specify all content that may result in unexpected rendering of the document contents. The author may provide further clarification of such content by means of the Attestation entry. Reviewers should establish for themselves that they trust the author and contents of the document. In the case of a legal challenge to the document, any questionable content can be reviewed in the context of the information in this dictionary.


Table 264 — Entries in a legal attestation dictionary

| Key | Type | Value |
| --- | --- | --- |
| JavaScriptActions | integer | (Optional) The number of ECMAScript actions found in the document (see 12.6.4.17, "ECMAScript actions"). |
| LaunchActions | integer | (Optional) The number of launch actions found in the document (see 12.6.4.6, "Launch actions"). |
| URIActions | integer | (Optional) The number of URI actions found in the document (see 12.6.4.8, "URI actions"). |
| MovieActions | integer | (Optional; deprecated in PDF 2.0) The number of movie actions found in the document (see 12.6.4.10, "Movie actions"). |
| SoundActions | integer | (Optional; deprecated in PDF 2.0) The number of sound actions found in the document (see 12.6.4.9, "Sound actions"). |
| HideAnnotationActions | integer | (Optional) The number of hide actions found in the document (see 12.6.4.11, "Hide actions"). |
| GoToRemoteActions | integer | (Optional) The number of remote go-to actions found in the document (see 12.6.4.3, "Remote Go-To actions"). |
| AlternateImages | integer | (Optional) The number of alternate images found in the document (see 8.9.5.4, "Alternate images") |
| ExternalStreams | integer | (Optional) The number of external streams found in the document. |
| TrueTypeFonts | integer | (Optional) The number of TrueType fonts found in the document (see 9.6.3, "TrueType fonts"). |
| ExternalRefXobjects | integer | (Optional) The number of reference XObjects found in the document (see 8.10.4, "Reference XObjects"). |
| ExternalOPIdicts | integer | (Optional; deprecated in PDF 2.0) The number of OPI dictionaries found in the document (see 14.11.7, "Open prepress interface (OPI)"). |
| NonEmbeddedFonts | integer | (Optional) The number of non-embedded fonts found in the document (see 9.9, "Embedded font programs") |
| DevDepGS_OP | integer | (Optional) The number of references to the graphics state parameter OP found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |
| DevDepGS_HT | integer | (Optional) The number of references to the graphics state parameter HT found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |
| DevDepGS_TR | integer | (Optional) The number of references to the graphics state parameter TR found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |
| DevDepGS_UCR | integer | (Optional) The number of references to the graphics state parameter UCR found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |


| Key | Type | Value |
| --- | --- | --- |
| DevDepGS_BG | integer | (Optional) The number of references to the graphics state parameter BG found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |
| DevDepGS_FL | integer | (Optional) The number of references to the graphics state parameter FL found in the document (see "Table 57 — Entries in a graphics state parameter dictionary"). |
| Annotations | integer | (Optional) The number of annotations found in the document (see 12.5, "Annotations"). |
| OptionalContent | boolean | (Optional) true if optional content is found in the document (see 8.11, "Optional content"). |
| Attestation | text | (Optional) An attestation, created by the author of the document, |
| string | explaining the presence of any of the other entries in this dictionary or the presence of any other content affecting the legal integrity of the document. |  |

## 12.9 Measurement properties

### 12.9.1 General

PDF documents, such as those created by CAD software, may contain graphics that are intended to represent real-world objects. Users of such documents often require information about the scale and units of measurement of the corresponding real-world objects and their relationship to units in PDF user space.

This information enables users of interactive PDF processors to perform measurements that yield results in the units intended by the creator of the document. A measurement in this context is the result of a canonical function that takes as input a set pairs

{(𝑥0,𝑦0), … ,(𝑥𝑛−1, 𝑦𝑛−1)}

and produces a single number as output depending on the type of measurement. For example, distance measurement is equivalent to

𝑛−2 ∑ √(𝑥i −  𝑥i+1)2  +  (𝑦i  − 𝑦i+1)2

i=0

Beginning with PDF 1.6, such information may be stored in a measure dictionary (see "Table 266 — Entries in a measure dictionary"). Measure dictionaries provide information about measurement units associated with a rectangular area of the document known as a viewport.

A viewport (PDF 1.6) is a rectangular region of a page. The optional VP entry in a page dictionary (see "Table 31 — Entries in a page object") shall specify an array of viewport dictionaries, whose entries shall be as shown in "Table 265 — Entries in a viewport dictionary". Viewports allow different


measurement scales (specified by the Measure entry) to be used in different areas of a page, if necessary.

The dictionaries in the VP array shall be in drawing order. Since viewports might overlap, to determine the viewport to use for any point on a page, the dictionaries in the array shall be examined, starting with the last one and iterating in reverse, and the first one whose BBox entry contains the point shall be chosen.

Any measurement that potentially involves multiple viewports, such as one specifying the distance between two points, shall use the information specified in the viewport of the first point.

Table 265 — Entries in a viewport dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; shall be Viewport for a viewport dictionary. |
| BBox | rectangle | (Required) A rectangle in default user space coordinates specifying the location of the viewport on the page. The two coordinate pairs of the rectangle shall be specified in normalised form; that is, lower-left followed by upper-right, relative to the measuring coordinate system. This ordering shall determine the orientation of the measuring coordinate system (that is, the direction of the positive x and y axes) in this viewport, which may have a different rotation from the page. The coordinates of this rectangle are independent of the origin of the measuring coordinate system, specified in the O entry (see "Table 267 — Additional entries in a rectilinear measure dictionary") of the measurement dictionary specified by Measure. |
| Name | text string | (Optional) A descriptive text string or title of the viewport, intended for use in a user interface. |
| Measure | dictionary | (Optional) A measure dictionary (see "Table 266 — Entries in a measure dictionary") that specifies the scale and units that shall apply to measurements taken on the contents within the viewport. |
| PtData | dictionary | (Optional; PDF 2.0) A point data dictionary (see "Table 272 — Entries in a point data dictionary") that shall specify the extended geospatial data that applies to this viewport. |

A measure dictionary shall specify an alternative coordinate system for a region of a page. Along with the viewport dictionary, it shall provide the information needed to convert coordinates in the page’s coordinate system to coordinates in the measuring coordinate system. The measure dictionary shall provide information for formatting the resulting values into textual form for presentation in a graphical user interface.

"Table 266 — Entries in a measure dictionary" shows the entries in a measure dictionary. PDF 1.6 defines only a single type of coordinate system, a rectilinear coordinate system, that shall be specified by the value RL for the Subtype entry. RL is defined as one in which the x and y axes are perpendicular and have units that increment linearly (to the right and up, respectively). PDF 2.0 defines a geospatial


coordinate system specified by the value GEO for the Subtype entry. Other subtypes may be used, providing the flexibility to measure using other types of coordinate systems.

When the value of the Subtype entry is GEO, the dictionary shall define the relationship between points or regions in the two dimensional PDF object space and points or regions with respect to an underlying model of the earth (or, potentially, other ellipsoid objects).

Table 266 — Entries in a measure dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; shall be Measure for a measure dictionary. |
| Subtype | name | (Optional) A name specifying the type of coordinate system to use for measuring. |
| RL | for a rectilinear coordinate system |  |
| GEO | (PDF 2.0) for a geospatial coordinate system Default value: RL |  |

"Table 267 — Additional entries in a rectilinear measure dictionary" shows the additional entries in a rectilinear measure dictionary. Many of the entries in this dictionary shall be number format arrays, which are arrays of number format dictionaries (see "Table 268 — Entries in a number format dictionary"). Each number format dictionary shall represent a specific unit of measurement (such as miles or feet). It shall contain information about how each unit shall be expressed in text and factors for calculating the number of units.

When the subtype of a measurement dictionary is GEO, additional entries are defined. "Table 269 — Additional entries in a geospatial measure dictionary" lists and describes these additional entries in a geospatial measure dictionary.

Number format arrays specify all the units that shall be used when expressing a specific measurement.
Each array shall contain one or more number format dictionaries, in descending order of granularity. If one unit of measurement X is larger than one unit of measurement Y then X has a larger order of granularity than Y. All the elements in the array shall contain text strings that, concatenated together, specify how the units shall be displayed.

> **NOTE 2** For example, a measurement of 1.4505 miles can be expressed as "1.4505 mi", which would require one number format dictionary for miles, or as "1 mi 2,378 ft 7 5/8 in", which would require three dictionaries (for miles, feet, and inches).

> **NOTE 3** A number format dictionary specifying feet needs to precede one specifying inches.


Table 267 — Additional entries in a rectilinear measure dictionary

| Key | Type | Value |
| --- | --- | --- |
| R | text string | (Required) A text string expressing the scale ratio of the drawing in the region corresponding to this dictionary. Universally recognised unit abbreviations should be used, either matching those of the number format arrays in this dictionary or those of commonly used scale ratios. |

> **EXAMPLE 1** A common scale in architectural drawings is "1/4 in = 1 ft",
| indicating that 1/4 inches in | default user space is equivalent to 1 foot in real -world measurements. |
| --- | --- |
| If the scale ratio differs in the | x and y directions, both scales should be specified. |

> **EXAMPLE 2** "in X 1 cm = 1 m, in Y 1 cm = 30 m".

| X | array | (Required) A number format array for measurement of change along the x axis and, if Y is not present, along the y axis as well. The first element in the array shall contain the scale factor for converting from default user space units to the largest units in the measuring coordinate system along that axis. The directions of the x and y axes are in the measuring coordinate system and are independent of the page rotation. These directions shall be determined by the BBox entry of the containing viewport (see "Table 265 — Entries in a viewport dictionary"). |
| --- | --- | --- |
| Y | array | (Required when the x and y scales have different units or conversion factors) A number format array for measurement of change along the y axis. The first element in the array shall contain the scale factor for converting from default user space units to the largest units in the measuring coordinate system along the y axis. |
| D | array | (Required) A number format array for measurement of distance in any direction. The first element in the array shall specify the conversion to the largest distance unit from units represented by the first element in X. The scale factors from X, Y (if present) and CYX (if Y is present) shall be used to convert from default user space to the appropriate units before applying the distance function. |
| A | array | (Required) A number format array for measurement of area. The first element in the array shall specify the conversion to the largest area unit from units represented by the first element in X, squared. The scale factors from X, Y (if present) and CYX (if Y is present) shall be used to convert from default user space to the appropriate units before applying the area function. |
| T | array | (Optional) A number format array for measurement of angles. The first element in the array shall specify the conversion to the largest angle unit from degrees. The scale factor from CYX (if present) shall be used to convert from default user space to the appropriate units before applying the angle function. |
| S | array | (Optional) A number format array for measurement of the slope of a line. The first element in the array shall specify the conversion to the largest slope unit from units represented by the first element in Y divided by the first element in X. The scale factors from X, Y (if present) and CYX (if Y is present) shall be used to convert from default user space to the appropriate units before applying the slope function. |


| Key | Type | Value |
| --- | --- | --- |
| O | array | (Optional) An array of two numbers that shall specify the origin of the measurement coordinate system in default user space coordinates. The directions by which x and y increase in value from this origin shall be determined by the viewport’s BBox entry (see "Table 265 — Entries in a viewport dictionary"). Default value: the first coordinate pair (lower-left corner) of the rectangle specified by the viewport’s BBox entry. |
| CYX | number | (Optional; meaningful only when Y is present) A factor that shall be used to convert the largest units along the y axis to the largest units along the x axis. It shall be used for calculations (distance, area, and angle) where the units are be equivalent; if not specified, these calculations may not be performed (which would be the case in situations such as x representing time and y representing temperature). Other calculations (change in x, change in y, and slope) shall not require this value. |

The X and Y entries in a measure dictionary shall be number format arrays that shall specify the units used for measurements in the x and y directions, respectively, and the ratio between user space units and the specified units. Y is present only when the x and y measurements are in different units or have different ratios; in this case, the CYX entry shall be used to convert y values to x values when appropriate.

Table 268 — Entries in a number format dictionary

| Key | Type | Value |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes; shall be NumberFormat for a number format dictionary. |
| U | text string | (Required) A text string specifying a label for displaying the units represented by this dictionary in a user interface; the label should use a universally recognised abbreviation. |
| C | number | (Required) The conversion factor used to multiply a value in partial units of the previous number format array element to obtain a value in the units of this dictionary. When this entry is in the first number format dictionary in the array, its meaning (that is, what it shall be multiplied by) depends on which entry in the rectilinear measure dictionary (see "Table 267 — Additional entries in a rectilinear measure dictionary") references the number format array. |
| F | name | (Optional; meaningful only for the last dictionary in a number format array) A name indicating whether and in what manner to display a fractional value from the result of converting to the units of this dictionary by means of the C entry. Valid values shall be: |

# D Show as decimal to the precision specified by the D entry.

# F Show as a fraction with denominator specified by the D entry.

| R | No fractional part; round to the nearest whole unit. |
| --- | --- |
| T | No fractional part; truncate to achieve whole units. Default value: D. |


| Key | Type | Value |
| --- | --- | --- |
| D | integer | (Optional; meaningful only for the last dictionary in a number format array) A positive integer that shall specify the precision or denominator of a fractional amount: When the value of F is D, this entry shall be the precision of a decimal display; it shall be a multiple of 10. Low-order zeros may be truncated unless FD is true. Default value: 100 (hundredths, corresponding to two decimal digits). When the value of F is F, this entry shall be the denominator of a fractional display. The fraction may be reduced unless the value of FD is true. Default value: 16. |
| FD | boolean | (Optional; meaningful only for the last dictionary in a number format array) If true, a fractional value formatted according to the D entry may not have its denominator reduced or low-order zeros truncated. Default value: false. |
| RT | text string | (Optional) Text that shall be used between orders of thousands in display of numerical values. An empty string indicates that no text shall be added. Default value: COMMA (2Ch). |
| RD | text string | (Optional) Text that shall be used as the decimal position in displaying numerical values. An empty string indicates that the default shall be used. Default value: PERIOD (2Eh). |
| PS | text string | (Optional) Text that shall be concatenated to the left of the label specified by U. An empty string indicates that no text shall be added. Default value: A single ASCII SPACE character (20h). |
| SS | text string | (Optional) Text that shall be concatenated after the label specified by U. An empty string indicates that no text shall be added. Default value: A single ASCII SPACE character (20h). |
| O | name | (Optional) A name indicating the position of the label specified by U with respect to the calculated unit value. Valid values shall be: |
| S | The label is a suffix to the value. |  |

# P The label is a prefix to the value.

The characters specified by PS and SS shall be concatenated before considering this entry.
Default value: S.

### 12.9.2 Algorithm: Use of a number format array to create a formatted text

string

To use a number format array to create a text string containing the appropriately formatted units for display in a user interface, apply the following algorithm:

a) The entry in the rectilinear measure dictionary (see "Table 267 — Additional entries in a rectilinear
| measure dictionary | ") that references the number format array determines the meaning of the initial |  |
| --- | --- | --- |
| measurement value. For example, the | X entry specifies user space units, and the | T entry specifies |

degrees.


b) Multiply the value specified previously by the C entry of the first number format dictionary in the array,
which converts the measurement to units of the largest granularity specified in the array. Apply the value of  RT as appropriate.
c) If the result contains no non-zero fractional portion, concatenate the label specified by the U entry in the
order specified by O, after adding spacing from PS and SS. The formatting is then complete.
d) If there is a non -zero fractional portion and no more elements in the array, format the fractional portion
| as specified by the | RD, F, D, and FD entries of the last dictionary. Concatenate the label specified by the | U |
| --- | --- | --- |
| entry in the order specified by | O, after adding spacing from | PS and SS. The formatting is then complete. |

e) If there is a non-zero fractional portion and more elements in the array, proceed to the next number
| format dictionary in the array. Multiply its | C entry by the fractional result from the previous step. Apply |
| --- | --- |
| the value of | RT as appropriate. Then proceed to step 3. |
| The concatenation of elements in this process assumes left | -to-right order. Documents using right-to- |

left languages may modify the process and the meaning of the entries as appropriate to produce the correct results.

> **EXAMPLE** The following example shows a measure dictionary that specifies that changes in x or y are expressed in miles; distances are expressed in miles, feet, and inches; and area is expressed in acres. Given a sample distance in scaled units of 1.4505 miles, the formatted text produced by applying the number format array would be "1 mi 2,378 ft 7 5/8 in".

<< /Type /Measure /Subtype /RL /R (1in = 0.1 mi)
| /X [<</U (mi) | %x offset represented in miles |
| --- | --- |
| /C .00139 | %Conversion from user space units to miles /D 100000 >>] |
| /D [<</U (mi) /C 1>> | %Distance: initial unit is miles; no conversion needed |
| <</U (ft) /C 5280>> | %Conversion from miles to feet |
| <</U (in) /C 12 | %Conversion from feet to inches |
| /F /F /D 8>> | %Fractions of inches rounded to nearest 1/8 ] |
| /A [<</U (acres) | %Area: measured in acres |
| /C 640>> | %Conversion from square miles to acres ] >> |

## 12.10 Geospatial features

### 12.10.1 General

PDF is a common delivery mechanism for map and satellite imagery data. In PDF 2.0, a geospatial coordinate system is introduced ("Table 266 — Entries in a measure dictionary") along with a number of PDF constructs, as explained in this clause, to support geospatially registered content.

### 12.10.2 Geospatial measure dictionary

When the subtype of a measurement dictionary ("Table 266 — Entries in a measure dictionary") is GEO, additional entries are defined through a geospatial measure dictionary.

A geospatial measure dictionary, "Table 269 — Additional entries in a geospatial measure dictionary", contains a description of the earth-based coordinate system associated with the PDF object, and corresponding arrays of points in that coordinate system and the local object coordinate system. It may


contain a bounding polygon (the Bounds entry), which defines the region of the PDF object for which the geographic associations are valid. It may also contain a choice of default units (the PDU entry) for user displays of positions, distances and areas. An optional display coordinate system (the DCS entry) allows a document to be authored to display values in a coordinate system other than that associated with the source data. For example, a map may be created in a state planar coordinate system based on a 1927 datum, but it is possible to display its latitude and longitude values in the WGS84 datum corresponding to values reported by a GPS device.

The entries of a geospatial measure dictionary are shown in "Table 269 — Additional entries in a geospatial measure dictionary".

Table 269 — Additional entries in a geospatial measure dictionary

| Key | Type | Description |
| --- | --- | --- |
| Bounds | array | (Optional; PDF 2.0) An array of numbers that shall be taken pairwise to define a series of points that describes the bounds of an area for which geospatial transformations are valid. For maps, this bounding polygon is known as a neatline. These numbers are expressed relative to a unit square that describes the BBox associated with a Viewport or form XObject, or the bounds of an image XObject. If not present, the default values shall define a rectangle describing the full unit square, with values of [0.0 0.0 0.0 1.0 1.0 1.0 1.0 0.0]. |

> **NOTE 1** The polygon description need not be explicitly closed by repeating the first point values as a final point.

| GCS | dictionary | (Required; PDF 2.0) A geographic or projected coordinate system dictionary. See "Table 270 — Entries in a geographic coordinate system dictionary" and "Table 271 — Entries in a projected coordinate system dictionary". |
| --- | --- | --- |
| DCS | dictionary | (Optional; PDF 2.0) A projected or geographic coordinate system that shall be used for the display of position values, such as latitude and longitude. See "Table 270 — Entries in a geographic coordinate system dictionary" and "Table 271 — Entries in a projected coordinate system dictionary". Formatting the displayed representation of these values is controlled by the interactive PDF processor. |


| Key | Type | Description |
| --- | --- | --- |
| PDU | array | (Optional; PDF 2.0) Preferred Display Units. An array of three names that identify in order a linear display unit, an area display unit, and an angular display unit. The following are valid linear display units: |
| M | a metre |  |
| KM | a kilometre |  |
| FT | an international foot |  |
| USFT | a U.S. Survey foot |  |
| MI | an international mile |  |
| NM | an international nautical mile The following are valid area display units: |  |
| SQM | a square metre |  |
| HA | a hectare (10,000 square metres) |  |
| SQKM | a square kilometre |  |
| SQFT | a square foot (US Survey) |  |
| A | an acre |  |
| SQMI | a square mile (international) The following are valid angular display units: |  |
| DEG | a degree |  |
| GRD | a grad (1/400 of a circle, or 0.9 degrees) |  |
| GPTS | array | (Required; PDF 2.0) An array of numbers that shall be taken pairwise, defining points in geographic space as degrees of latitude and longitude, respectively when defining a geographic coordinate system. These values shall be based on the geographic coordinate system described in the GCS dictionary. When defining a projected coordinate system, this array contains values in a planar projected coordinate space as eastings and northings. For Geospatial3D, when Geospatial feature information is present (requirement type Geospatial3D) in a 3D annotation, the GPTS array is required to hold 3D point coordinates as triples rather than pairwise where the third value of each tripe is an elevation value. |

> **NOTE 2** Any projected coordinate system includes an underlying geographic coordinate system.

| LPTS | array | (Optional; PDF 2.0) An array of numbers that shall be taken pairwise to define points in a 2D unit square. The unit square is mapped to the rectangular bounds of the Viewport, image XObject, or forms XObject that contains the measure dictionary. This array shall contain the same number of number pairs as the GPTS array; each number pair is the unit square object position corresponding to the geospatial position in the GPTS array. For Geospatial3D, when Geospatial feature information is present in a 3D annotation (requirement type Geospatial3D), the LPTS array is required to hold 3D point coordinates as triples corresponding to the GPTS array in the 3D annotation view world coordinate space. |
| --- | --- | --- |
| PCSM | array | (Optional; PDF 2.0) A 12-element transformation matrix of real numbers, defining the transformation from XObject position coordinates to projected coordinate system. If GCS is a geographic coordinate system dictionary then PCSM should be ignored and GTPS used instead. If PCSM is present, it has priority over GPTS, and GPTS values may be ignored. This priority provides backward compatibility. |

> **NOTE 3** PCSM is an acronym for "Projected Coordinate System Matrix".


### 12.10.3 Geographic coordinate system dictionary

A geographic coordinate system (GEOGCS) specifies an ellipsoidal object in geographic coordinates: angular units of latitude and longitude. The geographic coordinate system shall be described in either or both of two well-established standards: as a numeric EPSG reference code, or as a Well Known Text (WKT) string, which contains a description of algorithms and parameters needed for transformations.

"Table 270 — Entries in a geographic coordinate system dictionary" lists the entries in a geographic coordinate system dictionary. A geographic coordinate system dictionary may be a value of the GCS or the DCS entry of a geospatial measure dictionary. (See "Table 269 — Additional entries in a geospatial measure dictionary".)

Table 270 — Entries in a geographic coordinate system dictionary

| Key | Type | Description |
| --- | --- | --- |
| Type | name | (Required; PDF 2.0) The type of PDF object that this dictionary describes. If present, shall be GEOGCS for a geographic coordinate system dictionary. |
| EPSG | integer | (Optional; PDF 2.0) An EPSG reference code specifying the geographic coordinate system. Shall not be present if WKT is present. |
| WKT | ASCII string | (Optional; PDF 2.0) A string of Well Known Text describing the geographic coordinate system. Shall not be present if EPSG is present. |

Either an EPSG code or a WKT string shall be present in a geographic coordinate system dictionary.

The EPSG reference codes are described in a database available through http://www.epsg.org as administered by the International Association of Oil and Gas Producers (OGP). The WKT (Well Known Text) format is specified in ISO 19162.

### 12.10.4 Projected coordinate system dictionary

A projected coordinate system (PROJCS), which includes an embedded GEOGCS, specifies the algorithms and associated parameters used to transform points between geographic coordinates and a two-dimensional (projected) coordinate system. Any transformation between a three-dimensional curved geographic coordinate system and a two-dimensional coordinate system introduces distortions.
For small areas, this distortion may be small enough to allow direct mapping between geographic coordinates and PDF object coordinates without requiring the use of a projected coordinate system.

The projected coordinate system shall be described in either or both of two well-established standards: as a numeric EPSG reference code, or as a Well KnownText (WKT) string, which contains a description of algorithms and parameters needed for transformations.

"Table 271 — Entries in a projected coordinate system dictionary" lists the entries in a projected coordinate system dictionary. A projected coordinate system dictionary may be a value of the GCS or the DCS entry of a geospatial measure dictionary, "Table 269 — Additional entries in a geospatial measure dictionary".


Table 271 — Entries in a projected coordinate system dictionary

| Key | Type | Description |
| --- | --- | --- |
| Type | name | (Required; PDF 2.0) The type of PDF object that this dictionary describes; shall be PROJCS for a projected coordinate system dictionary. |
| EPSG | integer | (Optional; PDF 2.0) An EPSG reference code specifying the projected coordinate system. |
| WKT | ASCII string | (Optional; PDF 2.0) A string of Well Known Text describing the projected coordinate system. |

Either an EPSG code or a WKT string shall be required in the projected coordinate system dictionary.

> **EXAMPLE 1** A WKT describing a geographic coordinate system

An example of WKT description of a geographic coordinate system, formatted for readability. The EPSG code equivalent to the GCS_North_American_1983 geographic coordinate system is 4269.

GEOGCS["GCS_North_American_1983", DATUM[ "D_North_American_1983", SPHEROID["GRS_1980",6378137.0,298.257222101] ], PRIMEM["Greenwich",0.0], UNIT["Degree",0.0174532925199433] ]

> **EXAMPLE 2** A WKT describing a projected coordinate system

An example of WKT description of a projected coordinate system, formatted for readability. The EPSG code equivalent to the North_American_Albers_Equal_Area_Conic projected coordinate system is 102008.

PROJCS["North_America_Albers_Equal_Area_Conic", GEOGCS["GCS_North_American_1983", DATUM["D_North_American_1983", SPHEROID["GRS_1980",6378137.0, 298.257222101] ], PRIMEM["Greenwich",0.0], UNIT["Degree",0.0174532925199433] ], PROJECTION["Albers"], PARAMETER["False_Easting",0.0], PARAMETER["False_Northing",0.0], PARAMETER["Central_Meridian",-96.0], PARAMETER["Standard_Parallel_1",20.0], PARAMETER["Standard_Parallel_2",60.0] PARAMETER["Latitude_Of_Origin",40.0], UNIT["Meter",1.0] ]

### 12.10.5 Point data dictionary

Any 2D object (Viewport, image XObject, or form XObject) that contains a measure dictionary ("Table 266 — Entries in a measure dictionary") of subtype GEO can optionally include a PtData entry. The value of a PtData entry is a point data dictionary or an array of point data dictionaries of extended data associated with points in the 2D space. "Table 272 — Entries in a point data dictionary" lists the entries of a point data dictionary.


Table 272 — Entries in a point data dictionary

| Key | Type | Description |
| --- | --- | --- |
| Type | name | (Required; PDF 2.0) The type of PDF object that this dictionary describes; shall be PtData for a point data dictionary. |
| Subtype | name | (Required; PDF 2.0) Shall be Cloud. |
| Names | array | (Required; PDF 2.0) An array of names that identify the internal data elements of the individual point arrays in the XPTS array. There are three predefined names: |
| LAT | latitude in degrees. The XPTS value is a number type. |  |
| LON | longitude in degrees. The XPTS value is a number type. |  |
| ALT | altitude in metres. The XPTS value is a number type. |  |

> **NOTE** These names are, in effect, column headers for the array of XPTS values.

XPTS array (Required; PDF 2.0) An array of arrays of values. The number of members in each interior array shall correspond to the size of the Names array; each member in the interior arrays is of a type defined by the corresponding name in the Names array. The XPTS array is a collection of tuples without any guaranteed ordering or relationship from point to point.

The names LAT, LON, and ALT are predefined, and shall be used to associate altitude information with latitude and longitude positions.

## 12.11 Document requirements

### 12.11.1 General

A PDF processor that supports document requirements shall evaluate them before execution of any ECMAScripts.

The Requirements entry in the document catalog (see 7.7.2, "Document catalog dictionary") shall be an array of requirement dictionaries, whose entries are shown in "Table 273 — Entries common to all requirement dictionaries".

Table 273 — Entries common to all requirement dictionaries

| Key | Type | Description |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes. If present, shall be Requirement for a requirement dictionary. |
| S | name | (Required) The type of requirement that this dictionary describes. See "Table 276 — Entries in a requirement handler dictionary" for valid values. |


| Key | Type | Description |
| --- | --- | --- |
| V | name or | (Optional; PDF 2.0) The minimum version level of support needed to satisfy |
| dictionary | the requirement. See 12.11.4, "Requirement versions". If this entry is absent, determining if the requirement is satisfied shall be done without regard to version number. Unless otherwise mentioned in the entries in "Table 276 — Entries in a requirement handler dictionary", the value shall represent the PDF version. |  |
| RH | dictionary | (Optional) An alternative requirement handler dictionary or an array of such |
| or array | dictionaries. Each dictionary identifies a requirement handler that shall be disabled (not invoked) if the interactive PDF processor can check the requirement specified in the S entry (whether or not it can satisfy that requirement). See 12.11.5, "Requirement handlers". Default value: an empty array. |  |
| Penalty | integer | (Optional; PDF 2.0) An integer value that shall be between 0 and 100 (inclusive) that represents the penalty value to be applied when this requirement cannot be met by a PDF processor. Default value is 100. |

There are two additional keys that may appear in a requirements dictionary that are specific to certain types of requirements (i.e., value of the S key). These are described in "Table 274 — Entries for specific types of requirements".

Table 274 — Entries for specific types of requirements

| Key | Type | Description |
| --- | --- | --- |
| Encrypt | dictionary | (Required, if the S key has the value Encryption: PDF 2.0) An encryption dictionary ("Table 20 — Entries common to all encryption dictionaries") that defines all of the relevant aspects of the encryption method needed to process the document. |
| DigSig | dictionary | (Optional, but only used when the S key has the value of DigSig, DigSigValidation or DigSigMDP; PDF 2.0) A signature dictionary ("Table 255 — Entries in a signature dictionary") that defines all of the relevant aspects that are needed in order to process the digital signature requirements. |

### 12.11.2 Requirement types

The S entry in a requirement dictionary identifies ("Table 273 — Entries common to all requirement dictionaries") a feature of the PDF language or a capability that may be present in a PDF processor.
Such entries enable the document to identify feature(s) of PDF beyond those commonly expected, such as 2D graphics rendering, and are required for correct handling in accordance with this document. In addition, although not required for viewing, a document may also use requirement values that stipulate required features of interactive PDF processors such as the ability to interact with or modify the document.

"Table 275 — Requirement types" lists requirement types that have been defined through PDF 2.0.


Table 275 — Requirement types

| Type | Description |
| --- | --- |
| OCInteract | Requires an interactive PDF processor to be able to display the list of optional content groups (OCGs) in the Order array as described in "Table 99 — Entries in an optional content configuration dictionary". In addition, requires that an interactive PDF processor support the SetOCGState action (see 12.6.4.13, "Set-OCG-state actions"). Additional information about OCGs can be found in 8.11.2, "Optional content groups" and 8.11.4.4, "Usage and usage application dictionaries". |
| OCAutoStates | Requires an interactive PDF processor to implement the various Usage values that can be present as the value of the AS key in an OCD as described in "Table 99 — Entries in an optional content configuration dictionary" and 8.11.4.4, "Usage and usage application dictionaries". |
| AcroFormInteract | Requires support for user interaction with forms (see 12.7, "Forms") defined as interactive form dictionaries including updating field appearances when values change. In addition, support for Trigger Actions (12.6.3, "Trigger events") is required. |

> **NOTE 1** This requirement does not cover presentation of a form’s static appearance. That presentation uses annotation appearances (12.5.5, "Appearance streams"), which all PDF processors are assumed to support.

| Navigation | Requires support for the presentation and handling of basic navigational elements including link annotations (12.5.6.5, "Link annotations") and outlines (12.3.3, "Document outline"). In addition, support shall be provided for GoTo, GoToR and URI actions (12.6.4, "Action types") in any of these elements or as a document, page or annotation trigger events (12.6.3, "Trigger events"). |
| --- | --- |
| Markup | Requires support for the creation, modification and deletion of markup annotations (12.5.6.2, "Markup annotations") including text annotations. In addition, any time the visual appearance of the annotation changes, the appearance stream shall be updated. |
| 3DMarkup | Requires support for the creation, modification and deletion of text notes and markup annotations on 3D objects (13.6.7.3.6, "3D comment note"). In addition, any time where the visual appearance of the annotation changes, the appearance stream shall be updated. |
| Multimedia | Requires support for multimedia (Screen) annotations (12.5.6.18, "Screen annotations"). See also 13.2, "Multimedia". The support that is required is for the general multimedia framework, not for an external player for any specific type of multimedia content. Negotiation of the choice of an external player is handled by the must honour (MH) and best efforts (BE) mechanism (13.2.2, "Viability") that is defined as part of the multimedia framework (13.2, "Multimedia"). |
| U3D | Requires support for 3D data streams conforming to the U3D specification. This shall apply to the use of U3D in either 3D (13.6.3, "3D streams") or RichMedia annotations (13.7.2.2, "RichMediaSettings dictionary"). This also includes support for associated ECMAScripts. If a V key is present in its Requirements dictionary, it shall represent the version of U3D and not the PDF version. |


| Type | Description |
| --- | --- |
| PRC | Requires support for 3D data streams conforming to the PRC specification. This shall apply to the use of PRC in either 3D (13.6.3, "3D streams") or RichMedia annotations (13.7.2.2, "RichMediaSettings dictionary"). This also includes support for associated ECMAScripts. If a V key is present in its Requirements dictionary, it shall represent the version of PRC and not the PDF version. |
| Action | Requires support for actions in general (12.6, "Actions"), other than GoTo and URI actions (which are subsumed under the Navigation and Attachment requirements), SetOCGState (subsumed under OCInteract) and ECMAScript actions (which are separately declared with the EnableJavaScripts requirement). |
| EnableJavaScripts | Requires support for execution of ECMAScripts appearing in ECMAScript actions and in the ECMAScript name tree for document-level ECMAScripts. |

> **NOTE 2** ECMAScripts contained in 3D & RichMedia annotations are handled by their respective requirements.

Attachment Requires support for displaying (to the user) the list of file attachments (see 7.11.4, "Embedded file streams") and enabling users to extract any existing attachments. In addition, support is provided for GoToE actions (12.6.4, "Action types") when located in any navigational element or trigger event.

> **NOTE 3** The list of file attachments is taken from the EmbeddedFiles names tree (see 7.7.4, "Name dictionary") and any FileAttachment annotation (see 12.5.6.15, "File attachment annotations").

| AttachmentEditing | In addition to the requirements of the Attachment value, support for adding new attachments into the EmbeddedFiles names tree (see 7.7.4, "Name dictionary"), deleting existing ones as well as modification of attachment attributes (e.g., name & description) are also required. |
| --- | --- |
| Collection | Requires support for displaying the embedded files referenced from the document’s collection dictionary (12.3.5, "Collections") along with any associated metadata. Also requires that the user can extract or otherwise view the contents of each item in the collection. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the Collection requirement should not be specified for the unencrypted wrapper document. |

> **NOTE 4** Although the unencrypted wrapper document is a collection, the intent of the wrapper is to enable PDF processors that are unable to decrypt the embedded encrypted payload document to present the content of the unencrypted wrapper document to assist users in understanding the cryptographic requirements of the encrypted payload document. Specifying the Collection requirement on the wrapper could discourage PDF processors incapable of displaying collections from presenting the unencrypted wrapper content.

CollectionEditing In addition to the requirements of the Collection value, support for adding to the collection (12.3.5, "Collections"), deleting existing items as well as modification of collection item attributes and metadata, are also required.


| Type | Description |
| --- | --- |
| DigSigValidation | Requires support for the validation of digital signatures (both document and certifying) that have been applied to the PDF including the handling of supplied revocation information. See 12.8, "Digital signatures", and 12.8.3.4.5, "Requirements for validation of PAdES signatures". This does not require the support for 12.8.2.2.2, "Validating signatures that use the DocMDP transform method" which is a separate requirement: DigSigMDP. |
| DigSig | In addition to the validation requirements of DigSigValidation, this specifies the requirements to support the application of a digital signature to a document (also known as signing). See 12.8, "Digital signatures". |
| DigSigMDP | In addition to the requirements of DigSig and DigSigValidation, this also requires support for modification detection analysis to determine if only allowable modifications have been made. See 12.8.2.2.2, "Validating signatures that use the DocMDP transform method". |
| RichMedia | Requires support for playing rich media annotations as specified in 13.7.2, "RichMedia annotations". |
| Geospatial2D | Requires support for processing provided geospatial information in the page content and associated resources. See 12.10, "Geospatial features". |
| Geospatial3D | Requires support for processing provided geospatial information in any 3D annotations. See 12.10, "Geospatial features". This type requires provision within the 3D Annotation, and also applies 3D requirements to Geospatial information. |
| DPartInteract | Requires support for the display of the DParts hierarchy and its use for navigation of the document parts. See 14.12, "Document parts". |
| SeparationSimulation | Requires support for simulation separations as described in 10.8, "Rendering for separations" and 10.8.3, "Separation simulation". |

> **NOTE 5** This is sometimes referred to as "Overprint Preview".

| Transitions | Requires support for transitions/presentations (12.4.4, "Presentations") as well as transition actions (12.6.4.14, "Rendition actions") |
| --- | --- |
| Encryption | Requires support for the specific set of encryption parameters that are specified by the encryption dictionary provided as the value of the Encrypt key in the requirement dictionary (see "Table 273 — Entries common to all requirement dictionaries" and "Table 274 — Entries for specific types of requirements"). (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the Encryption requirement should not be specified for the unencrypted wrapper document. |

> **NOTE 6** The intent of the wrapper is to enable PDF processors that are unable to decrypt the embedded encrypted payload document to present the content of the unencrypted wrapper document to assist users in understanding the cryptographic requirements of the encrypted payload document. Specifying the Encryption requirement on the wrapper could discourage PDF processors incapable of decrypting the embedded encrypted payload from presenting the unencrypted wrapper content.

Additional requirement types, including ones identifying vendor-specific features, may be registered according to the rules described in Annex E, "Extending PDF".


### 12.11.3 Requirement penalty values

Each Requirements dictionary shall contain an S key whose value should be chosen from a list of those defined in "Table 275 — Requirement types" but only for those features that are deemed critical for the successful rendering of the document. The values of the Penalty key is the penalty number expressed as an integer in the range [0, 100]. The presence of this requirements dictionary indicates that the associated feature is used or needed by the document.

A value of zero for the Penalty key would indicate that although the document uses this feature the need is optional. A value of 100 indicates that this document will not produce the author’s intent unless the PDF processor can fully support this feature. Values between 0 and 100 are available to weight the value of this feature among other features in the same document requirements array as well as when contributing to the total penalty points to weigh against other documents in the choosing process if alternatives are available.

In the situation where the penalty values are being used to evaluate the presentation of the base PDF document, and there exist no other alternates, if the penalty value exceeds 100 then the PDF processor should not attempt to display or process the document.

### 12.11.4 Requirement versions

A requirement dictionary may include a V entry (PDF 2.0) that specifies a version number for a specific technology related to the requirement in question. It might be a PDF version number, an XFA version number (for those requirement related to XFA), a version of U3D or PRC, or a vendor-specific extension level.

Some PDF and XFA features have evolved over successive PDF versions. A PDF file may contain uses of a feature or an embedded data stream that can only be successfully interpreted by a PDF processor supporting a specific version of PDF or higher. This constraint may be indicated by including a V entry in the requirement dictionary identifying the PDF version.

The value of V shall be one of the following:

• A name that specifies a version number, represented as two or more decimal integers separated by a period. The number of decimals places is determined by the technology in question for which the version applies. For example, PDF and XFA versions are always two digits; others are always three.

> **NOTE** This is specified as a name, not as a number, in order to avoid any ambiguities caused by inexact internal representation of decimal fractions. No non-numerals, except for the decimal point, can be present.
• An extensions dictionary that specifies a vendor-specific extension to a PDF version (see 7.12, "Extensions dictionary"). It shall only be used when describing a version of PDF for which a simple version (e.g., 1.7) is not sufficient and has no meaning for any other requirement. The extensions dictionary shall contain exactly one entry, whose key shall correspond to the registered prefix for the vendor that has defined this requirement type.
If the V entry is not present, determining if the requirement is satisfied shall be done without regard to version number.


### 12.11.5 Requirement handlers

An alternative requirement handler is an alternative means for determining if the requirements associated with a document’s features are satisfied. Traditionally, requirements handling has been accomplished with an ECMAScript segment in the document-level ECMAScripts stored in the document’s name dictionary.

To create PDF documents that are compatible with processors that support PDF 1.7 and PDF 2.0, both the document requirements feature and the ECMAScript alternative requirements handler should be present in the document.

A document using the document requirements feature can specify that an ECMAScript function is to be disabled by including an RH entry whose value is an alternative requirement handler dictionary (or an array of such dictionaries), each of which identifies a handler that shall be disabled. "Table 276 — Entries in a requirement handler dictionary" describes the entries in an alternative requirement handler dictionary.

A requirement handler is a program that verifies certain requirements are satisfied. "Table 276 — Entries in a requirement handler dictionary" describes the entries in a requirement handler dictionary.

Table 276 — Entries in a requirement handler dictionary

| Key | Type | Description |
| --- | --- | --- |
| Type | name | (Optional) The type of PDF object that this dictionary describes. If present, shall be ReqHandler for a requirement handler dictionary. |
| S | name | (Required) The type of requirement handler that this dictionary describes. Valid requirement handler types shall be JS (for ECMAScript requirement handlers) and NoOp. A value of NoOp allows older PDF processors to ignore unrecognised requirements. This value does not add any specific entry to the requirement handler dictionary. |
| Script | text string | (Optional; valid only if the S entry has a value of JS) The name of a document-level ECMAScript action stored in the document name dictionary (see 7.7.4, "Name dictionary"). If the PDF processor understands the parent document requirements dictionary and can verify the requirement specified in that dictionary, it shall disable execution of the requirement handler identified in this dictionary. |

If an alternative requirement handler dictionary has an S entry with an unrecognised type, it shall be ignored.

### 12.11.6 Requirements processing

Document requirements can be presented for an individual document as the value of the Requirements key in that document's catalog. Document requirements shall be evaluated before execution of any document ECMAScripts. If requirements cannot be met, as determined by the computation of the penalty value as described in 12.11.3, "Requirement penalty values", then the processing of the document shall not continue.


For PDFs that are acceptable for processing, all subsequent processing shall occur without regard for the outcome of the requirements computation.

> **NOTE 1** That is, there is no formal connection between the requirement type and the operation of the associated feature(s).
If the reader encounters an unsupported feature (whether or not that feature was declared as a requirement), it shall take the normal fallback actions.

> **NOTE 2** The most common fallback is to do nothing — that is, to ignore the use of the unsupported feature. Other common fallbacks can include presenting a static annotation appearance in place of a dynamic annotation.
