Annex O (normative) Fragment identifiers

## O.1 General

Fragment identifiers (https://www.w3.org/DesignIssues/Fragment.html) are added at the end of a URI to provide a reference to subordinate content within the target of the URI. These fragment identifiers anchor onto specific content or characteristics of the document to which the URI refers. PDF is just one type of document that can be referenced from a URI and the fragment identifiers that are relevant to a PDF are different from those of other document formats. This clause describes the fragment identifiers that are generally applicable to PDF documents.

## O.2 Fragment identifiers

The fragment identifier is comprised of one or more parameters which are appended to a single URI, each of which is separated by the AMPERSAND (28h) character. Each parameter represents either an action to be performed on the document when it is opened or an object within the document.
Arguments to those parameters, which represent information needed to qualify the action or object, shall be COMMA (2Ch) separated when more than one is required. Fragment identifiers shall be processed and (if required) executed from left to right as they appear in the character string that makes up the fragment identifier.

### O.2.1 PDF object identifiers

A number of standard PDF objects can be referenced through the use of a fragment identifier as described below.  These are useful primarily when referring to them from external to the PDF such as a web page or web API.

Table Annex O.3 — PDF object identifiers

| Parameter | Arguments | Description |
| --- | --- | --- |
| page | pageNum | A specified page number of the document. The argument shall be a positive integer number. The first page in the document has a pageNum value of 1. When used as part of a PDF open parameter, the PDF processor shall open the document to the specified page. |
| nameddest | name | A specified named destination (see 12.3.2.4, "Named destinations"). The argument provided is a string which shall correspond to the name of a destination in the target document. When used as part of a PDF open parameter, the PDF processor shall open the document to the page referred to by the named destination. |


ISO 32000-2:2020

| Parameter | Arguments | Description |
| --- | --- | --- |
| structelem | structID | The structID is a byte string with URI encoding that matches the ID key within a StructElem dictionary of the document. If no content is contained within the hierarchy of the structure element or structID does not match a structure element, the first page in the document shall be identified. When used as part of a PDF open parameter, the PDF processor shall open to the page on which the first content item, hierarchically contained within the structure element identified by the structure ID, is located. |
| comment | commentID | The commentID shall be the value of an annotation name, which is defined by |
| the NM key in the corresponding annotation dictionary (see | 12.5.2, "Annotation dictionaries"). If the comment parameter is combined with another parameter that defines a specific page to be identified, then the comment parameter shall appear after that in the URI. |  |

> **NOTE** The NM key is unique to a specific page, but is not guaranteed to be unique to a document. Unless the page on which the comment resides has been selected prior to the comment parameter, the comment will not be selected.
When used as part of a PDF open parameter, the PDF processor shall open the document with the specified comment selected.

ef name The name argument shall be a byte string used to match a file specification dictionary in the EmbeddedFiles name tree. Any remaining parameters after this parameter apply to the selected embedded file. When used as part of a PDF open parameter, the PDF processor shall open the embedded file contained within the EmbeddedFiles name tree identified by name. Security should be strongly considered when opening an embedded file. When opening a file that is not from a trusted source, a PDF processor may choose to prompt the user or even prevent opening of the file.

### O.2.2 PDF open parameters

The fragment identifiers listed in this clause operate on the document at the point it is opened, and are therefore referred to as PDF open parameters. These fragment identifiers should be processed immediately after any other document-specified open parameters have been processed.

All coordinate values (left, right, top, and bottom) shall be expressed in the default user space coordinate system.

Table Annex O.4 — PDF open parameters

| Parameter | Arguments | Description |
| --- | --- | --- |
| zoom | scale | Open the document with the specified zoom level and optional offset. The |
| scale,left,top | scale argument shall be either an integer or floating point value representing the percentage to which the document should be zoomed, where a value of 100 would correspond to a zoom of 100%. The left and top arguments are optional, but shall both be specified if either is included. The left and top arguments shall be integer or floating point values representing the offset from the left and top of the page in a coordinate system where 0,0 represents the top left corner of the page. |  |


| Parameter | Arguments | Description |
| --- | --- | --- |
| view | keyword,position | Open the document with the specified destination set as the view. The arguments shall correspond to those found in 12.3.2.2, "Explicit destinations". The keyword shall correspond to one of the keywords defined in "Table 149 — Destination syntax" with appropriate position values. |
| viewrect | left,top,width,height | Open the document with the specified window view rectangle. The left and top arguments shall be integer or floating point values representing the offset from the left and top of the page in a coordinate system where 0,0 represents the top left corner of the page. The width and height arguments shall be integer or floating point values representing the width and height of the view. |
| highlight | left,right,top,bottom | Open the document with the specified rectangle highlighted. Each argument shall be an integer or floating point value representing the rectangle measured from the top left corner of the page. The nature of the highlighting is implementation-dependent. |
| search | wordList | Open the document and search for one or more words, selecting the first matching word in the document. The wordList argument defines the search words and shall be a string enclosed within quotation marks comprised of individual words separated by space characters. |

> **NOTE 1** Some browsers require that the space characters be encoded appropriately for a URI.

fdf URI Open the document and then import the data from the specified FDF or XFDF file. The URI shall be either a relative or absolute URI to an FDF or XFDF file. The fdf parameter should be specified as the last parameter to a given URI.

> **NOTE 2** The fdf parameter is recommended to be the last parameter so that the document can open directly to the appropriate view.


ISO 32000-2:2020
