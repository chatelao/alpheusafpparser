# Shallow Structured Field Implementations Report

This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).

**Total Shallow Fields Identified: 9**

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
