# Chapter 4. Formats and Codes
This chapter describes the formats of the IOCA self-defining fields.
• The formats of the IOCA self-defining fields
• The code points used by IOCA
Formats An IOCA Image Segment is a set of self-defining fields. Each self-defining field is in either long format or extended format. Both formats start with a code for the self-defining field, and the length of the parameters that follow.
Long Format Byte 0 L Parameters Length C 1 2 - n where:
C is a one-byte code for the self-defining field.
L is the length of the following parameters, excluding L itself.
Extended Format Byte 0 L L Parameters Length C C 1 4 - n2 3 where:
CC is a two-byte code for the self-defining field. The first byte is always X'FE'.
This format is used by all of the following:
• Image Data (X'FE92')
• Band Image Data (X'FE9C')
• Include Tile parameter (X'FEB8')
• Tile TOC parameter (X'FEBB')
• Image Subsampling parameter (X'FECE')
Other values for the second byte of CC are reserved.
LL is the length of the parameters, excluding LL itself.


Code Points T able 3lists the codes used by IOCA, the names of the associated elements, and the formats used.
Table 3. IOCA Code Points Code Name Format X'70' Begin Segment Long format X'71' End Segment Long format X'8C' Begin Tile Long format X'8D' End Tile Long format X'8E' Begin Transparency Mask Long format X'8F' End Transparency Mask Long format
X'91' Begin Image Content Long format X'93' End Image Content Long format X'94' Image Size Long format X'95' Image Encoding Long format X'96' IDE Size Long format X'97' Image LUT-ID (Retired) Long format X'98' Band Image Long format X'9B' IDE Structure Long format
X'9F' External Algorithm Specification Long format X'B5' Tile Position Long format X'B6' Tile Size Long format X'B7' Tile Set Color Long format X'F4' Set Extended Bilevel Image Color Long format X'F6' Set Bilevel Image Color Long format X'F7' IOCA Function Set Identification Long format X'FE92' Image Data Extended format
X'FE9C' Band Image Data Extended format X'FEB3' nColor Names Extended format X'FEB8' Include Tile Extended format X'FEBB' Tile TOC Extended format X'FECE' Image Subsampling Extended format Code Points


