# Annex J (informative) XObject comparison

## J.1 Background

A PDF document, as defined in 7.3, "Objects" can contain nine different types of basic objects booleans, integers, real numbers, strings, names, arrays, dictionaries, streams, and the null object.
Objects except for stream objects may be direct or indirect, as described in 7.3.10, "Indirect objects", but for the purposes of comparison, this aspect of the object is not considered.

> **NOTE 1** Clause 8.8, "External objects", defines the two types of external objects (XObjects) that can be present in a PDF - Image, and Form - as self-contained streams. These stream objects, like all other stream objects, actually consist of a stream of data preceded by a dictionary (7.3.8, "Stream objects").
This annex describes how to generally compare two objects of stream type, though it is expected that such comparison will be used strictly for the purpose of determining uniqueness among a pair of XObjects.

## J.2 General

Because a stream object also contains a dictionary object, and a dictionary is an unordered collection of key/value pairs - where a value can be of another PDF object type, a more complete discussion of the comparison of any PDF object type is required.

In order to compare two PDF objects, it should first be determined if the objects are of the same type (e.g. stream). Once that determination has been made, then the actual comparison process differs based on the type of object.

## J.3 Object comparisons

### J.3.1 Boolean objects

Clause 7.3.2, "Boolean objects" clearly states that the keywords shall be true and false. Comparison is a simple boolean logic test. See 4, "Notation", for information regarding the notation of these keywords throughout this document.

### J.3.2 Numeric objects

As described in 7.3.3, "Numeric objects", PDF defines two types of basic numeric objects - integer and real. Integers are well defined and their comparison should be done using standard integer mathematical principles. Real numbers shall be interpreted and compared using the internal representation of the PDF processor. In the case where an integer is being compared to a real, the integer shall be promoted to a real and then real comparison shall take place.


### J.3.3 String objects

Although 7.3.4, "String objects", defines two types of strings (literal and hexadecimal), when processing them for comparison they should be first processed into a single canonical form and then compared in a simple binary comparison.

Literal strings shall be processed looking for any escape characters ("Table 3 — Escape sequences in literal strings", in 7.3.4.2, "Literal strings") and replacing them with their meanings. Any strings that are split across multiple lines shall be processed as per instructions to "disregard the REVERSE SOLIDUS and the end-of-line marker following it when reading the string (i.e., the resulting string value shall be identical to that which would be read if the string were not split)". Finally, any octal characters described using the \ddd escape sequence shall be expanded to their proper value. The result of these expansions shall be the value to be used for binary comparison.

Hexadecimal strings shall be simply converted from hexadecimal format to literal format and that value shall be used for the binary comparison.

### J.3.4 Name objects

A Name object is defined as being a sequence of any characters (8-bit values) except null (character code 0), with any values outside the range of EXCLAMATION MARK to TILDE being escaped using a NUMBER SIGN and hexadecimal digits (See 7.3.5, "Name objects"). All such escaping shall be expanded and the result shall form a sequence of bytes that shall be used for binary comparison.

### J.3.5 Array objects

Comparison on two array objects is quite straightforward, in that each object (N) of the first array shall be compared with the same indexed object in the second array. Since any of the objects in the array may be a dictionary or an array, there is the need to be able to do a form of "tree comparison" (also known as breadth-first search), since processing can go from array to dictionary to array, etc.

If at any time, two objects do not match, then the arrays are non-equal. "Figure J.1 — Comparing two arrays" shows non-equal arrays.


ISO 32000-2:2020

Figure J.1 — Comparing two arrays J.3.6     Dictionary objects

Comparison of dictionaries is a relatively complex operation because of the flexibility of dictionary objects in PDF.

First, the order of keys in a dictionary is not defined - they may be alphabetically sorted, sorted by entry into the dictionary, or any other ordering that a producer chooses. This, of course, leads to the possibility that the same key/value pair exists in both dictionaries but in a different order - so a binary compare or crypto-hash will not be successful.

This lack of a standard ordering to keys in a dictionary requires that comparison be done by ensuring that the number of keys is equal and then iterating over each key in the first dictionary and comparing its value to the value of the key of the same name in dictionary 2. If a key from dictionary 1 cannot be found in dictionary 2, then the dictionaries are not equal - but if each key exists in both and they have the same associated values, then the dictionaries are equal.

Second, unlike PostScript, where a dictionary value of null is considered as a valid value, PDF instead stipulates that "A dictionary entry whose value is null shall be treated the same as if the entry does not


exist" (see 7.3.7, "Dictionary objects"). To ease comparison, any such key/value pairs shall be removed from a dictionary prior to processing.

Finally, since any of the values may itself be a dictionary (or an array), there is the need to be able to do a form of "tree comparison" (also known as breadth-first search), since processin g can go from dictionary to dictionary to dictionary, etc.

Fortunately, even with these complexities, the process for dictionary comparison can be clearly defined
| and made standard in a way that all implementations can agree on equality. | “Figure J.2 — Comparison |
| --- | --- |
| of two dictionaries” shows a comparison of two dictionar | ies. |

Figure J.2 — Comparison of two dictionaries J.3.7    Stream objects

| Given two streams, there are two things to compare | - the stream data and the associated dictionary. |
| --- | --- |
| Comparison of the dictionary was addressed in the previous | subclause. |


ISO 32000-2:2020

The stream of data in a PDF may have had one or more filters applied to it ( see "Table 5 — Entries common to all stream dictionaries") that compress or encoded the stream’s data. In order to compare the streams, all filters shall be removed and the original (unfiltered) data shall be used for all comparisons. The actual comparison of the data stream shall be done using a simple linear binary (byte-for-byte) comparison. “Figure J.3 – Comparing two XObjects” shows a comparison of two XObjects.

Figure J.3 – Comparing two XObjects

## J.4 Known issues

### J.4.1 Equal vs. equivalent

One issue with this particular solution for comparison of XObject equality is that it does not provide for the comparison of two functionally equivalent but binary different XObjects.

Consider the case where a PDF optimisation process has optimised the data of the stream itself - for example, converting all the end of line characters (e.g. CARRIAGE RETURN or LINE FEED) in the data of the stream to simpler SPACE characters in order to improve compression. The streams are equivalent -


in that they will produce the exact same content on the page but they will never compare correctly via binary comparison or hash generation.

In addition, a similar optimisation process may remove any private data or metadata associated with an object. Doing so would leave an object with the same data stream but with a different dictionary, and thus a different object.

### J.4.2 Recursion in dictionaries

As noted in J.3.6, "Dictionary objects", it is possible that when processing a dictionary, the value of one or more keys may itself be a dictionary that needs to be processed, then that dictionary may refer to another dictionary, and so on. Should one of those dictionaries reference an object "higher up" in the document’s object structure, this has the potential to lead to an infinite recursive situation when doing the comparison.

For the specific use case of this document - comparison of XObjects - it is not expected that this will occur. However, should the recommendations in this document be used in the general case, then the PDF processor will need to protect itself.


ISO 32000-2:2020
