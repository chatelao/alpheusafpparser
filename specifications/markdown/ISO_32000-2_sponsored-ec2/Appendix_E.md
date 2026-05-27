Annex E (normative) Extending PDF

## E.1 General

> **NOTE** This entire annex was rewritten in this document (2020).
The syntax of PDF is defined to be extensible in a variety of ways. This annex describes various types of extensions to PDF and steps that developers shall take to utilize them. "Developers" means any entity including individuals, companies, non-profits, standards bodies, open source groups, etc., who are developing standards or software to use and extend ISO 32000.

Developer-specific data may be added to PDF documents to enable PDF processors to change behaviour based on such data. At the same time, users have certain expectations when opening a PDF document, no matter which PDF processor is being used. PDF enforces certain restrictions on developer-specific data in order to meet these expectations. Developers shall only add data to PDF files when such additions conform to this annex.

There are many ways to extend PDF, starting with the most general ability to add data as additional keys to any dictionary object (7.3.7 "Dictionary objects") except the PDF file trailer dictionary (see 7.5.5 "File trailer"). A PDF developer may also use extension mechanisms to define new types of actions (12.6, "Actions"), annotations (12.5, "Annotations"), structure elements (14.7, "Logical structure”), security handlers (7.6, "Encryption") and signature handlers (12.8, "Digital signatures"). In addition, a PDF developer may create custom tags that indicate the role of marked-content operators, as described in 14.6, "Marked content".

In order for PDF processors to understand that they are encountering developer-specific data, a developer shall determine what type of name they are using for their extensions (see E.2 "Classes of PDF names"). If a user opens a PDF document using a PDF processor that does not support the developer-specific features, the PDF processor shall behave as described in Annex I, "PDF versions and compatibility".

## E.2 Classes of PDF names

In the context of extending PDF, the term names is used to refer to the fact that a Name object is used to define the extension (either as the key or the value of a dictionary entry).

PDF has three classes of names:

• First class. Names and data formats that are of value to a wide range of developers. All names defined in ISO 32000 specifications are first-class names (that is, they are not preceded by a second-class name prefix). First-class names and data formats are fully defined in official ISO publications and are available for all developers to use. Written proposals for future first class names are welcomed by ISO TC 171 SC 2 WG 8.
• Second class. Names that are applicable to a specific developer. All names that begin with 4 characters including or followed by a LOW LINE (5fh) or COLON (3Ah) in either the key or value


ISO 32000-2:2020

of a dictionary entry are second-class names, except keys added to a document information dictionary (see 14.3.3, "Document information dictionary") or a thread information dictionary (in the I entry of a thread dictionary; see 12.4.3, "Articles").  Those four-byte prefixes are developerspecific name prefixes that are managed in a public list at https://github.com/adobe/pdf-nameslist, to which new prefixes can be added NOTE 1       The above paragraph was reworded to more accurately describe previous usage of second-class names (2020).

> **NOTE 2** Developer-specific prefixes listed with Adobe prior to the ISO adoption of PDF (2008) are not listed in the PDF Names list for legal reasons. Developers can update their prefixes to appear in the public PDF Names list.
Developers should also add developer extensions dictionaries (see 7.12.3, "Developer extensions dictionary") to designate the use of developer-specific extensions in a PDF file and facilitate interoperability.
Developers may document their extensions and make that documentation publicly available so that other developers may support the extension in their products. It is strongly recommended that such documentation follow the style of ISO 32000 and give a complete specification of the intended function of the extension. A reference to this developer documentation should be supplied to the PDF Names list as part of the listing of the extension, as well as being the value of the URL entry of the developer extension dictionary (see "Table 49 — Entries in a developer extensions dictionary").
Developers should not use names in a way that might cause confusion with identical names that are defined in this or other ISO documents. It is also the responsibility of the developer not to use the same name in conflicting ways.
All references in the document object hierarchy which refer to developer-specific extensions shall use second-class names. If the key in a dictionary entry uses a second-class name, the value of that entry (or any other dictionary entries that are part of the value) are not required to use the second-class name.
EXAMPLE << /pdfa_NewFeature << % because the key uses a second-class name, the keys in this dictionary need not do so /Feature1 true /Feature2 false >> >> •    Third class. Names that may be used only in PDF files that are part of an internal process between writer and processor in order to avoid conflicts with third-class names defined by others. Thirdclass names shall all begin with a specific prefix reserved for private extensions. This prefix, which is XX, shall be used as the first characters in the names of all private data added by the developer.
It is not necessary to register third-class names in the PDF Names list.

EXAMPLE

<< /XXPrivateExtensionA true >>


