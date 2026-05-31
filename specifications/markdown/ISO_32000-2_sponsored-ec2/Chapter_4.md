# 4 Notation

## 4.1 General

PDF operators, PDF keywords, the names of keys in PDF dictionaries, and other predefined names are written in bold font; words that denote operands of PDF operators or values of dictionary keys are written in italic font. An italic font is also used to introduce key concepts or reference specific terms of importance.

> **NOTE 1** If a word or phrase is used as both a keyword and a value, the notation used in this document will default to italic font unless doing so would cause undue confusion.

> **NOTE 2** For backwards compatibility, words denoting predefined names, operands or values and other specific terms of importance use US English spelling.

Token characters used to delimit objects and describe the structure of PDF files, as defined in 7.2, "Lexical conventions", shall be identified by their INCITS 4-1986 (R2017) (ASCII 7-bit USA codes) character name written in upper case followed by a parenthetic two digit hexadecimal character value with the suffix "h".

Ellipses (...) are used within PDF examples to indicate omitted detail. Pairs of ellipses are also used to bracket comments about such omitted detail.

Characters in text streams, as defined by 7.9.2, "String object types", are identified by their Unicode character name written in uppercase in font followed by a parenthetic four digit hexadecimal character code value with the prefix "U+" as shown in the example in this clause:

> **EXAMPLE** EN SPACE (U+2002)

## 4.2 Established notations

Due to the historical development of PDF and the requirement to remain backward compatible with existing PDF files, the notations used for keys and numbers in PDF files use United States English conventions, while the rest of the language in this document conforms to Oxford spelling in most cases.
For example, within a PDF you will encounter the key Alternate whereas Oxford spelling would use the term Alternative. Likewise PDF files use the key Color rather than the Oxford spelling Colour.
Exceptions to this are found where switching between the two spellings could cause confusion, such as with the term artifact and the word artefact. In cases such as these, this document will use the spelling of the term of art throughout the entirety of the document rather than switching between two spellings. Real numbers that contain a decimal radix separator use the PERIOD (2Eh) character and not the COMMA (2Ch) as used in Oxford spelling. Numbers do not include spaces.

Any use of the terms UCS or UCS-2 throughout this document shall be inferred as referring to ISO/IEC 10646.

Any use of the term JPEG throughout this document shall be inferred as referring to compressed image data which is in accordance with ISO/IEC 10918 (all parts and amendments). ISO/IEC 10918 is informally known as the JPEG standard, for the Joint Photographic Experts Group, the ISO group that developed the standard.

Any use of the term XFDF throughout this document shall be inferred as referring to data which is in accordance with ISO 19444-1:2019.

Any use of the term RDF throughout this document shall be inferred as referring to data which is in accordance with RDFa Core 1.1, Third Edition.

Any use of the term sRGB throughout this document shall be inferred as referring to data in accordance with IEC 61966-2-1 ed1.0 (1999-10) with Amendment 1 IEC 61966-2-1-am1 ed1.0 (2003-01).

Any use of the phrases Group 3, Group 4, or CCITT facsimile shall be inferred as referring to data in accordance with ITU Recommendation T.4: Standardization of Group 3 facsimile terminals for document transmission and ITU Recommendation T.6: Facsimile coding schemes and coding control functions for Group 4 facsimile apparatus, International Telecommunication Union (ITU).

Any use of the phrase TIFF throughout this document shall be inferred as referring to data in accordance with Adobe TIFF Revision 6.0, Final, (June 1992).

Any use of the term XMP throughout this document shall be inferred as referring to data in accordance with ISO 16684-1.

Any use of the term Adobe-Japan1 throughout this document shall be inferred as referring to data in accordance with Adobe-Japan1-7 Character Collection for CID-Keyed Fonts.

Any use of the term Adobe-GB1 throughout this document shall be inferred as referring to data in accordance with Adobe-GB1-5 Character Collection for CID-Keyed Fonts.

Any use of the term Adobe-CNS1 throughout this document shall be inferred as referring to data in accordance with Adobe-CNS1-7 Character Collection for CID-Keyed Fonts.

Any use of the term Adobe-Korea1 throughout this document shall be inferred as referring to data in accordance with Adobe-Korea1-2 Character Collection for CID-Keyed Fonts. Adobe-Korea1 was deprecated in this document (2020).

Any use of the term Adobe-Japan2 throughout this document shall be inferred as referring to data in accordance with Adobe-Japan2-0 Character Collection for CID-Keyed Fonts. Adobe-Japan2 was deprecated in this document (2020).

Any use of the term DSA throughout this document shall be inferred as referring to the digital signature algorithm defined by FIPS PUB 186-4.

Any use of the term PKCS #7 throughout this document shall be inferred as referring to cryptographic data in accordance with Internet RFC 2315.

Any use of the term SHA throughout this document shall be inferred as referring to the Secure Hash Algorithms defined in FIPS 180-4.

Any use of the term CAdES throughout this document shall be inferred as referring to data in accordance with ETSI EN 319 122-1 V1.1.1 and ETSI EN 319 122-2 V1.1.1.

Any use of the term PAdES throughout this document shall be inferred as referring to data in accordance with ETSI EN 319 142-1 V1.1.1 and ETSI EN 319 142-2 V1.1.1.
