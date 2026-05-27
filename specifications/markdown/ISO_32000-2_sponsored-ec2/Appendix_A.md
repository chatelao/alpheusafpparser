Annex A (informative) Operator Summary

## A.1 General

This annex lists, in alphabetical order, all the operators that may be used in PDF content streams.

## A.2 PDF content stream operators

"Table A.1 — PDF content stream operators" lists each operator, its corresponding PostScript language operators (when it is an exact or near-exact equivalent of the PDF operator), a description of the operator, and references to the table where each operator is introduced.

Table A.1 — PDF content stream operators

| Operator | PostScript | Description | Table Equivalent |
| --- | --- | --- | --- |
| b | closepath, fill, | Close, fill, and stroke path using non-zero | "Table 59 — Path-painting operators" |
| stroke | winding number rule |  |  |
| B | fill, stroke | Fill and stroke path using non-zero winding | "Table 59 — Path-painting operators" number rule |
| b* | closepath, | Close, fill, and stroke path using even-odd | "Table 59 — Path-painting operators" |
| eofill, stroke | rule |  |  |
| B* | eofill, stroke | Fill and stroke path using even-odd rule | "Table 59 — Path-painting operators" |
| BDC | (PDF 1.2) Begin marked-content sequence | "Table 352 — Marked-content |  |
| with property list | operators" |  |  |

# BI Begin inline image object "Table 90 — Inline image operators"

| BMC | (PDF 1.2) Begin marked-content sequence | "Table 351 — Entries in a data dictionary" |  |
| --- | --- | --- | --- |
| BT | Begin text object | "Table 107 — Text-showing operators" |  |
| BX | (PDF 1.1) Begin compatibility section | "Table 33 — Compatibility operators" |  |
| c | curveto | Append curved segment to path (three | "Table 60 — Clipping path operators" control points) |
| cm | concat | Concatenate matrix to current | "Table 56 — Graphics state operators" transformation matrix |


| Operator | PostScript | Description | Table Equivalent |
| --- | --- | --- | --- |
| CS | setcolorspace | (PDF 1.1) Set colour space for stroking | "Table 73 — Colour operators" operations |
| cs | setcolorspace | (PDF 1.1) Set colour space for nonstroking | "Table 73 — Colour operators" operations |
| d | setdash | Set line dash pattern | "Table 56 — Graphics state operators" |
| d0 | setcharwidth | Set glyph width in Type 3 font | "Table 111 — Type 3 font operators" |
| d1 | setcachedevice | Set glyph width and bounding box in Type 3 | "Table 111 — Type 3 font operators" font |
| Do | Invoke named XObject | "Table 86 — XObject operator" |  |
| DP | (PDF 1.2) Define marked-content point with | "Table 352 — Marked-content |  |
| property list | operators" |  |  |

# EI End inline image object "Table 90 — Inline image operators"

| EMC | (PDF 1.2) End marked-content sequence | "Table 352 — Marked-content operators" |  |
| --- | --- | --- | --- |
| ET | End text object | "Table 105 — Text object operators" |  |
| EX | (PDF 1.1) End compatibility section | "Table 33 — Compatibility operators" |  |
| f | fill | Fill path using non-zero winding number | "Table 59 — Path-painting operators" rule |
| F | fill | Fill path using non-zero winding number | "Table 59 — Path-painting operators" rule (deprecated in PDF 2.0) |
| f* | eofill | Fill path using even-odd rule | "Table 59 — Path-painting operators" |
| G | setgray | Set gray level for stroking operations | "Table 73 — Colour operators" |
| g | setgray | Set gray level for nonstroking operations | "Table 73 — Colour operators" |
| gs | (PDF 1.2) Set parameters from graphics | "Table 56 — Graphics state operators" state parameter dictionary |  |
| h | closepath | Close subpath | "Table 58 — Path construction operators" |
| i | setflat | Set flatness tolerance | "Table 56 — Graphics state operators" |

# ID Begin inline image data "Table 90 — Inline image operators"

| j | setlinejoin | Set line join style | "Table 56 — Graphics state operators" |
| --- | --- | --- | --- |
| J | setlinecap | Set line cap style | "Table 56 — Graphics state operators" |
| K | setcmykcolor | Set CMYK colour for stroking operations | "Table 73 — Colour operators" |


| Operator | PostScript | Description | Table Equivalent |
| --- | --- | --- | --- |
| k | setcmykcolor | Set CMYK colour for nonstroking | "Table 73 — Colour operators" operations |
| l | lineto | Append straight line segment to path | "Table 58 — Path construction operators" |
| m | moveto | Begin new subpath | "Table 58 — Path construction operators" |
| M | setmiterlimit | Set miter limit | "Table 56 — Graphics state operators" |
| MP | (PDF 1.2) Define marked-content point | "Table 352 — Marked-content operators" |  |
| n | End path without filling or stroking | "Table 59 — Path-painting operators" |  |
| q | gsave | Save graphics state | "Table 56 — Graphics state operators" |
| Q | grestore | Restore graphics state | "Table 56 — Graphics state operators" |
| re | Append rectangle to path | "Table 58 — Path construction operators" |  |
| RG | setrgbcolor | Set RGB colour for stroking operations | "Table 73 — Colour operators" |
| rg | setrgbcolor | Set RGB colour for nonstroking operations | "Table 73 — Colour operators" |
| ri | Set colour rendering intent | "Table 56 — Graphics state operators" |  |
| s | closepath, | Close and stroke path | "Table 59 — Path-painting operators" stroke |
| S | stroke | Stroke path | "Table 59 — Path-painting operators" |
| SC | setcolor | (PDF 1.1) Set colour for stroking operations | "Table 73 — Colour operators" |
| sc | setcolor | (PDF 1.1) Set colour for nonstroking | "Table 73 — Colour operators" operations |
| SCN | setcolor | (PDF 1.2) Set colour for stroking operations | "Table 73 — Colour operators" (ICCBased and special colour spaces) |
| scn | setcolor | (PDF 1.2) Set colour for nonstroking | "Table 73 — Colour operators" operations (ICCBased and special colour spaces) |
| sh | shfill | (PDF 1.3) Paint area defined by shading | "Table 76 — Shading operator" pattern |
| T* | Move to start of next text line | "Table 106 — Text-positioning operators" |  |
| Tc | Set character spacing | "Table 103 — Text state operators" |  |


| Operator | PostScript | Description | Table Equivalent |
| --- | --- | --- | --- |
| Td | Move text position | "Table 106 — Text-positioning operators" |  |
| TD | Move text position and set leading | "Table 106 — Text-positioning operators" |  |
| Tf | selectfont | Set text font and size | "Table 103 — Text state operators" |
| Tj | show | Show text | "Table 107 — Text-showing operators" |
| TJ | Show text, allowing individual glyph | "Table 107 — Text-showing |  |
| positioning | operators" |  |  |
| TL | Set text leading | "Table 103 — Text state operators" |  |
| Tm | Set text matrix and text line matrix | "Table 106 — Text-positioning operators" |  |
| Tr | Set text rendering mode | "Table 103 — Text state operators" |  |
| Ts | Set text rise | "Table 103 — Text state operators" |  |
| Tw | Set word spacing | "Table 103 — Text state operators" |  |
| Tz | Set horizontal text scaling | "Table 103 — Text state operators" |  |
| v | curveto | Append curved segment to path (initial | "Table 58 — Path construction |
| point replicated) | operators" |  |  |
| w | setlinewidth | Set line width | "Table 56 — Graphics state operators" |
| W | clip | Set clipping path using non-zero winding | "Table 60 — Clipping path operators" number rule |
| W* | eoclip | Set clipping path using even-odd rule | "Table 60 — Clipping path operators" |
| y | curveto | Append curved segment to path (final point | "Table 58 — Path construction |
| replicated) | operators" |  |  |
| ' | Move to next line and show text | "Table 107 — Text-showing operators" |  |
| " | Set word and character spacing, move to | "Table 107 — Text-showing |  |
| next line, and show text | operators" |  |  |


