# Chapter 6. Font Interchange Formats

Font Information Interchange is defined to be the transfer of font information between or among processing systems, software processes, and hardware devices. You can transfer font information between any two systems, processes, or devices as long as both parties recognize the same format. The font information that you interchange can be a complete font resource, selected components of a font resource, or reference information about a font resource.

This chapter specifies the public font information interchange formats supported by this architecture.

## AFP System Font Resource

The format for font resource data, to be loaded and managed by Advanced Function Presentation (AFP) software, is defined by the following syntax specification. The syntax specification is divided into three sections: objects, structured fields, and triplets.

### Objects

#### Coded Font
A coded font is an AFP font resource object that associates AFP font character set objects with AFP code page objects.

The structured fields that compose the coded font must appear in the following sequence:
1.  **Begin Coded Font (BCF)**
2.  **Coded Font Control (CFC)**
3.  **Coded Font Index (CFI)**
4.  **End Coded Font (ECF)**
5.  **No Operation (NOP)**: (Optional)

#### Code Page
A code page is a font resource object that associates graphic character global IDs (GCGIDs) to code points.

The structured fields that compose the code page must appear in the following sequence:
1.  **Begin Code Page (BCP)**
2.  **Code Page Descriptor (CPD)**
3.  **Code Page Control (CPC)**
4.  **Code Page Index (CPI)**
5.  **End Code Page (ECP)**
6.  **No Operation (NOP)**: (Optional)

#### Font Character Set
A font character set is a font resource object that contains descriptive and metric information for the whole font, and metric and shape information for each character identifier.

The structured fields must appear in the following sequence:
1.  **Begin Font (BFN)**
2.  **Font Descriptor (FND)**
3.  **Font Control (FNC)**
4.  **Font Patterns Map (FNM)**
5.  **Font Orientation (FNO)**
6.  **Font Position (FNP)**
7.  **Font Index (FNI)**
8.  **Font Name Map (FNN)**
9.  **Font Patterns (FNG)**
10. **End Font (EFN)**
11. **No Operation (NOP)**: (Optional)

---

### Structured Fields

#### Structured Field Introducer
A structured field introducer begins each structured field.

**Table 13. Structured Field Introducer**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–1 | UBIN | Length | 8–32,767 | Length of Structured Field | M |
| 2–4 | CODE | SFID | See Below | Structured Field Identifier | M |
| 5 | BITS | SFFlags | See Below | Control Flags | M |
| 6–7 | UBIN | Sequence Number | 1–32,767 | The number of the structured field in the object. | M |
| 8 | UBIN | Extension Length | 1–255 | Length of extension data | O |
| 9–n | UNDF | Extension Data | | Up to 254 bytes of data | O |
| x–y | UNDF | Padding Data | | Up to 32,759 bytes of data | O |

**Table 14. Structured-Field Identifiers**

| Field Abbr | Assigned Number | Full Field Name |
| :--- | :--- | :--- |
| **BCF** | X'D3A88A' | Begin Coded Font |
| **BCP** | X'D3A887' | Begin Code Page |
| **BFN** | X'D3A889' | Begin Font |
| **CFC** | X'D3A78A' | Coded Font Control |
| **CFI** | X'D38C8A' | Coded Font Index |
| **CPC** | X'D3A787' | Code Page Control |
| **CPD** | X'D3A687' | Code Page Descriptor |
| **CPI** | X'D38C87' | Code Page Index |
| **ECF** | X'D3A98A' | End Coded Font |
| **ECP** | X'D3A987' | End Code Page |
| **EFN** | X'D3A989' | End Font |
| **FNC** | X'D3A789' | Font Control |
| **FND** | X'D3A689' | Font Descriptor |
| **FNG** | X'D3EE89' | Font Patterns |
| **FNI** | X'D38C89' | Font Index |
| **FNM** | X'D3A289' | Font Patterns Map |
| **FNN** | X'D3AB89' | Font Names (Outline Fonts Only) |
| **FNO** | X'D3AE89' | Font Orientation |
| **FNP** | X'D3AC89' | Font Position |
| **NOP** | X'D3EEEE' | No Operation |

---

### BCF – D3A88A – Begin Coded Font
The Begin Coded Font (BCF) structured field begins a coded font object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CFName | | Coded Font Name | O |

---

### BCP – D3A887 – Begin Code Page
The Begin Code Page (BCP) structured field begins a code page object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CPName | | Code Page Name | O |
| 8–n | UNDF | Triplets | See Below | Self Defining Triplets | O |

---

### BFN – D3A889 – Begin Font
The Begin Font (BFN) structured field begins the font character set object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CSName | | Font Character Set Name | O |
| 8–n | UNDF | Triplets | See Below | Self Defining Triplets | O |

---

### CFC – D3A78A – Coded Font Control
The Coded Font Control (CFC) structured field specifies the length of the repeating group in the Coded Font Index (CFI) structured field.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | CFIRGLen | X'19' | CFI Repeating Group Length | M |
| 1 | UBIN | Retired | X'01' | Retired Parameter | M |
| 2–end | Triplet | | X'79' | Metric Adjustment triplet | O |

---

### CFI – D38C8A – Coded Font Index
The Coded Font Index (CFI) structured field names the font character sets and code pages for the font.

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | FCSName | | Font Character Set Name | M |
| 8–15 | CHAR | CPName | | Code Page Name | M |
| 16–17 | UBIN | SVSize | 0–65,535 | Specified Vertical Font Size (in 1440ths) | M |
| 18–19 | UBIN | SHScale | 0–65,535 | Specified Horizontal Scale Factor (in 1440ths) | M |
| 20–23 | UNDF | | X'00000000' | Reserved | M |
| 24 | UBIN | Section | | Section Number | M |

---

### CPC – D3A787 – Code Page Control
The Code Page Control (CPC) contains information about the code page.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | DefCharID | | Default Graphic Character Global ID | M |
| 8 | BITS | PrtFlags | See Below | Default Character Use Flags | M |
| 9 | CODE | CPIRGLen | X'0A', X'0B', X'FE', X'FF' | CPI Repeating Group Length | M |
| 10 | UBIN | VSCharSN | X'00'–X'FF' | Space Character Section Number | M |
| 11 | UBIN | VSChar | X'00'–X'FF' | Space Character Code Point | M |
| 12 | BITS | VSFlags | See Below | Code Page Use Flags | M |
| +0–3 | UBIN | Unicode | | Optional Unicode value for default ID | O |

---

### CPD – D3A687 – Code Page Descriptor
The Code Page Descriptor (CPD) structured field describes the code page.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–31 | CHAR | CPDesc | | Code Page Description | M |
| 32–33 | UBIN | GCGIDLen | 8 | Graphic Character GID Length | M |
| 34–37 | UBIN | NumCdPts | 1–65,535 | Number of Assigned Code Points | M |
| 38–39 | UBIN | GCSGID | | Graphic Character Set GID | M |
| 40–41 | UBIN | CPGID | | Code Page Global Identifier | M |
| 42–43 | UBIN | EncScheme | See Below | Encoding Scheme | O |

---

### CPI – D38C87 – Code Page Index
In a series of repeating groups, the Code Page Index (CPI) associates character IDs with code points.

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | GCGID | | Graphic Character Global ID | M |
| 8 | BITS | PrtFlags | See Below | Graphic Character Use Flags | M |
| 9 or 9–10 | UBIN | CodePoint | 0–65,535 | Code Point | M |
| +0 | UBIN | Count | X'00'–X'FF' | Number of Unicode scalar values | O |
| ++0–3 | UBIN | Unicode | | Unicode scalar value | O |

---

### ECF – D3A98A – End Coded Font
The End Coded Font (ECF) structured field ends the coded font object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CFName | | Coded Font Name | O |

---

### ECP – D3A987 – End Code Page
The End Code Page (ECP) structured field ends the code page object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CPName | | Code Page Name | O |

---

### EFN – D3A989 – End Font
The End Font (EFN) structured field ends the font character set object.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | CSName | | Font Character Set Name | O |

---

### FNC – D3A789 – Font Control
The Font Control (FNC) structured field provides defaults and information about the font character set.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | Retired | X'01' | Retired Parameter | M |
| 1 | CODE | PatTech | X'05', X'1E', X'1F' | Pattern Technology Identifier | M |
| 2 | UNDF | Reserved | X'00' | Reserved | M |
| 3 | BITS | FntFlags | See Below | Font Use Flags | M |
| 4 | CODE | XUnitBase | X'00', X'02' | X Unit Base | M |
| 5 | CODE | YUnitBase | X'00', X'02' | Y Unit Base | M |
| 6–7 | UBIN | XftUnits | | X Units per Unit Base | M |
| 8–9 | UBIN | YftUnits | | Y Units per Unit Base | M |
| 10–11 | UBIN | MaxBoxWd | 0–32,767 | Max Character Box Width | M |
| 12–13 | UBIN | MaxBoxHt | 0–32,767 | Max Character Box Height | M |
| 14 | UBIN | FNORGLen | X'1A' | FNO Repeating Group Length | M |
| 15 | UBIN | FNIRGLen | | FNI Repeating Group Length | M |
| 16 | CODE | PatAlign | X'00', X'02', X'03' | Pattern Data Alignment Code | M |
| 17–19 | UBIN | RPatDCnt | | Raster Pattern Data Count | M |
| 20 | UBIN | FNPRGLen | X'16' | FNP Repeating Group Length | M |
| 21 | UBIN | FNMRGLen | X'00', X'08' | FNM Repeating Group Length | M |
| 22 | CODE | ResXUBase | X'00' | Shape resolution X Unit Base | O |
| 23 | CODE | ResYUBase | X'00' | Shape resolution Y Unit Base | O |
| 24–25 | UBIN | XfrUnits | | Shape resolution X units | O |
| 26–27 | UBIN | YfrUnits | | Shape resolution Y units | O |
| 28–31 | UBIN | OPatDCnt | | Outline Pattern Data Count | O |
| 32–34 | UNDF | Reserved | X'000000' | Reserved | O |
| 35 | UBIN | FNNRGLen | X'0C' | FNN Repeating Group Length | O |
| 36–39 | UBIN | FNNDCnt | | FNN Data Count | O |
| 40–41 | UBIN | FNNMapCnt | 0–65,535 | FNN Name Count | O |
| 42–nn | Triplets | | | Self-Defining Triplets | O |

---

### FND – D3A689 – Font Descriptor
The Font Descriptor (FND) specifies the overall characteristics of a font character set.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–31 | CHAR | TypeFcDesc | | Typeface Description | M |
| 32 | CODE | FtWtClass | 1–9 | Weight Class | M |
| 33 | CODE | FtWdClass | 1–9 | Width Class | M |
| 34–35 | UBIN | MaxPtSize | | Max Vertical Size (10ths of Pt) | M |
| 36–37 | UBIN | NomPtSize | | Nominal Vertical Size | M |
| 38–39 | UBIN | MinPtSize | | Min Vertical Size | M |
| 40–41 | UBIN | MaxHSize | 0–X'7FFE' | Max Horizontal Size (1440ths) | M |
| 42–43 | UBIN | NomHSize | 0–X'7FFE' | Nominal Horizontal Size | M |
| 44–45 | UBIN | MinHSize | 0–X'7FFE' | Minimum Horizontal Size | M |
| 46 | CODE | DsnGenCls | 0–255 | Design General Class (ISO) | M |
| 47 | CODE | DsnSubCls | 0–255 | Design Subclass (ISO) | M |
| 48 | CODE | DsnSpcGrp | 0–255 | Design Specific Group (ISO) | M |
| 49–63 | UBIN | Reserved | | Reserved | M |
| 64–65 | BITS | FtDsFlags | See Below | Font Design Flags | M |
| 66–75 | UBIN | Reserved | | Reserved | M |
| 76–77 | UBIN | GCSGID | | Graphic Character Set GID | M |
| 78–79 | UBIN | FGID | | Font Typeface GID (FGID) | M |
| 80–nn | Triplets | | | Self Defining Triplets (X'02') | O |

---

### FNG – D3EE89 – Font Patterns
The Font Patterns (FNG) structured field carries the character shape data (raster patterns or outline data).

**Outline Font Repeating Group:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | : :--- | :--- | :--- | :--- |
| 0–3 | UBIN | OFLLen | | Length of the outline font object | M |
| 4–7 | UBIN | Checksum | | Checksum value | M |
| 8–9 | UBIN | TIDLen | | Technology-specific ID length | M |
| 10–n | UBIN | TIDName | | Technology-specific ID | O |
| n+1..n+2 | UBIN | ODescLen | | Descriptor length | O |
| n+3..m | UNDF | ObjDesc | | Descriptor data | O |
| m+1..end | UNDF | ObjData | | Object data | O |

---

### FNI – D38C89 – Font Index
For each character in a raster font, the Font Index (FNI) describes specific characteristics and points to an entry in the Font Patterns Map (FNM).

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | CHAR | GCGID | | Graphic Character Global ID | M |
| 8–9 | UBIN | CharInc | | Character Increment | M |
| 10–11 | SBIN | AscendHt | | Ascender Height | O |
| 12–13 | SBIN | DescendDp | | Descender Depth | O |
| 14–15 | UBIN | Reserved | X'0000' | Reserved | O |
| 16–17 | UBIN | FNMCnt | | FNM Index | O |
| 18–19 | SBIN | ASpace | | A-Space | O |
| 20–21 | UBIN | BSpace | | B-space | O |
| 22–23 | SBIN | CSpace | | C-Space | O |
| 24–25 | UBIN | Reserved | X'0000' | Reserved | O |
| 26–27 | SBIN | BaseOset | | Baseline Offset | O |

---

### FNM – D3A289 – Font Patterns Map
The Font Patterns Map (FNM) structured field describes some characteristics of the font character patterns.

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–1 | UBIN | CharBoxWd | | Character Box Width | M |
| 2–3 | UBIN | CharBoxHt | | Character Box Height | M |
| 4–7 | UBIN | PatDOset | | Pattern Data Offset | M |

---

### FNN – D3AB89 – Font Name Map
The Font Name Map is used to map IBM character names to the character names in outline fonts.

**Section 1:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | IBM format | X'02' | IBM character ID format | M |
| 1 | CODE | Technology | X'03', X'05' | Tech-specific ID format | M |

**Section 2 (Repeating Groups):**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–7 | UNDF | GCGID | | Graphic Character Global ID | O |
| 8–11 | UBIN | TSOffset | | Tech-specific identifier offset | O |

**Section 3 (Variable Length):**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TSIDLen | 2–128 | Tech-specific ID length | O |
| 1–n | UNDF | TSID | | Tech-specific identifier | O |

#### FNN Example

| Offset | Meaning | Value | Comments |
| :--- | :--- | :--- | :--- |
| 0 | GCGID Type | X'02' | Second section in EBCDIC |
| 1 | GCGID Type | X'03' | Third section in Adobe ASCII |
| 2–9 | GCGID | LA010000 | GCGID for lowercase 'a' |
| 10–13 | Offset | X'0000005F' | 95 |
| 14–21 | GCGID | LA020000 | GCGID for uppercase 'A' |
| 22–25 | Offset | X'00000061' | 97 |
| ... | ... | ... | ... |
| 95 | TSID Length | 2 | |
| 96 | TSID | a | |
| 97 | TSID Length | 2 | |
| 98 | TSID | A | |

---

### FNO – D3AE89 – Font Orientation
Each repeating group in the Font Orientation (FNO) structured field applies to one character rotation.

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–1 | UBIN | Reserved | X'0000' | Reserved | M |
| 2–3 | UBIN | CharRot | | Character Rotation | M |
| 4–5 | SBIN | MaxBOset | | Max Baseline Offset | M |
| 6–7 | UBIN | MaxCharInc | | Max Character Increment | M |
| 8–9 | UBIN | SpCharInc | | Space Character Increment | M |
| 10–11 | UBIN | MaxBExt | | Max Baseline Extent | M |
| 12 | BITS | OrntFlgs | | Orientation Control Flags | M |
| 13 | UBIN | Reserved | X'00' | Reserved | M |
| 14–15 | UBIN | EmSpInc | | Em-Space Increment | M |
| 16–17 | UBIN | Reserved | X'0000' | Reserved | M |
| 18–19 | UBIN | FigSpInc | | Figure Space Increment | M |
| 20–21 | UBIN | NomCharInc | | Nominal Character Increment | M |
| 22–23 | UBIN | DefBInc | 0–65,535 | Default Baseline Increment | M |
| 24–25 | SBIN | MinASp | | Minimum A-Space | M |

---

### FNP – D3AC89 – Font Position
The Font Position (FNP) structured field describes the common characteristics of all characters.

**Repeating Group Definition:**

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–1 | UBIN | Reserved | X'0000' | Reserved | M |
| 2–3 | SBIN | LcHeight | | Lowercase Height | M |
| 4–5 | SBIN | CapMHt | | Cap-M Height | M |
| 6–7 | SBIN | MaxAscHt | | Max Ascender Height | M |
| 8–9 | SBIN | MaxDesDp | | Max Descender Depth | M |
| 10–14 | UBIN | Reserved | | Reserved | M |
| 15 | UBIN | Retired | X'01' | Retired Parameter | M |
| 16 | UBIN | Reserved | X'00' | Reserved | M |
| 17–18 | UBIN | UscoreWd | | Underscore Width (units) | M |
| 19 | UBIN | UscoreWdf | X'00' | Underscore Width (fraction) | M |
| 20–21 | SBIN | UscorePos | | Underscore Position | M |

---

### NOP – D3EEEE – No Operation
Can carry comments or unarchitected data.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0–end | UNDF | NopData | Any value | Comment data | O |

---

## Triplets

### X'02' – Fully Qualified Name Triplet
Enables identification using Global Identifiers.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TripLen | 5–254 | Triplet Length | M |
| 1 | CODE | TripID | X'02' | Triplet Identifier | M |
| 2 | CODE | FQNType | X'07', X'08' | GID usage type | M |
| 3 | | | 0 | Reserved | M |
| 4–253 | CHAR | FQName | | GID of the object | M |

---

### X'62' – Local Date and Time Stamp Triplet
Identifies object creation/revision date and time.

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TripLen | 17 | Triplet Length | M |
| 1 | CODE | TripID | X'62' | Triplet Identifier | M |
| 2 | CODE | TSType | 0, 1, 3 | Time Stamp Type | M |
| ... | ... | ... | ... | ... | ... |

---

### X'63', Type 1 – CRC Resource Management Triplet

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TripLen | 6 | Triplet Length | M |
| 1 | CODE | TripID | X'63' | Triplet Identifier | M |
| 2 | CODE | FmtQual | X'01' | Format Qualifier | M |
| 3–4 | UBIN | RMValue | | Retired RM value | M |
| 5 | BITS | ResClassFlg | | Resource Class Flags | M |

---

### X'64' – Object Origin Identifier Triplet

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TripLen | 61 | Triplet Length | M |
| 1 | CODE | TripID | X'64' | Triplet Identifier | M |
| 2 | CODE | FmtQual | | Format Qualifier | M |
| 3–10 | CHAR | HostID | | Host/System Identifier | M |
| 11–16 | CHAR | MediaID | | Media Identifier | M |
| 17–60 | CHAR | DataSID | | Data Set Identifier | M |

---

### X'6D' – Extension Font Triplet

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | TripLen | 4 | Triplet Length | M |
| 1 | CODE | TripID | X'6D' | Triplet Identifier | M |
| 2–3 | CODE | GCSGID | | Base font GCSGID | M |

---

### X'79' – Metric Adjustment Triplet

| Offset | Type | Name | Range | Meaning | M/O |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 0 | UBIN | Length | X'0F' | Triplet Length | M |
| 1 | CODE | TID | X'79' | Triplet Identifier | M |
| 2 | CODE | UnitBase | X'00' | Unit base | M |
| 3–4 | UBIN | XUPUB | | Units per unit base X | M |
| 5–6 | UBIN | YUPUB | | Units per unit base Y | M |
| 7–8 | SBIN | H-Inc | | Horizontal increment | M |
| 9–10 | SBIN | V-Inc | | Vertical increment | M |
| 11–12 | SBIN | H-Base | | Horizontal baseline adjustment | M |
| 13–14 | SBIN | V-Base | | Vertical baseline adjustment | M |

---

## Supplemental Font Formats

*   **SAA CPI System Font Resource**: Query Font Metrics calls.
*   **IPDS Device Font Resource**: Loaded-Font Command Set.
*   **MO:DCA Presentation Document Font Reference**: Map Coded Font (MCF).
*   **SAA CPI System Font Reference**: Create Logical Font program call.
*   **IPDS Device Font Reference**: Load Font Equivalence (LFE) command.
