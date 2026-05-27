Annex L
(normative)
Parent-child relationships between the standard structure
elements in the standard structure namespace for PDF 2.0
NOTE This annex was corrected (2020).
This annex defines the acceptable children of the standard structure elements defined in PDF 2.0.
Elements in the standard structure namespace for PDF 2.0 shall not have child or parent elements in
the standard structure namespace for PDF 2.0 that are not explicitly listed in Table L.2.
The containment rules specified in Table L.2 shall also apply to structure elements that are role
mapped into the standard structure namespace for PDF 2.0.
An informative matrix representation of Table L.2 is attached to the PDF of this document as
"ISO32000-2_AnnexL_matrix-version2020.pdf" and in machine-readable form, as "ISO32000-
2_AnnexL_matrix-version2020.xlsx."
Table L.1 provides a legend for use in interpreting Table L.2.
Table Annex L.1 — Legend for Table L.2
Value Valid usage relative to other standard structure types
ø shall not occur
ø* shall not occur unless the parent element is used as a grouping level element
0..n may be a child element with one or several occurrences, but is not required to be present
1..n shall be present as a child element with one or several occurrences
0..1 may occur, but not more than once
‡ for containment rules, refer to the respective structure element type’s description
[a] for specific provisions when and how these structure elements or content can be contained
inside a Ruby structure element see 14.8.4.7.3, "Ruby and warichu elements"
[b] for specific provisions when and how these structure elements or content can be contained
inside a Warichu structure element see 14.8.4.7.3, "Ruby and warichu elements".



 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 963

ISO 32000-2:2020
948 © ISO 2020 – All rights reserved

Table Annex L.2 — Parent-child relationships between the standard structure elements in the
standard structure namespace for PDF 2.0
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
StructTreeRoot 1 Document

Document 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Link
Annot
Form
FENote
L
Table
Figure
Formula
Artifact
1
0..n
0..n
‡
‡
0..n
‡
0..n
StructTreeRoot
Document
DocumentFragment
Part
Div
Aside
NonStruct
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 964

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 949
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
DocumentFragment 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Link
Annot
Form
FENote
L
Table
Figure
Formula
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
∅*
∅*
∅*
∅*
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Link
Annot
FENote
Caption
Artifact
Part ‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Link
Annot
Form
FENote
L
Table
Caption
Figure
Formula
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
∅*
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Link
Annot
Form
FENote
LBody
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 965

ISO 32000-2:2020
950 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Div ‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
RB
RT
RP
Warichu
WT
WP
FENote
L
LI
LBody
Table
TR
TH
TD
THead
TBody
TFoot
Caption
Figure
Formula
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Link
Annot
Form
FENote
LI
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 966

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 951
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Sect 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Lbl
Link
Annot
Form
FENote
L
Table
Caption
Figure
Formula
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
0..1
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Hn
H
Link
Annot
FENote
LBody
TH
TD
Caption
Figure
Artifact
Aside 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
NonStruct
P
Hn
H
Lbl
Link
Annot
Form
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
‡
0..n
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
NonStruct
Title
Link
Annot
FENote
LBody
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 967

ISO 32000-2:2020
952 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
NonStruct ‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
‡
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
RB
RT
RP
Warichu
WT
WP
FENote
L
LI
LBody
Table
TR
TH
TD
THead
TBody
TFoot
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
RB
RT
RP
Warichu
WT
WP
FENote
L
LI
LBody
Table
TR
TH
TD
THead
TBody
TFoot
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 968

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 953
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Title 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Part
Div
Aside
NonStruct
P
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
‡
∅*
∅*
0..n
Document
DocumentFragment
Part
Div
Sect
NonStruct
Link
Annot
Artifact
Sub 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Figure
Formula
Artifact
content item
‡
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
0..n
0..n
Part
Div
NonStruct
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 969

ISO 32000-2:2020
954 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
P 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Link
Annot
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
Hn 0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Sect
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Link
Annot
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 970

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 955
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
H 0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Sect
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
0..1
0..1
‡
‡
0..1
0..1
‡
∅*
∅*
0..1
0..1
0..1
0..1
0..1
0..1
0..1
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Link
Annot
LBody
TH
TD
Caption
Figure
Formula
Artifact
Lbl 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Em
Strong
Span
Link
Annot
Form
FENote
LI
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 971

ISO 32000-2:2020
956 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Em 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 972

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 957
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Strong 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 973

ISO 32000-2:2020
958 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Span 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
NonStruct
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 974

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 959
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Link ∅*
∅*
0..n
0..n
∅*
0..n
∅*
∅*
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
∅*
0..n
0..n
0..n
∅*
∅*
∅*
0..n
0..n
0..n
0..n
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Em
Strong
Span
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 975

ISO 32000-2:2020
960 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Annot ∅*
∅*
0..n
0..n
∅*
0..n
∅*
∅*
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
0..n
0..n
0..n
∅*
∅*
∅*
0..n
0..n
0..n
0..n
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 976

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 961
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Form ∅*
0..n
0..n
0..n
0..n
∅*
∅*
0..1
∅*
∅*
0..n
0..n
Part
Div
NonStruct
Lbl
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
RB
RT
RP
WT
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 977

ISO 32000-2:2020
962 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Ruby 0..n
[a]
[a]
[a]
0..n
NonStruct
RB
RT
RP
content item
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
RB 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
‡
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Artifact
content item
‡
‡
[a]
0..n
Div
NonStruct
Ruby
Artifact
RT 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
‡
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Artifact
content item
‡
‡
[a]
0..n
Div
NonStruct
Ruby
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 978

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 963
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
RP 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
‡
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Artifact
content item
‡
‡
[a]
0..n
Div
NonStruct
Ruby
Artifact
Warichu 0..n
[b]
[b]
0..n
NonStruct
WT
WP
content item
‡
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
WT 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
‡
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Artifact
content item
‡
‡
[b]
0..n
Div
NonStruct
Warichu
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 979

ISO 32000-2:2020
964 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
WP 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
c
0..n
‡
NonStruct
Sub
Em
Strong
Span
Link
Annot
Form
Figure
Artifact
content item
‡
‡
[b]
0..n
Div
NonStruct
Warichu
Artifact
FENote ∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
0..n
0..n
0..n
0..n
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 980

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 965
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
L 0..n
0..n
0..n
0..1
0..n
NonStruct
L
LI
Caption
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
∅*
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Link
Annot
Form
FENote
L
LBody
TH
TD
Caption
Figure
Formula
Artifact
LI 0..n
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Lbl
LBody
Artifact
content item
‡
‡
0..n
0..n
Div
NonStruct
L
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 981

ISO 32000-2:2020
966 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
LBody 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Sub
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
Div
NonStruct
LI
Artifact
Table 0..n
0..n
0..1
0..n
0..1
0..1
0..n
NonStruct
TR
THead
TBody
TFoot
Caption
Artifact
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
∅*
∅*
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Link
Annot
Form
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 982

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 967
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
TR 0..n
0..n
0..n
0..n
NonStruct
TH
TD
Artifact
‡
‡
0..n
0..n
0..n
0..n
0..n
Div
NonStruct
Table
THead
TBody
TFoot
Artifact
TH 0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
Sect
NonStruct
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
Div
NonStruct
TR
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 983

ISO 32000-2:2020
968 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
TD 0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Div
Sect
NonStruct
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Figure
Formula
Artifact
content item
‡
‡
0..n
0..n
Div
NonStruct
TR
Artifact
THead 0..n
0..n
0..n
NonStruct
TR
Artifact
‡
‡
0..1
0..n
Div
NonStruct
Table
Artifact
TBody 0..n
0..n
0..n
NonStruct
TR
Artifact
‡
‡
0..n
0..n
Div
NonStruct
Table
Artifact
TFoot 0..n
0..n
0..n
NonStruct
TR
Artifact
‡
‡
0..1
0..n
Div
NonStruct
Table
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 984

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 969
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Caption ∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Figure
Formula
Artifact
content item
‡
‡
0..n
0..1
‡
0..1
∅*
∅*
0..1
∅*
0..1
0..1
0..1
0..1
0..1
0..1
Part
Div
Sect
Aside
NonStruct
Title
Link
Annot
Form
FENote
L
LBody
Table
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 985

ISO 32000-2:2020
970 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Figure 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
c
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
WP
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 986

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 971
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Formula 0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Part
Div
Aside
NonStruct
P
Hn
H
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
Warichu
FENote
L
Table
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
∅*
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
FENote
LBody
TH
TD
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 987

ISO 32000-2:2020
972 © ISO 2020 – All rights reserved
Structure Type Children Parents
Occ. Structure Type Occ. Structure Type
Artifact 0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..1
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
P
Hn
H
Title
Sub
Lbl
Em
Strong
Span
Link
Annot
Form
Ruby
RB
RT
RP
Warichu
WT
WP
FENote
L
LI
LBody
Table
TR
TH
TD
THead
TBody
TFoot
Caption
Figure
Formula
Artifact
content item
0..n
0..n
‡
‡
0..n
0..n
‡
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
0..n
Document
DocumentFragment
Part
Div
Sect
Aside
NonStruct
Title
Sub
P
Hn
H
Lbl
Em
Strong
Span
Link
Annot
Form
RB
RT
RP
WT
WP
FENote
L
LI
LBody
Table
TR
TH
TD
THead
TBody
TFoot
Caption
Figure
Formula
Artifact
 Sold by the PDF Association to Olivier Chatelain  19713 | May 26, 2026 |
 Single user only, copying and networking prohibited.


## Page 988

ISO 32000-2:2020(E)
© ISO 2020 – All rights reserved 973
