Appendix C. AFP GOCA Migration Functions
Introduction
This appendix:
• Describes migration functions that may occur in an AFP GOCA object.
General
The objective in defining migration functions is twofold:
1. T o allow existing applications to run unchanged.
2. T o provide a clear growth direction for future applications.
The migration functions are divided into the following categories:
1. Obsolete functions. These are attributes, drawing orders, and parameters that will be accepted but ignored. New products must not generate these functions.
2. Obsolete exception conditions. These are exception conditions that will be accepted but ignored. New products must not generate these functions.
3. Retired functions. These are drawing orders and parameters whose use has been retired except for specific products. These specific products may use these functions. All other products should not use these functions; that is, generators should not generate these functions and receivers may ignore them. 4. Retired exception conditions. These are exception conditions whose use has been retired except for specific products. These specific products may report these exception conditions. All other products should not report these exception conditions.
Obsolete Functions
Obsolete Attributes
Marker Precision Attribute
The marker precision attribute was used in AFP GOCA as a method to allow implementations to draw markers in a device-dependent fashion, rather than necessarily using the marker attributes. Previously , the attribute was defined in the following way in “Markers” on page 57:
The position and appearance of a marker are dependent on the value of the marker precision attribute, as follows:
Precision 1 String Precision. In AFP GOCA, the size of the marker symbols in the default marker set are device dependent. The marker is positioned at a specified point, or at the current position.
Precision 2 Character Precision. In AFP GOCA, this is the same as Precision 1—String Precision.

---

More information about the marker precision attribute and the meaning of its possible values can be found in the description of the drawing order used to set the marker precision attribute, the obsolete Set Marker
Precision (GSMP) Order , shown in the next section.
Obsolete Drawing Orders
Set Marker Precision (GSMP) Order
This order sets the value of the current marker precision attribute.
Syntax
| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | X'3B' | GSMP | order code |
| 1 | CODE | PREC | X'00'-X'03' | Value for marker precision attribute: |
| X'00' Drawing default | | | | |
| X'01' String precision | | | | |
| X'02' Character precision | | | | |
| X'03' | Stroke | precision | (not | supported in AFP GOCA) |
| All | other | values | | Reserved |
| Semantics | | | | |
| The | Set | Marker | Precision | order sets the value of the current marker precision attribute to the value specified in the order . |
| Value Description | | | | |
| X'00' | Drawing | Default. | This | value sets the current marker precision attribute to the value of the drawing default. |
| The | standard | default | in | AFP environments is X'02'—Character precision. |
| X'01' | Precision | 1—String | Precision. | The markers are drawn using the quickest, simplest mode possible in the device. In this mode, the only attributes that must, as a minimum, be |
| considered | when | the | markers | are drawn are the marker symbol and the marker set. The positioning of the marker can be approximate. |
| X'02' | Precision | 2—Character | Precision. | In AFP GOCA, this is treated the same as precision 1. |
| X'03' | Precision | 3—Stroke | Precision. | This value is not supported in AFP GOCA. |
| The | following | exception | conditions | cause a standard action to be taken: |
| EC-0004 | The | attribute | value | specified in the order is not valid. |
| Standard | action: | The | standard | default value of the attribute is used. In AFP environments, this is X'02'—Character precision. |
| EC-000E | The | attribute | value | specified in the order is not supported. |
| Standard | action: | The | standard | default value of the attribute is used. In AFP environments, this is X'02'—Character precision. |
| AFP | GOCA | Migration | | Functions |

---

Obsolete Exception Conditions
EC-C202 The current marker set attribute value identifies a marker set definition that cannot support the functions implied by the current marker precision attribute.
Standard action: The marker set identified by the current marker set attribute value is used, with the lowest value of precision that the marker set can support.
Retired Functions
Retired Parameters
Drawing Order Subset Parameter
The use of this parameter in the GDD is restricted to pre-2012 applications that generate or receive DR/2V0 (GRS2) AFP GOCA objects.
| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | X'F7' | Drawing | Order Subset |
| 1 | UBIN | LENGTH | 7 | Length of following data |
| 2 | CODE | X'B0' | Drawing | order subset |
| 3-4 | RES | X'0000' | Reserved; | only valid value |
| 5 | CODE | SUBLEV | X'02' | Drawing order subset level 2.0 |
| 6 | CODE | VERSION | X'00' | V ersion 0 |
| 7 | UBIN | LENGTH | X'01' | Length of following field |
| 8 | CODE | GEOM | X'00' | Coordinate formats in data: |
| X'00' | 16-bit | high-byte-first | signed | integer |
| If | invalid | bits | are | specified in this self-identifying parameter , EC-000A may optionally be detected. |
| Retired Exception Conditions | | | | |
| EC-0002 | A | reserved | byte | or bit in the order is not set to zero. This is an optional exception. |
| The | use | of | this | exception condition is restricted to pre-May-2012 AFP GOCA receivers. New |
| AFP | GOCA | receivers | should | not report this exception condition. |
| Note: | Some | pre-May-2012 | receivers | might report this exception condition when new functions are encountered that use previously reserved bits. |
| AFP | GOCA | Migration | | Functions |

---

Copyright © AFP Consortium 1997, 2017 199
