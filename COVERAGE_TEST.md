# Alpheus AFP Parser Test Coverage Report

This report provides a detailed overview of the test coverage for the Alpheus AFP Parser, specifically focusing on the architectural coverage of Structured Fields, Triplets, and Control Sequences.

## 1. Executive Summary

The Alpheus AFP Parser utilizes a combination of **Round-Trip Tests** (ensuring data integrity during decode/encode) and **Integration Tests** (verifying XML export and parser configuration).

| Category | Total Defined | Tested (Round-Trip) | Coverage (%) |
| :--- | :---: | :---: | :---: |
| **Structured Fields (SF)** | 137 | 107 | 78.1% |
| **MO:DCA Triplets** | 68 | 63 | 92.6% |
| **PTOCA Control Sequences** | 33 | 33 | 100.0% |
| **GOCA Drawing Orders** | ~40 | ~10 | ~25.0% |
| **IOCA Segments** | ~20 | ~5 | ~25.0% |

---

## 2. Structured Field Coverage by Architecture

### 2.1. MO:DCA (Mixed Object Document Content Architecture)
MO:DCA Structured Fields form the backbone of the AFP data stream.

| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BAG** | Begin Active Environment Group | ✅ |
| **BDI** | Begin Document Index | ✅ |
| **BDT** | Begin Document | ✅ |
| **BNG** | Begin Named Page Group | ✅ |
| **BOC** | Begin Object Container | ✅ |
| **BOG** | Begin Object Environment Group | ❌ |
| **BPF** | Begin Print File | ✅ |
| **BPG** | Begin Page | ✅ |
| **BPS** | Begin Page Segment | ✅ |
| **BRG** | Begin Resource Group | ✅ |
| **BRS** | Begin Resource | ✅ |
| **BSG** | Begin Resource Environment Group | ✅ |
| **EAG** | End Active Environment Group | ✅ |
| **EDI** | End Document Index | ✅ |
| **EDT** | End Document | ✅ |
| **ENG** | End Named Page Group | ✅ |
| **EOC** | End Object Container | ✅ |
| **EOG** | End Object Environment Group | ✅ |
| **EPF** | End Print File | ✅ |
| **EPG** | End Page | ✅ |
| **EPS** | End Page Segment | ✅ |
| **ERG** | End Resource Group | ✅ |
| **ERS** | End Resource | ✅ |
| **ESG** | End Resource Environment Group | ✅ |
| **IEL** | Index Element | ✅ |
| **IOB** | Include Object | ✅ |
| **IPG** | Include Page | ✅ |
| **IPO** | Include Page Overlay | ✅ |
| **IPS** | Include Page Segment | ✅ |
| **LLE** | Link Logical Element | ✅ |
| **MCF** | Map Coded Font (F1/F2) | ✅ |
| **MDD** | Medium Descriptor | ✅ |
| **MDR** | Map Data Resource | ✅ |
| **MMO** | Map Medium Overlay | ✅ |
| **MPG** | Map Page | ✅ |
| **MPO** | Map Page Overlay | ✅ |
| **MPS** | Map Page Segment | ✅ |
| **MSU** | Map Suppression | ✅ |
| **NOP** | No Operation | ✅ |
| **OBD** | Object Area Descriptor | ✅ |
| **OBP** | Object Area Position | ❌ |
| **OCD** | Object Container Data | ✅ |
| **PEC** | Presentation Environment Control | ✅ |
| **PFC** | Presentation Fidelity Control | ✅ |
| **PGD** | Page Descriptor | ✅ |
| **PGP** | Page Position (F1/F2) | ❌ |
| **PMC** | Page Modification Control | ❌ |
| **PPO** | Preprocess Presentation Object | ❌ |
| **TLE** | Tag Logical Element | ✅ |

### 2.2. PTOCA (Presentation Text Object Content Architecture)
PTOCA SFs handle the inclusion and description of text data.

| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BPT** | Begin Presentation Text | ✅ |
| **EPT** | End Presentation Text | ✅ |
| **PTX** | Presentation Text Data | ✅ |
| **PTD** | Presentation Text Descriptor (F1/F2) | ❌ |

### 2.3. BCOCA (Bar Code Object Content Architecture)
| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BBC** | Begin Bar Code | ✅ |
| **EBC** | End Bar Code | ✅ |
| **BDA** | Bar Code Data | ✅ |
| **BDD** | Bar Code Data Descriptor | ✅ |
| **MBC** | Map Bar Code | ❌ |

### 2.4. GOCA (Graphics Object Content Architecture)
| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BGR** | Begin Graphics | ✅ |
| **EGR** | End Graphics | ✅ |
| **GAD** | Graphics Data | ✅ |
| **GDD** | Graphics Data Descriptor | ❌ |
| **MGO** | Map Graphics Object | ❌ |

### 2.5. IOCA (Image Object Content Architecture)
| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BIM** | Begin Image | ✅ |
| **EIM** | End Image | ✅ |
| **IDD** | Image Data Descriptor | ✅ |
| **IPD** | Image Picture Data | ✅ |
| **MIO** | Map Image Object | ❌ |
| **IRD** | IM Image Raster Data | ✅ |
| **ICP** | IM Image Cell Position | ❌ |
| **IID** | IM Image Input Descriptor | ❌ |
| **IOC** | IM Image Output Control | ❌ |

### 2.6. FOCA (Font Object Content Architecture)
FOCA coverage is currently limited to structural fields.

| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BCF** | Begin Coded Font | ✅ |
| **BCP** | Begin Code Page | ✅ |
| **BFN** | Begin Font | ✅ |
| **CFC** | Coded Font Control | ❌ |
| **CFI** | Coded Font Index | ❌ |
| **CPC** | Code Page Control | ❌ |
| **CPD** | Code Page Descriptor | ❌ |
| **CPI** | Code Page Index | ❌ |
| **ECF** | End Coded Font | ✅ |
| **ECP** | End Code Page | ✅ |
| **EFN** | End Font | ✅ |
| **FNC** | Font Control | ✅ |
| **FND** | Font Descriptor | ❌ |
| **FNG** | Font Patterns | ❌ |
| **FNI** | Font Index | ❌ |
| **FNM** | Font Patterns Map | ❌ |
| **FNN** | Font Name Map | ✅ |
| **FNO** | Font Orientation | ❌ |
| **FNP** | Font Position | ❌ |

### 2.7. Line Data
| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BDM** | Begin Data Map | ✅ |
| **BDX** | Begin Data Map Transmission Subcase | ✅ |
| **BPM** | Begin Page Map | ✅ |
| **CCP** | Conditional Processing Control | ✅ |
| **DXD** | Data Map Transmission Subcase Descriptor | ✅ |
| **EDM** | End Data Map | ✅ |
| **EDX** | End Data Map Transmission Subcase | ✅ |
| **EPM** | End Page Map | ✅ |
| **FDS** | Fixed Data Size | ✅ |
| **FDX** | Fixed Data Text | ✅ |
| **IDM** | Invoke Data Map | ✅ |
| **LNC** | Line Descriptor Count | ✅ |
| **LND** | Line Descriptor | ✅ |
| **RCD** | Record Descriptor | ✅ |
| **XMD** | XML Descriptor | ✅ |

### 2.8. CMOCA & MO:DCA-L
| SF Acronym | Description | Status |
| :--- | :--- | :---: |
| **BCA** | Begin Color Attribute Table | ✅ |
| **ECA** | End Color Attribute Table | ✅ |
| **CAT** | Color Attribute Table | ✅ |
| **MCA** | Map Color Attribute Table | ✅ |
| **CMR** | Color Management Resource | ✅ |

---

## 3. MO:DCA Triplet Coverage

Most MO:DCA Triplets are covered by `TripletRoundTripTest.java`.

| Triplet ID | Name | Status |
| :--- | :--- | :---: |
| **X'01'** | Coded Graphic Character Set Global ID | ✅ |
| **X'02'** | Fully Qualified Name | ✅ |
| **X'04'** | Mapping Option | ✅ |
| **X'10'** | Object Classification | ✅ |
| **X'18'** | MO:DCA Interchange Set | ✅ |
| **X'1D'** | Text Orientation (Retired) | ✅ |
| **X'1F'** | Font Descriptor Specification | ✅ |
| **X'20'** | Font CGCSGID | ✅ |
| **X'21'** | Resource Object Type | ✅ |
| **X'22'** | Extended Resource Local ID | ✅ |
| **X'24'** | Resource Local ID | ✅ |
| **X'25'** | Resource Section Number | ✅ |
| **X'26'** | Character Rotation | ✅ |
| **X'2D'** | Object Byte Offset | ✅ |
| **X'36'** | Attribute Value | ✅ |
| **X'43'** | Descriptor Position | ✅ |
| **X'45'** | Media Eject Control | ✅ |
| **X'46'** | Page Overlay Cond. Processing | ✅ |
| **X'47'** | Resource Usage Attribute | ✅ |
| **X'4B'** | Measurement Units | ✅ |
| **X'4C'** | Object Area Size | ✅ |
| **X'4D'** | Area Definition | ✅ |
| **X'4E'** | Color Specification | ✅ |
| **X'50'** | Encoding Scheme ID | ✅ |
| **X'56'** | Medium Map Page Number | ✅ |
| **X'57'** | Object Byte Extent | ✅ |
| **X'58'** | Object SF Offset | ✅ |
| **X'59'** | Object SF Extent | ✅ |
| **X'5A'** | Object Offset | ✅ |
| **X'5D'** | Font Horizontal Scale Factor | ✅ |
| **X'5E'** | Object Count | ✅ |
| **X'62'** | Local Object Date and Time Stamp | ✅ |
| **X'65'** | Comment | ✅ |
| **X'68'** | Medium Orientation | ✅ |
| **X'6C'** | Resource Object Include | ✅ |
| **X'70'** | Presentation Space Reset Mixing | ✅ |
| **X'71'** | Presentation Space Mixing Rule | ✅ |
| **X'72'** | Universal Date and Time Stamp | ✅ |
| **X'74'** | Toner Saver | ✅ |
| **X'75'** | Color Fidelity | ✅ |
| **X'78'** | Font Fidelity | ✅ |
| **X'80'** | Attribute Qualifier | ✅ |
| **X'81'** | Page Position Information | ✅ |
| **X'82'** | Parameter Value | ✅ |
| **X'83'** | Presentation Control | ✅ |
| **X'84'** | Font Resolution and Metric Tech | ✅ |
| **X'85'** | Finishing Operation | ✅ |
| **X'86'** | Text Fidelity | ✅ |
| **X'87'** | Media Fidelity | ✅ |
| **X'88'** | Finishing Fidelity | ✅ |
| **X'8B'** | Data Object Font Descriptor | ✅ |
| **X'8C'** | Locale Selector | ✅ |
| **X'8E'** | UP3i Finishing Operation | ✅ |
| **X'8F'** | MO:DCA Function Set | ✅ |
| **X'91'** | CMR Descriptor | ✅ |
| **X'95'** | Rendering Intent | ✅ |
| **X'96'** | CMR Tag Fidelity | ✅ |
| **X'97'** | Device Appearance | ✅ |
| **X'9A'** | Image Resolution | ✅ |
| **X'9D'** | Keep Group Together | ✅ |
| **X'9E'** | Setup Name | ✅ |
| **X'FF'** | Triplet Extender | ✅ |

**Untested/Gaps:**
- **X'27'**: Line Data Object Position Migration (Retired)
- **X'63'**: Object Checksum (Retired)
- **X'64'**: Object Origin Identifier (Retired)
- **X'73'**: IMM Insertion Triplet (Retired)
- **X'9C'**: Object Container Presentation Space Size

---

## 4. PTOCA Control Sequence Coverage

PTOCA Control Sequences are verified in `PTOCAControlSequenceRoundTripTest.java`.

| CS Acronym | Name | Status |
| :--- | :--- | :---: |
| **AMI** | Absolute Move Inline | ✅ |
| **AMB** | Absolute Move Baseline | ✅ |
| **RMI** | Relative Move Inline | ✅ |
| **RMB** | Relative Move Baseline | ✅ |
| **SIM** | Set Inline Margin | ✅ |
| **SBI** | Set Baseline Increment | ✅ |
| **BLN** | Begin Line | ✅ |
| **STO** | Set Text Orientation | ✅ |
| **SCFL** | Set Coded Font Local | ✅ |
| **STC** | Set Text Color | ✅ |
| **SEC** | Set Extended Text Color | ✅ |
| **SIA** | Set Intercharacter Adjustment | ✅ |
| **SVI** | Set Variable Space Character Increment | ✅ |
| **TBM** | Temporary Baseline Move | ✅ |
| **BSU** | Begin Suppression | ✅ |
| **ESU** | End Suppression | ✅ |
| **OVS** | Overstrike | ✅ |
| **USC** | Underscore | ✅ |
| **RPS** | Repeat String | ✅ |
| **DIR** | Draw I-axis Rule | ✅ |
| **DBR** | Draw B-axis Rule | ✅ |
| **NOP** | No Operation | ✅ |
| **UCT** | Unicode Complex Text | ✅ |
| **GLC** | Glyph Layout Control | ✅ |
| **ENC** | Encrypted Data | ✅ |
| **SKI** | Set Key Information | ✅ |
| **SEA** | Set Encrypted Alternate | ✅ |
| **GIR** | Glyph Id Run | ✅ |
| **GAR** | Glyph Advance Run | ✅ |
| **GOR** | Glyph Offset Run | ✅ |
| **TRN** | Transparent Data | ✅ |
| **Undefined** | Undefined Control Sequence | ✅ |
| **GraphicCharacters** | Free-standing Graphic Characters | ✅ |

---

## 5. Implementation Depth (Deep vs. Shallow)

The parser distinguishes between "Deep" support (full payload parsing) and "Shallow" support (header parsing only).

- **Deep Support:** SFs that override `decodeAFP` and `writeAFP` to handle specific architectural parameters.
- **Shallow Support:** SFs that inherit from `StructuredFieldBaseTriplets` or `StructuredFieldBaseName` without additional payload parsing.

Currently, **95+ SF classes** provide Deep support. Remaining gaps are primarily in specialized FOCA fields and retired/legacy MO:DCA fields.

---

## 6. How to Run Coverage Tests

To run the full test suite and verify architectural coverage:

```bash
./gradlew test
```

Round-trip tests are located in `src/test/java/com/mgz/afp/` under their respective architecture packages.
