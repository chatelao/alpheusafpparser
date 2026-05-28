# Annex L (normative) Parent-child relationships between the standard structure elements in the standard structure namespace for PDF 2.0

> **NOTE** This annex was corrected (2020).
This annex defines the acceptable children of the standard structure elements defined in PDF 2.0.
Elements in the standard structure namespace for PDF 2.0 shall not have child or parent elements in the standard structure namespace for PDF 2.0 that are not explicitly listed in Table L.2.

The containment rules specified in Table L.2 shall also apply to structure elements that are role mapped into the standard structure namespace for PDF 2.0.

An informative matrix representation of Table L.2 is attached to the PDF of this document as "ISO32000-2_AnnexL_matrix-version2020.pdf" and in machine-readable form, as "ISO320002_AnnexL_matrix-version2020.xlsx."

Table L.1 provides a legend for use in interpreting Table L.2.

Table Annex L.1 — Legend for Table L.2

| Value | Valid usage relative to other standard structure types |
| --- | --- |
| ø | shall not occur |
| ø* | shall not occur unless the parent element is used as a grouping level element |
| 0..n | may be a child element with one or several occurrences, but is not required to be present |
| 1..n | shall be present as a child element with one or several occurrences |
| 0..1 | may occur, but not more than once |
| ‡ | for containment rules, refer to the respective structure element type’s description |
| [a] | for specific provisions when and how these structure elements or content can be contained inside a Ruby structure element see 14.8.4.7.3, "Ruby and warichu elements" |
| [b] | for specific provisions when and how these structure elements or content can be contained inside a Warichu structure element see 14.8.4.7.3, "Ruby and warichu elements". |


ISO 32000-2:2020

Table Annex L.2 — Parent-child relationships between the standard structure elements in the standard structure namespace for PDF 2.0
| Structure Type | Children | Parents |  |
| --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |
| StructTreeRoot | 1 | Document |  |

| Document | 0..n | Document | 1 | StructTreeRoot |
| --- | --- | --- | --- | --- |
| 0..n | DocumentFragment | 0..n | Document |  |
| 0..n | Part | 0..n | DocumentFragment |  |
| 0..n | Div | ‡ | Part |  |
| 0..n | Sect | ‡ | Div |  |
| 0..n | Aside | 0..n | Aside |  |
| 0..n | NonStruct | ‡ | NonStruct |  |
| 0..n | P | 0..n | Artifact |  |
| 0..n | Hn |  |  |  |
| 0..1 | H |  |  |  |
| 0..n | Title |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| DocumentFragment | 0..n | Document | 0..n | Document |
| 0..n | DocumentFragment | 0..n | DocumentFragment |  |
| 0..n | Part | ‡ | Part |  |
| 0..n | Div | ‡ | Div |  |
| 0..n | Sect | 0..n | Sect |  |
| 0..n | Aside | 0..n | Aside |  |
| 0..n | NonStruct | ‡ | NonStruct |  |
| 0..n | P | ∅* | Link |  |
| 0..n | Hn | ∅* | Annot |  |
| 0..1 | H | ∅* | FENote |  |
| 0..n | Title | ∅* | Caption |  |
| 0..n | Link | 0..n | Artifact |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| Part | ‡ | Document | 0..n | Document |
| ‡ | DocumentFragment | 0..n | DocumentFragment |  |
| ‡ | Part | ‡ | Part |  |
| ‡ | Div | ‡ | Div |  |
| ‡ | Sect | 0..n | Sect |  |
| ‡ | Aside | 0..n | Aside |  |
| ‡ | NonStruct | ‡ | NonStruct |  |
| ‡ | P | 0..n | Title |  |
| ‡ | Hn | ∅* | Link |  |
| ‡ | H | ∅* | Annot |  |
| ‡ | Title | ∅* | Form |  |
| ‡ | Sub | 0..n | FENote |  |
| ‡ | Lbl | 0..n | LBody |  |
| ‡ | Link | 0..n | Caption |  |
| ‡ | Annot | 0..n | Figure |  |
| ‡ | Form | 0..n | Formula |  |
| ‡ | FENote | 0..n | Artifact |  |
| ‡ | L |  |  |  |
| ‡ | Table |  |  |  |
| ‡ | Caption |  |  |  |
| ‡ | Figure |  |  |  |
| ‡ | Formula |  |  |  |
| ‡ | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Div | ‡ | Document | 0..n | Document |
| ‡ | DocumentFragment | 0..n | DocumentFragment |  |
| ‡ | Part | ‡ | Part |  |
| ‡ | Div | ‡ | Div |  |
| ‡ | Sect | 0..n | Sect |  |
| ‡ | Aside | 0..n | Aside |  |
| ‡ | NonStruct | ‡ | NonStruct |  |
| ‡ | P | 0..n | Title |  |
| ‡ | Hn | 0..n | Link |  |
| ‡ | H | 0..n | Annot |  |
| ‡ | Title | 0..n | Form |  |
| ‡ | Sub | 0..n | FENote |  |
| ‡ | Lbl | 0..n | LI |  |
| ‡ | Em | 0..n | LBody |  |
| ‡ | Strong | 0..n | TH |  |
| ‡ | Span | 0..n | TD |  |
| ‡ | Link | 0..n | Caption |  |
| ‡ | Annot | 0..n | Figure |  |
| ‡ | Form | 0..n | Formula |  |
| ‡ | Ruby | 0..n | Artifact |  |
| ‡ | RB |  |  |  |
| ‡ | RT |  |  |  |
| ‡ | RP |  |  |  |
| ‡ | Warichu |  |  |  |
| ‡ | WT |  |  |  |
| ‡ | WP |  |  |  |
| ‡ | FENote |  |  |  |
| ‡ | L |  |  |  |
| ‡ | LI |  |  |  |
| ‡ | LBody |  |  |  |
| ‡ | Table |  |  |  |
| ‡ | TR |  |  |  |
| ‡ | TH |  |  |  |
| ‡ | TD |  |  |  |
| ‡ | THead |  |  |  |
| ‡ | TBody |  |  |  |
| ‡ | TFoot |  |  |  |
| ‡ | Caption |  |  |  |
| ‡ | Figure |  |  |  |
| ‡ | Formula |  |  |  |
| ‡ | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Sect | 0..n | DocumentFragment | 0..n | Document |
| 0..n | Part | 0..n | DocumentFragment |  |
| 0..n | Div | ‡ | Part |  |
| 0..n | Sect | ‡ | Div |  |
| 0..n | Aside | 0..n | Sect |  |
| 0..n | NonStruct | 0..n | Aside |  |
| 0..n | P | ‡ | NonStruct |  |
| 0..n | Hn | 0..1 | Hn |  |
| 0..1 | H | 0..1 | H |  |
| 0..n | Title | 0..n | Link |  |
| 0..n | Lbl | 0..n | Annot |  |
| 0..n | Link | 0..n | FENote |  |
| 0..n | Annot | 0..n | LBody |  |
| 0..n | Form | 0..n | TH |  |
| 0..n | FENote | 0..n | TD |  |
| 0..n | L | 0..n | Caption |  |
| 0..n | Table | 0..n | Figure |  |
| 0..n | Caption | 0..n | Artifact |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| Aside | 0..n | Document | 0..n | Document |
| 0..n | DocumentFragment | 0..n | DocumentFragment |  |
| 0..n | Part | ‡ | Part |  |
| 0..n | Div | ‡ | Div |  |
| 0..n | Sect | 0..n | Sect |  |
| 0..n | NonStruct | ‡ | NonStruct |  |
| 0..n | P | 0..n | Title |  |
| 0..n | Hn | ∅* | Link |  |
| 0..1 | H | ∅* | Annot |  |
| 0..n | Lbl | 0..n | FENote |  |
| 0..n | Link | 0..n | LBody |  |
| 0..n | Annot | 0..n | Caption |  |
| 0..n | Form | 0..n | Figure |  |
| 0..n | FENote | 0..n | Formula |  |
| 0..n | L | 0..n | Artifact |  |
| 0..n | Table |  |  |  |
| 0..1 | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| NonStruct | ‡ | Document | 0..n | Document |
| ‡ | DocumentFragment | 0..n | DocumentFragment |  |
| ‡ | Part | ‡ | Part |  |
| ‡ | Div | ‡ | Div |  |
| ‡ | Sect | 0..n | Sect |  |
| ‡ | Aside | 0..n | Aside |  |
| ‡ | NonStruct | ‡ | NonStruct |  |
| ‡ | P | 0..n | Title |  |
| ‡ | Hn | 0..n | Sub |  |
| ‡ | H | 0..n | P |  |
| ‡ | Title | 0..n | Hn |  |
| ‡ | Sub | 0..n | H |  |
| ‡ | Lbl | 0..n | Lbl |  |
| ‡ | Em | 0..n | Em |  |
| ‡ | Strong | 0..n | Strong |  |
| ‡ | Span | 0..n | Span |  |
| ‡ | Link | 0..n | Link |  |
| ‡ | Annot | 0..n | Annot |  |
| ‡ | Form | 0..n | Form |  |
| ‡ | Ruby | 0..n | Ruby |  |
| ‡ | RB | 0..n | RB |  |
| ‡ | RT | 0..n | RT |  |
| ‡ | RP | 0..n | RP |  |
| ‡ | Warichu | 0..n | Warichu |  |
| ‡ | WT | 0..n | WT |  |
| ‡ | WP | 0..n | WP |  |
| ‡ | FENote | 0..n | FENote |  |
| ‡ | L | 0..n | L |  |
| ‡ | LI | 0..n | LI |  |
| ‡ | LBody | 0..n | LBody |  |
| ‡ | Table | 0..n | Table |  |
| ‡ | TR | 0..n | TR |  |
| ‡ | TH | 0..n | TH |  |
| ‡ | TD | 0..n | TD |  |
| ‡ | THead | 0..n | THead |  |
| ‡ | TBody | 0..n | TBody |  |
| ‡ | TFoot | 0..n | TFoot |  |
| ‡ | Caption | 0..n | Caption |  |
| ‡ | Figure | 0..n | Figure |  |
| ‡ | Formula | 0..n | Formula |  |
| ‡ | Artifact | 0..n | Artifact |  |
| ‡ | content item |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Title | 0..n | Part | 0..n | Document |
| 0..n | Div | 0..n | DocumentFragment |  |
| 0..n | Aside | ‡ | Part |  |
| 0..n | NonStruct | ‡ | Div |  |
| 0..n | P | 0..n | Sect |  |
| 0..n | Lbl | ‡ | NonStruct |  |
| 0..n | Em | ∅* | Link |  |
| 0..n | Strong | ∅* | Annot |  |
| 0..n | Span | 0..n | Artifact |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Ruby |  |  |  |
| 0..n | Warichu |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..1 | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |
| Sub | 0..n | NonStruct | ‡ | Part |
| 0..n | Lbl | ‡ | Div |  |
| 0..n | Em | ‡ | NonStruct |  |
| 0..n | Strong | 0..n | P |  |
| 0..n | Span | 0..n | Hn |  |
| 0..n | Link | 0..n | H |  |
| 0..n | Annot | 0..n | Lbl |  |
| 0..n | Form | 0..n | Em |  |
| 0..n | Ruby | 0..n | Strong |  |
| 0..n | Warichu | 0..n | Span |  |
| 0..n | FENote | 0..n | Link |  |
| 0..n | L | 0..n | Annot |  |
| 0..n | Figure | 0..n | RB |  |
| 0..n | Formula | 0..n | RT |  |
| 0..n | Artifact | 0..n | RP |  |
| 0..n | content item | 0..n | WT |  |
| 0..n | WP |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | Caption |  |  |  |
| ∅* | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| P | 0..n | NonStruct | 0..n | Document |
| 0..n | Sub | 0..n | DocumentFragment |  |
| 0..n | Lbl | ‡ | Part |  |
| 0..n | Em | ‡ | Div |  |
| 0..n | Strong | 0..n | Sect |  |
| 0..n | Span | 0..n | Aside |  |
| 0..n | Link | ‡ | NonStruct |  |
| 0..n | Annot | 0..n | Title |  |
| 0..n | Form | ∅* | Link |  |
| 0..n | Ruby | ∅* | Annot |  |
| 0..n | Warichu | 0..n | FENote |  |
| 0..n | FENote | 0..n | LBody |  |
| 0..n | L | 0..n | TH |  |
| 0..n | Figure | 0..n | TD |  |
| 0..n | Formula | 0..n | Caption |  |
| 0..n | Artifact | 0..n | Figure |  |
| 0..n | content item | 0..n | Formula |  |
| 0..n | Artifact |  |  |  |

| Hn | 0..1 | Sect | 0..n | Document |
| --- | --- | --- | --- | --- |
| 0..n | NonStruct | 0..n | DocumentFragment |  |
| 0..n | Sub | ‡ | Part |  |
| 0..n | Lbl | ‡ | Div |  |
| 0..n | Em | 0..n | Sect |  |
| 0..n | Strong | 0..n | Aside |  |
| 0..n | Span | ‡ | NonStruct |  |
| 0..n | Link | ∅* | Link |  |
| 0..n | Annot | ∅* | Annot |  |
| 0..n | Form | 0..n | LBody |  |
| 0..n | Ruby | 0..n | TH |  |
| 0..n | Warichu | 0..n | TD |  |
| 0..n | FENote | 0..n | Caption |  |
| 0..n | Figure | 0..n | Figure |  |
| 0..n | Formula | 0..n | Formula |  |
| 0..n | Artifact | 0..n | Artifact |  |
| 0..n | content item |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| H | 0..1 | Sect | 0..1 | Document |
| 0..n | NonStruct | 0..1 | DocumentFragment |  |
| 0..n | Sub | ‡ | Part |  |
| 0..n | Lbl | ‡ | Div |  |
| 0..n | Em | 0..1 | Sect |  |
| 0..n | Strong | 0..1 | Aside |  |
| 0..n | Span | ‡ | NonStruct |  |
| 0..n | Link | ∅* | Link |  |
| 0..n | Annot | ∅* | Annot |  |
| 0..n | Form | 0..1 | LBody |  |
| 0..n | Ruby | 0..1 | TH |  |
| 0..n | Warichu | 0..1 | TD |  |
| 0..n | FENote | 0..1 | Caption |  |
| 0..n | Figure | 0..1 | Figure |  |
| 0..n | Formula | 0..1 | Formula |  |
| 0..n | Artifact | 0..1 | Artifact |  |
| 0..n | content item |  |  |  |
| Lbl | 0..n | NonStruct | ‡ | Part |
| 0..n | Sub | ‡ | Div |  |
| 0..n | Em | 0..n | Sect |  |
| 0..n | Strong | 0..n | Aside |  |
| 0..n | Span | ‡ | NonStruct |  |
| 0..n | Link | 0..n | Title |  |
| 0..n | Annot | 0..n | Sub |  |
| 0..n | Form | 0..n | P |  |
| 0..n | Ruby | 0..n | Hn |  |
| 0..n | Warichu | 0..n | H |  |
| 0..n | FENote | 0..n | Em |  |
| 0..n | Figure | 0..n | Strong |  |
| 0..n | Formula | 0..n | Span |  |
| 0..n | Artifact | 0..n | Link |  |
| 0..n | content item | 0..n | Annot |  |
| 0..n | Form |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LI |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Em | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Lbl | 0..n | Title |  |
| 0..n | Em | 0..n | Sub |  |
| 0..n | Strong | 0..n | P |  |
| 0..n | Span | 0..n | Hn |  |
| 0..n | Link | 0..n | H |  |
| 0..n | Annot | 0..n | Lbl |  |
| 0..n | Form | 0..n | Em |  |
| 0..n | Ruby | 0..n | Strong |  |
| 0..n | Warichu | 0..n | Span |  |
| 0..n | FENote | 0..n | Link |  |
| 0..n | Figure | 0..n | Annot |  |
| 0..n | Formula | 0..n | RB |  |
| 0..n | Artifact | 0..n | RT |  |
| 0..n | content item | 0..n | RP |  |
| 0..n | WT |  |  |  |
| 0..n | WP |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Strong | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Lbl | 0..n | Title |  |
| 0..n | Em | 0..n | Sub |  |
| 0..n | Strong | 0..n | P |  |
| 0..n | Span | 0..n | Hn |  |
| 0..n | Link | 0..n | H |  |
| 0..n | Annot | 0..n | Lbl |  |
| 0..n | Form | 0..n | Em |  |
| 0..n | Ruby | 0..n | Strong |  |
| 0..n | Warichu | 0..n | Span |  |
| 0..n | FENote | 0..n | Link |  |
| 0..n | Figure | 0..n | Annot |  |
| 0..n | Formula | 0..n | RB |  |
| 0..n | Artifact | 0..n | RT |  |
| 0..n | content item | 0..n | RP |  |
| 0..n | WT |  |  |  |
| 0..n | WP |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Span | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Lbl | 0..n | Title |  |
| 0..n | Em | 0..n | Sub |  |
| 0..n | Strong | 0..n | P |  |
| 0..n | Span | 0..n | Hn |  |
| 0..n | Link | 0..n | H |  |
| 0..n | Annot | 0..n | Lbl |  |
| 0..n | Form | 0..n | Em |  |
| 0..n | Ruby | 0..n | Strong |  |
| 0..n | Warichu | 0..n | Span |  |
| 0..n | FENote | 0..n | Link |  |
| 0..n | Figure | 0..n | Annot |  |
| 0..n | Formula | 0..n | RB |  |
| 0..n | Artifact | 0..n | RT |  |
| 0..n | content item | 0..n | RP |  |
| 0..n | WT |  |  |  |
| 0..n | WP |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Link | ∅* | DocumentFragment | 0..n | Document |
| ∅* | Part | 0..n | DocumentFragment |  |
| 0..n | Div | ‡ | Part |  |
| 0..n | Sect | ‡ | Div |  |
| ∅* | Aside | 0..n | Sect |  |
| 0..n | NonStruct | 0..n | Aside |  |
| ∅* | P | ‡ | NonStruct |  |
| ∅* | Hn | 0..n | Title |  |
| ∅* | H | 0..n | Sub |  |
| ∅* | Title | 0..n | P |  |
| 0..n | Sub | 0..n | Hn |  |
| 0..n | Lbl | 0..n | H |  |
| 0..n | Em | 0..n | Lbl |  |
| 0..n | Strong | 0..n | Em |  |
| 0..n | Span | 0..n | Strong |  |
| 0..n | Annot | 0..n | Span |  |
| ∅* | Form | 0..n | Annot |  |
| 0..n | Ruby | 0..n | RB |  |
| 0..n | Warichu | 0..n | RT |  |
| 0..n | FENote | 0..n | RP |  |
| ∅* | L | 0..n | WT |  |
| ∅* | Table | 0..n | WP |  |
| ∅* | Caption | 0..n | FENote |  |
| 0..n | Figure | 0..n | LBody |  |
| 0..n | Formula | 0..n | TH |  |
| 0..n | Artifact | 0..n | TD |  |
| 0..n | content item | 0..n | Caption |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Annot | ∅* | DocumentFragment | 0..n | Document |
| ∅* | Part | 0..n | DocumentFragment |  |
| 0..n | Div | ‡ | Part |  |
| 0..n | Sect | ‡ | Div |  |
| ∅* | Aside | 0..n | Sect |  |
| 0..n | NonStruct | 0..n | Aside |  |
| ∅* | P | ‡ | NonStruct |  |
| ∅* | Hn | 0..n | Title |  |
| ∅* | H | 0..n | Sub |  |
| ∅* | Title | 0..n | P |  |
| 0..n | Sub | 0..n | Hn |  |
| 0..n | Lbl | 0..n | H |  |
| 0..n | Em | 0..n | Lbl |  |
| 0..n | Strong | 0..n | Em |  |
| 0..n | Span | 0..n | Strong |  |
| 0..n | Link | 0..n | Span |  |
| 0..n | Annot | 0..n | Link |  |
| ∅* | Form | 0..n | Annot |  |
| 0..n | Ruby | 0..n | RB |  |
| 0..n | Warichu | 0..n | RT |  |
| 0..n | FENote | 0..n | RP |  |
| ∅* | L | 0..n | WT |  |
| ∅* | Table | 0..n | WP |  |
| ∅* | Caption | 0..n | FENote |  |
| 0..n | Figure | 0..n | LBody |  |
| 0..n | Formula | 0..n | TH |  |
| 0..n | Artifact | 0..n | TD |  |
| 0..n | content item | 0..n | Caption |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Form | ∅* | Part | 0..n | Document |
| 0..n | Div | 0..n | DocumentFragment |  |
| 0..n | NonStruct | ‡ | Part |  |
| 0..n | Lbl | ‡ | Div |  |
| 0..n | FENote | 0..n | Sect |  |
| ∅* | L | 0..n | Aside |  |
| ∅* | Table | ‡ | NonStruct |  |
| 0..1 | Caption | 0..n | Title |  |
| ∅* | Figure | 0..n | Sub |  |
| ∅* | Formula | 0..n | P |  |
| 0..n | Artifact | 0..n | Hn |  |
| 0..n | content item | 0..n | H |  |
| 0..n | Lbl |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| ∅* | Link |  |  |  |
| ∅* | Annot |  |  |  |
| 0..n | RB |  |  |  |
| 0..n | RT |  |  |  |
| 0..n | RP |  |  |  |
| 0..n | WT |  |  |  |
| 0..n | WP |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Ruby | 0..n | NonStruct | ‡ | Div |
| [a] | RB | ‡ | NonStruct |  |
| [a] | RT | 0..n | Title |  |
| [a] | RP | 0..n | Sub |  |
| 0..n | content item | 0..n | P |  |
| 0..n | Hn |  |  |  |
| 0..n | H |  |  |  |
| 0..n | Lbl |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| RB | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Em | [a] | Ruby |  |
| 0..n | Strong | 0..n | Artifact |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Artifact |  |  |  |
| ‡ | content item |  |  |  |
| RT | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Em | [a] | Ruby |  |
| 0..n | Strong | 0..n | Artifact |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Artifact |  |  |  |
| ‡ | content item |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| RP | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Em | [a] | Ruby |  |
| 0..n | Strong | 0..n | Artifact |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Artifact |  |  |  |
| ‡ | content item |  |  |  |
| Warichu | 0..n | NonStruct | ‡ | Div |
| [b] | WT | ‡ | NonStruct |  |
| [b] | WP | 0..n | Title |  |
| 0..n | content item | 0..n | Sub |  |
| 0..n | P |  |  |  |
| 0..n | Hn |  |  |  |
| 0..n | H |  |  |  |
| 0..n | Lbl |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| WT | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Em | [b] | Warichu |  |
| 0..n | Strong | 0..n | Artifact |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Artifact |  |  |  |
| ‡ | content item |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| WP | 0..n | NonStruct | ‡ | Div |
| 0..n | Sub | ‡ | NonStruct |  |
| 0..n | Em | [b] | Warichu |  |
| 0..n | Strong | 0..n | Artifact |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| c | Figure |  |  |  |
| 0..n | Artifact |  |  |  |
| ‡ | content item |  |  |  |
| FENote | ∅* | DocumentFragment | 0..n | Document |
| 0..n | Part | 0..n | DocumentFragment |  |
| 0..n | Div | ‡ | Part |  |
| 0..n | Sect | ‡ | Div |  |
| 0..n | Aside | 0..n | Sect |  |
| 0..n | NonStruct | 0..n | Aside |  |
| 0..n | P | ‡ | NonStruct |  |
| 0..n | Sub | 0..n | Title |  |
| 0..n | Lbl | 0..n | Sub |  |
| 0..n | Em | 0..n | P |  |
| 0..n | Strong | 0..n | Hn |  |
| 0..n | Span | 0..n | H |  |
| 0..n | Link | 0..n | Lbl |  |
| 0..n | Annot | 0..n | Em |  |
| 0..n | Form | 0..n | Strong |  |
| 0..n | Ruby | 0..n | Span |  |
| 0..n | Warichu | 0..n | Link |  |
| 0..n | FENote | 0..n | Annot |  |
| 0..n | L | 0..n | Form |  |
| 0..n | Table | 0..n | FENote |  |
| ∅* | Caption | 0..n | LBody |  |
| 0..n | Figure | 0..n | TH |  |
| 0..n | Formula | 0..n | TD |  |
| 0..n | Artifact | 0..n | Caption |  |
| 0..n | content item | 0..n | Figure |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| L | 0..n | NonStruct | 0..n | Document |
| 0..n | L | 0..n | DocumentFragment |  |
| 0..n | LI | ‡ | Part |  |
| 0..1 | Caption | ‡ | Div |  |
| 0..n | Artifact | 0..n | Sect |  |
| 0..n | Aside |  |  |  |
| ‡ | NonStruct |  |  |  |
| 0..n | Title |  |  |  |
| 0..n | Sub |  |  |  |
| 0..n | P |  |  |  |
| ∅* | Link |  |  |  |
| ∅* | Annot |  |  |  |
| ∅* | Form |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | LBody |  |  |  |
| 0..n | TH |  |  |  |
| 0..n | TD |  |  |  |
| 0..n | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| LI | 0..n | Div | ‡ | Div |
| 0..n | NonStruct | ‡ | NonStruct |  |
| 0..n | Lbl | 0..n | L |  |
| 0..n | LBody | 0..n | Artifact |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| LBody | 0..n | Part | ‡ | Div |
| 0..n | Div | ‡ | NonStruct |  |
| 0..n | Sect | 0..n | LI |  |
| 0..n | Aside | 0..n | Artifact |  |
| 0..n | NonStruct |  |  |  |
| 0..n | P |  |  |  |
| 0..n | Hn |  |  |  |
| 0..1 | H |  |  |  |
| 0..n | Sub |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Ruby |  |  |  |
| 0..n | Warichu |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..1 | Caption |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |

Table 0..n NonStruct 0..n Document
| 0..n | TR | 0..n | DocumentFragment |
| --- | --- | --- | --- |
| 0..1 | THead | ‡ | Part |
| 0..n | TBody | ‡ | Div |
| 0..1 | TFoot | 0..n | Sect |
| 0..1 | Caption | 0..n | Aside |
| 0..n | Artifact | ‡ | NonStruct |
| 0..n | Title |  |  |
| ∅* | Link |  |  |
| ∅* | Annot |  |  |
| ∅* | Form |  |  |
| 0..n | FENote |  |  |
| 0..n | LBody |  |  |
| 0..n | TH |  |  |
| 0..n | TD |  |  |
| 0..n | Caption |  |  |
| 0..n | Figure |  |  |
| 0..n | Formula |  |  |
| 0..n | Artifact |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| TR | 0..n | NonStruct | ‡ | Div |
| 0..n | TH | ‡ | NonStruct |  |
| 0..n | TD | 0..n | Table |  |
| 0..n | Artifact | 0..n | THead |  |
| 0..n | TBody |  |  |  |
| 0..n | TFoot |  |  |  |
| 0..n | Artifact |  |  |  |
| TH | 0..n | Div | ‡ | Div |
| 0..n | Sect | ‡ | NonStruct |  |
| 0..n | NonStruct | 0..n | TR |  |
| 0..n | P | 0..n | Artifact |  |
| 0..n | Hn |  |  |  |
| 0..1 | H |  |  |  |
| 0..n | Lbl |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Ruby |  |  |  |
| 0..n | Warichu |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| TD | 0..n | Div | ‡ | Div |
| 0..n | Sect | ‡ | NonStruct |  |
| 0..n | NonStruct | 0..n | TR |  |
| 0..n | P | 0..n | Artifact |  |
| 0..n | Hn |  |  |  |
| 0..1 | H |  |  |  |
| 0..n | Lbl |  |  |  |
| 0..n | Em |  |  |  |
| 0..n | Strong |  |  |  |
| 0..n | Span |  |  |  |
| 0..n | Link |  |  |  |
| 0..n | Annot |  |  |  |
| 0..n | Form |  |  |  |
| 0..n | Ruby |  |  |  |
| 0..n | Warichu |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |
| THead | 0..n | NonStruct | ‡ | Div |
| 0..n | TR | ‡ | NonStruct |  |
| 0..n | Artifact | 0..1 | Table |  |
| 0..n | Artifact |  |  |  |
| TBody | 0..n | NonStruct | ‡ | Div |
| 0..n | TR | ‡ | NonStruct |  |
| 0..n | Artifact | 0..n | Table |  |
| 0..n | Artifact |  |  |  |
| TFoot | 0..n | NonStruct | ‡ | Div |
| 0..n | TR | ‡ | NonStruct |  |
| 0..n | Artifact | 0..1 | Table |  |
| 0..n | Artifact |  |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Caption | ∅* | DocumentFragment | ‡ | Part |
| 0..n | Part | ‡ | Div |  |
| 0..n | Div | 0..n | Sect |  |
| 0..n | Sect | 0..1 | Aside |  |
| 0..n | Aside | ‡ | NonStruct |  |
| 0..n | NonStruct | 0..1 | Title |  |
| 0..n | P | ∅* | Link |  |
| 0..n | Hn | ∅* | Annot |  |
| 0..1 | H | 0..1 | Form |  |
| 0..n | Sub | ∅* | FENote |  |
| 0..n | Lbl | 0..1 | L |  |
| 0..n | Em | 0..1 | LBody |  |
| 0..n | Strong | 0..1 | Table |  |
| 0..n | Span | 0..1 | Figure |  |
| 0..n | Link | 0..1 | Formula |  |
| 0..n | Annot | 0..1 | Artifact |  |
| 0..n | Form |  |  |  |
| 0..n | Ruby |  |  |  |
| 0..n | Warichu |  |  |  |
| 0..n | FENote |  |  |  |
| 0..n | L |  |  |  |
| 0..n | Table |  |  |  |
| 0..n | Figure |  |  |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |
| --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |

Figure 0..n Part 0..n Document
| 0..n | Div | 0..n | DocumentFragment |
| --- | --- | --- | --- |
| 0..n | Sect | ‡ | Part |
| 0..n | Aside | ‡ | Div |
| 0..n | NonStruct | 0..n | Sect |
| 0..n | P | 0..n | Aside |
| 0..n | Hn | ‡ | NonStruct |
| 0..1 | H | 0..n | Title |
| ∅* | Sub | 0..n | Sub |
| 0..n | Lbl | 0..n | P |
| 0..n | Em | 0..n | Hn |
| 0..n | Strong | 0..n | H |
| 0..n | Span | 0..n | Lbl |
| 0..n | Link | 0..n | Em |
| 0..n | Annot | 0..n | Strong |
| 0..n | Form | 0..n | Span |
| 0..n | Ruby | 0..n | Link |
| 0..n | Warichu | 0..n | Annot |
| 0..n | FENote | ∅* | Form |
| 0..n | L | c | WP |
| 0..n | Table | 0..n | FENote |
| 0..1 | Caption | 0..n | LBody |
| 0..n | Figure | 0..n | TH |
| 0..n | Formula | 0..n | TD |
| 0..n | Artifact | 0..n | Caption |
| 0..n | content item | 0..n | Figure |
| 0..n | Formula |  |  |
| 0..n | Artifact |  |  |


| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Formula | 0..n | Part | 0..n | Document |
| 0..n | Div | 0..n | DocumentFragment |  |
| 0..n | Aside | ‡ | Part |  |
| 0..n | NonStruct | ‡ | Div |  |
| 0..n | P | 0..n | Sect |  |
| 0..n | Hn | 0..n | Aside |  |
| 0..1 | H | ‡ | NonStruct |  |
| 0..n | Sub | 0..n | Title |  |
| 0..n | Lbl | 0..n | Sub |  |
| 0..n | Em | 0..n | P |  |
| 0..n | Strong | 0..n | Hn |  |
| 0..n | Span | 0..n | H |  |
| 0..n | Link | 0..n | Lbl |  |
| 0..n | Annot | 0..n | Em |  |
| 0..n | Form | 0..n | Strong |  |
| 0..n | Ruby | 0..n | Span |  |
| 0..n | Warichu | 0..n | Link |  |
| 0..n | FENote | 0..n | Annot |  |
| 0..n | L | ∅* | Form |  |
| 0..n | Table | 0..n | FENote |  |
| 0..1 | Caption | 0..n | LBody |  |
| 0..n | Figure | 0..n | TH |  |
| 0..n | Formula | 0..n | TD |  |
| 0..n | Artifact | 0..n | Caption |  |
| 0..n | content item | 0..n | Figure |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |


ISO 32000-2:2020

| Structure Type | Children | Parents |  |  |
| --- | --- | --- | --- | --- |
| Occ. | Structure Type | Occ. | Structure Type |  |
| Artifact | 0..n | Document | 0..n | Document |
| 0..n | DocumentFragment | 0..n | DocumentFragment |  |
| 0..n | Part | ‡ | Part |  |
| 0..n | Div | ‡ | Div |  |
| 0..n | Sect | 0..n | Sect |  |
| 0..n | Aside | 0..n | Aside |  |
| 0..n | NonStruct | ‡ | NonStruct |  |
| 0..n | P | 0..n | Title |  |
| 0..n | Hn | 0..n | Sub |  |
| 0..1 | H | 0..n | P |  |
| 0..n | Title | 0..n | Hn |  |
| 0..n | Sub | 0..n | H |  |
| 0..n | Lbl | 0..n | Lbl |  |
| 0..n | Em | 0..n | Em |  |
| 0..n | Strong | 0..n | Strong |  |
| 0..n | Span | 0..n | Span |  |
| 0..n | Link | 0..n | Link |  |
| 0..n | Annot | 0..n | Annot |  |
| 0..n | Form | 0..n | Form |  |
| 0..n | Ruby | 0..n | RB |  |
| 0..n | RB | 0..n | RT |  |
| 0..n | RT | 0..n | RP |  |
| 0..n | RP | 0..n | WT |  |
| 0..n | Warichu | 0..n | WP |  |
| 0..n | WT | 0..n | FENote |  |
| 0..n | WP | 0..n | L |  |
| 0..n | FENote | 0..n | LI |  |
| 0..n | L | 0..n | LBody |  |
| 0..n | LI | 0..n | Table |  |
| 0..n | LBody | 0..n | TR |  |
| 0..n | Table | 0..n | TH |  |
| 0..n | TR | 0..n | TD |  |
| 0..n | TH | 0..n | THead |  |
| 0..n | TD | 0..n | TBody |  |
| 0..n | THead | 0..n | TFoot |  |
| 0..n | TBody | 0..n | Caption |  |
| 0..n | TFoot | 0..n | Figure |  |
| 0..1 | Caption | 0..n | Formula |  |
| 0..n | Figure | 0..n | Artifact |  |
| 0..n | Formula |  |  |  |
| 0..n | Artifact |  |  |  |
| 0..n | content item |  |  |  |


