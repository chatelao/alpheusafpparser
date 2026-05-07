# Shallow Structured Field Implementations Report

This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).

**Total Shallow Fields Identified: 15**

## Package: bcoca
*Bar Code Object Content Architecture - Provides the structures for bar code data objects.*
- EBC_EndBarCodeObject: Terminates a bar code data object.

## Package: foca
*Font Object Content Architecture - Defines resources for fonts, character sets, and code pages.*
- BCF_BeginCodedFont: Names and begins a coded font object.
- ECF_EndCodedFont: Terminates a coded font object.
- ECP_EndCodePage: Terminates a code page object.
- EFN_EndFont: Terminates a font character set object.

## Package: goca
*Graphics Object Content Architecture - Provides the structures for vector graphics objects.*
- EGR_EndGraphicsObject: Terminates a graphics data object.
- GAD_GraphicsData (Minimal/Empty override): Carries the drawing orders that define a graphics object.

## Package: lineData
*Definitions for line-printer data maps, page maps, and transmission subcases within the AFP environment.*
- BDX_BeginDataMapTransmitionSubcase: Names and begins a data map transmission subcase.
- BPM_BeginPageMap: Names and begins a page map object.
- EDM_EndDataMap: Terminates a data map object.
- EDX_EndDataMapTransmitionSubcase: Terminates a data map transmission subcase.
- EPM_EndPageMap: Terminates a page map object.
- IDM_InvokeDataMap: Identifies the data map to be used for formatting.

## Package: modca_L
*MO:DCA Color Attribute Table (CAT) extensions, typically used for legacy or specific color-to-grayscale mapping.*
- CAT_ColorAttributeTable (Minimal/Empty override): Carries the color-to-grayscale mapping definitions.

## Package: ptoca
*Presentation Text Object Content Architecture - Defines the structures for presentation text objects.*
- EPT_EndPresentationTextObject: Terminates a presentation text object.
