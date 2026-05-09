# Chapter 2. Introduction to CMOCA

The Color Management Object Content Architecture (CMOCA) defines objects that provide color management in presentation environments. These objects are called Color Management Resources (CMRs). CMOCA has the following objectives:

*   Consistent output across different devices
*   Accurate output, to the best of the device capability, from a wide variety of inputs provided that the input color information is properly described
*   Consistent output across different data streams
*   Flexible controls that enable customers to obtain output to their exact specifications

The architecture described in this document is defined in the terms of the AFP architectures to support color management in AFP environments, but care has been taken to make the mechanisms applicable to other presentation environments as well.

The device that presents the data could be a printer, a display, or other system. This document frequently references printers but it should be understood that the architecture also applies to displays and other presentation devices.

A Color Management Resource (CMR) is an architected resource that is used to carry the color management information required to render a print file, document, page, or data object. Each CMR carries a single type of color management resource. There are five types of CMRs:

1.  Halftone
2.  Tone Transfer Curve
3.  Color Conversion
4.  Link Color Conversion
5.  Indexed

**Note:** Not all CMR types are applicable for a particular kind of presentation device; for instance, halftones are not applicable for a display.

A CMR can reflect processing that has been done on an object, in which case it is referred to as an **audit CMR**, or it can specify processing that is to be done to an object, in which case it is referred to as an **instruction CMR**. Finally, there is a special case of an audit and instruction color conversion pair that has been combined to produce a link color conversion. This combined color conversion is called a **link CMR**.

In AFP environments, CMRs are processed as AFP resources by print servers so they can be downloaded once, captured, and used repeatedly without additional downloads. CMRs are also applicable to non-AFP environments such as PostScript, PDF, and PCL®.

The primary purpose of the Color Management Object Content Architecture is to provide a standard definition for color management resources that are used for controlling presentation of color objects. “Color objects”, as used in the document, means full-color, grayscale, and monochrome objects. This standardization provides conventions and directions for current and future products to present objects in a consistent way.

Development of CMOCA has the following goals:

*   To allow a means to represent color management information in any environment
*   To use a format that is flexible enough to allow it to exist intact in interactive, presentation, and interchange environments that are defined in the following data stream architectures:
    *   Intelligent Printer Data Stream (IPDS) and
    *   Mixed Object Document Content Architecture (MO:DCA)
*   To describe the CMR in terms of architected tags
*   To use industry-standard constructs when architecting the CMRs
*   To allow the CMR to be fully described in device-independent and process-independent terms
*   To use a naming convention for the CMRs that allow device-specific color resources to be substituted for generic resources
*   To define CMRs so that multiple CMRs can be invoked at one time, and a hierarchy can be searched to determine the appropriate CMRs to use

In AFP environments, CMRs will be carried within an object container. CMRs can be associated with a document component at various levels:

1.  Print file
2.  Document
3.  A group of pages or sheets
4.  Page/Overlay
5.  Data Object - for example, IOCA, EPS, TIFF

Within the IPDS data stream, CMRs are activated and deactivated like all other IPDS resources but the CMR is not used until it is explicitly invoked (except for certain Link Color Conversions CMRs, that need not be invoked). Within the device, IPDS hierarchical rules are used to determine which CMRs are actually applied.
