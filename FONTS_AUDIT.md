# Font Definition and Parsing Audit

This document summarizes the audit of Font Definition and Parsing implementation against FOCA-06, MO:DCA-10, and PTOCA-04 specifications.

## Audit Summary

| Structured Field | Abbr | Spec | Parsing | Writing | XML Fast-path | Status | Notes |
| :--- | :--- | :--- | :---: | :---: | :---: | :---: | :--- |
| **Begin Coded Font** | BCF | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 128 |
| **Begin Code Page** | BCP | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 129 |
| **Begin Font** | BFN | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 131 |
| **Coded Font Control** | CFC | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 133 |
| **Coded Font Index** | CFI | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 134 |
| **Code Page Control** | CPC | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 137 |
| **Code Page Descriptor** | CPD | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 141 |
| **Code Page Index** | CPI | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 143 |
| **End Coded Font** | ECF | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 147 |
| **End Code Page** | ECP | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 148 |
| **End Font** | EFN | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 149 |
| **Font Control** | FNC | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 150 |
| **Font Descriptor** | FND | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 157 |
| **Font Patterns** | FNG | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 162 |
| **Font Index** | FNI | FOCA | ✅ | ✅ | ✅ | ✅ | **FIXED**: Aligned repeating group offsets |
| **Font Patterns Map** | FNM | FOCA | ✅ | ✅ | ✅ | ✅ | **FIXED**: Corrected RG structure (Wd/Ht/Offset) |
| **Font Name Map** | FNN | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 173 |
| **Font Orientation** | FNO | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 177 |
| **Font Position** | FNP | FOCA | ✅ | ✅ | ✅ | ✅ | Matches FOCA-06 pg 182 |
| **Map Coded Font F1** | MCF | MO:DCA | ✅ | ✅ | ✅ | ✅ | Matches MO:DCA-10 Appendix C |
| **Map Coded Font F2** | MCF | MO:DCA | ✅ | ✅ | ✅ | ✅ | Matches MO:DCA-10 pg 233 |
| **Map Data Resource** | MDR | MO:DCA | ✅ | ✅ | ✅ | ✅ | Matches MO:DCA-10 pg 243 |

### PTOCA Control Sequences

| Control Sequence | Abbr | Spec | Parsing | Writing | XML Fast-path | Status | Notes |
| :--- | :--- | :--- | :---: | :---: | :---: | :---: | :--- |
| **Set Coded Font Local** | SCFL | PTOCA | ✅ | ✅ | ✅ | ✅ | Matches PTOCA-04 pg 77 |

## Detailed Findings

### FNI - Font Index
- **Issue**: The initial implementation had incorrect offsets and member names in `FNI_RepeatingGroup`.
- **Correction**: Re-aligned fields according to Table 14 in FOCA-06:
    - 0-7: GCGID
    - 8-9: Character Increment
    - 10-11: Ascender Height
    - 12-13: Descender Depth
    - 14: Kernable Character Flags
    - 15: Reserved
    - 16-17: A-Space
    - 18-19: B-space
    - 20-21: C-Space
    - 22-23: Reserved
    - 24-25: Baseline Offset
    - 26-27: Reserved

### FNM - Font Patterns Map
- **Issue**: The implementation was using a structure more consistent with IOCA or old AFP, using `charDataOffset` and `charDataCount` in the repeating group.
- **Correction**: Aligned with FOCA-06 page 171:
    - 0-1: Character Box Width
    - 2-3: Character Box Height
    - 4-7: Pattern Data Offset

### XML Fast-Paths
Manual StAX2 fast-path implementation was verified in `AfpJacksonXmlWriter.java`. All fields include the `page` attribute for spatial awareness and avoid reflection for high-performance serialization.

- FOCA Fields: `writeFndDirectly`, `writeFniDirectly`, `writeFnmDirectly`, `writeFnoDirectly`, `writeFnpDirectly`, `writeFnnDirectly`, `writeFngDirectly`, `writeCfcDirectly`, `writeCfiDirectly`, `writeCpcDirectly`, `writeCpiDirectly`, `writeCpdDirectly`.
- MO:DCA Fields: `writeMcf1Directly`, `writeMcfDirectly`, `writeMdrDirectly`.
- PTOCA Sequences: Handled in `writeControlSequence` via switch case for `SCFL_SetCodedFontLocal`.

## Conclusion
Font Definition and Parsing is now fully implemented and compliant with the specifications. identified bugs in `FNI` and `FNM` have been resolved.
