Annex C (informative) Advice on maximising portability

## C.1 General

In general, this PDF standard does not restrict the size or quantity of things described in the PDF file format, such as numbers, arrays, images, and so on. However, a particular PDF processor running on a particular device and in a particular operating environment will always have practical limits. When a PDF processor encounters a PDF construct that exceeds one of these internal limits or performs a computation whose intermediate results exceeds a limit, an error occurs. To avoid this situation, and to ensure portability of PDF documents across a wide range of devices, this annex provides guidance to both PDF writers and PDF processors.

This annex highlights some aspects of this PDF standard which can cause portability issues across PDF processors and devices, however this annex is not intended as a detailed implementation guide. Issues can be classified into three main classes:

| Architectural limits | Memory limits | File limits |
| --- | --- | --- |
| The hardware on which a PDF | The amount of memory available | PDF files may be very large in size |
| processor executes imposes | to a PDF processor limits the | and may exceed the capacity of |
| certain constraints. For example, | number of memory-consuming | some PDF processors. |
| an integer is usually represented | objects that can be held | Transmission of PDF documents |
| in 32 bits, limiting the range of | simultaneously. | across devices may also cause |
| allowed integers. In addition, the | corruption so PDF writers are |  |
| design of the software may impose | encouraged to follow some simple |  |
| other constraints, such as a limit to | formatting guidelines when |  |
| the number of elements in an array | generating PDF output. |  |

or string.

Because of these limits and specific PDF processor behaviours, implementers are encouraged not to rely on just one specific PDF processor to determine the validity and suitability of their PDF output.

> **NOTE** Some PDF subset standards mandate specific limits.

## C.2 Architectural limits

"Table C.1 — Architectural considerations" discusses some architectural limits that may be reasonably accommodated by most PDF processors running across a wide variety of devices. By appreciating these limits, PDF writers can ensure the PDF files they produce have the greatest chance of successful portability across a wide variety of PDF processors.

> **NOTE** Memory limits are often exceeded before architectural limits are reached.


Table C.1 — Architectural considerations

| Quantity | Description |
| --- | --- |
| Integers | Integer values (such as object numbers) can often be expressed within 32 bits. |
| Real numbers | Modern computers often represent and process real numbers using IEEE Standard for Floating-Point Arithmetic (IEEE 754) single or double precision. |
| Size of arrays | Although a PDF array object (7.3.6, "Array objects") can theoretically contain any number of elements, PDF processors are likely to have some limitations. |
| Size of dictionaries | Although a PDF dictionary object (7.3.7, "Dictionary objects") can theoretically contain any number of key/value pairs, PDF processors are likely to have some limitations. |
| Number of spot colours | In previous versions of the PDF, it was recommended that the maximum number of colourants or tint components in a single DeviceN colour space (8.6.6.5, "DeviceN colour spaces") be limited to 32. |
| Nested objects | As described in this PDF standard, many constructs can be nested including stitching functions, q/Q operators, XObjects, article threads, etc. However PDF processors may implement recursive algorithms which may cause issues for excessively nested constructs. |

> **NOTE** In previous versions of PDF, a maximum depth of graphics state nesting by q and Q operators was 28. This arose from the fact that q and Q can be implemented by the PostScript language gsave and grestore operators when generating PostScript language output.

| Name objects | In previous versions of PDF, it was recommended that the maximum length of the internal representation of a name object was limited 127 bytes. |
| --- | --- |
| Length of a string object in | In previous versions of PDF, it was recommended that the maximum |
| a content stream | length of a string in a content stream was limited to 32,767. This restriction applied only to strings in content streams. There were no effective restrictions on other strings in PDF files. |

# CID In previous versions of PDF, it was recommended that the maximum

value of a CID (character identifier) was limited to 65,535.

## C.3 Memory limits

Memory limits cannot be characterised as precisely as architectural limits because the amount of available memory and the ways in which it is allocated vary from one PDF processor to another.
Available memory is often much less in mobile devices than desktop computers and PDF writers may be able to avoid issues by using techniques such as limiting the size of individual PDF objects, such as images, paths or array objects, or using balanced trees (as noted in 7.7.3.1, "General").


## C.4 File Limits

A PDF cross-reference table (see 7.5.4, "Cross-reference table") allocates ten digits to represent byte offsets, which limits the size of a PDF file to 1010 bytes (approximately 10 gigabytes). However crossreference streams (see 7.5.8, "Cross-reference streams") allow PDF files to be even larger.

PDF writers creating large PDF documents need to realise that not all PDF processors may be able to handle large files. PDF writers can use various compression formats (see 7.4, "Filters") to help reduce file size. PDF is also a binary format intended for automatic processing and, although the use of whitespace characters makes PDF more readable to humans, unnecessary white-space can make PDF files larger than necessary.

> **NOTE** Examples shown in this document deliberately use white-space to aid understanding.
When a PDF processor reads a PDF file with a damaged or missing cross-reference table, it may attempt to rebuild the table by scanning all the objects in the file. However, the generation numbers of deleted entries are lost if the cross-reference table is missing or severely damaged. To help facilitate such reconstruction, object identifiers, the endobj keyword, and the endstream keyword can be placed at the start of a line. Also avoid data within streams beginning a line with the keyword endstream (aside from the required endstream that delimits the end of the stream).


