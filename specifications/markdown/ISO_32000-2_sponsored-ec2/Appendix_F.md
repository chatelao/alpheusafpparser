Annex F (normative) Linearized PDF

## F.1 General

Linearization of PDF is an optional feature available beginning in PDF 1.2 that enables efficient incremental access of the file in a network environment. A PDF processor that does not support this optional feature can still successfully process linearized files although not as efficiently. Enhanced PDF processors can recognise that a PDF file has been linearized and may take advantage of that organisation (as well as added hint information) to enhance performance.

The primary goal for a linearized PDF file is to achieve the following behaviour for documents of arbitrary size and so that the total number of pages in the document should have little or no effect on the user-perceived performance of viewing any particular page:

• When a document is opened, display the first page as quickly as possible. The first page to be viewed may be an arbitrary page of the document, not necessarily page 0 (though opening at page 0 is most common).
• When the user requests another page of an open document (for example, by going to the next page or by following a link to an arbitrary page), display that page as quickly as possible.
• When data for a page is delivered over a slow channel, display the page incrementally as it arrives.
To the extent possible, display the most useful data first.
• Permit user interaction, such as following a link, to be performed even before the entire page has been received and displayed.

> **NOTE** A linearized PDF is optimised for viewing of read-only PDF documents. A linearized PDF is intended to be generated once and read many times.
Incremental update shall still be permitted, but the resulting PDF is no longer linearized and subsequently shall be treated as ordinary PDF. Linearizing it again may require reprocessing the entire PDF file; see G.7, "Accessing an updated file" for details.

Linearized PDF requires two additions to the PDF specification:

• When a PDF file is initially accessed (such as by following a URL hyper link from some other document), the file type is not known to the PDF processor. Therefore, the PDF processor initiates a transaction to retrieve the entire document and then inspects the MIME tag of the response as it arrives. The MIME tag for PDF is defined by Internet RFC 8118. Only at that point is the document known to be PDF. Additionally, with a properly configured server environment, the length of the document becomes known at that time.
• Rules for the ordering of objects in the PDF file •    Additional optional data structures, called hint tables, that enable efficient navigation within the document Both of these additions are relatively simple to describe; however, using them effectively requires a deeper understanding of their purpose. Consequently, this annex goes considerably beyond a simple specification of these PDF extensions to include background, motivation, and strategies.


ISO 32000-2:2020

• F.2, "Background and assumptions" provides background information about the properties of the Web that are relevant to the design of Linearized PDF.
• F.3, "Linearized PDF document structure" specifies the file format and object-ordering requirements of Linearized PDF.
• F.4, "Hint tables" specifies the detailed representation of the hint tables.
• Annex G, "Linearized PDF access strategies" outlines strategies for accessing Linearized PDF over a network, which in turn determine the optimal way to organise the PDF file.
The reader is assumed to be familiar with the basic architecture of the Web, including terms such as URL, HTTP, and MIME.

## F.2 Background and assumptions

> **NOTE 1** The principal problem addressed by the Linearized PDF design is the access of PDF documents through the Web. This environment has the following important properties: •    The access protocol (HTTP) is a transaction consisting of a request and a response. The PDF processor presents a request in the form of a URL, and the server sends a response consisting of one or more MIME-tagged data blocks.
• After a transaction has completed, obtaining more data requires a new request-response transaction. The connection between PDF processor and server does not ordinarily persist beyond the end of a transaction, although some implementations might attempt to cache the open connection to expedite subsequent transactions with the same server.
• Round-trip delay can be significant. A request-response transaction can take up to several seconds, independent of the amount of data requested.
• The data rate can be limited. A typical bottleneck is a slow link between the PDF processor and the Internet service provider.
These properties are generally shared by other wide-area network architectures besides the Web.
Also, CD-ROMs share some of these properties, since they have relatively slow seek times and limited data rates compared to magnetic media. The remainder of this annex focuses on the Web.

Some additional properties of the HTTP protocol are relevant to the problem of accessing PDF files efficiently. These properties might not all be shared by other protocols or network environments.

• When a PDF file is initially accessed (such as by following a URL hyper link from some other document), the file type is not known to the PDF processor. Therefore, the PDF processor initiates a transaction to retrieve the entire document and then inspects the MIME tag of the response as it arrives. Only at that point is the document known to be PDF. Additionally, with a properly configured server environment, the length of the document becomes known at that time.
• The PDF processor can abort a response while the transaction is still in progress if it decides that the remainder of the data are not of immediate interest. In HTTP, aborting the transaction requires closing the connection, which interferes with the strategy of caching the open connection between transactions.
• The PDF processor can request retrieval of portions of a document by specifying one or more byte ranges (by offset and count) in the HTTP request headers. Each range can be relative to either the beginning or the end of the file. The PDF processor can specify as many ranges as it wants in the request, and the response consists of multiple blocks, each properly tagged.


• The PDF processor can initiate multiple concurrent transactions in an attempt to obtain multiple responses in parallel. This is commonly done, for instance, to retrieve inline images referenced from an HTML document. This strategy is not always reliable and might backfire if the transactions interfere with each other by competing for scarce resources in the server or the communication channel.

> **NOTE 2** Extensive experimentation has determined that having multiple concurrent transactions does not work very well for PDF in some important environments. Therefore, Linearized PDF is designed to enable good performance to be achieved using only one transaction at a time. In particular, this means that the PDF processor needs to have sufficient information to determine the byte ranges for all the objects needed to display a given page of the PDF file so that it can specify all those byte ranges in a single request.
The following additional assumptions are made about the PDF processor and its local environment:

• The PDF processor has plenty of local temporary storage available. It rarely will need to retrieve a given portion of a PDF document more than once from the server.
• The PDF processor is able to display PDF data quickly once it has been received. The performance bottleneck is assumed to be in the transpo rt system (throughput or round-trip delay), not in the processing of data after it arrives.
The consequence of these assumptions is that it might be advantageous for the PDF processor to do considerable extra work to minimise delays due to communications.

Such work includes maintaining local caches and reordering actions according to when the needed data becomes available.

## F.3 Linearized PDF document structure

### F.3.1 General

> **NOTE** This clause was updated to allow alternative part orderings (2020).
Except as noted below , all elements of a Linearized PDF file shall be as specified in 7.5, "File structure", and all indirect objects in the PDF file shall be divided into two groups.

• The first group shall consist of the document catalog dictionary, other document-level objects, and all objects belonging to the first page of the document. These objects shall be numbered sequentially, starting at the first object number after the last number of the second group. (The stream containing the hint tables, called a hint stream, may be numbered out of sequence; see F.3.6, "Hint streams (Parts 5 and 10)".
• The second group shall consist of all remaining objects in the document, including all pages after the first, all shared objects (objects referenced from more than one page, not counting objects referenced from the first page), and so forth. These objects shall be numbered sequentially starting at 1.
These groups of objects shall be indexed by exactly two cross-reference table sections. For pedagogical reasons the linearized PDF is considered to be composed from 11 parts and the composition of these groups is discussed in more detail below. All objects shall have a generation number of 0.

Beginning with PDF 1.5, PDF files may contain object streams (7.5.7, "Object streams"). In linearized files containing object streams, the following conditions shall apply:

• These additional objects may not be contained in an object stream: the linearization dictionary,


ISO 32000-2:2020

the document catalog dictionary, and page objects.
• Objects stored within object streams shall be given the highest range of object numbers within the main and first-page cross-reference sections.
• For PDF files containing object streams, hint data may specify the location and size of the object streams only (or uncompressed objects), not the individual compressed objects. Similarly, shared object references shall be made to the object stream containing a compressed object, not to the compressed object itself.
• Cross-reference streams (7.5.8, "Cross-reference streams") may be used in place of traditional cross-reference tables. The logic described in this subclause shall still apply, with the appropriate syntactic changes.

> **EXAMPLE 1** Part 1: Header

%PDF-2.0 % … Binary characters …

> **EXAMPLE 2** Part 2: Linearization parameter dictionary

43 0 obj
| << /Linearized 1.0 | % Version |  |
| --- | --- | --- |
| /L 54567 | % File length |  |
| /H [475  98] | % Primary hint stream offset and length (part 5) |  |
| /O | 5 | % Object number of first page’s page object (part 6) |
| /E | 437 | % Offset of end of first page |
| /N | 1 | % Number of pages in document |
| /T | 2786 | % Offset of first entry in main cross-reference table (part |

11) >> endobj

> **EXAMPLE 3** Part 3: First-page cross-reference table and trailer

xref 43 4 0000000052  0000 n 0000000392  0000 n 0000001073  0000 n … Cross-reference entries for remaining objects in the first page … 0000000475  0000 n trailer
| << /Size 57 | % Total number of cross-reference table entries in document |
| --- | --- |
| /Prev 52776 | % Offset of main cross-reference table (part 11) |
| /Root 44 0 R | % Indirect reference to catalog dictionary (part 4) |
| … Any other entries, such as Info and Encrypt … | %(part 9) >> % Dummy cross-reference table offset |

startxref

%%EOF

> **EXAMPLE 4** Part 4: Document catalog dictionary and other required document -level objects (can precede or follow part 5)

44 0 obj <</Type /Catalog /Pages 42 0 R >> endobj … Other objects …


> **EXAMPLE 5** Part 5: Primary hint stream (can precede or follow part 4 or part 6)

56 0 obj <</Length  57 … Possibly other stream attributes, such as Filter … /S 21 % Position of shared object hint table … Possibly entries for other hint tables … stream >> … Page offset hint table … … Shared object hint table … … Possibly other hint tables … endstream endobj

> **EXAMPLE 6** Part 6: First-page section (can precede or follow part 5)

45 0 obj <</Type /Page … >> endobj

… Outline hierarchy (if PageMode value in the document catalog dictionary is UseOutlines) … … Objects for first page, including both shared and nonshared objects …

> **EXAMPLE 7** Part 7: Remaining pages

1 0 obj <</Type /Page … Other page attributes, such as MediaBox, Parent, and Contents … >> endobj

… Nonshared objects for this page …

… Each successive page followed by its nonshared objects …

… Last page followed by its nonshared objects …

> **EXAMPLE 8** Part 8: Shared objects for all pages except the first

… Shared objects …

> **EXAMPLE 9** Part 9: Objects not associated with pages, if any

… Other objects …

> **EXAMPLE 10** Part 10: Overflow hint stream (optional)

… Overflow hint stream …

> **EXAMPLE 11** Part 11: Main cross-reference table and trailer

xref 0  43
| 0000000000 | 65535 f … Cross-reference entries for all except first page’s objects … trailer |
| --- | --- |
| <</Size 43>> | % Trailer need not contain other entries; in particular, % it shall not have a Prev entry % Offset of first-page cross-reference table (part 3) startxref |
| %%EOF |  |


ISO 32000-2:2020

### F.3.2 Header (Part 1)

The Linearized PDF file shall begin with the standard header line (see 7.5.2, "File header").
Linearization is independent of PDF version number and may be applied to any PDF file of version 1.1 or greater.

The binary characters following the PERCENT SIGN (25h) on the second line are characters with codes 128 or greater, as recommended in 7.5.2, "File header".

### F.3.3 Linearization parameter dictionary (Part 2)

Following the header, the first object in the body of the PDF file (part 2) shall be an indirect dictionary object, the linearization parameter dictionary, which shall contain the parameters listed in "Table F.1 — Entries in the linearization parameter dictionary". All values in this dictionary shall be direct objects. There shall be no references to this dictionary anywhere in the document; however, the firstpage cross-reference table (part 3) shall contain a normal entry for it.

The linearization parameter dictionary shall be entirely contained within the first 1024 bytes of the PDF file. This limits the amount of data a PDF processor will have to read before deciding whether the file is linearized.

Table F.1 — Entries in the linearization parameter dictionary

| Parameter | Type | Value |
| --- | --- | --- |
| Linearized | number | (Required) A version identification for the linearized format. |
| L | integer | (Required) The length of the entire PDF file in bytes. It shall be exactly equal to the actual length of the PDF file. A mismatch indicates that the file is not linearized and shall be treated as ordinary PDF file, ignoring linearization information. (If the mismatch resulted from appending an update, the linearization information may still be correct but requires validation; see G.7, "Accessing an updated file" for details.) |
| H | array | (Required) An array of two or four integers, [offset1 length1] or [offset1 length1 offset2 length2]. offset1 shall be the offset of the primary hint stream from the beginning of the PDF file. (This is the beginning of the stream object, not the beginning of the stream data.) length1 shall be the length of this stream, including stream object overhead. If the value of the primary hint stream dictionary’s Length entry is an indirect reference, the object it refers to shall immediately follow the stream object, and length1 also shall include the length of the indirect length object, including object overhead. If there is an overflow hint stream, offset2 and length2 shall specify its offset and length. |
| O | integer | (Required) The object number of the first page’s page object. |
| E | integer | (Required) The offset of the end of the first page (the end of Example 6 in F.3, "Linearized PDF document structure"), relative to the beginning of the PDF file. |
| N | integer | (Required) The number of pages in the document. |


| Parameter | Type | Value |
| --- | --- | --- |
| T | integer | (Required) In documents that use standard main cross-reference tables (including hybrid-reference files; see 7.5.8.4, "Compatibility with applications that do not support compressed reference streams"), this entry shall represent the offset of the white-space character preceding the first entry of the main cross-reference table (the entry for object number 0), relative to the beginning of the PDF file. Note that this differs from the Prev entry in the first-page trailer, which gives the location of the xref line that precedes the table. (PDF 1.5) Documents that use cross-reference streams exclusively (see 7.5.8, "Cross- reference streams"), this entry shall represent the offset of the main cross-reference stream object in the PDF file. |
| P | integer | (Optional) The page number of the first page; see F.3.4, "First-page cross-reference |

table and trailer (Part 3)". Default value: 0.

### F.3.4 First-page cross-reference table and trailer (Part 3)

Part 3 shall contain the cross-reference table for objects belonging to the first page as well as for the document catalog dictionary and document-level objects appearing before the first page (discussed in F.3.5, "Document catalog dictionary and document-level objects (Part 4)"). Additionally, this crossreference table shall contain entries for the linearization parameter dictionary (at the beginning) and the primary hint stream (at the end). This table shall be a valid cross-reference table as defined in 7.5.4, "Cross-reference table", although its position in the PDF file shall not be at the end of the PDF file.
It shall consist of a single cross-reference subsection that has no free entries.

In PDF 1.5 and later, cross-reference streams (see 7.5.8, "Cross-reference streams") may be used in linearized files in place of traditional cross-reference tables. The logic described in this section, along with the appropriate syntactic changes for cross-reference streams shall still apply.

Below the table shall be the first-page trailer. The trailer’s Prev entry shall give the offset of the main cross-reference table near the end of the PDF file. A PDF processor that does not support the linearized feature will process this correctly even though the trailers are linked in an unusual order. It interprets the first-page cross-reference table as an update to an original document that is indexed by the main cross-reference table.

The first-page trailer shall contain valid Size and Root entries, as well as any other entries needed to display the document. The Size value shall be the combined number of entries in both the first-page cross-reference table and the main cross-reference table.

The first-page trailer may optionally end with startxref, an integer, and %%EOF, just as in an ordinary trailer. This information shall be ignored.

### F.3.5 Document catalog dictionary and document-level objects (Part 4)

Following the first-page cross-reference table and trailer are the catalog dictionary and other objects that are required when the document is opened. These additional objects (constituting part 4) shall include the values of the following entries if they are present and are indirect objects:

• The PDF processor ViewerPreferences entry in the document catalog dictionary.


ISO 32000-2:2020

• The PageMode entry in the document catalog dictionary. Note that if the value of PageMode is UseOutlines, the outline hierarchy shall be located in part 6; otherwise, the outline hierarchy, if any, shall be located in part 9. See F.3.10, "Other objects (Part 9)" for details.
• The Threads entry in the document catalog dictionary, along with all thread dictionaries it refers to. This does not include the threads’ information dictionaries or the individual bead dictionaries belonging to the threads.
• The OpenAction entry in the document catalog dictionary.
• The AcroForm entry in the document catalog dictionary. Only the top-level interactive form dictionary shall be present, not the objects that it refers to.
• The Encrypt entry in the first-page trailer dictionary. All values in the encryption dictionary shall also be located here.
All other objects shall not be located here but instead shall be at the end of the PDF file; see F.3.10, "Other objects (Part 9)". This includes objects such as page tree nodes, the document information dictionary, and the definitions for named destinations.

> **NOTE** The objects located here are indexed by the first-page cross-reference table, even though they are not logically part of the first page.

### F.3.6 Hint streams (Parts 5 and 10)

The core of the linearization information are stored in data structures known as hint tables, whose format is described in F.4, "Hint tables" They shall provide indexing information that enables the PDF processor to construct a single request for all the objects that are needed to display any page of the document or to retrieve other information efficiently. The hint tables may contain additional information to optimise access by PDF processor extensions to application-specific data.

The hint tables are not logically part of the information content of the document; they shall be derived from the document. Any action that changes the document — for instance, appending an incremental update — invalidates the hint tables. The document remains a valid PDF file but is no longer linearized; see G.7, "Accessing an updated file" for details.

The hint tables are binary data structures that shall be enclosed in a stream object. Syntactically, this stream shall be a PDF indirect object. However, there shall be no references to the stream anywhere in the document. Therefore, it is not logically part of the document, and an operation that regenerates the document may remove the stream.

Usually, all the hint tables shall be contained in a single stream, known as the primary hint stream.
Optionally, there may be an additional stream containing more hints, known as the overflow hint stream. The contents of the two hint streams shall be concatenated and treated as if they were a single unbroken stream.

The primary hint stream, which shall be required, is shown as part 5 in Example 5. The order of this part and the first-page section, shown as part 6, may be reversed; alternatively, the order of this part and the document catalog dictionary and document-level objects, shown as part 4, may be reversed.
See Annex G, "Linearized PDF access strategies" for considerations on the choice of placement. The overflow hint stream, part 10, is optional.

The location and length of the primary hint stream, and of the overflow hint stream if present, shall be given in the linearization parameter dictionary at the beginning of the PDF file.


The hint streams shall be assigned the last object numbers in the PDF file — that is, after the object number for the last object in the first page, including any objects stored within object streams. Their cross-reference table entries shall be at the end of the first-page cross-reference table. This object number assignment shall be independent of the physical locations of the hint streams in the PDF file.

> **NOTE** This convention keeps their object numbers from conflicting with the numbering of the linearized objects.
With one exception, the values of all entries in the hint streams’ dictionaries shall be direct objects and may contain no indirect object references. The exception is the stream dictionary’s Length entry (see the discussion of the H entry in "Table F.1 — Entries in the linearization parameter dictionary".

In addition to the standard stream attributes, the dictionary of the primary hint stream shall contain entries giving the position of the beginning of each hint table in the stream. These positions shall be counted in bytes relative to the beginning of the stream data (after decoding filters, if any, are applied) and with the overflow hint stream concatenated if present. The dictionary of the overflow hint stream shall not contain these entries. The keys designating the standard hint tables in the primary hint stream’s dictionary are listed in "Table F.2 — Standard hint tables"; F.4, "Hint tables" documents the format of these hint tables. Additionally, there is a required page offset hint table, which shall be the first table in the stream and shall start at offset 0 in the PDF file.

Table F.2 — Standard hint tables

| Key | Hint table |
| --- | --- |
| S | (Required) Shared object hint table (see F.4.3, "Shared object hint table") |
| T | (Required only if thumbnail images exist) Thumbnail hint table (see F.4.4, "Thumbnail hint table") |
| O | (Required only if a document outline exists) Outline hint table (see F.4.5, "Generic hint tables") |
| A | (Required only if article threads exist) Thread information hint table (see F.4.5, "Generic hint tables") |
| E | (Required only if named destinations exist) Named destination hint table (see F.4.5, "Generic hint tables") |
| V | (Required only if an interactive form dictionary exists) Interactive form hint table (see F.4.6, "Extended generic hint tables") |
| I | (Required only if a document information dictionary exists) Information dictionary hint table (see F.4.5, "Generic hint tables") |
| C | (Required only if a logical structure hierarchy exists; PDF 1.3) Logical structure hint table (see F.4.6, "Extended generic hint tables") |
| L | (PDF 1.3) Page label hint table (see F.4.5, "Generic hint tables") |
| R | (Required only if a renditions name tree exists; PDF 1.5) Renditions name tree hint table (see F.4.6, "Extended generic hint tables") |
| B | (Required only if embedded file streams exist; PDF 1.5) Embedded file stream hint table (see F.4.7, "Embedded file stream hint tables") |


ISO 32000-2:2020

New keys may be registered for additional hint tables that are required by application-specific data accessed by PDF processor extensions. See Annex E, "Extending PDF" for further information.

### F.3.7 First-page section (Part 6)

This part of the PDF file contains all the objects needed to display the first page of the document.
| Ordinarily, the first page is page 0 | —that is, the leftmost leaf page node in the page tree. Howeve | r, if the |
| --- | --- | --- |
| document catalog dictionary contains an | OpenAction entry that specifies opening at some page other |  |

than page 0, that page shall be considered the first page and shall be located here. The page number of the first page is given in the P entry of the linearization parameter dictionary.

> **NOTE** As mentioned earlier, the section containing objects belonging to the first page of the document
| can either precede or follow the primary hint stream. The starting | PDF file offset and length of |
| --- | --- |
| this section can be determined from the hint tables. In addition, the | E entry in the linearization parameter dictionary specifies the end of the first page (as an offset relative to the beginning of |
| the PDF file), and the O entry gives the object number of the first page’s | page object. |
| The following objects shall be contained in the first | -page section: |

• The page object for the first page. This object shall be the first one in this part of the PDF file. Its
| object number is given in the linearization parameter dictionary. This | page object shall explicitly |
| --- | --- |
| specify all required attributes, such as | Resources and MediaBox; the attributes may not be |

inherited from ancestor page tree nodes.
• The entire outline hierarchy, if the value of the PageMode entry in the catalog dictionary is UseOutlines. If the PageMode entry is omitted or has some other value and the document has an outline hierarchy, the outline hierarchy shall appear in part 9; see F.3.10, "Other objects (Part 9)" for details.
• All objects that the page object refers to, to an arbitrary depth, except page tree nodes, other page objects and DPart tree nodes.
The order of objects referenced from the page object should facilitate early user interaction and incremental display of the page data as it arrives. The following order should be used:

a) The Annots array and all annotation dictionar ies, to a depth sufficient for those annotations to be
activated. Information required to draw the annotation may be deferred until later since annotations are always drawn on top of (hence after) the contents.
b) The B (beads) array and all bead dictionaries , if any, for this page. If any beads exist for this page, the B
array shall be present in the page dictionary. Additionally, each bead in the thread (not just the first bead) shall contain a T entry referring to the associated thread dictionary.
c) The Resources dictionary, but not the resource objects contained in the dictionary.
d) Resource objects, other than the types listed below, in the order that they are first referenced (directly or
indirectly) from the content stream. If the contents are represented as an array of streams, each resource object shall precede the stream in which it is first referenced. Note that Font, FontDescriptor, and Encoding resources shall be included here, but not substitutable font files referenced from font descriptors (see ite m (g) below).
e) The page contents ( Contents ). If large, this should be represented as an array of indirect references to
content streams, which in turn shall be interleaved with the resources they require. If small, the entire contents should be a single co ntent stream preceding the resources.
f) Image XObjects, in the order that they are first referenced. Images can be assumed to be large and slow
to transfer; therefore, the PDF processor should defer rendering images until all the other contents have been displayed.


g) FontFile streams, which contain the actual definitions of embedded fonts. These can be assumed to be
large and slow to transfer; therefore, the PDF processor should use substitute fonts until the real ones have arrived. Only those fonts for which substitution is possible may be deferred in this way. (Currently, this includes any Type 1 or TrueType font that has a font descriptor with the Nonsymbolic flag set, indicating the Standard Latin character set).
h) See Annex G, "Linearized PDF access strategies" for additional discussion about object order and
incremental drawing strategies.

### F.3.8 Remaining pages (Part 7)

Part 7 of the Linearized PDF file shall contai n the page objects and nonshared objects for all remaining pages of the PDFF file, with the objects for each page grouped together. The pages shall be contiguous and shall be ordered by page number. If the first page of the PDF file is not page 0, this sec tion shall start with page 0 and shall skip over the first page when its position in the sequence is reached.

For each page, the objects required to display that page shall be grouped together, except for resources
| and other objects that are shared with ot | her pages. Shared objects shall be located in the shared |
| --- | --- |
| objects section (part 8). The starting | PDF file offset and length of any page can be determined from the |

hint tables.

The recommended order of objects within a page is essentially the same as in the first page. In particular, the page object shall be the first object in each section.

In most cases, unlike for the first page, little benefit is gained from interleaving contents with resources
| because most resources other than images | — fonts in particula | r — are shared among multiple pages |
| --- | --- | --- |
| and therefore reside in the shared objects section. | Image XObjects usually are not shared, but they |  |
| should appear at the end of the page’s section of the | PDF file, since rendering of images is deferred. |  |

### F.3.9 Shared objects (P art 8)

Part 8 of the PDF file contains objects, primarily named resources, that are referenced from more than one page but that are not referenced (directly or indirectly) from the first page. The hint tables contain
| an index of these objects. For more information on named resourc | es, see 7.8.3, "Resource dictionaries | ". |
| --- | --- | --- |
| The order of these objects | can be arbitrary. However, wherever a resource consists of a multiple | -level |
| structure, all components of the structure shall be grouped together. If only the top | -level object is |  |
| referenced from outside the group, the entire group may be described by a single ent | ry in the shared |  |

object hint table. This helps to minimise the size of the shared object hint table and the number of individual references from entries in the page offset hint table.

### F.3.10 Other objects (Part 9)

Following the shared objects are any other objects that are part of the document but are not required for displaying pages. These objects are divided into functional categories. Objects within each of these categories should be grouped together; the relative order of the categories is unimportant.

• The page tree. This object can be located in this section because the PDF processor never needs to consult it. Note that all Resources attributes and other inheritable attributes of the page objects shall be pushed down and replicated in each of the leaf page objects (but they may contain indirect references to shared objects).
• Thumbnail images. These objects shall simply be ordered by page number. (The thumbnail image


ISO 32000-2:2020

for page 0 shall be first, even if the first page of the document is some page other than 0.) Each thumbnail image consists of one or more objects, which may refer to objects in the thumbnail shared objects section (see the next item).
• Thumbnail shared objects. These are objects that shall be shared among some or all thumbnail images and shall not be referenced from any other objects.
• The outline hierarchy, if not located in part 6. The order of objects shall be the same as the order in which they shall be displayed by the PDF processor. This is a preorder traversal of the outline tree, skipping over any subtree that is closed (that is, whose parent’s Count value is negative).
Following that shall be the subtrees that were skipped over, in the order in which they would have appeared if they were all open.
• Thread information dictionaries, referenced from the I entries of thread dictionaries. Note that the thread dictionaries themselves shall be located with the document catalog dictionary and the bead dictionaries with the individual pages.
• Named destinations. These objects include the value of the Dests or Names entry in the document catalog dictionary and all the destination objects that it refers to; see G.3, "Opening at an arbitrary page".
• The document information dictionary and the objects contained within it.
• The interactive form field hierarchy. This group of objects shall not include the top-level interactive form dictionary, which is located with the document catalog dictionary.
• Other entries in the document catalog dictionary that are not referenced from any page.
• (PDF 1.3) The logical structure hierarchy.
• (PDF 1.5) The renditions name tree hierarchy.
• (PDF 1.5) Embedded file streams.
• (PDF 2.0) DPart tree and Document Part Metadata F.3.11             Main cross-reference and trailer (Part 11)

Part 11 is the cross-reference table for all objects in the PDF file except those listed in the first-page cross-reference table (part 3). As indicated earlier, this cross-reference table shall play the role of the original cross-reference table for the PDF file (before any updates are appended) and shall conform to the following rules:

• It consists of a single cross-reference subsection, beginning at object number 0.
• The first entry (for object number 0) shall be a free entry.
• The remaining entries are for in-use objects, which shall be numbered consecutively, starting at 1.
The startxref line shall give the offset of the first-page cross-reference table in the PDF file. The Prev entry of the first-page trailer shall give the offset of the main cross-reference table in the PDF file. The main trailer has no Prev entry and should not contain any entries other than Size.

In PDF 1.5 and later, cross-reference streams (see 7.5.8, "Cross-reference streams") may be used in linearized PDF files in place of traditional cross-reference tables. The logic described in this subclause, along with the appropriate syntactic changes for cross -reference streams, still applies.


## F.4 Hint tables

### F.4.1 General

The core of the linearization information shall be stored in two or more hint tables, as indicated by the attributes of the primary hint stream; see F.3.6, "Hint streams (Parts 5 and 10)". The format of the standard hint tables is described in this section.

A PDF writer may add additional hint tables for PDF processor-specific data. A generic format for such hint tables is defined; see F.4.5, "Generic hint tables" Alternatively, the format of a hint table may be private to the PDF processor; see Annex E, "Extending PDF" for further information.

Each hint table shall consist of a portion of the stream, beginning at the position in the stream indicated by the corresponding stream attribute. Additionally, a PDF writer shall include a page offset hint table, which shall be the first table in the stream and shall start at offset 0. If there is an overflow hint stream, its contents shall be appended seamlessly to the primary hint stream.

> **NOTE 1** Hint table positions are relative to the beginning of this combined stream.
In general, this byte stream shall be treated as a bit stream, high-order bit first, which shall then be subdivided into fields of arbitrary width without regard to byte boundaries. However, each hint table shall begin at a byte boundary.

> **NOTE 2** The hint tables are designed to encode the required information in a compact manner.
Interpreting the hint tables requires reading them sequentially; they are not designed for random access.
The PDF processor will be expected to read and decode the tables once and retain the information for as long as the document remains open.

> **NOTE 3** A hint table encodes the positions of various objects in the PDF file. The representation is either explicit (an offset from the beginning of the PDF file) or implicit (accumulated lengths of preceding objects).
Regardless of the representation, the resulting positions shall be interpreted as if the primary hint stream itself were not present. That is, a position greater than the hint stream offset shall have the hint stream length added to it to determine the actual offset relative to the beginning of the PDF file.

> **NOTE 4** The hint stream offset and hint stream length are the values offset1 and length1 in the H array in the linearization parameter dictionary at the beginning of the PDF file.

The reason for this rule is that the length of the primary hint stream depends on the information contained within the hint tables, which is not known until after they have been generated. Any information contained in the hint tables does not depend on knowing the primary hint stream’s length in advance.

> **NOTE** that this rule applies only to offsets given in the hint tables and not to offsets given in the cross-reference tables or linearization parameter dictionary. Also, the offset and length of the overflow hint stream, if present, does not need to be taken into account, since this object follows all other objects in the PDF file.
In linearized PDF files that use object streams (7.5.7, "Object streams"), the position specified in a hint table for a compressed object is to be interpreted as a byte range in which the object can be found, not


ISO 32000-2:2020

as a precise offset. PDF processors need to locate the object via a cross-reference stream, as it would if the hint table were not present.

### F.4.2 Page offset hint table

The page offset hint table provides information required for locating each page. Additionally, for each page except the first, it also enumerates all shared objects that the page references, directly or indirectly.

This table shall begin with a header section, described in "Table F.3 — Page offset hint table, header section", followed by one or more per-page entries, described in "Table F.4 — Page offset hint table, per-page entry".

> **NOTE** The items making up each per-page entry are not contiguous; they are broken up with items from entries for other pages.
The order of items making up the per-page entries shall be as follows:

a) Item 1 for all pages, in page order starting with t he first page
b) Item 2 for all pages, in page ord er starting with the first page
c) Item 3 for all pages, in page order starting with the first page
d) Item 4 for all shared objects in the second page, followed by item 4 for all shared objects in the third
page, and so on
e) Item 5 for all shared objects in the second page, followed by item 5 for all shared objects in the third
page, and so on
f) Item 6 for all pages, in page order starting with the first page
g) Item 7 for all pages, in page order starting with the first p age
| All the items in "Table F.3 — Page offset hint table, header section | " that specify a number of bits |
| --- | --- |
| needed, such as item 3, have values in the range 0 through 32. | Although that range requires only 6 bits, |

16-bit numbers shall be used.

Table F.3 — Page offset hint table, header section

| Item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The least number of objects in a page (including the page object itself). |
| 2 | 32 | The location of the first page’s page object. |
| 3 | 16 | The number of bits needed to represent the difference between the greatest and least number of objects in a page. |
| 4 | 32 | The least length of a page in bytes. This shall be the least length from the beginning of a page object to the last byte of the last object used by that page. |
| 5 | 16 | The number of bits needed to represent the difference between the greatest and least length of a page, in bytes. |
| 6 | 32 | The least offset of the start of any content stream, relative to the beginning of its page. |


| Item | Size (bits) | Description |
| --- | --- | --- |
| 7 | 16 | The number of bits needed to represent the difference between the greatest and least offset to the start of the content stream. |
| 8 | 32 | The least content stream length. |
| 9 | 16 | The number of bits needed to represent the difference between the greatest and least content stream length. |
| 10 | 16 | The number of bits needed to represent the greatest number of shared object references. |
| 11 | 16 | The number of bits needed to represent the numerically greatest shared object identifier used by the pages (discussed further in "Table F.4 — Page offset hint |

table, per-page entry", item 4).

12 16 The number of bits needed to represent the numerator of the fractional position for each shared object reference. For each shared object referenced from a page, there shall be an indication of where in the page’s content stream the object is first referenced. That position shall be given as the numerator of a fraction, whose denominator is specified once for the entire document (in the next item in this table). The fraction is explained in more detail in "Table F.4 — Page offset hint table, per-page entry", item 5.

13 16 The denominator of the fractional position for each shared object reference.

Table F.4 — Page offset hint table, per-page entry

Item Size (bits) Description

# 1 See "Table F.3 — Page A number that, when added to the least number of objects in a page

| offset hint table, | ("Table F.3 — Page offset hint table, header section", item 1), shall give |
| --- | --- |
| header section", item 3 | the number of objects in the page. The first object of the first page shall have an object number that is the value of the O entry in the linearization parameter dictionary at the beginning of the PDF file. The first object of the second page shall have an object number of 1. Object numbers for subsequent pages shall be determined by accumulating the number of objects in all previous pages. |

# 2 See "Table F.3 — Page A number that, when added to the least page length ("Table F.3 — Page

| offset hint table, | offset hint table, header section", item 4), shall give the length of the |
| --- | --- |
| header section", item 5 | page in bytes. The location of the first object of the first page may be determined from its object number (the O entry in the linearization parameter dictionary) and the cross-reference table entry for that object; see F.3.4, "First-page cross-reference table and trailer (Part 3)". The locations of subsequent pages shall be determined by accumulating the lengths of all previous pages. A PDF processor shall skip over the primary hint stream, wherever it is located. |

# 3 See "Table F.3 — Page The number of shared objects referenced from the page. For the first

| offset hint table, | page, this number shall be 0; the next two items start with the second |
| --- | --- |
| header section", item | page. |


ISO 32000-2:2020

Item Size (bits) Description

# 4 See "Table F.3 — Page (One item for each shared object referenced from the page) A shared

| offset hint table, | object identifier — that is, an index into the shared object hint table |
| --- | --- |
| header section", item | (described in F.4.3, "Shared object hint table"). A single entry in the |

shared object hint table may designate a group of shared objects, but only one of which shall be referenced from outside the group. That is, shared object identifiers shall not be directly related to object numbers.
This identifier combines with the numerators provided in item 5 to form a shared object reference.

# 5 See "Table F.3 — Page (One item for each shared object referenced from the page) The

| offset hint table, | numerator of the fractional position for each shared object reference, |
| --- | --- |
| header section", item | which shall be in the same order as the preceding item. The fraction |

shall indicate where in the page’s content stream the shared object is first referenced. This item shall be interpreted as the numerator of a fraction whose denominator is specified once for the entire document ("Table F.3 — Page offset hint table, header section", item 13).

> **EXAMPLE** If the denominator is d, a numerator ranging from 0 to d - 1 indicates the corresponding portion of the page’s content stream. For example, if the denominator is 4, a numerator of 0, 1, 2, or 3 indicates
| that the first reference lies in the first, second, third, | or fourth quarter |
| --- | --- |
| of the content stream, respectively. | There are two (or more) other values for the numerator, which shall indicate that the shared object is not referenced from the content stream but instead is needed by |
| annotations or other objects that | are drawn after the contents. The value d shall indicate that the shared object is needed before image XObjects and other nonshared objects that are at the end of the page. A value of d + 1 or greater shall indicate that the shared object is needed after those objects. |

> **NOTE** This method of dividing the page into fractions is only approximate.
Determining the first reference to a shared object entails inspecting the unencoded content stream. The relationship between positions in the unencoded and encoded streams is not necessarily linear.

# 6 See "Table F.3 — Page A number that, when added to the least offset to the start of the co ntent

| offset hint table, | stream ("Table F.3 — Page offset hint table, header section | ", item 6), |
| --- | --- | --- |
| header section", item 7 | shall give the offset in bytes of the start of the page’s content stream (the stream object, not the stream data), relative to the beginning of the page. |  |

# 7 See "Table F.3 — Page A number that, when added to the least content stream length ( "Table

| offset hint table, | F.3 — Page offset hint table, header section | ", item 8), shall give the |
| --- | --- | --- |
| header section", item 9 | length of the page’s content stream in bytes. This | length shall include object overhead preceding and following the stream data. |

### F.4.3 Shared object hint table

The shared object hint table gives information required to locate shared objects; see F.3.9, "Shared objects (Part 8)". Shared objects may be physically located in either of two places: objects that are referenced from the first page shall be located with the first-page objects (part 6); all other shared objects shall be located in the shared objects section (part 8).

A single entry in the shared object hint table may describe a group of adjacent objects under the following condition: Only the first object in the group is referenced from outside the group; the


remaining objects in the group are referenced only from other objects in the same group. The objects in a group shall have adjacent object numbers.

The page offset hint table, interactive form hint table, and logical structure hint table shall refer to an entry in the shared object hint table by a simple index that is its sequential position in the table, counting from 0.

The shared object hint table (see "Table F.6 — Shared object hint table, shared object group entry") shall consist of a header section followed by one or more shared object group entries. There shall be two sequences of shared object group entries: the ones for objects located in the first page, followed by the ones for objects located in the shared objects section. The entries shall have the same format in both cases. Note that the items making up each shared object group entry need not be contiguous; they may be broken up with items from entries for other shared object groups. The order of items in each sequence shall be as follows:

a) Item 1 for the first group, item 1 for the second group, and so on
b) Item 2 for the first group, item 2 for the second group, and so on
c) Item 3 for the first group, item 3 for the second group, and so on
d) Item 4 for the first group, item 4 for the second group, and so on
All objects associat ed with the first page (part 6) shall have entries in the shared object hint table, regardless of whether they are actually shared. The first entry shall refer to the beginning of the first page and shall have an object count and length that shall span all the initial nonshared objects. The next entry shall refer to a group of shared objects. Subsequent entries shall span additional groups of either shared or nonshared objects consecutively until all shared objects in the first page have been enumerated. (There shall not be any entries that refer to nonshared objects.)

Table F.5 — Shared object hint table, header section

| item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The object number of the first object in the shared objects section (part 8). |
| 2 | 32 | The location of the first object in the shared objects section. |
| 3 | 32 | The number of shared object entries for the first page (including nonshared objects, as noted above). |
| 4 | 32 | The number of shared object entries for the shared objects section, including the number of shared object entries for the first page (that is, the value of item 3). |
| 5 | 16 | The number of bits needed to represent the greatest number of objects in a shared object group. |
| 6 | 32 | The least length of a shared object group in bytes. |
| 7 | 16 | The number of bits needed to represent the difference between the greatest and least length of a shared object group, in bytes. |


ISO 32000-2:2020

Table F.6 — Shared object hint table, shared object group entry

Item Size (bits) Description

# 1 See "Table F.5 — A number that, when added to the least shared object group length

Shared object hint ("Table F.5 — Shared object hint table, header section", item 6), gives table, header section",       the length of the object group in bytes. The location of the first object
| item 7 | of the first page shall be given in the page offset hint table, header section ("Table F.3 — Page offset hint table, header section", item 4). The locations of subsequent object groups can be determined by accumulating the lengths of all previous object groups until all shared objects in the first page have been enumerated. Following that, the location of the first object in the shared objects section can be obtained from the header section of the shared object hint table ("Table F.5 — Shared object hint table, header section", item 2). |  |
| --- | --- | --- |
| 2 | 1 | A flag indicating whether the shared object signature (item 3) is present; its value shall be 1 if the signature is present and 0 if it is absent. |
| 3 | 128 | (Only if item 2 is 1) The shared object signature, a 16-byte MD5 hash that uniquely identifies the resource that the group of objects represents. |

> **NOTE** It enables the PDF processor to substitute a locally cached copy of the resource instead of reading it from the PDF file. Note that this signature is unrelated to signature fields in interactive forms, as defined in 12.7.5.5, "Signature fields".

# 4 See "Table F.5 — A number equal to 1 less than the number of objects in the group. The

Shared object hint first object of the first page shall be the one whose object number is table, header section",       given by the O entry in the linearization parameter dictionary at the item 5 beginning of the PDF file. Object numbers for subsequent entries can be determined by accumulating the number of objects in all previous entries until all shared objects in the first page have been enumerated. Following that, the first object in the shared objects section has a number that can be obtained from the header section of the shared object hint table ("Table F.5 — Shared object hint table, header section", item 1).

> **NOTE** In a document consisting of only one page, all of that page’s objects are treated as if they were shared; the shared object hint table reflects this.

### F.4.4 Thumbnail hint table

The thumbnail hint table shall consist of a header section ("Table F.7 — Thumbnail hint table, header section") followed by the thumbnails section, which shall include one or more per-page entries ("Table F.8 — Thumbnail hint table, per-page entry"), each of which describes the thumbnail image for a single page. The entries shall be in page number order starting with page 0, even if the document catalog dictionary contains an OpenAction entry that specifies opening at some page other than page 0.
Thumbnail images may exist for some pages and not for others.


Table F.7 — Thumbnail hint table, header section

| Item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The object number of the first thumbnail image (that is, the thumbnail image that is described by the first entry in the thumbnails section). |
| 2 | 32 | The location of the first thumbnail image. |
| 3 | 32 | The number of pages that have thumbnail images. |
| 4 | 16 | The number of bits needed to represent the greatest number of consecutive pages that do not have a thumbnail image. |
| 5 | 32 | The least length of a thumbnail image in bytes. |
| 6 | 16 | The number of bits needed to represent the difference between the greatest and least length of a thumbnail image. |
| 7 | 32 | The least number of objects in a thumbnail image. |
| 8 | 16 | The number of bits needed to represent the difference between the greatest and least number of objects in a thumbnail image. |
| 9 | 32 | The object number of the first object in the thumbnail shared objects section (a subsection of part 9). This section includes objects (colour spaces, for example) that shall be referenced from some or all thumbnail objects and are not referenced from any other objects. The thumbnail shared objects shall be undifferentiated; there is no indication of which shared objects shall be referenced from any given page’s thumbnail image. |
| 10 | 32 | The location of the first object in the thumbnail shared objects section. |
| 11 | 32 | The number of thumbnail shared objects. |
| 12 | 32 | The length of the thumbnail shared objects section in bytes. |

Table F.8 — Thumbnail hint table, per-page entry

Item Size (bits) Description

# 1 See "Table F.7 — (Optional) The number of preceding pages lacking a thumbnail

| Thumbnail hint table, | image. This number indicates how many pages without a |
| --- | --- |
| header section", item 4 | thumbnail image lie between the previous entry’s page and this page. |

# 2 See "Table F.7 — A number that, when added to the least number of objects in a

| Thumbnail hint table, | thumbnail image ("Table F.7 — Thumbnail hint table, header |
| --- | --- |
| header section", item 8 | section", item 7), gives the number of objects in this page’s thumbnail image. |

# 3 See "Table F.7 — A number that, when added to the least length of a thumbnail

| Thumbnail hint table, | image ("Table F.7 — Thumbnail hint table, header section", item |
| --- | --- |
| header section", item 6 | 5), gives the length of this page’s thumbnail image in bytes. |


ISO 32000-2:2020

The order of items in "Table F.8 — Thumbnail hint table, per-page entry" is as follows:

a) 1 for all pages, in p age order starting with the first page
b) 2 for all pages, in page order starting with the first page
c) 3 for all pages, in page order starting with the first page

### F.4.5 Generic hint tables

Categories of objects are associated with the document as a whole rather than with individual pages (see F.3.10, "Other objects (Part 9)"), and hints should be provided for accessing those objects efficiently. For each category of hints, there shall be a separate entry in the primary hint stream giving the starting position of the table within the stream; see F.3.6, "Hint streams (Parts 5 and 10) ".

Such hints shall be represented by a generic hint table, which describes a single group of objects that are located together in the PDF file. The entries in this table are listed in "Table F.9 — Generic hint table". This representation shall be used for the following hint tables, if needed:

• Outline hint table •    Thread information hint table •    Named destination hint table •    Information dictionary hint table •    Page label hint table Generic hint tables may be used for product -specific objects accessed by PDF processors.

> **NOTE** It is considerably more convenient for a PDF processor to use the generic hint representation than to specify custom hints.

Table F.9 — Generic hint table

| item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The object number of the first object in the group. |
| 2 | 32 | The location of the first object in the group. |
| 3 | 32 | The number of objects in the group. |
| 4 | 32 | The length of the object group in bytes. |

### F.4.6 Extended generic hint tables

An extended generic hint table shall begin with the same entries as in a generic hint table, and shall be followed by three additional entries, as shown in "Table F.10 — Extended generic hint table". This table provides hints for accessing objects that reference shared objects. As of PDF 1.5, the following hint tables, if needed, shall use the extended generic format:

• Interactive form hint table •    Logical structure hint table


• Renditions name tree hint table Embedded file streams shall not be referred to by this hint table, even if they are reachable from nodes in the renditions name tree; instead they shall use the hint table described in F.4.7, "Embedded file stream hint tables".

Table F.10 — Extended generic hint table

| Item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The object number of the first object in the group. |
| 2 | 32 | The location of the first object in the group. |
| 3 | 32 | The number of objects in the group. |
| 4 | 32 | The length of the object group in bytes. |
| 5 | 32 | The number of shared object references. |
| 6 | 16 | The number of bits needed to represent the numerically greatest shared object identifier used by the objects in the group. |
| 7 … | See "Table F.3 — Page | Starting with item 7, each of the remaining items in this table shall |
| offset hint table, header | be a shared object identifier — that is, an index into the shared |  |
| section", item 11 | object hint table (described in F.4.3, "Shared object hint table"). |  |

### F.4.7 Embedded file stream hint tables

The embedded file streams hint table allows a PDF processor to locate all byte ranges of a PDF file needed to access its embedded file streams. An embedded file stream may be grouped with other objects that it references; all objects in such a group shall have adjacent object numbers. (A group shall contain no objects at all if it contains shared object references.)

This hint table shall have a header section (see "Table F.11 — Embedded file stream hint table, header section"), which shall have general information about the embedded file stream groups. The header section shall be followed by the entries in "Table F.12 — Embedded file stream hint table, perembedded file stream group entries". Each of the items in "Table F.12 — Embedded file stream hint table, per-embedded file stream group entries" shall be repeated for each embedded file stream group (the number of groups being represented by item 3 in "Table F.11 — Embedded file stream hint table, header section"). That is, the order of items in "Table F.12 — Embedded file stream hint table, perembedded file stream group entries" shall be item 1 for the first group, item 1 for the second group, and so on; item 2 for the first group, item 2 for the second group, and so on; repeated for the 5 items.

Table F.11 — Embedded file stream hint table, header section

| Item | Size (bits) | Description |
| --- | --- | --- |
| 1 | 32 | The object number of the first object in the first embedded file stream group. |
| 2 | 32 | The location of the first object in the first embedded file stream group. |


ISO 32000-2:2020

| Item | Size (bits) | Description |
| --- | --- | --- |
| 3 | 32 | The number of embedded file stream groups referenced by this hint table. |
| 4 | 16 | The number of bits needed to represent the highest object number corresponding to an embedded file stream object. |
| 5 | 16 | The number of bits needed to represent the greatest number of objects in an embedded file stream group. |
| 6 | 16 | The number of bits needed to represent the greatest length of an embedded file stream group, in bytes. |
| 7 | 16 | The number of bits needed to represent the greatest number of shared object references in any embedded file stream group. |

Table F.12 — Embedded file stream hint table, per-embedded file stream group entries

Item Size (bits) Description

# 1 See "Table F.11 — The object number of the embedded file stream that this entry is

Embedded file stream associated with. hint table, header section", item 4

# 2 See "Table F.11 — The number of objects in this embedded file streams group. This

| Embedded file stream | item may be 0, meaning that there are only shared object |
| --- | --- |
| hint table, header | references. In this case, item 4 for this group shall be greater than |
| section", item 5 | zero and item 3 shall be zero. |

# 3 See "Table F.11 — The length of this embedded file stream group, in bytes. This item

| Embedded file stream | may be 0, which shall mean that there are only shared object |
| --- | --- |
| hint table, header | references. In this case, item 4 for this group shall be greater than |
| section", item 6 | zero and item 2 shall be zero. |

# 4 See "Table F.11 — The number of shared objects referenced by this embedded file

Embedded file stream stream group. hint table, header section", item 7

# 5 See "Table F.3 — Page A bit-packed list of shared object identifiers; that is, indices into

| offset hint table, header | the shared object hint table (see "F.4.3, "Shared object hint table"). |
| --- | --- |
| section", item 11 | Item 4 for this group shall specify how many shared object identifiers shall be associated with the group. |


