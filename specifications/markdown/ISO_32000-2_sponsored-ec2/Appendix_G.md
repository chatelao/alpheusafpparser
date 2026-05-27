Annex G
(informative)
Linearized PDF access strategies

G.1      General

This annex outlines how the PDF processor can take advantage of the structure of a Linearized PDF file
to retrieve and display it efficiently. This material may help explain the rationale for the organisation.

G.2      Opening at the first page

As described earlier, when a document is initially accessed, a request is issued to retrieve the entire
file, starting at the beginning. Consequently, Linearized PDF is organised so that all the data required to
display the first page is at the beginning of the PDF file. This includes all resources that are referenced
from the first page, regardless of whether they are also referenced from other pages.

The first page is usually but not necessarily page 0. If the document catalog contains an OpenAction
entry that specifies opening at some page other than page 0, that page is the one physically located at
the beginning of the document. Thus, opening a document at the default place (rather than a specific
destination) requires simply waiting for the first-page data to arrive; no additional transactions are
required.

In an ordinary PDF processor, opening a document requires first positioning to the end to obtain the
startxref line. Since a Linearized PDF file has the first page’s cross-reference table at the beginning,
reading the startxref line is not necessary. All that is required is to verify that the file length given in
the linearization parameter dictionary at the beginning of the PDF file matches the actual length of the
PDF file, indicating that no updates have been appended to the PDF file.

The primary hint stream is located either before or after the first-page section, which means that it is
also retrieved as part of the initial sequential read of the file. The PDF processor is expected to
interpret and retain all the information in the hint tables. The tables are reasonably compact and are
not designed to be obtained from the PDF file in random pieces.

The PDF processor can now decide whether to continue reading the remainder of the document
sequentially or to abort the initial transaction and access subsequent pages by using separate
transactions requesting byte ranges. This decision is a function of the size of the file, the data rate of
the channel, and the overhead cost of a transaction.

| G.3 | Opening at an arbitrary page |

The PDF processor may be requested to open a PDF file at an arbitrary page. The page can be specified
in one of three ways:

| • | By page number (remote go-to action, integer page specifier) |
| --- | --- |
| • | By named destination (remote go-to action, name or string page specifier) |


## Page 917

ISO 32000-2:2020

| • | By article thread (thread action) |

Additionally, an indexed search results in opening a document by page number. Handling this case
efficiently is especially important.

As indicated above, when the document is initially opened, it is retrieved sequentially starting at the
beginning. As soon as the hint tables have been received, the PDF processor has sufficient information
to request retrieval of any page of the document given its page number. Therefore, the PDF processor
can abort the initial transaction and issue a new transaction for the target page, as described in G.4,
"Going to another page of an open document".

The position of the primary hint stream (part 5 in F.3, "Linearized PDF document structure") with
respect to the first-page section (part 6 in F.3, "Linearized PDF document structure") determines how
quickly this can be done. If the primary hint stream precedes the first-page section, the initial
transaction can be aborted very quickly; however, this is at the cost of increased delay when opening
the document at the first page. On the other hand, if the primary hint stream follows the first-page
section, displaying the first page is quicker (since the hint tables are not needed for that), but opening
at an arbitrary page is delayed by the time required to receive the first page. The decision whether to
favour opening at the first page or opening at an arbitrary page is to be made at the time a PDF file is
linearized.

If an overflow hint stream exists, obtaining it requires issuing an additional transaction. For this
reason, inclusion of an overflow hint stream in Linearized PDF, although permitted, is not
recommended. The feature exists to allow the linearizer to write the PDF file with space reserved for a
primary hint stream of an estimated size and then go back and fill in the hint tables. If the estimate is
too small, the linearizer can append an overflow stream containing the remaining hint table data. Thus,
the PDF file can be written in one pass, which may be an advantage if the performance of writing PDF is
considered important.

Opening at a named destination requires the PDF processor first to read the entire Dests or Names
dictionary, for which a hint is present. Using this information, it is possible to determine the page
containing the specific destination identified by the name.

Opening to an article requires the PDF processor first to read the entire Threads array, which is located
with the document catalog at the beginning of the document. Using this information, it is possible to
determine the page containing the first bead of any thread. Opening at other than the first bead of a
thread requires chaining through all the beads until the desired one is reached; there are no hints to
accelerate this.

| G.4 | Going to another page of an open document |

Given a page number and the information in the hint tables, it is now straightforward for the PDF
processor to construct a single request to retrieve any arbitrary page of the document. The request
should include the following items:

| • | The objects of the page itself, whose byte range can be determined from the entry in the page |

offset hint table.


## Page 918

| • | The portion of the main cross-reference table referring to those objects. This can be computed |

from main cross-reference table location (the T entry in the linearization parameter dictionary)
and the cumulative object number in the page offset hint table.
| • | The shared objects referenced from the page, whose byte ranges can be determined from |

information in the shared object hint table.
| • | The portion or portions of the main cross-reference table referring to those objects, as described |

above.
The purpose of the fractions in the page offset hint table is to enable the PDF processor to schedule
retrieval of the page in a way that allows incremental display of the data as it arrives. It accomplishes
this by constructing a request that interleaves pieces of the page contents with the shared resources
that the contents refer to. This serves much the same purpose as the physical interleaving that is done
for the first page.

| G.5 | Drawing a page incrementally |

The ordering of objects in pages and the organisation of the hint tables are intended to allow
progressive update of the display and early opportunities for user interaction when the data is arriving
slowly. The PDF processor should recognise instances in which the targets of indirect object references
have not yet arrived and, where possible, rearrange the order in which it acts on the objects in the
page. The following sequence of actions is recommended:

| a) | Activate the annotations, but do not draw them yet. Also activate the cursor feedback for any article |
| --- | --- |
| threads in | the page. |
| b) | Begin drawing the contents. Whenever there is a reference to an image XObject that has not yet arrived, |

skip over it. Whenever there is a reference to a font whose definition is an embedded font file that has
| not yet arrived, draw the text using | a substitute font (if that is possible). |
| --- | --- |
| c) | Draw the annotations. |
| d) | Draw the images as they arrive, together with anything that overlaps them. |
| e) | Once the embedded font definitions have arrived, redraw the text using the correct fonts, together with |

anything t  hat overlaps the text.
| The last two steps should be done using an off | -screen buffer, if possible, to avoid objectionable flashing |

during the redraw process.

| On encountering a reference XObject ( | see 8.10.4, "Reference XObjects | "), the PDF processor may choose |
| --- | --- | --- |
| to initially display the object as a proxy and defer the retrieval and | rendering of the imported content. |  |

Note that, since all XObjects in a Linearized PDF file follow the content stream of the page on which
they appear, their retrieval is already deferred; the use of a reference XObject results in an additional
level of deferral.

| G.6 | Following an article thread |

As indicated earlier, the bead dictionaries for any article thread that visits a given page are located with
that page. This enables the bead rectangles to be activated and proper cursor feedback to be shown.


## Page 919

ISO 32000-2:2020

If the user follows a thread, the PDF processor can obtain the object number from the N or P entry of
the bead dictionary. This identifies a target bead, which is located with the page to which it belongs.
Given this object number, the interactive PDF processor can go to that page, as discussed in G.4, "Going
to another page of an open document"

G.7      Accessing an updated file

As stated earlier, if a Linearized PDF file subsequently has an incremental update appended to it, the
linearization and hints might be invalid. The PDF processor will have to do some additional work to
validate the information.

When the PDF processor sees that the PDF file is longer than the length given in the linearization
parameter dictionary, it could issue an additional transaction to read everything that was appended
and analyse the objects in that update to see whether any of them modify objects that are in the first
page or that are the targets of hints. If so, augmenting the PDF file’s internal data structures as
necessary to take the updates into account is strongly recommended.

| For a PDF file that has received only a small update, this approach m | ay be worthwhile. Accessing the |

PDF file this way is quicker than accessing it without hints or retrieving the entire file before displaying
any of it.


## Page 920

