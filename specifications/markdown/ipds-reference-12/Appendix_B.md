Appendix B. Examples of IPDS Command Sequences
This appendix provides examples of the following IPDS command sequences:
• Initialization
• Page Segment
• Overlay
• Page
Note: These sequences are only examples, and the host need not send each command listed.
In the following examples, commands that request an acknowledgment from the printer have the
Acknowledgment Required (ARQ) bit on. When the ARQ bit is on, the printer sends an Acknowledge Reply
(ACK) to the host. In the example below:
→ indicates a command from the host to the printer
← indicates a reply from the printer to the host.
Table 72. A Typical IPDS Command Sequence
Sequence Flow Command Name Description
Initialization → Sense Type and Model (STM) with ARQ IPDS command set implementation
← Acknowledge Reply (ACK) Return type and model information
→ XOH Obtain Printer Characteristics (OPC) with
ARQ
Request printer characteristics
← Acknowledge Reply (ACK) Return printer characteristics
→ Set Home State (SHS) Set printer to home state
→ Logical Page Descriptor (LPD) Define a logical page
→ Logical Page Position (LPP) Position a logical page
→ Load Copy Control (LCC) Select copy options
→ Load Font Equivalence (LFE) with ARQ Establish font equivalences
← Acknowledge Reply (ACK) Acknowledge successful operation
Page Segment → Begin Page Segment (BPS) Set printer to Page Segment state
→ Write T ext (WT) Store text data in page segment
→ Write T ext (WT) Store text data in page segment
→ Write T ext (WT) Store text data in page segment
→ Write Image Control 2 (WIC2) Start IO-Image state
→ Write Image 2 (WI2) Store IO-Image data in page segment
→ End End IO-Image state
→ Write T ext (WT) Store text data in page segment
→ Write T ext (WT) Store text data in page segment
→ End Page (EP) with ARQ Return to home state
← Acknowledge Reply (ACK) Acknowledge successful operation
→ Logical Page Descriptor (LPD) Define a logical page

## Page 1028

994 IPDS Reference
Table 72 A Typical IPDS Command Sequence (cont'd.)
Sequence Flow Command Name Description
→ Load Font Equivalence (LFE) Establish font equivalences
Overlay → Begin Overlay (BO) Enter overlay state
→ Write T ext (WT) Store text data in overlay
→ Write T ext (WT) Store text data in overlay
→ Write T ext (WT) Store text data in overlay
→ Include Overlay (IO) Include another overlay
→ Write Graphics Control (WGC) Enter graphics state
→ Write Graphics (WG) Store graphics data in overlay
→ Write Graphics (WG) Store graphics data in overlay
→ End End graphics state
→ Write T ext (WT) Store text data in overlay
→ Write T ext (WT) Store text data in overlay
→ Include Overlay (IO) Include another overlay
→ Include Page Segment (IPS) Include page segment in overlay
→ End Page (EP) with ARQ Return to home state
← Acknowledge Reply (ACK) Acknowledge successful operation
Page → Begin Page (BP) Enter page state
→ Write T ext (WT) Send text data to printer
→ Include Overlay (IO) Print overlay
→ Include Page Segment (IPS) Print page segment
→ Write Image Control (WIC) Start IM-Image state
→ Write Image (WI) Send IM-Image data to printer
→ Write Image (WI) Send IM-Image data to printer
→ End End IM-Image state
→ Write T ext (WT) Send text data to printer
→ Write T ext (WT) Send text data to printer
→ Include Overlay (IO) Print overlay
→ Include Page Segment (IPS) Print page segment
→ End Page (EP) with ARQ Complete all printing and return to home
state
← Acknowledge Reply (ACK) Acknowledge successful operation

## Page 1029

IPDS Reference 995
A Printer-Initialization Sequence
Before any printing begins, the host must specify certain parameters and conditions for the printer. The
following command sequence, as shown in T able 72 on page 993, accomplishes this task.
STM (Sense Type and Model)
The host sends the STM command to sense the IPDS command set implementation.
ACK (Acknowledge Reply)
If the STM command has the ARQ bit on, the printer responds with type and model
information to the host. This information includes printer type, model, and command-set vector
information.
XOH OPC (Obtain Printer Characteristics)
The host sends the XOH-OPC command to the printer, requesting printer characteristics to be
placed in the Acknowledge Reply special data area.
ACK (Acknowledge Reply)
If the XOH-OPC command has the ARQ bit on, the information is supplied; if the ARQ bit is off,
the XOH-OPC command is treated as a NOP .
SHS (Set Home State)
The host sends the SHS command to ensure the printer is in home state.
LPD (Logical Page Descriptor)
The LPD command sets print characteristics for the current logical page. These parameters
include logical page size, initial text-coordinate positions and text direction, initial text margin,
intercharacter adjustment, baseline increment, font local ID, and text color.
LPP (Logical Page Position)
The LPP command positions the upper-left corner of the logical page (as defined by the LPD
command) with respect to the medium presentation space.
LCC (Load Copy Control)
The LCC command specifies how many copies of each sheet are produced, whether to print
simplex or duplex, the overlays that are to be included on each copy, and the suppressions
that are to be activated for each copy. Suppression allows text data to be selectively
suppressed during printing.
LFE (Load Font Equivalence)
The LFE command maps font local IDs (used within the text, graphics, or bar code data) to
font Host-Assigned IDs used for resource management.
ACK (Acknowledge Reply)
If the LFE command has the ARQ bit on, the printer responds with the Acknowledge Reply to
inform the host of successful receipt of all the previous commands. The printer is now ready to
accept data for print operations. The initialization and preparation sequence is finished.

## Page 1030

996 IPDS Reference
The Page-Segment Sequence
The page-segment sequence creates a page segment resource for later printing. The following command
sequence, as shown in T able 72 on page 993, illustrates the loading of a page segment.
Note: This sequence is only an example. A page segment can contain any combination of text, image,
graphics, and bar code data.
BPS (Begin Page Segment)
The host sends the BPS command to the printer, causing the printer to leave home state and
enter page segment state. Page segment state creates a segment of page data to save within
the printer for later printing. The BPS command contains an ID for later use in selecting this
segment. This segment can contain combinations of text, images, bar code data, and
graphics.
WT (Write Text)
The WT command sends text data to the printer. Because the printer is currently in page
segment state, the text information does not print at this time. Instead, the data becomes part
of the page segment. If no text data is to be included in the segment, this command does not
occur. Multiple WT commands can be sent to the printer while in page segment state.
WIC2 (Write Image Control 2)
The WIC2 command causes the printer to enter IO-Image state.
WI2 (Write Image 2)
The WI2 command sends IO-Image data to the printer.
Note: Both the IM-Image commands (WIC and WI) and the IO-Image commands (WIC2 and
WI2) send image data to the printer. However, the IO-Image commands provide
additional functions over the IM-Image commands.
END (End)
The END command terminates IO-Image state. The printer returns to page segment state,
with the image stored for later use.
WT (Write Text)
The WT command is repeated at this point in the sequence, to illustrate that additional text
data can be added to the page segment. Graphics data, bar code data, or additional image
data can also be included in the page segment.
EP (End Page)
The EP command causes the printer to leave page segment state and return to home state.
This command can contain an ARQ to ensure successful transmission of the page segment.
ACK (Acknowledge Reply)
If the EP command has the ARQ bit on, the printer responds with the ACK to inform the host of
successful receipt of all the previous commands. This reply indicates to the host that the
printer has accepted all the segment data and has stored this information for later printing.
Note: Page segment commands need not be syntax-checked until they are included on a
logical page by use of the Include Page Segment command.

## Page 1031

IPDS Reference 997
The Overlay Sequence
The overlay sequence creates an overlay resource for later printing. The following command sequence, as
shown in T able 72 on page 993, illustrates the loading of a typical overlay.
Note: This sequence is only an example. An overlay can contain any combination of text data, image data,
graphics data, bar code data, object container data, included page segments, and included overlays.
LPD (Logical Page Descriptor)
The LPD command sets print characteristics for the current logical page; it will be stored with
the overlay. These parameters include logical page size, initial text-coordinate positions and
text direction, initial text margin, intercharacter adjustment, baseline increment, font local ID,
and text color.
LFE (Load Font Equivalence)
The LFE command maps font local IDs (used within the text, graphics, or bar code data) to
font Host-Assigned IDs used for resource management. It will be stored with the overlay.
BO (Begin Overlay)
The host sends the BO command to the printer, causing the printer to leave home state and
enter overlay state. Overlay state creates an overlay resource to be saved within the printer for
later printing. The BO command contains an ID for later use in selecting this overlay.
WT (Write Text)
The WT command sends text data to the printer. Because the printer is currently in overlay
state, this text information does not print at this time. Instead, the data becomes part of the
overlay. If no text data is to be included in the overlay, this command does not occur. Multiple
WT commands can be sent to the printer while in overlay state.
IO (Include Overlay)
The IO command causes a previously stored overlay to be included with the current overlay.
The IO command contains an ID field that specifies the overlay.
WGC (Write Graphics Control)
The WGC command causes the printer to enter graphics state. Parameters in this command
specify the placement, size, and orientation of the graphics object area.
WG (Write Graphics)
The WG command sends graphics data to the printer. The graphics data is contained in
drawing orders, that specify various elements of the graphics picture. These include color,
size, line type, line width, and other parameters. One or more WG commands present the
graphics picture.
END (End)
The END command terminates graphics state. The printer returns to overlay state with the
graphics data now part of the overlay.
WT (Write Text)
The WT command is repeated at this point in the sequence to illustrate that additional text data
can be added to the overlay. Image data, graphics data, bar code data, or object container
data can also be included in the overlay.
IO (Include Overlay)
The IO command causes a previously stored overlay to be included with the current overlay.
IPS (Include Page Segment)
The IPS command causes a previously stored page segment to be included in the current
overlay as if it had been part of the overlay data. The IPS command contains an ID field that
specifies the page segment.

## Page 1032

998 IPDS Reference
EP (End Page)
The EP command causes the printer to leave overlay state and return to home state. This
command can contain an ARQ to verify transmission of the overlay.
ACK (Acknowledge Reply)
If the EP command has the ARQ bit on, the printer responds with the ACK reply to inform the
host of successful receipt of all the previous commands. This reply indicates to the host that
the printer has accepted all of the overlay data and has stored this information for later
printing.
Note: Overlay commands need not be syntax-checked until they are included on a logical
page by use of the Include Overlay command or on the medium presentation space by
use of the Load Copy Control command.

## Page 1033

IPDS Reference 999
The Page Sequence
The page sequence causes a page to be created and printed on the current sheet. This data can include
previously stored overlays or page segments, as well as immediate text or object data. The following command
sequence, as shown in T able 72 on page 993, illustrates the loading of a page.
Note: This sequence is only an example. A page sequence can contain any combination of text data, image
data, graphics data, bar code data, object container data, page segments, or overlays.
BP (Begin Page)
The host sends the BP command to the printer, causing the printer to leave home state and
enter page state.
WT (Write Text)
The WT command sends text data to the printer. Because the printer is currently in page state,
this text information prints on the current logical page. Multiple WT commands can be sent to
the printer while in page state.
IO (Include Overlay)
The IO command causes a previously stored overlay to merge with the current logical page.
The IO command contains an ID field that specifies the overlay. This included overlay is
independent of the page and can extend partially or completely outside of the page's logical
page.
IPS (Include Page Segment)
The IPS command causes a previously stored page segment to merge with the current logical
page as if it had been part of the page data. An ID field in this command identifies the page
segment.
WIC (Write Image Control)
The WIC command causes the printer to enter IM-Image state.
WI (Write Image)
The WI command sends IM-Image data to the printer. One or more of these commands create
the actual image for printing.
Note: Both the IM-Image commands (WIC and WI) and the IO-Image commands (WIC2 and
WI2) send image data to the printer. However, the IO-Image commands provide
additional functions over the IM-Image commands.
END (End)
The END command terminates IM-Image state. The printer returns to page state.
WT (Write Text)
The WT command is repeated at this point in the sequence to illustrate that additional text data
can be added to the page. Image data, graphics data, bar code data, or object container data
can also be included on the page.
IO (Include Overlay)
The IO command causes a previously stored overlay to merge with the current logical page.
IPS (Include Page Segment)
The IPS command causes a previously stored page segment to merge with the current logical
page as if it had been part of the page data. An ID field in this command identifies the page
segment.
EP (End Page)
The EP command causes the printer to leave page state and return to home state. This
command can contain an ARQ to verify successful transmission of the page data.

## Page 1034

1000 IPDS Reference
ACK (Acknowledge Reply)
If the EP command had the ARQ bit on, the printer responds with the ACK reply to inform the
host of successful receipt of all the previous commands. This reply indicates to the host that
the printer has successfully accepted all the previous commands, and that the page will
subsequently be transferred to paper.

## Page 1035

Copyright © AFP Consortium 1987, 2023 1001
