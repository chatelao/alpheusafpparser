# Granular Test Coverage - MOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| MOCA-4-001 | An MO consists of a header followed by MO data. | ❓ |
| MOCA-4-002 | Metadata Object (MO) Syntax overview. | ❓ |
| MOCA-4-003 | Data contained in fixed-length fields that are encoded as UTF-16BE is left-aligned and padded with "@" (X'0040'). | ❓ |
| MOCA-4-004 | MOLength: MO length, including the MOLength field. | ❓ |
| MOCA-4-005 | MO header starts here. | ❓ |
| MOCA-4-006 | HeaderLength: MO header length, including the HeaderLength field. | ❓ |
| MOCA-4-007 | MOType: Descriptive (DES - X'0044 0045 0053'). | ❓ |
| MOCA-4-008 | MOFormat: AFP Tagging (AFPT - X'0041 0046 0050 0054'). | ❓ |
| MOCA-4-009 | MOFormat: Extensible Metadata Platform (XMP - X'0058 004D 0050 0040'). | ❓ |
| MOCA-4-010 | MOCompression: Uncompressed (NONE - X'004E 004F 004E 0045 0040 0040 0040 0040 0040 0040'). | ❓ |
| MOCA-4-011 | MOCompression: "Gzip" text compression (GZIP - X'0047 005A 0049 0050 0040 0040 0040 0040 0040 0040'). | ❓ |
| MOCA-4-012 | MOCompression: Efficient XML Interchange (EXI) compression (EXI - X'0045 0058 0049 0040 0040 0040 0040 0040 0040 0040'). | ❓ |
| MOCA-4-013 | Reserved field in MO header - should be set to zero. | ❓ |
| MOCA-4-014 | MONameLength: Length, in bytes, of the MOName field that follows. | ❓ |
| MOCA-4-015 | MOName: A human-readable MO name in UTF-16BE. | ❓ |
| MOCA-4-016 | Reserved for future use field at the end of name. | ❓ |
| MOCA-4-017 | MO header ends here. | ❓ |
| MOCA-4-018 | MOData: Any MO Data. | ❓ |
| MOCA-4-019 | M/O: Mandatory or Optional field designation. | ❓ |
| MOCA-4-020 | MOLength semantics and exception EC-0100. | ❓ |
| MOCA-4-021 | HeaderLength semantics and exception EC-0200. | ❓ |
| MOCA-4-022 | MOType semantics and exception EC-0220. | ❓ |
| MOCA-4-023 | MOFormat semantics and exception EC-0230. | ❓ |
| MOCA-4-024 | MOCompression semantics and exception EC-0240. | ❓ |
| MOCA-4-025 | MONameLength semantics and exception EC-0250. | ❓ |
| MOCA-4-026 | MOName semantics and exception EC-0210. | ❓ |
| MOCA-4-027 | MOData semantics and exception EC-0300. | ❓ |
| MOCA-4-028 | Both recognition and reporting of exception conditions is optional. | ❓ |
| MOCA-4-029 | MOCA Exception Conditions list. | ❓ |
| MOCA-4-030 | EC-0100 Invalid Length Value: The specified MOLength is invalid. | ❓ |
| MOCA-4-031 | EC-0200 Invalid Field Value: The specified HeaderLength is invalid. | ❓ |
| MOCA-4-032 | EC-0210 Invalid Field Value: The specified MOName is not valid UTF-16BE. | ❓ |
| MOCA-4-033 | EC-0220 Invalid or Unsupported Field Value: The specified MOType is invalid or unsupported. | ❓ |
| MOCA-4-034 | EC-0230 Invalid or Unsupported Field Value: The specified MOFormat is invalid or unsupported. | ❓ |
| MOCA-4-035 | EC-0240 Invalid or Unsupported Field Value: The specified MOCompression is invalid or unsupported. | ❓ |
| MOCA-4-036 | EC-0250 Invalid Field Value: The specified MONameLength is invalid. | ❓ |
| MOCA-4-037 | EC-0300 Invalid MOData: The specified MOData does not meet the specification associated with the indicated MOFormat. | ❓ |
| MOCA-4-038 | In the IPDS environment, MOCA exception conditions are mapped to IPDS exceptions by adding X'06' on the front. | ❓ |
| MOCA-GLO-001 | MOCA Glossary: Definitions and terms. | ❓ |
| MOCA-GLO-002 | MOCA Glossary: External references. | ❓ |
| MOCA-GLO-003 | MOCA Glossary: Informational nature of definitions. | ❓ |
| MOCA-GLO-004 | Advanced Function Presentation (AFP) definition. | ❓ |
| MOCA-GLO-005 | MO:DCA definition in glossary. | ❓ |
| MOCA-GLO-006 | IPDS definition in glossary. | ❓ |
| MOCA-GLO-007 | AFP Line Data Architecture definition. | ❓ |
| MOCA-GLO-008 | BCOCA definition in glossary. | ❓ |
| MOCA-GLO-009 | CMOCA definition in glossary. | ❓ |
| MOCA-GLO-010 | FOCA definition in glossary. | ❓ |
| MOCA-GLO-011 | AFP GOCA definition in glossary. | ❓ |
| MOCA-GLO-012 | IOCA definition in glossary. | ❓ |
| MOCA-GLO-013 | MOCA definition in glossary. | ❓ |
| MOCA-GLO-014 | PTOCA definition in glossary. | ❓ |
| MOCA-GLO-015 | AFP abbreviation definition. | ❓ |
| MOCA-GLO-016 | AFP Consortium (AFPC) definition. | ❓ |
| MOCA-GLO-017 | AFP data stream definition. | ❓ |
| MOCA-GLO-018 | AFPDS definition. | ❓ |
| MOCA-GLO-019 | AFP environment definition. | ❓ |
| MOCA-GLO-020 | AFP Tagging definition. | ❓ |
