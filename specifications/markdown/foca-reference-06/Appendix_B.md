# Appendix B. Mapping of ISO Parameters

This appendix provides information to aid in understanding the relation between the parameters in this architecture and the parameters defined by the ISO/IEC 9541-1 Font Information Interchange standard. Detailed information about the transformation between ISO and FOCA parameters is defined with each FOCA parameter in the body of this document.

### Table 24. Mapping of ISO Font Parameters

| ISO 9541 Parameters | FOCA Parameters |
| :--- | :--- |
| **FONTNAME** (Req.) | Resource Name |
| **DATAVERSION** (Opt.) | Function Set Code |
| **STANDARDVERSION** (Req.) | Function Set Code |
| **DATASOURCE** (Opt.) | Data Source |
| **DATACOPYRIGHT** (Opt.) | Intellectual Property Data |
| **DSNSOURCE** (Req.) | Design Source |
| **DSNCOPYRIGHT** (Opt.) | Intellectual Property Data |
| **RELUNITS** (Opt.) | Measurement Units |
| **TYPEFACE** (Opt.) | Typeface Name |
| **FONTFAMILY** (Req.) | Family Name |
| **POSTURE** (Req.) | Posture Class |
| **POSTUREANGLE** (Opt.) | Nominal Character Slope |
| **WEIGHT** (Req.) | Weight Class |
| **PROPWIDTH** (Req.) | Width Class |
| **STRUCTURE** (Req.) | Structure Class |
| **DSNGROUP** (Req.) | Design Group / Sub-Group / Specific Group / General Class / Sub-Class |
| **NUMGLYPHS** (Opt.) | Number of Characters |
| **INCGLYPHCOLS** (Req.) | Graphic Character Set Global ID |
| **INCGLYPHS** (Req.) | Graphic Character Global ID |
| **DSNSIZE** (Req.) | Nominal Vertical Font Size |
| **MINSIZE** (Req.) | Minimum Vertical Font Size |
| **MAXSIZE** (Req.) | Maximum Vertical Font Size |
| **CAPHEIGHT** (Opt.) | Cap-M Height |
| **LCHEIGHT** (Opt.) | X-Height |
| **WRMODENAME** (Opt.) | (derived from character rotation) |
| **NOMESCDIR** (Req.) | Character Rotation |
| **ESCCLASS** (Req.) | Proportional Spaced (flag) |
| **AVGESCX/Y** (Req.) | Average Escapement |
| **AVGLCESCX/Y** (Req.) | Average Lowercase Escapement |
| **AVGCAPESCX/Y** (Req.) | Average Capital Escapement |
| **TABESCX/Y** (Req.) | Figure Space Increment |
| **MAXFONTEXT** (Req.) | Maximum Character Box Height & Width |
| **SCOREOFFSETX/Y** (Opt.) | Underscore / Overscore / Throughscore Position |
| **SCORETHICK** (Opt.) | Underscore / Overscore / Throughscore Width |
| **VSOFFSETX/Y** (Opt.) | Superscript / Subscript X-Axis / Y-Axis Offset |
| **VSSCALEX/Y** (Opt.) | Superscript / Subscript Vertical / Horizontal Size |
| **MINLINESP** (Opt.) | Default Baseline Increment |
| **MINANASCALE** (Opt.) | Minimum Horizontal Font Size |
| **MAXANASCALE** (Opt.) | Maximum Horizontal Font Size |
| **COPYFITMEASURE** (Opt.) | Average Weighted Escapement |
| **GNAME** (Req.) | Graphic Character Global ID |
| **PX, PY** (Opt.) | (coordinate system origin) |
| **EX, EY** (Req.) | Character Increment |
| **EXT** (Req.) | A-Space, B-Space, C-Space, Ascender Height, Descender Depth |
| **PEAN** (Opt.) | (linkage by data structure, not name) |
| **PEAX** (Opt.) | Kerning Pair X Adjust |

**(No corresponding ISO parameter)**:
*   Font Typeface Global ID
*   Font Use Code
*   Misc. Control Flags (Kerning Pair Data, MICR Font, etc.)
*   Em-Space Increment
*   Maximum/Minimum Character Slope
*   Nominal Horizontal Font Size
*   External/Internal Leading
*   Maximum Ascender Height / Baseline Extent / Baseline Offset / Character Increment / Descender Depth
*   Minimum A-Space
*   Space Character Increment
*   Character Box Height / Width
*   Frequency of Use
