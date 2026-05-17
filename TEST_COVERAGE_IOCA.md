# Granular Test Coverage - IOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| IOCA-1-001 | AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. Where appropriate, AFP architectures use industry and international standards, such as the ITU-TSS (formerly known as CCITT) facsimile standards for compressed image data. | ❓ |
| IOCA-1-002 | Mixed Object Document Content Architecture (MO:DCA) | ❓ |
| IOCA-1-003 | Intelligent Printer Data Stream (IPDS) Architecture. | ❓ |
| IOCA-1-004 | Architecture Components | ❓ |
| IOCA-1-005 | Object Containers Other Objects Architecture Components | ❓ |
| IOCA-1-006 | Presentation Text Object Content Architecture (PTOCA): A data architecture for describing text objects that | ❓ |
| IOCA-1-007 | Image Object Content Architecture (IOCA): A data architecture for describing resolution-independent image | ❓ |
| IOCA-1-008 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA): A version of GOCA | ❓ |
| IOCA-1-009 | Bar Code Object Content Architecture (BCOCA): A data architecture for describing bar code objects, using a | ❓ |
| IOCA-1-010 | Font Object Content Architecture (FOCA): A resource architecture for describing the structure and content of | ❓ |
| IOCA-1-011 | Color Management Object Content Architecture (CMOCA): A resource architecture used to carry the color | ❓ |
| IOCA-1-012 | Metadata Object Content Architecture (MOCA): A resource architecture used to carry metadata in an AFP | ❓ |
| IOCA-1-013 | Architecture Components | ❓ |
| IOCA-1-014 | 205 Main Street Plains, Iowa Sales have improved so dramatically since you have joined our team, I would like to know your techniques. | ❓ |
| IOCA-1-015 | Jim D. Bolt Architecture Components | ❓ |
| IOCA-2-001 | The rationale for IOCA | ❓ |
| IOCA-2-002 | The scope of IOCA | ❓ |
| IOCA-2-003 | Less expensive computer storage and memory are making the handling of larger volumes of image data | ❓ |
| IOCA-2-004 | Faster processors and techniques such as bit slicing and hardware buffering are improving the efficiency and | ❓ |
| IOCA-2-005 | Higher-resolution image devices are improving the usability of images and image applications. Images can | ❓ |
| IOCA-2-006 | The Image Object Content Architecture (IOCA) has been formulated to provide a format suited for high speed printing. IOCA contains enough flexibility that a wide variety of images can be printed, but formats images in such a way that they can be printed efficiently and with minimal processing. | ❓ |
| IOCA-2-007 | Can be used for scanning, displaying, printing, archiving, and other I/O operations. | ❓ |
| IOCA-2-008 | Has an image description that is flexible enough to allow it to exist intact in interactive, printer, and | ❓ |
| IOCA-2-009 | Allows the image to be fully described in device- and process-independent terms. Each image object is | ❓ |
| IOCA-2-010 | Describes images using self-defining fields; that is, each field contains a description of itself along with its | ❓ |
| IOCA-2-011 | Figure 4. Images and IOCA IOCA Image Information T o a representation that is independent of environments What is IOCA? | ❓ |
| IOCA-2-012 | 1. Creation. An image is created by a program or an input device such as a scanner. The creation step is supported by many types of devices and technologies. The resulting image contains device-dependent information. | ❓ |
| IOCA-2-013 | 2. Pre-processing. Pre-processing is the gateway from the input devices. In this step, the device-dependent information is removed from the image. For example, if the image was created by scanning a document, the end-of-scan-line code is removed. After this step, the image, along with its characteristics of resolution and size, is ready to be processed. | ❓ |
| IOCA-2-014 | 3. Processing. The image is now processed into an interchangeable form with all device-dependent characteristics removed. In this form, it can be passed to another system or environment and interpreted consistently. | ❓ |
| IOCA-2-015 | 4. Post-processing. Post-processing is the gateway to applications that support output devices. The required device-control information is inserted. This step might be different for each type of device. | ❓ |
| IOCA-2-016 | 5. Output. This step presents the image to the user. It is controlled locally by the output device, such as a display or a printer. | ❓ |
| IOCA-2-017 | IOCA in Image Processing | ❓ |
| IOCA-3-001 | IOCA Representation of Images | ❓ |
| IOCA-3-002 | Image Points | ❓ |
| IOCA-3-003 | Size and Resolution | ❓ |
| IOCA-3-004 | Compression | ❓ |
| IOCA-3-005 | Image Coordinate System | ❓ |
| IOCA-3-006 | Image Presentation Space | ❓ |
| IOCA-3-007 | Image Tiling | ❓ |
| IOCA-3-008 | Function Sets | ❓ |
| IOCA-3-009 | Size (how large) | ❓ |
| IOCA-3-010 | Resolution (how sharp) | ❓ |
| IOCA-3-011 | Color (whether it is black-and-white, grayscale, or color) | ❓ |
| IOCA-3-012 | Recording and compression algorithms (how Image Data is encoded) | ❓ |
| IOCA-3-013 | Image Data layout | ❓ |
| IOCA-3-014 | The Image Segment, a set of self-defining fields, is passed to and from controlling environment, which determine how it is handled. That is, the Image Segment can be presented as a displayed or a printed image in an environment, or can be merged with text and graphics objects into a compound document. | ❓ |
| IOCA-3-015 | Figure 7. Image Concept and IOCA Representation Concept Image | ❓ |
| IOCA-3-016 | Image Characteristics Representation IOCA Representation | ❓ |
| IOCA-3-017 | For bilevel images, the image foreground consists of all those image points whose IDE values are B'1'. The | ❓ |
| IOCA-3-018 | For any other images, the entire image is considered to be foreground. The unoccupied areas in the image | ❓ |
| IOCA-3-019 | The size of an image is expressed in terms of the number of image points in the horizontal and vertical | ❓ |
| IOCA-3-020 | The resolution of an image determines its sharpness. It is expressed in terms of the number of image points | ❓ |
| IOCA-3-021 | 3 Inches Height: | ❓ |
| IOCA-3-022 | 5 Inches If the image is divided into 600 image points horizontally and 1500 image points vertically, the image is represented as: | ❓ |
| IOCA-3-023 | Sizes: | ❓ |
| IOCA-3-024 | 600 Horizontal 1500 Vertical | ❓ |
| IOCA-3-025 | Resolutions: | ❓ |
| IOCA-3-026 | 200 image points/inch Horizontal 300 image points/inch Vertical Size and Resolution | ❓ |
| IOCA-3-027 | Image Coordinate System | ❓ |
| IOCA-3-028 | Controlling Environment Image Presentation Space | ❓ |
| IOCA-3-029 | Longer text line 3 Still longer text line 4 Bigger space to some more text Image Tiling | ❓ |
| IOCA-3-030 | Chapter 7, “Compliance” defines several subsets of the architecture (called function sets) that satisfy some particular common needs. It is the responsibility of the application to determine which function set(s) it must provide to generate and receive IOCA Image Objects. | ❓ |
| IOCA-4-001 | The formats of the IOCA self-defining fields | ❓ |
| IOCA-4-002 | The code points used by IOCA | ❓ |
| IOCA-4-003 | Image Data (X'FE92') | ❓ |
| IOCA-4-004 | Band Image Data (X'FE9C') | ❓ |
| IOCA-4-005 | Include Tile parameter (X'FEB8') | ❓ |
| IOCA-4-006 | Tile TOC parameter (X'FEBB') | ❓ |
| IOCA-4-007 | Image Subsampling parameter (X'FECE') | ❓ |
| IOCA-4-008 | LL is the length of the parameters, excluding LL itself. | ❓ |
| IOCA-4-009 | Code Points Table 3 lists the codes used by IOCA, the names of the associated elements, and the formats used. | ❓ |
| IOCA-4-010 | Table 3. IOCA Code Points | ❓ |
| IOCA-4-011 | X'70' | Begin Segment | Long format | ❓ |
| IOCA-4-012 | X'71' | End Segment | Long format | ❓ |
| IOCA-4-013 | X'8C' | Begin Tile | Long format | ❓ |
| IOCA-4-014 | X'8D' | End Tile | Long format | ❓ |
| IOCA-4-015 | X'8E' | Begin Transparency Mask | Long format | ❓ |
| IOCA-4-016 | X'8F' | End Transparency Mask | Long format | ❓ |
| IOCA-4-017 | X'91' | Begin Image Content | Long format | ❓ |
| IOCA-4-018 | X'93' | End Image Content | Long format | ❓ |
| IOCA-4-019 | X'94' | Image Size | Long format | ❓ |
| IOCA-4-020 | X'95' | Image Encoding | Long format | ❓ |
| IOCA-4-021 | X'96' | IDE Size | Long format | ❓ |
| IOCA-4-022 | X'97' | Image LUT-ID (Retired) | Long format | ❓ |
| IOCA-4-023 | X'98' | Band Image | Long format | ❓ |
| IOCA-4-024 | X'9B' | IDE Structure | Long format | ❓ |
| IOCA-4-025 | X'9F' | External Algorithm Specification | Long format | ❓ |
| IOCA-4-026 | X'B5' | Tile Position | Long format | ❓ |
| IOCA-4-027 | X'B6' | Tile Size | Long format | ❓ |
| IOCA-4-028 | X'B7' | Tile Set Color | Long format | ❓ |
| IOCA-4-029 | X'F4' | Set Extended Bilevel Image Color | Long format | ❓ |
| IOCA-4-030 | X'F6' | Set Bilevel Image Color | Long format | ❓ |
| IOCA-4-031 | X'F7' | IOCA Function Set Identification | Long format | ❓ |
| IOCA-4-032 | X'FE92' | Image Data | Extended format | ❓ |
| IOCA-4-033 | X'FE9C' | Band Image Data | Extended format | ❓ |
| IOCA-4-034 | X'FEB3' | nColor Names | Extended format | ❓ |
| IOCA-4-035 | X'FEB8' | Include Tile | Extended format | ❓ |
| IOCA-4-036 | X'FEBB' | Tile TOC | Extended format | ❓ |
| IOCA-4-037 | X'FECE' | Image Subsampling | Extended format | ❓ |
| IOCA-5-001 | Briefly describes the IOCA Image Segment | ❓ |
| IOCA-5-002 | States the purpose of each IOCA self-defining field in the Image Segment | ❓ |
| IOCA-5-003 | Provides the syntax and semantics of each self-defining field, its parameter set, and its exception conditions | ❓ |
| IOCA-5-004 | For an explanation of the layout of the syntax diagrams in this chapter, see “How to Read the Syntax Diagrams”. For an explanation of the notation conventions, see “Notation Conventions”. | ❓ |
| IOCA-5-005 | Image Data parameters that describe the | ❓ |
| IOCA-5-006 | An optional Transparency Mask | ❓ |
| IOCA-5-007 | Zero or more image data elements: Image Data and | ❓ |
| IOCA-5-008 | Image Data parameters that describe the | ❓ |
| IOCA-5-009 | Zero or more Tiles | ❓ |
| IOCA-5-010 | Image Data parameters that describe the | ❓ |
| IOCA-5-011 | An optional Transparency Mask | ❓ |
| IOCA-5-012 | Zero or more image data elements: Image Data and | ❓ |
| IOCA-5-013 | Multiple image contents may exist within a single IOCA image segment. All image contents share the same Image Presentation Space and are presented in the order they appear. | ❓ |
| IOCA-5-014 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-015 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-016 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-017 | The Begin Segment parameter defines the beginning of the Image Segment. | ❓ |
| IOCA-5-018 | 0 | CODE | ID | | X'70' Begin Segment | M | ❓ |
| IOCA-5-019 | 1 | UBIN | LENGTH | X'00' – X'04' | Length of the parameters to follow | M | ❓ |
| IOCA-5-020 | 2 | UBIN | NAME | | X'00000000' – X'FFFFFFFF' Name of the Image Segment | O | ❓ |
| IOCA-5-021 | EC-700F Invalid sequence Condition: A Begin Segment is missing, or it appeared out of sequence or more than once. IOCA receivers can generate an out-of-sequence exception condition—EC-xx0F—instead of EC-700F , where xx is the one-byte ID code of the IOCA self-defining field encountered in place of the Begin Segment self-defining field. | ❓ |
| IOCA-5-022 | The End Segment parameter defines the end of the Image Segment. | ❓ |
| IOCA-5-023 | 0 | CODE | ID | | X'71' End Segment | M | ❓ |
| IOCA-5-024 | 1 | UBIN | LENGTH | X'00' | Length of the parameters to follow | M | ❓ |
| IOCA-5-025 | EC-710F Invalid sequence Condition: An End Segment is missing, or it appeared out of sequence. | ❓ |
| IOCA-5-026 | If the Image Content is tiled, it starts with a Tile T able of Contents, followed optionally by a number of parameters that set the default values, followed by zero or more Tiles. The structure of each tile is very similar to that inside an untiled Image Content, with Image Data parameters, an optional Transparency Mask, and Image Data. | ❓ |
| IOCA-5-027 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-028 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-029 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-030 | The Begin Image Content parameter defines the beginning of the Image Content. | ❓ |
| IOCA-5-031 | 0 | CODE | ID | | X'91' Begin Image Content | M | ❓ |
| IOCA-5-032 | 1 | UBIN | LENGTH | X'01' | Length of the parameters to follow | M | ❓ |
| IOCA-5-033 | 2 | CODE | OBJTYPE | | X'FF' Object type: X'FF' IOCA image object. All other values are reserved. | M | ❓ |
| IOCA-5-034 | 1. IOCA allows multiple image contents in a single Image Segment, but the receivers are not required to support more than one image content in each image segment. If a receiver that does not support multiple image contents in a single image segment receives a second Begin Image Content Parameter in an image segment, exception EC-910F exists. | ❓ |
| IOCA-5-035 | 2. All receivers that support multiple image contents must support at least 128 image contents per image segment. | ❓ |
| IOCA-5-036 | 3. Architecture does not restrict the number of image contents contained within a single image segment. If an image segment contains too many image contents for a receiver to present, the receiver should take the same action as if too many image objects were specified on a page. | ❓ |
| IOCA-5-037 | 4. If a receiver supports multiple image contents, it must support them for any type of image. For example, such a receiver must process multiple image contents containing FS10 data without raising an exception, even though the FS10 definition specifies a single image content in each image segment. | ❓ |
| IOCA-5-038 | 5. Multiple image contents are treated by the receiver as if they were sent as multiple image objects, in the same order in which they appear in the image segment. | ❓ |
| IOCA-5-039 | 6. All of the image contents are presented using the same Image Presentation Space characteristics, as defined in the image data descriptor for the image object. | ❓ |
| IOCA-5-040 | 7. Function Sets 45 and 48 are the only current function sets that require receivers to support multiple image contents in a single image segment. | ❓ |
| IOCA-5-041 | EC-910F Invalid sequence Condition: One or more of the following conditions holds: | ❓ |
| IOCA-5-042 | A Begin Image Content is missing, or it appeared out of sequence. IOCA receivers can generate an out-of-sequence | ❓ |
| IOCA-5-043 | The Begin Image Content has appeared more than once and the receiver supports only a single image content in each | ❓ |
| IOCA-5-044 | The End Image Content parameter defines the end of the Image Content. | ❓ |
| IOCA-5-045 | 0 | CODE | ID | | X'93' End Image Content | M | ❓ |
| IOCA-5-046 | 1 | UBIN | LENGTH | X'00' | Length of the parameters to follow | M | ❓ |
| IOCA-5-047 | EC-930F Invalid sequence Condition: An End Image Content is missing, or it appeared out of sequence. IOCA receivers can generate an out-of- sequence exception condition—EC-xx0F—instead of EC-930F , where xx is the one-byte ID code of the IOCA self-defining field encountered in place of the End Image Content parameter. | ❓ |
| IOCA-5-048 | Image Size parameter | ❓ |
| IOCA-5-049 | Image Encoding parameter | ❓ |
| IOCA-5-050 | IDE Size parameter | ❓ |
| IOCA-5-051 | Band Image parameter | ❓ |
| IOCA-5-052 | IDE Structure parameter | ❓ |
| IOCA-5-053 | nColor Names parameter | ❓ |
| IOCA-5-054 | External Algorithm Specification parameter | ❓ |
| IOCA-5-055 | Image Subsampling parameter | ❓ |
| IOCA-5-056 | A function set is a set of self-defining fields that describes an Image Object. For more information on function sets, see “Function Sets”. | ❓ |
| IOCA-5-057 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-058 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-059 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-060 | This self-defining field, which is mandatory in non-tiled Image Contents, describes the measurement characteristics of the image when it is created. There is no default value. | ❓ |
| IOCA-5-061 | 0 | CODE | ID | | X'94' Image Size parameter M 1 UBIN LENGTH X'09' Length of the parameters to follow | M | ❓ |
| IOCA-5-062 | 2 | CODE | UNITBASE | | X'00' – X'02' Unit base: X'00' 10 inches X'01' 10 centimeters X'02' Logical (resolution ratio) All other values are reserved. M 3–4 UBIN HRESOL X'0000' – X'7FFF' Horizontal resolution M 5–6 UBIN VRESOL X'0000' – X'7FFF' Vertical resolution M 7–8 UBIN HSIZE X'0000' – X'7FFF' Horizontal size in image points (excluding any padding bit in each scan line) M 9–10 UBIN VSIZE X'0000' – X'7FFF' Vertical size in image points (excluding any padding scan line) M UNITBASE=X'02' (logical) indicates that the following HRESOL and VRESOL specify a ratio of the horizontal and vertical resolutions. The combinations of UNITBASE, HRESOL, and VRESOL have the following meanings: • When UNITBASE=X'00' or X'01': – When HRESOL or VRESOL (or both) is zero, the resolution of the Image Content in that direction is undefined. Image Contents with undefined resolutions are written with each image point mapped onto one point in the Image Presentation Space. – Nonzero HRESOL or VRESOL values, divided by 10, yield the number of image points per inch or per centimeter in the corresponding direction. Example: If the distance between image points is 1/200th of an inch, the resolutions are specified as X'0007D007D0'. This means that there are 2000 image points per 10 inches in both the horizontal and vertical directions. • With UNITBASE=X'02': – When either HRESOL or VRESOL is zero, the Image Content's resolutions in both directions are undefined. Image Contents with undefined resolutions are written with each image point mapped on a point in the Image Presentation Space. – Dividing a nonzero HRESOL value by a nonzero VRESOL value yields the ratio of the horizontal and vertical resolutions. Example: X'0200010002' means that the vertical resolution is twice the horizontal resolution, and that the image is sharper in the vertical direction than in the horizontal direction. T o keep this ratio, the controlling environment allows you to define the Image Presentation Space so as to have the doubled resolution in the vertical | direction. | ❓ |
| IOCA-5-063 | Note: IOCA generators should set HSIZE and VSIZE to the image's actual width and height regardless of the compression algorithm used. Setting either HSIZE or VSIZE to zero might cause some IOCA receivers to abort prematurely. | ❓ |
| IOCA-5-064 | System action: The size detected from the image data is used. | ❓ |
| IOCA-5-065 | This optional self-defining field describes the algorithms by which the image data is encoded. See Appendix A, “Compression and Recording Algorithms” for details. | ❓ |
| IOCA-5-066 | 0 | CODE | ID | | X'95' Image Encoding parameter M 1 UBIN LENGTH X'02' – X'03' Length of the parameters to follow | M | ❓ |
| IOCA-5-067 | 2 | CODE | COMPRID | | X'00' – X'0E', X'20', X'80' – X'84', X'A0' – X'AF', X'FE' Compression algorithm: X'00' (Retired) X'01' IBM MMR-Modified Modified Read X'02' (Retired) X'03' No compression X'04' (Retired) X'05' (Retired) X'06' RL4 (Run Length 4) X'07' (Retired) X'08' ABIC (Bilevel Q-Coder) X'09' TIFF algorithm 2 X'0A' Concatenated ABIC X'0B' Color compression used by OS/2 Image Support X'0C' TIFF PackBits X'0D' TIFF LZW X'0E' TIFF LZW with Differencing Predictor X'20' Solid Fill Rectangle X'80' G3 MH-Modified Huffman (ITU-TSS T .4 Group 3 one-dimensional coding standard for facsimile) X'81' G3 MH-Modified READ (ITU-TSS T .4 Group 3 two-dimensional coding option for facsimile) X'82' G4 MMR-Modified Modified READ (ITU-TSS T .6 Group 4 two-dimensional coding standard for facsimile) X'83' JPEG algorithms (See the External Algorithm Specification parameter for detail) X'84' JBIG2 X'A0'– X'AF' (Retired) X'FE' User-defined algorithms (see the External Algorithm Specification parameter for details) All other values are reserved. | M | ❓ |
| IOCA-5-068 | 3 | CODE | RECID | | X'00' – X'04', X'FE' Recording algorithm: X'00' (Retired) X'01' RIDIC (Recording Image Data Inline Coding) X'03' Bottom-to-T op X'04' Unpadded RIDIC X'FE' See the External Algorithm Specification parameter for details All other values are reserved. | M | ❓ |
| IOCA-5-069 | 4 | CODE | BITORDR | | X'00' – X'01' Bit order within each image data byte: X'00' Left-to-right X'01' Right-to-left All other values are reserved. | O | ❓ |
| IOCA-5-070 | 1. When COMPRID or RECID are X'FE', the External Algorithm Specification parameter must also be present within the same Image Content, otherwise exception condition EC-9F01 exists. | ❓ |
| IOCA-5-071 | 2. The External Algorithm Specification Parameter is no longer required when COMPRID is X'83'. If the decompressor in the receiver fails because the compressed datastream requires a feature unimplemented in the decoder, exception EC-9511 occurs. | ❓ |
| IOCA-5-072 | 3. The Solid Fill Rectangle compression algorithm can be used only within tiled images, for bilevel tiles. | ❓ |
| IOCA-5-073 | 4. JBIG2 is a toolkit with many different capabilities. The standard recognizes a number of profiles that serve the same function as Function Sets in IOCA. Receivers declaring the JBIG2 support must support at least one JBIG2 profile, but are not obliged to support all of them. If a receiver encounters JBIG2-compressed data encoding unsupported function, exception EC-9511 occurs. | ❓ |
| IOCA-5-074 | 5. LZW encoders sometimes terminate the data early. For either the TIFF LZW or the TIFF LZW with Differencing Predictor compression algorithms, if the LZW decoder does not produce the expected number of bytes, no exception should be raised and the receiver should fill the remaining data with binary zeros. | ❓ |
| IOCA-5-075 | If the Image Encoding parameter is not present, the defaults are X'03' for the compression algorithm, X'01' for the recording algorithm, and zero for the bit order. | ❓ |
| IOCA-5-076 | The image data is not encoded according to the compression or recording algorithm specified in the Image Encoding | ❓ |
| IOCA-5-077 | The image data cannot be decoded successfully using the size values specified in the Image Size parameter. This | ❓ |
| IOCA-5-078 | The image data is not in complete accordance with the compression algorithm specified in the Image Encoding | ❓ |
| IOCA-5-079 | Image is encoded using the algorithm specified in the Image Encoding Parameter, but uses a function of the algorithm | ❓ |
| IOCA-5-080 | System action: Receivers should attempt to present or make use of all successfully decompressed image data. Note, however, that the resulting partial image might differ from the original image. | ❓ |
| IOCA-5-081 | This optional self-defining field specifies the number of bits that comprise each Image Data Element (IDE) in the image data, before any subsampling or compression method is performed on the IDEs. | ❓ |
| IOCA-5-082 | 0 | CODE | ID | | X'96' IDE Size parameter M 1 UBIN LENGTH X'01' Length of the parameters to follow | M | ❓ |
| IOCA-5-083 | 2 | UBIN | IDESZ | | X'01' – X'FF' Number of bits in each IDE M If the IDE Size parameter is not present, the default value for IDESZ is 1 (bilevel | image). | ❓ |
| IOCA-5-084 | EC-9611 Inconsistent Image Data parameters, or inconsistent Image Data parameter and Image Data Condition: The compression scheme specified in the Image Encoding parameter does not support the IDE size specified in the IDE Size parameter. | ❓ |
| IOCA-5-085 | If the Band Image parameter is present, then the image data must be carried by the Band Image Data parameter. Each band of the image IDEs is carried by one or more Band Image Data parameters. The Band Image Data parameter is described in “Band Image Data”. | ❓ |
| IOCA-5-086 | 0 | CODE | ID | | X'98' Band Image parameter M 1 UBIN LENGTH X'02' – X'FE' Length of the parameters to follow | M | ❓ |
| IOCA-5-087 | 2 | UBIN | BCOUNT | | X'01' – X'FD' Number of bands M One or more repeating groups in the following | format: | ❓ |
| IOCA-5-088 | 0 | UBIN | BITCNT | | X'01' – X'FF' Bit count for the band M BITCNT specifies how many bits of the IDE comprise one band, and BCOUNT specifies how many bands comprise the image data. The number of BITCNT s in the self-defining field must equal the BCOUNT value. The BITCNT s appear in the order in which the bits were placed into the band. For boundary alignment purposes, BITCNT can include padding bits inserted into the data. If BITCNT contains no padding bits, then the sum of all the BITCNT values equals the IDE size specified by the IDE Size parameter. Example 1: For a single-band image with an IDE size of four with no padding bit, the first four bits of data represent the first IDE, the next four represent the second IDE, and so on. Figure 15 illustrates the layout of image bits in this image. Figure 15. Example of a Four-Bit Single-Band Image with No Padding Bit IDE1 IDE2 IDE3 IDE4 | IDEn | ❓ |
| IOCA-5-089 | 0000 | 1000 | 0110 | | 1010 1110 XXXX Band | 1 | ❓ |
| IOCA-5-090 | 0 1 1 X IDE2 IDE3 IDEn Band 1 Band 2 Band 3 Band 4 | ❓ |
| IOCA-5-091 | EC-980F Invalid sequence Condition: The Band Image parameter appeared out of sequence or more than once. | ❓ |
| IOCA-5-092 | EC-9815 Invalid IDE size Condition: The IDE size, determined by the Band Image parameter, does not match the IDE Size parameter. | ❓ |
| IOCA-5-093 | See Appendix B, “Bilevel, Grayscale, and Color Images” for details on the relationship with the IDE Size parameter. | ❓ |
| IOCA-5-094 | 0 | CODE | ID | | X'9B' IDE Structure parameter M 1 UBIN LENGTH X'06' – X'14' Length of the parameters to follow | M | ❓ |
| IOCA-5-095 | 2 | BITS | FLAGS | | M Bit 0 ASFLAG B'0' – B'1' Additive or Subtractive: B'0' Additive B'1' Subtractive Bit 1 GRAYCODE B'0' – B'1' Gray coding: B'0' Off B'1' On Bits 2–7 B'000000' Reserved; should be zero 3 CODE FORMAT X'01', X'02', X'04', X'12', X'8n' Color model: X'01' RGB X'02' YCrCb X'04' CMYK X'12' YCbCr X'8n' nColor (X'2' ≤ n ≤ X'F') All other values are reserved. M 4–6 X'000000' Reserved; should be zero M 7 UBIN SIZE1 X'00' – X'FF' Number of bits/IDE for component 1 | M | ❓ |
| IOCA-5-096 | 8 | UBIN | SIZE2 | | X'00' – X'FF' Number of bits/IDE for component 2 O 9 UBIN SIZE3 X'00' – X'FF' Number of bits/IDE for component 3 | O | ❓ |
| IOCA-5-097 | 10 | UBIN | SIZE4 | | X'00' – X'FF' Number of bits/IDE for component 4 O 11 UBIN SIZE5 X'00' – X'FF' Number of bits/IDE for component 5 O | ... | ❓ |
| IOCA-5-098 | 21 | UBIN | SIZE15 | | X'00' – X'FF' Number of bits/IDE for component 15 | O | ❓ |
| IOCA-5-099 | Additive means that the maximum color value represents full intensity of that color, while the minimum color | ❓ |
| IOCA-5-100 | Subtractive means that the minimum color value represents full intensity of that color, while the maximum | ❓ |
| IOCA-5-101 | RGB means that each value is to be treated as a set of red, green, blue intensity values, and the set is in the | ❓ |
| IOCA-5-102 | YCrCb means that each value is to be treated as a set of Y , Cr, Cb values, and the set is in the order Y , Cr, | ❓ |
| IOCA-5-103 | YCbCr means that each value is to be treated as a set of Y , Cb, Cr values, and the set is in the order Y , Cb, | ❓ |
| IOCA-5-104 | CMYK means that each value is to be treated as a set of cyan, magenta, yellow, black intensity values and | ❓ |
| IOCA-5-105 | nColor means that each value is to be treated as a set of n separate intensity values. A color management | ❓ |
| IOCA-5-106 | 1 Table 4. Gray Code Values (Decimal) | ❓ |
| IOCA-5-107 | 7 B'0100' 8 B'1100' 9 B'1101' | ❓ |
| IOCA-5-108 | 1. Source: R. W. Lucky, J. Salz, and E. J. Weldon Jr., (New York: McGraw-Hill, 1968). | ❓ |
| IOCA-5-109 | Note: If the IDE Structure parameter is not present, the defaults are ASFLAG of B'0' (additive), GRAYCODE of B'0' (off), FORMAT of YCbCr, and SIZE1 the same as the IDE size specified in the IDE Size parameter. | ❓ |
| IOCA-5-110 | The value of ASFLAG is invalid or unsupported. | ❓ |
| IOCA-5-111 | The value of GRAYCODE is invalid or unsupported. | ❓ |
| IOCA-5-112 | The value of FORMAT is invalid or unsupported. | ❓ |
| IOCA-5-113 | The value of a SIZE field is invalid or unsupported. | ❓ |
| IOCA-5-114 | The sum of the SIZE values does not match the IDE size specified by the IDE Size parameter. | ❓ |
| IOCA-5-115 | Color model is CMYK and SIZE4 is missing. | ❓ |
| IOCA-5-116 | SIZE4 is present and the color model is not CMYK or nColor. | ❓ |
| IOCA-5-117 | More than four SIZE parameters are present and the color model is not nColor. | ❓ |
| IOCA-5-118 | Color model is nColor and the number of SIZE parameters is not equal to the second half of the FORMAT byte. | ❓ |
| IOCA-5-119 | The data in this field is made up of n repeating groups, each of which contains a color name. | ❓ |
| IOCA-5-120 | 0–1 | CODE | ID | | X'FEB3' nColor Names parameter M 2–3 UBIN LENGTH X'0006' – X'0EC6' Length of the parameters to follow M 4–5 X'0000' Reserved; should be zero M n repeating groups in the following format, when the IDE Structure FORMAT field is | X'8n' | ❓ |
| IOCA-5-121 | 0 | X'00' | Reserved; | | should be zero M 1 UBIN NAMELEN X'00' – X'FA', even values only Length, in bytes, of the name to follow M 2–m CHAR CLRNAME any UTF-16BE characters Color name O The color names are specified using the UTF-16BE encoding. This means that the length of the name, NAMELEN, must be an even number, otherwise exception EC-B310 exists. If the color name is an invalid UTF- 16BE character string, exception EC-B310 also exists. Color names of zero-length are allowed, and state no information about the color name. If there is no nColor Names parameter, there is no information about any of the names of the colors. If the nColor Names parameter appears and correctly has an entry in the repeating groups for all n color names, but some color names have zero-length, then there is no information about the name of those colors. There is a similarity between this IOCA parameter and the Colorant Identification List tag in CMOCA, since they are both naming colors. In an IOCA nColor image, the color names are contained in the nColor Names parameter. When specifying a single “nColor” color to use in the Tile Set Color parameter—among many other similar places in AFP—the Highlight Color Space can be used in conjunction with an Indexed CMR, and in that CMR the color names are found in the Colorant Identification List tag. The names here are limited to 250 (X'FA') bytes, to match the same limit in CMOCA; if a name is longer, exception EC-B310 exists. When appropriate, the same color names should be used in both places. In particular, the following known colorant names defined in CMOCA in the Colorant Identification List tag can be used to specify colors in the device color space: AFPC_Device_C Device Cyan AFPC_Device_M Device Magenta AFPC_Device_Y Device Yellow AFPC_Device_K Device Black AFPC_Device_R Device | Red | ❓ |
| IOCA-5-122 | AFPC_Device_G Device Green AFPC_Device_B Device Blue AFPC_Device_Gray Device Gray | ❓ |
| IOCA-5-123 | EC-B311 Inconsistent Image Data parameters, or inconsistent Image Data parameter and Image Data Explanation: Either nColor Names should not be present because the nColor color model is not being used, or the number of color names in the nColor Names parameter does not match the number of colors in the nColor color model in the IDE Structure parameter. | ❓ |
| IOCA-5-124 | This optional self-defining field provides complementary information about the algorithm specified in the Image Encoding parameter. It can be used only in conjunction with that parameter. | ❓ |
| IOCA-5-125 | 0 | CODE | ID | | X'9F' External Algorithm Specification parameter M 1 UBIN LENGTH X'03' – X'FF' Length of the parameters to follow | M | ❓ |
| IOCA-5-126 | 2 | CODE | ALGTYPE | | X'00', X'10' Type of algorithm specified: X'00' Recording algorithm X'10' Compression algorithm All other values are reserved. | M | ❓ |
| IOCA-5-127 | 3 | X'00F' | Reserved; | | should be zero M 4–n CODE ALGSPEC Recording Algorithm Specification or Compression Algorithm Specification M Recording Algorithm Specification This subparameter is carried by the External Algorithm Specification parameter . Syntax Offset Type Name Range Meaning | M/O | ❓ |
| IOCA-5-128 | 0 | BITS | DIRCTN | | Direction of IDEs M Bits 0–1 IDEPTH B'11' Direction of successive IDEs along a line (clockwise from X-axis): B'11' 270 degrees All other values are reserved. Bits 2–3 LINEPRG B'00' Direction of progression of successive lines (clockwise from X-axis): B'00' 0 degrees All other values are reserved. Bit 4 RNDTRIP B'0' Direction of successive IDEs relative to the previous line (clockwise from the previous line): B'0' 0 degrees All other values are reserved. Bits 5–7 B'000' Reserved; should be zero 1 CODE PADBDRY X'03' Boundary length for padding: X'03' 32-bit boundary All other values are reserved. | M | ❓ |
| IOCA-5-129 | 2 | CODE | PADALMT | | X'00' Alignment for padding: X'00' Data left-aligned within boundary All other values are reserved. | M | ❓ |
| IOCA-5-130 | IDEPTH specifies how successive IDEs proceed along a line in relation to the X-axis of the Image | ❓ |
| IOCA-5-131 | LINEPRG specifies how successive lines of IDEs proceed in relation to the X-axis of the Image Coordinate | ❓ |
| IOCA-5-132 | RNDTRIP specifies how the next line of IDEs proceeds in relation to the previous line. Degrees are | ❓ |
| IOCA-5-133 | RNDTRIP = B’ 0’ ( 0 degrees) | ❓ |
| IOCA-5-134 | ITU-TSS Recommendation T .81 | ❓ |
| IOCA-5-135 | ISO/IEC International Standard 10918-1 | ❓ |
| IOCA-5-136 | 0 | CODE | COMPRID | | X'83' JPEG algorithms M 1 X'00' Reserved; should be zero | M | ❓ |
| IOCA-5-137 | 2 | CODE | VERSION | | X'00' Version M 3 X'00' Reserved; should be zero | M | ❓ |
| IOCA-5-138 | 4 | CODE | MARKER | | X'C0' – X'C3', X'C5' – X'C7', X'C9' – X'CB', X'CD' – X'CF' Marker code: Non-differential Huffman coding: X'C0' Baseline DCT X'C1' Extended sequential DCT X'C2' Progressive DCT X'C3' Lossless (sequential) Differential Huffman coding: X'C5' Differential sequential DCT X'C6' Differential progressive DCT X'C7' Differential lossless Non-differential arithmetic coding: X'C9' Extended sequential DCT X'CA' Progressive DCT X'CB' Lossless (sequential) Differential arithmetic coding: X'CD' Differential sequential DCT X'CE' Differential progressive DCT X'CF' Differential lossless All other values are reserved. M 5–7 X'000000' Reserved; should be zero M JPEG algorithms have the following restrictions: • They cannot be applied to images whose IDE size is 1 bit/IDE. • The baseline DCT-based algorithm is applicable only to images with 8-bits/component. The other DCT- based algorithms are applicable only to images with 8-bits/component or 12-bits/component. The IDE of the image can consist of at most four components. • The lossless algorithms are applicable only to images with n-bits/component, where 2 ≤ n ≤ 16. The IDE of the image can consist of at most four | components. | ❓ |
| IOCA-5-139 | 6–n COMPSPEC Any User-defined specification O | ❓ |
| IOCA-5-140 | EC-9F11 Inconsistent Image Data parameters, or inconsistent Image Data parameter and Image Data Condition: An External Algorithm Specification parameter is present, but the Image Encoding parameter does not require it. | ❓ |
| IOCA-5-141 | Subsampling relies on the fact that in color images the difference between adjacent IDEs is small for certain color components. For example, in the YCrCb and YCbCr color models, most of the image information is concentrated in the Y component; hence it is fairly common to store only the average values of the Cr and Cb components of two adjacent IDEs. | ❓ |
| IOCA-5-142 | 0–1 | CODE | ID | | X'FECE' Image Subsampling parameter M 2–3 UBIN LENGTH X'0000' – X'FFFF' Length of the parameters to follow M 4–n CODE SDF Zero or more self-defining fields that specify the subsampling methods O If the Image Subsampling parameter is not present, the default is that the IDEs have not been subsampled. Sampling Ratios This optional self-defining field is carried by the Image Subsampling parameter. It specifies the number of horizontal and vertical samples that make up each component of the IDEs. Syntax Offset Type Name Range Meaning | M/O | ❓ |
| IOCA-5-143 | 0 | CODE | ID | | X'01' Sampling Ratios M 1 UBIN LENGTH X'02' – X'FE' Length of the parameters to follow | M | ❓ |
| IOCA-5-144 | 1 | to | 127 | | repeating groups in the following | format: | ❓ |
| IOCA-5-145 | 0 | UBIN | HSAMPLE | | X'00' – X'7F' Number of horizontal samples that make up the component of the IDEs | M | ❓ |
| IOCA-5-146 | 1 | UBIN | VSAMPLE | | X'00' – X'7F' Number of vertical samples that make up the component of the IDEs M If the HSAMPLE and VSAMPLE group for a particular component of the IDEs is not present, the default value is 1 for both HSAMPLE and VSAMPLE. However, any preceding HSAMPLE and VSAMPLE group must be included. For example, a color image with only its third component subsampled must have HSAMPLE1, VSAMPLE1, HSAMPLE2, and VSAMPLE2 specified as equal to | 1. | ❓ |
| IOCA-5-147 | 7 The average of the third and fourth IDEs’ Cb component values ... ... | ❓ |
| IOCA-5-148 | EC-CE10 Invalid or unsupported Image Data parameter value Condition: The Image Subsampling parameter contains an invalid or unsupported value. | ❓ |
| IOCA-5-149 | Tiles are used when different parts of an image are described using different color spaces, resolutions, and compression algorithms. Tiles can also be used as resources (see Appendix C, “IOCA Tile Resource”). | ❓ |
| IOCA-5-150 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-151 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-152 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-153 | Each Image Content can be either tiled or untiled. In an untiled Image Content, no tiles may appear. Tiled | ❓ |
| IOCA-5-154 | Tiles can use different color spaces and compression algorithms. | ❓ |
| IOCA-5-155 | Each tile must either have the resolution of the underlying Image Presentation Space, or be subsampled by | ❓ |
| IOCA-5-156 | Tiles must be non-overlapping and must also be specified in top-down, left-to-right order. | ❓ |
| IOCA-5-157 | Tiles do not have to cover the whole Image Presentation Space. The part of the Image Presentation Space | ❓ |
| IOCA-5-158 | Within tiles, foreground and background are determined based on the color space used. | ❓ |
| IOCA-5-159 | A tile can be either a data tile (that is, a fully defined tile with all the data present), or a referencing tile. A | ❓ |
| IOCA-5-160 | referencing tile contains an invocation, positioning, and merging instruction for a tile resource and is intended to save bandwidth and processing time when processing multiple images that have some areas in common. | ❓ |
| IOCA-5-161 | The Begin Tile parameter defines the beginning of a tile. | ❓ |
| IOCA-5-162 | 0 | CODE | ID | | X'8C' Begin Tile parameter M 1 UBIN LENGTH X'00' Length of the parameters to follow M Notes: 1. In tiled images, all of the image data must be contained in tiles. That is, no Image Data or Band Image Data can appear outside of the sequence delimited by the Begin Tile/End Tile pairs. 2. The Begin Tile Parameter can appear in all of the contexts where Image Data and Band Image Data can appear in non-tiled images. 3. If the Begin Tile Parameter is encountered, the first parameter after the Begin Image Content parameter must be the Tile TOC parameter. Otherwise, exception EC-8C0F | occurs. | ❓ |
| IOCA-5-163 | EC-8C0F Invalid sequence Condition: A Begin Tile has appeared out of sequence. | ❓ |
| IOCA-5-164 | The End Tile parameter defines the end of a tile. | ❓ |
| IOCA-5-165 | 0 | CODE | ID | | X'8D' End Tile parameter M 1 UBIN LENGTH X'00' Length of the parameters to follow | M | ❓ |
| IOCA-5-166 | EC-8D0F Invalid sequence Condition: An End Tile is missing after a Begin Tile has been encountered, or it appeared out of sequence. | ❓ |
| IOCA-5-167 | The Tile Position parameter determines the position of the upper-left corner of the tile in the Image Presentation Space. | ❓ |
| IOCA-5-168 | 0 | CODE | ID | | X'B5' Tile Position parameter M 1 UBIN LENGTH X'08' Length of the parameters to follow M 2–5 UBIN XOFFSET X'00000000' – X'7FFFFFFF' Horizontal offset of the tile origin, relative to the presentation space origin M 6–9 UBIN YOFFSET X'00000000' – X'7FFFFFFF' Vertical offset of the tile origin, relative to the presentation space origin M Notes: 1. XOFFSET and YOFFSET are specified in presentation space image points. If subsampling is specified in the Tile Size parameter, it does not apply to XOFFSET and YOFFSET . 2. The upper-left corner of the tile must be contained in the presentation space; that is, XOFFSET and YOFFSET must be less than XSIZE and YSIZE, respectively, as specified in the Image Data Descriptor. For the definition of the Image Data Descriptor, see “Image Data Descriptor (IDD)”. 3. If the current tile is not the first tile specified, the YOFFSET value must be at least as large as any specified for the previous tiles. If YOFFSET is identical to the previous YOFFSET , XOFFSET must be greater than the previous XOFFSET . This requirement forces the tile order of top down (primary key) and left to right (secondary key). This condition applies only if the Tile TOC parameter does not contain the tile table of | contents. | ❓ |
| IOCA-5-169 | Tiles are specified out of order. This exception can occur only if the Tile TOC parameter does not contain the table of | ❓ |
| IOCA-5-170 | Offset mismatch: the tile table of contents has been specified, but the XOFFSET or YOFFSET given for this tile does not | ❓ |
| IOCA-5-171 | match the values specified in the Tile Position Parameter. | ❓ |
| IOCA-5-172 | The Tile Size parameter defines the size and resolution of a tile. | ❓ |
| IOCA-5-173 | 0 | CODE | ID | | X'B6' Tile Size parameter M 1 UBIN LENGTH X'08' – X'09' Length of the parameters to follow M 2–5 UBIN THSIZE X'00000000' – X'7FFFFFFF' Horizontal size in image points, excluding any padding bits in each scan line M 6–9 UBIN TVSIZE X'00000000' – X'7FFFFFFF' Vertical size in image points, excluding any padding scan lines | M | ❓ |
| IOCA-5-174 | 10 | UBIN | RELRES | | X'01' – X'02' Relative resolution of the tile O Notes: 1. If RELRES has not been specified, the tile resolution is the same as the resolution of the Image Presentation Space. 2. A RELRES value of 1 means that the tile has the same resolution as the Image Presentation Space. A RELRES value of 2 means the resolution of the tile is half the resolution of the Image Presentation Space. For example, if the Image Presentation Space has a resolution of 600 dpi, a tile with a RELRES of 2 has a resolution of 300 dpi. The default value of RELRES is 1. 3. The tile dimensions THSIZE and TVSIZE are specified in the tile resolution. T o get the size of the tile in presentation space points, multiply the THSIZE and TVSIZE by RELRES. 4. The tile must be wholly contained in the presentation space; that is, (XOFFSET + RELRES × THSIZE) must not exceed the XSIZE specified in the Image Data Descriptor and (YOFFSET + RELRES × TVSIZE) must not exceed the YSIZE specified in the Image Data Descriptor. 5. Tiles must be non-overlapping. If X1, Y1, H1, V1, S1 and X2, Y2, H2, V2, S2 describe the offset, size, and subsampling of any two tiles, at least one of the following relationships must hold: X1 + S1 × H1 ≤ X2 X2 + S2 × H2 ≤ X1 Y1 + S1 × V1 ≤ Y2 Y2 + S2 × V2 ≤ Y1 Note that, in this example, tiles 1 and 2 are not necessarily sorted. That is, the origin of tile 1 need not be above or left of the origin of tile 2. 6. The JPEG compression algorithm works on 8-by-8-pixel blocks. Depending on the JPEG subsampling (note that this is different from RELRES in Tile Size), the Minimum Coded Units (MCUs) used by JPEG might be larger. The most common MCU size is 16 by 16 pixels. The image must be padded before compression to the MCU boundary and the decompressor discards the padding pixels. T o help receivers merge JPEG-compressed tiles efficiently, the tile data must be padded to the left and top to the nearest 8- pixel boundary in the tile resolution, after applying tile subsampling and before compression. After padding on the left and top, the tile is padded as usual on the right and bottom. On decompression, the decompressor discards the right and bottom padding pixels. The receiver then must discard any left and top padding pixels. The number of pad pixels on the left and top can be computed by dividing the XOFFSET and YOFFSET by RELRES×8 and taking the remainder. Note that padding is done in the Image Presentation Space image points, before subsampling. Otherwise, images with odd XOFFSET or YOFFSET could not be | aligned. | ❓ |
| IOCA-5-175 | If the compressor allows the caller to specify the padding data, manual padding is not necessary. Note that manual padding also assumes that the receiver checks the image returned by the decompressor and discards not only the top and left pads, but also the bottom and right pads. The receiver can compute the pad sizes from the values of RELRES, XOFFSET , YOFFSET , THSIZE, and TVSIZE. | ❓ |
| IOCA-5-176 | The tile overlaps a previously specified tile. | ❓ |
| IOCA-5-177 | Subsampling mismatch: the RELRES value in the table of contents does not match the RELRES value in the Tile Size | ❓ |
| IOCA-5-178 | Size mismatch: the THSIZE or TVSIZE specified in the table of contents does not match the corresponding value in the | ❓ |
| IOCA-5-179 | The Tile Set Color parameter specifies the color used to paint significant pels of a bilevel tile. | ❓ |
| IOCA-5-180 | 0 | CODE | ID | | X'B7' Tile Set Color parameter M 1 UBIN LENGTH X'0A' – X'0C' Length of the parameters to follow | M | ❓ |
| IOCA-5-181 | 2 | CODE | CSPACE | | X'01', X'04', X'06', X'08', X'40' Color space: X'01' RGB X'04' CMYK X'06' Highlight color space X'08' CIELAB X'40' Standard OCA color space M 3–5 X'000000' Reserved; should be zero M 6 UBIN SIZE1 X'01' – X'08', X'10' Number of bits/IDE for component 1; see color space definitions | M | ❓ |
| IOCA-5-182 | 7 | UBIN | SIZE2 | | X'00' – X'08' Number of bits/IDE for component 2; see color space definitions | M | ❓ |
| IOCA-5-183 | 8 | UBIN | SIZE3 | | X'00' – X'08' Number of bits/IDE for component 3; see color space definitions | M | ❓ |
| IOCA-5-184 | 9 | UBIN | SIZE4 | | X'00' – X'08' Number of bits/IDE for component 4; see color space definitions M 10–n Color Color specification; see “Tile Set Color Semantics” for details M Notes: 1. The Tile Set Color Parameter serves two purposes. One purpose is to define the color of the significant pels in a bilevel tile. The other is to paint the whole tile with the specified color. In the second use, the tile does not contain any image data. 2. If the Tile Set Color Parameter is present, the significant image pels are painted with the specified color. Insignificant image pels are treated according to the rules for bilevel images. 3. If all pels are significant (that is, if the whole tile is to be painted), the compression algorithm must be set to Solid Fill Rectangle. In this case (solid fill), Image Data and Band Image Data cannot appear, or the exceptions EC-920F and EC-9C0F occur. 4. The Image Encoding parameter and IDE Structure parameter can appear for the tile, but must specify a bilevel image (the IDE size must be 1). The color space given in the IDE Structure parameter must be either YCbCr or YCrCb. Tile Set Color Semantics CSPACE Is a code that defines the color space and the encoding for the color specification. Value Description X'01' RGB color space. The color value is specified with three components. Components 1, 2, and 3 are unsigned binary numbers that specify the red, green, and blue intensity values, in that order. SIZE1, SIZE2, and SIZE3 are nonzero and define the number of bits used to specify each component. SIZE4 is reserved and should be set to | zero. | ❓ |
| IOCA-5-185 | 1. The color that is rendered when a highlight color is specified is device dependent. | ❓ |
| IOCA-5-186 | For presentation devices that support colors other than black, highlight color values in the range X'0001' to X'FFFF' may be mapped to any color. For bilevel devices, the color may be simulated with a graphic pattern. | ❓ |
| IOCA-5-187 | 2. If the specified highlight color is “presentation device default”, devices whose default color is black use the percent coverage parameter, which is specified in component 2, to render a percent shading. | ❓ |
| IOCA-5-188 | 3. On printing devices, the color of medium is normally white, in which case a coverage of n% results in adding (100-n)% white to the specified color, or tinting the color with (100-n)% white. Display devices may assume the color of medium to always be white and use this algorithm to render the specified coverage. | ❓ |
| IOCA-5-189 | 4. The highlight color space can also specify indexed colors when used in conjunction with a Color Mapping T able (CMT) or an Indexed (IX) Color Management Resource (CMR). In that case, component 1 specifies a two-byte value that is the index into the CMT or the IX CMR and components 2 and 3 are ignored. Note that when both a CMT and Indexed CMRs are used, the CMT is always accessed first. T o preserve compatibility with existing highlight color devices, indexed color values X'0000' to X'00FF' are reserved for existing highlight color applications and devices. That is, indexed color values in the range | ❓ |
| IOCA-5-190 | All others Reserved SIZE1 Defines the number of bits used to specify the first color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. For example, if SIZE1 = X'06', the first color component has two padding bits. | ❓ |
| IOCA-5-191 | Architecture Note: For a description of color spaces and their relationships, see R. Hunt, The Reproduction of Colour in Photography, Printing and Television, Fifth Edition, Fountain Press, 1995. | ❓ |
| IOCA-5-192 | CSPACE | ❓ |
| IOCA-5-193 | EC-B711 Inconsistent Tile Set Color parameter Condition: The IDESZ field in the IDE Size parameter has a value other than 1, or the color space specified in the IDE Structure parameter is not YCbCr or YCrCb. | ❓ |
| IOCA-5-194 | The Include Tile parameter defines the tile as a referencing tile. The tile does not contain any image data, except possibly a Transparency Mask, and is instead read from the referenced resource. | ❓ |
| IOCA-5-195 | 0–1 | CODE | ID | | X'FEB8' Include Tile parameter M 2–3 UBIN LENGTH X'0004' Length of the parameters to follow M 4–7 CODE TIRID X'00000000' – X'FFFFFFFF' Tile Resource local identifier M Notes: 1. If a tile contains the Include Tile parameter, it must contain a Tile Position parameter and can also contain a transparency mask. Any other parameters cause one of the Invalid Sequence (EC-xx0F) exceptions to be raised. 2. The Tile Position parameter in the included tile is ignored. The Tile Position parameter specified in the referencing tile is used instead. 3. If a referencing tile contains a transparency mask and the included tile also contains a transparency mask, the two masks are combined by using the logical AND operation. That is, a pixel is foreground if it is defined as foreground in both masks. 4. Tile resources do not contain any references to the Image Presentation Space. Each included tile is interpreted according to the current Image Presentation Space. 5. Except for the Tile Position and transparency mask, the included tile is treated exactly as if it was specified entirely locally. All defaulting and override rules for tile data apply. 6. The included tile must not contain another Include Tile parameter (that is, no nested references are allowed). There are no other constraints on the tile content. 7. Any other errors, such as the tile not being contained in the Image Presentation Space, are treated by raising the same exceptions as if the tile were specified | locally. | ❓ |
| IOCA-5-196 | EC-B811 Inconsistent Include Tile parameter Condition: The included tile resource contains an Include Tile parameter. | ❓ |
| IOCA-5-197 | The Tile T able of Contents (TOC) parameter defines the image as a tiled image. Optionally, it also defines the size and position of each tile. | ❓ |
| IOCA-5-198 | 0–1 | CODE | ID | | X'FEBB' Tile TOC parameter M 2–3 UBIN LENGTH X'0002' – X'7FFF' Length of the parameters to follow; this value must be a multiple of 26, plus 2 M 4–5 X'0000' Reserved; should be zero M Zero or more repeating groups, with tile table of contents. If any TOC entries are present, then an entry for each tile must be present. The groups have the following format: 0–3 UBIN XOFFSET X'00000000' – X'7FFFFFFF' Horizontal offset of the tile origin, relative to the image origin M 4–7 UBIN YOFFSET X'00000000' – X'7FFFFFFF' Vertical offset of the tile origin, relative to the image origin M 8–11 UBIN THSIZE X'00000000' – X'7FFFFFFF' Horizontal size in image points, excluding any padding bits in each scan line M 12–15 UBIN TVSIZE X'00000000' – X'7FFFFFFF' Vertical size in image points, excluding any padding scan lines | M | ❓ |
| IOCA-5-199 | 16 | UBIN | RELRES | | X'01' – X'02' Relative resolution of the tile M 17 CODE COMPR Compression algorithm; see Image Encoding parameter M 18–25 UBIN DATAPOS Any Offset, in bytes, from the start of the Begin Segment parameter of the current image, to the start of the Begin Tile parameter starting the tile M Notes: 1. Tiles in the table of contents must be specified in top-down, left-to-right order. If the table of contents is specified, the tiles themselves can be specified in any order (that is, the order restriction described for the Tile Position parameter is lifted). 2. The Tile TOC parameter must appear immediately after the Begin Image Content parameter; otherwise exception EC-BB0F occurs. If a Begin Tile parameter is encountered without a Tile TOC Parameter having been specified, exception EC-8C0F occurs. 3. If the image contains the Tile TOC parameter, no Image Data or Band Image Data may appear outside of the tiles (Begin Tile/End Tile pairs). Otherwise, exception EC-9201 (Image Data) or EC-9C01 (Band Image Data) occurs. 4. The presence of the Tile TOC parameter does not require that any tiles be actually specified. An empty image (no tiles present) is valid and all the image points are treated as background. 5. In terms of the DATAPOS, the first byte of the Begin Segment parameter has the offset zero. 6. If the Tile TOC parameter contains the table of contents, the values in the table of contents entry for each tile must match the values specified in the Tile Position parameter and Tile Size parameter for that tile. Otherwise, exception EC-B511 or EC-B611, respectively, occurs when inconsistent values are encountered in the Tile Position parameter and the Tile Size | parameter. | ❓ |
| IOCA-5-200 | Not all tiles are listed in the table of contents, even though the table of contents contains at least one tile. | ❓ |
| IOCA-5-201 | The table of contents lists a non-existent tile. | ❓ |
| IOCA-5-202 | Invalid tile order: two or more tiles in the table of contents have identical sort keys, or the sort keys are out of sequence. | ❓ |
| IOCA-5-203 | Invalid DATAPOS: the specified offset for one or more tiles does not point to a position where a Begin Tile parameter | ❓ |
| IOCA-5-204 | Function Sets 14, 45, and 48 are currently the only function sets that include transparency masks. For more information on function sets, see “Function Sets”. | ❓ |
| IOCA-5-205 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-206 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-207 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-208 | Table 5. Transparency Mask Structure | ❓ |
| IOCA-5-209 | X'8E' | Begin Transparency Mask parameter | ❓ |
| IOCA-5-210 | X'94' | Image Size parameter | ❓ |
| IOCA-5-211 | [ X'95' ] | [ Image Encoding parameter ] | ❓ |
| IOCA-5-212 | X'FE92' | Image Data (S) | ❓ |
| IOCA-5-213 | X'8F' | End Transparency Mask parameter | ❓ |
| IOCA-5-214 | Begin Transparency Mask | ❓ |
| IOCA-5-215 | Image Size | ❓ |
| IOCA-5-216 | Image Encoding | ❓ |
| IOCA-5-217 | Image Data | ❓ |
| IOCA-5-218 | End Transparency Mask | ❓ |
| IOCA-5-219 | 1. All recording algorithms and compression algorithms allowed for bilevel images in the IOCA Function Set specified for the image can be used. If the datastream does not specify the function set, any architecturally valid Image Encoding parameter values can be used, except Solid Fill Rectangle. The Solid Fill Rectangle algorithm is not needed, since omitting the transparency mask achieves the same effect as setting all the transparency mask image points to 1. Completely removing the image achieves the same effect as setting all transparency mask image points to 0. | ❓ |
| IOCA-5-220 | 2. If the Image Encoding parameter is missing, the default encoding (no compression and RIDIC) applies. | ❓ |
| IOCA-5-221 | The Begin Transparency Mask defines the beginning of the transparency mask. | ❓ |
| IOCA-5-222 | 0 | CODE | ID | | X'8E' Begin Transparency Mask parameter M 1 UBIN LENGTH X'00' Length of the parameters to follow | M | ❓ |
| IOCA-5-223 | EC-8E0F Invalid sequence Condition: A Begin Transparency Mask has appeared out of sequence or more than once within a tile or a non-tiled image. | ❓ |
| IOCA-5-224 | The End Transparency Mask defines the end of a transparency mask. | ❓ |
| IOCA-5-225 | 0 | CODE | ID | | X'8F' End Transparency Mask parameter M 1 UBIN LENGTH X'00' Length of the parameters to follow | M | ❓ |
| IOCA-5-226 | EC-8F0F Invalid sequence Condition: An End Transparency Mask is missing after a Begin Transparency Mask has been encountered, or it appeared out of sequence. | ❓ |
| IOCA-5-227 | This section describes the parameters used to carry the Image Data Elements. | ❓ |
| IOCA-5-228 | IDE Size Parameter Band Image Parameter IDE Structure Parameter Tile Set Color Parameter Include Tile Parameter Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-229 | nColor Names Parameter nColor Names Parameter nColor Names Parameter | ❓ |
| IOCA-5-230 | Image Size Parameter Image Encoding Parameter | ❓ |
| IOCA-5-231 | The Image Data is an element of the Image Content, and is a set of IDEs. Multiple Image Data self-defining fields of the same type can be contained in a single untiled Image Content, a single tile, or a single transparency mask. | ❓ |
| IOCA-5-232 | 0–1 | CODE | ID | | X'FE92' Image Data parameter M 2–3 UBIN LENGTH X'0001' – X'FFFF' Length of the image data to follow M 4–n DATA Any Image Data Elements. The data in this self-defining field is recorded, compressed, and ordered as specified by the Image Encoding parameter. For some function sets, the data can also be subsampled as described by the Image Subsampling parameter. | M | ❓ |
| IOCA-5-233 | EC-920F Invalid sequence Condition: Image Data is missing, or it appeared out of sequence. | ❓ |
| IOCA-5-234 | If the data for a particular band exceeds the length of a single Band Image Data, the remaining data is contained in one or more Band Image Data parameters having the same band number, and following in sequence. | ❓ |
| IOCA-5-235 | 0–1 | CODE | ID | | X'FE9C' Band Image Data parameter M 2–3 UBIN LENGTH X'0003' – X'FFFF' Length of the parameters to follow | M | ❓ |
| IOCA-5-236 | 4 | CODE | BANDNUM | | X'01' – X'FD' Band number M 5–6 X'0000' Reserved; should be zero M 7–n DATA Any Image Data Elements for the band. The data in this self- defining field is recorded, compressed, and ordered as specified by the Image Encoding parameter. For some function sets, the data can also be subsampled as described by the Image Subsampling parameter. ONotes: 1. If the Band Image Data contains no data (the length is X'0003') for a certain band, all the uncompressed image data elements in the band are zero. For such a band, only a single Band Image Data parameter can appear; otherwise exception EC-9C0F occurs. 2. If the color space of the image is CMYK and the bands 1, 2, and 3 (cyan, magenta, and yellow) contain no data, the receivers should color-manage the image as | monochrome. | ❓ |
| IOCA-5-237 | EC-9C0F Invalid sequence Condition: Band Image Data is missing, or it appeared out of sequence. | ❓ |
| IOCA-5-238 | EC-9C17 Invalid number/sequence of Band Image Data Condition: The band numbers of a band image do not appear for the number of bands defined in the Band Image parameter, or do not appear in succeeding order. | ❓ |
| IOCA-6-001 | Exception conditions causing the common standard action | ❓ |
| IOCA-6-002 | Exception conditions causing unique standard actions | ❓ |
| IOCA-6-003 | The table in “Mandatory or Optional Exception Conditions” summarizes for each IOCA exception condition whether it is mandatory or optional. Also shown in the table is whether the two primary IOCA controlling environments–MO:DCA and IPDS–would consider the exception condition to be mandatory or optional. | ❓ |
| IOCA-6-004 | Syntax | ❓ |
| IOCA-6-005 | Parameter value | ❓ |
| IOCA-6-006 | Self-defining field sequence | ❓ |
| IOCA-6-007 | Those that cause the common standard action | ❓ |
| IOCA-6-008 | Those that cause unique standard actions | ❓ |
| IOCA-6-009 | Exception conditions that cause the common standard action | ❓ |
| IOCA-6-010 | Exception conditions that cause unique standard actions | ❓ |
| IOCA-6-011 | If the action is not defined in the corresponding section, the rest of the Image Segment is not processed and the exception condition is reported immediately to the controlling environment. | ❓ |
| IOCA-6-012 | An exception action defined by IOCA | ❓ |
| IOCA-6-013 | An alternative action that is defined outside the IOCA Process Model | ❓ |
| IOCA-6-014 | Notifies the controlling environment of the condition | ❓ |
| IOCA-6-015 | Performs the defined unique action | ❓ |
| IOCA-6-016 | Notifies the controlling environment of the condition | ❓ |
| IOCA-6-017 | Does not process the rest of the Image Segment | ❓ |
| IOCA-6-018 | The exception conditions are reset after the controlling environment has been notified. | ❓ |
| IOCA-6-019 | EC-700F for Begin Segment | ❓ |
| IOCA-6-020 | EC-710F for End Segment (see Note below) | ❓ |
| IOCA-6-021 | EC-8C0F for Begin Tile | ❓ |
| IOCA-6-022 | EC-8D0F for End Tile | ❓ |
| IOCA-6-023 | EC-8E0F for Begin Transparency Mask | ❓ |
| IOCA-6-024 | EC-8F0F for End Transparency Mask | ❓ |
| IOCA-6-025 | EC-910F for Begin Image Content | ❓ |
| IOCA-6-026 | EC-920F for Image Data (see Note below) | ❓ |
| IOCA-6-027 | EC-930F for End Image Content | ❓ |
| IOCA-6-028 | EC-940F for Image Size | ❓ |
| IOCA-6-029 | EC-950F for Image Encoding | ❓ |
| IOCA-6-030 | EC-960F for IDE Size | ❓ |
| IOCA-6-031 | EC-970F (Retired) | ❓ |
| IOCA-6-032 | EC-980F for Band Image | ❓ |
| IOCA-6-033 | EC-9B0F for IDE Structure | ❓ |
| IOCA-6-034 | EC-9C0F for Band Image Data (see Note below) | ❓ |
| IOCA-6-035 | EC-9F0F for External Algorithm Specification | ❓ |
| IOCA-6-036 | EC-B30F for nColor Names | ❓ |
| IOCA-6-037 | EC-B50F for Tile Position | ❓ |
| IOCA-6-038 | EC-B60F for Tile Size | ❓ |
| IOCA-6-039 | EC-B70F for Tile Set Color | ❓ |
| IOCA-6-040 | EC-B80F for Include Tile | ❓ |
| IOCA-6-041 | EC-BB0F for Tile TOC | ❓ |
| IOCA-6-042 | EC-CE0F for Image Subsampling | ❓ |
| IOCA-6-043 | EC-9410 for Image Size | ❓ |
| IOCA-6-044 | EC-9510 for Image Encoding | ❓ |
| IOCA-6-045 | EC-9610 for IDE Size | ❓ |
| IOCA-6-046 | EC-9710 (Retired) | ❓ |
| IOCA-6-047 | EC-9810 for Band Image | ❓ |
| IOCA-6-048 | EC-9B10 for IDE Structure | ❓ |
| IOCA-6-049 | EC-9F10 for External Algorithm Specification | ❓ |
| IOCA-6-050 | EC-B310 for nColor Names | ❓ |
| IOCA-6-051 | EC-B510 for Tile Position | ❓ |
| IOCA-6-052 | EC-B610 for Tile Size | ❓ |
| IOCA-6-053 | EC-B710 for Tile Set Color | ❓ |
| IOCA-6-054 | EC-BB10 for Tile TOC | ❓ |
| IOCA-6-055 | EC-CE10 for Image Subsampling | ❓ |
| IOCA-6-056 | EC-9411 for Image Size | ❓ |
| IOCA-6-057 | EC-9611 for IDE Size | ❓ |
| IOCA-6-058 | EC-9F11 for External Algorithm Specification | ❓ |
| IOCA-6-059 | EC-B311 for nColor Names | ❓ |
| IOCA-6-060 | EC-B511 for Tile Position | ❓ |
| IOCA-6-061 | EC-B611 for Tile Size | ❓ |
| IOCA-6-062 | EC-B711 for Tile Set Color | ❓ |
| IOCA-6-063 | EC-B811 for Include Tile | ❓ |
| IOCA-6-064 | EC-BB11 for Tile TOC | ❓ |
| IOCA-6-065 | The sum of the SIZE values does not match the IDE size specified by the IDE Size parameter. | ❓ |
| IOCA-6-066 | The color model is CMYK and SIZE4 is missing. | ❓ |
| IOCA-6-067 | SIZE4 is present and the color model is not CMYK or nColor. | ❓ |
| IOCA-6-068 | More than four SIZE parameters are present and the color model is not nColor. | ❓ |
| IOCA-6-069 | The color model is nColor and the number of SIZE parameters is not equal to the second half of the FORMAT byte. | ❓ |
| IOCA-6-070 | The image data is not encoded according to the compression or recording algorithm specified in the Image Encoding | ❓ |
| IOCA-6-071 | The image data cannot be decoded successfully using the size values specified in the Image Size parameter. This | ❓ |
| IOCA-6-072 | The image data is not in complete accordance with the compression algorithm specified in the Image Encoding | ❓ |
| IOCA-6-073 | The image data is encoded using the algorithm specified in the Image Encoding parameter, but uses a function of the | ❓ |
| IOCA-6-074 | Mandatory or Optional Exception Conditions | ❓ |
| IOCA-7-001 | Describes the concept of IOCA function sets | ✅ |
| IOCA-7-002 | Lists the product compliance rules | ✅ |
| IOCA-7-003 | Defines IOCA Function Sets FS10, FS11, FS14, FS40, FS42, FS45, and FS48 | ✅ |
| IOCA-7-004 | Each function set has an identification. With that identification, products determine the level of support they must provide to generate or receive IOCA Image Objects. | ✅ |
| IOCA-7-005 | Table 6. Function Set Identification | ✅ |
| IOCA-7-006 | 0x | Stand-alone | ✅ |
| IOCA-7-007 | 1x | Carried by presentation-level data stream, non-tiled | FS10, FS11, FS14 | ✅ |
| IOCA-7-008 | 2x | Library/resource | FS20 (Retired) | ✅ |
| IOCA-7-009 | 3x | Reserved | ✅ |
| IOCA-7-010 | 4x | Carried by presentation-level data stream, tiled | FS40, FS42, FS45, FS48 | ✅ |
| IOCA-7-011 | Function Set 10 is intended for bilevel images. | ✅ |
| IOCA-7-012 | Function Set 11 covers bilevel, grayscale, and color images. | ✅ |
| IOCA-7-013 | Function Set 14 covers bilevel, grayscale, and color images that allow use of transparency masks. | ✅ |
| IOCA-7-014 | Function Set 40 covers tiled bilevel images. | ✅ |
| IOCA-7-015 | Function Set 42 covers tiled bilevel images and tiled CMYK images with one bit per spot (SIZE1=SIZE2= | ✅ |
| IOCA-7-016 | Function Set 45 carries tiled bilevel and CMYK images. CMYK images can be either one or eight bits per | ✅ |
| IOCA-7-017 | Function Set 48 carries tiled bilevel, CMYK, and nColor images. CMYK images can be either one or eight | ✅ |
| IOCA-7-018 | Function Set 14 is a superset of Function Set 11. Function Set 11 is a superset of Function Set 10. Function Set 48 is a superset of Function Set 45. Function Set 45 is a superset of Function Set 42. Function Set 42 is a superset of Function Set 40. There are no other relationships among the function sets. | ✅ |
| IOCA-7-019 | Products that generate IOCA Image Objects can only use the IOCA self-defining fields and parameter values | ✅ |
| IOCA-7-020 | Products that receive IOCA Image Objects must be capable of accepting any IOCA Image Object that | ✅ |
| IOCA-7-021 | The following sections show the self-defining fields that each function set contains and the acceptable values for each field. | ✅ |
| IOCA-7-022 | Function Set 10 describes bilevel images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS10 are defined as follows: | ✅ |
| IOCA-7-023 | Table 7. Function Set 10 Structure | ✅ |
| IOCA-7-024 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-025 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-026 | + X'94' | Image Size parameter | ✅ |
| IOCA-7-027 | + [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-028 | + [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-029 | + [ X'97' ] | [ Retired (Image LUT-ID parameter) ] | ✅ |
| IOCA-7-030 | X'FE92' | Image Data (S) | ✅ |
| IOCA-7-031 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-032 | X'71' | End Segment parameter | ✅ |
| IOCA-7-033 | The self-defining fields and values acceptable for FS10 are shown in the following table. | ✅ |
| IOCA-7-034 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-035 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-036 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-037 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-038 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-039 | Image Size | ID (1) | X'94' | ✅ |
| IOCA-7-040 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-041 | UNITBASE (1) | X'00' – X'02' | ✅ |
| IOCA-7-042 | HRESOL (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-043 | VRESOL (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-044 | HSIZE (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-045 | VSIZE (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-046 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-047 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-048 | COMPRID (1) | X'01', X'03', X'82' | X'01' IBM MMR<br>X'03' No compression<br>X'82' G4 MMR | ✅ |
| IOCA-7-049 | RECID (1) | X'01' | RIDIC | ✅ |
| IOCA-7-050 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-051 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-052 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-053 | Retired | RESERVED (3) | X'970100' | Retired Image LUT-ID parameter | ✅ |
| IOCA-7-054 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-055 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-056 | DATA | Any | IDEs (see Note) | ✅ |
| IOCA-7-057 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-058 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-059 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-060 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-061 | Note: IDE value 0 represents an insignificant image point, and 1 represents a significant image point. The controlling environment determines how to interpret each value. | ✅ |
| IOCA-7-062 | Function Set 11 is a superset of Function Set 10, and describes bilevel, grayscale, and color images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS11 are defined as follows: | ✅ |
| IOCA-7-063 | Table 8. Function Set 11 Structure | ✅ |
| IOCA-7-064 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-065 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-066 | + X'94' | Image Size parameter | ✅ |
| IOCA-7-067 | + [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-068 | + [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-069 | + [ X'97' ] | [ Retired (Image LUT-ID parameter) ] | ✅ |
| IOCA-7-070 | + [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-071 | + [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-072 | + [ X'9F' ] | [ External Algorithm Specification parameter ] | ✅ |
| IOCA-7-073 | + [ X'FECE' ] | [ Image Subsampling parameter ] | ✅ |
| IOCA-7-074 | Image Data or Band Image Data (S) | ✅ |
| IOCA-7-075 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-076 | X'71' | End Segment parameter | ✅ |
| IOCA-7-077 | Note: The External Algorithm Specification parameter is part of FS11, but in IOCA is no longer required for JPEG compression, as described in Note 2 in the description of the Image Encoding parameter. Thus, an FS11 receiver can ignore the External Algorithm Specification parameter if desired. | ✅ |
| IOCA-7-078 | The self-defining fields and values acceptable for FS11 are shown in the following table. | ✅ |
| IOCA-7-079 | Initial parameters:** | ✅ |
| IOCA-7-080 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-081 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-082 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-083 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-084 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-085 | Image Size | ID (1) | X'94' | ✅ |
| IOCA-7-086 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-087 | UNITBASE (1) | X'00' – X'02' | ✅ |
| IOCA-7-088 | HRESOL (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-089 | VRESOL (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-090 | HSIZE (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-091 | VSIZE (2) | X'0000' – X'7FFF' | ✅ |
| IOCA-7-092 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-093 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-094 | COMPRID (1) | X'01', X'03', X'08', X'0A', X'82', X'83' | X'01' IBM MMR (see Note 1)<br>X'03' No Compression<br>X'08' ABIC (see Note 1)<br>X'0A' Concatenated ABIC (see Note 2)<br>X'82' G4 MMR (see Note 1)<br>X'83' JPEG (see Note 3) | ✅ |
| IOCA-7-095 | RECID (1) | X'01' | RIDIC | ✅ |
| IOCA-7-096 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-097 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-098 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-099 | IDESZ (1) | X'01', X'04', X'08', X'18' | X'01' 1 bit/IDE<br>X'04' 4 bits/IDE<br>X'08' 8 bits/IDE<br>X'18' 24 bits/IDE | ✅ |
| IOCA-7-100 | External Algorithm Specification | ID (1) | X'9F' | ✅ |
| IOCA-7-101 | LENGTH (1) | X'0A' | ✅ |
| IOCA-7-102 | ALGTYPE (1) | X'10' | Compression algorithm specification | ✅ |
| IOCA-7-103 | RESERVED (1) | X'00' | Should be zero | ✅ |
| IOCA-7-104 | COMPRID (1) | X'83' | JPEG algorithms | ✅ |
| IOCA-7-105 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-106 | MARKER (1) | X'C0' – X'C2', X'C9' – X'CA' | **Non-differential Huffman coding:**<br>X'C0' Baseline DCT<br>X'C1' Extended sequential DCT<br>X'C2' Progressive DCT<br>**Non-differential arithmetic coding:**<br>X'C9' Extended sequential DCT<br>X'CA' Progressive DCT<br>See Note 3 | ✅ |
| IOCA-7-107 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-108 | 1. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit/IDE, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-109 | 2. Concatenated ABIC is applicable only to images whose IDE size is 4 or 8 bits/IDE, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-110 | 3. The JPEG baseline DCT-based algorithm is applicable only to images whose IDE size is 8 bits/IDE, while the other DCT-based algorithms are applicable only to images whose IDE size is 8 or 24 bits/IDE; otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-111 | Parameters used when IDESZ=1:** | ✅ |
| IOCA-7-112 | Retired | RESERVED (3) | X'970100' | Retired Image LUT-ID parameter | ✅ |
| IOCA-7-113 | Band Image (see General Note) | ID (1) | X'98' | ✅ |
| IOCA-7-114 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-115 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-116 | BITCNT (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-117 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-118 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-119 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-120 | FORMAT (1) | X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-121 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-122 | SIZE1 (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-123 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-124 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-125 | Image Subsampling (see General Note) | ID (2) | X'FECE' | ✅ |
| IOCA-7-126 | LENGTH (2) | X'0000', X'0004' | ✅ |
| IOCA-7-127 | ID (1) | X'01' | Sampling ratios | ✅ |
| IOCA-7-128 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-129 | HSAMPLE (1) | X'01' | ✅ |
| IOCA-7-130 | VSAMPLE (1) | X'01' | ✅ |
| IOCA-7-131 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-132 | Parameters used when IDESZ=4 or IDESZ=8:** | ✅ |
| IOCA-7-133 | Band Image (see General Note) | ID (1) | X'98' | ✅ |
| IOCA-7-134 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-135 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-136 | BITCNT (1) | X'04', X'08' | X'04' 4 bits/IDE<br>X'08' 8 bits/IDE | ✅ |
| IOCA-7-137 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-138 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-139 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0'–B'1' (see Note 1)<br>RESERVED B'000000' | ✅ |
| IOCA-7-140 | FORMAT (1) | X'02', X'12' | X'02' YCrCb (see Note 2)<br>X'12' YCbCr (see Note 2) | ✅ |
| IOCA-7-141 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-142 | SIZE1 (1) | X'04', X'08' | X'04' 4 bits/IDE<br>X'08' 8 bits/IDE | ✅ |
| IOCA-7-143 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-144 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-145 | Image Subsampling (see General Note) | ID (2) | X'FECE' | ✅ |
| IOCA-7-146 | LENGTH (2) | X'0000', X'0004' | ✅ |
| IOCA-7-147 | ID (1) | X'01' | Sampling ratios | ✅ |
| IOCA-7-148 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-149 | HSAMPLE (1) | X'01' | ✅ |
| IOCA-7-150 | VSAMPLE (1) | X'01' | ✅ |
| IOCA-7-151 | 1. Gray coding is valid only for the Concatenated ABIC algorithm, otherwise exception condition EC-9B10 is raised. | ✅ |
| IOCA-7-152 | 2. Grayscale images only. Grayscale IDEs are composed of the Y component only of the YCrCb or YCbCr color model. | ✅ |
| IOCA-7-153 | Parameters used when IDESZ=24:** | ✅ |
| IOCA-7-154 | Band Image (see General Note) | ID (1) | X'98' | ✅ |
| IOCA-7-155 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-156 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-157 | BITCNT (1) | X'18' | 24 bits/IDE | ✅ |
| IOCA-7-158 | or: | ✅ |
| IOCA-7-159 | Band Image (see General Note) | ID (1) | X'98' | ✅ |
| IOCA-7-160 | LENGTH (1) | X'04' | ✅ |
| IOCA-7-161 | BCOUNT (1) | X'03' | 3 bands: R,G,B or Y,Cr,Cb or Y,Cb,Cr | ✅ |
| IOCA-7-162 | BITCNT (1) | X'08' | 8 bits/IDE for R or Y band | ✅ |
| IOCA-7-163 | BITCNT (1) | X'08' | 8 bits/IDE for G or Cr or Cb band | ✅ |
| IOCA-7-164 | BITCNT (1) | X'08' | 8 bits/IDE for B or Cb or Cr band | ✅ |
| IOCA-7-165 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-166 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-167 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-168 | FORMAT (1) | X'01', X'02', X'12' | X'01' RGB<br>X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-169 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-170 | SIZE1 (1) | X'08' | 8 bits for R or Y component | ✅ |
| IOCA-7-171 | SIZE2 (1) | X'08' | 8 bits for G or Cr or Cb component | ✅ |
| IOCA-7-172 | SIZE3 (1) | X'08' | 8 bits for B or Cb or Cr component | ✅ |
| IOCA-7-173 | Image Subsampling (see General Note) | ID (2) | X'FECE' | ✅ |
| IOCA-7-174 | LENGTH (2) | X'0000', X'0004', X'0006', X'0008' | ✅ |
| IOCA-7-175 | ID (1) | X'01' | Sampling ratios | ✅ |
| IOCA-7-176 | LENGTH (1) | X'02', X'04', X'06' | ✅ |
| IOCA-7-177 | HSAMPLE1 (1) | X'01' – X'02' | X'02' for YCrCb/YCbCr only | ✅ |
| IOCA-7-178 | VSAMPLE1 (1) | X'01' | ✅ |
| IOCA-7-179 | HSAMPLE2 (1) | X'01' | ✅ |
| IOCA-7-180 | VSAMPLE2 (1) | X'01' | ✅ |
| IOCA-7-181 | HSAMPLE3 (1) | X'01' | ✅ |
| IOCA-7-182 | VSAMPLE3 (1) | X'01' | ✅ |
| IOCA-7-183 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-184 | Final parameters:** | ✅ |
| IOCA-7-185 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-186 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-187 | DATA | Any | IDEs | ✅ |
| IOCA-7-188 | or: | ✅ |
| IOCA-7-189 | Band Image Data (BCOUNT=1) | ID (2) | X'FE9C' | ✅ |
| IOCA-7-190 | LENGTH (2) | X'0004' – X'FFFF' | ✅ |
| IOCA-7-191 | BANDNUM (1) | X'01' | One band | ✅ |
| IOCA-7-192 | RESERVED (2) | X'0000' | Should be zero | ✅ |
| IOCA-7-193 | DATA | Any | IDEs | ✅ |
| IOCA-7-194 | or: | ✅ |
| IOCA-7-195 | Band Image Data (BCOUNT=3) | ID (2) | X'FE9C' | ✅ |
| IOCA-7-196 | LENGTH (2) | X'0004' – X'FFFF' | ✅ |
| IOCA-7-197 | BANDNUM (1) | X'01' | Band with R or Y component | ✅ |
| IOCA-7-198 | RESERVED (2) | X'0000' | Should be zero | ✅ |
| IOCA-7-199 | DATA | Any | R or Y component | ✅ |
| IOCA-7-200 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-201 | LENGTH (2) | X'0004' – X'FFFF' | ✅ |
| IOCA-7-202 | BANDNUM (1) | X'02' | Band with G, Cr, or Cb component | ✅ |
| IOCA-7-203 | RESERVED (2) | X'0000' | Should be zero | ✅ |
| IOCA-7-204 | DATA | Any | G, Cr, or Cb component | ✅ |
| IOCA-7-205 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-206 | LENGTH (2) | X'0004' – X'FFFF' | ✅ |
| IOCA-7-207 | BANDNUM (1) | X'03' | Band with B, Cb, or Cr component | ✅ |
| IOCA-7-208 | RESERVED (2) | X'0000' | Should be zero | ✅ |
| IOCA-7-209 | DATA | Any | B, Cb, or Cr component | ✅ |
| IOCA-7-210 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-211 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-212 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-213 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-214 | General note: In this function set, the Image Subsampling parameter and the Band Image parameter cannot coexist within the same Image Content; otherwise exception condition EC-9801 or EC-CE01 is raised. | ✅ |
| IOCA-7-215 | Function Set 14 is a superset of Function Set 10 and Function Set 11, and describes bilevel, grayscale, and color images that allow use of transparency masks, as well as some additional compression algorithm options. | ✅ |
| IOCA-7-216 | This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS14 are defined as follows: | ✅ |
| IOCA-7-217 | X'70' | Begin Segment | ✅ |
| IOCA-7-218 | X'91' | Begin Image Content | ✅ |
| IOCA-7-219 | X'94' | Image Size | ✅ |
| IOCA-7-220 | [ X'95' ] | Image Encoding | ✅ |
| IOCA-7-221 | [ X'96' ] | IDE Size | ✅ |
| IOCA-7-222 | [ X'97' ] | Retired (Image LUT-ID parameter) (ignored) | ✅ |
| IOCA-7-223 | [ X'98' ] | Band Image | ✅ |
| IOCA-7-224 | [ X'9B' ] | IDE Structure | ✅ |
| IOCA-7-225 | [ X'9F' ] | External Algorithm Specification (ignored) | ✅ |
| IOCA-7-226 | [ X'FECE' ] | Image Subsampling | ✅ |
| IOCA-7-227 | [ Transparency Mask ] | See Table 10 | ✅ |
| IOCA-7-228 | Image Data or Band Image Data (S) | ✅ |
| IOCA-7-229 | X'93' | End Image Content | ✅ |
| IOCA-7-230 | X'71' | End Segment | ✅ |
| IOCA-7-231 | Self-Defining Field | Description | ✅ |
| IOCA-7-232 | X'8E' | Begin Transparency Mask | ✅ |
| IOCA-7-233 | X'94' | Image Size | ✅ |
| IOCA-7-234 | [ X'95' ] | Image Encoding | ✅ |
| IOCA-7-235 | X'FE92' | Image Data | ✅ |
| IOCA-7-236 | X'8F' | End Transparency Mask | ✅ |
| IOCA-7-237 | 1. The Image LUT-ID parameter has been retired and is thus not used in FS14. However, to keep FS14 a superset of FS11, the parameter will be allowed, but ignored. | ✅ |
| IOCA-7-238 | 2. The External Algorithm Specification parameter is not needed in FS14, as there are no restrictions on which codings can be used in the JPEG compression. The parameter is thus allowed, but ignored, as in FS45. | ✅ |
| IOCA-7-239 | The self-defining fields and values acceptable for FS14 are shown in the following table. | ✅ |
| IOCA-7-240 | Initial parameters:** | ✅ |
| IOCA-7-241 | Begin Segment | X'70' (0) | ✅ |
| IOCA-7-242 | Begin Image Content | X'91' (1) | OBJTYPE X'FF' | IOCA | ✅ |
| IOCA-7-243 | Image Size | X'94' (9) | UNITBASE X'00'–X'02' | ✅ |
| IOCA-7-244 | HRESOL (2) X'0000'–X'7FFF' | ✅ |
| IOCA-7-245 | VRESOL (2) X'0000'–X'7FFF' | ✅ |
| IOCA-7-246 | HSIZE (2) X'0000'–X'7FFF' | ✅ |
| IOCA-7-247 | VSIZE (2) X'0000'–X'7FFF' | ✅ |
| IOCA-7-248 | Image Encoding | X'95' (2–3) | COMPRID X'01', X'03', X'08', X'0A', X'0D', X'0E', X'82', X'83' | X'01' IBM MMR (see Note 1)<br>X'03' No Compression<br>X'08' ABIC (see Note 1)<br>X'0A' Concatenated ABIC (see Note 2)<br>X'0D' TIFF LZW<br>X'0E' TIFF LZW with Diff Predictor<br>X'82' G4 MMR (see Note 1)<br>X'83' JPEG algorithms (see Note 3) | ✅ |
| IOCA-7-249 | RECID (1) X'01' | RIDIC | ✅ |
| IOCA-7-250 | BITORDR (1) X'00'–X'01' | X'00' Left to right<br>X'01' Right to left | ✅ |
| IOCA-7-251 | IDE Size | X'96' (1) | IDESZ (1) X'01', X'04', X'08', X'18' | X'01' 1 bit/IDE<br>X'04' 4 bits/IDE<br>X'08' 8 bits/IDE<br>X'18' 24 bits/IDE | ✅ |
| IOCA-7-252 | Parameters used when IDESZ=1:** | ✅ |
| IOCA-7-253 | Retired | X'97' (1) | X'00' | Retired Image LUT-ID parameter | ✅ |
| IOCA-7-254 | Band Image | X'98' (2) | BCOUNT (1) X'01' | One band | ✅ |
| IOCA-7-255 | BITCNT (1) X'01' | 1 bit/IDE | ✅ |
| IOCA-7-256 | IDE Structure | X'9B' (6–8) | FLAGS (1) | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-257 | FORMAT (1) X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-258 | RESERVED (3) X'000000' | ✅ |
| IOCA-7-259 | SIZE1 (1) X'01' | 1 bit/IDE | ✅ |
| IOCA-7-260 | SIZE2 (1) X'00' | 0 bits/IDE | ✅ |
| IOCA-7-261 | SIZE3 (1) X'00' | 0 bits/IDE | ✅ |
| IOCA-7-262 | Image Subsampling | X'FECE' (2, 4) | ID (1) X'01' | Sampling ratios | ✅ |
| IOCA-7-263 | LENGTH (1) X'02' | ✅ |
| IOCA-7-264 | HSAMPLE (1) X'01' | ✅ |
| IOCA-7-265 | VSAMPLE (1) X'01' | ✅ |
| IOCA-7-266 | Parameters used when IDESZ=4 or IDESZ=8:** | ✅ |
| IOCA-7-267 | Band Image | X'98' (2) | BCOUNT (1) X'01' | One band | ✅ |
| IOCA-7-268 | BITCNT (1) X'04', X'08' | X'04' 4 bits/IDE<br>X'08' 8 bits/IDE | ✅ |
| IOCA-7-269 | IDE Structure | X'9B' (6–8) | FLAGS (1) | ASFLAG B'0' Additive<br>GRAYCODE B'0'–B'1' (Note 1)<br>RESERVED B'000000' | ✅ |
| IOCA-7-270 | FORMAT (1) X'02', X'12' | X'02' YCrCb (Note 2)<br>X'12' YCbCr (Note 2) | ✅ |
| IOCA-7-271 | RESERVED (3) X'000000' | ✅ |
| IOCA-7-272 | SIZE1 (1) X'04', X'08' | X'04' 4 bits/IDE<br>X'08' 8 bits/IDE | ✅ |
| IOCA-7-273 | SIZE2 (1) X'00' | 0 bits/IDE | ✅ |
| IOCA-7-274 | SIZE3 (1) X'00' | 0 bits/IDE | ✅ |
| IOCA-7-275 | Image Subsampling | X'FECE' (2, 4) | ID (1) X'01' | Sampling ratios | ✅ |
| IOCA-7-276 | LENGTH (1) X'02' | ✅ |
| IOCA-7-277 | HSAMPLE (1) X'01' | ✅ |
| IOCA-7-278 | VSAMPLE (1) X'01' | ✅ |
| IOCA-7-279 | Parameters used when IDESZ=24:** | ✅ |
| IOCA-7-280 | Band Image | X'98' (2) | BCOUNT (1) X'01' | One band | ✅ |
| IOCA-7-281 | BITCNT (1) X'18' | 24 bits/IDE | ✅ |
| IOCA-7-282 | or:* | ✅ |
| IOCA-7-283 | Band Image | X'98' (4) | BCOUNT (1) X'03' | 3 bands: R,G,B or Y,Cr,Cb or Y,Cb,Cr | ✅ |
| IOCA-7-284 | BITCNT (1) X'08' | 8 bits/IDE for R or Y band | ✅ |
| IOCA-7-285 | BITCNT (1) X'08' | 8 bits/IDE for G or Cr or Cb band | ✅ |
| IOCA-7-286 | BITCNT (1) X'08' | 8 bits/IDE for B or Cb or Cr band | ✅ |
| IOCA-7-287 | IDE Structure | X'9B' (8) | FLAGS (1) | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-288 | FORMAT (1) X'01', X'02', X'12' | X'01' RGB<br>X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-289 | RESERVED (3) X'000000' | ✅ |
| IOCA-7-290 | SIZE1 (1) X'08' | 8 bits for R or Y | ✅ |
| IOCA-7-291 | SIZE2 (1) X'08' | 8 bits for G/Cr/Cb | ✅ |
| IOCA-7-292 | SIZE3 (1) X'08' | 8 bits for B/Cb/Cr | ✅ |
| IOCA-7-293 | Image Subsampling | X'FECE' (2–8) | ID (1) X'01' | Sampling ratios | ✅ |
| IOCA-7-294 | LENGTH (1) X'02', X'04', X'06' | ✅ |
| IOCA-7-295 | HSAMPLE1 (1) X'01'–X'02' | X'02' for YCrCb/YCbCr only | ✅ |
| IOCA-7-296 | VSAMPLE1 (1) X'01' | ✅ |
| IOCA-7-297 | HSAMPLE2 (1) X'01' | ✅ |
| IOCA-7-298 | VSAMPLE2 (1) X'01' | ✅ |
| IOCA-7-299 | HSAMPLE3 (1) X'01' | ✅ |
| IOCA-7-300 | VSAMPLE3 (1) X'01' | ✅ |
| IOCA-7-301 | Final parameters:** | ✅ |
| IOCA-7-302 | Begin Transparency Mask | X'8E' (0) | ✅ |
| IOCA-7-303 | Image Size | X'94' (9) | UNITBASE X'00'–X'01' | X'00' 10 in; X'01' 10 cm | ✅ |
| IOCA-7-304 | HRESOL (2) X'0001'–X'7FFF' | ✅ |
| IOCA-7-305 | VRESOL (2) X'0001'–X'7FFF' | ✅ |
| IOCA-7-306 | HSIZE (2) X'0001'–X'7FFF' | ✅ |
| IOCA-7-307 | VSIZE (2) X'0001'–X'7FFF' | ✅ |
| IOCA-7-308 | Image Encoding | X'95' (2–3) | COMPRID X'01', X'03', X'08', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'82' G4 MMR | ✅ |
| IOCA-7-309 | RECID (1) X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-310 | BITORDR (1) X'00', X'01' | X'00' Left to right<br>X'01' Right to left | ✅ |
| IOCA-7-311 | Image Data | X'FE92' (1–FFFF) | DATA | Any IDEs (bilevel only) | ✅ |
| IOCA-7-312 | End Transparency Mask | X'8F' (0) | ✅ |
| IOCA-7-313 | Image Data | X'FE92' (1–FFFF) | DATA | Any IDEs | ✅ |
| IOCA-7-314 | or:* | ✅ |
| IOCA-7-315 | Band Image Data (BCOUNT=1) | X'FE9C' (4–FFFF) | BANDNUM (1) X'01' | One band | ✅ |
| IOCA-7-316 | RESERVED (2) X'0000' | ✅ |
| IOCA-7-317 | DATA | Any IDEs | ✅ |
| IOCA-7-318 | or:* | ✅ |
| IOCA-7-319 | Band Image Data (BCOUNT=3) | X'FE9C' (4–FFFF) | BANDNUM (1) X'01' | Band with R or Y component | ✅ |
| IOCA-7-320 | RESERVED (2) X'0000' | ✅ |
| IOCA-7-321 | DATA | Any R or Y component | ✅ |
| IOCA-7-322 | BANDNUM (1) X'02' | Band with G/Cr/Cb component | ✅ |
| IOCA-7-323 | RESERVED (2) X'0000' | ✅ |
| IOCA-7-324 | DATA | Any G/Cr/Cb component | ✅ |
| IOCA-7-325 | BANDNUM (1) X'03' | Band with B/Cb/Cr component | ✅ |
| IOCA-7-326 | RESERVED (2) X'0000' | ✅ |
| IOCA-7-327 | DATA | Any B/Cb/Cr component | ✅ |
| IOCA-7-328 | End Image Content | X'93' (0) | ✅ |
| IOCA-7-329 | End Segment | X'71' (0) | ✅ |
| IOCA-7-330 | 1. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit/IDE, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-331 | 2. Concatenated ABIC is applicable only to images whose IDE size is 4 or 8 bits/IDE, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-332 | 3. The JPEG algorithms are applicable only to images whose IDE size is 8 or 24 bits/IDE; otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-333 | 1. Gray coding is valid only for the Concatenated ABIC algorithm, otherwise exception condition EC-9B10 is raised. | ✅ |
| IOCA-7-334 | 2. Grayscale images only. Grayscale IDEs are composed of the Y component only of the YCrCb or YCbCr color model. | ✅ |
| IOCA-7-335 | General note:** In this function set, the Image Subsampling parameter and the Band Image parameter cannot coexist within the same Image Content; otherwise exception condition EC-9801 or EC-CE01 is raised. | ✅ |
| IOCA-7-336 | Function Set 40 is a subset of Function Set 42, Function Set 45, and Function Set 48. It describes tiled images with one bit per spot (color space YCbCr or YCrCb, IDESZ=1). This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS40 are defined as follows: | ✅ |
| IOCA-7-337 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-338 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-339 | X'FEBB' | Tile TOC parameter | ✅ |
| IOCA-7-340 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-341 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-342 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-343 | [ Tiles (S) ] | ✅ |
| IOCA-7-344 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-345 | X'71' | End Segment parameter | ✅ |
| IOCA-7-346 | Code | Name | ✅ |
| IOCA-7-347 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-348 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-349 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-350 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-351 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-352 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-353 | X'FE92' | [ Image Data (S) ] | ✅ |
| IOCA-7-354 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-355 | 1. Note that the parameters in the above diagram must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS40 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised. | ✅ |
| IOCA-7-356 | 2. In the context of FS40, the Image Size parameter, Image Subsampling parameter, and External Algorithm Specification parameter cause the EC-0001 exception (Invalid parameter) to occur. If the first parameter after the Begin Image Content parameter is not the Tile TOC parameter, the image is not a tiled image and any of the tile-specific parameters (Tile TOC parameter, Begin Tile parameter, etc.) cause EC-0001 to occur. | ✅ |
| IOCA-7-357 | 3. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. The specification within a tile takes precedence over a specification outside of the tile. | ✅ |
| IOCA-7-358 | 4. If the IDE Size parameter is not present, the default IDE size is one bit per pel (bilevel image). | ✅ |
| IOCA-7-359 | 5. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero. | ✅ |
| IOCA-7-360 | The self-defining fields and values acceptable for FS40 are shown in the following table. | ✅ |
| IOCA-7-361 | Initial parameters in Function Set 40:** | ✅ |
| IOCA-7-362 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-363 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-364 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-365 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-366 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-367 | Tile TOC | ID (2) | X'FEBB' | ✅ |
| IOCA-7-368 | LENGTH (2) | X'0002' – X'7FFF' | ✅ |
| IOCA-7-369 | RESERVED (2) | X'0000' | Reserved; should be set to zero | ✅ |
| IOCA-7-370 | Either zero repeating groups, or one per tile in the following format: | ✅ |
| IOCA-7-371 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-372 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-373 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-374 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-375 | RELRES (1) | X'01' | Relative resolution | ✅ |
| IOCA-7-376 | COMPR (1) | | Compression algorithm | ✅ |
| IOCA-7-377 | DATAPOS (8) | | File offset to the beginning of the tile | ✅ |
| IOCA-7-378 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-379 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-380 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR-Modified Modified Read (see General Note)<br>X'03' No Compression<br>X'08' ABIC (Bilevel Q-Coder) (see General Note)<br>X'82' G4 MMR-Modified Modified READ (see General Note) | ✅ |
| IOCA-7-381 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-382 | BITORDR (1) | X'00' – X'01' | X'00' Bit order within each image data byte is from left to right<br>X'01' Bit order within each image data byte is from right to left | ✅ |
| IOCA-7-383 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-384 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-385 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-386 | Initial parameters in a tile:** | ✅ |
| IOCA-7-387 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-388 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-389 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-390 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-391 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-392 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-393 | Tile Size | ID (1) | X'B6' | ✅ |
| IOCA-7-394 | LENGTH (1) | X'08' – X'09' | ✅ |
| IOCA-7-395 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-396 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-397 | RELRES (1) | X'01' | Relative resolution | ✅ |
| IOCA-7-398 | Tile parameters:** | ✅ |
| IOCA-7-399 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-400 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-401 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR-Modified Modified Read (see General Note)<br>X'03' No Compression<br>X'08' ABIC (Bilevel Q-Coder)<br>X'82' G4 MMR-Modified Modified READ (see General Note) | ✅ |
| IOCA-7-402 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-403 | BITORDR (1) | X'00' – X'01' | X'00' Bit order within each image data byte is from left to right<br>X'01' Bit order within each image data byte is from right to left | ✅ |
| IOCA-7-404 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-405 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-406 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-407 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-408 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-409 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' Should be zero | ✅ |
| IOCA-7-410 | FORMAT (1) | X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-411 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-412 | SIZE1 (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-413 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-414 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-415 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-416 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-417 | DATA | Any | IDEs | ✅ |
| IOCA-7-418 | End Tile | ID (1) | X'8D' | ✅ |
| IOCA-7-419 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-420 | Final parameters in Function Set 40:** | ✅ |
| IOCA-7-421 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-422 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-423 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-424 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-425 | General note:** ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit per band, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-426 | Function Set 42 is a superset of Function Set 40 and a subset of Function Set 45 and Function Set 48. It describes tiled images with one bit per spot. Images can be either bilevel (color space YCbCr or YCrCb, IDESZ=1) or color (color space CMYK, IDESZ=4). This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS42 are defined as follows: | ✅ |
| IOCA-7-427 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-428 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-429 | X'FEBB' | Tile TOC parameter | ✅ |
| IOCA-7-430 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-431 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-432 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-433 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-434 | [ Tiles (S) ] | ✅ |
| IOCA-7-435 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-436 | X'71' | End Segment parameter | ✅ |
| IOCA-7-437 | Code | Name | ✅ |
| IOCA-7-438 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-439 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-440 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-441 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-442 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-443 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-444 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-445 | [ X'B7' ] | [ Tile Set Color parameter ] | ✅ |
| IOCA-7-446 | [ Image Data or Band Image Data (S) ] | ✅ |
| IOCA-7-447 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-448 | 1. Note that the parameters in Table 13 and Table 14 must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS42 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised. | ✅ |
| IOCA-7-449 | 2. In the context of FS42, the Image Size parameter, Image Subsampling parameter, and External Algorithm Specification parameter cause the EC-0001 exception (Invalid parameter) to occur. If the first parameter after the Begin Image Content parameter is not the Tile TOC parameter, the image is not a tiled image and any of the tile-specific parameters (Tile TOC parameter, Begin Tile parameter, etc.) cause EC-0001 to occur. | ✅ |
| IOCA-7-450 | 3. If the IDE Size is not set to 1 bit or the color space is not YCbCr or YCrCb for a tile, and the Tile Set Color parameter is specified, exception EC-B711 occurs. | ✅ |
| IOCA-7-451 | 4. If the Solid Fill Rectangle compression algorithm is specified for a tile and Image Data or Band Image Data is encountered, exception EC-0001 occurs. | ✅ |
| IOCA-7-452 | 5. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. The specification within a tile takes precedence over a specification outside of the tile. | ✅ |
| IOCA-7-453 | 6. If the IDE Size parameter is not present, the default IDE size is one bit per pel (bilevel image). | ✅ |
| IOCA-7-454 | 7. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero. | ✅ |
| IOCA-7-455 | 8. If a tile contains the IDE Structure parameter specifying the CMYK color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present. | ✅ |
| IOCA-7-456 | 9. If the IDE Structure parameter specifying the CMYK color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying that the tile is bilevel. | ✅ |
| IOCA-7-457 | 10. CMYK tiles must carry the image data in Band Image Data. Bilevel tiles must carry the data in Image Data, unless the Solid Fill Rectangle compression algorithm is specified. | ✅ |
| IOCA-7-458 | 11. If a tile has Solid Fill Rectangle specified as the compression algorithm, the tile is painted using the color specified in the Tile Set Color parameter for that tile. If the Tile Set Color parameter has not been specified, the color given using the Set Bilevel Image Color field in the Image Data Descriptor is used. If the Set Bilevel Image Color field is missing, the device default is used. | ✅ |
| IOCA-7-459 | The self-defining fields and values acceptable for FS42 are shown in the following table. | ✅ |
| IOCA-7-460 | Initial parameters in Function Set 42:** | ✅ |
| IOCA-7-461 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-462 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-463 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-464 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-465 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-466 | Tile TOC | ID (2) | X'FEBB' | ✅ |
| IOCA-7-467 | LENGTH (2) | X'0002' – X'7FFF' | ✅ |
| IOCA-7-468 | RESERVED (2) | X'0000' | Reserved; should be set to zero | ✅ |
| IOCA-7-469 | Either zero repeating groups, or one per tile in the following format: | ✅ |
| IOCA-7-470 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-471 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-472 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-473 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-474 | RELRES (1) | X'01' | Relative resolution | ✅ |
| IOCA-7-475 | COMPR (1) | | Compression algorithm | ✅ |
| IOCA-7-476 | DATAPOS (8) | | File offset to the beginning of the tile | ✅ |
| IOCA-7-477 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-478 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-479 | COMPRID (1) | X'01', X'03', X'08', X'20', X'82' | X'01' IBM MMR-Modified Modified Read (see General Note)<br>X'03' No Compression<br>X'08' ABIC (Bilevel Q-Coder) (see General Note)<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR-Modified Modified READ (see General Note) | ✅ |
| IOCA-7-480 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-481 | BITORDR (1) | X'00' – X'01' | X'00' Bit order within each image data byte is from left to right<br>X'01' Bit order within each image data byte is from right to left | ✅ |
| IOCA-7-482 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-483 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-484 | IDESZ (1) | X'01', X'04' | X'01' 1 bit/IDE<br>X'04' 4 bits/IDE | ✅ |
| IOCA-7-485 | Initial parameters in a tile:** | ✅ |
| IOCA-7-486 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-487 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-488 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-489 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-490 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-491 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-492 | Tile Size | ID (1) | X'B6' | ✅ |
| IOCA-7-493 | LENGTH (1) | X'08' – X'09' | ✅ |
| IOCA-7-494 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-495 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-496 | RELRES (1) | X'01' | Relative resolution | ✅ |
| IOCA-7-497 | Tile parameters used when IDESZ=1:** | ✅ |
| IOCA-7-498 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-499 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-500 | COMPRID (1) | X'01', X'03', X'08', X'20', X'82' | X'01' IBM MMR-Modified Modified Read (see General Note)<br>X'03' No Compression<br>X'08' ABIC (Bilevel Q-Coder)<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR-Modified Modified READ (see General Note) | ✅ |
| IOCA-7-501 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-502 | BITORDR (1) | X'00' – X'01' | X'00' Bit order within each image data byte is from left to right<br>X'01' Bit order within each image data byte is from right to left | ✅ |
| IOCA-7-503 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-504 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-505 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-506 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-507 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-508 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-509 | BITCNT (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-510 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-511 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-512 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' Should be zero | ✅ |
| IOCA-7-513 | FORMAT (1) | X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-514 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-515 | SIZE1 (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-516 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-517 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-518 | Tile Set Color | ID (1) | X'B7' | ✅ |
| IOCA-7-519 | LENGTH (1) | X'0B', X'0C' | ✅ |
| IOCA-7-520 | CSPACE (1) | X'04', X'08' | X'04' CMYK<br>X'08' CIELab | ✅ |
| IOCA-7-521 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-522 | SIZE1–SIZE3 (1) | X'08' | Bits/IDE for components 1-3 | ✅ |
| IOCA-7-523 | SIZE4 (1) | X'00', X'08' | Bits/IDE for component 4 | ✅ |
| IOCA-7-524 | CVAL1–CVAL4 (1) | X'00' – X'FF' | Color values | ✅ |
| IOCA-7-525 | Tile parameters used when IDESZ=4:** | ✅ |
| IOCA-7-526 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-527 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-528 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR-Modified Modified Read (see General Note)<br>X'03' No Compression<br>X'08' ABIC (Bilevel Q-Coder)<br>X'82' G4 MMR-Modified Modified READ (see General Note) | ✅ |
| IOCA-7-529 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-530 | BITORDR (1) | X'00', X'01' | X'00' Bit order within each image data byte is from left to right<br>X'01' Bit order within each image data byte is from right to left | ✅ |
| IOCA-7-531 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-532 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-533 | IDESZ (1) | X'04' | 4 bits/IDE | ✅ |
| IOCA-7-534 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-535 | LENGTH (1) | X'05' | ✅ |
| IOCA-7-536 | BCOUNT (1) | X'04' | Four bands: CMYK | ✅ |
| IOCA-7-537 | BITCNT (1) | X'01' | 1 bit/IDE for C band | ✅ |
| IOCA-7-538 | BITCNT (1) | X'01' | 1 bit/IDE for M band | ✅ |
| IOCA-7-539 | BITCNT (1) | X'01' | 1 bit/IDE for Y band | ✅ |
| IOCA-7-540 | BITCNT (1) | X'01' | 1 bit/IDE for K band | ✅ |
| IOCA-7-541 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-542 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-543 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' Should be zero | ✅ |
| IOCA-7-544 | FORMAT (1) | X'04' | CMYK | ✅ |
| IOCA-7-545 | RESERVED (3) | X'000000' | Should be zero | ✅ |
| IOCA-7-546 | SIZE1 (1) | X'01' | 1 bit/IDE (C component) | ✅ |
| IOCA-7-547 | SIZE2 (1) | X'01' | 1 bit/IDE (M component) | ✅ |
| IOCA-7-548 | SIZE3 (1) | X'01' | 1 bit/IDE (Y component) | ✅ |
| IOCA-7-549 | SIZE4 (1) | X'01' | 1 bit/IDE (K component) | ✅ |
| IOCA-7-550 | Final parameters in a tile:** | ✅ |
| IOCA-7-551 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-552 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-553 | DATA | Any | IDEs | ✅ |
| IOCA-7-554 | or:* | ✅ |
| IOCA-7-555 | Band Image Data** | (BCOUNT=4 only) | ✅ |
| IOCA-7-556 | Four bands, in order by BANDNUM, in the following format: | ✅ |
| IOCA-7-557 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-558 | LENGTH (2) | X'0004' – X'FFFF' | ✅ |
| IOCA-7-559 | BANDNUM (1) | X'01' – X'04' | X'01' Band contains C component<br>X'02' Band contains M component<br>X'03' Band contains Y component<br>X'04' Band contains K component | ✅ |
| IOCA-7-560 | RESERVED (2) | X'0000' | Should be zero | ✅ |
| IOCA-7-561 | DATA | Any | ✅ |
| IOCA-7-562 | End Tile | ID (1) | X'8D' | ✅ |
| IOCA-7-563 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-564 | Final parameters in Function Set 42:** | ✅ |
| IOCA-7-565 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-566 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-567 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-568 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-569 | General note:** ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit per band, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-570 | Function Set 45 is a superset of Function Set 40 and Function Set 42 and a subset of Function Set 48. It describes bilevel or color tiled images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS45 are defined as follows: | ✅ |
| IOCA-7-571 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-572 | Image Content (S) | ✅ |
| IOCA-7-573 | X'71' | End Segment parameter | ✅ |
| IOCA-7-574 | Code | Name | ✅ |
| IOCA-7-575 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-576 | X'FEBB' | Tile TOC parameter | ✅ |
| IOCA-7-577 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-578 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-579 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-580 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-581 | [ Data and Referencing Tiles (S) ] | ✅ |
| IOCA-7-582 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-583 | Code | Name | ✅ |
| IOCA-7-584 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-585 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-586 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-587 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-588 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-589 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-590 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-591 | [ X'9F' ] | [ External Algorithm Specification parameter (ignored) ] | ✅ |
| IOCA-7-592 | [ X'B7' ] | [ Tile Set Color parameter ] | ✅ |
| IOCA-7-593 | [ Transparency Mask ] | ✅ |
| IOCA-7-594 | [ Image Data or Band Image Data (S) ] | ✅ |
| IOCA-7-595 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-596 | Code | Name | ✅ |
| IOCA-7-597 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-598 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-599 | X'FEB8' | Include Tile parameter | ✅ |
| IOCA-7-600 | [ Transparency Mask ] | ✅ |
| IOCA-7-601 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-602 | Code | Name | ✅ |
| IOCA-7-603 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-604 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-605 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-606 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-607 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-608 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-609 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-610 | [ X'9F' ] | [ External Algorithm Specification parameter (ignored) ] | ✅ |
| IOCA-7-611 | [ X'B7' ] | [ Tile Set Color parameter ] | ✅ |
| IOCA-7-612 | [ Transparency Mask ] | ✅ |
| IOCA-7-613 | [ Image Data or Band Image Data (S) ] | ✅ |
| IOCA-7-614 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-615 | Code | Name | ✅ |
| IOCA-7-616 | X'8E' | Begin Transparency Mask parameter | ✅ |
| IOCA-7-617 | X'94' | Image Size parameter | ✅ |
| IOCA-7-618 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-619 | X'FE92' | Image Data | ✅ |
| IOCA-7-620 | X'8F' | End Transparency Mask parameter | ✅ |
| IOCA-7-621 | 1. Note that the parameters in Table 15, Table 16, Table 17, Table 18, Table 19, and Table 20 must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS45 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised. | ✅ |
| IOCA-7-622 | 2. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. Note that tile data might require that some of these parameters be specified. | ✅ |
| IOCA-7-623 | 3. If the IDE Size parameter is not present neither in the tile nor in the image content, the default IDE size is one bit per pel (bilevel image). | ✅ |
| IOCA-7-624 | 4. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero. | ✅ |
| IOCA-7-625 | 5. If a tile contains the IDE Structure parameter specifying the CMYK color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present. | ✅ |
| IOCA-7-626 | 6. If the IDE Structure parameter specifying the CMYK color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying a different color space. | ✅ |
| IOCA-7-627 | 7. Receivers implementing FS45 must support at least 128 image contents in a single image segment. Otherwise, if a receiver encounters too many image contents to process, it should act as if it encountered too many image objects on a page. | ✅ |
| IOCA-7-628 | 8. Resource tiles included via the Include Tile parameter must not contain the Include Tile parameter or the exception EC-B811 exists. | ✅ |
| IOCA-7-629 | The self-defining fields and parameter values that are acceptable in Function Set 45 are shown in the following table. | ✅ |
| IOCA-7-630 | Initial parameters in FS45:** | ✅ |
| IOCA-7-631 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-632 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-633 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-634 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-635 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-636 | Tile TOC | ID (2) | X'FEBB' | ✅ |
| IOCA-7-637 | LENGTH (2) | X'0002' – X'7FFF' | ✅ |
| IOCA-7-638 | RESERVED (2) | X'0000' | Reserved; should be set to zero | ✅ |
| IOCA-7-639 | Either zero repeating groups, or one per tile in the following format: | ✅ |
| IOCA-7-640 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-641 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-642 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-643 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-644 | RELRES (1) | X'01' – X'02' | Relative resolution (see Note 1) | ✅ |
| IOCA-7-645 | COMPR (1) | | Compression algorithm | ✅ |
| IOCA-7-646 | DATAPOS (8) | | File offset to the beginning of the tile | ✅ |
| IOCA-7-647 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-648 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-649 | COMPRID (1) | X'01', X'03', X'08', X'0D', X'20', X'82' – X'83' | X'01' IBM MMR (see Note 2)<br>X'03' No Compression<br>X'08' ABIC (see Note 2)<br>X'0D' TIFF LZW<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR (see Note 2)<br>X'83' JPEG algorithms (see Note 3) | ✅ |
| IOCA-7-650 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-651 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-652 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-653 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-654 | IDESZ (1) | X'01', X'04', X'20' | X'01' 1 bit/IDE<br>X'04' 4 bits/IDE<br>X'20' 32 bits/IDE | ✅ |
| IOCA-7-655 | 1. In the Tile TOC parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data. Using RELRES of 1 for JPEG-compressed data and RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general. | ✅ |
| IOCA-7-656 | 2. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit/band, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-657 | 3. The JPEG algorithms are applicable only to images whose IDE size is 32 bits/IDE; otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-658 | Initial parameters in a data tile:** | ✅ |
| IOCA-7-659 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-660 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-661 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-662 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-663 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-664 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-665 | Tile Size | ID (1) | X'B6' | ✅ |
| IOCA-7-666 | LENGTH (1) | X'08' – X'09' | ✅ |
| IOCA-7-667 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-668 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-669 | RELRES (1) | X'01' – X'02' | Relative resolution (see Note) | ✅ |
| IOCA-7-670 | Implementation Note:** Some FS45 receivers support a RELRES of 1 for JPEG-compressed data, and do not raise exception EC-B610 for such data. Also note that in FS48, a RELRES of 1 for JPEG-compressed data is allowed. | ✅ |
| IOCA-7-671 | Initial parameters in a referencing tile:** | ✅ |
| IOCA-7-672 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-673 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-674 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-675 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-676 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-677 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-678 | Include Tile | ID (2) | X'FEB8' | ✅ |
| IOCA-7-679 | LENGTH (2) | X'0004' | ✅ |
| IOCA-7-680 | TIRID (4) | X'00000000' – X'FFFFFFFF' | Resource Tile local identifier | ✅ |
| IOCA-7-681 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-682 | Parameters used when IDESZ=1:** | ✅ |
| IOCA-7-683 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-684 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-685 | COMPRID (1) | X'01', X'03', X'08', X'20', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR | ✅ |
| IOCA-7-686 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-687 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-688 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-689 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-690 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-691 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-692 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-693 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-694 | BITCNT (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-695 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-696 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-697 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'00000' | ✅ |
| IOCA-7-698 | FORMAT (1) | X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-699 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-700 | SIZE1 (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-701 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-702 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-703 | Tile Set Color | ID (1) | X'B7' | ✅ |
| IOCA-7-704 | LENGTH (1) | X'0B', X'0C' | ✅ |
| IOCA-7-705 | CSPACE (1) | X'04', X'08' | X'04' CMYK<br>X'08' CIELab | ✅ |
| IOCA-7-706 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-707 | SIZE1–SIZE3 (1) | X'08' | Bits/IDE for components 1-3 | ✅ |
| IOCA-7-708 | SIZE4 (1) | X'00', X'08' | Bits/IDE for component 4 | ✅ |
| IOCA-7-709 | CVAL1–CVAL4 (1) | X'00' – X'FF' | Color values | ✅ |
| IOCA-7-710 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-711 | Parameters used when IDESZ=4:** | ✅ |
| IOCA-7-712 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-713 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-714 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'82' G4 MMR | ✅ |
| IOCA-7-715 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-716 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-717 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-718 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-719 | IDESZ (1) | X'04' | 4 bits/IDE | ✅ |
| IOCA-7-720 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-721 | LENGTH (1) | X'05' | ✅ |
| IOCA-7-722 | BCOUNT (1) | X'04' | Four bands: CMYK | ✅ |
| IOCA-7-723 | BITCNT (1) | X'01' | 1 bit/IDE for C band | ✅ |
| IOCA-7-724 | BITCNT (1) | X'01' | 1 bit/IDE for M band | ✅ |
| IOCA-7-725 | BITCNT (1) | X'01' | 1 bit/IDE for Y band | ✅ |
| IOCA-7-726 | BITCNT (1) | X'01' | 1 bit/IDE for K band | ✅ |
| IOCA-7-727 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-728 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-729 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-730 | FORMAT (1) | X'04' | CMYK | ✅ |
| IOCA-7-731 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-732 | SIZE1 (1) | X'01' | 1 bit/IDE (C component) | ✅ |
| IOCA-7-733 | SIZE2 (1) | X'01' | 1 bit/IDE (M component) | ✅ |
| IOCA-7-734 | SIZE3 (1) | X'01' | 1 bit/IDE (Y component) | ✅ |
| IOCA-7-735 | SIZE4 (1) | X'01' | 1 bit/IDE (K component) | ✅ |
| IOCA-7-736 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-737 | Parameters used when IDESZ=32:** | ✅ |
| IOCA-7-738 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-739 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-740 | COMPRID (1) | X'03', X'0D', X'83' | X'03' No Compression<br>X'0D' TIFF LZW<br>X'83' JPEG algorithms | ✅ |
| IOCA-7-741 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-742 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-743 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-744 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-745 | IDESZ (1) | X'20' | 32 bits/IDE | ✅ |
| IOCA-7-746 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-747 | LENGTH (1) | X'05' | ✅ |
| IOCA-7-748 | BCOUNT (1) | X'04' | 4 bands: CMYK | ✅ |
| IOCA-7-749 | BITCNT (1) | X'08' | 8 bits/IDE for C band | ✅ |
| IOCA-7-750 | BITCNT (1) | X'08' | 8 bits/IDE for M band | ✅ |
| IOCA-7-751 | BITCNT (1) | X'08' | 8 bits/IDE for Y band | ✅ |
| IOCA-7-752 | BITCNT (1) | X'08' | 8 bits/IDE for K band | ✅ |
| IOCA-7-753 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-754 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-755 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-756 | FORMAT (1) | X'04' | CMYK | ✅ |
| IOCA-7-757 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-758 | SIZE1 (1) | X'08' | 8 bits/IDE (C component) | ✅ |
| IOCA-7-759 | SIZE2 (1) | X'08' | 8 bits/IDE (M component) | ✅ |
| IOCA-7-760 | SIZE3 (1) | X'08' | 8 bits/IDE (Y component) | ✅ |
| IOCA-7-761 | SIZE4 (1) | X'08' | 8 bits/IDE (K component) | ✅ |
| IOCA-7-762 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-763 | Final parameters in a tile:** | ✅ |
| IOCA-7-764 | Begin Transparency Mask | ID (1) | X'8E' | ✅ |
| IOCA-7-765 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-766 | Image Size | ID (1) | X'94' | ✅ |
| IOCA-7-767 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-768 | UNITBASE (1) | X'00' – X'01' | X'00' 10 in; X'01' 10 cm | ✅ |
| IOCA-7-769 | HRESOL (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-770 | VRESOL (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-771 | HSIZE (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-772 | VSIZE (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-773 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-774 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-775 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'82' G4 MMR | ✅ |
| IOCA-7-776 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-777 | BITORDR (1) | X'00', X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-778 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-779 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-780 | DATA | Any | IDEs (bilevel only) | ✅ |
| IOCA-7-781 | End Transparency Mask | ID (1) | X'8F' | ✅ |
| IOCA-7-782 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-783 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-784 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-785 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-786 | DATA | Any | IDEs | ✅ |
| IOCA-7-787 | or:* | ✅ |
| IOCA-7-788 | Band Image Data** | (BCOUNT=4 only) | ✅ |
| IOCA-7-789 | Four bands, in order by BANDNUM, in the following format: | ✅ |
| IOCA-7-790 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-791 | LENGTH (2) | X'0003' – X'FFFF' | (see Note) | ✅ |
| IOCA-7-792 | BANDNUM (1) | X'01' – X'04' | X'01' Band contains C component<br>X'02' Band contains M component<br>X'03' Band contains Y component<br>X'04' Band contains K component | ✅ |
| IOCA-7-793 | RESERVED (2) | X'0000' | ✅ |
| IOCA-7-794 | DATA | Any | ✅ |
| IOCA-7-795 | End Tile | ID (1) | X'8D' | ✅ |
| IOCA-7-796 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-797 | Note on the tile-final parameters:** Band Image Data parameters with length of X'0003' do not have a data field. The receiver generates zeros for the corresponding band. | ✅ |
| IOCA-7-798 | Final parameters in FS45:** | ✅ |
| IOCA-7-799 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-800 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-801 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-802 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-803 | Function Set 48 is a superset of Function Set 40, Function Set 42, and Function Set 45. It describes bilevel or color tiled images. This function set is carried by the MO:DCA and IPDS controlling environments. The permissible parameter groupings in FS48 are defined as follows: | ✅ |
| IOCA-7-804 | X'70' | Begin Segment parameter | ✅ |
| IOCA-7-805 | Image Content (S) | ✅ |
| IOCA-7-806 | X'71' | End Segment parameter | ✅ |
| IOCA-7-807 | Code | Name | ✅ |
| IOCA-7-808 | X'91' | Begin Image Content parameter | ✅ |
| IOCA-7-809 | X'FEBB' | Tile TOC parameter | ✅ |
| IOCA-7-810 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-811 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-812 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-813 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-814 | [ X'FEB3' ] | [ nColor Names parameter ] | ✅ |
| IOCA-7-815 | [ Data and Referencing Tiles (S) ] | ✅ |
| IOCA-7-816 | X'93' | End Image Content parameter | ✅ |
| IOCA-7-817 | Code | Name | ✅ |
| IOCA-7-818 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-819 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-820 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-821 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-822 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-823 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-824 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-825 | [ X'FEB3' ] | [ nColor Names parameter ] | ✅ |
| IOCA-7-826 | [ X'9F' ] | [ External Algorithm Specification parameter (ignored) ] | ✅ |
| IOCA-7-827 | [ X'B7' ] | [ Tile Set Color parameter ] | ✅ |
| IOCA-7-828 | [ Transparency Mask ] | ✅ |
| IOCA-7-829 | [ Image Data or Band Image Data (S) ] | ✅ |
| IOCA-7-830 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-831 | Code | Name | ✅ |
| IOCA-7-832 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-833 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-834 | X'FEB8' | Include Tile parameter | ✅ |
| IOCA-7-835 | [ Transparency Mask ] | ✅ |
| IOCA-7-836 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-837 | Code | Name | ✅ |
| IOCA-7-838 | X'8C' | Begin Tile parameter | ✅ |
| IOCA-7-839 | X'B5' | Tile Position parameter | ✅ |
| IOCA-7-840 | X'B6' | Tile Size parameter | ✅ |
| IOCA-7-841 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-842 | [ X'96' ] | [ IDE Size parameter ] | ✅ |
| IOCA-7-843 | [ X'98' ] | [ Band Image parameter ] | ✅ |
| IOCA-7-844 | [ X'9B' ] | [ IDE Structure parameter ] | ✅ |
| IOCA-7-845 | [ X'FEB3' ] | [ nColor Names parameter ] | ✅ |
| IOCA-7-846 | [ X'9F' ] | [ External Algorithm Specification parameter (ignored) ] | ✅ |
| IOCA-7-847 | [ X'B7' ] | [ Tile Set Color parameter ] | ✅ |
| IOCA-7-848 | [ Transparency Mask ] | ✅ |
| IOCA-7-849 | [ Image Data or Band Image Data (S) ] | ✅ |
| IOCA-7-850 | X'8D' | End Tile parameter | ✅ |
| IOCA-7-851 | Code | Name | ✅ |
| IOCA-7-852 | X'8E' | Begin Transparency Mask parameter | ✅ |
| IOCA-7-853 | X'94' | Image Size parameter | ✅ |
| IOCA-7-854 | [ X'95' ] | [ Image Encoding parameter ] | ✅ |
| IOCA-7-855 | X'FE92' | Image Data | ✅ |
| IOCA-7-856 | X'8F' | End Transparency Mask parameter | ✅ |
| IOCA-7-857 | 1. Note that the parameters in Table 21, Table 22, Table 23, Table 24, Table 25, and Table 26 must come in the specified order. Even though the general IOCA architecture allows different ordering for some of the parameters, the FS48 specification is more restrictive. If the parameters are given in a different order, an out-of-sequence exception is raised. | ✅ |
| IOCA-7-858 | 2. The Image Encoding parameter, IDE Size parameter, Band Image parameter, and IDE Structure parameter are shown as optional and can possibly be specified in two places. Note that tile data might require that some of these parameters be specified. | ✅ |
| IOCA-7-859 | 3. If the IDE Size parameter is not present neither in the tile nor in the image content, the default IDE size is one bit per pel (bilevel image). | ✅ |
| IOCA-7-860 | 4. If the Image Encoding parameter is not present, the default compression algorithm is X'03' (No Compression), the recording algorithm defaults to X'01' (RIDIC), and the bit order defaults to zero. | ✅ |
| IOCA-7-861 | 5. If a tile contains the IDE Structure parameter specifying the CMYK or nColor color space, then the IDE Size parameter, Band Image parameter, and Band Image Data must also be present. | ✅ |
| IOCA-7-862 | 6. If the IDE Structure parameter specifying the CMYK or nColor color space is given outside of the tiles, then the IDE Size parameter and the Band Image parameter must be given either outside of the tiles or within every tile that does not contain another IDE Structure parameter specifying a different color space. | ✅ |
| IOCA-7-863 | 7. Receivers implementing FS48 must support at least 128 image contents in a single image segment. Otherwise, if a receiver encounters too many image contents to process, it should act as if it encountered too many image objects on a page. | ✅ |
| IOCA-7-864 | 8. Resource tiles included via the Include Tile parameter must not contain the Include Tile parameter or the exception EC-B811 exists. | ✅ |
| IOCA-7-865 | 9. Implementations of FS48 are very strongly recommended to also support the nColor Names parameter. Support of this parameter is not required in FS48 because FS48 became an official part of IOCA before the introduction of the nColor Names parameter; if the parameter had existed at the time FS48 was added, its support would have been made part of FS48. The nColor Names parameter is optional, but when specified with an FS48 image, must immediately follow the IDE Structure parameter in Table 22, Table 23, and Table 25. | ✅ |
| IOCA-7-866 | The self-defining fields and parameter values that are acceptable in Function Set 48 are shown in the following table. | ✅ |
| IOCA-7-867 | Initial parameters in FS48:** | ✅ |
| IOCA-7-868 | Begin Segment | ID (1) | X'70' | ✅ |
| IOCA-7-869 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-870 | Begin Image Content | ID (1) | X'91' | ✅ |
| IOCA-7-871 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-872 | OBJTYPE (1) | X'FF' | IOCA | ✅ |
| IOCA-7-873 | Tile TOC | ID (2) | X'FEBB' | ✅ |
| IOCA-7-874 | LENGTH (2) | X'0002' – X'7FFF' | ✅ |
| IOCA-7-875 | RESERVED (2) | X'0000' | Reserved; should be set to zero | ✅ |
| IOCA-7-876 | Either zero repeating groups, or one per tile in the following format: | ✅ |
| IOCA-7-877 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-878 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-879 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-880 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-881 | RELRES (1) | X'01' – X'02' | Relative resolution (see Note 1) | ✅ |
| IOCA-7-882 | COMPR (1) | | Compression algorithm | ✅ |
| IOCA-7-883 | DATAPOS (8) | | File offset to the beginning of the tile | ✅ |
| IOCA-7-884 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-885 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-886 | COMPRID (1) | X'01', X'03', X'08', X'0D', X'0E', X'20', X'82' – X'83' | X'01' IBM MMR (see Note 2)<br>X'03' No Compression<br>X'08' ABIC (see Note 2)<br>X'0D' TIFF LZW<br>X'0E' TIFF LZW with Diff Predictor<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR (see Note 2)<br>X'83' JPEG algorithms (see Note 3) | ✅ |
| IOCA-7-887 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-888 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-889 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-890 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-891 | IDESZ (1) | X'01', X'04', X'10', X'18', X'20', X'28', X'30', X'38', X'40', X'48', X'50', X'58', X'60', X'68', X'70', X'78' | X'01' 1 bit/IDE<br>X'04' 4 bits/IDE<br>X'10'–X'78' 16–120 bits/IDE | ✅ |
| IOCA-7-892 | 1. In the Tile TOC parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data. Using a RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general. For JPEG-compressed data, either value of RELRES is supported. | ✅ |
| IOCA-7-893 | 2. ABIC (Bilevel Q-Coder), IBM MMR-Modified Modified Read, G4 MMR-Modified Modified READ, and Solid Fill Rectangle are applicable only to images whose IDE size is 1 bit/band, otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-894 | 3. The JPEG algorithms are applicable only to CMYK images whose IDE size is 32 bits/IDE or to nColor images; otherwise exception condition EC-9611 is raised. | ✅ |
| IOCA-7-895 | Initial parameters in a data tile:** | ✅ |
| IOCA-7-896 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-897 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-898 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-899 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-900 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-901 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-902 | Tile Size | ID (1) | X'B6' | ✅ |
| IOCA-7-903 | LENGTH (1) | X'08' – X'09' | ✅ |
| IOCA-7-904 | THSIZE (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile size | ✅ |
| IOCA-7-905 | TVSIZE (4) | X'00000000' – X'7FFFFFFF' | Vertical tile size | ✅ |
| IOCA-7-906 | RELRES (1) | X'01' – X'02' | Relative resolution (see Note) | ✅ |
| IOCA-7-907 | Note on the data-tile initial parameters:** In the Tile Size parameter, the relative resolution (RELRES) of 2 is supported only for JPEG-compressed data. Using a RELRES of 2 for non-JPEG data results in exception EC-B610 being raised. Note that this restriction on the relative resolution holds only for this function set, not for the IOCA architecture in general. For JPEG-compressed data, either value of RELRES is supported. | ✅ |
| IOCA-7-908 | Initial parameters in a referencing tile:** | ✅ |
| IOCA-7-909 | Begin Tile | ID (1) | X'8C' | ✅ |
| IOCA-7-910 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-911 | Tile Position | ID (1) | X'B5' | ✅ |
| IOCA-7-912 | LENGTH (1) | X'08' | ✅ |
| IOCA-7-913 | XOFFSET (4) | X'00000000' – X'7FFFFFFF' | Horizontal tile origin | ✅ |
| IOCA-7-914 | YOFFSET (4) | X'00000000' – X'7FFFFFFF' | Vertical tile origin | ✅ |
| IOCA-7-915 | Include Tile | ID (2) | X'FEB8' | ✅ |
| IOCA-7-916 | LENGTH (2) | X'0004' | ✅ |
| IOCA-7-917 | TIRID (4) | X'00000000' – X'FFFFFFFF' | Resource Tile local identifier | ✅ |
| IOCA-7-918 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-919 | Parameters used when IDESZ=1:** | ✅ |
| IOCA-7-920 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-921 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-922 | COMPRID (1) | X'01', X'03', X'08', X'20', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'20' Solid Fill Rectangle<br>X'82' G4 MMR | ✅ |
| IOCA-7-923 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-924 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-925 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-926 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-927 | IDESZ (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-928 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-929 | LENGTH (1) | X'02' | ✅ |
| IOCA-7-930 | BCOUNT (1) | X'01' | One band | ✅ |
| IOCA-7-931 | BITCNT (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-932 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-933 | LENGTH (1) | X'06' – X'08' | ✅ |
| IOCA-7-934 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'00000' | ✅ |
| IOCA-7-935 | FORMAT (1) | X'02', X'12' | X'02' YCrCb<br>X'12' YCbCr | ✅ |
| IOCA-7-936 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-937 | SIZE1 (1) | X'01' | 1 bit/IDE | ✅ |
| IOCA-7-938 | SIZE2 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-939 | SIZE3 (1) | X'00' | 0 bits/IDE | ✅ |
| IOCA-7-940 | Tile Set Color | ID (1) | X'B7' | ✅ |
| IOCA-7-941 | LENGTH (1) | X'0B', X'0C' | ✅ |
| IOCA-7-942 | CSPACE (1) | X'04', X'08' | X'04' CMYK<br>X'08' CIELab | ✅ |
| IOCA-7-943 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-944 | SIZE1–SIZE3 (1) | X'08' | Bits/IDE for components 1-3 | ✅ |
| IOCA-7-945 | SIZE4 (1) | X'00', X'08' | Bits/IDE for component 4 | ✅ |
| IOCA-7-946 | CVAL1–CVAL4 (1) | X'00' – X'FF' | Color values | ✅ |
| IOCA-7-947 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-948 | Parameters used when IDESZ=4:** | ✅ |
| IOCA-7-949 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-950 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-951 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'82' G4 MMR | ✅ |
| IOCA-7-952 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-953 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-954 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-955 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-956 | IDESZ (1) | X'04' | 4 bits/IDE | ✅ |
| IOCA-7-957 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-958 | LENGTH (1) | X'05' | ✅ |
| IOCA-7-959 | BCOUNT (1) | X'04' | Four bands: CMYK | ✅ |
| IOCA-7-960 | BITCNT (1) | X'01' | 1 bit/IDE for C band | ✅ |
| IOCA-7-961 | BITCNT (1) | X'01' | 1 bit/IDE for M band | ✅ |
| IOCA-7-962 | BITCNT (1) | X'01' | 1 bit/IDE for Y band | ✅ |
| IOCA-7-963 | BITCNT (1) | X'01' | 1 bit/IDE for K band | ✅ |
| IOCA-7-964 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-965 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-966 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-967 | FORMAT (1) | X'04' | CMYK | ✅ |
| IOCA-7-968 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-969 | SIZE1 (1) | X'01' | 1 bit/IDE (C component) | ✅ |
| IOCA-7-970 | SIZE2 (1) | X'01' | 1 bit/IDE (M component) | ✅ |
| IOCA-7-971 | SIZE3 (1) | X'01' | 1 bit/IDE (Y component) | ✅ |
| IOCA-7-972 | SIZE4 (1) | X'01' | 1 bit/IDE (K component) | ✅ |
| IOCA-7-973 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-974 | Parameters used when IDESZ=32 and FORMAT=CMYK:** | ✅ |
| IOCA-7-975 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-976 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-977 | COMPRID (1) | X'03', X'0D', X'0E', X'83' | X'03' No Compression<br>X'0D' TIFF LZW<br>X'0E' TIFF LZW with Diff Predictor<br>X'83' JPEG | ✅ |
| IOCA-7-978 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-979 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-980 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-981 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-982 | IDESZ (1) | X'20' | 32 bits/IDE | ✅ |
| IOCA-7-983 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-984 | LENGTH (1) | X'05' | ✅ |
| IOCA-7-985 | BCOUNT (1) | X'04' | 4 bands: CMYK | ✅ |
| IOCA-7-986 | BITCNT (1) | X'08' | 8 bits/IDE for C band | ✅ |
| IOCA-7-987 | BITCNT (1) | X'08' | 8 bits/IDE for M band | ✅ |
| IOCA-7-988 | BITCNT (1) | X'08' | 8 bits/IDE for Y band | ✅ |
| IOCA-7-989 | BITCNT (1) | X'08' | 8 bits/IDE for K band | ✅ |
| IOCA-7-990 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-991 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-992 | FLAGS (1) | | ASFLAG B'0' Additive<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-993 | FORMAT (1) | X'04' | CMYK | ✅ |
| IOCA-7-994 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-995 | SIZE1 (1) | X'08' | 8 bits/IDE (C component) | ✅ |
| IOCA-7-996 | SIZE2 (1) | X'08' | 8 bits/IDE (M component) | ✅ |
| IOCA-7-997 | SIZE3 (1) | X'08' | 8 bits/IDE (Y component) | ✅ |
| IOCA-7-998 | SIZE4 (1) | X'08' | 8 bits/IDE (K component) | ✅ |
| IOCA-7-999 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-1000 | Parameters used when FORMAT=nColor:** | ✅ |
| IOCA-7-1001 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-1002 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-1003 | COMPRID (1) | X'03', X'0D', X'0E', X'83' | X'03' No Compression<br>X'0D' TIFF LZW<br>X'0E' TIFF LZW with Diff Predictor<br>X'83' JPEG | ✅ |
| IOCA-7-1004 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-1005 | BITORDR (1) | X'00' – X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-1006 | IDE Size | ID (1) | X'96' | ✅ |
| IOCA-7-1007 | LENGTH (1) | X'01' | ✅ |
| IOCA-7-1008 | IDESZ (1) | X'10', X'18', X'20', X'28', X'30', X'38', X'40', X'48', X'50', X'58', X'60', X'68', X'70', X'78' | X'10'–X'78' 16–120 bits/IDE (n=2–15) | ✅ |
| IOCA-7-1009 | Band Image | ID (1) | X'98' | ✅ |
| IOCA-7-1010 | LENGTH (1) | X'03' – X'10' | ✅ |
| IOCA-7-1011 | BCOUNT (1) | X'02' – X'0F' | 2–15 bands | ✅ |
| IOCA-7-1012 | BITCNT (1) | X'08' | 8 bits/IDE for each band | ✅ |
| IOCA-7-1013 | IDE Structure | ID (1) | X'9B' | ✅ |
| IOCA-7-1014 | LENGTH (1) | X'07' – X'14' | ✅ |
| IOCA-7-1015 | FLAGS (1) | | ASFLAG (Ignored)<br>GRAYCODE B'0' No gray coding<br>RESERVED B'000000' | ✅ |
| IOCA-7-1016 | FORMAT (1) | X'8n' | nColor (X'2' ≤ n ≤ X'F') | ✅ |
| IOCA-7-1017 | RESERVED (3) | X'000000' | ✅ |
| IOCA-7-1018 | SIZE1–SIZEn (1) | X'08' | 8 bits/IDE for each component | ✅ |
| IOCA-7-1019 | Note on the parameters used when FORMAT=nColor:** When using FORMAT=nColor, generating implementations are very strongly recommended to also include an nColor Names parameter here, just after the IDE Structure parameter, and receiving implementations are very strongly recommended to support that parameter here. This allows an nColor FS48 IOCA image to specify the names of the colors being used. For more information on this recommendation, see Note 9. | ✅ |
| IOCA-7-1020 | Final parameters in a tile:** | ✅ |
| IOCA-7-1021 | Begin Transparency Mask | ID (1) | X'8E' | ✅ |
| IOCA-7-1022 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-1023 | Image Size | ID (1) | X'94' | ✅ |
| IOCA-7-1024 | LENGTH (1) | X'09' | ✅ |
| IOCA-7-1025 | UNITBASE (1) | X'00' – X'01' | X'00' 10 in; X'01' 10 cm | ✅ |
| IOCA-7-1026 | HRESOL (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-1027 | VRESOL (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-1028 | HSIZE (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-1029 | VSIZE (2) | X'0001' – X'7FFF' | ✅ |
| IOCA-7-1030 | Image Encoding | ID (1) | X'95' | ✅ |
| IOCA-7-1031 | LENGTH (1) | X'02' – X'03' | ✅ |
| IOCA-7-1032 | COMPRID (1) | X'01', X'03', X'08', X'82' | X'01' IBM MMR<br>X'03' No Compression<br>X'08' ABIC<br>X'82' G4 MMR | ✅ |
| IOCA-7-1033 | RECID (1) | X'01', X'04' | X'01' RIDIC<br>X'04' Unpadded RIDIC | ✅ |
| IOCA-7-1034 | BITORDR (1) | X'00', X'01' | X'00' L-to-R<br>X'01' R-to-L | ✅ |
| IOCA-7-1035 | Image Data | ID (2) | X'FE92' | ✅ |
| IOCA-7-1036 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-1037 | DATA | Any | IDEs (bilevel only) | ✅ |
| IOCA-7-1038 | End Transparency Mask | ID (1) | X'8F' | ✅ |
| IOCA-7-1039 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-1040 | IOCA Self-defining Field | Parameter (Bytes) | Acceptable Value | Comments | ✅ |
| IOCA-7-1041 | Image Data** | (IDESZ=1 only) | ✅ |
| IOCA-7-1042 | ID (2) | X'FE92' | ✅ |
| IOCA-7-1043 | LENGTH (2) | X'0001' – X'FFFF' | ✅ |
| IOCA-7-1044 | DATA | Any | IDEs (bilevel only) | ✅ |
| IOCA-7-1045 | or:* | ✅ |
| IOCA-7-1046 | Band Image Data** | (FORMAT=CMYK only) | ✅ |
| IOCA-7-1047 | Four bands, in order by BANDNUM, in the following format: | ✅ |
| IOCA-7-1048 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-1049 | LENGTH (2) | X'0003' – X'FFFF' | (see Note) | ✅ |
| IOCA-7-1050 | BANDNUM (1) | X'01' – X'04' | X'01' Band contains C component<br>X'02' Band contains M component<br>X'03' Band contains Y component<br>X'04' Band contains K component | ✅ |
| IOCA-7-1051 | RESERVED (2) | X'0000' | ✅ |
| IOCA-7-1052 | DATA | Any | ✅ |
| IOCA-7-1053 | or:* | ✅ |
| IOCA-7-1054 | Band Image Data** | (FORMAT=nColor only) | ✅ |
| IOCA-7-1055 | n bands, in order by BANDNUM, in the following format: | ✅ |
| IOCA-7-1056 | ID (2) | X'FE9C' | ✅ |
| IOCA-7-1057 | LENGTH (2) | X'0003' – X'FFFF' | (see Note) | ✅ |
| IOCA-7-1058 | BANDNUM (1) | X'01' – X'0F' | X'0n' Band contains the nth color component | ✅ |
| IOCA-7-1059 | RESERVED (2) | X'0000' | ✅ |
| IOCA-7-1060 | DATA | Any | ✅ |
| IOCA-7-1061 | End Tile | ID (1) | X'8D' | ✅ |
| IOCA-7-1062 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-1063 | Note on the tile-final parameters:** Band Image Data parameters with a length of X'0003' do not have a data field. The receiver generates zeros for the corresponding band. | ✅ |
| IOCA-7-1064 | Final parameters in FS48:** | ✅ |
| IOCA-7-1065 | End Image Content | ID (1) | X'93' | ✅ |
| IOCA-7-1066 | LENGTH (1) | X'00' | ✅ |
| IOCA-7-1067 | End Segment | ID (1) | X'71' | ✅ |
| IOCA-7-1068 | LENGTH (1) | X'00' | ✅ |
| IOCA-A-001 | This chapter consists of the image compression and recording algorithms that are presently defined in “Image Encoding”. This appendix is not meant to be a complete description or specification of each algorithm, but is meant to be a short and concise outline of the characteristics of each image compression algorithm. | ❓ |
| IOCA-A-002 | Other values All other values are reserved All of these compression algorithms are lossless—they result in no loss of data—except for some JPEG algorithms, which are lossy. | ❓ |
| IOCA-A-003 | In one-dimensional (1D) mode, color transitions in the image are coded by a run-length that denotes how | ❓ |
| IOCA-A-004 | In two-dimensional (2D) mode, the image is coded by how far each IDE is positioned from different color | ❓ |
| IOCA-A-005 | Infinite K value (only the first scan line is in 1D mode) | ❓ |
| IOCA-A-006 | No EOLs, except when switching from 1D to 2D and as part of the EOP | ❓ |
| IOCA-A-007 | No time-fill bit | ❓ |
| IOCA-A-008 | 1. IBM MMR-Modified Modified Read allows the IOCA Process Model to determine the number of image points in the horizontal and vertical directions. HSIZE and VSIZE can therefore be zero in the Image Size parameter. | ❓ |
| IOCA-A-009 | 2. If the HSIZE or VSIZE parameter of the Image Size parameter is nonzero, it may be less than the actual number of horizontal or vertical image points encoded in the image data due to padding bits or padding scan lines. | ❓ |
| IOCA-A-010 | Note: The value No Compression does not allow the IOCA Process Model to determine the number of horizontal image points from the image data. However, VSIZE can be zero in the Image Size parameter. | ❓ |
| IOCA-A-011 | For more details, refer to R. Arps, T . Truong, D. Lu, R. Pasco, and T . Friedman, “A multipurpose VLSI chip for adaptive data compression of bilevel images”, in IBM Journal of Research and Development, Volume 32, No. 6 (November 1988). | ❓ |
| IOCA-A-012 | New rows always begin on the next available byte boundary. | ❓ |
| IOCA-A-013 | No End-of-line (EOL) code words are used. | ❓ |
| IOCA-A-014 | No fill bits are used, except for the ignored bits at the end of the last byte of a row. | ❓ |
| IOCA-A-015 | No Return to control (RTC) is used. | ❓ |
| IOCA-A-016 | Hence both HSIZE and VSIZE cannot be zero in the Image Size parameter. | ❓ |
| IOCA-A-017 | 1. TIFF LZW does not allow the IOCA Process Model to determine the number of horizontal or vertical image points from the image data. Hence both HSIZE and VSIZE cannot be zero in the Image Size parameter. | ❓ |
| IOCA-A-018 | 2. LZW encoders sometimes terminate the data early. If the LZW decoder does not produce the expected number of bytes, no exception should be raised and the receiver should fill the remaining data with binary zeros. | ❓ |
| IOCA-A-019 | TIFF, Revision 6.0, Final (Aldus Corp.: June 3, 1992). | ❓ |
| IOCA-A-020 | T erry A. Welch, “A T echnique for High Performance Data Compression”, in IEEE Computer, vol. 17, no. 6 | ❓ |
| IOCA-A-021 | In general, the nature of this algorithm lends itself to better compression when compressing a single color component, as is found in banded data. | ❓ |
| IOCA-A-022 | JPEG Compression Algorithms The JPEG (Joint Photographic Experts Group) technical specification details a series of algorithms that can be applied to arbitrary source image resolutions, many color models, multiple image components, various sampling formats, and continuous-tone renditions of text. The algorithms are not applicable to bilevel images. | ❓ |
| IOCA-A-023 | ISO/IEC International Standard 10918-1 | ❓ |
| IOCA-A-024 | ITU-TSS Recommendation T .81 | ❓ |
| IOCA-A-025 | International T elecommunication Union, Recommendation T .88, “Information T echnology - Coded | ❓ |
| IOCA-A-026 | International T elecommunication Union, Recommendation T .89 Amendment 1, “Application Profiles for | ❓ |
| IOCA-A-027 | Recommendation T .88 - Lossy/lossless coding of bi-level images (JBIG2) for fascimile” User-defined Compression Algorithm Code point X'FE' denotes that the compression algorithm is supplied by the user, and that the algorithmic description can be found in the External Algorithm Specification parameter . Users should contact the IOCA architecture group to obtain a unique identifier for their exclusive use. | ❓ |
| IOCA-A-028 | 2 Table 28. Image Compression Algorithms Supported by IOCA Compression COMPRID HSIZE VSIZE IBM MMR-Modified Modified READ X'01' Can be zero in the Image Size parameter Can be zero in the Image Size | ❓ |
| IOCA-A-029 | Can be zero in the Image Size parameter | ❓ |
| IOCA-A-030 | 2. The OS/2 Image Support compression algorithm is based on an earlier version (V5.0) of the JPEG specification. Although JPEG encodes the actual image width and height in the compressed data header the current OS/2 Image Support implementation of the compression algorithm requires both the HSIZE and VSIZE parameters of the Image Size parameter to contain the actual image size. | ❓ |
| IOCA-A-031 | 4 bits/IDE CMYK (X'04') Concatenated ABIC (X'0A') | ❓ |
| IOCA-A-032 | 8 bits/IDE YCrCb (X'02') | ❓ |
| IOCA-A-033 | 24 bits/IDE RGB (X'01') | ❓ |
| IOCA-A-034 | 32 bits/IDE CMYK (X'04') TIFF LZW (X'0D') | ❓ |
| IOCA-A-035 | 1. The color space is the FORMAT field in the IDE Structure parameter. | ❓ |
| IOCA-A-036 | 2. The compression algorithm is the COMPRID field in the Image Encoding parameter. | ❓ |
| IOCA-A-037 | 3. “No Compression” (X'03') is valid for all image data types. | ❓ |
| IOCA-A-038 | 4. A mismatch between the image data and compression algorithm causes exception EC-9511 to be raised. | ❓ |
| IOCA-A-039 | The choice of the compression algorithm can have a major impact on both the printer performance and the print quality. Poor compression ratios can result in large datasets that cannot be downloaded to the printer quickly enough. The time required for decompression generally increases with the size of the compressed image and can also be a problem. The print quality is affected by using a lossy algorithm, such as JPEG, on unsuitable data. For more information on matching the compression algorithm to the type of image data, see | ❓ |
| IOCA-B-001 | IDE Size parameter | ❓ |
| IOCA-B-002 | IDE Structure parameter | ❓ |
| IOCA-B-003 | If the image is bilevel, the foreground color can be set to an arbitrary color using the Set Bilevel Image Color and Set Extended Bilevel Image Color structured fields in the Image Data Descriptor in MO:DCA. If an image tile is bilevel, the foreground color can also be set using the Tile Set Color parameter. If no color has been specified, the device default is used. | ❓ |
| IOCA-B-004 | Note: ASFLAG is ignored for bilevel images to maintain backward compatibility with the current usage, since the FS11, FS14, FS40, FS42, FS45, and FS48 function sets require ASFLAG of B'0' (additive) for bilevel images. For the Y color space, this would imply B'1' being white (untoned) pel, while the IDE value of B'0' is defined to be a toned pel. This is the opposite of how the images are processed. | ❓ |
| IOCA-B-005 | If the IDE Structure Parameter is present, the color space must be either X'02' (YCrCb) or X'12' (YCbCr). The ASFLAG value determines whether a higher IDE value is mapped to a brighter or a darker level. | ❓ |
| IOCA-B-006 | Refer to the resource appendix in the Mixed Object Document Content Architecture (MO:DCA) Reference for a description of the RGB model and the Y component of the YCrCb and YCbCr models. | ❓ |
| IOCA-B-007 | Bilevel, Grayscale, and Color Images | ❓ |
| IOCA-C-001 | A tile resource can contain any parameter otherwise allowed within tiles, except the Include Tile parameter. If | ❓ |
| IOCA-C-002 | The content of the Tile Position parameter in the tile resource is ignored. The receiver uses the Tile Position | ❓ |
| IOCA-C-003 | If both the tile resource and the calling tile contain Transparency Masks, they are combined using the logical | ❓ |
| IOCA-C-004 | If only one of the two possible transparency masks is specified, it is used without changes. | ❓ |
| IOCA-C-005 | At inclusion time, the tile is treated just as if it were specified locally: the Tile Position parameter in the tile | ❓ |
| IOCA-C-006 | Any defaults are applied as if the tile were specified locally. | ❓ |
| IOCA-C-007 | [ X'9F' External Algorithm Specification parameter (ignored) ] [ X'B7' Tile Set Color parameter ] [ Transparency Mask ] [ Image Data or Band Image Data (S) ] X'8D' End Tile parameter | ❓ |
| IOCA-D-001 | A set of rules that must be followed by all generators when constructing Image Objects | ❓ |
| IOCA-D-002 | A set of image processing capabilities that are guaranteed to be supported by all receivers | ❓ |
| IOCA-D-003 | In order to conform to a particular MO:DCA interchange set, products that receive Image Objects and convert them using a processor for output to some device are required to support all the image functions defined in that interchange set. | ❓ |
| IOCA-D-004 | UNITBASE = X'00' MO:DCA IDD | ❓ |
| IOCA-D-005 | 3 X'00' Reserved; should be zero M 4 CODE NAMECOLR X'0000' – X'0010', X'FF00' – X'FF08', X'FFFF' Named colors: | ❓ |
| IOCA-D-006 | 1. The medium is typically the physical paper in a printer environment, and the monitor screen in the display environment. | ❓ |
| IOCA-D-007 | 2. The presentation process is typically the program that performs the final imaging step on the medium. | ❓ |
| IOCA-D-008 | 3. This self-defining field is ignored if it is present and the image is not bilevel. | ❓ |
| IOCA-D-009 | Set Bilevel Image Color | ❓ |
| IOCA-D-010 | 4. Color specified by X'0007', rendered on presentation devices that do not support white, is device dependent. For example, some printers simulate white with the color of the medium, which results in white when a white medium is used. | ❓ |
| IOCA-D-011 | Set Bilevel Image Color | ❓ |
| IOCA-D-012 | This SDF is applicable only to significant image points of Bilevel Images. | ❓ |
| IOCA-D-013 | 0 | CODE | ID | | X'F4' Set Extended Bilevel Image Color M 1 UBIN LENGTH X'0C' – X'0E' Length of the parameters to follow | M | ❓ |
| IOCA-D-014 | 2 | Reserved; | must | | be zero M 3 CODE ColSpce X'01', X'04', X'06', X'08', X'40' Color space: X'01' RGB X'04' CMYK X'06' Highlight color space X'08' CIELAB X'40' Standard OCA color space M 4–7 Reserved; must be zero M 8 UBIN ColSize1 X'01' – X'08', X'10' Number of bits in component 1; see color space definitions | M | ❓ |
| IOCA-D-015 | 9 | UBIN | ColSize2 | | X'00' – X'08' Number of bits in component 2; see color space definitions | M | ❓ |
| IOCA-D-016 | 10 | UBIN | ColSize3 | | X'00' – X'08' Number of bits in component 3; see color space definitions | M | ❓ |
| IOCA-D-017 | 11 | UBIN | ColSize4 | | X'00' – X'08' Number of bits in component 4; see color space definitions M 12–n Color Color specification; see Set Extended Bilevel Image Color Semantics for details M Set Extended Bilevel Image Color Semantics ColSpce Is a code that defines the color space and the encoding for the color specification. Value Description X'01' RGB color space. The color value is specified with three components. Components 1, 2, and 3 are unsigned binary numbers that specify the red, green, and blue intensity values, in that order. ColSize1, ColSize2, and ColSize3 are nonzero and define the number of bits used to specify each component. ColSize4 is reserved and should be set to zero. The intensity range for the R,G,B components is 0 to 1, which is mapped to the binary value range 0 to (2 ColSizeN - 1), where N=1,2,3. Architecture Note: The reference white point and the chromaticity coordinates for RGB are defined in SMPTE RP 145-1987, entitled Color Monitor Colorimetry, and in RP 37-1969, entitled Color Temperature for Color Television Studio Monitors, respectively. The reference white point is commonly known as Illuminant D6500 or simply D65. The R,G,B components are assumed to be gamma-corrected (non-linear) with a gamma of 2.2. X'04' CMYK color space. The color value is specified with four components. Components 1, 2, 3, and 4 are unsigned binary numbers that specify the cyan, magenta, yellow, and black intensity values, in that order. ColSize1, ColSize2, ColSize3, and ColSize4 are nonzero and define the number of bits used to specify each component. The intensity Set Extended Bilevel Image Color range for the C,M,Y ,K components is 0 to 1, which is mapped to the binary value range 0 to (2 ColSizeN - 1), where N=1,2,3,4. This is a device-dependent color space. X'06' Highlight color space. This color space defines a request for the presentation device to generate a highlight color. The color value is specified with one to three components. Component 1 is a two-byte unsigned binary number that specifies the highlight color number. The first highlight color is assigned X'0001', the second highlight color is assigned X'0002', and so on. The value X'0000' specifies the presentation device default color. ColSize1 = X'10' and defines the number of bits used to specify component 1. Component 2 is an optional one-byte unsigned binary number that specifies a percent coverage for the specified color. Percent coverage can be any value from 0% to 100% (X'00'–X'64'). The number of distinct values supported is presentation-device dependent. If the coverage is less than 100%, the remaining coverage is achieved with color of medium. ColSize2 = X'00' or X'08' and defines the number of bits used to specify component 2. A value of X'00' indicates that component 2 is not specified in the color value, in which case the architected default for percent coverage is 100%. A value of X'08' indicates that component 2 is specified in the color value. Component 3 is an optional one-byte unsigned binary number that specifies a percent shading, which is a percentage of black that is to be added to the specified color. Percent shading can be any value from 0% to 100% (X'00'–X'64'). The number of distinct values supported is presentation-device dependent. If percent coverage and percent shading are specified, the effective range for percent shading is 0% to (100- coverage)%. If the sum of percent coverage plus percent shading is less than 100%, the remaining coverage is achieved with color of medium. ColSize3 = X'00' or X'08' and defines the number of bits used to specify component 3. A value of X'00' indicates that component 3 is not specified in the color value, in which case the architected default for percent shading is 0%. A value of X'08' indicates that component 3 is specified in the color value. Implementation Note: The percent shading parameter is currently not supported in AFP environments. ColSize4 is reserved and should be set to zero. This is a device-dependent color space. Architecture Notes: 1. The color that is rendered when a highlight color is specified is device dependent. For presentation devices that support colors other than black, highlight color values in the range X'0001' to X'FFFF' may be mapped to any color. For bilevel devices, the color may be simulated with a graphic pattern. 2. If the specified highlight color is “presentation device default”, devices whose default color is black use the percent coverage parameter, which is specified in component 2, to render a percent shading. 3. On printing devices, the color of medium is normally white, in which case a coverage of n% results in adding (100-n)% white to the specified color, or tinting the color with (100-n)% white. Display devices may assume the color of medium to always be white and use this algorithm to render the specified coverage. 4. The highlight color space can also specify indexed colors when used in conjunction with a Color Mapping T able (CMT) or an Indexed (IX) Color Management Resource (CMR). In that case, component 1 specifies a two-byte value that is the index into the CMT or the IX CMR and components 2 and 3 are ignored. Note that when both a CMT and Indexed CMRs are used, the CMT is always accessed first. T o preserve compatibility with existing highlight color Set Extended Bilevel Image Color devices, indexed color values X'0000' to X'00FF' are reserved for existing highlight color applications and devices. That is, indexed color values in the range X'0000' to X'00FF', assuming they are not mapped to a different color space in a CMT , are mapped directly to highlight colors. Indexed color values in the range X'0100' to X'FFFF', assuming they are not mapped to a different color space in a CMT , are used to access Indexed CMRs. For a description of the Color Mapping T able in MO:DCA environments, see the Mixed Object Document Content Architecture (MO:DCA) Reference. X'08' CIELAB color space. The color value is specified with three components. Components 1, 2, and 3 are binary numbers that specify the L, a, b values, in that order, where L is the luminance and a and b are the chrominance differences. Component 1 specifies the L value as an unsigned binary number; components 2 and 3 specify the a and b values as signed binary numbers. ColSize1, ColSize2, and ColSize3 are nonzero and define the number of bits used to specify each component. ColSize4 is reserved and should be set to zero. The range for the L component is 0 to 100, which is mapped to the binary value range 0 to (2 ColSize1 - 1). The range for the a and b components is -127 to +127, which is mapped to the binary range -(2 ColSizeN-1 - 1) to +(2 ColSizeN-1 - 1), where N=2,3. For color fidelity, 8-bit encoding should be used for each component, that is, ColSize1, ColSize2, and ColSize3 are set to X'08'. When the recommended 8-bit encoding is used for the a and b components, the range is extended to include -128, which is mapped to the value X'80'. If the encoding is less than 8 bits, treatment of the most negative binary endpoint for the a and b components is device dependent, and tends to be insignificant because of the quantization error. Architecture Note: The reference white point for CIELAB is known as D50 and is defined in CIE publication 15-2 entitled Colorimetry. X'40' Standard OCA color space. The color value is specified with one component. Component 1 is an unsigned binary number that specifies a named color using a two- byte value from the Standard OCA Color Value T able. For a complete description of the Standard OCA Color Value T able, see the Mixed Object Document Content Architecture (MO:DCA) Reference. ColSize1 = X'10' and defines the number of bits used to specify component 1. ColSize2, ColSize3, and ColSize4 are reserved and should be set to zero. This is a device-dependent color space. All others Reserved ColSize1 Defines the number of bits used to specify the first color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. For example, if ColSize1 = X'06', the first color component has two padding bits. ColSize2 Defines the number of bits used to specify the second color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. ColSize3 Defines the number of bits used to specify the third color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. ColSize4 Defines the number of bits used to specify the fourth color component. The color component is right-aligned and padded with zeros on the left to the nearest byte boundary. Color Specifies the color value in the defined format and encoding. Note that the number of bytes specified for this parameter depends on the color space. For example, when using 8 bits per component, an RGB color value is specified with 3 bytes, while a CMYK color value is specified with 4 bytes. If extra bytes are specified, they are ignored as long as the self-defining field length is valid. Set Extended Bilevel Image Color T o illustrate the syntax for the color value specified in the Color field, the following table shows examples of various ways that a light-green color can be specified. Note that the light-green color value is approximated in each of the color spaces. ColSpce ColSize1 ColSize2 ColSize3 ColSize4 Color RGB X'08' X'08' X'08' N/A X'00B761' RGB X'05' X'05' X'05' N/A X'00160B' CMYK X'08' X'08' X'08' X'08' X'FF489E00' CMYK X'01' X'02' X'04' X'08' X'01010900' Highlight (see note) X'10' X'08' X'00' N/A X'000264' CIELAB X'08' X'08' X'08' N/A X'A8CC21' Standard OCA X'10' N/A N/A N/A X'0004' Note: This example assumes that the light-green color is loaded in the printer as highlight color X'0002'. Architecture Note: For a description of color spaces and their relationships, see R. Hunt, The Reproduction of Colour in Photography, Printing and Television, Fifth Edition, Fountain Press, 1995. Notes: 1. This self-defining field is ignored if it is present and the image is not bilevel. 2. This field can coexist with the Set Bilevel Image Color self-defining field. 3. If multiple instances of this field and the Set Bilevel Image Color field are present, the last instance of a supported field is used, while the others are ignored. If an invalid or unsupported value is encountered in the self-defining field, the entire self-defining field is ignored. The IOCA Process Model should notify the controlling environment if this exception condition appears, or if multiple instances of this field and/or Set Bilevel Image Color field are present. Set Extended Bilevel Image Color IOCA Function Set Identification This optional self-defining field is carried by the IDD described in “Image Data Descriptor (IDD)” . It specifies the IOCA function set carried by the IPD. IOCA function sets are defined in “Function Sets” . Offset Type Name Range Meaning M/O 0 CODE SDFID X'F7' IOCA Function Set Identification | M | ❓ |
| IOCA-D-018 | 1 | UBIN | LENGTH | | X'02' Length of the parameters to follow M 2 CODE CATEGORY X'01' Function Set category: X'01' Function Set identifier All other values are reserved. | M | ❓ |
| IOCA-D-019 | 3 | CODE | FCNSET | | X'0A' – X'0B', X'0E', X'28', X'2A', X'2D', X'30' Function Set identifier: X'0A' Function Set 10 X'0B' Function Set 11 X'0E' Function Set 14 X'28' Function Set 40 X'2A' Function Set 42 X'2D' Function Set 45 X'30' Function Set 48 All other values are reserved. M IOCA Function Set Identification Image Picture Data (IPD) An IOCA Image Segment is carried by one or more IPD structured fields. Structured Field Introducer SF Length X'D3EEFB' Flags Sequence | Number | ❓ |
| IOCA-D-020 | 1. An IOCA Image Segment can be split into multiple IPD structured fields. There are no restrictions on how the image segment is split between multiple IPD structured fields. Data beyond the End Segment self- defining field is ignored by receivers. | ❓ |
| IOCA-D-021 | 2. Each image point in IOCA Image Content is mapped to one image point in the Image Presentation Space. | ❓ |
| IOCA-E-001 | The context of Image Objects in the IPDS environment | ❓ |
| IOCA-E-002 | IPDS commands specific to images | ❓ |
| IOCA-E-003 | Some special considerations when printing an image | ❓ |
| IOCA-E-004 | If the Image Output Control parameters are omitted, the default is Position and Trim. | ❓ |
| IOCA-E-005 | Page state | ❓ |
| IOCA-E-006 | Overlay state | ❓ |
| IOCA-E-007 | Page segment state | ❓ |
| IOCA-E-008 | The Write Image Control 2 command, to define where and how to present an Image Object | ❓ |
| IOCA-E-009 | The Write Image 2 command, which contains an Image Segment | ❓ |
| IOCA-E-010 | The Image Size parameter specifies an unknown horizontal image size (HSIZE=0), and an image | ❓ |
| IOCA-E-011 | The size detected from the image data is different from that specified in the Image Size parameter. The IOCA | ❓ |
| IOCA-F1-001 | Compressed image size. High frequency halftones tend to compress very poorly. For example, 212lpi | ❓ |
| IOCA-F1-002 | Device dependency. Halftoned images are device dependent. The halftone screens are built for a particular | ❓ |
| IOCA-F1-003 | type of the print engine. Moreover, each print engine behaves differently and behavior changes unpredictably with time, based on many environmental and internal factors. For the best quality, the halftones should be calibrated frequently. If quality output is desired, halftone images should not be archived. The generators should rather archive the original color or grayscale and generate the halftoned IOCA when the print device characteristics are known. Black and white text and linework are not device-specific and can be archived safely. | ❓ |
| IOCA-F1-004 | Scaling impact. Scaling halftoned images by non-integer factors results in artifacts and unacceptable output | ❓ |
| IOCA-F1-005 | Notes for IOCA Generators | ❓ |
| IOCA-F1-006 | IBM MMR-Modified Modified READ algorithm is obsolete. Using G4 MMR compression almost always | ❓ |
| IOCA-F1-007 | MMR algorithms are well-suited for compressing text and line art. If the image contains halftones, the | ❓ |
| IOCA-F1-008 | The ABIC compression algorithm compresses even high frequency halftones. ABIC is a complex algorithm | ❓ |
| IOCA-F1-009 | The JPEG algorithm is well-suited for compressing continuous tone images such as photographs. Using | ❓ |
| IOCA-F1-010 | The TIFF LZW algorithm is an excellent general-purpose lossless algorithm. It is particularly well suited to | ❓ |
| IOCA-F1-011 | Notes for IOCA Generators | ❓ |
| IOCA-F-001 | Appendix F , “Notes for IOCA Generators”. | ❓ |
| IOCA-F-002 | If the Image Size parameter specifies a non-multiple of 8 bits, the resulting compressed image must be compressed at the next multiple of 8 bits and must be decompressed at the next multiple of 8 bits. Once decompressed, only the number of bits specified in the Image Size parameter are to be used for each scan line. | ❓ |
| IOCA-F-003 | Unpadded RIDIC Recording Algorithm The Unpadded RIDIC algorithm is identical to the RIDIC recording algorithm except that raster scan lines can be any length; no padding is necessary. | ❓ |
| IOCA-G-001 | Each IDE value is an index into this LUT . | ❓ |
| IOCA-G-002 | 0 | CODE | ID | | X'97' Image LUT-ID parameter M 1 UBIN LENGTH X'01' Length of the parameters to follow | M | ❓ |
| IOCA-G-003 | 2 | CODE | LUTID | | X'00' – X'FF' LUT-ID identifier M If the Image LUT-ID parameter is not present, the default value for LUTID is zero for the standard | LUT-ID. | ❓ |
| IOCA-G-004 | EC-9710 Invalid or unsupported Image Data parameter value Condition: The Image LUT-ID parameter contains an invalid or unsupported value. | ❓ |
| IOCA-G-005 | 1. IBM MMR-Modified Modified Read and G4 MMR-Modified Modified READ are applicable only to images whose IDE size is 1 bit/IDE; otherwise exception condition EC-9611 is raised. | ❓ |
| IOCA-G-006 | 2. Bottom-to-T opis used only in conjunction with No Compression; otherwise exception condition EC-9510 is raised. | ❓ |
| IOCA-G-007 | Image Data ID (2) X'FE92' LENGTH (2) X'0001' – X'FFFF' DATA Any IDEs End Image Content ID (1) X'93' LENGTH (1) X'00' End Segment ID (1) X'71' LENGTH (1) X'00' Retired Architecture | ❓ |
| IOCA-G-008 | This information contains examples of data and reports used in daily business operations. T o illustrate them in a complete manner, some examples include the names of individuals, companies, brands, or products. These names are fictitious and any similarity to the names and addresses used by an actual business enterprise is entirely coincidental. | ❓ |
| IOCA-G-009 | Color Management Object Content Architecture InfoPrint Intelligent Printer Data Stream IPDS Mixed Object Document Content Architecture MO:DCA Ricoh Other company, product, or service names might be trademarks or service marks of others. | ❓ |
