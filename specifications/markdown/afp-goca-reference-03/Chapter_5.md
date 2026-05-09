# Chapter 5. Segments

Segments are self-contained collections of drawing orders. This chapter describes:
*   Segment types
*   Segment processing sequence
*   Segment properties
*   Segment prolog

## Segment Types

Segments of the following types can be created, as determined by the CHAIN parameter in the Begin Segment command:
*   Chained
*   Unchained

In AFP GOCA, all segments are processed in immediate mode. In this mode, chained segments define the picture. They are processed by the drawing processor as they are received from the environment interface, and are not stored. Unchained segments are ignored.
Segments are transmitted by the controlling environment to the drawing processor with Begin Segment Commands and the drawing orders that follow these commands. The Begin Segment (chained) command invokes the drawing processor to draw the segment.

In the MO:DCA and IPDS environments, a graphics object can contain multiple chained segments. All chained segments within the object are processed independently in the sequence in which they arrive; together they generate the graphics picture. A segment cannot be split across multiple graphics objects.

The Append option indicates that the segment is a continuation of the preceding segment. Unfinished drawing orders, areas, images, and prologs may be completed in appended segment data. See “Begin Segment Command” on page 75 for further details of the functions of the Append option.

## Segment Processing Sequence

Segment processing starts at the first segment in the segment chain. The processing of a segment always starts at its first order and proceeds in sequence, order by order, until the last order is processed, at which time the segment is terminated.

When the invocation operates on a single segment, it is complete when the segment is terminated.

When the invocation operates on a chain of segments, the graphics processor sequentially processes and terminates each segment in the chain. When the last specified chained segment is terminated, the invocation is complete.

---

## Segment Properties

Associated with each segment is a set of properties. These properties are specified in the Begin Segment command; see “Begin Segment Command” on page 75. The function of these properties is to provide control information relevant to the processing of the segment.

The properties and their functions are as follows:

| Property | Function |
| :--- | :--- |
| **Length** | 2-byte length of segment data. |
| **Chain** | Indicates whether or not the segment is to be chained. |
| **Prolog** | Indicates that the segment has a prolog section at the beginning of the data. See **Segment Prolog** for details of prolog processing. |
| **New/Append** | Indicates whether this is a new segment or a segment to be appended to the previous segment. |

These properties are unique to the segment. They are not inherited between segments. They are defined when a segment is created.
## Segment Prolog

Segment prologs provide a defined position where initial attributes and drawing process controls are set. A prolog is optional; its presence is indicated by the PROLOG bit in the Begin Segment command.

If present, the prolog is always at the beginning of the segment, and is ended by an End Prolog order within the same segment. Note that for a segment that is spread over multiple appended segments with multiple Begin Segment commands, the End Prolog drawing order may be specified in any of the appended segments.

The end of a prolog in a segment must be indicated by an End Prolog order. Exception condition EC-000C is raised if the end of the segment is reached while still in the prolog.

**Note:** Exception condition EC-3E00 is raised if an End Prolog order is found when not in a prolog.

Within the prolog, only the following orders are valid:
*   Comment
*   No-Operation
*   Segment Characteristics
*   Set Arc Parameters
*   Set Background Mix
*   Set Character Angle
*   Set Character Cell
*   Set Character Direction
*   Set Character Precision
*   Set Character Set
*   Set Character Shear
*   Set Color
*   Set Current Position
*   Set Custom Line Type
*   Set Extended Color
*   Set Fractional Line Width
*   Set Line End
Segment Properties

---

*   Set Line Join
*   Set Line Type
*   Set Line Width
*   Set Marker Cell
*   Set Marker Precision (obsolete, see Appendix C, “AFP GOCA Migration Functions”, on page 195)
*   Set Marker Set
*   Set Marker Symbol
*   Set Mix
*   Set Pattern Reference Point
*   Set Pattern Set
*   Set Pattern Symbol
*   Set Process Color

**Implementation Note:** Some AFP printers also accept the Set Pick Identifier order in a prolog, and process this order as a No-Op.

The other supported orders, when specified in the prolog, cause exception condition EC-000C to be raised.

### Segment Prolog Semantics

The semantics of the segment prolog for chained segments processed in immediate mode are as follows.

The segment data is processed by the graphics processor following processing of a Begin Segment command.

For an appended segment—that is, one specified with the APP flag equal to B'11'—the segment data that follows the Begin Segment command is part of the segment, not the whole segment.

For an Immediate mode chained segment that is spread over multiple appended segments with multiple Begin Segment commands, only the PROLOG flag bit in the Begin Segment that is marked as New determines whether the segment has a prolog or not. The PROLOG bits in subsequent Begin Segment commands for appended segments are ignored.

For a segment that is spread over multiple appended segments with multiple Begin Segment commands, it is necessary for the graphics processor to check the segment data in all the Begin Segment commands for the segment before it can determine whether the segment is valid or not, that is, whether or not the segment contains an End Prolog order to match the setting of the segment prolog property.

For example, with a segment whose data is spread over three appended segments with three Begin Segment commands, the first command could indicate that the segment has a prolog, but the End Prolog order could well be in data in the third segment. The PROLOG flag in the first Begin Segment command of a multi-part segment does not indicate that an End Prolog order is necessarily contained within the segment data that follows the Begin Segment command.
Segment Prolog

---

Copyright © AFP Consortium 1997, 2017 65
