# Appendix C. Pattern Technology Information

The following information defines the technologies which have been implemented for the definition of character shapes. Each technology is associated with a value of the Technology parameter in the Font Control Structured Field. This information is provided for information only and is not considered part of the font architecture, nor is it under control mode.

### CID Keyed Outline Font Technology
The data format for this outline technology is documented in *Adobe Type 1 Font Format*, Adobe Systems Incorporated, 1990, and *Composite Font Extensions*, Adobe Systems Incorporated, 1989.

The following notes apply to the FOCA implementation of the CID Keyed outline font technology:
*   The CID Keyed font files are totally contained within the FOCA Pattern Data parameter.
*   All character string data within the CID Keyed font file uses the ASCII character encoding technique.
*   The glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM Graphic Character Global Identifiers. Implementations must consider the possibility of different graphic character GID encodings and perform any necessary mapping.

### Type 1 PFB Outline Font Technology
The data format for this outline technology is documented in *Adobe Type 1 Font Format*, Adobe Systems Incorporated, 1990.

The following notes apply to the FOCA implementation of the Adobe Type 1 PFB outline font technology:
*   The Type 1 PFB file is totally contained within the FOCA Pattern Data parameter.
*   All character string data within the Type 1 PFB file uses the ASCII character encoding technique.
*   The PFB glyph procedures will contain the Adobe glyph names, while the FOCA metrics file may use IBM Graphic Character Global Identifiers. Implementations must consider the possibility of different graphic character GID encodings and perform any necessary mapping.

### TrueType/OpenType Outline Font Technology
The data format for the TrueType outline technology is documented in the *TrueType Font Files Technical Specification* from Microsoft® Corporation and the *TrueType Reference Manual* from Apple Computer, Inc.

The OpenType font format is an extension of the TrueType font format that allows better support for international character sets and broader multi-platform support. The OpenType font format, which was developed jointly by the Adobe and Microsoft Corporations, is further described in the OpenType Specification from Microsoft.

**Note:** These technologies are not supported within FOCA font objects, but are supported within AFP data streams as data object resources.

### Laser Matrix N-Bit Wide Horizontal Sections
An image is formatted as a single rectangle in the binary element sequence of a unidirectional raster scan with no interlaced fields and with parallel raster lines, from left to right (plus x-direction), from top to bottom (plus y-direction). Each binary element representing a pel, after decompression, without grayscale, is 0 for no dot, 1 for dot. More than one binary element can represent a pel, after decompression, corresponding to a grayscale algorithm. A pel is nominally centered about the point at which it appears.

Scan lines may range from 1 bit wide to the device limit.

---

## Notices

The AFP Consortium or consortium member companies might have patents or pending patent applications covering subject matter described in this document. The furnishing of this document does not give you any license to these patents.

The following statement does not apply to the United Kingdom or any other country where such provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICATION "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Some states do not allow disclaimer of express or implied warranties in certain transactions, therefore, this statement might not apply to you.

This publication could include technical inaccuracies or typographical errors. Changes are periodically made to the information herein; these changes will be incorporated in new editions of the publication. The AFP Consortium might make improvements and/or changes in the architecture described in this publication at any time without notice.

Any references in this publication to Web sites are provided for convenience only and do not in any manner serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for this architecture and use of those Web sites is at your own risk.

The AFP Consortium may use or distribute any information you supply in any way it believes appropriate without incurring any obligation to you.

## Trademarks

These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries, or both: ACMA, Advanced Function Presentation, AFP, AFPCC, AFP Color Consortium, AFP Color Management Architecture, Bar Code Object Content Architecture, BCOCA, CMOCA, Color Management Object Content Architecture, InfoPrint®, Intelligent Printer Data Stream, IPDS, Mixed Object Document Content Architecture, MO:DCA, Ricoh®.

Adobe®, the Adobe logo, PostScript®, and the PostScript logo are either registered trademarks or trademarks of Adobe Systems Incorporated in the United States and/or other countries.

AFPC and AFP Consortium are trademarks of the AFP Consortium.

IBM® is a registered trademark of the International Business Machines Corporation. MVS, PrintManager, Print Services Facility, SAA®, and Systems Application Architecture® are trademarks of IBM.

ISO® is a registered trademark of the International Organization for Standardization.

Microsoft® is a registered trademark of the Microsoft Corporation.

UP3I is a trademark of UP3I Limited.
