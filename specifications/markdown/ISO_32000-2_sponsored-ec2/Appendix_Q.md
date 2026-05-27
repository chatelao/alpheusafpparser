Annex Q (normative) Method for determining transparency on a page

## Q.1 General

This annex describes the method that PDF processor shall use to determine if a given page contains any graphical elements whose associated graphic state contains transparency or are otherwise involved in a transparency operation.

> **NOTE** Some of the subset standards for PDF, such as PDF/X (ISO 15930), PDF/A (ISO 19005), PDF/E (ISO 24517), or PDF/VT (ISO 16612-2) choose to mandate the use of this method because they restrict the processing of documents conforming to those standards to strict processing rules.
PDF 2.0 is highly generalized to accommodate various processing needs so this method is not required by this document.

## Q.2 Page content

For each graphical element to be rendered on the page, the graphic state shall be checked for any of the following conditions being present. If they are, then the element is considered involved in transparency and therefore the page contains transparency.

• SMask key is present and its value is of type dictionary; •    ca key is present and its value is less than one (1); •    CA key is present and its value is less than one (1); •    BM key is present and its value is not Normal.
If an element’s graphic state sets a colourspace that is a Type 1 Pattern, then the Pattern resource shall be treated as a form XObject and processed according to Q.3, "Form XObjects".

In addition to the graphic state, certain types of graphical elements need additional processing. Any graphical element that represents a form XObject shall be processed according to Q.3, "Form XObjects".
Graphical elements associated with image XObjects shall be processed according to Q.4, "Image XObjects". Text elements shall be processed according to Q.5, "Text objects".

Since Annotations require an appearance stream which is drawn by a PDF processor on top of the page content, it is possible that their presence may cause a page without any transparency to acquire some transparency. Therefore, all annotations object's in the page dictionary's Annots array shall have their appearance streams processed as a form XObject, according to Q.3, "Form XObjects".

## Q.3 Form XObjects

If the XObject’s dictionary contains a Group key, and the value of the S key in the dictionary that forms the value of Group is Transparency, then the XObject is part of a transparency group and any page on which it is placed contains transparency.


> **NOTE** The above paragraph was clarified (2020).
In addition, the content stream of the form XObject shall be processed according to Q.2, "Page content".

## Q.4 Image XObjects

If the XObject’s dictionary contains an SMask key and its value is of type stream, then the image has transparency associated with it and any page on which it is placed contains transparency.

If the XObject’s dictionary contains an SMaskInData key whose value is greater than zero (0), then the image has transparency associated with it and any page on which it is placed contains transparency.

## Q.5 Text objects

For any graphical element that represents text drawing, its text state shall be checked to determine the type of font being used for rendering. If the Subtype of the font dictionary is Type3, then each object in its CharProc array shall be processed as a form XObject according to Q.3, "Form XObjects".


ISO 32000-2:2020
