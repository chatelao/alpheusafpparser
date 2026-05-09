# Printed Text Sources in AFP Specifications

This document lists the elements within the AFP (Advanced Function Presentation) architectures where raw, human-readable text values are defined. These sources represent the content that is eventually rendered or used as metadata within an AFP document.

## PTOCA (Presentation Text Object Content Architecture)

| Mnemonic | Hex ID | Description |
| :--- | :--- | :--- |
| **TRN** | `X'DA'`, `X'DB'` | **Transparent Data**: Specifies a string of characters to be presented without being checked for control sequences. |
| **RPS** | `X'EE'`, `X'EF'` | **Repeat String**: Specifies a string of characters repeated until a specified length is reached. |
| **UCT** | `X'6A'` | **Unicode Complex Text**: Identifies a sequence of Unicode code points (UTF-16BE) for complex text rendering. |
| **Graphic Characters** | N/A | **Free-standing Characters**: Graphic character code points that appear between control sequences in a PTX structured field. |
| **NOP** | `X'F8'`, `X'F9'` | **No Operation**: A string of bytes to be ignored by the presentation process, often used for internal comments or padding. |

## GOCA (Graphics Object Content Architecture)

| Mnemonic | Hex ID | Description |
| :--- | :--- | :--- |
| **GCHST** | `X'C3'` | **Character String at Given Position**: Draws a character string starting at a specified (X, Y) coordinate. |
| **GCCHST** | `X'83'` | **Character String at Current Position**: Draws a character string starting at the current drawing position. |
| **GCOMT** | `X'01'` | **Comment**: A character string used for documentation within a graphics segment. |

## MO:DCA (Mixed Object Document Content Architecture)

| Element | Hex ID | Description |
| :--- | :--- | :--- |
| **TLE** | `X'D3A090'` | **Tag Logical Element**: Assigns an attribute name and value to a page or page group. |
| **Triplet X'36'** | `X'36'` | **Attribute Value Triplet**: Carries the actual attribute value string within a TLE. |
| **NOP** | `X'D3EEEE'` | **No Operation**: A structured field containing a string of bytes ignored by the receiver, used for comments. |
| **Triplet X'65'** | `X'65'` | **Comment Triplet**: Carries a character string used for descriptive comments. |
| **COMT** | `X'D3A088'` | **Comment**: A structured field used to include comments in the data stream. |

## BCOCA (Bar Code Object Content Architecture)

| Mnemonic | Hex ID | Description |
| :--- | :--- | :--- |
| **BDA** | `X'D3EE7B'` | **Bar Code Data**: Contains the data to be encoded in the bar code. This data is often also printed as **Human-Readable Interpretation (HRI)**. |

## Line Data

| Element | Mnemonic | Description |
| :--- | :--- | :--- |
| **Constant Content** | **CCP** | Specifies fixed text to be added to the data records during the formatting process. |

## CMOCA (Color Management Object Content Architecture)

| Element | Description |
| :--- | :--- |
| **CMR Header** | Bytes 10–155 of the **Color Management Resource (CMR)** header contain UTF-16BE encoded character data, including the CMR Name, Description, and Manufacturer. |

## FOCA (Font Object Content Architecture)

| Field | Description |
| :--- | :--- |
| **CPDesc** | **Code Page Description**: A 32-character field describing the code page. |
| **TypeFcDesc** | **Typeface Description**: A 32-character field describing the typeface. |
| **Resource Names** | Character strings identifying Coded Fonts, Font Character Sets, and Code Pages (e.g., `CFName`, `CSName`, `CPName`). |
