# Shallow Structured Field Implementations Report

This report lists all Structured Field classes that do not provide their own payload parsing logic (shallow implementations).

**Total Shallow Fields Identified: 6**

## Package: base
*Internal base classes and common structured field implementations for the Alpheus AFP Parser.*
- StructuredFieldErrornouslyBuilt: Represents a structured field that was built incorrectly due to parsing errors.

## Package: cmoca
*Color Management Object Content Architecture - Provides the structures for color management resources (CMRs).*
- CMR_ColorManagementResource: Carries a color management resource (CMR).

## Package: foca
*Font Object Content Architecture - Defines resources for fonts, character sets, and code pages.*
- FNG_FontPatterns: Carries the character pattern data for a font character set.

## Package: modca
*Mixed Object Document Content Architecture - The primary data stream architecture for AFP documents, defining the document's structure and component objects.*
- IRD_IMImageRasterData: Carries the raster pattern for an IM image.
- NOP_NoOperation: Carries unarchitected data and has no architectural effect.
- OCD_ObjectContainerData: Carries the data for an object container.

