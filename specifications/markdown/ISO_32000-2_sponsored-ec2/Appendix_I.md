Annex I (normative) PDF versions and compatibility

## I.1 General

> **NOTE** This annex has been updated (2020).
The goal of PDF is to enable people to exchange and view electronic documents easily and reliably.
Ideally, this means that any PDF processor should be able to render the contents of any PDF file, even if the PDF file was created long before or long after the PDF processor was developed. In reality, new versions of PDF are occasionally introduced to provide additional capabilities not present before.
Furthermore, PDF processors may support optional features and/or private extensions to PDF (see Annex E "Extending PDF"), making some PDF processors more capable than others, depending on what features and extensions are present.

| PDF has been designed to enable users to view everything in the | document that the PDF processor |

understands and to enable the PDF processor to ignore or inform the user about objects not understood. The decision whether to ignore or inform the user is made on a feature-by-feature basis, at the discretion of the PDF processor.

## I.2 PDF version numbers

The PDF version number identifies a specific version of the PDF specification. A PDF file is labelled with the version number from the PDF standard that describes the set of functionality used by the PDF file.

PDF version numbers take the form M.m, where M is the major and m the minor version number, each represented as a decimal integer. The version number for a subsequent version of the PDF specification was formed by incrementing m.

ISO 32000-1 contains information about PDF 1.0 through PDF 1.7. The document contains the information about PDF 2.0.

The header in the first line of a PDF file specifies a PDF version (see 7.5.2, "File header"). Starting with PDF 1.4, a PDF version can also be specified in the Version entry of the document catalog (see 7.7.2, "Document catalog dictionary"), essentially updating the version associated with the PDF file by overriding the one specified in the PDF file header (see 7.7.2, "Document catalog dictionary"). As described in the following paragraphs, the PDF processor’s behaviour upon opening or saving a document depends on comparing the PDF file's version with the PDF version that the PDF processor supports.

# A PDF processor shall attempt to read any PDF file, even if the PDF file’s version is more recent than

that for which the PDF processor was created.


## Page 952

If a PDF processor opens a PDF file with a version number newer than the version that it supports or it identifies document requirements (12.11, "Document requirements") that it is not prepared to process, it should warn the user that it is unlikely to be able to read the document successfully and that the user may not be able to change or save the document. Upon the first error that is caused by encountering an unrecognised feature, the PDF processor should notify the user that an error has occurred but that no further errors will be reported. (Some errors should nevertheless be always reported, including file I/O errors, out-of-memory errors, and notifications that a command has failed.) Processing should continue if possible.

Whether and how the version of a PDF file should change when the document is modified and saved depends on several factors. If the PDF file has a newer version than the version which the PDF writer
| supports, the PDF writer should not alter the version | — that is, a PDF file’s version should never be |
| --- | --- |
| changed to an older version. If the PDF file has an older version than the PDF writer su | pports, the PDF |

writer may update the PDF file’s version to match the newer version if changes using newer features were made. If a document is modified by inserting the contents of another PDF file into it, the saved
| document’s version should be the most | recent of the original PDF file's version, and the inserted PDF |

file’s version.

## I.3 Feature compatibility

Historically, when a new version of PDF was defined, many features were introduced simply by adding
| new entries to existing dictionaries. Earlier versions | of PDF processors do not notice the existence of |
| --- | --- |
| such entries and behave as if they were not there. Such new features are therefore both forward | - and |

backward-compatible. Likewise, adding entries not described in the PDF specification to dictionary
| objects does not affect the PDF processor’s behaviour. | See Annex E, "Extending PDF" for information |

on how to choose key names that may not be used in future versions of PDF. See clause 7.12.3, "Developer extensions dictionary" for a discussion of how to designate the use of public extensions in PDF file.

In some cases, a new feature is impossible to ignore, because doing so would preclude some vital operation such as viewing or printing a page. For instance, if a page’s content stream is encoded with some new type of filter, there is no way for an earlier version of PDF processor to view or print the page, even though the content stream (if decoded) would be perfectly understood by that PDF processor. There is little choice but to give an error in cases like these. Such new features are forwardcompatible but not backward-compatible.

In a few cases, new features are defined in a way that earlier versions of PDF processors will ignore, but the output will be degraded in some way without any error indication. If a PDF file undergoes editing by a PDF writer that supports an earlier version of PDF that does not define some of the features that the PDF file uses, the occurrences of those features may or may not survive.


## Page 953

ISO 32000-2:2020
