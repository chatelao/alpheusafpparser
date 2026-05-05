Appendix B. Mapping of ISO Parameters
This appendix provides information to aid in understanding the relation between the parameters in this
architecture and the parameters defined by the ISO/IEC 9541-1 Font Information Interchange standard.
Detailed information about the transformation between ISO and FOCA parameters is defined with each FOCA
parameter in the body of this document.
Table 24. Mapping of ISO Font Parameters
ISO 9541 Parameters FOCA Parameters
FONTNAME (Req.)
Font Resource Name
Resource Name
(optional global name prefix)
DATAVERSION (Opt.)
Data Version
Function Set Code
(no mapping of values required)
STANDARDVERSION (Req.)
Standard Version
Function Set Code
(no mapping of values required)
DATASOURCE (Opt.)
Data Source
Data Source
DATACOPYRIGHT (Opt.)
Data Copyright
Intellectual Property Data
DSNSOURCE (Req.)
Design Source
Design Source
DSNCOPYRIGHT (Opt.)
Design Copyright
Intellectual Property Data
RELUNITS (Opt.)
Relative Rational Units
Measurement Units
(format & coordinate system transformation)
TYPEFACE (Opt.)
Typeface Name
Typeface Name
FONTFAMILY (Req.)
Font Family Name
Family Name
POSTURE (Req.)
Posture
Posture Class
POSTUREANGLE (Opt.)
Posture Angle
Nominal Character Slope
(format & rotation transformation)
WEIGHT (Req.)
Weight
Weight Class
PROPWIDTH (Req.)
Proportionate Width
Width Class
STRUCTURE (Req.)
Structure
Structure Class
DSNGROUP (Req.)
Design Group
Design Sub-Group
Design Specific Group
Design General Class
Design Sub-Class
Design Specific Group

## Page 216

194 FOCA Reference
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
NUMGLYPHS (Opt.)
Number of Glyphs
Number of Characters
INCGLYPHCOLS (Req.)
Included Glyph Collections
Graphic Character Set Global ID
(requires mapping of identifiers)
EXCGLYPHCOLS (Opt.)
Excluded Glyph Collections (no corresponding parameter)
INCGLYPHS (Req.)
Included Glyphs
Graphic Character Global ID
(requires mapping of identifiers)
EXCGLYPHS (Opt.)
Excluded Glyphs (no corresponding parameter)
DSNSIZE (Req.)
Design Size
Nominal Vertical Font Size
(format & unit transformation)
MINSIZE (Req.)
Recommended Minimum Size
Minimum Vertical Font Size
(format & unit transformation)
MAXSIZE (Req.)
Recommended Maximum Size
Maximum Vertical Font Size
(format & unit transformation)
CAPHEIGHT (Opt.)
Capital Height
Cap-M Height
(format & unit transformation)
LCHEIGHT (Opt.)
Lowercase Height
X-Height
(format & unit transformation)
MINFEATSZ (Opt.)
Minimum Feature Size (no corresponding parameter)
NOMCAPSTEMWIDTH (Opt.)
Nominal Capital Stem Width (no corresponding parameter)
NOMLCSTEMWIDTH (Opt.)
Nominal Lowercase Stem Width (no corresponding parameter)
NOMWRMODE (Opt.)
Nominal Writing Mode Name (no corresponding parameter)
(no corresponding ISO parameter)
Font
Class
(used by non-AFP products)
(no corresponding ISO parameter)
Font Sub-Class
(used by non-AFP products)
(no corresponding ISO parameter)
Font Typeface Global ID
(no corresponding ISO parameter)
Font Quality
(used by IBM
Displaywrite Product)
(no corresponding ISO parameter)
Font Use Code
(used by IBM
Presentation Manager)

## Page 217

FOCA Reference 195
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
(no corresponding ISO parameter)
Misc. Control Flags:
Kerning Pair Data, MICR Font,
Negative Image, Outline Data,
Outline Font, Overstruck Font,
Transformable Font, Underscored Font,
Uniform Character Box Font, Kerning,
Uniform A-Space, Uniform Baseline Offset,
Uniform Character Increment
(no corresponding ISO parameter)
Comment
WRMODENAME (Opt.)
Writing Mode Name (derived from character rotation)
NOMESCDIR (Req.)
Nominal Escapement Direction
Character Rotation
(format & rotation transformation)
ESCCLASS (Req.)
Escapement Class
Proportional Spaced
(flag maps to both escapement codes)
AVGESCX (Req.)
Average Escapement X
Average Escapement
(format & coordinate system transformation)
AVGESCY (Req.)
Average Escapement Y
Average Escapement
(format & coordinate system transformation)
AVGLCESCX (Req.)
Average Lowercase Escapement X
Average Lowercase Escapement
(format & coordinate system transformation)
AVGLCESCY (Req.)
Average Lowercase Escapement Y
Average Lowercase Escapement
(format & coordinate system transformation)
AVGCAPESCX (Req.)
Average Capital Escapement X
Average Capital Escapement
(format & coordinate system transformation)
AVGCAPESCY (Req.)
Average Capital Escapement Y
Average Capital Escapement
(format & coordinate system transformation)
TABESCX (Req.)
T abular Escapement X
Figure Space Increment
(format & coordinate system transformation)
TABESCY (Req.)
T abular Escapement Y
Figure Space Increment
(format & coordinate system transformation)
MAXFONTEXT (Req.)
Maximum Font Extents
Maximum Character Box Height & Width
(format & coordinate system transformation)
SECTORS (Opt.)
Sectors (no corresponding parameter)
ESCADJNAME (Opt.)
Escapement Adjust Name (no corresponding parameter)
CPEA (Opt.)
Class Pairwise Esc. Adjust (no corresponding parameter)
SEC (Opt.)
Scale Escapement Correction (no corresponding parameter)

## Page 218

196 FOCA Reference
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
MINESCADJSZE (Opt.)
Minimum Escapement Adjust Size (no corresponding parameter)
MAXESCADJSZE (Opt.)
Maximum Escapement Adjust Size (no corresponding parameter)
SCORENAME (Opt.)
Score Name (derived from parameter name)
SCOREOFFSETX (Opt.)
Score Position Offset X
Underscore Position
Overscore Position
Throughscore Position
(format & coordinate system transformation)
SCOREOFFSETY (Opt.)
Score Position Offset Y
Underscore Position
Overscore Position
Throughscore Position
(format & coordinate system transformation)
SCORETHICK (Opt.)
Score Thickness
Underscore Width
Overscore Width
Throughscore Width
(format & coordinate system transformation)
VSNAME (Opt.)
Variant Script Name (derived from parameter name)
VSOFFSETX (Opt.)
Varian
t Script Position Offset X
Superscript X-Axis Offset
Subscript X-Axis Offset
(format & coordinate system transformation)
VSOFFSETY (Opt.)
Variant Script Position Offset Y
Superscript Y-Axis Offset
Subscript Y-Axis Offset
(format & coordinate system transformation)
VSSCALEX (Opt.)
Variant Script Anamorphic Scale X
Superscript Vertical Font Size
Subscript Vertical Font Size
(format & coordinate system transformation)
VSSCALEY (Opt.)
Variant Script Anamorphic Scale Y
Superscript Horizontal Font Size
Subscript Horizontal Font Size
(format & coordinate system transformation)
MINLINESP (Opt.)
Recommended Minimum Line Spacing
Default Baseline Increment
(format & coordinate system transformation)
MINANASCALE (Opt.)
Recommended Minimum Anamorphic Scale
Minimum Horizontal Font Size
(format & coordinate system transform)
MAXANASCALE (Opt.)
Recommended Maximum Anamorphic Scale
Maximum Horizontal Font Size
(format & coordinate system transform)
NOMALIGN (Opt.)
Nominial Alignment Mode (no corresponding parameter)
ALIGNNAME (Opt.)
Alignment Mode Name (no corresponding parameter)
ALIGNOFFSETX (Opt.)
Alignment Line Position Offset X (no corresponding parameter)

## Page 219

FOCA Reference 197
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
ALIGNOFFSETY (Opt.)
Alignment Line Position Offset Y (no corresponding parameter)
ALIGNSCALEX (Opt.)
Alignment Mode Anamorphic Scale X (no corresponding parameter)
ALIGNSCALEY (Opt.)
Alignment Mode Anamorphic Scale Y (no corresponding parameter)
COPYFITNAME (Opt.)
Copy Fit T echnique Name (derived from parameter name)
COPYFITMEASURE (Opt.)
Copy Fit Measure
Average Weighted Escapement
(format & coordinate system transform)
DSNWORDADD (Opt.)
Design Wordspace Addition (no corresponding parameter)
DSNWORDAMPL (Opt.)
Design Wordspace Amplification (no corresponding parameter)
MINWORDADD (Opt.)
Recommended Minimum Wordspace Addition (no corresponding parameter)
MINWORDAMPL (Opt.)
Recommended Minimum Wordspace Amplification (no corresponding parameter)
MAXWORDADD (Opt.)
Recommended Maximum Wordspace Addition (no corresponding parameter)
MAXWORDAMPL (Opt.)
Recommended Maximum Wordspace Amplification (no corresponding parameter)
DSNLETTERADD (Opt.)
Design Letterspace Addition (no corresponding parameter)
DSNLETTERAMPL (Opt.)
Design Letterspace Amplification (no corresponding parameter)
MINLETTERADD (Opt.)
Recommended Minimum Letterspace Addition (no corresponding parameter)
MINLETTERAMPL (Opt.)
Recommended Minimum Letterspace Amplification (no corresponding parameter)
MAXLETTERADD (Opt.)
Recommended Maximum Letterspace Addition (no corresponding parameter)
MAXLETTERAMPL (Opt.)
Recommended Maximum Letterspace Amplification (no corresponding parameter)
(no corresponding ISO parameter)
Em-Space Increment
(no corresponding ISO parameter)
Maximum Character Slope
(no corresponding ISO parameter)
Minimum Character Slope
(no corresponding ISO parameter)
Nominal Horizontal Font Size

## Page 220

198 FOCA Reference
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
(no corresponding ISO parameter)
External Leading
(no corresponding ISO parameter)
Internal Leading
(no corresponding ISO parameter)
Maximum Ascender Height
(no corresponding ISO parameter)
Maximum Baseline Extent
(no corresponding ISO parameter)
Maximum Baseline Offset
(no corresponding ISO parameter)
Maximum Character Increment
(no corresponding ISO parameter)
Maximum Descender Depth
(no corresponding ISO parameter)
Maximum Lowercase Descender Depth
(no corresponding ISO parameter)
Minimum A-Space
(no corresponding ISO parameter)
Space Character Increment
GNAME (Req.)
Glyph Name
Graphic Character Global ID
(requires mapping of identifiers)
PX (Opt.)
Glyph Position Point X (coordinate system origin)
PY (Opt.)
Glyph Position Point Y (coordinate system origin)
EX (Req.)
Glyph Escapement Point X
Character Increment
(format & coordinate system transform)
EY (Req.)
Glyph Escapement Point Y
Character Increment
(format & coordinate system transform)
EXT (Req.)
Glyph Extents
A-Space, B-Space, C-Space,
Ascender Height, Descender Depth
(format & coordinate system transform)
LGN (Opt.)
Ligature Name (no corresponding parameter)
LGSN (Opt.)
Ligature Successor Name (no corresponding parameter)
PEAN (Opt.)
Pairwise Escapement Adjust Name (derived from parameter name)
(linkage by data structure, not name)
Kerning Pair Character 1
(linkage by data structure, not name)
Kerning Pair Character 2

## Page 221

FOCA Reference 199
Table 24 Mapping of ISO Font Parameters (cont'd.)
ISO 9541 Parameters FOCA Parameters
PEAX (Opt.)
Pairwise Escapement Adjust X
Kerning Pair X Adjust
(format & coordinate system transform)
PEAY (Opt.)
Pairwise Escapement Adjust Y (no corresponding parameter)
SPEAFORWDX (Opt.)
Sector Pairwise Forward Adjust X (no corresponding parameter)
SPEAFORWDY (Opt.)
Sector Pairwise Forward Adjust Y (no corresponding parameter)
SPEABACKWDX (Opt.)
Sector Pairwise Backward Adjust X (no corresponding parameter)
SPEABACKWDY (Opt.)
Sector Pairwise Backward Adjust Y (no corresponding parameter)
CPEAI (Opt.)
Class Pairwise Escapement Adjustment Indicator (no corresponding parameter)
EAI (Opt.)
Escapement Adjustment Indicator (no corresponding parameter)
MINEX (Opt.)
Minimum Adjusted Escapement X (no corresponding parameter)
MINEY (Opt.)
Minimum Adjusted Escapement Y (no corresponding parameter)
MAXEX (Opt.)
Maximum Adjusted Escapement X (no corresponding parameter)
MAXEY (Opt.)
Maximum Adjusted Escapement Y (no corresponding parameter)
(no corresponding ISO parameter)
Baseline Offset
(no corresponding ISO parameter)
Character Box Height
(no corresponding ISO parameter)
Character Box Width
(no corresponding ISO parameter)
Frequency of Use
(Implemented by IBM
Presentation Manager)

## Page 222

200 FOCA Reference

## Page 223

Copyright © AFP Consortium 1998, 2015 201
