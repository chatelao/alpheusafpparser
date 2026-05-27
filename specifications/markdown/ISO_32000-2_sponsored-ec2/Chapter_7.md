# 7 Syntax

## 7.1 General

This clause covers everything about the syntax of a PDF file at the object, file, and document level. It sets the stage for subsequent clauses, which describe how the contents of a PDF file are interpreted as page descriptions, interactive navigational aids, and application-level logical structure.

PDF file syntax is best understood by considering it as four parts, as shown in "Figure 1 — PDF file components":

• Objects. A PDF file is a data structure composed from a small set of basic types of data objects.
Subclause 7.2, "Lexical conventions" describes the character set used to write objects and other syntactic elements. Subclause 7.3, "Objects" describes the syntax and essential properties of the objects. Subclause 7.3.8, "Stream objects" provides complete details of the most complex data type, the stream object. Subclause 7.4, "Filters" introduces stream filters primarily used to make PDF files smaller by applying compression technology to stream data.
• File structure. The PDF file structure determines how objects are stored in a PDF file, how they are accessed, and how they are updated. This structure is independent of the semantics of the objects.
Subclause 7.5, "File structure" describes the file structure. Subclause 7.6, "Encryption" describes a file-level mechanism for protecting a document’s content from unauthorised access.
• Document structure. The PDF file structure specifies how the basic object types are used to represent components of a PDF file: pages, fonts, annotations, and so forth. Subclause 7.7, "Document structure" describes the overall document structure; later clauses address the detailed semantics of the components.
• Content streams. A PDF content stream contains a sequence of instructions describing the appearance of a page or other graphical entity. These instructions, while also represented as objects, are conceptually distinct from the objects that represent the document structure and are described separately. Subclause 7.8, "Content streams and resources" discusses PDF content streams and their associated resources.

Figure 1 — PDF file components In addition, this clause describes some data structures, built from basic objects, that are so widely used that they can almost be considered basic object types in their own right. These objects are covered in: 7.9, "Common data structures"; 7.10, "Functions"; and 7.11, "File specifications". Subclause 7.12, "Extensions dictionary" describes a dictionary used to identify developer-defined extensions to this document that are used in the file.


## Page 36

> **NOTE** Variants of PDF’s object and file syntax are also used as the basis for other file formats. These
include the Forms Data Format (FDF), described in 12.7.8, "Forms data format", and the Portable Job Ticket Format (PJTF), described in Adobe Technical Note #5620, Portable Job Ticket Format.
Annex H, "Example PDF file" presents several examples showing the structure of PDF files.

## 7.2 Lexical conventions

### 7.2.1 General

At the most fundamental level, a PDF file is a sequence of bytes. These bytes can be grouped into tokens according to the syntax rules described in subclauses 7.2.2, “Representation” through 7.2.4, “Comments”. One or more tokens are assembled to form higher-level syntactic entities, principally objects, which are the basic data values from which a PDF file is constructed.

# A PDF file is represented as a sequence of 8-bit bytes, some of which are interpreted as character codes

in the ASCII character set and some of which are treated as arbitrary binary data depending upon the context.

The contents (data) of a string or stream object in some contexts are interpreted as character codes in the PDFDocEncoding, UTF-8 or UTF-16 character set as described in 7.9.2, "String object types" and 7.3.8, "Stream objects" respectively.

The contents of a string within a PDF content stream in some situations are interpreted as character codes that select glyphs to be drawn on the page according to a character encoding that is associated with the text font as described in clause 9, "Text".

### 7.2.2 Representation

A non-encrypted PDF file can be entirely represented using byte values corresponding to the visible printable subset of the ASCII character set defined in INCITS 4-1986 (R2017), plus white-space characters. However, a PDF file is not restricted to the ASCII character set; it may contain arbitrary bytes, subject to the following considerations:

• The tokens that delimit objects and that describe the structure of a PDF file shall use the ASCII character set. In addition all the reserved words and the names used as keys in PDF standard dictionaries and certain types of arrays shall be defined using the ASCII character set.
• The data values of strings and streams objects may be written either entirely using the ASCII character set or entirely in binary data. In actual practice, data that is naturally binary, such as sampled images, is usually represented in binary for compactness and efficiency.
• A PDF file containing binary data shall be transported as a binary file rather than as a text file to ensure that all bytes of the file are faithfully preserved.

> **NOTE 1** A binary file is not portable to environments that impose reserved character codes, maximum
line lengths, end-of-line conventions, or other restrictions.

> **NOTE 2** In this clause, the usage of the term character is entirely indepe ndent of any logical meaning that
| the value can have when it is treated as data in specific contexts, such as representing human | - readable text or selecting a glyph from a font. |

### 7.2.3 Character set

| The PDF character set is divided into three classes referred to a | s regular, delimiter, and white-space |


## Page 37

characters. This classification enables the grouping of characters into tokens including separating syntactic constructs such as names and numbers from each other. The rules defined in this subclause apply to all characters in the file except within strings, streams, and comments.

> **NOTE 1** See 7.3.4, "String objects", 7.3.8, "Stream objects" and 7.2.4, "Comments".
PDF syntax treats any sequence of consecutive white-space characters, not inside of a string or stream, as one character. The characters that are considered white-space characters are shown in "Table 1 — White-space characters" and can be used to separate syntactic constructs such as names and numbers from each other. In all other contexts, PDF syntax treats any sequence of consecutive white-space characters as one character.

Table 1 — White-space characters

| Decimal | Hexadecimal | Octal | Name |

| 0 | 00 | 000 | Null (NUL) |

| 9 | 09 | 011 | HORIZONTAL TAB (HT) |

| 10 | 0A | 012 | LINE FEED (LF) |

| 12 | 0C | 014 | FORM FEED (FF) |

| 13 | 0D | 015 | CARRIAGE RETURN (CR) |

| 32 | 20 | 040 | SPACE (SP) |

The CARRIAGE RETURN (0Dh) and LINE FEED (0Ah) characters, also called newline characters, shall be treated as end-of-line (EOL) markers. The combination of a CARRIAGE RETURN followed immediately by a LINE FEED shall be treated as one EOL marker. EOL markers may be treated the same as any other white-space characters. However, sometimes an EOL marker is required — that is, preceding a token that shall appear at the beginning of a line.

> **NOTE 2** The examples in this document use a convention that arranges tokens into lines. However, the
examples’ use of white-space for indentation is purely for clarity of exposition and need not be included in practical use.
The delimiter characters (, ), <, >, [, ], /, and % are special (LEFT PARENTHESIS (28h), RIGHT PARENTHESIS (29h), LESS-THAN SIGN (3Ch), GREATER-THAN SIGN (3Eh), LEFT SQUARE BRACKET (5Bh), RIGHT SQUARE BRACKET (5Dh), SOLIDUS (2Fh) and PERCENT SIGN (25h), respectively). They delimit syntactic entities such as arrays, names, and comments. The delimiter characters { and } (LEFT CURLY BRACE (7Bh) and RIGHT CURLY BRACE (7Dh)) are additional delimiter characters within Type

# 4 PostScript calculator functions (see 7.10.5 "Type 4 (PostScript calculator) functions"). In addition, the

double character constructs << (3C3Ch) and >> (3E3Eh) are used to delimit dictionary objects (see 7.3.7, "Dictionary objects"). Any of these delimiters terminates the entity preceding it and is not included in the entity. Delimiter characters are allowed within the scope of a string when following the rules for composing strings; see 7.3.4.2, "Literal strings". The leading ( of a string does delimit a preceding entity and the closing ) of a string delimits the string’s end. “Table 2 — Delimiter characters” shows the delimiter characters.


## Page 38

Table 2 — Delimiter characters

| Glyph | Decimal | Hexadecimal | Octal | Name |

| ( | 40 | 28 | 050 | LEFT PARENTHESIS |

| ) | 41 | 29 | 051 | RIGHT PARENTHESIS |

| < | 60 | 3C | 074 | LESS-THAN SIGN |

| > | 62 | 3E | 076 | GREATER-THAN SIGN |

| [ | 91 | 5B | 133 | LEFT SQUARE BRACKET |

| ] | 93 | 5D | 135 | RIGHT SQUARE BRACKET |

| { | 123 | 7B | 173 | LEFT CURLY BRACKET |

| } | 125 | 7D | 175 | RIGHT CURLY BRACKET |

| / | 47 | 2F | 057 | SOLIDUS |

| % | 37 | 25 | 045 | PERCENT SIGN |

All characters except the white-space characters and delimiters are referred to as regular characters.
These characters include bytes that are outside the ASCII character set. A sequence of consecutive regular characters comprises a single token. PDF syntax is case-sensitive; corresponding uppercase and lowercase letters are distinct.

### 7.2.4 Comments

Any occurrence of the PERCENT SIGN (25h) outside a string or inside a content stream (see 7.8.2, "Content streams") introduces a comment. The comment consists of all characters after the PERCENT SIGN and up to but not including the end-of-the-line marker. A PDF processor should ignore comments.
PDF processors shall treat comments as single white-space characters for the purposes of lexical conversion (see 7.2, "Lexical conventions"). That is, a comment separates the token preceding it from the one following it.

> **EXAMPLE** The PDF syntax fragment in this example is syntactically equivalent to just the tokens abc and 123.

abc%comment (/%) blah blah blah

Comments (other than the %PDF–n.m and %%EOF comments described in 7.5, "File structure") have no semantics defined by this document. PDF writers do not need to preserve comments.

> **NOTE 1** Using comments to communicate information to PDF processors is discouraged. The preferred
and documented mechanism is to use PDF names (see Annex E, "Extending PDF").

> **NOTE 2** This clause was clarified in this document (2020).


## Page 39

## 7.3 Objects

### 7.3.1 General

PDF syntax includes nine basic types of objects: boolean values, integers, real numbers, strings, names, arrays, dictionaries, streams, and the null object.

Objects may be labelled so that they can be referred to by other objects. A labelled object is called an indirect object (see 7.3.10, "Indirect objects").

Each object type, their method of creation and their proper referencing as indirect objects is described in 7.3.2, "Boolean objects" through 7.3.10, "Indirect objects".

### 7.3.2 Boolean objects

Boolean objects represent the logical values of true and false. They appear in PDF files using the keywords true and false. See 4, "Notation", for more information regarding the notation of these keywords throughout this document.

### 7.3.3 Numeric objects

PDF syntax provides two types of basic numeric objects: integer and real. Integer objects represent mathematical integers. Real objects represent mathematical real numbers. The range and precision of numbers may be limited by the internal representations used in the computer on which the PDF processor is running; Annex C, "Advice on maximising portability", gives these limits for typical implementations.

An integer shall be written as one or more decimal digits optionally preceded by a sign. The value shall be interpreted as a signed decimal integer and shall be converted to an integer object.

> **EXAMPLE 1** Integer objects

| 123 | 43445 +17 | -98 | 0 |

A real value shall be written as one or more decimal digits with an optional sign and a leading, trailing, or embedded PERIOD (2Eh) (decimal point). The value shall be interpreted as a real number and shall be converted to a real object. A real number shall not be present when an integer is expected.
Wherever a real number is expected, an integer may be used instead. For example, it is not necessary to write the number 1.0 in real format; the integer 1 is sufficient.

> **EXAMPLE 2** Real objects

| 34.5 | -3.62 +123.6 | 4.  -.002 0 |

# A PDF writer shall not use the PostScript language syntax for numbers with non-decimal radices (such

as 16#FFFE) or in exponential format (such as 6.02E23).

Throughout this document, the term number refers to an object whose type can be either integer or real.


## Page 40

### 7.3.4 String objects

#### 7.3.4.1 General

A string object shall consist of a series of zero or more bytes. The maximum length of a string used in the computer on which the PDF processor is running may be subject to implementation limits; see Annex C, "Advice on maximising portability".

String objects shall be written in one of the following two ways:

• As a sequence of literal characters enclosed in parentheses () (using LEFT PARENTHESIS (28h) and RIGHT PARENTHESIS (29h)); see 7.3.4.2, "Literal strings" • As hexadecimal data enclosed in angle brackets <> (using LESS-THAN SIGN (3Ch) and GREATER- THAN SIGN (3Eh)); see 7.3.4.3, "Hexadecimal strings"

> **NOTE** In many contexts, conventions exist for the interpretation of the contents of a string value. This
subclause defines only the basic syntax for writing a string as a sequence of bytes; conventions or rules governing the contents of strings in particular contexts are described with the definition of those particular contexts.
Subclause 7.9.2, "String object types" describes the encoding schemes used for the contents of string objects.

#### 7.3.4.2 Literal strings

A literal string shall be written as an arbitrary number of characters enclosed in parentheses (LEFT PARENHESIS (28h) and RIGHT PARENTHESIS (29h)). Any characters may appear in a string except unbalanced parentheses and the backslash (REVERSE SOLIDUS (5Ch)), which shall be treated specially as described in this subclause. Balanced pairs of parentheses within a string require no special treatment.

> **EXAMPLE 1** The following are valid literal strings:

(This is a string) (Strings can contain newlines and such.) (Strings can contain balanced parentheses () and special characters ( * ! & } ^ %and so on) .) (The following is an empty string .) () (It has zero (0) length.)

| Within a literal string, the REVERSE SOLIDUS is used as an escape character. The cha | racter |
| --- | --- |
| immediately following the REVERSE SOLIDUS determines its precise interpretation as shown in | "Table |

3 — Escape sequences in literal strings". If the character following the REVERSE SOLIDUS is not one of those shown in "Table 3 — Escape sequences in literal strings", the REVERSE SOLIDUS shall be ignored.

Table 3 — Escape sequences in literal strings

Sequence Meaning

| \n | LINE FEED (0Ah) (LF) |


## Page 41

Sequence Meaning

| \r | CARRIAGE RETURN (0Dh) (CR) |

| \t | HORIZONTAL TAB (09h) (HT) |

| \b | BACKSPACE (08h) (BS) |

| \f | FORM FEED (0Ch) (FF) |

| \( | LEFT PARENTHESIS (28h) |

| \) | RIGHT PARENTHESIS (29h) |

| \\ | REVERSE SOLIDUS (5Ch) (Backslash) |

| \ddd | Character code ddd (octal) |

# A PDF writer may split a literal string across multiple lines. The REVERSE SOLIDUS (5Ch) (backslash

character) at the end of a line shall be used to indicate that the string continues on the following line. A PDF processor shall disregard the REVERSE SOLIDUS and the end-of-line marker following it when reading the string; the resulting string value shall be identical to that which would be read if the string were not split.

> **EXAMPLE** 2

(These \ two strings \ are the same.) (These two strings are the same.)

An end-of-line marker appearing within a literal string without a preceding REVERSE SOLIDUS shall be treated as a byte value of (0Ah), irrespective of whether the end-of-line marker was a CARRIAGE RETURN (0Dh), a LINE FEED (0Ah), or both.

> **EXAMPLE** 3

(This string has an end-of-line at the end of it.
) (So does this one.\n)

The \ddd escape sequence provides a way to represent characters outside the printable ASCII character set.

> **EXAMPLE** 4

(This string contains \245two octal characters\307.)

The number ddd may consist of one, two, or three octal digits; high-order overflow shall be ignored.
Three octal digits shall be used, with leading zeros as needed, if the next character of the string is also a digit.

> **EXAMPLE** 5

the literal


## Page 42

(\0053) denotes a string containing two characters, \005 (Control-E) followed by the digit 3, whereas both (\053) and (\53) denote strings containing the single character \053, a plus sign (+).

Since any 8-bit value may appear in a string (with proper escaping for REVERSE SOLIDUS (backslash) and unbalanced PARENTHESES) this \ddd notation provides a way to specify characters outside the ASCII character set by using ASCII characters only. However, any 8-bit value may appear in a string, represented either as itself or with the \ddd notation described.

When a document is encrypted (see 7.6, "Encryption"), all of its strings are encrypted; the encrypted string values contain arbitrary 8-bit values. When writing encrypted strings using the literal string form, the PDF writer shall follow the rules described. That is, the REVERSE SOLIDUS character shall be used as an escape to specify unbalanced PARENTHESES or the REVERSE SOLIDUS character itself. The REVERSE SOLIDUS may, but is not required, to be used to specify other, arbitrary 8-bit values.

#### 7.3.4.3 Hexadecimal strings

Strings may also be written in hexadecimal form, which is useful for including arbitrary binary data in a PDF file. A hexadecimal string shall be written as a sequence of hexadecimal digits (0–9 and A–F or a–
f) encoded as ASCII characters and enclosed within angle brackets (using LESS-THAN SIGN (3Ch) and
GREATER-THAN SIGN (3Eh)).

> **EXAMPLE** 1

<4E6F762073686D6F7A206B6120706F702E>

Each pair of hexadecimal digits defines one byte of the string. White-space characters (see “Table 1 — White-space characters”) shall be ignored. The empty string can be represented in a hexadecimal string by a LESS-THAN SIGN (3Ch) immediately followed by a GREATER-THAN SIGN (3Eh).

If the final digit of a hexadecimal string is missing — that is, if there is an odd number of digits — the final digit shall be assumed to be 0.

> **EXAMPLE** 2

<901FA3>

is a 3-byte string consisting of the characters whose hexadecimal codes are 90, 1F, and A3, but

<901FA>

is a 3-byte string containing the characters whose hexadecimal codes are 90, 1F, and A0.

### 7.3.5 Name objects

Beginning with PDF 1.2 a name object is an atomic symbol uniquely defined by a sequence of any characters (8-bit values) except null (character code 0). Uniquely defined means that any two name objects that, after all escaping is expanded (see below), and the resulting sequences of bytes are not an exact binary match denote different objects. Atomic means that a name has no internal structure; although it is defined by a sequence of characters, those characters are not considered elements of the


## Page 43

name.

Even though a name may contain any characters except null, in order to express a name following the general rules for parsing PDF files, when written into a PDF file names should be encoded as described in the succeeding paragraphs.

When writing a name in a PDF file, a SOLIDUS (2Fh) (/) shall be used to introduce a name. The SOLIDUS is not part of the name but is a prefix indicating that what follows is a sequence of characters representing the name in the PDF file and shall follow these rules:

a) A NUMBER SIGN (23h) (#) in a name shall be w ritten by using its 2 -digit hexadecimal code (23),
preceded by the NUMBER SIGN.
b) Any character in a name that is a regular character (other than NUMBER SIGN) shall be written as itself
| or by using its 2 | -digit hexadecimal code, preceded by the NUMBER SIGN. |

c) Any character that is not a regular character shall be written using its 2 -digit hexadecimal code,
preceded by the NUMBER SIGN only.

> **NOTE 1** There is not a unique encoding of names into the PDF file because regular characters can be
coded in either of two wa ys.
| No token delimiter (such as white | -space) occurs between the SOLIDUS and the encoded name. White | - |
| --- | --- | --- |
| space used as part of a name shall always be coded using the 2 | -digit hexadecimal notation | . |

| Regular characters that are outside the range EXCLAMATION MARK(21 | h) (!) to TILDE (7Eh) (~) |

should be written using the hexadecimal notation.

The token SOLIDUS (a slash followed by no regular characters) introduces a unique valid name defined by the empty sequence of characters.

> **NOTE 2** The examples shown in "Table 4 — Examples of literal names " and containing # are not valid
literal names in PDF 1.0 or 1.1.

Table 4 — Examples of literal names

| Syntax for Literal name | Resulting Name |

| /Name1 | Name1 |

| /ASomewhatLongerName | ASomewhatLongerName |

| /A;Name_With | -Various***Characters? | A;Name_With-Various***Characters? |

| /1.2 | 1.2 |

| /$$ | $$ |

| /@pattern | @pattern |

| /.notdef | .notdef |

| /Lime#20Green | Lime Green |

| /paired#28#29parentheses | paired()parentheses |

| /The_Key_of_F#23_Minor | The_Key_of_F#_Minor |


## Page 44

| Syntax for Literal name | Resulting Name |

| /A#42 | AB |

In PDF syntax, literal names shall always be introduced by the SOLIDUS character (/), unlike keywords such as stream, endstream, and obj.

> **NOTE 3** This document follows a typographic convention of writing names without the leading SOLIDUS
when they appear in running text and tables. For example, Type and FullScreen denote names
| that would actually be written in a PDF file (and in code examples in | this document) as /Type and /FullScreen. |

The maximum length of a name used in the computer on which the PDF processor is running may be subject to an implementation limit; see Annex C, "Advice on maximising portability". The limit applies to the number of characters in the name’s internal representation. For example, the name /A#20B has three characters (A, SPACE, B), not six.

As stated above, name objects shall be treated as atomic within a PDF file. Ordinarily, the bytes making up the name are never treated as text to be presented to a human user or to an application external to a PDF processor. However, occasionally the need arises to treat a name object as text, such as one that represents a font name (see the BaseFont entry in "Table 109 — Entries in a Type 1 font dictionary"), a colourant name in a Separation or DeviceN colour space, or a structure type (see 14.7.3, "Structure types").

In such situations, the sequence of bytes making up the name object should be interpreted according to UTF-8, a variable-length byte-encoded representation of Unicode in which the printable ASCII characters have the same representations as in ASCII. This enables a name object to represent text virtually in any natural language, subject to the implementation limit on the length of a name.

> **NOTE 4** PDF syntax does not prescribe what UTF-8 sequence to choose for representing any given piece
of externally specified text as a name object. In some cases, multiple UTF-8 sequences can represent the same logical text. PDF name objects are considered distinct objects if, after all escaping is expanded, the resulting sequences of bytes are not an exact binary match.

### 7.3.6 Array objects

An array object is a one-dimensional collection of objects arranged sequentially. Unlike arrays in many other computer languages, PDF arrays may be heterogeneous; that is, an array’s elements may be any
| combination of numbers, strings, dictionaries, or any other objects, including other arrays. | An indirect |

reference (see 7.3.10, "Indirect objects") shall be used to refer to a stream object, and may also be used to refer to any other kind of object. An array may have zero elements.

An array shall be written as a sequence of objects enclosed in SQUARE BRACKETS (using LEFT SQUARE BRACKET (5Bh) and RIGHT SQUARE BRACKET (5Dh)).

EXAMPLE

[549 3.14 false (Ralph) /SomeName]

PDF syntax directly supports only one-dimensional arrays. Arrays of higher dimension can be constructed by using arrays as elements of arrays, nested to any depth.


## Page 45

### 7.3.7 Dictionary objects

A dictionary object is an associative table containing pairs of objects, known as the dictionary’s entries.
The first element of each entry is the key and the second element is the value. The key shall be a name (unlike dictionary keys in the PostScript language, which may be objects of any type). All keys shall be direct objects. The value may be any kind of object, including another dictionary. An indirect reference (see 7.3.10, "Indirect objects") shall be used as the value to refer to a stream object, and may also be used to refer to any other kind of object. A dictionary entry whose value is null (see 7.3.9, "Null object") shall be treated the same as if the entry does not exist. (This differs from the PostScript language, where null behaves like any other object as the value of a dictionary entry.) The maximum number of entries in a dictionary used in the computer on which the PDF processor is running may be subject to implementation limits; see Annex C, "Advice on maximising portability". A dictionary may have zero entries.

> **NOTE 1** The statement requiring dictionary keys to always be direct objects was added in this d ocument
(2020).
The entries in a dictionary represent an associative table and as such shall be unordered even though an arbitrary order may be imposed upon them when written in a file. That ordering shall be ignored.

| Multiple entries in the same dictionar | y shall not have the same key. |

| A dictionary shall be written as a sequence of key | -value pairs enclosed in double angle brackets |
| --- | --- |
| (<<…>>) (using LESS-THAN SIGNs (3Ch) and GREATER | -THAN SIGNs (3Eh)). |

EXAMPLE

<</Type /Example /Subtype /DictionaryExample /Version 0.01 /IntegerItem 12 /StringItem (a string) /Subdictionary << /Item1 0.4 /Item2 true /LastItem (not !) /VeryLastItem (OK) >> >>

> **NOTE 2** Do not confuse the double angle brackets with single angle brackets (< and >) (using LESS-THAN
SIGN (3Ch) and GREATER-THAN SIGN (3Eh)), which delimit a hexadecimal string (see 7.3.4.3, "Hexadecimal strings").
Dictionary objects are the main building blocks of a PDF file. They are commonly used to collect and tie together the attributes of a complex object, such as a font or a page of the document, with each entry in the dictionary specifying the name and value of an attribute. By convention, the Type entry of such a dictionary, if present, identifies the type of object the dictionary describes. In some cases, a Subtype entry (sometimes abbreviated S) may be used to further identify a specialised subcategory of the general type. The value of the Type or Subtype entry shall always be a name. For example, in a font dictionary, the value of the Type entry shall always be Font, whereas that of the Subtype entry may be Type1, TrueType, or one of several other values.

The value of the Type entry can almost always be inferred from context. The value of an entry in a page's font resource dictionary, for example, shall be a font object; therefore, the Type entry in a font


## Page 46

dictionary serves primarily as documentation and as information for error checking. The Type entry shall not be required unless so stated in its description; however, if the entry is present, it shall have the correct value. In addition, the value of the Type entry in any dictionary, even in private data, shall be either a name defined in this document or a registered name; see Annex E, "Extending PDF", for details.

### 7.3.8 Stream objects

#### 7.3.8.1 General

A stream object, like a string object, is a sequence of bytes. Furthermore, a stream may be of unlimited length, whereas a string shall be subject to an implementation limit. For this reason, objects with potentially large amounts of data, such as images and page descriptions, shall be represented as streams.

> **NOTE 1** This subclause describes only the syntax for writing a stream as a sequence of bytes. The context
in which a stream is referenced determines what the sequence of bytes represents.
A stream shall consist of a dictionary followed by zero or more bytes bracketed between the keywords stream (followed by newline) and endstream:

dictionary stream …Zero or more bytes… endstream

All streams shall be indirect objects (see 7.3.10, "Indirect objects") and the stream dictionary shall be a direct object. The keyword stream that follows the stream dictionary shall be followed by an end-ofline marker consisting of either a CARRIAGE RETURN and a LINE FEED or just a LINE FEED, and not by a CARRIAGE RETURN alone. The sequence of bytes that make up a stream lie between the end-of-line marker following the stream keyword and the endstream keyword; the stream dictionary specifies the exact number of bytes. There should be an end-of-line marker after the data and before endstream; this marker shall not be included in the stream length. There shall not be any extra bytes, other than white-space, between endstream and endobj.

> **NOTE 2** Without the restriction against following the keyword stream by a CARRIAGE RETURN alone,
differentiating a stream that uses CARRIAGE RETURN as its end-of-line marker and has a LINE
| FEED as its first byte of data from one that uses a CARRIAGE RETURN | –LINE FEED sequence to denote end-of-line is not possible. |

Alternatively, beginning with PDF 1.2, the bytes may be contained in an external file, in which case the
| stream dictionary specifies the file, and any bytes between | stream and endstream shall be ignored by |

a PDF processor.

| "Table 5 — Entries common to all stream dictionaries | " lists the entries common to all stream |

dictionaries; certain types of streams may have additional dictionary entries, as indicated where those streams are described. The optional entries regarding filters for the stream indicate whether and how the data in the stream shall be transformed (decoded) before it is used. Filters are described further in 7.4, "Filters".

#### 7.3.8.2 Stream extent

Every stream dictionary shall have a Length entry that indicates how many bytes of the PDF file are


## Page 47

used for the stream’s data. (If the stream has a filter, Length shall be the number of bytes of encoded data.) In addition, most filters are defined so that the data shall be self-limiting; that is, they use an encoding scheme in which an explicit end-of-data (EOD) marker delimits the extent of the data. Finally, streams are used to represent many objects from whose attributes a length can be inferred. All of these constraints shall be consistent.

> **EXAMPLE** An image with 10 rows and 20 columns, using a single colour component and 8 bits per component, requires
exactly 200 bytes of image data. If the stream uses a filter, there needs to be enough bytes of encoded data in the PDF file to produce those 200 bytes. An error occurs if Length is too small, if an explicit EOD marker occurs too soon, or if the decoded data does not contain 200 bytes.

Streams shall also not contain too much data, with the exception that there may be an extra end-of-line marker in the PDF file before the keyword endstream.

Table 5 — Entries common to all stream dictionaries

| Key | Type | Value |

| Length | integer | (Required) The number of bytes from the beginning of the line following the keyword stream to the last byte just before the keyword endstream. (There may be an additional EOL marker, preceding endstream, that is not included in the count and is not logically part of the stream data.) See 7.3.8.2, "Stream extent", for further discussion. |

| Filter | name or | (Optional) The name, or an array of zero, one or several names, of filter(s) |
| --- | --- | --- |
| array | that shall be applied in processing the stream data found between the keywords stream and endstream. Multiple filters shall be specified in the order in which they are to be applied. |  |

> **NOTE** It is not recommended to include the same filter more than once in a Filter
array.

| DecodeParms | dictionary | (Optional) A parameter dictionary or an array of such dictionaries, used by |
| --- | --- | --- |
| or array | the filters specified by Filter, respectively. If there is only one filter and that filter has parameters, DecodeParms shall be set to the filter’s parameter dictionary unless all the filter’s parameters have their default values, in which case the DecodeParms entry may be omitted. If there are multiple filters and any of the filters has parameters set to nondefault values, DecodeParms shall be an array with one entry for each filter in the same order as the Filter array: either the parameter dictionary for that filter, or the null object if that filter has no parameters (or if all of its parameters have their default values). If none of the filters have parameters, or if all their parameters have default values, the DecodeParms entry may be omitted. |  |

| F | file | (Optional; PDF 1.2) The file containing the stream data. If this entry is |
| --- | --- | --- |
| specification | present, the bytes between stream and endstream shall be ignored. However, the Length entry should still specify the number of those bytes (usually, there are no bytes and Length is 0). The filters that are applied to the file data shall be specified by FFilter and the filter parameters shall be specified by FDecodeParms. |  |

| FFilter | name or | (Optional; PDF 1.2) The name of a filter to be applied in processing the data |
| --- | --- | --- |
| array | found in the stream’s external file, or an array of zero, one or several such names. The same rules apply as for Filter. |  |


## Page 48

| Key | Type | Value |

| FDecodeParms | dictionary | (Optional; PDF 1.2) A parameter dictionary, or an array of such dictionaries, |
| --- | --- | --- |
| or array | used by the filters specified by FFilter, respectively. The same rules apply as for DecodeParms. |  |

| DL | integer | (Optional; PDF 1.5) A non-negative integer representing the number of bytes in the decoded (defiltered) stream. This value is only a hint; for some stream filters, it may not be possible to determine this value precisely. |

### 7.3.9 Null object

The null object has a type and value that are unequal to those of any other object. There shall be only one object of type null, denoted by the keyword null. An indirect object reference (see 7.3.10, "Indirect objects") to a nonexistent object shall be treated the same as a null object. Specifying the null object as the value of a dictionary entry (7.3.7, "Dictionary objects") shall be equivalent to omitting the entry entirely.

### 7.3.10 Indirect objects

Any object in a PDF file may be labelled as an indirect object. This gives the object a unique object identifier by which other objects can refer to it (for example, as an element of an array or as the value of a dictionary entry). The object identifier shall consist of two parts:

• A positive integer object number. Indirect objects may be numbered sequentially within a PDF file, but this is not required; object numbers may be assigned in any arbitrary order.
• A non-negative integer generation number. In a newly created file, all indirect objects shall have generation numbers of 0. Non-zero generation numbers may be introduced when the file is later updated; see 7.5.4, "Cross-reference table" and 7.5.6, "Incremental updates" Together, the combination of an object number and a generation number shall uniquely identify an indirect object.

The definition of an indirect object in a PDF file shall consist of its object number and generation number (separated by white-space), followed by the value of the object bracketed between the keywords obj and endobj.

> **EXAMPLE 1** Indirect object definition

12 0 obj (Brillig) endobj

| Defines an indirect string object with an object number of 12, a generation number of 0, and the | string value Brillig. |

The object may be referred to from elsewhere in the file by an indirect reference. Such indirect references shall consist of the object number, the generation number, and the keyword R (with whitespace separating each part):

12 0 R


## Page 49

> **NOTE** (2020) Any object outside of an object stream (see 7.5.7, "Object streams") can consist solely of
an object reference. PDF syntax thus permits chains of such objects.
Beginning with PDF 1.5, indirect objects may reside in object streams (see 7.5.7, "Object streams").
They are referred to in the same way; however, their definition shall not include the keywords obj and endobj, and their generation number shall be zero.

An indirect reference to an undefined object shall not be considered an error by a PDF processor; it shall be treated as a reference to the null object.

> **EXAMPLE 2** If a file contains the indirect reference 17 0 R but does not contain the corresponding definition then the
indirect reference is considered to refer to the null object.

Except where documented to the contrary, any object value may be a direct or an indirect reference; the semantics are equivalent.

> **EXAMPLE 3** The following shows the use of an indirect object to specify the length of a stream. The value of the stream’s
Length entry is an integer object that follows the stream in the file. This allows applications that generate
| PDF files in a single pass to defer specifying the stream’s length until after its contents have been generate | d. |

7 0 obj
| <</Length 8 0 R>> | %An indirect reference to object 8 stream BT /F1 12 Tf 72 712 Td (A stream with an indirect length) Tj ET endstream endobj |

8 0 obj %The length of the preceding stream endobj

## 7.4 Filters

### 7.4.1 General

Stream filters are introduced in 7.3.8, "Stream objects". An option when reading stream data is to decode it using a filter to produce the original non-encoded data. Whether to do so and which decoding filter or filters to use are specified in the stream dictionary.

> **EXAMPLE 1** If a stream dictionary specifies the use of an ASCIIHexDecode filter, an application reading the data in that
stream will need to transform the ASCII hexadecimal-encoded data in that stream in order to obtain the original binary data.

# A PDF writer may encode data in a stream (for example, data for sampled images) to compress it or to

convert it to a portable ASCII representation (or both). A PDF reader shall invoke the corresponding decoding filter or filters to convert the information back to its original form.

The filter or filters for a stream shall be specified by the Filter entry in the stream’s dictionary (or the FFilter entry if the stream is external). Filters may be cascaded to form a pipeline that passes the stream through two or more decoding transformations in sequence. For example, data encoded using LZW and ASCII base-85 encoding (in that order) shall be decoded using the following entry in the stream dictionary:


## Page 50

> **EXAMPLE** 2:

/Filter [/ASCII85Decode /LZWDecode]

Some filters may take parameters to control how they operate. These optional parameters shall be specified by the DecodeParms entry in the stream’s dictionary (or the FDecodeParms entry if the stream is external).

PDF files support a standard set of filters that fall into two main categories:

• ASCII filters enable decoding of arbitrary binary data that has been encoded as ASCII text (see 7.2, "Lexical conventions" for an explanation of why this type of encoding might be useful).
• Decompression filters enable decoding of data that has been compressed. The compressed data shall be in binary format, even if the original data is in ASCII text.

> **NOTE 1** ASCII filters serve no useful purpose in a PDF file that is encrypted; see 7.6, "Encryption".

> **NOTE 2** Compression is particularly valuable for large sampled images, since it reduces storage
requirements and transmission time. Some types of compression are lossy, meaning that some data are lost during the encoding, resulting in a loss of quality when the data are decompressed.
Compression in which no loss of data occurs is called lossless. Though somehow obvious it is
| worth pointing out that lossy compression can only be applied to sampl | ed image data (and only certain types of lossy compression for certain types of images). Lossless compression on the other hand can be used for any kind of stream. |

The standard filters are summarised in "Table 6 — Standard filters", which also indicates whether they accept any optional parameters. The following subclauses describe these filters and their parameters
| (if any) in greater detail, including specifications o | f encoding algorithms for some filters. |

Table 6 — Standard filters

| FILTER name | Parameters | Description |

| ASCIIHexDecode | no | Decodes data encoded in an ASCII hexadecimal representation, reproducing the original binary data. |

| ASCII85Decode | no | Decodes data encoded in an ASCII base-85 representation, reproducing the original binary data. |

| LZWDecode | yes | Decompresses data encoded using the LZW (Lempel | -Ziv-Welch) adaptive compression method, reproducing the original text or binary data. |

| FlateDecode | yes | (PDF 1.2) Decompresses data encoded using the zlib/deflate compression method, reproducing the original text or binary data. |

| RunLengthDecode | no | Decompresses data encoded using a byte-oriented run-length encoding algorithm, reproducing the original text or binary data (typically monochrome image data, or any data that contains frequent long runs of a single byte value). |

| CCITTFaxDecode | yes | Decompresses data encoded using the CCITT facsimile standard, reproducing the original data (typically monochrome image data at 1 bit per pixel). |


## Page 51

| FILTER name | Parameters | Description |

| JBIG2Decode | yes | (PDF 1.4) Decompresses data encoded using the JBIG2 standard, reproducing the original monochrome (1 bit per pixel) image data (or an approximation of that data). |

| DCTDecode | yes | Decompresses data encoded using a DCT (discrete cosine transform) technique based on the JPEG standard (ISO/IEC 10918), reproducing image sample data that approximates the original data. |

| JPXDecode | no | (PDF 1.5) Decompresses data encoded using the wavelet | -based JPEG 2000 standard, reproducing the original image data. |

| Crypt | yes | (PDF 1.5) Decrypts data encrypted by a security handler, reproducing the data as it was before encryption. |

> **EXAMPLE 3** The following example shows a stream, containing the marking instructions for a page, that was compressed
using the Flate compression method and then encoded in ASCII base-85 representation. The optional entry, DecodeParms is not used in this example, resulting in each filter using its default parameter values.

1 0 obj <</Length 447 /Filter [/ASCII85Decode /FlateDecode] >> stream GhQ\j_/=ii'LfceJ%/-bPR=PCD-LeMX!Z7pmG\.BSJGfG1MH^`^(F$rh*;h>h/i!XMIl:?" o%Tk8W%<tN0"eRE==1.4N>=Cc@SiF+*s\3`snB>Y$n=^$I[!5nkk"\Wp1;WBoI"]#[bZ=Ou#c QqtW?@3^VI[CftgH<HWngT*U:T12>8lm%+Q=gI^i0+\=:@fc@rHeGG!&rPQYfZ>YdpV`eM._@ Z]46KR;P?sT-tkq- Xn4&]=EE2[h<FPBAePZJ9inNNF4R*)W-?\NH1C-GG&?=IC_>b%&++C2jo *B+a7eU&`hee<0<F%-Y]De8<t.o"7p,rtNedLjK.J`kSL@c;BDEq+;F1Y?O)>keNVh+-(1*S= CQ?fQ_I]jWNc*Qe2XR^R#;3:<$\m;6d>@C3egX,+W+VfZ]X4*3r5*tiejhQe:!'jq$^jfSIj% 13FL endstream endobj

> **EXAMPLE 4** The following shows the same stream without any filters applied to it. (The stream’s contents are explained
in 7.8.2, "Content streams" and the operators used there are further described in clause 9, "Text".)

1 0 obj <</Length 562>> stream

# 2 J

BT /F1 12 Tf

# 0 Tc

# 0 Tw

72.5 712 TD [(Unfiltered streams can be read easily) 65 (,)] TJ 0 -14 TD [(b) 20 (ut generally tak) 10 (e more space than \311)] TJ T* (compressed streams.) Tj 0 -28 TD [(Se) 25 (v) 15 (eral encoding methods are a) 20 (v) 25 (ailable in PDF) 80 (.)] TJ 0 -14 TD (Some are used for compression and others simply) Tj T* [(to represent binary data in an) 55 (ASCII format.)] TJ T* (Some of the compression filters are \ suitable) Tj T*


## Page 52

(for both data and images, while others are \ suitable only) Tj T* (for continuous-tone images.) Tj ET endstream endobj

### 7.4.2 ASCIIHexDecode filter

The ASCIIHexDecode filter decodes data that has been encoded in ASCII hexadecimal form. ASCII hexadecimal encoding and ASCII base-85 encoding (7.4.3, "ASCII85Decode filter") convert binary data, such as image data or previously compressed data, to 7-bit ASCII characters.

> **NOTE** ASCII base-85 encoding is preferred to ASCII hexadecimal encoding. Base-85 encoding is
preferred because it is more compact: it expands the data by a factor of 4:5, compared with 1:2 for ASCII hexadecimal encoding.
The ASCIIHexDecode filter shall produce one byte of binary data for each pair of ASCII hexadecimal digits (0–9 and A–F or a–f). All white-space characters (see 7.2, "Lexical conventions") shall be ignored.

# A GREATER-THAN SIGN (3Eh) indicates EOD (End Of Data). Any other characters shall cause an error.

If the filter encounters the EOD marker after reading an odd number of hexadecimal digits, it shall behave as if a 0 (zero) followed the last digit.

### 7.4.3 ASCII85Decode filter

The ASCII85Decode filter decodes data that has been encoded in ASCII base-85 encoding and produces binary data. The following paragraphs describe the process for encoding binary data in ASCII base-85; the ASCII85Decode filter reverses this process.

The ASCII base-85 encoding shall use the ASCII characters ! through u ((21h) - (75h)) and the character z (7Ah), with the 2-character sequence ~> (7Eh)(3Eh) as its EOD marker. The ASCII85Decode filter shall ignore all white-space characters (see 7.2, "Lexical conventions"). Any other characters, and any character sequences that represent impossible combinations in the ASCII base-85 encoding, shall cause an error.

Specifically, ASCII base-85 encoding shall produce 5 ASCII characters for every 4 bytes of binary data.
Each group of 4 binary input bytes, (b1 b2 b3 b4), shall be converted to a group of 5 output bytes, (𝑐1 𝑐2 𝑐3 𝑐4 𝑐5), using the relation
| (𝑏1  ×  2563)+ | (𝑏2  × 2562)+ | (𝑏3  ×  2561)+ | 𝑏4 |  |
| --- | --- | --- | --- | --- |
| =  (𝑐1  × 854)+ | (𝑐2 ×  853)+ | (𝑐3  ×  852)+ | (𝑐4  × 851)+ | 𝑐5 |

In other words, 4 bytes of binary data shall be interpreted as a base-256 number and then shall be converted to a base-85 number. The five bytes of the base-85 number shall then be converted to ASCII characters by adding 33 (the ASCII code for the character !) to each. The resulting encoded data shall contain only printable ASCII characters with codes in the range 33 (!) to 117 (u). As a special case, if all five bytes are 0, they shall be represented by the character with code 122 (z) instead of by five exclamation points (! ! ! ! !).

If the length of the data to be encoded is not a multiple of 4 bytes, the last, partial group of 4 shall be used to produce a last, partial group of 5 output characters. Given n (1, 2, or 3) bytes of binary data, the


## Page 53

encoder shall first append 4 - n zero bytes to make a complete group of 4. It shall encode this group in the usual way, but shall not apply the special z case. Finally, it shall write only the first n + 1 characters of the resulting group of 5. These characters shall be immediately followed by the ~> EOD marker.

The following conditions shall never occur in a correctly encoded byte sequence:

• The value represented by a group of 5 characters is greater than 232 - 1.
• A z character occurs in the middle of a group.
• A final partial group contains only one character.

### 7.4.4 LZWDecode and FlateDecode filters

#### 7.4.4.1 General

The LZWDecode and (PDF 1.2) FlateDecode filters have much in common and are discussed together in this subclause. They decode data that has been encoded using the LZW or Flate data compression method, respectively:

• LZW (Lempel-Ziv-Welch) is a variable-length, adaptive compression method that has been adopted as one of the standard compression methods in Adobe TIFF Revision 6.0; Final (TIFF). For details on LZW encoding see 7.4.4.2, "Details of LZW encoding".
• The Flate method is based on the public-domain zlib/deflate compression method, which is a variable-length Lempel-Ziv adaptive compression method cascaded with adaptive Huffman coding. It is fully defined in Internet RFC 1950, and Internet RFC 1951.
Both of these methods compress either binary data or ASCII text but (like all compression methods) always produce binary data, even if the original data were text.

The LZW and Flate compression methods can discover and exploit many patterns in the input data, whether the data are text or images. As described later, both filters support optional transformation by a predictor function, which improves the compression of sampled image data.

> **NOTE 1** Because of its cascaded adaptive Huffman coding, Flate-encoded output is usually much more
compact than LZW-encoded output for the same input. Flate and LZW decoding speeds are comparable, but Flate encoding is considerably slower than LZW encoding.

> **NOTE 2** Usually, both Flate and LZW encodings compress their input substantially. However, in the worst
case (in which no pair of adjacent bytes appears twice), Flate encoding expands its input by no more than 11 bytes or a factor of 1.003 (whichever is larger), plus the effects of algorithm tags added by PNG predictors. For LZW encoding, the best case (all zeros) provides a compression
| approaching 1365:1 for long files, but the worst | -case expansion is at least a factor of 1.125, which can increase to nearly 1.5 in some implementations, plus the effects of PNG tags as with Flate encoding. |

#### 7.4.4.2 Details of LZW encoding

Data encoded using the LZW compression method shall consist of a sequence of codes that are 9 to 12 bits long. Each code shall represent a single character of input data (0–255), a clear-table marker (256), an EOD marker (257), or a table entry representing a multiple-character sequence that has been encountered previously in the input (258 or greater).

Initially, the code length shall be 9 bits and the LZW table shall contain only entries for the 258 fixed codes. As encoding proceeds, entries shall be appended to the table, associating new codes with longer


## Page 54

and longer sequences of input characters. The encoder and the decoder shall maintain identical copies of this table.

Whenever both the encoder and the decoder independently (but synchronously) realise that the current code length is no longer sufficient to represent the number of entries in the table, they shall increase the number of bits per code by 1. The first output code that is 10 bits long shall be the one following the creation of table entry 511, and similarly for 11 (1023) and 12 (2047) bits. Codes shall never be longer than 12 bits; therefore, entry 4095 is the last entry of the LZW table.

The encoder shall execute the following sequence of steps to generate each output code:

a) Accumulate a seq uence of one or more input characters matching a sequence already present in the
table.
b) For maximum compression, the encoder looks for the longest such sequence.
c) Emit the code corresponding to that sequence.
d) Create a new table entry for the first unused c ode. Its value is the sequence found in step (a) followed by
the next input character.

> **EXAMPLE 1** Suppose the input consists of the following sequence of ASCII character codes:

45 45 45 45 45 65 45 45 45 66

Starting with an empty table, the encoder proceeds as shown in "Table 7 — Typical LZW encoding sequence".

Table 7 — Typical LZW encoding sequence

| Input sequence | Output code | Code added to | Sequence represented by |

table new code

| – | 256 (clear-table) | – | – |

| 45 | 258 | 45 45 |

| 45 45 | 258 | 259 | 45 45 45 |

| 45 45 | 258 | 260 | 45 45 65 |

| 65 | 261 | 65 45 |

| 45 45 45 | 259 | 262 | 45 45 45 66 |

| 66 | – | – |

| – | 257 (EOD) | – | – |

Codes shall be packed into a continuous bit stream, high-order bit first. This stream shall then be divided into bytes, high-order bit first. Thus, codes may straddle byte boundaries arbitrarily. After the EOD marker (code value 257), any leftover bits in the final byte shall be set to 0.

In the example above, all the output codes are 9 bits long; they would pack into bytes as follows


## Page 55

(represented in hexadecimal):

> **EXAMPLE** 2

80 0B 60 50 22 0C 0C 85 01

To adapt to changing input sequences, the encoder may at any point issue a clear-table code, which causes both the encoder and the decoder to restart with initial tables and a 9-bit code length. The encoder shall begin by issuing a clear-table code. It shall issue a clear-table code when the table becomes full; it may do so sooner.

#### 7.4.4.3 LZWDecode and FlateDecode parameters

The LZWDecode and FlateDecode filters shall accept optional parameters to control the decoding process.

> **NOTE** Most of these parameters are related to techniques that reduce the size of compressed sa mpled
images (rectangular arrays of colour values, described in 8.9, "Images"). For example, image data typically changes very little from sample to sample. Therefore, subtracting the values of adjacent samples (a process called differencing), and encoding the differences rather than the raw sample values, can reduce the size of the output data. Furthermore, when the image data contains several colour components (red-green-blue or cyan-magenta-yellow-black) per sample, taking the difference between the values of corresponding components in adjacent samples, rather than between different colour components in the same sample, often reduces the output data size.
"Table 8 — Optional parameters for LZWDecode and FlateDecode filters" shows the parameters that may optionally be specified for LZWDecode and FlateDecode filters. Except where otherwise noted, all values supplied to the decoding filter for any optional parameters shall match those used when the data were encoded.

Table 8 — Optional parameters for LZWDecode and FlateDecode filters

| Key | Type | Value |

| Predictor | integer | A code that selects the predictor algorithm, if any. If the value of this entry is 1, the filter shall assume that the normal algorithm was used to encode the data, without prediction. If the value is greater than 1, the filter shall assume that the data were differenced before being encoded, and Predictor selects the predictor algorithm. For more information regarding Predictor values greater than 1, see 7.4.4.4, "LZW and Flate predictor functions". Default value: 1. |

| Colors | integer | (May be used only if Predictor is greater than 1) The number of interleaved colour components per sample. Valid values are 1 to 4 (PDF 1.0) and 1 or greater (PDF 1.3). Default value: 1. |

| BitsPerComponent | integer | (May be used only if Predictor is greater than 1) The number of bits used to represent each colour component in a sample. Valid values are 1, 2, 4, 8, and (PDF 1.5) 16. Default value: 8. |

| Columns | integer | (May be used only if Predictor is greater than 1) The number of samples in each row. Default value: 1. |


## Page 56

| Key | Type | Value |

| EarlyChange | integer | (LZWDecode only) An indication of when to increase the code length. If the value of this entry is 0, code length increases shall be postponed as long as possible. If the value is 1, code length increases shall occur one code early. This parameter is included because LZW sample code distributed by some vendors increases the code length one code earlier than necessary. Default value: 1. |

#### 7.4.4.4 LZW and Flate predictor functions

LZW and Flate encoding compress more compactly if their input data are highly predictable. One way of increasing the predictability of many continuous-tone sampled images is to replace each sample with the difference between that sample and a predictor function applied to earlier neighbouring samples. If the predictor function works well, the postprediction data clusters toward 0.

PDF files support two groups of predictor functions. The first, the TIFF group, shall be the single function that is Predictor 2 as defined in the Adobe TIFF Revision 6.0 specification.

> **NOTE 1** In the Adobe TIFF Revision 6.0 specification, Predictor 2 applies only to LZW compression, but
here it applies to Flate compression as well. TIFF Predictor 2 predicts that each colour component of a sample is the same as the corresponding colour component of the sample immediately to its left.
The second supported group of predictor functions, the PNG group, shall be the filters of the World Wide Web Consortium’s Portable Network Graphics recommendation, as defined in ISO/IEC 15948:2004.

The term predictors is used here instead of filters to avoid confusion.

“Table 9 — PNG predictor algorithms” lists the five basic PNG predictor algorithms (and a sixth that chooses the optimum predictor function separately for each row).

Table 9 — PNG predictor algorithms

# PNG Predictor Algorithms Description

| None | No prediction |

| Sub | Predicts the same as the sample to the left |

| Up | Predicts the same as the sample above |

| Average | Predicts the average of the sample to the left and the sample above |

| Paeth | A nonlinear function of the sample above, the sample to the left, and the sample to the upper left |

The predictor algorithm to be used, if any, shall be indicated by the Predictor filter parameter (see "Table 8 — Optional parameters for LZWDecode and FlateDecode filters"), whose value shall be one of


## Page 57

those listed in "Table 10 — Predictor values".

For LZWDecode and FlateDecode, a Predictor value greater than or equal to 10 shall indicate that a PNG predictor is in use; the specific predictor function used shall be explicitly encoded in the incoming data. The value of Predictor supplied by the decoding filter need not match the value used when the data were encoded if they are both greater than or equal to 10.

Table 10 — Predictor values

Value Meaning

No prediction (the default value)

TIFF Predictor 2

PNG prediction (on encoding, PNG None on all rows)

PNG prediction (on encoding, PNG Sub on all rows)

PNG prediction (on encoding, PNG Up on all rows)

PNG prediction (on encoding, PNG Average on all rows)

PNG prediction (on encoding, PNG Paeth on all rows)

PNG prediction (on encoding, PNG optimum)

The two groups of predictor functions have some commonalities. Both make the following assumptions:

• Data shall be presented in order, from the top row to the bottom row and, within a row, from left to right.
• A row shall occupy a whole number of bytes, rounded up if necessary.
• Samples and their components shall be packed into bytes from high-order to low-order bits.
• All colour components of samples outside the image (which are necessary for predictions near the boundaries) shall be 0.
The predictor function groups also differ in significant ways:

• The postprediction data for each PNG-predicted row shall begin with an explicit algorithm tag; therefore, different rows can be predicted with different algorithms to improve compression.
TIFF Predictor 2 has no such identifier; the same algorithm applies to all rows.
• The TIFF function group shall predict each colour component from the prior instance of that component, taking into account the number of bits per component and components per sample.
In contrast, the PNG function group shall predict each byte of data as a function of the corresponding byte of one or more previous image samples, regardless of whether there are multiple colour components in a byte or whether a single colour component spans multiple bytes.

> **NOTE 2** This can yield significantly better speed at the cost of somewhat worse compression.

### 7.4.5 RunLengthDecode filter

The RunLengthDecode filter decodes data that has been encoded in a simple byte-oriented format


## Page 58

based on run length. The encoded data shall be a sequence of runs, where each run shall consist of a length byte followed by 1 to 128 bytes of data. If the length byte is in the range 0 to 127, the following length + 1 (1 to 128) bytes shall be copied literally during decompression. If length is in the range 129 to 255, the following single byte shall be copied 257 - length (2 to 128) times during decompression. A length value of 128 shall denote EOD.

> **NOTE** The compression achieved by run-length encoding depends on the input data. In the best case
(all zeros), a compression of approximately 64:1 is achieved for long files. The worst case (the hexadecimal sequence 00 alternating with FF) results in an expansion of 127:128.

### 7.4.6 CCITTFaxDecode filter

The CCITTFaxDecode filter decodes image data that has been encoded using either Group 3 or Group

# 4 CCITT facsimile (fax) encoding in accordance with ITU Recommendation T.4: Standardization of Group

3 facsimile terminals for document transmission and ITU Recommendation T.6: Facsimile coding schemes and coding control functions for Group 4 facsimile apparatus, International Telecommunication Union (ITU).

> **NOTE 1** CCITT encoding is designed to achieve efficient compression of monochrome (1 bit per pixel)
image data at relatively low resolutions, and so is useful only for bitmap image data, not for colour images, grayscale images, or general data.

> **NOTE 2** The CCITT encoding standard is defined by the International Telecommunications Union (ITU),
formerly known as the Comité Consultatif International Téléphonique et Télégraphique (International Coordinating Committee for Telephony and Telegraphy). The encoding algorithm is not described in detail in this document but can be found in ITU Recommendations T.4 and T.6. For historical reasons, these documents are referred to as the CCITT standard.
CCITT encoding is bit-oriented, not byte-oriented. Therefore, in principle, encoded or decoded data need not end at a byte boundary. This problem shall be dealt with in the following ways:

• Unencoded data shall be treated as complete scan lines, with unused bits inserted at the end of each scan line to fill out the last byte. This approach is compatible with the PDF syntactic convention for sampled image data.
• Encoded data shall ordinarily be treated as a continuous, unbroken bit stream. The EncodedByteAlign parameter (described in "Table 11 – Optional parameters for the CCITTFaxDecode filter") may be used to cause each encoded scan line to be filled to a byte boundary.

> **NOTE 3** Although this is not prescribed by the CCITT standard and fax machines never do this, some
software packages find it convenient to encode data this way.
• When a filter reaches EOD, it shall always skip to the next byte boundary following the encoded data.
The filter shall not perform any error correction or resynchronization, except as noted for the DamagedRowsBeforeError parameter in "Table 11 – Optional parameters for the CCITTFaxDecode filter".

"Table 11 – Optional parameters for the CCITTFaxDecode filter" lists the optional parameters that may be used to control the decoding. Except where noted otherwise, all values supplied to the decoding filter by any of these parameters shall match those used when the data were encoded.


## Page 59

Table 11 – Optional parameters for the CCITTFaxDecode filter

| Key | Type | Value |

| K | integer | A code identifying the encoding scheme used: |
| --- | --- | --- |
| < 0 | Pure two-dimensional encoding (Group 4) |  |
| = 0 | Pure one-dimensional encoding (Group 3, 1-D) |  |
| > 0 | Mixed one- and two-dimensional encoding (Group 3, 2-D), in which a line encoded one-dimensionally may be followed by at most K-1 lines encoded two-dimensionally The filter shall distinguish among negative, zero, and positive values of K to determine how to interpret the encoded data; however, it shall not distinguish between different positive K values. Default value: 0. |  |

| EndOfLine | boolean | A flag indicating whether end-of-line bit patterns shall be present in the encoding. The CCITTFaxDecode filter shall always accept end- of-line bit patterns. If EndOfLine is true end-of-line bit patterns shall be present. Default value: false. |

| EncodedByteAlign | boolean | A flag indicating whether the filter shall expect extra 0 bits before each encoded line so that the line begins on a byte boundary. If true, the filter shall skip over encoded bits to begin decoding each line at a byte boundary. If false, the filter shall not expect extra bits in the encoded representation. Default value: false. |

| Columns | integer | The width of the image in pixels. If the value is not a multiple of 8, the filter shall adjust the width of the unencoded image to the next multiple of 8 so that each line starts on a byte boundary. Default value: 1728. |

| Rows | integer | The height of the image in scan lines. If the value is 0 or absent, the image’s height is not predetermined, and the encoded data shall be terminated by an end-of-block bit pattern or by the end of the filter’s data. Default value: 0. |

| EndOfBlock | boolean | A flag indicating whether the filter shall expect the encoded data to be terminated by an end-of-block pattern, overriding the Rows parameter. If false, the filter shall stop when it has decoded the number of lines indicated by Rows or when its data has been exhausted, whichever occurs first. The end-of-block pattern shall be the CCITT end-of-facsimile-block (EOFB) or return-to-control (RTC) appropriate for the K parameter. Default value: true. |

| BlackIs1 | boolean | A flag indicating whether bits with a value of 1 shall be interpreted as black pixels and 0 bits as white pixels, the reverse of the normal PDF syntactic convention for image data. Default value: false. |

| DamagedRowsBeforeError | integer | The number of damaged rows of data that shall be tolerated before an error occurs. This entry shall apply only if EndOfLine is true and K is non-negative. Tolerating a damaged row shall mean locating its end in the encoded data by searching for an EndOfLine pattern and then substituting decoded data from the previous row if the previous row was not damaged, or a white scan line if the previous row was also damaged. Default value: 0. |


## Page 60

> **NOTE 4** The compression achieved using CCITT encoding depends on the data, as well as on the value of
various optional parameters. For Group 3 one-dimensional encoding, in the best case (all zeros), each scan line compresses to 4 bytes, and the compression factor depends on the length of a scan line. If the scan line is 300 bytes long, a compression ratio of approximately 75:1 is achieved. The worst case, an image of alternating ones and zeros, produces an expansion of 2:9.

### 7.4.7 JBIG2Decode filter

The JBIG2Decode filter (PDF 1.4) decodes bitonal (1 bit per pixel) image data that has been encoded according to the JBIG2 standard as defined by ISO 14492:2019 excluding colour palette coding. While the JBIG2 compression method may also be used for encoding colour palette images into a JBIG2 bit stream, use of JBIG2 for PDF image XObjects shall be limited to using only bitonal (1 bit per pixel) in a JBIG2 coded bit stream. Specifically segments of type "colour palette" (type 54) shall not be present, and any segment and file header colour extension flag (COLEXTFLAG) shall be 0.

> **NOTE 1** The JBIG2 standard defines a coding method for bi-level images (e.g., black and white pages).
These are images consisting of a single rectangular bit plane, with each pixel taking on one of just two colours. It has been drafted by the Joint Bi-level Image Experts Group (JBIG), a committee established in 1988 that reports both to ISO/IEC JTC 1/SC29/WG1 and to ITU-T.
JBIG2, which supports both lossy and lossless compression, is useful for bitonal image data. JBIG2 explicitly defines the requirements of a compliant bitstream, and thus defines decoder behaviour.
JBIG2 does not explicitly define a standard encoder, but instead is flexible enough to allow various approaches to encoder design.

In general, JBIG2 provides considerably better compression of bitonal images than the CCITT standard (discussed in 7.4.6, "CCITTFaxDecode filter"). The compression it achieves depends strongly on the nature of the image. Images of pages containing text in any language compress particularly well. Where JBIG2 is used for lossy compression based on symbol dictionaries, care should be taken to avoid mismatches leading to character substitution (see 0.2.1 "Symbol coding" in ISO/IEC 14492:2019 for more details). While best compression is achieved for images of text, the JBIG2 standard also includes algorithms for representing regions of an image that contain dithered halftone images (for example, photographs) or line art.

While the JBIG2 compression method may also be used for encoding multiple images into a single JBIG2 bit stream, use of JBIG2 for PDF image XObjects shall be limited to using only page 1 in a JBIG2 coded bit stream. This does not preclude the shared use of global segments across several JBIG2 encoded PDF image XObjects.

> **NOTE 2** Typically, these images are scanned pages of a multiple-page document. Since a single table of
symbol bitmaps can be used to match symbols across multiple pages, this type of encoding can result in higher compression ratios than if each of the pages had been individually encoded using JBIG2.
In general, an image can be specified in PDF files as either an image XObject or an inline image (as described in 8.9, "Images"); however, the JBIG2Decode filter shall not be used with inline images.

Both single-page and multiple-page JBIG2 bit streams can be represented in PDF files by encoding each JBIG2 page as one PDF image XObject, as follows:

• The filter shall use the embedded file organisation of JBIG2 as defined in ISO/IEC 14492:2019, Annex D.3, "Embedded organization".


## Page 61

• The optional 2-byte combination (marker) defined in ISO/IEC 14492:2019, D.3, "Embedded organization" shall not be present.
• JBIG2 bit streams shall be represented in sequential organisation as defined in ISO/IEC 14492:2019, D.1, "Sequential organisation".
• The JBIG2 file header, end-of-page segments, and end-of-file segment shall not be present.

> **NOTE 3** It is recommended that segments appear in increasing order.
• The image XObject to which the JBIG2Decode filter is applied shall contain all segments that are associated with the JBIG2 page represented by that image; that is, all segments whose segment page association field contains the page number of the JBIG2 page represented by the image. In the image XObject, however, the segment’s page number shall always be 1; that is, when each such segment is written to the XObject, the value of its segment page association field shall be set to 1.
• If the bit stream contains global segments (segments whose segment page association field contains 0), these segments shall be placed in a separate PDF stream, and the filter parameter listed in "Table 12 — Optional parameter for the JBIG2Decode filter" shall refer to that stream.
The stream may be shared by multiple image XObjects whose JBIG2 encoded streams use the same global segments.

Table 12 — Optional parameter for the JBIG2Decode filter

| Key | Type | Value |

| JBIG2Globals | stream | A stream containing the JBIG2 global (page 0) segments. Global segments shall be placed in this stream even if only a single JBIG2 image XObject refers to it. |

The following shows an image that was compressed according to the JBIG2 standard and then encoded in ASCII hexadecimal representation. Since the JBIG2 bit stream contains global segments, these segments are placed in a separate PDF stream, as indicated by the JBIG2Globals filter parameter.

5 0 obj <</Type /XObject /Subtype /Image /Width 52 /Height 66 /ColorSpace /DeviceGray /BitsPerComponent 1 /Length 224 /Filter [/ASCIIHexDecode /JBIG2Decode] /DecodeParms [null <</JBIG2Globals 6 0 R>>] >> stream

00000040000000000002062000010000001e000000340000 004200000000000000000200100000000231db51ce51ffac> endstream endobj

6 0 obj <</Length 126 /Filter /ASCIIHexDecode >> stream 0000000000010000000032000003fffdff02fefefe000000 01000000012ae225aea9a5a538b4d9999c5c8e56ef0f872 7f2b53d4e37ef795cc5506dffac>


## Page 62

endstream endobj

The original JBIG2 bit stream from which the image XObject for this example was constructed is as follows:

97 4A 42 32 0D 0A 1A 0A 01 00 00 00 01 00 00 00 00 00 01 00 00 00 00 32 00 00 03 FF FD FF 02 FE FE FE 00 00 00 01 00 00 00 01 2A E2 25 AE A9 A5 A5 38 B4 D9 99 9C 5C 8E 56 EF 0F 87 27 F2 B5 3D 4E 37 EF 79 5C C5 50 6D

# FF AC 00 00 00 01 30 00 01 00 00 00 13 00 00 00 34 00 00 00 42 00 00 00

00 00 00 00 00 40 00 00 00 00 00 02 06 20 00 01 00 00 00 1E 00 00 00 34 00 00 00 42 00 00 00 00 00 00 00 00 02 00 10 00 00 00 02 31 DB 51 CE 51

# FF AC 00 00 00 03 31 00 01 00 00 00 00 00 00 00 04 33 01 00 00 00 00

This bit stream is made up of the following parts (in the order listed):

a) The JBIG2 file header

97 4A 42 32 0D 0A 1A 0A 01 00 00 00 01

Since the JBIG2 file header is not allowed in a JBIG2 encoded image XObject in PDF files, this header is not placed in the JBIG2 stream object and is discarded.

b) The first JBIG2 segment (segment 0) is a symbol dictionary segment

00 00 00 00 00 01 00 00 00 00 32 00 00 03 FF FD FF 02 FE FE FE 00 00 00 01 00 00 00 01 2A E2 25 AE A9 A5 A5 38 B4 D9 99 9C 5C 8E 56 EF 0F 87

# 27 F2 B5 3D 4E 37 EF 79 5C C5 50 6D FF AC

This is a global segment (segment page association = 0) and thus is placed in the JBIG2Globals stream.

c) The next segments are the page information segment
00 00 00 01 30 00 01 00 00 00 13 00 00 00 34 00 00 00 42 00 00 00 00 00 00 00 00 40 00 00

and the immediate text region segment

00 00 00 02 06 20 00 01 00 00 00 1E 00 00 00 34 00 00 00 42 00 00 00 00 00 00 00 00 02 00 10 00 00 00 02 31 DB 51 CE 51 FF AC

These two segments constitute the contents of the JBIG2 page and are placed in the image XObject representing this image.

d) The last segments are the end-of-page segment
00 00 00 03 31 00 01 00 00 00 00

and the end-of-file segment

00 00 00 04 33 01 00 00 00 00

Since these segments are not allowed in a JBIG2 encoded image XObject in PDF files, they are discarded.

The resulting PDF image XObject, then, contains the page information segment and the immediate text region segment and refers to a JBIG2Globals stream that contains the symbol dictionary segment.


## Page 63

### 7.4.8 DCTDecode filter

The DCTDecode filter decodes grayscale or colour image data that has been encoded in the JPEG baseline format in accordance with ISO/IEC 10918 (all parts). Refer also to Adobe Technical Note #5116, Supporting the DCT Filters in PostScript Level 2, for additional information about the use of JPEG "markers".

> **NOTE 1** JPEG stands for the Joint Photographic Experts Group, a group within the International
Organization for Standardization that developed the format; DCT stands for discrete cosine transform, the primary technique used in the encoding.
JPEG encoding is a lossy compression method, designed specifically for compression of sampled continuous-tone images and not for general data compression.

Data to be encoded using JPEG shall consist of a stream of image samples, each consisting of one, three, or four colour components. The colour component values for a particular sample shall appear consecutively. Each component value shall occupy a byte.

During encoding, several parameters shall control the algorithm and the information loss. The values of these parameters, which include the dimensions of the image and the number of components per sample, are entirely under the control of the encoder and shall be stored in the encoded data.
DCTDecode may obtain the parameter values it requires directly from the encoded data. However, in one instance, the parameter need not be present in the encoded data but shall be specified in the filter parameter dictionary; see "Table 13 – Optional parameter for the DCTDecode filter".

The details of the encoding algorithm are not presented here but are in the ISO JPEG standard. Briefly, the JPEG algorithm breaks an image up into blocks that are 8 samples wide by 8 samples high. Each colour component in an image is treated separately. A two-dimensional DCT is performed on each block. This operation produces 64 coefficients, which are then quantised. Each coefficient can be quantised with a different step size. It is this quantization that results in the loss of information in the JPEG algorithm. The quantised coefficients are then compressed.


## Page 64

Table 13 – Optional parameter for the DCTDecode filter

| Key | Type | Value |

| ColorTransform | integer | (Optional) A code specifying the transformation that shall be performed on the sample values: |

# 0 No transformation.

# 1 If the image has three colour components, RGB values

shall be transformed to YCbCr before encoding and from YCbCr to RGB after decoding. If the image has four components, CMYK values shall be transformed to YCbCrK before encoding and from YCbCrK to CMYK after decoding.
This option shall be ignored if the image has one or two colour components.
If the encoding algorithm has inserted the Adobe-defined marker code in the encoded data indicating the ColorTransform value, then the colours shall be transformed, or not, after the DCT decoding has been performed according to the value provided in the encoded data and the value of this dictionary entry shall be ignored. If the Adobe-defined marker code in the encoded data indicating the ColorTransform value is not present then the value specified in this dictionary entry will be used. If the Adobe-defined marker code (APP14) in the encoded data indicating the ColorTransform value is not present and this dictionary entry is not present in the filter dictionary then the default value of ColorTransform shall be 1 if the image has three components and 0 otherwise.
Parameters that control the decoding process as well as other metadata are embedded within the encoded data stream using a notation referred to as "markers". When Adobe Systems Incorporated defined the use of JPEG images within PostScript language data streams, Adobe defined a particular set of rules pertaining to which markers are to be recognised, which are to be ignored and which are considered errors. A specific Adobe-defined marker (X’FFEE, sometimes called ‘APPE’ or ‘APP14’) was also introduced. The exact rules for producing and consuming DCT encoded data within PostScript language are provided in Adobe Technical Note #5116 and PDF DCT encoding shall exactly follow all those rules established by Adobe for the PostScript language.

> **NOTE 2** The encoding algorithm can reduce the information loss by making the step size in the
quantization smaller at the expense of reducing the amount of compression achieved by the algorithm. The compression achieved by the JPEG algorithm depends on the image being compressed and the amount of loss that is acceptable. In general, a compression of 15:1 can be achieved without perceptible loss of information, and 30:1 compression causes little impairment of the image.

> **NOTE 3** Better compression is often available for colour spaces that treat luminance and chrominance
separately than for those that do not. The RGB-to-YCbCr conversion provided by the filters is one attempt to separate luminance and chrominance; it conforms to CCIR recommendation 601-1.
Other colour spaces, such as the CIE 1976 L*a*b* space, can also achieve this objective. The chrominance components can then be compressed more than the luminance by using coarser sampling or quantization, with no degradation in quality.
In addition to the baseline JPEG format, beginning with PDF 1.3, the DCTDecode filter shall support the progressive JPEG extension. This extension does not add any entries to the DCTDecode parameter


## Page 65

dictionary; the distinction between baseline and progressive JPEG shall be represented in the encoded data.

> **NOTE 4** There is no benefit to using progressive JPEG for stream data that is embedded in a PDF file.
Decoding progressive JPEG is slower and consumes more memory than baseline JPEG. The purpose of this feature is to enable a stream to refer to an external file whose data happens to be already encoded in progressive JPEG.

### 7.4.9 JPXDecode filter

The JPXDecode filter (PDF 1.5) decodes data that has been encoded using the JPEG 2000 compression method, an International Standard for the compression and packaging of image data. See ISO/IEC 15444-1, Information technology — JPEG 2000 image coding system: Core coding system and ISO/IEC 15444-2, Information Technology — JPEG 2000 image coding system: Extensions, available from http://www.jpeg.org/jpeg2000/.

> **NOTE 1** JPEG 2000 defines a wavelet-based method for image compression that gives somewhat better
size reduction than other methods such as regular JPEG or CCITT. Also, the filter can reproduce samples that are losslessly compressed.
This filter shall only be applied to image XObjects, and not to inline images (see 8.9, "Images"). It is suitable both for images that have a single colour component and for those that have multiple colour components. The colour components in an image may have different numbers of bits per sample, however bits per sample shall be between 1 to 38 inclusive.

> **NOTE 2** From a single JPEG 2000 data stream, multiple versions of an image can be decoded. These
different versions form progressions along four degrees of freedom: sampling resolution, colour depth, band, and location. For example, with a resolution progression, a thumbnail version of the image can be decoded from the data, followed by a sequence of other versions of the image, each with approximately four times as many samples (twice the width times twice the height) as the previous one. The last version is the full-resolution image.

> **NOTE 3** Viewing and printing applications can gain performance benefits by using the resolution
progression. If the full-resolution image is densely sampled, an application can select and decode only the data making up a lower-resolution version, thereby spending less time decoding. Fewer bytes need be processed, a particular benefit when viewing files over the Web. The tiling structure of the image can also provide benefits if only certain areas of an image need to be displayed or printed.

> **NOTE 4** Information on these progressions is encoded in the data; no decode parameters are needed to
describe them. The decoder deals with any progressions it encounters to deliver the correct image data. Progressions that are of no interest can simply have performance consequences.
The JPEG 2000 specifications define two widely used formats, JP2 and JPX, for packaging the compressed image data. JP2 is a subset of JPX. These packagings contain all the information needed to properly interpret the image data, including the colour space, bits per component, and image dimensions. In other words, they are complete descriptions of images (as opposed to image data that require outside parameters for correct interpretation). The JPXDecode filter shall expect to read a full JPX file structure – either internal to the PDF file or as an external file.

> **NOTE 5** To promote interoperability, the specifications define a subset of JPX called JPX baseline (of
which JP2 is also a subset). The complete details of the baseline set of JPX features are contained in ISO/IEC 15444-2.
Data used in PDF image XObjects shall be limited to the JPX baseline set of features, except for enumerated colour space 19 (CIEJab). In addition, enumerated colour space 12 (CMYK), which is part


## Page 66

of JPX but not JPX baseline, shall be supported in a PDF file. JPX file structures used in PDF files shall conform to the JPEG 2000 specification.

# A JPX file describes a collection of channels that are present in the image data. A channel may have one

of three types:

• An ordinary channel contains values that, when decoded, shall become samples for a specified colour component.
• An opacity channel provides samples that shall be interpreted as raw opacity information.
• A premultiplied opacity channel shall provide samples that have been multiplied into the colour samples of those channels with which it is associated.
JPX opacity and premultiplied opacity channels are associated with specific colour channels. There shall not be more than one opacity channel (of either type) associated with a given colour channel.

> **EXAMPLE** It is possible for one opacity channel to apply to the red samples and another to apply to the green and blue
colour channels of an RGB image.

> **NOTE 6** The method by which the opacity information is to be used is explicitly not specified, although
one available method shows a normal blending mode.

> **NOTE 7** As specified below, if JPX opacity is used in PDF files, then the JPX data are only allowed to have
one opacity channel, and this single channel is required to be associated with all colour channels.
In addition to using opacity channels for describing transparency, JPX files also have the ability to specify chroma-key transparency. A single colour may be specified by giving an array of values, one value for each colour channel. Any image location that matches this colour shall be considered to be completely transparent. For JPX files used in PDF files, the chroma-key transparency shall be ignored.

> **NOTE 8** For images in PDF files a similar mechanism exists, which allows to specify a range of colours to
be considered transparent; see 8.9.6.4, "Colour key masking".
Images in JPX files may have one of the following colour spaces:

• A predefined colour space, chosen from a list of enumerated colour spaces. (Two of these are actually families of spaces and parameters are included.) • A restricted ICC profile. These are the only sorts of ICC profiles that are allowed in JP2 files.
• An input ICC profile of any sort defined by ICC.1.
• A vendor-defined colour space.
More than one colour space may be specified for an image, with each space being tagged with a precedence and an approximation value that indicates how well it represents the preferred colour space. In addition, the image’s colour space may serve as the foundation for a palette of colours that are
| selected using samples coming from the image’s data channels: the equivalent of an | Indexed colour |

space in PDF syntax.

| There are other features in the JPX format beyond describing a simple image | ; they are not supported in |

PDF files. These include provisions for describing layering and giving instructions on composition and specifying simple animation. Relevant metadata should be replicated in the image dictionary’s Metadata stream in XMP format (see 14.3.2, "Metadata streams").

When using the JPXDecode filter with image XObjects, the following changes to and constraints on some entries in the image dictionary shall apply (see 8.9.5, "Image dictionaries" for details on these


## Page 67

entries):

• Width and Height shall match the corresponding width and height values in the JPEG 2000 data.
• ColorSpace shall be optional since JPEG 2000 data contain colour space specifications. If present, it shall determine how the image samples are interpreted, and the colour space specifications in the JPEG 2000 data shall be ignored. The number of ordinary colour channels in the JPEG 2000 data shall match the number of components in the colour space; a PDF writer shall ensure that the samples are consistent with the colour space used.
• Any colour space other than Pattern may be specified. If an Indexed colour space is used, it shall be subject to the PDF syntactic limit of 256 colours. If the colour space does not match one of JPX’s enumerated colour spaces (for example, if it has two colour components or more than four), it should be specified as a vendor colour space in the JPX data.
• If ColorSpace is not present in the image dictionary, the colour space information in the JPEG 2000 data shall be used. A JPEG 2000 image within a PDF file shall have one of: the baseline JPX colour spaces excluding enumerated colour space 19 (CIEJab); or enumerated colour space 12 (CMYK); or at least one ICC profile that is valid within PDF files. PDF processors shall support the JPX baseline set of enumerated colour spaces; they shall also be responsible for dealing with the interaction between the colour spaces and the bit depth of samples.

> **NOTE 9** The above bullet point was clarified in this document (2020).
• If multiple colour space specifications are given in the JPEG 2000 data, a PDF processor should attempt to use the one with the highest precedence and best approximation value. If the colour space is given by an unsupported ICC profile, the next lower colour space, in terms of precedence and approximation value, shall be used. If no supported colour space is found, the colour space used shall be DeviceGray, DeviceRGB, or DeviceCMYK, depending on the whether the number of ordinary channels in the JPEG 2000 data is 1, 3, or 4.
• SMaskInData specifies whether soft-mask information packaged with the image samples shall be used (see 11.6.5.2, "Soft-mask images"); if it is, the SMask entry shall not be present. If SMaskInData is non-zero, there shall be only one opacity channel in the JPEG 2000 data and it shall apply to all colour channels.
• If ColorSpace is absent, then the Decode array shall be ignored unless ImageMask is true.
• If ImageMask is true, the JPEG 2000 data shall provide a single colour channel with 1-bit samples.

### 7.4.10 Crypt filter

The Crypt filter (PDF 1.5) allows the document-level security handler (see 7.6, "Encryption") to determine which algorithms should be used to decrypt the input data. The Name parameter in the decode parameters dictionary for this filter (see "Table 14 — Optional parameters for Crypt filters") shall specify which of the named crypt filters in the document (see 7.6.6, "Crypt filters") shall be used.
The Crypt filter shall be the first filter in the Filter array entry.

Table 14 — Optional parameters for Crypt filters

| Key | Type | Value |

| Type | name | (Optional) If present, shall be CryptFilterDecodeParms for a Crypt filter decode parameter dictionary. |


## Page 68

| Key | Type | Value |

| Name | name | (Optional) The name of the crypt filter that shall be used to decrypt this stream. The name shall correspond to an entry in the CF entry of the encryption dictionary (see "Table 20 — Entries common to all encryption dictionaries") or one of the standard crypt filters (see "Table 26 — Standard crypt filter names"). Default value: Identity. |

In addition, the decode parameters dictionary may include entries that are private to the security handler. Security handlers may use information from both the crypt filter decode parameters dictionary and the crypt filter dictionaries (see "Table 25 — Entries common to all crypt filter dictionaries") when decrypting data or providing a key to decrypt data.

> **NOTE** When adding private data to the decode parameters dictionary, security handlers are required to
name these entries as defined by Annex E, "Extending PDF".
If a stream specifies a crypt filter, then the security handler does not apply "Algorithm 1: Encryption of data using the RC4 or AES algorithms" in 7.6.3, "General encryption algorithm" to the key prior to decrypting the stream. Instead, the security handler shall decrypt the stream using the key as is. 7.4, "Filters" explains how a stream specifies filters.

## 7.5 File structure

### 7.5.1 General

This subclause describes how objects are organised in a PDF file for efficient random access and incremental update. A basic conforming PDF file shall be constructed of the following four elements (see "Figure 2 — Initial structure of a PDF file"):

• A one-line header identifying the version of the PDF specification to which the PDF file conforms • A body containing the objects that make up the document contained in the PDF file • A cross-reference table containing information about the indirect objects in the PDF file • A trailer giving the location of the cross-reference table and of certain special objects within the body of the PDF file This initial structure may be modified by later updates, which append additional elements to the end of the file; see 7.5.6, "Incremental updates" for details.


## Page 69

Figure 2 — Initial structure of a PDF file As a matter of convention, the tokens in a PDF file are arranged into lines; see 7.2, "Lexical conventions". PDF files with binary data may have arbitrarily long lines.

The rules described here are sufficient to produce a basic conforming PDF file. However, additional rules apply to organising a PDF file to enable efficient incremental access to a document’s components in a network environment. This form of organisation, called Linearized PDF, is described in Annex F, "Linearized PDF" and Annex G, " Linearized PDF access strategies". With the exception of linearized PDF files, all PDF files should be read using the trailer and cross-reference table as described in the following subclauses. Reading a non-linearized file in a serial manner is not reliable because of the way objects are to be processed after an incremental update. (See 6.3.2, "Conformance of PDF processors".)

### 7.5.2 File header

The PDF file begins with the 5 characters “%PDF–” and byte offsets shall be calculated from the PERCENT SIGN (25h).

> **NOTE 1** This provision allows for arbitrary bytes preceding the %PDF- without impacting the viability of
the PDF file and its byte offsets.

# A PDF reader shall accept files with any of the following headers:

%PDF–1.0

%PDF–1.1

%PDF–1.2

%PDF–1.3

%PDF–1.4

%PDF–1.5

%PDF–1.6


## Page 70

%PDF–1.7

%PDF–2.0

The file header shall consist of “%PDF–1.n” or “%PDF–2.n” followed by a single EOL marker, where ‘n’ is a single digit number between 0 (30h) and 9 (39h).

> **NOTE 2** The above paragraph was added in this document (2020).

# A PDF processor that writes a file that conforms to this document shall identify the version (either in

the header or as the value of the Version entry in the document’s catalog dictionary (see 7.7.2, "Document catalog dictionary") as 2.0.

> **NOTE 3** The Version entry in the document’s catalog dictionary is used to update the version using an
incremental update (see 7.5.6, "Incremental updates").
Under some conditions, a PDF processor may be able to process PDF files conforming to a later version than it was designed to accept. New features in PDF files are often introduced in such a way that they can safely be ignored by a PDF processor that does not understand them (see Annex I, "PDF versions and compatibility).

This document defines the Extensions entry in the document’s catalog dictionary. If present, it shall identify any developer-defined extensions that are contained in this PDF file. See 7.12, "Extensions dictionary".

If a PDF file contains binary data, as most do (see 7.2, "Lexical conventions"), the header line shall be immediately followed by a comment line containing at least four binary characters–that is, characters whose codes are 128 or greater. This ensures proper behaviour of file transfer applications that inspect data near the beginning of a file to determine whether to treat the file’s contents as text or as binary.

### 7.5.3 File body

The body of a PDF file shall consist of a sequence of indirect objects representing the contents of a document. The objects, which are of the basic types described in 7.3, "Objects" represent components of the document such as fonts, pages, and sampled images. Beginning with PDF 1.5, the body can also contain object streams, each of which contains a sequence of indirect objects; see 7.5.7, "Object streams".

### 7.5.4 Cross-reference table

The cross-reference table contains information that permits random access to indirect objects within the PDF file so that the entire PDF file need not be read to locate any particular object. The table shall contain a one-line entry for each indirect object, specifying the byte offset of that object within the body of the PDF file. Beginning with PDF 1.5, some or all of the cross-reference information may alternatively be contained in cross-reference streams; see 7.5.8, "Cross-reference streams".

> **NOTE 1** The cross-reference table is the only part of a PDF file with a fixed format, which permits entries
in the table to be accessed randomly.
The table comprises one or more cross-reference sections. Initially, the entire table consists of a single section (or two sections if the file is linearized; see Annex F, "Linearized PDF" and Annex G, "Linearized PDF access strategies"). One additional section shall be added each time the file is incrementally


## Page 71

updated (see 7.5.6, "Incremental updates").

Each cross-reference section shall begin with a line containing the keyword xref. Following this line shall be one or more cross-reference subsections, which may appear in any order. For a PDF file that has never been incrementally updated, the cross-reference section shall contain only one subsection, whose object numbering begins at 0. PDF comments shall not be included in a cross-reference table between the keywords xref and trailer.

> **NOTE 2** (2020) This document clarified the above paragraph in relation to comments not being
permitted in cross-reference tables, as this is also stated in 7.5.8.4, "Compatibility with applications that do not support compressed reference streams".

> **NOTE 3** The subsection structure is useful for incremental updates, since it allows a new cross-reference
section to be added to the PDF file, containing entries only for objects that have been added, modified or deleted. This also means that cross reference subsections of incremental updates can never have an object number of zero.
Each cross-reference subsection shall contain entries for a contiguous range of object numbers. Each cross-reference subsection shall contain entries for a contiguous range of object numbers. The subsection shall begin with a line containing only two integers separated by a SPACE (20h) and terminated by an end-of-line marker (see 7.2.3, "Character set"). The two integers denote (respectively) the object number of the first object in this subsection and the number of entries in the subsection.

> **NOTE 4** The number of entries in a subsection for a file that has never been inc rementally updated can
never be zero.

> **EXAMPLE 1** The following line introduces a subsection containing five objects numbered consecutively from 28 to 32.

28 5

A given object number shall not have an entry in more than one subsection within a single section.

Following this line are the cross-reference entries themselves, one per line. Each entry shall be exactly 20 bytes long, including the end-of-line marker. There are two kinds of cross-reference entries: one for objects that are in use and another for objects that have been deleted and therefore are free. Both types of entries have similar basic formats, distinguished by the keyword n (for an in-use entry) or f (for a free entry). The format of an in-use entry shall be:

nnnnnnnnnn ggggg n eol

where:

nnnnnnnnnn shall be a 10-digit byte offset in the decoded stream

ggggg shall be a 5-digit generation number

n shall be a keyword identifying this as an in-use entry

eol shall be a 2-character end-of-line sequence

The byte offset in the decoded stream shall be a 10-digit number, padded with leading zeros if necessary, giving the number of bytes from the beginning of the PDF file to the beginning of the object.
It shall be separated from the generation number by a single SPACE. The generation number shall be a


## Page 72

5-digit number, also padded with leading zeros if necessary. Following the generation number shall be a single SPACE, the keyword n, and a 2-character end-of-line sequence consisting of one of the following: SP CR, SP LF, or CR LF. Thus, the overall length of the entry shall always be exactly 20 bytes.

The cross-reference entry for a free object has essentially the same format, except that the keyword shall be f instead of n and the interpretation of the first item is different:

nnnnnnnnnn ggggg f eol

where:

nnnnnnnnnn shall be the 10-digit object number of the next free object

ggggg shall be a 5-digit generation number

f shall be a keyword identifying this as a free entry

eol shall be a 2-character end-of-line sequence

There are two ways an entry may be a member of the free entries list. Using the basic mechanism the free entries in the cross-reference table may form a linked list, with each free entry containing the object number of the next. The first entry in the table (object number 0) shall always be free and shall have a generation number of 65,535; this entry shall be the head of the linked list of free objects. The last free entry (the tail of the linked list) links back to object number 0. Using the second mechanism, the table may contain other free entries that link back to object number 0 and have a generation number of 65,535, even though these entries are not in the linked list itself.

Except for object number 0, all objects in the cross-reference table shall initially have generation numbers of 0. When an indirect object is deleted, its cross-reference entry shall be marked free and it shall be added to the linked list of free entries. The entry’s generation number shall be incremented by 1 to indicate the generation number to be used the next time an object with that object number is created. Thus, each time the entry is reused, it is given a new generation number. The maximum generation number is 65,535; when a cross-reference entry reaches this value, it shall never be reused.

The cross-reference table (comprising the original cross-reference section and all update sections) shall contain one entry for each object number from 0 to the maximum object number defined in the PDF file, even if one or more of the object numbers in this range do not actually occur in the PDF file.

> **EXAMPLE 2** The following shows a cross-reference section consisting of a single subsection with six entries: four that
are in use (objects number 1, 2, 4, and 5) and two that are free (objects number 0 and 3). Object number 3 has been deleted, and the next object created with that object number is given a generation number of 7.

xref
| 0 | 6 0000000003 65535 f 0000000017 00000 n 0000000081 00000 n 0000000000 00007 f 0000000331 00000 n 0000000409 00000 n |

> **EXAMPLE 3** The following shows a cross-reference section with four subsections, containing a total of five entries. The
first subsection contains one entry, for object number 0, which is free. The second subsection contains one entry, for object number 3, which is in use. The third subsection contains two entries, for objects number 23 and 24, both of which are in use. Object number 23 has been reused, as can be seen from the fact that it has


## Page 73

a generation number of 2. The fourth subsection contains one entry, for object number 30, which is in use.

xref
| 0 | 1 |
| --- | --- |
| 0000000000 | 65535 f 3 1 0000025325 00000 n 23 2 0000025518 00002 n 0000025635 00000 n 30 1 0000025777 00000 n |

See H.7, "Updating example", for a more extensive example of the structure of a PDF file that has been updated several times.

### 7.5.5 File trailer

The trailer of a PDF file enables a PDF processor to quickly find the cross-reference table and certain special objects. PDF processors should read a PDF file from its end. The last line of the file shall contain only the end-of-file marker, %%EOF. The two preceding lines shall contain, one per line and in order, the keyword startxref and the byte offset in the decoded stream from the beginning of the PDF file to the beginning of the xref keyword in the last cross-reference section. The startxref line shall be preceded by the trailer dictionary, consisting of the keyword trailer followed by a series of key-value pairs enclosed in double angle brackets (<<…>>) (using LESS-THAN SIGNs (3Ch) and GREATER-THAN SIGNs (3Eh)). Thus, the trailer has the following overall structure:

trailer <<key1 value1 key2 value2 … keyn valuen >> startxref Byte_offset_of_last_cross-reference_section %%EOF

"Table 15 — Entries in the file trailer dictionary" lists the contents of the trailer dictionary. The PDF file trailer dictionary may also contain any second-class name as described in Annex E, "Extending PDF".

Table 15 — Entries in the file trailer dictionary

| Key | Type | Value |

| Size | integer | (Required; shall not be an indirect reference) The total number of entries in the PDF file’s cross-reference table, as defined by the combination of the original section and all update sections. Equivalently, this value shall be 1 greater than the highest object number defined in the PDF file. Any object in a cross-reference section whose number is greater than this value shall be ignored and defined to be missing by a PDF reader. |

| Prev | integer | (Optional, present only if the file has more than one cross-reference section; shall be a direct object) The byte offset from the beginning of the PDF file to the beginning of the previous cross-reference stream. |


## Page 74

| Key | Type | Value |

| Root | dictionary | (Required; shall be an indirect reference) The catalog dictionary for the PDF file (see 7.7.2, "Document catalog dictionary"). |

| Encrypt | dictionary | (Required if document is encrypted; PDF 1.1) The PDF file’s encryption dictionary (see 7.6, "Encryption"). |

| Info | dictionary | (Optional; shall be an indirect reference) The PDF file’s information dictionary. As described in 14.3.3, "Document information dictionary", this method for specifying document metadata has been deprecated in PDF 2.0 and should therefore only be used to encode information that is stated as required elsewhere in this document. |

> **NOTE 1** The ModDate key within the Info dictionary is required if Page-Piece
dictionaries (see 14.5, "Page-piece dictionaries") are used.

| ID | array | (Required in PDF 2.0 and later, or if an Encrypt entry is present; optional otherwise; PDF 1.1) An array of two byte-strings constituting a PDF file identifier (See 14.4, "File identifiers") for the PDF file. Each PDF file identifier byte-string shall have a minimum length of 16 bytes. If there is an Encrypt entry, this array and the two byte-strings shall be direct objects and shall be unencrypted. |

> **NOTE 2** Because the ID entries are not encrypted, the ID key can be checked to
assure that the correct PDF file is being accessed without decrypting the PDF file. The restrictions that the objects all be direct objects and not be encrypted ensure this.

> **NOTE 3** Although this entry is optional prior to PDF 2.0, its absence can
prevent the PDF file from functioning in some workflows that depend on PDF files being uniquely identified.

> **NOTE 4** The values of the ID strings are used as input to the encryption
algorithm. If these strings were indirect, or if the ID array were indirect, these strings would be encrypted when written. This would result in a circular condition for a PDF reader: the ID strings need be decrypted in order to use them to decrypt strings, including the ID strings themselves. The preceding restriction prevents this circular condition.

> **NOTE** "Table 19 — Additional entries in a hybrid-reference file’s trailer dictionary" defines an
additional entry, XRefStm, that appears only in the trailer of hybrid-reference files, described in 7.5.8.4, "Compatibility with applications that do not support compressed reference streams"

> **EXAMPLE** This example shows a trailer for a PDF file that has never been updated (as indicated by the absence of a
Prev entry in the trailer dictionary).

trailer <</Size 22 /Root 2 0 R /Info 1 0 R /ID [<81b14aafa313db63dbd6f981e49f94f4> <81b14aafa313db63dbd6f981e49f94f4> ] >> startxref

%%EOF


## Page 75

### 7.5.6 Incremental updates

The contents of a PDF file can be updated incrementally without rewriting the entire file. When updating a PDF file incrementally, changes shall be appended to the end of the file, leaving its original contents intact.

> **NOTE 1** The main advantage to updating a PDF file in this way is that small changes to a large document
can be saved quickly. There are additional advantages such as when editing a document across an HTTP connection or using OLE embedding (a Microsoft WindowsTM specific technology), a
| PDF processor cannot overwrite the contents of the original | PDF file. Incremental updates are used to save changes to documents in these contexts. |

> **NOTE 2** The resulting PDF file has the structure shown in "Figure 3 — Structure of an updated PDF file".
A complete example of an updated file is shown in H.7, "Updating example".
A cross-reference section for an incremental update shall contain entries only for objects that have been changed, replaced, or deleted. Deleted objects shall be left unchanged in the PDF file, but shall be marked as deleted by means of their cross-reference entries. The added trailer shall contain all the entries except the Prev entry (if present) from the previous trailer, whether modified or not. In addition, the added trailer dictionary shall contain a Prev entry giving the location of the previous cross-reference section (see "Table 15 — Entries in the file trailer dictionary"). Each trailer shall be terminated by its own end-of-file (%%EOF) marker.

> **NOTE 3** As shown in "Figure 3 — Structure of an updated PDF file", a PDF file that has been updated
several times contains several trailers. Because updates are appended to PDF files, a PDF file can have several copies of an object with the same object identifier (object number and generation number).

> **EXAMPLE** Several copies of an object can occur if a text annotation (see 12.5, "Annotations") is changed several times
and the PDF file is saved between changes. Because the text annotation object is not deleted, it retains the same object number and generation number as before. The updated copy of the object is included in the new update section added to the PDF file.

The update’s cross-reference section shall include a byte offset to this new copy of the object, overriding the old byte offset contained in the original cross-reference section. When a PDF reader reads the PDF file, it shall build its cross-reference information in such a way that the most recent copy of each object shall be the one accessed from the PDF file.

In versions of PDF 1.4 or later a PDF writer may use the Version entry in the document’s catalog dictionary (see 7.7.2, "Document catalog dictionary") to override the version specified in the header. A PDF writer may also need to update the Extensions dictionary, see 7.12, "Extensions dictionary", if the update either deleted or added developer-defined extensions.

> **NOTE 4** The Version entry enables the version to be altered when performing an incremental update.


## Page 76

Figure 3 — Structure of an updated PDF file

### 7.5.7 Object streams

An object stream is a stream object in which a sequence of indirect objects may be stored, as an alternative to their being stored at the outermost PDF file level.

> **NOTE 1** Object streams are first introduced in PDF 1.5. The purpose of object streams is to allow indirect
objects other than streams to be stored more compactly by using the facilities provided by stream compression filters.

> **NOTE 2** The term "compressed object" is used regardless of whether the stream is actually encoded with
a compression filter.
The following objects shall not be stored in an object stream:

• Stream objects • Objects with a generation number other than zero • A document’s encryption dictionary (see 7.6, "Encryption") • An object representing the value of the Length entry in an object stream dictionary • In linearized files (see Annex F, "Linearized PDF" and Annex G, "Linearized PDF access strategies"), the document catalog dictionary, the linearization dictionary, and page objects shall


## Page 77

not appear in an object stream.

> **NOTE 3** Indirect references to objects inside object streams use the normal syntax: for example, 14 0 R.
Access to these objects requires a different way of storing cross-reference information; see 7.5.8, "Cross-reference streams". Use of compressed objects requires a PDF 1.5 PDF reader. However, compressed objects can be stored in a manner that a PDF 1.4 PDF reader can ignore.
In addition to the regular keys for streams shown in "Table 5 — Entries common to all stream dictionaries", the stream dictionary describing an object stream contains the entries specified in “Table 16 — Additional entries specific to an object stream dictionary”.

Table 16 — Additional entries specific to an object stream dictionary

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be ObjStm for an object stream. |

| N | integer | (Required) The number of indirect objects stored in the stream. |

| First | integer | (Required) The byte offset in the decoded stream of the first compressed object. |

| Extends | stream | (Optional) A reference to another object stream, of which the current object stream is an extension. Both streams are considered part of a collection of object streams (see below). A given collection consists of a set of streams whose Extends links form a directed acyclic graph. |

# A PDF writer determines which objects, if any, to store in object streams.

> **EXAMPLE 1** It can be useful to store objects having common characteristics together, such as "fonts on page 1" or
"Comments for draft #3." These objects are known as a collection.

> **NOTE 4** To avoid a degradation of performance, such as would occur when downloading and
decompressing a large object stream to access a single compressed object, the number of objects in an individual object stream needs to be limited. This can require a group of object streams to be linked as a collection, which can be done by means of the Extends entry in the object stream dictionary.

> **NOTE 5** Extends can also be used when a collection is being updated to include new objects. Rather than
modifying the original object stream, which could entail duplicating much of the stream data, the new objects can be stored in a separate object stream. This is particularly important when adding an update section to a document.
The stream data in an object stream shall contain the following items:

• N pairs of integers separated by white-space, where the first integer in each pair shall represent the object number of a compressed object and the second integer shall represent the byte offset in the decoded stream of that object, relative to the first object stored in the object stream, the offset for which is the value of the stream's First entry. The byte offsets shall be in increasing order. The pairs, themselves, shall also be separated by white-space.

> **NOTE 6** There is no restriction on the order of objects in the object stream; in particular, the objects need
not be stored in object-number order.
• The value of the First entry in the stream dictionary shall be the byte offset in the decoded stream of the first object.


## Page 78

• Following the N pairs of integers, the N objects are stored consecutively. Only the object values are stored in the stream; the obj and endobj keywords shall not be used.

> **NOTE7** (2020) Previous editions of this specification incorrectly stated that white -space was required
| between objects. This document corrects the text to clarify that p | rocessing of each object in an object stream starts at the specified byte offset in the decompressed stream and ends prior to the byte offset of the next object or when the end of stream is encountered. |

> **NOTE 8** A compressed dictionary or array can contain indirect references.
An object in an object stream shall not consist solely of an object reference.

> **EXAMPLE** 2

3 0 R

In an encrypted PDF file (i.e., entire object stream is encrypted), strings occurring anywhere in an object stream shall not be separately encrypted.

# A PDF writer shall store the first object immediately after the last byte offset separated by white-space.

# A PDF processor shall rely on the First entry in the stream dictionary to locate the first obje ct.

An object stream itself, like any stream, shall be an indirect object, and therefore, there shall be an entry for it in a cross-reference table or cross-reference stream (see 7.5.8, "Cross-reference streams"), although there might not be any references to it (of the form 243 0 R).

| The generation number of an object stream an | d of any compressed object shall be zero. If either an |

object stream or a compressed object is deleted and the object number is freed, that object number shall be reused only for an ordinary (uncompressed) object other than an object stream. When new object streams and compressed objects are created, they shall always be assigned new object numbers, not old ones taken from the free list.

> **EXAMPLE 3** The following shows three objects (two fonts and a font descriptor) as they would be represented in a PDF
1.4 or earlier file, along with a cross-reference table.

11 0 obj <</Type /Font /Subtype /TrueType … other entries … /FontDescriptor 12 0 R >> endobj

12 0 obj <</Type /FontDescriptor /Ascent 891 … other entries … /FontFile2 22 0 R >> endobj

13 0 obj <</Type /Font /Subtype /Type0 … other entries … /ToUnicode 10 0 R >> endobj

…


## Page 79

xref 0 32 0000000000 65535 f … cross-reference entries for objects 1 through 10 … 0000001434 00000 n 0000001735 00000 n 0000002155 00000 n … cross-reference entries for objects 14 and on … trailer <</Size 32 /Root … >>

> **NOTE 9** For readability, the object stream has been shown unencoded. In a PDF 2.0 file, Flate encoding
would typically be used to gain the benefits of compression.

> **EXAMPLE 4** The following shows the same objects from the previous example stored in an object stream in a PDF 1.5
file, along with a cross-reference stream.

The cross-reference stream (see 7.5.8, "Cross-reference streams") contains entries for the fonts (objects 11
| and 13) and the descriptor (object 12), which are | compressed objects in an object stream. The first field of, along with a cross-reference table field is the number of the object stream (15), and the third field is the |
| --- | --- |
| position within the sequence of objects in the object stream (0, 1, and 2). The cross | -reference stream also contains a Type 1 entry for the object stream itself. |

| 15 0 obj | %The object stream <</Type /ObjStm /Length 1856 |
| --- | --- |
| /N 3 | %The number of objects in the stream |
| /First 24 | %The byte offset in the decoded stream of the first object %The object numbers and offsets of the objects, %relative to the first are shown on the first line of %the stream (i.e., 11 0 12 547 13 665). >> stream 11 0 12 547 13 665 <</Type /Font /Subtype /TrueType … other keys … /FontDescriptor 12 0 R >> |

<</Type /FontDescriptor /Ascent 891 …other keys… /FontFile2 22 0 R >> <</Type /Font /Subtype /Type0 … other keys … /ToUnicode 10 0 R >> … endstream endobj

| 99 0 obj | %The cross-reference stream <</Type /XRef |
| --- | --- |
| /Index [0 32] | %This section has one subsection with 32 objects |
| /W [1 2 2] | %Each entry has 3 fields: 1, 2 & 2 bytes in width respectively |
| /Filter /ASCIIHexDecode | %For readability in this example /Size 32 … >> |


## Page 80

stream 00 0000 FFFF … cross-references for objects 1 through 10 … 02 000F 0000 02 000F 0001 02 000F 0002 … cross-reference for object 14 …

# 01 BA5E 0000

… endstream endobj

startxref

%%EOF

> **NOTE 10** The number 54321 in Example 4 is the byte offset for object 99 0.

### 7.5.8 Cross-reference streams

#### 7.5.8.1 General

| Beginning with PDF 1.5, cross | -reference information may be stored in a cross | -reference stream instead |

of in a cross-reference table. Cross-reference streams provide the following advantages:

• A more compact representation of cross -reference information • The ability to access compressed objects that are stored in object streams ( see 7.5.7, "Object streams") and to allow new cross-reference entry types to be added in the future.
Cross-reference streams are stream objects (see 7.3.8, "Stream objects"), and contain a dictionary and
| a data stream. Each cross-reference stream contains the information equivalent to the cross | -reference |

table (see 7.5.4, "Cross-reference table") and trailer (7.5.5, "File trailer") for one cross-reference section.

> **EXAMPLE** In this example, the trailer dictionary entries are stored in the stream dictionary, and the cross -reference
table entries are stored as the stream data.

… objects …

| 12 0 obj | %Cross-reference stream |
| --- | --- |
| <</Type /XRef | %Cross-reference stream dictionary /Size … /Root … |

>>

stream …Stream data containing cross-reference information … endstream endobj

… more objects …

startxref byte_offset_of_cross-reference_stream (points to object 12) %%EOF

The value following the startxref keyword shall be the byte offset of the cross-reference stream rather than the xref keyword. For PDF files that use cross-reference streams entirely (that is, PDF files that are not hybrid-reference files; see 7.5.8.4, "Compatibility with applications that do not support compressed reference streams"), the keywords xref and trailer shall no longer be used. Therefore,


## Page 81

with the exception of the startxref address, %%EOF segment and comments, a PDF file may be entirely a sequence of objects.

In linearized PDF files (see F.3, "Linearized PDF document structure"), the document catalog dictionary, the linearization dictionary, and page objects shall not appear in an object stream.

#### 7.5.8.2 Cross-reference stream dictionary

Cross-reference streams shall contain the required entries and may contain the optional entries shown
| in "Table 17 — Additional entries specific to a cross-reference stream dictionary | " in addition to the |
| --- | --- |
| entries common to all streams ("Table 5 — Entries common to all stream dictionaries | ") and trailer |

dictionaries ("Table 15 — Entries in the file trailer dictionary"). Since some of the information in the cross-reference stream is needed by the PDF processor to construct the index that allows indirect references to be resolved, the entries in cross-reference streams shall be subject to the following restrictions:

• The values of all entries shown in "Table 17 — Additional entries specific to a cross-reference stream dictionary" shall be direct objects; indirect references shall not be permitted. For arrays (the Index and W entries), all of their elements shall be direct objects as well. If the stream is encoded, the Filter and DecodeParms entries in "Table 5 — Entries common to all stream dictionaries" shall also be direct objects.
• Other cross-reference stream entries not listed in "Table 17 — Additional entries specific to a cross-reference stream dictionary" may be indirect; in fact, some (such as Root in "Table 15 —
| Entries in the file trailer dictionary | ") shall be indirect. |

• The cross-reference stream shall not be encrypted and strings appearing in the cross -reference
| stream dictionary shall not be encrypted. It shall not have a | Filter entry that specifies a Crypt |

filter (see 7.4.10, "Crypt filter").

Table 17 — Additional entries specific to a cross-reference stream dictionary

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be XRef for a cross-reference stream. |

| Size | integer | (Required) The number one greater than the highest object number used in this section or in any section for which this shall be an update. It shall be equivalent to the Size entry in a trailer dictionary. |

| Index | array | (Optional) An array containing a pair of integers for each subsection in this section. The first integer shall be the first object number in the subsection; the second integer shall be the number of entries in the subsection The array shall be sorted in ascending order by object number. Subsections cannot overlap; an object number shall have no more than one entry in a section. Default value: [0 Size]. |


## Page 82

| Key | Type | Value |

| Prev | integer | (Required, if any cross reference streams are already present in the file) The byte offset from the beginning of the PDF file to the beginning of the previous cross-reference stream. The value is meaningful if the PDF file has more than one cross-reference stream. It is not meaningful in hybrid-reference files; see 7.5.8.4, "Compatibility with applications that do not support compressed reference streams". This entry has the same function as the Prev entry in the trailer dictionary ("Table 15 — Entries in the file trailer dictionary"). |

| W | array | (Required) An array of integers representing the size of the fields in a single cross-reference entry. "Table 18 — Entries in a cross-reference stream" describes the types of entries and their fields. For PDF 1.5, W always contains three integers; the value of each integer shall be the number of bytes (in the decoded stream) of the corresponding field. |

> **EXAMPLE** [1 2 1] means that the fields are one byte, two bytes, and one
byte, respectively.

A value of zero for an element in the W array indicates that the corresponding field shall not be present in the stream, and the default value shall be used, if there is one. A value of zero shall not be used for the second element of the array. If the first element is zero, the type field shall not be present, and shall default to Type 1.
The sum of the items shall be the total length of each entry; it can be used with the Index array to determine the starting position of each subsection.
Different cross-reference streams in a PDF file may use different values for W.

#### 7.5.8.3 Cross-reference stream data

Each entry in a cross-reference stream shall have one or more fields, the first of which designates the entry’s type (see "Table 18 — Entries in a cross-reference stream"). In PDF 1.5 through PDF 2.0, only types 0, 1, and 2 are allowed. Any other value shall be interpreted as a reference to the null object, thus permitting new entry types to be defined in the future.

The fields are written in increasing order of field number; the length of each field shall be determined
| by the corresponding value in the | W entry (see "Table 17 — Additional entries specific to a cross- |

reference stream dictionary"). Fields requiring more than one byte are stored with the high-order byte first.

Table 18 — Entries in a cross-reference stream

| Type | Field | Description |

| 0 | 1 | The type of this entry, which shall be 0. Type 0 entries define the linked list of free objects (corresponding to f entries in a cross-reference table). Default value: 0. |

# 2 The object number of the next free object.


## Page 83

| Type | Field | Description |

# 3 The generation number to use if this object number is used

again. Default value: 0.

| 1 | 1 | The type of this entry, which shall be 1. Type 1 entries define objects that are in use but are not compressed (corresponding to n entries in a cross-reference table). |

# 2 The byte offset of the object, starting from the beginning of

the PDF file. Default value: 0.

# 3 The generation number of the object. Default value: 0.

| 2 | 1 | The type of this entry, which shall be 2. Type 2 entries define compressed objects. |

# 2 The object number of the object stream in which this

object is stored. (The generation number of the object stream shall be implicitly 0.)

# 3 The index of this object within the object stream.

> **NOTE** This index value will be between zero and the value of
N minus 1 from the associated object stream dictionary.

Like any stream, a cross-reference stream shall be an indirect object. Therefore, an entry for it shall exist in either a cross-reference stream (usually itself) or in a cross-reference table (in hybridreference files; see 7.5.8.4, "Compatibility with applications that do not support compressed reference streams").

#### 7.5.8.4 Compatibility with applications that do not support compressed

reference streams

A hybrid-reference PDF file is readable by PDF processors designed only to support versions of PDF before PDF 1.5. Such a PDF file contains objects referenced by standard cross-reference tables in addition to objects in object streams that are referenced by cross-reference streams.

In these PDF files, the trailer dictionary may contain, in addition to the entry for trailers shown in "Table 15 — Entries in the file trailer dictionary", an entry, as shown in “Table 19 — Additional entries in a hybrid-reference file’s trailer dictionary”.

Table 19 — Additional entries in a hybrid-reference file’s trailer dictionary

| Key | Type | Value |

| XRefStm | integer | (Optional) The byte offset in the decoded stream from the beginning of the PDF file of a cross-reference stream. |

When a PDF reader opens a hybrid-reference PDF file, objects with entries in cross-reference streams are not hidden. When the PDF reader searches for an object, if an entry is not found in any given


## Page 84

standard cross-reference section, the search shall proceed to a cross-reference stream specified by the XRefStm entry before looking in the previous cross-reference section (the Prev entry in the trailer).

> **NOTE** Hidden objects, therefore, have two cross-reference entries. One is in the cross-reference stream.
The other is a free entry in some previous section, typically the section referenced by the Prev entry.

# A PDF reader shall look in the cross-reference stream first, find the object there, and shall ignore the

free entry in the previous section.

> **EXAMPLE** In this example, an ASCIIHexDecode filter is specified to make the format and contents of the cross-
| reference  stream  readable. | This  example  shows  a  hybrid-reference | PDF | file  containing  a main  cross- |
| --- | --- | --- | --- |
| reference section and an update cross-reference section with an | XRefStm entry that points to a cross- reference stream (object 11), which in turn has references to an object stream (object 2). |  |  |

In this example, the catalog dictionary (object 1) contains an indirect reference (3 0 R) to the root of the structure tree. The search for the object starts at the update cross-reference table, which has no objects in it. The search proceeds depending on the version of the PDF reader.

One choice for a PDF reader designed only to support versions of PDF before PDF 1.5 is to continue the search by following the Prev pointer to the main cross-reference table. That table defines object 3 as a free object, which is treated as the null object. Therefore, the entry is considered missing, and the document has no structure tree.

Another choice for a PDF reader, is to continue the search by following the XRefStm pointer to the crossreference stream (object 11). It defines object 3 as a compressed object, stored at index 0 in the object stream (2 0 obj). Therefore, the document has a structure tree.

| 1 0 obj | %The document root, at offset 23. << /Type /Catalog /StructTreeRoot 3 0 R … >> endobj |

12 0 obj … endobj … 99 0 obj … endobj

%The main xref section, at offset 2664 is next with entries for objects 0-99.
%Objects 2 through 11 are marked free and objects 12, 13 and 99 are marked in use. xref 0 100 0000000002 65535 f 0000000023 00000 n 0000000003 65535 f 0000000004 65535 f 0000000005 65535 f 0000000006 65535 f 0000000007 65535 f 0000000008 65535 f 0000000009 65535 f 0000000010 65535 f 0000000011 65535 f 0000000000 65535 f 0000000045 00000 n 0000000179 00000 n … cross-reference entries for objects 14 through 98 … 0000002201 00000 n trailer


## Page 85

<< /Size 100 /Root 1 0 R /ID … >> %The main xref section starts at offset 2664. startxref

%%EOF

| 2 0 obj | %The object stream, at offset 3722 |

<< /Length …
| /N 8 | %This stream contains 8 objects. |
| --- | --- |
| /First 47 | %The stream-offset of the first object |

>>

stream 3 0 4 50 5 72 … the numbers and stream-offsets of the remaining 5 objects followed by dictionary objects 3-5 … << /Type /StructTreeRoot /K 4 0 R /RoleMap 5 0 R /ClassMap 6 0 R /ParentTree 7 0 R /ParentTreeNextKey 8 >> << /S /Workbook /P 8 0 R /K 9 0 R >> << /Workbook /Div /Worksheet /Sect /TextBox /Figure /Shape /Figure >> … definitions for objects 6 through 10 … endstream endobj

| 11 0 obj | %The cross-reference stream, at offset 4899 |

<< /Type /XRef
| /Index [2 10] | %This stream contains entries for objects 2 through 11 /Size 100 |
| --- | --- |
| /W [1 2 1] | %The byte-widths of each field |
| /Filter /ASCIIHexDecode | %For readability only … |

>>

stream 01 0E8A 00 02 0002 00 02 0002 01 02 0002 02 02 0002 03 02 0002 04 02 0002 05 02 0002 06 02 0002 07 01 1323 00 endstream endobj

%The entries above are for: object 2 (0x0E8A = 3722), object 3 (in object stream 2, index 0), %object 4 (in object stream 2, index 1) … object 10 (in object stream 2, index 7), %object 11 (0x1323 = 4899).

%The update xref section starting at offset 5640. There are no entries in this section. xref 0 0 trailer


## Page 86

<< /Size 100
| /Prev 2664 | %Offset of previous xref section /XRefStm 4899 /Root 1 0 R /ID … |

>>

startxref

%%EOF

The previous example illustrates several other points:

• The object stream is unencoded and the cross-reference stream uses an ASCII hexadecimal encoding for clarity. In practice, both streams should be Flate-encoded. PDF comments shall not be included in a cross-reference table or in cross-reference streams.
• The hidden objects, 2 through 11, are numbered consecutively. In practice, hidden objects and other free items in a cross-reference table need not be linked in ascending order until the end.
• The update cross-reference table need not contain any entries. A PDF writer that uses the hybridreference format creates the main cross-reference table, the update cross-reference table, and the cross-reference stream at the same time. Objects 12 and 13, for example, are not compressed.
They might have entries in the update table. Since objects 2 and 11, the object stream and the cross-reference stream, are not compressed, they might also be defined in the update table. Since they are part of the hidden section, however, it makes sense to define them in the cross-reference stream.
• The update cross-reference section shall appear at the end of the PDF file, but otherwise, there are no ordering restrictions on any of the objects or on the main cross-reference section. However, a PDF file that uses both the hybrid-reference format and the linearized format has ordering requirements (see Annex F, "Linearized PDF" and Annex G, "Linearized PDF access strategies").

## 7.6 Encryption

### 7.6.1 General

# A PDF file can be encrypted (PDF 1.1) to protect its contents from unauthorised access.

### 7.6.2 Application of encryption

Encryption applies to all strings and streams in the document's PDF file, with the following exceptions:

• The values for the ID entry in the trailer • Any strings in an Encrypt dictionary • Any strings that are inside streams such as content streams and compressed object streams, which themselves are encrypted • Any hexadecimal strings representing the value of the Contents key in a Signature dictionary Encryption is not applied to other object types such as integers and boolean values, which are used primarily to convey information about the document's structure rather than its contents. Leaving these values unencrypted allows random access to the objects within a document, whereas encrypting the strings and streams protects the document's contents.

When a PDF stream object (see 7.3.8, "Stream objects") refers to an external file, the stream’s contents shall not be encrypted, since they are not part of the PDF file itself. However, if the contents of the stream are embedded within the PDF file (see 7.11.4, "Embedded file streams"), they shall be


## Page 87

encrypted like any other stream in the file. Beginning with PDF 1.5, embedded files can be encrypted in an otherwise unencrypted document (see 7.6.6, "Crypt filters").

Encryption-related information shall be stored in a document’s encryption dictionary, which shall be
| the value of the Encrypt entry in the document’s trailer dictionary (see | "Table 15 — Entries in the file |

trailer dictionary"). The absence of this entry from the trailer dictionary means that a PDF processor shall consider the document to be not encrypted. The entries shown in "Table 20 — Entries common to all encryption dictionaries" are common to all encryption dictionaries. For document with multiple versions the Encrypt entry should be identical in all copies of the trailer.

The encryption dictionary’s Filter entry identifies the file’s security handler, a software module that implements various aspects of the encryption process and controls access to the contents of the encrypted document. PDF specifies a standard password-based security handler that all PDF processors shall support, but PDF processors can optionally provide additional security handlers of their own.

The SubFilter entry specifies the syntax of the encryption dictionary contents. It allows interoperability between handlers; that is, a document can be decrypted by a handler other than the preferred one (the Filter entry) if they both support the format specified by SubFilter.

The V entry, in specifying which algorithm to use, determines the length of the file encryption key, on which the encryption (and decryption) of data in a PDF file shall be based. For V values 2 and 3, the Length entry specifies the exact length of the file encryption key. In PDF 1.5, a value of 4 for V permits the security handler to use its own encryption and decryption algorithms and to specify crypt filters with a key length of 128 bits (16 bytes) to use on specific streams (see 7.6.6, "Crypt filters"). A value of 5 for V permits the specification of crypt filters with a file encryption key length of 256 bits (32 bytes).
Values less than 5 for the V entry are deprecated in PDF 2.0.

The remaining contents of the encryption dictionary shall be determined by the security handler and may vary from one handler to another. Entries for the standard security handler are described in 7.6.4, "Standard security handler". Entries for public-key security handlers are described in 7.6.5, "Public-key security handlers".

Table 20 — Entries common to all encryption dictionaries

| Key | Type | Value |

| Filter | name | (Required) The name of the preferred security handler for this document. It shall be the name of the security handler that was used to encrypt the document. If SubFilter is not present, only this security handler shall be used when opening the document. If it is present, a PDF processor can use any security handler that implements the format specified by SubFilter. Standard shall be the name of the built-in password-based security handler. Names for other security handlers may be registered by using the procedure described in Annex E, "Extending PDF". |

| SubFilter | name | (Optional; PDF 1.3) A name that completely specifies the format and interpretation of the contents of the encryption dictionary. It allows security handlers other than the one specified by Filter to decrypt the document. If this entry is absent, other security handlers shall not decrypt the document. |


## Page 88

| Key | Type | Value |

| V | integer | (Required) A code specifying the algorithm to be used in encrypting and decrypting the document: |

# 0 An algorithm that is undocumented. This value shall not be used.

| 1 | (Deprecated in PDF 2.0) Indicates the use of 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms" (deprecated in PDF 2.0) with a file encryption key length of 40 bits; see below. |
| --- | --- |
| 2 | (PDF 1.4; deprecated in PDF 2.0) Indicates the use of 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms" (deprecated in PDF 2.0) but permitting file encryption key lengths greater than 40 bits. |
| 3 | (PDF 1.4; deprecated in PDF 2.0) An unpublished algorithm that permits file encryption key lengths ranging from 40 to 128 bits. This value shall not appear in a conforming PDF file. |
| 4 | (PDF 1.5; deprecated in PDF 2.0) The security handler defines the use of encryption and decryption in the document, using the rules specified by the CF, StmF, and StrF entries using 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms" (deprecated in PDF 2.0) with a file encryption key length of 128 bits. |
| 5 | (PDF 2.0) The security handler defines the use of encryption and decryption in the document, using the rules specified by the CF, StmF, StrF and EFF entries using 7.6.3.3, "Algorithm 1.A: Encryption of data using the AES algorithms" with a file encryption key length of 256 bits. |

| Length | integer | (Optional; PDF 1.4; only if V is 2 or 3; deprecated in PDF 2.0) The length of the file encryption key, in bits. The value shall be a multiple of 8, in the range 40 to 128. Default value: 40. |

| CF | dictionary | (Optional; meaningful only when the value of V is 4 (PDF 1.5) or 5 (PDF 2.0)) A dictionary whose keys shall be crypt filter names and whose values shall be the corresponding crypt filter dictionaries (see "Table 25 — Entries common to all crypt filter dictionaries"). Every crypt filter used in the document shall have an entry in this dictionary, except for the standard crypt filter names (see "Table 26 — Standard crypt filter names"). Any keys in the CF dictionary that are listed in "Table 26 — Standard crypt filter names" shall be ignored by a PDF processor. Instead, the PDF processor shall use properties of the respective standard crypt filters. |

| StmF | name | (Optional; meaningful only when the value of V is 4 (PDF 1.5) or 5 (PDF 2.0)) The name of the crypt filter that shall be used by default when decrypting streams. The name shall be a key in the CF dictionary or a standard crypt filter name specified in "Table 26 — Standard crypt filter names". All streams in the document, except for cross-reference streams (see 7.5.8, "Cross-reference streams") or streams that have a Crypt entry in their Filter array (see "Table 6 — Standard filters"), shall be decrypted by the security handler, using this crypt filter. Default value: Identity. |

| StrF | name | (Optional; meaningful only when the value of V is 4 (PDF 1.5) or 5 (PDF 2.0)) The name of the crypt filter that shall be used when decrypting all strings in the document. The name shall be a key in the CF dictionary or a standard crypt filter name specified in "Table 26 — Standard crypt filter names". Default value: Identity. |


## Page 89

| Key | Type | Value |

| EFF | name | (Optional; meaningful only when the value of V is 4 (PDF 1.6) or 5 (PDF 2.0)) The name of the crypt filter that shall be used when encrypting embedded file streams that do not have their own crypt filter specifier; it shall correspond to a key in the CF dictionary or a standard crypt filter name specified in "Table 26 — Standard crypt filter names". This entry shall be provided by the security handler. PDF writers shall respect this value when encrypting embedded files, except for embedded file streams that have their own crypt filter specifier. If this entry is not present, and the embedded file stream does not contain a crypt filter specifier, the stream shall be encrypted using the default stream crypt filter specified by StmF. |

Unlike strings within the body of the document, those in the encryption dictionary shall be direct objects. The values of the keys defined in "Table 20 — Entries common to all encryption dictionaries" shall not be encrypted. However, a security handler may choose to encrypt any objects that are private to itself.

> **NOTE** PDF writers have two choices if the encryption methods and syntax provided by PDF are not
sufficient for their needs: they can provide an alternative security handler or they can encrypt whole PDF files themselves, not making use of PDF security.

### 7.6.3 General encryption algorithm

#### 7.6.3.1 General

One of the following algorithms shall be used when encrypting data in a PDF file:

• A proprietary encryption algorithm known as RC4. RC4 is a symmetric stream cipher: the same algorithm shall be used for both encryption and decryption, and the algorithm does not change the length of the data. RC4 is a copyrighted, proprietary algorithm of RSA Security. The use of this algorithm in PDF 2.0 is deprecated.

> **NOTE 1** The name RC4™ is a registered trademark of RSA Security Inc. and cannot be used by third
parties creating implementations of the algorithm. Proprietary implementations of the RC4 encryption algorithm are available under license from RSA Security Inc. For licensing information, contact: RSA Security Inc. 2955 Campus Drive, Suite 400, San Mateo, CA 94403- 2507, USA, or http://www.rsasecurity.com/.
• The AES (Advanced Encryption Standard) algorithm (beginning with PDF 1.6). AES is a symmetric block cipher: the same algorithm shall be used for both encryption and decryption, and the length of the data when encrypted is rounded up to a multiple of the block size, which is fixed to always be 16 bytes, as specified in FIPS 197, Advanced Encryption Standard (AES).
Strings and streams encrypted with AES shall use a padding scheme that is described in Internet RFC 8018. For an original message length of M, the pad shall consist of 16 - (M modulo 16) bytes whose value shall also be 16 - (M modulo 16).

> **EXAMPLE** A 9-byte message has a pad of 7 bytes, each with the value 0x07. The pad can be unambiguously removed to
determine the original message length when decrypting. Note that the pad is present when M is evenly divisible by 16; it contains 16 bytes of 0x10.

> **NOTE 2** Since Encrypt versions 1 to 4 are deprecated in PDF 2.0, this has the effect of implicitly
deprecating the use of MD5 for key generation purposes.


## Page 90

For Encrypt versions 1-4, as defined by the V key of the encryption dictionary, PDF’s standard encryption methods also make use of the MD5 message-digest algorithm for key generation purposes (described in Internet RFC 1321). Encrypt version 5 does not use MD5.

The encryption of data in a PDF file shall be based on the use of an encryption key computed by the security handler. Different security handlers compute the encryption key using their own mechanisms.
Encryption of data uses one of two algorithms. When the value of the V key of the encryption dictionary is 1, 2, 3 or 4 (PDF 1.7), 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms" (deprecated in PDF 2.0) shall be used. When the value of the V key of the encryption dictionary is 5 (PDF 2.0) 7.6.3.3, "Algorithm 1.A: Encryption of data using the AES algorithms" shall be used. The difference is that Algorithm 1.A uses the starting key directly and does not modify the file encryption key at all. Algorithm 1.A is used only with the AES algorithm and 256-bit file encryption keys.

Algorithms in 7.6, "Encryption" are uniquely numbered within this subclause in a manner that maintains compatibility with previous documentation.

#### 7.6.3.2 Algorithm 1: Encryption of data using the RC4 or AES algorithms

This algorithm is deprecated in PDF 2.0.

a) Obtain the object number and generation number from the object identifier of the string or stream to be
| encrypted (see | 7.3.10, "Indirect objects | "). If the string is a direct object, use the identifier of the indirect |
| --- | --- | --- |
| object containing it | . |  |

b) For all strings and streams without crypt filter specifier; tre ating the object number and generation
| number as binary integers, extend the original | n-byte file encryption key to | n + 5 bytes by appending the |  |
| --- | --- | --- | --- |
| low-order 3 bytes of the object number and the low | -order 2 bytes of the generation number in that |  |  |
| order, low-order byte first. (n is 5 unless the value of | V in the encryption dictionary is greater than 1, in |  |  |
| which case | n is the value of | Length divided by 8.) | For example, for object number 258 and generation |
| number 7, the hexadecimal values 0x02 0x01 0x00 0x07 0x00 would be appended to the | file encryption |  |  |

key.
| If using the AES algorithm, extend the | file encryption key an additional 4 bytes by adding the value |

"sAlT", which corresponds to the hexadecimal values 0x73, 0x41, 0x6C, 0x54. (This addition is done for backward compatibility and is not intended to provide additional security.)

c) Initialise the MD5 hash function and pass the result of step (b) as input to t his function.
d) Use the first ( n + 5) bytes, up to a maximum of 16, of the output from the MD5 hash as the key for the
RC4 or AES symmetric key algorithms, along with the string or stream data to be encrypted.
| If using the AES algorithm, the Cipher Block Cha | ining (CBC) mode, which requires an initialization |
| --- | --- |
| vector, is used. The block size parameter is set to 16 bytes, and the initialization vector is a 16 | -byte |

random number that is stored as the first 16 bytes of the encrypted stream or string.

| The output is | the encrypted data to be stored in the PDF file. |

#### 7.6.3.3 Algorithm 1.A: Encryption of data using the AES algorithms

a) Use the 32 -byte file encryption key for the AES -256 symmetric key algorithm, along with the string or
stream data to be encrypted.
| Use the AES algor | ithm in Cipher Block Chaining (CBC) mode, | which requires an initialization |
| --- | --- | --- |
| vector. The block size parameter is set to 16 bytes, and the initialization vector is a 16 | -byte random |  |


## Page 91

number that is stored as the first 16 bytes of the encrypted stream or string.

The output is the encrypted data to be stored in the PDF file.

Stream data shall be encrypted after applying all stream encoding filters and shall be decrypted before applying any stream decoding filters. The number of bytes to be encrypted or decrypted shall be given by the Length entry in the stream dictionary. Decryption of strings (other than those in the encryption dictionary) shall be done after escape-sequence processing and hexadecimal decoding as appropriate to the string representation described in 7.3.4, "String objects".

### 7.6.4 Standard security handler

#### 7.6.4.1 General

PDF’s standard security handler shall allow access permissions and up to two passwords to be specified for a document: an owner password and a user password. An application’s decision to encrypt a document shall be based on whether the user creating the document specifies any passwords or access restrictions.

> **EXAMPLE** A PDF processor might have a security settings dialogue box that the user can invoke before saving the file.

If passwords or access restrictions are specified, the document shall be encrypted, and the permissions and information required to validate the passwords shall be stored in the encryption dictionary.
Documents in which only file attachments are encrypted shall use the same user and owner passwords.

> **NOTE 1** A PDF processor can also create an encrypted document without any user interaction if it has
some other source of information about what passwords and permissions to use.
If a user attempts to open an encrypted document that has a user password, the PDF reader shall first try to authenticate the encrypted document using the padding string defined in 7.6.4.3, "File encryption key algorithm" (default user password):

• If this authentication attempt is successful, the PDF reader may open, decrypt, render and otherwise provide access to the document.
• If this authentication attempt fails, the interactive PDF processor should prompt for a password.
Correctly supplying either password (owner or user password) should enable the user to gain access to the document.
Whether additional operations shall be allowed on a decrypted document depends on which password (if any) was supplied when the document was opened and on any access restrictions that were specified when the document was created:

• Opening the document with the correct owner password should allow full (owner) access to the document. This unlimited access includes the ability to change the document’s passwords and access permissions.
• Opening the document with the correct user password (or opening a document with the default password) should allow additional operations to be performed according to the user access permissions specified in the document’s encryption dictionary.
Access permissions shall be specified in the form of flags corresponding to the various operations and the set of operations to which they correspond shall depend on the security handler’s revision number (also stored in the encryption dictionary). If the security handler’s revision number is 2 or greater, the


## Page 92

operations to which user access can be controlled shall be as follows:

• Modifying the document’s contents • Copying or otherwise extracting text, images and graphics from the document.
• Adding or modifying text annotations (see 12.5.6.4, "Text annotations") and interactive form fields (see 12.7, "Forms").
• Printing the document If the security handler’s revision number is 3 or greater, user access to the following operations shall be controlled more selectively:

• Filling in forms (that is, filling in existing interactive form fields) and signing the document (which amounts to filling in existing signature fields, a type of interactive form field).
• Assembling the document: inserting, rotating, or deleting pages and creating navigation elements such as document outline items or thumbnail images (see 12.3, "Document-level navigation").
• Printing to a representation from which a faithful digital copy of the PDF content could be generated. Disallowing such printing may result in degradation of output quality.
In addition, security handlers of revisions 3 and greater shall enable the extraction of text, images and graphics (in support of accessibility, or for other purposes) to be controlled separately.

If a security handler of revision 4 or 5 is specified, the standard security handler shall support crypt filters (see 7.6.6, "Crypt filters"). The support shall be limited to the Identity crypt filter (see "Table 26 — Standard crypt filter names") and crypt filters named StdCF whose dictionaries contain an AuthEvent value of DocOpen. For revision 4, the filter CFM value shall be V2 (RC4) or AESV2 (AES- 128). For revision 6, the filter CFM value shall be AESV3 (AES-256). Public-Key security handlers in this case shall use crypt filters named DefaultCryptFilter when all document content is encrypted, and
| shall use crypt filters named | DefEmbeddedFile when file attachments only are encrypted in place of |
| --- | --- |
| StdCF name. This nomenclature shall not be used as | an indicator of the type of the security handler or |

encryption. Use of security handler revisions 1, 2, 3, 4 and 5 is deprecated in PDF 2.0.

| Once the document has been opened and decrypted s | uccessfully, a PDF reader technically has access to |

the entire contents of the document. There is nothing inherent in PDF encryption that enforces the document permissions specified in the encryption dictionary. PDF readers shall respect the intent of the document creator by restricting user access to an encrypted PDF file according to the permissions contained in the file.

> **NOTE 2** PDF 1.5 introduces a set of access permissions that do not require the document to be encrypted
(see 12.8.6, "Permissions"). This enables limited access to a document when a user is not be able to respond to a prompt for a password. For example, there can be non-interactive PDF readers that do not have a person running them such as printing off-line or on a server.
All passwords for revision 6 shall be based on Unicode. Preprocessing of a user-provided password consists first of normalizing its representation by applying the "SASLPrep" profile (Internet RFC 4013) of the "stringprep" algorithm (Internet RFC 3454) to the supplied password using the Normalize and BiDi options. Next, the password string shall be converted to UTF-8 encoding, and then truncated to the first 127 bytes if the string is longer than 127 bytes (see 7.6.4.3.3, "Algorithm 2.A: Retrieving the file encryption key from an encrypted document in order to decrypt it (revision 6 and later)", steps (a, b)).


## Page 93

> **NOTE 3** The use of the “SASLPrep” profile (Internet RFC 4013) and the “stringprep” algorithm (Internet
RFC 3454) places limitations on the set of Unicode characters which can be used in PDF passwords.

#### 7.6.4.2 Standard encryption dictionary

"Table 21 — Additional encryption dictionary entries for the standard security handler" shows the encryption dictionary entries for the standard security handler (in addition to those in "Table 20 — Entries common to all encryption dictionaries").

Table 21 — Additional encryption dictionary entries for the standard security handler

| Key | Type | Value |

| R | integer | (Required) A number specifying which revision of the standard security handler shall be used to interpret this dictionary: |
| --- | --- | --- |
| 2 | (Deprecated in PDF 2.0) if the document is encrypted with a V value less than 2 (see "Table 20 — Entries common to all encryption dictionaries") and does not have any of the access permissions set to 0 (by means of the P entry, below) that are designated "Security handlers of revision 3 or greater" in "Table 22 — Standard security handler user access permissions". |  |
| 3 | (Deprecated in PDF 2.0) if the document is encrypted with a V value of 2 or 3, or has any "Security handlers of revision 3 or greater" access permissions set to 0. |  |
| 4 | (Deprecated in PDF 2.0) if the document is encrypted with a V value of 4. |  |
| 5 | (PDF 2.0; deprecated in PDF 2.0) Shall not be used. This value was used by a deprecated proprietary Adobe extension. |  |
| 6 | (PDF 2.0) if the document is encrypted with a V value of 5. |  |

| O | byte | (Required) A byte string, 32 bytes long if the value of R is 4 or less and 48 |
| --- | --- | --- |
| string | bytes long if the value of R is 6, based on both the owne r and user passwords, that shall be used in computing the file encryption key and in determining whether a valid owner password was entered. For more information, see 7.6.4.3, "File encryption key algorithm" and 7.6.4.4, "Password algorithms". |  |

| U | byte | (Required) A byte string, 32 bytes long if the value of R is 4 or less and 48 |
| --- | --- | --- |
| string | bytes long if the value of R is 6, based on the owner and user password, that shall be used in determining whether to prompt the user for a password and, if so, whether a valid user or owner password was entered. For more information, see 7.6.4.4, "Password algorithms". |  |

| OE | byte | (Required if R is 6 (PDF 2.0)) A 32-byte string, based on the owner and user |
| --- | --- | --- |
| string | password, that shall be used in computing the file encryption key. For more information, see 7.6.4.4, "Password algorithms". |  |

| UE | byte | (Required if R is 6 (PDF 2.0)) A 32-byte string, based on the user password, |
| --- | --- | --- |
| string | that shall be used in computing the file encryption key. For more information, see 7.6.4.4, "Password algorithms". |  |

| P | integer | (Required) A set of flags specifying which operations shall be permitted when the document is opened with user access (see "Table 22 — Standard security handler user access permissions"). |


## Page 94

| Key | Type | Value |

| Perms | byte | (Required if R is 6 (PDF 2.0)) A 16-byte string, encrypted with the file |
| --- | --- | --- |
| string | encryption key, that contains an encrypted copy of the permissions flags. For more information, see 7.6.4.4, "Password algorithms". |  |

| EncryptMetadata | boolean | (Optional; meaningful only when the value of V is 4 (PDF 1.5) or 5 (PDF 2.0)) Indicates whether the document-level metadata stream (see 14.3.2, "Metadata streams") shall be encrypted. Default value: true. |

The values of the O and U entries in this dictionary shall be used to determine whether a password entered when the document is opened is the correct owner password, user password, or neither.

The value of the P entry shall be interpreted as an unsigned 32-bit quantity containing a set of flags specifying which access permissions shall be granted when the document is opened with user access.
"Table 22 — Standard security handler user access permissions" shows the meanings of these flags. Bit positions within the flag word shall be numbered from 1 (low-order) to 32 (high-order). A 1 bit in any position shall enable the corresponding access permission. Which bits shall be meaningful, and in some cases how they shall be interpreted, shall depend on the security handler’s revision number (specified in the encryption dictionary’s R entry).

PDF readers shall ignore all flags other than those at bit positions 3, 4, 5, 6, 9, 10, 11, and 12.

> **NOTE** PDF integer objects can be interpreted as binary values in a signed twos-complement form. Since
all the reserved high-order flag bits in the encryption dictionary’s P value are required to be 1, the integer value P is always specified as a negative integer. For example, assuming revision 2 of the security handler, the value -44 permits printing and copying but disallows modifying the contents and annotations.

Table 22 — Standard security handler user access permissions

Bit position Meaning

| 1 - 2 | Reserved. Must be zero (0). |

| 3 | (Security handlers of revision 2) Print the document. (Security handlers of revision 3 or greater) Print the document (possibly not at the highest quality level, depending on whether bit 12 is also set). |

# 4 Modify the contents of the document by operations other than those controlled by bits 6, 9,

and 11.

# 5 Copy or otherwise extract text and graphics from the document. However, for the limited

purpose of providing this content to assistive technology, a PDF reader should behave as if this bit was set to 1.

> **NOTE** For accessibility, ISO 32000-1 had this option restricted by bit 10, but that exception has been
deprecated in PDF 2.0.

# 6 Add or modify text annotations, fill in interactive form fields, and, if bit 4 is also set, create or

modify interactive form fields (including signature fields).

| 7 - 8 | Reserved. Must be 1. |


## Page 95

Bit position Meaning

| 9 | (Security handlers of revision 3 or greater) Fill in existing interactive form fields (including signature fields), even if bit 6 is clear. |

# 10 Not used. This bit was previously used to determine whether content could be extracted for

the purposes of accessibility, however, that restriction has been deprecated in PDF 2.0. PDF readers shall ignore this bit and PDF writers shall always set this bit to 1 to ensure compatibility with PDF readers following earlier specifications.

| 11 | (Security handlers of revision 3 or greater) Assemble the document (insert, rotate, or delete pages and create document outline items or thumbnail images), even if bit 4 is clear. |

| 12 | (Security handlers of revision 3 or greater) Print the document to a representation from which a faithful digital copy of the PDF content could be generated, based on an implementation- dependent algorithm. When this bit is clear (and bit 3 is set), printing shall be limited to a low- level representation of the appearance, possibly of degraded quality. |

| 13 - 32 | (Security handlers of revision 3 or greater) Reserved. Must be 1. |

> **NOTE** The above table was re-titled and corrected in this document (2020).

#### 7.6.4.3 File encryption key algorithm

##### 7.6.4.3.1 General

As noted earlier, one function of a security handler is to generate a file encryption key for use in encrypting and decrypting the contents of a document. Given a password string, the standard security handler computes a file encryption key. For revision 4 and earlier, the algorithm is as shown in 7.6.4.3.2, "Algorithm 2: Computing a file encryption key in order to encrypt a document (revision 4 and earlier)" and for revision 6, the algorithm is as shown in 7.6.4.3.3, "Algorithm 2.A: Retrieving the file encryption key from an encrypted document in order to decrypt it (revision 6 and later)".

##### 7.6.4.3.2 Algorithm 2: Computing a file encryption key in order to encrypt a

document (revision 4 and earlier)

This algorithm is deprecated in PDF 2.0.

a) The password string is generated from host system codepage characters (or system scripts) by first
| converting the string to | PDFDocEncoding | . If the input is Unicode, first convert to a codepage encoding, |  |
| --- | --- | --- | --- |
| and then to | PDFDocEncoding | for backward compatibility | . Pad or truncate the resulting password string |

to exactly 32 bytes. If the password string is more than 32 bytes long, use only its first 32 bytes; if it is
| less than 32 bytes long, pad it by appending the required number of additional bytes from the begi | nning |

of the following padding string: <28 BF 4E 5E 4E 75 8A 41 64 00 4E 56 FF FA 01 08 2E 2E 00 B6 D0 68 3E 80 2F 0C A9 FE 64 53 69 7A>

That is, if the password string is n bytes long, append the first 32 - n bytes of the padding string to the end of the password string. If the password string is empty (zero-length), meaning there is no user password, substitute the entire padding string in its place.

b) Initialise the MD5 hash function and pass the result of step (a) as input to this function.
c) Pass the value of the encryption dictionary’s O entry to the MD5 hash function. ( 7.6.4.4.2, "Algorithm 3:


## Page 96

Computing the encryption dictionary’s O-entry value (revision 4 and earlier)" shows how the O value is computed.)
d) Convert the integer value of the P entry to a 32 -bit unsigned binary number and pass these bytes to the
| MD5 hash function, low | -order byte first. |

e) Pass the first element of the file’s file identifier array (the value of the ID entry in the document’s trailer
| dictionary; see | "Table 15 — Entries in the file trailer dictionary | ") to the MD5 hash function. |

f) (Security handlers of revision 4 or greater ) If document metadata is not being encrypted, pass 4 bytes
with the value 0xFFFFFFFF to the MD5 hash function.

> **NOTE 1** This provision pertains only to document -level XMP metadata.
g) Finish the hash.
h) (Security handlers of revision 3 or greater ) Do the following 50 times: Take the output from the previous
| MD5 hash and pass the first n bytes of the output as input into a new MD5 hash, where | n is the number |  |
| --- | --- | --- |
| of bytes of the | file encryption key as defined by the value of the encryption dictionary’s | Length entry. |

i) Set the file encryption key to the first n bytes of the output from the final MD5 hash, where n shall always
be 5 for security handlers of revision 2 but, for security handlers of revision 3 or greater, shall depend on
| the value of the encry | ption dictionary’s | Length entry. |

> **NOTE 2** The first element of the ID array, as used in 7.6.4.3.2, "Algorithm 2: Computing a file encryption
key in order to encrypt a document (revision 4 and earlier) ", step e, generally remains unchanged across revisions of a given document. However, since this is not guaranteed, use of the ID in computation of the file encryption key, as required when using 7.6.4.3.3, "Algorithm 2.A: Retrieving the file encryption key from an encrypted document in order to decrypt it (revision 6 and later)Algorithm 2: Computing a file encryption key in order to encrypt a document (revision 4 and earlier)", can complicate updates to the document. For this reason,
| security handlers are encouraged to use Algorithm 2.A or higher, which do not use the | ID in file encryption key computation. This algorithm, when applied to the user password string, produces the file encryption key used to encrypt or decrypt string and stream data according to 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms". Parts of this algorithm are also used in the algorithms described below. |

##### 7.6.4.3.3 Algorithm 2.A: Retrieving the file encryption key from an encrypted

document in order to decrypt it (revision 6 and later)

To understand the algorithm below, it is necessary to treat the 48-bytes of the O and U strings in the Encrypt dictionary as made up of three sections, as described in Algorithms 8 and 9. The first 32 bytes are a hash value (explained below). The next 8 bytes are called the Validation Salt. The final 8 bytes are called the Key Salt. Whenever UTF-8 password is used below, steps (a) and (b) are to be applied to the relevant password string to generate the UTF-8 password.

a) The UTF -8 password string shall be genera ted from Unicode input by processing the input string with
| the SASLprep ( | Internet | RFC 4013 | ) profile of stringprep ( | Internet | RFC 3454) using the Normalize and BiDi |
| --- | --- | --- | --- | --- | --- |
| options, and then converting to a UTF | -8 representation. |  |  |  |  |

b) Truncate the UTF -8 representation to 127 bytes if it is longer than 127 bytes.
c) Test the password against the owner key by computing a hash using algorithm 2.B with an input string
| consisting of the UTF | -8 password concatenated with the 8 bytes of owner Validation Salt, concatenated |
| --- | --- |
| with the 48-byte U string. If the 32 | -byte result matches the first 32 bytes of the O string, this is the owner |

password.
d) Compute an intermediate owner key by computing a hash using algorithm 2.B with an input string
| consisting of the UTF | -8 owner password concatenat | ed with the 8 bytes of owner Key Salt, concatenated |  |  |
| --- | --- | --- | --- | --- |
| with the 48 | -byte U string. The 32 | -byte result is the key used to decrypt the 32 | -byte OE string using AES | - |


## Page 97

256 in CBC mode with no padding and an initialization vector of zero. The 32-byte result is the file encryption key.
e) Compute an intermediate user key by computing a hash using algorithm 2.B with an input string
| consisting of the UTF | -8 user password concatenated with the 8 bytes of user Key Salt. The 32 | -byte result |
| --- | --- | --- |
| is the key used to decrypt the 32 | -byte UE string using AES | -256 in CBC mode with no padding and an |
| initialization vector of zero. The 32 | -byte result is the file encryption key. |  |

f) Decrypt the 16 -byte Perms string using AES -256 in ECB mode with an initialization vector of zero and
| the file encrypti | on key as the key. Verify that bytes 9 | -11 of the result are the characters | "a", "d", "b". Bytes |
| --- | --- | --- | --- |
| 0-3 of the decrypted | Perms entry, treated as a little | -endian integer, are the user permissions. They | shall |
| match the value in the | P key. |  |  |

##### 7.6.4.3.4 Algorithm 2.B: Computing a hash (revision 6 and later)

| Take the SHA-256 hash of the original input to the algorithm and name the resulting 32 byte | s, K. |
| --- | --- |
| Perform the following steps (a) | -(d) 64 times: |

a) Make a new string, K1, consisting of 64 repetitions of the sequence: input password, K, the 48 -byte user
key. The 48 byte user key is only used when checking the owner password or creating the owner key. If
| checking the user password or creating the user key, | K1 is the concatenation of the input password and |

K.
b) Encrypt K1 with the AES -128 (CBC, no padding) algorithm, using the first 16 bytes of K as the key and
| the second 16 bytes of | K as the initializatio | n vector. The result of this encryption is | E. |

c) Taking the first 16 bytes of E as an unsigned big -endian integer, compute the remainder, modulo 3. If the
| result is 0, the next hash used is SHA | -256, if the result is 1, the next hash used is SHA | -384, if the re | sult is |
| --- | --- | --- | --- |
| 2, the next hash used is SHA | -512. |  |  |

d) Using the hash algorithm determined in step c, take the hash of E. The result is a new value of K, which
will be 32, 48, or 64 bytes in length.
| Repeat the process (a | -d) with this new value for | K. Following 64 rounds (round number 0 to round |

number 63), do the following, starting with round number 64:

> **NOTE 2** The reason for multiple rounds is to defeat the possibility of running all paths in paralle l. With 64
rounds (minimum) there are 3^64 paths through the algorithm.
e) Look at the very last byte of E. If the value of that byte (taken as an unsigned integer) is greater than the
| round number | - 32, repeat steps (a | -d) again. |

f) Repeat from steps (a -e) until the value of the last byte is ≤ (round number) - 32.

> **NOTE 3** Tests indicate that the total number of rounds will most likely be between 65 and 80.
| The first 32 bytes of the final | K are the output of the algorithm. |

#### 7.6.4.4 Password algorithms

##### 7.6.4.4.1 General

| In addition to the | file encryption key, the standard security handler shall provide the contents of the |
| --- | --- |
| encryption dictionary ( | "Table 20 — Entries common to all encryption dictionaries" and "Table 21 — |

Additional encryption dictionary entries for the standard security handler"). The values of the Filter, V, Length, R, and P entries are straightforward. The computation of the values for the O (owner password) and the U (user password) entries for encryption revision 4 and earlier, and the O, U, OE (owner encryption), UE (user encryption) and Perms (permissions) values for encryption revision 6


## Page 98

require more explanation.

Algorithms 3 through 5 show how the values of the owner password and user password are computed for revision 4 and earlier. Algorithm 6 and 7 show how to determine if a password is valid. Algorithms 8 through 10 show how to compute the values stored in the PDF (by writers) to determine if a revision 6 password is valid. Algorithms 11 through 13 describe how to validate a password provided by a user or owner to read the PDF.

Passwords for revision 4 and earlier are up to 32 characters in length, and are limited to characters in the PDFDocEncoding character set (see Annex D, "Character sets and encodings").

In revision 4 and earlier, the result of running the password algorithm was the file encryption key. In revision 6, the file encryption key is decoupled from the password algorithm to make the owner and user keys independent. For algorithms 8 to 13, the file encryption key shall be a 256-bit (32-byte) value generated with a strong random number generator.

##### 7.6.4.4.2 Algorithm 3: Computing the encryption dictionary’s O-entry value

(revision 4 and earlier)

This algorithm is deprecated in PDF 2.0.

a) Pad or truncate the owner password string as described in step (b) of 7.6.4.3.2 , "Algorithm 2: Computing
| a file encryption key | in order to encrypt a document (revision 4 and earlier) | ". If there is no owner |

password, use the user password instead.
b) Initialise the MD5 hash function and pass the result of step (a) as input to this function.
c) (Security handlers of revision 3 or greater ) Do the following 50 times: Take the output from the previous
MD5 hash and pass it as input into a new MD5 hash.
d) Create an RC4 file encryption key using the first n bytes of the output from the final MD5 hash, where n
shall always be 5 for security handlers of revision 2 but, for security handlers of revision 3 or greater,
| shall depend on the value of the encryption dicti | onary’s | Length entry. |

e) Pad or truncate the user password string as described in step (b) of 7.6.4.3.2, "Algorithm 2: Computing a
| file encryption key | in order to encrypt a document (revision 4 and earlier) | ". |

f) Encrypt the result of step (e), using an RC4 encryption function with the file encryption key obtained in
step (d).
g) (Security handlers of revision 3 or greater ) Do the following 19 times: Take the output from the previous
| invocation of the RC4 function and pass it as input to a new invocation of the function; use a | file |
| --- | --- |
| encryption key generated by taking each byte of the enc | ryption key obtained in step (d) and performing |
| an XOR (exclusive or) operation between that byte and the single | -byte value of the iteration counter |

(from 1 to 19).
h) Store the output from the final invocation of the RC4 function as the value of the O entry in the
encryption dictionary.

##### 7.6.4.4.3 Algorithm 4: Computing the encryption dictionary’s U -entry value

(Security handlers of revision 2)

This algorithm is deprecated in PDF 2.0.

a) Create a file encryption key based on the user password string, as described in 7.6.4.3.2 , "Algorithm 2:
| Computing a | file encryption key | in order to encrypt a document (revision 4 and earlier) | ". |


## Page 99

b) Encrypt the 32 -byte padding string shown in step (b) of 7.6.4.3.2 , "Algorithm 2: Computing a file
| encryption key | in order to encrypt a document (revision 4 and earlier) | ", using an RC4 encryp | tion |
| --- | --- | --- | --- |
| function with the | file encryption key from the preceding step. |  |  |

c) Store the result of step (b) as the value of the U entry in the encryption dictionary.

##### 7.6.4.4.4 Algorithm 5: Computing the encryption dictionary’s U (user password)

| value (Security handlers of re | vision 3 or 4) |

This algorithm is deprecated in PDF 2.0.

a) Create a file encryption key based on the user password string, as described in 7.6.4.3.2 , "Algorithm 2:
| Computing a | file encryption key | in order to encrypt a document (revision 4 and earlier) | ". |

b) Initialise the MD5 hash function and pass the 32 -byte padding string shown in step (b) of 7.6.4.3.2 ,
| "Algorithm 2: Computing a | file encryption key | in order to encrypt a document (revision 4 and earlier) | " |

as input to this function.
c) Pass the first element of the fi le’s file identifier array (the value of the ID entry in the document’s trailer
| dictionary; see | "Table 15 — Entries in the file trailer dictionary | ") to the hash funct | ion and finish the hash. |

d) Encrypt the 16 -byte result of the hash, using an RC4 encryption function with the encryption key from
step (a).
e) Do the following 19 times: Take the output from the previous invocation of the RC4 function and pass it
| as input to a | new invocation of the function; use a file | encryption key generated by taking each byte of |
| --- | --- | --- |
| the original | file encryption key obtained in step (a) and performing an XOR (exclusive or) operation |  |
| between that byte and the single | -byte value of the | iteration counter (from 1 to 19). |

f) Append 16 bytes of arbitrary padding to the output from the final invocation of the RC4 function and
| store the 32 | -byte result as the value of the | U entry in the encryption dictionary. |

> **NOTE** The standard security handler u ses the algorithms 6 and 7 that follow, to determine whether a
supplied password string is the correct user or owner password. Note too that algorithm 6 can be used to determine whether a document’s user password is the empty string, and therefore whether to suppress prompting for a password when the document is opened.

##### 7.6.4.4.5 Algorithm 6: Authenticating the user password (Security handlers of

revision 4 and earlier)

This algorithm is deprecated in PDF 2.0.

a) Perform all but the last step of 7.6.4.4.3 , "Algorithm 4: Computing the encryption dictionary’s U -entry
| value (Security handlers of revision 2) | " or 7.6.4.4.4  , "Algorithm 5: Computing the encryption dictionary’s |
| --- | --- |
| U (user password) value (Security handlers of revision 3 or | 4)" using the supplied password string. |

b) If the result of step (a) is equal to the value of the encryption dictionary’s U entry (comparing on the first
| 16 bytes in the case of security handlers of revision 3 or greater), the password supplied is t | he correct |  |  |
| --- | --- | --- | --- |
| user password. The | file encryption | key obtained in step (a) (that is, in the first step of | 7.6.4.4.3  , |
| "Algorithm 4: Computing the encryption dictionary’s U | -entry value (Security handlers of revision 2) | " or |  |

7.6.4.4.4 , "Algorithm 5: Computing the encryption dictionary’s U (user password) value (Security
| handlers of revision 3 or | 4)") shall be used to decrypt the document. |

##### 7.6.4.4.6 Algorithm 7: Authenticating the owner password (Security handlers of

revision 4 and earlier)

This algorithm is deprecated in PDF 2.0.

a) Compute a file encryption key from the supplied password string, as described in step (a) to step (d) of


## Page 100

| "Algorithm 3: Computing the encryption dictionary’s O-entry value (revision 4 and earlier) | ". |

b) (Security handlers of revision 2 only ) Decrypt the value of the encryption dictionary’s O entry, using an
| RC4 encryption function with the | file encryption key computed in step (a). |  |
| --- | --- | --- |
| (Security handlers of revision 3 or greater | ) Do the following 20 times: Decrypt the | value of the |
| encryption dictionary’s | O entry (first iteration) or the output from the previous iteration (all |  |

subsequent iterations), using an RC4 encryption function with a different encryption key at each
| iteration. The key shall be generated by taking t | he original key (obtained in step (a)) and |
| --- | --- |
| performing an XOR (exclusive or) operation between each byte of the key and the single | -byte value |

of the iteration counter (from 19 to 0).

c) The result of step (b) purports to be the user password. Authenticate this user password using 7.6.4.4.5,
| "Algorithm 6: Auth | enticating the user password | (Security handlers of revision | 4 and earlier | )". If it is |

correct, the password supplied is the correct owner password.

##### 7.6.4.4.7 Algorithm 8: Computing the encryption d ictionary’s U (user password) and

| UE (user encryption) values | (Security handlers of revision | 6) |

a) Generate 16 random bytes of data using a strong random number generator. The first 8 bytes are the
| User Validation Salt. The second 8 bytes are the User Key Sal | t. Compute the 32 | -byte hash using algorithm |
| --- | --- | --- |
| 2.B with an input string consisting of the UTF | -8 password concatenated with the User Validation Salt. |  |
| The 48- byte string consisting of the 32 | -byte hash followed by the User Validation Salt followed by the |  |
| User Key Salt is stored as the | U key. |  |

b) Compute the 32 -byte hash using algorithm 2.B with an input string consisting of the UTF -8 password
concatenated with the User Key Salt. Using this hash as the key, encrypt the file encryption key using
| AES-256 in CBC mode with no padding and an initializat | ion vector of zero. The resulting 32 | -byte string is |
| --- | --- | --- |
| stored as the | UE key. |  |

##### 7.6.4.4.8 Algorithm 9: Computing the encryption dictionary’s O (owner password)

| and OE (owner encryption) values | (Security handlers of revision | 6) |

a) Generate 16 random bytes of data using a stro ng random number generator. The first 8 bytes are the
| Owner Validation Salt. The second 8 bytes are the Owner Key Salt. Compute the 32 | -byte hash using |  |
| --- | --- | --- |
| algorithm 2.B with an input string consisting of the UTF | -8 password concatenated with the Owner |  |
| Validation Salt and then concatenated with the 48 | -byte U string as generated in Algorithm 8. The 48 | -byte |
| string consisting of the 32 | -byte hash followed by the Owner Validation Salt followed by the Owner Key |  |
| Salt is stored as the | O key. |  |

b) Compute the 32 -byte hash usin g 7.6.4.3.4, "Algorithm 2.B: Computing a hash (revision 6 and later) " with
| an input string consisting of the UTF | -8 password concatenated with the Owner Key Salt and then |  |
| --- | --- | --- |
| concatenated with the 48 | -byte U string as generated in | 7.6.4.4.7, "Algorithm 8: Computing the |
| encryption dictionary’s | U (user password) and UE (user encryption) values | (Security handlers of |
| revision | 6)". Using this hash as the key, encrypt the file encryption key using AES | -256 in CBC mode with |
| no padding and an initialization vector of zero. The resulting 32 | -byte string is stored as the | OE key. |

##### 7.6.4.4.9 Algorithm 10: Computing the encryption dictionary’s Perms (p ermissions)

| value (Security handlers of revision | 6) |

Fill a 16-byte block as follows:

a) Extend the permissions (contents of the P integer) to 64 bits by setting the upper 32 bits to all 1’s.

> **NOTE** This allows for future extension without changing the format.
b) Record the 8 bytes of permission in the bytes 0 -7 of the block, low order byte first.


## Page 101

c) Set byte 8 to the ASCII character "T" or "F" according to the EncryptMetadata boolean.
d) Set bytes 9 -11 to the ASCII characters ' "a", "d", "b".
e) Set bytes 12 -15 to 4 bytes of random data, which will be ignored.
f) Encrypt the 16 -byte block using AES -256 in ECB mode with an initialization vector of zero, using the file
| encryption key as the key. The result (16 bytes) is stored as the | Perms string, and checked for validity |

when the file is opened.

##### 7.6.4.4.10 Algorithm 11: Authenticating the user password (Security handlers of

revision 6)

a) Test the password against the user key by computing the 32 -byte hash using 7.6.4.3.4 , "Algorithm 2.B:
| Computing a hash | (revision 6 and later) | " with an input string consisting of the UTF | -8 password |
| --- | --- | --- | --- |
| concatenated with the 8 bytes of User Validation Salt | (see 7.6.4.4.7  , "Algorithm 8: Computing the |  |  |
| encryption dictionary’s U (user password) and UE (user encryption) values | (Security handlers of |  |  |
| revision  6)"). If the 32  - byte resu  lt matches the first 32 bytes of the | U string, this is the | user password. |  |

##### 7.6.4.4.11 Algorithm 12: Authenticating the owner password (Security handlers of

revision 6)

a) Test the password against the owner key by computing the 32 -byte hash using algorithm 2.B with an
| input string consisting of the UTF | -8 password concatenated with the 8 bytes of Owner Validation Salt |  |  |
| --- | --- | --- | --- |
| and the 48 byte | U string. If the 32 byte result matches t | he first 32 bytes of the | O string, this is the owner |

password.

##### 7.6.4.4.12 Algorithm 13: Validating the permissions (Security handlers of revision 6)

a) Decrypt the 16 byte Perms string using AES -256 in ECB mode with an initialization vector of zero and
| the file encrypti  on key as the key. Verify that bytes 9 | -11 of the result are the characters | "a", "d", "b". Bytes |
| --- | --- | --- |
| 0-3 of the decrypted | Perms entry, treated as a little | -endian integer, are the user permissions. They |
| should match the value in the | P key. Byte 8 should match th | e ASCII character "T" or "F" according to the |
| boolean value of the | EncryptMetadata | key. |

### 7.6.5 Public-key security handlers

#### 7.6.5.1 General

| Security handlers may use public | -key encryption technology to encrypt a document (or strings and |
| --- | --- |
| streams within a document). When d | oing so, specifying one or more lists of recipients, where each list |

has its own unique access permissions may be done. Only specified recipients shall open the encrypted
| document or content, unlike the standard security handler, where a password determine | s access. The |  |
| --- | --- | --- |
| permissions defined for public | -key security handlers are shown in | "Table 24 — Public-key security |

handler user access permissions" in 7.6.5.2, "Public-key encryption dictionary".

Public-key security handlers use the industry standard Public Key Cryptographic Standard Number 7 (CMS) binary encoding syntax to encode recipient list, decryption key, and access permission information. The CMS specification is in Internet RFC 5652.

When encrypting the data, each recipient’s X.509 public key certificate (as described in ITU-T Recommendation X.509) shall be available. When decrypting the data, the PDF reader shall scan the recipient list for which the content is encrypted and shall attempt to find a match with a certificate that belongs to the user. If a match is found, the user requires access to the corresponding private key, which may require authentication, possibly using a password. Once access is obtained, the private key


## Page 102

shall be used to decrypt the encrypted data.

#### 7.6.5.2 Public-key encryption dictionary

Encryption dictionaries for public-key security handlers contain the common entries shown in "Table 20 — Entries common to all encryption dictionaries", whose values are described above. In addition, they may contain the entry shown in "Table 23 — Additional encryption dictionary entries for publickey security handlers" as described below.

The Filter entry shall be the name of a public-key security handler. This filter entry shall be the name of the public key handler used to encrypt the document. See "Table 20 — Entries common to all encryption dictionaries".

> **NOTE** Examples of existing security handlers that support public-key encryption are Entrust.PPKEF,
Adobe.PPKLite, and Adobe.PubSec.
Permitted values of the SubFilter entry for use with conforming public-key security handlers are adbe.pkcs7.s3, adbe.pkcs7.s4, which shall be used when not using crypt filters (see 7.6.6, "Crypt filters") and adbe.pkcs7.s5, which shall be used when using crypt filters.

The CF, StmF, and StrF entries may be present when SubFilter is adbe.pkcs7.s5.

“Table 23 — Additional encryption dictionary entries for public-key security handlers” defines additional encryption dictionary entries for public-key security handlers.

Table 23 — Additional encryption dictionary entries for public-key security handlers

| Key | Type | Value |

| Recipients | array | (Required when SubFilter is adbe.pkcs7.s3 or adbe.pkcs7.s4; PDF 1.3) An array of byte-strings, where each string is a CMS object listing recipients who have been granted equal access rights to the document. The data contained in the CMS object shall include both a cryptographic key that shall be used to decrypt the encrypted data and the access permissions (see "Table 24 — Public-key security handler user access permissions") that apply to the recipient list. There shall be only one CMS object per unique set of access permissions; if a recipient appears in more than one list, the permissions used shall be those in the first matching list. When SubFilter is adbe.pkcs7.s5, recipient lists shall be specified in the crypt filter dictionary; see "Table 27 — Additional crypt filter dictionary entries for public-key security handlers". |

> **NOTE** (2020) "Table 23 — Additional encryption dictionary entries for public -key security handlers"
previously specified a P key which was removed in this document due to errors that prohibited interoperable implementations.
| The access permissions shall be interpreted as an unsigned 32-bit quantity containing a set o | f flags |

specifying which access permissions shall be granted when the document is opened with user access.
"Table 24 — Public-key security handler user access permissions" shows the meanings of these flags.
Bit positions within the flag word shall be numbered from 1 (low-order) to 32 (high-order). A 1 bit in any position shall enable the corresponding access permission.

PDF processors shall ignore all flags other than those at bit positions 2, 3, 4, 5, 6, 9, 10, 11, and 12.


## Page 103

Table 24 — Public-key security handler user access permissions

Bit position Meaning

When set permits change of encryption and enables all other permissions.

Print the document (possibly not at the highest quality level, depending on whether bit 12 is also set).

Modify the contents of the document by operations other than those controlled by bits 6, 9, and 11.

Copy or otherwise extract text and graphics from the document. However, for the limited purpose of providing this content to assistive technology, a PDF reader shall behave as if this bit was set to 1.

> **NOTE** ISO 32000-1 had this option restricted by bit 10, for accessibility, but that
exception has been deprecated in PDF 2.0.

Add or modify text annotations, fill in interactive form fields, and, if bit 4 is also set, create or modify interactive form fields (including signature fields).

Fill in existing interactive form fields (including signature fields), even if bit 6 is clear.

# 10 Not used. This bit was previously used to determine whether content could be

extracted for the purposes of accessibility, however, that restriction has been deprecated in PDF 2.0. PDF readers shall ignore this bit and PDF writers shall always set this bit to 1 to ensure compatibility with PDF readers supporting older specifications.

# 11 Assemble the document (insert, rotate, or delete pages and create document

outline items or thumbnail images), even if bit 4 is clear.

# 12 Print the document to a representation from which a faithful digital copy of the

PDF content could be generated, based on an implementation-dependent algorithm. When this bit is clear (and bit 3 is set), printing shall be limited to a low-level representation of the appearance, possibly of degraded quality.

> **NOTE** "Table 24 — Public-key security handler user access permissions" is different to "Table 22 —
Standard security handler user access permissions".

#### 7.6.5.3 Public-key encryption algorithms

"Figure 4 — Public-key encryption algorithm" illustrates how CMS objects shall be used when encrypting PDF files. A CMS object is designed to encapsulate and encrypt what is referred to as the enveloped data.


## Page 104

Figure 4 — Public-key encryption algorithm The enveloped data in the CMS object contains keying material that shall be used to decrypt the document (or individual strings or streams in the document, when crypt filters are used; see 7.6.6, "Crypt filters"). A key shall be used to encrypt (and decrypt) the enveloped data. This key (the plaintext key in "Figure 4 — Public-key encryption algorithm") shall be encrypted for each recipient, using that recipient’s public key, and shall be stored in the CMS object (as the encrypted key for each recipient).
To decrypt the document, that key shall be decrypted using the recipient’s private key, which yields a decrypted (plaintext) key. That key, in turn, shall be used to decrypt the enveloped data in the CMS object, resulting in a byte array that includes the following information:

• A 20-byte seed that shall be used to create the file encryption key that is used by "Algorithm 1: Encryption of data using the RC4 or AES algorithms" or "Algorithm 1.A: Encryption of data using the AES algorithms". The seed shall be a unique random number generated by the security handler that encrypted the document.
• A 4-byte value defining the permissions, most significant byte first. See "Table 24 — Public-key security handler user access permissions" for the possible permission values.

> **NOTE** The above bullet was corrected in this document (2020).
• When SubFilter is adbe.pkcs7.s3, the relevant permissions shall be only those specified for revision 2 of the standard security handler.
• For adbe.pkcs7.s4, security handlers of revision 3 permissions shall apply.
• For adbe.pkcs7.s5, which supports the use of crypt filters, the permissions shall be the same as adbe.pkcs7.s4 when the crypt filter is referenced from the StmF or StrF entries of the encryption dictionary. When referenced from the Crypt filter decode parameter dictionary of a stream object (see "Table 14 — Optional parameters for Crypt filters"), the 4 bytes of permissions shall be absent from the enveloped data.
The algorithms that shall be used to encrypt the enveloped data in the CMS object are:

• RC4 with key lengths up to 256-bits (deprecated in PDF 2.0); • DES, Triple DES, RC2 with key lengths up to 128 bits (deprecated in PDF 2.0); • 128-bit AES in Cipher Block Chaining (CBC) mode (deprecated in PDF 2.0); • 192-bit AES in CBC mode (deprecated in PDF 2.0);


## Page 105

• 256-bit AES in CBC mode.
The CMS specification is in Internet RFC 5652, Cryptographic Message Syntax.

The file encryption key used by 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms" shall be calculated by means of an SHA-1 message digest operation, for a key length of 128 bits. For the file encryption key used by 7.6.3.3 "Algorithm 1.A: Encryption of data using the AES algorithms", a SHA-256 digest operation shall be used for a key length of 256 bits. These operations digest the following data, in order:

a) The 20 bytes of seed .
b) The bytes of each item in the Recipients array of CMS objects in the order in which they appear in the
array.
c) 4 bytes with the value 0xFF if the key being generated is intended for use in document -level encryption
| and the document metadata | is being left as plaintext | . |

d) The first n/8 bytes of the res ulting digest shall be used as the file encryption key, where n is the bit length
of the file encryption key.

### 7.6.6 Crypt filters

| PDF 1.5 introduces | crypt filters  , which provide finer granularity control of encryption within a PDF file. |
| --- | --- |
| The use of crypt filters | involves the following structures: |

• The encryption dictionary (see "Table 20 — Entries common to all encryption dictionaries") contains entries that enumerate the crypt filters in the document (CF) and specify which ones are used by default to decrypt all the streams (StmF) and strings (StrF) in the document. In addition, the value of the V entry shall be 4 to use crypt filters.
• Each crypt filter specified in the CF entry of the encryption dictionary shall be represented by a
| crypt filter dictionary, whose entries are shown in | "Table 25 — Entries common to all crypt filter |

dictionaries".
• A stream filter type, the Crypt filter (see 7.4.10, "Crypt filter") can be specified for any stream in the document to override the default filter for streams. A PDF processor shall provide a standard Identity filter which shall pass the data unchanged (see "Table 26 — Standard crypt filter names") to allow specific streams, such as document metadata, to be unencrypted in an otherwise encrypted document. The stream’s DecodeParms entry shall contain a Crypt filter decode parameters dictionary (see "Table 14 — Optional parameters for Crypt filters") whose Name entry specifies the particular crypt filter that shall be used (if missing, Identity is used). Different streams may specify different crypt filters.
Authorization to decrypt a stream shall always be obtained before the stream can be accessed. This typically occurs when the document is opened, as specified by a value of DocOpen for the AuthEvent entry in the crypt filter dictionary. PDF readers and security handlers shall treat any attempt to access a stream for which authorization has failed as an error. AuthEvent can also be EFOpen, which indicates the presence of an embedded file that is encrypted with a crypt filter that may be different from the crypt filters used by default to encrypt strings and streams in the document.

In the file specification dictionary (see 7.11.3, "File specification dictionaries"), related files (RF) shall use the same crypt filter as the embedded file (EF).

A value of None for the CFM entry in the crypt filter dictionary allows the security handler to do its own decryption. This allows the handler to tightly control key management and use any preferred


## Page 106

symmetric-key cryptographic algorithm.

Table 25 — Entries common to all crypt filter dictionaries

| Key | Type | Value |

| Type | name | (Optional) If present, shall be CryptFilter for a crypt filter dictionary. |

| CFM | name | (Optional) The method used, if any, by the PDF reader to decrypt data. The following values shall be supported: |
| --- | --- | --- |
| None | The application shall not decrypt data but shall direct the input stream to the security handler for decryption. |  |
| V2 | (Deprecated in PDF 2.0) The application shall ask the security handler for the file encryption key and shall implicitly decrypt data with 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms", using the RC4 algorithm. |  |
| AESV2 | (PDF 1.6; deprecated in PDF 2.0) The application shall ask the security handler for the file encryption key and shall implicitly decrypt data with 7.6.3.2, "Algorithm 1: Encryption of data using the RC4 or AES algorithms", using the AES algorithm in Cipher Block Chaining (CBC) mode with a 16-byte block size and an initialization vector that shall be randomly generated and placed as the first 16 bytes in the stream or string. The key size (Length) shall be 128 bits. |  |
| AESV3 | (PDF 2.0) The application shall ask the security handler for the file encryption key and shall implicitly decrypt data with 7.6.3.3, "Algorithm 1.A: Encryption of data using the AES algorithms", using the AES-256 algorithm in Cipher Block Chaining (CBC) with padding mode with a 16-byte block size and an initialization vector that is randomly generated and placed as the first 16 bytes in the stream or string. The key size (Length) shall be 256 bits. When the value is V2, AESV2 or AESV3, the application may ask once for this file encryption key and cache the key for subsequent use for streams that use the same crypt filter. Therefore, there shall be a one-to- one relationship between a crypt filter name and the corresponding file encryption key. Only the values listed here shall be supported. Applications that encounter other values shall report that the file is encrypted with an unsupported algorithm. Default value: None. |  |

| AuthEvent | name | (Optional) The event that shall be used to trigger the authorization that is required to access file encryption keys used by this filter. If authorization fails, the event shall fail. Valid values shall be: |
| --- | --- | --- |
| DocOpen | Authorization shall be required when a document is opened. |  |
| EFOpen | Authorization shall be required when accessing embedded files. Default value: DocOpen. If this filter is used as the value of StrF or StmF in the encryption dictionary (see "Table 20 — Entries common to all encryption dictionaries"), the PDF reader shall ignore this key and behave as if the value is DocOpen. |  |


## Page 107

| Key | Type | Value |

| Length | integer | (Required; deprecated in PDF 2.0) Security handlers may define their own use of the Length entry and should use it to define the bit length of the file encryption key. The bit length of the file encryption key shall be a multiple of 8 in the range of 40 to 256. The standard security handler expresses the Length entry in bytes (e.g., 32 means a length of 256 bits) and public-key security handlers express it as is (e.g., 256 means a length of 256 bits). When CFM is AESV2, the Length key shall have the value of 128. When CFM is AESV3, the Length key shall have a value of 256. |

> **NOTE** (2020) The Length key was corrected to be required and the
descriptive text updated.

Security handlers may add their own private data to crypt filter dictionaries. Names for private data entries shall conform to the PDF name registry (see Annex E, "Extending PDF").

Table 26 — Standard crypt filter names

Name Description

| Identity | Input data shall be passed through without any processing. |

"Table 27 — Additional crypt filter dictionary entries for public-key security handlers" lists the additional crypt filter dictionary entries used by public-key security handlers (see 7.6.5, "Public-key security handlers"). When these entries are present, the value of CFM shall be V2 or AESV2 or AESV3.

> **NOTE** The allowed values of CFM were corrected in this document (2020).

Table 27 — Additional crypt filter dictionary entries for public-key security handlers

| Key | Type | Value |

| Recipients | string or | (Required) If the crypt filter is referenced from StmF or StrF in the |
| --- | --- | --- |
| array | encryption dictionary, this entry shall be an array of byte strings, where each string shall be a binary-encoded CMS object that shall list recipients that have been granted equal access rights to the document. The enveloped data contained in the CMS object shall include both a 20-byte seed value that shall be used to compute the file encryption key (see 7.6.5.3, "Public- key encryption algorithms") followed by 4 bytes of permissions settings (see "Table 24 — Public-key security handler user access permissions") that shall apply to the recipient list. There shall be only one object per unique set of access permissions. If a recipient appears in more than one list, the permissions used shall be those in the first matching list. |  |

> **NOTE** (2020) The cross-reference to Table 24 in the above paragraph was
corrected.
| If the crypt filter is referenced from a | Crypt filter decode parameter dictionary (see "Table 14 — Optional parameters for Crypt filters"), this entry shall be a string that shall be a binary-encoded CMS object that shall contain a list of all recipients who are permitted to access the corresponding encrypted stream. The enveloped data contained in the CMS |


## Page 108

| Key | Type | Value object shall be a 20-byte seed value that shall be used to create the file encryption key that shall be used by the algorithm in "Algorithm 1: Encryption of data using the RC4 or AES algorithms". |

| EncryptMetadata | boolean | (Optional; used only by crypt filters that are referenced from StmF in an encryption dictionary) Indicates whether the document-level metadata stream (see 14.3.2, "Metadata streams") shall be encrypted. PDF processors shall respect this value when determining whether metadata shall be encrypted. The value of the EncryptMetadata entry is set by the security handler rather than the PDF processor. Default value: true. |

> **EXAMPLE** The following shows the use of crypt filters in an encrypted document containing a plaintext document-level
| metadata stream. The metadata stream is left as is by applying the | Identity crypt filter. The remaining streams and strings are decrypted using the default filters. |

%PDF–2.0 %… at least four binary characters > 127
| 1 0 obj | %Document catalog <</Type /Catalog /Pages 2 0 R /Metadata 6 0 R >> endobj |

| 2 0 obj | %Page tree <</Type /Pages /Kids [3 0 R] /Count 1 >> endobj |

| 3 0 obj | %1st page <</Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R >> endobj |

| 4 0 obj | %Page contents <</Length 35>> stream … Encrypted Page-marking operators … endstream endobj |

5 0 obj
| <</Title ($#*#%*$#^&##)>> | %Info dictionary: encrypted text string endobj |

6 0 obj <</Type /Metadata /Subtype /XML /Length 15
| /Filter [/Crypt] | %Uses a crypt filter |
| --- | --- |
| /DecodeParms | %with these parameters <</Type /CryptFilterDecodeParms |
| /Name /Identity | %Indicates no encryption >> >> stream |


## Page 109

| … XML metadata… | %Unencrypted metadata |

endstream endobj

| 8 0 obj | %Encryption dictionary <</Filter /MySecurityHandlerName |
| --- | --- |
| /V 4 | %Version 4: allow crypt filters |
| /CF | %List of crypt filters <</MyFilter0 <</Type /CryptFilter |
| /CFM V2>> | %Uses the standard algorithm >> |
| /StrF /MyFilter0 | %Strings are decrypted using /MyFilter0 |
| /StmF /MyFilter0 | %Streams are decrypted using /MyFilter0 |
| … | %Private data for /MySecurityHandlerName /MyUnsecureKey (12345678) /EncryptMetadata false >> |

endobj

xref … trailer << /Size 8 /Root 1 0 R /Info 5 0 R /Encrypt 8 0 R /ID [<1379F4B0AA2C1B40B3F1A63E4B8C7810> <4C9F4FF0E656CF40AD95CDEA3E720E1B> ] %ID is not encrypted >> startxref

%%EOF

### 7.6.7 Unencrypted wrapper document

This document allows PDF writers to use custom security handlers that encrypt the strings and streams within a PDF file using algorithms that are not specified in this standard. This allows PDF producers the flexibility to adapt to changes in encryption technology and respond to customer needs.
However, when PDF files are encrypted using such non-standard security handlers, users attempting to open such documents may not have enough information to identify a security handler for the user’s PDF processor that would enable the document to be decrypted.

To enable PDF producers using non-standard security handlers to provide users with information to help them identify and install necessary security handler(s) the producer may embed the encrypted PDF file within an unencrypted PDF file (an unencrypted wrapper). An unencrypted wrapper should provide guidance informing users of the security handler that is needed to decrypt the embedded encrypted PDF file (encrypted payload). This subclause specifies use of the collection dictionary and associated files to identify the encrypted payload in a way that allows PDF processors that already have the necessary security handler to immediately present the encrypted payload, although PDF processors without the custom security handler will present the unencrypted wrapper document with helpful instructions to the user.

To enable automatic display of an encrypted payload within an unencrypted wrapper the PDF producer shall include a Collection dictionary (as described in 12.3.5, "Collections") identifying the encrypted payload as the initial document in the collection and setting the collection View to H (hidden). In addition, the PDF producer shall include the file specification dictionary for the encrypted


## Page 110

payload in the EmbeddedFiles name tree, and as an entry in the AF array in the document catalog. The file specification dictionary for the encrypted payload shall include the AFRelationship key with a value of EncryptedPayload, and shall include an encrypted payload dictionary (see "Table 28 — Entries in an encrypted payload dictionary") with details of the cryptographic filter needed to decrypt the encrypted payload. For a PDF file that is an unencrypted wrapper for an encrypted payload document, the EmbeddedFiles name tree shall contain exactly one entry, for the encrypted payload document.

Table 28 — Entries in an encrypted payload dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be EncryptedPayload for an encrypted payload file specification. |

| Subtype | name | (Required) The name of the cryptographic filter used to encrypt the encrypted payload document. This allows a PDF processor to easily determine whether it has the appropriate cryptographic filter. |

| Version | name | (Optional) The version number of the cryptographic filter used to encrypt the encrypted payload referenced by this dictionary. |

> **NOTE** The value of Version is not to be interpreted as a real number but as
integers with a PERIOD (2Eh) between them.

> **EXAMPLE** This example shows details of an unencrypted wrapper document containing an encrypted payload
document.

%PDF-2.0 15 0 obj <</Length 377 /Filter /FlateDecode>> stream … endstream endobj

| 17 0 obj | %File Specification Dictionary << /Desc (This embedded file is encrypted using the Acme Custom Crypto filter) /EF <</F 18 0 R>> /F (AcmeCustomCrypto Protected PDF.pdf) /Type /Filespec /UF (AcmeCustomCrypto Protected PDF.pdf) /EP << /Type /EncryptedPayload /Subtype /AcmeCustomCrypto /Version /1.0 >> /AFRelationship /EncryptedPayload >> |

endobj

| 18 0 obj | %Embedded File stream << /Type /EmbeddedFile /DL 123456 /Filter /FlateDecode /Length 123456 /Params << /CheckSum <01234567890123456789012345678901> |


## Page 111

/CreationDate (D:20130228170141-08'00) /ModDate (D:20130228170141-08'00) /Size 123456 >> /Subtype /application#2Fpdf >> stream … Flate-encoded contents of encrypted payload document … endstream endobj

| 19 0 obj | %Document Catalog << /Type/Catalog /Collection << /D (AcmeCustomCrypto Protected PDF.pdf) /View /H >> /Names 20 0 R /Pages 12 0 R … /AF [17 0 R] >> |

endobj

| 20 0 obj | %Document Name tree <</EmbeddedFiles 21 0 R>> |

endobj

| 21 0 obj | %Embedded Files Name dictionary <</Names [(AcmeCustomCrypto Protected PDF.pdf) 17 0 R]>> |

endobj

12 0 obj %Page tree … Page tree for wrapper cover page … endobj

… xref 0 21 0000000000 65536 f … 0000036212 00000 n trailer << /Root 19 0 R /Info 3 0 R /Size 21 >> startxref

%%EOF

## 7.7 Document structure

### 7.7.1 General

# A PDF document can be regarded as a hierarchy of objects contained in the body section of a PDF file.

At the root of the hierarchy is the document’s catalog dictionary (see 7.7.2, "Document catalog dictionary").


## Page 112

> **NOTE** Most of the objects in the hierarchy are dictionaries. "Figure 5 — Structure of a PDF document"
illustrates the structure of the object hierarchy.

> **EXAMPLE** Each page of the document is represented by a page object–a dictionary that includes references to the
page’s contents and other attributes, such as its thumbnail image (12.3.4, "Thumbnail images") and any annotations (12.5, "Annotations") associated with it. The individual page objects are tied together in a structure called the page tree (described in 7.7.3, "Page tree"), which in turn is specified by an indirect reference in the document catalog dictionary. Parent, child, and sibling relationships within the hierarchy are defined by dictionary entries whose values are indirect references to other dictionaries.

The data structures described in this subclause, particularly the Catalog and Page dictionaries, combine entries describing document structure with ones dealing with the detailed semantics of documents and pages. All entries are listed here, but many of their descriptions are deferred to subsequent subclauses.

### 7.7.2 Document catalog dictionary

The root of a document’s object hierarchy is the catalog dictionary, located by means of the Root entry in the trailer of the PDF file (see 7.5.5, "File trailer"). The catalog dictionary contains references to other objects defining the document’s contents, outline, article threads, named destinations, and other attributes. In addition, it contains information about how the document shall be displayed on the screen, such as whether its outline and thumbnail page images shall be displayed automatically and whether some location other than the first page shall be shown when the document is opened. "Table 29 — Entries in the catalog dictionary" shows the entries in the catalog dictionary.


## Page 113

Figure 5 — Structure of a PDF document

Table 29 — Entries in the catalog dictionary

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be Catalog for the catalog dictionary. |


## Page 114

| Key | Type | Value |

| Version | name | (Optional; PDF 1.4) The version of the PDF specification to which the document conforms (for example, 1.4) if later than the version specified in the file’s header (see 7.5.2, "File header"). If the header specifies a later version, or if this entry is absent, the document shall conform to the version specified in the header. This entry enables a PDF processor to update the version using an incremental update; see 7.5.6, "Incremental updates". The value of this entry shall be a name object, not a number, and therefore shall be preceded by a SOLIDUS (2Fh) character (/) when written in the PDF file (for example, /1.4). |

| Extensions | dictionary | (Optional; ISO 32000-1) An extensions dictionary containing developer prefix identification and version numbers for developer extensions that occur in this document. 7.12, "Extensions dictionary", describes this dictionary and how it shall be used. |

| Pages | dictionary | (Required; shall be an indirect reference) The page tree node that shall be the root of the document’s page tree (see 7.7.3, "Page tree"). |

| PageLabels | number tree | (Optional; PDF 1.3) A number tree (see 7.9.7, "Number trees") defining the page labelling for the document. The keys in this tree shall be page indices; the corresponding values shall be page label dictionaries (see 12.4.2, "Page labels"). Each page index shall denote the first page in a labelling range to which the specified page label dictionary applies. The tree shall include a value for page index 0. |

| Names | dictionary | (Optional; PDF 1.2) The document’s name dictionary (see 7.7.4, "Name dictionary"). (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the Names dictionary is required and shall contain the EmbeddedFiles name tree. |

| Dests | dictionary | (Optional; PDF 1.1; shall be an indirect reference) A dictionary of names and corresponding destinations (see 12.3.2.4, "Named destinations"). |

| ViewerPreferences | dictionary | (Optional; PDF 1.2) A viewer preferences dictionary (see 12.2, "Viewer preferences") specifying the way the document shall be displayed on the screen. If this entry is absent, PDF readers shall use their own current user preference settings. |


## Page 115

| Key | Type | Value |

| PageLayout | name | (Optional) A name object specifying the page layout shall be used when the document is opened: |
| --- | --- | --- |
| SinglePage | Display one page at a time |  |
| OneColumn | Display the pages in one column |  |
| TwoColumnLeft | Display the pages in two columns, with odd- numbered pages on the left |  |
| TwoColumnRight | Display the pages in two columns, with odd- numbered pages on the right |  |
| TwoPageLeft | (PDF 1.5) Display the pages two at a time, with odd-numbered pages on the left |  |
| TwoPageRight | (PDF 1.5) Display the pages two at a time, with odd-numbered pages on the right Default value: SinglePage. |  |

| PageMode | name | (Optional) A name object specifying how the document shall be displayed when opened: |
| --- | --- | --- |
| UseNone | Neither document outline nor thumbnail images visible |  |
| UseOutlines | Document outline visible |  |
| UseThumbs | Thumbnail images visible |  |
| FullScreen | Full-screen mode, with no menu bar, window controls, or any other window visible |  |
| UseOC | (PDF 1.5) Optional content group panel visible |  |
| UseAttachments | (PDF 1.6) Attachments panel visible Default value: UseNone. |  |

| Outlines | dictionary | (Optional; shall be an indirect reference) The outline dictionary that shall be the root of the document’s outline hierarchy (see 12.3.3, "Document outline"). |

| Threads | array | (Optional; PDF 1.1; shall be an indirect reference) An array of thread dictionaries that shall represent the document’s article threads (see 12.4.3, "Articles"). |

| OpenAction | dictionary | (Optional; PDF 1.1) A value specifying a destination that shall be |
| --- | --- | --- |
| or array | displayed or an action that shall be performed when the document is opened. The value shall be either an array defining a destination (see 12.3.2, "Destinations") or an action dictionary representing an action (12.6.2, "Action dictionaries"). If this entry is absent, the document shall be opened to the top of the first page at the default magnification factor. |  |

| AA | dictionary | (Optional; PDF 1.2) An additional-actions dictionary defining the actions that shall be taken in response to various trigger events affecting the document as a whole (see 12.6.3, "Trigger events"). |

| URI | dictionary | (Optional; PDF 1.1) A URI dictionary containing document-level information for URI (uniform resource identifier) actions (see 12.6.4.8, "URI actions"). |

| AcroForm | dictionary | (Optional; PDF 1.2) The document’s interactive form (AcroForm) dictionary (see 12.7.3, "Interactive form dictionary"). |


## Page 116

| Key | Type | Value |

| Metadata | stream | (Optional; PDF 1.4; shall be an indirect reference) A metadata stream that shall contain metadata for the document (see 14.3.2, "Metadata streams"). |

| StructTreeRoot | dictionary | (Optional; PDF 1.3) The document’s structure tree root dictionary (see 14.7.2, "Structure hierarchy"). |

| MarkInfo | dictionary | (Optional; PDF 1.4) A mark information dictionary that shall contain information about the document’s usage of tagged PDF conventions (see 14.7, "Logical structure"). |

| Lang | text string | (Optional; PDF 1.4) A language identifier that shall specify the natural language for all text in the document except where overridden by language specifications for structure elements or marked-content (see 14.9.2, "Natural language specification"). If this entry is absent, the language shall be considered unknown. |

| SpiderInfo | dictionary | (Optional; PDF 1.3) A Web Capture information dictionary that shall contain state information used by any Web Capture extension (see 14.10.2, "Web capture information dictionary"). |

| OutputIntents | array | (Optional; PDF 1.4) An array of output intent dictionaries that shall specify the colour characteristics of output devices on which the document might be rendered (see 14.11.5, "Output intents"). |

| PieceInfo | dictionary | (Optional; PDF 1.4) A page-piece dictionary associated with the document (see 14.5, "Page-piece dictionaries"). |

| OCProperties | dictionary | (Optional; PDF 1.5; required if a document contains optional content) The document’s optional content properties dictionary (see 8.11.4, "Configuring optional content"). |

| Perms | dictionary | (Optional; PDF 1.5) A permissions dictionary that shall specify user access permissions for the document. 12.8.6, "Permissions", describes this dictionary and how it shall be used. |

| Legal | dictionary | (Optional; PDF 1.5) A dictionary that shall contain attestations regarding the content of a PDF document, as it relates to the legality of digital signatures (see 12.8.7, "Legal content attestations"). |

| Requirements | array | (Optional; PDF 1.7) An array of requirement dictionaries that shall represent requirements for the document. Subclause 12.11, "Document requirements", describes this dictionary and how it shall be used. |

| Collection | dictionary | (Optional; PDF 1.7) A collection dictionary that an interactive PDF processor shall use to enhance the presentation of file attachments stored in the PDF document. (see 12.3.5, "Collections"). (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document ") the Collection is required and shall identify the encrypted payload as the default document (as indicated by the D entry) of the collection and shall further specify that the collection View should be initially H (hidden). |


## Page 117

| Key | Type | Value |

| NeedsRendering | boolean | (Optional; deprecated in PDF 2.0) A flag used to expedite the display of PDF documents containing XFA forms. It specifies whether the document shall be regenerated when the document is first opened. See Annex K, “XFA forms”. Default value: false. |

| DSS | dictionary | (Optional; PDF 2.0) A DSS dictionary containing document-wide security information. See 12.8.4.3, "Document Security Store (DSS)". |

| AF | array of | (Optional; PDF 2.0) An array of one or more file specification |
| --- | --- | --- |
| dictionaries | dictionaries (7.11.3, "File specification dictionaries") which denote the associated files for this PDF document. See 14.13, "Associated files" and 14.13.3, "Associated files linked to the PDF document’s catalog" for more details. For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the AF key is required and shall include a reference to the file specification dictionary for the encrypted payload document. |  |

| DPartRoot | dictionary | (Optional; PDF 2.0) A DPartRoot dictionary used to describe the document parts hierarchy for this PDF document. See 14.12, "Document parts". |

> **EXAMPLE** The following shows a sample catalog dictionary object.

1 0 obj <</Type /Catalog /Pages 2 0 R /PageMode /UseOutlines /Outlines 3 0 R >> endobj

### 7.7.3 Page tree

#### 7.7.3.1 General

The pages of a document are accessed through a structure known as the page tree, which defines the ordering of pages in the document. Using the tree structure, PDF readers using only limited memory, can quickly open a document containing thousands of pages. The tree contains nodes of two types — intermediate nodes, called page tree nodes, and leaf nodes, called page objects — whose form is described in the subsequent subclauses. Compliant PDF processors shall be prepared to handle any form of tree structure built of such nodes.

> **NOTE** The simplest structure can consist of a single page tree node that references all of the
document’s page objects directly. However, to optimise application performance, a PDF writer can construct trees of a particular form, known as balanced trees. Further information on this form of tree can be found in Data Structures and Algorithms, by Aho, Hopcroft, and Ullman.

#### 7.7.3.2 Page tree nodes

"Table 30 — Required entries in a page tree node" shows the entries in a page tree node that shall always be present (Required).


## Page 118

Table 30 — Required entries in a page tree node

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be Pages for a page tree node. |

| Parent | dictionary | (Required except in root node; not permitted in the root node; shall be an indirect reference) The page tree node that is the immediate parent of this one. |

| Kids | array | (Required) An array of indirect references to the immediate children of this node. The children shall only be page objects or other page tree nodes. |

| Count | integer | (Required) The number of leaf nodes (page objects) that are descendants of this node within the page tree. |

> **NOTE** Since the number of pages descendent from a Pages dictionary can be
accurately determined by examining the tree itself using the Kids arrays, the Count entry is redundant.

# A PDF writer shall ensure that the value of the Count key is consistent with

the number of entries in the Kids array and its descendants which definitively determines the number of descendant pages.

> **NOTE 1** The structure of the page tree is not necessarily related to the logical structure of the document;
that is, page tree nodes do not represent chapters, sections, and so forth. Other data structures are defined for this purpose; see 14.7, "Logical structure".
PDF processors shall not be required to preserve the existing structure of the page tree.

> **EXAMPLE** The following illustrates the page tree for a document with three pages. See 7.7.3.3, "Page objects" for the
contents of the individual page objects, and H.5, "Page tree example", for a more extended example showing the page tree for a longer document.

2 0 obj <</Type /Pages /Kids [4 0 R 10 0 R 24 0 R ] /Count 3 >> endobj

4 0 obj <</Type /Page …Additional entries describing the attributes of this page… >> endobj

10 0 obj <</Type /Page …Additional entries describing the attributes of this page… >> endobj

24 0 obj <</Type /Page …Additional entries describing the attributes of this page… >> endobj


## Page 119

| In addition to the entries shown in "Table 30 — Required entries in a page tree node | ", a page tree node |

may contain further entries defining inherited attributes for the page objects that are its descendants (see 7.7.3.4, "Inheritance of page attributes"). A page tree shall not contain multiple indirect references to the same page tree node.

> **NOTE 2** This requirement prevents multiple copies of the same node being present in the tree.

#### 7.7.3.3 Page objects

The leaves of the page tree are page objects, each of which is a dictionary specifying the attributes of a single page of the document. "Table 31 — Entries in a page object" shows the contents of this dictionary. The table also identifies which attributes a page may inherit from its ancestor nodes in the page tree, as described under 7.7.3.4, "Inheritance of page attributes". Attributes that are not explicitly identified in the table as inheritable shall not be inherited.

Table 31 — Entries in a page object

| Key | Type | Value |

| Type | name | (Required) The type of PDF object that this dictionary describes; shall be Page for a page object or Template for an invisible Template page (see 12.7.7, "Named pages"). |

| Parent | dictionary | (Required; shall be an indirect reference) The page tree node that is the immediate parent of this page object. Objects of Type Template shall have no Parent key. |

| LastModified | date | (Required if PieceInfo is present; optional otherwise; PDF 1.3) The date and time (see 7.9.4, "Dates") when the page’s contents were most recently modified. If a page-piece dictionary (PieceInfo) is present, the modification date shall be used to ascertain which of the application data dictionaries that it contains correspond to the current content of the page (see 14.5, "Page-piece dictionaries"). |

| Resources | dictionary | (Required; inheritable) A dictionary containing any resources required by the page contents (see 7.8.3, "Resource dictionaries"). If the page requires no resources, the value of this entry shall be an empty dictionary. Omitting the entry entirely indicates that the resources shall be inherited from an ancestor node in the page tree, but PDF writers should not use this method of sharing resources as described in 7.8.3, "Resource dictionaries". |

| MediaBox | rectangle | (Required; inheritable) A rectangle (see 7.9.5, "Rectangles"), expressed in default user space units, that shall define the boundaries of the physical medium on which the page shall be displayed or printed (see 14.11.2, "Page boundaries"). |

| CropBox | rectangle | (Optional; Inheritable) A rectangle, expressed in default user space units, that shall define the visible region of default user space. When the page is displayed or printed, its contents shall be clipped (cropped) to this rectangle (see 14.11.2, "Page boundaries"). Default value: the value of MediaBox. |

> **NOTE 1** This clipped page output will often be placed (imposed) on the
output medium in some implementation-defined manner.


## Page 120

| Key | Type | Value |

| BleedBox | rectangle | (Optional; PDF 1.3) A rectangle, expressed in default user space units, that shall define the region to which the contents of the page shall be clipped when output in a production environment (see 14.11.2, "Page boundaries"). Default value: the value of CropBox. |

| TrimBox | rectangle | (Optional; PDF 1.3) A rectangle, expressed in default user space units, that shall define the intended dimensions of the finished page after trimming (see 14.11.2, "Page boundaries"). Default value: the value of CropBox. |

| ArtBox | rectangle | (Optional; PDF 1.3) A rectangle, expressed in default user space units, that shall define the extent of the page’s meaningful content (including potential white-space) as intended by the page’s creator (see 14.11.2, "Page boundaries"). Default value: the value of CropBox. |

| BoxColorInfo | dictionary | (Optional; PDF 1.4) A box colour information dictionary that shall specify the colours and other visual characteristics that should be used in displaying guidelines on the screen for the various page boundaries (see 14.11.2.2, "Display of page boundaries"). If this entry is absent, the application shall use its own current default settings. |

| Contents | stream or | (Optional) A content stream (see 7.8.2, "Content streams") that shall |
| --- | --- | --- |
| array | describe the contents of this page. If this entry is absent, the page shall be empty. The value shall be either a single stream or an array of streams. If the value is an array, the effect shall be as if all of the streams in the array were concatenated with at least one white-space character added between the streams’ data, in order, to form a single stream. PDF writers can create image objects and other resources as they occur, even though they interrupt the content stream. The division between streams may occur only at the boundaries between lexical tokens (see 7.2, "Lexical conventions") but shall be unrelated to the page’s logical content or organisation. Applications that consume or produce PDF files need not preserve the existing structure of the Contents array. PDF writers shall not create a Contents array containing no elements. |  |

| Rotate | integer | (Optional; inheritable) The number of degrees by which the page shall be rotated clockwise when displayed or printed. The value shall be a multiple of 90. Default value: 0. |

| Group | dictionary | (Optional; PDF 1.4) A group attributes dictionary that shall specify the attributes of the page’s page group for use in the transparent imaging model (see 11.4.7, "Page group" and 11.6.6, "Transparency group XObjects"). |

| Thumb | stream | (Optional) A stream object that shall define the page’s thumbnail image (see 12.3.4, "Thumbnail images"). |


## Page 121

| Key | Type | Value |

| B | array | (Optional; PDF 1.1; recommended if the page contains article beads) An array that shall contain indirect references to all article beads appearing on the page (see 12.4.3, "Articles"). The beads shall be listed in the array in natural reading order. Objects of Type Template shall have no B key. |

> **NOTE 2** The information in this entry can be created or recreated from the
information obtained from the Threads key in the catalog dictionary.

| Dur | number | (Optional; PDF 1.1) The page’s display duration (also called its advance timing): the maximum length of time, in seconds, that the page shall be displayed during presentations before the viewer application shall automatically advance to the next page (see 12.4.4, "Presentations"). By default, the viewer shall not advance automatically. |

| Trans | dictionary | (Optional; PDF 1.1) A transition dictionary describing the transition effect that shall be used when displaying the page during presentations (see 12.4.4, "Presentations"). |

| Annots | array | (Optional) An array of annotation dictionaries that shall contain indirect references to all annotations associated with the page (see 12.5, "Annotations"). |

| AA | dictionary | (Optional; PDF 1.2) An additional-actions dictionary that shall define actions to be performed when the page is opened or closed (see 12.6.3, "Trigger events"). (PDF 1.3) additional-actions dictionaries are not inheritable. |

| Metadata | stream | (Optional; PDF 1.4) A metadata stream that shall contain metadata for the page (see 14.3.2, "Metadata streams"). |

| PieceInfo | dictionary | (Optional; PDF 1.3) A page-piece dictionary associated with the page (see 14.5, "Page-piece dictionaries"). |

| StructParents | integer | (Required if the page contains structural content items; PDF 1.3) The integer key of the page’s entry in the structural parent tree (see 14.7.5.4, "Finding structure elements from content items"). |

| ID | byte string | (Optional; PDF 1.3; indirect reference preferred) The digital identifier of the page’s parent Web Capture content set (see 14.10.6, "Object attributes related to web capture"). |

| PZ | number | (Optional; PDF 1.3) The page’s preferred zoom (magnification) factor: the factor by which it shall be scaled to achieve the natural display magnification (see 14.10.6, "Object attributes related to web capture"). |

| SeparationInfo | dictionary | (Optional; PDF 1.3) A separation dictionary that shall contain information needed to generate colour separations for the page (see 14.11.4, "Separation dictionaries"). |


## Page 122

| Key | Type | Value |

| Tabs | name | (Optional; PDF 1.5) A name specifying the tab order that shall be used for annotations on the page (see 12.5 "Annotations"). If present, the values shall be one of R (row order), C (column order), and S (structure order). Beginning with PDF 2.0, additional values also include A (annotations array order) and W (widget order). Annotations array order refers to the order of the annotation enumerated in the Annots entry of the Page dictionary (see "Table 31 — Entries in a page object"). Widget order means using the same array ordering but making two passes, the first only picking the widget annotations and the second picking all other annotations. |

| TemplateInstantiated | name | (Required if this page was created from a named page object; PDF 1.5) The name of the originating page object (see 12.7.7, "Named pages"). |

| PresSteps | dictionary | (Optional; PDF 1.5) A navigation node dictionary that shall represent the first node on the page (see 12.4.4.2, "Sub-page navigation"). |

| UserUnit | number | (Optional; PDF 1.6) A positive number that shall give the size of default user space units, in multiples of 1 ⁄ 72 inch. The range of supported values shall be implementation-dependent. Default value: 1.0 (user space unit is 1 ⁄ 72 inch). |

| VP | array | (Optional; PDF 1.6) An array of viewport dictionaries (see "Table 265 — Entries in a viewport dictionary") that shall specify rectangular regions of the page. |

| AF | array of | (Optional; PDF 2.0) An array of one or more file specification |
| --- | --- | --- |
| dictionaries | dictionaries (7.11.3, "File specification dictionaries") which denote the associated files for this page. See 14.13, "Associated files" and 14.13.8, "Associated files linked to DParts" for more details. |  |

| OutputIntents | array | (Optional; PDF 2.0) An array of output intent dictionaries that shall specify the colour characteristics of output devices on which this page might be rendered (see 14.11.5, "Output intents"). |

| DPart | dictionary | (Required, if this page is within the range of a DPart, not permitted otherwise; PDF 2.0) An indirect reference to the DPart dictionary whose range of pages includes this page object (see 14.12.3, "Connecting the DPart tree structure to pages"). NOTE 3 The DPart key in a page object allows a PDF processor to directly retrieve the section of the document part hierarchy that applies to this page object. This also allows for ready access of DPM data in PDF processors. |


## Page 123

> **EXAMPLE** The following shows the definition of a page object with two annotations. The media box specifies that the
page is to be printed on letter-size paper. In addition, the resource dictionary is specified as a direct object and shows that the page makes use of three fonts named F3, F5, and F7.

3 0 obj <</Type /Page /Parent 4 0 R /MediaBox [0 0 612 792] /Resources <</Font <</F3 7 0 R /F5 9 0 R /F7 11 0 R >> >> /Contents 12 0 R /Annots [23 0 R 24 0 R ] >> endobj

A page tree shall not contain multiple indirect references to the same page object.

> **NOTE** This requirement prevents multiple copies of the same page being present in the tree.

#### 7.7.3.4 Inheritance of page attributes

Some of the page attributes shown in "Table 31 — Entries in a page object" are designated as inheritable. If such an attribute is omitted from a page object, its value shall be inherited from an ancestor node in the page tree. If the attribute is a required one, a value shall be supplied in an ancestor node. If the attribute is optional and no inherited value is specified, the default value shall be used.

An attribute can thus be defined once for a whole set of pages by specifying it in an intermediate page tree node and arranging the pages that share the attribute as descendants of that node.

> **EXAMPLE** A document might specify the same media box for all of its pages by including a MediaBox entry in the root
node of the page tree. If necessary, an individual page object can override this inherited value with a MediaBox entry of its own.

All values shall be inherited as-is, without merging, even for composite data types such as arrays and dictionaries. Thus (at least conceptually) the Resources dictionary for a page shall be found by searching the Page object and then each Pages object encountered by following Parent links up the pages tree from the Page towards the Catalog object. When the first Resources dictionary is found the search shall be stopped and that Resources dictionary shall be used in its entirety.

In a document conforming to Linearized PDF (see Annex F, "Linearized PDF" and Annex G, "Linearized PDF access strategies") all page attributes shall be specified explicitly as entries in the page dictionaries to which they apply; they shall not be inherited from an ancestor node.

"Figure 6 — Inheritance of attributes" illustrates the inheritance of attributes. In the page tree shown, pages 1, 2, and 4 are rotated clockwise by 90 degrees, page 3 by 270 degrees, page 6 by 180 degrees, and pages 5 and 7 not at all (0 degrees).


## Page 124

Figure 6 — Inheritance of attributes

### 7.7.4 Name dictionary

Some categories of objects in a PDF file can be referred to by name rather than by object reference. The correspondence between names and objects is established by the document’s name dictionary (PDF 1.2), located by means of the Names entry in the document’s catalog dictionary (see 7.7.2, "Document catalog dictionary"). Each entry in this dictionary designates the root of a name tree (see 7.9.6, "Name trees") defining names for a particular category of objects. "Table 32 — Entries in the name dictionary" shows the contents of the name dictionary.

Table 32 — Entries in the name dictionary

| Key | Type | Value |

| Dests | name | (Optional; PDF 1.2) A name tree mapping name strings to |
| --- | --- | --- |
| tree | destinations (see 12.3.2.4, "Named destinations"). |  |

| AP | name | (Optional; PDF 1.3) A name tree mapping name strings to annotation |
| --- | --- | --- |
| tree | appearance streams (see 12.5.5, "Appearance streams"). |  |

| JavaScript | name | (Optional; PDF 1.3) A name tree mapping name strings to document- |
| --- | --- | --- |
| tree | level ECMAScript actions (see 12.6.4.17, "ECMAScript actions"). |  |

| Pages | name | (Optional; PDF 1.3) A name tree mapping name strings to visible |
| --- | --- | --- |
| tree | pages for use in interactive forms (see 12.7.7, "Named pages"). |  |

| Templates | name | (Optional; PDF 1.3) A name tree mapping name strings to invisible |
| --- | --- | --- |
| tree | (template) pages for use in interactive forms (see 12.7.7, "Named pages"). |  |

| IDS | name | (Optional; PDF 1.3) A name tree mapping digital identifiers to Web. |
| --- | --- | --- |
| tree | Capture content sets (see 14.10.4, "Content sets"). |  |

| URLS | name | (Optional; PDF 1.3) A name tree mapping uniform resource locators. |
| --- | --- | --- |
| tree | (URLs) to Web Capture content sets (see 14.10.4, "Content sets"). |  |


## Page 125

| Key | Type | Value |

| EmbeddedFiles | name | (Optional; PDF 1.4) A name tree mapping name strings to file |
| --- | --- | --- |
| tree | specifications for embedded file streams (see 7.11.4, "Embedded file streams"). All File Specification dictionaries referenced from this name tree shall contain an EF key whose value is a dictionary which contains either an F or UF key whose value is an embedded file stream. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the name strings provided in this tree shall not contain or be derived from the encrypted payload document’s actual file name. This is to avoid potential disclosure of sensitive information in the original filename. The name string should match the value of F or UF in the referenced File Specification dictionary. |  |

| AlternatePresentations | name | (Optional; PDF 1.4; deprecated in PDF 2.0) A name tree mapping |
| --- | --- | --- |
| tree | name strings to alternate presentations (see 13.5, "Alternate presentations"). |  |

| Renditions | name | (Optional; PDF 1.5) A name tree mapping name strings (which shall |
| --- | --- | --- |
| tree | have Unicode encoding) to rendition objects (see 13.2.3, "Renditions"). |  |

## 7.8 Content streams and resources

### 7.8.1 General

Content streams are the primary means for describing the appearance of pages and other graphical elements. A content stream depends on information contained in an associated resource dictionary; in combination, these two objects form a self-contained entity. This subclause describes these objects.

### 7.8.2 Content streams

A content stream is a PDF stream object whose data consists of a sequence of instructions describing the graphical elements to be painted on a page. The instructions shall be represented in the form of PDF objects, using the same object syntax as in the rest of the PDF file. However, whereas the file as a whole is a static, random-access data structure, the objects in the content stream shall be interpreted and acted upon sequentially.

Each page of a document shall be represented by one or more content streams. Content streams shall also be used to package sequences of instructions as self-contained graphical elements, such as forms (see 8.10, "Form XObjects"), patterns (8.7, "Patterns"), certain fonts (9.6.4, "Type 3 fonts"), and annotation appearances (12.5.5, "Appearance streams").

A content stream, after decoding with any specified filters, shall be interpreted according to the PDF syntax rules described in 7.2, "Lexical conventions". It consists of PDF objects denoting operands and operators. The operands needed by an operator shall precede it in the stream. See Example 4 in 7.4, "Filters" for an example of a content stream.

An operand is a direct object belonging to any of the basic PDF data types except a stream. Dictionaries


## Page 126

shall be permitted as operands only by certain specific operators. Indirect objects and object references shall not be permitted at all.

An operator is a PDF keyword specifying some action that shall be performed, such as painting a graphical shape on the page. An operator keyword shall be distinguished from a name object by the absence of an initial SOLIDUS character (2Fh) (/). Operators shall be meaningful only inside a content stream.

> **NOTE 1** Annex A, "Operator Summary" provides a summary of all PDF operators.

> **NOTE 2** This postfix notation, in which an operator is preceded by its operands, is superficially the same
as in the PostScript language. However, PDF has no concept of an operand stack as the PostScript language has.
In PDF, all of the operands needed by an operator shall immediately precede that operator. Operators do not return results, and operands shall not be left over when an operator finishes execution.

> **NOTE 3** Most operators have to do with painting graphical elements on the page or with specifying
parameters that affect subsequent painting operations. The individual operators are described in the clauses devoted to their functions: Clause 8, "Graphics" describes operators that paint general graphics, such as filled areas, strokes, and sampled images, and that specify device-independent graphical parameters, such as colour.

Clause 9, "Text" describes operators that paint text using character glyphs defined in fonts.

Clause 10, "Rendering" describes operators that specify device-dependent rendering parameters.

Clause 14, "Document interchange" describes the marked-content operators that associate higher-level logical information with objects in the content stream. These operators do not affect the rendered appearance of the content; they specify information useful to applications that use PDF for document interchange.

Ordinarily, when a PDF reader encounters an operator in a content stream that it does not recognise, an error shall occur. A pair of compatibility operators, BX and EX (PDF 1.1), shall modify this behaviour (see "Table 33 — Compatibility operators"). These operators shall occur in pairs and may be nested.
They bracket a compatibility section, a portion of a content stream within which unrecognised operators shall be ignored without error. This mechanism enables a PDF processor to use operators defined in later versions of PDF without sacrificing compatibility with older applications. It should be used only in cases where ignoring such newer operators is the appropriate thing to do. The BX and EX
| operators are not themselves part of any graphics objec | t (see 8.2, "Graphics objects") or of the graphics |

state (8.4, "Graphics state").


## Page 127

Table 33 — Compatibility operators

| Operands | Operator | Description |

| — | BX | (PDF 1.1) Begin a compatibility section. Unrecognised operators (along with their operands) shall be ignored without error until the balancing EX operator is encountered. |

| — | EX | (PDF 1.1) End a compatibility section begun by a balancing BX operator. Ignore any unrecognised operands and operators from previous matching BX onward. |

### 7.8.3 Resource dictionaries

As stated in 7.8.2, "Content streams" the operands supplied to operators in a content stream shall only be direct objects; indirect objects and object references shall not be permitted. In some cases, an operator shall refer to a PDF object that is defined outside the content stream, such as a font dictionary or a stream containing image data. This shall be accomplished by defining such objects as named resources and referring to them by name from within the content stream.

Named resources shall be meaningful only in the context of a content stream. The scope of a resource name shall be local to a particular content stream and shall be unrelated to externally known identifiers for objects such as fonts. References from one object outside of content streams to another outside of content streams shall be made by means of indirect object references rather than named resources.

A content stream’s named resources shall be defined by a resource dictionary, which shall enumerate the named resources needed by the operators in the content stream and the names by which they can be referred to.

> **EXAMPLE 1** If a text operator appearing within the content stream needs a certain fo nt, the content stream’s resource
dictionary can associate the name F42 with the corresponding font dictionary. The text operator can use this name to refer to the font.

| A resource dictionary shall be associated with a content stream in one of the followin | g ways: |

• For a content stream that is the value of a page’s Contents entry (or is an element of an array that is the value of that entry), the resource dictionary shall be designated by the page dictionary’s Resources entry or is inherited, as described under 7.7.3.4, "Inheritance of page attributes" from some ancestor node of the page object. PDF writers should not use this inheritance feature of PDF as its use can cause undue complexity for a PDF reader. A PDF writer should only include resource definitions for resources that are actually referenced by the content streams of the associated page in the Resources dictionary. If the content streams of multiple pages require exactly the same set of resources, a single Resources dictionary may be shared between them by using indirect references. If each page requires different sets of resources, then each should be written with its own Resources dictionary.
• Content streams that define the glyph descriptions of a Type 3 font shall include a Resources entry in the Type 3 font dictionary specifying all the resources used by all the content streams in the CharProcs dictionary of a Type 3 font.
• For other content streams, a PDF writer shall include a Resources entry in the stream's
| dictionary specifying the resource dictionary which contains all the resources use | d by that |


## Page 128

content stream. This shall apply to content streams that define form XObjects, patterns, and annotation appearances.
• PDF files written obeying earlier versions of PDF may have omitted the Resources entry in all form XObjects and Type 3 fonts used on a page. All resources that are referenced from those
| forms and fonts shall be inherited from the resource dictionary of the page | on which they are |

used.

> **NOTE** The above bulleted list was clarified in this document (2020).
In the context of a given content stream, the term current resource dictionary refers to the resource dictionary associated with the stream in one of the ways described above. Each key in a resource dictionary shall be the name of a resource type, as shown in "Table 34 — Entries in a resource dictionary".

The corresponding values shall be as follows:

• For all resource types, the value shall be a subdictionary. Each key in the subdictionary shall be the name of a specific resource, and the corresponding value shall be a PDF object associated with the name.

Table 34 — Entries in a resource dictionary

| Key | Type | Value |

| ExtGState | dictionary | (Optional) A dictionary that maps resource names to graphics state parameter dictionaries (see 8.4.5, "Graphics state parameter dictionaries"). |

| ColorSpace | dictionary | (Optional) A dictionary that maps each resource name to either the name of a device-dependent colour space or an array describing a colour space (see 8.6, "Colour spaces"). |

| Pattern | dictionary | (Optional) A dictionary that maps resource names to pattern objects (see 8.7, "Patterns"). |

| Shading | dictionary | (Optional; PDF 1.3) A dictionary that maps resource names to shading dictionaries (see 8.7.4.3, "Shading dictionaries"). |

| XObject | dictionary | (Optional) A dictionary that maps resource names to external objects (see 8.8, "External objects"). |

| Font | dictionary | (Optional) A dictionary that maps resource names to font dictionaries (see 9, "Text"). |

| ProcSet | array | (Optional; deprecated in PDF 2.0) An array of predefined procedure set names (see 14.2, "Procedure sets"). |

| Properties | dictionary | (Optional; PDF 1.2) A dictionary that maps resource names to property list dictionaries for marked-content (see 14.6.2, "Property lists"). |


## Page 129

> **EXAMPLE 2** The following shows a resource dictionary containing fonts, and external objects. The fonts are specified
with a subdictionary associating the names F5, F6, F7, and F8 with objects 6, 8, 10, and 12, respectively.
Likewise, the XObject subdictionary associates the names Im1 and Im2 with objects 13 and 15, respectively.

<</Font <</F5 6 0 R /F6 8 0 R /F7 10 0 R /F8 12 0 R >> /XObject <</Im1 13 0 R /Im2 15 0 R >> >>

## 7.9 Common data structures

### 7.9.1 General

As mentioned at the beginning of this clause, there are some general-purpose data structures that are built from the basic object types described in 7.3, "Objects" and used throughout PDF technology. This subclause describes data structures for text strings, dates, rectangles, name trees, and number trees.
More complex data structures are described in 7.10, "Functions" and 7.11, "File specifications".

All of these data structures are meaningful only as part of the document hierarchy; they may not appear within content streams. In particular, the special conventions for interpreting the values of string objects apply only to strings outside content streams. An entirely different convention is used within content streams for using strings to select sequences of glyphs to be painted on the page (see
| clause 9, "Text"). "Table 35 — PDF data types" summarises the basic a | nd higher-level data types that |

are used throughout this document to describe the values of dictionary entries and other PDF data values.

Table 35 — PDF data types

| Type | Description | Subclause |

| ASCII string | Bytes containing ASCII characters | 7.9.2 |

| array | Array object | 7.3.6 |

| boolean | Boolean value | 7.3.2 |

| byte string | A series of bytes that shall represent | 7.9.2 |
| --- | --- | --- |
| characters or other binary data. If | 7.9.2.4 such a type represents characters, the encoding shall be determined by the context. |  |

| date | Date (ASCII string) | 7.9.4 |

| dictionary | Dictionary object | 7.3.7 |

| file specification | File specification (string or | 7.11 dictionary) |

| function | Function (dictionary or stream) | 7.10 |


## Page 130

| Type | Description | Subclause |

| integer | Integer number | 7.3.3 |

| name | Name object | 7.3.5 |

| name tree | Name tree (dictionary) | 7.9.6 |

| null | Null object | 7.3.9 |

| number | Number (integer or real) | 7.3.3 |

| number tree | Number tree (dictionary) | 7.9.7 |

| PDFDocEncoded string | Bytes containing a string that shall | 7.9.2 |
| --- | --- | --- |
| be encoded using PDFDocEncoding | 7.9.2.3 |  |

| rectangle | Rectangle (array) | 7.9.5 |

| stream | Stream object | 7.3.8 |

| string | Any string that is not a text string. | 7.9.2 Beginning with PDF 1.7, this type is further qualified as the types: ASCII string and byte string. |

| text string | Bytes that represent characters that | 7.9.2 |
| --- | --- | --- |
| shall be encoded using either | 7.9.2.2 PDFDocEncoding, UTF-16BE or UTF- 8 (as defined in 7.9.2.2, "Text string type".) |  |

| text stream | Text stream | 7.9.3 |

### 7.9.2 String object types

#### 7.9.2.1 General

PDF supports one fundamental string object (see 7.3.4, "String objects"). The string object shall be further qualified as a text string, ASCII string, or byte string. The further qualification reflects the encoding used to represent the characters or glyphs described by the string.

The string types described in "Table 35 — PDF data types" specify increasingly specific encoding
| schemes, as shown in "Figure 7 — Relationship between string types | ". |


## Page 131

Figure 7 — Relationship between string types

#### 7.9.2.2 Text string type

##### 7.9.2.2.1 General

The text string type shall be used for character strings that contain information intended to be humanreadable, such as text annotations, document outline item names, article names, and so forth.

> **NOTE 1** Text string type is a subtype of string type and represents data encoded using specific
conventions.
The text string type shall be used for character strings that shall be encoded in PDFDocEncoding, the UTF-16BE Unicode character encoding scheme, or (PDF 2.0) the UTF-8 Unicode character encoding scheme. PDFDocEncoding can encode all of the ISO Latin 1 character set and is documented in Annex D, "Character sets and encodings". UTF-16BE and UTF-8 can encode all Unicode characters. UTF-16BE, UTF-8 and Unicode character encoding are described in The Unicode Standard by the Unicode Consortium.

> **EXAMPLE 1** A PDF dictionary containing key ‘Key’ with the value that is the text string "text‰" will look like

<</Key(text?)>>

where the character ‘?’ after the ‘text’ is represented by the hex code 8B (octal code 213 - that is according to "D.2 Latin character set and encodings").

> **NOTE 2** PDFDocEncoding does not support all Unicode characters whereas UTF-16BE and UTF-8 do.
For text strings encoded in UTF-16BE, the first two bytes shall be 254 followed by 255. These two bytes represent the Unicode byte order marker, ZERO WIDTH NO-BREAK SPACE (U+FEFF), indicating that the string is encoded in the UTF-16BE (big-endian) encoding scheme specified in Unicode.

> **EXAMPLE 2** A PDF dictionary containing key ‘Key’ with the value that is the text string "тест" (that is what the word in
Russian with the translation to English as ‘test’) will look like

<</Key(??????????)>> where the characters in parentheses is the sequence of bytes with hex codes FE, FF, 04, 42, 04, 35, 04, 41, 04, 42.

> **NOTE 3** This mechanism precludes beginning a string using PDFDocEncoding with the two characters
thorn ydieresis, which is unlikely to be a meaningful beginning of a word or phrase.
For text strings encoded in UTF-8, the first three bytes shall be 239 followed by 187, followed by 191.
These three bytes represent the Unicode byte order marker indicating that the string is encoded in the UTF-8 encoding scheme specified in Unicode.


## Page 132

> **NOTE 4** This mechanism precludes beginning a string using PDFDocEncoding with the three characters
dieresis, guillemotright, questiondown, which is unlikely to be a meaningful beginning of a word or phrase.
PDF readers that process PDF files containing Unicode text strings shall be prepared to handle supplementary characters; that is, characters requiring more than two bytes to represent.

> **NOTE 5** It is important not to confuse UTF-16BE with UCS2 (i.e. wchar_t). UTF-16 is not a fixed width
encoding scheme.

##### 7.9.2.2.2 Text string language escape sequences

Escape sequences may appear anywhere in a Unicode text string to indicate the language in which subsequent text shall be written.

> **NOTE 1** This is useful when the language cannot be determined from the character codes used in the text.
The escape sequence shall consist of the following elements, in order:

a) The Unicode value ESCAPE ( U+001B) (that is, for strings encoded in UTF -16BE, the byte sequence 0
| followed by 27; for strings | encoded in UTF | -8 the byte value 27). |

b) A 2- byte BCP 47 language code.

> **EXAMPLE 1** en for English or ja for Japanese encoded as ASCII characters.

c) (Optional) A 2-byte ISO 3166 country code.

> **EXAMPLE 2** US for the United States or JP for Japan encoded as ASCII characters.

d) The Unicode value ESCAPE (U+001B).

> **NOTE 2** The complete list of codes defined by BCP 47 and ISO 3166 can be obtained from the Internet
| Engineering Task Force and the International O | rganization for Standardization | . |

> **NOTE 3** Since Unicode defines an escape sequence for indicating the language of the text , this mechanism
| enables the alternate description to change from the language specified by the prevailing | Lang entry. |

#### 7.9.2.3 PDFDocEncoded string type

# A PDFDocEncoded string is a characte r string in which the characters shall be represented in a single

byte using PDFDocEncoding.

> **NOTE** PDFDocEncoding does not support all Unicode characters whereas UTF -16BE or UTF-8 do.

#### 7.9.2.4 Byte string type

| The byte string type shall be used for binary data that | shall be represented as a series of bytes, where |
| --- | --- |
| each byte may be any value representable in 8 bits. Byte string type is a subtype of string type. | For |

example, byte strings are used to define a file identifier (see 14.4, "File identifiers") that is specified in ID entry of PDF file trailer (see "Table 15 — Entries in the file trailer dictionary"). In such case byte string is written in hexadecimal form (see 7.3.4.3, "Hexadecimal strings") and looks like

<B6FB54F3F8554D478DC874F11DAD0F11>

> **NOTE** The string can represent characters but the encoding is not known. The bytes of the string do not
have to represent characters.


## Page 133

### 7.9.3 Text streams

A text stream (PDF 1.5) shall be a PDF stream object (7.3.8, "Stream objects") whose unencoded bytes shall meet the same requirements as a text string (7.9.2.2, "Text string type") with respect to encoding, byte order, and lead bytes.

### 7.9.4 Dates

Date values used in a PDF shall conform to a standard date format, which closely follows that of the international standard ASN.1 (Abstract Syntax Notation One), defined in ISO/IEC 8824-1. A date shall be a text string value containing no white-space, of the form:

(D:YYYYMMDDHHmmSSOHH'mm)

where:

YYYY shall be the year

MM shall be the month (01–12)

DD shall be the day (01–31)

HH shall be the hour (00–23)

mm shall be the minute (00–59)

SS shall be the second (00–59)

O shall be the relationship of local time to Universal Time (UT), and shall be denoted by one of the characters PLUS SIGN (U+002B) (+), HYPHEN-MINUS (U+002D) (-), or LATIN CAPITAL LETTER Z (U+005A) (Z) (see below)

HH followed by APOSTROPHE (U+0027) (') shall be the absolute value of the offset from UT in hours (00–23)

mm shall be the absolute value of the offset from UT in minutes (00–59)

The prefix “D:” shall be present, the year field (YYYY) shall be present and all other fields may be present but only if all of their preceding fields are also present. The APOSTROPHE following the hour offset field (HH) shall only be present if the HH field is present. The minute offset field (mm) shall only be present if the APOSTROPHE following the hour offset field (HH) is present. The default values for MM and DD shall be both 01; all other numerical fields shall default to zero values. A PLUS SIGN as the value of the O field signifies that local time is now and later than UT, a HYPHEN-MINUS signifies that local time is earlier than UT, and the LATIN CAPITAL LETTER Z signifies that local time is equal to UT.
If no UT information is specified, the relationship of the specified time to UT shall be considered to be GMT. Regardless of whether the time zone is specified, the rest of the date shall be specified in local time.

> **EXAMPLE** For example, December 23, 1998, at 7:52 PM, U.S. Pacific Standard Time, is represented by the string:

D:199812231952-08'00

> **NOTE 1** A date string can be any valid PDF string object as described in 7.3.4, "String objects". The
description above relates to the text string value after appropriate processing.


## Page 134

> **NOTE 2** PDF versions up to and including 1.7 defined a date string to include a terminating apostrophe.
PDF processors are recommended to accept date strings that still follow that convention.

> **NOTE 3** The letter Z can optionally be followed by hour and minute offsets, which are zero in this case.

### 7.9.5 Rectangles

Rectangles are used to describe locations on a page and bounding boxes for a variety of objects. A rectangle shall be written as an array of four numbers giving the coordinates of a pair of diagonally opposite corners.

Typically, the array takes the form

[llx lly urx ury]

specifying the lower-left x, lower-left y, upper-right x, and upper-right y coordinates of the rectangle, in
| that order. The other two corners of the rectangle are then assumed to have coordinates ( | llx, ury) and |

(urx, lly).

> **NOTE** Rectangles can have a width of zero or height of zero.

### 7.9.6 Name trees

A name tree serves a similar purpose to a dictionary — associating keys and values — but by different means. A name tree differs from a dictionary in the following important ways:

• Unlike the keys in a dictionary, which are name objects, those in a name tree are strings.
• The keys are ordered.
• The values associated with the keys may be objects of any type. Stream objects shall be specified by indirect object references (7.3.8, "Stream objects"). The dictionary, array, and string objects should be specified by indirect object references, and other PDF objects (null objects, numbers, booleans, and names) should be specified as direct objects.
• The data structure can represent an arbitrarily large collection of key-value pairs, which can be looked up efficiently without requiring the entire data structure to be read from the PDF file. (In contrast, a dictionary can be subject to an implementation limit on the number of entries it can contain.) A name tree shall be constructed of nodes, each of which shall be a dictionary object. "Table 36 — Entries in a name tree node dictionary" shows the entries in a node dictionary. The nodes shall be of three kinds, depending on the specific entries they contain. The tree shall always have exactly one root node, which shall contain a single entry: either Kids or Names but not both. If the root node has a Names entry, it shall be the only node in the tree. If it has a Kids entry, each of the remaining nodes shall be either an intermediate node, that shall contain a Limits entry and a Kids entry, or a leaf node, that shall contain a Limits entry and a Names entry.


## Page 135

Table 36 — Entries in a name tree node dictionary

| Key | Type | Value |

| Kids | array | (Root and intermediate nodes only; required in intermediate nodes; present in the root node if and only if Names is not present) Shall be an array of indirect references to the immediate children of this node. The children may be intermediate or leaf nodes. |

| Names | array | (Root and leaf nodes only; required in leaf nodes; present in the root node if and only if Kids is not present) Shall be an array of the form [key1 value1 key2 value2 …keyn valuen] where each keyi shall be a string and the corresponding valuei shall be the object associated with that key. The keys shall be sorted in lexical order, as described below. |

| Limits | array | (Required for intermediate and leaf nodes; not permitted in root nodes) Shall be an array of two strings, that shall specify the (lexically) least and greatest keys included in the Names array of a leaf node or in the Names arrays of any leaf nodes that are descendants of an intermediate node. |

The Kids entries in the root and intermediate nodes define the tree’s structure by identifying the immediate children of each node. The Names entries in the leaf (or root) nodes shall contain the tree’s keys and their associated values, arranged in key-value pairs and shall be sorted lexically in ascending order by key. Shorter keys shall appear before longer ones beginning with the same byte sequence.
Any encoding of the keys may be used as long as it is self-consistent; keys shall be compared for equality on a simple byte-by-byte basis.

The keys contained within the various nodes’ Names entries shall not overlap; each Names entry shall contain a single contiguous range of all the keys in the tree. In a leaf node, the Limits entry shall specify the least and greatest keys contained within the node’s Names entry. In an intermediate node, it shall specify the least and greatest keys contained within the Names entries of any of that node’s descendants. The value associated with a given key can thus be found by walking the tree in order, searching for the leaf node whose Names entry contains that key.

> **EXAMPLE 1** The following is an abbreviated outline, showing object numbers and nodes, of a name tree that maps the
names of all the chemical elements, from actinium to zirconium, to their atomic numbers.

> **EXAMPLE** of a name tree

1: Root node 2: Intermediate node: Actinium to Gold 5: Leaf node: Actinium = 25,…, Astatine = 31 25: Integer: 89 … 31: Integer: 85 … 11: Leaf node: Gadolinium = 56,…, Gold = 59 56: Integer: 64 … 59: Integer: 79 3: Intermediate node: Hafnium to Protactinium 12: Leaf node: Hafnium = 60,…, Hydrogen = 65 60: Integer: 72


## Page 136

… 65: Integer: 1 … 19: Leaf node: Palladium = 92,…, Protactinium = 100 92: Integer: 46 … 100:Integer: 91 4: Intermediate node: Radium to Zirconium 20: Leaf node: Radium = 101,…, Ruthenium = 107 101:Integer: 89 … 107:Integer: 85 … 24: Leaf node: Xenon = 129,…, Zirconium = 133 129:Integer: 54 … 133:Integer: 40

> **EXAMPLE 2** The following shows the representation of this tree in a PDF file:

1 0 obj
| <</Kids [2 0 R | %Root node 3 0 R 4 0 R ] |

>>

endobj

2 0 obj
| <</Limits [(Actinium) (Gold)] | %Intermediate node /Kids [5 0 R 6 0 R 7 0 R 8 0 R 9 0 R 10 0 R 11 0 R ] >> |

endobj

3 0 obj
| <</Limits [(Hafnium) (Protactinium)] | %Intermediate node /Kids [12 0 R 13 0 R 14 0 R 15 0 R 16 0 R 17 0 R 18 0 R 19 0 R ] >> |

endobj

4 0 obj
| <</Limits [(Radium) (Zirconium)] | %Intermediate node /Kids [20 0 R 21 0 R 22 0 R 23 0 R 24 0 R ] >> |

endobj


## Page 137

5 0 obj
| <</Limits [(Actinium) (Astatine)] | ` | %Leaf node /Names [(Actinium) 25 0 R (Aluminum) 26 0 R (Americium) 27 0 R (Antimony) 28 0 R (Argon) 29 0 R (Arsenic) 30 0 R (Astatine) 31 0 R ] >> endobj … |

24 0 obj
| <</Limits [(Xenon) (Zirconium)] | %Leaf node /Names [(Xenon) 129 0 R (Ytterbium) 130 0 R (Yttrium) 131 0 R (Zinc) 132 0 R (Zirconium) 133 0 R ] >> endobj |

25 0 obj %Atomic number (Actinium) endobj … 133 0 obj %Atomic number (Zirconium) endobj

### 7.9.7 Number trees

A number tree is similar to a name tree (see 7.9.6, "Name trees"), except that its keys shall be integers instead of strings and shall be sorted in ascending numerical order. The entries in the leaf (or root) nodes containing the key-value pairs shall be named Nums instead of Names as in a name tree. "Table 37 — Entries in a number tree node dictionary" shows the entries in a number tree’s node dictionaries.

Table 37 — Entries in a number tree node dictionary

| Key | Type | Value |

| Kids | array | (Root and intermediate nodes only; required in intermediate nodes; present in the root node if and only if Nums is not present) Shall be an array of indirect references to the immediate children of this node. The children may be intermediate or leaf nodes. |

| Nums | array | (Root and leaf nodes only; shall be required in leaf nodes; present in the root node if and only if Kids is not present) Shall be an array of the form: [key1 value1 key2 value2 …keyn valuen] where each keyi is an integer and the corresponding valuei shall be the object associated with that key. The keys shall be sorted in numerical order, analogously to the arrangement of keys in a name tree as described in 7.9.6, "Name trees". |


## Page 138

| Key | Type | Value |

| Limits | array | (Shall be present in Intermediate and leaf nodes only) Shall be an array of two integers, that shall specify the (numerically) least and greatest keys included in the Nums array of a leaf node or in the Nums arrays of any leaf nodes that are descendants of an intermediate node. |

## 7.10 Functions

### 7.10.1 General

PDF is not a programming language, and a PDF file is not a program. However, PDF provides several types of function objects (PDF 1.2) that represent parameterised classes of functions, including mathematical formulas and sampled representations with arbitrary resolution.

> **NOTE 1** Functions can be used in various ways in PDF, including device-dependent rasterization
information for high-quality printing (halftone spot functions and transfer functions), colour transform functions for certain colour spaces, and specification of colours as a function of position for smooth shadings.
Functions in PDF represent static, self-contained numerical transformations.

> **NOTE 2** A function to add two numbers has two input values and one output value:
𝑓(𝑥0,𝑥1)= 𝑥0 + 𝑥1

Similarly, a function that computes the arithmetic and geometric mean of two numbers can be viewed as a function of two input values and two output values:

| 𝑓(𝑥 | ,𝑥  )= | 𝑥0  +  𝑥1 , √𝑥 | × 𝑥 |  |
| --- | --- | --- | --- | --- |
| 0 | 1 | 2 | 0 | 1 |

In general, a function can take any number (m) of input values and produce any number (n) of output values:

| 𝑓(𝑥0,…, | 𝑥𝑚−1)= | 𝑦0, …  ,𝑦𝑛−1 |

In PDF functions, all the input values and all the output values shall be numbers, and functions shall have no side effects.

Each function definition includes a domain, the set of valid values for the input. Some types of functions also define a range, the set of valid values for the output. Input values passed to the function shall be clipped to the domain, and output values produced by the function shall be clipped to the range.

> **EXAMPLE** Suppose the following function is defined with a domain of [-1 1]. If the function is called with the input value
6, that value is replaced with the nearest value in the defined domain, 1, before the function is evaluated; the resulting output value is therefore 3.

𝑓(𝑥)= 𝑥 + 2

Similarly, if the following function is defined with a range of [0 100], and if the input values -6 and 4 are


## Page 139

passed to the function (and are within its domain), then the output value produced by the function, -14, is replaced with 0, the nearest value in the defined range.

𝑓(𝑥0,𝑥1)= 3 × 𝑥0 + 𝑥1

A function object may be a dictionary or a stream, depending on the type of function. The term function dictionary is used generically in this subclause to refer to either a dictionary object or the dictionary portion of a stream object. A function dictionary specifies the function’s representation, the set of attributes that parameterize that representation, and the additional data needed by that representation. Four types of functions are available, as indicated by the dictionary’s FunctionType entry:

• (PDF 1.2) A sampled function (Type 0) uses a table of sample values to define the function. Various techniques are used to interpolate values between the sample values; see 7.10.2, "Type 0 (sampled) functions" • (PDF 1.3) An exponential interpolation function (Type 2) defines a set of coefficients for an exponential function; see 7.10.3, "Type 2 (exponential interpolation) functions" • (PDF 1.3) A stitching function (Type 3) is a combination of other functions, partitioned across a domain; see 7.10.4, "Type 3 (stitching) functions" • (PDF 1.3) A PostScript calculator function (Type 4) uses operators from the PostScript language to describe an arithmetic expression; see 7.10.5, "Type 4 (PostScript calculator) functions" All function dictionaries shall share the entries listed in "Table 38 — Entries common to all function dictionaries".

Table 38 — Entries common to all function dictionaries

| Key | Type | Value |

| FunctionType | integer | (Required) The function type: |

# 0 Sampled function

# 2 Exponential interpolation function

# 3 Stitching function

# 4 PostScript calculator function

| Domain | array | (Required) An array of 2 × m numbers, where m shall be the number of input values. For each i from 0 to m - 1, Domain2i shall be less than or equal to Domain2i+1, and the ith input value, xi, shall lie in the interval Domain2i ≤ xi ≤ Domain2i+1 . Input values outside the declared domain shall be clipped to the nearest boundary value. |

| Range | array | (Required for Type 0 and Type 4 functions, optional otherwise; see below) An array of 2 × n numbers, where n shall be the number of output values. For each j from 0 to n - 1, Range2j shall be less than or equal to Range2j+1, and the jth output value, yj, shall lie in the interval Range2j ≤ yj ≤ Range2j+1. Output values outside the declared range shall be clipped to the nearest boundary value. If this entry is absent, no clipping shall be done (subject to implementation limits). |


## Page 140

In addition, each type of function dictionary shall include entries appropriate to the particular function type. The number of output values can usually be inferred from other attributes of the function; if not (as is always the case for Type 0 and Type 4 functions), the Range entry is required. The dimensionality of the function implied by the Domain and Range entries shall be consistent with that implied by other attributes of the function.

| 𝑦  =  Interpolate(𝑥, | 𝑥min,𝑥max, | 𝑦min, 𝑦max) |
| --- | --- | --- |
| = 𝑦 | +  ((𝑥  −  𝑥 | )×  𝑦max−𝑦min) |
| min | min | 𝑥max−𝑥min |

For a given value of x, Interpolate calculates the y value on the line defined by the two points (xmin, ymin) and (xmax, ymax).

### 7.10.2 Type 0 (sampled) functions

Type 0 functions use a sequence of sample values (contained in a stream) to provide an approximation for functions whose domains and ranges are bounded. The samples are organised as an m-dimensional table in which each entry has n components.

> **NOTE 1** Sampled functions are highly general and offer reasonably accurate representations of arbitrary
analytic functions at low expense. For example, a 1-input sinusoidal function can be represented over the range [0 180] with an average error of only 1 percent, using just ten samples and linear interpolation. Two-input functions require significantly more samples but usually not a prohibitive number if the function does not have high frequency variations.
There shall be no dimensionality limit of a sampled function except for possible implementation limits.

> **NOTE 2** The number of samples required to represent functions with high dimensionality multiplies
rapidly unless the sampling resolution is very low. Also, the process of multilinear interpolation becomes computationally intensive if the number of inputs m is greater than 2. The multidimensional spline interpolation is even more computationally intensive.
In addition to the entries in "Table 38 — Entries common to all function dictionaries", a Type 0 function dictionary includes those shown in "Table 39 — Additional entries specific to a Type 0 function dictionary".

The Domain, Encode, and Size entries determine how the function’s input variable values are mapped into the sample table. For example, if Size is [21 31], the default Encode array shall be [0 20 0 30], which maps the entire domain into the full set of sample table entries. Other values of Encode may be used.

The interpolate function (7.10.1, "General") explains the relationship between Domain, Encode, Size, Decode, and Range.

Table 39 — Additional entries specific to a Type 0 function dictionary

| Key | Type | Value |

| Size | array | (Required) An array of m positive integers that shall specify the number of samples in each input dimension of the sample table. |


## Page 141

| Key | Type | Value |

| BitsPerSample | integer | (Required) The number of bits that shall represent each sample. (If the function has multiple output values, each one shall occupy BitsPerSample bits.) Valid values shall be 1, 2, 4, 8, 12, 16, 24, and 32. |

| Order | integer | (Optional) The order of interpolation between samples. Valid values shall be 1 and 3, specifying linear and cubic spline interpolation, respectively. Default value: 1. |

| Encode | array | (Optional) An array of 2 × m numbers specifying the linear mapping of input values into the domain of the function’s sample table. Default value: [0 (Size0 -1) 0 (Size1 - 1)…]. |

| Decode | array | (Optional) An array of 2 × n numbers specifying the linear mapping of sample values into the range appropriate for the function’s output values. Default value: same as the value of Range. |

| other stream | (various) | (Optional) Other attributes of the stream that shall provide the sample values, |
| --- | --- | --- |
| attributes | as appropriate (see "Table 5 — Entries common to all stream dictionaries"). |  |

When a sampled function is called, each input value xi, for 0 ≤ i < m, shall be clipped to the domain:
| x′ =  min(max (x | ,Domain | ), Domain | ) |
| --- | --- | --- | --- |
| i | i | 2i | 2i+1 |

That value shall be encoded:
| e  =  Interpolate (x′, | Domain | ,Domain | , Encode | , Encode | ) |
| --- | --- | --- | --- | --- | --- |
| i | i | 2i | 2i+1 | 2i | 2i+1 |

That value shall be clipped to the size of the sample table in that dimension:
| e′ =  min (max(ei, | 0),Sizei | −  1) |

i

The encoded input values shall be real numbers, not restricted to integers. Interpolation shall be used to determine output values from the nearest surrounding values in the sample table. Each output value rj, for 0 ≤ j < n, shall then be decoded:
| rj′=  Interpolate (rj,0, | 2BitsPerSample− | 1, Decode2j, | Decode2j+1) |

Finally, each decoded value shall be clipped to the range:
| yj =  min(max (rj′, | Range2j), | Range2j+1) |

Sample data shall be represented as a stream of bytes. The bytes shall constitute a continuous bit stream, with the high-order bit of each byte first. Each sample value shall be represented as a sequence of BitsPerSample bits. Successive values shall be adjacent in the bit stream; there shall be no padding at byte boundaries.

For a function with multidimensional input (more than one input variable), the sample values in the first dimension vary fastest, and the values in the last dimension vary slowest.


## Page 142

> **EXAMPLE 1** For a function f(a, b, c), where a, b, and c vary from 0 to 9 in steps of 1, the sample values would appear in
this order: f(0, 0, 0), f(1, 0, 0),…, f(9, 0, 0), f(0, 1, 0), f(1, 1, 0),…, f(9, 1, 0), f(0, 2, 0), f(1, 2, 0),…, f(9, 9, 0), f(0, 0, 1), f(1, 0, 1), and so on.

For a function with multidimensional output (more than one output value), the values shall be stored in the same order as Range.

The stream data shall be long enough to contain the entire sample array, as indicated by Size, Range, and BitsPerSample; see 7.3.8.2, "Stream extent".

> **EXAMPLE 2** illustrates a sampled function with 4-bit samples in an array containing 21 columns and 31
rows (651 values). The function takes two arguments, x and y, in the domain [-1.0 1.0], and returns one value, z, in that same range. The x argument shall be linearly transformed by the encoding to the domain [0 20] and the y argument to the domain [0 30]. Using bilinear interpolation between sample points, the function computes a value for z, which (because BitsPerSample is 4) will be in the range [0 15], and the decoding transforms z to a number in the range [-1.0 1.0] for the result. The sample array shall be stored in a string of 326 bytes, calculated as follows (rounded up):

326 bytes = 31 rows × 21 samples /row × 4 bits /sample ÷ 8 bits /byte

The first byte contains the sample for the point (-1.0, -1.0) in the high-order 4 bits and the sample for the point (-0.9, -1.0) in the low-order 4 bits.

> **EXAMPLE** 2

14 0 obj <</FunctionType 0 /Domain [-1.0 1.0 -1.0 1.0] /Size [21 31] /Encode [0 20 0 30] /BitsPerSample 4 /Range [-1.0 1.0] /Decode [-1.0 1.0] /Length … /Filter… >> stream …651 sample values… endstream endobj

> **NOTE 3** The Decode entry can be used creatively to increase the accuracy of encoded samples
corresponding to certain values in the range.

> **EXAMPLE 3** If the range of the function is [-1.0 1.0] and BitsPerSample is 4, the usual value of Decode would be [-1.0
1.0] and the sample values would be integers in the interval [0 15] (as shown in "Figure 8 — Mapping with the Decode array"). But if these values are used, the midpoint of the range, 0.0, is not represented exactly by any sample value, since it falls halfway between 7 and 8. However, if the Decode array is [-1.0 +1.1429] (1.1429 being approximately equal to 16 ÷ 14) and the sample values supplied are in the interval [0 14], the


## Page 143

effective range of [-1.0 1.0] is achieved, and the range value 0.0 is represented by the sample value 7.

Figure 8 — Mapping with the Decode array The Size value for an input dimension can be 1, in which case all input values in that dimension shall be mapped to the single allowed value. If Size is less than 4, cubic spline interpolation is not possible and Order 3 shall be ignored if specified.

### 7.10.3 Type 2 (exponential interpolation) functions

Type 2 functions (PDF 1.3) include a set of parameters that define an exponential interpolation of one input value and n output values:

𝑓(𝑥)= 𝑦0, … ,𝑦𝑛−1

In addition to the entries in "Table 38 — Entries common to all function dictionaries", a Type 2 function dictionary shall include those listed in "Table 40 — Additional entries specific to a Type 2 function dictionary".

Table 40 — Additional entries specific to a Type 2 function dictionary

| Key | Type | Value |

| C0 | array | (Optional) An array of n numbers that shall define the function result when |
| --- | --- | --- |
| 𝑥  = | 0.0. Default value: [0.0]. |  |

| C1 | array | (Optional) An array of n numbers that shall define the function result when 𝑥  =  1.0. Default value: [1.0]. |

| N | number | (Required) The interpolation exponent. Each input value x shall return n |
| --- | --- | --- |
| values, given by yj | = C0j  +  xN ×  (C1j −  C0j), for 0  ≤  j<  n24T. |  |

Values of Domain shall constrain x in such a way that if N is not an integer, all values of x will be nonnegative, and if N is negative, no value of x will be zero. Typically, Domain is declared as [0.0 1.0], and N is a positive number. To clip the output to a specified range the Range attribute shall be used.

> **NOTE** When N is 1, the function performs a linear interpolation between C0 and C1; therefore, the
| function can also be expressed as a sampled function ( | Type 0). |

### 7.10.4 Type 3 (stitching) functions

Type 3 functions (PDF 1.3) define a stitching of the subdomains of several 1-input functions to produce


## Page 144

a single new 1-input function. Since the resulting stitching function is a 1-input function, the domain is given by a two-element array, [Domain0 Domain1].

In addition to the entries in "Table 38 — Entries common to all function dictionaries", a Type 3 function dictionary shall include those listed in "Table 41 — Additional entries specific to a Type 3 function dictionary".

Table 41 — Additional entries specific to a Type 3 function dictionary

| Key | Type | Value |

| Functions | array | (Required) An array of k, 1-input functions that shall make up the stitching function. The output dimensionality of all functions shall be the same, and compatible with the value of Range if Range is present. |

| Bounds | array | (Required) An array of 𝑘 | − | 1 numbers that, in combination with Domain, shall define the intervals to which each function from the Functions array shall apply. Bounds elements shall be in order of increasing value, and each value shall be within the domain defined by Domain. |

| Encode | array | (Required) An array of 2 | × | 𝑘 numbers that, taken in pairs, shall map each subset of the domain defined by Domain and the Bounds array to the domain of the corresponding function. |

| Domain shall be of size 2 (that is, 𝑚 | = | 1), and Domain0 shall be strictly less than Domain1 unless |
| --- | --- | --- |
| 𝑘  = | 1. The domain shall be partitioned into k subdomains, as indicated by the dictionary’s Bounds |  |
| entry, which shall be an array of 𝑘 | − | 1 numbers that obey the following relationships (with exceptions |

as noted below):

| 𝐷𝑜𝑚𝑎𝑖𝑛0 | ≤ | 𝐵𝑜𝑢𝑛𝑑𝑠0 | <  𝐵𝑜𝑢𝑛𝑑𝑠1 | <  ⋯ | <  𝐵𝑜𝑢𝑛𝑑𝑠𝑘−2 | ≤ | 𝐷𝑜𝑚𝑎𝑖𝑛1 |

The Bounds array shall describe a series of k half-open intervals, closed on the left and open on the right with the following exceptions:

• the last interval, shall always be closed on the right, • if 𝐷𝑜𝑚𝑎𝑖𝑛0 = 𝐵𝑜𝑢𝑛𝑑𝑠0 then the first interval shall be closed on both the left and right and the second (next) interval shall be open on the left.

> **EXAMPLE 1** Using the normal notation where ( and ) mean open on the left and right, respectively, and [ and ] mean close
on the left and right, the following cases are depicted:

| 𝑘  = 1 and 𝐷𝑜𝑚𝑎𝑖𝑛0 | ≤ 𝐷𝑜𝑚𝑎𝑖𝑛1 results in one interval [𝐷𝑜𝑚𝑎𝑖𝑛0,𝐷𝑜𝑚𝑎𝑖𝑛1]. |

| 𝑘  = 2 and 𝐷𝑜𝑚𝑎𝑖𝑛0 | <  𝐵𝑜𝑢𝑛𝑑𝑠0 | ≤ 𝐷𝑜𝑚𝑎𝑖𝑛1results in two intervals [𝐷𝑜𝑚𝑎𝑖𝑛0,𝐵𝑜𝑢𝑛𝑑𝑠0); [𝐵𝑜𝑢𝑛𝑑𝑠0,𝐷𝑜𝑚𝑎𝑖𝑛1]. |

| 𝑘  = 2 and 𝐷𝑜𝑚𝑎𝑖𝑛0 | = 𝐵𝑜𝑢𝑛𝑑𝑠0 | < 𝐷𝑜𝑚𝑎𝑖𝑛1results in two intervals [𝐷𝑜𝑚𝑎𝑖𝑛0,𝐵𝑜𝑢𝑛𝑑𝑠0]; |
| --- | --- | --- |
| (𝐵𝑜𝑢𝑛𝑑𝑠0, | 𝐷𝑜𝑚𝑎𝑖𝑛1]. |  |

| 𝑘  = 3 and 𝐷𝑜𝑚𝑎𝑖𝑛0 | <  𝐵𝑜𝑢𝑛𝑑𝑠0 | <  𝐵𝑜𝑢𝑛𝑑𝑠1 | <  𝐷𝑜𝑚𝑎𝑖𝑛1 results in three intervals [𝐷𝑜𝑚𝑎𝑖𝑛0,𝐵𝑜𝑢𝑛𝑑𝑠0); |
| --- | --- | --- | --- |
| [𝐵𝑜𝑢𝑛𝑑𝑠0, | 𝐵𝑜𝑢𝑛𝑑𝑠1); [𝐵𝑜𝑢𝑛𝑑𝑠1, | 𝐷𝑜𝑚𝑎𝑖𝑛1]. |  |

| 𝑘  = 3 𝑎𝑛𝑑 𝐷𝑜𝑚𝑎𝑖𝑛0 | = 𝐵𝑜𝑢𝑛𝑑𝑠0 | <  𝐵𝑜𝑢𝑛𝑑𝑠1 | < 𝐷𝑜𝑚𝑎𝑖𝑛1 results in three intervals | [𝐷𝑜𝑚𝑎𝑖𝑛0,𝐵𝑜𝑢𝑛𝑑𝑠0]; |


## Page 145

| (𝐵𝑜𝑢𝑛𝑑𝑠0,𝐵𝑜𝑢𝑛𝑑𝑠1); [𝐵𝑜𝑢𝑛𝑑𝑠1, | 𝐷𝑜𝑚𝑎𝑖𝑛1]. |

The value of the Functions entry shall be an array of k functions. The first function shall apply to x values in the first subdomain (interval), defined by Domain0 and Bounds0 as defined just above; the second function shall apply to x values in the second subdomain, defined by Bounds0 and Bounds1; and so on. The last function shall apply to x values in the last subdomain, which includes the upper bound defined by Boundsk-2 and Domain1. The value of k may be 1, in which case the Bounds array shall be empty and the single item in the Functions array shall apply to all x values, Domain0 ≤ x ≤ Domain1.

| The Encode array contains 2 | × | 𝑘 numbers. A value x from the ith subdomain shall be encoded as |

follows:
| x′  =  Interpolate(x, | Boundsi−1, | Boundsi, | Encode2i, | Encode2i+1) |

| for 0  ≤ | 𝑖  <  𝑘. In this equation, Bounds-1 means Domain0, and Boundsk-1 means Domain1. If the last |

bound, Boundsk-2, is equal to Domain1, then x′ shall be defined to be Encode2(k-1). For the degenerate case, if the first bound, Bounds0, is equal to Domain0 then x′ shall be defined to be Encode0.

> **NOTE** The stitching function is designed to make it easy to combine several functions to be used within
one shading pattern over different parts of the shading’s domain. Shading patterns are discussed in 8.7.4, "Shading patterns". The same effect could be achieved by creating a separate shading dictionary for each of the functions, with adjacent domains. However, since each shading would have similar parameters, and because the overall effect is one shading, it is more convenient to
| have a single shading with multiple function definitions. Also, | Type 3 functions provide a general mechanism for inverting the domains of 1-input functions. |

> **EXAMPLE 2** Consider a function f with a Domain of [0.0 1.0] and a stitching function g with a Domain of [0.0 1.0], a
| Functions array containing f, and an Encode array of [1.0 0.0]. In effect, 𝑔 (𝑥) = | 𝑓 (1 − | 𝑥). |

### 7.10.5 Type 4 (PostScript calculator) functions

#### 7.10.5.1 General

# A Type 4 function (PDF 1.3), also called a PostScript calculator function, shall be represented as a

| stream containing code written in a small subset of the PostScript lan | guage. This subset is comprised |

of the following PostScript language features:

• Expressions involving only integers, real numbers, and boolean values • Comments • No composite data structures (such as strings or arrays) • No procedures • No variables or names

> **NOTE 1** Although any function can be sampled (in a Type 4 PDF function) and others can be described
with exponential functions (Type 2 in PDF), Type 4 functions offer greater flexibility and
| potentially greater accuracy. For example, a tint transformation functi | on for a hexachrome (six- component) DeviceN colour space with an alternative colour space of DeviceCMYK (see 8.6.6.5, "DeviceN colour spaces") requires a 6-in, 4-out function. If such a function were sampled with m values for each input variable, the number of samples, 4 × m6, could be prohibitively large. In practice, such functions can often be written as short, simple PostScript language functions. |


## Page 146

> **NOTE 2** Type 4 functions also facilitate inclusion of a wide variety of halftone spot functions without the
loss of accuracy that comes from sampling, and without adding to the list of predefined spot functions (see 10.6.3, "Spot functions"). All of the predefined spot functions can be written as Type 4 functions.

#### 7.10.5.2 Operators and operands

"Table 42 — Operators in Type 4 functions" lists the operators that can be used in this type of function.
The PostScript Language Reference, Third Edition shall define the semantics of these operators and all other syntax rules of the PostScript language. Although the semantics are those of the corresponding PostScript language operators, a full PostScript language compatible interpreter is not required.

Table 42 — Operators in Type 4 functions

Operator Type Operators

| Arithmetic | abs | cvi | floor | mod | sin |
| --- | --- | --- | --- | --- | --- |
| operators | add | cvr | idiv | mul | sqrt |

| atan | div | ln | neg | sub |
| --- | --- | --- | --- | --- |
| ceiling | exp | log | round | truncate cos |

| Relational, | and | false | le | not | true |
| --- | --- | --- | --- | --- | --- |
| boolean, and | bitshift | ge | lt | or | xor bitwise operators |
| eq | gt | ne |  |  |  |

| Conditional | If | ifelse |

operators

| Stack operators | copy | exch | pop |
| --- | --- | --- | --- |
| dup | index | roll |  |

The operand syntax for Type 4 functions shall follow PDF conventions rather than PostScript language conventions. The entire code stream defining the function shall be enclosed in braces { } (using LEFT CURLY BRACE (7Bh) and RIGHT CURLY BRACE (07hD)). Braces also shall delimit expressions that are executed conditionally by the if and ifelse operators:

• boolean {expression} if • boolean {expression1} {expression2} ifelse This construct is purely syntactic; unlike in the PostScript language, no "procedure objects" shall be involved.

#### 7.10.5.3 Type 4 function dictionary

# A Type 4 function dictionary shall include the entries in "Table 38 — Entries common to all function

dictionaries", as well as other appropriate stream attributes (see "Table 5 — Entries common to all stream dictionaries"). The following example shows a Type 4 function equivalent to the predefined


## Page 147

spot function DoubleDot (see 10.6.3, "Spot functions").

EXAMPLE

10 0 obj <</FunctionType 4 /Domain [-1.0 1.0 -1.0 1.0] /Range [-1.0 1.0] /Length 71 >> stream {360 mul sin 2 div exch 360 mul sin 2 div add } endstream endobj

The Domain and Range entries shall both be required. The input variables shall constitute the initial operand stack; the items remaining on the operand stack after execution of the function shall be the output variables. It shall be an error for the number of remaining operands to differ from the number of output variables specified by Range or for any of them to be objects other than numbers.

Implementations of Type 4 functions shall provide a stack with room for at least 100 entries. No implementation shall be required to provide a larger stack, and it shall be an error to overflow the stack.

# A PDF writer shall not use a nesting depth for { } greater than 255 in order to prevent unwanted stack

overflows. Annex B, "Operators in Type 4 Functions", contains a summary of these operators.

## 7.11 File specifications

### 7.11.1 General

# A PDF file can refer to the contents of another file by using a file specification (PDF 1.1), which shall

take either of two forms:

• A simple file specification shall give just the name of the target file in a standard format, independent of the naming conventions of any particular file system. It shall take the form of either a string or a dictionary.
• A full file specification shall include information related to one or more specific file systems. It shall only be represented as a dictionary.
A file specification shall refer to a file external to the PDF file or to a file embedded within the referring PDF file, allowing its contents to be stored or transmitted along with the PDF file. The file is considered external to the PDF file in either case.

### 7.11.2 File specification strings

#### 7.11.2.1 General

The standard format for representing a simple file specification in string form divides the string into component substrings separated by the SOLIDUS character (2Fh) (/). The SOLIDUS is a generic


## Page 148

component separator that shall be mapped to the appropriate platform-specific separator when generating a platform-dependent file name. Any of the components may be empty. If a component contains one or more literal SOLIDIUS character, each shall be preceded by a REVERSE SOLIDUS (5Ch) (\), which in turn shall be preceded by another REVERSE SOLIDUS to indicate that it is part of the string and not an escape character.

EXAMPLE

(in\\/out)

represents the file name

in/out

The REVERSE SOLIDIUS character shall be removed in processing the string; they are needed only to distinguish the component values from the component separators. The component substrings shall be stored as bytes and shall be passed to the operating system without interpretation or conversion of any sort.

#### 7.11.2.2 Absolute and relative file specifications

A simple file specification that begins with a SOLIDUS shall be an absolute file specification. The last
| component shall be the file name; the preceding components shall specify its context. In | some file |

specifications, the file name may be empty; for example, URL (uniform resource locator) specifications can specify directories instead of files. A file specification that does not begin with a SOLIDUS shall be a relative file specification giving the location of the file relative to that of the PDF file containing it.

In the case of a URL-based file system, the rules of Internet RFC 3986 shall be used to compute an absolute URL from a relative file specification and the specification of the PDF file. Prior to this process, the relative file specification shall be converted to a relative URL by using the escape mechanism of Internet RFC 3986 to represent any bytes that would be either unsafe according to Internet RFC 3986 or not representable in 7-bit U.S. ASCII. In addition, such URL-based relative file specifications shall be limited to paths as defined in Internet RFC 3986. The scheme, network location/login, fragment identifier, query information, and parameter sections shall not be allowed.

In the case of other file systems, a relative file specification shall be converted to an absolute file specification by removing the file name component from the specification of the containing PDF file and appending the relative file specification in its place.

> **EXAMPLE 1** The relative file specification

ArtFiles/Figure1.pdf

appearing in a PDF file whose specification is /HardDisk/PDFDocuments/AnnualReport/Summary.pdf

yields the absolute specification /HardDisk/PDFDocuments/AnnualReport/ArtFiles/Figure1.pdf

The special component . . (two PERIODs) (2Eh) can be used in a relative file specification to move up a level in the file system hierarchy. After an absolute specification has been derived, when the component immediately preceding . . is not another . ., the two cancel each other; both are eliminated


## Page 149

from the file specification and the process is repeated.

> **EXAMPLE 2** The relative file specification from Example 1 in this subclause using the .. (two PERIODs) special component

../../ArtFiles/Figure1.pdf

would yield the absolute specification

/HardDisk/ArtFiles/Figure1.pdf

### 7.11.3 File specification dictionaries

The dictionary form of file specification provides more flexibility than the string form, allowing different files to be specified for different file systems or platforms. "Table 43 — Entries in a file
| specification dictionary | " shows the entries in a file specification dictionary. PDF writers should use the |
| --- | --- |
| F entries to specify files. The | UF entry is optional, but should be included because it enables cross- |

platform and cross-language compatibility.

Table 43 — Entries in a file specification dictionary

| Key | Type | Value |

| Type | name | (Required if an EF, EP or RF entry is present; recommended always | ) The type of |
| --- | --- | --- | --- |
| PDF object that this dictionary describes; shall be | Filespec for a file specification dictionary. |  |  |

| FS | name | (Optional) The name of the file system that shall be used to interpret this file specification. If this entry is present, all other entries in the dictionary shall be interpreted by the designated file system. PDF shall define only one standard file system name, URL (see 7.11.5, "URL specifications"); an application can register other names (see Annex E, "Extending PDF"). This entry shall be independent of the F and UF entries. |

| F | string | (Required if the DOS, Mac, and Unix entries are all absent; amended with the UF entry for PDF 1.7) A file specification string of the form described in 7.11.2, "File specification strings" or (if the file system is URL) a uniform resource locator, as described in 7.11.5, "URL specifications". The UF entry should be used in addition to the F entry. The UF entry provides cross-platform and cross-language compatibility and the F entry provides backwards compatibility. A PDF reader shall use the value of the UF key, when present, instead of the F key. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the F entry shall not contain or be derived from the encrypted payload’s actual file name. This is to avoid potential disclosure of sensitive information in the original filename. The value of F for encrypted payload documents should include the name of the cryptographic filter needed to decrypt the document. See the example in 7.6.7, "Unencrypted wrapper document". |


## Page 150

| Key | Type | Value |

| UF | text string | (Optional, but recommended if the F entry exists in the dictionary; PDF 1.7) A Unicode text string that provides file specification of the form described in 7.11.2, "File specification strings". This is a text string as defined in 7.9.2.2, "Text string type". The F entry should be included along with this entry for backwards compatibility reasons. A PDF reader shall use the value of the UF key, when present, instead of the F key. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the UF entry shall not contain or be derived from the encrypted payload’s actual file name. This is to avoid potential disclosure of sensitive information in the original filename. |

| DOS | byte string | (Optional; deprecated in PDF 2.0) A file specification string (see 7.11.2, "File specification strings") representing a DOS file name. |

| Mac | byte string | (Optional; deprecated in PDF 2.0) A file specification string (see 7.11.2, "File specification strings") representing a Mac OS file name. |

| Unix | byte string | (Optional; deprecated in PDF 2.0) A file specification string (see 7.11.2, "File specification strings") representing a UNIX file name. |

| ID | array | (Optional) An array of two byte strings constituting a file identifier (see 14.4, "File identifiers") that should be included in the referenced file. |

> **NOTE 1** The use of this entry improves a PDF processor’s chances of finding the
intended file, and allows a PDF processor to warn the user if the file has changed since the link was made.

| V | boolean | (Optional; PDF 1.2) A flag indicating whether the file referenced by the file specification is volatile (changes frequently with time). If the value is true, applications shall not cache a copy of the file. For example, a movie annotation referencing a URL to a live video camera could set this flag to true to notify the PDF reader that it should re-acquire the movie each time it is played. Default value: false. |

| EF | dictionary | (Required if RF is present; PDF 1.3; amended to include the | UF key in PDF 1.7) A dictionary containing a subset of the F and UF keys corresponding to the entries by those names in the file specification dictionary. The value of each |
| --- | --- | --- | --- |
| such key shall be an embedded file stream (see | 7.11.4, "Embedded file streams") containing the corresponding file. If this entry is present, the Type entry is required and the file specification dictionary shall be indirectly referenced. (PDF 2.0) For unencrypted wrapper documents for an encrypted payload document (see 7.6.7, "Unencrypted wrapper document") the EF dictionary is required for the file stream containing the encrypted payload. |  |  |

| RF | dictionary | (Optional; PDF 1.3) A dictionary with the same structure as the EF dictionary, which shall be present. For each key in this dictionary, the same key shall appear in the EF dictionary of this file specification dictionary. Each value shall be a related files array (see 7.11.4.2, "Related files arrays") identifying files that are related to the corresponding file in the EF dictionary. If this entry is present, the Type entry is required and the file specification dictionary shall be indirectly referenced. |


## Page 151

| Key | Type | Value |

| Desc | text string | (Optional; PDF 1.6) Descriptive text associated with the file specification. It shall be used for file specification dictionaries referenced from the EmbeddedFiles name tree (see 7.7.4, "Name dictionary"). |

| CI | dictionary | (Optional; shall be indirect reference; PDF 1.7) A collection item dictionary, which shall be used to create the user interface for portable collections (see 7.11.6, "Collection items"). |

| Thumb | stream | (Optional; PDF 2.0) A stream object defining the thumbnail image for the file specification. (See 12.3.4, "Thumbnail images") |

| EP | dictionary | (PDF 2.0; Required if this file specification references an encrypted payload document as described in 7.6.7, "Unencrypted wrapper document") The value of this key is an encrypted payload dictionary which identifies that the file specified in the EF dictionary is an encrypted payload. |


## Page 152

| Key | Type | Value |

| AFRelationship | name | (Optional; PDF 2.0) A name value that represents the relationship between the component of this PDF document that refers to this file specification and the associated file denoted by this file specification dictionary. See 14.13, "Associated files" for more details. These values represent the following relationships: |
| --- | --- | --- |
| Source | shall be used if this file specification is the original source material for the associated content. |  |
| Data | shall be used if this file specification represents information used to derive a visual presentation – such as for a table or a graph. |  |
| Alternative | shall be used if this file specification is an alternative representation of content, for example audio. |  |
| Supplement | shall be used if this file specification represents a supplemental representation of the original source or data that may be more easily consumable (e.g., A MathML version of an equation). |  |
| EncryptedPayload | shall be used if this file specification is an encrypted payload document that should be displayed to the user if the PDF processor has the cryptographic filter needed to decrypt the document. |  |
| FormData | shall be used if this file specification is the data associated with the AcroForm (see 12.7.3, "Interactive form dictionary") of this PDF. |  |
| Schema | shall be used if this file specification is a schema definition for the associated object (e.g. an XML schema associated with a metadata stream). |  |
| Unspecified | shall be used when the relationship is not known or cannot be described using one of the other values. |  |

> **NOTE 2** Unspecified is to be used only when no other value correctly reflects the
relationship.
Second-class names (see Annex E, “Extending PDF") should be used to represent other types of relationships.
Default: Unspecified

> **NOTE 3** The value of AFRelationship does not explicitly provide any processing
instructions for a PDF processor. It is provided for information and semantic purposes for those processors that are able to use such additional information.

### 7.11.4 Embedded file streams

#### 7.11.4.1 General

If a PDF file contains file specifications that refer to an external file and the PDF file is archived or transmitted, some provision should be made to ensure that the external references will remain valid.
One way to do this is to arrange for the external files to accompany the PDF file. Embedded file streams (PDF 1.3) address this problem by allowing the contents of referenced files to be embedded directly within the body of the PDF file. This makes the PDF file a self-contained unit that can be stored or transmitted as a single entity. (The embedded files are included purely for convenience and need not be directly processed by any PDF processor.)


## Page 153

> **NOTE** If the file contains OPI (Open Prepress Interface) dictionaries that refer to externally stored highresolution images (see 14.11.7, "Open prepress interface (OPI)"), the image data can be
incorporated into the PDF file with embedded file streams.
An embedded file stream shall be included in a PDF file in one of the following ways:

• Any file specification dictionary in the document may have an EF entry that specifies an embedded file stream. The stream data shall still be associated with a location in the file system.
In particular, this method shall be used for file attachment annotations (see 12.5.6.15, "File attachment annotations"), which associate the embedded file with a location on a page in the document.
• Embedded file streams may be associated with the document as a whole through the EmbeddedFiles entry in the PDF file’s name dictionary (see 7.7.4, "Name dictionary"). The associated name tree shall map name strings to file specifications that refer to embedded file streams through their EF entries.
Beginning with PDF 1.6, the Desc entry of the file specification dictionary (see "Table 43 — Entries in a file specification dictionary") should be used to provide a textual description of the embedded file, which can be displayed in the user interface of an interactive PDF processor. Previously, it was necessary to identify document-level embedded files by the name string provided in the name dictionary associated with an embedded file stream in much the same way that the ECMAScript name tree associates name strings with document-level ECMAScript actions (see 12.6.4.17, "ECMAScript actions").

The stream dictionary describing an embedded file shall contain the standard entries for any stream, such as Length and Filter (see "Table 5 — Entries common to all stream dictionaries"), as well as the additional entries shown in "Table 44 — Additional entries in an embedded file stream dictionary".

Table 44 — Additional entries in an embedded file stream dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be EmbeddedFile for an embedded file stream. |

| Subtype | name | (Optional, required in the case of an embedded file stream used as an associated file) The subtype of the embedded file. The value of this entry shall conform to the MIME media type names defined in Internet RFC 2046, with the provision that characters not permitted in names shall use the 2-character hexadecimal code format described in 7.3.5, "Name objects". NOTE The media type for PDF is defined by Internet RFC 8118. |

| Params | dictionary | (Optional, required in the case of an embedded file stream used as an associated file) An embedded file parameter dictionary that shall contain additional file- specific information (see "Table 45 — Entries in an embedded file parameter dictionary"). |

Table 45 — Entries in an embedded file parameter dictionary

| Key | Type | Value |

| Size | integer | (Optional) The size of the uncompressed embedded file, in bytes. See "Table 155 — Entries in a collection field dictionary". |


## Page 154

| Key | Type | Value |

| CreationDate | date | (Optional) The date and time when the embedded file was created. See "Table 155 — Entries in a collection field dictionary". |

| ModDate | date | (Optional, required in the case of an embedded file stream used as an associated file) The date and time when the embedded file was last modified. See "Table 155 — Entries in a collection field dictionary". |

| Mac | dictionary | (Optional; deprecated in PDF 2.0) A subdictionary containing additional information specific to Mac OS files. |

| CheckSum | string | (Optional) A 16-byte string that is the checksum of the bytes of the uncompressed embedded file. The checksum shall be calculated by applying the standard MD5 message-digest algorithm (defined in Internet RFC 1321) to the bytes of the embedded file stream. |

> **NOTE** This is strictly a checksum, and is not used for security purposes.

#### 7.11.4.2 Related files arrays

In some circumstances, a PDF file can refer to a group of related files, such as the set of five files that make up a DCS 1.0 colour-separated image. The file specification explicitly names only one of the files; the rest shall be identified by some systematic variation of that file name (such as by altering the extension). When such a file is to be embedded in a PDF file, the related files shall be embedded as well.
This is accomplished by including a related files array (PDF 1.3) as the value of the RF entry in the file
| specification dic | tionary. The array shall have 2 × n elements, which shall be paired in the form |

[ string1 stream1 string2 stream2 … stringn streamn ]
| The first element of each pair shall be a string giving the name of one of the related files; the | second |

element shall be an embedded file stream holding the file’s contents.

> **EXAMPLE** In the following example, object 31 is an embedded file stream containing Encapsulated Postscript File
(EPSF) file Sunset.eps. The file specification dictionary’s RF entry specifies an array, object 30, identifying a set of embedded files, forming a DCS 1.0 set.

| 10 0 obj | %File specification dictionary <</Type /Filespec /F (Sunset.eps) /UF (Sunset.eps) /EF <</F 21 0 R /UF 41 0 R >> |
| --- | --- |
| /RF <</UF 30 0 R>> | %Related files array |

endobj

| 30 0 obj | %Related files array |
| --- | --- |
| [(Sunset.eps) 31 0 R | %Embedded file stream for the EPSF Sunset.eps (Sunset.C) 32 0 R |


## Page 155

(Sunset.M) 33 0 R (Sunset.Y) 34 0 R (Sunset.K) 35 0 R ] endobj

| 31 0 obj | %Embedded file stream for Mac OS file |
| --- | --- |
| <</Type /EmbeddedFile | %Sunset.eps /Length … /Filter… >> |

stream … Data for Sunset.eps … endstream endobj

| 32 0 obj | %Embedded file stream for related file |
| --- | --- |
| <</Type /EmbeddedFile | %Sunset.C /Length … /Filter… >> |

stream … Data for Sunset.C … endstream endobj

### 7.11.5 URL specifications

When the FS entry in a file specification dictionary has the value URL, the value of the F entry in that dictionary is not a file specification string, but a uniform resource locator (URL) of the form defined in Internet RFC 3986.

> **EXAMPLE** The following example shows a URL specification.

<< /FS /URL /F (ftp://www.beatles.com/Movies/AbbeyRoad.mov) >>

The URL shall adhere to the character-encoding requirements specified in Internet RFC 3986. Because 7-bit U.S. ASCII is a strict subset of PDFDocEncoding, this value shall also be considered to be in PDFDocEncoding.

### 7.11.6 Collection items

Beginning with PDF 1.7, a collection item dictionary shall contain the data described by the collection schema dictionary for a particular file in a collection (see 12.3.5, "Collections"). "Table 46 — Entries in a collection item dictionary" describes the entries in a collection item dictionary.

Table 46 — Entries in a collection item dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be CollectionItem for a collection item dictionary. |


## Page 156

| Key | Type | Value |

| Other keys | text string, | (Optional) Provides the data corresponding to the related fields in the collection |
| --- | --- | --- |
| date, | dictionary. If the entry is a dictionary, then it shall be a collection subitem |  |
| number or | dictionary (see "Table 47 — Entries in a collection subitem dictionary"). |  |
| dictionary | The type of each entry shall match the type of data identified by the collection field dictionary (see "Table 155 — Entries in a collection field dictionary") referenced by the same key in the collection schema dictionary (see "Table 154 — Entries in a collection schema dictionary"). |  |

> **EXAMPLE** If the corresponding collection field has a Subtype entry of S, then the
entry is a text string.

A single collection item dictionary may contain multiple entries, with one entry representing each key (see Example 1 in 12.3.5, "Collections").

A collection subitem dictionary provides the data corresponding to the related fields in the collection dictionary, and it provides a means of associating a prefix string with that data value. The prefix shall
| be ignored by the sorting algorithm. "Table 47 — Entries in a collection subitem dictionary | " describes |
| --- | --- |
| the entries in a collection subitem dict | ionary. |

Table 47 — Entries in a collection subitem dictionary

| Key | Type | Value |

| Type | name | (Optional) The type of PDF object that this dictionary describes; if present, shall be CollectionSubitem for a collection item dictionary. |

| D | text string, | (Optional) The data corresponding to the related entry in the collection field dictionary |
| --- | --- | --- |
| date, or | (see "Table 155 — Entries in a collection field dictionary"). The type of data shall match |  |
| number | the data type identified by the corresponding collection field dictionary. Default: none. |  |

| P | text string | (Optional) A prefix string that shall be concatenated with the text string presented to the user. This entry is ignored when an interactive PDF processor sorts the items in the collection. Default: none. |

## 7.12 Extensions dictionary

### 7.12.1 General

An optional key, Extensions, in the document’s catalog dictionary shall have as its value an Extensions dictionary (see "Table 48 — Entries in an extensions dictionary") containing one or more entries identifying developer-defined extensions to this document. The keys allowed in an extensions dictionary, are shown in "Table 48 — Entries in an extensions dictionary", and may optionally contain a Type entry whose value is the name Extensions. The remaining keys shall be names consisting of registered prefix names of the developer extensions used, as described in Annex E, "Extending PDF".
The values of those prefix name keys shall be developer extensions dictionaries (see "Table 49 — Entries in a developer extensions dictionary") specifying developer-defined version information. The extensions dictionary, all developer extensions dictionaries, as well as their entries, shall be direct objects (i.e., this information shall be nested directly within the catalog dictionary with no indirect


## Page 157

objects used).

### 7.12.2 Extensions dictionary

"Table 48 — Entries in an extensions dictionary" describes the entries in an extensions dictionary.

Table 48 — Entries in an extensions dictionary

| Key | Type | Value |

| Type | name | (Optional; , shall be a direct objectif present) The type of PDF object that this dictionary describes; if present, shall be Extensions. |

| Prefix name | array or | (Required; PDF 1.7; shall be a direct object) A developer extensions dictionary, |
| --- | --- | --- |
| dictionary | or an array of developer extension dictionaries (PDF 2.0), denoting extensions used (See "Table 49 — Entries in a developer extensions dictionary"). The key shall be a registered prefix name as described in Annex E, “Extending PDF". This entry is repeated for each different developer’s extensions used. Multiple distinct registered prefix names for different developers may be used for each different developer’s extensions used. See Example 3 below. |  |

> **NOTE** Arrays of developer extension dictionaries were introduced in this document
(2020).

### 7.12.3 Developer extensions dictionary

"Table 49 — Entries in a developer extensions dictionary" describes the entries in a developer extensions dictionary.

Table 49 — Entries in a developer extensions dictionary

| Key | Type | Value |

| Type | name | (Optional; shall be a direct object if present) The type of PDF object that this dictionary describes; if present, shall be DeveloperExtensions. |

| BaseVersion | name | (Required; shall be a direct object) The name of the PDF version to which this extension applies. The name shall be consistent with the syntax used for the Version entry of the catalog dictionary (see 7.7.2, "Document catalog dictionary"). |

| ExtensionLevel | integer | (Required; shall be a direct object) An integer defined by the developer to denote the extension being used. If the developer introduces more than one extension to a given BaseVersion the extension level numbers assigned by that developer should increase over time. |

| URL | string | (Optional; PDF 2.0; shall be a direct object if present) A URL that refers to the documentation for this extension (see Annex E, "Extending PDF"). |

| ExtensionRevision | text string | (Optional; PDF 2.0; shall be a direct object if present) An optional text string that provides additional revision information on the extension level being used. |

> **NOTE** The ExtensionRevision key was introduced in this document (2020).


## Page 158

### 7.12.4 BaseVersion

The value of the BaseVersion entry shall be a name and shall be consistent with the syntax used for the Version entry value of the catalog dictionary (see 7.7.2, "Document catalog dictionary"). The value of BaseVersion, when treated as a version number, shall be less than or equal to the PDF version, both in the document header (see 7.5.2, "File header") and the catalog dictionary Version key value, if present. The value of BaseVersion may be different from the version number in the document header or that supplied by the Version key in the catalog dictionary. This is because it reflects the version of the standard that has been extended and not the version of this particular file.

> **NOTE** The value of BaseVersion is not to be interpreted as a real number but as two integers with a
PERIOD (2Eh) between them.

### 7.12.5 ExtensionLevel

The value of the ExtensionLevel entry shall be an integer, which shall be interpreted with respect to the BaseVersion value. If a developer has released multiple extensions against the same BaseVersion value, they shall be ordered over time and the ExtensionLevel numbers shall be a monotonically increasing sequence over time.

### 7.12.6 URL

The value of the optional URL entry shall be a string containing a URL to the documentation that is provided by the developer of this extension and as registered in the PDF Registry (see Annex E, "Extending PDF").

> **EXAMPLE** 1

%PDF–2.0 << /Type /Catalog /Extensions << /Type /Extensions /ADBE << /BaseVersion /1.7 /ExtensionLevel 3 /URL (http://www.adobe.com/documentation/ext3.pdf) >> >> >>

> **EXAMPLE 2** This is a minimal example.

%PDF–2.0 << /Type /Catalog /Extensions << /GLGR << /BaseVersion /1.7 /ExtensionLevel 1002 >> >> >>

> **EXAMPLE 3** This example shows the use of both arrays (PDF 2.0) and dictionaries with different developer prefixes.

%PDF–2.0 << /Type /Catalog /Extensions << /Type /Extensions /ISO_ [ % Multiple developer extensions under the registered prefix “ISO_” << /Type /DeveloperExtensions /BaseVersion /2.0 /ExtensionLevel 24064


## Page 159

/URL (https://www.iso.org/standard/77686.html) >> << /Type /DeveloperExtensions /BaseVersion /2.0 /ExtensionLevel 24654 /URL (https://www.iso.org/standard/79150.html) >> ] /GLGR << /Type /DeveloperExtensions /BaseVersion /1.7 /ExtensionLevel 1002 /URL (http://www.generallanguage.com/ISO32000/extension1002.pdf) >> >> >>


## Page 160

