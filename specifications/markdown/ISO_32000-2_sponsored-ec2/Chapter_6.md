# 6 Conformance

## 6.1 General

This clause describes conformance with this document.

## 6.2 Conforming PDF documents

Conforming PDF files shall adhere to all requirements of this document. A conforming PDF file is not obligated to contain any feature other than those explicitly required by this document.

> **NOTE** The proper mechanism by which a file can identify itself as being a PDF file of a given version level is described in 7.5.2, "File header".

## 6.3 PDF processors

### 6.3.1 General

A PDF processor is any software, hardware or any other active agent that writes, reads, updates or otherwise processes a PDF file which conforms to this document, and does so in a manner that conforms to this document. Two specialised classifications of PDF processors are PDF writers, which create PDF documents, and PDF readers, which interpret PDF documents. The purpose of a PDF reader can be to display or print a document, or to extract data from a document. An interactive PDF processor interacts with a user while processing the PDF file and as a consequence may read or write PDF files. In this document, when it is important to distinguish what is written into a PDF file, the more specialised term PDF writer will be used, when it is important to distinguish the act of reading from the contents of a PDF file, the term PDF reader will be used and when it is important to distinguish the act of user interaction while processing a PDF file, the term interactive PDF processor will be used.
Otherwise, the more general term PDF processor will be used.

Some PDF processors may also choose to support fragment identifiers (https://www.w3.org/DesignIssues/Fragment.html) which are added at the end of a URI to provide a reference to subordinate content within the target of the URI. These fragment identifiers anchor onto specific content or characteristics of the PDF document to which the URI refers, and represent either an action to be performed on the document when it is opened, or an object within the document (see Annex O, "Fragment identifiers").

### 6.3.2 Conformance of PDF processors

#### 6.3.2.1 General

If a PDF writer creates a PDF file, the file created shall conform to this document. If a PDF writer adds or amends objects in a pre-existing PDF file then the added or amended elements shall conform to the file format requirements of this document, and shall be consistent with related existing elements.

With the exception of linearized PDF files, all PDF files should be read using the trailer and crossreference table as described in 7.5, "File structure". Linearized files should be read as specified in Annex F, "Linearized PDF" and Annex G, "Linearized PDF access strategies". Reading a non-linearized file in a serial manner is not reliable because of the way objects are to be processed after an


incremental update. A PDF processor shall respect and honour standard security (see 7.6.4, "Standard security handler") when it opens a document for processing.

Each PDF processor chooses which subsets of PDF functionality to support. For each subset that the processor chooses to support, the processor shall comply with the applicable provisions in this document.

> **NOTE** The subset standards for PDF, such as PDF/X (ISO 15930), PDF/A (ISO 19005), PDF/E (ISO 24517), PDF/VT (ISO 16612-2) and PDF/UA (ISO 14289), define the term "conforming reader" because they restrict the processing of documents conforming to those standards to strict processing rules. PDF 2.0 is highly generalized to accommodate various processing needs, so the notion of a “conforming reader” is not useful for this document.

#### 6.3.2.2 PDF processors providing rendering

When a PDF processor renders a PDF page, it shall render the page contents as defined in this document. When doing so, the PDF processor shall respect the default (or any other user-specified) optional content definitions (8.11, "Optional content"), if any. A PDF processor shall also render the appropriate appearance stream for all annotations (12.5.5, "Appearance streams") which have appearance streams designated for this purpose as indicated by the annotation flags (see 12.5.3, "Annotation flags"), unless otherwise instructed.

#### 6.3.2.3 Interactive PDF processors

An interactive PDF processor shall follow all the requirements in 6.3.2.2, "PDF processors providing rendering" for rendering. In addition, such a processor shall support all of the interactive aspects of optional content (8.11, "Optional content").
