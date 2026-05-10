# MO:DCA Implementation Gap Analysis

This report compares the current Java implementation of MO:DCA in the Alpheus AFP Parser against the MO:DCA Reference (Version 10) specifications.

## 1. Structured Field Coverage

### Missing Structured Fields
The following MO:DCA structured fields are defined in the specification but missing from `SFTypeID.java` and have no implementation class:

| Acronym | Hex ID   | Name |
| :--- | :--- | :--- |
| MPT | X'D3AB9B' | Map Presentation Text |

### Cross-Architecture Structured Fields
Several structured fields defined in the MO:DCA specification are implemented in architecture-specific packages (BCOCA, GOCA, IOCA, PTOCA) as they primarily carry data for those objects:

- **BCOCA:** BBC, BDA, BDD, EBC
- **GOCA:** BGR, EGR, GAD, GDD
- **IOCA:** IDD, IPD
- **PTOCA:** BPT, EPT, PTD, PTX

### Implementation Notes on Specific SFs
- **MCF (Map Coded Font):** Implemented as `MCF_MapCodedFont_Format1` (X'D3B18A') and `MCF_MapCodedFont_Format2` (X'D3AB8A'). Format 2 is the primary modern form.
- **PGP (Page Position):** Implemented as `PGP_PagePosition_Format1` (X'D3ACAF') and `PGP_PagePosition_Format2` (X'D3B1AF').
- **PTD (Presentation Text Descriptor):** Implemented as `PTD_PresentationTextDataDescriptor_Format1` (X'D3A69B') and `Format2` (X'D3B19B') in the `ptoca` package.

## 2. Triplet Coverage

### Missing Triplets
The following Triplets are defined in the MO:DCA specification but are missing from the `TripletID` enum in `Triplet.java`:

| Hex ID | Name |
| :--- | :--- |
| X'8F' | MO:DCA Function Set |
| X'9D' | Keep Group Together |
| X'9E' | Setup Name |

### Implementation Gaps
- **Triplet X'FF' (Triplet Extender):** While the ID exists in the code (referred to in logic), there is no dedicated `public static class TripletExtender` implementation to handle the concatenation logic.
- **Triplet X'21':** This ID is shared between `ResourceObjectType` and `ObjectFunctionSetSpecification_Retired`. The `TripletParser` contains special logic to disambiguate them.

## 3. Field-Level Discrepancies and Bugs

### Critical Bug: MMC (Medium Modification Control)
The implementation of `MMC_MediumModificationControl.decodeAFP` contains a logic error. It only parses the **first** keyword and ignores the rest of the structured field data:

```java
// Current buggy implementation
if (actualLength > 2) {
  keywords = new ArrayList<MMC_KeyWord>();
  int pos = 2;
  MMC_KeyWord kw = new MMC_KeyWord();
  kw.decodeAFP(sfData, offset + pos, actualLength - pos, config);
  keywords.add(kw);
  pos += 2; // BUG: Loop is missing! Only one keyword is parsed.
}
```

### Shallow vs. Deep Support
Most MO:DCA Structured Fields that primarily carry triplets (e.g., `TLE`, `BAG`, `BDI`) are implemented using `StructuredFieldBaseTriplets` or `StructuredFieldBaseNameAndTriplets`. This provides "Deep" support for all contained triplets but "Shallow" support for the SF-specific fixed fields if they are just inherited.

- **PGD (Page Descriptor):** Correctly implements fixed fields (Units, Size) and then delegates to base for triplets.
- **OBP (Object Area Position):** Correctly implements the repeating group structure.
- **MCF Format 1:** Correctly implements the legacy 28/30-byte repeating group structure.

## 4. Summary

Overall, the MO:DCA implementation is **Highly Comprehensive**.

- **SF Coverage:** >98% (Only MPT missing).
- **Triplet Coverage:** >95% (Only 3 minor triplets missing).
- **Architecture Integrity:** Well-organized separation between MO:DCA containers and OCA-specific data objects.

**Recommendations:**
1. Fix the loop in `MMC_MediumModificationControl.decodeAFP`.
2. Add the `MPT` (Map Presentation Text) structured field.
3. Add the missing triplets (X'8F', X'9D', X'9E').
4. Implement `TripletExtender` logic in `TripletParser` for long GIDs.
