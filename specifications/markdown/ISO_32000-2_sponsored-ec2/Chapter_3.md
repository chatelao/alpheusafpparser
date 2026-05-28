# 3 Terms and definitions

For the purposes of this document, the following terms and definitions apply.

ISO and IEC maintain terminological databases for use in standardization at the following addresses:

• ISO Online browsing platform: available at http://www.iso.org/obp •    IEC Electropedia: available at http://www.electropedia.org/

3.1 approval signature one or more digital signatures applied to a document, each one allowing the detection of changes to the document and which confirms a signer of the document

3.2 array object one-dimensional collection of objects (3.44) arranged sequentially and implicitly numbered starting at

3.3 ASCII American Standard Code for Information Interchange widely used convention for encoding a specific set of 128 characters as binary numbers defined in INCITS 4-1986 (R2017)

3.4 ASN.1 means of reference to ISO/IEC 8824-1

3.5 binary data sequence of bytes

3.6 boolean objects either the keyword true or the keyword false

3.7 byte group of 8 binary digits (an 8-bit value) which collectively can be configured to represent one of 256 different values

3.8 CAdES means of reference to ETSI EN 319 122-1 V1.1.1 and ETSI EN 319 122-2 V1.1.1

3.9 certification signature one and only one digital signature applied to a document which allows the detection of changes to the


document and which confirms the origin or the author of the document and that is able to allow or to block specified actions to be performed when using conformant signature handlers (3.61) or PDF processors (3.49)

3.10 character numeric code representing an abstract symbol according to some defined character encoding rule

> **NOTE 1** to entry : There are three manifestations of characters in PDF, depending on context:

• A PDF file is represented as a sequence of 8-bit bytes (3.7), some of which are interpreted as character codes in the ASCII (3.3) character set and some of which are treated as arbitrary binary data (3.5) depending upon the context.
• The contents (data) of a string or stream object (3.63) in some contexts are interpreted as character codes in the PDFDocEncoding, UTF-8 or UTF-16BE character set.
• The contents of a string within a PDF content stream (3.12) in some situations are interpreted as character codes that select glyphs (3.29) to be drawn on the page according to a character encoding that is associated with the text font.

3.11 character set defined set of characters (3.10) each assigned a unique character value

3.12 content stream stream object whose data consists of a sequence of instructions describing the graphical elements to be painted on a page

3.13 cross reference table data structure that contains the byte (3.7) offset start for each of the indirect objects (3.33) within the file

3.14 developer entity, including individuals, companies, non-profits, standards bodies, open source groups, etc., developing standards or software to use and extend this document

3.15 deprecated a part of ISO 32000 that should not be written into a PDF 2.0 document, and should be ignored by a PDF processor (3.49)

> **NOTE 1** to entry: In some cases variations on these restrictions on continued use of a deprecated feature are explicitly stated in this document.

> **NOTE 2** to entry: Implementers are cautioned that some features that are deprecated in this document could have tighter constraints placed on them, or even be removed completely, in a later version of ISO 32000, or in subset standards such as PDF/X (ISO 15930), PDF/A (ISO 19005), PDF/E (ISO 24517),


PDF/VT (ISO 16612-2 and ISO 16612-3) and PDF/UA (ISO 14289).

3.16 dictionary object associative table containing pairs of objects, the first object being a name object (3.39) serving as the key and the second object serving as the value and may be any kind of object including another dictionary

3.17 direct object object that has not been made into an indirect object (3.33)

3.18 document part set of related pages and/or related sets of pages

> **EXAMPLE** The chapter pages of a book or all sets of pages intended for a recipient.

3.19 document part hierarchy hierarchical data structure that specifies the organisation of document parts (3.18)

3.20 document part metadata metadata associated with a document part (3.18)

3.21 DSA Digital Signature Algorithm, and means of reference to FIPS PUB 186-4

3.22 electronic document electronic representation of a page-oriented aggregation of text, image and graphic data, and metadata useful to identify, understand and render that data, that can be reproduced on paper or displayed without significant loss of its information content

3.23 end-of-line marker EOL marker one or two character (3.10) sequence marking the end of a line of text, consisting of a CARRIAGE RETURN character (0Dh) or a LINE FEED character (0Ah) or a CARRIAGE RETURN followed immediately by a LINE FEED

3.24 FDF file file conforming to the Forms Data Format containing form data or annotations that may be imported into a PDF file

> **NOTE 1** to entry: See 12.7.8, "Forms data format".


3.25 filter optional part of the specification of a stream object (3.63), indicating how the data in the stream should be decoded before it is used

3.26 font identified collection of graphics that may be glyphs (3.29) or other graphical elements

3.27 font program software program written in a special-purpose language, such as the Type 1, TrueType, or OpenType font (3.26) format, that is understood by a specialized font interpreter

3.28 function special type of object that represents parameterised classes, including mathematical formulas and sampled representations with arbitrary resolution

3.29 glyph recognizable abstract graphic symbol that is independent of any specific design

[SOURCE: ISO/IEC 9541-1:2012, 3.12]

3.30 graphics state top of a push down stack of the graphics control parameters that defines the current global framework within which the graphics operators execute

3.31 ICC profile ISO 15076-1:2010 or any of the ICC.1 specifications

3.32 ICC specification cross-platform profile format for the creation and interpretation of colour data and means of reference to ISO 15076-1:2010 or any of the ICC.1 specifications

3.33 indirect object object (3.44) that is labelled with a positive integer object (3.34) number followed by a non-negative integer generation number followed by keyword obj and having keyword endobj after it

3.34 integer object mathematical integers with an implementation specified interval centred at 0 and written as one or more decimal digits optionally preceded by a sign


3.35 interactive PDF processor special case of a PDF processor (3.49) that responds to interactive user controls defined in this document

3.36 JPEG common image compression data format and means of reference to ISO/IEC 10918

3.37 job definition information that specifies the production requirements and workflow of a unit of work involving purposing PDF content to one or more messaging channels

3.38 job ticket electronic specification of process control for print production in either a published or proprietary format

3.39 name object atomic symbol uniquely defined by a sequence of characters (3.10) introduced by a SOLIDUS (/) (2Fh), but the SOLIDUS is not considered to be part of the name

3.40 name tree similar to a dictionary that associates keys and values but the keys in a name tree are strings and are ordered

3.41 null object single object (3.44) of type null, denoted by the keyword null, and having a type and value that are unequal to those of any other object

3.42 number tree similar to a dictionary that associates keys and values but the keys in a number tree are integers and are ordered

3.43 numeric object either an integer object (3.34) or a real object (3.56)

3.44 object basic data structure from which PDF files are constructed and includes these 9 types: array, boolean, dictionary, integer, name, null, real, stream and string

3.45 object reference


object (3.44) value used to allow one object to refer to another; that has the form "<n> <m> R" where <n> is an indirect object (3.33) number, <m> is its generation number and R is the uppercase letter R

3.46 object stream stream that contains a sequence of PDF objects (3.44), except those of Type stream

3.47 PAdES means of reference to ETSI EN 319 142-1 V1.1.1 and ETSI EN 319 142-2 V1.1.1

3.48 PDF imaging model derived from the PostScript page description language and enables the description of high quality text and graphics in a device-independent and resolution-independent manner

3.49 PDF processor software, hardware or any other active agent that writes, reads, updates or otherwise processes a PDF file which conforms to this document and does so in a manner that conforms to this document

3.50 PDF file contiguous stream of binary data (3.5) that conforms to this document

3.51 PDF reader PDF processor (3.49) that reads a PDF file

3.52 PDF writer PDF processor (3.49) that writes a PDF file

3.53 PKCS #7 means of reference to Internet RFC 2315

3.54 PRC means of reference to ISO 14739-1

3.55 RDF means of reference to RDFa Core 1.1

3.56 real object approximate mathematical real numbers, but with limited range and precision and written as one or more decimal digits with an optional sign and a leading, trailing, or embedded PERIOD (2Eh) (decimal point)


3.57 rectangle specific array object (3.2) used to describe locations on a page and bounding boxes for a variety of objects (3.44) and written as an array of four numbers giving the coordinates of a pair of diagonally opposite corners, typically in the form [llx lly urx ury] specifying the lower-left x, lower-left y, upper-right x, and upper-right y coordinates of the rectangle, in that order

3.58 resource dictionary associates resource names, used in content streams (3.12), with the resource objects (3.44) themselves and is organised into various categories (e.g., Font, ColorSpace, Pattern)

3.59 running text body of text, as distinct from headings, footnotes, diagrams, callouts and other non-textual material

3.60 SHA Secure Hash Algorithms, and means of reference to FIPS 180-4

3.61 signature handler software module that implements a specific form of signing and/or augmentation and/or verification of digital signatures

3.62 sRGB means of reference to IEC 61966-2-1 ed1.0 (1999-10) with Amendment 1 IEC 61966-2-1-am1 ed1.0 (2003-01)

3.63 stream object dictionary followed by zero or more bytes (3.7) bracketed between the keywords stream and endstream

3.64 string object series of bytes (3.7) (unsigned integer values in the range 0 to 255) in which the bytes are not integer objects (3.34), but are stored in a more compact form

3.65 TIFF means of reference to Adobe TIFF Revision 6.0

3.66 UCS means of reference to ISO/IEC 10646

3.67 U3D


means of reference to ECMA-363, Universal 3D File Format, 3rd Edition (U3D)

3.68 white-space character characters (3.10) that separate PDF syntactic constructs such as names and numbers from each other; white-space characters are HORIZONTAL TABULATION (09h), LINE FEED (0Ah), FORM FEED (0Ch), CARRIAGE RETURN (0Dh), SPACE (20h), NULL (00h); (see "Table 1 — White-space characters" in 7.2.3, "Character set")

3.69 XFDF means of reference to ISO 19444-1:2019, Document management – XML Forms Data Format – Part 1: Use of ISO 32000-2 (XFDF 3.0)

3.70 XMP means of reference to ISO 16684-1

> **NOTE 1** to entry: In addition to ISO 16684-1, Adobe Systems Inc. also publish Adobe XMP: Extensible Metadata Platform, Part 2: Additional Properties and Adobe XMP: Extensible Metadata Platform, Part 3: Storage in Files.

3.71 XMP packet structured wrapper for serialised XML metadata and means of reference to ISO 16684-1 that can be embedded in a wide variety of file formats

> **NOTE 1** to entry: In addition to ISO 16684-1, Adobe Systems Inc. also publish Adobe XMP: Extensible Metadata Platform, Part 2: Additional Properties and Adobe XMP: Extensible Metadata Platform, Part 3: Storage in Files.
