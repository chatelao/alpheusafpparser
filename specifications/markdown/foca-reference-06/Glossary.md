# Glossary

This glossary contains terms that apply to the FOCA Architecture and also terms that apply to other related presentation architectures. If you do not find the term that you are looking for, please refer to the IBM Dictionary of Computing or the InfoPrint Dictionary of Printing.

The following definitions are provided as supporting information only, and are not intended to be used as a substitute for the semantics described in the body of this reference.

---

### A

**absolute coordinate.** One of the coordinates that identify the location of an addressable point with respect to the origin of a specified coordinate system. Contrast with relative coordinate.

**absolute move.** A method used to designate a new presentation position by specifying the distance from the designated axes to the new presentation position. The reference for locating the new presentation position is a fixed position as opposed to the current presentation position.

**absolute positioning.** The establishment of a position within a coordinate system as an offset from the coordinate system origin. Contrast with relative positioning.

**Abstract Syntax Notation One (ASN.1).** A notation for defining data structures and data types. The notation is defined in international standard ISO/IEC 8824(E). See also object identifier.

**ACK.** See Positive Acknowledge Reply.

**Acknowledge Reply.** A printer-to-host reply that returns printer information or reports exceptions. An Acknowledge Reply can be positive or negative. See also Positive Acknowledge Reply and Negative Acknowledge Reply.

**Acknowledgment Request.** A request from the host for information from the printer. An example of an Acknowledgment Request is the use of the acknowledgment-required flag by a host system to request an Acknowledge Reply from an attached printer.

**acknowledgment-required flag (ARQ).** A flag that requests a printer to return an Acknowledge Reply. The acknowledgment-required flag is bit zero of an IPDS command's flag byte.

**active coded font.** The coded font that is currently being used by a product to process text.

**additive primary colors.** Red, green, and blue light, transmitted in video monitors and televisions. When used in various degrees of intensity and variation, they create all other colors of light; when superimposed equally, they create white. Contrast with subtractive primary colors.

**addressable position.** A position in a presentation space or on a physical medium that can be identified by a coordinate from the coordinate system of the presentation space or physical medium. See also picture element. Synonymous with position.

**Advanced Function Presentation (AFP).** An open architecture for the management of presentable information that is developed by the AFP Consortium (AFPC). AFP comprises a number of data stream and data object architectures:
*   Mixed Object Document Content Architecture (MO:DCA); formerly referred to as AFPDS
*   Intelligent Printer Data Stream (IPDS) Architecture
*   AFP Line Data Architecture
*   Bar Code Object Content Architecture (BCOCA)
*   Color Management Object Content Architecture (CMOCA)
*   Font Object Content Architecture (FOCA)
*   Graphics Object Content Architecture for AFP (AFP GOCA)
*   Image Object Content Architecture (IOCA)
*   Metadata Object Content Architecture (MOCA)
*   Presentation Text Object Content Architecture (PTOCA)

**AEA.** See alternate exception action.

**AFM file.** A file containing the metric information required for positioning the characters of a font. The metric information contained in this file was extracted from a PFB file, in an ASCII file format defined by Adobe Systems Inc., and used for character positioning and page formatting.

**AFP.** See Advanced Function Presentation.

**AFP Consortium (AFPC).** A formal open standards body that develops and maintains AFP architecture. Information about the consortium can be found at www.afpcinc.org.

**AFP data stream.** A presentation data stream that is processed in AFP environments. The MO:DCA architecture defines the strategic AFP interchange data stream. The IPDS architecture defines the strategic AFP printer data stream.
