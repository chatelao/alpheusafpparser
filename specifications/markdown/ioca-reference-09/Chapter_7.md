# Chapter 7. Compliance
This chapter:
• Describes the concept of IOCA function sets
• Lists the product compliance rules
• Defines IOCA Function Sets FS10, FS11, FS14, FS40, FS42, FS45, and FS48
## Function Sets
A function set is a set of self-defining fields that describes an Image Object. Specifically, it is a definition of the Image Segment: which parameters the Image Segment should consist of, and what values each parameter should have. The Image Object described in the function set can thus be processed in different controlling environments.
Each function set has an identification. With that identification, products determine the level of support they must provide to generate or receive IOCA Image Objects.
Table 6. Function Set Identification ID Description Function Sets Currently Defined 0x Stand-alone 1x Carried by presentation-level data stream, non-tiled FS10, FS11, FS14 2x Library/resource FS20 (Retired)
3x Reserved 4x Carried by presentation-level data stream, tiled FS40, FS42, FS45, FS48 Note: x is generally assigned in ascending numerical order from zero.
IOCA defines seven function sets: FS10, FS11, FS14, FS40, FS42, FS45, and FS48.
• Function Set 10 is intended for bilevel images.
• Function Set 11 covers bilevel, grayscale, and color images.
• Function Set 14 covers bilevel, grayscale, and color images that allow use of transparency masks.
• Function Set 40 covers tiled bilevel images.
• Function Set 42 covers tiled bilevel images and tiled CMYK images with one bit per spot (SIZE1=SIZE2=
SIZE3=SIZE4=1, IDESZ=4).
• Function Set 45 carries tiled bilevel and CMYK images. CMYK images can be either one or eight bits per
spot (IDESZ=4 or IDESZ=32).
• Function Set 48 carries tiled bilevel, CMYK, and nColor images. CMYK images can be either one or eight
bits per spot (IDESZ=4 or IDESZ=32). nColor images are eight bits per spot (IDESZ is a multiple of eight).
Note: Function Set 20 is used only in MO:DCA-L and has been retired. See Appendix G, “Retired Architecture”.
Function Set 14 is a superset of Function Set 11. Function Set 11 is a superset of Function Set 10. Function Set 48 is a superset of Function Set 45. Function Set 45 is a superset of Function Set 42. Function Set 42 is a superset of Function Set 40. There are no other relationships among the function sets.


Products that conform to an IOCA function set must meet the following criteria:
• Products that generate IOCA Image Objects can only use the IOCA self-defining fields and parameter values
that are defined in the corresponding Function Set definition.
• Products that receive IOCA Image Objects must be capable of accepting any IOCA Image Object that
conforms to the corresponding Function Set definition.
The following sections show the self-defining fields that each function set contains and the acceptable values for each field.
## Function Sets


IOCA Function Set 10 (IOCA FS10)
Function Set 10 describes bilevel images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS10 are defined as follows:
Table 7. Function Set 10 Structure X'70' Begin Segment parameter X'91' Begin Image Content parameter + X'94' Image Size parameter + [ X'95' Image Encoding parameter ] + [ X'96' IDE Size parameter ] + [ X'97' Retired (Image LUT-ID parameter) ] X'FE92' Image Data (S)
X'93' End Image Content parameter X'71' End Segment parameter The self-defining fields and values acceptable for FS10 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Image Size ID (1) X'94' LENGTH (1) X'09'
UNITBASE (1) X'00' – X'02' HRESOL (2) X'0000' – X'7FFF' VRESOL (2) X'0000' – X'7FFF' HSIZE (2) X'0000' – X'7FFF' VSIZE (2) X'0000' – X'7FFF' Image Encoding ID (1) X'95' LENGTH (1) X'02' COMPRID (1) X'01', X'03', X'82' X'01' IBM MMR-Modified Modified Read
X'03' No compression X'82' G4 MMR-Modified Modified READ RECID (1) X'01' RIDIC IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01' 1bit/IDE Retired RESERVED (3) X'970100' Retired Image LUT-ID parameter Function Set 10


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs (see Note)
End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' Note: IDE value 0 represents an insignificant image point, and 1 represents a significant image point. The controlling environment determines how to interpret each value.
Function Set 10


IOCA Function Set 11 (IOCA FS11)
Function Set 11 is a superset of Function Set 10, and describes bilevel, grayscale, and color images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS11 are defined as follows:
Table 8. Function Set 11 Structure X'70' Begin Segment parameter X'91' Begin Image Content parameter + X'94' Image Size parameter + [ X'95' Image Encoding parameter ] + [ X'96' IDE Size parameter ] + [ X'97' Retired (Image LUT-ID parameter) ] + [ X'98' Band Image parameter ]
+ [ X'9B' IDE Structure parameter ] + [ X'9F' External Algorithm Specification parameter ] + [ X'FECE' Image Subsampling parameter ] Image Data or Band Image Data (S)
X'93' End Image Content parameter X'71' End Segment parameter Note: The External Algorithm Specification parameter is part of FS11, but in IOCA is no longer required for JPEG compression, as described in Note 2 in the description of the Image Encoding parameter. Thus, an FS11 receiver can ignore the External Algorithm Specification parameter if desired.
The self-defining fields and values acceptable for FS11 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Image Size ID (1) X'94' LENGTH (1) X'09' UNITBASE (1) X'00' – X'02'
HRESOL (2) X'0000' – X'7FFF' VRESOL (2) X'0000' – X'7FFF' HSIZE (2) X'0000' – X'7FFF' VSIZE (2) X'0000' – X'7FFF' Function Set 11


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'0A', X'82', X'83' X'01' IBM MMR-Modified Modified Read (see Note 1)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see Note 1)
X'0A' Concatenated ABIC (see Note 2)
X'82' G4 MMR-Modified Modified READ (see Note 1)
X'83' JPEG algorithms (see Note 3)
RECID (1) X'01' RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01', X'04', X'08',
X'18' X'01' 1 bit/IDE X'04' 4 bits/IDE X'08' 8 bits/IDE X'18' 24 bits/IDE External Algorithm Specification ID (1) X'9F'
LENGTH (1) X'0A' ALGTYPE (1) X'10' Compression algorithm specification RESERVED (1) X'00' Should be zero COMPRID (1) X'83' JPEG algorithms RESERVED (3) X'000000' Should be zero MARKER (1) X'C0' – X'C2', X'C9' – X'CA' Non-differential Huffman coding:
X'C0' Baseline DCT X'C1' Extended sequential DCT X'C2' Progressive DCT Non-differential arithmetic coding:
X'C9' Extended sequential DCT X'CA' Progressive DCT See Note 3 RESERVED (3) X'000000' Should be zero Notes on the initial parameters:
1. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit/IDE, otherwise exception condition EC-9611 is raised.
2. Concatenated ABIC is applicable only to images whose IDE size is 4 or 8 bits/IDE, otherwise exception condition EC-9611 is raised.
3. The JPEG baseline DCT-based algorithm is applicable only to images whose IDE size is 8 bits/IDE, while the other DCT-based algorithms are applicable only to images whose IDE size is 8 or 24 bits/IDE; otherwise exception condition EC-9611 is raised.
Function Set 11


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=1:
Retired RESERVED (3) X'970100' Retired Image LUT-ID parameter Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'01' 1 bit/IDE IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004' ID (1) X'01' Sampling ratios LENGTH (1) X'02' HSAMPLE (1) X'01' VSAMPLE (1) X'01' Function Set 11


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=4 or IDESZ=8 Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'04', X'08' X'04' 4 bits/IDE X'08' 8 bits/IDE IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' – B'1' B'0' No gray coding B'1' Gray coding (see Note 1)
RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb (see Note 2)
X'12' YCbCr (see Note 2)
RESERVED (3) X'000000' Should be zero SIZE1 (1) X'04', X'08' X'04' 4 bits/IDE X'08' 8 bits/IDE SIZE2 (1) X'00' 0 bits/IDE SIZE3 (1) X'00' 0 bits/IDE Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004' ID (1) X'01' Sampling ratios LENGTH (1) X'02' HSAMPLE (1) X'01' VSAMPLE (1) X'01' Notes on parameters used when IDESZ=4 or IDESZ=8:
1. Gray coding is valid only for the Concatenated ABIC algorithm, otherwise exception condition EC-9B10 is raised.
2. Grayscale images only. Grayscale IDEs are composed of the Y component only of the YCrCb or YCbCr color model.
Function Set 11


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=24:
Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'18' 24 bits/IDE or:
Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'04' BCOUNT (1) X'03' 3 bands: R,G,B or Y ,Cr,Cb or Y ,Cb,Cr BITCNT (1) X'08' 8 bits/IDE for R or Y band BITCNT (1) X'08' 8 bits/IDE for G or Cr or Cb band BITCNT (1) X'08' 8 bits/IDE for B or Cb or Cr band IDE Structure ID (1) X'9B' LENGTH (1) X'08'
FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'01', X'02', X'12' X'01' RGB X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'08' 8 bits of the IDE for the R or Y component
SIZE2 (1) X'08' 8 bits of the IDE for the G or Cr or Cb component SIZE3 (1) X'08' 8 bits of the IDE for the B or Cb or Cr component Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004', X'0006', X'0008' ID (1) X'01' Sampling ratios LENGTH (1) X'02', X'04', X'06' HSAMPLE1 (1) X'01' – X'02' X'02' YCrCb and YCbCr color models only VSAMPLE1 (1) X'01' HSAMPLE2 (1) X'01'
VSAMPLE2 (1) X'01' HSAMPLE3 (1) X'01' VSAMPLE3 (1) X'01' Function Set 11


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Final parameters:
Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs or:
## Band Image Data
(BCOUNT=1 only)
ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'01' One band RESERVED (2) X'0000' Should be zero DATA Any IDEs or:
## Band Image Data
(BCOUNT=3 only)
ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'01' Band containing the R or Y component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any R or Y component of the IDEs ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF'
BANDNUM (1) X'02' Band containing the G or Cr or Cb component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any G or Cr or Cb component of the IDEs ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'03' Band containing the B or Cb or Cr component of the IDEs
RESERVED (2) X'0000' Should be zero DATA Any B or Cb or Cr component of the IDEs End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' General note: In this function set, the Image Subsampling parameter and the Band Image parameter cannot coexist within the same Image Content; otherwise exception condition EC-9801 or EC-CE01 is raised.
Function Set 11


IOCA Function Set 14 (IOCA FS14)
Function Set 14 is a superset of Function Set 10 and Function Set 11, and describes bilevel, grayscale, and color images that allow use of transparency masks, as well as some additional compression algorithm options.
This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS14 are defined as follows:
Table 9. Function Set 14 Structure X'70' Begin Segment parameter X'91' Begin Image Content parameter + X'94' Image Size parameter + [ X'95' Image Encoding parameter ] + [ X'96' IDE Size parameter ] + [ X'97' Retired (Image LUT-ID parameter) (ignored) ] + [ X'98' Band Image parameter ]
+ [ X'9B' IDE Structure parameter ] + [ X'9F' External Algorithm Specification parameter (ignored) ] + [ X'FECE' Image Subsampling parameter ] [ Transparency Mask ] Image Data or Band Image Data (S)
X'93' End Image Content parameter X'71' End Segment parameter Table 10. Transparency Mask Structure X'8E' Begin Transparency Mask parameter X'94' Image Size parameter [ X'95' Image Encoding parameter ] X'FE92' Image Data X'8F' End Transparency Mask parameter
Notes:
1. The Image LUT-ID parameter has been retired and is thus not used in FS14. However, to keep FS14 a superset of FS11, the parameter will be allowed, but ignored.
2. The External Algorithm Specification parameter is not needed in FS14, as there are no restrictions on which codings can be used in the JPEG compression. The parameter is thus allowed, but ignored, as in FS45.
Function Set 14


The self-defining fields and values acceptable for FS14 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Image Size ID (1) X'94' LENGTH (1) X'09' UNITBASE (1) X'00' – X'02'
HRESOL (2) X'0000' – X'7FFF' VRESOL (2) X'0000' – X'7FFF' HSIZE (2) X'0000' – X'7FFF' VSIZE (2) X'0000' – X'7FFF' Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'0A', X'0D', X'0E',
X'82', X'83' X'01' IBM MMR-Modified Modified Read (see Note 1)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see Note 1)
X'0A' Concatenated ABIC (see Note 2)
X'0D' TIFF LZW X'0E' TIFF LZW with Differencing Predictor X'82' G4 MMR-Modified Modified READ (see Note 1)
X'83' JPEG algorithms (see Note 3)
RECID (1) X'01' RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01', X'04', X'08', X'18' X'01' 1 bit/IDE X'04' 4 bits/IDE X'08' 8 bits/IDE
X'18' 24 bits/IDE Notes on the initial parameters:
1. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit/IDE, otherwise exception condition EC-9611 is raised.
2. Concatenated ABIC is applicable only to images whose IDE size is 4 or 8 bits/IDE, otherwise exception condition EC-9611 is raised.
3. The JPEG algorithms are applicable only to images whose IDE size is 8 or 24 bits/IDE; otherwise exception condition EC-9611 is raised.
Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=1:
Retired RESERVED (3) X'970100' Retired Image LUT-ID parameter Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'01' 1 bit/IDE IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004' ID (1) X'01' Sampling ratios LENGTH (1) X'02' HSAMPLE (1) X'01' VSAMPLE (1) X'01' Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=4 or IDESZ=8 Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'04', X'08' X'04' 4 bits/IDE X'08' 8 bits/IDE IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' – B'1' B'0' No gray coding B'1' Gray coding (see Note 1)
RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb (see Note 2)
X'12' YCbCr (see Note 2)
RESERVED (3) X'000000' Should be zero SIZE1 (1) X'04', X'08' X'04' 4 bits/IDE X'08' 8 bits/IDE SIZE2 (1) X'00' 0 bits/IDE SIZE3 (1) X'00' 0 bits/IDE Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004' ID (1) X'01' Sampling ratios LENGTH (1) X'02' HSAMPLE (1) X'01' VSAMPLE (1) X'01' Notes on parameters used when IDESZ=4 or IDESZ=8:
1. Gray coding is valid only for the Concatenated ABIC algorithm, otherwise exception condition EC-9B10 is raised.
2. Grayscale images only. Grayscale IDEs are composed of the Y component only of the YCrCb or YCbCr color model.
Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=24:
Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'18' 24 bits/IDE or:
Band Image (see General Note at the end of the table)
ID (1) X'98' LENGTH (1) X'04' BCOUNT (1) X'03' 3 bands: R,G,B or Y ,Cr,Cb or Y ,Cb,Cr BITCNT (1) X'08' 8 bits/IDE for R or Y band BITCNT (1) X'08' 8 bits/IDE for G or Cr or Cb band BITCNT (1) X'08' 8 bits/IDE for B or Cb or Cr band IDE Structure ID (1) X'9B' LENGTH (1) X'08'
FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'01', X'02', X'12' X'01' RGB X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'08' 8 bits of the IDE for the R or Y component
SIZE2 (1) X'08' 8 bits of the IDE for the G or Cr or Cb component SIZE3 (1) X'08' 8 bits of the IDE for the B or Cb or Cr component Image Subsampling (see General Note at the end of the table)
ID (2) X'FECE' LENGTH (2) X'0000', X'0004', X'0006', X'0008' ID (1) X'01' Sampling ratios LENGTH (1) X'02', X'04', X'06' HSAMPLE1 (1) X'01' – X'02' X'02' YCrCb and YCbCr color models only VSAMPLE1 (1) X'01' HSAMPLE2 (1) X'01'
VSAMPLE2 (1) X'01' HSAMPLE3 (1) X'01' VSAMPLE3 (1) X'01' Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Final parameters:
Begin Transparency Mask ID (1) X'8E' LENGTH (1) X'00' Image Size ID (1) X'94' LENGTH (1) X'09' UNITBASE (1) X'00' – X'01' X'00' 10 inches X'01' 10 centimeters HRESOL (2) X'0001' – X'7FFF' VRESOL (2) X'0001' – X'7FFF'
HSIZE (2) X'0001' – X'7FFF' VSIZE (2) X'0001' – X'7FFF' Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression
X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00', X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Image Data ID (2) X'FE92'
LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs (bilevel only)
End Transparency Mask ID (1) X'8F' LENGTH (1) X'00' Function Set 14


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs or:
## Band Image Data
(BCOUNT=1 only)
ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'01' One band RESERVED (2) X'0000' Should be zero DATA Any IDEs or:
## Band Image Data
(BCOUNT=3 only)
ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'01' Band containing the R or Y component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any R or Y component of the IDEs ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF'
BANDNUM (1) X'02' Band containing the G or Cr or Cb component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any G or Cr or Cb component of the IDEs ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'03' Band containing the B or Cb or Cr component of the IDEs
RESERVED (2) X'0000' Should be zero DATA Any B or Cb or Cr component of the IDEs End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' General note: In this function set, the Image Subsampling parameter and the Band Image parameter cannot coexist within the same Image Content; otherwise exception condition EC-9801 or EC-CE01 is raised.
Function Set 14


IOCA Function Set 40 (IOCA FS40)
Function Set 40 is a subset of Function Set 42, Function Set 45, and Function Set 48. It describes tiled images with one bit per spot (color space YCbCr or YCrCb, IDESZ=1). This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS40 are defined as follows:
Table 11. Function Set 40 Structure X'70' Begin Segment parameter X'91' Begin Image Content parameter X'FEBB' Tile TOC parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'9B' IDE Structure parameter ] [ Tiles (S) ]
X'93' End Image Content parameter X'71' End Segment parameter Table 12. Tile Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ]
[ X'9B' IDE Structure parameter ] [ X'FE92' Image Data (S) ] X'8D' End Tile parameter Notes:
1. Note that the parameters in the above diagram must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS40 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised.
2. In the context of FS40, the Image Size parameter, Image Subsampling parameter, and External Algorithm Specification parameter cause the EC-0001 exception (Invalid parameter) to occur. If the first parameter after the Begin Image Content parameter is not the Tile TOC parameter, the image is not a tiled image and any of the tile-specific parameters (Tile TOC parameter, Begin Tile parameter, etc.) cause EC-0001 to occur.
3. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. The specification within a tile takes precedence over a specification outside of the tile.
4. If the IDE Size parameter is not present, the default IDE size is one bit per pel (bilevel image).
5. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero.
Function Set 40


The self-defining fields and values acceptable for FS40 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in Function Set 40:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Tile TOC ID (2) X'FEBB' LENGTH (2) X'0002' – X'7FFF' RESERVED (2) X'0000' Reserved; should be set to zero
Either zero repeating groups, or one per tile in the following format:
XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' – X'7FFFFFFF' Vertical tile origin THSIZE (4) X'00000000' – X'7FFFFFFF'
Horizontal tile size TVSIZE (4) X'00000000' – X'7FFFFFFF' Vertical tile size RELRES (1) X'01' Relative resolution COMPR (1) Compression algorithm DATAPOS (8) File offset to the beginning of the tile Image Encoding ID (1) X'95'
LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read (see General Note)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see General Note)
X'82' G4 MMR-Modified Modified READ (see General Note)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'01' 1 bit/IDE Function Set 40


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in a tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Tile Size ID (1) X'B6' LENGTH (1) X'08' – X'09' THSIZE (4) X'00000000' – X'7FFFFFFF' Horizontal tile size TVSIZE (4) X'00000000' –
X'7FFFFFFF' Vertical tile size RELRES (1) X'01' Relative resolution Tile parameters:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read (see General Note)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ (see General Note)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'01' 1 bit/IDE Function Set 40


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs End Tile ID (1) X'8D' LENGTH (1) X'00' Final parameters in Function Set 40:
End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' General note: ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit per band, otherwise exception condition EC-9611 is raised.
Function Set 40


IOCA Function Set 42 (IOCA FS42)
Function Set 42 is a superset of Function Set 40 and a subset of Function Set 45 and Function Set 48. It describes tiled images with one bit per spot. Images can be either bilevel (color space YCbCr or YCrCb, IDESZ=1) or color (color space CMYK, IDESZ=4). This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS42 are defined as follows:
Table 13. Function Set 42 Structure X'70' Begin Segment parameter X'91' Begin Image Content parameter X'FEBB' Tile TOC parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ]
[ Tiles (S) ] X'93' End Image Content parameter X'71' End Segment parameter Table 14. Tile Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ]
[ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ] [ X'B7' Tile Set Color parameter ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter Notes:
1. Note that the parameters in T able 13and T able 14must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS42 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised.
2. In the context of FS42, the Image Size parameter, Image Subsampling parameter, and External Algorithm Specification parameter cause the EC-0001 exception (Invalid parameter) to occur. If the first parameter after the Begin Image Content parameter is not the Tile TOC parameter, the image is not a tiled image and any of the tile-specific parameters (Tile TOC parameter, Begin Tile parameter, etc.) cause EC-0001 to occur.
3. If the IDE Size is not set to 1 bit or the color space is not YCbCr or YCrCb for a tile, and the Tile Set Color parameter is specified, exception EC-B711 occurs.
4. If the Solid Fill Rectangle compression algorithm is specified for a tile and Image Data or Band Image Data is encountered, exception EC-0001 occurs.
5. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. The specification within a tile takes precedence over a specification outside of the tile.
6. If the IDE Size parameter is not present, the default IDE size is one bit per pel (bilevel image).
Function Set 42


7. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero.
8. If a tile contains the IDE Structure parameter specifying the CMYK color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present.
9. If the IDE Structure parameter specifying the CMYK color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying that the tile is bilevel.
10. CMYK tiles must carry the image data in Band Image Data. Bilevel tiles must carry the data in Image Data, unless the Solid Fill Rectangle compression algorithm is specified.
11. If a tile has Solid Fill Rectangle specified as the compression algorithm, the tile is painted using the color specified in the Tile Set Color parameter for that tile. If the Tile Set Color parameter has not been specified, the color given using the Set Bilevel Image Color field in the Image Data Descriptor is used. If the Set Bilevel Image Color field is missing, the device default is used.
The self-defining fields and values acceptable for FS42 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in Function Set 42:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Tile TOC ID (2) X'FEBB' LENGTH (2) X'0002' – X'7FFF' RESERVED (2) X'0000' Reserved; should be set to zero
Either zero repeating groups, or one per tile in the following format:
XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' – X'7FFFFFFF' Vertical tile origin THSIZE (4) X'00000000' – X'7FFFFFFF'
Horizontal tile size TVSIZE (4) X'00000000' – X'7FFFFFFF' Vertical tile size RELRES (1) X'01' Relative resolution COMPR (1) Compression algorithm DATAPOS (8) File offset to the beginning of the tile Function Set 42


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'20', X'82' X'01' IBM MMR-Modified Modified Read (see General Note)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see General Note)
X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ (see General Note)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'01', X'04' X'01' 1 bit/IDE X'04' 4 bits/IDE Initial parameters in a tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Tile Size ID (1) X'B6' LENGTH (1) X'08' – X'09' THSIZE (4) X'00000000' – X'7FFFFFFF' Horizontal tile size TVSIZE (4) X'00000000' –
X'7FFFFFFF' Vertical tile size RELRES (1) X'01' Relative resolution Function Set 42


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Tile parameters used when IDESZ=1:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'20', X'82' X'01' IBM MMR-Modified Modified Read (see General Note)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ (see General Note)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'01' 1 bit/IDE Band Image ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'01' 1 bit/IDE IDE Structure ID (1) X'9B' LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Function Set 42


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Tile Set Color ID (1) X'B7' LENGTH (1) X'0B', X'0C' CSPACE (1) X'04', X'08' X'04' CMYK X'08' CIELab RESERVED (3) X'000000' Should be zero SIZE1–SIZE3 (1)
X'08' Bits/IDE for components 1-3 SIZE4 (1) X'00', X'08' Bits/IDE for component 4 CVAL1–CVAL4 (1)
X'00' – X'FF' Color values Function Set 42


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Tile parameters used when IDESZ=4:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read (see General Note)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ (see General Note)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00', X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'04' 4 bits/IDE Band Image ID (1) X'98' LENGTH (1) X'05' BCOUNT (1) X'04' Four bands: CMYK BITCNT (1) X'01' 1 bit/IDE for C band BITCNT (1) X'01' 1 bit/IDE for M band BITCNT (1) X'01' 1 bit/IDE for Y band BITCNT (1) X'01' 1 bit/IDE for K band
IDE Structure ID (1) X'9B' LENGTH (1) X'09' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'04' CMYK RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE (C component)
SIZE2 (1) X'01' 1 bit/IDE (M component)
SIZE3 (1) X'01' 1 bit/IDE (Y component)
SIZE4 (1) X'01' 1 bit/IDE (K component)
Function Set 42


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Final parameters in a tile:
Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs or:
## Band Image Data
(BCOUNT=4 only)
Four bands, in order by BANDNUM, in the following format:
ID (2) X'FE9C' LENGTH (2) X'0004' – X'FFFF' BANDNUM (1) X'01' – X'04' X'01' Band contains the C component of the IDEs X'02' Band contains the M component of the IDEs X'03' Band contains the Y component of the IDEs
X'04' Band contains the K component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any End Tile ID (1) X'8D' LENGTH (1) X'00' Final parameters in Function Set 42:
End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' General note: ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit per band, otherwise exception condition EC- 9611 is raised.
Function Set 42


IOCA Function Set 45 (IOCA FS45)
Function Set 45 is a superset of Function Set 40 and Function Set 42 and a subset of Function Set 48. It describes bilevel or color tiled images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS45 are now defined as follows:
Table 15. Function Set 45 Structure X'70' Begin Segment parameter Image Content (S)
X'71' End Segment parameter Table 16. Image Content Structure X'91' Begin Image Content parameter X'FEBB' Tile TOC parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ]
[ Data and Referencing Tiles (S) ] X'93' End Image Content parameter Table 17. Data Tile Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ]
[ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ] [ X'9F' External Algorithm Specification parameter (ignored) ] [ X'B7' Tile Set Color parameter ] [ Transparency Mask ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter Table 18. Referencing Tile Structure
X'8C' Begin Tile parameter X'B5' Tile Position parameter X'FEB8' Include Tile parameter [ Transparency Mask ] X'8D' End Tile parameter Function Set 45


Table 19. IOCA Tile Resource Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ]
[ X'9F' External Algorithm Specification parameter (ignored) ] [ X'B7' Tile Set Color parameter ] [ Transparency Mask ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter Table 20. Transparency Mask Structure X'8E' Begin Transparency Mask parameter X'94' Image Size parameter
[ X'95' Image Encoding parameter ] X'FE92' Image Data X'8F' End Transparency Mask parameter Notes:
1. Note that the parameters in T able 15,T able 16,T able 17,T able 18, T able 19, andT able 20must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS45 specification is more restrictive.
If the parameters are given in a different order, an out-of-sequence exception is raised.
2. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. Note that tile data might require that some of these parameters be specified.
3. If the IDE Size parameter is not present neither in the tile nor in the image content, the default IDE size is one bit per pel (bilevel image).
4. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero.
5. If a tile contains the IDE Structure parameter specifying the CMYK color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present.
6. If the IDE Structure parameter specifying the CMYK color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying a different color space.
7. Receivers implementing FS45 must support at least 128 image contents in a single image segment.
Otherwise, if a receiver encounters too many image contents to process, it should act as if it encountered too many image objects on a page.
8. Resource tiles included via the Include Tile parameter must not contain the Include Tile parameter or the exception EC-B811 exists.
Function Set 45


The self-defining fields and parameter values that are acceptable in Function Set 45 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in Function Set 45:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Tile TOC ID (2) X'FEBB' LENGTH (2) X'0002' – X'7FFF' RESERVED (2) X'0000' Reserved; should be set to zero
Either zero repeating groups, or one per tile in the following format:
XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' – X'7FFFFFFF' Vertical tile origin THSIZE (4) X'00000000' – X'7FFFFFFF'
Horizontal tile size TVSIZE (4) X'00000000' – X'7FFFFFFF' Vertical tile size RELRES (1) X'01' – X'02' Relative resolution (see Note 1)
COMPR (1) Compression algorithm DATAPOS (8) File offset to the beginning of the tile Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'0D', X'20', X'82' – X'83' X'01' IBM MMR-Modified Modified Read
(see Note 2)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see Note 2)
X'0D' TIFF LZW X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ (see Note 2)
X'83' JPEG algorithms (see Note 3)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01', X'04', X'20' X'01' 1 bit/IDE X'04' 4 bits/IDE X'20' 32 bits/IDE Notes on the initial parameters:
1. In the Tile TOC parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data.
Using RELRES of 1 for JPEG-compressed data and RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general.
Implementation Note: Some FS45 receivers support a RELRES of 1 for JPEG-compressed data, and do not raise exception EC-B610 for such data. Also note that in FS48, a RELRES of 1 for JPEG-compressed data is allowed.
2. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit/band, otherwise exception condition EC-9611 is raised.
3. The JPEG algorithms are applicable only to images whose IDE size is 32 bits/IDE; otherwise exception condition EC-9611 is raised.
Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in a data tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Tile Size ID (1) X'B6' LENGTH (1) X'08' – X'09' THSIZE (4) X'00000000' – X'7FFFFFFF' Horizontal tile size TVSIZE (4) X'00000000' –
X'7FFFFFFF' Vertical tile size RELRES (1) X'01' – X'02' Relative resolution (see Note on the data-tile initial parameters)
Note on the data-tile initial parameters: In the Tile Size parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data. Using RELRES of 1 for JPEG-compressed data and RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general.
Implementation Note: Some FS45 receivers support a RELRES of 1 for JPEG-compressed data, and do not raise exception EC-B610 for such data. Also note that in FS48, a RELRES of 1 for JPEG-compressed data is allowed.
Initial parameters in a referencing tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Include Tile ID (2) X'FEB8' LENGTH (2) X'0004' TIRID (4) X'00000000' – X'FFFFFFFF' Resource Tile local identifier Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=1:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'20', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left
IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01' 1 bit/IDE Band Image ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'01' 1bit/IDE IDE Structure ID (1) X'9B'
LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'00000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Tile Set Color ID (1) X'B7' LENGTH (1) X'0B', X'0C' CSPACE (1) X'04', X'08' X'04' CMYK X'08' CIELab RESERVED (3) X'000000' Should be zero SIZE1–SIZE3 (1)
X'08' Bits/IDE for components 1-3 SIZE4 (1) X'00', X'08' Bits/IDE for component 4 CVAL1–CVAL4 (1)
X'00' – X'FF' Color values Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=4:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96'
LENGTH (1) X'01' IDESZ (1) X'04' 4 bits/IDE Band Image ID (1) X'98' LENGTH (1) X'05' BCOUNT (1) X'04' Four bands: CMYK BITCNT (1) X'01' 1 bit/IDE for C band BITCNT (1) X'01' 1 bit/IDE for M band BITCNT (1) X'01' 1 bit/IDE for Y band
BITCNT (1) X'01' 1 bit/IDE for K band IDE Structure ID (1) X'9B' LENGTH (1) X'09' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'04' CMYK RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE (C component)
SIZE2 (1) X'01' 1 bit/IDE (M component)
SIZE3 (1) X'01' 1 bit/IDE (Y component)
SIZE4 (1) X'01' 1 bit/IDE (K component)
Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=32:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'03', X'0D', X'83' X'03' No Compression X'0D' TIFF LZW X'83' JPEG algorithms RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is
from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'20' 32 bits/IDE Band Image ID (1) X'98' LENGTH (1) X'05'
BCOUNT (1) X'04' 4 bands: CMYK BITCNT (1) X'08' 8 bits/IDE for C band BITCNT (1) X'08' 8 bits/IDE for M band BITCNT (1) X'08' 8 bits/IDE for Y band BITCNT (1) X'08' 8 bits/IDE for K band IDE Structure ID (1) X'9B' LENGTH (1) X'09' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'04' CMYK RESERVED (3) X'000000' Should be zero SIZE1 (1) X'08' 8 bits/IDE (C component)
SIZE2 (1) X'08' 8 bits/IDE (M component)
SIZE3 (1) X'08' 8 bits/IDE (Y component)
SIZE4 (1) X'08' 8 bits/IDE (K component)
Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Final parameters in a tile:
Begin Transparency Mask ID (1) X'8E' LENGTH (1) X'00' Image Size ID (1) X'94' LENGTH (1) X'09' UNITBASE (1) X'00' – X'01' X'00' 10 inches X'01' 10 centimeters HRESOL (2) X'0001' – X'7FFF' VRESOL (2) X'0001' – X'7FFF'
HSIZE (2) X'0001' – X'7FFF' VSIZE (2) X'0001' – X'7FFF' Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression
X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00', X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Image Data ID (2) X'FE92'
LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs (bilevel only)
End Transparency Mask ID (1) X'8F' LENGTH (1) X'00' Function Set 45


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs or:
## Band Image Data
(BCOUNT=4 only)
Four bands, in order by BANDNUM, in the following format:
ID (2) X'FE9C' LENGTH (2) X'0003' – X'FFFF' (see Note on the tile-final parameters)
BANDNUM (1) X'01' – X'04' X'01' Band contains the C component of the IDEs X'02' Band contains the M component of the IDEs X'03' Band contains the Y component of the IDEs X'04' Band contains the K component of the IDEs
RESERVED (2) X'0000' Should be zero DATA Any End Tile ID (1) X'8D' LENGTH (1) X'00' Note on the tile-final parameters: Band Image Data parameters with length of X'0003' do not have a data field. The receiver generates zeros for the corresponding band.
Final parameters in Function Set 45:
End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' Function Set 45


IOCA Function Set 48 (IOCA FS48)
Function Set 48 is a superset of Function Set 40, Function Set 42, and Function Set 45. It describes bilevel or color tiled images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS48 are defined as follows:
Table 21. Function Set 48 Structure X'70' Begin Segment parameter Image Content (S)
X'71' End Segment parameter Table 22. Image Content Structure X'91' Begin Image Content parameter X'FEBB' Tile TOC parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ]
[ Data and Referencing Tiles (S) ] X'93' End Image Content parameter Table 23. Data Tile Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ]
[ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ] [ X'9F' External Algorithm Specification parameter (ignored) ] [ X'B7' Tile Set Color parameter ] [ Transparency Mask ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter Table 24. Referencing Tile Structure
X'8C' Begin Tile parameter X'B5' Tile Position parameter X'FEB8' Include Tile parameter [ Transparency Mask ] X'8D' End Tile parameter Function Set 48


Table 25. IOCA Tile Resource Structure X'8C' Begin Tile parameter X'B5' Tile Position parameter X'B6' Tile Size parameter [ X'95' Image Encoding parameter ] [ X'96' IDE Size parameter ] [ X'98' Band Image parameter ] [ X'9B' IDE Structure parameter ]
[ X'9F' External Algorithm Specification parameter (ignored) ] [ X'B7' Tile Set Color parameter ] [ Transparency Mask ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter Table 26. Transparency Mask Structure X'8E' Begin Transparency Mask parameter X'94' Image Size parameter
[ X'95' Image Encoding parameter ] X'FE92' Image Data X'8F' End Transparency Mask parameter Notes:
1. Note that the parameters in T able 21, T able 22, T able 23, T able 24, T able 25, andT able 26must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS48 specification is more restrictive.
If the parameters are given in a different order, an out-of-sequence exception is raised.
2. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. Note that tile data might require that some of these parameters be specified.
3. If the IDE Size parameter is not present neither in the tile nor in the image content, the default IDE size is one bit per pel (bilevel image).
4. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero.
5. If a tile contains the IDE Structure parameter specifying the CMYK or nColor color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present.
6. If the IDE Structure parameter specifying the CMYK or nColor color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying a different color space.
7. Receivers implementing FS48 must support at least 128 image contents in a single image segment.
Otherwise, if a receiver encounters too many image contents to process, it should act as if it encountered too many image objects on a page.
8. Resource tiles included via the Include Tile parameter must not contain the Include Tile parameter or the exception EC-B811 exists.
9. Implementations of FS48 are very strongly recommended to also support the nColor Names parameter.
Support of this parameter is not required in FS48 because FS48 became an official part of IOCA before the introduction of the nColor Names parameter; if the parameter had existed at the time FS48 was added, its support would have been made part of FS48.
Function Set 48


The nColor Names parameter is optional, but when specified with an FS48 image, must immediately follow the IDE Structure parameter in T able 22, T able 23, and T able 25.
The effective syntax in those tables, then, would be:
...
[ X'9B' IDE Structure parameter ] [ X'FEB3' nColor Names parameter ] ...
Function Set 48


The self-defining fields and parameter values that are acceptable in Function Set 48 are shown in the following table.
IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in Function Set 48:
Begin Segment ID (1) X'70' LENGTH (1) X'00' Begin Image Content ID (1) X'91' LENGTH (1) X'01' OBJTYPE (1) X'FF' IOCA Tile TOC ID (2) X'FEBB' LENGTH (2) X'0002' – X'7FFF' RESERVED (2) X'0000' Reserved; should be set to zero
Either zero repeating groups, or one per tile in the following format:
XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' – X'7FFFFFFF' Vertical tile origin THSIZE (4) X'00000000' – X'7FFFFFFF'
Horizontal tile size TVSIZE (4) X'00000000' – X'7FFFFFFF' Vertical tile size RELRES (1) X'01' – X'02' Relative resolution (see Note 1)
COMPR (1) Compression algorithm DATAPOS (8) File offset to the beginning of the tile Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'0D', X'0E', X'20', X'82' – X'83' X'01' IBM MMR-Modified Modified Read
(see Note 2)
X'03' No Compression X'08' ABIC (Bilevel Q-Coder) (see Note 2)
X'0D' TIFF LZW X'0E' TIFF LZW with Differencing Predictor X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ (see Note 2)
X'83' JPEG algorithms (see Note 3)
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01', X'04', X'10', X'18', X'20', X'28', X'30', X'38', X'40', X'48', X'50', X'58', X'60', X'68', X'70',
X'78' X'01' 1 bit/IDE X'04' 4 bits/IDE X'10' 16 bits/IDE X'18' 24 bits/IDE X'20' 32 bits/IDE X'28' 40 bits/IDE X'30' 48 bits/IDE
X'38' 56 bits/IDE X'40' 64 bits/IDE X'48' 72 bits/IDE X'50' 80 bits/IDE X'58' 88 bits/IDE X'60' 96 bits/IDE X'68' 104 bits/IDE X'70' 112 bits/IDE
X'78' 120 bits/IDE Notes on the initial parameters:
1. In the Tile TOC parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data.
Using a RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general. For JPEG-compressed data, either value of RELRES is supported.
2. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit/band, otherwise exception condition EC-9611 is raised.
3. The JPEG algorithms are applicable only to CMYK images whose IDE size is 32 bits/IDE or to nColor images;
otherwise exception condition EC-9611 is raised.
Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Initial parameters in a data tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Tile Size ID (1) X'B6' LENGTH (1) X'08' – X'09' THSIZE (4) X'00000000' – X'7FFFFFFF' Horizontal tile size TVSIZE (4) X'00000000' –
X'7FFFFFFF' Vertical tile size RELRES (1) X'01' – X'02' Relative resolution (see Note on the data-tile initial parameters)
Note on the data-tile initial parameters: In the Tile Size parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data. Using a RELRES of 2 for non-JPEG data results in exception EC-B610 being raised.
Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general. For JPEG-compressed data, either value of RELRES is supported.
Initial parameters in a referencing tile:
Begin Tile ID (1) X'8C' LENGTH (1) X'00' Tile Position ID (1) X'B5' LENGTH (1) X'08' XOFFSET (4) X'00000000' – X'7FFFFFFF' Horizontal tile origin YOFFSET (4) X'00000000' –
X'7FFFFFFF' Vertical tile origin Include Tile ID (2) X'FEB8' LENGTH (2) X'0004' TIRID (4) X'00000000' – X'FFFFFFFF' Resource Tile local identifier Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=1:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'20', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'20' Solid Fill Rectangle X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left
IDE Size ID (1) X'96' LENGTH (1) X'01' IDESZ (1) X'01' 1 bit/IDE Band Image ID (1) X'98' LENGTH (1) X'02' BCOUNT (1) X'01' One band BITCNT (1) X'01' 1bit/IDE IDE Structure ID (1) X'9B'
LENGTH (1) X'06' – X'08' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'00000' Should be zero FORMAT (1) X'02', X'12' X'02' YCrCb X'12' YCbCr RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE SIZE2 (1) X'00' 0 bits/IDE
SIZE3 (1) X'00' 0 bits/IDE Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Tile Set Color ID (1) X'B7' LENGTH (1) X'0B', X'0C' CSPACE (1) X'04', X'08' X'04' CMYK X'08' CIELab RESERVED (3) X'000000' Should be zero SIZE1–SIZE3 (1)
X'08' Bits/IDE for components 1-3 SIZE4 (1) X'00', X'08' Bits/IDE for component 4 CVAL1–CVAL4 (1)
X'00' – X'FF' Color values Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=4:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96'
LENGTH (1) X'01' IDESZ (1) X'04' 4 bits/IDE Band Image ID (1) X'98' LENGTH (1) X'05' BCOUNT (1) X'04' Four bands: CMYK BITCNT (1) X'01' 1 bit/IDE for C band BITCNT (1) X'01' 1 bit/IDE for M band BITCNT (1) X'01' 1 bit/IDE for Y band
BITCNT (1) X'01' 1 bit/IDE for K band IDE Structure ID (1) X'9B' LENGTH (1) X'09' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'04' CMYK RESERVED (3) X'000000' Should be zero SIZE1 (1) X'01' 1 bit/IDE (C component)
SIZE2 (1) X'01' 1 bit/IDE (M component)
SIZE3 (1) X'01' 1 bit/IDE (Y component)
SIZE4 (1) X'01' 1 bit/IDE (K component)
Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when IDESZ=32 and FORMAT=CMYK:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'03', X'0D', X'0E', X'83' X'03' No Compression X'0D' TIFF LZW X'0E' TIFF LZW with Differencing Predictor X'83' JPEG
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'20' 32 bits/IDE Band Image ID (1) X'98' LENGTH (1) X'05' BCOUNT (1) X'04' 4 bands: CMYK BITCNT (1) X'08' 8 bits/IDE for C band BITCNT (1) X'08' 8 bits/IDE for M band BITCNT (1) X'08' 8 bits/IDE for Y band BITCNT (1) X'08' 8 bits/IDE for K band
IDE Structure ID (1) X'9B' LENGTH (1) X'09' FLAGS (1)
ASFLAG B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'04' CMYK RESERVED (3) X'000000' Should be zero SIZE1 (1) X'08' 8 bits/IDE (C component)
SIZE2 (1) X'08' 8 bits/IDE (M component)
SIZE3 (1) X'08' 8 bits/IDE (Y component)
SIZE4 (1) X'08' 8 bits/IDE (K component)
Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Parameters used when FORMAT=nColor:
Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'03', X'0D', X'0E', X'83' X'03' No Compression X'0D' TIFF LZW X'0E' TIFF LZW with Differencing Predictor X'83' JPEG
RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00' – X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left IDE Size ID (1) X'96' LENGTH (1) X'01'
IDESZ (1) X'10', X'18', X'20', X'28', X'30', X'38', X'40', X'48', X'50', X'58', X'60', X'68', X'70', X'78' X'10' 16 bits/IDE (n=2)
X'18' 24 bits/IDE (n=3)
X'20' 32 bits/IDE (n=4)
X'28' 40 bits/IDE (n=5)
X'30' 48 bits/IDE (n=6)
X'38' 56 bits/IDE (n=7)
X'40' 64 bits/IDE (n=8)
X'48' 72 bits/IDE (n=9)
X'50' 80 bits/IDE (n=10)
X'58' 88 bits/IDE (n=11)
X'60' 96 bits/IDE (n=12)
X'68' 104 bits/IDE (n=13)
X'70' 112 bits/IDE (n=14)
X'78' 120 bits/IDE (n=15)
Band Image ID (1) X'98' LENGTH (1) X'03' – X'10' BCOUNT (1) X'02' – X'0F' 2–15 bands BITCNT (1) X'08' 8 bits/IDE for 1st band BITCNT (1) X'08' 8 bits/IDE for 2nd band ... ... ...
BITCNT (1) X'08' 8 bits/IDE for nth band Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments IDE Structure ID (1) X'9B' LENGTH (1) X'07' – X'14' FLAGS (1)
ASFLAG Ignored, should be B'0' Additive GRAYCODE B'0' No gray coding RESERVED B'000000' Should be zero FORMAT (1) X'8n' nColor (X'2' ≤ n ≤ X'F')
RESERVED (3) X'000000' Should be zero SIZE1 (1) X'08' 8 bits/IDE (1st component)
SIZE2 (1) X'08' 8 bits/IDE (2nd component)
... ... ...
SIZEn (1) X'08' 8 bits/IDE (nth component)
Note on the parameters used when FORMAT=nColor: When using FORMAT=nColor, generating implementations are very strongly recommended to also include an nColor Names parameter here, just after the IDE Structure parameter, and receiving implementations are very strongly recommended to support that parameter here. This allows an nColor FS48 IOCA image to specify the names of the colors being used. For more information on this recommendation, see Note 9.
Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments Final parameters in a tile:
Begin Transparency Mask ID (1) X'8E' LENGTH (1) X'00' Image Size ID (1) X'94' LENGTH (1) X'09' UNITBASE (1) X'00' – X'01' X'00' 10 inches X'01' 10 centimeters HRESOL (2) X'0001' – X'7FFF' VRESOL (2) X'0001' – X'7FFF'
HSIZE (2) X'0001' – X'7FFF' VSIZE (2) X'0001' – X'7FFF' Image Encoding ID (1) X'95' LENGTH (1) X'02' – X'03' COMPRID (1) X'01', X'03', X'08', X'82' X'01' IBM MMR-Modified Modified Read X'03' No Compression
X'08' ABIC (Bilevel Q-Coder)
X'82' G4 MMR-Modified Modified READ RECID (1) X'01', X'04' X'01' RIDIC X'04' Unpadded RIDIC BITORDR (1) X'00', X'01' X'00' Bit order within each image data byte is from left to right X'01' Bit order within each image data byte is from right to left Image Data ID (2) X'FE92'
LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs (bilevel only)
End Transparency Mask ID (1) X'8F' LENGTH (1) X'00' Function Set 48


IOCA Self-defining Field Parameter (Bytes)
Acceptable Value Comments
## Image Data
(IDESZ=1 only)
Unbanded image data, in the following format:
ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs (bilevel only)
or:
## Band Image Data
(FORMAT=CMYK only)
Four bands, in order by BANDNUM, in the following format:
ID (2) X'FE9C' LENGTH (2) X'0003' – X'FFFF' (see Note on the tile-final parameters)
BANDNUM (1) X'01' – X'04' X'01' Band contains the C component of the IDEs X'02' Band contains the M component of the IDEs X'03' Band contains the Y component of the IDEs X'04' Band contains the K component of the IDEs
RESERVED (2) X'0000' Should be zero DATA Any or:
## Band Image Data
(FORMAT=nColor only)
n bands, in order by BANDNUM, in the following format:
ID (2) X'FE9C' LENGTH (2) X'0003' – X'FFFF' (see Note on the tile-final parameters)
BANDNUM (1) X'01' – X'0F' X'0n' Band contains the nth color component of the IDEs RESERVED (2) X'0000' Should be zero DATA Any End Tile ID (1) X'8D' LENGTH (1) X'00' Note on the tile-final parameters: Band Image Data parameters with a length of X'0003' do not have a data field. The receiver generates zeros for the corresponding band.
Final parameters in Function Set 48:
End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' Function Set 48


