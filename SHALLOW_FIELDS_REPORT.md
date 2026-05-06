# Shallow Structured Field Implementations Report

This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).

**Total Shallow Fields Identified: 32**

## Package: base
*Internal base classes and common structured field implementations for the Alpheus AFP Parser.*
- StructuredFieldErrornouslyBuilt: Represents a structured field that was built incorrectly due to parsing errors.

## Package: bcoca
*Bar Code Object Content Architecture - Provides the structures for bar code data objects.*
- EBC_EndBarCodeObject: Terminates a bar code data object.

## Package: cmoca
*Color Management Object Content Architecture - Provides the structures for color management resources (CMRs).*
- CMR_ColorManagementResource: Carries a color management resource (CMR).

## Package: foca
*Font Object Content Architecture - Defines resources for fonts, character sets, and code pages.*
- BCF_BeginCodedFont: Names and begins a coded font object.
- ECF_EndCodedFont: Terminates a coded font object.
- ECP_EndCodePage: Terminates a code page object.
- EFN_EndFont: Terminates a font character set object.
- FNG_FontPatterns: Carries the character pattern data for a font character set.
- FNN_FontNameMap: Maps font names to global identifiers.

## Package: goca
*Graphics Object Content Architecture - Provides the structures for vector graphics objects.*
- EGR_EndGraphicsObject: Terminates a graphics data object.
- GAD_GraphicsData (Minimal/Empty override): Carries the drawing orders that define a graphics object.

## Package: lineData
*Definitions for line-printer data maps, page maps, and transmission subcases within the AFP environment.*
- BDX_BeginDataMapTransmitionSubcase: Names and begins a data map transmission subcase.
- BPM_BeginPageMap: Names and begins a page map object.
- DXD_DataMapTransmitionSubcaseDescriptor: Specifies the characteristics of a data map transmission subcase.
- EDM_EndDataMap: Terminates a data map object.
- EDX_EndDataMapTransmitionSubcase: Terminates a data map transmission subcase.
- EPM_EndPageMap: Terminates a page map object.
- IDM_InvokeDataMap: Identifies the data map to be used for formatting.

## Package: modca
*Mixed Object Document Content Architecture - The primary data stream architecture for AFP documents, defining the document's structure and component objects.*
- BII_BeginIMImageObject: Names and begins an IM image object.
- EII_EndIMImageObject: Terminates an IM image object.
- EIM_EndImageObject: Terminates an image object.
- EMO_EndOverlay: Terminates an overlay object.
- EOC_EndObjectContainer: Terminates an object container.
- EOG_EndObjectEnvironmentGroup: Terminates an Object Environment Group.
- EPF_EndPrintFile: Terminates a print file.
- EPS_EndPageSegment: Terminates a page segment object.
- ERS_EndResource: Terminates a resource object.
- IRD_IMImageRasterData: Carries the raster pattern for an IM image.
- NOP_NoOperation: Carries unarchitected data and has no architectural effect.
- OCD_ObjectContainerData: Carries the data for an object container.

## Package: modca_L
*MO:DCA Color Attribute Table (CAT) extensions, typically used for legacy or specific color-to-grayscale mapping.*
- CAT_ColorAttributeTable (Minimal/Empty override): Carries the color-to-grayscale mapping definitions.

## Package: ptoca
*Presentation Text Object Content Architecture - Defines the structures for presentation text objects.*
- EPT_EndPresentationTextObject: Terminates a presentation text object.
