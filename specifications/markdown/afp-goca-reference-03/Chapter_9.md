Chapter 9. Compliance
This chapter describes the AFP GOCA subsets that are supported in the AFP GOCA architecture.
AFP GOCA Subsets
AFP GOCA subsets are used to identify a specific level of AFP GOCA functionality . Each new (higher level) subset must incorporate the complete functionality of the previous (lower level) subset. The naming of AFP
GOCA subsets and subsets of GOCA architecture is rooted in the original IBM GOCA architecture that was defined in the late 1980s. The first AFP GOCA subset, DR/2V0, follows the original GOCA naming conventions and represents a GOCA subset that contains drawing orders at level 2 and base functionality at version 0.
However , using this naming scheme to identify additional AFP GOCA subsets is impractical, and therefore a new naming scheme will be used to identify AFP GOCA subsets beyond DR/2V0. This new naming scheme is defined as follows.
Each new AFP GOCA subset beyond DR/2V0 will be identified by the term GRSx, where:
• GRS = Graphics Subset
• x = level. GRS levels start with “2” = level 2. The next level can be called “3” = level 3. The GRS levels are not tied to the GOCA “DR” levels, although the starting level (“2”) is chosen to indicate a relationship to
GOCA drawing order level 2 (DR/2), which is supported in the first AFP GOCA subset DR/2V0.
Based on this new naming scheme, the current DR/2V0 subset is assigned the synonym “GRS2”, and the next new subset is called GRS3 = Graphics Subset Level 3.
Base (Mandatory) Level (V ersion 0)
This level represents the base set of functions defined within V ersion 0 of GOCA. It is the minimum set of functions required to be supported in any environment. It consists of the following minimum general communication capabilities:
• Recognition of commands and modes
• Interpretation and validation of the commands within the mode
• Rejection of those commands and modes that are not supported, and return of error data, within the supported subset levels
• Reporting, on request of the environment, the supported features of the drawing process
• Reporting error conditions to the environment
The following commands are supported:
• Begin Segment (chained) in immediate mode

---

Drawing Order Level 2, V ersion 0 (DR/2V0)
This is a GOCA subset supported by printers and viewers in AFP environments. The DR/2V0 subset is also referred to as “GRS2”.
Immediate segments are a prerequisite to DR/2V0.
The following segment properties must be supported:
• Length
• Name (ignored in AFP GOCA)
• Chain
• Prolog
• New/Append
The coordinate type value is X'00'—2-D coordinates.
The geometric parameter format is X'00'—16-bit signed integer , high-order byte first.
The functions include straight and curved lines, areas, images, character strings, patterns, and markers. The following drawing orders must be supported:
• Begin Area (GBAR) order . The required support for INSIDE flag is Alternate Mode.
• Begin Image (GBIMG, GCBIMG) orders (format X'00' only)
• Character String (GCHST , GCCHST) orders
• Comment (GCOMT) order
• End Area (GEAR) order
• End Image (GEIMG) order
• End Prolog (GEPROL) order
• Fillet (GFL T , GCFL T) orders
• Full Arc (GF ARC, GCF ARC) orders
• Image Data (GIMD) order
• Line (GLINE, GCLINE) orders
• Marker (GMRK, GCMRK) orders
• No-Operation (GNOP1) order
• Relative Line (GRLINE, GCRLINE) orders
• Segment Characteristics (GSGCH) order . A check that this order is in the prolog state is optionally performed.
• Set Arc Parameters (GSAP) order
• Set Background Mix (GSBMX) order . The required support is X'00' and X'05'—Leave Alone.
• Set Character Angle (GSCA) order . The required support is 90-degree angles when applied to precision 2 symbols.
• Set Character Cell (GSCC) order
• Set Character Direction (GSCD) order
• Set Character Precision (GSCR) order . The required support is drawing default and precisions 1 and 2.
• Set Character Set (GSCS) order

---

• Set Character Shear (GSCH) order . The required support is drawing default and “no shear”. Other values can be treated as “no shear”, but generators should not produce these values.
• Set Color (GSCOL) order
• Set Current Position (GSCP) order
• Set Extended Color (GSECOL) order
• Set Line Type (GSL T) order
• Set Line Width (GSL W) order . The required support is normal line width, plus a further line width selectable by a multiplier of two.
• Set Marker Cell (GSMC) order . The required support is drawing default.
Note: At the time the DR/2V0 subset was defined in AFP GOCA, it was specified in this Reference that the
Set Marker Cell order was to be processed as a No-Op, and the only valid value for the LENGTH field was 4, so this is the only functionality required in DR/2V0.
• Set Marker Precision (GSMP) order . The required support is drawing default and precisions 1 and 2.
• Set Marker Set (GSMS) order . The required support is drawing default (default marker set).
Note: Because the required support for marker set is drawing default, the only marker set available in the
AFP environment is the default marker set.
• Set Marker Symbol (GSMT) order
• Set Mix (GSMX) order . The required support is X'00' and X'02'—Overpaint.
• Set Pattern Set (GSPS) order . The required support is drawing default (default pattern set).
Note: Because the required support for pattern set is drawing default, the only pattern set available in the
AFP environment is the default pattern set.
• Set Pattern Symbol (GSPT) order
Architecture Notes:
1. Some AFP printers accept the Set Fractional Line Width (GSFL W) order .
2. Some AFP printers accept the following drawing orders and process them as No-Ops:
• Set Pick Identifier (GSPIK, X'43'). This drawing order is in long format.
• End Segment drawing order (X'71'). This drawing order is in fixed 2-byte format, where the second byte is reserved and should be set to X'00'.

---

Graphics Subset Level 3 (GRS3)
This subset contains all of subset DR/2V0 (GRS2), as well as the following additional drawing orders and functionality:
• Set Fractional Line Width (GSFL W) order
• Set Process Color (GSPCOL) order
• Box (GBOX, GCBOX) orders; required support does not include the ability to draw boxes in a clockwise direction
• Partial Arc (GP ARC, GCP ARC) orders
• Image resolution information for GOCA image in MO:DCA and IPDS Graphics Data Descriptor (GDD)
• New exception EC-C303 for T rueType/OpenType font support
• Extensions to Set Current Defaults instruction:
- Set Process Color (SET = X'10') - Set Normal Line Width (SET = X'1 1')
• Support for clockwise arcs in addition to the support for counterclockwise arcs (which was part of DR/2V0), as specified by the determinant of the arc parameters
• Support for the full range of possible line widths in the Set Line Width (GSL W) order
Architecture note: As with DR/2V0, the Set Pick Identifier (X'43') and End Segment (X'71') drawing orders are tolerated, but not defined, in GRS3. As a recommendation, GRS3-compliant receivers should accept these two drawing orders and treat them as No-Ops, GRS3-compliant generators should not generate them, and GRS3 validators must allow them (but may generate a warning to discourage future use).

---

Copyright © AFP Consortium 1997, 2017 179
